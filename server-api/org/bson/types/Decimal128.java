/*     */ package org.bson.types;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.math.MathContext;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Decimal128
/*     */   extends Number
/*     */   implements Comparable<Decimal128>
/*     */ {
/*     */   private static final long serialVersionUID = 4570973266503637887L;
/*     */   private static final long INFINITY_MASK = 8646911284551352320L;
/*     */   private static final long NaN_MASK = 8935141660703064064L;
/*     */   private static final long SIGN_BIT_MASK = -9223372036854775808L;
/*     */   private static final int MIN_EXPONENT = -6176;
/*     */   private static final int MAX_EXPONENT = 6111;
/*     */   private static final int EXPONENT_OFFSET = 6176;
/*     */   private static final int MAX_BIT_LENGTH = 113;
/*  52 */   private static final BigInteger BIG_INT_TEN = new BigInteger("10");
/*  53 */   private static final BigInteger BIG_INT_ONE = new BigInteger("1");
/*  54 */   private static final BigInteger BIG_INT_ZERO = new BigInteger("0");
/*     */   
/*  56 */   private static final Set<String> NaN_STRINGS = new HashSet<>(Collections.singletonList("nan"));
/*  57 */   private static final Set<String> NEGATIVE_NaN_STRINGS = new HashSet<>(Collections.singletonList("-nan"));
/*  58 */   private static final Set<String> POSITIVE_INFINITY_STRINGS = new HashSet<>(Arrays.asList(new String[] { "inf", "+inf", "infinity", "+infinity" }));
/*  59 */   private static final Set<String> NEGATIVE_INFINITY_STRINGS = new HashSet<>(Arrays.asList(new String[] { "-inf", "-infinity" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final Decimal128 POSITIVE_INFINITY = fromIEEE754BIDEncoding(8646911284551352320L, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final Decimal128 NEGATIVE_INFINITY = fromIEEE754BIDEncoding(-576460752303423488L, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final Decimal128 NEGATIVE_NaN = fromIEEE754BIDEncoding(-288230376151711744L, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final Decimal128 NaN = fromIEEE754BIDEncoding(8935141660703064064L, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final Decimal128 POSITIVE_ZERO = fromIEEE754BIDEncoding(3476778912330022912L, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public static final Decimal128 NEGATIVE_ZERO = fromIEEE754BIDEncoding(-5746593124524752896L, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long high;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long low;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Decimal128 parse(String value) {
/* 111 */     String lowerCasedValue = value.toLowerCase();
/*     */     
/* 113 */     if (NaN_STRINGS.contains(lowerCasedValue)) {
/* 114 */       return NaN;
/*     */     }
/* 116 */     if (NEGATIVE_NaN_STRINGS.contains(lowerCasedValue)) {
/* 117 */       return NEGATIVE_NaN;
/*     */     }
/* 119 */     if (POSITIVE_INFINITY_STRINGS.contains(lowerCasedValue)) {
/* 120 */       return POSITIVE_INFINITY;
/*     */     }
/* 122 */     if (NEGATIVE_INFINITY_STRINGS.contains(lowerCasedValue)) {
/* 123 */       return NEGATIVE_INFINITY;
/*     */     }
/* 125 */     return new Decimal128(new BigDecimal(value), (value.charAt(0) == '-'));
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
/*     */   public static Decimal128 fromIEEE754BIDEncoding(long high, long low) {
/* 137 */     return new Decimal128(high, low);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal128(long value) {
/* 146 */     this(new BigDecimal(value, MathContext.DECIMAL128));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal128(BigDecimal value) {
/* 156 */     this(value, (value.signum() == -1));
/*     */   }
/*     */   
/*     */   private Decimal128(long high, long low) {
/* 160 */     this.high = high;
/* 161 */     this.low = low;
/*     */   }
/*     */ 
/*     */   
/*     */   private Decimal128(BigDecimal initialValue, boolean isNegative) {
/* 166 */     long localHigh = 0L;
/* 167 */     long localLow = 0L;
/*     */     
/* 169 */     BigDecimal value = clampAndRound(initialValue);
/*     */     
/* 171 */     long exponent = -value.scale();
/*     */     
/* 173 */     if (exponent < -6176L || exponent > 6111L) {
/* 174 */       throw new AssertionError("Exponent is out of range for Decimal128 encoding: " + exponent);
/*     */     }
/* 176 */     if (value.unscaledValue().bitLength() > 113) {
/* 177 */       throw new AssertionError("Unscaled roundedValue is out of range for Decimal128 encoding:" + value.unscaledValue());
/*     */     }
/*     */     
/* 180 */     BigInteger significand = value.unscaledValue().abs();
/* 181 */     int bitLength = significand.bitLength();
/*     */     int i;
/* 183 */     for (i = 0; i < Math.min(64, bitLength); i++) {
/* 184 */       if (significand.testBit(i)) {
/* 185 */         localLow |= 1L << i;
/*     */       }
/*     */     } 
/*     */     
/* 189 */     for (i = 64; i < bitLength; i++) {
/* 190 */       if (significand.testBit(i)) {
/* 191 */         localHigh |= 1L << i - 64;
/*     */       }
/*     */     } 
/*     */     
/* 195 */     long biasedExponent = exponent + 6176L;
/*     */     
/* 197 */     localHigh |= biasedExponent << 49L;
/*     */     
/* 199 */     if (value.signum() == -1 || isNegative) {
/* 200 */       localHigh |= Long.MIN_VALUE;
/*     */     }
/*     */     
/* 203 */     this.high = localHigh;
/* 204 */     this.low = localLow;
/*     */   }
/*     */   
/*     */   private BigDecimal clampAndRound(BigDecimal initialValue) {
/*     */     BigDecimal value;
/* 209 */     if (-initialValue.scale() > 6111) {
/* 210 */       int diff = -initialValue.scale() - 6111;
/* 211 */       if (initialValue.unscaledValue().equals(BIG_INT_ZERO))
/* 212 */       { value = new BigDecimal(initialValue.unscaledValue(), -6111); }
/* 213 */       else { if (diff + initialValue.precision() > 34) {
/* 214 */           throw new NumberFormatException("Exponent is out of range for Decimal128 encoding of " + initialValue);
/*     */         }
/* 216 */         BigInteger multiplier = BIG_INT_TEN.pow(diff);
/* 217 */         value = new BigDecimal(initialValue.unscaledValue().multiply(multiplier), initialValue.scale() + diff); }
/*     */     
/* 219 */     } else if (-initialValue.scale() < -6176) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       int diff = initialValue.scale() + -6176;
/* 225 */       int undiscardedPrecision = ensureExactRounding(initialValue, diff);
/* 226 */       BigInteger divisor = (undiscardedPrecision == 0) ? BIG_INT_ONE : BIG_INT_TEN.pow(diff);
/* 227 */       value = new BigDecimal(initialValue.unscaledValue().divide(divisor), initialValue.scale() - diff);
/*     */     } else {
/* 229 */       value = initialValue.round(MathContext.DECIMAL128);
/* 230 */       int extraPrecision = initialValue.precision() - value.precision();
/* 231 */       if (extraPrecision > 0)
/*     */       {
/* 233 */         ensureExactRounding(initialValue, extraPrecision);
/*     */       }
/*     */     } 
/* 236 */     return value;
/*     */   }
/*     */   
/*     */   private int ensureExactRounding(BigDecimal initialValue, int extraPrecision) {
/* 240 */     String significand = initialValue.unscaledValue().abs().toString();
/* 241 */     int undiscardedPrecision = Math.max(0, significand.length() - extraPrecision);
/* 242 */     for (int i = undiscardedPrecision; i < significand.length(); i++) {
/* 243 */       if (significand.charAt(i) != '0') {
/* 244 */         throw new NumberFormatException("Conversion to Decimal128 would require inexact rounding of " + initialValue);
/*     */       }
/*     */     } 
/* 247 */     return undiscardedPrecision;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getHigh() {
/* 257 */     return this.high;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLow() {
/* 267 */     return this.low;
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
/*     */   public BigDecimal bigDecimalValue() {
/* 279 */     if (isNaN()) {
/* 280 */       throw new ArithmeticException("NaN can not be converted to a BigDecimal");
/*     */     }
/*     */     
/* 283 */     if (isInfinite()) {
/* 284 */       throw new ArithmeticException("Infinity can not be converted to a BigDecimal");
/*     */     }
/*     */     
/* 287 */     BigDecimal bigDecimal = bigDecimalValueNoNegativeZeroCheck();
/*     */ 
/*     */     
/* 290 */     if (isNegative() && bigDecimal.signum() == 0) {
/* 291 */       throw new ArithmeticException("Negative zero can not be converted to a BigDecimal");
/*     */     }
/*     */     
/* 294 */     return bigDecimal;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasDifferentSign(BigDecimal bigDecimal) {
/* 299 */     return (isNegative() && bigDecimal.signum() == 0);
/*     */   }
/*     */   
/*     */   private boolean isZero(BigDecimal bigDecimal) {
/* 303 */     return (!isNaN() && !isInfinite() && bigDecimal.compareTo(BigDecimal.ZERO) == 0);
/*     */   }
/*     */   
/*     */   private BigDecimal bigDecimalValueNoNegativeZeroCheck() {
/* 307 */     int scale = -getExponent();
/*     */     
/* 309 */     if (twoHighestCombinationBitsAreSet()) {
/* 310 */       return BigDecimal.valueOf(0L, scale);
/*     */     }
/*     */     
/* 313 */     return new BigDecimal(new BigInteger(isNegative() ? -1 : 1, getBytes()), scale);
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] getBytes() {
/* 318 */     byte[] bytes = new byte[15];
/*     */     
/* 320 */     long mask = 255L; int i;
/* 321 */     for (i = 14; i >= 7; i--) {
/* 322 */       bytes[i] = (byte)(int)((this.low & mask) >>> 14 - i << 3);
/* 323 */       mask <<= 8L;
/*     */     } 
/*     */     
/* 326 */     mask = 255L;
/* 327 */     for (i = 6; i >= 1; i--) {
/* 328 */       bytes[i] = (byte)(int)((this.high & mask) >>> 6 - i << 3);
/* 329 */       mask <<= 8L;
/*     */     } 
/*     */     
/* 332 */     mask = 281474976710656L;
/* 333 */     bytes[0] = (byte)(int)((this.high & mask) >>> 48L);
/* 334 */     return bytes;
/*     */   }
/*     */   
/*     */   private int getExponent() {
/* 338 */     if (twoHighestCombinationBitsAreSet()) {
/* 339 */       return (int)((this.high & 0x1FFFE00000000000L) >>> 47L) - 6176;
/*     */     }
/* 341 */     return (int)((this.high & 0x7FFF800000000000L) >>> 49L) - 6176;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean twoHighestCombinationBitsAreSet() {
/* 346 */     return ((this.high & 0x6000000000000000L) == 6917529027641081856L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNegative() {
/* 355 */     return ((this.high & Long.MIN_VALUE) == Long.MIN_VALUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfinite() {
/* 364 */     return ((this.high & 0x7800000000000000L) == 8646911284551352320L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFinite() {
/* 373 */     return !isInfinite();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/* 382 */     return ((this.high & 0x7C00000000000000L) == 8935141660703064064L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Decimal128 o) {
/* 388 */     if (isNaN()) {
/* 389 */       return o.isNaN() ? 0 : 1;
/*     */     }
/* 391 */     if (isInfinite()) {
/* 392 */       if (isNegative()) {
/* 393 */         if (o.isInfinite() && o.isNegative()) {
/* 394 */           return 0;
/*     */         }
/* 396 */         return -1;
/*     */       } 
/*     */       
/* 399 */       if (o.isNaN())
/* 400 */         return -1; 
/* 401 */       if (o.isInfinite() && !o.isNegative()) {
/* 402 */         return 0;
/*     */       }
/* 404 */       return 1;
/*     */     } 
/*     */ 
/*     */     
/* 408 */     BigDecimal bigDecimal = bigDecimalValueNoNegativeZeroCheck();
/* 409 */     BigDecimal otherBigDecimal = o.bigDecimalValueNoNegativeZeroCheck();
/*     */     
/* 411 */     if (isZero(bigDecimal) && o.isZero(otherBigDecimal)) {
/* 412 */       if (hasDifferentSign(bigDecimal)) {
/* 413 */         if (o.hasDifferentSign(otherBigDecimal)) {
/* 414 */           return 0;
/*     */         }
/*     */         
/* 417 */         return -1;
/*     */       } 
/* 419 */       if (o.hasDifferentSign(otherBigDecimal)) {
/* 420 */         return 1;
/*     */       }
/*     */     } 
/*     */     
/* 424 */     if (o.isNaN())
/* 425 */       return -1; 
/* 426 */     if (o.isInfinite()) {
/* 427 */       if (o.isNegative()) {
/* 428 */         return 1;
/*     */       }
/* 430 */       return -1;
/*     */     } 
/*     */     
/* 433 */     return bigDecimal.compareTo(otherBigDecimal);
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
/*     */   public int intValue() {
/* 450 */     return (int)doubleValue();
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
/*     */   public long longValue() {
/* 466 */     return (long)doubleValue();
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
/*     */   public float floatValue() {
/* 481 */     return (float)doubleValue();
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
/*     */   public double doubleValue() {
/* 496 */     if (isNaN()) {
/* 497 */       return Double.NaN;
/*     */     }
/* 499 */     if (isInfinite()) {
/* 500 */       if (isNegative()) {
/* 501 */         return Double.NEGATIVE_INFINITY;
/*     */       }
/* 503 */       return Double.POSITIVE_INFINITY;
/*     */     } 
/*     */ 
/*     */     
/* 507 */     BigDecimal bigDecimal = bigDecimalValueNoNegativeZeroCheck();
/*     */     
/* 509 */     if (hasDifferentSign(bigDecimal)) {
/* 510 */       return -0.0D;
/*     */     }
/*     */     
/* 513 */     return bigDecimal.doubleValue();
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
/*     */   public boolean equals(Object o) {
/* 532 */     if (this == o) {
/* 533 */       return true;
/*     */     }
/* 535 */     if (o == null || getClass() != o.getClass()) {
/* 536 */       return false;
/*     */     }
/*     */     
/* 539 */     Decimal128 that = (Decimal128)o;
/*     */     
/* 541 */     if (this.high != that.high) {
/* 542 */       return false;
/*     */     }
/* 544 */     if (this.low != that.low) {
/* 545 */       return false;
/*     */     }
/*     */     
/* 548 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 553 */     int result = (int)(this.low ^ this.low >>> 32L);
/* 554 */     result = 31 * result + (int)(this.high ^ this.high >>> 32L);
/* 555 */     return result;
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
/*     */   public String toString() {
/* 567 */     if (isNaN()) {
/* 568 */       return "NaN";
/*     */     }
/* 570 */     if (isInfinite()) {
/* 571 */       if (isNegative()) {
/* 572 */         return "-Infinity";
/*     */       }
/* 574 */       return "Infinity";
/*     */     } 
/*     */     
/* 577 */     return toStringWithBigDecimal();
/*     */   }
/*     */   
/*     */   private String toStringWithBigDecimal() {
/* 581 */     StringBuilder buffer = new StringBuilder();
/*     */     
/* 583 */     BigDecimal bigDecimal = bigDecimalValueNoNegativeZeroCheck();
/* 584 */     String significand = bigDecimal.unscaledValue().abs().toString();
/*     */     
/* 586 */     if (isNegative()) {
/* 587 */       buffer.append('-');
/*     */     }
/*     */     
/* 590 */     int exponent = -bigDecimal.scale();
/* 591 */     int adjustedExponent = exponent + significand.length() - 1;
/* 592 */     if (exponent <= 0 && adjustedExponent >= -6) {
/* 593 */       if (exponent == 0) {
/* 594 */         buffer.append(significand);
/*     */       } else {
/* 596 */         int pad = -exponent - significand.length();
/* 597 */         if (pad >= 0) {
/* 598 */           buffer.append('0');
/* 599 */           buffer.append('.');
/* 600 */           for (int i = 0; i < pad; i++) {
/* 601 */             buffer.append('0');
/*     */           }
/* 603 */           buffer.append(significand, 0, significand.length());
/*     */         } else {
/* 605 */           buffer.append(significand, 0, -pad);
/* 606 */           buffer.append('.');
/* 607 */           buffer.append(significand, -pad, -pad - exponent);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 611 */       buffer.append(significand.charAt(0));
/* 612 */       if (significand.length() > 1) {
/* 613 */         buffer.append('.');
/* 614 */         buffer.append(significand, 1, significand.length());
/*     */       } 
/* 616 */       buffer.append('E');
/* 617 */       if (adjustedExponent > 0) {
/* 618 */         buffer.append('+');
/*     */       }
/* 620 */       buffer.append(adjustedExponent);
/*     */     } 
/* 622 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\Decimal128.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */