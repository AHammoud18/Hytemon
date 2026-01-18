/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import java.util.List;
/*     */ import java.util.zip.Adler32;
/*     */ import java.util.zip.Checksum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastLzFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private enum State
/*     */   {
/*  41 */     INIT_BLOCK,
/*  42 */     INIT_BLOCK_PARAMS,
/*  43 */     DECOMPRESS_DATA,
/*  44 */     CORRUPTED;
/*     */   }
/*     */   
/*  47 */   private State currentState = State.INIT_BLOCK;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ByteBufChecksum checksum;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int chunkLength;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int originalLength;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCompressed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasChecksum;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int currentChecksum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastLzFrameDecoder() {
/*  84 */     this(false);
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
/*     */   public FastLzFrameDecoder(boolean validateChecksums) {
/*  98 */     this(validateChecksums ? new Adler32() : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastLzFrameDecoder(Checksum checksum) {
/* 109 */     this.checksum = (checksum == null) ? null : ByteBufChecksum.wrapChecksum(checksum);
/*     */   } protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*     */     try {
/*     */       int magic;
/*     */       byte options;
/*     */       int chunkLength;
/* 115 */       switch (this.currentState) {
/*     */         case INIT_BLOCK:
/* 117 */           if (in.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/* 121 */           magic = in.readUnsignedMedium();
/* 122 */           if (magic != 4607066) {
/* 123 */             throw new DecompressionException("unexpected block identifier");
/*     */           }
/*     */           
/* 126 */           options = in.readByte();
/* 127 */           this.isCompressed = ((options & 0x1) == 1);
/* 128 */           this.hasChecksum = ((options & 0x10) == 16);
/*     */           
/* 130 */           this.currentState = State.INIT_BLOCK_PARAMS;
/*     */         
/*     */         case INIT_BLOCK_PARAMS:
/* 133 */           if (in.readableBytes() < 2 + (this.isCompressed ? 2 : 0) + (this.hasChecksum ? 4 : 0)) {
/*     */             return;
/*     */           }
/* 136 */           this.currentChecksum = this.hasChecksum ? in.readInt() : 0;
/* 137 */           this.chunkLength = in.readUnsignedShort();
/* 138 */           this.originalLength = this.isCompressed ? in.readUnsignedShort() : this.chunkLength;
/*     */           
/* 140 */           this.currentState = State.DECOMPRESS_DATA;
/*     */         
/*     */         case DECOMPRESS_DATA:
/* 143 */           chunkLength = this.chunkLength;
/* 144 */           if (in.readableBytes() >= chunkLength) {
/*     */ 
/*     */ 
/*     */             
/* 148 */             int idx = in.readerIndex();
/* 149 */             int originalLength = this.originalLength;
/*     */             
/* 151 */             ByteBuf output = null;
/*     */             
/*     */             try {
/* 154 */               if (this.isCompressed) {
/*     */                 
/* 156 */                 output = ctx.alloc().buffer(originalLength);
/* 157 */                 int outputOffset = output.writerIndex();
/* 158 */                 int decompressedBytes = FastLz.decompress(in, idx, chunkLength, output, outputOffset, originalLength);
/*     */                 
/* 160 */                 if (originalLength != decompressedBytes)
/* 161 */                   throw new DecompressionException(String.format("stream corrupted: originalLength(%d) and actual length(%d) mismatch", new Object[] {
/*     */                           
/* 163 */                           Integer.valueOf(originalLength), Integer.valueOf(decompressedBytes)
/*     */                         })); 
/* 165 */                 output.writerIndex(output.writerIndex() + decompressedBytes);
/*     */               } else {
/* 167 */                 output = in.retainedSlice(idx, chunkLength);
/*     */               } 
/*     */               
/* 170 */               ByteBufChecksum checksum = this.checksum;
/* 171 */               if (this.hasChecksum && checksum != null) {
/* 172 */                 checksum.reset();
/* 173 */                 checksum.update(output, output.readerIndex(), output.readableBytes());
/* 174 */                 int checksumResult = (int)checksum.getValue();
/* 175 */                 if (checksumResult != this.currentChecksum) {
/* 176 */                   throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", new Object[] {
/*     */                           
/* 178 */                           Integer.valueOf(checksumResult), Integer.valueOf(this.currentChecksum)
/*     */                         }));
/*     */                 }
/*     */               } 
/* 182 */               if (output.readableBytes() > 0) {
/* 183 */                 out.add(output);
/*     */               } else {
/* 185 */                 output.release();
/*     */               } 
/* 187 */               output = null;
/* 188 */               in.skipBytes(chunkLength);
/*     */               
/* 190 */               this.currentState = State.INIT_BLOCK;
/*     */             } finally {
/* 192 */               if (output != null)
/* 193 */                 output.release(); 
/*     */             } 
/*     */           } 
/*     */           return;
/*     */         case CORRUPTED:
/* 198 */           in.skipBytes(in.readableBytes());
/*     */           return;
/*     */       } 
/* 201 */       throw new IllegalStateException();
/*     */     }
/* 203 */     catch (Exception e) {
/* 204 */       this.currentState = State.CORRUPTED;
/* 205 */       throw e;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\FastLzFrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */