/*     */ package com.hypixel.hytale.builtin.adventure.farming;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.FarmingBlock;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OnFarmBlockAdded
/*     */   extends RefSystem<ChunkStore>
/*     */ {
/*  98 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] {
/*  99 */         (Query)BlockModule.BlockStateInfo.getComponentType(), 
/* 100 */         (Query)FarmingBlock.getComponentType()
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*     */     // Byte code:
/*     */     //   0: aload #4
/*     */     //   2: aload_1
/*     */     //   3: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   6: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   9: checkcast com/hypixel/hytale/builtin/adventure/farming/states/FarmingBlock
/*     */     //   12: astore #5
/*     */     //   14: getstatic com/hypixel/hytale/builtin/adventure/farming/FarmingSystems$OnFarmBlockAdded.$assertionsDisabled : Z
/*     */     //   17: ifne -> 33
/*     */     //   20: aload #5
/*     */     //   22: ifnonnull -> 33
/*     */     //   25: new java/lang/AssertionError
/*     */     //   28: dup
/*     */     //   29: invokespecial <init> : ()V
/*     */     //   32: athrow
/*     */     //   33: aload #4
/*     */     //   35: aload_1
/*     */     //   36: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   39: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   42: checkcast com/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfo
/*     */     //   45: astore #6
/*     */     //   47: getstatic com/hypixel/hytale/builtin/adventure/farming/FarmingSystems$OnFarmBlockAdded.$assertionsDisabled : Z
/*     */     //   50: ifne -> 66
/*     */     //   53: aload #6
/*     */     //   55: ifnonnull -> 66
/*     */     //   58: new java/lang/AssertionError
/*     */     //   61: dup
/*     */     //   62: invokespecial <init> : ()V
/*     */     //   65: athrow
/*     */     //   66: aload #5
/*     */     //   68: invokevirtual getLastTickGameTime : ()Ljava/time/Instant;
/*     */     //   71: ifnonnull -> 479
/*     */     //   74: aload #4
/*     */     //   76: aload #6
/*     */     //   78: invokevirtual getChunkRef : ()Lcom/hypixel/hytale/component/Ref;
/*     */     //   81: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   84: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   87: checkcast com/hypixel/hytale/server/core/universe/world/chunk/BlockChunk
/*     */     //   90: astore #7
/*     */     //   92: aload #7
/*     */     //   94: aload #6
/*     */     //   96: invokevirtual getIndex : ()I
/*     */     //   99: invokestatic xFromBlockInColumn : (I)I
/*     */     //   102: aload #6
/*     */     //   104: invokevirtual getIndex : ()I
/*     */     //   107: invokestatic yFromBlockInColumn : (I)I
/*     */     //   110: aload #6
/*     */     //   112: invokevirtual getIndex : ()I
/*     */     //   115: invokestatic zFromBlockInColumn : (I)I
/*     */     //   118: invokevirtual getBlock : (III)I
/*     */     //   121: istore #8
/*     */     //   123: invokestatic getAssetMap : ()Lcom/hypixel/hytale/assetstore/map/BlockTypeAssetMap;
/*     */     //   126: iload #8
/*     */     //   128: invokevirtual getAsset : (I)Lcom/hypixel/hytale/assetstore/map/JsonAssetWithMap;
/*     */     //   131: checkcast com/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType
/*     */     //   134: astore #9
/*     */     //   136: aload #9
/*     */     //   138: invokevirtual getFarming : ()Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/farming/FarmingData;
/*     */     //   141: ifnonnull -> 145
/*     */     //   144: return
/*     */     //   145: aload #5
/*     */     //   147: aload #9
/*     */     //   149: invokevirtual getFarming : ()Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/farming/FarmingData;
/*     */     //   152: invokevirtual getStartingStageSet : ()Ljava/lang/String;
/*     */     //   155: invokevirtual setCurrentStageSet : (Ljava/lang/String;)V
/*     */     //   158: aload #5
/*     */     //   160: aload_3
/*     */     //   161: invokevirtual getExternalData : ()Ljava/lang/Object;
/*     */     //   164: checkcast com/hypixel/hytale/server/core/universe/world/storage/ChunkStore
/*     */     //   167: invokevirtual getWorld : ()Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   170: invokevirtual getEntityStore : ()Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;
/*     */     //   173: invokevirtual getStore : ()Lcom/hypixel/hytale/component/Store;
/*     */     //   176: invokestatic getResourceType : ()Lcom/hypixel/hytale/component/ResourceType;
/*     */     //   179: invokevirtual getResource : (Lcom/hypixel/hytale/component/ResourceType;)Lcom/hypixel/hytale/component/Resource;
/*     */     //   182: checkcast com/hypixel/hytale/server/core/modules/time/WorldTimeResource
/*     */     //   185: invokevirtual getGameTime : ()Ljava/time/Instant;
/*     */     //   188: invokevirtual setLastTickGameTime : (Ljava/time/Instant;)V
/*     */     //   191: aload #9
/*     */     //   193: invokevirtual getFarming : ()Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/farming/FarmingData;
/*     */     //   196: invokevirtual getStages : ()Ljava/util/Map;
/*     */     //   199: ifnull -> 479
/*     */     //   202: aload #9
/*     */     //   204: invokevirtual getFarming : ()Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/farming/FarmingData;
/*     */     //   207: invokevirtual getStages : ()Ljava/util/Map;
/*     */     //   210: aload #9
/*     */     //   212: invokevirtual getFarming : ()Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/farming/FarmingData;
/*     */     //   215: invokevirtual getStartingStageSet : ()Ljava/lang/String;
/*     */     //   218: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   223: checkcast [Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/farming/FarmingStageData;
/*     */     //   226: astore #10
/*     */     //   228: aload #10
/*     */     //   230: ifnull -> 479
/*     */     //   233: aload #10
/*     */     //   235: arraylength
/*     */     //   236: ifle -> 479
/*     */     //   239: iconst_0
/*     */     //   240: istore #11
/*     */     //   242: iconst_0
/*     */     //   243: istore #12
/*     */     //   245: iload #12
/*     */     //   247: aload #10
/*     */     //   249: arraylength
/*     */     //   250: if_icmpge -> 405
/*     */     //   253: aload #10
/*     */     //   255: iload #12
/*     */     //   257: aaload
/*     */     //   258: astore #13
/*     */     //   260: aload #13
/*     */     //   262: dup
/*     */     //   263: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   266: pop
/*     */     //   267: astore #14
/*     */     //   269: iconst_0
/*     */     //   270: istore #15
/*     */     //   272: aload #14
/*     */     //   274: iload #15
/*     */     //   276: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   281: lookupswitch default -> 399, 0 -> 308, 1 -> 345
/*     */     //   308: aload #14
/*     */     //   310: checkcast com/hypixel/hytale/builtin/adventure/farming/config/stages/BlockTypeFarmingStageData
/*     */     //   313: astore #16
/*     */     //   315: aload #16
/*     */     //   317: invokevirtual getBlock : ()Ljava/lang/String;
/*     */     //   320: aload #9
/*     */     //   322: invokevirtual getId : ()Ljava/lang/String;
/*     */     //   325: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   328: ifeq -> 399
/*     */     //   331: aload #5
/*     */     //   333: iload #12
/*     */     //   335: i2f
/*     */     //   336: invokevirtual setGrowthProgress : (F)V
/*     */     //   339: iconst_1
/*     */     //   340: istore #11
/*     */     //   342: goto -> 399
/*     */     //   345: aload #14
/*     */     //   347: checkcast com/hypixel/hytale/builtin/adventure/farming/config/stages/BlockStateFarmingStageData
/*     */     //   350: astore #17
/*     */     //   352: aload #9
/*     */     //   354: aload #17
/*     */     //   356: invokevirtual getState : ()Ljava/lang/String;
/*     */     //   359: invokevirtual getBlockForState : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType;
/*     */     //   362: astore #18
/*     */     //   364: aload #18
/*     */     //   366: ifnull -> 396
/*     */     //   369: aload #18
/*     */     //   371: invokevirtual getId : ()Ljava/lang/String;
/*     */     //   374: aload #9
/*     */     //   376: invokevirtual getId : ()Ljava/lang/String;
/*     */     //   379: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   382: ifeq -> 396
/*     */     //   385: aload #5
/*     */     //   387: iload #12
/*     */     //   389: i2f
/*     */     //   390: invokevirtual setGrowthProgress : (F)V
/*     */     //   393: iconst_1
/*     */     //   394: istore #11
/*     */     //   396: goto -> 399
/*     */     //   399: iinc #12, 1
/*     */     //   402: goto -> 245
/*     */     //   405: iload #11
/*     */     //   407: ifne -> 479
/*     */     //   410: aload #4
/*     */     //   412: aload #6
/*     */     //   414: invokevirtual getChunkRef : ()Lcom/hypixel/hytale/component/Ref;
/*     */     //   417: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   420: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   423: checkcast com/hypixel/hytale/server/core/universe/world/chunk/ChunkColumn
/*     */     //   426: aload #6
/*     */     //   428: invokevirtual getIndex : ()I
/*     */     //   431: invokestatic yFromBlockInColumn : (I)I
/*     */     //   434: invokestatic chunkCoordinate : (I)I
/*     */     //   437: invokevirtual getSection : (I)Lcom/hypixel/hytale/component/Ref;
/*     */     //   440: astore #12
/*     */     //   442: aload #10
/*     */     //   444: iconst_0
/*     */     //   445: aaload
/*     */     //   446: aload #4
/*     */     //   448: aload #12
/*     */     //   450: aload_1
/*     */     //   451: aload #6
/*     */     //   453: invokevirtual getIndex : ()I
/*     */     //   456: invokestatic xFromBlockInColumn : (I)I
/*     */     //   459: aload #6
/*     */     //   461: invokevirtual getIndex : ()I
/*     */     //   464: invokestatic yFromBlockInColumn : (I)I
/*     */     //   467: aload #6
/*     */     //   469: invokevirtual getIndex : ()I
/*     */     //   472: invokestatic zFromBlockInColumn : (I)I
/*     */     //   475: aconst_null
/*     */     //   476: invokevirtual apply : (Lcom/hypixel/hytale/component/ComponentAccessor;Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/Ref;IIILcom/hypixel/hytale/server/core/asset/type/blocktype/config/farming/FarmingStageData;)V
/*     */     //   479: aload #5
/*     */     //   481: invokevirtual getLastTickGameTime : ()Ljava/time/Instant;
/*     */     //   484: ifnonnull -> 520
/*     */     //   487: aload #5
/*     */     //   489: aload_3
/*     */     //   490: invokevirtual getExternalData : ()Ljava/lang/Object;
/*     */     //   493: checkcast com/hypixel/hytale/server/core/universe/world/storage/ChunkStore
/*     */     //   496: invokevirtual getWorld : ()Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   499: invokevirtual getEntityStore : ()Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;
/*     */     //   502: invokevirtual getStore : ()Lcom/hypixel/hytale/component/Store;
/*     */     //   505: invokestatic getResourceType : ()Lcom/hypixel/hytale/component/ResourceType;
/*     */     //   508: invokevirtual getResource : (Lcom/hypixel/hytale/component/ResourceType;)Lcom/hypixel/hytale/component/Resource;
/*     */     //   511: checkcast com/hypixel/hytale/server/core/modules/time/WorldTimeResource
/*     */     //   514: invokevirtual getGameTime : ()Ljava/time/Instant;
/*     */     //   517: invokevirtual setLastTickGameTime : (Ljava/time/Instant;)V
/*     */     //   520: aload #6
/*     */     //   522: invokevirtual getIndex : ()I
/*     */     //   525: invokestatic xFromBlockInColumn : (I)I
/*     */     //   528: istore #7
/*     */     //   530: aload #6
/*     */     //   532: invokevirtual getIndex : ()I
/*     */     //   535: invokestatic yFromBlockInColumn : (I)I
/*     */     //   538: istore #8
/*     */     //   540: aload #6
/*     */     //   542: invokevirtual getIndex : ()I
/*     */     //   545: invokestatic zFromBlockInColumn : (I)I
/*     */     //   548: istore #9
/*     */     //   550: aload #4
/*     */     //   552: aload #6
/*     */     //   554: invokevirtual getChunkRef : ()Lcom/hypixel/hytale/component/Ref;
/*     */     //   557: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   560: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   563: checkcast com/hypixel/hytale/server/core/universe/world/chunk/BlockComponentChunk
/*     */     //   566: astore #10
/*     */     //   568: getstatic com/hypixel/hytale/builtin/adventure/farming/FarmingSystems$OnFarmBlockAdded.$assertionsDisabled : Z
/*     */     //   571: ifne -> 587
/*     */     //   574: aload #10
/*     */     //   576: ifnonnull -> 587
/*     */     //   579: new java/lang/AssertionError
/*     */     //   582: dup
/*     */     //   583: invokespecial <init> : ()V
/*     */     //   586: athrow
/*     */     //   587: aload #4
/*     */     //   589: aload #6
/*     */     //   591: invokevirtual getChunkRef : ()Lcom/hypixel/hytale/component/Ref;
/*     */     //   594: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   597: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   600: checkcast com/hypixel/hytale/server/core/universe/world/chunk/ChunkColumn
/*     */     //   603: astore #11
/*     */     //   605: getstatic com/hypixel/hytale/builtin/adventure/farming/FarmingSystems$OnFarmBlockAdded.$assertionsDisabled : Z
/*     */     //   608: ifne -> 624
/*     */     //   611: aload #11
/*     */     //   613: ifnonnull -> 624
/*     */     //   616: new java/lang/AssertionError
/*     */     //   619: dup
/*     */     //   620: invokespecial <init> : ()V
/*     */     //   623: athrow
/*     */     //   624: aload #11
/*     */     //   626: iload #8
/*     */     //   628: invokestatic chunkCoordinate : (I)I
/*     */     //   631: invokevirtual getSection : (I)Lcom/hypixel/hytale/component/Ref;
/*     */     //   634: astore #12
/*     */     //   636: aload #4
/*     */     //   638: aload #12
/*     */     //   640: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   643: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   646: checkcast com/hypixel/hytale/server/core/universe/world/chunk/section/BlockSection
/*     */     //   649: astore #13
/*     */     //   651: aload #4
/*     */     //   653: aload #13
/*     */     //   655: aload #12
/*     */     //   657: aload_1
/*     */     //   658: aload #5
/*     */     //   660: iload #7
/*     */     //   662: iload #8
/*     */     //   664: iload #9
/*     */     //   666: iconst_1
/*     */     //   667: invokestatic tickFarming : (Lcom/hypixel/hytale/component/CommandBuffer;Lcom/hypixel/hytale/server/core/universe/world/chunk/section/BlockSection;Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/builtin/adventure/farming/states/FarmingBlock;IIIZ)V
/*     */     //   670: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #105	-> 0
/*     */     //   #106	-> 14
/*     */     //   #107	-> 33
/*     */     //   #108	-> 47
/*     */     //   #111	-> 66
/*     */     //   #112	-> 74
/*     */     //   #113	-> 92
/*     */     //   #114	-> 96
/*     */     //   #115	-> 104
/*     */     //   #116	-> 112
/*     */     //   #113	-> 118
/*     */     //   #118	-> 123
/*     */     //   #119	-> 136
/*     */     //   #120	-> 145
/*     */     //   #122	-> 158
/*     */     //   #124	-> 191
/*     */     //   #125	-> 202
/*     */     //   #126	-> 228
/*     */     //   #128	-> 239
/*     */     //   #129	-> 242
/*     */     //   #130	-> 253
/*     */     //   #131	-> 260
/*     */     //   #132	-> 308
/*     */     //   #133	-> 315
/*     */     //   #134	-> 331
/*     */     //   #135	-> 339
/*     */     //   #138	-> 345
/*     */     //   #139	-> 352
/*     */     //   #140	-> 364
/*     */     //   #141	-> 385
/*     */     //   #142	-> 393
/*     */     //   #144	-> 396
/*     */     //   #129	-> 399
/*     */     //   #150	-> 405
/*     */     //   #151	-> 410
/*     */     //   #152	-> 442
/*     */     //   #153	-> 453
/*     */     //   #154	-> 461
/*     */     //   #155	-> 469
/*     */     //   #152	-> 476
/*     */     //   #162	-> 479
/*     */     //   #163	-> 487
/*     */     //   #166	-> 520
/*     */     //   #167	-> 530
/*     */     //   #168	-> 540
/*     */     //   #170	-> 550
/*     */     //   #171	-> 568
/*     */     //   #172	-> 587
/*     */     //   #173	-> 605
/*     */     //   #174	-> 624
/*     */     //   #175	-> 636
/*     */     //   #177	-> 651
/*     */     //   #178	-> 670
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   315	30	16	data	Lcom/hypixel/hytale/builtin/adventure/farming/config/stages/BlockTypeFarmingStageData;
/*     */     //   364	32	18	stateBlockType	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType;
/*     */     //   352	47	17	data	Lcom/hypixel/hytale/builtin/adventure/farming/config/stages/BlockStateFarmingStageData;
/*     */     //   260	139	13	stage	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/farming/FarmingStageData;
/*     */     //   245	160	12	i	I
/*     */     //   442	37	12	sectionRef	Lcom/hypixel/hytale/component/Ref;
/*     */     //   242	237	11	found	Z
/*     */     //   228	251	10	stages	[Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/farming/FarmingStageData;
/*     */     //   92	387	7	blockChunk	Lcom/hypixel/hytale/server/core/universe/world/chunk/BlockChunk;
/*     */     //   123	356	8	blockId	I
/*     */     //   136	343	9	blockType	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType;
/*     */     //   0	671	0	this	Lcom/hypixel/hytale/builtin/adventure/farming/FarmingSystems$OnFarmBlockAdded;
/*     */     //   0	671	1	ref	Lcom/hypixel/hytale/component/Ref;
/*     */     //   0	671	2	reason	Lcom/hypixel/hytale/component/AddReason;
/*     */     //   0	671	3	store	Lcom/hypixel/hytale/component/Store;
/*     */     //   0	671	4	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer;
/*     */     //   14	657	5	farmingBlock	Lcom/hypixel/hytale/builtin/adventure/farming/states/FarmingBlock;
/*     */     //   47	624	6	info	Lcom/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfo;
/*     */     //   530	141	7	x	I
/*     */     //   540	131	8	y	I
/*     */     //   550	121	9	z	I
/*     */     //   568	103	10	blockComponentChunk	Lcom/hypixel/hytale/server/core/universe/world/chunk/BlockComponentChunk;
/*     */     //   605	66	11	column	Lcom/hypixel/hytale/server/core/universe/world/chunk/ChunkColumn;
/*     */     //   636	35	12	section	Lcom/hypixel/hytale/component/Ref;
/*     */     //   651	20	13	blockSection	Lcom/hypixel/hytale/server/core/universe/world/chunk/section/BlockSection;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   442	37	12	sectionRef	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */     //   0	671	1	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */     //   0	671	3	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */     //   0	671	4	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */     //   636	35	12	section	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
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
/*     */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Query<ChunkStore> getQuery() {
/* 187 */     return QUERY;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\FarmingSystems$OnFarmBlockAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */