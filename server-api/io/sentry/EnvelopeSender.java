/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.hints.Flushable;
/*     */ import io.sentry.hints.Retryable;
/*     */ import io.sentry.util.HintUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class EnvelopeSender
/*     */   extends DirectoryProcessor
/*     */   implements IEnvelopeSender
/*     */ {
/*     */   @NotNull
/*     */   private final IScopes scopes;
/*     */   @NotNull
/*     */   private final ISerializer serializer;
/*     */   @NotNull
/*     */   private final ILogger logger;
/*     */   
/*     */   public EnvelopeSender(@NotNull IScopes scopes, @NotNull ISerializer serializer, @NotNull ILogger logger, long flushTimeoutMillis, int maxQueueSize) {
/*  30 */     super(scopes, logger, flushTimeoutMillis, maxQueueSize);
/*  31 */     this.scopes = (IScopes)Objects.requireNonNull(scopes, "Scopes are required.");
/*  32 */     this.serializer = (ISerializer)Objects.requireNonNull(serializer, "Serializer is required.");
/*  33 */     this.logger = (ILogger)Objects.requireNonNull(logger, "Logger is required.");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processFile(@NotNull File file, @NotNull Hint hint) {
/*  38 */     if (!file.isFile()) {
/*  39 */       this.logger.log(SentryLevel.DEBUG, "'%s' is not a file.", new Object[] { file.getAbsolutePath() });
/*     */       
/*     */       return;
/*     */     } 
/*  43 */     if (!isRelevantFileName(file.getName())) {
/*  44 */       this.logger.log(SentryLevel.DEBUG, "File '%s' doesn't match extension expected.", new Object[] { file
/*  45 */             .getAbsolutePath() });
/*     */       
/*     */       return;
/*     */     } 
/*  49 */     if (!file.getParentFile().canWrite()) {
/*  50 */       this.logger.log(SentryLevel.WARNING, "File '%s' cannot be deleted so it will not be processed.", new Object[] { file
/*     */ 
/*     */             
/*  53 */             .getAbsolutePath() });
/*     */       return;
/*     */     } 
/*     */     
/*  57 */     try { InputStream is = new BufferedInputStream(new FileInputStream(file)); 
/*  58 */       try { SentryEnvelope envelope = this.serializer.deserializeEnvelope(is);
/*  59 */         if (envelope == null) {
/*  60 */           this.logger.log(SentryLevel.ERROR, "Failed to deserialize cached envelope %s", new Object[] { file
/*  61 */                 .getAbsolutePath() });
/*     */         } else {
/*  63 */           this.scopes.captureEnvelope(envelope, hint);
/*     */         } 
/*     */         
/*  66 */         HintUtils.runIfHasTypeLogIfNot(hint, Flushable.class, this.logger, flushable -> {
/*     */               if (!flushable.waitFlush()) {
/*     */                 this.logger.log(SentryLevel.WARNING, "Timed out waiting for envelope submission.", new Object[0]);
/*     */               }
/*     */             });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  75 */         is.close(); } catch (Throwable throwable) { try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (FileNotFoundException e)
/*  76 */     { this.logger.log(SentryLevel.ERROR, e, "File '%s' cannot be found.", new Object[] { file.getAbsolutePath() }); }
/*  77 */     catch (IOException e)
/*  78 */     { this.logger.log(SentryLevel.ERROR, e, "I/O on file '%s' failed.", new Object[] { file.getAbsolutePath() }); }
/*  79 */     catch (Throwable e)
/*  80 */     { this.logger.log(SentryLevel.ERROR, e, "Failed to capture cached envelope %s", new Object[] { file
/*  81 */             .getAbsolutePath() });
/*  82 */       HintUtils.runIfHasTypeLogIfNot(hint, Retryable.class, this.logger, retryable -> {
/*     */             retryable.setRetry(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             this.logger.log(SentryLevel.INFO, e, "File '%s' won't retry.", new Object[] { file.getAbsolutePath() });
/*     */           }); }
/*     */     finally
/*  92 */     { HintUtils.runIfHasTypeLogIfNot(hint, Retryable.class, this.logger, retryable -> {
/*     */             if (!retryable.isRetry()) {
/*     */               safeDelete(file, "after trying to capture it");
/*     */               this.logger.log(SentryLevel.DEBUG, "Deleted file %s.", new Object[] { file.getAbsolutePath() });
/*     */             } else {
/*     */               this.logger.log(SentryLevel.INFO, "File not deleted since retry was marked. %s.", new Object[] { file.getAbsolutePath() });
/*     */             } 
/*     */           }); }
/*     */   
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
/*     */   protected boolean isRelevantFileName(@NotNull String fileName) {
/* 112 */     return fileName.endsWith(".envelope");
/*     */   }
/*     */ 
/*     */   
/*     */   public void processEnvelopeFile(@NotNull String path, @NotNull Hint hint) {
/* 117 */     Objects.requireNonNull(path, "Path is required.");
/*     */     
/* 119 */     processFile(new File(path), hint);
/*     */   }
/*     */   
/*     */   private void safeDelete(@NotNull File file, @NotNull String errorMessageSuffix) {
/*     */     try {
/* 124 */       if (!file.delete()) {
/* 125 */         this.logger.log(SentryLevel.ERROR, "Failed to delete '%s' %s", new Object[] { file
/*     */ 
/*     */               
/* 128 */               .getAbsolutePath(), errorMessageSuffix });
/*     */       }
/*     */     }
/* 131 */     catch (Throwable e) {
/* 132 */       this.logger.log(SentryLevel.ERROR, e, "Failed to delete '%s' %s", new Object[] { file
/*     */ 
/*     */ 
/*     */             
/* 136 */             .getAbsolutePath(), errorMessageSuffix });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\EnvelopeSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */