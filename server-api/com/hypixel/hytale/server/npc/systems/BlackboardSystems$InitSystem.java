/*    */ package com.hypixel.hytale.server.npc.systems;
/*    */ 
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.StoreSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InitSystem
/*    */   extends StoreSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final ResourceType<EntityStore, Blackboard> resourceType;
/*    */   
/*    */   public InitSystem(@Nonnull ResourceType<EntityStore, Blackboard> resourceType) {
/* 41 */     this.resourceType = resourceType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onSystemAddedToStore(@Nonnull Store<EntityStore> store) {
/* 46 */     ((Blackboard)store.getResource(this.resourceType)).init(((EntityStore)store.getExternalData()).getWorld());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onSystemRemovedFromStore(@Nonnull Store<EntityStore> store) {
/* 51 */     ((Blackboard)store.getResource(this.resourceType)).onWorldRemoved();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\BlackboardSystems$InitSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */