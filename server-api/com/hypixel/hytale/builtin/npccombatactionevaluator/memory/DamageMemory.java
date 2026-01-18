/*    */ package com.hypixel.hytale.builtin.npccombatactionevaluator.memory;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.NPCCombatActionEvaluatorPlugin;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DamageMemory
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   private float recentDamage;
/*    */   private float totalCombatDamage;
/*    */   
/*    */   public static ComponentType<EntityStore, DamageMemory> getComponentType() {
/* 18 */     return NPCCombatActionEvaluatorPlugin.get().getDamageMemoryComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getRecentDamage() {
/* 25 */     return this.recentDamage;
/*    */   }
/*    */   
/*    */   public float getTotalCombatDamage() {
/* 29 */     return this.totalCombatDamage;
/*    */   }
/*    */   
/*    */   public void addDamage(float damage) {
/* 33 */     this.totalCombatDamage += damage;
/* 34 */     this.recentDamage += damage;
/*    */   }
/*    */   
/*    */   public void clearRecentDamage() {
/* 38 */     this.recentDamage = 0.0F;
/*    */   }
/*    */   
/*    */   public void clearTotalDamage() {
/* 42 */     this.totalCombatDamage = 0.0F;
/* 43 */     clearRecentDamage();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 50 */     DamageMemory damageMemory = new DamageMemory();
/* 51 */     damageMemory.recentDamage = this.recentDamage;
/* 52 */     damageMemory.totalCombatDamage = this.totalCombatDamage;
/* 53 */     return damageMemory;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "DamageMemory{recentDamage=" + this.recentDamage + ", totalCombatDamage=" + this.totalCombatDamage + "}" + super
/*    */ 
/*    */       
/* 62 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\memory\DamageMemory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */