/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class FastIntegerMath
/*     */ {
/*  14 */   public static final BigInteger FIVE = BigInteger.valueOf(5L);
/*  15 */   static final BigInteger TEN_POW_16 = BigInteger.valueOf(10000000000000000L);
/*  16 */   static final BigInteger FIVE_POW_16 = BigInteger.valueOf(152587890625L);
/*  17 */   private static final BigInteger[] SMALL_POWERS_OF_TEN = new BigInteger[] { BigInteger.ONE, BigInteger.TEN, 
/*     */ 
/*     */       
/*  20 */       BigInteger.valueOf(100L), 
/*  21 */       BigInteger.valueOf(1000L), 
/*  22 */       BigInteger.valueOf(10000L), 
/*  23 */       BigInteger.valueOf(100000L), 
/*  24 */       BigInteger.valueOf(1000000L), 
/*  25 */       BigInteger.valueOf(10000000L), 
/*  26 */       BigInteger.valueOf(100000000L), 
/*  27 */       BigInteger.valueOf(1000000000L), 
/*  28 */       BigInteger.valueOf(10000000000L), 
/*  29 */       BigInteger.valueOf(100000000000L), 
/*  30 */       BigInteger.valueOf(1000000000000L), 
/*  31 */       BigInteger.valueOf(10000000000000L), 
/*  32 */       BigInteger.valueOf(100000000000000L), 
/*  33 */       BigInteger.valueOf(1000000000000000L) };
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
/*     */   static BigInteger computePowerOfTen(NavigableMap<Integer, BigInteger> powersOfTen, int n) {
/*  51 */     if (n < SMALL_POWERS_OF_TEN.length) {
/*  52 */       return SMALL_POWERS_OF_TEN[n];
/*     */     }
/*  54 */     if (powersOfTen != null) {
/*  55 */       Map.Entry<Integer, BigInteger> floorEntry = powersOfTen.floorEntry(Integer.valueOf(n));
/*  56 */       Integer floorN = floorEntry.getKey();
/*  57 */       if (floorN.intValue() == n) {
/*  58 */         return floorEntry.getValue();
/*     */       }
/*  60 */       return FftMultiplier.multiply(floorEntry.getValue(), computePowerOfTen(powersOfTen, n - floorN.intValue()));
/*     */     } 
/*     */     
/*  63 */     return FIVE.pow(n).shiftLeft(n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static BigInteger computeTenRaisedByNFloor16Recursive(NavigableMap<Integer, BigInteger> powersOfTen, int n) {
/*  70 */     n &= 0xFFFFFFF0;
/*  71 */     Map.Entry<Integer, BigInteger> floorEntry = powersOfTen.floorEntry(Integer.valueOf(n));
/*  72 */     int floorPower = ((Integer)floorEntry.getKey()).intValue();
/*  73 */     BigInteger floorValue = floorEntry.getValue();
/*  74 */     if (floorPower == n) {
/*  75 */       return floorValue;
/*     */     }
/*  77 */     int diff = n - floorPower;
/*  78 */     BigInteger diffValue = powersOfTen.get(Integer.valueOf(diff));
/*  79 */     if (diffValue == null) {
/*  80 */       diffValue = computeTenRaisedByNFloor16Recursive(powersOfTen, diff);
/*  81 */       powersOfTen.put(Integer.valueOf(diff), diffValue);
/*     */     } 
/*  83 */     return FftMultiplier.multiply(floorValue, diffValue);
/*     */   }
/*     */ 
/*     */   
/*     */   static NavigableMap<Integer, BigInteger> createPowersOfTenFloor16Map() {
/*  88 */     NavigableMap<Integer, BigInteger> powersOfTen = new TreeMap<>();
/*  89 */     powersOfTen.put(Integer.valueOf(0), BigInteger.ONE);
/*  90 */     powersOfTen.put(Integer.valueOf(16), TEN_POW_16);
/*  91 */     return powersOfTen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long estimateNumBits(long numDecimalDigits) {
/*  99 */     return (numDecimalDigits * 3402L >>> 10L) + 1L;
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
/*     */   static NavigableMap<Integer, BigInteger> fillPowersOf10Floor16(int from, int to) {
/* 111 */     NavigableMap<Integer, BigInteger> powers = new TreeMap<>();
/* 112 */     powers.put(Integer.valueOf(0), BigInteger.valueOf(5L));
/* 113 */     powers.put(Integer.valueOf(16), FIVE_POW_16);
/* 114 */     fillPowersOfNFloor16Recursive(powers, from, to);
/*     */ 
/*     */     
/* 117 */     for (Iterator<Map.Entry<Integer, BigInteger>> iterator = powers.entrySet().iterator(); iterator.hasNext(); ) {
/* 118 */       Map.Entry<Integer, BigInteger> e = iterator.next();
/* 119 */       e.setValue(((BigInteger)e.getValue()).shiftLeft(((Integer)e.getKey()).intValue()));
/*     */     } 
/*     */     
/* 122 */     return powers;
/*     */   }
/*     */   
/*     */   static void fillPowersOfNFloor16Recursive(NavigableMap<Integer, BigInteger> powersOfTen, int from, int to) {
/* 126 */     int numDigits = to - from;
/*     */     
/* 128 */     if (numDigits <= 18) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     int mid = splitFloor16(from, to);
/* 133 */     int n = to - mid;
/* 134 */     if (!powersOfTen.containsKey(Integer.valueOf(n))) {
/* 135 */       fillPowersOfNFloor16Recursive(powersOfTen, from, mid);
/* 136 */       fillPowersOfNFloor16Recursive(powersOfTen, mid, to);
/* 137 */       powersOfTen.put(Integer.valueOf(n), computeTenRaisedByNFloor16Recursive(powersOfTen, n));
/*     */     } 
/*     */   }
/*     */   
/*     */   static long unsignedMultiplyHigh(long x, long y) {
/* 142 */     long x0 = x & 0xFFFFFFFFL, x1 = x >>> 32L;
/* 143 */     long y0 = y & 0xFFFFFFFFL, y1 = y >>> 32L;
/* 144 */     long p11 = x1 * y1, p01 = x0 * y1;
/* 145 */     long p10 = x1 * y0, p00 = x0 * y0;
/*     */ 
/*     */     
/* 148 */     long middle = p10 + (p00 >>> 32L) + (p01 & 0xFFFFFFFFL);
/*     */ 
/*     */     
/* 151 */     return p11 + (middle >>> 32L) + (p01 >>> 32L);
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
/*     */   static int splitFloor16(int from, int to) {
/* 163 */     int range = to - from + 31 >>> 5 << 4;
/* 164 */     return to - range;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\FastIntegerMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */