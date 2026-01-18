/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class AesCmacPrf extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCmacPrf.class
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
/* 47 */     String[] descriptorData = { "\n\030proto/aes_cmac_prf.proto\022\022google.crypto.tink\"3\n\rAesCmacPrfKey\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\002 \001(\f\"8\n\023AesCmacPrfKeyFormat\022\017\n\007version\030\002 \001(\r\022\020\n\bkey_size\030\001 \001(\rB_\n\034com.google.crypto.tink.protoP\001Z=github.com/tink-crypto/tink-go/v2/proto/aes_cmac_prf_go_protob\006proto3" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*    */ 
/*    */ 
/*    */     
/* 61 */     internal_static_google_crypto_tink_AesCmacPrfKey_descriptor = getDescriptor().getMessageTypes().get(0);
/* 62 */     internal_static_google_crypto_tink_AesCmacPrfKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCmacPrfKey_descriptor, new String[] { "Version", "KeyValue" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 67 */     internal_static_google_crypto_tink_AesCmacPrfKeyFormat_descriptor = getDescriptor().getMessageTypes().get(1);
/* 68 */     internal_static_google_crypto_tink_AesCmacPrfKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCmacPrfKeyFormat_descriptor, new String[] { "Version", "KeySize" });
/*    */ 
/*    */ 
/*    */     
/* 72 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCmacPrfKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCmacPrfKey_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCmacPrfKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCmacPrfKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCmacPrf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */