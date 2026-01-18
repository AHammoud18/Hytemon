/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class ProfilingTransactionData
/*     */   implements JsonUnknown, JsonSerializable {
/*     */   @NotNull
/*     */   private String id;
/*     */   @NotNull
/*     */   private String traceId;
/*     */   @NotNull
/*     */   private String name;
/*     */   @NotNull
/*     */   private Long relativeStartNs;
/*     */   
/*     */   public ProfilingTransactionData() {
/*  25 */     this(NoOpTransaction.getInstance(), Long.valueOf(0L), Long.valueOf(0L)); } @Nullable
/*     */   private Long relativeEndNs; @NotNull
/*     */   private Long relativeStartCpuMs; @Nullable
/*     */   private Long relativeEndCpuMs; @Nullable
/*     */   private Map<String, Object> unknown; public ProfilingTransactionData(@NotNull ITransaction transaction, @NotNull Long startNs, @NotNull Long startCpuMs) {
/*  30 */     this.id = transaction.getEventId().toString();
/*  31 */     this.traceId = transaction.getSpanContext().getTraceId().toString();
/*  32 */     this.name = transaction.getName().isEmpty() ? "unknown" : transaction.getName();
/*  33 */     this.relativeStartNs = startNs;
/*  34 */     this.relativeStartCpuMs = startCpuMs;
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
/*     */   public void notifyFinish(@NotNull Long endNs, @NotNull Long profileStartNs, @NotNull Long endCpuMs, @NotNull Long profileStartCpuMs) {
/*  50 */     if (this.relativeEndNs == null) {
/*  51 */       this.relativeEndNs = Long.valueOf(endNs.longValue() - profileStartNs.longValue());
/*  52 */       this.relativeStartNs = Long.valueOf(this.relativeStartNs.longValue() - profileStartNs.longValue());
/*  53 */       this.relativeEndCpuMs = Long.valueOf(endCpuMs.longValue() - profileStartCpuMs.longValue());
/*  54 */       this.relativeStartCpuMs = Long.valueOf(this.relativeStartCpuMs.longValue() - profileStartCpuMs.longValue());
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   public String getId() {
/*  59 */     return this.id;
/*     */   }
/*     */   @NotNull
/*     */   public String getTraceId() {
/*  63 */     return this.traceId;
/*     */   }
/*     */   @NotNull
/*     */   public String getName() {
/*  67 */     return this.name;
/*     */   }
/*     */   @NotNull
/*     */   public Long getRelativeStartNs() {
/*  71 */     return this.relativeStartNs;
/*     */   }
/*     */   @Nullable
/*     */   public Long getRelativeEndNs() {
/*  75 */     return this.relativeEndNs;
/*     */   }
/*     */   @Nullable
/*     */   public Long getRelativeEndCpuMs() {
/*  79 */     return this.relativeEndCpuMs;
/*     */   }
/*     */   @NotNull
/*     */   public Long getRelativeStartCpuMs() {
/*  83 */     return this.relativeStartCpuMs;
/*     */   }
/*     */   
/*     */   public void setId(@NotNull String id) {
/*  87 */     this.id = id;
/*     */   }
/*     */   
/*     */   public void setTraceId(@NotNull String traceId) {
/*  91 */     this.traceId = traceId;
/*     */   }
/*     */   
/*     */   public void setName(@NotNull String name) {
/*  95 */     this.name = name;
/*     */   }
/*     */   
/*     */   public void setRelativeStartNs(@NotNull Long relativeStartNs) {
/*  99 */     this.relativeStartNs = relativeStartNs;
/*     */   }
/*     */   
/*     */   public void setRelativeEndNs(@Nullable Long relativeEndNs) {
/* 103 */     this.relativeEndNs = relativeEndNs;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 108 */     if (this == o) return true; 
/* 109 */     if (o == null || getClass() != o.getClass()) return false; 
/* 110 */     ProfilingTransactionData that = (ProfilingTransactionData)o;
/* 111 */     return (this.id.equals(that.id) && this.traceId
/* 112 */       .equals(that.traceId) && this.name
/* 113 */       .equals(that.name) && this.relativeStartNs
/* 114 */       .equals(that.relativeStartNs) && this.relativeStartCpuMs
/* 115 */       .equals(that.relativeStartCpuMs) && 
/* 116 */       Objects.equals(this.relativeEndCpuMs, that.relativeEndCpuMs) && 
/* 117 */       Objects.equals(this.relativeEndNs, that.relativeEndNs) && 
/* 118 */       Objects.equals(this.unknown, that.unknown));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 123 */     return Objects.hash(new Object[] { this.id, this.traceId, this.name, this.relativeStartNs, this.relativeEndNs, this.relativeStartCpuMs, this.relativeEndCpuMs, this.unknown });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String ID = "id";
/*     */ 
/*     */     
/*     */     public static final String TRACE_ID = "trace_id";
/*     */ 
/*     */     
/*     */     public static final String NAME = "name";
/*     */     
/*     */     public static final String START_NS = "relative_start_ns";
/*     */     
/*     */     public static final String END_NS = "relative_end_ns";
/*     */     
/*     */     public static final String START_CPU_MS = "relative_cpu_start_ms";
/*     */     
/*     */     public static final String END_CPU_MS = "relative_cpu_end_ms";
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 149 */     writer.beginObject();
/* 150 */     writer.name("id").value(logger, this.id);
/* 151 */     writer.name("trace_id").value(logger, this.traceId);
/* 152 */     writer.name("name").value(logger, this.name);
/* 153 */     writer.name("relative_start_ns").value(logger, this.relativeStartNs);
/* 154 */     writer.name("relative_end_ns").value(logger, this.relativeEndNs);
/* 155 */     writer.name("relative_cpu_start_ms").value(logger, this.relativeStartCpuMs);
/* 156 */     writer.name("relative_cpu_end_ms").value(logger, this.relativeEndCpuMs);
/* 157 */     if (this.unknown != null) {
/* 158 */       for (String key : this.unknown.keySet()) {
/* 159 */         Object value = this.unknown.get(key);
/* 160 */         writer.name(key);
/* 161 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 164 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 170 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 175 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ProfilingTransactionData>
/*     */   {
/*     */     @NotNull
/*     */     public ProfilingTransactionData deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 183 */       reader.beginObject();
/* 184 */       ProfilingTransactionData data = new ProfilingTransactionData();
/* 185 */       Map<String, Object> unknown = null;
/*     */       
/* 187 */       while (reader.peek() == JsonToken.NAME) {
/* 188 */         String id, traceId, name; Long startNs, endNs, startCpuMs, endCpuMs; String nextName = reader.nextName();
/* 189 */         switch (nextName) {
/*     */           case "id":
/* 191 */             id = reader.nextStringOrNull();
/* 192 */             if (id != null) {
/* 193 */               data.id = id;
/*     */             }
/*     */             continue;
/*     */           case "trace_id":
/* 197 */             traceId = reader.nextStringOrNull();
/* 198 */             if (traceId != null) {
/* 199 */               data.traceId = traceId;
/*     */             }
/*     */             continue;
/*     */           case "name":
/* 203 */             name = reader.nextStringOrNull();
/* 204 */             if (name != null) {
/* 205 */               data.name = name;
/*     */             }
/*     */             continue;
/*     */           case "relative_start_ns":
/* 209 */             startNs = reader.nextLongOrNull();
/* 210 */             if (startNs != null) {
/* 211 */               data.relativeStartNs = startNs;
/*     */             }
/*     */             continue;
/*     */           case "relative_end_ns":
/* 215 */             endNs = reader.nextLongOrNull();
/* 216 */             if (endNs != null) {
/* 217 */               data.relativeEndNs = endNs;
/*     */             }
/*     */             continue;
/*     */           case "relative_cpu_start_ms":
/* 221 */             startCpuMs = reader.nextLongOrNull();
/* 222 */             if (startCpuMs != null) {
/* 223 */               data.relativeStartCpuMs = startCpuMs;
/*     */             }
/*     */             continue;
/*     */           case "relative_cpu_end_ms":
/* 227 */             endCpuMs = reader.nextLongOrNull();
/* 228 */             if (endCpuMs != null) {
/* 229 */               data.relativeEndCpuMs = endCpuMs;
/*     */             }
/*     */             continue;
/*     */         } 
/* 233 */         if (unknown == null) {
/* 234 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 236 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 240 */       data.setUnknown(unknown);
/* 241 */       reader.endObject();
/* 242 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ProfilingTransactionData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */