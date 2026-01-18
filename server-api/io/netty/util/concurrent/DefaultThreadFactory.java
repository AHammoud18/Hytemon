/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class DefaultThreadFactory
/*     */   implements ThreadFactory
/*     */ {
/*  31 */   private static final AtomicInteger poolId = new AtomicInteger();
/*     */   
/*  33 */   private final AtomicInteger nextId = new AtomicInteger();
/*     */   private final String prefix;
/*     */   private final boolean daemon;
/*     */   private final int priority;
/*     */   protected final ThreadGroup threadGroup;
/*     */   
/*     */   public DefaultThreadFactory(Class<?> poolType) {
/*  40 */     this(poolType, false, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String poolName) {
/*  44 */     this(poolName, false, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(Class<?> poolType, boolean daemon) {
/*  48 */     this(poolType, daemon, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String poolName, boolean daemon) {
/*  52 */     this(poolName, daemon, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(Class<?> poolType, int priority) {
/*  56 */     this(poolType, false, priority);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String poolName, int priority) {
/*  60 */     this(poolName, false, priority);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(Class<?> poolType, boolean daemon, int priority) {
/*  64 */     this(toPoolName(poolType), daemon, priority);
/*     */   }
/*     */   
/*     */   public static String toPoolName(Class<?> poolType) {
/*  68 */     ObjectUtil.checkNotNull(poolType, "poolType");
/*     */     
/*  70 */     String poolName = StringUtil.simpleClassName(poolType);
/*  71 */     switch (poolName.length()) {
/*     */       case 0:
/*  73 */         return "unknown";
/*     */       case 1:
/*  75 */         return poolName.toLowerCase(Locale.US);
/*     */     } 
/*  77 */     if (Character.isUpperCase(poolName.charAt(0)) && Character.isLowerCase(poolName.charAt(1))) {
/*  78 */       return Character.toLowerCase(poolName.charAt(0)) + poolName.substring(1);
/*     */     }
/*  80 */     return poolName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultThreadFactory(String poolName, boolean daemon, int priority, ThreadGroup threadGroup) {
/*  86 */     ObjectUtil.checkNotNull(poolName, "poolName");
/*     */     
/*  88 */     if (priority < 1 || priority > 10) {
/*  89 */       throw new IllegalArgumentException("priority: " + priority + " (expected: Thread.MIN_PRIORITY <= priority <= Thread.MAX_PRIORITY)");
/*     */     }
/*     */ 
/*     */     
/*  93 */     this.prefix = poolName + '-' + poolId.incrementAndGet() + '-';
/*  94 */     this.daemon = daemon;
/*  95 */     this.priority = priority;
/*  96 */     this.threadGroup = threadGroup;
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String poolName, boolean daemon, int priority) {
/* 100 */     this(poolName, daemon, priority, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Thread newThread(Runnable r) {
/* 105 */     Thread t = newThread(FastThreadLocalRunnable.wrap(r), this.prefix + this.nextId.incrementAndGet());
/*     */     try {
/* 107 */       if (t.isDaemon() != this.daemon) {
/* 108 */         t.setDaemon(this.daemon);
/*     */       }
/*     */       
/* 111 */       if (t.getPriority() != this.priority) {
/* 112 */         t.setPriority(this.priority);
/*     */       }
/* 114 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 117 */     return t;
/*     */   }
/*     */   
/*     */   protected Thread newThread(Runnable r, String name) {
/* 121 */     return new FastThreadLocalThread(this.threadGroup, r, name);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\DefaultThreadFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */