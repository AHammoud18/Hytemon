/*    */ package com.hypixel.hytale.server.worldgen.climate;
/*    */ 
/*    */ public class ClimateColor
/*    */ {
/*    */   public static final int UNSET = -1;
/*    */   public static final int ISLAND = 5588019;
/*    */   public static final int ISLAND_SHORE = 7820612;
/*    */   public static final int SHORE = 14535833;
/*    */   public static final int OCEAN = 33913;
/*    */   public static final int SHALLOW_OCEAN = 3377356;
/*    */   public final int land;
/*    */   public final int shore;
/*    */   public final int ocean;
/*    */   public final int shallowOcean;
/*    */   
/*    */   public ClimateColor(int land, int shore, int ocean, int shallowOcean) {
/* 17 */     this.land = land;
/* 18 */     this.shore = shore;
/* 19 */     this.ocean = ocean;
/* 20 */     this.shallowOcean = shallowOcean;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\ClimateColor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */