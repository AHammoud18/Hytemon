/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.quic.QuicChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamType;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ final class Http3CodecUtils
/*     */ {
/*     */   static final long MIN_RESERVED_FRAME_TYPE = 64L;
/*     */   static final long MAX_RESERVED_FRAME_TYPE = 66571993090L;
/*     */   static final int HTTP3_DATA_FRAME_TYPE = 0;
/*     */   static final int HTTP3_HEADERS_FRAME_TYPE = 1;
/*     */   static final int HTTP3_CANCEL_PUSH_FRAME_TYPE = 3;
/*     */   static final int HTTP3_SETTINGS_FRAME_TYPE = 4;
/*     */   static final int HTTP3_PUSH_PROMISE_FRAME_TYPE = 5;
/*     */   static final int HTTP3_GO_AWAY_FRAME_TYPE = 7;
/*     */   static final int HTTP3_MAX_PUSH_ID_FRAME_TYPE = 13;
/*     */   static final int HTTP3_CANCEL_PUSH_FRAME_MAX_LEN = 8;
/*     */   static final int HTTP3_SETTINGS_FRAME_MAX_LEN = 256;
/*     */   static final int HTTP3_GO_AWAY_FRAME_MAX_LEN = 8;
/*     */   static final int HTTP3_MAX_PUSH_ID_FRAME_MAX_LEN = 8;
/*     */   static final int HTTP3_CONTROL_STREAM_TYPE = 0;
/*     */   static final int HTTP3_PUSH_STREAM_TYPE = 1;
/*     */   static final int HTTP3_QPACK_ENCODER_STREAM_TYPE = 2;
/*     */   static final int HTTP3_QPACK_DECODER_STREAM_TYPE = 3;
/*     */   
/*     */   static long checkIsReservedFrameType(long type) {
/*  63 */     return ObjectUtil.checkInRange(type, 64L, 66571993090L, "type");
/*     */   }
/*     */   
/*     */   static boolean isReservedFrameType(long type) {
/*  67 */     return (type >= 64L && type <= 66571993090L);
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
/*     */   static boolean isServerInitiatedQuicStream(QuicStreamChannel channel) {
/*  79 */     return (channel.streamId() % 2L != 0L);
/*     */   }
/*     */   
/*     */   static boolean isReservedHttp2FrameType(long type) {
/*  83 */     switch ((int)type) {
/*     */ 
/*     */       
/*     */       case 2:
/*     */       case 6:
/*     */       case 8:
/*     */       case 9:
/*  90 */         return true;
/*     */     } 
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isReservedHttp2Setting(long key) {
/*  99 */     return (2L <= key && key <= 5L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int numBytesForVariableLengthInteger(long value) {
/* 109 */     if (value <= 63L) {
/* 110 */       return 1;
/*     */     }
/* 112 */     if (value <= 16383L) {
/* 113 */       return 2;
/*     */     }
/* 115 */     if (value <= 1073741823L) {
/* 116 */       return 4;
/*     */     }
/* 118 */     if (value <= 4611686018427387903L) {
/* 119 */       return 8;
/*     */     }
/* 121 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void writeVariableLengthInteger(ByteBuf out, long value) {
/* 131 */     int numBytes = numBytesForVariableLengthInteger(value);
/* 132 */     writeVariableLengthInteger(out, value, numBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void writeVariableLengthInteger(ByteBuf out, long value, int numBytes) {
/* 142 */     int writerIndex = out.writerIndex();
/* 143 */     switch (numBytes) {
/*     */       case 1:
/* 145 */         out.writeByte((byte)(int)value);
/*     */         return;
/*     */       case 2:
/* 148 */         out.writeShort((short)(int)value);
/* 149 */         encodeLengthIntoBuffer(out, writerIndex, (byte)64);
/*     */         return;
/*     */       case 4:
/* 152 */         out.writeInt((int)value);
/* 153 */         encodeLengthIntoBuffer(out, writerIndex, -128);
/*     */         return;
/*     */       case 8:
/* 156 */         out.writeLong(value);
/* 157 */         encodeLengthIntoBuffer(out, writerIndex, (byte)-64);
/*     */         return;
/*     */     } 
/* 160 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void encodeLengthIntoBuffer(ByteBuf out, int index, byte b) {
/* 165 */     out.setByte(index, out.getByte(index) | b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long readVariableLengthInteger(ByteBuf in, int len) {
/* 175 */     switch (len) {
/*     */       case 1:
/* 177 */         return in.readUnsignedByte();
/*     */       case 2:
/* 179 */         return (in.readUnsignedShort() & 0x3FFF);
/*     */       case 4:
/* 181 */         return in.readUnsignedInt() & 0x3FFFFFFFL;
/*     */       case 8:
/* 183 */         return in.readLong() & 0x3FFFFFFFFFFFFFFFL;
/*     */     } 
/* 185 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int numBytesForVariableLengthInteger(byte b) {
/* 196 */     byte val = (byte)(b >> 6);
/* 197 */     if ((val & 0x1) != 0) {
/* 198 */       if ((val & 0x2) != 0) {
/* 199 */         return 8;
/*     */       }
/* 201 */       return 2;
/*     */     } 
/* 203 */     if ((val & 0x2) != 0) {
/* 204 */       return 4;
/*     */     }
/* 206 */     return 1;
/*     */   }
/*     */   
/*     */   static void criticalStreamClosed(ChannelHandlerContext ctx) {
/* 210 */     if (ctx.channel().parent().isActive())
/*     */     {
/* 212 */       connectionError(ctx, Http3ErrorCode.H3_CLOSED_CRITICAL_STREAM, "Critical stream closed.", false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void connectionError(ChannelHandlerContext ctx, Http3Exception exception, boolean fireException) {
/* 224 */     if (fireException) {
/* 225 */       ctx.fireExceptionCaught(exception);
/*     */     }
/* 227 */     connectionError(ctx.channel(), exception.errorCode(), exception.getMessage());
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
/*     */   static void connectionError(ChannelHandlerContext ctx, Http3ErrorCode errorCode, @Nullable String msg, boolean fireException) {
/* 240 */     if (fireException) {
/* 241 */       ctx.fireExceptionCaught(new Http3Exception(errorCode, msg));
/*     */     }
/* 243 */     connectionError(ctx.channel(), errorCode, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void closeOnFailure(ChannelFuture future) {
/* 252 */     if (future.isDone() && !future.isSuccess()) {
/* 253 */       future.channel().close();
/*     */       return;
/*     */     } 
/* 256 */     future.addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void connectionError(Channel channel, Http3ErrorCode errorCode, @Nullable String msg) {
/*     */     QuicChannel quicChannel;
/*     */     ByteBuf buffer;
/* 269 */     if (channel instanceof QuicChannel) {
/* 270 */       quicChannel = (QuicChannel)channel;
/*     */     } else {
/* 272 */       quicChannel = (QuicChannel)channel.parent();
/*     */     } 
/*     */     
/* 275 */     if (msg != null) {
/*     */       
/* 277 */       buffer = quicChannel.alloc().buffer();
/* 278 */       buffer.writeCharSequence(msg, CharsetUtil.US_ASCII);
/*     */     } else {
/* 280 */       buffer = Unpooled.EMPTY_BUFFER;
/*     */     } 
/* 282 */     quicChannel.close(true, errorCode.code, buffer);
/*     */   }
/*     */   
/*     */   static void streamError(ChannelHandlerContext ctx, Http3ErrorCode errorCode) {
/* 286 */     ((QuicStreamChannel)ctx.channel()).shutdownOutput(errorCode.code);
/*     */   }
/*     */   
/*     */   static void readIfNoAutoRead(ChannelHandlerContext ctx) {
/* 290 */     if (!ctx.channel().config().isAutoRead()) {
/* 291 */       ctx.read();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static Http3ConnectionHandler getConnectionHandlerOrClose(QuicChannel ch) {
/* 304 */     Http3ConnectionHandler connectionHandler = (Http3ConnectionHandler)ch.pipeline().get(Http3ConnectionHandler.class);
/* 305 */     if (connectionHandler == null) {
/* 306 */       connectionError((Channel)ch, Http3ErrorCode.H3_INTERNAL_ERROR, "Couldn't obtain the " + 
/* 307 */           StringUtil.simpleClassName(Http3ConnectionHandler.class) + " of the parent Channel");
/* 308 */       return null;
/*     */     } 
/* 310 */     return connectionHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void verifyIsUnidirectional(QuicStreamChannel ch) {
/* 321 */     if (ch.type() != QuicStreamType.UNIDIRECTIONAL)
/* 322 */       throw new IllegalArgumentException("Invalid stream type: " + ch.type() + " for stream: " + ch.streamId()); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3CodecUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */