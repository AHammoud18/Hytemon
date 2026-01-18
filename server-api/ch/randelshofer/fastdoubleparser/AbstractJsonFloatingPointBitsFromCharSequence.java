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
/*     */ abstract class AbstractJsonFloatingPointBitsFromCharSequence
/*     */   extends AbstractFloatValueParser
/*     */ {
/*     */   public final long parseNumber(CharSequence str, int offset, int length) {
/*     */     int i, digitCount, exponent;
/*     */     boolean isSignificandTruncated;
/*  31 */     int exponentOfTruncatedSignificand, endIndex = checkBounds(str.length(), offset, length);
/*  32 */     int index = offset;
/*  33 */     char ch = charAt(str, index, endIndex);
/*     */ 
/*     */ 
/*     */     
/*  37 */     boolean isNegative = (ch == '-');
/*  38 */     if (isNegative) {
/*  39 */       ch = charAt(str, ++index, endIndex);
/*  40 */       if (ch == '\000') {
/*  41 */         return 9221120237041090561L;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  47 */     boolean hasLeadingZero = (ch == '0');
/*  48 */     if (hasLeadingZero) {
/*  49 */       ch = charAt(str, ++index, endIndex);
/*  50 */       if (ch == '0') {
/*  51 */         return 9221120237041090561L;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     long significand = 0L;
/*  60 */     int significandStartIndex = index;
/*  61 */     int integerDigitCount = -1;
/*  62 */     boolean illegal = false;
/*     */     
/*  64 */     for (; index < endIndex; index++) {
/*  65 */       ch = str.charAt(index);
/*  66 */       int digit = (char)(ch - 48);
/*  67 */       if (digit < 10) {
/*     */         
/*  69 */         significand = 10L * significand + digit;
/*  70 */       } else if (ch == '.') {
/*  71 */         i = illegal | ((integerDigitCount >= 0) ? 1 : 0);
/*  72 */         integerDigitCount = index - significandStartIndex;
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
/*  88 */     int significandEndIndex = index;
/*     */     
/*  90 */     if (integerDigitCount < 0) {
/*  91 */       digitCount = index - significandStartIndex;
/*  92 */       integerDigitCount = digitCount;
/*  93 */       exponent = 0;
/*     */     } else {
/*  95 */       digitCount = index - significandStartIndex - 1;
/*  96 */       exponent = integerDigitCount - digitCount;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 101 */     int expNumber = 0;
/* 102 */     if ((ch | 0x20) == 101) {
/* 103 */       ch = charAt(str, ++index, endIndex);
/* 104 */       boolean isExponentNegative = (ch == '-');
/* 105 */       if (isExponentNegative || ch == '+') {
/* 106 */         ch = charAt(str, ++index, endIndex);
/*     */       }
/* 108 */       int digit = (char)(ch - 48);
/* 109 */       i |= (digit >= 10) ? 1 : 0;
/*     */       
/*     */       while (true) {
/* 112 */         if (expNumber < 1024) {
/* 113 */           expNumber = 10 * expNumber + digit;
/*     */         }
/* 115 */         ch = charAt(str, ++index, endIndex);
/* 116 */         digit = (char)(ch - 48);
/* 117 */         if (digit >= 10) {
/* 118 */           if (isExponentNegative) {
/* 119 */             expNumber = -expNumber;
/*     */           }
/* 121 */           exponent += expNumber;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 126 */     if (i != 0 || index < endIndex || (!hasLeadingZero && digitCount == 0))
/*     */     {
/* 128 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (digitCount > 19) {
/* 136 */       int truncatedDigitCount = 0;
/* 137 */       significand = 0L;
/* 138 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 139 */         ch = str.charAt(index);
/* 140 */         int digit = (char)(ch - 48);
/* 141 */         if (digit < 10) {
/* 142 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 143 */             significand = 10L * significand + digit;
/* 144 */             truncatedDigitCount++;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/* 150 */       isSignificandTruncated = (index < significandEndIndex);
/* 151 */       exponentOfTruncatedSignificand = integerDigitCount - truncatedDigitCount + expNumber;
/*     */     } else {
/* 153 */       isSignificandTruncated = false;
/* 154 */       exponentOfTruncatedSignificand = 0;
/*     */     } 
/* 156 */     return valueOfFloatLiteral(str, offset, endIndex, isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*     */   }
/*     */   
/*     */   abstract long valueOfFloatLiteral(CharSequence paramCharSequence, int paramInt1, int paramInt2, boolean paramBoolean1, long paramLong, int paramInt3, boolean paramBoolean2, int paramInt4);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractJsonFloatingPointBitsFromCharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */