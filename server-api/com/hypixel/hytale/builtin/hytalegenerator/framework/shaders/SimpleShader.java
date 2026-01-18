/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.shaders;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SimpleShader<T> implements Shader<T> {
/*    */   @Nonnull
/*    */   private final T value;
/*    */   
/*    */   private SimpleShader(@Nonnull T value) {
/* 10 */     this.value = value;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T> SimpleShader<T> of(@Nonnull T value) {
/* 15 */     return new SimpleShader<>(value);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public T shade(T current, long seed) {
/* 21 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public T shade(T current, long seedA, long seedB) {
/* 27 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public T shade(T current, long seedA, long seedB, long seedC) {
/* 33 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 39 */     return "SimpleShader{value=" + String.valueOf(this.value) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\shaders\SimpleShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */