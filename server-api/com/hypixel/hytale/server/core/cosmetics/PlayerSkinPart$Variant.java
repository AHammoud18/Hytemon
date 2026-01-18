/*     */ package com.hypixel.hytale.server.core.cosmetics;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Variant
/*     */ {
/*     */   private final String model;
/*     */   private String greyscaleTexture;
/*     */   private Map<String, PlayerSkinPartTexture> textures;
/*     */   
/*     */   protected Variant(@Nonnull BsonDocument doc) {
/* 152 */     this.model = doc.getString("Model").getValue();
/*     */     
/* 154 */     if (doc.containsKey("GreyscaleTexture")) {
/* 155 */       this.greyscaleTexture = doc.getString("GreyscaleTexture").getValue();
/*     */     }
/*     */     
/* 158 */     if (doc.containsKey("Textures")) {
/* 159 */       BsonDocument texturesDoc = doc.getDocument("Textures");
/* 160 */       this.textures = (Map<String, PlayerSkinPartTexture>)new Object2ObjectOpenHashMap();
/* 161 */       for (Map.Entry<String, BsonValue> set : (Iterable<Map.Entry<String, BsonValue>>)texturesDoc.entrySet()) {
/* 162 */         this.textures.put(set.getKey(), new PlayerSkinPartTexture(((BsonValue)set.getValue()).asDocument()));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 168 */     return this.model;
/*     */   }
/*     */   
/*     */   public String getGreyscaleTexture() {
/* 172 */     return this.greyscaleTexture;
/*     */   }
/*     */   
/*     */   public Map<String, PlayerSkinPartTexture> getTextures() {
/* 176 */     return this.textures;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 182 */     return "CharacterPartVariant{model='" + this.model + "'greyscaleTexture='" + this.greyscaleTexture + "', textures=" + String.valueOf(this.textures) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\PlayerSkinPart$Variant.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */