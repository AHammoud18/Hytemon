/*     */ package com.hypixel.hytale.protocol.io;
/*     */ 
/*     */ import com.github.luben.zstd.Zstd;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.PacketRegistry;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PacketIO
/*     */ {
/*     */   public static final int FRAME_HEADER_SIZE = 4;
/*  25 */   public static final Charset UTF8 = StandardCharsets.UTF_8;
/*  26 */   public static final Charset ASCII = StandardCharsets.US_ASCII;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float readHalfLE(@Nonnull ByteBuf buf, int index) {
/*  33 */     short bits = buf.getShortLE(index);
/*  34 */     return halfToFloat(bits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeHalfLE(@Nonnull ByteBuf buf, float value) {
/*  42 */     buf.writeShortLE(floatToHalf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static byte[] readBytes(@Nonnull ByteBuf buf, int offset, int length) {
/*  49 */     byte[] bytes = new byte[length];
/*  50 */     buf.getBytes(offset, bytes);
/*  51 */     return bytes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static byte[] readByteArray(@Nonnull ByteBuf buf, int offset, int length) {
/*  56 */     byte[] result = new byte[length];
/*  57 */     buf.getBytes(offset, result);
/*  58 */     return result;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static short[] readShortArrayLE(@Nonnull ByteBuf buf, int offset, int length) {
/*  63 */     short[] result = new short[length];
/*  64 */     for (int i = 0; i < length; i++) {
/*  65 */       result[i] = buf.getShortLE(offset + i * 2);
/*     */     }
/*  67 */     return result;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static float[] readFloatArrayLE(@Nonnull ByteBuf buf, int offset, int length) {
/*  72 */     float[] result = new float[length];
/*  73 */     for (int i = 0; i < length; i++) {
/*  74 */       result[i] = buf.getFloatLE(offset + i * 4);
/*     */     }
/*  76 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String readFixedAsciiString(@Nonnull ByteBuf buf, int offset, int length) {
/*  83 */     byte[] bytes = new byte[length];
/*  84 */     buf.getBytes(offset, bytes);
/*     */     
/*  86 */     int end = 0;
/*  87 */     for (; end < length && bytes[end] != 0; end++);
/*  88 */     return new String(bytes, 0, end, StandardCharsets.US_ASCII);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String readFixedString(@Nonnull ByteBuf buf, int offset, int length) {
/*  93 */     byte[] bytes = new byte[length];
/*  94 */     buf.getBytes(offset, bytes);
/*     */     
/*  96 */     int end = 0;
/*  97 */     for (; end < length && bytes[end] != 0; end++);
/*  98 */     return new String(bytes, 0, end, StandardCharsets.UTF_8);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String readVarString(@Nonnull ByteBuf buf, int offset) {
/* 105 */     return readVarString(buf, offset, StandardCharsets.UTF_8);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String readVarAsciiString(@Nonnull ByteBuf buf, int offset) {
/* 110 */     return readVarString(buf, offset, StandardCharsets.US_ASCII);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String readVarString(@Nonnull ByteBuf buf, int offset, Charset charset) {
/* 115 */     int len = VarInt.peek(buf, offset);
/* 116 */     int varIntLen = VarInt.length(buf, offset);
/* 117 */     byte[] bytes = new byte[len];
/* 118 */     buf.getBytes(offset + varIntLen, bytes);
/* 119 */     return new String(bytes, charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int utf8ByteLength(@Nonnull String s) {
/* 129 */     int len = 0;
/* 130 */     for (int i = 0; i < s.length(); i++) {
/* 131 */       char c = s.charAt(i);
/* 132 */       if (c < '') {
/* 133 */         len++;
/* 134 */       } else if (c < 'ࠀ') {
/* 135 */         len += 2;
/* 136 */       } else if (Character.isHighSurrogate(c)) {
/* 137 */         len += 4;
/* 138 */         i++;
/*     */       } else {
/* 140 */         len += 3;
/*     */       } 
/*     */     } 
/* 143 */     return len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int stringSize(@Nonnull String s) {
/* 151 */     int len = utf8ByteLength(s);
/* 152 */     return VarInt.size(len) + len;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeFixedBytes(@Nonnull ByteBuf buf, @Nonnull byte[] data, int length) {
/* 158 */     buf.writeBytes(data, 0, Math.min(data.length, length));
/* 159 */     for (int i = data.length; i < length; ) { buf.writeByte(0); i++; }
/*     */   
/*     */   }
/*     */   public static void writeFixedAsciiString(@Nonnull ByteBuf buf, @Nullable String value, int length) {
/* 163 */     if (value != null) {
/* 164 */       byte[] bytes = value.getBytes(StandardCharsets.US_ASCII);
/* 165 */       if (bytes.length > length) {
/* 166 */         throw new ProtocolException("Fixed ASCII string exceeds length: " + bytes.length + " > " + length);
/*     */       }
/* 168 */       buf.writeBytes(bytes);
/* 169 */       buf.writeZero(length - bytes.length);
/*     */     } else {
/* 171 */       buf.writeZero(length);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeFixedString(@Nonnull ByteBuf buf, @Nullable String value, int length) {
/* 176 */     if (value != null) {
/* 177 */       byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
/* 178 */       if (bytes.length > length) {
/* 179 */         throw new ProtocolException("Fixed UTF-8 string exceeds length: " + bytes.length + " > " + length);
/*     */       }
/* 181 */       buf.writeBytes(bytes);
/* 182 */       buf.writeZero(length - bytes.length);
/*     */     } else {
/* 184 */       buf.writeZero(length);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeVarString(@Nonnull ByteBuf buf, @Nonnull String value, int maxLength) {
/* 189 */     byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
/* 190 */     if (bytes.length > maxLength) {
/* 191 */       throw new ProtocolException("String exceeds max bytes: " + bytes.length + " > " + maxLength);
/*     */     }
/* 193 */     VarInt.write(buf, bytes.length);
/* 194 */     buf.writeBytes(bytes);
/*     */   }
/*     */   
/*     */   public static void writeVarAsciiString(@Nonnull ByteBuf buf, @Nonnull String value, int maxLength) {
/* 198 */     byte[] bytes = value.getBytes(StandardCharsets.US_ASCII);
/* 199 */     if (bytes.length > maxLength) {
/* 200 */       throw new ProtocolException("String exceeds max bytes: " + bytes.length + " > " + maxLength);
/*     */     }
/* 202 */     VarInt.write(buf, bytes.length);
/* 203 */     buf.writeBytes(bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static UUID readUUID(@Nonnull ByteBuf buf, int offset) {
/* 210 */     long mostSig = buf.getLong(offset);
/* 211 */     long leastSig = buf.getLong(offset + 8);
/* 212 */     return new UUID(mostSig, leastSig);
/*     */   }
/*     */   
/*     */   public static void writeUUID(@Nonnull ByteBuf buf, @Nonnull UUID value) {
/* 216 */     buf.writeLong(value.getMostSignificantBits());
/* 217 */     buf.writeLong(value.getLeastSignificantBits());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static float halfToFloat(short half) {
/* 223 */     int h = half & 0xFFFF;
/* 224 */     int sign = h >>> 15 & 0x1;
/* 225 */     int exp = h >>> 10 & 0x1F;
/* 226 */     int mant = h & 0x3FF;
/*     */     
/* 228 */     if (exp == 0) {
/* 229 */       if (mant == 0) return (sign == 0) ? 0.0F : -0.0F;
/*     */       
/* 231 */       exp = 1;
/* 232 */       while ((mant & 0x400) == 0) {
/* 233 */         mant <<= 1;
/* 234 */         exp--;
/*     */       } 
/* 236 */       mant &= 0x3FF;
/* 237 */     } else if (exp == 31) {
/* 238 */       return (mant == 0) ? ((sign == 0) ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY) : Float.NaN;
/*     */     } 
/*     */     
/* 241 */     int floatBits = sign << 31 | exp + 112 << 23 | mant << 13;
/* 242 */     return Float.intBitsToFloat(floatBits);
/*     */   }
/*     */   
/*     */   private static short floatToHalf(float f) {
/* 246 */     int bits = Float.floatToRawIntBits(f);
/* 247 */     int sign = bits >>> 16 & 0x8000;
/* 248 */     int val = (bits & Integer.MAX_VALUE) + 4096;
/*     */     
/* 250 */     if (val >= 1199570944) {
/* 251 */       if ((bits & Integer.MAX_VALUE) >= 1199570944) {
/* 252 */         if (val < 2139095040) return (short)(sign | 0x7C00); 
/* 253 */         return (short)(sign | 0x7C00 | (bits & 0x7FFFFF) >>> 13);
/*     */       } 
/* 255 */       return (short)(sign | 0x7BFF);
/*     */     } 
/* 257 */     if (val >= 947912704) return (short)(sign | val - 939524096 >>> 13); 
/* 258 */     if (val < 855638016) return (short)sign; 
/* 259 */     val = (bits & Integer.MAX_VALUE) >>> 23;
/* 260 */     return (short)(sign | (bits & 0x7FFFFF | 0x800000) + (8388608 >>> val - 102) >>> 126 - val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 266 */   private static final int COMPRESSION_LEVEL = Integer.getInteger("hytale.protocol.compressionLevel", Zstd.defaultCompressionLevel()).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int compressToBuffer(@Nonnull ByteBuf src, @Nonnull ByteBuf dst, int dstOffset, int maxDstSize) {
/* 277 */     if (src.isDirect() && dst.isDirect()) {
/* 278 */       return Zstd.compress(dst.nioBuffer(dstOffset, maxDstSize), src.nioBuffer(), COMPRESSION_LEVEL);
/*     */     }
/*     */ 
/*     */     
/* 282 */     int srcSize = src.readableBytes();
/* 283 */     byte[] srcBytes = new byte[srcSize];
/* 284 */     src.getBytes(src.readerIndex(), srcBytes);
/* 285 */     byte[] compressed = Zstd.compress(srcBytes, COMPRESSION_LEVEL);
/* 286 */     dst.setBytes(dstOffset, compressed);
/* 287 */     return compressed.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static ByteBuf decompressFromBuffer(@Nonnull ByteBuf src, int srcOffset, int srcLength, int maxDecompressedSize) {
/* 299 */     if (srcLength > maxDecompressedSize) {
/* 300 */       throw new ProtocolException("Compressed size " + srcLength + " exceeds max decompressed size " + maxDecompressedSize);
/*     */     }
/*     */ 
/*     */     
/* 304 */     if (src.isDirect()) {
/* 305 */       ByteBuffer srcNio = src.nioBuffer(srcOffset, srcLength);
/*     */       
/* 307 */       long l = Zstd.getFrameContentSize(srcNio);
/* 308 */       if (l < 0L) {
/* 309 */         throw new ProtocolException("Invalid Zstd frame or unknown content size");
/*     */       }
/* 311 */       if (l > maxDecompressedSize) {
/* 312 */         throw new ProtocolException("Decompressed size " + l + " exceeds maximum " + maxDecompressedSize);
/*     */       }
/*     */       
/* 315 */       ByteBuf dst = Unpooled.directBuffer((int)l);
/* 316 */       ByteBuffer dstNio = dst.nioBuffer(0, (int)l);
/*     */       
/* 318 */       int result = Zstd.decompress(dstNio, srcNio);
/* 319 */       if (Zstd.isError(result)) {
/* 320 */         dst.release();
/* 321 */         throw new ProtocolException("Zstd decompression failed: " + Zstd.getErrorName(result));
/*     */       } 
/* 323 */       dst.writerIndex(result);
/* 324 */       return dst;
/*     */     } 
/*     */ 
/*     */     
/* 328 */     byte[] srcBytes = new byte[srcLength];
/* 329 */     src.getBytes(srcOffset, srcBytes);
/*     */     
/* 331 */     long decompressedSize = Zstd.getFrameContentSize(srcBytes);
/* 332 */     if (decompressedSize < 0L) {
/* 333 */       throw new ProtocolException("Invalid Zstd frame or unknown content size");
/*     */     }
/* 335 */     if (decompressedSize > maxDecompressedSize) {
/* 336 */       throw new ProtocolException("Decompressed size " + decompressedSize + " exceeds maximum " + maxDecompressedSize);
/*     */     }
/*     */     
/* 339 */     byte[] decompressed = Zstd.decompress(srcBytes, (int)decompressedSize);
/* 340 */     return Unpooled.wrappedBuffer(decompressed);
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
/*     */   public static void writeFramedPacket(@Nonnull Packet packet, @Nonnull Class<? extends Packet> packetClass, @Nonnull ByteBuf out, @Nonnull PacketStatsRecorder statsRecorder) {
/* 356 */     Integer id = PacketRegistry.getId(packetClass);
/* 357 */     if (id == null) {
/* 358 */       throw new ProtocolException("Unknown packet type: " + packetClass.getName());
/*     */     }
/* 360 */     PacketRegistry.PacketInfo info = PacketRegistry.getById(id.intValue());
/*     */ 
/*     */     
/* 363 */     int lengthIndex = out.writerIndex();
/* 364 */     out.writeIntLE(0);
/* 365 */     out.writeIntLE(id.intValue());
/*     */ 
/*     */     
/* 368 */     ByteBuf payloadBuf = Unpooled.buffer(Math.min(info.maxSize(), 65536));
/*     */     try {
/* 370 */       packet.serialize(payloadBuf);
/*     */       
/* 372 */       int serializedSize = payloadBuf.readableBytes();
/* 373 */       if (serializedSize > info.maxSize()) {
/* 374 */         throw new ProtocolException("Packet " + info.name() + " serialized to " + serializedSize + " bytes, exceeds max size " + info.maxSize());
/*     */       }
/*     */       
/* 377 */       if (info.compressed() && serializedSize > 0) {
/* 378 */         int compressBound = (int)Zstd.compressBound(serializedSize);
/* 379 */         out.ensureWritable(compressBound);
/*     */         
/* 381 */         int compressedSize = compressToBuffer(payloadBuf, out, out.writerIndex(), compressBound);
/*     */         
/* 383 */         if (Zstd.isError(compressedSize)) {
/* 384 */           throw new ProtocolException("Zstd compression failed: " + Zstd.getErrorName(compressedSize));
/*     */         }
/*     */         
/* 387 */         if (compressedSize > 1677721600) {
/* 388 */           throw new ProtocolException("Packet " + info.name() + " compressed payload size " + compressedSize + " exceeds protocol maximum");
/*     */         }
/* 390 */         out.writerIndex(out.writerIndex() + compressedSize);
/*     */         
/* 392 */         out.setIntLE(lengthIndex, compressedSize);
/* 393 */         statsRecorder.recordSend(id.intValue(), serializedSize, compressedSize);
/*     */       } else {
/* 395 */         if (serializedSize > 1677721600) {
/* 396 */           throw new ProtocolException("Packet " + info.name() + " payload size " + serializedSize + " exceeds protocol maximum");
/*     */         }
/* 398 */         out.writeBytes(payloadBuf);
/*     */         
/* 400 */         out.setIntLE(lengthIndex, serializedSize);
/* 401 */         statsRecorder.recordSend(id.intValue(), serializedSize, 0);
/*     */       } 
/*     */     } finally {
/* 404 */       payloadBuf.release();
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
/*     */   @Nonnull
/*     */   public static Packet readFramedPacket(@Nonnull ByteBuf in, int payloadLength, @Nonnull PacketStatsRecorder statsRecorder) {
/* 421 */     int packetId = in.readIntLE();
/* 422 */     PacketRegistry.PacketInfo info = PacketRegistry.getById(packetId);
/* 423 */     if (info == null) {
/* 424 */       in.skipBytes(payloadLength);
/* 425 */       throw new ProtocolException("Unknown packet ID: " + packetId);
/*     */     } 
/*     */     
/* 428 */     return readFramedPacketWithInfo(in, payloadLength, info, statsRecorder);
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
/*     */   @Nonnull
/*     */   public static Packet readFramedPacketWithInfo(@Nonnull ByteBuf in, int payloadLength, @Nonnull PacketRegistry.PacketInfo info, @Nonnull PacketStatsRecorder statsRecorder) {
/*     */     ByteBuf payload;
/* 445 */     int uncompressedSize, compressedSize = 0;
/*     */     
/* 447 */     if (info.compressed() && payloadLength > 0) {
/*     */       try {
/* 449 */         payload = decompressFromBuffer(in, in.readerIndex(), payloadLength, info.maxSize());
/* 450 */       } catch (ProtocolException e) {
/* 451 */         in.skipBytes(payloadLength);
/* 452 */         throw e;
/*     */       } 
/* 454 */       in.skipBytes(payloadLength);
/* 455 */       uncompressedSize = payload.readableBytes();
/* 456 */       compressedSize = payloadLength;
/* 457 */     } else if (payloadLength > 0) {
/* 458 */       payload = in.readRetainedSlice(payloadLength);
/* 459 */       uncompressedSize = payloadLength;
/*     */     } else {
/* 461 */       payload = Unpooled.EMPTY_BUFFER;
/* 462 */       uncompressedSize = 0;
/*     */     } 
/*     */     
/*     */     try {
/* 466 */       Packet packet = info.deserialize().apply(payload, Integer.valueOf(0));
/* 467 */       statsRecorder.recordReceive(info.id(), uncompressedSize, compressedSize);
/* 468 */       return packet;
/*     */     } finally {
/* 470 */       if (payloadLength > 0)
/* 471 */         payload.release(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\io\PacketIO.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */