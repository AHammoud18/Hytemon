/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class OffsetDensity
/*    */   extends Density {
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction offsetFunc;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public OffsetDensity(@Nonnull Double2DoubleFunction offsetFunction, Density input) {
/* 16 */     this.offsetFunc = offsetFunction;
/* 17 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 22 */     if (this.input == null)
/* 23 */       return this.offsetFunc.get(context.position.y); 
/* 24 */     return this.input.process(context) + this.offsetFunc.get(context.position.y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 29 */     if (inputs.length == 0) this.input = null; 
/* 30 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\OffsetDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */