/*     */ package com.hypixel.hytale.server.worldgen.chunk;
/*     */ 
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*     */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.prefab.CavePrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.PrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.unique.UniquePrefabGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.util.LogUtil;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZonePatternProvider;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValidationUtil
/*     */ {
/*     */   public static boolean isInvalid(@Nonnull ZonePatternProvider zonePatternProvider, @Nonnull Executor executor) {
/*  34 */     return ((Boolean)CompletableFuture.<Boolean>supplyAsync(() -> { Deque<String> trace = new ArrayDeque<>(); boolean invalid = false; for (Zone zone : zonePatternProvider.getZones()) trace.push("Zone[\"" + zone.name() + "\"]");  return Boolean.valueOf(invalid); }executor)
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
/*  46 */       .join()).booleanValue();
/*     */   }
/*     */   
/*     */   private static boolean isZoneInvalid(@Nonnull Zone zone, @Nonnull Deque<String> trace) {
/*  50 */     boolean invalid = false;
/*  51 */     for (UniquePrefabGenerator uniquePrefabGenerator : zone.uniquePrefabContainer().getGenerators()) {
/*  52 */       trace.push("UniquePrefabs[\"" + uniquePrefabGenerator.getName() + "\"]");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     for (Biome biome : zone.biomePatternGenerator().getBiomes()) {
/*  60 */       trace.push("Biome[\"" + biome.getName() + "\"]");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     if (zone.caveGenerator() != null) {
/*  68 */       for (CaveType caveType : zone.caveGenerator().getCaveTypes()) {
/*  69 */         trace.push("Cave[\"" + caveType.getName() + "\"].Entry");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     return invalid;
/*     */   }
/*     */   
/*     */   private static boolean isBiomeInvalid(@Nonnull Biome biome, @Nonnull Deque<String> trace) {
/*  82 */     boolean invalid = false;
/*  83 */     if (biome.getPrefabContainer() != null) {
/*  84 */       PrefabContainer.PrefabContainerEntry[] prefabContainerEntries = biome.getPrefabContainer().getEntries();
/*  85 */       for (int i = 0; i < prefabContainerEntries.length; i++) {
/*  86 */         trace.push("Prefabs[" + i + "]");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     return invalid;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isCaveNodeInvalid(@Nonnull CaveNodeType caveNodeType, @Nonnull Set<String> encounteredNodes, @Nonnull Deque<String> trace) {
/*  99 */     if (!encounteredNodes.add(caveNodeType.getName())) return false;
/*     */     
/* 101 */     boolean invalid = false;
/* 102 */     if (caveNodeType.getPrefabContainer() != null) {
/* 103 */       CavePrefabContainer.CavePrefabEntry[] cavePrefabEntries = caveNodeType.getPrefabContainer().getEntries();
/* 104 */       for (int j = 0; j < cavePrefabEntries.length; j++) {
/* 105 */         trace.push("Prefabs[" + j + "]");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     CaveNodeType.CaveNodeChildEntry[] children = caveNodeType.getChildren();
/* 115 */     for (int i = 0; i < children.length; i++) {
/* 116 */       CaveNodeType[] nodes = (CaveNodeType[])children[i].getTypes().internalKeys();
/* 117 */       for (int n = 0; n < nodes.length; n++) {
/* 118 */         trace.push("Children[" + i + "].Node[" + n + "]");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     return invalid;
/*     */   }
/*     */   
/*     */   private static boolean arePrefabsInvalid(@Nonnull IWeightedMap<WorldGenPrefabSupplier> prefabs, @Nonnull Deque<String> trace) {
/* 130 */     boolean invalid = false;
/* 131 */     WorldGenPrefabSupplier[] suppliers = (WorldGenPrefabSupplier[])prefabs.internalKeys();
/* 132 */     for (int i = 0; i < suppliers.length; i++) {
/* 133 */       trace.push("Prefabs[" + i + "]");
/*     */     }
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
/* 161 */     return invalid;
/*     */   }
/*     */   
/*     */   private static boolean isChildPrefabInvalid(@Nonnull PrefabBuffer.ChildPrefab childMarker, @Nonnull WorldGenPrefabLoader loader, @Nonnull Deque<String> trace) {
/*     */     WorldGenPrefabSupplier[] suppliers;
/*     */     try {
/* 167 */       suppliers = loader.get(childMarker.getPath());
/* 168 */     } catch (Throwable e) {
/* 169 */       ((HytaleLogger.Api)LogUtil.getLogger().at(Level.SEVERE).withCause(e)).log("Failed to resolve child prefab: %s loaded from %s", childMarker.getPath(), String.join(".", (Iterable)trace));
/* 170 */       return true;
/*     */     } 
/*     */     
/* 173 */     boolean invalid = false;
/* 174 */     for (WorldGenPrefabSupplier childSupplier : suppliers) {
/*     */       try {
/* 176 */         childSupplier.get();
/* 177 */       } catch (Throwable e) {
/* 178 */         invalid = true;
/* 179 */         ((HytaleLogger.Api)LogUtil.getLogger().at(Level.SEVERE).withCause(e)).log("Failed to load child prefab: %s loaded from %s", childSupplier.getName(), String.join(".", (Iterable)trace));
/*     */       } 
/*     */     } 
/* 182 */     return invalid;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\ValidationUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */