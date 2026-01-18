/*    */ package com.google.common.flogger.parameter;
/*    */ 
/*    */ import com.google.common.flogger.backend.FormatOptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DateTimeParameter
/*    */   extends Parameter
/*    */ {
/*    */   private final DateTimeFormat format;
/*    */   private final String formatString;
/*    */   
/*    */   public static Parameter of(DateTimeFormat format, FormatOptions options, int index) {
/* 37 */     return new DateTimeParameter(options, index, format);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private DateTimeParameter(FormatOptions options, int index, DateTimeFormat format) {
/* 44 */     super(options, index);
/* 45 */     this.format = format;
/* 46 */     this
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 51 */       .formatString = options.appendPrintfOptions(new StringBuilder("%")).append(options.shouldUpperCase() ? 84 : 116).append(format.getChar()).toString();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void accept(ParameterVisitor visitor, Object value) {
/* 56 */     visitor.visitDateTime(value, this.format, getFormatOptions());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFormat() {
/* 61 */     return this.formatString;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parameter\DateTimeParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */