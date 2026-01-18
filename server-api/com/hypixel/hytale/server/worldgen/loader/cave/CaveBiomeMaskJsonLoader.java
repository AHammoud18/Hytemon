/*    */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.condition.ConstantIntCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.CaveBiomeMaskFlags;
/*    */ import com.hypixel.hytale.server.worldgen.loader.biome.BiomeMaskJsonLoader;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.flag.CompositeInt2Flags;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.flag.FlagOperator;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.flag.Int2FlagsCondition;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class CaveBiomeMaskJsonLoader
/*    */   extends JsonLoader<SeedStringResource, Int2FlagsCondition>
/*    */ {
/*    */   private final ZoneFileContext zoneContext;
/*    */   
/*    */   public CaveBiomeMaskJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneContext) {
/* 26 */     super(seed.append(".CaveBiomeMask"), dataFolder, json);
/* 27 */     this.zoneContext = zoneContext;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Int2FlagsCondition load() {
/* 33 */     IIntCondition generate = loadGenerationMask();
/* 34 */     if (generate == ConstantIntCondition.DEFAULT_FALSE) return CaveBiomeMaskFlags.DEFAULT_DENY;
/*    */     
/* 36 */     IIntCondition populate = loadPopulationMask();
/* 37 */     if (generate == ConstantIntCondition.DEFAULT_TRUE && populate == ConstantIntCondition.DEFAULT_TRUE) return CaveBiomeMaskFlags.DEFAULT_ALLOW;
/*    */     
/* 39 */     int defaultResult = loadDefaultResult();
/* 40 */     CompositeInt2Flags.FlagCondition[] flagConditions = loadFlagConditions(generate, populate);
/* 41 */     return (Int2FlagsCondition)new CompositeInt2Flags(defaultResult, flagConditions);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected IIntCondition loadGenerationMask() {
/* 46 */     return loadBiomeMask("Generate");
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected IIntCondition loadPopulationMask() {
/* 51 */     return loadBiomeMask("Populate");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CompositeInt2Flags.FlagCondition[] loadFlagConditions(IIntCondition generate, IIntCondition populate) {
/* 56 */     return new CompositeInt2Flags.FlagCondition[] { new CompositeInt2Flags.FlagCondition(generate, FlagOperator.Or, 1), new CompositeInt2Flags.FlagCondition(populate, FlagOperator.Or, 2) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int loadDefaultResult() {
/* 63 */     int result = 4;
/*    */     
/* 65 */     if (loadFlagSetting("Terminate", Constants.DEFAULT_TERMINATE_SETTING)) result ^= 0x4;
/*    */     
/* 67 */     return result;
/*    */   }
/*    */   @Nullable
/*    */   protected IIntCondition loadBiomeMask(String maskName) {
/*    */     IIntCondition iIntCondition;
/* 72 */     ConstantIntCondition constantIntCondition = ConstantIntCondition.DEFAULT_TRUE;
/* 73 */     if (has(maskName))
/*    */     {
/* 75 */       iIntCondition = (new BiomeMaskJsonLoader(this.seed, this.dataFolder, getRaw(maskName), maskName, this.zoneContext)).load();
/*    */     }
/* 77 */     return iIntCondition;
/*    */   }
/*    */   
/*    */   protected boolean loadFlagSetting(String key, boolean defaultValue) {
/* 81 */     boolean result = defaultValue;
/* 82 */     if (has(key)) {
/* 83 */       result = getRaw(key).getAsBoolean();
/*    */     }
/* 85 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public static interface Constants
/*    */   {
/*    */     public static final String KEY_GENERATION = "Generate";
/*    */     public static final String KEY_POPULATION = "Populate";
/*    */     public static final String KEY_TERMINATE = "Terminate";
/* 94 */     public static final boolean DEFAULT_TERMINATE_SETTING = !CaveBiomeMaskFlags.canContinue(4);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CaveBiomeMaskJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */