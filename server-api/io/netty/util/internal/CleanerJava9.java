/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CleanerJava9
/*     */   implements Cleaner
/*     */ {
/*     */   static {
/*     */     MethodHandle method;
/*     */     Throwable error;
/*     */   }
/*     */   
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(CleanerJava9.class);
/*     */ 
/*     */   
/*     */   private static final MethodHandle INVOKE_CLEANER;
/*     */ 
/*     */   
/*     */   static {
/*  41 */     if (PlatformDependent0.hasUnsafe()) {
/*  42 */       final ByteBuffer buffer = ByteBuffer.allocateDirect(1);
/*  43 */       Object maybeInvokeMethod = AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run()
/*     */             {
/*     */               try {
/*  48 */                 Class<? extends Unsafe> unsafeClass = (Class)PlatformDependent0.UNSAFE.getClass();
/*  49 */                 MethodHandles.Lookup lookup = MethodHandles.lookup();
/*  50 */                 MethodHandle invokeCleaner = lookup.findVirtual(unsafeClass, "invokeCleaner", 
/*  51 */                     MethodType.methodType(void.class, ByteBuffer.class));
/*  52 */                 invokeCleaner = invokeCleaner.bindTo(PlatformDependent0.UNSAFE);
/*  53 */                 invokeCleaner.invokeExact(buffer);
/*  54 */                 return invokeCleaner;
/*  55 */               } catch (Throwable e) {
/*  56 */                 return e;
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/*  61 */       if (maybeInvokeMethod instanceof Throwable) {
/*  62 */         method = null;
/*  63 */         error = (Throwable)maybeInvokeMethod;
/*     */       } else {
/*  65 */         method = (MethodHandle)maybeInvokeMethod;
/*  66 */         error = null;
/*     */       } 
/*     */     } else {
/*  69 */       method = null;
/*  70 */       error = new UnsupportedOperationException("sun.misc.Unsafe unavailable");
/*     */     } 
/*  72 */     if (error == null) {
/*  73 */       logger.debug("java.nio.ByteBuffer.cleaner(): available");
/*     */     } else {
/*  75 */       logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", error);
/*     */     } 
/*  77 */     INVOKE_CLEANER = method;
/*     */   }
/*     */   
/*     */   static boolean isSupported() {
/*  81 */     return (INVOKE_CLEANER != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public CleanableDirectBuffer allocate(int capacity) {
/*  86 */     return new CleanableDirectBufferImpl(ByteBuffer.allocateDirect(capacity));
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void freeDirectBuffer(ByteBuffer buffer) {
/*  92 */     freeDirectBufferStatic(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void freeDirectBufferStatic(ByteBuffer buffer) {
/*  98 */     if (System.getSecurityManager() == null) {
/*     */       try {
/* 100 */         INVOKE_CLEANER.invokeExact(buffer);
/* 101 */       } catch (Throwable cause) {
/* 102 */         PlatformDependent0.throwException(cause);
/*     */       } 
/*     */     } else {
/* 105 */       freeDirectBufferPrivileged(buffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void freeDirectBufferPrivileged(final ByteBuffer buffer) {
/* 110 */     Throwable error = AccessController.<Throwable>doPrivileged(new PrivilegedAction<Throwable>()
/*     */         {
/*     */           public Throwable run() {
/*     */             try {
/* 114 */               CleanerJava9.INVOKE_CLEANER.invokeExact(buffer);
/* 115 */             } catch (Throwable e) {
/* 116 */               return e;
/*     */             } 
/* 118 */             return null;
/*     */           }
/*     */         });
/* 121 */     if (error != null)
/* 122 */       PlatformDependent0.throwException(error); 
/*     */   }
/*     */   
/*     */   private static final class CleanableDirectBufferImpl
/*     */     implements CleanableDirectBuffer {
/*     */     private final ByteBuffer buffer;
/*     */     
/*     */     private CleanableDirectBufferImpl(ByteBuffer buffer) {
/* 130 */       this.buffer = buffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer buffer() {
/* 135 */       return this.buffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clean() {
/* 140 */       CleanerJava9.freeDirectBufferStatic(this.buffer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\CleanerJava9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */