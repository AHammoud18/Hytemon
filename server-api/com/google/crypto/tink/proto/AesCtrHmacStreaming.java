/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class AesCtrHmacStreaming extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCtrHmacStreaming.class
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 18 */         .getName());
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     String[] descriptorData = { "\n\"proto/aes_ctr_hmac_streaming.proto\022\022google.crypto.tink\032\022proto/common.proto\032\020proto/hmac.proto\"Á\001\n\031AesCtrHmacStreamingParams\022\037\n\027ciphertext_segment_size\030\001 \001(\r\022\030\n\020derived_key_size\030\002 \001(\r\0224\n\016hkdf_hash_type\030\003 \001(\0162\034.google.crypto.tink.HashType\0223\n\013hmac_params\030\004 \001(\0132\036.google.crypto.tink.HmacParams\"\001\n\034AesCtrHmacStreamingKeyFormat\022\017\n\007version\030\003 \001(\r\022=\n\006params\030\001 \001(\0132-.google.crypto.tink.AesCtrHmacStreamingParams\022\020\n\bkey_size\030\002 \001(\r\"{\n\026AesCtrHmacStreamingKey\022\017\n\007version\030\001 \001(\r\022=\n\006params\030\002 \001(\0132-.google.crypto.tink.AesCtrHmacStreamingParams\022\021\n\tkey_value\030\003 \001(\fBi\n\034com.google.crypto.tink.protoP\001ZGgithub.com/tink-crypto/tink-go/v2/proto/aes_ctr_hmac_streaming_go_protob\006proto3" };
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
/* 72 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*    */           
/* 74 */           Common.getDescriptor(), 
/* 75 */           Hmac.getDescriptor()
/*    */         });
/*    */     
/* 78 */     internal_static_google_crypto_tink_AesCtrHmacStreamingParams_descriptor = getDescriptor().getMessageTypes().get(0);
/* 79 */     internal_static_google_crypto_tink_AesCtrHmacStreamingParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCtrHmacStreamingParams_descriptor, new String[] { "CiphertextSegmentSize", "DerivedKeySize", "HkdfHashType", "HmacParams" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 84 */     internal_static_google_crypto_tink_AesCtrHmacStreamingKeyFormat_descriptor = getDescriptor().getMessageTypes().get(1);
/* 85 */     internal_static_google_crypto_tink_AesCtrHmacStreamingKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCtrHmacStreamingKeyFormat_descriptor, new String[] { "Version", "Params", "KeySize" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 90 */     internal_static_google_crypto_tink_AesCtrHmacStreamingKey_descriptor = getDescriptor().getMessageTypes().get(2);
/* 91 */     internal_static_google_crypto_tink_AesCtrHmacStreamingKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCtrHmacStreamingKey_descriptor, new String[] { "Version", "Params", "KeyValue" });
/*    */ 
/*    */ 
/*    */     
/* 95 */     descriptor.resolveAllFeaturesImmutable();
/* 96 */     Common.getDescriptor();
/* 97 */     Hmac.getDescriptor();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCtrHmacStreamingParams_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCtrHmacStreamingParams_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCtrHmacStreamingKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCtrHmacStreamingKeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCtrHmacStreamingKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCtrHmacStreamingKey_fieldAccessorTable;
/*    */   private static Descriptors.FileDescriptor descriptor;
/*    */   
/*    */   public static void registerAllExtensions(ExtensionRegistryLite registry) {}
/*    */   
/*    */   public static void registerAllExtensions(ExtensionRegistry registry) {
/*    */     registerAllExtensions((ExtensionRegistryLite)registry);
/*    */   }
/*    */   
/*    */   public static Descriptors.FileDescriptor getDescriptor() {
/*    */     return descriptor;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrHmacStreaming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */