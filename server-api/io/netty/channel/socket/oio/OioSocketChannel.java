/*     */ package io.netty.channel.socket.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ConnectTimeoutException;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.oio.OioByteStreamChannel;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.SocketChannel;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketTimeoutException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class OioSocketChannel
/*     */   extends OioByteStreamChannel
/*     */   implements SocketChannel
/*     */ {
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSocketChannel.class);
/*     */ 
/*     */   
/*     */   private final Socket socket;
/*     */   
/*     */   private final OioSocketChannelConfig config;
/*     */ 
/*     */   
/*     */   public OioSocketChannel() {
/*  56 */     this(new Socket());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSocketChannel(Socket socket) {
/*  65 */     this((Channel)null, socket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSocketChannel(Channel parent, Socket socket) {
/*  76 */     super(parent);
/*  77 */     this.socket = socket;
/*  78 */     this.config = new DefaultOioSocketChannelConfig(this, socket);
/*     */     
/*  80 */     boolean success = false;
/*     */     try {
/*  82 */       if (socket.isConnected()) {
/*  83 */         activate(socket.getInputStream(), socket.getOutputStream());
/*     */       }
/*  85 */       socket.setSoTimeout(1000);
/*  86 */       success = true;
/*  87 */     } catch (Exception e) {
/*  88 */       throw new ChannelException("failed to initialize a socket", e);
/*     */     } finally {
/*  90 */       if (!success) {
/*     */         try {
/*  92 */           socket.close();
/*  93 */         } catch (IOException e) {
/*  94 */           logger.warn("Failed to close a socket.", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannel parent() {
/* 102 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig config() {
/* 107 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 112 */     return !this.socket.isClosed();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 117 */     return (!this.socket.isClosed() && this.socket.isConnected());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 122 */     return (this.socket.isOutputShutdown() || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInputShutdown() {
/* 127 */     return (this.socket.isInputShutdown() || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 132 */     return ((this.socket.isInputShutdown() && this.socket.isOutputShutdown()) || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void doShutdownOutput() throws Exception {
/* 137 */     shutdownOutput0();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput() {
/* 142 */     return shutdownOutput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownInput() {
/* 147 */     return shutdownInput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdown() {
/* 152 */     return shutdown(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadBytes(ByteBuf buf) throws Exception {
/* 157 */     if (this.socket.isClosed()) {
/* 158 */       return -1;
/*     */     }
/*     */     try {
/* 161 */       return super.doReadBytes(buf);
/* 162 */     } catch (SocketTimeoutException ignored) {
/* 163 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
/* 169 */     EventLoop loop = eventLoop();
/* 170 */     if (loop.inEventLoop()) {
/* 171 */       shutdownOutput0(promise);
/*     */     } else {
/* 173 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 176 */               OioSocketChannel.this.shutdownOutput0(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 180 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void shutdownOutput0(ChannelPromise promise) {
/*     */     try {
/* 185 */       shutdownOutput0();
/* 186 */       promise.setSuccess();
/* 187 */     } catch (Throwable t) {
/* 188 */       promise.setFailure(t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void shutdownOutput0() throws IOException {
/* 193 */     this.socket.shutdownOutput();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownInput(final ChannelPromise promise) {
/* 198 */     EventLoop loop = eventLoop();
/* 199 */     if (loop.inEventLoop()) {
/* 200 */       shutdownInput0(promise);
/*     */     } else {
/* 202 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 205 */               OioSocketChannel.this.shutdownInput0(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 209 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void shutdownInput0(ChannelPromise promise) {
/*     */     try {
/* 214 */       this.socket.shutdownInput();
/* 215 */       promise.setSuccess();
/* 216 */     } catch (Throwable t) {
/* 217 */       promise.setFailure(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdown(final ChannelPromise promise) {
/* 223 */     ChannelFuture shutdownOutputFuture = shutdownOutput();
/* 224 */     if (shutdownOutputFuture.isDone()) {
/* 225 */       shutdownOutputDone(shutdownOutputFuture, promise);
/*     */     } else {
/* 227 */       shutdownOutputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture shutdownOutputFuture) throws Exception {
/* 230 */               OioSocketChannel.this.shutdownOutputDone(shutdownOutputFuture, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 234 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void shutdownOutputDone(final ChannelFuture shutdownOutputFuture, final ChannelPromise promise) {
/* 238 */     ChannelFuture shutdownInputFuture = shutdownInput();
/* 239 */     if (shutdownInputFuture.isDone()) {
/* 240 */       shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*     */     } else {
/* 242 */       shutdownInputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture shutdownInputFuture) throws Exception {
/* 245 */               OioSocketChannel.shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void shutdownDone(ChannelFuture shutdownOutputFuture, ChannelFuture shutdownInputFuture, ChannelPromise promise) {
/* 254 */     Throwable shutdownOutputCause = shutdownOutputFuture.cause();
/* 255 */     Throwable shutdownInputCause = shutdownInputFuture.cause();
/* 256 */     if (shutdownOutputCause != null) {
/* 257 */       if (shutdownInputCause != null) {
/* 258 */         logger.debug("Exception suppressed because a previous exception occurred.", shutdownInputCause);
/*     */       }
/*     */       
/* 261 */       promise.setFailure(shutdownOutputCause);
/* 262 */     } else if (shutdownInputCause != null) {
/* 263 */       promise.setFailure(shutdownInputCause);
/*     */     } else {
/* 265 */       promise.setSuccess();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 271 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 276 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 281 */     return this.socket.getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 286 */     return this.socket.getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 291 */     SocketUtils.bind(this.socket, localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 297 */     if (localAddress != null) {
/* 298 */       SocketUtils.bind(this.socket, localAddress);
/*     */     }
/*     */     
/* 301 */     int connectTimeoutMillis = config().getConnectTimeoutMillis();
/* 302 */     boolean success = false;
/*     */     try {
/* 304 */       SocketUtils.connect(this.socket, remoteAddress, connectTimeoutMillis);
/* 305 */       activate(this.socket.getInputStream(), this.socket.getOutputStream());
/* 306 */       success = true;
/* 307 */     } catch (SocketTimeoutException e) {
/* 308 */       ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out after " + connectTimeoutMillis + " ms: " + remoteAddress);
/*     */       
/* 310 */       cause.setStackTrace(e.getStackTrace());
/* 311 */       throw cause;
/*     */     } finally {
/* 313 */       if (!success) {
/* 314 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 321 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 326 */     this.socket.close();
/*     */   }
/*     */   
/*     */   protected boolean checkInputShutdown() {
/* 330 */     if (isInputShutdown()) {
/*     */       try {
/* 332 */         Thread.sleep(config().getSoTimeout());
/* 333 */       } catch (Throwable throwable) {}
/*     */ 
/*     */       
/* 336 */       return true;
/*     */     } 
/* 338 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void setReadPending(boolean readPending) {
/* 344 */     super.setReadPending(readPending);
/*     */   }
/*     */   
/*     */   final void clearReadPending0() {
/* 348 */     clearReadPending();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\oio\OioSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */