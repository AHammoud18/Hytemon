/*    */ package com.hypixel.hytale.server.core.universe.world.storage.provider;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkLoader;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultChunkStorageProvider
/*    */   implements IChunkStorageProvider
/*    */ {
/*    */   @Nonnull
/* 22 */   public static final DefaultChunkStorageProvider INSTANCE = new DefaultChunkStorageProvider();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final String ID = "Hytale";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   public static final BuilderCodec<DefaultChunkStorageProvider> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DefaultChunkStorageProvider.class, () -> INSTANCE)
/* 34 */     .documentation("Selects the default recommended storage as decided by the server."))
/* 35 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 41 */   public static final IChunkStorageProvider DEFAULT = new IndexedStorageChunkStorageProvider();
/*    */ 
/*    */   
/*    */   @NonNullDecl
/*    */   public IChunkLoader getLoader(@NonNullDecl Store<ChunkStore> store) throws IOException {
/* 46 */     return DEFAULT.getLoader(store);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public IChunkSaver getSaver(@NonNullDecl Store<ChunkStore> store) throws IOException {
/* 51 */     return DEFAULT.getSaver(store);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 57 */     return "DefaultChunkStorageProvider{DEFAULT=" + String.valueOf(DEFAULT) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\provider\DefaultChunkStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */