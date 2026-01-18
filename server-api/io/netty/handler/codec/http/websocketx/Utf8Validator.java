/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.ByteProcessor;
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
/*     */ final class Utf8Validator
/*     */   implements ByteProcessor
/*     */ {
/*     */   private static final int UTF8_ACCEPT = 0;
/*     */   private static final int UTF8_REJECT = 12;
/*  48 */   private static final byte[] TYPES = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 11, 6, 6, 6, 5, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8 };
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
/*  59 */   private static final byte[] STATES = new byte[] { 0, 12, 24, 36, 60, 96, 84, 12, 12, 12, 48, 72, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0, 12, 12, 12, 12, 12, 0, 12, 0, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 24, 12, 12, 12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private int state = 0;
/*     */   
/*     */   private int codep;
/*     */   private boolean checking;
/*     */   
/*     */   public void check(ByteBuf buffer) {
/*  72 */     this.checking = true;
/*  73 */     buffer.forEachByte(this);
/*     */   }
/*     */   
/*     */   void check(ByteBuf buffer, int index, int length) {
/*  77 */     this.checking = true;
/*  78 */     buffer.forEachByte(index, length, this);
/*     */   }
/*     */   
/*     */   public void finish() {
/*  82 */     this.checking = false;
/*  83 */     this.codep = 0;
/*  84 */     if (this.state != 0) {
/*  85 */       this.state = 0;
/*  86 */       throw new CorruptedWebSocketFrameException(WebSocketCloseStatus.INVALID_PAYLOAD_DATA, "bytes are not UTF-8");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean process(byte b) throws Exception {
/*  93 */     byte type = TYPES[b & 0xFF];
/*     */     
/*  95 */     this.codep = (this.state != 0) ? (b & 0x3F | this.codep << 6) : (255 >> type & b);
/*     */     
/*  97 */     this.state = STATES[this.state + type];
/*     */     
/*  99 */     if (this.state == 12) {
/* 100 */       this.checking = false;
/* 101 */       throw new CorruptedWebSocketFrameException(WebSocketCloseStatus.INVALID_PAYLOAD_DATA, "bytes are not UTF-8");
/*     */     } 
/*     */     
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isChecking() {
/* 108 */     return this.checking;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\Utf8Validator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */