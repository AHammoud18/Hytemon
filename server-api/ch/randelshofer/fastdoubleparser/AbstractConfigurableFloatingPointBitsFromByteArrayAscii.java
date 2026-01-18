/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import ch.randelshofer.fastdoubleparser.bte.ByteDigitSet;
/*     */ import ch.randelshofer.fastdoubleparser.bte.ByteSet;
/*     */ import ch.randelshofer.fastdoubleparser.bte.ByteTrie;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractConfigurableFloatingPointBitsFromByteArrayAscii
/*     */   extends AbstractFloatValueParser
/*     */ {
/*     */   private final ByteDigitSet digitSet;
/*     */   private final ByteSet minusSign;
/*     */   private final ByteSet plusSign;
/*     */   private final ByteSet decimalSeparator;
/*     */   private final ByteSet groupingSeparator;
/*     */   private final ByteTrie nan;
/*     */   private final ByteTrie infinity;
/*     */   private final ByteTrie exponentSeparator;
/*     */   
/*     */   public AbstractConfigurableFloatingPointBitsFromByteArrayAscii(NumberFormatSymbols symbols, boolean ignoreCase) {
/*  25 */     this.decimalSeparator = ByteSet.copyOf(symbols.decimalSeparator(), ignoreCase);
/*  26 */     this.groupingSeparator = ByteSet.copyOf(symbols.groupingSeparator(), ignoreCase);
/*  27 */     this.digitSet = ByteDigitSet.copyOf(symbols.digits());
/*  28 */     this.minusSign = ByteSet.copyOf(symbols.minusSign(), ignoreCase);
/*  29 */     this.exponentSeparator = ByteTrie.copyOf(symbols.exponentSeparator(), ignoreCase);
/*  30 */     this.plusSign = ByteSet.copyOf(symbols.plusSign(), ignoreCase);
/*  31 */     this.nan = ByteTrie.copyOf(symbols.nan(), ignoreCase);
/*  32 */     this.infinity = ByteTrie.copyOf(symbols.infinity(), ignoreCase);
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
/*     */   public final long parseFloatingPointLiteral(byte[] str, int offset, int length) {
/*     */     int i, digitCount, exponent;
/*     */     boolean isSignificandTruncated;
/*  66 */     int exponentOfTruncatedSignificand, endIndex = checkBounds(str.length, offset, length);
/*  67 */     int index = offset;
/*  68 */     byte ch = str[index];
/*     */ 
/*     */ 
/*     */     
/*  72 */     boolean isNegative = this.minusSign.containsKey(ch);
/*  73 */     boolean isSignificandSigned = false;
/*  74 */     if (isNegative || this.plusSign.containsKey(ch)) {
/*  75 */       isSignificandSigned = true;
/*  76 */       index++;
/*  77 */       if (index == endIndex) {
/*  78 */         return 9221120237041090561L;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     long significand = 0L;
/*  87 */     int significandStartIndex = index;
/*  88 */     int decimalSeparatorIndex = -1;
/*  89 */     int integerDigitCount = -1;
/*  90 */     int groupingCount = 0;
/*  91 */     boolean illegal = false;
/*     */     
/*  93 */     for (; index < endIndex; index++) {
/*  94 */       ch = str[index];
/*  95 */       int digit = this.digitSet.toDigit(ch);
/*  96 */       if (digit < 10) {
/*     */         
/*  98 */         significand = 10L * significand + digit;
/*  99 */       } else if (this.decimalSeparator.containsKey(ch)) {
/* 100 */         i = illegal | ((integerDigitCount >= 0) ? 1 : 0);
/* 101 */         decimalSeparatorIndex = index;
/* 102 */         integerDigitCount = index - significandStartIndex - groupingCount;
/* 103 */       } else if (this.groupingSeparator.containsKey(ch)) {
/* 104 */         i |= (decimalSeparatorIndex != -1) ? 1 : 0;
/* 105 */         groupingCount++;
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     int significandEndIndex = index;
/*     */     
/* 113 */     if (integerDigitCount < 0) {
/* 114 */       integerDigitCount = digitCount = significandEndIndex - significandStartIndex - groupingCount;
/* 115 */       decimalSeparatorIndex = significandEndIndex;
/* 116 */       exponent = 0;
/*     */     } else {
/* 118 */       digitCount = significandEndIndex - significandStartIndex - 1 - groupingCount;
/* 119 */       exponent = integerDigitCount - digitCount;
/*     */     } 
/* 121 */     i |= (digitCount == 0 && significandEndIndex > significandStartIndex) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (index < endIndex && !isSignificandSigned) {
/* 126 */       isNegative = this.minusSign.containsKey(ch);
/* 127 */       if (isNegative || this.plusSign.containsKey(ch)) {
/* 128 */         index++;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 134 */     int expNumber = 0;
/* 135 */     boolean isExponentSigned = false;
/* 136 */     if (digitCount > 0) {
/* 137 */       int count = this.exponentSeparator.match(str, index, endIndex);
/* 138 */       if (count > 0) {
/* 139 */         index += count;
/*     */ 
/*     */         
/* 142 */         ch = charAt(str, index, endIndex);
/* 143 */         boolean isExponentNegative = this.minusSign.containsKey(ch);
/* 144 */         if (isExponentNegative || this.plusSign.containsKey(ch)) {
/* 145 */           ch = charAt(str, ++index, endIndex);
/* 146 */           isExponentSigned = true;
/*     */         } 
/*     */         
/* 149 */         int digit = this.digitSet.toDigit(ch);
/* 150 */         i |= (digit >= 10) ? 1 : 0;
/*     */         
/*     */         do {
/* 153 */           if (expNumber < 1024) {
/* 154 */             expNumber = 10 * expNumber + digit;
/*     */           }
/* 156 */           ch = charAt(str, ++index, endIndex);
/* 157 */           digit = this.digitSet.toDigit(ch);
/* 158 */         } while (digit < 10);
/*     */ 
/*     */ 
/*     */         
/* 162 */         if (!isExponentSigned) {
/* 163 */           isExponentNegative = this.minusSign.containsKey(ch);
/* 164 */           if (isExponentNegative || this.plusSign.containsKey(ch)) {
/* 165 */             index++;
/*     */           }
/*     */         } 
/*     */         
/* 169 */         if (isExponentNegative) {
/* 170 */           expNumber = -expNumber;
/*     */         }
/* 172 */         exponent += expNumber;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 178 */     if (i == 0 && digitCount == 0) {
/* 179 */       return parseNaNOrInfinity(str, index, endIndex, isNegative, isSignificandSigned);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (i != 0 || index < endIndex) {
/* 185 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     if (digitCount > 19) {
/* 193 */       int truncatedDigitCount = 0;
/* 194 */       significand = 0L;
/* 195 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 196 */         ch = str[index];
/* 197 */         int digit = this.digitSet.toDigit(ch);
/* 198 */         if (digit < 10) {
/* 199 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 200 */             significand = 10L * significand + digit;
/* 201 */             truncatedDigitCount++;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/* 207 */       isSignificandTruncated = (index < significandEndIndex);
/* 208 */       exponentOfTruncatedSignificand = integerDigitCount - truncatedDigitCount + expNumber;
/*     */     } else {
/* 210 */       isSignificandTruncated = false;
/* 211 */       exponentOfTruncatedSignificand = 0;
/*     */     } 
/* 213 */     return valueOfFloatLiteral(str, significandStartIndex, decimalSeparatorIndex, decimalSeparatorIndex + 1, significandEndIndex, isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand, expNumber, offset, endIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long parseNaNOrInfinity(byte[] str, int index, int endIndex, boolean isNegative, boolean isSignificandSigned) {
/* 220 */     int nanMatch = this.nan.match(str, index, endIndex);
/* 221 */     if (nanMatch > 0) {
/* 222 */       index += nanMatch;
/* 223 */       if (index < endIndex && !isSignificandSigned) {
/* 224 */         byte ch = str[index];
/* 225 */         if (this.minusSign.containsKey(ch) || this.plusSign.containsKey(ch)) {
/* 226 */           index++;
/*     */         }
/*     */       } 
/* 229 */       return (index == endIndex) ? nan() : 9221120237041090561L;
/*     */     } 
/* 231 */     int infinityMatch = this.infinity.match(str, index, endIndex);
/* 232 */     if (infinityMatch > 0) {
/* 233 */       index += infinityMatch;
/* 234 */       if (index < endIndex && !isSignificandSigned) {
/* 235 */         byte ch = str[index];
/* 236 */         isNegative = this.minusSign.containsKey(ch);
/* 237 */         if (isNegative || this.plusSign.containsKey(ch)) {
/* 238 */           index++;
/*     */         }
/*     */       } 
/* 241 */       if (index == endIndex) {
/* 242 */         return isNegative ? negativeInfinity() : positiveInfinity();
/*     */       }
/*     */     } 
/* 245 */     return 9221120237041090561L;
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
/* 284 */     return SlowDoubleConversionPath.toDouble(str, this.digitSet, integerStartIndex, integerEndIndex, fractionStartIndex, fractionEndIndex, isSignificandNegative, exponentValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractConfigurableFloatingPointBitsFromByteArrayAscii.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */