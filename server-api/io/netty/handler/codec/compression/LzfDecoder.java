/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.ning.compress.BufferRecycler;
/*     */ import com.ning.compress.lzf.ChunkDecoder;
/*     */ import com.ning.compress.lzf.util.ChunkDecoderFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LzfDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private enum State
/*     */   {
/*  45 */     INIT_BLOCK,
/*  46 */     INIT_ORIGINAL_LENGTH,
/*  47 */     DECOMPRESS_DATA,
/*  48 */     CORRUPTED;
/*     */   }
/*     */   
/*  51 */   private State currentState = State.INIT_BLOCK;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short MAGIC_NUMBER = 23126;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChunkDecoder decoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferRecycler recycler;
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
/*     */   
/*     */   public LzfDecoder() {
/*  91 */     this(false);
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
/*     */   public LzfDecoder(boolean safeInstance) {
/* 104 */     this
/*     */       
/* 106 */       .decoder = safeInstance ? ChunkDecoderFactory.safeInstance() : ChunkDecoderFactory.optimalInstance();
/*     */     
/* 108 */     this.recycler = BufferRecycler.instance();
/*     */   } protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*     */     try {
/*     */       int magic;
/*     */       int type;
/*     */       int chunkLength;
/* 114 */       switch (this.currentState) {
/*     */         case INIT_BLOCK:
/* 116 */           if (in.readableBytes() < 5) {
/*     */             return;
/*     */           }
/* 119 */           magic = in.readUnsignedShort();
/* 120 */           if (magic != 23126) {
/* 121 */             throw new DecompressionException("unexpected block identifier");
/*     */           }
/*     */           
/* 124 */           type = in.readByte();
/* 125 */           switch (type) {
/*     */             case 0:
/* 127 */               this.isCompressed = false;
/* 128 */               this.currentState = State.DECOMPRESS_DATA;
/*     */               break;
/*     */             case 1:
/* 131 */               this.isCompressed = true;
/* 132 */               this.currentState = State.INIT_ORIGINAL_LENGTH;
/*     */               break;
/*     */             default:
/* 135 */               throw new DecompressionException(String.format("unknown type of chunk: %d (expected: %d or %d)", new Object[] {
/*     */                       
/* 137 */                       Integer.valueOf(type), Integer.valueOf(0), Integer.valueOf(1) }));
/*     */           } 
/* 139 */           this.chunkLength = in.readUnsignedShort();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 144 */           if (this.chunkLength > 65535) {
/* 145 */             throw new DecompressionException(String.format("chunk length exceeds maximum: %d (expected: =< %d)", new Object[] {
/*     */                     
/* 147 */                     Integer.valueOf(this.chunkLength), Integer.valueOf(65535)
/*     */                   }));
/*     */           }
/* 150 */           if (type != 1) {
/*     */             return;
/*     */           }
/*     */         
/*     */         case INIT_ORIGINAL_LENGTH:
/* 155 */           if (in.readableBytes() < 2) {
/*     */             return;
/*     */           }
/* 158 */           this.originalLength = in.readUnsignedShort();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 163 */           if (this.originalLength > 65535) {
/* 164 */             throw new DecompressionException(String.format("original length exceeds maximum: %d (expected: =< %d)", new Object[] {
/*     */                     
/* 166 */                     Integer.valueOf(this.chunkLength), Integer.valueOf(65535)
/*     */                   }));
/*     */           }
/* 169 */           this.currentState = State.DECOMPRESS_DATA;
/*     */         
/*     */         case DECOMPRESS_DATA:
/* 172 */           chunkLength = this.chunkLength;
/* 173 */           if (in.readableBytes() >= chunkLength) {
/*     */ 
/*     */             
/* 176 */             int originalLength = this.originalLength;
/*     */             
/* 178 */             if (this.isCompressed) {
/* 179 */               byte[] inputArray; int inPos; byte[] outputArray; int outPos, idx = in.readerIndex();
/*     */ 
/*     */ 
/*     */               
/* 183 */               if (in.hasArray()) {
/* 184 */                 inputArray = in.array();
/* 185 */                 inPos = in.arrayOffset() + idx;
/*     */               } else {
/* 187 */                 inputArray = this.recycler.allocInputBuffer(chunkLength);
/* 188 */                 in.getBytes(idx, inputArray, 0, chunkLength);
/* 189 */                 inPos = 0;
/*     */               } 
/*     */               
/* 192 */               ByteBuf uncompressed = ctx.alloc().heapBuffer(originalLength, originalLength);
/*     */ 
/*     */               
/* 195 */               if (uncompressed.hasArray()) {
/* 196 */                 outputArray = uncompressed.array();
/* 197 */                 outPos = uncompressed.arrayOffset() + uncompressed.writerIndex();
/*     */               } else {
/* 199 */                 outputArray = new byte[originalLength];
/* 200 */                 outPos = 0;
/*     */               } 
/*     */               
/* 203 */               boolean success = false;
/*     */               try {
/* 205 */                 this.decoder.decodeChunk(inputArray, inPos, outputArray, outPos, outPos + originalLength);
/* 206 */                 if (uncompressed.hasArray()) {
/* 207 */                   uncompressed.writerIndex(uncompressed.writerIndex() + originalLength);
/*     */                 } else {
/* 209 */                   uncompressed.writeBytes(outputArray);
/*     */                 } 
/* 211 */                 out.add(uncompressed);
/* 212 */                 in.skipBytes(chunkLength);
/* 213 */                 success = true;
/*     */               } finally {
/* 215 */                 if (!success) {
/* 216 */                   uncompressed.release();
/*     */                 }
/*     */               } 
/*     */               
/* 220 */               if (!in.hasArray()) {
/* 221 */                 this.recycler.releaseInputBuffer(inputArray);
/*     */               }
/* 223 */             } else if (chunkLength > 0) {
/* 224 */               out.add(in.readRetainedSlice(chunkLength));
/*     */             } 
/*     */             
/* 227 */             this.currentState = State.INIT_BLOCK;
/*     */           }  return;
/*     */         case CORRUPTED:
/* 230 */           in.skipBytes(in.readableBytes());
/*     */           return;
/*     */       } 
/* 233 */       throw new IllegalStateException();
/*     */     }
/* 235 */     catch (Exception e) {
/* 236 */       this.currentState = State.CORRUPTED;
/* 237 */       this.decoder = null;
/* 238 */       this.recycler = null;
/* 239 */       throw e;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\LzfDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */