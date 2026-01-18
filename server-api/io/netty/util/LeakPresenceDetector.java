/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import java.io.Closeable;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.LongAdder;
/*     */ import java.util.function.Supplier;
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
/*     */ public class LeakPresenceDetector<T>
/*     */   extends ResourceLeakDetector<T>
/*     */ {
/*     */   private static final String TRACK_CREATION_STACK_PROPERTY = "io.netty.util.LeakPresenceDetector.trackCreationStack";
/*  86 */   private static final boolean TRACK_CREATION_STACK = SystemPropertyUtil.getBoolean("io.netty.util.LeakPresenceDetector.trackCreationStack", false);
/*  87 */   private static final ResourceScope GLOBAL = new ResourceScope("global");
/*     */   
/*     */   private static int staticInitializerCount;
/*     */   
/*     */   private static boolean inStaticInitializerSlow(StackTraceElement[] stackTrace) {
/*  92 */     for (StackTraceElement element : stackTrace) {
/*  93 */       if (element.getMethodName().equals("<clinit>")) {
/*  94 */         return true;
/*     */       }
/*     */     } 
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean inStaticInitializerFast() {
/* 102 */     return (staticInitializerCount != 0 && inStaticInitializerSlow(Thread.currentThread().getStackTrace()));
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
/*     */   public static <R> R staticInitializer(Supplier<R> supplier) {
/* 122 */     if (!inStaticInitializerSlow(Thread.currentThread().getStackTrace())) {
/* 123 */       throw new IllegalStateException("Not in static initializer.");
/*     */     }
/* 125 */     synchronized (LeakPresenceDetector.class) {
/* 126 */       staticInitializerCount++;
/*     */     } 
/*     */     try {
/* 129 */       return supplier.get();
/*     */     } finally {
/* 131 */       synchronized (LeakPresenceDetector.class) {
/* 132 */         staticInitializerCount--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeakPresenceDetector(Class<?> resourceType) {
/* 143 */     super(resourceType, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public LeakPresenceDetector(Class<?> resourceType, int samplingInterval) {
/* 155 */     this(resourceType);
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
/*     */   public LeakPresenceDetector(Class<?> resourceType, int samplingInterval, long maxActive) {
/* 167 */     this(resourceType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceScope currentScope() throws AllocationProhibitedException {
/* 177 */     return GLOBAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ResourceLeakTracker<T> track(T obj) {
/* 182 */     if (inStaticInitializerFast()) {
/* 183 */       return null;
/*     */     }
/* 185 */     return trackForcibly(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ResourceLeakTracker<T> trackForcibly(T obj) {
/* 190 */     return new PresenceTracker<>(currentScope());
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isRecordEnabled() {
/* 195 */     return false;
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
/*     */   public static void check() {
/* 207 */     ResourceLeakDetector<Object> detector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(Object.class);
/*     */     
/* 209 */     if (!(detector instanceof LeakPresenceDetector)) {
/* 210 */       throw new IllegalStateException("LeakPresenceDetector not in use. Please register it using -Dio.netty.customResourceLeakDetector=" + LeakPresenceDetector.class
/*     */           
/* 212 */           .getName());
/*     */     }
/*     */ 
/*     */     
/* 216 */     ((LeakPresenceDetector)detector).currentScope().check();
/*     */   }
/*     */   
/*     */   private static final class PresenceTracker<T> extends AtomicBoolean implements ResourceLeakTracker<T> {
/*     */     private final LeakPresenceDetector.ResourceScope scope;
/*     */     
/*     */     PresenceTracker(LeakPresenceDetector.ResourceScope scope) {
/* 223 */       super(false);
/* 224 */       this.scope = scope;
/*     */       
/* 226 */       scope.checkOpen();
/*     */       
/* 228 */       scope.openResourceCounter.increment();
/* 229 */       if (LeakPresenceDetector.TRACK_CREATION_STACK) {
/* 230 */         scope.creationStacks.put(this, new LeakPresenceDetector.LeakCreation());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void record() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void record(Object hint) {}
/*     */ 
/*     */     
/*     */     public boolean close(Object trackedObject) {
/* 244 */       if (compareAndSet(false, true)) {
/* 245 */         this.scope.openResourceCounter.decrement();
/* 246 */         if (LeakPresenceDetector.TRACK_CREATION_STACK) {
/* 247 */           this.scope.creationStacks.remove(this);
/*     */         }
/* 249 */         this.scope.checkOpen();
/* 250 */         return true;
/*     */       } 
/* 252 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class ResourceScope
/*     */     implements Closeable
/*     */   {
/*     */     final String name;
/*     */     
/* 262 */     final LongAdder openResourceCounter = new LongAdder();
/*     */     
/* 264 */     final Map<LeakPresenceDetector.PresenceTracker<?>, Throwable> creationStacks = LeakPresenceDetector.TRACK_CREATION_STACK ? new ConcurrentHashMap<>() : null;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean closed;
/*     */ 
/*     */ 
/*     */     
/*     */     public ResourceScope(String name) {
/* 273 */       this.name = name;
/*     */     }
/*     */     
/*     */     void checkOpen() {
/* 277 */       if (this.closed) {
/* 278 */         throw new LeakPresenceDetector.AllocationProhibitedException("Resource scope '" + this.name + "' already closed");
/*     */       }
/*     */     }
/*     */     
/*     */     void check() {
/* 283 */       long n = this.openResourceCounter.sumThenReset();
/* 284 */       if (n != 0L) {
/*     */         
/* 286 */         StringBuilder msg = (new StringBuilder("Possible memory leak detected for resource scope '")).append(this.name).append("'. ");
/* 287 */         if (n < 0L) {
/* 288 */           msg.append("Resource count was negative: A resource previously reported as a leak was released after all. Please ensure that that resource is released before its test finishes.");
/*     */           
/* 290 */           throw new IllegalStateException(msg.toString());
/*     */         } 
/* 292 */         if (LeakPresenceDetector.TRACK_CREATION_STACK) {
/* 293 */           msg.append("Creation stack traces:");
/* 294 */           IllegalStateException ise = new IllegalStateException(msg.toString());
/* 295 */           int i = 0;
/* 296 */           for (Throwable t : this.creationStacks.values()) {
/* 297 */             ise.addSuppressed(t);
/* 298 */             if (i++ > 5) {
/*     */               break;
/*     */             }
/*     */           } 
/* 302 */           this.creationStacks.clear();
/* 303 */           throw ise;
/*     */         } 
/* 305 */         msg.append("Please use paranoid leak detection to get more information, or set -Dio.netty.util.LeakPresenceDetector.trackCreationStack=true");
/*     */         
/* 307 */         throw new IllegalStateException(msg.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasOpenResources() {
/* 317 */       return (this.openResourceCounter.sum() > 0L);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() {
/* 326 */       this.closed = true;
/* 327 */       check();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class LeakCreation extends Throwable {
/* 332 */     final Thread thread = Thread.currentThread();
/*     */     
/*     */     String message;
/*     */     
/*     */     public synchronized String getMessage() {
/* 337 */       if (this.message == null) {
/* 338 */         if (LeakPresenceDetector.inStaticInitializerSlow(getStackTrace())) {
/* 339 */           this.message = "Resource created in static initializer. Please wrap the static initializer in LeakPresenceDetector.staticInitializer so that this resource is excluded.";
/*     */         } else {
/*     */           
/* 342 */           this
/* 343 */             .message = "Resource created outside static initializer on thread '" + this.thread.getName() + "' (" + this.thread.getState() + "), likely leak.";
/*     */         } 
/*     */       }
/* 346 */       return this.message;
/*     */     }
/*     */ 
/*     */     
/*     */     private LeakCreation() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class AllocationProhibitedException
/*     */     extends IllegalStateException
/*     */   {
/*     */     public AllocationProhibitedException(String s) {
/* 358 */       super(s);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\LeakPresenceDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */