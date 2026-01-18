/*     */ package com.hypixel.hytale.math.hitdetection.view;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.math.hitdetection.MatrixProvider;
/*     */ import com.hypixel.hytale.math.matrix.Matrix4d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DirectionViewProvider
/*     */   implements MatrixProvider
/*     */ {
/*     */   public static final BuilderCodec<DirectionViewProvider> CODEC;
/*     */   
/*     */   static {
/*  33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DirectionViewProvider.class, DirectionViewProvider::new).append(new KeyedCodec("YawOffset", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.yawOffset = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.yawOffset)).add()).append(new KeyedCodec("PitchOffset", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.pitchOffset = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.pitchOffset)).add()).append(new KeyedCodec("Up", (Codec)Vector3d.CODEC), (projectionProvider, vec) -> projectionProvider.up.assign(vec), projectionProvider -> projectionProvider.up).add()).build();
/*     */   }
/*  35 */   public static final Vector3d DEFAULT_UP = new Vector3d(0.0D, 1.0D, 0.0D); protected final Matrix4d matrix;
/*     */   protected final Vector3d position;
/*     */   protected final Vector3d direction;
/*     */   protected final Vector3d up;
/*     */   protected double yaw;
/*     */   protected double pitch;
/*     */   protected double yawOffset;
/*     */   protected double pitchOffset;
/*     */   protected boolean invalid;
/*     */   
/*     */   public DirectionViewProvider() {
/*  46 */     this(new Matrix4d(), new Vector3d(), new Vector3d(), new Vector3d(DEFAULT_UP));
/*     */   }
/*     */   
/*     */   public DirectionViewProvider(Matrix4d matrix, Vector3d position, Vector3d direction, Vector3d up) {
/*  50 */     this.matrix = matrix;
/*  51 */     this.position = position;
/*  52 */     this.direction = direction;
/*  53 */     this.up = up;
/*  54 */     this.invalid = true;
/*     */   }
/*     */   
/*     */   public Vector3d getPosition() {
/*  58 */     return this.position;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DirectionViewProvider setPosition(@Nonnull Vector3d vec) {
/*  63 */     return setPosition(vec, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DirectionViewProvider setPosition(@Nonnull Vector3d vec, double offsetX, double offsetY, double offsetZ) {
/*  68 */     return setPosition(vec.x, vec.y, vec.z, offsetX, offsetY, offsetZ);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DirectionViewProvider setPosition(double x, double y, double z) {
/*  73 */     this.position.assign(x, y, z);
/*  74 */     this.invalid = true;
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DirectionViewProvider setPosition(double x, double y, double z, double offsetX, double offsetY, double offsetZ) {
/*  80 */     return setPosition(x + offsetX, y + offsetY, z + offsetZ);
/*     */   }
/*     */   
/*     */   public Vector3d getDirection() {
/*  84 */     return this.direction;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DirectionViewProvider setDirection(@Nonnull Vector3d vec) {
/*  89 */     return setDirection(vec.x, vec.y, vec.z);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DirectionViewProvider setDirection(double yaw, double pitch) {
/*  94 */     yaw += this.yawOffset;
/*  95 */     pitch += this.pitchOffset;
/*  96 */     this.direction.assign(yaw, pitch);
/*  97 */     this.invalid = true;
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DirectionViewProvider setDirection(double x, double y, double z) {
/* 103 */     this.direction.assign(x, y, z);
/* 104 */     this.invalid = true;
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DirectionViewProvider setUp(double x, double y, double z) {
/* 110 */     this.up.assign(x, y, z);
/* 111 */     this.invalid = true;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix4d getMatrix() {
/* 117 */     if (this.invalid) {
/* 118 */       this.matrix.viewDirection(this.position.x, this.position.y, this.position.z, this.direction.x, this.direction.y, this.direction.z, this.up.x, this.up.y, this.up.z);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       this.invalid = false;
/*     */     } 
/* 125 */     return this.matrix;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 131 */     return "DirectionViewProvider{up=" + String.valueOf(this.up) + ", yaw=" + this.yaw + ", pitch=" + this.pitch + ", yawOffset=" + this.yawOffset + ", pitchOffset=" + this.pitchOffset + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\hitdetection\view\DirectionViewProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */