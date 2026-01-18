/*    */ package com.hypixel.hytale.builtin.hytalegenerator.scanners;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiDouble2DoubleFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColumnLinearScanner
/*    */   extends Scanner
/*    */ {
/*    */   private final int minY;
/*    */   private final int maxY;
/*    */   private final boolean isRelativeToPosition;
/*    */   @Nullable
/*    */   private final BiDouble2DoubleFunction baseHeightFunction;
/*    */   private final int resultsCap;
/*    */   private final boolean topDownOrder;
/*    */   @Nonnull
/*    */   private final SpaceSize scanSpaceSize;
/*    */   
/*    */   public ColumnLinearScanner(int minY, int maxY, int resultsCap, boolean topDownOrder, boolean isRelativeToPosition, @Nullable BiDouble2DoubleFunction baseHeightFunction) {
/* 32 */     if (resultsCap < 0) throw new IllegalArgumentException();
/*    */     
/* 34 */     this.baseHeightFunction = baseHeightFunction;
/* 35 */     this.minY = minY;
/* 36 */     this.maxY = maxY;
/* 37 */     this.isRelativeToPosition = isRelativeToPosition;
/* 38 */     this.resultsCap = resultsCap;
/* 39 */     this.topDownOrder = topDownOrder;
/*    */     
/* 41 */     this.scanSpaceSize = new SpaceSize(new Vector3i(0, 0, 0), new Vector3i(1, 0, 1));
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<Vector3i> scan(@Nonnull Scanner.Context context) {
/*    */     int scanMinY, scanMaxY;
/* 47 */     ArrayList<Vector3i> validPositions = new ArrayList<>(this.resultsCap);
/*    */ 
/*    */ 
/*    */     
/* 51 */     if (this.isRelativeToPosition) {
/* 52 */       scanMinY = Math.max(context.position.y + this.minY, context.materialSpace.minY());
/* 53 */       scanMaxY = Math.min(context.position.y + this.maxY, context.materialSpace.maxY());
/* 54 */     } else if (this.baseHeightFunction != null) {
/* 55 */       int bedY = (int)this.baseHeightFunction.apply(context.position.x, context.position.z);
/* 56 */       scanMinY = Math.max(bedY + this.minY, context.materialSpace.minY());
/* 57 */       scanMaxY = Math.min(bedY + this.maxY, context.materialSpace.maxY());
/*    */     } else {
/* 59 */       scanMinY = Math.max(this.minY, context.materialSpace.minY());
/* 60 */       scanMaxY = Math.min(this.maxY, context.materialSpace.maxY());
/*    */     } 
/*    */     
/* 63 */     Vector3i patternPosition = context.position.clone();
/* 64 */     Pattern.Context patternContext = new Pattern.Context(patternPosition, context.materialSpace, context.workerId);
/*    */     
/* 66 */     if (this.topDownOrder) {
/* 67 */       for (patternPosition.y = scanMaxY - 1; patternPosition.y >= scanMinY; patternPosition.y--) {
/* 68 */         if (context.pattern.matches(patternContext)) {
/*    */ 
/*    */           
/* 71 */           validPositions.add(patternPosition.clone());
/*    */           
/* 73 */           if (validPositions.size() >= this.resultsCap)
/* 74 */             return validPositions; 
/*    */         } 
/*    */       } 
/*    */     } else {
/* 78 */       for (patternPosition.y = scanMinY; patternPosition.y < scanMaxY; patternPosition.y++) {
/* 79 */         if (context.pattern.matches(patternContext)) {
/*    */ 
/*    */           
/* 82 */           validPositions.add(patternPosition.clone());
/*    */           
/* 84 */           if (validPositions.size() >= this.resultsCap)
/* 85 */             return validPositions; 
/*    */         } 
/*    */       } 
/*    */     } 
/* 89 */     return validPositions;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize scanSpace() {
/* 95 */     return this.scanSpaceSize.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\scanners\ColumnLinearScanner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */