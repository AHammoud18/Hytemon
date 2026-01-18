/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class JwtHmac extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtHmac.class
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 18 */         .getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     String[] descriptorData = { "\n\024proto/jwt_hmac.proto\022\022google.crypto.tink\"Ã\001\n\nJwtHmacKey\022\017\n\007version\030\001 \001(\r\0227\n\talgorithm\030\002 \001(\0162$.google.crypto.tink.JwtHmacAlgorithm\022\021\n\tkey_value\030\003 \001(\f\022<\n\ncustom_kid\030\004 \001(\0132(.google.crypto.tink.JwtHmacKey.CustomKid\032\032\n\tCustomKid\022\r\n\005value\030\001 \001(\t\"n\n\020JwtHmacKeyFormat\022\017\n\007version\030\001 \001(\r\0227\n\talgorithm\030\002 \001(\0162$.google.crypto.tink.JwtHmacAlgorithm\022\020\n\bkey_size\030\003 \001(\r*C\n\020JwtHmacAlgorithm\022\016\n\nHS_UNKNOWN\020\000\022\t\n\005HS256\020\001\022\t\n\005HS384\020\002\022\t\n\005HS512\020\003B[\n\034com.google.crypto.tink.protoP\001Z9github.com/tink-crypto/tink-go/v2/proto/jwt_hmac_go_protob\006proto3" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 69 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*    */ 
/*    */ 
/*    */     
/* 73 */     internal_static_google_crypto_tink_JwtHmacKey_descriptor = getDescriptor().getMessageTypes().get(0);
/* 74 */     internal_static_google_crypto_tink_JwtHmacKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtHmacKey_descriptor, new String[] { "Version", "Algorithm", "KeyValue", "CustomKid" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 79 */     internal_static_google_crypto_tink_JwtHmacKey_CustomKid_descriptor = internal_static_google_crypto_tink_JwtHmacKey_descriptor.getNestedTypes().get(0);
/* 80 */     internal_static_google_crypto_tink_JwtHmacKey_CustomKid_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtHmacKey_CustomKid_descriptor, new String[] { "Value" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 85 */     internal_static_google_crypto_tink_JwtHmacKeyFormat_descriptor = getDescriptor().getMessageTypes().get(1);
/* 86 */     internal_static_google_crypto_tink_JwtHmacKeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_JwtHmacKeyFormat_descriptor, new String[] { "Version", "Algorithm", "KeySize" });
/*    */ 
/*    */ 
/*    */     
/* 90 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtHmacKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtHmacKey_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtHmacKey_CustomKid_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtHmacKey_CustomKid_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_JwtHmacKeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_JwtHmacKeyFormat_fieldAccessorTable;
/*    */   private static Descriptors.FileDescriptor descriptor;
/*    */   
/*    */   public static void registerAllExtensions(ExtensionRegistryLite registry) {}
/*    */   
/*    */   public static void registerAllExtensions(ExtensionRegistry registry) {
/*    */     registerAllExtensions((ExtensionRegistryLite)registry);
/*    */   }
/*    */   
/*    */   public static Descriptors.FileDescriptor getDescriptor() {
/*    */     return descriptor;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtHmac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */