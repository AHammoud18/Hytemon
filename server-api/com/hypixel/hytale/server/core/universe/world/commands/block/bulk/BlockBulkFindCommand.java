/*     */ package com.hypixel.hytale.server.core.universe.world.commands.block.bulk;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.iterator.SpiralIterator;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.IntCoord;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BlockBulkFindCommand extends AbstractWorldCommand {
/*     */   @Nonnull
/*  31 */   private static final Message MESSAGE_COMMANDS_BLOCK_FIND_TIME_OUT = Message.translation("server.commands.block.find.timeout");
/*     */   @Nonnull
/*  33 */   private static final Message MESSAGE_COMMANDS_BLOCK_FIND_DONE = Message.translation("server.commands.block.find.done");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  39 */   private final RequiredArg<IntCoord> chunkXArg = withRequiredArg("chunkX", "", (ArgumentType)ArgTypes.RELATIVE_INT_COORD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  45 */   private final RequiredArg<IntCoord> chunkZArg = withRequiredArg("chunkZ", "", (ArgumentType)ArgTypes.RELATIVE_INT_COORD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  51 */   private final RequiredArg<String> blockTypeArg = withRequiredArg("block", "", (ArgumentType)ArgTypes.BLOCK_TYPE_KEY);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  57 */   private final RequiredArg<Integer> countArg = withRequiredArg("count", "", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  63 */   private final RequiredArg<Integer> timeoutArg = withRequiredArg("timeout", "", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockBulkFindCommand() {
/*  69 */     super("find", "server.commands.find.desc", true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  74 */     CommandSender sender = context.sender();
/*     */     
/*  76 */     IntCoord relChunkX = (IntCoord)this.chunkXArg.get(context);
/*  77 */     IntCoord relChunkZ = (IntCoord)this.chunkZArg.get(context);
/*  78 */     int baseChunkX = 0;
/*  79 */     int baseChunkZ = 0;
/*     */     
/*  81 */     if (context.isPlayer()) {
/*  82 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*  83 */       if (playerRef != null) {
/*  84 */         TransformComponent transformComponent = (TransformComponent)store.getComponent(playerRef, TransformComponent.getComponentType());
/*  85 */         assert transformComponent != null;
/*     */         
/*  87 */         Vector3d playerPos = transformComponent.getPosition();
/*  88 */         baseChunkX = MathUtil.floor(playerPos.getX()) >> 5;
/*  89 */         baseChunkZ = MathUtil.floor(playerPos.getZ()) >> 5;
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     int originChunkX = relChunkX.resolveXZ(baseChunkX);
/*  94 */     int originChunkZ = relChunkZ.resolveXZ(baseChunkZ);
/*     */     
/*  96 */     String blockTypeKey = (String)this.blockTypeArg.get(context);
/*  97 */     int blockId = BlockType.getAssetMap().getIndex(blockTypeKey);
/*  98 */     IntList idAsList = IntLists.singleton(blockId);
/*     */     
/* 100 */     int count = ((Integer)this.countArg.get(context)).intValue();
/* 101 */     int timeout = ((Integer)this.timeoutArg.get(context)).intValue();
/*     */     
/* 103 */     CompletableFuture.runAsync(() -> {
/*     */           long start = System.nanoTime();
/*     */           int tested = 0;
/*     */           int[] found = { 0 };
/*     */           IntOpenHashSet temp = new IntOpenHashSet();
/*     */           ChunkStore chunkComponentStore = world.getChunkStore();
/*     */           SpiralIterator iterator = new SpiralIterator(originChunkX, originChunkZ, SpiralIterator.MAX_RADIUS);
/*     */           label26: while (iterator.hasNext()) {
/*     */             long key = iterator.next();
/*     */             BlockChunk blockChunk = chunkComponentStore.getChunkReferenceAsync(key).thenApplyAsync((), (Executor)world).join();
/*     */             for (int sectionIndex = 0; sectionIndex < 10; sectionIndex++) {
/*     */               BlockSection section = blockChunk.getSectionAtIndex(sectionIndex);
/*     */               if (section.contains(blockId)) {
/*     */                 int chunkX = ChunkUtil.xOfChunkIndex(key);
/*     */                 int chunkY = sectionIndex;
/*     */                 int chunkZ = ChunkUtil.zOfChunkIndex(key);
/*     */                 section.find(idAsList, (IntSet)temp, ());
/*     */                 if (found[0] >= count)
/*     */                   break label26; 
/*     */                 temp.clear();
/*     */               } 
/*     */             } 
/*     */             if (++tested % 100 == 0)
/*     */               sender.sendMessage(Message.translation("server.commands.block.find.chunksTested").param("nb", tested)); 
/*     */             long diff = System.nanoTime() - start;
/*     */             if (diff > TimeUnit.SECONDS.toNanos(timeout)) {
/*     */               sender.sendMessage(MESSAGE_COMMANDS_BLOCK_FIND_TIME_OUT);
/*     */               return;
/*     */             } 
/*     */           } 
/*     */           sender.sendMessage(MESSAGE_COMMANDS_BLOCK_FIND_DONE);
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\bulk\BlockBulkFindCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */