/*     */ package com.hypixel.hytale.server.core.util;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.FormattedMessage;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.ParamValue;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.FailureReply;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.SuccessReply;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.jline.utils.AttributedString;
/*     */ import org.jline.utils.AttributedStringBuilder;
/*     */ import org.jline.utils.AttributedStyle;
/*     */ import org.jline.utils.Colors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageUtil
/*     */ {
/*     */   public static AttributedString toAnsiString(@Nonnull Message message) {
/*  33 */     AttributedStyle style = AttributedStyle.DEFAULT;
/*     */     
/*  35 */     String color = message.getColor();
/*  36 */     if (color != null) style = hexToStyle(color);
/*     */     
/*  38 */     AttributedStringBuilder sb = new AttributedStringBuilder();
/*  39 */     sb.style(style).append(message.getAnsiMessage());
/*     */     
/*  41 */     List<Message> children = message.getChildren();
/*  42 */     for (Message child : children) {
/*  43 */       sb.append(toAnsiString(child));
/*     */     }
/*  45 */     return sb.toAttributedString();
/*     */   }
/*     */   
/*     */   public static AttributedStyle hexToStyle(@Nonnull String str) {
/*  49 */     Color color = ColorParseUtil.parseColor(str);
/*  50 */     if (color == null) return AttributedStyle.DEFAULT;
/*     */ 
/*     */     
/*  53 */     int colorId = Colors.roundRgbColor(color.red & 0xFF, color.green & 0xFF, color.blue & 0xFF, 256);
/*  54 */     return AttributedStyle.DEFAULT.foreground(colorId);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static void sendSuccessReply(@Nonnull PlayerRef playerRef, int token) {
/*  59 */     sendSuccessReply(playerRef, token, null);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static void sendSuccessReply(@Nonnull PlayerRef playerRef, int token, @Nullable Message message) {
/*  64 */     FormattedMessage msg = (message != null) ? message.getFormattedMessage() : null;
/*  65 */     playerRef.getPacketHandler().writeNoCache((Packet)new SuccessReply(token, msg));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static void sendFailureReply(@Nonnull PlayerRef playerRef, int token, @Nonnull Message message) {
/*  70 */     FormattedMessage msg = (message != null) ? message.getFormattedMessage() : null;
/*  71 */     playerRef.getPacketHandler().writeNoCache((Packet)new FailureReply(token, msg));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String formatText(String text, @Nullable Map<String, ParamValue> params, @Nullable Map<String, FormattedMessage> messageParams) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: ifnonnull -> 14
/*     */     //   4: new java/lang/IllegalArgumentException
/*     */     //   7: dup
/*     */     //   8: ldc 'text cannot be null'
/*     */     //   10: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   13: athrow
/*     */     //   14: aload_1
/*     */     //   15: ifnonnull -> 24
/*     */     //   18: aload_2
/*     */     //   19: ifnonnull -> 24
/*     */     //   22: aload_0
/*     */     //   23: areturn
/*     */     //   24: aload_0
/*     */     //   25: invokevirtual length : ()I
/*     */     //   28: istore_3
/*     */     //   29: new java/lang/StringBuilder
/*     */     //   32: dup
/*     */     //   33: aload_0
/*     */     //   34: invokevirtual length : ()I
/*     */     //   37: invokespecial <init> : (I)V
/*     */     //   40: astore #4
/*     */     //   42: iconst_0
/*     */     //   43: istore #5
/*     */     //   45: iconst_0
/*     */     //   46: istore #6
/*     */     //   48: iload #6
/*     */     //   50: iload_3
/*     */     //   51: if_icmpge -> 1467
/*     */     //   54: aload_0
/*     */     //   55: iload #6
/*     */     //   57: invokevirtual charAt : (I)C
/*     */     //   60: istore #7
/*     */     //   62: iload #7
/*     */     //   64: bipush #123
/*     */     //   66: if_icmpne -> 1398
/*     */     //   69: iload #6
/*     */     //   71: iconst_1
/*     */     //   72: iadd
/*     */     //   73: iload_3
/*     */     //   74: if_icmpge -> 128
/*     */     //   77: aload_0
/*     */     //   78: iload #6
/*     */     //   80: iconst_1
/*     */     //   81: iadd
/*     */     //   82: invokevirtual charAt : (I)C
/*     */     //   85: bipush #123
/*     */     //   87: if_icmpne -> 128
/*     */     //   90: iload #6
/*     */     //   92: iload #5
/*     */     //   94: if_icmple -> 108
/*     */     //   97: aload #4
/*     */     //   99: aload_0
/*     */     //   100: iload #5
/*     */     //   102: iload #6
/*     */     //   104: invokevirtual append : (Ljava/lang/CharSequence;II)Ljava/lang/StringBuilder;
/*     */     //   107: pop
/*     */     //   108: aload #4
/*     */     //   110: bipush #123
/*     */     //   112: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*     */     //   115: pop
/*     */     //   116: iinc #6, 1
/*     */     //   119: iload #6
/*     */     //   121: iconst_1
/*     */     //   122: iadd
/*     */     //   123: istore #5
/*     */     //   125: goto -> 1461
/*     */     //   128: aload_0
/*     */     //   129: iload #6
/*     */     //   131: invokestatic findMatchingBrace : (Ljava/lang/String;I)I
/*     */     //   134: istore #8
/*     */     //   136: iload #8
/*     */     //   138: ifge -> 144
/*     */     //   141: goto -> 1461
/*     */     //   144: iload #6
/*     */     //   146: iload #5
/*     */     //   148: if_icmple -> 162
/*     */     //   151: aload #4
/*     */     //   153: aload_0
/*     */     //   154: iload #5
/*     */     //   156: iload #6
/*     */     //   158: invokevirtual append : (Ljava/lang/CharSequence;II)Ljava/lang/StringBuilder;
/*     */     //   161: pop
/*     */     //   162: iload #6
/*     */     //   164: iconst_1
/*     */     //   165: iadd
/*     */     //   166: istore #9
/*     */     //   168: aload_0
/*     */     //   169: bipush #44
/*     */     //   171: iload #9
/*     */     //   173: iload #8
/*     */     //   175: invokevirtual indexOf : (III)I
/*     */     //   178: istore #10
/*     */     //   180: iload #10
/*     */     //   182: iflt -> 200
/*     */     //   185: aload_0
/*     */     //   186: bipush #44
/*     */     //   188: iload #10
/*     */     //   190: iconst_1
/*     */     //   191: iadd
/*     */     //   192: iload #8
/*     */     //   194: invokevirtual indexOf : (III)I
/*     */     //   197: goto -> 201
/*     */     //   200: iconst_m1
/*     */     //   201: istore #11
/*     */     //   203: iload #9
/*     */     //   205: istore #12
/*     */     //   207: iload #10
/*     */     //   209: iflt -> 224
/*     */     //   212: iload #10
/*     */     //   214: iload #8
/*     */     //   216: if_icmpge -> 224
/*     */     //   219: iload #10
/*     */     //   221: goto -> 226
/*     */     //   224: iload #8
/*     */     //   226: istore #13
/*     */     //   228: aload_0
/*     */     //   229: iload #12
/*     */     //   231: iload #13
/*     */     //   233: iconst_1
/*     */     //   234: isub
/*     */     //   235: invokestatic trimStart : (Ljava/lang/String;II)I
/*     */     //   238: istore #14
/*     */     //   240: aload_0
/*     */     //   241: iload #14
/*     */     //   243: iload #13
/*     */     //   245: iconst_1
/*     */     //   246: isub
/*     */     //   247: invokestatic trimEnd : (Ljava/lang/String;II)I
/*     */     //   250: istore #15
/*     */     //   252: iload #15
/*     */     //   254: ifle -> 271
/*     */     //   257: aload_0
/*     */     //   258: iload #14
/*     */     //   260: iload #14
/*     */     //   262: iload #15
/*     */     //   264: iadd
/*     */     //   265: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   268: goto -> 273
/*     */     //   271: ldc ''
/*     */     //   273: astore #16
/*     */     //   275: aconst_null
/*     */     //   276: astore #17
/*     */     //   278: iload #10
/*     */     //   280: iflt -> 352
/*     */     //   283: iload #10
/*     */     //   285: iload #8
/*     */     //   287: if_icmpge -> 352
/*     */     //   290: iload #10
/*     */     //   292: iconst_1
/*     */     //   293: iadd
/*     */     //   294: istore #18
/*     */     //   296: iload #11
/*     */     //   298: iflt -> 306
/*     */     //   301: iload #11
/*     */     //   303: goto -> 308
/*     */     //   306: iload #8
/*     */     //   308: istore #19
/*     */     //   310: aload_0
/*     */     //   311: iload #18
/*     */     //   313: iload #19
/*     */     //   315: iconst_1
/*     */     //   316: isub
/*     */     //   317: invokestatic trimStart : (Ljava/lang/String;II)I
/*     */     //   320: istore #20
/*     */     //   322: aload_0
/*     */     //   323: iload #20
/*     */     //   325: iload #19
/*     */     //   327: iconst_1
/*     */     //   328: isub
/*     */     //   329: invokestatic trimEnd : (Ljava/lang/String;II)I
/*     */     //   332: istore #21
/*     */     //   334: iload #21
/*     */     //   336: ifle -> 352
/*     */     //   339: aload_0
/*     */     //   340: iload #20
/*     */     //   342: iload #20
/*     */     //   344: iload #21
/*     */     //   346: iadd
/*     */     //   347: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   350: astore #17
/*     */     //   352: aconst_null
/*     */     //   353: astore #18
/*     */     //   355: iload #11
/*     */     //   357: iflt -> 415
/*     */     //   360: iload #11
/*     */     //   362: iload #8
/*     */     //   364: if_icmpge -> 415
/*     */     //   367: iload #11
/*     */     //   369: iconst_1
/*     */     //   370: iadd
/*     */     //   371: istore #19
/*     */     //   373: aload_0
/*     */     //   374: iload #19
/*     */     //   376: iload #8
/*     */     //   378: iconst_1
/*     */     //   379: isub
/*     */     //   380: invokestatic trimStart : (Ljava/lang/String;II)I
/*     */     //   383: istore #20
/*     */     //   385: aload_0
/*     */     //   386: iload #20
/*     */     //   388: iload #8
/*     */     //   390: iconst_1
/*     */     //   391: isub
/*     */     //   392: invokestatic trimEnd : (Ljava/lang/String;II)I
/*     */     //   395: istore #21
/*     */     //   397: iload #21
/*     */     //   399: ifle -> 415
/*     */     //   402: aload_0
/*     */     //   403: iload #20
/*     */     //   405: iload #20
/*     */     //   407: iload #21
/*     */     //   409: iadd
/*     */     //   410: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   413: astore #18
/*     */     //   415: aload_1
/*     */     //   416: ifnull -> 433
/*     */     //   419: aload_1
/*     */     //   420: aload #16
/*     */     //   422: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   427: checkcast com/hypixel/hytale/protocol/ParamValue
/*     */     //   430: goto -> 434
/*     */     //   433: aconst_null
/*     */     //   434: astore #19
/*     */     //   436: aload_2
/*     */     //   437: ifnull -> 454
/*     */     //   440: aload_2
/*     */     //   441: aload #16
/*     */     //   443: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   448: checkcast com/hypixel/hytale/protocol/FormattedMessage
/*     */     //   451: goto -> 455
/*     */     //   454: aconst_null
/*     */     //   455: astore #20
/*     */     //   457: aload #20
/*     */     //   459: ifnull -> 550
/*     */     //   462: aload #20
/*     */     //   464: getfield rawText : Ljava/lang/String;
/*     */     //   467: ifnull -> 484
/*     */     //   470: aload #4
/*     */     //   472: aload #20
/*     */     //   474: getfield rawText : Ljava/lang/String;
/*     */     //   477: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   480: pop
/*     */     //   481: goto -> 1385
/*     */     //   484: aload #20
/*     */     //   486: getfield messageId : Ljava/lang/String;
/*     */     //   489: ifnull -> 1385
/*     */     //   492: invokestatic get : ()Lcom/hypixel/hytale/server/core/modules/i18n/I18nModule;
/*     */     //   495: ldc 'en-US'
/*     */     //   497: aload #20
/*     */     //   499: getfield messageId : Ljava/lang/String;
/*     */     //   502: invokevirtual getMessage : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   505: astore #21
/*     */     //   507: aload #21
/*     */     //   509: ifnull -> 536
/*     */     //   512: aload #4
/*     */     //   514: aload #21
/*     */     //   516: aload #20
/*     */     //   518: getfield params : Ljava/util/Map;
/*     */     //   521: aload #20
/*     */     //   523: getfield messageParams : Ljava/util/Map;
/*     */     //   526: invokestatic formatText : (Ljava/lang/String;Ljava/util/Map;Ljava/util/Map;)Ljava/lang/String;
/*     */     //   529: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   532: pop
/*     */     //   533: goto -> 547
/*     */     //   536: aload #4
/*     */     //   538: aload #20
/*     */     //   540: getfield messageId : Ljava/lang/String;
/*     */     //   543: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   546: pop
/*     */     //   547: goto -> 1385
/*     */     //   550: aload #19
/*     */     //   552: ifnull -> 1374
/*     */     //   555: ldc ''
/*     */     //   557: astore #21
/*     */     //   559: aload #17
/*     */     //   561: astore #22
/*     */     //   563: iconst_0
/*     */     //   564: istore #23
/*     */     //   566: aload #22
/*     */     //   568: iload #23
/*     */     //   570: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   575: tableswitch default -> 1212, -1 -> 1212, 0 -> 608, 1 -> 636, 2 -> 664, 3 -> 1023
/*     */     //   608: aload #19
/*     */     //   610: instanceof com/hypixel/hytale/protocol/StringParamValue
/*     */     //   613: ifeq -> 633
/*     */     //   616: aload #19
/*     */     //   618: checkcast com/hypixel/hytale/protocol/StringParamValue
/*     */     //   621: astore #24
/*     */     //   623: aload #24
/*     */     //   625: getfield value : Ljava/lang/String;
/*     */     //   628: invokevirtual toUpperCase : ()Ljava/lang/String;
/*     */     //   631: astore #21
/*     */     //   633: goto -> 1212
/*     */     //   636: aload #19
/*     */     //   638: instanceof com/hypixel/hytale/protocol/StringParamValue
/*     */     //   641: ifeq -> 661
/*     */     //   644: aload #19
/*     */     //   646: checkcast com/hypixel/hytale/protocol/StringParamValue
/*     */     //   649: astore #24
/*     */     //   651: aload #24
/*     */     //   653: getfield value : Ljava/lang/String;
/*     */     //   656: invokevirtual toLowerCase : ()Ljava/lang/String;
/*     */     //   659: astore #21
/*     */     //   661: goto -> 1212
/*     */     //   664: aload #18
/*     */     //   666: astore #24
/*     */     //   668: iconst_0
/*     */     //   669: istore #25
/*     */     //   671: aload #24
/*     */     //   673: iload #25
/*     */     //   675: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   680: tableswitch default -> 866, -1 -> 866, 0 -> 708, 1 -> 866
/*     */     //   708: aload #19
/*     */     //   710: dup
/*     */     //   711: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   714: pop
/*     */     //   715: astore #26
/*     */     //   717: iconst_0
/*     */     //   718: istore #27
/*     */     //   720: aload #26
/*     */     //   722: iload #27
/*     */     //   724: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   729: tableswitch default -> 859, 0 -> 764, 1 -> 779, 2 -> 804, 3 -> 823, 4 -> 841
/*     */     //   764: aload #26
/*     */     //   766: checkcast com/hypixel/hytale/protocol/StringParamValue
/*     */     //   769: astore #28
/*     */     //   771: aload #28
/*     */     //   773: getfield value : Ljava/lang/String;
/*     */     //   776: goto -> 861
/*     */     //   779: aload #26
/*     */     //   781: checkcast com/hypixel/hytale/protocol/BoolParamValue
/*     */     //   784: astore #29
/*     */     //   786: aload #29
/*     */     //   788: getfield value : Z
/*     */     //   791: ifeq -> 799
/*     */     //   794: ldc '1'
/*     */     //   796: goto -> 861
/*     */     //   799: ldc '0'
/*     */     //   801: goto -> 861
/*     */     //   804: aload #26
/*     */     //   806: checkcast com/hypixel/hytale/protocol/DoubleParamValue
/*     */     //   809: astore #30
/*     */     //   811: aload #30
/*     */     //   813: getfield value : D
/*     */     //   816: d2i
/*     */     //   817: invokestatic toString : (I)Ljava/lang/String;
/*     */     //   820: goto -> 861
/*     */     //   823: aload #26
/*     */     //   825: checkcast com/hypixel/hytale/protocol/IntParamValue
/*     */     //   828: astore #31
/*     */     //   830: aload #31
/*     */     //   832: getfield value : I
/*     */     //   835: invokestatic toString : (I)Ljava/lang/String;
/*     */     //   838: goto -> 861
/*     */     //   841: aload #26
/*     */     //   843: checkcast com/hypixel/hytale/protocol/LongParamValue
/*     */     //   846: astore #32
/*     */     //   848: aload #32
/*     */     //   850: getfield value : J
/*     */     //   853: invokestatic toString : (J)Ljava/lang/String;
/*     */     //   856: goto -> 861
/*     */     //   859: ldc ''
/*     */     //   861: astore #21
/*     */     //   863: goto -> 1020
/*     */     //   866: aload #19
/*     */     //   868: dup
/*     */     //   869: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   872: pop
/*     */     //   873: astore #26
/*     */     //   875: iconst_0
/*     */     //   876: istore #27
/*     */     //   878: aload #26
/*     */     //   880: iload #27
/*     */     //   882: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   887: tableswitch default -> 1016, 0 -> 920, 1 -> 935, 2 -> 960, 3 -> 980, 4 -> 998
/*     */     //   920: aload #26
/*     */     //   922: checkcast com/hypixel/hytale/protocol/StringParamValue
/*     */     //   925: astore #28
/*     */     //   927: aload #28
/*     */     //   929: getfield value : Ljava/lang/String;
/*     */     //   932: goto -> 1018
/*     */     //   935: aload #26
/*     */     //   937: checkcast com/hypixel/hytale/protocol/BoolParamValue
/*     */     //   940: astore #29
/*     */     //   942: aload #29
/*     */     //   944: getfield value : Z
/*     */     //   947: ifeq -> 955
/*     */     //   950: ldc '1'
/*     */     //   952: goto -> 1018
/*     */     //   955: ldc '0'
/*     */     //   957: goto -> 1018
/*     */     //   960: aload #26
/*     */     //   962: checkcast com/hypixel/hytale/protocol/DoubleParamValue
/*     */     //   965: astore #30
/*     */     //   967: aload #30
/*     */     //   969: getfield value : D
/*     */     //   972: d2i
/*     */     //   973: i2d
/*     */     //   974: invokestatic toString : (D)Ljava/lang/String;
/*     */     //   977: goto -> 1018
/*     */     //   980: aload #26
/*     */     //   982: checkcast com/hypixel/hytale/protocol/IntParamValue
/*     */     //   985: astore #31
/*     */     //   987: aload #31
/*     */     //   989: getfield value : I
/*     */     //   992: invokestatic toString : (I)Ljava/lang/String;
/*     */     //   995: goto -> 1018
/*     */     //   998: aload #26
/*     */     //   1000: checkcast com/hypixel/hytale/protocol/LongParamValue
/*     */     //   1003: astore #32
/*     */     //   1005: aload #32
/*     */     //   1007: getfield value : J
/*     */     //   1010: invokestatic toString : (J)Ljava/lang/String;
/*     */     //   1013: goto -> 1018
/*     */     //   1016: ldc ''
/*     */     //   1018: astore #21
/*     */     //   1020: goto -> 1212
/*     */     //   1023: aload #18
/*     */     //   1025: ifnull -> 1212
/*     */     //   1028: aconst_null
/*     */     //   1029: astore #24
/*     */     //   1031: aconst_null
/*     */     //   1032: astore #25
/*     */     //   1034: aload #18
/*     */     //   1036: ldc_w 'one {'
/*     */     //   1039: invokevirtual indexOf : (Ljava/lang/String;)I
/*     */     //   1042: istore #26
/*     */     //   1044: aload #18
/*     */     //   1046: ldc_w 'other {'
/*     */     //   1049: invokevirtual indexOf : (Ljava/lang/String;)I
/*     */     //   1052: istore #27
/*     */     //   1054: iload #26
/*     */     //   1056: iflt -> 1099
/*     */     //   1059: iload #26
/*     */     //   1061: ldc_w 'one {'
/*     */     //   1064: invokevirtual length : ()I
/*     */     //   1067: iadd
/*     */     //   1068: istore #28
/*     */     //   1070: aload #18
/*     */     //   1072: iload #28
/*     */     //   1074: iconst_1
/*     */     //   1075: isub
/*     */     //   1076: invokestatic findMatchingBrace : (Ljava/lang/String;I)I
/*     */     //   1079: istore #29
/*     */     //   1081: iload #29
/*     */     //   1083: iload #28
/*     */     //   1085: if_icmple -> 1099
/*     */     //   1088: aload #18
/*     */     //   1090: iload #28
/*     */     //   1092: iload #29
/*     */     //   1094: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   1097: astore #24
/*     */     //   1099: iload #27
/*     */     //   1101: iflt -> 1144
/*     */     //   1104: iload #27
/*     */     //   1106: ldc_w 'other {'
/*     */     //   1109: invokevirtual length : ()I
/*     */     //   1112: iadd
/*     */     //   1113: istore #28
/*     */     //   1115: aload #18
/*     */     //   1117: iload #28
/*     */     //   1119: iconst_1
/*     */     //   1120: isub
/*     */     //   1121: invokestatic findMatchingBrace : (Ljava/lang/String;I)I
/*     */     //   1124: istore #29
/*     */     //   1126: iload #29
/*     */     //   1128: iload #28
/*     */     //   1130: if_icmple -> 1144
/*     */     //   1133: aload #18
/*     */     //   1135: iload #28
/*     */     //   1137: iload #29
/*     */     //   1139: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   1142: astore #25
/*     */     //   1144: aload #19
/*     */     //   1146: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   1149: invokestatic parseInt : (Ljava/lang/String;)I
/*     */     //   1152: istore #28
/*     */     //   1154: iload #28
/*     */     //   1156: iconst_1
/*     */     //   1157: if_icmpne -> 1172
/*     */     //   1160: aload #24
/*     */     //   1162: ifnull -> 1172
/*     */     //   1165: aload #24
/*     */     //   1167: astore #29
/*     */     //   1169: goto -> 1200
/*     */     //   1172: aload #25
/*     */     //   1174: ifnull -> 1184
/*     */     //   1177: aload #25
/*     */     //   1179: astore #29
/*     */     //   1181: goto -> 1200
/*     */     //   1184: aload #24
/*     */     //   1186: ifnull -> 1196
/*     */     //   1189: aload #24
/*     */     //   1191: astore #29
/*     */     //   1193: goto -> 1200
/*     */     //   1196: ldc ''
/*     */     //   1198: astore #29
/*     */     //   1200: aload #29
/*     */     //   1202: aload_1
/*     */     //   1203: aload_2
/*     */     //   1204: invokestatic formatText : (Ljava/lang/String;Ljava/util/Map;Ljava/util/Map;)Ljava/lang/String;
/*     */     //   1207: astore #21
/*     */     //   1209: goto -> 1212
/*     */     //   1212: aload #17
/*     */     //   1214: ifnonnull -> 1363
/*     */     //   1217: aload #19
/*     */     //   1219: dup
/*     */     //   1220: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   1223: pop
/*     */     //   1224: astore #22
/*     */     //   1226: iconst_0
/*     */     //   1227: istore #23
/*     */     //   1229: aload #22
/*     */     //   1231: iload #23
/*     */     //   1233: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   1238: tableswitch default -> 1359, 0 -> 1272, 1 -> 1287, 2 -> 1305, 3 -> 1323, 4 -> 1341
/*     */     //   1272: aload #22
/*     */     //   1274: checkcast com/hypixel/hytale/protocol/StringParamValue
/*     */     //   1277: astore #24
/*     */     //   1279: aload #24
/*     */     //   1281: getfield value : Ljava/lang/String;
/*     */     //   1284: goto -> 1361
/*     */     //   1287: aload #22
/*     */     //   1289: checkcast com/hypixel/hytale/protocol/BoolParamValue
/*     */     //   1292: astore #25
/*     */     //   1294: aload #25
/*     */     //   1296: getfield value : Z
/*     */     //   1299: invokestatic toString : (Z)Ljava/lang/String;
/*     */     //   1302: goto -> 1361
/*     */     //   1305: aload #22
/*     */     //   1307: checkcast com/hypixel/hytale/protocol/DoubleParamValue
/*     */     //   1310: astore #26
/*     */     //   1312: aload #26
/*     */     //   1314: getfield value : D
/*     */     //   1317: invokestatic toString : (D)Ljava/lang/String;
/*     */     //   1320: goto -> 1361
/*     */     //   1323: aload #22
/*     */     //   1325: checkcast com/hypixel/hytale/protocol/IntParamValue
/*     */     //   1328: astore #27
/*     */     //   1330: aload #27
/*     */     //   1332: getfield value : I
/*     */     //   1335: invokestatic toString : (I)Ljava/lang/String;
/*     */     //   1338: goto -> 1361
/*     */     //   1341: aload #22
/*     */     //   1343: checkcast com/hypixel/hytale/protocol/LongParamValue
/*     */     //   1346: astore #28
/*     */     //   1348: aload #28
/*     */     //   1350: getfield value : J
/*     */     //   1353: invokestatic toString : (J)Ljava/lang/String;
/*     */     //   1356: goto -> 1361
/*     */     //   1359: ldc ''
/*     */     //   1361: astore #21
/*     */     //   1363: aload #4
/*     */     //   1365: aload #21
/*     */     //   1367: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   1370: pop
/*     */     //   1371: goto -> 1385
/*     */     //   1374: aload #4
/*     */     //   1376: aload_0
/*     */     //   1377: iload #6
/*     */     //   1379: iload #8
/*     */     //   1381: invokevirtual append : (Ljava/lang/CharSequence;II)Ljava/lang/StringBuilder;
/*     */     //   1384: pop
/*     */     //   1385: iload #8
/*     */     //   1387: istore #6
/*     */     //   1389: iload #8
/*     */     //   1391: iconst_1
/*     */     //   1392: iadd
/*     */     //   1393: istore #5
/*     */     //   1395: goto -> 1461
/*     */     //   1398: iload #7
/*     */     //   1400: bipush #125
/*     */     //   1402: if_icmpne -> 1461
/*     */     //   1405: iload #6
/*     */     //   1407: iconst_1
/*     */     //   1408: iadd
/*     */     //   1409: iload_3
/*     */     //   1410: if_icmpge -> 1461
/*     */     //   1413: aload_0
/*     */     //   1414: iload #6
/*     */     //   1416: iconst_1
/*     */     //   1417: iadd
/*     */     //   1418: invokevirtual charAt : (I)C
/*     */     //   1421: bipush #125
/*     */     //   1423: if_icmpne -> 1461
/*     */     //   1426: iload #6
/*     */     //   1428: iload #5
/*     */     //   1430: if_icmple -> 1444
/*     */     //   1433: aload #4
/*     */     //   1435: aload_0
/*     */     //   1436: iload #5
/*     */     //   1438: iload #6
/*     */     //   1440: invokevirtual append : (Ljava/lang/CharSequence;II)Ljava/lang/StringBuilder;
/*     */     //   1443: pop
/*     */     //   1444: aload #4
/*     */     //   1446: bipush #125
/*     */     //   1448: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*     */     //   1451: pop
/*     */     //   1452: iinc #6, 1
/*     */     //   1455: iload #6
/*     */     //   1457: iconst_1
/*     */     //   1458: iadd
/*     */     //   1459: istore #5
/*     */     //   1461: iinc #6, 1
/*     */     //   1464: goto -> 48
/*     */     //   1467: iload #5
/*     */     //   1469: iload_3
/*     */     //   1470: if_icmpge -> 1483
/*     */     //   1473: aload #4
/*     */     //   1475: aload_0
/*     */     //   1476: iload #5
/*     */     //   1478: iload_3
/*     */     //   1479: invokevirtual append : (Ljava/lang/CharSequence;II)Ljava/lang/StringBuilder;
/*     */     //   1482: pop
/*     */     //   1483: aload #4
/*     */     //   1485: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   1488: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #76	-> 0
/*     */     //   #77	-> 14
/*     */     //   #79	-> 24
/*     */     //   #80	-> 29
/*     */     //   #81	-> 42
/*     */     //   #83	-> 45
/*     */     //   #84	-> 54
/*     */     //   #87	-> 62
/*     */     //   #88	-> 69
/*     */     //   #90	-> 90
/*     */     //   #91	-> 108
/*     */     //   #92	-> 116
/*     */     //   #93	-> 119
/*     */     //   #94	-> 125
/*     */     //   #98	-> 128
/*     */     //   #99	-> 136
/*     */     //   #102	-> 144
/*     */     //   #104	-> 162
/*     */     //   #106	-> 168
/*     */     //   #107	-> 180
/*     */     //   #110	-> 203
/*     */     //   #111	-> 207
/*     */     //   #112	-> 228
/*     */     //   #113	-> 240
/*     */     //   #114	-> 252
/*     */     //   #117	-> 275
/*     */     //   #118	-> 278
/*     */     //   #119	-> 290
/*     */     //   #120	-> 296
/*     */     //   #121	-> 310
/*     */     //   #122	-> 322
/*     */     //   #123	-> 334
/*     */     //   #127	-> 352
/*     */     //   #128	-> 355
/*     */     //   #129	-> 367
/*     */     //   #130	-> 373
/*     */     //   #131	-> 385
/*     */     //   #132	-> 397
/*     */     //   #135	-> 415
/*     */     //   #136	-> 436
/*     */     //   #137	-> 457
/*     */     //   #138	-> 462
/*     */     //   #139	-> 470
/*     */     //   #140	-> 484
/*     */     //   #141	-> 492
/*     */     //   #142	-> 507
/*     */     //   #143	-> 512
/*     */     //   #145	-> 536
/*     */     //   #147	-> 547
/*     */     //   #148	-> 550
/*     */     //   #149	-> 555
/*     */     //   #151	-> 559
/*     */     //   #153	-> 608
/*     */     //   #154	-> 623
/*     */     //   #156	-> 633
/*     */     //   #159	-> 636
/*     */     //   #160	-> 651
/*     */     //   #162	-> 661
/*     */     //   #166	-> 664
/*     */     //   #168	-> 708
/*     */     //   #169	-> 764
/*     */     //   #170	-> 779
/*     */     //   #171	-> 804
/*     */     //   #172	-> 823
/*     */     //   #173	-> 841
/*     */     //   #174	-> 859
/*     */     //   #175	-> 861
/*     */     //   #176	-> 863
/*     */     //   #181	-> 866
/*     */     //   #182	-> 920
/*     */     //   #183	-> 935
/*     */     //   #184	-> 960
/*     */     //   #185	-> 980
/*     */     //   #186	-> 998
/*     */     //   #187	-> 1016
/*     */     //   #188	-> 1018
/*     */     //   #192	-> 1020
/*     */     //   #197	-> 1023
/*     */     //   #198	-> 1028
/*     */     //   #199	-> 1034
/*     */     //   #200	-> 1044
/*     */     //   #201	-> 1054
/*     */     //   #202	-> 1059
/*     */     //   #203	-> 1070
/*     */     //   #204	-> 1081
/*     */     //   #205	-> 1088
/*     */     //   #209	-> 1099
/*     */     //   #210	-> 1104
/*     */     //   #211	-> 1115
/*     */     //   #212	-> 1126
/*     */     //   #213	-> 1133
/*     */     //   #218	-> 1144
/*     */     //   #220	-> 1154
/*     */     //   #221	-> 1165
/*     */     //   #223	-> 1172
/*     */     //   #224	-> 1177
/*     */     //   #225	-> 1184
/*     */     //   #226	-> 1189
/*     */     //   #228	-> 1196
/*     */     //   #231	-> 1200
/*     */     //   #232	-> 1209
/*     */     //   #241	-> 1212
/*     */     //   #242	-> 1217
/*     */     //   #243	-> 1272
/*     */     //   #244	-> 1287
/*     */     //   #245	-> 1305
/*     */     //   #246	-> 1323
/*     */     //   #247	-> 1341
/*     */     //   #248	-> 1359
/*     */     //   #249	-> 1361
/*     */     //   #252	-> 1363
/*     */     //   #253	-> 1371
/*     */     //   #255	-> 1374
/*     */     //   #258	-> 1385
/*     */     //   #259	-> 1389
/*     */     //   #260	-> 1395
/*     */     //   #264	-> 1398
/*     */     //   #265	-> 1426
/*     */     //   #266	-> 1444
/*     */     //   #267	-> 1452
/*     */     //   #268	-> 1455
/*     */     //   #83	-> 1461
/*     */     //   #273	-> 1467
/*     */     //   #275	-> 1483
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   296	56	18	formatStart	I
/*     */     //   310	42	19	formatEndExclusive	I
/*     */     //   322	30	20	fs	I
/*     */     //   334	18	21	fl	I
/*     */     //   373	42	19	optionsStart	I
/*     */     //   385	30	20	os	I
/*     */     //   397	18	21	ol	I
/*     */     //   507	40	21	message	Ljava/lang/String;
/*     */     //   623	10	24	s	Lcom/hypixel/hytale/protocol/StringParamValue;
/*     */     //   651	10	24	s	Lcom/hypixel/hytale/protocol/StringParamValue;
/*     */     //   771	8	28	s	Lcom/hypixel/hytale/protocol/StringParamValue;
/*     */     //   786	18	29	b	Lcom/hypixel/hytale/protocol/BoolParamValue;
/*     */     //   811	12	30	d	Lcom/hypixel/hytale/protocol/DoubleParamValue;
/*     */     //   830	11	31	iv	Lcom/hypixel/hytale/protocol/IntParamValue;
/*     */     //   848	11	32	l	Lcom/hypixel/hytale/protocol/LongParamValue;
/*     */     //   927	8	28	s	Lcom/hypixel/hytale/protocol/StringParamValue;
/*     */     //   942	18	29	b	Lcom/hypixel/hytale/protocol/BoolParamValue;
/*     */     //   967	13	30	d	Lcom/hypixel/hytale/protocol/DoubleParamValue;
/*     */     //   987	11	31	iv	Lcom/hypixel/hytale/protocol/IntParamValue;
/*     */     //   1005	11	32	l	Lcom/hypixel/hytale/protocol/LongParamValue;
/*     */     //   1070	29	28	oneStart	I
/*     */     //   1081	18	29	oneEnd	I
/*     */     //   1115	29	28	otherStart	I
/*     */     //   1126	18	29	otherEnd	I
/*     */     //   1169	3	29	selected	Ljava/lang/String;
/*     */     //   1181	3	29	selected	Ljava/lang/String;
/*     */     //   1193	3	29	selected	Ljava/lang/String;
/*     */     //   1031	178	24	oneText	Ljava/lang/String;
/*     */     //   1034	175	25	otherText	Ljava/lang/String;
/*     */     //   1044	165	26	oneIdx	I
/*     */     //   1054	155	27	otherIdx	I
/*     */     //   1154	55	28	value	I
/*     */     //   1200	9	29	selected	Ljava/lang/String;
/*     */     //   1279	8	24	s	Lcom/hypixel/hytale/protocol/StringParamValue;
/*     */     //   1294	11	25	b	Lcom/hypixel/hytale/protocol/BoolParamValue;
/*     */     //   1312	11	26	d	Lcom/hypixel/hytale/protocol/DoubleParamValue;
/*     */     //   1330	11	27	iv	Lcom/hypixel/hytale/protocol/IntParamValue;
/*     */     //   1348	11	28	l	Lcom/hypixel/hytale/protocol/LongParamValue;
/*     */     //   559	812	21	formattedReplacement	Ljava/lang/String;
/*     */     //   136	1262	8	end	I
/*     */     //   168	1230	9	contentStart	I
/*     */     //   180	1218	10	c1	I
/*     */     //   203	1195	11	c2	I
/*     */     //   207	1191	12	nameStart	I
/*     */     //   228	1170	13	nameEndExclusive	I
/*     */     //   240	1158	14	ns	I
/*     */     //   252	1146	15	nl	I
/*     */     //   275	1123	16	key	Ljava/lang/String;
/*     */     //   278	1120	17	format	Ljava/lang/String;
/*     */     //   355	1043	18	options	Ljava/lang/String;
/*     */     //   436	962	19	replacement	Lcom/hypixel/hytale/protocol/ParamValue;
/*     */     //   457	941	20	replacementMessage	Lcom/hypixel/hytale/protocol/FormattedMessage;
/*     */     //   62	1399	7	ch	C
/*     */     //   48	1419	6	i	I
/*     */     //   0	1489	0	text	Ljava/lang/String;
/*     */     //   0	1489	1	params	Ljava/util/Map;
/*     */     //   0	1489	2	messageParams	Ljava/util/Map;
/*     */     //   29	1460	3	len	I
/*     */     //   42	1447	4	sb	Ljava/lang/StringBuilder;
/*     */     //   45	1444	5	lastWritePos	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	1489	1	params	Ljava/util/Map<Ljava/lang/String;Lcom/hypixel/hytale/protocol/ParamValue;>;
/*     */     //   0	1489	2	messageParams	Ljava/util/Map<Ljava/lang/String;Lcom/hypixel/hytale/protocol/FormattedMessage;>;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int findMatchingBrace(@Nonnull String text, int start) {
/* 280 */     int depth = 0;
/* 281 */     int len = text.length();
/* 282 */     for (int i = start; i < len; i++) {
/* 283 */       if (text.charAt(i) == '{') {
/* 284 */         depth++;
/*     */       } else {
/* 286 */         depth--;
/* 287 */         if (text.charAt(i) == '}' && depth == 0) return i;
/*     */       
/*     */       } 
/*     */     } 
/* 291 */     return -1;
/*     */   }
/*     */   
/*     */   private static int trimStart(@Nonnull String text, int start, int end) {
/* 295 */     int i = start;
/* 296 */     for (; i <= end && Character.isWhitespace(text.charAt(i)); i++);
/* 297 */     return i;
/*     */   }
/*     */   
/*     */   private static int trimEnd(@Nonnull String text, int start, int end) {
/* 301 */     int i = start;
/* 302 */     for (; end >= i && Character.isWhitespace(text.charAt(i)); end--);
/* 303 */     return (end >= i) ? (end - i + 1) : 0;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\MessageUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */