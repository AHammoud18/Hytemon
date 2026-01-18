/*    */ package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabBufferColumn
/*    */ {
/*    */   private final int readerIndex;
/*    */   private final Holder<EntityStore>[] entityHolders;
/*    */   private final Int2ObjectMap<Holder<ChunkStore>> blockComponents;
/*    */   
/*    */   public PrefabBufferColumn(int readerIndex, Holder<EntityStore>[] entityHolders, Int2ObjectMap<Holder<ChunkStore>> blockComponents) {
/* 18 */     this.readerIndex = readerIndex;
/* 19 */     this.entityHolders = entityHolders;
/* 20 */     this.blockComponents = blockComponents;
/*    */   }
/*    */   
/*    */   public int getReaderIndex() {
/* 24 */     return this.readerIndex;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Holder<EntityStore>[] getEntityHolders() {
/* 29 */     return this.entityHolders;
/*    */   }
/*    */   
/*    */   public Int2ObjectMap<Holder<ChunkStore>> getBlockComponents() {
/* 33 */     return this.blockComponents;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\PrefabBufferColumn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */