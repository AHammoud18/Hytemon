/*     */ package com.hypixel.hytale.server.worldgen.climate;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.server.worldgen.util.LogUtil;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UniqueClimateGenerator {
/*  16 */   public static final UniqueClimateGenerator EMPTY = new UniqueClimateGenerator(Entry.EMPTY_ARRAY, Unique.EMPTY_ARRAY);
/*  17 */   private static final int[] EMPTY_PARENTS = new int[0];
/*     */   private static final int MAX_PARENT_DEPTH = 10;
/*  19 */   private static final Vector2i DEFAULT_ORIGIN = new Vector2i(0, 0);
/*  20 */   private static final Vector2i[] EMPTY_POSITIONS = new Vector2i[0];
/*     */   
/*     */   protected final Entry[] entries;
/*     */   protected final Unique[] zones;
/*     */   
/*     */   public UniqueClimateGenerator(@Nonnull Entry[] entries) {
/*  26 */     this(entries, Unique.EMPTY_ARRAY);
/*     */   }
/*     */   
/*     */   public UniqueClimateGenerator(@Nonnull Entry[] entries, @Nonnull Unique[] zones) {
/*  30 */     this.entries = entries;
/*  31 */     this.zones = zones;
/*     */   }
/*     */   
/*     */   public Entry[] entries() {
/*  35 */     return this.entries;
/*     */   }
/*     */   
/*     */   public Unique[] zones() {
/*  39 */     return this.zones;
/*     */   }
/*     */   
/*     */   public int generate(int x, int y) {
/*  43 */     for (int i = 0; i < this.zones.length; i++) {
/*  44 */       if (this.zones[i].contains(x, y)) {
/*  45 */         return (this.zones[i]).color;
/*     */       }
/*     */     } 
/*  48 */     return -1;
/*     */   }
/*     */   
/*     */   public Zone.UniqueCandidate[] getCandidates(Map<String, Zone> zoneLookup) {
/*  52 */     if (this.entries.length == 0) {
/*  53 */       return Zone.UniqueCandidate.EMPTY_ARRAY;
/*     */     }
/*     */     
/*  56 */     Zone.UniqueCandidate[] candidates = new Zone.UniqueCandidate[this.entries.length];
/*  57 */     for (int i = 0; i < this.entries.length; i++) {
/*  58 */       Entry entry = this.entries[i];
/*  59 */       Zone zone = zoneLookup.get(entry.zone);
/*     */       
/*  61 */       if (zone == null) {
/*  62 */         throw new Error("Could not find zone: " + entry.zone);
/*     */       }
/*     */       
/*  65 */       candidates[i] = new Zone.UniqueCandidate(new Zone.UniqueEntry(zone, entry.color, EMPTY_PARENTS, entry.radius, 0), EMPTY_POSITIONS);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     return candidates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UniqueClimateGenerator apply(int seed, @Nonnull Zone.UniqueCandidate[] candidates, @Nonnull ClimateNoise noise, @Nonnull ClimateGraph graph, @Nonnull List<Zone.Unique> collector) {
/*  79 */     if (candidates.length != this.entries.length) {
/*  80 */       LogUtil.getLogger().at(Level.WARNING).log("Mismatched unique climate generator candidates: expected %d, got %d", this.entries.length, candidates.length);
/*  81 */       return this;
/*     */     } 
/*     */     
/*  84 */     Unique[] unique = new Unique[candidates.length];
/*  85 */     Object2ObjectOpenHashMap<String, Unique> lookup = new Object2ObjectOpenHashMap();
/*     */     
/*  87 */     for (int it = 0; it < 10 && lookup.size() < unique.length; it++) {
/*  88 */       for (int i = 0; i < this.entries.length; i++) {
/*  89 */         if (unique[i] == null) {
/*     */ 
/*     */ 
/*     */           
/*  93 */           Entry entry = this.entries[i];
/*  94 */           Unique parent = (Unique)lookup.get(entry.parent);
/*     */           
/*  96 */           if (entry.parent.isEmpty() || parent != null) {
/*     */ 
/*     */ 
/*     */             
/* 100 */             CompletableFuture<Vector2i> position = findZonePosition(seed, DEFAULT_ORIGIN, entry, parent, noise, graph);
/*     */             
/* 102 */             unique[i] = new Unique(entry.color, entry.radius, position);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 108 */             collector.add(new Zone.Unique(candidates[i]
/* 109 */                   .zone().zone(), position));
/*     */ 
/*     */ 
/*     */             
/* 113 */             lookup.put(entry.zone, unique[i]);
/*     */           } 
/*     */         } 
/*     */       } 
/* 117 */     }  return new UniqueClimateGenerator(this.entries, unique);
/*     */   }
/*     */   
/*     */   public UniqueClimateGenerator apply(int seed, @Nonnull ClimateNoise noise, @Nonnull ClimateGraph graph) {
/* 121 */     Unique[] unique = new Unique[this.entries.length];
/* 122 */     Object2ObjectOpenHashMap<String, Unique> lookup = new Object2ObjectOpenHashMap();
/*     */     
/* 124 */     for (int it = 0; it < 10 && lookup.size() < unique.length; it++) {
/* 125 */       for (int i = 0; i < this.entries.length; i++) {
/* 126 */         if (unique[i] == null) {
/*     */ 
/*     */ 
/*     */           
/* 130 */           Entry entry = this.entries[i];
/* 131 */           Unique parent = (Unique)lookup.get(entry.parent);
/*     */           
/* 133 */           if (entry.parent.isEmpty() || parent != null) {
/*     */ 
/*     */ 
/*     */             
/* 137 */             CompletableFuture<Vector2i> position = findZonePosition(seed, DEFAULT_ORIGIN, entry, parent, noise, graph);
/*     */             
/* 139 */             unique[i] = new Unique(entry.color, entry.radius, position);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 145 */             lookup.put(entry.zone, unique[i]);
/*     */           } 
/*     */         } 
/*     */       } 
/* 149 */     }  if (lookup.size() < unique.length) {
/* 150 */       LogUtil.getLogger()
/* 151 */         .at(Level.WARNING)
/* 152 */         .log("Could not resolve all unique climate zones, resolved %d out of %d", lookup.size(), unique.length);
/*     */       
/* 154 */       Entry[] newEntries = new Entry[lookup.size()];
/* 155 */       Unique[] newUnique = new Unique[lookup.size()];
/*     */       
/* 157 */       int index = 0;
/* 158 */       for (int i = 0; i < unique.length; i++) {
/* 159 */         if (unique[i] != null) {
/* 160 */           newEntries[index] = this.entries[i];
/* 161 */           newUnique[index] = unique[i];
/* 162 */           index++;
/*     */         } 
/*     */       } 
/*     */       
/* 166 */       return new UniqueClimateGenerator(newEntries, newUnique);
/*     */     } 
/*     */     
/* 169 */     return new UniqueClimateGenerator(this.entries, unique); } public static final class Entry extends Record { @Nonnull private final String zone; @Nonnull
/*     */     private final String parent; private final int color; private final int radius; @Nonnull
/*     */     private final Vector2i origin; private final int minDistance; private final int maxDistance; @Nonnull
/* 172 */     private final ClimateSearch.Rule rule; public Entry(@Nonnull String zone, @Nonnull String parent, int color, int radius, @Nonnull Vector2i origin, int minDistance, int maxDistance, @Nonnull ClimateSearch.Rule rule) { this.zone = zone; this.parent = parent; this.color = color; this.radius = radius; this.origin = origin; this.minDistance = minDistance; this.maxDistance = maxDistance; this.rule = rule; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #172	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 172 */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry; } @Nonnull public String zone() { return this.zone; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #172	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #172	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Entry;
/* 172 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public String parent() { return this.parent; } public int color() { return this.color; } public int radius() { return this.radius; } @Nonnull public Vector2i origin() { return this.origin; } public int minDistance() { return this.minDistance; } public int maxDistance() { return this.maxDistance; } @Nonnull public ClimateSearch.Rule rule() { return this.rule; }
/* 173 */      public static final Entry[] EMPTY_ARRAY = new Entry[0]; public static final String DEFAULT_PARENT = ""; }
/*     */   public static final class Unique extends Record { private final int color; private final int radius; private final int radius2; @Nonnull
/*     */     private final CompletableFuture<Vector2i> position;
/*     */     
/* 177 */     public Unique(int color, int radius, int radius2, @Nonnull CompletableFuture<Vector2i> position) { this.color = color; this.radius = radius; this.radius2 = radius2; this.position = position; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Unique;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #177	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Unique; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Unique;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #177	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Unique; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Unique;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #177	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/climate/UniqueClimateGenerator$Unique;
/* 177 */       //   0	8	1	o	Ljava/lang/Object; } public int color() { return this.color; } public int radius() { return this.radius; } public int radius2() { return this.radius2; } @Nonnull public CompletableFuture<Vector2i> position() { return this.position; }
/* 178 */      public static final Unique[] EMPTY_ARRAY = new Unique[0];
/*     */     
/*     */     public Unique(int color, int radius, @Nonnull CompletableFuture<Vector2i> position) {
/* 181 */       this(color, radius, radius * radius, position);
/*     */     }
/*     */     
/*     */     public boolean contains(int x, int y) {
/* 185 */       Vector2i pos = this.position.join();
/* 186 */       int dx = x - pos.x;
/* 187 */       int dy = y - pos.y;
/*     */       
/* 189 */       return (dx >= -this.radius && dy >= -this.radius && dx <= this.radius && dy <= this.radius && dx * dx + dy * dy <= this.radius2);
/*     */     } }
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
/*     */   protected static CompletableFuture<Vector2i> findZonePosition(int seed, Vector2i origin, @Nonnull Entry entry, @Nullable Unique parent, @Nonnull ClimateNoise noise, @Nonnull ClimateGraph graph) {
/* 202 */     if (parent != null)
/*     */     {
/* 204 */       return parent.position.thenCompose(pos -> findZonePosition(seed, pos, entry, null, noise, graph));
/*     */     }
/*     */     
/* 207 */     return ClimateSearch.search(seed, origin.x + entry.origin.x, origin.y + entry.origin.y, entry.minDistance, entry.maxDistance, entry.rule, noise, graph)
/* 208 */       .thenApply(result -> {
/*     */           LogUtil.getLogger().at(Level.INFO).log("Found location for unique zone '%s' -> %s", entry.zone, result.pretty());
/*     */           return result.position();
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\UniqueClimateGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */