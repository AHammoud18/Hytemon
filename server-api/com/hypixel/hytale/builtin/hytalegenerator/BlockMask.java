/*    */ package com.hypixel.hytale.builtin.hytalegenerator;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockMask
/*    */ {
/* 17 */   private MaterialSet skippedBlocks = new MaterialSet();
/* 18 */   private MaterialSet defaultMask = new MaterialSet();
/* 19 */   private final List<MaterialSet> sourceBlocks = new ArrayList<>(0);
/* 20 */   private final List<MaterialSet> destinationBlocks = new ArrayList<>(0);
/*    */ 
/*    */   
/*    */   public boolean canPlace(@Nonnull Material material) {
/* 24 */     return !this.skippedBlocks.test(material);
/*    */   }
/*    */   
/*    */   public boolean canPlace(int materialHash) {
/* 28 */     return !this.skippedBlocks.test(materialHash);
/*    */   }
/*    */   
/*    */   public boolean canReplace(Material source, Material destination) {
/* 32 */     return canReplace(source.hashMaterialIds(), destination.hashMaterialIds());
/*    */   }
/*    */   
/*    */   public boolean canReplace(int sourceHash, int destinationHash) {
/* 36 */     for (int i = 0; i < this.sourceBlocks.size(); i++) {
/* 37 */       if (((MaterialSet)this.sourceBlocks.get(i)).test(sourceHash)) {
/* 38 */         return ((MaterialSet)this.destinationBlocks.get(i)).test(destinationHash);
/*    */       }
/*    */     } 
/* 41 */     return !this.defaultMask.test(destinationHash);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSkippedBlocks(@Nonnull MaterialSet materialSet) {
/* 47 */     this.skippedBlocks = materialSet;
/*    */   }
/*    */   
/*    */   public void putBlockMaskEntry(@Nonnull MaterialSet source, @Nonnull MaterialSet destination) {
/* 51 */     this.sourceBlocks.add(source);
/* 52 */     this.destinationBlocks.add(destination);
/*    */   }
/*    */   
/*    */   public void setDefaultMask(@Nonnull MaterialSet materialSet) {
/* 56 */     this.defaultMask = materialSet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\BlockMask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */