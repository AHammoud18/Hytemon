/*     */ package com.hypixel.hytale.builtin.instances.blocks;
/*     */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ConfigurableInstanceBlock implements Component<ChunkStore> {
/*     */   public static ComponentType<ChunkStore, ConfigurableInstanceBlock> getComponentType() {
/*  23 */     return InstancesPlugin.get().getConfigurableInstanceBlockComponentType();
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
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ConfigurableInstanceBlock> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UUID worldUUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CompletableFuture<World> worldFuture;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  81 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ConfigurableInstanceBlock.class, ConfigurableInstanceBlock::new).appendInherited(new KeyedCodec("WorldName", (Codec)Codec.UUID_BINARY), (o, i) -> o.worldUUID = i, o -> o.worldUUID, (o, p) -> o.worldUUID = p.worldUUID).add()).appendInherited(new KeyedCodec("CloseOnBlockRemove", (Codec)Codec.BOOLEAN), (o, i) -> o.closeOnRemove = i.booleanValue(), o -> Boolean.valueOf(o.closeOnRemove), (o, p) -> o.closeOnRemove = p.closeOnRemove).add()).appendInherited(new KeyedCodec("InstanceName", (Codec)Codec.STRING), (o, i) -> o.instanceName = i, o -> o.instanceName, (o, p) -> o.instanceName = p.instanceName).add()).appendInherited(new KeyedCodec("InstanceKey", (Codec)Codec.STRING), (o, i) -> o.instanceKey = i, o -> o.instanceKey, (o, p) -> o.instanceKey = p.instanceKey).add()).appendInherited(new KeyedCodec("PositionOffset", (Codec)Vector3d.CODEC), (o, i) -> o.positionOffset = i, o -> o.positionOffset, (o, p) -> o.positionOffset = p.positionOffset).add()).appendInherited(new KeyedCodec("Rotation", (Codec)Vector3f.ROTATION), (o, i) -> o.rotation = i, o -> o.rotation, (o, p) -> o.rotation = p.rotation).add()).appendInherited(new KeyedCodec("PersonalReturnPoint", (Codec)Codec.BOOLEAN), (o, i) -> o.personalReturnPoint = i.booleanValue(), o -> Boolean.valueOf(o.personalReturnPoint), (o, p) -> o.personalReturnPoint = p.personalReturnPoint).add()).appendInherited(new KeyedCodec("RemoveBlockAfter", (Codec)Codec.DOUBLE), (o, i) -> o.removeBlockAfter = i.doubleValue(), o -> Double.valueOf(o.removeBlockAfter), (o, p) -> o.removeBlockAfter = p.removeBlockAfter).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean closeOnRemove = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String instanceName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String instanceKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Vector3d positionOffset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Vector3f rotation;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean personalReturnPoint = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   private double removeBlockAfter = -1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConfigurableInstanceBlock(UUID worldUUID, boolean closeOnRemove, String instanceName, String instanceKey, @Nullable Vector3d positionOffset, @Nullable Vector3f rotation, boolean personalReturnPoint, double removeBlockAfter) {
/* 131 */     this.worldUUID = worldUUID;
/* 132 */     this.closeOnRemove = closeOnRemove;
/* 133 */     this.instanceName = instanceName;
/* 134 */     this.instanceKey = instanceKey;
/* 135 */     this.positionOffset = positionOffset;
/* 136 */     this.rotation = rotation;
/* 137 */     this.personalReturnPoint = personalReturnPoint;
/* 138 */     this.removeBlockAfter = removeBlockAfter;
/*     */   }
/*     */   
/*     */   public UUID getWorldUUID() {
/* 142 */     return this.worldUUID;
/*     */   }
/*     */   
/*     */   public void setWorldUUID(UUID worldUUID) {
/* 146 */     this.worldUUID = worldUUID;
/*     */   }
/*     */   
/*     */   public CompletableFuture<World> getWorldFuture() {
/* 150 */     return this.worldFuture;
/*     */   }
/*     */   
/*     */   public void setWorldFuture(CompletableFuture<World> worldFuture) {
/* 154 */     this.worldFuture = worldFuture;
/*     */   }
/*     */   
/*     */   public boolean isCloseOnRemove() {
/* 158 */     return this.closeOnRemove;
/*     */   }
/*     */   
/*     */   public void setCloseOnRemove(boolean closeOnRemove) {
/* 162 */     this.closeOnRemove = closeOnRemove;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInstanceName() {
/* 169 */     return this.instanceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInstanceName(@Nonnull String instanceName) {
/* 178 */     this.instanceName = instanceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInstanceKey() {
/* 185 */     return this.instanceKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInstanceKey(@Nonnull String instanceKey) {
/* 194 */     this.instanceKey = instanceKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vector3d getPositionOffset() {
/* 202 */     return this.positionOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositionOffset(@Nullable Vector3d positionOffset) {
/* 211 */     this.positionOffset = positionOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vector3f getRotation() {
/* 219 */     return this.rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotation(@Nullable Vector3f rotation) {
/* 228 */     this.rotation = rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPersonalReturnPoint() {
/* 237 */     return this.personalReturnPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersonalReturnPoint(boolean personalReturnPoint) {
/* 246 */     this.personalReturnPoint = personalReturnPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRemoveBlockAfter() {
/* 253 */     return this.removeBlockAfter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRemoveBlockAfter(double removeBlockAfter) {
/* 262 */     this.removeBlockAfter = removeBlockAfter;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component<ChunkStore> clone() {
/* 268 */     return new ConfigurableInstanceBlock(this.worldUUID, this.closeOnRemove, this.instanceName, this.instanceKey, this.positionOffset, this.rotation, this.personalReturnPoint, this.removeBlockAfter);
/*     */   }
/*     */   
/*     */   public ConfigurableInstanceBlock() {}
/*     */   
/*     */   public static class OnRemove
/*     */     extends RefSystem<ChunkStore>
/*     */   {
/*     */     public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 279 */       ConfigurableInstanceBlock instance = (ConfigurableInstanceBlock)commandBuffer.getComponent(ref, ConfigurableInstanceBlock.getComponentType());
/* 280 */       assert instance != null;
/* 281 */       if (!instance.closeOnRemove)
/* 282 */         return;  if (instance.worldUUID != null) {
/* 283 */         InstancesPlugin.get(); InstancesPlugin.safeRemoveInstance(instance.worldUUID);
/* 284 */       } else if (instance.worldFuture != null) {
/* 285 */         instance.worldFuture.thenAccept(world -> {
/*     */               InstancesPlugin.get();
/*     */               InstancesPlugin.safeRemoveInstance(world.getName());
/*     */             });
/*     */       } 
/*     */     } @Nullable
/*     */     public Query<ChunkStore> getQuery() {
/* 292 */       return (Query)ConfigurableInstanceBlock.getComponentType();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\blocks\ConfigurableInstanceBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */