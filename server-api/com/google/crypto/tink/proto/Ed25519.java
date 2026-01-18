/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class Ed25519 extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Ed25519.class
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
/* 52 */     String[] descriptorData = { "\n\023proto/ed25519.proto\022\022google.crypto.tink\"#\n\020Ed25519KeyFormat\022\017\n\007version\030\001 \001(\r\"6\n\020Ed25519PublicKey\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\002 \001(\f\"q\n\021Ed25519PrivateKey\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\002 \001(\f\0228\n\npublic_key\030\003 \001(\0132$.google.crypto.tink.Ed25519PublicKeyBZ\n\034com.google.crypto.tink.protoP\001Z8github.com/tink-crypto/tink-go/v2/proto/ed25519_go_protob\006proto3" };
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
/* 64 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*    */ 
/*    */ 
/*    */     
/* 68 */     internal_static_google_crypto_tink_Ed25519KeyFormat_descriptor = getDescriptor().getMessageTypes().get(0);
/* 69 */     internal_static_google_crypto_tink_Ed25519KeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_Ed25519KeyFormat_descriptor, new String[] { "Version" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 74 */     internal_static_google_crypto_tink_Ed25519PublicKey_descriptor = getDescriptor().getMessageTypes().get(1);
/* 75 */     internal_static_google_crypto_tink_Ed25519PublicKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_Ed25519PublicKey_descriptor, new String[] { "Version", "KeyValue" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     internal_static_google_crypto_tink_Ed25519PrivateKey_descriptor = getDescriptor().getMessageTypes().get(2);
/* 81 */     internal_static_google_crypto_tink_Ed25519PrivateKey_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_Ed25519PrivateKey_descriptor, new String[] { "Version", "KeyValue", "PublicKey" });
/*    */ 
/*    */ 
/*    */     
/* 85 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_Ed25519KeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_Ed25519KeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_Ed25519PublicKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_Ed25519PublicKey_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_Ed25519PrivateKey_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_Ed25519PrivateKey_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Ed25519.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */