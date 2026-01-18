/*    */ package com.hypixel.hytale.server.core.modules.collision;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CharacterCollisionData
/*    */   extends BasicCollisionData
/*    */ {
/*    */   public Ref<EntityStore> entityReference;
/*    */   public boolean isPlayer;
/*    */   
/*    */   public void assign(@Nonnull Vector3d collisionPoint, double collisionVectorScale, Ref<EntityStore> entityReference, boolean isPlayer) {
/* 17 */     this.collisionPoint.assign(collisionPoint);
/* 18 */     this.collisionStart = collisionVectorScale;
/* 19 */     this.entityReference = entityReference;
/* 20 */     this.isPlayer = isPlayer;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\CharacterCollisionData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */