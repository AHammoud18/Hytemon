/*     */ package io.sentry.cache;
/*     */ 
/*     */ import io.sentry.Breadcrumb;
/*     */ import io.sentry.IScope;
/*     */ import io.sentry.ScopeObserverAdapter;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.SpanContext;
/*     */ import io.sentry.cache.tape.ObjectQueue;
/*     */ import io.sentry.cache.tape.QueueFile;
/*     */ import io.sentry.protocol.Contexts;
/*     */ import io.sentry.protocol.Request;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.util.LazyEvaluator;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PersistingScopeObserver
/*     */   extends ScopeObserverAdapter
/*     */ {
/*  38 */   private static final Charset UTF_8 = Charset.forName("UTF-8"); public static final String SCOPE_CACHE = ".scope-cache"; public static final String USER_FILENAME = "user.json"; public static final String BREADCRUMBS_FILENAME = "breadcrumbs.json";
/*     */   public static final String TAGS_FILENAME = "tags.json";
/*     */   public static final String EXTRAS_FILENAME = "extras.json";
/*     */   public static final String CONTEXTS_FILENAME = "contexts.json";
/*     */   public static final String REQUEST_FILENAME = "request.json";
/*     */   public static final String LEVEL_FILENAME = "level.json";
/*     */   public static final String FINGERPRINT_FILENAME = "fingerprint.json";
/*     */   public static final String TRANSACTION_FILENAME = "transaction.json";
/*     */   public static final String TRACE_FILENAME = "trace.json";
/*     */   public static final String REPLAY_FILENAME = "replay.json";
/*     */   @NotNull
/*     */   private SentryOptions options;
/*     */   @NotNull
/*     */   private final LazyEvaluator<ObjectQueue<Breadcrumb>> breadcrumbsQueue;
/*     */   
/*     */   public PersistingScopeObserver(@NotNull SentryOptions options) {
/*  54 */     this.breadcrumbsQueue = new LazyEvaluator(() -> {
/*     */           File cacheDir = CacheUtils.ensureCacheDir(this.options, ".scope-cache");
/*     */           
/*     */           if (cacheDir == null) {
/*     */             this.options.getLogger().log(SentryLevel.INFO, "Cache dir is not set, cannot store in scope cache", new Object[0]);
/*     */             
/*     */             return (LazyEvaluator.Evaluator)ObjectQueue.createEmpty();
/*     */           } 
/*     */           
/*     */           QueueFile queueFile = null;
/*     */           File file = new File(cacheDir, "breadcrumbs.json");
/*     */           try {
/*     */             try {
/*     */               queueFile = (new QueueFile.Builder(file)).size(this.options.getMaxBreadcrumbs()).build();
/*  68 */             } catch (IOException e) {
/*     */               file.delete();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               queueFile = (new QueueFile.Builder(file)).size(this.options.getMaxBreadcrumbs()).build();
/*     */             } 
/*  78 */           } catch (IOException e) {
/*     */             this.options.getLogger().log(SentryLevel.ERROR, "Failed to create breadcrumbs queue", e);
/*     */             
/*     */             return (LazyEvaluator.Evaluator)ObjectQueue.createEmpty();
/*     */           } 
/*     */           
/*     */           return (LazyEvaluator.Evaluator)ObjectQueue.create(queueFile, new ObjectQueue.Converter<Breadcrumb>()
/*     */               {
/*     */                 @Nullable
/*     */                 public Breadcrumb from(byte[] source)
/*     */                 {
/*     */                   
/*  90 */                   try { Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(source), PersistingScopeObserver.UTF_8)); 
/*  91 */                     try { Breadcrumb breadcrumb = (Breadcrumb)PersistingScopeObserver.this.options.getSerializer().deserialize(reader, Breadcrumb.class);
/*  92 */                       reader.close(); return breadcrumb; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/*  93 */                   { PersistingScopeObserver.this.options.getLogger().log(SentryLevel.ERROR, e, "Error reading entity from scope cache", new Object[0]);
/*     */                     
/*  95 */                     return null; }
/*     */                 
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public void toStream(Breadcrumb value, OutputStream sink) throws IOException {
/* 101 */                   Writer writer = new BufferedWriter(new OutputStreamWriter(sink, PersistingScopeObserver.UTF_8)); 
/* 102 */                   try { PersistingScopeObserver.this.options.getSerializer().serialize(value, writer);
/* 103 */                     writer.close(); }
/*     */                   catch (Throwable throwable) { try { writer.close(); }
/*     */                     catch (Throwable throwable1)
/*     */                     { throwable.addSuppressed(throwable1); }
/*     */                      throw throwable; }
/*     */                    } });
/* 109 */         }); this.options = options;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUser(@Nullable User user) {
/* 114 */     serializeToDisk(() -> {
/*     */           if (user == null) {
/*     */             delete("user.json");
/*     */           } else {
/*     */             store(user, "user.json");
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb crumb) {
/* 126 */     serializeToDisk(() -> {
/*     */           
/*     */           try {
/*     */             ((ObjectQueue)this.breadcrumbsQueue.getValue()).add(crumb);
/* 130 */           } catch (IOException e) {
/*     */             this.options.getLogger().log(SentryLevel.ERROR, "Failed to add breadcrumb to file queue", e);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBreadcrumbs(@NotNull Collection<Breadcrumb> breadcrumbs) {
/* 138 */     if (breadcrumbs.isEmpty())
/*     */     {
/*     */       
/* 141 */       serializeToDisk(() -> {
/*     */             
/*     */             try {
/*     */               ((ObjectQueue)this.breadcrumbsQueue.getValue()).clear();
/* 145 */             } catch (IOException e) {
/*     */               this.options.getLogger().log(SentryLevel.ERROR, "Failed to clear breadcrumbs from file queue", e);
/*     */             } 
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTags(@NotNull Map<String, String> tags) {
/* 154 */     serializeToDisk(() -> store(tags, "tags.json"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExtras(@NotNull Map<String, Object> extras) {
/* 159 */     serializeToDisk(() -> store(extras, "extras.json"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRequest(@Nullable Request request) {
/* 164 */     serializeToDisk(() -> {
/*     */           if (request == null) {
/*     */             delete("request.json");
/*     */           } else {
/*     */             store(request, "request.json");
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFingerprint(@NotNull Collection<String> fingerprint) {
/* 176 */     serializeToDisk(() -> store(fingerprint, "fingerprint.json"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {
/* 181 */     serializeToDisk(() -> {
/*     */           if (level == null) {
/*     */             delete("level.json");
/*     */           } else {
/*     */             store(level, "level.json");
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransaction(@Nullable String transaction) {
/* 193 */     serializeToDisk(() -> {
/*     */           if (transaction == null) {
/*     */             delete("transaction.json");
/*     */           } else {
/*     */             store(transaction, "transaction.json");
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTrace(@Nullable SpanContext spanContext, @NotNull IScope scope) {
/* 205 */     serializeToDisk(() -> {
/*     */           if (spanContext == null) {
/*     */             store(scope.getPropagationContext().toSpanContext(), "trace.json");
/*     */           } else {
/*     */             store(spanContext, "trace.json");
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContexts(@NotNull Contexts contexts) {
/* 219 */     serializeToDisk(() -> store(contexts, "contexts.json"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReplayId(@NotNull SentryId replayId) {
/* 224 */     serializeToDisk(() -> store(replayId, "replay.json"));
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeToDisk(@NotNull Runnable task) {
/* 229 */     if (!this.options.isEnableScopePersistence()) {
/*     */       return;
/*     */     }
/* 232 */     if (Thread.currentThread().getName().contains("SentryExecutor")) {
/*     */       
/*     */       try {
/* 235 */         task.run();
/* 236 */       } catch (Throwable e) {
/* 237 */         this.options.getLogger().log(SentryLevel.ERROR, "Serialization task failed", e);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 243 */       this.options
/* 244 */         .getExecutorService()
/* 245 */         .submit(() -> {
/*     */             
/*     */             try {
/*     */               task.run();
/* 249 */             } catch (Throwable e) {
/*     */               this.options.getLogger().log(SentryLevel.ERROR, "Serialization task failed", e);
/*     */             } 
/*     */           });
/* 253 */     } catch (Throwable e) {
/* 254 */       this.options.getLogger().log(SentryLevel.ERROR, "Serialization task could not be scheduled", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> void store(@NotNull T entity, @NotNull String fileName) {
/* 259 */     store(this.options, entity, fileName);
/*     */   }
/*     */   
/*     */   private void delete(@NotNull String fileName) {
/* 263 */     CacheUtils.delete(this.options, ".scope-cache", fileName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> void store(@NotNull SentryOptions options, @NotNull T entity, @NotNull String fileName) {
/* 270 */     CacheUtils.store(options, entity, ".scope-cache", fileName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T read(@NotNull SentryOptions options, @NotNull String fileName, @NotNull Class<T> clazz) {
/* 277 */     if (fileName.equals("breadcrumbs.json")) {
/*     */       try {
/* 279 */         return clazz.cast(((ObjectQueue)this.breadcrumbsQueue.getValue()).asList());
/* 280 */       } catch (IOException e) {
/* 281 */         options.getLogger().log(SentryLevel.ERROR, "Unable to read serialized breadcrumbs from QueueFile", new Object[0]);
/* 282 */         return null;
/*     */       } 
/*     */     }
/* 285 */     return CacheUtils.read(options, ".scope-cache", fileName, clazz, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetCache() {
/*     */     try {
/* 295 */       ((ObjectQueue)this.breadcrumbsQueue.getValue()).clear();
/* 296 */     } catch (IOException e) {
/* 297 */       this.options.getLogger().log(SentryLevel.ERROR, "Failed to clear breadcrumbs from file queue", e);
/*     */     } 
/*     */ 
/*     */     
/* 301 */     delete("user.json");
/* 302 */     delete("level.json");
/* 303 */     delete("request.json");
/* 304 */     delete("fingerprint.json");
/* 305 */     delete("contexts.json");
/* 306 */     delete("extras.json");
/* 307 */     delete("tags.json");
/* 308 */     delete("trace.json");
/* 309 */     delete("transaction.json");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\PersistingScopeObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */