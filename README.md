# Password Manager CLI

This project is a Java-based interactive password manager CLI. It supports user signup and login, vault management, encrypted entry storage, JWT-backed CLI sessions, and a command architecture built with Picocli, JLine, and Guice.

The current implementation is centered around an interactive shell started from `Application.main(...)`, with command handlers resolved at runtime through Guice.

## What The Application Does

The CLI currently supports:

- User signup and login
- JWT-backed authenticated CLI sessions
- Automatic default vault creation on signup
- Creation and listing of named vaults
- Creation, listing, retrieval, update, deletion, and search of vault entries
- Exact label lookup across one vault or all vaults
- Duplicate-label resolution ordered by latest update first
- File-based persistence by default, with partial SQL-oriented infrastructure present

## Tech Stack

- Java
- Maven
- Picocli for command parsing
- JLine for the interactive shell
- Guice for dependency injection and method interception
- Auth0 Java JWT for token creation and validation
- Argon2 for password hashing
- AES encryption for vault and entry payloads
- Jackson for payload serialization
- Caffeine for short-lived in-memory caches
- Log4j2 for logging

## Project Entry Point

The application starts in `src/main/java/com/project/password/manager/Application.java` and launches the interactive CLI:

```java
public static void main(String[] args) throws IOException {
    CLI.initCLI();
}
```

The CLI prompt is:

```text
password_manager>>
```

Type `exit` to leave the shell.

## Current CLI Command Surface

Top-level commands:

- `login`
- `signup`
- `logout`
- `whoami`
- `ping`
- `vault ...`
- `entry ...`
- `help`

### Auth Commands

Create a user and open a session:

```bash
signup <username> <password>
```

Authenticate and open a session:

```bash
login <username> <password>
```

Revoke the active session:

```bash
logout
```

Show the authenticated user:

```bash
whoami
```

Validate the current session:

```bash
ping
```

### Vault Commands

List all vaults for the authenticated user:

```bash
vault list
```

Create a named vault:

```bash
vault create <vaultName>
```

Show the default vault:

```bash
vault default
```

### Entry Commands

List entries in a vault. If `--vault` is omitted, the default vault is used:

```bash
entry list
entry list --vault "Work"
```

Create an entry:

```bash
entry create "github" "MySecret123!"
entry create --vault "Work" "github" "MySecret123!" --username "alice" --login-name "alice.dev" --url "https://github.com" --tag "dev,code" --note "main account"
```

Get entries by entry id or exact label:

```bash
entry get "github"
entry get --vault "Work" "github"
entry get --vault "Work" "<entry-id>"
```

Behavior of `entry get`:

- If the reference matches an entry id in the selected vault, that exact entry is returned.
- If the reference matches one or more labels exactly, all matches are returned.
- If `--vault` is omitted, lookup spans all vaults owned by the authenticated user.
- When multiple entries share the same label, results are ordered by newest `updatedAtEpochMs` first.

Update an entry:

```bash
entry update --vault "Work" "<entry-id>" "github" "NewSecret123!" --username "alice" --login-name "alice.dev" --url "https://github.com" --tag "dev,critical" --note "rotated on 2026-04-22"
```

Delete an entry:

```bash
entry delete --vault "Work" "<entry-id>"
```

Search entries in a vault:

```bash
entry search "github"
entry search --vault "Work" "alice github"
```

Search behavior:

- Search is vault-scoped.
- If `--vault` is omitted, the default vault is used.
- Search tokenizes labels, usernames, login names, URLs, tags, and notes.
- Results are ordered by latest update first.

## Authentication And Session Model

Authentication uses two layers:

- Password hashing with Argon2
- JWT access tokens persisted per user

Current flow:

1. `signup` creates a new user.
2. A default vault named `Default` is created automatically.
3. A JWT is issued and persisted.
4. The CLI session stores the authenticated `userId` and raw token in memory.

Protected command handlers are annotated with `@RequireAuthorization`.
Guice applies `TokenAuthorizationInterceptor`, which delegates to `TokenVerifier`.

`TokenVerifier` checks:

- a CLI session exists
- the user still exists
- the persisted token matches the in-memory session token
- the JWT still verifies and is not expired

If verification fails, the CLI session is cleared and the command fails with an unauthorized session error.

## Security Model

The current codebase uses the following security approach:

- User passwords are hashed using Argon2
- Entry payloads are encrypted before persistence
- Vault payloads are encrypted before persistence
- JWT tokens are used to authenticate CLI sessions
- Handler-level authorization is enforced with Guice interception rather than manual checks in each command path

Important implementation details:

- Expired JWTs invalidate the active CLI session
- Re-login after token expiry is supported by token re-issuance logic in `TokenService`
- Vault ownership is checked before entry operations
- Vault references can be either vault id or vault name

## Storage Model

The application is designed around repository interfaces and a factory that selects persistence based on configuration.

### Default Mode

By default, storage falls back to the local file system.

`DataRepositoryFactory` currently uses file-backed repositories when:

- `app.database.enabled=false`, or
- no supported database-backed implementation is available for the requested repository type

Current file-backed repositories include:

- users
- tokens
- vaults
- entries

### SQL And NoSQL Status

The project contains SQL and NoSQL-oriented infrastructure, but entry persistence is not fully implemented there yet.

Current state:

- User entity wiring exists for SQL and NoSQL paths
- General repository factory support exists
- Entry repository support for SQL and NoSQL currently throws `UnsupportedOperationException`

For practical use today, assume file-backed storage is the supported path.

## Workspace And Configuration

The application reads configuration from a file named:

```text
password-manager.properties
```

This file is expected under a workspace directory in the OS user home:

```text
<user-home>/password-manager/password-manager.properties
```

On Windows this typically resolves to:

```text
C:\Users\<your-user>\password-manager\password-manager.properties
```

### Important Current Behavior

The project contains `Workspace.configureWorkSpace()` to create the workspace and a starter property file, but the current `Application.main(...)` does not call it automatically.

That means you should ensure the workspace directory and property file already exist before running the CLI.

### Minimal Recommended Properties

For local file-backed usage with HMAC JWTs, create `password-manager.properties` with values like these:

```properties
app.name=password-manager-cli
app.version=1.0.0

app.database.enabled=false
app.database.type=sql
app.database.vendor=postgres
app.database.host=localhost
app.database.port=5432
app.database.name=password_manager
app.database.username=username
app.database.password=password
app.database.ddl.mode=update

app.crypto.argon2.memory.kb=65536
app.crypto.argon2.iterations=3
app.crypto.argon2.parallelism=1
app.crypto.argon2.salt.length=16
app.crypto.argon2.hash.length=32

app.jwt.algorithm=HS256
app.jwt.issuer=password-manager-cli
app.jwt.access.expiration.ms=900000
app.jwt.refresh.expiration.ms=604800000
app.jwt.secret=replace-with-a-long-random-secret

app.aes.transformation=AES/GCM/NoPadding
app.aes.key.size=128
app.aes.tag.length=128
app.aes.iv.length=12

app.salt.iterations=65536
app.salt.key.size=256
```

### JWT Algorithm Configuration

Supported algorithms include:

- `HS256`
- `HS384`
- `HS512`
- `RS256`
- `RS384`
- `RS512`
- `ES256`
- `ES384`
- `ES512`

Configuration requirements:

- For `HS*` algorithms, `app.jwt.secret` must be set.
- For `RS*` and `ES*` algorithms, key files must exist under:

```text
<user-home>/password-manager/authentication/
```

and the following properties must point to the file names inside that folder:

- `app.jwt.private.key.path`
- `app.jwt.public.key.path`

## Output Shape

The CLI prints plain text views.

Examples of current output fields include:

- user id
- vault id
- vault name
- entry id
- label
- username
- login name
- URL
- tags
- notes
- creation time
- update time

When listing multiple vaults or entries, each item is printed as a separate block.

## Architecture Overview

### Command Layer

Commands live under `src/main/java/com/project/password/manager/cli/commands`.

The CLI uses Picocli annotations for parsing and a root `BaseCommand` with subcommands.

### Handler Layer

Handlers live under `src/main/java/com/project/password/manager/cli/handlers`.

Each command delegates to a corresponding handler. This keeps parsing and business behavior separate.

Key patterns in the current implementation:

- `DelegatingCliCommand<TRequest, THandler>` builds a request object from CLI input
- handlers implement `CommandHandler<TRequest>`
- vault-scoped handlers resolve vault names or ids through `VaultService`
- authorized handlers rely on middleware instead of duplicating token checks

### Runtime Layer

Runtime support lives under `src/main/java/com/project/password/manager/cli/runtime`.

It is responsible for:

- binding handlers to commands
- invoking handlers
- managing CLI session state
- formatting command exceptions into user-facing failures

### Dependency Injection

Guice is the composition root.

Current Guice responsibilities:

- service construction
- repository provisioning
- interceptor registration for authorization
- CLI session and output singletons
- handler registry provisioning

Command-handler registration is currently provided through Guice as a singleton `CommandHandlerRegistry`.

### Service Layer

The primary business services are:

- `AuthService`
- `UserService`
- `TokenService`
- `VaultService`
- `EntryService`

Responsibilities:

- `AuthService`: signup and login orchestration
- `TokenService`: JWT issue, verify, cache, persist, revoke
- `VaultService`: vault creation, default vault resolution, vault ownership checks
- `EntryService`: entry CRUD, exact reference lookup, vault-scoped search, in-memory search index maintenance

## Entry Retrieval Semantics

This is one of the more important current behaviors.

`entry get` supports both id-based and label-based retrieval:

- If the argument is an existing entry id in the selected vault, it returns that entry.
- Otherwise it looks for exact label matches.
- If `--vault` is omitted, lookup spans all vaults owned by the current user.
- When multiple entries share a label, they are sorted by `updatedAtEpochMs` descending, then by `createdAtEpochMs`, then by id.

This makes retrieval practical even when the user remembers a label but not the vault.

## Search Semantics

Search is intentionally different from `entry get`:

- `entry get` is exact reference lookup by id or exact label
- `entry search` is token-based search inside a single vault

The search index currently tokenizes:

- label
- username
- login name
- URL
- tags
- notes

## Build And Run

### Compile

```bash
mvn -DskipTests compile
```

### Package

```bash
mvn -DskipTests package
```

### Run

The most reliable current approach is to run `Application.java` from your IDE after the workspace and properties file are present.

Main class:

```text
com.project.password.manager.Application
```

If you prefer Maven-based launching, you can add an exec plugin or run the class using your IDE's Java launcher configuration.

## Testing

Current automated test coverage is minimal.

At the moment the repository only contains:

- a basic `AppTest` smoke test using JUnit 3 style scaffolding

Run tests with:

```bash
mvn test
```

For real validation today, manual CLI testing is important.

### Suggested Manual Test Flow

```bash
signup demo-user Demo@123
whoami
vault list
vault create Work
vault list

entry create "gmail" "Secret123!"
entry create --vault "Work" "github" "WorkSecret123!" --username "alice" --login-name "alice.dev" --url "https://github.com" --tag "dev,code" --note "primary work account"

entry list
entry list --vault "Work"
entry search --vault "Work" "github alice"
entry get "github"
```

To validate duplicate-label ordering:

```bash
entry create --vault "Work" "github" "OlderSecret"
entry list --vault "Work"
entry update --vault "Work" "<entry-id>" "github" "NewerSecret"
entry get "github"
```

Expected behavior:

- all exact-label matches are returned
- the most recently updated one appears first

## Known Limitations

- `Application.main(...)` does not currently bootstrap the workspace automatically.
- The root CLI uses an interactive shell; non-interactive automation is not the primary path right now.
- SQL and NoSQL entry repositories are not implemented yet.
- Automated test coverage is still very thin.
- The project relies on a user-home workspace and external property file rather than a resource-bundled config.

## Repository Layout

High-level structure:

```text
src/main/java/com/project/password/manager/
  Application.java
  argon/
  auth/
  cli/
    commands/
    handlers/
    runtime/
  configuration/
  database/
  encryption/
  exceptions/
  guice/
  middleware/
  model/
  service/
  util/

src/main/resources/
  log4j2.xml

src/test/java/com/project/password/manager/
  AppTest.java
```

## Recommended Next Improvements

- Call `Workspace.configureWorkSpace()` during startup or document a bootstrap command.
- Add a Maven plugin or script for straightforward CLI launching outside the IDE.
- Add integration tests for signup, login, vault creation, and entry CRUD flows.
- Add dedicated SQL and NoSQL entry repository implementations.
- Add a non-interactive mode for scripting and CI use.
