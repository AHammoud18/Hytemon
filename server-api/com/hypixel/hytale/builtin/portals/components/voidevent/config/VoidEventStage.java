/*    */ package com.hypixel.hytale.builtin.portals.components.voidevent.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
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
/*    */ public class VoidEventStage
/*    */ {
/*    */   public static final BuilderCodec<VoidEventStage> CODEC;
/*    */   private int secondsInto;
/*    */   private String forcedWeatherId;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(VoidEventStage.class, VoidEventStage::new).append(new KeyedCodec("SecondsInto", (Codec)Codec.INTEGER), (stage, o) -> stage.secondsInto = o.intValue(), stage -> Integer.valueOf(stage.secondsInto)).documentation("How many seconds into the void event does this stage becomes the active stage.").add()).append(new KeyedCodec("ForcedWeather", (Codec)Codec.STRING), (stage, o) -> stage.forcedWeatherId = o, stage -> stage.forcedWeatherId).documentation("What weather to force during this stage.").addValidator(Weather.VALIDATOR_CACHE.getValidator()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSecondsInto() {
/* 33 */     return this.secondsInto;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getForcedWeatherId() {
/* 38 */     return this.forcedWeatherId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\components\voidevent\config\VoidEventStage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */