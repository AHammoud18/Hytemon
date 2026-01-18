/*    */ package com.hypixel.hytale.server.core.asset.type.weather.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DayTexture
/*    */ {
/*    */   public static final BuilderCodec<DayTexture> CODEC;
/*    */   protected int day;
/*    */   protected String texture;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DayTexture.class, DayTexture::new).addField(new KeyedCodec("Day", (Codec)Codec.INTEGER), (dayTexture, i) -> dayTexture.day = i.intValue(), DayTexture::getDay)).append(new KeyedCodec("Texture", (Codec)Codec.STRING), (dayTexture, s) -> dayTexture.texture = s, DayTexture::getTexture).addValidator((Validator)CommonAssetValidator.TEXTURE_SKY).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DayTexture(int day, String texture) {
/* 30 */     this.day = day;
/* 31 */     this.texture = texture;
/*    */   }
/*    */ 
/*    */   
/*    */   protected DayTexture() {}
/*    */   
/*    */   public int getDay() {
/* 38 */     return this.day;
/*    */   }
/*    */   
/*    */   public String getTexture() {
/* 42 */     return this.texture;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 48 */     return "DayTexture{day=" + this.day + ", texture='" + this.texture + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\weather\config\DayTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */