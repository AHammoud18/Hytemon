/*    */ package com.hypixel.hytale.server.worldgen.biome;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntMaps;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class BiomeInterpolation
/*    */ {
/* 10 */   public static final Int2IntMap EMPTY_MAP = (Int2IntMap)new EmptyInt2IntMap();
/* 11 */   public static final BiomeInterpolation DEFAULT = new BiomeInterpolation(5, EMPTY_MAP);
/*    */   
/*    */   protected final int radius;
/*    */   protected final Int2IntMap biomeRadii2;
/*    */   
/*    */   protected BiomeInterpolation(int radius, Int2IntMap biomeRadii2) {
/* 17 */     this.radius = radius;
/* 18 */     this.biomeRadii2 = biomeRadii2;
/*    */   }
/*    */   
/*    */   public int getRadius() {
/* 22 */     return this.radius;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBiomeRadius2(int biome) {
/* 32 */     return this.biomeRadii2.get(biome);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 38 */     return "BiomeInterpolation{radius=" + this.radius + ", biomeRadii2=" + String.valueOf(this.biomeRadii2) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static BiomeInterpolation create(int radius, @Nonnull Int2IntMap biomeRadii2) {
/* 46 */     if (radius == DEFAULT.getRadius() && biomeRadii2.isEmpty()) return DEFAULT;
/*    */ 
/*    */     
/* 49 */     if (biomeRadii2.isEmpty()) { biomeRadii2 = EMPTY_MAP; }
/* 50 */     else { biomeRadii2.defaultReturnValue(radius * radius); }
/*    */     
/* 52 */     return new BiomeInterpolation(radius, biomeRadii2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static class EmptyInt2IntMap
/*    */     extends Int2IntMaps.EmptyMap
/*    */   {
/*    */     public int defaultReturnValue() {
/* 61 */       return 25;
/*    */     }
/*    */ 
/*    */     
/*    */     public int get(int k) {
/* 66 */       return 25;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\biome\BiomeInterpolation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */