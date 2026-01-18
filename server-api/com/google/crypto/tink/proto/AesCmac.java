/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class AesCmac extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCmac.class
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
/* 52 */     String[] descriptorData = { "\n\024proto/aes_cmac.proto\022\022google.crypto.tink\"!\n\rAesCmacParams\022\020\n\btag_size\030\001 \001(\r\"c\n\nAesCmacKey\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\002 \001(\f\0221\n\006params\030\003 \001(\0132!.google.crypto.tink.AesCmacParams\"W\n\020AesCmacKeyFormat\022\020\n\bkey_size\030\001 \001(\r\0221\n\006params\030\002 \001(\0132!.google.crypto.tink.AesCmacParamsB[\n\034com.google.crypto.tink.protoP\001Z9github.com/tink-crypto/tink-go/v2/proto/aes_cmac_go_protob\006proto3" };
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
/* 65 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*    */ 
/*    */ 
/*    */     
/* 69 */     internal_static_google_crypto_tink_AesCmacParams_descriptor = getDescriptor().getMessageTypes().get(0);
/* 70 */     internal_static_google_crypto_tink_AesCmacParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCmacParams_descriptor, new String[] { "TagSize" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 75 */     internal_static_google_crypto_tink_AesCmacKey_descriptor = getDescriptor().getMessageTypes().get(1);
/* 76 */     internal_static_google_crypto_tink_AesCmacKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCmacKey_descriptor, new String[] { "Version", "KeyValue", "Params" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     internal_static_google_crypto_tink_AesCmacKeyFormat_descriptor = getDescriptor().getMessageTypes().get(2);
/* 82 */     internal_static_google_crypto_tink_AesCmacKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCmacKeyFormat_descriptor, new String[] { "KeySize", "Params" });
/*    */ 
/*    */ 
/*    */     
/* 86 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCmacParams_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCmacParams_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCmacKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCmacKey_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCmacKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCmacKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCmac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */