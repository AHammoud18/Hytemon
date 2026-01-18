/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.ning.compress.BufferRecycler;
/*     */ import com.ning.compress.lzf.ChunkEncoder;
/*     */ import com.ning.compress.lzf.LZFChunk;
/*     */ import com.ning.compress.lzf.LZFEncoder;
/*     */ import com.ning.compress.lzf.util.ChunkEncoderFactory;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LzfEncoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   private static final int MIN_BLOCK_TO_COMPRESS = 16;
/*  43 */   private static final boolean DEFAULT_SAFE = !PlatformDependent.hasUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int compressThreshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ChunkEncoder encoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BufferRecycler recycler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LzfEncoder() {
/*  71 */     this(DEFAULT_SAFE);
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
/*     */   @Deprecated
/*     */   public LzfEncoder(boolean safeInstance) {
/*  86 */     this(safeInstance, 65535);
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
/*     */   @Deprecated
/*     */   public LzfEncoder(boolean safeInstance, int totalLength) {
/* 103 */     this(safeInstance, totalLength, 16);
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
/*     */   public LzfEncoder(int totalLength) {
/* 115 */     this(DEFAULT_SAFE, totalLength);
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
/*     */   public LzfEncoder(int totalLength, int compressThreshold) {
/* 128 */     this(DEFAULT_SAFE, totalLength, compressThreshold);
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
/*     */   @Deprecated
/*     */   public LzfEncoder(boolean safeInstance, int totalLength, int compressThreshold) {
/* 148 */     super(ByteBuf.class, false);
/* 149 */     if (totalLength < 16 || totalLength > 65535) {
/* 150 */       throw new IllegalArgumentException("totalLength: " + totalLength + " (expected: " + '\020' + '-' + Character.MAX_VALUE + ')');
/*     */     }
/*     */ 
/*     */     
/* 154 */     if (compressThreshold < 16)
/*     */     {
/* 156 */       throw new IllegalArgumentException("compressThreshold:" + compressThreshold + " expected >=" + '\020');
/*     */     }
/*     */     
/* 159 */     this.compressThreshold = compressThreshold;
/*     */     
/* 161 */     this
/*     */       
/* 163 */       .encoder = safeInstance ? ChunkEncoderFactory.safeNonAllocatingInstance(totalLength) : ChunkEncoderFactory.optimalNonAllocatingInstance(totalLength);
/*     */     
/* 165 */     this.recycler = BufferRecycler.instance();
/*     */   } protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/*     */     byte[] input;
/*     */     int inputPtr;
/*     */     byte[] output;
/* 170 */     int outputPtr, outputLength, length = in.readableBytes();
/* 171 */     int idx = in.readerIndex();
/*     */ 
/*     */     
/* 174 */     if (in.hasArray()) {
/* 175 */       input = in.array();
/* 176 */       inputPtr = in.arrayOffset() + idx;
/*     */     } else {
/* 178 */       input = this.recycler.allocInputBuffer(length);
/* 179 */       in.getBytes(idx, input, 0, length);
/* 180 */       inputPtr = 0;
/*     */     } 
/*     */ 
/*     */     
/* 184 */     int maxOutputLength = LZFEncoder.estimateMaxWorkspaceSize(length) + 1;
/* 185 */     out.ensureWritable(maxOutputLength);
/*     */ 
/*     */     
/* 188 */     if (out.hasArray()) {
/* 189 */       output = out.array();
/* 190 */       outputPtr = out.arrayOffset() + out.writerIndex();
/*     */     } else {
/* 192 */       output = new byte[maxOutputLength];
/* 193 */       outputPtr = 0;
/*     */     } 
/*     */ 
/*     */     
/* 197 */     if (length >= this.compressThreshold) {
/*     */       
/* 199 */       outputLength = encodeCompress(input, inputPtr, length, output, outputPtr);
/*     */     } else {
/*     */       
/* 202 */       outputLength = encodeNonCompress(input, inputPtr, length, output, outputPtr);
/*     */     } 
/*     */     
/* 205 */     if (out.hasArray()) {
/* 206 */       out.writerIndex(out.writerIndex() + outputLength);
/*     */     } else {
/* 208 */       out.writeBytes(output, 0, outputLength);
/*     */     } 
/*     */     
/* 211 */     in.skipBytes(length);
/*     */     
/* 213 */     if (!in.hasArray()) {
/* 214 */       this.recycler.releaseInputBuffer(input);
/*     */     }
/*     */   }
/*     */   
/*     */   private int encodeCompress(byte[] input, int inputPtr, int length, byte[] output, int outputPtr) {
/* 219 */     return LZFEncoder.appendEncoded(this.encoder, input, inputPtr, length, output, outputPtr) - outputPtr;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int lzfEncodeNonCompress(byte[] input, int inputPtr, int length, byte[] output, int outputPtr) {
/* 224 */     int left = length;
/* 225 */     int chunkLen = Math.min(65535, left);
/* 226 */     outputPtr = LZFChunk.appendNonCompressed(input, inputPtr, chunkLen, output, outputPtr);
/* 227 */     left -= chunkLen;
/* 228 */     if (left < 1) {
/* 229 */       return outputPtr;
/*     */     }
/* 231 */     inputPtr += chunkLen;
/*     */     while (true) {
/* 233 */       chunkLen = Math.min(left, 65535);
/* 234 */       outputPtr = LZFChunk.appendNonCompressed(input, inputPtr, chunkLen, output, outputPtr);
/* 235 */       inputPtr += chunkLen;
/* 236 */       left -= chunkLen;
/* 237 */       if (left <= 0) {
/* 238 */         return outputPtr;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int encodeNonCompress(byte[] input, int inputPtr, int length, byte[] output, int outputPtr) {
/* 245 */     return lzfEncodeNonCompress(input, inputPtr, length, output, outputPtr) - outputPtr;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 250 */     this.encoder.close();
/* 251 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\LzfEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */