/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SwitchDensity
/*    */   extends Density {
/*    */   private Density[] inputs;
/*    */   @Nonnull
/*    */   private final int[] switchStates;
/*    */   
/*    */   public SwitchDensity(@Nonnull List<Density> inputs, @Nonnull List<Integer> switchStates) {
/* 14 */     if (inputs.size() != switchStates.size()) {
/* 15 */       throw new IllegalArgumentException("inputs and switch states have different sizes");
/*    */     }
/* 17 */     this.inputs = new Density[inputs.size()];
/* 18 */     this.switchStates = new int[switchStates.size()];
/*    */     
/* 20 */     inputs.toArray(this.inputs);
/* 21 */     for (int i = 0; i < switchStates.size(); i++) {
/* 22 */       this.switchStates[i] = ((Integer)switchStates.get(i)).intValue();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 28 */     if (context == null) {
/* 29 */       return 0.0D;
/*    */     }
/*    */     
/* 32 */     int contextSwitchState = context.switchState;
/* 33 */     for (int i = 0; i < this.switchStates.length; i++) {
/* 34 */       if (this.switchStates[i] == contextSwitchState) {
/* 35 */         Density node = this.inputs[i];
/* 36 */         if (node == null) {
/* 37 */           return 0.0D;
/*    */         }
/* 39 */         return node.process(context);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 44 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 49 */     if (inputs.length == 0) {
/* 50 */       for (int i = 0; i < this.switchStates.length; i++) {
/* 51 */         this.inputs[i] = null;
/*    */       }
/*    */     }
/* 54 */     else if (inputs.length < this.inputs.length) {
/*    */       
/* 56 */       System.arraycopy(inputs, 0, this.inputs, 0, inputs.length);
/* 57 */       for (int i = inputs.length; i < this.inputs.length; i++) {
/* 58 */         this.inputs[i] = null;
/*    */       }
/*    */     } else {
/*    */       
/* 62 */       System.arraycopy(inputs, 0, this.inputs, 0, this.inputs.length);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SwitchDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */