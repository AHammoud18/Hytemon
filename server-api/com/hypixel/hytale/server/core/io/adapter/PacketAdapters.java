/*     */ package com.hypixel.hytale.server.core.io.adapter;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.handlers.game.GamePacketHandler;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class PacketAdapters
/*     */ {
/*  15 */   private static final List<PacketFilter> inboundHandlers = new CopyOnWriteArrayList<>();
/*  16 */   private static final List<PacketFilter> outboundHandlers = new CopyOnWriteArrayList<>();
/*     */   
/*     */   @Nonnull
/*     */   public static PacketFilter registerInbound(@Nonnull PacketWatcher watcher) {
/*  20 */     PacketFilter out = (packetListener, packet) -> {
/*     */         watcher.accept(packetListener, packet);
/*     */         return false;
/*     */       };
/*  24 */     registerInbound(out);
/*  25 */     return out;
/*     */   }
/*     */   
/*     */   public static void registerInbound(PacketFilter predicate) {
/*  29 */     inboundHandlers.add(predicate);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PacketFilter registerOutbound(@Nonnull PacketWatcher watcher) {
/*  34 */     PacketFilter out = (packetListener, packet) -> {
/*     */         watcher.accept(packetListener, packet);
/*     */         return false;
/*     */       };
/*  38 */     registerOutbound(out);
/*  39 */     return out;
/*     */   }
/*     */   
/*     */   public static void registerOutbound(PacketFilter predicate) {
/*  43 */     outboundHandlers.add(predicate);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PacketFilter registerInbound(@Nonnull PlayerPacketFilter filter) {
/*  48 */     PacketFilter out = (packetHandler, client) -> (packetHandler instanceof GamePacketHandler && filter.test(((GamePacketHandler)packetHandler).getPlayerRef(), client));
/*  49 */     registerInbound(out);
/*  50 */     return out;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PacketFilter registerOutbound(@Nonnull PlayerPacketFilter filter) {
/*  55 */     PacketFilter out = (packetHandler, server) -> (packetHandler instanceof GamePacketHandler && filter.test(((GamePacketHandler)packetHandler).getPlayerRef(), server));
/*  56 */     registerOutbound(out);
/*  57 */     return out;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PacketFilter registerInbound(@Nonnull PlayerPacketWatcher watcher) {
/*  62 */     PacketFilter out = (packetHandler, client) -> {
/*     */         if (packetHandler instanceof GamePacketHandler) {
/*     */           watcher.accept(((GamePacketHandler)packetHandler).getPlayerRef(), client);
/*     */         }
/*     */         return false;
/*     */       };
/*  68 */     registerInbound(out);
/*  69 */     return out;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PacketFilter registerOutbound(@Nonnull PlayerPacketWatcher watcher) {
/*  74 */     PacketFilter out = (packetHandler, server) -> {
/*     */         if (packetHandler instanceof GamePacketHandler) {
/*     */           watcher.accept(((GamePacketHandler)packetHandler).getPlayerRef(), server);
/*     */         }
/*     */         return false;
/*     */       };
/*  80 */     registerOutbound(out);
/*  81 */     return out;
/*     */   }
/*     */   
/*     */   public static void deregisterInbound(PacketFilter predicate) {
/*  85 */     if (!inboundHandlers.remove(predicate)) throw new IllegalArgumentException("That handler was not registered to inbound!"); 
/*     */   }
/*     */   
/*     */   public static void deregisterOutbound(PacketFilter predicate) {
/*  89 */     if (!outboundHandlers.remove(predicate)) throw new IllegalArgumentException("That handler was not registered to outbound!"); 
/*     */   }
/*     */   
/*     */   public static boolean __handleInbound(PacketHandler player, Packet packet) {
/*  93 */     return handle(inboundHandlers, player, packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends Packet> boolean handle(@Nonnull List<PacketFilter> list, PacketHandler player, T packet) {
/* 101 */     for (int i = 0; i < list.size(); i++) {
/*     */       try {
/* 103 */         if (((PacketFilter)list.get(i)).test(player, (Packet)packet)) {
/* 104 */           return true;
/*     */         }
/* 106 */       } catch (Throwable t) {
/* 107 */         ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(t)).log("Failed to test packet %s against %s:", packet, player);
/*     */       } 
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean __handleOutbound(PacketHandler player, Packet packet) {
/* 114 */     return handle(outboundHandlers, player, packet);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\adapter\PacketAdapters.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */