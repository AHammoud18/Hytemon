/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public final class Device
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "device";
/*     */   @Nullable
/*     */   private String name;
/*     */   @Nullable
/*     */   private String manufacturer;
/*     */   @Nullable
/*     */   private String brand;
/*     */   @Nullable
/*     */   private String family;
/*     */   @Nullable
/*     */   private String model;
/*     */   @Nullable
/*     */   private String modelId;
/*     */   @Nullable
/*     */   private String[] archs;
/*     */   @Nullable
/*     */   private Float batteryLevel;
/*     */   @Nullable
/*     */   private Boolean charging;
/*     */   @Nullable
/*     */   private Boolean online;
/*     */   @Nullable
/*     */   private DeviceOrientation orientation;
/*     */   @Nullable
/*     */   private Boolean simulator;
/*     */   @Nullable
/*     */   private Long memorySize;
/*     */   @Nullable
/*     */   private Long freeMemory;
/*     */   @Nullable
/*     */   private Long usableMemory;
/*     */   @Nullable
/*     */   private Boolean lowMemory;
/*     */   @Nullable
/*     */   private Long storageSize;
/*     */   @Nullable
/*     */   private Long freeStorage;
/*     */   @Nullable
/*     */   private Long externalStorageSize;
/*     */   @Nullable
/*     */   private Long externalFreeStorage;
/*     */   @Nullable
/*     */   private Integer screenWidthPixels;
/*     */   @Nullable
/*     */   private Integer screenHeightPixels;
/*     */   @Nullable
/*     */   private Float screenDensity;
/*     */   @Nullable
/*     */   private Integer screenDpi;
/*     */   @Nullable
/*     */   private Date bootTime;
/*     */   @Nullable
/*     */   private TimeZone timezone;
/*     */   @Nullable
/*     */   private String id;
/*     */   @Nullable
/*     */   private String locale;
/*     */   @Nullable
/*     */   private String connectionType;
/*     */   @Nullable
/*     */   private Float batteryTemperature;
/*     */   @Nullable
/*     */   private Integer processorCount;
/*     */   @Nullable
/*     */   private Double processorFrequency;
/*     */   @Nullable
/*     */   private String cpuDescription;
/*     */   @Nullable
/*     */   private String chipset;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public Device() {}
/*     */   
/*     */   Device(@NotNull Device device) {
/* 157 */     this.name = device.name;
/* 158 */     this.manufacturer = device.manufacturer;
/* 159 */     this.brand = device.brand;
/* 160 */     this.family = device.family;
/* 161 */     this.model = device.model;
/* 162 */     this.modelId = device.modelId;
/* 163 */     this.charging = device.charging;
/* 164 */     this.online = device.online;
/* 165 */     this.orientation = device.orientation;
/* 166 */     this.simulator = device.simulator;
/* 167 */     this.memorySize = device.memorySize;
/* 168 */     this.freeMemory = device.freeMemory;
/* 169 */     this.usableMemory = device.usableMemory;
/* 170 */     this.lowMemory = device.lowMemory;
/* 171 */     this.storageSize = device.storageSize;
/* 172 */     this.freeStorage = device.freeStorage;
/* 173 */     this.externalStorageSize = device.externalStorageSize;
/* 174 */     this.externalFreeStorage = device.externalFreeStorage;
/* 175 */     this.screenWidthPixels = device.screenWidthPixels;
/* 176 */     this.screenHeightPixels = device.screenHeightPixels;
/* 177 */     this.screenDensity = device.screenDensity;
/* 178 */     this.screenDpi = device.screenDpi;
/* 179 */     this.bootTime = device.bootTime;
/* 180 */     this.id = device.id;
/* 181 */     this.connectionType = device.connectionType;
/* 182 */     this.batteryTemperature = device.batteryTemperature;
/* 183 */     this.batteryLevel = device.batteryLevel;
/* 184 */     String[] archsRef = device.archs;
/* 185 */     this.archs = (archsRef != null) ? (String[])archsRef.clone() : null;
/* 186 */     this.locale = device.locale;
/*     */     
/* 188 */     TimeZone timezoneRef = device.timezone;
/* 189 */     this.timezone = (timezoneRef != null) ? (TimeZone)timezoneRef.clone() : null;
/*     */     
/* 191 */     this.processorCount = device.processorCount;
/* 192 */     this.processorFrequency = device.processorFrequency;
/* 193 */     this.cpuDescription = device.cpuDescription;
/* 194 */     this.chipset = device.chipset;
/*     */     
/* 196 */     this.unknown = CollectionUtils.newConcurrentHashMap(device.unknown);
/*     */   }
/*     */   @Nullable
/*     */   public String getName() {
/* 200 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(@Nullable String name) {
/* 204 */     this.name = name;
/*     */   }
/*     */   @Nullable
/*     */   public String getManufacturer() {
/* 208 */     return this.manufacturer;
/*     */   }
/*     */   
/*     */   public void setManufacturer(@Nullable String manufacturer) {
/* 212 */     this.manufacturer = manufacturer;
/*     */   }
/*     */   @Nullable
/*     */   public String getBrand() {
/* 216 */     return this.brand;
/*     */   }
/*     */   
/*     */   public void setBrand(@Nullable String brand) {
/* 220 */     this.brand = brand;
/*     */   }
/*     */   @Nullable
/*     */   public String getFamily() {
/* 224 */     return this.family;
/*     */   }
/*     */   
/*     */   public void setFamily(@Nullable String family) {
/* 228 */     this.family = family;
/*     */   }
/*     */   @Nullable
/*     */   public String getModel() {
/* 232 */     return this.model;
/*     */   }
/*     */   
/*     */   public void setModel(@Nullable String model) {
/* 236 */     this.model = model;
/*     */   }
/*     */   @Nullable
/*     */   public String getModelId() {
/* 240 */     return this.modelId;
/*     */   }
/*     */   
/*     */   public void setModelId(@Nullable String modelId) {
/* 244 */     this.modelId = modelId;
/*     */   }
/*     */   @Nullable
/*     */   public Float getBatteryLevel() {
/* 248 */     return this.batteryLevel;
/*     */   }
/*     */   
/*     */   public void setBatteryLevel(@Nullable Float batteryLevel) {
/* 252 */     this.batteryLevel = batteryLevel;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isCharging() {
/* 256 */     return this.charging;
/*     */   }
/*     */   
/*     */   public void setCharging(@Nullable Boolean charging) {
/* 260 */     this.charging = charging;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isOnline() {
/* 264 */     return this.online;
/*     */   }
/*     */   
/*     */   public void setOnline(@Nullable Boolean online) {
/* 268 */     this.online = online;
/*     */   }
/*     */   @Nullable
/*     */   public DeviceOrientation getOrientation() {
/* 272 */     return this.orientation;
/*     */   }
/*     */   
/*     */   public void setOrientation(@Nullable DeviceOrientation orientation) {
/* 276 */     this.orientation = orientation;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isSimulator() {
/* 280 */     return this.simulator;
/*     */   }
/*     */   
/*     */   public void setSimulator(@Nullable Boolean simulator) {
/* 284 */     this.simulator = simulator;
/*     */   }
/*     */   @Nullable
/*     */   public Long getMemorySize() {
/* 288 */     return this.memorySize;
/*     */   }
/*     */   
/*     */   public void setMemorySize(@Nullable Long memorySize) {
/* 292 */     this.memorySize = memorySize;
/*     */   }
/*     */   @Nullable
/*     */   public Long getFreeMemory() {
/* 296 */     return this.freeMemory;
/*     */   }
/*     */   
/*     */   public void setFreeMemory(@Nullable Long freeMemory) {
/* 300 */     this.freeMemory = freeMemory;
/*     */   }
/*     */   @Nullable
/*     */   public Long getUsableMemory() {
/* 304 */     return this.usableMemory;
/*     */   }
/*     */   
/*     */   public void setUsableMemory(@Nullable Long usableMemory) {
/* 308 */     this.usableMemory = usableMemory;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isLowMemory() {
/* 312 */     return this.lowMemory;
/*     */   }
/*     */   
/*     */   public void setLowMemory(@Nullable Boolean lowMemory) {
/* 316 */     this.lowMemory = lowMemory;
/*     */   }
/*     */   @Nullable
/*     */   public Long getStorageSize() {
/* 320 */     return this.storageSize;
/*     */   }
/*     */   
/*     */   public void setStorageSize(@Nullable Long storageSize) {
/* 324 */     this.storageSize = storageSize;
/*     */   }
/*     */   @Nullable
/*     */   public Long getFreeStorage() {
/* 328 */     return this.freeStorage;
/*     */   }
/*     */   
/*     */   public void setFreeStorage(@Nullable Long freeStorage) {
/* 332 */     this.freeStorage = freeStorage;
/*     */   }
/*     */   @Nullable
/*     */   public Long getExternalStorageSize() {
/* 336 */     return this.externalStorageSize;
/*     */   }
/*     */   
/*     */   public void setExternalStorageSize(@Nullable Long externalStorageSize) {
/* 340 */     this.externalStorageSize = externalStorageSize;
/*     */   }
/*     */   @Nullable
/*     */   public Long getExternalFreeStorage() {
/* 344 */     return this.externalFreeStorage;
/*     */   }
/*     */   
/*     */   public void setExternalFreeStorage(@Nullable Long externalFreeStorage) {
/* 348 */     this.externalFreeStorage = externalFreeStorage;
/*     */   }
/*     */   @Nullable
/*     */   public Float getScreenDensity() {
/* 352 */     return this.screenDensity;
/*     */   }
/*     */   
/*     */   public void setScreenDensity(@Nullable Float screenDensity) {
/* 356 */     this.screenDensity = screenDensity;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getScreenDpi() {
/* 360 */     return this.screenDpi;
/*     */   }
/*     */   
/*     */   public void setScreenDpi(@Nullable Integer screenDpi) {
/* 364 */     this.screenDpi = screenDpi;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Date getBootTime() {
/* 369 */     Date bootTimeRef = this.bootTime;
/* 370 */     return (bootTimeRef != null) ? (Date)bootTimeRef.clone() : null;
/*     */   }
/*     */   
/*     */   public void setBootTime(@Nullable Date bootTime) {
/* 374 */     this.bootTime = bootTime;
/*     */   }
/*     */   @Nullable
/*     */   public TimeZone getTimezone() {
/* 378 */     return this.timezone;
/*     */   }
/*     */   
/*     */   public void setTimezone(@Nullable TimeZone timezone) {
/* 382 */     this.timezone = timezone;
/*     */   }
/*     */   @Nullable
/*     */   public String[] getArchs() {
/* 386 */     return this.archs;
/*     */   }
/*     */   
/*     */   public void setArchs(@Nullable String[] archs) {
/* 390 */     this.archs = archs;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getScreenWidthPixels() {
/* 394 */     return this.screenWidthPixels;
/*     */   }
/*     */   
/*     */   public void setScreenWidthPixels(@Nullable Integer screenWidthPixels) {
/* 398 */     this.screenWidthPixels = screenWidthPixels;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getScreenHeightPixels() {
/* 402 */     return this.screenHeightPixels;
/*     */   }
/*     */   
/*     */   public void setScreenHeightPixels(@Nullable Integer screenHeightPixels) {
/* 406 */     this.screenHeightPixels = screenHeightPixels;
/*     */   }
/*     */   @Nullable
/*     */   public String getId() {
/* 410 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(@Nullable String id) {
/* 414 */     this.id = id;
/*     */   }
/*     */   @Nullable
/*     */   public String getConnectionType() {
/* 418 */     return this.connectionType;
/*     */   }
/*     */   
/*     */   public void setConnectionType(@Nullable String connectionType) {
/* 422 */     this.connectionType = connectionType;
/*     */   }
/*     */   @Nullable
/*     */   public Float getBatteryTemperature() {
/* 426 */     return this.batteryTemperature;
/*     */   }
/*     */   
/*     */   public void setBatteryTemperature(@Nullable Float batteryTemperature) {
/* 430 */     this.batteryTemperature = batteryTemperature;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getProcessorCount() {
/* 434 */     return this.processorCount;
/*     */   }
/*     */   
/*     */   public void setProcessorCount(@Nullable Integer processorCount) {
/* 438 */     this.processorCount = processorCount;
/*     */   }
/*     */   @Nullable
/*     */   public Double getProcessorFrequency() {
/* 442 */     return this.processorFrequency;
/*     */   }
/*     */   
/*     */   public void setProcessorFrequency(@Nullable Double processorFrequency) {
/* 446 */     this.processorFrequency = processorFrequency;
/*     */   }
/*     */   @Nullable
/*     */   public String getCpuDescription() {
/* 450 */     return this.cpuDescription;
/*     */   }
/*     */   
/*     */   public void setCpuDescription(@Nullable String cpuDescription) {
/* 454 */     this.cpuDescription = cpuDescription;
/*     */   }
/*     */   @Nullable
/*     */   public String getChipset() {
/* 458 */     return this.chipset;
/*     */   }
/*     */   
/*     */   public void setChipset(@Nullable String chipset) {
/* 462 */     this.chipset = chipset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 467 */     if (this == o) return true; 
/* 468 */     if (o == null || getClass() != o.getClass()) return false; 
/* 469 */     Device device = (Device)o;
/* 470 */     return (Objects.equals(this.name, device.name) && 
/* 471 */       Objects.equals(this.manufacturer, device.manufacturer) && 
/* 472 */       Objects.equals(this.brand, device.brand) && 
/* 473 */       Objects.equals(this.family, device.family) && 
/* 474 */       Objects.equals(this.model, device.model) && 
/* 475 */       Objects.equals(this.modelId, device.modelId) && 
/* 476 */       Arrays.equals((Object[])this.archs, (Object[])device.archs) && 
/* 477 */       Objects.equals(this.batteryLevel, device.batteryLevel) && 
/* 478 */       Objects.equals(this.charging, device.charging) && 
/* 479 */       Objects.equals(this.online, device.online) && this.orientation == device.orientation && 
/*     */       
/* 481 */       Objects.equals(this.simulator, device.simulator) && 
/* 482 */       Objects.equals(this.memorySize, device.memorySize) && 
/* 483 */       Objects.equals(this.freeMemory, device.freeMemory) && 
/* 484 */       Objects.equals(this.usableMemory, device.usableMemory) && 
/* 485 */       Objects.equals(this.lowMemory, device.lowMemory) && 
/* 486 */       Objects.equals(this.storageSize, device.storageSize) && 
/* 487 */       Objects.equals(this.freeStorage, device.freeStorage) && 
/* 488 */       Objects.equals(this.externalStorageSize, device.externalStorageSize) && 
/* 489 */       Objects.equals(this.externalFreeStorage, device.externalFreeStorage) && 
/* 490 */       Objects.equals(this.screenWidthPixels, device.screenWidthPixels) && 
/* 491 */       Objects.equals(this.screenHeightPixels, device.screenHeightPixels) && 
/* 492 */       Objects.equals(this.screenDensity, device.screenDensity) && 
/* 493 */       Objects.equals(this.screenDpi, device.screenDpi) && 
/* 494 */       Objects.equals(this.bootTime, device.bootTime) && 
/* 495 */       Objects.equals(this.id, device.id) && 
/* 496 */       Objects.equals(this.locale, device.locale) && 
/* 497 */       Objects.equals(this.connectionType, device.connectionType) && 
/* 498 */       Objects.equals(this.batteryTemperature, device.batteryTemperature) && 
/* 499 */       Objects.equals(this.processorCount, device.processorCount) && 
/* 500 */       Objects.equals(this.processorFrequency, device.processorFrequency) && 
/* 501 */       Objects.equals(this.cpuDescription, device.cpuDescription) && 
/* 502 */       Objects.equals(this.chipset, device.chipset));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 508 */     int result = Objects.hash(new Object[] { 
/*     */           this.name, this.manufacturer, this.brand, this.family, this.model, this.modelId, this.batteryLevel, this.charging, this.online, this.orientation, 
/*     */           this.simulator, this.memorySize, this.freeMemory, this.usableMemory, this.lowMemory, this.storageSize, this.freeStorage, this.externalStorageSize, this.externalFreeStorage, this.screenWidthPixels, 
/*     */           this.screenHeightPixels, this.screenDensity, this.screenDpi, this.bootTime, this.timezone, this.id, this.locale, this.connectionType, this.batteryTemperature, this.processorCount, 
/*     */           this.processorFrequency, this.cpuDescription, this.chipset });
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
/* 542 */     result = 31 * result + Arrays.hashCode((Object[])this.archs);
/* 543 */     return result;
/*     */   }
/*     */   
/*     */   public enum DeviceOrientation implements JsonSerializable {
/* 547 */     PORTRAIT,
/* 548 */     LANDSCAPE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 555 */       writer.value(toString().toLowerCase(Locale.ROOT));
/*     */     }
/*     */ 
/*     */     
/*     */     public static final class Deserializer
/*     */       implements JsonDeserializer<DeviceOrientation>
/*     */     {
/*     */       @NotNull
/*     */       public Device.DeviceOrientation deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 564 */         return Device.DeviceOrientation.valueOf(reader.nextString().toUpperCase(Locale.ROOT));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String NAME = "name";
/*     */     
/*     */     public static final String MANUFACTURER = "manufacturer";
/*     */     
/*     */     public static final String BRAND = "brand";
/*     */     public static final String FAMILY = "family";
/*     */     public static final String MODEL = "model";
/*     */     public static final String MODEL_ID = "model_id";
/*     */     public static final String ARCHS = "archs";
/*     */     public static final String BATTERY_LEVEL = "battery_level";
/*     */     public static final String CHARGING = "charging";
/*     */     public static final String ONLINE = "online";
/*     */     public static final String ORIENTATION = "orientation";
/*     */     public static final String SIMULATOR = "simulator";
/*     */     public static final String MEMORY_SIZE = "memory_size";
/*     */     public static final String FREE_MEMORY = "free_memory";
/*     */     public static final String USABLE_MEMORY = "usable_memory";
/*     */     public static final String LOW_MEMORY = "low_memory";
/*     */     public static final String STORAGE_SIZE = "storage_size";
/*     */     public static final String FREE_STORAGE = "free_storage";
/*     */     public static final String EXTERNAL_STORAGE_SIZE = "external_storage_size";
/*     */     public static final String EXTERNAL_FREE_STORAGE = "external_free_storage";
/*     */     public static final String SCREEN_WIDTH_PIXELS = "screen_width_pixels";
/*     */     public static final String SCREEN_HEIGHT_PIXELS = "screen_height_pixels";
/*     */     public static final String SCREEN_DENSITY = "screen_density";
/*     */     public static final String SCREEN_DPI = "screen_dpi";
/*     */     public static final String BOOT_TIME = "boot_time";
/*     */     public static final String TIMEZONE = "timezone";
/*     */     public static final String ID = "id";
/*     */     public static final String CONNECTION_TYPE = "connection_type";
/*     */     public static final String BATTERY_TEMPERATURE = "battery_temperature";
/*     */     public static final String LOCALE = "locale";
/*     */     public static final String PROCESSOR_COUNT = "processor_count";
/*     */     public static final String CPU_DESCRIPTION = "cpu_description";
/*     */     public static final String PROCESSOR_FREQUENCY = "processor_frequency";
/*     */     public static final String CHIPSET = "chipset";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 611 */     writer.beginObject();
/* 612 */     if (this.name != null) {
/* 613 */       writer.name("name").value(this.name);
/*     */     }
/* 615 */     if (this.manufacturer != null) {
/* 616 */       writer.name("manufacturer").value(this.manufacturer);
/*     */     }
/* 618 */     if (this.brand != null) {
/* 619 */       writer.name("brand").value(this.brand);
/*     */     }
/* 621 */     if (this.family != null) {
/* 622 */       writer.name("family").value(this.family);
/*     */     }
/* 624 */     if (this.model != null) {
/* 625 */       writer.name("model").value(this.model);
/*     */     }
/* 627 */     if (this.modelId != null) {
/* 628 */       writer.name("model_id").value(this.modelId);
/*     */     }
/* 630 */     if (this.archs != null) {
/* 631 */       writer.name("archs").value(logger, this.archs);
/*     */     }
/* 633 */     if (this.batteryLevel != null) {
/* 634 */       writer.name("battery_level").value(this.batteryLevel);
/*     */     }
/* 636 */     if (this.charging != null) {
/* 637 */       writer.name("charging").value(this.charging);
/*     */     }
/* 639 */     if (this.online != null) {
/* 640 */       writer.name("online").value(this.online);
/*     */     }
/* 642 */     if (this.orientation != null) {
/* 643 */       writer.name("orientation").value(logger, this.orientation);
/*     */     }
/* 645 */     if (this.simulator != null) {
/* 646 */       writer.name("simulator").value(this.simulator);
/*     */     }
/* 648 */     if (this.memorySize != null) {
/* 649 */       writer.name("memory_size").value(this.memorySize);
/*     */     }
/* 651 */     if (this.freeMemory != null) {
/* 652 */       writer.name("free_memory").value(this.freeMemory);
/*     */     }
/* 654 */     if (this.usableMemory != null) {
/* 655 */       writer.name("usable_memory").value(this.usableMemory);
/*     */     }
/* 657 */     if (this.lowMemory != null) {
/* 658 */       writer.name("low_memory").value(this.lowMemory);
/*     */     }
/* 660 */     if (this.storageSize != null) {
/* 661 */       writer.name("storage_size").value(this.storageSize);
/*     */     }
/* 663 */     if (this.freeStorage != null) {
/* 664 */       writer.name("free_storage").value(this.freeStorage);
/*     */     }
/* 666 */     if (this.externalStorageSize != null) {
/* 667 */       writer.name("external_storage_size").value(this.externalStorageSize);
/*     */     }
/* 669 */     if (this.externalFreeStorage != null) {
/* 670 */       writer.name("external_free_storage").value(this.externalFreeStorage);
/*     */     }
/* 672 */     if (this.screenWidthPixels != null) {
/* 673 */       writer.name("screen_width_pixels").value(this.screenWidthPixels);
/*     */     }
/* 675 */     if (this.screenHeightPixels != null) {
/* 676 */       writer.name("screen_height_pixels").value(this.screenHeightPixels);
/*     */     }
/* 678 */     if (this.screenDensity != null) {
/* 679 */       writer.name("screen_density").value(this.screenDensity);
/*     */     }
/* 681 */     if (this.screenDpi != null) {
/* 682 */       writer.name("screen_dpi").value(this.screenDpi);
/*     */     }
/* 684 */     if (this.bootTime != null) {
/* 685 */       writer.name("boot_time").value(logger, this.bootTime);
/*     */     }
/* 687 */     if (this.timezone != null) {
/* 688 */       writer.name("timezone").value(logger, this.timezone);
/*     */     }
/* 690 */     if (this.id != null) {
/* 691 */       writer.name("id").value(this.id);
/*     */     }
/* 693 */     if (this.connectionType != null) {
/* 694 */       writer.name("connection_type").value(this.connectionType);
/*     */     }
/* 696 */     if (this.batteryTemperature != null) {
/* 697 */       writer.name("battery_temperature").value(this.batteryTemperature);
/*     */     }
/* 699 */     if (this.locale != null) {
/* 700 */       writer.name("locale").value(this.locale);
/*     */     }
/* 702 */     if (this.processorCount != null) {
/* 703 */       writer.name("processor_count").value(this.processorCount);
/*     */     }
/* 705 */     if (this.processorFrequency != null) {
/* 706 */       writer.name("processor_frequency").value(this.processorFrequency);
/*     */     }
/* 708 */     if (this.cpuDescription != null) {
/* 709 */       writer.name("cpu_description").value(this.cpuDescription);
/*     */     }
/* 711 */     if (this.chipset != null) {
/* 712 */       writer.name("chipset").value(this.chipset);
/*     */     }
/* 714 */     if (this.unknown != null) {
/* 715 */       for (String key : this.unknown.keySet()) {
/* 716 */         Object value = this.unknown.get(key);
/* 717 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 720 */     writer.endObject();
/*     */   }
/*     */   @Nullable
/*     */   public String getLocale() {
/* 724 */     return this.locale;
/*     */   }
/*     */   
/*     */   public void setLocale(@Nullable String locale) {
/* 728 */     this.locale = locale;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 734 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 739 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Device>
/*     */   {
/*     */     @NotNull
/*     */     public Device deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 747 */       reader.beginObject();
/* 748 */       Device device = new Device();
/* 749 */       Map<String, Object> unknown = null;
/* 750 */       while (reader.peek() == JsonToken.NAME) {
/* 751 */         List<?> archsList; String nextName = reader.nextName();
/* 752 */         switch (nextName) {
/*     */           case "name":
/* 754 */             device.name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "manufacturer":
/* 757 */             device.manufacturer = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "brand":
/* 760 */             device.brand = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "family":
/* 763 */             device.family = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "model":
/* 766 */             device.model = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "model_id":
/* 769 */             device.modelId = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "archs":
/* 772 */             archsList = (List)reader.nextObjectOrNull();
/* 773 */             if (archsList != null) {
/* 774 */               String[] arrayOfString = new String[archsList.size()];
/* 775 */               archsList.toArray(arrayOfString);
/* 776 */               device.archs = arrayOfString;
/*     */             } 
/*     */             continue;
/*     */           case "battery_level":
/* 780 */             device.batteryLevel = reader.nextFloatOrNull();
/*     */             continue;
/*     */           case "charging":
/* 783 */             device.charging = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "online":
/* 786 */             device.online = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "orientation":
/* 789 */             device.orientation = (Device.DeviceOrientation)reader.nextOrNull(logger, new Device.DeviceOrientation.Deserializer());
/*     */             continue;
/*     */           case "simulator":
/* 792 */             device.simulator = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "memory_size":
/* 795 */             device.memorySize = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "free_memory":
/* 798 */             device.freeMemory = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "usable_memory":
/* 801 */             device.usableMemory = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "low_memory":
/* 804 */             device.lowMemory = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "storage_size":
/* 807 */             device.storageSize = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "free_storage":
/* 810 */             device.freeStorage = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "external_storage_size":
/* 813 */             device.externalStorageSize = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "external_free_storage":
/* 816 */             device.externalFreeStorage = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "screen_width_pixels":
/* 819 */             device.screenWidthPixels = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "screen_height_pixels":
/* 822 */             device.screenHeightPixels = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "screen_density":
/* 825 */             device.screenDensity = reader.nextFloatOrNull();
/*     */             continue;
/*     */           case "screen_dpi":
/* 828 */             device.screenDpi = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "boot_time":
/* 831 */             if (reader.peek() == JsonToken.STRING) {
/* 832 */               device.bootTime = reader.nextDateOrNull(logger);
/*     */             }
/*     */             continue;
/*     */           case "timezone":
/* 836 */             device.timezone = reader.nextTimeZoneOrNull(logger);
/*     */             continue;
/*     */           case "id":
/* 839 */             device.id = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "connection_type":
/* 842 */             device.connectionType = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "battery_temperature":
/* 845 */             device.batteryTemperature = reader.nextFloatOrNull();
/*     */             continue;
/*     */           case "locale":
/* 848 */             device.locale = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "processor_count":
/* 851 */             device.processorCount = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "processor_frequency":
/* 854 */             device.processorFrequency = reader.nextDoubleOrNull();
/*     */             continue;
/*     */           case "cpu_description":
/* 857 */             device.cpuDescription = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "chipset":
/* 860 */             device.chipset = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 863 */         if (unknown == null) {
/* 864 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 866 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 870 */       device.setUnknown(unknown);
/* 871 */       reader.endObject();
/* 872 */       return device;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Device.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */