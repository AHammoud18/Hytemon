/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryStackFrame;
/*     */ import io.sentry.protocol.SentryStackTrace;
/*     */ import io.sentry.protocol.SentryThread;
/*     */ import io.sentry.util.Objects;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.TestOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class SentryThreadFactory
/*     */ {
/*     */   @NotNull
/*     */   private final SentryStackTraceFactory sentryStackTraceFactory;
/*     */   
/*     */   public SentryThreadFactory(@NotNull SentryStackTraceFactory sentryStackTraceFactory) {
/*  29 */     this
/*  30 */       .sentryStackTraceFactory = (SentryStackTraceFactory)Objects.requireNonNull(sentryStackTraceFactory, "The SentryStackTraceFactory is required.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   List<SentryThread> getCurrentThread(boolean attachStackTrace) {
/*  41 */     Map<Thread, StackTraceElement[]> threads = (Map)new HashMap<>();
/*  42 */     Thread currentThread = Thread.currentThread();
/*  43 */     threads.put(currentThread, currentThread.getStackTrace());
/*     */     
/*  45 */     return getCurrentThreads(threads, null, false, attachStackTrace);
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
/*     */   @Nullable
/*     */   List<SentryThread> getCurrentThreads(@Nullable List<Long> mechanismThreadIds, boolean ignoreCurrentThread, boolean attachStackTrace) {
/*  62 */     return getCurrentThreads(
/*  63 */         Thread.getAllStackTraces(), mechanismThreadIds, ignoreCurrentThread, attachStackTrace);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   List<SentryThread> getCurrentThreads(@Nullable List<Long> mechanismThreadIds, boolean attachStackTrace) {
/*  69 */     return getCurrentThreads(
/*  70 */         Thread.getAllStackTraces(), mechanismThreadIds, false, attachStackTrace);
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
/*     */ 
/*     */   
/*     */   @TestOnly
/*     */   @Nullable
/*     */   List<SentryThread> getCurrentThreads(@NotNull Map<Thread, StackTraceElement[]> threads, @Nullable List<Long> mechanismThreadIds, boolean ignoreCurrentThread, boolean attachStackTrace) {
/*  90 */     List<SentryThread> result = null;
/*     */     
/*  92 */     Thread currentThread = Thread.currentThread();
/*     */     
/*  94 */     if (!threads.isEmpty()) {
/*  95 */       result = new ArrayList<>();
/*     */ 
/*     */       
/*  98 */       if (!threads.containsKey(currentThread)) {
/*  99 */         threads.put(currentThread, currentThread.getStackTrace());
/*     */       }
/*     */       
/* 102 */       for (Map.Entry<Thread, StackTraceElement[]> item : threads.entrySet()) {
/*     */         
/* 104 */         Thread thread = item.getKey();
/*     */ 
/*     */ 
/*     */         
/* 108 */         boolean crashed = ((thread == currentThread && !ignoreCurrentThread) || (mechanismThreadIds != null && mechanismThreadIds.contains(Long.valueOf(thread.getId())) && !ignoreCurrentThread));
/*     */ 
/*     */         
/* 111 */         result.add(getSentryThread(crashed, item.getValue(), item.getKey(), attachStackTrace));
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return result;
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
/*     */   @NotNull
/*     */   private SentryThread getSentryThread(boolean crashed, @NotNull StackTraceElement[] stackFramesElements, @NotNull Thread thread, boolean attachStacktrace) {
/* 131 */     SentryThread sentryThread = new SentryThread();
/*     */     
/* 133 */     sentryThread.setName(thread.getName());
/* 134 */     sentryThread.setPriority(Integer.valueOf(thread.getPriority()));
/* 135 */     sentryThread.setId(Long.valueOf(thread.getId()));
/* 136 */     sentryThread.setDaemon(Boolean.valueOf(thread.isDaemon()));
/* 137 */     sentryThread.setState(thread.getState().name());
/* 138 */     sentryThread.setCrashed(Boolean.valueOf(crashed));
/*     */     
/* 140 */     if (attachStacktrace) {
/*     */       
/* 142 */       List<SentryStackFrame> frames = this.sentryStackTraceFactory.getStackFrames(stackFramesElements, false);
/*     */       
/* 144 */       if (frames != null && !frames.isEmpty()) {
/* 145 */         SentryStackTrace sentryStackTrace = new SentryStackTrace(frames);
/*     */         
/* 147 */         sentryStackTrace.setSnapshot(Boolean.valueOf(true));
/*     */         
/* 149 */         sentryThread.setStacktrace(sentryStackTrace);
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return sentryThread;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryThreadFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */