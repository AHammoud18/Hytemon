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
/*     */ final class JavaBigIntegerFromCharArray
/*     */   extends AbstractBigIntegerParser
/*     */ {
/*     */   public BigInteger parseBigIntegerString(char[] str, int offset, int length, int radix) throws NumberFormatException {
/*     */     try {
/*  23 */       int endIndex = AbstractNumberParser.checkBounds(str.length, offset, length);
/*     */ 
/*     */ 
/*     */       
/*  27 */       int index = offset;
/*  28 */       char ch = str[index];
/*  29 */       boolean isNegative = (ch == '-');
/*  30 */       if (isNegative || ch == '+') {
/*  31 */         ch = charAt(str, ++index, endIndex);
/*  32 */         if (ch == '\000') {
/*  33 */           throw new NumberFormatException("illegal syntax");
/*     */         }
/*     */       } 
/*     */       
/*  37 */       switch (radix) {
/*     */         case 10:
/*  39 */           return parseDecDigits(str, index, endIndex, isNegative);
/*     */         case 16:
/*  41 */           return parseHexDigits(str, index, endIndex, isNegative);
/*     */       } 
/*  43 */       return new BigInteger(new String(str, offset, length), radix);
/*     */     }
/*  45 */     catch (ArithmeticException e) {
/*  46 */       NumberFormatException nfe = new NumberFormatException("value exceeds limits");
/*  47 */       nfe.initCause(e);
/*  48 */       throw nfe;
/*     */     } 
/*     */   }
/*     */   
/*     */   private BigInteger parseDecDigits(char[] str, int from, int to, boolean isNegative) {
/*  53 */     int i, numDigits = to - from;
/*  54 */     if (hasManyDigits(numDigits)) {
/*  55 */       return parseManyDecDigits(str, from, to, isNegative);
/*     */     }
/*  57 */     int preroll = from + (numDigits & 0x7);
/*  58 */     long significand = FastDoubleSwar.tryToParseUpTo7Digits(str, from, preroll);
/*  59 */     boolean success = (significand >= 0L);
/*  60 */     for (from = preroll; from < to; from += 8) {
/*  61 */       int addend = FastDoubleSwar.tryToParseEightDigits(str, from);
/*  62 */       i = success & ((addend >= 0) ? 1 : 0);
/*  63 */       significand = significand * 100000000L + addend;
/*     */     } 
/*  65 */     if (i == 0) {
/*  66 */       throw new NumberFormatException("illegal syntax");
/*     */     }
/*  68 */     return BigInteger.valueOf(isNegative ? -significand : significand);
/*     */   }
/*     */   private BigInteger parseHexDigits(char[] str, int from, int to, boolean isNegative) {
/*     */     int i;
/*  72 */     from = skipZeroes(str, from, to);
/*  73 */     int numDigits = to - from;
/*  74 */     if (numDigits <= 0) {
/*  75 */       return BigInteger.ZERO;
/*     */     }
/*  77 */     checkHexBigIntegerBounds(numDigits);
/*  78 */     byte[] bytes = new byte[(numDigits + 1 >> 1) + 1];
/*  79 */     int index = 1;
/*  80 */     boolean illegalDigits = false;
/*     */     
/*  82 */     if ((numDigits & 0x1) != 0) {
/*  83 */       char chLow = str[from++];
/*  84 */       int valueLow = lookupHex(chLow);
/*  85 */       bytes[index++] = (byte)valueLow;
/*  86 */       illegalDigits = (valueLow < 0);
/*     */     } 
/*  88 */     int prerollLimit = from + (to - from & 0x7);
/*  89 */     for (; from < prerollLimit; from += 2) {
/*  90 */       char chHigh = str[from];
/*  91 */       char chLow = str[from + 1];
/*  92 */       int valueHigh = lookupHex(chHigh);
/*  93 */       int valueLow = lookupHex(chLow);
/*  94 */       bytes[index++] = (byte)(valueHigh << 4 | valueLow);
/*  95 */       i = illegalDigits | ((valueHigh < 0 || valueLow < 0) ? 1 : 0);
/*     */     } 
/*  97 */     for (; from < to; from += 8, index += 4) {
/*  98 */       long value = FastDoubleSwar.tryToParseEightHexDigits(str, from);
/*  99 */       FastDoubleSwar.writeIntBE(bytes, index, (int)value);
/* 100 */       i |= (value < 0L) ? 1 : 0;
/*     */     } 
/* 102 */     if (i != 0) {
/* 103 */       throw new NumberFormatException("illegal syntax");
/*     */     }
/* 105 */     BigInteger result = new BigInteger(bytes);
/* 106 */     return isNegative ? result.negate() : result;
/*     */   }
/*     */   
/*     */   private BigInteger parseManyDecDigits(char[] str, int from, int to, boolean isNegative) {
/* 110 */     from = skipZeroes(str, from, to);
/* 111 */     int numDigits = to - from;
/* 112 */     checkDecBigIntegerBounds(numDigits);
/* 113 */     Map<Integer, BigInteger> powersOfTen = FastIntegerMath.fillPowersOf10Floor16(from, to);
/* 114 */     BigInteger result = ParseDigitsTaskCharArray.parseDigitsRecursive(str, from, to, powersOfTen, 400);
/* 115 */     return isNegative ? result.negate() : result;
/*     */   }
/*     */   
/*     */   private int skipZeroes(char[] str, int from, int to) {
/* 119 */     while (from < to - 8 && FastDoubleSwar.isEightZeroes(str, from)) {
/* 120 */       from += 8;
/*     */     }
/* 122 */     while (from < to && str[from] == '0') {
/* 123 */       from++;
/*     */     }
/* 125 */     return from;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaBigIntegerFromCharArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */