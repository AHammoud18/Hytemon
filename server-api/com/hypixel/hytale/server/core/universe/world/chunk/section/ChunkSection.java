/*    */ package com.hypixel.hytale.server.core.universe.world.chunk.section;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.modules.LegacyModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkSection
/*    */   implements Component<ChunkStore>
/*    */ {
/*    */   public static ComponentType<ChunkStore, ChunkSection> getComponentType() {
/* 18 */     return LegacyModule.get().getChunkSectionComponentType();
/*    */   }
/*    */   
/* 21 */   public static final BuilderCodec<ChunkSection> CODEC = BuilderCodec.builder(ChunkSection.class, ChunkSection::new).build();
/*    */   
/*    */   private Ref<ChunkStore> chunkColumnReference;
/*    */   
/*    */   private int x;
/*    */ 
/*    */   
/*    */   public ChunkSection(Ref<ChunkStore> chunkColumnReference, int x, int y, int z) {
/* 29 */     this.chunkColumnReference = chunkColumnReference;
/* 30 */     this.x = x;
/* 31 */     this.y = y;
/* 32 */     this.z = z;
/*    */   } private int y; private int z;
/*    */   private ChunkSection() {}
/*    */   public void load(Ref<ChunkStore> chunkReference, int x, int y, int z) {
/* 36 */     this.chunkColumnReference = chunkReference;
/* 37 */     this.x = x;
/* 38 */     this.y = y;
/* 39 */     this.z = z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Ref<ChunkStore> getChunkColumnReference() {
/* 46 */     return this.chunkColumnReference;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 50 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 54 */     return this.y;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 58 */     return this.z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<ChunkStore> clone() {
/* 66 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\ChunkSection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */