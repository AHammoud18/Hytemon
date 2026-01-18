/*     */ package com.hypixel.hytale.math.vector;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import java.util.Objects;
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
/*     */ public class Transform
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Transform> CODEC;
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Transform> CODEC_DEGREES;
/*     */   @Nonnull
/*     */   protected Vector3d position;
/*     */   @Nonnull
/*     */   protected Vector3f rotation;
/*     */   public static final int X_IS_RELATIVE = 1;
/*     */   public static final int Y_IS_RELATIVE = 2;
/*     */   public static final int Z_IS_RELATIVE = 4;
/*     */   public static final int YAW_IS_RELATIVE = 8;
/*     */   public static final int PITCH_IS_RELATIVE = 16;
/*     */   public static final int ROLL_IS_RELATIVE = 32;
/*     */   public static final int RELATIVE_TO_BLOCK = 64;
/*     */   
/*     */   static {
/*  71 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Transform.class, Transform::new).appendInherited(new KeyedCodec("X", (Codec)Codec.DOUBLE), (o, i) -> (o.getPosition()).x = i.doubleValue(), o -> Double.valueOf((o.getPosition()).x), (o, p) -> (o.getPosition()).x = (p.getPosition()).x).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.DOUBLE), (o, i) -> (o.getPosition()).y = i.doubleValue(), o -> Double.valueOf((o.getPosition()).y), (o, p) -> (o.getPosition()).y = (p.getPosition()).y).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Z", (Codec)Codec.DOUBLE), (o, i) -> (o.getPosition()).z = i.doubleValue(), o -> Double.valueOf((o.getPosition()).z), (o, p) -> (o.getPosition()).z = (p.getPosition()).z).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Pitch", (Codec)Codec.FLOAT), (o, i) -> (o.getRotation()).x = i.floatValue(), o -> Float.isNaN((o.getRotation()).x) ? null : Float.valueOf((o.getRotation()).x), (o, p) -> (o.getRotation()).x = (p.getRotation()).x).add()).appendInherited(new KeyedCodec("Yaw", (Codec)Codec.FLOAT), (o, i) -> (o.getRotation()).y = i.floatValue(), o -> Float.isNaN((o.getRotation()).y) ? null : Float.valueOf((o.getRotation()).y), (o, p) -> (o.getRotation()).y = (p.getRotation()).y).add()).appendInherited(new KeyedCodec("Roll", (Codec)Codec.FLOAT), (o, i) -> (o.getRotation()).z = i.floatValue(), o -> Float.isNaN((o.getRotation()).z) ? null : Float.valueOf((o.getRotation()).z), (o, p) -> (o.getRotation()).z = (p.getRotation()).z).add()).build();
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
/* 123 */     CODEC_DEGREES = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Transform.class, Transform::new).appendInherited(new KeyedCodec("X", (Codec)Codec.DOUBLE), (o, i) -> (o.getPosition()).x = i.doubleValue(), o -> Double.valueOf((o.getPosition()).x), (o, p) -> (o.getPosition()).x = (p.getPosition()).x).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.DOUBLE), (o, i) -> (o.getPosition()).y = i.doubleValue(), o -> Double.valueOf((o.getPosition()).y), (o, p) -> (o.getPosition()).y = (p.getPosition()).y).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Z", (Codec)Codec.DOUBLE), (o, i) -> (o.getPosition()).z = i.doubleValue(), o -> Double.valueOf((o.getPosition()).z), (o, p) -> (o.getPosition()).z = (p.getPosition()).z).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Pitch", (Codec)Codec.FLOAT), (o, i) -> (o.getRotation()).x = (float)Math.toRadians(i.floatValue()), o -> Float.isNaN((o.getRotation()).x) ? null : Float.valueOf((float)Math.toDegrees((o.getRotation()).x)), (o, p) -> (o.getRotation()).x = (p.getRotation()).x).add()).appendInherited(new KeyedCodec("Yaw", (Codec)Codec.FLOAT), (o, i) -> (o.getRotation()).y = (float)Math.toRadians(i.floatValue()), o -> Float.isNaN((o.getRotation()).y) ? null : Float.valueOf((float)Math.toDegrees((o.getRotation()).y)), (o, p) -> (o.getRotation()).y = (p.getRotation()).y).add()).appendInherited(new KeyedCodec("Roll", (Codec)Codec.FLOAT), (o, i) -> (o.getRotation()).z = (float)Math.toRadians(i.floatValue()), o -> Float.isNaN((o.getRotation()).z) ? null : Float.valueOf((float)Math.toDegrees((o.getRotation()).z)), (o, p) -> (o.getRotation()).z = (p.getRotation()).z).add()).build();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform() {
/* 143 */     this(new Vector3d(), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform(@Nonnull Vector3i position) {
/* 152 */     this(new Vector3d(position), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform(@Nonnull Vector3d position) {
/* 161 */     this(new Vector3d(position), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform(double x, double y, double z) {
/* 172 */     this(new Vector3d(x, y, z), new Vector3f(Float.NaN, Float.NaN, Float.NaN));
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
/*     */   public Transform(double x, double y, double z, float pitch, float yaw, float roll) {
/* 186 */     this(new Vector3d(x, y, z), new Vector3f(pitch, yaw, roll));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform(@Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/* 196 */     this.position = position;
/* 197 */     this.rotation = rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void assign(@Nonnull Transform transform) {
/* 206 */     this.position.assign(transform.getPosition());
/* 207 */     this.rotation.assign(transform.getRotation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getPosition() {
/* 215 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(@Nonnull Vector3d position) {
/* 224 */     this.position = position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f getRotation() {
/* 232 */     return this.rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotation(@Nonnull Vector3f rotation) {
/* 241 */     this.rotation = rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getDirection() {
/* 249 */     return getDirection(this.rotation.getPitch(), this.rotation.getYaw());
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
/*     */   public static Vector3d getDirection(float pitch, float yaw) {
/* 261 */     if (Float.isNaN(pitch)) throw new IllegalStateException("Pitch can't be NaN"); 
/* 262 */     if (Float.isNaN(yaw)) throw new IllegalStateException("Yaw can't be NaN");
/*     */     
/* 264 */     double len = TrigMathUtil.cos(pitch);
/* 265 */     double x = len * -TrigMathUtil.sin(yaw);
/* 266 */     double y = TrigMathUtil.sin(pitch);
/* 267 */     double z = len * -TrigMathUtil.cos(yaw);
/* 268 */     return new Vector3d(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getAxisDirection() {
/* 276 */     return getAxisDirection(this.rotation.getPitch(), this.rotation.getYaw());
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
/*     */   public Vector3i getAxisDirection(float pitch, float yaw) {
/* 288 */     if (Float.isNaN(pitch)) throw new IllegalStateException("Pitch can't be NaN"); 
/* 289 */     if (Float.isNaN(yaw)) throw new IllegalStateException("Yaw can't be NaN");
/*     */     
/* 291 */     float len = TrigMathUtil.cos(pitch);
/* 292 */     float x = len * -TrigMathUtil.sin(yaw);
/* 293 */     float y = TrigMathUtil.sin(pitch);
/* 294 */     float z = len * -TrigMathUtil.cos(yaw);
/* 295 */     return new Vector3i(MathUtil.fastRound(x), MathUtil.fastRound(y), MathUtil.fastRound(z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Axis getAxis() {
/* 303 */     Vector3i axisDirection = getAxisDirection();
/* 304 */     if (axisDirection.getX() != 0)
/* 305 */       return Axis.X; 
/* 306 */     if (axisDirection.getY() != 0) {
/* 307 */       return Axis.Y;
/*     */     }
/* 309 */     return Axis.Z;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Transform clone() {
/* 315 */     return new Transform(this.position.clone(), this.rotation.clone());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 320 */     if (this == o) return true; 
/* 321 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 323 */     Transform transform = (Transform)o;
/*     */     
/* 325 */     if (!Objects.equals(this.position, transform.position)) return false; 
/* 326 */     return Objects.equals(this.rotation, transform.rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 331 */     int result = this.position.hashCode();
/* 332 */     result = 31 * result + this.rotation.hashCode();
/* 333 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 339 */     return "Transform{position=" + String.valueOf(this.position) + ", rotation=" + String.valueOf(this.rotation) + "}";
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
/*     */   public static void applyMaskedRelativeTransform(@Nonnull Transform transform, byte relativeMask, @Nonnull Vector3d sourcePosition, @Nonnull Vector3f sourceRotation, @Nonnull Vector3i blockPosition) {
/* 399 */     if (relativeMask == 0)
/*     */       return; 
/* 401 */     if ((relativeMask & 0x40) != 0) {
/* 402 */       if ((relativeMask & 0x1) != 0) {
/* 403 */         transform.getPosition().setX(transform.getPosition().getX() + blockPosition.getX());
/*     */       }
/* 405 */       if ((relativeMask & 0x2) != 0) {
/* 406 */         transform.getPosition().setY(transform.getPosition().getY() + blockPosition.getY());
/*     */       }
/* 408 */       if ((relativeMask & 0x4) != 0) {
/* 409 */         transform.getPosition().setZ(transform.getPosition().getZ() + blockPosition.getZ());
/*     */       }
/*     */     } else {
/* 412 */       if ((relativeMask & 0x1) != 0) {
/* 413 */         transform.getPosition().setX(transform.getPosition().getX() + sourcePosition.getX());
/*     */       }
/* 415 */       if ((relativeMask & 0x2) != 0) {
/* 416 */         transform.getPosition().setY(transform.getPosition().getY() + sourcePosition.getY());
/*     */       }
/* 418 */       if ((relativeMask & 0x4) != 0) {
/* 419 */         transform.getPosition().setZ(transform.getPosition().getZ() + sourcePosition.getZ());
/*     */       }
/*     */     } 
/*     */     
/* 423 */     if ((relativeMask & 0x8) != 0) {
/* 424 */       transform.getRotation().setYaw(transform.getRotation().getYaw() + sourceRotation.getYaw());
/*     */     }
/* 426 */     if ((relativeMask & 0x10) != 0) {
/* 427 */       transform.getRotation().setPitch(transform.getRotation().getPitch() + sourceRotation.getPitch());
/*     */     }
/* 429 */     if ((relativeMask & 0x20) != 0)
/* 430 */       transform.getRotation().setRoll(transform.getRotation().getRoll() + sourceRotation.getRoll()); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Transform.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */