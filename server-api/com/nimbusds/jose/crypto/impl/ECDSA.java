/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.ECParameterTable;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import java.math.BigInteger;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import java.security.interfaces.ECKey;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ECDSA
/*     */ {
/*     */   public static JWSAlgorithm resolveAlgorithm(ECKey ecKey) throws JOSEException {
/*  59 */     ECParameterSpec ecParameterSpec = ecKey.getParams();
/*  60 */     return resolveAlgorithm(Curve.forECParameterSpec(ecParameterSpec));
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
/*     */   public static JWSAlgorithm resolveAlgorithm(Curve curve) throws JOSEException {
/*  77 */     if (curve == null)
/*  78 */       throw new JOSEException("The EC key curve is not supported, must be P-256, P-384 or P-521"); 
/*  79 */     if (Curve.P_256.equals(curve))
/*  80 */       return JWSAlgorithm.ES256; 
/*  81 */     if (Curve.SECP256K1.equals(curve))
/*  82 */       return JWSAlgorithm.ES256K; 
/*  83 */     if (Curve.P_384.equals(curve))
/*  84 */       return JWSAlgorithm.ES384; 
/*  85 */     if (Curve.P_521.equals(curve)) {
/*  86 */       return JWSAlgorithm.ES512;
/*     */     }
/*  88 */     throw new JOSEException("Unexpected curve: " + curve);
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
/*     */   public static Signature getSignerAndVerifier(JWSAlgorithm alg, Provider jcaProvider) throws JOSEException {
/*     */     String jcaAlg;
/* 111 */     if (alg.equals(JWSAlgorithm.ES256)) {
/* 112 */       jcaAlg = "SHA256withECDSA";
/* 113 */     } else if (alg.equals(JWSAlgorithm.ES256K)) {
/* 114 */       jcaAlg = "SHA256withECDSA";
/* 115 */     } else if (alg.equals(JWSAlgorithm.ES384)) {
/* 116 */       jcaAlg = "SHA384withECDSA";
/* 117 */     } else if (alg.equals(JWSAlgorithm.ES512)) {
/* 118 */       jcaAlg = "SHA512withECDSA";
/*     */     } else {
/* 120 */       throw new JOSEException(
/* 121 */           AlgorithmSupportMessage.unsupportedJWSAlgorithm(alg, ECDSAProvider.SUPPORTED_ALGORITHMS));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 127 */       if (jcaProvider != null) {
/* 128 */         return Signature.getInstance(jcaAlg, jcaProvider);
/*     */       }
/* 130 */       return Signature.getInstance(jcaAlg);
/*     */     }
/* 132 */     catch (NoSuchAlgorithmException e) {
/* 133 */       throw new JOSEException("Unsupported ECDSA algorithm: " + e.getMessage(), e);
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
/*     */   public static int getSignatureByteArrayLength(JWSAlgorithm alg) throws JOSEException {
/* 152 */     if (alg.equals(JWSAlgorithm.ES256))
/*     */     {
/* 154 */       return 64;
/*     */     }
/* 156 */     if (alg.equals(JWSAlgorithm.ES256K))
/*     */     {
/* 158 */       return 64;
/*     */     }
/* 160 */     if (alg.equals(JWSAlgorithm.ES384))
/*     */     {
/* 162 */       return 96;
/*     */     }
/* 164 */     if (alg.equals(JWSAlgorithm.ES512))
/*     */     {
/* 166 */       return 132;
/*     */     }
/*     */ 
/*     */     
/* 170 */     throw new JOSEException(AlgorithmSupportMessage.unsupportedJWSAlgorithm(alg, ECDSAProvider.SUPPORTED_ALGORITHMS));
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
/*     */   public static byte[] transcodeSignatureToConcat(byte[] derSignature, int outputLength) throws JOSEException {
/*     */     int offset;
/* 191 */     if (derSignature.length < 8 || derSignature[0] != 48) {
/* 192 */       throw new JOSEException("Invalid ECDSA signature format");
/*     */     }
/*     */ 
/*     */     
/* 196 */     if (derSignature[1] > 0) {
/* 197 */       offset = 2;
/* 198 */     } else if (derSignature[1] == -127) {
/* 199 */       offset = 3;
/*     */     } else {
/* 201 */       throw new JOSEException("Invalid ECDSA signature format");
/*     */     } 
/*     */     
/* 204 */     byte rLength = derSignature[offset + 1];
/*     */     
/*     */     int i;
/* 207 */     for (i = rLength; i > 0 && derSignature[offset + 2 + rLength - i] == 0; i--);
/*     */ 
/*     */ 
/*     */     
/* 211 */     byte sLength = derSignature[offset + 2 + rLength + 1];
/*     */     
/*     */     int j;
/* 214 */     for (j = sLength; j > 0 && derSignature[offset + 2 + rLength + 2 + sLength - j] == 0; j--);
/*     */ 
/*     */ 
/*     */     
/* 218 */     int rawLen = Math.max(i, j);
/* 219 */     rawLen = Math.max(rawLen, outputLength / 2);
/*     */     
/* 221 */     if ((derSignature[offset - 1] & 0xFF) != derSignature.length - offset || (derSignature[offset - 1] & 0xFF) != 2 + rLength + 2 + sLength || derSignature[offset] != 2 || derSignature[offset + 2 + rLength] != 2)
/*     */     {
/*     */ 
/*     */       
/* 225 */       throw new JOSEException("Invalid ECDSA signature format");
/*     */     }
/*     */     
/* 228 */     byte[] concatSignature = new byte[2 * rawLen];
/*     */     
/* 230 */     System.arraycopy(derSignature, offset + 2 + rLength - i, concatSignature, rawLen - i, i);
/* 231 */     System.arraycopy(derSignature, offset + 2 + rLength + 2 + sLength - j, concatSignature, 2 * rawLen - j, j);
/*     */     
/* 233 */     return concatSignature;
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
/*     */   public static byte[] transcodeSignatureToDER(byte[] jwsSignature) throws JOSEException {
/*     */     try {
/*     */       int offset;
/*     */       byte[] derSignature;
/* 256 */       int rawLen = jwsSignature.length / 2;
/*     */       
/*     */       int i;
/*     */       
/* 260 */       for (i = rawLen; i > 0 && jwsSignature[rawLen - i] == 0; i--);
/*     */ 
/*     */ 
/*     */       
/* 264 */       int j = i;
/*     */       
/* 266 */       if (jwsSignature[rawLen - i] < 0) {
/* 267 */         j++;
/*     */       }
/*     */       
/*     */       int k;
/*     */       
/* 272 */       for (k = rawLen; k > 0 && jwsSignature[2 * rawLen - k] == 0; k--);
/*     */ 
/*     */ 
/*     */       
/* 276 */       int l = k;
/*     */       
/* 278 */       if (jwsSignature[2 * rawLen - k] < 0) {
/* 279 */         l++;
/*     */       }
/*     */       
/* 282 */       int len = 2 + j + 2 + l;
/*     */       
/* 284 */       if (len > 255) {
/* 285 */         throw new JOSEException("Invalid ECDSA signature format");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       if (len < 128) {
/* 293 */         derSignature = new byte[4 + j + 2 + l];
/* 294 */         offset = 1;
/*     */       } else {
/* 296 */         derSignature = new byte[5 + j + 2 + l];
/* 297 */         derSignature[1] = -127;
/* 298 */         offset = 2;
/*     */       } 
/*     */       
/* 301 */       derSignature[0] = 48;
/* 302 */       derSignature[offset++] = (byte)len;
/* 303 */       derSignature[offset++] = 2;
/* 304 */       derSignature[offset++] = (byte)j;
/*     */       
/* 306 */       System.arraycopy(jwsSignature, rawLen - i, derSignature, offset + j - i, i);
/*     */       
/* 308 */       offset += j;
/*     */       
/* 310 */       derSignature[offset++] = 2;
/* 311 */       derSignature[offset++] = (byte)l;
/*     */       
/* 313 */       System.arraycopy(jwsSignature, 2 * rawLen - k, derSignature, offset + l - k, k);
/*     */       
/* 315 */       return derSignature;
/*     */     }
/* 317 */     catch (Exception e) {
/*     */ 
/*     */       
/* 320 */       if (e instanceof JOSEException) {
/* 321 */         throw e;
/*     */       }
/*     */       
/* 324 */       throw new JOSEException(e.getMessage(), e);
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
/*     */   public static void ensureLegalSignature(byte[] jwsSignature, JWSAlgorithm jwsAlg) throws JOSEException {
/* 345 */     if (ByteUtils.isZeroFilled(jwsSignature))
/*     */     {
/* 347 */       throw new JOSEException("Blank signature");
/*     */     }
/*     */     
/* 350 */     Set<Curve> matchingCurves = Curve.forJWSAlgorithm(jwsAlg);
/* 351 */     if (matchingCurves == null || matchingCurves.size() > 1) {
/* 352 */       throw new JOSEException("Unsupported JWS algorithm: " + jwsAlg);
/*     */     }
/*     */     
/* 355 */     Curve curve = matchingCurves.iterator().next();
/*     */     
/* 357 */     ECParameterSpec ecParameterSpec = ECParameterTable.get(curve);
/*     */     
/* 359 */     if (ecParameterSpec == null) {
/* 360 */       throw new JOSEException("Unsupported curve: " + curve);
/*     */     }
/*     */     
/* 363 */     int signatureLength = getSignatureByteArrayLength(jwsAlg);
/*     */     
/* 365 */     if (getSignatureByteArrayLength(jwsAlg) != jwsSignature.length)
/*     */     {
/*     */       
/* 368 */       throw new JOSEException("Illegal signature length");
/*     */     }
/*     */ 
/*     */     
/* 372 */     int valueLength = signatureLength / 2;
/*     */ 
/*     */     
/* 375 */     byte[] rBytes = ByteUtils.subArray(jwsSignature, 0, valueLength);
/* 376 */     BigInteger rValue = new BigInteger(1, rBytes);
/*     */ 
/*     */     
/* 379 */     byte[] sBytes = ByteUtils.subArray(jwsSignature, valueLength, valueLength);
/* 380 */     BigInteger sValue = new BigInteger(1, sBytes);
/*     */ 
/*     */     
/* 383 */     if (sValue.equals(BigInteger.ZERO) || rValue.equals(BigInteger.ZERO)) {
/* 384 */       throw new JOSEException("S and R must not be 0");
/*     */     }
/*     */     
/* 387 */     BigInteger N = ecParameterSpec.getOrder();
/*     */ 
/*     */     
/* 390 */     if (N.compareTo(rValue) < 1 || N.compareTo(sValue) < 1) {
/* 391 */       throw new JOSEException("S and R must not exceed N");
/*     */     }
/*     */ 
/*     */     
/* 395 */     if (rValue.mod(N).equals(BigInteger.ZERO) || sValue.mod(N).equals(BigInteger.ZERO))
/* 396 */       throw new JOSEException("R or S mod N != 0 check failed"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\ECDSA.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */