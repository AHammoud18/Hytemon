/*     */ package com.hypixel.hytale.builtin.mounts.interactions;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.mounts.minecart.MinecartComponent;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.RailConfig;
/*     */ import com.hypixel.hytale.protocol.RailPoint;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Interactable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.Interactions;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawnMinecartInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   public static final BuilderCodec<SpawnMinecartInteraction> CODEC;
/*     */   private String modelId;
/*     */   
/*     */   static {
/*  59 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SpawnMinecartInteraction.class, SpawnMinecartInteraction::new, SimpleBlockInteraction.CODEC).documentation("Spawns a minecart at the target block")).appendInherited(new KeyedCodec("Model", (Codec)Codec.STRING), (o, v) -> o.modelId = v, o -> o.modelId, (o, p) -> o.modelId = p.modelId).addValidator(ModelAsset.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("CartInteractions", (Codec)new EnumMapCodec(InteractionType.class, (Codec)RootInteraction.CHILD_ASSET_CODEC)), (o, v) -> o.cartInteractions = v, o -> o.cartInteractions, (o, p) -> o.cartInteractions = p.cartInteractions).addValidatorLate(() -> RootInteraction.VALIDATOR_CACHE.getMapValueValidator().late()).add()).build();
/*     */   }
/*     */   
/*  62 */   private Map<InteractionType, String> cartInteractions = Collections.emptyMap();
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*  66 */     Ref<EntityStore> ref = context.getEntity();
/*  67 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*     */     
/*  69 */     Vector3d targetPosition = targetBlock.toVector3d();
/*  70 */     targetPosition.add(0.5D, 0.5D, 0.5D);
/*     */     
/*  72 */     Vector3f rotation = new Vector3f();
/*  73 */     HeadRotation headRotation = (HeadRotation)commandBuffer.getComponent(ref, HeadRotation.getComponentType());
/*  74 */     if (headRotation != null) {
/*  75 */       rotation.setYaw(headRotation.getRotation().getYaw());
/*     */     }
/*     */     
/*  78 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/*  79 */     if (chunk == null)
/*     */       return; 
/*  81 */     BlockType block = chunk.getBlockType(targetBlock);
/*  82 */     int blockRotation = chunk.getRotationIndex(targetBlock.x, targetBlock.y, targetBlock.z);
/*  83 */     RailConfig railConfig = block.getRailConfig(blockRotation);
/*  84 */     if (railConfig != null) {
/*  85 */       alignToRail(targetBlock, targetPosition, rotation, rotation.getYaw(), railConfig);
/*     */     } else {
/*  87 */       BlockBoundingBoxes.RotatedVariantBoxes bounding = ((BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(block.getHitboxTypeIndex())).get(blockRotation);
/*  88 */       targetPosition.add(0.0D, (bounding.getBoundingBox()).max.y - 0.5D, 0.0D);
/*     */     } 
/*     */     
/*  91 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(targetPosition, rotation));
/*  92 */     holder.ensureComponent(UUIDComponent.getComponentType());
/*     */     
/*  94 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(this.modelId);
/*  95 */     if (modelAsset == null) {
/*  96 */       modelAsset = ModelAsset.DEBUG;
/*     */     }
/*     */     
/*  99 */     Model model = Model.createRandomScaleModel(modelAsset);
/*     */     
/* 101 */     holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/* 102 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 103 */     holder.addComponent(BoundingBox.getComponentType(), (Component)new BoundingBox(model.getBoundingBox()));
/* 104 */     holder.ensureComponent(Interactable.getComponentType());
/* 105 */     holder.addComponent(Interactions.getComponentType(), (Component)new Interactions(this.cartInteractions));
/* 106 */     holder.putComponent(MinecartComponent.getComponentType(), (Component)new MinecartComponent((context.getHeldItem() != null) ? context.getHeldItem().getItemId() : null));
/*     */     
/* 108 */     commandBuffer.addEntity(holder, AddReason.SPAWN);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ 
/*     */   
/*     */   private static void alignToRail(@Nonnull Vector3i targetBlock, @Nonnull Vector3d target, @Nonnull Vector3f rotation, float yaw, @Nonnull RailConfig config) {
/* 117 */     RailPoint[] points = config.points;
/*     */     
/* 119 */     double smallestDistance = Double.MAX_VALUE;
/* 120 */     double ox = target.x, oy = target.y, oz = target.z;
/*     */     
/* 122 */     Vector3d facingDir = new Vector3d();
/* 123 */     facingDir.assign(yaw, 0.0D);
/*     */ 
/*     */     
/* 126 */     for (int index = 0; index < points.length - 1; index++) {
/* 127 */       RailPoint p = points[index];
/* 128 */       RailPoint p2 = points[index + 1];
/* 129 */       Vector3d point = new Vector3d((targetBlock.x + p.point.x), (targetBlock.y + p.point.y), (targetBlock.z + p.point.z));
/* 130 */       Vector3d point2 = new Vector3d((targetBlock.x + p2.point.x), (targetBlock.y + p2.point.y), (targetBlock.z + p2.point.z));
/*     */       
/* 132 */       Vector3d dir = point2.clone().subtract(point);
/* 133 */       double maxLength = dir.length();
/* 134 */       dir.normalize();
/*     */       
/* 136 */       Vector3d toPoint = target.clone().subtract(point);
/* 137 */       double distance = dir.dot(toPoint);
/*     */       
/* 139 */       Vector3d pointOnLine = point.clone();
/* 140 */       pointOnLine.addScaled(dir, Math.min(maxLength, Math.max(0.0D, distance)));
/* 141 */       double pointDist = pointOnLine.distanceSquaredTo(target);
/*     */ 
/*     */       
/* 144 */       if (pointDist >= 0.0D && pointDist <= 0.800000011920929D && pointDist < smallestDistance) {
/* 145 */         ox = pointOnLine.x;
/* 146 */         oy = pointOnLine.y;
/* 147 */         oz = pointOnLine.z;
/* 148 */         smallestDistance = pointDist;
/*     */         
/* 150 */         if (facingDir.dot(dir) < 0.0D) {
/* 151 */           dir.scale(-1.0D);
/*     */         }
/*     */         
/* 154 */         float newYaw = (float)(Math.atan2(dir.x, dir.z) + Math.PI);
/* 155 */         float newPitch = (float)Math.asin(dir.y);
/* 156 */         rotation.setYaw(newYaw);
/* 157 */         rotation.setPitch(newPitch);
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     if (smallestDistance >= Double.MAX_VALUE)
/*     */       return; 
/* 163 */     target.assign(ox, oy, oz);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\interactions\SpawnMinecartInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */