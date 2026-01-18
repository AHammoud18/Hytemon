/*     */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Splitter
/*     */ {
/*     */   @Nonnull
/*     */   public static Range[] split(@Nonnull Range range, int pieces) {
/*  16 */     if (pieces < 0)
/*  17 */       throw new IllegalArgumentException("negative number of pieces"); 
/*  18 */     int size = range.max - range.min;
/*  19 */     int pieceSize = size / pieces;
/*     */     
/*  21 */     if (size % pieces > 0)
/*     */     {
/*     */ 
/*     */       
/*  25 */       pieceSize++;
/*     */     }
/*  27 */     Range[] output = new Range[pieces];
/*     */     
/*  29 */     for (int i = 0; i < output.length; i++) {
/*  30 */       int min = Math.min(i * pieceSize + range.min, range.max);
/*  31 */       int max = Math.min(min + pieceSize, range.max);
/*  32 */       output[i] = new Range(min, max);
/*     */     } 
/*  34 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Area[] split(@Nonnull Area area, int pieces) {
/*  46 */     if (pieces < 1)
/*  47 */       throw new IllegalArgumentException("negative number of pieces"); 
/*  48 */     if (pieces == 1) return new Area[] { area };
/*     */     
/*  50 */     int sizeX = area.maxX - area.minX;
/*  51 */     int sizeZ = area.maxZ - area.minZ;
/*  52 */     if (pieces > sizeX) pieces = sizeX;
/*     */     
/*  54 */     Area[] output = new Area[pieces];
/*  55 */     if (pieces % 3 == 0) {
/*     */       
/*  57 */       Range[] rangesX = split(new Range(area.minX, area.maxX), 3);
/*     */       
/*  59 */       Range[] rangesZ = split(new Range(area.minZ, area.maxZ), pieces / 3);
/*  60 */       int o = 0;
/*  61 */       for (Range x : rangesX) {
/*  62 */         for (Range range : rangesZ)
/*  63 */           output[o++] = new Area(x.min, range.min, x.max, range.max); 
/*     */       } 
/*  65 */     } else if (pieces % 2 == 0) {
/*     */       
/*  67 */       Range[] rangesX = split(new Range(area.minX, area.maxX), 2);
/*     */       
/*  69 */       Range[] rangesZ = split(new Range(area.minZ, area.maxZ), pieces / 2);
/*  70 */       int o = 0;
/*  71 */       for (Range x : rangesX) {
/*  72 */         for (Range range : rangesZ) {
/*  73 */           output[o++] = new Area(x.min, range.min, x.max, range.max);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/*  78 */       Range[] ranges = split(new Range(area.minX, area.maxX), pieces);
/*  79 */       for (int i = 0; i < ranges.length; i++) {
/*  80 */         output[i] = new Area((ranges[i]).min, area.minZ, (ranges[i]).max, area.maxZ);
/*     */       }
/*     */     } 
/*  83 */     return output;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Area[] splitX(@Nonnull Area area, int pieces) {
/*  88 */     if (pieces < 1)
/*  89 */       throw new IllegalArgumentException("negative number of pieces"); 
/*  90 */     if (pieces == 1) return new Area[] { area };
/*     */     
/*  92 */     int sizeX = area.maxX - area.minX;
/*  93 */     int sizeZ = area.maxZ - area.minZ;
/*  94 */     if (pieces > sizeX) pieces = sizeX;
/*     */     
/*  96 */     Area[] output = new Area[pieces];
/*     */     
/*  98 */     Range[] ranges = split(new Range(area.minX, area.maxX), pieces);
/*  99 */     for (int i = 0; i < ranges.length; i++) {
/* 100 */       output[i] = new Area((ranges[i]).min, area.minZ, (ranges[i]).max, area.maxZ);
/*     */     }
/*     */     
/* 103 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Area
/*     */   {
/*     */     public final int minX;
/*     */     
/*     */     public final int minZ;
/*     */     public final int maxX;
/*     */     public final int maxZ;
/*     */     
/*     */     public Area(int minX, int minZ, int maxX, int maxZ) {
/* 116 */       if (maxX < minX || maxZ < minZ)
/* 117 */         throw new IllegalArgumentException("max smaller than min"); 
/* 118 */       this.minX = minX;
/* 119 */       this.minZ = minZ;
/* 120 */       this.maxX = maxX;
/* 121 */       this.maxZ = maxZ;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 127 */       return "Area{minX=" + this.minX + ", minZ=" + this.minZ + ", maxX=" + this.maxX + ", maxZ=" + this.maxZ + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Range
/*     */   {
/*     */     public final int min;
/*     */ 
/*     */     
/*     */     public final int max;
/*     */ 
/*     */ 
/*     */     
/*     */     public Range(int min, int max) {
/* 144 */       if (max < min)
/* 145 */         throw new IllegalArgumentException("max smaller than min"); 
/* 146 */       this.min = min;
/* 147 */       this.max = max;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\Splitter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */