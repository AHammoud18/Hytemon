/*     */ package com.google.common.flogger.parser;
/*     */ 
/*     */ import com.google.common.flogger.backend.FormatChar;
/*     */ import com.google.common.flogger.backend.FormatOptions;
/*     */ import com.google.common.flogger.parameter.DateTimeFormat;
/*     */ import com.google.common.flogger.parameter.DateTimeParameter;
/*     */ import com.google.common.flogger.parameter.Parameter;
/*     */ import com.google.common.flogger.parameter.ParameterVisitor;
/*     */ import com.google.common.flogger.parameter.SimpleParameter;
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
/*     */ public class DefaultPrintfMessageParser
/*     */   extends PrintfMessageParser
/*     */ {
/*  39 */   private static final PrintfMessageParser INSTANCE = new DefaultPrintfMessageParser();
/*     */   
/*     */   public static PrintfMessageParser getInstance() {
/*  42 */     return INSTANCE;
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
/*     */   public int parsePrintfTerm(MessageBuilder<?> builder, int index, String message, int termStart, int specStart, int formatStart) throws ParseException {
/*     */     Parameter parameter;
/*  58 */     int termEnd = formatStart + 1;
/*     */     
/*  60 */     char typeChar = message.charAt(formatStart);
/*  61 */     boolean isUpperCase = ((typeChar & 0x20) == 0);
/*  62 */     FormatOptions options = FormatOptions.parse(message, specStart, formatStart, isUpperCase);
/*     */ 
/*     */     
/*  65 */     FormatChar formatChar = FormatChar.of(typeChar);
/*  66 */     if (formatChar != null) {
/*  67 */       if (!options.areValidFor(formatChar)) {
/*  68 */         throw ParseException.withBounds("invalid format specifier", message, termStart, termEnd);
/*     */       }
/*  70 */       SimpleParameter simpleParameter = SimpleParameter.of(index, formatChar, options);
/*  71 */     } else if (typeChar == 't' || typeChar == 'T') {
/*  72 */       if (!options.validate(160, false)) {
/*  73 */         throw ParseException.withBounds("invalid format specification", message, termStart, termEnd);
/*     */       }
/*     */ 
/*     */       
/*  77 */       termEnd++;
/*  78 */       if (termEnd > message.length()) {
/*  79 */         throw ParseException.atPosition("truncated format specifier", message, termStart);
/*     */       }
/*  81 */       DateTimeFormat dateTimeFormat = DateTimeFormat.of(message.charAt(formatStart + 1));
/*  82 */       if (dateTimeFormat == null) {
/*  83 */         throw ParseException.atPosition("illegal date/time conversion", message, formatStart + 1);
/*     */       }
/*  85 */       parameter = DateTimeParameter.of(dateTimeFormat, options, index);
/*  86 */     } else if (typeChar == 'h' || typeChar == 'H') {
/*     */ 
/*     */       
/*  89 */       if (!options.validate(160, false)) {
/*  90 */         throw ParseException.withBounds("invalid format specification", message, termStart, termEnd);
/*     */       }
/*     */       
/*  93 */       parameter = wrapHexParameter(options, index);
/*     */     } else {
/*  95 */       throw ParseException.withBounds("invalid format specification", message, termStart, formatStart + 1);
/*     */     } 
/*     */     
/*  98 */     builder.addParameter(termStart, termEnd, parameter);
/*  99 */     return termEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Parameter wrapHexParameter(final FormatOptions options, int index) {
/* 105 */     return new Parameter(options, index)
/*     */       {
/*     */         protected void accept(ParameterVisitor visitor, Object value) {
/* 108 */           visitor.visit(Integer.valueOf(value.hashCode()), FormatChar.HEX, getFormatOptions());
/*     */         }
/*     */ 
/*     */         
/*     */         public String getFormat() {
/* 113 */           return options.shouldUpperCase() ? "%H" : "%h";
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parser\DefaultPrintfMessageParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */