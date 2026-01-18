/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.saving;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabSaveException;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Path;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
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
/*     */ public class PrefabSaver
/*     */ {
/*     */   protected static final String EDITOR_BLOCK = "Editor_Block";
/*     */   protected static final String EDITOR_BLOCK_PREFAB_AIR = "Editor_Empty";
/*     */   protected static final String EDITOR_BLOCK_PREFAB_ANCHOR = "Editor_Anchor";
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<Boolean> savePrefab(@Nonnull CommandSender sender, @Nonnull World world, @Nonnull Path pathToSave, @Nonnull Vector3i anchorPoint, @Nonnull Vector3i minPoint, @Nonnull Vector3i maxPoint, @Nonnull Vector3i pastePosition, @Nonnull Vector3i originalFileAnchor, @Nonnull PrefabSaverSettings settings) {
/*  58 */     return CompletableFuture.supplyAsync(() -> { BlockSelection blockSelection = copyBlocks(sender, world, anchorPoint, minPoint, maxPoint, pastePosition, originalFileAnchor, settings); return (blockSelection == null) ? Boolean.valueOf(false) : Boolean.valueOf(save(sender, blockSelection, pathToSave, settings)); }(Executor)world);
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
/*     */   @Nullable
/*     */   private static BlockSelection copyBlocks(@Nonnull CommandSender sender, @Nonnull World world, @Nonnull Vector3i anchorPoint, @Nonnull Vector3i minPoint, @Nonnull Vector3i maxPoint, @Nonnull Vector3i pastePosition, @Nonnull Vector3i originalFileAnchor, @Nonnull PrefabSaverSettings settings) {
/*  74 */     ChunkStore chunkStore = world.getChunkStore();
/*     */     
/*  76 */     long start = System.nanoTime();
/*     */     
/*  78 */     int width = maxPoint.x - minPoint.x;
/*  79 */     int height = maxPoint.y - minPoint.y;
/*  80 */     int depth = maxPoint.z - minPoint.z;
/*     */     
/*  82 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*     */     
/*  84 */     int editorBlock = assetMap.getIndex("Editor_Block");
/*  85 */     if (editorBlock == Integer.MIN_VALUE) {
/*  86 */       sender.sendMessage(Message.translation("server.commands.editprefab.save.error.unknownBlockIdKey")
/*  87 */           .param("key", "Editor_Block".toString()));
/*  88 */       return null;
/*     */     } 
/*     */     
/*  91 */     int editorBlockPrefabAir = assetMap.getIndex("Editor_Empty");
/*  92 */     if (editorBlockPrefabAir == Integer.MIN_VALUE) {
/*  93 */       sender.sendMessage(Message.translation("server.commands.editprefab.save.error.unknownBlockIdKey")
/*  94 */           .param("key", "Editor_Empty".toString()));
/*  95 */       return null;
/*     */     } 
/*     */     
/*  98 */     int editorBlockPrefabAnchor = assetMap.getIndex("Editor_Anchor");
/*  99 */     if (editorBlockPrefabAnchor == Integer.MIN_VALUE) {
/* 100 */       sender.sendMessage(Message.translation("server.commands.editprefab.save.error.unknownBlockIdKey")
/* 101 */           .param("key", "Editor_Anchor".toString()));
/* 102 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     int newAnchorX = anchorPoint.x - pastePosition.x;
/* 107 */     int newAnchorY = anchorPoint.y - pastePosition.y;
/* 108 */     int newAnchorZ = anchorPoint.z - pastePosition.z;
/*     */     
/* 110 */     BlockSelection selection = new BlockSelection();
/*     */ 
/*     */     
/* 113 */     selection.setPosition(pastePosition.x - originalFileAnchor.x, pastePosition.y - originalFileAnchor.y, pastePosition.z - originalFileAnchor.z);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     selection.setSelectionArea(minPoint, maxPoint);
/*     */     
/* 120 */     selection.setAnchor(newAnchorX, newAnchorY, newAnchorZ);
/*     */     
/* 122 */     int blockCount = 0;
/* 123 */     int fluidCount = 0;
/*     */     
/* 125 */     int top = Math.max(minPoint.y, maxPoint.y);
/* 126 */     int bottom = Math.min(minPoint.y, maxPoint.y);
/*     */ 
/*     */ 
/*     */     
/* 130 */     Long2ObjectMap<Ref<ChunkStore>> loadedChunks = preloadChunksInSelection(world, chunkStore, minPoint, maxPoint);
/*     */     
/* 132 */     for (int x = minPoint.x; x <= maxPoint.x; x++) {
/* 133 */       for (int z = minPoint.z; z <= maxPoint.z; z++) {
/* 134 */         long chunkIndex = ChunkUtil.indexChunkFromBlock(x, z);
/* 135 */         Ref<ChunkStore> chunkRef = (Ref<ChunkStore>)loadedChunks.get(chunkIndex);
/* 136 */         if (chunkRef != null && chunkRef.isValid()) {
/*     */           
/* 138 */           WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getStore().getComponent(chunkRef, WorldChunk.getComponentType());
/* 139 */           assert worldChunkComponent != null;
/*     */           
/* 141 */           ChunkColumn chunkColumnComponent = (ChunkColumn)chunkStore.getStore().getComponent(chunkRef, ChunkColumn.getComponentType());
/* 142 */           assert chunkColumnComponent != null;
/*     */           
/* 144 */           for (int y = top; y >= bottom; y--) {
/* 145 */             int sectionIndex = ChunkUtil.indexSection(y);
/* 146 */             Ref<ChunkStore> sectionRef = chunkColumnComponent.getSection(sectionIndex);
/* 147 */             if (sectionRef != null && sectionRef.isValid()) {
/*     */               
/* 149 */               BlockSection sectionComponent = (BlockSection)chunkStore.getStore().getComponent(sectionRef, BlockSection.getComponentType());
/* 150 */               assert sectionComponent != null;
/*     */               
/* 152 */               BlockPhysics blockPhysicsComponent = (BlockPhysics)chunkStore.getStore().getComponent(sectionRef, BlockPhysics.getComponentType());
/*     */               
/* 154 */               int block = sectionComponent.get(x, y, z);
/* 155 */               if (settings.isBlocks() && (block != 0 || settings.isEmpty()) && block != editorBlock) {
/* 156 */                 if (block == editorBlockPrefabAir) {
/* 157 */                   block = 0;
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/* 162 */                 Holder<ChunkStore> holder = worldChunkComponent.getBlockComponentHolder(x, y, z);
/* 163 */                 if (holder != null) {
/* 164 */                   holder = holder.clone();
/* 165 */                   BlockState blockState = BlockState.getBlockState(holder);
/* 166 */                   if (blockState != null) {
/*     */                     
/* 168 */                     int localX = x - pastePosition.x;
/* 169 */                     int localY = y - pastePosition.y;
/* 170 */                     int localZ = z - pastePosition.z;
/*     */                     
/* 172 */                     Vector3i position = blockState.__internal_getPosition();
/* 173 */                     if (position != null) {
/* 174 */                       position.assign(localX, localY, localZ);
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */                 
/* 179 */                 selection.addBlockAtWorldPos(x, y, z, block, sectionComponent
/*     */                     
/* 181 */                     .getRotationIndex(x, y, z), sectionComponent
/* 182 */                     .getFiller(x, y, z), 
/* 183 */                     (blockPhysicsComponent != null) ? blockPhysicsComponent.get(x, y, z) : 0, holder);
/*     */ 
/*     */                 
/* 186 */                 blockCount++;
/*     */               } 
/*     */               
/* 189 */               FluidSection fluidSectionComponent = (FluidSection)chunkStore.getStore().getComponent(sectionRef, FluidSection.getComponentType());
/* 190 */               assert fluidSectionComponent != null;
/*     */               
/* 192 */               int fluid = fluidSectionComponent.getFluidId(x, y, z);
/* 193 */               if (settings.isBlocks() && (fluid != 0 || settings.isEmpty())) {
/* 194 */                 byte fluidLevel = fluidSectionComponent.getFluidLevel(x, y, z);
/* 195 */                 selection.addFluidAtWorldPos(x, y, z, fluid, fluidLevel);
/* 196 */                 fluidCount++;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 202 */     }  if (settings.isEntities()) {
/* 203 */       Store<EntityStore> store = world.getEntityStore().getStore();
/*     */       
/* 205 */       BuilderToolsPlugin.forEachCopyableInSelection(world, minPoint.x, minPoint.y, minPoint.z, width, height, depth, e -> {
/*     */             Holder<EntityStore> holder = store.copyEntity(e);
/*     */             
/*     */             selection.addEntityFromWorld(holder);
/*     */           });
/*     */     } 
/* 211 */     long end = System.nanoTime();
/* 212 */     long diff = end - start;
/* 213 */     BuilderToolsPlugin.get().getLogger().at(Level.FINE).log("Took: %dns (%dms) to execute copy of %d blocks, %d fluids", Long.valueOf(diff), Long.valueOf(TimeUnit.NANOSECONDS.toMillis(diff)), Integer.valueOf(blockCount), Integer.valueOf(fluidCount));
/* 214 */     return selection;
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
/*     */   @Nonnull
/*     */   private static Long2ObjectMap<Ref<ChunkStore>> preloadChunksInSelection(@Nonnull World world, @Nonnull ChunkStore chunkStore, @Nonnull Vector3i minPoint, @Nonnull Vector3i maxPoint) {
/* 234 */     LongOpenHashSet longOpenHashSet = new LongOpenHashSet();
/* 235 */     int minChunkX = minPoint.x >> 5;
/* 236 */     int maxChunkX = maxPoint.x >> 5;
/* 237 */     int minChunkZ = minPoint.z >> 5;
/* 238 */     int maxChunkZ = maxPoint.z >> 5;
/*     */     
/* 240 */     for (int cx = minChunkX; cx <= maxChunkX; cx++) {
/* 241 */       for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
/* 242 */         longOpenHashSet.add(ChunkUtil.indexChunk(cx, cz));
/*     */       }
/*     */     } 
/*     */     
/* 246 */     Long2ObjectOpenHashMap long2ObjectOpenHashMap = new Long2ObjectOpenHashMap(longOpenHashSet.size());
/*     */ 
/*     */     
/* 249 */     for (LongIterator<Long> longIterator = longOpenHashSet.iterator(); longIterator.hasNext(); ) { long chunkIndex = ((Long)longIterator.next()).longValue();
/* 250 */       CompletableFuture<Ref<ChunkStore>> future = chunkStore.getChunkReferenceAsync(chunkIndex);
/*     */ 
/*     */       
/* 253 */       while (!future.isDone()) {
/* 254 */         world.consumeTaskQueue();
/*     */       }
/*     */       
/* 257 */       Ref<ChunkStore> reference = future.join();
/* 258 */       if (reference != null && reference.isValid()) {
/* 259 */         long2ObjectOpenHashMap.put(chunkIndex, reference);
/*     */       } }
/*     */ 
/*     */     
/* 263 */     return (Long2ObjectMap<Ref<ChunkStore>>)long2ObjectOpenHashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean save(@Nonnull CommandSender sender, @Nonnull BlockSelection copiedSelection, @Nonnull Path saveFilePath, @Nonnull PrefabSaverSettings settings) {
/* 271 */     if (saveFilePath.getFileSystem() != FileSystems.getDefault()) {
/* 272 */       sender.sendMessage(Message.translation("server.builderTools.cannotSaveToReadOnlyPath").param("path", saveFilePath.toString()));
/* 273 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 277 */       long start = System.nanoTime();
/*     */       
/* 279 */       BlockSelection postClone = settings.isRelativize() ? copiedSelection.relativize() : copiedSelection.cloneSelection();
/* 280 */       PrefabStore.get().savePrefab(saveFilePath, postClone, settings.isOverwriteExisting());
/*     */       
/* 282 */       long diff = System.nanoTime() - start;
/* 283 */       BuilderToolsPlugin.get().getLogger().at(Level.FINE).log("Took: %dns (%dms) to execute save of %d blocks", Long.valueOf(diff), Long.valueOf(TimeUnit.NANOSECONDS.toMillis(diff)), Integer.valueOf(copiedSelection.getBlockCount()));
/* 284 */       return true;
/* 285 */     } catch (PrefabSaveException e) {
/* 286 */       switch (e.getType()) {
/*     */         case ERROR:
/* 288 */           ((HytaleLogger.Api)BuilderToolsPlugin.get().getLogger().at(Level.WARNING).withCause((Throwable)e)).log("Exception saving prefab %s", saveFilePath);
/* 289 */           sender.sendMessage(Message.translation("server.builderTools.errorSavingPrefab").param("name", saveFilePath.toString()).param("message", e.getCause().getMessage()));
/*     */           break;
/*     */         case ALREADY_EXISTS:
/* 292 */           BuilderToolsPlugin.get().getLogger().at(Level.WARNING).log("Prefab already exists %s", saveFilePath.toString());
/* 293 */           sender.sendMessage(Message.translation("server.builderTools.prefabAlreadyExists"));
/*     */           break;
/*     */       } 
/* 296 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\saving\PrefabSaver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */