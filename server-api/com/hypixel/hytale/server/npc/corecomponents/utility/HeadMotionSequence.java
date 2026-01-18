/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderHeadMotionSequence;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderMotionSequence;
/*    */ import com.hypixel.hytale.server.npc.instructions.HeadMotion;
/*    */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class HeadMotionSequence
/*    */   extends MotionSequence<HeadMotion>
/*    */   implements HeadMotion, IAnnotatedComponentCollection
/*    */ {
/*    */   public HeadMotionSequence(@Nonnull BuilderHeadMotionSequence builder, @Nonnull BuilderSupport support) {
/* 16 */     super((BuilderMotionSequence<HeadMotion>)builder, builder.getSteps(support));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\HeadMotionSequence.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */