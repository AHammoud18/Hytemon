/*      */ package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;
/*      */ 
/*      */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*      */ import com.hypixel.hytale.component.Holder;
/*      */ import com.hypixel.hytale.math.util.MathUtil;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*      */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*      */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferCall;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*      */ import com.hypixel.hytale.server.core.util.io.ByteBufUtil;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PrefabBufferAccessor
/*      */   implements IPrefabBuffer
/*      */ {
/*      */   @Nonnull
/*      */   private final PrefabBuffer prefabBuffer;
/*      */   @Nullable
/*      */   private ByteBuf buffer;
/*      */   
/*      */   private PrefabBufferAccessor(@Nonnull PrefabBuffer prefabBuffer) {
/*  485 */     this.buffer = prefabBuffer.buf.retainedDuplicate();
/*  486 */     this.prefabBuffer = prefabBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAnchorX() {
/*  491 */     return this.prefabBuffer.getAnchorX();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAnchorY() {
/*  496 */     return this.prefabBuffer.getAnchorY();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAnchorZ() {
/*  501 */     return this.prefabBuffer.getAnchorZ();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMinX(@Nonnull PrefabRotation rotation) {
/*  506 */     this.prefabBuffer.checkReleased();
/*  507 */     return Math.min(rotation.getX(this.prefabBuffer.min.getX(), this.prefabBuffer.min.getZ()), rotation.getX(this.prefabBuffer.max.getX(), this.prefabBuffer.max.getZ()));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMinY() {
/*  512 */     this.prefabBuffer.checkReleased();
/*  513 */     return this.prefabBuffer.min.getY();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMinZ(@Nonnull PrefabRotation rotation) {
/*  518 */     this.prefabBuffer.checkReleased();
/*  519 */     return Math.min(rotation.getZ(this.prefabBuffer.min.getX(), this.prefabBuffer.min.getZ()), rotation.getZ(this.prefabBuffer.max.getX(), this.prefabBuffer.max.getZ()));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxX(@Nonnull PrefabRotation rotation) {
/*  524 */     this.prefabBuffer.checkReleased();
/*  525 */     return Math.max(rotation.getX(this.prefabBuffer.min.getX(), this.prefabBuffer.min.getZ()), rotation.getX(this.prefabBuffer.max.getX(), this.prefabBuffer.max.getZ()));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxY() {
/*  530 */     this.prefabBuffer.checkReleased();
/*  531 */     return this.prefabBuffer.max.getY();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxZ(@Nonnull PrefabRotation rotation) {
/*  536 */     this.prefabBuffer.checkReleased();
/*  537 */     return Math.max(rotation.getZ(this.prefabBuffer.min.getX(), this.prefabBuffer.min.getZ()), rotation.getZ(this.prefabBuffer.max.getX(), this.prefabBuffer.max.getZ()));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getColumnCount() {
/*  542 */     return this.prefabBuffer.columns.size();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public PrefabBuffer.ChildPrefab[] getChildPrefabs() {
/*  548 */     return this.prefabBuffer.childPrefabs;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMinYAt(@Nonnull PrefabRotation rotation, int x, int z) {
/*  553 */     this.prefabBuffer.checkReleased();
/*  554 */     int rotatedX = rotation.getX(x, z);
/*  555 */     int rotatedZ = rotation.getZ(x, z);
/*  556 */     int columnIndex = MathUtil.packInt(rotatedX, rotatedZ);
/*  557 */     PrefabBufferColumn columnData = (PrefabBufferColumn)this.prefabBuffer.columns.get(columnIndex);
/*  558 */     if (columnData != null) {
/*  559 */       this.buffer.readerIndex(columnData.getReaderIndex());
/*  560 */       int blockCount = this.buffer.readInt();
/*  561 */       if (blockCount > 0) {
/*  562 */         return this.buffer.readInt() + 1;
/*      */       }
/*      */     } 
/*  565 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxYAt(@Nonnull PrefabRotation rotation, int x, int z) {
/*  570 */     this.prefabBuffer.checkReleased();
/*  571 */     int rotatedX = rotation.getX(x, z);
/*  572 */     int rotatedZ = rotation.getZ(x, z);
/*  573 */     int columnIndex = MathUtil.packInt(rotatedX, rotatedZ);
/*  574 */     PrefabBufferColumn column = (PrefabBufferColumn)this.prefabBuffer.columns.get(columnIndex);
/*  575 */     if (column == null) return -1;
/*      */     
/*  577 */     this.buffer.readerIndex(column.getReaderIndex());
/*  578 */     int blockCount = this.buffer.readInt();
/*  579 */     if (blockCount > 0) {
/*  580 */       int y = this.buffer.readInt();
/*  581 */       for (int i = 0; i < blockCount; i++) {
/*  582 */         int mask = this.buffer.readUnsignedShort();
/*  583 */         if (PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask) > 0) {
/*  584 */           this.buffer.skipBytes(PrefabBuffer.BlockMaskConstants.getBlockBytes(mask));
/*  585 */           y += ByteBufUtil.readNumber(this.buffer, PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask));
/*  586 */           if (PrefabBuffer.BlockMaskConstants.hasChance(mask)) this.buffer.skipBytes(4); 
/*  587 */           if (PrefabBuffer.BlockMaskConstants.hasRotation(mask)) this.buffer.skipBytes(1); 
/*  588 */           if (PrefabBuffer.BlockMaskConstants.hasFiller(mask)) this.buffer.skipBytes(2); 
/*  589 */           this.buffer.skipBytes(PrefabBuffer.BlockMaskConstants.getFluidBytes(mask));
/*      */         } else {
/*  591 */           this.buffer.skipBytes(PrefabBuffer.BlockMaskConstants.getSkipBytes(mask));
/*  592 */           y++;
/*      */         } 
/*      */       } 
/*  595 */       return y;
/*      */     } 
/*  597 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends PrefabBufferCall> void forEach(@Nonnull IPrefabBuffer.ColumnPredicate<T> columnPredicate, @Nonnull IPrefabBuffer.BlockConsumer<T> blockConsumer, @Nullable IPrefabBuffer.EntityConsumer<T> entityConsumer, @Nullable IPrefabBuffer.ChildConsumer<T> childConsumer, @Nonnull T t) {
/*  606 */     this.prefabBuffer.checkReleased();
/*  607 */     this.prefabBuffer.columns.int2ObjectEntrySet().forEach(entry -> {
/*      */           int columnIndex = entry.getIntKey();
/*      */           
/*      */           int cx = MathUtil.unpackLeft(columnIndex);
/*      */           
/*      */           int cz = MathUtil.unpackRight(columnIndex);
/*      */           
/*      */           int x = t.rotation.getX(cx, cz);
/*      */           
/*      */           int z = t.rotation.getZ(cx, cz);
/*      */           
/*      */           PrefabBufferColumn column = (PrefabBufferColumn)entry.getValue();
/*      */           
/*      */           this.buffer.readerIndex(column.getReaderIndex());
/*      */           
/*      */           int blockCount = this.buffer.readInt();
/*      */           
/*      */           if (!columnPredicate.test(x, z, blockCount, t)) {
/*      */             return;
/*      */           }
/*      */           
/*      */           BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*      */           
/*      */           if (blockCount > 0) {
/*      */             int y = this.buffer.readInt();
/*      */             
/*      */             for (int i = 0; i < blockCount; i++) {
/*      */               int mask = this.buffer.readUnsignedShort();
/*      */               
/*      */               int blockBytes = PrefabBuffer.BlockMaskConstants.getBlockBytes(mask);
/*      */               int blockId = ByteBufUtil.readNumber(this.buffer, blockBytes);
/*      */               int offsetBytes = PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask);
/*      */               y += (offsetBytes == 0) ? 1 : ByteBufUtil.readNumber(this.buffer, offsetBytes);
/*      */               if (PrefabBuffer.BlockMaskConstants.hasChance(mask)) {
/*      */                 float chance = this.buffer.readFloat();
/*      */                 if (chance < t.random.nextFloat()) {
/*      */                   this.buffer.skipBytes(2);
/*      */                   this.buffer.skipBytes(PrefabBuffer.BlockMaskConstants.getFluidBytes(mask));
/*      */                   continue;
/*      */                 } 
/*      */               } 
/*      */               Holder<ChunkStore> holder = PrefabBuffer.BlockMaskConstants.hasComponents(mask) ? (Holder<ChunkStore>)column.getBlockComponents().get(y) : null;
/*      */               int supportValue = PrefabBuffer.BlockMaskConstants.getSupportValue(mask);
/*      */               int rotation = 0;
/*      */               if (PrefabBuffer.BlockMaskConstants.hasRotation(mask)) {
/*      */                 rotation = this.buffer.readUnsignedByte();
/*      */               }
/*      */               rotation = t.rotation.getRotation(rotation);
/*      */               int filler = 0;
/*      */               if (PrefabBuffer.BlockMaskConstants.hasFiller(mask)) {
/*      */                 filler = t.rotation.getFiller(this.buffer.readUnsignedShort());
/*      */               }
/*      */               int fluidBytes = PrefabBuffer.BlockMaskConstants.getFluidBytes(mask);
/*      */               int fluidId = 0;
/*      */               int fluidLevel = 0;
/*      */               if (fluidBytes != 0) {
/*      */                 fluidId = ByteBufUtil.readNumber(this.buffer, fluidBytes - 1);
/*      */                 fluidLevel = this.buffer.readByte();
/*      */               } 
/*      */               blockConsumer.accept(x, y, z, blockId, holder, supportValue, rotation, filler, t, fluidId, fluidLevel);
/*      */               continue;
/*      */             } 
/*      */           } 
/*      */           Holder[] arrayOfHolder = (Holder[])column.getEntityHolders();
/*      */           if (arrayOfHolder != null && entityConsumer != null) {
/*      */             entityConsumer.accept(x, z, (Holder<EntityStore>[])arrayOfHolder, t);
/*      */           }
/*      */         });
/*  675 */     if (this.prefabBuffer.childPrefabs != null && childConsumer != null) {
/*  676 */       for (PrefabBuffer.ChildPrefab childPrefab : this.prefabBuffer.childPrefabs) {
/*  677 */         int x = ((PrefabBufferCall)t).rotation.getX(childPrefab.x, childPrefab.z);
/*  678 */         int z = ((PrefabBufferCall)t).rotation.getZ(childPrefab.x, childPrefab.z);
/*  679 */         childConsumer.accept(x, childPrefab.y, z, childPrefab.path, childPrefab.fitHeightmap, childPrefab.inheritSeed, childPrefab.inheritHeightCondition, childPrefab.weights, childPrefab.rotation, t);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> void forEachRaw(@Nonnull IPrefabBuffer.ColumnPredicate<T> columnPredicate, @Nonnull IPrefabBuffer.RawBlockConsumer<T> blockConsumer, @Nonnull IPrefabBuffer.FluidConsumer<T> fluidConsumer, @Nullable IPrefabBuffer.EntityConsumer<T> entityConsumer, @Nullable T t) {
/*  690 */     this.prefabBuffer.checkReleased();
/*  691 */     this.prefabBuffer.columns.int2ObjectEntrySet().forEach(entry -> {
/*      */           int columnIndex = entry.getIntKey();
/*      */           int x = MathUtil.unpackLeft(columnIndex);
/*      */           int z = MathUtil.unpackRight(columnIndex);
/*      */           PrefabBufferColumn column = (PrefabBufferColumn)entry.getValue();
/*      */           this.buffer.readerIndex(column.getReaderIndex());
/*      */           int blockCount = this.buffer.readInt();
/*      */           if (!columnPredicate.test(x, z, blockCount, t)) {
/*      */             return;
/*      */           }
/*      */           if (blockCount > 0) {
/*      */             int y = this.buffer.readInt();
/*      */             for (int i = 0; i < blockCount; i++) {
/*      */               int mask = this.buffer.readUnsignedShort();
/*      */               int blockBytes = PrefabBuffer.BlockMaskConstants.getBlockBytes(mask);
/*      */               int blockId = ByteBufUtil.readNumber(this.buffer, blockBytes);
/*      */               int offsetBytes = PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask);
/*      */               y += (offsetBytes == 0) ? 1 : ByteBufUtil.readNumber(this.buffer, offsetBytes);
/*      */               float chance = PrefabBuffer.BlockMaskConstants.hasChance(mask) ? this.buffer.readFloat() : 1.0F;
/*      */               Holder<ChunkStore> holder = PrefabBuffer.BlockMaskConstants.hasComponents(mask) ? (Holder<ChunkStore>)column.getBlockComponents().get(y) : null;
/*      */               int supportValue = PrefabBuffer.BlockMaskConstants.getSupportValue(mask);
/*      */               int rotation = 0;
/*      */               if (PrefabBuffer.BlockMaskConstants.hasRotation(mask)) {
/*      */                 rotation = this.buffer.readUnsignedByte();
/*      */               }
/*      */               int filler = 0;
/*      */               if (PrefabBuffer.BlockMaskConstants.hasFiller(mask)) {
/*      */                 filler = this.buffer.readUnsignedShort();
/*      */               }
/*      */               int position = this.buffer.readerIndex();
/*      */               blockConsumer.accept(x, y, z, mask, blockId, chance, holder, supportValue, rotation, filler, t);
/*      */               this.buffer.readerIndex(position);
/*      */               int fluidBytes = PrefabBuffer.BlockMaskConstants.getFluidBytes(mask);
/*      */               if (fluidBytes != 0) {
/*      */                 int fluidId = ByteBufUtil.readNumber(this.buffer, fluidBytes - 1);
/*      */                 byte fluidLevel = this.buffer.readByte();
/*      */                 position = this.buffer.readerIndex();
/*      */                 fluidConsumer.accept(x, y, z, fluidId, fluidLevel, t);
/*      */                 this.buffer.readerIndex(position);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           Holder[] arrayOfHolder = (Holder[])column.getEntityHolders();
/*      */           if (entityConsumer != null) {
/*      */             entityConsumer.accept(x, z, (Holder<EntityStore>[])arrayOfHolder, t);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> boolean forEachRaw(@Nonnull IPrefabBuffer.ColumnPredicate<T> columnPredicate, @Nonnull IPrefabBuffer.RawBlockPredicate<T> blockPredicate, @Nonnull IPrefabBuffer.FluidPredicate<T> fluidPredicate, @Nullable IPrefabBuffer.EntityPredicate<T> entityPredicate, @Nullable T t) {
/*  754 */     this.prefabBuffer.checkReleased();
/*      */     
/*  756 */     for (ObjectIterator<Int2ObjectMap.Entry<PrefabBufferColumn>> objectIterator = this.prefabBuffer.columns.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<PrefabBufferColumn> entry = objectIterator.next();
/*  757 */       int columnIndex = entry.getIntKey();
/*  758 */       int x = MathUtil.unpackLeft(columnIndex);
/*  759 */       int z = MathUtil.unpackRight(columnIndex);
/*      */       
/*  761 */       PrefabBufferColumn column = (PrefabBufferColumn)entry.getValue();
/*  762 */       this.buffer.readerIndex(column.getReaderIndex());
/*  763 */       int blockCount = this.buffer.readInt();
/*  764 */       if (!columnPredicate.test(x, z, blockCount, t)) {
/*  765 */         return false;
/*      */       }
/*      */       
/*  768 */       if (blockCount > 0) {
/*  769 */         int y = this.buffer.readInt();
/*  770 */         for (int i = 0; i < blockCount; i++) {
/*  771 */           int mask = this.buffer.readUnsignedShort();
/*  772 */           int blockBytes = PrefabBuffer.BlockMaskConstants.getBlockBytes(mask);
/*  773 */           int blockId = ByteBufUtil.readNumber(this.buffer, blockBytes);
/*      */           
/*  775 */           int offsetBytes = PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask);
/*  776 */           y += (offsetBytes == 0) ? 1 : ByteBufUtil.readNumber(this.buffer, offsetBytes);
/*      */           
/*  778 */           float chance = PrefabBuffer.BlockMaskConstants.hasChance(mask) ? this.buffer.readFloat() : 1.0F;
/*  779 */           Holder<ChunkStore> holder = PrefabBuffer.BlockMaskConstants.hasComponents(mask) ? (Holder<ChunkStore>)column.getBlockComponents().get(y) : null;
/*  780 */           short rotation = PrefabBuffer.BlockMaskConstants.hasRotation(mask) ? this.buffer.readUnsignedByte() : 0;
/*  781 */           int filler = PrefabBuffer.BlockMaskConstants.hasFiller(mask) ? this.buffer.readUnsignedShort() : 0;
/*      */           
/*  783 */           int supportValue = PrefabBuffer.BlockMaskConstants.getSupportValue(mask);
/*      */           
/*  785 */           int position = this.buffer.readerIndex();
/*  786 */           if (!blockPredicate.test(x, y, z, blockId, chance, holder, supportValue, rotation, filler, t)) {
/*  787 */             return false;
/*      */           }
/*      */           
/*  790 */           this.buffer.readerIndex(position);
/*      */           
/*  792 */           int fluidBytes = PrefabBuffer.BlockMaskConstants.getFluidBytes(mask);
/*  793 */           if (fluidBytes != 0) {
/*  794 */             int fluidId = ByteBufUtil.readNumber(this.buffer, fluidBytes - 1);
/*  795 */             byte fluidLevel = this.buffer.readByte();
/*  796 */             position = this.buffer.readerIndex();
/*  797 */             if (!fluidPredicate.test(x, y, z, fluidId, fluidLevel, t)) {
/*  798 */               return false;
/*      */             }
/*  800 */             this.buffer.readerIndex(position);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  805 */       Holder[] arrayOfHolder = (Holder[])column.getEntityHolders();
/*  806 */       if (entityPredicate != null && !entityPredicate.test(x, z, (Holder<EntityStore>[])arrayOfHolder, t)) {
/*  807 */         return false;
/*      */       } }
/*      */ 
/*      */     
/*  811 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void release() {
/*  816 */     this.buffer.release();
/*  817 */     this.buffer = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends PrefabBufferCall> boolean compare(@Nonnull IPrefabBuffer.BlockComparingPrefabPredicate<T> blockComparingIterator, @Nonnull T t, @Nonnull IPrefabBuffer otherPrefab) {
/*      */     PrefabBufferAccessor secondPrefab;
/*  824 */     if (otherPrefab instanceof PrefabBufferAccessor) { secondPrefab = (PrefabBufferAccessor)otherPrefab; } else { return super.compare(blockComparingIterator, t, otherPrefab); }
/*  825 */      Int2ObjectMap<PrefabBufferColumn> secondPrefabColumns = secondPrefab.prefabBuffer.columns;
/*      */     
/*  827 */     IntOpenHashSet columnIndexes = new IntOpenHashSet(this.prefabBuffer.columns.size() + secondPrefabColumns.size());
/*  828 */     columnIndexes.addAll((IntCollection)this.prefabBuffer.columns.keySet());
/*  829 */     columnIndexes.addAll((IntCollection)secondPrefabColumns.keySet());
/*      */     
/*  831 */     this.prefabBuffer.checkReleased();
/*      */     
/*  833 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*      */     
/*  835 */     IntIterator columnIterator = columnIndexes.iterator();
/*  836 */     label112: while (columnIterator.hasNext()) {
/*  837 */       int columnIndex = columnIterator.nextInt();
/*  838 */       int cx = MathUtil.unpackLeft(columnIndex);
/*  839 */       int cz = MathUtil.unpackRight(columnIndex);
/*      */       
/*  841 */       int x = ((PrefabBufferCall)t).rotation.getX(cx, cz);
/*  842 */       int z = ((PrefabBufferCall)t).rotation.getZ(cx, cz);
/*      */ 
/*      */       
/*  845 */       PrefabBufferColumn firstColumn = (PrefabBufferColumn)this.prefabBuffer.columns.get(columnIndex);
/*  846 */       PrefabBufferColumn secondColumn = (PrefabBufferColumn)secondPrefabColumns.get(columnIndex);
/*      */ 
/*      */       
/*  849 */       if (firstColumn != null) this.buffer.readerIndex(firstColumn.getReaderIndex()); 
/*  850 */       if (secondColumn != null) secondPrefab.buffer.readerIndex(secondColumn.getReaderIndex());
/*      */ 
/*      */       
/*  853 */       int firstColumnBlockCount = (firstColumn != null) ? this.buffer.readInt() : 0;
/*  854 */       int secondColumnBlockCount = (secondColumn != null) ? secondPrefab.buffer.readInt() : 0;
/*      */ 
/*      */       
/*  857 */       if (firstColumnBlockCount == 0 && secondColumnBlockCount == 0) {
/*      */         continue;
/*      */       }
/*  860 */       int firstColumnY = (firstColumnBlockCount > 0) ? this.buffer.readInt() : Integer.MAX_VALUE;
/*  861 */       int secondColumnY = (secondColumnBlockCount > 0) ? secondPrefab.buffer.readInt() : Integer.MAX_VALUE;
/*      */ 
/*      */       
/*  864 */       int firstColumnBlockId = Integer.MIN_VALUE;
/*  865 */       float firstColumnChance = 1.0F;
/*  866 */       int firstColumnRotation = 0;
/*  867 */       int firstColumnFiller = 0;
/*  868 */       Holder<ChunkStore> firstColumnComponents = null;
/*      */       
/*  870 */       int secondColumnBlockId = Integer.MIN_VALUE;
/*  871 */       float secondColumnChance = 1.0F;
/*  872 */       int secondColumnRotation = 0;
/*  873 */       int secondColumnFiller = 0;
/*  874 */       Holder<ChunkStore> secondColumnComponents = null;
/*      */       
/*  876 */       int firstColumnBlocksRead = 0;
/*  877 */       int secondColumnBlocksRead = 0; while (true) {
/*  878 */         if (firstColumnBlocksRead < firstColumnBlockCount || secondColumnBlocksRead < secondColumnBlockCount) {
/*  879 */           int oldFirstColumnY = firstColumnY;
/*  880 */           int oldSecondColumnY = secondColumnY;
/*  881 */           int oldFirstColumnReaderIndex = (firstColumnBlocksRead < firstColumnBlockCount) ? this.buffer.readerIndex() : -1;
/*  882 */           int oldSecondColumnReaderIndex = (secondColumnBlocksRead < secondColumnBlockCount) ? secondPrefab.buffer.readerIndex() : -1;
/*      */ 
/*      */           
/*  885 */           if (firstColumnBlocksRead < firstColumnBlockCount) {
/*  886 */             int mask = this.buffer.readUnsignedShort();
/*  887 */             int blockBytes = PrefabBuffer.BlockMaskConstants.getBlockBytes(mask);
/*  888 */             firstColumnBlockId = ByteBufUtil.readNumber(this.buffer, blockBytes);
/*      */             
/*  890 */             int offsetBytes = PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask);
/*  891 */             firstColumnY += (offsetBytes == 0) ? 1 : ByteBufUtil.readNumber(this.buffer, offsetBytes);
/*      */             
/*  893 */             firstColumnChance = PrefabBuffer.BlockMaskConstants.hasChance(mask) ? this.buffer.readFloat() : 1.0F;
/*  894 */             firstColumnRotation = ((PrefabBufferCall)t).rotation.getRotation(PrefabBuffer.BlockMaskConstants.hasRotation(mask) ? this.buffer.readUnsignedByte() : 0);
/*  895 */             firstColumnFiller = PrefabBuffer.BlockMaskConstants.hasFiller(mask) ? ((PrefabBufferCall)t).rotation.getFiller(this.buffer.readUnsignedShort()) : 0;
/*  896 */             firstColumnComponents = PrefabBuffer.BlockMaskConstants.hasComponents(mask) ? (Holder<ChunkStore>)firstColumn.getBlockComponents().get(firstColumnY) : null;
/*  897 */             this.buffer.skipBytes(PrefabBuffer.BlockMaskConstants.getFluidBytes(mask));
/*      */           } 
/*      */ 
/*      */           
/*  901 */           if (secondColumnBlocksRead < secondColumnBlockCount) {
/*  902 */             int mask = secondPrefab.buffer.readUnsignedShort();
/*  903 */             int blockBytes = PrefabBuffer.BlockMaskConstants.getBlockBytes(mask);
/*  904 */             secondColumnBlockId = ByteBufUtil.readNumber(secondPrefab.buffer, blockBytes);
/*      */             
/*  906 */             int offsetBytes = PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask);
/*  907 */             secondColumnY += (offsetBytes == 0) ? 1 : ByteBufUtil.readNumber(secondPrefab.buffer, offsetBytes);
/*      */             
/*  909 */             secondColumnChance = PrefabBuffer.BlockMaskConstants.hasChance(mask) ? secondPrefab.buffer.readFloat() : 1.0F;
/*  910 */             secondColumnRotation = ((PrefabBufferCall)t).rotation.getRotation(PrefabBuffer.BlockMaskConstants.hasRotation(mask) ? secondPrefab.buffer.readUnsignedByte() : 0);
/*  911 */             secondColumnFiller = PrefabBuffer.BlockMaskConstants.hasFiller(mask) ? ((PrefabBufferCall)t).rotation.getFiller(secondPrefab.buffer.readUnsignedShort()) : 0;
/*  912 */             secondColumnComponents = PrefabBuffer.BlockMaskConstants.hasComponents(mask) ? (Holder<ChunkStore>)secondColumn.getBlockComponents().get(secondColumnY) : null;
/*  913 */             secondPrefab.buffer.skipBytes(PrefabBuffer.BlockMaskConstants.getFluidBytes(mask));
/*      */           } 
/*      */ 
/*      */           
/*  917 */           if (firstColumnY == secondColumnY) {
/*  918 */             firstColumnBlocksRead++;
/*  919 */             secondColumnBlocksRead++;
/*      */             
/*  921 */             boolean bool = blockComparingIterator.test(x, firstColumnY, z, firstColumnBlockId, firstColumnComponents, firstColumnChance, firstColumnRotation, firstColumnFiller, secondColumnBlockId, secondColumnComponents, secondColumnChance, secondColumnRotation, secondColumnFiller, t);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  927 */             if (!bool) return false;  continue;
/*  928 */           }  if ((firstColumnY < secondColumnY && firstColumnBlocksRead < firstColumnBlockCount) || secondColumnBlocksRead >= secondColumnBlockCount) {
/*      */ 
/*      */             
/*  931 */             firstColumnBlocksRead++;
/*  932 */             secondColumnY = oldSecondColumnY;
/*      */             
/*  934 */             if (oldSecondColumnReaderIndex != -1) {
/*  935 */               secondPrefab.buffer.readerIndex(oldSecondColumnReaderIndex);
/*      */             }
/*      */             
/*  938 */             boolean bool = blockComparingIterator.test(x, firstColumnY, z, firstColumnBlockId, firstColumnComponents, firstColumnChance, firstColumnRotation, firstColumnFiller, -2147483648, null, 1.0F, 0, 0, t);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  944 */             if (!bool) return false; 
/*      */             continue;
/*      */           } 
/*  947 */           secondColumnBlocksRead++;
/*      */           
/*  949 */           firstColumnY = oldFirstColumnY;
/*  950 */           if (oldFirstColumnReaderIndex != -1) {
/*  951 */             this.buffer.readerIndex(oldFirstColumnReaderIndex);
/*      */           }
/*      */           
/*  954 */           boolean test = blockComparingIterator.test(x, secondColumnY, z, -2147483648, null, 1.0F, 0, 0, secondColumnBlockId, secondColumnComponents, secondColumnChance, secondColumnRotation, secondColumnFiller, t);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  960 */           if (!test) return false;  continue;
/*      */         } 
/*      */         continue label112;
/*      */       } 
/*      */     } 
/*  965 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBlockId(int x, int y, int z) {
/*  970 */     this.prefabBuffer.checkReleased();
/*  971 */     PrefabBufferColumn column = (PrefabBufferColumn)this.prefabBuffer.columns.get(MathUtil.packInt(x, z));
/*  972 */     if (column == null) return 0;
/*      */     
/*  974 */     this.buffer.readerIndex(column.getReaderIndex());
/*  975 */     int blockCount = this.buffer.readInt();
/*  976 */     if (blockCount <= 0) return 0;
/*      */     
/*  978 */     int blockY = this.buffer.readInt();
/*  979 */     for (int i = 0; i < blockCount; i++) {
/*  980 */       int mask = this.buffer.readUnsignedShort();
/*  981 */       int blockBytes = PrefabBuffer.BlockMaskConstants.getBlockBytes(mask);
/*  982 */       int blockId = ByteBufUtil.readNumber(this.buffer, blockBytes);
/*      */       
/*  984 */       int offsetBytes = PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask);
/*  985 */       blockY += (offsetBytes == 0) ? 1 : ByteBufUtil.readNumber(this.buffer, offsetBytes);
/*      */ 
/*      */       
/*  988 */       if (blockY > y) return 0;
/*      */       
/*  990 */       if (PrefabBuffer.BlockMaskConstants.hasChance(mask)) this.buffer.readFloat(); 
/*  991 */       if (PrefabBuffer.BlockMaskConstants.hasRotation(mask)) this.buffer.readUnsignedByte(); 
/*  992 */       if (PrefabBuffer.BlockMaskConstants.hasFiller(mask)) this.buffer.readUnsignedShort();
/*      */       
/*  994 */       int fluidBytes = PrefabBuffer.BlockMaskConstants.getFluidBytes(mask);
/*  995 */       this.buffer.skipBytes(fluidBytes);
/*      */       
/*  997 */       if (blockY == y) {
/*  998 */         if (PrefabBuffer.BlockMaskConstants.hasChance(mask)) throw new UnsupportedOperationException("Unable to access block with chance!"); 
/*  999 */         return blockId;
/*      */       } 
/*      */     } 
/* 1002 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getFiller(int x, int y, int z) {
/* 1007 */     this.prefabBuffer.checkReleased();
/* 1008 */     PrefabBufferColumn column = (PrefabBufferColumn)this.prefabBuffer.columns.get(MathUtil.packInt(x, z));
/* 1009 */     if (column == null) return 0;
/*      */     
/* 1011 */     this.buffer.readerIndex(column.getReaderIndex());
/* 1012 */     int blockCount = this.buffer.readInt();
/* 1013 */     if (blockCount <= 0) return 0;
/*      */     
/* 1015 */     int blockY = this.buffer.readInt();
/* 1016 */     for (int i = 0; i < blockCount; i++) {
/* 1017 */       int mask = this.buffer.readUnsignedShort();
/* 1018 */       int blockBytes = PrefabBuffer.BlockMaskConstants.getBlockBytes(mask);
/* 1019 */       int blockId = ByteBufUtil.readNumber(this.buffer, blockBytes);
/*      */       
/* 1021 */       int offsetBytes = PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask);
/* 1022 */       blockY += (offsetBytes == 0) ? 1 : ByteBufUtil.readNumber(this.buffer, offsetBytes);
/*      */ 
/*      */       
/* 1025 */       if (blockY > y) return 0;
/*      */       
/* 1027 */       if (PrefabBuffer.BlockMaskConstants.hasChance(mask)) this.buffer.readFloat(); 
/* 1028 */       if (PrefabBuffer.BlockMaskConstants.hasRotation(mask)) this.buffer.readUnsignedByte(); 
/* 1029 */       int filler = 0;
/* 1030 */       if (PrefabBuffer.BlockMaskConstants.hasFiller(mask)) filler = this.buffer.readUnsignedShort();
/*      */       
/* 1032 */       int fluidBytes = PrefabBuffer.BlockMaskConstants.getFluidBytes(mask);
/* 1033 */       this.buffer.skipBytes(fluidBytes);
/*      */       
/* 1035 */       if (blockY == y) {
/* 1036 */         if (PrefabBuffer.BlockMaskConstants.hasChance(mask)) throw new UnsupportedOperationException("Unable to access block with chance!"); 
/* 1037 */         return filler;
/*      */       } 
/*      */     } 
/* 1040 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRotationIndex(int x, int y, int z) {
/* 1045 */     this.prefabBuffer.checkReleased();
/* 1046 */     PrefabBufferColumn column = (PrefabBufferColumn)this.prefabBuffer.columns.get(MathUtil.packInt(x, z));
/* 1047 */     if (column == null) return 0;
/*      */     
/* 1049 */     this.buffer.readerIndex(column.getReaderIndex());
/* 1050 */     int blockCount = this.buffer.readInt();
/* 1051 */     if (blockCount <= 0) return 0;
/*      */     
/* 1053 */     int blockY = this.buffer.readInt();
/* 1054 */     for (int i = 0; i < blockCount; i++) {
/* 1055 */       int mask = this.buffer.readUnsignedShort();
/* 1056 */       int blockBytes = PrefabBuffer.BlockMaskConstants.getBlockBytes(mask);
/* 1057 */       int blockId = ByteBufUtil.readNumber(this.buffer, blockBytes);
/*      */       
/* 1059 */       int offsetBytes = PrefabBuffer.BlockMaskConstants.getOffsetBytes(mask);
/* 1060 */       blockY += (offsetBytes == 0) ? 1 : ByteBufUtil.readNumber(this.buffer, offsetBytes);
/*      */ 
/*      */       
/* 1063 */       if (blockY > y) return 0;
/*      */       
/* 1065 */       if (PrefabBuffer.BlockMaskConstants.hasChance(mask)) this.buffer.readFloat(); 
/* 1066 */       int rotation = 0;
/* 1067 */       if (PrefabBuffer.BlockMaskConstants.hasRotation(mask)) rotation = this.buffer.readUnsignedByte(); 
/* 1068 */       if (PrefabBuffer.BlockMaskConstants.hasFiller(mask)) this.buffer.readUnsignedShort();
/*      */       
/* 1070 */       int fluidBytes = PrefabBuffer.BlockMaskConstants.getFluidBytes(mask);
/* 1071 */       this.buffer.skipBytes(fluidBytes);
/*      */       
/* 1073 */       if (blockY == y) {
/* 1074 */         if (PrefabBuffer.BlockMaskConstants.hasChance(mask)) throw new UnsupportedOperationException("Unable to access block with chance!"); 
/* 1075 */         return rotation;
/*      */       } 
/*      */     } 
/* 1078 */     return 0;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\PrefabBuffer$PrefabBufferAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */