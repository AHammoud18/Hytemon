/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.crypto.tink.KeyTemplate;
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.proto.KeyTemplate;
/*    */ import com.google.crypto.tink.proto.OutputPrefixType;
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
/*    */ public final class KeyTemplateProtoConverter
/*    */ {
/*    */   public static KeyTemplate.OutputPrefixType prefixFromProto(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/* 29 */     switch (outputPrefixType) {
/*    */       case TINK:
/* 31 */         return KeyTemplate.OutputPrefixType.TINK;
/*    */       case LEGACY:
/* 33 */         return KeyTemplate.OutputPrefixType.LEGACY;
/*    */       case RAW:
/* 35 */         return KeyTemplate.OutputPrefixType.RAW;
/*    */       case CRUNCHY:
/* 37 */         return KeyTemplate.OutputPrefixType.CRUNCHY;
/*    */     } 
/* 39 */     throw new GeneralSecurityException("Unknown output prefix type");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static KeyTemplate toProto(KeyTemplate keyTemplate) throws GeneralSecurityException {
/* 45 */     Parameters parameters = keyTemplate.toParameters();
/* 46 */     if (parameters instanceof LegacyProtoParameters) {
/* 47 */       return ((LegacyProtoParameters)parameters).getSerialization().getKeyTemplate();
/*    */     }
/*    */ 
/*    */     
/* 51 */     ProtoParametersSerialization s = MutableSerializationRegistry.globalInstance().<Parameters, ProtoParametersSerialization>serializeParameters(parameters, ProtoParametersSerialization.class);
/* 52 */     return s.getKeyTemplate();
/*    */   }
/*    */   
/*    */   public static byte[] toByteArray(KeyTemplate keyTemplate) throws GeneralSecurityException {
/* 56 */     return toProto(keyTemplate).toByteArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public static KeyTemplate.OutputPrefixType getOutputPrefixType(KeyTemplate t) throws GeneralSecurityException {
/* 61 */     return prefixFromProto(toProto(t).getOutputPrefixType());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\KeyTemplateProtoConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */