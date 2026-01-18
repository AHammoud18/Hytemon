/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import ch.randelshofer.fastdoubleparser.bte.ByteDigitSet;
/*     */ import ch.randelshofer.fastdoubleparser.bte.ByteTrie;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractConfigurableFloatingPointBitsFromByteArrayUtf8
/*     */   extends AbstractFloatValueParser
/*     */ {
/*     */   private final ByteDigitSet digitSet;
/*     */   private final ByteTrie minusSign;
/*     */   private final ByteTrie plusSign;
/*     */   private final ByteTrie decimalSeparator;
/*     */   private final ByteTrie groupingSeparator;
/*     */   private final ByteTrie nan;
/*     */   private final ByteTrie infinity;
/*     */   private final ByteTrie exponentSeparator;
/*     */   
/*     */   public AbstractConfigurableFloatingPointBitsFromByteArrayUtf8(NumberFormatSymbols symbols, boolean ignoreCase) {
/*  24 */     this.decimalSeparator = ByteTrie.copyOfChars(symbols.decimalSeparator(), ignoreCase);
/*  25 */     this.groupingSeparator = ByteTrie.copyOfChars(symbols.groupingSeparator(), ignoreCase);
/*  26 */     this.digitSet = ByteDigitSet.copyOf(symbols.digits());
/*  27 */     this.minusSign = ByteTrie.copyOfChars(symbols.minusSign(), ignoreCase);
/*  28 */     this.exponentSeparator = ByteTrie.copyOf(symbols.exponentSeparator(), ignoreCase);
/*  29 */     this.plusSign = ByteTrie.copyOfChars(symbols.plusSign(), ignoreCase);
/*  30 */     this.nan = ByteTrie.copyOf(symbols.nan(), ignoreCase);
/*  31 */     this.infinity = ByteTrie.copyOf(symbols.infinity(), ignoreCase);
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
/*     */   abstract long negativeInfinity();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long parseFloatingPointLiteral(byte[] str, int offset, int length) {
/*     */     int i, digitCount, exponent;
/*     */     boolean isSignificandTruncated;
/*  64 */     int exponentOfTruncatedSignificand, endIndex = checkBounds(str.length, offset, length);
/*  65 */     int index = offset;
/*     */ 
/*     */     
/*     */     int matchCount;
/*     */     
/*  70 */     boolean isNegative = ((matchCount = this.minusSign.match(str, index, endIndex)) > 0);
/*  71 */     if (isNegative) {
/*  72 */       index += matchCount;
/*     */     } else {
/*  74 */       index += matchCount = this.plusSign.match(str, index, endIndex);
/*     */     } 
/*  76 */     boolean isSignificandSigned = (matchCount > 0);
/*  77 */     if (index == endIndex) {
/*  78 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     long significand = 0L;
/*  86 */     int significandStartIndex = index;
/*  87 */     int decimalSeparatorIndex = -1;
/*  88 */     int integerDigitCount = -1;
/*  89 */     int groupingCount = 0;
/*  90 */     boolean illegal = false;
/*     */     
/*  92 */     for (; index < endIndex; index++) {
/*  93 */       byte ch = str[index];
/*  94 */       int digit = this.digitSet.toDigit(ch);
/*  95 */       if (digit < 10) {
/*     */         
/*  97 */         significand = 10L * significand + digit;
/*  98 */       } else if ((matchCount = this.decimalSeparator.match(str, index, endIndex)) > 0) {
/*  99 */         i = illegal | ((integerDigitCount >= 0) ? 1 : 0);
/* 100 */         decimalSeparatorIndex = index;
/* 101 */         integerDigitCount = index - significandStartIndex - groupingCount;
/* 102 */         index += matchCount - 1;
/* 103 */       } else if ((matchCount = this.groupingSeparator.match(str, index, endIndex)) > 0) {
/* 104 */         i |= (decimalSeparatorIndex != -1) ? 1 : 0;
/* 105 */         groupingCount += matchCount;
/* 106 */         index += matchCount - 1;
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     int significandEndIndex = index;
/*     */     
/* 114 */     if (integerDigitCount < 0) {
/* 115 */       integerDigitCount = digitCount = significandEndIndex - significandStartIndex - groupingCount;
/* 116 */       decimalSeparatorIndex = significandEndIndex;
/* 117 */       exponent = 0;
/*     */     } else {
/* 119 */       digitCount = significandEndIndex - significandStartIndex - 1 - groupingCount;
/* 120 */       exponent = integerDigitCount - digitCount;
/*     */     } 
/* 122 */     i |= (digitCount == 0 && significandEndIndex > significandStartIndex) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (index < endIndex && !isSignificandSigned) {
/* 127 */       matchCount = this.minusSign.match(str, index, endIndex);
/* 128 */       if (matchCount > 0) {
/* 129 */         isNegative = true;
/* 130 */         index += matchCount;
/*     */       } else {
/* 132 */         index += this.plusSign.match(str, index, endIndex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 138 */     int expNumber = 0;
/* 139 */     boolean isExponentSigned = false;
/* 140 */     if (digitCount > 0) {
/* 141 */       int count = this.exponentSeparator.match(str, index, endIndex);
/* 142 */       if (count > 0) {
/* 143 */         index += count;
/*     */ 
/*     */         
/* 146 */         byte ch = charAt(str, index, endIndex);
/* 147 */         boolean isExponentNegative = ((matchCount = this.minusSign.match(str, index, endIndex)) > 0);
/* 148 */         if (isExponentNegative) {
/* 149 */           index += matchCount;
/*     */         } else {
/* 151 */           index += this.plusSign.match(str, index, endIndex);
/*     */         } 
/* 153 */         ch = charAt(str, index, endIndex);
/* 154 */         int digit = this.digitSet.toDigit(ch);
/* 155 */         i |= (digit >= 10) ? 1 : 0;
/*     */         
/*     */         do {
/* 158 */           if (expNumber < 1024) {
/* 159 */             expNumber = 10 * expNumber + digit;
/*     */           }
/* 161 */           ch = charAt(str, ++index, endIndex);
/* 162 */           digit = this.digitSet.toDigit(ch);
/* 163 */         } while (digit < 10);
/*     */ 
/*     */ 
/*     */         
/* 167 */         if (!isExponentSigned) {
/* 168 */           boolean isExponentNegative2 = ((matchCount = this.minusSign.match(str, index, endIndex)) > 0);
/* 169 */           if (isExponentNegative2 || (matchCount = this.plusSign.match(str, index, endIndex)) > 0) {
/* 170 */             isExponentNegative |= isExponentNegative2;
/* 171 */             index += matchCount;
/*     */           } 
/*     */         } 
/*     */         
/* 175 */         if (isExponentNegative) {
/* 176 */           expNumber = -expNumber;
/*     */         }
/* 178 */         exponent += expNumber;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (i == 0 && digitCount == 0) {
/* 185 */       return parseNaNOrInfinity(str, index, endIndex, isNegative, isSignificandSigned);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 190 */     if (i != 0 || index < endIndex) {
/* 191 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     if (digitCount > 19) {
/* 199 */       int truncatedDigitCount = 0;
/* 200 */       significand = 0L;
/* 201 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 202 */         byte ch = str[index];
/* 203 */         int digit = this.digitSet.toDigit(ch);
/* 204 */         if (digit < 10) {
/* 205 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 206 */             significand = 10L * significand + digit;
/* 207 */             truncatedDigitCount++;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/* 213 */       isSignificandTruncated = (index < significandEndIndex);
/* 214 */       exponentOfTruncatedSignificand = integerDigitCount - truncatedDigitCount + expNumber;
/*     */     } else {
/* 216 */       isSignificandTruncated = false;
/* 217 */       exponentOfTruncatedSignificand = 0;
/*     */     } 
/* 219 */     return valueOfFloatLiteral(str, significandStartIndex, decimalSeparatorIndex, decimalSeparatorIndex + 1, significandEndIndex, isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand, expNumber, offset, endIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long parseNaNOrInfinity(byte[] str, int index, int endIndex, boolean isNegative, boolean isSignificandSigned) {
/* 226 */     int nanMatch = this.nan.match(str, index, endIndex);
/* 227 */     if (nanMatch > 0) {
/* 228 */       index += nanMatch;
/* 229 */       if (index < endIndex && !isSignificandSigned) {
/*     */         int matchCount;
/* 231 */         if ((matchCount = this.minusSign.match(str, index, endIndex)) > 0 || (
/* 232 */           matchCount = this.plusSign.match(str, index, endIndex)) > 0) {
/* 233 */           index += matchCount;
/*     */         }
/*     */       } 
/* 236 */       return (index == endIndex) ? nan() : 9221120237041090561L;
/*     */     } 
/* 238 */     int infinityMatch = this.infinity.match(str, index, endIndex);
/* 239 */     if (infinityMatch > 0) {
/* 240 */       index += infinityMatch;
/* 241 */       if (index < endIndex && !isSignificandSigned) {
/* 242 */         int matchCount = this.minusSign.match(str, index, endIndex);
/* 243 */         isNegative = (matchCount > 0);
/* 244 */         if (isNegative || (matchCount = this.plusSign.match(str, index, endIndex)) > 0) {
/* 245 */           index += matchCount;
/*     */         }
/*     */       } 
/* 248 */       if (index == endIndex) {
/* 249 */         return isNegative ? negativeInfinity() : positiveInfinity();
/*     */       }
/*     */     } 
/* 252 */     return 9221120237041090561L;
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
/*     */   abstract long positiveInfinity();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract long valueOfFloatLiteral(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, long paramLong, int paramInt5, boolean paramBoolean2, int paramInt6, int paramInt7, int paramInt8, int paramInt9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double slowPathToDouble(byte[] str, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, boolean isSignificandNegative, int exponentValue) {
/* 291 */     return SlowDoubleConversionPath.toDouble(str, this.digitSet, integerStartIndex, integerEndIndex, fractionStartIndex, fractionEndIndex, isSignificandNegative, exponentValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractConfigurableFloatingPointBitsFromByteArrayUtf8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */