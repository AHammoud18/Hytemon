/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
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
/*     */ 
/*     */ 
/*     */ public class MixedFileUpload
/*     */   extends AbstractMixedHttpData<FileUpload>
/*     */   implements FileUpload
/*     */ {
/*     */   public MixedFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size, long limitSize) {
/*  30 */     this(name, filename, contentType, contentTransferEncoding, charset, size, limitSize, DiskFileUpload.baseDirectory, DiskFileUpload.deleteOnExitTemporaryFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixedFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size, long limitSize, String baseDir, boolean deleteOnExit) {
/*  37 */     super(limitSize, baseDir, deleteOnExit, 
/*  38 */         (size > limitSize) ? 
/*  39 */         new DiskFileUpload(name, filename, contentType, contentTransferEncoding, charset, size, baseDir, deleteOnExit) : 
/*     */         
/*  41 */         new MemoryFileUpload(name, filename, contentType, contentTransferEncoding, charset, size));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentTransferEncoding() {
/*  47 */     return this.wrapped.getContentTransferEncoding();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFilename() {
/*  52 */     return this.wrapped.getFilename();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTransferEncoding(String contentTransferEncoding) {
/*  57 */     this.wrapped.setContentTransferEncoding(contentTransferEncoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFilename(String filename) {
/*  62 */     this.wrapped.setFilename(filename);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentType(String contentType) {
/*  67 */     this.wrapped.setContentType(contentType);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentType() {
/*  72 */     return this.wrapped.getContentType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   FileUpload makeDiskData() {
/*  78 */     DiskFileUpload diskFileUpload = new DiskFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), definedLength(), this.baseDir, this.deleteOnExit);
/*     */     
/*  80 */     diskFileUpload.setMaxSize(getMaxSize());
/*  81 */     return diskFileUpload;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload copy() {
/*  87 */     return super.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload duplicate() {
/*  93 */     return super.duplicate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload retainedDuplicate() {
/*  99 */     return super.retainedDuplicate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload replace(ByteBuf content) {
/* 105 */     return super.replace(content);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload touch() {
/* 111 */     return super.touch();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload touch(Object hint) {
/* 117 */     return super.touch(hint);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload retain() {
/* 123 */     return super.retain();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload retain(int increment) {
/* 129 */     return super.retain(increment);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\MixedFileUpload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */