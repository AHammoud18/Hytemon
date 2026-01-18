/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageCodec;
/*    */ import io.netty.handler.codec.http.HttpMessage;
/*    */ import io.netty.util.ReferenceCountUtil;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.List;
/*    */ import java.util.Queue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpdyHttpResponseStreamIdHandler
/*    */   extends MessageToMessageCodec<Object, HttpMessage>
/*    */ {
/* 35 */   private static final Integer NO_ID = Integer.valueOf(-1);
/* 36 */   private final Queue<Integer> ids = new ArrayDeque<>();
/*    */   
/*    */   public SpdyHttpResponseStreamIdHandler() {
/* 39 */     super(Object.class, HttpMessage.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptInboundMessage(Object msg) throws Exception {
/* 44 */     return (msg instanceof HttpMessage || msg instanceof SpdyRstStreamFrame);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, HttpMessage msg, List<Object> out) throws Exception {
/* 49 */     Integer id = this.ids.poll();
/* 50 */     if (id != null && id.intValue() != NO_ID.intValue() && !msg.headers().contains((CharSequence)SpdyHttpHeaders.Names.STREAM_ID)) {
/* 51 */       msg.headers().setInt((CharSequence)SpdyHttpHeaders.Names.STREAM_ID, id.intValue());
/*    */     }
/*    */     
/* 54 */     out.add(ReferenceCountUtil.retain(msg));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/* 59 */     if (msg instanceof HttpMessage) {
/* 60 */       boolean contains = ((HttpMessage)msg).headers().contains((CharSequence)SpdyHttpHeaders.Names.STREAM_ID);
/* 61 */       if (!contains) {
/* 62 */         this.ids.add(NO_ID);
/*    */       } else {
/* 64 */         this.ids.add(((HttpMessage)msg).headers().getInt((CharSequence)SpdyHttpHeaders.Names.STREAM_ID));
/*    */       } 
/* 66 */     } else if (msg instanceof SpdyRstStreamFrame) {
/* 67 */       this.ids.remove(Integer.valueOf(((SpdyRstStreamFrame)msg).streamId()));
/*    */     } 
/*    */     
/* 70 */     out.add(ReferenceCountUtil.retain(msg));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHttpResponseStreamIdHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */