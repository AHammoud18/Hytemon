/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FieldFunctionPattern
/*    */   extends Pattern {
/*    */   @Nonnull
/*    */   private final Density field;
/*    */   @Nonnull
/*    */   private final SpaceSize readSpaceSize;
/*    */   @Nonnull
/*    */   private final List<Delimiter> delimiters;
/*    */   
/*    */   public FieldFunctionPattern(@Nonnull Density field) {
/* 19 */     this.field = field;
/* 20 */     this.readSpaceSize = SpaceSize.empty();
/* 21 */     this.delimiters = new ArrayList<>(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Pattern.Context context) {
/* 26 */     Density.Context densityContext = new Density.Context(context);
/*    */     
/* 28 */     double density = this.field.process(densityContext);
/* 29 */     for (Delimiter d : this.delimiters) {
/* 30 */       if (d.isInside(density)) {
/* 31 */         return true;
/*    */       }
/*    */     } 
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize readSpace() {
/* 40 */     return this.readSpaceSize.clone();
/*    */   }
/*    */   
/*    */   public void addDelimiter(double min, double max) {
/* 44 */     Delimiter d = new Delimiter();
/* 45 */     d.min = min;
/* 46 */     d.max = max;
/* 47 */     this.delimiters.add(d);
/*    */   }
/*    */   
/*    */   private static class Delimiter {
/*    */     double min;
/*    */     double max;
/*    */     
/*    */     boolean isInside(double v) {
/* 55 */       return (v >= this.min && v < this.max);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\FieldFunctionPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */