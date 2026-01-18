/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionWander;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionWanderBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BodyMotionWander extends BodyMotionWanderBase {
/*    */   public BodyMotionWander(@Nonnull BuilderBodyMotionWander builder, @Nonnull BuilderSupport builderSupport) {
/* 15 */     super((BuilderBodyMotionWanderBase)builder, builderSupport);
/*    */   }
/*    */ 
/*    */   
/*    */   protected double constrainMove(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull Vector3d probePosition, @Nonnull Vector3d targetPosition, double moveDist, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 20 */     return moveDist;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionWander.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */