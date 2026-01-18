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
/*     */ abstract class AbstractConfigurableFloatingPointBitsFromCharSequence
/*     */   extends AbstractFloatValueParser
/*     */ {
/*     */   private final CharDigitSet digitSet;
/*     */   private final CharSet minusSignChar;
/*     */   private final CharSet plusSignChar;
/*     */   private final CharSet decimalSeparator;
/*     */   private final CharSet groupingSeparator;
/*     */   private final CharTrie nanTrie;
/*     */   private final CharTrie infinityTrie;
/*     */   private final CharTrie exponentSeparatorTrie;
/*     */   private final CharSet formatChar;
/*     */   
/*     */   public AbstractConfigurableFloatingPointBitsFromCharSequence(NumberFormatSymbols symbols, boolean ignoreCase) {
/*  28 */     this.decimalSeparator = CharSet.copyOf(symbols.decimalSeparator(), ignoreCase);
/*  29 */     this.groupingSeparator = CharSet.copyOf(symbols.groupingSeparator(), ignoreCase);
/*  30 */     this.digitSet = CharDigitSet.copyOf(symbols.digits());
/*  31 */     this.minusSignChar = CharSet.copyOf(symbols.minusSign(), ignoreCase);
/*  32 */     this.exponentSeparatorTrie = CharTrie.copyOf(symbols.exponentSeparator(), ignoreCase);
/*  33 */     this.plusSignChar = CharSet.copyOf(symbols.plusSign(), ignoreCase);
/*  34 */     this.nanTrie = CharTrie.copyOf(symbols.nan(), ignoreCase);
/*  35 */     this.infinityTrie = CharTrie.copyOf(symbols.infinity(), ignoreCase);
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
/*     */   
/*     */   public final long parseFloatingPointLiteral(CharSequence str, int offset, int length) {
/*     */     int i, digitCount, exponent;
/*     */     boolean isSignificandTruncated;
/*  70 */     int exponentOfTruncatedSignificand, endIndex = checkBounds(str.length(), offset, length);
/*     */ 
/*     */ 
/*     */     
/*  74 */     int index = skipFormatCharacters(str, offset, endIndex);
/*  75 */     if (index == endIndex) {
/*  76 */       return 9221120237041090561L;
/*     */     }
/*  78 */     char ch = str.charAt(index);
/*     */ 
/*     */ 
/*     */     
/*  82 */     boolean isNegative = this.minusSignChar.containsKey(ch);
/*  83 */     boolean isSignificandSigned = false;
/*  84 */     if (isNegative || this.plusSignChar.containsKey(ch)) {
/*  85 */       isSignificandSigned = true;
/*  86 */       index++;
/*  87 */       if (index == endIndex) {
/*  88 */         return 9221120237041090561L;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     long significand = 0L;
/*  98 */     int significandStartIndex = index;
/*  99 */     int decimalSeparatorIndex = -1;
/* 100 */     int integerDigitCount = -1;
/* 101 */     int groupingCount = 0;
/* 102 */     boolean illegal = false;
/*     */     
/* 104 */     for (; index < endIndex; index++) {
/* 105 */       ch = str.charAt(index);
/* 106 */       int digit = this.digitSet.toDigit(ch);
/* 107 */       if (digit < 10) {
/*     */         
/* 109 */         significand = 10L * significand + digit;
/* 110 */       } else if (this.decimalSeparator.containsKey(ch)) {
/* 111 */         i = illegal | ((integerDigitCount >= 0) ? 1 : 0);
/* 112 */         decimalSeparatorIndex = index;
/* 113 */         integerDigitCount = index - significandStartIndex - groupingCount;
/* 114 */       } else if (this.groupingSeparator.containsKey(ch)) {
/* 115 */         i |= (decimalSeparatorIndex != -1) ? 1 : 0;
/* 116 */         groupingCount++;
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     int significandEndIndex = index;
/*     */     
/* 124 */     if (integerDigitCount < 0) {
/* 125 */       integerDigitCount = digitCount = significandEndIndex - significandStartIndex - groupingCount;
/* 126 */       decimalSeparatorIndex = significandEndIndex;
/* 127 */       exponent = 0;
/*     */     } else {
/* 129 */       digitCount = significandEndIndex - significandStartIndex - 1 - groupingCount;
/* 130 */       exponent = integerDigitCount - digitCount;
/*     */     } 
/* 132 */     i |= (digitCount == 0 && significandEndIndex > significandStartIndex) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 136 */     if (i == 0 && digitCount == 0) {
/* 137 */       return parseNaNOrInfinity(str, index, endIndex, isNegative, isSignificandSigned);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (index < endIndex && !isSignificandSigned) {
/* 143 */       boolean isNegative2 = this.minusSignChar.containsKey(ch);
/* 144 */       if (isNegative2 || this.plusSignChar.containsKey(ch)) {
/* 145 */         isNegative |= isNegative2;
/* 146 */         index++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 152 */     int expNumber = 0;
/* 153 */     boolean isExponentSigned = false;
/* 154 */     if (digitCount > 0) {
/* 155 */       int count = this.exponentSeparatorTrie.match(str, index, endIndex);
/* 156 */       if (count > 0) {
/* 157 */         index += count;
/* 158 */         index = skipFormatCharacters(str, index, endIndex);
/*     */ 
/*     */         
/* 161 */         ch = charAt(str, index, endIndex);
/* 162 */         boolean isExponentNegative = this.minusSignChar.containsKey(ch);
/* 163 */         if (isExponentNegative || this.plusSignChar.containsKey(ch)) {
/* 164 */           ch = charAt(str, ++index, endIndex);
/* 165 */           isExponentSigned = true;
/*     */         } 
/*     */         
/* 168 */         int digit = this.digitSet.toDigit(ch);
/* 169 */         i |= (digit >= 10) ? 1 : 0;
/*     */         
/*     */         do {
/* 172 */           if (expNumber < 1024) {
/* 173 */             expNumber = 10 * expNumber + digit;
/*     */           }
/* 175 */           ch = charAt(str, ++index, endIndex);
/* 176 */           digit = this.digitSet.toDigit(ch);
/* 177 */         } while (digit < 10);
/*     */ 
/*     */ 
/*     */         
/* 181 */         if (!isExponentSigned) {
/* 182 */           isExponentNegative = this.minusSignChar.containsKey(ch);
/* 183 */           if (isExponentNegative || this.plusSignChar.containsKey(ch)) {
/* 184 */             index++;
/*     */           }
/*     */         } 
/*     */         
/* 188 */         if (isExponentNegative) {
/* 189 */           expNumber = -expNumber;
/*     */         }
/* 191 */         exponent += expNumber;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     if (i != 0 || index < endIndex) {
/* 199 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     if (digitCount > 19) {
/* 207 */       int truncatedDigitCount = 0;
/* 208 */       significand = 0L;
/* 209 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 210 */         ch = str.charAt(index);
/* 211 */         int digit = this.digitSet.toDigit(ch);
/* 212 */         if (digit < 10) {
/* 213 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 214 */             significand = 10L * significand + digit;
/* 215 */             truncatedDigitCount++;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/* 221 */       isSignificandTruncated = (index < significandEndIndex);
/* 222 */       exponentOfTruncatedSignificand = integerDigitCount - truncatedDigitCount + expNumber;
/*     */     } else {
/* 224 */       isSignificandTruncated = false;
/* 225 */       exponentOfTruncatedSignificand = 0;
/*     */     } 
/* 227 */     return valueOfFloatLiteral(str, significandStartIndex, decimalSeparatorIndex, decimalSeparatorIndex + 1, significandEndIndex, isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand, expNumber, offset, endIndex);
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
/*     */   private int skipFormatCharacters(CharSequence str, int index, int endIndex) {
/* 242 */     while (index < endIndex && this.formatChar.containsKey(str.charAt(index))) {
/* 243 */       index++;
/*     */     }
/* 245 */     return index;
/*     */   }
/*     */   
/*     */   private long parseNaNOrInfinity(CharSequence str, int index, int endIndex, boolean isNegative, boolean isSignificandSigned) {
/* 249 */     int nanMatch = this.nanTrie.match(str, index, endIndex);
/* 250 */     if (nanMatch > 0) {
/* 251 */       index += nanMatch;
/* 252 */       if (index < endIndex && !isSignificandSigned) {
/* 253 */         char ch = str.charAt(index);
/* 254 */         if (this.minusSignChar.containsKey(ch) || this.plusSignChar.containsKey(ch)) {
/* 255 */           index++;
/*     */         }
/*     */       } 
/* 258 */       return (index == endIndex) ? nan() : 9221120237041090561L;
/*     */     } 
/* 260 */     int infinityMatch = this.infinityTrie.match(str, index, endIndex);
/* 261 */     if (infinityMatch > 0) {
/* 262 */       index += infinityMatch;
/* 263 */       if (index < endIndex && !isSignificandSigned) {
/* 264 */         char ch = str.charAt(index);
/* 265 */         isNegative = this.minusSignChar.containsKey(ch);
/* 266 */         if (isNegative || this.plusSignChar.containsKey(ch)) {
/* 267 */           index++;
/*     */         }
/*     */       } 
/* 270 */       if (index == endIndex) {
/* 271 */         return isNegative ? negativeInfinity() : positiveInfinity();
/*     */       }
/*     */     } 
/* 274 */     return 9221120237041090561L;
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
/*     */   
/*     */   abstract long valueOfFloatLiteral(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, long paramLong, int paramInt5, boolean paramBoolean2, int paramInt6, int paramInt7, int paramInt8, int paramInt9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double slowPathToDouble(CharSequence str, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, boolean isSignificandNegative, int exponentValue) {
/* 314 */     return SlowDoubleConversionPath.toDouble(str, this.digitSet, integerStartIndex, integerEndIndex, fractionStartIndex, fractionEndIndex, isSignificandNegative, exponentValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractConfigurableFloatingPointBitsFromCharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */