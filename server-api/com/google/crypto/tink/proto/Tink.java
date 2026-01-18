/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedFile;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.RuntimeVersion;
/*     */ 
/*     */ public final class Tink extends GeneratedFile {
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Tink.class
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
/*  72 */     String[] descriptorData = { "\n\020proto/tink.proto\022\022google.crypto.tink\"p\n\013KeyTemplate\022\020\n\btype_url\030\001 \001(\t\022\r\n\005value\030\002 \001(\f\022@\n\022output_prefix_type\030\003 \001(\0162$.google.crypto.tink.OutputPrefixType\"è\001\n\007KeyData\022\020\n\btype_url\030\001 \001(\t\022\r\n\005value\030\002 \001(\f\022F\n\021key_material_type\030\003 \001(\0162+.google.crypto.tink.KeyData.KeyMaterialType\"t\n\017KeyMaterialType\022\027\n\023UNKNOWN_KEYMATERIAL\020\000\022\r\n\tSYMMETRIC\020\001\022\026\n\022ASYMMETRIC_PRIVATE\020\002\022\025\n\021ASYMMETRIC_PUBLIC\020\003\022\n\n\006REMOTE\020\004\"\002\n\006Keyset\022\026\n\016primary_key_id\030\001 \001(\r\022+\n\003key\030\002 \003(\0132\036.google.crypto.tink.Keyset.Key\032¹\001\n\003Key\022-\n\bkey_data\030\001 \001(\0132\033.google.crypto.tink.KeyData\0221\n\006status\030\002 \001(\0162!.google.crypto.tink.KeyStatusType\022\016\n\006key_id\030\003 \001(\r\022@\n\022output_prefix_type\030\004 \001(\0162$.google.crypto.tink.OutputPrefixType\"\002\n\nKeysetInfo\022\026\n\016primary_key_id\030\001 \001(\r\0228\n\bkey_info\030\002 \003(\0132&.google.crypto.tink.KeysetInfo.KeyInfo\032 \001\n\007KeyInfo\022\020\n\btype_url\030\001 \001(\t\0221\n\006status\030\002 \001(\0162!.google.crypto.tink.KeyStatusType\022\016\n\006key_id\030\003 \001(\r\022@\n\022output_prefix_type\030\004 \001(\0162$.google.crypto.tink.OutputPrefixType\"`\n\017EncryptedKeyset\022\030\n\020encrypted_keyset\030\002 \001(\f\0223\n\013keyset_info\030\003 \001(\0132\036.google.crypto.tink.KeysetInfo*M\n\rKeyStatusType\022\022\n\016UNKNOWN_STATUS\020\000\022\013\n\007ENABLED\020\001\022\f\n\bDISABLED\020\002\022\r\n\tDESTROYED\020\003*k\n\020OutputPrefixType\022\022\n\016UNKNOWN_PREFIX\020\000\022\b\n\004TINK\020\001\022\n\n\006LEGACY\020\002\022\007\n\003RAW\020\003\022\013\n\007CRUNCHY\020\004\022\027\n\023WITH_ID_REQUIREMENT\020\005B`\n\034com.google.crypto.tink.protoP\001Z5github.com/tink-crypto/tink-go/v2/proto/tink_go_proto¢\002\006TINKPBb\006proto3" };
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
/* 108 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*     */ 
/*     */ 
/*     */     
/* 112 */     internal_static_google_crypto_tink_KeyTemplate_descriptor = getDescriptor().getMessageTypes().get(0);
/* 113 */     internal_static_google_crypto_tink_KeyTemplate_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_KeyTemplate_descriptor, new String[] { "TypeUrl", "Value", "OutputPrefixType" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     internal_static_google_crypto_tink_KeyData_descriptor = getDescriptor().getMessageTypes().get(1);
/* 119 */     internal_static_google_crypto_tink_KeyData_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_KeyData_descriptor, new String[] { "TypeUrl", "Value", "KeyMaterialType" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     internal_static_google_crypto_tink_Keyset_descriptor = getDescriptor().getMessageTypes().get(2);
/* 125 */     internal_static_google_crypto_tink_Keyset_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_Keyset_descriptor, new String[] { "PrimaryKeyId", "Key" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     internal_static_google_crypto_tink_Keyset_Key_descriptor = internal_static_google_crypto_tink_Keyset_descriptor.getNestedTypes().get(0);
/* 131 */     internal_static_google_crypto_tink_Keyset_Key_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_Keyset_Key_descriptor, new String[] { "KeyData", "Status", "KeyId", "OutputPrefixType" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     internal_static_google_crypto_tink_KeysetInfo_descriptor = getDescriptor().getMessageTypes().get(3);
/* 137 */     internal_static_google_crypto_tink_KeysetInfo_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_KeysetInfo_descriptor, new String[] { "PrimaryKeyId", "KeyInfo" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     internal_static_google_crypto_tink_KeysetInfo_KeyInfo_descriptor = internal_static_google_crypto_tink_KeysetInfo_descriptor.getNestedTypes().get(0);
/* 143 */     internal_static_google_crypto_tink_KeysetInfo_KeyInfo_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_KeysetInfo_KeyInfo_descriptor, new String[] { "TypeUrl", "Status", "KeyId", "OutputPrefixType" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     internal_static_google_crypto_tink_EncryptedKeyset_descriptor = getDescriptor().getMessageTypes().get(4);
/* 149 */     internal_static_google_crypto_tink_EncryptedKeyset_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_crypto_tink_EncryptedKeyset_descriptor, new String[] { "EncryptedKeyset", "KeysetInfo" });
/*     */ 
/*     */ 
/*     */     
/* 153 */     descriptor.resolveAllFeaturesImmutable();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_KeyTemplate_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_KeyTemplate_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_KeyData_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_KeyData_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_Keyset_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_Keyset_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_Keyset_Key_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_Keyset_Key_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_KeysetInfo_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_KeysetInfo_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_KeysetInfo_KeyInfo_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_KeysetInfo_KeyInfo_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_crypto_tink_EncryptedKeyset_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_crypto_tink_EncryptedKeyset_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Tink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */