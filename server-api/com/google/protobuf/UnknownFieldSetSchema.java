/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ class UnknownFieldSetSchema
/*     */   extends UnknownFieldSchema<UnknownFieldSet, UnknownFieldSet.Builder>
/*     */ {
/*     */   boolean shouldDiscardUnknownFields(Reader reader) {
/*  18 */     return reader.shouldDiscardUnknownFields();
/*     */   }
/*     */ 
/*     */   
/*     */   UnknownFieldSet.Builder newBuilder() {
/*  23 */     return UnknownFieldSet.newBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   void addVarint(UnknownFieldSet.Builder fields, int number, long value) {
/*  28 */     fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addVarint(value).build());
/*     */   }
/*     */ 
/*     */   
/*     */   void addFixed32(UnknownFieldSet.Builder fields, int number, int value) {
/*  33 */     fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addFixed32(value).build());
/*     */   }
/*     */ 
/*     */   
/*     */   void addFixed64(UnknownFieldSet.Builder fields, int number, long value) {
/*  38 */     fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addFixed64(value).build());
/*     */   }
/*     */ 
/*     */   
/*     */   void addLengthDelimited(UnknownFieldSet.Builder fields, int number, ByteString value) {
/*  43 */     fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addLengthDelimited(value).build());
/*     */   }
/*     */ 
/*     */   
/*     */   void addGroup(UnknownFieldSet.Builder fields, int number, UnknownFieldSet subFieldSet) {
/*  48 */     fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addGroup(subFieldSet).build());
/*     */   }
/*     */ 
/*     */   
/*     */   UnknownFieldSet toImmutable(UnknownFieldSet.Builder fields) {
/*  53 */     return fields.build();
/*     */   }
/*     */ 
/*     */   
/*     */   void writeTo(UnknownFieldSet message, Writer writer) throws IOException {
/*  58 */     message.writeTo(writer);
/*     */   }
/*     */ 
/*     */   
/*     */   void writeAsMessageSetTo(UnknownFieldSet message, Writer writer) throws IOException {
/*  63 */     message.writeAsMessageSetTo(writer);
/*     */   }
/*     */ 
/*     */   
/*     */   UnknownFieldSet getFromMessage(Object message) {
/*  68 */     return ((GeneratedMessage)message).unknownFields;
/*     */   }
/*     */ 
/*     */   
/*     */   void setToMessage(Object message, UnknownFieldSet fields) {
/*  73 */     ((GeneratedMessage)message).unknownFields = fields;
/*     */   }
/*     */ 
/*     */   
/*     */   UnknownFieldSet.Builder getBuilderFromMessage(Object message) {
/*  78 */     return ((GeneratedMessage)message).unknownFields.toBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   void setBuilderToMessage(Object message, UnknownFieldSet.Builder builder) {
/*  83 */     ((GeneratedMessage)message).unknownFields = builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void makeImmutable(Object message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   UnknownFieldSet merge(UnknownFieldSet message, UnknownFieldSet other) {
/*  93 */     return message.toBuilder().mergeFrom(other).build();
/*     */   }
/*     */ 
/*     */   
/*     */   int getSerializedSize(UnknownFieldSet message) {
/*  98 */     return message.getSerializedSize();
/*     */   }
/*     */ 
/*     */   
/*     */   int getSerializedSizeAsMessageSet(UnknownFieldSet unknowns) {
/* 103 */     return unknowns.getSerializedSizeAsMessageSet();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UnknownFieldSetSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */