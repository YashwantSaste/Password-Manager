package com.project.password.manager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.password.manager.database.DataRepository;
import com.project.password.manager.encryption.AesGcmEncryptionService;
import com.project.password.manager.encryption.IEncryptionService;
import com.project.password.manager.exceptions.EntityNotFoundException;
import com.project.password.manager.exceptions.UnauthorizedSessionException;
import com.project.password.manager.exceptions.UserNotFoundException;
import com.project.password.manager.guice.PlatformEntityProvider;
import com.project.password.manager.model.IEntry;
import com.project.password.manager.model.ILogin;
import com.project.password.manager.model.INote;
import com.project.password.manager.model.ITag;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.VaultPayload;
import com.project.password.manager.model.payload.Entry;
import com.project.password.manager.model.payload.Login;
import com.project.password.manager.model.payload.Note;
import com.project.password.manager.model.payload.Tag;
import com.project.password.manager.util.ModelObjectMapperFactory;

public class VaultService {
	@NotNull
	private final DataRepository<IVault, String> vaultRepository;
	@NotNull
	private final DataRepository<IUser, String> userRepository;
	@NotNull
	private final IEncryptionService encryptionService;
	@NotNull
	private final ObjectMapper objectMapper;

	public VaultService(@NotNull DataRepository<IUser, String> userRepository,
			@NotNull DataRepository<IVault, String> vaultRepository) {
		this(userRepository, vaultRepository, new AesGcmEncryptionService(new UserService(userRepository)),
				ModelObjectMapperFactory.create());
	}

	public VaultService(@NotNull DataRepository<IUser, String> userRepository,
			@NotNull DataRepository<IVault, String> vaultRepository, @NotNull IEncryptionService encryptionService,
			@NotNull ObjectMapper objectMapper) {
		this.userRepository = userRepository;
		this.vaultRepository = vaultRepository;
		this.encryptionService = encryptionService;
		this.objectMapper = objectMapper;
	}

	@NotNull
	public IVault createDefaultVault(@NotNull IUser user) {
		List<IVault> vaults = ensureVaultsInitialized(user);
		if (!vaults.isEmpty() || hasDefaultVault(user)) {
			throw new UnsupportedOperationException("A user can have only one default vault.");
		}
		IVault vault = createVault(user, vaults);
		user.setDefaultVaultId(vault.getId());
		persistUserIfPresent(user);
		return vault;
	}

	@NotNull
	public List<IVault> getAllVaults(@NotNull String userId) {
		return ensureVaultsInitialized(getUser(userId));
	}

	@NotNull
	public IEntry addEntry(@NotNull String userId, @NotNull String vaultId, @NotNull IEntry entry) {
		IVault vault = getOwnedVault(userId, vaultId);
		VaultPayload payload = readVaultPayload(vault);
		Entry normalizedEntry = normalizeEntry(entry);
		if (findEntryById(payload, normalizedEntry.getId()) != null) {
			throw new IllegalArgumentException("Entry with id " + normalizedEntry.getId() + " already exists");
		}
		payload.getEntries().add(normalizedEntry);
		writeVaultPayload(vault, payload);
		return normalizedEntry;
	}

	@NotNull
	public IEntry getEntry(@NotNull String userId, @NotNull String vaultId, @NotNull String entryId) {
		IVault vault = getOwnedVault(userId, vaultId);
		VaultPayload payload = readVaultPayload(vault);
		IEntry entry = findEntryById(payload, requireText(entryId, "Entry id"));
		if (entry == null) {
			throw new EntityNotFoundException("The entry with id " + entryId + " does not exist");
		}
		return entry;
	}

	@NotNull
	public List<IEntry> getEntries(@NotNull String userId, @NotNull String vaultId) {
		IVault vault = getOwnedVault(userId, vaultId);
		return Collections.unmodifiableList(readVaultPayload(vault).getEntries());
	}

	@NotNull
	public IEntry updateEntry(@NotNull String userId, @NotNull String vaultId, @NotNull IEntry entry) {
		IVault vault = getOwnedVault(userId, vaultId);
		VaultPayload payload = readVaultPayload(vault);
		Entry normalizedEntry = normalizeEntry(entry);
		int entryIndex = findEntryIndex(payload, normalizedEntry.getId());
		if (entryIndex < 0) {
			throw new EntityNotFoundException("The entry with id " + normalizedEntry.getId() + " does not exist");
		}
		payload.getEntries().set(entryIndex, normalizedEntry);
		writeVaultPayload(vault, payload);
		return normalizedEntry;
	}

	public void deleteEntry(@NotNull String userId, @NotNull String vaultId, @NotNull String entryId) {
		IVault vault = getOwnedVault(userId, vaultId);
		VaultPayload payload = readVaultPayload(vault);
		int entryIndex = findEntryIndex(payload, requireText(entryId, "Entry id"));
		if (entryIndex < 0) {
			throw new EntityNotFoundException("The entry with id " + entryId + " does not exist");
		}
		payload.getEntries().remove(entryIndex);
		writeVaultPayload(vault, payload);
	}

	@NotNull
	public List<IEntry> searchEntries(@NotNull String userId, @NotNull String vaultId, @NotNull String query) {
		String normalizedQuery = requireText(query, "Search query").toLowerCase(Locale.ROOT);
		IVault vault = getOwnedVault(userId, vaultId);
		List<IEntry> matches = new ArrayList<>();
		for (IEntry entry : readVaultPayload(vault).getEntries()) {
			if (matches(entry, normalizedQuery)) {
				matches.add(entry);
			}
		}
		return matches;
	}

	@NotNull
	public String createVaultForUser(@NotNull String userId) {
		IUser user = getUser(userId);
		IVault vault = createVault(user, ensureVaultsInitialized(user));
		if (!hasDefaultVault(user)) {
			user.setDefaultVaultId(vault.getId());
		}
		userRepository.update(userId, user);
		return vault.getId();
	}

	@NotNull
	public IVault getDefaultVault(@NotNull String userId) {
		IUser user = getUser(userId);
		String defaultVaultId = user.getDefaultVaultId();
		if (defaultVaultId == null || defaultVaultId.trim().isEmpty()) {
			throw new EntityNotFoundException("The user with id " + userId + " does not have a default vault");
		}
		IVault vault = vaultRepository.findById(defaultVaultId);
		if (vault == null) {
			throw new EntityNotFoundException("The default vault with id " + defaultVaultId + " does not exist");
		}
		return vault;
	}

	@NotNull
	private IVault getOwnedVault(@NotNull String userId, @NotNull String vaultId) {
		requireText(userId, "User id");
		IVault vault = getVault(vaultId);
		if (!userId.equals(vault.getUserId())) {
			throw new UnauthorizedSessionException(
					"The vault with id " + vaultId + " does not belong to user " + userId);
		}
		return vault;
	}

	@NotNull
	private IVault getVault(@NotNull String vaultId) {
		String normalizedVaultId = requireText(vaultId, "Vault id");
		IVault vault = vaultRepository.findById(normalizedVaultId);
		if (vault == null) {
			throw new EntityNotFoundException("The vault with id " + normalizedVaultId + " does not exist");
		}
		return vault;
	}

	@NotNull
	private IVault createVault(@NotNull IUser user) {
		return createVault(user, ensureVaultsInitialized(user));
	}

	@NotNull
	private IVault createVault(@NotNull IUser user, @NotNull List<IVault> vaults) {
		String vaultId = UUID.randomUUID().toString();
		IVault vault = PlatformEntityProvider.getEntityProvider().getVault();
		vault.setId(vaultId);
		vault.setUserId(user.getId());
		vault.setEncryptedBlob(encryptPayload(new VaultPayload(), user.getId()));
		vaultRepository.save(vault);
		vaults.add(vault);
		return vault;
	}

	@NotNull
	private VaultPayload readVaultPayload(@NotNull IVault vault) {
		String encryptedBlob = vault.getEncryptedBlob();
		if (encryptedBlob == null || encryptedBlob.isBlank()) {
			return new VaultPayload();
		}
		try {
			String payload = encryptionService.decrypt(encryptedBlob, vault.getUserId());
			VaultPayload vaultPayload = objectMapper.readValue(payload, VaultPayload.class);
			if (vaultPayload.getEntries() == null) {
				vaultPayload.setEntries(new ArrayList<>());
			}
			return vaultPayload;
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to read vault payload for vault " + vault.getId(), ex);
		}
	}

	private void writeVaultPayload(@NotNull IVault vault, @NotNull VaultPayload payload) {
		vault.setEncryptedBlob(encryptPayload(payload, vault.getUserId()));
		vaultRepository.update(vault.getId(), vault);
	}

	@NotNull
	private String encryptPayload(@NotNull VaultPayload payload, @NotNull String userId) {
		try {
			String rawPayload = objectMapper.writeValueAsString(payload);
			return encryptionService.encrypt(rawPayload, userId);
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to encrypt vault payload for user " + userId, ex);
		}
	}

	@NotNull
	private Entry normalizeEntry(@NotNull IEntry entry) {
		String id = requireText(entry.getId(), "Entry id");
		String title = requireText(entry.getTitle(), "Entry title");
		ILogin login = requireLogin(entry.getLogin());
		ITag tag = requireTag(entry.getTag());
		Entry normalizedEntry = new Entry();
		normalizedEntry.setId(id);
		normalizedEntry.setTitle(title);
		normalizedEntry.setLogin(normalizeLogin(login));
		normalizedEntry.setTag(normalizeTag(tag));
		return normalizedEntry;
	}

	@NotNull
	private Login normalizeLogin(@NotNull ILogin login) {
		Login normalizedLogin = new Login();
		normalizedLogin.setLoginName(requireText(login.getLoginName(), "Login name"));
		normalizedLogin.setUri(requireText(login.getUri(), "Login uri"));
		normalizedLogin.setEncryptedValue(requireText(login.getEncryptedValue(), "Encrypted value"));
		List<Note> notes = new ArrayList<>();
		List<INote> loginNotes = login.getNotes();
		if (loginNotes != null) {
			for (INote note : loginNotes) {
				notes.add(normalizeNote(note));
			}
		}
		normalizedLogin.setNotes(notes);
		return normalizedLogin;
	}

	@NotNull
	private Tag normalizeTag(@NotNull ITag tag) {
		Tag normalizedTag = new Tag();
		normalizedTag.setId(requireText(tag.getId(), "Tag id"));
		normalizedTag.setValue(requireText(tag.getValue(), "Tag value"));
		return normalizedTag;
	}

	@NotNull
	private Note normalizeNote(@Nullable INote note) {
		if (note == null) {
			throw new IllegalArgumentException("Note cannot be null");
		}
		Note normalizedNote = new Note();
		normalizedNote.setId(requireText(note.getId(), "Note id"));
		normalizedNote.setDescription(requireText(note.getDescription(), "Note description"));
		return normalizedNote;
	}

	@NotNull
	private ILogin requireLogin(@Nullable ILogin login) {
		if (login == null) {
			throw new IllegalArgumentException("Entry login cannot be null");
		}
		return login;
	}

	@NotNull
	private ITag requireTag(@Nullable ITag tag) {
		if (tag == null) {
			throw new IllegalArgumentException("Entry tag cannot be null");
		}
		return tag;
	}

	@Nullable
	private IEntry findEntryById(@NotNull VaultPayload payload, @NotNull String entryId) {
		for (IEntry entry : payload.getEntries()) {
			if (entryId.equals(entry.getId())) {
				return entry;
			}
		}
		return null;
	}

	private int findEntryIndex(@NotNull VaultPayload payload, @NotNull String entryId) {
		List<IEntry> entries = payload.getEntries();
		for (int index = 0; index < entries.size(); index++) {
			if (entryId.equals(entries.get(index).getId())) {
				return index;
			}
		}
		return -1;
	}

	private boolean matches(@NotNull IEntry entry, @NotNull String normalizedQuery) {
		if (entry.getTitle().toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
			return true;
		}
		ILogin login = entry.getLogin();
		if (login.getLoginName().toLowerCase(Locale.ROOT).contains(normalizedQuery)
				|| login.getUri().toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
			return true;
		}
		if (entry.getTag().getValue().toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
			return true;
		}
		for (INote note : login.getNotes()) {
			if (note.getDescription().toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
				return true;
			}
		}
		return false;
	}

	@NotNull
	private String requireText(@Nullable String value, @NotNull String fieldName) {
		if (value == null) {
			throw new IllegalArgumentException(fieldName + " cannot be null");
		}
		String normalizedValue = value.trim();
		if (normalizedValue.isEmpty()) {
			throw new IllegalArgumentException(fieldName + " cannot be blank");
		}
		return normalizedValue;
	}

	@NotNull
	private List<IVault> ensureVaultsInitialized(@NotNull IUser user) {
		List<IVault> vaults = user.getVaults();
		if (vaults == null) {
			vaults = new ArrayList<>();
			user.setVaults(vaults);
		}
		return vaults;
	}

	private boolean hasDefaultVault(@NotNull IUser user) {
		String defaultVaultId = user.getDefaultVaultId();
		return defaultVaultId != null && !defaultVaultId.trim().isEmpty();
	}

	private void persistUserIfPresent(@NotNull IUser user) {
		if (userRepository.findById(user.getId()) != null) {
			userRepository.update(user.getId(), user);
		}
	}

	@NotNull
	private IUser getUser(@NotNull String userId) {
		IUser user = userRepository.findById(userId);
		if (user == null) {
			throw new UserNotFoundException("The user with id " + userId + " does not exist");
		}
		return user;
	}
}
