/*     */ package com.hypixel.hytale.server.worldgen.climate;
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
/*     */ public class Rule
/*     */ {
/*     */   public final ClimateSearch.Range continent;
/*     */   public final ClimateSearch.Range temperature;
/*     */   public final ClimateSearch.Range intensity;
/*     */   public final ClimateSearch.Range fade;
/*     */   public final transient double sumWeight;
/*     */   
/*     */   public Rule(ClimateSearch.Range continent, ClimateSearch.Range temperature, ClimateSearch.Range intensity, ClimateSearch.Range fade) {
/* 103 */     this.continent = continent;
/* 104 */     this.temperature = temperature;
/* 105 */     this.intensity = intensity;
/* 106 */     this.fade = fade;
/* 107 */     this.sumWeight = continent.weight + temperature.weight + intensity.weight + fade.weight;
/*     */   }
/*     */   
/*     */   public double score(double continent, double temperature, double intensity, double fade) {
/* 111 */     double sumScore = 0.0D;
/* 112 */     sumScore += this.continent.score(continent) * this.continent.weight;
/* 113 */     sumScore += this.temperature.score(temperature) * this.temperature.weight;
/* 114 */     sumScore += this.intensity.score(intensity) * this.intensity.weight;
/* 115 */     sumScore += this.fade.score(fade) * this.fade.weight;
/* 116 */     return sumScore / this.sumWeight;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\ClimateSearch$Rule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */