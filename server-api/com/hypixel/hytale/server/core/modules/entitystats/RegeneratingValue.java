/*    */ package com.hypixel.hytale.server.core.modules.entitystats;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.condition.Condition;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.modifier.RegeneratingModifier;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegeneratingValue
/*    */ {
/*    */   @Nonnull
/*    */   private final EntityStatType.Regenerating regenerating;
/*    */   private float remainingUntilRegen;
/*    */   
/*    */   public RegeneratingValue(@Nonnull EntityStatType.Regenerating regenerating) {
/* 21 */     this.regenerating = regenerating;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldRegenerate(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime, float dt, @Nonnull EntityStatType.Regenerating regenerating) {
/* 29 */     this.remainingUntilRegen -= dt;
/* 30 */     if (this.remainingUntilRegen < 0.0F) {
/* 31 */       this.remainingUntilRegen += regenerating.getInterval();
/* 32 */       return Condition.allConditionsMet(store, ref, currentTime, regenerating);
/*    */     } 
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float regenerate(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime, float dt, @Nonnull EntityStatValue value, float currentAmount) {
/* 46 */     if (!shouldRegenerate(store, ref, currentTime, dt, this.regenerating)) return 0.0F;
/*    */     
/* 48 */     switch (this.regenerating.getRegenType()) { default: throw new MatchException(null, null);case ADDITIVE: case PERCENTAGE: break; }  float toAdd = 
/*    */       
/* 50 */       this.regenerating.getAmount() * (value.getMax() - value.getMin());
/*    */ 
/*    */     
/* 53 */     if (this.regenerating.getModifiers() != null) {
/* 54 */       for (RegeneratingModifier modifier : this.regenerating.getModifiers()) {
/* 55 */         toAdd *= modifier.getModifier(store, ref, currentTime);
/*    */       }
/*    */     }
/*    */     
/* 59 */     return this.regenerating.clampAmount(toAdd, currentAmount, value);
/*    */   }
/*    */   
/*    */   public EntityStatType.Regenerating getRegenerating() {
/* 63 */     return this.regenerating;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 69 */     return "RegeneratingValue{regenerating=" + String.valueOf(this.regenerating) + ", remainingUntilRegen=" + this.remainingUntilRegen + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\RegeneratingValue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */