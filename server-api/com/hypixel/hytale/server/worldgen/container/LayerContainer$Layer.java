/*     */ package com.hypixel.hytale.server.worldgen.container;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class Layer<T extends LayerContainer.LayerEntry>
/*     */ {
/*     */   protected final T[] entries;
/*     */   protected final ICoordinateCondition mapCondition;
/*     */   protected final int environmentId;
/*     */   
/*     */   public Layer(T[] entries, ICoordinateCondition mapCondition, int environmentId) {
/*  78 */     this.entries = entries;
/*  79 */     this.mapCondition = mapCondition;
/*  80 */     this.environmentId = environmentId;
/*     */   }
/*     */   
/*     */   public int getEnvironmentId() {
/*  84 */     return this.environmentId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T getActiveEntry(int seed, int x, int z) {
/*  89 */     if (!this.mapCondition.eval(seed, x, z)) return null; 
/*  90 */     for (T entry : this.entries) {
/*  91 */       if (entry.isActive(seed, x, z)) {
/*  92 */         return entry;
/*     */       }
/*     */     } 
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 102 */     return "Layer{entries=" + Arrays.toString((Object[])this.entries) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\LayerContainer$Layer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */