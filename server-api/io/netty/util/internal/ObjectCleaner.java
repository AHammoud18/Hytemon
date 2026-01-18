/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocalThread;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*     */ public final class ObjectCleaner
/*     */ {
/*  37 */   private static final int REFERENCE_QUEUE_POLL_TIMEOUT_MS = Math.max(500, SystemPropertyUtil.getInt("io.netty.util.internal.ObjectCleaner.refQueuePollTimeout", 10000));
/*     */ 
/*     */   
/*  40 */   static final String CLEANER_THREAD_NAME = ObjectCleaner.class.getSimpleName() + "Thread";
/*     */   
/*  42 */   private static final Set<AutomaticCleanerReference> LIVE_SET = ConcurrentHashMap.newKeySet();
/*  43 */   private static final ReferenceQueue<Object> REFERENCE_QUEUE = new ReferenceQueue();
/*  44 */   private static final AtomicBoolean CLEANER_RUNNING = new AtomicBoolean(false);
/*  45 */   private static final Runnable CLEANER_TASK = new Runnable()
/*     */     {
/*     */       public void run() {
/*  48 */         boolean interrupted = false;
/*     */ 
/*     */         
/*     */         do {
/*  52 */           while (!ObjectCleaner.LIVE_SET.isEmpty()) {
/*     */             ObjectCleaner.AutomaticCleanerReference reference;
/*     */             try {
/*  55 */               reference = (ObjectCleaner.AutomaticCleanerReference)ObjectCleaner.REFERENCE_QUEUE.remove(ObjectCleaner.REFERENCE_QUEUE_POLL_TIMEOUT_MS);
/*  56 */             } catch (InterruptedException ex) {
/*     */               
/*  58 */               interrupted = true;
/*     */               continue;
/*     */             } 
/*  61 */             if (reference != null) {
/*     */               try {
/*  63 */                 reference.cleanup();
/*  64 */               } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */               
/*  68 */               ObjectCleaner.LIVE_SET.remove(reference);
/*     */             } 
/*     */           } 
/*  71 */           ObjectCleaner.CLEANER_RUNNING.set(false);
/*     */ 
/*     */         
/*     */         }
/*  75 */         while (!ObjectCleaner.LIVE_SET.isEmpty() && ObjectCleaner.CLEANER_RUNNING.compareAndSet(false, true));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  81 */         if (interrupted)
/*     */         {
/*  83 */           Thread.currentThread().interrupt();
/*     */         }
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Object object, Runnable cleanupTask) {
/*  97 */     AutomaticCleanerReference reference = new AutomaticCleanerReference(object, ObjectUtil.<Runnable>checkNotNull(cleanupTask, "cleanupTask"));
/*     */ 
/*     */     
/* 100 */     LIVE_SET.add(reference);
/*     */ 
/*     */     
/* 103 */     if (CLEANER_RUNNING.compareAndSet(false, true)) {
/* 104 */       final FastThreadLocalThread cleanupThread = new FastThreadLocalThread(CLEANER_TASK);
/* 105 */       fastThreadLocalThread.setPriority(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */           {
/*     */             public Void run() {
/* 114 */               cleanupThread.setContextClassLoader(null);
/* 115 */               return null;
/*     */             }
/*     */           });
/* 118 */       fastThreadLocalThread.setName(CLEANER_THREAD_NAME);
/*     */ 
/*     */ 
/*     */       
/* 122 */       fastThreadLocalThread.setDaemon(true);
/* 123 */       fastThreadLocalThread.start();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getLiveSetCount() {
/* 128 */     return LIVE_SET.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class AutomaticCleanerReference
/*     */     extends WeakReference<Object>
/*     */   {
/*     */     private final Runnable cleanupTask;
/*     */ 
/*     */     
/*     */     AutomaticCleanerReference(Object referent, Runnable cleanupTask) {
/* 139 */       super(referent, ObjectCleaner.REFERENCE_QUEUE);
/* 140 */       this.cleanupTask = cleanupTask;
/*     */     }
/*     */     
/*     */     void cleanup() {
/* 144 */       this.cleanupTask.run();
/*     */     }
/*     */ 
/*     */     
/*     */     public Thread get() {
/* 149 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 154 */       ObjectCleaner.LIVE_SET.remove(this);
/* 155 */       super.clear();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ObjectCleaner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */