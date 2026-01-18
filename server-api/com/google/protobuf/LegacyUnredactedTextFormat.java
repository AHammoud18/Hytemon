/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.StreamSupport;
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
/*     */ public final class LegacyUnredactedTextFormat
/*     */ {
/*     */   public static String legacyUnredactedMultilineString(MessageOrBuilder message) {
/*  20 */     return TextFormat.printer()
/*  21 */       .printToString(message, TextFormat.Printer.FieldReporterLevel.LEGACY_MULTILINE);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String legacyUnredactedMultilineString(UnknownFieldSet fields) {
/*  26 */     return TextFormat.printer().printToString(fields);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String legacyUnredactedSingleLineString(MessageOrBuilder message) {
/*  34 */     return TextFormat.printer()
/*  35 */       .emittingSingleLine(true)
/*  36 */       .printToString(message, TextFormat.Printer.FieldReporterLevel.LEGACY_SINGLE_LINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String legacyUnredactedSingleLineString(UnknownFieldSet fields) {
/*  44 */     return TextFormat.printer().emittingSingleLine(true).printToString(fields);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String legacyUnredactedToString(Object object) {
/*  55 */     String[] result = new String[1];
/*  56 */     ProtobufToStringOutput.callWithTextFormat(() -> result[0] = object.toString());
/*  57 */     return result[0];
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
/*     */   public static String legacyUnredactedStringValueOf(Object object) {
/*  70 */     return (object == null) ? String.valueOf(object) : legacyUnredactedToString(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Iterable<String> legacyUnredactedToStringList(Iterable<?> iterable) {
/*  81 */     return (iterable == null) ? 
/*  82 */       null : 
/*  83 */       (Iterable<String>)StreamSupport.stream(
/*     */         
/*  85 */         iterable.spliterator(), false).map(LegacyUnredactedTextFormat::legacyUnredactedStringValueOf).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static String[] legacyUnredactedToStringArray(Object[] objects) {
/*  95 */     return (objects == null) ? 
/*  96 */       null : 
/*  97 */       (String[])Arrays.<Object>stream(
/*     */         
/*  99 */         objects).map(LegacyUnredactedTextFormat::legacyUnredactedStringValueOf).toArray(x$0 -> new String[x$0]);
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
/*     */   public static String legacyUnredactedStringFormat(String format, Object... args) {
/* 111 */     String[] result = new String[1];
/* 112 */     ProtobufToStringOutput.callWithTextFormat(() -> result[0] = String.format(format, args));
/* 113 */     return result[0];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\LegacyUnredactedTextFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */