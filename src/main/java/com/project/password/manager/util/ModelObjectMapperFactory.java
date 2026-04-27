package com.project.password.manager.util;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.password.manager.model.IEntry;
import com.project.password.manager.model.ILogin;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.INote;
import com.project.password.manager.model.ITag;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.database.file.storage.Metadata;
import com.project.password.manager.model.database.file.storage.Vault;
import com.project.password.manager.model.payload.Entry;
import com.project.password.manager.model.payload.Login;
import com.project.password.manager.model.payload.Note;
import com.project.password.manager.model.payload.Tag;

public final class ModelObjectMapperFactory {

	private ModelObjectMapperFactory() {
	}

	@NotNull
	public static ObjectMapper create() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		SimpleModule module = new SimpleModule();
		module.addAbstractTypeMapping(IVault.class, Vault.class);
		module.addAbstractTypeMapping(IEntry.class, Entry.class);
		module.addAbstractTypeMapping(ILogin.class, Login.class);
		module.addAbstractTypeMapping(INote.class, Note.class);
		module.addAbstractTypeMapping(ITag.class, Tag.class);
		module.addAbstractTypeMapping(IMetadata.class, Metadata.class);
		mapper.registerModule(module);
		return mapper;
	}

}