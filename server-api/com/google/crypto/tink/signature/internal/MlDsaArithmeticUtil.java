/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
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
/*     */ final class MlDsaArithmeticUtil
/*     */ {
/*     */   static final class MatrixTq
/*     */   {
/*     */     final MlDsaArithmeticUtil.RingTq[][] matrix;
/*     */     
/*     */     MatrixTq(int k, int l) throws GeneralSecurityException {
/*  30 */       if ((k != 6 || l != 5) && (k != 8 || l != 7)) {
/*  31 */         throw new GeneralSecurityException("Wrong size of the ML-DSA matrix: k=" + k + ", l=" + l);
/*     */       }
/*  33 */       this.matrix = new MlDsaArithmeticUtil.RingTq[k][l];
/*  34 */       for (int i = 0; i < k; i++) {
/*  35 */         for (int j = 0; j < l; j++) {
/*  36 */           this.matrix[i][j] = new MlDsaArithmeticUtil.RingTq();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     MlDsaArithmeticUtil.VectorTq multiplyVector(MlDsaArithmeticUtil.VectorTq other) throws GeneralSecurityException {
/*  43 */       if ((this.matrix[0]).length != other.vector.length) {
/*  44 */         throw new GeneralSecurityException("Invalid parameters for matrix multiplication: matrix size (" + this.matrix.length + ", " + (this.matrix[0]).length + "), vector size " + other.vector.length);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  53 */       MlDsaArithmeticUtil.VectorTq result = new MlDsaArithmeticUtil.VectorTq(this.matrix.length);
/*  54 */       for (int i = 0; i < this.matrix.length; i++) {
/*  55 */         for (int j = 0; j < other.vector.length; j++) {
/*  56 */           result.vector[i] = result.vector[i].plus(this.matrix[i][j].multiply(other.vector[j]));
/*     */         }
/*     */       } 
/*  59 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class VectorTq {
/*     */     final MlDsaArithmeticUtil.RingTq[] vector;
/*     */     
/*     */     VectorTq(int l) {
/*  67 */       this.vector = new MlDsaArithmeticUtil.RingTq[l];
/*  68 */       for (int i = 0; i < l; i++) {
/*  69 */         this.vector[i] = new MlDsaArithmeticUtil.RingTq();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class RingTq
/*     */   {
/*  79 */     final MlDsaArithmeticUtil.RingZq[] vector = new MlDsaArithmeticUtil.RingZq[256]; RingTq() {
/*  80 */       for (int i = 0; i < 256; i++) {
/*  81 */         this.vector[i] = new MlDsaArithmeticUtil.RingZq(0);
/*     */       }
/*     */     }
/*     */     
/*     */     static RingTq copyFromPolynomial(MlDsaArithmeticUtil.PolyRq polynomial) {
/*  86 */       RingTq result = new RingTq();
/*  87 */       System.arraycopy(polynomial.polynomial, 0, result.vector, 0, 256);
/*  88 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     RingTq plus(RingTq other) {
/*  93 */       RingTq result = new RingTq();
/*  94 */       for (int i = 0; i < 256; i++) {
/*  95 */         result.vector[i] = this.vector[i].plus(other.vector[i]);
/*     */       }
/*  97 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     RingTq multiply(RingTq other) {
/* 102 */       RingTq result = new RingTq();
/* 103 */       for (int i = 0; i < 256; i++) {
/* 104 */         result.vector[i] = this.vector[i].multiply(other.vector[i]);
/*     */       }
/* 106 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 111 */       if (this == o) {
/* 112 */         return true;
/*     */       }
/* 114 */       if (!(o instanceof RingTq)) {
/* 115 */         return false;
/*     */       }
/* 117 */       RingTq other = (RingTq)o;
/* 118 */       return Arrays.equals((Object[])this.vector, (Object[])other.vector);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 123 */       return Arrays.hashCode((Object[])this.vector);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class VectorRqPair {
/*     */     MlDsaArithmeticUtil.VectorRq s1;
/*     */     MlDsaArithmeticUtil.VectorRq s2;
/*     */     
/*     */     VectorRqPair(int l1, int l2) {
/* 132 */       this.s1 = new MlDsaArithmeticUtil.VectorRq(l1);
/* 133 */       this.s2 = new MlDsaArithmeticUtil.VectorRq(l2);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class VectorRq {
/*     */     final MlDsaArithmeticUtil.PolyRq[] vector;
/*     */     
/*     */     VectorRq(int l) {
/* 141 */       this.vector = new MlDsaArithmeticUtil.PolyRq[l];
/* 142 */       for (int i = 0; i < l; i++)
/* 143 */         this.vector[i] = new MlDsaArithmeticUtil.PolyRq(); 
/*     */     }
/*     */   }
/*     */   
/*     */   static final class PolyRqPair
/*     */   {
/*     */     final MlDsaArithmeticUtil.PolyRq t1Bold;
/*     */     final MlDsaArithmeticUtil.PolyRq t0Bold;
/*     */     
/*     */     PolyRqPair(MlDsaArithmeticUtil.PolyRq t1Bold, MlDsaArithmeticUtil.PolyRq t0Bold) {
/* 153 */       this.t1Bold = t1Bold;
/* 154 */       this.t0Bold = t0Bold;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class PolyRq
/*     */   {
/*     */     final MlDsaArithmeticUtil.RingZq[] polynomial;
/*     */     
/*     */     static PolyRq copyFromVector(MlDsaArithmeticUtil.RingTq vector) {
/* 163 */       PolyRq result = new PolyRq();
/* 164 */       System.arraycopy(vector.vector, 0, result.polynomial, 0, 256);
/* 165 */       return result;
/*     */     }
/*     */     
/*     */     PolyRq() {
/* 169 */       this.polynomial = new MlDsaArithmeticUtil.RingZq[256];
/* 170 */       for (int i = 0; i < 256; i++) {
/* 171 */         this.polynomial[i] = new MlDsaArithmeticUtil.RingZq(0);
/*     */       }
/*     */     }
/*     */     
/*     */     PolyRq plus(PolyRq other) {
/* 176 */       PolyRq result = new PolyRq();
/* 177 */       for (int i = 0; i < 256; i++) {
/* 178 */         result.polynomial[i] = this.polynomial[i].plus(other.polynomial[i]);
/*     */       }
/* 180 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     MlDsaArithmeticUtil.PolyRqPair power2Round() {
/* 185 */       PolyRq t1Bold = new PolyRq();
/* 186 */       PolyRq t0Bold = new PolyRq();
/*     */       
/* 188 */       for (int i = 0; i < 256; i++) {
/* 189 */         MlDsaArithmeticUtil.RingZqPair result = this.polynomial[i].power2Round();
/* 190 */         t1Bold.polynomial[i] = result.r1;
/* 191 */         t0Bold.polynomial[i] = result.r0;
/*     */       } 
/* 193 */       return new MlDsaArithmeticUtil.PolyRqPair(t1Bold, t0Bold);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 198 */       if (this == o) {
/* 199 */         return true;
/*     */       }
/* 201 */       if (!(o instanceof PolyRq)) {
/* 202 */         return false;
/*     */       }
/* 204 */       PolyRq other = (PolyRq)o;
/* 205 */       return Arrays.equals((Object[])this.polynomial, (Object[])other.polynomial);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 210 */       return Arrays.hashCode((Object[])this.polynomial);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class RingZqPair {
/*     */     final MlDsaArithmeticUtil.RingZq r1;
/*     */     final MlDsaArithmeticUtil.RingZq r0;
/*     */     
/*     */     RingZqPair(int r1, int r0) {
/* 219 */       this.r1 = new MlDsaArithmeticUtil.RingZq(r1);
/* 220 */       this.r0 = new MlDsaArithmeticUtil.RingZq(r0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class RingZq
/*     */   {
/* 227 */     static final RingZq INVALID = new RingZq(-1);
/*     */     
/*     */     static final int Q = 8380417;
/*     */     final int r;
/*     */     
/*     */     RingZq(int r) {
/* 233 */       if ((r < 0 || r >= 8380417) && INVALID != null) {
/* 234 */         this.r = INVALID.r;
/*     */         return;
/*     */       } 
/* 237 */       this.r = r;
/*     */     }
/*     */     
/*     */     RingZq plus(RingZq other) {
/* 241 */       return new RingZq((this.r + other.r) % 8380417);
/*     */     }
/*     */     
/*     */     RingZq minus(RingZq other) {
/* 245 */       return new RingZq((this.r - other.r + 8380417) % 8380417);
/*     */     }
/*     */     
/*     */     RingZq multiply(RingZq other) {
/* 249 */       return new RingZq((int)(this.r * other.r % 8380417L));
/*     */     }
/*     */     
/*     */     RingZq negative() {
/* 253 */       return new RingZq((8380417 - this.r) % 8380417);
/*     */     }
/*     */ 
/*     */     
/*     */     MlDsaArithmeticUtil.RingZqPair power2Round() {
/* 258 */       int rPlus = this.r % 8380417;
/* 259 */       int rZero = ((rPlus + 4096 - 1 & 0x1FFF) - 4095 + 8380417) % 8380417;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 264 */       int rOne = (rPlus - rZero + 8380417) % 8380417 >> 13;
/* 265 */       return new MlDsaArithmeticUtil.RingZqPair(rOne, rZero);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 270 */       if (this == o) {
/* 271 */         return true;
/*     */       }
/* 273 */       if (!(o instanceof RingZq)) {
/* 274 */         return false;
/*     */       }
/* 276 */       RingZq other = (RingZq)o;
/* 277 */       return (this.r == other.r);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 282 */       return Integer.hashCode(this.r);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\MlDsaArithmeticUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */