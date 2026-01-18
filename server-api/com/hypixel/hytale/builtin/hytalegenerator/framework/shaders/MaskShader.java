/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.shaders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MaskShader<T>
/*    */   implements Shader<T> {
/*    */   private final Shader<T> childShader;
/*    */   private final Predicate<T> mask;
/*    */   private SeedGenerator seedGenerator;
/*    */   
/*    */   private MaskShader(Predicate<T> mask, Shader<T> childShader, long seed) {
/* 14 */     this.mask = mask;
/* 15 */     this.childShader = childShader;
/* 16 */     this.seedGenerator = new SeedGenerator(seed);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T> Builder<T> builder(@Nonnull Class<T> dataType) {
/* 21 */     return new Builder<>();
/*    */   }
/*    */   
/*    */   public static class Builder<T> {
/*    */     private Shader<T> childShader;
/*    */     private Predicate<T> mask;
/* 27 */     private long seed = System.nanoTime();
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public MaskShader<T> build() {
/* 33 */       if (this.childShader == null || this.mask == null)
/* 34 */         throw new IllegalStateException("incomplete builder"); 
/* 35 */       return new MaskShader<>(this.mask, this.childShader, this.seed);
/*    */     }
/*    */     
/*    */     @Nonnull
/*    */     public Builder<T> withSeed(long seed) {
/* 40 */       this.seed = seed;
/* 41 */       return this;
/*    */     }
/*    */     
/*    */     @Nonnull
/*    */     public Builder<T> withMask(@Nonnull Predicate<T> mask) {
/* 46 */       this.mask = mask;
/* 47 */       return this;
/*    */     }
/*    */     
/*    */     @Nonnull
/*    */     public Builder<T> withChildShader(@Nonnull Shader<T> shader) {
/* 52 */       this.childShader = shader;
/* 53 */       return this;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public T shade(T current, long seed) {
/* 60 */     if (!this.mask.test(current)) return current; 
/* 61 */     return this.childShader.shade(current, seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public T shade(T current, long seedA, long seedB) {
/* 66 */     return shade(current, 0L);
/*    */   }
/*    */ 
/*    */   
/*    */   public T shade(T current, long seedA, long seedB, long seedC) {
/* 71 */     return shade(current, 0L);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 77 */     return "MaskShader{childShader=" + String.valueOf(this.childShader) + ", mask=" + String.valueOf(this.mask) + ", seedGenerator=" + String.valueOf(this.seedGenerator) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\shaders\MaskShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */