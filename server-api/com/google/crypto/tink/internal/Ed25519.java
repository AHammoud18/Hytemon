/*      */ package com.google.crypto.tink.internal;
/*      */ 
/*      */ import com.google.crypto.tink.subtle.Bytes;
/*      */ import com.google.crypto.tink.subtle.EngineFactory;
/*      */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.MessageDigest;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Ed25519
/*      */ {
/*      */   public static final int SECRET_KEY_LEN = 32;
/*      */   public static final int PUBLIC_KEY_LEN = 32;
/*      */   public static final int SIGNATURE_LEN = 64;
/*   47 */   private static final CachedXYT CACHED_NEUTRAL = new CachedXYT(new long[] { 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L }, new long[] { 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L }, new long[] { 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L });
/*      */ 
/*      */ 
/*      */   
/*   51 */   private static final PartialXYZT NEUTRAL = new PartialXYZT(new XYZ(new long[] { 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L }, new long[] { 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L }, new long[] { 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L }), new long[] { 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class XYZ
/*      */   {
/*      */     final long[] x;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final long[] y;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final long[] z;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     XYZ() {
/*   76 */       this(new long[10], new long[10], new long[10]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     XYZ(long[] x, long[] y, long[] z) {
/*   83 */       this.x = x;
/*   84 */       this.y = y;
/*   85 */       this.z = z;
/*      */     }
/*      */     
/*      */     XYZ(XYZ xyz) {
/*   89 */       this.x = Arrays.copyOf(xyz.x, 10);
/*   90 */       this.y = Arrays.copyOf(xyz.y, 10);
/*   91 */       this.z = Arrays.copyOf(xyz.z, 10);
/*      */     }
/*      */     
/*      */     XYZ(Ed25519.PartialXYZT partialXYZT) {
/*   95 */       this();
/*   96 */       fromPartialXYZT(this, partialXYZT);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     static XYZ fromPartialXYZT(XYZ out, Ed25519.PartialXYZT in) {
/*  104 */       Field25519.mult(out.x, in.xyz.x, in.t);
/*  105 */       Field25519.mult(out.y, in.xyz.y, in.xyz.z);
/*  106 */       Field25519.mult(out.z, in.xyz.z, in.t);
/*  107 */       return out;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     byte[] toBytes() {
/*  114 */       long[] recip = new long[10];
/*  115 */       long[] x = new long[10];
/*  116 */       long[] y = new long[10];
/*  117 */       Field25519.inverse(recip, this.z);
/*  118 */       Field25519.mult(x, this.x, recip);
/*  119 */       Field25519.mult(y, this.y, recip);
/*  120 */       byte[] s = Field25519.contract(y);
/*  121 */       s[31] = (byte)(s[31] ^ Ed25519.getLsb(x) << 7);
/*  122 */       return s;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isOnCurve() {
/*  127 */       long[] x2 = new long[10];
/*  128 */       Field25519.square(x2, this.x);
/*  129 */       long[] y2 = new long[10];
/*  130 */       Field25519.square(y2, this.y);
/*  131 */       long[] z2 = new long[10];
/*  132 */       Field25519.square(z2, this.z);
/*  133 */       long[] z4 = new long[10];
/*  134 */       Field25519.square(z4, z2);
/*  135 */       long[] lhs = new long[10];
/*      */       
/*  137 */       Field25519.sub(lhs, y2, x2);
/*      */       
/*  139 */       Field25519.mult(lhs, lhs, z2);
/*  140 */       long[] rhs = new long[10];
/*      */       
/*  142 */       Field25519.mult(rhs, x2, y2);
/*      */       
/*  144 */       Field25519.mult(rhs, rhs, Ed25519Constants.D);
/*      */       
/*  146 */       Field25519.sum(rhs, z4);
/*      */ 
/*      */       
/*  149 */       Field25519.reduce(rhs, rhs);
/*      */       
/*  151 */       return Bytes.equal(Field25519.contract(lhs), Field25519.contract(rhs));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class XYZT
/*      */   {
/*      */     final Ed25519.XYZ xyz;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final long[] t;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     XYZT() {
/*  173 */       this(new Ed25519.XYZ(), new long[10]);
/*      */     }
/*      */     
/*      */     XYZT(Ed25519.XYZ xyz, long[] t) {
/*  177 */       this.xyz = xyz;
/*  178 */       this.t = t;
/*      */     }
/*      */     
/*      */     XYZT(Ed25519.PartialXYZT partialXYZT) {
/*  182 */       this();
/*  183 */       fromPartialXYZT(this, partialXYZT);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     private static XYZT fromPartialXYZT(XYZT out, Ed25519.PartialXYZT in) {
/*  191 */       Field25519.mult(out.xyz.x, in.xyz.x, in.t);
/*  192 */       Field25519.mult(out.xyz.y, in.xyz.y, in.xyz.z);
/*  193 */       Field25519.mult(out.xyz.z, in.xyz.z, in.t);
/*  194 */       Field25519.mult(out.t, in.xyz.x, in.xyz.y);
/*  195 */       return out;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static XYZT fromBytesNegateVarTime(byte[] s) throws GeneralSecurityException {
/*  203 */       long[] x = new long[10];
/*  204 */       long[] y = Field25519.expand(s);
/*  205 */       long[] z = new long[10];
/*  206 */       z[0] = 1L;
/*  207 */       long[] t = new long[10];
/*  208 */       long[] u = new long[10];
/*  209 */       long[] v = new long[10];
/*  210 */       long[] vxx = new long[10];
/*  211 */       long[] check = new long[10];
/*  212 */       Field25519.square(u, y);
/*  213 */       Field25519.mult(v, u, Ed25519Constants.D);
/*  214 */       Field25519.sub(u, u, z);
/*  215 */       Field25519.sum(v, v, z);
/*      */       
/*  217 */       long[] v3 = new long[10];
/*  218 */       Field25519.square(v3, v);
/*  219 */       Field25519.mult(v3, v3, v);
/*  220 */       Field25519.square(x, v3);
/*  221 */       Field25519.mult(x, x, v);
/*  222 */       Field25519.mult(x, x, u);
/*      */       
/*  224 */       Ed25519.pow2252m3(x, x);
/*  225 */       Field25519.mult(x, x, v3);
/*  226 */       Field25519.mult(x, x, u);
/*      */       
/*  228 */       Field25519.square(vxx, x);
/*  229 */       Field25519.mult(vxx, vxx, v);
/*  230 */       Field25519.sub(check, vxx, u);
/*  231 */       if (Ed25519.isNonZeroVarTime(check)) {
/*  232 */         Field25519.sum(check, vxx, u);
/*  233 */         if (Ed25519.isNonZeroVarTime(check)) {
/*  234 */           throw new GeneralSecurityException("Cannot convert given bytes to extended projective coordinates. No square root exists for modulo 2^255-19");
/*      */         }
/*      */         
/*  237 */         Field25519.mult(x, x, Ed25519Constants.SQRTM1);
/*      */       } 
/*      */       
/*  240 */       if (!Ed25519.isNonZeroVarTime(x) && (s[31] & 0xFF) >> 7 != 0) {
/*  241 */         throw new GeneralSecurityException("Cannot convert given bytes to extended projective coordinates. Computed x is zero and encoded x's least significant bit is not zero");
/*      */       }
/*      */       
/*  244 */       if (Ed25519.getLsb(x) == (s[31] & 0xFF) >> 7) {
/*  245 */         Ed25519.neg(x, x);
/*      */       }
/*      */       
/*  248 */       Field25519.mult(t, x, y);
/*  249 */       return new XYZT(new Ed25519.XYZ(x, y, z), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PartialXYZT
/*      */   {
/*      */     final Ed25519.XYZ xyz;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final long[] t;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     PartialXYZT() {
/*  274 */       this(new Ed25519.XYZ(), new long[10]);
/*      */     }
/*      */     
/*      */     PartialXYZT(Ed25519.XYZ xyz, long[] t) {
/*  278 */       this.xyz = xyz;
/*  279 */       this.t = t;
/*      */     }
/*      */     
/*      */     PartialXYZT(PartialXYZT other) {
/*  283 */       this.xyz = new Ed25519.XYZ(other.xyz);
/*  284 */       this.t = Arrays.copyOf(other.t, 10);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CachedXYT
/*      */   {
/*      */     final long[] yPlusX;
/*      */     
/*      */     final long[] yMinusX;
/*      */     
/*      */     final long[] t2d;
/*      */ 
/*      */     
/*      */     CachedXYT() {
/*  300 */       this(new long[10], new long[10], new long[10]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CachedXYT(long[] yPlusX, long[] yMinusX, long[] t2d) {
/*  314 */       this.yPlusX = yPlusX;
/*  315 */       this.yMinusX = yMinusX;
/*  316 */       this.t2d = t2d;
/*      */     }
/*      */     
/*      */     CachedXYT(CachedXYT other) {
/*  320 */       this.yPlusX = Arrays.copyOf(other.yPlusX, 10);
/*  321 */       this.yMinusX = Arrays.copyOf(other.yMinusX, 10);
/*  322 */       this.t2d = Arrays.copyOf(other.t2d, 10);
/*      */     }
/*      */ 
/*      */     
/*      */     void multByZ(long[] output, long[] in) {
/*  327 */       System.arraycopy(in, 0, output, 0, 10);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void copyConditional(CachedXYT other, int icopy) {
/*  334 */       Curve25519.copyConditional(this.yPlusX, other.yPlusX, icopy);
/*  335 */       Curve25519.copyConditional(this.yMinusX, other.yMinusX, icopy);
/*  336 */       Curve25519.copyConditional(this.t2d, other.t2d, icopy);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class CachedXYZT
/*      */     extends CachedXYT {
/*      */     private final long[] z;
/*      */     
/*      */     CachedXYZT() {
/*  345 */       this(new long[10], new long[10], new long[10], new long[10]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CachedXYZT(Ed25519.XYZT xyzt) {
/*  356 */       this();
/*  357 */       Field25519.sum(this.yPlusX, xyzt.xyz.y, xyzt.xyz.x);
/*  358 */       Field25519.sub(this.yMinusX, xyzt.xyz.y, xyzt.xyz.x);
/*  359 */       System.arraycopy(xyzt.xyz.z, 0, this.z, 0, 10);
/*  360 */       Field25519.mult(this.t2d, xyzt.t, Ed25519Constants.D2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CachedXYZT(long[] yPlusX, long[] yMinusX, long[] z, long[] t2d) {
/*  372 */       super(yPlusX, yMinusX, t2d);
/*  373 */       this.z = z;
/*      */     }
/*      */ 
/*      */     
/*      */     public void multByZ(long[] output, long[] in) {
/*  378 */       Field25519.mult(output, in, this.z);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void add(PartialXYZT partialXYZT, XYZT extended, CachedXYT cached) {
/*  393 */     long[] t = new long[10];
/*      */ 
/*      */     
/*  396 */     Field25519.sum(partialXYZT.xyz.x, extended.xyz.y, extended.xyz.x);
/*      */ 
/*      */     
/*  399 */     Field25519.sub(partialXYZT.xyz.y, extended.xyz.y, extended.xyz.x);
/*      */ 
/*      */     
/*  402 */     Field25519.mult(partialXYZT.xyz.y, partialXYZT.xyz.y, cached.yMinusX);
/*      */ 
/*      */     
/*  405 */     Field25519.mult(partialXYZT.xyz.z, partialXYZT.xyz.x, cached.yPlusX);
/*      */ 
/*      */     
/*  408 */     Field25519.mult(partialXYZT.t, extended.t, cached.t2d);
/*      */ 
/*      */     
/*  411 */     cached.multByZ(partialXYZT.xyz.x, extended.xyz.z);
/*      */ 
/*      */     
/*  414 */     Field25519.sum(t, partialXYZT.xyz.x, partialXYZT.xyz.x);
/*      */ 
/*      */     
/*  417 */     Field25519.sub(partialXYZT.xyz.x, partialXYZT.xyz.z, partialXYZT.xyz.y);
/*      */ 
/*      */     
/*  420 */     Field25519.sum(partialXYZT.xyz.y, partialXYZT.xyz.z, partialXYZT.xyz.y);
/*      */ 
/*      */     
/*  423 */     Field25519.sum(partialXYZT.xyz.z, t, partialXYZT.t);
/*      */ 
/*      */     
/*  426 */     Field25519.sub(partialXYZT.t, t, partialXYZT.t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void sub(PartialXYZT partialXYZT, XYZT extended, CachedXYT cached) {
/*  440 */     long[] t = new long[10];
/*      */ 
/*      */     
/*  443 */     Field25519.sum(partialXYZT.xyz.x, extended.xyz.y, extended.xyz.x);
/*      */ 
/*      */     
/*  446 */     Field25519.sub(partialXYZT.xyz.y, extended.xyz.y, extended.xyz.x);
/*      */ 
/*      */     
/*  449 */     Field25519.mult(partialXYZT.xyz.y, partialXYZT.xyz.y, cached.yPlusX);
/*      */ 
/*      */     
/*  452 */     Field25519.mult(partialXYZT.xyz.z, partialXYZT.xyz.x, cached.yMinusX);
/*      */ 
/*      */     
/*  455 */     Field25519.mult(partialXYZT.t, extended.t, cached.t2d);
/*      */ 
/*      */     
/*  458 */     cached.multByZ(partialXYZT.xyz.x, extended.xyz.z);
/*      */ 
/*      */     
/*  461 */     Field25519.sum(t, partialXYZT.xyz.x, partialXYZT.xyz.x);
/*      */ 
/*      */     
/*  464 */     Field25519.sub(partialXYZT.xyz.x, partialXYZT.xyz.z, partialXYZT.xyz.y);
/*      */ 
/*      */     
/*  467 */     Field25519.sum(partialXYZT.xyz.y, partialXYZT.xyz.z, partialXYZT.xyz.y);
/*      */ 
/*      */     
/*  470 */     Field25519.sub(partialXYZT.xyz.z, t, partialXYZT.t);
/*      */ 
/*      */     
/*  473 */     Field25519.sum(partialXYZT.t, t, partialXYZT.t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void doubleXYZ(PartialXYZT partialXYZT, XYZ p) {
/*  487 */     long[] t0 = new long[10];
/*      */ 
/*      */     
/*  490 */     Field25519.square(partialXYZT.xyz.x, p.x);
/*      */ 
/*      */     
/*  493 */     Field25519.square(partialXYZT.xyz.z, p.y);
/*      */ 
/*      */     
/*  496 */     Field25519.square(partialXYZT.t, p.z);
/*      */ 
/*      */     
/*  499 */     Field25519.sum(partialXYZT.t, partialXYZT.t, partialXYZT.t);
/*      */ 
/*      */     
/*  502 */     Field25519.sum(partialXYZT.xyz.y, p.x, p.y);
/*      */ 
/*      */     
/*  505 */     Field25519.square(t0, partialXYZT.xyz.y);
/*      */ 
/*      */     
/*  508 */     Field25519.sum(partialXYZT.xyz.y, partialXYZT.xyz.z, partialXYZT.xyz.x);
/*      */ 
/*      */     
/*  511 */     Field25519.sub(partialXYZT.xyz.z, partialXYZT.xyz.z, partialXYZT.xyz.x);
/*      */ 
/*      */     
/*  514 */     Field25519.sub(partialXYZT.xyz.x, t0, partialXYZT.xyz.y);
/*      */ 
/*      */     
/*  517 */     Field25519.sub(partialXYZT.t, partialXYZT.t, partialXYZT.xyz.z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void doubleXYZT(PartialXYZT partialXYZT, XYZT p) {
/*  524 */     doubleXYZ(partialXYZT, p.xyz);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int eq(int a, int b) {
/*  534 */     int r = (a ^ b ^ 0xFFFFFFFF) & 0xFF;
/*  535 */     r &= r << 4;
/*  536 */     r &= r << 2;
/*  537 */     r &= r << 1;
/*  538 */     return r >> 7 & 0x1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void select(CachedXYT t, int pos, byte b) {
/*  555 */     int bnegative = (b & 0xFF) >> 7;
/*  556 */     int babs = b - ((-bnegative & b) << 1);
/*      */     
/*  558 */     t.copyConditional(Ed25519Constants.B_TABLE[pos][0], eq(babs, 1));
/*  559 */     t.copyConditional(Ed25519Constants.B_TABLE[pos][1], eq(babs, 2));
/*  560 */     t.copyConditional(Ed25519Constants.B_TABLE[pos][2], eq(babs, 3));
/*  561 */     t.copyConditional(Ed25519Constants.B_TABLE[pos][3], eq(babs, 4));
/*  562 */     t.copyConditional(Ed25519Constants.B_TABLE[pos][4], eq(babs, 5));
/*  563 */     t.copyConditional(Ed25519Constants.B_TABLE[pos][5], eq(babs, 6));
/*  564 */     t.copyConditional(Ed25519Constants.B_TABLE[pos][6], eq(babs, 7));
/*  565 */     t.copyConditional(Ed25519Constants.B_TABLE[pos][7], eq(babs, 8));
/*      */     
/*  567 */     long[] yPlusX = Arrays.copyOf(t.yMinusX, 10);
/*  568 */     long[] yMinusX = Arrays.copyOf(t.yPlusX, 10);
/*  569 */     long[] t2d = Arrays.copyOf(t.t2d, 10);
/*  570 */     neg(t2d, t2d);
/*  571 */     CachedXYT minust = new CachedXYT(yPlusX, yMinusX, t2d);
/*  572 */     t.copyConditional(minust, bnegative);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XYZ scalarMultWithBase(byte[] a) {
/*  586 */     byte[] e = new byte[64];
/*  587 */     for (int i = 0; i < 32; i++) {
/*  588 */       e[2 * i + 0] = (byte)((a[i] & 0xFF) >> 0 & 0xF);
/*  589 */       e[2 * i + 1] = (byte)((a[i] & 0xFF) >> 4 & 0xF);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  597 */     int carry = 0;
/*  598 */     for (int j = 0; j < e.length - 1; j++) {
/*  599 */       e[j] = (byte)(e[j] + carry);
/*  600 */       carry = e[j] + 8;
/*  601 */       carry >>= 4;
/*  602 */       e[j] = (byte)(e[j] - (carry << 4));
/*      */     } 
/*  604 */     e[e.length - 1] = (byte)(e[e.length - 1] + carry);
/*      */     
/*  606 */     PartialXYZT ret = new PartialXYZT(NEUTRAL);
/*  607 */     XYZT xyzt = new XYZT();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  612 */     for (int k = 1; k < e.length; k += 2) {
/*  613 */       CachedXYT t = new CachedXYT(CACHED_NEUTRAL);
/*  614 */       select(t, k / 2, e[k]);
/*  615 */       add(ret, XYZT.fromPartialXYZT(xyzt, ret), t);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  620 */     XYZ xyz = new XYZ();
/*  621 */     doubleXYZ(ret, XYZ.fromPartialXYZT(xyz, ret));
/*  622 */     doubleXYZ(ret, XYZ.fromPartialXYZT(xyz, ret));
/*  623 */     doubleXYZ(ret, XYZ.fromPartialXYZT(xyz, ret));
/*  624 */     doubleXYZ(ret, XYZ.fromPartialXYZT(xyz, ret));
/*      */ 
/*      */     
/*  627 */     for (int m = 0; m < e.length; m += 2) {
/*  628 */       CachedXYT t = new CachedXYT(CACHED_NEUTRAL);
/*  629 */       select(t, m / 2, e[m]);
/*  630 */       add(ret, XYZT.fromPartialXYZT(xyzt, ret), t);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  635 */     XYZ result = new XYZ(ret);
/*  636 */     if (!result.isOnCurve()) {
/*  637 */       throw new IllegalStateException("arithmetic error in scalar multiplication");
/*      */     }
/*  639 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] scalarMultWithBaseToBytes(byte[] a) {
/*  651 */     return scalarMultWithBase(a).toBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   private static byte[] slide(byte[] a) {
/*  656 */     byte[] r = new byte[256];
/*      */     
/*      */     int i;
/*      */     
/*  660 */     for (i = 0; i < 256; i++) {
/*  661 */       r[i] = (byte)(0x1 & (a[i >> 3] & 0xFF) >> (i & 0x7));
/*      */     }
/*      */ 
/*      */     
/*  665 */     for (i = 0; i < 256; i++) {
/*  666 */       if (r[i] != 0) {
/*  667 */         for (int b = 1; b <= 6 && i + b < 256; b++) {
/*  668 */           if (r[i + b] != 0) {
/*  669 */             if (r[i] + (r[i + b] << b) <= 15) {
/*  670 */               r[i] = (byte)(r[i] + (r[i + b] << b));
/*  671 */               r[i + b] = 0;
/*  672 */             } else if (r[i] - (r[i + b] << b) >= -15) {
/*  673 */               r[i] = (byte)(r[i] - (r[i + b] << b));
/*  674 */               for (int k = i + b; k < 256; k++) {
/*  675 */                 if (r[k] == 0) {
/*  676 */                   r[k] = 1;
/*      */                   break;
/*      */                 } 
/*  679 */                 r[k] = 0;
/*      */               } 
/*      */             } else {
/*      */               break;
/*      */             } 
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*  688 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XYZ doubleScalarMultVarTime(byte[] a, XYZT pointA, byte[] b) {
/*  702 */     CachedXYZT[] pointAArray = new CachedXYZT[8];
/*  703 */     pointAArray[0] = new CachedXYZT(pointA);
/*  704 */     PartialXYZT t = new PartialXYZT();
/*  705 */     doubleXYZT(t, pointA);
/*  706 */     XYZT doubleA = new XYZT(t);
/*  707 */     for (int i = 1; i < pointAArray.length; i++) {
/*  708 */       add(t, doubleA, pointAArray[i - 1]);
/*  709 */       pointAArray[i] = new CachedXYZT(new XYZT(t));
/*      */     } 
/*      */     
/*  712 */     byte[] aSlide = slide(a);
/*  713 */     byte[] bSlide = slide(b);
/*  714 */     t = new PartialXYZT(NEUTRAL);
/*  715 */     XYZT u = new XYZT();
/*  716 */     int j = 255;
/*  717 */     for (; j >= 0 && 
/*  718 */       aSlide[j] == 0 && bSlide[j] == 0; j--);
/*      */ 
/*      */ 
/*      */     
/*  722 */     for (; j >= 0; j--) {
/*  723 */       doubleXYZ(t, new XYZ(t));
/*  724 */       if (aSlide[j] > 0) {
/*  725 */         add(t, XYZT.fromPartialXYZT(u, t), pointAArray[aSlide[j] / 2]);
/*  726 */       } else if (aSlide[j] < 0) {
/*  727 */         sub(t, XYZT.fromPartialXYZT(u, t), pointAArray[-aSlide[j] / 2]);
/*      */       } 
/*  729 */       if (bSlide[j] > 0) {
/*  730 */         add(t, XYZT.fromPartialXYZT(u, t), Ed25519Constants.B2[bSlide[j] / 2]);
/*  731 */       } else if (bSlide[j] < 0) {
/*  732 */         sub(t, XYZT.fromPartialXYZT(u, t), Ed25519Constants.B2[-bSlide[j] / 2]);
/*      */       } 
/*      */     } 
/*      */     
/*  736 */     return new XYZ(t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNonZeroVarTime(long[] in) {
/*  745 */     long[] inCopy = new long[in.length + 1];
/*  746 */     System.arraycopy(in, 0, inCopy, 0, in.length);
/*  747 */     Field25519.reduceCoefficients(inCopy);
/*  748 */     byte[] bytes = Field25519.contract(inCopy);
/*  749 */     for (byte b : bytes) {
/*  750 */       if (b != 0) {
/*  751 */         return true;
/*      */       }
/*      */     } 
/*  754 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getLsb(long[] in) {
/*  761 */     return Field25519.contract(in)[0] & 0x1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void neg(long[] out, long[] in) {
/*  768 */     for (int i = 0; i < in.length; i++) {
/*  769 */       out[i] = -in[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void pow2252m3(long[] out, long[] in) {
/*  777 */     long[] t0 = new long[10];
/*  778 */     long[] t1 = new long[10];
/*  779 */     long[] t2 = new long[10];
/*      */ 
/*      */     
/*  782 */     Field25519.square(t0, in);
/*      */ 
/*      */     
/*  785 */     Field25519.square(t1, t0); int i;
/*  786 */     for (i = 1; i < 2; i++) {
/*  787 */       Field25519.square(t1, t1);
/*      */     }
/*      */ 
/*      */     
/*  791 */     Field25519.mult(t1, in, t1);
/*      */ 
/*      */     
/*  794 */     Field25519.mult(t0, t0, t1);
/*      */ 
/*      */     
/*  797 */     Field25519.square(t0, t0);
/*      */ 
/*      */     
/*  800 */     Field25519.mult(t0, t1, t0);
/*      */ 
/*      */     
/*  803 */     Field25519.square(t1, t0);
/*  804 */     for (i = 1; i < 5; i++) {
/*  805 */       Field25519.square(t1, t1);
/*      */     }
/*      */ 
/*      */     
/*  809 */     Field25519.mult(t0, t1, t0);
/*      */ 
/*      */     
/*  812 */     Field25519.square(t1, t0);
/*  813 */     for (i = 1; i < 10; i++) {
/*  814 */       Field25519.square(t1, t1);
/*      */     }
/*      */ 
/*      */     
/*  818 */     Field25519.mult(t1, t1, t0);
/*      */ 
/*      */     
/*  821 */     Field25519.square(t2, t1);
/*  822 */     for (i = 1; i < 20; i++) {
/*  823 */       Field25519.square(t2, t2);
/*      */     }
/*      */ 
/*      */     
/*  827 */     Field25519.mult(t1, t2, t1);
/*      */ 
/*      */     
/*  830 */     Field25519.square(t1, t1);
/*  831 */     for (i = 1; i < 10; i++) {
/*  832 */       Field25519.square(t1, t1);
/*      */     }
/*      */ 
/*      */     
/*  836 */     Field25519.mult(t0, t1, t0);
/*      */ 
/*      */     
/*  839 */     Field25519.square(t1, t0);
/*  840 */     for (i = 1; i < 50; i++) {
/*  841 */       Field25519.square(t1, t1);
/*      */     }
/*      */ 
/*      */     
/*  845 */     Field25519.mult(t1, t1, t0);
/*      */ 
/*      */     
/*  848 */     Field25519.square(t2, t1);
/*  849 */     for (i = 1; i < 100; i++) {
/*  850 */       Field25519.square(t2, t2);
/*      */     }
/*      */ 
/*      */     
/*  854 */     Field25519.mult(t1, t2, t1);
/*      */ 
/*      */     
/*  857 */     Field25519.square(t1, t1);
/*  858 */     for (i = 1; i < 50; i++) {
/*  859 */       Field25519.square(t1, t1);
/*      */     }
/*      */ 
/*      */     
/*  863 */     Field25519.mult(t0, t1, t0);
/*      */ 
/*      */     
/*  866 */     Field25519.square(t0, t0);
/*  867 */     for (i = 1; i < 2; i++) {
/*  868 */       Field25519.square(t0, t0);
/*      */     }
/*      */ 
/*      */     
/*  872 */     Field25519.mult(out, t0, in);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long load3(byte[] in, int idx) {
/*  880 */     long result = in[idx] & 0xFFL;
/*  881 */     result |= (in[idx + 1] & 0xFF) << 8L;
/*  882 */     result |= (in[idx + 2] & 0xFF) << 16L;
/*  883 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long load4(byte[] in, int idx) {
/*  890 */     long result = load3(in, idx);
/*  891 */     result |= (in[idx + 3] & 0xFF) << 24L;
/*  892 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void reduce(byte[] s) {
/*  912 */     long s0 = 0x1FFFFFL & load3(s, 0);
/*  913 */     long s1 = 0x1FFFFFL & load4(s, 2) >> 5L;
/*  914 */     long s2 = 0x1FFFFFL & load3(s, 5) >> 2L;
/*  915 */     long s3 = 0x1FFFFFL & load4(s, 7) >> 7L;
/*  916 */     long s4 = 0x1FFFFFL & load4(s, 10) >> 4L;
/*  917 */     long s5 = 0x1FFFFFL & load3(s, 13) >> 1L;
/*  918 */     long s6 = 0x1FFFFFL & load4(s, 15) >> 6L;
/*  919 */     long s7 = 0x1FFFFFL & load3(s, 18) >> 3L;
/*  920 */     long s8 = 0x1FFFFFL & load3(s, 21);
/*  921 */     long s9 = 0x1FFFFFL & load4(s, 23) >> 5L;
/*  922 */     long s10 = 0x1FFFFFL & load3(s, 26) >> 2L;
/*  923 */     long s11 = 0x1FFFFFL & load4(s, 28) >> 7L;
/*  924 */     long s12 = 0x1FFFFFL & load4(s, 31) >> 4L;
/*  925 */     long s13 = 0x1FFFFFL & load3(s, 34) >> 1L;
/*  926 */     long s14 = 0x1FFFFFL & load4(s, 36) >> 6L;
/*  927 */     long s15 = 0x1FFFFFL & load3(s, 39) >> 3L;
/*  928 */     long s16 = 0x1FFFFFL & load3(s, 42);
/*  929 */     long s17 = 0x1FFFFFL & load4(s, 44) >> 5L;
/*  930 */     long s18 = 0x1FFFFFL & load3(s, 47) >> 2L;
/*  931 */     long s19 = 0x1FFFFFL & load4(s, 49) >> 7L;
/*  932 */     long s20 = 0x1FFFFFL & load4(s, 52) >> 4L;
/*  933 */     long s21 = 0x1FFFFFL & load3(s, 55) >> 1L;
/*  934 */     long s22 = 0x1FFFFFL & load4(s, 57) >> 6L;
/*  935 */     long s23 = load4(s, 60) >> 3L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  958 */     s11 += s23 * 666643L;
/*  959 */     s12 += s23 * 470296L;
/*  960 */     s13 += s23 * 654183L;
/*  961 */     s14 -= s23 * 997805L;
/*  962 */     s15 += s23 * 136657L;
/*  963 */     s16 -= s23 * 683901L;
/*      */ 
/*      */     
/*  966 */     s10 += s22 * 666643L;
/*  967 */     s11 += s22 * 470296L;
/*  968 */     s12 += s22 * 654183L;
/*  969 */     s13 -= s22 * 997805L;
/*  970 */     s14 += s22 * 136657L;
/*  971 */     s15 -= s22 * 683901L;
/*      */ 
/*      */     
/*  974 */     s9 += s21 * 666643L;
/*  975 */     s10 += s21 * 470296L;
/*  976 */     s11 += s21 * 654183L;
/*  977 */     s12 -= s21 * 997805L;
/*  978 */     s13 += s21 * 136657L;
/*  979 */     s14 -= s21 * 683901L;
/*      */ 
/*      */     
/*  982 */     s8 += s20 * 666643L;
/*  983 */     s9 += s20 * 470296L;
/*  984 */     s10 += s20 * 654183L;
/*  985 */     s11 -= s20 * 997805L;
/*  986 */     s12 += s20 * 136657L;
/*  987 */     s13 -= s20 * 683901L;
/*      */ 
/*      */     
/*  990 */     s7 += s19 * 666643L;
/*  991 */     s8 += s19 * 470296L;
/*  992 */     s9 += s19 * 654183L;
/*  993 */     s10 -= s19 * 997805L;
/*  994 */     s11 += s19 * 136657L;
/*  995 */     s12 -= s19 * 683901L;
/*      */ 
/*      */     
/*  998 */     s6 += s18 * 666643L;
/*  999 */     s7 += s18 * 470296L;
/* 1000 */     s8 += s18 * 654183L;
/* 1001 */     s9 -= s18 * 997805L;
/* 1002 */     s10 += s18 * 136657L;
/* 1003 */     s11 -= s18 * 683901L;
/*      */ 
/*      */ 
/*      */     
/* 1007 */     long carry6 = s6 + 1048576L >> 21L; s7 += carry6; s6 -= carry6 << 21L;
/* 1008 */     long carry8 = s8 + 1048576L >> 21L; s9 += carry8; s8 -= carry8 << 21L;
/* 1009 */     long carry10 = s10 + 1048576L >> 21L; s11 += carry10; s10 -= carry10 << 21L;
/* 1010 */     long carry12 = s12 + 1048576L >> 21L; s13 += carry12; s12 -= carry12 << 21L;
/* 1011 */     long carry14 = s14 + 1048576L >> 21L; s15 += carry14; s14 -= carry14 << 21L;
/* 1012 */     long carry16 = s16 + 1048576L >> 21L; s17 += carry16; s16 -= carry16 << 21L;
/*      */     
/* 1014 */     long carry7 = s7 + 1048576L >> 21L; s8 += carry7; s7 -= carry7 << 21L;
/* 1015 */     long carry9 = s9 + 1048576L >> 21L; s10 += carry9; s9 -= carry9 << 21L;
/* 1016 */     long carry11 = s11 + 1048576L >> 21L; s12 += carry11; s11 -= carry11 << 21L;
/* 1017 */     long carry13 = s13 + 1048576L >> 21L; s14 += carry13; s13 -= carry13 << 21L;
/* 1018 */     long carry15 = s15 + 1048576L >> 21L; s16 += carry15; s15 -= carry15 << 21L;
/*      */ 
/*      */     
/* 1021 */     s5 += s17 * 666643L;
/* 1022 */     s6 += s17 * 470296L;
/* 1023 */     s7 += s17 * 654183L;
/* 1024 */     s8 -= s17 * 997805L;
/* 1025 */     s9 += s17 * 136657L;
/* 1026 */     s10 -= s17 * 683901L;
/*      */ 
/*      */     
/* 1029 */     s4 += s16 * 666643L;
/* 1030 */     s5 += s16 * 470296L;
/* 1031 */     s6 += s16 * 654183L;
/* 1032 */     s7 -= s16 * 997805L;
/* 1033 */     s8 += s16 * 136657L;
/* 1034 */     s9 -= s16 * 683901L;
/*      */ 
/*      */     
/* 1037 */     s3 += s15 * 666643L;
/* 1038 */     s4 += s15 * 470296L;
/* 1039 */     s5 += s15 * 654183L;
/* 1040 */     s6 -= s15 * 997805L;
/* 1041 */     s7 += s15 * 136657L;
/* 1042 */     s8 -= s15 * 683901L;
/*      */ 
/*      */     
/* 1045 */     s2 += s14 * 666643L;
/* 1046 */     s3 += s14 * 470296L;
/* 1047 */     s4 += s14 * 654183L;
/* 1048 */     s5 -= s14 * 997805L;
/* 1049 */     s6 += s14 * 136657L;
/* 1050 */     s7 -= s14 * 683901L;
/*      */ 
/*      */     
/* 1053 */     s1 += s13 * 666643L;
/* 1054 */     s2 += s13 * 470296L;
/* 1055 */     s3 += s13 * 654183L;
/* 1056 */     s4 -= s13 * 997805L;
/* 1057 */     s5 += s13 * 136657L;
/* 1058 */     s6 -= s13 * 683901L;
/*      */ 
/*      */     
/* 1061 */     s0 += s12 * 666643L;
/* 1062 */     s1 += s12 * 470296L;
/* 1063 */     s2 += s12 * 654183L;
/* 1064 */     s3 -= s12 * 997805L;
/* 1065 */     s4 += s12 * 136657L;
/* 1066 */     s5 -= s12 * 683901L;
/* 1067 */     s12 = 0L;
/*      */ 
/*      */     
/* 1070 */     long carry0 = s0 + 1048576L >> 21L; s1 += carry0; s0 -= carry0 << 21L;
/* 1071 */     long carry2 = s2 + 1048576L >> 21L; s3 += carry2; s2 -= carry2 << 21L;
/* 1072 */     long carry4 = s4 + 1048576L >> 21L; s5 += carry4; s4 -= carry4 << 21L;
/* 1073 */     carry6 = s6 + 1048576L >> 21L; s7 += carry6; s6 -= carry6 << 21L;
/* 1074 */     carry8 = s8 + 1048576L >> 21L; s9 += carry8; s8 -= carry8 << 21L;
/* 1075 */     carry10 = s10 + 1048576L >> 21L; s11 += carry10; s10 -= carry10 << 21L;
/*      */     
/* 1077 */     long carry1 = s1 + 1048576L >> 21L; s2 += carry1; s1 -= carry1 << 21L;
/* 1078 */     long carry3 = s3 + 1048576L >> 21L; s4 += carry3; s3 -= carry3 << 21L;
/* 1079 */     long carry5 = s5 + 1048576L >> 21L; s6 += carry5; s5 -= carry5 << 21L;
/* 1080 */     carry7 = s7 + 1048576L >> 21L; s8 += carry7; s7 -= carry7 << 21L;
/* 1081 */     carry9 = s9 + 1048576L >> 21L; s10 += carry9; s9 -= carry9 << 21L;
/* 1082 */     carry11 = s11 + 1048576L >> 21L; s12 += carry11; s11 -= carry11 << 21L;
/*      */     
/* 1084 */     s0 += s12 * 666643L;
/* 1085 */     s1 += s12 * 470296L;
/* 1086 */     s2 += s12 * 654183L;
/* 1087 */     s3 -= s12 * 997805L;
/* 1088 */     s4 += s12 * 136657L;
/* 1089 */     s5 -= s12 * 683901L;
/* 1090 */     s12 = 0L;
/*      */ 
/*      */     
/* 1093 */     carry0 = s0 >> 21L; s1 += carry0; s0 -= carry0 << 21L;
/* 1094 */     carry1 = s1 >> 21L; s2 += carry1; s1 -= carry1 << 21L;
/* 1095 */     carry2 = s2 >> 21L; s3 += carry2; s2 -= carry2 << 21L;
/* 1096 */     carry3 = s3 >> 21L; s4 += carry3; s3 -= carry3 << 21L;
/* 1097 */     carry4 = s4 >> 21L; s5 += carry4; s4 -= carry4 << 21L;
/* 1098 */     carry5 = s5 >> 21L; s6 += carry5; s5 -= carry5 << 21L;
/* 1099 */     carry6 = s6 >> 21L; s7 += carry6; s6 -= carry6 << 21L;
/* 1100 */     carry7 = s7 >> 21L; s8 += carry7; s7 -= carry7 << 21L;
/* 1101 */     carry8 = s8 >> 21L; s9 += carry8; s8 -= carry8 << 21L;
/* 1102 */     carry9 = s9 >> 21L; s10 += carry9; s9 -= carry9 << 21L;
/* 1103 */     carry10 = s10 >> 21L; s11 += carry10; s10 -= carry10 << 21L;
/* 1104 */     carry11 = s11 >> 21L; s12 += carry11; s11 -= carry11 << 21L;
/*      */ 
/*      */     
/* 1107 */     s0 += s12 * 666643L;
/* 1108 */     s1 += s12 * 470296L;
/* 1109 */     s2 += s12 * 654183L;
/* 1110 */     s3 -= s12 * 997805L;
/* 1111 */     s4 += s12 * 136657L;
/* 1112 */     s5 -= s12 * 683901L;
/*      */ 
/*      */     
/* 1115 */     carry0 = s0 >> 21L; s1 += carry0; s0 -= carry0 << 21L;
/* 1116 */     carry1 = s1 >> 21L; s2 += carry1; s1 -= carry1 << 21L;
/* 1117 */     carry2 = s2 >> 21L; s3 += carry2; s2 -= carry2 << 21L;
/* 1118 */     carry3 = s3 >> 21L; s4 += carry3; s3 -= carry3 << 21L;
/* 1119 */     carry4 = s4 >> 21L; s5 += carry4; s4 -= carry4 << 21L;
/* 1120 */     carry5 = s5 >> 21L; s6 += carry5; s5 -= carry5 << 21L;
/* 1121 */     carry6 = s6 >> 21L; s7 += carry6; s6 -= carry6 << 21L;
/* 1122 */     carry7 = s7 >> 21L; s8 += carry7; s7 -= carry7 << 21L;
/* 1123 */     carry8 = s8 >> 21L; s9 += carry8; s8 -= carry8 << 21L;
/* 1124 */     carry9 = s9 >> 21L; s10 += carry9; s9 -= carry9 << 21L;
/* 1125 */     carry10 = s10 >> 21L; s11 += carry10; s10 -= carry10 << 21L;
/*      */ 
/*      */     
/* 1128 */     s[0] = (byte)(int)s0;
/* 1129 */     s[1] = (byte)(int)(s0 >> 8L);
/* 1130 */     s[2] = (byte)(int)(s0 >> 16L | s1 << 5L);
/* 1131 */     s[3] = (byte)(int)(s1 >> 3L);
/* 1132 */     s[4] = (byte)(int)(s1 >> 11L);
/* 1133 */     s[5] = (byte)(int)(s1 >> 19L | s2 << 2L);
/* 1134 */     s[6] = (byte)(int)(s2 >> 6L);
/* 1135 */     s[7] = (byte)(int)(s2 >> 14L | s3 << 7L);
/* 1136 */     s[8] = (byte)(int)(s3 >> 1L);
/* 1137 */     s[9] = (byte)(int)(s3 >> 9L);
/* 1138 */     s[10] = (byte)(int)(s3 >> 17L | s4 << 4L);
/* 1139 */     s[11] = (byte)(int)(s4 >> 4L);
/* 1140 */     s[12] = (byte)(int)(s4 >> 12L);
/* 1141 */     s[13] = (byte)(int)(s4 >> 20L | s5 << 1L);
/* 1142 */     s[14] = (byte)(int)(s5 >> 7L);
/* 1143 */     s[15] = (byte)(int)(s5 >> 15L | s6 << 6L);
/* 1144 */     s[16] = (byte)(int)(s6 >> 2L);
/* 1145 */     s[17] = (byte)(int)(s6 >> 10L);
/* 1146 */     s[18] = (byte)(int)(s6 >> 18L | s7 << 3L);
/* 1147 */     s[19] = (byte)(int)(s7 >> 5L);
/* 1148 */     s[20] = (byte)(int)(s7 >> 13L);
/* 1149 */     s[21] = (byte)(int)s8;
/* 1150 */     s[22] = (byte)(int)(s8 >> 8L);
/* 1151 */     s[23] = (byte)(int)(s8 >> 16L | s9 << 5L);
/* 1152 */     s[24] = (byte)(int)(s9 >> 3L);
/* 1153 */     s[25] = (byte)(int)(s9 >> 11L);
/* 1154 */     s[26] = (byte)(int)(s9 >> 19L | s10 << 2L);
/* 1155 */     s[27] = (byte)(int)(s10 >> 6L);
/* 1156 */     s[28] = (byte)(int)(s10 >> 14L | s11 << 7L);
/* 1157 */     s[29] = (byte)(int)(s11 >> 1L);
/* 1158 */     s[30] = (byte)(int)(s11 >> 9L);
/* 1159 */     s[31] = (byte)(int)(s11 >> 17L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void mulAdd(byte[] s, byte[] a, byte[] b, byte[] c) {
/* 1175 */     long a0 = 0x1FFFFFL & load3(a, 0);
/* 1176 */     long a1 = 0x1FFFFFL & load4(a, 2) >> 5L;
/* 1177 */     long a2 = 0x1FFFFFL & load3(a, 5) >> 2L;
/* 1178 */     long a3 = 0x1FFFFFL & load4(a, 7) >> 7L;
/* 1179 */     long a4 = 0x1FFFFFL & load4(a, 10) >> 4L;
/* 1180 */     long a5 = 0x1FFFFFL & load3(a, 13) >> 1L;
/* 1181 */     long a6 = 0x1FFFFFL & load4(a, 15) >> 6L;
/* 1182 */     long a7 = 0x1FFFFFL & load3(a, 18) >> 3L;
/* 1183 */     long a8 = 0x1FFFFFL & load3(a, 21);
/* 1184 */     long a9 = 0x1FFFFFL & load4(a, 23) >> 5L;
/* 1185 */     long a10 = 0x1FFFFFL & load3(a, 26) >> 2L;
/* 1186 */     long a11 = load4(a, 28) >> 7L;
/* 1187 */     long b0 = 0x1FFFFFL & load3(b, 0);
/* 1188 */     long b1 = 0x1FFFFFL & load4(b, 2) >> 5L;
/* 1189 */     long b2 = 0x1FFFFFL & load3(b, 5) >> 2L;
/* 1190 */     long b3 = 0x1FFFFFL & load4(b, 7) >> 7L;
/* 1191 */     long b4 = 0x1FFFFFL & load4(b, 10) >> 4L;
/* 1192 */     long b5 = 0x1FFFFFL & load3(b, 13) >> 1L;
/* 1193 */     long b6 = 0x1FFFFFL & load4(b, 15) >> 6L;
/* 1194 */     long b7 = 0x1FFFFFL & load3(b, 18) >> 3L;
/* 1195 */     long b8 = 0x1FFFFFL & load3(b, 21);
/* 1196 */     long b9 = 0x1FFFFFL & load4(b, 23) >> 5L;
/* 1197 */     long b10 = 0x1FFFFFL & load3(b, 26) >> 2L;
/* 1198 */     long b11 = load4(b, 28) >> 7L;
/* 1199 */     long c0 = 0x1FFFFFL & load3(c, 0);
/* 1200 */     long c1 = 0x1FFFFFL & load4(c, 2) >> 5L;
/* 1201 */     long c2 = 0x1FFFFFL & load3(c, 5) >> 2L;
/* 1202 */     long c3 = 0x1FFFFFL & load4(c, 7) >> 7L;
/* 1203 */     long c4 = 0x1FFFFFL & load4(c, 10) >> 4L;
/* 1204 */     long c5 = 0x1FFFFFL & load3(c, 13) >> 1L;
/* 1205 */     long c6 = 0x1FFFFFL & load4(c, 15) >> 6L;
/* 1206 */     long c7 = 0x1FFFFFL & load3(c, 18) >> 3L;
/* 1207 */     long c8 = 0x1FFFFFL & load3(c, 21);
/* 1208 */     long c9 = 0x1FFFFFL & load4(c, 23) >> 5L;
/* 1209 */     long c10 = 0x1FFFFFL & load3(c, 26) >> 2L;
/* 1210 */     long c11 = load4(c, 28) >> 7L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1259 */     long s0 = c0 + a0 * b0;
/* 1260 */     long s1 = c1 + a0 * b1 + a1 * b0;
/* 1261 */     long s2 = c2 + a0 * b2 + a1 * b1 + a2 * b0;
/* 1262 */     long s3 = c3 + a0 * b3 + a1 * b2 + a2 * b1 + a3 * b0;
/* 1263 */     long s4 = c4 + a0 * b4 + a1 * b3 + a2 * b2 + a3 * b1 + a4 * b0;
/* 1264 */     long s5 = c5 + a0 * b5 + a1 * b4 + a2 * b3 + a3 * b2 + a4 * b1 + a5 * b0;
/* 1265 */     long s6 = c6 + a0 * b6 + a1 * b5 + a2 * b4 + a3 * b3 + a4 * b2 + a5 * b1 + a6 * b0;
/* 1266 */     long s7 = c7 + a0 * b7 + a1 * b6 + a2 * b5 + a3 * b4 + a4 * b3 + a5 * b2 + a6 * b1 + a7 * b0;
/* 1267 */     long s8 = c8 + a0 * b8 + a1 * b7 + a2 * b6 + a3 * b5 + a4 * b4 + a5 * b3 + a6 * b2 + a7 * b1 + a8 * b0;
/*      */     
/* 1269 */     long s9 = c9 + a0 * b9 + a1 * b8 + a2 * b7 + a3 * b6 + a4 * b5 + a5 * b4 + a6 * b3 + a7 * b2 + a8 * b1 + a9 * b0;
/*      */     
/* 1271 */     long s10 = c10 + a0 * b10 + a1 * b9 + a2 * b8 + a3 * b7 + a4 * b6 + a5 * b5 + a6 * b4 + a7 * b3 + a8 * b2 + a9 * b1 + a10 * b0;
/*      */     
/* 1273 */     long s11 = c11 + a0 * b11 + a1 * b10 + a2 * b9 + a3 * b8 + a4 * b7 + a5 * b6 + a6 * b5 + a7 * b4 + a8 * b3 + a9 * b2 + a10 * b1 + a11 * b0;
/*      */     
/* 1275 */     long s12 = a1 * b11 + a2 * b10 + a3 * b9 + a4 * b8 + a5 * b7 + a6 * b6 + a7 * b5 + a8 * b4 + a9 * b3 + a10 * b2 + a11 * b1;
/*      */     
/* 1277 */     long s13 = a2 * b11 + a3 * b10 + a4 * b9 + a5 * b8 + a6 * b7 + a7 * b6 + a8 * b5 + a9 * b4 + a10 * b3 + a11 * b2;
/*      */     
/* 1279 */     long s14 = a3 * b11 + a4 * b10 + a5 * b9 + a6 * b8 + a7 * b7 + a8 * b6 + a9 * b5 + a10 * b4 + a11 * b3;
/*      */     
/* 1281 */     long s15 = a4 * b11 + a5 * b10 + a6 * b9 + a7 * b8 + a8 * b7 + a9 * b6 + a10 * b5 + a11 * b4;
/* 1282 */     long s16 = a5 * b11 + a6 * b10 + a7 * b9 + a8 * b8 + a9 * b7 + a10 * b6 + a11 * b5;
/* 1283 */     long s17 = a6 * b11 + a7 * b10 + a8 * b9 + a9 * b8 + a10 * b7 + a11 * b6;
/* 1284 */     long s18 = a7 * b11 + a8 * b10 + a9 * b9 + a10 * b8 + a11 * b7;
/* 1285 */     long s19 = a8 * b11 + a9 * b10 + a10 * b9 + a11 * b8;
/* 1286 */     long s20 = a9 * b11 + a10 * b10 + a11 * b9;
/* 1287 */     long s21 = a10 * b11 + a11 * b10;
/* 1288 */     long s22 = a11 * b11;
/* 1289 */     long s23 = 0L;
/*      */     
/* 1291 */     long carry0 = s0 + 1048576L >> 21L; s1 += carry0; s0 -= carry0 << 21L;
/* 1292 */     long carry2 = s2 + 1048576L >> 21L; s3 += carry2; s2 -= carry2 << 21L;
/* 1293 */     long carry4 = s4 + 1048576L >> 21L; s5 += carry4; s4 -= carry4 << 21L;
/* 1294 */     long carry6 = s6 + 1048576L >> 21L; s7 += carry6; s6 -= carry6 << 21L;
/* 1295 */     long carry8 = s8 + 1048576L >> 21L; s9 += carry8; s8 -= carry8 << 21L;
/* 1296 */     long carry10 = s10 + 1048576L >> 21L; s11 += carry10; s10 -= carry10 << 21L;
/* 1297 */     long carry12 = s12 + 1048576L >> 21L; s13 += carry12; s12 -= carry12 << 21L;
/* 1298 */     long carry14 = s14 + 1048576L >> 21L; s15 += carry14; s14 -= carry14 << 21L;
/* 1299 */     long carry16 = s16 + 1048576L >> 21L; s17 += carry16; s16 -= carry16 << 21L;
/* 1300 */     long carry18 = s18 + 1048576L >> 21L; s19 += carry18; s18 -= carry18 << 21L;
/* 1301 */     long carry20 = s20 + 1048576L >> 21L; s21 += carry20; s20 -= carry20 << 21L;
/* 1302 */     long carry22 = s22 + 1048576L >> 21L; s23 += carry22; s22 -= carry22 << 21L;
/*      */     
/* 1304 */     long carry1 = s1 + 1048576L >> 21L; s2 += carry1; s1 -= carry1 << 21L;
/* 1305 */     long carry3 = s3 + 1048576L >> 21L; s4 += carry3; s3 -= carry3 << 21L;
/* 1306 */     long carry5 = s5 + 1048576L >> 21L; s6 += carry5; s5 -= carry5 << 21L;
/* 1307 */     long carry7 = s7 + 1048576L >> 21L; s8 += carry7; s7 -= carry7 << 21L;
/* 1308 */     long carry9 = s9 + 1048576L >> 21L; s10 += carry9; s9 -= carry9 << 21L;
/* 1309 */     long carry11 = s11 + 1048576L >> 21L; s12 += carry11; s11 -= carry11 << 21L;
/* 1310 */     long carry13 = s13 + 1048576L >> 21L; s14 += carry13; s13 -= carry13 << 21L;
/* 1311 */     long carry15 = s15 + 1048576L >> 21L; s16 += carry15; s15 -= carry15 << 21L;
/* 1312 */     long carry17 = s17 + 1048576L >> 21L; s18 += carry17; s17 -= carry17 << 21L;
/* 1313 */     long carry19 = s19 + 1048576L >> 21L; s20 += carry19; s19 -= carry19 << 21L;
/* 1314 */     long carry21 = s21 + 1048576L >> 21L; s22 += carry21; s21 -= carry21 << 21L;
/*      */     
/* 1316 */     s11 += s23 * 666643L;
/* 1317 */     s12 += s23 * 470296L;
/* 1318 */     s13 += s23 * 654183L;
/* 1319 */     s14 -= s23 * 997805L;
/* 1320 */     s15 += s23 * 136657L;
/* 1321 */     s16 -= s23 * 683901L;
/*      */ 
/*      */     
/* 1324 */     s10 += s22 * 666643L;
/* 1325 */     s11 += s22 * 470296L;
/* 1326 */     s12 += s22 * 654183L;
/* 1327 */     s13 -= s22 * 997805L;
/* 1328 */     s14 += s22 * 136657L;
/* 1329 */     s15 -= s22 * 683901L;
/*      */ 
/*      */     
/* 1332 */     s9 += s21 * 666643L;
/* 1333 */     s10 += s21 * 470296L;
/* 1334 */     s11 += s21 * 654183L;
/* 1335 */     s12 -= s21 * 997805L;
/* 1336 */     s13 += s21 * 136657L;
/* 1337 */     s14 -= s21 * 683901L;
/*      */ 
/*      */     
/* 1340 */     s8 += s20 * 666643L;
/* 1341 */     s9 += s20 * 470296L;
/* 1342 */     s10 += s20 * 654183L;
/* 1343 */     s11 -= s20 * 997805L;
/* 1344 */     s12 += s20 * 136657L;
/* 1345 */     s13 -= s20 * 683901L;
/*      */ 
/*      */     
/* 1348 */     s7 += s19 * 666643L;
/* 1349 */     s8 += s19 * 470296L;
/* 1350 */     s9 += s19 * 654183L;
/* 1351 */     s10 -= s19 * 997805L;
/* 1352 */     s11 += s19 * 136657L;
/* 1353 */     s12 -= s19 * 683901L;
/*      */ 
/*      */     
/* 1356 */     s6 += s18 * 666643L;
/* 1357 */     s7 += s18 * 470296L;
/* 1358 */     s8 += s18 * 654183L;
/* 1359 */     s9 -= s18 * 997805L;
/* 1360 */     s10 += s18 * 136657L;
/* 1361 */     s11 -= s18 * 683901L;
/*      */ 
/*      */     
/* 1364 */     carry6 = s6 + 1048576L >> 21L; s7 += carry6; s6 -= carry6 << 21L;
/* 1365 */     carry8 = s8 + 1048576L >> 21L; s9 += carry8; s8 -= carry8 << 21L;
/* 1366 */     carry10 = s10 + 1048576L >> 21L; s11 += carry10; s10 -= carry10 << 21L;
/* 1367 */     carry12 = s12 + 1048576L >> 21L; s13 += carry12; s12 -= carry12 << 21L;
/* 1368 */     carry14 = s14 + 1048576L >> 21L; s15 += carry14; s14 -= carry14 << 21L;
/* 1369 */     carry16 = s16 + 1048576L >> 21L; s17 += carry16; s16 -= carry16 << 21L;
/*      */     
/* 1371 */     carry7 = s7 + 1048576L >> 21L; s8 += carry7; s7 -= carry7 << 21L;
/* 1372 */     carry9 = s9 + 1048576L >> 21L; s10 += carry9; s9 -= carry9 << 21L;
/* 1373 */     carry11 = s11 + 1048576L >> 21L; s12 += carry11; s11 -= carry11 << 21L;
/* 1374 */     carry13 = s13 + 1048576L >> 21L; s14 += carry13; s13 -= carry13 << 21L;
/* 1375 */     carry15 = s15 + 1048576L >> 21L; s16 += carry15; s15 -= carry15 << 21L;
/*      */     
/* 1377 */     s5 += s17 * 666643L;
/* 1378 */     s6 += s17 * 470296L;
/* 1379 */     s7 += s17 * 654183L;
/* 1380 */     s8 -= s17 * 997805L;
/* 1381 */     s9 += s17 * 136657L;
/* 1382 */     s10 -= s17 * 683901L;
/*      */ 
/*      */     
/* 1385 */     s4 += s16 * 666643L;
/* 1386 */     s5 += s16 * 470296L;
/* 1387 */     s6 += s16 * 654183L;
/* 1388 */     s7 -= s16 * 997805L;
/* 1389 */     s8 += s16 * 136657L;
/* 1390 */     s9 -= s16 * 683901L;
/*      */ 
/*      */     
/* 1393 */     s3 += s15 * 666643L;
/* 1394 */     s4 += s15 * 470296L;
/* 1395 */     s5 += s15 * 654183L;
/* 1396 */     s6 -= s15 * 997805L;
/* 1397 */     s7 += s15 * 136657L;
/* 1398 */     s8 -= s15 * 683901L;
/*      */ 
/*      */     
/* 1401 */     s2 += s14 * 666643L;
/* 1402 */     s3 += s14 * 470296L;
/* 1403 */     s4 += s14 * 654183L;
/* 1404 */     s5 -= s14 * 997805L;
/* 1405 */     s6 += s14 * 136657L;
/* 1406 */     s7 -= s14 * 683901L;
/*      */ 
/*      */     
/* 1409 */     s1 += s13 * 666643L;
/* 1410 */     s2 += s13 * 470296L;
/* 1411 */     s3 += s13 * 654183L;
/* 1412 */     s4 -= s13 * 997805L;
/* 1413 */     s5 += s13 * 136657L;
/* 1414 */     s6 -= s13 * 683901L;
/*      */ 
/*      */     
/* 1417 */     s0 += s12 * 666643L;
/* 1418 */     s1 += s12 * 470296L;
/* 1419 */     s2 += s12 * 654183L;
/* 1420 */     s3 -= s12 * 997805L;
/* 1421 */     s4 += s12 * 136657L;
/* 1422 */     s5 -= s12 * 683901L;
/* 1423 */     s12 = 0L;
/*      */     
/* 1425 */     carry0 = s0 + 1048576L >> 21L; s1 += carry0; s0 -= carry0 << 21L;
/* 1426 */     carry2 = s2 + 1048576L >> 21L; s3 += carry2; s2 -= carry2 << 21L;
/* 1427 */     carry4 = s4 + 1048576L >> 21L; s5 += carry4; s4 -= carry4 << 21L;
/* 1428 */     carry6 = s6 + 1048576L >> 21L; s7 += carry6; s6 -= carry6 << 21L;
/* 1429 */     carry8 = s8 + 1048576L >> 21L; s9 += carry8; s8 -= carry8 << 21L;
/* 1430 */     carry10 = s10 + 1048576L >> 21L; s11 += carry10; s10 -= carry10 << 21L;
/*      */     
/* 1432 */     carry1 = s1 + 1048576L >> 21L; s2 += carry1; s1 -= carry1 << 21L;
/* 1433 */     carry3 = s3 + 1048576L >> 21L; s4 += carry3; s3 -= carry3 << 21L;
/* 1434 */     carry5 = s5 + 1048576L >> 21L; s6 += carry5; s5 -= carry5 << 21L;
/* 1435 */     carry7 = s7 + 1048576L >> 21L; s8 += carry7; s7 -= carry7 << 21L;
/* 1436 */     carry9 = s9 + 1048576L >> 21L; s10 += carry9; s9 -= carry9 << 21L;
/* 1437 */     carry11 = s11 + 1048576L >> 21L; s12 += carry11; s11 -= carry11 << 21L;
/*      */     
/* 1439 */     s0 += s12 * 666643L;
/* 1440 */     s1 += s12 * 470296L;
/* 1441 */     s2 += s12 * 654183L;
/* 1442 */     s3 -= s12 * 997805L;
/* 1443 */     s4 += s12 * 136657L;
/* 1444 */     s5 -= s12 * 683901L;
/* 1445 */     s12 = 0L;
/*      */     
/* 1447 */     carry0 = s0 >> 21L; s1 += carry0; s0 -= carry0 << 21L;
/* 1448 */     carry1 = s1 >> 21L; s2 += carry1; s1 -= carry1 << 21L;
/* 1449 */     carry2 = s2 >> 21L; s3 += carry2; s2 -= carry2 << 21L;
/* 1450 */     carry3 = s3 >> 21L; s4 += carry3; s3 -= carry3 << 21L;
/* 1451 */     carry4 = s4 >> 21L; s5 += carry4; s4 -= carry4 << 21L;
/* 1452 */     carry5 = s5 >> 21L; s6 += carry5; s5 -= carry5 << 21L;
/* 1453 */     carry6 = s6 >> 21L; s7 += carry6; s6 -= carry6 << 21L;
/* 1454 */     carry7 = s7 >> 21L; s8 += carry7; s7 -= carry7 << 21L;
/* 1455 */     carry8 = s8 >> 21L; s9 += carry8; s8 -= carry8 << 21L;
/* 1456 */     carry9 = s9 >> 21L; s10 += carry9; s9 -= carry9 << 21L;
/* 1457 */     carry10 = s10 >> 21L; s11 += carry10; s10 -= carry10 << 21L;
/* 1458 */     carry11 = s11 >> 21L; s12 += carry11; s11 -= carry11 << 21L;
/*      */     
/* 1460 */     s0 += s12 * 666643L;
/* 1461 */     s1 += s12 * 470296L;
/* 1462 */     s2 += s12 * 654183L;
/* 1463 */     s3 -= s12 * 997805L;
/* 1464 */     s4 += s12 * 136657L;
/* 1465 */     s5 -= s12 * 683901L;
/*      */ 
/*      */     
/* 1468 */     carry0 = s0 >> 21L; s1 += carry0; s0 -= carry0 << 21L;
/* 1469 */     carry1 = s1 >> 21L; s2 += carry1; s1 -= carry1 << 21L;
/* 1470 */     carry2 = s2 >> 21L; s3 += carry2; s2 -= carry2 << 21L;
/* 1471 */     carry3 = s3 >> 21L; s4 += carry3; s3 -= carry3 << 21L;
/* 1472 */     carry4 = s4 >> 21L; s5 += carry4; s4 -= carry4 << 21L;
/* 1473 */     carry5 = s5 >> 21L; s6 += carry5; s5 -= carry5 << 21L;
/* 1474 */     carry6 = s6 >> 21L; s7 += carry6; s6 -= carry6 << 21L;
/* 1475 */     carry7 = s7 >> 21L; s8 += carry7; s7 -= carry7 << 21L;
/* 1476 */     carry8 = s8 >> 21L; s9 += carry8; s8 -= carry8 << 21L;
/* 1477 */     carry9 = s9 >> 21L; s10 += carry9; s9 -= carry9 << 21L;
/* 1478 */     carry10 = s10 >> 21L; s11 += carry10; s10 -= carry10 << 21L;
/*      */     
/* 1480 */     s[0] = (byte)(int)s0;
/* 1481 */     s[1] = (byte)(int)(s0 >> 8L);
/* 1482 */     s[2] = (byte)(int)(s0 >> 16L | s1 << 5L);
/* 1483 */     s[3] = (byte)(int)(s1 >> 3L);
/* 1484 */     s[4] = (byte)(int)(s1 >> 11L);
/* 1485 */     s[5] = (byte)(int)(s1 >> 19L | s2 << 2L);
/* 1486 */     s[6] = (byte)(int)(s2 >> 6L);
/* 1487 */     s[7] = (byte)(int)(s2 >> 14L | s3 << 7L);
/* 1488 */     s[8] = (byte)(int)(s3 >> 1L);
/* 1489 */     s[9] = (byte)(int)(s3 >> 9L);
/* 1490 */     s[10] = (byte)(int)(s3 >> 17L | s4 << 4L);
/* 1491 */     s[11] = (byte)(int)(s4 >> 4L);
/* 1492 */     s[12] = (byte)(int)(s4 >> 12L);
/* 1493 */     s[13] = (byte)(int)(s4 >> 20L | s5 << 1L);
/* 1494 */     s[14] = (byte)(int)(s5 >> 7L);
/* 1495 */     s[15] = (byte)(int)(s5 >> 15L | s6 << 6L);
/* 1496 */     s[16] = (byte)(int)(s6 >> 2L);
/* 1497 */     s[17] = (byte)(int)(s6 >> 10L);
/* 1498 */     s[18] = (byte)(int)(s6 >> 18L | s7 << 3L);
/* 1499 */     s[19] = (byte)(int)(s7 >> 5L);
/* 1500 */     s[20] = (byte)(int)(s7 >> 13L);
/* 1501 */     s[21] = (byte)(int)s8;
/* 1502 */     s[22] = (byte)(int)(s8 >> 8L);
/* 1503 */     s[23] = (byte)(int)(s8 >> 16L | s9 << 5L);
/* 1504 */     s[24] = (byte)(int)(s9 >> 3L);
/* 1505 */     s[25] = (byte)(int)(s9 >> 11L);
/* 1506 */     s[26] = (byte)(int)(s9 >> 19L | s10 << 2L);
/* 1507 */     s[27] = (byte)(int)(s10 >> 6L);
/* 1508 */     s[28] = (byte)(int)(s10 >> 14L | s11 << 7L);
/* 1509 */     s[29] = (byte)(int)(s11 >> 1L);
/* 1510 */     s[30] = (byte)(int)(s11 >> 9L);
/* 1511 */     s[31] = (byte)(int)(s11 >> 17L);
/*      */   }
/*      */   
/*      */   public static byte[] getHashedScalar(byte[] privateKey) throws GeneralSecurityException {
/* 1515 */     MessageDigest digest = (MessageDigest)EngineFactory.MESSAGE_DIGEST.getInstance("SHA-512");
/* 1516 */     digest.update(privateKey, 0, 32);
/* 1517 */     byte[] h = digest.digest();
/*      */ 
/*      */     
/* 1520 */     h[0] = (byte)(h[0] & 0xF8);
/*      */     
/* 1522 */     h[31] = (byte)(h[31] & Byte.MAX_VALUE);
/*      */     
/* 1524 */     h[31] = (byte)(h[31] | 0x40);
/* 1525 */     return h;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] sign(byte[] message, byte[] publicKey, byte[] hashedPrivateKey) throws GeneralSecurityException {
/* 1543 */     byte[] messageCopy = Arrays.copyOfRange(message, 0, message.length);
/* 1544 */     MessageDigest digest = (MessageDigest)EngineFactory.MESSAGE_DIGEST.getInstance("SHA-512");
/* 1545 */     digest.update(hashedPrivateKey, 32, 32);
/* 1546 */     digest.update(messageCopy);
/* 1547 */     byte[] r = digest.digest();
/* 1548 */     reduce(r);
/*      */     
/* 1550 */     byte[] rB = Arrays.copyOfRange(scalarMultWithBase(r).toBytes(), 0, 32);
/* 1551 */     digest.reset();
/* 1552 */     digest.update(rB);
/* 1553 */     digest.update(publicKey);
/* 1554 */     digest.update(messageCopy);
/* 1555 */     byte[] hram = digest.digest();
/* 1556 */     reduce(hram);
/* 1557 */     byte[] s = new byte[32];
/* 1558 */     mulAdd(s, hram, hashedPrivateKey, r);
/* 1559 */     return Bytes.concat(new byte[][] { rB, s });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1565 */   static final byte[] GROUP_ORDER = new byte[] { -19, -45, -11, 92, 26, 99, 18, 88, -42, -100, -9, -94, -34, -7, -34, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isSmallerThanGroupOrder(byte[] s) {
/* 1580 */     for (int j = 31; j >= 0; j--) {
/*      */       
/* 1582 */       int a = s[j] & 0xFF;
/* 1583 */       int b = GROUP_ORDER[j] & 0xFF;
/* 1584 */       if (a != b) {
/* 1585 */         return (a < b);
/*      */       }
/*      */     } 
/* 1588 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean verify(byte[] message, byte[] signature, byte[] publicKey) throws GeneralSecurityException {
/* 1600 */     if (signature.length != 64) {
/* 1601 */       return false;
/*      */     }
/* 1603 */     byte[] s = Arrays.copyOfRange(signature, 32, 64);
/* 1604 */     if (!isSmallerThanGroupOrder(s)) {
/* 1605 */       return false;
/*      */     }
/* 1607 */     MessageDigest digest = (MessageDigest)EngineFactory.MESSAGE_DIGEST.getInstance("SHA-512");
/* 1608 */     digest.update(signature, 0, 32);
/* 1609 */     digest.update(publicKey);
/* 1610 */     digest.update(message);
/* 1611 */     byte[] h = digest.digest();
/* 1612 */     reduce(h);
/*      */     
/* 1614 */     XYZT negPublicKey = XYZT.fromBytesNegateVarTime(publicKey);
/* 1615 */     XYZ xyz = doubleScalarMultVarTime(h, negPublicKey, s);
/* 1616 */     byte[] expectedR = xyz.toBytes();
/* 1617 */     for (int i = 0; i < 32; i++) {
/* 1618 */       if (expectedR[i] != signature[i]) {
/* 1619 */         return false;
/*      */       }
/*      */     } 
/* 1622 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init() {
/* 1628 */     if (Ed25519Constants.D == null)
/* 1629 */       throw new IllegalStateException("Could not initialize Ed25519."); 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\Ed25519.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */