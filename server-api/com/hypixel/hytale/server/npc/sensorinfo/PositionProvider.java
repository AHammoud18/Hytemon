/*     */ package com.hypixel.hytale.server.npc.sensorinfo;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.ParameterProvider;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PositionProvider
/*     */   extends InfoProviderBase
/*     */   implements IPositionProvider
/*     */ {
/*  19 */   protected double x = 2.147483647E9D;
/*  20 */   protected double y = 2.147483647E9D;
/*  21 */   protected double z = 2.147483647E9D;
/*     */   
/*     */   protected boolean isValid;
/*     */   
/*     */   public PositionProvider() {}
/*     */   
/*     */   public PositionProvider(ParameterProvider parameterProvider) {
/*  28 */     super(parameterProvider);
/*     */   }
/*     */   
/*     */   public PositionProvider(ParameterProvider parameterProvider, ExtraInfoProvider... providers) {
/*  32 */     super(parameterProvider, providers);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  37 */     this.x = 2.147483647E9D;
/*  38 */     this.y = 2.147483647E9D;
/*  39 */     this.z = 2.147483647E9D;
/*  40 */     this.isValid = false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> setTarget(@Nullable Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  45 */     if (ref == null) return null;
/*     */     
/*  47 */     if (!ref.isValid() || componentAccessor.getArchetype(ref).contains(DeathComponent.getComponentType())) {
/*  48 */       clear();
/*  49 */       return null;
/*     */     } 
/*     */     
/*  52 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  53 */     assert transformComponent != null;
/*  54 */     setTarget(transformComponent.getPosition());
/*  55 */     return ref;
/*     */   }
/*     */   
/*     */   public void setTarget(@Nonnull Vector3d pos) {
/*  59 */     setTarget(pos.x, pos.y, pos.z);
/*     */   }
/*     */   
/*     */   public void setTarget(double x, double y, double z) {
/*  63 */     this.x = x;
/*  64 */     this.y = y;
/*  65 */     this.z = z;
/*  66 */     this.isValid = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean providePosition(@Nonnull Vector3d result) {
/*  75 */     if (!hasPosition()) return false;
/*     */     
/*  77 */     result.assign(this.x, this.y, this.z);
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/*  83 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/*  88 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/*  93 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IPositionProvider getPositionProvider() {
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPosition() {
/* 104 */     return this.isValid;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getTarget() {
/* 110 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\PositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */