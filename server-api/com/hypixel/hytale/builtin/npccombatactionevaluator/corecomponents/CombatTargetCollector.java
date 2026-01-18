/*     */ package com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemory;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemorySystems;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ISensorEntityCollector;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CombatTargetCollector implements ISensorEntityCollector {
/*  21 */   private static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   
/*     */   @Nullable
/*     */   private Role role;
/*     */   
/*     */   @Nullable
/*     */   private TargetMemory targetMemory;
/*     */   
/*  29 */   private double closestHostileDistanceSquared = Double.MAX_VALUE;
/*     */ 
/*     */   
/*     */   public void registerWithSupport(@Nonnull Role role) {
/*  33 */     role.getWorldSupport().requireAttitudeCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  38 */     this.targetMemory = (TargetMemory)componentAccessor.getComponent(ref, TargetMemory.getComponentType());
/*  39 */     this.role = role; } public void collectMatching(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) { Int2FloatOpenHashMap hostiles, friendlies; TransformComponent transformComponent;
/*     */     Vector3d selfPos;
/*     */     TransformComponent targetTransformComponent;
/*     */     Vector3d targetPos;
/*     */     double distanceSquared;
/*  44 */     if (this.targetMemory == null)
/*     */       return; 
/*  46 */     Attitude attitude = this.role.getWorldSupport().getAttitude(ref, targetRef, componentAccessor);
/*  47 */     switch (attitude) {
/*     */ 
/*     */       
/*     */       case HOSTILE:
/*  51 */         hostiles = this.targetMemory.getKnownHostiles();
/*  52 */         if (hostiles.put(targetRef.getIndex(), this.targetMemory.getRememberFor()) <= 0.0F) {
/*  53 */           this.targetMemory.getKnownHostilesList().add(targetRef);
/*  54 */           HytaleLogger.Api context = TargetMemorySystems.LOGGER.at(Level.FINE);
/*  55 */           if (context.isEnabled()) {
/*  56 */             context.log("%s: Registered new hostile %s", ref, targetRef);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  61 */         transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/*  62 */         assert transformComponent != null;
/*  63 */         selfPos = transformComponent.getPosition();
/*     */         
/*  65 */         targetTransformComponent = (TransformComponent)componentAccessor.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/*  66 */         assert targetTransformComponent != null;
/*  67 */         targetPos = targetTransformComponent.getPosition();
/*     */         
/*  69 */         distanceSquared = selfPos.distanceSquaredTo(targetPos);
/*  70 */         if (distanceSquared < this.closestHostileDistanceSquared) {
/*  71 */           this.targetMemory.setClosestHostile(targetRef);
/*  72 */           this.closestHostileDistanceSquared = distanceSquared;
/*     */         }  break;
/*     */       case FRIENDLY:
/*     */       case REVERED:
/*  76 */         friendlies = this.targetMemory.getKnownFriendlies();
/*  77 */         if (friendlies.put(targetRef.getIndex(), this.targetMemory.getRememberFor()) <= 0.0F) {
/*  78 */           this.targetMemory.getKnownFriendliesList().add(targetRef);
/*  79 */           HytaleLogger.Api context = TargetMemorySystems.LOGGER.at(Level.FINE);
/*  80 */           if (context.isEnabled()) {
/*  81 */             context.log("%s: Registered new friendly %s", ref, targetRef);
/*     */           }
/*     */         } 
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void collectNonMatching(@Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*     */ 
/*     */   
/*     */   public boolean terminateOnFirstMatch() {
/*  94 */     return (this.targetMemory == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanup() {
/*  99 */     this.role = null;
/* 100 */     this.targetMemory = null;
/* 101 */     this.closestHostileDistanceSquared = Double.MAX_VALUE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\corecomponents\CombatTargetCollector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */