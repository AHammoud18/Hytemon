/*     */ package com.hypixel.hytale.server.core.modules.entity.teleport;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Teleport
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   @Nullable
/*     */   private final World world;
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, Teleport> getComponentType() {
/*  25 */     return EntityModule.get().getTeleportComponentType();
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
/*     */   @Nonnull
/*  37 */   private final Vector3d position = new Vector3d();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  43 */   private final Vector3f rotation = new Vector3f();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Vector3f headRotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean resetVelocity = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Teleport(@Nullable World world, @Nonnull Transform transform) {
/*  64 */     this(world, transform.getPosition(), transform.getRotation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Teleport(@Nullable World world, @Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/*  75 */     this.world = world;
/*  76 */     this.position.assign(position);
/*  77 */     this.rotation.assign(rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Teleport(@Nonnull Transform transform) {
/*  86 */     this(null, transform.getPosition(), transform.getRotation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Teleport(@Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/*  96 */     this.world = null;
/*  97 */     this.position.assign(position);
/*  98 */     this.rotation.assign(rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Teleport withHeadRotation(@Nonnull Vector3f headRotation) {
/* 109 */     this.headRotation = headRotation;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Teleport withResetRoll() {
/* 117 */     this.rotation.setRoll(0.0F);
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Teleport withoutVelocityReset() {
/* 125 */     this.resetVelocity = false;
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public World getWorld() {
/* 134 */     return this.world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getPosition() {
/* 142 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f getRotation() {
/* 150 */     return this.rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vector3f getHeadRotation() {
/* 158 */     return this.headRotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isResetVelocity() {
/* 165 */     return this.resetVelocity;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Teleport clone() {
/* 171 */     return new Teleport(this.world, this.position, this.rotation);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\teleport\Teleport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */