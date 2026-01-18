/*     */ package com.hypixel.hytale.server.core.modules.serverplayerlist;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.event.EventRegistry;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.metrics.metric.HistoricMetric;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.connection.PongType;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.AddToServerPlayerList;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.RemoveFromServerPlayerList;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.ServerPlayerListPlayer;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.ServerPlayerListUpdate;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.UpdateServerPlayerList;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.UpdateServerPlayerListPing;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerPlayerListModule
/*     */   extends JavaPlugin
/*     */ {
/*     */   @Nonnull
/*  39 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(ServerPlayerListModule.class)
/*  40 */     .depends(new Class[] { Universe.class
/*  41 */       }).build();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int PING_UPDATE_INTERVAL_SECONDS = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ServerPlayerListModule instance;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ServerPlayerListModule get() {
/*  58 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerPlayerListModule(@Nonnull JavaPluginInit init) {
/*  67 */     super(init);
/*  68 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  73 */     EventRegistry eventRegistry = getEventRegistry();
/*  74 */     eventRegistry.register(PlayerConnectEvent.class, this::onPlayerConnect);
/*  75 */     eventRegistry.register(PlayerDisconnectEvent.class, this::onPlayerDisconnect);
/*  76 */     eventRegistry.registerGlobal(AddPlayerToWorldEvent.class, this::onPlayerAddedToWorld);
/*     */ 
/*     */     
/*  79 */     HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(this::broadcastPingUpdates, 10L, 10L, TimeUnit.SECONDS);
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
/*     */   private void onPlayerConnect(@Nonnull PlayerConnectEvent event) {
/*  95 */     PlayerRef joiningPlayerRef = event.getPlayerRef();
/*  96 */     UUID joiningPlayerUuid = joiningPlayerRef.getUuid();
/*     */ 
/*     */     
/*  99 */     List<PlayerRef> allPlayers = Universe.get().getPlayers();
/*     */ 
/*     */     
/* 102 */     ServerPlayerListPlayer[] serverListPlayers = new ServerPlayerListPlayer[allPlayers.size()];
/* 103 */     int index = 0;
/* 104 */     for (PlayerRef playerRef : allPlayers) {
/* 105 */       serverListPlayers[index++] = createServerPlayerListPlayer(playerRef);
/*     */     }
/*     */     
/* 108 */     AddToServerPlayerList fullListPacket = new AddToServerPlayerList(serverListPlayers);
/* 109 */     joiningPlayerRef.getPacketHandler().write((Packet)fullListPacket);
/*     */ 
/*     */ 
/*     */     
/* 113 */     AddToServerPlayerList newPlayerPacket = new AddToServerPlayerList(new ServerPlayerListPlayer[] { createServerPlayerListPlayer(joiningPlayerRef) });
/*     */ 
/*     */     
/* 116 */     for (PlayerRef playerRef : allPlayers) {
/* 117 */       if (!playerRef.getUuid().equals(joiningPlayerUuid)) {
/* 118 */         playerRef.getPacketHandler().write((Packet)newPlayerPacket);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onPlayerDisconnect(@Nonnull PlayerDisconnectEvent event) {
/* 130 */     PlayerRef leavingPlayerRef = event.getPlayerRef();
/* 131 */     UUID leavingPlayerUuid = leavingPlayerRef.getUuid();
/*     */ 
/*     */     
/* 134 */     RemoveFromServerPlayerList removePacket = new RemoveFromServerPlayerList(new UUID[] { leavingPlayerUuid });
/*     */     
/* 136 */     for (PlayerRef playerRef : Universe.get().getPlayers()) {
/* 137 */       if (!playerRef.getUuid().equals(leavingPlayerUuid)) {
/* 138 */         playerRef.getPacketHandler().write((Packet)removePacket);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onPlayerAddedToWorld(@Nonnull AddPlayerToWorldEvent event) {
/* 150 */     Holder<EntityStore> holder = event.getHolder();
/* 151 */     PlayerRef playerRefComponent = (PlayerRef)holder.getComponent(PlayerRef.getComponentType());
/* 152 */     if (playerRefComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 156 */     UUID playerUuid = playerRefComponent.getUuid();
/* 157 */     UUID worldUuid = event.getWorld().getWorldConfig().getUuid();
/*     */ 
/*     */     
/* 160 */     UpdateServerPlayerList updatePacket = new UpdateServerPlayerList(new ServerPlayerListUpdate[] { new ServerPlayerListUpdate(playerUuid, worldUuid) });
/*     */ 
/*     */ 
/*     */     
/* 164 */     for (PlayerRef otherPlayerRef : Universe.get().getPlayers()) {
/* 165 */       otherPlayerRef.getPacketHandler().write((Packet)updatePacket);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void broadcastPingUpdates() {
/* 173 */     List<PlayerRef> allPlayers = Universe.get().getPlayers();
/* 174 */     if (allPlayers.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 179 */     Object2IntOpenHashMap<UUID> pingMap = new Object2IntOpenHashMap(allPlayers.size());
/* 180 */     for (PlayerRef playerRef : allPlayers) {
/* 181 */       pingMap.put(playerRef.getUuid(), getPingValue(playerRef.getPacketHandler()));
/*     */     }
/*     */     
/* 184 */     UpdateServerPlayerListPing packet = new UpdateServerPlayerListPing((Map)pingMap);
/* 185 */     for (PlayerRef playerRef : allPlayers) {
/* 186 */       playerRef.getPacketHandler().writeNoCache((Packet)packet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getPingValue(@Nonnull PacketHandler handler) {
/* 197 */     HistoricMetric historicMetric = handler.getPingInfo(PongType.Direct).getPingMetricSet();
/* 198 */     double average = historicMetric.getAverage(0);
/* 199 */     return (int)PacketHandler.PingInfo.TIME_UNIT.toMillis(MathUtil.fastCeil(average));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static ServerPlayerListPlayer createServerPlayerListPlayer(@Nonnull PlayerRef playerRef) {
/* 210 */     return new ServerPlayerListPlayer(playerRef
/* 211 */         .getUuid(), playerRef
/* 212 */         .getUsername(), playerRef
/* 213 */         .getWorldUuid(), 
/* 214 */         getPingValue(playerRef.getPacketHandler()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\serverplayerlist\ServerPlayerListModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */