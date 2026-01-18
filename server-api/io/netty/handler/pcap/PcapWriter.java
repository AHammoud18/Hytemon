/*     */ package io.netty.handler.pcap;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PcapWriter
/*     */   implements Closeable
/*     */ {
/*  31 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PcapWriter.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final PcapWriteHandler pcapWriteHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   private final OutputStream outputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PcapWriter(PcapWriteHandler pcapWriteHandler) throws IOException {
/*  46 */     this.pcapWriteHandler = pcapWriteHandler;
/*  47 */     this.outputStream = pcapWriteHandler.outputStream();
/*     */ 
/*     */     
/*  50 */     if (pcapWriteHandler.writePcapGlobalHeader() && !pcapWriteHandler.sharedOutputStream()) {
/*  51 */       PcapHeaders.writeGlobalHeader(pcapWriteHandler.outputStream());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void writePacket(ByteBuf packetHeaderBuf, ByteBuf packet) throws IOException {
/*  63 */     if (this.pcapWriteHandler.state() == State.CLOSED) {
/*  64 */       logger.debug("Pcap Write attempted on closed PcapWriter");
/*     */     }
/*     */     
/*  67 */     long timestamp = System.currentTimeMillis();
/*     */     
/*  69 */     PcapHeaders.writePacketHeader(packetHeaderBuf, (int)(timestamp / 1000L), (int)(timestamp % 1000L * 1000L), packet
/*     */ 
/*     */ 
/*     */         
/*  73 */         .readableBytes(), packet
/*  74 */         .readableBytes());
/*     */ 
/*     */     
/*  77 */     if (this.pcapWriteHandler.sharedOutputStream()) {
/*  78 */       synchronized (this.outputStream) {
/*  79 */         packetHeaderBuf.readBytes(this.outputStream, packetHeaderBuf.readableBytes());
/*  80 */         packet.readBytes(this.outputStream, packet.readableBytes());
/*     */       } 
/*     */     } else {
/*  83 */       packetHeaderBuf.readBytes(this.outputStream, packetHeaderBuf.readableBytes());
/*  84 */       packet.readBytes(this.outputStream, packet.readableBytes());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return "PcapWriter{outputStream=" + this.outputStream + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  97 */     if (this.pcapWriteHandler.state() == State.CLOSED) {
/*  98 */       logger.debug("PcapWriter is already closed");
/*     */     } else {
/* 100 */       if (this.pcapWriteHandler.sharedOutputStream()) {
/* 101 */         synchronized (this.outputStream) {
/* 102 */           this.outputStream.flush();
/*     */         } 
/*     */       } else {
/* 105 */         this.outputStream.flush();
/* 106 */         this.outputStream.close();
/*     */       } 
/* 108 */       this.pcapWriteHandler.markClosed();
/* 109 */       logger.debug("PcapWriter is now closed");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\pcap\PcapWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */