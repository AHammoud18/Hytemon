/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
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
/*     */ public class Utf8FrameValidator
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final boolean closeOnProtocolViolation;
/*     */   private int fragmentedFramesCount;
/*     */   private Utf8Validator utf8Validator;
/*     */   
/*     */   public Utf8FrameValidator() {
/*  34 */     this(true);
/*     */   }
/*     */   
/*     */   public Utf8FrameValidator(boolean closeOnProtocolViolation) {
/*  38 */     this.closeOnProtocolViolation = closeOnProtocolViolation;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isControlFrame(WebSocketFrame frame) {
/*  43 */     return (frame instanceof CloseWebSocketFrame || frame instanceof PingWebSocketFrame || frame instanceof PongWebSocketFrame);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  50 */     if (msg instanceof WebSocketFrame) {
/*  51 */       WebSocketFrame frame = (WebSocketFrame)msg;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  56 */         if (frame.isFinalFragment()) {
/*     */ 
/*     */           
/*  59 */           if (!isControlFrame(frame)) {
/*     */ 
/*     */             
/*  62 */             this.fragmentedFramesCount = 0;
/*     */ 
/*     */             
/*  65 */             if (frame instanceof TextWebSocketFrame || (this.utf8Validator != null && this.utf8Validator
/*  66 */               .isChecking()))
/*     */             {
/*  68 */               checkUTF8String(frame.content());
/*     */ 
/*     */ 
/*     */               
/*  72 */               this.utf8Validator.finish();
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/*  78 */           if (this.fragmentedFramesCount == 0) {
/*     */             
/*  80 */             if (frame instanceof TextWebSocketFrame) {
/*  81 */               checkUTF8String(frame.content());
/*     */             
/*     */             }
/*     */           }
/*  85 */           else if (this.utf8Validator != null && this.utf8Validator.isChecking()) {
/*  86 */             checkUTF8String(frame.content());
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*  91 */           this.fragmentedFramesCount++;
/*     */         } 
/*  93 */       } catch (CorruptedWebSocketFrameException e) {
/*  94 */         protocolViolation(ctx, frame, e);
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     super.channelRead(ctx, msg);
/*     */   }
/*     */   
/*     */   private void checkUTF8String(ByteBuf buffer) {
/* 102 */     if (this.utf8Validator == null) {
/* 103 */       this.utf8Validator = new Utf8Validator();
/*     */     }
/* 105 */     this.utf8Validator.check(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   private void protocolViolation(ChannelHandlerContext ctx, WebSocketFrame frame, CorruptedWebSocketFrameException ex) {
/* 110 */     frame.release();
/* 111 */     if (this.closeOnProtocolViolation && ctx.channel().isOpen()) {
/* 112 */       WebSocketCloseStatus closeStatus = ex.closeStatus();
/* 113 */       String reasonText = ex.getMessage();
/* 114 */       if (reasonText == null) {
/* 115 */         reasonText = closeStatus.reasonText();
/*     */       }
/*     */       
/* 118 */       CloseWebSocketFrame closeFrame = new CloseWebSocketFrame(closeStatus.code(), reasonText);
/* 119 */       ctx.writeAndFlush(closeFrame).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */     } 
/*     */     
/* 122 */     throw ex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 127 */     super.exceptionCaught(ctx, cause);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\Utf8FrameValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */