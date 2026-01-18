/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class SentryStackTrace
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private List<SentryStackFrame> frames;
/*     */   @Nullable
/*     */   private Map<String, String> registers;
/*     */   @Nullable
/*     */   private Boolean snapshot;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public SentryStackTrace() {}
/*     */   
/*     */   public SentryStackTrace(@Nullable List<SentryStackFrame> frames) {
/*  75 */     this.frames = frames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<SentryStackFrame> getFrames() {
/*  84 */     return this.frames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrames(@Nullable List<SentryStackFrame> frames) {
/*  93 */     this.frames = frames;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, String> getRegisters() {
/*  97 */     return this.registers;
/*     */   }
/*     */   
/*     */   public void setRegisters(@Nullable Map<String, String> registers) {
/* 101 */     this.registers = registers;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getSnapshot() {
/* 105 */     return this.snapshot;
/*     */   }
/*     */   
/*     */   public void setSnapshot(@Nullable Boolean snapshot) {
/* 109 */     this.snapshot = snapshot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 117 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 122 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String FRAMES = "frames";
/*     */     public static final String REGISTERS = "registers";
/*     */     public static final String SNAPSHOT = "snapshot";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 134 */     writer.beginObject();
/* 135 */     if (this.frames != null) {
/* 136 */       writer.name("frames").value(logger, this.frames);
/*     */     }
/* 138 */     if (this.registers != null) {
/* 139 */       writer.name("registers").value(logger, this.registers);
/*     */     }
/* 141 */     if (this.snapshot != null) {
/* 142 */       writer.name("snapshot").value(this.snapshot);
/*     */     }
/* 144 */     if (this.unknown != null) {
/* 145 */       for (String key : this.unknown.keySet()) {
/* 146 */         Object value = this.unknown.get(key);
/* 147 */         writer.name(key);
/* 148 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 151 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryStackTrace>
/*     */   {
/*     */     @NotNull
/*     */     public SentryStackTrace deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 159 */       SentryStackTrace sentryStackTrace = new SentryStackTrace();
/* 160 */       Map<String, Object> unknown = null;
/* 161 */       reader.beginObject();
/* 162 */       while (reader.peek() == JsonToken.NAME) {
/* 163 */         String nextName = reader.nextName();
/* 164 */         switch (nextName) {
/*     */           case "frames":
/* 166 */             sentryStackTrace.frames = reader
/* 167 */               .nextListOrNull(logger, new SentryStackFrame.Deserializer());
/*     */             continue;
/*     */           case "registers":
/* 170 */             sentryStackTrace.registers = 
/* 171 */               CollectionUtils.newConcurrentHashMap((Map)reader
/* 172 */                 .nextObjectOrNull());
/*     */             continue;
/*     */           case "snapshot":
/* 175 */             sentryStackTrace.snapshot = reader.nextBooleanOrNull();
/*     */             continue;
/*     */         } 
/* 178 */         if (unknown == null) {
/* 179 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 181 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 185 */       sentryStackTrace.setUnknown(unknown);
/* 186 */       reader.endObject();
/* 187 */       return sentryStackTrace;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SentryStackTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */