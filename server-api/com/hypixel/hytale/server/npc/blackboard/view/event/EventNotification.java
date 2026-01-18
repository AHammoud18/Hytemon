/*    */ package com.hypixel.hytale.server.npc.blackboard.view.event;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EventNotification
/*    */ {
/* 10 */   private final Vector3d position = new Vector3d();
/*    */   private Ref<EntityStore> initiator;
/*    */   private int set;
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getPosition() {
/* 16 */     return this.position;
/*    */   }
/*    */   
/*    */   public void setPosition(double x, double y, double z) {
/* 20 */     this.position.assign(x, y, z);
/*    */   }
/*    */   
/*    */   public Ref<EntityStore> getInitiator() {
/* 24 */     return this.initiator;
/*    */   }
/*    */   
/*    */   public void setInitiator(Ref<EntityStore> initiator) {
/* 28 */     this.initiator = initiator;
/*    */   }
/*    */   
/*    */   public int getSet() {
/* 32 */     return this.set;
/*    */   }
/*    */   
/*    */   public void setSet(int set) {
/* 36 */     this.set = set;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\event\EventNotification.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */