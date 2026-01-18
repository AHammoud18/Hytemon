/*     */ package com.hypixel.hytale.server.core.universe.world.commands.block.bulk;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.iterator.SpiralIterator;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
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
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class BlockBulkFindHereCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  38 */   private final FlagArg printNameArg = withFlagArg("print", "");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  44 */   private final RequiredArg<String> blockTypeArg = withRequiredArg("block", "", (ArgumentType)ArgTypes.BLOCK_TYPE_KEY);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  50 */   private final DefaultArg<Integer> radiusArg = withDefaultArg("radius", "", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(3), "");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockBulkFindHereCommand() {
/*  56 */     super("find-here", "server.commands.block.find-here.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  61 */     String blockTypeKey = (String)this.blockTypeArg.get(context);
/*  62 */     int blockId = BlockType.getAssetMap().getIndex(blockTypeKey);
/*  63 */     IntList blockIdList = BlockBulkReplaceCommand.getBlockIdList(blockId);
/*     */     
/*  65 */     int radius = ((Integer)this.radiusArg.get(context)).intValue();
/*  66 */     boolean printBlockName = ((Boolean)this.printNameArg.get(context)).booleanValue();
/*     */     
/*  68 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  69 */     assert transformComponent != null;
/*     */     
/*  71 */     Vector3d playerPos = transformComponent.getPosition();
/*  72 */     int originChunkX = MathUtil.floor(playerPos.getX()) >> 5;
/*  73 */     int originChunkZ = MathUtil.floor(playerPos.getZ()) >> 5;
/*     */     
/*  75 */     CompletableFuture.runAsync(() -> {
/*     */           long start = System.nanoTime();
/*     */           IntOpenHashSet temp = new IntOpenHashSet();
/*     */           ChunkStore chunkComponentStore = world.getChunkStore();
/*     */           AtomicInteger found = new AtomicInteger();
/*     */           SpiralIterator iterator = new SpiralIterator(originChunkX, originChunkZ, radius);
/*     */           while (iterator.hasNext()) {
/*     */             long key = iterator.next();
/*     */             BlockChunk blockChunk = chunkComponentStore.getChunkReferenceAsync(key).thenApplyAsync((), (Executor)world).join();
/*     */             for (int sectionIndex = 0; sectionIndex < 10; sectionIndex++) {
/*     */               BlockSection section = blockChunk.getSectionAtIndex(sectionIndex);
/*     */               if (section.containsAny(blockIdList)) {
/*     */                 section.find(blockIdList, (IntSet)temp, ());
/*     */                 temp.clear();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           long diff = System.nanoTime() - start;
/*     */           BlockType findBlock = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*     */           String blockName = printBlockName ? (" " + findBlock.getId()) : "";
/*     */           playerRef.sendMessage(Message.translation("Found " + found.get() + blockName + " blocks in " + TimeUnit.NANOSECONDS.toSeconds(diff) + " seconds!"));
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\bulk\BlockBulkFindHereCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */