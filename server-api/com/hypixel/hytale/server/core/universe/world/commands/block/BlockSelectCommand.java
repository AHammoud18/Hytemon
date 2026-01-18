/*     */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFlipType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.VariantRotation;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.EnumArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.SingleArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockSelectCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  51 */   private static final SingleArgumentType<BlockFlipType> BLOCK_FLIP_TYPE = (SingleArgumentType<BlockFlipType>)new EnumArgumentType("server.commands.parsing.argtype.blockfliptype.name", BlockFlipType.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  60 */   private static final SingleArgumentType<VariantRotation> VARIANT_ROTATION = (SingleArgumentType<VariantRotation>)new EnumArgumentType("server.commands.parsing.argtype.variantrotation.name", VariantRotation.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  66 */   private static final Message MESSAGE_COMMANDS_BLOCK_SELECT_DONE = Message.translation("server.commands.block.select.done");
/*     */   @Nonnull
/*  68 */   private static final Message MESSAGE_COMMANDS_BLOCK_SELECT_NO_SELECTION_PROVIDER = Message.translation("server.commands.block.select.noSelectionProvider");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  74 */   private final OptionalArg<String> regexArg = withOptionalArg("regex", "server.commands.block.select.regex.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  80 */   private final FlagArg allFlag = withFlagArg("all", "server.commands.block.select.all.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  86 */   private final OptionalArg<String> sortArg = withOptionalArg("sort", "server.commands.block.select.sort.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  92 */   private final OptionalArg<BlockFlipType> flipTypeArg = withOptionalArg("fliptype", "server.commands.block.select.fliptype.desc", (ArgumentType)BLOCK_FLIP_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  98 */   private final OptionalArg<VariantRotation> variantRotationArg = withOptionalArg("variantrotation", "server.commands.block.select.variantrotation.desc", (ArgumentType)VARIANT_ROTATION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 104 */   private final DefaultArg<Integer> paddingArg = withDefaultArg("padding", "server.commands.block.select.padding.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(1), "1");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 110 */   private final OptionalArg<String> groundArg = withOptionalArg("ground", "server.commands.block.select.ground.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockSelectCommand() {
/* 116 */     super("blockselect", "server.commands.block.select.desc");
/*     */   }
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: aload_3
/*     */     //   2: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   5: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   8: checkcast com/hypixel/hytale/server/core/entity/entities/Player
/*     */     //   11: astore #6
/*     */     //   13: getstatic com/hypixel/hytale/server/core/universe/world/commands/block/BlockSelectCommand.$assertionsDisabled : Z
/*     */     //   16: ifne -> 32
/*     */     //   19: aload #6
/*     */     //   21: ifnonnull -> 32
/*     */     //   24: new java/lang/AssertionError
/*     */     //   27: dup
/*     */     //   28: invokespecial <init> : ()V
/*     */     //   31: athrow
/*     */     //   32: invokestatic getSelectionProvider : ()Lcom/hypixel/hytale/server/core/prefab/selection/SelectionProvider;
/*     */     //   35: astore #7
/*     */     //   37: aload #7
/*     */     //   39: ifnonnull -> 50
/*     */     //   42: aload_1
/*     */     //   43: getstatic com/hypixel/hytale/server/core/universe/world/commands/block/BlockSelectCommand.MESSAGE_COMMANDS_BLOCK_SELECT_NO_SELECTION_PROVIDER : Lcom/hypixel/hytale/server/core/Message;
/*     */     //   46: invokevirtual sendMessage : (Lcom/hypixel/hytale/server/core/Message;)V
/*     */     //   49: return
/*     */     //   50: aload_0
/*     */     //   51: getfield regexArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   54: aload_1
/*     */     //   55: invokevirtual provided : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Z
/*     */     //   58: ifeq -> 78
/*     */     //   61: aload_0
/*     */     //   62: getfield regexArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   65: aload_1
/*     */     //   66: invokevirtual get : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Ljava/lang/Object;
/*     */     //   69: checkcast java/lang/String
/*     */     //   72: invokestatic compile : (Ljava/lang/String;)Ljava/util/regex/Pattern;
/*     */     //   75: goto -> 79
/*     */     //   78: aconst_null
/*     */     //   79: astore #8
/*     */     //   81: invokestatic getAssetMap : ()Lcom/hypixel/hytale/assetstore/map/BlockTypeAssetMap;
/*     */     //   84: invokevirtual getAssetMap : ()Ljava/util/Map;
/*     */     //   87: invokeinterface entrySet : ()Ljava/util/Set;
/*     */     //   92: invokeinterface parallelStream : ()Ljava/util/stream/Stream;
/*     */     //   97: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   102: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   107: aload #8
/*     */     //   109: <illegal opcode> test : (Ljava/util/regex/Pattern;)Ljava/util/function/Predicate;
/*     */     //   114: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   119: astore #9
/*     */     //   121: aload_0
/*     */     //   122: getfield allFlag : Lcom/hypixel/hytale/server/core/command/system/arguments/system/FlagArg;
/*     */     //   125: aload_1
/*     */     //   126: invokevirtual get : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Ljava/lang/Object;
/*     */     //   129: checkcast java/lang/Boolean
/*     */     //   132: invokevirtual booleanValue : ()Z
/*     */     //   135: ifne -> 152
/*     */     //   138: aload #9
/*     */     //   140: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   145: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   150: astore #9
/*     */     //   152: aload_0
/*     */     //   153: getfield flipTypeArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   156: aload_1
/*     */     //   157: invokevirtual provided : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Z
/*     */     //   160: ifeq -> 192
/*     */     //   163: aload_0
/*     */     //   164: getfield flipTypeArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   167: aload_1
/*     */     //   168: invokevirtual get : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Ljava/lang/Object;
/*     */     //   171: checkcast com/hypixel/hytale/server/core/asset/type/blocktype/config/BlockFlipType
/*     */     //   174: astore #10
/*     */     //   176: aload #9
/*     */     //   178: aload #10
/*     */     //   180: <illegal opcode> test : (Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockFlipType;)Ljava/util/function/Predicate;
/*     */     //   185: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   190: astore #9
/*     */     //   192: aload_0
/*     */     //   193: getfield variantRotationArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   196: aload_1
/*     */     //   197: invokevirtual provided : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Z
/*     */     //   200: ifeq -> 232
/*     */     //   203: aload_0
/*     */     //   204: getfield variantRotationArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   207: aload_1
/*     */     //   208: invokevirtual get : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Ljava/lang/Object;
/*     */     //   211: checkcast com/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation
/*     */     //   214: astore #10
/*     */     //   216: aload #9
/*     */     //   218: aload #10
/*     */     //   220: <illegal opcode> test : (Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;)Ljava/util/function/Predicate;
/*     */     //   225: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   230: astore #9
/*     */     //   232: aload_0
/*     */     //   233: getfield sortArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   236: aload_1
/*     */     //   237: invokevirtual provided : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Z
/*     */     //   240: ifeq -> 496
/*     */     //   243: aload_0
/*     */     //   244: getfield sortArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   247: aload_1
/*     */     //   248: invokevirtual get : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Ljava/lang/Object;
/*     */     //   251: checkcast java/lang/String
/*     */     //   254: astore #10
/*     */     //   256: aload #10
/*     */     //   258: invokevirtual isEmpty : ()Z
/*     */     //   261: ifeq -> 279
/*     */     //   264: aload #9
/*     */     //   266: invokestatic comparingByKey : ()Ljava/util/Comparator;
/*     */     //   269: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
/*     */     //   274: astore #9
/*     */     //   276: goto -> 496
/*     */     //   279: aload #10
/*     */     //   281: ldc ','
/*     */     //   283: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
/*     */     //   286: astore #11
/*     */     //   288: aload #11
/*     */     //   290: arraylength
/*     */     //   291: istore #12
/*     */     //   293: iconst_0
/*     */     //   294: istore #13
/*     */     //   296: iload #13
/*     */     //   298: iload #12
/*     */     //   300: if_icmpge -> 496
/*     */     //   303: aload #11
/*     */     //   305: iload #13
/*     */     //   307: aaload
/*     */     //   308: astore #14
/*     */     //   310: aload #14
/*     */     //   312: astore #15
/*     */     //   314: iconst_m1
/*     */     //   315: istore #16
/*     */     //   317: aload #15
/*     */     //   319: invokevirtual hashCode : ()I
/*     */     //   322: lookupswitch default -> 401, 106079 -> 356, 3373707 -> 372, 1099846370 -> 388
/*     */     //   356: aload #15
/*     */     //   358: ldc 'key'
/*     */     //   360: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   363: ifeq -> 401
/*     */     //   366: iconst_0
/*     */     //   367: istore #16
/*     */     //   369: goto -> 401
/*     */     //   372: aload #15
/*     */     //   374: ldc 'name'
/*     */     //   376: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   379: ifeq -> 401
/*     */     //   382: iconst_1
/*     */     //   383: istore #16
/*     */     //   385: goto -> 401
/*     */     //   388: aload #15
/*     */     //   390: ldc 'reverse'
/*     */     //   392: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   395: ifeq -> 401
/*     */     //   398: iconst_2
/*     */     //   399: istore #16
/*     */     //   401: iload #16
/*     */     //   403: tableswitch default -> 467, 0 -> 428, 1 -> 441, 2 -> 454
/*     */     //   428: aload #9
/*     */     //   430: invokestatic comparingByKey : ()Ljava/util/Comparator;
/*     */     //   433: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
/*     */     //   438: goto -> 488
/*     */     //   441: aload #9
/*     */     //   443: invokestatic comparingByKey : ()Ljava/util/Comparator;
/*     */     //   446: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
/*     */     //   451: goto -> 488
/*     */     //   454: aload #9
/*     */     //   456: invokestatic reverseOrder : ()Ljava/util/Comparator;
/*     */     //   459: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
/*     */     //   464: goto -> 488
/*     */     //   467: new com/hypixel/hytale/server/core/command/system/exceptions/GeneralCommandException
/*     */     //   470: dup
/*     */     //   471: ldc 'server.commands.block.select.invalidSortType'
/*     */     //   473: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*     */     //   476: ldc_w 'sortType'
/*     */     //   479: aload #14
/*     */     //   481: invokevirtual param : (Ljava/lang/String;Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*     */     //   484: invokespecial <init> : (Lcom/hypixel/hytale/server/core/Message;)V
/*     */     //   487: athrow
/*     */     //   488: astore #9
/*     */     //   490: iinc #13, 1
/*     */     //   493: goto -> 296
/*     */     //   496: aload #9
/*     */     //   498: <illegal opcode> apply : ()Ljava/util/function/Function;
/*     */     //   503: invokeinterface map : (Ljava/util/function/Function;)Ljava/util/stream/Stream;
/*     */     //   508: invokeinterface toList : ()Ljava/util/List;
/*     */     //   513: astore #10
/*     */     //   515: aload_1
/*     */     //   516: ldc_w 'server.commands.block.select.select'
/*     */     //   519: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*     */     //   522: ldc_w 'count'
/*     */     //   525: aload #10
/*     */     //   527: invokeinterface size : ()I
/*     */     //   532: invokevirtual param : (Ljava/lang/String;I)Lcom/hypixel/hytale/server/core/Message;
/*     */     //   535: invokevirtual sendMessage : (Lcom/hypixel/hytale/server/core/Message;)V
/*     */     //   538: new com/hypixel/hytale/math/shape/Box
/*     */     //   541: dup
/*     */     //   542: invokespecial <init> : ()V
/*     */     //   545: astore #11
/*     */     //   547: aload #10
/*     */     //   549: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   554: astore #12
/*     */     //   556: aload #12
/*     */     //   558: invokeinterface hasNext : ()Z
/*     */     //   563: ifeq -> 604
/*     */     //   566: aload #12
/*     */     //   568: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   573: checkcast java/util/Map$Entry
/*     */     //   576: astore #13
/*     */     //   578: aload #11
/*     */     //   580: aload #13
/*     */     //   582: invokeinterface getValue : ()Ljava/lang/Object;
/*     */     //   587: checkcast com/hypixel/hytale/server/core/asset/type/blockhitbox/BlockBoundingBoxes
/*     */     //   590: iconst_0
/*     */     //   591: invokevirtual get : (I)Lcom/hypixel/hytale/server/core/asset/type/blockhitbox/BlockBoundingBoxes$RotatedVariantBoxes;
/*     */     //   594: invokevirtual getBoundingBox : ()Lcom/hypixel/hytale/math/shape/Box;
/*     */     //   597: invokevirtual union : (Lcom/hypixel/hytale/math/shape/Box;)Lcom/hypixel/hytale/math/shape/Box;
/*     */     //   600: pop
/*     */     //   601: goto -> 556
/*     */     //   604: aload_0
/*     */     //   605: getfield paddingArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/DefaultArg;
/*     */     //   608: aload_1
/*     */     //   609: invokevirtual get : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Ljava/lang/Object;
/*     */     //   612: checkcast java/lang/Integer
/*     */     //   615: invokevirtual intValue : ()I
/*     */     //   618: istore #12
/*     */     //   620: aload #10
/*     */     //   622: invokeinterface size : ()I
/*     */     //   627: i2d
/*     */     //   628: invokestatic sqrt : (D)D
/*     */     //   631: invokestatic ceil : (D)I
/*     */     //   634: iconst_1
/*     */     //   635: iadd
/*     */     //   636: istore #13
/*     */     //   638: aload #11
/*     */     //   640: invokevirtual width : ()D
/*     */     //   643: invokestatic ceil : (D)I
/*     */     //   646: iload #12
/*     */     //   648: iadd
/*     */     //   649: istore #14
/*     */     //   651: aload #11
/*     */     //   653: invokevirtual depth : ()D
/*     */     //   656: invokestatic ceil : (D)I
/*     */     //   659: iload #12
/*     */     //   661: iadd
/*     */     //   662: istore #15
/*     */     //   664: iload #14
/*     */     //   666: iconst_2
/*     */     //   667: idiv
/*     */     //   668: istore #16
/*     */     //   670: iload #15
/*     */     //   672: iconst_2
/*     */     //   673: idiv
/*     */     //   674: istore #17
/*     */     //   676: aload #11
/*     */     //   678: invokevirtual height : ()D
/*     */     //   681: dstore #18
/*     */     //   683: aload_0
/*     */     //   684: getfield groundArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   687: aload_1
/*     */     //   688: invokevirtual provided : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Z
/*     */     //   691: ifeq -> 710
/*     */     //   694: aload_0
/*     */     //   695: getfield groundArg : Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*     */     //   698: aload_1
/*     */     //   699: invokevirtual get : (Lcom/hypixel/hytale/server/core/command/system/CommandContext;)Ljava/lang/Object;
/*     */     //   702: checkcast java/lang/String
/*     */     //   705: astore #20
/*     */     //   707: goto -> 740
/*     */     //   710: ldc_w 'Rock_Stone'
/*     */     //   713: astore #21
/*     */     //   715: invokestatic getAssetMap : ()Lcom/hypixel/hytale/assetstore/map/BlockTypeAssetMap;
/*     */     //   718: ldc_w 'Rock_Stone'
/*     */     //   721: invokevirtual getAsset : (Ljava/lang/Object;)Lcom/hypixel/hytale/assetstore/JsonAsset;
/*     */     //   724: ifnull -> 735
/*     */     //   727: ldc_w 'Rock_Stone'
/*     */     //   730: astore #20
/*     */     //   732: goto -> 740
/*     */     //   735: ldc_w 'Unknown'
/*     */     //   738: astore #20
/*     */     //   740: aload #7
/*     */     //   742: aload_3
/*     */     //   743: aload #6
/*     */     //   745: aload #20
/*     */     //   747: iload #12
/*     */     //   749: iload #13
/*     */     //   751: iload #14
/*     */     //   753: iload #15
/*     */     //   755: dload #18
/*     */     //   757: aload #10
/*     */     //   759: iload #16
/*     */     //   761: iload #17
/*     */     //   763: aload_1
/*     */     //   764: <illegal opcode> acceptNow : (Ljava/lang/String;IIIIDLjava/util/List;IILcom/hypixel/hytale/server/core/command/system/CommandContext;)Lcom/hypixel/hytale/sneakythrow/consumer/ThrowableConsumer;
/*     */     //   769: aload_2
/*     */     //   770: invokeinterface computeSelectionCopy : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/server/core/entity/entities/Player;Lcom/hypixel/hytale/sneakythrow/consumer/ThrowableConsumer;Lcom/hypixel/hytale/component/ComponentAccessor;)V
/*     */     //   775: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #125	-> 0
/*     */     //   #126	-> 13
/*     */     //   #128	-> 32
/*     */     //   #130	-> 37
/*     */     //   #131	-> 42
/*     */     //   #132	-> 49
/*     */     //   #135	-> 50
/*     */     //   #137	-> 81
/*     */     //   #138	-> 102
/*     */     //   #139	-> 114
/*     */     //   #142	-> 121
/*     */     //   #143	-> 138
/*     */     //   #146	-> 152
/*     */     //   #147	-> 163
/*     */     //   #148	-> 176
/*     */     //   #151	-> 192
/*     */     //   #152	-> 203
/*     */     //   #153	-> 216
/*     */     //   #156	-> 232
/*     */     //   #157	-> 243
/*     */     //   #158	-> 256
/*     */     //   #159	-> 264
/*     */     //   #161	-> 279
/*     */     //   #162	-> 310
/*     */     //   #163	-> 428
/*     */     //   #164	-> 441
/*     */     //   #165	-> 454
/*     */     //   #166	-> 467
/*     */     //   #167	-> 473
/*     */     //   #168	-> 481
/*     */     //   #162	-> 488
/*     */     //   #161	-> 490
/*     */     //   #174	-> 496
/*     */     //   #175	-> 503
/*     */     //   #176	-> 508
/*     */     //   #178	-> 515
/*     */     //   #179	-> 527
/*     */     //   #178	-> 535
/*     */     //   #181	-> 538
/*     */     //   #182	-> 547
/*     */     //   #183	-> 578
/*     */     //   #184	-> 601
/*     */     //   #186	-> 604
/*     */     //   #188	-> 620
/*     */     //   #189	-> 638
/*     */     //   #190	-> 651
/*     */     //   #191	-> 664
/*     */     //   #192	-> 670
/*     */     //   #193	-> 676
/*     */     //   #196	-> 683
/*     */     //   #197	-> 694
/*     */     //   #201	-> 710
/*     */     //   #202	-> 715
/*     */     //   #203	-> 727
/*     */     //   #205	-> 735
/*     */     //   #209	-> 740
/*     */     //   #240	-> 775
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   176	16	10	flipType	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockFlipType;
/*     */     //   216	16	10	variantRotation	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;
/*     */     //   310	180	14	sortType	Ljava/lang/String;
/*     */     //   256	240	10	sort	Ljava/lang/String;
/*     */     //   578	23	13	block	Ljava/util/Map$Entry;
/*     */     //   707	3	20	groundBlock	Ljava/lang/String;
/*     */     //   732	3	20	groundBlock	Ljava/lang/String;
/*     */     //   715	25	21	rockStone	Ljava/lang/String;
/*     */     //   0	776	0	this	Lcom/hypixel/hytale/server/core/universe/world/commands/block/BlockSelectCommand;
/*     */     //   0	776	1	context	Lcom/hypixel/hytale/server/core/command/system/CommandContext;
/*     */     //   0	776	2	store	Lcom/hypixel/hytale/component/Store;
/*     */     //   0	776	3	ref	Lcom/hypixel/hytale/component/Ref;
/*     */     //   0	776	4	playerRef	Lcom/hypixel/hytale/server/core/universe/PlayerRef;
/*     */     //   0	776	5	world	Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   13	763	6	playerComponent	Lcom/hypixel/hytale/server/core/entity/entities/Player;
/*     */     //   37	739	7	selectionProvider	Lcom/hypixel/hytale/server/core/prefab/selection/SelectionProvider;
/*     */     //   81	695	8	pattern	Ljava/util/regex/Pattern;
/*     */     //   121	655	9	stream	Ljava/util/stream/Stream;
/*     */     //   515	261	10	blocks	Ljava/util/List;
/*     */     //   547	229	11	largestBox	Lcom/hypixel/hytale/math/shape/Box;
/*     */     //   620	156	12	paddingSize	I
/*     */     //   638	138	13	sqrt	I
/*     */     //   651	125	14	strideX	I
/*     */     //   664	112	15	strideZ	I
/*     */     //   670	106	16	halfStrideX	I
/*     */     //   676	100	17	halfStrideZ	I
/*     */     //   683	93	18	height	D
/*     */     //   740	36	20	groundBlock	Ljava/lang/String;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   578	23	13	block	Ljava/util/Map$Entry<Ljava/lang/String;Lcom/hypixel/hytale/server/core/asset/type/blockhitbox/BlockBoundingBoxes;>;
/*     */     //   0	776	2	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   0	776	3	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   121	655	9	stream	Ljava/util/stream/Stream<Ljava/util/Map$Entry<Ljava/lang/String;Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType;>;>;
/*     */     //   515	261	10	blocks	Ljava/util/List<Ljava/util/Map$Entry<Ljava/lang/String;Lcom/hypixel/hytale/server/core/asset/type/blockhitbox/BlockBoundingBoxes;>;>;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockSelectCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */