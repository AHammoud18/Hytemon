/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class AesGcmHkdfStreaming extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesGcmHkdfStreaming.class
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
/* 52 */     String[] descriptorData = { "\n\"proto/aes_gcm_hkdf_streaming.proto\022\022google.crypto.tink\032\022proto/common.proto\"\001\n\031AesGcmHkdfStreamingParams\022\037\n\027ciphertext_segment_size\030\001 \001(\r\022\030\n\020derived_key_size\030\002 \001(\r\0224\n\016hkdf_hash_type\030\003 \001(\0162\034.google.crypto.tink.HashType\"\001\n\034AesGcmHkdfStreamingKeyFormat\022\017\n\007version\030\003 \001(\r\022=\n\006params\030\001 \001(\0132-.google.crypto.tink.AesGcmHkdfStreamingParams\022\020\n\bkey_size\030\002 \001(\r\"{\n\026AesGcmHkdfStreamingKey\022\017\n\007version\030\001 \001(\r\022=\n\006params\030\002 \001(\0132-.google.crypto.tink.AesGcmHkdfStreamingParams\022\021\n\tkey_value\030\003 \001(\fBi\n\034com.google.crypto.tink.protoP\001ZGgithub.com/tink-crypto/tink-go/v2/proto/aes_gcm_hkdf_streaming_go_protob\006proto3" };
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
/* 70 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*    */           
/* 72 */           Common.getDescriptor()
/*    */         });
/*    */     
/* 75 */     internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_descriptor = getDescriptor().getMessageTypes().get(0);
/* 76 */     internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_descriptor, new String[] { "CiphertextSegmentSize", "DerivedKeySize", "HkdfHashType" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_descriptor = getDescriptor().getMessageTypes().get(1);
/* 82 */     internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_descriptor, new String[] { "Version", "Params", "KeySize" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 87 */     internal_static_google_crypto_tink_AesGcmHkdfStreamingKey_descriptor = getDescriptor().getMessageTypes().get(2);
/* 88 */     internal_static_google_crypto_tink_AesGcmHkdfStreamingKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesGcmHkdfStreamingKey_descriptor, new String[] { "Version", "Params", "KeyValue" });
/*    */ 
/*    */ 
/*    */     
/* 92 */     descriptor.resolveAllFeaturesImmutable();
/* 93 */     Common.getDescriptor();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesGcmHkdfStreamingKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesGcmHkdfStreamingKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesGcmHkdfStreaming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */