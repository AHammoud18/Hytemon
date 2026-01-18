/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.proto.KeyTypeEntry;
/*     */ import com.google.crypto.tink.proto.RegistryConfig;
/*     */ import java.security.GeneralSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Config
/*     */ {
/*     */   public static KeyTypeEntry getTinkKeyTypeEntry(String catalogueName, String primitiveName, String keyProtoName, int keyManagerVersion, boolean newKeyAllowed) {
/*  51 */     return KeyTypeEntry.newBuilder()
/*  52 */       .setPrimitiveName(primitiveName)
/*  53 */       .setTypeUrl("type.googleapis.com/google.crypto.tink." + keyProtoName)
/*  54 */       .setKeyManagerVersion(keyManagerVersion)
/*  55 */       .setNewKeyAllowed(newKeyAllowed)
/*  56 */       .setCatalogueName(catalogueName)
/*  57 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(RegistryConfig config) throws GeneralSecurityException {
/*  70 */     for (KeyTypeEntry entry : config.getEntryList()) {
/*  71 */       registerKeyType(entry);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerKeyType(KeyTypeEntry entry) throws GeneralSecurityException {
/*  84 */     validate(entry);
/*     */ 
/*     */     
/*  87 */     if (entry.getCatalogueName().equals("TinkAead") || entry
/*  88 */       .getCatalogueName().equals("TinkMac") || entry
/*  89 */       .getCatalogueName().equals("TinkHybridDecrypt") || entry
/*  90 */       .getCatalogueName().equals("TinkHybridEncrypt") || entry
/*  91 */       .getCatalogueName().equals("TinkPublicKeySign") || entry
/*  92 */       .getCatalogueName().equals("TinkPublicKeyVerify") || entry
/*  93 */       .getCatalogueName().equals("TinkStreamingAead") || entry
/*  94 */       .getCatalogueName().equals("TinkDeterministicAead")) {
/*     */       return;
/*     */     }
/*  97 */     Catalogue<?> catalogue = Registry.getCatalogue(entry.getCatalogueName());
/*  98 */     MutablePrimitiveRegistry.globalInstance()
/*  99 */       .registerPrimitiveWrapper(catalogue.getPrimitiveWrapper());
/*     */     
/* 101 */     KeyManager<?> keyManager = catalogue.getKeyManager(entry
/* 102 */         .getTypeUrl(), entry.getPrimitiveName(), entry.getKeyManagerVersion());
/* 103 */     Registry.registerKeyManager(keyManager, entry.getNewKeyAllowed());
/*     */   }
/*     */   
/*     */   private static void validate(KeyTypeEntry entry) throws GeneralSecurityException {
/* 107 */     if (entry.getTypeUrl().isEmpty()) {
/* 108 */       throw new GeneralSecurityException("Missing type_url.");
/*     */     }
/* 110 */     if (entry.getPrimitiveName().isEmpty()) {
/* 111 */       throw new GeneralSecurityException("Missing primitive_name.");
/*     */     }
/* 113 */     if (entry.getCatalogueName().isEmpty())
/* 114 */       throw new GeneralSecurityException("Missing catalogue_name."); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */