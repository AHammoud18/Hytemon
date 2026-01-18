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
/*     */ abstract class AbstractJavaFloatingPointBitsFromByteArray
/*     */   extends AbstractFloatValueParser
/*     */ {
/*     */   private static int skipWhitespace(byte[] str, int index, int endIndex) {
/*  28 */     while (index < endIndex && (str[index] & 0xFF) <= 32) {
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
/*     */   private long parseDecFloatLiteral(byte[] str, int index, int startIndex, int endIndex, boolean isNegative) {
/*     */     int i, digitCount, exponent;
/*     */     boolean isSignificandTruncated;
/*     */     int exponentOfTruncatedSignificand;
/*  72 */     long significand = 0L;
/*  73 */     int significandStartIndex = index;
/*  74 */     int integerDigitCount = -1;
/*  75 */     boolean illegal = false;
/*  76 */     byte ch = 0;
/*  77 */     int swarLimit = Math.min(endIndex - 4, 1073741824);
/*  78 */     for (; index < endIndex; index++) {
/*  79 */       ch = str[index];
/*  80 */       int digit = (char)(ch - 48);
/*  81 */       if (digit < 10) {
/*     */         
/*  83 */         significand = 10L * significand + digit;
/*  84 */       } else if (ch == 46) {
/*  85 */         i = illegal | ((integerDigitCount >= 0) ? 1 : 0);
/*  86 */         integerDigitCount = index - significandStartIndex;
/*  87 */         for (; index < swarLimit; index += 4) {
/*  88 */           int digits = FastDoubleSwar.tryToParseFourDigits(str, index + 1);
/*  89 */           if (digits < 0) {
/*     */             break;
/*     */           }
/*     */           
/*  93 */           significand = 10000L * significand + digits;
/*     */         } 
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     int significandEndIndex = index;
/*     */     
/* 102 */     if (integerDigitCount < 0) {
/* 103 */       digitCount = index - significandStartIndex;
/* 104 */       integerDigitCount = digitCount;
/* 105 */       exponent = 0;
/*     */     } else {
/* 107 */       digitCount = index - significandStartIndex - 1;
/* 108 */       exponent = integerDigitCount - digitCount;
/*     */     } 
/* 110 */     i |= (digitCount == 0 && significandEndIndex > significandStartIndex) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 114 */     int expNumber = 0;
/* 115 */     if ((ch | 0x20) == 101) {
/* 116 */       ch = charAt(str, ++index, endIndex);
/* 117 */       boolean isExponentNegative = (ch == 45);
/* 118 */       if (isExponentNegative || ch == 43) {
/* 119 */         ch = charAt(str, ++index, endIndex);
/*     */       }
/* 121 */       int digit = (char)(ch - 48);
/* 122 */       i |= (digit >= 10) ? 1 : 0;
/*     */       
/*     */       while (true) {
/* 125 */         if (expNumber < 1024) {
/* 126 */           expNumber = 10 * expNumber + digit;
/*     */         }
/* 128 */         ch = charAt(str, ++index, endIndex);
/* 129 */         digit = (char)(ch - 48);
/* 130 */         if (digit >= 10) {
/* 131 */           if (isExponentNegative) {
/* 132 */             expNumber = -expNumber;
/*     */           }
/* 134 */           exponent += expNumber;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 140 */     if (i == 0 && digitCount == 0) {
/* 141 */       return parseNaNOrInfinity(str, index, endIndex, isNegative);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     if ((ch | 0x22) == 102) {
/* 148 */       index++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 153 */     index = skipWhitespace(str, index, endIndex);
/* 154 */     if (i != 0 || index < endIndex) {
/* 155 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     if (digitCount > 19) {
/* 163 */       int truncatedDigitCount = 0;
/* 164 */       significand = 0L;
/* 165 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 166 */         ch = str[index];
/* 167 */         int digit = (char)(ch - 48);
/* 168 */         if (digit < 10) {
/* 169 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 170 */             significand = 10L * significand + digit;
/* 171 */             truncatedDigitCount++;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/* 177 */       isSignificandTruncated = (index < significandEndIndex);
/* 178 */       exponentOfTruncatedSignificand = integerDigitCount - truncatedDigitCount + expNumber;
/*     */     } else {
/* 180 */       isSignificandTruncated = false;
/* 181 */       exponentOfTruncatedSignificand = 0;
/*     */     } 
/* 183 */     return valueOfFloatLiteral(str, startIndex, endIndex, isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
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
/*     */   public long parseFloatingPointLiteral(byte[] str, int offset, int length) {
/* 207 */     int endIndex = checkBounds(str.length, offset, length);
/*     */ 
/*     */ 
/*     */     
/* 211 */     int index = skipWhitespace(str, offset, endIndex);
/* 212 */     if (index == endIndex) {
/* 213 */       return 9221120237041090561L;
/*     */     }
/* 215 */     byte ch = str[index];
/*     */ 
/*     */ 
/*     */     
/* 219 */     boolean isNegative = (ch == 45);
/* 220 */     if (isNegative || ch == 43) {
/* 221 */       ch = charAt(str, ++index, endIndex);
/* 222 */       if (ch == 0) {
/* 223 */         return 9221120237041090561L;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 229 */     boolean hasLeadingZero = (ch == 48);
/* 230 */     if (hasLeadingZero) {
/* 231 */       ch = charAt(str, ++index, endIndex);
/* 232 */       if ((ch | 0x20) == 120) {
/* 233 */         return parseHexFloatingPointLiteral(str, index + 1, offset, endIndex, isNegative);
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
/*     */   private long parseHexFloatingPointLiteral(byte[] str, int index, int startIndex, int endIndex, boolean isNegative) {
/*     */     int digitCount, i;
/*     */     boolean isSignificandTruncated;
/* 269 */     long significand = 0L;
/* 270 */     int exponent = 0;
/* 271 */     int significandStartIndex = index;
/* 272 */     int virtualIndexOfPoint = -1;
/*     */     
/* 274 */     boolean illegal = false;
/* 275 */     byte ch = 0;
/* 276 */     for (; index < endIndex; index++) {
/* 277 */       ch = str[index];
/*     */       
/* 279 */       int hexValue = lookupHex(ch);
/* 280 */       if (hexValue >= 0) {
/* 281 */         significand = significand << 4L | hexValue;
/* 282 */       } else if (hexValue == -4) {
/* 283 */         i = illegal | ((virtualIndexOfPoint >= 0) ? 1 : 0);
/* 284 */         virtualIndexOfPoint = index;
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
/* 300 */     int significandEndIndex = index;
/* 301 */     if (virtualIndexOfPoint < 0) {
/* 302 */       digitCount = significandEndIndex - significandStartIndex;
/* 303 */       virtualIndexOfPoint = significandEndIndex;
/*     */     } else {
/* 305 */       digitCount = significandEndIndex - significandStartIndex - 1;
/* 306 */       exponent = Math.min(virtualIndexOfPoint - index + 1, 1024) * 4;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 311 */     int expNumber = 0;
/* 312 */     boolean hasExponent = ((ch | 0x20) == 112);
/* 313 */     if (hasExponent) {
/* 314 */       ch = charAt(str, ++index, endIndex);
/* 315 */       boolean isExponentNegative = (ch == 45);
/* 316 */       if (isExponentNegative || ch == 43) {
/* 317 */         ch = charAt(str, ++index, endIndex);
/*     */       }
/* 319 */       int digit = (char)(ch - 48);
/* 320 */       i |= (digit >= 10) ? 1 : 0;
/*     */       
/*     */       while (true) {
/* 323 */         if (expNumber < 1024) {
/* 324 */           expNumber = 10 * expNumber + digit;
/*     */         }
/* 326 */         ch = charAt(str, ++index, endIndex);
/* 327 */         digit = (char)(ch - 48);
/* 328 */         if (digit >= 10) {
/* 329 */           if (isExponentNegative) {
/* 330 */             expNumber = -expNumber;
/*     */           }
/* 332 */           exponent += expNumber;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 338 */     if ((ch | 0x22) == 102) {
/* 339 */       index++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 344 */     index = skipWhitespace(str, index, endIndex);
/* 345 */     if (i != 0 || index < endIndex || digitCount == 0 || !hasExponent)
/*     */     {
/*     */       
/* 348 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     int skipCountInTruncatedDigits = 0;
/* 355 */     if (digitCount > 16) {
/* 356 */       significand = 0L;
/* 357 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 358 */         ch = str[index];
/*     */         
/* 360 */         int hexValue = lookupHex(ch);
/* 361 */         if (hexValue >= 0) {
/* 362 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 363 */             significand = significand << 4L | hexValue;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         } else {
/* 368 */           skipCountInTruncatedDigits++;
/*     */         } 
/*     */       } 
/* 371 */       isSignificandTruncated = (index < significandEndIndex);
/*     */     } else {
/* 373 */       isSignificandTruncated = false;
/*     */     } 
/*     */     
/* 376 */     return valueOfHexLiteral(str, startIndex, endIndex, isNegative, significand, exponent, isSignificandTruncated, (virtualIndexOfPoint - index + skipCountInTruncatedDigits) * 4 + expNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long parseNaNOrInfinity(byte[] str, int index, int endIndex, boolean isNegative) {
/* 382 */     if (index < endIndex) {
/* 383 */       if (str[index] == 78) {
/* 384 */         if (index + 2 < endIndex && str[index + 1] == 97 && str[index + 2] == 78)
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
/* 395 */       } else if (index + 7 < endIndex && 
/* 396 */         FastDoubleSwar.readLongLE(str, index) == 8751735898823355977L) {
/*     */         
/* 398 */         index = skipWhitespace(str, index + 8, endIndex);
/* 399 */         if (index == endIndex) {
/* 400 */           return isNegative ? negativeInfinity() : positiveInfinity();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 405 */     return 9221120237041090561L;
/*     */   }
/*     */   
/*     */   abstract long positiveInfinity();
/*     */   
/*     */   abstract long valueOfFloatLiteral(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean1, long paramLong, int paramInt3, boolean paramBoolean2, int paramInt4);
/*     */   
/*     */   abstract long valueOfHexLiteral(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean1, long paramLong, int paramInt3, boolean paramBoolean2, int paramInt4);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractJavaFloatingPointBitsFromByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */