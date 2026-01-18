/*     */ package com.hypixel.hytale.builtin.hytalegenerator.framework.cartas;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.TriCarta;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.TriDoubleFunction;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ public class ImageCarta<R>
/*     */   extends TriCarta<R>
/*     */ {
/*     */   private int[] rgbArray;
/*     */   private int width;
/*     */   private int height;
/*     */   private TriDoubleFunction<Double> functionX;
/*     */   private TriDoubleFunction<Double> functionY;
/*     */   private Map<Integer, R> rgbToTerrainMap;
/*     */   private List<R> allPossibleValues;
/*     */   
/*     */   public static class Builder<R>
/*     */   {
/*     */     @Nonnull
/*  42 */     private final Map<Integer, R> rgbToTerrainMap = new HashMap<>();
/*     */     private BufferedImage bufferedImage;
/*     */     private boolean bufferedImageCheck;
/*     */     
/*     */     @Nonnull
/*     */     public ImageCarta<R> build() {
/*  48 */       if (!this.bufferedImageCheck || !this.noiseCheck)
/*  49 */         throw new IllegalStateException("incomplete builder"); 
/*  50 */       ImageCarta<R> instance = new ImageCarta<>();
/*  51 */       instance.rgbToTerrainMap = this.rgbToTerrainMap;
/*  52 */       instance.functionX = this.noiseX;
/*  53 */       instance.functionY = this.noiseY;
/*  54 */       instance.allPossibleValues = new ArrayList<>(1);
/*  55 */       instance.allPossibleValues.addAll(this.rgbToTerrainMap.values());
/*     */ 
/*     */       
/*  58 */       instance.width = this.bufferedImage.getWidth();
/*  59 */       instance.height = this.bufferedImage.getHeight();
/*  60 */       instance.rgbArray = new int[instance.width * instance.height];
/*  61 */       for (int x = 0; x < instance.width; x++) {
/*  62 */         for (int y = 0; y < instance.height; y++)
/*  63 */           instance.rgbArray[x + y * instance.width] = 0xFFFFFF & this.bufferedImage
/*  64 */             .getRGB(x, y); 
/*  65 */       }  return instance;
/*     */     }
/*     */     private TriDoubleFunction<Double> noiseX; private TriDoubleFunction<Double> noiseY; private boolean noiseCheck;
/*     */     
/*     */     @Nonnull
/*     */     public Builder<R> withImage(BufferedImage image) {
/*  71 */       Objects.requireNonNull(image);
/*  72 */       this.bufferedImage = image;
/*  73 */       this.bufferedImageCheck = true;
/*  74 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Builder<R> withNoiseFunctions(TriDoubleFunction<Double> noiseX, TriDoubleFunction<Double> noiseY) {
/*  80 */       Objects.requireNonNull(noiseX);
/*  81 */       Objects.requireNonNull(noiseY);
/*  82 */       this.noiseX = noiseX;
/*  83 */       this.noiseY = noiseY;
/*  84 */       this.noiseCheck = true;
/*  85 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Builder<R> addTerrainRgb(int rgb, @Nonnull R terrain) {
/*  91 */       this.rgbToTerrainMap.put(Integer.valueOf(rgb), terrain);
/*  92 */       return this;
/*     */     }
/*     */   }
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
/*     */   @Nullable
/*     */   public R apply(int x, int y, int z, @Nonnull WorkerIndexer.Id tHreadId) {
/* 107 */     Objects.requireNonNull(Integer.valueOf(x));
/* 108 */     Objects.requireNonNull(Integer.valueOf(y));
/* 109 */     Objects.requireNonNull(Integer.valueOf(z));
/*     */     
/* 111 */     int sampleX = Calculator.toNearestInt(((Double)this.functionX.apply(x, y, z)).doubleValue() * this.width);
/* 112 */     sampleX = (sampleX < 0) ? 0 : Math.min(sampleX, this.width - 1);
/* 113 */     int sampleY = Calculator.toNearestInt(((Double)this.functionY.apply(x, y, z)).doubleValue() * this.height);
/* 114 */     sampleY = (sampleY < 0) ? 0 : Math.min(sampleY, this.height - 1);
/*     */     
/* 116 */     int rgb = this.rgbArray[sampleX + sampleY * this.width];
/* 117 */     if (!this.rgbToTerrainMap.containsKey(Integer.valueOf(rgb)))
/*     */     {
/* 119 */       return null; } 
/* 120 */     return this.rgbToTerrainMap.get(Integer.valueOf(rgb));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<R> allPossibleValues() {
/* 129 */     return this.allPossibleValues;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int greenFromRgb(int rgb) {
/* 134 */     return (rgb & 0xFF00) >> 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int redFromRgb(int rgb) {
/* 139 */     return (rgb & 0xFF0000) >> 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blueFromRgb(int rgb) {
/* 144 */     return rgb & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int coloursToRgb(int red, int green, int blue) {
/* 149 */     int rgb = red << 16;
/* 150 */     rgb += green << 8;
/* 151 */     rgb += blue;
/* 152 */     return rgb;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 159 */     return "ImageCarta{rgbArray=" + Arrays.toString(this.rgbArray) + ", width=" + this.width + ", height=" + this.height + ", functionX=" + String.valueOf(this.functionX) + ", functionY=" + String.valueOf(this.functionY) + ", rgbToTerrainMap=" + String.valueOf(this.rgbToTerrainMap) + ", allPossibleValues=" + String.valueOf(this.allPossibleValues) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\cartas\ImageCarta.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */