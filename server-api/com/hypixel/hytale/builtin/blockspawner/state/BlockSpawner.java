/*    */ package com.hypixel.hytale.builtin.blockspawner.state;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.blockspawner.BlockSpawnerPlugin;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BlockSpawner
/*    */   implements Component<ChunkStore> {
/*    */   public static ComponentType<ChunkStore, BlockSpawner> getComponentType() {
/* 17 */     return BlockSpawnerPlugin.get().getBlockSpawnerComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<BlockSpawner> CODEC;
/*    */   
/*    */   private String blockSpawnerId;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockSpawner.class, BlockSpawner::new).addField(new KeyedCodec("BlockSpawnerId", (Codec)Codec.STRING), (state, s) -> state.blockSpawnerId = s, state -> state.blockSpawnerId)).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockSpawner() {}
/*    */ 
/*    */   
/*    */   public BlockSpawner(String blockSpawnerId) {
/* 34 */     this.blockSpawnerId = blockSpawnerId;
/*    */   }
/*    */   
/*    */   public String getBlockSpawnerId() {
/* 38 */     return this.blockSpawnerId;
/*    */   }
/*    */   
/*    */   public void setBlockSpawnerId(String blockSpawnerId) {
/* 42 */     this.blockSpawnerId = blockSpawnerId;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 48 */     return "BlockSpawnerState{blockSpawnerId='" + this.blockSpawnerId + "'} " + super
/*    */       
/* 50 */       .toString();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Component<ChunkStore> clone() {
/* 56 */     return new BlockSpawner(this.blockSpawnerId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockspawner\state\BlockSpawner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */