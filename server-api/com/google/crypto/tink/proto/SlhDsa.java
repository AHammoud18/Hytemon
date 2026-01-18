/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class SlhDsa extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", SlhDsa.class
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
/*  57 */     String[] descriptorData = { "\n\023proto/slh_dsa.proto\022\022google.crypto.tink\"\001\n\fSlhDsaParams\022\020\n\bkey_size\030\001 \001(\005\0225\n\thash_type\030\002 \001(\0162\".google.crypto.tink.SlhDsaHashType\0229\n\bsig_type\030\003 \001(\0162'.google.crypto.tink.SlhDsaSignatureType\"T\n\017SlhDsaKeyFormat\022\017\n\007version\030\001 \001(\r\0220\n\006params\030\002 \001(\0132 .google.crypto.tink.SlhDsaParams\"g\n\017SlhDsaPublicKey\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\002 \001(\f\0220\n\006params\030\003 \001(\0132 .google.crypto.tink.SlhDsaParams\"o\n\020SlhDsaPrivateKey\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\002 \001(\f\0227\n\npublic_key\030\003 \001(\0132#.google.crypto.tink.SlhDsaPublicKey*H\n\016SlhDsaHashType\022!\n\035SLH_DSA_HASH_TYPE_UNSPECIFIED\020\000\022\b\n\004SHA2\020\001\022\t\n\005SHAKE\020\002*d\n\023SlhDsaSignatureType\022&\n\"SLH_DSA_SIGNATURE_TYPE_UNSPECIFIED\020\000\022\020\n\fFAST_SIGNING\020\001\022\023\n\017SMALL_SIGNATURE\020\002BW\n\034com.google.crypto.tink.protoP\001Z5github.com/tink-crypto/tink-go/v2/proto/slh_dsa_protob\006proto3" };
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
/*  80 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*     */ 
/*     */ 
/*     */     
/*  84 */     internal_static_google_crypto_tink_SlhDsaParams_descriptor = getDescriptor().getMessageTypes().get(0);
/*  85 */     internal_static_google_crypto_tink_SlhDsaParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_SlhDsaParams_descriptor, new String[] { "KeySize", "HashType", "SigType" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     internal_static_google_crypto_tink_SlhDsaKeyFormat_descriptor = getDescriptor().getMessageTypes().get(1);
/*  91 */     internal_static_google_crypto_tink_SlhDsaKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_SlhDsaKeyFormat_descriptor, new String[] { "Version", "Params" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     internal_static_google_crypto_tink_SlhDsaPublicKey_descriptor = getDescriptor().getMessageTypes().get(2);
/*  97 */     internal_static_google_crypto_tink_SlhDsaPublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_SlhDsaPublicKey_descriptor, new String[] { "Version", "KeyValue", "Params" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     internal_static_google_crypto_tink_SlhDsaPrivateKey_descriptor = getDescriptor().getMessageTypes().get(3);
/* 103 */     internal_static_google_crypto_tink_SlhDsaPrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_SlhDsaPrivateKey_descriptor, new String[] { "Version", "KeyValue", "PublicKey" });
/*     */ 
/*     */ 
/*     */     
/* 107 */     descriptor.resolveAllFeaturesImmutable();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_SlhDsaParams_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_SlhDsaParams_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_SlhDsaKeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_SlhDsaKeyFormat_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_SlhDsaPublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_SlhDsaPublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_SlhDsaPrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_SlhDsaPrivateKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\SlhDsa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */