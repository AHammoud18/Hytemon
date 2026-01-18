/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.aead.AeadWrapper;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadKey;
/*     */ import com.google.crypto.tink.aead.AesGcmKey;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.InternalConfiguration;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.Random;
/*     */ import com.google.crypto.tink.mac.ChunkedMac;
/*     */ import com.google.crypto.tink.mac.ChunkedMacWrapper;
/*     */ import com.google.crypto.tink.mac.HmacKey;
/*     */ import com.google.crypto.tink.mac.MacWrapper;
/*     */ import com.google.crypto.tink.prf.HmacPrfKey;
/*     */ import com.google.crypto.tink.prf.Prf;
/*     */ import com.google.crypto.tink.prf.PrfSetWrapper;
/*     */ import com.google.crypto.tink.signature.EcdsaPrivateKey;
/*     */ import com.google.crypto.tink.signature.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.signature.PublicKeySignWrapper;
/*     */ import com.google.crypto.tink.signature.PublicKeyVerifyWrapper;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.signature.internal.RsaSsaPkcs1VerifyConscrypt;
/*     */ import com.google.crypto.tink.signature.internal.RsaSsaPssSignConscrypt;
/*     */ import com.google.crypto.tink.signature.internal.RsaSsaPssVerifyConscrypt;
/*     */ import com.google.crypto.tink.subtle.AesGcmJce;
/*     */ import com.google.crypto.tink.subtle.EcdsaSignJce;
/*     */ import com.google.crypto.tink.subtle.EcdsaVerifyJce;
/*     */ import com.google.crypto.tink.subtle.EncryptThenAuthenticate;
/*     */ import com.google.crypto.tink.subtle.PrfHmacJce;
/*     */ import com.google.crypto.tink.subtle.PrfMac;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1SignJce;
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
/*     */ public class ConfigurationFips140v2
/*     */ {
/*     */   public static Configuration get() throws GeneralSecurityException {
/*  65 */     if (!TinkFipsUtil.fipsModuleAvailable()) {
/*  66 */       throw new GeneralSecurityException("Conscrypt is not available or does not support checking for FIPS build.");
/*     */     }
/*     */     
/*  69 */     Random.validateUsesConscrypt();
/*     */ 
/*     */     
/*  72 */     PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*     */ 
/*     */     
/*  75 */     MacWrapper.registerToInternalPrimitiveRegistry(builder);
/*  76 */     ChunkedMacWrapper.registerToInternalPrimitiveRegistry(builder);
/*  77 */     builder.registerPrimitiveConstructor(
/*  78 */         PrimitiveConstructor.create(PrfMac::create, HmacKey.class, Mac.class));
/*  79 */     builder.registerPrimitiveConstructor(
/*  80 */         PrimitiveConstructor.create(com.google.crypto.tink.mac.internal.ChunkedHmacImpl::new, HmacKey.class, ChunkedMac.class));
/*     */ 
/*     */     
/*  83 */     AeadWrapper.registerToInternalPrimitiveRegistry(builder);
/*  84 */     builder.registerPrimitiveConstructor(
/*  85 */         PrimitiveConstructor.create(EncryptThenAuthenticate::create, AesCtrHmacAeadKey.class, Aead.class));
/*     */     
/*  87 */     builder.registerPrimitiveConstructor(
/*  88 */         PrimitiveConstructor.create(AesGcmJce::create, AesGcmKey.class, Aead.class));
/*     */ 
/*     */     
/*  91 */     PrfSetWrapper.registerToInternalPrimitiveRegistry(builder);
/*  92 */     builder.registerPrimitiveConstructor(
/*  93 */         PrimitiveConstructor.create(PrfHmacJce::create, HmacPrfKey.class, Prf.class));
/*     */ 
/*     */     
/*  96 */     PublicKeySignWrapper.registerToInternalPrimitiveRegistry(builder);
/*  97 */     PublicKeyVerifyWrapper.registerToInternalPrimitiveRegistry(builder);
/*  98 */     builder.registerPrimitiveConstructor(
/*  99 */         PrimitiveConstructor.create(EcdsaSignJce::create, EcdsaPrivateKey.class, PublicKeySign.class));
/*     */     
/* 101 */     builder.registerPrimitiveConstructor(
/* 102 */         PrimitiveConstructor.create(EcdsaVerifyJce::create, EcdsaPublicKey.class, PublicKeyVerify.class));
/*     */     
/* 104 */     builder.registerPrimitiveConstructor(
/* 105 */         PrimitiveConstructor.create(ConfigurationFips140v2::rsaSsaPkcs1SignCreate, RsaSsaPkcs1PrivateKey.class, PublicKeySign.class));
/*     */ 
/*     */ 
/*     */     
/* 109 */     builder.registerPrimitiveConstructor(
/* 110 */         PrimitiveConstructor.create(ConfigurationFips140v2::rsaSsaPkcs1VerifyCreate, RsaSsaPkcs1PublicKey.class, PublicKeyVerify.class));
/*     */ 
/*     */ 
/*     */     
/* 114 */     builder.registerPrimitiveConstructor(
/* 115 */         PrimitiveConstructor.create(ConfigurationFips140v2::rsaSsaPssSignCreate, RsaSsaPssPrivateKey.class, PublicKeySign.class));
/*     */ 
/*     */ 
/*     */     
/* 119 */     builder.registerPrimitiveConstructor(
/* 120 */         PrimitiveConstructor.create(ConfigurationFips140v2::rsaSsaPssVerifyCreate, RsaSsaPssPublicKey.class, PublicKeyVerify.class));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return (Configuration)InternalConfiguration.createFromPrimitiveRegistry(builder.build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PublicKeySign rsaSsaPkcs1SignCreate(RsaSsaPkcs1PrivateKey key) throws GeneralSecurityException {
/* 134 */     if (key.getParameters().getModulusSizeBits() != 2048 && key
/* 135 */       .getParameters().getModulusSizeBits() != 3072) {
/* 136 */       throw new GeneralSecurityException("Cannot create FIPS-compliant PublicKeySign: wrong RsaSsaPkcs1 key modulus size");
/*     */     }
/*     */     
/* 139 */     return RsaSsaPkcs1SignJce.create(key);
/*     */   }
/*     */ 
/*     */   
/*     */   private static PublicKeyVerify rsaSsaPkcs1VerifyCreate(RsaSsaPkcs1PublicKey key) throws GeneralSecurityException {
/* 144 */     if (key.getParameters().getModulusSizeBits() != 2048 && key
/* 145 */       .getParameters().getModulusSizeBits() != 3072) {
/* 146 */       throw new GeneralSecurityException("Cannot create FIPS-compliant PublicKeyVerify: wrong RsaSsaPkcs1 key modulus size");
/*     */     }
/*     */     
/* 149 */     return RsaSsaPkcs1VerifyConscrypt.create(key);
/*     */   }
/*     */ 
/*     */   
/*     */   private static PublicKeySign rsaSsaPssSignCreate(RsaSsaPssPrivateKey key) throws GeneralSecurityException {
/* 154 */     if (key.getParameters().getModulusSizeBits() != 2048 && key
/* 155 */       .getParameters().getModulusSizeBits() != 3072) {
/* 156 */       throw new GeneralSecurityException("Cannot create FIPS-compliant PublicKeySign: wrong RsaSsaPss key modulus size");
/*     */     }
/*     */     
/* 159 */     return RsaSsaPssSignConscrypt.create(key);
/*     */   }
/*     */ 
/*     */   
/*     */   private static PublicKeyVerify rsaSsaPssVerifyCreate(RsaSsaPssPublicKey key) throws GeneralSecurityException {
/* 164 */     if (key.getParameters().getModulusSizeBits() != 2048 && key
/* 165 */       .getParameters().getModulusSizeBits() != 3072) {
/* 166 */       throw new GeneralSecurityException("Cannot create FIPS-compliant PublicKeyVerify: wrong RsaSsaPss key modulus size");
/*     */     }
/*     */     
/* 169 */     return RsaSsaPssVerifyConscrypt.create(key);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\ConfigurationFips140v2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */