/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMemoryHttpData
/*     */   extends AbstractHttpData
/*     */ {
/*     */   private ByteBuf byteBuf;
/*     */   private int chunkPosition;
/*     */   
/*     */   protected AbstractMemoryHttpData(String name, Charset charset, long size) {
/*  45 */     super(name, charset, size);
/*  46 */     this.byteBuf = Unpooled.EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(ByteBuf buffer) throws IOException {
/*  51 */     ObjectUtil.checkNotNull(buffer, "buffer");
/*  52 */     long localsize = buffer.readableBytes();
/*     */     try {
/*  54 */       checkSize(localsize);
/*  55 */     } catch (IOException e) {
/*  56 */       buffer.release();
/*  57 */       throw e;
/*     */     } 
/*  59 */     if (this.definedSize > 0L && this.definedSize < localsize) {
/*  60 */       buffer.release();
/*  61 */       throw new IOException("Out of size: " + localsize + " > " + this.definedSize);
/*     */     } 
/*     */     
/*  64 */     if (this.byteBuf != null) {
/*  65 */       this.byteBuf.release();
/*     */     }
/*  67 */     this.byteBuf = buffer;
/*  68 */     this.size = localsize;
/*  69 */     setCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(InputStream inputStream) throws IOException {
/*  74 */     ObjectUtil.checkNotNull(inputStream, "inputStream");
/*     */     
/*  76 */     byte[] bytes = new byte[16384];
/*  77 */     ByteBuf buffer = Unpooled.buffer();
/*  78 */     int written = 0;
/*     */     try {
/*  80 */       int read = inputStream.read(bytes);
/*  81 */       while (read > 0) {
/*  82 */         buffer.writeBytes(bytes, 0, read);
/*  83 */         written += read;
/*  84 */         checkSize(written);
/*  85 */         read = inputStream.read(bytes);
/*     */       } 
/*  87 */     } catch (IOException e) {
/*  88 */       buffer.release();
/*  89 */       throw e;
/*     */     } 
/*  91 */     this.size = written;
/*  92 */     if (this.definedSize > 0L && this.definedSize < this.size) {
/*  93 */       buffer.release();
/*  94 */       throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */     } 
/*  96 */     if (this.byteBuf != null) {
/*  97 */       this.byteBuf.release();
/*     */     }
/*  99 */     this.byteBuf = buffer;
/* 100 */     setCompleted();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/* 106 */     if (buffer != null) {
/* 107 */       long localsize = buffer.readableBytes();
/*     */       try {
/* 109 */         checkSize(this.size + localsize);
/* 110 */       } catch (IOException e) {
/* 111 */         buffer.release();
/* 112 */         throw e;
/*     */       } 
/* 114 */       if (this.definedSize > 0L && this.definedSize < this.size + localsize) {
/* 115 */         buffer.release();
/* 116 */         throw new IOException("Out of size: " + (this.size + localsize) + " > " + this.definedSize);
/*     */       } 
/*     */       
/* 119 */       this.size += localsize;
/* 120 */       if (this.byteBuf == null) {
/* 121 */         this.byteBuf = buffer;
/* 122 */       } else if (localsize == 0L) {
/*     */         
/* 124 */         buffer.release();
/* 125 */       } else if (this.byteBuf.readableBytes() == 0) {
/*     */         
/* 127 */         this.byteBuf.release();
/* 128 */         this.byteBuf = buffer;
/* 129 */       } else if (this.byteBuf instanceof CompositeByteBuf) {
/* 130 */         CompositeByteBuf cbb = (CompositeByteBuf)this.byteBuf;
/* 131 */         cbb.addComponent(true, buffer);
/*     */       } else {
/* 133 */         CompositeByteBuf cbb = Unpooled.compositeBuffer(2147483647);
/* 134 */         cbb.addComponents(true, new ByteBuf[] { this.byteBuf, buffer });
/* 135 */         this.byteBuf = (ByteBuf)cbb;
/*     */       } 
/*     */     } 
/* 138 */     if (last) {
/* 139 */       setCompleted();
/*     */     } else {
/* 141 */       ObjectUtil.checkNotNull(buffer, "buffer");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setContent(File file) throws IOException {
/*     */     ByteBuffer byteBuffer;
/* 147 */     ObjectUtil.checkNotNull(file, "file");
/*     */     
/* 149 */     long newsize = file.length();
/* 150 */     if (newsize > 2147483647L) {
/* 151 */       throw new IllegalArgumentException("File too big to be loaded in memory");
/*     */     }
/* 153 */     checkSize(newsize);
/* 154 */     RandomAccessFile accessFile = new RandomAccessFile(file, "r");
/*     */     
/*     */     try {
/* 157 */       FileChannel fileChannel = accessFile.getChannel();
/*     */       try {
/* 159 */         byte[] array = new byte[(int)newsize];
/* 160 */         byteBuffer = ByteBuffer.wrap(array);
/* 161 */         int read = 0;
/* 162 */         while (read < newsize) {
/* 163 */           read += fileChannel.read(byteBuffer);
/*     */         }
/*     */       } finally {
/* 166 */         fileChannel.close();
/*     */       } 
/*     */     } finally {
/* 169 */       accessFile.close();
/*     */     } 
/* 171 */     byteBuffer.flip();
/* 172 */     if (this.byteBuf != null) {
/* 173 */       this.byteBuf.release();
/*     */     }
/* 175 */     this.byteBuf = Unpooled.wrappedBuffer(2147483647, new ByteBuffer[] { byteBuffer });
/* 176 */     this.size = newsize;
/* 177 */     setCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/* 182 */     if (this.byteBuf != null) {
/* 183 */       this.byteBuf.release();
/* 184 */       this.byteBuf = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] get() {
/* 190 */     if (this.byteBuf == null) {
/* 191 */       return Unpooled.EMPTY_BUFFER.array();
/*     */     }
/* 193 */     byte[] array = new byte[this.byteBuf.readableBytes()];
/* 194 */     this.byteBuf.getBytes(this.byteBuf.readerIndex(), array);
/* 195 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() {
/* 200 */     return getString(HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(Charset encoding) {
/* 205 */     if (this.byteBuf == null) {
/* 206 */       return "";
/*     */     }
/* 208 */     if (encoding == null) {
/* 209 */       encoding = HttpConstants.DEFAULT_CHARSET;
/*     */     }
/* 211 */     return this.byteBuf.toString(encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf getByteBuf() {
/* 221 */     return this.byteBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getChunk(int length) throws IOException {
/* 226 */     if (this.byteBuf == null || length == 0 || this.byteBuf.readableBytes() == 0) {
/* 227 */       this.chunkPosition = 0;
/* 228 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/* 230 */     int sizeLeft = this.byteBuf.readableBytes() - this.chunkPosition;
/* 231 */     if (sizeLeft == 0) {
/* 232 */       this.chunkPosition = 0;
/* 233 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/* 235 */     int sliceLength = length;
/* 236 */     if (sizeLeft < length) {
/* 237 */       sliceLength = sizeLeft;
/*     */     }
/* 239 */     ByteBuf chunk = this.byteBuf.retainedSlice(this.chunkPosition, sliceLength);
/* 240 */     this.chunkPosition += sliceLength;
/* 241 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInMemory() {
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renameTo(File dest) throws IOException {
/* 251 */     ObjectUtil.checkNotNull(dest, "dest");
/* 252 */     if (this.byteBuf == null) {
/*     */       
/* 254 */       if (!dest.createNewFile()) {
/* 255 */         throw new IOException("file exists already: " + dest);
/*     */       }
/* 257 */       return true;
/*     */     } 
/* 259 */     int length = this.byteBuf.readableBytes();
/* 260 */     long written = 0L;
/* 261 */     RandomAccessFile accessFile = new RandomAccessFile(dest, "rw");
/*     */     try {
/* 263 */       FileChannel fileChannel = accessFile.getChannel();
/*     */       try {
/* 265 */         if (this.byteBuf.nioBufferCount() == 1) {
/* 266 */           ByteBuffer byteBuffer = this.byteBuf.nioBuffer();
/* 267 */           while (written < length) {
/* 268 */             written += fileChannel.write(byteBuffer);
/*     */           }
/*     */         } else {
/* 271 */           ByteBuffer[] byteBuffers = this.byteBuf.nioBuffers();
/* 272 */           while (written < length) {
/* 273 */             written += fileChannel.write(byteBuffers);
/*     */           }
/*     */         } 
/* 276 */         fileChannel.force(false);
/*     */       } finally {
/* 278 */         fileChannel.close();
/*     */       } 
/*     */     } finally {
/* 281 */       accessFile.close();
/*     */     } 
/* 283 */     return (written == length);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile() throws IOException {
/* 288 */     throw new IOException("Not represented by a file");
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpData touch() {
/* 293 */     return touch((Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpData touch(Object hint) {
/* 298 */     if (this.byteBuf != null) {
/* 299 */       this.byteBuf.touch(hint);
/*     */     }
/* 301 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\AbstractMemoryHttpData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */