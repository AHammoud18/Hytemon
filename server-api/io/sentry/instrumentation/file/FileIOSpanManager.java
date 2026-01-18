/*     */ package io.sentry.instrumentation.file;
/*     */ 
/*     */ import io.sentry.IScopes;
/*     */ import io.sentry.ISpan;
/*     */ import io.sentry.SentryIntegrationPackageStorage;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.SentryStackTraceFactory;
/*     */ import io.sentry.SpanStatus;
/*     */ import io.sentry.util.Platform;
/*     */ import io.sentry.util.StringUtils;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ final class FileIOSpanManager
/*     */ {
/*     */   @Nullable
/*     */   private final ISpan currentSpan;
/*     */   @Nullable
/*     */   private final File file;
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */   @NotNull
/*  26 */   private SpanStatus spanStatus = SpanStatus.OK;
/*     */   
/*     */   private long byteCount;
/*     */   
/*     */   @Nullable
/*     */   static ISpan startSpan(@NotNull IScopes scopes, @NotNull String op) {
/*  32 */     ISpan parent = Platform.isAndroid() ? (ISpan)scopes.getTransaction() : scopes.getSpan();
/*  33 */     return (parent != null) ? parent.startChild(op) : null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private final SentryStackTraceFactory stackTraceFactory;
/*     */   
/*     */   FileIOSpanManager(@Nullable ISpan currentSpan, @Nullable File file, @NotNull SentryOptions options) {
/*  40 */     this.currentSpan = currentSpan;
/*  41 */     this.file = file;
/*  42 */     this.options = options;
/*  43 */     this.stackTraceFactory = new SentryStackTraceFactory(options);
/*  44 */     SentryIntegrationPackageStorage.getInstance().addIntegration("FileIO");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <T> T performIO(@NotNull FileIOCallable<T> operation) throws IOException {
/*     */     try {
/*  56 */       T result = operation.call();
/*  57 */       if (result instanceof Integer) {
/*  58 */         int resUnboxed = ((Integer)result).intValue();
/*  59 */         if (resUnboxed != -1) {
/*  60 */           this.byteCount += resUnboxed;
/*     */         }
/*  62 */       } else if (result instanceof Long) {
/*  63 */         long resUnboxed = ((Long)result).longValue();
/*  64 */         if (resUnboxed != -1L) {
/*  65 */           this.byteCount += resUnboxed;
/*     */         }
/*     */       } 
/*  68 */       return result;
/*  69 */     } catch (IOException exception) {
/*  70 */       this.spanStatus = SpanStatus.INTERNAL_ERROR;
/*  71 */       if (this.currentSpan != null) {
/*  72 */         this.currentSpan.setThrowable(exception);
/*     */       }
/*  74 */       throw exception;
/*     */     } 
/*     */   }
/*     */   
/*     */   void finish(@NotNull Closeable delegate) throws IOException {
/*     */     try {
/*  80 */       delegate.close();
/*  81 */     } catch (IOException exception) {
/*  82 */       this.spanStatus = SpanStatus.INTERNAL_ERROR;
/*  83 */       if (this.currentSpan != null) {
/*  84 */         this.currentSpan.setThrowable(exception);
/*     */       }
/*  86 */       throw exception;
/*     */     } finally {
/*  88 */       finishSpan();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void finishSpan() {
/*  93 */     if (this.currentSpan != null) {
/*  94 */       String byteCountToString = StringUtils.byteCountToString(this.byteCount);
/*  95 */       if (this.file != null) {
/*  96 */         String description = getDescription(this.file);
/*  97 */         this.currentSpan.setDescription(description);
/*  98 */         if (this.options.isSendDefaultPii()) {
/*  99 */           this.currentSpan.setData("file.path", this.file.getAbsolutePath());
/*     */         }
/*     */       } else {
/* 102 */         this.currentSpan.setDescription(byteCountToString);
/*     */       } 
/* 104 */       this.currentSpan.setData("file.size", Long.valueOf(this.byteCount));
/* 105 */       boolean isMainThread = this.options.getThreadChecker().isMainThread();
/* 106 */       this.currentSpan.setData("blocked_main_thread", Boolean.valueOf(isMainThread));
/* 107 */       if (isMainThread) {
/* 108 */         this.currentSpan.setData("call_stack", this.stackTraceFactory
/* 109 */             .getInAppCallStack());
/*     */       }
/* 111 */       this.currentSpan.finish(this.spanStatus);
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   private String getDescription(@NotNull File file) {
/* 116 */     String byteCountToString = StringUtils.byteCountToString(this.byteCount);
/*     */     
/* 118 */     if (this.options.isSendDefaultPii()) {
/* 119 */       return file.getName() + " (" + byteCountToString + ")";
/*     */     }
/* 121 */     int lastDotIndex = file.getName().lastIndexOf('.');
/*     */     
/* 123 */     if (lastDotIndex > 0 && lastDotIndex < file.getName().length() - 1) {
/* 124 */       String fileExtension = file.getName().substring(lastDotIndex);
/* 125 */       return "***" + fileExtension + " (" + byteCountToString + ")";
/*     */     } 
/* 127 */     return "*** (" + byteCountToString + ")";
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   static interface FileIOCallable<T> {
/*     */     T call() throws IOException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\instrumentation\file\FileIOSpanManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */