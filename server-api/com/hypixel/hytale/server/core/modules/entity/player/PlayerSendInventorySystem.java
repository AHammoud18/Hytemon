/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerSendInventorySystem
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, Player> componentType;
/*    */   @Nonnull
/* 30 */   private final ComponentType<EntityStore, PlayerRef> refComponentType = PlayerRef.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerSendInventorySystem(ComponentType<EntityStore, Player> componentType) {
/* 44 */     this.componentType = componentType;
/* 45 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)this.refComponentType });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 51 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 56 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 61 */     Player playerComponent = (Player)archetypeChunk.getComponent(index, this.componentType);
/* 62 */     assert playerComponent != null;
/*    */     
/* 64 */     Inventory inventory = playerComponent.getInventory();
/* 65 */     if (inventory.consumeIsDirty()) {
/*    */       
/* 67 */       PlayerRef playerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, this.refComponentType);
/* 68 */       assert playerRefComponent != null;
/* 69 */       playerRefComponent.getPacketHandler().write((Packet)inventory.toPacket());
/*    */     } 
/*    */     
/* 72 */     playerComponent.getWindowManager().updateWindows();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerSendInventorySystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */