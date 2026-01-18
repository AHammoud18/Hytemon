/*    */ package com.hypixel.hytale.server.worldgen.zone;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.MaskProvider;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
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
/*    */ public class ZonePatternProvider
/*    */ {
/*    */   protected final IPointGenerator pointGenerator;
/*    */   protected final Zone[] zones;
/*    */   protected final Zone.UniqueCandidate[] uniqueZones;
/*    */   protected final MaskProvider maskProvider;
/*    */   protected final ZoneColorMapping zoneColorMapping;
/*    */   protected final int maxExtent;
/*    */   
/*    */   public ZonePatternProvider(IPointGenerator pointGenerator, Zone[] zones, Zone.UniqueCandidate[] uniqueZones, MaskProvider maskProvider, ZoneColorMapping zoneColorMapping) {
/* 29 */     this.pointGenerator = pointGenerator;
/* 30 */     this.zones = zones;
/* 31 */     this.uniqueZones = uniqueZones;
/* 32 */     this.maskProvider = maskProvider;
/* 33 */     this.zoneColorMapping = zoneColorMapping;
/* 34 */     this.maxExtent = getMaxExtent(zones);
/*    */     
/* 36 */     for (Zone.UniqueCandidate uniqueZone : uniqueZones) {
/* 37 */       zoneColorMapping.add(uniqueZone.zone().color(), uniqueZone.zone().zone());
/*    */     }
/*    */   }
/*    */   
/*    */   public int getMaxExtent() {
/* 42 */     return this.maxExtent;
/*    */   }
/*    */   
/*    */   public Zone[] getZones() {
/* 46 */     return this.zones;
/*    */   }
/*    */   
/*    */   public MaskProvider getMaskProvider() {
/* 50 */     return this.maskProvider;
/*    */   }
/*    */   
/*    */   public ZonePatternGenerator createGenerator(int seed) {
/* 54 */     FastRandom random = new FastRandom(seed);
/* 55 */     ArrayList<Zone.Unique> uniqueZones = new ArrayList<>(this.uniqueZones.length);
/* 56 */     MaskProvider maskProvider = this.maskProvider.generateUniqueZones(seed, this.uniqueZones, random, uniqueZones);
/*    */     
/* 58 */     return new ZonePatternGenerator(this.pointGenerator, this.zones, (Zone.Unique[])uniqueZones
/*    */ 
/*    */         
/* 61 */         .toArray(x$0 -> new Zone.Unique[x$0]), maskProvider, this.zoneColorMapping);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return "ZonePatternProvider{pointGenerator=" + String.valueOf(this.pointGenerator) + ", zones=" + 
/*    */       
/* 71 */       Arrays.toString((Object[])this.zones) + ", uniqueZones=" + 
/* 72 */       Arrays.toString((Object[])this.uniqueZones) + ", maskProvider=" + String.valueOf(this.maskProvider) + ", zoneColorMapping=" + String.valueOf(this.zoneColorMapping) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int getMaxExtent(@Nonnull Zone[] zones) {
/* 79 */     int max = 0;
/* 80 */     for (Zone zone : zones) {
/* 81 */       for (Biome biome : zone.biomePatternGenerator().getBiomes()) {
/* 82 */         if (biome.getPrefabContainer() != null) {
/* 83 */           max = Math.max(max, biome.getPrefabContainer().getMaxSize());
/*    */         }
/*    */       } 
/*    */     } 
/* 87 */     return max;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zone\ZonePatternProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */