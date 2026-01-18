/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.util.ReferenceCounted;
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
/*    */ public class PingWebSocketFrame
/*    */   extends WebSocketFrame
/*    */ {
/*    */   public PingWebSocketFrame() {
/* 30 */     super(true, 0, Unpooled.buffer(0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PingWebSocketFrame(ByteBuf binaryData) {
/* 40 */     super(binaryData);
/*    */   }
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
/*    */   public PingWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
/* 54 */     super(finalFragment, rsv, binaryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public PingWebSocketFrame copy() {
/* 59 */     return (PingWebSocketFrame)super.copy();
/*    */   }
/*    */ 
/*    */   
/*    */   public PingWebSocketFrame duplicate() {
/* 64 */     return (PingWebSocketFrame)super.duplicate();
/*    */   }
/*    */ 
/*    */   
/*    */   public PingWebSocketFrame retainedDuplicate() {
/* 69 */     return (PingWebSocketFrame)super.retainedDuplicate();
/*    */   }
/*    */ 
/*    */   
/*    */   public PingWebSocketFrame replace(ByteBuf content) {
/* 74 */     return new PingWebSocketFrame(isFinalFragment(), rsv(), content);
/*    */   }
/*    */ 
/*    */   
/*    */   public PingWebSocketFrame retain() {
/* 79 */     super.retain();
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public PingWebSocketFrame retain(int increment) {
/* 85 */     super.retain(increment);
/* 86 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public PingWebSocketFrame touch() {
/* 91 */     super.touch();
/* 92 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public PingWebSocketFrame touch(Object hint) {
/* 97 */     super.touch(hint);
/* 98 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\PingWebSocketFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */