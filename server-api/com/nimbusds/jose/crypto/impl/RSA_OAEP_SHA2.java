/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.crypto.opts.CipherMode;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.security.spec.MGF1ParameterSpec;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.OAEPParameterSpec;
/*     */ import javax.crypto.spec.PSource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class RSA_OAEP_SHA2
/*     */ {
/*     */   private static final String RSA_OEAP_256_JCA_ALG = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
/*     */   private static final String RSA_OEAP_384_JCA_ALG = "RSA/ECB/OAEPWithSHA-384AndMGF1Padding";
/*     */   private static final String RSA_OEAP_512_JCA_ALG = "RSA/ECB/OAEPWithSHA-512AndMGF1Padding";
/*     */   private static final String SHA_256_JCA_ALG = "SHA-256";
/*     */   private static final String SHA_384_JCA_ALG = "SHA-384";
/*     */   private static final String SHA_512_JCA_ALG = "SHA-512";
/*     */   
/*     */   public static byte[] encryptCEK(RSAPublicKey pub, SecretKey cek, int shaBitSize, CipherMode mode, Provider provider) throws JOSEException {
/*     */     String jcaAlgName;
/*     */     String jcaShaAlgName;
/*     */     MGF1ParameterSpec mgf1ParameterSpec;
/* 110 */     assert mode == CipherMode.WRAP_UNWRAP || mode == CipherMode.ENCRYPT_DECRYPT;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     if (256 == shaBitSize) {
/* 116 */       jcaAlgName = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
/* 117 */       jcaShaAlgName = "SHA-256";
/* 118 */       mgf1ParameterSpec = MGF1ParameterSpec.SHA256;
/* 119 */     } else if (384 == shaBitSize) {
/* 120 */       jcaAlgName = "RSA/ECB/OAEPWithSHA-384AndMGF1Padding";
/* 121 */       jcaShaAlgName = "SHA-384";
/* 122 */       mgf1ParameterSpec = MGF1ParameterSpec.SHA384;
/* 123 */     } else if (512 == shaBitSize) {
/* 124 */       jcaAlgName = "RSA/ECB/OAEPWithSHA-512AndMGF1Padding";
/* 125 */       jcaShaAlgName = "SHA-512";
/* 126 */       mgf1ParameterSpec = MGF1ParameterSpec.SHA512;
/*     */     } else {
/* 128 */       throw new JOSEException("Unsupported SHA-2 bit size: " + shaBitSize);
/*     */     } 
/*     */     
/*     */     try {
/* 132 */       AlgorithmParameters algp = AlgorithmParametersHelper.getInstance("OAEP", provider);
/* 133 */       AlgorithmParameterSpec paramSpec = new OAEPParameterSpec(jcaShaAlgName, "MGF1", mgf1ParameterSpec, PSource.PSpecified.DEFAULT);
/* 134 */       algp.init(paramSpec);
/* 135 */       Cipher cipher = CipherHelper.getInstance(jcaAlgName, provider);
/* 136 */       cipher.init(mode.getForJWEEncrypter(), pub, algp);
/* 137 */       if (mode == CipherMode.WRAP_UNWRAP) {
/* 138 */         return cipher.wrap(cek);
/*     */       }
/*     */       
/* 141 */       return cipher.doFinal(cek.getEncoded());
/*     */     
/*     */     }
/* 144 */     catch (InvalidKeyException e) {
/* 145 */       throw new JOSEException("Encryption failed due to invalid RSA key for SHA-" + shaBitSize + ": The RSA key may be too short, use a longer key", e);
/*     */     }
/* 147 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       throw new JOSEException(e.getMessage(), e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey decryptCEK(PrivateKey priv, byte[] encryptedCEK, int shaBitSize, CipherMode mode, Provider provider) throws JOSEException {
/*     */     String jcaAlgName;
/*     */     String jcaShaAlgName;
/*     */     MGF1ParameterSpec mgf1ParameterSpec;
/* 180 */     assert mode == CipherMode.WRAP_UNWRAP || mode == CipherMode.ENCRYPT_DECRYPT;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (256 == shaBitSize) {
/* 186 */       jcaAlgName = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
/* 187 */       jcaShaAlgName = "SHA-256";
/* 188 */       mgf1ParameterSpec = MGF1ParameterSpec.SHA256;
/* 189 */     } else if (384 == shaBitSize) {
/* 190 */       jcaAlgName = "RSA/ECB/OAEPWithSHA-384AndMGF1Padding";
/* 191 */       jcaShaAlgName = "SHA-384";
/* 192 */       mgf1ParameterSpec = MGF1ParameterSpec.SHA384;
/* 193 */     } else if (512 == shaBitSize) {
/* 194 */       jcaAlgName = "RSA/ECB/OAEPWithSHA-512AndMGF1Padding";
/* 195 */       jcaShaAlgName = "SHA-512";
/* 196 */       mgf1ParameterSpec = MGF1ParameterSpec.SHA512;
/*     */     } else {
/* 198 */       throw new JOSEException("Unsupported SHA-2 bit size: " + shaBitSize);
/*     */     } 
/*     */     
/*     */     try {
/* 202 */       AlgorithmParameters algp = AlgorithmParametersHelper.getInstance("OAEP", provider);
/* 203 */       AlgorithmParameterSpec paramSpec = new OAEPParameterSpec(jcaShaAlgName, "MGF1", mgf1ParameterSpec, PSource.PSpecified.DEFAULT);
/* 204 */       algp.init(paramSpec);
/* 205 */       Cipher cipher = CipherHelper.getInstance(jcaAlgName, provider);
/* 206 */       cipher.init(mode.getForJWEDecrypter(), priv, algp);
/*     */       
/* 208 */       if (mode == CipherMode.WRAP_UNWRAP) {
/* 209 */         return (SecretKey)cipher.unwrap(encryptedCEK, "AES", 3);
/*     */       }
/*     */       
/* 212 */       return new SecretKeySpec(cipher.doFinal(encryptedCEK), "AES");
/*     */     
/*     */     }
/* 215 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 221 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\RSA_OAEP_SHA2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */