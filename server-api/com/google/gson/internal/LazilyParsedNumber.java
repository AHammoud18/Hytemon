/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.math.BigDecimal;
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
/*     */ public final class LazilyParsedNumber
/*     */   extends Number
/*     */ {
/*     */   private final String value;
/*     */   
/*     */   public LazilyParsedNumber(String value) {
/*  37 */     this.value = value;
/*     */   }
/*     */   
/*     */   private BigDecimal asBigDecimal() {
/*  41 */     return NumberLimits.parseBigDecimal(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int intValue() {
/*     */     try {
/*  47 */       return Integer.parseInt(this.value);
/*  48 */     } catch (NumberFormatException e) {
/*     */       try {
/*  50 */         return (int)Long.parseLong(this.value);
/*  51 */       } catch (NumberFormatException nfe) {
/*  52 */         return asBigDecimal().intValue();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long longValue() {
/*     */     try {
/*  60 */       return Long.parseLong(this.value);
/*  61 */     } catch (NumberFormatException e) {
/*  62 */       return asBigDecimal().longValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float floatValue() {
/*  68 */     return Float.parseFloat(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/*  73 */     return Double.parseDouble(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  78 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() throws ObjectStreamException {
/*  86 */     return asBigDecimal();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/*  92 */     throw new InvalidObjectException("Deserialization is unsupported");
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  97 */     return this.value.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 102 */     if (this == obj) {
/* 103 */       return true;
/*     */     }
/* 105 */     if (obj instanceof LazilyParsedNumber) {
/* 106 */       LazilyParsedNumber other = (LazilyParsedNumber)obj;
/* 107 */       return this.value.equals(other.value);
/*     */     } 
/* 109 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\LazilyParsedNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */