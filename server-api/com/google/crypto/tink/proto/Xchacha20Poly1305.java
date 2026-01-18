/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class Xchacha20Poly1305 extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Xchacha20Poly1305.class
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
/* 47 */     String[] descriptorData = { "\n\036proto/xchacha20_poly1305.proto\022\022google.crypto.tink\"-\n\032XChaCha20Poly1305KeyFormat\022\017\n\007version\030\001 \001(\r\":\n\024XChaCha20Poly1305Key\022\017\n\007version\030\001 \001(\r\022\021\n\tkey_value\030\003 \001(\fBe\n\034com.google.crypto.tink.protoP\001ZCgithub.com/tink-crypto/tink-go/v2/proto/xchacha20_poly1305_go_protob\006proto3" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*    */ 
/*    */ 
/*    */     
/* 61 */     internal_static_google_crypto_tink_XChaCha20Poly1305KeyFormat_descriptor = getDescriptor().getMessageTypes().get(0);
/* 62 */     internal_static_google_crypto_tink_XChaCha20Poly1305KeyFormat_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_XChaCha20Poly1305KeyFormat_descriptor, new String[] { "Version" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 67 */     internal_static_google_crypto_tink_XChaCha20Poly1305Key_descriptor = getDescriptor().getMessageTypes().get(1);
/* 68 */     internal_static_google_crypto_tink_XChaCha20Poly1305Key_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_XChaCha20Poly1305Key_descriptor, new String[] { "Version", "KeyValue" });
/*    */ 
/*    */ 
/*    */     
/* 72 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_XChaCha20Poly1305KeyFormat_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_XChaCha20Poly1305KeyFormat_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_XChaCha20Poly1305Key_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_XChaCha20Poly1305Key_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Xchacha20Poly1305.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */