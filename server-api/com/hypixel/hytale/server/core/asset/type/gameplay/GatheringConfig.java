/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GatheringConfig
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<GatheringConfig> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(GatheringConfig.class, GatheringConfig::new).appendInherited(new KeyedCodec("UnbreakableBlock", (Codec)GatheringEffectsConfig.CODEC), (gameplayConfig, o) -> gameplayConfig.unbreakableBlockConfig = o, gameplayConfig -> gameplayConfig.unbreakableBlockConfig, (gameplayConfig, parent) -> gameplayConfig.unbreakableBlockConfig = parent.unbreakableBlockConfig).add()).appendInherited(new KeyedCodec("IncorrectTool", (Codec)GatheringEffectsConfig.CODEC), (gameplayConfig, o) -> gameplayConfig.incorrectToolConfig = o, gameplayConfig -> gameplayConfig.incorrectToolConfig, (gameplayConfig, parent) -> gameplayConfig.incorrectToolConfig = parent.incorrectToolConfig).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/* 35 */   protected GatheringEffectsConfig unbreakableBlockConfig = new GatheringEffectsConfig();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 41 */   protected GatheringEffectsConfig incorrectToolConfig = new GatheringEffectsConfig();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public GatheringEffectsConfig getUnbreakableBlockConfig() {
/* 49 */     return this.unbreakableBlockConfig;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public GatheringEffectsConfig getIncorrectToolConfig() {
/* 57 */     return this.incorrectToolConfig;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\GatheringConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */