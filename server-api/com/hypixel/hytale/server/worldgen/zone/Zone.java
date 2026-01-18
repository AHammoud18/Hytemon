/*     */ package com.hypixel.hytale.server.worldgen.zone;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.server.worldgen.biome.BiomePatternGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.container.UniquePrefabContainer;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class Zone
/*     */   extends Record
/*     */ {
/*     */   private final int id;
/*     */   private final String name;
/*     */   @Nonnull
/*     */   private final ZoneDiscoveryConfig discoveryConfig;
/*     */   @Nullable
/*     */   private final CaveGenerator caveGenerator;
/*     */   @Nonnull
/*     */   private final BiomePatternGenerator biomePatternGenerator;
/*     */   @Nonnull
/*     */   private final UniquePrefabContainer uniquePrefabContainer;
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/zone/Zone;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #22	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public Zone(int id, @Nonnull String name, @Nonnull ZoneDiscoveryConfig discoveryConfig, @Nullable CaveGenerator caveGenerator, @Nonnull BiomePatternGenerator biomePatternGenerator, @Nonnull UniquePrefabContainer uniquePrefabContainer) {
/*  45 */     this.id = id;
/*  46 */     this.name = name;
/*  47 */     this.discoveryConfig = discoveryConfig;
/*  48 */     this.caveGenerator = caveGenerator;
/*  49 */     this.biomePatternGenerator = biomePatternGenerator;
/*  50 */     this.uniquePrefabContainer = uniquePrefabContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int id() {
/*  58 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  66 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ZoneDiscoveryConfig discoveryConfig() {
/*  75 */     return this.discoveryConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BiomePatternGenerator biomePatternGenerator() {
/*  84 */     return this.biomePatternGenerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CaveGenerator caveGenerator() {
/*  93 */     return this.caveGenerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public UniquePrefabContainer uniquePrefabContainer() {
/* 102 */     return this.uniquePrefabContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 113 */     return "Zone{id=" + this.id + ", name='" + this.name + "', discoveryConfig=" + String.valueOf(this.discoveryConfig) + ", caveGenerator=" + String.valueOf(this.caveGenerator) + ", biomePatternGenerator=" + String.valueOf(this.biomePatternGenerator) + ", uniquePrefabContainer=" + String.valueOf(this.uniquePrefabContainer) + "}";
/*     */   }
/*     */   
/*     */   public static final class Unique extends Record { @Nonnull
/*     */     private final Zone zone;
/*     */     @Nonnull
/*     */     private final CompletableFuture<Vector2i> position;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/zone/Zone$Unique;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #129	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone$Unique;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/zone/Zone$Unique;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #129	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone$Unique;
/*     */     }
/*     */     
/* 129 */     public Unique(@Nonnull Zone zone, @Nonnull CompletableFuture<Vector2i> position) { this.zone = zone; this.position = position; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/zone/Zone$Unique;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #129	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone$Unique;
/* 129 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public Zone zone() { return this.zone; } @Nonnull public CompletableFuture<Vector2i> position() { return this.position; }
/*     */      public Vector2i getPosition() {
/* 131 */       return this.position.join();
/*     */     } }
/*     */   public static final class UniqueEntry extends Record { @Nonnull
/*     */     private final Zone zone; private final int color; private final int[] parent; private final int radius; private final int padding;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #144	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueEntry;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #144	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueEntry;
/*     */     }
/*     */     
/* 144 */     public UniqueEntry(@Nonnull Zone zone, int color, int[] parent, int radius, int padding) { this.zone = zone; this.color = color; this.parent = parent; this.radius = radius; this.padding = padding; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #144	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueEntry;
/* 144 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public Zone zone() { return this.zone; } public int color() { return this.color; } public int[] parent() { return this.parent; } public int radius() { return this.radius; } public int padding() { return this.padding; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 150 */     public static final UniqueEntry[] EMPTY_ARRAY = new UniqueEntry[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matchesParent(int color) {
/* 159 */       for (int p : this.parent) {
/* 160 */         if (p == color) {
/* 161 */           return true;
/*     */         }
/*     */       } 
/* 164 */       return false;
/*     */     } }
/*     */    public static final class UniqueCandidate extends Record { @Nonnull
/*     */     private final Zone.UniqueEntry zone; @Nonnull
/*     */     private final Vector2i[] positions; public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueCandidate;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #174	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueCandidate;
/*     */     }
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueCandidate;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #174	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueCandidate;
/*     */     }
/* 174 */     public UniqueCandidate(@Nonnull Zone.UniqueEntry zone, @Nonnull Vector2i[] positions) { this.zone = zone; this.positions = positions; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueCandidate;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #174	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/zone/Zone$UniqueCandidate;
/* 174 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public Zone.UniqueEntry zone() { return this.zone; } @Nonnull public Vector2i[] positions() { return this.positions; }
/* 175 */      public static final UniqueCandidate[] EMPTY_ARRAY = new UniqueCandidate[0]; }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zone\Zone.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */