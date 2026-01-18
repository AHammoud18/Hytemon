/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NotCondition
/*    */   implements SpaceAndDepthMaterialProvider.Condition {
/*    */   @Nonnull
/*    */   private final SpaceAndDepthMaterialProvider.Condition condition;
/*    */   
/*    */   public NotCondition(@Nonnull SpaceAndDepthMaterialProvider.Condition condition) {
/* 12 */     this.condition = condition;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean qualifies(int x, int y, int z, int depthIntoFloor, int depthIntoCeiling, int spaceAboveFloor, int spaceBelowCeiling) {
/* 21 */     return !this.condition.qualifies(x, y, z, depthIntoFloor, depthIntoCeiling, spaceAboveFloor, spaceBelowCeiling);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\spaceanddepth\conditions\NotCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */