# Hytale Server Core API Dokümantasyonu

Bu belge, `com.hypixel.hytale.server.core` paketi ve alt paketlerindeki önemli sınıfların ve metotların detaylı açıklamalarını içerir. Bu bilgiler, sunucu yönetimi ve mod geliştirme süreçlerinde referans olması amacıyla hazırlanmıştır.

## İçindekiler
1. [Core (Ana Paket)](#core-ana-paket)
2. [Auth (Kimlik Doğrulama)](#auth-kimlik-dogrulama)
3. [Command (Komutlar)](#command-komutlar)
4. [Event (Olaylar)](#event-olaylar)
5. [Plugin (Eklentiler)](#plugin-eklentiler)
6. [Permissions (İzinler)](#permissions-izinler)
7. [Util (Araçlar)](#util-araclar)

---

## Core (Ana Paket)
**Paket:** `com.hypixel.hytale.server.core`

### `HytaleServer`
Sunucunun merkezi yönetim sınıfıdır (Singleton). Sunucu yaşam döngüsünü (başlatma, döngü, kapatma), yöneticileri (plugin, command, event) ve modülleri koordine eder.

**Önemli Public Metotlar:**
*   `static HytaleServer get()`: Çalışan sunucunun tekil örneğini (instance) döndürür.
*   `EventBus getEventBus()`: Sunucu genelindeki olayların yönetildiği `EventBus` nesnesini döndürür.
*   `PluginManager getPluginManager()`: Eklenti yöneticisini döndürür.
*   `CommandManager getCommandManager()`: Komut yöneticisini döndürür.
*   `HytaleServerConfig getConfig()`: Sunucu yapılandırma dosyasını (`hytale-server.json`) temsil eden nesneyi döndürür.
*   `void shutdownServer(ShutdownReason reason)`: Sunucuyu belirtilen bir sebeple (`ShutdownReason`) kapatır.
*   `String getServerName()`: Konfigürasyonda ayarlanan sunucu ismini döndürür.
*   `boolean isBooted()`: Sunucunun tamamen açılıp açılmadığını belirtir.
*   `boolean isShuttingDown()`: Sunucunun kapanma sürecinde olup olmadığını belirtir.
*   `Instant getBoot()`: Sunucunun başlatılma zamanını döndürür.

### `HytaleServerConfig`
Sunucu ayarlarını tutar ve yönetir. Değişiklikler diskteki dosyaya kaydedilebilir.

**Önemli Public Metotlar:**
*   `static HytaleServerConfig load()`: Varsayılan yoldan konfigürasyonu yükler.
*   `static CompletableFuture<Void> save(HytaleServerConfig config)`: Konfigürasyonu diske kaydeder.
*   `void setMotd(String motd)`: Sunucu listesinde görünen mesajı (MOTD) ayarlar.
*   `int getMaxPlayers()`: Maksimum oyuncu sayısını döndürür.
*   `void setMaxPlayers(int maxPlayers)`: Maksimum oyuncu sayısını ayarlar.
*   `Module getModule(String moduleName)`: İsmi verilen modülün (örn. "WorldModule") ayarlarını getirir.

---

## Auth (Kimlik Doğrulama)
**Paket:** `com.hypixel.hytale.server.core.auth`

### `ServerAuthManager`
Sunucudaki kimlik doğrulama işlemlerini yönetir. Oyuncuların geçerliliğini kontrol eder.

**Önemli Public Metotlar:**
*   `static ServerAuthManager getInstance()`: Yönetici örneğini döndürür.
*   `void initialize()`: Kimlik doğrulama anahtarlarını ve yapılarını hazırlar.
*   `AuthMode getAuthMode()`: Sunucunun kimlik doğrulama modunu (ONLINE, OFFLINE vb.) döndürür.

### `SessionServiceClient`
Hytale oturum servisleriyle (Backend API) iletişim kurar. Oyuncu oturumlarını doğrulamak ve profil bilgilerini çekmek için kullanılır.

**Önemli Public Metotlar:**
*   `CompletableFuture<String> requestAuthorizationGrantAsync(...)`: Yetkilendirme izni ister.
*   `CompletableFuture<String> exchangeAuthGrantForTokenAsync(...)`: İzni erişim anahtarına (token) çevirir.
*   `GameProfile[] getGameProfiles(String oauthAccessToken)`: Erişim anahtarı ile oyuncu profillerini getirir.
*   `GameSessionResponse createGameSession(...)`: Yeni bir oyun oturumu başlatır.

### `PlayerAuthentication`
Bir oyuncunun kimlik doğrulama bilgilerini (UUID, Kullanıcı adı) tutan veri sınıfıdır.

---

## Command (Komutlar)
**Paket:** `com.hypixel.hytale.server.core.command.system`

### `CommandManager`
Komut sisteminin kalbidir. Komutları kaydeder, ayrıştırır ve ilgili işlemciye yönlendirir.

**Önemli Public Metotlar:**
*   `void registerCommands()`: Varsayılan sistem komutlarını kaydeder.
*   `CommandRegistration register(AbstractCommand command)`: Yeni bir komut nesnesini sisteme kaydeder. Modlarda özel komut eklemek için kullanılır.
*   `CompletableFuture<Void> handleCommand(CommandSender sender, String commandString)`: Bir komut satırını, gönderen kişi adına çalıştırır.
*   `Map<String, AbstractCommand> getCommandRegistration()`: Kayıtlı tüm komutların listesini harita olarak döndürür.

### `CommandSender`
Komutu çalıştıran varlığı temsil eden arayüzdür. `Player` veya `ConsoleSender` olabilir.

**Metotlar:**
*   `void sendMessage(Message message)`: Göndericiye mesaj iletir.
*   `String getName()`: Göndericinin ismini döndürür.
*   `boolean hasPermission(String permission)`: Belirli bir yetkiye sahip olup olmadığını kontrol eder.

---

## Event (Olaylar)
**Paket:** `com.hypixel.hytale.event` (ve `com.hypixel.hytale.server.core.event`)

### `EventBus`
Olay tabanlı sistemin merkezidir. Olayların yayınlanmasını (dispatch) ve dinlenmesini (listen) sağlar.

**Önemli Public Metotlar:**
*   `EventRegistration register(Class<T> eventClass, Consumer<T> consumer)`: Belirli bir olay sınıfı için dinleyici (listener) kaydeder.
*   `IEventDispatcher dispatchFor(Class<T> eventClass)`: Bir olay sınıfı için yayınlayıcı döndürür.

### Örnek Olaylar (`server.core.event.events`)
*   `BootEvent`: Sunucu açıldığında tetiklenir.
*   `ShutdownEvent`: Sunucu kapanmaya başladığında tetiklenir.

---

## Plugin (Eklentiler)
**Paket:** `com.hypixel.hytale.server.core.plugin`

### `PluginManager`
Sunucuya yüklenen eklentileri (Mods/Plugins) yönetir.

**Önemli Public Metotlar:**
*   `List<PluginBase> getPlugins()`: Yüklü ve aktif tüm eklentileri listeler.
*   `PluginBase getPlugin(PluginIdentifier identifier)`: Belirli bir ID'ye sahip eklentiyi getirir.
*   `void setup()`: Eklentilerin kurulum aşamasını başlatır.
*   `void start()`: Eklentileri başlatır (enable eder).
*   `void shutdown()`: Eklentileri güvenli bir şekilde durdurur.

### `PluginBase`
Tüm eklentilerin ana sınıfıdır. Mod geliştirirken bu sınıf miras alınır (genellikle `JavaPlugin` üzerinden).

---

## Permissions (İzinler)
**Paket:** `com.hypixel.hytale.server.core.permissions`

### `HytalePermissions`
Sunucu içindeki standart izinlerin (permission nodes) tanımlandığı sabitleri içerir.

**Önemli Sabitler:**
*   `COMMAND_BASE`: Temel komut yetkisi (`hytale.command`).
*   `ASSET_EDITOR`: Varlık editörü yetkisi.
*   `FLY_CAM`: Fly kamera kullanım yetkisi.
*   `fromCommand(String name)`: Bir komut ismi için yetki stringi oluşturur (örn. `hytale.command.give`).

---

## Util (Araçlar)
**Paket:** `com.hypixel.hytale.server.core.util`

### `MessageUtil`
Mesajların biçimlendirilmesi, renklendirilmesi ve oyunculara iletilmesi için yardımcı metotlar içerir.

**Önemli Public Metotlar:**
*   `AttributedString toAnsiString(Message message)`: Mesaj nesnesini konsolda renkli görünecek şekilde ANSI formatına çevirir.
*   `formatText(String text, ...)`: Metin içindeki parametreleri ({0}, {name} gibi) değerleriyle değiştirir.

### `NotificationUtil` (İncelenen diğer araç)
Bildirim gönderme işlemlerini kolaylaştırır.
