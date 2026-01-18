/*     */ package com.hypixel.hytale.server.worldgen.zoom;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.server.worldgen.util.LogUtil;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExactZoom
/*     */ {
/*     */   @Nonnull
/*     */   private final PixelProvider source;
/*     */   @Nonnull
/*     */   private final PixelDistanceProvider distanceProvider;
/*     */   private final double zoomX;
/*     */   private final double zoomY;
/*     */   private final int offsetX;
/*     */   private final int offsetY;
/*     */   
/*     */   public ExactZoom(@Nonnull PixelProvider source, double zoomX, double zoomY, int offsetX, int offsetY) {
/*  34 */     this.source = source;
/*  35 */     this.distanceProvider = new PixelDistanceProvider(source);
/*  36 */     this.zoomX = zoomX;
/*  37 */     this.zoomY = zoomY;
/*  38 */     this.offsetX = offsetX;
/*  39 */     this.offsetY = offsetY;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PixelDistanceProvider getDistanceProvider() {
/*  44 */     return this.distanceProvider;
/*     */   }
/*     */   
/*     */   public boolean inBounds(double x, double y) {
/*  48 */     x += this.offsetX;
/*  49 */     y += this.offsetY;
/*     */     
/*  51 */     if (x < 0.0D || y < 0.0D) {
/*  52 */       return false;
/*     */     }
/*  54 */     x /= this.zoomX;
/*  55 */     y /= this.zoomY;
/*     */     
/*  57 */     return ((this.source.getWidth() - 1) >= x && (this.source.getHeight() - 1) >= y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int generate(double x, double y) {
/*  62 */     x += this.offsetX;
/*  63 */     y += this.offsetY;
/*     */     
/*  65 */     x /= this.zoomX;
/*  66 */     y /= this.zoomY;
/*     */     
/*  68 */     int px = Math.max(0, Math.min(MathUtil.floor(x), this.source.getWidth() - 1));
/*  69 */     int py = Math.max(0, Math.min(MathUtil.floor(y), this.source.getHeight() - 1));
/*     */     
/*  71 */     return this.source.getPixel(px, py);
/*     */   }
/*     */   
/*     */   public double distanceToNextPixel(double x, double y) {
/*  75 */     x += this.offsetX;
/*  76 */     y += this.offsetY;
/*     */     
/*  78 */     x /= this.zoomX;
/*  79 */     y /= this.zoomY;
/*     */     
/*  81 */     return this.zoomX * Math.sqrt(this.distanceProvider.distanceSqToDifferentPixel(x, y, 
/*     */           
/*  83 */           (int)MathUtil.fastFloor(x), (int)MathUtil.fastFloor(y)));
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
/*     */   public ExactZoom generateUniqueZones(Zone.UniqueCandidate[] candidates, FastRandom random, List<Zone.Unique> zones) {
/*  95 */     PixelProvider source = this.source.copy();
/*     */     
/*  97 */     for (int i = 0; i < candidates.length; i++) {
/*  98 */       Zone.UniqueCandidate candidate = candidates[i];
/*  99 */       Vector2i pos = selectCandidatePosition(candidate, source, random);
/*     */       
/* 101 */       if (pos == null) {
/* 102 */         LogUtil.getLogger().at(Level.WARNING).log("Failed to place unique zone: %s", candidate.zone());
/*     */       }
/*     */       else {
/*     */         
/* 106 */         int radius = candidate.zone().radius();
/* 107 */         int radius2 = radius * radius;
/*     */ 
/*     */         
/* 110 */         for (int dy = -radius; dy <= radius; dy++) {
/* 111 */           for (int dx = -radius; dx <= radius; dx++) {
/* 112 */             if (dx * dx + dy * dy <= radius2) {
/* 113 */               source.setPixel(pos.x + dx, pos.y + dy, candidate.zone().color());
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 119 */         zones.add(new Zone.Unique(candidate.zone().zone(), CompletableFuture.completedFuture(new Vector2i((int)(pos.x * this.zoomX - this.offsetX), (int)(pos.y * this.zoomY - this.offsetY)))));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return new ExactZoom(source, this.zoomX, this.zoomY, this.offsetX, this.offsetY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Zone.UniqueCandidate[] generateUniqueZoneCandidates(Zone.UniqueEntry[] entries, int maxPositions) {
/* 136 */     Zone.UniqueCandidate[] candidates = new Zone.UniqueCandidate[entries.length];
/* 137 */     ArrayList<Vector2i> positions = new ArrayList<>();
/*     */     
/* 139 */     for (int i = 0; i < entries.length; i++) {
/* 140 */       Zone.UniqueEntry entry = entries[i];
/* 141 */       int radius = entry.radius();
/* 142 */       int searchRadius = radius + entry.padding();
/*     */ 
/*     */       
/* 145 */       positions.clear();
/*     */       
/* 147 */       for (int iy = searchRadius; iy < this.source.getHeight() - 1 - searchRadius; iy++) {
/* 148 */         for (int ix = searchRadius; ix < this.source.getWidth() - 1 - searchRadius; ix++) {
/* 149 */           if (testZoneFit(entry, this.source, ix, iy, searchRadius)) {
/* 150 */             positions.add(new Vector2i(ix, iy));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 155 */       int size = positions.size();
/* 156 */       if (size == 0)
/* 157 */         throw new Error("No parent matches found for unique zone entry: " + String.valueOf(entry)); 
/* 158 */       if (size > maxPositions) {
/*     */         
/* 160 */         int n = Math.max(1, size / maxPositions);
/* 161 */         int count = size / n;
/*     */         
/* 163 */         Vector2i[] arr = new Vector2i[count];
/* 164 */         for (int j = 0; j < count; j++) {
/* 165 */           arr[j] = positions.get(j * n);
/*     */         }
/*     */         
/* 168 */         candidates[i] = new Zone.UniqueCandidate(entry, arr);
/*     */       } else {
/* 170 */         candidates[i] = new Zone.UniqueCandidate(entry, (Vector2i[])positions.toArray(x$0 -> new Vector2i[x$0]));
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     return candidates;
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
/*     */   private static Vector2i selectCandidatePosition(Zone.UniqueCandidate candidate, PixelProvider source, FastRandom random) {
/* 186 */     Vector2i[] positions = candidate.positions();
/* 187 */     int searchRadius = candidate.zone().radius() + candidate.zone().padding();
/*     */     
/* 189 */     int len = positions.length;
/*     */ 
/*     */     
/* 192 */     while (len > 0) {
/* 193 */       int index = random.nextInt(len);
/* 194 */       Vector2i pos = positions[index];
/*     */ 
/*     */       
/* 197 */       if (!testZoneFit(candidate.zone(), source, pos.x, pos.y, searchRadius)) {
/*     */         
/* 199 */         Vector2i back = positions[len - 1];
/* 200 */         positions[len - 1] = pos;
/* 201 */         positions[index] = back;
/* 202 */         len--;
/*     */         
/*     */         continue;
/*     */       } 
/* 206 */       return pos;
/*     */     } 
/*     */     
/* 209 */     return null;
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
/*     */   private static boolean testZoneFit(Zone.UniqueEntry entry, PixelProvider source, int x, int y, int radius) {
/* 223 */     int radius2 = radius * radius;
/*     */     
/* 225 */     for (int dy = -radius; dy <= radius; dy++) {
/* 226 */       for (int dx = -radius; dx <= radius; dx++) {
/* 227 */         if (dx * dx + dy * dy <= radius2 && !entry.matchesParent(source.getPixel(x + dx, y + dy))) {
/* 228 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 233 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage exportImage() {
/* 238 */     BufferedImage image = new BufferedImage(this.source.getWidth(), this.source.getHeight(), 1);
/* 239 */     for (int x = 0; x < this.source.getWidth(); x++) {
/* 240 */       for (int y = 0; y < this.source.getHeight(); y++) {
/* 241 */         image.setRGB(x, y, this.source.getPixel(x, y));
/*     */       }
/*     */     } 
/* 244 */     return image;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 250 */     return "ExactZoom{source=" + String.valueOf(this.source) + ", distanceProvider=" + String.valueOf(this.distanceProvider) + ", zoomX=" + this.zoomX + ", zoomY=" + this.zoomY + ", offsetX=" + this.offsetX + ", offsetY=" + this.offsetY + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zoom\ExactZoom.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */