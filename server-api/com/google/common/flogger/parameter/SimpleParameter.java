/*     */ package com.google.common.flogger.parameter;
/*     */ 
/*     */ import com.google.common.flogger.backend.FormatChar;
/*     */ import com.google.common.flogger.backend.FormatOptions;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
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
/*     */ public final class SimpleParameter
/*     */   extends Parameter
/*     */ {
/*     */   private static final int MAX_CACHED_PARAMETERS = 10;
/*     */   private static final Map<FormatChar, SimpleParameter[]> DEFAULT_PARAMETERS;
/*     */   private final FormatChar formatChar;
/*     */   private final String formatString;
/*     */   
/*     */   static {
/*  41 */     Map<FormatChar, SimpleParameter[]> map = (Map)new EnumMap<FormatChar, SimpleParameter>(FormatChar.class);
/*     */     
/*  43 */     for (FormatChar fc : FormatChar.values()) {
/*  44 */       map.put(fc, createParameterArray(fc));
/*     */     }
/*  46 */     DEFAULT_PARAMETERS = Collections.unmodifiableMap((Map)map);
/*     */   }
/*     */ 
/*     */   
/*     */   private static SimpleParameter[] createParameterArray(FormatChar formatChar) {
/*  51 */     SimpleParameter[] parameters = new SimpleParameter[10];
/*  52 */     for (int index = 0; index < 10; index++) {
/*  53 */       parameters[index] = new SimpleParameter(index, formatChar, FormatOptions.getDefault());
/*     */     }
/*  55 */     return parameters;
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
/*     */   public static SimpleParameter of(int index, FormatChar formatChar, FormatOptions options) {
/*  70 */     if (index < 10 && options.isDefault()) {
/*  71 */       return ((SimpleParameter[])DEFAULT_PARAMETERS.get(formatChar))[index];
/*     */     }
/*  73 */     return new SimpleParameter(index, formatChar, options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SimpleParameter(int index, FormatChar formatChar, FormatOptions options) {
/*  80 */     super(options, index);
/*  81 */     this.formatChar = (FormatChar)Checks.checkNotNull(formatChar, "format char");
/*     */     
/*  83 */     this
/*     */       
/*  85 */       .formatString = options.isDefault() ? formatChar.getDefaultFormatString() : buildFormatString(options, formatChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String buildFormatString(FormatOptions options, FormatChar formatChar) {
/*  92 */     char c = formatChar.getChar();
/*  93 */     c = options.shouldUpperCase() ? (char)(c & 0xFFFFFFDF) : c;
/*  94 */     return options.appendPrintfOptions(new StringBuilder("%")).append(c).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void accept(ParameterVisitor visitor, Object value) {
/*  99 */     visitor.visit(value, this.formatChar, getFormatOptions());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormat() {
/* 104 */     return this.formatString;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parameter\SimpleParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */