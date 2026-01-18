/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.exception.ExceptionMechanismException;
/*     */ import io.sentry.protocol.Mechanism;
/*     */ import io.sentry.protocol.SentryException;
/*     */ import io.sentry.protocol.SentryStackFrame;
/*     */ import io.sentry.protocol.SentryStackTrace;
/*     */ import io.sentry.protocol.SentryThread;
/*     */ import io.sentry.util.Objects;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Deque;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public final class SentryExceptionFactory
/*     */ {
/*     */   @NotNull
/*     */   private final SentryStackTraceFactory sentryStackTraceFactory;
/*     */   
/*     */   public SentryExceptionFactory(@NotNull SentryStackTraceFactory sentryStackTraceFactory) {
/*  34 */     this
/*  35 */       .sentryStackTraceFactory = (SentryStackTraceFactory)Objects.requireNonNull(sentryStackTraceFactory, "The SentryStackTraceFactory is required.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<SentryException> getSentryExceptionsFromThread(@NotNull SentryThread thread, @NotNull Mechanism mechanism, @NotNull Throwable throwable) {
/*  43 */     SentryStackTrace threadStacktrace = thread.getStacktrace();
/*  44 */     if (threadStacktrace == null) {
/*  45 */       return new ArrayList<>(0);
/*     */     }
/*  47 */     List<SentryException> exceptions = new ArrayList<>(1);
/*  48 */     exceptions.add(
/*  49 */         getSentryException(throwable, mechanism, thread
/*  50 */           .getId(), threadStacktrace.getFrames(), true));
/*  51 */     return exceptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<SentryException> getSentryExceptions(@NotNull Throwable throwable) {
/*  61 */     return getSentryExceptions(extractExceptionQueue(throwable));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private List<SentryException> getSentryExceptions(@NotNull Deque<SentryException> exceptions) {
/*  71 */     return new ArrayList<>(exceptions);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private SentryException getSentryException(@NotNull Throwable throwable, @Nullable Mechanism exceptionMechanism, @Nullable Long threadId, @Nullable List<SentryStackFrame> frames, boolean snapshot) {
/*  96 */     Package exceptionPackage = throwable.getClass().getPackage();
/*  97 */     String fullClassName = throwable.getClass().getName();
/*     */     
/*  99 */     SentryException exception = new SentryException();
/*     */     
/* 101 */     String exceptionMessage = throwable.getMessage();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     String exceptionClassName = (exceptionPackage != null) ? fullClassName.replace(exceptionPackage.getName() + ".", "") : fullClassName;
/*     */ 
/*     */     
/* 109 */     String exceptionPackageName = (exceptionPackage != null) ? exceptionPackage.getName() : null;
/*     */     
/* 111 */     if (frames != null && !frames.isEmpty()) {
/* 112 */       SentryStackTrace sentryStackTrace = new SentryStackTrace(frames);
/* 113 */       if (snapshot) {
/* 114 */         sentryStackTrace.setSnapshot(Boolean.valueOf(true));
/*     */       }
/* 116 */       exception.setStacktrace(sentryStackTrace);
/*     */     } 
/*     */     
/* 119 */     exception.setThreadId(threadId);
/* 120 */     exception.setType(exceptionClassName);
/* 121 */     exception.setMechanism(exceptionMechanism);
/* 122 */     exception.setModule(exceptionPackageName);
/* 123 */     exception.setValue(exceptionMessage);
/*     */     
/* 125 */     return exception;
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
/*     */   @TestOnly
/*     */   @NotNull
/*     */   Deque<SentryException> extractExceptionQueue(@NotNull Throwable throwable) {
/* 139 */     return extractExceptionQueueInternal(throwable, new AtomicInteger(-1), new HashSet<>(), new ArrayDeque<>(), null);
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
/*     */   Deque<SentryException> extractExceptionQueueInternal(@NotNull Throwable throwable, @NotNull AtomicInteger exceptionId, @NotNull HashSet<Throwable> circularityDetector, @NotNull Deque<SentryException> exceptions, @Nullable String mechanismTypeOverride) {
/* 152 */     Throwable currentThrowable = throwable;
/* 153 */     int parentId = exceptionId.get();
/*     */ 
/*     */     
/* 156 */     while (currentThrowable != null && circularityDetector.add(currentThrowable)) {
/* 157 */       Mechanism exceptionMechanism; Thread thread; boolean snapshot = false;
/*     */       
/* 159 */       String mechanismType = (mechanismTypeOverride == null) ? "chained" : mechanismTypeOverride;
/* 160 */       if (currentThrowable instanceof ExceptionMechanismException) {
/*     */         
/* 162 */         ExceptionMechanismException exceptionMechanismThrowable = (ExceptionMechanismException)currentThrowable;
/*     */         
/* 164 */         exceptionMechanism = exceptionMechanismThrowable.getExceptionMechanism();
/* 165 */         currentThrowable = exceptionMechanismThrowable.getThrowable();
/* 166 */         thread = exceptionMechanismThrowable.getThread();
/* 167 */         snapshot = exceptionMechanismThrowable.isSnapshot();
/*     */       } else {
/* 169 */         exceptionMechanism = new Mechanism();
/* 170 */         thread = Thread.currentThread();
/*     */       } 
/*     */       
/* 173 */       boolean includeSentryFrames = Boolean.FALSE.equals(exceptionMechanism.isHandled());
/*     */       
/* 175 */       List<SentryStackFrame> frames = this.sentryStackTraceFactory.getStackFrames(currentThrowable
/* 176 */           .getStackTrace(), includeSentryFrames);
/*     */       
/* 178 */       SentryException exception = getSentryException(currentThrowable, exceptionMechanism, 
/* 179 */           Long.valueOf(thread.getId()), frames, snapshot);
/* 180 */       exceptions.addFirst(exception);
/*     */       
/* 182 */       if (exceptionMechanism.getType() == null) {
/* 183 */         exceptionMechanism.setType(mechanismType);
/*     */       }
/*     */       
/* 186 */       if (exceptionId.get() >= 0) {
/* 187 */         exceptionMechanism.setParentId(Integer.valueOf(parentId));
/*     */       }
/*     */       
/* 190 */       int currentExceptionId = exceptionId.incrementAndGet();
/* 191 */       exceptionMechanism.setExceptionId(Integer.valueOf(currentExceptionId));
/*     */       
/* 193 */       Throwable[] suppressed = currentThrowable.getSuppressed();
/* 194 */       if (suppressed != null && suppressed.length > 0)
/*     */       {
/*     */ 
/*     */         
/* 198 */         for (Throwable suppressedThrowable : suppressed) {
/* 199 */           extractExceptionQueueInternal(suppressedThrowable, exceptionId, circularityDetector, exceptions, "suppressed");
/*     */         }
/*     */       }
/*     */       
/* 203 */       currentThrowable = currentThrowable.getCause();
/* 204 */       parentId = currentExceptionId;
/* 205 */       mechanismTypeOverride = null;
/*     */     } 
/*     */     
/* 208 */     return exceptions;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryExceptionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */