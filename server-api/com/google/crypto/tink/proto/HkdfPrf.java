/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class HkdfPrf extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HkdfPrf.class
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
/* 52 */     String[] descriptorData = { "\n\024proto/hkdf_prf.proto\022\022google.crypto.tink\032\022proto/common.proto\"I\n\rHkdfPrfParams\022*\n\004hash\030\001 \001(\0162\034.google.crypto.tink.HashType\022\f\n\004salt\030\002 \001(\f\"c\n\nHkdfPrfKey\022\017\n\007version\030\001 \001(\r\0221\n\006params\030\002 \001(\0132!.google.crypto.tink.HkdfPrfParams\022\021\n\tkey_value\030\003 \001(\f\"h\n\020HkdfPrfKeyFormat\0221\n\006params\030\001 \001(\0132!.google.crypto.tink.HkdfPrfParams\022\020\n\bkey_size\030\002 \001(\r\022\017\n\007version\030\003 \001(\rBX\n\034com.google.crypto.tink.protoP\001Z6github.com/tink-crypto/tink-go/v2/proto/hkdf_prf_protob\006proto3" };
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
/* 67 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*    */           
/* 69 */           Common.getDescriptor()
/*    */         });
/*    */     
/* 72 */     internal_static_google_crypto_tink_HkdfPrfParams_descriptor = getDescriptor().getMessageTypes().get(0);
/* 73 */     internal_static_google_crypto_tink_HkdfPrfParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HkdfPrfParams_descriptor, new String[] { "Hash", "Salt" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 78 */     internal_static_google_crypto_tink_HkdfPrfKey_descriptor = getDescriptor().getMessageTypes().get(1);
/* 79 */     internal_static_google_crypto_tink_HkdfPrfKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HkdfPrfKey_descriptor, new String[] { "Version", "Params", "KeyValue" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 84 */     internal_static_google_crypto_tink_HkdfPrfKeyFormat_descriptor = getDescriptor().getMessageTypes().get(2);
/* 85 */     internal_static_google_crypto_tink_HkdfPrfKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HkdfPrfKeyFormat_descriptor, new String[] { "Params", "KeySize", "Version" });
/*    */ 
/*    */ 
/*    */     
/* 89 */     descriptor.resolveAllFeaturesImmutable();
/* 90 */     Common.getDescriptor();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HkdfPrfParams_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HkdfPrfParams_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HkdfPrfKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HkdfPrfKey_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HkdfPrfKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HkdfPrfKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HkdfPrf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */