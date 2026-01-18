/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class MonitorConfig
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   private MonitorSchedule schedule;
/*     */   @Nullable
/*     */   private Long checkinMargin;
/*     */   @Nullable
/*     */   private Long maxRuntime;
/*     */   
/*     */   public MonitorConfig(@NotNull MonitorSchedule schedule) {
/*  21 */     this.schedule = schedule;
/*  22 */     SentryOptions.Cron defaultCron = ScopesAdapter.getInstance().getOptions().getCron();
/*  23 */     if (defaultCron != null) {
/*  24 */       this.checkinMargin = defaultCron.getDefaultCheckinMargin();
/*  25 */       this.maxRuntime = defaultCron.getDefaultMaxRuntime();
/*  26 */       this.timezone = defaultCron.getDefaultTimezone();
/*  27 */       this.failureIssueThreshold = defaultCron.getDefaultFailureIssueThreshold();
/*  28 */       this.recoveryThreshold = defaultCron.getDefaultRecoveryThreshold();
/*     */     }  } @Nullable private String timezone; @Nullable
/*     */   private Long failureIssueThreshold; @Nullable
/*     */   private Long recoveryThreshold; @Nullable
/*     */   private Map<String, Object> unknown; @NotNull
/*  33 */   public MonitorSchedule getSchedule() { return this.schedule; }
/*     */ 
/*     */   
/*     */   public void setSchedule(@NotNull MonitorSchedule schedule) {
/*  37 */     this.schedule = schedule;
/*     */   }
/*     */   @Nullable
/*     */   public Long getCheckinMargin() {
/*  41 */     return this.checkinMargin;
/*     */   }
/*     */   
/*     */   public void setCheckinMargin(@Nullable Long checkinMargin) {
/*  45 */     this.checkinMargin = checkinMargin;
/*     */   }
/*     */   @Nullable
/*     */   public Long getMaxRuntime() {
/*  49 */     return this.maxRuntime;
/*     */   }
/*     */   
/*     */   public void setMaxRuntime(@Nullable Long maxRuntime) {
/*  53 */     this.maxRuntime = maxRuntime;
/*     */   }
/*     */   @Nullable
/*     */   public String getTimezone() {
/*  57 */     return this.timezone;
/*     */   }
/*     */   
/*     */   public void setTimezone(@Nullable String timezone) {
/*  61 */     this.timezone = timezone;
/*     */   }
/*     */   @Nullable
/*     */   public Long getFailureIssueThreshold() {
/*  65 */     return this.failureIssueThreshold;
/*     */   }
/*     */   
/*     */   public void setFailureIssueThreshold(@Nullable Long failureIssueThreshold) {
/*  69 */     this.failureIssueThreshold = failureIssueThreshold;
/*     */   }
/*     */   @Nullable
/*     */   public Long getRecoveryThreshold() {
/*  73 */     return this.recoveryThreshold;
/*     */   }
/*     */   
/*     */   public void setRecoveryThreshold(@Nullable Long recoveryThreshold) {
/*  77 */     this.recoveryThreshold = recoveryThreshold;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String SCHEDULE = "schedule";
/*     */     
/*     */     public static final String CHECKIN_MARGIN = "checkin_margin";
/*     */     
/*     */     public static final String MAX_RUNTIME = "max_runtime";
/*     */     public static final String TIMEZONE = "timezone";
/*     */     public static final String FAILURE_ISSUE_THRESHOLD = "failure_issue_threshold";
/*     */     public static final String RECOVERY_THRESHOLD = "recovery_threshold";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  95 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 100 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 108 */     writer.beginObject();
/* 109 */     writer.name("schedule");
/* 110 */     this.schedule.serialize(writer, logger);
/* 111 */     if (this.checkinMargin != null) {
/* 112 */       writer.name("checkin_margin").value(this.checkinMargin);
/*     */     }
/* 114 */     if (this.maxRuntime != null) {
/* 115 */       writer.name("max_runtime").value(this.maxRuntime);
/*     */     }
/* 117 */     if (this.timezone != null) {
/* 118 */       writer.name("timezone").value(this.timezone);
/*     */     }
/* 120 */     if (this.failureIssueThreshold != null) {
/* 121 */       writer.name("failure_issue_threshold").value(this.failureIssueThreshold);
/*     */     }
/* 123 */     if (this.recoveryThreshold != null) {
/* 124 */       writer.name("recovery_threshold").value(this.recoveryThreshold);
/*     */     }
/* 126 */     if (this.unknown != null) {
/* 127 */       for (String key : this.unknown.keySet()) {
/* 128 */         Object value = this.unknown.get(key);
/* 129 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 132 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<MonitorConfig>
/*     */   {
/*     */     @NotNull
/*     */     public MonitorConfig deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 141 */       MonitorSchedule schedule = null;
/* 142 */       Long checkinMargin = null;
/* 143 */       Long maxRuntime = null;
/* 144 */       String timezone = null;
/* 145 */       Long failureIssureThreshold = null;
/* 146 */       Long recoveryThreshold = null;
/* 147 */       Map<String, Object> unknown = null;
/*     */       
/* 149 */       reader.beginObject();
/* 150 */       while (reader.peek() == JsonToken.NAME) {
/* 151 */         String nextName = reader.nextName();
/* 152 */         switch (nextName) {
/*     */           case "schedule":
/* 154 */             schedule = (new MonitorSchedule.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "checkin_margin":
/* 157 */             checkinMargin = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "max_runtime":
/* 160 */             maxRuntime = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "timezone":
/* 163 */             timezone = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "failure_issue_threshold":
/* 166 */             failureIssureThreshold = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "recovery_threshold":
/* 169 */             recoveryThreshold = reader.nextLongOrNull();
/*     */             continue;
/*     */         } 
/* 172 */         if (unknown == null) {
/* 173 */           unknown = new HashMap<>();
/*     */         }
/* 175 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 179 */       reader.endObject();
/*     */       
/* 181 */       if (schedule == null) {
/* 182 */         String message = "Missing required field \"schedule\"";
/* 183 */         Exception exception = new IllegalStateException(message);
/* 184 */         logger.log(SentryLevel.ERROR, message, exception);
/* 185 */         throw exception;
/*     */       } 
/*     */       
/* 188 */       MonitorConfig monitorConfig = new MonitorConfig(schedule);
/* 189 */       monitorConfig.setCheckinMargin(checkinMargin);
/* 190 */       monitorConfig.setMaxRuntime(maxRuntime);
/* 191 */       monitorConfig.setTimezone(timezone);
/* 192 */       monitorConfig.setFailureIssueThreshold(failureIssureThreshold);
/* 193 */       monitorConfig.setRecoveryThreshold(recoveryThreshold);
/* 194 */       monitorConfig.setUnknown(unknown);
/* 195 */       return monitorConfig;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\MonitorConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */