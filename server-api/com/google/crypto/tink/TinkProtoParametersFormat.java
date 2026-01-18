/*    */ package com.google.crypto.tink;
/*    */ 
/*    */ import com.google.crypto.tink.internal.LegacyProtoParameters;
/*    */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*    */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*    */ import com.google.crypto.tink.proto.KeyTemplate;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import java.io.IOException;
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
/*    */ public final class TinkProtoParametersFormat
/*    */ {
/*    */   public static byte[] serialize(Parameters parameters) throws GeneralSecurityException {
/* 33 */     if (parameters instanceof LegacyProtoParameters) {
/* 34 */       return ((LegacyProtoParameters)parameters).getSerialization().getKeyTemplate().toByteArray();
/*    */     }
/*    */ 
/*    */     
/* 38 */     ProtoParametersSerialization s = (ProtoParametersSerialization)MutableSerializationRegistry.globalInstance().serializeParameters(parameters, ProtoParametersSerialization.class);
/* 39 */     return s.getKeyTemplate().toByteArray();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Parameters parse(byte[] serializedParameters) throws GeneralSecurityException {
/*    */     KeyTemplate t;
/*    */     try {
/* 48 */       t = KeyTemplate.parseFrom(serializedParameters, ExtensionRegistryLite.getEmptyRegistry());
/* 49 */     } catch (IOException e) {
/* 50 */       throw new GeneralSecurityException("Failed to parse proto", e);
/*    */     } 
/* 52 */     return MutableSerializationRegistry.globalInstance()
/* 53 */       .parseParametersWithLegacyFallback(ProtoParametersSerialization.checkedCreate(t));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\TinkProtoParametersFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */