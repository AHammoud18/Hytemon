/*     */ package com.hypixel.hytale.builtin.instances.blocks;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InstanceBlock implements Component<ChunkStore> {
/*     */   public static ComponentType<ChunkStore, InstanceBlock> getComponentType() {
/*  26 */     return InstancesPlugin.get().getInstanceBlockComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<InstanceBlock> CODEC;
/*     */ 
/*     */ 
/*     */   
/*     */   protected UUID worldUUID;
/*     */ 
/*     */   
/*     */   protected CompletableFuture<World> worldFuture;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  44 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InstanceBlock.class, InstanceBlock::new).appendInherited(new KeyedCodec("WorldName", (Codec)Codec.UUID_BINARY), (o, i) -> o.worldUUID = i, o -> o.worldUUID, (o, p) -> o.worldUUID = p.worldUUID).add()).appendInherited(new KeyedCodec("CloseOnBlockRemove", (Codec)Codec.BOOLEAN), (o, i) -> o.closeOnRemove = i.booleanValue(), o -> Boolean.valueOf(o.closeOnRemove), (o, p) -> o.closeOnRemove = p.closeOnRemove).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean closeOnRemove = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public InstanceBlock(UUID worldUUID, boolean closeOnRemove) {
/*  55 */     this.worldUUID = worldUUID;
/*  56 */     this.closeOnRemove = closeOnRemove;
/*     */   }
/*     */   
/*     */   public UUID getWorldUUID() {
/*  60 */     return this.worldUUID;
/*     */   }
/*     */   
/*     */   public void setWorldUUID(UUID worldUUID) {
/*  64 */     this.worldUUID = worldUUID;
/*     */   }
/*     */   
/*     */   public CompletableFuture<World> getWorldFuture() {
/*  68 */     return this.worldFuture;
/*     */   }
/*     */   
/*     */   public void setWorldFuture(CompletableFuture<World> worldFuture) {
/*  72 */     this.worldFuture = worldFuture;
/*     */   }
/*     */   
/*     */   public boolean isCloseOnRemove() {
/*  76 */     return this.closeOnRemove;
/*     */   }
/*     */   
/*     */   public void setCloseOnRemove(boolean closeOnRemove) {
/*  80 */     this.closeOnRemove = closeOnRemove;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component<ChunkStore> clone() {
/*  86 */     return new InstanceBlock(this.worldUUID, this.closeOnRemove);
/*     */   }
/*     */   
/*     */   public InstanceBlock() {}
/*     */   
/*     */   public static class OnRemove
/*     */     extends RefSystem<ChunkStore>
/*     */   {
/*     */     public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  97 */       InstanceBlock instance = (InstanceBlock)commandBuffer.getComponent(ref, InstanceBlock.getComponentType());
/*  98 */       assert instance != null;
/*  99 */       if (!instance.closeOnRemove)
/* 100 */         return;  if (instance.worldUUID != null) {
/* 101 */         InstancesPlugin.get(); InstancesPlugin.safeRemoveInstance(instance.worldUUID);
/* 102 */       } else if (instance.worldFuture != null) {
/* 103 */         instance.worldFuture.thenAccept(world -> {
/*     */               InstancesPlugin.get();
/*     */               InstancesPlugin.safeRemoveInstance(world.getName());
/*     */             });
/*     */       } 
/*     */     } @Nullable
/*     */     public Query<ChunkStore> getQuery() {
/* 110 */       return (Query)InstanceBlock.getComponentType();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\blocks\InstanceBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */