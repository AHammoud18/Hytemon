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
/*     */ @CheckReturnValue
/*     */ abstract class UnknownFieldSchema<T, B>
/*     */ {
/*     */   static final int DEFAULT_RECURSION_LIMIT = 100;
/*  19 */   private static volatile int recursionLimit = 100;
/*     */ 
/*     */ 
/*     */   
/*     */   abstract boolean shouldDiscardUnknownFields(Reader paramReader);
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void addVarint(B paramB, int paramInt, long paramLong);
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void addFixed32(B paramB, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   abstract void addFixed64(B paramB, int paramInt, long paramLong);
/*     */ 
/*     */   
/*     */   abstract void addLengthDelimited(B paramB, int paramInt, ByteString paramByteString);
/*     */ 
/*     */   
/*     */   abstract void addGroup(B paramB, int paramInt, T paramT);
/*     */ 
/*     */   
/*     */   abstract B newBuilder();
/*     */ 
/*     */   
/*     */   abstract T toImmutable(B paramB);
/*     */ 
/*     */   
/*     */   abstract void setToMessage(Object paramObject, T paramT);
/*     */ 
/*     */   
/*     */   abstract T getFromMessage(Object paramObject);
/*     */ 
/*     */   
/*     */   abstract B getBuilderFromMessage(Object paramObject);
/*     */ 
/*     */   
/*     */   abstract void setBuilderToMessage(Object paramObject, B paramB);
/*     */ 
/*     */   
/*     */   abstract void makeImmutable(Object paramObject);
/*     */ 
/*     */   
/*     */   final boolean mergeOneFieldFrom(B unknownFields, Reader reader, int currentDepth) throws IOException {
/*     */     B subFields;
/*  66 */     int endGroupTag, tag = reader.getTag();
/*  67 */     int fieldNumber = WireFormat.getTagFieldNumber(tag);
/*  68 */     switch (WireFormat.getTagWireType(tag)) {
/*     */       case 0:
/*  70 */         addVarint(unknownFields, fieldNumber, reader.readInt64());
/*  71 */         return true;
/*     */       case 5:
/*  73 */         addFixed32(unknownFields, fieldNumber, reader.readFixed32());
/*  74 */         return true;
/*     */       case 1:
/*  76 */         addFixed64(unknownFields, fieldNumber, reader.readFixed64());
/*  77 */         return true;
/*     */       case 2:
/*  79 */         addLengthDelimited(unknownFields, fieldNumber, reader.readBytes());
/*  80 */         return true;
/*     */       case 3:
/*  82 */         subFields = newBuilder();
/*  83 */         endGroupTag = WireFormat.makeTag(fieldNumber, 4);
/*  84 */         currentDepth++;
/*  85 */         if (currentDepth >= recursionLimit) {
/*  86 */           throw InvalidProtocolBufferException.recursionLimitExceeded();
/*     */         }
/*  88 */         mergeFrom(subFields, reader, currentDepth);
/*  89 */         currentDepth--;
/*  90 */         if (endGroupTag != reader.getTag()) {
/*  91 */           throw InvalidProtocolBufferException.invalidEndTag();
/*     */         }
/*  93 */         addGroup(unknownFields, fieldNumber, toImmutable(subFields));
/*  94 */         return true;
/*     */       case 4:
/*  96 */         if (currentDepth == 0) {
/*  97 */           throw InvalidProtocolBufferException.invalidEndTag();
/*     */         }
/*  99 */         return false;
/*     */     } 
/* 101 */     throw InvalidProtocolBufferException.invalidWireType();
/*     */   }
/*     */ 
/*     */   
/*     */   private final void mergeFrom(B unknownFields, Reader reader, int currentDepth) throws IOException {
/*     */     do {
/*     */     
/* 108 */     } while (reader.getFieldNumber() != Integer.MAX_VALUE && 
/* 109 */       mergeOneFieldFrom(unknownFields, reader, currentDepth));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void writeTo(T paramT, Writer paramWriter) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void writeAsMessageSetTo(T paramT, Writer paramWriter) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   abstract T merge(T paramT1, T paramT2);
/*     */ 
/*     */   
/*     */   abstract int getSerializedSizeAsMessageSet(T paramT);
/*     */ 
/*     */   
/*     */   abstract int getSerializedSize(T paramT);
/*     */ 
/*     */   
/*     */   public void setRecursionLimit(int limit) {
/* 132 */     recursionLimit = limit;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UnknownFieldSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */