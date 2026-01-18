/*    */ package com.hypixel.hytale.server.core.universe.world.storage;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BufferChunkLoader
/*    */   implements IChunkLoader
/*    */ {
/*    */   @Nonnull
/*    */   private final Store<ChunkStore> store;
/*    */   
/*    */   public BufferChunkLoader(@Nonnull Store<ChunkStore> store) {
/* 30 */     Objects.requireNonNull(store);
/* 31 */     this.store = store;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Store<ChunkStore> getStore() {
/* 39 */     return this.store;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract CompletableFuture<ByteBuffer> loadBuffer(int paramInt1, int paramInt2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Holder<ChunkStore>> loadHolder(int x, int z) {
/* 57 */     return loadBuffer(x, z).thenApplyAsync(buffer -> {
/*    */           if (buffer == null)
/*    */             return null; 
/*    */           BsonDocument bsonDocument = BsonUtil.readFromBuffer(buffer);
/*    */           Holder<ChunkStore> holder = ChunkStore.REGISTRY.deserialize(bsonDocument);
/*    */           WorldChunk worldChunkComponent = (WorldChunk)holder.getComponent(WorldChunk.getComponentType());
/*    */           assert worldChunkComponent != null;
/*    */           worldChunkComponent.loadFromHolder(((ChunkStore)this.store.getExternalData()).getWorld(), x, z, holder);
/*    */           return holder;
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\BufferChunkLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */