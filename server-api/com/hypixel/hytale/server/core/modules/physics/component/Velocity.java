/*     */ package com.hypixel.hytale.server.core.modules.physics.component;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
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
/*     */ public class Velocity
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Velocity> CODEC;
/*     */   
/*     */   static {
/*  33 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(Velocity.class, Velocity::new).append(new KeyedCodec("Velocity", (Codec)Vector3d.CODEC), (entity, o) -> entity.velocity.assign(o), entity -> entity.velocity).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, Velocity> getComponentType() {
/*  40 */     return EntityModule.get().getVelocityComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   protected final List<Instruction> instructions = (List<Instruction>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  52 */   protected final Vector3d velocity = new Vector3d();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  58 */   protected final Vector3d clientVelocity = new Vector3d();
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
/*     */   public Velocity(@Nonnull Velocity other) {
/*  76 */     this(other.velocity.clone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Velocity(@Nonnull Vector3d initialVelocity) {
/*  85 */     this.velocity.assign(initialVelocity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZero() {
/*  92 */     set(0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addForce(@Nonnull Vector3d force) {
/* 101 */     this.velocity.add(force);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addForce(double x, double y, double z) {
/* 112 */     this.velocity.add(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(@Nonnull Vector3d newVelocity) {
/* 121 */     set(newVelocity.getX(), newVelocity.getY(), newVelocity.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(double x, double y, double z) {
/* 132 */     this.velocity.assign(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClient(@Nonnull Vector3d newVelocity) {
/* 141 */     setClient(newVelocity.getX(), newVelocity.getY(), newVelocity.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClient(double x, double y, double z) {
/* 152 */     this.clientVelocity.assign(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(double x) {
/* 161 */     this.velocity.setX(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(double y) {
/* 170 */     this.velocity.setY(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZ(double z) {
/* 179 */     this.velocity.setZ(z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 186 */     return this.velocity.getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 193 */     return this.velocity.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 200 */     return this.velocity.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSpeed() {
/* 207 */     return this.velocity.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInstruction(@Nonnull Vector3d velocity, @Nullable VelocityConfig config, @Nonnull ChangeVelocityType type) {
/* 218 */     this.instructions.add(new Instruction(velocity, config, type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Instruction> getInstructions() {
/* 226 */     return this.instructions;
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
/*     */   @Nonnull
/*     */   public Vector3d getVelocity() {
/* 241 */     return this.velocity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getClientVelocity() {
/* 249 */     return this.clientVelocity;
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
/*     */   public Vector3d assignVelocityTo(@Nonnull Vector3d vector) {
/* 261 */     return vector.assign(this.velocity);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 267 */     return new Velocity(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Velocity() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Instruction
/*     */   {
/*     */     @Nonnull
/*     */     private final Vector3d velocity;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private final VelocityConfig config;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ChangeVelocityType type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Instruction(@Nonnull Vector3d velocity, @Nullable VelocityConfig config, @Nonnull ChangeVelocityType type) {
/* 304 */       this.velocity = velocity;
/* 305 */       this.config = config;
/* 306 */       this.type = type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Vector3d getVelocity() {
/* 314 */       return this.velocity;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public VelocityConfig getConfig() {
/* 322 */       return this.config;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public ChangeVelocityType getType() {
/* 330 */       return this.type;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physics\component\Velocity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */