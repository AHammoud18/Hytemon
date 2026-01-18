/*     */ package com.hypixel.hytale.protocol.io;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtocolException
/*     */   extends RuntimeException
/*     */ {
/*     */   public ProtocolException(@Nonnull String message) {
/*  12 */     super(message);
/*     */   }
/*     */   
/*     */   public ProtocolException(@Nonnull String message, @Nonnull Throwable cause) {
/*  16 */     super(message, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException arrayTooLong(@Nonnull String fieldName, int actual, int max) {
/*  22 */     return new ProtocolException(fieldName + ": array length " + fieldName + " exceeds maximum " + actual);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException stringTooLong(@Nonnull String fieldName, int actual, int max) {
/*  28 */     return new ProtocolException(fieldName + ": string length " + fieldName + " exceeds maximum " + actual);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException dictionaryTooLarge(@Nonnull String fieldName, int actual, int max) {
/*  34 */     return new ProtocolException(fieldName + ": dictionary count " + fieldName + " exceeds maximum " + actual);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException bufferTooSmall(@Nonnull String fieldName, int required, int available) {
/*  40 */     return new ProtocolException(fieldName + ": buffer too small, need " + fieldName + " bytes but only " + required + " available");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException invalidVarInt(@Nonnull String fieldName) {
/*  46 */     return new ProtocolException(fieldName + ": invalid or incomplete VarInt");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException negativeLength(@Nonnull String fieldName, int value) {
/*  52 */     return new ProtocolException(fieldName + ": negative length " + fieldName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException invalidOffset(@Nonnull String fieldName, int offset, int bufferLength) {
/*  58 */     return new ProtocolException(fieldName + ": offset " + fieldName + " is out of bounds (buffer length: " + offset + ")");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException unknownPolymorphicType(@Nonnull String typeName, int typeId) {
/*  64 */     return new ProtocolException(typeName + ": unknown polymorphic type ID " + typeName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException duplicateKey(@Nonnull String fieldName, @Nonnull Object key) {
/*  70 */     return new ProtocolException(fieldName + ": duplicate key '" + fieldName + "'");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException invalidEnumValue(@Nonnull String enumName, int value) {
/*  76 */     return new ProtocolException(enumName + ": invalid enum value " + enumName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException arrayTooShort(@Nonnull String fieldName, int actual, int min) {
/*  82 */     return new ProtocolException(fieldName + ": array length " + fieldName + " is below minimum " + actual);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException stringTooShort(@Nonnull String fieldName, int actual, int min) {
/*  88 */     return new ProtocolException(fieldName + ": string length " + fieldName + " is below minimum " + actual);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException dictionaryTooSmall(@Nonnull String fieldName, int actual, int min) {
/*  94 */     return new ProtocolException(fieldName + ": dictionary count " + fieldName + " is below minimum " + actual);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException valueOutOfRange(@Nonnull String fieldName, @Nonnull Object value, double min, double max) {
/* 100 */     return new ProtocolException(fieldName + ": value " + fieldName + " is outside allowed range [" + String.valueOf(value) + ", " + min + "]");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException valueBelowMinimum(@Nonnull String fieldName, @Nonnull Object value, double min) {
/* 106 */     return new ProtocolException(fieldName + ": value " + fieldName + " is below minimum " + String.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ProtocolException valueAboveMaximum(@Nonnull String fieldName, @Nonnull Object value, double max) {
/* 112 */     return new ProtocolException(fieldName + ": value " + fieldName + " exceeds maximum " + String.valueOf(value));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\io\ProtocolException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */