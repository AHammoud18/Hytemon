/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ final class VarHandleByteBufferAccess
/*     */ {
/*     */   static short getShortBE(ByteBuffer buffer, int index) {
/*  37 */     return PlatformDependent.shortBeByteBufferView().get(buffer, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setShortBE(ByteBuffer buffer, int index, int value) {
/*  42 */     PlatformDependent.shortBeByteBufferView().set(buffer, index, (short)value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static short getShortLE(ByteBuffer buffer, int index) {
/*  48 */     return PlatformDependent.shortLeByteBufferView().get(buffer, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setShortLE(ByteBuffer buffer, int index, int value) {
/*  53 */     PlatformDependent.shortLeByteBufferView().set(buffer, index, (short)value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getIntBE(ByteBuffer buffer, int index) {
/*  59 */     return PlatformDependent.intBeByteBufferView().get(buffer, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setIntBE(ByteBuffer buffer, int index, int value) {
/*  64 */     PlatformDependent.intBeByteBufferView().set(buffer, index, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getIntLE(ByteBuffer buffer, int index) {
/*  70 */     return PlatformDependent.intLeByteBufferView().get(buffer, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setIntLE(ByteBuffer buffer, int index, int value) {
/*  75 */     PlatformDependent.intLeByteBufferView().set(buffer, index, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static long getLongBE(ByteBuffer buffer, int index) {
/*  81 */     return PlatformDependent.longBeByteBufferView().get(buffer, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setLongBE(ByteBuffer buffer, int index, long value) {
/*  86 */     PlatformDependent.longBeByteBufferView().set(buffer, index, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static long getLongLE(ByteBuffer buffer, int index) {
/*  92 */     return PlatformDependent.longLeByteBufferView().get(buffer, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setLongLE(ByteBuffer buffer, int index, long value) {
/*  97 */     PlatformDependent.longLeByteBufferView().set(buffer, index, value);
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
/*     */   static short getShortBE(byte[] memory, int index) {
/* 111 */     return PlatformDependent.shortBeArrayView().get(memory, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setShortBE(byte[] memory, int index, int value) {
/* 116 */     PlatformDependent.shortBeArrayView().set(memory, index, (short)value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static short getShortLE(byte[] memory, int index) {
/* 122 */     return PlatformDependent.shortLeArrayView().get(memory, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setShortLE(byte[] memory, int index, int value) {
/* 127 */     PlatformDependent.shortLeArrayView().set(memory, index, (short)value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getIntBE(byte[] memory, int index) {
/* 133 */     return PlatformDependent.intBeArrayView().get(memory, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setIntBE(byte[] memory, int index, int value) {
/* 138 */     PlatformDependent.intBeArrayView().set(memory, index, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getIntLE(byte[] memory, int index) {
/* 144 */     return PlatformDependent.intLeArrayView().get(memory, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setIntLE(byte[] memory, int index, int value) {
/* 149 */     PlatformDependent.intLeArrayView().set(memory, index, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static long getLongBE(byte[] memory, int index) {
/* 155 */     return PlatformDependent.longBeArrayView().get(memory, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setLongBE(byte[] memory, int index, long value) {
/* 160 */     PlatformDependent.longBeArrayView().set(memory, index, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static long getLongLE(byte[] memory, int index) {
/* 166 */     return PlatformDependent.longLeArrayView().get(memory, index);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setLongLE(byte[] memory, int index, long value) {
/* 171 */     PlatformDependent.longLeArrayView().set(memory, index, value);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\VarHandleByteBufferAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */