/*     */ package com.hypixel.hytale.server.npc.movement;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*     */ public class Steering
/*     */ {
/*  17 */   public static final Steering NULL = (new Steering()).clear();
/*  18 */   private final Vector3d translation = new Vector3d();
/*  19 */   private double maxDistance = Double.MAX_VALUE;
/*     */   private Vector3d maxDistanceComponentSelector;
/*     */   private boolean hasTranslation;
/*     */   private float yaw;
/*     */   private boolean hasYaw;
/*     */   private float pitch;
/*     */   private boolean hasPitch;
/*     */   private float roll;
/*     */   private boolean hasRoll;
/*     */   private double relativeTurnSpeed;
/*     */   private boolean hasRelativeTurnSpeed;
/*     */   
/*     */   @Nonnull
/*     */   public Steering clear() {
/*  33 */     clearTranslation();
/*  34 */     clearRotation();
/*  35 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering assign(@Nonnull Steering other) {
/*  40 */     this.translation.assign(other.translation);
/*  41 */     this.maxDistance = other.maxDistance;
/*  42 */     this.maxDistanceComponentSelector = other.maxDistanceComponentSelector;
/*  43 */     this.hasTranslation = other.hasTranslation;
/*  44 */     this.yaw = other.yaw;
/*  45 */     this.hasYaw = other.hasYaw;
/*  46 */     this.pitch = other.pitch;
/*  47 */     this.hasPitch = other.hasPitch;
/*  48 */     this.relativeTurnSpeed = other.relativeTurnSpeed;
/*  49 */     this.hasRelativeTurnSpeed = other.hasRelativeTurnSpeed;
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  55 */     if (this == o) return true; 
/*  56 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  58 */     Steering steering = (Steering)o;
/*     */     
/*  60 */     if (Double.compare(steering.maxDistance, this.maxDistance) != 0) return false; 
/*  61 */     if (this.hasTranslation != steering.hasTranslation) return false; 
/*  62 */     if (Float.compare(steering.yaw, this.yaw) != 0) return false; 
/*  63 */     if (this.hasYaw != steering.hasYaw) return false; 
/*  64 */     if (Float.compare(steering.pitch, this.pitch) != 0) return false; 
/*  65 */     if (this.hasPitch != steering.hasPitch) return false; 
/*  66 */     if (Float.compare(steering.roll, this.roll) != 0) return false; 
/*  67 */     if (this.hasRoll != steering.hasRoll) return false; 
/*  68 */     if (!this.translation.equals(steering.translation)) return false; 
/*  69 */     if (Double.compare(steering.relativeTurnSpeed, this.relativeTurnSpeed) != 0) return false; 
/*  70 */     if (this.hasRelativeTurnSpeed != steering.hasRelativeTurnSpeed) return false; 
/*  71 */     return this.maxDistanceComponentSelector.equals(steering.maxDistanceComponentSelector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  78 */     int result = this.translation.hashCode();
/*  79 */     long temp = Double.doubleToLongBits(this.maxDistance);
/*  80 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/*  81 */     result = 31 * result + this.maxDistanceComponentSelector.hashCode();
/*  82 */     result = 31 * result + (this.hasTranslation ? 1 : 0);
/*  83 */     result = 31 * result + ((this.yaw != 0.0F) ? Float.floatToIntBits(this.yaw) : 0);
/*  84 */     result = 31 * result + (this.hasYaw ? 1 : 0);
/*  85 */     result = 31 * result + ((this.pitch != 0.0F) ? Float.floatToIntBits(this.pitch) : 0);
/*  86 */     result = 31 * result + (this.hasPitch ? 1 : 0);
/*  87 */     result = 31 * result + ((this.roll != 0.0F) ? Float.floatToIntBits(this.roll) : 0);
/*  88 */     result = 31 * result + (this.hasRoll ? 1 : 0);
/*  89 */     return result;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering clearTranslation() {
/*  94 */     this.translation.assign(Vector3d.ZERO);
/*  95 */     this.maxDistance = Double.MAX_VALUE;
/*  96 */     this.hasTranslation = false;
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering clearRotation() {
/* 102 */     this.yaw = 0.0F;
/* 103 */     this.hasYaw = false;
/* 104 */     this.pitch = 0.0F;
/* 105 */     this.hasPitch = false;
/* 106 */     this.relativeTurnSpeed = 0.0D;
/* 107 */     this.hasRelativeTurnSpeed = false;
/* 108 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getTranslation() {
/* 113 */     return this.translation;
/*     */   }
/*     */   
/*     */   public double getX() {
/* 117 */     return this.translation.x;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setX(double value) {
/* 122 */     this.hasTranslation = true;
/* 123 */     this.translation.x = value;
/* 124 */     return this;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 128 */     return this.translation.y;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setY(double value) {
/* 133 */     this.hasTranslation = true;
/* 134 */     this.translation.y = value;
/* 135 */     return this;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 139 */     return this.translation.z;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setZ(double value) {
/* 144 */     this.hasTranslation = true;
/* 145 */     this.translation.z = value;
/* 146 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setTranslation(@Nonnull Vector3d translation) {
/* 151 */     this.hasTranslation = true;
/* 152 */     this.translation.assign(translation);
/* 153 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setTranslation(double x, double y, double z) {
/* 158 */     this.hasTranslation = true;
/* 159 */     this.translation.assign(x, y, z);
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setTranslationRelativeSpeed(double relativeSpeed) {
/* 165 */     this.translation.setLength(relativeSpeed);
/* 166 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering scaleTranslation(double speedFactor) {
/* 171 */     this.translation.scale(speedFactor);
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering ensureMinTranslation(double relativeSpeed) {
/* 177 */     if (this.translation.squaredLength() < relativeSpeed * relativeSpeed && this.translation.squaredLength() > 0.0D) {
/* 178 */       this.translation.setLength(relativeSpeed);
/*     */     }
/* 180 */     return this;
/*     */   }
/*     */   
/*     */   public double getMaxDistance() {
/* 184 */     return this.maxDistance;
/*     */   }
/*     */   
/*     */   public void setMaxDistance(double maxDistance) {
/* 188 */     this.maxDistance = maxDistance;
/*     */   }
/*     */   
/*     */   public void clearMaxDistance() {
/* 192 */     setMaxDistance(Double.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public Vector3d getMaxDistanceComponentSelector() {
/* 196 */     return this.maxDistanceComponentSelector;
/*     */   }
/*     */   
/*     */   public void setMaxDistanceComponentSelector(Vector3d maxDistanceComponentSelector) {
/* 200 */     this.maxDistanceComponentSelector = maxDistanceComponentSelector;
/*     */   }
/*     */   
/*     */   public void clearMaxDistanceComponentSelector() {
/* 204 */     setMaxDistanceComponentSelector(null);
/*     */   }
/*     */   
/*     */   public float getYaw() {
/* 208 */     return this.yaw;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setYaw(float angle) {
/* 213 */     this.yaw = angle;
/* 214 */     this.hasYaw = true;
/* 215 */     return this;
/*     */   }
/*     */   
/*     */   public void clearYaw() {
/* 219 */     this.hasYaw = false;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/* 223 */     return this.pitch;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setPitch(float angle) {
/* 228 */     this.pitch = angle;
/* 229 */     this.hasPitch = true;
/* 230 */     return this;
/*     */   }
/*     */   
/*     */   public void clearPitch() {
/* 234 */     this.hasPitch = false;
/*     */   }
/*     */   
/*     */   public float getRoll() {
/* 238 */     return this.roll;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setRoll(float angle) {
/* 243 */     this.roll = angle;
/* 244 */     this.hasRoll = true;
/* 245 */     return this;
/*     */   }
/*     */   
/*     */   public void clearRoll() {
/* 249 */     this.hasRoll = false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Steering setRelativeTurnSpeed(double relativeTurnSpeed) {
/* 254 */     this.relativeTurnSpeed = relativeTurnSpeed;
/* 255 */     this.hasRelativeTurnSpeed = true;
/* 256 */     return this;
/*     */   }
/*     */   
/*     */   public boolean hasTranslation() {
/* 260 */     return this.hasTranslation;
/*     */   }
/*     */   
/*     */   public boolean hasYaw() {
/* 264 */     return this.hasYaw;
/*     */   }
/*     */   
/*     */   public boolean hasPitch() {
/* 268 */     return this.hasPitch;
/*     */   }
/*     */   
/*     */   public boolean hasRoll() {
/* 272 */     return this.hasRoll;
/*     */   }
/*     */   
/*     */   public double getSpeed() {
/* 276 */     if (!hasTranslation()) {
/* 277 */       return 0.0D;
/*     */     }
/* 279 */     return this.translation.length();
/*     */   }
/*     */   
/*     */   public double getRelativeTurnSpeed() {
/* 283 */     if (!this.hasRelativeTurnSpeed) return 1.0D; 
/* 284 */     return this.relativeTurnSpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 290 */     return "Steering{translation=" + String.valueOf(this.translation) + ", hasTranslation=" + this.hasTranslation + ", yaw=" + this.yaw + ", hasYaw=" + this.hasYaw + ", pitch=" + this.pitch + ", hasPitch=" + this.hasPitch + ", roll=" + this.roll + ", hasRoll=" + this.hasRoll + ", relativeTurnSpeed=" + this.relativeTurnSpeed + ", hasRelativeTurnSpeed=" + this.hasRelativeTurnSpeed + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\Steering.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */