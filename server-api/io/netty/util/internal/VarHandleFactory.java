/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.invoke.VarHandle;
/*     */ import java.nio.ByteOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class VarHandleFactory
/*     */ {
/*     */   private static final MethodHandle FIND_VAR_HANDLE;
/*     */   private static final MethodHandle PRIVATE_LOOKUP_IN;
/*     */   private static final VarHandle LONG_LE_ARRAY_VIEW;
/*     */   private static final VarHandle LONG_BE_ARRAY_VIEW;
/*     */   private static final VarHandle INT_LE_ARRAY_VIEW;
/*     */   private static final VarHandle INT_BE_ARRAY_VIEW;
/*     */   private static final VarHandle SHORT_LE_ARRAY_VIEW;
/*     */   private static final VarHandle SHORT_BE_ARRAY_VIEW;
/*     */   private static final VarHandle LONG_LE_BYTE_BUFFER_VIEW;
/*     */   private static final VarHandle LONG_BE_BYTE_BUFFER_VIEW;
/*     */   private static final VarHandle INT_LE_BYTE_BUFFER_VIEW;
/*     */   private static final VarHandle INT_BE_BYTE_BUFFER_VIEW;
/*     */   private static final VarHandle SHORT_LE_BYTE_BUFFER_VIEW;
/*     */   private static final VarHandle SHORT_BE_BYTE_BUFFER_VIEW;
/*     */   private static final Throwable UNAVAILABILITY_CAUSE;
/*     */   
/*     */   static {
/*  47 */     MethodHandle findVarHandle = null;
/*  48 */     MethodHandle privateLookupIn = null;
/*  49 */     VarHandle longLeArrayViewHandle = null;
/*  50 */     VarHandle longBeArrayViewHandle = null;
/*  51 */     VarHandle intLeArrayViewHandle = null;
/*  52 */     VarHandle intBeArrayViewHandle = null;
/*  53 */     VarHandle shortLeArrayViewHandle = null;
/*  54 */     VarHandle shortBeArrayViewHandle = null;
/*  55 */     VarHandle longLeByteBufferViewHandle = null;
/*  56 */     VarHandle longBeByteBufferViewHandle = null;
/*  57 */     VarHandle intLeByteBufferViewHandle = null;
/*  58 */     VarHandle intBeByteBufferViewHandle = null;
/*  59 */     VarHandle shortLeByteBufferViewHandle = null;
/*  60 */     VarHandle shortBeByteBufferViewHandle = null;
/*  61 */     Throwable error = null;
/*     */     try {
/*  63 */       MethodHandles.Lookup lookup = MethodHandles.lookup();
/*  64 */       findVarHandle = lookup.findVirtual(MethodHandles.Lookup.class, "findVarHandle", 
/*  65 */           MethodType.methodType(VarHandle.class, Class.class, new Class[] { String.class, Class.class }));
/*  66 */       privateLookupIn = lookup.findStatic(MethodHandles.class, "privateLookupIn", 
/*  67 */           MethodType.methodType(MethodHandles.Lookup.class, Class.class, new Class[] { MethodHandles.Lookup.class }));
/*  68 */       MethodHandle byteArrayViewHandle = lookup.findStatic(MethodHandles.class, "byteArrayViewVarHandle", 
/*  69 */           MethodType.methodType(VarHandle.class, Class.class, new Class[] { ByteOrder.class }));
/*  70 */       longLeArrayViewHandle = byteArrayViewHandle.invokeExact(long[].class, ByteOrder.LITTLE_ENDIAN);
/*  71 */       longBeArrayViewHandle = byteArrayViewHandle.invokeExact(long[].class, ByteOrder.BIG_ENDIAN);
/*  72 */       intLeArrayViewHandle = byteArrayViewHandle.invokeExact(int[].class, ByteOrder.LITTLE_ENDIAN);
/*  73 */       intBeArrayViewHandle = byteArrayViewHandle.invokeExact(int[].class, ByteOrder.BIG_ENDIAN);
/*  74 */       shortLeArrayViewHandle = byteArrayViewHandle.invokeExact(short[].class, ByteOrder.LITTLE_ENDIAN);
/*     */       
/*  76 */       shortBeArrayViewHandle = byteArrayViewHandle.invokeExact(short[].class, ByteOrder.BIG_ENDIAN);
/*  77 */       MethodHandle byteBufferViewHandle = lookup.findStatic(MethodHandles.class, "byteBufferViewVarHandle", 
/*  78 */           MethodType.methodType(VarHandle.class, Class.class, new Class[] { ByteOrder.class }));
/*  79 */       longLeByteBufferViewHandle = byteBufferViewHandle.invokeExact(long[].class, ByteOrder.LITTLE_ENDIAN);
/*     */       
/*  81 */       longBeByteBufferViewHandle = byteBufferViewHandle.invokeExact(long[].class, ByteOrder.BIG_ENDIAN);
/*     */       
/*  83 */       intLeByteBufferViewHandle = byteBufferViewHandle.invokeExact(int[].class, ByteOrder.LITTLE_ENDIAN);
/*     */       
/*  85 */       intBeByteBufferViewHandle = byteBufferViewHandle.invokeExact(int[].class, ByteOrder.BIG_ENDIAN);
/*     */       
/*  87 */       shortLeByteBufferViewHandle = byteBufferViewHandle.invokeExact(short[].class, ByteOrder.LITTLE_ENDIAN);
/*     */       
/*  89 */       shortBeByteBufferViewHandle = byteBufferViewHandle.invokeExact(short[].class, ByteOrder.BIG_ENDIAN);
/*     */       
/*  91 */       error = null;
/*  92 */     } catch (Throwable e) {
/*  93 */       error = e;
/*  94 */       findVarHandle = null;
/*  95 */       privateLookupIn = null;
/*  96 */       longLeArrayViewHandle = null;
/*  97 */       longBeArrayViewHandle = null;
/*  98 */       intLeArrayViewHandle = null;
/*  99 */       intBeArrayViewHandle = null;
/* 100 */       shortLeArrayViewHandle = null;
/* 101 */       longLeByteBufferViewHandle = null;
/* 102 */       longBeByteBufferViewHandle = null;
/* 103 */       intLeByteBufferViewHandle = null;
/* 104 */       intBeByteBufferViewHandle = null;
/* 105 */       shortLeByteBufferViewHandle = null;
/* 106 */       shortBeByteBufferViewHandle = null;
/*     */     } finally {
/* 108 */       FIND_VAR_HANDLE = findVarHandle;
/* 109 */       PRIVATE_LOOKUP_IN = privateLookupIn;
/* 110 */       LONG_LE_ARRAY_VIEW = longLeArrayViewHandle;
/* 111 */       LONG_BE_ARRAY_VIEW = longBeArrayViewHandle;
/* 112 */       INT_LE_ARRAY_VIEW = intLeArrayViewHandle;
/* 113 */       INT_BE_ARRAY_VIEW = intBeArrayViewHandle;
/* 114 */       SHORT_LE_ARRAY_VIEW = shortLeArrayViewHandle;
/* 115 */       SHORT_BE_ARRAY_VIEW = shortBeArrayViewHandle;
/* 116 */       LONG_LE_BYTE_BUFFER_VIEW = longLeByteBufferViewHandle;
/* 117 */       LONG_BE_BYTE_BUFFER_VIEW = longBeByteBufferViewHandle;
/* 118 */       INT_LE_BYTE_BUFFER_VIEW = intLeByteBufferViewHandle;
/* 119 */       INT_BE_BYTE_BUFFER_VIEW = intBeByteBufferViewHandle;
/* 120 */       SHORT_LE_BYTE_BUFFER_VIEW = shortLeByteBufferViewHandle;
/* 121 */       SHORT_BE_BYTE_BUFFER_VIEW = shortBeByteBufferViewHandle;
/* 122 */       UNAVAILABILITY_CAUSE = error;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isSupported() {
/* 127 */     return (UNAVAILABILITY_CAUSE == null);
/*     */   }
/*     */   
/*     */   public static Throwable unavailableCause() {
/* 131 */     return UNAVAILABILITY_CAUSE;
/*     */   }
/*     */   
/*     */   private static MethodHandles.Lookup privateLookup(MethodHandles.Lookup lookup, Class<?> targetClass) {
/*     */     try {
/* 136 */       return PRIVATE_LOOKUP_IN.invokeExact(targetClass, lookup);
/* 137 */     } catch (Throwable e) {
/* 138 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static VarHandle privateFindVarHandle(MethodHandles.Lookup lookup, Class<?> declaringClass, String name, Class<?> type) {
/*     */     try {
/* 145 */       return FIND_VAR_HANDLE.invokeExact(privateLookup(lookup, declaringClass), declaringClass, name, type);
/*     */     }
/* 147 */     catch (Throwable e) {
/* 148 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static VarHandle longLeArrayView() {
/* 153 */     return LONG_LE_ARRAY_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle longBeArrayView() {
/* 157 */     return LONG_BE_ARRAY_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle intLeArrayView() {
/* 161 */     return INT_LE_ARRAY_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle intBeArrayView() {
/* 165 */     return INT_BE_ARRAY_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle shortLeArrayView() {
/* 169 */     return SHORT_LE_ARRAY_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle shortBeArrayView() {
/* 173 */     return SHORT_BE_ARRAY_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle longLeByteBufferView() {
/* 177 */     return LONG_LE_BYTE_BUFFER_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle longBeByteBufferView() {
/* 181 */     return LONG_BE_BYTE_BUFFER_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle intLeByteBufferView() {
/* 185 */     return INT_LE_BYTE_BUFFER_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle intBeByteBufferView() {
/* 189 */     return INT_BE_BYTE_BUFFER_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle shortLeByteBufferView() {
/* 193 */     return SHORT_LE_BYTE_BUFFER_VIEW;
/*     */   }
/*     */   
/*     */   public static VarHandle shortBeByteBufferView() {
/* 197 */     return SHORT_BE_BYTE_BUFFER_VIEW;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\VarHandleFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */