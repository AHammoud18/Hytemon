/*     */ package com.hypixel.hytale.server.core.entity.entities;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.projectile.config.Projectile;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.BlockMigrationExtraInfo;
/*     */ import com.hypixel.hytale.server.core.modules.entity.DespawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.SimplePhysicsProvider;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockEntity
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   public static final BuilderCodec<BlockEntity> CODEC;
/*     */   public static final int DEFAULT_DESPAWN_SECONDS = 120;
/*     */   
/*     */   static {
/*  39 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockEntity.class, BlockEntity::new).append(new KeyedCodec("BlockTypeKey", (Codec)Codec.STRING), (blockEntity, newBlockTypeKey, extraInfo) -> { blockEntity.blockTypeKey = newBlockTypeKey; if (extraInfo instanceof BlockMigrationExtraInfo) blockEntity.blockTypeKey = ((BlockMigrationExtraInfo)extraInfo).getBlockMigration().apply(newBlockTypeKey);  }(blockEntity, extraInfo) -> blockEntity.blockTypeKey).add()).build();
/*     */   }
/*     */   public static ComponentType<EntityStore, BlockEntity> getComponentType() {
/*  42 */     return EntityModule.get().getBlockEntityComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  48 */   private transient SimplePhysicsProvider simplePhysicsProvider = new SimplePhysicsProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String blockTypeKey;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBlockIdNetworkOutdated;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockEntity(String blockTypeKey) {
/*  63 */     this.blockTypeKey = blockTypeKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Holder<EntityStore> assembleDefaultBlockEntity(@Nonnull TimeResource time, String blockTypeKey, @Nonnull Vector3d position) {
/*  71 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*     */     
/*  73 */     holder.addComponent(getComponentType(), new BlockEntity(blockTypeKey));
/*  74 */     holder.addComponent(DespawnComponent.getComponentType(), (Component)DespawnComponent.despawnInSeconds(time, 120));
/*  75 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(position.clone(), Vector3f.FORWARD));
/*  76 */     holder.ensureComponent(Velocity.getComponentType());
/*  77 */     holder.ensureComponent(UUIDComponent.getComponentType());
/*     */     
/*  79 */     return holder;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SimplePhysicsProvider initPhysics(@Nonnull BoundingBox boundingBox) {
/*  85 */     this.simplePhysicsProvider.initialize((Projectile)Projectile.getAssetMap().getAsset("Projectile"), boundingBox);
/*  86 */     this.simplePhysicsProvider.setProvideCharacterCollisions(false);
/*  87 */     this.simplePhysicsProvider.setMoveOutOfSolid(true);
/*  88 */     return this.simplePhysicsProvider;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BoundingBox updateHitbox(@Nonnull Ref<EntityStore> ref, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  93 */     BoundingBox boundingBoxComponent = createBoundingBoxComponent();
/*  94 */     commandBuffer.putComponent(ref, BoundingBox.getComponentType(), (Component)boundingBoxComponent);
/*  95 */     return boundingBoxComponent;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BoundingBox createBoundingBoxComponent() {
/* 100 */     if (this.blockTypeKey == null) return null;
/*     */     
/* 102 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/* 103 */     if (assetMap == null) return null;
/*     */     
/* 105 */     BlockType blockType = (BlockType)assetMap.getAsset(this.blockTypeKey);
/* 106 */     if (blockType == null) return null;
/*     */     
/* 108 */     return new BoundingBox(((BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxTypeIndex())).get(0).getBoundingBox());
/*     */   }
/*     */   
/*     */   public void setBlockTypeKey(String blockTypeKey, @Nonnull Ref<EntityStore> ref, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 112 */     this.blockTypeKey = blockTypeKey;
/* 113 */     this.isBlockIdNetworkOutdated = true;
/* 114 */     updateHitbox(ref, commandBuffer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public SimplePhysicsProvider getSimplePhysicsProvider() {
/* 119 */     return this.simplePhysicsProvider;
/*     */   }
/*     */   
/*     */   public String getBlockTypeKey() {
/* 123 */     return this.blockTypeKey;
/*     */   }
/*     */   
/*     */   public void addForce(float x, float y, float z) {
/* 127 */     this.simplePhysicsProvider.addVelocity(x, y, z);
/*     */   }
/*     */   
/*     */   public void addForce(@Nonnull Vector3d force) {
/* 131 */     this.simplePhysicsProvider.addVelocity((float)force.x, (float)force.y, (float)force.z);
/*     */   }
/*     */   
/*     */   public boolean consumeBlockIdNetworkOutdated() {
/* 135 */     boolean temp = this.isBlockIdNetworkOutdated;
/* 136 */     this.isBlockIdNetworkOutdated = false;
/* 137 */     return temp;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 143 */     return new BlockEntity(this.blockTypeKey);
/*     */   }
/*     */   
/*     */   protected BlockEntity() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\BlockEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */