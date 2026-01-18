/*     */ package io.netty.channel.local;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AbstractServerChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.IoEvent;
/*     */ import io.netty.channel.IoEventLoop;
/*     */ import io.netty.channel.IoEventLoopGroup;
/*     */ import io.netty.channel.IoRegistration;
/*     */ import io.netty.channel.PreferHeapByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.ServerChannelRecvByteBufAllocator;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.SingleThreadEventExecutor;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalServerChannel
/*     */   extends AbstractServerChannel
/*     */ {
/*  44 */   private final ChannelConfig config = (ChannelConfig)new DefaultChannelConfig((Channel)this, (RecvByteBufAllocator)new ServerChannelRecvByteBufAllocator()) {  }
/*     */   ;
/*  46 */   private final Queue<Object> inboundBuffer = new ArrayDeque();
/*  47 */   private final Runnable shutdownHook = new Runnable()
/*     */     {
/*     */       public void run() {
/*  50 */         LocalServerChannel.this.unsafe().close(LocalServerChannel.this.unsafe().voidPromise());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private IoRegistration registration;
/*     */   private volatile int state;
/*     */   private volatile LocalAddress localAddress;
/*     */   private volatile boolean acceptInProgress;
/*     */   
/*     */   public LocalServerChannel() {
/*  61 */     config().setAllocator((ByteBufAllocator)new PreferHeapByteBufAllocator(this.config.getAllocator()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig config() {
/*  66 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress localAddress() {
/*  71 */     return (LocalAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress remoteAddress() {
/*  76 */     return (LocalAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  81 */     return (this.state < 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  86 */     return (this.state == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/*  91 */     return (loop instanceof io.netty.channel.SingleThreadEventLoop || (loop instanceof IoEventLoopGroup && ((IoEventLoopGroup)loop)
/*  92 */       .isCompatible(LocalServerUnsafe.class)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*  97 */     return this.localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister(ChannelPromise promise) {
/* 102 */     EventLoop loop = eventLoop();
/* 103 */     if (loop instanceof IoEventLoop) {
/* 104 */       assert this.registration == null;
/* 105 */       ((IoEventLoop)loop).register((LocalServerUnsafe)unsafe()).addListener(f -> {
/*     */             if (f.isSuccess()) {
/*     */               this.registration = (IoRegistration)f.getNow();
/*     */               promise.setSuccess();
/*     */             } else {
/*     */               promise.setFailure(f.cause());
/*     */             } 
/*     */           });
/*     */     } else {
/*     */       try {
/* 115 */         ((LocalServerUnsafe)unsafe()).registered();
/* 116 */       } catch (Throwable cause) {
/* 117 */         promise.setFailure(cause);
/*     */         return;
/*     */       } 
/* 120 */       promise.setSuccess();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 126 */     this.localAddress = LocalChannelRegistry.register((Channel)this, this.localAddress, localAddress);
/* 127 */     this.state = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 132 */     if (this.state <= 1) {
/*     */       
/* 134 */       if (this.localAddress != null) {
/* 135 */         LocalChannelRegistry.unregister(this.localAddress);
/* 136 */         this.localAddress = null;
/*     */       } 
/* 138 */       this.state = 2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 144 */     EventLoop loop = eventLoop();
/* 145 */     if (loop instanceof IoEventLoop) {
/* 146 */       IoRegistration registration = this.registration;
/* 147 */       if (registration != null) {
/* 148 */         this.registration = null;
/* 149 */         registration.cancel();
/*     */       } 
/*     */     } else {
/* 152 */       ((LocalServerUnsafe)unsafe()).unregistered();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 158 */     if (this.acceptInProgress) {
/*     */       return;
/*     */     }
/*     */     
/* 162 */     Queue<Object> inboundBuffer = this.inboundBuffer;
/* 163 */     if (inboundBuffer.isEmpty()) {
/* 164 */       this.acceptInProgress = true;
/*     */       
/*     */       return;
/*     */     } 
/* 168 */     readInbound();
/*     */   }
/*     */   
/*     */   LocalChannel serve(LocalChannel peer) {
/* 172 */     final LocalChannel child = newLocalChannel(peer);
/* 173 */     if (eventLoop().inEventLoop()) {
/* 174 */       serve0(child);
/*     */     } else {
/* 176 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 179 */               LocalServerChannel.this.serve0(child);
/*     */             }
/*     */           });
/*     */     } 
/* 183 */     return child;
/*     */   }
/*     */   
/*     */   private void readInbound() {
/* 187 */     RecvByteBufAllocator.Handle handle = unsafe().recvBufAllocHandle();
/* 188 */     handle.reset(config());
/* 189 */     ChannelPipeline pipeline = pipeline();
/*     */     do {
/* 191 */       Object m = this.inboundBuffer.poll();
/* 192 */       if (m == null) {
/*     */         break;
/*     */       }
/* 195 */       pipeline.fireChannelRead(m);
/* 196 */     } while (handle.continueReading());
/* 197 */     handle.readComplete();
/* 198 */     pipeline.fireChannelReadComplete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LocalChannel newLocalChannel(LocalChannel peer) {
/* 206 */     return new LocalChannel(this, peer);
/*     */   }
/*     */   
/*     */   private void serve0(LocalChannel child) {
/* 210 */     this.inboundBuffer.add(child);
/* 211 */     if (this.acceptInProgress) {
/* 212 */       this.acceptInProgress = false;
/*     */       
/* 214 */       readInbound();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/* 220 */     return new LocalServerUnsafe();
/*     */   }
/*     */   private class LocalServerUnsafe extends AbstractChannel.AbstractUnsafe implements LocalIoHandle { private LocalServerUnsafe() {
/* 223 */       super((AbstractChannel)LocalServerChannel.this);
/*     */     }
/*     */     public void close() {
/* 226 */       close(voidPromise());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handle(IoRegistration registration, IoEvent event) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 236 */       safeSetFailure(promise, new UnsupportedOperationException());
/*     */     }
/*     */ 
/*     */     
/*     */     public void registered() {
/* 241 */       ((SingleThreadEventExecutor)LocalServerChannel.this.eventLoop()).addShutdownHook(LocalServerChannel.this.shutdownHook);
/*     */     }
/*     */ 
/*     */     
/*     */     public void unregistered() {
/* 246 */       ((SingleThreadEventExecutor)LocalServerChannel.this.eventLoop()).removeShutdownHook(LocalServerChannel.this.shutdownHook);
/*     */     }
/*     */ 
/*     */     
/*     */     public void closeNow() {
/* 251 */       close(voidPromise());
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\local\LocalServerChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */