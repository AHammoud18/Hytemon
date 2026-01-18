/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SwitchStateDensity
/*    */   extends Density
/*    */ {
/*    */   public static final int DEFAULT_SWITCH_STATE = 0;
/*    */   @Nullable
/*    */   private Density input;
/*    */   private final int switchState;
/*    */   
/*    */   public SwitchStateDensity(Density input, int switchState) {
/* 16 */     this.input = input;
/* 17 */     this.switchState = switchState;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 22 */     if (this.input == null) return 0.0D;
/*    */     
/* 24 */     Density.Context childContext = new Density.Context(context);
/* 25 */     childContext.switchState = this.switchState;
/*    */     
/* 27 */     return this.input.process(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 32 */     if (inputs.length == 0) this.input = null; 
/* 33 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SwitchStateDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */