/*     */ package com.hypixel.hytale.server.npc.role.support;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.builders.BuilderRole;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CombatSupport
/*     */ {
/*     */   public static final String ATTACK_TAG = "Attack";
/*  26 */   public static final int ATTACK_TAG_INDEX = AssetRegistry.getOrCreateTagIndex("Attack");
/*     */   public static final String AIMING_REFERENCE_TAG = "AimingReference";
/*  28 */   public static final int AIMING_REFERENCE_TAG_INDEX = AssetRegistry.getOrCreateTagIndex("AimingReference");
/*     */   public static final String MELEE_TAG = "Attack=Melee";
/*  30 */   public static final int MELEE_TAG_INDEX = AssetRegistry.getOrCreateTagIndex("Attack=Melee");
/*     */   public static final String RANGED_TAG = "Attack=Ranged";
/*  32 */   public static final int RANGED_TAG_INDEX = AssetRegistry.getOrCreateTagIndex("Attack=Ranged");
/*     */   public static final String BLOCK_TAG = "Attack=Block";
/*  34 */   public static final int BLOCK_TAG_INDEX = AssetRegistry.getOrCreateTagIndex("Attack=Block");
/*     */   
/*     */   protected final NPCEntity parent;
/*     */   
/*     */   protected final boolean disableDamageFlock;
/*     */   
/*     */   protected final int[] disableDamageGroups;
/*     */   
/*     */   @Nullable
/*     */   protected InteractionChain activeAttack;
/*     */   protected boolean dealFriendlyDamage;
/*     */   protected double attackPause;
/*  46 */   protected final List<String> attackOverrides = (List<String>)new ObjectArrayList();
/*  47 */   protected int attackOverrideIndex = -1;
/*     */   
/*     */   public CombatSupport(NPCEntity parent, @Nonnull BuilderRole builder, @Nonnull BuilderSupport support) {
/*  50 */     this.parent = parent;
/*  51 */     this.disableDamageFlock = builder.isDisableDamageFlock();
/*  52 */     this.disableDamageGroups = builder.getDisableDamageGroups(support);
/*     */   }
/*     */   
/*     */   public boolean isDealingFriendlyDamage() {
/*  56 */     return this.dealFriendlyDamage;
/*     */   }
/*     */   
/*     */   public int[] getDisableDamageGroups() {
/*  60 */     return this.disableDamageGroups;
/*     */   }
/*     */   
/*     */   public boolean isExecutingAttack() {
/*  64 */     return (this.attackPause > 0.0D || this.activeAttack != null);
/*     */   }
/*     */   
/*     */   public void tick(double dt) {
/*  68 */     if (this.attackPause > 0.0D) {
/*  69 */       this.attackPause -= dt;
/*     */     }
/*  71 */     if (this.activeAttack != null && this.activeAttack.getServerState() != InteractionState.NotFinished) {
/*  72 */       this.activeAttack = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getCanCauseDamage(@Nonnull Ref<EntityStore> attackerRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  77 */     if (this.disableDamageFlock) {
/*  78 */       Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(this.parent.getReference(), (ComponentAccessor)((EntityStore)componentAccessor.getExternalData()).getStore());
/*  79 */       if (flockReference != null) {
/*  80 */         EntityGroup entityGroupComponent = (EntityGroup)componentAccessor.getComponent(flockReference, EntityGroup.getComponentType());
/*  81 */         if (entityGroupComponent != null && entityGroupComponent.isMember(attackerRef)) {
/*  82 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     boolean friendlyDamage = false;
/*     */     
/*  89 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(attackerRef, NPCEntity.getComponentType());
/*  90 */     if (npcComponent != null) {
/*  91 */       friendlyDamage = npcComponent.getRole().getCombatSupport().isDealingFriendlyDamage();
/*     */     }
/*     */     
/*  94 */     return (friendlyDamage || !WorldSupport.isGroupMember(this.parent.getRoleIndex(), attackerRef, this.disableDamageGroups, componentAccessor));
/*     */   }
/*     */   
/*     */   public void setExecutingAttack(InteractionChain chain, boolean damageFriendlies, double attackPause) {
/*  98 */     this.activeAttack = chain;
/*  99 */     this.dealFriendlyDamage = damageFriendlies;
/* 100 */     this.attackPause = attackPause;
/*     */   }
/*     */   
/*     */   public void addAttackOverride(String attackSequence) {
/* 104 */     this.attackOverrides.add(attackSequence);
/* 105 */     this.attackOverrideIndex = 0;
/*     */   }
/*     */   
/*     */   public void clearAttackOverrides() {
/* 109 */     this.attackOverrides.clear();
/* 110 */     this.attackOverrideIndex = -1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getNextAttackOverride() {
/* 115 */     if (this.attackOverrideIndex == -1) return null;
/*     */     
/* 117 */     int index = this.attackOverrideIndex;
/* 118 */     this.attackOverrideIndex = (this.attackOverrideIndex < this.attackOverrides.size() - 1) ? (this.attackOverrideIndex + 1) : 0;
/* 119 */     return this.attackOverrides.get(index);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\support\CombatSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */