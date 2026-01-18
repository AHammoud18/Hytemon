/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public abstract class Interaction
/*     */ {
/*     */   public static final int MAX_SIZE = 1677721605;
/*     */   @Nonnull
/*  16 */   public WaitForDataFrom waitForDataFrom = WaitForDataFrom.Client; @Nullable
/*     */   public InteractionEffects effects; public float horizontalSpeedMultiplier; public float runTime; public boolean cancelOnItemChange;
/*     */   @Nullable
/*     */   public Map<GameMode, InteractionSettings> settings;
/*     */   @Nullable
/*     */   public InteractionRules rules;
/*     */   @Nullable
/*     */   public int[] tags;
/*     */   @Nullable
/*     */   public InteractionCameraSettings camera;
/*     */   
/*     */   @Nonnull
/*     */   public static Interaction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  29 */     int typeId = VarInt.peek(buf, offset);
/*  30 */     int typeIdLen = VarInt.length(buf, offset);
/*     */     
/*  32 */     switch (typeId) { case 0: 
/*     */       case 1: 
/*     */       case 2: 
/*     */       case 3: 
/*     */       case 4: 
/*     */       case 5: 
/*     */       case 6: 
/*     */       case 7: 
/*     */       case 8: 
/*     */       case 9: 
/*     */       case 10: 
/*     */       case 11: 
/*     */       case 12: 
/*     */       case 13: 
/*     */       case 14: 
/*     */       case 15: 
/*     */       case 16: 
/*     */       case 17: 
/*     */       case 18: 
/*     */       case 19: 
/*     */       case 20: 
/*     */       case 21: 
/*     */       case 22: 
/*     */       case 23: 
/*     */       case 24: 
/*     */       case 25: 
/*     */       case 26: 
/*     */       case 27: 
/*     */       case 28: 
/*     */       case 29: 
/*     */       case 30: 
/*     */       case 31: 
/*     */       case 32: 
/*     */       case 33: 
/*     */       case 34: 
/*     */       case 35: 
/*     */       case 36: 
/*     */       case 37: 
/*     */       case 38: 
/*     */       case 39: 
/*     */       case 40: 
/*     */       case 41: 
/*     */       case 42: 
/*     */       case 43:
/*     */       
/*     */       case 44:
/*  78 */        }  throw ProtocolException.unknownPolymorphicType("Interaction", typeId);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: iload_1
/*     */     //   2: invokestatic peek : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   5: istore_2
/*     */     //   6: aload_0
/*     */     //   7: iload_1
/*     */     //   8: invokestatic length : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   11: istore_3
/*     */     //   12: iload_3
/*     */     //   13: iload_2
/*     */     //   14: tableswitch default -> 658, 0 -> 208, 1 -> 218, 2 -> 228, 3 -> 238, 4 -> 248, 5 -> 258, 6 -> 268, 7 -> 278, 8 -> 288, 9 -> 298, 10 -> 308, 11 -> 318, 12 -> 328, 13 -> 338, 14 -> 348, 15 -> 358, 16 -> 368, 17 -> 378, 18 -> 388, 19 -> 398, 20 -> 408, 21 -> 418, 22 -> 428, 23 -> 438, 24 -> 448, 25 -> 458, 26 -> 468, 27 -> 478, 28 -> 488, 29 -> 498, 30 -> 508, 31 -> 518, 32 -> 528, 33 -> 538, 34 -> 548, 35 -> 558, 36 -> 568, 37 -> 578, 38 -> 588, 39 -> 598, 40 -> 608, 41 -> 618, 42 -> 628, 43 -> 638, 44 -> 648
/*     */     //   208: aload_0
/*     */     //   209: iload_1
/*     */     //   210: iload_3
/*     */     //   211: iadd
/*     */     //   212: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   215: goto -> 665
/*     */     //   218: aload_0
/*     */     //   219: iload_1
/*     */     //   220: iload_3
/*     */     //   221: iadd
/*     */     //   222: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   225: goto -> 665
/*     */     //   228: aload_0
/*     */     //   229: iload_1
/*     */     //   230: iload_3
/*     */     //   231: iadd
/*     */     //   232: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   235: goto -> 665
/*     */     //   238: aload_0
/*     */     //   239: iload_1
/*     */     //   240: iload_3
/*     */     //   241: iadd
/*     */     //   242: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   245: goto -> 665
/*     */     //   248: aload_0
/*     */     //   249: iload_1
/*     */     //   250: iload_3
/*     */     //   251: iadd
/*     */     //   252: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   255: goto -> 665
/*     */     //   258: aload_0
/*     */     //   259: iload_1
/*     */     //   260: iload_3
/*     */     //   261: iadd
/*     */     //   262: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   265: goto -> 665
/*     */     //   268: aload_0
/*     */     //   269: iload_1
/*     */     //   270: iload_3
/*     */     //   271: iadd
/*     */     //   272: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   275: goto -> 665
/*     */     //   278: aload_0
/*     */     //   279: iload_1
/*     */     //   280: iload_3
/*     */     //   281: iadd
/*     */     //   282: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   285: goto -> 665
/*     */     //   288: aload_0
/*     */     //   289: iload_1
/*     */     //   290: iload_3
/*     */     //   291: iadd
/*     */     //   292: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   295: goto -> 665
/*     */     //   298: aload_0
/*     */     //   299: iload_1
/*     */     //   300: iload_3
/*     */     //   301: iadd
/*     */     //   302: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   305: goto -> 665
/*     */     //   308: aload_0
/*     */     //   309: iload_1
/*     */     //   310: iload_3
/*     */     //   311: iadd
/*     */     //   312: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   315: goto -> 665
/*     */     //   318: aload_0
/*     */     //   319: iload_1
/*     */     //   320: iload_3
/*     */     //   321: iadd
/*     */     //   322: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   325: goto -> 665
/*     */     //   328: aload_0
/*     */     //   329: iload_1
/*     */     //   330: iload_3
/*     */     //   331: iadd
/*     */     //   332: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   335: goto -> 665
/*     */     //   338: aload_0
/*     */     //   339: iload_1
/*     */     //   340: iload_3
/*     */     //   341: iadd
/*     */     //   342: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   345: goto -> 665
/*     */     //   348: aload_0
/*     */     //   349: iload_1
/*     */     //   350: iload_3
/*     */     //   351: iadd
/*     */     //   352: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   355: goto -> 665
/*     */     //   358: aload_0
/*     */     //   359: iload_1
/*     */     //   360: iload_3
/*     */     //   361: iadd
/*     */     //   362: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   365: goto -> 665
/*     */     //   368: aload_0
/*     */     //   369: iload_1
/*     */     //   370: iload_3
/*     */     //   371: iadd
/*     */     //   372: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   375: goto -> 665
/*     */     //   378: aload_0
/*     */     //   379: iload_1
/*     */     //   380: iload_3
/*     */     //   381: iadd
/*     */     //   382: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   385: goto -> 665
/*     */     //   388: aload_0
/*     */     //   389: iload_1
/*     */     //   390: iload_3
/*     */     //   391: iadd
/*     */     //   392: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   395: goto -> 665
/*     */     //   398: aload_0
/*     */     //   399: iload_1
/*     */     //   400: iload_3
/*     */     //   401: iadd
/*     */     //   402: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   405: goto -> 665
/*     */     //   408: aload_0
/*     */     //   409: iload_1
/*     */     //   410: iload_3
/*     */     //   411: iadd
/*     */     //   412: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   415: goto -> 665
/*     */     //   418: aload_0
/*     */     //   419: iload_1
/*     */     //   420: iload_3
/*     */     //   421: iadd
/*     */     //   422: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   425: goto -> 665
/*     */     //   428: aload_0
/*     */     //   429: iload_1
/*     */     //   430: iload_3
/*     */     //   431: iadd
/*     */     //   432: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   435: goto -> 665
/*     */     //   438: aload_0
/*     */     //   439: iload_1
/*     */     //   440: iload_3
/*     */     //   441: iadd
/*     */     //   442: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   445: goto -> 665
/*     */     //   448: aload_0
/*     */     //   449: iload_1
/*     */     //   450: iload_3
/*     */     //   451: iadd
/*     */     //   452: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   455: goto -> 665
/*     */     //   458: aload_0
/*     */     //   459: iload_1
/*     */     //   460: iload_3
/*     */     //   461: iadd
/*     */     //   462: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   465: goto -> 665
/*     */     //   468: aload_0
/*     */     //   469: iload_1
/*     */     //   470: iload_3
/*     */     //   471: iadd
/*     */     //   472: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   475: goto -> 665
/*     */     //   478: aload_0
/*     */     //   479: iload_1
/*     */     //   480: iload_3
/*     */     //   481: iadd
/*     */     //   482: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   485: goto -> 665
/*     */     //   488: aload_0
/*     */     //   489: iload_1
/*     */     //   490: iload_3
/*     */     //   491: iadd
/*     */     //   492: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   495: goto -> 665
/*     */     //   498: aload_0
/*     */     //   499: iload_1
/*     */     //   500: iload_3
/*     */     //   501: iadd
/*     */     //   502: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   505: goto -> 665
/*     */     //   508: aload_0
/*     */     //   509: iload_1
/*     */     //   510: iload_3
/*     */     //   511: iadd
/*     */     //   512: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   515: goto -> 665
/*     */     //   518: aload_0
/*     */     //   519: iload_1
/*     */     //   520: iload_3
/*     */     //   521: iadd
/*     */     //   522: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   525: goto -> 665
/*     */     //   528: aload_0
/*     */     //   529: iload_1
/*     */     //   530: iload_3
/*     */     //   531: iadd
/*     */     //   532: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   535: goto -> 665
/*     */     //   538: aload_0
/*     */     //   539: iload_1
/*     */     //   540: iload_3
/*     */     //   541: iadd
/*     */     //   542: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   545: goto -> 665
/*     */     //   548: aload_0
/*     */     //   549: iload_1
/*     */     //   550: iload_3
/*     */     //   551: iadd
/*     */     //   552: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   555: goto -> 665
/*     */     //   558: aload_0
/*     */     //   559: iload_1
/*     */     //   560: iload_3
/*     */     //   561: iadd
/*     */     //   562: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   565: goto -> 665
/*     */     //   568: aload_0
/*     */     //   569: iload_1
/*     */     //   570: iload_3
/*     */     //   571: iadd
/*     */     //   572: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   575: goto -> 665
/*     */     //   578: aload_0
/*     */     //   579: iload_1
/*     */     //   580: iload_3
/*     */     //   581: iadd
/*     */     //   582: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   585: goto -> 665
/*     */     //   588: aload_0
/*     */     //   589: iload_1
/*     */     //   590: iload_3
/*     */     //   591: iadd
/*     */     //   592: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   595: goto -> 665
/*     */     //   598: aload_0
/*     */     //   599: iload_1
/*     */     //   600: iload_3
/*     */     //   601: iadd
/*     */     //   602: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   605: goto -> 665
/*     */     //   608: aload_0
/*     */     //   609: iload_1
/*     */     //   610: iload_3
/*     */     //   611: iadd
/*     */     //   612: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   615: goto -> 665
/*     */     //   618: aload_0
/*     */     //   619: iload_1
/*     */     //   620: iload_3
/*     */     //   621: iadd
/*     */     //   622: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   625: goto -> 665
/*     */     //   628: aload_0
/*     */     //   629: iload_1
/*     */     //   630: iload_3
/*     */     //   631: iadd
/*     */     //   632: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   635: goto -> 665
/*     */     //   638: aload_0
/*     */     //   639: iload_1
/*     */     //   640: iload_3
/*     */     //   641: iadd
/*     */     //   642: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   645: goto -> 665
/*     */     //   648: aload_0
/*     */     //   649: iload_1
/*     */     //   650: iload_3
/*     */     //   651: iadd
/*     */     //   652: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*     */     //   655: goto -> 665
/*     */     //   658: ldc 'Interaction'
/*     */     //   660: iload_2
/*     */     //   661: invokestatic unknownPolymorphicType : (Ljava/lang/String;I)Lcom/hypixel/hytale/protocol/io/ProtocolException;
/*     */     //   664: athrow
/*     */     //   665: iadd
/*     */     //   666: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #83	-> 0
/*     */     //   #84	-> 6
/*     */     //   #86	-> 12
/*     */     //   #87	-> 208
/*     */     //   #88	-> 218
/*     */     //   #89	-> 228
/*     */     //   #90	-> 238
/*     */     //   #91	-> 248
/*     */     //   #92	-> 258
/*     */     //   #93	-> 268
/*     */     //   #94	-> 278
/*     */     //   #95	-> 288
/*     */     //   #96	-> 298
/*     */     //   #97	-> 308
/*     */     //   #98	-> 318
/*     */     //   #99	-> 328
/*     */     //   #100	-> 338
/*     */     //   #101	-> 348
/*     */     //   #102	-> 358
/*     */     //   #103	-> 368
/*     */     //   #104	-> 378
/*     */     //   #105	-> 388
/*     */     //   #106	-> 398
/*     */     //   #107	-> 408
/*     */     //   #108	-> 418
/*     */     //   #109	-> 428
/*     */     //   #110	-> 438
/*     */     //   #111	-> 448
/*     */     //   #112	-> 458
/*     */     //   #113	-> 468
/*     */     //   #114	-> 478
/*     */     //   #115	-> 488
/*     */     //   #116	-> 498
/*     */     //   #117	-> 508
/*     */     //   #118	-> 518
/*     */     //   #119	-> 528
/*     */     //   #120	-> 538
/*     */     //   #121	-> 548
/*     */     //   #122	-> 558
/*     */     //   #123	-> 568
/*     */     //   #124	-> 578
/*     */     //   #125	-> 588
/*     */     //   #126	-> 598
/*     */     //   #127	-> 608
/*     */     //   #128	-> 618
/*     */     //   #129	-> 628
/*     */     //   #130	-> 638
/*     */     //   #131	-> 648
/*     */     //   #132	-> 658
/*     */     //   #133	-> 665
/*     */     //   #86	-> 666
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	667	0	buf	Lio/netty/buffer/ByteBuf;
/*     */     //   0	667	1	offset	I
/*     */     //   6	661	2	typeId	I
/*     */     //   12	655	3	typeIdLen	I
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTypeId() {
/* 137 */     Interaction interaction = this; if (interaction instanceof BreakBlockInteraction) { BreakBlockInteraction sub = (BreakBlockInteraction)interaction; return 3; }
/* 138 */      interaction = this; if (interaction instanceof PickBlockInteraction) { PickBlockInteraction sub = (PickBlockInteraction)interaction; return 4; }
/* 139 */      interaction = this; if (interaction instanceof UseBlockInteraction) { UseBlockInteraction sub = (UseBlockInteraction)interaction; return 5; }
/* 140 */      interaction = this; if (interaction instanceof BlockConditionInteraction) { BlockConditionInteraction sub = (BlockConditionInteraction)interaction; return 14; }
/* 141 */      interaction = this; if (interaction instanceof ChangeBlockInteraction) { ChangeBlockInteraction sub = (ChangeBlockInteraction)interaction; return 16; }
/* 142 */      interaction = this; if (interaction instanceof ChangeStateInteraction) { ChangeStateInteraction sub = (ChangeStateInteraction)interaction; return 17; }
/* 143 */      interaction = this; if (interaction instanceof RefillContainerInteraction) { RefillContainerInteraction sub = (RefillContainerInteraction)interaction; return 19; }
/* 144 */      interaction = this; if (interaction instanceof SimpleBlockInteraction) { SimpleBlockInteraction sub = (SimpleBlockInteraction)interaction; return 0; }
/* 145 */      interaction = this; if (interaction instanceof PlaceBlockInteraction) { PlaceBlockInteraction sub = (PlaceBlockInteraction)interaction; return 2; }
/* 146 */      interaction = this; if (interaction instanceof UseEntityInteraction) { UseEntityInteraction sub = (UseEntityInteraction)interaction; return 6; }
/* 147 */      interaction = this; if (interaction instanceof BuilderToolInteraction) { BuilderToolInteraction sub = (BuilderToolInteraction)interaction; return 7; }
/* 148 */      interaction = this; if (interaction instanceof ModifyInventoryInteraction) { ModifyInventoryInteraction sub = (ModifyInventoryInteraction)interaction; return 8; }
/* 149 */      interaction = this; if (interaction instanceof WieldingInteraction) { WieldingInteraction sub = (WieldingInteraction)interaction; return 10; }
/* 150 */      interaction = this; if (interaction instanceof ConditionInteraction) { ConditionInteraction sub = (ConditionInteraction)interaction; return 12; }
/* 151 */      interaction = this; if (interaction instanceof StatsConditionInteraction) { StatsConditionInteraction sub = (StatsConditionInteraction)interaction; return 13; }
/* 152 */      interaction = this; if (interaction instanceof SelectInteraction) { SelectInteraction sub = (SelectInteraction)interaction; return 20; }
/* 153 */      interaction = this; if (interaction instanceof RepeatInteraction) { RepeatInteraction sub = (RepeatInteraction)interaction; return 22; }
/* 154 */      interaction = this; if (interaction instanceof EffectConditionInteraction) { EffectConditionInteraction sub = (EffectConditionInteraction)interaction; return 25; }
/* 155 */      interaction = this; if (interaction instanceof ApplyForceInteraction) { ApplyForceInteraction sub = (ApplyForceInteraction)interaction; return 26; }
/* 156 */      interaction = this; if (interaction instanceof ApplyEffectInteraction) { ApplyEffectInteraction sub = (ApplyEffectInteraction)interaction; return 27; }
/* 157 */      interaction = this; if (interaction instanceof ClearEntityEffectInteraction) { ClearEntityEffectInteraction sub = (ClearEntityEffectInteraction)interaction; return 28; }
/* 158 */      interaction = this; if (interaction instanceof ChangeStatInteraction) { ChangeStatInteraction sub = (ChangeStatInteraction)interaction; return 30; }
/* 159 */      interaction = this; if (interaction instanceof MovementConditionInteraction) { MovementConditionInteraction sub = (MovementConditionInteraction)interaction; return 31; }
/* 160 */      interaction = this; if (interaction instanceof ProjectileInteraction) { ProjectileInteraction sub = (ProjectileInteraction)interaction; return 32; }
/* 161 */      interaction = this; if (interaction instanceof RemoveEntityInteraction) { RemoveEntityInteraction sub = (RemoveEntityInteraction)interaction; return 33; }
/* 162 */      interaction = this; if (interaction instanceof ResetCooldownInteraction) { ResetCooldownInteraction sub = (ResetCooldownInteraction)interaction; return 34; }
/* 163 */      interaction = this; if (interaction instanceof TriggerCooldownInteraction) { TriggerCooldownInteraction sub = (TriggerCooldownInteraction)interaction; return 35; }
/* 164 */      interaction = this; if (interaction instanceof CooldownConditionInteraction) { CooldownConditionInteraction sub = (CooldownConditionInteraction)interaction; return 36; }
/* 165 */      interaction = this; if (interaction instanceof ChainFlagInteraction) { ChainFlagInteraction sub = (ChainFlagInteraction)interaction; return 37; }
/* 166 */      interaction = this; if (interaction instanceof IncrementCooldownInteraction) { IncrementCooldownInteraction sub = (IncrementCooldownInteraction)interaction; return 38; }
/* 167 */      interaction = this; if (interaction instanceof CancelChainInteraction) { CancelChainInteraction sub = (CancelChainInteraction)interaction; return 39; }
/* 168 */      interaction = this; if (interaction instanceof RunRootInteraction) { RunRootInteraction sub = (RunRootInteraction)interaction; return 40; }
/* 169 */      interaction = this; if (interaction instanceof CameraInteraction) { CameraInteraction sub = (CameraInteraction)interaction; return 41; }
/* 170 */      interaction = this; if (interaction instanceof SpawnDeployableFromRaycastInteraction) { SpawnDeployableFromRaycastInteraction sub = (SpawnDeployableFromRaycastInteraction)interaction; return 42; }
/* 171 */      interaction = this; if (interaction instanceof ToggleGliderInteraction) { ToggleGliderInteraction sub = (ToggleGliderInteraction)interaction; return 44; }
/* 172 */      interaction = this; if (interaction instanceof SimpleInteraction) { SimpleInteraction sub = (SimpleInteraction)interaction; return 1; }
/* 173 */      interaction = this; if (interaction instanceof ChargingInteraction) { ChargingInteraction sub = (ChargingInteraction)interaction; return 9; }
/* 174 */      interaction = this; if (interaction instanceof ChainingInteraction) { ChainingInteraction sub = (ChainingInteraction)interaction; return 11; }
/* 175 */      interaction = this; if (interaction instanceof ReplaceInteraction) { ReplaceInteraction sub = (ReplaceInteraction)interaction; return 15; }
/* 176 */      interaction = this; if (interaction instanceof FirstClickInteraction) { FirstClickInteraction sub = (FirstClickInteraction)interaction; return 18; }
/* 177 */      interaction = this; if (interaction instanceof DamageEntityInteraction) { DamageEntityInteraction sub = (DamageEntityInteraction)interaction; return 21; }
/* 178 */      interaction = this; if (interaction instanceof ParallelInteraction) { ParallelInteraction sub = (ParallelInteraction)interaction; return 23; }
/* 179 */      interaction = this; if (interaction instanceof ChangeActiveSlotInteraction) { ChangeActiveSlotInteraction sub = (ChangeActiveSlotInteraction)interaction; return 24; }
/* 180 */      interaction = this; if (interaction instanceof SerialInteraction) { SerialInteraction sub = (SerialInteraction)interaction; return 29; }
/* 181 */      interaction = this; if (interaction instanceof MemoriesConditionInteraction) { MemoriesConditionInteraction sub = (MemoriesConditionInteraction)interaction; return 43; }
/* 182 */      throw new IllegalStateException("Unknown subtype: " + getClass().getName());
/*     */   }
/*     */   public abstract int serialize(@Nonnull ByteBuf paramByteBuf);
/*     */   
/*     */   public abstract int computeSize();
/*     */   
/*     */   public int serializeWithTypeId(@Nonnull ByteBuf buf) {
/* 189 */     int startPos = buf.writerIndex();
/* 190 */     VarInt.write(buf, getTypeId());
/* 191 */     serialize(buf);
/* 192 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */   
/*     */   public int computeSizeWithTypeId() {
/* 196 */     return VarInt.size(getTypeId()) + computeSize();
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 200 */     int typeId = VarInt.peek(buffer, offset);
/* 201 */     int typeIdLen = VarInt.length(buffer, offset);
/*     */     
/* 203 */     switch (typeId) { case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 32: case 33: case 34: case 35: case 36: case 37: case 38: case 39: case 40: case 41: case 42: case 43: case 44:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       ValidationResult.error("Unknown polymorphic type ID " + typeId + " for Interaction");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Interaction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */