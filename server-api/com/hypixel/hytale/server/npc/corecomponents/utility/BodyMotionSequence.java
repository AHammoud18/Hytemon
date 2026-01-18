/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderBodyMotionSequence;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderMotionSequence;
/*    */ import com.hypixel.hytale.server.npc.instructions.BodyMotion;
/*    */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BodyMotionSequence
/*    */   extends MotionSequence<BodyMotion>
/*    */   implements BodyMotion, IAnnotatedComponentCollection
/*    */ {
/*    */   public BodyMotionSequence(@Nonnull BuilderBodyMotionSequence builder, @Nonnull BuilderSupport support) {
/* 16 */     super((BuilderMotionSequence<BodyMotion>)builder, builder.getSteps(support));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BodyMotion getSteeringMotion() {
/* 22 */     return (this.activeMotion == null) ? null : this.activeMotion.getSteeringMotion();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\BodyMotionSequence.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */