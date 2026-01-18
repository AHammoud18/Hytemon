/*    */ package com.hypixel.hytale.server.npc.components.messaging;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EventMessage
/*    */   extends NPCMessage {
/* 10 */   private final Vector3d position = new Vector3d();
/*    */   
/*    */   private final double maxRangeSquared;
/*    */   private boolean sameFlock;
/*    */   
/*    */   public EventMessage(double maxRange) {
/* 16 */     this.maxRangeSquared = maxRange * maxRange;
/*    */   }
/*    */   
/*    */   private EventMessage(@Nonnull Vector3d position, double maxRangeSquared, boolean sameFlock) {
/* 20 */     this.position.assign(position);
/* 21 */     this.maxRangeSquared = maxRangeSquared;
/* 22 */     this.sameFlock = sameFlock;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getPosition() {
/* 27 */     return this.position;
/*    */   }
/*    */   
/*    */   public double getMaxRangeSquared() {
/* 31 */     return this.maxRangeSquared;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSameFlock() {
/* 36 */     return this.sameFlock;
/*    */   }
/*    */   
/*    */   public void setSameFlock(boolean sameFlock) {
/* 40 */     this.sameFlock = sameFlock;
/*    */   }
/*    */   
/*    */   public void activate(double x, double y, double z, Ref<EntityStore> target, double age) {
/* 44 */     activate(target, age);
/* 45 */     this.position.assign(x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public EventMessage clone() {
/* 52 */     return new EventMessage(this.position, this.maxRangeSquared, this.sameFlock);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\messaging\EventMessage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */