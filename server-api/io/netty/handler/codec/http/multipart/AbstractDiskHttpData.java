/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public abstract class AbstractDiskHttpData
/*     */   extends AbstractHttpData
/*     */ {
/*  42 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractDiskHttpData.class);
/*     */   
/*     */   private File file;
/*     */   private boolean isRenamed;
/*     */   private FileChannel fileChannel;
/*     */   
/*     */   protected AbstractDiskHttpData(String name, Charset charset, long size) {
/*  49 */     super(name, charset, size);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File tempFile() throws IOException {
/*     */     String newpostfix;
/*     */     File tmpFile;
/*  83 */     String diskFilename = getDiskFilename();
/*  84 */     if (diskFilename != null) {
/*  85 */       newpostfix = '_' + Integer.toString(diskFilename.hashCode());
/*     */     } else {
/*  87 */       newpostfix = getPostfix();
/*     */     } 
/*     */     
/*  90 */     if (getBaseDirectory() == null) {
/*     */       
/*  92 */       tmpFile = PlatformDependent.createTempFile(getPrefix(), newpostfix, null);
/*     */     } else {
/*  94 */       tmpFile = PlatformDependent.createTempFile(getPrefix(), newpostfix, new File(
/*  95 */             getBaseDirectory()));
/*     */     } 
/*  97 */     if (deleteOnExit())
/*     */     {
/*  99 */       DeleteFileOnExitHook.add(tmpFile.getPath());
/*     */     }
/* 101 */     return tmpFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(ByteBuf buffer) throws IOException {
/* 106 */     ObjectUtil.checkNotNull(buffer, "buffer");
/*     */     try {
/* 108 */       this.size = buffer.readableBytes();
/* 109 */       checkSize(this.size);
/* 110 */       if (this.definedSize > 0L && this.definedSize < this.size) {
/* 111 */         throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */       }
/* 113 */       if (this.file == null) {
/* 114 */         this.file = tempFile();
/*     */       }
/* 116 */       if (buffer.readableBytes() == 0) {
/*     */         
/* 118 */         if (!this.file.createNewFile()) {
/* 119 */           if (this.file.length() == 0L) {
/*     */             return;
/*     */           }
/* 122 */           if (!this.file.delete() || !this.file.createNewFile()) {
/* 123 */             throw new IOException("file exists already: " + this.file);
/*     */           }
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 129 */       RandomAccessFile accessFile = new RandomAccessFile(this.file, "rw");
/*     */       try {
/* 131 */         accessFile.setLength(0L);
/* 132 */         FileChannel localfileChannel = accessFile.getChannel();
/* 133 */         ByteBuffer byteBuffer = buffer.nioBuffer();
/* 134 */         int written = 0;
/* 135 */         while (written < this.size) {
/* 136 */           written += localfileChannel.write(byteBuffer);
/*     */         }
/* 138 */         buffer.readerIndex(buffer.readerIndex() + written);
/* 139 */         localfileChannel.force(false);
/*     */       } finally {
/* 141 */         accessFile.close();
/*     */       } 
/* 143 */       setCompleted();
/*     */     }
/*     */     finally {
/*     */       
/* 147 */       buffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/* 154 */     if (buffer != null) {
/*     */       try {
/* 156 */         int localsize = buffer.readableBytes();
/* 157 */         checkSize(this.size + localsize);
/* 158 */         if (this.definedSize > 0L && this.definedSize < this.size + localsize) {
/* 159 */           throw new IOException("Out of size: " + (this.size + localsize) + " > " + this.definedSize);
/*     */         }
/*     */         
/* 162 */         if (this.file == null) {
/* 163 */           this.file = tempFile();
/*     */         }
/* 165 */         if (this.fileChannel == null) {
/* 166 */           RandomAccessFile accessFile = new RandomAccessFile(this.file, "rw");
/* 167 */           this.fileChannel = accessFile.getChannel();
/*     */         } 
/* 169 */         int remaining = localsize;
/* 170 */         long position = this.fileChannel.position();
/* 171 */         int index = buffer.readerIndex();
/* 172 */         while (remaining > 0) {
/* 173 */           int written = buffer.getBytes(index, this.fileChannel, position, remaining);
/* 174 */           if (written < 0) {
/*     */             break;
/*     */           }
/* 177 */           remaining -= written;
/* 178 */           position += written;
/* 179 */           index += written;
/*     */         } 
/* 181 */         this.fileChannel.position(position);
/* 182 */         buffer.readerIndex(index);
/* 183 */         this.size += (localsize - remaining);
/*     */       }
/*     */       finally {
/*     */         
/* 187 */         buffer.release();
/*     */       } 
/*     */     }
/* 190 */     if (last) {
/* 191 */       if (this.file == null) {
/* 192 */         this.file = tempFile();
/*     */       }
/* 194 */       if (this.fileChannel == null) {
/* 195 */         RandomAccessFile accessFile = new RandomAccessFile(this.file, "rw");
/* 196 */         this.fileChannel = accessFile.getChannel();
/*     */       } 
/*     */       try {
/* 199 */         this.fileChannel.force(false);
/*     */       } finally {
/* 201 */         this.fileChannel.close();
/*     */       } 
/* 203 */       this.fileChannel = null;
/* 204 */       setCompleted();
/*     */     } else {
/* 206 */       ObjectUtil.checkNotNull(buffer, "buffer");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(File file) throws IOException {
/* 212 */     long size = file.length();
/* 213 */     checkSize(size);
/* 214 */     this.size = size;
/* 215 */     if (this.file != null) {
/* 216 */       delete();
/*     */     }
/* 218 */     this.file = file;
/* 219 */     this.isRenamed = true;
/* 220 */     setCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(InputStream inputStream) throws IOException {
/* 225 */     ObjectUtil.checkNotNull(inputStream, "inputStream");
/* 226 */     if (this.file != null) {
/* 227 */       delete();
/*     */     }
/* 229 */     this.file = tempFile();
/* 230 */     RandomAccessFile accessFile = new RandomAccessFile(this.file, "rw");
/* 231 */     long written = 0L;
/*     */     try {
/* 233 */       accessFile.setLength(0L);
/* 234 */       FileChannel localfileChannel = accessFile.getChannel();
/* 235 */       byte[] bytes = new byte[16384];
/* 236 */       ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
/* 237 */       int read = inputStream.read(bytes);
/* 238 */       while (read > 0) {
/* 239 */         byteBuffer.position(read).flip();
/* 240 */         written += localfileChannel.write(byteBuffer);
/* 241 */         checkSize(written);
/* 242 */         byteBuffer.clear();
/* 243 */         read = inputStream.read(bytes);
/*     */       } 
/* 245 */       localfileChannel.force(false);
/*     */     } finally {
/* 247 */       accessFile.close();
/*     */     } 
/* 249 */     this.size = written;
/* 250 */     if (this.definedSize > 0L && this.definedSize < this.size) {
/* 251 */       if (!this.file.delete()) {
/* 252 */         logger.warn("Failed to delete: {}", this.file);
/*     */       }
/* 254 */       this.file = null;
/* 255 */       throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */     } 
/* 257 */     this.isRenamed = true;
/* 258 */     setCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/* 263 */     if (this.fileChannel != null) {
/*     */       try {
/* 265 */         this.fileChannel.force(false);
/* 266 */       } catch (IOException e) {
/* 267 */         logger.warn("Failed to force.", e);
/*     */       } finally {
/*     */         try {
/* 270 */           this.fileChannel.close();
/* 271 */         } catch (IOException e) {
/* 272 */           logger.warn("Failed to close a file.", e);
/*     */         } 
/*     */       } 
/* 275 */       this.fileChannel = null;
/*     */     } 
/* 277 */     if (!this.isRenamed) {
/* 278 */       String filePath = null;
/*     */       
/* 280 */       if (this.file != null && this.file.exists()) {
/* 281 */         filePath = this.file.getPath();
/* 282 */         if (!this.file.delete()) {
/* 283 */           filePath = null;
/* 284 */           logger.warn("Failed to delete: {}", this.file);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 289 */       if (deleteOnExit() && filePath != null) {
/* 290 */         DeleteFileOnExitHook.remove(filePath);
/*     */       }
/* 292 */       this.file = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] get() throws IOException {
/* 298 */     if (this.file == null) {
/* 299 */       return EmptyArrays.EMPTY_BYTES;
/*     */     }
/* 301 */     return readFrom(this.file);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getByteBuf() throws IOException {
/* 306 */     if (this.file == null) {
/* 307 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 309 */     byte[] array = readFrom(this.file);
/* 310 */     return Unpooled.wrappedBuffer(array);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getChunk(int length) throws IOException {
/* 315 */     if (this.file == null || length == 0) {
/* 316 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 318 */     if (this.fileChannel == null) {
/* 319 */       RandomAccessFile accessFile = new RandomAccessFile(this.file, "r");
/* 320 */       this.fileChannel = accessFile.getChannel();
/*     */     } 
/* 322 */     int read = 0;
/* 323 */     ByteBuffer byteBuffer = ByteBuffer.allocate(length);
/*     */     try {
/* 325 */       while (read < length) {
/* 326 */         int readnow = this.fileChannel.read(byteBuffer);
/* 327 */         if (readnow == -1) {
/* 328 */           this.fileChannel.close();
/* 329 */           this.fileChannel = null;
/*     */           break;
/*     */         } 
/* 332 */         read += readnow;
/*     */       } 
/* 334 */     } catch (IOException e) {
/* 335 */       this.fileChannel.close();
/* 336 */       this.fileChannel = null;
/* 337 */       throw e;
/*     */     } 
/* 339 */     if (read == 0) {
/* 340 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 342 */     byteBuffer.flip();
/* 343 */     ByteBuf buffer = Unpooled.wrappedBuffer(byteBuffer);
/* 344 */     buffer.readerIndex(0);
/* 345 */     buffer.writerIndex(read);
/* 346 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() throws IOException {
/* 351 */     return getString(HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(Charset encoding) throws IOException {
/* 356 */     if (this.file == null) {
/* 357 */       return "";
/*     */     }
/* 359 */     if (encoding == null) {
/* 360 */       byte[] arrayOfByte = readFrom(this.file);
/* 361 */       return new String(arrayOfByte, HttpConstants.DEFAULT_CHARSET);
/*     */     } 
/* 363 */     byte[] array = readFrom(this.file);
/* 364 */     return new String(array, encoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInMemory() {
/* 369 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renameTo(File dest) throws IOException {
/* 374 */     ObjectUtil.checkNotNull(dest, "dest");
/* 375 */     if (this.file == null) {
/* 376 */       throw new IOException("No file defined so cannot be renamed");
/*     */     }
/* 378 */     if (!this.file.renameTo(dest)) {
/*     */       
/* 380 */       IOException exception = null;
/* 381 */       RandomAccessFile inputAccessFile = null;
/* 382 */       RandomAccessFile outputAccessFile = null;
/* 383 */       long chunkSize = 8196L;
/* 384 */       long position = 0L;
/*     */       try {
/* 386 */         inputAccessFile = new RandomAccessFile(this.file, "r");
/* 387 */         outputAccessFile = new RandomAccessFile(dest, "rw");
/* 388 */         FileChannel in = inputAccessFile.getChannel();
/* 389 */         FileChannel out = outputAccessFile.getChannel();
/* 390 */         while (position < this.size) {
/* 391 */           if (chunkSize < this.size - position) {
/* 392 */             chunkSize = this.size - position;
/*     */           }
/* 394 */           position += in.transferTo(position, chunkSize, out);
/*     */         } 
/* 396 */       } catch (IOException e) {
/* 397 */         exception = e;
/*     */       } finally {
/* 399 */         if (inputAccessFile != null) {
/*     */           try {
/* 401 */             inputAccessFile.close();
/* 402 */           } catch (IOException e) {
/* 403 */             if (exception == null) {
/* 404 */               exception = e;
/*     */             } else {
/* 406 */               logger.warn("Multiple exceptions detected, the following will be suppressed {}", e);
/*     */             } 
/*     */           } 
/*     */         }
/* 410 */         if (outputAccessFile != null) {
/*     */           try {
/* 412 */             outputAccessFile.close();
/* 413 */           } catch (IOException e) {
/* 414 */             if (exception == null) {
/* 415 */               exception = e;
/*     */             } else {
/* 417 */               logger.warn("Multiple exceptions detected, the following will be suppressed {}", e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 422 */       if (exception != null) {
/* 423 */         throw exception;
/*     */       }
/* 425 */       if (position == this.size) {
/* 426 */         if (!this.file.delete()) {
/* 427 */           logger.warn("Failed to delete: {}", this.file);
/*     */         }
/* 429 */         this.file = dest;
/* 430 */         this.isRenamed = true;
/* 431 */         return true;
/*     */       } 
/* 433 */       if (!dest.delete()) {
/* 434 */         logger.warn("Failed to delete: {}", dest);
/*     */       }
/* 436 */       return false;
/*     */     } 
/*     */     
/* 439 */     this.file = dest;
/* 440 */     this.isRenamed = true;
/* 441 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] readFrom(File src) throws IOException {
/* 450 */     long srcsize = src.length();
/* 451 */     if (srcsize > 2147483647L) {
/* 452 */       throw new IllegalArgumentException("File too big to be loaded in memory");
/*     */     }
/*     */     
/* 455 */     RandomAccessFile accessFile = new RandomAccessFile(src, "r");
/* 456 */     byte[] array = new byte[(int)srcsize];
/*     */     try {
/* 458 */       FileChannel fileChannel = accessFile.getChannel();
/* 459 */       ByteBuffer byteBuffer = ByteBuffer.wrap(array);
/* 460 */       int read = 0;
/* 461 */       while (read < srcsize) {
/* 462 */         read += fileChannel.read(byteBuffer);
/*     */       }
/*     */     } finally {
/* 465 */       accessFile.close();
/*     */     } 
/* 467 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile() throws IOException {
/* 472 */     return this.file;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpData touch() {
/* 477 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpData touch(Object hint) {
/* 482 */     return this;
/*     */   }
/*     */   
/*     */   protected abstract String getDiskFilename();
/*     */   
/*     */   protected abstract String getPrefix();
/*     */   
/*     */   protected abstract String getBaseDirectory();
/*     */   
/*     */   protected abstract String getPostfix();
/*     */   
/*     */   protected abstract boolean deleteOnExit();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\AbstractDiskHttpData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */