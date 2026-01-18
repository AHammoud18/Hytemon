/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class AesCtr extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCtr.class
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
/* 52 */     String[] descriptorData = { "\n\023proto/aes_ctr.proto\022\022google.crypto.tink\"\037\n\fAesCtrParams\022\017\n\007iv_size\030\001 \001(\r\"U\n\017AesCtrKeyFormat\0220\n\006params\030\001 \001(\0132 .google.crypto.tink.AesCtrParams\022\020\n\bkey_size\030\002 \001(\r\"a\n\tAesCtrKey\022\017\n\007version\030\001 \001(\r\0220\n\006params\030\002 \001(\0132 .google.crypto.tink.AesCtrParams\022\021\n\tkey_value\030\003 \001(\fBZ\n\034com.google.crypto.tink.protoP\001Z8github.com/tink-crypto/tink-go/v2/proto/aes_ctr_go_protob\006proto3" };
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
/* 64 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*    */ 
/*    */ 
/*    */     
/* 68 */     internal_static_google_crypto_tink_AesCtrParams_descriptor = getDescriptor().getMessageTypes().get(0);
/* 69 */     internal_static_google_crypto_tink_AesCtrParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCtrParams_descriptor, new String[] { "IvSize" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 74 */     internal_static_google_crypto_tink_AesCtrKeyFormat_descriptor = getDescriptor().getMessageTypes().get(1);
/* 75 */     internal_static_google_crypto_tink_AesCtrKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCtrKeyFormat_descriptor, new String[] { "Params", "KeySize" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     internal_static_google_crypto_tink_AesCtrKey_descriptor = getDescriptor().getMessageTypes().get(2);
/* 81 */     internal_static_google_crypto_tink_AesCtrKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCtrKey_descriptor, new String[] { "Version", "Params", "KeyValue" });
/*    */ 
/*    */ 
/*    */     
/* 85 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCtrParams_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCtrParams_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCtrKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCtrKeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCtrKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCtrKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */