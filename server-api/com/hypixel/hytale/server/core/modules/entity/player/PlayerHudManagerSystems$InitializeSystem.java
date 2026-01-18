/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InitializeSystem
/*    */   extends RefSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 26 */   private static final ComponentType<EntityStore, PlayerRef> PLAYER_REF_COMPONENT_TYPE = PlayerRef.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 32 */   private static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 38 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)PLAYER_REF_COMPONENT_TYPE, (Query)PLAYER_COMPONENT_TYPE });
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 43 */     return QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 48 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, PLAYER_COMPONENT_TYPE);
/* 49 */     assert playerComponent != null;
/*    */     
/* 51 */     PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PLAYER_REF_COMPONENT_TYPE);
/* 52 */     assert playerRefComponent != null;
/*    */     
/* 54 */     HudManager hudManager = playerComponent.getHudManager();
/* 55 */     PacketHandler packetHandler = playerRefComponent.getPacketHandler();
/* 56 */     hudManager.sendVisibleHudComponents(packetHandler);
/*    */   }
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerHudManagerSystems$InitializeSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */