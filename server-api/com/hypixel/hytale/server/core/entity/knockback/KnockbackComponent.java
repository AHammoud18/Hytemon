/*    */ package com.hypixel.hytale.server.core.entity.knockback;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class KnockbackComponent
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, KnockbackComponent> getComponentType() {
/* 18 */     return EntityModule.get().getKnockbackComponentType();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private Vector3d velocity;
/* 23 */   private ChangeVelocityType velocityType = ChangeVelocityType.Add;
/*    */   
/*    */   @Nonnull
/* 26 */   private DoubleList modifiers = (DoubleList)new DoubleArrayList(); @Nullable
/*    */   private VelocityConfig velocityConfig;
/*    */   private float duration;
/*    */   private float timer;
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getVelocity() {
/* 33 */     return this.velocity;
/*    */   }
/*    */   
/*    */   public void setVelocity(@Nonnull Vector3d velocity) {
/* 37 */     this.velocity = velocity;
/*    */   }
/*    */   
/*    */   public ChangeVelocityType getVelocityType() {
/* 41 */     return this.velocityType;
/*    */   }
/*    */   
/*    */   public void setVelocityType(ChangeVelocityType velocityType) {
/* 45 */     this.velocityType = velocityType;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public VelocityConfig getVelocityConfig() {
/* 50 */     return this.velocityConfig;
/*    */   }
/*    */   
/*    */   public void setVelocityConfig(@Nullable VelocityConfig velocityConfig) {
/* 54 */     this.velocityConfig = velocityConfig;
/*    */   }
/*    */   
/*    */   public void addModifier(double modifier) {
/* 58 */     this.modifiers.add(modifier);
/*    */   }
/*    */   
/*    */   public void applyModifiers() {
/* 62 */     for (int i = 0; i < this.modifiers.size(); i++) {
/* 63 */       this.velocity.scale(this.modifiers.getDouble(i));
/*    */     }
/*    */     
/* 66 */     this.modifiers.clear();
/*    */   }
/*    */   
/*    */   public float getDuration() {
/* 70 */     return this.duration;
/*    */   }
/*    */   
/*    */   public void setDuration(float duration) {
/* 74 */     this.duration = duration;
/*    */   }
/*    */   
/*    */   public float getTimer() {
/* 78 */     return this.timer;
/*    */   }
/*    */   
/*    */   public void incrementTimer(float time) {
/* 82 */     this.timer += time;
/*    */   }
/*    */   
/*    */   public void setTimer(float time) {
/* 86 */     this.timer = time;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 92 */     KnockbackComponent component = new KnockbackComponent();
/* 93 */     component.velocity = this.velocity;
/* 94 */     component.velocityType = this.velocityType;
/* 95 */     component.velocityConfig = this.velocityConfig;
/* 96 */     component.duration = this.duration;
/* 97 */     component.timer = this.timer;
/* 98 */     return component;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\knockback\KnockbackComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */