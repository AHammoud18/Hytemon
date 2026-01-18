/*     */ package com.hypixel.hytale.builtin.beds.respawn;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.Page;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerRespawnPointData;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*     */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.RespawnBlock;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class RespawnPointPage extends InteractiveCustomUIPage<RespawnPointPage.RespawnPointEventData> {
/*  33 */   private final int RESPAWN_NAME_MAX_LENGTH = 32;
/*     */   
/*     */   public RespawnPointPage(@Nonnull PlayerRef playerRef, InteractionType interactionType) {
/*  36 */     super(playerRef, (interactionType == InteractionType.Use) ? CustomPageLifetime.CanDismissOrCloseThroughInteraction : CustomPageLifetime.CanDismiss, RespawnPointEventData.CODEC);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void build(@Nonnull Ref<EntityStore> paramRef, @Nonnull UICommandBuilder paramUICommandBuilder, @Nonnull UIEventBuilder paramUIEventBuilder, @Nonnull Store<EntityStore> paramStore);
/*     */   
/*     */   protected void setRespawnPointForPlayer(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Vector3i blockPosition, @Nonnull RespawnBlock respawnBlock, @Nonnull String respawnPointName, @Nullable PlayerRespawnPointData... respawnPointsToRemove) {
/*  43 */     respawnPointName = respawnPointName.trim();
/*  44 */     if (respawnPointName.isEmpty()) {
/*  45 */       displayError(Message.translation("server.customUI.needToSetName"));
/*     */       
/*     */       return;
/*     */     } 
/*  49 */     if (respawnPointName.length() > 32) {
/*  50 */       displayError(Message.translation("server.customUI.respawnNameTooLong").param("maxLength", 32));
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     respawnBlock.setOwnerUUID(this.playerRef.getUuid());
/*     */     
/*  56 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  57 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  58 */     assert playerComponent != null;
/*     */     
/*  60 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(blockPosition.x, blockPosition.z));
/*  61 */     if (chunk == null)
/*     */       return; 
/*  63 */     chunk.markNeedsSaving();
/*     */     
/*  65 */     BlockType blockType = chunk.getBlockType(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
/*  66 */     int rotationIndex = chunk.getRotationIndex(blockPosition.x, blockPosition.y, blockPosition.z);
/*  67 */     Box hitbox = ((BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxTypeIndex())).get(rotationIndex).getBoundingBox();
/*     */ 
/*     */     
/*  70 */     double blockCenterWidthOffset = hitbox.min.x + hitbox.width() / 2.0D;
/*  71 */     double blockCenterDepthOffset = hitbox.min.z + hitbox.depth() / 2.0D;
/*  72 */     Vector3d respawnPosition = new Vector3d(blockPosition.getX() + blockCenterWidthOffset, blockPosition.getY() + hitbox.height(), blockPosition.getZ() + blockCenterDepthOffset);
/*     */     
/*  74 */     PlayerRespawnPointData respawnPointData = new PlayerRespawnPointData(blockPosition, respawnPosition, respawnPointName);
/*  75 */     PlayerWorldData perWorldData = playerComponent.getPlayerConfigData().getPerWorldData(world.getName());
/*  76 */     PlayerRespawnPointData[] respawnPoints = handleRespawnPointsToRemove(perWorldData.getRespawnPoints(), respawnPointsToRemove, world);
/*     */     
/*  78 */     if (respawnPoints != null) {
/*  79 */       if (ArrayUtil.contains((Object[])respawnPoints, respawnPointData)) {
/*     */         return;
/*     */       }
/*  82 */       if (respawnPointsToRemove == null || respawnPointsToRemove.length == 0) {
/*  83 */         for (int i = 0; i < respawnPoints.length; ) {
/*  84 */           PlayerRespawnPointData savedRespawnPointData = respawnPoints[i];
/*  85 */           if (!savedRespawnPointData.getBlockPosition().equals(blockPosition)) {
/*     */             i++; continue;
/*  87 */           }  savedRespawnPointData.setName(respawnPointName);
/*  88 */           this.playerRef.sendMessage(Message.translation("server.customUI.updatedRespawnPointName").param("name", respawnPointName));
/*  89 */           playerComponent.getPageManager().setPage(ref, store, Page.None);
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/*     */     } 
/*  95 */     perWorldData.setRespawnPoints((PlayerRespawnPointData[])ArrayUtil.append((Object[])respawnPoints, respawnPointData));
/*  96 */     this.playerRef.sendMessage(Message.translation("server.customUI.respawnPointSet").param("name", respawnPointName));
/*  97 */     playerComponent.getPageManager().setPage(ref, store, Page.None);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private PlayerRespawnPointData[] handleRespawnPointsToRemove(@Nonnull PlayerRespawnPointData[] respawnPoints, @Nullable PlayerRespawnPointData[] respawnPointsToRemove, @Nonnull World world) {
/* 102 */     if (respawnPointsToRemove == null) return respawnPoints;
/*     */     
/* 104 */     ChunkStore chunkStore = world.getChunkStore();
/*     */     
/* 106 */     for (int i = 0; i < respawnPointsToRemove.length; i++) {
/* 107 */       PlayerRespawnPointData respawnPointToRemove = respawnPointsToRemove[i];
/*     */       
/* 109 */       for (int j = 0; j < respawnPoints.length; j++) {
/* 110 */         PlayerRespawnPointData respawnPoint = respawnPoints[j];
/* 111 */         if (respawnPoint.getBlockPosition().equals(respawnPointToRemove.getBlockPosition())) {
/* 112 */           respawnPoints = (PlayerRespawnPointData[])ArrayUtil.remove((Object[])respawnPoints, j);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 117 */       Vector3i position = respawnPointToRemove.getBlockPosition();
/* 118 */       Ref<ChunkStore> chunkReference = chunkStore.getChunkReference(ChunkUtil.indexChunkFromBlock(position.x, position.z));
/* 119 */       if (chunkReference != null) {
/*     */         
/* 121 */         BlockComponentChunk blockComponentChunk = (BlockComponentChunk)chunkStore.getStore().getComponent(chunkReference, BlockComponentChunk.getComponentType());
/* 122 */         Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(ChunkUtil.indexBlockInColumn(position.x, position.y, position.z));
/* 123 */         if (blockRef != null) {
/*     */           
/* 125 */           RespawnBlock respawnBlock = (RespawnBlock)chunkStore.getStore().getComponent(blockRef, RespawnBlock.getComponentType());
/* 126 */           if (respawnBlock != null)
/*     */           
/* 128 */           { respawnBlock.setOwnerUUID(null);
/*     */             
/* 130 */             WorldChunk worldChunk = (WorldChunk)chunkStore.getStore().getComponent(chunkReference, WorldChunk.getComponentType());
/* 131 */             if (worldChunk != null) worldChunk.markNeedsSaving();  } 
/*     */         } 
/*     */       } 
/* 134 */     }  return respawnPoints;
/*     */   }
/*     */   
/*     */   protected void displayError(@Nonnull Message errorMessage) {
/* 138 */     UICommandBuilder commandBuilder = new UICommandBuilder();
/* 139 */     commandBuilder.set("#Error.Visible", true);
/* 140 */     commandBuilder.set("#Error.Text", errorMessage);
/* 141 */     sendUpdate(commandBuilder);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RespawnPointEventData
/*     */   {
/*     */     static final String KEY_ACTION = "Action";
/*     */ 
/*     */     
/*     */     static final String ACTION_CANCEL = "Cancel";
/*     */ 
/*     */     
/*     */     static final String KEY_INDEX = "Index";
/*     */ 
/*     */     
/*     */     static final String KEY_RESPAWN_POINT_NAME = "@RespawnPointName";
/*     */ 
/*     */     
/*     */     public static final BuilderCodec<RespawnPointEventData> CODEC;
/*     */ 
/*     */     
/*     */     private String action;
/*     */     
/*     */     private String indexStr;
/*     */ 
/*     */     
/*     */     static {
/* 169 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RespawnPointEventData.class, RespawnPointEventData::new).append(new KeyedCodec("Action", (Codec)Codec.STRING), (entry, s) -> entry.action = s, entry -> entry.action).add()).append(new KeyedCodec("Index", (Codec)Codec.STRING), (entry, s) -> { entry.indexStr = s; entry.index = Integer.parseInt(s); }entry -> entry.indexStr).add()).append(new KeyedCodec("@RespawnPointName", (Codec)Codec.STRING), (entry, s) -> entry.respawnPointName = s, entry -> entry.respawnPointName).add()).build();
/*     */     }
/*     */ 
/*     */     
/* 173 */     private int index = -1;
/*     */     private String respawnPointName;
/*     */     
/*     */     public String getAction() {
/* 177 */       return this.action;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/* 181 */       return this.index;
/*     */     }
/*     */     
/*     */     public String getRespawnPointName() {
/* 185 */       return this.respawnPointName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\respawn\RespawnPointPage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */