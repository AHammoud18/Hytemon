/*    */ package com.hypixel.hytale.server.core.universe.world.worldgen;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeneratedBlockStateChunk
/*    */ {
/* 21 */   private final Int2ObjectMap<Holder<ChunkStore>> mapping = (Int2ObjectMap<Holder<ChunkStore>>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   public Holder<ChunkStore> getState(int x, int y, int z) {
/* 24 */     return (Holder<ChunkStore>)this.mapping.get(ChunkUtil.indexBlockInColumn(x, y, z));
/*    */   }
/*    */   
/*    */   public void setState(int x, int y, int z, @Nullable Holder<ChunkStore> state) {
/* 28 */     int index = ChunkUtil.indexBlockInColumn(x, y, z);
/* 29 */     if (state == null) {
/* 30 */       this.mapping.remove(index);
/*    */     } else {
/* 32 */       BlockState blockState = BlockState.getBlockState(state);
/* 33 */       if (blockState != null) blockState.setPosition(new Vector3i(x, y, z)); 
/* 34 */       this.mapping.put(index, state);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public BlockComponentChunk toBlockComponentChunk() {
/* 40 */     return new BlockComponentChunk(this.mapping, (Int2ObjectMap)new Int2ObjectOpenHashMap());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\GeneratedBlockStateChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */