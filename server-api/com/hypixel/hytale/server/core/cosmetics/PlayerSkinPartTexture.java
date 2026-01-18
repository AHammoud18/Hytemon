/*    */ package com.hypixel.hytale.server.core.cosmetics;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonArray;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ public class PlayerSkinPartTexture {
/*    */   private String texture;
/*    */   private String[] baseColor;
/*    */   
/*    */   protected PlayerSkinPartTexture(@Nonnull BsonDocument doc) {
/* 13 */     this.texture = doc.getString("Texture").getValue();
/*    */     
/* 15 */     if (doc.containsKey("BaseColor")) {
/* 16 */       BsonArray baseColor = doc.getArray("BaseColor");
/* 17 */       this.baseColor = new String[baseColor.size()];
/* 18 */       for (int i = 0; i < baseColor.size(); i++) {
/* 19 */         this.baseColor[i] = baseColor.get(i).asString().getValue();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTexture() {
/* 25 */     return this.texture;
/*    */   }
/*    */   
/*    */   public String[] getBaseColor() {
/* 29 */     return this.baseColor;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 35 */     return "PlayerSkinPartTexture{texture='" + this.texture + "', baseColor=" + 
/*    */       
/* 37 */       Arrays.toString((Object[])this.baseColor) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\PlayerSkinPartTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */