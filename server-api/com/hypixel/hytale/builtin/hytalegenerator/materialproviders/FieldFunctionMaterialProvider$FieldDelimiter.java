/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldDelimiter<V>
/*    */ {
/*    */   double top;
/*    */   double bottom;
/*    */   MaterialProvider<V> materialProvider;
/*    */   
/*    */   public FieldDelimiter(@Nonnull MaterialProvider<V> materialProvider, double bottom, double top) {
/* 49 */     this.bottom = bottom;
/* 50 */     this.top = top;
/* 51 */     this.materialProvider = materialProvider;
/*    */   }
/*    */   
/*    */   boolean isInside(double fieldValue) {
/* 55 */     return (fieldValue < this.top && fieldValue >= this.bottom);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\FieldFunctionMaterialProvider$FieldDelimiter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */