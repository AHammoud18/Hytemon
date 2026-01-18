/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
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
/*     */ public class SnappyFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private static final int SNAPPY_IDENTIFIER_LEN = 6;
/*     */   private static final int MAX_UNCOMPRESSED_DATA_SIZE = 65540;
/*     */   private static final int MAX_DECOMPRESSED_DATA_SIZE = 65536;
/*     */   private static final int MAX_COMPRESSED_CHUNK_SIZE = 16777215;
/*     */   
/*     */   private enum ChunkType
/*     */   {
/*  40 */     STREAM_IDENTIFIER,
/*  41 */     COMPRESSED_DATA,
/*  42 */     UNCOMPRESSED_DATA,
/*  43 */     RESERVED_UNSKIPPABLE,
/*  44 */     RESERVED_SKIPPABLE;
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
/*  55 */   private final Snappy snappy = new Snappy();
/*     */ 
/*     */   
/*     */   private final boolean validateChecksums;
/*     */   
/*     */   private boolean started;
/*     */   
/*     */   private boolean corrupted;
/*     */   
/*     */   private int numBytesToSkip;
/*     */ 
/*     */   
/*     */   public SnappyFrameDecoder() {
/*  68 */     this(false);
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
/*     */   public SnappyFrameDecoder(boolean validateChecksums) {
/*  81 */     this.validateChecksums = validateChecksums;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  86 */     if (this.corrupted) {
/*  87 */       in.skipBytes(in.readableBytes());
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     if (this.numBytesToSkip != 0) {
/*     */       
/*  93 */       int skipBytes = Math.min(this.numBytesToSkip, in.readableBytes());
/*  94 */       in.skipBytes(skipBytes);
/*  95 */       this.numBytesToSkip -= skipBytes;
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/*     */       int offset, skipBytes, checksum, uncompressedSize;
/*     */       ByteBuf uncompressed;
/* 102 */       int idx = in.readerIndex();
/* 103 */       int inSize = in.readableBytes();
/* 104 */       if (inSize < 4) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 110 */       int chunkTypeVal = in.getUnsignedByte(idx);
/* 111 */       ChunkType chunkType = mapChunkType((byte)chunkTypeVal);
/* 112 */       int chunkLength = in.getUnsignedMediumLE(idx + 1);
/*     */       
/* 114 */       switch (chunkType) {
/*     */         case STREAM_IDENTIFIER:
/* 116 */           if (chunkLength != 6) {
/* 117 */             throw new DecompressionException("Unexpected length of stream identifier: " + chunkLength);
/*     */           }
/*     */           
/* 120 */           if (inSize < 10) {
/*     */             break;
/*     */           }
/*     */           
/* 124 */           in.skipBytes(4);
/* 125 */           offset = in.readerIndex();
/* 126 */           in.skipBytes(6);
/*     */           
/* 128 */           checkByte(in.getByte(offset++), (byte)115);
/* 129 */           checkByte(in.getByte(offset++), (byte)78);
/* 130 */           checkByte(in.getByte(offset++), (byte)97);
/* 131 */           checkByte(in.getByte(offset++), (byte)80);
/* 132 */           checkByte(in.getByte(offset++), (byte)112);
/* 133 */           checkByte(in.getByte(offset), (byte)89);
/*     */           
/* 135 */           this.started = true;
/*     */           break;
/*     */         case RESERVED_SKIPPABLE:
/* 138 */           if (!this.started) {
/* 139 */             throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
/*     */           }
/*     */           
/* 142 */           in.skipBytes(4);
/*     */           
/* 144 */           skipBytes = Math.min(chunkLength, in.readableBytes());
/* 145 */           in.skipBytes(skipBytes);
/* 146 */           if (skipBytes != chunkLength)
/*     */           {
/*     */             
/* 149 */             this.numBytesToSkip = chunkLength - skipBytes;
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case RESERVED_UNSKIPPABLE:
/* 156 */           throw new DecompressionException("Found reserved unskippable chunk type: 0x" + 
/* 157 */               Integer.toHexString(chunkTypeVal));
/*     */         case UNCOMPRESSED_DATA:
/* 159 */           if (!this.started) {
/* 160 */             throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
/*     */           }
/* 162 */           if (chunkLength > 65540) {
/* 163 */             throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
/*     */           }
/*     */ 
/*     */           
/* 167 */           if (inSize < 4 + chunkLength) {
/*     */             return;
/*     */           }
/*     */           
/* 171 */           in.skipBytes(4);
/* 172 */           if (this.validateChecksums) {
/* 173 */             int i = in.readIntLE();
/* 174 */             Snappy.validateChecksum(i, in, in.readerIndex(), chunkLength - 4);
/*     */           } else {
/* 176 */             in.skipBytes(4);
/*     */           } 
/* 178 */           out.add(in.readRetainedSlice(chunkLength - 4));
/*     */           break;
/*     */         case COMPRESSED_DATA:
/* 181 */           if (!this.started) {
/* 182 */             throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
/*     */           }
/*     */           
/* 185 */           if (chunkLength > 16777215) {
/* 186 */             throw new DecompressionException("Received COMPRESSED_DATA that contains chunk that exceeds 16777215 bytes");
/*     */           }
/*     */ 
/*     */           
/* 190 */           if (inSize < 4 + chunkLength) {
/*     */             return;
/*     */           }
/*     */           
/* 194 */           in.skipBytes(4);
/* 195 */           checksum = in.readIntLE();
/*     */           
/* 197 */           uncompressedSize = this.snappy.getPreamble(in);
/* 198 */           if (uncompressedSize > 65536) {
/* 199 */             throw new DecompressionException("Received COMPRESSED_DATA that contains uncompressed data that exceeds 65536 bytes");
/*     */           }
/*     */ 
/*     */           
/* 203 */           uncompressed = ctx.alloc().buffer(uncompressedSize, 65536);
/*     */           try {
/* 205 */             if (this.validateChecksums) {
/* 206 */               int oldWriterIndex = in.writerIndex();
/*     */               try {
/* 208 */                 in.writerIndex(in.readerIndex() + chunkLength - 4);
/* 209 */                 this.snappy.decode(in, uncompressed);
/*     */               } finally {
/* 211 */                 in.writerIndex(oldWriterIndex);
/*     */               } 
/* 213 */               Snappy.validateChecksum(checksum, uncompressed, 0, uncompressed.writerIndex());
/*     */             } else {
/* 215 */               this.snappy.decode(in.readSlice(chunkLength - 4), uncompressed);
/*     */             } 
/* 217 */             out.add(uncompressed);
/* 218 */             uncompressed = null;
/*     */           } finally {
/* 220 */             if (uncompressed != null) {
/* 221 */               uncompressed.release();
/*     */             }
/*     */           } 
/* 224 */           this.snappy.reset();
/*     */           break;
/*     */       } 
/* 227 */     } catch (Exception e) {
/* 228 */       this.corrupted = true;
/* 229 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkByte(byte actual, byte expect) {
/* 234 */     if (actual != expect) {
/* 235 */       throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
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
/*     */   private static ChunkType mapChunkType(byte type) {
/* 247 */     if (type == 0)
/* 248 */       return ChunkType.COMPRESSED_DATA; 
/* 249 */     if (type == 1)
/* 250 */       return ChunkType.UNCOMPRESSED_DATA; 
/* 251 */     if (type == -1)
/* 252 */       return ChunkType.STREAM_IDENTIFIER; 
/* 253 */     if ((type & 0x80) == 128) {
/* 254 */       return ChunkType.RESERVED_SKIPPABLE;
/*     */     }
/* 256 */     return ChunkType.RESERVED_UNSKIPPABLE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\SnappyFrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */