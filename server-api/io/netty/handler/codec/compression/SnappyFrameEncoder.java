/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
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
/*     */ public class SnappyFrameEncoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   private static final short SNAPPY_SLICE_SIZE = 32767;
/*     */   private static final int SNAPPY_SLICE_JUMBO_SIZE = 65535;
/*     */   private static final int MIN_COMPRESSIBLE_LENGTH = 18;
/*  52 */   private static final byte[] STREAM_START = new byte[] { -1, 6, 0, 0, 115, 78, 97, 80, 112, 89 };
/*     */   
/*     */   private final Snappy snappy;
/*     */   
/*     */   public SnappyFrameEncoder() {
/*  57 */     this(32767);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean started;
/*     */   
/*     */   private final int sliceSize;
/*     */   
/*     */   public static SnappyFrameEncoder snappyEncoderWithJumboFrames() {
/*  66 */     return new SnappyFrameEncoder(65535);
/*     */   }
/*     */   
/*     */   private SnappyFrameEncoder(int sliceSize) {
/*  70 */     super(ByteBuf.class);
/*     */ 
/*     */ 
/*     */     
/*  74 */     this.snappy = new Snappy();
/*     */     this.sliceSize = sliceSize;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/*  80 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     if (!this.started) {
/*  85 */       this.started = true;
/*  86 */       out.writeBytes(STREAM_START);
/*     */     } 
/*     */     
/*  89 */     int dataLength = in.readableBytes();
/*  90 */     if (dataLength > 18) {
/*     */       while (true) {
/*  92 */         int lengthIdx = out.writerIndex() + 1;
/*  93 */         if (dataLength < 18) {
/*  94 */           ByteBuf byteBuf = in.readSlice(dataLength);
/*  95 */           writeUnencodedChunk(byteBuf, out, dataLength);
/*     */           
/*     */           break;
/*     */         } 
/*  99 */         out.writeInt(0);
/* 100 */         if (dataLength > this.sliceSize) {
/* 101 */           ByteBuf byteBuf = in.readSlice(this.sliceSize);
/* 102 */           calculateAndWriteChecksum(byteBuf, out);
/* 103 */           this.snappy.encode(byteBuf, out, this.sliceSize);
/* 104 */           setChunkLength(out, lengthIdx);
/* 105 */           dataLength -= this.sliceSize; continue;
/*     */         } 
/* 107 */         ByteBuf slice = in.readSlice(dataLength);
/* 108 */         calculateAndWriteChecksum(slice, out);
/* 109 */         this.snappy.encode(slice, out, dataLength);
/* 110 */         setChunkLength(out, lengthIdx);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } else {
/* 115 */       writeUnencodedChunk(in, out, dataLength);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeUnencodedChunk(ByteBuf in, ByteBuf out, int dataLength) {
/* 120 */     out.writeByte(1);
/* 121 */     writeChunkLength(out, dataLength + 4);
/* 122 */     calculateAndWriteChecksum(in, out);
/* 123 */     out.writeBytes(in, dataLength);
/*     */   }
/*     */   
/*     */   private static void setChunkLength(ByteBuf out, int lengthIdx) {
/* 127 */     int chunkLength = out.writerIndex() - lengthIdx - 3;
/* 128 */     if (chunkLength >>> 24 != 0) {
/* 129 */       throw new CompressionException("compressed data too large: " + chunkLength);
/*     */     }
/* 131 */     out.setMediumLE(lengthIdx, chunkLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeChunkLength(ByteBuf out, int chunkLength) {
/* 141 */     out.writeMediumLE(chunkLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void calculateAndWriteChecksum(ByteBuf slice, ByteBuf out) {
/* 151 */     out.writeIntLE(Snappy.calculateChecksum(slice));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\SnappyFrameEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */