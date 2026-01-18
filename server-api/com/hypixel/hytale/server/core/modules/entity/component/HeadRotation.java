/*     */ package com.hypixel.hytale.server.core.modules.entity.component;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*     */ public class HeadRotation
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   public static final BuilderCodec<HeadRotation> CODEC;
/*     */   
/*     */   static {
/*  29 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(HeadRotation.class, HeadRotation::new).append(new KeyedCodec("Rotation", (Codec)Vector3f.ROTATION), (o, i) -> o.rotation.assign(i), o -> o.rotation).add()).build();
/*     */   }
/*     */   public static ComponentType<EntityStore, HeadRotation> getComponentType() {
/*  32 */     return EntityModule.get().getHeadRotationComponentType();
/*     */   }
/*     */   
/*  35 */   private final Vector3f rotation = new Vector3f();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeadRotation(@Nonnull Vector3f rotation) {
/*  41 */     this.rotation.assign(rotation);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f getRotation() {
/*  46 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public void setRotation(@Nonnull Vector3f rotation) {
/*  50 */     this.rotation.assign(rotation);
/*     */   }
/*     */   
/*     */   public Vector3d getDirection() {
/*  54 */     return getDirection(this.rotation.getPitch(), this.rotation.getYaw(), new Vector3d());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getAxisDirection() {
/*  62 */     return getAxisDirection(this.rotation.getPitch(), this.rotation.getYaw(), new Vector3i());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getAxisDirection(@Nonnull Vector3i result) {
/*  67 */     return getAxisDirection(this.rotation.getPitch(), this.rotation.getYaw(), result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getHorizontalAxisDirection() {
/*  75 */     return getAxisDirection(0.0F, this.rotation.getYaw(), new Vector3i());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Axis getAxis() {
/*  83 */     Vector3i axisDirection = getAxisDirection();
/*  84 */     if (axisDirection.getX() != 0)
/*  85 */       return Axis.X; 
/*  86 */     if (axisDirection.getY() != 0) {
/*  87 */       return Axis.Y;
/*     */     }
/*  89 */     return Axis.Z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3i getAxisDirection(float pitch, float yaw, @Nonnull Vector3i result) {
/*  97 */     if (Float.isNaN(pitch)) throw new IllegalStateException("Pitch can't be NaN"); 
/*  98 */     if (Float.isNaN(yaw)) throw new IllegalStateException("Yaw can't be NaN");
/*     */ 
/*     */     
/* 101 */     double len = TrigMathUtil.cos(pitch);
/* 102 */     double x = len * -TrigMathUtil.sin(yaw);
/* 103 */     double y = TrigMathUtil.sin(pitch);
/* 104 */     double z = len * -TrigMathUtil.cos(yaw);
/* 105 */     return result.assign((int)Math.round(x), (int)Math.round(y), (int)Math.round(z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static Vector3d getDirection(float pitch, float yaw, @Nonnull Vector3d result) {
/* 113 */     if (Float.isNaN(pitch)) throw new IllegalStateException("Pitch can't be NaN"); 
/* 114 */     if (Float.isNaN(yaw)) throw new IllegalStateException("Yaw can't be NaN");
/*     */     
/* 116 */     return result.assign(yaw, pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void teleportRotation(@Nonnull Vector3f rotation) {
/* 124 */     float yaw = rotation.getYaw();
/* 125 */     if (!Float.isNaN(yaw)) {
/* 126 */       this.rotation.setYaw(yaw);
/*     */     }
/* 128 */     float pitch = rotation.getPitch();
/* 129 */     if (!Float.isNaN(pitch)) {
/* 130 */       this.rotation.setPitch(pitch);
/*     */     }
/* 132 */     float roll = rotation.getRoll();
/* 133 */     if (!Float.isNaN(roll)) {
/* 134 */       this.rotation.setRoll(roll);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public HeadRotation clone() {
/* 142 */     return new HeadRotation(this.rotation);
/*     */   }
/*     */   
/*     */   public HeadRotation() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\HeadRotation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */