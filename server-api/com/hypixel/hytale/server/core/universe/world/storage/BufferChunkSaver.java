/*    */ package com.hypixel.hytale.server.core.universe.world.storage;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Store;
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
/*    */ public abstract class BufferChunkSaver
/*    */   implements IChunkSaver
/*    */ {
/*    */   @Nonnull
/*    */   private final Store<ChunkStore> store;
/*    */   
/*    */   protected BufferChunkSaver(@Nonnull Store<ChunkStore> store) {
/* 29 */     Objects.requireNonNull(store);
/* 30 */     this.store = store;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Store<ChunkStore> getStore() {
/* 38 */     return this.store;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public abstract CompletableFuture<Void> saveBuffer(int paramInt1, int paramInt2, @Nonnull ByteBuffer paramByteBuffer);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public abstract CompletableFuture<Void> removeBuffer(int paramInt1, int paramInt2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Void> saveHolder(int x, int z, @Nonnull Holder<ChunkStore> holder) {
/* 65 */     BsonDocument document = ChunkStore.REGISTRY.serialize(holder);
/*    */ 
/*    */     
/* 68 */     ByteBuffer buffer = ByteBuffer.wrap(BsonUtil.writeToBytes(document));
/* 69 */     return saveBuffer(x, z, buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Void> removeHolder(int x, int z) {
/* 75 */     return removeBuffer(x, z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\BufferChunkSaver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */