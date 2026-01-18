/*     */ package io.sentry.protocol.profiling;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.protocol.SentryStackFrame;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class SentryProfile implements JsonUnknown, JsonSerializable {
/*     */   @NotNull
/*  23 */   private List<SentrySample> samples = new ArrayList<>();
/*     */   @NotNull
/*  25 */   private List<List<Integer>> stacks = new ArrayList<>();
/*     */   @NotNull
/*  27 */   private List<SentryStackFrame> frames = new ArrayList<>();
/*     */   @NotNull
/*  29 */   private Map<String, SentryThreadMetadata> threadMetadata = new HashMap<>();
/*     */   
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  36 */     writer.beginObject();
/*  37 */     writer.name("samples").value(logger, this.samples);
/*  38 */     writer.name("stacks").value(logger, this.stacks);
/*  39 */     writer.name("frames").value(logger, this.frames);
/*  40 */     writer.name("thread_metadata").value(logger, this.threadMetadata);
/*     */     
/*  42 */     if (this.unknown != null) {
/*  43 */       for (String key : this.unknown.keySet()) {
/*  44 */         Object value = this.unknown.get(key);
/*  45 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*  48 */     writer.endObject();
/*     */   }
/*     */   @NotNull
/*     */   public List<SentrySample> getSamples() {
/*  52 */     return this.samples;
/*     */   }
/*     */   
/*     */   public void setSamples(@NotNull List<SentrySample> samples) {
/*  56 */     this.samples = samples;
/*     */   }
/*     */   @NotNull
/*     */   public List<List<Integer>> getStacks() {
/*  60 */     return this.stacks;
/*     */   }
/*     */   
/*     */   public void setStacks(@NotNull List<List<Integer>> stacks) {
/*  64 */     this.stacks = stacks;
/*     */   }
/*     */   @NotNull
/*     */   public List<SentryStackFrame> getFrames() {
/*  68 */     return this.frames;
/*     */   }
/*     */   
/*     */   public void setFrames(@NotNull List<SentryStackFrame> frames) {
/*  72 */     this.frames = frames;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, SentryThreadMetadata> getThreadMetadata() {
/*  76 */     return this.threadMetadata;
/*     */   }
/*     */   
/*     */   public void setThreadMetadata(@NotNull Map<String, SentryThreadMetadata> threadMetadata) {
/*  80 */     this.threadMetadata = threadMetadata;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  85 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  90 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String SAMPLES = "samples";
/*     */     public static final String STACKS = "stacks";
/*     */     public static final String FRAMES = "frames";
/*     */     public static final String THREAD_METADATA = "thread_metadata";
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryProfile> {
/*     */     @NotNull
/*     */     public SentryProfile deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 105 */       reader.beginObject();
/* 106 */       SentryProfile data = new SentryProfile();
/* 107 */       Map<String, Object> unknown = null;
/*     */       
/* 109 */       while (reader.peek() == JsonToken.NAME) {
/* 110 */         List<SentryStackFrame> jfrFrame; List<SentrySample> sentrySamples; Map<String, SentryThreadMetadata> threadMetadata; List<List<Integer>> jfrStacks; String nextName = reader.nextName();
/* 111 */         switch (nextName) {
/*     */           
/*     */           case "frames":
/* 114 */             jfrFrame = reader.nextListOrNull(logger, (JsonDeserializer)new SentryStackFrame.Deserializer());
/* 115 */             if (jfrFrame != null) {
/* 116 */               data.frames = jfrFrame;
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "samples":
/* 121 */             sentrySamples = reader.nextListOrNull(logger, new SentrySample.Deserializer());
/* 122 */             if (sentrySamples != null) {
/* 123 */               data.samples = sentrySamples;
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "thread_metadata":
/* 128 */             threadMetadata = reader.nextMapOrNull(logger, new SentryThreadMetadata.Deserializer());
/* 129 */             if (threadMetadata != null) {
/* 130 */               data.threadMetadata = threadMetadata;
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "stacks":
/* 135 */             jfrStacks = (List<List<Integer>>)reader.nextOrNull(logger, new SentryProfile.NestedIntegerListDeserializer());
/* 136 */             if (jfrStacks != null) {
/* 137 */               data.stacks = jfrStacks;
/*     */             }
/*     */             continue;
/*     */         } 
/* 141 */         if (unknown == null) {
/* 142 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 144 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 148 */       data.setUnknown(unknown);
/* 149 */       reader.endObject();
/* 150 */       return data;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class NestedIntegerListDeserializer
/*     */     implements JsonDeserializer<List<List<Integer>>> {
/*     */     private NestedIntegerListDeserializer() {}
/*     */     
/*     */     @NotNull
/*     */     public List<List<Integer>> deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 160 */       List<List<Integer>> result = new ArrayList<>();
/* 161 */       reader.beginArray();
/* 162 */       while (reader.hasNext()) {
/* 163 */         List<Integer> innerList = new ArrayList<>();
/* 164 */         reader.beginArray();
/* 165 */         while (reader.hasNext()) {
/* 166 */           innerList.add(Integer.valueOf(reader.nextInt()));
/*     */         }
/* 168 */         reader.endArray();
/* 169 */         result.add(innerList);
/*     */       } 
/* 171 */       reader.endArray();
/* 172 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\profiling\SentryProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */