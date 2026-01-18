/*      */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*      */ 
/*      */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*      */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*      */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*      */ import com.hypixel.hytale.assetstore.AssetStore;
/*      */ import com.hypixel.hytale.assetstore.AssetUpdateQuery;
/*      */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*      */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*      */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*      */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*      */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*      */ import com.hypixel.hytale.codec.codecs.map.MergedEnumMapCodec;
/*      */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIPropertyTitle;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIRebuildCaches;
/*      */ import com.hypixel.hytale.codec.store.StoredCodec;
/*      */ import com.hypixel.hytale.codec.validation.Validator;
/*      */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*      */ import com.hypixel.hytale.codec.validation.Validators;
/*      */ import com.hypixel.hytale.common.util.ArrayUtil;
/*      */ import com.hypixel.hytale.common.util.MapUtil;
/*      */ import com.hypixel.hytale.component.Holder;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.math.shape.Box;
/*      */ import com.hypixel.hytale.math.vector.Vector3d;
/*      */ import com.hypixel.hytale.math.vector.Vector3f;
/*      */ import com.hypixel.hytale.protocol.BenchType;
/*      */ import com.hypixel.hytale.protocol.BlockFaceSupport;
/*      */ import com.hypixel.hytale.protocol.BlockFlags;
/*      */ import com.hypixel.hytale.protocol.BlockMaterial;
/*      */ import com.hypixel.hytale.protocol.BlockNeighbor;
/*      */ import com.hypixel.hytale.protocol.BlockSupportsRequiredForType;
/*      */ import com.hypixel.hytale.protocol.BlockTextures;
/*      */ import com.hypixel.hytale.protocol.BlockType;
/*      */ import com.hypixel.hytale.protocol.Color;
/*      */ import com.hypixel.hytale.protocol.ColorLight;
/*      */ import com.hypixel.hytale.protocol.DrawType;
/*      */ import com.hypixel.hytale.protocol.InteractionType;
/*      */ import com.hypixel.hytale.protocol.ModelTexture;
/*      */ import com.hypixel.hytale.protocol.Opacity;
/*      */ import com.hypixel.hytale.protocol.RailConfig;
/*      */ import com.hypixel.hytale.protocol.RailPoint;
/*      */ import com.hypixel.hytale.protocol.RandomRotation;
/*      */ import com.hypixel.hytale.protocol.RequiredBlockFaceSupport;
/*      */ import com.hypixel.hytale.protocol.ShaderType;
/*      */ import com.hypixel.hytale.protocol.ShadingMode;
/*      */ import com.hypixel.hytale.protocol.Tint;
/*      */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*      */ import com.hypixel.hytale.server.core.asset.type.blockbreakingdecal.config.BlockBreakingDecal;
/*      */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*      */ import com.hypixel.hytale.server.core.asset.type.blockparticle.config.BlockParticleSet;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocksound.config.BlockSoundSet;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktick.config.TickProcedure;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.Bench;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingData;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.mountpoints.RotatedMountPointsArray;
/*      */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BlockTypeListAsset;
/*      */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.PrefabListAsset;
/*      */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*      */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*      */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*      */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*      */ import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
/*      */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*      */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.InteractionTypeUtils;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.section.palette.ISectionPalette;
/*      */ import com.hypixel.hytale.server.core.universe.world.connectedblocks.ConnectedBlockRuleSet;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*      */ import com.hypixel.hytale.server.core.util.io.ByteBufUtil;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import it.unimi.dsi.fastutil.ints.IntSet;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.EnumMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.function.ToIntFunction;
/*      */ import java.util.logging.Level;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BlockType
/*      */   implements JsonAssetWithMap<String, BlockTypeAssetMap<String, BlockType>>, NetworkSerializable<BlockType>
/*      */ {
/*      */   public static final AssetBuilderCodec<String, BlockType> CODEC;
/*      */   
/*      */   static {
/*  702 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockType.class, BlockType::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("The definition for a block in the game. Can only be defined within an **Item** and not standalone.")).appendInherited(new KeyedCodec("Group", (Codec)Codec.STRING), (blockType, o) -> blockType.group = o, blockType -> blockType.group, (blockType, parent) -> blockType.group = parent.group).documentation("Sets the group for this block. Used by **BlockSets**.\n\nA group of _\"@Tech\"_ will prevent physics from being automatically applied to the block.").metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.TextField("BlockGroups"))).add()).appendInherited(new KeyedCodec("BlockListAssetId", (Codec)Codec.STRING), (blockType, blockListAssetId) -> blockType.blockListAssetId = blockListAssetId, blockType -> blockType.blockListAssetId, (blockType, parent) -> blockType.blockListAssetId = parent.blockListAssetId).addValidator(BlockTypeListAsset.VALIDATOR_CACHE.getValidator()).documentation("The name of a BlockList asset, for use  in builder tool brushes").add()).appendInherited(new KeyedCodec("PrefabListAssetId", (Codec)Codec.STRING), (blockType, prefabListAssetId) -> blockType.prefabListAssetId = prefabListAssetId, blockType -> blockType.prefabListAssetId, (blockType, parent) -> blockType.prefabListAssetId = parent.prefabListAssetId).addValidator(PrefabListAsset.VALIDATOR_CACHE.getValidator()).documentation("The name of a PrefabList asset, for use  in builder tool brushes").add()).appendInherited(new KeyedCodec("DrawType", (Codec)new EnumCodec(DrawType.class)), (blockType, o) -> blockType.drawType = o, blockType -> blockType.drawType, (blockType, parent) -> blockType.drawType = parent.drawType).addValidator(Validators.nonNull()).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS, UIRebuildCaches.ClientCache.BLOCK_TEXTURES, UIRebuildCaches.ClientCache.MODEL_TEXTURES })).metadata((Metadata)new UIEditorSectionStart("Rendering")).add()).appendInherited(new KeyedCodec("Textures", (Codec)new ArrayCodec((Codec)BlockTypeTextures.CODEC, x$0 -> new BlockTypeTextures[x$0])), (blockType, o) -> blockType.textures = o, blockType -> blockType.textures, (blockType, parent) -> blockType.textures = parent.textures).metadata((Metadata)new UIPropertyTitle("Block Textures")).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS, UIRebuildCaches.ClientCache.BLOCK_TEXTURES })).add()).appendInherited(new KeyedCodec("TextureSideMask", (Codec)Codec.STRING), (blockType, o) -> blockType.textureSideMask = o, blockType -> blockType.textureSideMask, (blockType, parent) -> blockType.textureSideMask = parent.textureSideMask).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).metadata((Metadata)new UIPropertyTitle("Block Texture Side Mask")).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS, UIRebuildCaches.ClientCache.BLOCK_TEXTURES })).add()).appendInherited(new KeyedCodec("CubeShadingMode", (Codec)new EnumCodec(ShadingMode.class)), (blockType, o) -> blockType.cubeShadingMode = o, blockType -> blockType.cubeShadingMode, (blockType, parent) -> blockType.cubeShadingMode = parent.cubeShadingMode).addValidator(Validators.nonNull()).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).add()).appendInherited(new KeyedCodec("CustomModelTexture", (Codec)new ArrayCodec((Codec)CustomModelTexture.CODEC, x$0 -> new CustomModelTexture[x$0])), (blockType, o) -> blockType.customModelTexture = o, blockType -> blockType.customModelTexture, (blockType, parent) -> blockType.customModelTexture = parent.customModelTexture).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS, UIRebuildCaches.ClientCache.BLOCK_TEXTURES })).metadata((Metadata)new UIPropertyTitle("Block Model Textures")).add()).appendInherited(new KeyedCodec("CustomModel", (Codec)Codec.STRING), (blockType, o) -> blockType.customModel = o, blockType -> blockType.customModel, (blockType, parent) -> blockType.customModel = parent.customModel).addValidator((Validator)CommonAssetValidator.MODEL_ITEM).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).metadata((Metadata)new UIPropertyTitle("Block Model")).add()).appendInherited(new KeyedCodec("BlockBreakingDecalId", (Codec)Codec.STRING), (blockType, s) -> blockType.blockBreakingDecalId = s, blockType -> blockType.blockBreakingDecalId, (blockType, parent) -> blockType.blockBreakingDecalId = parent.blockBreakingDecalId).documentation("The block breaking decal defined here defines the decal asset that should be overlaid when this block is damaged").addValidator(BlockBreakingDecal.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("Material", (Codec)new EnumCodec(BlockMaterial.class)), (blockType, o) -> blockType.material = o, blockType -> blockType.material, (blockType, parent) -> blockType.material = parent.material).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Opacity", (Codec)new EnumCodec(Opacity.class)), (blockType, o) -> blockType.opacity = o, blockType -> blockType.opacity, (blockType, parent) -> blockType.opacity = parent.opacity).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("RequiresAlphaBlending", (Codec)Codec.BOOLEAN), (blockType, o) -> blockType.requiresAlphaBlending = o.booleanValue(), blockType -> Boolean.valueOf(blockType.requiresAlphaBlending), (blockType, parent) -> blockType.requiresAlphaBlending = parent.requiresAlphaBlending).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).add()).appendInherited(new KeyedCodec("CustomModelScale", (Codec)Codec.FLOAT), (blockType, o) -> blockType.customModelScale = o.floatValue(), blockType -> Float.valueOf(blockType.customModelScale), (blockType, parent) -> blockType.customModelScale = parent.customModelScale).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).add()).appendInherited(new KeyedCodec("CustomModelAnimation", (Codec)Codec.STRING), (blockType, o) -> blockType.customModelAnimation = o, blockType -> blockType.customModelAnimation, (blockType, parent) -> blockType.customModelAnimation = parent.customModelAnimation).addValidator((Validator)CommonAssetValidator.ANIMATION_ITEM_BLOCK).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).metadata((Metadata)new UIPropertyTitle("Block Model Animation")).add()).appendInherited(new KeyedCodec("Light", (Codec)ProtocolCodecs.COLOR_LIGHT), (blockType, o) -> blockType.light = o, blockType -> blockType.light, (blockType, parent) -> blockType.light = parent.light).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).metadata((Metadata)new UIPropertyTitle("Block Light")).add()).appendInherited(new KeyedCodec("TickProcedure", (Codec)TickProcedure.CODEC), (blockType, v) -> blockType.tickProcedure = v, blockType -> blockType.tickProcedure, (blockType, parent) -> blockType.tickProcedure = parent.tickProcedure).add()).appendInherited(new KeyedCodec("ConnectedBlockRuleSet", (Codec)ConnectedBlockRuleSet.CODEC), (blockType, connectedBlockRuleSet) -> blockType.connectedBlockRuleSet = connectedBlockRuleSet, blockType -> blockType.connectedBlockRuleSet, (blockType, parent) -> blockType.connectedBlockRuleSet = parent.connectedBlockRuleSet).add()).appendInherited(new KeyedCodec("Effect", (Codec)new ArrayCodec((Codec)new EnumCodec(ShaderType.class), x$0 -> new ShaderType[x$0])), (blockType, o) -> blockType.effect = o, blockType -> blockType.effect, (blockType, parent) -> blockType.effect = parent.effect).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).add()).appendInherited(new KeyedCodec("TransitionTexture", (Codec)Codec.STRING), (blockType, o) -> blockType.transitionTexture = o, blockType -> blockType.transitionTexture, (blockType, parent) -> blockType.transitionTexture = parent.transitionTexture).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS, UIRebuildCaches.ClientCache.BLOCK_TEXTURES })).add()).appendInherited(new KeyedCodec("TransitionToGroups", (Codec)(new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.TextField("BlockGroups")))), (blockType, o) -> blockType.transitionToGroups = o, blockType -> blockType.transitionToGroups, (blockType, parent) -> blockType.transitionToGroups = parent.transitionToGroups).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).add()).appendInherited(new KeyedCodec("TransitionToTag", (Codec)Codec.STRING), (blockType, o) -> blockType.transitionToTag = o, blockType -> blockType.transitionToTag, (blockType, parent) -> blockType.transitionToTag = parent.transitionToTag).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).add()).appendInherited(new KeyedCodec("BlockParticleSetId", (Codec)Codec.STRING), (blockType, s) -> blockType.blockParticleSetId = s, blockType -> blockType.blockParticleSetId, (blockType, parent) -> blockType.blockParticleSetId = parent.blockParticleSetId).documentation("The block particle set defined here defines which particles should be spawned when an entity interacts with this block (like when stepping on it for example").addValidator(BlockParticleSet.VALIDATOR_CACHE.getValidator()).metadata((Metadata)new UIEditorSectionStart("Particles")).add()).appendInherited(new KeyedCodec("ParticleColor", (Codec)ProtocolCodecs.COLOR), (blockType, s) -> blockType.particleColor = s, blockType -> blockType.particleColor, (blockType, parent) -> blockType.particleColor = parent.particleColor).add()).appendInherited(new KeyedCodec("Particles", (Codec)ModelParticle.ARRAY_CODEC), (blockType, s) -> blockType.particles = s, blockType -> blockType.particles, (blockType, parent) -> blockType.particles = parent.particles).documentation("The particles defined here will be spawned on top of blocks of this type placed in the world.").metadata((Metadata)new UIPropertyTitle("Block Particles")).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).addValidator((Validator)Validators.nonNullArrayElements()).add()).appendInherited(new KeyedCodec("RandomRotation", (Codec)new EnumCodec(RandomRotation.class)), (blockType, o) -> blockType.randomRotation = o, blockType -> blockType.randomRotation, (blockType, parent) -> blockType.randomRotation = parent.randomRotation).metadata((Metadata)new UIEditorSectionStart("Rotation")).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("VariantRotation", (Codec)new EnumCodec(VariantRotation.class)), (blockType, o) -> blockType.variantRotation = o, blockType -> blockType.variantRotation, (blockType, parent) -> blockType.variantRotation = parent.variantRotation).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("FlipType", (Codec)new EnumCodec(BlockFlipType.class)), (blockType, o) -> blockType.flipType = o, blockType -> blockType.flipType, (blockType, parent) -> blockType.flipType = parent.flipType).add()).appendInherited(new KeyedCodec("RotationYawPlacementOffset", (Codec)new EnumCodec(Rotation.class)), (blockType, o) -> blockType.rotationYawPlacementOffset = o, blockType -> blockType.rotationYawPlacementOffset, (blockType, parent) -> blockType.rotationYawPlacementOffset = parent.rotationYawPlacementOffset).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Seats", RotatedMountPointsArray.CODEC), (blockType, o) -> blockType.seats = o, blockType -> blockType.seats, (blockType, parent) -> blockType.seats = parent.seats).metadata((Metadata)new UIEditorSectionStart("Behaviour")).documentation("The details of the seats on this block.").add()).appendInherited(new KeyedCodec("Beds", RotatedMountPointsArray.CODEC), (blockType, o) -> blockType.beds = o, blockType -> blockType.beds, (blockType, parent) -> blockType.beds = parent.beds).documentation("The details of the beds for this block.").add()).appendInherited(new KeyedCodec("MovementSettings", (Codec)BlockMovementSettings.CODEC), (blockType, o) -> blockType.movementSettings = o, blockType -> blockType.movementSettings, (blockType, parent) -> blockType.movementSettings = parent.movementSettings).add()).appendInherited(new KeyedCodec("Flags", (Codec)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockFlags.class, BlockFlags::new).appendInherited(new KeyedCodec("IsUsable", (Codec)Codec.BOOLEAN), (blockFlags, b) -> blockFlags.isUsable = b.booleanValue(), blockFlags -> Boolean.valueOf(blockFlags.isUsable), (blockFlags, parent) -> blockFlags.isUsable = parent.isUsable).add()).appendInherited(new KeyedCodec("IsStackable", (Codec)Codec.BOOLEAN), (blockFlags, b) -> blockFlags.isStackable = b.booleanValue(), blockFlags -> Boolean.valueOf(blockFlags.isStackable), (blockFlags, parent) -> blockFlags.isStackable = parent.isStackable).add()).build()), (blockType, o) -> blockType.flags = o, blockType -> blockType.flags, (blockType, parent) -> blockType.flags = new BlockFlags(parent.flags)).add()).appendInherited(new KeyedCodec("Bench", (Codec)Bench.CODEC), (blockType, s) -> blockType.bench = s, blockType -> blockType.bench, (blockType, parent) -> blockType.bench = parent.bench).add()).appendInherited(new KeyedCodec("Gathering", (Codec)BlockGathering.CODEC), (blockType, s) -> blockType.gathering = s, blockType -> blockType.gathering, (blockType, parent) -> blockType.gathering = parent.gathering).add()).appendInherited(new KeyedCodec("PlacementSettings", (Codec)BlockPlacementSettings.CODEC), (blockType, s) -> blockType.placementSettings = s, blockType -> blockType.placementSettings, (blockType, parent) -> blockType.placementSettings = parent.placementSettings).add()).appendInherited(new KeyedCodec("Farming", FarmingData.CODEC), (blockType, farming) -> blockType.farming = farming, blockType -> blockType.farming, (blockType, parent) -> blockType.farming = parent.farming).add()).appendInherited(new KeyedCodec("IsDoor", (Codec)Codec.BOOLEAN), (blockType, s) -> blockType.isDoor = s.booleanValue(), blockType -> Boolean.valueOf(blockType.isDoor), (blockType, parent) -> blockType.isDoor = parent.isDoor).add()).appendInherited(new KeyedCodec("AllowsMultipleUsers", (Codec)Codec.BOOLEAN), (blockType, b) -> blockType.allowsMultipleUsers = b.booleanValue(), blockType -> Boolean.valueOf(blockType.allowsMultipleUsers), (blockType, parent) -> blockType.allowsMultipleUsers = parent.allowsMultipleUsers).add()).appendInherited(new KeyedCodec("HitboxType", (Codec)Codec.STRING), (blockType, o) -> blockType.hitboxType = o, blockType -> blockType.hitboxType, (blockType, parent) -> blockType.hitboxType = parent.hitboxType).addValidator(BlockBoundingBoxes.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("InteractionHitboxType", (Codec)Codec.STRING), (blockType, o) -> blockType.interactionHitboxType = o, blockType -> blockType.interactionHitboxType, (blockType, parent) -> blockType.interactionHitboxType = parent.interactionHitboxType).addValidator(BlockBoundingBoxes.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("InteractionHint", (Codec)Codec.STRING), (blockType, s) -> blockType.interactionHint = s, blockType -> blockType.interactionHint, (blockType, parent) -> blockType.interactionHint = parent.interactionHint).documentation("This property allows to specify custom text that will be displayed underneath the crosshair when the player aims at this block. The value of this property should be a reference to a translation. *{key}* will be replaced with the interaction input binding.").add()).appendInherited(new KeyedCodec("DamageToEntities", (Codec)Codec.INTEGER), (blockType, s) -> blockType.damageToEntities = s.intValue(), blockType -> Integer.valueOf(blockType.damageToEntities), (blockType, parent) -> blockType.damageToEntities = parent.damageToEntities).add()).appendInherited(new KeyedCodec("Interactions", (Codec)new EnumMapCodec(InteractionType.class, (Codec)RootInteraction.CHILD_ASSET_CODEC)), (item, v) -> item.interactions = MapUtil.combineUnmodifiable(item.interactions, v, ()), item -> item.interactions, (item, parent) -> item.interactions = parent.interactions).addValidator((Validator)RootInteraction.VALIDATOR_CACHE.getMapValueValidator()).metadata((Metadata)new UIEditorSectionStart("Interactions")).add()).appendInherited(new KeyedCodec("BlockSoundSetId", (Codec)Codec.STRING), (blockType, o) -> blockType.blockSoundSetId = o, blockType -> blockType.blockSoundSetId, (blockType, parent) -> blockType.blockSoundSetId = parent.blockSoundSetId).documentation("Sets the **BlockSoundSet** that will be used for this block for various events e.g. placement, breaking").addValidator(BlockSoundSet.VALIDATOR_CACHE.getValidator()).metadata((Metadata)new UIEditorSectionStart("Sounds")).add()).appendInherited(new KeyedCodec("AmbientSoundEventId", (Codec)Codec.STRING), (blockType, s) -> blockType.ambientSoundEventId = s, blockType -> blockType.ambientSoundEventId, (blockType, parent) -> blockType.ambientSoundEventId = parent.ambientSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.LOOPING).documentation("A looping ambient sound event that emits from this block when placed in the world or held in-hand.").add()).appendInherited(new KeyedCodec("InteractionSoundEventId", (Codec)Codec.STRING), (blockType, s) -> blockType.interactionSoundEventId = s, blockType -> blockType.interactionSoundEventId, (blockType, parent) -> blockType.interactionSoundEventId = parent.interactionSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.ONESHOT).documentation("A oneshot sound event that plays upon interaction with this block.").add()).appendInherited(new KeyedCodec("Looping", (Codec)Codec.BOOLEAN), (blockType, s) -> blockType.isLooping = s.booleanValue(), blockType -> Boolean.valueOf(blockType.isLooping), (blockType, parent) -> blockType.isLooping = parent.isLooping).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MODELS })).add()).appendInherited(new KeyedCodec("SupportDropType", (Codec)SupportDropType.CODEC), (blockType, o) -> blockType.supportDropType = o, blockType -> blockType.supportDropType, (blockType, parent) -> blockType.supportDropType = parent.supportDropType).metadata((Metadata)new UIEditorSectionStart("Support")).add()).appendInherited(new KeyedCodec("MaxSupportDistance", (Codec)Codec.INTEGER), (blockType, i) -> blockType.maxSupportDistance = i.intValue(), blockType -> Integer.valueOf(blockType.maxSupportDistance), (blockType, parent) -> blockType.maxSupportDistance = parent.maxSupportDistance).addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(14))).add()).appendInherited(new KeyedCodec("SupportsRequiredFor", (Codec)new EnumCodec(BlockSupportsRequiredForType.class)), (blockType, o) -> blockType.blockSupportsRequiredFor = o, blockType -> blockType.blockSupportsRequiredFor, (blockType, parent) -> blockType.blockSupportsRequiredFor = parent.blockSupportsRequiredFor).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Support", (Codec)new MergedEnumMapCodec(BlockFace.class, MergedBlockFaces.class, MergedBlockFaces::getComponents, ArrayUtil::combine, (Codec)new ArrayCodec((Codec)RequiredBlockFaceSupport.CODEC, x$0 -> new RequiredBlockFaceSupport[x$0]))), (blockType, o) -> blockType.support = o, blockType -> blockType.support, (blockType, parent) -> blockType.support = parent.support).addValidator(RequiredBlockFaceSupportValidator.INSTANCE).documentation("A set of \"Required Support\" conditions. If met, the block won't fall off from block physics checks.\n*If this field is empty the block is automatically considered supported.*\n").add()).appendInherited(new KeyedCodec("Supporting", (Codec)new MergedEnumMapCodec(BlockFace.class, MergedBlockFaces.class, MergedBlockFaces::getComponents, ArrayUtil::combine, (Codec)new ArrayCodec((Codec)BlockFaceSupport.CODEC, x$0 -> new BlockFaceSupport[x$0]))), (blockType, o) -> blockType.supporting = o, blockType -> blockType.supporting, (blockType, parent) -> blockType.supporting = parent.supporting).add()).documentation("The counter-party to \"Support\". This block offers supporting faces which can match the face requirements of adjacent/nearby blocks.")).appendInherited(new KeyedCodec("IgnoreSupportWhenPlaced", (Codec)Codec.BOOLEAN), (o, i) -> o.ignoreSupportWhenPlaced = i.booleanValue(), o -> Boolean.valueOf(o.ignoreSupportWhenPlaced), (o, p) -> o.ignoreSupportWhenPlaced = p.ignoreSupportWhenPlaced).documentation("Whether when this block is placed by a player that the support requirements should be ignored.").add()).append(new KeyedCodec("Aliases", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (blockType, o) -> blockType.aliases = o, blockType -> blockType.aliases).documentation("Specifies the alternatives names (aliases) for a block type for use in command matching").add()).append(new KeyedCodec("Tint", (Codec)ProtocolCodecs.COLOR_ARRAY), (blockType, o) -> { blockType.tintUp = o; blockType.tintDown = o; blockType.tintNorth = o; blockType.tintSouth = o; blockType.tintWest = o; blockType.tintEast = o; }blockType -> null).metadata((Metadata)new UIEditorSectionStart("Tint")).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("TintUp", (Codec)ProtocolCodecs.COLOR_ARRAY), (blockType, o) -> blockType.tintUp = o, blockType -> blockType.tintUp, (blockType, parent) -> blockType.tintUp = parent.tintUp).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("TintDown", (Codec)ProtocolCodecs.COLOR_ARRAY), (blockType, o) -> blockType.tintDown = o, blockType -> blockType.tintDown, (blockType, parent) -> blockType.tintDown = parent.tintDown).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("TintNorth", (Codec)ProtocolCodecs.COLOR_ARRAY), (blockType, o) -> blockType.tintNorth = o, blockType -> blockType.tintNorth, (blockType, parent) -> blockType.tintNorth = parent.tintNorth).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("TintSouth", (Codec)ProtocolCodecs.COLOR_ARRAY), (blockType, o) -> blockType.tintSouth = o, blockType -> blockType.tintSouth, (blockType, parent) -> blockType.tintSouth = parent.tintSouth).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("TintWest", (Codec)ProtocolCodecs.COLOR_ARRAY), (blockType, o) -> blockType.tintWest = o, blockType -> blockType.tintWest, (blockType, parent) -> blockType.tintWest = parent.tintWest).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("TintEast", (Codec)ProtocolCodecs.COLOR_ARRAY), (blockType, o) -> blockType.tintEast = o, blockType -> blockType.tintEast, (blockType, parent) -> blockType.tintEast = parent.tintEast).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).append(new KeyedCodec("BiomeTint", (Codec)Codec.INTEGER), (blockType, o) -> { blockType.biomeTintUp = o.intValue(); blockType.biomeTintDown = o.intValue(); blockType.biomeTintNorth = o.intValue(); blockType.biomeTintSouth = o.intValue(); blockType.biomeTintWest = o.intValue(); blockType.biomeTintEast = o.intValue(); }blockType -> null).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("BiomeTintUp", (Codec)Codec.INTEGER), (blockType, o) -> blockType.biomeTintUp = o.intValue(), blockType -> Integer.valueOf(blockType.biomeTintUp), (blockType, parent) -> blockType.biomeTintUp = parent.biomeTintUp).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("BiomeTintDown", (Codec)Codec.INTEGER), (blockType, o) -> blockType.biomeTintDown = o.intValue(), blockType -> Integer.valueOf(blockType.biomeTintDown), (blockType, parent) -> blockType.biomeTintDown = parent.biomeTintDown).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("BiomeTintNorth", (Codec)Codec.INTEGER), (blockType, o) -> blockType.biomeTintNorth = o.intValue(), blockType -> Integer.valueOf(blockType.biomeTintNorth), (blockType, parent) -> blockType.biomeTintNorth = parent.biomeTintNorth).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("BiomeTintSouth", (Codec)Codec.INTEGER), (blockType, o) -> blockType.biomeTintSouth = o.intValue(), blockType -> Integer.valueOf(blockType.biomeTintSouth), (blockType, parent) -> blockType.biomeTintSouth = parent.biomeTintSouth).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("BiomeTintWest", (Codec)Codec.INTEGER), (blockType, o) -> blockType.biomeTintWest = o.intValue(), blockType -> Integer.valueOf(blockType.biomeTintWest), (blockType, parent) -> blockType.biomeTintWest = parent.biomeTintWest).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("BiomeTintEast", (Codec)Codec.INTEGER), (blockType, o) -> blockType.biomeTintEast = o.intValue(), blockType -> Integer.valueOf(blockType.biomeTintEast), (blockType, parent) -> blockType.biomeTintEast = parent.biomeTintEast).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.MAP_GEOMETRY })).add()).appendInherited(new KeyedCodec("State", (Codec)StateData.CODEC), (blockType, s) -> { s.copyFrom(blockType.state); blockType.state = s; }blockType -> blockType.state, (blockType, parent) -> blockType.state = parent.state).metadata((Metadata)new UIEditorSectionStart("State")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("BlockEntity", (Codec)new StoredCodec(ChunkStore.HOLDER_CODEC_KEY)), (blockType, s) -> blockType.blockEntity = s, blockType -> blockType.blockEntity, (blockType, parent) -> blockType.blockEntity = parent.blockEntity).metadata((Metadata)new UIEditorSectionStart("Components")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("Rail", (Codec)ProtocolCodecs.RAIL_CONFIG_CODEC), (o, v) -> o.railConfig = v, o -> o.railConfig, (o, p) -> o.railConfig = p.railConfig).add()).afterDecode(BlockType::processConfig)).build();
/*  703 */   } public static final String[] EMPTY_ALIAS_LIST = new String[0];
/*      */ 
/*      */   
/*      */   static {
/*  707 */     StateData.addDefinitions();
/*      */   }
/*      */   
/*  710 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BlockType::getAssetStore));
/*      */   
/*      */   public static final String UNKNOWN_TEXTURE = "BlockTextures/Unknown.png";
/*      */   
/*  714 */   public static final ModelTexture[] UNKNOWN_CUSTOM_MODEL_TEXTURE = new ModelTexture[] { new ModelTexture("BlockTextures/Unknown.png", 1.0F) };
/*  715 */   public static final BlockTextures[] UNKNOWN_BLOCK_TEXTURES = new BlockTextures[] { new BlockTextures("BlockTextures/Unknown.png", "BlockTextures/Unknown.png", "BlockTextures/Unknown.png", "BlockTextures/Unknown.png", "BlockTextures/Unknown.png", "BlockTextures/Unknown.png", 1.0F) };
/*  716 */   public static final Map<BlockFace, RequiredBlockFaceSupport[]> REQUIRED_BOTTOM_FACE_SUPPORT = (Map)Collections.unmodifiableMap(new EnumMap<BlockFace, RequiredBlockFaceSupport[]>(BlockFace.class) {
/*      */       
/*      */       });
/*  719 */   public static final BlockFaceSupport[] BLOCK_FACE_SUPPORT_ALL_ARRAY = new BlockFaceSupport[] { BlockFaceSupport.ALL };
/*  720 */   public static final Map<BlockFace, BlockFaceSupport[]> ALL_SUPPORTING_FACES = (Map)Collections.unmodifiableMap(new EnumMap<BlockFace, BlockFaceSupport[]>(BlockFace.class)
/*      */       {
/*      */       
/*      */       });
/*      */   
/*  725 */   public static final ShaderType[] DEFAULT_SHADER_EFFECTS = new ShaderType[] { ShaderType.None };
/*      */   
/*  727 */   public static final BlockType DEFAULT_BLOCK_TYPE = new BlockType(); public static final ISectionPalette.KeySerializer KEY_SERIALIZER; public static final ToIntFunction<ByteBuf> KEY_DESERIALIZER; public static final String EMPTY_KEY = "Empty"; public static final String UNKNOWN_KEY = "Unknown"; public static final String DEBUG_CUBE_KEY = "Debug_Cube"; public static final String DEBUG_MODEL_KEY = "Debug_Model"; public static final int EMPTY_ID = 0;
/*      */   static {
/*  729 */     KEY_SERIALIZER = ((buf, id) -> {
/*      */         String key = ((BlockType)getAssetMap().getAssetOrDefault(id, UNKNOWN)).getId();
/*      */         ByteBufUtil.writeUTF(buf, key);
/*      */       });
/*  733 */     KEY_DESERIALIZER = (byteBuf -> {
/*      */         String blockType = ByteBufUtil.readUTF(byteBuf);
/*      */         return getBlockIdOrUnknown(blockType, "Failed to find block '%s' in chunk section!", new Object[] { blockType });
/*      */       });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  744 */   public static final BlockType EMPTY = new BlockType("Empty")
/*      */     {
/*      */     
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int UNKNOWN_ID = 1;
/*      */ 
/*      */ 
/*      */   
/*  756 */   public static final BlockType UNKNOWN = new BlockType("Unknown")
/*      */     {
/*      */     
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int DEBUG_CUBE_ID = 2;
/*      */ 
/*      */   
/*  766 */   public static final BlockType DEBUG_CUBE = new BlockType("Debug_Cube")
/*      */     {
/*      */     
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int DEBUG_MODEL_ID = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  788 */   public static final BlockType DEBUG_MODEL = new BlockType("Debug_Model") {
/*      */     
/*      */     };
/*      */   public static final String TECHNICAL_BLOCK_GROUP = "@Tech";
/*      */   private static AssetStore<String, BlockType, BlockTypeAssetMap<String, BlockType>> ASSET_STORE;
/*      */   protected AssetExtraInfo.Data data;
/*      */   protected String id;
/*      */   protected boolean unknown;
/*      */   protected String group;
/*      */   protected String blockListAssetId;
/*      */   protected String prefabListAssetId;
/*      */   
/*      */   @Nullable
/*      */   public static BlockType fromString(@Nonnull String input) {
/*  802 */     return (BlockType)getAssetMap().getAsset(input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AssetStore<String, BlockType, BlockTypeAssetMap<String, BlockType>> getAssetStore() {
/*  810 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BlockType.class); 
/*  811 */     return ASSET_STORE;
/*      */   }
/*      */   
/*      */   public static BlockTypeAssetMap<String, BlockType> getAssetMap() {
/*  815 */     return (BlockTypeAssetMap<String, BlockType>)getAssetStore().getAssetMap();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  829 */   protected String blockSoundSetId = "EMPTY";
/*  830 */   protected transient int blockSoundSetIndex = 0;
/*      */   
/*      */   protected ModelParticle[] particles;
/*      */   
/*      */   protected String blockParticleSetId;
/*      */   
/*      */   protected String blockBreakingDecalId;
/*      */   protected Color particleColor;
/*      */   protected TickProcedure tickProcedure;
/*      */   protected ShaderType[] effect;
/*      */   protected BlockTypeTextures[] textures;
/*      */   protected String textureSideMask;
/*      */   @Nonnull
/*  843 */   protected ShadingMode cubeShadingMode = ShadingMode.Standard;
/*      */   
/*      */   @Nullable
/*      */   protected String customModel;
/*      */   
/*      */   @Nullable
/*      */   protected CustomModelTexture[] customModelTexture;
/*      */   
/*  851 */   protected float customModelScale = 1.0F;
/*      */   protected String customModelAnimation;
/*      */   @Nonnull
/*  854 */   protected DrawType drawType = DrawType.Cube;
/*      */   @Nonnull
/*  856 */   protected BlockMaterial material = BlockMaterial.Empty;
/*      */   @Nonnull
/*  858 */   protected Opacity opacity = Opacity.Solid;
/*      */   
/*      */   protected boolean requiresAlphaBlending;
/*      */   
/*      */   protected Color[] tintUp;
/*      */   
/*      */   protected Color[] tintDown;
/*      */   protected Color[] tintNorth;
/*      */   protected Color[] tintSouth;
/*      */   protected Color[] tintWest;
/*      */   protected Color[] tintEast;
/*      */   protected int biomeTintUp;
/*      */   protected int biomeTintDown;
/*      */   protected int biomeTintNorth;
/*      */   protected int biomeTintSouth;
/*      */   protected int biomeTintWest;
/*      */   protected int biomeTintEast;
/*      */   @Nonnull
/*  876 */   protected BlockSupportsRequiredForType blockSupportsRequiredFor = BlockSupportsRequiredForType.All;
/*      */   @Nonnull
/*  878 */   protected RandomRotation randomRotation = RandomRotation.None;
/*      */   @Nonnull
/*  880 */   protected VariantRotation variantRotation = VariantRotation.None;
/*      */   
/*  882 */   protected BlockFlipType flipType = BlockFlipType.SYMMETRIC; @Nonnull
/*  883 */   protected Rotation rotationYawPlacementOffset = Rotation.None;
/*      */   
/*      */   @Nullable
/*      */   protected RotatedMountPointsArray seats;
/*      */   
/*      */   @Nullable
/*      */   protected RotatedMountPointsArray beds;
/*      */   
/*      */   protected String transitionTexture;
/*      */   
/*      */   protected String[] transitionToGroups;
/*      */   protected String transitionToTag;
/*  895 */   protected String hitboxType = "Full";
/*  896 */   protected transient int hitboxTypeIndex = 0;
/*      */   
/*      */   @Nullable
/*      */   protected String interactionHitboxType;
/*  900 */   protected transient int interactionHitboxTypeIndex = Integer.MIN_VALUE;
/*      */   
/*      */   protected ColorLight light;
/*      */   
/*  904 */   protected BlockMovementSettings movementSettings = new BlockMovementSettings();
/*      */   
/*  906 */   protected BlockFlags flags = new BlockFlags(false, true);
/*      */ 
/*      */   
/*      */   protected String interactionHint;
/*      */ 
/*      */   
/*      */   protected boolean isTrigger;
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected boolean isDoor;
/*      */ 
/*      */   
/*      */   protected int damageToEntities;
/*      */ 
/*      */   
/*      */   protected boolean allowsMultipleUsers = true;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected ConnectedBlockRuleSet connectedBlockRuleSet;
/*      */ 
/*      */   
/*      */   protected Bench bench;
/*      */ 
/*      */   
/*      */   protected BlockGathering gathering;
/*      */ 
/*      */   
/*      */   protected BlockPlacementSettings placementSettings;
/*      */   
/*      */   protected StateData state;
/*      */   
/*      */   protected String ambientSoundEventId;
/*      */   
/*      */   protected transient int ambientSoundEventIndex;
/*      */   
/*      */   protected String interactionSoundEventId;
/*      */   
/*      */   protected transient int interactionSoundEventIndex;
/*      */   
/*      */   protected boolean isLooping;
/*      */   
/*      */   protected Holder<ChunkStore> blockEntity;
/*      */   
/*      */   protected FarmingData farming;
/*      */   
/*  953 */   protected SupportDropType supportDropType = SupportDropType.BREAK;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int maxSupportDistance;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected Map<BlockFace, RequiredBlockFaceSupport[]> support;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected transient Map<BlockFace, RequiredBlockFaceSupport[]>[] rotatedSupport;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected Map<BlockFace, BlockFaceSupport[]> supporting;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected transient Map<BlockFace, BlockFaceSupport[]>[] rotatedSupporting;
/*      */ 
/*      */   
/*      */   protected boolean ignoreSupportWhenPlaced;
/*      */ 
/*      */   
/*  981 */   protected Map<InteractionType, String> interactions = Collections.emptyMap();
/*      */   
/*      */   @Nullable
/*      */   protected RailConfig railConfig;
/*      */   
/*      */   @Nullable
/*      */   protected RailConfig[] rotatedRailConfig;
/*  988 */   protected String[] aliases = EMPTY_ALIAS_LIST;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private transient String defaultStateKey;
/*      */   
/*      */   @Nullable
/*      */   private transient SoftReference<BlockType> cachedPacket;
/*      */ 
/*      */   
/*      */   public BlockType(String id) {
/*  999 */     this.id = id;
/*      */   }
/*      */   
/*      */   public BlockType(@Nonnull BlockType other) {
/* 1003 */     this.data = other.data;
/* 1004 */     this.id = other.id;
/* 1005 */     this.unknown = other.unknown;
/* 1006 */     this.group = other.group;
/* 1007 */     this.blockSoundSetId = other.blockSoundSetId;
/* 1008 */     this.blockSoundSetIndex = other.blockSoundSetIndex;
/* 1009 */     this.particles = other.particles;
/* 1010 */     this.blockParticleSetId = other.blockParticleSetId;
/* 1011 */     this.blockBreakingDecalId = other.blockBreakingDecalId;
/* 1012 */     this.particleColor = other.particleColor;
/* 1013 */     this.tickProcedure = other.tickProcedure;
/* 1014 */     this.effect = other.effect;
/* 1015 */     this.textures = other.textures;
/* 1016 */     this.textureSideMask = other.textureSideMask;
/* 1017 */     this.customModelTexture = other.customModelTexture;
/* 1018 */     this.drawType = other.drawType;
/* 1019 */     this.material = other.material;
/* 1020 */     this.opacity = other.opacity;
/* 1021 */     this.requiresAlphaBlending = other.requiresAlphaBlending;
/* 1022 */     this.customModel = other.customModel;
/* 1023 */     this.customModelScale = other.customModelScale;
/* 1024 */     this.customModelAnimation = other.customModelAnimation;
/* 1025 */     this.tintUp = other.tintUp;
/* 1026 */     this.tintDown = other.tintDown;
/* 1027 */     this.tintNorth = other.tintNorth;
/* 1028 */     this.tintSouth = other.tintSouth;
/* 1029 */     this.tintWest = other.tintWest;
/* 1030 */     this.tintEast = other.tintEast;
/* 1031 */     this.biomeTintUp = other.biomeTintUp;
/* 1032 */     this.biomeTintDown = other.biomeTintDown;
/* 1033 */     this.biomeTintNorth = other.biomeTintNorth;
/* 1034 */     this.biomeTintSouth = other.biomeTintSouth;
/* 1035 */     this.biomeTintWest = other.biomeTintWest;
/* 1036 */     this.biomeTintEast = other.biomeTintEast;
/* 1037 */     this.randomRotation = other.randomRotation;
/* 1038 */     this.variantRotation = other.variantRotation;
/* 1039 */     this.flipType = other.flipType;
/* 1040 */     this.rotationYawPlacementOffset = other.rotationYawPlacementOffset;
/* 1041 */     this.seats = other.seats;
/* 1042 */     this.transitionTexture = other.transitionTexture;
/* 1043 */     this.transitionToGroups = other.transitionToGroups;
/* 1044 */     this.transitionToTag = other.transitionToTag;
/* 1045 */     this.hitboxType = other.hitboxType;
/* 1046 */     this.hitboxTypeIndex = other.hitboxTypeIndex;
/* 1047 */     this.interactionHitboxType = other.interactionHitboxType;
/* 1048 */     this.interactionHitboxTypeIndex = other.interactionHitboxTypeIndex;
/* 1049 */     this.light = other.light;
/* 1050 */     this.movementSettings = other.movementSettings;
/* 1051 */     this.flags = other.flags;
/* 1052 */     this.interactionHint = other.interactionHint;
/* 1053 */     this.isTrigger = other.isTrigger;
/* 1054 */     this.damageToEntities = other.damageToEntities;
/* 1055 */     this.bench = other.bench;
/* 1056 */     this.gathering = other.gathering;
/* 1057 */     this.placementSettings = other.placementSettings;
/* 1058 */     this.state = other.state;
/* 1059 */     this.blockEntity = other.blockEntity;
/* 1060 */     this.farming = other.farming;
/* 1061 */     this.supportDropType = other.supportDropType;
/* 1062 */     this.maxSupportDistance = other.maxSupportDistance;
/* 1063 */     this.support = other.support;
/* 1064 */     this.supporting = other.supporting;
/* 1065 */     this.cubeShadingMode = other.cubeShadingMode;
/* 1066 */     this.allowsMultipleUsers = other.allowsMultipleUsers;
/* 1067 */     this.interactions = other.interactions;
/* 1068 */     this.ambientSoundEventId = other.ambientSoundEventId;
/* 1069 */     this.ambientSoundEventIndex = other.ambientSoundEventIndex;
/* 1070 */     this.interactionSoundEventId = other.interactionSoundEventId;
/* 1071 */     this.interactionSoundEventIndex = other.interactionSoundEventIndex;
/* 1072 */     this.isLooping = other.isLooping;
/* 1073 */     this.isDoor = other.isDoor;
/* 1074 */     this.blockSupportsRequiredFor = other.blockSupportsRequiredFor;
/* 1075 */     this.connectedBlockRuleSet = other.connectedBlockRuleSet;
/* 1076 */     this.railConfig = other.railConfig;
/* 1077 */     this.ignoreSupportWhenPlaced = other.ignoreSupportWhenPlaced;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public BlockType toPacket() {
/* 1084 */     BlockType cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 1085 */     if (cached != null) return cached;
/*      */     
/* 1087 */     BlockTypeAssetMap<String, BlockType> blockTypeAssetMap = getAssetMap();
/*      */     
/* 1089 */     BlockType packet = new BlockType();
/*      */     
/* 1091 */     packet.name = this.id;
/*      */     
/* 1093 */     Item item = getItem();
/* 1094 */     if (item != null) {
/* 1095 */       packet.item = item.getId();
/*      */     }
/* 1097 */     packet.unknown = this.unknown;
/* 1098 */     if (this.group != null) {
/* 1099 */       packet.group = getAssetMap().getGroupId(this.group);
/*      */     }
/*      */     
/* 1102 */     packet.blockSoundSetIndex = this.blockSoundSetIndex;
/* 1103 */     packet.blockParticleSetId = this.blockParticleSetId;
/* 1104 */     packet.blockBreakingDecalId = this.blockBreakingDecalId;
/* 1105 */     packet.particleColor = this.particleColor;
/*      */     
/* 1107 */     if (this.support != null) {
/* 1108 */       Object2ObjectOpenHashMap<BlockNeighbor, RequiredBlockFaceSupport[]> supportMap = new Object2ObjectOpenHashMap();
/*      */       
/* 1110 */       for (Map.Entry<BlockFace, RequiredBlockFaceSupport[]> entry : this.support.entrySet()) {
/* 1111 */         RequiredBlockFaceSupport[] supports = entry.getValue();
/* 1112 */         RequiredBlockFaceSupport[] protocolSupports = new RequiredBlockFaceSupport[supports.length];
/*      */         
/* 1114 */         for (int i = 0; i < supports.length; i++) {
/* 1115 */           protocolSupports[i] = supports[i].toPacket();
/*      */         }
/*      */         
/* 1118 */         supportMap.put(((BlockFace)entry.getKey()).toProtocolBlockNeighbor(), protocolSupports);
/*      */       } 
/*      */       
/* 1121 */       packet.support = (Map)supportMap;
/*      */     } 
/*      */     
/* 1124 */     if (this.supporting != null) {
/* 1125 */       Object2ObjectOpenHashMap<BlockNeighbor, BlockFaceSupport[]> supportingMap = new Object2ObjectOpenHashMap();
/*      */       
/* 1127 */       for (Map.Entry<BlockFace, BlockFaceSupport[]> entry : this.supporting.entrySet()) {
/* 1128 */         BlockFaceSupport[] blockFaceSupports = entry.getValue();
/* 1129 */         BlockFaceSupport[] protocolBlockFaceSupports = new BlockFaceSupport[blockFaceSupports.length];
/*      */         
/* 1131 */         for (int i = 0; i < blockFaceSupports.length; i++) {
/* 1132 */           protocolBlockFaceSupports[i] = blockFaceSupports[i].toPacket();
/*      */         }
/* 1134 */         supportingMap.put(((BlockFace)entry.getKey()).toProtocolBlockNeighbor(), protocolBlockFaceSupports);
/*      */       } 
/*      */       
/* 1137 */       packet.supporting = (Map)supportingMap;
/*      */     } 
/* 1139 */     switch (this.blockSupportsRequiredFor) { default: throw new MatchException(null, null);case Any: case All: break; }  packet.blockSupportsRequiredFor = 
/*      */       
/* 1141 */       BlockSupportsRequiredForType.All;
/*      */     
/* 1143 */     packet.maxSupportDistance = this.maxSupportDistance;
/*      */     
/* 1145 */     if (this.effect != null && this.effect.length > 0) {
/* 1146 */       packet.shaderEffect = this.effect;
/*      */     } else {
/* 1148 */       packet.shaderEffect = DEFAULT_SHADER_EFFECTS;
/*      */     } 
/*      */     
/* 1151 */     if (this.textures != null && this.textures.length > 0) {
/* 1152 */       int totalWeight = 0;
/* 1153 */       for (BlockTypeTextures texture : this.textures) {
/* 1154 */         totalWeight = (int)(totalWeight + texture.getWeight());
/*      */       }
/* 1156 */       BlockTextures[] texturePackets = new BlockTextures[this.textures.length];
/* 1157 */       for (int i = 0; i < this.textures.length; i++) {
/* 1158 */         texturePackets[i] = this.textures[i].toPacket(totalWeight);
/*      */       }
/* 1160 */       packet.cubeTextures = texturePackets;
/*      */     } else {
/* 1162 */       packet.cubeTextures = UNKNOWN_BLOCK_TEXTURES;
/*      */     } 
/* 1164 */     packet.cubeSideMaskTexture = this.textureSideMask;
/* 1165 */     packet.cubeShadingMode = this.cubeShadingMode;
/*      */     
/* 1167 */     if (this.customModelTexture != null && this.customModelTexture.length > 0) {
/* 1168 */       int totalWeight = 0;
/* 1169 */       for (CustomModelTexture modelTexture : this.customModelTexture) {
/* 1170 */         totalWeight += modelTexture.getWeight();
/*      */       }
/* 1172 */       ModelTexture[] texturePackets = new ModelTexture[this.customModelTexture.length];
/* 1173 */       for (int i = 0; i < this.customModelTexture.length; i++) {
/* 1174 */         texturePackets[i] = this.customModelTexture[i].toPacket(totalWeight);
/*      */       }
/* 1176 */       packet.modelTexture = texturePackets;
/*      */     } else {
/* 1178 */       packet.modelTexture = UNKNOWN_CUSTOM_MODEL_TEXTURE;
/*      */     } 
/* 1180 */     packet.drawType = this.drawType;
/* 1181 */     packet.requiresAlphaBlending = this.requiresAlphaBlending;
/*      */     
/* 1183 */     if (this.customModel != null) {
/* 1184 */       packet.model = this.customModel;
/*      */     }
/* 1186 */     packet.modelScale = this.customModelScale;
/* 1187 */     if (this.customModelAnimation != null) {
/* 1188 */       packet.modelAnimation = this.customModelAnimation;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1196 */     packet.tint = new Tint();
/*      */ 
/*      */     
/* 1199 */     if (this.tintUp != null && this.tintUp.length > 0) {
/* 1200 */       packet.tint.top = ColorParseUtil.colorToARGBInt(this.tintUp[0]);
/*      */     } else {
/* 1202 */       packet.tint.top = -1;
/*      */     } 
/* 1204 */     if (this.tintDown != null && this.tintDown.length > 0) {
/* 1205 */       packet.tint.bottom = ColorParseUtil.colorToARGBInt(this.tintDown[0]);
/*      */     } else {
/* 1207 */       packet.tint.bottom = -1;
/*      */     } 
/* 1209 */     if (this.tintNorth != null && this.tintNorth.length > 0) {
/* 1210 */       packet.tint.back = ColorParseUtil.colorToARGBInt(this.tintNorth[0]);
/*      */     } else {
/* 1212 */       packet.tint.back = -1;
/*      */     } 
/* 1214 */     if (this.tintSouth != null && this.tintSouth.length > 0) {
/* 1215 */       packet.tint.front = ColorParseUtil.colorToARGBInt(this.tintSouth[0]);
/*      */     } else {
/* 1217 */       packet.tint.front = -1;
/*      */     } 
/* 1219 */     if (this.tintWest != null && this.tintWest.length > 0) {
/* 1220 */       packet.tint.left = ColorParseUtil.colorToARGBInt(this.tintWest[0]);
/*      */     } else {
/* 1222 */       packet.tint.left = -1;
/*      */     } 
/* 1224 */     if (this.tintEast != null && this.tintEast.length > 0) {
/* 1225 */       packet.tint.right = ColorParseUtil.colorToARGBInt(this.tintEast[0]);
/*      */     } else {
/* 1227 */       packet.tint.right = -1;
/*      */     } 
/*      */     
/* 1230 */     packet.biomeTint = new Tint(this.biomeTintUp, this.biomeTintDown, this.biomeTintSouth, this.biomeTintNorth, this.biomeTintWest, this.biomeTintEast);
/*      */     
/* 1232 */     packet.variantRotation = this.variantRotation.toPacket();
/*      */     
/* 1234 */     packet.randomRotation = this.randomRotation;
/* 1235 */     packet.rotationYawPlacementOffset = this.rotationYawPlacementOffset.toPacket();
/* 1236 */     packet.opacity = this.opacity;
/* 1237 */     if (this.transitionTexture != null) {
/* 1238 */       packet.transitionTexture = this.transitionTexture;
/*      */     }
/* 1240 */     if (this.transitionToGroups != null && this.transitionToGroups.length > 0) {
/* 1241 */       int[] arr = new int[this.transitionToGroups.length];
/* 1242 */       for (int i = 0; i < this.transitionToGroups.length; i++) {
/* 1243 */         arr[i] = blockTypeAssetMap.getGroupId(this.transitionToGroups[i]);
/*      */       }
/* 1245 */       packet.transitionToGroups = arr;
/*      */     } 
/* 1247 */     if (this.transitionToTag != null) {
/* 1248 */       packet.transitionToTag = AssetRegistry.getOrCreateTagIndex(this.transitionToTag);
/*      */     } else {
/* 1250 */       packet.transitionToTag = Integer.MIN_VALUE;
/*      */     } 
/* 1252 */     packet.material = this.material;
/*      */     
/* 1254 */     packet.hitbox = this.hitboxTypeIndex;
/* 1255 */     packet.interactionHitbox = this.interactionHitboxTypeIndex;
/* 1256 */     packet.light = this.light;
/* 1257 */     packet.movementSettings = this.movementSettings.toPacket();
/* 1258 */     packet.flags = this.flags;
/* 1259 */     packet.interactionHint = this.interactionHint;
/*      */     
/* 1261 */     if (this.gathering != null) {
/* 1262 */       packet.gathering = this.gathering.toPacket();
/*      */     }
/*      */     
/* 1265 */     if (this.placementSettings != null) {
/* 1266 */       packet.placementSettings = this.placementSettings.toPacket();
/*      */     }
/*      */     
/* 1269 */     packet.looping = this.isLooping;
/* 1270 */     packet.ambientSoundEventIndex = this.ambientSoundEventIndex;
/*      */     
/* 1272 */     if (this.particles != null && this.particles.length > 0) {
/* 1273 */       packet.particles = new com.hypixel.hytale.protocol.ModelParticle[this.particles.length];
/*      */       
/* 1275 */       for (int i = 0; i < this.particles.length; i++) {
/* 1276 */         packet.particles[i] = this.particles[i].toPacket();
/*      */       }
/*      */     } 
/*      */     
/* 1280 */     Object2IntOpenHashMap<InteractionType> interactionMap = new Object2IntOpenHashMap();
/* 1281 */     for (Map.Entry<InteractionType, String> e : this.interactions.entrySet()) {
/* 1282 */       interactionMap.put(e.getKey(), RootInteraction.getRootInteractionIdOrUnknown(e.getValue()));
/*      */     }
/* 1284 */     packet.interactions = (Map)interactionMap;
/*      */     
/* 1286 */     if (this.state != null) {
/* 1287 */       packet.states = this.state.toPacket(this);
/*      */       
/* 1289 */       String def = getBlockKeyForState("default");
/* 1290 */       if (def != null) packet.states.put("default", Integer.valueOf(getAssetMap().getIndex(def)));
/*      */     
/*      */     } 
/* 1293 */     if (this.data != null) {
/* 1294 */       IntSet tags = this.data.getExpandedTagIndexes();
/* 1295 */       if (tags != null) {
/* 1296 */         packet.tagIndexes = tags.toIntArray();
/*      */       }
/*      */     } 
/*      */     
/* 1300 */     packet.rail = this.railConfig;
/* 1301 */     packet.ignoreSupportWhenPlaced = this.ignoreSupportWhenPlaced;
/*      */     
/* 1303 */     if (this.bench != null) {
/* 1304 */       packet.bench = this.bench.toPacket();
/*      */     }
/*      */     
/* 1307 */     if (this.connectedBlockRuleSet != null) {
/* 1308 */       packet.connectedBlockRuleSet = this.connectedBlockRuleSet.toPacket(blockTypeAssetMap);
/*      */     }
/*      */     
/* 1311 */     this.cachedPacket = new SoftReference<>(packet);
/* 1312 */     return packet;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getId() {
/* 1317 */     return this.id;
/*      */   }
/*      */   
/*      */   public AssetExtraInfo.Data getData() {
/* 1321 */     return this.data;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Item getItem() {
/* 1327 */     if (this.data == null) return null; 
/* 1328 */     String itemKey = (String)this.data.getContainerKey(Item.class);
/* 1329 */     if (itemKey == null) return null; 
/* 1330 */     return (Item)Item.getAssetMap().getAsset(itemKey);
/*      */   }
/*      */   
/*      */   public boolean isState() {
/* 1334 */     return (getStateForBlock(this.id) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public BlockType getBlockForState(@Nonnull String state) {
/* 1340 */     String key = getBlockKeyForState(state);
/* 1341 */     if (key == null) return null; 
/* 1342 */     return (BlockType)getAssetMap().getAsset(key);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getBlockKeyForState(@Nonnull String state) {
/*      */     String key;
/* 1348 */     if (state.equals("default")) {
/* 1349 */       key = getDefaultStateKey();
/*      */     } else {
/* 1351 */       key = (this.state != null) ? this.state.getBlockForState(state) : null;
/*      */     } 
/* 1353 */     return key;
/*      */   }
/*      */   
/*      */   public String getDefaultStateKey() {
/* 1357 */     if (this.defaultStateKey == null) {
/* 1358 */       this.defaultStateKey = (String)this.data.getContainerKey(BlockType.class);
/*      */     }
/* 1360 */     return this.defaultStateKey;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getStateForBlock(@Nonnull BlockType blockType) {
/* 1365 */     return getStateForBlock(blockType.getId());
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getStateForBlock(@Nonnull String blockTypeKey) {
/* 1370 */     return (this.state != null) ? this.state.getStateForBlock(blockTypeKey) : null;
/*      */   }
/*      */   
/*      */   public boolean isUnknown() {
/* 1374 */     return this.unknown;
/*      */   }
/*      */   
/*      */   public String getGroup() {
/* 1378 */     return this.group;
/*      */   }
/*      */   
/*      */   public String getBlockSoundSetId() {
/* 1382 */     return this.blockSoundSetId;
/*      */   }
/*      */   
/*      */   public int getBlockSoundSetIndex() {
/* 1386 */     return this.blockSoundSetIndex;
/*      */   }
/*      */   
/*      */   public ModelParticle[] getParticles() {
/* 1390 */     return this.particles;
/*      */   }
/*      */   
/*      */   public String getBlockParticleSetId() {
/* 1394 */     return this.blockParticleSetId;
/*      */   }
/*      */   
/*      */   public String getBlockBreakingDecalId() {
/* 1398 */     return this.blockBreakingDecalId;
/*      */   }
/*      */   
/*      */   public Color getParticleColor() {
/* 1402 */     return this.particleColor;
/*      */   }
/*      */   
/*      */   public TickProcedure getTickProcedure() {
/* 1406 */     return this.tickProcedure;
/*      */   }
/*      */   
/*      */   public ShaderType[] getEffect() {
/* 1410 */     return this.effect;
/*      */   }
/*      */   
/*      */   public BlockTypeTextures[] getTextures() {
/* 1414 */     return this.textures;
/*      */   }
/*      */   
/*      */   public String getTextureSideMask() {
/* 1418 */     return this.textureSideMask;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public CustomModelTexture[] getCustomModelTexture() {
/* 1423 */     return this.customModelTexture;
/*      */   }
/*      */   
/*      */   public DrawType getDrawType() {
/* 1427 */     return this.drawType;
/*      */   }
/*      */   
/*      */   public BlockMaterial getMaterial() {
/* 1431 */     return this.material;
/*      */   }
/*      */   
/*      */   public Opacity getOpacity() {
/* 1435 */     return this.opacity;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getCustomModel() {
/* 1440 */     return this.customModel;
/*      */   }
/*      */   
/*      */   public float getCustomModelScale() {
/* 1444 */     return this.customModelScale;
/*      */   }
/*      */   
/*      */   public String getCustomModelAnimation() {
/* 1448 */     return this.customModelAnimation;
/*      */   }
/*      */   
/*      */   public Color[] getTintUp() {
/* 1452 */     return this.tintUp;
/*      */   }
/*      */   
/*      */   public Color[] getTintDown() {
/* 1456 */     return this.tintDown;
/*      */   }
/*      */   
/*      */   public Color[] getTintNorth() {
/* 1460 */     return this.tintNorth;
/*      */   }
/*      */   
/*      */   public Color[] getTintSouth() {
/* 1464 */     return this.tintSouth;
/*      */   }
/*      */   
/*      */   public Color[] getTintWest() {
/* 1468 */     return this.tintWest;
/*      */   }
/*      */   
/*      */   public Color[] getTintEast() {
/* 1472 */     return this.tintEast;
/*      */   }
/*      */   
/*      */   public int getBiomeTintUp() {
/* 1476 */     return this.biomeTintUp;
/*      */   }
/*      */   
/*      */   public int getBiomeTintDown() {
/* 1480 */     return this.biomeTintDown;
/*      */   }
/*      */   
/*      */   public int getBiomeTintNorth() {
/* 1484 */     return this.biomeTintNorth;
/*      */   }
/*      */   
/*      */   public int getBiomeTintSouth() {
/* 1488 */     return this.biomeTintSouth;
/*      */   }
/*      */   
/*      */   public int getBiomeTintWest() {
/* 1492 */     return this.biomeTintWest;
/*      */   }
/*      */   
/*      */   public int getBiomeTintEast() {
/* 1496 */     return this.biomeTintEast;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ConnectedBlockRuleSet getConnectedBlockRuleSet() {
/* 1506 */     return this.connectedBlockRuleSet;
/*      */   }
/*      */   
/*      */   public BlockSupportsRequiredForType getBlockSupportsRequiredFor() {
/* 1510 */     return this.blockSupportsRequiredFor;
/*      */   }
/*      */   
/*      */   public RandomRotation getRandomRotation() {
/* 1514 */     return this.randomRotation;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public VariantRotation getVariantRotation() {
/* 1519 */     return this.variantRotation;
/*      */   }
/*      */   
/*      */   public BlockFlipType getFlipType() {
/* 1523 */     return this.flipType;
/*      */   }
/*      */   
/*      */   public Rotation getRotationYawPlacementOffset() {
/* 1527 */     return this.rotationYawPlacementOffset;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RotatedMountPointsArray getSeats() {
/* 1532 */     return this.seats;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RotatedMountPointsArray getBeds() {
/* 1537 */     return this.beds;
/*      */   }
/*      */   
/*      */   public String getTransitionTexture() {
/* 1541 */     return this.transitionTexture;
/*      */   }
/*      */   
/*      */   public String[] getTransitionToGroups() {
/* 1545 */     return this.transitionToGroups;
/*      */   }
/*      */   
/*      */   public String getBlockListAssetId() {
/* 1549 */     return this.blockListAssetId;
/*      */   }
/*      */   
/*      */   public String getPrefabListAssetId() {
/* 1553 */     return this.prefabListAssetId;
/*      */   }
/*      */   
/*      */   public String getHitboxType() {
/* 1557 */     return this.hitboxType;
/*      */   }
/*      */   
/*      */   public int getHitboxTypeIndex() {
/* 1561 */     return this.hitboxTypeIndex;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getInteractionHitboxType() {
/* 1566 */     return this.interactionHitboxType;
/*      */   }
/*      */   
/*      */   public int getInteractionHitboxTypeIndex() {
/* 1570 */     return this.interactionHitboxTypeIndex;
/*      */   }
/*      */   
/*      */   public ColorLight getLight() {
/* 1574 */     return this.light;
/*      */   }
/*      */   
/*      */   public BlockMovementSettings getMovementSettings() {
/* 1578 */     return this.movementSettings;
/*      */   }
/*      */   
/*      */   public BlockFlags getFlags() {
/* 1582 */     return this.flags;
/*      */   }
/*      */   
/*      */   public String getInteractionHint() {
/* 1586 */     return this.interactionHint;
/*      */   }
/*      */   
/*      */   public boolean isTrigger() {
/* 1590 */     return this.isTrigger;
/*      */   }
/*      */   
/*      */   public int getDamageToEntities() {
/* 1594 */     return this.damageToEntities;
/*      */   }
/*      */   
/*      */   public Bench getBench() {
/* 1598 */     return this.bench;
/*      */   }
/*      */   
/*      */   public BlockGathering getGathering() {
/* 1602 */     return this.gathering;
/*      */   }
/*      */   
/*      */   public BlockPlacementSettings getPlacementSettings() {
/* 1606 */     return this.placementSettings;
/*      */   }
/*      */   
/*      */   public StateData getState() {
/* 1610 */     return this.state;
/*      */   }
/*      */   
/*      */   public Holder<ChunkStore> getBlockEntity() {
/* 1614 */     return this.blockEntity;
/*      */   }
/*      */   
/*      */   public String getAmbientSoundEventId() {
/* 1618 */     return this.ambientSoundEventId;
/*      */   }
/*      */   
/*      */   public int getAmbientSoundEventIndex() {
/* 1622 */     return this.ambientSoundEventIndex;
/*      */   }
/*      */   
/*      */   public String getInteractionSoundEventId() {
/* 1626 */     return this.interactionSoundEventId;
/*      */   }
/*      */   
/*      */   public int getInteractionSoundEventIndex() {
/* 1630 */     return this.interactionSoundEventIndex;
/*      */   }
/*      */   
/*      */   public boolean isLooping() {
/* 1634 */     return this.isLooping;
/*      */   }
/*      */   
/*      */   public FarmingData getFarming() {
/* 1638 */     return this.farming;
/*      */   }
/*      */   
/*      */   public SupportDropType getSupportDropType() {
/* 1642 */     return this.supportDropType;
/*      */   }
/*      */   
/*      */   public int getMaxSupportDistance() {
/* 1646 */     return this.maxSupportDistance;
/*      */   }
/*      */   
/*      */   public boolean isFullySupportive() {
/* 1650 */     return (this.supporting == ALL_SUPPORTING_FACES);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Map<BlockFace, RequiredBlockFaceSupport[]> getSupport(int rotationIndex) {
/* 1655 */     if (this.support == null || rotationIndex == 0) return this.support;
/*      */ 
/*      */     
/* 1658 */     if (this.rotatedSupport == null)
/*      */     {
/* 1660 */       this.rotatedSupport = (Map<BlockFace, RequiredBlockFaceSupport[]>[])new Map[RotationTuple.VALUES.length];
/*      */     }
/* 1662 */     Map<BlockFace, RequiredBlockFaceSupport[]> rotatedSupportArray = this.rotatedSupport[rotationIndex];
/* 1663 */     if (rotatedSupportArray == null) {
/* 1664 */       RotationTuple rotation = RotationTuple.get(rotationIndex);
/* 1665 */       Map<BlockFace, List<RequiredBlockFaceSupport>> rotatedSupport = new EnumMap<>(BlockFace.class);
/* 1666 */       for (Map.Entry<BlockFace, RequiredBlockFaceSupport[]> entry : this.support.entrySet()) {
/* 1667 */         BlockFace blockFace = entry.getKey();
/* 1668 */         RequiredBlockFaceSupport[] requiredBlockFaceSupports = entry.getValue();
/*      */         
/* 1670 */         BlockFace rotatedBlockFace = BlockFace.rotate(blockFace, rotation.yaw(), rotation.pitch(), rotation.roll());
/*      */         
/* 1672 */         for (RequiredBlockFaceSupport requiredBlockFaceSupport : requiredBlockFaceSupports) {
/* 1673 */           if (requiredBlockFaceSupport.isRotated()) {
/* 1674 */             RequiredBlockFaceSupport rotatedRequiredBlockFaceSupport = RequiredBlockFaceSupport.rotate(requiredBlockFaceSupport, rotation.yaw(), rotation.pitch(), rotation.roll());
/* 1675 */             ((List<RequiredBlockFaceSupport>)rotatedSupport.computeIfAbsent(rotatedBlockFace, k -> new ObjectArrayList())).add(rotatedRequiredBlockFaceSupport);
/*      */           } else {
/* 1677 */             ((List<RequiredBlockFaceSupport>)rotatedSupport.computeIfAbsent(blockFace, k -> new ObjectArrayList())).add(requiredBlockFaceSupport);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1682 */       rotatedSupportArray = (Map)new EnumMap<>(BlockFace.class);
/* 1683 */       for (Map.Entry<BlockFace, List<RequiredBlockFaceSupport>> entry : rotatedSupport.entrySet()) {
/* 1684 */         rotatedSupportArray.put(entry.getKey(), (RequiredBlockFaceSupport[])((List)entry.getValue()).toArray(x$0 -> new RequiredBlockFaceSupport[x$0]));
/*      */       }
/*      */       
/* 1687 */       this.rotatedSupport[rotationIndex] = rotatedSupportArray;
/*      */     } 
/* 1689 */     return rotatedSupportArray;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Map<BlockFace, BlockFaceSupport[]> getSupporting(int rotationIndex) {
/* 1694 */     if (this.supporting == null || rotationIndex == 0) return this.supporting;
/*      */ 
/*      */     
/* 1697 */     if (this.rotatedSupporting == null)
/*      */     {
/* 1699 */       this.rotatedSupporting = (Map<BlockFace, BlockFaceSupport[]>[])new Map[RotationTuple.VALUES.length];
/*      */     }
/* 1701 */     Map<BlockFace, BlockFaceSupport[]> rotatedSupportingArray = this.rotatedSupporting[rotationIndex];
/* 1702 */     if (rotatedSupportingArray == null) {
/* 1703 */       RotationTuple rotation = RotationTuple.get(rotationIndex);
/* 1704 */       if (isFullySupportive()) {
/* 1705 */         rotatedSupportingArray = ALL_SUPPORTING_FACES;
/*      */       } else {
/* 1707 */         Map<BlockFace, List<BlockFaceSupport>> rotatedSupporting = new EnumMap<>(BlockFace.class);
/* 1708 */         for (Map.Entry<BlockFace, BlockFaceSupport[]> entry : this.supporting.entrySet()) {
/* 1709 */           BlockFace blockFace = entry.getKey();
/* 1710 */           BlockFaceSupport[] blockFaceSupports = entry.getValue();
/*      */           
/* 1712 */           BlockFace rotatedBlockFace = BlockFace.rotate(blockFace, rotation.yaw(), rotation.pitch(), rotation.roll());
/*      */           
/* 1714 */           for (BlockFaceSupport blockFaceSupport : blockFaceSupports) {
/* 1715 */             BlockFaceSupport rotatedBlockFaceSupport = BlockFaceSupport.rotate(blockFaceSupport, rotation.yaw(), rotation.pitch(), rotation.roll());
/* 1716 */             ((List<BlockFaceSupport>)rotatedSupporting.computeIfAbsent(rotatedBlockFace, k -> new ObjectArrayList())).add(rotatedBlockFaceSupport);
/*      */           } 
/*      */         } 
/*      */         
/* 1720 */         rotatedSupportingArray = (Map)new EnumMap<>(BlockFace.class);
/* 1721 */         for (Map.Entry<BlockFace, List<BlockFaceSupport>> entry : rotatedSupporting.entrySet()) {
/* 1722 */           rotatedSupportingArray.put(entry.getKey(), (BlockFaceSupport[])((List)entry.getValue()).toArray(x$0 -> new BlockFaceSupport[x$0]));
/*      */         }
/*      */       } 
/*      */       
/* 1726 */       this.rotatedSupporting[rotationIndex] = rotatedSupportingArray;
/*      */     } 
/* 1728 */     return rotatedSupportingArray;
/*      */   }
/*      */   
/*      */   public boolean hasSupport() {
/* 1732 */     return ((this.support != null && !this.support.isEmpty()) || this.maxSupportDistance > 0);
/*      */   }
/*      */   
/*      */   public boolean isAllowsMultipleUsers() {
/* 1736 */     return this.allowsMultipleUsers;
/*      */   }
/*      */   
/*      */   public Map<InteractionType, String> getInteractions() {
/* 1740 */     return this.interactions;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RailConfig getRailConfig(int rotationIndex) {
/* 1745 */     if (this.railConfig == null || rotationIndex == 0) return this.railConfig;
/*      */ 
/*      */     
/* 1748 */     if (this.rotatedRailConfig == null) {
/* 1749 */       this.rotatedRailConfig = new RailConfig[RotationTuple.VALUES.length];
/*      */     }
/* 1751 */     RailConfig rotatedRail = this.rotatedRailConfig[rotationIndex];
/* 1752 */     if (rotatedRail == null) {
/* 1753 */       RotationTuple rotation = RotationTuple.get(rotationIndex);
/*      */       
/* 1755 */       rotatedRail = new RailConfig(this.railConfig);
/* 1756 */       for (RailPoint p : rotatedRail.points) {
/* 1757 */         Vector3f hyPoint = new Vector3f(p.point.x - 0.5F, p.point.y - 0.5F, p.point.z - 0.5F);
/* 1758 */         hyPoint = Rotation.rotate(hyPoint, rotation.yaw(), rotation.pitch(), rotation.roll());
/* 1759 */         p.point.x = hyPoint.x + 0.5F;
/* 1760 */         p.point.y = hyPoint.y + 0.5F;
/* 1761 */         p.point.z = hyPoint.z + 0.5F;
/*      */         
/* 1763 */         Vector3f hyNormal = new Vector3f(p.normal.x, p.normal.y, p.normal.z);
/* 1764 */         hyNormal = Rotation.rotate(hyNormal, rotation.yaw(), rotation.pitch(), rotation.roll());
/* 1765 */         p.normal.x = hyNormal.x;
/* 1766 */         p.normal.y = hyNormal.y;
/* 1767 */         p.normal.z = hyNormal.z;
/*      */       } 
/*      */       
/* 1770 */       this.rotatedRailConfig[rotationIndex] = rotatedRail;
/*      */     } 
/* 1772 */     return rotatedRail;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public boolean isDoor() {
/* 1777 */     return this.isDoor;
/*      */   }
/*      */   
/*      */   public boolean shouldIgnoreSupportWhenPlaced() {
/* 1781 */     return this.ignoreSupportWhenPlaced;
/*      */   }
/*      */   
/*      */   public boolean canBePlacedAsDeco() {
/* 1785 */     return (this.ignoreSupportWhenPlaced || (this.gathering != null && this.gathering.shouldUseDefaultDropWhenPlaced()));
/*      */   }
/*      */   
/*      */   protected void processConfig() {
/* 1789 */     if (this.bench != null)
/* 1790 */     { if (this.state == null && this.bench.getType() == BenchType.Processing) {
/* 1791 */         this.state = new StateData("processingBench");
/*      */       }
/*      */       
/* 1794 */       this.flags.isUsable = true;
/* 1795 */       if (this.interactionHint == null) this.interactionHint = "server.interactionHints.open";  }
/* 1796 */     else if (this.state != null && ("container".equalsIgnoreCase(this.state.getId()) || "Door".equalsIgnoreCase(this.state.getId())))
/* 1797 */     { this.flags.isUsable = true;
/* 1798 */       if (this.interactionHint == null) this.interactionHint = "server.interactionHints.open";  }
/* 1799 */     else if (this.gathering != null && this.gathering.isHarvestable())
/* 1800 */     { this.flags.isUsable = true;
/* 1801 */       if (this.interactionHint == null) this.interactionHint = "server.interactionHints.gather";  }
/* 1802 */     else if (this.seats != null && this.seats.size() > 0 && 
/* 1803 */       this.interactionHint == null) { this.interactionHint = "server.interactionHints.sit"; }
/*      */ 
/*      */     
/* 1806 */     if (this.interactions.containsKey(InteractionType.Use)) {
/* 1807 */       this.flags.isUsable = true;
/* 1808 */       if (this.interactionHint == null) this.interactionHint = "server.interactionHints.generic";
/*      */     
/*      */     } 
/* 1811 */     if (this.flags.isUsable && this.interactionHint == null) {
/* 1812 */       this.interactionHint = "server.interactionHints.generic";
/*      */     }
/*      */     
/* 1815 */     if (this.ambientSoundEventId != null) {
/* 1816 */       this.ambientSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.ambientSoundEventId);
/*      */     }
/*      */     
/* 1819 */     if (this.interactionSoundEventId != null) {
/* 1820 */       this.interactionSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.interactionSoundEventId);
/*      */     }
/*      */ 
/*      */     
/* 1824 */     if (this.support == null && this.material == BlockMaterial.Empty && 
/* 1825 */       !"@Tech".equals(this.group)) {
/* 1826 */       this.support = REQUIRED_BOTTOM_FACE_SUPPORT;
/*      */     }
/*      */ 
/*      */     
/* 1830 */     if (this.supporting == null && (this.drawType == DrawType.Cube || this.drawType == DrawType.CubeWithModel || this.drawType == DrawType.GizmoCube) && this.material == BlockMaterial.Solid) {
/*      */ 
/*      */       
/* 1833 */       this.supporting = ALL_SUPPORTING_FACES;
/* 1834 */     } else if (this.supporting == null) {
/* 1835 */       this.supporting = Collections.emptyMap();
/*      */     } 
/*      */     
/* 1838 */     this.hitboxTypeIndex = this.hitboxType.equals("Full") ? 0 : BlockBoundingBoxes.getAssetMap().getIndex(this.hitboxType);
/* 1839 */     if (this.hitboxTypeIndex == Integer.MIN_VALUE) {
/* 1840 */       HytaleLogger.getLogger().at(Level.WARNING).log("Unknown hitbox '%s' for block '%s', using default", this.hitboxType, getId());
/* 1841 */       this.hitboxTypeIndex = 0;
/*      */     } 
/*      */     
/* 1844 */     if (this.interactionHitboxType != null) {
/* 1845 */       this.interactionHitboxTypeIndex = this.interactionHitboxType.equals("Full") ? 0 : BlockBoundingBoxes.getAssetMap().getIndex(this.interactionHitboxType);
/* 1846 */       if (this.interactionHitboxTypeIndex == Integer.MIN_VALUE) {
/* 1847 */         HytaleLogger.getLogger().at(Level.WARNING).log("Unknown interaction hitbox '%s' for block '%s', using collision hitbox", this.interactionHitboxType, getId());
/* 1848 */         this.interactionHitboxTypeIndex = this.hitboxTypeIndex;
/*      */       } 
/*      */     } 
/*      */     
/* 1852 */     this.blockSoundSetIndex = this.blockSoundSetId.equals("EMPTY") ? 0 : BlockSoundSet.getAssetMap().getIndex(this.blockSoundSetId);
/* 1853 */     if (this.blockSoundSetIndex == Integer.MIN_VALUE) {
/* 1854 */       HytaleLogger.getLogger().at(Level.WARNING).log("Unknown block sound set '%s' for block '%s', using empty", this.blockSoundSetId, getId());
/* 1855 */       this.blockSoundSetIndex = 0;
/*      */     } 
/*      */     
/* 1858 */     for (InteractionType type : this.interactions.keySet()) {
/* 1859 */       if (InteractionTypeUtils.isCollisionType(type)) {
/* 1860 */         this.isTrigger = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1865 */     if (this.bench != null && !this.interactions.containsKey(InteractionType.Use)) {
/* 1866 */       Map<InteractionType, String> interactions = this.interactions.isEmpty() ? new EnumMap<>(InteractionType.class) : new EnumMap<>(this.interactions);
/*      */       
/* 1868 */       RootInteraction rootInteraction = this.bench.getRootInteraction();
/* 1869 */       if (rootInteraction != null) interactions.put(InteractionType.Use, rootInteraction.getId());
/*      */       
/* 1871 */       this.interactions = Collections.unmodifiableMap(interactions);
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static BlockType getUnknownFor(String blockTypeKey) {
/* 1877 */     return UNKNOWN.clone(blockTypeKey);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getBlockCenter(int rotationIndex, @Nonnull Vector3d outCenter) {
/* 1887 */     BlockBoundingBoxes hitboxAsset = (BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(this.hitboxTypeIndex);
/* 1888 */     if (hitboxAsset == null) throw new IllegalStateException("Unknown hitbox: " + this.hitboxType);
/*      */     
/* 1890 */     BlockBoundingBoxes.RotatedVariantBoxes rotatedHitbox = hitboxAsset.get(rotationIndex);
/* 1891 */     Box boundingBox = rotatedHitbox.getBoundingBox();
/* 1892 */     outCenter.assign(boundingBox.middleX(), boundingBox.middleY(), boundingBox.middleZ());
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String toString() {
/* 1898 */     return "BlockType{id=" + this.id + ", unknown=" + this.unknown + ", group='" + this.group + "', blockSoundSetId='" + this.blockSoundSetId + "', blockSoundSetIndex=" + this.blockSoundSetIndex + ", particles=" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1904 */       Arrays.toString((Object[])this.particles) + ", blockParticleSetId='" + this.blockParticleSetId + "', blockBreakingDecalId='" + this.blockBreakingDecalId + "', particleColor=" + String.valueOf(this.particleColor) + ", effect=" + 
/*      */ 
/*      */ 
/*      */       
/* 1908 */       Arrays.toString((Object[])this.effect) + ", textures=" + 
/* 1909 */       Arrays.toString((Object[])this.textures) + ", textureSideMask='" + this.textureSideMask + "', cubeShadingMode=" + String.valueOf(this.cubeShadingMode) + ", customModel='" + this.customModel + "', customModelTexture=" + 
/*      */ 
/*      */ 
/*      */       
/* 1913 */       Arrays.toString((Object[])this.customModelTexture) + ", customModelScale=" + this.customModelScale + ", customModelAnimation='" + this.customModelAnimation + "', drawType=" + String.valueOf(this.drawType) + ", material=" + String.valueOf(this.material) + ", opacity=" + String.valueOf(this.opacity) + ", requiresAlphaBlending=" + this.requiresAlphaBlending + ", tickProcedure" + String.valueOf(this.tickProcedure) + ", tintUp=" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1921 */       Arrays.toString((Object[])this.tintUp) + ", tintDown=" + 
/* 1922 */       Arrays.toString((Object[])this.tintDown) + ", tintNorth=" + 
/* 1923 */       Arrays.toString((Object[])this.tintNorth) + ", tintSouth=" + 
/* 1924 */       Arrays.toString((Object[])this.tintSouth) + ", tintWest=" + 
/* 1925 */       Arrays.toString((Object[])this.tintWest) + ", tintEast=" + 
/* 1926 */       Arrays.toString((Object[])this.tintEast) + ", biomeTintUp=" + this.biomeTintUp + ", biomeTintDown=" + this.biomeTintDown + ", biomeTintNorth=" + this.biomeTintNorth + ", biomeTintSouth=" + this.biomeTintSouth + ", biomeTintWest=" + this.biomeTintWest + ", biomeTintEast=" + this.biomeTintEast + ", randomRotation=" + String.valueOf(this.randomRotation) + ", variantRotation=" + String.valueOf(this.variantRotation) + ", flipType=" + String.valueOf(this.flipType) + ", rotationYawPlacementOffset=" + String.valueOf(this.rotationYawPlacementOffset) + ", transitionTexture='" + this.transitionTexture + "', transitionToGroups=" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1938 */       Arrays.toString((Object[])this.transitionToGroups) + ", hitboxType='" + this.hitboxType + "', hitboxTypeIndex=" + this.hitboxTypeIndex + ", interactionHitboxType='" + this.interactionHitboxType + "', interactionHitboxTypeIndex=" + this.interactionHitboxTypeIndex + ", light=" + String.valueOf(this.light) + ", movementSettings=" + String.valueOf(this.movementSettings) + ", flags=" + String.valueOf(this.flags) + ", interactionHint='" + this.interactionHint + "', isTrigger=" + this.isTrigger + ", damageToEntities=" + this.damageToEntities + ", allowsMultipleUsers=" + this.allowsMultipleUsers + ", bench=" + String.valueOf(this.bench) + ", gathering=" + String.valueOf(this.gathering) + ", placementSettings=" + String.valueOf(this.placementSettings) + ", state=" + String.valueOf(this.state) + ", ambientSoundEventId='" + this.ambientSoundEventId + "', ambientSoundEventIndex='" + this.ambientSoundEventIndex + "', interactionSoundEventId='" + this.interactionSoundEventId + "', interactionSoundEventIndex='" + this.interactionSoundEventIndex + "', isLooping=" + this.isLooping + ", farming=" + String.valueOf(this.farming) + ", supportDropType=" + String.valueOf(this.supportDropType) + ", maxSupportDistance=" + this.maxSupportDistance + ", support=" + String.valueOf(this.support) + ", supporting=" + String.valueOf(this.supporting) + ", interactions=" + String.valueOf(this.interactions) + ", railConfig=" + String.valueOf(this.railConfig) + "}";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public BlockType clone(String newKey) {
/* 1971 */     if (this.id != null && this.id.equals(newKey)) return this; 
/* 1972 */     BlockType blockType = new BlockType(this);
/* 1973 */     blockType.id = newKey;
/* 1974 */     blockType.cachedPacket = null;
/* 1975 */     return blockType;
/*      */   }
/*      */   
/*      */   public static int getBlockIdOrUnknown(@Nonnull String blockTypeKey, String message, Object... params) {
/* 1979 */     return getBlockIdOrUnknown(getAssetMap(), blockTypeKey, message, params);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBlockIdOrUnknown(@Nonnull BlockTypeAssetMap<String, BlockType> assetMap, @Nonnull String blockTypeKey, String message, Object... params) {
/* 1984 */     int blockId = assetMap.getIndex(blockTypeKey);
/*      */ 
/*      */     
/* 1987 */     if (blockId == Integer.MIN_VALUE) {
/* 1988 */       HytaleLogger.getLogger().at(Level.WARNING).logVarargs(message, params);
/* 1989 */       AssetRegistry.getAssetStore(BlockType.class).loadAssets("Hytale:Hytale", 
/*      */           
/* 1991 */           Collections.singletonList(getUnknownFor(blockTypeKey)), AssetUpdateQuery.DEFAULT_NO_REBUILD);
/*      */ 
/*      */       
/* 1994 */       int index = assetMap.getIndex(blockTypeKey);
/* 1995 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + blockTypeKey); 
/* 1996 */       blockId = index;
/*      */     } 
/*      */     
/* 1999 */     return blockId;
/*      */   }
/*      */   
/*      */   public BlockType() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\BlockType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */