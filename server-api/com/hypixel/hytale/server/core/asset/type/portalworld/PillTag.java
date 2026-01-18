/*    */ package com.hypixel.hytale.server.core.asset.type.portalworld;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.Color;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import java.util.function.Supplier;
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
/*    */ public class PillTag
/*    */ {
/*    */   public static final BuilderCodec<PillTag> CODEC;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PillTag.class, PillTag::new).appendInherited(new KeyedCodec("TranslationKey", (Codec)Codec.STRING), (pillTag, o) -> pillTag.translationKey = o, pillTag -> pillTag.translationKey, (pillTag, parent) -> pillTag.translationKey = parent.translationKey).documentation("The translation key for the text of this tag.").add()).appendInherited(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR), (pillTag, o) -> pillTag.color = o, pillTag -> pillTag.color, (pillTag, parent) -> pillTag.color = parent.color).add()).build();
/*    */   }
/* 29 */   public static final PillTag[] EMPTY_LIST = new PillTag[0];
/*    */   
/*    */   private String translationKey;
/*    */   private Color color;
/*    */   
/*    */   public String getTranslationKey() {
/* 35 */     return this.translationKey;
/*    */   }
/*    */   
/*    */   public Message getMessage() {
/* 39 */     return Message.translation(this.translationKey);
/*    */   }
/*    */   
/*    */   public Color getColor() {
/* 43 */     return this.color;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 48 */     return "PillTag{translationKey='" + this.translationKey + "', color=" + String.valueOf(this.color) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\portalworld\PillTag.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */