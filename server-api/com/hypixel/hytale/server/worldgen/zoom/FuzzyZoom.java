/*    */ package com.hypixel.hytale.server.worldgen.zoom;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import com.hypixel.hytale.procedurallib.random.ICoordinateRandomizer;
/*    */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FuzzyZoom
/*    */ {
/*    */   private final ICoordinateRandomizer randomizer;
/*    */   @Nonnull
/*    */   private final ExactZoom exactZoom;
/*    */   
/*    */   public FuzzyZoom(ICoordinateRandomizer randomizer, @Nonnull PixelProvider source, double zoomX, double zoomY, int offsetX, int offsetY) {
/* 21 */     this.randomizer = randomizer;
/* 22 */     this.exactZoom = new ExactZoom(source, zoomX, zoomY, offsetX, offsetY);
/*    */   }
/*    */   
/*    */   public FuzzyZoom(ICoordinateRandomizer randomizer, ExactZoom exactZoom) {
/* 26 */     this.randomizer = randomizer;
/* 27 */     this.exactZoom = exactZoom;
/*    */   }
/*    */   
/*    */   public double getX(int seed, double x, double y) {
/* 31 */     return this.randomizer.randomDoubleX(seed, x, y);
/*    */   }
/*    */   
/*    */   public double getY(int seed, double x, double y) {
/* 35 */     return this.randomizer.randomDoubleY(seed, x, y);
/*    */   }
/*    */   
/*    */   public int generate(double x, double y) {
/* 39 */     return this.exactZoom.generate(x, y);
/*    */   }
/*    */   
/*    */   public double distance(double x, double y) {
/* 43 */     return this.exactZoom.distanceToNextPixel(x, y);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ExactZoom getExactZoom() {
/* 48 */     return this.exactZoom;
/*    */   }
/*    */   
/*    */   public boolean inBounds(double x, double y) {
/* 52 */     return this.exactZoom.inBounds(x, y);
/*    */   }
/*    */   
/*    */   public Zone.UniqueCandidate[] generateUniqueZoneCandidates(Zone.UniqueEntry[] entries, int maxPositions) {
/* 56 */     return this.exactZoom.generateUniqueZoneCandidates(entries, maxPositions);
/*    */   }
/*    */   
/*    */   public FuzzyZoom generateUniqueZones(Zone.UniqueCandidate[] candidates, FastRandom random, List<Zone.Unique> zones) {
/* 60 */     return new FuzzyZoom(this.randomizer, this.exactZoom.generateUniqueZones(candidates, random, zones));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 66 */     return "FuzzyZoom{randomizer=" + String.valueOf(this.randomizer) + ", exactZoom=" + String.valueOf(this.exactZoom) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zoom\FuzzyZoom.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */