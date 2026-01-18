/*     */ package com.hypixel.hytale.server.core.cosmetics;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ public class PlayerSkin
/*     */ {
/*     */   private final PlayerSkinPartId bodyCharacteristic;
/*     */   private final PlayerSkinPartId underwear;
/*     */   private final String face;
/*     */   private final String ears;
/*     */   private final String mouth;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId eyes;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId facialHair;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId haircut;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId eyebrows;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId pants;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId overpants;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId undertop;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId overtop;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId shoes;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId headAccessory;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId faceAccessory;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId earAccessory;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId skinFeature;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId gloves;
/*     */   @Nullable
/*     */   private final PlayerSkinPartId cape;
/*     */   
/*     */   public PlayerSkin(@Nonnull BsonDocument doc) {
/*  48 */     this.bodyCharacteristic = getId(doc, "bodyCharacteristic");
/*  49 */     this.underwear = getId(doc, "underwear");
/*  50 */     this.face = doc.getString("face").getValue();
/*  51 */     this.ears = doc.getString("ears").getValue();
/*  52 */     this.mouth = doc.getString("mouth").getValue();
/*  53 */     this.eyes = PlayerSkinPartId.fromString(doc.getString("eyes").getValue());
/*     */     
/*  55 */     this.facialHair = getId(doc, "facialHair");
/*  56 */     this.haircut = getId(doc, "haircut");
/*  57 */     this.eyebrows = getId(doc, "eyebrows");
/*  58 */     this.pants = getId(doc, "pants");
/*  59 */     this.overpants = getId(doc, "overpants");
/*  60 */     this.undertop = getId(doc, "undertop");
/*  61 */     this.overtop = getId(doc, "overtop");
/*  62 */     this.shoes = getId(doc, "shoes");
/*  63 */     this.headAccessory = getId(doc, "headAccessory");
/*  64 */     this.faceAccessory = getId(doc, "faceAccessory");
/*  65 */     this.earAccessory = getId(doc, "earAccessory");
/*  66 */     this.skinFeature = getId(doc, "skinFeature");
/*  67 */     this.gloves = getId(doc, "gloves");
/*  68 */     this.cape = getId(doc, "cape");
/*     */   }
/*     */   
/*     */   public PlayerSkin(PlayerSkinPartId bodyCharacteristic, PlayerSkinPartId underwear, String face, String ears, String mouth, PlayerSkinPartId eyes, PlayerSkinPartId facialHair, PlayerSkinPartId haircut, PlayerSkinPartId eyebrows, PlayerSkinPartId pants, PlayerSkinPartId overpants, PlayerSkinPartId undertop, PlayerSkinPartId overtop, PlayerSkinPartId shoes, PlayerSkinPartId headAccessory, PlayerSkinPartId faceAccessory, PlayerSkinPartId earAccessory, PlayerSkinPartId skinFeature, PlayerSkinPartId gloves, PlayerSkinPartId cape) {
/*  72 */     this.bodyCharacteristic = bodyCharacteristic;
/*  73 */     this.underwear = underwear;
/*  74 */     this.face = face;
/*  75 */     this.ears = ears;
/*  76 */     this.mouth = mouth;
/*  77 */     this.eyes = eyes;
/*  78 */     this.facialHair = facialHair;
/*  79 */     this.haircut = haircut;
/*  80 */     this.eyebrows = eyebrows;
/*  81 */     this.pants = pants;
/*  82 */     this.overpants = overpants;
/*  83 */     this.undertop = undertop;
/*  84 */     this.overtop = overtop;
/*  85 */     this.shoes = shoes;
/*  86 */     this.headAccessory = headAccessory;
/*  87 */     this.faceAccessory = faceAccessory;
/*  88 */     this.earAccessory = earAccessory;
/*  89 */     this.skinFeature = skinFeature;
/*  90 */     this.gloves = gloves;
/*  91 */     this.cape = cape;
/*     */   }
/*     */   
/*     */   public PlayerSkin(String bodyCharacteristic, String underwear, String face, String ears, String mouth, @Nullable String eyes, @Nullable String facialHair, @Nullable String haircut, @Nullable String eyebrows, @Nullable String pants, @Nullable String overpants, @Nullable String undertop, @Nullable String overtop, @Nullable String shoes, @Nullable String headAccessory, @Nullable String faceAccessory, @Nullable String earAccessory, @Nullable String skinFeature, @Nullable String gloves, @Nullable String cape) {
/*  95 */     this.bodyCharacteristic = (bodyCharacteristic != null) ? PlayerSkinPartId.fromString(bodyCharacteristic) : null;
/*  96 */     this.underwear = (underwear != null) ? PlayerSkinPartId.fromString(underwear) : null;
/*  97 */     this.face = face;
/*  98 */     this.ears = ears;
/*  99 */     this.mouth = mouth;
/* 100 */     this.eyes = (eyes != null) ? PlayerSkinPartId.fromString(eyes) : null;
/* 101 */     this.facialHair = (facialHair != null) ? PlayerSkinPartId.fromString(facialHair) : null;
/* 102 */     this.haircut = (haircut != null) ? PlayerSkinPartId.fromString(haircut) : null;
/* 103 */     this.eyebrows = (eyebrows != null) ? PlayerSkinPartId.fromString(eyebrows) : null;
/* 104 */     this.pants = (pants != null) ? PlayerSkinPartId.fromString(pants) : null;
/* 105 */     this.overpants = (overpants != null) ? PlayerSkinPartId.fromString(overpants) : null;
/* 106 */     this.undertop = (undertop != null) ? PlayerSkinPartId.fromString(undertop) : null;
/* 107 */     this.overtop = (overtop != null) ? PlayerSkinPartId.fromString(overtop) : null;
/* 108 */     this.shoes = (shoes != null) ? PlayerSkinPartId.fromString(shoes) : null;
/* 109 */     this.headAccessory = (headAccessory != null) ? PlayerSkinPartId.fromString(headAccessory) : null;
/* 110 */     this.faceAccessory = (faceAccessory != null) ? PlayerSkinPartId.fromString(faceAccessory) : null;
/* 111 */     this.earAccessory = (earAccessory != null) ? PlayerSkinPartId.fromString(earAccessory) : null;
/* 112 */     this.skinFeature = (skinFeature != null) ? PlayerSkinPartId.fromString(skinFeature) : null;
/* 113 */     this.gloves = (gloves != null) ? PlayerSkinPartId.fromString(gloves) : null;
/* 114 */     this.cape = (cape != null) ? PlayerSkinPartId.fromString(cape) : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static PlayerSkinPartId getId(@Nonnull BsonDocument doc, String key) {
/* 119 */     BsonValue bsonValue = doc.get(key);
/* 120 */     if (bsonValue == null || bsonValue.isNull()) return null;
/*     */     
/* 122 */     return PlayerSkinPartId.fromString(bsonValue.asString().getValue());
/*     */   }
/*     */   
/*     */   public PlayerSkinPartId getBodyCharacteristic() {
/* 126 */     return this.bodyCharacteristic;
/*     */   }
/*     */   
/*     */   public PlayerSkinPartId getUnderwear() {
/* 130 */     return this.underwear;
/*     */   }
/*     */   
/*     */   public String getFace() {
/* 134 */     return this.face;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getEyes() {
/* 139 */     return this.eyes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getEars() {
/* 144 */     return this.ears;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getMouth() {
/* 149 */     return this.mouth;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getFacialHair() {
/* 154 */     return this.facialHair;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getHaircut() {
/* 159 */     return this.haircut;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getEyebrows() {
/* 164 */     return this.eyebrows;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getPants() {
/* 169 */     return this.pants;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getOverpants() {
/* 174 */     return this.overpants;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getUndertop() {
/* 179 */     return this.undertop;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getOvertop() {
/* 184 */     return this.overtop;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getShoes() {
/* 189 */     return this.shoes;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getHeadAccessory() {
/* 194 */     return this.headAccessory;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getFaceAccessory() {
/* 199 */     return this.faceAccessory;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getEarAccessory() {
/* 204 */     return this.earAccessory;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getSkinFeature() {
/* 209 */     return this.skinFeature;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerSkinPartId getGloves() {
/* 214 */     return this.gloves;
/*     */   }
/*     */   @Nullable
/*     */   public PlayerSkinPartId getCape() {
/* 218 */     return this.cape;
/*     */   }
/*     */   
/*     */   public static class PlayerSkinPartId { public final String assetId;
/*     */     public final String textureId;
/*     */     public final String variantId;
/*     */     
/*     */     public PlayerSkinPartId(String assetId, String textureId, String variantId) {
/* 226 */       this.assetId = assetId;
/* 227 */       this.textureId = textureId;
/* 228 */       this.variantId = variantId;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public static PlayerSkinPartId fromString(@Nonnull String stringId) {
/* 233 */       String[] idParts = stringId.split("\\.");
/* 234 */       return new PlayerSkinPartId(idParts[0], (idParts.length > 1) ? idParts[1] : null, (idParts.length > 2) ? idParts[2] : null);
/*     */     }
/*     */     
/*     */     public String getAssetId() {
/* 238 */       return this.assetId;
/*     */     }
/*     */     
/*     */     public String getTextureId() {
/* 242 */       return this.textureId;
/*     */     }
/*     */     
/*     */     public String getVariantId() {
/* 246 */       return this.variantId;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 252 */       return "CharacterPartId{assetId='" + this.assetId + "', textureId='" + this.textureId + "', variantId='" + this.variantId + "'}";
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\PlayerSkin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */