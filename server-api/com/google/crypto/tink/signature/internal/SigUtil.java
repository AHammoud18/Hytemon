/*    */ package com.google.crypto.tink.signature.internal;
/*    */ 
/*    */ import com.google.crypto.tink.proto.EcdsaSignatureEncoding;
/*    */ import com.google.crypto.tink.proto.EllipticCurveType;
/*    */ import com.google.crypto.tink.proto.HashType;
/*    */ import com.google.crypto.tink.subtle.EllipticCurves;
/*    */ import com.google.crypto.tink.subtle.Enums;
/*    */ import com.google.protobuf.ByteString;
/*    */ import java.math.BigInteger;
/*    */ import java.security.GeneralSecurityException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SigUtil
/*    */ {
/*    */   static final String INVALID_PARAMS = "Invalid ECDSA parameters";
/*    */   
/*    */   public static Enums.HashType toHashType(HashType hash) throws GeneralSecurityException {
/* 38 */     switch (hash) {
/*    */       case IEEE_P1363:
/* 40 */         return Enums.HashType.SHA256;
/*    */       case DER:
/* 42 */         return Enums.HashType.SHA384;
/*    */       case null:
/* 44 */         return Enums.HashType.SHA512;
/*    */     } 
/*    */ 
/*    */     
/* 48 */     throw new GeneralSecurityException("unsupported hash type: " + hash.name());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static EllipticCurves.CurveType toCurveType(EllipticCurveType type) throws GeneralSecurityException {
/* 54 */     switch (type) {
/*    */       case IEEE_P1363:
/* 56 */         return EllipticCurves.CurveType.NIST_P256;
/*    */       case DER:
/* 58 */         return EllipticCurves.CurveType.NIST_P384;
/*    */       case null:
/* 60 */         return EllipticCurves.CurveType.NIST_P521;
/*    */     } 
/* 62 */     throw new GeneralSecurityException("unknown curve type: " + type.name());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EllipticCurves.EcdsaEncoding toEcdsaEncoding(EcdsaSignatureEncoding encoding) throws GeneralSecurityException {
/* 72 */     switch (encoding) {
/*    */       case IEEE_P1363:
/* 74 */         return EllipticCurves.EcdsaEncoding.IEEE_P1363;
/*    */       case DER:
/* 76 */         return EllipticCurves.EcdsaEncoding.DER;
/*    */     } 
/* 78 */     throw new GeneralSecurityException("unknown ECDSA encoding: " + encoding.name());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ByteString toUnsignedIntByteString(BigInteger i) {
/* 88 */     byte[] twosComplement = i.toByteArray();
/* 89 */     if (twosComplement[0] == 0) {
/* 90 */       return ByteString.copyFrom(twosComplement, 1, twosComplement.length - 1);
/*    */     }
/* 92 */     return ByteString.copyFrom(twosComplement);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\SigUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */