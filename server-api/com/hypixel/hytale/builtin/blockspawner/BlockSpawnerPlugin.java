/*     */ package com.hypixel.hytale.builtin.blockspawner;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.blockspawner.command.BlockSpawnerCommand;
/*     */ import com.hypixel.hytale.builtin.blockspawner.state.BlockSpawner;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.data.unknown.UnknownComponents;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockSpawnerPlugin extends JavaPlugin {
/*  38 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private ComponentType<ChunkStore, BlockSpawner> blockSpawnerComponentType;
/*     */   
/*     */   private static BlockSpawnerPlugin INSTANCE;
/*     */   
/*     */   public static BlockSpawnerPlugin get() {
/*  45 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public BlockSpawnerPlugin(@Nonnull JavaPluginInit init) {
/*  49 */     super(init);
/*  50 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  55 */     getCommandRegistry().registerCommand((AbstractCommand)new BlockSpawnerCommand());
/*     */     
/*  57 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(BlockSpawnerTable.class, (AssetMap)new DefaultAssetMap())
/*  58 */         .setPath("Item/Block/Spawners"))
/*  59 */         .setCodec((AssetCodec)BlockSpawnerTable.CODEC))
/*  60 */         .setKeyFunction(BlockSpawnerTable::getId))
/*  61 */         .loadsAfter(new Class[] { Item.class, BlockType.class
/*  62 */           })).build());
/*     */     
/*  64 */     this.blockSpawnerComponentType = getChunkStoreRegistry().registerComponent(BlockSpawner.class, "BlockSpawner", BlockSpawner.CODEC);
/*     */     
/*  66 */     getChunkStoreRegistry().registerSystem((ISystem)new BlockSpawnerSystem());
/*  67 */     getChunkStoreRegistry().registerSystem((ISystem)new MigrateBlockSpawner());
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, BlockSpawner> getBlockSpawnerComponentType() {
/*  71 */     return this.blockSpawnerComponentType;
/*     */   }
/*     */   
/*     */   private static class BlockSpawnerSystem extends RefSystem<ChunkStore> {
/*  75 */     private static final ComponentType<ChunkStore, BlockSpawner> COMPONENT_TYPE = BlockSpawner.getComponentType();
/*  76 */     private static final ComponentType<ChunkStore, BlockModule.BlockStateInfo> BLOCK_INFO_COMPONENT_TYPE = BlockModule.BlockStateInfo.getComponentType();
/*  77 */     private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)COMPONENT_TYPE, (Query)BLOCK_INFO_COMPONENT_TYPE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Query<ChunkStore> getQuery() {
/*  84 */       return QUERY;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*     */       // Byte code:
/*     */       //   0: aload_3
/*     */       //   1: invokevirtual getExternalData : ()Ljava/lang/Object;
/*     */       //   4: checkcast com/hypixel/hytale/server/core/universe/world/storage/ChunkStore
/*     */       //   7: invokevirtual getWorld : ()Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */       //   10: invokevirtual getWorldConfig : ()Lcom/hypixel/hytale/server/core/universe/world/WorldConfig;
/*     */       //   13: astore #5
/*     */       //   15: aload #5
/*     */       //   17: invokevirtual getGameMode : ()Lcom/hypixel/hytale/protocol/GameMode;
/*     */       //   20: getstatic com/hypixel/hytale/protocol/GameMode.Creative : Lcom/hypixel/hytale/protocol/GameMode;
/*     */       //   23: if_acmpne -> 27
/*     */       //   26: return
/*     */       //   27: aload #4
/*     */       //   29: aload_1
/*     */       //   30: getstatic com/hypixel/hytale/builtin/blockspawner/BlockSpawnerPlugin$BlockSpawnerSystem.COMPONENT_TYPE : Lcom/hypixel/hytale/component/ComponentType;
/*     */       //   33: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */       //   36: checkcast com/hypixel/hytale/builtin/blockspawner/state/BlockSpawner
/*     */       //   39: astore #6
/*     */       //   41: getstatic com/hypixel/hytale/builtin/blockspawner/BlockSpawnerPlugin$BlockSpawnerSystem.$assertionsDisabled : Z
/*     */       //   44: ifne -> 60
/*     */       //   47: aload #6
/*     */       //   49: ifnonnull -> 60
/*     */       //   52: new java/lang/AssertionError
/*     */       //   55: dup
/*     */       //   56: invokespecial <init> : ()V
/*     */       //   59: athrow
/*     */       //   60: aload #4
/*     */       //   62: aload_1
/*     */       //   63: getstatic com/hypixel/hytale/builtin/blockspawner/BlockSpawnerPlugin$BlockSpawnerSystem.BLOCK_INFO_COMPONENT_TYPE : Lcom/hypixel/hytale/component/ComponentType;
/*     */       //   66: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */       //   69: checkcast com/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfo
/*     */       //   72: astore #7
/*     */       //   74: getstatic com/hypixel/hytale/builtin/blockspawner/BlockSpawnerPlugin$BlockSpawnerSystem.$assertionsDisabled : Z
/*     */       //   77: ifne -> 93
/*     */       //   80: aload #7
/*     */       //   82: ifnonnull -> 93
/*     */       //   85: new java/lang/AssertionError
/*     */       //   88: dup
/*     */       //   89: invokespecial <init> : ()V
/*     */       //   92: athrow
/*     */       //   93: aload #6
/*     */       //   95: invokevirtual getBlockSpawnerId : ()Ljava/lang/String;
/*     */       //   98: astore #8
/*     */       //   100: aload #8
/*     */       //   102: ifnonnull -> 106
/*     */       //   105: return
/*     */       //   106: invokestatic getAssetMap : ()Lcom/hypixel/hytale/assetstore/map/DefaultAssetMap;
/*     */       //   109: aload #8
/*     */       //   111: invokevirtual getAsset : (Ljava/lang/Object;)Lcom/hypixel/hytale/assetstore/JsonAsset;
/*     */       //   114: checkcast com/hypixel/hytale/builtin/blockspawner/BlockSpawnerTable
/*     */       //   117: astore #9
/*     */       //   119: aload #9
/*     */       //   121: ifnonnull -> 143
/*     */       //   124: getstatic com/hypixel/hytale/builtin/blockspawner/BlockSpawnerPlugin.LOGGER : Lcom/hypixel/hytale/logger/HytaleLogger;
/*     */       //   127: getstatic java/util/logging/Level.WARNING : Ljava/util/logging/Level;
/*     */       //   130: invokevirtual at : (Ljava/util/logging/Level;)Lcom/hypixel/hytale/logger/HytaleLogger$Api;
/*     */       //   133: ldc 'Failed to find BlockSpawner Asset by name: %s'
/*     */       //   135: aload #8
/*     */       //   137: invokeinterface log : (Ljava/lang/String;Ljava/lang/Object;)V
/*     */       //   142: return
/*     */       //   143: aload #7
/*     */       //   145: invokevirtual getChunkRef : ()Lcom/hypixel/hytale/component/Ref;
/*     */       //   148: astore #10
/*     */       //   150: aload #10
/*     */       //   152: ifnonnull -> 156
/*     */       //   155: return
/*     */       //   156: aload #4
/*     */       //   158: aload #10
/*     */       //   160: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */       //   163: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */       //   166: checkcast com/hypixel/hytale/server/core/universe/world/chunk/WorldChunk
/*     */       //   169: astore #11
/*     */       //   171: aload #11
/*     */       //   173: invokevirtual getX : ()I
/*     */       //   176: aload #7
/*     */       //   178: invokevirtual getIndex : ()I
/*     */       //   181: invokestatic xFromBlockInColumn : (I)I
/*     */       //   184: invokestatic worldCoordFromLocalCoord : (II)I
/*     */       //   187: istore #12
/*     */       //   189: aload #7
/*     */       //   191: invokevirtual getIndex : ()I
/*     */       //   194: invokestatic yFromBlockInColumn : (I)I
/*     */       //   197: istore #13
/*     */       //   199: aload #11
/*     */       //   201: invokevirtual getZ : ()I
/*     */       //   204: aload #7
/*     */       //   206: invokevirtual getIndex : ()I
/*     */       //   209: invokestatic zFromBlockInColumn : (I)I
/*     */       //   212: invokestatic worldCoordFromLocalCoord : (II)I
/*     */       //   215: istore #14
/*     */       //   217: aload #5
/*     */       //   219: invokevirtual getSeed : ()J
/*     */       //   222: lstore #15
/*     */       //   224: iload #12
/*     */       //   226: i2l
/*     */       //   227: iload #13
/*     */       //   229: i2l
/*     */       //   230: iload #14
/*     */       //   232: i2l
/*     */       //   233: lload #15
/*     */       //   235: ldc2_w -1699164769
/*     */       //   238: ladd
/*     */       //   239: invokestatic random : (JJJJ)D
/*     */       //   242: dstore #17
/*     */       //   244: aload #9
/*     */       //   246: invokevirtual getEntries : ()Lcom/hypixel/hytale/common/map/IWeightedMap;
/*     */       //   249: dload #17
/*     */       //   251: invokeinterface get : (D)Ljava/lang/Object;
/*     */       //   256: checkcast com/hypixel/hytale/builtin/blockspawner/BlockSpawnerEntry
/*     */       //   259: astore #19
/*     */       //   261: aload #19
/*     */       //   263: ifnonnull -> 267
/*     */       //   266: return
/*     */       //   267: aload #19
/*     */       //   269: invokevirtual getBlockName : ()Ljava/lang/String;
/*     */       //   272: astore #20
/*     */       //   274: getstatic com/hypixel/hytale/builtin/blockspawner/BlockSpawnerPlugin$1.$SwitchMap$com$hypixel$hytale$builtin$blockspawner$BlockSpawnerEntry$RotationMode : [I
/*     */       //   277: aload #19
/*     */       //   279: invokevirtual getRotationMode : ()Lcom/hypixel/hytale/builtin/blockspawner/BlockSpawnerEntry$RotationMode;
/*     */       //   282: invokevirtual ordinal : ()I
/*     */       //   285: iaload
/*     */       //   286: tableswitch default -> 312, 1 -> 322, 2 -> 328, 3 -> 418
/*     */       //   312: new java/lang/MatchException
/*     */       //   315: dup
/*     */       //   316: aconst_null
/*     */       //   317: aconst_null
/*     */       //   318: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*     */       //   321: athrow
/*     */       //   322: getstatic com/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple.NONE : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   325: goto -> 494
/*     */       //   328: aload #19
/*     */       //   330: invokevirtual getBlockName : ()Ljava/lang/String;
/*     */       //   333: astore #22
/*     */       //   335: invokestatic getAssetMap : ()Lcom/hypixel/hytale/assetstore/map/BlockTypeAssetMap;
/*     */       //   338: aload #22
/*     */       //   340: invokevirtual getAsset : (Ljava/lang/Object;)Lcom/hypixel/hytale/assetstore/JsonAsset;
/*     */       //   343: checkcast com/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType
/*     */       //   346: invokevirtual getVariantRotation : ()Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;
/*     */       //   349: astore #23
/*     */       //   351: aload #23
/*     */       //   353: getstatic com/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation.None : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;
/*     */       //   356: if_acmpne -> 365
/*     */       //   359: getstatic com/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple.NONE : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   362: goto -> 494
/*     */       //   365: iload #12
/*     */       //   367: i2l
/*     */       //   368: iload #13
/*     */       //   370: i2l
/*     */       //   371: iload #14
/*     */       //   373: i2l
/*     */       //   374: lload #15
/*     */       //   376: ldc2_w -1699164769
/*     */       //   379: ladd
/*     */       //   380: invokestatic rehash : (JJJJ)J
/*     */       //   383: l2i
/*     */       //   384: istore #24
/*     */       //   386: getstatic com/hypixel/hytale/server/core/asset/type/blocktype/config/Rotation.NORMAL : [Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/Rotation;
/*     */       //   389: iload #24
/*     */       //   391: ldc 65535
/*     */       //   393: iand
/*     */       //   394: getstatic com/hypixel/hytale/server/core/asset/type/blocktype/config/Rotation.NORMAL : [Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/Rotation;
/*     */       //   397: arraylength
/*     */       //   398: irem
/*     */       //   399: aaload
/*     */       //   400: astore #25
/*     */       //   402: getstatic com/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple.NONE : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   405: getstatic com/hypixel/hytale/math/Axis.Y : Lcom/hypixel/hytale/math/Axis;
/*     */       //   408: aload #25
/*     */       //   410: aload #23
/*     */       //   412: invokestatic getRotated : (Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;Lcom/hypixel/hytale/math/Axis;Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/Rotation;Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;)Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   415: goto -> 494
/*     */       //   418: aload #19
/*     */       //   420: invokevirtual getBlockName : ()Ljava/lang/String;
/*     */       //   423: astore #22
/*     */       //   425: invokestatic getAssetMap : ()Lcom/hypixel/hytale/assetstore/map/BlockTypeAssetMap;
/*     */       //   428: aload #22
/*     */       //   430: invokevirtual getAsset : (Ljava/lang/Object;)Lcom/hypixel/hytale/assetstore/JsonAsset;
/*     */       //   433: checkcast com/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType
/*     */       //   436: invokevirtual getVariantRotation : ()Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;
/*     */       //   439: astore #23
/*     */       //   441: aload #23
/*     */       //   443: getstatic com/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation.None : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;
/*     */       //   446: if_acmpne -> 455
/*     */       //   449: getstatic com/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple.NONE : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   452: goto -> 494
/*     */       //   455: aload #11
/*     */       //   457: iload #12
/*     */       //   459: iload #13
/*     */       //   461: iload #14
/*     */       //   463: invokevirtual getRotationIndex : (III)I
/*     */       //   466: invokestatic get : (I)Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   469: astore #24
/*     */       //   471: aload #24
/*     */       //   473: invokevirtual yaw : ()Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/Rotation;
/*     */       //   476: astore #25
/*     */       //   478: getstatic com/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple.NONE : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   481: getstatic com/hypixel/hytale/math/Axis.Y : Lcom/hypixel/hytale/math/Axis;
/*     */       //   484: aload #25
/*     */       //   486: aload #23
/*     */       //   488: invokestatic getRotated : (Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;Lcom/hypixel/hytale/math/Axis;Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/Rotation;Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;)Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   491: goto -> 494
/*     */       //   494: astore #21
/*     */       //   496: aload #19
/*     */       //   498: invokevirtual getBlockComponents : ()Lcom/hypixel/hytale/component/Holder;
/*     */       //   501: astore #22
/*     */       //   503: aload #4
/*     */       //   505: aload_1
/*     */       //   506: getstatic com/hypixel/hytale/component/RemoveReason.REMOVE : Lcom/hypixel/hytale/component/RemoveReason;
/*     */       //   509: invokevirtual removeEntity : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/RemoveReason;)V
/*     */       //   512: aload #4
/*     */       //   514: aload #22
/*     */       //   516: aload #20
/*     */       //   518: aload #11
/*     */       //   520: iload #12
/*     */       //   522: iload #13
/*     */       //   524: iload #14
/*     */       //   526: aload #21
/*     */       //   528: <illegal opcode> accept : (Lcom/hypixel/hytale/component/Holder;Ljava/lang/String;Lcom/hypixel/hytale/server/core/universe/world/chunk/WorldChunk;IIILcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;)Ljava/util/function/Consumer;
/*     */       //   533: invokevirtual run : (Ljava/util/function/Consumer;)V
/*     */       //   536: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       //   #90	-> 15
/*     */       //   #92	-> 27
/*     */       //   #93	-> 41
/*     */       //   #95	-> 60
/*     */       //   #96	-> 74
/*     */       //   #98	-> 93
/*     */       //   #99	-> 100
/*     */       //   #101	-> 106
/*     */       //   #102	-> 119
/*     */       //   #103	-> 124
/*     */       //   #104	-> 142
/*     */       //   #107	-> 143
/*     */       //   #108	-> 150
/*     */       //   #109	-> 156
/*     */       //   #111	-> 171
/*     */       //   #112	-> 189
/*     */       //   #113	-> 199
/*     */       //   #116	-> 217
/*     */       //   #117	-> 224
/*     */       //   #119	-> 244
/*     */       //   #120	-> 261
/*     */       //   #122	-> 267
/*     */       //   #123	-> 274
/*     */       //   #124	-> 322
/*     */       //   #126	-> 328
/*     */       //   #127	-> 335
/*     */       //   #128	-> 351
/*     */       //   #129	-> 359
/*     */       //   #132	-> 365
/*     */       //   #133	-> 386
/*     */       //   #134	-> 402
/*     */       //   #137	-> 418
/*     */       //   #138	-> 425
/*     */       //   #139	-> 441
/*     */       //   #140	-> 449
/*     */       //   #143	-> 455
/*     */       //   #144	-> 471
/*     */       //   #145	-> 478
/*     */       //   #123	-> 494
/*     */       //   #149	-> 496
/*     */       //   #153	-> 503
/*     */       //   #154	-> 512
/*     */       //   #164	-> 536
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   335	83	22	key	Ljava/lang/String;
/*     */       //   351	67	23	variantRotation	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;
/*     */       //   386	32	24	randomHash	I
/*     */       //   402	16	25	rotationYaw	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/Rotation;
/*     */       //   425	69	22	key	Ljava/lang/String;
/*     */       //   441	53	23	variantRotation	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/VariantRotation;
/*     */       //   471	23	24	spawnerRotation	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   478	16	25	spawnerYaw	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/Rotation;
/*     */       //   0	537	0	this	Lcom/hypixel/hytale/builtin/blockspawner/BlockSpawnerPlugin$BlockSpawnerSystem;
/*     */       //   0	537	1	ref	Lcom/hypixel/hytale/component/Ref;
/*     */       //   0	537	2	reason	Lcom/hypixel/hytale/component/AddReason;
/*     */       //   0	537	3	store	Lcom/hypixel/hytale/component/Store;
/*     */       //   0	537	4	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer;
/*     */       //   15	522	5	worldConfig	Lcom/hypixel/hytale/server/core/universe/world/WorldConfig;
/*     */       //   41	496	6	state	Lcom/hypixel/hytale/builtin/blockspawner/state/BlockSpawner;
/*     */       //   74	463	7	info	Lcom/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfo;
/*     */       //   100	437	8	blockSpawnerId	Ljava/lang/String;
/*     */       //   119	418	9	table	Lcom/hypixel/hytale/builtin/blockspawner/BlockSpawnerTable;
/*     */       //   150	387	10	chunk	Lcom/hypixel/hytale/component/Ref;
/*     */       //   171	366	11	wc	Lcom/hypixel/hytale/server/core/universe/world/chunk/WorldChunk;
/*     */       //   189	348	12	x	I
/*     */       //   199	338	13	y	I
/*     */       //   217	320	14	z	I
/*     */       //   224	313	15	seed	J
/*     */       //   244	293	17	randomRnd	D
/*     */       //   261	276	19	entry	Lcom/hypixel/hytale/builtin/blockspawner/BlockSpawnerEntry;
/*     */       //   274	263	20	blockKey	Ljava/lang/String;
/*     */       //   496	41	21	rotation	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*     */       //   503	34	22	holder	Lcom/hypixel/hytale/component/Holder;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	537	1	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   0	537	3	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   0	537	4	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   150	387	10	chunk	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   503	34	22	holder	Lcom/hypixel/hytale/component/Holder<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
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
/*     */   @Deprecated(forRemoval = true)
/*     */   public static class MigrateBlockSpawner
/*     */     extends BlockModule.MigrationSystem
/*     */   {
/*     */     public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {
/* 174 */       UnknownComponents<ChunkStore> unknown = (UnknownComponents<ChunkStore>)holder.getComponent(ChunkStore.REGISTRY.getUnknownComponentType());
/* 175 */       assert unknown != null;
/* 176 */       BlockSpawner blockSpawner = (BlockSpawner)unknown.removeComponent("blockspawner", (Codec)BlockSpawner.CODEC);
/* 177 */       if (blockSpawner != null) {
/* 178 */         holder.putComponent(BlockSpawner.getComponentType(), (Component)blockSpawner);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<ChunkStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store) {}
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Query<ChunkStore> getQuery() {
/* 190 */       return (Query<ChunkStore>)ChunkStore.REGISTRY.getUnknownComponentType();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockspawner\BlockSpawnerPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */