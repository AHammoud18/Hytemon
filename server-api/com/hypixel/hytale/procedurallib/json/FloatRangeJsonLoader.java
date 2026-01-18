/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.supplier.FloatRange;
/*    */ import com.hypixel.hytale.procedurallib.supplier.IFloatRange;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FloatRangeJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, IFloatRange>
/*    */ {
/*    */   protected final float default1;
/*    */   protected final float default2;
/*    */   @Nonnull
/*    */   protected final FloatToFloatFunction function;
/*    */   
/*    */   public FloatRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 24 */     this(seed, dataFolder, json, 0.0F, d -> d);
/*    */   }
/*    */   
/*    */   public FloatRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, FloatToFloatFunction function) {
/* 28 */     this(seed, dataFolder, json, 0.0F, function);
/*    */   }
/*    */   
/*    */   public FloatRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, float default1) {
/* 32 */     this(seed, dataFolder, json, default1, default1, d -> d);
/*    */   }
/*    */   
/*    */   public FloatRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, float default1, FloatToFloatFunction function) {
/* 36 */     this(seed, dataFolder, json, default1, default1, function);
/*    */   }
/*    */   
/*    */   public FloatRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, float default1, float default2) {
/* 40 */     this(seed, dataFolder, json, default1, default2, d -> d);
/*    */   }
/*    */   
/*    */   public FloatRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, float default1, float default2, FloatToFloatFunction function) {
/* 44 */     super(seed.append(".DoubleRange"), dataFolder, json);
/* 45 */     this.default1 = default1;
/* 46 */     this.default2 = default2;
/* 47 */     this.function = Objects.<FloatToFloatFunction>requireNonNull(function);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IFloatRange load() {
/* 53 */     if (this.json == null || this.json.isJsonNull()) {
/* 54 */       if (this.default1 == this.default2) {
/* 55 */         return (IFloatRange)new FloatRange.Constant(this.function
/* 56 */             .get(this.default1));
/*    */       }
/*    */       
/* 59 */       return (IFloatRange)new FloatRange.Normal(this.function
/* 60 */           .get(this.default1), this.function.get(this.default2));
/*    */     } 
/*    */     
/* 63 */     if (this.json.isJsonArray()) {
/* 64 */       JsonArray array = this.json.getAsJsonArray();
/* 65 */       if (array.size() != 1 && array.size() != 2) throw new IllegalStateException(String.format("Range array contains %s values. Only 1 or 2 entries are allowed.", new Object[] { Integer.valueOf(array.size()) })); 
/* 66 */       if (array.size() == 1) {
/* 67 */         return (IFloatRange)new FloatRange.Constant(this.function
/* 68 */             .get(array.get(0).getAsFloat()));
/*    */       }
/*    */       
/* 71 */       return (IFloatRange)new FloatRange.Normal(this.function
/* 72 */           .get(array.get(0).getAsFloat()), this.function
/* 73 */           .get(array.get(1).getAsFloat()));
/*    */     } 
/*    */     
/* 76 */     if (this.json.isJsonObject()) {
/* 77 */       if (!has("Min")) throw new IllegalStateException("Minimum value of range is not defined. Keyword: Min"); 
/* 78 */       if (!has("Max")) throw new IllegalStateException("Maximum value of range is not defined. Keyword: Max"); 
/* 79 */       float min = get("Min").getAsFloat();
/* 80 */       float max = get("Max").getAsFloat();
/* 81 */       return (IFloatRange)new FloatRange.Normal(this.function.get(min), this.function.get(max));
/*    */     } 
/* 83 */     return (IFloatRange)new FloatRange.Constant(this.function
/* 84 */         .get(this.json.getAsFloat()));
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_MIN = "Min";
/*    */     public static final String KEY_MAX = "Max";
/*    */     public static final String ERROR_ARRAY_SIZE = "Range array contains %s values. Only 1 or 2 entries are allowed.";
/*    */     public static final String ERROR_NO_MIN = "Minimum value of range is not defined. Keyword: Min";
/*    */     public static final String ERROR_NO_MAX = "Maximum value of range is not defined. Keyword: Max";
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface FloatToFloatFunction {
/*    */     float get(float param1Float);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\FloatRangeJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */