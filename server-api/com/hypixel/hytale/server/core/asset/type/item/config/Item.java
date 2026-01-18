/*      */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*      */ 
/*      */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*      */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*      */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*      */ import com.hypixel.hytale.assetstore.AssetStore;
/*      */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*      */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*      */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*      */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*      */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*      */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*      */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*      */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*      */ import com.hypixel.hytale.codec.schema.metadata.AllowEmptyObject;
/*      */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIButton;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UICreateButtons;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDisplayMode;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorPreview;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIPropertyTitle;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIRebuildCaches;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UISidebarButtons;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UITypeIcon;
/*      */ import com.hypixel.hytale.codec.validation.LateValidator;
/*      */ import com.hypixel.hytale.codec.validation.Validator;
/*      */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*      */ import com.hypixel.hytale.codec.validation.Validators;
/*      */ import com.hypixel.hytale.common.util.MapUtil;
/*      */ import com.hypixel.hytale.protocol.ColorLight;
/*      */ import com.hypixel.hytale.protocol.InteractionType;
/*      */ import com.hypixel.hytale.protocol.ItemAppearanceCondition;
/*      */ import com.hypixel.hytale.protocol.ItemBase;
/*      */ import com.hypixel.hytale.protocol.ItemResourceType;
/*      */ import com.hypixel.hytale.protocol.ModelTrail;
/*      */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*      */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BuilderToolData;
/*      */ import com.hypixel.hytale.server.core.asset.type.itemanimation.config.ItemPlayerAnimations;
/*      */ import com.hypixel.hytale.server.core.asset.type.itemsound.config.ItemSoundSet;
/*      */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*      */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*      */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*      */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*      */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*      */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*      */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*      */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.UnarmedInteractions;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.InteractionConfiguration;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*      */ import it.unimi.dsi.fastutil.ints.IntSet;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.EnumMap;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Item
/*      */   implements JsonAssetWithMap<String, DefaultAssetMap<String, Item>>, NetworkSerializable<ItemBase>
/*      */ {
/*      */   private static final AssetBuilderCodec.Builder<String, Item> CODEC_BUILDER;
/*      */   
/*      */   static {
/*  468 */     CODEC_BUILDER = (AssetBuilderCodec.Builder<String, Item>)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(Item.class, Item::new, (Codec)Codec.STRING, (item, blockTypeKey) -> item.id = blockTypeKey, item -> item.id, (asset, data) -> asset.data = data, asset -> asset.data).metadata((Metadata)new UIEditorPreview(UIEditorPreview.PreviewType.ITEM))).metadata((Metadata)new UITypeIcon("Item.png"))).metadata((Metadata)new UIRebuildCaches(false, new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS, UIRebuildCaches.ClientCache.BLOCK_TEXTURES, UIRebuildCaches.ClientCache.MODEL_TEXTURES, UIRebuildCaches.ClientCache.MAP_GEOMETRY, UIRebuildCaches.ClientCache.ITEM_ICONS }))).metadata((Metadata)new UISidebarButtons(new UIButton[] { new UIButton("server.assetEditor.buttons.equipItem", "EquipItem") }))).metadata((Metadata)new UICreateButtons(new UIButton[] { new UIButton("server.assetEditor.buttons.createAndEquipItem", "EquipItem") }))).appendInherited(new KeyedCodec("Icon", (Codec)Codec.STRING), (item, s) -> item.icon = s, item -> item.icon, (item, parent) -> item.icon = parent.icon).addValidator((Validator)CommonAssetValidator.ICON_ITEM).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.Icon("Icons/ItemsGenerated/{assetId}.png", 64, 64))).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.ITEM_ICONS })).add()).appendInherited(new KeyedCodec("Categories", (Codec)(new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.Dropdown("ItemCategories")))), (item, s) -> item.categories = s, item -> item.categories, (item, parent) -> item.categories = parent.categories).addValidatorLate(() -> ItemCategory.VALIDATOR_CACHE.getArrayValidator().late()).documentation("A list of categories this item will be shown in on the creative library menu.").add()).appendInherited(new KeyedCodec("IconProperties", (Codec)AssetIconProperties.CODEC), (item, s) -> item.iconProperties = s, item -> item.iconProperties, (item, parent) -> item.iconProperties = parent.iconProperties).metadata((Metadata)UIDisplayMode.HIDDEN).add()).appendInherited(new KeyedCodec("TranslationProperties", (Codec)ItemTranslationProperties.CODEC), (item, s) -> item.translationProperties = s, item -> item.translationProperties, (item, parent) -> item.translationProperties = parent.translationProperties).documentation("The translation properties for this item asset.").add()).appendInherited(new KeyedCodec("ItemLevel", (Codec)Codec.INTEGER), (item, s) -> item.itemLevel = s.intValue(), item -> Integer.valueOf(item.itemLevel), (item, parent) -> item.itemLevel = parent.itemLevel).add()).appendInherited(new KeyedCodec("Reticle", (Codec)new ContainedAssetCodec(ItemReticleConfig.class, (AssetCodec)ItemReticleConfig.CODEC)), (item, s) -> item.reticleId = s, item -> item.reticleId, (item, parent) -> item.reticleId = parent.reticleId).addValidator(ItemReticleConfig.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("MaxStack", (Codec)Codec.INTEGER), (item, s) -> item.maxStack = s.intValue(), item -> Integer.valueOf(item.maxStack), (item, parent) -> item.maxStack = parent.maxStack).metadata((Metadata)new UIPropertyTitle("Max Stack Size")).documentation("The maximum amount this item can be stacked in the inventory").addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("Quality", (Codec)Codec.STRING), (item, s) -> item.qualityId = s, item -> item.qualityId).addValidator(ItemQuality.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("ItemEntity", (Codec)ItemEntityConfig.CODEC), (item, itemEntityConfig) -> item.itemEntityConfig = itemEntityConfig, item -> item.itemEntityConfig, (item, parent) -> item.itemEntityConfig = parent.itemEntityConfig).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("SoundEventId", (Codec)Codec.STRING), (item, s) -> item.soundEventId = s, item -> item.soundEventId, (item, parent) -> item.soundEventId = parent.soundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("ItemSoundSetId", (Codec)Codec.STRING), (item, s) -> item.itemSoundSetId = s, item -> item.itemSoundSetId, (item, parent) -> item.itemSoundSetId = parent.itemSoundSetId).addValidator(Validators.nonNull()).addValidator(ItemSoundSet.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("Set", (Codec)Codec.STRING), (item, s) -> item.set = s, item -> item.set, (item, parent) -> item.set = parent.set).add()).appendInherited(new KeyedCodec("Model", (Codec)Codec.STRING), (item, s) -> item.model = s, item -> item.model, (item, parent) -> item.model = parent.model).addValidator((Validator)CommonAssetValidator.MODEL_ITEM).metadata((Metadata)new UIEditorSectionStart("Rendering")).metadata((Metadata)new UIRebuildCaches(false, new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).metadata((Metadata)new UIPropertyTitle("Item Model")).documentation("The model used for rendering this item. If this is a block, BlockType.Model should be used instead.").add()).appendInherited(new KeyedCodec("Scale", (Codec)Codec.DOUBLE), (item, s) -> item.scale = s.floatValue(), item -> Double.valueOf(item.scale), (item, parent) -> item.scale = parent.scale).metadata((Metadata)new UIPropertyTitle("Item Scale")).add()).appendInherited(new KeyedCodec("Texture", (Codec)Codec.STRING), (item, s) -> item.texture = s, item -> item.texture, (item, parent) -> item.texture = parent.texture).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).metadata((Metadata)new UIPropertyTitle("Item Texture")).documentation("The texture used for rendering this item. If this is a block, block specific properties should be used instead.").add()).appendInherited(new KeyedCodec("Animation", (Codec)Codec.STRING), (item, s) -> item.animation = s, item -> item.animation, (item, parent) -> item.animation = parent.animation).addValidator((Validator)CommonAssetValidator.ANIMATION_ITEM_BLOCK).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).metadata((Metadata)new UIPropertyTitle("Item Animation")).documentation("The animation used for rendering this item. If this is a block, block specific properties should be used instead.").add()).appendInherited(new KeyedCodec("UsePlayerAnimations", (Codec)Codec.BOOLEAN), (item, s) -> item.usePlayerAnimations = s.booleanValue(), item -> Boolean.valueOf(item.usePlayerAnimations), (item, parent) -> item.usePlayerAnimations = parent.usePlayerAnimations).add()).appendInherited(new KeyedCodec("PlayerAnimationsId", ItemPlayerAnimations.CHILD_CODEC), (item, s) -> item.playerAnimationsId = s, item -> item.playerAnimationsId, (item, parent) -> item.playerAnimationsId = parent.playerAnimationsId).addValidator(Validators.nonNull()).addValidator(ItemPlayerAnimations.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("DroppedItemAnimation", (Codec)Codec.STRING), (item, animation) -> item.droppedItemAnimation = animation, item -> item.droppedItemAnimation, (item, parent) -> item.droppedItemAnimation = parent.droppedItemAnimation).addValidator((Validator)CommonAssetValidator.ANIMATION_ITEM_BLOCK).add()).appendInherited(new KeyedCodec("Particles", (Codec)ModelParticle.ARRAY_CODEC), (item, s) -> item.particles = s, item -> item.particles, (item, parent) -> item.particles = parent.particles).metadata((Metadata)new UIPropertyTitle("Item Particles")).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).documentation("The particles played for this item. If this is a block, block specific properties should be used instead.").add()).appendInherited(new KeyedCodec("FirstPersonParticles", (Codec)ModelParticle.ARRAY_CODEC), (item, s) -> item.firstPersonParticles = s, item -> item.firstPersonParticles, (item, parent) -> item.firstPersonParticles = parent.firstPersonParticles).metadata((Metadata)new UIPropertyTitle("Item First Person Particles")).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).documentation("The particles played for this item when in first person. If this is a block, block specific properties should be used instead.").add()).appendInherited(new KeyedCodec("Trails", (Codec)ModelAsset.MODEL_TRAIL_ARRAY_CODEC), (item, s) -> item.trails = s, item -> item.trails, (item, parent) -> item.trails = parent.trails).metadata((Metadata)new UIPropertyTitle("Item Trails")).documentation("The trail attached to this item").add()).appendInherited(new KeyedCodec("Light", (Codec)ProtocolCodecs.COLOR_LIGHT), (item, o) -> item.light = o, item -> item.light, (item, parent) -> item.light = parent.light).metadata((Metadata)new UIPropertyTitle("Item Light")).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).documentation("The light this item is emitting when being held or dropped. For block light, see Block properties").add()).append(new KeyedCodec("Recipe", (Codec)CraftingRecipe.CODEC), (item, s) -> item.recipeToGenerate = s, item -> item.recipeToGenerate).metadata((Metadata)new UIEditorSectionStart("Crafting")).add()).appendInherited(new KeyedCodec("ResourceTypes", (Codec)new ArrayCodec((Codec)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemResourceType.class, ItemResourceType::new).append(new KeyedCodec("Id", (Codec)Codec.STRING), (itemResourceType, s) -> itemResourceType.id = s, itemResourceType -> itemResourceType.id).addValidator(ResourceType.VALIDATOR_CACHE.getValidator()).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Quantity", (Codec)Codec.INTEGER), (itemResourceType, s) -> itemResourceType.quantity = s.intValue(), itemResourceType -> Integer.valueOf(itemResourceType.quantity)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).build(), x$0 -> new ItemResourceType[x$0])), (item, s) -> item.resourceTypes = s, item -> item.resourceTypes, (item, parent) -> item.resourceTypes = parent.resourceTypes).add()).appendInherited(new KeyedCodec("Tool", (Codec)ItemTool.CODEC), (item, s) -> item.tool = s, item -> item.tool, (item, parent) -> item.tool = parent.tool).metadata((Metadata)new UIEditorSectionStart("Functionality")).add()).appendInherited(new KeyedCodec("BlockSelectorTool", (Codec)BlockSelectorToolData.CODEC), (item, s) -> item.blockSelectorToolData = s, item -> item.blockSelectorToolData, (item, parent) -> item.blockSelectorToolData = parent.blockSelectorToolData).add()).appendInherited(new KeyedCodec("BuilderTool", (Codec)BuilderToolData.CODEC), (item, s) -> item.builderToolData = s, item -> item.builderToolData, (item, parent) -> item.builderToolData = parent.builderToolData).add()).appendInherited(new KeyedCodec("Weapon", (Codec)ItemWeapon.CODEC), (item, s) -> item.weapon = s, item -> item.weapon, (item, parent) -> item.weapon = parent.weapon).metadata((Metadata)AllowEmptyObject.INSTANCE).add()).appendInherited(new KeyedCodec("Armor", (Codec)ItemArmor.CODEC), (item, s) -> item.armor = s, item -> item.armor, (item, parent) -> item.armor = parent.armor).add()).appendInherited(new KeyedCodec("Glider", (Codec)ItemGlider.CODEC), (item, s) -> item.glider = s, item -> item.glider, (item, parent) -> item.glider = parent.glider).add()).appendInherited(new KeyedCodec("Utility", (Codec)ItemUtility.CODEC), (item, s) -> item.utility = s, item -> item.utility, (item, parent) -> item.utility = parent.utility).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("PortalKey", (Codec)PortalKey.CODEC), (item, s) -> item.portalKey = s, item -> item.portalKey, (item, parent) -> item.portalKey = parent.portalKey).add()).appendInherited(new KeyedCodec("Container", (Codec)ItemStackContainerConfig.CODEC), (item, s) -> item.itemStackContainerConfig = s, item -> item.itemStackContainerConfig, (item, parent) -> item.itemStackContainerConfig = parent.itemStackContainerConfig).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Consumable", (Codec)Codec.BOOLEAN), (item, s) -> item.consumable = s.booleanValue(), item -> Boolean.valueOf(item.consumable), (item, parent) -> item.consumable = parent.consumable).add()).appendInherited(new KeyedCodec("Variant", (Codec)Codec.BOOLEAN), (item, b) -> item.variant = b.booleanValue(), item -> Boolean.valueOf(item.variant), (item, parent) -> item.variant = parent.variant).documentation("Whether this item is a variant of another. Typically this is only the case for connected blocks. If this item is marked as a variant, then we filter it out of the item library menu by default, unless the player chooses to display variants.").add()).appendInherited(new KeyedCodec("MaxDurability", (Codec)Codec.DOUBLE), (item, s) -> item.maxDurability = s.doubleValue(), item -> Double.valueOf(item.maxDurability), (item, parent) -> item.maxDurability = parent.maxDurability).add()).appendInherited(new KeyedCodec("FuelQuality", (Codec)Codec.DOUBLE), (item, s) -> item.fuelQuality = s.doubleValue(), item -> Double.valueOf(item.fuelQuality), (item, parent) -> item.fuelQuality = parent.fuelQuality).add()).appendInherited(new KeyedCodec("DurabilityLossOnHit", (Codec)Codec.DOUBLE), (item, s) -> item.durabilityLossOnHit = s.doubleValue(), item -> Double.valueOf(item.durabilityLossOnHit), (item, parent) -> item.durabilityLossOnHit = parent.durabilityLossOnHit).add()).appendInherited(new KeyedCodec("BlockType", (Codec)new ContainedAssetCodec(BlockType.class, (AssetCodec)BlockType.CODEC, ContainedAssetCodec.Mode.INHERIT_ID_AND_PARENT)), (item, s) -> item.hasBlockType = true, item -> item.blockId, (item, parent) -> item.blockId = parent.blockId).metadata((Metadata)new UIEditorSectionStart("Block")).metadata((Metadata)new UIRebuildCaches(false, new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS, UIRebuildCaches.ClientCache.BLOCK_TEXTURES, UIRebuildCaches.ClientCache.MODEL_TEXTURES })).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("Interactions", (Codec)new EnumMapCodec(InteractionType.class, (Codec)RootInteraction.CHILD_ASSET_CODEC)), (item, v) -> item.interactions = MapUtil.combineUnmodifiable(item.interactions, v, ()), item -> item.interactions, (item, parent) -> item.interactions = parent.interactions).addValidator((Validator)RootInteraction.VALIDATOR_CACHE.getMapValueValidator()).metadata((Metadata)new UIEditorSectionStart("Interactions")).add()).appendInherited(new KeyedCodec("InteractionConfig", (Codec)InteractionConfiguration.CODEC), (item, v) -> item.interactionConfig = v, item -> item.interactionConfig, (item, parent) -> item.interactionConfig = parent.interactionConfig).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("InteractionVars", (Codec)new MapCodec((Codec)RootInteraction.CHILD_ASSET_CODEC, HashMap::new)), (item, v) -> item.interactionVars = MapUtil.combineUnmodifiable(item.interactionVars, v), item -> item.interactionVars, (item, parent) -> item.interactionVars = parent.interactionVars).addValidator((Validator)RootInteraction.VALIDATOR_CACHE.getMapValueValidator()).add()).appendInherited(new KeyedCodec("ItemAppearanceConditions", (Codec)new MapCodec((Codec)new ArrayCodec((Codec)ItemAppearanceCondition.CODEC, x$0 -> new ItemAppearanceCondition[x$0]), HashMap::new)), (item, stringMap) -> item.itemAppearanceConditions = stringMap, item -> item.itemAppearanceConditions, (item, parent) -> item.itemAppearanceConditions = parent.itemAppearanceConditions).documentation("Define per EntityStat an array of ItemAppearanceCondition. Only a single condition will be applied to the item at the same time.").addValidator((Validator)EntityStatType.VALIDATOR_CACHE.getMapKeyValidator().late()).add()).appendInherited(new KeyedCodec("DisplayEntityStatsHUD", (Codec)Codec.STRING_ARRAY), (item, strings) -> item.rawDisplayEntityStatsHUD = strings, item -> item.rawDisplayEntityStatsHUD, (item, parent) -> item.rawDisplayEntityStatsHUD = parent.rawDisplayEntityStatsHUD).documentation("Used to indicate to the client whether an EntityStat HUD UI needs to be displayed").add()).appendInherited(new KeyedCodec("PullbackConfig", (Codec)ItemPullbackConfig.CODEC), (item, s) -> item.pullbackConfig = s, item -> item.pullbackConfig, (item, parent) -> item.pullbackConfig = parent.pullbackConfig).documentation("Overrides the offset of first person arms when close to obstacles").add()).appendInherited(new KeyedCodec("ClipsGeometry", (Codec)Codec.BOOLEAN), (item, s) -> item.clipsGeometry = s.booleanValue(), item -> Boolean.valueOf(item.clipsGeometry), (item, parent) -> item.clipsGeometry = parent.clipsGeometry).add()).appendInherited(new KeyedCodec("RenderDeployablePreview", (Codec)Codec.BOOLEAN), (item, s) -> item.renderDeployablePreview = s.booleanValue(), item -> Boolean.valueOf(item.renderDeployablePreview), (item, parent) -> item.renderDeployablePreview = parent.renderDeployablePreview).add()).appendInherited(new KeyedCodec("DropOnDeath", (Codec)Codec.BOOLEAN), (item, aBoolean) -> item.dropOnDeath = aBoolean.booleanValue(), item -> Boolean.valueOf(item.dropOnDeath), (item, parent) -> item.dropOnDeath = parent.dropOnDeath).add()).afterDecode(Item::processConfig);
/*  469 */   } public static final AssetCodec<String, Item> CODEC = (AssetCodec<String, Item>)CODEC_BUILDER.build();
/*      */ 
/*      */   
/*      */   static {
/*  473 */     CODEC_BUILDER.appendInherited(new KeyedCodec("State", (Codec)new MapCodec((Codec)new ContainedAssetCodec(Item.class, CODEC, ContainedAssetCodec.Mode.INJECT_PARENT), HashMap::new)), (item, m) -> item.stateToBlock = m, item -> item.stateToBlock, (item, parent) -> item.stateToBlock = parent.stateToBlock)
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  479 */       .metadata((Metadata)new UIEditorSectionStart("State"))
/*  480 */       .add();
/*      */   }
/*      */   
/*  483 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(Item::getAssetStore));
/*      */   private static AssetStore<String, Item, DefaultAssetMap<String, Item>> ASSET_STORE;
/*      */   public static final String UNKNOWN_TEXTURE = "Items/Unknown.png";
/*      */   
/*      */   public static AssetStore<String, Item, DefaultAssetMap<String, Item>> getAssetStore() {
/*  488 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(Item.class); 
/*  489 */     return ASSET_STORE;
/*      */   }
/*      */   
/*      */   public static DefaultAssetMap<String, Item> getAssetMap() {
/*  493 */     return (DefaultAssetMap<String, Item>)getAssetStore().getAssetMap();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  498 */   public static final Item UNKNOWN = new Item("Unknown")
/*      */     {
/*      */     
/*      */     };
/*      */ 
/*      */   
/*      */   protected AssetExtraInfo.Data data;
/*      */   
/*      */   protected String id;
/*      */   
/*      */   protected String icon;
/*      */   
/*      */   protected AssetIconProperties iconProperties;
/*      */   
/*      */   protected ItemTranslationProperties translationProperties;
/*      */   
/*      */   protected String reticleId;
/*      */   
/*  516 */   protected int reticleIndex = 0;
/*      */   
/*      */   protected int itemLevel;
/*  519 */   protected int maxStack = -1;
/*      */   protected String qualityId;
/*  521 */   protected int qualityIndex = 0;
/*      */   
/*      */   protected CraftingRecipe recipeToGenerate;
/*      */   protected String blockId;
/*      */   protected boolean hasBlockType;
/*      */   protected boolean consumable;
/*      */   protected boolean variant;
/*      */   protected ItemTool tool;
/*      */   protected BlockSelectorToolData blockSelectorToolData;
/*      */   protected BuilderToolData builderToolData;
/*      */   protected ItemWeapon weapon;
/*      */   protected ItemArmor armor;
/*      */   protected ItemGlider glider;
/*  534 */   protected ItemUtility utility = ItemUtility.DEFAULT;
/*  535 */   protected ItemStackContainerConfig itemStackContainerConfig = ItemStackContainerConfig.DEFAULT;
/*      */   
/*      */   protected PortalKey portalKey;
/*  538 */   protected String playerAnimationsId = "Default";
/*      */   protected boolean usePlayerAnimations = false;
/*      */   protected String model;
/*  541 */   protected float scale = 1.0F;
/*  542 */   protected String texture = "Items/Unknown.png";
/*      */   protected String animation;
/*      */   protected String[] categories;
/*      */   protected String set;
/*      */   protected String soundEventId;
/*      */   protected transient int soundEventIndex;
/*  548 */   protected String itemSoundSetId = "ISS_Default";
/*      */   
/*      */   protected transient int itemSoundSetIndex;
/*      */   
/*      */   protected ModelParticle[] particles;
/*      */   protected ModelParticle[] firstPersonParticles;
/*      */   protected ModelTrail[] trails;
/*      */   protected ColorLight light;
/*      */   protected ItemResourceType[] resourceTypes;
/*      */   protected Map<String, String> stateToBlock;
/*      */   protected Map<String, String> blockToState;
/*  559 */   protected Map<InteractionType, String> interactions = Collections.emptyMap();
/*  560 */   protected Map<String, String> interactionVars = Collections.emptyMap();
/*      */   
/*      */   protected InteractionConfiguration interactionConfig;
/*      */   protected ItemEntityConfig itemEntityConfig;
/*      */   protected String droppedItemAnimation;
/*      */   protected double maxDurability;
/*  566 */   protected double fuelQuality = 1.0D;
/*      */   
/*      */   protected double durabilityLossOnHit;
/*      */   
/*      */   protected Map<String, ItemAppearanceCondition[]> itemAppearanceConditions;
/*      */   
/*      */   protected String[] rawDisplayEntityStatsHUD;
/*      */   
/*      */   @Nullable
/*      */   protected int[] displayEntityStatsHUD;
/*      */   protected ItemPullbackConfig pullbackConfig;
/*      */   protected boolean clipsGeometry;
/*      */   protected boolean renderDeployablePreview;
/*      */   protected boolean dropOnDeath;
/*      */   private transient SoftReference<ItemBase> cachedPacket;
/*      */   
/*      */   public Item(String id) {
/*  583 */     this.id = id;
/*      */   }
/*      */   
/*      */   public Item(@Nonnull Item other) {
/*  587 */     this.data = other.data;
/*  588 */     this.id = other.id;
/*  589 */     this.icon = other.icon;
/*  590 */     this.iconProperties = other.iconProperties;
/*  591 */     this.translationProperties = other.translationProperties;
/*  592 */     this.reticleId = other.reticleId;
/*  593 */     this.itemLevel = other.itemLevel;
/*  594 */     this.maxStack = other.maxStack;
/*  595 */     this.qualityId = other.qualityId;
/*  596 */     this.recipeToGenerate = other.recipeToGenerate;
/*  597 */     this.consumable = other.consumable;
/*  598 */     this.variant = other.variant;
/*  599 */     this.playerAnimationsId = other.playerAnimationsId;
/*  600 */     this.usePlayerAnimations = other.usePlayerAnimations;
/*  601 */     this.model = other.model;
/*  602 */     this.scale = other.scale;
/*  603 */     this.texture = other.texture;
/*  604 */     this.animation = other.animation;
/*  605 */     this.tool = other.tool;
/*  606 */     this.blockSelectorToolData = other.blockSelectorToolData;
/*  607 */     this.builderToolData = other.builderToolData;
/*  608 */     this.weapon = other.weapon;
/*  609 */     this.armor = other.armor;
/*  610 */     this.utility = other.utility;
/*  611 */     this.portalKey = other.portalKey;
/*  612 */     this.categories = other.categories;
/*  613 */     this.set = other.set;
/*  614 */     this.soundEventId = other.soundEventId;
/*  615 */     this.soundEventIndex = other.soundEventIndex;
/*  616 */     this.itemSoundSetId = other.itemSoundSetId;
/*  617 */     this.itemSoundSetIndex = other.itemSoundSetIndex;
/*  618 */     this.particles = other.particles;
/*  619 */     this.firstPersonParticles = other.firstPersonParticles;
/*  620 */     this.trails = other.trails;
/*  621 */     this.light = other.light;
/*  622 */     this.resourceTypes = other.resourceTypes;
/*  623 */     this.interactions = other.interactions;
/*  624 */     this.interactionVars = other.interactionVars;
/*  625 */     this.interactionConfig = other.interactionConfig;
/*  626 */     this.droppedItemAnimation = other.droppedItemAnimation;
/*  627 */     this.itemEntityConfig = other.itemEntityConfig;
/*  628 */     this.stateToBlock = other.stateToBlock;
/*  629 */     this.blockId = other.blockId;
/*  630 */     this.hasBlockType = other.hasBlockType;
/*  631 */     this.displayEntityStatsHUD = other.displayEntityStatsHUD;
/*  632 */     this.pullbackConfig = other.pullbackConfig;
/*  633 */     this.clipsGeometry = other.clipsGeometry;
/*  634 */     this.renderDeployablePreview = other.renderDeployablePreview;
/*  635 */     this.dropOnDeath = other.dropOnDeath;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ItemBase toPacket() {
/*  642 */     ItemBase cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/*  643 */     if (cached != null) return cached;
/*      */     
/*  645 */     ItemBase packet = new ItemBase();
/*  646 */     packet.id = this.id;
/*      */     
/*  648 */     if (this.icon != null) packet.icon = this.icon; 
/*  649 */     if (this.iconProperties != null) packet.iconProperties = this.iconProperties.toPacket(); 
/*  650 */     if (this.translationProperties != null) packet.translationProperties = this.translationProperties.toPacket(); 
/*  651 */     if (this.model != null) packet.model = this.model; 
/*  652 */     packet.scale = this.scale;
/*  653 */     if (this.texture != null) packet.texture = this.texture; 
/*  654 */     if (this.animation != null) packet.animation = this.animation; 
/*  655 */     packet.playerAnimationsId = this.playerAnimationsId;
/*  656 */     packet.usePlayerAnimations = this.usePlayerAnimations;
/*      */     
/*  658 */     packet.reticleIndex = this.reticleIndex;
/*      */     
/*  660 */     packet.maxStack = this.maxStack;
/*  661 */     packet.itemLevel = this.itemLevel;
/*      */     
/*  663 */     packet.qualityIndex = this.qualityIndex;
/*      */     
/*  665 */     if (this.blockId != null) {
/*  666 */       packet.blockId = BlockType.getAssetMap().getIndexOrDefault(this.blockId, 1);
/*  667 */       if (packet.blockId == 0) throw new IllegalArgumentException("Block Id Can't be 0"); 
/*      */     } 
/*  669 */     packet.consumable = this.consumable;
/*  670 */     packet.variant = this.variant;
/*  671 */     if (this.tool != null) packet.tool = this.tool.toPacket(); 
/*  672 */     if (this.blockSelectorToolData != null) packet.blockSelectorTool = this.blockSelectorToolData.toPacket(); 
/*  673 */     if (this.builderToolData != null) packet.builderToolData = this.builderToolData.toPacket(); 
/*  674 */     if (this.weapon != null) packet.weapon = this.weapon.toPacket(); 
/*  675 */     if (this.armor != null) packet.armor = this.armor.toPacket(); 
/*  676 */     if (this.glider != null) packet.gliderConfig = this.glider.toPacket();
/*      */     
/*  678 */     if (this.utility != null) packet.utility = this.utility.toPacket();
/*      */     
/*  680 */     if (this.categories != null && this.categories.length > 0) packet.categories = this.categories; 
/*  681 */     if (this.set != null) packet.set = this.set; 
/*  682 */     packet.soundEventIndex = this.soundEventIndex;
/*  683 */     packet.itemSoundSetIndex = this.itemSoundSetIndex;
/*  684 */     if (this.particles != null && this.particles.length > 0) {
/*  685 */       packet.particles = new com.hypixel.hytale.protocol.ModelParticle[this.particles.length];
/*      */       
/*  687 */       for (int i = 0; i < this.particles.length; i++) {
/*  688 */         packet.particles[i] = this.particles[i].toPacket();
/*      */       }
/*      */     } 
/*      */     
/*  692 */     if (this.firstPersonParticles != null && this.firstPersonParticles.length > 0) {
/*  693 */       packet.firstPersonParticles = new com.hypixel.hytale.protocol.ModelParticle[this.firstPersonParticles.length];
/*      */       
/*  695 */       for (int i = 0; i < this.firstPersonParticles.length; i++) {
/*  696 */         packet.firstPersonParticles[i] = this.firstPersonParticles[i].toPacket();
/*      */       }
/*      */     } 
/*  699 */     if (this.trails != null && this.trails.length > 0) packet.trails = this.trails; 
/*  700 */     if (this.light != null) packet.light = this.light; 
/*  701 */     if (this.resourceTypes != null && this.resourceTypes.length > 0) packet.resourceTypes = this.resourceTypes;
/*      */     
/*  703 */     Object2IntOpenHashMap<InteractionType> interactionsIntMap = new Object2IntOpenHashMap();
/*  704 */     for (Map.Entry<InteractionType, String> e : this.interactions.entrySet()) {
/*  705 */       interactionsIntMap.put(e.getKey(), RootInteraction.getRootInteractionIdOrUnknown(e.getValue()));
/*      */     }
/*  707 */     packet.interactions = (Map)interactionsIntMap;
/*      */     
/*  709 */     Object2IntOpenHashMap<String> interactionVarsIntMap = new Object2IntOpenHashMap();
/*  710 */     for (Map.Entry<String, String> e : this.interactionVars.entrySet()) {
/*  711 */       interactionVarsIntMap.put(e.getKey(), RootInteraction.getRootInteractionIdOrUnknown(e.getValue()));
/*      */     }
/*  713 */     packet.interactionVars = (Map)interactionVarsIntMap;
/*      */     
/*  715 */     packet.interactionConfig = this.interactionConfig.toPacket();
/*  716 */     packet.durability = getMaxDurability();
/*      */     
/*  718 */     packet.itemEntity = this.itemEntityConfig.toPacket();
/*      */     
/*  720 */     if (this.droppedItemAnimation != null) packet.droppedItemAnimation = this.droppedItemAnimation;
/*      */     
/*  722 */     if (this.itemAppearanceConditions != null) {
/*  723 */       HashMap<Integer, ItemAppearanceCondition[]> map = (HashMap)new HashMap<>();
/*  724 */       for (Map.Entry<String, ItemAppearanceCondition[]> entry : this.itemAppearanceConditions.entrySet()) {
/*  725 */         ItemAppearanceCondition[] conditions = entry.getValue();
/*  726 */         ItemAppearanceCondition[] protocolConditions = new ItemAppearanceCondition[conditions.length];
/*      */         
/*  728 */         for (int i = 0; i < conditions.length; i++) {
/*  729 */           protocolConditions[i] = conditions[i].toPacket();
/*      */         }
/*      */         
/*  732 */         map.put(Integer.valueOf(EntityStatType.getAssetMap().getIndex(entry.getKey())), protocolConditions);
/*      */       } 
/*      */       
/*  735 */       packet.itemAppearanceConditions = map;
/*      */     } 
/*      */     
/*  738 */     if (this.data != null) {
/*  739 */       IntSet expandedTagIndexes = this.data.getExpandedTagIndexes();
/*  740 */       if (expandedTagIndexes != null) packet.tagIndexes = expandedTagIndexes.toIntArray();
/*      */     
/*      */     } 
/*  743 */     packet.displayEntityStatsHUD = this.displayEntityStatsHUD;
/*      */     
/*  745 */     if (this.pullbackConfig != null) packet.pullbackConfig = this.pullbackConfig.toPacket(); 
/*  746 */     packet.clipsGeometry = this.clipsGeometry;
/*  747 */     packet.renderDeployablePreview = this.renderDeployablePreview;
/*      */     
/*  749 */     this.cachedPacket = new SoftReference<>(packet);
/*  750 */     return packet;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getItemIdForState(String state) {
/*  755 */     return (this.stateToBlock != null) ? this.stateToBlock.get(state) : null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Item getItemForState(String state) {
/*  760 */     String id = getItemIdForState(state);
/*  761 */     if (id == null) return null; 
/*  762 */     return (Item)getAssetMap().getAsset(id);
/*      */   }
/*      */   
/*      */   public boolean isState() {
/*  766 */     return (getStateForItem(this.id) != null);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getStateForItem(@Nonnull Item item) {
/*  771 */     return getStateForItem(item.getId());
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getStateForItem(String key) {
/*  776 */     return (this.blockToState != null) ? this.blockToState.get(key) : null;
/*      */   }
/*      */   
/*      */   public AssetExtraInfo.Data getData() {
/*  780 */     return this.data;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getId() {
/*  785 */     return this.id;
/*      */   }
/*      */   
/*      */   public String getBlockId() {
/*  789 */     return this.blockId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String getTranslationKey() {
/*  799 */     if (this.translationProperties != null) {
/*  800 */       String nameTranslation = this.translationProperties.getName();
/*  801 */       if (nameTranslation != null) {
/*  802 */         return nameTranslation;
/*      */       }
/*      */     } 
/*      */     
/*  806 */     return "server.items." + this.id + ".name";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String getDescriptionTranslationKey() {
/*  816 */     if (this.translationProperties != null) {
/*  817 */       String descriptionTranslation = this.translationProperties.getDescription();
/*  818 */       if (descriptionTranslation != null) {
/*  819 */         return descriptionTranslation;
/*      */       }
/*      */     } 
/*      */     
/*  823 */     return "server.items." + this.id + ".description";
/*      */   }
/*      */   
/*      */   public String getModel() {
/*  827 */     return this.model;
/*      */   }
/*      */   
/*      */   public String getTexture() {
/*  831 */     return this.texture;
/*      */   }
/*      */   
/*      */   public boolean isConsumable() {
/*  835 */     return this.consumable;
/*      */   }
/*      */   
/*      */   public boolean isVariant() {
/*  839 */     return this.variant;
/*      */   }
/*      */   
/*      */   public boolean getUsePlayerAnimations() {
/*  843 */     return this.usePlayerAnimations;
/*      */   }
/*      */   
/*      */   public String getPlayerAnimationsId() {
/*  847 */     return this.playerAnimationsId;
/*      */   }
/*      */   
/*      */   public String getIcon() {
/*  851 */     return this.icon;
/*      */   }
/*      */   
/*      */   public AssetIconProperties getIconProperties() {
/*  855 */     return this.iconProperties;
/*      */   }
/*      */   
/*      */   public ItemTranslationProperties getTranslationProperties() {
/*  859 */     return this.translationProperties;
/*      */   }
/*      */   
/*      */   public float getScale() {
/*  863 */     return this.scale;
/*      */   }
/*      */   
/*      */   public String getReticleId() {
/*  867 */     return this.reticleId;
/*      */   }
/*      */   
/*      */   public int getItemLevel() {
/*  871 */     return this.itemLevel;
/*      */   }
/*      */   
/*      */   public int getMaxStack() {
/*  875 */     return this.maxStack;
/*      */   }
/*      */   
/*      */   public int getQualityIndex() {
/*  879 */     return this.qualityIndex;
/*      */   }
/*      */   
/*      */   public ItemTool getTool() {
/*  883 */     return this.tool;
/*      */   }
/*      */   
/*      */   public BlockSelectorToolData getBlockSelectorToolData() {
/*  887 */     return this.blockSelectorToolData;
/*      */   }
/*      */   
/*      */   public BuilderToolData getBuilderToolData() {
/*  891 */     return this.builderToolData;
/*      */   }
/*      */   
/*      */   public ItemArmor getArmor() {
/*  895 */     return this.armor;
/*      */   }
/*      */   
/*      */   public ItemGlider getGlider() {
/*  899 */     return this.glider;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemUtility getUtility() {
/*  904 */     return this.utility;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public PortalKey getPortalKey() {
/*  909 */     return this.portalKey;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackContainerConfig getItemStackContainerConfig() {
/*  914 */     return this.itemStackContainerConfig;
/*      */   }
/*      */   
/*      */   public String[] getCategories() {
/*  918 */     return this.categories;
/*      */   }
/*      */   
/*      */   public String getSoundEventId() {
/*  922 */     return this.soundEventId;
/*      */   }
/*      */   
/*      */   public int getSoundEventIndex() {
/*  926 */     return this.soundEventIndex;
/*      */   }
/*      */   
/*      */   public boolean hasBlockType() {
/*  930 */     return (this.blockId != null);
/*      */   }
/*      */   
/*      */   public ItemWeapon getWeapon() {
/*  934 */     return this.weapon;
/*      */   }
/*      */   
/*      */   public ItemResourceType[] getResourceTypes() {
/*  938 */     return this.resourceTypes;
/*      */   }
/*      */   
/*      */   public double getMaxDurability() {
/*  942 */     return this.maxDurability;
/*      */   }
/*      */   
/*      */   public ColorLight getLight() {
/*  946 */     return this.light;
/*      */   }
/*      */   
/*      */   public Map<InteractionType, String> getInteractions() {
/*  950 */     return this.interactions;
/*      */   }
/*      */   
/*      */   public Map<String, String> getInteractionVars() {
/*  954 */     return this.interactionVars;
/*      */   }
/*      */   
/*      */   public ItemEntityConfig getItemEntityConfig() {
/*  958 */     return this.itemEntityConfig;
/*      */   }
/*      */   
/*      */   public String getDroppedItemAnimation() {
/*  962 */     return this.droppedItemAnimation;
/*      */   }
/*      */   
/*      */   public double getDurabilityLossOnHit() {
/*  966 */     return this.durabilityLossOnHit;
/*      */   }
/*      */   
/*      */   public int[] getDisplayEntityStatsHUD() {
/*  970 */     return this.displayEntityStatsHUD;
/*      */   }
/*      */   
/*      */   public ItemPullbackConfig getPullbackConfig() {
/*  974 */     return this.pullbackConfig;
/*      */   }
/*      */   
/*      */   public boolean getClipsGeometry() {
/*  978 */     return this.clipsGeometry;
/*      */   }
/*      */   
/*      */   public boolean getRenderDeployablePreview() {
/*  982 */     return this.renderDeployablePreview;
/*      */   }
/*      */   
/*      */   public double getFuelQuality() {
/*  986 */     return this.fuelQuality;
/*      */   }
/*      */   
/*      */   public InteractionConfiguration getInteractionConfig() {
/*  990 */     return this.interactionConfig;
/*      */   }
/*      */   
/*      */   public int getItemSoundSetIndex() {
/*  994 */     return this.itemSoundSetIndex;
/*      */   }
/*      */   
/*      */   public void collectRecipesToGenerate(Collection<CraftingRecipe> recipes) {
/*  998 */     if (this.recipeToGenerate != null) {
/*  999 */       recipes.add(this.recipeToGenerate);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hasRecipesToGenerate() {
/* 1004 */     return (this.recipeToGenerate != null);
/*      */   }
/*      */   
/*      */   public boolean dropsOnDeath() {
/* 1008 */     return this.dropOnDeath;
/*      */   }
/*      */   
/*      */   protected void processConfig() {
/* 1012 */     if (this.hasBlockType) this.blockId = this.id;
/*      */     
/* 1014 */     if (this.maxStack == -1) {
/* 1015 */       if (this.tool != null || this.weapon != null || this.armor != null || this.builderToolData != null || this.blockSelectorToolData != null) {
/* 1016 */         this.maxStack = 1;
/*      */       } else {
/* 1018 */         this.maxStack = 100;
/*      */       } 
/*      */     }
/*      */     
/* 1022 */     Map<InteractionType, String> interactions = this.interactions.isEmpty() ? new EnumMap<>(InteractionType.class) : new EnumMap<>(this.interactions);
/*      */ 
/*      */     
/* 1025 */     DefaultAssetMap<String, UnarmedInteractions> unarmedInteractionsAssetMap = UnarmedInteractions.getAssetMap();
/*      */     
/* 1027 */     UnarmedInteractions fallbackInteractions = (this.playerAnimationsId != null) ? (UnarmedInteractions)unarmedInteractionsAssetMap.getAsset(this.playerAnimationsId) : null;
/* 1028 */     if (fallbackInteractions != null)
/*      */     {
/* 1030 */       for (Map.Entry<InteractionType, String> entry : (Iterable<Map.Entry<InteractionType, String>>)fallbackInteractions.getInteractions().entrySet()) {
/* 1031 */         interactions.putIfAbsent(entry.getKey(), entry.getValue());
/*      */       }
/*      */     }
/*      */     
/* 1035 */     UnarmedInteractions defaultUnarmedInteractions = (UnarmedInteractions)unarmedInteractionsAssetMap.getAsset("Empty");
/* 1036 */     if (defaultUnarmedInteractions != null)
/*      */     {
/* 1038 */       for (Map.Entry<InteractionType, String> entry : (Iterable<Map.Entry<InteractionType, String>>)defaultUnarmedInteractions.getInteractions().entrySet()) {
/* 1039 */         interactions.putIfAbsent(entry.getKey(), entry.getValue());
/*      */       }
/*      */     }
/*      */     
/* 1043 */     this.interactions = Collections.unmodifiableMap(interactions);
/*      */     
/* 1045 */     if (this.reticleId != null) {
/* 1046 */       this.reticleIndex = ItemReticleConfig.getAssetMap().getIndexOrDefault(this.reticleId, 0);
/*      */     }
/*      */     
/* 1049 */     IndexedLookupTableAssetMap<String, ItemQuality> itemQualityAssetMap = ItemQuality.getAssetMap();
/* 1050 */     if (this.qualityId != null) {
/* 1051 */       this.qualityIndex = itemQualityAssetMap.getIndexOrDefault(this.qualityId, 0);
/*      */       
/* 1053 */       ItemQuality itemQuality = (ItemQuality)itemQualityAssetMap.getAsset(this.qualityIndex);
/* 1054 */       if (this.itemEntityConfig == null && itemQuality != null) {
/* 1055 */         this.itemEntityConfig = itemQuality.getItemEntityConfig();
/*      */       }
/*      */     } 
/*      */     
/* 1059 */     if (this.itemEntityConfig == null) {
/* 1060 */       if (this.blockId != null) {
/* 1061 */         this.itemEntityConfig = ItemEntityConfig.DEFAULT_BLOCK;
/*      */       } else {
/* 1063 */         this.itemEntityConfig = ItemEntityConfig.DEFAULT;
/*      */       } 
/*      */     }
/*      */     
/* 1067 */     if (this.interactionConfig == null) {
/* 1068 */       if (this.weapon != null) {
/* 1069 */         this.interactionConfig = InteractionConfiguration.DEFAULT_WEAPON;
/*      */       } else {
/* 1071 */         this.interactionConfig = InteractionConfiguration.DEFAULT;
/*      */       } 
/*      */     }
/*      */     
/* 1075 */     if (this.soundEventId != null) {
/* 1076 */       this.soundEventIndex = SoundEvent.getAssetMap().getIndex(this.soundEventId);
/*      */     }
/*      */     
/* 1079 */     this.itemSoundSetIndex = ItemSoundSet.getAssetMap().getIndex(this.itemSoundSetId);
/*      */     
/* 1081 */     if (this.stateToBlock != null) {
/* 1082 */       Object2ObjectOpenHashMap<String, String> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 1083 */       for (Map.Entry<String, String> entry : this.stateToBlock.entrySet()) {
/* 1084 */         object2ObjectOpenHashMap.put(entry.getValue(), entry.getKey());
/*      */       }
/* 1086 */       this.blockToState = Collections.unmodifiableMap((Map<? extends String, ? extends String>)object2ObjectOpenHashMap);
/*      */     } 
/*      */     
/* 1089 */     if (this.recipeToGenerate != null) {
/* 1090 */       CraftingRecipe recipe = this.recipeToGenerate;
/* 1091 */       CraftingRecipe newRecipe = new CraftingRecipe(recipe);
/*      */       
/* 1093 */       MaterialQuantity primaryOutput = new MaterialQuantity(this.id, null, null, newRecipe.primaryOutputQuantity, null);
/*      */       
/* 1095 */       if (newRecipe.outputs == null || newRecipe.outputs.length == 0) {
/* 1096 */         newRecipe.outputs = new MaterialQuantity[] { primaryOutput };
/*      */       }
/*      */       
/* 1099 */       newRecipe.primaryOutput = primaryOutput;
/* 1100 */       newRecipe.id = CraftingRecipe.generateIdFromItemRecipe(this, 0);
/* 1101 */       this.recipeToGenerate = newRecipe;
/*      */     } 
/*      */     
/* 1104 */     this.displayEntityStatsHUD = EntityStatsModule.resolveEntityStats(this.rawDisplayEntityStatsHUD);
/*      */   }
/*      */   
/*      */   protected Item() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\Item.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */