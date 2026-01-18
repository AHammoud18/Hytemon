/*     */ package com.hypixel.hytale.builtin.beds.interactions;
/*     */ import com.hypixel.hytale.builtin.beds.sleep.components.PlayerSomnolence;
/*     */ import com.hypixel.hytale.builtin.mounts.BlockMountAPI;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.RespawnConfig;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerRespawnPointData;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.PageManager;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.RespawnBlock;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ public class BedInteraction extends SimpleBlockInteraction {
/*  40 */   public static final BuilderCodec<BedInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BedInteraction.class, BedInteraction::new, SimpleBlockInteraction.CODEC)
/*  41 */     .documentation("Interact with a bed block, ostensibly to sleep in it."))
/*  42 */     .build();
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@NonNullDecl World world, @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NullableDecl ItemStack itemInHand, @NonNullDecl Vector3i pos, @NonNullDecl CooldownHandler cooldownHandler) {
/*  46 */     Ref<EntityStore> ref = context.getEntity();
/*     */     
/*  48 */     Player player = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  49 */     if (player == null)
/*     */       return; 
/*  51 */     Store<EntityStore> store = commandBuffer.getStore();
/*     */     
/*  53 */     PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/*  54 */     assert playerRefComponent != null;
/*     */     
/*  56 */     UUIDComponent playerUuidComponent = (UUIDComponent)commandBuffer.getComponent(ref, UUIDComponent.getComponentType());
/*  57 */     assert playerUuidComponent != null;
/*     */     
/*  59 */     UUID playerUuid = playerUuidComponent.getUuid();
/*     */     
/*  61 */     Ref<ChunkStore> chunkReference = world.getChunkStore().getChunkReference(ChunkUtil.indexChunkFromBlock(pos.x, pos.z));
/*  62 */     if (chunkReference == null)
/*     */       return; 
/*  64 */     Store<ChunkStore> chunkStore = chunkReference.getStore();
/*  65 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)chunkStore.getComponent(chunkReference, BlockComponentChunk.getComponentType());
/*  66 */     assert blockComponentChunk != null;
/*     */     
/*  68 */     int blockIndex = ChunkUtil.indexBlockInColumn(pos.x, pos.y, pos.z);
/*  69 */     Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(blockIndex);
/*  70 */     if (blockRef == null) {
/*  71 */       Holder<ChunkStore> holder = ChunkStore.REGISTRY.newHolder();
/*  72 */       holder.putComponent(BlockModule.BlockStateInfo.getComponentType(), (Component)new BlockModule.BlockStateInfo(blockIndex, chunkReference));
/*  73 */       holder.ensureComponent(RespawnBlock.getComponentType());
/*  74 */       blockRef = chunkStore.addEntity(holder, AddReason.SPAWN);
/*     */     } 
/*     */     
/*  77 */     RespawnBlock respawnBlock = (RespawnBlock)chunkStore.getComponent(blockRef, RespawnBlock.getComponentType());
/*  78 */     if (respawnBlock == null)
/*     */       return; 
/*  80 */     UUID ownerUUID = respawnBlock.getOwnerUUID();
/*  81 */     PageManager pageManager = player.getPageManager();
/*     */     
/*  83 */     boolean isOwner = playerUuid.equals(ownerUUID);
/*  84 */     if (isOwner) {
/*  85 */       BlockPosition rawTarget = (BlockPosition)context.getMetaStore().getMetaObject(TARGET_BLOCK_RAW);
/*  86 */       Vector3f whereWasHit = new Vector3f(rawTarget.x + 0.5F, rawTarget.y + 0.5F, rawTarget.z + 0.5F);
/*  87 */       BlockMountAPI.BlockMountResult result = BlockMountAPI.mountOnBlock(ref, commandBuffer, pos, whereWasHit);
/*  88 */       if (result instanceof BlockMountAPI.DidNotMount) {
/*  89 */         player.sendMessage(Message.translation("server.interactions.didNotMount").param("state", result.toString()));
/*  90 */       } else if (result instanceof BlockMountAPI.Mounted) {
/*  91 */         commandBuffer.putComponent(ref, PlayerSomnolence.getComponentType(), (Component)PlayerSleep.NoddingOff.createComponent());
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     if (ownerUUID != null) {
/*     */       
/*  98 */       player.sendMessage(Message.translation("server.customUI.respawnPointClaimed"));
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     PlayerRespawnPointData[] respawnPoints = player.getPlayerConfigData().getPerWorldData(world.getName()).getRespawnPoints();
/* 103 */     RespawnConfig respawnConfig = world.getGameplayConfig().getRespawnConfig();
/*     */     
/* 105 */     int radiusLimitRespawnPoint = respawnConfig.getRadiusLimitRespawnPoint();
/* 106 */     PlayerRespawnPointData[] nearbyRespawnPoints = getNearbySavedRespawnPoints(pos, respawnBlock, respawnPoints, radiusLimitRespawnPoint);
/* 107 */     if (nearbyRespawnPoints != null) {
/* 108 */       pageManager.openCustomPage(ref, store, (CustomUIPage)new OverrideNearbyRespawnPointPage(playerRefComponent, type, pos, respawnBlock, nearbyRespawnPoints, radiusLimitRespawnPoint));
/*     */       
/*     */       return;
/*     */     } 
/* 112 */     if (respawnPoints != null && respawnPoints.length >= respawnConfig.getMaxRespawnPointsPerPlayer()) {
/* 113 */       pageManager.openCustomPage(ref, store, (CustomUIPage)new SelectOverrideRespawnPointPage(playerRefComponent, type, pos, respawnBlock, respawnPoints));
/*     */       
/*     */       return;
/*     */     } 
/* 117 */     pageManager.openCustomPage(ref, store, (CustomUIPage)new SetNameRespawnPointPage(playerRefComponent, type, pos, respawnBlock));
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
/*     */   protected void simulateInteractWithBlock(@NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NullableDecl ItemStack itemInHand, @NonNullDecl World world, @NonNullDecl Vector3i targetBlock) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private PlayerRespawnPointData[] getNearbySavedRespawnPoints(@Nonnull Vector3i currentRespawnPointPosition, @Nonnull RespawnBlock respawnBlock, @Nullable PlayerRespawnPointData[] respawnPoints, int radiusLimitRespawnPoint) {
/* 139 */     if (respawnPoints == null || respawnPoints.length == 0) return null;
/*     */     
/* 141 */     ObjectArrayList<PlayerRespawnPointData> nearbyRespawnPointList = new ObjectArrayList();
/*     */     
/* 143 */     for (int i = 0; i < respawnPoints.length; i++) {
/* 144 */       PlayerRespawnPointData respawnPoint = respawnPoints[i];
/* 145 */       Vector3i respawnPointPosition = respawnPoint.getBlockPosition();
/*     */ 
/*     */       
/* 148 */       if (respawnPointPosition.distanceTo(currentRespawnPointPosition.x, respawnPointPosition.y, currentRespawnPointPosition.z) < radiusLimitRespawnPoint) {
/* 149 */         nearbyRespawnPointList.add(respawnPoint);
/*     */       }
/*     */     } 
/*     */     
/* 153 */     return nearbyRespawnPointList.isEmpty() ? null : (PlayerRespawnPointData[])nearbyRespawnPointList.toArray(x$0 -> new PlayerRespawnPointData[x$0]);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNullDecl
/*     */   public String toString() {
/* 159 */     return "BedInteraction{} " + super.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\interactions\BedInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */