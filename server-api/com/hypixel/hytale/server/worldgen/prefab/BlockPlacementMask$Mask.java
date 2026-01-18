/*     */ package com.hypixel.hytale.server.worldgen.prefab;
/*     */ 
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
/*     */ public class Mask
/*     */   implements BlockPlacementMask.IMask
/*     */ {
/*     */   private final BlockPlacementMask.IEntry[] entries;
/*     */   
/*     */   public Mask(BlockPlacementMask.IEntry[] entries) {
/*  76 */     this.entries = entries;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReplace(int current, int fluid) {
/*  81 */     for (BlockPlacementMask.IEntry entry : this.entries) {
/*  82 */       if (entry.shouldHandle(current, fluid)) {
/*  83 */         return entry.shouldReplace();
/*     */       }
/*     */     } 
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  91 */     if (this == o) return true; 
/*  92 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  94 */     Mask mask = (Mask)o;
/*     */     
/*  96 */     return Arrays.equals((Object[])this.entries, (Object[])mask.entries);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Arrays.hashCode((Object[])this.entries);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 108 */     return "Mask{entries=" + Arrays.toString((Object[])this.entries) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\prefab\BlockPlacementMask$Mask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */