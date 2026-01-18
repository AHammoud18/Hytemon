/*     */ package io.netty.handler.codec.http.websocketx.extensions;
/*     */ 
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.CodecException;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class WebSocketClientExtensionHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*     */   private final List<WebSocketClientExtensionHandshaker> extensionHandshakers;
/*     */   
/*     */   public WebSocketClientExtensionHandler(WebSocketClientExtensionHandshaker... extensionHandshakers) {
/*  55 */     this.extensionHandshakers = Arrays.asList((WebSocketClientExtensionHandshaker[])ObjectUtil.checkNonEmpty((Object[])extensionHandshakers, "extensionHandshakers"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*  60 */     if (msg instanceof HttpRequest && WebSocketExtensionUtil.isWebsocketUpgrade(((HttpRequest)msg).headers())) {
/*  61 */       HttpRequest request = (HttpRequest)msg;
/*  62 */       String headerValue = request.headers().getAsString((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
/*     */       
/*  64 */       List<WebSocketExtensionData> extraExtensions = new ArrayList<>(this.extensionHandshakers.size());
/*  65 */       for (WebSocketClientExtensionHandshaker extensionHandshaker : this.extensionHandshakers) {
/*  66 */         extraExtensions.add(extensionHandshaker.newRequestData());
/*     */       }
/*     */       
/*  69 */       String newHeaderValue = WebSocketExtensionUtil.computeMergeExtensionsHeaderValue(headerValue, extraExtensions);
/*     */       
/*  71 */       request.headers().set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS, newHeaderValue);
/*     */     } 
/*     */     
/*  74 */     super.write(ctx, msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  80 */     if (msg instanceof HttpResponse) {
/*  81 */       HttpResponse response = (HttpResponse)msg;
/*     */       
/*  83 */       if (WebSocketExtensionUtil.isWebsocketUpgrade(response.headers())) {
/*  84 */         String extensionsHeader = response.headers().getAsString((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
/*     */         
/*  86 */         if (extensionsHeader != null) {
/*     */           
/*  88 */           List<WebSocketExtensionData> extensions = WebSocketExtensionUtil.extractExtensions(extensionsHeader);
/*     */           
/*  90 */           List<WebSocketClientExtension> validExtensions = new ArrayList<>(extensions.size());
/*  91 */           int rsv = 0;
/*     */           
/*  93 */           for (WebSocketExtensionData extensionData : extensions) {
/*     */             
/*  95 */             Iterator<WebSocketClientExtensionHandshaker> extensionHandshakersIterator = this.extensionHandshakers.iterator();
/*  96 */             WebSocketClientExtension validExtension = null;
/*     */             
/*  98 */             while (validExtension == null && extensionHandshakersIterator.hasNext()) {
/*     */               
/* 100 */               WebSocketClientExtensionHandshaker extensionHandshaker = extensionHandshakersIterator.next();
/* 101 */               validExtension = extensionHandshaker.handshakeExtension(extensionData);
/*     */             } 
/*     */             
/* 104 */             if (validExtension != null && (validExtension.rsv() & rsv) == 0) {
/* 105 */               rsv |= validExtension.rsv();
/* 106 */               validExtensions.add(validExtension); continue;
/*     */             } 
/* 108 */             throw new CodecException("invalid WebSocket Extension handshake for \"" + extensionsHeader + '"');
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 113 */           for (WebSocketClientExtension validExtension : validExtensions) {
/* 114 */             WebSocketExtensionDecoder decoder = validExtension.newExtensionDecoder();
/* 115 */             WebSocketExtensionEncoder encoder = validExtension.newExtensionEncoder();
/* 116 */             ctx.pipeline().addAfter(ctx.name(), decoder.getClass().getName(), (ChannelHandler)decoder);
/* 117 */             ctx.pipeline().addAfter(ctx.name(), encoder.getClass().getName(), (ChannelHandler)encoder);
/*     */           } 
/*     */         } 
/*     */         
/* 121 */         ctx.pipeline().remove(ctx.name());
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     super.channelRead(ctx, msg);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\WebSocketClientExtensionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */