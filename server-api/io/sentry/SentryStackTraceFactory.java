/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryStackFrame;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class SentryStackTraceFactory
/*     */ {
/*     */   private static final int STACKTRACE_FRAME_LIMIT = 100;
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */   
/*     */   public SentryStackTraceFactory(@NotNull SentryOptions options) {
/*  20 */     this.options = options;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<SentryStackFrame> getStackFrames(@Nullable StackTraceElement[] elements, boolean includeSentryFrames) {
/*  32 */     List<SentryStackFrame> sentryStackFrames = null;
/*     */     
/*  34 */     if (elements != null && elements.length > 0) {
/*  35 */       sentryStackFrames = new ArrayList<>();
/*  36 */       for (StackTraceElement item : elements) {
/*  37 */         if (item != null) {
/*     */ 
/*     */           
/*  40 */           String className = item.getClassName();
/*  41 */           if (includeSentryFrames || 
/*  42 */             !className.startsWith("io.sentry.") || className
/*  43 */             .startsWith("io.sentry.samples.") || className
/*  44 */             .startsWith("io.sentry.mobile.")) {
/*     */ 
/*     */ 
/*     */             
/*  48 */             SentryStackFrame sentryStackFrame = new SentryStackFrame();
/*     */             
/*  50 */             sentryStackFrame.setInApp(isInApp(className));
/*  51 */             sentryStackFrame.setModule(className);
/*  52 */             sentryStackFrame.setFunction(item.getMethodName());
/*  53 */             sentryStackFrame.setFilename(item.getFileName());
/*     */ 
/*     */             
/*  56 */             if (item.getLineNumber() >= 0) {
/*  57 */               sentryStackFrame.setLineno(Integer.valueOf(item.getLineNumber()));
/*     */             }
/*  59 */             sentryStackFrame.setNative(Boolean.valueOf(item.isNativeMethod()));
/*  60 */             sentryStackFrames.add(sentryStackFrame);
/*     */ 
/*     */             
/*  63 */             if (sentryStackFrames.size() >= 100)
/*     */               break; 
/*     */           } 
/*     */         } 
/*     */       } 
/*  68 */       Collections.reverse(sentryStackFrames);
/*     */     } 
/*     */     
/*  71 */     return sentryStackFrames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Boolean isInApp(@Nullable String className) {
/*  82 */     if (className == null || className.isEmpty()) {
/*  83 */       return Boolean.valueOf(true);
/*     */     }
/*     */     
/*  86 */     List<String> inAppIncludes = this.options.getInAppIncludes();
/*  87 */     for (String include : inAppIncludes) {
/*  88 */       if (className.startsWith(include)) {
/*  89 */         return Boolean.valueOf(true);
/*     */       }
/*     */     } 
/*     */     
/*  93 */     List<String> inAppExcludes = this.options.getInAppExcludes();
/*  94 */     for (String exclude : inAppExcludes) {
/*  95 */       if (className.startsWith(exclude)) {
/*  96 */         return Boolean.valueOf(false);
/*     */       }
/*     */     } 
/*     */     
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   List<SentryStackFrame> getInAppCallStack(@NotNull Throwable exception) {
/* 112 */     StackTraceElement[] stacktrace = exception.getStackTrace();
/* 113 */     List<SentryStackFrame> frames = getStackFrames(stacktrace, false);
/* 114 */     if (frames == null) {
/* 115 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/* 119 */     List<SentryStackFrame> inAppFrames = CollectionUtils.filterListEntries(frames, frame -> Boolean.TRUE.equals(frame.isInApp()));
/*     */     
/* 121 */     if (!inAppFrames.isEmpty()) {
/* 122 */       return inAppFrames;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 127 */     return CollectionUtils.filterListEntries(frames, frame -> {
/*     */           String module = frame.getModule();
/*     */           
/*     */           boolean isSystemFrame = false;
/*     */           
/*     */           if (module != null) {
/* 133 */             isSystemFrame = (module.startsWith("sun.") || module.startsWith("java.") || module.startsWith("android.") || module.startsWith("com.android."));
/*     */           }
/*     */           return !isSystemFrame;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public List<SentryStackFrame> getInAppCallStack() {
/* 146 */     return getInAppCallStack(new Exception());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryStackTraceFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */