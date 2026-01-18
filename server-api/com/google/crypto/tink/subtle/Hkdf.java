/*     */ package com.google.crypto.tink.subtle;
/*     */ 
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
/*     */ public final class Hkdf
/*     */ {
/*     */   public static byte[] computeHkdf(String macAlgorithm, byte[] ikm, byte[] salt, byte[] info, int size) throws GeneralSecurityException {
/*  50 */     Mac mac = EngineFactory.MAC.getInstance(macAlgorithm);
/*  51 */     if (size > 255 * mac.getMacLength()) {
/*  52 */       throw new GeneralSecurityException("size too large");
/*     */     }
/*  54 */     if (salt == null || salt.length == 0) {
/*     */ 
/*     */       
/*  57 */       mac.init(new SecretKeySpec(new byte[mac.getMacLength()], macAlgorithm));
/*     */     } else {
/*  59 */       mac.init(new SecretKeySpec(salt, macAlgorithm));
/*     */     } 
/*  61 */     byte[] prk = mac.doFinal(ikm);
/*  62 */     byte[] result = new byte[size];
/*  63 */     int ctr = 1;
/*  64 */     int pos = 0;
/*  65 */     mac.init(new SecretKeySpec(prk, macAlgorithm));
/*  66 */     byte[] digest = new byte[0];
/*     */     while (true) {
/*  68 */       mac.update(digest);
/*  69 */       mac.update(info);
/*  70 */       mac.update((byte)ctr);
/*  71 */       digest = mac.doFinal();
/*  72 */       if (pos + digest.length < size) {
/*  73 */         System.arraycopy(digest, 0, result, pos, digest.length);
/*  74 */         pos += digest.length;
/*  75 */         ctr++; continue;
/*     */       }  break;
/*  77 */     }  System.arraycopy(digest, 0, result, pos, size - pos);
/*     */ 
/*     */ 
/*     */     
/*  81 */     return result;
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
/*     */   public static byte[] computeEciesHkdfSymmetricKey(byte[] ephemeralPublicKeyBytes, byte[] sharedSecret, String hmacAlgo, byte[] hkdfSalt, byte[] hkdfInfo, int keySizeInBytes) throws GeneralSecurityException {
/* 110 */     byte[] hkdfInput = Bytes.concat(new byte[][] { ephemeralPublicKeyBytes, sharedSecret });
/* 111 */     return computeHkdf(hmacAlgo, hkdfInput, hkdfSalt, hkdfInfo, keySizeInBytes);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\Hkdf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */