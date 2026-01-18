/*     */ package com.hypixel.hytale.server.npc.util;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.BallisticData;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.ExtraInfoProvider;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AimingData
/*     */   implements ExtraInfoProvider
/*     */ {
/*     */   public static final double MIN_MOVE_SPEED_STATIC = 0.01D;
/*     */   public static final double MIN_MOVE_SPEED_STATIC_2 = 1.0E-4D;
/*     */   public static final double MIN_AIMING_DISTANCE = 0.01D;
/*     */   public static final double MIN_AIMING_DISTANCE_2 = 1.0E-4D;
/*     */   public static final double MIN_AIR_TIME = 0.01D;
/*     */   public static final double ANGLE_EPSILON = 0.1D;
/*     */   @Nullable
/*     */   private BallisticData ballisticData;
/*     */   private boolean useFlatTrajectory = true;
/*     */   private double depthOffset;
/*     */   private boolean pitchAdjustOffset;
/*     */   private boolean haveSolution;
/*     */   private boolean haveOrientation;
/*     */   private boolean haveAttacked;
/*     */   private double chargeDistance;
/*     */   private double desiredHitAngle;
/*  37 */   private final float[] pitch = new float[2];
/*  38 */   private final float[] yaw = new float[2];
/*     */   
/*     */   @Nullable
/*     */   private Ref<EntityStore> target;
/*  42 */   private int owner = Integer.MIN_VALUE;
/*     */   
/*     */   public boolean isHaveAttacked() {
/*  45 */     return this.haveAttacked;
/*     */   }
/*     */   
/*     */   public void setHaveAttacked(boolean haveAttacked) {
/*  49 */     this.haveAttacked = haveAttacked;
/*     */   }
/*     */   
/*     */   public void requireBallistic(@Nonnull BallisticData ballisticData) {
/*  53 */     this.ballisticData = ballisticData;
/*  54 */     this.haveSolution = false;
/*  55 */     this.haveOrientation = false;
/*     */   }
/*     */   
/*     */   public void requireCloseCombat() {
/*  59 */     this.ballisticData = null;
/*  60 */     this.haveSolution = false;
/*  61 */     this.haveOrientation = false;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  65 */     return getPitch(this.useFlatTrajectory);
/*     */   }
/*     */   
/*     */   public float getPitch(boolean flatTrajectory) {
/*  69 */     return this.pitch[flatTrajectory ? 0 : 1];
/*     */   }
/*     */   
/*     */   public float getYaw() {
/*  73 */     return getYaw(this.useFlatTrajectory);
/*     */   }
/*     */   
/*     */   public float getYaw(boolean flatTrajectory) {
/*  77 */     return this.yaw[flatTrajectory ? 0 : 1];
/*     */   }
/*     */   
/*     */   public boolean isBallistic() {
/*  81 */     return (this.ballisticData != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BallisticData getBallisticData() {
/*  86 */     return this.ballisticData;
/*     */   }
/*     */   
/*     */   public void setUseFlatTrajectory(boolean useFlatTrajectory) {
/*  90 */     this.useFlatTrajectory = useFlatTrajectory;
/*     */   }
/*     */   
/*     */   public void setChargeDistance(double chargeDistance) {
/*  94 */     this.chargeDistance = chargeDistance;
/*     */   }
/*     */   
/*     */   public double getChargeDistance() {
/*  98 */     return this.chargeDistance;
/*     */   }
/*     */   
/*     */   public void setDesiredHitAngle(double desiredHitAngle) {
/* 102 */     this.desiredHitAngle = desiredHitAngle;
/*     */   }
/*     */   
/*     */   public double getDesiredHitAngle() {
/* 106 */     return this.desiredHitAngle;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Class<AimingData> getType() {
/* 112 */     return AimingData.class;
/*     */   }
/*     */   
/*     */   public void setDepthOffset(double depthOffset, boolean pitchAdjustOffset) {
/* 116 */     this.depthOffset = depthOffset;
/* 117 */     this.pitchAdjustOffset = (depthOffset != 0.0D && pitchAdjustOffset);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getTarget() {
/* 122 */     if (this.target == null) return null;
/*     */     
/* 124 */     if (!this.target.isValid() || this.target.getStore().getArchetype(this.target).contains(DeathComponent.getComponentType())) {
/* 125 */       this.target = null;
/* 126 */       return null;
/*     */     } 
/* 128 */     return this.target;
/*     */   }
/*     */   
/*     */   public void setTarget(Ref<EntityStore> ref) {
/* 132 */     this.target = ref;
/*     */   }
/*     */   
/*     */   public boolean haveOrientation() {
/* 136 */     return (this.haveOrientation || this.haveSolution);
/*     */   }
/*     */   
/*     */   public void setOrientation(float yaw, float pitch) {
/* 140 */     this.yaw[1] = yaw; this.yaw[0] = yaw;
/* 141 */     this.pitch[1] = pitch; this.pitch[0] = pitch;
/* 142 */     this.haveOrientation = true;
/*     */   }
/*     */   
/*     */   public void clearSolution() {
/* 146 */     this.haveOrientation = false;
/* 147 */     this.haveSolution = false;
/* 148 */     this.target = null;
/*     */   }
/*     */   
/*     */   public boolean computeSolution(double x, double y, double z, double vx, double vy, double vz) {
/* 152 */     double xxzz = x * x + z * z;
/* 153 */     double d2 = xxzz + y * y;
/* 154 */     if (d2 < 0.01D)
/*     */     {
/* 156 */       return this.haveSolution = false;
/*     */     }
/* 158 */     if (!isBallistic()) {
/*     */       
/* 160 */       this.yaw[1] = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z)); this.yaw[0] = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z));
/* 161 */       this.pitch[1] = PhysicsMath.pitchFromDirection(x, y, z); this.pitch[0] = PhysicsMath.pitchFromDirection(x, y, z);
/* 162 */       return this.haveSolution = true;
/*     */     } 
/*     */ 
/*     */     
/* 166 */     if (!this.pitchAdjustOffset && xxzz > this.depthOffset * this.depthOffset) {
/* 167 */       double len = Math.sqrt(xxzz);
/* 168 */       double newLen = len - this.depthOffset;
/* 169 */       double scale = newLen / len;
/* 170 */       x *= scale;
/* 171 */       z *= scale;
/* 172 */       xxzz = newLen * newLen;
/* 173 */       d2 = xxzz + y * y;
/*     */     } 
/*     */     
/* 176 */     double v2 = NPCPhysicsMath.dotProduct(vx, vy, vz);
/* 177 */     if (v2 < 1.0E-4D) {
/*     */       
/* 179 */       if (this.haveSolution = computeStaticSolution(Math.sqrt(xxzz), y)) {
/* 180 */         this.yaw[1] = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z)); this.yaw[0] = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z));
/*     */       } 
/* 182 */       return this.haveSolution;
/*     */     } 
/*     */ 
/*     */     
/* 186 */     double gravity = this.ballisticData.getGravity();
/* 187 */     double muzzleVelocity = this.ballisticData.getMuzzleVelocity();
/*     */ 
/*     */     
/* 190 */     double[] solutions = new double[4];
/* 191 */     double c4 = gravity * gravity / 4.0D;
/* 192 */     double c3 = vy * gravity;
/* 193 */     double c2 = v2 + y * gravity - muzzleVelocity * muzzleVelocity;
/* 194 */     double c1 = 2.0D * (x * vx + y * vy + z * vz);
/*     */ 
/*     */     
/* 197 */     int numSolutions = RootSolver.solveQuartic(c4, c3, c2, c1, d2, solutions);
/*     */     
/* 199 */     if (numSolutions == 0) {
/* 200 */       return this.haveSolution = false;
/*     */     }
/*     */     
/* 203 */     int numResults = 0;
/* 204 */     double lastT = Double.MAX_VALUE;
/*     */     
/* 206 */     for (int i = 0; i < numSolutions; i++) {
/* 207 */       double t = solutions[i];
/* 208 */       if (t > 0.01D) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 213 */         double tx = x + t * vx;
/* 214 */         double tz = z + t * vz;
/*     */         
/* 216 */         xxzz = tx * tx + tz * tz;
/* 217 */         if (xxzz >= 0.01D)
/*     */         
/*     */         { 
/*     */ 
/*     */           
/* 222 */           double sine = (y / t + 0.5D * gravity * t) / muzzleVelocity;
/*     */           
/* 224 */           if (sine >= -1.0D && sine <= 1.0D)
/*     */           
/*     */           { 
/*     */ 
/*     */             
/* 229 */             float p = TrigMathUtil.asin(sine);
/* 230 */             float h = PhysicsMath.headingFromDirection(tx, tz);
/*     */             
/* 232 */             if (numResults < 2)
/*     */             
/*     */             { 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 239 */               if (numResults == 0 || t > lastT) {
/* 240 */                 lastT = t;
/* 241 */                 this.pitch[numResults] = p;
/* 242 */                 this.yaw[numResults] = h;
/*     */               } else {
/*     */                 
/* 245 */                 this.pitch[numResults] = this.pitch[numResults - 1];
/* 246 */                 this.yaw[numResults] = this.yaw[numResults - 1];
/* 247 */                 this.pitch[numResults - 1] = p;
/* 248 */                 this.yaw[numResults - 1] = h;
/*     */               } 
/* 250 */               numResults++; }  }  } 
/*     */       } 
/* 252 */     }  if (numResults == 0) {
/* 253 */       return this.haveSolution = false;
/*     */     }
/* 255 */     if (numResults == 1) {
/* 256 */       this.pitch[1] = this.pitch[0];
/* 257 */       this.yaw[1] = this.yaw[0];
/*     */     } 
/* 259 */     return this.haveSolution = true;
/*     */   }
/*     */   
/*     */   public boolean isOnTarget(float yaw, float pitch, double hitAngle) {
/* 263 */     if (!haveOrientation()) {
/* 264 */       return false;
/*     */     }
/* 266 */     double differenceYaw = NPCPhysicsMath.turnAngle(yaw, getYaw());
/* 267 */     if (!isBallistic()) {
/* 268 */       return (-hitAngle <= differenceYaw && differenceYaw <= hitAngle);
/*     */     }
/* 270 */     double differencePitch = NPCPhysicsMath.turnAngle(pitch, getPitch());
/* 271 */     return (differencePitch >= -0.1D && differencePitch <= 0.1D && differenceYaw >= -0.1D && differenceYaw <= 0.1D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tryClaim(int id) {
/* 276 */     if (this.owner != Integer.MIN_VALUE)
/* 277 */       return;  this.owner = id;
/* 278 */     clear();
/*     */   }
/*     */   
/*     */   public boolean isClaimedBy(int id) {
/* 282 */     return (this.owner == id);
/*     */   }
/*     */   
/*     */   public void release() {
/* 286 */     this.owner = Integer.MIN_VALUE;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 290 */     clearSolution();
/* 291 */     this.ballisticData = null;
/* 292 */     this.useFlatTrajectory = true;
/* 293 */     this.depthOffset = 0.0D;
/* 294 */     this.pitchAdjustOffset = false;
/* 295 */     this.haveAttacked = false;
/*     */   }
/*     */   
/*     */   protected boolean computeStaticSolution(double dx, double dy) {
/* 299 */     return this.haveSolution = AimingHelper.computePitch(dx, dy, this.ballisticData.getMuzzleVelocity(), this.ballisticData.getGravity(), this.pitch);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\AimingData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */