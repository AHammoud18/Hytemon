/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.nio.charset.StandardCharsets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class JavaBigIntegerFromByteArray
/*     */   extends AbstractBigIntegerParser
/*     */ {
/*     */   public BigInteger parseBigIntegerString(byte[] str, int offset, int length, int radix) throws NumberFormatException {
/*     */     try {
/*  28 */       int endIndex = AbstractNumberParser.checkBounds(str.length, offset, length);
/*     */ 
/*     */ 
/*     */       
/*  32 */       int index = offset;
/*  33 */       byte ch = str[index];
/*  34 */       boolean isNegative = (ch == 45);
/*  35 */       if (isNegative || ch == 43) {
/*  36 */         ch = charAt(str, ++index, endIndex);
/*  37 */         if (ch == 0) {
/*  38 */           throw new NumberFormatException("illegal syntax");
/*     */         }
/*     */       } 
/*  41 */       switch (radix) {
/*     */         case 10:
/*  43 */           return parseDecDigits(str, index, endIndex, isNegative);
/*     */         case 16:
/*  45 */           return parseHexDigits(str, index, endIndex, isNegative);
/*     */       } 
/*  47 */       return new BigInteger(new String(str, offset, length, StandardCharsets.ISO_8859_1), radix);
/*     */     }
/*  49 */     catch (ArithmeticException e) {
/*  50 */       NumberFormatException nfe = new NumberFormatException("value exceeds limits");
/*  51 */       nfe.initCause(e);
/*  52 */       throw nfe;
/*     */     } 
/*     */   }
/*     */   
/*     */   private BigInteger parseDecDigits(byte[] str, int from, int to, boolean isNegative) {
/*  57 */     int i, numDigits = to - from;
/*  58 */     if (hasManyDigits(numDigits)) {
/*  59 */       return parseManyDecDigits(str, from, to, isNegative);
/*     */     }
/*  61 */     int preroll = from + (numDigits & 0x7);
/*  62 */     long significand = FastDoubleSwar.tryToParseUpTo7Digits(str, from, preroll);
/*  63 */     boolean success = (significand >= 0L);
/*  64 */     for (from = preroll; from < to; from += 8) {
/*  65 */       int addend = FastDoubleSwar.tryToParseEightDigitsUtf8(str, from);
/*  66 */       i = success & ((addend >= 0) ? 1 : 0);
/*  67 */       significand = significand * 100000000L + addend;
/*     */     } 
/*  69 */     if (i == 0) {
/*  70 */       throw new NumberFormatException("illegal syntax");
/*     */     }
/*  72 */     return BigInteger.valueOf(isNegative ? -significand : significand);
/*     */   }
/*     */   private BigInteger parseHexDigits(byte[] str, int from, int to, boolean isNegative) {
/*     */     int i;
/*  76 */     from = skipZeroes(str, from, to);
/*  77 */     int numDigits = to - from;
/*  78 */     if (numDigits <= 0) {
/*  79 */       return BigInteger.ZERO;
/*     */     }
/*  81 */     checkHexBigIntegerBounds(numDigits);
/*  82 */     byte[] bytes = new byte[(numDigits + 1 >> 1) + 1];
/*  83 */     int index = 1;
/*  84 */     boolean illegalDigits = false;
/*     */     
/*  86 */     if ((numDigits & 0x1) != 0) {
/*  87 */       byte chLow = str[from++];
/*  88 */       int valueLow = lookupHex(chLow);
/*  89 */       bytes[index++] = (byte)valueLow;
/*  90 */       illegalDigits = (valueLow < 0);
/*     */     } 
/*  92 */     int prerollLimit = from + (to - from & 0x7);
/*  93 */     for (; from < prerollLimit; from += 2) {
/*  94 */       byte chHigh = str[from];
/*  95 */       byte chLow = str[from + 1];
/*  96 */       int valueHigh = lookupHex(chHigh);
/*  97 */       int valueLow = lookupHex(chLow);
/*  98 */       bytes[index++] = (byte)(valueHigh << 4 | valueLow);
/*  99 */       i = illegalDigits | ((valueHigh < 0 || valueLow < 0) ? 1 : 0);
/*     */     } 
/* 101 */     for (; from < to; from += 8, index += 4) {
/* 102 */       long value = FastDoubleSwar.tryToParseEightHexDigits(str, from);
/* 103 */       FastDoubleSwar.writeIntBE(bytes, index, (int)value);
/* 104 */       i |= (value < 0L) ? 1 : 0;
/*     */     } 
/* 106 */     if (i != 0) {
/* 107 */       throw new NumberFormatException("illegal syntax");
/*     */     }
/* 109 */     BigInteger result = new BigInteger(bytes);
/* 110 */     return isNegative ? result.negate() : result;
/*     */   }
/*     */   
/*     */   private BigInteger parseManyDecDigits(byte[] str, int from, int to, boolean isNegative) {
/* 114 */     from = skipZeroes(str, from, to);
/* 115 */     int numDigits = to - from;
/* 116 */     checkDecBigIntegerBounds(numDigits);
/* 117 */     Map<Integer, BigInteger> powersOfTen = FastIntegerMath.fillPowersOf10Floor16(from, to);
/* 118 */     BigInteger result = ParseDigitsTaskByteArray.parseDigitsRecursive(str, from, to, powersOfTen, 400);
/* 119 */     return isNegative ? result.negate() : result;
/*     */   }
/*     */   
/*     */   private int skipZeroes(byte[] str, int from, int to) {
/* 123 */     while (from < to - 8 && FastDoubleSwar.isEightZeroes(str, from)) {
/* 124 */       from += 8;
/*     */     }
/* 126 */     while (from < to && str[from] == 48) {
/* 127 */       from++;
/*     */     }
/* 129 */     return from;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaBigIntegerFromByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */