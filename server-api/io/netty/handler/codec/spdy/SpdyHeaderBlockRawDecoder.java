/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdyHeaderBlockRawDecoder
/*     */   extends SpdyHeaderBlockDecoder
/*     */ {
/*     */   private static final int LENGTH_FIELD_SIZE = 4;
/*     */   private final int maxHeaderSize;
/*     */   private State state;
/*     */   private ByteBuf cumulation;
/*     */   private int headerSize;
/*     */   private int numHeaders;
/*     */   private int length;
/*     */   private String name;
/*     */   
/*     */   private enum State
/*     */   {
/*  42 */     READ_NUM_HEADERS,
/*  43 */     READ_NAME_LENGTH,
/*  44 */     READ_NAME,
/*  45 */     SKIP_NAME,
/*  46 */     READ_VALUE_LENGTH,
/*  47 */     READ_VALUE,
/*  48 */     SKIP_VALUE,
/*  49 */     END_HEADER_BLOCK,
/*  50 */     ERROR;
/*     */   }
/*     */   
/*     */   public SpdyHeaderBlockRawDecoder(SpdyVersion spdyVersion, int maxHeaderSize) {
/*  54 */     ObjectUtil.checkNotNull(spdyVersion, "spdyVersion");
/*  55 */     this.maxHeaderSize = maxHeaderSize;
/*  56 */     this.state = State.READ_NUM_HEADERS;
/*     */   }
/*     */   
/*     */   private static int readLengthField(ByteBuf buffer) {
/*  60 */     int length = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
/*  61 */     buffer.skipBytes(4);
/*  62 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   void decode(ByteBufAllocator alloc, ByteBuf headerBlock, SpdyHeadersFrame frame) throws Exception {
/*  67 */     ObjectUtil.checkNotNull(headerBlock, "headerBlock");
/*  68 */     ObjectUtil.checkNotNull(frame, "frame");
/*     */     
/*  70 */     if (this.cumulation == null) {
/*  71 */       decodeHeaderBlock(headerBlock, frame);
/*  72 */       if (headerBlock.isReadable()) {
/*  73 */         this.cumulation = alloc.buffer(headerBlock.readableBytes());
/*  74 */         this.cumulation.writeBytes(headerBlock);
/*     */       } 
/*     */     } else {
/*  77 */       this.cumulation.writeBytes(headerBlock);
/*  78 */       decodeHeaderBlock(this.cumulation, frame);
/*  79 */       if (this.cumulation.isReadable()) {
/*  80 */         this.cumulation.discardReadBytes();
/*     */       } else {
/*  82 */         releaseBuffer();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decodeHeaderBlock(ByteBuf headerBlock, SpdyHeadersFrame frame) throws Exception {
/*  89 */     while (headerBlock.isReadable()) {
/*  90 */       int skipLength; byte[] nameBytes; byte[] valueBytes; int index; int offset; switch (this.state) {
/*     */         case READ_NUM_HEADERS:
/*  92 */           if (headerBlock.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/*  96 */           this.numHeaders = readLengthField(headerBlock);
/*     */           
/*  98 */           if (this.numHeaders < 0) {
/*  99 */             this.state = State.ERROR;
/* 100 */             frame.setInvalid(); continue;
/* 101 */           }  if (this.numHeaders == 0) {
/* 102 */             this.state = State.END_HEADER_BLOCK; continue;
/*     */           } 
/* 104 */           this.state = State.READ_NAME_LENGTH;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_NAME_LENGTH:
/* 109 */           if (headerBlock.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/* 113 */           this.length = readLengthField(headerBlock);
/*     */ 
/*     */           
/* 116 */           if (this.length <= 0) {
/* 117 */             this.state = State.ERROR;
/* 118 */             frame.setInvalid(); continue;
/* 119 */           }  if (this.length > this.maxHeaderSize || this.headerSize > this.maxHeaderSize - this.length) {
/* 120 */             this.headerSize = this.maxHeaderSize + 1;
/* 121 */             this.state = State.SKIP_NAME;
/* 122 */             frame.setTruncated(); continue;
/*     */           } 
/* 124 */           this.headerSize += this.length;
/* 125 */           this.state = State.READ_NAME;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_NAME:
/* 130 */           if (headerBlock.readableBytes() < this.length) {
/*     */             return;
/*     */           }
/*     */           
/* 134 */           nameBytes = new byte[this.length];
/* 135 */           headerBlock.readBytes(nameBytes);
/* 136 */           this.name = new String(nameBytes, StandardCharsets.UTF_8);
/*     */ 
/*     */           
/* 139 */           if (frame.headers().contains(this.name)) {
/* 140 */             this.state = State.ERROR;
/* 141 */             frame.setInvalid(); continue;
/*     */           } 
/* 143 */           this.state = State.READ_VALUE_LENGTH;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case SKIP_NAME:
/* 148 */           skipLength = Math.min(headerBlock.readableBytes(), this.length);
/* 149 */           headerBlock.skipBytes(skipLength);
/* 150 */           this.length -= skipLength;
/*     */           
/* 152 */           if (this.length == 0) {
/* 153 */             this.state = State.READ_VALUE_LENGTH;
/*     */           }
/*     */           continue;
/*     */         
/*     */         case READ_VALUE_LENGTH:
/* 158 */           if (headerBlock.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/* 162 */           this.length = readLengthField(headerBlock);
/*     */ 
/*     */           
/* 165 */           if (this.length < 0) {
/* 166 */             this.state = State.ERROR;
/* 167 */             frame.setInvalid(); continue;
/* 168 */           }  if (this.length == 0) {
/* 169 */             if (!frame.isTruncated())
/*     */             {
/* 171 */               frame.headers().add(this.name, "");
/*     */             }
/*     */             
/* 174 */             this.name = null;
/* 175 */             if (--this.numHeaders == 0) {
/* 176 */               this.state = State.END_HEADER_BLOCK; continue;
/*     */             } 
/* 178 */             this.state = State.READ_NAME_LENGTH;
/*     */             continue;
/*     */           } 
/* 181 */           if (this.length > this.maxHeaderSize || this.headerSize > this.maxHeaderSize - this.length) {
/* 182 */             this.headerSize = this.maxHeaderSize + 1;
/* 183 */             this.name = null;
/* 184 */             this.state = State.SKIP_VALUE;
/* 185 */             frame.setTruncated(); continue;
/*     */           } 
/* 187 */           this.headerSize += this.length;
/* 188 */           this.state = State.READ_VALUE;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_VALUE:
/* 193 */           if (headerBlock.readableBytes() < this.length) {
/*     */             return;
/*     */           }
/*     */           
/* 197 */           valueBytes = new byte[this.length];
/* 198 */           headerBlock.readBytes(valueBytes);
/*     */ 
/*     */           
/* 201 */           index = 0;
/* 202 */           offset = 0;
/*     */ 
/*     */           
/* 205 */           if (valueBytes[0] == 0) {
/* 206 */             this.state = State.ERROR;
/* 207 */             frame.setInvalid();
/*     */             
/*     */             continue;
/*     */           } 
/* 211 */           while (index < this.length) {
/* 212 */             while (index < valueBytes.length && valueBytes[index] != 0) {
/* 213 */               index++;
/*     */             }
/* 215 */             if (index < valueBytes.length)
/*     */             {
/* 217 */               if (index + 1 == valueBytes.length || valueBytes[index + 1] == 0) {
/*     */ 
/*     */ 
/*     */                 
/* 221 */                 this.state = State.ERROR;
/* 222 */                 frame.setInvalid();
/*     */                 break;
/*     */               } 
/*     */             }
/* 226 */             String value = new String(valueBytes, offset, index - offset, StandardCharsets.UTF_8);
/*     */             
/*     */             try {
/* 229 */               frame.headers().add(this.name, value);
/* 230 */             } catch (IllegalArgumentException e) {
/*     */               
/* 232 */               this.state = State.ERROR;
/* 233 */               frame.setInvalid();
/*     */               
/*     */               break;
/*     */             } 
/* 237 */             offset = ++index;
/*     */           } 
/*     */           
/* 240 */           this.name = null;
/*     */ 
/*     */           
/* 243 */           if (this.state == State.ERROR) {
/*     */             continue;
/*     */           }
/*     */           
/* 247 */           if (--this.numHeaders == 0) {
/* 248 */             this.state = State.END_HEADER_BLOCK; continue;
/*     */           } 
/* 250 */           this.state = State.READ_NAME_LENGTH;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case SKIP_VALUE:
/* 255 */           skipLength = Math.min(headerBlock.readableBytes(), this.length);
/* 256 */           headerBlock.skipBytes(skipLength);
/* 257 */           this.length -= skipLength;
/*     */           
/* 259 */           if (this.length == 0) {
/* 260 */             if (--this.numHeaders == 0) {
/* 261 */               this.state = State.END_HEADER_BLOCK; continue;
/*     */             } 
/* 263 */             this.state = State.READ_NAME_LENGTH;
/*     */           } 
/*     */           continue;
/*     */ 
/*     */         
/*     */         case END_HEADER_BLOCK:
/* 269 */           this.state = State.ERROR;
/* 270 */           frame.setInvalid();
/*     */           continue;
/*     */         
/*     */         case ERROR:
/* 274 */           headerBlock.skipBytes(headerBlock.readableBytes());
/*     */           return;
/*     */       } 
/*     */       
/* 278 */       throw new Error("Unexpected state: " + this.state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void endHeaderBlock(SpdyHeadersFrame frame) throws Exception {
/* 285 */     if (this.state != State.END_HEADER_BLOCK) {
/* 286 */       frame.setInvalid();
/*     */     }
/*     */     
/* 289 */     releaseBuffer();
/*     */ 
/*     */     
/* 292 */     this.headerSize = 0;
/* 293 */     this.name = null;
/* 294 */     this.state = State.READ_NUM_HEADERS;
/*     */   }
/*     */ 
/*     */   
/*     */   void end() {
/* 299 */     releaseBuffer();
/*     */   }
/*     */   
/*     */   private void releaseBuffer() {
/* 303 */     if (this.cumulation != null) {
/* 304 */       this.cumulation.release();
/* 305 */       this.cumulation = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockRawDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */