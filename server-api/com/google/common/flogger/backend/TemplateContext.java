/*    */ package com.google.common.flogger.backend;
/*    */ 
/*    */ import com.google.common.flogger.parser.MessageParser;
/*    */ import com.google.common.flogger.util.Checks;
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
/*    */ public final class TemplateContext
/*    */ {
/*    */   private final MessageParser parser;
/*    */   private final String message;
/*    */   
/*    */   public TemplateContext(MessageParser parser, String message) {
/* 37 */     this.parser = (MessageParser)Checks.checkNotNull(parser, "parser");
/* 38 */     this.message = (String)Checks.checkNotNull(message, "message");
/*    */   }
/*    */ 
/*    */   
/*    */   public MessageParser getParser() {
/* 43 */     return this.parser;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 48 */     return this.message;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 53 */     if (obj instanceof TemplateContext) {
/* 54 */       TemplateContext other = (TemplateContext)obj;
/* 55 */       return (this.parser.equals(other.parser) && this.message.equals(other.message));
/*    */     } 
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 63 */     return this.parser.hashCode() ^ this.message.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\TemplateContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */