/*     */ package com.hypixel.hytale.server.core.universe.world.commands.block.bulk;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.iterator.SpiralIterator;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.VariantRotation;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BlockBulkReplaceCommand
/*     */   extends AbstractPlayerCommand {
/*     */   @Nonnull
/*  40 */   private final RequiredArg<String> findArg = withRequiredArg("find", "", (ArgumentType)ArgTypes.BLOCK_TYPE_KEY);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   private final RequiredArg<String> replaceArg = withRequiredArg("replaceWith", "", (ArgumentType)ArgTypes.BLOCK_TYPE_KEY);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  52 */   private final RequiredArg<Integer> radiusArg = withRequiredArg("radius", "", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockBulkReplaceCommand() {
/*  58 */     super("replace", "");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  63 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  64 */     assert transformComponent != null;
/*     */     
/*  66 */     String findBlockTypeKey = (String)this.findArg.get(context);
/*  67 */     String replaceBlockTypeKey = (String)this.replaceArg.get(context);
/*     */     
/*  69 */     int findBlockId = BlockType.getAssetMap().getIndex(findBlockTypeKey);
/*  70 */     int replaceBlockId = BlockType.getAssetMap().getIndex(replaceBlockTypeKey);
/*     */     
/*  72 */     IntList findBlockIdList = getBlockIdList(findBlockId);
/*  73 */     IntList replaceBlockIdList = getBlockIdList(replaceBlockId);
/*  74 */     int radius = ((Integer)this.radiusArg.get(context)).intValue();
/*     */     
/*  76 */     Vector3d playerPos = transformComponent.getPosition();
/*  77 */     int originChunkX = MathUtil.floor(playerPos.getX()) >> 5;
/*  78 */     int originChunkZ = MathUtil.floor(playerPos.getZ()) >> 5;
/*     */     
/*  80 */     CompletableFuture.runAsync(() -> {
/*     */           long start = System.nanoTime();
/*     */           IntOpenHashSet temp = new IntOpenHashSet();
/*     */           ChunkStore chunkComponentStore = world.getChunkStore();
/*     */           AtomicInteger replaced = new AtomicInteger();
/*     */           SpiralIterator iterator = new SpiralIterator(originChunkX, originChunkZ, radius);
/*     */           while (iterator.hasNext()) {
/*     */             long key = iterator.next();
/*     */             BlockChunk blockChunk = chunkComponentStore.getChunkReferenceAsync(key).thenApplyAsync((), (Executor)world).join();
/*     */             for (int sectionIndex = 0; sectionIndex < 10; sectionIndex++) {
/*     */               BlockSection section = blockChunk.getSectionAtIndex(sectionIndex);
/*     */               if (section.containsAny(findBlockIdList)) {
/*     */                 int chunkX = ChunkUtil.xOfChunkIndex(key);
/*     */                 int chunkY = sectionIndex;
/*     */                 int chunkZ = ChunkUtil.zOfChunkIndex(key);
/*     */                 section.find(findBlockIdList, (IntSet)temp, ());
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           long diff = System.nanoTime() - start;
/*     */           playerRef.sendMessage(Message.translation("Found and replaced " + replaced.get() + " blocks in " + TimeUnit.NANOSECONDS.toSeconds(diff) + " seconds!"));
/*     */         });
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
/*     */   @Nonnull
/*     */   protected static IntList getBlockIdList(int blockId) {
/*     */     IntList intList;
/* 124 */     IntArrayList intArrayList = new IntArrayList();
/*     */     
/* 126 */     BlockType findBlock = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/* 127 */     if (findBlock.getVariantRotation().equals(VariantRotation.NESW)) {
/* 128 */       intList = createNESWRotationLists(findBlock, (IntList)intArrayList);
/*     */     } else {
/* 130 */       intList.add(blockId);
/*     */     } 
/* 132 */     return intList;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static IntList createNESWRotationLists(@Nonnull BlockType block, @Nonnull IntList blockIdList) {
/* 137 */     RotationTuple[] rotations = block.getVariantRotation().getRotations();
/* 138 */     String blockName = block.getId();
/*     */     
/* 140 */     blockIdList.add(BlockType.getAssetMap().getIndex(blockName));
/* 141 */     for (RotationTuple rp : rotations) {
/* 142 */       String newBlockRotation = blockName + "|Yaw=" + blockName;
/* 143 */       blockIdList.add(BlockType.getAssetMap().getIndex(newBlockRotation));
/*     */     } 
/* 145 */     return blockIdList;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\bulk\BlockBulkReplaceCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */