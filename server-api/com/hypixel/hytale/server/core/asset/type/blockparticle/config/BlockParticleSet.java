/*     */ package com.hypixel.hytale.server.core.asset.type.blockparticle.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.BlockParticleEvent;
/*     */ import com.hypixel.hytale.protocol.BlockParticleSet;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Map;
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
/*     */ public class BlockParticleSet
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BlockParticleSet>>, NetworkSerializable<BlockParticleSet>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, BlockParticleSet> CODEC;
/*     */   private static AssetStore<String, BlockParticleSet, DefaultAssetMap<String, BlockParticleSet>> ASSET_STORE;
/*     */   
/*     */   static {
/*  82 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockParticleSet.class, BlockParticleSet::new, (Codec)Codec.STRING, (blockParticleSet, s) -> blockParticleSet.id = s, blockParticleSet -> blockParticleSet.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("Particle Systems that can be spawned in relation to block events.")).appendInherited(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR), (blockParticleSet, s) -> blockParticleSet.color = s, blockParticleSet -> blockParticleSet.color, (blockParticleSet, parent) -> blockParticleSet.color = parent.color).documentation("The colour used if none was specified in the particle settings.").add()).appendInherited(new KeyedCodec("Scale", (Codec)Codec.FLOAT), (blockParticleSet, f) -> blockParticleSet.scale = f.floatValue(), blockParticleSet -> Float.valueOf(blockParticleSet.scale), (blockParticleSet, parent) -> blockParticleSet.scale = parent.scale).documentation("The scale of the particle system.").add()).appendInherited(new KeyedCodec("PositionOffset", (Codec)ProtocolCodecs.VECTOR3F), (blockParticleSet, s) -> blockParticleSet.positionOffset = s, blockParticleSet -> blockParticleSet.positionOffset, (blockParticleSet, parent) -> blockParticleSet.positionOffset = parent.positionOffset).documentation("The position offset from the spawn position.").add()).appendInherited(new KeyedCodec("RotationOffset", (Codec)ProtocolCodecs.DIRECTION), (blockParticleSet, s) -> blockParticleSet.rotationOffset = s, blockParticleSet -> blockParticleSet.rotationOffset, (blockParticleSet, parent) -> blockParticleSet.rotationOffset = parent.rotationOffset).documentation("The rotation offset from the spawn rotation.").add()).appendInherited(new KeyedCodec("Particles", (Codec)new EnumMapCodec(BlockParticleEvent.class, (Codec)Codec.STRING)), (blockParticleSet, s) -> blockParticleSet.particleSystemIds = s, blockParticleSet -> blockParticleSet.particleSystemIds, (blockParticleSet, parent) -> blockParticleSet.particleSystemIds = parent.particleSystemIds).addValidator(Validators.nonNull()).addValidator((Validator)ParticleSystem.VALIDATOR_CACHE.getMapValueValidator()).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static AssetStore<String, BlockParticleSet, DefaultAssetMap<String, BlockParticleSet>> getAssetStore() {
/*  87 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BlockParticleSet.class); 
/*  88 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, BlockParticleSet> getAssetMap() {
/*  92 */     return (DefaultAssetMap<String, BlockParticleSet>)getAssetStore().getAssetMap();
/*     */   }
/*     */   
/*  95 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BlockParticleSet::getAssetStore));
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   protected String id;
/*     */   protected Color color;
/* 101 */   protected float scale = 1.0F;
/*     */   
/*     */   protected Vector3f positionOffset;
/*     */   protected Direction rotationOffset;
/*     */   protected Map<BlockParticleEvent, String> particleSystemIds;
/*     */   private SoftReference<BlockParticleSet> cachedPacket;
/*     */   
/*     */   public BlockParticleSet(String id, Color color, float scale, Vector3f positionOffset, Direction rotationOffset, Map<BlockParticleEvent, String> particleSystemIds) {
/* 109 */     this.id = id;
/* 110 */     this.color = color;
/* 111 */     this.scale = scale;
/* 112 */     this.positionOffset = positionOffset;
/* 113 */     this.rotationOffset = rotationOffset;
/* 114 */     this.particleSystemIds = particleSystemIds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockParticleSet toPacket() {
/* 123 */     BlockParticleSet cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 124 */     if (cached != null) return cached;
/*     */     
/* 126 */     BlockParticleSet packet = new BlockParticleSet();
/*     */     
/* 128 */     packet.id = this.id;
/* 129 */     packet.color = this.color;
/* 130 */     packet.scale = this.scale;
/* 131 */     packet.positionOffset = this.positionOffset;
/* 132 */     packet.rotationOffset = this.rotationOffset;
/*     */     
/* 134 */     packet.particleSystemIds = this.particleSystemIds;
/*     */     
/* 136 */     this.cachedPacket = new SoftReference<>(packet);
/* 137 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 142 */     return this.id;
/*     */   }
/*     */   
/*     */   public Color getColor() {
/* 146 */     return this.color;
/*     */   }
/*     */   
/*     */   public float getScale() {
/* 150 */     return this.scale;
/*     */   }
/*     */   
/*     */   public Vector3f getPositionOffset() {
/* 154 */     return this.positionOffset;
/*     */   }
/*     */   
/*     */   public Direction getRotationOffset() {
/* 158 */     return this.rotationOffset;
/*     */   }
/*     */   
/*     */   public Map<BlockParticleEvent, String> getParticleSystemIds() {
/* 162 */     return this.particleSystemIds;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 168 */     return "BlockParticleSet{id='" + this.id + "', color=" + String.valueOf(this.color) + ", scale=" + this.scale + ", positionOffset=" + String.valueOf(this.positionOffset) + ", rotationOffset=" + String.valueOf(this.rotationOffset) + ", particleSystemIds=" + String.valueOf(this.particleSystemIds) + "}";
/*     */   }
/*     */   
/*     */   protected BlockParticleSet() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blockparticle\config\BlockParticleSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */