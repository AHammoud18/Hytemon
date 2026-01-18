/*     */ package com.hypixel.hytale.math.data;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class VarInt
/*     */ {
/*     */   private VarInt() {
/*  15 */     throw new UnsupportedOperationException("Do not instantiate.");
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
/*     */   public static void writeSignedVarLong(long value, @Nonnull DataOutput out) throws IOException {
/*  31 */     writeUnsignedVarLong(value << 1L ^ value >> 63L, out);
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
/*     */   public static void writeUnsignedVarLong(long value, @Nonnull DataOutput out) throws IOException {
/*  46 */     while ((value & 0xFFFFFFFFFFFFFF80L) != 0L) {
/*  47 */       out.writeByte((int)value & 0x7F | 0x80);
/*  48 */       value >>>= 7L;
/*     */     } 
/*  50 */     out.writeByte((int)value & 0x7F);
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
/*     */   public static void writeSignedVarInt(int value, @Nonnull DataOutput out) throws IOException {
/*  66 */     writeUnsignedVarInt(value << 1 ^ value >> 31, out);
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
/*     */   public static void writeUnsignedVarInt(int value, @Nonnull DataOutput out) throws IOException {
/*  81 */     while ((value & 0xFFFFFF80) != 0L) {
/*  82 */       out.writeByte(value & 0x7F | 0x80);
/*  83 */       value >>>= 7;
/*     */     } 
/*  85 */     out.writeByte(value & 0x7F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] writeSignedVarInt(int value) {
/*  90 */     return writeUnsignedVarInt(value << 1 ^ value >> 31);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] writeUnsignedVarInt(int value) {
/* 101 */     byte[] byteArrayList = new byte[10];
/* 102 */     int i = 0;
/* 103 */     while ((value & 0xFFFFFF80) != 0L) {
/* 104 */       byteArrayList[i++] = (byte)(value & 0x7F | 0x80);
/* 105 */       value >>>= 7;
/*     */     } 
/* 107 */     byteArrayList[i] = (byte)(value & 0x7F);
/* 108 */     byte[] out = new byte[i + 1];
/* 109 */     for (; i >= 0; i--) {
/* 110 */       out[i] = byteArrayList[i];
/*     */     }
/* 112 */     return out;
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
/*     */   public static long readSignedVarLong(@Nonnull DataInput in) throws IOException {
/* 124 */     long raw = readUnsignedVarLong(in);
/*     */     
/* 126 */     long temp = (raw << 63L >> 63L ^ raw) >> 1L;
/*     */ 
/*     */ 
/*     */     
/* 130 */     return temp ^ raw & Long.MIN_VALUE;
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
/*     */   public static long readUnsignedVarLong(@Nonnull DataInput in) throws IOException {
/* 142 */     long value = 0L;
/* 143 */     int i = 0;
/*     */     long b;
/* 145 */     while (((b = in.readByte()) & 0x80L) != 0L) {
/* 146 */       value |= (b & 0x7FL) << i;
/* 147 */       i += 7;
/* 148 */       if (i > 63) {
/* 149 */         throw new IllegalArgumentException("Variable length quantity is too long");
/*     */       }
/*     */     } 
/* 152 */     return value | b << i;
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
/*     */   public static int readSignedVarInt(@Nonnull DataInput in) throws IOException {
/* 164 */     int raw = readUnsignedVarInt(in);
/*     */     
/* 166 */     int temp = (raw << 31 >> 31 ^ raw) >> 1;
/*     */ 
/*     */ 
/*     */     
/* 170 */     return temp ^ raw & Integer.MIN_VALUE;
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
/*     */   public static int readUnsignedVarInt(@Nonnull DataInput in) throws IOException {
/* 182 */     int value = 0;
/* 183 */     int i = 0;
/*     */     int b;
/* 185 */     while (((b = in.readByte()) & 0x80) != 0) {
/* 186 */       value |= (b & 0x7F) << i;
/* 187 */       i += 7;
/* 188 */       if (i > 35) {
/* 189 */         throw new IllegalArgumentException("Variable length quantity is too long");
/*     */       }
/*     */     } 
/* 192 */     return value | b << i;
/*     */   }
/*     */   
/*     */   public static int readSignedVarInt(@Nonnull byte[] bytes) {
/* 196 */     int raw = readUnsignedVarInt(bytes);
/*     */     
/* 198 */     int temp = (raw << 31 >> 31 ^ raw) >> 1;
/*     */ 
/*     */ 
/*     */     
/* 202 */     return temp ^ raw & Integer.MIN_VALUE;
/*     */   }
/*     */   
/*     */   public static int readUnsignedVarInt(@Nonnull byte[] bytes) {
/* 206 */     int value = 0;
/* 207 */     int i = 0;
/* 208 */     byte rb = Byte.MIN_VALUE;
/* 209 */     for (byte b : bytes) {
/* 210 */       rb = b;
/* 211 */       if ((b & 0x80) == 0) {
/*     */         break;
/*     */       }
/* 214 */       value |= (b & Byte.MAX_VALUE) << i;
/* 215 */       i += 7;
/* 216 */       if (i > 35) {
/* 217 */         throw new IllegalArgumentException("Variable length quantity is too long");
/*     */       }
/*     */     } 
/* 220 */     return value | rb << i;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\data\VarInt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */