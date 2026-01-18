/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.condition.DefaultDoubleCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.DoubleThresholdCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.IDoubleCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.SingleDoubleCondition;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoubleConditionJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, IDoubleCondition>
/*    */ {
/*    */   protected final Boolean defaultValue;
/*    */   
/*    */   public DoubleConditionJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 24 */     this(seed.append(".DoubleCondition"), dataFolder, json, Boolean.valueOf(true));
/*    */   }
/*    */   
/*    */   public DoubleConditionJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json, Boolean defaultValue) {
/* 28 */     super(seed, dataFolder, json);
/* 29 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IDoubleCondition load() {
/* 35 */     if (this.json == null || this.json.isJsonNull()) {
/* 36 */       Objects.requireNonNull(this.defaultValue, "Default value is not set and condition is not defined.");
/* 37 */       return this.defaultValue.booleanValue() ? (IDoubleCondition)DefaultDoubleCondition.DEFAULT_TRUE : (IDoubleCondition)DefaultDoubleCondition.DEFAULT_FALSE;
/* 38 */     }  if (this.json.isJsonPrimitive() || (this.json.isJsonArray() && this.json.getAsJsonArray().size() == 1 && this.json.getAsJsonArray().get(0).isJsonPrimitive())) {
/* 39 */       double limit = this.json.getAsDouble();
/* 40 */       return (IDoubleCondition)new SingleDoubleCondition(limit);
/* 41 */     }  if (this.json.isJsonArray()) {
/* 42 */       return (IDoubleCondition)new DoubleThresholdCondition((new DoubleThresholdJsonLoader<>(this.seed, this.dataFolder, this.json))
/* 43 */           .load());
/*    */     }
/*    */     
/* 46 */     throw new Error(String.format("Failed to load \"%s\" as DoubleCondition", new Object[] { this.json }));
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String ERROR_NO_DEFAULT = "Default value is not set and condition is not defined.";
/*    */     public static final String ERROR_FAILED = "Failed to load \"%s\" as DoubleCondition";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\DoubleConditionJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */