/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.shaders;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class RelationalShader<T> implements Shader<T> {
/*    */   @Nonnull
/*    */   private final Map<T, Shader<T>> relations;
/*    */   @Nonnull
/*    */   private final Shader<T> onMissingKey;
/*    */   
/*    */   public RelationalShader(@Nonnull Shader<T> onMissingKey) {
/* 14 */     this.onMissingKey = onMissingKey;
/* 15 */     this.relations = new HashMap<>(1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public RelationalShader<T> addRelation(@Nonnull T key, @Nonnull Shader<T> value) {
/* 21 */     this.relations.put(key, value);
/* 22 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public T shade(T current, long seed) {
/* 27 */     if (!this.relations.containsKey(current))
/* 28 */       return this.onMissingKey.shade(current, seed); 
/* 29 */     return ((Shader<T>)this.relations.get(current)).shade(current, seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public T shade(T current, long seedA, long seedB) {
/* 34 */     if (!this.relations.containsKey(current))
/* 35 */       return this.onMissingKey.shade(current, seedA, seedB); 
/* 36 */     return ((Shader<T>)this.relations.get(current)).shade(current, seedA, seedB);
/*    */   }
/*    */ 
/*    */   
/*    */   public T shade(T current, long seedA, long seedB, long seedC) {
/* 41 */     if (!this.relations.containsKey(current))
/* 42 */       return this.onMissingKey.shade(current, seedA, seedB, seedC); 
/* 43 */     return ((Shader<T>)this.relations.get(current)).shade(current, seedA, seedB, seedC);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 49 */     return "RelationalShader{relations=" + String.valueOf(this.relations) + ", onMissingKey=" + String.valueOf(this.onMissingKey) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\shaders\RelationalShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */