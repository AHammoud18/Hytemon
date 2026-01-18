/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
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
/*     */ public class FastLzFrameEncoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   private final int level;
/*     */   private final ByteBufChecksum checksum;
/*     */   
/*     */   public FastLzFrameEncoder() {
/*  60 */     this(0, null);
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
/*     */   public FastLzFrameEncoder(int level) {
/*  72 */     this(level, null);
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
/*     */   public FastLzFrameEncoder(boolean validateChecksums) {
/*  86 */     this(0, validateChecksums ? new Adler32() : null);
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
/*     */   public FastLzFrameEncoder(int level, Checksum checksum) {
/* 101 */     super(ByteBuf.class);
/* 102 */     if (level != 0 && level != 1 && level != 2)
/* 103 */       throw new IllegalArgumentException(String.format("level: %d (expected: %d or %d or %d)", new Object[] {
/* 104 */               Integer.valueOf(level), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2)
/*     */             })); 
/* 106 */     this.level = level;
/* 107 */     this.checksum = (checksum == null) ? null : ByteBufChecksum.wrapChecksum(checksum);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/* 112 */     ByteBufChecksum checksum = this.checksum; while (true) {
/*     */       byte blockType;
/*     */       int chunkLength;
/* 115 */       if (!in.isReadable()) {
/*     */         return;
/*     */       }
/* 118 */       int idx = in.readerIndex();
/* 119 */       int length = Math.min(in.readableBytes(), 65535);
/*     */       
/* 121 */       int outputIdx = out.writerIndex();
/* 122 */       out.setMedium(outputIdx, 4607066);
/* 123 */       int outputOffset = outputIdx + 4 + ((checksum != null) ? 4 : 0);
/*     */ 
/*     */ 
/*     */       
/* 127 */       if (length < 32) {
/* 128 */         blockType = 0;
/*     */         
/* 130 */         out.ensureWritable(outputOffset + 2 + length);
/* 131 */         int outputPtr = outputOffset + 2;
/*     */         
/* 133 */         if (checksum != null) {
/* 134 */           checksum.reset();
/* 135 */           checksum.update(in, idx, length);
/* 136 */           out.setInt(outputIdx + 4, (int)checksum.getValue());
/*     */         } 
/* 138 */         out.setBytes(outputPtr, in, idx, length);
/* 139 */         chunkLength = length;
/*     */       } else {
/*     */         
/* 142 */         if (checksum != null) {
/* 143 */           checksum.reset();
/* 144 */           checksum.update(in, idx, length);
/* 145 */           out.setInt(outputIdx + 4, (int)checksum.getValue());
/*     */         } 
/*     */         
/* 148 */         int maxOutputLength = FastLz.calculateOutputBufferLength(length);
/* 149 */         out.ensureWritable(outputOffset + 4 + maxOutputLength);
/* 150 */         int outputPtr = outputOffset + 4;
/* 151 */         int compressedLength = FastLz.compress(in, in.readerIndex(), length, out, outputPtr, this.level);
/*     */         
/* 153 */         if (compressedLength < length) {
/* 154 */           blockType = 1;
/* 155 */           chunkLength = compressedLength;
/*     */           
/* 157 */           out.setShort(outputOffset, chunkLength);
/* 158 */           outputOffset += 2;
/*     */         } else {
/* 160 */           blockType = 0;
/* 161 */           out.setBytes(outputOffset + 2, in, idx, length);
/* 162 */           chunkLength = length;
/*     */         } 
/*     */       } 
/* 165 */       out.setShort(outputOffset, length);
/*     */       
/* 167 */       out.setByte(outputIdx + 3, blockType | (
/* 168 */           (checksum != null) ? 16 : 0));
/* 169 */       out.writerIndex(outputOffset + 2 + chunkLength);
/* 170 */       in.skipBytes(length);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\FastLzFrameEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */