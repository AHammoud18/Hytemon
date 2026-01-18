/*     */ package com.hypixel.hytale.metrics;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.sun.management.OperatingSystemMXBean;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import java.lang.management.ClassLoadingMXBean;
/*     */ import java.lang.management.GarbageCollectorMXBean;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MemoryMXBean;
/*     */ import java.lang.management.MemoryManagerMXBean;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.lang.management.MemoryType;
/*     */ import java.lang.management.MemoryUsage;
/*     */ import java.lang.management.OperatingSystemMXBean;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JVMMetrics
/*     */ {
/*     */   @Nonnull
/*  32 */   public static final MetricsRegistry<ClassLoader> CLASS_LOADER_METRICS_REGISTRY = new MetricsRegistry<>(); static {
/*  33 */     CLASS_LOADER_METRICS_REGISTRY.register("Name", ClassLoader::getName, (Codec<?>)Codec.STRING);
/*  34 */     CLASS_LOADER_METRICS_REGISTRY.register("Parent", ClassLoader::getParent, CLASS_LOADER_METRICS_REGISTRY);
/*     */   } @Nonnull
/*  36 */   public static final MetricsRegistry<MemoryUsage> MEMORY_USAGE_METRICS_REGISTRY = new MetricsRegistry<>(); static {
/*  37 */     MEMORY_USAGE_METRICS_REGISTRY.register("Init", MemoryUsage::getInit, (Codec<?>)Codec.LONG);
/*  38 */     MEMORY_USAGE_METRICS_REGISTRY.register("Used", MemoryUsage::getUsed, (Codec<?>)Codec.LONG);
/*  39 */     MEMORY_USAGE_METRICS_REGISTRY.register("Committed", MemoryUsage::getCommitted, (Codec<?>)Codec.LONG);
/*  40 */     MEMORY_USAGE_METRICS_REGISTRY.register("Max", MemoryUsage::getMax, (Codec<?>)Codec.LONG);
/*     */   } @Nonnull
/*  42 */   public static final MetricsRegistry<GarbageCollectorMXBean> GARBAGE_COLLECTOR_METRICS_REGISTRY = new MetricsRegistry<>(); static {
/*  43 */     GARBAGE_COLLECTOR_METRICS_REGISTRY.register("Name", MemoryManagerMXBean::getName, (Codec<?>)Codec.STRING);
/*  44 */     GARBAGE_COLLECTOR_METRICS_REGISTRY.register("MemoryPoolNames", MemoryManagerMXBean::getMemoryPoolNames, (Codec<?>)Codec.STRING_ARRAY);
/*  45 */     GARBAGE_COLLECTOR_METRICS_REGISTRY.register("CollectionCount", GarbageCollectorMXBean::getCollectionCount, (Codec<?>)Codec.LONG);
/*  46 */     GARBAGE_COLLECTOR_METRICS_REGISTRY.register("CollectionTime", GarbageCollectorMXBean::getCollectionTime, (Codec<?>)Codec.LONG);
/*     */   } @Nonnull
/*  48 */   public static final MetricsRegistry<MemoryPoolMXBean> MEMORY_POOL_METRICS_REGISTRY = new MetricsRegistry<>(); static {
/*  49 */     MEMORY_POOL_METRICS_REGISTRY.register("Name", MemoryPoolMXBean::getName, (Codec<?>)Codec.STRING);
/*  50 */     MEMORY_POOL_METRICS_REGISTRY.register("Type", MemoryPoolMXBean::getType, (Codec<?>)new EnumCodec(MemoryType.class));
/*  51 */     MEMORY_POOL_METRICS_REGISTRY.register("PeakUsage", MemoryPoolMXBean::getPeakUsage, MEMORY_USAGE_METRICS_REGISTRY);
/*  52 */     MEMORY_POOL_METRICS_REGISTRY.register("Usage", MemoryPoolMXBean::getUsage, MEMORY_USAGE_METRICS_REGISTRY);
/*  53 */     MEMORY_POOL_METRICS_REGISTRY.register("CollectionUsage", MemoryPoolMXBean::getCollectionUsage, MEMORY_USAGE_METRICS_REGISTRY);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     MetricsRegistry<MemoryPoolMXBean> usageThreshold = new MetricsRegistry<>();
/*  59 */     usageThreshold.register("Threshold", MemoryPoolMXBean::getUsageThreshold, (Codec<?>)Codec.LONG);
/*  60 */     usageThreshold.register("ThresholdCount", MemoryPoolMXBean::getUsageThresholdCount, (Codec<?>)Codec.LONG);
/*  61 */     usageThreshold.register("ThresholdExceeded", MemoryPoolMXBean::isUsageThresholdExceeded, (Codec<?>)Codec.BOOLEAN);
/*     */     
/*  63 */     MEMORY_POOL_METRICS_REGISTRY.register("UsageThreshold", memoryPoolMXBean -> !memoryPoolMXBean.isUsageThresholdSupported() ? null : memoryPoolMXBean, usageThreshold);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     MetricsRegistry<MemoryPoolMXBean> collectionUsageThreshold = new MetricsRegistry<>();
/*  70 */     collectionUsageThreshold.register("Threshold", MemoryPoolMXBean::getCollectionUsageThreshold, (Codec<?>)Codec.LONG);
/*  71 */     collectionUsageThreshold.register("ThresholdCount", MemoryPoolMXBean::getCollectionUsageThresholdCount, (Codec<?>)Codec.LONG);
/*  72 */     collectionUsageThreshold.register("ThresholdExceeded", MemoryPoolMXBean::isCollectionUsageThresholdExceeded, (Codec<?>)Codec.BOOLEAN);
/*     */     
/*  74 */     MEMORY_POOL_METRICS_REGISTRY.register("CollectionUsageThreshold", memoryPoolMXBean -> !memoryPoolMXBean.isCollectionUsageThresholdSupported() ? null : memoryPoolMXBean, collectionUsageThreshold);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  80 */   public static final MetricsRegistry<Void> METRICS_REGISTRY = new MetricsRegistry<>();
/*     */   
/*     */   static {
/*  83 */     MetricsRegistry<OperatingSystemMXBean> operatingSystem = new MetricsRegistry<>();
/*     */     
/*  85 */     METRICS_REGISTRY.register("PROCESSOR", unused -> System.getenv("PROCESSOR_IDENTIFIER"), (Codec<?>)Codec.STRING);
/*  86 */     METRICS_REGISTRY.register("PROCESSOR_ARCHITECTURE", unused -> System.getenv("PROCESSOR_ARCHITECTURE"), (Codec<?>)Codec.STRING);
/*  87 */     METRICS_REGISTRY.register("PROCESSOR_ARCHITEW6432", unused -> System.getenv("PROCESSOR_ARCHITEW6432"), (Codec<?>)Codec.STRING);
/*     */     
/*  89 */     operatingSystem.register("OSName", OperatingSystemMXBean::getName, (Codec<?>)Codec.STRING);
/*  90 */     operatingSystem.register("OSArch", OperatingSystemMXBean::getArch, (Codec<?>)Codec.STRING);
/*  91 */     operatingSystem.register("OSVersion", OperatingSystemMXBean::getVersion, (Codec<?>)Codec.STRING);
/*     */     
/*  93 */     operatingSystem.register("AvailableProcessors", unused -> Integer.valueOf(Runtime.getRuntime().availableProcessors()), (Codec<?>)Codec.INTEGER);
/*  94 */     operatingSystem.register("SystemLoadAverage", OperatingSystemMXBean::getSystemLoadAverage, (Codec<?>)Codec.DOUBLE);
/*     */     
/*  96 */     if (ManagementFactory.getOperatingSystemMXBean() instanceof OperatingSystemMXBean) {
/*  97 */       operatingSystem.register("CpuLoad", operatingSystemMXBean -> Double.valueOf(((OperatingSystemMXBean)operatingSystemMXBean).getCpuLoad()), (Codec<?>)Codec.DOUBLE);
/*  98 */       operatingSystem.register("ProcessCpuLoad", operatingSystemMXBean -> Double.valueOf(((OperatingSystemMXBean)operatingSystemMXBean).getProcessCpuLoad()), (Codec<?>)Codec.DOUBLE);
/*     */       
/* 100 */       operatingSystem.register("TotalMemorySize", operatingSystemMXBean -> Long.valueOf(((OperatingSystemMXBean)operatingSystemMXBean).getTotalMemorySize()), (Codec<?>)Codec.LONG);
/* 101 */       operatingSystem.register("FreeMemorySize", operatingSystemMXBean -> Long.valueOf(((OperatingSystemMXBean)operatingSystemMXBean).getFreeMemorySize()), (Codec<?>)Codec.LONG);
/* 102 */       operatingSystem.register("TotalSwapSpaceSize", operatingSystemMXBean -> Long.valueOf(((OperatingSystemMXBean)operatingSystemMXBean).getTotalSwapSpaceSize()), (Codec<?>)Codec.LONG);
/* 103 */       operatingSystem.register("FreeSwapSpaceSize", operatingSystemMXBean -> Long.valueOf(((OperatingSystemMXBean)operatingSystemMXBean).getFreeSwapSpaceSize()), (Codec<?>)Codec.LONG);
/*     */     } 
/*     */     
/* 106 */     METRICS_REGISTRY.register("System", aVoid -> ManagementFactory.getOperatingSystemMXBean(), operatingSystem);
/*     */ 
/*     */ 
/*     */     
/* 110 */     MetricsRegistry<RuntimeMXBean> runtimeBean = new MetricsRegistry<>();
/* 111 */     runtimeBean.register("StartTime", runtimeMXBean -> Instant.ofEpochMilli(runtimeMXBean.getStartTime()), (Codec<?>)Codec.INSTANT);
/* 112 */     runtimeBean.register("Uptime", runtimeMXBean -> Duration.ofMillis(runtimeMXBean.getUptime()), (Codec<?>)Codec.DURATION);
/*     */     
/* 114 */     runtimeBean.register("RuntimeName", RuntimeMXBean::getName, (Codec<?>)Codec.STRING);
/* 115 */     runtimeBean.register("SpecName", RuntimeMXBean::getSpecName, (Codec<?>)Codec.STRING);
/* 116 */     runtimeBean.register("SpecVendor", RuntimeMXBean::getSpecVendor, (Codec<?>)Codec.STRING);
/* 117 */     runtimeBean.register("SpecVersion", RuntimeMXBean::getSpecVersion, (Codec<?>)Codec.STRING);
/* 118 */     runtimeBean.register("ManagementSpecVersion", RuntimeMXBean::getManagementSpecVersion, (Codec<?>)Codec.STRING);
/* 119 */     runtimeBean.register("VMName", RuntimeMXBean::getVmName, (Codec<?>)Codec.STRING);
/* 120 */     runtimeBean.register("VMVendor", RuntimeMXBean::getVmVendor, (Codec<?>)Codec.STRING);
/* 121 */     runtimeBean.register("VMVersion", RuntimeMXBean::getVmVersion, (Codec<?>)Codec.STRING);
/* 122 */     runtimeBean.register("LibraryPath", RuntimeMXBean::getLibraryPath, (Codec<?>)Codec.STRING);
/*     */     try {
/* 124 */       ManagementFactory.getRuntimeMXBean().getBootClassPath();
/* 125 */       runtimeBean.register("BootClassPath", RuntimeMXBean::getBootClassPath, (Codec<?>)Codec.STRING);
/* 126 */     } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */     
/* 128 */     runtimeBean.register("ClassPath", RuntimeMXBean::getClassPath, (Codec<?>)Codec.STRING);
/* 129 */     runtimeBean.register("InputArguments", runtimeMXBean -> (String[])runtimeMXBean.getInputArguments().toArray(()), (Codec<?>)Codec.STRING_ARRAY);
/* 130 */     runtimeBean.register("SystemProperties", RuntimeMXBean::getSystemProperties, (Codec<?>)new MapCodec((Codec)Codec.STRING, java.util.HashMap::new));
/*     */     
/* 132 */     METRICS_REGISTRY.register("Runtime", aVoid -> ManagementFactory.getRuntimeMXBean(), runtimeBean);
/*     */ 
/*     */ 
/*     */     
/* 136 */     MetricsRegistry<MemoryMXBean> memoryBean = new MetricsRegistry<>();
/* 137 */     memoryBean.register("ObjectPendingFinalizationCount", memoryMXBean -> Integer.valueOf(memoryMXBean.getObjectPendingFinalizationCount()), (Codec<?>)Codec.INTEGER);
/*     */     
/* 139 */     memoryBean.register("HeapMemoryUsage", memoryMXBean -> memoryMXBean.getHeapMemoryUsage(), MEMORY_USAGE_METRICS_REGISTRY);
/* 140 */     memoryBean.register("NonHeapMemoryUsage", memoryMXBean -> memoryMXBean.getNonHeapMemoryUsage(), MEMORY_USAGE_METRICS_REGISTRY);
/*     */     
/* 142 */     METRICS_REGISTRY.register("Memory", aVoid -> ManagementFactory.getMemoryMXBean(), memoryBean);
/*     */ 
/*     */     
/* 145 */     METRICS_REGISTRY.register("GarbageCollectors", memoryMXBean -> (GarbageCollectorMXBean[])ManagementFactory.getGarbageCollectorMXBeans().toArray(()), (Codec<?>)new ArrayCodec(GARBAGE_COLLECTOR_METRICS_REGISTRY, x$0 -> new GarbageCollectorMXBean[x$0]));
/* 146 */     METRICS_REGISTRY.register("MemoryPools", memoryMXBean -> (MemoryPoolMXBean[])ManagementFactory.getMemoryPoolMXBeans().toArray(()), (Codec<?>)new ArrayCodec(MEMORY_POOL_METRICS_REGISTRY, x$0 -> new MemoryPoolMXBean[x$0]));
/*     */     
/* 148 */     METRICS_REGISTRY.register("Threads", aVoid -> { ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean(); ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true); Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces(); Long2ObjectOpenHashMap<Thread> threadIdMap = new Long2ObjectOpenHashMap(); for (Thread thread : stackTraces.keySet()) threadIdMap.put(thread.getId(), thread);  ThreadMetricData[] data = new ThreadMetricData[threadInfos.length]; for (int i = 0; i < threadInfos.length; i++) { ThreadInfo threadInfo = threadInfos[i]; data[i] = new ThreadMetricData(threadInfo, (Thread)threadIdMap.get(threadInfo.getThreadId()), threadMXBean); }  return data; }(Codec<?>)new ArrayCodec(ThreadMetricData.METRICS_REGISTRY, x$0 -> new ThreadMetricData[x$0]));
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
/* 168 */     METRICS_REGISTRY.register("SecurityManager", aVoid -> { SecurityManager securityManager = System.getSecurityManager(); return (securityManager == null) ? null : securityManager.getClass().getName(); }(Codec<?>)Codec.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     MetricsRegistry<ClassLoadingMXBean> classLoading = new MetricsRegistry<>();
/* 175 */     classLoading.register("LoadedClassCount", ClassLoadingMXBean::getLoadedClassCount, (Codec<?>)Codec.INTEGER);
/* 176 */     classLoading.register("UnloadedClassCount", ClassLoadingMXBean::getUnloadedClassCount, (Codec<?>)Codec.LONG);
/* 177 */     classLoading.register("TotalLoadedClassCount", ClassLoadingMXBean::getTotalLoadedClassCount, (Codec<?>)Codec.LONG);
/*     */     
/* 179 */     classLoading.register("SystemClassloader", unused -> ClassLoader.getSystemClassLoader(), CLASS_LOADER_METRICS_REGISTRY);
/* 180 */     classLoading.register("JVMMetricsClassloader", unused -> JVMMetrics.class.getClassLoader(), CLASS_LOADER_METRICS_REGISTRY);
/*     */     
/* 182 */     METRICS_REGISTRY.register("ClassLoading", aVoid -> ManagementFactory.getClassLoadingMXBean(), classLoading);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ThreadMetricData
/*     */   {
/*     */     @Nonnull
/* 193 */     public static final MetricsRegistry<StackTraceElement> STACK_TRACE_ELEMENT_METRICS_REGISTRY = new MetricsRegistry<>(); static {
/* 194 */       STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("FileName", StackTraceElement::getFileName, (Codec<?>)Codec.STRING);
/* 195 */       STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("LineNumber", StackTraceElement::getLineNumber, (Codec<?>)Codec.INTEGER);
/* 196 */       STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("ModuleName", StackTraceElement::getModuleName, (Codec<?>)Codec.STRING);
/* 197 */       STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("ModuleVersion", StackTraceElement::getModuleVersion, (Codec<?>)Codec.STRING);
/* 198 */       STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("ClassLoaderName", StackTraceElement::getClassLoaderName, (Codec<?>)Codec.STRING);
/* 199 */       STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("ClassName", StackTraceElement::getClassName, (Codec<?>)Codec.STRING);
/* 200 */       STACK_TRACE_ELEMENT_METRICS_REGISTRY.register("MethodName", StackTraceElement::getMethodName, (Codec<?>)Codec.STRING);
/*     */     } @Nonnull
/* 202 */     public static final MetricsRegistry<ThreadMetricData> METRICS_REGISTRY = new MetricsRegistry<>(); private final ThreadInfo threadInfo; private final Thread thread; private final ThreadMXBean threadMXBean; static {
/* 203 */       METRICS_REGISTRY.register("Id", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getThreadId()), (Codec<?>)Codec.LONG);
/* 204 */       METRICS_REGISTRY.register("Name", threadMetricData -> threadMetricData.threadInfo.getThreadName(), (Codec<?>)Codec.STRING);
/* 205 */       METRICS_REGISTRY.register("State", threadMetricData -> threadMetricData.threadInfo.getThreadState(), (Codec<?>)new EnumCodec(Thread.State.class));
/* 206 */       METRICS_REGISTRY.register("Priority", threadMetricData -> Integer.valueOf(threadMetricData.threadInfo.getPriority()), (Codec<?>)Codec.INTEGER);
/* 207 */       METRICS_REGISTRY.register("Daemon", threadMetricData -> Boolean.valueOf(threadMetricData.threadInfo.isDaemon()), (Codec<?>)Codec.BOOLEAN);
/*     */       
/* 209 */       METRICS_REGISTRY.register("CPUTime", threadMetricData -> Long.valueOf(threadMetricData.threadMXBean.getThreadCpuTime(threadMetricData.threadInfo.getThreadId())), (Codec<?>)Codec.LONG);
/*     */       
/* 211 */       METRICS_REGISTRY.register("WaitedTime", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getWaitedTime()), (Codec<?>)Codec.LONG);
/* 212 */       METRICS_REGISTRY.register("WaitedCount", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getWaitedCount()), (Codec<?>)Codec.LONG);
/* 213 */       METRICS_REGISTRY.register("BlockedTime", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getBlockedTime()), (Codec<?>)Codec.LONG);
/* 214 */       METRICS_REGISTRY.register("BlockedCount", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getBlockedCount()), (Codec<?>)Codec.LONG);
/*     */       
/* 216 */       METRICS_REGISTRY.register("LockName", threadMetricData -> threadMetricData.threadInfo.getLockName(), (Codec<?>)Codec.STRING);
/* 217 */       METRICS_REGISTRY.register("LockOwnerId", threadMetricData -> Long.valueOf(threadMetricData.threadInfo.getLockOwnerId()), (Codec<?>)Codec.LONG);
/* 218 */       METRICS_REGISTRY.register("LockOwnerName", threadMetricData -> threadMetricData.threadInfo.getLockOwnerName(), (Codec<?>)Codec.STRING);
/*     */ 
/*     */ 
/*     */       
/* 222 */       METRICS_REGISTRY.register("StackTrace", threadMetricData -> threadMetricData.threadInfo.getStackTrace(), (Codec<?>)new ArrayCodec(STACK_TRACE_ELEMENT_METRICS_REGISTRY, x$0 -> new StackTraceElement[x$0]));
/* 223 */       METRICS_REGISTRY.register("InitStackTrace", threadMetricData -> (threadMetricData.thread instanceof InitStackThread) ? ((InitStackThread)threadMetricData.thread).getInitStack() : null, (Codec<?>)new ArrayCodec(STACK_TRACE_ELEMENT_METRICS_REGISTRY, x$0 -> new StackTraceElement[x$0]));
/*     */       
/* 225 */       METRICS_REGISTRY.register("Interrupted", threadMetricData -> (threadMetricData.thread != null) ? Boolean.valueOf(threadMetricData.thread.isInterrupted()) : null, (Codec<?>)Codec.BOOLEAN);
/* 226 */       METRICS_REGISTRY.register("ThreadClass", threadMetricData -> (threadMetricData.thread != null) ? threadMetricData.thread.getClass().getName() : null, (Codec<?>)Codec.STRING);
/*     */       
/* 228 */       MetricsRegistry<ThreadGroup> threadGroup = new MetricsRegistry<>();
/* 229 */       threadGroup.register("Name", ThreadGroup::getName, (Codec<?>)Codec.STRING);
/* 230 */       threadGroup.register("Parent", ThreadGroup::getParent, threadGroup);
/* 231 */       threadGroup.register("MaxPriority", ThreadGroup::getMaxPriority, (Codec<?>)Codec.INTEGER);
/* 232 */       threadGroup.register("Destroyed", ThreadGroup::isDestroyed, (Codec<?>)Codec.BOOLEAN);
/* 233 */       threadGroup.register("Daemon", ThreadGroup::isDaemon, (Codec<?>)Codec.BOOLEAN);
/* 234 */       threadGroup.register("ActiveCount", ThreadGroup::activeCount, (Codec<?>)Codec.INTEGER);
/* 235 */       threadGroup.register("ActiveGroupCount", ThreadGroup::activeGroupCount, (Codec<?>)Codec.INTEGER);
/* 236 */       METRICS_REGISTRY.register("ThreadGroup", threadMetricData -> (threadMetricData.thread != null) ? threadMetricData.thread.getThreadGroup() : null, threadGroup);
/*     */       
/* 238 */       METRICS_REGISTRY.register("UncaughtExceptionHandler", threadMetricData -> (threadMetricData.thread != null) ? threadMetricData.thread.getUncaughtExceptionHandler().getClass().getName() : null, (Codec<?>)Codec.STRING);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ThreadMetricData(ThreadInfo threadInfo, Thread thread, ThreadMXBean threadMXBean) {
/* 246 */       this.threadInfo = threadInfo;
/* 247 */       this.thread = thread;
/* 248 */       this.threadMXBean = threadMXBean;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\metrics\JVMMetrics.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */