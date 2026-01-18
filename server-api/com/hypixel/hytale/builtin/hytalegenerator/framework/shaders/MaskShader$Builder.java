/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.shaders;
/*    */ 
/*    */ import java.util.function.Predicate;
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
/*    */ public class Builder<T>
/*    */ {
/*    */   private Shader<T> childShader;
/*    */   private Predicate<T> mask;
/* 27 */   private long seed = System.nanoTime();
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaskShader<T> build() {
/* 33 */     if (this.childShader == null || this.mask == null)
/* 34 */       throw new IllegalStateException("incomplete builder"); 
/* 35 */     return new MaskShader<>(this.mask, this.childShader, this.seed);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Builder<T> withSeed(long seed) {
/* 40 */     this.seed = seed;
/* 41 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Builder<T> withMask(@Nonnull Predicate<T> mask) {
/* 46 */     this.mask = mask;
/* 47 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Builder<T> withChildShader(@Nonnull Shader<T> shader) {
/* 52 */     this.childShader = shader;
/* 53 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\shaders\MaskShader$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */