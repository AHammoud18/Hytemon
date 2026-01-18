/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.cartas;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.TriDoubleFunction;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder<R>
/*    */ {
/*    */   @Nonnull
/* 42 */   private final Map<Integer, R> rgbToTerrainMap = new HashMap<>();
/*    */   private BufferedImage bufferedImage;
/*    */   private boolean bufferedImageCheck;
/*    */   
/*    */   @Nonnull
/*    */   public ImageCarta<R> build() {
/* 48 */     if (!this.bufferedImageCheck || !this.noiseCheck)
/* 49 */       throw new IllegalStateException("incomplete builder"); 
/* 50 */     ImageCarta<R> instance = new ImageCarta<>();
/* 51 */     instance.rgbToTerrainMap = this.rgbToTerrainMap;
/* 52 */     instance.functionX = this.noiseX;
/* 53 */     instance.functionY = this.noiseY;
/* 54 */     instance.allPossibleValues = new ArrayList<>(1);
/* 55 */     instance.allPossibleValues.addAll(this.rgbToTerrainMap.values());
/*    */ 
/*    */     
/* 58 */     instance.width = this.bufferedImage.getWidth();
/* 59 */     instance.height = this.bufferedImage.getHeight();
/* 60 */     instance.rgbArray = new int[instance.width * instance.height];
/* 61 */     for (int x = 0; x < instance.width; x++) {
/* 62 */       for (int y = 0; y < instance.height; y++)
/* 63 */         instance.rgbArray[x + y * instance.width] = 0xFFFFFF & this.bufferedImage
/* 64 */           .getRGB(x, y); 
/* 65 */     }  return instance;
/*    */   }
/*    */   private TriDoubleFunction<Double> noiseX; private TriDoubleFunction<Double> noiseY; private boolean noiseCheck;
/*    */   
/*    */   @Nonnull
/*    */   public Builder<R> withImage(BufferedImage image) {
/* 71 */     Objects.requireNonNull(image);
/* 72 */     this.bufferedImage = image;
/* 73 */     this.bufferedImageCheck = true;
/* 74 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<R> withNoiseFunctions(TriDoubleFunction<Double> noiseX, TriDoubleFunction<Double> noiseY) {
/* 80 */     Objects.requireNonNull(noiseX);
/* 81 */     Objects.requireNonNull(noiseY);
/* 82 */     this.noiseX = noiseX;
/* 83 */     this.noiseY = noiseY;
/* 84 */     this.noiseCheck = true;
/* 85 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<R> addTerrainRgb(int rgb, @Nonnull R terrain) {
/* 91 */     this.rgbToTerrainMap.put(Integer.valueOf(rgb), terrain);
/* 92 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\cartas\ImageCarta$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */