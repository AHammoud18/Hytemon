/*    */ package com.hypixel.hytale.server.core.cosmetics;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ public class PlayerSkinGradient
/*    */   extends PlayerSkinTintColor {
/*    */   private String texture;
/*    */   
/*    */   protected PlayerSkinGradient(@Nonnull BsonDocument doc) {
/* 11 */     super(doc);
/* 12 */     if (doc.containsKey("Texture")) this.texture = doc.getString("Texture").getValue(); 
/*    */   }
/*    */   
/*    */   public String getTexture() {
/* 16 */     return this.texture;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 22 */     return "PlayerSkinGradient{texture='" + this.texture + "', id='" + this.id + "', baseColor='" + String.valueOf(this.baseColor) + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\PlayerSkinGradient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */