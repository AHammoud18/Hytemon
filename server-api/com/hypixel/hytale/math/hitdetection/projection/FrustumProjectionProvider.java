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
/*     */ public class FrustumProjectionProvider
/*     */   implements MatrixProvider
/*     */ {
/*     */   public static final BuilderCodec<FrustumProjectionProvider> CODEC;
/*     */   protected final Matrix4d matrix;
/*     */   
/*     */   static {
/*  47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FrustumProjectionProvider.class, FrustumProjectionProvider::new).addField(new KeyedCodec("Left", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.left = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.left))).addField(new KeyedCodec("Right", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.right = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.right))).addField(new KeyedCodec("Top", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.top = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.top))).addField(new KeyedCodec("Bottom", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.bottom = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.bottom))).addField(new KeyedCodec("Near", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.near = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.near))).addField(new KeyedCodec("Far", (Codec)Codec.DOUBLE), (projectionProvider, d) -> projectionProvider.far = d.doubleValue(), projectionProvider -> Double.valueOf(projectionProvider.far))).build();
/*     */   }
/*     */   
/*  50 */   protected final Matrix4d rotMatrix = new Matrix4d();
/*     */   protected boolean invalid;
/*     */   protected double left;
/*     */   protected double right;
/*     */   protected double bottom;
/*     */   protected double top;
/*     */   
/*     */   public FrustumProjectionProvider() {
/*  58 */     this(new Matrix4d(), 0.1D, 0.1D, 0.1D, 0.1D, 1.0D, 5.0D);
/*     */   }
/*     */   protected double near; protected double far; protected double yaw; protected double pitch; protected double roll;
/*     */   public FrustumProjectionProvider(Matrix4d matrix, double left, double right, double bottom, double top, double near, double far) {
/*  62 */     this.matrix = matrix;
/*  63 */     this.left = left;
/*  64 */     this.right = right;
/*  65 */     this.bottom = bottom;
/*  66 */     this.top = top;
/*  67 */     this.near = near;
/*  68 */     this.far = far;
/*  69 */     this.invalid = true;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FrustumProjectionProvider setLeft(double left) {
/*  74 */     this.left = left;
/*  75 */     this.invalid = true;
/*  76 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FrustumProjectionProvider setRight(double right) {
/*  81 */     this.right = right;
/*  82 */     this.invalid = true;
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FrustumProjectionProvider setBottom(double bottom) {
/*  88 */     this.bottom = bottom;
/*  89 */     this.invalid = true;
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FrustumProjectionProvider setTop(double top) {
/*  95 */     this.top = top;
/*  96 */     this.invalid = true;
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FrustumProjectionProvider setNear(double near) {
/* 102 */     this.near = near;
/* 103 */     this.invalid = true;
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FrustumProjectionProvider setFar(double far) {
/* 109 */     this.far = far;
/* 110 */     this.invalid = true;
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FrustumProjectionProvider setRotation(double yaw, double pitch, double roll) {
/* 116 */     this.yaw = yaw;
/* 117 */     this.pitch = pitch;
/* 118 */     this.roll = roll;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix4d getMatrix() {
/* 124 */     if (this.invalid) {
/* 125 */       this.matrix.projectionFrustum(this.left, this.right, this.bottom, this.top, this.near, this.far);
/* 126 */       this.matrix.rotateAxis(this.pitch, 1.0D, 0.0D, 0.0D, this.rotMatrix);
/* 127 */       this.matrix.rotateAxis(this.yaw, 0.0D, 1.0D, 0.0D, this.rotMatrix);
/* 128 */       this.matrix.rotateAxis(this.roll, 0.0D, 0.0D, 1.0D, this.rotMatrix);
/* 129 */       this.invalid = false;
/*     */     } 
/* 131 */     return this.matrix;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\hitdetection\projection\FrustumProjectionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */