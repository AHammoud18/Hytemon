/*     */ package com.hypixel.hytale.server.core.cosmetics;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.common.util.RandomUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.PlayerSkin;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*     */ import com.hypixel.hytale.server.core.asset.LoadAssetEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.cosmetics.commands.EmoteCommand;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CosmeticsModule extends JavaPlugin {
/*  24 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(CosmeticsModule.class)
/*  25 */     .build();
/*     */   
/*     */   private static CosmeticsModule INSTANCE;
/*     */   
/*     */   private CosmeticRegistry registry;
/*     */   
/*     */   public CosmeticsModule(@Nonnull JavaPluginInit init) {
/*  32 */     super(init);
/*  33 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  38 */     this.registry = new CosmeticRegistry(AssetModule.get().getBaseAssetPack());
/*     */     
/*  40 */     getCommandRegistry().registerCommand((AbstractCommand)new EmoteCommand());
/*     */     
/*  42 */     if (Options.getOptionSet().has(Options.VALIDATE_ASSETS)) {
/*  43 */       getEventRegistry().register((short)64, LoadAssetEvent.class, this::validateGeneratedSkin);
/*     */     }
/*     */   }
/*     */   
/*     */   public CosmeticRegistry getRegistry() {
/*  48 */     return this.registry;
/*     */   }
/*     */   
/*     */   private void validateGeneratedSkin(@Nonnull LoadAssetEvent eventType) {
/*  52 */     for (int i = 0; i < 10; i++) {
/*  53 */       PlayerSkin skin = generateRandomSkin(new Random(i));
/*     */       try {
/*  55 */         validateSkin(skin);
/*  56 */       } catch (InvalidSkinException e) {
/*  57 */         eventType.failed(true, e.getMessage());
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Model createRandomModel(@Nonnull Random random) {
/*  65 */     PlayerSkin skin = generateRandomSkin(random);
/*  66 */     return get().createModel(skin);
/*     */   }
/*     */   @Nullable
/*     */   public Model createModel(@Nonnull PlayerSkin skin) {
/*  70 */     return createModel(skin, 1.0F);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Model createModel(@Nonnull PlayerSkin skin, float scale) {
/*     */     try {
/*  76 */       validateSkin(skin);
/*  77 */     } catch (InvalidSkinException e) {
/*  78 */       ((HytaleLogger.Api)getLogger().at(Level.WARNING).withCause(e)).log("Was passed an invalid skin %s", skin);
/*  79 */       return null;
/*     */     } 
/*  81 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset("Player");
/*  82 */     return Model.createScaledModel(modelAsset, scale, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateSkin(@Nonnull PlayerSkin skin) throws InvalidSkinException {
/*  88 */     if (skin == null) throw new InvalidSkinException("Skin can't be null!");
/*     */     
/*  90 */     if (skin.face == null || !this.registry.getFaces().containsKey(skin.face)) {
/*  91 */       throw new InvalidSkinException("Invalid face attachment!");
/*     */     }
/*     */     
/*  94 */     if (skin.ears == null || !this.registry.getEars().containsKey(skin.ears)) {
/*  95 */       throw new InvalidSkinException("Invalid ears attachment!");
/*     */     }
/*     */     
/*  98 */     if (skin.mouth == null || !this.registry.getMouths().containsKey(skin.mouth)) {
/*  99 */       throw new InvalidSkinException("Invalid mouth attachment!");
/*     */     }
/*     */     
/* 102 */     if (!isValidAttachment(this.registry.getBodyCharacteristics(), skin.bodyCharacteristic, true)) {
/* 103 */       throw new InvalidSkinException("Invalid body characteristic!");
/*     */     }
/* 105 */     if (!isValidAttachment(this.registry.getUnderwear(), skin.underwear, true)) {
/* 106 */       throw new InvalidSkinException("Invalid underwear attachment!");
/*     */     }
/* 108 */     if (!isValidAttachment(this.registry.getEyes(), skin.eyes, true)) {
/* 109 */       throw new InvalidSkinException("Invalid eye attachment!");
/*     */     }
/* 111 */     if (!isValidAttachment(this.registry.getSkinFeatures(), skin.skinFeature)) {
/* 112 */       throw new InvalidSkinException("Invalid skin feature attachment!");
/*     */     }
/* 114 */     if (!isValidAttachment(this.registry.getEyebrows(), skin.eyebrows)) {
/* 115 */       throw new InvalidSkinException("Invalid eye brows attachment!");
/*     */     }
/* 117 */     if (!isValidAttachment(this.registry.getPants(), skin.pants)) {
/* 118 */       throw new InvalidSkinException("Invalid pants attachment!");
/*     */     }
/* 120 */     if (!isValidAttachment(this.registry.getOverpants(), skin.overpants)) {
/* 121 */       throw new InvalidSkinException("Invalid overpants attachment!");
/*     */     }
/* 123 */     if (!isValidAttachment(this.registry.getShoes(), skin.shoes)) {
/* 124 */       throw new InvalidSkinException("Invalid shoes attachment!");
/*     */     }
/* 126 */     if (!isValidAttachment(this.registry.getUndertops(), skin.undertop)) {
/* 127 */       throw new InvalidSkinException("Invalid under top attachment!");
/*     */     }
/* 129 */     if (!isValidAttachment(this.registry.getOvertops(), skin.overtop)) {
/* 130 */       throw new InvalidSkinException("Invalid over top attachment!");
/*     */     }
/* 132 */     if (!isValidAttachment(this.registry.getGloves(), skin.gloves)) {
/* 133 */       throw new InvalidSkinException("Invalid gloves attachment!");
/*     */     }
/* 135 */     if (!isValidAttachment(this.registry.getHeadAccessories(), skin.headAccessory)) {
/* 136 */       throw new InvalidSkinException("Invalid head accessory attachment!");
/*     */     }
/* 138 */     if (!isValidAttachment(this.registry.getFaceAccessories(), skin.faceAccessory)) {
/* 139 */       throw new InvalidSkinException("Invalid face accessory attachment!");
/*     */     }
/* 141 */     if (!isValidAttachment(this.registry.getEarAccessories(), skin.earAccessory)) {
/* 142 */       throw new InvalidSkinException("Invalid ear accessory attachment!");
/*     */     }
/* 144 */     if (!isValidHaircutAttachment(skin.haircut, skin.headAccessory)) {
/* 145 */       throw new InvalidSkinException("Invalid haircut attachment!");
/*     */     }
/* 147 */     if (!isValidAttachment(this.registry.getFacialHairs(), skin.facialHair)) {
/* 148 */       throw new InvalidSkinException("Invalid facial accessory attachment!");
/*     */     }
/* 150 */     if (!isValidAttachment(this.registry.getCapes(), skin.cape)) {
/* 151 */       throw new InvalidSkinException("Invalid capes attachment!");
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isValidAttachment(@Nonnull Map<String, PlayerSkinPart> map, String id) {
/* 156 */     return isValidAttachment(map, id, false);
/*     */   }
/*     */   
/*     */   private boolean isValidTexture(@Nonnull PlayerSkinPart part, String variantId, String textureId) {
/* 160 */     if (part.getGradientSet() != null && (
/* 161 */       (PlayerSkinGradientSet)this.registry.getGradientSets().get(part.getGradientSet())).getGradients().containsKey(textureId)) return true;
/*     */ 
/*     */     
/* 164 */     if (part.getVariants() != null) {
/* 165 */       return ((PlayerSkinPart.Variant)part.getVariants().get(variantId)).getTextures().containsKey(textureId);
/*     */     }
/* 167 */     return part.getTextures().containsKey(textureId);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidAttachment(@Nonnull Map<String, PlayerSkinPart> map, @Nullable String id, boolean required) {
/* 172 */     if (id == null) return !required;
/*     */     
/* 174 */     String[] idParts = id.split("\\.");
/*     */     
/* 176 */     PlayerSkinPart skinPart = map.get(idParts[0]);
/* 177 */     if (skinPart == null) return false;
/*     */     
/* 179 */     String variantId = (idParts.length > 2 && !idParts[2].isEmpty()) ? idParts[2] : null;
/* 180 */     if (skinPart.getVariants() != null && !skinPart.getVariants().containsKey(variantId)) return false; 
/* 181 */     return isValidTexture(skinPart, variantId, idParts[1]);
/*     */   }
/*     */   
/*     */   private boolean isValidHaircutAttachment(@Nullable String haircutId, @Nullable String headAccessoryId) {
/* 185 */     if (haircutId == null) return true; 
/* 186 */     Map<String, PlayerSkinPart> haircuts = this.registry.getHaircuts();
/*     */     
/* 188 */     String[] idParts = haircutId.split("\\.");
/* 189 */     String haircutAssetId = idParts[0];
/* 190 */     String haircutAssetTextureId = (idParts.length > 1 && !idParts[1].isEmpty()) ? idParts[1] : null;
/*     */     
/* 192 */     if (headAccessoryId != null) {
/* 193 */       idParts = headAccessoryId.split("\\.");
/* 194 */       String headAccessoryAssetId = idParts[0];
/*     */       
/* 196 */       PlayerSkinPart headAccessoryPart = this.registry.getHeadAccessories().get(headAccessoryAssetId);
/* 197 */       if (headAccessoryPart != null) {
/* 198 */         PlayerSkinPart haircutPart; switch (headAccessoryPart.getHeadAccessoryType()) {
/*     */           case HalfCovering:
/* 200 */             haircutPart = haircuts.get(haircutAssetId);
/* 201 */             if (haircutPart == null) return false; 
/* 202 */             if (haircutPart.doesRequireGenericHaircut()) {
/* 203 */               PlayerSkinPart baseHaircutPart = haircuts.get("Generic" + String.valueOf(haircutPart.getHairType()));
/* 204 */               return isValidAttachment(haircuts, baseHaircutPart.getId() + "." + baseHaircutPart.getId(), false);
/*     */             } 
/*     */             break;
/*     */           case FullyCovering:
/* 208 */             return isValidAttachment(haircuts, haircutId);
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 213 */     return isValidAttachment(haircuts, haircutId);
/*     */   }
/*     */   
/*     */   public static CosmeticsModule get() {
/* 217 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PlayerSkin generateRandomSkin(@Nonnull Random random) {
/* 222 */     String bodyCharacteristic = randomSkinPart(this.registry.getBodyCharacteristics(), true, random);
/* 223 */     String underwear = randomSkinPart(this.registry.getUnderwear(), true, random);
/* 224 */     String face = randomSkinPart(this.registry.getFaces(), true, false, random);
/* 225 */     String ears = randomSkinPart(this.registry.getEars(), true, false, random);
/* 226 */     String mouth = randomSkinPart(this.registry.getMouths(), true, false, random);
/* 227 */     String eyes = randomSkinPart(this.registry.getEyes(), true, random);
/* 228 */     String facialHair = null;
/* 229 */     if (random.nextInt(10) > 4) {
/* 230 */       facialHair = randomSkinPart(this.registry.getFacialHairs(), random);
/*     */     }
/* 232 */     String haircut = randomSkinPart(this.registry.getHaircuts(), random);
/* 233 */     String eyebrows = randomSkinPart(this.registry.getEyebrows(), random);
/* 234 */     String pants = randomSkinPart(this.registry.getPants(), random);
/* 235 */     String overpants = null;
/* 236 */     String undertop = randomSkinPart(this.registry.getUndertops(), random);
/* 237 */     String overtop = randomSkinPart(this.registry.getOvertops(), random);
/* 238 */     String shoes = randomSkinPart(this.registry.getShoes(), random);
/* 239 */     String headAccessory = null;
/* 240 */     if (random.nextInt(10) > 8) {
/* 241 */       headAccessory = randomSkinPart(this.registry.getHeadAccessories(), random);
/*     */     }
/* 243 */     String faceAccessory = null;
/* 244 */     if (random.nextInt(10) > 8) {
/* 245 */       faceAccessory = randomSkinPart(this.registry.getFaceAccessories(), random);
/*     */     }
/* 247 */     String earAccessory = null;
/* 248 */     if (random.nextInt(10) > 8) {
/* 249 */       earAccessory = randomSkinPart(this.registry.getEarAccessories(), random);
/*     */     }
/* 251 */     String skinFeature = null;
/* 252 */     if (random.nextInt(10) > 8) {
/* 253 */       skinFeature = randomSkinPart(this.registry.getSkinFeatures(), random);
/*     */     }
/* 255 */     String gloves = null;
/* 256 */     return new PlayerSkin(bodyCharacteristic, underwear, face, eyes, ears, mouth, facialHair, haircut, eyebrows, pants, overpants, undertop, overtop, shoes, headAccessory, faceAccessory, earAccessory, skinFeature, gloves, null);
/*     */   }
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
/*     */   @Nullable
/*     */   private String randomSkinPart(@Nonnull Map<String, PlayerSkinPart> map, @Nonnull Random random) {
/* 282 */     return randomSkinPart(map, false, random);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private String randomSkinPart(@Nonnull Map<String, PlayerSkinPart> map, boolean required, @Nonnull Random random) {
/* 287 */     return randomSkinPart(map, required, true, random);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private String randomSkinPart(@Nonnull Map<String, PlayerSkinPart> map, boolean required, boolean color, @Nonnull Random random) {
/* 292 */     PlayerSkinPart[] arr = (PlayerSkinPart[])map.values().toArray(x$0 -> new PlayerSkinPart[x$0]);
/* 293 */     PlayerSkinPart part = required ? (PlayerSkinPart)RandomUtil.selectRandom((Object[])arr, random) : (PlayerSkinPart)RandomUtil.selectRandomOrNull((Object[])arr, random);
/* 294 */     if (part == null) return null;
/*     */     
/* 296 */     if (!color) return part.getId();
/*     */     
/* 298 */     String[] colors = ArrayUtil.EMPTY_STRING_ARRAY;
/* 299 */     if (part.getGradientSet() != null) {
/* 300 */       colors = (String[])((PlayerSkinGradientSet)this.registry.getGradientSets().get(part.getGradientSet())).getGradients().keySet().toArray(x$0 -> new String[x$0]);
/*     */     }
/*     */     
/* 303 */     Map<String, PlayerSkinPartTexture> textures = part.getTextures();
/* 304 */     String variantId = null;
/* 305 */     if (part.getVariants() != null) {
/* 306 */       variantId = (String)RandomUtil.selectRandom(part.getVariants().keySet().toArray(x$0 -> new String[x$0]), random);
/* 307 */       textures = ((PlayerSkinPart.Variant)part.getVariants().get(variantId)).getTextures();
/*     */     } 
/*     */     
/* 310 */     if (textures != null) {
/* 311 */       colors = (String[])ArrayUtil.combine((Object[])colors, textures.keySet().toArray(x$0 -> new String[x$0]));
/*     */     }
/*     */     
/* 314 */     String colorId = (String)RandomUtil.selectRandom((Object[])colors, random);
/*     */     
/* 316 */     if (variantId == null) return part.getId() + "." + part.getId(); 
/* 317 */     return part.getId() + "." + part.getId() + "." + colorId;
/*     */   }
/*     */   
/*     */   public static class InvalidSkinException extends Exception {
/*     */     public InvalidSkinException(String message) {
/* 322 */       super(message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\CosmeticsModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */