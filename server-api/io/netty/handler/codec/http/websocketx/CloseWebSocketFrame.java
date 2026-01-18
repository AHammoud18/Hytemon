/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.ReferenceCounted;
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
/*     */ public class CloseWebSocketFrame
/*     */   extends WebSocketFrame
/*     */ {
/*     */   public CloseWebSocketFrame() {
/*  32 */     super(Unpooled.buffer(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CloseWebSocketFrame(WebSocketCloseStatus status) {
/*  43 */     this(requireValidStatusCode(status.code()), status.reasonText());
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
/*     */   public CloseWebSocketFrame(WebSocketCloseStatus status, String reasonText) {
/*  56 */     this(requireValidStatusCode(status.code()), reasonText);
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
/*     */   public CloseWebSocketFrame(int statusCode, String reasonText) {
/*  69 */     this(true, 0, requireValidStatusCode(statusCode), reasonText);
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
/*     */   public CloseWebSocketFrame(boolean finalFragment, int rsv) {
/*  81 */     this(finalFragment, rsv, Unpooled.buffer(0));
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
/*     */   public CloseWebSocketFrame(boolean finalFragment, int rsv, int statusCode, String reasonText) {
/*  98 */     super(finalFragment, rsv, newBinaryData(requireValidStatusCode(statusCode), reasonText));
/*     */   }
/*     */   
/*     */   private static ByteBuf newBinaryData(int statusCode, String reasonText) {
/* 102 */     if (reasonText == null) {
/* 103 */       reasonText = "";
/*     */     }
/*     */     
/* 106 */     ByteBuf binaryData = Unpooled.buffer(2 + reasonText.length());
/* 107 */     binaryData.writeShort(statusCode);
/* 108 */     if (!reasonText.isEmpty()) {
/* 109 */       binaryData.writeCharSequence(reasonText, CharsetUtil.UTF_8);
/*     */     }
/* 111 */     return binaryData;
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
/*     */   public CloseWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
/* 125 */     super(finalFragment, rsv, binaryData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int statusCode() {
/* 133 */     ByteBuf binaryData = content();
/* 134 */     if (binaryData == null || binaryData.readableBytes() < 2) {
/* 135 */       return -1;
/*     */     }
/*     */     
/* 138 */     return binaryData.getUnsignedShort(binaryData.readerIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String reasonText() {
/* 146 */     ByteBuf binaryData = content();
/* 147 */     if (binaryData == null || binaryData.readableBytes() <= 2) {
/* 148 */       return "";
/*     */     }
/*     */     
/* 151 */     return binaryData.toString(binaryData.readerIndex() + 2, binaryData.readableBytes() - 2, CharsetUtil.UTF_8);
/*     */   }
/*     */ 
/*     */   
/*     */   public CloseWebSocketFrame copy() {
/* 156 */     return (CloseWebSocketFrame)super.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public CloseWebSocketFrame duplicate() {
/* 161 */     return (CloseWebSocketFrame)super.duplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public CloseWebSocketFrame retainedDuplicate() {
/* 166 */     return (CloseWebSocketFrame)super.retainedDuplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public CloseWebSocketFrame replace(ByteBuf content) {
/* 171 */     return new CloseWebSocketFrame(isFinalFragment(), rsv(), content);
/*     */   }
/*     */ 
/*     */   
/*     */   public CloseWebSocketFrame retain() {
/* 176 */     super.retain();
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public CloseWebSocketFrame retain(int increment) {
/* 182 */     super.retain(increment);
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public CloseWebSocketFrame touch() {
/* 188 */     super.touch();
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public CloseWebSocketFrame touch(Object hint) {
/* 194 */     super.touch(hint);
/* 195 */     return this;
/*     */   }
/*     */   
/*     */   static int requireValidStatusCode(int statusCode) {
/* 199 */     if (WebSocketCloseStatus.isValidStatusCode(statusCode)) {
/* 200 */       return statusCode;
/*     */     }
/* 202 */     throw new IllegalArgumentException("WebSocket close status code does NOT comply with RFC-6455: " + statusCode);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\CloseWebSocketFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */