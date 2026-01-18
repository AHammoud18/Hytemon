/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import com.hypixel.hytale.procedurallib.random.CoordinateRandomizer;
/*    */ import com.hypixel.hytale.procedurallib.random.CoordinateRotator;
/*    */ import com.hypixel.hytale.procedurallib.random.ICoordinateRandomizer;
/*    */ import com.hypixel.hytale.procedurallib.random.RotatedCoordinateRandomizer;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CoordinateRandomizerJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ICoordinateRandomizer>
/*    */ {
/*    */   public CoordinateRandomizerJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 24 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ICoordinateRandomizer load() {
/* 30 */     if (this.json == null || this.json.isJsonNull()) {
/* 31 */       return CoordinateRandomizer.EMPTY_RANDOMIZER;
/*    */     }
/* 33 */     return loadRandomizer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected ICoordinateRandomizer loadRandomizer() {
/*    */     RotatedCoordinateRandomizer rotatedCoordinateRandomizer;
/* 42 */     CoordinateRandomizer coordinateRandomizer = new CoordinateRandomizer(loadGenerators(".X-Noise#%s"), loadGenerators(".Y-Noise#%s"), loadGenerators(".Z-Noise#%s"));
/*    */ 
/*    */     
/* 45 */     if (has("Rotate")) {
/*    */       
/* 47 */       CoordinateRotator rotation = (new CoordinateRotatorJsonLoader<>(this.seed, this.dataFolder, get("Rotate"))).load();
/*    */       
/* 49 */       if (rotation != CoordinateRotator.NONE) rotatedCoordinateRandomizer = new RotatedCoordinateRandomizer((ICoordinateRandomizer)coordinateRandomizer, rotation);
/*    */     
/*    */     } 
/* 52 */     return (ICoordinateRandomizer)rotatedCoordinateRandomizer;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CoordinateRandomizer.AmplitudeNoiseProperty[] loadGenerators(@Nonnull String seedSuffix) {
/* 57 */     JsonArray array = get("Generators").getAsJsonArray();
/* 58 */     CoordinateRandomizer.AmplitudeNoiseProperty[] generators = new CoordinateRandomizer.AmplitudeNoiseProperty[array.size()];
/* 59 */     for (int i = 0; i < array.size(); i++) {
/* 60 */       JsonObject object = array.get(i).getAsJsonObject();
/*    */       
/* 62 */       NoiseProperty property = (new NoisePropertyJsonLoader<>(this.seed.alternateOriginal(String.format(seedSuffix, new Object[] { Integer.valueOf(i) })), this.dataFolder, (JsonElement)object)).load();
/* 63 */       double amplitude = object.get("Amplitude").getAsDouble();
/* 64 */       generators[i] = new CoordinateRandomizer.AmplitudeNoiseProperty(property, amplitude);
/*    */     } 
/* 66 */     return generators;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_GENERATORS = "Generators";
/*    */     public static final String KEY_GENERATORS_AMPLITUDE = "Amplitude";
/*    */     public static final String SEED_X_NOISE_SUFFIX = ".X-Noise#%s";
/*    */     public static final String SEED_Y_NOISE_SUFFIX = ".Y-Noise#%s";
/*    */     public static final String SEED_Z_NOISE_SUFFIX = ".Z-Noise#%s";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\CoordinateRandomizerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */