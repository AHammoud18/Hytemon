/*    */ package com.hypixel.hytale.builtin.hytalegenerator.referencebundle;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiDouble2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BaseHeightReference
/*    */   extends Reference {
/*    */   @Nonnull
/*    */   private final BiDouble2DoubleFunction heightFunction;
/*    */   
/*    */   public BaseHeightReference(@Nonnull BiDouble2DoubleFunction heightFunction) {
/* 12 */     this.heightFunction = heightFunction;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public BiDouble2DoubleFunction getHeightFunction() {
/* 17 */     return this.heightFunction;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\referencebundle\BaseHeightReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */