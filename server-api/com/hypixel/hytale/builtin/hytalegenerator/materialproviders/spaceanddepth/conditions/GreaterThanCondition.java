/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class GreaterThanCondition
/*    */   implements SpaceAndDepthMaterialProvider.Condition {
/*    */   private final int threshold;
/*    */   @Nonnull
/*    */   private final ConditionParameter parameter;
/*    */   
/*    */   public GreaterThanCondition(int threshold, @Nonnull ConditionParameter parameter) {
/* 13 */     this.threshold = threshold;
/* 14 */     this.parameter = parameter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean qualifies(int x, int y, int z, int depthIntoFloor, int depthIntoCeiling, int spaceAboveFloor, int spaceBelowCeiling) {
/* 23 */     switch (this.parameter) { default: throw new MatchException(null, null);case SPACE_ABOVE_FLOOR: case SPACE_BELOW_CEILING: break; }  int contextValue = 
/*    */       
/* 25 */       spaceBelowCeiling;
/*    */     
/* 27 */     return (contextValue > this.threshold);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\spaceanddepth\conditions\GreaterThanCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */