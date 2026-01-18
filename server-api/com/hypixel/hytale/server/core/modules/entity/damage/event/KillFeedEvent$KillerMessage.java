/*    */ package com.hypixel.hytale.server.core.modules.entity.damage.event;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
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
/*    */ public final class KillerMessage
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final Damage damage;
/*    */   @Nonnull
/*    */   private final Ref<EntityStore> targetRef;
/*    */   @Nullable
/* 42 */   private Message message = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public KillerMessage(@Nonnull Damage damage, @Nonnull Ref<EntityStore> targetRef) {
/* 52 */     this.damage = damage;
/* 53 */     this.targetRef = targetRef;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Damage getDamage() {
/* 61 */     return this.damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Ref<EntityStore> getTargetRef() {
/* 69 */     return this.targetRef;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMessage(@Nullable Message message) {
/* 78 */     this.message = message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Message getMessage() {
/* 86 */     return this.message;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\event\KillFeedEvent$KillerMessage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */