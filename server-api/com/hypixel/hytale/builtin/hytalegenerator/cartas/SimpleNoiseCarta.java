/*    */ package com.hypixel.hytale.builtin.hytalegenerator.cartas;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiCarta;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.rangemaps.DoubleRange;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.rangemaps.DoubleRangeMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SimpleNoiseCarta<T>
/*    */   extends BiCarta<T> {
/*    */   @Nonnull
/*    */   private final Density density;
/*    */   @Nonnull
/*    */   private final DoubleRangeMap<T> rangeMap;
/*    */   private final T defaultValue;
/*    */   
/*    */   public SimpleNoiseCarta(@Nonnull Density density, T defaultValue) {
/* 21 */     this.density = density;
/* 22 */     this.defaultValue = defaultValue;
/*    */     
/* 24 */     this.rangeMap = new DoubleRangeMap();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SimpleNoiseCarta<T> put(@Nonnull DoubleRange range, T value) {
/* 29 */     this.rangeMap.put(range, value);
/* 30 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public T apply(int x, int z, @Nonnull WorkerIndexer.Id id) {
/* 35 */     Density.Context context = new Density.Context();
/* 36 */     context.position = new Vector3d(x, 0.0D, z);
/* 37 */     context.workerId = id;
/*    */     
/* 39 */     double noiseValue = this.density.process(context);
/* 40 */     T value = (T)this.rangeMap.get(noiseValue);
/* 41 */     return (value == null) ? this.defaultValue : value;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<T> allPossibleValues() {
/* 47 */     List<T> list = this.rangeMap.values();
/* 48 */     list.add(this.defaultValue);
/* 49 */     return list;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\cartas\SimpleNoiseCarta.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */