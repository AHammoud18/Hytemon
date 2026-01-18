/*    */ package com.hypixel.hytale.builtin.adventure.objectives.transaction;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnEntityTransactionRecord
/*    */   extends TransactionRecord
/*    */ {
/*    */   public static final BuilderCodec<SpawnEntityTransactionRecord> CODEC;
/*    */   protected UUID worldUUID;
/*    */   protected UUID entityUUID;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SpawnEntityTransactionRecord.class, SpawnEntityTransactionRecord::new, BASE_CODEC).append(new KeyedCodec("WorldUUID", (Codec)Codec.UUID_BINARY), (spawnEntityTransactionRecord, uuid) -> spawnEntityTransactionRecord.worldUUID = uuid, spawnEntityTransactionRecord -> spawnEntityTransactionRecord.worldUUID).add()).append(new KeyedCodec("EntityUUID", (Codec)Codec.UUID_BINARY), (spawnEntityTransactionRecord, uuid) -> spawnEntityTransactionRecord.entityUUID = uuid, spawnEntityTransactionRecord -> spawnEntityTransactionRecord.entityUUID).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SpawnEntityTransactionRecord(@Nonnull UUID worldUUID, @Nonnull UUID entityUUID) {
/* 30 */     this.worldUUID = worldUUID;
/* 31 */     this.entityUUID = entityUUID;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SpawnEntityTransactionRecord() {}
/*    */ 
/*    */   
/*    */   public void revert() {
/* 39 */     removeEntity();
/*    */   }
/*    */ 
/*    */   
/*    */   public void complete() {
/* 44 */     removeEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void unload() {}
/*    */ 
/*    */   
/*    */   public boolean shouldBeSerialized() {
/* 53 */     return true;
/*    */   }
/*    */   
/*    */   private void removeEntity() {
/* 57 */     World world = Universe.get().getWorld(this.worldUUID);
/*    */     
/* 59 */     Entity entity = world.getEntity(this.entityUUID);
/* 60 */     if (entity == null)
/*    */       return; 
/* 62 */     if (!entity.remove()) {
/* 63 */       throw new RuntimeException("Failed to revert record!");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 70 */     return "SpawnEntityTransactionRecord{worldUUID=" + String.valueOf(this.worldUUID) + ", entityUUID=" + String.valueOf(this.entityUUID) + "} " + super
/*    */ 
/*    */       
/* 73 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\transaction\SpawnEntityTransactionRecord.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */