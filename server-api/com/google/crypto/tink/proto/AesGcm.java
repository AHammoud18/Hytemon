/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class AesGcm extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesGcm.class
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
/* 47 */     String[] descriptorData = { "\n\023proto/aes_gcm.proto\022\022google.crypto.tink\"4\n\017AesGcmKeyFormat\022\020\n\bkey_size\030\002 \001(\r\022\017\n\007version\030\003 \001(\r\"/\n\tAesGcmKey\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\003 \001(\fBc\n\034com.google.crypto.tink.protoP\001Z8github.com/tink-crypto/tink-go/v2/proto/aes_gcm_go_proto¢\002\006TINKPBb\006proto3" };
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
/* 61 */     internal_static_google_crypto_tink_AesGcmKeyFormat_descriptor = getDescriptor().getMessageTypes().get(0);
/* 62 */     internal_static_google_crypto_tink_AesGcmKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesGcmKeyFormat_descriptor, new String[] { "KeySize", "Version" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 67 */     internal_static_google_crypto_tink_AesGcmKey_descriptor = getDescriptor().getMessageTypes().get(1);
/* 68 */     internal_static_google_crypto_tink_AesGcmKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesGcmKey_descriptor, new String[] { "Version", "KeyValue" });
/*    */ 
/*    */ 
/*    */     
/* 72 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesGcmKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesGcmKeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesGcmKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesGcmKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesGcm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */