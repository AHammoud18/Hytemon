/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class AesCtrHmacAead extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCtrHmacAead.class
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
/* 47 */     String[] descriptorData = { "\n\035proto/aes_ctr_hmac_aead.proto\022\022google.crypto.tink\032\023proto/aes_ctr.proto\032\020proto/hmac.proto\"\001\n\027AesCtrHmacAeadKeyFormat\022?\n\022aes_ctr_key_format\030\001 \001(\0132#.google.crypto.tink.AesCtrKeyFormat\022:\n\017hmac_key_format\030\002 \001(\0132!.google.crypto.tink.HmacKeyFormat\"\001\n\021AesCtrHmacAeadKey\022\017\n\007version\030\001 \001(\r\0222\n\013aes_ctr_key\030\002 \001(\0132\035.google.crypto.tink.AesCtrKey\022-\n\bhmac_key\030\003 \001(\0132\033.google.crypto.tink.HmacKeyBd\n\034com.google.crypto.tink.protoP\001ZBgithub.com/tink-crypto/tink-go/v2/proto/aes_ctr_hmac_aead_go_protob\006proto3" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 63 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*    */           
/* 65 */           AesCtr.getDescriptor(), 
/* 66 */           Hmac.getDescriptor()
/*    */         });
/*    */     
/* 69 */     internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_descriptor = getDescriptor().getMessageTypes().get(0);
/* 70 */     internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_descriptor, new String[] { "AesCtrKeyFormat", "HmacKeyFormat" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 75 */     internal_static_google_crypto_tink_AesCtrHmacAeadKey_descriptor = getDescriptor().getMessageTypes().get(1);
/* 76 */     internal_static_google_crypto_tink_AesCtrHmacAeadKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_AesCtrHmacAeadKey_descriptor, new String[] { "Version", "AesCtrKey", "HmacKey" });
/*    */ 
/*    */ 
/*    */     
/* 80 */     descriptor.resolveAllFeaturesImmutable();
/* 81 */     AesCtr.getDescriptor();
/* 82 */     Hmac.getDescriptor();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_AesCtrHmacAeadKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_AesCtrHmacAeadKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrHmacAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */