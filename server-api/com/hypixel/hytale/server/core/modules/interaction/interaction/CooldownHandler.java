/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.common.thread.ticking.Tickable;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CooldownHandler
/*     */   implements Tickable
/*     */ {
/*     */   @Nonnull
/*  26 */   private final Map<String, Cooldown> cooldowns = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnCooldown(@Nonnull RootInteraction root, @Nonnull String id, float maxTime, @Nonnull float[] chargeTimes, boolean interruptRecharge) {
/*  38 */     if (maxTime <= 0.0F) return false;
/*     */     
/*  40 */     Cooldown cooldown = getCooldown(id, maxTime, chargeTimes, root.resetCooldownOnStart(), interruptRecharge);
/*  41 */     return (cooldown != null && cooldown.hasCooldown(true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetCooldown(@Nonnull String id, float maxTime, @Nonnull float[] chargeTimes, boolean interruptRecharge) {
/*  52 */     Cooldown cooldown = getCooldown(id, maxTime, chargeTimes, true, interruptRecharge);
/*  53 */     if (cooldown != null) {
/*  54 */       cooldown.resetCooldown();
/*  55 */       cooldown.resetCharges();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Cooldown getCooldown(@Nonnull String id, float maxTime, @Nonnull float[] chargeTimes, boolean force, boolean interruptRecharge) {
/*  70 */     return force ? this.cooldowns.computeIfAbsent(id, k -> new Cooldown(maxTime, chargeTimes, interruptRecharge)) : this.cooldowns.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Cooldown getCooldown(@Nonnull String id) {
/*  81 */     return this.cooldowns.get(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt) {
/*  86 */     this.cooldowns.values().removeIf(cooldown -> cooldown.tick(dt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Cooldown
/*     */   {
/*     */     private float cooldownMax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float[] charges;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     private float remainingCooldown = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float chargeTimer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int chargeCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean interruptRecharge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Cooldown(float cooldownMax, @Nonnull float[] charges, boolean interruptRecharge) {
/* 132 */       setCooldownMax(cooldownMax);
/* 133 */       setCharges(charges);
/* 134 */       resetCharges();
/* 135 */       this.interruptRecharge = interruptRecharge;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCooldownMax(float cooldownMax) {
/* 144 */       this.cooldownMax = cooldownMax;
/* 145 */       if (this.remainingCooldown > cooldownMax) {
/* 146 */         this.remainingCooldown = cooldownMax;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCharges(@Nonnull float[] charges) {
/* 156 */       this.charges = charges;
/* 157 */       if (this.chargeCount > charges.length) {
/* 158 */         this.chargeCount = charges.length;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasCooldown(boolean deduct) {
/* 169 */       if (this.remainingCooldown <= 0.0F && this.chargeCount > 0) {
/*     */ 
/*     */         
/* 172 */         if (deduct) {
/* 173 */           deductCharge();
/*     */         }
/* 175 */         return false;
/*     */       } 
/*     */       
/* 178 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasMaxCharges() {
/* 187 */       return (this.chargeCount >= this.charges.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void resetCharges() {
/* 194 */       this.chargeCount = this.charges.length;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void resetCooldown() {
/* 201 */       this.remainingCooldown = this.cooldownMax;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void deductCharge() {
/* 208 */       if (this.chargeCount > 0) {
/* 209 */         this.chargeCount--;
/*     */       }
/*     */       
/* 212 */       if (this.interruptRecharge) {
/* 213 */         this.chargeTimer = 0.0F;
/*     */       }
/*     */       
/* 216 */       resetCooldown();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean tick(float dt) {
/* 226 */       if (!hasMaxCharges()) {
/* 227 */         float chargeTimeMax = this.charges[this.chargeCount];
/* 228 */         this.chargeTimer += dt;
/*     */         
/* 230 */         if (this.chargeTimer >= chargeTimeMax) {
/* 231 */           this.chargeCount++;
/* 232 */           this.chargeTimer = 0.0F;
/*     */         } 
/*     */       } 
/*     */       
/* 236 */       this.remainingCooldown -= dt;
/*     */       
/* 238 */       return ((hasMaxCharges() || this.charges.length <= 1) && this.remainingCooldown <= 0.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getCooldown() {
/* 247 */       return this.cooldownMax;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float[] getCharges() {
/* 256 */       return this.charges;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean interruptRecharge() {
/* 265 */       return this.interruptRecharge;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void replenishCharge(int amount, boolean interrupt) {
/* 275 */       this.chargeCount = MathUtil.clamp(this.chargeCount + amount, 0, this.charges.length);
/*     */       
/* 277 */       if (interrupt && amount != 0) {
/* 278 */         this.chargeTimer = 0.0F;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void increaseTime(float time) {
/* 288 */       this.remainingCooldown = MathUtil.clamp(this.remainingCooldown + time, 0.0F, this.cooldownMax);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void increaseChargeTime(float time) {
/* 298 */       if (hasMaxCharges()) {
/*     */         return;
/*     */       }
/* 301 */       if (this.charges.length <= 1)
/*     */         return; 
/* 303 */       float chargeTimeMax = this.charges[this.chargeCount];
/* 304 */       this.chargeTimer = MathUtil.clamp(this.chargeTimer + time, 0.0F, chargeTimeMax);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\CooldownHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */