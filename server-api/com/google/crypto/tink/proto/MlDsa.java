/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class MlDsa extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", MlDsa.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  18 */         .getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     String[] descriptorData = { "\n\022proto/ml_dsa.proto\022\022google.crypto.tink\"I\n\013MlDsaParams\022:\n\017ml_dsa_instance\030\001 \001(\0162!.google.crypto.tink.MlDsaInstance\"R\n\016MlDsaKeyFormat\022\017\n\007version\030\001 \001(\r\022/\n\006params\030\002 \001(\0132\037.google.crypto.tink.MlDsaParams\"e\n\016MlDsaPublicKey\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\002 \001(\f\022/\n\006params\030\003 \001(\0132\037.google.crypto.tink.MlDsaParams\"m\n\017MlDsaPrivateKey\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\002 \001(\f\0226\n\npublic_key\030\003 \001(\0132\".google.crypto.tink.MlDsaPublicKey*J\n\rMlDsaInstance\022\033\n\027ML_DSA_UNKNOWN_INSTANCE\020\000\022\r\n\tML_DSA_65\020\001\022\r\n\tML_DSA_87\020\002BV\n\034com.google.crypto.tink.protoP\001Z4github.com/tink-crypto/tink-go/v2/proto/ml_dsa_protob\006proto3" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*     */ 
/*     */ 
/*     */     
/*  79 */     internal_static_google_crypto_tink_MlDsaParams_descriptor = getDescriptor().getMessageTypes().get(0);
/*  80 */     internal_static_google_crypto_tink_MlDsaParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_MlDsaParams_descriptor, new String[] { "MlDsaInstance" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     internal_static_google_crypto_tink_MlDsaKeyFormat_descriptor = getDescriptor().getMessageTypes().get(1);
/*  86 */     internal_static_google_crypto_tink_MlDsaKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_MlDsaKeyFormat_descriptor, new String[] { "Version", "Params" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     internal_static_google_crypto_tink_MlDsaPublicKey_descriptor = getDescriptor().getMessageTypes().get(2);
/*  92 */     internal_static_google_crypto_tink_MlDsaPublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_MlDsaPublicKey_descriptor, new String[] { "Version", "KeyValue", "Params" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     internal_static_google_crypto_tink_MlDsaPrivateKey_descriptor = getDescriptor().getMessageTypes().get(3);
/*  98 */     internal_static_google_crypto_tink_MlDsaPrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_MlDsaPrivateKey_descriptor, new String[] { "Version", "KeyValue", "PublicKey" });
/*     */ 
/*     */ 
/*     */     
/* 102 */     descriptor.resolveAllFeaturesImmutable();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_MlDsaParams_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_MlDsaParams_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_MlDsaKeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_MlDsaKeyFormat_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_MlDsaPublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_MlDsaPublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_MlDsaPrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_MlDsaPrivateKey_fieldAccessorTable;
/*     */   private static Descriptors.FileDescriptor descriptor;
/*     */   
/*     */   public static void registerAllExtensions(ExtensionRegistryLite registry) {}
/*     */   
/*     */   public static void registerAllExtensions(ExtensionRegistry registry) {
/*     */     registerAllExtensions((ExtensionRegistryLite)registry);
/*     */   }
/*     */   
/*     */   public static Descriptors.FileDescriptor getDescriptor() {
/*     */     return descriptor;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\MlDsa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */