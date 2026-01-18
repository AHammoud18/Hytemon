/*    */ package com.hypixel.hytale.server.worldgen.zone;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.HashUtil;
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.MaskProvider;
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
/*    */ public class ZonePatternGenerator
/*    */ {
/*    */   protected final IPointGenerator pointGenerator;
/*    */   protected final Zone[] zones;
/*    */   protected final Zone.Unique[] uniqueZones;
/*    */   protected final MaskProvider maskProvider;
/*    */   protected final ZoneColorMapping zoneColorMapping;
/*    */   
/*    */   public ZonePatternGenerator(IPointGenerator pointGenerator, Zone[] zones, Zone.Unique[] uniqueZones, MaskProvider maskProvider, ZoneColorMapping zoneColorMapping) {
/* 27 */     this.pointGenerator = pointGenerator;
/* 28 */     this.zones = zones;
/* 29 */     this.uniqueZones = uniqueZones;
/* 30 */     this.maskProvider = maskProvider;
/* 31 */     this.zoneColorMapping = zoneColorMapping;
/*    */   }
/*    */   
/*    */   public Zone[] getZones() {
/* 35 */     return this.zones;
/*    */   }
/*    */   
/*    */   public Zone.Unique[] getUniqueZones() {
/* 39 */     return this.uniqueZones;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ZoneGeneratorResult generate(int seed, double x, double z) {
/* 44 */     return generate(seed, x, z, new ZoneGeneratorResult());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ZoneGeneratorResult generate(int seed, double x, double z, @Nonnull ZoneGeneratorResult result) {
/* 49 */     double rx = this.maskProvider.getX(seed, x, z);
/* 50 */     double rz = this.maskProvider.getY(seed, x, z);
/* 51 */     int mask = this.maskProvider.get(seed, rx, rz) & 0xFFFFFF;
/* 52 */     Zone[] zoneArr = this.zoneColorMapping.get(mask);
/* 53 */     if (zoneArr == null) throw new Error("Unknown zone colour mapping for mask: " + mask); 
/* 54 */     if (zoneArr.length == 1) {
/* 55 */       double distance = Double.POSITIVE_INFINITY;
/* 56 */       if (this.maskProvider.inBounds(rx, rz)) {
/* 57 */         distance = this.maskProvider.distance(rx, rz);
/*    */       }
/*    */       
/* 60 */       result.setZone(zoneArr[0]);
/* 61 */       result.setBorderDistance(distance);
/*    */     } else {
/* 63 */       getZone(seed, x, z, result, zoneArr);
/*    */     } 
/*    */     
/* 66 */     return result;
/*    */   }
/*    */   
/*    */   protected void getZone(int seed, double x, double z, @Nonnull ZoneGeneratorResult result, @Nonnull Zone[] zoneArr) {
/* 70 */     ResultBuffer.ResultBuffer2d buf = this.pointGenerator.nearest2D(seed, x, z);
/* 71 */     int index = ((int)HashUtil.hash(seed, buf.ix, buf.iy) & Integer.MAX_VALUE) % zoneArr.length;
/*    */     
/* 73 */     result.setZone(zoneArr[index]);
/* 74 */     result.setBorderDistance(buf.distance);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 80 */     return "ZonePatternGenerator{pointGenerator=" + String.valueOf(this.pointGenerator) + ", zones=" + 
/*    */       
/* 82 */       Arrays.toString((Object[])this.zones) + ", maskProvider=" + String.valueOf(this.maskProvider) + ", zoneColorMapping=" + String.valueOf(this.zoneColorMapping) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zone\ZonePatternGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */