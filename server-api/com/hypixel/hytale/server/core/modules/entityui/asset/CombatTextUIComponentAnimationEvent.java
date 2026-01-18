/*    */ package com.hypixel.hytale.server.core.modules.entityui.asset;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.CombatTextEntityUIAnimationEventType;
/*    */ import com.hypixel.hytale.protocol.CombatTextEntityUIComponentAnimationEvent;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class CombatTextUIComponentAnimationEvent implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, CombatTextUIComponentAnimationEvent>> {
/*    */   static {
/* 18 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
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
/* 45 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)AssetBuilderCodec.abstractBuilder(CombatTextUIComponentAnimationEvent.class).append(new KeyedCodec("StartAt", (Codec)Codec.FLOAT), (event, f) -> event.startAt = f.floatValue(), event -> Float.valueOf(event.startAt)).documentation("The percentage of the display duration at which this event should begin.").addValidator(Validators.nonNull()).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).append(new KeyedCodec("EndAt", (Codec)Codec.FLOAT), (event, f) -> event.endAt = f.floatValue(), event -> Float.valueOf(event.endAt)).documentation("The percentage of the display duration at which this event should end.").addValidator(Validators.nonNull()).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).build();
/*    */   }
/*    */   
/*    */   public static final AssetCodecMapCodec<String, CombatTextUIComponentAnimationEvent> CODEC;
/*    */   public static final BuilderCodec<CombatTextUIComponentAnimationEvent> ABSTRACT_CODEC;
/*    */   protected String id;
/*    */   protected AssetExtraInfo.Data data;
/*    */   private CombatTextEntityUIAnimationEventType type;
/*    */   private float startAt;
/*    */   private float endAt;
/*    */   
/*    */   public String getId() {
/* 57 */     return this.id;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CombatTextEntityUIComponentAnimationEvent generatePacket() {
/* 62 */     CombatTextEntityUIComponentAnimationEvent packet = new CombatTextEntityUIComponentAnimationEvent();
/* 63 */     packet.type = this.type;
/* 64 */     packet.startAt = this.startAt;
/* 65 */     packet.endAt = this.endAt;
/* 66 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 72 */     return "CombatTextUIComponentAnimationEvent{type=" + String.valueOf(this.type) + ", startAt=" + this.startAt + ", endAt=" + this.endAt + "} " + super
/*    */ 
/*    */ 
/*    */       
/* 76 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\asset\CombatTextUIComponentAnimationEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */