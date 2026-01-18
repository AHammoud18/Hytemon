/*    */ package com.hypixel.hytale.server.npc.corecomponents.combat;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderSensorDamage;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.CombatSupport;
/*    */ import com.hypixel.hytale.server.npc.role.support.WorldSupport;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.EntityPositionProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.DamageData;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorDamage
/*    */   extends SensorBase {
/*    */   protected final boolean combatDamage;
/*    */   protected final boolean friendlyDamage;
/*    */   protected final boolean drowningDamage;
/* 26 */   protected final EntityPositionProvider positionProvider = new EntityPositionProvider(); protected final boolean environmentDamage; protected final boolean otherDamage; protected final int targetSlot;
/*    */   
/*    */   public SensorDamage(@Nonnull BuilderSensorDamage builder, @Nonnull BuilderSupport support) {
/* 29 */     super((BuilderSensorBase)builder);
/* 30 */     this.combatDamage = builder.isCombatDamage();
/* 31 */     this.friendlyDamage = builder.isFriendlyDamage();
/* 32 */     this.drowningDamage = builder.isDrowningDamage();
/* 33 */     this.environmentDamage = builder.isEnvironmentDamage();
/* 34 */     this.otherDamage = builder.isOtherDamage();
/* 35 */     this.targetSlot = builder.getTargetSlot(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 40 */     if (!super.matches(ref, role, dt, store)) {
/* 41 */       this.positionProvider.clear();
/* 42 */       return false;
/*    */     } 
/*    */     
/* 45 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 46 */     assert npcComponent != null;
/*    */     
/* 48 */     CombatSupport combatSupport = role.getCombatSupport();
/* 49 */     DamageData damageData = npcComponent.getDamageData();
/* 50 */     if (this.combatDamage) {
/*    */       
/* 52 */       Ref<EntityStore> attackerRef = damageData.getMostDamagingAttacker();
/* 53 */       if (attackerRef == null) {
/* 54 */         attackerRef = damageData.getAnyAttacker();
/*    */       }
/*    */       
/* 57 */       attackerRef = this.positionProvider.setTarget(attackerRef, (ComponentAccessor)store);
/* 58 */       if (attackerRef != null) {
/*    */         
/* 60 */         if (this.friendlyDamage) {
/*    */           
/* 62 */           int[] damageGroups = combatSupport.getDisableDamageGroups();
/* 63 */           if (!WorldSupport.isGroupMember(npcComponent.getRoleIndex(), attackerRef, damageGroups, (ComponentAccessor)store)) {
/* 64 */             return false;
/*    */           }
/*    */         } 
/*    */         
/* 68 */         if (this.targetSlot >= 0) {
/* 69 */           role.getMarkedEntitySupport().setMarkedEntity(this.targetSlot, attackerRef);
/*    */         }
/* 71 */         return true;
/* 72 */       }  if (damageData.hasSufferedDamage(DamageCause.PHYSICAL) || damageData.hasSufferedDamage(DamageCause.PROJECTILE)) {
/* 73 */         this.positionProvider.clear();
/* 74 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 78 */     this.positionProvider.clear();
/*    */     
/* 80 */     return ((this.drowningDamage && damageData.hasSufferedDamage(DamageCause.DROWNING)) || (this.environmentDamage && damageData
/* 81 */       .hasSufferedDamage(DamageCause.ENVIRONMENT)) || (this.otherDamage && (damageData
/* 82 */       .hasSufferedDamage(DamageCause.FALL) || damageData.hasSufferedDamage(DamageCause.OUT_OF_WORLD) || damageData
/* 83 */       .hasSufferedDamage(DamageCause.SUFFOCATION) || damageData.hasSufferedDamage(DamageCause.COMMAND))));
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 88 */     return (InfoProvider)this.positionProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\combat\SensorDamage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */