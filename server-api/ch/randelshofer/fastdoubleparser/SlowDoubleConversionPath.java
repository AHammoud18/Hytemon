/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import ch.randelshofer.fastdoubleparser.bte.ByteDigitSet;
/*     */ import ch.randelshofer.fastdoubleparser.chr.CharDigitSet;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SlowDoubleConversionPath
/*     */ {
/*  17 */   private static final int[] powersOfTen = new int[] { 0, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };
/*     */   
/*     */   static double toDouble(CharSequence str, CharDigitSet digitSet, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, boolean isSignificandNegative, long exponentValue) {
/*  20 */     double v = toBigDecimal(str, digitSet, integerStartIndex, integerEndIndex, fractionStartIndex, fractionEndIndex, 768, exponentValue).doubleValue();
/*  21 */     return isSignificandNegative ? -v : v;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static BigDecimal toBigDecimal(CharSequence str, CharDigitSet digitSet, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, int maxRequiredDigits, long exponentValue) {
/*  27 */     for (; integerStartIndex < integerEndIndex; integerStartIndex++) {
/*  28 */       char ch = str.charAt(integerStartIndex);
/*  29 */       int digit = digitSet.toDigit(ch);
/*  30 */       boolean isDigit = (digit < 10);
/*  31 */       if (isDigit && 
/*  32 */         digit > 0) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  38 */     int skippedFractionDigits = 0;
/*  39 */     if (integerStartIndex == integerEndIndex) {
/*  40 */       for (; fractionStartIndex < fractionEndIndex; fractionStartIndex++) {
/*  41 */         char ch = str.charAt(fractionStartIndex);
/*  42 */         int digit = digitSet.toDigit(ch);
/*  43 */         if (digit > 0 && digit < 10) {
/*     */           break;
/*     */         }
/*  46 */         skippedFractionDigits++;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  52 */     int estimatedNumDigits = integerEndIndex - integerStartIndex + fractionEndIndex - fractionStartIndex;
/*  53 */     BigSignificand b = new BigSignificand(FastIntegerMath.estimateNumBits(Math.min(estimatedNumDigits, maxRequiredDigits)));
/*     */ 
/*     */     
/*  56 */     int numIntegerDigits = 0;
/*  57 */     int acc = 0;
/*     */     int i;
/*  59 */     for (i = integerStartIndex; i < integerEndIndex && numIntegerDigits < maxRequiredDigits; i++) {
/*  60 */       char ch = str.charAt(i);
/*  61 */       int digit = digitSet.toDigit(ch);
/*  62 */       if (digit < 10) {
/*  63 */         acc = acc * 10 + digit;
/*  64 */         numIntegerDigits++;
/*  65 */         if (numIntegerDigits % 8 == 0) {
/*  66 */           b.fma(100000000, acc);
/*  67 */           acc = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*  71 */     int mul = powersOfTen[numIntegerDigits % 8];
/*  72 */     if (mul != 0) b.fma(mul, acc);
/*     */ 
/*     */ 
/*     */     
/*  76 */     int skippedIntegerDigits = 0;
/*  77 */     for (; i < integerEndIndex; i++) {
/*  78 */       char ch = str.charAt(i);
/*  79 */       int digit = digitSet.toDigit(ch);
/*  80 */       if (digit < 10) {
/*  81 */         skippedIntegerDigits++;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  87 */     fractionEndIndex = Math.min(fractionEndIndex, fractionStartIndex + Math.max(maxRequiredDigits - numIntegerDigits, 0));
/*     */ 
/*     */     
/*  90 */     int numFractionDigits = 0;
/*  91 */     acc = 0;
/*  92 */     for (i = fractionStartIndex; i < fractionEndIndex; i++) {
/*  93 */       char ch = str.charAt(i);
/*  94 */       acc = acc * 10 + digitSet.toDigit(ch);
/*  95 */       numFractionDigits++;
/*  96 */       if (numFractionDigits % 8 == 0) {
/*  97 */         b.fma(100000000, acc);
/*  98 */         acc = 0;
/*     */       } 
/*     */     } 
/* 101 */     mul = powersOfTen[numFractionDigits % 8];
/* 102 */     if (mul != 0) b.fma(mul, acc);
/*     */     
/* 104 */     int exponent = (int)(exponentValue + skippedIntegerDigits - numFractionDigits - skippedFractionDigits);
/*     */     
/* 106 */     BigInteger bigInteger = b.toBigInteger();
/*     */     
/* 108 */     return new BigDecimal(bigInteger, -exponent);
/*     */   }
/*     */   
/*     */   static double toDouble(char[] str, CharDigitSet digitSet, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, boolean isSignificandNegative, long exponentValue) {
/* 112 */     double v = toBigDecimal(str, digitSet, integerStartIndex, integerEndIndex, fractionStartIndex, fractionEndIndex, 768, exponentValue).doubleValue();
/* 113 */     return isSignificandNegative ? -v : v;
/*     */   }
/*     */   
/*     */   static double toDouble(byte[] str, ByteDigitSet digitSet, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, boolean isSignificandNegative, long exponentValue) {
/* 117 */     double v = toBigDecimal(str, digitSet, integerStartIndex, integerEndIndex, fractionStartIndex, fractionEndIndex, 768, exponentValue).doubleValue();
/* 118 */     return isSignificandNegative ? -v : v;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static BigDecimal toBigDecimal(char[] str, CharDigitSet digitSet, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, int maxRequiredDigits, long exponentValue) {
/* 124 */     for (; integerStartIndex < integerEndIndex; integerStartIndex++) {
/* 125 */       char ch = str[integerStartIndex];
/* 126 */       int digit = digitSet.toDigit(ch);
/* 127 */       boolean isDigit = (digit < 10);
/* 128 */       if (isDigit && 
/* 129 */         digit > 0) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 135 */     int skippedFractionDigits = 0;
/* 136 */     if (integerStartIndex == integerEndIndex) {
/* 137 */       for (; fractionStartIndex < fractionEndIndex; fractionStartIndex++) {
/* 138 */         char ch = str[fractionStartIndex];
/* 139 */         int digit = digitSet.toDigit(ch);
/* 140 */         if (digit > 0 && digit < 10) {
/*     */           break;
/*     */         }
/* 143 */         skippedFractionDigits++;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 149 */     int estimatedNumDigits = integerEndIndex - integerStartIndex + fractionEndIndex - fractionStartIndex;
/* 150 */     BigSignificand b = new BigSignificand(FastIntegerMath.estimateNumBits(Math.min(estimatedNumDigits, maxRequiredDigits)));
/*     */ 
/*     */     
/* 153 */     int numIntegerDigits = 0;
/* 154 */     int acc = 0;
/*     */     int i;
/* 156 */     for (i = integerStartIndex; i < integerEndIndex && numIntegerDigits < maxRequiredDigits; i++) {
/* 157 */       char ch = str[i];
/* 158 */       int digit = digitSet.toDigit(ch);
/* 159 */       if (digit < 10) {
/* 160 */         acc = acc * 10 + digit;
/* 161 */         numIntegerDigits++;
/* 162 */         if (numIntegerDigits % 8 == 0) {
/* 163 */           b.fma(100000000, acc);
/* 164 */           acc = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/* 168 */     int mul = powersOfTen[numIntegerDigits % 8];
/* 169 */     if (mul != 0) b.fma(mul, acc);
/*     */ 
/*     */ 
/*     */     
/* 173 */     int skippedIntegerDigits = 0;
/* 174 */     for (; i < integerEndIndex; i++) {
/* 175 */       char ch = str[i];
/* 176 */       int digit = digitSet.toDigit(ch);
/* 177 */       if (digit < 10) {
/* 178 */         skippedIntegerDigits++;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 184 */     fractionEndIndex = Math.min(fractionEndIndex, fractionStartIndex + Math.max(maxRequiredDigits - numIntegerDigits, 0));
/*     */ 
/*     */     
/* 187 */     int numFractionDigits = 0;
/* 188 */     acc = 0;
/* 189 */     for (i = fractionStartIndex; i < fractionEndIndex; i++) {
/* 190 */       char ch = str[i];
/* 191 */       acc = acc * 10 + digitSet.toDigit(ch);
/* 192 */       numFractionDigits++;
/* 193 */       if (numFractionDigits % 8 == 0) {
/* 194 */         b.fma(100000000, acc);
/* 195 */         acc = 0;
/*     */       } 
/*     */     } 
/* 198 */     mul = powersOfTen[numFractionDigits % 8];
/* 199 */     if (mul != 0) b.fma(mul, acc);
/*     */     
/* 201 */     int exponent = (int)(exponentValue + skippedIntegerDigits - numFractionDigits - skippedFractionDigits);
/*     */     
/* 203 */     BigInteger bigInteger = b.toBigInteger();
/*     */     
/* 205 */     return new BigDecimal(bigInteger, -exponent);
/*     */   }
/*     */ 
/*     */   
/*     */   static BigDecimal toBigDecimal(byte[] str, ByteDigitSet digitSet, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, int maxRequiredDigits, long exponentValue) {
/* 210 */     for (; integerStartIndex < integerEndIndex; integerStartIndex++) {
/* 211 */       byte ch = str[integerStartIndex];
/* 212 */       int digit = digitSet.toDigit(ch);
/* 213 */       boolean isDigit = (digit < 10);
/* 214 */       if (isDigit && 
/* 215 */         digit > 0) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 221 */     int skippedFractionDigits = 0;
/* 222 */     if (integerStartIndex == integerEndIndex) {
/* 223 */       for (; fractionStartIndex < fractionEndIndex; fractionStartIndex++) {
/* 224 */         byte ch = str[fractionStartIndex];
/* 225 */         int digit = digitSet.toDigit(ch);
/* 226 */         if (digit > 0 && digit < 10) {
/*     */           break;
/*     */         }
/* 229 */         skippedFractionDigits++;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 235 */     int estimatedNumDigits = integerEndIndex - integerStartIndex + fractionEndIndex - fractionStartIndex;
/* 236 */     BigSignificand b = new BigSignificand(FastIntegerMath.estimateNumBits(Math.min(estimatedNumDigits, maxRequiredDigits)));
/*     */ 
/*     */     
/* 239 */     int numIntegerDigits = 0;
/* 240 */     int acc = 0;
/*     */     int i;
/* 242 */     for (i = integerStartIndex; i < integerEndIndex && numIntegerDigits < maxRequiredDigits; i++) {
/* 243 */       byte ch = str[i];
/* 244 */       int digit = digitSet.toDigit(ch);
/* 245 */       if (digit < 10) {
/* 246 */         acc = acc * 10 + digit;
/* 247 */         numIntegerDigits++;
/* 248 */         if (numIntegerDigits % 8 == 0) {
/* 249 */           b.fma(100000000, acc);
/* 250 */           acc = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/* 254 */     int mul = powersOfTen[numIntegerDigits % 8];
/* 255 */     if (mul != 0) b.fma(mul, acc);
/*     */ 
/*     */ 
/*     */     
/* 259 */     int skippedIntegerDigits = 0;
/* 260 */     for (; i < integerEndIndex; i++) {
/* 261 */       byte ch = str[i];
/* 262 */       int digit = digitSet.toDigit(ch);
/* 263 */       if (digit < 10) {
/* 264 */         skippedIntegerDigits++;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 270 */     fractionEndIndex = Math.min(fractionEndIndex, fractionStartIndex + Math.max(maxRequiredDigits - numIntegerDigits, 0));
/*     */ 
/*     */     
/* 273 */     int numFractionDigits = 0;
/* 274 */     acc = 0;
/* 275 */     for (i = fractionStartIndex; i < fractionEndIndex; i++) {
/* 276 */       byte ch = str[i];
/* 277 */       acc = acc * 10 + digitSet.toDigit(ch);
/* 278 */       numFractionDigits++;
/* 279 */       if (numFractionDigits % 8 == 0) {
/* 280 */         b.fma(100000000, acc);
/* 281 */         acc = 0;
/*     */       } 
/*     */     } 
/* 284 */     mul = powersOfTen[numFractionDigits % 8];
/* 285 */     if (mul != 0) b.fma(mul, acc);
/*     */     
/* 287 */     int exponent = (int)(exponentValue + skippedIntegerDigits - numFractionDigits - skippedFractionDigits);
/*     */     
/* 289 */     BigInteger bigInteger = b.toBigInteger();
/*     */     
/* 291 */     return new BigDecimal(bigInteger, -exponent);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\SlowDoubleConversionPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */