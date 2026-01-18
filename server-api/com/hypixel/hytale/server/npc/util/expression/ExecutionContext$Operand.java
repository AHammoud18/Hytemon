/*     */ package com.hypixel.hytale.server.npc.util.expression;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class Operand
/*     */ {
/*     */   public ValueType type;
/*     */   public String string;
/*     */   public double number;
/*     */   public boolean bool;
/*     */   @Nullable
/*     */   public double[] numberArray;
/*     */   @Nullable
/*     */   public String[] stringArray;
/*     */   @Nullable
/*     */   public boolean[] boolArray;
/*     */   
/*     */   public ValueType set(String value) {
/*  46 */     reInit(ValueType.STRING);
/*  47 */     this.string = value;
/*  48 */     return this.type;
/*     */   }
/*     */   
/*     */   public ValueType set(double value) {
/*  52 */     reInit(ValueType.NUMBER);
/*  53 */     this.number = value;
/*  54 */     return this.type;
/*     */   }
/*     */   
/*     */   public ValueType set(boolean value) {
/*  58 */     reInit(ValueType.BOOLEAN);
/*  59 */     this.bool = value;
/*  60 */     return this.type;
/*     */   }
/*     */   
/*     */   public ValueType set(String[] value) {
/*  64 */     reInit(ValueType.STRING_ARRAY);
/*  65 */     this.stringArray = value;
/*  66 */     return this.type;
/*     */   }
/*     */   
/*     */   public ValueType set(double[] value) {
/*  70 */     reInit(ValueType.NUMBER_ARRAY);
/*  71 */     this.numberArray = value;
/*  72 */     return this.type;
/*     */   }
/*     */   
/*     */   public ValueType set(boolean[] value) {
/*  76 */     reInit(ValueType.BOOLEAN_ARRAY);
/*  77 */     this.boolArray = value;
/*  78 */     return this.type;
/*     */   }
/*     */   
/*     */   public ValueType setEmptyArray() {
/*  82 */     reInit(ValueType.EMPTY_ARRAY);
/*  83 */     return this.type;
/*     */   }
/*     */   
/*     */   private void reInit(ValueType type) {
/*  87 */     this.type = type;
/*  88 */     this.numberArray = null;
/*  89 */     this.stringArray = null;
/*  90 */     this.boolArray = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  96 */     return "Operand{type=" + String.valueOf(this.type) + ", string='" + this.string + "', number=" + this.number + ", bool=" + this.bool + ", numberArray=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       Arrays.toString(this.numberArray) + ", stringArray=" + 
/* 102 */       Arrays.toString((Object[])this.stringArray) + ", boolArray=" + 
/* 103 */       Arrays.toString(this.boolArray) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\ExecutionContext$Operand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */