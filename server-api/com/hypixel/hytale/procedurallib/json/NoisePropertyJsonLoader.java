/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.property.CurveNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.FractalNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.GradientNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.MinNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseFormulaProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.NoisePropertyType;
/*     */ import com.hypixel.hytale.procedurallib.property.NormalizeNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.OffsetNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.RotateNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.ScaleNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.SingleNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.property.SumNoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.random.CoordinateRotator;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class NoisePropertyJsonLoader<K extends SeedResource> extends JsonLoader<K, NoiseProperty> {
/*  24 */   public NoisePropertyJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) { super(seed.append(".NoiseProperty"), dataFolder, json); } @Nonnull public NoiseProperty load() { SingleNoiseProperty singleNoiseProperty; NoiseFormulaProperty noiseFormulaProperty;
/*     */     CurveNoiseProperty curveNoiseProperty;
/*     */     ScaleNoiseProperty scaleNoiseProperty;
/*     */     NormalizeNoiseProperty normalizeNoiseProperty;
/*     */     OffsetNoiseProperty offsetNoiseProperty;
/*     */     RotateNoiseProperty rotateNoiseProperty;
/*     */     GradientNoiseProperty gradientNoiseProperty;
/*  31 */     NoisePropertyType type = null;
/*  32 */     if (has("Type")) {
/*  33 */       MaxNoiseProperty maxNoiseProperty; MinNoiseProperty minNoiseProperty; SumNoiseProperty sumNoiseProperty; ScaleNoiseProperty scaleNoiseProperty1; NoiseFormulaProperty noiseFormulaProperty1; MultiplyNoiseProperty multiplyNoiseProperty; DistortedNoiseProperty distortedNoiseProperty; NormalizeNoiseProperty normalizeNoiseProperty1; InvertNoiseProperty invertNoiseProperty; OffsetNoiseProperty offsetNoiseProperty1; RotateNoiseProperty rotateNoiseProperty1; GradientNoiseProperty gradientNoiseProperty1; CurveNoiseProperty curveNoiseProperty1; BlendNoiseProperty blendNoiseProperty; NoiseProperty arrayOfNoiseProperty1[], noiseProperty1, noiseProperties[], noise; double factors[], scale; NoiseFormulaProperty.NoiseFormula noiseFormula; IDoubleRange range; double offset; CoordinateRotator rotation; SumNoiseProperty.Entry[] entries; int i; double offsetX, offsetY, offsetZ; type = NoisePropertyType.valueOf(get("Type").getAsString());
/*  34 */       switch (type) {
/*     */         case MAX:
/*  36 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise"); 
/*  37 */           arrayOfNoiseProperty1 = loadNoiseProperties(get("Noise"));
/*  38 */           maxNoiseProperty = new MaxNoiseProperty(arrayOfNoiseProperty1);
/*     */           break;
/*     */         
/*     */         case MIN:
/*  42 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise"); 
/*  43 */           arrayOfNoiseProperty1 = loadNoiseProperties(get("Noise"));
/*  44 */           minNoiseProperty = new MinNoiseProperty(arrayOfNoiseProperty1);
/*     */           break;
/*     */         
/*     */         case SUM:
/*  48 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise"); 
/*  49 */           if (!has("Factors")) throw new IllegalStateException("Could not find factors for sum composed noise map. Keyword: Factors"); 
/*  50 */           arrayOfNoiseProperty1 = loadNoiseProperties(get("Noise"));
/*  51 */           factors = loadDoubleArray(get("Factors"), arrayOfNoiseProperty1.length);
/*  52 */           entries = new SumNoiseProperty.Entry[arrayOfNoiseProperty1.length];
/*  53 */           for (i = 0; i < entries.length; i++) {
/*  54 */             entries[i] = new SumNoiseProperty.Entry(arrayOfNoiseProperty1[i], factors[i]);
/*     */           }
/*  56 */           sumNoiseProperty = new SumNoiseProperty(entries);
/*     */           break;
/*     */         
/*     */         case SCALE:
/*  60 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise"); 
/*  61 */           if (!has("Scale")) throw new IllegalStateException("Could not find scale data for scaled noise map. Keyword: Scale");
/*     */           
/*  63 */           noiseProperty1 = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise"))).load();
/*  64 */           scale = get("Scale").getAsDouble();
/*  65 */           scaleNoiseProperty1 = new ScaleNoiseProperty(noiseProperty1, scale);
/*     */           break;
/*     */         
/*     */         case FORMULA:
/*  69 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise"); 
/*  70 */           if (!has("Formula")) throw new IllegalStateException("Could not find formula type for noise map. Keyword: Formula");
/*     */           
/*  72 */           noiseProperty1 = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise"))).load();
/*  73 */           noiseFormula = NoiseFormulaProperty.NoiseFormula.valueOf(get("Formula").getAsString());
/*  74 */           noiseFormulaProperty1 = new NoiseFormulaProperty(noiseProperty1, noiseFormula.getFormula());
/*     */           break;
/*     */         
/*     */         case MULTIPLY:
/*  78 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise"); 
/*  79 */           noiseProperties = loadNoiseProperties(get("Noise"));
/*  80 */           multiplyNoiseProperty = new MultiplyNoiseProperty(noiseProperties);
/*     */           break;
/*     */         
/*     */         case DISTORTED:
/*  84 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise"); 
/*  85 */           if (!has("Randomizer")) throw new IllegalStateException("Could not find randomizer for distorted noise map. Keyword: Randomizer");
/*     */           
/*  87 */           noise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise"))).load();
/*     */           
/*  89 */           distortedNoiseProperty = new DistortedNoiseProperty(noise, (new CoordinateRandomizerJsonLoader<>(this.seed, this.dataFolder, get("Randomizer"))).load());
/*     */           break;
/*     */         
/*     */         case NORMALIZE:
/*  93 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise"); 
/*  94 */           if (!has("Range")) throw new IllegalStateException("Could not find range data for normalized noise map. Keyword: Range");
/*     */           
/*  96 */           noise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise"))).load();
/*     */           
/*  98 */           range = (new DoubleRangeJsonLoader<>(this.seed, this.dataFolder, get("Range"))).load();
/*  99 */           normalizeNoiseProperty1 = new NormalizeNoiseProperty(noise, range.getValue(0.0D), range.getValue(1.0D) - range.getValue(0.0D));
/*     */           break;
/*     */         
/*     */         case INVERT:
/* 103 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise");
/*     */           
/* 105 */           noise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise"))).load();
/* 106 */           invertNoiseProperty = new InvertNoiseProperty(noise);
/*     */           break;
/*     */         
/*     */         case OFFSET:
/* 110 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise");
/*     */           
/* 112 */           noise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise"))).load();
/* 113 */           offset = has("Offset") ? get("Offset").getAsDouble() : 0.0D;
/* 114 */           offsetX = has("OffsetX") ? get("OffsetX").getAsDouble() : offset;
/* 115 */           offsetY = has("OffsetY") ? get("OffsetY").getAsDouble() : offset;
/* 116 */           offsetZ = has("OffsetZ") ? get("OffsetZ").getAsDouble() : offset;
/* 117 */           offsetNoiseProperty1 = new OffsetNoiseProperty(noise, offsetX, offsetY, offsetZ);
/*     */           break;
/*     */         
/*     */         case ROTATE:
/* 121 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise"); 
/* 122 */           if (!has("Rotate")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise");
/*     */           
/* 124 */           noise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise"))).load();
/*     */ 
/*     */           
/* 127 */           rotation = (new CoordinateRotatorJsonLoader<>(this.seed, this.dataFolder, get("Rotate"))).load();
/*     */           
/* 129 */           rotateNoiseProperty1 = new RotateNoiseProperty(noise, rotation);
/*     */           break;
/*     */         
/*     */         case GRADIENT:
/* 133 */           if (!has("Noise")) throw new IllegalStateException("Could not find noise map data. Keyword: Noise");
/*     */ 
/*     */           
/* 136 */           noise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise"))).load();
/*     */ 
/*     */           
/* 139 */           gradientNoiseProperty1 = (new GradientNoisePropertyJsonLoader<>(this.seed, this.dataFolder, this.json, noise)).load();
/*     */           break;
/*     */ 
/*     */         
/*     */         case CURVE:
/* 144 */           curveNoiseProperty1 = (new CurveNoisePropertyJsonLoader<>(this.seed, this.dataFolder, this.json, null)).load();
/*     */           break;
/*     */ 
/*     */         
/*     */         case BLEND:
/* 149 */           blendNoiseProperty = (new BlendNoisePropertyJsonLoader<>(this.seed, this.dataFolder, this.json)).load();
/*     */           break;
/*     */         
/*     */         default:
/* 153 */           throw new Error(String.format("Could not find instructions for noise property type: %s", new Object[] { type }));
/*     */       } 
/*     */     } else {
/* 156 */       NoiseFunction noiseFunction = newNoiseFunctionJsonLoader(this.seed, this.dataFolder, this.json).load();
/* 157 */       if (has("Octaves")) {
/* 158 */         FractalNoiseProperty.FractalMode fractalMode = has("FractalMode") ? FractalNoiseProperty.FractalMode.valueOf(get("FractalMode").getAsString()) : Constants.DEFAULT_FRACTAL_MODE;
/* 159 */         int octaves = get("Octaves").getAsInt();
/* 160 */         double lacunarity = has("Lacunarity") ? get("Lacunarity").getAsDouble() : 2.0D;
/* 161 */         double persistence = has("Persistence") ? get("Persistence").getAsDouble() : 0.5D;
/* 162 */         FractalNoiseProperty fractalNoiseProperty = new FractalNoiseProperty(loadSeed(), noiseFunction, fractalMode.getFunction(), octaves, lacunarity, persistence);
/*     */       } else {
/* 164 */         singleNoiseProperty = new SingleNoiseProperty(loadSeed(), noiseFunction);
/*     */       } 
/*     */     } 
/* 167 */     if (type != NoisePropertyType.FORMULA && has("Formula")) {
/* 168 */       NoiseFormulaProperty.NoiseFormula noiseFormula = NoiseFormulaProperty.NoiseFormula.valueOf(get("Formula").getAsString());
/* 169 */       noiseFormulaProperty = new NoiseFormulaProperty((NoiseProperty)singleNoiseProperty, noiseFormula.getFormula());
/*     */     } 
/* 171 */     if (type != NoisePropertyType.CURVE && has("Curve"))
/*     */     {
/* 173 */       curveNoiseProperty = (new CurveNoisePropertyJsonLoader<>(this.seed, this.dataFolder, get("Curve"), (NoiseProperty)noiseFormulaProperty)).load();
/*     */     }
/* 175 */     if (type != NoisePropertyType.SCALE && has("Scale")) {
/* 176 */       double scale = get("Scale").getAsDouble();
/* 177 */       scaleNoiseProperty = new ScaleNoiseProperty((NoiseProperty)curveNoiseProperty, scale);
/*     */     } 
/*     */     
/* 180 */     if (type != NoisePropertyType.NORMALIZE && has("Normalize") && type != NoisePropertyType.GRADIENT) {
/*     */       
/* 182 */       IDoubleRange range = (new DoubleRangeJsonLoader<>(this.seed, this.dataFolder, get("Normalize"))).load();
/* 183 */       normalizeNoiseProperty = new NormalizeNoiseProperty((NoiseProperty)scaleNoiseProperty, range.getValue(0.0D), range.getValue(1.0D) - range.getValue(0.0D));
/*     */     } 
/* 185 */     if (type != NoisePropertyType.OFFSET && (has("Offset") || has("OffsetX") || has("OffsetY") || has("OffsetZ"))) {
/* 186 */       double offset = has("Offset") ? get("Offset").getAsDouble() : 0.0D;
/* 187 */       double offsetX = has("OffsetX") ? get("OffsetX").getAsDouble() : offset;
/* 188 */       double offsetY = has("OffsetY") ? get("OffsetY").getAsDouble() : offset;
/* 189 */       double offsetZ = has("OffsetZ") ? get("OffsetZ").getAsDouble() : offset;
/* 190 */       offsetNoiseProperty = new OffsetNoiseProperty((NoiseProperty)normalizeNoiseProperty, offsetX, offsetY, offsetZ);
/*     */     } 
/* 192 */     if (type != NoisePropertyType.ROTATE && (has("Pitch") || has("Yaw"))) {
/*     */       
/* 194 */       CoordinateRotator rotation = (new CoordinateRotatorJsonLoader<>(this.seed, this.dataFolder, this.json)).load();
/*     */       
/* 196 */       rotateNoiseProperty = new RotateNoiseProperty((NoiseProperty)offsetNoiseProperty, rotation);
/*     */     } 
/* 198 */     if (type != NoisePropertyType.GRADIENT && has("Gradient"))
/*     */     {
/* 200 */       gradientNoiseProperty = (new GradientNoisePropertyJsonLoader<>(this.seed, this.dataFolder, get("Gradient"), (NoiseProperty)rotateNoiseProperty)).load();
/*     */     }
/* 202 */     return (NoiseProperty)gradientNoiseProperty; }
/*     */ 
/*     */   
/*     */   protected int loadSeed() {
/* 206 */     int seedVal = this.seed.hashCode();
/* 207 */     if (has("Seed")) {
/* 208 */       SeedString<?> overwritten = this.seed.appendToOriginal(get("Seed").getAsString());
/* 209 */       seedVal = overwritten.hashCode();
/* 210 */       this.seed.get().reportSeeds(seedVal, this.seed.original, this.seed.seed, overwritten.seed);
/*     */     } else {
/* 212 */       this.seed.get().reportSeeds(seedVal, this.seed.original, this.seed.seed, null);
/*     */     } 
/* 214 */     return seedVal;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected NoiseProperty[] loadNoiseProperties(@Nonnull JsonElement element) {
/* 219 */     if (element.isJsonArray()) {
/* 220 */       JsonArray array = element.getAsJsonArray();
/* 221 */       NoiseProperty[] noiseProperties = new NoiseProperty[array.size()];
/* 222 */       for (int i = 0; i < noiseProperties.length; i++) {
/* 223 */         noiseProperties[i] = (new NoisePropertyJsonLoader(this.seed.append(String.format("-#%s", new Object[] { Integer.valueOf(i) })), this.dataFolder, array.get(i)))
/* 224 */           .load();
/*     */       } 
/* 226 */       return noiseProperties;
/*     */     } 
/* 228 */     return new NoiseProperty[0];
/*     */   }
/*     */ 
/*     */   
/*     */   protected double[] loadDoubleArray(@Nullable JsonElement element, int size) {
/* 233 */     double[] values = new double[size];
/* 234 */     if (element == null || element.isJsonNull()) {
/* 235 */       Arrays.fill(values, 1.0D / size);
/* 236 */     } else if (element.isJsonArray()) {
/* 237 */       JsonArray array = element.getAsJsonArray();
/* 238 */       for (int i = 0; i < size; i++) {
/* 239 */         values[i] = array.get(i).getAsDouble();
/*     */       }
/*     */     } 
/* 242 */     return values;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected NoiseFunctionJsonLoader newNoiseFunctionJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 247 */     return new NoiseFunctionJsonLoader<>(seed, dataFolder, json);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Constants
/*     */   {
/*     */     public static final String KEY_SEED = "Seed";
/*     */ 
/*     */     
/*     */     public static final String KEY_SUM_FACTORS = "Factors";
/*     */ 
/*     */     
/*     */     public static final String KEY_NORMALIZE_RANGE = "Range";
/*     */ 
/*     */     
/*     */     public static final String KEY_DISTORTED_RANDOMIZER = "Randomizer";
/*     */ 
/*     */     
/*     */     public static final String KEY_TYPE = "Type";
/*     */ 
/*     */     
/*     */     public static final String KEY_NOISE = "Noise";
/*     */ 
/*     */     
/*     */     public static final String KEY_FRACTAL_MODE = "FractalMode";
/*     */ 
/*     */     
/*     */     public static final String KEY_OCTAVES = "Octaves";
/*     */ 
/*     */     
/*     */     public static final String KEY_LACUNARITY = "Lacunarity";
/*     */ 
/*     */     
/*     */     public static final String KEY_PERSISTENCE = "Persistence";
/*     */ 
/*     */     
/*     */     public static final String KEY_FORMULA = "Formula";
/*     */ 
/*     */     
/*     */     public static final String KEY_CURVE = "Curve";
/*     */ 
/*     */     
/*     */     public static final String KEY_SCALE = "Scale";
/*     */ 
/*     */     
/*     */     public static final String KEY_NORMALIZE = "Normalize";
/*     */ 
/*     */     
/*     */     public static final String KEY_OFFSET = "Offset";
/*     */ 
/*     */     
/*     */     public static final String KEY_OFFSET_X = "OffsetX";
/*     */ 
/*     */     
/*     */     public static final String KEY_OFFSET_Y = "OffsetY";
/*     */ 
/*     */     
/*     */     public static final String KEY_OFFSET_Z = "OffsetZ";
/*     */     
/*     */     public static final String KEY_GRADIENT = "Gradient";
/*     */     
/*     */     public static final String ERROR_NO_NOISE = "Could not find noise map data. Keyword: Noise";
/*     */     
/*     */     public static final String ERROR_SUM_NO_FACTORS = "Could not find factors for sum composed noise map. Keyword: Factors";
/*     */     
/*     */     public static final String ERROR_NO_FORMULA = "Could not find formula type for noise map. Keyword: Formula";
/*     */     
/*     */     public static final String ERROR_NO_SCALE = "Could not find scale data for scaled noise map. Keyword: Scale";
/*     */     
/*     */     public static final String ERROR_DISTORTED_RANDOMIZER = "Could not find randomizer for distorted noise map. Keyword: Randomizer";
/*     */     
/*     */     public static final String ERROR_NORMALIZE_NO_RANGE = "Could not find range data for normalized noise map. Keyword: Range";
/*     */     
/*     */     public static final String ERROR_UNKOWN_TYPE = "Could not find instructions for noise property type: %s";
/*     */     
/* 323 */     public static final FractalNoiseProperty.FractalMode DEFAULT_FRACTAL_MODE = FractalNoiseProperty.FractalMode.FBM;
/*     */     public static final double DEFAULT_LACUNARITY = 2.0D;
/*     */     public static final double DEFAULT_PERSISTENCE = 0.5D;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\NoisePropertyJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */