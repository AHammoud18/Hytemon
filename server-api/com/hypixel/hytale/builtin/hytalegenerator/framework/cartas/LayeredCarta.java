/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.cartas;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.TriCarta;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
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
/*    */ public class LayeredCarta<R>
/*    */   extends TriCarta<R>
/*    */ {
/*    */   @Nonnull
/*    */   private final List<TriCarta<R>> layers;
/*    */   @Nonnull
/*    */   private final List<R> allValues;
/*    */   @Nonnull
/*    */   private final R defaultValue;
/*    */   
/*    */   public LayeredCarta(@Nonnull R defaultValue) {
/* 31 */     Objects.requireNonNull(defaultValue);
/* 32 */     this.layers = new ArrayList<>(1);
/* 33 */     this.allValues = new ArrayList<>(1);
/*    */     
/* 35 */     this.defaultValue = defaultValue;
/* 36 */     this.allValues.add(defaultValue);
/*    */   }
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
/*    */   public R apply(int x, int y, int z, @Nonnull WorkerIndexer.Id id) {
/* 50 */     R result = this.defaultValue;
/* 51 */     for (TriCarta<R> layer : this.layers) {
/* 52 */       R value = (R)layer.apply(x, y, z, id);
/* 53 */       if (value == null)
/* 54 */         continue;  result = value;
/*    */     } 
/* 56 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<R> allPossibleValues() {
/* 66 */     return Collections.unmodifiableList(this.allValues);
/*    */   }
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
/*    */   @Nonnull
/*    */   public LayeredCarta<R> addLayer(@Nonnull TriCarta<R> layer) {
/* 80 */     Objects.requireNonNull(layer);
/* 81 */     this.layers.add(layer);
/* 82 */     this.allValues.addAll(layer.allPossibleValues());
/* 83 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 89 */     return "LayeredCarta{layers=" + String.valueOf(this.layers) + ", allValues=" + String.valueOf(this.allValues) + ", defaultValue=" + String.valueOf(this.defaultValue) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\cartas\LayeredCarta.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */