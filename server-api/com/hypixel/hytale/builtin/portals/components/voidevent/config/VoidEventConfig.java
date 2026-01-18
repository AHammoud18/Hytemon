/*    */ package com.hypixel.hytale.builtin.portals.components.voidevent.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.ambiencefx.config.AmbienceFX;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VoidEventConfig
/*    */ {
/*    */   public static final BuilderCodec<VoidEventConfig> CODEC;
/*    */   
/*    */   static {
/* 48 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(VoidEventConfig.class, VoidEventConfig::new).append(new KeyedCodec("DurationSeconds", (Codec)Codec.INTEGER), (config, o) -> config.durationSeconds = o.intValue(), config -> Integer.valueOf(config.durationSeconds)).documentation("How long the void event lasts in seconds. The void event starts at the end of the instance. If your fragment is 10 minutes and this is 180 seconds, it will start 7 minutes in.").add()).append(new KeyedCodec("InvasionPortals", (Codec)InvasionPortalConfig.CODEC), (config, o) -> config.portalConfig = o, config -> config.portalConfig).documentation("Configuration regarding the enemy portals that spawn around the players during the event").add()).append(new KeyedCodec("Stages", (Codec)new ArrayCodec((Codec)VoidEventStage.CODEC, x$0 -> new VoidEventStage[x$0])), (config, o) -> config.stages = o, config -> config.stages).documentation("Certain event characteristics happen over stages that can be defined here. Stages are spread in time. Only one stage is \"active\" at a time.").add()).append(new KeyedCodec("MusicAmbienceFX", (Codec)Codec.STRING), (config, o) -> config.musicAmbienceFX = o, config -> config.musicAmbienceFX).documentation("The ID of an AmbienceFX which will be used for the music during the event").addValidator(AmbienceFX.VALIDATOR_CACHE.getValidator()).add()).afterDecode(VoidEventConfig::processConfig)).build();
/*    */   }
/* 50 */   private int durationSeconds = 180;
/*    */   private InvasionPortalConfig portalConfig;
/*    */   private VoidEventStage[] stages;
/*    */   private List<VoidEventStage> stagesSortedByStartTime;
/*    */   private String musicAmbienceFX;
/*    */   
/*    */   public int getDurationSeconds() {
/* 57 */     return this.durationSeconds;
/*    */   }
/*    */   
/*    */   public int getShouldStartAfterSeconds(int portalTimeLimitSeconds) {
/* 61 */     return Math.max(10, portalTimeLimitSeconds - this.durationSeconds);
/*    */   }
/*    */   
/*    */   public InvasionPortalConfig getInvasionPortalConfig() {
/* 65 */     return this.portalConfig;
/*    */   }
/*    */   
/*    */   public VoidEventStage[] getStages() {
/* 69 */     return this.stages;
/*    */   }
/*    */   
/*    */   public List<VoidEventStage> getStagesSortedByStartTime() {
/* 73 */     return this.stagesSortedByStartTime;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getMusicAmbienceFX() {
/* 78 */     return this.musicAmbienceFX;
/*    */   }
/*    */   
/*    */   private void processConfig() {
/* 82 */     this.stagesSortedByStartTime = (List<VoidEventStage>)new ObjectArrayList();
/* 83 */     if (this.stages == null)
/*    */       return; 
/* 85 */     Collections.addAll(this.stagesSortedByStartTime, this.stages);
/* 86 */     this.stagesSortedByStartTime.sort(Comparator.comparingInt(VoidEventStage::getSecondsInto));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\components\voidevent\config\VoidEventConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */