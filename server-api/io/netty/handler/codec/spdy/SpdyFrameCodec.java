/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.UnsupportedMessageTypeException;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
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
/*     */ public class SpdyFrameCodec
/*     */   extends ByteToMessageDecoder
/*     */   implements SpdyFrameDecoderDelegate, ChannelOutboundHandler
/*     */ {
/*  37 */   protected static final SpdyProtocolException INVALID_FRAME = new SpdyProtocolException("Received invalid frame");
/*     */ 
/*     */   
/*     */   protected final SpdyFrameDecoder spdyFrameDecoder;
/*     */   
/*     */   protected final SpdyFrameEncoder spdyFrameEncoder;
/*     */   
/*     */   private final SpdyHeaderBlockDecoder spdyHeaderBlockDecoder;
/*     */   
/*     */   private final SpdyHeaderBlockEncoder spdyHeaderBlockEncoder;
/*     */   
/*     */   private SpdyHeadersFrame spdyHeadersFrame;
/*     */   
/*     */   private SpdySettingsFrame spdySettingsFrame;
/*     */   
/*     */   private ChannelHandlerContext ctx;
/*     */   
/*     */   private boolean read;
/*     */   
/*     */   private final boolean validateHeaders;
/*     */   
/*     */   private final boolean supportsUnknownFrames;
/*     */ 
/*     */   
/*     */   public SpdyFrameCodec(SpdyVersion version) {
/*  62 */     this(version, true);
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
/*     */   public SpdyFrameCodec(SpdyVersion version, boolean validateHeaders) {
/*  74 */     this(version, 8192, 16384, 6, 15, 8, validateHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyFrameCodec(SpdyVersion version, int maxChunkSize, int maxHeaderSize, int compressionLevel, int windowBits, int memLevel) {
/*  84 */     this(version, maxChunkSize, maxHeaderSize, compressionLevel, windowBits, memLevel, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyFrameCodec(SpdyVersion version, int maxChunkSize, int maxHeaderSize, int compressionLevel, int windowBits, int memLevel, boolean validateHeaders) {
/*  94 */     this(version, maxChunkSize, 
/*  95 */         SpdyHeaderBlockDecoder.newInstance(version, maxHeaderSize), 
/*  96 */         SpdyHeaderBlockEncoder.newInstance(version, compressionLevel, windowBits, memLevel), validateHeaders, false);
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
/*     */   public SpdyFrameCodec(SpdyVersion version, int maxChunkSize, int maxHeaderSize, int compressionLevel, int windowBits, int memLevel, boolean validateHeaders, boolean supportsUnknownFrames) {
/* 108 */     this(version, maxChunkSize, 
/* 109 */         SpdyHeaderBlockDecoder.newInstance(version, maxHeaderSize), 
/* 110 */         SpdyHeaderBlockEncoder.newInstance(version, compressionLevel, windowBits, memLevel), validateHeaders, supportsUnknownFrames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SpdyFrameCodec(SpdyVersion version, int maxChunkSize, SpdyHeaderBlockDecoder spdyHeaderBlockDecoder, SpdyHeaderBlockEncoder spdyHeaderBlockEncoder, boolean validateHeaders) {
/* 118 */     this(version, maxChunkSize, spdyHeaderBlockDecoder, spdyHeaderBlockEncoder, validateHeaders, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SpdyFrameCodec(SpdyVersion version, int maxChunkSize, SpdyHeaderBlockDecoder spdyHeaderBlockDecoder, SpdyHeaderBlockEncoder spdyHeaderBlockEncoder, boolean validateHeaders, boolean supportsUnknownFrames) {
/* 124 */     this.supportsUnknownFrames = supportsUnknownFrames;
/* 125 */     this.spdyFrameDecoder = createDecoder(version, this, maxChunkSize);
/* 126 */     this.spdyFrameEncoder = createEncoder(version);
/* 127 */     this.spdyHeaderBlockDecoder = spdyHeaderBlockDecoder;
/* 128 */     this.spdyHeaderBlockEncoder = spdyHeaderBlockEncoder;
/* 129 */     this.validateHeaders = validateHeaders;
/*     */   }
/*     */   
/*     */   protected SpdyFrameDecoder createDecoder(SpdyVersion version, SpdyFrameDecoderDelegate delegate, int maxChunkSize) {
/* 133 */     return new SpdyFrameDecoder(version, delegate, maxChunkSize)
/*     */       {
/*     */         protected boolean isValidUnknownFrameHeader(int streamId, int type, byte flags, int length) {
/* 136 */           if (SpdyFrameCodec.this.supportsUnknownFrames) {
/* 137 */             return SpdyFrameCodec.this.isValidUnknownFrameHeader(streamId, type, flags, length);
/*     */           }
/* 139 */           return super.isValidUnknownFrameHeader(streamId, type, flags, length);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected SpdyFrameEncoder createEncoder(SpdyVersion version) {
/* 145 */     return new SpdyFrameEncoder(version);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 150 */     super.handlerAdded(ctx);
/* 151 */     this.ctx = ctx;
/* 152 */     ctx.channel().closeFuture().addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/* 155 */             SpdyFrameCodec.this.spdyHeaderBlockDecoder.end();
/* 156 */             SpdyFrameCodec.this.spdyHeaderBlockEncoder.end();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 163 */     this.spdyFrameDecoder.decode(in);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 168 */     if (!this.read && 
/* 169 */       !ctx.channel().config().isAutoRead()) {
/* 170 */       ctx.read();
/*     */     }
/*     */     
/* 173 */     this.read = false;
/* 174 */     super.channelReadComplete(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 179 */     ctx.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 185 */     ctx.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 190 */     ctx.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 195 */     ctx.close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 200 */     ctx.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 205 */     ctx.read();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 210 */     ctx.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 217 */     if (msg instanceof SpdyDataFrame) {
/*     */       
/* 219 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/*     */       try {
/* 221 */         ByteBuf frame = this.spdyFrameEncoder.encodeDataFrame(ctx
/* 222 */             .alloc(), spdyDataFrame
/* 223 */             .streamId(), spdyDataFrame
/* 224 */             .isLast(), spdyDataFrame
/* 225 */             .content());
/*     */         
/* 227 */         ctx.write(frame, promise);
/*     */       } finally {
/* 229 */         spdyDataFrame.release();
/*     */       }
/*     */     
/* 232 */     } else if (msg instanceof SpdySynStreamFrame) {
/*     */       ByteBuf frame;
/* 234 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 235 */       ByteBuf headerBlock = this.spdyHeaderBlockEncoder.encode(ctx.alloc(), spdySynStreamFrame);
/*     */       try {
/* 237 */         frame = this.spdyFrameEncoder.encodeSynStreamFrame(ctx
/* 238 */             .alloc(), spdySynStreamFrame
/* 239 */             .streamId(), spdySynStreamFrame
/* 240 */             .associatedStreamId(), spdySynStreamFrame
/* 241 */             .priority(), spdySynStreamFrame
/* 242 */             .isLast(), spdySynStreamFrame
/* 243 */             .isUnidirectional(), headerBlock);
/*     */       }
/*     */       finally {
/*     */         
/* 247 */         headerBlock.release();
/*     */       } 
/* 249 */       ctx.write(frame, promise);
/*     */     }
/* 251 */     else if (msg instanceof SpdySynReplyFrame) {
/*     */       ByteBuf frame;
/* 253 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 254 */       ByteBuf headerBlock = this.spdyHeaderBlockEncoder.encode(ctx.alloc(), spdySynReplyFrame);
/*     */       try {
/* 256 */         frame = this.spdyFrameEncoder.encodeSynReplyFrame(ctx
/* 257 */             .alloc(), spdySynReplyFrame
/* 258 */             .streamId(), spdySynReplyFrame
/* 259 */             .isLast(), headerBlock);
/*     */       }
/*     */       finally {
/*     */         
/* 263 */         headerBlock.release();
/*     */       } 
/* 265 */       ctx.write(frame, promise);
/*     */     }
/* 267 */     else if (msg instanceof SpdyRstStreamFrame) {
/*     */       
/* 269 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 270 */       ByteBuf frame = this.spdyFrameEncoder.encodeRstStreamFrame(ctx
/* 271 */           .alloc(), spdyRstStreamFrame
/* 272 */           .streamId(), spdyRstStreamFrame
/* 273 */           .status().code());
/*     */       
/* 275 */       ctx.write(frame, promise);
/*     */     }
/* 277 */     else if (msg instanceof SpdySettingsFrame) {
/*     */       
/* 279 */       SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
/* 280 */       ByteBuf frame = this.spdyFrameEncoder.encodeSettingsFrame(ctx
/* 281 */           .alloc(), spdySettingsFrame);
/*     */ 
/*     */       
/* 284 */       ctx.write(frame, promise);
/*     */     }
/* 286 */     else if (msg instanceof SpdyPingFrame) {
/*     */       
/* 288 */       SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
/* 289 */       ByteBuf frame = this.spdyFrameEncoder.encodePingFrame(ctx
/* 290 */           .alloc(), spdyPingFrame
/* 291 */           .id());
/*     */       
/* 293 */       ctx.write(frame, promise);
/*     */     }
/* 295 */     else if (msg instanceof SpdyGoAwayFrame) {
/*     */       
/* 297 */       SpdyGoAwayFrame spdyGoAwayFrame = (SpdyGoAwayFrame)msg;
/* 298 */       ByteBuf frame = this.spdyFrameEncoder.encodeGoAwayFrame(ctx
/* 299 */           .alloc(), spdyGoAwayFrame
/* 300 */           .lastGoodStreamId(), spdyGoAwayFrame
/* 301 */           .status().code());
/*     */       
/* 303 */       ctx.write(frame, promise);
/*     */     }
/* 305 */     else if (msg instanceof SpdyHeadersFrame) {
/*     */       ByteBuf frame;
/* 307 */       SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 308 */       ByteBuf headerBlock = this.spdyHeaderBlockEncoder.encode(ctx.alloc(), spdyHeadersFrame);
/*     */       try {
/* 310 */         frame = this.spdyFrameEncoder.encodeHeadersFrame(ctx
/* 311 */             .alloc(), spdyHeadersFrame
/* 312 */             .streamId(), spdyHeadersFrame
/* 313 */             .isLast(), headerBlock);
/*     */       }
/*     */       finally {
/*     */         
/* 317 */         headerBlock.release();
/*     */       } 
/* 319 */       ctx.write(frame, promise);
/*     */     }
/* 321 */     else if (msg instanceof SpdyWindowUpdateFrame) {
/*     */       
/* 323 */       SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)msg;
/* 324 */       ByteBuf frame = this.spdyFrameEncoder.encodeWindowUpdateFrame(ctx
/* 325 */           .alloc(), spdyWindowUpdateFrame
/* 326 */           .streamId(), spdyWindowUpdateFrame
/* 327 */           .deltaWindowSize());
/*     */       
/* 329 */       ctx.write(frame, promise);
/* 330 */     } else if (msg instanceof SpdyUnknownFrame) {
/* 331 */       SpdyUnknownFrame spdyUnknownFrame = (SpdyUnknownFrame)msg;
/*     */       try {
/* 333 */         ByteBuf frame = this.spdyFrameEncoder.encodeUnknownFrame(ctx
/* 334 */             .alloc(), spdyUnknownFrame
/* 335 */             .frameType(), spdyUnknownFrame
/* 336 */             .flags(), spdyUnknownFrame
/* 337 */             .content());
/* 338 */         ctx.write(frame, promise);
/*     */       } finally {
/* 340 */         spdyUnknownFrame.release();
/*     */       } 
/*     */     } else {
/* 343 */       throw new UnsupportedMessageTypeException(msg, new Class[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readDataFrame(int streamId, boolean last, ByteBuf data) {
/* 349 */     this.read = true;
/*     */     
/* 351 */     SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(streamId, data);
/* 352 */     spdyDataFrame.setLast(last);
/* 353 */     this.ctx.fireChannelRead(spdyDataFrame);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readSynStreamFrame(int streamId, int associatedToStreamId, byte priority, boolean last, boolean unidirectional) {
/* 359 */     SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamId, associatedToStreamId, priority, this.validateHeaders);
/*     */     
/* 361 */     spdySynStreamFrame.setLast(last);
/* 362 */     spdySynStreamFrame.setUnidirectional(unidirectional);
/* 363 */     this.spdyHeadersFrame = spdySynStreamFrame;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSynReplyFrame(int streamId, boolean last) {
/* 368 */     SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId, this.validateHeaders);
/* 369 */     spdySynReplyFrame.setLast(last);
/* 370 */     this.spdyHeadersFrame = spdySynReplyFrame;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readRstStreamFrame(int streamId, int statusCode) {
/* 375 */     this.read = true;
/*     */     
/* 377 */     SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, statusCode);
/* 378 */     this.ctx.fireChannelRead(spdyRstStreamFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSettingsFrame(boolean clearPersisted) {
/* 383 */     this.read = true;
/*     */     
/* 385 */     this.spdySettingsFrame = new DefaultSpdySettingsFrame();
/* 386 */     this.spdySettingsFrame.setClearPreviouslyPersistedSettings(clearPersisted);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSetting(int id, int value, boolean persistValue, boolean persisted) {
/* 391 */     this.spdySettingsFrame.setValue(id, value, persistValue, persisted);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSettingsEnd() {
/* 396 */     this.read = true;
/*     */     
/* 398 */     Object frame = this.spdySettingsFrame;
/* 399 */     this.spdySettingsFrame = null;
/* 400 */     this.ctx.fireChannelRead(frame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readPingFrame(int id) {
/* 405 */     this.read = true;
/*     */     
/* 407 */     SpdyPingFrame spdyPingFrame = new DefaultSpdyPingFrame(id);
/* 408 */     this.ctx.fireChannelRead(spdyPingFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readGoAwayFrame(int lastGoodStreamId, int statusCode) {
/* 413 */     this.read = true;
/*     */     
/* 415 */     SpdyGoAwayFrame spdyGoAwayFrame = new DefaultSpdyGoAwayFrame(lastGoodStreamId, statusCode);
/* 416 */     this.ctx.fireChannelRead(spdyGoAwayFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readHeadersFrame(int streamId, boolean last) {
/* 421 */     this.spdyHeadersFrame = new DefaultSpdyHeadersFrame(streamId, this.validateHeaders);
/* 422 */     this.spdyHeadersFrame.setLast(last);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readWindowUpdateFrame(int streamId, int deltaWindowSize) {
/* 427 */     this.read = true;
/*     */     
/* 429 */     SpdyWindowUpdateFrame spdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(streamId, deltaWindowSize);
/* 430 */     this.ctx.fireChannelRead(spdyWindowUpdateFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readHeaderBlock(ByteBuf headerBlock) {
/*     */     try {
/* 436 */       this.spdyHeaderBlockDecoder.decode(this.ctx.alloc(), headerBlock, this.spdyHeadersFrame);
/* 437 */     } catch (Exception e) {
/* 438 */       this.ctx.fireExceptionCaught(e);
/*     */     } finally {
/* 440 */       headerBlock.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readHeaderBlockEnd() {
/* 446 */     Object frame = null;
/*     */     try {
/* 448 */       this.spdyHeaderBlockDecoder.endHeaderBlock(this.spdyHeadersFrame);
/* 449 */       frame = this.spdyHeadersFrame;
/* 450 */       this.spdyHeadersFrame = null;
/* 451 */     } catch (Exception e) {
/* 452 */       this.ctx.fireExceptionCaught(e);
/*     */     } 
/* 454 */     if (frame != null) {
/* 455 */       this.read = true;
/*     */       
/* 457 */       this.ctx.fireChannelRead(frame);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readUnknownFrame(int frameType, byte flags, ByteBuf payload) {
/* 463 */     this.read = true;
/* 464 */     this.ctx.fireChannelRead(newSpdyUnknownFrame(frameType, flags, payload));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SpdyFrame newSpdyUnknownFrame(int frameType, byte flags, ByteBuf payload) {
/* 471 */     return new DefaultSpdyUnknownFrame(frameType, flags, payload);
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
/*     */   protected boolean isValidUnknownFrameHeader(int streamId, int type, byte flags, int length) {
/* 484 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFrameError(String message) {
/* 489 */     this.ctx.fireExceptionCaught(INVALID_FRAME);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyFrameCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */