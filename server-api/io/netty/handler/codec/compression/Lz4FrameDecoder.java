/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.List;
/*     */ import java.util.zip.Checksum;
/*     */ import net.jpountz.lz4.LZ4Factory;
/*     */ import net.jpountz.lz4.LZ4FastDecompressor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Lz4FrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private enum State
/*     */   {
/*  58 */     INIT_BLOCK,
/*  59 */     DECOMPRESS_DATA,
/*  60 */     FINISHED,
/*  61 */     CORRUPTED;
/*     */   }
/*     */   
/*  64 */   private State currentState = State.INIT_BLOCK;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LZ4FastDecompressor decompressor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ByteBufChecksum checksum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int blockType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int compressedLength;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int decompressedLength;
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
/*     */   public Lz4FrameDecoder() {
/* 106 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Lz4FrameDecoder(boolean validateChecksums) {
/* 117 */     this(LZ4Factory.fastestInstance(), validateChecksums);
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
/*     */   public Lz4FrameDecoder(LZ4Factory factory, boolean validateChecksums) {
/* 133 */     this(factory, validateChecksums ? new Lz4XXHash32(-1756908916) : null);
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
/*     */   public Lz4FrameDecoder(LZ4Factory factory, Checksum checksum) {
/* 146 */     this.decompressor = ((LZ4Factory)ObjectUtil.checkNotNull(factory, "factory")).fastDecompressor();
/* 147 */     this.checksum = (checksum == null) ? null : ByteBufChecksum.wrapChecksum(checksum); } protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*     */     try {
/*     */       int blockType;
/*     */       int compressedLength;
/*     */       int decompressedLength;
/*     */       int currentChecksum;
/* 153 */       switch (this.currentState) {
/*     */         case INIT_BLOCK:
/* 155 */           if (in.readableBytes() >= 21) {
/*     */ 
/*     */             
/* 158 */             long magic = in.readLong();
/* 159 */             if (magic != 5501767354678207339L) {
/* 160 */               throw new DecompressionException("unexpected block identifier");
/*     */             }
/*     */             
/* 163 */             int token = in.readByte();
/* 164 */             int compressionLevel = (token & 0xF) + 10;
/* 165 */             int i = token & 0xF0;
/*     */             
/* 167 */             int j = Integer.reverseBytes(in.readInt());
/* 168 */             if (j < 0 || j > 33554432) {
/* 169 */               throw new DecompressionException(String.format("invalid compressedLength: %d (expected: 0-%d)", new Object[] {
/*     */                       
/* 171 */                       Integer.valueOf(j), Integer.valueOf(33554432)
/*     */                     }));
/*     */             }
/* 174 */             int k = Integer.reverseBytes(in.readInt());
/* 175 */             int maxDecompressedLength = 1 << compressionLevel;
/* 176 */             if (k < 0 || k > maxDecompressedLength)
/* 177 */               throw new DecompressionException(String.format("invalid decompressedLength: %d (expected: 0-%d)", new Object[] {
/*     */                       
/* 179 */                       Integer.valueOf(k), Integer.valueOf(maxDecompressedLength)
/*     */                     })); 
/* 181 */             if ((k == 0 && j != 0) || (k != 0 && j == 0) || (i == 16 && k != j))
/*     */             {
/*     */               
/* 184 */               throw new DecompressionException(String.format("stream corrupted: compressedLength(%d) and decompressedLength(%d) mismatch", new Object[] {
/*     */                       
/* 186 */                       Integer.valueOf(j), Integer.valueOf(k)
/*     */                     }));
/*     */             }
/* 189 */             int m = Integer.reverseBytes(in.readInt());
/* 190 */             if (k == 0 && j == 0)
/* 191 */             { if (m != 0) {
/* 192 */                 throw new DecompressionException("stream corrupted: checksum error");
/*     */               }
/* 194 */               this.currentState = State.FINISHED;
/* 195 */               this.decompressor = null;
/* 196 */               this.checksum = null; }
/*     */             
/*     */             else
/*     */             
/* 200 */             { this.blockType = i;
/* 201 */               this.compressedLength = j;
/* 202 */               this.decompressedLength = k;
/* 203 */               this.currentChecksum = m;
/*     */               
/* 205 */               this.currentState = State.DECOMPRESS_DATA; } 
/*     */           }  return;
/*     */         case DECOMPRESS_DATA:
/* 208 */           blockType = this.blockType;
/* 209 */           compressedLength = this.compressedLength;
/* 210 */           decompressedLength = this.decompressedLength;
/* 211 */           currentChecksum = this.currentChecksum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case FINISHED:
/*     */         case CORRUPTED:
/* 259 */           in.skipBytes(in.readableBytes());
/*     */           return;
/*     */       } 
/* 262 */       throw new IllegalStateException();
/*     */     }
/* 264 */     catch (Exception e) {
/* 265 */       this.currentState = State.CORRUPTED;
/* 266 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 275 */     return (this.currentState == State.FINISHED);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Lz4FrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */