/*    */ package com.hypixel.hytale.builtin.adventure.npcreputation;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.ReputationPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.StoreSystem;
/*    */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.attitude.AttitudeView;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ReputationAttitudeSystem
/*    */   extends StoreSystem<EntityStore> {
/* 19 */   private final ResourceType<EntityStore, Blackboard> resourceType = Blackboard.getResourceType();
/*    */ 
/*    */ 
/*    */   
/*    */   public void onSystemAddedToStore(@Nonnull Store<EntityStore> store) {
/* 24 */     Blackboard blackboard = (Blackboard)store.getResource(this.resourceType);
/* 25 */     AttitudeView view = (AttitudeView)blackboard.getView(AttitudeView.class, 0L);
/* 26 */     view.registerProvider(100, (ref, role, targetRef, accessor) -> {
/*    */           Player playerComponent = (Player)store.getComponent(targetRef, Player.getComponentType());
/*    */           return (playerComponent == null) ? null : ReputationPlugin.get().getAttitude(store, targetRef, ref);
/*    */         });
/*    */   }
/*    */   
/*    */   public void onSystemRemovedFromStore(@Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcreputation\ReputationAttitudeSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */