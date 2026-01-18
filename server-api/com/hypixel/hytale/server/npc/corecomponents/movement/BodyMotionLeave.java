/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionFindBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionLeave;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import com.hypixel.hytale.server.npc.navigation.AStarBase;
/*    */ import com.hypixel.hytale.server.npc.navigation.AStarNode;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class BodyMotionLeave
/*    */   extends BodyMotionFindBase<AStarBase>
/*    */ {
/*    */   protected final double distanceSquared;
/*    */   
/*    */   public BodyMotionLeave(@Nonnull BuilderBodyMotionLeave builderMotionLeave, @Nonnull BuilderSupport support) {
/* 22 */     super((BuilderBodyMotionFindBase)builderMotionLeave, support, new AStarBase());
/* 23 */     double distance = builderMotionLeave.getDistance(support);
/* 24 */     this.distanceSquared = distance * distance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGoalReached(Ref<EntityStore> ref, @Nonnull MotionController controller, Vector3d position, ComponentAccessor<EntityStore> componentAccessor) {
/* 29 */     return (controller.waypointDistanceSquared(this.aStar.getStartPosition(), position) >= this.distanceSquared);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGoalReached(Ref<EntityStore> ref, @Nonnull AStarBase aStarBase, @Nonnull AStarNode aStarNode, @Nonnull MotionController controller, ComponentAccessor<EntityStore> componentAccessor) {
/* 34 */     return (controller.waypointDistanceSquared(aStarBase.getStartPosition(), aStarNode.getPosition()) >= this.distanceSquared);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float estimateToGoal(AStarBase aStarBase, Vector3d fromPosition, MotionController motionController) {
/* 40 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void findBestPath(@Nonnull AStarBase aStarBase, MotionController controller) {
/* 45 */     aStarBase.buildFurthestPath();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionLeave.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */