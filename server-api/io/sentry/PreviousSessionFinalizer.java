/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.cache.EnvelopeCache;
/*     */ import io.sentry.cache.IEnvelopeCache;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Date;
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
/*     */ final class PreviousSessionFinalizer
/*     */   implements Runnable
/*     */ {
/*  32 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */   @NotNull
/*     */   private final IScopes scopes;
/*     */   
/*     */   PreviousSessionFinalizer(@NotNull SentryOptions options, @NotNull IScopes scopes) {
/*  39 */     this.options = options;
/*  40 */     this.scopes = scopes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  45 */     String cacheDirPath = this.options.getCacheDirPath();
/*  46 */     if (cacheDirPath == null) {
/*  47 */       this.options.getLogger().log(SentryLevel.INFO, "Cache dir is not set, not finalizing the previous session.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*  51 */     if (!this.options.isEnableAutoSessionTracking()) {
/*  52 */       this.options
/*  53 */         .getLogger()
/*  54 */         .log(SentryLevel.DEBUG, "Session tracking is disabled, bailing from previous session finalizer.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*  58 */     IEnvelopeCache cache = this.options.getEnvelopeDiskCache();
/*  59 */     if (cache instanceof EnvelopeCache && 
/*  60 */       !((EnvelopeCache)cache).waitPreviousSessionFlush()) {
/*  61 */       this.options
/*  62 */         .getLogger()
/*  63 */         .log(SentryLevel.WARNING, "Timed out waiting to flush previous session to its own file in session finalizer.", new Object[0]);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  70 */     File previousSessionFile = EnvelopeCache.getPreviousSessionFile(cacheDirPath);
/*  71 */     ISerializer serializer = this.options.getSerializer();
/*     */     
/*  73 */     if (previousSessionFile.exists()) {
/*  74 */       this.options.getLogger().log(SentryLevel.WARNING, "Current session is not ended, we'd need to end it.", new Object[0]);
/*     */       
/*  76 */       try { Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(previousSessionFile), UTF_8));
/*     */ 
/*     */ 
/*     */         
/*  80 */         try { Session session = serializer.<Session>deserialize(reader, Session.class);
/*  81 */           if (session == null) {
/*  82 */             this.options
/*  83 */               .getLogger()
/*  84 */               .log(SentryLevel.ERROR, "Stream from path %s resulted in a null envelope.", new Object[] {
/*     */ 
/*     */                   
/*  87 */                   previousSessionFile.getAbsolutePath() });
/*     */           } else {
/*  89 */             Date timestamp = null;
/*     */             
/*  91 */             File crashMarkerFile = new File(this.options.getCacheDirPath(), ".sentry-native/last_crash");
/*  92 */             if (crashMarkerFile.exists()) {
/*  93 */               this.options
/*  94 */                 .getLogger()
/*  95 */                 .log(SentryLevel.INFO, "Crash marker file exists, last Session is gonna be Crashed.", new Object[0]);
/*     */               
/*  97 */               timestamp = getTimestampFromCrashMarkerFile(crashMarkerFile);
/*     */               
/*  99 */               if (!crashMarkerFile.delete())
/* 100 */                 this.options
/* 101 */                   .getLogger()
/* 102 */                   .log(SentryLevel.ERROR, "Failed to delete the crash marker file. %s.", new Object[] {
/*     */ 
/*     */                       
/* 105 */                       crashMarkerFile.getAbsolutePath()
/*     */                     }); 
/* 107 */               session.update(Session.State.Crashed, null, true);
/*     */             } 
/*     */ 
/*     */             
/* 111 */             if (session.getAbnormalMechanism() == null) {
/* 112 */               session.end(timestamp);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 118 */             SentryEnvelope fromSession = SentryEnvelope.from(serializer, session, this.options.getSdkVersion());
/* 119 */             this.scopes.captureEnvelope(fromSession);
/*     */           } 
/* 121 */           reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 122 */       { this.options.getLogger().log(SentryLevel.ERROR, "Error processing previous session.", e); }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 127 */       if (!previousSessionFile.delete()) {
/* 128 */         this.options.getLogger().log(SentryLevel.WARNING, "Failed to delete the previous session file.", new Object[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Date getTimestampFromCrashMarkerFile(@NotNull File markerFile) {
/*     */     
/* 140 */     try { BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(markerFile), UTF_8));
/*     */       
/* 142 */       try { String timestamp = reader.readLine();
/* 143 */         this.options.getLogger().log(SentryLevel.DEBUG, "Crash marker file has %s timestamp.", new Object[] { timestamp });
/* 144 */         Date date = DateUtils.getDateTime(timestamp);
/* 145 */         reader.close(); return date; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 146 */     { this.options.getLogger().log(SentryLevel.ERROR, "Error reading the crash marker file.", e); }
/* 147 */     catch (IllegalArgumentException e)
/* 148 */     { this.options.getLogger().log(SentryLevel.ERROR, e, "Error converting the crash timestamp.", new Object[0]); }
/*     */     
/* 150 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\PreviousSessionFinalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */