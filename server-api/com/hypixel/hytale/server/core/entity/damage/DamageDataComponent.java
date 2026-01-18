/*     */ package com.hypixel.hytale.server.core.entity.damage;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.WieldingInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Instant;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DamageDataComponent
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, DamageDataComponent> getComponentType() {
/*  23 */     return EntityModule.get().getDamageDataComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  29 */   private Instant lastCombatAction = Instant.MIN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  35 */   private Instant lastDamageTime = Instant.MIN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private WieldingInteraction currentWielding;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Instant lastChargeTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Instant getLastCombatAction() {
/*  55 */     return this.lastCombatAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastCombatAction(@Nonnull Instant lastCombatAction) {
/*  64 */     this.lastCombatAction = lastCombatAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Instant getLastDamageTime() {
/*  72 */     return this.lastDamageTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastDamageTime(@Nonnull Instant lastDamageTime) {
/*  81 */     this.lastDamageTime = lastDamageTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Instant getLastChargeTime() {
/*  89 */     return this.lastChargeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastChargeTime(@Nonnull Instant lastChargeTime) {
/*  98 */     this.lastChargeTime = lastChargeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WieldingInteraction getCurrentWielding() {
/* 106 */     return this.currentWielding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentWielding(@Nullable WieldingInteraction currentWielding) {
/* 115 */     this.currentWielding = currentWielding;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 121 */     DamageDataComponent damageDataComponent = new DamageDataComponent();
/* 122 */     damageDataComponent.lastCombatAction = this.lastCombatAction;
/* 123 */     damageDataComponent.lastDamageTime = this.lastDamageTime;
/* 124 */     damageDataComponent.currentWielding = this.currentWielding;
/* 125 */     damageDataComponent.lastChargeTime = this.lastChargeTime;
/* 126 */     return damageDataComponent;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\damage\DamageDataComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */