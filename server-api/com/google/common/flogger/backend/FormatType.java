/*     */ package com.google.common.flogger.backend;
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
/*     */ public enum FormatType
/*     */ {
/*  27 */   GENERAL(false, true)
/*     */   {
/*     */     public boolean canFormat(Object arg) {
/*  30 */       return true;
/*     */     }
/*     */   },
/*     */ 
/*     */   
/*  35 */   BOOLEAN(false, false)
/*     */   {
/*     */     public boolean canFormat(Object arg) {
/*  38 */       return arg instanceof Boolean;
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   CHARACTER(false, false)
/*     */   {
/*     */     public boolean canFormat(Object arg)
/*     */     {
/*  50 */       if (arg instanceof Character)
/*  51 */         return true; 
/*  52 */       if (arg instanceof Integer || arg instanceof Byte || arg instanceof Short) {
/*  53 */         return Character.isValidCodePoint(((Number)arg).intValue());
/*     */       }
/*  55 */       return false;
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   INTEGRAL(true, false)
/*     */   {
/*     */     public boolean canFormat(Object arg)
/*     */     {
/*  70 */       return (arg instanceof Integer || arg instanceof Long || arg instanceof Byte || arg instanceof Short || arg instanceof java.math.BigInteger);
/*     */     }
/*     */   },
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
/*  84 */   FLOAT(true, true)
/*     */   {
/*     */     public boolean canFormat(Object arg)
/*     */     {
/*  88 */       return (arg instanceof Double || arg instanceof Float || arg instanceof java.math.BigDecimal);
/*     */     }
/*     */   };
/*     */   
/*     */   private final boolean isNumeric;
/*     */   private final boolean supportsPrecision;
/*     */   
/*     */   FormatType(boolean isNumeric, boolean supportsPrecision) {
/*  96 */     this.isNumeric = isNumeric;
/*  97 */     this.supportsPrecision = supportsPrecision;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean supportsPrecision() {
/* 106 */     return this.supportsPrecision;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNumeric() {
/* 114 */     return this.isNumeric;
/*     */   }
/*     */   
/*     */   public abstract boolean canFormat(Object paramObject);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\FormatType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */