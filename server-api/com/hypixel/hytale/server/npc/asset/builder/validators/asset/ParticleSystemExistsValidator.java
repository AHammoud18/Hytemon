/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleSystemExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final ParticleSystemExistsValidator DEFAULT_INSTANCE = new ParticleSystemExistsValidator();
/*    */ 
/*    */   
/*    */   private ParticleSystemExistsValidator() {}
/*    */ 
/*    */   
/*    */   private ParticleSystemExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "ParticleSystem";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String particleSystem) {
/* 32 */     return (ParticleSystem.getAssetMap().getAsset(particleSystem) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String particleSystem, String attributeName) {
/* 38 */     return "The particle system with the name \"" + particleSystem + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return ParticleSystem.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ParticleSystemExistsValidator required() {
/* 53 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ParticleSystemExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 58 */     return new ParticleSystemExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\ParticleSystemExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */