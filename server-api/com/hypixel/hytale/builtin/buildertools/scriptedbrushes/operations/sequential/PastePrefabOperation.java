/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigEditStore;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.PrefabListAsset;
/*     */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabWeights;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferCall;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferUtil;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.LocalCachedChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Random;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PastePrefabOperation extends SequenceBrushOperation {
/*     */   public static final BuilderCodec<PastePrefabOperation> CODEC;
/*     */   
/*     */   static {
/*  44 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PastePrefabOperation.class, PastePrefabOperation::new).append(new KeyedCodec("PrefabListAssetName", (Codec)Codec.STRING), (op, val) -> op.prefabListAssetId = val, op -> op.prefabListAssetId).documentation("The name of a PrefabList asset").add()).documentation("Paste a prefab at the origin+offset point")).build();
/*     */   } @Nullable
/*  46 */   public String prefabListAssetId = null;
/*     */   
/*     */   private boolean hasBeenPlacedAlready = false;
/*     */ 
/*     */   
/*     */   public PastePrefabOperation() {
/*  52 */     super("Paste Prefab", "Paste a prefab at the origin+offset point", true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetInternalState() {
/*  57 */     this.hasBeenPlacedAlready = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  62 */     this.hasBeenPlacedAlready = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifyBlocks(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, BrushConfigCommandExecutor brushConfigCommandExecutor, BrushConfigEditStore edit, int x, int y, int z, ComponentAccessor<EntityStore> componentAccessor) {
/*  67 */     if (this.hasBeenPlacedAlready) return false;
/*     */     
/*  69 */     PrefabListAsset prefabListAsset = (this.prefabListAssetId != null) ? (PrefabListAsset)PrefabListAsset.getAssetMap().getAsset(this.prefabListAssetId) : null;
/*  70 */     if (prefabListAsset == null) {
/*  71 */       brushConfig.setErrorFlag("PrefabList asset not found: " + this.prefabListAssetId);
/*  72 */       return false;
/*     */     } 
/*     */     
/*  75 */     Path prefabPath = prefabListAsset.getRandomPrefab();
/*  76 */     if (prefabPath == null) {
/*  77 */       brushConfig.setErrorFlag("No prefab found in prefab list. Please double check your PrefabList asset.");
/*  78 */       return false;
/*     */     } 
/*     */     
/*  81 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  82 */     PrefabBuffer.PrefabBufferAccessor accessor = PrefabBufferUtil.loadBuffer(prefabPath).newAccess();
/*     */     
/*  84 */     this.hasBeenPlacedAlready = true;
/*     */     
/*  86 */     double xLength = (accessor.getMaxX() - accessor.getMinX());
/*  87 */     double zLength = (accessor.getMaxZ() - accessor.getMinZ());
/*  88 */     int prefabRadius = (int)MathUtil.fastFloor(0.5D * Math.sqrt(xLength * xLength + zLength * zLength));
/*     */     
/*  90 */     LocalCachedChunkAccessor chunkAccessor = LocalCachedChunkAccessor.atWorldCoords((ChunkAccessor)world, x, z, prefabRadius);
/*  91 */     BlockTypeAssetMap<String, BlockType> blockTypeMap = BlockType.getAssetMap();
/*     */     
/*  93 */     accessor.forEach(IPrefabBuffer.iterateAllColumns(), (xi, yi, zi, blockId, holder, supportValue, rotation, filler, call, fluidId, fluidLevel) -> { int bx = x + xi; int by = y + yi; int bz = z + zi; WorldChunk chunk = chunkAccessor.getNonTickingChunk(ChunkUtil.indexChunkFromBlock(bx, bz)); Store<ChunkStore> store = chunk.getWorld().getChunkStore().getStore(); ChunkColumn column = (ChunkColumn)store.getComponent(chunk.getReference(), ChunkColumn.getComponentType()); Ref<ChunkStore> section = column.getSection(ChunkUtil.chunkCoordinate(by)); FluidSection fluidSection = (FluidSection)store.ensureAndGetComponent(section, FluidSection.getComponentType()); fluidSection.setFluid(bx, by, bz, fluidId, (byte)fluidLevel); BlockType block = (BlockType)blockTypeMap.getAsset(blockId); String blockKey = block.getId(); if (filler != 0) return;  RotationTuple rot = RotationTuple.get(rotation); chunk.placeBlock(bx, by, bz, blockKey, rot.yaw(), rot.pitch(), rot.roll(), 0); if (supportValue != 0) { Ref<ChunkStore> chunkRef = chunk.getReference(); store = chunkRef.getStore(); column = (ChunkColumn)store.getComponent(chunkRef, ChunkColumn.getComponentType()); BlockPhysics.setSupportValue(store, column.getSection(ChunkUtil.chunkCoordinate(by)), bx, by, bz, supportValue); }  if (holder != null) chunk.setState(bx, by, bz, holder.clone());  }(xi, zi, entityWrappers, t) -> {  }(xi, yi, zi, path, fitHeightmap, inheritSeed, inheritHeightCondition, weights, rotation, t) -> {  }new PrefabBufferCall(new Random(), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 127 */           PrefabRotation.fromRotation(Rotation.None)));
/*     */ 
/*     */     
/* 130 */     accessor.release();
/*     */     
/* 132 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\PastePrefabOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */