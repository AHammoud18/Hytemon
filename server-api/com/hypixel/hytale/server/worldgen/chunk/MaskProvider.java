/*    */ package com.hypixel.hytale.server.worldgen.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*    */ import com.hypixel.hytale.server.worldgen.zoom.FuzzyZoom;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MaskProvider
/*    */ {
/*    */   protected final FuzzyZoom fuzzyZoom;
/*    */   
/*    */   public MaskProvider(FuzzyZoom fuzzyZoom) {
/* 18 */     this.fuzzyZoom = fuzzyZoom;
/*    */   }
/*    */   
/*    */   public double getX(int seed, double x, double y) {
/* 22 */     return this.fuzzyZoom.getX(seed, x, y);
/*    */   }
/*    */   
/*    */   public double getY(int seed, double x, double y) {
/* 26 */     return this.fuzzyZoom.getY(seed, x, y);
/*    */   }
/*    */   
/*    */   public int get(int seed, double x, double y) {
/* 30 */     return this.fuzzyZoom.generate(x, y);
/*    */   }
/*    */   
/*    */   public double distance(double x, double y) {
/* 34 */     return this.fuzzyZoom.distance(x, y);
/*    */   }
/*    */   
/*    */   public boolean inBounds(double x, double y) {
/* 38 */     return this.fuzzyZoom.inBounds(x, y);
/*    */   }
/*    */   
/*    */   public FuzzyZoom getFuzzyZoom() {
/* 42 */     return this.fuzzyZoom;
/*    */   }
/*    */   
/*    */   public Zone.UniqueCandidate[] generateUniqueZoneCandidates(Zone.UniqueEntry[] entries, int maxPositions) {
/* 46 */     return this.fuzzyZoom.generateUniqueZoneCandidates(entries, maxPositions);
/*    */   }
/*    */   
/*    */   public MaskProvider generateUniqueZones(int seed, Zone.UniqueCandidate[] candidates, FastRandom random, List<Zone.Unique> zones) {
/* 50 */     return new MaskProvider(this.fuzzyZoom.generateUniqueZones(candidates, random, zones));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 56 */     return "MaskProvider{fuzzyZoom=" + String.valueOf(this.fuzzyZoom) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\MaskProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */