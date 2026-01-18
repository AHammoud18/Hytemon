/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.supplier.DoubleRange;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleRangeJsonLoader<K extends SeedResource>
/*     */   extends JsonLoader<K, IDoubleRange>
/*     */ {
/*     */   protected final double default1;
/*     */   protected final double default2;
/*     */   @Nonnull
/*     */   protected final DoubleToDoubleFunction function;
/*     */   
/*     */   public DoubleRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/*  24 */     this(seed, dataFolder, json, 0.0D, d -> d);
/*     */   }
/*     */   
/*     */   public DoubleRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, DoubleToDoubleFunction function) {
/*  28 */     this(seed, dataFolder, json, 0.0D, function);
/*     */   }
/*     */   
/*     */   public DoubleRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, double default1) {
/*  32 */     this(seed, dataFolder, json, default1, default1, d -> d);
/*     */   }
/*     */   
/*     */   public DoubleRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, double default1, DoubleToDoubleFunction function) {
/*  36 */     this(seed, dataFolder, json, default1, default1, function);
/*     */   }
/*     */   
/*     */   public DoubleRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, double default1, double default2) {
/*  40 */     this(seed, dataFolder, json, default1, default2, d -> d);
/*     */   }
/*     */   
/*     */   public DoubleRangeJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, double default1, double default2, DoubleToDoubleFunction function) {
/*  44 */     super(seed.append(".DoubleRange"), dataFolder, json);
/*  45 */     this.default1 = default1;
/*  46 */     this.default2 = default2;
/*  47 */     this.function = Objects.<DoubleToDoubleFunction>requireNonNull(function);
/*     */   }
/*     */ 
/*     */   
/*     */   public IDoubleRange load() {
/*  52 */     if (this.json == null || this.json.isJsonNull()) {
/*  53 */       if (this.default1 == this.default2) {
/*  54 */         return (IDoubleRange)new DoubleRange.Constant(this.function
/*  55 */             .get(this.default1));
/*     */       }
/*     */       
/*  58 */       return (IDoubleRange)new DoubleRange.Normal(this.function
/*  59 */           .get(this.default1), this.function.get(this.default2));
/*     */     } 
/*     */     
/*  62 */     if (this.json.isJsonArray()) {
/*  63 */       JsonArray array = this.json.getAsJsonArray();
/*  64 */       if (array.size() != 1 && array.size() != 2) throw new IllegalStateException(String.format("Range array contains %s values. Only 1 or 2 entries are allowed.", new Object[] { Integer.valueOf(array.size()) })); 
/*  65 */       if (array.size() == 1) {
/*  66 */         return (IDoubleRange)new DoubleRange.Constant(this.function
/*  67 */             .get(array.get(0).getAsDouble()));
/*     */       }
/*     */       
/*  70 */       return (IDoubleRange)new DoubleRange.Normal(this.function
/*  71 */           .get(array.get(0).getAsDouble()), this.function
/*  72 */           .get(array.get(1).getAsDouble()));
/*     */     } 
/*     */     
/*  75 */     if (this.json.isJsonObject()) {
/*  76 */       if (has("Thresholds") && has("Values")) return loadThreshold();
/*     */       
/*  78 */       if (!has("Min")) throw new IllegalStateException("Minimum value of range is not defined. Keyword: Min"); 
/*  79 */       if (!has("Max")) throw new IllegalStateException("Maximum value of range is not defined. Keyword: Max"); 
/*  80 */       double min = get("Min").getAsDouble();
/*  81 */       double max = get("Max").getAsDouble();
/*  82 */       return (IDoubleRange)new DoubleRange.Normal(this.function
/*  83 */           .get(min), this.function.get(max));
/*     */     } 
/*  85 */     return (IDoubleRange)new DoubleRange.Constant(this.function
/*  86 */         .get(this.json.getAsDouble()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected IDoubleRange loadThreshold() {
/*  93 */     JsonArray thresholdsJson = get("Thresholds").getAsJsonArray();
/*  94 */     JsonArray valuesJson = get("Values").getAsJsonArray();
/*     */     
/*  96 */     double[] thresholds = new double[thresholdsJson.size()];
/*  97 */     double[] values = new double[thresholdsJson.size()];
/*  98 */     for (int i = 0; i < thresholds.length; i++) {
/*  99 */       thresholds[i] = thresholdsJson.get(i).getAsDouble();
/* 100 */       values[i] = valuesJson.get(i).getAsDouble();
/*     */     } 
/*     */     
/* 103 */     return (IDoubleRange)new DoubleRange.Multiple(thresholds, values);
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_MIN = "Min";
/*     */     public static final String KEY_MAX = "Max";
/*     */     public static final String KEY_THRESHOLDS = "Thresholds";
/*     */     public static final String KEY_VALUES = "Values";
/*     */     public static final String ERROR_ARRAY_SIZE = "Range array contains %s values. Only 1 or 2 entries are allowed.";
/*     */     public static final String ERROR_NO_MIN = "Minimum value of range is not defined. Keyword: Min";
/*     */     public static final String ERROR_NO_MAX = "Maximum value of range is not defined. Keyword: Max";
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface DoubleToDoubleFunction {
/*     */     double get(double param1Double);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\DoubleRangeJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */