/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.List;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Inflater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JdkZlibDecoder
/*     */   extends ZlibDecoder
/*     */ {
/*     */   private static final int FHCRC = 2;
/*     */   private static final int FEXTRA = 4;
/*     */   private static final int FNAME = 8;
/*     */   private static final int FCOMMENT = 16;
/*     */   private static final int FRESERVED = 224;
/*     */   private Inflater inflater;
/*     */   private final byte[] dictionary;
/*     */   private final ByteBufChecksum crc;
/*     */   private final boolean decompressConcatenated;
/*     */   
/*     */   private enum GzipState
/*     */   {
/*  47 */     HEADER_START,
/*  48 */     HEADER_END,
/*  49 */     FLG_READ,
/*  50 */     XLEN_READ,
/*  51 */     SKIP_FNAME,
/*  52 */     SKIP_COMMENT,
/*  53 */     PROCESS_FHCRC,
/*  54 */     FOOTER_START;
/*     */   }
/*     */   
/*  57 */   private GzipState gzipState = GzipState.HEADER_START;
/*  58 */   private int flags = -1;
/*  59 */   private int xlen = -1;
/*     */ 
/*     */   
/*     */   private boolean needsRead;
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */   
/*     */   private boolean decideZlibOrNone;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JdkZlibDecoder() {
/*  73 */     this(ZlibWrapper.ZLIB, (byte[])null, false, 0);
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
/*     */   public JdkZlibDecoder(int maxAllocation) {
/*  85 */     this(ZlibWrapper.ZLIB, (byte[])null, false, maxAllocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JdkZlibDecoder(byte[] dictionary) {
/*  97 */     this(ZlibWrapper.ZLIB, dictionary, false, 0);
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
/*     */   public JdkZlibDecoder(byte[] dictionary, int maxAllocation) {
/* 110 */     this(ZlibWrapper.ZLIB, dictionary, false, maxAllocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JdkZlibDecoder(ZlibWrapper wrapper) {
/* 122 */     this(wrapper, (byte[])null, false, 0);
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
/*     */   public JdkZlibDecoder(ZlibWrapper wrapper, int maxAllocation) {
/* 135 */     this(wrapper, (byte[])null, false, maxAllocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JdkZlibDecoder(ZlibWrapper wrapper, boolean decompressConcatenated) {
/* 143 */     this(wrapper, (byte[])null, decompressConcatenated, 0);
/*     */   }
/*     */   
/*     */   public JdkZlibDecoder(ZlibWrapper wrapper, boolean decompressConcatenated, int maxAllocation) {
/* 147 */     this(wrapper, (byte[])null, decompressConcatenated, maxAllocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JdkZlibDecoder(boolean decompressConcatenated) {
/* 155 */     this(ZlibWrapper.GZIP, (byte[])null, decompressConcatenated, 0);
/*     */   }
/*     */   
/*     */   public JdkZlibDecoder(boolean decompressConcatenated, int maxAllocation) {
/* 159 */     this(ZlibWrapper.GZIP, (byte[])null, decompressConcatenated, maxAllocation);
/*     */   }
/*     */   
/*     */   private JdkZlibDecoder(ZlibWrapper wrapper, byte[] dictionary, boolean decompressConcatenated, int maxAllocation) {
/* 163 */     super(maxAllocation);
/*     */     
/* 165 */     ObjectUtil.checkNotNull(wrapper, "wrapper");
/*     */     
/* 167 */     this.decompressConcatenated = decompressConcatenated;
/* 168 */     switch (wrapper) {
/*     */       case HEADER_START:
/* 170 */         this.inflater = new Inflater(true);
/* 171 */         this.crc = ByteBufChecksum.wrapChecksum(new CRC32());
/*     */         break;
/*     */       case FLG_READ:
/* 174 */         this.inflater = new Inflater(true);
/* 175 */         this.crc = null;
/*     */         break;
/*     */       case XLEN_READ:
/* 178 */         this.inflater = new Inflater();
/* 179 */         this.crc = null;
/*     */         break;
/*     */       
/*     */       case SKIP_FNAME:
/* 183 */         this.decideZlibOrNone = true;
/* 184 */         this.crc = null;
/*     */         break;
/*     */       default:
/* 187 */         throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + wrapper);
/*     */     } 
/* 189 */     this.dictionary = dictionary;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 194 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 199 */     this.needsRead = true;
/* 200 */     if (this.finished) {
/*     */       
/* 202 */       in.skipBytes(in.readableBytes());
/*     */       
/*     */       return;
/*     */     } 
/* 206 */     int readableBytes = in.readableBytes();
/* 207 */     if (readableBytes == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 211 */     if (this.decideZlibOrNone) {
/*     */       
/* 213 */       if (readableBytes < 2) {
/*     */         return;
/*     */       }
/*     */       
/* 217 */       boolean nowrap = !looksLikeZlib(in.getShort(in.readerIndex()));
/* 218 */       this.inflater = new Inflater(nowrap);
/* 219 */       this.decideZlibOrNone = false;
/*     */     } 
/*     */     
/* 222 */     if (this.crc != null && 
/* 223 */       this.gzipState != GzipState.HEADER_END) {
/* 224 */       if (this.gzipState == GzipState.FOOTER_START) {
/* 225 */         if (!handleGzipFooter(in)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 230 */         assert this.gzipState == GzipState.HEADER_START;
/*     */       } 
/* 232 */       if (!readGZIPHeader(in)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 237 */       readableBytes = in.readableBytes();
/* 238 */       if (readableBytes == 0) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 244 */     if (this.inflater.needsInput()) {
/* 245 */       if (in.hasArray()) {
/* 246 */         this.inflater.setInput(in.array(), in.arrayOffset() + in.readerIndex(), readableBytes);
/*     */       } else {
/* 248 */         byte[] array = new byte[readableBytes];
/* 249 */         in.getBytes(in.readerIndex(), array);
/* 250 */         this.inflater.setInput(array);
/*     */       } 
/*     */     }
/*     */     
/* 254 */     ByteBuf decompressed = prepareDecompressBuffer(ctx, (ByteBuf)null, this.inflater.getRemaining() << 1);
/*     */     try {
/* 256 */       boolean readFooter = false;
/* 257 */       while (!this.inflater.needsInput()) {
/* 258 */         byte[] outArray = decompressed.array();
/* 259 */         int writerIndex = decompressed.writerIndex();
/* 260 */         int outIndex = decompressed.arrayOffset() + writerIndex;
/* 261 */         int writable = decompressed.writableBytes();
/* 262 */         int outputLength = this.inflater.inflate(outArray, outIndex, writable);
/* 263 */         if (outputLength > 0) {
/* 264 */           decompressed.writerIndex(writerIndex + outputLength);
/* 265 */           if (this.crc != null) {
/* 266 */             this.crc.update(outArray, outIndex, outputLength);
/*     */           }
/* 268 */           if (this.maxAllocation == 0) {
/*     */ 
/*     */             
/* 271 */             ByteBuf buffer = decompressed;
/* 272 */             decompressed = null;
/* 273 */             this.needsRead = false;
/* 274 */             ctx.fireChannelRead(buffer);
/*     */           } 
/* 276 */         } else if (this.inflater.needsDictionary()) {
/* 277 */           if (this.dictionary == null) {
/* 278 */             throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
/*     */           }
/*     */           
/* 281 */           this.inflater.setDictionary(this.dictionary);
/*     */         } 
/*     */         
/* 284 */         if (this.inflater.finished()) {
/* 285 */           if (this.crc == null) {
/* 286 */             this.finished = true; break;
/*     */           } 
/* 288 */           readFooter = true;
/*     */           
/*     */           break;
/*     */         } 
/* 292 */         decompressed = prepareDecompressBuffer(ctx, decompressed, this.inflater.getRemaining() << 1);
/*     */       } 
/*     */ 
/*     */       
/* 296 */       in.skipBytes(readableBytes - this.inflater.getRemaining());
/*     */       
/* 298 */       if (readFooter) {
/* 299 */         this.gzipState = GzipState.FOOTER_START;
/* 300 */         handleGzipFooter(in);
/*     */       } 
/* 302 */     } catch (DataFormatException e) {
/* 303 */       throw new DecompressionException("decompression failure", e);
/*     */     } finally {
/* 305 */       if (decompressed != null) {
/* 306 */         if (decompressed.isReadable()) {
/* 307 */           this.needsRead = false;
/* 308 */           ctx.fireChannelRead(decompressed);
/*     */         } else {
/* 310 */           decompressed.release();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean handleGzipFooter(ByteBuf in) {
/* 317 */     if (readGZIPFooter(in)) {
/* 318 */       this.finished = !this.decompressConcatenated;
/*     */       
/* 320 */       if (!this.finished) {
/* 321 */         this.inflater.reset();
/* 322 */         this.crc.reset();
/* 323 */         this.gzipState = GzipState.HEADER_START;
/* 324 */         return true;
/*     */       } 
/*     */     } 
/* 327 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decompressionBufferExhausted(ByteBuf buffer) {
/* 332 */     this.finished = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/* 337 */     super.handlerRemoved0(ctx);
/* 338 */     if (this.inflater != null)
/* 339 */       this.inflater.end(); 
/*     */   } private boolean readGZIPHeader(ByteBuf in) {
/*     */     int magic0;
/*     */     int magic1;
/*     */     int method;
/* 344 */     switch (this.gzipState) {
/*     */       case HEADER_START:
/* 346 */         if (in.readableBytes() < 10) {
/* 347 */           return false;
/*     */         }
/*     */         
/* 350 */         magic0 = in.readByte();
/* 351 */         magic1 = in.readByte();
/*     */         
/* 353 */         if (magic0 != 31) {
/* 354 */           throw new DecompressionException("Input is not in the GZIP format");
/*     */         }
/* 356 */         this.crc.update(magic0);
/* 357 */         this.crc.update(magic1);
/*     */         
/* 359 */         method = in.readUnsignedByte();
/* 360 */         if (method != 8) {
/* 361 */           throw new DecompressionException("Unsupported compression method " + method + " in the GZIP header");
/*     */         }
/*     */         
/* 364 */         this.crc.update(method);
/*     */         
/* 366 */         this.flags = in.readUnsignedByte();
/* 367 */         this.crc.update(this.flags);
/*     */         
/* 369 */         if ((this.flags & 0xE0) != 0) {
/* 370 */           throw new DecompressionException("Reserved flags are set in the GZIP header");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 375 */         this.crc.update(in, in.readerIndex(), 4);
/* 376 */         in.skipBytes(4);
/*     */         
/* 378 */         this.crc.update(in.readUnsignedByte());
/* 379 */         this.crc.update(in.readUnsignedByte());
/*     */         
/* 381 */         this.gzipState = GzipState.FLG_READ;
/*     */       
/*     */       case FLG_READ:
/* 384 */         if ((this.flags & 0x4) != 0) {
/* 385 */           if (in.readableBytes() < 2) {
/* 386 */             return false;
/*     */           }
/* 388 */           int xlen1 = in.readUnsignedByte();
/* 389 */           int xlen2 = in.readUnsignedByte();
/* 390 */           this.crc.update(xlen1);
/* 391 */           this.crc.update(xlen2);
/*     */           
/* 393 */           this.xlen |= xlen1 << 8 | xlen2;
/*     */         } 
/* 395 */         this.gzipState = GzipState.XLEN_READ;
/*     */       
/*     */       case XLEN_READ:
/* 398 */         if (this.xlen != -1) {
/* 399 */           if (in.readableBytes() < this.xlen) {
/* 400 */             return false;
/*     */           }
/* 402 */           this.crc.update(in, in.readerIndex(), this.xlen);
/* 403 */           in.skipBytes(this.xlen);
/*     */         } 
/* 405 */         this.gzipState = GzipState.SKIP_FNAME;
/*     */       
/*     */       case SKIP_FNAME:
/* 408 */         if (!skipIfNeeded(in, 8)) {
/* 409 */           return false;
/*     */         }
/* 411 */         this.gzipState = GzipState.SKIP_COMMENT;
/*     */       
/*     */       case SKIP_COMMENT:
/* 414 */         if (!skipIfNeeded(in, 16)) {
/* 415 */           return false;
/*     */         }
/* 417 */         this.gzipState = GzipState.PROCESS_FHCRC;
/*     */       
/*     */       case PROCESS_FHCRC:
/* 420 */         if ((this.flags & 0x2) != 0 && 
/* 421 */           !verifyCrc16(in)) {
/* 422 */           return false;
/*     */         }
/*     */         
/* 425 */         this.crc.reset();
/* 426 */         this.gzipState = GzipState.HEADER_END;
/*     */       
/*     */       case HEADER_END:
/* 429 */         return true;
/*     */     } 
/* 431 */     throw new IllegalStateException();
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
/*     */   private boolean skipIfNeeded(ByteBuf in, int flagMask) {
/* 443 */     if ((this.flags & flagMask) != 0) {
/*     */       int b; do {
/* 445 */         if (!in.isReadable())
/*     */         {
/* 447 */           return false;
/*     */         }
/* 449 */         b = in.readUnsignedByte();
/* 450 */         this.crc.update(b);
/* 451 */       } while (b != 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 457 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readGZIPFooter(ByteBuf in) {
/* 468 */     if (in.readableBytes() < 8) {
/* 469 */       return false;
/*     */     }
/*     */     
/* 472 */     boolean enoughData = verifyCrc(in);
/* 473 */     assert enoughData;
/*     */ 
/*     */     
/* 476 */     int dataLength = in.readIntLE();
/* 477 */     int readLength = this.inflater.getTotalOut();
/* 478 */     if (dataLength != readLength) {
/* 479 */       throw new DecompressionException("Number of bytes mismatch. Expected: " + dataLength + ", Got: " + readLength);
/*     */     }
/*     */     
/* 482 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean verifyCrc(ByteBuf in) {
/* 493 */     if (in.readableBytes() < 4) {
/* 494 */       return false;
/*     */     }
/* 496 */     long crcValue = in.readUnsignedIntLE();
/*     */     
/* 498 */     long readCrc = this.crc.getValue();
/* 499 */     if (crcValue != readCrc) {
/* 500 */       throw new DecompressionException("CRC value mismatch. Expected: " + crcValue + ", Got: " + readCrc);
/*     */     }
/*     */     
/* 503 */     return true;
/*     */   }
/*     */   
/*     */   private boolean verifyCrc16(ByteBuf in) {
/* 507 */     if (in.readableBytes() < 2) {
/* 508 */       return false;
/*     */     }
/*     */     
/* 511 */     int crc16Value = in.readUnsignedShortLE();
/*     */     
/* 513 */     int readCrc16 = (int)(this.crc.getValue() & 0xFFFFL);
/*     */     
/* 515 */     if (crc16Value != readCrc16) {
/* 516 */       throw new DecompressionException("CRC16 value mismatch. Expected: " + crc16Value + ", Got: " + readCrc16);
/*     */     }
/*     */     
/* 519 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean looksLikeZlib(short cmf_flg) {
/* 530 */     return ((cmf_flg & 0x7800) == 30720 && cmf_flg % 31 == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 537 */     discardSomeReadBytes();
/*     */     
/* 539 */     if (this.needsRead && !ctx.channel().config().isAutoRead()) {
/* 540 */       ctx.read();
/*     */     }
/* 542 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\JdkZlibDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */