/*     */ package com.hypixel.hytale.builtin.buildertools.objimport;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TextureSampler
/*     */ {
/*  18 */   private static final Map<Path, BufferedImage> textureCache = new HashMap<>();
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
/*     */   public static BufferedImage loadTexture(@Nonnull Path path) {
/*  30 */     if (!Files.exists(path, new java.nio.file.LinkOption[0])) return null;
/*     */ 
/*     */     
/*  33 */     BufferedImage cached = textureCache.get(path);
/*  34 */     if (cached != null) return cached;
/*     */     
/*     */     try {
/*  37 */       BufferedImage image = ImageIO.read(path.toFile());
/*  38 */       if (image != null) {
/*  39 */         textureCache.put(path, image);
/*     */       }
/*  41 */       return image;
/*  42 */     } catch (IOException e) {
/*  43 */       return null;
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
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static int[] sampleAt(@Nonnull BufferedImage texture, float u, float v) {
/*  60 */     u -= (float)Math.floor(u);
/*  61 */     v -= (float)Math.floor(v);
/*     */ 
/*     */     
/*  64 */     v = 1.0F - v;
/*     */     
/*  66 */     int width = texture.getWidth();
/*  67 */     int height = texture.getHeight();
/*     */     
/*  69 */     int x = Math.min((int)(u * width), width - 1);
/*  70 */     int y = Math.min((int)(v * height), height - 1);
/*     */     
/*  72 */     int rgb = texture.getRGB(x, y);
/*  73 */     return new int[] { rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb & 0xFF };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int sampleAlphaAt(@Nonnull BufferedImage texture, float u, float v) {
/*  91 */     if (!texture.getColorModel().hasAlpha()) {
/*  92 */       return 255;
/*     */     }
/*     */     
/*  95 */     u -= (float)Math.floor(u);
/*  96 */     v -= (float)Math.floor(v);
/*  97 */     v = 1.0F - v;
/*     */     
/*  99 */     int width = texture.getWidth();
/* 100 */     int height = texture.getHeight();
/*     */     
/* 102 */     int x = Math.min((int)(u * width), width - 1);
/* 103 */     int y = Math.min((int)(v * height), height - 1);
/*     */     
/* 105 */     int rgba = texture.getRGB(x, y);
/* 106 */     return rgba >> 24 & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearCache() {
/* 113 */     textureCache.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static int[] getAverageColor(@Nonnull Path path) {
/* 125 */     if (!Files.exists(path, new java.nio.file.LinkOption[0])) return null;
/*     */     
/*     */     try {
/* 128 */       BufferedImage image = ImageIO.read(path.toFile());
/* 129 */       if (image == null) return null;
/*     */       
/* 131 */       long totalR = 0L, totalG = 0L, totalB = 0L;
/* 132 */       int count = 0;
/*     */       
/* 134 */       int width = image.getWidth();
/* 135 */       int height = image.getHeight();
/* 136 */       boolean hasAlpha = image.getColorModel().hasAlpha();
/*     */       
/* 138 */       for (int y = 0; y < height; y++) {
/* 139 */         for (int x = 0; x < width; x++) {
/* 140 */           int rgba = image.getRGB(x, y);
/*     */ 
/*     */           
/* 143 */           if (hasAlpha) {
/* 144 */             int alpha = rgba >> 24 & 0xFF;
/* 145 */             if (alpha == 0)
/*     */               continue; 
/*     */           } 
/* 148 */           int r = rgba >> 16 & 0xFF;
/* 149 */           int g = rgba >> 8 & 0xFF;
/* 150 */           int b = rgba & 0xFF;
/*     */           
/* 152 */           totalR += r;
/* 153 */           totalG += g;
/* 154 */           totalB += b;
/* 155 */           count++;
/*     */           continue;
/*     */         } 
/*     */       } 
/* 159 */       if (count == 0) return null;
/*     */       
/* 161 */       return new int[] { (int)(totalR / count), (int)(totalG / count), (int)(totalB / count) };
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 166 */     catch (IOException e) {
/* 167 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\objimport\TextureSampler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */