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
/*     */ final class JavaBigDecimalFromCharSequence
/*     */   extends AbstractBigDecimalParser
/*     */ {
/*     */   public BigDecimal parseBigDecimalString(CharSequence str, int offset, int length) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokeinterface length : ()I
/*     */     //   6: iload_2
/*     */     //   7: iload_3
/*     */     //   8: invokestatic checkBounds : (III)I
/*     */     //   11: istore #4
/*     */     //   13: iload_3
/*     */     //   14: invokestatic hasManyDigits : (I)Z
/*     */     //   17: ifeq -> 28
/*     */     //   20: aload_0
/*     */     //   21: aload_1
/*     */     //   22: iload_2
/*     */     //   23: iload_3
/*     */     //   24: invokevirtual parseBigDecimalStringWithManyDigits : (Ljava/lang/CharSequence;II)Ljava/math/BigDecimal;
/*     */     //   27: areturn
/*     */     //   28: lconst_0
/*     */     //   29: lstore #5
/*     */     //   31: iconst_m1
/*     */     //   32: istore #8
/*     */     //   34: iload_2
/*     */     //   35: istore #10
/*     */     //   37: aload_1
/*     */     //   38: iload #10
/*     */     //   40: iload #4
/*     */     //   42: invokestatic charAt : (Ljava/lang/CharSequence;II)C
/*     */     //   45: istore #11
/*     */     //   47: iconst_0
/*     */     //   48: istore #12
/*     */     //   50: iload #11
/*     */     //   52: bipush #45
/*     */     //   54: if_icmpne -> 61
/*     */     //   57: iconst_1
/*     */     //   58: goto -> 62
/*     */     //   61: iconst_0
/*     */     //   62: istore #13
/*     */     //   64: iload #13
/*     */     //   66: ifne -> 76
/*     */     //   69: iload #11
/*     */     //   71: bipush #43
/*     */     //   73: if_icmpne -> 104
/*     */     //   76: aload_1
/*     */     //   77: iinc #10, 1
/*     */     //   80: iload #10
/*     */     //   82: iload #4
/*     */     //   84: invokestatic charAt : (Ljava/lang/CharSequence;II)C
/*     */     //   87: istore #11
/*     */     //   89: iload #11
/*     */     //   91: ifne -> 104
/*     */     //   94: new java/lang/NumberFormatException
/*     */     //   97: dup
/*     */     //   98: ldc 'illegal syntax'
/*     */     //   100: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   103: athrow
/*     */     //   104: iload #10
/*     */     //   106: istore #7
/*     */     //   108: iload #10
/*     */     //   110: iload #4
/*     */     //   112: if_icmpge -> 232
/*     */     //   115: aload_1
/*     */     //   116: iload #10
/*     */     //   118: invokeinterface charAt : (I)C
/*     */     //   123: istore #11
/*     */     //   125: iload #11
/*     */     //   127: bipush #48
/*     */     //   129: isub
/*     */     //   130: i2c
/*     */     //   131: istore #14
/*     */     //   133: iload #14
/*     */     //   135: bipush #10
/*     */     //   137: if_icmpge -> 155
/*     */     //   140: ldc2_w 10
/*     */     //   143: lload #5
/*     */     //   145: lmul
/*     */     //   146: iload #14
/*     */     //   148: i2l
/*     */     //   149: ladd
/*     */     //   150: lstore #5
/*     */     //   152: goto -> 226
/*     */     //   155: iload #11
/*     */     //   157: bipush #46
/*     */     //   159: if_icmpne -> 232
/*     */     //   162: iload #12
/*     */     //   164: iload #8
/*     */     //   166: iflt -> 173
/*     */     //   169: iconst_1
/*     */     //   170: goto -> 174
/*     */     //   173: iconst_0
/*     */     //   174: ior
/*     */     //   175: istore #12
/*     */     //   177: iload #10
/*     */     //   179: istore #8
/*     */     //   181: iload #10
/*     */     //   183: iload #4
/*     */     //   185: iconst_4
/*     */     //   186: isub
/*     */     //   187: if_icmpge -> 226
/*     */     //   190: aload_1
/*     */     //   191: iload #10
/*     */     //   193: iconst_1
/*     */     //   194: iadd
/*     */     //   195: invokestatic tryToParseFourDigits : (Ljava/lang/CharSequence;I)I
/*     */     //   198: istore #15
/*     */     //   200: iload #15
/*     */     //   202: ifge -> 208
/*     */     //   205: goto -> 226
/*     */     //   208: ldc2_w 10000
/*     */     //   211: lload #5
/*     */     //   213: lmul
/*     */     //   214: iload #15
/*     */     //   216: i2l
/*     */     //   217: ladd
/*     */     //   218: lstore #5
/*     */     //   220: iinc #10, 4
/*     */     //   223: goto -> 181
/*     */     //   226: iinc #10, 1
/*     */     //   229: goto -> 108
/*     */     //   232: iload #10
/*     */     //   234: istore #15
/*     */     //   236: iload #8
/*     */     //   238: ifge -> 258
/*     */     //   241: iload #15
/*     */     //   243: iload #7
/*     */     //   245: isub
/*     */     //   246: istore #14
/*     */     //   248: iload #15
/*     */     //   250: istore #8
/*     */     //   252: lconst_0
/*     */     //   253: lstore #16
/*     */     //   255: goto -> 277
/*     */     //   258: iload #15
/*     */     //   260: iload #7
/*     */     //   262: isub
/*     */     //   263: iconst_1
/*     */     //   264: isub
/*     */     //   265: istore #14
/*     */     //   267: iload #8
/*     */     //   269: iload #15
/*     */     //   271: isub
/*     */     //   272: iconst_1
/*     */     //   273: iadd
/*     */     //   274: i2l
/*     */     //   275: lstore #16
/*     */     //   277: lconst_0
/*     */     //   278: lstore #18
/*     */     //   280: iload #11
/*     */     //   282: bipush #32
/*     */     //   284: ior
/*     */     //   285: bipush #101
/*     */     //   287: if_icmpne -> 440
/*     */     //   290: iload #10
/*     */     //   292: istore #9
/*     */     //   294: aload_1
/*     */     //   295: iinc #10, 1
/*     */     //   298: iload #10
/*     */     //   300: iload #4
/*     */     //   302: invokestatic charAt : (Ljava/lang/CharSequence;II)C
/*     */     //   305: istore #11
/*     */     //   307: iload #11
/*     */     //   309: bipush #45
/*     */     //   311: if_icmpne -> 318
/*     */     //   314: iconst_1
/*     */     //   315: goto -> 319
/*     */     //   318: iconst_0
/*     */     //   319: istore #20
/*     */     //   321: iload #20
/*     */     //   323: ifne -> 333
/*     */     //   326: iload #11
/*     */     //   328: bipush #43
/*     */     //   330: if_icmpne -> 346
/*     */     //   333: aload_1
/*     */     //   334: iinc #10, 1
/*     */     //   337: iload #10
/*     */     //   339: iload #4
/*     */     //   341: invokestatic charAt : (Ljava/lang/CharSequence;II)C
/*     */     //   344: istore #11
/*     */     //   346: iload #11
/*     */     //   348: bipush #48
/*     */     //   350: isub
/*     */     //   351: i2c
/*     */     //   352: istore #21
/*     */     //   354: iload #12
/*     */     //   356: iload #21
/*     */     //   358: bipush #10
/*     */     //   360: if_icmplt -> 367
/*     */     //   363: iconst_1
/*     */     //   364: goto -> 368
/*     */     //   367: iconst_0
/*     */     //   368: ior
/*     */     //   369: istore #12
/*     */     //   371: lload #18
/*     */     //   373: ldc2_w 2147483647
/*     */     //   376: lcmp
/*     */     //   377: ifge -> 392
/*     */     //   380: ldc2_w 10
/*     */     //   383: lload #18
/*     */     //   385: lmul
/*     */     //   386: iload #21
/*     */     //   388: i2l
/*     */     //   389: ladd
/*     */     //   390: lstore #18
/*     */     //   392: aload_1
/*     */     //   393: iinc #10, 1
/*     */     //   396: iload #10
/*     */     //   398: iload #4
/*     */     //   400: invokestatic charAt : (Ljava/lang/CharSequence;II)C
/*     */     //   403: istore #11
/*     */     //   405: iload #11
/*     */     //   407: bipush #48
/*     */     //   409: isub
/*     */     //   410: i2c
/*     */     //   411: istore #21
/*     */     //   413: iload #21
/*     */     //   415: bipush #10
/*     */     //   417: if_icmplt -> 371
/*     */     //   420: iload #20
/*     */     //   422: ifeq -> 430
/*     */     //   425: lload #18
/*     */     //   427: lneg
/*     */     //   428: lstore #18
/*     */     //   430: lload #16
/*     */     //   432: lload #18
/*     */     //   434: ladd
/*     */     //   435: lstore #16
/*     */     //   437: goto -> 444
/*     */     //   440: iload #4
/*     */     //   442: istore #9
/*     */     //   444: iload #12
/*     */     //   446: iload #14
/*     */     //   448: ifne -> 455
/*     */     //   451: iconst_1
/*     */     //   452: goto -> 456
/*     */     //   455: iconst_0
/*     */     //   456: ior
/*     */     //   457: istore #12
/*     */     //   459: iload #12
/*     */     //   461: iload #10
/*     */     //   463: iload #4
/*     */     //   465: iload #14
/*     */     //   467: lload #16
/*     */     //   469: invokestatic checkParsedBigDecimalBounds : (ZIIIJ)V
/*     */     //   472: iload #14
/*     */     //   474: bipush #19
/*     */     //   476: if_icmpge -> 506
/*     */     //   479: new java/math/BigDecimal
/*     */     //   482: dup
/*     */     //   483: iload #13
/*     */     //   485: ifeq -> 494
/*     */     //   488: lload #5
/*     */     //   490: lneg
/*     */     //   491: goto -> 496
/*     */     //   494: lload #5
/*     */     //   496: invokespecial <init> : (J)V
/*     */     //   499: lload #16
/*     */     //   501: l2i
/*     */     //   502: invokevirtual scaleByPowerOfTen : (I)Ljava/math/BigDecimal;
/*     */     //   505: areturn
/*     */     //   506: aload_0
/*     */     //   507: aload_1
/*     */     //   508: iload #7
/*     */     //   510: iload #8
/*     */     //   512: iload #8
/*     */     //   514: iconst_1
/*     */     //   515: iadd
/*     */     //   516: iload #9
/*     */     //   518: iload #13
/*     */     //   520: lload #16
/*     */     //   522: l2i
/*     */     //   523: invokevirtual valueOfBigDecimalString : (Ljava/lang/CharSequence;IIIIZI)Ljava/math/BigDecimal;
/*     */     //   526: areturn
/*     */     //   527: astore #4
/*     */     //   529: new java/lang/NumberFormatException
/*     */     //   532: dup
/*     */     //   533: ldc 'value exceeds limits'
/*     */     //   535: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   538: astore #5
/*     */     //   540: aload #5
/*     */     //   542: aload #4
/*     */     //   544: invokevirtual initCause : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
/*     */     //   547: pop
/*     */     //   548: aload #5
/*     */     //   550: athrow
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #41	-> 0
/*     */     //   #42	-> 13
/*     */     //   #43	-> 20
/*     */     //   #45	-> 28
/*     */     //   #47	-> 31
/*     */     //   #50	-> 34
/*     */     //   #51	-> 37
/*     */     //   #52	-> 47
/*     */     //   #57	-> 50
/*     */     //   #58	-> 64
/*     */     //   #59	-> 76
/*     */     //   #60	-> 89
/*     */     //   #61	-> 94
/*     */     //   #66	-> 104
/*     */     //   #67	-> 108
/*     */     //   #68	-> 115
/*     */     //   #69	-> 125
/*     */     //   #70	-> 133
/*     */     //   #72	-> 140
/*     */     //   #73	-> 155
/*     */     //   #74	-> 162
/*     */     //   #75	-> 177
/*     */     //   #76	-> 181
/*     */     //   #77	-> 190
/*     */     //   #78	-> 200
/*     */     //   #79	-> 205
/*     */     //   #82	-> 208
/*     */     //   #76	-> 220
/*     */     //   #67	-> 226
/*     */     //   #90	-> 232
/*     */     //   #92	-> 236
/*     */     //   #93	-> 241
/*     */     //   #94	-> 248
/*     */     //   #95	-> 252
/*     */     //   #97	-> 258
/*     */     //   #98	-> 267
/*     */     //   #103	-> 277
/*     */     //   #104	-> 280
/*     */     //   #105	-> 290
/*     */     //   #106	-> 294
/*     */     //   #107	-> 307
/*     */     //   #108	-> 321
/*     */     //   #109	-> 333
/*     */     //   #111	-> 346
/*     */     //   #112	-> 354
/*     */     //   #115	-> 371
/*     */     //   #116	-> 380
/*     */     //   #118	-> 392
/*     */     //   #119	-> 405
/*     */     //   #120	-> 413
/*     */     //   #121	-> 420
/*     */     //   #122	-> 425
/*     */     //   #124	-> 430
/*     */     //   #125	-> 437
/*     */     //   #126	-> 440
/*     */     //   #128	-> 444
/*     */     //   #129	-> 459
/*     */     //   #130	-> 472
/*     */     //   #131	-> 479
/*     */     //   #133	-> 506
/*     */     //   #134	-> 527
/*     */     //   #135	-> 529
/*     */     //   #136	-> 540
/*     */     //   #137	-> 548
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   200	20	15	digits	I
/*     */     //   133	93	14	digit	I
/*     */     //   248	10	14	digitCount	I
/*     */     //   255	3	16	exponent	J
/*     */     //   321	116	20	isExponentNegative	Z
/*     */     //   354	83	21	digit	I
/*     */     //   294	146	9	exponentIndicatorIndex	I
/*     */     //   13	514	4	endIndex	I
/*     */     //   31	496	5	significand	J
/*     */     //   108	419	7	integerPartIndex	I
/*     */     //   34	493	8	decimalPointIndex	I
/*     */     //   444	83	9	exponentIndicatorIndex	I
/*     */     //   37	490	10	index	I
/*     */     //   47	480	11	ch	C
/*     */     //   50	477	12	illegal	Z
/*     */     //   64	463	13	isNegative	Z
/*     */     //   267	260	14	digitCount	I
/*     */     //   236	291	15	significandEndIndex	I
/*     */     //   277	250	16	exponent	J
/*     */     //   280	247	18	expNumber	J
/*     */     //   540	11	5	nfe	Ljava/lang/NumberFormatException;
/*     */     //   529	22	4	e	Ljava/lang/ArithmeticException;
/*     */     //   0	551	0	this	Lch/randelshofer/fastdoubleparser/JavaBigDecimalFromCharSequence;
/*     */     //   0	551	1	str	Ljava/lang/CharSequence;
/*     */     //   0	551	2	offset	I
/*     */     //   0	551	3	length	I
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	27	527	java/lang/ArithmeticException
/*     */     //   28	505	527	java/lang/ArithmeticException
/*     */     //   506	526	527	java/lang/ArithmeticException
/*     */   }
/*     */   
/*     */   BigDecimal parseBigDecimalStringWithManyDigits(CharSequence str, int offset, int length) {
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
/*     */     //   19: invokestatic charAt : (Ljava/lang/CharSequence;II)C
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
/*     */     //   61: invokestatic charAt : (Ljava/lang/CharSequence;II)C
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
/*     */     //   107: invokestatic isEightZeroes : (Ljava/lang/CharSequence;I)Z
/*     */     //   110: ifeq -> 119
/*     */     //   113: iinc #10, 8
/*     */     //   116: goto -> 97
/*     */     //   119: iload #10
/*     */     //   121: iload #9
/*     */     //   123: if_icmpge -> 145
/*     */     //   126: aload_1
/*     */     //   127: iload #10
/*     */     //   129: invokeinterface charAt : (I)C
/*     */     //   134: bipush #48
/*     */     //   136: if_icmpne -> 145
/*     */     //   139: iinc #10, 1
/*     */     //   142: goto -> 119
/*     */     //   145: iload #10
/*     */     //   147: istore #5
/*     */     //   149: iload #10
/*     */     //   151: iload #14
/*     */     //   153: if_icmpge -> 171
/*     */     //   156: aload_1
/*     */     //   157: iload #10
/*     */     //   159: invokestatic isEightDigits : (Ljava/lang/CharSequence;I)Z
/*     */     //   162: ifeq -> 171
/*     */     //   165: iinc #10, 8
/*     */     //   168: goto -> 149
/*     */     //   171: iload #10
/*     */     //   173: iload #9
/*     */     //   175: if_icmpge -> 201
/*     */     //   178: aload_1
/*     */     //   179: iload #10
/*     */     //   181: invokeinterface charAt : (I)C
/*     */     //   186: dup
/*     */     //   187: istore #11
/*     */     //   189: invokestatic isDigit : (C)Z
/*     */     //   192: ifeq -> 201
/*     */     //   195: iinc #10, 1
/*     */     //   198: goto -> 171
/*     */     //   201: iload #11
/*     */     //   203: bipush #46
/*     */     //   205: if_icmpne -> 319
/*     */     //   208: iload #10
/*     */     //   210: iinc #10, 1
/*     */     //   213: istore #7
/*     */     //   215: iload #10
/*     */     //   217: iload #14
/*     */     //   219: if_icmpge -> 237
/*     */     //   222: aload_1
/*     */     //   223: iload #10
/*     */     //   225: invokestatic isEightZeroes : (Ljava/lang/CharSequence;I)Z
/*     */     //   228: ifeq -> 237
/*     */     //   231: iinc #10, 8
/*     */     //   234: goto -> 215
/*     */     //   237: iload #10
/*     */     //   239: iload #9
/*     */     //   241: if_icmpge -> 263
/*     */     //   244: aload_1
/*     */     //   245: iload #10
/*     */     //   247: invokeinterface charAt : (I)C
/*     */     //   252: bipush #48
/*     */     //   254: if_icmpne -> 263
/*     */     //   257: iinc #10, 1
/*     */     //   260: goto -> 237
/*     */     //   263: iload #10
/*     */     //   265: istore #6
/*     */     //   267: iload #10
/*     */     //   269: iload #14
/*     */     //   271: if_icmpge -> 289
/*     */     //   274: aload_1
/*     */     //   275: iload #10
/*     */     //   277: invokestatic isEightDigits : (Ljava/lang/CharSequence;I)Z
/*     */     //   280: ifeq -> 289
/*     */     //   283: iinc #10, 8
/*     */     //   286: goto -> 267
/*     */     //   289: iload #10
/*     */     //   291: iload #9
/*     */     //   293: if_icmpge -> 319
/*     */     //   296: aload_1
/*     */     //   297: iload #10
/*     */     //   299: invokeinterface charAt : (I)C
/*     */     //   304: dup
/*     */     //   305: istore #11
/*     */     //   307: invokestatic isDigit : (C)Z
/*     */     //   310: ifeq -> 319
/*     */     //   313: iinc #10, 1
/*     */     //   316: goto -> 289
/*     */     //   319: iload #10
/*     */     //   321: istore #16
/*     */     //   323: iload #7
/*     */     //   325: ifge -> 349
/*     */     //   328: iload #16
/*     */     //   330: iload #5
/*     */     //   332: isub
/*     */     //   333: istore #15
/*     */     //   335: iload #16
/*     */     //   337: istore #7
/*     */     //   339: iload #16
/*     */     //   341: istore #6
/*     */     //   343: lconst_0
/*     */     //   344: lstore #17
/*     */     //   346: goto -> 383
/*     */     //   349: iload #5
/*     */     //   351: iload #7
/*     */     //   353: if_icmpne -> 364
/*     */     //   356: iload #16
/*     */     //   358: iload #6
/*     */     //   360: isub
/*     */     //   361: goto -> 371
/*     */     //   364: iload #16
/*     */     //   366: iload #5
/*     */     //   368: isub
/*     */     //   369: iconst_1
/*     */     //   370: isub
/*     */     //   371: istore #15
/*     */     //   373: iload #7
/*     */     //   375: iload #16
/*     */     //   377: isub
/*     */     //   378: iconst_1
/*     */     //   379: iadd
/*     */     //   380: i2l
/*     */     //   381: lstore #17
/*     */     //   383: lconst_0
/*     */     //   384: lstore #19
/*     */     //   386: iload #11
/*     */     //   388: bipush #32
/*     */     //   390: ior
/*     */     //   391: bipush #101
/*     */     //   393: if_icmpne -> 546
/*     */     //   396: iload #10
/*     */     //   398: istore #8
/*     */     //   400: aload_1
/*     */     //   401: iinc #10, 1
/*     */     //   404: iload #10
/*     */     //   406: iload #9
/*     */     //   408: invokestatic charAt : (Ljava/lang/CharSequence;II)C
/*     */     //   411: istore #11
/*     */     //   413: iload #11
/*     */     //   415: bipush #45
/*     */     //   417: if_icmpne -> 424
/*     */     //   420: iconst_1
/*     */     //   421: goto -> 425
/*     */     //   424: iconst_0
/*     */     //   425: istore #21
/*     */     //   427: iload #21
/*     */     //   429: ifne -> 439
/*     */     //   432: iload #11
/*     */     //   434: bipush #43
/*     */     //   436: if_icmpne -> 452
/*     */     //   439: aload_1
/*     */     //   440: iinc #10, 1
/*     */     //   443: iload #10
/*     */     //   445: iload #9
/*     */     //   447: invokestatic charAt : (Ljava/lang/CharSequence;II)C
/*     */     //   450: istore #11
/*     */     //   452: iload #11
/*     */     //   454: bipush #48
/*     */     //   456: isub
/*     */     //   457: i2c
/*     */     //   458: istore #22
/*     */     //   460: iload #12
/*     */     //   462: iload #22
/*     */     //   464: bipush #10
/*     */     //   466: if_icmplt -> 473
/*     */     //   469: iconst_1
/*     */     //   470: goto -> 474
/*     */     //   473: iconst_0
/*     */     //   474: ior
/*     */     //   475: istore #12
/*     */     //   477: lload #19
/*     */     //   479: ldc2_w 2147483647
/*     */     //   482: lcmp
/*     */     //   483: ifge -> 498
/*     */     //   486: ldc2_w 10
/*     */     //   489: lload #19
/*     */     //   491: lmul
/*     */     //   492: iload #22
/*     */     //   494: i2l
/*     */     //   495: ladd
/*     */     //   496: lstore #19
/*     */     //   498: aload_1
/*     */     //   499: iinc #10, 1
/*     */     //   502: iload #10
/*     */     //   504: iload #9
/*     */     //   506: invokestatic charAt : (Ljava/lang/CharSequence;II)C
/*     */     //   509: istore #11
/*     */     //   511: iload #11
/*     */     //   513: bipush #48
/*     */     //   515: isub
/*     */     //   516: i2c
/*     */     //   517: istore #22
/*     */     //   519: iload #22
/*     */     //   521: bipush #10
/*     */     //   523: if_icmplt -> 477
/*     */     //   526: iload #21
/*     */     //   528: ifeq -> 536
/*     */     //   531: lload #19
/*     */     //   533: lneg
/*     */     //   534: lstore #19
/*     */     //   536: lload #17
/*     */     //   538: lload #19
/*     */     //   540: ladd
/*     */     //   541: lstore #17
/*     */     //   543: goto -> 550
/*     */     //   546: iload #9
/*     */     //   548: istore #8
/*     */     //   550: iload #12
/*     */     //   552: iload #4
/*     */     //   554: iload #7
/*     */     //   556: if_icmpne -> 570
/*     */     //   559: iload #7
/*     */     //   561: iload #8
/*     */     //   563: if_icmpne -> 570
/*     */     //   566: iconst_1
/*     */     //   567: goto -> 571
/*     */     //   570: iconst_0
/*     */     //   571: ior
/*     */     //   572: istore #12
/*     */     //   574: iload #12
/*     */     //   576: iload #10
/*     */     //   578: iload #9
/*     */     //   580: iload #15
/*     */     //   582: lload #17
/*     */     //   584: invokestatic checkParsedBigDecimalBounds : (ZIIIJ)V
/*     */     //   587: aload_0
/*     */     //   588: aload_1
/*     */     //   589: iload #5
/*     */     //   591: iload #7
/*     */     //   593: iload #6
/*     */     //   595: iload #8
/*     */     //   597: iload #13
/*     */     //   599: lload #17
/*     */     //   601: l2i
/*     */     //   602: invokevirtual valueOfBigDecimalString : (Ljava/lang/CharSequence;IIIIZI)Ljava/math/BigDecimal;
/*     */     //   605: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #147	-> 0
/*     */     //   #148	-> 3
/*     */     //   #151	-> 6
/*     */     //   #152	-> 11
/*     */     //   #153	-> 14
/*     */     //   #154	-> 24
/*     */     //   #158	-> 27
/*     */     //   #159	-> 41
/*     */     //   #160	-> 53
/*     */     //   #161	-> 66
/*     */     //   #162	-> 71
/*     */     //   #169	-> 81
/*     */     //   #171	-> 85
/*     */     //   #172	-> 97
/*     */     //   #173	-> 113
/*     */     //   #175	-> 119
/*     */     //   #176	-> 139
/*     */     //   #179	-> 145
/*     */     //   #180	-> 149
/*     */     //   #181	-> 165
/*     */     //   #183	-> 171
/*     */     //   #184	-> 195
/*     */     //   #186	-> 201
/*     */     //   #187	-> 208
/*     */     //   #189	-> 215
/*     */     //   #190	-> 231
/*     */     //   #192	-> 237
/*     */     //   #193	-> 257
/*     */     //   #195	-> 263
/*     */     //   #197	-> 267
/*     */     //   #198	-> 283
/*     */     //   #200	-> 289
/*     */     //   #201	-> 313
/*     */     //   #206	-> 319
/*     */     //   #208	-> 323
/*     */     //   #209	-> 328
/*     */     //   #210	-> 335
/*     */     //   #211	-> 339
/*     */     //   #212	-> 343
/*     */     //   #214	-> 349
/*     */     //   #215	-> 356
/*     */     //   #216	-> 364
/*     */     //   #217	-> 373
/*     */     //   #222	-> 383
/*     */     //   #223	-> 386
/*     */     //   #224	-> 396
/*     */     //   #225	-> 400
/*     */     //   #226	-> 413
/*     */     //   #227	-> 427
/*     */     //   #228	-> 439
/*     */     //   #230	-> 452
/*     */     //   #231	-> 460
/*     */     //   #234	-> 477
/*     */     //   #235	-> 486
/*     */     //   #237	-> 498
/*     */     //   #238	-> 511
/*     */     //   #239	-> 519
/*     */     //   #240	-> 526
/*     */     //   #241	-> 531
/*     */     //   #243	-> 536
/*     */     //   #244	-> 543
/*     */     //   #245	-> 546
/*     */     //   #247	-> 550
/*     */     //   #248	-> 574
/*     */     //   #250	-> 587
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   335	14	15	digitCountWithoutLeadingZeros	I
/*     */     //   346	3	17	exponent	J
/*     */     //   427	116	21	isExponentNegative	Z
/*     */     //   460	83	22	digit	I
/*     */     //   400	146	8	exponentIndicatorIndex	I
/*     */     //   0	606	0	this	Lch/randelshofer/fastdoubleparser/JavaBigDecimalFromCharSequence;
/*     */     //   0	606	1	str	Ljava/lang/CharSequence;
/*     */     //   0	606	2	offset	I
/*     */     //   0	606	3	length	I
/*     */     //   85	521	4	integerPartIndex	I
/*     */     //   149	457	5	nonZeroIntegerPartIndex	I
/*     */     //   3	603	6	nonZeroFractionalPartIndex	I
/*     */     //   6	600	7	decimalPointIndex	I
/*     */     //   550	56	8	exponentIndicatorIndex	I
/*     */     //   11	595	9	endIndex	I
/*     */     //   14	592	10	index	I
/*     */     //   24	582	11	ch	C
/*     */     //   27	579	12	illegal	Z
/*     */     //   41	565	13	isNegative	Z
/*     */     //   97	509	14	swarLimit	I
/*     */     //   373	233	15	digitCountWithoutLeadingZeros	I
/*     */     //   323	283	16	significandEndIndex	I
/*     */     //   383	223	17	exponent	J
/*     */     //   386	220	19	expNumber	J
/*     */   }
/*     */   
/*     */   BigDecimal valueOfBigDecimalString(CharSequence str, int integerPartIndex, int decimalPointIndex, int nonZeroFractionalPartIndex, int exponentIndicatorIndex, boolean isNegative, int exponent) {
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
/* 294 */         integerPart = ParseDigitsTaskCharSequence.parseDigitsRecursive(str, integerPartIndex, decimalPointIndex, powersOfTen, 400);
/*     */       } else {
/* 296 */         integerPart = ParseDigitsTaskCharSequence.parseDigitsIterative(str, integerPartIndex, decimalPointIndex);
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
/* 311 */         fractionalPart = ParseDigitsTaskCharSequence.parseDigitsRecursive(str, nonZeroFractionalPartIndex, exponentIndicatorIndex, powersOfTen, 400);
/*     */       } else {
/* 313 */         fractionalPart = ParseDigitsTaskCharSequence.parseDigitsIterative(str, nonZeroFractionalPartIndex, exponentIndicatorIndex);
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaBigDecimalFromCharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */