/*    */ package com.google.protobuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class UnredactedDebugFormatForTest
/*    */ {
/*    */   public static String unredactedMultilineString(MessageOrBuilder message) {
/* 13 */     return TextFormat.printer()
/* 14 */       .printToString(message, TextFormat.Printer.FieldReporterLevel.TEXT_GENERATOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String unredactedMultilineString(UnknownFieldSet fields) {
/* 19 */     return TextFormat.printer().printToString(fields);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String unredactedSingleLineString(MessageOrBuilder message) {
/* 27 */     return TextFormat.printer()
/* 28 */       .emittingSingleLine(true)
/* 29 */       .printToString(message, TextFormat.Printer.FieldReporterLevel.TEXT_GENERATOR);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String unredactedSingleLineString(UnknownFieldSet fields) {
/* 37 */     return TextFormat.printer().emittingSingleLine(true).printToString(fields);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String unredactedToString(Object object) {
/* 48 */     return LegacyUnredactedTextFormat.legacyUnredactedToString(object);
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
/*    */   public static String unredactedStringValueOf(Object object) {
/* 61 */     return LegacyUnredactedTextFormat.legacyUnredactedStringValueOf(object);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Iterable<String> unredactedToStringList(Iterable<?> iterable) {
/* 71 */     return LegacyUnredactedTextFormat.legacyUnredactedToStringList(iterable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String[] unredactedToStringArray(Object[] objects) {
/* 80 */     return LegacyUnredactedTextFormat.legacyUnredactedToStringArray(objects);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UnredactedDebugFormatForTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */