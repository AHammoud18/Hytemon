/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpClientUpgradeHandler
/*     */   extends HttpObjectAggregator
/*     */   implements ChannelOutboundHandler
/*     */ {
/*     */   private final SourceCodec sourceCodec;
/*     */   private final UpgradeCodec upgradeCodec;
/*     */   private UpgradeEvent currentUpgradeEvent;
/*     */   
/*     */   public static interface UpgradeCodec
/*     */   {
/*     */     CharSequence protocol();
/*     */     
/*     */     Collection<CharSequence> setUpgradeHeaders(ChannelHandlerContext param1ChannelHandlerContext, HttpRequest param1HttpRequest);
/*     */     
/*     */     void upgradeTo(ChannelHandlerContext param1ChannelHandlerContext, FullHttpResponse param1FullHttpResponse) throws Exception;
/*     */   }
/*     */   
/*     */   public static interface SourceCodec
/*     */   {
/*     */     void prepareUpgradeFrom(ChannelHandlerContext param1ChannelHandlerContext);
/*     */     
/*     */     void upgradeFrom(ChannelHandlerContext param1ChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public enum UpgradeEvent
/*     */   {
/*  49 */     UPGRADE_ISSUED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     UPGRADE_SUCCESSFUL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     UPGRADE_REJECTED;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientUpgradeHandler(SourceCodec sourceCodec, UpgradeCodec upgradeCodec, int maxContentLength) {
/* 119 */     super(maxContentLength);
/* 120 */     this.sourceCodec = (SourceCodec)ObjectUtil.checkNotNull(sourceCodec, "sourceCodec");
/* 121 */     this.upgradeCodec = (UpgradeCodec)ObjectUtil.checkNotNull(upgradeCodec, "upgradeCodec");
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 126 */     ctx.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 132 */     ctx.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 137 */     ctx.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 142 */     ctx.close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 147 */     ctx.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 152 */     ctx.read();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 158 */     if (!(msg instanceof HttpRequest) || this.currentUpgradeEvent == UpgradeEvent.UPGRADE_SUCCESSFUL) {
/* 159 */       ctx.write(msg, promise);
/*     */       
/*     */       return;
/*     */     } 
/* 163 */     if (this.currentUpgradeEvent == UpgradeEvent.UPGRADE_ISSUED) {
/*     */       
/* 165 */       ReferenceCountUtil.release(msg);
/* 166 */       promise.setFailure(new IllegalStateException("Attempting to write HTTP request with upgrade in progress"));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 171 */     this.currentUpgradeEvent = UpgradeEvent.UPGRADE_ISSUED;
/* 172 */     setUpgradeRequestHeaders(ctx, (HttpRequest)msg);
/*     */ 
/*     */     
/* 175 */     ctx.write(msg, promise);
/*     */ 
/*     */     
/* 178 */     ctx.fireUserEventTriggered(UpgradeEvent.UPGRADE_ISSUED);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 184 */     ctx.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/* 190 */     FullHttpResponse response = null;
/*     */     try {
/* 192 */       if (this.currentUpgradeEvent != UpgradeEvent.UPGRADE_ISSUED) {
/* 193 */         throw new IllegalStateException("Read HTTP response without requesting protocol switch");
/*     */       }
/*     */       
/* 196 */       if (msg instanceof HttpResponse) {
/* 197 */         HttpResponse rep = (HttpResponse)msg;
/* 198 */         if (!HttpResponseStatus.SWITCHING_PROTOCOLS.equals(rep.status())) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 203 */           this.currentUpgradeEvent = null;
/* 204 */           ctx.fireUserEventTriggered(UpgradeEvent.UPGRADE_REJECTED);
/* 205 */           removeThisHandler(ctx);
/* 206 */           ctx.fireChannelRead(msg);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 211 */       if (msg instanceof FullHttpResponse) {
/* 212 */         response = (FullHttpResponse)msg;
/*     */         
/* 214 */         response.retain();
/* 215 */         out.add(response);
/*     */       } else {
/*     */         
/* 218 */         super.decode(ctx, msg, out);
/* 219 */         if (out.isEmpty()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 224 */         assert out.size() == 1;
/* 225 */         response = (FullHttpResponse)out.get(0);
/*     */       } 
/*     */       
/* 228 */       CharSequence upgradeHeader = response.headers().get((CharSequence)HttpHeaderNames.UPGRADE);
/* 229 */       if (upgradeHeader != null && !AsciiString.contentEqualsIgnoreCase(this.upgradeCodec.protocol(), upgradeHeader)) {
/* 230 */         throw new IllegalStateException("Switching Protocols response with unexpected UPGRADE protocol: " + upgradeHeader);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 235 */       this.sourceCodec.prepareUpgradeFrom(ctx);
/* 236 */       this.upgradeCodec.upgradeTo(ctx, response);
/*     */ 
/*     */ 
/*     */       
/* 240 */       this.currentUpgradeEvent = UpgradeEvent.UPGRADE_SUCCESSFUL;
/*     */ 
/*     */       
/* 243 */       ctx.fireUserEventTriggered(UpgradeEvent.UPGRADE_SUCCESSFUL);
/*     */ 
/*     */ 
/*     */       
/* 247 */       this.sourceCodec.upgradeFrom(ctx);
/*     */ 
/*     */ 
/*     */       
/* 251 */       response.release();
/* 252 */       out.clear();
/* 253 */       removeThisHandler(ctx);
/* 254 */     } catch (Throwable t) {
/* 255 */       ctx.fireExceptionCaught(t);
/* 256 */       removeThisHandler(ctx);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void removeThisHandler(ChannelHandlerContext ctx) {
/* 261 */     ctx.pipeline().remove(ctx.name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setUpgradeRequestHeaders(ChannelHandlerContext ctx, HttpRequest request) {
/* 269 */     request.headers().set((CharSequence)HttpHeaderNames.UPGRADE, this.upgradeCodec.protocol());
/*     */ 
/*     */     
/* 272 */     Set<CharSequence> connectionParts = new LinkedHashSet<>(2);
/* 273 */     connectionParts.addAll(this.upgradeCodec.setUpgradeHeaders(ctx, request));
/*     */ 
/*     */     
/* 276 */     StringBuilder builder = new StringBuilder();
/* 277 */     for (CharSequence part : connectionParts) {
/* 278 */       builder.append(part);
/* 279 */       builder.append(',');
/*     */     } 
/* 281 */     builder.append((CharSequence)HttpHeaderValues.UPGRADE);
/* 282 */     request.headers().add((CharSequence)HttpHeaderNames.CONNECTION, builder.toString());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpClientUpgradeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */