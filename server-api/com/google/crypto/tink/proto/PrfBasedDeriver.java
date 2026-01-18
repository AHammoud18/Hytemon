/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class PrfBasedDeriver extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", PrfBasedDeriver.class
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
/* 52 */     String[] descriptorData = { "\n\035proto/prf_based_deriver.proto\022\022google.crypto.tink\032\020proto/tink.proto\"V\n\025PrfBasedDeriverParams\022=\n\024derived_key_template\030\001 \001(\0132\037.google.crypto.tink.KeyTemplate\"\001\n\030PrfBasedDeriverKeyFormat\0229\n\020prf_key_template\030\001 \001(\0132\037.google.crypto.tink.KeyTemplate\0229\n\006params\030\002 \001(\0132).google.crypto.tink.PrfBasedDeriverParams\"\001\n\022PrfBasedDeriverKey\022\017\n\007version\030\001 \001(\r\022,\n\007prf_key\030\002 \001(\0132\033.google.crypto.tink.KeyData\0229\n\006params\030\003 \001(\0132).google.crypto.tink.PrfBasedDeriverParamsBd\n\034com.google.crypto.tink.protoP\001ZBgithub.com/tink-crypto/tink-go/v2/proto/prf_based_deriver_go_protob\006proto3" };
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
/* 69 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*    */           
/* 71 */           Tink.getDescriptor()
/*    */         });
/*    */     
/* 74 */     internal_static_google_crypto_tink_PrfBasedDeriverParams_descriptor = getDescriptor().getMessageTypes().get(0);
/* 75 */     internal_static_google_crypto_tink_PrfBasedDeriverParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_PrfBasedDeriverParams_descriptor, new String[] { "DerivedKeyTemplate" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_descriptor = getDescriptor().getMessageTypes().get(1);
/* 81 */     internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_descriptor, new String[] { "PrfKeyTemplate", "Params" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 86 */     internal_static_google_crypto_tink_PrfBasedDeriverKey_descriptor = getDescriptor().getMessageTypes().get(2);
/* 87 */     internal_static_google_crypto_tink_PrfBasedDeriverKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_PrfBasedDeriverKey_descriptor, new String[] { "Version", "PrfKey", "Params" });
/*    */ 
/*    */ 
/*    */     
/* 91 */     descriptor.resolveAllFeaturesImmutable();
/* 92 */     Tink.getDescriptor();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_PrfBasedDeriverParams_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_PrfBasedDeriverParams_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_PrfBasedDeriverKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_PrfBasedDeriverKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\PrfBasedDeriver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */