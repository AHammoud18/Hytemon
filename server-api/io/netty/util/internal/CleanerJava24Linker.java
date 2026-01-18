/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Array;
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
/*     */ public class CleanerJava24Linker
/*     */   implements Cleaner
/*     */ {
/*     */   private static final InternalLogger logger;
/*     */   private static final MethodHandle INVOKE_MALLOC;
/*     */   private static final MethodHandle INVOKE_CREATE_BYTEBUFFER;
/*     */   private static final MethodHandle INVOKE_FREE;
/*     */   
/*     */   static {
/*     */     boolean suitableJavaVersion;
/*     */     MethodHandle mallocMethod, wrapMethod, freeMethod;
/*     */     Throwable error;
/*  37 */     if (System.getProperty("org.graalvm.nativeimage.imagecode") != null) {
/*     */ 
/*     */       
/*  40 */       String v = System.getProperty("java.specification.version");
/*     */       try {
/*  42 */         suitableJavaVersion = (Integer.parseInt(v) >= 25);
/*  43 */       } catch (NumberFormatException e) {
/*  44 */         suitableJavaVersion = false;
/*     */       } 
/*     */       
/*  47 */       logger = null;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/*  55 */       suitableJavaVersion = (PlatformDependent0.javaVersion() >= 24);
/*  56 */       logger = InternalLoggerFactory.getInstance(CleanerJava24Linker.class);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     if (suitableJavaVersion) {
/*     */       
/*     */       try {
/*  67 */         MethodHandles.Lookup lookup = MethodHandles.lookup();
/*  68 */         Class<?> moduleCls = Class.forName("java.lang.Module");
/*  69 */         MethodHandle getModule = lookup.findVirtual(Class.class, "getModule", 
/*  70 */             MethodType.methodType(moduleCls));
/*  71 */         MethodHandle isNativeAccessEnabledModule = lookup.findVirtual(moduleCls, "isNativeAccessEnabled", 
/*  72 */             MethodType.methodType(boolean.class));
/*  73 */         MethodHandle isNativeAccessEnabledForClass = MethodHandles.filterArguments(isNativeAccessEnabledModule, 0, new MethodHandle[] { getModule });
/*     */ 
/*     */         
/*  76 */         boolean isNativeAccessEnabled = isNativeAccessEnabledForClass.invokeExact(CleanerJava24Linker.class);
/*  77 */         if (!isNativeAccessEnabled) {
/*  78 */           throw new UnsupportedOperationException("Native access (restricted methods) is not enabled for the io.netty.common module.");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  84 */         Class<?> memoryLayoutCls = Class.forName("java.lang.foreign.MemoryLayout");
/*  85 */         Class<?> memoryLayoutArrayCls = Class.forName("[Ljava.lang.foreign.MemoryLayout;");
/*  86 */         Class<?> valueLayoutCls = Class.forName("java.lang.foreign.ValueLayout");
/*  87 */         Class<?> valueLayoutAddressCls = Class.forName("java.lang.foreign.AddressLayout");
/*  88 */         MethodHandle addressLayoutGetter = lookup.findStaticGetter(valueLayoutCls, "ADDRESS", valueLayoutAddressCls);
/*     */         
/*  90 */         MethodHandle byteSize = lookup.findVirtual(valueLayoutAddressCls, "byteSize", MethodType.methodType(long.class));
/*  91 */         MethodHandle byteSizeOfAddress = MethodHandles.foldArguments(byteSize, addressLayoutGetter);
/*  92 */         long addressSize = byteSizeOfAddress.invokeExact();
/*  93 */         if (addressSize != 8L) {
/*  94 */           throw new UnsupportedOperationException("Linking to malloc and free is only supported on 64-bit platforms.");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 110 */         Class<?> ofLongValueLayoutCls = Class.forName("java.lang.foreign.ValueLayout$OfLong");
/* 111 */         Class<?> linkerCls = Class.forName("java.lang.foreign.Linker");
/* 112 */         Class<?> linkerOptionCls = Class.forName("java.lang.foreign.Linker$Option");
/* 113 */         Class<?> linkerOptionArrayCls = Class.forName("[Ljava.lang.foreign.Linker$Option;");
/* 114 */         Class<?> symbolLookupCls = Class.forName("java.lang.foreign.SymbolLookup");
/* 115 */         Class<?> memSegCls = Class.forName("java.lang.foreign.MemorySegment");
/* 116 */         Class<?> funcDescCls = Class.forName("java.lang.foreign.FunctionDescriptor");
/*     */         
/* 118 */         MethodHandle nativeLinker = lookup.findStatic(linkerCls, "nativeLinker", MethodType.methodType(linkerCls));
/* 119 */         MethodHandle defaultLookupStatic = MethodHandles.foldArguments(lookup
/* 120 */             .findVirtual(linkerCls, "defaultLookup", MethodType.methodType(symbolLookupCls)), nativeLinker);
/*     */         
/* 122 */         MethodHandle downcallHandleStatic = MethodHandles.foldArguments(lookup
/* 123 */             .findVirtual(linkerCls, "downcallHandle", 
/* 124 */               MethodType.methodType(MethodHandle.class, memSegCls, new Class[] { funcDescCls, linkerOptionArrayCls })), nativeLinker);
/*     */         
/* 126 */         MethodHandle findSymbol = MethodHandles.foldArguments(lookup
/* 127 */             .findVirtual(symbolLookupCls, "findOrThrow", MethodType.methodType(memSegCls, String.class)), defaultLookupStatic);
/*     */ 
/*     */ 
/*     */         
/* 131 */         Object longLayout = lookup.findStaticGetter(valueLayoutCls, "JAVA_LONG", ofLongValueLayoutCls).invoke();
/* 132 */         Object layoutArray = Array.newInstance(memoryLayoutCls, 1);
/* 133 */         Array.set(layoutArray, 0, longLayout);
/* 134 */         MethodHandle mallocFuncDesc = MethodHandles.insertArguments(lookup
/* 135 */             .findStatic(funcDescCls, "of", 
/* 136 */               MethodType.methodType(funcDescCls, memoryLayoutCls, new Class[] { memoryLayoutArrayCls })), 0, new Object[] { longLayout, layoutArray });
/*     */         
/* 138 */         MethodHandle mallocLinker = MethodHandles.foldArguments(
/* 139 */             MethodHandles.foldArguments(downcallHandleStatic, 
/* 140 */               MethodHandles.foldArguments(findSymbol, 
/* 141 */                 MethodHandles.constant(String.class, "malloc"))), mallocFuncDesc);
/*     */         
/* 143 */         mallocMethod = mallocLinker.invoke(Array.newInstance(linkerOptionCls, 0));
/*     */ 
/*     */         
/* 146 */         MethodHandle freeFuncDesc = MethodHandles.insertArguments(lookup
/* 147 */             .findStatic(funcDescCls, "ofVoid", 
/* 148 */               MethodType.methodType(funcDescCls, memoryLayoutArrayCls)), 0, new Object[] { layoutArray });
/*     */         
/* 150 */         MethodHandle freeLinker = MethodHandles.foldArguments(
/* 151 */             MethodHandles.foldArguments(downcallHandleStatic, 
/* 152 */               MethodHandles.foldArguments(findSymbol, 
/* 153 */                 MethodHandles.constant(String.class, "free"))), freeFuncDesc);
/*     */         
/* 155 */         freeMethod = freeLinker.invoke(Array.newInstance(linkerOptionCls, 0));
/*     */ 
/*     */         
/* 158 */         MethodHandle ofAddress = lookup.findStatic(memSegCls, "ofAddress", MethodType.methodType(memSegCls, long.class));
/* 159 */         MethodHandle reinterpret = lookup.findVirtual(memSegCls, "reinterpret", 
/* 160 */             MethodType.methodType(memSegCls, long.class));
/* 161 */         MethodHandle asByteBuffer = lookup.findVirtual(memSegCls, "asByteBuffer", MethodType.methodType(ByteBuffer.class));
/* 162 */         wrapMethod = MethodHandles.filterReturnValue(
/* 163 */             MethodHandles.filterArguments(reinterpret, 0, new MethodHandle[] { ofAddress }), asByteBuffer);
/*     */ 
/*     */         
/* 166 */         error = null;
/* 167 */       } catch (Throwable throwable) {
/* 168 */         mallocMethod = null;
/* 169 */         wrapMethod = null;
/* 170 */         freeMethod = null;
/* 171 */         error = throwable;
/*     */       } 
/*     */     } else {
/* 174 */       mallocMethod = null;
/* 175 */       wrapMethod = null;
/* 176 */       freeMethod = null;
/* 177 */       error = new UnsupportedOperationException("java.lang.foreign.MemorySegment unavailable");
/*     */     } 
/*     */     
/* 180 */     if (logger != null) {
/* 181 */       if (error == null) {
/* 182 */         logger.debug("java.nio.ByteBuffer.cleaner(): available");
/*     */       } else {
/* 184 */         logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", error);
/*     */       } 
/*     */     }
/* 187 */     INVOKE_MALLOC = mallocMethod;
/* 188 */     INVOKE_CREATE_BYTEBUFFER = wrapMethod;
/* 189 */     INVOKE_FREE = freeMethod;
/*     */   }
/*     */   
/*     */   static boolean isSupported() {
/* 193 */     return (INVOKE_MALLOC != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public CleanableDirectBuffer allocate(int capacity) {
/* 198 */     return new CleanableDirectBufferImpl(capacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeDirectBuffer(ByteBuffer buffer) {
/* 203 */     throw new UnsupportedOperationException("Cannot clean arbitrary ByteBuffer instances");
/*     */   }
/*     */   
/*     */   static long malloc(int capacity) {
/*     */     long addr;
/*     */     try {
/* 209 */       addr = INVOKE_MALLOC.invokeExact(capacity);
/* 210 */     } catch (Throwable e) {
/* 211 */       throw new Error(e);
/*     */     } 
/* 213 */     if (addr == 0L) {
/* 214 */       throw new OutOfMemoryError("malloc(2) failed to allocate " + capacity + " bytes");
/*     */     }
/* 216 */     return addr;
/*     */   }
/*     */   
/*     */   static void free(long memoryAddress) {
/*     */     try {
/* 221 */       INVOKE_FREE.invokeExact(memoryAddress);
/* 222 */     } catch (Throwable e) {
/* 223 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class CleanableDirectBufferImpl implements CleanableDirectBuffer {
/*     */     private final ByteBuffer buffer;
/*     */     private final long memoryAddress;
/*     */     
/*     */     private CleanableDirectBufferImpl(int capacity) {
/* 232 */       long addr = CleanerJava24Linker.malloc(capacity);
/*     */       try {
/* 234 */         this.memoryAddress = addr;
/* 235 */         this.buffer = CleanerJava24Linker.INVOKE_CREATE_BYTEBUFFER.invokeExact(addr, capacity);
/* 236 */       } catch (Throwable throwable) {
/* 237 */         Error error = new Error(throwable);
/*     */         try {
/* 239 */           CleanerJava24Linker.free(addr);
/* 240 */         } catch (Throwable e) {
/* 241 */           error.addSuppressed(e);
/*     */         } 
/* 243 */         throw error;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer buffer() {
/* 249 */       return this.buffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clean() {
/* 254 */       CleanerJava24Linker.free(this.memoryAddress);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasMemoryAddress() {
/* 259 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public long memoryAddress() {
/* 264 */       return this.memoryAddress;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\CleanerJava24Linker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */