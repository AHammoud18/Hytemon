/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class KnockbackSimulation
/*     */   implements Component<EntityStore> {
/*     */   public static final float KNOCKBACK_SIMULATION_TIME = 0.5F;
/*     */   public static final float BLEND_DELAY = 0.2F;
/*     */   
/*     */   public static ComponentType<EntityStore, KnockbackSimulation> getComponentType() {
/*  20 */     return EntityModule.get().getKnockbackSimulationComponentType();
/*     */   }
/*     */   
/*  23 */   private final Vector3d requestedVelocity = new Vector3d();
/*     */   
/*  25 */   private final Vector3d clientLastPosition = new Vector3d();
/*  26 */   private final Vector3d clientPosition = new Vector3d();
/*  27 */   private final Vector3d relativeMovement = new Vector3d();
/*     */   
/*  29 */   private final Vector3d simPosition = new Vector3d();
/*  30 */   private final Vector3d simVelocity = new Vector3d();
/*     */   @Nullable
/*  32 */   private ChangeVelocityType requestedVelocityChangeType = null;
/*     */   
/*     */   private MovementStates clientMovementStates;
/*     */   
/*  36 */   private float remainingTime = 0.5F;
/*     */   private boolean hadWishMovement = false;
/*     */   private boolean clientFinished = false;
/*     */   private boolean wasJumping = false;
/*  40 */   private int jumpCombo = 0;
/*     */   
/*     */   private boolean wasOnGround = false;
/*  43 */   private float tickBuffer = 0.0F;
/*     */ 
/*     */   
/*  46 */   private final Vector3d movementOffset = new Vector3d();
/*  47 */   private final CollisionResult collisionResult = new CollisionResult();
/*  48 */   private final Vector3d checkPosition = new Vector3d();
/*  49 */   private final Vector3d tempPosition = new Vector3d();
/*     */   
/*     */   public float getTickBuffer() {
/*  52 */     return this.tickBuffer;
/*     */   }
/*     */   
/*     */   public void setTickBuffer(float tickBuffer) {
/*  56 */     this.tickBuffer = tickBuffer;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getRequestedVelocity() {
/*  61 */     return this.requestedVelocity;
/*     */   }
/*     */   
/*     */   public void addRequestedVelocity(@Nonnull Vector3d velocity) {
/*  65 */     if (this.requestedVelocityChangeType == null || this.requestedVelocityChangeType == ChangeVelocityType.Add) {
/*  66 */       this.requestedVelocityChangeType = ChangeVelocityType.Add;
/*     */     }
/*  68 */     this.requestedVelocity.add(velocity);
/*     */   }
/*     */   
/*     */   public void setRequestedVelocity(@Nonnull Vector3d velocity) {
/*  72 */     if (this.requestedVelocityChangeType == null || this.requestedVelocityChangeType == ChangeVelocityType.Add) {
/*  73 */       this.requestedVelocityChangeType = ChangeVelocityType.Set;
/*     */     }
/*  75 */     this.requestedVelocity.assign(velocity);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ChangeVelocityType getRequestedVelocityChangeType() {
/*  80 */     return this.requestedVelocityChangeType;
/*     */   }
/*     */   
/*     */   public void setRequestedVelocityChangeType(ChangeVelocityType requestedVelocityChangeType) {
/*  84 */     this.requestedVelocityChangeType = requestedVelocityChangeType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getClientLastPosition() {
/*  89 */     return this.clientLastPosition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getClientPosition() {
/*  94 */     return this.clientPosition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getRelativeMovement() {
/*  99 */     return this.relativeMovement;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getSimPosition() {
/* 104 */     return this.simPosition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getSimVelocity() {
/* 109 */     return this.simVelocity;
/*     */   }
/*     */   
/*     */   public float getRemainingTime() {
/* 113 */     return this.remainingTime;
/*     */   }
/*     */   
/*     */   public void setRemainingTime(float remainingTime) {
/* 117 */     this.remainingTime = remainingTime;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 121 */     this.remainingTime = 0.5F;
/*     */   }
/*     */   
/*     */   public boolean consumeWasJumping() {
/* 125 */     boolean tmp = this.wasJumping;
/* 126 */     this.wasJumping = false;
/* 127 */     return tmp;
/*     */   }
/*     */   
/*     */   public void setWasJumping(boolean wasJumping) {
/* 131 */     this.wasJumping = wasJumping;
/*     */   }
/*     */   
/*     */   public boolean hadWishMovement() {
/* 135 */     return this.hadWishMovement;
/*     */   }
/*     */   
/*     */   public void setHadWishMovement(boolean hadWishMovement) {
/* 139 */     this.hadWishMovement = hadWishMovement;
/*     */   }
/*     */   
/*     */   public boolean isClientFinished() {
/* 143 */     return this.clientFinished;
/*     */   }
/*     */   
/*     */   public void setClientFinished(boolean clientFinished) {
/* 147 */     this.clientFinished = clientFinished;
/*     */   }
/*     */   
/*     */   public int getJumpCombo() {
/* 151 */     return this.jumpCombo;
/*     */   }
/*     */   
/*     */   public void setJumpCombo(int jumpCombo) {
/* 155 */     this.jumpCombo = jumpCombo;
/*     */   }
/*     */   
/*     */   public boolean wasOnGround() {
/* 159 */     return this.wasOnGround;
/*     */   }
/*     */   
/*     */   public void setWasOnGround(boolean wasOnGround) {
/* 163 */     this.wasOnGround = wasOnGround;
/*     */   }
/*     */   
/*     */   public MovementStates getClientMovementStates() {
/* 167 */     return this.clientMovementStates;
/*     */   }
/*     */   
/*     */   public void setClientMovementStates(MovementStates clientMovementStates) {
/* 171 */     this.clientMovementStates = clientMovementStates;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getMovementOffset() {
/* 176 */     return this.movementOffset;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CollisionResult getCollisionResult() {
/* 181 */     return this.collisionResult;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getCheckPosition() {
/* 186 */     return this.checkPosition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getTempPosition() {
/* 191 */     return this.tempPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 197 */     KnockbackSimulation simulation = new KnockbackSimulation();
/* 198 */     simulation.requestedVelocity.assign(this.requestedVelocity);
/* 199 */     return simulation;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\KnockbackSimulation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */