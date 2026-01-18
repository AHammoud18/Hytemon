/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
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
/*     */ public abstract class MessageToMessageCodec<INBOUND_IN, OUTBOUND_IN>
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  57 */   private final MessageToMessageDecoder<Object> decoder = new MessageToMessageDecoder(Object.class)
/*     */     {
/*     */       public boolean acceptInboundMessage(Object msg) throws Exception
/*     */       {
/*  61 */         return MessageToMessageCodec.this.acceptInboundMessage(msg);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/*  67 */         MessageToMessageCodec.this.decode(ctx, msg, out);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isSharable() {
/*  72 */         return MessageToMessageCodec.this.isSharable();
/*     */       }
/*     */     };
/*  75 */   private final MessageToMessageEncoder<Object> encoder = new MessageToMessageEncoder(Object.class)
/*     */     {
/*     */       public boolean acceptOutboundMessage(Object msg) throws Exception
/*     */       {
/*  79 */         return MessageToMessageCodec.this.acceptOutboundMessage(msg);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/*  85 */         MessageToMessageCodec.this.encode(ctx, msg, out);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isSharable() {
/*  90 */         return MessageToMessageCodec.this.isSharable();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private final TypeParameterMatcher inboundMsgMatcher;
/*     */   
/*     */   private final TypeParameterMatcher outboundMsgMatcher;
/*     */ 
/*     */   
/*     */   protected MessageToMessageCodec() {
/* 102 */     this.inboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "INBOUND_IN");
/* 103 */     this.outboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "OUTBOUND_IN");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessageToMessageCodec(Class<? extends INBOUND_IN> inboundMessageType, Class<? extends OUTBOUND_IN> outboundMessageType) {
/* 114 */     this.inboundMsgMatcher = TypeParameterMatcher.get(inboundMessageType);
/* 115 */     this.outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 120 */     this.decoder.channelRead(ctx, msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 125 */     this.decoder.channelReadComplete(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 130 */     this.encoder.write(ctx, msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptInboundMessage(Object msg) throws Exception {
/* 139 */     return this.inboundMsgMatcher.match(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 148 */     return this.outboundMsgMatcher.match(msg);
/*     */   }
/*     */   
/*     */   protected abstract void encode(ChannelHandlerContext paramChannelHandlerContext, OUTBOUND_IN paramOUTBOUND_IN, List<Object> paramList) throws Exception;
/*     */   
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, INBOUND_IN paramINBOUND_IN, List<Object> paramList) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\MessageToMessageCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */