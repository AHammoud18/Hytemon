/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.random.RandomExtra;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorRandom;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorRandom
/*    */   extends SensorBase
/*    */ {
/*    */   protected final double minFalseDuration;
/*    */   protected final double maxFalseDuration;
/*    */   protected final double minTrueDuration;
/*    */   protected final double maxTrueDuration;
/*    */   protected double remainingDuration;
/*    */   protected boolean state;
/*    */   
/*    */   public SensorRandom(@Nonnull BuilderSensorRandom builder, @Nonnull BuilderSupport support) {
/* 26 */     super((BuilderSensorBase)builder);
/* 27 */     double[] falseDuration = builder.getFalseRange(support);
/* 28 */     this.minFalseDuration = falseDuration[0];
/* 29 */     this.maxFalseDuration = falseDuration[1];
/* 30 */     double[] trueDuration = builder.getTrueRange(support);
/* 31 */     this.minTrueDuration = trueDuration[0];
/* 32 */     this.maxTrueDuration = trueDuration[1];
/*    */     
/* 34 */     this.state = RandomExtra.randomBoolean();
/* 35 */     this.remainingDuration = pickNextDuration();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 40 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 42 */     if ((this.remainingDuration -= dt) <= 0.0D) {
/* 43 */       this.state = !this.state;
/* 44 */       this.remainingDuration = pickNextDuration();
/*    */     } 
/*    */     
/* 47 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 52 */     return null;
/*    */   }
/*    */   
/*    */   protected double pickNextDuration() {
/* 56 */     return this.state ? RandomExtra.randomRange(this.minTrueDuration, this.maxTrueDuration) : RandomExtra.randomRange(this.minFalseDuration, this.maxFalseDuration);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorRandom.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */