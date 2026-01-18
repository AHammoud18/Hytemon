/*    */ package com.hypixel.hytale.server.npc.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.math.random.RandomExtra;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionWithDelay;
/*    */ import com.hypixel.hytale.server.npc.role.support.EntitySupport;
/*    */ import com.hypixel.hytale.server.npc.util.IComponentExecutionControl;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ActionWithDelay
/*    */   extends ActionBase
/*    */ {
/*    */   private final double[] delayRange;
/*    */   private double delay;
/*    */   private boolean isDelaying;
/*    */   
/*    */   public ActionWithDelay(@Nonnull BuilderActionWithDelay builder, @Nonnull BuilderSupport support) {
/* 21 */     super((BuilderActionBase)builder);
/* 22 */     this.delayRange = builder.getDelayRange(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processDelay(float dt) {
/* 27 */     if (!this.isDelaying) return true; 
/* 28 */     if ((this.delay -= dt) <= 0.0D) this.isDelaying = false; 
/* 29 */     return !this.isDelaying;
/*    */   }
/*    */   
/*    */   protected boolean isDelaying() {
/* 33 */     return this.isDelaying;
/*    */   }
/*    */   
/*    */   protected boolean isDelayPrepared() {
/* 37 */     return (this.delay > 0.0D);
/*    */   }
/*    */   
/*    */   protected void prepareDelay() {
/* 41 */     this.delay = RandomExtra.randomRange(this.delayRange);
/* 42 */     this.isDelaying = false;
/*    */   }
/*    */   
/*    */   protected void clearDelay() {
/* 46 */     this.delay = 0.0D;
/* 47 */     this.isDelaying = false;
/*    */   }
/*    */   
/*    */   protected void startDelay(@Nonnull EntitySupport support) {
/* 51 */     support.registerDelay((IComponentExecutionControl)this);
/* 52 */     this.isDelaying = true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\ActionWithDelay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */