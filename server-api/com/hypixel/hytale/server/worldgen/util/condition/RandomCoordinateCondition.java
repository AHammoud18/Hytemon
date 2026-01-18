/*    */ package com.hypixel.hytale.server.worldgen.util.condition;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.HashUtil;
/*    */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RandomCoordinateCondition
/*    */   implements ICoordinateCondition
/*    */ {
/*    */   private final double chance;
/*    */   
/*    */   public RandomCoordinateCondition(double chance) {
/* 17 */     this.chance = chance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int y) {
/* 22 */     return (HashUtil.random(seed, x, y) <= this.chance);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int y, int z) {
/* 27 */     return (HashUtil.random(seed, x, y, z) <= this.chance);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 33 */     return "RandomCoordinateCondition{chance=" + this.chance + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\RandomCoordinateCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */