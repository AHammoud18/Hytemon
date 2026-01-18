/*     */ package com.hypixel.hytale.server.core.modules.entity.component;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.ModelTransform;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class TransformComponent implements Component<EntityStore> {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<TransformComponent> CODEC;
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, TransformComponent> getComponentType() {
/*  33 */     return EntityModule.get().getTransformComponentType();
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
/*     */   static {
/*  53 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TransformComponent.class, TransformComponent::new).append(new KeyedCodec("Position", (Codec)Vector3d.CODEC), (o, i) -> o.position.assign(i), o -> o.position).add()).append(new KeyedCodec("Rotation", (Codec)Vector3f.ROTATION), (o, i) -> o.rotation.assign(i), o -> o.rotation).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*  58 */   private final Vector3d position = new Vector3d();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  64 */   private final Vector3f rotation = new Vector3f();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  71 */   private final ModelTransform sentTransform = new ModelTransform(new Position(), new Direction(), new Direction());
/*     */ 
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
/*     */   @Deprecated(forRemoval = true)
/*     */   private WorldChunk chunk;
/*     */ 
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
/*     */   private Ref<ChunkStore> chunkRef;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformComponent(@Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/* 109 */     this.position.assign(position);
/* 110 */     this.rotation.assign(rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getPosition() {
/* 118 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(@Nonnull Vector3d position) {
/* 127 */     this.position.assign(position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void teleportPosition(@Nonnull Vector3d position) {
/* 136 */     double x = position.getX();
/* 137 */     if (!Double.isNaN(x)) {
/* 138 */       this.position.setX(x);
/*     */     }
/*     */     
/* 141 */     double y = position.getY();
/* 142 */     if (!Double.isNaN(y)) {
/* 143 */       this.position.setY(y);
/*     */     }
/*     */     
/* 146 */     double z = position.getZ();
/* 147 */     if (!Double.isNaN(z)) {
/* 148 */       this.position.setZ(z);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f getRotation() {
/* 157 */     return this.rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotation(@Nonnull Vector3f rotation) {
/* 166 */     this.rotation.assign(rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Transform getTransform() {
/* 174 */     return new Transform(this.position, this.rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void teleportRotation(@Nonnull Vector3f rotation) {
/* 183 */     float yaw = rotation.getYaw();
/* 184 */     if (!Float.isNaN(yaw)) {
/* 185 */       this.rotation.setYaw(yaw);
/*     */     }
/*     */     
/* 188 */     float pitch = rotation.getPitch();
/* 189 */     if (!Float.isNaN(pitch)) {
/* 190 */       this.rotation.setPitch(pitch);
/*     */     }
/*     */     
/* 193 */     float roll = rotation.getRoll();
/* 194 */     if (!Float.isNaN(roll)) {
/* 195 */       this.rotation.setRoll(roll);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ModelTransform getSentTransform() {
/* 204 */     return this.sentTransform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Deprecated(forRemoval = true)
/*     */   public WorldChunk getChunk() {
/* 214 */     return this.chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<ChunkStore> getChunkRef() {
/* 222 */     return this.chunkRef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markChunkDirty(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 231 */     if (this.chunkRef == null || !this.chunkRef.isValid())
/*     */       return; 
/* 233 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 234 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */     
/* 236 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(this.chunkRef, WorldChunk.getComponentType());
/* 237 */     assert worldChunkComponent != null;
/*     */     
/* 239 */     worldChunkComponent.markNeedsSaving();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChunkLocation(@Nullable Ref<ChunkStore> chunkRef, @Nullable WorldChunk chunk) {
/* 249 */     this.chunkRef = chunkRef;
/* 250 */     this.chunk = chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TransformComponent clone() {
/* 257 */     TransformComponent transformComponent = new TransformComponent(this.position, this.rotation);
/* 258 */     ModelTransform transform = transformComponent.sentTransform;
/* 259 */     PositionUtil.assign(transform.position, this.sentTransform.position);
/* 260 */     PositionUtil.assign(transform.bodyOrientation, this.sentTransform.bodyOrientation);
/* 261 */     PositionUtil.assign(transform.lookOrientation, this.sentTransform.lookOrientation);
/* 262 */     return transformComponent;
/*     */   }
/*     */   
/*     */   public TransformComponent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\TransformComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */