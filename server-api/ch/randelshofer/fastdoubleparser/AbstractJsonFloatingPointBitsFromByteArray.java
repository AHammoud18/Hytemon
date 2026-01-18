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
/*     */ abstract class AbstractJsonFloatingPointBitsFromByteArray
/*     */   extends AbstractFloatValueParser
/*     */ {
/*     */   public final long parseNumber(byte[] str, int offset, int length) {
/*     */     int i, digitCount, exponent;
/*     */     boolean isSignificandTruncated;
/*  31 */     int exponentOfTruncatedSignificand, endIndex = checkBounds(str.length, offset, length);
/*  32 */     int index = offset;
/*  33 */     byte ch = charAt(str, index, endIndex);
/*     */ 
/*     */ 
/*     */     
/*  37 */     boolean isNegative = (ch == 45);
/*  38 */     if (isNegative) {
/*  39 */       ch = charAt(str, ++index, endIndex);
/*  40 */       if (ch == 0) {
/*  41 */         return 9221120237041090561L;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  47 */     boolean hasLeadingZero = (ch == 48);
/*  48 */     if (hasLeadingZero) {
/*  49 */       ch = charAt(str, ++index, endIndex);
/*  50 */       if (ch == 48) {
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
/*  62 */     int swarLimit = Math.min(endIndex - 4, 1073741824);
/*  63 */     boolean illegal = false;
/*  64 */     for (; index < endIndex; index++) {
/*  65 */       ch = str[index];
/*  66 */       int digit = (char)(ch - 48);
/*  67 */       if (digit < 10) {
/*     */         
/*  69 */         significand = 10L * significand + digit;
/*  70 */       } else if (ch == 46) {
/*  71 */         i = illegal | ((integerDigitCount >= 0) ? 1 : 0);
/*  72 */         integerDigitCount = index - significandStartIndex;
/*  73 */         for (; index < swarLimit; index += 4) {
/*  74 */           int digits = FastDoubleSwar.tryToParseFourDigits(str, index + 1);
/*  75 */           if (digits < 0) {
/*     */             break;
/*     */           }
/*     */           
/*  79 */           significand = 10000L * significand + digits;
/*     */         } 
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     int significandEndIndex = index;
/*     */     
/*  88 */     if (integerDigitCount < 0) {
/*  89 */       digitCount = index - significandStartIndex;
/*  90 */       integerDigitCount = digitCount;
/*  91 */       exponent = 0;
/*     */     } else {
/*  93 */       digitCount = index - significandStartIndex - 1;
/*  94 */       exponent = integerDigitCount - digitCount;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  99 */     int expNumber = 0;
/* 100 */     if ((ch | 0x20) == 101) {
/* 101 */       ch = charAt(str, ++index, endIndex);
/* 102 */       boolean isExponentNegative = (ch == 45);
/* 103 */       if (isExponentNegative || ch == 43) {
/* 104 */         ch = charAt(str, ++index, endIndex);
/*     */       }
/* 106 */       int digit = (char)(ch - 48);
/* 107 */       i |= (digit >= 10) ? 1 : 0;
/*     */       
/*     */       while (true) {
/* 110 */         if (expNumber < 1024) {
/* 111 */           expNumber = 10 * expNumber + digit;
/*     */         }
/* 113 */         ch = charAt(str, ++index, endIndex);
/* 114 */         digit = (char)(ch - 48);
/* 115 */         if (digit >= 10) {
/* 116 */           if (isExponentNegative) {
/* 117 */             expNumber = -expNumber;
/*     */           }
/* 119 */           exponent += expNumber;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 124 */     if (i != 0 || index < endIndex || (!hasLeadingZero && digitCount == 0))
/*     */     {
/* 126 */       return 9221120237041090561L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     if (digitCount > 19) {
/* 134 */       int truncatedDigitCount = 0;
/* 135 */       significand = 0L;
/* 136 */       for (index = significandStartIndex; index < significandEndIndex; index++) {
/* 137 */         ch = str[index];
/* 138 */         int digit = (char)(ch - 48);
/* 139 */         if (digit < 10) {
/* 140 */           if (Long.compareUnsigned(significand, 1000000000000000000L) < 0) {
/* 141 */             significand = 10L * significand + digit;
/* 142 */             truncatedDigitCount++;
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/* 148 */       isSignificandTruncated = (index < significandEndIndex);
/* 149 */       exponentOfTruncatedSignificand = integerDigitCount - truncatedDigitCount + expNumber;
/*     */     } else {
/* 151 */       isSignificandTruncated = false;
/* 152 */       exponentOfTruncatedSignificand = 0;
/*     */     } 
/* 154 */     return valueOfFloatLiteral(str, offset, endIndex, isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*     */   }
/*     */   
/*     */   abstract long valueOfFloatLiteral(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean1, long paramLong, int paramInt3, boolean paramBoolean2, int paramInt4);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractJsonFloatingPointBitsFromByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */