/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.spec.SecretKeySpec;
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
/*     */ @Immutable
/*     */ final class HkdfHpkeKdf
/*     */   implements HpkeKdf
/*     */ {
/*     */   private final String macAlgorithm;
/*     */   
/*     */   HkdfHpkeKdf(String macAlgorithm) {
/*  31 */     this.macAlgorithm = macAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] extract(byte[] ikm, byte[] salt) throws GeneralSecurityException {
/*  39 */     Mac mac = (Mac)EngineFactory.MAC.getInstance(this.macAlgorithm);
/*  40 */     if (salt == null || salt.length == 0) {
/*     */ 
/*     */       
/*  43 */       mac.init(new SecretKeySpec(new byte[mac.getMacLength()], this.macAlgorithm));
/*     */     } else {
/*  45 */       mac.init(new SecretKeySpec(salt, this.macAlgorithm));
/*     */     } 
/*  47 */     return mac.doFinal(ikm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] expand(byte[] prk, byte[] info, int length) throws GeneralSecurityException {
/*  56 */     Mac mac = (Mac)EngineFactory.MAC.getInstance(this.macAlgorithm);
/*  57 */     if (length > 255 * mac.getMacLength()) {
/*  58 */       throw new GeneralSecurityException("size too large");
/*     */     }
/*  60 */     byte[] result = new byte[length];
/*  61 */     int ctr = 1;
/*  62 */     int pos = 0;
/*  63 */     mac.init(new SecretKeySpec(prk, this.macAlgorithm));
/*  64 */     byte[] digest = new byte[0];
/*     */     while (true) {
/*  66 */       mac.update(digest);
/*  67 */       mac.update(info);
/*  68 */       mac.update((byte)ctr);
/*  69 */       digest = mac.doFinal();
/*  70 */       if (pos + digest.length < length) {
/*  71 */         System.arraycopy(digest, 0, result, pos, digest.length);
/*  72 */         pos += digest.length;
/*  73 */         ctr++; continue;
/*     */       }  break;
/*  75 */     }  System.arraycopy(digest, 0, result, pos, length - pos);
/*     */ 
/*     */ 
/*     */     
/*  79 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] labeledExtract(byte[] salt, byte[] ikm, String ikmLabel, byte[] suiteId) throws GeneralSecurityException {
/*  85 */     return extract(HpkeUtil.labelIkm(ikmLabel, ikm, suiteId), salt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] labeledExpand(byte[] prk, byte[] info, String infoLabel, byte[] suiteId, int length) throws GeneralSecurityException {
/*  91 */     return expand(prk, HpkeUtil.labelInfo(infoLabel, info, suiteId, length), length);
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
/*     */   public byte[] extractAndExpand(byte[] salt, byte[] ikm, String ikmLabel, byte[] info, String infoLabel, byte[] suiteId, int length) throws GeneralSecurityException {
/* 104 */     byte[] prk = extract(HpkeUtil.labelIkm(ikmLabel, ikm, suiteId), salt);
/* 105 */     return expand(prk, HpkeUtil.labelInfo(infoLabel, info, suiteId, length), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getKdfId() throws GeneralSecurityException {
/* 110 */     switch (this.macAlgorithm) {
/*     */       case "HmacSha256":
/* 112 */         return HpkeUtil.HKDF_SHA256_KDF_ID;
/*     */       case "HmacSha384":
/* 114 */         return HpkeUtil.HKDF_SHA384_KDF_ID;
/*     */       case "HmacSha512":
/* 116 */         return HpkeUtil.HKDF_SHA512_KDF_ID;
/*     */     } 
/* 118 */     throw new GeneralSecurityException("Could not determine HPKE KDF ID");
/*     */   }
/*     */ 
/*     */   
/*     */   int getMacLength() throws GeneralSecurityException {
/* 123 */     return Mac.getInstance(this.macAlgorithm).getMacLength();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HkdfHpkeKdf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */