/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import javax.crypto.SecretKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DirectCryptoProvider
/*     */   extends BaseJWEProvider
/*     */ {
/*     */   public static final Set<JWEAlgorithm> SUPPORTED_ALGORITHMS;
/*  72 */   public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS = ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS;
/*     */ 
/*     */   
/*     */   static {
/*  76 */     Set<JWEAlgorithm> algs = new LinkedHashSet<>();
/*  77 */     algs.add(JWEAlgorithm.DIR);
/*  78 */     SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
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
/*     */   private static Set<EncryptionMethod> getCompatibleEncryptionMethods(int cekBitLength) throws KeyLengthException {
/*  95 */     if (cekBitLength == 0)
/*     */     {
/*     */       
/*  98 */       return (Set<EncryptionMethod>)EncryptionMethod.Family.AES_GCM;
/*     */     }
/*     */     
/* 101 */     Set<EncryptionMethod> encs = ContentCryptoProvider.COMPATIBLE_ENCRYPTION_METHODS.get(Integer.valueOf(cekBitLength));
/*     */     
/* 103 */     if (encs == null) {
/* 104 */       throw new KeyLengthException("The Content Encryption Key length must be 128 bits (16 bytes), 192 bits (24 bytes), 256 bits (32 bytes), 384 bits (48 bytes) or 512 bites (64 bytes)");
/*     */     }
/*     */     
/* 107 */     return encs;
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
/*     */   protected DirectCryptoProvider(SecretKey cek) throws KeyLengthException {
/* 124 */     super(SUPPORTED_ALGORITHMS, getCompatibleEncryptionMethods(ByteUtils.bitLength(cek.getEncoded())), cek);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey getKey() {
/*     */     try {
/* 135 */       return getCEK(null);
/* 136 */     } catch (Exception e) {
/* 137 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\DirectCryptoProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */