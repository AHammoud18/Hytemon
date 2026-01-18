/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.jcraft.jzlib.Inflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.internal.ObjectUtil;
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
/*     */ public class JZlibDecoder
/*     */   extends ZlibDecoder
/*     */ {
/*  29 */   private final Inflater z = new Inflater();
/*     */ 
/*     */   
/*     */   private byte[] dictionary;
/*     */ 
/*     */   
/*     */   private boolean needsRead;
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JZlibDecoder() {
/*  42 */     this(ZlibWrapper.ZLIB, 0);
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
/*     */   public JZlibDecoder(int maxAllocation) {
/*  56 */     this(ZlibWrapper.ZLIB, maxAllocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JZlibDecoder(ZlibWrapper wrapper) {
/*  67 */     this(wrapper, 0);
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
/*     */   public JZlibDecoder(ZlibWrapper wrapper, int maxAllocation) {
/*  80 */     super(maxAllocation);
/*     */     
/*  82 */     ObjectUtil.checkNotNull(wrapper, "wrapper");
/*     */     
/*  84 */     int resultCode = this.z.init(ZlibUtil.convertWrapperType(wrapper));
/*  85 */     if (resultCode != 0) {
/*  86 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
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
/*     */   
/*     */   @Deprecated
/*     */   public JZlibDecoder(byte[] dictionary) {
/* 100 */     this(dictionary, 0);
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
/*     */   public JZlibDecoder(byte[] dictionary, int maxAllocation) {
/* 115 */     super(maxAllocation);
/* 116 */     this.dictionary = (byte[])ObjectUtil.checkNotNull(dictionary, "dictionary");
/*     */     
/* 118 */     int resultCode = this.z.inflateInit(JZlib.W_ZLIB);
/* 119 */     if (resultCode != 0) {
/* 120 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 130 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 135 */     this.needsRead = true;
/* 136 */     if (this.finished) {
/*     */       
/* 138 */       in.skipBytes(in.readableBytes());
/*     */       
/*     */       return;
/*     */     } 
/* 142 */     int inputLength = in.readableBytes();
/* 143 */     if (inputLength == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 149 */       this.z.avail_in = inputLength;
/* 150 */       if (in.hasArray()) {
/* 151 */         this.z.next_in = in.array();
/* 152 */         this.z.next_in_index = in.arrayOffset() + in.readerIndex();
/*     */       } else {
/* 154 */         byte[] array = new byte[inputLength];
/* 155 */         in.getBytes(in.readerIndex(), array);
/* 156 */         this.z.next_in = array;
/* 157 */         this.z.next_in_index = 0;
/*     */       } 
/* 159 */       int oldNextInIndex = this.z.next_in_index;
/*     */ 
/*     */       
/* 162 */       ByteBuf decompressed = prepareDecompressBuffer(ctx, (ByteBuf)null, inputLength << 1);
/*     */       
/*     */       try {
/*     */         while (true) {
/* 166 */           decompressed = prepareDecompressBuffer(ctx, decompressed, this.z.avail_in << 1);
/* 167 */           this.z.avail_out = decompressed.writableBytes();
/* 168 */           this.z.next_out = decompressed.array();
/* 169 */           this.z.next_out_index = decompressed.arrayOffset() + decompressed.writerIndex();
/* 170 */           int oldNextOutIndex = this.z.next_out_index;
/*     */ 
/*     */           
/* 173 */           int resultCode = this.z.inflate(2);
/* 174 */           int outputLength = this.z.next_out_index - oldNextOutIndex;
/* 175 */           if (outputLength > 0) {
/* 176 */             decompressed.writerIndex(decompressed.writerIndex() + outputLength);
/* 177 */             if (this.maxAllocation == 0) {
/*     */ 
/*     */               
/* 180 */               ByteBuf buffer = decompressed;
/* 181 */               decompressed = null;
/* 182 */               this.needsRead = false;
/* 183 */               ctx.fireChannelRead(buffer);
/*     */             } 
/*     */           } 
/*     */           
/* 187 */           switch (resultCode) {
/*     */             case 2:
/* 189 */               if (this.dictionary == null) {
/* 190 */                 ZlibUtil.fail(this.z, "decompression failure", resultCode); continue;
/*     */               } 
/* 192 */               resultCode = this.z.inflateSetDictionary(this.dictionary, this.dictionary.length);
/* 193 */               if (resultCode != 0) {
/* 194 */                 ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode);
/*     */               }
/*     */               continue;
/*     */             
/*     */             case 1:
/* 199 */               this.finished = true;
/* 200 */               this.z.inflateEnd();
/*     */               break;
/*     */             case 0:
/*     */               continue;
/*     */             case -5:
/* 205 */               if (this.z.avail_in <= 0) {
/*     */                 break;
/*     */               }
/*     */               continue;
/*     */           } 
/* 210 */           ZlibUtil.fail(this.z, "decompression failure", resultCode);
/*     */         } 
/*     */       } finally {
/*     */         
/* 214 */         in.skipBytes(this.z.next_in_index - oldNextInIndex);
/* 215 */         if (decompressed != null) {
/* 216 */           if (decompressed.isReadable()) {
/* 217 */             this.needsRead = false;
/* 218 */             ctx.fireChannelRead(decompressed);
/*     */           } else {
/* 220 */             decompressed.release();
/*     */           }
/*     */         
/*     */         }
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 229 */       this.z.next_in = null;
/* 230 */       this.z.next_out = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 237 */     discardSomeReadBytes();
/*     */     
/* 239 */     if (this.needsRead && !ctx.channel().config().isAutoRead()) {
/* 240 */       ctx.read();
/*     */     }
/* 242 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decompressionBufferExhausted(ByteBuf buffer) {
/* 247 */     this.finished = true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\JZlibDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */