/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
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
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.ItemQuality;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class ItemQuality
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, ItemQuality>>, NetworkSerializable<ItemQuality>
/*     */ {
/*     */   @Nonnull
/*     */   public static final AssetBuilderCodec<String, ItemQuality> CODEC;
/*     */   public static final int DEFAULT_INDEX = 0;
/*     */   public static final String DEFAULT_ID = "Default";
/*     */   
/*     */   static {
/* 144 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ItemQuality.class, ItemQuality::new, (Codec)Codec.STRING, (itemQuality, s) -> itemQuality.id = s, ItemQuality::getId, (itemQuality, data) -> itemQuality.data = data, itemQuality -> itemQuality.data).append(new KeyedCodec("QualityValue", (Codec)Codec.INTEGER), (itemQuality, integer) -> itemQuality.qualityValue = integer.intValue(), itemQuality -> Integer.valueOf(itemQuality.qualityValue)).documentation("Define the value of the quality to order them, 0 being the lowest quality.").add()).append(new KeyedCodec("ItemTooltipTexture", (Codec)Codec.STRING), (itemQuality, s) -> itemQuality.itemTooltipTexture = s, itemQuality -> itemQuality.itemTooltipTexture).documentation("The path to the texture of the item tooltip. It has to be located in Common/Items/Qualities.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM_QUALITY).add()).append(new KeyedCodec("ItemTooltipArrowTexture", (Codec)Codec.STRING), (itemQuality, s) -> itemQuality.itemTooltipArrowTexture = s, itemQuality -> itemQuality.itemTooltipArrowTexture).documentation("The path to the texture of the item tooltip arrow. It has to be located in Common/Items/Qualities.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM_QUALITY).add()).append(new KeyedCodec("SlotTexture", (Codec)Codec.STRING), (itemQuality, s) -> itemQuality.slotTexture = s, itemQuality -> itemQuality.slotTexture).documentation("The path to the texture of the item slot. It has to be located in Common/Items/Qualities.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM_QUALITY).add()).append(new KeyedCodec("BlockSlotTexture", (Codec)Codec.STRING), (itemQuality, s) -> itemQuality.blockSlotTexture = s, itemQuality -> itemQuality.blockSlotTexture).documentation("The path to the texture of the item slot, if it has an associated block type. It has to be located in Common/Items/Qualities.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM_QUALITY).add()).append(new KeyedCodec("SpecialSlotTexture", (Codec)Codec.STRING), (itemQuality, s) -> itemQuality.specialSlotTexture = s, itemQuality -> itemQuality.specialSlotTexture).documentation("The path to the texture of the item slot used when RenderSpecialSlot is true and the item is consumable or usable. It has to be located in Common/Items/Qualities.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM_QUALITY).add()).append(new KeyedCodec("TextColor", (Codec)ProtocolCodecs.COLOR), (itemQuality, s) -> itemQuality.textColor = s, itemQuality -> itemQuality.textColor).documentation("The color that'll be used to display the text of the item in the inventory.").addValidator(Validators.nonNull()).add()).append(new KeyedCodec("LocalizationKey", (Codec)Codec.STRING), (itemQuality, s) -> itemQuality.localizationKey = s, itemQuality -> itemQuality.localizationKey).documentation("The localization key for the item quality name.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.LocalizationKeyField("qualities.{assetId}", true))).add()).append(new KeyedCodec("VisibleQualityLabel", (Codec)Codec.BOOLEAN), (itemQuality, aBoolean) -> itemQuality.visibleQualityLabel = aBoolean.booleanValue(), itemQuality -> Boolean.valueOf(itemQuality.visibleQualityLabel)).documentation("To specify the quality label should be displayed in the tooltip.").add()).append(new KeyedCodec("RenderSpecialSlot", (Codec)Codec.BOOLEAN), (itemQuality, aBoolean) -> itemQuality.renderSpecialSlot = aBoolean.booleanValue(), itemQuality -> Boolean.valueOf(itemQuality.renderSpecialSlot)).documentation("To specify if we display a special slot texture if the item is a consumable or usable.").add()).append(new KeyedCodec("ItemEntityConfig", (Codec)ItemEntityConfig.CODEC), (itemQuality, itemEntityConfig) -> itemQuality.itemEntityConfig = itemEntityConfig, itemQuality -> itemQuality.itemEntityConfig).documentation("Provides an ItemEntityConfig used for all items with their item quality set to this asset unless overridden by an ItemEntityConfig defined on the item itself.").add()).append(new KeyedCodec("HideFromSearch", (Codec)Codec.BOOLEAN), (itemQuality, aBoolean) -> itemQuality.hideFromSearch = aBoolean.booleanValue(), itemQuality -> Boolean.valueOf(itemQuality.hideFromSearch)).documentation("Whether this item is hidden from typical public search, like the creative library").add()).build();
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
/*     */   @Nonnull
/* 160 */   public static final ItemQuality DEFAULT_ITEM_QUALITY = new ItemQuality("Default")
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 173 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ItemQuality::getAssetStore));
/*     */   
/*     */   private static AssetStore<String, ItemQuality, IndexedLookupTableAssetMap<String, ItemQuality>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   protected int qualityValue;
/*     */   protected String itemTooltipTexture;
/*     */   protected String itemTooltipArrowTexture;
/*     */   protected String slotTexture;
/*     */   
/*     */   @Nonnull
/*     */   public static AssetStore<String, ItemQuality, IndexedLookupTableAssetMap<String, ItemQuality>> getAssetStore() {
/* 185 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ItemQuality.class); 
/* 186 */     return ASSET_STORE;
/*     */   }
/*     */   protected String blockSlotTexture;
/*     */   protected String specialSlotTexture;
/*     */   protected Color textColor;
/*     */   
/*     */   @Nonnull
/*     */   public static IndexedLookupTableAssetMap<String, ItemQuality> getAssetMap() {
/* 194 */     return (IndexedLookupTableAssetMap<String, ItemQuality>)getAssetStore().getAssetMap();
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
/*     */   protected String localizationKey;
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
/*     */   protected boolean visibleQualityLabel;
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
/*     */   protected boolean renderSpecialSlot;
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
/*     */   protected ItemEntityConfig itemEntityConfig;
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
/*     */   protected boolean hideFromSearch = false;
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
/*     */   private transient SoftReference<ItemQuality> cachedPacket;
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
/*     */   public ItemQuality(String id, int qualityValue, String itemTooltipTexture, String itemTooltipArrowTexture, String slotTexture, String blockSlotTexture, String specialSlotTexture, Color textColor, String localizationKey, boolean visibleQualityLabel, boolean renderSpecialSlot, boolean hideFromSearch, ItemEntityConfig itemEntityConfig) {
/* 298 */     this.id = id;
/* 299 */     this.qualityValue = qualityValue;
/* 300 */     this.itemTooltipTexture = itemTooltipTexture;
/* 301 */     this.itemTooltipArrowTexture = itemTooltipArrowTexture;
/* 302 */     this.slotTexture = slotTexture;
/* 303 */     this.blockSlotTexture = blockSlotTexture;
/* 304 */     this.specialSlotTexture = specialSlotTexture;
/* 305 */     this.textColor = textColor;
/* 306 */     this.localizationKey = localizationKey;
/* 307 */     this.visibleQualityLabel = visibleQualityLabel;
/* 308 */     this.renderSpecialSlot = renderSpecialSlot;
/* 309 */     this.hideFromSearch = hideFromSearch;
/* 310 */     this.itemEntityConfig = itemEntityConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemQuality(@Nonnull String id) {
/* 321 */     this.id = id;
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
/*     */   public String getId() {
/* 334 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQualityValue() {
/* 341 */     return this.qualityValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemTooltipTexture() {
/* 348 */     return this.itemTooltipTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemTooltipArrowTexture() {
/* 355 */     return this.itemTooltipArrowTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSlotTexture() {
/* 362 */     return this.slotTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBlockSlotTexture() {
/* 369 */     return this.blockSlotTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSpecialSlotTexture() {
/* 376 */     return this.specialSlotTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getTextColor() {
/* 383 */     return this.textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizationKey() {
/* 390 */     return this.localizationKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisibleQualityLabel() {
/* 397 */     return this.visibleQualityLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRenderSpecialSlot() {
/* 404 */     return this.renderSpecialSlot;
/*     */   }
/*     */   
/*     */   public boolean isHiddenFromSearch() {
/* 408 */     return this.hideFromSearch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemEntityConfig getItemEntityConfig() {
/* 415 */     return this.itemEntityConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemQuality toPacket() {
/* 421 */     ItemQuality cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 422 */     if (cached != null) return cached;
/*     */     
/* 424 */     ItemQuality packet = new ItemQuality();
/*     */     
/* 426 */     packet.id = this.id;
/* 427 */     packet.itemTooltipTexture = this.itemTooltipTexture;
/* 428 */     packet.itemTooltipArrowTexture = this.itemTooltipArrowTexture;
/* 429 */     packet.slotTexture = this.slotTexture;
/* 430 */     packet.blockSlotTexture = this.blockSlotTexture;
/* 431 */     packet.specialSlotTexture = this.specialSlotTexture;
/* 432 */     packet.textColor = this.textColor;
/* 433 */     packet.localizationKey = this.localizationKey;
/* 434 */     packet.visibleQualityLabel = this.visibleQualityLabel;
/* 435 */     packet.renderSpecialSlot = this.renderSpecialSlot;
/* 436 */     packet.hideFromSearch = this.hideFromSearch;
/*     */     
/* 438 */     this.cachedPacket = new SoftReference<>(packet);
/*     */     
/* 440 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 446 */     return "ItemQuality{id='" + this.id + "', qualityValue=" + this.qualityValue + ", itemTooltipTexture='" + this.itemTooltipTexture + "', itemTooltipArrowTexture='" + this.itemTooltipArrowTexture + "', slotTexture='" + this.slotTexture + "', blockSlotTexture='" + this.blockSlotTexture + "', specialSlotTexture='" + this.specialSlotTexture + "', textColor='" + String.valueOf(this.textColor) + "', localizationKey='" + this.localizationKey + "', visibleQualityLabel=" + this.visibleQualityLabel + ", renderSpecialSlot=" + this.renderSpecialSlot + ", itemEntityConfig=" + String.valueOf(this.itemEntityConfig) + ", hideFromSearch=" + this.hideFromSearch + "}";
/*     */   }
/*     */   
/*     */   protected ItemQuality() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemQuality.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */