/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
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
/*     */ public class BreakBlockInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<BreakBlockInteraction> CODEC;
/*     */   protected boolean harvest;
/*     */   @Nullable
/*     */   protected String toolId;
/*     */   protected boolean matchTool;
/*     */   
/*     */   static {
/*  64 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BreakBlockInteraction.class, BreakBlockInteraction::new, SimpleBlockInteraction.CODEC).documentation("Attempts to break the target block.")).appendInherited(new KeyedCodec("Harvest", (Codec)Codec.BOOLEAN), (interaction, v) -> interaction.harvest = v.booleanValue(), interaction -> Boolean.valueOf(interaction.harvest), (o, p) -> o.harvest = p.harvest).documentation("Whether this should trigger as a harvest gather vs a break gather.").add()).appendInherited(new KeyedCodec("Tool", (Codec)Codec.STRING), (interaction, v) -> interaction.toolId = v, interaction -> interaction.toolId, (o, p) -> o.toolId = p.toolId).documentation("Tool to break as.").add()).appendInherited(new KeyedCodec("MatchTool", (Codec)Codec.BOOLEAN), (interaction, v) -> interaction.matchTool = v.booleanValue(), interaction -> Boolean.valueOf(interaction.matchTool), (o, p) -> o.matchTool = p.matchTool).documentation("Whether to require an match to `Tool` to work.").add()).build();
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
/*     */   protected void tick0(boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  81 */     super.tick0(firstRun, time, type, context, cooldownHandler);
/*  82 */     computeCurrentBlockSyncData(context);
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
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack heldItemStack, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*     */     // Byte code:
/*     */     //   0: aload #4
/*     */     //   2: invokevirtual getEntity : ()Lcom/hypixel/hytale/component/Ref;
/*     */     //   5: astore #8
/*     */     //   7: aload_2
/*     */     //   8: aload #8
/*     */     //   10: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   13: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   16: checkcast com/hypixel/hytale/server/core/entity/entities/Player
/*     */     //   19: astore #9
/*     */     //   21: aload #9
/*     */     //   23: ifnonnull -> 57
/*     */     //   26: invokestatic getLogger : ()Lcom/hypixel/hytale/logger/HytaleLogger;
/*     */     //   29: getstatic java/util/logging/Level.INFO : Ljava/util/logging/Level;
/*     */     //   32: invokevirtual at : (Ljava/util/logging/Level;)Lcom/hypixel/hytale/logger/HytaleLogger$Api;
/*     */     //   35: iconst_5
/*     */     //   36: getstatic java/util/concurrent/TimeUnit.MINUTES : Ljava/util/concurrent/TimeUnit;
/*     */     //   39: invokeinterface atMostEvery : (ILjava/util/concurrent/TimeUnit;)Lcom/google/common/flogger/LoggingApi;
/*     */     //   44: checkcast com/hypixel/hytale/logger/HytaleLogger$Api
/*     */     //   47: ldc 'BreakBlockInteraction requires a Player but was used for: %s'
/*     */     //   49: aload #8
/*     */     //   51: invokeinterface log : (Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   56: return
/*     */     //   57: aload_1
/*     */     //   58: invokevirtual getChunkStore : ()Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;
/*     */     //   61: astore #10
/*     */     //   63: aload #10
/*     */     //   65: invokevirtual getStore : ()Lcom/hypixel/hytale/component/Store;
/*     */     //   68: astore #11
/*     */     //   70: aload #6
/*     */     //   72: getfield x : I
/*     */     //   75: aload #6
/*     */     //   77: getfield z : I
/*     */     //   80: invokestatic indexChunkFromBlock : (II)J
/*     */     //   83: lstore #12
/*     */     //   85: aload #10
/*     */     //   87: lload #12
/*     */     //   89: invokevirtual getChunkReference : (J)Lcom/hypixel/hytale/component/Ref;
/*     */     //   92: astore #14
/*     */     //   94: aload #14
/*     */     //   96: ifnull -> 107
/*     */     //   99: aload #14
/*     */     //   101: invokevirtual isValid : ()Z
/*     */     //   104: ifne -> 108
/*     */     //   107: return
/*     */     //   108: aload #11
/*     */     //   110: aload #14
/*     */     //   112: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   115: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   118: checkcast com/hypixel/hytale/server/core/universe/world/chunk/WorldChunk
/*     */     //   121: astore #15
/*     */     //   123: getstatic com/hypixel/hytale/server/core/modules/interaction/interaction/config/client/BreakBlockInteraction.$assertionsDisabled : Z
/*     */     //   126: ifne -> 142
/*     */     //   129: aload #15
/*     */     //   131: ifnonnull -> 142
/*     */     //   134: new java/lang/AssertionError
/*     */     //   137: dup
/*     */     //   138: invokespecial <init> : ()V
/*     */     //   141: athrow
/*     */     //   142: aload #11
/*     */     //   144: aload #14
/*     */     //   146: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   149: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   152: checkcast com/hypixel/hytale/server/core/universe/world/chunk/BlockChunk
/*     */     //   155: astore #16
/*     */     //   157: getstatic com/hypixel/hytale/server/core/modules/interaction/interaction/config/client/BreakBlockInteraction.$assertionsDisabled : Z
/*     */     //   160: ifne -> 176
/*     */     //   163: aload #16
/*     */     //   165: ifnonnull -> 176
/*     */     //   168: new java/lang/AssertionError
/*     */     //   171: dup
/*     */     //   172: invokespecial <init> : ()V
/*     */     //   175: athrow
/*     */     //   176: aload #16
/*     */     //   178: aload #6
/*     */     //   180: invokevirtual getY : ()I
/*     */     //   183: invokevirtual getSectionAtBlockY : (I)Lcom/hypixel/hytale/server/core/universe/world/chunk/section/BlockSection;
/*     */     //   186: astore #17
/*     */     //   188: aload_1
/*     */     //   189: invokevirtual getGameplayConfig : ()Lcom/hypixel/hytale/server/core/asset/type/gameplay/GameplayConfig;
/*     */     //   192: astore #18
/*     */     //   194: aload #18
/*     */     //   196: invokevirtual getWorldConfig : ()Lcom/hypixel/hytale/server/core/asset/type/gameplay/WorldConfig;
/*     */     //   199: astore #19
/*     */     //   201: aload_0
/*     */     //   202: getfield harvest : Z
/*     */     //   205: ifeq -> 331
/*     */     //   208: aload #6
/*     */     //   210: invokevirtual getX : ()I
/*     */     //   213: istore #20
/*     */     //   215: aload #6
/*     */     //   217: invokevirtual getY : ()I
/*     */     //   220: istore #21
/*     */     //   222: aload #6
/*     */     //   224: invokevirtual getZ : ()I
/*     */     //   227: istore #22
/*     */     //   229: aload #15
/*     */     //   231: iload #20
/*     */     //   233: iload #21
/*     */     //   235: iload #22
/*     */     //   237: invokevirtual getBlockType : (III)Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType;
/*     */     //   240: astore #23
/*     */     //   242: aload #23
/*     */     //   244: ifnonnull -> 259
/*     */     //   247: aload #4
/*     */     //   249: invokevirtual getState : ()Lcom/hypixel/hytale/protocol/InteractionSyncData;
/*     */     //   252: getstatic com/hypixel/hytale/protocol/InteractionState.Failed : Lcom/hypixel/hytale/protocol/InteractionState;
/*     */     //   255: putfield state : Lcom/hypixel/hytale/protocol/InteractionState;
/*     */     //   258: return
/*     */     //   259: aload #19
/*     */     //   261: invokevirtual isBlockGatheringAllowed : ()Z
/*     */     //   264: ifne -> 279
/*     */     //   267: aload #4
/*     */     //   269: invokevirtual getState : ()Lcom/hypixel/hytale/protocol/InteractionSyncData;
/*     */     //   272: getstatic com/hypixel/hytale/protocol/InteractionState.Failed : Lcom/hypixel/hytale/protocol/InteractionState;
/*     */     //   275: putfield state : Lcom/hypixel/hytale/protocol/InteractionState;
/*     */     //   278: return
/*     */     //   279: aload #23
/*     */     //   281: invokestatic shouldPickupByInteraction : (Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType;)Z
/*     */     //   284: ifne -> 299
/*     */     //   287: aload #4
/*     */     //   289: invokevirtual getState : ()Lcom/hypixel/hytale/protocol/InteractionSyncData;
/*     */     //   292: getstatic com/hypixel/hytale/protocol/InteractionState.Failed : Lcom/hypixel/hytale/protocol/InteractionState;
/*     */     //   295: putfield state : Lcom/hypixel/hytale/protocol/InteractionState;
/*     */     //   298: return
/*     */     //   299: aload #17
/*     */     //   301: iload #20
/*     */     //   303: iload #21
/*     */     //   305: iload #22
/*     */     //   307: invokevirtual getFiller : (III)I
/*     */     //   310: istore #24
/*     */     //   312: aload #8
/*     */     //   314: aload #6
/*     */     //   316: aload #23
/*     */     //   318: iload #24
/*     */     //   320: aload #14
/*     */     //   322: aload_2
/*     */     //   323: aload #11
/*     */     //   325: invokestatic performPickupByInteraction : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/math/vector/Vector3i;Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType;ILcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentAccessor;Lcom/hypixel/hytale/component/ComponentAccessor;)V
/*     */     //   328: goto -> 458
/*     */     //   331: aload #19
/*     */     //   333: invokevirtual isBlockBreakingAllowed : ()Z
/*     */     //   336: istore #20
/*     */     //   338: iload #20
/*     */     //   340: ifne -> 355
/*     */     //   343: aload #4
/*     */     //   345: invokevirtual getState : ()Lcom/hypixel/hytale/protocol/InteractionSyncData;
/*     */     //   348: getstatic com/hypixel/hytale/protocol/InteractionState.Failed : Lcom/hypixel/hytale/protocol/InteractionState;
/*     */     //   351: putfield state : Lcom/hypixel/hytale/protocol/InteractionState;
/*     */     //   354: return
/*     */     //   355: aload #9
/*     */     //   357: invokevirtual getGameMode : ()Lcom/hypixel/hytale/protocol/GameMode;
/*     */     //   360: astore #21
/*     */     //   362: iconst_0
/*     */     //   363: istore #22
/*     */     //   365: aload #21
/*     */     //   367: iload #22
/*     */     //   369: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   374: tableswitch default -> 448, -1 -> 448, 0 -> 400, 1 -> 431
/*     */     //   400: aload #9
/*     */     //   402: aload #8
/*     */     //   404: aload #6
/*     */     //   406: aload #5
/*     */     //   408: aconst_null
/*     */     //   409: aload_0
/*     */     //   410: getfield toolId : Ljava/lang/String;
/*     */     //   413: aload_0
/*     */     //   414: getfield matchTool : Z
/*     */     //   417: fconst_1
/*     */     //   418: iconst_0
/*     */     //   419: aload #14
/*     */     //   421: aload_2
/*     */     //   422: aload #11
/*     */     //   424: invokestatic performBlockDamage : (Lcom/hypixel/hytale/server/core/entity/LivingEntity;Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/math/vector/Vector3i;Lcom/hypixel/hytale/server/core/inventory/ItemStack;Lcom/hypixel/hytale/server/core/asset/type/item/config/ItemTool;Ljava/lang/String;ZFILcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentAccessor;Lcom/hypixel/hytale/component/ComponentAccessor;)Z
/*     */     //   427: pop
/*     */     //   428: goto -> 458
/*     */     //   431: aload #8
/*     */     //   433: aload #5
/*     */     //   435: aload #6
/*     */     //   437: aload #14
/*     */     //   439: aload_2
/*     */     //   440: aload #11
/*     */     //   442: invokestatic performBlockBreak : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/server/core/inventory/ItemStack;Lcom/hypixel/hytale/math/vector/Vector3i;Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentAccessor;Lcom/hypixel/hytale/component/ComponentAccessor;)V
/*     */     //   445: goto -> 458
/*     */     //   448: new java/lang/UnsupportedOperationException
/*     */     //   451: dup
/*     */     //   452: ldc 'GameMode is not supported'
/*     */     //   454: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   457: athrow
/*     */     //   458: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #87	-> 0
/*     */     //   #88	-> 7
/*     */     //   #90	-> 21
/*     */     //   #91	-> 26
/*     */     //   #92	-> 39
/*     */     //   #93	-> 51
/*     */     //   #94	-> 56
/*     */     //   #97	-> 57
/*     */     //   #98	-> 63
/*     */     //   #100	-> 70
/*     */     //   #101	-> 85
/*     */     //   #102	-> 94
/*     */     //   #104	-> 108
/*     */     //   #105	-> 123
/*     */     //   #107	-> 142
/*     */     //   #108	-> 157
/*     */     //   #110	-> 176
/*     */     //   #111	-> 188
/*     */     //   #112	-> 194
/*     */     //   #114	-> 201
/*     */     //   #116	-> 208
/*     */     //   #117	-> 215
/*     */     //   #118	-> 222
/*     */     //   #120	-> 229
/*     */     //   #121	-> 242
/*     */     //   #122	-> 247
/*     */     //   #123	-> 258
/*     */     //   #126	-> 259
/*     */     //   #127	-> 267
/*     */     //   #128	-> 278
/*     */     //   #131	-> 279
/*     */     //   #132	-> 287
/*     */     //   #133	-> 298
/*     */     //   #136	-> 299
/*     */     //   #137	-> 312
/*     */     //   #138	-> 328
/*     */     //   #140	-> 331
/*     */     //   #141	-> 338
/*     */     //   #142	-> 343
/*     */     //   #143	-> 354
/*     */     //   #146	-> 355
/*     */     //   #148	-> 400
/*     */     //   #149	-> 431
/*     */     //   #150	-> 448
/*     */     //   #153	-> 458
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   215	113	20	x	I
/*     */     //   222	106	21	y	I
/*     */     //   229	99	22	z	I
/*     */     //   242	86	23	blockType	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/BlockType;
/*     */     //   312	16	24	filler	I
/*     */     //   338	120	20	blockBreakingAllowed	Z
/*     */     //   0	459	0	this	Lcom/hypixel/hytale/server/core/modules/interaction/interaction/config/client/BreakBlockInteraction;
/*     */     //   0	459	1	world	Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   0	459	2	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer;
/*     */     //   0	459	3	type	Lcom/hypixel/hytale/protocol/InteractionType;
/*     */     //   0	459	4	context	Lcom/hypixel/hytale/server/core/entity/InteractionContext;
/*     */     //   0	459	5	heldItemStack	Lcom/hypixel/hytale/server/core/inventory/ItemStack;
/*     */     //   0	459	6	targetBlock	Lcom/hypixel/hytale/math/vector/Vector3i;
/*     */     //   0	459	7	cooldownHandler	Lcom/hypixel/hytale/server/core/modules/interaction/interaction/CooldownHandler;
/*     */     //   7	452	8	ref	Lcom/hypixel/hytale/component/Ref;
/*     */     //   21	438	9	playerComponent	Lcom/hypixel/hytale/server/core/entity/entities/Player;
/*     */     //   63	396	10	chunkStore	Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;
/*     */     //   70	389	11	chunkStoreStore	Lcom/hypixel/hytale/component/Store;
/*     */     //   85	374	12	chunkIndex	J
/*     */     //   94	365	14	chunkReference	Lcom/hypixel/hytale/component/Ref;
/*     */     //   123	336	15	worldChunkComponent	Lcom/hypixel/hytale/server/core/universe/world/chunk/WorldChunk;
/*     */     //   157	302	16	blockChunkComponent	Lcom/hypixel/hytale/server/core/universe/world/chunk/BlockChunk;
/*     */     //   188	271	17	blockSection	Lcom/hypixel/hytale/server/core/universe/world/chunk/section/BlockSection;
/*     */     //   194	265	18	gameplayConfig	Lcom/hypixel/hytale/server/core/asset/type/gameplay/GameplayConfig;
/*     */     //   201	258	19	worldConfig	Lcom/hypixel/hytale/server/core/asset/type/gameplay/WorldConfig;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	459	2	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   7	452	8	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   70	389	11	chunkStoreStore	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */     //   94	365	14	chunkReference	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
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
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   protected Interaction generatePacket() {
/* 163 */     return (Interaction)new com.hypixel.hytale.protocol.BreakBlockInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 168 */     super.configurePacket(packet);
/* 169 */     com.hypixel.hytale.protocol.BreakBlockInteraction p = (com.hypixel.hytale.protocol.BreakBlockInteraction)packet;
/* 170 */     p.harvest = this.harvest;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 176 */     return "BreakBlockInteraction{harvest=" + this.harvest + "} " + super
/*     */       
/* 178 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\BreakBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */