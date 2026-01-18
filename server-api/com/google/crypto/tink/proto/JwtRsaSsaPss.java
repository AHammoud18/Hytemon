/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class JwtRsaSsaPss extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtRsaSsaPss.class
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
/*  57 */     String[] descriptorData = { "\n\033proto/jwt_rsa_ssa_pss.proto\022\022google.crypto.tink\"á\001\n\025JwtRsaSsaPssPublicKey\022\017\n\007version\030\001 \001(\r\022<\n\talgorithm\030\002 \001(\0162).google.crypto.tink.JwtRsaSsaPssAlgorithm\022\t\n\001n\030\003 \001(\f\022\t\n\001e\030\004 \001(\f\022G\n\ncustom_kid\030\005 \001(\01323.google.crypto.tink.JwtRsaSsaPssPublicKey.CustomKid\032\032\n\tCustomKid\022\r\n\005value\030\001 \001(\t\"®\001\n\026JwtRsaSsaPssPrivateKey\022\017\n\007version\030\001 \001(\r\022=\n\npublic_key\030\002 \001(\0132).google.crypto.tink.JwtRsaSsaPssPublicKey\022\t\n\001d\030\003 \001(\f\022\t\n\001p\030\004 \001(\f\022\t\n\001q\030\005 \001(\f\022\n\n\002dp\030\006 \001(\f\022\n\n\002dq\030\007 \001(\f\022\013\n\003crt\030\b \001(\f\"\001\n\025JwtRsaSsaPssKeyFormat\022\017\n\007version\030\001 \001(\r\022<\n\talgorithm\030\002 \001(\0162).google.crypto.tink.JwtRsaSsaPssAlgorithm\022\034\n\024modulus_size_in_bits\030\003 \001(\r\022\027\n\017public_exponent\030\004 \001(\f*H\n\025JwtRsaSsaPssAlgorithm\022\016\n\nPS_UNKNOWN\020\000\022\t\n\005PS256\020\001\022\t\n\005PS384\020\002\022\t\n\005PS512\020\003Bb\n\034com.google.crypto.tink.protoP\001Z@github.com/tink-crypto/tink-go/v2/proto/jwt_rsa_ssa_pss_go_protob\006proto3" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  84 */     internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_descriptor = getDescriptor().getMessageTypes().get(0);
/*  85 */     internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_descriptor, new String[] { "Version", "Algorithm", "N", "E", "CustomKid" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_descriptor = internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_descriptor.getNestedTypes().get(0);
/*  91 */     internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_descriptor, new String[] { "Value" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_descriptor = getDescriptor().getMessageTypes().get(1);
/*  97 */     internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_descriptor, new String[] { "Version", "PublicKey", "D", "P", "Q", "Dp", "Dq", "Crt" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_descriptor = getDescriptor().getMessageTypes().get(2);
/* 103 */     internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_descriptor, new String[] { "Version", "Algorithm", "ModulusSizeInBits", "PublicExponent" });
/*     */ 
/*     */ 
/*     */     
/* 107 */     descriptor.resolveAllFeaturesImmutable();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtRsaSsaPss.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */