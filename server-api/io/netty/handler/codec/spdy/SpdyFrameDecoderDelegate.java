/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ public interface SpdyFrameDecoderDelegate
/*     */ {
/*     */   void readDataFrame(int paramInt, boolean paramBoolean, ByteBuf paramByteBuf);
/*     */   
/*     */   void readSynStreamFrame(int paramInt1, int paramInt2, byte paramByte, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   void readSynReplyFrame(int paramInt, boolean paramBoolean);
/*     */   
/*     */   void readRstStreamFrame(int paramInt1, int paramInt2);
/*     */   
/*     */   void readSettingsFrame(boolean paramBoolean);
/*     */   
/*     */   void readSetting(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   void readSettingsEnd();
/*     */   
/*     */   void readPingFrame(int paramInt);
/*     */   
/*     */   void readGoAwayFrame(int paramInt1, int paramInt2);
/*     */   
/*     */   void readHeadersFrame(int paramInt, boolean paramBoolean);
/*     */   
/*     */   void readWindowUpdateFrame(int paramInt1, int paramInt2);
/*     */   
/*     */   void readHeaderBlock(ByteBuf paramByteBuf);
/*     */   
/*     */   void readHeaderBlockEnd();
/*     */   
/*     */   void readFrameError(String paramString);
/*     */   
/*     */   default void readUnknownFrame(int frameType, byte flags, ByteBuf payload) {
/* 108 */     payload.release();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyFrameDecoderDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */