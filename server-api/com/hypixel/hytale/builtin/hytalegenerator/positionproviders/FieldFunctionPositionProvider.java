/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FieldFunctionPositionProvider extends PositionProvider {
/*    */   @Nonnull
/*    */   private final Density field;
/*    */   @Nonnull
/*    */   private final List<Delimiter> delimiters;
/*    */   @Nonnull
/*    */   private final PositionProvider positionProvider;
/*    */   
/*    */   public FieldFunctionPositionProvider(@Nonnull Density field, @Nonnull PositionProvider positionProvider) {
/* 18 */     this.field = field;
/* 19 */     this.positionProvider = positionProvider;
/* 20 */     this.delimiters = new ArrayList<>();
/*    */   }
/*    */   
/*    */   public void addDelimiter(double min, double max) {
/* 24 */     Delimiter d = new Delimiter();
/* 25 */     d.min = min;
/* 26 */     d.max = max;
/* 27 */     this.delimiters.add(d);
/*    */   }
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 32 */     PositionProvider.Context childContext = new PositionProvider.Context(context);
/* 33 */     childContext.consumer = (p -> {
/*    */         Density.Context densityContext = new Density.Context();
/*    */         
/*    */         densityContext.position = p;
/*    */         
/*    */         densityContext.positionsAnchor = context.anchor;
/*    */         
/*    */         densityContext.workerId = context.workerId;
/*    */         double value = this.field.process(densityContext);
/*    */         for (Delimiter d : this.delimiters) {
/*    */           if (d.isInside(value)) {
/*    */             context.consumer.accept(p);
/*    */           }
/*    */         } 
/*    */       });
/* 48 */     this.positionProvider.positionsIn(childContext);
/*    */   }
/*    */   
/*    */   private static class Delimiter {
/*    */     double min;
/*    */     double max;
/*    */     
/*    */     boolean isInside(double v) {
/* 56 */       return (v >= this.min && v < this.max);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\FieldFunctionPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */