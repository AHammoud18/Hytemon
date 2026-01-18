/*     */ package com.hypixel.hytale.server.core.cosmetics;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonArray;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ public class CosmeticRegistry
/*     */ {
/*     */   public static final String MODEL = "Characters/Player.blockymodel";
/*     */   public static final String SKIN_GRADIENTSET_ID = "Skin";
/*     */   @Nonnull
/*     */   private final Map<String, Emote> emotes;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinTintColor> eyeColors;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinGradientSet> gradientSets;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> bodyCharacteristics;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> underwear;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> eyebrows;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> ears;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> eyes;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> faces;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> mouths;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> facialHair;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> pants;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> overpants;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> undertops;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> overtops;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> haircuts;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> shoes;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> headAccessory;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> faceAccessory;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> earAccessory;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> gloves;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> capes;
/*     */   @Nonnull
/*     */   private final Map<String, PlayerSkinPart> skinFeatures;
/*     */   
/*     */   public CosmeticRegistry(@Nonnull AssetPack pack) {
/*  68 */     Path assetsDirectory = pack.getRoot();
/*  69 */     this.emotes = load(assetsDirectory, "Emotes.json", Emote::new);
/*     */     
/*  71 */     this.eyeColors = load(assetsDirectory, "EyeColors.json", PlayerSkinTintColor::new);
/*  72 */     this.gradientSets = load(assetsDirectory, "GradientSets.json", PlayerSkinGradientSet::new);
/*     */     
/*  74 */     this.bodyCharacteristics = load(assetsDirectory, "BodyCharacteristics.json", PlayerSkinPart::new);
/*  75 */     this.underwear = load(assetsDirectory, "Underwear.json", PlayerSkinPart::new);
/*  76 */     this.eyes = load(assetsDirectory, "Eyes.json", PlayerSkinPart::new);
/*  77 */     this.faces = load(assetsDirectory, "Faces.json", PlayerSkinPart::new);
/*  78 */     this.eyebrows = load(assetsDirectory, "Eyebrows.json", PlayerSkinPart::new);
/*  79 */     this.ears = load(assetsDirectory, "Ears.json", PlayerSkinPart::new);
/*  80 */     this.mouths = load(assetsDirectory, "Mouths.json", PlayerSkinPart::new);
/*  81 */     this.facialHair = load(assetsDirectory, "FacialHair.json", PlayerSkinPart::new);
/*  82 */     this.pants = load(assetsDirectory, "Pants.json", PlayerSkinPart::new);
/*  83 */     this.overpants = load(assetsDirectory, "Overpants.json", PlayerSkinPart::new);
/*  84 */     this.undertops = load(assetsDirectory, "Undertops.json", PlayerSkinPart::new);
/*  85 */     this.overtops = load(assetsDirectory, "Overtops.json", PlayerSkinPart::new);
/*  86 */     this.haircuts = load(assetsDirectory, "Haircuts.json", PlayerSkinPart::new);
/*  87 */     this.shoes = load(assetsDirectory, "Shoes.json", PlayerSkinPart::new);
/*  88 */     this.headAccessory = load(assetsDirectory, "HeadAccessory.json", PlayerSkinPart::new);
/*  89 */     this.faceAccessory = load(assetsDirectory, "FaceAccessory.json", PlayerSkinPart::new);
/*  90 */     this.earAccessory = load(assetsDirectory, "EarAccessory.json", PlayerSkinPart::new);
/*  91 */     this.gloves = load(assetsDirectory, "Gloves.json", PlayerSkinPart::new);
/*  92 */     this.capes = load(assetsDirectory, "Capes.json", PlayerSkinPart::new);
/*  93 */     this.skinFeatures = load(assetsDirectory, "SkinFeatures.json", PlayerSkinPart::new);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private <T> Map<String, T> load(@Nonnull Path assetsDirectory, @Nonnull String file, @Nonnull Function<BsonDocument, T> func) {
/*  98 */     Object2ObjectOpenHashMap object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*  99 */     Path path = assetsDirectory.resolve("Cosmetics").resolve("CharacterCreator").resolve(file);
/*     */     
/*     */     try {
/* 102 */       BsonArray bsonArray = BsonArray.parse(Files.readString(path));
/* 103 */       for (BsonValue bsonValue : bsonArray) {
/* 104 */         BsonDocument doc = bsonValue.asDocument();
/* 105 */         object2ObjectOpenHashMap.put(doc.getString("Id").getValue(), func.apply(doc));
/*     */       } 
/* 107 */     } catch (IOException e) {
/* 108 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 111 */     return Collections.unmodifiableMap((Map<? extends String, ? extends T>)object2ObjectOpenHashMap);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, Emote> getEmotes() {
/* 116 */     return this.emotes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinTintColor> getEyeColors() {
/* 121 */     return this.eyeColors;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinGradientSet> getGradientSets() {
/* 126 */     return this.gradientSets;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getBodyCharacteristics() {
/* 131 */     return this.bodyCharacteristics;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getUnderwear() {
/* 136 */     return this.underwear;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getEyebrows() {
/* 141 */     return this.eyebrows;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getEars() {
/* 146 */     return this.ears;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getEyes() {
/* 151 */     return this.eyes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getFaces() {
/* 156 */     return this.faces;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getMouths() {
/* 161 */     return this.mouths;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getFacialHairs() {
/* 166 */     return this.facialHair;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getPants() {
/* 171 */     return this.pants;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getOverpants() {
/* 176 */     return this.overpants;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getUndertops() {
/* 181 */     return this.undertops;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getOvertops() {
/* 186 */     return this.overtops;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getHaircuts() {
/* 191 */     return this.haircuts;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getShoes() {
/* 196 */     return this.shoes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getHeadAccessories() {
/* 201 */     return this.headAccessory;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getFaceAccessories() {
/* 206 */     return this.faceAccessory;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getEarAccessories() {
/* 211 */     return this.earAccessory;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getGloves() {
/* 216 */     return this.gloves;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getSkinFeatures() {
/* 221 */     return this.skinFeatures;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerSkinPart> getCapes() {
/* 226 */     return this.capes;
/*     */   }
/*     */   
/*     */   public Map<String, ?> getByType(@Nonnull CosmeticType type) {
/* 230 */     switch (type) { default: throw new MatchException(null, null);case EMOTES: case SKIN_TONES: case EYE_COLORS: case GRADIENT_SETS: case BODY_CHARACTERISTICS: case UNDERWEAR: case EYEBROWS: case EARS: case EYES: case FACE: case MOUTHS: case FACIAL_HAIR: case PANTS: case OVERPANTS: case UNDERTOPS: case OVERTOPS: case HAIRCUTS: case SHOES: case HEAD_ACCESSORY: case FACE_ACCESSORY: case EAR_ACCESSORY: case GLOVES: case CAPES: case SKIN_FEATURES: break; }  return 
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
/* 254 */       getSkinFeatures();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\CosmeticRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */