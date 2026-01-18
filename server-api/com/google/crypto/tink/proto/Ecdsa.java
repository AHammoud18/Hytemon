/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class Ecdsa extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Ecdsa.class
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
/*  57 */     String[] descriptorData = { "\n\021proto/ecdsa.proto\022\022google.crypto.tink\032\022proto/common.proto\"²\001\n\013EcdsaParams\022/\n\thash_type\030\001 \001(\0162\034.google.crypto.tink.HashType\0224\n\005curve\030\002 \001(\0162%.google.crypto.tink.EllipticCurveType\022<\n\bencoding\030\003 \001(\0162*.google.crypto.tink.EcdsaSignatureEncoding\"h\n\016EcdsaPublicKey\022\017\n\007version\030\001 \001(\r\022/\n\006params\030\002 \001(\0132\037.google.crypto.tink.EcdsaParams\022\t\n\001x\030\003 \001(\f\022\t\n\001y\030\004 \001(\f\"m\n\017EcdsaPrivateKey\022\017\n\007version\030\001 \001(\r\0226\n\npublic_key\030\002 \001(\0132\".google.crypto.tink.EcdsaPublicKey\022\021\n\tkey_value\030\003 \001(\f\"R\n\016EcdsaKeyFormat\022/\n\006params\030\002 \001(\0132\037.google.crypto.tink.EcdsaParams\022\017\n\007version\030\003 \001(\r*G\n\026EcdsaSignatureEncoding\022\024\n\020UNKNOWN_ENCODING\020\000\022\016\n\nIEEE_P1363\020\001\022\007\n\003DER\020\002BX\n\034com.google.crypto.tink.protoP\001Z6github.com/tink-crypto/tink-go/v2/proto/ecdsa_go_protob\006proto3" };
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
/*  78 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*     */           
/*  80 */           Common.getDescriptor()
/*     */         });
/*     */     
/*  83 */     internal_static_google_crypto_tink_EcdsaParams_descriptor = getDescriptor().getMessageTypes().get(0);
/*  84 */     internal_static_google_crypto_tink_EcdsaParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EcdsaParams_descriptor, new String[] { "HashType", "Curve", "Encoding" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     internal_static_google_crypto_tink_EcdsaPublicKey_descriptor = getDescriptor().getMessageTypes().get(1);
/*  90 */     internal_static_google_crypto_tink_EcdsaPublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EcdsaPublicKey_descriptor, new String[] { "Version", "Params", "X", "Y" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     internal_static_google_crypto_tink_EcdsaPrivateKey_descriptor = getDescriptor().getMessageTypes().get(2);
/*  96 */     internal_static_google_crypto_tink_EcdsaPrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EcdsaPrivateKey_descriptor, new String[] { "Version", "PublicKey", "KeyValue" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     internal_static_google_crypto_tink_EcdsaKeyFormat_descriptor = getDescriptor().getMessageTypes().get(3);
/* 102 */     internal_static_google_crypto_tink_EcdsaKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EcdsaKeyFormat_descriptor, new String[] { "Params", "Version" });
/*     */ 
/*     */ 
/*     */     
/* 106 */     descriptor.resolveAllFeaturesImmutable();
/* 107 */     Common.getDescriptor();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EcdsaParams_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EcdsaParams_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EcdsaPublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EcdsaPublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EcdsaPrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EcdsaPrivateKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EcdsaKeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EcdsaKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Ecdsa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */