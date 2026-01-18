/*    */ package com.hypixel.hytale.server.worldgen.zoom;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PixelProvider
/*    */ {
/*    */   @Nonnull
/*    */   protected final int[] pixels;
/*    */   protected final int width;
/*    */   protected final int height;
/*    */   
/*    */   public PixelProvider(@Nonnull BufferedImage image) {
/* 18 */     this.width = image.getWidth();
/* 19 */     this.height = image.getHeight();
/* 20 */     this.pixels = new int[this.width * this.height];
/* 21 */     for (int x = 0; x < image.getWidth(); x++) {
/* 22 */       for (int y = 0; y < image.getHeight(); y++) {
/* 23 */         setPixel(x, y, image.getRGB(x, y) & 0xFFFFFF);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public PixelProvider(PixelProvider other) {
/* 29 */     this.pixels = Arrays.copyOf(other.pixels, other.pixels.length);
/* 30 */     this.width = other.width;
/* 31 */     this.height = other.height;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 35 */     return this.width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 39 */     return this.height;
/*    */   }
/*    */   
/*    */   public int[] getPixels() {
/* 43 */     return this.pixels;
/*    */   }
/*    */   
/*    */   public void setPixel(int x, int y, int pixel) {
/* 47 */     this.pixels[arrIndex(x, y)] = pixel;
/*    */   }
/*    */   
/*    */   public int getPixel(int x, int y) {
/* 51 */     if (x < 0) { x = 0; }
/* 52 */     else if (x >= this.width) { x = this.width - 1; }
/* 53 */      if (y < 0) { y = 0; }
/* 54 */     else if (y >= this.height) { y = this.height - 1; }
/* 55 */      return this.pixels[arrIndex(x, y)];
/*    */   }
/*    */   
/*    */   protected int arrIndex(int x, int y) {
/* 59 */     return x * this.height + y;
/*    */   }
/*    */   
/*    */   public PixelProvider copy() {
/* 63 */     return new PixelProvider(this);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 69 */     return "PixelProvider{pixels=int[" + this.pixels.length + "], width=" + this.width + ", height=" + this.height + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zoom\PixelProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */