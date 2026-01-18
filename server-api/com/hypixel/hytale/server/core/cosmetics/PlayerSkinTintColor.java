/*    */ package com.hypixel.hytale.server.core.cosmetics;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonArray;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ public class PlayerSkinTintColor {
/*    */   protected String id;
/*    */   protected String[] baseColor;
/*    */   
/*    */   protected PlayerSkinTintColor(@Nonnull BsonDocument doc) {
/* 12 */     this.id = doc.getString("Id").getValue();
/*    */     
/* 14 */     BsonArray baseColor = doc.getArray("BaseColor");
/* 15 */     this.baseColor = new String[baseColor.size()];
/* 16 */     for (int i = 0; i < baseColor.size(); i++) {
/* 17 */       this.baseColor[i] = baseColor.get(i).asString().getValue();
/*    */     }
/*    */   }
/*    */   
/*    */   public String getId() {
/* 22 */     return this.id;
/*    */   }
/*    */   
/*    */   public String[] getBaseColor() {
/* 26 */     return this.baseColor;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 32 */     return "PlayerSkinTintColor{id='" + this.id + "', baseColor='" + String.valueOf(this.baseColor) + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\PlayerSkinTintColor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */