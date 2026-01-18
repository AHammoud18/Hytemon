/*    */ package com.hypixel.hytale.server.core.modules.collision;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.Comparator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicCollisionData
/*    */ {
/*    */   public static final Comparator<BasicCollisionData> COLLISION_START_COMPARATOR;
/*    */   
/*    */   static {
/* 14 */     COLLISION_START_COMPARATOR = Comparator.comparingDouble(a -> a.collisionStart);
/*    */   }
/* 16 */   public final Vector3d collisionPoint = new Vector3d();
/*    */   public double collisionStart;
/*    */   
/*    */   public void setStart(@Nonnull Vector3d point, double start) {
/* 20 */     this.collisionPoint.assign(point);
/* 21 */     this.collisionStart = start;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\BasicCollisionData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */