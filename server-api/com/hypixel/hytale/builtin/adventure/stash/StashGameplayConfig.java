/*    */ package com.hypixel.hytale.builtin.adventure.stash;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StashGameplayConfig
/*    */ {
/*    */   public static final String ID = "Stash";
/*    */   public static final BuilderCodec<StashGameplayConfig> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(StashGameplayConfig.class, StashGameplayConfig::new).appendInherited(new KeyedCodec("ClearContainerDropList", (Codec)Codec.BOOLEAN), (gameplayConfig, clearContainerDropList) -> gameplayConfig.clearContainerDropList = clearContainerDropList.booleanValue(), gameplayConfig -> Boolean.valueOf(gameplayConfig.clearContainerDropList), (gameplayConfig, parent) -> gameplayConfig.clearContainerDropList = parent.clearContainerDropList).add()).build();
/*    */   }
/* 27 */   private static final StashGameplayConfig DEFAULT_STASH_GAMEPLAY_CONFIG = new StashGameplayConfig();
/*    */   
/*    */   protected boolean clearContainerDropList = true;
/*    */   
/*    */   @Nullable
/*    */   public static StashGameplayConfig get(@Nonnull GameplayConfig config) {
/* 33 */     return (StashGameplayConfig)config.getPluginConfig().get(StashGameplayConfig.class);
/*    */   }
/*    */   
/*    */   public static StashGameplayConfig getOrDefault(@Nonnull GameplayConfig config) {
/* 37 */     StashGameplayConfig stashGameplayConfig = get(config);
/* 38 */     return (stashGameplayConfig != null) ? stashGameplayConfig : DEFAULT_STASH_GAMEPLAY_CONFIG;
/*    */   }
/*    */   
/*    */   public boolean isClearContainerDropList() {
/* 42 */     return this.clearContainerDropList;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 48 */     return "StashGameplayConfig{clearContainerDropList=" + this.clearContainerDropList + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\stash\StashGameplayConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */