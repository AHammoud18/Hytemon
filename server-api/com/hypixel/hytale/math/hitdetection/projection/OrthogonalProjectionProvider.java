/*     */ package com.hypixel.hytale.math.hitdetection.projection;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.math.hitdetection.MatrixProvider;
/*     */ import com.hypixel.hytale.math.matrix.Matrix4d;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrthogonalProjectionProvider
/*     */   implements MatrixProvider
/*     */ {
/*     */   public static final BuilderCodec<OrthogonalProjectionProvider> CODEC;
/*     */   protected final Matrix4d matrix;
/*     */   
/*     */   static {
/*  47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(OrthogonalProjectionProvider.class, OrthogonalProjectionProvider::new).addField(new KeyedCodec("Left", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.left = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.left))).addField(new KeyedCodec("Right", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.right = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.right))).addField(new KeyedCodec("Top", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.top = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.top))).addField(new KeyedCodec("Bottom", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.bottom = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.bottom))).addField(new KeyedCodec("Near", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.near = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.near))).addField(new KeyedCodec("Far", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.far = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.far))).build();
/*     */   }
/*     */   
/*  50 */   protected final Matrix4d rotMatrix = new Matrix4d();
/*     */   protected boolean invalid;
/*     */   protected double left;
/*     */   protected double right;
/*     */   protected double bottom;
/*     */   protected double top;
/*     */   
/*     */   public OrthogonalProjectionProvider() {
/*  58 */     this(new Matrix4d(), 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   protected double near; protected double far; protected double yaw; protected double pitch; protected double roll;
/*     */   public OrthogonalProjectionProvider(Matrix4d matrix, double left, double right, double bottom, double top, double near, double far, double yaw, double pitch, double roll) {
/*  62 */     this.matrix = matrix;
/*  63 */     this.left = left;
/*  64 */     this.right = right;
/*  65 */     this.bottom = bottom;
/*  66 */     this.top = top;
/*  67 */     this.near = near;
/*  68 */     this.far = far;
/*  69 */     this.yaw = yaw;
/*  70 */     this.pitch = pitch;
/*  71 */     this.roll = roll;
/*  72 */     this.invalid = true;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OrthogonalProjectionProvider setLeft(double left) {
/*  77 */     this.left = left;
/*  78 */     this.invalid = true;
/*  79 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OrthogonalProjectionProvider setRight(double right) {
/*  84 */     this.right = right;
/*  85 */     this.invalid = true;
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OrthogonalProjectionProvider setBottom(double bottom) {
/*  91 */     this.bottom = bottom;
/*  92 */     this.invalid = true;
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OrthogonalProjectionProvider setTop(double top) {
/*  98 */     this.top = top;
/*  99 */     this.invalid = true;
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OrthogonalProjectionProvider setNear(double near) {
/* 105 */     this.near = near;
/* 106 */     this.invalid = true;
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OrthogonalProjectionProvider setFar(double far) {
/* 112 */     this.far = far;
/* 113 */     this.invalid = true;
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public double getRange() {
/* 118 */     return this.far;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OrthogonalProjectionProvider setRotation(double yaw, double pitch, double roll) {
/* 123 */     this.yaw = yaw;
/* 124 */     this.pitch = pitch;
/* 125 */     this.roll = roll;
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix4d getMatrix() {
/* 131 */     if (this.invalid) {
/* 132 */       this.matrix.projectionOrtho(this.left, this.right, this.bottom, this.top, this.near, this.far);
/* 133 */       this.matrix.rotateAxis(this.roll, 0.0D, 0.0D, 1.0D, this.rotMatrix);
/* 134 */       this.matrix.rotateAxis(this.pitch, 1.0D, 0.0D, 0.0D, this.rotMatrix);
/* 135 */       this.matrix.rotateAxis(this.yaw, 0.0D, 1.0D, 0.0D, this.rotMatrix);
/* 136 */       this.invalid = false;
/*     */     } 
/* 138 */     return this.matrix;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 144 */     return "OrthogonalProjectionProvider{left=" + this.left + ", right=" + this.right + ", bottom=" + this.bottom + ", top=" + this.top + ", near=" + this.near + ", far=" + this.far + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\hitdetection\projection\OrthogonalProjectionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */