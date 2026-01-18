/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.security.interfaces.ECPublicKey;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.ECPoint;
/*     */ import java.security.spec.ECPrivateKeySpec;
/*     */ import java.security.spec.ECPublicKeySpec;
/*     */ import java.security.spec.EllipticCurve;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.KeyAgreement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EllipticCurves
/*     */ {
/*     */   public enum PointFormatType
/*     */   {
/*  53 */     UNCOMPRESSED,
/*  54 */     COMPRESSED,
/*     */ 
/*     */     
/*  57 */     DO_NOT_USE_CRUNCHY_UNCOMPRESSED;
/*     */   }
/*     */   
/*     */   public enum CurveType
/*     */   {
/*  62 */     NIST_P256,
/*  63 */     NIST_P384,
/*  64 */     NIST_P521;
/*     */   }
/*     */   
/*     */   public enum EcdsaEncoding
/*     */   {
/*  69 */     IEEE_P1363,
/*  70 */     DER;
/*     */   }
/*     */   
/*     */   public static ECParameterSpec getNistP256Params() {
/*  74 */     return EllipticCurvesUtil.NIST_P256_PARAMS;
/*     */   }
/*     */   
/*     */   public static ECParameterSpec getNistP384Params() {
/*  78 */     return EllipticCurvesUtil.NIST_P384_PARAMS;
/*     */   }
/*     */   
/*     */   public static ECParameterSpec getNistP521Params() {
/*  82 */     return EllipticCurvesUtil.NIST_P521_PARAMS;
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
/*     */   static void checkPublicKey(ECPublicKey key) throws GeneralSecurityException {
/* 101 */     EllipticCurvesUtil.checkPointOnCurve(key.getW(), key.getParams().getCurve());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isNistEcParameterSpec(ECParameterSpec spec) {
/* 106 */     return EllipticCurvesUtil.isNistEcParameterSpec(spec);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSameEcParameterSpec(ECParameterSpec one, ECParameterSpec two) {
/* 111 */     return EllipticCurvesUtil.isSameEcParameterSpec(one, two);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validatePublicKey(ECPublicKey publicKey, ECPrivateKey privateKey) throws GeneralSecurityException {
/* 122 */     validatePublicKeySpec(publicKey, privateKey);
/* 123 */     EllipticCurvesUtil.checkPointOnCurve(publicKey.getW(), privateKey.getParams().getCurve());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void validatePublicKeySpec(ECPublicKey publicKey, ECPrivateKey privateKey) throws GeneralSecurityException {
/*     */     try {
/* 130 */       ECParameterSpec publicKeySpec = publicKey.getParams();
/* 131 */       ECParameterSpec privateKeySpec = privateKey.getParams();
/* 132 */       if (!isSameEcParameterSpec(publicKeySpec, privateKeySpec)) {
/* 133 */         throw new GeneralSecurityException("invalid public key spec");
/*     */       }
/* 135 */     } catch (IllegalArgumentException|NullPointerException ex) {
/*     */ 
/*     */       
/* 138 */       throw new GeneralSecurityException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigInteger getModulus(EllipticCurve curve) throws GeneralSecurityException {
/* 149 */     return EllipticCurvesUtil.getModulus(curve);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int fieldSizeInBits(EllipticCurve curve) throws GeneralSecurityException {
/* 159 */     return getModulus(curve).subtract(BigInteger.ONE).bitLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int fieldSizeInBytes(EllipticCurve curve) throws GeneralSecurityException {
/* 169 */     return (fieldSizeInBits(curve) + 7) / 8;
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
/*     */   private static BigInteger modSqrt(BigInteger x, BigInteger p) throws GeneralSecurityException {
/* 182 */     if (p.signum() != 1) {
/* 183 */       throw new InvalidAlgorithmParameterException("p must be positive");
/*     */     }
/* 185 */     x = x.mod(p);
/* 186 */     BigInteger squareRoot = null;
/*     */ 
/*     */     
/* 189 */     if (x.equals(BigInteger.ZERO)) {
/* 190 */       return BigInteger.ZERO;
/*     */     }
/* 192 */     if (p.testBit(0) && p.testBit(1)) {
/*     */ 
/*     */       
/* 195 */       BigInteger q = p.add(BigInteger.ONE).shiftRight(2);
/* 196 */       squareRoot = x.modPow(q, p);
/* 197 */     } else if (p.testBit(0) && !p.testBit(1)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       BigInteger a = BigInteger.ONE;
/* 203 */       BigInteger d = null;
/* 204 */       BigInteger q1 = p.subtract(BigInteger.ONE).shiftRight(1);
/* 205 */       int tries = 0;
/*     */       while (true) {
/* 207 */         d = a.multiply(a).subtract(x).mod(p);
/*     */         
/* 209 */         if (d.equals(BigInteger.ZERO)) {
/* 210 */           return a;
/*     */         }
/*     */         
/* 213 */         BigInteger t = d.modPow(q1, p);
/* 214 */         if (t.add(BigInteger.ONE).equals(p)) {
/*     */           break;
/*     */         }
/* 217 */         if (!t.equals(BigInteger.ONE))
/*     */         {
/* 219 */           throw new InvalidAlgorithmParameterException("p is not prime");
/*     */         }
/* 221 */         a = a.add(BigInteger.ONE);
/*     */         
/* 223 */         tries++;
/*     */ 
/*     */ 
/*     */         
/* 227 */         if (tries == 128 && 
/* 228 */           !p.isProbablePrime(80)) {
/* 229 */           throw new InvalidAlgorithmParameterException("p is not prime");
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 238 */       BigInteger q = p.add(BigInteger.ONE).shiftRight(1);
/* 239 */       BigInteger u = a;
/* 240 */       BigInteger v = BigInteger.ONE;
/* 241 */       for (int bit = q.bitLength() - 2; bit >= 0; bit--) {
/*     */         
/* 243 */         BigInteger tmp = u.multiply(v);
/* 244 */         u = u.multiply(u).add(v.multiply(v).mod(p).multiply(d)).mod(p);
/* 245 */         v = tmp.add(tmp).mod(p);
/* 246 */         if (q.testBit(bit)) {
/*     */           
/* 248 */           tmp = u.multiply(a).add(v.multiply(d)).mod(p);
/* 249 */           v = a.multiply(v).add(u).mod(p);
/* 250 */           u = tmp;
/*     */         } 
/*     */       } 
/* 253 */       squareRoot = u;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 258 */     if (squareRoot != null && squareRoot.multiply(squareRoot).mod(p).compareTo(x) != 0) {
/* 259 */       throw new GeneralSecurityException("Could not find a modular square root");
/*     */     }
/* 261 */     return squareRoot;
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
/*     */   private static BigInteger computeY(BigInteger x, boolean lsb, EllipticCurve curve) throws GeneralSecurityException {
/* 278 */     BigInteger p = getModulus(curve);
/* 279 */     BigInteger a = curve.getA();
/* 280 */     BigInteger b = curve.getB();
/* 281 */     BigInteger rhs = x.multiply(x).add(a).multiply(x).add(b).mod(p);
/* 282 */     BigInteger y = modSqrt(rhs, p);
/* 283 */     if (lsb != y.testBit(0)) {
/* 284 */       y = p.subtract(y).mod(p);
/*     */     }
/* 286 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static BigInteger getY(BigInteger x, boolean lsb, EllipticCurve curve) throws GeneralSecurityException {
/* 297 */     return computeY(x, lsb, curve);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] toMinimalSignedNumber(byte[] bs) {
/* 306 */     int start = 0;
/* 307 */     while (start < bs.length && bs[start] == 0) {
/* 308 */       start++;
/*     */     }
/* 310 */     if (start == bs.length) {
/* 311 */       start = bs.length - 1;
/*     */     }
/*     */     
/* 314 */     int extraZero = 0;
/*     */     
/* 316 */     if ((bs[start] & 0x80) == 128)
/*     */     {
/* 318 */       extraZero = 1;
/*     */     }
/* 320 */     byte[] res = new byte[bs.length - start + extraZero];
/* 321 */     System.arraycopy(bs, start, res, extraZero, bs.length - start);
/* 322 */     return res;
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
/*     */   public static byte[] ecdsaIeee2Der(byte[] ieee) throws GeneralSecurityException {
/*     */     byte[] der;
/* 342 */     if (ieee.length % 2 != 0 || ieee.length == 0 || ieee.length > 132) {
/* 343 */       throw new GeneralSecurityException("Invalid IEEE_P1363 encoding");
/*     */     }
/* 345 */     byte[] r = toMinimalSignedNumber(Arrays.copyOf(ieee, ieee.length / 2));
/* 346 */     byte[] s = toMinimalSignedNumber(Arrays.copyOfRange(ieee, ieee.length / 2, ieee.length));
/*     */     
/* 348 */     int offset = 0;
/* 349 */     int length = 2 + r.length + 1 + 1 + s.length;
/*     */     
/* 351 */     if (length >= 128) {
/* 352 */       der = new byte[length + 3];
/* 353 */       der[offset++] = 48;
/* 354 */       der[offset++] = -127;
/* 355 */       der[offset++] = (byte)length;
/*     */     } else {
/* 357 */       der = new byte[length + 2];
/* 358 */       der[offset++] = 48;
/* 359 */       der[offset++] = (byte)length;
/*     */     } 
/* 361 */     der[offset++] = 2;
/* 362 */     der[offset++] = (byte)r.length;
/* 363 */     System.arraycopy(r, 0, der, offset, r.length);
/* 364 */     offset += r.length;
/* 365 */     der[offset++] = 2;
/* 366 */     der[offset++] = (byte)s.length;
/* 367 */     System.arraycopy(s, 0, der, offset, s.length);
/* 368 */     return der;
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
/*     */   public static byte[] ecdsaDer2Ieee(byte[] der, int ieeeLength) throws GeneralSecurityException {
/* 389 */     if (!isValidDerEncoding(der)) {
/* 390 */       throw new GeneralSecurityException("Invalid DER encoding");
/*     */     }
/* 392 */     byte[] ieee = new byte[ieeeLength];
/* 393 */     int length = der[1] & 0xFF;
/* 394 */     int offset = 2;
/* 395 */     if (length >= 128) {
/* 396 */       offset++;
/*     */     }
/* 398 */     offset++;
/* 399 */     int rLength = der[offset++];
/* 400 */     int extraZero = 0;
/* 401 */     if (der[offset] == 0) {
/* 402 */       extraZero = 1;
/*     */     }
/* 404 */     System.arraycopy(der, offset + extraZero, ieee, ieeeLength / 2 - rLength + extraZero, rLength - extraZero);
/*     */     
/* 406 */     offset += rLength + 1;
/* 407 */     int sLength = der[offset++];
/* 408 */     extraZero = 0;
/* 409 */     if (der[offset] == 0) {
/* 410 */       extraZero = 1;
/*     */     }
/* 412 */     System.arraycopy(der, offset + extraZero, ieee, ieeeLength - sLength + extraZero, sLength - extraZero);
/*     */     
/* 414 */     return ieee;
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
/*     */   public static boolean isValidDerEncoding(byte[] sig) {
/* 429 */     if (sig.length < 8)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 439 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     if (sig[0] != 48) {
/* 446 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 450 */     int totalLen = sig[1] & 0xFF;
/* 451 */     int totalLenLen = 1;
/* 452 */     if (totalLen == 129) {
/*     */ 
/*     */       
/* 455 */       totalLenLen = 2;
/*     */       
/* 457 */       totalLen = sig[2] & 0xFF;
/* 458 */       if (totalLen < 128)
/*     */       {
/* 460 */         return false;
/*     */       }
/* 462 */     } else if (totalLen == 128 || totalLen > 129) {
/*     */       
/* 464 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 468 */     if (totalLen != sig.length - 1 - totalLenLen) {
/* 469 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 474 */     if (sig[1 + totalLenLen] != 2) {
/* 475 */       return false;
/*     */     }
/*     */     
/* 478 */     int rLen = sig[1 + totalLenLen + 1] & 0xFF;
/*     */     
/* 480 */     if (1 + totalLenLen + 1 + 1 + rLen + 1 >= sig.length)
/*     */     {
/* 482 */       return false;
/*     */     }
/*     */     
/* 485 */     if (rLen == 0) {
/* 486 */       return false;
/*     */     }
/*     */     
/* 489 */     if ((sig[3 + totalLenLen] & 0xFF) >= 128) {
/* 490 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 494 */     if (rLen > 1 && sig[3 + totalLenLen] == 0 && (sig[4 + totalLenLen] & 0xFF) < 128) {
/* 495 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 500 */     if (sig[3 + totalLenLen + rLen] != 2) {
/* 501 */       return false;
/*     */     }
/*     */     
/* 504 */     int sLen = sig[1 + totalLenLen + 1 + 1 + rLen + 1] & 0xFF;
/*     */ 
/*     */     
/* 507 */     if (1 + totalLenLen + 1 + 1 + rLen + 1 + 1 + sLen != sig.length)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 516 */       return false;
/*     */     }
/*     */     
/* 519 */     if (sLen == 0) {
/* 520 */       return false;
/*     */     }
/*     */     
/* 523 */     if ((sig[5 + totalLenLen + rLen] & 0xFF) >= 128) {
/* 524 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 528 */     if (sLen > 1 && sig[5 + totalLenLen + rLen] == 0 && (sig[6 + totalLenLen + rLen] & 0xFF) < 128)
/*     */     {
/*     */       
/* 531 */       return false;
/*     */     }
/*     */     
/* 534 */     return true;
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
/*     */   public static int encodingSizeInBytes(EllipticCurve curve, PointFormatType format) throws GeneralSecurityException {
/* 548 */     int coordinateSize = fieldSizeInBytes(curve);
/* 549 */     switch (format.ordinal()) {
/*     */       case 0:
/* 551 */         return 2 * coordinateSize + 1;
/*     */       case 2:
/* 553 */         return 2 * coordinateSize;
/*     */       case 1:
/* 555 */         return coordinateSize + 1;
/*     */     } 
/* 557 */     throw new GeneralSecurityException("unknown EC point format");
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
/*     */   public static ECPoint ecPointDecode(EllipticCurve curve, PointFormatType format, byte[] encoded) throws GeneralSecurityException {
/* 573 */     return pointDecode(curve, format, encoded);
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
/*     */   public static ECPoint pointDecode(CurveType curveType, PointFormatType format, byte[] encoded) throws GeneralSecurityException {
/* 590 */     return pointDecode(getCurveSpec(curveType).getCurve(), format, encoded);
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
/*     */   public static ECPoint pointDecode(EllipticCurve curve, PointFormatType format, byte[] encoded) throws GeneralSecurityException {
/*     */     BigInteger x, p, y;
/*     */     boolean lsb;
/*     */     ECPoint point;
/*     */     BigInteger bigInteger1, bigInteger2;
/* 607 */     int coordinateSize = fieldSizeInBytes(curve);
/* 608 */     switch (format.ordinal()) {
/*     */       
/*     */       case 0:
/* 611 */         if (encoded.length != 2 * coordinateSize + 1) {
/* 612 */           throw new GeneralSecurityException("invalid point size");
/*     */         }
/* 614 */         if (encoded[0] != 4) {
/* 615 */           throw new GeneralSecurityException("invalid point format");
/*     */         }
/* 617 */         x = new BigInteger(1, Arrays.copyOfRange(encoded, 1, coordinateSize + 1));
/*     */         
/* 619 */         y = new BigInteger(1, Arrays.copyOfRange(encoded, coordinateSize + 1, encoded.length));
/* 620 */         point = new ECPoint(x, y);
/* 621 */         EllipticCurvesUtil.checkPointOnCurve(point, curve);
/* 622 */         return point;
/*     */ 
/*     */       
/*     */       case 2:
/* 626 */         if (encoded.length != 2 * coordinateSize) {
/* 627 */           throw new GeneralSecurityException("invalid point size");
/*     */         }
/* 629 */         x = new BigInteger(1, Arrays.copyOf(encoded, coordinateSize));
/*     */         
/* 631 */         y = new BigInteger(1, Arrays.copyOfRange(encoded, coordinateSize, encoded.length));
/* 632 */         point = new ECPoint(x, y);
/* 633 */         EllipticCurvesUtil.checkPointOnCurve(point, curve);
/* 634 */         return point;
/*     */ 
/*     */       
/*     */       case 1:
/* 638 */         p = getModulus(curve);
/* 639 */         if (encoded.length != coordinateSize + 1) {
/* 640 */           throw new GeneralSecurityException("compressed point has wrong length");
/*     */         }
/*     */         
/* 643 */         if (encoded[0] == 2) {
/* 644 */           lsb = false;
/* 645 */         } else if (encoded[0] == 3) {
/* 646 */           lsb = true;
/*     */         } else {
/* 648 */           throw new GeneralSecurityException("invalid format");
/*     */         } 
/* 650 */         bigInteger1 = new BigInteger(1, Arrays.copyOfRange(encoded, 1, encoded.length));
/* 651 */         if (bigInteger1.signum() == -1 || bigInteger1.compareTo(p) >= 0) {
/* 652 */           throw new GeneralSecurityException("x is out of range");
/*     */         }
/* 654 */         bigInteger2 = computeY(bigInteger1, lsb, curve);
/* 655 */         return new ECPoint(bigInteger1, bigInteger2);
/*     */     } 
/*     */     
/* 658 */     throw new GeneralSecurityException("invalid format:" + format);
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
/*     */   public static byte[] pointEncode(CurveType curveType, PointFormatType format, ECPoint point) throws GeneralSecurityException {
/* 674 */     return pointEncode(getCurveSpec(curveType).getCurve(), format, point);
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
/*     */   public static byte[] pointEncode(EllipticCurve curve, PointFormatType format, ECPoint point) throws GeneralSecurityException {
/*     */     byte[] encoded, x, y;
/* 690 */     EllipticCurvesUtil.checkPointOnCurve(point, curve);
/* 691 */     int coordinateSize = fieldSizeInBytes(curve);
/* 692 */     switch (format.ordinal()) {
/*     */       
/*     */       case 0:
/* 695 */         encoded = new byte[2 * coordinateSize + 1];
/* 696 */         x = BigIntegerEncoding.toBigEndianBytes(point.getAffineX());
/* 697 */         y = BigIntegerEncoding.toBigEndianBytes(point.getAffineY());
/*     */         
/* 699 */         System.arraycopy(y, 0, encoded, 1 + 2 * coordinateSize - y.length, y.length);
/* 700 */         System.arraycopy(x, 0, encoded, 1 + coordinateSize - x.length, x.length);
/* 701 */         encoded[0] = 4;
/* 702 */         return encoded;
/*     */ 
/*     */       
/*     */       case 2:
/* 706 */         encoded = new byte[2 * coordinateSize];
/* 707 */         x = BigIntegerEncoding.toBigEndianBytes(point.getAffineX());
/* 708 */         if (x.length > coordinateSize)
/*     */         {
/* 710 */           x = Arrays.copyOfRange(x, x.length - coordinateSize, x.length);
/*     */         }
/* 712 */         y = BigIntegerEncoding.toBigEndianBytes(point.getAffineY());
/* 713 */         if (y.length > coordinateSize)
/*     */         {
/* 715 */           y = Arrays.copyOfRange(y, y.length - coordinateSize, y.length);
/*     */         }
/* 717 */         System.arraycopy(y, 0, encoded, 2 * coordinateSize - y.length, y.length);
/* 718 */         System.arraycopy(x, 0, encoded, coordinateSize - x.length, x.length);
/* 719 */         return encoded;
/*     */ 
/*     */       
/*     */       case 1:
/* 723 */         encoded = new byte[coordinateSize + 1];
/* 724 */         x = BigIntegerEncoding.toBigEndianBytes(point.getAffineX());
/* 725 */         System.arraycopy(x, 0, encoded, 1 + coordinateSize - x.length, x.length);
/* 726 */         encoded[0] = (byte)(point.getAffineY().testBit(0) ? 3 : 2);
/* 727 */         return encoded;
/*     */     } 
/*     */     
/* 730 */     throw new GeneralSecurityException("invalid format:" + format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ECParameterSpec getCurveSpec(CurveType curve) throws NoSuchAlgorithmException {
/* 740 */     switch (curve.ordinal()) {
/*     */       case 0:
/* 742 */         return getNistP256Params();
/*     */       case 1:
/* 744 */         return getNistP384Params();
/*     */       case 2:
/* 746 */         return getNistP521Params();
/*     */     } 
/* 748 */     throw new NoSuchAlgorithmException("curve not implemented:" + curve);
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
/*     */   public static ECPublicKey getEcPublicKey(byte[] x509PublicKey) throws GeneralSecurityException {
/* 760 */     KeyFactory kf = EngineFactory.KEY_FACTORY.getInstance("EC");
/* 761 */     return (ECPublicKey)kf.generatePublic(new X509EncodedKeySpec(x509PublicKey));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ECPublicKey getEcPublicKey(CurveType curve, PointFormatType pointFormat, byte[] publicKey) throws GeneralSecurityException {
/* 771 */     return getEcPublicKey(getCurveSpec(curve), pointFormat, publicKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ECPublicKey getEcPublicKey(ECParameterSpec spec, PointFormatType pointFormat, byte[] publicKey) throws GeneralSecurityException {
/* 781 */     ECPoint point = pointDecode(spec.getCurve(), pointFormat, publicKey);
/* 782 */     ECPublicKeySpec pubSpec = new ECPublicKeySpec(point, spec);
/* 783 */     KeyFactory kf = EngineFactory.KEY_FACTORY.getInstance("EC");
/* 784 */     return (ECPublicKey)kf.generatePublic(pubSpec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ECPublicKey getEcPublicKey(CurveType curve, byte[] x, byte[] y) throws GeneralSecurityException {
/* 792 */     ECParameterSpec ecParams = getCurveSpec(curve);
/* 793 */     BigInteger pubX = new BigInteger(1, x);
/* 794 */     BigInteger pubY = new BigInteger(1, y);
/* 795 */     ECPoint w = new ECPoint(pubX, pubY);
/* 796 */     EllipticCurvesUtil.checkPointOnCurve(w, ecParams.getCurve());
/* 797 */     ECPublicKeySpec spec = new ECPublicKeySpec(w, ecParams);
/* 798 */     KeyFactory kf = EngineFactory.KEY_FACTORY.getInstance("EC");
/* 799 */     return (ECPublicKey)kf.generatePublic(spec);
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
/*     */   public static ECPrivateKey getEcPrivateKey(byte[] pkcs8PrivateKey) throws GeneralSecurityException {
/* 811 */     KeyFactory kf = EngineFactory.KEY_FACTORY.getInstance("EC");
/* 812 */     return (ECPrivateKey)kf.generatePrivate(new PKCS8EncodedKeySpec(pkcs8PrivateKey));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ECPrivateKey getEcPrivateKey(CurveType curve, byte[] keyValue) throws GeneralSecurityException {
/* 818 */     ECParameterSpec ecParams = getCurveSpec(curve);
/* 819 */     BigInteger privValue = BigIntegerEncoding.fromUnsignedBigEndianBytes(keyValue);
/* 820 */     ECPrivateKeySpec spec = new ECPrivateKeySpec(privValue, ecParams);
/* 821 */     KeyFactory kf = EngineFactory.KEY_FACTORY.getInstance("EC");
/* 822 */     return (ECPrivateKey)kf.generatePrivate(spec);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeyPair generateKeyPair(CurveType curve) throws GeneralSecurityException {
/* 827 */     return generateKeyPair(getCurveSpec(curve));
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeyPair generateKeyPair(ECParameterSpec spec) throws GeneralSecurityException {
/* 832 */     KeyPairGenerator keyGen = EngineFactory.KEY_PAIR_GENERATOR.getInstance("EC");
/* 833 */     keyGen.initialize(spec);
/* 834 */     return keyGen.generateKeyPair();
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
/*     */   static void validateSharedSecret(byte[] secret, ECPrivateKey privateKey) throws GeneralSecurityException {
/* 848 */     EllipticCurve privateKeyCurve = privateKey.getParams().getCurve();
/* 849 */     BigInteger x = new BigInteger(1, secret);
/* 850 */     if (x.signum() == -1 || x.compareTo(getModulus(privateKeyCurve)) >= 0) {
/* 851 */       throw new GeneralSecurityException("shared secret is out of range");
/*     */     }
/*     */     
/* 854 */     Object unused = computeY(x, true, privateKeyCurve);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] computeSharedSecret(ECPrivateKey myPrivateKey, ECPublicKey peerPublicKey) throws GeneralSecurityException {
/* 860 */     validatePublicKeySpec(peerPublicKey, myPrivateKey);
/* 861 */     return computeSharedSecret(myPrivateKey, peerPublicKey.getW());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] computeSharedSecret(ECPrivateKey myPrivateKey, ECPoint publicPoint) throws GeneralSecurityException {
/* 871 */     EllipticCurvesUtil.checkPointOnCurve(publicPoint, myPrivateKey.getParams().getCurve());
/*     */     
/* 873 */     ECParameterSpec privSpec = myPrivateKey.getParams();
/* 874 */     ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(publicPoint, privSpec);
/* 875 */     KeyFactory kf = EngineFactory.KEY_FACTORY.getInstance("EC");
/* 876 */     PublicKey publicKey = kf.generatePublic(publicKeySpec);
/* 877 */     KeyAgreement ka = EngineFactory.KEY_AGREEMENT.getInstance("ECDH");
/* 878 */     ka.init(myPrivateKey);
/*     */     try {
/* 880 */       ka.doPhase(publicKey, true);
/* 881 */       byte[] secret = ka.generateSecret();
/* 882 */       validateSharedSecret(secret, myPrivateKey);
/* 883 */       return secret;
/* 884 */     } catch (IllegalStateException ex) {
/*     */ 
/*     */       
/* 887 */       throw new GeneralSecurityException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EllipticCurves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */