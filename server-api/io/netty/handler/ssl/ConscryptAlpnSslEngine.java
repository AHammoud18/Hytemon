/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLException;
/*     */ import org.conscrypt.AllocatedBuffer;
/*     */ import org.conscrypt.BufferAllocator;
/*     */ import org.conscrypt.Conscrypt;
/*     */ import org.conscrypt.HandshakeListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ConscryptAlpnSslEngine
/*     */   extends JdkSslEngine
/*     */ {
/*  45 */   private static final boolean USE_BUFFER_ALLOCATOR = SystemPropertyUtil.getBoolean("io.netty.handler.ssl.conscrypt.useBufferAllocator", true);
/*     */ 
/*     */ 
/*     */   
/*     */   static ConscryptAlpnSslEngine newClientEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator) {
/*  50 */     return new ClientEngine(engine, alloc, applicationNegotiator);
/*     */   }
/*     */ 
/*     */   
/*     */   static ConscryptAlpnSslEngine newServerEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator) {
/*  55 */     return new ServerEngine(engine, alloc, applicationNegotiator);
/*     */   }
/*     */   
/*     */   private ConscryptAlpnSslEngine(SSLEngine engine, ByteBufAllocator alloc, List<String> protocols) {
/*  59 */     super(engine);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     if (USE_BUFFER_ALLOCATOR) {
/*  71 */       Conscrypt.setBufferAllocator(engine, new BufferAllocatorAdapter(alloc));
/*     */     }
/*     */ 
/*     */     
/*  75 */     Conscrypt.setApplicationProtocols(engine, protocols.<String>toArray(EmptyArrays.EMPTY_STRINGS));
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
/*     */   final int calculateOutNetBufSize(int plaintextBytes, int numBuffers) {
/*  88 */     return calculateSpace(plaintextBytes, numBuffers, 2147483647L);
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
/*     */   final int calculateRequiredOutBufSpace(int plaintextBytes, int numBuffers) {
/* 100 */     return calculateSpace(plaintextBytes, numBuffers, Conscrypt.maxEncryptedPacketLength());
/*     */   }
/*     */   
/*     */   private int calculateSpace(int plaintextBytes, int numBuffers, long maxPacketLength) {
/* 104 */     long maxOverhead = Conscrypt.maxSealOverhead(getWrappedEngine()) * numBuffers;
/* 105 */     return (int)Math.min(maxPacketLength, plaintextBytes + maxOverhead);
/*     */   }
/*     */   
/*     */   final SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dests) throws SSLException {
/* 109 */     return Conscrypt.unwrap(getWrappedEngine(), srcs, dests);
/*     */   }
/*     */   
/*     */   private static final class ClientEngine
/*     */     extends ConscryptAlpnSslEngine {
/*     */     private final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolListener;
/*     */     
/*     */     ClientEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator) {
/* 117 */       super(engine, alloc, applicationNegotiator.protocols());
/*     */       
/* 119 */       Conscrypt.setHandshakeListener(engine, new HandshakeListener()
/*     */           {
/*     */             public void onHandshakeFinished() throws SSLException {
/* 122 */               ConscryptAlpnSslEngine.ClientEngine.this.selectProtocol();
/*     */             }
/*     */           });
/*     */       
/* 126 */       this.protocolListener = (JdkApplicationProtocolNegotiator.ProtocolSelectionListener)ObjectUtil.checkNotNull(applicationNegotiator
/* 127 */           .protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener");
/*     */     }
/*     */ 
/*     */     
/*     */     private void selectProtocol() throws SSLException {
/* 132 */       String protocol = Conscrypt.getApplicationProtocol(getWrappedEngine());
/*     */       try {
/* 134 */         this.protocolListener.selected(protocol);
/* 135 */       } catch (Throwable e) {
/* 136 */         throw SslUtils.toSSLHandshakeException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ServerEngine
/*     */     extends ConscryptAlpnSslEngine {
/*     */     private final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector;
/*     */     
/*     */     ServerEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator) {
/* 146 */       super(engine, alloc, applicationNegotiator.protocols());
/*     */ 
/*     */       
/* 149 */       Conscrypt.setHandshakeListener(engine, new HandshakeListener()
/*     */           {
/*     */             public void onHandshakeFinished() throws SSLException {
/* 152 */               ConscryptAlpnSslEngine.ServerEngine.this.selectProtocol();
/*     */             }
/*     */           });
/*     */       
/* 156 */       this.protocolSelector = (JdkApplicationProtocolNegotiator.ProtocolSelector)ObjectUtil.checkNotNull(applicationNegotiator.protocolSelectorFactory()
/* 157 */           .newSelector(this, new LinkedHashSet<>(applicationNegotiator
/* 158 */               .protocols())), "protocolSelector");
/*     */     }
/*     */ 
/*     */     
/*     */     private void selectProtocol() throws SSLException {
/*     */       try {
/* 164 */         String protocol = Conscrypt.getApplicationProtocol(getWrappedEngine());
/* 165 */         this.protocolSelector.select((protocol != null) ? Collections.<String>singletonList(protocol) : 
/* 166 */             Collections.<String>emptyList());
/* 167 */       } catch (Throwable e) {
/* 168 */         throw SslUtils.toSSLHandshakeException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class BufferAllocatorAdapter extends BufferAllocator {
/*     */     private final ByteBufAllocator alloc;
/*     */     
/*     */     BufferAllocatorAdapter(ByteBufAllocator alloc) {
/* 177 */       this.alloc = alloc;
/*     */     }
/*     */ 
/*     */     
/*     */     public AllocatedBuffer allocateDirectBuffer(int capacity) {
/* 182 */       return new ConscryptAlpnSslEngine.BufferAdapter(this.alloc.directBuffer(capacity));
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class BufferAdapter extends AllocatedBuffer {
/*     */     private final ByteBuf nettyBuffer;
/*     */     private final ByteBuffer buffer;
/*     */     
/*     */     BufferAdapter(ByteBuf nettyBuffer) {
/* 191 */       this.nettyBuffer = nettyBuffer;
/* 192 */       this.buffer = nettyBuffer.nioBuffer(0, nettyBuffer.capacity());
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer nioBuffer() {
/* 197 */       return this.buffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public AllocatedBuffer retain() {
/* 202 */       this.nettyBuffer.retain();
/* 203 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public AllocatedBuffer release() {
/* 208 */       this.nettyBuffer.release();
/* 209 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ConscryptAlpnSslEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */