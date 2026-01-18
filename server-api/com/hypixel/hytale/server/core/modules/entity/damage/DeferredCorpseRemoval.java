/*    */ package com.hypixel.hytale.server.core.modules.entity.damage;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DeferredCorpseRemoval
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   protected double timeRemaining;
/*    */   
/*    */   public static ComponentType<EntityStore, DeferredCorpseRemoval> getComponentType() {
/* 14 */     return DamageModule.get().getDeferredCorpseRemovalComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DeferredCorpseRemoval(double timeUntilCorpseRemoval) {
/* 20 */     this.timeRemaining = timeUntilCorpseRemoval;
/*    */   }
/*    */   
/*    */   public boolean tick(float dt) {
/* 24 */     return ((this.timeRemaining -= dt) <= 0.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 31 */     return new DeferredCorpseRemoval(this.timeRemaining);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\DeferredCorpseRemoval.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */