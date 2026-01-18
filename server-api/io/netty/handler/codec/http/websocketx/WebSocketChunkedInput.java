/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.stream.ChunkedInput;
/*     */ import io.netty.util.internal.ObjectUtil;
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
/*     */ public final class WebSocketChunkedInput
/*     */   implements ChunkedInput<WebSocketFrame>
/*     */ {
/*     */   private final ChunkedInput<ByteBuf> input;
/*     */   private final int rsv;
/*     */   
/*     */   public WebSocketChunkedInput(ChunkedInput<ByteBuf> input) {
/*  40 */     this(input, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketChunkedInput(ChunkedInput<ByteBuf> input, int rsv) {
/*  51 */     this.input = (ChunkedInput<ByteBuf>)ObjectUtil.checkNotNull(input, "input");
/*  52 */     this.rsv = rsv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEndOfInput() throws Exception {
/*  61 */     return this.input.isEndOfInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws Exception {
/*  69 */     this.input.close();
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
/*     */   @Deprecated
/*     */   public WebSocketFrame readChunk(ChannelHandlerContext ctx) throws Exception {
/*  85 */     return readChunk(ctx.alloc());
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
/*     */   public WebSocketFrame readChunk(ByteBufAllocator allocator) throws Exception {
/*  98 */     ByteBuf buf = (ByteBuf)this.input.readChunk(allocator);
/*  99 */     if (buf == null) {
/* 100 */       return null;
/*     */     }
/* 102 */     return new ContinuationWebSocketFrame(this.input.isEndOfInput(), this.rsv, buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() {
/* 107 */     return this.input.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public long progress() {
/* 112 */     return this.input.progress();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketChunkedInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */