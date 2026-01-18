/*     */ package com.hypixel.hytale.server.core.modules.entity.item;
/*     */ 
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class PickupItemComponent
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   public static final float PICKUP_TRAVEL_TIME_DEFAULT = 0.15F;
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, PickupItemComponent> getComponentType() {
/*  29 */     return EntityModule.get().getPickupItemComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  36 */   public static final BuilderCodec<PickupItemComponent> CODEC = BuilderCodec.builder(PickupItemComponent.class, PickupItemComponent::new).build();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Ref<EntityStore> targetRef;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector3d startPosition;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float originalLifeTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private float lifeTime = 0.15F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean finished = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PickupItemComponent(@Nonnull Ref<EntityStore> targetRef, @Nonnull Vector3d startPosition) {
/*  76 */     this(targetRef, startPosition, 0.15F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PickupItemComponent(@Nonnull Ref<EntityStore> targetRef, @Nonnull Vector3d startPosition, float lifeTime) {
/*  87 */     this.targetRef = targetRef;
/*  88 */     this.startPosition = startPosition;
/*  89 */     this.lifeTime = lifeTime;
/*  90 */     this.originalLifeTime = lifeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PickupItemComponent(@Nonnull PickupItemComponent pickupItemComponent) {
/*  99 */     this.targetRef = pickupItemComponent.targetRef;
/* 100 */     this.lifeTime = pickupItemComponent.lifeTime;
/* 101 */     this.startPosition = pickupItemComponent.startPosition;
/* 102 */     this.originalLifeTime = pickupItemComponent.originalLifeTime;
/* 103 */     this.finished = pickupItemComponent.finished;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFinished() {
/* 110 */     return this.finished;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFinished(boolean finished) {
/* 119 */     this.finished = finished;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decreaseLifetime(float amount) {
/* 128 */     this.lifeTime -= amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLifeTime() {
/* 135 */     return this.lifeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getOriginalLifeTime() {
/* 142 */     return this.originalLifeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInitialLifeTime(float lifeTimeS) {
/* 151 */     this.originalLifeTime = lifeTimeS;
/* 152 */     this.lifeTime = lifeTimeS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getStartPosition() {
/* 160 */     return this.startPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Ref<EntityStore> getTargetRef() {
/* 168 */     return this.targetRef;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PickupItemComponent clone() {
/* 174 */     return new PickupItemComponent(this);
/*     */   }
/*     */   
/*     */   public PickupItemComponent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\PickupItemComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */