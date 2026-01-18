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
/*     */ public class TextWebSocketFrame
/*     */   extends WebSocketFrame
/*     */ {
/*     */   public TextWebSocketFrame() {
/*  31 */     super(Unpooled.buffer(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame(String text) {
/*  41 */     super(fromText(text));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame(ByteBuf binaryData) {
/*  51 */     super(binaryData);
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
/*     */   public TextWebSocketFrame(boolean finalFragment, int rsv, String text) {
/*  65 */     super(finalFragment, rsv, fromText(text));
/*     */   }
/*     */   
/*     */   private static ByteBuf fromText(String text) {
/*  69 */     if (text == null || text.isEmpty()) {
/*  70 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*  72 */     return Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);
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
/*     */   public TextWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
/*  87 */     super(finalFragment, rsv, binaryData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String text() {
/*  94 */     return content().toString(CharsetUtil.UTF_8);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame copy() {
/*  99 */     return (TextWebSocketFrame)super.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame duplicate() {
/* 104 */     return (TextWebSocketFrame)super.duplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame retainedDuplicate() {
/* 109 */     return (TextWebSocketFrame)super.retainedDuplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame replace(ByteBuf content) {
/* 114 */     return new TextWebSocketFrame(isFinalFragment(), rsv(), content);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame retain() {
/* 119 */     super.retain();
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame retain(int increment) {
/* 125 */     super.retain(increment);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame touch() {
/* 131 */     super.touch();
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame touch(Object hint) {
/* 137 */     super.touch(hint);
/* 138 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\TextWebSocketFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */