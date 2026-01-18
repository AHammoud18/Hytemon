/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.MessageAggregator;
/*    */ import io.netty.util.internal.StringUtil;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WebSocketFrameAggregator
/*    */   extends MessageAggregator<WebSocketFrame, WebSocketFrame, ContinuationWebSocketFrame, WebSocketFrame>
/*    */ {
/*    */   public WebSocketFrameAggregator(int maxContentLength) {
/* 41 */     super(maxContentLength, WebSocketFrame.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isStartMessage(WebSocketFrame msg) throws Exception {
/* 46 */     return (msg instanceof TextWebSocketFrame || msg instanceof BinaryWebSocketFrame);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isContentMessage(WebSocketFrame msg) throws Exception {
/* 51 */     return msg instanceof ContinuationWebSocketFrame;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isLastContentMessage(ContinuationWebSocketFrame msg) throws Exception {
/* 56 */     return (isContentMessage(msg) && msg.isFinalFragment());
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isAggregated(WebSocketFrame msg) throws Exception {
/* 61 */     if (msg.isFinalFragment()) {
/* 62 */       return !isContentMessage(msg);
/*    */     }
/*    */     
/* 65 */     return (!isStartMessage(msg) && !isContentMessage(msg));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isContentLengthInvalid(WebSocketFrame start, int maxContentLength) {
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object newContinueResponse(WebSocketFrame start, int maxContentLength, ChannelPipeline pipeline) {
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean closeAfterContinueResponse(Object msg) throws Exception {
/* 80 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean ignoreContentAfterContinueResponse(Object msg) throws Exception {
/* 85 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   protected WebSocketFrame beginAggregation(WebSocketFrame start, ByteBuf content) throws Exception {
/* 90 */     if (start instanceof TextWebSocketFrame) {
/* 91 */       return new TextWebSocketFrame(true, start.rsv(), content);
/*    */     }
/*    */     
/* 94 */     if (start instanceof BinaryWebSocketFrame) {
/* 95 */       return new BinaryWebSocketFrame(true, start.rsv(), content);
/*    */     }
/*    */ 
/*    */     
/* 99 */     throw new Error("Unexpected websocket frame type: " + StringUtil.className(start));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketFrameAggregator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */