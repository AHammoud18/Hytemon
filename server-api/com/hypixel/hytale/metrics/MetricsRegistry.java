/*     */ package com.hypixel.hytale.metrics;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.backend.HytaleFileHandler;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.LocalDateTime;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.CheckForNull;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.codecs.BsonDocumentCodec;
/*     */ import org.bson.codecs.EncoderContext;
/*     */ import org.bson.json.JsonMode;
/*     */ import org.bson.json.JsonWriter;
/*     */ import org.bson.json.JsonWriterSettings;
/*     */ import org.bson.json.StrictJsonWriter;
/*     */ 
/*     */ public class MetricsRegistry<T>
/*     */   implements Codec<T>
/*     */ {
/*  36 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */   
/*     */   public static final JsonWriterSettings JSON_SETTINGS;
/*     */ 
/*     */   
/*     */   static {
/*  43 */     JSON_SETTINGS = JsonWriterSettings.builder().outputMode(JsonMode.STRICT).indent(false).newLineCharacters("\n").int64Converter((value, writer) -> writer.writeNumber(Long.toString(value.longValue()))).build();
/*     */   }
/*  45 */   private static final EncoderContext ENCODER_CONTEXT = EncoderContext.builder().build();
/*  46 */   private static final BsonDocumentCodec BSON_DOCUMENT_CODEC = new BsonDocumentCodec();
/*     */   
/*     */   @Nullable
/*     */   private final Function<T, MetricProvider> appendFunc;
/*     */   
/*  51 */   private final StampedLock lock = new StampedLock();
/*  52 */   private final Map<String, Metric<T, ?>> map = (Map<String, Metric<T, ?>>)new Object2ObjectLinkedOpenHashMap();
/*     */   
/*     */   public MetricsRegistry() {
/*  55 */     this.appendFunc = null;
/*     */   }
/*     */   
/*     */   public MetricsRegistry(Function<T, MetricProvider> appendFunc) {
/*  59 */     this.appendFunc = appendFunc;
/*     */   }
/*     */   
/*     */   public MetricsRegistry<T> register(String id, MetricsRegistry<Void> metricsRegistry) {
/*  63 */     long stamp = this.lock.writeLock();
/*     */     try {
/*  65 */       if (this.map.putIfAbsent(id, new Metric<>(null, metricsRegistry)) != null) throw new IllegalArgumentException("Metric already registered: " + id); 
/*     */     } finally {
/*  67 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*  69 */     return this;
/*     */   }
/*     */   
/*     */   public <R> MetricsRegistry<T> register(String id, Function<T, R> func, Codec<R> codec) {
/*  73 */     long stamp = this.lock.writeLock();
/*     */     try {
/*  75 */       if (this.map.putIfAbsent(id, new Metric<>(func, codec)) != null) throw new IllegalArgumentException("Metric already registered: " + id); 
/*     */     } finally {
/*  77 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*  79 */     return this;
/*     */   }
/*     */   
/*     */   public <R extends MetricProvider> MetricsRegistry<T> register(String id, @Nonnull Function<T, R> func) {
/*  83 */     return register(id, func.andThen(r -> (r == null) ? null : r.toMetricResults()), MetricResults.CODEC);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public <R> MetricsRegistry<T> register(String id, Function<T, R> func, Function<R, MetricsRegistry<R>> codecFunc) {
/*  88 */     long stamp = this.lock.writeLock();
/*     */     try {
/*  90 */       if (this.map.putIfAbsent(id, new Metric<>(func, codecFunc)) != null) throw new IllegalArgumentException("Metric already registered: " + id); 
/*     */     } finally {
/*  92 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public T decode(BsonValue bsonValue, ExtraInfo extraInfo) {
/*  99 */     throw new UnsupportedOperationException("Not implemented");
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue encode(T t, ExtraInfo extraInfo) {
/* 104 */     BsonDocument document = new BsonDocument();
/* 105 */     long stamp = this.lock.readLock();
/*     */     try {
/* 107 */       for (Map.Entry<String, Metric<T, ?>> entry : this.map.entrySet()) {
/* 108 */         String key = entry.getKey();
/* 109 */         BsonValue value = ((Metric)entry.getValue()).encode(t, extraInfo);
/* 110 */         if (value != null) document.put(key, value); 
/*     */       } 
/*     */     } finally {
/* 113 */       this.lock.unlockRead(stamp);
/*     */     } 
/* 115 */     if (this.appendFunc != null) {
/* 116 */       MetricProvider metricProvider = this.appendFunc.apply(t);
/* 117 */       if (metricProvider != null) {
/* 118 */         MetricResults metricResults = metricProvider.toMetricResults();
/* 119 */         if (metricResults != null) document.putAll((Map)metricResults.getBson()); 
/*     */       } 
/*     */     } 
/* 122 */     return (BsonValue)document;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Schema toSchema(@Nonnull SchemaContext context) {
/* 128 */     throw new UnsupportedOperationException("Not implemented");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public MetricResults toMetricResults(T t) {
/* 133 */     return new MetricResults(dumpToBson(t).asDocument());
/*     */   }
/*     */   
/*     */   public BsonValue dumpToBson(T t) {
/* 137 */     ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 138 */     BsonDocument bson = encode(t, extraInfo).asDocument();
/* 139 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/* 140 */     return (BsonValue)bson;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Path dumpToJson(T t) throws IOException {
/* 145 */     Path path = createDumpPath(".dump.json");
/* 146 */     dumpToJson(path, t);
/* 147 */     return path;
/*     */   }
/*     */   
/*     */   public void dumpToJson(@Nonnull Path path, T t) throws IOException {
/* 151 */     BsonValue bson = dumpToBson(t);
/*     */     
/* 153 */     BufferedWriter writer = Files.newBufferedWriter(path, new java.nio.file.OpenOption[0]); 
/* 154 */     try { BSON_DOCUMENT_CODEC.encode((BsonWriter)new JsonWriter(writer, JSON_SETTINGS), bson.asDocument(), ENCODER_CONTEXT);
/* 155 */       if (writer != null) writer.close();  }
/*     */     catch (Throwable throwable) { if (writer != null)
/*     */         try { writer.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 160 */      } @Nonnull public static Path createDumpPath(@Nullable String ext) throws IOException { return createDumpPath((String)null, ext); }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Path createDumpPath(@Nonnull Path dir, @Nullable String ext) {
/* 165 */     return createDatePath(dir, null, ext);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Path createDumpPath(@Nullable String prefix, @Nullable String ext) throws IOException {
/* 170 */     Path path = Paths.get("dumps", new String[0]);
/* 171 */     if (!Files.exists(path, new java.nio.file.LinkOption[0])) Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
/*     */     
/* 173 */     return createDatePath(path, prefix, ext);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Path createDatePath(@Nonnull Path dir, @Nullable String prefix, @Nullable String suffix) {
/* 178 */     String name = HytaleFileHandler.LOG_FILE_DATE_FORMAT.format(LocalDateTime.now());
/* 179 */     if (prefix != null) name = prefix + prefix;
/*     */     
/* 181 */     Path file = (suffix != null) ? dir.resolve(name + name) : dir.resolve(name);
/* 182 */     int i = 0;
/* 183 */     while (Files.exists(file, new java.nio.file.LinkOption[0])) {
/* 184 */       if (suffix != null) {
/* 185 */         file = dir.resolve(name + "_" + name + i++); continue;
/*     */       } 
/* 187 */       file = dir.resolve(name + "_" + name);
/*     */     } 
/*     */     
/* 190 */     return file;
/*     */   }
/*     */   
/*     */   private static class Metric<T, R>
/*     */   {
/*     */     @Nullable
/*     */     private final Function<T, R> func;
/*     */     @CheckForNull
/*     */     private final Codec<R> codec;
/*     */     @CheckForNull
/*     */     private final Function<R, MetricsRegistry<R>> codecFunc;
/*     */     
/*     */     public Metric(@Nullable Function<T, R> func, @Nullable Codec<R> codec) {
/* 203 */       this.func = func;
/* 204 */       this.codec = codec;
/* 205 */       this.codecFunc = null;
/*     */     }
/*     */     
/*     */     public Metric(@Nullable Function<T, R> func, @Nullable Function<R, MetricsRegistry<R>> codecFunc) {
/* 209 */       this.func = func;
/* 210 */       this.codec = null;
/* 211 */       this.codecFunc = codecFunc;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public BsonValue encode(T t, ExtraInfo extraInfo) {
/* 216 */       if (this.func == null) {
/* 217 */         assert this.codec != null;
/* 218 */         return this.codec.encode(null, extraInfo);
/*     */       } 
/*     */       
/* 221 */       R value = this.func.apply(t);
/* 222 */       return (value == null) ? null : getCodec(value).encode(value, extraInfo);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Codec<R> getCodec(R value) {
/* 227 */       if (this.codec != null) return this.codec; 
/* 228 */       assert this.codecFunc != null;
/* 229 */       return this.codecFunc.apply(value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\metrics\MetricsRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */