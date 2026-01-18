/*    */ package com.hypixel.hytale.server.worldgen.benchmark;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGenBenchmark;
/*    */ import java.util.SortedMap;
/*    */ import java.util.TreeMap;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkWorldgenBenchmark
/*    */   implements IWorldGenBenchmark
/*    */ {
/*    */   @Nonnull
/* 20 */   private final ConcurrentHashMap<String, AtomicInteger> prefabCounts = new ConcurrentHashMap<>(); @Nonnull
/* 21 */   private final ConcurrentHashMap<String, AtomicInteger> caveNodeCounts = new ConcurrentHashMap<>();
/*    */   
/*    */   private boolean enabled = false;
/*    */ 
/*    */   
/*    */   public void start() {
/* 27 */     this.enabled = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 32 */     this.enabled = false;
/* 33 */     this.prefabCounts.clear();
/* 34 */     this.caveNodeCounts.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<String> buildReport() {
/* 40 */     SortedMap<String, Integer> map = new TreeMap<>(String::compareToIgnoreCase);
/*    */     
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append("Generated prefab counts: \n");
/* 44 */     this.prefabCounts.forEach((key, value) -> map.put(key, Integer.valueOf(value.get())));
/* 45 */     map.forEach((key, value) -> sb.append(key).append('\t').append(value).append('\n'));
/* 46 */     sb.append('\n');
/* 47 */     map.clear();
/*    */     
/* 49 */     sb.append("Generated cave node counts: \n");
/* 50 */     this.caveNodeCounts.forEach((key, value) -> map.put(key, Integer.valueOf(value.get())));
/* 51 */     map.forEach((key, value) -> sb.append(key).append('\t').append(value).append('\n'));
/* 52 */     sb.append('\n');
/* 53 */     map.clear();
/*    */     
/* 55 */     return CompletableFuture.completedFuture(sb.toString());
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 59 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public void registerPrefab(String name) {
/* 63 */     this.prefabCounts.computeIfAbsent(name, i -> new AtomicInteger(0));
/* 64 */     ((AtomicInteger)this.prefabCounts.get(name)).incrementAndGet();
/*    */   }
/*    */   
/*    */   public void registerCaveNode(String name) {
/* 68 */     this.caveNodeCounts.computeIfAbsent(name, i -> new AtomicInteger(0));
/* 69 */     ((AtomicInteger)this.caveNodeCounts.get(name)).incrementAndGet();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\benchmark\ChunkWorldgenBenchmark.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */