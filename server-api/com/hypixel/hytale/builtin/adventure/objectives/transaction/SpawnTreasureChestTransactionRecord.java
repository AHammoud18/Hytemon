/*    */ package com.hypixel.hytale.builtin.adventure.objectives.transaction;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.blockstates.TreasureChestState;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class SpawnTreasureChestTransactionRecord
/*    */   extends TransactionRecord
/*    */ {
/*    */   public static final BuilderCodec<SpawnTreasureChestTransactionRecord> CODEC;
/*    */   protected UUID worldUUID;
/*    */   protected Vector3i blockPosition;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SpawnTreasureChestTransactionRecord.class, SpawnTreasureChestTransactionRecord::new, BASE_CODEC).append(new KeyedCodec("WorldUUID", (Codec)Codec.UUID_BINARY), (spawnTreasureChestTransactionRecord, uuid) -> spawnTreasureChestTransactionRecord.worldUUID = uuid, spawnTreasureChestTransactionRecord -> spawnTreasureChestTransactionRecord.worldUUID).add()).append(new KeyedCodec("BlockPosition", (Codec)Vector3i.CODEC), (spawnTreasureChestTransactionRecord, vector3d) -> spawnTreasureChestTransactionRecord.blockPosition = vector3d, spawnTreasureChestTransactionRecord -> spawnTreasureChestTransactionRecord.blockPosition).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SpawnTreasureChestTransactionRecord(UUID worldUUID, Vector3i blockPosition) {
/* 32 */     this.worldUUID = worldUUID;
/* 33 */     this.blockPosition = blockPosition;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SpawnTreasureChestTransactionRecord() {}
/*    */ 
/*    */   
/*    */   public void revert() {
/* 41 */     World world = Universe.get().getWorld(this.worldUUID);
/* 42 */     if (world == null)
/*    */       return; 
/* 44 */     WorldChunk worldChunk = world.getChunk(ChunkUtil.indexChunkFromBlock(this.blockPosition.x, this.blockPosition.z));
/* 45 */     BlockState blockState = worldChunk.getState(this.blockPosition.x, this.blockPosition.y, this.blockPosition.z);
/* 46 */     if (!(blockState instanceof TreasureChestState))
/*    */       return; 
/* 48 */     ((TreasureChestState)blockState).setOpened(true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void complete() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void unload() {}
/*    */ 
/*    */   
/*    */   public boolean shouldBeSerialized() {
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 67 */     return "SpawnTreasureChestTransactionRecord{worldUUID=" + String.valueOf(this.worldUUID) + ", blockPosition=" + String.valueOf(this.blockPosition) + "} " + super
/*    */ 
/*    */       
/* 70 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\transaction\SpawnTreasureChestTransactionRecord.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */