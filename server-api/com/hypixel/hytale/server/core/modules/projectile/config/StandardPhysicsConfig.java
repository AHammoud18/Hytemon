/*     */ package com.hypixel.hytale.server.core.modules.projectile.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.PhysicsConfig;
/*     */ import com.hypixel.hytale.protocol.PhysicsType;
/*     */ import com.hypixel.hytale.protocol.RotationMode;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
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
/*     */ public class StandardPhysicsConfig
/*     */   implements PhysicsConfig
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<StandardPhysicsConfig> CODEC;
/*     */   
/*     */   static {
/* 185 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StandardPhysicsConfig.class, StandardPhysicsConfig::new).appendInherited(new KeyedCodec("Density", (Codec)Codec.DOUBLE), (o, i) -> o.density = i.doubleValue(), o -> Double.valueOf(o.density), (o, p) -> o.density = p.density).add()).appendInherited(new KeyedCodec("Gravity", (Codec)Codec.DOUBLE), (o, i) -> o.gravity = i.doubleValue(), o -> Double.valueOf(o.gravity), (o, p) -> o.gravity = p.gravity).add()).appendInherited(new KeyedCodec("Bounciness", (Codec)Codec.DOUBLE), (o, i) -> o.bounciness = i.doubleValue(), o -> Double.valueOf(o.bounciness), (o, p) -> o.bounciness = p.bounciness).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).add()).appendInherited(new KeyedCodec("BounceLimit", (Codec)Codec.DOUBLE), (o, i) -> o.bounceLimit = i.doubleValue(), o -> Double.valueOf(o.bounceLimit), (o, p) -> o.bounceLimit = p.bounceLimit).add()).appendInherited(new KeyedCodec("BounceCount", (Codec)Codec.INTEGER), (o, i) -> o.bounceCount = i.intValue(), o -> Integer.valueOf(o.bounceCount), (o, p) -> o.bounceCount = p.bounceCount).add()).appendInherited(new KeyedCodec("SticksVertically", (Codec)Codec.BOOLEAN), (o, i) -> o.sticksVertically = i.booleanValue(), o -> Boolean.valueOf(o.sticksVertically), (o, p) -> o.sticksVertically = p.sticksVertically).add()).appendInherited(new KeyedCodec("ComputeYaw", (Codec)Codec.BOOLEAN), (o, i) -> o.computeYaw = i.booleanValue(), o -> Boolean.valueOf(o.computeYaw), (o, p) -> o.computeYaw = p.computeYaw).add()).appendInherited(new KeyedCodec("ComputePitch", (Codec)Codec.BOOLEAN), (o, i) -> o.computePitch = i.booleanValue(), o -> Boolean.valueOf(o.computePitch), (o, p) -> o.computePitch = p.computePitch).add()).appendInherited(new KeyedCodec("RotationMode", (Codec)new EnumCodec(RotationMode.class)), (o, i) -> o.rotationMode = i, o -> o.rotationMode, (o, p) -> o.rotationMode = p.rotationMode).add()).appendInherited(new KeyedCodec("MoveOutOfSolidSpeed", (Codec)Codec.DOUBLE), (o, i) -> o.moveOutOfSolidSpeed = i.doubleValue(), o -> Double.valueOf(o.moveOutOfSolidSpeed), (o, p) -> o.moveOutOfSolidSpeed = p.moveOutOfSolidSpeed).add()).appendInherited(new KeyedCodec("TerminalVelocityAir", (Codec)Codec.DOUBLE), (o, i) -> o.terminalVelocityAir = i.doubleValue(), o -> Double.valueOf(o.terminalVelocityAir), (o, p) -> o.terminalVelocityAir = p.terminalVelocityAir).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("DensityAir", (Codec)Codec.DOUBLE), (o, i) -> o.densityAir = i.doubleValue(), o -> Double.valueOf(o.densityAir), (o, p) -> o.densityAir = p.densityAir).add()).appendInherited(new KeyedCodec("TerminalVelocityWater", (Codec)Codec.DOUBLE), (o, i) -> o.terminalVelocityWater = i.doubleValue(), o -> Double.valueOf(o.terminalVelocityWater), (o, p) -> o.terminalVelocityWater = p.terminalVelocityWater).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("DensityWater", (Codec)Codec.DOUBLE), (o, i) -> o.densityWater = i.doubleValue(), o -> Double.valueOf(o.densityWater), (o, p) -> o.densityWater = p.densityWater).add()).appendInherited(new KeyedCodec("HitWaterImpulseLoss", (Codec)Codec.DOUBLE), (o, i) -> o.hitWaterImpulseLoss = i.doubleValue(), o -> Double.valueOf(o.hitWaterImpulseLoss), (o, p) -> o.hitWaterImpulseLoss = p.hitWaterImpulseLoss).add()).appendInherited(new KeyedCodec("RotationForce", (Codec)Codec.DOUBLE), (o, i) -> o.rotationForce = i.doubleValue(), o -> Double.valueOf(o.rotationForce), (o, p) -> o.rotationForce = p.rotationForce).add()).appendInherited(new KeyedCodec("SpeedRotationFactor", (Codec)Codec.FLOAT), (o, i) -> o.speedRotationFactor = i.floatValue(), o -> Float.valueOf(o.speedRotationFactor), (o, p) -> o.speedRotationFactor = p.speedRotationFactor).add()).appendInherited(new KeyedCodec("SwimmingDampingFactor", (Codec)Codec.DOUBLE), (o, i) -> o.swimmingDampingFactor = i.doubleValue(), o -> Double.valueOf(o.swimmingDampingFactor), (o, p) -> o.swimmingDampingFactor = p.swimmingDampingFactor).add()).appendInherited(new KeyedCodec("AllowRolling", (Codec)Codec.BOOLEAN), (o, i) -> o.allowRolling = i.booleanValue(), o -> Boolean.valueOf(o.allowRolling), (o, p) -> o.allowRolling = p.allowRolling).add()).appendInherited(new KeyedCodec("RollingFrictionFactor", (Codec)Codec.DOUBLE), (o, i) -> o.rollingFrictionFactor = i.doubleValue(), o -> Double.valueOf(o.rollingFrictionFactor), (o, p) -> o.rollingFrictionFactor = p.rollingFrictionFactor).add()).appendInherited(new KeyedCodec("RollingSpeed", (Codec)Codec.FLOAT), (o, i) -> o.rollingSpeed = i.floatValue(), o -> Float.valueOf(o.rollingSpeed), (o, p) -> o.rollingSpeed = p.rollingSpeed).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 190 */   public static final StandardPhysicsConfig DEFAULT = new StandardPhysicsConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double HIT_WATER_IMPULSE_LOSS = 0.2D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double ROTATION_FORCE = 3.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final float SPEED_ROTATION_FACTOR = 2.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double SWIMMING_DAMPING_FACTOR = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 215 */   protected double density = 700.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double gravity;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double bounciness;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   protected int bounceCount = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 235 */   protected double bounceLimit = 0.4D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean sticksVertically;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean computeYaw = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean computePitch = true;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 255 */   protected RotationMode rotationMode = RotationMode.VelocityDamped;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowRolling = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 266 */   protected double rollingFrictionFactor = 0.99D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   protected float rollingSpeed = 0.1F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double moveOutOfSolidSpeed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 281 */   protected double terminalVelocityAir = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 286 */   protected double densityAir = 1.2D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 291 */   protected double terminalVelocityWater = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 296 */   protected double densityWater = 998.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 301 */   protected double hitWaterImpulseLoss = 0.2D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 306 */   protected double rotationForce = 3.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 311 */   protected float speedRotationFactor = 2.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 316 */   protected double swimmingDampingFactor = 1.0D;
/*     */ 
/*     */   
/*     */   public double getGravity() {
/* 320 */     return this.gravity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(@Nonnull Holder<EntityStore> holder, @Nullable Ref<EntityStore> creatorRef, @Nonnull Vector3d velocity, @Nonnull ComponentAccessor<EntityStore> componentAccessor, boolean predicted) {
/*     */     UUID creatorUUID;
/* 331 */     if (creatorRef != null) {
/* 332 */       UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(creatorRef, UUIDComponent.getComponentType());
/* 333 */       assert uuidComponent != null;
/*     */       
/* 335 */       creatorUUID = uuidComponent.getUuid();
/*     */     } else {
/* 337 */       creatorUUID = null;
/*     */     } 
/*     */     
/* 340 */     BoundingBox boundingBoxComponent = (BoundingBox)holder.getComponent(BoundingBox.getComponentType());
/* 341 */     assert boundingBoxComponent != null;
/*     */     
/* 343 */     holder.addComponent(StandardPhysicsProvider.getComponentType(), new StandardPhysicsProvider(boundingBoxComponent, creatorUUID, this, velocity, predicted));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PhysicsConfig toPacket() {
/* 355 */     PhysicsConfig packet = new PhysicsConfig();
/* 356 */     packet.type = PhysicsType.Standard;
/* 357 */     packet.density = this.density;
/* 358 */     packet.gravity = this.gravity;
/* 359 */     packet.bounciness = this.bounciness;
/* 360 */     packet.bounceCount = this.bounceCount;
/* 361 */     packet.bounceLimit = this.bounceLimit;
/* 362 */     packet.sticksVertically = this.sticksVertically;
/* 363 */     packet.computeYaw = this.computeYaw;
/* 364 */     packet.computePitch = this.computePitch;
/* 365 */     packet.rotationMode = this.rotationMode;
/* 366 */     packet.moveOutOfSolidSpeed = this.moveOutOfSolidSpeed;
/* 367 */     packet.terminalVelocityAir = this.terminalVelocityAir;
/* 368 */     packet.densityAir = this.densityAir;
/* 369 */     packet.terminalVelocityWater = this.terminalVelocityWater;
/* 370 */     packet.densityWater = this.densityWater;
/* 371 */     packet.hitWaterImpulseLoss = this.hitWaterImpulseLoss;
/* 372 */     packet.rotationForce = this.rotationForce;
/* 373 */     packet.speedRotationFactor = this.speedRotationFactor;
/* 374 */     packet.swimmingDampingFactor = this.swimmingDampingFactor;
/* 375 */     packet.allowRolling = this.allowRolling;
/* 376 */     packet.rollingFrictionFactor = this.rollingFrictionFactor;
/* 377 */     packet.rollingSpeed = this.rollingSpeed;
/* 378 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBounciness() {
/* 385 */     return this.bounciness;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBounceCount() {
/* 392 */     return this.bounceCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBounceLimit() {
/* 399 */     return this.bounceLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSticksVertically() {
/* 406 */     return this.sticksVertically;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAllowRolling() {
/* 413 */     return this.allowRolling;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRollingFrictionFactor() {
/* 420 */     return this.rollingFrictionFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSwimmingDampingFactor() {
/* 427 */     return this.swimmingDampingFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHitWaterImpulseLoss() {
/* 434 */     return this.hitWaterImpulseLoss;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\config\StandardPhysicsConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */