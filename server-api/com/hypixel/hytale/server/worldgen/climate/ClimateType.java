/*    */ package com.hypixel.hytale.server.worldgen.climate;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ClimateType {
/*  8 */   public static final ClimateType[] EMPTY_ARRAY = new ClimateType[0];
/*    */   
/*    */   private static final int MAX_DEPTH = 10;
/*    */   
/*    */   public static final int IS_ISLAND = 536870912;
/*    */   
/*    */   public static final int IS_SHORE = 1073741824;
/*    */   
/*    */   public static final int IS_OCEAN = -2147483648;
/*    */   
/*    */   public static final int IS_MAINLAND = 0;
/*    */   
/*    */   public static final int IS_MAINLAND_SHORE = 1073741824;
/*    */   public static final int IS_MAINLAND_SHALLOW_OCEAN = -1073741824;
/*    */   public static final int IS_ISLAND_SHORE = 1610612736;
/*    */   public static final int IS_ISLAND_SHALLOW_OCEAN = -536870912;
/*    */   public static final int MASK = 536870911;
/*    */   @Nonnull
/*    */   public final String name;
/*    */   @Nonnull
/*    */   public final ClimateColor color;
/*    */   @Nonnull
/*    */   public final ClimateColor island;
/*    */   @Nonnull
/*    */   public final ClimatePoint[] points;
/*    */   @Nonnull
/*    */   public final ClimateType[] children;
/*    */   
/*    */   public ClimateType(@Nonnull String name, @Nonnull ClimateColor color, @Nonnull ClimateColor island, @Nonnull ClimatePoint[] points, @Nonnull ClimateType[] children) {
/* 37 */     this.name = name;
/* 38 */     this.color = color;
/* 39 */     this.island = island;
/* 40 */     this.points = points;
/* 41 */     this.children = children;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return this.name;
/*    */   }
/*    */   
/*    */   public static String name(@Nullable ClimateType parent, @Nonnull ClimateType type) {
/* 50 */     return (parent == null || parent == type) ? type.name : (parent.name + " / " + parent.name);
/*    */   }
/*    */   
/*    */   public static void walk(ClimateType type, Consumer<ClimateType> visitor) {
/* 54 */     walkRecursive(type, visitor, 0);
/*    */   }
/*    */   
/*    */   public static void walk(ClimateType[] types, Consumer<ClimateType> visitor) {
/* 58 */     for (ClimateType type : types) {
/* 59 */       walkRecursive(type, visitor, 0);
/*    */     }
/*    */   }
/*    */   
/*    */   public static int color(int id, @Nonnull ClimateGraph climate) {
/* 64 */     int flags = id & 0xE0000000;
/* 65 */     ClimateType type = climate.getType(id & 0x1FFFFFFF);
/*    */     
/* 67 */     switch (flags) { case -2147483648: case 1073741824: case -1073741824: case 536870912: case 1610612736: case -536870912:  }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 77 */       type.color.land;
/*    */   }
/*    */ 
/*    */   
/*    */   private static void walkRecursive(ClimateType type, Consumer<ClimateType> visitor, int depth) {
/* 82 */     if (depth >= 10) {
/*    */       return;
/*    */     }
/*    */     
/* 86 */     visitor.accept(type);
/*    */     
/* 88 */     for (ClimateType child : type.children)
/* 89 */       walkRecursive(child, visitor, depth + 1); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\ClimateType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */