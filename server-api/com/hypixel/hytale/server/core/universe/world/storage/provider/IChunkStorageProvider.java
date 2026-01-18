/*    */ package com.hypixel.hytale.server.core.universe.world.storage.provider;
/*    */ 
/*    */ import com.hypixel.hytale.codec.lookup.BuilderCodecMapCodec;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkLoader;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IChunkStorageProvider
/*    */ {
/*    */   @Nonnull
/* 21 */   public static final BuilderCodecMapCodec<IChunkStorageProvider> CODEC = new BuilderCodecMapCodec("Type", true);
/*    */   
/*    */   @Nonnull
/*    */   IChunkLoader getLoader(@Nonnull Store<ChunkStore> paramStore) throws IOException;
/*    */   
/*    */   @Nonnull
/*    */   IChunkSaver getSaver(@Nonnull Store<ChunkStore> paramStore) throws IOException;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\provider\IChunkStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */