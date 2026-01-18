/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.protocol.ModelTexture;
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
/*    */ public class CustomModelTexture
/*    */ {
/*    */   @Nonnull
/*    */   public static BuilderCodec<CustomModelTexture> CODEC;
/*    */   private String texture;
/*    */   private int weight;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CustomModelTexture.class, CustomModelTexture::new).append(new KeyedCodec("Texture", (Codec)Codec.STRING), (customModelTexture, s) -> customModelTexture.texture = s, customModelTexture -> customModelTexture.texture).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).addField(new KeyedCodec("Weight", (Codec)Codec.INTEGER), (customModelTexture, i) -> customModelTexture.weight = i.intValue(), customModelTexture -> Integer.valueOf(customModelTexture.weight))).build();
/*    */   }
/*    */   
/*    */   public CustomModelTexture() {}
/*    */   
/*    */   public CustomModelTexture(String texture, int weight) {
/* 32 */     this.texture = texture;
/* 33 */     this.weight = weight;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTexture() {
/* 40 */     return this.texture;
/*    */   }
/*    */   
/*    */   public int getWeight() {
/* 44 */     return this.weight;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ModelTexture toPacket(float totalWight) {
/* 49 */     return new ModelTexture(this.texture, this.weight / totalWight);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 55 */     return "CustomModelTexture{texture='" + this.texture + "', weight=" + this.weight + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\CustomModelTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */