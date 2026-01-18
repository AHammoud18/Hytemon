/*    */ package com.hypixel.hytale.server.core.universe.world.worldgen;
/*    */ 
/*    */ import com.hypixel.hytale.common.collection.Flag;
/*    */ import com.hypixel.hytale.common.collection.Flags;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.EntityChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class GeneratedChunk
/*    */ {
/*    */   private final GeneratedBlockChunk generatedBlockChunk;
/*    */   private final GeneratedBlockStateChunk generatedBlockStateChunk;
/*    */   private final GeneratedEntityChunk generatedEntityChunk;
/*    */   private final Holder<ChunkStore>[] sections;
/*    */   
/*    */   public GeneratedChunk() {
/* 26 */     this(new GeneratedBlockChunk(), new GeneratedBlockStateChunk(), new GeneratedEntityChunk(), makeSections());
/*    */   }
/*    */ 
/*    */   
/*    */   public GeneratedChunk(GeneratedBlockChunk generatedBlockChunk, GeneratedBlockStateChunk generatedBlockStateChunk, GeneratedEntityChunk generatedEntityChunk, Holder<ChunkStore>[] sections) {
/* 31 */     this.generatedBlockChunk = generatedBlockChunk;
/* 32 */     this.generatedBlockStateChunk = generatedBlockStateChunk;
/* 33 */     this.generatedEntityChunk = generatedEntityChunk;
/* 34 */     this.sections = sections;
/*    */   }
/*    */   
/*    */   public GeneratedBlockChunk getBlockChunk() {
/* 38 */     return this.generatedBlockChunk;
/*    */   }
/*    */   
/*    */   public GeneratedBlockStateChunk getBlockStateChunk() {
/* 42 */     return this.generatedBlockStateChunk;
/*    */   }
/*    */   
/*    */   public GeneratedEntityChunk getEntityChunk() {
/* 46 */     return this.generatedEntityChunk;
/*    */   }
/*    */   
/*    */   public Holder<ChunkStore>[] getSections() {
/* 50 */     return this.sections;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Holder<ChunkStore> toWorldChunk(World world) {
/* 55 */     BlockChunk blockChunk = this.generatedBlockChunk.toBlockChunk(this.sections);
/* 56 */     BlockComponentChunk blockComponentChunk = this.generatedBlockStateChunk.toBlockComponentChunk();
/* 57 */     EntityChunk entityChunk = this.generatedEntityChunk.toEntityChunk();
/*    */     
/* 59 */     WorldChunk worldChunk = new WorldChunk(world, new Flags((Flag)ChunkFlag.NEWLY_GENERATED), blockChunk, blockComponentChunk, entityChunk);
/*    */ 
/*    */ 
/*    */     
/* 63 */     Holder<ChunkStore> holder = worldChunk.toHolder();
/* 64 */     holder.putComponent(ChunkColumn.getComponentType(), (Component)new ChunkColumn((Holder[])this.sections));
/* 65 */     return holder;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Holder<ChunkStore> toHolder(World world) {
/* 70 */     return toWorldChunk(world);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static Holder<ChunkStore>[] makeSections() {
/* 76 */     Holder[] arrayOfHolder = new Holder[10];
/* 77 */     for (int i = 0; i < arrayOfHolder.length; i++) {
/* 78 */       arrayOfHolder[i] = ChunkStore.REGISTRY.newHolder();
/*    */     }
/* 80 */     return (Holder<ChunkStore>[])arrayOfHolder;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\GeneratedChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */