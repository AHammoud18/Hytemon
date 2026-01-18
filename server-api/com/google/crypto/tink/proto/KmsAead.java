/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class KmsAead extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KmsAead.class
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
/* 47 */     String[] descriptorData = { "\n\024proto/kms_aead.proto\022\022google.crypto.tink\"#\n\020KmsAeadKeyFormat\022\017\n\007key_uri\030\001 \001(\t\"S\n\nKmsAeadKey\022\017\n\007version\030\001 \001(\r\0224\n\006params\030\002 \001(\0132$.google.crypto.tink.KmsAeadKeyFormatB[\n\034com.google.crypto.tink.protoP\001Z9github.com/tink-crypto/tink-go/v2/proto/kms_aead_go_protob\006proto3" };
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
/* 61 */     internal_static_google_crypto_tink_KmsAeadKeyFormat_descriptor = getDescriptor().getMessageTypes().get(0);
/* 62 */     internal_static_google_crypto_tink_KmsAeadKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_KmsAeadKeyFormat_descriptor, new String[] { "KeyUri" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 67 */     internal_static_google_crypto_tink_KmsAeadKey_descriptor = getDescriptor().getMessageTypes().get(1);
/* 68 */     internal_static_google_crypto_tink_KmsAeadKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_KmsAeadKey_descriptor, new String[] { "Version", "Params" });
/*    */ 
/*    */ 
/*    */     
/* 72 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_KmsAeadKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_KmsAeadKeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_KmsAeadKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_KmsAeadKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KmsAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */