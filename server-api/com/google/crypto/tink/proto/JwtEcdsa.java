/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class JwtEcdsa extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtEcdsa.class
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
/*  57 */     String[] descriptorData = { "\n\025proto/jwt_ecdsa.proto\022\022google.crypto.tink\"Õ\001\n\021JwtEcdsaPublicKey\022\017\n\007version\030\001 \001(\r\0228\n\talgorithm\030\002 \001(\0162%.google.crypto.tink.JwtEcdsaAlgorithm\022\t\n\001x\030\003 \001(\f\022\t\n\001y\030\004 \001(\f\022C\n\ncustom_kid\030\005 \001(\0132/.google.crypto.tink.JwtEcdsaPublicKey.CustomKid\032\032\n\tCustomKid\022\r\n\005value\030\001 \001(\t\"s\n\022JwtEcdsaPrivateKey\022\017\n\007version\030\001 \001(\r\0229\n\npublic_key\030\002 \001(\0132%.google.crypto.tink.JwtEcdsaPublicKey\022\021\n\tkey_value\030\003 \001(\f\"^\n\021JwtEcdsaKeyFormat\022\017\n\007version\030\001 \001(\r\0228\n\talgorithm\030\002 \001(\0162%.google.crypto.tink.JwtEcdsaAlgorithm*D\n\021JwtEcdsaAlgorithm\022\016\n\nES_UNKNOWN\020\000\022\t\n\005ES256\020\001\022\t\n\005ES384\020\002\022\t\n\005ES512\020\003B\\\n\034com.google.crypto.tink.protoP\001Z:github.com/tink-crypto/tink-go/v2/proto/jwt_ecdsa_go_protob\006proto3" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*     */ 
/*     */ 
/*     */     
/*  81 */     internal_static_google_crypto_tink_JwtEcdsaPublicKey_descriptor = getDescriptor().getMessageTypes().get(0);
/*  82 */     internal_static_google_crypto_tink_JwtEcdsaPublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtEcdsaPublicKey_descriptor, new String[] { "Version", "Algorithm", "X", "Y", "CustomKid" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_descriptor = internal_static_google_crypto_tink_JwtEcdsaPublicKey_descriptor.getNestedTypes().get(0);
/*  88 */     internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_descriptor, new String[] { "Value" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     internal_static_google_crypto_tink_JwtEcdsaPrivateKey_descriptor = getDescriptor().getMessageTypes().get(1);
/*  94 */     internal_static_google_crypto_tink_JwtEcdsaPrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtEcdsaPrivateKey_descriptor, new String[] { "Version", "PublicKey", "KeyValue" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     internal_static_google_crypto_tink_JwtEcdsaKeyFormat_descriptor = getDescriptor().getMessageTypes().get(2);
/* 100 */     internal_static_google_crypto_tink_JwtEcdsaKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtEcdsaKeyFormat_descriptor, new String[] { "Version", "Algorithm" });
/*     */ 
/*     */ 
/*     */     
/* 104 */     descriptor.resolveAllFeaturesImmutable();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtEcdsaPublicKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtEcdsaPublicKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtEcdsaPrivateKey_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtEcdsaPrivateKey_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtEcdsaKeyFormat_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtEcdsaKeyFormat_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtEcdsa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */