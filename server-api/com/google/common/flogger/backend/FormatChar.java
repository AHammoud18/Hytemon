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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum FormatChar
/*     */ {
/*  36 */   STRING('s', FormatType.GENERAL, "-#", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   BOOLEAN('b', FormatType.BOOLEAN, "-", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   CHAR('c', FormatType.CHARACTER, "-", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   DECIMAL('d', FormatType.INTEGRAL, "-0+ ,(", false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   OCTAL('o', FormatType.INTEGRAL, "-#0(", false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   HEX('x', FormatType.INTEGRAL, "-#0(", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   FLOAT('f', FormatType.FLOAT, "-#0+ ,(", false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   EXPONENT('e', FormatType.FLOAT, "-#0+ (", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   GENERAL('g', FormatType.FLOAT, "-0+ ,(", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   EXPONENT_HEX('a', FormatType.FLOAT, "-#0+ ", true);
/*     */   private static final FormatChar[] MAP;
/*     */   private final char formatChar;
/*     */   
/*     */   private static int indexOf(char letter) {
/* 113 */     return (letter | 0x20) - 97;
/*     */   }
/*     */   private final FormatType type; private final int allowedFlags; private final String defaultFormatString;
/*     */   
/*     */   private static boolean isLowerCase(char letter) {
/* 118 */     return ((letter & 0x20) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 124 */     MAP = new FormatChar[26];
/*     */     
/* 126 */     for (FormatChar fc : values()) {
/* 127 */       MAP[indexOf(fc.getChar())] = fc;
/*     */     }
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
/*     */   public static FormatChar of(char c) {
/* 140 */     FormatChar fc = MAP[indexOf(c)];
/* 141 */     if (isLowerCase(c))
/*     */     {
/* 143 */       return fc;
/*     */     }
/*     */     
/* 146 */     return (fc != null && fc.hasUpperCaseVariant()) ? fc : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FormatChar(char c, FormatType type, String allowedFlagChars, boolean hasUpperCaseVariant) {
/* 155 */     this.formatChar = c;
/* 156 */     this.type = type;
/* 157 */     this.allowedFlags = FormatOptions.parseValidFlags(allowedFlagChars, hasUpperCaseVariant);
/* 158 */     this.defaultFormatString = "%" + c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getChar() {
/* 169 */     return this.formatChar;
/*     */   }
/*     */ 
/*     */   
/*     */   public FormatType getType() {
/* 174 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getAllowedFlags() {
/* 182 */     return this.allowedFlags;
/*     */   }
/*     */   
/*     */   private boolean hasUpperCaseVariant() {
/* 186 */     return ((this.allowedFlags & 0x80) != 0);
/*     */   }
/*     */   
/*     */   public String getDefaultFormatString() {
/* 190 */     return this.defaultFormatString;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\FormatChar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */