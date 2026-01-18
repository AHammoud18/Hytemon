/*     */ package com.hypixel.hytale.builtin.landiscovery;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.io.ServerManager;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.SocketException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ class LANDiscoveryThread extends Thread {
/*  18 */   private static final byte[] REPLY_HEADER = "HYTALE_DISCOVER_REPLY".getBytes(StandardCharsets.US_ASCII);
/*  19 */   private static final byte[] REQUEST_HEADER = "HYTALE_DISCOVER_REQUEST".getBytes(StandardCharsets.US_ASCII);
/*     */   
/*     */   public static final int LAN_DISCOVERY_PORT = 5510;
/*     */   @Nonnull
/*     */   private final HytaleLogger LOGGER;
/*     */   private MulticastSocket socket;
/*     */   
/*     */   public LANDiscoveryThread() {
/*  27 */     super("LAN Discovery Listener");
/*  28 */     setDaemon(true);
/*  29 */     this.LOGGER = LANDiscoveryPlugin.get().getLogger();
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  35 */       this.socket = new MulticastSocket(5510);
/*  36 */       this.socket.setBroadcast(true);
/*     */       
/*  38 */       this.LOGGER.at(Level.INFO).log("Bound to UDP 0.0.0.0:5510 for LAN discovery");
/*     */       
/*  40 */       String name = HytaleServer.get().getServerName();
/*  41 */       if (name.length() > 16377) {
/*  42 */         name = name.substring(0, 16377) + "...";
/*     */       }
/*  44 */       byte[] serverName = name.getBytes(StandardCharsets.UTF_8);
/*     */       
/*  46 */       byte[] receiveBuf = new byte[15000];
/*  47 */       DatagramPacket packet = new DatagramPacket(receiveBuf, receiveBuf.length);
/*  48 */       while (!isInterrupted()) {
/*  49 */         this.socket.receive(packet);
/*     */         
/*  51 */         if (ArrayUtil.startsWith(packet.getData(), REQUEST_HEADER)) {
/*  52 */           InetSocketAddress publicAddress = ServerManager.get().getNonLoopbackAddress();
/*     */ 
/*     */           
/*  55 */           if (publicAddress == null)
/*     */             continue; 
/*  57 */           ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
/*  58 */           buf.writeBytes(REPLY_HEADER);
/*  59 */           buf.writeByte(0);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  64 */           InetAddress address = publicAddress.getAddress();
/*     */ 
/*     */           
/*  67 */           if (address == null || address.isLoopbackAddress()) {
/*  68 */             this.LOGGER.at(Level.WARNING).log("No public address to send as response!");
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/*  73 */           if (address instanceof java.net.Inet4Address) {
/*  74 */             buf.writeByte(4);
/*  75 */           } else if (address instanceof java.net.Inet6Address) {
/*  76 */             buf.writeByte(16);
/*     */           } else {
/*  78 */             this.LOGGER.at(Level.WARNING).log("Unrecognized target address class %s: %s", address.getClass(), address);
/*     */             continue;
/*     */           } 
/*  81 */           buf.writeBytes(address.getAddress());
/*  82 */           buf.writeShortLE(publicAddress.getPort());
/*     */ 
/*     */ 
/*     */           
/*  86 */           buf.writeShortLE(serverName.length);
/*  87 */           buf.writeBytes(serverName);
/*     */           
/*  89 */           buf.writeIntLE(Universe.get().getPlayerCount());
/*  90 */           int maxPlayers = HytaleServer.get().getConfig().getMaxPlayers();
/*  91 */           buf.writeIntLE(Math.max(maxPlayers, 0));
/*     */ 
/*     */           
/*  94 */           byte[] sendData = ByteBufUtil.getBytesRelease(buf);
/*  95 */           DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
/*  96 */           this.socket.send(sendPacket);
/*     */           
/*  98 */           this.LOGGER.at(Level.FINE).log("Was discovered by %s:%d", packet.getAddress(), packet.getPort());
/*     */         } 
/*     */       } 
/* 101 */     } catch (SocketException e) {
/* 102 */       if (!"Socket closed".equalsIgnoreCase(e.getMessage()) && !"Socket is closed".equalsIgnoreCase(e.getMessage())) {
/* 103 */         ((HytaleLogger.Api)this.LOGGER.at(Level.SEVERE).withCause(e)).log("Exception in lan discovery listener:");
/*     */       }
/* 105 */     } catch (Throwable t) {
/* 106 */       ((HytaleLogger.Api)this.LOGGER.at(Level.SEVERE).withCause(t)).log("Exception in lan discovery listener:");
/*     */     } finally {
/* 108 */       if (this.socket != null) {
/* 109 */         this.socket.close();
/*     */       }
/*     */     } 
/*     */     
/* 113 */     this.LOGGER.at(Level.INFO).log("Stopped listing on UDP 0.0.0.0:5510 for LAN discovery");
/*     */   }
/*     */   
/*     */   public MulticastSocket getSocket() {
/* 117 */     return this.socket;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\landiscovery\LANDiscoveryThread.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */