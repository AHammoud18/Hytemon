/*    */ package com.hypixel.hytale.server.npc.blackboard.view.event;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ public class EntityEventNotification extends EventNotification {
/*    */   private Ref<EntityStore> flockReference;
/*    */   
/*    */   public Ref<EntityStore> getFlockReference() {
/* 10 */     return this.flockReference;
/*    */   }
/*    */   
/*    */   public void setFlockReference(Ref<EntityStore> flockReference) {
/* 14 */     this.flockReference = flockReference;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\event\EntityEventNotification.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */