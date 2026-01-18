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
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CleanerJava6
/*     */   implements Cleaner
/*     */ {
/*     */   private static final MethodHandle CLEAN_METHOD;
/*     */   
/*     */   static {
/*     */     MethodHandle clean;
/*     */   }
/*     */   
/*  39 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(CleanerJava6.class);
/*     */ 
/*     */   
/*     */   static {
/*  43 */     Throwable error = null;
/*  44 */     ByteBuffer direct = ByteBuffer.allocateDirect(1);
/*     */     try {
/*  46 */       Object mayBeCleanerField = AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run() {
/*     */               try {
/*  50 */                 Class<?> cleanerClass = Class.forName("sun.misc.Cleaner");
/*  51 */                 Class<?> directBufClass = Class.forName("sun.nio.ch.DirectBuffer");
/*  52 */                 MethodHandles.Lookup lookup = MethodHandles.lookup();
/*     */ 
/*     */                 
/*  55 */                 MethodHandle clean = lookup.findVirtual(cleanerClass, "clean", 
/*  56 */                     MethodType.methodType(void.class));
/*     */                 
/*  58 */                 MethodHandle nullTest = lookup.findStatic(Objects.class, "nonNull", 
/*  59 */                     MethodType.methodType(boolean.class, Object.class));
/*  60 */                 clean = MethodHandles.guardWithTest(nullTest
/*  61 */                     .asType(MethodType.methodType(boolean.class, cleanerClass)), clean, nullTest
/*     */                     
/*  63 */                     .asType(MethodType.methodType(void.class, cleanerClass)));
/*     */                 
/*  65 */                 clean = MethodHandles.filterArguments(clean, 0, new MethodHandle[] { lookup.findVirtual(directBufClass, "cleaner", 
/*     */ 
/*     */                         
/*  68 */                         MethodType.methodType(cleanerClass)) });
/*     */                 
/*  70 */                 clean = MethodHandles.explicitCastArguments(clean, 
/*  71 */                     MethodType.methodType(void.class, ByteBuffer.class));
/*  72 */                 return clean;
/*  73 */               } catch (Throwable cause) {
/*  74 */                 return cause;
/*     */               } 
/*     */             }
/*     */           });
/*  78 */       if (mayBeCleanerField instanceof Throwable) {
/*  79 */         throw (Throwable)mayBeCleanerField;
/*     */       }
/*     */       
/*  82 */       clean = (MethodHandle)mayBeCleanerField;
/*  83 */       clean.invokeExact(direct);
/*  84 */     } catch (Throwable t) {
/*     */       
/*  86 */       clean = null;
/*  87 */       error = t;
/*     */     } 
/*     */     
/*  90 */     if (error == null) {
/*  91 */       logger.debug("java.nio.ByteBuffer.cleaner(): available");
/*     */     } else {
/*  93 */       logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", error);
/*     */     } 
/*  95 */     CLEAN_METHOD = clean;
/*     */   }
/*     */   
/*     */   static boolean isSupported() {
/*  99 */     return (CLEAN_METHOD != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public CleanableDirectBuffer allocate(int capacity) {
/* 104 */     return new CleanableDirectBufferImpl(ByteBuffer.allocateDirect(capacity));
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void freeDirectBuffer(ByteBuffer buffer) {
/* 110 */     freeDirectBufferStatic(buffer);
/*     */   }
/*     */   
/*     */   private static void freeDirectBufferStatic(ByteBuffer buffer) {
/* 114 */     if (!buffer.isDirect()) {
/*     */       return;
/*     */     }
/* 117 */     if (System.getSecurityManager() == null) {
/*     */       try {
/* 119 */         freeDirectBuffer0(buffer);
/* 120 */       } catch (Throwable cause) {
/* 121 */         PlatformDependent0.throwException(cause);
/*     */       } 
/*     */     } else {
/* 124 */       freeDirectBufferPrivileged(buffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void freeDirectBufferPrivileged(final ByteBuffer buffer) {
/* 129 */     Throwable cause = AccessController.<Throwable>doPrivileged(new PrivilegedAction<Throwable>()
/*     */         {
/*     */           public Throwable run() {
/*     */             try {
/* 133 */               CleanerJava6.freeDirectBuffer0(buffer);
/* 134 */               return null;
/* 135 */             } catch (Throwable cause) {
/* 136 */               return cause;
/*     */             } 
/*     */           }
/*     */         });
/* 140 */     if (cause != null) {
/* 141 */       PlatformDependent0.throwException(cause);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void freeDirectBuffer0(ByteBuffer buffer) throws Throwable {
/* 146 */     CLEAN_METHOD.invokeExact(buffer);
/*     */   }
/*     */   
/*     */   private static final class CleanableDirectBufferImpl implements CleanableDirectBuffer {
/*     */     private final ByteBuffer buffer;
/*     */     
/*     */     private CleanableDirectBufferImpl(ByteBuffer buffer) {
/* 153 */       this.buffer = buffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer buffer() {
/* 158 */       return this.buffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clean() {
/* 163 */       CleanerJava6.freeDirectBufferStatic(this.buffer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\CleanerJava6.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */