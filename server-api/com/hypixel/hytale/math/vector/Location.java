/*     */ package com.hypixel.hytale.math.vector;
/*     */ 
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import java.util.Objects;
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
/*     */ public class Location
/*     */ {
/*     */   @Nullable
/*     */   protected String world;
/*     */   @Nonnull
/*     */   protected Vector3d position;
/*     */   @Nonnull
/*     */   protected Vector3f rotation;
/*     */   
/*     */   public Location() {
/*  39 */     this((String)null, new Vector3d(), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(@Nonnull Vector3i position) {
/*  48 */     this((String)null, new Vector3d(position), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(@Nullable String world, @Nonnull Vector3i position) {
/*  58 */     this(world, new Vector3d(position), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(@Nonnull Vector3d position) {
/*  67 */     this((String)null, new Vector3d(position), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(@Nullable String world, @Nonnull Vector3d position) {
/*  77 */     this(world, new Vector3d(position), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(double x, double y, double z) {
/*  88 */     this((String)null, new Vector3d(x, y, z), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(@Nullable String world, double x, double y, double z) {
/* 100 */     this(world, new Vector3d(x, y, z), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
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
/*     */   public Location(double x, double y, double z, float pitch, float yaw, float roll) {
/* 114 */     this((String)null, new Vector3d(x, y, z), new Vector3f(pitch, yaw, roll));
/*     */   }
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
/*     */   public Location(@Nullable String world, double x, double y, double z, float pitch, float yaw, float roll) {
/* 129 */     this(world, new Vector3d(x, y, z), new Vector3f(pitch, yaw, roll));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(@Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/* 139 */     this((String)null, position, rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(@Nonnull Transform transform) {
/* 148 */     this((String)null, transform.position, transform.rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(@Nullable String world, @Nonnull Transform transform) {
/* 158 */     this(world, transform.position, transform.rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location(@Nullable String world, @Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/* 169 */     this.world = world;
/* 170 */     this.position = position;
/* 171 */     this.rotation = rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getWorld() {
/* 179 */     return this.world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorld(@Nullable String world) {
/* 188 */     this.world = world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getPosition() {
/* 196 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(@Nonnull Vector3d position) {
/* 205 */     this.position = position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f getRotation() {
/* 213 */     return this.rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotation(@Nonnull Vector3f rotation) {
/* 222 */     this.rotation = rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getDirection() {
/* 230 */     return Transform.getDirection(this.rotation.getPitch(), this.rotation.getYaw());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getAxisDirection() {
/* 238 */     return getAxisDirection(this.rotation.getPitch(), this.rotation.getYaw());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getAxisDirection(float pitch, float yaw) {
/* 248 */     if (Float.isNaN(pitch)) throw new IllegalStateException("Pitch can't be NaN"); 
/* 249 */     if (Float.isNaN(yaw)) throw new IllegalStateException("Yaw can't be NaN");
/*     */     
/* 251 */     float len = TrigMathUtil.cos(pitch);
/* 252 */     float x = len * -TrigMathUtil.sin(yaw);
/* 253 */     float y = TrigMathUtil.sin(pitch);
/* 254 */     float z = len * -TrigMathUtil.cos(yaw);
/* 255 */     return new Vector3i(MathUtil.fastRound(x), MathUtil.fastRound(y), MathUtil.fastRound(z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Axis getAxis() {
/* 263 */     Vector3i axisDirection = getAxisDirection();
/* 264 */     if (axisDirection.getX() != 0)
/* 265 */       return Axis.X; 
/* 266 */     if (axisDirection.getY() != 0) {
/* 267 */       return Axis.Y;
/*     */     }
/* 269 */     return Axis.Z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Transform toTransform() {
/* 277 */     return new Transform(this.position, this.rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 282 */     if (this == o) return true; 
/* 283 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 285 */     Location location = (Location)o;
/*     */     
/* 287 */     if (!Objects.equals(this.world, location.world)) return false; 
/* 288 */     if (!Objects.equals(this.position, location.position)) return false; 
/* 289 */     return Objects.equals(this.rotation, location.rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 294 */     int result = (this.world != null) ? this.world.hashCode() : 0;
/* 295 */     result = 31 * result + this.position.hashCode();
/* 296 */     result = 31 * result + this.rotation.hashCode();
/* 297 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 303 */     return "Location{world='" + this.world + "', position=" + String.valueOf(this.position) + ", rotation=" + String.valueOf(this.rotation) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Location.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */