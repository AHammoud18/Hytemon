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
/*     */ abstract class AbstractJavaFloatingPointBitsFromCharArray
/*     */   extends AbstractFloatValueParser
/*     */ {
/*     */   private static final boolean CONDITIONAL_COMPILATION_PARSE_EIGHT_HEX_DIGITS = true;
/*     */   
/*     */   private static int skipWhitespace(char[] str, int index, int endIndex) {
/*  31 */     while (index < endIndex && str[index] <= ' ') {
/*  32 */       index++;
/*     */     }
/*  34 */     return index;
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
/*     */   private long parseDecFloatLiteral(char[] str, int index, int startIndex, int endIndex, boolean isNegative) {
/*     */     int i, digitCount, exponent;
/*     */     boolean isSignificandTruncated;
/*     */     int exponentOfTruncatedSignificand;
/*  75 */     long significand = 0L;
/*  76 */     int significandStartIndex = index;
/*  77 */     int integerDigitCount = -1;
/*  78 */     boolean illegal = false;
/*  79 */     char ch = Character.MIN_VALUE;
/*  80 */     int swarLimit = Math.min(endIndex - 4, 1073741824);
/*  81 */     for (; index < endIndex; index++) {
/*  82 */       ch = str[index];
/*  83 */       int digit = (char)(ch - 48);
/*  84 */       if (digit < 10) {
/*     */         
/*  86 */         significand = 10L * significand + digit;
/*  87 */       } else if (ch == '.') {
/*  88 */         i = illegal | ((integerDigitCount >= 0) ? 1 : 0);
/*  89 */         integerDigitCount = index - significandStartIndex;
/*  90 */         for (; index < swarLimit; index += 4) {
/*  91 */           int digits = FastDoubleSwar.tryToParseFourDigits(str, index + 1);
/*  92 */           if (digits < 0) {
/*     */             break;
/*     */           }
/*     */           
/*  96 */           significand = 10000L * significand + digits;
/*     */         } 
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     int significandEndIndex = index;
/*     */     
/* 105 */     if (integerDigitCount < 0) {
/* 106 */       digitCount = index - significandStartIndex;
/* 107 */       integerDigitCount = digitCount;
/* 108 */       exponent = 0;
/*     */     } else {
/* 110 */       digitCount = index - significandStartIndex - 1;
/* 111 */       exponent = integerDigitCount - digitCount;
/*     */     } 
/* 113 */     i |= (digitCount == 0 && significandEndIndex > significandStartIndex) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 117 */     int expNumber = 0;
/* 118 */     if ((ch | 0x20) == 101) {
/* 119 */       ch = charAt(str, ++index, endIndex);
/* 120 */       boolean isExponentNegative = (ch == '-');
/* 121 */       if (isExponentNegative || ch == '+') {
/* 122 */         ch = charAt(str, ++index, endIndex);
/*     */       }
/* 124 */       int digit = (char)(ch - 48);
/* 125 */       i |= (digit >= 10) ? 1 : 0;
/*     */       
/*     */       while (true) {
/* 128 */         if (expNumber < 1024) {
/* 129 */           expNumber = 10 * expNumber + digit;
/*     */         }
/* 131 */         ch = charAt(str, ++index, endIndex);
/* 132 */         digit = (char)(ch - 48);
/* 133 */         if (digit >= 10) {
/* 134 */           if (isExponentNegative) {
/* 135 */             expNumber = -expNumber;
/*     */           }
/* 137 */           exponent += expNumber;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 142 */     if (i == 0 && digitCount == 0) {
/* 143 */       return parseNaNOrInfinity(str, index, endIndex, isNegative);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     if ((ch | 0x22) == 102) {
/* 150 */       index++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 155 */     index = skipWhitespace(str, index, endIndex);
/* 156 */     if (i != 0 || index < endIndex) {
/* 157 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (digitCount > 19) {
/* 165 */       int truncatedDigitCount = 0;
/* 166 */       significand = 0L;
/* 167 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 168 */         ch = str[index];
/* 169 */         int digit = (char)(ch - 48);
/* 170 */         if (digit < 10) {
/* 171 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 172 */             significand = 10L * significand + digit;
/* 173 */             truncatedDigitCount++;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/* 179 */       isSignificandTruncated = (index < significandEndIndex);
/* 180 */       exponentOfTruncatedSignificand = integerDigitCount - truncatedDigitCount + expNumber;
/*     */     } else {
/* 182 */       isSignificandTruncated = false;
/* 183 */       exponentOfTruncatedSignificand = 0;
/*     */     } 
/* 185 */     return valueOfFloatLiteral(str, startIndex, endIndex, isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
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
/*     */   public long parseFloatingPointLiteral(char[] str, int offset, int length) {
/* 208 */     int endIndex = checkBounds(str.length, offset, length);
/*     */ 
/*     */ 
/*     */     
/* 212 */     int index = skipWhitespace(str, offset, endIndex);
/* 213 */     if (index == endIndex) {
/* 214 */       return 9221120237041090561L;
/*     */     }
/* 216 */     char ch = str[index];
/*     */ 
/*     */ 
/*     */     
/* 220 */     boolean isNegative = (ch == '-');
/* 221 */     if (isNegative || ch == '+') {
/* 222 */       ch = charAt(str, ++index, endIndex);
/* 223 */       if (ch == '\000') {
/* 224 */         return 9221120237041090561L;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 230 */     boolean hasLeadingZero = (ch == '0');
/* 231 */     if (hasLeadingZero) {
/* 232 */       ch = charAt(str, ++index, endIndex);
/* 233 */       if ((ch | 0x20) == 120) {
/* 234 */         return parseHexFloatLiteral(str, index + 1, offset, endIndex, isNegative);
/*     */       }
/* 236 */       index--;
/*     */     } 
/*     */     
/* 239 */     return parseDecFloatLiteral(str, index, offset, endIndex, isNegative);
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
/*     */   private long parseHexFloatLiteral(char[] str, int index, int startIndex, int endIndex, boolean isNegative) {
/*     */     int digitCount, i;
/*     */     boolean isSignificandTruncated;
/* 270 */     long significand = 0L;
/* 271 */     int exponent = 0;
/* 272 */     int significandStartIndex = index;
/* 273 */     int virtualIndexOfPoint = -1;
/*     */     
/* 275 */     boolean illegal = false;
/* 276 */     char ch = Character.MIN_VALUE;
/* 277 */     for (; index < endIndex; index++) {
/* 278 */       ch = str[index];
/*     */       
/* 280 */       int hexValue = lookupHex(ch);
/* 281 */       if (hexValue >= 0) {
/* 282 */         significand = significand << 4L | hexValue;
/* 283 */       } else if (hexValue == -4) {
/* 284 */         i = illegal | ((virtualIndexOfPoint >= 0) ? 1 : 0);
/* 285 */         virtualIndexOfPoint = index;
/*     */         
/* 287 */         while (index < endIndex - 8) {
/* 288 */           long parsed = tryToParseEightHexDigits(str, index + 1);
/* 289 */           if (parsed >= 0L) {
/*     */             
/* 291 */             significand = (significand << 32L) + parsed;
/*     */             
/*     */             index += 8;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 301 */     int significandEndIndex = index;
/* 302 */     if (virtualIndexOfPoint < 0) {
/* 303 */       digitCount = significandEndIndex - significandStartIndex;
/* 304 */       virtualIndexOfPoint = significandEndIndex;
/*     */     } else {
/* 306 */       digitCount = significandEndIndex - significandStartIndex - 1;
/* 307 */       exponent = Math.min(virtualIndexOfPoint - index + 1, 1024) * 4;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 312 */     int expNumber = 0;
/* 313 */     boolean hasExponent = ((ch | 0x20) == 112);
/* 314 */     if (hasExponent) {
/* 315 */       ch = charAt(str, ++index, endIndex);
/* 316 */       boolean isExponentNegative = (ch == '-');
/* 317 */       if (isExponentNegative || ch == '+') {
/* 318 */         ch = charAt(str, ++index, endIndex);
/*     */       }
/* 320 */       int digit = (char)(ch - 48);
/* 321 */       i |= (digit >= 10) ? 1 : 0;
/*     */       
/*     */       while (true) {
/* 324 */         if (expNumber < 1024) {
/* 325 */           expNumber = 10 * expNumber + digit;
/*     */         }
/* 327 */         ch = charAt(str, ++index, endIndex);
/* 328 */         digit = (char)(ch - 48);
/* 329 */         if (digit >= 10) {
/* 330 */           if (isExponentNegative) {
/* 331 */             expNumber = -expNumber;
/*     */           }
/* 333 */           exponent += expNumber;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 339 */     if ((ch | 0x22) == 102) {
/* 340 */       index++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 345 */     index = skipWhitespace(str, index, endIndex);
/* 346 */     if (i != 0 || index < endIndex || digitCount == 0 || !hasExponent)
/*     */     {
/*     */       
/* 349 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     int skipCountInTruncatedDigits = 0;
/* 356 */     if (digitCount > 16) {
/* 357 */       significand = 0L;
/* 358 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 359 */         ch = str[index];
/*     */         
/* 361 */         int hexValue = lookupHex(ch);
/* 362 */         if (hexValue >= 0) {
/* 363 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 364 */             significand = significand << 4L | hexValue;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         } else {
/* 369 */           skipCountInTruncatedDigits++;
/*     */         } 
/*     */       } 
/* 372 */       isSignificandTruncated = (index < significandEndIndex);
/*     */     } else {
/* 374 */       isSignificandTruncated = false;
/*     */     } 
/*     */     
/* 377 */     return valueOfHexLiteral(str, startIndex, endIndex, isNegative, significand, exponent, isSignificandTruncated, (virtualIndexOfPoint - index + skipCountInTruncatedDigits) * 4 + expNumber);
/*     */   }
/*     */ 
/*     */   
/*     */   private long parseNaNOrInfinity(char[] str, int index, int endIndex, boolean isNegative) {
/* 382 */     if (index < endIndex) {
/* 383 */       if (str[index] == 'N') {
/* 384 */         if (index + 2 < endIndex && str[index + 1] == 'a' && str[index + 2] == 'N')
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 389 */           index = skipWhitespace(str, index + 3, endIndex);
/* 390 */           if (index == endIndex) {
/* 391 */             return nan();
/*     */           }
/*     */         }
/*     */       
/* 395 */       } else if (index + 7 < endIndex && str[index] == 'I' && str[index + 1] == 'n' && str[index + 2] == 'f' && str[index + 3] == 'i' && str[index + 4] == 'n' && str[index + 5] == 'i' && str[index + 6] == 't' && str[index + 7] == 'y') {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 405 */         index = skipWhitespace(str, index + 8, endIndex);
/* 406 */         if (index == endIndex) {
/* 407 */           return isNegative ? negativeInfinity() : positiveInfinity();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 412 */     return 9221120237041090561L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract long positiveInfinity();
/*     */ 
/*     */ 
/*     */   
/*     */   private long tryToParseEightHexDigits(char[] str, int offset) {
/* 422 */     return FastDoubleSwar.tryToParseEightHexDigits(str, offset);
/*     */   }
/*     */   
/*     */   abstract long valueOfFloatLiteral(char[] paramArrayOfchar, int paramInt1, int paramInt2, boolean paramBoolean1, long paramLong, int paramInt3, boolean paramBoolean2, int paramInt4);
/*     */   
/*     */   abstract long valueOfHexLiteral(char[] paramArrayOfchar, int paramInt1, int paramInt2, boolean paramBoolean1, long paramLong, int paramInt3, boolean paramBoolean2, int paramInt4);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractJavaFloatingPointBitsFromCharArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */