/*      */ package ch.randelshofer.fastdoubleparser;
/*      */ 
/*      */ import java.math.BigInteger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class FftMultiplier
/*      */ {
/*   29 */   public static final double COS_0_25 = Math.cos(0.7853981633974483D);
/*   30 */   public static final double SIN_0_25 = Math.sin(0.7853981633974483D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int FFT_THRESHOLD = 33220;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_MAG_LENGTH = 67108864;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ROOTS3_CACHE_SIZE = 20;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ROOTS_CACHE2_SIZE = 20;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int TOOM_COOK_THRESHOLD = 1920;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   61 */   private static volatile ComplexVector[] ROOTS2_CACHE = new ComplexVector[20];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   67 */   private static volatile ComplexVector[] ROOTS3_CACHE = new ComplexVector[20];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int bitsPerFftPoint(int bitLen) {
/*   77 */     if (bitLen <= 9728) {
/*   78 */       return 19;
/*      */     }
/*   80 */     if (bitLen <= 18432) {
/*   81 */       return 18;
/*      */     }
/*   83 */     if (bitLen <= 69632) {
/*   84 */       return 17;
/*      */     }
/*   86 */     if (bitLen <= 262144) {
/*   87 */       return 16;
/*      */     }
/*   89 */     if (bitLen <= 983040) {
/*   90 */       return 15;
/*      */     }
/*   92 */     if (bitLen <= 3670016) {
/*   93 */       return 14;
/*      */     }
/*   95 */     if (bitLen <= 13631488) {
/*   96 */       return 13;
/*      */     }
/*   98 */     if (bitLen <= 25165824) {
/*   99 */       return 12;
/*      */     }
/*  101 */     if (bitLen <= 92274688) {
/*  102 */       return 11;
/*      */     }
/*  104 */     if (bitLen <= 335544320) {
/*  105 */       return 10;
/*      */     }
/*  107 */     if (bitLen <= 1207959552) {
/*  108 */       return 9;
/*      */     }
/*  110 */     return 8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ComplexVector calculateRootsOfUnity(int n) {
/*  120 */     if (n == 1) {
/*  121 */       ComplexVector v = new ComplexVector(1);
/*  122 */       v.real(0, 1.0D);
/*  123 */       v.imag(0, 0.0D);
/*  124 */       return v;
/*      */     } 
/*  126 */     ComplexVector roots = new ComplexVector(n);
/*  127 */     roots.set(0, 1.0D, 0.0D);
/*  128 */     double cos = COS_0_25;
/*  129 */     double sin = SIN_0_25;
/*  130 */     roots.set(n / 2, cos, sin);
/*  131 */     double angleTerm = 1.5707963267948966D / n;
/*  132 */     for (int i = 1; i < n / 2; i++) {
/*  133 */       double angle = angleTerm * i;
/*  134 */       cos = Math.cos(angle);
/*  135 */       sin = Math.sin(angle);
/*  136 */       roots.set(i, cos, sin);
/*  137 */       roots.set(n - i, sin, cos);
/*      */     } 
/*  139 */     return roots;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void fft(ComplexVector a, ComplexVector[] roots) {
/*  154 */     int n = a.length;
/*  155 */     int logN = 31 - Integer.numberOfLeadingZeros(n);
/*  156 */     MutableComplex a0 = new MutableComplex();
/*  157 */     MutableComplex a1 = new MutableComplex();
/*  158 */     MutableComplex a2 = new MutableComplex();
/*  159 */     MutableComplex a3 = new MutableComplex();
/*      */ 
/*      */     
/*  162 */     MutableComplex omega1 = new MutableComplex();
/*  163 */     MutableComplex omega2 = new MutableComplex();
/*  164 */     int s = logN;
/*  165 */     for (; s >= 2; s -= 2) {
/*  166 */       ComplexVector rootsS = roots[s - 2];
/*  167 */       int m = 1 << s; int i;
/*  168 */       for (i = 0; i < n; i += m) {
/*  169 */         for (int j = 0; j < m / 4; j++) {
/*  170 */           omega1.set(rootsS, j);
/*      */ 
/*      */ 
/*      */           
/*  174 */           omega1.squareInto(omega2);
/*      */           
/*  176 */           int idx0 = i + j;
/*  177 */           int idx1 = i + j + m / 4;
/*  178 */           int idx2 = i + j + m / 2;
/*  179 */           int idx3 = i + j + m * 3 / 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  187 */           a.addInto(idx0, a, idx1, a0);
/*  188 */           a0.add(a, idx2);
/*  189 */           a0.add(a, idx3);
/*      */           
/*  191 */           a.subtractTimesIInto(idx0, a, idx1, a1);
/*  192 */           a1.subtract(a, idx2);
/*  193 */           a1.addTimesI(a, idx3);
/*  194 */           a1.multiplyConjugate(omega1);
/*      */           
/*  196 */           a.subtractInto(idx0, a, idx1, a2);
/*  197 */           a2.add(a, idx2);
/*  198 */           a2.subtract(a, idx3);
/*  199 */           a2.multiplyConjugate(omega2);
/*      */           
/*  201 */           a.addTimesIInto(idx0, a, idx1, a3);
/*  202 */           a3.subtract(a, idx2);
/*  203 */           a3.subtractTimesI(a, idx3);
/*  204 */           a3.multiply(omega1);
/*      */           
/*  206 */           a0.copyInto(a, idx0);
/*  207 */           a1.copyInto(a, idx1);
/*  208 */           a2.copyInto(a, idx2);
/*  209 */           a3.copyInto(a, idx3);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  215 */     if (s > 0) {
/*  216 */       for (int i = 0; i < n; i += 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  223 */         a.copyInto(i, a0);
/*  224 */         a.copyInto(i + 1, a1);
/*  225 */         a.add(i, a1);
/*  226 */         a0.subtractInto(a1, a, i + 1);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void fft3(ComplexVector a0, ComplexVector a1, ComplexVector a2, int sign, double scale) {
/*  242 */     double omegaImag = sign * -0.5D * Math.sqrt(3.0D);
/*  243 */     for (int i = 0; i < a0.length; i++) {
/*  244 */       double a0Real = a0.real(i) + a1.real(i) + a2.real(i);
/*  245 */       double a0Imag = a0.imag(i) + a1.imag(i) + a2.imag(i);
/*  246 */       double c = omegaImag * (a2.imag(i) - a1.imag(i));
/*  247 */       double d = omegaImag * (a1.real(i) - a2.real(i));
/*  248 */       double e = 0.5D * (a1.real(i) + a2.real(i));
/*  249 */       double f = 0.5D * (a1.imag(i) + a2.imag(i));
/*  250 */       double a1Real = a0.real(i) - e + c;
/*  251 */       double a1Imag = a0.imag(i) + d - f;
/*  252 */       double a2Real = a0.real(i) - e - c;
/*  253 */       double a2Imag = a0.imag(i) - d - f;
/*  254 */       a0.real(i, a0Real * scale);
/*  255 */       a0.imag(i, a0Imag * scale);
/*  256 */       a1.real(i, a1Real * scale);
/*  257 */       a1.imag(i, a1Imag * scale);
/*  258 */       a2.real(i, a2Real * scale);
/*  259 */       a2.imag(i, a2Imag * scale);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void fftMixedRadix(ComplexVector a, ComplexVector[] roots2, ComplexVector roots3) {
/*  280 */     int oneThird = a.length / 3;
/*  281 */     ComplexVector a0 = new ComplexVector(a, 0, oneThird);
/*  282 */     ComplexVector a1 = new ComplexVector(a, oneThird, oneThird * 2);
/*  283 */     ComplexVector a2 = new ComplexVector(a, oneThird * 2, a.length);
/*      */ 
/*      */     
/*  286 */     fft3(a0, a1, a2, 1, 1.0D);
/*      */ 
/*      */     
/*  289 */     MutableComplex omega = new MutableComplex(); int i;
/*  290 */     for (i = 0; i < a.length / 4; i++) {
/*  291 */       omega.set(roots3, i);
/*      */       
/*  293 */       a1.multiplyConjugate(i, omega);
/*  294 */       a2.multiplyConjugate(i, omega);
/*  295 */       a2.multiplyConjugate(i, omega);
/*      */     } 
/*  297 */     for (i = a.length / 4; i < oneThird; i++) {
/*  298 */       omega.set(roots3, i - a.length / 4);
/*      */       
/*  300 */       a1.multiplyConjugateTimesI(i, omega);
/*  301 */       a2.multiplyConjugateTimesI(i, omega);
/*  302 */       a2.multiplyConjugateTimesI(i, omega);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  308 */     fft(a0, roots2);
/*  309 */     fft(a1, roots2);
/*  310 */     fft(a2, roots2);
/*      */   }
/*      */   
/*      */   static BigInteger fromFftVector(ComplexVector fftVec, int signum, int bitsPerFftPoint) {
/*  314 */     assert bitsPerFftPoint <= 25 : bitsPerFftPoint + " does not fit into an int with slack";
/*      */     
/*  316 */     int fftLen = (int)Math.min(fftVec.length, 2147483648L / bitsPerFftPoint + 1L);
/*  317 */     int magLen = (int)(8L * (fftLen * bitsPerFftPoint + 31L) / 32L);
/*  318 */     byte[] mag = new byte[magLen];
/*  319 */     int base = 1 << bitsPerFftPoint;
/*  320 */     int bitMask = base - 1;
/*  321 */     int bitPadding = 32 - bitsPerFftPoint;
/*  322 */     long carry = 0L;
/*  323 */     int bitLength = mag.length * 8;
/*  324 */     int bitIdx = bitLength - bitsPerFftPoint;
/*  325 */     int magComponent = 0;
/*  326 */     int prevIdx = Math.min(Math.max(0, bitIdx >> 3), mag.length - 4);
/*  327 */     for (int part = 0; part <= 1; part++) {
/*  328 */       for (int fftIdx = 0; fftIdx < fftLen; fftIdx++) {
/*  329 */         long fftElem = Math.round(fftVec.part(fftIdx, part)) + carry;
/*  330 */         carry = fftElem >> bitsPerFftPoint;
/*      */         
/*  332 */         int idx = Math.min(Math.max(0, bitIdx >> 3), mag.length - 4);
/*  333 */         magComponent >>>= prevIdx - idx << 3;
/*  334 */         int shift = bitPadding - bitIdx + (idx << 3);
/*  335 */         magComponent |= (int)((fftElem & bitMask) << shift);
/*  336 */         FastDoubleSwar.writeIntBE(mag, idx, magComponent);
/*      */         
/*  338 */         prevIdx = idx;
/*  339 */         bitIdx -= bitsPerFftPoint;
/*      */       } 
/*      */     } 
/*  342 */     return new BigInteger(signum, mag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ComplexVector[] getRootsOfUnity2(int logN) {
/*  352 */     ComplexVector[] roots = new ComplexVector[logN + 1];
/*  353 */     for (int i = logN; i >= 0; i -= 2) {
/*  354 */       if (i < 20) {
/*  355 */         if (ROOTS2_CACHE[i] == null) {
/*  356 */           ROOTS2_CACHE[i] = calculateRootsOfUnity(1 << i);
/*      */         }
/*  358 */         roots[i] = ROOTS2_CACHE[i];
/*      */       } else {
/*  360 */         roots[i] = calculateRootsOfUnity(1 << i);
/*      */       } 
/*      */     } 
/*  363 */     return roots;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ComplexVector getRootsOfUnity3(int logN) {
/*  373 */     if (logN < 20) {
/*  374 */       if (ROOTS3_CACHE[logN] == null) {
/*  375 */         ROOTS3_CACHE[logN] = calculateRootsOfUnity(3 << logN);
/*      */       }
/*  377 */       return ROOTS3_CACHE[logN];
/*      */     } 
/*  379 */     return calculateRootsOfUnity(3 << logN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void ifft(ComplexVector a, ComplexVector[] roots) {
/*  395 */     int n = a.length;
/*  396 */     int logN = 31 - Integer.numberOfLeadingZeros(n);
/*  397 */     MutableComplex a0 = new MutableComplex();
/*  398 */     MutableComplex a1 = new MutableComplex();
/*  399 */     MutableComplex a2 = new MutableComplex();
/*  400 */     MutableComplex a3 = new MutableComplex();
/*  401 */     MutableComplex b0 = new MutableComplex();
/*  402 */     MutableComplex b1 = new MutableComplex();
/*  403 */     MutableComplex b2 = new MutableComplex();
/*  404 */     MutableComplex b3 = new MutableComplex();
/*      */     
/*  406 */     int s = 1;
/*      */     
/*  408 */     if (logN % 2 != 0) {
/*  409 */       for (int j = 0; j < n; j += 2) {
/*      */         
/*  411 */         a.copyInto(j + 1, a2);
/*  412 */         a.copyInto(j, a0);
/*  413 */         a.add(j, a2);
/*  414 */         a0.subtractInto(a2, a, j + 1);
/*      */       } 
/*  416 */       s++;
/*      */     } 
/*      */ 
/*      */     
/*  420 */     MutableComplex omega1 = new MutableComplex();
/*  421 */     MutableComplex omega2 = new MutableComplex();
/*  422 */     for (; s <= logN; s += 2) {
/*  423 */       ComplexVector rootsS = roots[s - 1];
/*  424 */       int m = 1 << s + 1; int j;
/*  425 */       for (j = 0; j < n; j += m) {
/*  426 */         for (int k = 0; k < m / 4; k++) {
/*  427 */           omega1.set(rootsS, k);
/*      */ 
/*      */ 
/*      */           
/*  431 */           omega1.squareInto(omega2);
/*      */           
/*  433 */           int idx0 = j + k;
/*  434 */           int idx1 = j + k + m / 4;
/*  435 */           int idx2 = j + k + m / 2;
/*  436 */           int idx3 = j + k + m * 3 / 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  444 */           a.copyInto(idx0, a0);
/*  445 */           a.multiplyInto(idx1, omega1, a1);
/*  446 */           a.multiplyInto(idx2, omega2, a2);
/*  447 */           a.multiplyConjugateInto(idx3, omega1, a3);
/*      */           
/*  449 */           a0.addInto(a1, b0);
/*  450 */           b0.add(a2);
/*  451 */           b0.add(a3);
/*      */           
/*  453 */           a0.addTimesIInto(a1, b1);
/*  454 */           b1.subtract(a2);
/*  455 */           b1.subtractTimesI(a3);
/*      */           
/*  457 */           a0.subtractInto(a1, b2);
/*  458 */           b2.add(a2);
/*  459 */           b2.subtract(a3);
/*      */           
/*  461 */           a0.subtractTimesIInto(a1, b3);
/*  462 */           b3.subtract(a2);
/*  463 */           b3.addTimesI(a3);
/*      */           
/*  465 */           b0.copyInto(a, idx0);
/*  466 */           b1.copyInto(a, idx1);
/*  467 */           b2.copyInto(a, idx2);
/*  468 */           b3.copyInto(a, idx3);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  474 */     for (int i = 0; i < n; i++) {
/*  475 */       a.timesTwoToThe(i, -logN);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void ifftMixedRadix(ComplexVector a, ComplexVector[] roots2, ComplexVector roots3) {
/*  496 */     int oneThird = a.length / 3;
/*  497 */     ComplexVector a0 = new ComplexVector(a, 0, oneThird);
/*  498 */     ComplexVector a1 = new ComplexVector(a, oneThird, oneThird * 2);
/*  499 */     ComplexVector a2 = new ComplexVector(a, oneThird * 2, a.length);
/*      */ 
/*      */     
/*  502 */     ifft(a0, roots2);
/*  503 */     ifft(a1, roots2);
/*  504 */     ifft(a2, roots2);
/*      */ 
/*      */     
/*  507 */     MutableComplex omega = new MutableComplex(); int i;
/*  508 */     for (i = 0; i < a.length / 4; i++) {
/*  509 */       omega.set(roots3, i);
/*      */       
/*  511 */       a1.multiply(i, omega);
/*  512 */       a2.multiply(i, omega);
/*  513 */       a2.multiply(i, omega);
/*      */     } 
/*  515 */     for (i = a.length / 4; i < oneThird; i++) {
/*  516 */       omega.set(roots3, i - a.length / 4);
/*      */       
/*  518 */       a1.multiplyByIAnd(i, omega);
/*  519 */       a2.multiplyByIAnd(i, omega);
/*  520 */       a2.multiplyByIAnd(i, omega);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  526 */     fft3(a0, a1, a2, -1, 0.3333333333333333D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigInteger multiply(BigInteger a, BigInteger b) {
/*  539 */     assert a != null : "a==null";
/*  540 */     assert b != null : "b==null";
/*      */     
/*  542 */     if (b.signum() == 0 || a.signum() == 0) {
/*  543 */       return BigInteger.ZERO;
/*      */     }
/*      */ 
/*      */     
/*  547 */     if (b == a) {
/*  548 */       return square(b);
/*      */     }
/*      */     
/*  551 */     int xlen = a.bitLength();
/*  552 */     int ylen = b.bitLength();
/*  553 */     if (xlen + ylen > 2147483648L) {
/*  554 */       throw new ArithmeticException("BigInteger would overflow supported range");
/*      */     }
/*      */     
/*  557 */     if (xlen > 1920 && ylen > 1920 && (xlen > 33220 || ylen > 33220))
/*      */     {
/*      */       
/*  560 */       return multiplyFft(a, b);
/*      */     }
/*  562 */     return a.multiply(b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigInteger multiplyFft(BigInteger a, BigInteger b) {
/*  608 */     int signum = a.signum() * b.signum();
/*  609 */     byte[] aMag = ((a.signum() < 0) ? a.negate() : a).toByteArray();
/*  610 */     byte[] bMag = ((b.signum() < 0) ? b.negate() : b).toByteArray();
/*  611 */     int bitLen = Math.max(aMag.length, bMag.length) * 8;
/*  612 */     int bitsPerPoint = bitsPerFftPoint(bitLen);
/*  613 */     int fftLen = (bitLen + bitsPerPoint - 1) / bitsPerPoint + 1;
/*  614 */     int logFFTLen = 32 - Integer.numberOfLeadingZeros(fftLen - 1);
/*      */ 
/*      */     
/*  617 */     int fftLen2 = 1 << logFFTLen;
/*  618 */     int fftLen3 = fftLen2 * 3 / 4;
/*  619 */     if (fftLen < fftLen3 && logFFTLen > 3) {
/*  620 */       ComplexVector[] roots2 = getRootsOfUnity2(logFFTLen - 2);
/*  621 */       ComplexVector weights = getRootsOfUnity3(logFFTLen - 2);
/*  622 */       ComplexVector twiddles = getRootsOfUnity3(logFFTLen - 4);
/*  623 */       ComplexVector complexVector1 = toFftVector(aMag, fftLen3, bitsPerPoint);
/*  624 */       complexVector1.applyWeights(weights);
/*  625 */       fftMixedRadix(complexVector1, roots2, twiddles);
/*  626 */       ComplexVector complexVector2 = toFftVector(bMag, fftLen3, bitsPerPoint);
/*  627 */       complexVector2.applyWeights(weights);
/*  628 */       fftMixedRadix(complexVector2, roots2, twiddles);
/*  629 */       complexVector1.multiplyPointwise(complexVector2);
/*  630 */       ifftMixedRadix(complexVector1, roots2, twiddles);
/*  631 */       complexVector1.applyInverseWeights(weights);
/*  632 */       return fromFftVector(complexVector1, signum, bitsPerPoint);
/*      */     } 
/*  634 */     ComplexVector[] roots = getRootsOfUnity2(logFFTLen);
/*  635 */     ComplexVector aVec = toFftVector(aMag, fftLen2, bitsPerPoint);
/*  636 */     aVec.applyWeights(roots[logFFTLen]);
/*  637 */     fft(aVec, roots);
/*  638 */     ComplexVector bVec = toFftVector(bMag, fftLen2, bitsPerPoint);
/*  639 */     bVec.applyWeights(roots[logFFTLen]);
/*  640 */     fft(bVec, roots);
/*  641 */     aVec.multiplyPointwise(bVec);
/*  642 */     ifft(aVec, roots);
/*  643 */     aVec.applyInverseWeights(roots[logFFTLen]);
/*  644 */     return fromFftVector(aVec, signum, bitsPerPoint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigInteger square(BigInteger a) {
/*  654 */     if (a.signum() == 0) {
/*  655 */       return BigInteger.ZERO;
/*      */     }
/*  657 */     return (a.bitLength() < 33220) ? a.multiply(a) : squareFft(a);
/*      */   }
/*      */   
/*      */   static BigInteger squareFft(BigInteger a) {
/*  661 */     byte[] mag = a.toByteArray();
/*  662 */     int bitLen = mag.length * 8;
/*  663 */     int bitsPerPoint = bitsPerFftPoint(bitLen);
/*  664 */     int fftLen = (bitLen + bitsPerPoint - 1) / bitsPerPoint + 1;
/*  665 */     int logFFTLen = 32 - Integer.numberOfLeadingZeros(fftLen - 1);
/*      */ 
/*      */     
/*  668 */     int fftLen2 = 1 << logFFTLen;
/*  669 */     int fftLen3 = fftLen2 * 3 / 4;
/*  670 */     if (fftLen < fftLen3) {
/*  671 */       fftLen = fftLen3;
/*  672 */       ComplexVector complexVector1 = toFftVector(mag, fftLen, bitsPerPoint);
/*  673 */       ComplexVector[] roots2 = getRootsOfUnity2(logFFTLen - 2);
/*  674 */       ComplexVector weights = getRootsOfUnity3(logFFTLen - 2);
/*  675 */       ComplexVector twiddles = getRootsOfUnity3(logFFTLen - 4);
/*  676 */       complexVector1.applyWeights(weights);
/*  677 */       fftMixedRadix(complexVector1, roots2, twiddles);
/*  678 */       complexVector1.squarePointwise();
/*  679 */       ifftMixedRadix(complexVector1, roots2, twiddles);
/*  680 */       complexVector1.applyInverseWeights(weights);
/*  681 */       return fromFftVector(complexVector1, 1, bitsPerPoint);
/*      */     } 
/*  683 */     fftLen = fftLen2;
/*  684 */     ComplexVector vec = toFftVector(mag, fftLen, bitsPerPoint);
/*  685 */     ComplexVector[] roots = getRootsOfUnity2(logFFTLen);
/*  686 */     vec.applyWeights(roots[logFFTLen]);
/*  687 */     fft(vec, roots);
/*  688 */     vec.squarePointwise();
/*  689 */     ifft(vec, roots);
/*  690 */     vec.applyInverseWeights(roots[logFFTLen]);
/*  691 */     return fromFftVector(vec, 1, bitsPerPoint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ComplexVector toFftVector(byte[] mag, int fftLen, int bitsPerFftPoint) {
/*  700 */     assert bitsPerFftPoint <= 25 : bitsPerFftPoint + " does not fit into an int with slack";
/*      */     
/*  702 */     ComplexVector fftVec = new ComplexVector(fftLen);
/*  703 */     if (mag.length < 4) {
/*  704 */       byte[] paddedMag = new byte[4];
/*  705 */       System.arraycopy(mag, 0, paddedMag, 4 - mag.length, mag.length);
/*  706 */       mag = paddedMag;
/*      */     } 
/*      */ 
/*      */     
/*  710 */     int base = 1 << bitsPerFftPoint;
/*  711 */     int halfBase = base / 2;
/*  712 */     int bitMask = base - 1;
/*  713 */     int bitPadding = 32 - bitsPerFftPoint;
/*  714 */     int bitLength = mag.length * 8;
/*  715 */     int carry = 0;
/*  716 */     int fftIdx = 0; int bitIdx;
/*  717 */     for (bitIdx = bitLength - bitsPerFftPoint; bitIdx > -bitsPerFftPoint; bitIdx -= bitsPerFftPoint) {
/*  718 */       int idx = Math.min(Math.max(0, bitIdx >> 3), mag.length - 4);
/*  719 */       int shift = bitPadding - bitIdx + (idx << 3);
/*  720 */       int fftPoint = FastDoubleSwar.readIntBE(mag, idx) >>> shift & bitMask;
/*      */ 
/*      */       
/*  723 */       fftPoint += carry;
/*  724 */       carry = halfBase - fftPoint >>> 31;
/*  725 */       fftPoint -= base & -carry;
/*      */       
/*  727 */       fftVec.real(fftIdx, fftPoint);
/*  728 */       fftIdx++;
/*      */     } 
/*      */     
/*  731 */     if (carry > 0) {
/*  732 */       fftVec.real(fftIdx, carry);
/*      */     }
/*      */     
/*  735 */     return fftVec;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class ComplexVector
/*      */   {
/*      */     private static final int COMPLEX_SIZE_SHIFT = 1;
/*      */ 
/*      */     
/*      */     static final int IMAG = 1;
/*      */ 
/*      */     
/*      */     static final int REAL = 0;
/*      */ 
/*      */     
/*      */     private final double[] a;
/*      */ 
/*      */     
/*      */     private final int length;
/*      */ 
/*      */     
/*      */     private final int offset;
/*      */ 
/*      */ 
/*      */     
/*      */     ComplexVector(int length) {
/*  763 */       this.a = new double[length << 1];
/*  764 */       this.length = length;
/*  765 */       this.offset = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ComplexVector(ComplexVector c, int from, int to) {
/*  776 */       this.length = to - from;
/*  777 */       this.a = c.a;
/*  778 */       this.offset = from << 1;
/*      */     }
/*      */     
/*      */     void add(int idxa, FftMultiplier.MutableComplex c) {
/*  782 */       this.a[realIdx(idxa)] = this.a[realIdx(idxa)] + c.real;
/*  783 */       this.a[imagIdx(idxa)] = this.a[imagIdx(idxa)] + c.imag;
/*      */     }
/*      */     
/*      */     void addInto(int idxa, ComplexVector c, int idxc, FftMultiplier.MutableComplex destination) {
/*  787 */       destination.real = this.a[realIdx(idxa)] + c.real(idxc);
/*  788 */       destination.imag = this.a[imagIdx(idxa)] + c.imag(idxc);
/*      */     }
/*      */     
/*      */     void addTimesIInto(int idxa, ComplexVector c, int idxc, FftMultiplier.MutableComplex destination) {
/*  792 */       destination.real = this.a[realIdx(idxa)] - c.imag(idxc);
/*  793 */       destination.imag = this.a[imagIdx(idxa)] + c.real(idxc);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void applyInverseWeights(ComplexVector weights) {
/*  801 */       int offw = weights.offset;
/*  802 */       double[] w = weights.a;
/*  803 */       int end = this.offset + this.length << 1;
/*  804 */       for (int offa = this.offset; offa < end; offa += 2) {
/*      */ 
/*      */         
/*  807 */         double real = this.a[offa + 0];
/*  808 */         double imag = this.a[offa + 1];
/*  809 */         this.a[offa] = FastDoubleSwar.fma(real, w[offw + 0], imag * w[offw + 1]);
/*  810 */         this.a[offa + 1] = FastDoubleSwar.fma(-real, w[offw + 1], imag * w[offw + 0]);
/*  811 */         offw += 2;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void applyWeights(ComplexVector weights) {
/*  823 */       int offw = weights.offset;
/*  824 */       double[] w = weights.a;
/*  825 */       int end = this.offset + this.length << 1;
/*  826 */       for (int offa = this.offset; offa < end; offa += 2) {
/*  827 */         double real = this.a[offa + 0];
/*  828 */         this.a[offa + 0] = real * w[offw + 0];
/*  829 */         this.a[offa + 1] = real * w[offw + 1];
/*  830 */         offw += 2;
/*      */       } 
/*      */     }
/*      */     
/*      */     void copyInto(int idxa, FftMultiplier.MutableComplex destination) {
/*  835 */       destination.real = this.a[realIdx(idxa)];
/*  836 */       destination.imag = this.a[imagIdx(idxa)];
/*      */     }
/*      */     
/*      */     double imag(int idxa) {
/*  840 */       return this.a[(idxa << 1) + this.offset + 1];
/*      */     }
/*      */     
/*      */     void imag(int idxa, double value) {
/*  844 */       this.a[(idxa << 1) + this.offset + 1] = value;
/*      */     }
/*      */     
/*      */     private int imagIdx(int idxa) {
/*  848 */       return (idxa << 1) + this.offset + 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void multiply(int idxa, FftMultiplier.MutableComplex c) {
/*  856 */       int ri = realIdx(idxa);
/*  857 */       int ii = imagIdx(idxa);
/*  858 */       double real = this.a[ri];
/*  859 */       double imag = this.a[ii];
/*  860 */       this.a[ri] = FastDoubleSwar.fma(real, c.real, -imag * c.imag);
/*  861 */       this.a[ii] = FastDoubleSwar.fma(real, c.imag, imag * c.real);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void multiplyByIAnd(int idxa, FftMultiplier.MutableComplex c) {
/*  869 */       int ri = realIdx(idxa);
/*  870 */       int ii = imagIdx(idxa);
/*  871 */       double real = this.a[ri];
/*  872 */       double imag = this.a[ii];
/*  873 */       this.a[ri] = FastDoubleSwar.fma(-real, c.imag, -imag * c.real);
/*  874 */       this.a[ii] = FastDoubleSwar.fma(real, c.real, -imag * c.imag);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void multiplyConjugate(int idxa, FftMultiplier.MutableComplex c) {
/*  882 */       int ri = realIdx(idxa);
/*  883 */       int ii = imagIdx(idxa);
/*  884 */       double real = this.a[ri];
/*  885 */       double imag = this.a[ii];
/*  886 */       this.a[ri] = FastDoubleSwar.fma(real, c.real, imag * c.imag);
/*  887 */       this.a[ii] = FastDoubleSwar.fma(-real, c.imag, imag * c.real);
/*      */     }
/*      */     
/*      */     void multiplyConjugateInto(int idxa, FftMultiplier.MutableComplex c, FftMultiplier.MutableComplex destination) {
/*  891 */       double real = this.a[realIdx(idxa)];
/*  892 */       double imag = this.a[imagIdx(idxa)];
/*  893 */       destination.real = FastDoubleSwar.fma(real, c.real, imag * c.imag);
/*  894 */       destination.imag = FastDoubleSwar.fma(-real, c.imag, imag * c.real);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void multiplyConjugateTimesI(int idxa, FftMultiplier.MutableComplex c) {
/*  903 */       int ri = realIdx(idxa);
/*  904 */       int ii = imagIdx(idxa);
/*  905 */       double real = this.a[ri];
/*  906 */       double imag = this.a[ii];
/*  907 */       this.a[ri] = FastDoubleSwar.fma(-real, c.imag, imag * c.real);
/*  908 */       this.a[ii] = FastDoubleSwar.fma(-real, c.real, -imag * c.imag);
/*      */     }
/*      */     
/*      */     void multiplyInto(int idxa, FftMultiplier.MutableComplex c, FftMultiplier.MutableComplex destination) {
/*  912 */       double real = this.a[realIdx(idxa)];
/*  913 */       double imag = this.a[imagIdx(idxa)];
/*  914 */       destination.real = FastDoubleSwar.fma(real, c.real, -imag * c.imag);
/*  915 */       destination.imag = FastDoubleSwar.fma(real, c.imag, imag * c.real);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void multiplyPointwise(ComplexVector cvec) {
/*  921 */       int offc = cvec.offset;
/*  922 */       double[] c = cvec.a;
/*  923 */       int end = this.offset + this.length << 1;
/*  924 */       for (int offa = this.offset; offa < end; offa += 2) {
/*      */         
/*  926 */         double real = this.a[offa + 0];
/*  927 */         double imag = this.a[offa + 1];
/*  928 */         double creal = c[offc + 0];
/*  929 */         double cimag = c[offc + 1];
/*  930 */         this.a[offa + 0] = FastDoubleSwar.fma(real, creal, -imag * cimag);
/*  931 */         this.a[offa + 1] = FastDoubleSwar.fma(real, cimag, imag * creal);
/*  932 */         offc += 2;
/*      */       } 
/*      */     }
/*      */     
/*      */     double part(int idxa, int part) {
/*  937 */       return this.a[(idxa << 1) + part];
/*      */     }
/*      */     
/*      */     double real(int idxa) {
/*  941 */       return this.a[(idxa << 1) + this.offset];
/*      */     }
/*      */     
/*      */     void real(int idxa, double value) {
/*  945 */       this.a[(idxa << 1) + this.offset] = value;
/*      */     }
/*      */     
/*      */     private int realIdx(int idxa) {
/*  949 */       return (idxa << 1) + this.offset;
/*      */     }
/*      */     
/*      */     void set(int idxa, double real, double imag) {
/*  953 */       int idx = realIdx(idxa);
/*  954 */       this.a[idx] = real;
/*  955 */       this.a[idx + 1] = imag;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void squarePointwise() {
/*  964 */       int end = this.offset + this.length << 1;
/*  965 */       for (int offa = this.offset; offa < end; offa += 2) {
/*  966 */         double real = this.a[offa + 0];
/*  967 */         double imag = this.a[offa + 1];
/*  968 */         this.a[offa + 0] = FastDoubleSwar.fma(real, real, -imag * imag);
/*  969 */         this.a[offa + 1] = 2.0D * real * imag;
/*      */       } 
/*      */     }
/*      */     
/*      */     void subtractInto(int idxa, ComplexVector c, int idxc, FftMultiplier.MutableComplex destination) {
/*  974 */       destination.real = this.a[realIdx(idxa)] - c.real(idxc);
/*  975 */       destination.imag = this.a[imagIdx(idxa)] - c.imag(idxc);
/*      */     }
/*      */     
/*      */     void subtractTimesIInto(int idxa, ComplexVector c, int idxc, FftMultiplier.MutableComplex destination) {
/*  979 */       destination.real = this.a[realIdx(idxa)] + c.imag(idxc);
/*  980 */       destination.imag = this.a[imagIdx(idxa)] - c.real(idxc);
/*      */     }
/*      */     
/*      */     void timesTwoToThe(int idxa, int n) {
/*  984 */       int ri = realIdx(idxa);
/*  985 */       int ii = imagIdx(idxa);
/*  986 */       double real = this.a[ri];
/*  987 */       double imag = this.a[ii];
/*  988 */       this.a[ri] = FastDoubleMath.fastScalb(real, n);
/*  989 */       this.a[ii] = FastDoubleMath.fastScalb(imag, n);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MutableComplex
/*      */   {
/*      */     double real;
/*      */     double imag;
/*      */     
/*      */     void add(MutableComplex c) {
/* 1000 */       this.real += c.real;
/* 1001 */       this.imag += c.imag;
/*      */     }
/*      */     
/*      */     void add(FftMultiplier.ComplexVector c, int idxc) {
/* 1005 */       this.real += c.real(idxc);
/* 1006 */       this.imag += c.imag(idxc);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void addInto(MutableComplex c, MutableComplex destination) {
/* 1014 */       this.real += c.real;
/* 1015 */       this.imag += c.imag;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void addTimesI(MutableComplex c) {
/* 1022 */       this.real -= c.imag;
/* 1023 */       this.imag += c.real;
/*      */     }
/*      */     
/*      */     void addTimesI(FftMultiplier.ComplexVector c, int idxc) {
/* 1027 */       this.real -= c.imag(idxc);
/* 1028 */       this.imag += c.real(idxc);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void addTimesIInto(MutableComplex c, MutableComplex destination) {
/* 1036 */       this.real -= c.imag;
/* 1037 */       this.imag += c.real;
/*      */     }
/*      */     
/*      */     void copyInto(FftMultiplier.ComplexVector c, int idxc) {
/* 1041 */       c.real(idxc, this.real);
/* 1042 */       c.imag(idxc, this.imag);
/*      */     }
/*      */     
/*      */     void multiply(MutableComplex c) {
/* 1046 */       double temp = this.real;
/* 1047 */       this.real = FastDoubleSwar.fma(temp, c.real, -this.imag * c.imag);
/* 1048 */       this.imag = FastDoubleSwar.fma(temp, c.imag, this.imag * c.real);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void multiplyConjugate(MutableComplex c) {
/* 1055 */       double temp = this.real;
/* 1056 */       this.real = FastDoubleSwar.fma(temp, c.real, this.imag * c.imag);
/* 1057 */       this.imag = FastDoubleSwar.fma(-temp, c.imag, this.imag * c.real);
/*      */     }
/*      */     
/*      */     void set(FftMultiplier.ComplexVector c, int idxc) {
/* 1061 */       this.real = c.real(idxc);
/* 1062 */       this.imag = c.imag(idxc);
/*      */     }
/*      */     
/*      */     void squareInto(MutableComplex destination) {
/* 1066 */       destination.real = FastDoubleSwar.fma(this.real, this.real, -this.imag * this.imag);
/* 1067 */       destination.imag = 2.0D * this.real * this.imag;
/*      */     }
/*      */     
/*      */     void subtract(MutableComplex c) {
/* 1071 */       this.real -= c.real;
/* 1072 */       this.imag -= c.imag;
/*      */     }
/*      */     
/*      */     void subtract(FftMultiplier.ComplexVector c, int idxc) {
/* 1076 */       this.real -= c.real(idxc);
/* 1077 */       this.imag -= c.imag(idxc);
/*      */     }
/*      */     
/*      */     void subtractInto(MutableComplex c, MutableComplex destination) {
/* 1081 */       this.real -= c.real;
/* 1082 */       this.imag -= c.imag;
/*      */     }
/*      */     
/*      */     void subtractInto(MutableComplex c, FftMultiplier.ComplexVector destination, int idxd) {
/* 1086 */       destination.real(idxd, this.real - c.real);
/* 1087 */       destination.imag(idxd, this.imag - c.imag);
/*      */     }
/*      */     
/*      */     void subtractTimesI(MutableComplex c) {
/* 1091 */       this.real += c.imag;
/* 1092 */       this.imag -= c.real;
/*      */     }
/*      */     
/*      */     void subtractTimesI(FftMultiplier.ComplexVector c, int idxc) {
/* 1096 */       this.real += c.imag(idxc);
/* 1097 */       this.imag -= c.real(idxc);
/*      */     }
/*      */     
/*      */     void subtractTimesIInto(MutableComplex c, MutableComplex destination) {
/* 1101 */       this.real += c.imag;
/* 1102 */       this.imag -= c.real;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\FftMultiplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */