/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AndCondition
/*    */   implements SpaceAndDepthMaterialProvider.Condition {
/*    */   @Nonnull
/*    */   private final SpaceAndDepthMaterialProvider.Condition[] conditions;
/*    */   
/*    */   public AndCondition(@Nonnull List<SpaceAndDepthMaterialProvider.Condition> conditions) {
/* 13 */     this.conditions = new SpaceAndDepthMaterialProvider.Condition[conditions.size()];
/* 14 */     for (int i = 0; i < conditions.size(); i++) {
/* 15 */       this.conditions[i] = conditions.get(i);
/* 16 */       if (this.conditions[i] == null) {
/* 17 */         throw new IllegalArgumentException("conditions contains null element");
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean qualifies(int x, int y, int z, int depthIntoFloor, int depthIntoCeiling, int spaceAboveFloor, int spaceBelowCeiling) {
/* 27 */     for (SpaceAndDepthMaterialProvider.Condition c : this.conditions) {
/* 28 */       if (!c.qualifies(x, y, z, depthIntoFloor, depthIntoCeiling, spaceAboveFloor, spaceBelowCeiling))
/* 29 */         return false; 
/* 30 */     }  return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\spaceanddepth\conditions\AndCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */