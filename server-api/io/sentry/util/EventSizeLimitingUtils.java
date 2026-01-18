/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.Breadcrumb;
/*     */ import io.sentry.Hint;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.SentryEvent;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.protocol.SentryException;
/*     */ import io.sentry.protocol.SentryStackFrame;
/*     */ import io.sentry.protocol.SentryStackTrace;
/*     */ import io.sentry.protocol.SentryThread;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class EventSizeLimitingUtils
/*     */ {
/*     */   private static final long MAX_EVENT_SIZE_BYTES = 1048576L;
/*     */   private static final int MAX_FRAMES_PER_STACK = 500;
/*     */   private static final int FRAMES_PER_SIDE = 250;
/*     */   
/*     */   @Nullable
/*     */   public static SentryEvent limitEventSize(@NotNull SentryEvent event, @NotNull Hint hint, @NotNull SentryOptions options) {
/*     */     try {
/*  44 */       if (!options.isEnableEventSizeLimiting()) {
/*  45 */         return event;
/*     */       }
/*     */       
/*  48 */       if (isSizeOk(event, options)) {
/*  49 */         return event;
/*     */       }
/*     */       
/*  52 */       options
/*  53 */         .getLogger()
/*  54 */         .log(SentryLevel.INFO, "Event %s exceeds %d bytes limit. Reducing size by dropping fields.", new Object[] {
/*     */ 
/*     */             
/*  57 */             event.getEventId(), 
/*  58 */             Long.valueOf(1048576L)
/*     */           });
/*  60 */       SentryEvent reducedEvent = event;
/*     */ 
/*     */       
/*  63 */       SentryOptions.OnOversizedEventCallback callback = options.getOnOversizedEvent();
/*  64 */       if (callback != null) {
/*     */         try {
/*  66 */           reducedEvent = callback.execute(reducedEvent, hint);
/*  67 */           if (isSizeOk(reducedEvent, options)) {
/*  68 */             return reducedEvent;
/*     */           }
/*  70 */         } catch (Throwable e) {
/*  71 */           options
/*  72 */             .getLogger()
/*  73 */             .log(SentryLevel.ERROR, "The onOversizedEvent callback threw an exception. It will be ignored and automatic reduction will continue.", e);
/*     */ 
/*     */ 
/*     */           
/*  77 */           reducedEvent = event;
/*     */         } 
/*     */       }
/*     */       
/*  81 */       reducedEvent = removeAllBreadcrumbs(reducedEvent, options);
/*  82 */       if (isSizeOk(reducedEvent, options)) {
/*  83 */         return reducedEvent;
/*     */       }
/*     */       
/*  86 */       reducedEvent = truncateStackFrames(reducedEvent, options);
/*  87 */       if (!isSizeOk(reducedEvent, options)) {
/*  88 */         options
/*  89 */           .getLogger()
/*  90 */           .log(SentryLevel.WARNING, "Event %s still exceeds size limit after reducing all fields. Event may be rejected by server.", new Object[] {
/*     */ 
/*     */               
/*  93 */               event.getEventId()
/*     */             });
/*     */       }
/*  96 */       return reducedEvent;
/*  97 */     } catch (Throwable e) {
/*  98 */       options
/*  99 */         .getLogger()
/* 100 */         .log(SentryLevel.ERROR, "An error occurred while limiting event size. Event will be sent as-is.", e);
/*     */ 
/*     */ 
/*     */       
/* 104 */       return event;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSizeOk(@NotNull SentryEvent event, @NotNull SentryOptions options) {
/* 111 */     long size = JsonSerializationUtils.byteSizeOf(options.getSerializer(), options.getLogger(), (JsonSerializable)event);
/* 112 */     return (size <= 1048576L);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private static SentryEvent removeAllBreadcrumbs(@NotNull SentryEvent event, @NotNull SentryOptions options) {
/* 117 */     List<Breadcrumb> breadcrumbs = event.getBreadcrumbs();
/* 118 */     if (breadcrumbs != null && !breadcrumbs.isEmpty()) {
/* 119 */       event.setBreadcrumbs(null);
/* 120 */       options
/* 121 */         .getLogger()
/* 122 */         .log(SentryLevel.DEBUG, "Removed breadcrumbs to reduce size of event %s", new Object[] {
/*     */ 
/*     */             
/* 125 */             event.getEventId() });
/*     */     } 
/* 127 */     return event;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private static SentryEvent truncateStackFrames(@NotNull SentryEvent event, @NotNull SentryOptions options) {
/* 132 */     List<SentryException> exceptions = event.getExceptions();
/* 133 */     if (exceptions != null) {
/* 134 */       for (SentryException exception : exceptions) {
/* 135 */         SentryStackTrace stacktrace = exception.getStacktrace();
/* 136 */         if (stacktrace != null) {
/* 137 */           truncateStackFramesInStackTrace(stacktrace, event, options, "Truncated exception stack frames of event %s");
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 143 */     List<SentryThread> threads = event.getThreads();
/* 144 */     if (threads != null) {
/* 145 */       for (SentryThread thread : threads) {
/* 146 */         SentryStackTrace stacktrace = thread.getStacktrace();
/* 147 */         if (stacktrace != null) {
/* 148 */           truncateStackFramesInStackTrace(stacktrace, event, options, "Truncated thread stack frames for event %s");
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 154 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void truncateStackFramesInStackTrace(@NotNull SentryStackTrace stacktrace, @NotNull SentryEvent event, @NotNull SentryOptions options, @NotNull String logMessage) {
/* 162 */     List<SentryStackFrame> frames = stacktrace.getFrames();
/* 163 */     if (frames != null && frames.size() > 500) {
/* 164 */       List<SentryStackFrame> truncatedFrames = new ArrayList<>(500);
/* 165 */       truncatedFrames.addAll(frames.subList(0, 250));
/* 166 */       truncatedFrames.addAll(frames.subList(frames.size() - 250, frames.size()));
/* 167 */       stacktrace.setFrames(truncatedFrames);
/* 168 */       options.getLogger().log(SentryLevel.DEBUG, logMessage, new Object[] { event.getEventId() });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\EventSizeLimitingUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */