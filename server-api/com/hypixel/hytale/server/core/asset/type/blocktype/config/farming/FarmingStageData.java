/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.farming;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.protocol.Rangef;
/*    */ import com.hypixel.hytale.protocol.SoundCategory;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class FarmingStageData {
/*    */   @Nonnull
/* 24 */   public static CodecMapCodec<FarmingStageData> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderCodec<FarmingStageData> BASE_CODEC;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Rangef duration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 47 */     BASE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(FarmingStageData.class).append(new KeyedCodec("Duration", (Codec)ProtocolCodecs.RANGEF), (farmingStage, duration) -> farmingStage.duration = duration, farmingStage -> farmingStage.duration).add()).append(new KeyedCodec("SoundEventId", (Codec)Codec.STRING), (farmingStage, sound) -> farmingStage.soundEventId = sound, farmingStage -> farmingStage.soundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.ONESHOT).add()).afterDecode(farmingStage -> { if (farmingStage.soundEventId != null) farmingStage.soundEventIndex = SoundEvent.getAssetMap().getIndex(farmingStage.soundEventId);  })).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/* 54 */   protected String soundEventId = null;
/*    */   
/* 56 */   protected transient int soundEventIndex = 0;
/*    */   
/*    */   @Nullable
/*    */   public Rangef getDuration() {
/* 60 */     return this.duration;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getSoundEventId() {
/* 65 */     return this.soundEventId;
/*    */   }
/*    */   
/*    */   public int getSoundEventIndex() {
/* 69 */     return this.soundEventIndex;
/*    */   }
/*    */   
/*    */   public boolean implementsShouldStop() {
/* 73 */     return false;
/*    */   }
/*    */   
/*    */   public boolean shouldStop(ComponentAccessor<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z) {
/* 77 */     return false;
/*    */   }
/*    */   
/*    */   public void apply(ComponentAccessor<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z, @Nullable FarmingStageData previousStage) {
/* 81 */     ChunkSection section = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/* 82 */     int worldX = ChunkUtil.worldCoordFromLocalCoord(section.getX(), x);
/* 83 */     int worldY = ChunkUtil.worldCoordFromLocalCoord(section.getY(), y);
/* 84 */     int worldZ = ChunkUtil.worldCoordFromLocalCoord(section.getZ(), z);
/* 85 */     SoundUtil.playSoundEvent3d(this.soundEventIndex, SoundCategory.SFX, worldX, worldY, worldZ, (ComponentAccessor)((ChunkStore)commandBuffer.getExternalData()).getWorld().getEntityStore().getStore());
/*    */     
/* 87 */     if (previousStage != null) {
/* 88 */       previousStage.remove(commandBuffer, sectionRef, blockRef, x, y, z);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(ComponentAccessor<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z) {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 99 */     return "FarmingStageData{duration=" + String.valueOf(this.duration) + ", soundEventId='" + this.soundEventId + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\farming\FarmingStageData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */