/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class RsaSsaPkcs1 extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", RsaSsaPkcs1.class
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
/*  57 */     String[] descriptorData = { "\n\031proto/rsa_ssa_pkcs1.proto\022\022google.crypto.tink\032\022proto/common.proto\"D\n\021RsaSsaPkcs1Params\022/\n\thash_type\030\001 \001(\0162\034.google.crypto.tink.HashType\"t\n\024RsaSsaPkcs1PublicKey\022\017\n\007version\030\001 \001(\r\0225\n\006params\030\002 \001(\0132%.google.crypto.tink.RsaSsaPkcs1Params\022\t\n\001n\030\003 \001(\f\022\t\n\001e\030\004 \001(\f\"¬\001\n\025RsaSsaPkcs1PrivateKey\022\017\n\007version\030\001 \001(\r\022<\n\npublic_key\030\002 \001(\0132(.google.crypto.tink.RsaSsaPkcs1PublicKey\022\t\n\001d\030\003 \001(\f\022\t\n\001p\030\004 \001(\f\022\t\n\001q\030\005 \001(\f\022\n\n\002dp\030\006 \001(\f\022\n\n\002dq\030\007 \001(\f\022\013\n\003crt\030\b \001(\f\"\001\n\024RsaSsaPkcs1KeyFormat\0225\n\006params\030\001 \001(\0132%.google.crypto.tink.RsaSsaPkcs1Params\022\034\n\024modulus_size_in_bits\030\002 \001(\r\022\027\n\017public_exponent\030\003 \001(\fB`\n\034com.google.crypto.tink.protoP\001Z>github.com/tink-crypto/tink-go/v2/proto/rsa_ssa_pkcs1_go_protob\006proto3" };
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
/*  77 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*     */           
/*  79 */           Common.getDescriptor()
/*     */         });
/*     */     
/*  82 */     internal_static_google_crypto_tink_RsaSsaPkcs1Params_descriptor = getDescriptor().getMessageTypes().get(0);
/*  83 */     internal_static_google_crypto_tink_RsaSsaPkcs1Params_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_RsaSsaPkcs1Params_descriptor, new String[] { "HashType" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_descriptor = getDescriptor().getMessageTypes().get(1);
/*  89 */     internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_descriptor, new String[] { "Version", "Params", "N", "E" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_descriptor = getDescriptor().getMessageTypes().get(2);
/*  95 */     internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_descriptor, new String[] { "Version", "PublicKey", "D", "P", "Q", "Dp", "Dq", "Crt" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_descriptor = getDescriptor().getMessageTypes().get(3);
/* 101 */     internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_descriptor, new String[] { "Params", "ModulusSizeInBits", "PublicExponent" });
/*     */ 
/*     */ 
/*     */     
/* 105 */     descriptor.resolveAllFeaturesImmutable();
/* 106 */     Common.getDescriptor();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_RsaSsaPkcs1Params_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_RsaSsaPkcs1Params_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPkcs1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */