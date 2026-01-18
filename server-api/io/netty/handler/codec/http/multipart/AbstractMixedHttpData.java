/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.util.AbstractReferenceCounted;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ abstract class AbstractMixedHttpData<D extends HttpData>
/*     */   extends AbstractReferenceCounted
/*     */   implements HttpData
/*     */ {
/*     */   final String baseDir;
/*     */   final boolean deleteOnExit;
/*     */   D wrapped;
/*     */   private final long limitSize;
/*     */   
/*     */   AbstractMixedHttpData(long limitSize, String baseDir, boolean deleteOnExit, D initial) {
/*  34 */     this.limitSize = limitSize;
/*  35 */     this.wrapped = initial;
/*  36 */     this.baseDir = baseDir;
/*  37 */     this.deleteOnExit = deleteOnExit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxSize() {
/*  44 */     return this.wrapped.getMaxSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxSize(long maxSize) {
/*  49 */     this.wrapped.setMaxSize(maxSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*  54 */     return this.wrapped.content();
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkSize(long newSize) throws IOException {
/*  59 */     this.wrapped.checkSize(newSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public long definedLength() {
/*  64 */     return this.wrapped.definedLength();
/*     */   }
/*     */ 
/*     */   
/*     */   public Charset getCharset() {
/*  69 */     return this.wrapped.getCharset();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  74 */     return this.wrapped.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/*  79 */     if (this.wrapped instanceof AbstractMemoryHttpData) {
/*     */       try {
/*  81 */         checkSize(this.wrapped.length() + buffer.readableBytes());
/*  82 */         if (this.wrapped.length() + buffer.readableBytes() > this.limitSize) {
/*  83 */           D diskData = makeDiskData();
/*  84 */           ByteBuf data = ((AbstractMemoryHttpData)this.wrapped).getByteBuf();
/*  85 */           if (data != null && data.isReadable()) {
/*  86 */             diskData.addContent(data.retain(), false);
/*     */           }
/*  88 */           this.wrapped.release();
/*  89 */           this.wrapped = diskData;
/*     */         } 
/*  91 */       } catch (IOException e) {
/*  92 */         buffer.release();
/*  93 */         throw e;
/*     */       } 
/*     */     }
/*  96 */     this.wrapped.addContent(buffer, last);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/* 101 */     delete();
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/* 106 */     this.wrapped.delete();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] get() throws IOException {
/* 111 */     return this.wrapped.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getByteBuf() throws IOException {
/* 116 */     return this.wrapped.getByteBuf();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() throws IOException {
/* 121 */     return this.wrapped.getString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(Charset encoding) throws IOException {
/* 126 */     return this.wrapped.getString(encoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInMemory() {
/* 131 */     return this.wrapped.isInMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() {
/* 136 */     return this.wrapped.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renameTo(File dest) throws IOException {
/* 141 */     return this.wrapped.renameTo(dest);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCharset(Charset charset) {
/* 146 */     this.wrapped.setCharset(charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(ByteBuf buffer) throws IOException {
/*     */     try {
/* 152 */       checkSize(buffer.readableBytes());
/* 153 */     } catch (IOException e) {
/* 154 */       buffer.release();
/* 155 */       throw e;
/*     */     } 
/* 157 */     if (buffer.readableBytes() > this.limitSize && 
/* 158 */       this.wrapped instanceof AbstractMemoryHttpData) {
/*     */       
/* 160 */       this.wrapped.release();
/* 161 */       this.wrapped = makeDiskData();
/*     */     } 
/*     */     
/* 164 */     this.wrapped.setContent(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(File file) throws IOException {
/* 169 */     checkSize(file.length());
/* 170 */     if (file.length() > this.limitSize && 
/* 171 */       this.wrapped instanceof AbstractMemoryHttpData) {
/*     */       
/* 173 */       this.wrapped.release();
/* 174 */       this.wrapped = makeDiskData();
/*     */     } 
/*     */     
/* 177 */     this.wrapped.setContent(file);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(InputStream inputStream) throws IOException {
/* 182 */     if (this.wrapped instanceof AbstractMemoryHttpData) {
/*     */       
/* 184 */       this.wrapped.release();
/* 185 */       this.wrapped = makeDiskData();
/*     */     } 
/* 187 */     this.wrapped.setContent(inputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompleted() {
/* 192 */     return this.wrapped.isCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/* 197 */     return this.wrapped.getHttpDataType();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 202 */     return this.wrapped.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 207 */     return this.wrapped.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/* 212 */     return this.wrapped.compareTo(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 217 */     return "Mixed: " + this.wrapped;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getChunk(int length) throws IOException {
/* 222 */     return this.wrapped.getChunk(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile() throws IOException {
/* 227 */     return this.wrapped.getFile();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D copy() {
/* 233 */     return (D)this.wrapped.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D duplicate() {
/* 239 */     return (D)this.wrapped.duplicate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D retainedDuplicate() {
/* 245 */     return (D)this.wrapped.retainedDuplicate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D replace(ByteBuf content) {
/* 251 */     return (D)this.wrapped.replace(content);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D touch() {
/* 257 */     this.wrapped.touch();
/* 258 */     return (D)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D touch(Object hint) {
/* 264 */     this.wrapped.touch(hint);
/* 265 */     return (D)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D retain() {
/* 271 */     return (D)super.retain();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D retain(int increment) {
/* 277 */     return (D)super.retain(increment);
/*     */   }
/*     */   
/*     */   abstract D makeDiskData();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\AbstractMixedHttpData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */