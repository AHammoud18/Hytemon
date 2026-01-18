/*     */ package com.hypixel.hytale.server.core.universe.world.worldmap;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletionStage;
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
/*     */ public class PlayerMarkerReference
/*     */   implements WorldMapManager.MarkerReference
/*     */ {
/*     */   public static final BuilderCodec<PlayerMarkerReference> CODEC;
/*     */   private UUID player;
/*     */   private String world;
/*     */   private String markerId;
/*     */   
/*     */   static {
/* 340 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlayerMarkerReference.class, PlayerMarkerReference::new).addField(new KeyedCodec("Player", (Codec)Codec.UUID_BINARY), (playerMarkerReference, uuid) -> playerMarkerReference.player = uuid, playerMarkerReference -> playerMarkerReference.player)).addField(new KeyedCodec("World", (Codec)Codec.STRING), (playerMarkerReference, s) -> playerMarkerReference.world = s, playerMarkerReference -> playerMarkerReference.world)).addField(new KeyedCodec("MarkerId", (Codec)Codec.STRING), (playerMarkerReference, s) -> playerMarkerReference.markerId = s, playerMarkerReference -> playerMarkerReference.markerId)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PlayerMarkerReference() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerMarkerReference(@Nonnull UUID player, @Nonnull String world, @Nonnull String markerId) {
/* 350 */     this.player = player;
/* 351 */     this.world = world;
/* 352 */     this.markerId = markerId;
/*     */   }
/*     */   
/*     */   public UUID getPlayer() {
/* 356 */     return this.player;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMarkerId() {
/* 361 */     return this.markerId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() {
/* 366 */     PlayerRef playerRef = Universe.get().getPlayer(this.player);
/* 367 */     if (playerRef != null) {
/*     */       
/* 369 */       Player playerComponent = (Player)playerRef.getComponent(Player.getComponentType());
/* 370 */       removeMarkerFromOnlinePlayer(playerComponent);
/*     */     } else {
/* 372 */       removeMarkerFromOfflinePlayer();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeMarkerFromOnlinePlayer(@Nonnull Player player) {
/* 377 */     PlayerConfigData data = player.getPlayerConfigData();
/*     */ 
/*     */ 
/*     */     
/* 381 */     String world = this.world;
/* 382 */     if (world == null) world = player.getWorld().getName();
/*     */     
/* 384 */     removeMarkerFromData(data, world, this.markerId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeMarkerFromOfflinePlayer() {
/* 391 */     Universe.get().getPlayerStorage().load(this.player)
/* 392 */       .thenApply(holder -> {
/*     */           Player player = (Player)holder.getComponent(Player.getComponentType());
/*     */           
/*     */           PlayerConfigData data = player.getPlayerConfigData();
/*     */           
/*     */           String world = this.world;
/*     */           
/*     */           if (world == null) {
/*     */             world = data.getWorld();
/*     */           }
/*     */           removeMarkerFromData(data, world, this.markerId);
/*     */           return holder;
/* 404 */         }).thenCompose(holder -> Universe.get().getPlayerStorage().save(this.player, holder));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static MapMarker removeMarkerFromData(@Nonnull PlayerConfigData data, @Nonnull String worldName, @Nonnull String markerId) {
/* 409 */     PlayerWorldData perWorldData = data.getPerWorldData(worldName);
/* 410 */     MapMarker[] worldMapMarkers = perWorldData.getWorldMapMarkers();
/* 411 */     if (worldMapMarkers == null) return null;
/*     */     
/* 413 */     int index = -1;
/* 414 */     for (int i = 0; i < worldMapMarkers.length; i++) {
/* 415 */       if ((worldMapMarkers[i]).id.equals(markerId)) {
/* 416 */         index = i;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 421 */     if (index == -1) return null;
/*     */     
/* 423 */     MapMarker[] newWorldMapMarkers = new MapMarker[worldMapMarkers.length - 1];
/* 424 */     System.arraycopy(worldMapMarkers, 0, newWorldMapMarkers, 0, index);
/* 425 */     System.arraycopy(worldMapMarkers, index + 1, newWorldMapMarkers, index, newWorldMapMarkers.length - index);
/* 426 */     perWorldData.setWorldMapMarkers(newWorldMapMarkers);
/*     */     
/* 428 */     return worldMapMarkers[index];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\WorldMapManager$PlayerMarkerReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */