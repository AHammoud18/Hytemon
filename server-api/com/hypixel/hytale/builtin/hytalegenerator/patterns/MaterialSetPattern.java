/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.MaterialSet;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MaterialSetPattern
/*    */   extends Pattern {
/* 11 */   private static final SpaceSize READ_SPACE_SIZE = new SpaceSize(new Vector3i(0, 0, 0), new Vector3i(1, 0, 1));
/*    */   private final MaterialSet materialSet;
/*    */   
/*    */   public MaterialSetPattern(@Nonnull MaterialSet materialSet) {
/* 15 */     this.materialSet = materialSet;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Pattern.Context context) {
/* 20 */     if (!context.materialSpace.isInsideSpace(context.position)) {
/* 21 */       return false;
/*    */     }
/*    */     
/* 24 */     Material material = (Material)context.materialSpace.getContent(context.position);
/* 25 */     int hash = material.hashMaterialIds();
/*    */     
/* 27 */     return this.materialSet.test(hash);
/*    */   }
/*    */ 
/*    */   
/*    */   public SpaceSize readSpace() {
/* 32 */     return READ_SPACE_SIZE.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\MaterialSetPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */