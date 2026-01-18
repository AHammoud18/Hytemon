/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class XAesGcm extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", XAesGcm.class
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
/* 52 */     String[] descriptorData = { "\n\025proto/x_aes_gcm.proto\022\022google.crypto.tink\"\"\n\rXAesGcmParams\022\021\n\tsalt_size\030\001 \001(\r\"\\\n\020XAesGcmKeyFormat\022\017\n\007version\030\001 \001(\r\0221\n\006params\030\003 \001(\0132!.google.crypto.tink.XAesGcmParamsJ\004\b\002\020\003\"c\n\nXAesGcmKey\022\017\n\007version\030\001 \001(\r\0221\n\006params\030\002 \001(\0132!.google.crypto.tink.XAesGcmParams\022\021\n\tkey_value\030\003 \001(\fB\\\n\034com.google.crypto.tink.protoP\001Z:github.com/tink-crypto/tink-go/v2/proto/x_aes_gcm_go_protob\006proto3" };
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
/* 69 */     internal_static_google_crypto_tink_XAesGcmParams_descriptor = getDescriptor().getMessageTypes().get(0);
/* 70 */     internal_static_google_crypto_tink_XAesGcmParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_XAesGcmParams_descriptor, new String[] { "SaltSize" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 75 */     internal_static_google_crypto_tink_XAesGcmKeyFormat_descriptor = getDescriptor().getMessageTypes().get(1);
/* 76 */     internal_static_google_crypto_tink_XAesGcmKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_XAesGcmKeyFormat_descriptor, new String[] { "Version", "Params" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     internal_static_google_crypto_tink_XAesGcmKey_descriptor = getDescriptor().getMessageTypes().get(2);
/* 82 */     internal_static_google_crypto_tink_XAesGcmKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_XAesGcmKey_descriptor, new String[] { "Version", "Params", "KeyValue" });
/*    */ 
/*    */ 
/*    */     
/* 86 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_XAesGcmParams_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_XAesGcmParams_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_XAesGcmKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_XAesGcmKeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_XAesGcmKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_XAesGcmKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\XAesGcm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */