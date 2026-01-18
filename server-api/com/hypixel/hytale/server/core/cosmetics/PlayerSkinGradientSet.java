/*    */ package com.hypixel.hytale.server.core.cosmetics;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ import org.bson.BsonValue;
/*    */ 
/*    */ public class PlayerSkinGradientSet {
/*    */   private final String id;
/*    */   private final Map<String, PlayerSkinPartTexture> gradients;
/*    */   
/*    */   protected PlayerSkinGradientSet(@Nonnull BsonDocument doc) {
/* 14 */     this.id = doc.getString("Id").getValue();
/*    */     
/* 16 */     BsonDocument gradients = doc.getDocument("Gradients");
/* 17 */     this.gradients = (Map<String, PlayerSkinPartTexture>)new Object2ObjectOpenHashMap();
/* 18 */     for (Map.Entry<String, BsonValue> gradient : (Iterable<Map.Entry<String, BsonValue>>)gradients.entrySet()) {
/* 19 */       this.gradients.put(gradient.getKey(), new PlayerSkinPartTexture(((BsonValue)gradient.getValue()).asDocument()));
/*    */     }
/*    */   }
/*    */   
/*    */   public String getId() {
/* 24 */     return this.id;
/*    */   }
/*    */   
/*    */   public Map<String, PlayerSkinPartTexture> getGradients() {
/* 28 */     return this.gradients;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\PlayerSkinGradientSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */