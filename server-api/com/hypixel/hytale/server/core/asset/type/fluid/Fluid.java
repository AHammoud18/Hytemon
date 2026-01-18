/*     */ package com.hypixel.hytale.server.core.asset.type.fluid;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIPropertyTitle;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIRebuildCaches;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.BlockTextures;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.protocol.Fluid;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.Opacity;
/*     */ import com.hypixel.hytale.protocol.ShaderType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockparticle.config.BlockParticleSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocksound.config.BlockSoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockTypeTextures;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluidfx.config.FluidFX;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.InteractionTypeUtils;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.palette.ISectionPalette;
/*     */ import com.hypixel.hytale.server.core.util.io.ByteBufUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.ToIntFunction;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class Fluid
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, Fluid>>, NetworkSerializable<Fluid>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, Fluid> CODEC;
/*     */   
/*     */   static {
/* 162 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(Fluid.class, Fluid::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("MaxFluidLevel", (Codec)Codec.INTEGER), (asset, v) -> asset.maxFluidLevel = v.intValue(), asset -> Integer.valueOf(asset.maxFluidLevel), (asset, parent) -> asset.maxFluidLevel = parent.maxFluidLevel).addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(15))).add()).appendInherited(new KeyedCodec("Textures", (Codec)new ArrayCodec((Codec)BlockTypeTextures.CODEC, x$0 -> new BlockTypeTextures[x$0])), (fluid, o) -> fluid.textures = o, fluid -> fluid.textures, (fluid, parent) -> fluid.textures = parent.textures).metadata((Metadata)new UIPropertyTitle("Block Textures")).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS, UIRebuildCaches.ClientCache.BLOCK_TEXTURES })).add()).appendInherited(new KeyedCodec("Effect", (Codec)new ArrayCodec((Codec)new EnumCodec(ShaderType.class), x$0 -> new ShaderType[x$0])), (fluid, o) -> fluid.effect = o, fluid -> fluid.effect, (fluid, parent) -> fluid.effect = parent.effect).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).add()).appendInherited(new KeyedCodec("Opacity", (Codec)new EnumCodec(Opacity.class)), (fluid, o) -> fluid.opacity = o, fluid -> fluid.opacity, (fluid, parent) -> fluid.opacity = parent.opacity).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("RequiresAlphaBlending", (Codec)Codec.BOOLEAN), (fluid, o) -> fluid.requiresAlphaBlending = o.booleanValue(), fluid -> Boolean.valueOf(fluid.requiresAlphaBlending), (fluid, parent) -> fluid.requiresAlphaBlending = parent.requiresAlphaBlending).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).add()).appendInherited(new KeyedCodec("FluidFXId", (Codec)Codec.STRING), (fluid, o) -> fluid.fluidFXId = o, fluid -> fluid.fluidFXId, (fluid, parent) -> fluid.fluidFXId = parent.fluidFXId).addValidator(FluidFX.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("Ticker", (Codec)FluidTicker.CODEC), (fluid, o) -> fluid.ticker = o, fluid -> fluid.ticker, (fluid, parent) -> fluid.ticker = parent.ticker).add()).appendInherited(new KeyedCodec("Light", (Codec)ProtocolCodecs.COLOR_LIGHT), (fluid, o) -> fluid.light = o, fluid -> fluid.light, (fluid, parent) -> fluid.light = parent.light).add()).appendInherited(new KeyedCodec("DamageToEntities", (Codec)Codec.INTEGER), (fluid, s) -> fluid.damageToEntities = s.intValue(), fluid -> Integer.valueOf(fluid.damageToEntities), (fluid, parent) -> fluid.damageToEntities = parent.damageToEntities).add()).appendInherited(new KeyedCodec("BlockParticleSetId", (Codec)Codec.STRING), (fluid, s) -> fluid.blockParticleSetId = s, fluid -> fluid.blockParticleSetId, (fluid, parent) -> fluid.blockParticleSetId = parent.blockParticleSetId).documentation("The block particle set defined here defines which particles should be spawned when an entity interacts with this block (like when stepping on it for example").addValidator(BlockParticleSet.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("ParticleColor", (Codec)ProtocolCodecs.COLOR), (fluid, s) -> fluid.particleColor = s, fluid -> fluid.particleColor, (fluid, parent) -> fluid.particleColor = parent.particleColor).add()).appendInherited(new KeyedCodec("BlockSoundSetId", (Codec)Codec.STRING), (fluid, o) -> fluid.blockSoundSetId = o, fluid -> fluid.blockSoundSetId, (fluid, parent) -> fluid.blockSoundSetId = parent.blockSoundSetId).documentation("Sets the **BlockSoundSet** that will be used for this block for various events e.g. placement, breaking").addValidator(BlockSoundSet.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("Interactions", (Codec)new EnumMapCodec(InteractionType.class, (Codec)RootInteraction.CHILD_ASSET_CODEC)), (item, v) -> item.interactions = v, item -> item.interactions, (item, parent) -> item.interactions = parent.interactions).addValidator((Validator)RootInteraction.VALIDATOR_CACHE.getMapValueValidator()).metadata((Metadata)new UIEditorSectionStart("Interactions")).add()).afterDecode(Fluid::processConfig)).build();
/*     */   }
/* 164 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(Fluid::getAssetStore));
/*     */   
/*     */   public static final String UNKNOWN_TEXTURE = "BlockTextures/Unknown.png";
/*     */   
/* 168 */   public static final BlockTextures[] UNKNOWN_BLOCK_TEXTURES = new BlockTextures[] { new BlockTextures("BlockTextures/Unknown.png", "BlockTextures/Unknown.png", "BlockTextures/Unknown.png", "BlockTextures/Unknown.png", "BlockTextures/Unknown.png", "BlockTextures/Unknown.png", 1.0F) }; public static final ISectionPalette.KeySerializer KEY_SERIALIZER;
/* 169 */   public static final ShaderType[] DEFAULT_SHADER_EFFECTS = new ShaderType[] { ShaderType.None }; public static final ToIntFunction<ByteBuf> KEY_DESERIALIZER;
/*     */   static {
/* 171 */     KEY_SERIALIZER = ((buf, id) -> {
/*     */         String key = ((Fluid)getAssetMap().getAssetOrDefault(id, UNKNOWN)).getId();
/*     */         ByteBufUtil.writeUTF(buf, key);
/*     */       });
/* 175 */     KEY_DESERIALIZER = (byteBuf -> {
/*     */         String fluid = ByteBufUtil.readUTF(byteBuf);
/*     */         return getFluidIdOrUnknown(fluid, "Failed to find fluid '%s' in chunk section!", new Object[] { fluid });
/*     */       });
/*     */   }
/*     */   public static final int EMPTY_ID = 0;
/*     */   public static final String EMPTY_KEY = "Empty";
/* 182 */   public static final Fluid EMPTY = new Fluid("Empty")
/*     */     {
/*     */     
/*     */     };
/*     */   public static final int UNKNOWN_ID = 1;
/* 187 */   public static final Fluid UNKNOWN = new Fluid("Unknown") {  }
/*     */   ;
/*     */   private static AssetStore<String, Fluid, IndexedLookupTableAssetMap<String, Fluid>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   protected boolean unknown;
/*     */   
/*     */   public static AssetStore<String, Fluid, IndexedLookupTableAssetMap<String, Fluid>> getAssetStore() {
/* 195 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(Fluid.class); 
/* 196 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, Fluid> getAssetMap() {
/* 200 */     return (IndexedLookupTableAssetMap<String, Fluid>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   private int maxFluidLevel = 8; private BlockTypeTextures[] textures;
/*     */   private ShaderType[] effect;
/*     */   @Nonnull
/* 210 */   private Opacity opacity = Opacity.Solid;
/*     */   
/*     */   private boolean requiresAlphaBlending = true;
/* 213 */   private String fluidFXId = "Empty";
/* 214 */   protected transient int fluidFXIndex = 0;
/* 215 */   private FluidTicker ticker = DefaultFluidTicker.INSTANCE;
/*     */   
/*     */   protected int damageToEntities;
/*     */   protected ColorLight light;
/*     */   protected Color particleColor;
/* 220 */   protected String blockSoundSetId = "EMPTY";
/* 221 */   protected transient int blockSoundSetIndex = 0;
/*     */   
/*     */   public String blockParticleSetId;
/* 224 */   protected Map<InteractionType, String> interactions = Collections.emptyMap();
/*     */ 
/*     */   
/*     */   protected transient boolean isTrigger;
/*     */ 
/*     */ 
/*     */   
/*     */   public Fluid(String id) {
/* 232 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Fluid(@Nonnull Fluid other) {
/* 236 */     this.data = other.data;
/* 237 */     this.id = other.id;
/* 238 */     this.unknown = other.unknown;
/* 239 */     this.maxFluidLevel = other.maxFluidLevel;
/* 240 */     this.textures = other.textures;
/* 241 */     this.effect = other.effect;
/* 242 */     this.opacity = other.opacity;
/* 243 */     this.requiresAlphaBlending = other.requiresAlphaBlending;
/* 244 */     this.fluidFXId = other.fluidFXId;
/* 245 */     this.damageToEntities = other.damageToEntities;
/* 246 */     this.light = other.light;
/* 247 */     this.particleColor = other.particleColor;
/* 248 */     this.blockSoundSetId = other.blockSoundSetId;
/* 249 */     this.interactions = other.interactions;
/* 250 */     this.isTrigger = other.isTrigger;
/* 251 */     processConfig();
/*     */   }
/*     */   
/*     */   public AssetExtraInfo.Data getData() {
/* 255 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 260 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean isUnknown() {
/* 264 */     return this.unknown;
/*     */   }
/*     */   
/*     */   public int getMaxFluidLevel() {
/* 268 */     return this.maxFluidLevel;
/*     */   }
/*     */   
/*     */   public FluidTicker getTicker() {
/* 272 */     return this.ticker;
/*     */   }
/*     */   
/*     */   public int getDamageToEntities() {
/* 276 */     return this.damageToEntities;
/*     */   }
/*     */   
/*     */   public String getFluidFXId() {
/* 280 */     return this.fluidFXId;
/*     */   }
/*     */   
/*     */   public int getFluidFXIndex() {
/* 284 */     return this.fluidFXIndex;
/*     */   }
/*     */   
/*     */   public ColorLight getLight() {
/* 288 */     return this.light;
/*     */   }
/*     */   
/*     */   public Color getParticleColor() {
/* 292 */     return this.particleColor;
/*     */   }
/*     */   
/*     */   public boolean isTrigger() {
/* 296 */     return this.isTrigger;
/*     */   }
/*     */   
/*     */   public Map<InteractionType, String> getInteractions() {
/* 300 */     return this.interactions;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 304 */     this.fluidFXIndex = this.fluidFXId.equals("Empty") ? 0 : FluidFX.getAssetMap().getIndex(this.fluidFXId);
/* 305 */     this.blockSoundSetIndex = this.blockSoundSetId.equals("EMPTY") ? 0 : BlockSoundSet.getAssetMap().getIndex(this.blockSoundSetId);
/*     */     
/* 307 */     for (InteractionType type : this.interactions.keySet()) {
/* 308 */       if (InteractionTypeUtils.isCollisionType(type)) {
/* 309 */         this.isTrigger = true;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Fluid getUnknownFor(String key) {
/* 317 */     return UNKNOWN.clone(key);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Fluid clone(String newKey) {
/* 322 */     if (this.id != null && this.id.equals(newKey)) return this; 
/* 323 */     Fluid fluid = new Fluid(this);
/* 324 */     fluid.id = newKey;
/*     */     
/* 326 */     return fluid;
/*     */   }
/*     */   
/*     */   public static int getFluidIdOrUnknown(String key, String message, Object... params) {
/* 330 */     return getFluidIdOrUnknown(getAssetMap(), key, message, params);
/*     */   }
/*     */   
/*     */   public static int getFluidIdOrUnknown(@Nonnull IndexedLookupTableAssetMap<String, Fluid> assetMap, String key, String message, Object... params) {
/* 334 */     int fluidId = assetMap.getIndex(key);
/*     */ 
/*     */     
/* 337 */     if (fluidId == Integer.MIN_VALUE) {
/* 338 */       HytaleLogger.getLogger().at(Level.WARNING).logVarargs(message, params);
/* 339 */       AssetRegistry.getAssetStore(Fluid.class).loadAssets("Hytale:Hytale", Collections.singletonList(
/* 340 */             getUnknownFor(key)));
/*     */       
/* 342 */       int index = assetMap.getIndex(key);
/* 343 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 344 */       fluidId = index;
/*     */     } 
/*     */     
/* 347 */     return fluidId;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Fluid toPacket() {
/* 353 */     Fluid packet = new Fluid();
/* 354 */     packet.id = this.id;
/* 355 */     packet.maxFluidLevel = this.maxFluidLevel;
/* 356 */     packet.fluidFXIndex = this.fluidFXIndex;
/*     */     
/* 358 */     packet.opacity = this.opacity;
/* 359 */     packet.light = this.light;
/*     */     
/* 361 */     if (this.effect != null && this.effect.length > 0) {
/* 362 */       packet.shaderEffect = this.effect;
/*     */     } else {
/* 364 */       packet.shaderEffect = DEFAULT_SHADER_EFFECTS;
/*     */     } 
/*     */     
/* 367 */     if (this.textures != null && this.textures.length > 0) {
/* 368 */       int totalWeight = 0;
/* 369 */       for (BlockTypeTextures texture : this.textures) {
/* 370 */         totalWeight = (int)(totalWeight + texture.getWeight());
/*     */       }
/* 372 */       BlockTextures[] texturePackets = new BlockTextures[this.textures.length];
/* 373 */       for (int i = 0; i < this.textures.length; i++) {
/* 374 */         texturePackets[i] = this.textures[i].toPacket(totalWeight);
/*     */       }
/* 376 */       packet.cubeTextures = texturePackets;
/*     */     } else {
/* 378 */       packet.cubeTextures = UNKNOWN_BLOCK_TEXTURES;
/*     */     } 
/* 380 */     packet.requiresAlphaBlending = this.requiresAlphaBlending;
/* 381 */     packet.blockSoundSetIndex = this.blockSoundSetIndex;
/* 382 */     packet.blockParticleSetId = this.blockParticleSetId;
/* 383 */     packet.particleColor = this.particleColor;
/* 384 */     packet.fluidFXIndex = this.fluidFXIndex;
/*     */     
/* 386 */     if (this.data != null) {
/* 387 */       IntSet tags = this.data.getExpandedTagIndexes();
/* 388 */       if (tags != null) {
/* 389 */         packet.tagIndexes = tags.toIntArray();
/*     */       }
/*     */     } 
/*     */     
/* 393 */     return packet;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @Deprecated(forRemoval = true)
/*     */   public static String convertLegacyName(@Nonnull String fluidName, byte level) {
/* 399 */     switch (fluidName) { case "Fluid_Water": return 
/* 400 */           (level == 0) ? "Water_Source" : "Water";
/*     */       case "Fluid_Water_Test": 
/* 402 */       case "Fluid_Lava": return (level == 0) ? "Lava_Source" : "Lava";
/* 403 */       case "Fluid_Tar": return (level == 0) ? "Tar_Source" : "Tar";
/* 404 */       case "Fluid_Slime": return (level == 0) ? "Slime_Source" : "Slime";
/* 405 */       case "Fluid_Poison": return (level == 0) ? "Poison_Source" : "Poison"; }
/* 406 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Deprecated(forRemoval = true)
/*     */   public static ConversionResult convertBlockToFluid(@Nonnull String blockTypeStr) {
/* 414 */     int fluidPos = blockTypeStr.indexOf("|Fluid=");
/* 415 */     if (fluidPos != -1) {
/* 416 */       int fluidNameEnd = blockTypeStr.indexOf('|', fluidPos + 2);
/*     */       
/* 418 */       if (fluidNameEnd != -1) {
/* 419 */         fluidName = blockTypeStr.substring(fluidPos + "|Fluid=".length(), fluidNameEnd);
/* 420 */         blockTypeStr = blockTypeStr.substring(0, fluidPos) + blockTypeStr.substring(0, fluidPos);
/*     */       } else {
/* 422 */         fluidName = blockTypeStr.substring(fluidPos + "|Fluid=".length());
/* 423 */         blockTypeStr = blockTypeStr.substring(0, fluidPos);
/*     */       } 
/*     */ 
/*     */       
/* 427 */       int fluidLevelStart = blockTypeStr.indexOf("|FluidLevel=");
/* 428 */       if (fluidLevelStart != -1) {
/* 429 */         String fluidLevelStr; int fluidLevelEnd = blockTypeStr.indexOf('|', fluidLevelStart + 2);
/*     */         
/* 431 */         if (fluidLevelEnd != -1) {
/* 432 */           fluidLevelStr = blockTypeStr.substring(fluidLevelStart + "|FluidLevel=".length(), fluidLevelEnd);
/* 433 */           blockTypeStr = blockTypeStr.substring(0, fluidLevelStart) + blockTypeStr.substring(0, fluidLevelStart);
/*     */         } else {
/* 435 */           fluidLevelStr = blockTypeStr.substring(fluidLevelStart + "|FluidLevel=".length());
/* 436 */           blockTypeStr = blockTypeStr.substring(0, fluidLevelStart);
/*     */         } 
/* 438 */         fluidLevel = Byte.parseByte(fluidLevelStr);
/*     */       } else {
/* 440 */         fluidLevel = 0;
/*     */       } 
/*     */       
/* 443 */       String fluidName = convertLegacyName(fluidName, fluidLevel);
/* 444 */       int fluidId = getFluidIdOrUnknown(fluidName, "Failed to find fluid '%s'", new Object[] { fluidName });
/* 445 */       byte fluidLevel = (fluidLevel == 0) ? (byte)((Fluid)getAssetMap().getAsset(fluidId)).getMaxFluidLevel() : fluidLevel;
/* 446 */       return new ConversionResult(blockTypeStr, fluidId, fluidLevel);
/* 447 */     }  if (blockTypeStr.startsWith("Fluid_")) {
/*     */       
/* 449 */       int fluidLevelStart = blockTypeStr.indexOf("|FluidLevel=");
/* 450 */       if (fluidLevelStart != -1) {
/* 451 */         String fluidLevelStr; int fluidLevelEnd = blockTypeStr.indexOf('|', fluidLevelStart + 2);
/*     */         
/* 453 */         if (fluidLevelEnd != -1) {
/* 454 */           fluidLevelStr = blockTypeStr.substring(fluidLevelStart + "|FluidLevel=".length(), fluidLevelEnd);
/* 455 */           blockTypeStr = blockTypeStr.substring(0, fluidLevelStart) + blockTypeStr.substring(0, fluidLevelStart);
/*     */         } else {
/* 457 */           fluidLevelStr = blockTypeStr.substring(fluidLevelStart + "|FluidLevel=".length());
/* 458 */           blockTypeStr = blockTypeStr.substring(0, fluidLevelStart);
/*     */         } 
/* 460 */         fluidLevel = Byte.parseByte(fluidLevelStr);
/*     */       } else {
/* 462 */         fluidLevel = 0;
/*     */       } 
/*     */       
/* 465 */       String newFluidName = convertLegacyName(blockTypeStr, fluidLevel);
/* 466 */       int fluidId = getFluidIdOrUnknown(newFluidName, "Failed to find fluid '%s'", new Object[] { newFluidName });
/* 467 */       byte fluidLevel = (fluidLevel == 0) ? (byte)((Fluid)getAssetMap().getAsset(fluidId)).getMaxFluidLevel() : fluidLevel;
/* 468 */       return new ConversionResult(null, fluidId, fluidLevel);
/*     */     } 
/*     */     
/* 471 */     return null;
/*     */   }
/*     */   public Fluid() {}
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public static class ConversionResult { public String blockTypeStr;
/*     */     public int fluidId;
/*     */     public byte fluidLevel;
/*     */     
/*     */     public ConversionResult(String blockTypeStr, int fluidId, byte fluidLevel) {
/* 481 */       this.blockTypeStr = blockTypeStr;
/* 482 */       this.fluidId = fluidId;
/* 483 */       this.fluidLevel = fluidLevel;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\fluid\Fluid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */