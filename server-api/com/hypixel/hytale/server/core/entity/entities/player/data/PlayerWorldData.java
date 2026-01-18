/*     */ package com.hypixel.hytale.server.core.entity.entities.player.data;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.protocol.SavedMovementStates;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class PlayerWorldData
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PlayerWorldData> CODEC;
/*     */   private static final int DEATH_POSITIONS_COUNT_MAX = 5;
/*     */   private transient PlayerConfigData playerConfigData;
/*     */   private Transform lastPosition;
/*     */   private SavedMovementStates lastMovementStates;
/*     */   private MapMarker[] worldMapMarkers;
/*     */   
/*     */   static {
/*  70 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlayerWorldData.class, PlayerWorldData::new).append(new KeyedCodec("LastPosition", (Codec)Transform.CODEC), (playerWorldData, lastPosition) -> playerWorldData.lastPosition = lastPosition, playerWorldData -> playerWorldData.lastPosition).documentation("The last known position of the player.").add()).append(new KeyedCodec("LastMovementStates", (Codec)ProtocolCodecs.SAVED_MOVEMENT_STATES), (playerWorldData, lastMovementStates) -> playerWorldData.lastMovementStates = lastMovementStates, playerWorldData -> playerWorldData.lastMovementStates).documentation("The last known movement states of the player.").add()).append(new KeyedCodec("WorldMapMarkers", (Codec)ProtocolCodecs.MARKER_ARRAY), (playerConfigData, objectives) -> playerConfigData.worldMapMarkers = objectives, playerConfigData -> playerConfigData.worldMapMarkers).documentation("The world map markers of the player.").add()).append(new KeyedCodec("FirstSpawn", (Codec)Codec.BOOLEAN), (playerWorldData, value) -> playerWorldData.firstSpawn = value.booleanValue(), playerWorldData -> Boolean.valueOf(playerWorldData.firstSpawn)).documentation("Whether this is the first spawn of the player.").add()).append(new KeyedCodec("RespawnPoints", (Codec)new ArrayCodec((Codec)PlayerRespawnPointData.CODEC, x$0 -> new PlayerRespawnPointData[x$0])), (playerWorldData, respawnPointData) -> playerWorldData.respawnPoints = respawnPointData, playerWorldData -> playerWorldData.respawnPoints).documentation("The respawn points of the player.").add()).append(new KeyedCodec("DeathPositions", (Codec)new ArrayCodec((Codec)PlayerDeathPositionData.CODEC, x$0 -> new PlayerDeathPositionData[x$0])), (playerWorldData, deathPositions) -> playerWorldData.deathPositions = (List<PlayerDeathPositionData>)ObjectArrayList.wrap((Object[])deathPositions), playerWorldData -> (PlayerDeathPositionData[])playerWorldData.deathPositions.toArray(())).documentation("The death positions of the player in this world.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean firstSpawn = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private PlayerRespawnPointData[] respawnPoints;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  94 */   private List<PlayerDeathPositionData> deathPositions = (List<PlayerDeathPositionData>)new ObjectArrayList();
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
/*     */   PlayerWorldData(@Nonnull PlayerConfigData playerConfigData) {
/* 109 */     this.playerConfigData = playerConfigData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerConfigData(@Nonnull PlayerConfigData playerConfigData) {
/* 118 */     this.playerConfigData = playerConfigData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform getLastPosition() {
/* 125 */     return this.lastPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastPosition(@Nonnull Transform lastPosition) {
/* 134 */     this.lastPosition = lastPosition;
/* 135 */     this.playerConfigData.markChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SavedMovementStates getLastMovementStates() {
/* 144 */     return this.lastMovementStates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastMovementStates(@Nonnull MovementStates lastMovementStates, boolean save) {
/* 154 */     setLastMovementStates_internal(lastMovementStates);
/* 155 */     if (save) {
/* 156 */       this.playerConfigData.markChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLastMovementStates_internal(@Nonnull MovementStates lastMovementStates) {
/* 166 */     this.lastMovementStates = new SavedMovementStates(lastMovementStates.flying);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MapMarker[] getWorldMapMarkers() {
/* 174 */     return this.worldMapMarkers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldMapMarkers(MapMarker[] worldMapMarkers) {
/* 183 */     this.worldMapMarkers = worldMapMarkers;
/* 184 */     this.playerConfigData.markChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirstSpawn() {
/* 191 */     return this.firstSpawn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstSpawn(boolean firstSpawn) {
/* 200 */     this.firstSpawn = firstSpawn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PlayerRespawnPointData[] getRespawnPoints() {
/* 208 */     return this.respawnPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRespawnPoints(@Nonnull PlayerRespawnPointData[] respawnPoints) {
/* 217 */     this.respawnPoints = respawnPoints;
/* 218 */     this.playerConfigData.markChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<PlayerDeathPositionData> getDeathPositions() {
/* 226 */     return this.deathPositions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLastDeath(@Nonnull String markerId, @Nonnull Transform transform, int deathDay) {
/* 237 */     this.deathPositions.add(new PlayerDeathPositionData(markerId, transform, deathDay));
/*     */     
/* 239 */     while (this.deathPositions.size() > 5) {
/* 240 */       this.deathPositions.removeFirst();
/*     */     }
/*     */     
/* 243 */     this.playerConfigData.markChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLastDeath(@Nonnull String markerId) {
/* 252 */     this.deathPositions.removeIf(deathPosition -> deathPosition.getMarkerId().equalsIgnoreCase(markerId));
/* 253 */     this.playerConfigData.markChanged();
/*     */   }
/*     */   
/*     */   private PlayerWorldData() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\data\PlayerWorldData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */