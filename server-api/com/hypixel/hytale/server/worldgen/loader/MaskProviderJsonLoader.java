/*    */ package com.hypixel.hytale.server.worldgen.loader;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.math.vector.Vector2i;
/*    */ import com.hypixel.hytale.procedurallib.json.CoordinateRandomizerJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.MaskProvider;
/*    */ import com.hypixel.hytale.server.worldgen.zoom.FuzzyZoom;
/*    */ import com.hypixel.hytale.server.worldgen.zoom.PixelProvider;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MaskProviderJsonLoader
/*    */   extends JsonLoader<SeedStringResource, MaskProvider>
/*    */ {
/*    */   protected final Path file;
/*    */   protected final Vector2i zoomSize;
/*    */   protected final Vector2i worldOffset;
/*    */   
/*    */   public MaskProviderJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, Path file, Vector2i zoomSize, Vector2i worldOffset) {
/* 35 */     super(seed.append(".MaskProvider"), dataFolder, json);
/* 36 */     this.file = file;
/* 37 */     this.zoomSize = zoomSize;
/* 38 */     this.worldOffset = worldOffset;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaskProvider load() {
/*    */     try {
/* 45 */       BufferedImage mask = loadImage(this.file);
/* 46 */       return new MaskProvider(
/* 47 */           loadFuzzyZoom(mask));
/*    */     }
/* 49 */     catch (Throwable e) {
/* 50 */       throw new Error("Error while loading MaskProvider in " + String.valueOf(this.file.toAbsolutePath()), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static BufferedImage loadImage(@Nonnull Path file) throws IOException {
/*    */     try {
/* 60 */       return ImageIO.read(Files.newInputStream(file, new java.nio.file.OpenOption[0]));
/* 61 */     } catch (IOException e) {
/* 62 */       throw new IOException("Failed to load image " + String.valueOf(file), e);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected FuzzyZoom loadFuzzyZoom(@Nonnull BufferedImage mask) {
/* 68 */     return new FuzzyZoom((new CoordinateRandomizerJsonLoader(this.seed, this.dataFolder, this.json))
/* 69 */         .load(), new PixelProvider(mask), this.zoomSize
/* 70 */         .getX() / mask.getWidth(), this.zoomSize.getY() / mask.getHeight(), this.worldOffset.x, this.worldOffset.y);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\MaskProviderJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */