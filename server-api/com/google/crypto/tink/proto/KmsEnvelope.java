/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class KmsEnvelope extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KmsEnvelope.class
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
/* 47 */     String[] descriptorData = { "\n\030proto/kms_envelope.proto\022\022google.crypto.tink\032\020proto/tink.proto\"b\n\030KmsEnvelopeAeadKeyFormat\022\017\n\007kek_uri\030\001 \001(\t\0225\n\fdek_template\030\002 \001(\0132\037.google.crypto.tink.KeyTemplate\"c\n\022KmsEnvelopeAeadKey\022\017\n\007version\030\001 \001(\r\022<\n\006params\030\002 \001(\0132,.google.crypto.tink.KmsEnvelopeAeadKeyFormatB_\n\034com.google.crypto.tink.protoP\001Z=github.com/tink-crypto/tink-go/v2/proto/kms_envelope_go_protob\006proto3" };
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
/* 60 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*    */           
/* 62 */           Tink.getDescriptor()
/*    */         });
/*    */     
/* 65 */     internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_descriptor = getDescriptor().getMessageTypes().get(0);
/* 66 */     internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_descriptor, new String[] { "KekUri", "DekTemplate" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 71 */     internal_static_google_crypto_tink_KmsEnvelopeAeadKey_descriptor = getDescriptor().getMessageTypes().get(1);
/* 72 */     internal_static_google_crypto_tink_KmsEnvelopeAeadKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_KmsEnvelopeAeadKey_descriptor, new String[] { "Version", "Params" });
/*    */ 
/*    */ 
/*    */     
/* 76 */     descriptor.resolveAllFeaturesImmutable();
/* 77 */     Tink.getDescriptor();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_KmsEnvelopeAeadKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_KmsEnvelopeAeadKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KmsEnvelope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */