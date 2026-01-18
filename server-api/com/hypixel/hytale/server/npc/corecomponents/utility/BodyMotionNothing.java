/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.BodyMotionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderBodyMotionBase;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BodyMotionNothing
/*    */   extends BodyMotionBase {
/*    */   public BodyMotionNothing(@Nonnull BuilderBodyMotionBase builderMotionBase) {
/* 17 */     super(builderMotionBase);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role support, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 22 */     desiredSteering.clear();
/*    */     
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\BodyMotionNothing.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */