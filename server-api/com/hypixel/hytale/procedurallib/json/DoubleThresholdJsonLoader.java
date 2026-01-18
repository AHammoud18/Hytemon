/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.condition.DefaultDoubleThresholdCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.DoubleThreshold;
/*    */ import com.hypixel.hytale.procedurallib.condition.IDoubleThreshold;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoubleThresholdJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, IDoubleThreshold>
/*    */ {
/*    */   protected final boolean defaultValue;
/*    */   
/*    */   public DoubleThresholdJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 23 */     this(seed, dataFolder, json, true);
/*    */   }
/*    */   
/*    */   public DoubleThresholdJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, boolean defaultValue) {
/* 27 */     super(seed.append(".DoubleThreshold"), dataFolder, json);
/* 28 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IDoubleThreshold load() {
/* 34 */     if (this.json == null || this.json.isJsonNull())
/* 35 */       return this.defaultValue ? (IDoubleThreshold)DefaultDoubleThresholdCondition.DEFAULT_TRUE : (IDoubleThreshold)DefaultDoubleThresholdCondition.DEFAULT_FALSE; 
/* 36 */     if (this.json.isJsonPrimitive()) {
/* 37 */       double value = this.json.getAsDouble();
/* 38 */       return (IDoubleThreshold)new DoubleThreshold.Single(0.0D, value);
/*    */     } 
/* 40 */     JsonArray jsonArray = this.json.getAsJsonArray();
/* 41 */     if (jsonArray.size() <= 0) throw new IllegalArgumentException("Threshold array must contain at least one entry!"); 
/* 42 */     if (jsonArray.get(0).isJsonArray()) {
/* 43 */       DoubleThreshold.Single[] entries = new DoubleThreshold.Single[jsonArray.size()];
/* 44 */       for (int i = 0; i < entries.length; i++) {
/* 45 */         JsonArray jsonArrayEntry = jsonArray.get(i).getAsJsonArray();
/* 46 */         if (jsonArrayEntry.size() != 2) throw new IllegalArgumentException("Threshold array entries must have 2 numbers for lower/upper limit!"); 
/* 47 */         entries[i] = new DoubleThreshold.Single(jsonArrayEntry
/* 48 */             .get(0).getAsDouble(), jsonArrayEntry
/* 49 */             .get(1).getAsDouble());
/*    */       } 
/*    */       
/* 52 */       return (IDoubleThreshold)new DoubleThreshold.Multiple(entries);
/*    */     } 
/* 54 */     if (jsonArray.size() != 2) throw new IllegalArgumentException("Threshold array entries must have 2 numbers for lower/upper limit!"); 
/* 55 */     return (IDoubleThreshold)new DoubleThreshold.Single(jsonArray
/* 56 */         .get(0).getAsDouble(), jsonArray
/* 57 */         .get(1).getAsDouble());
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String ERROR_NO_ENTRY = "Threshold array must contain at least one entry!";
/*    */     public static final String ERROR_THRESHOLD_SIZE = "Threshold array entries must have 2 numbers for lower/upper limit!";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\DoubleThresholdJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */