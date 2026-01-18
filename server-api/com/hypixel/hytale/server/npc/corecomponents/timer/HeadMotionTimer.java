/*    */ package com.hypixel.hytale.server.npc.corecomponents.timer;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderHeadMotionTimer;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderMotionTimer;
/*    */ import com.hypixel.hytale.server.npc.instructions.HeadMotion;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class HeadMotionTimer
/*    */   extends MotionTimer<HeadMotion>
/*    */   implements HeadMotion
/*    */ {
/*    */   public HeadMotionTimer(@Nonnull BuilderHeadMotionTimer builder, @Nonnull BuilderSupport builderSupport, HeadMotion motion) {
/* 15 */     super((BuilderMotionTimer<HeadMotion>)builder, builderSupport, motion);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\timer\HeadMotionTimer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */