/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class EciesAeadHkdf extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EciesAeadHkdf.class
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
/*  67 */     String[] descriptorData = { "\n\033proto/ecies_aead_hkdf.proto\022\022google.crypto.tink\032\022proto/common.proto\032\020proto/tink.proto\"\001\n\022EciesHkdfKemParams\0229\n\ncurve_type\030\001 \001(\0162%.google.crypto.tink.EllipticCurveType\0224\n\016hkdf_hash_type\030\002 \001(\0162\034.google.crypto.tink.HashType\022\021\n\thkdf_salt\030\013 \001(\f\"G\n\022EciesAeadDemParams\0221\n\baead_dem\030\002 \001(\0132\037.google.crypto.tink.KeyTemplate\"É\001\n\023EciesAeadHkdfParams\022:\n\nkem_params\030\001 \001(\0132&.google.crypto.tink.EciesHkdfKemParams\022:\n\ndem_params\030\002 \001(\0132&.google.crypto.tink.EciesAeadDemParams\022:\n\017ec_point_format\030\003 \001(\0162!.google.crypto.tink.EcPointFormat\"x\n\026EciesAeadHkdfPublicKey\022\017\n\007version\030\001 \001(\r\0227\n\006params\030\002 \001(\0132'.google.crypto.tink.EciesAeadHkdfParams\022\t\n\001x\030\003 \001(\f\022\t\n\001y\030\004 \001(\f\"}\n\027EciesAeadHkdfPrivateKey\022\017\n\007version\030\001 \001(\r\022>\n\npublic_key\030\002 \001(\0132*.google.crypto.tink.EciesAeadHkdfPublicKey\022\021\n\tkey_value\030\003 \001(\f\"Q\n\026EciesAeadHkdfKeyFormat\0227\n\006params\030\001 \001(\0132'.google.crypto.tink.EciesAeadHkdfParamsBb\n\034com.google.crypto.tink.protoP\001Z@github.com/tink-crypto/tink-go/v2/proto/ecies_aead_hkdf_go_protob\006proto3" };
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
/*  94 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*     */           
/*  96 */           Common.getDescriptor(), 
/*  97 */           Tink.getDescriptor()
/*     */         });
/*     */     
/* 100 */     internal_static_google_crypto_tink_EciesHkdfKemParams_descriptor = getDescriptor().getMessageTypes().get(0);
/* 101 */     internal_static_google_crypto_tink_EciesHkdfKemParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EciesHkdfKemParams_descriptor, new String[] { "CurveType", "HkdfHashType", "HkdfSalt" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     internal_static_google_crypto_tink_EciesAeadDemParams_descriptor = getDescriptor().getMessageTypes().get(1);
/* 107 */     internal_static_google_crypto_tink_EciesAeadDemParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EciesAeadDemParams_descriptor, new String[] { "AeadDem" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     internal_static_google_crypto_tink_EciesAeadHkdfParams_descriptor = getDescriptor().getMessageTypes().get(2);
/* 113 */     internal_static_google_crypto_tink_EciesAeadHkdfParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EciesAeadHkdfParams_descriptor, new String[] { "KemParams", "DemParams", "EcPointFormat" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_descriptor = getDescriptor().getMessageTypes().get(3);
/* 119 */     internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_descriptor, new String[] { "Version", "Params", "X", "Y" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     internal_static_google_crypto_tink_EciesAeadHkdfPrivateKey_descriptor = getDescriptor().getMessageTypes().get(4);
/* 125 */     internal_static_google_crypto_tink_EciesAeadHkdfPrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EciesAeadHkdfPrivateKey_descriptor, new String[] { "Version", "PublicKey", "KeyValue" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_descriptor = getDescriptor().getMessageTypes().get(5);
/* 131 */     internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_descriptor, new String[] { "Params" });
/*     */ 
/*     */ 
/*     */     
/* 135 */     descriptor.resolveAllFeaturesImmutable();
/* 136 */     Common.getDescriptor();
/* 137 */     Tink.getDescriptor();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EciesHkdfKemParams_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EciesHkdfKemParams_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EciesAeadDemParams_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EciesAeadDemParams_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EciesAeadHkdfParams_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EciesAeadHkdfParams_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EciesAeadHkdfPrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EciesAeadHkdfPrivateKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesAeadHkdf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */