/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.InetSocketAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class QuicHeaderParser
/*     */   implements AutoCloseable
/*     */ {
/*     */   private static final int AES_128_GCM_TAG_LENGTH = 16;
/*     */   private final int localConnectionIdLength;
/*     */   private boolean closed;
/*     */   
/*     */   public QuicHeaderParser(int localConnectionIdLength) {
/*  42 */     this.localConnectionIdLength = ObjectUtil.checkPositiveOrZero(localConnectionIdLength, "localConnectionIdLength");
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  47 */     if (!this.closed) {
/*  48 */       this.closed = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(InetSocketAddress sender, InetSocketAddress recipient, ByteBuf packet, QuicHeaderProcessor callback) throws Exception {
/*     */     QuicPacketType type;
/*     */     long version;
/*     */     ByteBuf dcid, scid, token;
/*  70 */     if (this.closed) {
/*  71 */       throw new IllegalStateException(QuicHeaderParser.class.getSimpleName() + " is already closed");
/*     */     }
/*     */ 
/*     */     
/*  75 */     int offset = 0;
/*  76 */     int readable = packet.readableBytes();
/*     */     
/*  78 */     checkReadable(offset, readable, 1);
/*  79 */     byte first = packet.getByte(offset);
/*  80 */     offset++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     if (hasShortHeader(first)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       version = 0L;
/* 102 */       type = QuicPacketType.SHORT;
/*     */       
/* 104 */       scid = Unpooled.EMPTY_BUFFER;
/* 105 */       token = Unpooled.EMPTY_BUFFER;
/* 106 */       dcid = sliceCid(packet, offset, this.localConnectionIdLength);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       checkReadable(offset, readable, 4);
/*     */ 
/*     */ 
/*     */       
/* 125 */       version = packet.getUnsignedInt(offset);
/* 126 */       offset += 4;
/* 127 */       type = typeOfLongHeader(first, version);
/*     */       
/* 129 */       int dcidLen = packet.getUnsignedByte(offset);
/* 130 */       checkCidLength(dcidLen);
/* 131 */       offset++;
/* 132 */       dcid = sliceCid(packet, offset, dcidLen);
/* 133 */       offset += dcidLen;
/*     */       
/* 135 */       int scidLen = packet.getUnsignedByte(offset);
/* 136 */       checkCidLength(scidLen);
/* 137 */       offset++;
/* 138 */       scid = sliceCid(packet, offset, scidLen);
/* 139 */       offset += scidLen;
/* 140 */       token = sliceToken(type, packet, offset, readable);
/*     */     } 
/* 142 */     callback.process(sender, recipient, packet, type, version, scid, dcid, token);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkCidLength(int length) throws QuicException {
/* 148 */     if (length > 20)
/* 149 */       throw new QuicException("connection id to large: " + length + " > " + '\024', QuicTransportError.PROTOCOL_VIOLATION); 
/*     */   }
/*     */   
/*     */   private static ByteBuf sliceToken(QuicPacketType type, ByteBuf packet, int offset, int readable) throws QuicException {
/*     */     int numBytes;
/*     */     int len;
/*     */     int tokenLen;
/* 156 */     switch (type) {
/*     */       
/*     */       case INITIAL:
/* 159 */         checkReadable(offset, readable, 1);
/* 160 */         numBytes = numBytesForVariableLengthInteger(packet.getByte(offset));
/* 161 */         len = (int)getVariableLengthInteger(packet, offset, numBytes);
/* 162 */         offset += numBytes;
/*     */         
/* 164 */         checkReadable(offset, readable, len);
/* 165 */         return packet.slice(offset, len);
/*     */ 
/*     */       
/*     */       case RETRY:
/* 169 */         checkReadable(offset, readable, 16);
/* 170 */         tokenLen = readable - offset - 16;
/* 171 */         return packet.slice(offset, tokenLen);
/*     */     } 
/*     */     
/* 174 */     return Unpooled.EMPTY_BUFFER;
/*     */   }
/*     */   
/*     */   private static QuicException newProtocolViolationException(String message) {
/* 178 */     return new QuicException(message, QuicTransportError.PROTOCOL_VIOLATION);
/*     */   }
/*     */   
/*     */   static ByteBuf sliceCid(ByteBuf buffer, int offset, int len) throws QuicException {
/* 182 */     checkReadable(offset, buffer.readableBytes(), len);
/* 183 */     return buffer.slice(offset, len);
/*     */   }
/*     */   
/*     */   private static void checkReadable(int offset, int readable, int needed) throws QuicException {
/* 187 */     int r = readable - offset;
/* 188 */     if (r < needed) {
/* 189 */       throw newProtocolViolationException("Not enough bytes to read, " + r + " < " + needed);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long getVariableLengthInteger(ByteBuf in, int offset, int len) throws QuicException {
/* 199 */     checkReadable(offset, in.readableBytes(), len);
/* 200 */     switch (len) {
/*     */       case 1:
/* 202 */         return in.getUnsignedByte(offset);
/*     */       case 2:
/* 204 */         return (in.getUnsignedShort(offset) & 0x3FFF);
/*     */       case 4:
/* 206 */         return in.getUnsignedInt(offset) & 0x3FFFFFFFL;
/*     */       case 8:
/* 208 */         return in.getLong(offset) & 0x3FFFFFFFFFFFFFFFL;
/*     */     } 
/* 210 */     throw newProtocolViolationException("Unsupported length:" + len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int numBytesForVariableLengthInteger(byte b) {
/* 220 */     byte val = (byte)(b >> 6);
/* 221 */     if ((val & 0x1) != 0) {
/* 222 */       if ((val & 0x2) != 0) {
/* 223 */         return 8;
/*     */       }
/* 225 */       return 2;
/*     */     } 
/* 227 */     if ((val & 0x2) != 0) {
/* 228 */       return 4;
/*     */     }
/* 230 */     return 1;
/*     */   }
/*     */   
/*     */   static boolean hasShortHeader(byte b) {
/* 234 */     return ((b & 0x80) == 0);
/*     */   }
/*     */   
/*     */   private static QuicPacketType typeOfLongHeader(byte first, long version) throws QuicException {
/* 238 */     if (version == 0L)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 243 */       return QuicPacketType.VERSION_NEGOTIATION;
/*     */     }
/* 245 */     int packetType = (first & 0x30) >> 4;
/* 246 */     switch (packetType) {
/*     */       case 0:
/* 248 */         return QuicPacketType.INITIAL;
/*     */       case 1:
/* 250 */         return QuicPacketType.ZERO_RTT;
/*     */       case 2:
/* 252 */         return QuicPacketType.HANDSHAKE;
/*     */       case 3:
/* 254 */         return QuicPacketType.RETRY;
/*     */     } 
/* 256 */     throw newProtocolViolationException("Unknown packet type: " + packetType);
/*     */   }
/*     */   
/*     */   public static interface QuicHeaderProcessor {
/*     */     void process(InetSocketAddress param1InetSocketAddress1, InetSocketAddress param1InetSocketAddress2, ByteBuf param1ByteBuf1, QuicPacketType param1QuicPacketType, long param1Long, ByteBuf param1ByteBuf2, ByteBuf param1ByteBuf3, ByteBuf param1ByteBuf4) throws Exception;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicHeaderParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */