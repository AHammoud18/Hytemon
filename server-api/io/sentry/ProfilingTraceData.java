/*     */ package io.sentry;
/*     */ import io.sentry.profilemeasurements.ProfileMeasurement;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class ProfilingTraceData implements JsonUnknown, JsonSerializable {
/*     */   private static final String DEFAULT_ENVIRONMENT = "production";
/*     */   @Internal
/*     */   public static final String TRUNCATION_REASON_NORMAL = "normal";
/*     */   @Internal
/*     */   public static final String TRUNCATION_REASON_TIMEOUT = "timeout";
/*     */   @Internal
/*     */   public static final String TRUNCATION_REASON_BACKGROUNDED = "backgrounded";
/*     */   @NotNull
/*     */   private final File traceFile;
/*     */   @NotNull
/*     */   private final Callable<List<Integer>> deviceCpuFrequenciesReader;
/*     */   private int androidApiLevel;
/*     */   @NotNull
/*     */   private String deviceLocale;
/*     */   @NotNull
/*     */   private String deviceManufacturer;
/*     */   @NotNull
/*     */   private String deviceModel;
/*     */   @NotNull
/*     */   private String deviceOsBuildNumber;
/*     */   @NotNull
/*     */   private String deviceOsName;
/*     */   @NotNull
/*     */   private String deviceOsVersion;
/*     */   private boolean deviceIsEmulator;
/*     */   @NotNull
/*     */   private String cpuArchitecture;
/*     */   @NotNull
/*  46 */   private List<Integer> deviceCpuFrequencies = new ArrayList<>(); @NotNull
/*     */   private String devicePhysicalMemoryBytes; @NotNull
/*     */   private String platform; @NotNull
/*     */   private String buildId; @NotNull
/*     */   private List<ProfilingTransactionData> transactions; @NotNull
/*     */   private String transactionName; @NotNull
/*     */   private String durationNs;
/*     */   @NotNull
/*     */   private String versionCode;
/*     */   @NotNull
/*     */   private String release;
/*     */   @NotNull
/*     */   private String transactionId;
/*     */   @NotNull
/*     */   private String traceId;
/*     */   @NotNull
/*     */   private String profileId;
/*     */   @NotNull
/*     */   private String environment;
/*     */   @NotNull
/*     */   private String truncationReason;
/*     */   @NotNull
/*     */   private Date timestamp;
/*     */   @NotNull
/*     */   private final Map<String, ProfileMeasurement> measurementsMap;
/*     */   @Nullable
/*  72 */   private String sampledProfile = null; @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   private ProfilingTraceData() {
/*  75 */     this(new File("dummy"), NoOpTransaction.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public ProfilingTraceData(@NotNull File traceFile, @NotNull ITransaction transaction) {
/*  80 */     this(traceFile, 
/*     */         
/*  82 */         DateUtils.getCurrentDateTime(), new ArrayList<>(), transaction
/*     */         
/*  84 */         .getName(), transaction
/*  85 */         .getEventId().toString(), transaction
/*  86 */         .getSpanContext().getTraceId().toString(), "0", 0, "", () -> new ArrayList(), null, null, null, null, null, null, null, null, "normal", new HashMap<>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProfilingTraceData(@NotNull File traceFile, @NotNull Date profileStartTimestamp, @NotNull List<ProfilingTransactionData> transactions, @NotNull String transactionName, @NotNull String transactionId, @NotNull String traceId, @NotNull String durationNanos, int sdkInt, @NotNull String cpuArchitecture, @NotNull Callable<List<Integer>> deviceCpuFrequenciesReader, @Nullable String deviceManufacturer, @Nullable String deviceModel, @Nullable String deviceOsVersion, @Nullable Boolean deviceIsEmulator, @Nullable String devicePhysicalMemoryBytes, @Nullable String buildId, @Nullable String release, @Nullable String environment, @NotNull String truncationReason, @NotNull Map<String, ProfileMeasurement> measurementsMap) {
/* 125 */     this.traceFile = traceFile;
/* 126 */     this.timestamp = profileStartTimestamp;
/* 127 */     this.cpuArchitecture = cpuArchitecture;
/* 128 */     this.deviceCpuFrequenciesReader = deviceCpuFrequenciesReader;
/*     */ 
/*     */     
/* 131 */     this.androidApiLevel = sdkInt;
/* 132 */     this.deviceLocale = Locale.getDefault().toString();
/* 133 */     this.deviceManufacturer = (deviceManufacturer != null) ? deviceManufacturer : "";
/* 134 */     this.deviceModel = (deviceModel != null) ? deviceModel : "";
/* 135 */     this.deviceOsVersion = (deviceOsVersion != null) ? deviceOsVersion : "";
/* 136 */     this.deviceIsEmulator = (deviceIsEmulator != null) ? deviceIsEmulator.booleanValue() : false;
/* 137 */     this
/* 138 */       .devicePhysicalMemoryBytes = (devicePhysicalMemoryBytes != null) ? devicePhysicalMemoryBytes : "0";
/* 139 */     this.deviceOsBuildNumber = "";
/* 140 */     this.deviceOsName = "android";
/* 141 */     this.platform = "android";
/* 142 */     this.buildId = (buildId != null) ? buildId : "";
/*     */ 
/*     */     
/* 145 */     this.transactions = transactions;
/* 146 */     this.transactionName = transactionName.isEmpty() ? "unknown" : transactionName;
/* 147 */     this.durationNs = durationNanos;
/*     */ 
/*     */     
/* 150 */     this.versionCode = "";
/* 151 */     this.release = (release != null) ? release : "";
/*     */ 
/*     */     
/* 154 */     this.transactionId = transactionId;
/* 155 */     this.traceId = traceId;
/* 156 */     this.profileId = SentryUUID.generateSentryId();
/* 157 */     this.environment = (environment != null) ? environment : "production";
/* 158 */     this.truncationReason = truncationReason;
/* 159 */     if (!isTruncationReasonValid()) {
/* 160 */       this.truncationReason = "normal";
/*     */     }
/* 162 */     this.measurementsMap = measurementsMap;
/*     */   }
/*     */   
/*     */   private boolean isTruncationReasonValid() {
/* 166 */     return (this.truncationReason.equals("normal") || this.truncationReason
/* 167 */       .equals("timeout") || this.truncationReason
/* 168 */       .equals("backgrounded"));
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public File getTraceFile() {
/* 174 */     return this.traceFile;
/*     */   }
/*     */   
/*     */   public int getAndroidApiLevel() {
/* 178 */     return this.androidApiLevel;
/*     */   }
/*     */   @NotNull
/*     */   public String getCpuArchitecture() {
/* 182 */     return this.cpuArchitecture;
/*     */   }
/*     */   @NotNull
/*     */   public String getDeviceLocale() {
/* 186 */     return this.deviceLocale;
/*     */   }
/*     */   @NotNull
/*     */   public String getDeviceManufacturer() {
/* 190 */     return this.deviceManufacturer;
/*     */   }
/*     */   @NotNull
/*     */   public String getDeviceModel() {
/* 194 */     return this.deviceModel;
/*     */   }
/*     */   @NotNull
/*     */   public String getDeviceOsBuildNumber() {
/* 198 */     return this.deviceOsBuildNumber;
/*     */   }
/*     */   @NotNull
/*     */   public String getDeviceOsName() {
/* 202 */     return this.deviceOsName;
/*     */   }
/*     */   @NotNull
/*     */   public String getDeviceOsVersion() {
/* 206 */     return this.deviceOsVersion;
/*     */   }
/*     */   
/*     */   public boolean isDeviceIsEmulator() {
/* 210 */     return this.deviceIsEmulator;
/*     */   }
/*     */   @NotNull
/*     */   public String getPlatform() {
/* 214 */     return this.platform;
/*     */   }
/*     */   @NotNull
/*     */   public String getBuildId() {
/* 218 */     return this.buildId;
/*     */   }
/*     */   @NotNull
/*     */   public String getTransactionName() {
/* 222 */     return this.transactionName;
/*     */   }
/*     */   @NotNull
/*     */   public String getRelease() {
/* 226 */     return this.release;
/*     */   }
/*     */   @NotNull
/*     */   public String getTransactionId() {
/* 230 */     return this.transactionId;
/*     */   }
/*     */   @NotNull
/*     */   public List<ProfilingTransactionData> getTransactions() {
/* 234 */     return this.transactions;
/*     */   }
/*     */   @NotNull
/*     */   public String getTraceId() {
/* 238 */     return this.traceId;
/*     */   }
/*     */   @NotNull
/*     */   public String getProfileId() {
/* 242 */     return this.profileId;
/*     */   }
/*     */   @NotNull
/*     */   public String getEnvironment() {
/* 246 */     return this.environment;
/*     */   }
/*     */   @Nullable
/*     */   public String getSampledProfile() {
/* 250 */     return this.sampledProfile;
/*     */   }
/*     */   @NotNull
/*     */   public String getDurationNs() {
/* 254 */     return this.durationNs;
/*     */   }
/*     */   @NotNull
/*     */   public List<Integer> getDeviceCpuFrequencies() {
/* 258 */     return this.deviceCpuFrequencies;
/*     */   }
/*     */   @NotNull
/*     */   public String getDevicePhysicalMemoryBytes() {
/* 262 */     return this.devicePhysicalMemoryBytes;
/*     */   }
/*     */   @NotNull
/*     */   public String getTruncationReason() {
/* 266 */     return this.truncationReason;
/*     */   }
/*     */   @NotNull
/*     */   public Date getTimestamp() {
/* 270 */     return this.timestamp;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, ProfileMeasurement> getMeasurementsMap() {
/* 274 */     return this.measurementsMap;
/*     */   }
/*     */   
/*     */   public void setAndroidApiLevel(int androidApiLevel) {
/* 278 */     this.androidApiLevel = androidApiLevel;
/*     */   }
/*     */   
/*     */   public void setCpuArchitecture(@NotNull String cpuArchitecture) {
/* 282 */     this.cpuArchitecture = cpuArchitecture;
/*     */   }
/*     */   
/*     */   public void setDeviceLocale(@NotNull String deviceLocale) {
/* 286 */     this.deviceLocale = deviceLocale;
/*     */   }
/*     */   
/*     */   public void setDeviceManufacturer(@NotNull String deviceManufacturer) {
/* 290 */     this.deviceManufacturer = deviceManufacturer;
/*     */   }
/*     */   
/*     */   public void setDeviceModel(@NotNull String deviceModel) {
/* 294 */     this.deviceModel = deviceModel;
/*     */   }
/*     */   
/*     */   public void setDeviceOsBuildNumber(@NotNull String deviceOsBuildNumber) {
/* 298 */     this.deviceOsBuildNumber = deviceOsBuildNumber;
/*     */   }
/*     */   
/*     */   public void setDeviceOsVersion(@NotNull String deviceOsVersion) {
/* 302 */     this.deviceOsVersion = deviceOsVersion;
/*     */   }
/*     */   
/*     */   public void setDeviceIsEmulator(boolean deviceIsEmulator) {
/* 306 */     this.deviceIsEmulator = deviceIsEmulator;
/*     */   }
/*     */   
/*     */   public void setDeviceCpuFrequencies(@NotNull List<Integer> deviceCpuFrequencies) {
/* 310 */     this.deviceCpuFrequencies = deviceCpuFrequencies;
/*     */   }
/*     */   
/*     */   public void setDevicePhysicalMemoryBytes(@NotNull String devicePhysicalMemoryBytes) {
/* 314 */     this.devicePhysicalMemoryBytes = devicePhysicalMemoryBytes;
/*     */   }
/*     */   
/*     */   public void setTimestamp(@NotNull Date timestamp) {
/* 318 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   public void setTruncationReason(@NotNull String truncationReason) {
/* 322 */     this.truncationReason = truncationReason;
/*     */   }
/*     */   
/*     */   public void setTransactions(@NotNull List<ProfilingTransactionData> transactions) {
/* 326 */     this.transactions = transactions;
/*     */   }
/*     */   
/*     */   public void setBuildId(@NotNull String buildId) {
/* 330 */     this.buildId = buildId;
/*     */   }
/*     */   
/*     */   public void setTransactionName(@NotNull String transactionName) {
/* 334 */     this.transactionName = transactionName;
/*     */   }
/*     */   
/*     */   public void setDurationNs(@NotNull String durationNs) {
/* 338 */     this.durationNs = durationNs;
/*     */   }
/*     */   
/*     */   public void setRelease(@NotNull String release) {
/* 342 */     this.release = release;
/*     */   }
/*     */   
/*     */   public void setTransactionId(@NotNull String transactionId) {
/* 346 */     this.transactionId = transactionId;
/*     */   }
/*     */   
/*     */   public void setTraceId(@NotNull String traceId) {
/* 350 */     this.traceId = traceId;
/*     */   }
/*     */   
/*     */   public void setProfileId(@NotNull String profileId) {
/* 354 */     this.profileId = profileId;
/*     */   }
/*     */   
/*     */   public void setEnvironment(@NotNull String environment) {
/* 358 */     this.environment = environment;
/*     */   }
/*     */   
/*     */   public void setSampledProfile(@Nullable String sampledProfile) {
/* 362 */     this.sampledProfile = sampledProfile;
/*     */   }
/*     */   
/*     */   public void readDeviceCpuFrequencies() {
/*     */     try {
/* 367 */       this.deviceCpuFrequencies = this.deviceCpuFrequenciesReader.call();
/* 368 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String ANDROID_API_LEVEL = "android_api_level";
/*     */     
/*     */     public static final String DEVICE_LOCALE = "device_locale";
/*     */     
/*     */     public static final String DEVICE_MANUFACTURER = "device_manufacturer";
/*     */     
/*     */     public static final String DEVICE_MODEL = "device_model";
/*     */     
/*     */     public static final String DEVICE_OS_BUILD_NUMBER = "device_os_build_number";
/*     */     public static final String DEVICE_OS_NAME = "device_os_name";
/*     */     public static final String DEVICE_OS_VERSION = "device_os_version";
/*     */     public static final String DEVICE_IS_EMULATOR = "device_is_emulator";
/*     */     public static final String ARCHITECTURE = "architecture";
/*     */     public static final String DEVICE_CPU_FREQUENCIES = "device_cpu_frequencies";
/*     */     public static final String DEVICE_PHYSICAL_MEMORY_BYTES = "device_physical_memory_bytes";
/*     */     public static final String PLATFORM = "platform";
/*     */     public static final String BUILD_ID = "build_id";
/*     */     public static final String TRANSACTION_NAME = "transaction_name";
/*     */     public static final String DURATION_NS = "duration_ns";
/*     */     public static final String RELEASE = "version_name";
/*     */     public static final String VERSION_CODE = "version_code";
/*     */     public static final String TRANSACTION_LIST = "transactions";
/*     */     public static final String TRANSACTION_ID = "transaction_id";
/*     */     public static final String TRACE_ID = "trace_id";
/*     */     public static final String PROFILE_ID = "profile_id";
/*     */     public static final String ENVIRONMENT = "environment";
/*     */     public static final String SAMPLED_PROFILE = "sampled_profile";
/*     */     public static final String TRUNCATION_REASON = "truncation_reason";
/*     */     public static final String MEASUREMENTS = "measurements";
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 407 */     writer.beginObject();
/* 408 */     writer.name("android_api_level").value(logger, Integer.valueOf(this.androidApiLevel));
/* 409 */     writer.name("device_locale").value(logger, this.deviceLocale);
/* 410 */     writer.name("device_manufacturer").value(this.deviceManufacturer);
/* 411 */     writer.name("device_model").value(this.deviceModel);
/* 412 */     writer.name("device_os_build_number").value(this.deviceOsBuildNumber);
/* 413 */     writer.name("device_os_name").value(this.deviceOsName);
/* 414 */     writer.name("device_os_version").value(this.deviceOsVersion);
/* 415 */     writer.name("device_is_emulator").value(this.deviceIsEmulator);
/* 416 */     writer.name("architecture").value(logger, this.cpuArchitecture);
/*     */     
/* 418 */     writer.name("device_cpu_frequencies").value(logger, this.deviceCpuFrequencies);
/* 419 */     writer.name("device_physical_memory_bytes").value(this.devicePhysicalMemoryBytes);
/* 420 */     writer.name("platform").value(this.platform);
/* 421 */     writer.name("build_id").value(this.buildId);
/* 422 */     writer.name("transaction_name").value(this.transactionName);
/* 423 */     writer.name("duration_ns").value(this.durationNs);
/* 424 */     writer.name("version_name").value(this.release);
/* 425 */     writer.name("version_code").value(this.versionCode);
/* 426 */     if (!this.transactions.isEmpty()) {
/* 427 */       writer.name("transactions").value(logger, this.transactions);
/*     */     }
/* 429 */     writer.name("transaction_id").value(this.transactionId);
/* 430 */     writer.name("trace_id").value(this.traceId);
/* 431 */     writer.name("profile_id").value(this.profileId);
/* 432 */     writer.name("environment").value(this.environment);
/* 433 */     writer.name("truncation_reason").value(this.truncationReason);
/* 434 */     if (this.sampledProfile != null) {
/* 435 */       writer.name("sampled_profile").value(this.sampledProfile);
/*     */     }
/*     */ 
/*     */     
/* 439 */     String prevIndent = writer.getIndent();
/* 440 */     writer.setIndent("");
/* 441 */     writer.name("measurements").value(logger, this.measurementsMap);
/* 442 */     writer.setIndent(prevIndent);
/* 443 */     writer.name("timestamp").value(logger, this.timestamp);
/* 444 */     if (this.unknown != null) {
/* 445 */       for (String key : this.unknown.keySet()) {
/* 446 */         Object value = this.unknown.get(key);
/* 447 */         writer.name(key);
/* 448 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 451 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 457 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 462 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ProfilingTraceData>
/*     */   {
/*     */     @NotNull
/*     */     public ProfilingTraceData deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 471 */       reader.beginObject();
/* 472 */       ProfilingTraceData data = new ProfilingTraceData();
/* 473 */       Map<String, Object> unknown = null;
/*     */       
/* 475 */       while (reader.peek() == JsonToken.NAME) {
/* 476 */         Integer apiLevel; String deviceLocale, deviceManufacturer, deviceModel, deviceOsBuildNumber, deviceOsName, deviceOsVersion; Boolean deviceIsEmulator; String cpuArchitecture; List<Integer> deviceCpuFrequencies; String devicePhysicalMemoryBytes, platform, buildId, transactionName, durationNs, versionCode, versionName; List<ProfilingTransactionData> transactions; String transactionId, traceId, profileId, environment, truncationReason; Map<String, ProfileMeasurement> measurements; Date timestamp; String sampledProfile, nextName = reader.nextName();
/* 477 */         switch (nextName) {
/*     */           case "android_api_level":
/* 479 */             apiLevel = reader.nextIntegerOrNull();
/* 480 */             if (apiLevel != null) {
/* 481 */               data.androidApiLevel = apiLevel.intValue();
/*     */             }
/*     */             continue;
/*     */           case "device_locale":
/* 485 */             deviceLocale = reader.nextStringOrNull();
/* 486 */             if (deviceLocale != null) {
/* 487 */               data.deviceLocale = deviceLocale;
/*     */             }
/*     */             continue;
/*     */           case "device_manufacturer":
/* 491 */             deviceManufacturer = reader.nextStringOrNull();
/* 492 */             if (deviceManufacturer != null) {
/* 493 */               data.deviceManufacturer = deviceManufacturer;
/*     */             }
/*     */             continue;
/*     */           case "device_model":
/* 497 */             deviceModel = reader.nextStringOrNull();
/* 498 */             if (deviceModel != null) {
/* 499 */               data.deviceModel = deviceModel;
/*     */             }
/*     */             continue;
/*     */           case "device_os_build_number":
/* 503 */             deviceOsBuildNumber = reader.nextStringOrNull();
/* 504 */             if (deviceOsBuildNumber != null) {
/* 505 */               data.deviceOsBuildNumber = deviceOsBuildNumber;
/*     */             }
/*     */             continue;
/*     */           case "device_os_name":
/* 509 */             deviceOsName = reader.nextStringOrNull();
/* 510 */             if (deviceOsName != null) {
/* 511 */               data.deviceOsName = deviceOsName;
/*     */             }
/*     */             continue;
/*     */           case "device_os_version":
/* 515 */             deviceOsVersion = reader.nextStringOrNull();
/* 516 */             if (deviceOsVersion != null) {
/* 517 */               data.deviceOsVersion = deviceOsVersion;
/*     */             }
/*     */             continue;
/*     */           case "device_is_emulator":
/* 521 */             deviceIsEmulator = reader.nextBooleanOrNull();
/* 522 */             if (deviceIsEmulator != null) {
/* 523 */               data.deviceIsEmulator = deviceIsEmulator.booleanValue();
/*     */             }
/*     */             continue;
/*     */           case "architecture":
/* 527 */             cpuArchitecture = reader.nextStringOrNull();
/* 528 */             if (cpuArchitecture != null) {
/* 529 */               data.cpuArchitecture = cpuArchitecture;
/*     */             }
/*     */             continue;
/*     */           case "device_cpu_frequencies":
/* 533 */             deviceCpuFrequencies = (List<Integer>)reader.nextObjectOrNull();
/* 534 */             if (deviceCpuFrequencies != null) {
/* 535 */               data.deviceCpuFrequencies = deviceCpuFrequencies;
/*     */             }
/*     */             continue;
/*     */           case "device_physical_memory_bytes":
/* 539 */             devicePhysicalMemoryBytes = reader.nextStringOrNull();
/* 540 */             if (devicePhysicalMemoryBytes != null) {
/* 541 */               data.devicePhysicalMemoryBytes = devicePhysicalMemoryBytes;
/*     */             }
/*     */             continue;
/*     */           case "platform":
/* 545 */             platform = reader.nextStringOrNull();
/* 546 */             if (platform != null) {
/* 547 */               data.platform = platform;
/*     */             }
/*     */             continue;
/*     */           case "build_id":
/* 551 */             buildId = reader.nextStringOrNull();
/* 552 */             if (buildId != null) {
/* 553 */               data.buildId = buildId;
/*     */             }
/*     */             continue;
/*     */           case "transaction_name":
/* 557 */             transactionName = reader.nextStringOrNull();
/* 558 */             if (transactionName != null) {
/* 559 */               data.transactionName = transactionName;
/*     */             }
/*     */             continue;
/*     */           case "duration_ns":
/* 563 */             durationNs = reader.nextStringOrNull();
/* 564 */             if (durationNs != null) {
/* 565 */               data.durationNs = durationNs;
/*     */             }
/*     */             continue;
/*     */           case "version_code":
/* 569 */             versionCode = reader.nextStringOrNull();
/* 570 */             if (versionCode != null) {
/* 571 */               data.versionCode = versionCode;
/*     */             }
/*     */             continue;
/*     */           case "version_name":
/* 575 */             versionName = reader.nextStringOrNull();
/* 576 */             if (versionName != null) {
/* 577 */               data.release = versionName;
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "transactions":
/* 582 */             transactions = reader.nextListOrNull(logger, new ProfilingTransactionData.Deserializer());
/* 583 */             if (transactions != null) {
/* 584 */               data.transactions.addAll(transactions);
/*     */             }
/*     */             continue;
/*     */           case "transaction_id":
/* 588 */             transactionId = reader.nextStringOrNull();
/* 589 */             if (transactionId != null) {
/* 590 */               data.transactionId = transactionId;
/*     */             }
/*     */             continue;
/*     */           case "trace_id":
/* 594 */             traceId = reader.nextStringOrNull();
/* 595 */             if (traceId != null) {
/* 596 */               data.traceId = traceId;
/*     */             }
/*     */             continue;
/*     */           case "profile_id":
/* 600 */             profileId = reader.nextStringOrNull();
/* 601 */             if (profileId != null) {
/* 602 */               data.profileId = profileId;
/*     */             }
/*     */             continue;
/*     */           case "environment":
/* 606 */             environment = reader.nextStringOrNull();
/* 607 */             if (environment != null) {
/* 608 */               data.environment = environment;
/*     */             }
/*     */             continue;
/*     */           case "truncation_reason":
/* 612 */             truncationReason = reader.nextStringOrNull();
/* 613 */             if (truncationReason != null) {
/* 614 */               data.truncationReason = truncationReason;
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "measurements":
/* 619 */             measurements = reader.nextMapOrNull(logger, (JsonDeserializer<ProfileMeasurement>)new ProfileMeasurement.Deserializer());
/* 620 */             if (measurements != null) {
/* 621 */               data.measurementsMap.putAll(measurements);
/*     */             }
/*     */             continue;
/*     */           case "timestamp":
/* 625 */             timestamp = reader.nextDateOrNull(logger);
/* 626 */             if (timestamp != null) {
/* 627 */               data.timestamp = timestamp;
/*     */             }
/*     */             continue;
/*     */           case "sampled_profile":
/* 631 */             sampledProfile = reader.nextStringOrNull();
/* 632 */             if (sampledProfile != null) {
/* 633 */               data.sampledProfile = sampledProfile;
/*     */             }
/*     */             continue;
/*     */         } 
/* 637 */         if (unknown == null) {
/* 638 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 640 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 644 */       data.setUnknown(unknown);
/* 645 */       reader.endObject();
/* 646 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ProfilingTraceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */