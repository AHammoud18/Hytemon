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
/*     */ 
/*     */ 
/*     */ @CheckReturnValue
/*     */ class UnknownFieldSetLiteSchema
/*     */   extends UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite>
/*     */ {
/*     */   boolean shouldDiscardUnknownFields(Reader reader) {
/*  21 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   UnknownFieldSetLite newBuilder() {
/*  26 */     return UnknownFieldSetLite.newInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   void addVarint(UnknownFieldSetLite fields, int number, long value) {
/*  31 */     fields.storeField(WireFormat.makeTag(number, 0), Long.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   void addFixed32(UnknownFieldSetLite fields, int number, int value) {
/*  36 */     fields.storeField(WireFormat.makeTag(number, 5), Integer.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   void addFixed64(UnknownFieldSetLite fields, int number, long value) {
/*  41 */     fields.storeField(WireFormat.makeTag(number, 1), Long.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   void addLengthDelimited(UnknownFieldSetLite fields, int number, ByteString value) {
/*  46 */     fields.storeField(WireFormat.makeTag(number, 2), value);
/*     */   }
/*     */ 
/*     */   
/*     */   void addGroup(UnknownFieldSetLite fields, int number, UnknownFieldSetLite subFieldSet) {
/*  51 */     fields.storeField(WireFormat.makeTag(number, 3), subFieldSet);
/*     */   }
/*     */ 
/*     */   
/*     */   UnknownFieldSetLite toImmutable(UnknownFieldSetLite fields) {
/*  56 */     fields.makeImmutable();
/*  57 */     return fields;
/*     */   }
/*     */ 
/*     */   
/*     */   void setToMessage(Object message, UnknownFieldSetLite fields) {
/*  62 */     ((GeneratedMessageLite)message).unknownFields = fields;
/*     */   }
/*     */ 
/*     */   
/*     */   UnknownFieldSetLite getFromMessage(Object message) {
/*  67 */     return ((GeneratedMessageLite)message).unknownFields;
/*     */   }
/*     */ 
/*     */   
/*     */   UnknownFieldSetLite getBuilderFromMessage(Object message) {
/*  72 */     UnknownFieldSetLite unknownFields = getFromMessage(message);
/*     */ 
/*     */     
/*  75 */     if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
/*  76 */       unknownFields = UnknownFieldSetLite.newInstance();
/*  77 */       setToMessage(message, unknownFields);
/*     */     } 
/*  79 */     return unknownFields;
/*     */   }
/*     */ 
/*     */   
/*     */   void setBuilderToMessage(Object message, UnknownFieldSetLite fields) {
/*  84 */     setToMessage(message, fields);
/*     */   }
/*     */ 
/*     */   
/*     */   void makeImmutable(Object message) {
/*  89 */     getFromMessage(message).makeImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   void writeTo(UnknownFieldSetLite fields, Writer writer) throws IOException {
/*  94 */     fields.writeTo(writer);
/*     */   }
/*     */ 
/*     */   
/*     */   void writeAsMessageSetTo(UnknownFieldSetLite fields, Writer writer) throws IOException {
/*  99 */     fields.writeAsMessageSetTo(writer);
/*     */   }
/*     */ 
/*     */   
/*     */   UnknownFieldSetLite merge(UnknownFieldSetLite target, UnknownFieldSetLite source) {
/* 104 */     if (UnknownFieldSetLite.getDefaultInstance().equals(source)) {
/* 105 */       return target;
/*     */     }
/* 107 */     if (UnknownFieldSetLite.getDefaultInstance().equals(target)) {
/* 108 */       return UnknownFieldSetLite.mutableCopyOf(target, source);
/*     */     }
/* 110 */     return target.mergeFrom(source);
/*     */   }
/*     */ 
/*     */   
/*     */   int getSerializedSize(UnknownFieldSetLite unknowns) {
/* 115 */     return unknowns.getSerializedSize();
/*     */   }
/*     */ 
/*     */   
/*     */   int getSerializedSizeAsMessageSet(UnknownFieldSetLite unknowns) {
/* 120 */     return unknowns.getSerializedSizeAsMessageSet();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UnknownFieldSetLiteSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */