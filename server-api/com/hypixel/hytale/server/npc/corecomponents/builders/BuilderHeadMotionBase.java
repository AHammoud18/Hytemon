/*    */ package com.hypixel.hytale.server.npc.corecomponents.builders;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.instructions.HeadMotion;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderHeadMotionBase
/*    */   extends BuilderMotionBase<HeadMotion>
/*    */ {
/*    */   @Nonnull
/*    */   public final Class<HeadMotion> category() {
/* 15 */     return HeadMotion.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\builders\BuilderHeadMotionBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */