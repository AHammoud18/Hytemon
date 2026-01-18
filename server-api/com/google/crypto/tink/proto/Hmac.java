/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class Hmac extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Hmac.class
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
/* 52 */     String[] descriptorData = { "\n\020proto/hmac.proto\022\022google.crypto.tink\032\022proto/common.proto\"J\n\nHmacParams\022*\n\004hash\030\001 \001(\0162\034.google.crypto.tink.HashType\022\020\n\btag_size\030\002 \001(\r\"]\n\007HmacKey\022\017\n\007version\030\001 \001(\r\022.\n\006params\030\002 \001(\0132\036.google.crypto.tink.HmacParams\022\021\n\tkey_value\030\003 \001(\f\"b\n\rHmacKeyFormat\022.\n\006params\030\001 \001(\0132\036.google.crypto.tink.HmacParams\022\020\n\bkey_size\030\002 \001(\r\022\017\n\007version\030\003 \001(\rBW\n\034com.google.crypto.tink.protoP\001Z5github.com/tink-crypto/tink-go/v2/proto/hmac_go_protob\006proto3" };
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
/* 66 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*    */           
/* 68 */           Common.getDescriptor()
/*    */         });
/*    */     
/* 71 */     internal_static_google_crypto_tink_HmacParams_descriptor = getDescriptor().getMessageTypes().get(0);
/* 72 */     internal_static_google_crypto_tink_HmacParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HmacParams_descriptor, new String[] { "Hash", "TagSize" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     internal_static_google_crypto_tink_HmacKey_descriptor = getDescriptor().getMessageTypes().get(1);
/* 78 */     internal_static_google_crypto_tink_HmacKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HmacKey_descriptor, new String[] { "Version", "Params", "KeyValue" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 83 */     internal_static_google_crypto_tink_HmacKeyFormat_descriptor = getDescriptor().getMessageTypes().get(2);
/* 84 */     internal_static_google_crypto_tink_HmacKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HmacKeyFormat_descriptor, new String[] { "Params", "KeySize", "Version" });
/*    */ 
/*    */ 
/*    */     
/* 88 */     descriptor.resolveAllFeaturesImmutable();
/* 89 */     Common.getDescriptor();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HmacParams_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HmacParams_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HmacKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HmacKey_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HmacKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HmacKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Hmac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */