/*     */ package com.hypixel.hytale.server.core.util.io;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.BitSetUtil;
/*     */ import com.hypixel.hytale.unsafe.UnsafeUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.BitSet;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteBufUtil
/*     */ {
/*  17 */   private static int MAX_UNSIGNED_SHORT_VALUE = 65535;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeUTF(@Nonnull ByteBuf buf, @Nonnull String string) {
/*  26 */     if (io.netty.buffer.ByteBufUtil.utf8MaxBytes(string) >= MAX_UNSIGNED_SHORT_VALUE) throw new IllegalArgumentException("String is too large");
/*     */     
/*  28 */     int before = buf.writerIndex();
/*  29 */     buf.writeShort(-1);
/*  30 */     int length = buf.writeCharSequence(string, StandardCharsets.UTF_8);
/*  31 */     if (length < 0 || length >= MAX_UNSIGNED_SHORT_VALUE) throw new IllegalArgumentException("Serialized string is too large");
/*     */     
/*  33 */     int after = buf.writerIndex();
/*  34 */     buf.writerIndex(before);
/*  35 */     buf.writeShort(length);
/*  36 */     buf.writerIndex(after);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String readUTF(@Nonnull ByteBuf buf) {
/*  47 */     int length = buf.readUnsignedShort();
/*  48 */     return buf.readCharSequence(length, StandardCharsets.UTF_8).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeByteArray(@Nonnull ByteBuf buf, @Nonnull byte[] arr) {
/*  58 */     writeByteArray(buf, arr, 0, arr.length);
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
/*     */   public static void writeByteArray(@Nonnull ByteBuf buf, byte[] arr, int src, int length) {
/*  70 */     if (length <= 0 || length >= MAX_UNSIGNED_SHORT_VALUE) throw new IllegalArgumentException("length is too large");
/*     */     
/*  72 */     buf.writeShort(length);
/*  73 */     buf.writeBytes(arr, src, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] readByteArray(@Nonnull ByteBuf buf) {
/*  83 */     int length = buf.readUnsignedShort();
/*  84 */     byte[] bytes = new byte[length];
/*  85 */     buf.readBytes(bytes);
/*  86 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBytesRelease(@Nonnull ByteBuf buf) {
/*     */     try {
/*  98 */       return io.netty.buffer.ByteBufUtil.getBytes(buf, 0, buf.writerIndex(), false);
/*     */     } finally {
/* 100 */       buf.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeNumber(@Nonnull ByteBuf buf, int bytes, int value) {
/* 111 */     switch (bytes) {
/*     */       case 1:
/* 113 */         buf.writeByte(value);
/*     */         break;
/*     */       case 2:
/* 116 */         buf.writeShort(value);
/*     */         break;
/*     */       case 4:
/* 119 */         buf.writeInt(value);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int readNumber(@Nonnull ByteBuf buf, int bytes) {
/* 125 */     switch (bytes) { case 1: case 2: case 4:  }  return 
/*     */ 
/*     */ 
/*     */       
/* 129 */       0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeBitSet(@Nonnull ByteBuf buf, @Nonnull BitSet bitset) {
/*     */     int wordsInUse;
/*     */     long[] words;
/* 136 */     if (UnsafeUtil.UNSAFE == null) {
/* 137 */       words = bitset.toLongArray();
/* 138 */       wordsInUse = words.length;
/*     */     } else {
/* 140 */       wordsInUse = UnsafeUtil.UNSAFE.getInt(bitset, BitSetUtil.WORDS_IN_USE_OFFSET);
/* 141 */       words = (long[])UnsafeUtil.UNSAFE.getObject(bitset, BitSetUtil.WORDS_OFFSET);
/*     */     } 
/*     */     
/* 144 */     buf.writeInt(wordsInUse);
/* 145 */     for (int i = 0; i < wordsInUse; i++) {
/* 146 */       buf.writeLong(words[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void readBitSet(@Nonnull ByteBuf buf, @Nonnull BitSet bitset) {
/* 151 */     int wordsInUse = buf.readInt();
/*     */     
/* 153 */     if (UnsafeUtil.UNSAFE == null) {
/* 154 */       long[] arrayOfLong = new long[wordsInUse];
/* 155 */       for (int j = 0; j < wordsInUse; j++) {
/* 156 */         arrayOfLong[j] = buf.readLong();
/*     */       }
/* 158 */       bitset.clear();
/* 159 */       bitset.or(BitSet.valueOf(arrayOfLong));
/*     */       
/*     */       return;
/*     */     } 
/* 163 */     UnsafeUtil.UNSAFE.putInt(bitset, BitSetUtil.WORDS_IN_USE_OFFSET, wordsInUse);
/*     */     
/* 165 */     long[] words = (long[])UnsafeUtil.UNSAFE.getObject(bitset, BitSetUtil.WORDS_OFFSET);
/* 166 */     if (wordsInUse > words.length) {
/* 167 */       words = new long[wordsInUse];
/* 168 */       UnsafeUtil.UNSAFE.putObject(bitset, BitSetUtil.WORDS_OFFSET, words);
/*     */     } 
/*     */     
/* 171 */     for (int i = 0; i < wordsInUse; i++)
/* 172 */       words[i] = buf.readLong(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\io\ByteBufUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */