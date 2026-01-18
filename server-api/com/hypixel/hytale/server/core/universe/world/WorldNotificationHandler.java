/*     */ package com.hypixel.hytale.server.core.universe.world;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.BlockParticleEvent;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.packets.world.SpawnBlockParticleSystem;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateBlockDamage;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import java.util.function.Predicate;
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
/*     */ public class WorldNotificationHandler
/*     */ {
/*     */   @Nonnull
/*     */   private final World world;
/*     */   
/*     */   public WorldNotificationHandler(@Nonnull World world) {
/*  39 */     this.world = world;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateState(int x, int y, int z, BlockState state, BlockState oldState) {
/*  44 */     updateState(x, y, z, state, oldState, null);
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
/*     */   public void updateState(int x, int y, int z, BlockState state, BlockState oldState, @Nullable Predicate<PlayerRef> skip) {
/*     */     // Byte code:
/*     */     //   0: iload_2
/*     */     //   1: iflt -> 11
/*     */     //   4: iload_2
/*     */     //   5: sipush #320
/*     */     //   8: if_icmplt -> 27
/*     */     //   11: new java/lang/IllegalArgumentException
/*     */     //   14: dup
/*     */     //   15: iload_1
/*     */     //   16: iload_2
/*     */     //   17: iload_3
/*     */     //   18: <illegal opcode> makeConcatWithConstants : (III)Ljava/lang/String;
/*     */     //   23: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   26: athrow
/*     */     //   27: aload #5
/*     */     //   29: instanceof com/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState
/*     */     //   32: ifeq -> 80
/*     */     //   35: aload #5
/*     */     //   37: checkcast com/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState
/*     */     //   40: astore #9
/*     */     //   42: aload #4
/*     */     //   44: aload #5
/*     */     //   46: if_acmpeq -> 80
/*     */     //   49: aload #9
/*     */     //   51: dup
/*     */     //   52: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   55: pop
/*     */     //   56: <illegal opcode> accept : (Lcom/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState;)Ljava/util/function/Consumer;
/*     */     //   61: astore #7
/*     */     //   63: aload #9
/*     */     //   65: dup
/*     */     //   66: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   69: pop
/*     */     //   70: <illegal opcode> test : (Lcom/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState;)Ljava/util/function/Predicate;
/*     */     //   75: astore #8
/*     */     //   77: goto -> 86
/*     */     //   80: aconst_null
/*     */     //   81: astore #7
/*     */     //   83: aconst_null
/*     */     //   84: astore #8
/*     */     //   86: aload #4
/*     */     //   88: instanceof com/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState
/*     */     //   91: ifeq -> 132
/*     */     //   94: aload #4
/*     */     //   96: checkcast com/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState
/*     */     //   99: astore #11
/*     */     //   101: aload #11
/*     */     //   103: dup
/*     */     //   104: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   107: pop
/*     */     //   108: <illegal opcode> accept : (Lcom/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState;)Ljava/util/function/Consumer;
/*     */     //   113: astore #9
/*     */     //   115: aload #11
/*     */     //   117: dup
/*     */     //   118: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   121: pop
/*     */     //   122: <illegal opcode> test : (Lcom/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState;)Ljava/util/function/Predicate;
/*     */     //   127: astore #10
/*     */     //   129: goto -> 138
/*     */     //   132: aconst_null
/*     */     //   133: astore #9
/*     */     //   135: aconst_null
/*     */     //   136: astore #10
/*     */     //   138: aload #7
/*     */     //   140: ifnonnull -> 148
/*     */     //   143: aload #9
/*     */     //   145: ifnull -> 346
/*     */     //   148: iload_1
/*     */     //   149: iload_3
/*     */     //   150: invokestatic indexChunkFromBlock : (II)J
/*     */     //   153: lstore #11
/*     */     //   155: new it/unimi/dsi/fastutil/objects/ObjectArrayList
/*     */     //   158: dup
/*     */     //   159: invokespecial <init> : ()V
/*     */     //   162: astore #13
/*     */     //   164: aload_0
/*     */     //   165: getfield world : Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   168: invokevirtual getPlayerRefs : ()Ljava/util/Collection;
/*     */     //   171: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   176: astore #14
/*     */     //   178: aload #14
/*     */     //   180: invokeinterface hasNext : ()Z
/*     */     //   185: ifeq -> 346
/*     */     //   188: aload #14
/*     */     //   190: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   195: checkcast com/hypixel/hytale/server/core/universe/PlayerRef
/*     */     //   198: astore #15
/*     */     //   200: aload #15
/*     */     //   202: invokevirtual getChunkTracker : ()Lcom/hypixel/hytale/server/core/modules/entity/player/ChunkTracker;
/*     */     //   205: astore #16
/*     */     //   207: aload #16
/*     */     //   209: lload #11
/*     */     //   211: invokevirtual isLoaded : (J)Z
/*     */     //   214: ifne -> 220
/*     */     //   217: goto -> 178
/*     */     //   220: aload #6
/*     */     //   222: ifnull -> 240
/*     */     //   225: aload #6
/*     */     //   227: aload #15
/*     */     //   229: invokeinterface test : (Ljava/lang/Object;)Z
/*     */     //   234: ifeq -> 240
/*     */     //   237: goto -> 178
/*     */     //   240: aload #7
/*     */     //   242: ifnull -> 266
/*     */     //   245: aload #8
/*     */     //   247: aload #15
/*     */     //   249: invokeinterface test : (Ljava/lang/Object;)Z
/*     */     //   254: ifeq -> 266
/*     */     //   257: aload #7
/*     */     //   259: aload #13
/*     */     //   261: invokeinterface accept : (Ljava/lang/Object;)V
/*     */     //   266: aload #9
/*     */     //   268: ifnull -> 292
/*     */     //   271: aload #10
/*     */     //   273: aload #15
/*     */     //   275: invokeinterface test : (Ljava/lang/Object;)Z
/*     */     //   280: ifeq -> 292
/*     */     //   283: aload #9
/*     */     //   285: aload #13
/*     */     //   287: invokeinterface accept : (Ljava/lang/Object;)V
/*     */     //   292: aload #13
/*     */     //   294: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   299: astore #17
/*     */     //   301: aload #17
/*     */     //   303: invokeinterface hasNext : ()Z
/*     */     //   308: ifeq -> 336
/*     */     //   311: aload #17
/*     */     //   313: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   318: checkcast com/hypixel/hytale/protocol/Packet
/*     */     //   321: astore #18
/*     */     //   323: aload #15
/*     */     //   325: invokevirtual getPacketHandler : ()Lcom/hypixel/hytale/server/core/io/PacketHandler;
/*     */     //   328: aload #18
/*     */     //   330: invokevirtual write : (Lcom/hypixel/hytale/protocol/Packet;)V
/*     */     //   333: goto -> 301
/*     */     //   336: aload #13
/*     */     //   338: invokeinterface clear : ()V
/*     */     //   343: goto -> 178
/*     */     //   346: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #49	-> 0
/*     */     //   #53	-> 27
/*     */     //   #54	-> 49
/*     */     //   #55	-> 63
/*     */     //   #57	-> 80
/*     */     //   #58	-> 83
/*     */     //   #63	-> 86
/*     */     //   #64	-> 101
/*     */     //   #65	-> 115
/*     */     //   #67	-> 132
/*     */     //   #68	-> 135
/*     */     //   #71	-> 138
/*     */     //   #72	-> 148
/*     */     //   #73	-> 155
/*     */     //   #74	-> 164
/*     */     //   #75	-> 200
/*     */     //   #77	-> 207
/*     */     //   #78	-> 220
/*     */     //   #81	-> 240
/*     */     //   #82	-> 257
/*     */     //   #85	-> 266
/*     */     //   #86	-> 283
/*     */     //   #90	-> 292
/*     */     //   #91	-> 323
/*     */     //   #92	-> 333
/*     */     //   #93	-> 336
/*     */     //   #94	-> 343
/*     */     //   #96	-> 346
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   63	17	7	removeOldState	Ljava/util/function/Consumer;
/*     */     //   77	3	8	canPlayerSeeOld	Ljava/util/function/Predicate;
/*     */     //   42	38	9	sendableBlockState	Lcom/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState;
/*     */     //   115	17	9	updateBlockState	Ljava/util/function/Consumer;
/*     */     //   129	3	10	canPlayerSee	Ljava/util/function/Predicate;
/*     */     //   101	31	11	sendableBlockState	Lcom/hypixel/hytale/server/core/universe/world/meta/state/SendableBlockState;
/*     */     //   323	10	18	packet	Lcom/hypixel/hytale/protocol/Packet;
/*     */     //   207	136	16	chunkTracker	Lcom/hypixel/hytale/server/core/modules/entity/player/ChunkTracker;
/*     */     //   200	143	15	playerRef	Lcom/hypixel/hytale/server/core/universe/PlayerRef;
/*     */     //   155	191	11	indexChunk	J
/*     */     //   164	182	13	packets	Ljava/util/List;
/*     */     //   0	347	0	this	Lcom/hypixel/hytale/server/core/universe/world/WorldNotificationHandler;
/*     */     //   0	347	1	x	I
/*     */     //   0	347	2	y	I
/*     */     //   0	347	3	z	I
/*     */     //   0	347	4	state	Lcom/hypixel/hytale/server/core/universe/world/meta/BlockState;
/*     */     //   0	347	5	oldState	Lcom/hypixel/hytale/server/core/universe/world/meta/BlockState;
/*     */     //   0	347	6	skip	Ljava/util/function/Predicate;
/*     */     //   83	264	7	removeOldState	Ljava/util/function/Consumer;
/*     */     //   86	261	8	canPlayerSeeOld	Ljava/util/function/Predicate;
/*     */     //   135	212	9	updateBlockState	Ljava/util/function/Consumer;
/*     */     //   138	209	10	canPlayerSee	Ljava/util/function/Predicate;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   63	17	7	removeOldState	Ljava/util/function/Consumer<Ljava/util/List<Lcom/hypixel/hytale/protocol/Packet;>;>;
/*     */     //   77	3	8	canPlayerSeeOld	Ljava/util/function/Predicate<Lcom/hypixel/hytale/server/core/universe/PlayerRef;>;
/*     */     //   115	17	9	updateBlockState	Ljava/util/function/Consumer<Ljava/util/List<Lcom/hypixel/hytale/protocol/Packet;>;>;
/*     */     //   129	3	10	canPlayerSee	Ljava/util/function/Predicate<Lcom/hypixel/hytale/server/core/universe/PlayerRef;>;
/*     */     //   164	182	13	packets	Ljava/util/List<Lcom/hypixel/hytale/protocol/Packet;>;
/*     */     //   0	347	6	skip	Ljava/util/function/Predicate<Lcom/hypixel/hytale/server/core/universe/PlayerRef;>;
/*     */     //   83	264	7	removeOldState	Ljava/util/function/Consumer<Ljava/util/List<Lcom/hypixel/hytale/protocol/Packet;>;>;
/*     */     //   86	261	8	canPlayerSeeOld	Ljava/util/function/Predicate<Lcom/hypixel/hytale/server/core/universe/PlayerRef;>;
/*     */     //   135	212	9	updateBlockState	Ljava/util/function/Consumer<Ljava/util/List<Lcom/hypixel/hytale/protocol/Packet;>;>;
/*     */     //   138	209	10	canPlayerSee	Ljava/util/function/Predicate<Lcom/hypixel/hytale/server/core/universe/PlayerRef;>;
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
/*     */   public void updateChunk(long indexChunk) {
/*  99 */     for (PlayerRef playerRef : this.world.getPlayerRefs()) {
/* 100 */       playerRef.getChunkTracker().removeForReload(indexChunk);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendBlockParticle(double x, double y, double z, int id, @Nonnull BlockParticleEvent particleType) {
/* 105 */     sendPacketIfChunkLoaded((Packet)getBlockParticlePacket(x, y, z, id, particleType), MathUtil.floor(x), MathUtil.floor(z));
/*     */   }
/*     */   
/*     */   public void sendBlockParticle(@Nonnull PlayerRef playerRef, double x, double y, double z, int id, @Nonnull BlockParticleEvent particleType) {
/* 109 */     sendPacketIfChunkLoaded(playerRef, (Packet)getBlockParticlePacket(x, y, z, id, particleType), MathUtil.floor(x), MathUtil.floor(z));
/*     */   }
/*     */   
/*     */   public void updateBlockDamage(int x, int y, int z, float health, float healthDelta) {
/* 113 */     sendPacketIfChunkLoaded((Packet)getBlockDamagePacket(x, y, z, health, healthDelta), x, z);
/*     */   }
/*     */   
/*     */   public void updateBlockDamage(int x, int y, int z, float health, float healthDelta, @Nullable Predicate<PlayerRef> filter) {
/* 117 */     sendPacketIfChunkLoaded((Packet)getBlockDamagePacket(x, y, z, health, healthDelta), x, z, filter);
/*     */   }
/*     */   
/*     */   public void sendPacketIfChunkLoaded(@Nonnull Packet packet, int x, int z) {
/* 121 */     long indexChunk = ChunkUtil.indexChunkFromBlock(x, z);
/* 122 */     sendPacketIfChunkLoaded(packet, indexChunk);
/*     */   }
/*     */   
/*     */   public void sendPacketIfChunkLoaded(@Nonnull Packet packet, long indexChunk) {
/* 126 */     for (PlayerRef playerRef : this.world.getPlayerRefs()) {
/* 127 */       if (playerRef.getChunkTracker().isLoaded(indexChunk)) {
/* 128 */         playerRef.getPacketHandler().write(packet);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendPacketIfChunkLoaded(@Nonnull Packet packet, int x, int z, @Nullable Predicate<PlayerRef> filter) {
/* 134 */     long indexChunk = ChunkUtil.indexChunkFromBlock(x, z);
/* 135 */     sendPacketIfChunkLoaded(packet, indexChunk, filter);
/*     */   }
/*     */   
/*     */   public void sendPacketIfChunkLoaded(@Nonnull Packet packet, long indexChunk, @Nullable Predicate<PlayerRef> filter) {
/* 139 */     for (PlayerRef playerRef : this.world.getPlayerRefs()) {
/* 140 */       if (filter != null && !filter.test(playerRef))
/*     */         continue; 
/* 142 */       if (playerRef.getChunkTracker().isLoaded(indexChunk)) {
/* 143 */         playerRef.getPacketHandler().write(packet);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendPacketIfChunkLoaded(@Nonnull PlayerRef player, @Nonnull Packet packet, int x, int z) {
/* 149 */     long indexChunk = ChunkUtil.indexChunkFromBlock(x, z);
/* 150 */     sendPacketIfChunkLoaded(player, packet, indexChunk);
/*     */   }
/*     */   
/*     */   private void sendPacketIfChunkLoaded(@Nonnull PlayerRef playerRef, @Nonnull Packet packet, long indexChunk) {
/* 154 */     if (playerRef.getChunkTracker().isLoaded(indexChunk)) {
/* 155 */       playerRef.getPacketHandler().write(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public SpawnBlockParticleSystem getBlockParticlePacket(double x, double y, double z, int id, @Nonnull BlockParticleEvent particleType) {
/* 161 */     if (y < 0.0D || y >= 320.0D) throw new IllegalArgumentException("Y value is outside the world! " + x + ", " + y + ", " + z); 
/* 162 */     return new SpawnBlockParticleSystem(id, particleType, new Position(x, y, z));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UpdateBlockDamage getBlockDamagePacket(int x, int y, int z, float health, float healthDelta) {
/* 167 */     if (y < 0 || y >= 320) throw new IllegalArgumentException("Y value is outside the world! " + x + ", " + y + ", " + z); 
/* 168 */     return new UpdateBlockDamage(new BlockPosition(x, y, z), health, healthDelta);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\WorldNotificationHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */