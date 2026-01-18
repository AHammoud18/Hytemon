/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteOrder;
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
/*     */ public class WebSocket08FrameEncoder
/*     */   extends MessageToMessageEncoder<WebSocketFrame>
/*     */   implements WebSocketFrameEncoder
/*     */ {
/*  74 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameEncoder.class);
/*     */ 
/*     */   
/*     */   private static final byte OPCODE_CONT = 0;
/*     */ 
/*     */   
/*     */   private static final byte OPCODE_TEXT = 1;
/*     */ 
/*     */   
/*     */   private static final byte OPCODE_BINARY = 2;
/*     */ 
/*     */   
/*     */   private static final byte OPCODE_CLOSE = 8;
/*     */ 
/*     */   
/*     */   private static final byte OPCODE_PING = 9;
/*     */ 
/*     */   
/*     */   private static final byte OPCODE_PONG = 10;
/*     */ 
/*     */   
/*     */   private static final int GATHERING_WRITE_THRESHOLD = 1024;
/*     */   
/*     */   private final WebSocketFrameMaskGenerator maskGenerator;
/*     */ 
/*     */   
/*     */   public WebSocket08FrameEncoder(boolean maskPayload) {
/* 101 */     this(maskPayload ? RandomWebSocketFrameMaskGenerator.INSTANCE : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocket08FrameEncoder(WebSocketFrameMaskGenerator maskGenerator) {
/* 112 */     super(WebSocketFrame.class);
/* 113 */     this.maskGenerator = maskGenerator;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/* 118 */     ByteBuf data = msg.content();
/*     */     
/* 120 */     byte opcode = getOpCode(msg);
/*     */     
/* 122 */     int length = data.readableBytes();
/*     */     
/* 124 */     if (logger.isTraceEnabled()) {
/* 125 */       logger.trace("Encoding WebSocket Frame opCode={} length={}", Byte.valueOf(opcode), Integer.valueOf(length));
/*     */     }
/*     */     
/* 128 */     int b0 = 0;
/* 129 */     if (msg.isFinalFragment()) {
/* 130 */       b0 |= 0x80;
/*     */     }
/* 132 */     b0 |= (msg.rsv() & 0x7) << 4;
/* 133 */     b0 |= opcode & Byte.MAX_VALUE;
/*     */     
/* 135 */     if (opcode == 9 && length > 125) {
/* 136 */       throw new TooLongFrameException("invalid payload for PING (payload length must be <= 125, was " + length);
/*     */     }
/*     */     
/* 139 */     boolean release = true;
/* 140 */     ByteBuf buf = null;
/*     */     try {
/* 142 */       int maskLength = (this.maskGenerator != null) ? 4 : 0;
/* 143 */       if (length <= 125) {
/* 144 */         int size = 2 + maskLength + length;
/* 145 */         buf = ctx.alloc().buffer(size);
/* 146 */         buf.writeByte(b0);
/* 147 */         byte b = (byte)((this.maskGenerator != null) ? (0x80 | length) : length);
/* 148 */         buf.writeByte(b);
/* 149 */       } else if (length <= 65535) {
/* 150 */         int size = 4 + maskLength;
/* 151 */         if (this.maskGenerator != null || length <= 1024) {
/* 152 */           size += length;
/*     */         }
/* 154 */         buf = ctx.alloc().buffer(size);
/* 155 */         buf.writeByte(b0);
/* 156 */         buf.writeByte((this.maskGenerator != null) ? 254 : 126);
/* 157 */         buf.writeByte(length >>> 8 & 0xFF);
/* 158 */         buf.writeByte(length & 0xFF);
/*     */       } else {
/* 160 */         int size = 10 + maskLength;
/* 161 */         if (this.maskGenerator != null) {
/* 162 */           size += length;
/*     */         }
/* 164 */         buf = ctx.alloc().buffer(size);
/* 165 */         buf.writeByte(b0);
/* 166 */         buf.writeByte((this.maskGenerator != null) ? 255 : 127);
/* 167 */         buf.writeLong(length);
/*     */       } 
/*     */ 
/*     */       
/* 171 */       if (this.maskGenerator != null) {
/* 172 */         int mask = this.maskGenerator.nextMask();
/* 173 */         buf.writeInt(mask);
/*     */ 
/*     */         
/* 176 */         if (mask != 0) {
/* 177 */           if (length > 0) {
/* 178 */             ByteOrder srcOrder = data.order();
/* 179 */             ByteOrder dstOrder = buf.order();
/*     */             
/* 181 */             int i = data.readerIndex();
/* 182 */             int end = data.writerIndex();
/*     */             
/* 184 */             if (srcOrder == dstOrder) {
/*     */ 
/*     */               
/* 187 */               long longMask = mask & 0xFFFFFFFFL;
/* 188 */               longMask |= longMask << 32L;
/*     */ 
/*     */ 
/*     */               
/* 192 */               if (srcOrder == ByteOrder.LITTLE_ENDIAN) {
/* 193 */                 longMask = Long.reverseBytes(longMask);
/*     */               }
/*     */               
/* 196 */               for (int lim = end - 7; i < lim; i += 8) {
/* 197 */                 buf.writeLong(data.getLong(i) ^ longMask);
/*     */               }
/*     */               
/* 200 */               if (i < end - 3) {
/* 201 */                 buf.writeInt(data.getInt(i) ^ (int)longMask);
/* 202 */                 i += 4;
/*     */               } 
/*     */             } 
/* 205 */             int maskOffset = 0;
/* 206 */             for (; i < end; i++) {
/* 207 */               byte byteData = data.getByte(i);
/* 208 */               buf.writeByte(byteData ^ WebSocketUtil.byteAtIndex(mask, maskOffset++ & 0x3));
/*     */             } 
/*     */           } 
/* 211 */           out.add(buf);
/*     */         } else {
/* 213 */           addBuffers(buf, data, out);
/*     */         } 
/*     */       } else {
/* 216 */         addBuffers(buf, data, out);
/*     */       } 
/* 218 */       release = false;
/*     */     } finally {
/* 220 */       if (release && buf != null) {
/* 221 */         buf.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static byte getOpCode(WebSocketFrame msg) {
/* 227 */     if (msg instanceof TextWebSocketFrame) {
/* 228 */       return 1;
/*     */     }
/* 230 */     if (msg instanceof BinaryWebSocketFrame) {
/* 231 */       return 2;
/*     */     }
/* 233 */     if (msg instanceof PingWebSocketFrame) {
/* 234 */       return 9;
/*     */     }
/* 236 */     if (msg instanceof PongWebSocketFrame) {
/* 237 */       return 10;
/*     */     }
/* 239 */     if (msg instanceof CloseWebSocketFrame) {
/* 240 */       return 8;
/*     */     }
/* 242 */     if (msg instanceof ContinuationWebSocketFrame) {
/* 243 */       return 0;
/*     */     }
/* 245 */     throw new UnsupportedOperationException("Cannot encode frame of type: " + msg.getClass().getName());
/*     */   }
/*     */   
/*     */   private static void addBuffers(ByteBuf buf, ByteBuf data, List<Object> out) {
/* 249 */     int readableBytes = data.readableBytes();
/* 250 */     if (buf.writableBytes() >= readableBytes) {
/*     */       
/* 252 */       buf.writeBytes(data);
/* 253 */       out.add(buf);
/*     */     } else {
/* 255 */       out.add(buf);
/* 256 */       if (readableBytes > 0)
/* 257 */         out.add(data.retain()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocket08FrameEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */