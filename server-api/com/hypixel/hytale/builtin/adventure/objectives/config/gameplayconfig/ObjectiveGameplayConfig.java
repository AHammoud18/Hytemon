/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.gameplayconfig;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLineAsset;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectiveGameplayConfig
/*    */ {
/*    */   public static final String ID = "Objective";
/*    */   public static final BuilderCodec<ObjectiveGameplayConfig> CODEC;
/*    */   protected Map<String, String> starterObjectiveLinePerWorld;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ObjectiveGameplayConfig.class, ObjectiveGameplayConfig::new).appendInherited(new KeyedCodec("StarterObjectiveLinePerWorld", (Codec)new MapCodec((Codec)Codec.STRING, it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap::new, true)), (o, s) -> o.starterObjectiveLinePerWorld = s, o -> o.starterObjectiveLinePerWorld, (o, parent) -> o.starterObjectiveLinePerWorld = parent.starterObjectiveLinePerWorld).addValidator((Validator)ObjectiveLineAsset.VALIDATOR_CACHE.getMapValueValidator()).add()).build();
/*    */   }
/*    */   @Nullable
/*    */   public static ObjectiveGameplayConfig get(@Nonnull GameplayConfig config) {
/* 31 */     return (ObjectiveGameplayConfig)config.getPluginConfig().get(ObjectiveGameplayConfig.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, String> getStarterObjectiveLinePerWorld() {
/* 37 */     return this.starterObjectiveLinePerWorld;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\gameplayconfig\ObjectiveGameplayConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */