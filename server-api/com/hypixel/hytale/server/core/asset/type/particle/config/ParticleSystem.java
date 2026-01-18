/*     */ package com.hypixel.hytale.server.core.asset.type.particle.config;
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
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UITypeIcon;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.ParticleSpawnerGroup;
/*     */ import com.hypixel.hytale.protocol.ParticleSystem;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
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
/*     */ public class ParticleSystem
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ParticleSystem>>, NetworkSerializable<ParticleSystem>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, ParticleSystem> CODEC;
/*     */   
/*     */   static {
/*  67 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ParticleSystem.class, ParticleSystem::new, (Codec)Codec.STRING, (particleSystem, s) -> particleSystem.id = s, particleSystem -> particleSystem.id, (asset, data) -> asset.data = data, asset -> asset.data).metadata((Metadata)new UITypeIcon("ParticleSystem.png"))).appendInherited(new KeyedCodec("Spawners", (Codec)new ArrayCodec((Codec)ParticleSpawnerGroup.CODEC, x$0 -> new ParticleSpawnerGroup[x$0])), (particleSystem, o) -> particleSystem.spawners = o, particleSystem -> particleSystem.spawners, (particleSystem, parent) -> particleSystem.spawners = parent.spawners).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).addValidator(Validators.nonEmptyArray()).add()).appendInherited(new KeyedCodec("LifeSpan", (Codec)Codec.FLOAT), (particleSystem, f) -> particleSystem.lifeSpan = f.floatValue(), particleSystem -> Float.valueOf(particleSystem.lifeSpan), (particleSystem, parent) -> particleSystem.lifeSpan = parent.lifeSpan).add()).appendInherited(new KeyedCodec("CullDistance", (Codec)Codec.FLOAT), (particleSystem, f) -> particleSystem.cullDistance = f.floatValue(), particleSystem -> Float.valueOf(particleSystem.cullDistance), (particleSystem, parent) -> particleSystem.cullDistance = parent.cullDistance).add()).appendInherited(new KeyedCodec("BoundingRadius", (Codec)Codec.FLOAT), (particleSystem, f) -> particleSystem.boundingRadius = f.floatValue(), particleSystem -> Float.valueOf(particleSystem.boundingRadius), (particleSystem, parent) -> particleSystem.boundingRadius = parent.boundingRadius).add()).appendInherited(new KeyedCodec("IsImportant", (Codec)Codec.BOOLEAN), (particleSystem, b) -> particleSystem.isImportant = b.booleanValue(), particleSystem -> Boolean.valueOf(particleSystem.isImportant), (particleSystem, parent) -> particleSystem.isImportant = parent.isImportant).add()).build();
/*     */   }
/*  69 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ParticleSystem::getAssetStore)); private static AssetStore<String, ParticleSystem, DefaultAssetMap<String, ParticleSystem>> ASSET_STORE; protected AssetExtraInfo.Data data; protected String id;
/*     */   protected float lifeSpan;
/*     */   
/*     */   public static AssetStore<String, ParticleSystem, DefaultAssetMap<String, ParticleSystem>> getAssetStore() {
/*  73 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ParticleSystem.class); 
/*  74 */     return ASSET_STORE;
/*     */   }
/*     */   protected ParticleSpawnerGroup[] spawners; protected float cullDistance; protected float boundingRadius; protected boolean isImportant; private SoftReference<ParticleSystem> cachedPacket;
/*     */   public static DefaultAssetMap<String, ParticleSystem> getAssetMap() {
/*  78 */     return (DefaultAssetMap<String, ParticleSystem>)getAssetStore().getAssetMap();
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
/*     */   public ParticleSystem(String id, float lifeSpan, ParticleSpawnerGroup[] spawners, float cullDistance, float boundingRadius, boolean isImportant) {
/*  95 */     this.id = id;
/*  96 */     this.lifeSpan = lifeSpan;
/*  97 */     this.spawners = spawners;
/*  98 */     this.cullDistance = cullDistance;
/*  99 */     this.boundingRadius = boundingRadius;
/* 100 */     this.isImportant = isImportant;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ParticleSystem() {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ParticleSystem toPacket() {
/* 109 */     ParticleSystem cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 110 */     if (cached != null) return cached;
/*     */     
/* 112 */     ParticleSystem packet = new ParticleSystem();
/* 113 */     packet.id = this.id;
/* 114 */     packet.lifeSpan = this.lifeSpan;
/*     */     
/* 116 */     if (this.spawners != null && this.spawners.length > 0) {
/* 117 */       packet.spawners = (ParticleSpawnerGroup[])ArrayUtil.copyAndMutate((Object[])this.spawners, ParticleSpawnerGroup::toPacket, x$0 -> new ParticleSpawnerGroup[x$0]);
/*     */     }
/*     */     
/* 120 */     packet.cullDistance = this.cullDistance;
/* 121 */     packet.boundingRadius = this.boundingRadius;
/* 122 */     packet.isImportant = this.isImportant;
/*     */     
/* 124 */     this.cachedPacket = new SoftReference<>(packet);
/* 125 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 130 */     return this.id;
/*     */   }
/*     */   
/*     */   public float getLifeSpan() {
/* 134 */     return this.lifeSpan;
/*     */   }
/*     */   
/*     */   public ParticleSpawnerGroup[] getSpawners() {
/* 138 */     return this.spawners;
/*     */   }
/*     */   
/*     */   public float getCullDistance() {
/* 142 */     return this.cullDistance;
/*     */   }
/*     */   
/*     */   public float getBoundingRadius() {
/* 146 */     return this.boundingRadius;
/*     */   }
/*     */   
/*     */   public boolean isImportant() {
/* 150 */     return this.isImportant;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 156 */     return "ParticleSystem{id='" + this.id + "', lifeSpan=" + this.lifeSpan + ", spawners=" + 
/*     */ 
/*     */       
/* 159 */       Arrays.toString((Object[])this.spawners) + ", cullDistance=" + this.cullDistance + ", boundingRadius=" + this.boundingRadius + ", isImportant=" + this.isImportant + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\config\ParticleSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */