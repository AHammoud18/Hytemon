/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MaterialPattern
/*    */   extends Pattern {
/* 10 */   private static final SpaceSize READ_SPACE_SIZE = new SpaceSize(new Vector3i(0, 0, 0), new Vector3i(1, 0, 1));
/*    */   
/*    */   private final Material material;
/*    */   
/*    */   public MaterialPattern(@Nonnull Material material) {
/* 15 */     this.material = material;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Pattern.Context context) {
/* 20 */     if (!context.materialSpace.isInsideSpace(context.position)) {
/* 21 */       return false;
/*    */     }
/*    */     
/* 24 */     Material material = (Material)context.materialSpace.getContent(context.position);
/* 25 */     return ((this.material.solid()).blockId == (material.solid()).blockId && 
/* 26 */       (this.material.fluid()).fluidId == (material.fluid()).fluidId);
/*    */   }
/*    */ 
/*    */   
/*    */   public SpaceSize readSpace() {
/* 31 */     return READ_SPACE_SIZE.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\MaterialPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */