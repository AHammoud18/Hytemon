/*    */ package com.hypixel.hytale.server.npc.navigation;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AStarDebugWithTarget
/*    */   extends AStarDebugBase
/*    */ {
/*    */   protected final AStarWithTarget aStarWithTarget;
/*    */   
/*    */   public AStarDebugWithTarget(AStarWithTarget aStarWithTarget, @Nonnull HytaleLogger logger) {
/* 18 */     super(aStarWithTarget, logger);
/* 19 */     this.aStarWithTarget = aStarWithTarget;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getDumpMapRegionZ(int def) {
/* 24 */     return AStarBase.zFromIndex(this.aStarWithTarget.getTargetPositionIndex());
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getDumpMapRegionX(int def) {
/* 29 */     return AStarBase.xFromIndex(this.aStarWithTarget.getTargetPositionIndex());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawMapFinish(@Nonnull StringBuilder[] map, int minX, int minZ) {
/* 34 */     super.drawMapFinish(map, minX, minZ);
/* 35 */     plot(this.aStarWithTarget.getTargetPositionIndex(), 'Ω', map, minX, minZ);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected String getExtraLogString(MotionController controller) {
/* 41 */     return String.format("end=%s dist=%s", new Object[] { AStarBase.positionIndexToString(this.aStarWithTarget.getTargetPositionIndex()), Float.valueOf(this.aStarWithTarget.getEvaluator().estimateToGoal(this.aStarWithTarget, this.aStarWithTarget.getStartPosition(), controller)) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\navigation\AStarDebugWithTarget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */