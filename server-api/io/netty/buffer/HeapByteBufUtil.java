/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ final class HeapByteBufUtil
/*     */ {
/*     */   static byte getByte(byte[] memory, int index) {
/*  26 */     return memory[index];
/*     */   }
/*     */   
/*     */   static short getShort(byte[] memory, int index) {
/*  30 */     if (PlatformDependent.hasVarHandle()) {
/*  31 */       return VarHandleByteBufferAccess.getShortBE(memory, index);
/*     */     }
/*  33 */     return getShort0(memory, index);
/*     */   }
/*     */   
/*     */   private static short getShort0(byte[] memory, int index) {
/*  37 */     return (short)(memory[index] << 8 | memory[index + 1] & 0xFF);
/*     */   }
/*     */   
/*     */   static short getShortLE(byte[] memory, int index) {
/*  41 */     if (PlatformDependent.hasVarHandle()) {
/*  42 */       return VarHandleByteBufferAccess.getShortLE(memory, index);
/*     */     }
/*  44 */     return (short)(memory[index] & 0xFF | memory[index + 1] << 8);
/*     */   }
/*     */   
/*     */   static int getUnsignedMedium(byte[] memory, int index) {
/*  48 */     return (memory[index] & 0xFF) << 16 | (memory[index + 1] & 0xFF) << 8 | memory[index + 2] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getUnsignedMediumLE(byte[] memory, int index) {
/*  54 */     return memory[index] & 0xFF | (memory[index + 1] & 0xFF) << 8 | (memory[index + 2] & 0xFF) << 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getInt(byte[] memory, int index) {
/*  60 */     if (PlatformDependent.hasVarHandle()) {
/*  61 */       return VarHandleByteBufferAccess.getIntBE(memory, index);
/*     */     }
/*  63 */     return getInt0(memory, index);
/*     */   }
/*     */   
/*     */   private static int getInt0(byte[] memory, int index) {
/*  67 */     return (memory[index] & 0xFF) << 24 | (memory[index + 1] & 0xFF) << 16 | (memory[index + 2] & 0xFF) << 8 | memory[index + 3] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getIntLE(byte[] memory, int index) {
/*  74 */     if (PlatformDependent.hasVarHandle()) {
/*  75 */       return VarHandleByteBufferAccess.getIntLE(memory, index);
/*     */     }
/*  77 */     return getIntLE0(memory, index);
/*     */   }
/*     */   
/*     */   private static int getIntLE0(byte[] memory, int index) {
/*  81 */     return memory[index] & 0xFF | (memory[index + 1] & 0xFF) << 8 | (memory[index + 2] & 0xFF) << 16 | (memory[index + 3] & 0xFF) << 24;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getLong(byte[] memory, int index) {
/*  88 */     if (PlatformDependent.hasVarHandle()) {
/*  89 */       return VarHandleByteBufferAccess.getLongBE(memory, index);
/*     */     }
/*  91 */     return getLong0(memory, index);
/*     */   }
/*     */   
/*     */   private static long getLong0(byte[] memory, int index) {
/*  95 */     return (memory[index] & 0xFFL) << 56L | (memory[index + 1] & 0xFFL) << 48L | (memory[index + 2] & 0xFFL) << 40L | (memory[index + 3] & 0xFFL) << 32L | (memory[index + 4] & 0xFFL) << 24L | (memory[index + 5] & 0xFFL) << 16L | (memory[index + 6] & 0xFFL) << 8L | memory[index + 7] & 0xFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getLongLE(byte[] memory, int index) {
/* 106 */     if (PlatformDependent.hasVarHandle()) {
/* 107 */       return VarHandleByteBufferAccess.getLongLE(memory, index);
/*     */     }
/* 109 */     return getLongLE0(memory, index);
/*     */   }
/*     */   
/*     */   private static long getLongLE0(byte[] memory, int index) {
/* 113 */     return memory[index] & 0xFFL | (memory[index + 1] & 0xFFL) << 8L | (memory[index + 2] & 0xFFL) << 16L | (memory[index + 3] & 0xFFL) << 24L | (memory[index + 4] & 0xFFL) << 32L | (memory[index + 5] & 0xFFL) << 40L | (memory[index + 6] & 0xFFL) << 48L | (memory[index + 7] & 0xFFL) << 56L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void setByte(byte[] memory, int index, int value) {
/* 124 */     memory[index] = (byte)(value & 0xFF);
/*     */   }
/*     */   
/*     */   static void setShort(byte[] memory, int index, int value) {
/* 128 */     if (PlatformDependent.hasVarHandle()) {
/* 129 */       VarHandleByteBufferAccess.setShortBE(memory, index, value);
/*     */       return;
/*     */     } 
/* 132 */     memory[index] = (byte)(value >>> 8);
/* 133 */     memory[index + 1] = (byte)value;
/*     */   }
/*     */   
/*     */   static void setShortLE(byte[] memory, int index, int value) {
/* 137 */     if (PlatformDependent.hasVarHandle()) {
/* 138 */       VarHandleByteBufferAccess.setShortLE(memory, index, value);
/*     */       return;
/*     */     } 
/* 141 */     memory[index] = (byte)value;
/* 142 */     memory[index + 1] = (byte)(value >>> 8);
/*     */   }
/*     */   
/*     */   static void setMedium(byte[] memory, int index, int value) {
/* 146 */     memory[index] = (byte)(value >>> 16);
/* 147 */     memory[index + 1] = (byte)(value >>> 8);
/* 148 */     memory[index + 2] = (byte)value;
/*     */   }
/*     */   
/*     */   static void setMediumLE(byte[] memory, int index, int value) {
/* 152 */     memory[index] = (byte)value;
/* 153 */     memory[index + 1] = (byte)(value >>> 8);
/* 154 */     memory[index + 2] = (byte)(value >>> 16);
/*     */   }
/*     */   
/*     */   static void setInt(byte[] memory, int index, int value) {
/* 158 */     if (PlatformDependent.hasVarHandle()) {
/* 159 */       VarHandleByteBufferAccess.setIntBE(memory, index, value);
/*     */       return;
/*     */     } 
/* 162 */     setInt0(memory, index, value);
/*     */   }
/*     */   
/*     */   private static void setInt0(byte[] memory, int index, int value) {
/* 166 */     memory[index] = (byte)(value >>> 24);
/* 167 */     memory[index + 1] = (byte)(value >>> 16);
/* 168 */     memory[index + 2] = (byte)(value >>> 8);
/* 169 */     memory[index + 3] = (byte)value;
/*     */   }
/*     */   
/*     */   static void setIntLE(byte[] memory, int index, int value) {
/* 173 */     if (PlatformDependent.hasVarHandle()) {
/* 174 */       VarHandleByteBufferAccess.setIntLE(memory, index, value);
/*     */       return;
/*     */     } 
/* 177 */     setIntLE0(memory, index, value);
/*     */   }
/*     */   
/*     */   private static void setIntLE0(byte[] memory, int index, int value) {
/* 181 */     memory[index] = (byte)value;
/* 182 */     memory[index + 1] = (byte)(value >>> 8);
/* 183 */     memory[index + 2] = (byte)(value >>> 16);
/* 184 */     memory[index + 3] = (byte)(value >>> 24);
/*     */   }
/*     */   
/*     */   static void setLong(byte[] memory, int index, long value) {
/* 188 */     if (PlatformDependent.hasVarHandle()) {
/* 189 */       VarHandleByteBufferAccess.setLongBE(memory, index, value);
/*     */       return;
/*     */     } 
/* 192 */     setLong0(memory, index, value);
/*     */   }
/*     */   
/*     */   private static void setLong0(byte[] memory, int index, long value) {
/* 196 */     memory[index] = (byte)(int)(value >>> 56L);
/* 197 */     memory[index + 1] = (byte)(int)(value >>> 48L);
/* 198 */     memory[index + 2] = (byte)(int)(value >>> 40L);
/* 199 */     memory[index + 3] = (byte)(int)(value >>> 32L);
/* 200 */     memory[index + 4] = (byte)(int)(value >>> 24L);
/* 201 */     memory[index + 5] = (byte)(int)(value >>> 16L);
/* 202 */     memory[index + 6] = (byte)(int)(value >>> 8L);
/* 203 */     memory[index + 7] = (byte)(int)value;
/*     */   }
/*     */   
/*     */   static void setLongLE(byte[] memory, int index, long value) {
/* 207 */     if (PlatformDependent.hasVarHandle()) {
/* 208 */       VarHandleByteBufferAccess.setLongLE(memory, index, value);
/*     */       return;
/*     */     } 
/* 211 */     setLongLE0(memory, index, value);
/*     */   }
/*     */   
/*     */   private static void setLongLE0(byte[] memory, int index, long value) {
/* 215 */     memory[index] = (byte)(int)value;
/* 216 */     memory[index + 1] = (byte)(int)(value >>> 8L);
/* 217 */     memory[index + 2] = (byte)(int)(value >>> 16L);
/* 218 */     memory[index + 3] = (byte)(int)(value >>> 24L);
/* 219 */     memory[index + 4] = (byte)(int)(value >>> 32L);
/* 220 */     memory[index + 5] = (byte)(int)(value >>> 40L);
/* 221 */     memory[index + 6] = (byte)(int)(value >>> 48L);
/* 222 */     memory[index + 7] = (byte)(int)(value >>> 56L);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\HeapByteBufUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */