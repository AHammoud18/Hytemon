/*    */ package com.google.common.flogger.parser;
/*    */ 
/*    */ import com.google.common.flogger.parameter.BraceStyleParameter;
/*    */ import com.google.common.flogger.parameter.Parameter;
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
/*    */ public class DefaultBraceStyleMessageParser
/*    */   extends BraceStyleMessageParser
/*    */ {
/* 32 */   private static final BraceStyleMessageParser INSTANCE = new DefaultBraceStyleMessageParser();
/*    */   
/*    */   public static BraceStyleMessageParser getInstance() {
/* 35 */     return INSTANCE;
/*    */   }
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
/*    */   public void parseBraceFormatTerm(MessageBuilder<?> builder, int index, String message, int termStart, int formatStart, int termEnd) throws ParseException {
/* 50 */     if (formatStart != -1)
/*    */     {
/* 52 */       throw ParseException.withBounds("the default brace style parser does not allow trailing format specifiers", message, formatStart - 1, termEnd - 1);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     builder.addParameter(termStart, termEnd, (Parameter)BraceStyleParameter.of(index));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parser\DefaultBraceStyleMessageParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */