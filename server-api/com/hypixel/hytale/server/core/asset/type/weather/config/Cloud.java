/*    */ package com.hypixel.hytale.server.core.asset.type.weather.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*    */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.protocol.Cloud;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.Arrays;
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
/*    */ public class Cloud
/*    */   implements NetworkSerializable<Cloud>
/*    */ {
/*    */   public static final BuilderCodec<Cloud> CODEC;
/*    */   protected String texture;
/*    */   protected TimeColorAlpha[] colors;
/*    */   protected TimeFloat[] speeds;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Cloud.class, Cloud::new).append(new KeyedCodec("Texture", (Codec)Codec.STRING), (cloud, s) -> cloud.texture = s, Cloud::getTexture).addValidator((Validator)CommonAssetValidator.TEXTURE_SKY).add()).append(new KeyedCodec("Colors", (Codec)new ArrayCodec((Codec)TimeColorAlpha.CODEC, x$0 -> new TimeColorAlpha[x$0])), (cloud, s) -> cloud.colors = s, Cloud::getColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).add()).append(new KeyedCodec("Speeds", (Codec)new ArrayCodec((Codec)TimeFloat.CODEC, x$0 -> new TimeFloat[x$0])), (cloud, s) -> cloud.speeds = s, Cloud::getSpeeds).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Cloud(String texture, TimeColorAlpha[] colors, TimeFloat[] speeds) {
/* 44 */     this.texture = texture;
/* 45 */     this.colors = colors;
/* 46 */     this.speeds = speeds;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Cloud() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Cloud toPacket() {
/* 56 */     Cloud packet = new Cloud();
/* 57 */     packet.texture = this.texture;
/* 58 */     packet.colors = Weather.toColorAlphaMap(this.colors);
/* 59 */     packet.speeds = Weather.toFloatMap(this.speeds);
/* 60 */     return packet;
/*    */   }
/*    */   
/*    */   public String getTexture() {
/* 64 */     return this.texture;
/*    */   }
/*    */   
/*    */   public TimeColorAlpha[] getColors() {
/* 68 */     return this.colors;
/*    */   }
/*    */   
/*    */   public TimeFloat[] getSpeeds() {
/* 72 */     return this.speeds;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 78 */     return "Cloud{texture='" + this.texture + "', colors=" + 
/*    */       
/* 80 */       Arrays.toString((Object[])this.colors) + ", speeds=" + 
/* 81 */       Arrays.toString((Object[])this.speeds) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\weather\config\Cloud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */