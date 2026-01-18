/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class FastFloatMath
/*     */ {
/*     */   private static final int FLOAT_EXPONENT_BIAS = 127;
/*     */   private static final int FLOAT_SIGNIFICAND_WIDTH = 24;
/*     */   private static final int FLOAT_MIN_EXPONENT_POWER_OF_TEN = -45;
/*     */   private static final int FLOAT_MAX_EXPONENT_POWER_OF_TEN = 38;
/*     */   private static final int FLOAT_MIN_EXPONENT_POWER_OF_TWO = -126;
/*     */   private static final int FLOAT_MAX_EXPONENT_POWER_OF_TWO = 127;
/*  35 */   private static final float[] FLOAT_POWER_OF_TEN = new float[] { 1.0F, 10.0F, 100.0F, 1000.0F, 10000.0F, 100000.0F, 1000000.0F, 1.0E7F, 1.0E8F, 1.0E9F, 1.0E10F };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float tryDecFloatToFloatTruncated(boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/*     */     float result;
/*  48 */     if (significand == 0L) {
/*  49 */       return isNegative ? -0.0F : 0.0F;
/*     */     }
/*     */ 
/*     */     
/*  53 */     if (isSignificandTruncated) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  59 */       if (-45 <= exponentOfTruncatedSignificand && exponentOfTruncatedSignificand <= 38) {
/*     */         
/*  61 */         float withoutRounding = tryDecToFloatWithFastAlgorithm(isNegative, significand, exponentOfTruncatedSignificand);
/*  62 */         float roundedUp = tryDecToFloatWithFastAlgorithm(isNegative, significand + 1L, exponentOfTruncatedSignificand);
/*  63 */         if (roundedUp == withoutRounding) {
/*  64 */           return withoutRounding;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  70 */       result = Float.NaN;
/*     */     
/*     */     }
/*  73 */     else if (-45 <= exponent && exponent <= 38) {
/*  74 */       result = tryDecToFloatWithFastAlgorithm(isNegative, significand, exponent);
/*     */     } else {
/*  76 */       result = Float.NaN;
/*     */     } 
/*  78 */     return result;
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
/*     */   static float tryHexFloatToFloatTruncated(boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/*  97 */     int power = isSignificandTruncated ? exponentOfTruncatedSignificand : exponent;
/*  98 */     if (-126 <= power && power <= 127) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       float d = (float)significand + ((significand < 0L) ? 1.8446744E19F : 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 108 */       d = fastScalb(d, power);
/* 109 */       return isNegative ? -d : d;
/*     */     } 
/* 111 */     return Float.NaN;
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
/*     */   static float tryDecToFloatWithFastAlgorithm(boolean isNegative, long significand, int power) {
/* 140 */     if (-10 <= power && power <= 10 && Long.compareUnsigned(significand, 16777215L) <= 0) {
/*     */ 
/*     */       
/* 143 */       float d = (float)significand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       if (power < 0) {
/* 152 */         d /= FLOAT_POWER_OF_TEN[-power];
/*     */       } else {
/* 154 */         d *= FLOAT_POWER_OF_TEN[power];
/*     */       } 
/* 156 */       return isNegative ? -d : d;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     long factorMantissa = FastDoubleMath.MANTISSA_64[power - -325];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     long exponent = (217706L * power >> 16L) + 127L + 64L;
/*     */     
/* 201 */     int lz = Long.numberOfLeadingZeros(significand);
/* 202 */     long shiftedSignificand = significand << lz;
/*     */ 
/*     */     
/* 205 */     long upper = FastIntegerMath.unsignedMultiplyHigh(shiftedSignificand, factorMantissa);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     long upperbit = upper >>> 63L;
/* 214 */     long mantissa = upper >>> (int)(upperbit + 38L);
/* 215 */     lz += (int)(0x1L ^ upperbit);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     if ((upper & 0x3FFFFFFFFFL) == 274877906943L || ((upper & 0x3FFFFFFFFFL) == 0L && (mantissa & 0x3L) == 1L))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 243 */       return Float.NaN;
/*     */     }
/*     */     
/* 246 */     mantissa++;
/* 247 */     mantissa >>>= 1L;
/*     */ 
/*     */     
/* 250 */     if (mantissa >= 16777216L) {
/*     */       
/* 252 */       mantissa = 8388608L;
/* 253 */       lz--;
/*     */     } 
/*     */     
/* 256 */     mantissa &= 0xFFFFFFFFFF7FFFFFL;
/*     */ 
/*     */     
/* 259 */     long real_exponent = exponent - lz;
/*     */     
/* 261 */     if (real_exponent < 1L || real_exponent > 254L) {
/* 262 */       return Float.NaN;
/*     */     }
/*     */ 
/*     */     
/* 266 */     int bits = (int)(mantissa | real_exponent << 23L | (isNegative ? 2147483648L : 0L));
/* 267 */     return Float.intBitsToFloat(bits);
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
/*     */   static float fastScalb(float number, int scaleFactor) {
/* 281 */     return number * Float.intBitsToFloat(scaleFactor + 127 << 23);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\FastFloatMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */