/*    */ package com.hypixel.hytale.server.core.modules.entity.stamina;
/*    */ 
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SprintStaminaRegenDelay
/*    */   implements Resource<EntityStore>
/*    */ {
/*    */   public static ResourceType<EntityStore, SprintStaminaRegenDelay> getResourceType() {
/* 13 */     return StaminaModule.get().getSprintRegenDelayResourceType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 20 */   private static final AtomicInteger ASSET_VALIDATION_STATE = new AtomicInteger(0);
/*    */   
/* 22 */   protected int statIndex = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float statValue;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   protected int validationState = ASSET_VALIDATION_STATE.get() - 1;
/*    */ 
/*    */ 
/*    */   
/*    */   public SprintStaminaRegenDelay(@Nonnull SprintStaminaRegenDelay other) {
/* 37 */     this.statIndex = other.statIndex;
/* 38 */     this.statValue = other.statValue;
/* 39 */     this.validationState = other.validationState;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 43 */     return this.statIndex;
/*    */   }
/*    */   
/*    */   public float getValue() {
/* 47 */     return this.statValue;
/*    */   }
/*    */   
/*    */   public boolean validate() {
/* 51 */     return (this.validationState == ASSET_VALIDATION_STATE.get());
/*    */   }
/*    */   
/*    */   public boolean hasDelay() {
/* 55 */     return (this.statIndex != 0 && this.statValue < 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void markEmpty() {
/* 62 */     update(0, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(int statIndex, float statValue) {
/* 72 */     this.statIndex = statIndex;
/* 73 */     this.statValue = statValue;
/* 74 */     this.validationState = ASSET_VALIDATION_STATE.get();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<EntityStore> clone() {
/* 80 */     return new SprintStaminaRegenDelay(this);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 86 */     return "SprintStaminaRegenDelay{statIndex=" + this.statIndex + ", statValue=" + this.statValue + ", validationState=" + this.validationState + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void invalidateResources() {
/* 94 */     ASSET_VALIDATION_STATE.incrementAndGet();
/*    */   }
/*    */   
/*    */   public SprintStaminaRegenDelay() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\stamina\SprintStaminaRegenDelay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */