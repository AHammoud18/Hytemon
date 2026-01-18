/*     */ package io.sentry.cache;
/*     */ 
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.Hint;
/*     */ import io.sentry.ISentryLifecycleToken;
/*     */ import io.sentry.ISerializer;
/*     */ import io.sentry.SentryCrashLastRunState;
/*     */ import io.sentry.SentryEnvelope;
/*     */ import io.sentry.SentryEnvelopeItem;
/*     */ import io.sentry.SentryItemType;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.SentryUUID;
/*     */ import io.sentry.Session;
/*     */ import io.sentry.UncaughtExceptionHandlerIntegration;
/*     */ import io.sentry.hints.AbnormalExit;
/*     */ import io.sentry.hints.SessionEnd;
/*     */ import io.sentry.hints.SessionStart;
/*     */ import io.sentry.transport.NoOpEnvelopeCache;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import io.sentry.util.HintUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ @Internal
/*     */ public class EnvelopeCache
/*     */   extends CacheStrategy
/*     */   implements IEnvelopeCache
/*     */ {
/*     */   public static final String SUFFIX_ENVELOPE_FILE = ".envelope";
/*     */   public static final String PREFIX_CURRENT_SESSION_FILE = "session";
/*     */   public static final String PREFIX_PREVIOUS_SESSION_FILE = "previous_session";
/*     */   static final String SUFFIX_SESSION_FILE = ".json";
/*     */   public static final String CRASH_MARKER_FILE = "last_crash";
/*     */   public static final String NATIVE_CRASH_MARKER_FILE = ".sentry-native/last_crash";
/*     */   public static final String STARTUP_CRASH_MARKER_FILE = "startup_crash";
/*     */   private final CountDownLatch previousSessionLatch;
/*     */   @NotNull
/*  74 */   private final Map<SentryEnvelope, String> fileNameMap = new WeakHashMap<>(); @NotNull
/*  75 */   protected final AutoClosableReentrantLock cacheLock = new AutoClosableReentrantLock(); @NotNull
/*  76 */   protected final AutoClosableReentrantLock sessionLock = new AutoClosableReentrantLock();
/*     */   @NotNull
/*     */   public static IEnvelopeCache create(@NotNull SentryOptions options) {
/*  79 */     String cacheDirPath = options.getCacheDirPath();
/*  80 */     int maxCacheItems = options.getMaxCacheItems();
/*  81 */     if (cacheDirPath == null) {
/*  82 */       options.getLogger().log(SentryLevel.WARNING, "cacheDirPath is null, returning NoOpEnvelopeCache", new Object[0]);
/*  83 */       return (IEnvelopeCache)NoOpEnvelopeCache.getInstance();
/*     */     } 
/*  85 */     return new EnvelopeCache(options, cacheDirPath, maxCacheItems);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnvelopeCache(@NotNull SentryOptions options, @NotNull String cacheDirPath, int maxCacheItems) {
/*  93 */     super(options, cacheDirPath, maxCacheItems);
/*  94 */     this.previousSessionLatch = new CountDownLatch(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void store(@NotNull SentryEnvelope envelope, @NotNull Hint hint) {
/* 100 */     storeInternal(envelope, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean storeEnvelope(@NotNull SentryEnvelope envelope, @NotNull Hint hint) {
/* 105 */     return storeInternal(envelope, hint);
/*     */   }
/*     */   
/*     */   private boolean storeInternal(@NotNull SentryEnvelope envelope, @NotNull Hint hint) {
/* 109 */     Objects.requireNonNull(envelope, "Envelope is required.");
/*     */     
/* 111 */     rotateCacheIfNeeded(allEnvelopeFiles());
/*     */     
/* 113 */     File currentSessionFile = getCurrentSessionFile(this.directory.getAbsolutePath());
/* 114 */     File previousSessionFile = getPreviousSessionFile(this.directory.getAbsolutePath());
/*     */     
/* 116 */     if (HintUtils.hasType(hint, SessionEnd.class) && 
/* 117 */       !currentSessionFile.delete()) {
/* 118 */       this.options.getLogger().log(SentryLevel.WARNING, "Current envelope doesn't exist.", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 122 */     if (HintUtils.hasType(hint, AbnormalExit.class)) {
/* 123 */       tryEndPreviousSession(hint);
/*     */     }
/*     */     
/* 126 */     if (HintUtils.hasType(hint, SessionStart.class)) {
/* 127 */       movePreviousSession(currentSessionFile, previousSessionFile);
/* 128 */       updateCurrentSession(currentSessionFile, envelope);
/*     */       
/* 130 */       boolean crashedLastRun = false;
/* 131 */       File crashMarkerFile = new File(this.options.getCacheDirPath(), ".sentry-native/last_crash");
/* 132 */       if (crashMarkerFile.exists()) {
/* 133 */         crashedLastRun = true;
/*     */       }
/*     */ 
/*     */       
/* 137 */       if (!crashedLastRun) {
/* 138 */         File javaCrashMarkerFile = new File(this.options.getCacheDirPath(), "last_crash");
/* 139 */         if (javaCrashMarkerFile.exists()) {
/* 140 */           this.options
/* 141 */             .getLogger()
/* 142 */             .log(SentryLevel.INFO, "Crash marker file exists, crashedLastRun will return true.", new Object[0]);
/*     */           
/* 144 */           crashedLastRun = true;
/* 145 */           if (!javaCrashMarkerFile.delete()) {
/* 146 */             this.options
/* 147 */               .getLogger()
/* 148 */               .log(SentryLevel.ERROR, "Failed to delete the crash marker file. %s.", new Object[] {
/*     */ 
/*     */                   
/* 151 */                   javaCrashMarkerFile.getAbsolutePath()
/*     */                 });
/*     */           }
/*     */         } 
/*     */       } 
/* 156 */       SentryCrashLastRunState.getInstance().setCrashedLastRun(crashedLastRun);
/*     */       
/* 158 */       flushPreviousSession();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     File envelopeFile = getEnvelopeFile(envelope);
/* 165 */     if (envelopeFile.exists()) {
/* 166 */       this.options
/* 167 */         .getLogger()
/* 168 */         .log(SentryLevel.WARNING, "Not adding Envelope to offline storage because it already exists: %s", new Object[] {
/*     */ 
/*     */             
/* 171 */             envelopeFile.getAbsolutePath() });
/* 172 */       return true;
/*     */     } 
/* 174 */     this.options
/* 175 */       .getLogger()
/* 176 */       .log(SentryLevel.DEBUG, "Adding Envelope to offline storage: %s", new Object[] { envelopeFile.getAbsolutePath() });
/*     */ 
/*     */     
/* 179 */     boolean didWriteToDisk = writeEnvelopeToDisk(envelopeFile, envelope);
/*     */ 
/*     */     
/* 182 */     if (HintUtils.hasType(hint, UncaughtExceptionHandlerIntegration.UncaughtExceptionHint.class)) {
/* 183 */       writeCrashMarkerFile();
/*     */     }
/* 185 */     return didWriteToDisk;
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
/*     */   private void tryEndPreviousSession(@NotNull Hint hint) {
/* 199 */     Object sdkHint = HintUtils.getSentrySdkHint(hint);
/* 200 */     if (sdkHint instanceof AbnormalExit) {
/* 201 */       File previousSessionFile = getPreviousSessionFile(this.directory.getAbsolutePath());
/*     */       
/* 203 */       if (previousSessionFile.exists()) {
/* 204 */         this.options.getLogger().log(SentryLevel.WARNING, "Previous session is not ended, we'd need to end it.", new Object[0]);
/*     */         
/* 206 */         try { Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(previousSessionFile), UTF_8));
/*     */ 
/*     */           
/* 209 */           try { Session session = (Session)((ISerializer)this.serializer.getValue()).deserialize(reader, Session.class);
/* 210 */             if (session != null)
/* 211 */             { AbnormalExit abnormalHint = (AbnormalExit)sdkHint;
/* 212 */               Long abnormalExitTimestamp = abnormalHint.timestamp();
/* 213 */               Date timestamp = null;
/*     */               
/* 215 */               if (abnormalExitTimestamp != null)
/* 216 */               { timestamp = DateUtils.getDateTime(abnormalExitTimestamp.longValue());
/*     */                 
/* 218 */                 Date sessionStart = session.getStarted();
/* 219 */                 if (sessionStart == null || timestamp.before(sessionStart))
/* 220 */                 { this.options
/* 221 */                     .getLogger()
/* 222 */                     .log(SentryLevel.WARNING, "Abnormal exit happened before previous session start, not ending the session.", new Object[0]);
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
/* 236 */                   reader.close(); return; }  }  String abnormalMechanism = abnormalHint.mechanism(); session.update(Session.State.Abnormal, null, true, abnormalMechanism); session.end(timestamp); writeSessionToDisk(previousSessionFile, session); }  reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 237 */         { this.options.getLogger().log(SentryLevel.ERROR, "Error processing previous session.", e); }
/*     */       
/*     */       } else {
/* 240 */         this.options.getLogger().log(SentryLevel.DEBUG, "No previous session file to end.", new Object[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeCrashMarkerFile() {
/* 246 */     File crashMarkerFile = new File(this.options.getCacheDirPath(), "last_crash"); 
/* 247 */     try { OutputStream outputStream = new FileOutputStream(crashMarkerFile); 
/* 248 */       try { String timestamp = DateUtils.getTimestamp(DateUtils.getCurrentDateTime());
/* 249 */         outputStream.write(timestamp.getBytes(UTF_8));
/* 250 */         outputStream.flush();
/* 251 */         outputStream.close(); } catch (Throwable throwable) { try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 252 */     { this.options.getLogger().log(SentryLevel.ERROR, "Error writing the crash marker file to the disk", e); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateCurrentSession(@NotNull File currentSessionFile, @NotNull SentryEnvelope envelope) {
/* 258 */     Iterable<SentryEnvelopeItem> items = envelope.getItems();
/*     */ 
/*     */     
/* 261 */     if (items.iterator().hasNext()) {
/* 262 */       SentryEnvelopeItem item = items.iterator().next();
/*     */       
/* 264 */       if (SentryItemType.Session.equals(item.getHeader().getType())) {
/*     */ 
/*     */         
/* 267 */         try { Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(item.getData()), UTF_8)); 
/* 268 */           try { Session session = (Session)((ISerializer)this.serializer.getValue()).deserialize(reader, Session.class);
/* 269 */             if (session == null) {
/* 270 */               this.options
/* 271 */                 .getLogger()
/* 272 */                 .log(SentryLevel.ERROR, "Item of type %s returned null by the parser.", new Object[] {
/*     */ 
/*     */                     
/* 275 */                     item.getHeader().getType() });
/*     */             } else {
/* 277 */               writeSessionToDisk(currentSessionFile, session);
/*     */             } 
/* 279 */             reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 280 */         { this.options.getLogger().log(SentryLevel.ERROR, "Item failed to process.", e); }
/*     */       
/*     */       } else {
/* 283 */         this.options
/* 284 */           .getLogger()
/* 285 */           .log(SentryLevel.INFO, "Current envelope has a different envelope type %s", new Object[] {
/*     */ 
/*     */               
/* 288 */               item.getHeader().getType() });
/*     */       } 
/*     */     } else {
/* 291 */       this.options
/* 292 */         .getLogger()
/* 293 */         .log(SentryLevel.INFO, "Current envelope %s is empty", new Object[] { currentSessionFile.getAbsolutePath() });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean writeEnvelopeToDisk(@NotNull File file, @NotNull SentryEnvelope envelope) {
/* 299 */     if (file.exists()) {
/* 300 */       this.options
/* 301 */         .getLogger()
/* 302 */         .log(SentryLevel.DEBUG, "Overwriting envelope to offline storage: %s", new Object[] { file.getAbsolutePath() });
/* 303 */       if (!file.delete()) {
/* 304 */         this.options.getLogger().log(SentryLevel.ERROR, "Failed to delete: %s", new Object[] { file.getAbsolutePath() });
/*     */       }
/*     */     } 
/*     */     
/* 308 */     try { OutputStream outputStream = new FileOutputStream(file); 
/* 309 */       try { ((ISerializer)this.serializer.getValue()).serialize(envelope, outputStream);
/* 310 */         outputStream.close(); } catch (Throwable throwable) { try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 311 */     { this.options
/* 312 */         .getLogger()
/* 313 */         .log(SentryLevel.ERROR, e, "Error writing Envelope %s to offline storage", new Object[] { file.getAbsolutePath() });
/* 314 */       return false; }
/*     */     
/* 316 */     return true;
/*     */   }
/*     */   private void writeSessionToDisk(@NotNull File file, @NotNull Session session) {
/*     */     
/* 320 */     try { OutputStream outputStream = new FileOutputStream(file); 
/* 321 */       try { Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, UTF_8)); 
/* 322 */         try { this.options
/* 323 */             .getLogger()
/* 324 */             .log(SentryLevel.DEBUG, "Overwriting session to offline storage: %s", new Object[] { session.getSessionId() });
/*     */           
/* 326 */           ((ISerializer)this.serializer.getValue()).serialize(session, writer);
/* 327 */           writer.close(); } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  outputStream.close(); } catch (Throwable throwable) { try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 328 */     { this.options
/* 329 */         .getLogger()
/* 330 */         .log(SentryLevel.ERROR, e, "Error writing Session to offline storage: %s", new Object[] { session.getSessionId() }); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void discard(@NotNull SentryEnvelope envelope) {
/* 336 */     Objects.requireNonNull(envelope, "Envelope is required.");
/*     */     
/* 338 */     File envelopeFile = getEnvelopeFile(envelope);
/* 339 */     if (envelopeFile.delete()) {
/* 340 */       this.options
/* 341 */         .getLogger()
/* 342 */         .log(SentryLevel.DEBUG, "Discarding envelope from cache: %s", new Object[] { envelopeFile.getAbsolutePath() });
/*     */     } else {
/* 344 */       this.options
/* 345 */         .getLogger()
/* 346 */         .log(SentryLevel.DEBUG, "Envelope was not cached or could not be deleted: %s", new Object[] {
/*     */ 
/*     */             
/* 349 */             envelopeFile.getAbsolutePath()
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private File getEnvelopeFile(@NotNull SentryEnvelope envelope) {
/* 361 */     ISentryLifecycleToken ignored = this.cacheLock.acquire(); 
/*     */     try { String fileName;
/* 363 */       if (this.fileNameMap.containsKey(envelope)) {
/* 364 */         fileName = this.fileNameMap.get(envelope);
/*     */       } else {
/* 366 */         fileName = SentryUUID.generateSentryId() + ".envelope";
/* 367 */         this.fileNameMap.put(envelope, fileName);
/*     */       } 
/*     */       
/* 370 */       File file = new File(this.directory.getAbsolutePath(), fileName);
/* 371 */       if (ignored != null) ignored.close();  return file; } catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 375 */      } @NotNull public static File getCurrentSessionFile(@NotNull String cacheDirPath) { return new File(cacheDirPath, "session.json"); }
/*     */   
/*     */   @NotNull
/*     */   public static File getPreviousSessionFile(@NotNull String cacheDirPath) {
/* 379 */     return new File(cacheDirPath, "previous_session.json");
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Iterator<SentryEnvelope> iterator() {
/* 384 */     File[] allCachedEnvelopes = allEnvelopeFiles();
/*     */     
/* 386 */     List<SentryEnvelope> ret = new ArrayList<>(allCachedEnvelopes.length);
/*     */     
/* 388 */     for (File file : allCachedEnvelopes) { 
/* 389 */       try { InputStream is = new BufferedInputStream(new FileInputStream(file));
/*     */         
/* 391 */         try { ret.add(((ISerializer)this.serializer.getValue()).deserializeEnvelope(is));
/* 392 */           is.close(); } catch (Throwable throwable) { try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (FileNotFoundException e)
/* 393 */       { this.options
/* 394 */           .getLogger()
/* 395 */           .log(SentryLevel.DEBUG, "Envelope file '%s' disappeared while converting all cached files to envelopes.", new Object[] {
/*     */ 
/*     */               
/* 398 */               file.getAbsolutePath() }); }
/* 399 */       catch (IOException e)
/* 400 */       { this.options
/* 401 */           .getLogger()
/* 402 */           .log(SentryLevel.ERROR, 
/*     */             
/* 404 */             String.format("Error while reading cached envelope from file %s", new Object[] { file.getAbsolutePath() }), e); }
/*     */        }
/*     */ 
/*     */ 
/*     */     
/* 409 */     return ret.iterator();
/*     */   }
/*     */   @NotNull
/*     */   private File[] allEnvelopeFiles() {
/* 413 */     if (isDirectoryValid()) {
/*     */ 
/*     */       
/* 416 */       File[] files = this.directory.listFiles((__, fileName) -> fileName.endsWith(".envelope"));
/* 417 */       if (files != null) {
/* 418 */         return files;
/*     */       }
/*     */     } 
/* 421 */     return new File[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean waitPreviousSessionFlush() {
/*     */     try {
/* 427 */       return this.previousSessionLatch.await(this.options
/* 428 */           .getSessionFlushTimeoutMillis(), TimeUnit.MILLISECONDS);
/* 429 */     } catch (InterruptedException e) {
/* 430 */       Thread.currentThread().interrupt();
/* 431 */       this.options.getLogger().log(SentryLevel.DEBUG, "Timed out waiting for previous session to flush.", new Object[0]);
/*     */       
/* 433 */       return false;
/*     */     } 
/*     */   }
/*     */   public void flushPreviousSession() {
/* 437 */     this.previousSessionLatch.countDown();
/*     */   }
/*     */ 
/*     */   
/*     */   public void movePreviousSession(@NotNull File currentSessionFile, @NotNull File previousSessionFile) {
/* 442 */     ISentryLifecycleToken ignored = this.sessionLock.acquire(); try {
/* 443 */       if (previousSessionFile.exists()) {
/* 444 */         this.options.getLogger().log(SentryLevel.DEBUG, "Previous session file already exists, deleting it.", new Object[0]);
/* 445 */         if (!previousSessionFile.delete()) {
/* 446 */           this.options
/* 447 */             .getLogger()
/* 448 */             .log(SentryLevel.WARNING, "Unable to delete previous session file: %s", new Object[] { previousSessionFile });
/*     */         }
/*     */       } 
/*     */       
/* 452 */       if (currentSessionFile.exists()) {
/* 453 */         this.options.getLogger().log(SentryLevel.INFO, "Moving current session to previous session.", new Object[0]);
/*     */         
/*     */         try {
/* 456 */           boolean renamed = currentSessionFile.renameTo(previousSessionFile);
/* 457 */           if (!renamed) {
/* 458 */             this.options.getLogger().log(SentryLevel.WARNING, "Unable to move current session to previous session.", new Object[0]);
/*     */           }
/* 460 */         } catch (Throwable e) {
/* 461 */           this.options
/* 462 */             .getLogger()
/* 463 */             .log(SentryLevel.ERROR, "Error moving current session to previous session.", e);
/*     */         } 
/*     */       } 
/* 466 */       if (ignored != null) ignored.close(); 
/*     */     } catch (Throwable throwable) {
/*     */       if (ignored != null)
/*     */         try {
/*     */           ignored.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }  
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\EnvelopeCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */