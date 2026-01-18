/*    */ package com.hypixel.hytale.builtin.hytalegenerator.vectorproviders;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ConstantVectorProvider
/*    */   extends VectorProvider {
/*    */   @Nonnull
/*    */   private final Vector3d value;
/*    */   
/*    */   public ConstantVectorProvider(@Nonnull Vector3d value) {
/* 12 */     this.value = value.clone();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d process(@Nonnull VectorProvider.Context context) {
/* 17 */     return this.value.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\vectorproviders\ConstantVectorProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */