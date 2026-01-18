/*     */ package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.modules.prefabspawner.PrefabSpawnerState;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabWeights;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockStateModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.io.ByteBufUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/* 328 */   private final ByteBuf buf = Unpooled.buffer(); @Nonnull
/* 329 */   private final Vector3i min = new Vector3i(Vector3i.MAX); @Nonnull
/* 330 */   private final Vector3i max = new Vector3i(Vector3i.MIN); @Nonnull
/* 331 */   private final Int2ObjectMap<PrefabBufferColumn> columns = (Int2ObjectMap<PrefabBufferColumn>)new Int2ObjectOpenHashMap(); @Nonnull
/* 332 */   private final List<PrefabBuffer.ChildPrefab> childPrefabs = (List<PrefabBuffer.ChildPrefab>)new ObjectArrayList(0);
/* 333 */   private Vector3i anchor = Vector3i.ZERO;
/*     */ 
/*     */   
/*     */   public void setAnchor(@Nonnull Vector3i anchor) {
/* 337 */     this.anchor = anchor;
/*     */   }
/*     */   
/*     */   public void addColumn(int x, int z, @Nonnull PrefabBufferBlockEntry[] entries, @Nullable Holder<EntityStore>[] entityHolders) {
/* 341 */     if (x < -32768) throw new IllegalArgumentException("x is smaller than -32768. Given: " + x); 
/* 342 */     if (x > 32767) throw new IllegalArgumentException("x is larger than 32767. Given: " + x); 
/* 343 */     if (z < -32768) throw new IllegalArgumentException("z is smaller than -32768. Given: " + z); 
/* 344 */     if (z > 32767) throw new IllegalArgumentException("z is larger than 32767. Given: " + z);
/*     */     
/* 346 */     int columnIndex = MathUtil.packInt((short)x, (short)z);
/* 347 */     if (this.columns.containsKey(columnIndex)) {
/* 348 */       throw new IllegalStateException("Column is already set! Given: " + x + ", " + z);
/*     */     }
/* 350 */     int blockCount = entries.length;
/* 351 */     Int2ObjectOpenHashMap<Holder<ChunkStore>> holderMap = new Int2ObjectOpenHashMap();
/*     */ 
/*     */     
/* 354 */     if (blockCount == 0 && (entityHolders == null || entityHolders.length == 0))
/*     */       return; 
/* 356 */     int readerIndex = this.buf.writerIndex();
/* 357 */     this.buf.writeInt(blockCount);
/* 358 */     if (blockCount > 0) {
/*     */       
/* 360 */       int offset = (entries[0]).y;
/* 361 */       if (offset < this.min.y) this.min.y = offset; 
/* 362 */       this.buf.writeInt(offset - 1);
/*     */ 
/*     */       
/* 365 */       int lastY = Integer.MIN_VALUE;
/* 366 */       for (int i = 0; i < blockCount; i++) {
/* 367 */         PrefabBufferBlockEntry entry = entries[i];
/* 368 */         int y = entry.y;
/* 369 */         int blockId = entry.blockId;
/* 370 */         float chance = entry.chance;
/* 371 */         Holder<ChunkStore> holder = entry.state;
/*     */         
/* 373 */         int fluidId = entry.fluidId;
/* 374 */         byte fluidLevel = entry.fluidLevel;
/*     */         
/* 376 */         if (y <= lastY) {
/* 377 */           throw new IllegalArgumentException("Y Values are not sequential. " + lastY + " -> " + y);
/*     */         }
/* 379 */         int j = (i == 0) ? 0 : (y - lastY);
/* 380 */         if (j > 65535) {
/* 381 */           throw new IllegalArgumentException("Offset is larger than 65535. Given: " + j);
/*     */         }
/* 383 */         boolean hasChance = (chance < 1.0F);
/* 384 */         int blockBytes = MathUtil.byteCount(blockId);
/* 385 */         int offsetBytes = (j == 1) ? 0 : MathUtil.byteCount(j);
/* 386 */         int fluidBytes = MathUtil.byteCount(fluidId);
/* 387 */         int mask = PrefabBuffer.BlockMaskConstants.getBlockMask(blockBytes, fluidBytes, hasChance, offsetBytes, holder, entry.supportValue, entry.rotation, entry.filler);
/*     */ 
/*     */         
/* 390 */         this.buf.writeShort(mask);
/* 391 */         ByteBufUtil.writeNumber(this.buf, blockBytes, blockId);
/* 392 */         ByteBufUtil.writeNumber(this.buf, offsetBytes, j);
/* 393 */         if (hasChance) this.buf.writeFloat(chance);
/*     */         
/* 395 */         if (entry.rotation != 0) {
/* 396 */           this.buf.writeByte(entry.rotation);
/*     */         }
/*     */         
/* 399 */         if (entry.filler != 0) {
/* 400 */           this.buf.writeShort(entry.filler);
/*     */         }
/*     */         
/* 403 */         if (fluidId != 0) {
/* 404 */           ByteBufUtil.writeNumber(this.buf, fluidBytes, fluidId);
/* 405 */           this.buf.writeByte(fluidLevel);
/*     */         } 
/*     */ 
/*     */         
/* 409 */         if (holder != null) {
/* 410 */           holderMap.put(y, holder);
/* 411 */           handleBlockComponents(entry.rotation, x, y, z, holder);
/*     */         } 
/*     */         
/* 414 */         lastY = y;
/*     */       } 
/*     */       
/* 417 */       if (lastY > this.max.y) this.max.y = lastY;
/*     */     
/*     */     } 
/* 420 */     if (x < this.min.x) this.min.x = x; 
/* 421 */     if (x > this.max.x) this.max.x = x; 
/* 422 */     if (z < this.min.z) this.min.z = z; 
/* 423 */     if (z > this.max.z) this.max.z = z;
/*     */     
/* 425 */     if (holderMap.isEmpty()) holderMap = null;
/*     */     
/* 427 */     PrefabBufferColumn column = new PrefabBufferColumn(readerIndex, entityHolders, (Int2ObjectMap<Holder<ChunkStore>>)holderMap);
/* 428 */     this.columns.put(columnIndex, column);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleBlockComponents(int blockRotation, int x, int y, int z, @Nonnull Holder<ChunkStore> holder) {
/* 434 */     ComponentType<ChunkStore, PrefabSpawnerState> componentType = BlockStateModule.get().getComponentType(PrefabSpawnerState.class);
/* 435 */     PrefabSpawnerState spawnerState = (PrefabSpawnerState)holder.getComponent(componentType);
/* 436 */     if (spawnerState == null)
/*     */       return; 
/* 438 */     String path = spawnerState.getPrefabPath();
/* 439 */     if (path == null) {
/* 440 */       HytaleLogger.getLogger().at(Level.WARNING).log("Prefab spawner at %d, %d, %d is missing prefab path!", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
/*     */       
/*     */       return;
/*     */     } 
/* 444 */     PrefabWeights weights = spawnerState.getPrefabWeights();
/*     */     
/* 446 */     PrefabRotation rotation = PrefabRotation.fromRotation(RotationTuple.get(blockRotation).yaw());
/* 447 */     addChildPrefab(x, y, z, path, spawnerState.isFitHeightmap(), spawnerState.isInheritSeed(), spawnerState.isInheritHeightCondition(), weights, rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChildPrefab(int x, int y, int z, @Nonnull String path, boolean fitHeightmap, boolean inheritSeed, boolean inheritHeightCondition, @Nullable PrefabWeights weights, @Nonnull PrefabRotation rotation) {
/* 456 */     this.childPrefabs.add(new PrefabBuffer.ChildPrefab(x, y, z, path, fitHeightmap, inheritSeed, inheritHeightCondition, weights, rotation));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabBufferBlockEntry newBlockEntry(int y) {
/* 461 */     return new PrefabBufferBlockEntry(y);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabBuffer build() {
/* 466 */     ByteBuf buffer = Unpooled.copiedBuffer(this.buf);
/* 467 */     this.buf.release();
/* 468 */     PrefabBuffer.ChildPrefab[] childPrefabArray = (PrefabBuffer.ChildPrefab[])this.childPrefabs.toArray(x$0 -> new PrefabBuffer.ChildPrefab[x$0]);
/*     */     
/* 470 */     if (this.columns.isEmpty()) {
/* 471 */       this.min.assign(0);
/* 472 */       this.max.assign(0);
/*     */     } 
/* 474 */     return new PrefabBuffer(buffer, this.anchor, this.min, this.max, this.columns, childPrefabArray);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\PrefabBuffer$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */