/*    */ package com.hypixel.hytale.server.npc.corecomponents.combat;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*    */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderActionApplyEntityEffect;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
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
/*    */ public class ActionApplyEntityEffect
/*    */   extends ActionBase
/*    */ {
/*    */   protected final int entityEffectId;
/*    */   protected final boolean useTarget;
/*    */   
/*    */   public ActionApplyEntityEffect(@Nonnull BuilderActionApplyEntityEffect builder, @Nonnull BuilderSupport support) {
/* 39 */     super((BuilderActionBase)builder);
/* 40 */     this.entityEffectId = builder.getEntityEffect(support);
/* 41 */     this.useTarget = builder.isUseTarget(support);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 47 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && (!this.useTarget || (sensorInfo != null && sensorInfo.hasPosition())));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 52 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 54 */     Ref<EntityStore> targetRef = this.useTarget ? sensorInfo.getPositionProvider().getTarget() : ref;
/* 55 */     if (targetRef == null || !targetRef.isValid()) {
/* 56 */       return true;
/*    */     }
/*    */     
/* 59 */     EntityEffect entityEffect = (EntityEffect)EntityEffect.getAssetMap().getAsset(this.entityEffectId);
/* 60 */     if (entityEffect != null) {
/* 61 */       EffectControllerComponent effectControllerComponent = (EffectControllerComponent)store.getComponent(targetRef, EffectControllerComponent.getComponentType());
/* 62 */       if (effectControllerComponent != null) {
/* 63 */         effectControllerComponent.addEffect(targetRef, this.entityEffectId, entityEffect, (ComponentAccessor)store);
/*    */       }
/*    */     } 
/* 66 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\combat\ActionApplyEntityEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */