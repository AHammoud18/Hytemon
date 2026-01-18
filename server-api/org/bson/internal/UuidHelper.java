/*     */ package org.bson.internal;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.UUID;
/*     */ import org.bson.BSONException;
/*     */ import org.bson.BsonBinarySubType;
/*     */ import org.bson.BsonSerializationException;
/*     */ import org.bson.UuidRepresentation;
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
/*     */ public final class UuidHelper
/*     */ {
/*     */   private static void writeLongToArrayBigEndian(byte[] bytes, int offset, long x) {
/*  32 */     bytes[offset + 7] = (byte)(int)(0xFFL & x);
/*  33 */     bytes[offset + 6] = (byte)(int)(0xFFL & x >> 8L);
/*  34 */     bytes[offset + 5] = (byte)(int)(0xFFL & x >> 16L);
/*  35 */     bytes[offset + 4] = (byte)(int)(0xFFL & x >> 24L);
/*  36 */     bytes[offset + 3] = (byte)(int)(0xFFL & x >> 32L);
/*  37 */     bytes[offset + 2] = (byte)(int)(0xFFL & x >> 40L);
/*  38 */     bytes[offset + 1] = (byte)(int)(0xFFL & x >> 48L);
/*  39 */     bytes[offset] = (byte)(int)(0xFFL & x >> 56L);
/*     */   }
/*     */   
/*     */   private static long readLongFromArrayBigEndian(byte[] bytes, int offset) {
/*  43 */     long x = 0L;
/*  44 */     x |= 0xFFL & bytes[offset + 7];
/*  45 */     x |= (0xFFL & bytes[offset + 6]) << 8L;
/*  46 */     x |= (0xFFL & bytes[offset + 5]) << 16L;
/*  47 */     x |= (0xFFL & bytes[offset + 4]) << 24L;
/*  48 */     x |= (0xFFL & bytes[offset + 3]) << 32L;
/*  49 */     x |= (0xFFL & bytes[offset + 2]) << 40L;
/*  50 */     x |= (0xFFL & bytes[offset + 1]) << 48L;
/*  51 */     x |= (0xFFL & bytes[offset]) << 56L;
/*  52 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void reverseByteArray(byte[] data, int start, int length) {
/*  57 */     for (int left = start, right = start + length - 1; left < right; left++, right--) {
/*     */       
/*  59 */       byte temp = data[left];
/*  60 */       data[left] = data[right];
/*  61 */       data[right] = temp;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static byte[] encodeUuidToBinary(UUID uuid, UuidRepresentation uuidRepresentation) {
/*  66 */     byte[] binaryData = new byte[16];
/*  67 */     writeLongToArrayBigEndian(binaryData, 0, uuid.getMostSignificantBits());
/*  68 */     writeLongToArrayBigEndian(binaryData, 8, uuid.getLeastSignificantBits());
/*  69 */     switch (uuidRepresentation) {
/*     */       case C_SHARP_LEGACY:
/*  71 */         reverseByteArray(binaryData, 0, 4);
/*  72 */         reverseByteArray(binaryData, 4, 2);
/*  73 */         reverseByteArray(binaryData, 6, 2);
/*     */       
/*     */       case JAVA_LEGACY:
/*  76 */         reverseByteArray(binaryData, 0, 8);
/*  77 */         reverseByteArray(binaryData, 8, 8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case PYTHON_LEGACY:
/*     */       case STANDARD:
/*  86 */         return binaryData;
/*     */     } 
/*     */     throw new BSONException("Unexpected UUID representation: " + uuidRepresentation);
/*     */   }
/*     */   public static UUID decodeBinaryToUuid(byte[] data, byte type, UuidRepresentation uuidRepresentation) {
/*  91 */     if (data.length != 16) {
/*  92 */       throw new BsonSerializationException(String.format("Expected length to be 16, not %d.", new Object[] { Integer.valueOf(data.length) }));
/*     */     }
/*     */     
/*  95 */     byte[] localData = data;
/*     */     
/*  97 */     if (type == BsonBinarySubType.UUID_LEGACY.getValue()) {
/*  98 */       switch (uuidRepresentation) {
/*     */         case C_SHARP_LEGACY:
/* 100 */           localData = Arrays.copyOf(data, 16);
/*     */           
/* 102 */           reverseByteArray(localData, 0, 4);
/* 103 */           reverseByteArray(localData, 4, 2);
/* 104 */           reverseByteArray(localData, 6, 2);
/*     */         
/*     */         case JAVA_LEGACY:
/* 107 */           localData = Arrays.copyOf(data, 16);
/*     */           
/* 109 */           reverseByteArray(localData, 0, 8);
/* 110 */           reverseByteArray(localData, 8, 8);
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
/*     */         case PYTHON_LEGACY:
/* 122 */           return new UUID(readLongFromArrayBigEndian(localData, 0), readLongFromArrayBigEndian(localData, 8));
/*     */         case STANDARD:
/*     */           throw new BSONException("Can not decode a subtype 3 (UUID legacy) BSON binary when the decoder is configured to use the standard UUID representation");
/*     */       } 
/*     */       throw new BSONException("Unexpected UUID representation: " + uuidRepresentation);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\internal\UuidHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */