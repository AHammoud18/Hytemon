/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundEventExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final SoundEventExistsValidator DEFAULT_INSTANCE = new SoundEventExistsValidator();
/*    */ 
/*    */   
/*    */   private SoundEventExistsValidator() {}
/*    */ 
/*    */   
/*    */   private SoundEventExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "SoundEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String soundEvent) {
/* 32 */     return (SoundEvent.getAssetMap().getAsset(soundEvent) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String soundEvent, String attributeName) {
/* 38 */     return "The sound event with the name \"" + soundEvent + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return SoundEvent.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SoundEventExistsValidator required() {
/* 53 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SoundEventExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 58 */     return new SoundEventExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\SoundEventExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */