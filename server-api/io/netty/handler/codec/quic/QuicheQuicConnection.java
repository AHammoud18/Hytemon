/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.ResourceLeakDetector;
/*     */ import io.netty.util.ResourceLeakDetectorFactory;
/*     */ import io.netty.util.ResourceLeakTracker;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QuicheQuicConnection
/*     */ {
/*  31 */   private static final int TOTAL_RECV_INFO_SIZE = Quiche.SIZEOF_QUICHE_RECV_INFO + Quiche.SIZEOF_SOCKADDR_STORAGE + Quiche.SIZEOF_SOCKADDR_STORAGE;
/*     */ 
/*     */   
/*  34 */   private static final ResourceLeakDetector<QuicheQuicConnection> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(QuicheQuicConnection.class);
/*     */ 
/*     */   
/*     */   private final QuicheQuicSslEngine engine;
/*     */ 
/*     */   
/*     */   private final ResourceLeakTracker<QuicheQuicConnection> leakTracker;
/*     */ 
/*     */   
/*     */   final long ssl;
/*     */   
/*     */   private ReferenceCounted refCnt;
/*     */   
/*     */   private final ByteBuf recvInfoBuffer;
/*     */   
/*     */   private final ByteBuf sendInfoBuffer;
/*     */   
/*     */   private boolean sendInfoFirst = true;
/*     */   
/*     */   private final ByteBuffer recvInfoBuffer1;
/*     */   
/*     */   private final ByteBuffer sendInfoBuffer1;
/*     */   
/*     */   private final ByteBuffer sendInfoBuffer2;
/*     */   
/*     */   private long connection;
/*     */ 
/*     */   
/*     */   QuicheQuicConnection(long connection, long ssl, QuicheQuicSslEngine engine, ReferenceCounted refCnt) {
/*  63 */     assert connection != -1L;
/*  64 */     this.connection = connection;
/*  65 */     this.ssl = ssl;
/*  66 */     this.engine = engine;
/*  67 */     this.refCnt = refCnt;
/*     */     
/*  69 */     this.recvInfoBuffer = Quiche.allocateNativeOrder(TOTAL_RECV_INFO_SIZE);
/*  70 */     this.sendInfoBuffer = Quiche.allocateNativeOrder(2 * Quiche.SIZEOF_QUICHE_SEND_INFO);
/*     */ 
/*     */     
/*  73 */     this.recvInfoBuffer.setZero(0, this.recvInfoBuffer.capacity());
/*  74 */     this.sendInfoBuffer.setZero(0, this.sendInfoBuffer.capacity());
/*     */     
/*  76 */     this.recvInfoBuffer1 = this.recvInfoBuffer.nioBuffer(0, TOTAL_RECV_INFO_SIZE);
/*  77 */     this.sendInfoBuffer1 = this.sendInfoBuffer.nioBuffer(0, Quiche.SIZEOF_QUICHE_SEND_INFO);
/*  78 */     this.sendInfoBuffer2 = this.sendInfoBuffer.nioBuffer(Quiche.SIZEOF_QUICHE_SEND_INFO, Quiche.SIZEOF_QUICHE_SEND_INFO);
/*  79 */     this.engine.connection = this;
/*  80 */     this.leakTracker = leakDetector.track(this);
/*     */   }
/*     */   
/*     */   synchronized void reattach(ReferenceCounted refCnt) {
/*  84 */     this.refCnt.release();
/*  85 */     this.refCnt = refCnt;
/*     */   }
/*     */   
/*     */   void free() {
/*  89 */     free(true);
/*     */   }
/*     */   
/*     */   boolean isFreed() {
/*  93 */     return (this.connection == -1L);
/*     */   }
/*     */   
/*     */   private void free(boolean closeLeakTracker) {
/*  97 */     boolean release = false;
/*  98 */     synchronized (this) {
/*  99 */       if (this.connection != -1L) {
/*     */         try {
/* 101 */           BoringSSL.SSL_cleanup(this.ssl);
/* 102 */           Quiche.quiche_conn_free(this.connection);
/* 103 */           this.engine.ctx.remove(this.engine);
/* 104 */           release = true;
/* 105 */           this.refCnt.release();
/*     */         } finally {
/* 107 */           this.connection = -1L;
/*     */         } 
/*     */       }
/*     */     } 
/* 111 */     if (release) {
/* 112 */       this.recvInfoBuffer.release();
/* 113 */       this.sendInfoBuffer.release();
/* 114 */       if (closeLeakTracker && this.leakTracker != null) {
/* 115 */         this.leakTracker.close(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   Runnable sslTask() {
/*     */     Runnable task;
/* 123 */     synchronized (this) {
/* 124 */       if (this.connection != -1L) {
/* 125 */         task = BoringSSL.SSL_getTask(this.ssl);
/*     */       } else {
/* 127 */         task = null;
/*     */       } 
/*     */     } 
/* 130 */     if (task == null) {
/* 131 */       return null;
/*     */     }
/*     */     
/* 134 */     return () -> {
/*     */         if (this.connection == -1L) {
/*     */           return;
/*     */         }
/*     */         task.run();
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   QuicConnectionAddress sourceId() {
/* 145 */     return connectionId(() -> Quiche.quiche_conn_source_id(this.connection));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   QuicConnectionAddress destinationId() {
/* 150 */     return connectionId(() -> Quiche.quiche_conn_destination_id(this.connection));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   QuicConnectionAddress connectionId(Supplier<byte[]> idSupplier) {
/*     */     byte[] id;
/* 156 */     synchronized (this) {
/* 157 */       if (this.connection == -1L) {
/* 158 */         return null;
/*     */       }
/* 160 */       id = idSupplier.get();
/*     */     } 
/* 162 */     return (id == null) ? QuicConnectionAddress.NULL_LEN : new QuicConnectionAddress(id);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   QuicheQuicTransportParameters peerParameters() {
/*     */     long[] ret;
/* 168 */     synchronized (this) {
/* 169 */       if (this.connection == -1L) {
/* 170 */         return null;
/*     */       }
/* 172 */       ret = Quiche.quiche_conn_peer_transport_params(this.connection);
/*     */     } 
/* 174 */     if (ret == null) {
/* 175 */       return null;
/*     */     }
/* 177 */     return new QuicheQuicTransportParameters(ret);
/*     */   }
/*     */   
/*     */   QuicheQuicSslEngine engine() {
/* 181 */     return this.engine;
/*     */   }
/*     */   
/*     */   long address() {
/* 185 */     assert this.connection != -1L;
/* 186 */     return this.connection;
/*     */   }
/*     */   
/*     */   void init(InetSocketAddress local, InetSocketAddress remote, Consumer<String> sniSelectedCallback) {
/* 190 */     assert this.connection != -1L;
/* 191 */     assert this.recvInfoBuffer.refCnt() != 0;
/* 192 */     assert this.sendInfoBuffer.refCnt() != 0;
/*     */ 
/*     */     
/* 195 */     QuicheRecvInfo.setRecvInfo(this.recvInfoBuffer1, remote, local);
/*     */ 
/*     */     
/* 198 */     QuicheSendInfo.setSendInfo(this.sendInfoBuffer1, local, remote);
/* 199 */     QuicheSendInfo.setSendInfo(this.sendInfoBuffer2, local, remote);
/* 200 */     this.engine.sniSelectedCallback = sniSelectedCallback;
/*     */   }
/*     */   
/*     */   ByteBuffer nextRecvInfo() {
/* 204 */     assert this.recvInfoBuffer.refCnt() != 0;
/* 205 */     return this.recvInfoBuffer1;
/*     */   }
/*     */   
/*     */   ByteBuffer nextSendInfo() {
/* 209 */     assert this.sendInfoBuffer.refCnt() != 0;
/* 210 */     this.sendInfoFirst = !this.sendInfoFirst;
/* 211 */     return this.sendInfoFirst ? this.sendInfoBuffer1 : this.sendInfoBuffer2;
/*     */   }
/*     */   
/*     */   boolean isSendInfoChanged() {
/* 215 */     assert this.sendInfoBuffer.refCnt() != 0;
/* 216 */     return !QuicheSendInfo.isSameAddress(this.sendInfoBuffer1, this.sendInfoBuffer2);
/*     */   }
/*     */   
/*     */   boolean isClosed() {
/* 220 */     return (isFreed() || Quiche.quiche_conn_is_closed(this.connection));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 228 */       free(false);
/*     */     } finally {
/* 230 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */