/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.TypeParameterMatcher;
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
/*     */ public abstract class MessageToMessageDecoder<I>
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   private boolean decodeCalled;
/*     */   private boolean messageProduced;
/*     */   
/*     */   protected MessageToMessageDecoder() {
/*  62 */     this.matcher = TypeParameterMatcher.find(this, MessageToMessageDecoder.class, "I");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessageToMessageDecoder(Class<? extends I> inboundMessageType) {
/*  71 */     this.matcher = TypeParameterMatcher.get(inboundMessageType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptInboundMessage(Object msg) throws Exception {
/*  79 */     return this.matcher.match(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  84 */     this.decodeCalled = true;
/*  85 */     CodecOutputList out = CodecOutputList.newInstance();
/*     */     try {
/*  87 */       if (acceptInboundMessage(msg)) {
/*     */         
/*  89 */         I cast = (I)msg;
/*     */         try {
/*  91 */           decode(ctx, cast, out);
/*     */         } finally {
/*  93 */           ReferenceCountUtil.release(cast);
/*     */         } 
/*     */       } else {
/*  96 */         out.add(msg);
/*     */       } 
/*  98 */     } catch (DecoderException e) {
/*  99 */       throw e;
/* 100 */     } catch (Exception e) {
/* 101 */       throw new DecoderException(e);
/*     */     } finally {
/*     */       try {
/* 104 */         int size = out.size();
/* 105 */         this.messageProduced |= (size > 0) ? 1 : 0;
/* 106 */         for (int i = 0; i < size; i++) {
/* 107 */           ctx.fireChannelRead(out.getUnsafe(i));
/*     */         }
/*     */       } finally {
/* 110 */         out.recycle();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 117 */     if (!isSharable()) {
/*     */       
/* 119 */       if (this.decodeCalled && !this.messageProduced && !ctx.channel().config().isAutoRead()) {
/* 120 */         ctx.read();
/*     */       }
/* 122 */       this.decodeCalled = false;
/* 123 */       this.messageProduced = false;
/*     */     } 
/* 125 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */   
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, I paramI, List<Object> paramList) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\MessageToMessageDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */