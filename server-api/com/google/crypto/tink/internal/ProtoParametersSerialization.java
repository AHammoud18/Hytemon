/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.crypto.tink.proto.KeyTemplate;
/*    */ import com.google.crypto.tink.proto.OutputPrefixType;
/*    */ import com.google.crypto.tink.util.Bytes;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import com.google.protobuf.MessageLite;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ @Immutable
/*    */ public final class ProtoParametersSerialization
/*    */   implements Serialization
/*    */ {
/*    */   private final Bytes objectIdentifier;
/*    */   private final KeyTemplate keyTemplate;
/*    */   
/*    */   private ProtoParametersSerialization(KeyTemplate keyTemplate, Bytes objectIdentifier) {
/* 41 */     this.keyTemplate = keyTemplate;
/* 42 */     this.objectIdentifier = objectIdentifier;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ProtoParametersSerialization create(String typeUrl, OutputPrefixType outputPrefixType, MessageLite value) {
/* 52 */     return create(
/* 53 */         KeyTemplate.newBuilder()
/* 54 */         .setTypeUrl(typeUrl)
/* 55 */         .setOutputPrefixType(outputPrefixType)
/* 56 */         .setValue(value.toByteString())
/* 57 */         .build());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ProtoParametersSerialization create(KeyTemplate keyTemplate) {
/* 66 */     return new ProtoParametersSerialization(keyTemplate, 
/* 67 */         Util.toBytesFromPrintableAscii(keyTemplate.getTypeUrl()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ProtoParametersSerialization checkedCreate(KeyTemplate keyTemplate) throws GeneralSecurityException {
/* 78 */     return new ProtoParametersSerialization(keyTemplate, 
/* 79 */         Util.checkedToBytesFromPrintableAscii(keyTemplate.getTypeUrl()));
/*    */   }
/*    */ 
/*    */   
/*    */   public KeyTemplate getKeyTemplate() {
/* 84 */     return this.keyTemplate;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Bytes getObjectIdentifier() {
/* 90 */     return this.objectIdentifier;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\ProtoParametersSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */