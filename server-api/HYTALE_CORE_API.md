# Hytale Server Core API Documentation

This document contains detailed descriptions of important classes and methods in the `com.hypixel.hytale.server.core` package and its sub-packages. This information is prepared to serve as a reference for server management and mod development processes.

## Table of Contents
1. [Core (Main Package)](#core-main-package)
2. [Auth (Authentication)](#auth-authentication)
3. [Command](#command)
4. [Event](#event)
5. [Plugin](#plugin)
6. [Permissions](#permissions)
7. [Util (Tools)](#util-tools)

---

## Core (Main Package)
**Package:** `com.hypixel.hytale.server.core`

### `HytaleServer`
The central management class of the server (Singleton). It coordinates the server lifecycle (startup, loop, shutdown), managers (plugin, command, event), and modules.

**Important Public Methods:**
*   `static HytaleServer get()`: Returns the single instance of the running server.
*   `EventBus getEventBus()`: Returns the `EventBus` object where server-wide events are managed.
*   `PluginManager getPluginManager()`: Returns the plugin manager.
*   `CommandManager getCommandManager()`: Returns the command manager.
*   `HytaleServerConfig getConfig()`: Returns the object representing the server configuration file (`hytale-server.json`).
*   `void shutdownServer(ShutdownReason reason)`: Shuts down the server for the specified reason (`ShutdownReason`).
*   `String getServerName()`: Returns the server name set in the configuration.
*   `boolean isBooted()`: Indicates whether the server has fully started.
*   `boolean isShuttingDown()`: Indicates whether the server is in the process of shutting down.
*   `Instant getBoot()`: Returns the time when the server was started.

### `HytaleServerConfig`
Holds and manages server settings. Changes can be saved to the file on disk.

**Important Public Methods:**
*   `static HytaleServerConfig load()`: Loads the configuration from the default path.
*   `static CompletableFuture<Void> save(HytaleServerConfig config)`: Saves the configuration to disk.
*   `void setMotd(String motd)`: Sets the message of the day (MOTD) visible in the server list.
*   `int getMaxPlayers()`: Returns the maximum number of players.
*   `void setMaxPlayers(int maxPlayers)`: Sets the maximum number of players.
*   `Module getModule(String moduleName)`: Retrieves the settings of the named module (e.g., "WorldModule").

---

## Auth (Authentication)
**Package:** `com.hypixel.hytale.server.core.auth`

### `ServerAuthManager`
Manages authentication processes on the server. Checks the validity of players.

**Important Public Methods:**
*   `static ServerAuthManager getInstance()`: Returns the manager instance.
*   `void initialize()`: Prepares authentication keys and structures.
*   `AuthMode getAuthMode()`: Returns the server's authentication mode (ONLINE, OFFLINE, etc.).

### `SessionServiceClient`
Communicates with Hytale session services (Backend API). Used to verify player sessions and retrieve profile information.

**Important Public Methods:**
*   `CompletableFuture<String> requestAuthorizationGrantAsync(...)`: Requests authorization grant.
*   `CompletableFuture<String> exchangeAuthGrantForTokenAsync(...)`: Exchanges the grant for an access token.
*   `GameProfile[] getGameProfiles(String oauthAccessToken)`: Retrieves player profiles using the access token.
*   `GameSessionResponse createGameSession(...)`: Starts a new game session.

### `PlayerAuthentication`
A data class holding a player's authentication information (UUID, Username).

---

## Command
**Package:** `com.hypixel.hytale.server.core.command.system`

### `CommandManager`
The heart of the command system. It registers, parses, and routes commands to the relevant processor.

**Important Public Methods:**
*   `void registerCommands()`: Registers default system commands.
*   `CommandRegistration register(AbstractCommand command)`: Registers a new command object to the system. Used to add custom commands in mods.
*   `CompletableFuture<Void> handleCommand(CommandSender sender, String commandString)`: Executes a command line on behalf of the sender.
*   `Map<String, AbstractCommand> getCommandRegistration()`: Returns a map of all registered commands.

### `CommandSender`
An interface representing the entity executing the command. Can be `Player` or `ConsoleSender`.

**Methods:**
*   `void sendMessage(Message message)`: Sends a message to the sender.
*   `String getName()`: Returns the name of the sender.
*   `boolean hasPermission(String permission)`: Checks if the sender has specific permission.

---

## Event
**Package:** `com.hypixel.hytale.event` (and `com.hypixel.hytale.server.core.event`)

### `EventBus`
The center of the event-based system. Enables dispatching and listening to events.

**Important Public Methods:**
*   `EventRegistration register(Class<T> eventClass, Consumer<T> consumer)`: Registers a listener for a specific event class.
*   `IEventDispatcher dispatchFor(Class<T> eventClass)`: Returns a publisher for an event class.

### Example Events (`server.core.event.events`)
*   `BootEvent`: Triggered when the server starts.
*   `ShutdownEvent`: Triggered when the server begins to shut down.

---

## Plugin
**Package:** `com.hypixel.hytale.server.core.plugin`

### `PluginManager`
Manages plugins (Mods/Plugins) loaded on the server.

**Important Public Methods:**
*   `List<PluginBase> getPlugins()`: Lists all loaded and active plugins.
*   `PluginBase getPlugin(PluginIdentifier identifier)`: Retrieves the plugin with the specified ID.
*   `void setup()`: Starts the setup phase of plugins.
*   `void start()`: Starts (enables) the plugins.
*   `void shutdown()`: Safely stops the plugins.

### `PluginBase`
The base class for all plugins. This class is inherited when developing mods (usually via `JavaPlugin`).

---

## Permissions
**Package:** `com.hypixel.hytale.server.core.permissions`

### `HytalePermissions`
Contains constants defining standard permissions (permission nodes) within the server.

**Important Constants:**
*   `COMMAND_BASE`: Basic command permission (`hytale.command`).
*   `ASSET_EDITOR`: Asset editor permission.
*   `FLY_CAM`: Fly camera usage permission.
*   `fromCommand(String name)`: Creates a permission string for a command name (e.g., `hytale.command.give`).

---

## Util (Tools)
**Package:** `com.hypixel.hytale.server.core.util`

### `MessageUtil`
Contains helper methods for formatting, coloring, and sending messages to players.

**Important Public Methods:**
*   `AttributedString toAnsiString(Message message)`: Converts a message object to ANSI format to appear colored in the console.
*   `formatText(String text, ...)`: Replaces parameters (like {0}, {name}) in the text with their values.

### `NotificationUtil` (Other tool examined)
Simplifies sending notifications.
