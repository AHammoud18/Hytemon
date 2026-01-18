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
/*     */ abstract class AbstractJavaFloatingPointBitsFromCharSequence
/*     */   extends AbstractFloatValueParser
/*     */ {
/*     */   private static int skipWhitespace(CharSequence str, int index, int endIndex) {
/*  28 */     while (index < endIndex && str.charAt(index) <= ' ') {
/*  29 */       index++;
/*     */     }
/*  31 */     return index;
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
/*     */   abstract long nan();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract long negativeInfinity();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long parseDecFloatLiteral(CharSequence str, int index, int startIndex, int endIndex, boolean isNegative) {
/*     */     int i, digitCount, exponent;
/*     */     boolean isSignificandTruncated;
/*     */     int exponentOfTruncatedSignificand;
/*  72 */     long significand = 0L;
/*  73 */     int significandStartIndex = index;
/*  74 */     int integerDigitCount = -1;
/*  75 */     boolean illegal = false;
/*  76 */     char ch = Character.MIN_VALUE;
/*     */     
/*  78 */     for (; index < endIndex; index++) {
/*  79 */       ch = str.charAt(index);
/*  80 */       int digit = (char)(ch - 48);
/*  81 */       if (digit < 10) {
/*     */         
/*  83 */         significand = 10L * significand + digit;
/*  84 */       } else if (ch == '.') {
/*  85 */         i = illegal | ((integerDigitCount >= 0) ? 1 : 0);
/*  86 */         integerDigitCount = index - significandStartIndex;
/*     */       } else {
/*     */         break;
/*     */       } 
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
/* 102 */     int significandEndIndex = index;
/*     */     
/* 104 */     if (integerDigitCount < 0) {
/* 105 */       digitCount = index - significandStartIndex;
/* 106 */       integerDigitCount = digitCount;
/* 107 */       exponent = 0;
/*     */     } else {
/* 109 */       digitCount = index - significandStartIndex - 1;
/* 110 */       exponent = integerDigitCount - digitCount;
/*     */     } 
/* 112 */     i |= (digitCount == 0 && significandEndIndex > significandStartIndex) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 116 */     int expNumber = 0;
/* 117 */     if ((ch | 0x20) == 101) {
/* 118 */       ch = charAt(str, ++index, endIndex);
/* 119 */       boolean isExponentNegative = (ch == '-');
/* 120 */       if (isExponentNegative || ch == '+') {
/* 121 */         ch = charAt(str, ++index, endIndex);
/*     */       }
/* 123 */       int digit = (char)(ch - 48);
/* 124 */       i |= (digit >= 10) ? 1 : 0;
/*     */       
/*     */       while (true) {
/* 127 */         if (expNumber < 1024) {
/* 128 */           expNumber = 10 * expNumber + digit;
/*     */         }
/* 130 */         ch = charAt(str, ++index, endIndex);
/* 131 */         digit = (char)(ch - 48);
/* 132 */         if (digit >= 10) {
/* 133 */           if (isExponentNegative) {
/* 134 */             expNumber = -expNumber;
/*     */           }
/* 136 */           exponent += expNumber;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 141 */     if (i == 0 && digitCount == 0) {
/* 142 */       return parseNaNOrInfinity(str, index, endIndex, isNegative);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     if ((ch | 0x22) == 102) {
/* 149 */       index++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 154 */     index = skipWhitespace(str, index, endIndex);
/* 155 */     if (i != 0 || index < endIndex) {
/* 156 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (digitCount > 19) {
/* 164 */       int truncatedDigitCount = 0;
/* 165 */       significand = 0L;
/* 166 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 167 */         ch = str.charAt(index);
/* 168 */         int digit = (char)(ch - 48);
/* 169 */         if (digit < 10) {
/* 170 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 171 */             significand = 10L * significand + digit;
/* 172 */             truncatedDigitCount++;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/* 178 */       isSignificandTruncated = (index < significandEndIndex);
/* 179 */       exponentOfTruncatedSignificand = integerDigitCount - truncatedDigitCount + expNumber;
/*     */     } else {
/* 181 */       isSignificandTruncated = false;
/* 182 */       exponentOfTruncatedSignificand = 0;
/*     */     } 
/* 184 */     return valueOfFloatLiteral(str, startIndex, endIndex, isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
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
/*     */   public final long parseFloatingPointLiteral(CharSequence str, int offset, int length) {
/* 207 */     int endIndex = checkBounds(str.length(), offset, length);
/*     */ 
/*     */ 
/*     */     
/* 211 */     int index = skipWhitespace(str, offset, endIndex);
/* 212 */     if (index == endIndex) {
/* 213 */       return 9221120237041090561L;
/*     */     }
/* 215 */     char ch = str.charAt(index);
/*     */ 
/*     */ 
/*     */     
/* 219 */     boolean isNegative = (ch == '-');
/* 220 */     if (isNegative || ch == '+') {
/* 221 */       ch = charAt(str, ++index, endIndex);
/* 222 */       if (ch == '\000') {
/* 223 */         return 9221120237041090561L;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 229 */     boolean hasLeadingZero = (ch == '0');
/* 230 */     if (hasLeadingZero) {
/* 231 */       ch = charAt(str, ++index, endIndex);
/* 232 */       if ((ch | 0x20) == 120) {
/* 233 */         return parseHexFloatLiteral(str, index + 1, offset, endIndex, isNegative);
/*     */       }
/* 235 */       index--;
/*     */     } 
/*     */     
/* 238 */     return parseDecFloatLiteral(str, index, offset, endIndex, isNegative);
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
/*     */   private long parseHexFloatLiteral(CharSequence str, int index, int startIndex, int endIndex, boolean isNegative) {
/*     */     int digitCount, i;
/*     */     boolean isSignificandTruncated;
/* 269 */     long significand = 0L;
/* 270 */     int exponent = 0;
/* 271 */     int significandStartIndex = index;
/* 272 */     int virtualIndexOfPoint = -1;
/*     */     
/* 274 */     boolean illegal = false;
/* 275 */     char ch = Character.MIN_VALUE;
/* 276 */     for (; index < endIndex; index++) {
/* 277 */       ch = str.charAt(index);
/*     */       
/* 279 */       int hexValue = lookupHex(ch);
/* 280 */       if (hexValue >= 0) {
/* 281 */         significand = significand << 4L | hexValue;
/* 282 */       } else if (hexValue == -4) {
/* 283 */         i = illegal | ((virtualIndexOfPoint >= 0) ? 1 : 0);
/* 284 */         virtualIndexOfPoint = index;
/* 285 */         while (index < endIndex - 8) {
/* 286 */           long parsed = FastDoubleSwar.tryToParseEightHexDigits(str, index + 1);
/* 287 */           if (parsed >= 0L) {
/*     */             
/* 289 */             significand = (significand << 32L) + parsed;
/*     */             
/*     */             index += 8;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/* 298 */     int significandEndIndex = index;
/* 299 */     if (virtualIndexOfPoint < 0) {
/* 300 */       digitCount = significandEndIndex - significandStartIndex;
/* 301 */       virtualIndexOfPoint = significandEndIndex;
/*     */     } else {
/* 303 */       digitCount = significandEndIndex - significandStartIndex - 1;
/* 304 */       exponent = Math.min(virtualIndexOfPoint - index + 1, 1024) * 4;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 309 */     int expNumber = 0;
/* 310 */     boolean hasExponent = ((ch | 0x20) == 112);
/* 311 */     if (hasExponent) {
/* 312 */       ch = charAt(str, ++index, endIndex);
/* 313 */       boolean isExponentNegative = (ch == '-');
/* 314 */       if (isExponentNegative || ch == '+') {
/* 315 */         ch = charAt(str, ++index, endIndex);
/*     */       }
/* 317 */       int digit = (char)(ch - 48);
/* 318 */       i |= (digit >= 10) ? 1 : 0;
/*     */       
/*     */       while (true) {
/* 321 */         if (expNumber < 1024) {
/* 322 */           expNumber = 10 * expNumber + digit;
/*     */         }
/* 324 */         ch = charAt(str, ++index, endIndex);
/* 325 */         digit = (char)(ch - 48);
/* 326 */         if (digit >= 10) {
/* 327 */           if (isExponentNegative) {
/* 328 */             expNumber = -expNumber;
/*     */           }
/* 330 */           exponent += expNumber;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 336 */     if ((ch | 0x22) == 102) {
/* 337 */       index++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 342 */     index = skipWhitespace(str, index, endIndex);
/* 343 */     if (i != 0 || index < endIndex || digitCount == 0 || !hasExponent)
/*     */     {
/*     */       
/* 346 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     int skipCountInTruncatedDigits = 0;
/* 353 */     if (digitCount > 16) {
/* 354 */       significand = 0L;
/* 355 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 356 */         ch = str.charAt(index);
/*     */         
/* 358 */         int hexValue = lookupHex(ch);
/* 359 */         if (hexValue >= 0) {
/* 360 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 361 */             significand = significand << 4L | hexValue;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         } else {
/* 366 */           skipCountInTruncatedDigits++;
/*     */         } 
/*     */       } 
/* 369 */       isSignificandTruncated = (index < significandEndIndex);
/*     */     } else {
/* 371 */       isSignificandTruncated = false;
/*     */     } 
/*     */     
/* 374 */     return valueOfHexLiteral(str, startIndex, endIndex, isNegative, significand, exponent, isSignificandTruncated, (virtualIndexOfPoint - index + skipCountInTruncatedDigits) * 4 + expNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long parseNaNOrInfinity(CharSequence str, int index, int endIndex, boolean isNegative) {
/* 380 */     if (index < endIndex) {
/* 381 */       if (str.charAt(index) == 'N') {
/* 382 */         if (index + 2 < endIndex && str
/*     */           
/* 384 */           .charAt(index + 1) == 'a' && str
/* 385 */           .charAt(index + 2) == 'N')
/*     */         {
/* 387 */           index = skipWhitespace(str, index + 3, endIndex);
/* 388 */           if (index == endIndex) {
/* 389 */             return nan();
/*     */           }
/*     */         }
/*     */       
/* 393 */       } else if (index + 7 < endIndex && str
/* 394 */         .charAt(index) == 'I' && str
/* 395 */         .charAt(index + 1) == 'n' && str
/* 396 */         .charAt(index + 2) == 'f' && str
/* 397 */         .charAt(index + 3) == 'i' && str
/* 398 */         .charAt(index + 4) == 'n' && str
/* 399 */         .charAt(index + 5) == 'i' && str
/* 400 */         .charAt(index + 6) == 't' && str
/* 401 */         .charAt(index + 7) == 'y') {
/*     */         
/* 403 */         index = skipWhitespace(str, index + 8, endIndex);
/* 404 */         if (index == endIndex) {
/* 405 */           return isNegative ? negativeInfinity() : positiveInfinity();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 410 */     return 9221120237041090561L;
/*     */   }
/*     */   
/*     */   abstract long positiveInfinity();
/*     */   
/*     */   abstract long valueOfFloatLiteral(CharSequence paramCharSequence, int paramInt1, int paramInt2, boolean paramBoolean1, long paramLong, int paramInt3, boolean paramBoolean2, int paramInt4);
/*     */   
/*     */   abstract long valueOfHexLiteral(CharSequence paramCharSequence, int paramInt1, int paramInt2, boolean paramBoolean1, long paramLong, int paramInt3, boolean paramBoolean2, int paramInt4);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractJavaFloatingPointBitsFromCharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */