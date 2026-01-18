/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseMaskCondition
/*    */   implements ICoordinateCondition
/*    */ {
/*    */   protected final NoiseProperty noiseMask;
/*    */   protected final IDoubleCondition condition;
/*    */   
/*    */   public NoiseMaskCondition(NoiseProperty noiseMask, IDoubleCondition condition) {
/* 16 */     this.noiseMask = noiseMask;
/* 17 */     this.condition = condition;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int y) {
/* 22 */     return this.condition.eval(this.noiseMask.get(seed, x, y));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int y, int z) {
/* 27 */     return this.condition.eval(this.noiseMask.get(seed, x, y, z));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 33 */     return "NoiseMaskCondition{noiseMask=" + String.valueOf(this.noiseMask) + ", condition=" + String.valueOf(this.condition) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\NoiseMaskCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */