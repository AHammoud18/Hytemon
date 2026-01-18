/*    */ package com.hypixel.hytale.server.core.io.handlers;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import com.hypixel.hytale.server.core.io.ProtocolVersion;
/*    */ import io.netty.channel.Channel;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GenericPacketHandler
/*    */   extends PacketHandler
/*    */ {
/*    */   private static final Consumer<Packet> EMPTY_CONSUMER = packet -> {
/*    */     
/*    */     };
/*    */   
/*    */   @Nonnull
/*    */   public static Consumer<Packet>[] newHandlerArray(int size) {
/* 24 */     return (Consumer<Packet>[])new Consumer[size];
/*    */   }
/*    */   @Nonnull
/* 27 */   protected final List<SubPacketHandler> packetHandlers = (List<SubPacketHandler>)new ObjectArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Consumer<Packet>[] handlers;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GenericPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion) {
/* 39 */     super(channel, protocolVersion);
/* 40 */     this.handlers = newHandlerArray(0);
/*    */   }
/*    */   
/*    */   public void registerSubPacketHandler(SubPacketHandler subPacketHandler) {
/* 44 */     this.packetHandlers.add(subPacketHandler);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void registerHandler(int packetId, @Nonnull Consumer<Packet> handler) {
/* 54 */     if (packetId >= this.handlers.length) {
/* 55 */       Consumer[] arrayOfConsumer = (Consumer[])newHandlerArray(packetId + 1);
/* 56 */       System.arraycopy(this.handlers, 0, arrayOfConsumer, 0, this.handlers.length);
/* 57 */       this.handlers = (Consumer<Packet>[])arrayOfConsumer;
/*    */     } 
/* 59 */     this.handlers[packetId] = handler;
/*    */   }
/*    */   
/*    */   public void registerNoOpHandlers(@Nonnull int... packetIds) {
/* 63 */     for (int packetId : packetIds) {
/* 64 */       registerHandler(packetId, EMPTY_CONSUMER);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public final void accept(@Nonnull Packet packet) {
/* 70 */     int packetId = packet.getId();
/* 71 */     Consumer<Packet> handler = (this.handlers.length > packetId) ? this.handlers[packetId] : null;
/* 72 */     if (handler != null) {
/*    */       try {
/* 74 */         handler.accept(packet);
/* 75 */       } catch (Throwable e) {
/* 76 */         throw new RuntimeException("Could not handle packet (" + packetId + "): " + String.valueOf(packet), e);
/*    */       } 
/*    */     } else {
/* 79 */       throw new RuntimeException("No handler is registered for (" + packetId + "): " + String.valueOf(packet));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\handlers\GenericPacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */