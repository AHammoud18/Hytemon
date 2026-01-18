/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.IDoubleCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.NoiseMaskCondition;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseMaskConditionJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ICoordinateCondition>
/*    */ {
/*    */   protected final boolean defaultValue;
/*    */   
/*    */   public NoiseMaskConditionJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 24 */     this(seed, dataFolder, json, true);
/*    */   }
/*    */   
/*    */   public NoiseMaskConditionJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, boolean defaultValue) {
/* 28 */     super(seed.append(".NoiseMaskCondition"), dataFolder, json);
/* 29 */     this.defaultValue = defaultValue;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ICoordinateCondition load() {
/*    */     NoiseMaskCondition noiseMaskCondition;
/* 35 */     DefaultCoordinateCondition defaultCoordinateCondition = this.defaultValue ? DefaultCoordinateCondition.DEFAULT_TRUE : DefaultCoordinateCondition.DEFAULT_FALSE;
/* 36 */     if (this.json != null && !this.json.isJsonNull()) {
/* 37 */       if (!has("Threshold")) throw new IllegalStateException("Could not find threshold data in noise mask. Keyword: Threshold");
/*    */       
/* 39 */       NoiseProperty noise = (new NoisePropertyJsonLoader<>(this.seed, this.dataFolder, this.json)).load();
/*    */       
/* 41 */       IDoubleCondition threshold = (new DoubleConditionJsonLoader<>(this.seed, this.dataFolder, get("Threshold"))).load();
/* 42 */       noiseMaskCondition = new NoiseMaskCondition(noise, threshold);
/*    */     } 
/* 44 */     return (ICoordinateCondition)noiseMaskCondition;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_THRESHOLD = "Threshold";
/*    */     public static final String ERROR_THRESHOLD = "Could not find threshold data in noise mask. Keyword: Threshold";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\NoiseMaskConditionJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */