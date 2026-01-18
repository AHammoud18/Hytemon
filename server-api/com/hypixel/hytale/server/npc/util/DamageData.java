/*     */ package com.hypixel.hytale.server.npc.util;
/*     */ 
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DamageData
/*     */ {
/*  26 */   private final Map<Ref<EntityStore>, Vector3d> kills = new HashMap<>();
/*  27 */   private final Object2DoubleMap<Ref<EntityStore>> damageInflicted = (Object2DoubleMap<Ref<EntityStore>>)new Object2DoubleOpenHashMap();
/*  28 */   private final Object2DoubleMap<Ref<EntityStore>> damageSuffered = (Object2DoubleMap<Ref<EntityStore>>)new Object2DoubleOpenHashMap();
/*  29 */   private final Object2DoubleMap<DamageCause> damageByCause = (Object2DoubleMap<DamageCause>)new Object2DoubleOpenHashMap();
/*     */   private double maxDamageSuffered;
/*     */   private double maxDamageInflicted;
/*     */   @Nullable
/*     */   private Ref<EntityStore> mostPersistentAttacker;
/*     */   @Nullable
/*     */   private Ref<EntityStore> mostDamagedVictim;
/*     */   
/*     */   public DamageData() {
/*  38 */     reset();
/*     */   }
/*     */   
/*     */   public void reset() {
/*  42 */     this.kills.clear();
/*  43 */     this.damageInflicted.clear();
/*  44 */     this.damageSuffered.clear();
/*  45 */     this.damageByCause.clear();
/*  46 */     this.maxDamageInflicted = 0.0D;
/*  47 */     this.maxDamageSuffered = 0.0D;
/*  48 */     this.mostPersistentAttacker = null;
/*  49 */     this.mostDamagedVictim = null;
/*     */   }
/*     */   
/*     */   public void onInflictedDamage(Ref<EntityStore> target, double amount) {
/*  53 */     double d = this.damageInflicted.mergeDouble(target, amount, Double::sum);
/*  54 */     if (d > this.maxDamageInflicted) {
/*  55 */       this.maxDamageInflicted = d;
/*  56 */       this.mostDamagedVictim = target;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onSufferedDamage(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Damage damage) {
/*  61 */     this.damageByCause.mergeDouble(damage.getCause(), damage.getAmount(), Double::sum);
/*  62 */     if (!(damage.getSource() instanceof Damage.EntitySource))
/*     */       return; 
/*  64 */     Ref<EntityStore> ref = ((Damage.EntitySource)damage.getSource()).getRef();
/*  65 */     if (!ref.isValid())
/*     */       return; 
/*  67 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  68 */     if (playerComponent != null && playerComponent.getGameMode() == GameMode.Creative) {
/*     */       
/*  70 */       PlayerSettings playerSettingsComponent = (PlayerSettings)commandBuffer.getComponent(ref, PlayerSettings.getComponentType());
/*  71 */       if (playerSettingsComponent == null || !playerSettingsComponent.creativeSettings().allowNPCDetection())
/*     */         return; 
/*     */     } 
/*  74 */     double damageByEntity = this.damageSuffered.mergeDouble(ref, damage.getAmount(), Double::sum);
/*  75 */     if (damageByEntity > this.maxDamageSuffered) {
/*  76 */       this.maxDamageSuffered = damageByEntity;
/*  77 */       this.mostPersistentAttacker = ref;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onKill(@Nonnull Ref<EntityStore> victim, @Nonnull Vector3d position) {
/*  82 */     this.kills.put(victim, position);
/*     */   }
/*     */   
/*     */   public boolean haveKill() {
/*  86 */     return !this.kills.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean haveKilled(Ref<EntityStore> entity) {
/*  90 */     return this.kills.containsKey(entity);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getAnyKilled() {
/*  95 */     if (this.kills.isEmpty()) return null; 
/*  96 */     for (Ref<EntityStore> kill : this.kills.keySet()) {
/*  97 */       if (kill.isValid()) return kill; 
/*     */     } 
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public Vector3d getKillPosition(Ref<EntityStore> entity) {
/* 103 */     return this.kills.get(entity);
/*     */   }
/*     */   
/*     */   public double getMaxDamageInflicted() {
/* 107 */     return this.maxDamageInflicted;
/*     */   }
/*     */   
/*     */   public double getMaxDamageSuffered() {
/* 111 */     return this.maxDamageSuffered;
/*     */   }
/*     */   
/*     */   public double getDamage(DamageCause cause) {
/* 115 */     return this.damageByCause.getDouble(cause);
/*     */   }
/*     */   
/*     */   public boolean hasSufferedDamage(DamageCause cause) {
/* 119 */     return this.damageByCause.containsKey(cause);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getMostDamagedVictim() {
/* 124 */     return (this.mostDamagedVictim != null && this.mostDamagedVictim.isValid()) ? this.mostDamagedVictim : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getMostDamagingAttacker() {
/* 129 */     return (this.mostPersistentAttacker != null && this.mostPersistentAttacker.isValid()) ? this.mostPersistentAttacker : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getAnyAttacker() {
/* 134 */     if (this.damageSuffered.isEmpty()) return null;
/*     */     
/* 136 */     for (ObjectIterator<Ref<EntityStore>> objectIterator = this.damageSuffered.keySet().iterator(); objectIterator.hasNext(); ) { Ref<EntityStore> attacker = objectIterator.next();
/* 137 */       if (attacker.isValid()) return attacker;  }
/*     */     
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public DamageData clone() {
/* 146 */     DamageData damageData = new DamageData();
/* 147 */     damageData.kills.putAll(this.kills);
/* 148 */     damageData.damageInflicted.putAll((Map)this.damageInflicted);
/* 149 */     damageData.damageSuffered.putAll((Map)this.damageSuffered);
/* 150 */     damageData.damageByCause.putAll((Map)this.damageByCause);
/* 151 */     damageData.maxDamageSuffered = this.maxDamageSuffered;
/* 152 */     damageData.maxDamageInflicted = this.maxDamageInflicted;
/* 153 */     damageData.mostPersistentAttacker = this.mostPersistentAttacker;
/* 154 */     damageData.mostDamagedVictim = this.mostDamagedVictim;
/* 155 */     return damageData;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 161 */     return "DamageData{kills=" + String.valueOf(this.kills) + ", damageInflicted=" + String.valueOf(this.damageInflicted) + ", damageSuffered=" + String.valueOf(this.damageSuffered) + ", damageByCause=" + String.valueOf(this.damageByCause) + ", maxDamageSuffered=" + this.maxDamageSuffered + ", maxDamageInflicted=" + this.maxDamageInflicted + ", mostPersistentAttacker=" + String.valueOf(this.mostPersistentAttacker) + ", mostDamagedVictim=" + String.valueOf(this.mostDamagedVictim) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\DamageData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */