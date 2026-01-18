/*    */ package com.hypixel.hytale.server.npc.components.messaging;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class NPCMessage
/*    */ {
/*    */   public static final double AGE_INFINITE = -1.0D;
/*    */   private boolean enabled = true;
/*    */   private boolean activated = false;
/*    */   private double age;
/*    */   private Ref<EntityStore> target;
/*    */   
/*    */   public boolean tickAge(float dt) {
/* 28 */     return ((this.age -= dt) <= 0.0D);
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 32 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 36 */     this.enabled = enabled;
/*    */   }
/*    */   
/*    */   public boolean isActivated() {
/* 40 */     return this.activated;
/*    */   }
/*    */   
/*    */   public boolean isInfinite() {
/* 44 */     return (this.age == -1.0D);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> getTarget() {
/* 49 */     return (this.target != null && this.target.isValid()) ? this.target : null;
/*    */   }
/*    */   
/*    */   public void activate(Ref<EntityStore> target, double age) {
/* 53 */     this.age = age;
/* 54 */     this.activated = true;
/* 55 */     this.target = target;
/*    */   }
/*    */   
/*    */   public void deactivate() {
/* 59 */     this.activated = false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NPCMessage clone() {
/* 66 */     NPCMessage message = new NPCMessage();
/* 67 */     message.enabled = this.enabled;
/* 68 */     message.activated = this.activated;
/* 69 */     message.age = this.age;
/* 70 */     message.target = this.target;
/* 71 */     return message;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\messaging\NPCMessage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */