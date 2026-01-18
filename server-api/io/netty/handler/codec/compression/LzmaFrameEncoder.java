/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import io.netty.buffer.ByteBufOutputStream;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import lzma.sdk.lzma.Encoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LzmaFrameEncoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*  41 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(LzmaFrameEncoder.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MEDIUM_DICTIONARY_SIZE = 65536;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MIN_FAST_BYTES = 5;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MEDIUM_FAST_BYTES = 32;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_FAST_BYTES = 273;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_MATCH_FINDER = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_LC = 3;
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_LP = 0;
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_PB = 2;
/*     */ 
/*     */   
/*     */   private final Encoder encoder;
/*     */ 
/*     */   
/*     */   private final byte properties;
/*     */ 
/*     */   
/*     */   private final int littleEndianDictionarySize;
/*     */ 
/*     */   
/*     */   private static boolean warningLogged;
/*     */ 
/*     */ 
/*     */   
/*     */   public LzmaFrameEncoder() {
/*  88 */     this(65536);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LzmaFrameEncoder(int lc, int lp, int pb) {
/*  96 */     this(lc, lp, pb, 65536);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LzmaFrameEncoder(int dictionarySize) {
/* 106 */     this(3, 0, 2, dictionarySize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LzmaFrameEncoder(int lc, int lp, int pb, int dictionarySize) {
/* 113 */     this(lc, lp, pb, dictionarySize, false, 32);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LzmaFrameEncoder(int lc, int lp, int pb, int dictionarySize, boolean endMarkerMode, int numFastBytes) {
/* 138 */     super(ByteBuf.class);
/* 139 */     if (lc < 0 || lc > 8) {
/* 140 */       throw new IllegalArgumentException("lc: " + lc + " (expected: 0-8)");
/*     */     }
/* 142 */     if (lp < 0 || lp > 4) {
/* 143 */       throw new IllegalArgumentException("lp: " + lp + " (expected: 0-4)");
/*     */     }
/* 145 */     if (pb < 0 || pb > 4) {
/* 146 */       throw new IllegalArgumentException("pb: " + pb + " (expected: 0-4)");
/*     */     }
/* 148 */     if (lc + lp > 4 && 
/* 149 */       !warningLogged) {
/* 150 */       logger.warn("The latest versions of LZMA libraries (for example, XZ Utils) has an additional requirement: lc + lp <= 4. Data which don't follow this requirement cannot be decompressed with this libraries.");
/*     */ 
/*     */       
/* 153 */       warningLogged = true;
/*     */     } 
/*     */     
/* 156 */     if (dictionarySize < 0) {
/* 157 */       throw new IllegalArgumentException("dictionarySize: " + dictionarySize + " (expected: 0+)");
/*     */     }
/* 159 */     if (numFastBytes < 5 || numFastBytes > 273) {
/* 160 */       throw new IllegalArgumentException(String.format("numFastBytes: %d (expected: %d-%d)", new Object[] {
/* 161 */               Integer.valueOf(numFastBytes), Integer.valueOf(5), Integer.valueOf(273)
/*     */             }));
/*     */     }
/*     */     
/* 165 */     this.encoder = new Encoder();
/* 166 */     this.encoder.setDictionarySize(dictionarySize);
/* 167 */     this.encoder.setEndMarkerMode(endMarkerMode);
/* 168 */     this.encoder.setMatchFinder(1);
/* 169 */     this.encoder.setNumFastBytes(numFastBytes);
/* 170 */     this.encoder.setLcLpPb(lc, lp, pb);
/*     */     
/* 172 */     this.properties = (byte)((pb * 5 + lp) * 9 + lc);
/* 173 */     this.littleEndianDictionarySize = Integer.reverseBytes(dictionarySize);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/* 178 */     int length = in.readableBytes();
/* 179 */     ByteBufInputStream byteBufInputStream = new ByteBufInputStream(in); 
/* 180 */     try { ByteBufOutputStream bbOut = new ByteBufOutputStream(out); 
/* 181 */       try { bbOut.writeByte(this.properties);
/* 182 */         bbOut.writeInt(this.littleEndianDictionarySize);
/* 183 */         bbOut.writeLong(Long.reverseBytes(length));
/* 184 */         this.encoder.code((InputStream)byteBufInputStream, (OutputStream)bbOut, -1L, -1L, null);
/* 185 */         bbOut.close(); } catch (Throwable throwable) { try { bbOut.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  byteBufInputStream.close(); }
/*     */     catch (Throwable throwable) { try { byteBufInputStream.close(); }
/*     */       catch (Throwable throwable1)
/*     */       { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 190 */      } protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf in, boolean preferDirect) throws Exception { int length = in.readableBytes();
/* 191 */     int maxOutputLength = maxOutputBufferLength(length);
/* 192 */     return ctx.alloc().ioBuffer(maxOutputLength); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int maxOutputBufferLength(int inputLength) {
/*     */     double factor;
/* 200 */     if (inputLength < 200) {
/* 201 */       factor = 1.5D;
/* 202 */     } else if (inputLength < 500) {
/* 203 */       factor = 1.2D;
/* 204 */     } else if (inputLength < 1000) {
/* 205 */       factor = 1.1D;
/* 206 */     } else if (inputLength < 10000) {
/* 207 */       factor = 1.05D;
/*     */     } else {
/* 209 */       factor = 1.02D;
/*     */     } 
/* 211 */     return 13 + (int)(inputLength * factor);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\LzmaFrameEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */