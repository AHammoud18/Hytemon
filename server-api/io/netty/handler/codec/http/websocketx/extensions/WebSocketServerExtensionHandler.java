/*     */ package io.netty.handler.codec.http.websocketx.extensions;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.LastHttpContent;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class WebSocketServerExtensionHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*     */   private final List<WebSocketServerExtensionHandshaker> extensionHandshakers;
/*  58 */   private final Queue<List<WebSocketServerExtension>> validExtensions = new ArrayDeque<>(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerExtensionHandler(WebSocketServerExtensionHandshaker... extensionHandshakers) {
/*  69 */     this.extensionHandshakers = Arrays.asList((WebSocketServerExtensionHandshaker[])ObjectUtil.checkNonEmpty((Object[])extensionHandshakers, "extensionHandshakers"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  79 */     if (msg != LastHttpContent.EMPTY_LAST_CONTENT) {
/*  80 */       if (msg instanceof io.netty.handler.codec.http.DefaultHttpRequest) {
/*     */         
/*  82 */         onHttpRequestChannelRead(ctx, (HttpRequest)msg);
/*  83 */       } else if (msg instanceof HttpRequest) {
/*     */         
/*  85 */         onHttpRequestChannelRead(ctx, (HttpRequest)msg);
/*     */       } else {
/*  87 */         super.channelRead(ctx, msg);
/*     */       } 
/*     */     } else {
/*  90 */       super.channelRead(ctx, msg);
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
/*     */   protected void onHttpRequestChannelRead(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
/* 122 */     List<WebSocketServerExtension> validExtensionsList = null;
/*     */     
/* 124 */     if (WebSocketExtensionUtil.isWebsocketUpgrade(request.headers())) {
/* 125 */       String extensionsHeader = request.headers().getAsString((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
/*     */       
/* 127 */       if (extensionsHeader != null) {
/*     */         
/* 129 */         List<WebSocketExtensionData> extensions = WebSocketExtensionUtil.extractExtensions(extensionsHeader);
/* 130 */         int rsv = 0;
/*     */         
/* 132 */         for (WebSocketExtensionData extensionData : extensions) {
/*     */           
/* 134 */           Iterator<WebSocketServerExtensionHandshaker> extensionHandshakersIterator = this.extensionHandshakers.iterator();
/* 135 */           WebSocketServerExtension validExtension = null;
/*     */           
/* 137 */           while (validExtension == null && extensionHandshakersIterator.hasNext()) {
/*     */             
/* 139 */             WebSocketServerExtensionHandshaker extensionHandshaker = extensionHandshakersIterator.next();
/* 140 */             validExtension = extensionHandshaker.handshakeExtension(extensionData);
/*     */           } 
/*     */           
/* 143 */           if (validExtension != null && (validExtension.rsv() & rsv) == 0) {
/* 144 */             if (validExtensionsList == null) {
/* 145 */               validExtensionsList = new ArrayList<>(1);
/*     */             }
/* 147 */             rsv |= validExtension.rsv();
/* 148 */             validExtensionsList.add(validExtension);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     if (validExtensionsList == null) {
/* 155 */       validExtensionsList = Collections.emptyList();
/*     */     }
/* 157 */     this.validExtensions.offer(validExtensionsList);
/* 158 */     super.channelRead(ctx, request);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 163 */     if (msg != Unpooled.EMPTY_BUFFER && !(msg instanceof io.netty.buffer.ByteBuf)) {
/* 164 */       if (msg instanceof io.netty.handler.codec.http.DefaultHttpResponse) {
/* 165 */         onHttpResponseWrite(ctx, (HttpResponse)msg, promise);
/* 166 */       } else if (msg instanceof HttpResponse) {
/* 167 */         onHttpResponseWrite(ctx, (HttpResponse)msg, promise);
/*     */       } else {
/* 169 */         super.write(ctx, msg, promise);
/*     */       } 
/*     */     } else {
/* 172 */       super.write(ctx, msg, promise);
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
/*     */   protected void onHttpResponseWrite(ChannelHandlerContext ctx, HttpResponse response, ChannelPromise promise) throws Exception {
/* 204 */     List<WebSocketServerExtension> validExtensionsList = this.validExtensions.poll();
/*     */     
/* 206 */     if (HttpResponseStatus.SWITCHING_PROTOCOLS.equals(response.status())) {
/* 207 */       handlePotentialUpgrade(ctx, promise, response, validExtensionsList);
/*     */     }
/* 209 */     super.write(ctx, response, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void handlePotentialUpgrade(final ChannelHandlerContext ctx, ChannelPromise promise, HttpResponse httpResponse, final List<WebSocketServerExtension> validExtensionsList) {
/* 215 */     HttpHeaders headers = httpResponse.headers();
/*     */     
/* 217 */     if (WebSocketExtensionUtil.isWebsocketUpgrade(headers)) {
/* 218 */       if (validExtensionsList != null && !validExtensionsList.isEmpty()) {
/* 219 */         String headerValue = headers.getAsString((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
/*     */         
/* 221 */         List<WebSocketExtensionData> extraExtensions = new ArrayList<>(this.extensionHandshakers.size());
/* 222 */         for (WebSocketServerExtension extension : validExtensionsList) {
/* 223 */           extraExtensions.add(extension.newReponseData());
/*     */         }
/*     */         
/* 226 */         String newHeaderValue = WebSocketExtensionUtil.computeMergeExtensionsHeaderValue(headerValue, extraExtensions);
/* 227 */         promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) {
/* 230 */                 if (future.isSuccess()) {
/* 231 */                   for (WebSocketServerExtension extension : validExtensionsList) {
/* 232 */                     WebSocketExtensionDecoder decoder = extension.newExtensionDecoder();
/* 233 */                     WebSocketExtensionEncoder encoder = extension.newExtensionEncoder();
/* 234 */                     String name = ctx.name();
/* 235 */                     ctx.pipeline()
/* 236 */                       .addAfter(name, decoder.getClass().getName(), (ChannelHandler)decoder)
/* 237 */                       .addAfter(name, encoder.getClass().getName(), (ChannelHandler)encoder);
/*     */                   } 
/*     */                 }
/*     */               }
/*     */             });
/*     */         
/* 243 */         if (newHeaderValue != null) {
/* 244 */           headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS, newHeaderValue);
/*     */         }
/*     */       } 
/*     */       
/* 248 */       promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) {
/* 251 */               if (future.isSuccess())
/* 252 */                 ctx.pipeline().remove((ChannelHandler)WebSocketServerExtensionHandler.this); 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\WebSocketServerExtensionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */