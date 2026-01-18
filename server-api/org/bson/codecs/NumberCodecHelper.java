/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import org.bson.BsonInvalidOperationException;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.types.Decimal128;
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
/*     */ final class NumberCodecHelper
/*     */ {
/*     */   static int decodeInt(BsonReader reader) {
/*     */     int intValue;
/*     */     long longValue;
/*     */     double doubleValue;
/*     */     Decimal128 decimal128;
/*  32 */     BsonType bsonType = reader.getCurrentBsonType();
/*  33 */     switch (bsonType) {
/*     */       case INT32:
/*  35 */         intValue = reader.readInt32();
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
/*  61 */         return intValue;case INT64: longValue = reader.readInt64(); intValue = (int)longValue; if (longValue != intValue) throw invalidConversion(Integer.class, Long.valueOf(longValue));  return intValue;case DOUBLE: doubleValue = reader.readDouble(); intValue = (int)doubleValue; if (doubleValue != intValue) throw invalidConversion(Integer.class, Double.valueOf(doubleValue));  return intValue;case DECIMAL128: decimal128 = reader.readDecimal128(); intValue = decimal128.intValue(); if (!decimal128.equals(new Decimal128(intValue))) throw invalidConversion(Integer.class, decimal128);  return intValue;
/*     */     } 
/*     */     throw new BsonInvalidOperationException(String.format("Invalid numeric type, found: %s", new Object[] { bsonType })); } static long decodeLong(BsonReader reader) { long longValue;
/*     */     double doubleValue;
/*     */     Decimal128 decimal128;
/*  66 */     BsonType bsonType = reader.getCurrentBsonType();
/*  67 */     switch (bsonType) {
/*     */       case INT32:
/*  69 */         longValue = reader.readInt32();
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
/*  91 */         return longValue;case INT64: longValue = reader.readInt64(); return longValue;case DOUBLE: doubleValue = reader.readDouble(); longValue = (long)doubleValue; if (doubleValue != longValue) throw invalidConversion(Long.class, Double.valueOf(doubleValue));  return longValue;case DECIMAL128: decimal128 = reader.readDecimal128(); longValue = decimal128.longValue(); if (!decimal128.equals(new Decimal128(longValue))) throw invalidConversion(Long.class, decimal128);  return longValue;
/*     */     } 
/*     */     throw new BsonInvalidOperationException(String.format("Invalid numeric type, found: %s", new Object[] { bsonType })); } static double decodeDouble(BsonReader reader) { double doubleValue;
/*     */     long longValue;
/*     */     Decimal128 decimal128;
/*  96 */     BsonType bsonType = reader.getCurrentBsonType();
/*  97 */     switch (bsonType) {
/*     */       case INT32:
/*  99 */         doubleValue = reader.readInt32();
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
/* 125 */         return doubleValue;case INT64: longValue = reader.readInt64(); doubleValue = longValue; if (longValue != (long)doubleValue) throw invalidConversion(Double.class, Long.valueOf(longValue));  return doubleValue;case DOUBLE: doubleValue = reader.readDouble(); return doubleValue;case DECIMAL128: decimal128 = reader.readDecimal128(); try { doubleValue = decimal128.doubleValue(); if (!decimal128.equals(new Decimal128(new BigDecimal(doubleValue)))) throw invalidConversion(Double.class, decimal128);  } catch (NumberFormatException e) { throw invalidConversion(Double.class, decimal128); }  return doubleValue;
/*     */     } 
/*     */     throw new BsonInvalidOperationException(String.format("Invalid numeric type, found: %s", new Object[] { bsonType })); }
/*     */    private static <T extends Number> BsonInvalidOperationException invalidConversion(Class<T> clazz, Number value) {
/* 129 */     return new BsonInvalidOperationException(String.format("Could not convert `%s` to a %s without losing precision", new Object[] { value, clazz }));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\NumberCodecHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */