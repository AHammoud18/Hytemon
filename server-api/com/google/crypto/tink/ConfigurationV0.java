/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.aead.AeadWrapper;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadKey;
/*     */ import com.google.crypto.tink.aead.AesEaxKey;
/*     */ import com.google.crypto.tink.aead.AesGcmKey;
/*     */ import com.google.crypto.tink.aead.AesGcmSivKey;
/*     */ import com.google.crypto.tink.aead.ChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.aead.XChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.aead.internal.ChaCha20Poly1305Jce;
/*     */ import com.google.crypto.tink.aead.internal.XChaCha20Poly1305Jce;
/*     */ import com.google.crypto.tink.aead.subtle.AesGcmSiv;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.daead.AesSivKey;
/*     */ import com.google.crypto.tink.daead.DeterministicAeadWrapper;
/*     */ import com.google.crypto.tink.hybrid.EciesPrivateKey;
/*     */ import com.google.crypto.tink.hybrid.EciesPublicKey;
/*     */ import com.google.crypto.tink.hybrid.HpkePrivateKey;
/*     */ import com.google.crypto.tink.hybrid.HpkePublicKey;
/*     */ import com.google.crypto.tink.hybrid.HybridDecryptWrapper;
/*     */ import com.google.crypto.tink.hybrid.HybridEncryptWrapper;
/*     */ import com.google.crypto.tink.hybrid.internal.HpkeDecrypt;
/*     */ import com.google.crypto.tink.hybrid.internal.HpkeEncrypt;
/*     */ import com.google.crypto.tink.internal.InternalConfiguration;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.mac.AesCmacKey;
/*     */ import com.google.crypto.tink.mac.ChunkedMac;
/*     */ import com.google.crypto.tink.mac.ChunkedMacWrapper;
/*     */ import com.google.crypto.tink.mac.HmacKey;
/*     */ import com.google.crypto.tink.mac.MacWrapper;
/*     */ import com.google.crypto.tink.mac.internal.ChunkedAesCmacImpl;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfKey;
/*     */ import com.google.crypto.tink.prf.HkdfPrfKey;
/*     */ import com.google.crypto.tink.prf.HkdfPrfParameters;
/*     */ import com.google.crypto.tink.prf.HmacPrfKey;
/*     */ import com.google.crypto.tink.prf.Prf;
/*     */ import com.google.crypto.tink.prf.PrfSetWrapper;
/*     */ import com.google.crypto.tink.signature.EcdsaPrivateKey;
/*     */ import com.google.crypto.tink.signature.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.signature.Ed25519PrivateKey;
/*     */ import com.google.crypto.tink.signature.Ed25519PublicKey;
/*     */ import com.google.crypto.tink.signature.PublicKeySignWrapper;
/*     */ import com.google.crypto.tink.signature.PublicKeyVerifyWrapper;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.streamingaead.AesCtrHmacStreamingKey;
/*     */ import com.google.crypto.tink.streamingaead.AesGcmHkdfStreamingKey;
/*     */ import com.google.crypto.tink.streamingaead.StreamingAeadWrapper;
/*     */ import com.google.crypto.tink.subtle.AesCtrHmacStreaming;
/*     */ import com.google.crypto.tink.subtle.AesEaxJce;
/*     */ import com.google.crypto.tink.subtle.AesGcmHkdfStreaming;
/*     */ import com.google.crypto.tink.subtle.AesGcmJce;
/*     */ import com.google.crypto.tink.subtle.AesSiv;
/*     */ import com.google.crypto.tink.subtle.ChaCha20Poly1305;
/*     */ import com.google.crypto.tink.subtle.EcdsaSignJce;
/*     */ import com.google.crypto.tink.subtle.EcdsaVerifyJce;
/*     */ import com.google.crypto.tink.subtle.EciesAeadHkdfHybridDecrypt;
/*     */ import com.google.crypto.tink.subtle.EciesAeadHkdfHybridEncrypt;
/*     */ import com.google.crypto.tink.subtle.Ed25519Sign;
/*     */ import com.google.crypto.tink.subtle.Ed25519Verify;
/*     */ import com.google.crypto.tink.subtle.EncryptThenAuthenticate;
/*     */ import com.google.crypto.tink.subtle.PrfAesCmac;
/*     */ import com.google.crypto.tink.subtle.PrfHmacJce;
/*     */ import com.google.crypto.tink.subtle.PrfMac;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1SignJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1VerifyJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPssSignJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPssVerifyJce;
/*     */ import com.google.crypto.tink.subtle.XChaCha20Poly1305;
/*     */ import com.google.crypto.tink.subtle.prf.HkdfStreamingPrf;
/*     */ import com.google.crypto.tink.subtle.prf.PrfImpl;
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
/*     */ public class ConfigurationV0
/*     */ {
/*     */   private static final int AES_CMAC_KEY_SIZE_BYTES = 32;
/*     */   
/*     */   public static Configuration get() throws GeneralSecurityException {
/* 156 */     if (TinkFipsUtil.useOnlyFips()) {
/* 157 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant ConfigurationV0 in FIPS mode");
/*     */     }
/*     */ 
/*     */     
/* 161 */     PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*     */ 
/*     */     
/* 164 */     MacWrapper.registerToInternalPrimitiveRegistry(builder);
/* 165 */     ChunkedMacWrapper.registerToInternalPrimitiveRegistry(builder);
/* 166 */     builder.registerPrimitiveConstructor(
/* 167 */         PrimitiveConstructor.create(ConfigurationV0::createAesCmac, AesCmacKey.class, Mac.class));
/* 168 */     builder.registerPrimitiveConstructor(
/* 169 */         PrimitiveConstructor.create(PrfMac::create, HmacKey.class, Mac.class));
/* 170 */     builder.registerPrimitiveConstructor(
/* 171 */         PrimitiveConstructor.create(ConfigurationV0::createChunkedAesCmac, AesCmacKey.class, ChunkedMac.class));
/*     */     
/* 173 */     builder.registerPrimitiveConstructor(
/* 174 */         PrimitiveConstructor.create(com.google.crypto.tink.mac.internal.ChunkedHmacImpl::new, HmacKey.class, ChunkedMac.class));
/*     */ 
/*     */     
/* 177 */     AeadWrapper.registerToInternalPrimitiveRegistry(builder);
/* 178 */     builder.registerPrimitiveConstructor(
/* 179 */         PrimitiveConstructor.create(EncryptThenAuthenticate::create, AesCtrHmacAeadKey.class, Aead.class));
/*     */     
/* 181 */     builder.registerPrimitiveConstructor(
/* 182 */         PrimitiveConstructor.create(AesEaxJce::create, AesEaxKey.class, Aead.class));
/* 183 */     builder.registerPrimitiveConstructor(
/* 184 */         PrimitiveConstructor.create(AesGcmJce::create, AesGcmKey.class, Aead.class));
/* 185 */     builder.registerPrimitiveConstructor(
/* 186 */         PrimitiveConstructor.create(AesGcmSiv::create, AesGcmSivKey.class, Aead.class));
/* 187 */     builder.registerPrimitiveConstructor(
/* 188 */         PrimitiveConstructor.create(ConfigurationV0::createChaCha20Poly1305, ChaCha20Poly1305Key.class, Aead.class));
/*     */     
/* 190 */     builder.registerPrimitiveConstructor(
/* 191 */         PrimitiveConstructor.create(ConfigurationV0::createXChaCha20Poly1305, XChaCha20Poly1305Key.class, Aead.class));
/*     */ 
/*     */ 
/*     */     
/* 195 */     DeterministicAeadWrapper.registerToInternalPrimitiveRegistry(builder);
/* 196 */     builder.registerPrimitiveConstructor(
/* 197 */         PrimitiveConstructor.create(ConfigurationV0::createAesSiv, AesSivKey.class, DeterministicAead.class));
/*     */ 
/*     */ 
/*     */     
/* 201 */     StreamingAeadWrapper.registerToInternalPrimitiveRegistry(builder);
/* 202 */     builder.registerPrimitiveConstructor(
/* 203 */         PrimitiveConstructor.create(AesCtrHmacStreaming::create, AesCtrHmacStreamingKey.class, StreamingAead.class));
/*     */     
/* 205 */     builder.registerPrimitiveConstructor(
/* 206 */         PrimitiveConstructor.create(AesGcmHkdfStreaming::create, AesGcmHkdfStreamingKey.class, StreamingAead.class));
/*     */ 
/*     */ 
/*     */     
/* 210 */     HybridEncryptWrapper.registerToInternalPrimitiveRegistry(builder);
/* 211 */     HybridDecryptWrapper.registerToInternalPrimitiveRegistry(builder);
/* 212 */     builder.registerPrimitiveConstructor(
/* 213 */         PrimitiveConstructor.create(EciesAeadHkdfHybridEncrypt::create, EciesPublicKey.class, HybridEncrypt.class));
/*     */     
/* 215 */     builder.registerPrimitiveConstructor(
/* 216 */         PrimitiveConstructor.create(EciesAeadHkdfHybridDecrypt::create, EciesPrivateKey.class, HybridDecrypt.class));
/*     */     
/* 218 */     builder.registerPrimitiveConstructor(
/* 219 */         PrimitiveConstructor.create(HpkeEncrypt::create, HpkePublicKey.class, HybridEncrypt.class));
/* 220 */     builder.registerPrimitiveConstructor(
/* 221 */         PrimitiveConstructor.create(HpkeDecrypt::create, HpkePrivateKey.class, HybridDecrypt.class));
/*     */ 
/*     */ 
/*     */     
/* 225 */     PrfSetWrapper.registerToInternalPrimitiveRegistry(builder);
/* 226 */     builder.registerPrimitiveConstructor(
/* 227 */         PrimitiveConstructor.create(ConfigurationV0::createAesCmacPrf, AesCmacPrfKey.class, Prf.class));
/*     */     
/* 229 */     builder.registerPrimitiveConstructor(
/* 230 */         PrimitiveConstructor.create(ConfigurationV0::createHkdfPrf, HkdfPrfKey.class, Prf.class));
/* 231 */     builder.registerPrimitiveConstructor(
/* 232 */         PrimitiveConstructor.create(PrfHmacJce::create, HmacPrfKey.class, Prf.class));
/*     */ 
/*     */     
/* 235 */     PublicKeySignWrapper.registerToInternalPrimitiveRegistry(builder);
/* 236 */     PublicKeyVerifyWrapper.registerToInternalPrimitiveRegistry(builder);
/* 237 */     builder.registerPrimitiveConstructor(
/* 238 */         PrimitiveConstructor.create(EcdsaSignJce::create, EcdsaPrivateKey.class, PublicKeySign.class));
/*     */     
/* 240 */     builder.registerPrimitiveConstructor(
/* 241 */         PrimitiveConstructor.create(EcdsaVerifyJce::create, EcdsaPublicKey.class, PublicKeyVerify.class));
/*     */     
/* 243 */     builder.registerPrimitiveConstructor(
/* 244 */         PrimitiveConstructor.create(Ed25519Sign::create, Ed25519PrivateKey.class, PublicKeySign.class));
/*     */     
/* 246 */     builder.registerPrimitiveConstructor(
/* 247 */         PrimitiveConstructor.create(Ed25519Verify::create, Ed25519PublicKey.class, PublicKeyVerify.class));
/*     */     
/* 249 */     builder.registerPrimitiveConstructor(
/* 250 */         PrimitiveConstructor.create(RsaSsaPkcs1SignJce::create, RsaSsaPkcs1PrivateKey.class, PublicKeySign.class));
/*     */     
/* 252 */     builder.registerPrimitiveConstructor(
/* 253 */         PrimitiveConstructor.create(RsaSsaPkcs1VerifyJce::create, RsaSsaPkcs1PublicKey.class, PublicKeyVerify.class));
/*     */     
/* 255 */     builder.registerPrimitiveConstructor(
/* 256 */         PrimitiveConstructor.create(RsaSsaPssSignJce::create, RsaSsaPssPrivateKey.class, PublicKeySign.class));
/*     */     
/* 258 */     builder.registerPrimitiveConstructor(
/* 259 */         PrimitiveConstructor.create(RsaSsaPssVerifyJce::create, RsaSsaPssPublicKey.class, PublicKeyVerify.class));
/*     */ 
/*     */     
/* 262 */     return (Configuration)InternalConfiguration.createFromPrimitiveRegistry(builder
/* 263 */         .allowReparsingLegacyKeys().build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Aead createChaCha20Poly1305(ChaCha20Poly1305Key key) throws GeneralSecurityException {
/* 271 */     if (ChaCha20Poly1305Jce.isSupported()) {
/* 272 */       return ChaCha20Poly1305Jce.create(key);
/*     */     }
/* 274 */     return ChaCha20Poly1305.create(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Aead createXChaCha20Poly1305(XChaCha20Poly1305Key key) throws GeneralSecurityException {
/* 282 */     if (XChaCha20Poly1305Jce.isSupported()) {
/* 283 */       return XChaCha20Poly1305Jce.create(key);
/*     */     }
/* 285 */     return XChaCha20Poly1305.create(key);
/*     */   }
/*     */   
/*     */   private static DeterministicAead createAesSiv(AesSivKey key) throws GeneralSecurityException {
/* 289 */     int aesSivKeySizeInBytes = 64;
/* 290 */     if (key.getParameters().getKeySizeBytes() != aesSivKeySizeInBytes) {
/* 291 */       throw new GeneralSecurityException("invalid key size: " + key
/*     */           
/* 293 */           .getParameters().getKeySizeBytes() + ". Valid keys must have " + aesSivKeySizeInBytes + " bytes.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 298 */     return (DeterministicAead)AesSiv.create(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Prf createHkdfPrf(HkdfPrfKey key) throws GeneralSecurityException {
/* 307 */     int minHkdfPrfKeySize = 32;
/* 308 */     if (key.getParameters().getKeySizeBytes() < minHkdfPrfKeySize) {
/* 309 */       throw new GeneralSecurityException("Key size must be at least " + minHkdfPrfKeySize);
/*     */     }
/* 311 */     if (key.getParameters().getHashType() != HkdfPrfParameters.HashType.SHA256 && key
/* 312 */       .getParameters().getHashType() != HkdfPrfParameters.HashType.SHA512) {
/* 313 */       throw new GeneralSecurityException("Hash type must be SHA256 or SHA512");
/*     */     }
/* 315 */     return (Prf)PrfImpl.wrap(HkdfStreamingPrf.create(key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Prf createAesCmacPrf(AesCmacPrfKey key) throws GeneralSecurityException {
/* 322 */     if (key.getParameters().getKeySizeBytes() != 32) {
/* 323 */       throw new GeneralSecurityException("Key size must be 32 bytes");
/*     */     }
/* 325 */     return PrfAesCmac.create(key);
/*     */   }
/*     */   
/*     */   private static ChunkedMac createChunkedAesCmac(AesCmacKey key) throws GeneralSecurityException {
/* 329 */     if (key.getParameters().getKeySizeBytes() != 32) {
/* 330 */       throw new GeneralSecurityException("AesCmac key size is not 32 bytes");
/*     */     }
/* 332 */     return ChunkedAesCmacImpl.create(key);
/*     */   }
/*     */   
/*     */   private static Mac createAesCmac(AesCmacKey key) throws GeneralSecurityException {
/* 336 */     if (key.getParameters().getKeySizeBytes() != 32) {
/* 337 */       throw new GeneralSecurityException("AesCmac key size is not 32 bytes");
/*     */     }
/* 339 */     return PrfMac.create(key);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\ConfigurationV0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */