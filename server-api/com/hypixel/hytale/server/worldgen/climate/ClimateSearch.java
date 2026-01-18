/*     */ package com.hypixel.hytale.server.worldgen.climate;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import java.util.concurrent.CompletableFuture;
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
/*     */ public class ClimateSearch
/*     */ {
/*     */   public static final int STEP_SIZE = 100;
/*     */   public static final int DEFAULT_RADIUS = 5000;
/*     */   public static final int MAX_RADIUS = 10000;
/*     */   public static final double PI2 = 6.283185307179586D;
/*     */   public static final long SEED_OFFSET = 1659788403585L;
/*     */   public static final double TARGET_SCORE = 0.75D;
/*     */   
/*     */   public static CompletableFuture<Result> search(int seed, int cx, int cy, int startRadius, int searchRadius, @Nonnull Rule rule, @Nonnull ClimateNoise noise, @Nonnull ClimateGraph graph) {
/*  27 */     return CompletableFuture.supplyAsync(() -> {
/*     */           double bestScore = 0.0D;
/*     */           Vector2i bestPosition = new Vector2i(cx, cy);
/*     */           int radius = Math.min(searchRadius, 10000);
/*     */           FastRandom rng = new FastRandom(HashUtil.hash(seed, 1659788403585L));
/*     */           long start_ms = System.currentTimeMillis();
/*     */           for (int r = startRadius; r <= radius; r += 100) {
/*     */             int steps = (int)Math.floor(6.283185307179586D * r / 100.0D);
/*     */             double inc = 6.283185307179586D / steps;
/*     */             double off = 6.283185307179586D * rng.nextDouble();
/*     */             for (int i = 0; i < steps; i++) {
/*     */               double t = i * inc + off;
/*     */               int x = cx + (int)(Math.cos(t) * r);
/*     */               int y = cy + (int)(Math.sin(t) * r);
/*     */               double score = collect(seed, x, y, noise, graph, rule);
/*     */               if (score > bestScore) {
/*     */                 bestPosition.assign(x, y);
/*     */                 bestScore = score;
/*     */               } 
/*     */             } 
/*     */             if (bestScore >= 0.75D) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */           long time_ms = System.currentTimeMillis() - start_ms;
/*     */           return new Result(bestPosition, bestScore, time_ms);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double collect(int seed, int x, int y, ClimateNoise noise, ClimateGraph graph, Rule rule) {
/*  64 */     double continent = noise.continent.get(seed, x, y);
/*     */     
/*  66 */     if (!rule.continent.test(continent)) {
/*  67 */       return 0.0D;
/*     */     }
/*     */     
/*  70 */     double temperature = noise.temperature.get(seed, x, y);
/*  71 */     if (!rule.temperature.test(temperature)) {
/*  72 */       return 0.0D;
/*     */     }
/*     */     
/*  75 */     double intensity = noise.intensity.get(seed, x, y);
/*  76 */     if (!rule.intensity.test(intensity)) {
/*  77 */       return 0.0D;
/*     */     }
/*     */     
/*  80 */     double fade = graph.getFadeRaw(temperature, intensity);
/*  81 */     if (!rule.fade.test(fade)) {
/*  82 */       return 0.0D;
/*     */     }
/*     */     
/*  85 */     return rule.score(continent, temperature, intensity, fade);
/*     */   }
/*     */   public static final class Result extends Record { private final Vector2i position; private final double score; private final long time_ms;
/*  88 */     public Result(Vector2i position, double score, long time_ms) { this.position = position; this.score = score; this.time_ms = time_ms; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/climate/ClimateSearch$Result;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #88	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  88 */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/ClimateSearch$Result; } public Vector2i position() { return this.position; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/climate/ClimateSearch$Result;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #88	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/ClimateSearch$Result; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/climate/ClimateSearch$Result;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #88	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/climate/ClimateSearch$Result;
/*  88 */       //   0	8	1	o	Ljava/lang/Object; } public double score() { return this.score; } public long time_ms() { return this.time_ms; }
/*     */      public String pretty() {
/*  90 */       double score = this.score * 100.0D;
/*  91 */       return String.format("Position: {%d, %d}, Score: %.2f%%, Time: %dms", new Object[] { Integer.valueOf(this.position.x), Integer.valueOf(this.position.y), Double.valueOf(score), Long.valueOf(this.time_ms) });
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class Rule {
/*     */     public final ClimateSearch.Range continent;
/*     */     public final ClimateSearch.Range temperature;
/*     */     public final ClimateSearch.Range intensity;
/*     */     public final ClimateSearch.Range fade;
/*     */     public final transient double sumWeight;
/*     */     
/*     */     public Rule(ClimateSearch.Range continent, ClimateSearch.Range temperature, ClimateSearch.Range intensity, ClimateSearch.Range fade) {
/* 103 */       this.continent = continent;
/* 104 */       this.temperature = temperature;
/* 105 */       this.intensity = intensity;
/* 106 */       this.fade = fade;
/* 107 */       this.sumWeight = continent.weight + temperature.weight + intensity.weight + fade.weight;
/*     */     }
/*     */     
/*     */     public double score(double continent, double temperature, double intensity, double fade) {
/* 111 */       double sumScore = 0.0D;
/* 112 */       sumScore += this.continent.score(continent) * this.continent.weight;
/* 113 */       sumScore += this.temperature.score(temperature) * this.temperature.weight;
/* 114 */       sumScore += this.intensity.score(intensity) * this.intensity.weight;
/* 115 */       sumScore += this.fade.score(fade) * this.fade.weight;
/* 116 */       return sumScore / this.sumWeight;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Range
/*     */   {
/* 122 */     public static final Range DEFAULT = new Range(0.0D, 1.0D, 0.0D);
/*     */     
/*     */     public final double value;
/*     */     public final double radius;
/*     */     public final double weight;
/*     */     
/*     */     public Range(double value, double radius, double weight) {
/* 129 */       this.value = value;
/* 130 */       this.radius = radius;
/* 131 */       this.weight = weight;
/*     */     }
/*     */     
/*     */     public double score(double value) {
/* 135 */       double dif = Math.min(this.radius, Math.abs(value - this.value));
/* 136 */       return 1.0D - dif / this.radius;
/*     */     }
/*     */     
/*     */     public boolean test(double value) {
/* 140 */       return (Math.abs(value - this.value) <= this.radius);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\ClimateSearch.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */