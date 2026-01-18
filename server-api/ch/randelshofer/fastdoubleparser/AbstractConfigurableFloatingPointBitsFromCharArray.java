/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import ch.randelshofer.fastdoubleparser.chr.CharDigitSet;
/*     */ import ch.randelshofer.fastdoubleparser.chr.CharSet;
/*     */ import ch.randelshofer.fastdoubleparser.chr.CharSetOfNone;
/*     */ import ch.randelshofer.fastdoubleparser.chr.CharTrie;
/*     */ import ch.randelshofer.fastdoubleparser.chr.FormatCharSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractConfigurableFloatingPointBitsFromCharArray
/*     */   extends AbstractFloatValueParser
/*     */ {
/*     */   private final CharDigitSet digitSet;
/*     */   private final CharSet minusSign;
/*     */   private final CharSet plusSign;
/*     */   private final CharSet decimalSeparator;
/*     */   private final CharSet groupingSeparator;
/*     */   private final CharTrie nan;
/*     */   private final CharTrie infinity;
/*     */   private final CharTrie exponentSeparator;
/*     */   private final CharSet formatChar;
/*     */   
/*     */   public AbstractConfigurableFloatingPointBitsFromCharArray(NumberFormatSymbols symbols, boolean ignoreCase) {
/*  28 */     this.decimalSeparator = CharSet.copyOf(symbols.decimalSeparator(), ignoreCase);
/*  29 */     this.groupingSeparator = CharSet.copyOf(symbols.groupingSeparator(), ignoreCase);
/*  30 */     this.digitSet = CharDigitSet.copyOf(symbols.digits());
/*  31 */     this.minusSign = CharSet.copyOf(symbols.minusSign(), ignoreCase);
/*  32 */     this.exponentSeparator = CharTrie.copyOf(symbols.exponentSeparator(), ignoreCase);
/*  33 */     this.plusSign = CharSet.copyOf(symbols.plusSign(), ignoreCase);
/*  34 */     this.nan = CharTrie.copyOf(symbols.nan(), ignoreCase);
/*  35 */     this.infinity = CharTrie.copyOf(symbols.infinity(), ignoreCase);
/*  36 */     this.formatChar = NumberFormatSymbolsInfo.containsFormatChars(symbols) ? (CharSet)new CharSetOfNone() : (CharSet)new FormatCharSet();
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
/*     */   public final long parseFloatingPointLiteral(char[] str, int offset, int length) {
/*     */     int i, digitCount, exponent;
/*     */     boolean isSignificandTruncated;
/*  69 */     int exponentOfTruncatedSignificand, endIndex = checkBounds(str.length, offset, length);
/*     */ 
/*     */ 
/*     */     
/*  73 */     int index = skipFormatCharacters(str, offset, endIndex);
/*  74 */     if (index == endIndex) {
/*  75 */       return 9221120237041090561L;
/*     */     }
/*  77 */     char ch = str[index];
/*     */ 
/*     */ 
/*     */     
/*  81 */     boolean isNegative = this.minusSign.containsKey(ch);
/*  82 */     boolean isSignificandSigned = false;
/*  83 */     if (isNegative || this.plusSign.containsKey(ch)) {
/*  84 */       isSignificandSigned = true;
/*  85 */       index++;
/*  86 */       if (index == endIndex) {
/*  87 */         return 9221120237041090561L;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     long significand = 0L;
/*  96 */     int significandStartIndex = index;
/*  97 */     int decimalSeparatorIndex = -1;
/*  98 */     int integerDigitCount = -1;
/*  99 */     int groupingCount = 0;
/* 100 */     boolean illegal = false;
/*     */     
/* 102 */     for (; index < endIndex; index++) {
/* 103 */       ch = str[index];
/* 104 */       int digit = this.digitSet.toDigit(ch);
/* 105 */       if (digit < 10) {
/*     */         
/* 107 */         significand = 10L * significand + digit;
/* 108 */       } else if (this.decimalSeparator.containsKey(ch)) {
/* 109 */         i = illegal | ((integerDigitCount >= 0) ? 1 : 0);
/* 110 */         decimalSeparatorIndex = index;
/* 111 */         integerDigitCount = index - significandStartIndex - groupingCount;
/* 112 */       } else if (this.groupingSeparator.containsKey(ch)) {
/* 113 */         i |= (decimalSeparatorIndex != -1) ? 1 : 0;
/* 114 */         groupingCount++;
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     int significandEndIndex = index;
/*     */     
/* 122 */     if (integerDigitCount < 0) {
/* 123 */       integerDigitCount = digitCount = significandEndIndex - significandStartIndex - groupingCount;
/* 124 */       decimalSeparatorIndex = significandEndIndex;
/* 125 */       exponent = 0;
/*     */     } else {
/* 127 */       digitCount = significandEndIndex - significandStartIndex - 1 - groupingCount;
/* 128 */       exponent = integerDigitCount - digitCount;
/*     */     } 
/* 130 */     i |= (digitCount == 0 && significandEndIndex > significandStartIndex) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (i == 0 && digitCount == 0) {
/* 135 */       return parseNaNOrInfinity(str, index, endIndex, isNegative, isSignificandSigned);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 140 */     if (index < endIndex && !isSignificandSigned) {
/* 141 */       isNegative = this.minusSign.containsKey(ch);
/* 142 */       if (isNegative || this.plusSign.containsKey(ch)) {
/* 143 */         index++;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 149 */     int expNumber = 0;
/* 150 */     boolean isExponentSigned = false;
/* 151 */     if (digitCount > 0) {
/* 152 */       int count = this.exponentSeparator.match(str, index, endIndex);
/* 153 */       if (count > 0) {
/* 154 */         index += count;
/* 155 */         index = skipFormatCharacters(str, index, endIndex);
/*     */ 
/*     */         
/* 158 */         ch = charAt(str, index, endIndex);
/* 159 */         boolean isExponentNegative = this.minusSign.containsKey(ch);
/* 160 */         if (isExponentNegative || this.plusSign.containsKey(ch)) {
/* 161 */           ch = charAt(str, ++index, endIndex);
/* 162 */           isExponentSigned = true;
/*     */         } 
/*     */         
/* 165 */         int digit = this.digitSet.toDigit(ch);
/* 166 */         i |= (digit >= 10) ? 1 : 0;
/*     */         
/*     */         do {
/* 169 */           if (expNumber < 1024) {
/* 170 */             expNumber = 10 * expNumber + digit;
/*     */           }
/* 172 */           ch = charAt(str, ++index, endIndex);
/* 173 */           digit = this.digitSet.toDigit(ch);
/* 174 */         } while (digit < 10);
/*     */ 
/*     */ 
/*     */         
/* 178 */         if (!isExponentSigned) {
/* 179 */           isExponentNegative = this.minusSign.containsKey(ch);
/* 180 */           if (isExponentNegative || this.plusSign.containsKey(ch)) {
/* 181 */             index++;
/*     */           }
/*     */         } 
/*     */         
/* 185 */         if (isExponentNegative) {
/* 186 */           expNumber = -expNumber;
/*     */         }
/* 188 */         exponent += expNumber;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 194 */     if (i != 0 || index < endIndex) {
/* 195 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     if (digitCount > 19) {
/* 203 */       int truncatedDigitCount = 0;
/* 204 */       significand = 0L;
/* 205 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 206 */         ch = str[index];
/* 207 */         int digit = this.digitSet.toDigit(ch);
/* 208 */         if (digit < 10) {
/* 209 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 210 */             significand = 10L * significand + digit;
/* 211 */             truncatedDigitCount++;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/* 217 */       isSignificandTruncated = (index < significandEndIndex);
/* 218 */       exponentOfTruncatedSignificand = integerDigitCount - truncatedDigitCount + expNumber;
/*     */     } else {
/* 220 */       isSignificandTruncated = false;
/* 221 */       exponentOfTruncatedSignificand = 0;
/*     */     } 
/* 223 */     return valueOfFloatLiteral(str, significandStartIndex, decimalSeparatorIndex, decimalSeparatorIndex + 1, significandEndIndex, isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand, expNumber, offset, endIndex);
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
/*     */   private int skipFormatCharacters(char[] str, int index, int endIndex) {
/* 238 */     while (index < endIndex && this.formatChar.containsKey(str[index])) {
/* 239 */       index++;
/*     */     }
/* 241 */     return index;
/*     */   }
/*     */   
/*     */   private long parseNaNOrInfinity(char[] str, int index, int endIndex, boolean isNegative, boolean isSignificandSigned) {
/* 245 */     int nanMatch = this.nan.match(str, index, endIndex);
/* 246 */     if (nanMatch > 0) {
/* 247 */       index += nanMatch;
/* 248 */       if (index < endIndex && !isSignificandSigned) {
/* 249 */         char ch = str[index];
/* 250 */         if (this.minusSign.containsKey(ch) || this.plusSign.containsKey(ch)) {
/* 251 */           index++;
/*     */         }
/*     */       } 
/* 254 */       return (index == endIndex) ? nan() : 9221120237041090561L;
/*     */     } 
/* 256 */     int infinityMatch = this.infinity.match(str, index, endIndex);
/* 257 */     if (infinityMatch > 0) {
/* 258 */       index += infinityMatch;
/* 259 */       if (index < endIndex && !isSignificandSigned) {
/* 260 */         char ch = str[index];
/* 261 */         isNegative = this.minusSign.containsKey(ch);
/* 262 */         if (isNegative || this.plusSign.containsKey(ch)) {
/* 263 */           index++;
/*     */         }
/*     */       } 
/* 266 */       if (index == endIndex) {
/* 267 */         return isNegative ? negativeInfinity() : positiveInfinity();
/*     */       }
/*     */     } 
/* 270 */     return 9221120237041090561L;
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
/*     */   abstract long valueOfFloatLiteral(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, long paramLong, int paramInt5, boolean paramBoolean2, int paramInt6, int paramInt7, int paramInt8, int paramInt9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double slowPathToDouble(char[] str, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, boolean isSignificandNegative, int exponentValue) {
/* 309 */     return SlowDoubleConversionPath.toDouble(str, this.digitSet, integerStartIndex, integerEndIndex, fractionStartIndex, fractionEndIndex, isSignificandNegative, exponentValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractConfigurableFloatingPointBitsFromCharArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */