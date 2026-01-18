/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.NavigableMap;
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
/*     */ final class JavaBigDecimalFromCharArray
/*     */   extends AbstractBigDecimalParser
/*     */ {
/*     */   public BigDecimal parseBigDecimalString(char[] str, int offset, int length) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: arraylength
/*     */     //   2: iload_2
/*     */     //   3: iload_3
/*     */     //   4: invokestatic checkBounds : (III)I
/*     */     //   7: istore #4
/*     */     //   9: iload_3
/*     */     //   10: invokestatic hasManyDigits : (I)Z
/*     */     //   13: ifeq -> 24
/*     */     //   16: aload_0
/*     */     //   17: aload_1
/*     */     //   18: iload_2
/*     */     //   19: iload_3
/*     */     //   20: invokevirtual parseBigDecimalStringWithManyDigits : ([CII)Ljava/math/BigDecimal;
/*     */     //   23: areturn
/*     */     //   24: lconst_0
/*     */     //   25: lstore #5
/*     */     //   27: iconst_m1
/*     */     //   28: istore #8
/*     */     //   30: iload_2
/*     */     //   31: istore #10
/*     */     //   33: aload_1
/*     */     //   34: iload #10
/*     */     //   36: iload #4
/*     */     //   38: invokestatic charAt : ([CII)C
/*     */     //   41: istore #11
/*     */     //   43: iconst_0
/*     */     //   44: istore #12
/*     */     //   46: iload #11
/*     */     //   48: bipush #45
/*     */     //   50: if_icmpne -> 57
/*     */     //   53: iconst_1
/*     */     //   54: goto -> 58
/*     */     //   57: iconst_0
/*     */     //   58: istore #13
/*     */     //   60: iload #13
/*     */     //   62: ifne -> 72
/*     */     //   65: iload #11
/*     */     //   67: bipush #43
/*     */     //   69: if_icmpne -> 100
/*     */     //   72: aload_1
/*     */     //   73: iinc #10, 1
/*     */     //   76: iload #10
/*     */     //   78: iload #4
/*     */     //   80: invokestatic charAt : ([CII)C
/*     */     //   83: istore #11
/*     */     //   85: iload #11
/*     */     //   87: ifne -> 100
/*     */     //   90: new java/lang/NumberFormatException
/*     */     //   93: dup
/*     */     //   94: ldc 'illegal syntax'
/*     */     //   96: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   99: athrow
/*     */     //   100: iload #10
/*     */     //   102: istore #7
/*     */     //   104: iload #10
/*     */     //   106: iload #4
/*     */     //   108: if_icmpge -> 224
/*     */     //   111: aload_1
/*     */     //   112: iload #10
/*     */     //   114: caload
/*     */     //   115: istore #11
/*     */     //   117: iload #11
/*     */     //   119: bipush #48
/*     */     //   121: isub
/*     */     //   122: i2c
/*     */     //   123: istore #14
/*     */     //   125: iload #14
/*     */     //   127: bipush #10
/*     */     //   129: if_icmpge -> 147
/*     */     //   132: ldc2_w 10
/*     */     //   135: lload #5
/*     */     //   137: lmul
/*     */     //   138: iload #14
/*     */     //   140: i2l
/*     */     //   141: ladd
/*     */     //   142: lstore #5
/*     */     //   144: goto -> 218
/*     */     //   147: iload #11
/*     */     //   149: bipush #46
/*     */     //   151: if_icmpne -> 224
/*     */     //   154: iload #12
/*     */     //   156: iload #8
/*     */     //   158: iflt -> 165
/*     */     //   161: iconst_1
/*     */     //   162: goto -> 166
/*     */     //   165: iconst_0
/*     */     //   166: ior
/*     */     //   167: istore #12
/*     */     //   169: iload #10
/*     */     //   171: istore #8
/*     */     //   173: iload #10
/*     */     //   175: iload #4
/*     */     //   177: iconst_4
/*     */     //   178: isub
/*     */     //   179: if_icmpge -> 218
/*     */     //   182: aload_1
/*     */     //   183: iload #10
/*     */     //   185: iconst_1
/*     */     //   186: iadd
/*     */     //   187: invokestatic tryToParseFourDigits : ([CI)I
/*     */     //   190: istore #15
/*     */     //   192: iload #15
/*     */     //   194: ifge -> 200
/*     */     //   197: goto -> 218
/*     */     //   200: ldc2_w 10000
/*     */     //   203: lload #5
/*     */     //   205: lmul
/*     */     //   206: iload #15
/*     */     //   208: i2l
/*     */     //   209: ladd
/*     */     //   210: lstore #5
/*     */     //   212: iinc #10, 4
/*     */     //   215: goto -> 173
/*     */     //   218: iinc #10, 1
/*     */     //   221: goto -> 104
/*     */     //   224: iload #10
/*     */     //   226: istore #15
/*     */     //   228: iload #8
/*     */     //   230: ifge -> 250
/*     */     //   233: iload #15
/*     */     //   235: iload #7
/*     */     //   237: isub
/*     */     //   238: istore #14
/*     */     //   240: iload #15
/*     */     //   242: istore #8
/*     */     //   244: lconst_0
/*     */     //   245: lstore #16
/*     */     //   247: goto -> 269
/*     */     //   250: iload #15
/*     */     //   252: iload #7
/*     */     //   254: isub
/*     */     //   255: iconst_1
/*     */     //   256: isub
/*     */     //   257: istore #14
/*     */     //   259: iload #8
/*     */     //   261: iload #15
/*     */     //   263: isub
/*     */     //   264: iconst_1
/*     */     //   265: iadd
/*     */     //   266: i2l
/*     */     //   267: lstore #16
/*     */     //   269: lconst_0
/*     */     //   270: lstore #18
/*     */     //   272: iload #11
/*     */     //   274: bipush #32
/*     */     //   276: ior
/*     */     //   277: bipush #101
/*     */     //   279: if_icmpne -> 432
/*     */     //   282: iload #10
/*     */     //   284: istore #9
/*     */     //   286: aload_1
/*     */     //   287: iinc #10, 1
/*     */     //   290: iload #10
/*     */     //   292: iload #4
/*     */     //   294: invokestatic charAt : ([CII)C
/*     */     //   297: istore #11
/*     */     //   299: iload #11
/*     */     //   301: bipush #45
/*     */     //   303: if_icmpne -> 310
/*     */     //   306: iconst_1
/*     */     //   307: goto -> 311
/*     */     //   310: iconst_0
/*     */     //   311: istore #20
/*     */     //   313: iload #20
/*     */     //   315: ifne -> 325
/*     */     //   318: iload #11
/*     */     //   320: bipush #43
/*     */     //   322: if_icmpne -> 338
/*     */     //   325: aload_1
/*     */     //   326: iinc #10, 1
/*     */     //   329: iload #10
/*     */     //   331: iload #4
/*     */     //   333: invokestatic charAt : ([CII)C
/*     */     //   336: istore #11
/*     */     //   338: iload #11
/*     */     //   340: bipush #48
/*     */     //   342: isub
/*     */     //   343: i2c
/*     */     //   344: istore #21
/*     */     //   346: iload #12
/*     */     //   348: iload #21
/*     */     //   350: bipush #10
/*     */     //   352: if_icmplt -> 359
/*     */     //   355: iconst_1
/*     */     //   356: goto -> 360
/*     */     //   359: iconst_0
/*     */     //   360: ior
/*     */     //   361: istore #12
/*     */     //   363: lload #18
/*     */     //   365: ldc2_w 2147483647
/*     */     //   368: lcmp
/*     */     //   369: ifge -> 384
/*     */     //   372: ldc2_w 10
/*     */     //   375: lload #18
/*     */     //   377: lmul
/*     */     //   378: iload #21
/*     */     //   380: i2l
/*     */     //   381: ladd
/*     */     //   382: lstore #18
/*     */     //   384: aload_1
/*     */     //   385: iinc #10, 1
/*     */     //   388: iload #10
/*     */     //   390: iload #4
/*     */     //   392: invokestatic charAt : ([CII)C
/*     */     //   395: istore #11
/*     */     //   397: iload #11
/*     */     //   399: bipush #48
/*     */     //   401: isub
/*     */     //   402: i2c
/*     */     //   403: istore #21
/*     */     //   405: iload #21
/*     */     //   407: bipush #10
/*     */     //   409: if_icmplt -> 363
/*     */     //   412: iload #20
/*     */     //   414: ifeq -> 422
/*     */     //   417: lload #18
/*     */     //   419: lneg
/*     */     //   420: lstore #18
/*     */     //   422: lload #16
/*     */     //   424: lload #18
/*     */     //   426: ladd
/*     */     //   427: lstore #16
/*     */     //   429: goto -> 436
/*     */     //   432: iload #4
/*     */     //   434: istore #9
/*     */     //   436: iload #12
/*     */     //   438: iload #14
/*     */     //   440: ifne -> 447
/*     */     //   443: iconst_1
/*     */     //   444: goto -> 448
/*     */     //   447: iconst_0
/*     */     //   448: ior
/*     */     //   449: istore #12
/*     */     //   451: iload #12
/*     */     //   453: iload #10
/*     */     //   455: iload #4
/*     */     //   457: iload #14
/*     */     //   459: lload #16
/*     */     //   461: invokestatic checkParsedBigDecimalBounds : (ZIIIJ)V
/*     */     //   464: iload #14
/*     */     //   466: bipush #19
/*     */     //   468: if_icmpge -> 498
/*     */     //   471: new java/math/BigDecimal
/*     */     //   474: dup
/*     */     //   475: iload #13
/*     */     //   477: ifeq -> 486
/*     */     //   480: lload #5
/*     */     //   482: lneg
/*     */     //   483: goto -> 488
/*     */     //   486: lload #5
/*     */     //   488: invokespecial <init> : (J)V
/*     */     //   491: lload #16
/*     */     //   493: l2i
/*     */     //   494: invokevirtual scaleByPowerOfTen : (I)Ljava/math/BigDecimal;
/*     */     //   497: areturn
/*     */     //   498: aload_0
/*     */     //   499: aload_1
/*     */     //   500: iload #7
/*     */     //   502: iload #8
/*     */     //   504: iload #8
/*     */     //   506: iconst_1
/*     */     //   507: iadd
/*     */     //   508: iload #9
/*     */     //   510: iload #13
/*     */     //   512: lload #16
/*     */     //   514: l2i
/*     */     //   515: invokevirtual valueOfBigDecimalString : ([CIIIIZI)Ljava/math/BigDecimal;
/*     */     //   518: areturn
/*     */     //   519: astore #4
/*     */     //   521: new java/lang/NumberFormatException
/*     */     //   524: dup
/*     */     //   525: ldc 'value exceeds limits'
/*     */     //   527: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   530: astore #5
/*     */     //   532: aload #5
/*     */     //   534: aload #4
/*     */     //   536: invokevirtual initCause : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
/*     */     //   539: pop
/*     */     //   540: aload #5
/*     */     //   542: athrow
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #40	-> 0
/*     */     //   #41	-> 9
/*     */     //   #42	-> 16
/*     */     //   #44	-> 24
/*     */     //   #46	-> 27
/*     */     //   #49	-> 30
/*     */     //   #50	-> 33
/*     */     //   #51	-> 43
/*     */     //   #56	-> 46
/*     */     //   #57	-> 60
/*     */     //   #58	-> 72
/*     */     //   #59	-> 85
/*     */     //   #60	-> 90
/*     */     //   #65	-> 100
/*     */     //   #66	-> 104
/*     */     //   #67	-> 111
/*     */     //   #68	-> 117
/*     */     //   #69	-> 125
/*     */     //   #71	-> 132
/*     */     //   #72	-> 147
/*     */     //   #73	-> 154
/*     */     //   #74	-> 169
/*     */     //   #75	-> 173
/*     */     //   #76	-> 182
/*     */     //   #77	-> 192
/*     */     //   #78	-> 197
/*     */     //   #81	-> 200
/*     */     //   #75	-> 212
/*     */     //   #66	-> 218
/*     */     //   #89	-> 224
/*     */     //   #91	-> 228
/*     */     //   #92	-> 233
/*     */     //   #93	-> 240
/*     */     //   #94	-> 244
/*     */     //   #96	-> 250
/*     */     //   #97	-> 259
/*     */     //   #102	-> 269
/*     */     //   #103	-> 272
/*     */     //   #104	-> 282
/*     */     //   #105	-> 286
/*     */     //   #106	-> 299
/*     */     //   #107	-> 313
/*     */     //   #108	-> 325
/*     */     //   #110	-> 338
/*     */     //   #111	-> 346
/*     */     //   #114	-> 363
/*     */     //   #115	-> 372
/*     */     //   #117	-> 384
/*     */     //   #118	-> 397
/*     */     //   #119	-> 405
/*     */     //   #120	-> 412
/*     */     //   #121	-> 417
/*     */     //   #123	-> 422
/*     */     //   #124	-> 429
/*     */     //   #125	-> 432
/*     */     //   #127	-> 436
/*     */     //   #128	-> 451
/*     */     //   #129	-> 464
/*     */     //   #130	-> 471
/*     */     //   #132	-> 498
/*     */     //   #133	-> 519
/*     */     //   #134	-> 521
/*     */     //   #135	-> 532
/*     */     //   #136	-> 540
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   192	20	15	digits	I
/*     */     //   125	93	14	digit	I
/*     */     //   240	10	14	digitCount	I
/*     */     //   247	3	16	exponent	J
/*     */     //   313	116	20	isExponentNegative	Z
/*     */     //   346	83	21	digit	I
/*     */     //   286	146	9	exponentIndicatorIndex	I
/*     */     //   9	510	4	endIndex	I
/*     */     //   27	492	5	significand	J
/*     */     //   104	415	7	integerPartIndex	I
/*     */     //   30	489	8	decimalPointIndex	I
/*     */     //   436	83	9	exponentIndicatorIndex	I
/*     */     //   33	486	10	index	I
/*     */     //   43	476	11	ch	C
/*     */     //   46	473	12	illegal	Z
/*     */     //   60	459	13	isNegative	Z
/*     */     //   259	260	14	digitCount	I
/*     */     //   228	291	15	significandEndIndex	I
/*     */     //   269	250	16	exponent	J
/*     */     //   272	247	18	expNumber	J
/*     */     //   532	11	5	nfe	Ljava/lang/NumberFormatException;
/*     */     //   521	22	4	e	Ljava/lang/ArithmeticException;
/*     */     //   0	543	0	this	Lch/randelshofer/fastdoubleparser/JavaBigDecimalFromCharArray;
/*     */     //   0	543	1	str	[C
/*     */     //   0	543	2	offset	I
/*     */     //   0	543	3	length	I
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	23	519	java/lang/ArithmeticException
/*     */     //   24	497	519	java/lang/ArithmeticException
/*     */     //   498	518	519	java/lang/ArithmeticException
/*     */   }
/*     */   
/*     */   BigDecimal parseBigDecimalStringWithManyDigits(char[] str, int offset, int length) {
/*     */     // Byte code:
/*     */     //   0: iconst_m1
/*     */     //   1: istore #6
/*     */     //   3: iconst_m1
/*     */     //   4: istore #7
/*     */     //   6: iload_2
/*     */     //   7: iload_3
/*     */     //   8: iadd
/*     */     //   9: istore #9
/*     */     //   11: iload_2
/*     */     //   12: istore #10
/*     */     //   14: aload_1
/*     */     //   15: iload #10
/*     */     //   17: iload #9
/*     */     //   19: invokestatic charAt : ([CII)C
/*     */     //   22: istore #11
/*     */     //   24: iconst_0
/*     */     //   25: istore #12
/*     */     //   27: iload #11
/*     */     //   29: bipush #45
/*     */     //   31: if_icmpne -> 38
/*     */     //   34: iconst_1
/*     */     //   35: goto -> 39
/*     */     //   38: iconst_0
/*     */     //   39: istore #13
/*     */     //   41: iload #13
/*     */     //   43: ifne -> 53
/*     */     //   46: iload #11
/*     */     //   48: bipush #43
/*     */     //   50: if_icmpne -> 81
/*     */     //   53: aload_1
/*     */     //   54: iinc #10, 1
/*     */     //   57: iload #10
/*     */     //   59: iload #9
/*     */     //   61: invokestatic charAt : ([CII)C
/*     */     //   64: istore #11
/*     */     //   66: iload #11
/*     */     //   68: ifne -> 81
/*     */     //   71: new java/lang/NumberFormatException
/*     */     //   74: dup
/*     */     //   75: ldc 'illegal syntax'
/*     */     //   77: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   80: athrow
/*     */     //   81: iload #10
/*     */     //   83: istore #4
/*     */     //   85: iload #9
/*     */     //   87: bipush #8
/*     */     //   89: isub
/*     */     //   90: ldc 1073741824
/*     */     //   92: invokestatic min : (II)I
/*     */     //   95: istore #14
/*     */     //   97: iload #10
/*     */     //   99: iload #14
/*     */     //   101: if_icmpge -> 119
/*     */     //   104: aload_1
/*     */     //   105: iload #10
/*     */     //   107: invokestatic isEightZeroes : ([CI)Z
/*     */     //   110: ifeq -> 119
/*     */     //   113: iinc #10, 8
/*     */     //   116: goto -> 97
/*     */     //   119: iload #10
/*     */     //   121: iload #9
/*     */     //   123: if_icmpge -> 141
/*     */     //   126: aload_1
/*     */     //   127: iload #10
/*     */     //   129: caload
/*     */     //   130: bipush #48
/*     */     //   132: if_icmpne -> 141
/*     */     //   135: iinc #10, 1
/*     */     //   138: goto -> 119
/*     */     //   141: iload #10
/*     */     //   143: istore #5
/*     */     //   145: iload #10
/*     */     //   147: iload #14
/*     */     //   149: if_icmpge -> 167
/*     */     //   152: aload_1
/*     */     //   153: iload #10
/*     */     //   155: invokestatic isEightDigits : ([CI)Z
/*     */     //   158: ifeq -> 167
/*     */     //   161: iinc #10, 8
/*     */     //   164: goto -> 145
/*     */     //   167: iload #10
/*     */     //   169: iload #9
/*     */     //   171: if_icmpge -> 193
/*     */     //   174: aload_1
/*     */     //   175: iload #10
/*     */     //   177: caload
/*     */     //   178: dup
/*     */     //   179: istore #11
/*     */     //   181: invokestatic isDigit : (C)Z
/*     */     //   184: ifeq -> 193
/*     */     //   187: iinc #10, 1
/*     */     //   190: goto -> 167
/*     */     //   193: iload #11
/*     */     //   195: bipush #46
/*     */     //   197: if_icmpne -> 303
/*     */     //   200: iload #10
/*     */     //   202: iinc #10, 1
/*     */     //   205: istore #7
/*     */     //   207: iload #10
/*     */     //   209: iload #14
/*     */     //   211: if_icmpge -> 229
/*     */     //   214: aload_1
/*     */     //   215: iload #10
/*     */     //   217: invokestatic isEightZeroes : ([CI)Z
/*     */     //   220: ifeq -> 229
/*     */     //   223: iinc #10, 8
/*     */     //   226: goto -> 207
/*     */     //   229: iload #10
/*     */     //   231: iload #9
/*     */     //   233: if_icmpge -> 251
/*     */     //   236: aload_1
/*     */     //   237: iload #10
/*     */     //   239: caload
/*     */     //   240: bipush #48
/*     */     //   242: if_icmpne -> 251
/*     */     //   245: iinc #10, 1
/*     */     //   248: goto -> 229
/*     */     //   251: iload #10
/*     */     //   253: istore #6
/*     */     //   255: iload #10
/*     */     //   257: iload #14
/*     */     //   259: if_icmpge -> 277
/*     */     //   262: aload_1
/*     */     //   263: iload #10
/*     */     //   265: invokestatic isEightDigits : ([CI)Z
/*     */     //   268: ifeq -> 277
/*     */     //   271: iinc #10, 8
/*     */     //   274: goto -> 255
/*     */     //   277: iload #10
/*     */     //   279: iload #9
/*     */     //   281: if_icmpge -> 303
/*     */     //   284: aload_1
/*     */     //   285: iload #10
/*     */     //   287: caload
/*     */     //   288: dup
/*     */     //   289: istore #11
/*     */     //   291: invokestatic isDigit : (C)Z
/*     */     //   294: ifeq -> 303
/*     */     //   297: iinc #10, 1
/*     */     //   300: goto -> 277
/*     */     //   303: iload #10
/*     */     //   305: istore #16
/*     */     //   307: iload #7
/*     */     //   309: ifge -> 333
/*     */     //   312: iload #16
/*     */     //   314: iload #5
/*     */     //   316: isub
/*     */     //   317: istore #15
/*     */     //   319: iload #16
/*     */     //   321: istore #7
/*     */     //   323: iload #16
/*     */     //   325: istore #6
/*     */     //   327: lconst_0
/*     */     //   328: lstore #17
/*     */     //   330: goto -> 367
/*     */     //   333: iload #5
/*     */     //   335: iload #7
/*     */     //   337: if_icmpne -> 348
/*     */     //   340: iload #16
/*     */     //   342: iload #6
/*     */     //   344: isub
/*     */     //   345: goto -> 355
/*     */     //   348: iload #16
/*     */     //   350: iload #5
/*     */     //   352: isub
/*     */     //   353: iconst_1
/*     */     //   354: isub
/*     */     //   355: istore #15
/*     */     //   357: iload #7
/*     */     //   359: iload #16
/*     */     //   361: isub
/*     */     //   362: iconst_1
/*     */     //   363: iadd
/*     */     //   364: i2l
/*     */     //   365: lstore #17
/*     */     //   367: lconst_0
/*     */     //   368: lstore #19
/*     */     //   370: iload #11
/*     */     //   372: bipush #32
/*     */     //   374: ior
/*     */     //   375: bipush #101
/*     */     //   377: if_icmpne -> 530
/*     */     //   380: iload #10
/*     */     //   382: istore #8
/*     */     //   384: aload_1
/*     */     //   385: iinc #10, 1
/*     */     //   388: iload #10
/*     */     //   390: iload #9
/*     */     //   392: invokestatic charAt : ([CII)C
/*     */     //   395: istore #11
/*     */     //   397: iload #11
/*     */     //   399: bipush #45
/*     */     //   401: if_icmpne -> 408
/*     */     //   404: iconst_1
/*     */     //   405: goto -> 409
/*     */     //   408: iconst_0
/*     */     //   409: istore #21
/*     */     //   411: iload #21
/*     */     //   413: ifne -> 423
/*     */     //   416: iload #11
/*     */     //   418: bipush #43
/*     */     //   420: if_icmpne -> 436
/*     */     //   423: aload_1
/*     */     //   424: iinc #10, 1
/*     */     //   427: iload #10
/*     */     //   429: iload #9
/*     */     //   431: invokestatic charAt : ([CII)C
/*     */     //   434: istore #11
/*     */     //   436: iload #11
/*     */     //   438: bipush #48
/*     */     //   440: isub
/*     */     //   441: i2c
/*     */     //   442: istore #22
/*     */     //   444: iload #12
/*     */     //   446: iload #22
/*     */     //   448: bipush #10
/*     */     //   450: if_icmplt -> 457
/*     */     //   453: iconst_1
/*     */     //   454: goto -> 458
/*     */     //   457: iconst_0
/*     */     //   458: ior
/*     */     //   459: istore #12
/*     */     //   461: lload #19
/*     */     //   463: ldc2_w 2147483647
/*     */     //   466: lcmp
/*     */     //   467: ifge -> 482
/*     */     //   470: ldc2_w 10
/*     */     //   473: lload #19
/*     */     //   475: lmul
/*     */     //   476: iload #22
/*     */     //   478: i2l
/*     */     //   479: ladd
/*     */     //   480: lstore #19
/*     */     //   482: aload_1
/*     */     //   483: iinc #10, 1
/*     */     //   486: iload #10
/*     */     //   488: iload #9
/*     */     //   490: invokestatic charAt : ([CII)C
/*     */     //   493: istore #11
/*     */     //   495: iload #11
/*     */     //   497: bipush #48
/*     */     //   499: isub
/*     */     //   500: i2c
/*     */     //   501: istore #22
/*     */     //   503: iload #22
/*     */     //   505: bipush #10
/*     */     //   507: if_icmplt -> 461
/*     */     //   510: iload #21
/*     */     //   512: ifeq -> 520
/*     */     //   515: lload #19
/*     */     //   517: lneg
/*     */     //   518: lstore #19
/*     */     //   520: lload #17
/*     */     //   522: lload #19
/*     */     //   524: ladd
/*     */     //   525: lstore #17
/*     */     //   527: goto -> 534
/*     */     //   530: iload #9
/*     */     //   532: istore #8
/*     */     //   534: iload #12
/*     */     //   536: iload #4
/*     */     //   538: iload #7
/*     */     //   540: if_icmpne -> 554
/*     */     //   543: iload #7
/*     */     //   545: iload #8
/*     */     //   547: if_icmpne -> 554
/*     */     //   550: iconst_1
/*     */     //   551: goto -> 555
/*     */     //   554: iconst_0
/*     */     //   555: ior
/*     */     //   556: istore #12
/*     */     //   558: iload #12
/*     */     //   560: iload #10
/*     */     //   562: iload #9
/*     */     //   564: iload #15
/*     */     //   566: lload #17
/*     */     //   568: invokestatic checkParsedBigDecimalBounds : (ZIIIJ)V
/*     */     //   571: aload_0
/*     */     //   572: aload_1
/*     */     //   573: iload #5
/*     */     //   575: iload #7
/*     */     //   577: iload #6
/*     */     //   579: iload #8
/*     */     //   581: iload #13
/*     */     //   583: lload #17
/*     */     //   585: l2i
/*     */     //   586: invokevirtual valueOfBigDecimalString : ([CIIIIZI)Ljava/math/BigDecimal;
/*     */     //   589: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #146	-> 0
/*     */     //   #147	-> 3
/*     */     //   #150	-> 6
/*     */     //   #151	-> 11
/*     */     //   #152	-> 14
/*     */     //   #153	-> 24
/*     */     //   #157	-> 27
/*     */     //   #158	-> 41
/*     */     //   #159	-> 53
/*     */     //   #160	-> 66
/*     */     //   #161	-> 71
/*     */     //   #168	-> 81
/*     */     //   #170	-> 85
/*     */     //   #171	-> 97
/*     */     //   #172	-> 113
/*     */     //   #174	-> 119
/*     */     //   #175	-> 135
/*     */     //   #178	-> 141
/*     */     //   #179	-> 145
/*     */     //   #180	-> 161
/*     */     //   #182	-> 167
/*     */     //   #183	-> 187
/*     */     //   #185	-> 193
/*     */     //   #186	-> 200
/*     */     //   #188	-> 207
/*     */     //   #189	-> 223
/*     */     //   #191	-> 229
/*     */     //   #192	-> 245
/*     */     //   #194	-> 251
/*     */     //   #196	-> 255
/*     */     //   #197	-> 271
/*     */     //   #199	-> 277
/*     */     //   #200	-> 297
/*     */     //   #205	-> 303
/*     */     //   #207	-> 307
/*     */     //   #208	-> 312
/*     */     //   #209	-> 319
/*     */     //   #210	-> 323
/*     */     //   #211	-> 327
/*     */     //   #213	-> 333
/*     */     //   #214	-> 340
/*     */     //   #215	-> 348
/*     */     //   #216	-> 357
/*     */     //   #221	-> 367
/*     */     //   #222	-> 370
/*     */     //   #223	-> 380
/*     */     //   #224	-> 384
/*     */     //   #225	-> 397
/*     */     //   #226	-> 411
/*     */     //   #227	-> 423
/*     */     //   #229	-> 436
/*     */     //   #230	-> 444
/*     */     //   #233	-> 461
/*     */     //   #234	-> 470
/*     */     //   #236	-> 482
/*     */     //   #237	-> 495
/*     */     //   #238	-> 503
/*     */     //   #239	-> 510
/*     */     //   #240	-> 515
/*     */     //   #242	-> 520
/*     */     //   #243	-> 527
/*     */     //   #244	-> 530
/*     */     //   #246	-> 534
/*     */     //   #247	-> 558
/*     */     //   #249	-> 571
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   319	14	15	digitCountWithoutLeadingZeros	I
/*     */     //   330	3	17	exponent	J
/*     */     //   411	116	21	isExponentNegative	Z
/*     */     //   444	83	22	digit	I
/*     */     //   384	146	8	exponentIndicatorIndex	I
/*     */     //   0	590	0	this	Lch/randelshofer/fastdoubleparser/JavaBigDecimalFromCharArray;
/*     */     //   0	590	1	str	[C
/*     */     //   0	590	2	offset	I
/*     */     //   0	590	3	length	I
/*     */     //   85	505	4	integerPartIndex	I
/*     */     //   145	445	5	nonZeroIntegerPartIndex	I
/*     */     //   3	587	6	nonZeroFractionalPartIndex	I
/*     */     //   6	584	7	decimalPointIndex	I
/*     */     //   534	56	8	exponentIndicatorIndex	I
/*     */     //   11	579	9	endIndex	I
/*     */     //   14	576	10	index	I
/*     */     //   24	566	11	ch	C
/*     */     //   27	563	12	illegal	Z
/*     */     //   41	549	13	isNegative	Z
/*     */     //   97	493	14	swarLimit	I
/*     */     //   357	233	15	digitCountWithoutLeadingZeros	I
/*     */     //   307	283	16	significandEndIndex	I
/*     */     //   367	223	17	exponent	J
/*     */     //   370	220	19	expNumber	J
/*     */   }
/*     */   
/*     */   BigDecimal valueOfBigDecimalString(char[] str, int integerPartIndex, int decimalPointIndex, int nonZeroFractionalPartIndex, int exponentIndicatorIndex, boolean isNegative, int exponent) {
/*     */     BigInteger significand, integerPart;
/* 278 */     int fractionDigitsCount = exponentIndicatorIndex - decimalPointIndex - 1;
/* 279 */     int nonZeroFractionDigitsCount = exponentIndicatorIndex - nonZeroFractionalPartIndex;
/* 280 */     int integerDigitsCount = decimalPointIndex - integerPartIndex;
/* 281 */     NavigableMap<Integer, BigInteger> powersOfTen = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     if (integerDigitsCount > 0) {
/* 291 */       if (integerDigitsCount > 400) {
/* 292 */         powersOfTen = FastIntegerMath.createPowersOfTenFloor16Map();
/* 293 */         FastIntegerMath.fillPowersOfNFloor16Recursive(powersOfTen, integerPartIndex, decimalPointIndex);
/* 294 */         integerPart = ParseDigitsTaskCharArray.parseDigitsRecursive(str, integerPartIndex, decimalPointIndex, powersOfTen, 400);
/*     */       } else {
/* 296 */         integerPart = ParseDigitsTaskCharArray.parseDigitsIterative(str, integerPartIndex, decimalPointIndex);
/*     */       } 
/*     */     } else {
/* 299 */       integerPart = BigInteger.ZERO;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 304 */     if (fractionDigitsCount > 0) {
/*     */       BigInteger fractionalPart;
/* 306 */       if (nonZeroFractionDigitsCount > 400) {
/* 307 */         if (powersOfTen == null) {
/* 308 */           powersOfTen = FastIntegerMath.createPowersOfTenFloor16Map();
/*     */         }
/* 310 */         FastIntegerMath.fillPowersOfNFloor16Recursive(powersOfTen, nonZeroFractionalPartIndex, exponentIndicatorIndex);
/* 311 */         fractionalPart = ParseDigitsTaskCharArray.parseDigitsRecursive(str, nonZeroFractionalPartIndex, exponentIndicatorIndex, powersOfTen, 400);
/*     */       } else {
/* 313 */         fractionalPart = ParseDigitsTaskCharArray.parseDigitsIterative(str, nonZeroFractionalPartIndex, exponentIndicatorIndex);
/*     */       } 
/*     */       
/* 316 */       if (integerPart.signum() == 0) {
/* 317 */         significand = fractionalPart;
/*     */       } else {
/* 319 */         BigInteger integerFactor = FastIntegerMath.computePowerOfTen(powersOfTen, fractionDigitsCount);
/* 320 */         significand = FftMultiplier.multiply(integerPart, integerFactor).add(fractionalPart);
/*     */       } 
/*     */     } else {
/* 323 */       significand = integerPart;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 328 */     return new BigDecimal(isNegative ? significand.negate() : significand, -exponent);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaBigDecimalFromCharArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */