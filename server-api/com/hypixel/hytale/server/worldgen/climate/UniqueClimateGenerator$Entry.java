/*     */ package com.hypixel.hytale.server.worldgen.climate;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Entry
/*     */   extends Record
/*     */ {
/*     */   @Nonnull
/*     */   private final String zone;
/*     */   @Nonnull
/*     */   private final String parent;
/*     */   private final int color;
/*     */   private final int radius;
/*     */   @Nonnull
/*     */   private final Vector2i origin;
/*     */   private final int minDistance;
/*     */   private final int maxDistance;
/*     */   @Nonnull
/*     */   private final ClimateSearch.Rule rule;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #172	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #172	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #172	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public Entry(@Nonnull String zone, @Nonnull String parent, int color, int radius, @Nonnull Vector2i origin, int minDistance, int maxDistance, @Nonnull ClimateSearch.Rule rule) {
/* 172 */     this.zone = zone; this.parent = parent; this.color = color; this.radius = radius; this.origin = origin; this.minDistance = minDistance; this.maxDistance = maxDistance; this.rule = rule; } @Nonnull public String zone() { return this.zone; } @Nonnull public String parent() { return this.parent; } public int color() { return this.color; } public int radius() { return this.radius; } @Nonnull public Vector2i origin() { return this.origin; } public int minDistance() { return this.minDistance; } public int maxDistance() { return this.maxDistance; } @Nonnull public ClimateSearch.Rule rule() { return this.rule; }
/* 173 */    public static final Entry[] EMPTY_ARRAY = new Entry[0];
/*     */   public static final String DEFAULT_PARENT = "";
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\UniqueClimateGenerator$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */