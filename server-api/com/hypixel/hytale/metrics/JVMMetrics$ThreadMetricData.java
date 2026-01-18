/*     */ package com.hypixel.hytale.metrics;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import javax.annotation.Nonnull;
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
/*     */ class ThreadMetricData
/*     */ {
/*     */   @Nonnull
/* 193 */   public static final MetricsRegistry<StackTraceElement> STACK_TRACE_ELEMENT_METRICS_REGISTRY = new MetricsRegistry<>(); static {
/* 194 */     STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("FileName", StackTraceElement::getFileName, (Codec<?>)Codec.STRING);
/* 195 */     STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("LineNumber", StackTraceElement::getLineNumber, (Codec<?>)Codec.INTEGER);
/* 196 */     STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("ModuleName", StackTraceElement::getModuleName, (Codec<?>)Codec.STRING);
/* 197 */     STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("ModuleVersion", StackTraceElement::getModuleVersion, (Codec<?>)Codec.STRING);
/* 198 */     STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("ClassLoaderName", StackTraceElement::getClassLoaderName, (Codec<?>)Codec.STRING);
/* 199 */     STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("ClassName", StackTraceElement::getClassName, (Codec<?>)Codec.STRING);
/* 200 */     STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("MethodName", StackTraceElement::getMethodName, (Codec<?>)Codec.STRING);
/*     */   } @Nonnull
/* 202 */   public static final MetricsRegistry<ThreadMetricData> METRICS_REGISTRY = new MetricsRegistry<>(); static {
/* 203 */     METRICS_REGISTRY.register("Id", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getThreadId()), (Codec<?>)Codec.LONG);
/* 204 */     METRICS_REGISTRY.register("Name", threadMetricData -> threadMetricData.threadInfo.getThreadName(), (Codec<?>)Codec.STRING);
/* 205 */     METRICS_REGISTRY.register("State", threadMetricData -> threadMetricData.threadInfo.getThreadState(), (Codec<?>)new EnumCodec(Thread.State.class));
/* 206 */     METRICS_REGISTRY.register("Priority", threadMetricData -> Integer.valueOf(threadMetricData.threadInfo.getPriority()), (Codec<?>)Codec.INTEGER);
/* 207 */     METRICS_REGISTRY.register("Daemon", threadMetricData -> Boolean.valueOf(threadMetricData.threadInfo.isDaemon()), (Codec<?>)Codec.BOOLEAN);
/*     */     
/* 209 */     METRICS_REGISTRY.register("CPUTime", threadMetricData -> Long.valueOf(threadMetricData.threadMXBean.getThreadCpuTime(threadMetricData.threadInfo.getThreadId())), (Codec<?>)Codec.LONG);
/*     */     
/* 211 */     METRICS_REGISTRY.register("WaitedTime", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getWaitedTime()), (Codec<?>)Codec.LONG);
/* 212 */     METRICS_REGISTRY.register("WaitedCount", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getWaitedCount()), (Codec<?>)Codec.LONG);
/* 213 */     METRICS_REGISTRY.register("BlockedTime", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getBlockedTime()), (Codec<?>)Codec.LONG);
/* 214 */     METRICS_REGISTRY.register("BlockedCount", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getBlockedCount()), (Codec<?>)Codec.LONG);
/*     */     
/* 216 */     METRICS_REGISTRY.register("LockName", threadMetricData -> threadMetricData.threadInfo.getLockName(), (Codec<?>)Codec.STRING);
/* 217 */     METRICS_REGISTRY.register("LockOwnerId", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getLockOwnerId()), (Codec<?>)Codec.LONG);
/* 218 */     METRICS_REGISTRY.register("LockOwnerName", threadMetricData -> threadMetricData.threadInfo.getLockOwnerName(), (Codec<?>)Codec.STRING);
/*     */ 
/*     */ 
/*     */     
/* 222 */     METRICS_REGISTRY.register("StackTrace", threadMetricData -> threadMetricData.threadInfo.getStackTrace(), (Codec<?>)new ArrayCodec(STACK_TRACE_ELEMENT_METRICS_REGISTRY, x$0 -> new StackTraceElement[x$0]));
/* 223 */     METRICS_REGISTRY.register("InitStackTrace", threadMetricData -> (threadMetricData.thread instanceof InitStackThread) ? ((InitStackThread)threadMetricData.thread).getInitStack() : null, (Codec<?>)new ArrayCodec(STACK_TRACE_ELEMENT_METRICS_REGISTRY, x$0 -> new StackTraceElement[x$0]));
/*     */     
/* 225 */     METRICS_REGISTRY.register("Interrupted", threadMetricData -> (threadMetricData.thread != null) ? Boolean.valueOf(threadMetricData.thread.isInterrupted()) : null, (Codec<?>)Codec.BOOLEAN);
/* 226 */     METRICS_REGISTRY.register("ThreadClass", threadMetricData -> (threadMetricData.thread != null) ? threadMetricData.thread.getClass().getName() : null, (Codec<?>)Codec.STRING);
/*     */     
/* 228 */     MetricsRegistry<ThreadGroup> threadGroup = new MetricsRegistry<>();
/* 229 */     threadGroup.register("Name", ThreadGroup::getName, (Codec<?>)Codec.STRING);
/* 230 */     threadGroup.register("Parent", ThreadGroup::getParent, threadGroup);
/* 231 */     threadGroup.register("MaxPriority", ThreadGroup::getMaxPriority, (Codec<?>)Codec.INTEGER);
/* 232 */     threadGroup.register("Destroyed", ThreadGroup::isDestroyed, (Codec<?>)Codec.BOOLEAN);
/* 233 */     threadGroup.register("Daemon", ThreadGroup::isDaemon, (Codec<?>)Codec.BOOLEAN);
/* 234 */     threadGroup.register("ActiveCount", ThreadGroup::activeCount, (Codec<?>)Codec.INTEGER);
/* 235 */     threadGroup.register("ActiveGroupCount", ThreadGroup::activeGroupCount, (Codec<?>)Codec.INTEGER);
/* 236 */     METRICS_REGISTRY.register("ThreadGroup", threadMetricData -> (threadMetricData.thread != null) ? threadMetricData.thread.getThreadGroup() : null, threadGroup);
/*     */     
/* 238 */     METRICS_REGISTRY.register("UncaughtExceptionHandler", threadMetricData -> (threadMetricData.thread != null) ? threadMetricData.thread.getUncaughtExceptionHandler().getClass().getName() : null, (Codec<?>)Codec.STRING);
/*     */   }
/*     */   
/*     */   private final ThreadInfo threadInfo;
/*     */   private final Thread thread;
/*     */   private final ThreadMXBean threadMXBean;
/*     */   
/*     */   public ThreadMetricData(ThreadInfo threadInfo, Thread thread, ThreadMXBean threadMXBean) {
/* 246 */     this.threadInfo = threadInfo;
/* 247 */     this.thread = thread;
/* 248 */     this.threadMXBean = threadMXBean;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\metrics\JVMMetrics$ThreadMetricData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */