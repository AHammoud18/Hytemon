/*    */ package com.hypixel.hytale.builtin.adventure.memories;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
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
/*    */ public class MemoriesGameplayConfig
/*    */ {
/*    */   public static final String ID = "Memories";
/*    */   public static final BuilderCodec<MemoriesGameplayConfig> CODEC;
/*    */   private int[] memoriesAmountPerLevel;
/*    */   private String memoriesRecordParticles;
/*    */   private String memoriesCatchItemId;
/*    */   
/*    */   static {
/* 42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MemoriesGameplayConfig.class, MemoriesGameplayConfig::new).appendInherited(new KeyedCodec("MemoriesAmountPerLevel", (Codec)Codec.INT_ARRAY), (config, value) -> config.memoriesAmountPerLevel = value, config -> config.memoriesAmountPerLevel, (config, parent) -> config.memoriesAmountPerLevel = parent.memoriesAmountPerLevel).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("MemoriesRecordParticles", (Codec)Codec.STRING), (config, value) -> config.memoriesRecordParticles = value, config -> config.memoriesRecordParticles, (config, parent) -> config.memoriesRecordParticles = parent.memoriesRecordParticles).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("MemoriesCatchItemId", (Codec)Codec.STRING), (memoriesGameplayConfig, s) -> memoriesGameplayConfig.memoriesCatchItemId = s, memoriesGameplayConfig -> memoriesGameplayConfig.memoriesCatchItemId, (memoriesGameplayConfig, parent) -> memoriesGameplayConfig.memoriesCatchItemId = parent.memoriesCatchItemId).addValidator(Validators.nonNull()).addValidator(Item.VALIDATOR_CACHE.getValidator()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static MemoriesGameplayConfig get(@Nonnull GameplayConfig config) {
/* 50 */     return (MemoriesGameplayConfig)config.getPluginConfig().get(MemoriesGameplayConfig.class);
/*    */   }
/*    */   
/*    */   public int[] getMemoriesAmountPerLevel() {
/* 54 */     return this.memoriesAmountPerLevel;
/*    */   }
/*    */   
/*    */   public String getMemoriesRecordParticles() {
/* 58 */     return this.memoriesRecordParticles;
/*    */   }
/*    */   
/*    */   public String getMemoriesCatchItemId() {
/* 62 */     return this.memoriesCatchItemId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\MemoriesGameplayConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */