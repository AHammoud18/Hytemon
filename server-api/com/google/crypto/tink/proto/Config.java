/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.GeneratedMessage;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class Config extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Config.class
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
/* 47 */     String[] descriptorData = { "\n\022proto/config.proto\022\022google.crypto.tink\"\001\n\fKeyTypeEntry\022\026\n\016primitive_name\030\001 \001(\t\022\020\n\btype_url\030\002 \001(\t\022\033\n\023key_manager_version\030\003 \001(\r\022\027\n\017new_key_allowed\030\004 \001(\b\022\026\n\016catalogue_name\030\005 \001(\t:\002\030\001\"Z\n\016RegistryConfig\022\023\n\013config_name\030\001 \001(\t\022/\n\005entry\030\002 \003(\0132 .google.crypto.tink.KeyTypeEntry:\002\030\001BY\n\034com.google.crypto.tink.protoP\001Z7github.com/tink-crypto/tink-go/v2/proto/config_go_protob\006proto3" };
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
/* 60 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*    */ 
/*    */ 
/*    */     
/* 64 */     internal_static_google_crypto_tink_KeyTypeEntry_descriptor = getDescriptor().getMessageTypes().get(0);
/* 65 */     internal_static_google_crypto_tink_KeyTypeEntry_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_KeyTypeEntry_descriptor, new String[] { "PrimitiveName", "TypeUrl", "KeyManagerVersion", "NewKeyAllowed", "CatalogueName" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     internal_static_google_crypto_tink_RegistryConfig_descriptor = getDescriptor().getMessageTypes().get(1);
/* 71 */     internal_static_google_crypto_tink_RegistryConfig_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_RegistryConfig_descriptor, new String[] { "ConfigName", "Entry" });
/*    */ 
/*    */ 
/*    */     
/* 75 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_KeyTypeEntry_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_KeyTypeEntry_fieldAccessorTable;
/*    */   static final Descriptors.Descriptor internal_static_google_crypto_tink_RegistryConfig_descriptor;
/*    */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_RegistryConfig_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */