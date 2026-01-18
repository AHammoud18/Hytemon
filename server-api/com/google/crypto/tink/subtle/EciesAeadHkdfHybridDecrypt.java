/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.HybridDecrypt;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.hybrid.EciesPrivateKey;
/*     */ import com.google.crypto.tink.hybrid.internal.EciesDemHelper;
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.security.spec.EllipticCurve;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EciesAeadHkdfHybridDecrypt
/*     */   implements HybridDecrypt
/*     */ {
/*     */   private final ECPrivateKey recipientPrivateKey;
/*     */   private final EciesHkdfRecipientKem recipientKem;
/*     */   private final String hkdfHmacAlgo;
/*     */   private final byte[] hkdfSalt;
/*     */   private final EllipticCurves.PointFormatType ecPointFormat;
/*     */   private final EciesDemHelper.Dem dem;
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private EciesAeadHkdfHybridDecrypt(ECPrivateKey recipientPrivateKey, byte[] hkdfSalt, String hkdfHmacAlgo, EllipticCurves.PointFormatType ecPointFormat, EciesDemHelper.Dem dem, byte[] outputPrefix) {
/*  54 */     this.recipientPrivateKey = recipientPrivateKey;
/*  55 */     this.recipientKem = new EciesHkdfRecipientKem(recipientPrivateKey);
/*  56 */     this.hkdfSalt = hkdfSalt;
/*  57 */     this.hkdfHmacAlgo = hkdfHmacAlgo;
/*  58 */     this.ecPointFormat = ecPointFormat;
/*  59 */     this.dem = dem;
/*  60 */     this.outputPrefix = outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static HybridDecrypt create(EciesPrivateKey key) throws GeneralSecurityException {
/*  66 */     EllipticCurves.CurveType curveType = (EllipticCurves.CurveType)EciesAeadHkdfHybridEncrypt.CURVE_TYPE_CONVERTER.toProtoEnum(key
/*  67 */         .getParameters().getCurveType());
/*     */     
/*  69 */     ECPrivateKey recipientPrivateKey = EllipticCurves.getEcPrivateKey(curveType, 
/*     */         
/*  71 */         BigIntegerEncoding.toBigEndianBytes(key
/*  72 */           .getNistPrivateKeyValue().getBigInteger(InsecureSecretKeyAccess.get())));
/*  73 */     byte[] hkdfSalt = new byte[0];
/*  74 */     if (key.getParameters().getSalt() != null) {
/*  75 */       hkdfSalt = key.getParameters().getSalt().toByteArray();
/*     */     }
/*  77 */     return new EciesAeadHkdfHybridDecrypt(recipientPrivateKey, hkdfSalt, 
/*     */ 
/*     */         
/*  80 */         EciesAeadHkdfHybridEncrypt.toHmacAlgo(key.getParameters().getHashType()), (EllipticCurves.PointFormatType)EciesAeadHkdfHybridEncrypt.POINT_FORMAT_TYPE_CONVERTER
/*  81 */         .toProtoEnum(key
/*  82 */           .getParameters().getNistCurvePointFormat()), 
/*  83 */         EciesDemHelper.getDem(key.getParameters()), key
/*  84 */         .getOutputPrefix().toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] contextInfo) throws GeneralSecurityException {
/*  90 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/*  91 */       throw new GeneralSecurityException("Invalid ciphertext (output prefix mismatch)");
/*     */     }
/*  93 */     int prefixSize = this.outputPrefix.length;
/*  94 */     EllipticCurve curve = this.recipientPrivateKey.getParams().getCurve();
/*  95 */     int headerSize = EllipticCurves.encodingSizeInBytes(curve, this.ecPointFormat);
/*  96 */     if (ciphertext.length < prefixSize + headerSize) {
/*  97 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/*  99 */     byte[] kemBytes = Arrays.copyOfRange(ciphertext, prefixSize, prefixSize + headerSize);
/*     */     
/* 101 */     byte[] symmetricKey = this.recipientKem.generateKey(kemBytes, this.hkdfHmacAlgo, this.hkdfSalt, contextInfo, this.dem
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 106 */         .getSymmetricKeySizeInBytes(), this.ecPointFormat);
/*     */     
/* 108 */     return this.dem.decrypt(symmetricKey, ciphertext, prefixSize + headerSize);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EciesAeadHkdfHybridDecrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */