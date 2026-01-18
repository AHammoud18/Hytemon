/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Random;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECField;
/*     */ import java.security.spec.ECFieldFp;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.ECPoint;
/*     */ import java.security.spec.EllipticCurve;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EllipticCurvesUtil
/*     */ {
/*  31 */   public static final ECParameterSpec NIST_P256_PARAMS = getNistP256Params();
/*  32 */   public static final ECParameterSpec NIST_P384_PARAMS = getNistP384Params();
/*  33 */   public static final ECParameterSpec NIST_P521_PARAMS = getNistP521Params();
/*     */   
/*     */   private static ECParameterSpec getNistP256Params() {
/*  36 */     return getNistCurveSpec("115792089210356248762697446949407573530086143415290314195533631308867097853951", "115792089210356248762697446949407573529996955224135760342422259061068512044369", "5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", "6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296", "4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ECParameterSpec getNistP384Params() {
/*  45 */     return getNistCurveSpec("39402006196394479212279040100143613805079739270465446667948293404245721771496870329047266088258938001861606973112319", "39402006196394479212279040100143613805079739270465446667946905279627659399113263569398956308152294913554433653942643", "b3312fa7e23ee7e4988e056be3f82d19181d9c6efe8141120314088f5013875ac656398d8a2ed19d2a85c8edd3ec2aef", "aa87ca22be8b05378eb1c71ef320ad746e1d3b628ba79b9859f741e082542a385502f25dbf55296c3a545e3872760ab7", "3617de4a96262c6f5d9e98bf9292dc29f8f41dbd289a147ce9da3113b5f0b8c00a60b1ce1d7e819d7a431d7c90ea0e5f");
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
/*     */   private static ECParameterSpec getNistP521Params() {
/*  59 */     return getNistCurveSpec("6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057151", "6864797660130609714981900799081393217269435300143305409394463459185543183397655394245057746333217197532963996371363321113864768612440380340372808892707005449", "051953eb9618e1c9a1f929a21a0b68540eea2da725b99b315f3b8b489918ef109e156193951ec7e937b1652c0bd3bb1bf073573df883d2c34f1ef451fd46b503f00", "c6858e06b70404e9cd9e3ecb662395b4429c648139053fb521f828af606b4d3dbaa14b5e77efe75928fe1dc127a2ffa8de3348b3c1856a429bf97e7e31c2e5bd66", "11839296a789a3bc0045c8a5fb42c7d1bd998f54449579b446817afbd17273e662c97ee72995ef42640c550b9013fad0761353c7086a272c24088be94769fd16650");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkPointOnCurve(ECPoint point, EllipticCurve ec) throws GeneralSecurityException {
/*  92 */     BigInteger p = getModulus(ec);
/*  93 */     BigInteger x = point.getAffineX();
/*  94 */     BigInteger y = point.getAffineY();
/*  95 */     if (x == null || y == null) {
/*  96 */       throw new GeneralSecurityException("point is at infinity");
/*     */     }
/*     */     
/*  99 */     if (x.signum() == -1 || x.compareTo(p) >= 0) {
/* 100 */       throw new GeneralSecurityException("x is out of range");
/*     */     }
/* 102 */     if (y.signum() == -1 || y.compareTo(p) >= 0) {
/* 103 */       throw new GeneralSecurityException("y is out of range");
/*     */     }
/*     */     
/* 106 */     BigInteger lhs = y.multiply(y).mod(p);
/* 107 */     BigInteger rhs = x.multiply(x).add(ec.getA()).multiply(x).add(ec.getB()).mod(p);
/* 108 */     if (!lhs.equals(rhs)) {
/* 109 */       throw new GeneralSecurityException("Point is not on curve");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isNistEcParameterSpec(ECParameterSpec spec) {
/* 115 */     return (isSameEcParameterSpec(spec, NIST_P256_PARAMS) || 
/* 116 */       isSameEcParameterSpec(spec, NIST_P384_PARAMS) || 
/* 117 */       isSameEcParameterSpec(spec, NIST_P521_PARAMS));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSameEcParameterSpec(ECParameterSpec one, ECParameterSpec two) {
/* 122 */     return (one.getCurve().equals(two.getCurve()) && one
/* 123 */       .getGenerator().equals(two.getGenerator()) && one
/* 124 */       .getOrder().equals(two.getOrder()) && one
/* 125 */       .getCofactor() == two.getCofactor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigInteger getModulus(EllipticCurve curve) throws GeneralSecurityException {
/* 135 */     ECField field = curve.getField();
/* 136 */     if (field instanceof ECFieldFp) {
/* 137 */       return ((ECFieldFp)field).getP();
/*     */     }
/* 139 */     throw new GeneralSecurityException("Only curves over prime order fields are supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ECParameterSpec getNistCurveSpec(String decimalP, String decimalN, String hexB, String hexGX, String hexGY) {
/* 145 */     BigInteger p = new BigInteger(decimalP);
/* 146 */     BigInteger n = new BigInteger(decimalN);
/* 147 */     BigInteger three = new BigInteger("3");
/* 148 */     BigInteger a = p.subtract(three);
/* 149 */     BigInteger b = new BigInteger(hexB, 16);
/* 150 */     BigInteger gx = new BigInteger(hexGX, 16);
/* 151 */     BigInteger gy = new BigInteger(hexGY, 16);
/* 152 */     int h = 1;
/* 153 */     ECFieldFp fp = new ECFieldFp(p);
/* 154 */     EllipticCurve curveSpec = new EllipticCurve(fp, a, b);
/* 155 */     ECPoint g = new ECPoint(gx, gy);
/* 156 */     ECParameterSpec ecSpec = new ECParameterSpec(curveSpec, g, n, 1);
/* 157 */     return ecSpec;
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
/*     */   public static ECPoint multiplyByGenerator(BigInteger x, ECParameterSpec spec) throws GeneralSecurityException {
/* 173 */     if (!isNistEcParameterSpec(spec)) {
/* 174 */       throw new GeneralSecurityException("spec must be NIST P256, P384 or P521");
/*     */     }
/* 176 */     if (x.signum() != 1) {
/* 177 */       throw new GeneralSecurityException("k must be positive");
/*     */     }
/* 179 */     if (x.compareTo(spec.getOrder()) >= 0) {
/* 180 */       throw new GeneralSecurityException("k must be smaller than the order of the generator");
/*     */     }
/* 182 */     EllipticCurve curve = spec.getCurve();
/* 183 */     ECPoint generator = spec.getGenerator();
/* 184 */     checkPointOnCurve(generator, curve);
/* 185 */     BigInteger a = spec.getCurve().getA();
/* 186 */     BigInteger modulus = getModulus(curve);
/*     */     
/* 188 */     JacobianEcPoint r0 = toJacobianEcPoint(ECPoint.POINT_INFINITY, modulus);
/* 189 */     JacobianEcPoint r1 = toJacobianEcPoint(generator, modulus);
/* 190 */     for (int i = x.bitLength(); i >= 0; i--) {
/* 191 */       if (x.testBit(i)) {
/* 192 */         r0 = addJacobianPoints(r0, r1, a, modulus);
/* 193 */         r1 = doubleJacobianPoint(r1, a, modulus);
/*     */       } else {
/* 195 */         r1 = addJacobianPoints(r0, r1, a, modulus);
/* 196 */         r0 = doubleJacobianPoint(r0, a, modulus);
/*     */       } 
/*     */     } 
/* 199 */     ECPoint output = r0.toECPoint(modulus);
/* 200 */     checkPointOnCurve(output, curve);
/* 201 */     return output;
/*     */   }
/*     */   
/* 204 */   private static final BigInteger TWO = BigInteger.valueOf(2L);
/* 205 */   private static final BigInteger THREE = BigInteger.valueOf(3L);
/* 206 */   private static final BigInteger FOUR = BigInteger.valueOf(4L);
/* 207 */   private static final BigInteger EIGHT = BigInteger.valueOf(8L);
/*     */ 
/*     */   
/*     */   static class JacobianEcPoint
/*     */   {
/*     */     BigInteger x;
/*     */     
/*     */     BigInteger y;
/*     */     
/*     */     BigInteger z;
/*     */ 
/*     */     
/*     */     JacobianEcPoint(BigInteger x, BigInteger y, BigInteger z) {
/* 220 */       this.x = x;
/* 221 */       this.y = y;
/* 222 */       this.z = z;
/*     */     }
/*     */     
/*     */     boolean isInfinity() {
/* 226 */       return this.z.equals(BigInteger.ZERO);
/*     */     }
/*     */     
/*     */     ECPoint toECPoint(BigInteger modulus) {
/* 230 */       if (isInfinity()) {
/* 231 */         return ECPoint.POINT_INFINITY;
/*     */       }
/* 233 */       BigInteger zInv = this.z.modInverse(modulus);
/* 234 */       BigInteger zInv2 = zInv.multiply(zInv).mod(modulus);
/* 235 */       return new ECPoint(this.x
/* 236 */           .multiply(zInv2).mod(modulus), this.y
/* 237 */           .multiply(zInv2).mod(modulus).multiply(zInv).mod(modulus));
/*     */     }
/*     */     
/* 240 */     static final JacobianEcPoint INFINITY = new JacobianEcPoint(BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);
/*     */   }
/*     */ 
/*     */   
/*     */   static JacobianEcPoint toJacobianEcPoint(ECPoint p, BigInteger modulus) {
/* 245 */     if (p.equals(ECPoint.POINT_INFINITY)) {
/* 246 */       return JacobianEcPoint.INFINITY;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     BigInteger z = (new BigInteger(1, Random.randBytes((modulus.bitLength() + 8) / 8))).mod(modulus);
/* 255 */     BigInteger zz = z.multiply(z).mod(modulus);
/* 256 */     BigInteger zzz = zz.multiply(z).mod(modulus);
/* 257 */     return new JacobianEcPoint(p
/* 258 */         .getAffineX().multiply(zz).mod(modulus), p.getAffineY().multiply(zzz).mod(modulus), z);
/*     */   }
/*     */ 
/*     */   
/*     */   static JacobianEcPoint doubleJacobianPoint(JacobianEcPoint p, BigInteger a, BigInteger modulus) {
/* 263 */     if (p.y.equals(BigInteger.ZERO)) {
/* 264 */       return JacobianEcPoint.INFINITY;
/*     */     }
/* 266 */     BigInteger xx = p.x.multiply(p.x).mod(modulus);
/* 267 */     BigInteger yy = p.y.multiply(p.y).mod(modulus);
/* 268 */     BigInteger yyyy = yy.multiply(yy).mod(modulus);
/* 269 */     BigInteger zz = p.z.multiply(p.z).mod(modulus);
/* 270 */     BigInteger x1yy = p.x.add(yy);
/* 271 */     BigInteger s = x1yy.multiply(x1yy).mod(modulus).subtract(xx).subtract(yyyy).multiply(TWO);
/* 272 */     BigInteger m = xx.multiply(THREE).add(a.multiply(zz).multiply(zz).mod(modulus));
/* 273 */     BigInteger t = m.multiply(m).mod(modulus).subtract(s.multiply(TWO)).mod(modulus);
/* 274 */     BigInteger x3 = t;
/*     */     
/* 276 */     BigInteger y3 = m.multiply(s.subtract(t)).mod(modulus).subtract(yyyy.multiply(EIGHT)).mod(modulus);
/* 277 */     BigInteger y1z1 = p.y.add(p.z);
/* 278 */     BigInteger z3 = y1z1.multiply(y1z1).mod(modulus).subtract(yy).subtract(zz).mod(modulus);
/* 279 */     return new JacobianEcPoint(x3, y3, z3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static JacobianEcPoint addJacobianPoints(JacobianEcPoint p1, JacobianEcPoint p2, BigInteger a, BigInteger modulus) {
/* 286 */     if (p1.isInfinity()) {
/* 287 */       return p2;
/*     */     }
/* 289 */     if (p2.isInfinity()) {
/* 290 */       return p1;
/*     */     }
/* 292 */     BigInteger z1z1 = p1.z.multiply(p1.z).mod(modulus);
/* 293 */     BigInteger z2z2 = p2.z.multiply(p2.z).mod(modulus);
/* 294 */     BigInteger u1 = p1.x.multiply(z2z2).mod(modulus);
/* 295 */     BigInteger u2 = p2.x.multiply(z1z1).mod(modulus);
/* 296 */     BigInteger s1 = p1.y.multiply(p2.z).mod(modulus).multiply(z2z2).mod(modulus);
/* 297 */     BigInteger s2 = p2.y.multiply(p1.z).mod(modulus).multiply(z1z1).mod(modulus);
/* 298 */     if (u1.equals(u2)) {
/* 299 */       if (!s1.equals(s2)) {
/* 300 */         return JacobianEcPoint.INFINITY;
/*     */       }
/* 302 */       return doubleJacobianPoint(p1, a, modulus);
/*     */     } 
/*     */     
/* 305 */     BigInteger h = u2.subtract(u1).mod(modulus);
/* 306 */     BigInteger i = h.multiply(FOUR).multiply(h).mod(modulus);
/* 307 */     BigInteger j = h.multiply(i).mod(modulus);
/* 308 */     BigInteger r = s2.subtract(s1).multiply(TWO).mod(modulus);
/* 309 */     BigInteger v = u1.multiply(i).mod(modulus);
/* 310 */     BigInteger x3 = r.multiply(r).mod(modulus).subtract(j).subtract(v.multiply(TWO)).mod(modulus);
/* 311 */     BigInteger y3 = r.multiply(v.subtract(x3)).subtract(s1.multiply(TWO).multiply(j)).mod(modulus);
/* 312 */     BigInteger z12 = p1.z.add(p2.z);
/*     */     
/* 314 */     BigInteger z3 = z12.multiply(z12).mod(modulus).subtract(z1z1).subtract(z2z2).multiply(h).mod(modulus);
/* 315 */     return new JacobianEcPoint(x3, y3, z3);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\EllipticCurvesUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */