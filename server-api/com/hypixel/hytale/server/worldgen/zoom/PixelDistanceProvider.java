/*     */ package com.hypixel.hytale.server.worldgen.zoom;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PixelDistanceProvider
/*     */ {
/*     */   private static final int TABLE_SIZE = 8;
/*     */   @Nonnull
/*     */   protected final PixelProvider image;
/*     */   protected final int width;
/*     */   protected final int height;
/*     */   protected final int cellsX;
/*     */   protected final int cellsY;
/*     */   @Nonnull
/*     */   protected final IPixelSet[] table;
/*     */   @Nonnull
/*     */   protected final IntSet pixels;
/*     */   
/*     */   public PixelDistanceProvider(@Nonnull PixelProvider image) {
/*  29 */     this.image = image;
/*  30 */     this.width = image.getWidth();
/*  31 */     this.height = image.getHeight();
/*  32 */     this.cellsX = image.getWidth() / 8;
/*  33 */     this.cellsY = image.getHeight() / 8;
/*  34 */     this.table = new IPixelSet[this.cellsX * this.cellsY];
/*  35 */     this.pixels = (IntSet)new IntOpenHashSet();
/*  36 */     prepareSegmentTable();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public IntSet getColors() {
/*  41 */     return this.pixels;
/*     */   }
/*     */   
/*     */   public double distanceSqToDifferentPixel(double ox, double oy, int px, int py) {
/*  45 */     px = clampX(px);
/*  46 */     py = clampY(py);
/*  47 */     int color = this.image.getPixel(px, py);
/*  48 */     int cellX = px / 8, cellY = py / 8;
/*  49 */     double distance = Double.POSITIVE_INFINITY;
/*     */     
/*  51 */     int minX = Math.max(cellX - 1, 0), maxX = Math.min(cellX + 2, this.cellsX);
/*  52 */     int minY = Math.max(cellY - 1, 0), maxY = Math.min(cellY + 2, this.cellsY);
/*  53 */     for (int ix = minX; ix < maxX; ix++) {
/*  54 */       for (int iy = minY; iy < maxY; iy++) {
/*  55 */         double dist = distanceSqToDiffInSeq(ox, oy, color, ix, iy);
/*  56 */         if (dist < distance) {
/*  57 */           distance = dist;
/*     */         }
/*     */       } 
/*     */     } 
/*  61 */     return distance;
/*     */   }
/*     */   
/*     */   protected double distanceSqToDiffInSeq(double ox, double oy, int pixel, int cellX, int cellY) {
/*  65 */     double distSq = Double.POSITIVE_INFINITY;
/*  66 */     if (hasDifferentPixel(cellX, cellY, pixel)) {
/*  67 */       int offsetX = cellX * 8, offsetY = cellY * 8;
/*  68 */       for (int ix = 0; ix < 8; ix++) {
/*  69 */         int px = ix + offsetX;
/*  70 */         for (int iy = 0; iy < 8; iy++) {
/*  71 */           int py = iy + offsetY;
/*  72 */           if (pixel != this.image.getPixel(px, py)) {
/*  73 */             double dist = distanceSqToPixel(ox, oy, px, py);
/*  74 */             if (dist < distSq) {
/*  75 */               distSq = dist;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  81 */     return distSq;
/*     */   }
/*     */   
/*     */   protected boolean hasDifferentPixel(int cellX, int cellY, int pixel) {
/*  85 */     IPixelSet pixelSet = this.table[cellIndex(cellX, cellY)];
/*  86 */     return (!pixelSet.contains(pixel) || pixelSet.size() > 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepareSegmentTable() {
/*  91 */     for (int cellX = 0; cellX < this.cellsX; cellX++) {
/*  92 */       for (int cellY = 0; cellY < this.cellsY; cellY++) {
/*     */         
/*  94 */         IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
/*     */ 
/*     */         
/*  97 */         int offsetX = cellX * 8;
/*  98 */         int offsetY = cellY * 8;
/*  99 */         for (int ix = 0; ix < 8; ix++) {
/* 100 */           for (int iy = 0; iy < 8; iy++) {
/* 101 */             int x = ix + offsetX;
/* 102 */             int y = iy + offsetY;
/* 103 */             if (x < this.width && y < this.height) {
/* 104 */               intOpenHashSet.add(this.image.getPixel(x, y));
/*     */             }
/*     */           } 
/*     */         } 
/* 108 */         this.pixels.addAll((IntCollection)intOpenHashSet);
/*     */         
/* 110 */         if (intOpenHashSet.size() == 1) {
/* 111 */           this.table[cellIndex(cellX, cellY)] = new SinglePixelSet(intOpenHashSet.iterator().nextInt());
/*     */         } else {
/* 113 */           this.table[cellIndex(cellX, cellY)] = new MultiplePixelSet((IntSet)intOpenHashSet);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int clampX(int x) {
/* 120 */     if (x < 0) return 0; 
/* 121 */     if (x >= this.width) return this.width - 1; 
/* 122 */     return x;
/*     */   }
/*     */   
/*     */   protected int clampY(int y) {
/* 126 */     if (y < 0) return 0; 
/* 127 */     if (y >= this.height) return this.height - 1; 
/* 128 */     return y;
/*     */   }
/*     */   
/*     */   protected int cellIndex(int cellX, int cellY) {
/* 132 */     return cellX * this.cellsY + cellY;
/*     */   }
/*     */   
/*     */   private static double distanceSqToPixel(double ox, double oy, int px, int py) {
/* 136 */     double dx = Math.max(Math.max(px - ox, ox - px - 1.0D), 0.0D);
/* 137 */     double dy = Math.max(Math.max(py - oy, oy - py - 1.0D), 0.0D);
/* 138 */     return dx * dx + dy * dy;
/*     */   }
/*     */   
/*     */   private static interface IPixelSet {
/*     */     boolean contains(int param1Int);
/*     */     
/*     */     int size();
/*     */   }
/*     */   
/*     */   private static class SinglePixelSet implements IPixelSet {
/*     */     private final int pixel;
/*     */     
/*     */     SinglePixelSet(int pixel) {
/* 151 */       this.pixel = pixel;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(int pixel) {
/* 156 */       return (this.pixel == pixel);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 161 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 167 */       return "SinglePixelSet{pixel=" + this.pixel + "}";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MultiplePixelSet
/*     */     implements IPixelSet
/*     */   {
/*     */     private final IntSet pixels;
/*     */     
/*     */     MultiplePixelSet(IntSet pixels) {
/* 177 */       this.pixels = pixels;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(int pixel) {
/* 182 */       return this.pixels.contains(pixel);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 187 */       return this.pixels.size();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 193 */       return "MultiplePixelSet{pixels=" + String.valueOf(this.pixels) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zoom\PixelDistanceProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */