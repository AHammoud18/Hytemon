/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
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
/*    */ public class GatheringEffectsConfig
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<GatheringEffectsConfig> CODEC;
/*    */   protected String particleSystemId;
/*    */   protected String soundEventId;
/*    */   protected transient int soundEventIndex;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(GatheringEffectsConfig.class, GatheringEffectsConfig::new).append(new KeyedCodec("ParticleSystemId", (Codec)Codec.STRING), (unbreakableBlockConfig, o) -> unbreakableBlockConfig.particleSystemId = o, unbreakableBlockConfig -> unbreakableBlockConfig.particleSystemId).addValidator(ParticleSystem.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("SoundEventId", (Codec)Codec.STRING), (unbreakableBlockConfig, o) -> unbreakableBlockConfig.soundEventId = o, unbreakableBlockConfig -> unbreakableBlockConfig.soundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).afterDecode(GatheringEffectsConfig::processConfig)).build();
/*    */   }
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
/*    */   public String getParticleSystemId() {
/* 55 */     return this.particleSystemId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSoundEventId() {
/* 62 */     return this.soundEventId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSoundEventIndex() {
/* 69 */     return this.soundEventIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processConfig() {
/* 76 */     if (this.soundEventId != null)
/* 77 */       this.soundEventIndex = SoundEvent.getAssetMap().getIndex(this.soundEventId); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\GatheringEffectsConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */