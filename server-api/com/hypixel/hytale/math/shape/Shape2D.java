/*    */ package com.hypixel.hytale.math.shape;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Shape2D
/*    */ {
/*    */   default Box2D getBox(@Nonnull Vector2d position) {
/* 14 */     return getBox(position.getX(), position.getY());
/*    */   }
/*    */   
/*    */   Box2D getBox(double paramDouble1, double paramDouble2);
/*    */   
/*    */   boolean containsPosition(Vector2d paramVector2d1, Vector2d paramVector2d2);
/*    */   
/*    */   boolean containsPosition(Vector2d paramVector2d, double paramDouble1, double paramDouble2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Shape2D.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */