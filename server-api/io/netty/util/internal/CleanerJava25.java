/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CleanerJava25
/*     */   implements Cleaner
/*     */ {
/*     */   private static final InternalLogger logger;
/*     */   private static final MethodHandle INVOKE_ALLOCATOR;
/*     */   
/*     */   static {
/*     */     boolean suitableJavaVersion;
/*     */     MethodHandle method;
/*     */     Throwable error;
/*  38 */     if (System.getProperty("org.graalvm.nativeimage.imagecode") != null) {
/*     */ 
/*     */       
/*  41 */       String v = System.getProperty("java.specification.version");
/*     */       try {
/*  43 */         suitableJavaVersion = (Integer.parseInt(v) >= 25);
/*  44 */       } catch (NumberFormatException e) {
/*  45 */         suitableJavaVersion = false;
/*     */       } 
/*     */       
/*  48 */       logger = null;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  53 */       suitableJavaVersion = (PlatformDependent0.javaVersion() >= 25);
/*  54 */       logger = InternalLoggerFactory.getInstance(CleanerJava25.class);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (suitableJavaVersion) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  78 */         Class<?> arenaCls = Class.forName("java.lang.foreign.Arena");
/*  79 */         Class<?> memsegCls = Class.forName("java.lang.foreign.MemorySegment");
/*  80 */         Class<CleanableDirectBufferImpl> bufCls = CleanableDirectBufferImpl.class;
/*     */ 
/*     */         
/*  83 */         MethodHandles.Lookup lookup = MethodHandles.lookup();
/*     */ 
/*     */         
/*  86 */         MethodHandle ofShared = lookup.findStatic(arenaCls, "ofShared", MethodType.methodType(arenaCls));
/*     */ 
/*     */ 
/*     */         
/*  90 */         Object shared = ofShared.invoke();
/*  91 */         ((AutoCloseable)shared).close();
/*     */ 
/*     */         
/*  94 */         MethodHandle allocate = lookup.findVirtual(arenaCls, "allocate", MethodType.methodType(memsegCls, long.class));
/*     */         
/*  96 */         MethodHandle asByteBuffer = lookup.findVirtual(memsegCls, "asByteBuffer", MethodType.methodType(ByteBuffer.class));
/*     */         
/*  98 */         MethodHandle address = lookup.findVirtual(memsegCls, "address", MethodType.methodType(long.class));
/*     */         
/* 100 */         MethodHandle bufClsCtor = lookup.findConstructor(bufCls, 
/* 101 */             MethodType.methodType(void.class, AutoCloseable.class, new Class[] { ByteBuffer.class, long.class }));
/*     */ 
/*     */ 
/*     */         
/* 105 */         MethodHandle allocateInt = MethodHandles.explicitCastArguments(allocate, 
/* 106 */             MethodType.methodType(memsegCls, arenaCls, new Class[] { int.class }));
/*     */ 
/*     */ 
/*     */         
/* 110 */         MethodHandle ctorArenaMemsegMemseg = MethodHandles.explicitCastArguments(
/* 111 */             MethodHandles.filterArguments(bufClsCtor, 1, new MethodHandle[] { asByteBuffer, address
/* 112 */               }), MethodType.methodType(bufCls, arenaCls, new Class[] { memsegCls, memsegCls }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 119 */         MethodHandle ctorArenaMemsegNull = MethodHandles.permuteArguments(ctorArenaMemsegMemseg, 
/* 120 */             MethodType.methodType(bufCls, arenaCls, new Class[] { memsegCls, memsegCls }), new int[] { 0, 1, 1 });
/*     */ 
/*     */         
/* 123 */         MethodHandle ctorArenaMemseg = MethodHandles.insertArguments(ctorArenaMemsegNull, 2, new Object[] { null });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 128 */         MethodHandle ctorArenaArenaInt = MethodHandles.collectArguments(ctorArenaMemseg, 1, allocateInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 134 */         MethodHandle ctorArenaNullInt = MethodHandles.permuteArguments(ctorArenaArenaInt, 
/* 135 */             MethodType.methodType(bufCls, arenaCls, new Class[] { arenaCls, int.class }), new int[] { 0, 0, 2 });
/*     */ 
/*     */ 
/*     */         
/* 139 */         MethodHandle ctorArenaInt = MethodHandles.insertArguments(ctorArenaNullInt, 1, new Object[] { null });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 145 */         method = MethodHandles.foldArguments(ctorArenaInt, ofShared);
/* 146 */         error = null;
/* 147 */       } catch (Throwable throwable) {
/* 148 */         method = null;
/* 149 */         error = throwable;
/*     */       } 
/*     */     } else {
/* 152 */       method = null;
/* 153 */       error = new UnsupportedOperationException("java.lang.foreign.MemorySegment unavailable");
/*     */     } 
/* 155 */     if (logger != null) {
/* 156 */       if (error == null) {
/* 157 */         logger.debug("java.nio.ByteBuffer.cleaner(): available");
/*     */       } else {
/* 159 */         logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", error);
/*     */       } 
/*     */     }
/* 162 */     INVOKE_ALLOCATOR = method;
/*     */   }
/*     */   
/*     */   static boolean isSupported() {
/* 166 */     return (INVOKE_ALLOCATOR != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CleanableDirectBuffer allocate(int capacity) {
/*     */     try {
/* 173 */       return INVOKE_ALLOCATOR.invokeExact(capacity);
/* 174 */     } catch (RuntimeException e) {
/* 175 */       throw e;
/* 176 */     } catch (Throwable e) {
/* 177 */       throw new IllegalStateException("Unexpected allocation exception", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeDirectBuffer(ByteBuffer buffer) {
/* 183 */     throw new UnsupportedOperationException("Cannot clean arbitrary ByteBuffer instances");
/*     */   }
/*     */   
/*     */   private static final class CleanableDirectBufferImpl
/*     */     implements CleanableDirectBuffer {
/*     */     private final AutoCloseable closeable;
/*     */     private final ByteBuffer buffer;
/*     */     private final long memoryAddress;
/*     */     
/*     */     CleanableDirectBufferImpl(AutoCloseable closeable, ByteBuffer buffer, long memoryAddress) {
/* 193 */       this.closeable = closeable;
/* 194 */       this.buffer = buffer;
/* 195 */       this.memoryAddress = memoryAddress;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer buffer() {
/* 200 */       return this.buffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clean() {
/*     */       try {
/* 206 */         this.closeable.close();
/* 207 */       } catch (RuntimeException e) {
/* 208 */         throw e;
/* 209 */       } catch (Exception e) {
/* 210 */         throw new IllegalStateException("Unexpected close exception", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasMemoryAddress() {
/* 216 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public long memoryAddress() {
/* 221 */       return this.memoryAddress;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\CleanerJava25.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */