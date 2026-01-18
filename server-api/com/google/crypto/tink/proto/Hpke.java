/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class Hpke extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Hpke.class
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
/*  57 */     String[] descriptorData = { "\n\020proto/hpke.proto\022\022google.crypto.tink\"\001\n\nHpkeParams\022(\n\003kem\030\001 \001(\0162\033.google.crypto.tink.HpkeKem\022(\n\003kdf\030\002 \001(\0162\033.google.crypto.tink.HpkeKdf\022*\n\004aead\030\003 \001(\0162\034.google.crypto.tink.HpkeAead\"d\n\rHpkePublicKey\022\017\n\007version\030\001 \001(\r\022.\n\006params\030\002 \001(\0132\036.google.crypto.tink.HpkeParams\022\022\n\npublic_key\030\003 \001(\f\"m\n\016HpkePrivateKey\022\017\n\007version\030\001 \001(\r\0225\n\npublic_key\030\002 \001(\0132!.google.crypto.tink.HpkePublicKey\022\023\n\013private_key\030\003 \001(\f\"?\n\rHpkeKeyFormat\022.\n\006params\030\001 \001(\0132\036.google.crypto.tink.HpkeParams*·\001\n\007HpkeKem\022\017\n\013KEM_UNKNOWN\020\000\022\034\n\030DHKEM_X25519_HKDF_SHA256\020\001\022\032\n\026DHKEM_P256_HKDF_SHA256\020\002\022\032\n\026DHKEM_P384_HKDF_SHA384\020\003\022\032\n\026DHKEM_P521_HKDF_SHA512\020\004\022\n\n\006X_WING\020\005\022\r\n\tML_KEM768\020\006\022\016\n\nML_KEM1024\020\007*M\n\007HpkeKdf\022\017\n\013KDF_UNKNOWN\020\000\022\017\n\013HKDF_SHA256\020\001\022\017\n\013HKDF_SHA384\020\002\022\017\n\013HKDF_SHA512\020\003*U\n\bHpkeAead\022\020\n\fAEAD_UNKNOWN\020\000\022\017\n\013AES_128_GCM\020\001\022\017\n\013AES_256_GCM\020\002\022\025\n\021CHACHA20_POLY1305\020\003BT\n\034com.google.crypto.tink.protoP\001Z2github.com/tink-crypto/tink-go/v2/proto/hpke_protob\006proto3" };
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
/*  83 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*     */ 
/*     */ 
/*     */     
/*  87 */     internal_static_google_crypto_tink_HpkeParams_descriptor = getDescriptor().getMessageTypes().get(0);
/*  88 */     internal_static_google_crypto_tink_HpkeParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HpkeParams_descriptor, new String[] { "Kem", "Kdf", "Aead" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     internal_static_google_crypto_tink_HpkePublicKey_descriptor = getDescriptor().getMessageTypes().get(1);
/*  94 */     internal_static_google_crypto_tink_HpkePublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HpkePublicKey_descriptor, new String[] { "Version", "Params", "PublicKey" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     internal_static_google_crypto_tink_HpkePrivateKey_descriptor = getDescriptor().getMessageTypes().get(2);
/* 100 */     internal_static_google_crypto_tink_HpkePrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HpkePrivateKey_descriptor, new String[] { "Version", "PublicKey", "PrivateKey" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     internal_static_google_crypto_tink_HpkeKeyFormat_descriptor = getDescriptor().getMessageTypes().get(3);
/* 106 */     internal_static_google_crypto_tink_HpkeKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_HpkeKeyFormat_descriptor, new String[] { "Params" });
/*     */ 
/*     */ 
/*     */     
/* 110 */     descriptor.resolveAllFeaturesImmutable();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HpkeParams_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HpkeParams_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HpkePublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HpkePublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HpkePrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HpkePrivateKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_HpkeKeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_HpkeKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Hpke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */