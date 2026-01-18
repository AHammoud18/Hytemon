/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ByteBufferWriter
/*     */ {
/*     */   private static final int MIN_CACHED_BUFFER_SIZE = 1024;
/*     */   private static final int MAX_CACHED_BUFFER_SIZE = 16384;
/*     */   private static final float BUFFER_REALLOCATION_THRESHOLD = 0.5F;
/*  48 */   private static final ThreadLocal<SoftReference<byte[]>> BUFFER = new ThreadLocal<>();
/*     */ 
/*     */ 
/*     */   
/*  52 */   private static final Class<?> FILE_OUTPUT_STREAM_CLASS = safeGetClass("java.io.FileOutputStream");
/*     */   
/*  54 */   private static final long CHANNEL_FIELD_OFFSET = getChannelFieldOffset(FILE_OUTPUT_STREAM_CLASS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void clearCachedBuffer() {
/*  61 */     BUFFER.set(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void write(ByteBuffer buffer, OutputStream output) throws IOException {
/*  69 */     int initialPos = buffer.position();
/*     */     try {
/*  71 */       if (buffer.hasArray()) {
/*     */ 
/*     */         
/*  74 */         output.write(buffer.array(), buffer.arrayOffset() + buffer.position(), buffer.remaining());
/*  75 */       } else if (!writeToChannel(buffer, output)) {
/*     */ 
/*     */         
/*  78 */         byte[] array = getOrCreateBuffer(buffer.remaining());
/*  79 */         while (buffer.hasRemaining()) {
/*  80 */           int length = Math.min(buffer.remaining(), array.length);
/*  81 */           buffer.get(array, 0, length);
/*  82 */           output.write(array, 0, length);
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/*  87 */       Java8Compatibility.position(buffer, initialPos);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static byte[] getOrCreateBuffer(int requestedSize) {
/*  92 */     requestedSize = Math.max(requestedSize, 1024);
/*     */     
/*  94 */     byte[] buffer = getBuffer();
/*     */     
/*  96 */     if (buffer == null || needToReallocate(requestedSize, buffer.length)) {
/*  97 */       buffer = new byte[requestedSize];
/*     */ 
/*     */       
/* 100 */       if (requestedSize <= 16384) {
/* 101 */         setBuffer(buffer);
/*     */       }
/*     */     } 
/* 104 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean needToReallocate(int requestedSize, int bufferLength) {
/* 109 */     return (bufferLength < requestedSize && bufferLength < requestedSize * 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] getBuffer() {
/* 114 */     SoftReference<byte[]> sr = BUFFER.get();
/* 115 */     return (sr == null) ? null : sr.get();
/*     */   }
/*     */   
/*     */   private static void setBuffer(byte[] value) {
/* 119 */     BUFFER.set((SoftReference)new SoftReference<>(value));
/*     */   }
/*     */   
/*     */   private static boolean writeToChannel(ByteBuffer buffer, OutputStream output) throws IOException {
/* 123 */     if (CHANNEL_FIELD_OFFSET >= 0L && FILE_OUTPUT_STREAM_CLASS.isInstance(output)) {
/*     */       
/* 125 */       WritableByteChannel channel = null;
/*     */       try {
/* 127 */         channel = (WritableByteChannel)UnsafeUtil.getObject(output, CHANNEL_FIELD_OFFSET);
/* 128 */       } catch (ClassCastException classCastException) {}
/*     */ 
/*     */       
/* 131 */       if (channel != null) {
/* 132 */         channel.write(buffer);
/* 133 */         return true;
/*     */       } 
/*     */     } 
/* 136 */     return false;
/*     */   }
/*     */   
/*     */   private static Class<?> safeGetClass(String className) {
/*     */     try {
/* 141 */       return Class.forName(className);
/* 142 */     } catch (ClassNotFoundException e) {
/* 143 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static long getChannelFieldOffset(Class<?> clazz) {
/*     */     try {
/* 149 */       if (clazz != null && UnsafeUtil.hasUnsafeArrayOperations()) {
/* 150 */         Field field = clazz.getDeclaredField("channel");
/* 151 */         return UnsafeUtil.objectFieldOffset(field);
/*     */       } 
/* 153 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 156 */     return -1L;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ByteBufferWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */