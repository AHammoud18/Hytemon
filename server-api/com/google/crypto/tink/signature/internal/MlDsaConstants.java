/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.signature.MlDsaParameters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MlDsaConstants
/*     */ {
/*     */   static final int ML_DSA_65_K = 6;
/*     */   static final int ML_DSA_65_L = 5;
/*     */   static final int ML_DSA_65_ETA = 4;
/*     */   static final int ML_DSA_87_K = 8;
/*     */   static final int ML_DSA_87_L = 7;
/*     */   static final int ML_DSA_87_ETA = 2;
/*     */   static final int DEGREE = 256;
/*     */   static final int D = 13;
/*     */   static final int TWO_POW_D = 8192;
/*     */   static final int TWO_POW_D_MINUS_ONE = 4096;
/*     */   static final int COMPRESSED_PK_BIT_LENGTH = 10;
/*     */   static final int RHO_LENGTH = 32;
/*     */   static final int RHO_PRIME_LENGTH = 64;
/*     */   static final int K_LENGTH = 32;
/*     */   static final int TR_LENGTH = 64;
/*     */   
/*     */   static final class Params
/*     */   {
/*     */     final byte k;
/*     */     final byte l;
/*     */     final int eta;
/*     */     final int pkLength;
/*     */     final int bitlen2Eta;
/*     */     final int skLength;
/*     */     
/*     */     Params(byte k, byte l, int eta, int pkLength, int bitlen2Eta, int skLength, MlDsaParameters.MlDsaInstance instance) {
/*  59 */       if (instance == MlDsaParameters.MlDsaInstance.ML_DSA_65) {
/*  60 */         if (k != 6 || l != 5 || eta != 4 || bitlen2Eta != 4 || pkLength != 1952 || skLength != 4032)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  66 */           throw new IllegalStateException("Wrong parameters for ML-DSA-65: (k, l, eta, bitlen2Eta) was (" + k + ", " + l + ", " + eta + ", " + bitlen2Eta + "), expected (6, 5, 4, 4)");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  77 */       else if (instance == MlDsaParameters.MlDsaInstance.ML_DSA_87) {
/*  78 */         if (k != 8 || l != 7 || eta != 2 || bitlen2Eta != 3 || pkLength != 2592 || skLength != 4896)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  84 */           throw new IllegalStateException("Wrong parameters for ML-DSA-87: (k, l, eta, bitlen2Eta) was (" + k + ", " + l + ", " + eta + ", " + bitlen2Eta + "), expected (8, 7, 2, 3)");
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/*  97 */         throw new IllegalStateException("MlDsaInstance not ML_DSA_65 nor ML_DSA_87");
/*     */       } 
/*     */       
/* 100 */       this.k = k;
/* 101 */       this.l = l;
/* 102 */       this.eta = eta;
/* 103 */       this.bitlen2Eta = bitlen2Eta;
/* 104 */       this.pkLength = pkLength;
/* 105 */       this.skLength = skLength;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\MlDsaConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */