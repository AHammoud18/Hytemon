/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class RsaSsaPss extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", RsaSsaPss.class
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
/*  57 */     String[] descriptorData = { "\n\027proto/rsa_ssa_pss.proto\022\022google.crypto.tink\032\022proto/common.proto\"\001\n\017RsaSsaPssParams\022.\n\bsig_hash\030\001 \001(\0162\034.google.crypto.tink.HashType\022/\n\tmgf1_hash\030\002 \001(\0162\034.google.crypto.tink.HashType\022\023\n\013salt_length\030\003 \001(\005\"p\n\022RsaSsaPssPublicKey\022\017\n\007version\030\001 \001(\r\0223\n\006params\030\002 \001(\0132#.google.crypto.tink.RsaSsaPssParams\022\t\n\001n\030\003 \001(\f\022\t\n\001e\030\004 \001(\f\"¨\001\n\023RsaSsaPssPrivateKey\022\017\n\007version\030\001 \001(\r\022:\n\npublic_key\030\002 \001(\0132&.google.crypto.tink.RsaSsaPssPublicKey\022\t\n\001d\030\003 \001(\f\022\t\n\001p\030\004 \001(\f\022\t\n\001q\030\005 \001(\f\022\n\n\002dp\030\006 \001(\f\022\n\n\002dq\030\007 \001(\f\022\013\n\003crt\030\b \001(\f\"\001\n\022RsaSsaPssKeyFormat\0223\n\006params\030\001 \001(\0132#.google.crypto.tink.RsaSsaPssParams\022\034\n\024modulus_size_in_bits\030\002 \001(\r\022\027\n\017public_exponent\030\003 \001(\fB^\n\034com.google.crypto.tink.protoP\001Z<github.com/tink-crypto/tink-go/v2/proto/rsa_ssa_pss_go_protob\006proto3" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*     */           
/*  81 */           Common.getDescriptor()
/*     */         });
/*     */     
/*  84 */     internal_static_google_crypto_tink_RsaSsaPssParams_descriptor = getDescriptor().getMessageTypes().get(0);
/*  85 */     internal_static_google_crypto_tink_RsaSsaPssParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_RsaSsaPssParams_descriptor, new String[] { "SigHash", "Mgf1Hash", "SaltLength" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     internal_static_google_crypto_tink_RsaSsaPssPublicKey_descriptor = getDescriptor().getMessageTypes().get(1);
/*  91 */     internal_static_google_crypto_tink_RsaSsaPssPublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_RsaSsaPssPublicKey_descriptor, new String[] { "Version", "Params", "N", "E" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     internal_static_google_crypto_tink_RsaSsaPssPrivateKey_descriptor = getDescriptor().getMessageTypes().get(2);
/*  97 */     internal_static_google_crypto_tink_RsaSsaPssPrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_RsaSsaPssPrivateKey_descriptor, new String[] { "Version", "PublicKey", "D", "P", "Q", "Dp", "Dq", "Crt" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     internal_static_google_crypto_tink_RsaSsaPssKeyFormat_descriptor = getDescriptor().getMessageTypes().get(3);
/* 103 */     internal_static_google_crypto_tink_RsaSsaPssKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_RsaSsaPssKeyFormat_descriptor, new String[] { "Params", "ModulusSizeInBits", "PublicExponent" });
/*     */ 
/*     */ 
/*     */     
/* 107 */     descriptor.resolveAllFeaturesImmutable();
/* 108 */     Common.getDescriptor();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_RsaSsaPssParams_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_RsaSsaPssParams_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_RsaSsaPssPublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_RsaSsaPssPublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_RsaSsaPssPrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_RsaSsaPssPrivateKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_RsaSsaPssKeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_RsaSsaPssKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPss.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */