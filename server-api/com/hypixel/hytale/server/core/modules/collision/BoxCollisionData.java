/*    */ package com.hypixel.hytale.server.core.modules.collision;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BoxCollisionData
/*    */   extends BasicCollisionData
/*    */ {
/*    */   public double collisionEnd;
/* 16 */   public final Vector3d collisionNormal = new Vector3d();
/*    */   
/*    */   public void setEnd(double collisionEnd, @Nonnull Vector3d collisionNormal) {
/* 19 */     this.collisionEnd = collisionEnd;
/* 20 */     this.collisionNormal.assign(collisionNormal);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\BoxCollisionData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */