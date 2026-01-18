/*    */ package com.hypixel.hytale.server.npc.corecomponents.timer;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderBodyMotionTimer;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderMotionTimer;
/*    */ import com.hypixel.hytale.server.npc.instructions.BodyMotion;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BodyMotionTimer
/*    */   extends MotionTimer<BodyMotion>
/*    */   implements BodyMotion
/*    */ {
/*    */   public BodyMotionTimer(@Nonnull BuilderBodyMotionTimer builder, @Nonnull BuilderSupport builderSupport, BodyMotion motion) {
/* 14 */     super((BuilderMotionTimer<BodyMotion>)builder, builderSupport, motion);
/*    */   }
/*    */ 
/*    */   
/*    */   public BodyMotion getSteeringMotion() {
/* 19 */     return this.motion.getSteeringMotion();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\timer\BodyMotionTimer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */