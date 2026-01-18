/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
/*    */ import io.netty.util.LeakPresenceDetector;
/*    */ import java.util.List;
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
/*    */ @Sharable
/*    */ public class WebSocket00FrameEncoder
/*    */   extends MessageToMessageEncoder<WebSocketFrame>
/*    */   implements WebSocketFrameEncoder
/*    */ {
/* 35 */   private static final ByteBuf _0X00 = (ByteBuf)LeakPresenceDetector.staticInitializer(() -> Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(0)).asReadOnly());
/*    */   
/* 37 */   private static final ByteBuf _0XFF = (ByteBuf)LeakPresenceDetector.staticInitializer(() -> Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(-1)).asReadOnly());
/*    */   
/* 39 */   private static final ByteBuf _0XFF_0X00 = (ByteBuf)LeakPresenceDetector.staticInitializer(() -> Unpooled.unreleasableBuffer(Unpooled.directBuffer(2, 2).writeByte(-1).writeByte(0)).asReadOnly());
/*    */ 
/*    */   
/*    */   public WebSocket00FrameEncoder() {
/* 43 */     super(WebSocketFrame.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/* 48 */     if (msg instanceof TextWebSocketFrame) {
/*    */       
/* 50 */       ByteBuf data = msg.content();
/*    */       
/* 52 */       out.add(_0X00.duplicate());
/* 53 */       out.add(data.retain());
/* 54 */       out.add(_0XFF.duplicate());
/* 55 */     } else if (msg instanceof CloseWebSocketFrame) {
/*    */ 
/*    */       
/* 58 */       out.add(_0XFF_0X00.duplicate());
/*    */     } else {
/*    */       
/* 61 */       ByteBuf data = msg.content();
/* 62 */       int dataLen = data.readableBytes();
/*    */       
/* 64 */       ByteBuf buf = ctx.alloc().buffer(5);
/* 65 */       boolean release = true;
/*    */       
/*    */       try {
/* 68 */         buf.writeByte(-128);
/*    */ 
/*    */         
/* 71 */         int b1 = dataLen >>> 28 & 0x7F;
/* 72 */         int b2 = dataLen >>> 14 & 0x7F;
/* 73 */         int b3 = dataLen >>> 7 & 0x7F;
/* 74 */         int b4 = dataLen & 0x7F;
/* 75 */         if (b1 == 0) {
/* 76 */           if (b2 == 0) {
/* 77 */             if (b3 != 0) {
/* 78 */               buf.writeByte(b3 | 0x80);
/*    */             }
/* 80 */             buf.writeByte(b4);
/*    */           } else {
/* 82 */             buf.writeByte(b2 | 0x80);
/* 83 */             buf.writeByte(b3 | 0x80);
/* 84 */             buf.writeByte(b4);
/*    */           } 
/*    */         } else {
/* 87 */           buf.writeByte(b1 | 0x80);
/* 88 */           buf.writeByte(b2 | 0x80);
/* 89 */           buf.writeByte(b3 | 0x80);
/* 90 */           buf.writeByte(b4);
/*    */         } 
/*    */ 
/*    */         
/* 94 */         out.add(buf);
/* 95 */         out.add(data.retain());
/* 96 */         release = false;
/*    */       } finally {
/* 98 */         if (release)
/* 99 */           buf.release(); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocket00FrameEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */