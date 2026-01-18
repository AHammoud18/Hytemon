/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.Map;
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
/*     */ final class JavaBigIntegerFromCharSequence
/*     */   extends AbstractBigIntegerParser
/*     */ {
/*     */   public BigInteger parseBigIntegerString(CharSequence str, int offset, int length, int radix) throws NumberFormatException {
/*     */     try {
/*  24 */       int size = str.length();
/*  25 */       int endIndex = AbstractNumberParser.checkBounds(size, offset, length);
/*     */ 
/*     */ 
/*     */       
/*  29 */       int index = offset;
/*  30 */       char ch = str.charAt(index);
/*  31 */       boolean isNegative = (ch == '-');
/*  32 */       if (isNegative || ch == '+') {
/*  33 */         ch = charAt(str, ++index, endIndex);
/*  34 */         if (ch == '\000') {
/*  35 */           throw new NumberFormatException("illegal syntax");
/*     */         }
/*     */       } 
/*     */       
/*  39 */       switch (radix) {
/*     */         case 10:
/*  41 */           return parseDecDigits(str, index, endIndex, isNegative);
/*     */         case 16:
/*  43 */           return parseHexDigits(str, index, endIndex, isNegative);
/*     */       } 
/*  45 */       return new BigInteger(str.subSequence(offset, length).toString(), radix);
/*     */     }
/*  47 */     catch (ArithmeticException e) {
/*  48 */       NumberFormatException nfe = new NumberFormatException("value exceeds limits");
/*  49 */       nfe.initCause(e);
/*  50 */       throw nfe;
/*     */     } 
/*     */   }
/*     */   
/*     */   private BigInteger parseDecDigits(CharSequence str, int from, int to, boolean isNegative) {
/*  55 */     int i, numDigits = to - from;
/*  56 */     if (hasManyDigits(numDigits)) {
/*  57 */       return parseManyDecDigits(str, from, to, isNegative);
/*     */     }
/*  59 */     int preroll = from + (numDigits & 0x7);
/*  60 */     long significand = FastDoubleSwar.tryToParseUpTo7Digits(str, from, preroll);
/*  61 */     boolean success = (significand >= 0L);
/*  62 */     for (from = preroll; from < to; from += 8) {
/*  63 */       int addend = FastDoubleSwar.tryToParseEightDigits(str, from);
/*  64 */       i = success & ((addend >= 0) ? 1 : 0);
/*  65 */       significand = significand * 100000000L + addend;
/*     */     } 
/*  67 */     if (i == 0) {
/*  68 */       throw new NumberFormatException("illegal syntax");
/*     */     }
/*  70 */     return BigInteger.valueOf(isNegative ? -significand : significand);
/*     */   }
/*     */   private BigInteger parseHexDigits(CharSequence str, int from, int to, boolean isNegative) {
/*     */     int i;
/*  74 */     from = skipZeroes(str, from, to);
/*  75 */     int numDigits = to - from;
/*  76 */     if (numDigits <= 0) {
/*  77 */       return BigInteger.ZERO;
/*     */     }
/*  79 */     checkHexBigIntegerBounds(numDigits);
/*  80 */     byte[] bytes = new byte[(numDigits + 1 >> 1) + 1];
/*  81 */     int index = 1;
/*  82 */     boolean illegalDigits = false;
/*  83 */     if ((numDigits & 0x1) != 0) {
/*  84 */       char chLow = str.charAt(from++);
/*  85 */       int valueLow = lookupHex(chLow);
/*  86 */       bytes[index++] = (byte)valueLow;
/*  87 */       illegalDigits = (valueLow < 0);
/*     */     } 
/*  89 */     int prerollLimit = from + (to - from & 0x7);
/*  90 */     for (; from < prerollLimit; from += 2) {
/*  91 */       char chHigh = str.charAt(from);
/*  92 */       char chLow = str.charAt(from + 1);
/*  93 */       int valueHigh = lookupHex(chHigh);
/*  94 */       int valueLow = lookupHex(chLow);
/*  95 */       bytes[index++] = (byte)(valueHigh << 4 | valueLow);
/*  96 */       i = illegalDigits | ((valueLow < 0 || valueHigh < 0) ? 1 : 0);
/*     */     } 
/*  98 */     for (; from < to; from += 8, index += 4) {
/*  99 */       long value = FastDoubleSwar.tryToParseEightHexDigits(str, from);
/* 100 */       FastDoubleSwar.writeIntBE(bytes, index, (int)value);
/* 101 */       i |= (value < 0L) ? 1 : 0;
/*     */     } 
/* 103 */     if (i != 0) {
/* 104 */       throw new NumberFormatException("illegal syntax");
/*     */     }
/* 106 */     BigInteger result = new BigInteger(bytes);
/* 107 */     return isNegative ? result.negate() : result;
/*     */   }
/*     */   
/*     */   private BigInteger parseManyDecDigits(CharSequence str, int from, int to, boolean isNegative) {
/* 111 */     from = skipZeroes(str, from, to);
/* 112 */     int numDigits = to - from;
/* 113 */     checkDecBigIntegerBounds(numDigits);
/* 114 */     Map<Integer, BigInteger> powersOfTen = FastIntegerMath.fillPowersOf10Floor16(from, to);
/* 115 */     BigInteger result = ParseDigitsTaskCharSequence.parseDigitsRecursive(str, from, to, powersOfTen, 400);
/* 116 */     return isNegative ? result.negate() : result;
/*     */   }
/*     */   
/*     */   private int skipZeroes(CharSequence str, int from, int to) {
/* 120 */     for (; from < to && str.charAt(from) == '0'; from++);
/* 121 */     return from;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaBigIntegerFromCharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */