/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class JwtRsaSsaPkcs1 extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtRsaSsaPkcs1.class
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
/*  57 */     String[] descriptorData = { "\n\035proto/jwt_rsa_ssa_pkcs1.proto\022\022google.crypto.tink\"ç\001\n\027JwtRsaSsaPkcs1PublicKey\022\017\n\007version\030\001 \001(\r\022>\n\talgorithm\030\002 \001(\0162+.google.crypto.tink.JwtRsaSsaPkcs1Algorithm\022\t\n\001n\030\003 \001(\f\022\t\n\001e\030\004 \001(\f\022I\n\ncustom_kid\030\005 \001(\01325.google.crypto.tink.JwtRsaSsaPkcs1PublicKey.CustomKid\032\032\n\tCustomKid\022\r\n\005value\030\001 \001(\t\"²\001\n\030JwtRsaSsaPkcs1PrivateKey\022\017\n\007version\030\001 \001(\r\022?\n\npublic_key\030\002 \001(\0132+.google.crypto.tink.JwtRsaSsaPkcs1PublicKey\022\t\n\001d\030\003 \001(\f\022\t\n\001p\030\004 \001(\f\022\t\n\001q\030\005 \001(\f\022\n\n\002dp\030\006 \001(\f\022\n\n\002dq\030\007 \001(\f\022\013\n\003crt\030\b \001(\f\"¡\001\n\027JwtRsaSsaPkcs1KeyFormat\022\017\n\007version\030\001 \001(\r\022>\n\talgorithm\030\002 \001(\0162+.google.crypto.tink.JwtRsaSsaPkcs1Algorithm\022\034\n\024modulus_size_in_bits\030\003 \001(\r\022\027\n\017public_exponent\030\004 \001(\f*J\n\027JwtRsaSsaPkcs1Algorithm\022\016\n\nRS_UNKNOWN\020\000\022\t\n\005RS256\020\001\022\t\n\005RS384\020\002\022\t\n\005RS512\020\003B`\n\034com.google.crypto.tink.protoP\001Z>github.com/tink-crypto/tink-go/v2/proto/rsa_ssa_pkcs1_go_protob\006proto3" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*     */ 
/*     */ 
/*     */     
/*  85 */     internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_descriptor = getDescriptor().getMessageTypes().get(0);
/*  86 */     internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_descriptor, new String[] { "Version", "Algorithm", "N", "E", "CustomKid" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_CustomKid_descriptor = internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_descriptor.getNestedTypes().get(0);
/*  92 */     internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_CustomKid_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_CustomKid_descriptor, new String[] { "Value" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     internal_static_google_crypto_tink_JwtRsaSsaPkcs1PrivateKey_descriptor = getDescriptor().getMessageTypes().get(1);
/*  98 */     internal_static_google_crypto_tink_JwtRsaSsaPkcs1PrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtRsaSsaPkcs1PrivateKey_descriptor, new String[] { "Version", "PublicKey", "D", "P", "Q", "Dp", "Dq", "Crt" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     internal_static_google_crypto_tink_JwtRsaSsaPkcs1KeyFormat_descriptor = getDescriptor().getMessageTypes().get(2);
/* 104 */     internal_static_google_crypto_tink_JwtRsaSsaPkcs1KeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtRsaSsaPkcs1KeyFormat_descriptor, new String[] { "Version", "Algorithm", "ModulusSizeInBits", "PublicExponent" });
/*     */ 
/*     */ 
/*     */     
/* 108 */     descriptor.resolveAllFeaturesImmutable();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_CustomKid_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtRsaSsaPkcs1PublicKey_CustomKid_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtRsaSsaPkcs1PrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtRsaSsaPkcs1PrivateKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtRsaSsaPkcs1KeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtRsaSsaPkcs1KeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtRsaSsaPkcs1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */