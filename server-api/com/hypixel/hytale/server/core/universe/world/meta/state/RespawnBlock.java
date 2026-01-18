/*     */ package com.hypixel.hytale.server.core.universe.world.meta.state;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerRespawnPointData;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class RespawnBlock implements Component<ChunkStore> {
/*     */   public static ComponentType<ChunkStore, RespawnBlock> getComponentType() {
/*  27 */     return BlockModule.get().getRespawnBlockComponentType();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<RespawnBlock> CODEC;
/*     */   
/*     */   private UUID ownerUUID;
/*     */   
/*     */   static {
/*  36 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(RespawnBlock.class, RespawnBlock::new).append(new KeyedCodec("OwnerUUID", (Codec)Codec.UUID_BINARY), (respawnBlockState, uuid) -> respawnBlockState.ownerUUID = uuid, respawnBlockState -> respawnBlockState.ownerUUID).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public RespawnBlock() {}
/*     */ 
/*     */   
/*     */   public RespawnBlock(UUID ownerUUID) {
/*  44 */     this.ownerUUID = ownerUUID;
/*     */   }
/*     */   
/*     */   public UUID getOwnerUUID() {
/*  48 */     return this.ownerUUID;
/*     */   }
/*     */   
/*     */   public void setOwnerUUID(UUID ownerUUID) {
/*  52 */     this.ownerUUID = ownerUUID;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component<ChunkStore> clone() {
/*  58 */     return new RespawnBlock(this.ownerUUID);
/*     */   }
/*     */   
/*     */   public static class OnRemove
/*     */     extends RefSystem<ChunkStore> {
/*  63 */     public static final ComponentType<ChunkStore, RespawnBlock> COMPONENT_TYPE = RespawnBlock.getComponentType();
/*  64 */     public static final ComponentType<ChunkStore, BlockModule.BlockStateInfo> BLOCK_INFO_TYPE = BlockModule.BlockStateInfo.getComponentType();
/*  65 */     public static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)COMPONENT_TYPE, (Query)BLOCK_INFO_TYPE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  77 */       if (reason == RemoveReason.UNLOAD)
/*  78 */         return;  RespawnBlock respawnState = (RespawnBlock)commandBuffer.getComponent(ref, COMPONENT_TYPE);
/*  79 */       assert respawnState != null;
/*  80 */       if (respawnState.ownerUUID == null)
/*     */         return; 
/*  82 */       BlockModule.BlockStateInfo blockInfo = (BlockModule.BlockStateInfo)commandBuffer.getComponent(ref, BLOCK_INFO_TYPE);
/*  83 */       assert blockInfo != null;
/*     */       
/*  85 */       PlayerRef playerRef = Universe.get().getPlayer(respawnState.ownerUUID);
/*  86 */       if (playerRef == null) {
/*     */         
/*  88 */         HytaleLogger.getLogger().at(Level.WARNING).log("Need to load PlayerConfig to remove RespawnPoint!");
/*     */         return;
/*     */       } 
/*  91 */       Player player = (Player)playerRef.getComponent(Player.getComponentType());
/*     */       
/*  93 */       Ref<ChunkStore> chunkRef = blockInfo.getChunkRef();
/*  94 */       if (chunkRef == null || !chunkRef.isValid())
/*     */         return; 
/*  96 */       PlayerWorldData playerWorldData = player.getPlayerConfigData().getPerWorldData(((ChunkStore)store.getExternalData()).getWorld().getName());
/*  97 */       PlayerRespawnPointData[] respawnPoints = playerWorldData.getRespawnPoints();
/*     */       
/*  99 */       WorldChunk wc = (WorldChunk)commandBuffer.getComponent(chunkRef, WorldChunk.getComponentType());
/*     */ 
/*     */ 
/*     */       
/* 103 */       Vector3i blockPosition = new Vector3i(ChunkUtil.worldCoordFromLocalCoord(wc.getX(), ChunkUtil.xFromBlockInColumn(blockInfo.getIndex())), ChunkUtil.yFromBlockInColumn(blockInfo.getIndex()), ChunkUtil.worldCoordFromLocalCoord(wc.getZ(), ChunkUtil.zFromBlockInColumn(blockInfo.getIndex())));
/*     */ 
/*     */       
/* 106 */       for (int i = 0; i < respawnPoints.length; i++) {
/* 107 */         PlayerRespawnPointData respawnPoint = respawnPoints[i];
/* 108 */         if (respawnPoint.getBlockPosition().equals(blockPosition)) {
/* 109 */           playerWorldData.setRespawnPoints((PlayerRespawnPointData[])ArrayUtil.remove((Object[])respawnPoints, i));
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Query<ChunkStore> getQuery() {
/* 118 */       return QUERY;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\state\RespawnBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */