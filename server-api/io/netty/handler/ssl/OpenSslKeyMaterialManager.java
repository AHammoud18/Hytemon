/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import java.util.Arrays;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLHandshakeException;
/*     */ import javax.net.ssl.X509ExtendedKeyManager;
/*     */ import javax.net.ssl.X509KeyManager;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class OpenSslKeyMaterialManager
/*     */ {
/*     */   static final String KEY_TYPE_RSA = "RSA";
/*     */   static final String KEY_TYPE_DH_RSA = "DH_RSA";
/*     */   static final String KEY_TYPE_EC = "EC";
/*     */   static final String KEY_TYPE_EC_EC = "EC_EC";
/*     */   static final String KEY_TYPE_EC_RSA = "EC_RSA";
/*     */   private static final int TYPE_RSA = 1;
/*     */   private static final int TYPE_DH_RSA = 2;
/*     */   private static final int TYPE_EC = 4;
/*     */   private static final int TYPE_EC_EC = 8;
/*     */   private static final int TYPE_EC_RSA = 16;
/*     */   private final OpenSslKeyMaterialProvider provider;
/*     */   private final boolean hasTmpDhKeys;
/*     */   
/*     */   OpenSslKeyMaterialManager(OpenSslKeyMaterialProvider provider, boolean hasTmpDhKeys) {
/*  56 */     this.provider = provider;
/*  57 */     this.hasTmpDhKeys = hasTmpDhKeys;
/*     */   }
/*     */   
/*     */   void setKeyMaterialServerSide(ReferenceCountedOpenSslEngine engine) throws SSLException {
/*  61 */     String[] authMethods = engine.authMethods();
/*  62 */     if (authMethods.length == 0) {
/*  63 */       throw new SSLHandshakeException("Unable to find key material");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     int seenTypes = 0;
/*  71 */     for (String authMethod : authMethods) {
/*  72 */       int typeBit = resolveKeyTypeBit(authMethod);
/*  73 */       if (typeBit != 0 && (seenTypes & typeBit) == 0) {
/*     */ 
/*     */ 
/*     */         
/*  77 */         seenTypes |= typeBit;
/*     */         
/*  79 */         String keyType = keyTypeString(typeBit);
/*  80 */         String alias = chooseServerAlias(engine, keyType);
/*  81 */         if (alias != null) {
/*  82 */           setKeyMaterial(engine, alias);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*  87 */     if (this.hasTmpDhKeys && authMethods.length == 1 && ("DH_anon"
/*  88 */       .equals(authMethods[0]) || "ECDH_anon".equals(authMethods[0]))) {
/*     */       return;
/*     */     }
/*  91 */     throw new SSLHandshakeException("Unable to find key material for auth method(s): " + 
/*  92 */         Arrays.toString(authMethods));
/*     */   }
/*     */   
/*     */   private static int resolveKeyTypeBit(String authMethod) {
/*  96 */     switch (authMethod) {
/*     */       case "RSA":
/*     */       case "DHE_RSA":
/*     */       case "ECDHE_RSA":
/* 100 */         return 1;
/*     */       case "DH_RSA":
/* 102 */         return 2;
/*     */       case "ECDHE_ECDSA":
/* 104 */         return 4;
/*     */       case "ECDH_ECDSA":
/* 106 */         return 8;
/*     */       case "ECDH_RSA":
/* 108 */         return 16;
/*     */     } 
/* 110 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String keyTypeString(int typeBit) {
/* 115 */     switch (typeBit) { case 1:
/* 116 */         return "RSA";
/* 117 */       case 2: return "DH_RSA";
/* 118 */       case 4: return "EC";
/* 119 */       case 8: return "EC_EC";
/* 120 */       case 16: return "EC_RSA"; }
/* 121 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setKeyMaterialClientSide(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) throws SSLException {
/* 127 */     String alias = chooseClientAlias(engine, keyTypes, issuer);
/*     */ 
/*     */ 
/*     */     
/* 131 */     if (alias != null) {
/* 132 */       setKeyMaterial(engine, alias);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setKeyMaterial(ReferenceCountedOpenSslEngine engine, String alias) throws SSLException {
/* 137 */     OpenSslKeyMaterial keyMaterial = null;
/*     */     try {
/* 139 */       keyMaterial = this.provider.chooseKeyMaterial(engine.alloc, alias);
/* 140 */       if (keyMaterial == null) {
/*     */         return;
/*     */       }
/* 143 */       engine.setKeyMaterial(keyMaterial);
/* 144 */     } catch (SSLException e) {
/* 145 */       throw e;
/* 146 */     } catch (Exception e) {
/* 147 */       throw new SSLException(e);
/*     */     } finally {
/* 149 */       if (keyMaterial != null) {
/* 150 */         keyMaterial.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String chooseClientAlias(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
/* 156 */     X509KeyManager manager = this.provider.keyManager();
/* 157 */     if (manager instanceof X509ExtendedKeyManager) {
/* 158 */       return ((X509ExtendedKeyManager)manager).chooseEngineClientAlias(keyTypes, (Principal[])issuer, engine);
/*     */     }
/* 160 */     return manager.chooseClientAlias(keyTypes, (Principal[])issuer, null);
/*     */   }
/*     */   
/*     */   private String chooseServerAlias(ReferenceCountedOpenSslEngine engine, String type) {
/* 164 */     X509KeyManager manager = this.provider.keyManager();
/* 165 */     if (manager instanceof X509ExtendedKeyManager) {
/* 166 */       return ((X509ExtendedKeyManager)manager).chooseEngineServerAlias(type, null, engine);
/*     */     }
/* 168 */     return manager.chooseServerAlias(type, null, null);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslKeyMaterialManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */