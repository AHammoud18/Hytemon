/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
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
/*     */ public class SpdyFrameDecoder
/*     */ {
/*     */   protected final SpdyFrameDecoderDelegate delegate;
/*     */   protected final int spdyVersion;
/*     */   private final int maxChunkSize;
/*     */   private int frameType;
/*     */   private State state;
/*     */   private byte flags;
/*     */   private int length;
/*     */   private int streamId;
/*     */   private int numSettings;
/*     */   
/*     */   private enum State
/*     */   {
/*  66 */     READ_COMMON_HEADER,
/*  67 */     READ_DATA_FRAME,
/*  68 */     READ_SYN_STREAM_FRAME,
/*  69 */     READ_SYN_REPLY_FRAME,
/*  70 */     READ_RST_STREAM_FRAME,
/*  71 */     READ_SETTINGS_FRAME,
/*  72 */     READ_SETTING,
/*  73 */     READ_PING_FRAME,
/*  74 */     READ_GOAWAY_FRAME,
/*  75 */     READ_HEADERS_FRAME,
/*  76 */     READ_WINDOW_UPDATE_FRAME,
/*  77 */     READ_UNKNOWN_FRAME,
/*  78 */     READ_HEADER_BLOCK,
/*  79 */     DISCARD_FRAME,
/*  80 */     FRAME_ERROR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyFrameDecoder(SpdyVersion spdyVersion, SpdyFrameDecoderDelegate delegate) {
/*  88 */     this(spdyVersion, delegate, 8192);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyFrameDecoder(SpdyVersion spdyVersion, SpdyFrameDecoderDelegate delegate, int maxChunkSize) {
/*  95 */     this.spdyVersion = ((SpdyVersion)ObjectUtil.checkNotNull(spdyVersion, "spdyVersion")).version();
/*  96 */     this.delegate = (SpdyFrameDecoderDelegate)ObjectUtil.checkNotNull(delegate, "delegate");
/*  97 */     this.maxChunkSize = ObjectUtil.checkPositive(maxChunkSize, "maxChunkSize");
/*  98 */     this.state = State.READ_COMMON_HEADER; } public void decode(ByteBuf buffer) { while (true) {
/*     */       boolean last; int statusCode; int frameOffset; int flagsOffset; int lengthOffset; boolean control; int version; int dataLength; ByteBuf data; int offset; int associatedToStreamId; byte priority; boolean unidirectional; boolean clear; byte settingsFlags; int id; int value; boolean persistValue; boolean persisted;
/*     */       int pingId;
/*     */       int lastGoodStreamId;
/*     */       int deltaWindowSize;
/*     */       int compressedBytes;
/*     */       ByteBuf headerBlock;
/*     */       int numBytes;
/* 106 */       switch (this.state) {
/*     */         case READ_COMMON_HEADER:
/* 108 */           if (buffer.readableBytes() < 8) {
/*     */             return;
/*     */           }
/*     */           
/* 112 */           frameOffset = buffer.readerIndex();
/* 113 */           flagsOffset = frameOffset + 4;
/* 114 */           lengthOffset = frameOffset + 5;
/* 115 */           buffer.skipBytes(8);
/*     */           
/* 117 */           control = ((buffer.getByte(frameOffset) & 0x80) != 0);
/*     */ 
/*     */           
/* 120 */           if (control) {
/*     */             
/* 122 */             version = SpdyCodecUtil.getUnsignedShort(buffer, frameOffset) & 0x7FFF;
/* 123 */             this.frameType = SpdyCodecUtil.getUnsignedShort(buffer, frameOffset + 2);
/* 124 */             this.streamId = 0;
/*     */           } else {
/*     */             
/* 127 */             version = this.spdyVersion;
/* 128 */             this.frameType = 0;
/* 129 */             this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, frameOffset);
/*     */           } 
/*     */           
/* 132 */           this.flags = buffer.getByte(flagsOffset);
/* 133 */           this.length = SpdyCodecUtil.getUnsignedMedium(buffer, lengthOffset);
/*     */ 
/*     */           
/* 136 */           if (version != this.spdyVersion) {
/* 137 */             this.state = State.FRAME_ERROR;
/* 138 */             this.delegate.readFrameError("Invalid SPDY Version"); continue;
/* 139 */           }  if (!isValidFrameHeader(this.streamId, this.frameType, this.flags, this.length)) {
/* 140 */             this.state = State.FRAME_ERROR;
/* 141 */             this.delegate.readFrameError("Invalid Frame Error"); continue;
/* 142 */           }  if (isValidUnknownFrameHeader(this.streamId, this.frameType, this.flags, this.length)) {
/* 143 */             this.state = State.READ_UNKNOWN_FRAME; continue;
/*     */           } 
/* 145 */           this.state = getNextState(this.frameType, this.length);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_DATA_FRAME:
/* 150 */           if (this.length == 0) {
/* 151 */             this.state = State.READ_COMMON_HEADER;
/* 152 */             this.delegate.readDataFrame(this.streamId, hasFlag(this.flags, (byte)1), Unpooled.buffer(0));
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 157 */           dataLength = Math.min(this.maxChunkSize, this.length);
/*     */ 
/*     */           
/* 160 */           if (buffer.readableBytes() < dataLength) {
/*     */             return;
/*     */           }
/*     */           
/* 164 */           data = buffer.readRetainedSlice(dataLength);
/* 165 */           this.length -= dataLength;
/*     */           
/* 167 */           if (this.length == 0) {
/* 168 */             this.state = State.READ_COMMON_HEADER;
/*     */           }
/*     */           
/* 171 */           last = (this.length == 0 && hasFlag(this.flags, (byte)1));
/*     */           
/* 173 */           this.delegate.readDataFrame(this.streamId, last, data);
/*     */           continue;
/*     */         
/*     */         case READ_SYN_STREAM_FRAME:
/* 177 */           if (buffer.readableBytes() < 10) {
/*     */             return;
/*     */           }
/*     */           
/* 181 */           offset = buffer.readerIndex();
/* 182 */           this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, offset);
/* 183 */           associatedToStreamId = SpdyCodecUtil.getUnsignedInt(buffer, offset + 4);
/* 184 */           priority = (byte)(buffer.getByte(offset + 8) >> 5 & 0x7);
/* 185 */           last = hasFlag(this.flags, (byte)1);
/* 186 */           unidirectional = hasFlag(this.flags, (byte)2);
/* 187 */           buffer.skipBytes(10);
/* 188 */           this.length -= 10;
/*     */           
/* 190 */           if (this.streamId == 0) {
/* 191 */             this.state = State.FRAME_ERROR;
/* 192 */             this.delegate.readFrameError("Invalid SYN_STREAM Frame"); continue;
/*     */           } 
/* 194 */           this.state = State.READ_HEADER_BLOCK;
/* 195 */           this.delegate.readSynStreamFrame(this.streamId, associatedToStreamId, priority, last, unidirectional);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_SYN_REPLY_FRAME:
/* 200 */           if (buffer.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/* 204 */           this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 205 */           last = hasFlag(this.flags, (byte)1);
/*     */           
/* 207 */           buffer.skipBytes(4);
/* 208 */           this.length -= 4;
/*     */           
/* 210 */           if (this.streamId == 0) {
/* 211 */             this.state = State.FRAME_ERROR;
/* 212 */             this.delegate.readFrameError("Invalid SYN_REPLY Frame"); continue;
/*     */           } 
/* 214 */           this.state = State.READ_HEADER_BLOCK;
/* 215 */           this.delegate.readSynReplyFrame(this.streamId, last);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_RST_STREAM_FRAME:
/* 220 */           if (buffer.readableBytes() < 8) {
/*     */             return;
/*     */           }
/*     */           
/* 224 */           this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 225 */           statusCode = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex() + 4);
/* 226 */           buffer.skipBytes(8);
/*     */           
/* 228 */           if (this.streamId == 0 || statusCode == 0) {
/* 229 */             this.state = State.FRAME_ERROR;
/* 230 */             this.delegate.readFrameError("Invalid RST_STREAM Frame"); continue;
/*     */           } 
/* 232 */           this.state = State.READ_COMMON_HEADER;
/* 233 */           this.delegate.readRstStreamFrame(this.streamId, statusCode);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_SETTINGS_FRAME:
/* 238 */           if (buffer.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/* 242 */           clear = hasFlag(this.flags, (byte)1);
/*     */           
/* 244 */           this.numSettings = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 245 */           buffer.skipBytes(4);
/* 246 */           this.length -= 4;
/*     */ 
/*     */           
/* 249 */           if ((this.length & 0x7) != 0 || this.length >> 3 != this.numSettings) {
/* 250 */             this.state = State.FRAME_ERROR;
/* 251 */             this.delegate.readFrameError("Invalid SETTINGS Frame"); continue;
/*     */           } 
/* 253 */           this.state = State.READ_SETTING;
/* 254 */           this.delegate.readSettingsFrame(clear);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_SETTING:
/* 259 */           if (this.numSettings == 0) {
/* 260 */             this.state = State.READ_COMMON_HEADER;
/* 261 */             this.delegate.readSettingsEnd();
/*     */             
/*     */             continue;
/*     */           } 
/* 265 */           if (buffer.readableBytes() < 8) {
/*     */             return;
/*     */           }
/*     */           
/* 269 */           settingsFlags = buffer.getByte(buffer.readerIndex());
/* 270 */           id = SpdyCodecUtil.getUnsignedMedium(buffer, buffer.readerIndex() + 1);
/* 271 */           value = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex() + 4);
/* 272 */           persistValue = hasFlag(settingsFlags, (byte)1);
/* 273 */           persisted = hasFlag(settingsFlags, (byte)2);
/* 274 */           buffer.skipBytes(8);
/*     */           
/* 276 */           this.numSettings--;
/*     */           
/* 278 */           this.delegate.readSetting(id, value, persistValue, persisted);
/*     */           continue;
/*     */         
/*     */         case READ_PING_FRAME:
/* 282 */           if (buffer.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/* 286 */           pingId = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
/* 287 */           buffer.skipBytes(4);
/*     */           
/* 289 */           this.state = State.READ_COMMON_HEADER;
/* 290 */           this.delegate.readPingFrame(pingId);
/*     */           continue;
/*     */         
/*     */         case READ_GOAWAY_FRAME:
/* 294 */           if (buffer.readableBytes() < 8) {
/*     */             return;
/*     */           }
/*     */           
/* 298 */           lastGoodStreamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 299 */           statusCode = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex() + 4);
/* 300 */           buffer.skipBytes(8);
/*     */           
/* 302 */           this.state = State.READ_COMMON_HEADER;
/* 303 */           this.delegate.readGoAwayFrame(lastGoodStreamId, statusCode);
/*     */           continue;
/*     */         
/*     */         case READ_HEADERS_FRAME:
/* 307 */           if (buffer.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/* 311 */           this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 312 */           last = hasFlag(this.flags, (byte)1);
/*     */           
/* 314 */           buffer.skipBytes(4);
/* 315 */           this.length -= 4;
/*     */           
/* 317 */           if (this.streamId == 0) {
/* 318 */             this.state = State.FRAME_ERROR;
/* 319 */             this.delegate.readFrameError("Invalid HEADERS Frame"); continue;
/*     */           } 
/* 321 */           this.state = State.READ_HEADER_BLOCK;
/* 322 */           this.delegate.readHeadersFrame(this.streamId, last);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_WINDOW_UPDATE_FRAME:
/* 327 */           if (buffer.readableBytes() < 8) {
/*     */             return;
/*     */           }
/*     */           
/* 331 */           this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 332 */           deltaWindowSize = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex() + 4);
/* 333 */           buffer.skipBytes(8);
/*     */           
/* 335 */           if (deltaWindowSize == 0) {
/* 336 */             this.state = State.FRAME_ERROR;
/* 337 */             this.delegate.readFrameError("Invalid WINDOW_UPDATE Frame"); continue;
/*     */           } 
/* 339 */           this.state = State.READ_COMMON_HEADER;
/* 340 */           this.delegate.readWindowUpdateFrame(this.streamId, deltaWindowSize);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_UNKNOWN_FRAME:
/* 345 */           if (decodeUnknownFrame(this.frameType, this.flags, this.length, buffer)) {
/* 346 */             this.state = State.READ_COMMON_HEADER;
/*     */             continue;
/*     */           } 
/*     */           return;
/*     */         
/*     */         case READ_HEADER_BLOCK:
/* 352 */           if (this.length == 0) {
/* 353 */             this.state = State.READ_COMMON_HEADER;
/* 354 */             this.delegate.readHeaderBlockEnd();
/*     */             
/*     */             continue;
/*     */           } 
/* 358 */           if (!buffer.isReadable()) {
/*     */             return;
/*     */           }
/*     */           
/* 362 */           compressedBytes = Math.min(buffer.readableBytes(), this.length);
/* 363 */           headerBlock = buffer.readRetainedSlice(compressedBytes);
/* 364 */           this.length -= compressedBytes;
/*     */           
/* 366 */           this.delegate.readHeaderBlock(headerBlock);
/*     */           continue;
/*     */         
/*     */         case DISCARD_FRAME:
/* 370 */           numBytes = Math.min(buffer.readableBytes(), this.length);
/* 371 */           buffer.skipBytes(numBytes);
/* 372 */           this.length -= numBytes;
/* 373 */           if (this.length == 0) {
/* 374 */             this.state = State.READ_COMMON_HEADER;
/*     */             continue;
/*     */           } 
/*     */           return;
/*     */         
/*     */         case FRAME_ERROR:
/* 380 */           buffer.skipBytes(buffer.readableBytes()); return;
/*     */       } 
/*     */       break;
/*     */     } 
/* 384 */     throw new Error("Unexpected state: " + this.state); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasFlag(byte flags, byte flag) {
/* 390 */     return ((flags & flag) != 0);
/*     */   }
/*     */   
/*     */   private static State getNextState(int type, int length) {
/* 394 */     switch (type) {
/*     */       case 0:
/* 396 */         return State.READ_DATA_FRAME;
/*     */       
/*     */       case 1:
/* 399 */         return State.READ_SYN_STREAM_FRAME;
/*     */       
/*     */       case 2:
/* 402 */         return State.READ_SYN_REPLY_FRAME;
/*     */       
/*     */       case 3:
/* 405 */         return State.READ_RST_STREAM_FRAME;
/*     */       
/*     */       case 4:
/* 408 */         return State.READ_SETTINGS_FRAME;
/*     */       
/*     */       case 6:
/* 411 */         return State.READ_PING_FRAME;
/*     */       
/*     */       case 7:
/* 414 */         return State.READ_GOAWAY_FRAME;
/*     */       
/*     */       case 8:
/* 417 */         return State.READ_HEADERS_FRAME;
/*     */       
/*     */       case 9:
/* 420 */         return State.READ_WINDOW_UPDATE_FRAME;
/*     */     } 
/*     */ 
/*     */     
/* 424 */     if (length != 0) {
/* 425 */       return State.DISCARD_FRAME;
/*     */     }
/* 427 */     return State.READ_COMMON_HEADER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean decodeUnknownFrame(int frameType, byte flags, int length, ByteBuf buffer) {
/* 436 */     if (length == 0) {
/* 437 */       this.delegate.readUnknownFrame(frameType, flags, Unpooled.EMPTY_BUFFER);
/* 438 */       return true;
/*     */     } 
/* 440 */     if (buffer.readableBytes() < length) {
/* 441 */       return false;
/*     */     }
/* 443 */     ByteBuf data = buffer.readRetainedSlice(length);
/* 444 */     this.delegate.readUnknownFrame(frameType, flags, data);
/* 445 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidUnknownFrameHeader(int streamId, int type, byte flags, int length) {
/* 453 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isValidFrameHeader(int streamId, int type, byte flags, int length) {
/* 457 */     switch (type) {
/*     */       case 0:
/* 459 */         return (streamId != 0);
/*     */       
/*     */       case 1:
/* 462 */         return (length >= 10);
/*     */       
/*     */       case 2:
/* 465 */         return (length >= 4);
/*     */       
/*     */       case 3:
/* 468 */         return (flags == 0 && length == 8);
/*     */       
/*     */       case 4:
/* 471 */         return (length >= 4);
/*     */       
/*     */       case 6:
/* 474 */         return (length == 4);
/*     */       
/*     */       case 7:
/* 477 */         return (length == 8);
/*     */       
/*     */       case 8:
/* 480 */         return (length >= 4);
/*     */       
/*     */       case 9:
/* 483 */         return (length == 8);
/*     */     } 
/*     */     
/* 486 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyFrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */