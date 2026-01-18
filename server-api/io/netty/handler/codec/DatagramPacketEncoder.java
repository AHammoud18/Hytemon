/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
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
/*     */ public class DatagramPacketEncoder<M>
/*     */   extends MessageToMessageEncoder<AddressedEnvelope<M, InetSocketAddress>>
/*     */ {
/*     */   private final MessageToMessageEncoder<? super M> encoder;
/*     */   
/*     */   public DatagramPacketEncoder(MessageToMessageEncoder<? super M> encoder) {
/*  56 */     this.encoder = (MessageToMessageEncoder<? super M>)ObjectUtil.checkNotNull(encoder, "encoder");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/*  61 */     if (super.acceptOutboundMessage(msg)) {
/*     */       
/*  63 */       AddressedEnvelope envelope = (AddressedEnvelope)msg;
/*  64 */       return (this.encoder.acceptOutboundMessage(envelope.content()) && (envelope
/*  65 */         .sender() instanceof InetSocketAddress || envelope.sender() == null) && envelope
/*  66 */         .recipient() instanceof InetSocketAddress);
/*     */     } 
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, AddressedEnvelope<M, InetSocketAddress> msg, List<Object> out) throws Exception {
/*  74 */     assert out.isEmpty();
/*     */     
/*  76 */     this.encoder.encode(ctx, (M)msg.content(), out);
/*  77 */     if (out.size() != 1) {
/*  78 */       throw new EncoderException(
/*  79 */           StringUtil.simpleClassName(this.encoder) + " must produce only one message.");
/*     */     }
/*  81 */     Object content = out.get(0);
/*  82 */     if (content instanceof ByteBuf) {
/*     */       
/*  84 */       out.set(0, new DatagramPacket((ByteBuf)content, (InetSocketAddress)msg.recipient(), (InetSocketAddress)msg.sender()));
/*     */     } else {
/*  86 */       throw new EncoderException(
/*  87 */           StringUtil.simpleClassName(this.encoder) + " must produce only ByteBuf.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/*  93 */     this.encoder.bind(ctx, localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 100 */     this.encoder.connect(ctx, remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 105 */     this.encoder.disconnect(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 110 */     this.encoder.close(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 115 */     this.encoder.deregister(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 120 */     this.encoder.read(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 125 */     this.encoder.flush(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 130 */     this.encoder.handlerAdded(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 135 */     this.encoder.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 140 */     this.encoder.exceptionCaught(ctx, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSharable() {
/* 145 */     return this.encoder.isSharable();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\DatagramPacketEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */