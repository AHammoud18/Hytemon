/*     */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleAVLTreeMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
/*     */ import java.util.LinkedList;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Entry
/*     */ {
/*     */   @Nonnull
/*     */   private final Object2DoubleMap<Vector3d> map;
/*     */   @Nonnull
/*     */   private final LinkedList<Vector3d> keyHistory;
/*     */   private final int size;
/*     */   
/*     */   public Entry(int size) {
/* 111 */     if (size < 0)
/* 112 */       throw new IllegalArgumentException("negative size"); 
/* 113 */     this.map = (Object2DoubleMap<Vector3d>)new Object2DoubleAVLTreeMap(new DensityReturnType.Vector3dComparator());
/* 114 */     this.keyHistory = new LinkedList<>();
/* 115 */     this.size = size;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Vector3d k) {
/* 119 */     return this.map.containsKey(k);
/*     */   }
/*     */   
/*     */   public double get(Vector3d k) {
/* 123 */     return this.map.getOrDefault(k, 0.0D);
/*     */   }
/*     */   
/*     */   public void put(Vector3d k, double v) {
/* 127 */     if (this.keyHistory.size() == this.size) {
/* 128 */       Vector3d oldKey = this.keyHistory.removeLast();
/* 129 */       this.map.removeDouble(oldKey);
/*     */     } 
/* 131 */     this.map.put(k, v);
/* 132 */     this.keyHistory.addFirst(k);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\positions\returntypes\DensityReturnType$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */