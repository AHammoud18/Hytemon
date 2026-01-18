/*     */ package com.hypixel.hytale.server.core.modules.physics.component;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PhysicsValues
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, PhysicsValues> getComponentType() {
/*  21 */     return EntityModule.get().getPhysicsValuesComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  28 */   public static final Double ZERO = Double.valueOf(0.0D);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PhysicsValues> CODEC;
/*     */ 
/*     */   
/*     */   private static final double DEFAULT_MASS = 1.0D;
/*     */ 
/*     */   
/*     */   private static final double DEFAULT_DRAG_COEFFICIENT = 0.5D;
/*     */ 
/*     */   
/*     */   private static final boolean DEFAULT_INVERTED_GRAVITY = false;
/*     */ 
/*     */   
/*     */   protected double mass;
/*     */ 
/*     */   
/*     */   protected double dragCoefficient;
/*     */ 
/*     */   
/*     */   protected boolean invertedGravity;
/*     */ 
/*     */   
/*     */   static {
/*  54 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PhysicsValues.class, PhysicsValues::new).append(new KeyedCodec("Mass", (Codec)Codec.DOUBLE), (instance, value) -> instance.mass = value.doubleValue(), instance -> Double.valueOf(instance.mass)).addValidator(Validators.greaterThan(ZERO)).add()).append(new KeyedCodec("DragCoefficient", (Codec)Codec.DOUBLE), (instance, value) -> instance.dragCoefficient = value.doubleValue(), instance -> Double.valueOf(instance.dragCoefficient)).addValidator(Validators.greaterThanOrEqual(ZERO)).add()).append(new KeyedCodec("InvertedGravity", (Codec)Codec.BOOLEAN), (instance, value) -> instance.invertedGravity = value.booleanValue(), instance -> Boolean.valueOf(instance.invertedGravity)).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PhysicsValues() {
/*  65 */     this(1.0D, 0.5D, false);
/*     */   }
/*     */   
/*     */   public PhysicsValues(@Nonnull PhysicsValues other) {
/*  69 */     this(other.mass, other.dragCoefficient, other.isInvertedGravity());
/*     */   }
/*     */   
/*     */   public PhysicsValues(double mass, double dragCoefficient, boolean invertedGravity) {
/*  73 */     this.mass = mass;
/*  74 */     this.dragCoefficient = dragCoefficient;
/*  75 */     this.invertedGravity = invertedGravity;
/*     */   }
/*     */   
/*     */   public void replaceValues(@Nonnull PhysicsValues other) {
/*  79 */     this.mass = other.mass;
/*  80 */     this.dragCoefficient = other.dragCoefficient;
/*  81 */     this.invertedGravity = other.invertedGravity;
/*     */   }
/*     */   
/*     */   public void resetToDefault() {
/*  85 */     this.mass = 1.0D;
/*  86 */     this.dragCoefficient = 0.5D;
/*  87 */     this.invertedGravity = false;
/*     */   }
/*     */   
/*     */   public void scale(float scale) {
/*  91 */     this.mass *= scale;
/*  92 */     this.dragCoefficient *= scale;
/*     */   }
/*     */   
/*     */   public double getMass() {
/*  96 */     return this.mass;
/*     */   }
/*     */   
/*     */   public double getDragCoefficient() {
/* 100 */     return this.dragCoefficient;
/*     */   }
/*     */   
/*     */   public boolean isInvertedGravity() {
/* 104 */     return this.invertedGravity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PhysicsValues getDefault() {
/* 109 */     return new PhysicsValues();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 115 */     return "PhysicsValues{mass=" + this.mass + ", dragCoefficient=" + this.dragCoefficient + ", invertedGravity=" + this.invertedGravity + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 125 */     return new PhysicsValues(this);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physics\component\PhysicsValues.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */