/*    */ package com.google.protobuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DebugFormat
/*    */ {
/*    */   private final boolean isSingleLine;
/*    */   
/*    */   private TextFormat.Printer getPrinter() {
/* 15 */     TextFormat.Printer printer = TextFormat.debugFormatPrinter();
/* 16 */     if (this.isSingleLine) {
/* 17 */       return printer.emittingSingleLine(true);
/*    */     }
/* 19 */     return printer;
/*    */   }
/*    */ 
/*    */   
/*    */   private DebugFormat(boolean singleLine) {
/* 24 */     this.isSingleLine = singleLine;
/*    */   }
/*    */   
/*    */   public static DebugFormat singleLine() {
/* 28 */     return new DebugFormat(true);
/*    */   }
/*    */   
/*    */   public static DebugFormat multiline() {
/* 32 */     return new DebugFormat(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString(MessageOrBuilder message) {
/* 39 */     TextFormat.Printer.FieldReporterLevel fieldReporterLevel = this.isSingleLine ? TextFormat.Printer.FieldReporterLevel.DEBUG_SINGLE_LINE : TextFormat.Printer.FieldReporterLevel.DEBUG_MULTILINE;
/* 40 */     return getPrinter().printToString(message, fieldReporterLevel);
/*    */   }
/*    */   
/*    */   public String toString(Descriptors.FieldDescriptor field, Object value) {
/* 44 */     return getPrinter().printFieldToString(field, value);
/*    */   }
/*    */   
/*    */   public String toString(UnknownFieldSet fields) {
/* 48 */     return getPrinter().printToString(fields);
/*    */   }
/*    */   
/*    */   public Object lazyToString(MessageOrBuilder message) {
/* 52 */     return new LazyDebugOutput(message, this);
/*    */   }
/*    */   
/*    */   public Object lazyToString(UnknownFieldSet fields) {
/* 56 */     return new LazyDebugOutput(fields, this);
/*    */   }
/*    */   
/*    */   private static class LazyDebugOutput {
/*    */     private final MessageOrBuilder message;
/*    */     private final UnknownFieldSet fields;
/*    */     private final DebugFormat format;
/*    */     
/*    */     LazyDebugOutput(MessageOrBuilder message, DebugFormat format) {
/* 65 */       this.message = message;
/* 66 */       this.fields = null;
/* 67 */       this.format = format;
/*    */     }
/*    */     
/*    */     LazyDebugOutput(UnknownFieldSet fields, DebugFormat format) {
/* 71 */       this.message = null;
/* 72 */       this.fields = fields;
/* 73 */       this.format = format;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 78 */       if (this.message != null) {
/* 79 */         return this.format.toString(this.message);
/*    */       }
/* 81 */       return this.format.toString(this.fields);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\DebugFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */