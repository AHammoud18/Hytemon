/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.IOException;
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
/*     */ public class MemoryFileUpload
/*     */   extends AbstractMemoryHttpData
/*     */   implements FileUpload
/*     */ {
/*     */   private String filename;
/*     */   private String contentType;
/*     */   private String contentTransferEncoding;
/*     */   
/*     */   public MemoryFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
/*  42 */     super(name, charset, size);
/*  43 */     setFilename(filename);
/*  44 */     setContentType(contentType);
/*  45 */     setContentTransferEncoding(contentTransferEncoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/*  50 */     return InterfaceHttpData.HttpDataType.FileUpload;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFilename() {
/*  55 */     return this.filename;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFilename(String filename) {
/*  60 */     this.filename = (String)ObjectUtil.checkNotNull(filename, "filename");
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  65 */     return FileUploadUtil.hashCode(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  70 */     return (o instanceof FileUpload && FileUploadUtil.equals(this, (FileUpload)o));
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/*  75 */     if (!(o instanceof FileUpload)) {
/*  76 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + o
/*  77 */           .getHttpDataType());
/*     */     }
/*  79 */     return compareTo((FileUpload)o);
/*     */   }
/*     */   
/*     */   public int compareTo(FileUpload o) {
/*  83 */     return FileUploadUtil.compareTo(this, o);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentType(String contentType) {
/*  88 */     this.contentType = (String)ObjectUtil.checkNotNull(contentType, "contentType");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentType() {
/*  93 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentTransferEncoding() {
/*  98 */     return this.contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTransferEncoding(String contentTransferEncoding) {
/* 103 */     this.contentTransferEncoding = contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 108 */     return HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + 
/* 109 */       getName() + "\"; " + HttpHeaderValues.FILENAME + "=\"" + this.filename + "\"\r\n" + HttpHeaderNames.CONTENT_TYPE + ": " + this.contentType + (
/*     */ 
/*     */       
/* 112 */       (getCharset() != null) ? ("; " + HttpHeaderValues.CHARSET + '=' + getCharset().name() + "\r\n") : "\r\n") + HttpHeaderNames.CONTENT_LENGTH + ": " + 
/* 113 */       length() + "\r\nCompleted: " + 
/* 114 */       isCompleted() + "\r\nIsInMemory: " + 
/* 115 */       isInMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload copy() {
/* 120 */     ByteBuf content = content();
/* 121 */     return replace((content != null) ? content.copy() : content);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload duplicate() {
/* 126 */     ByteBuf content = content();
/* 127 */     return replace((content != null) ? content.duplicate() : content);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retainedDuplicate() {
/* 132 */     ByteBuf content = content();
/* 133 */     if (content != null) {
/* 134 */       content = content.retainedDuplicate();
/* 135 */       boolean success = false;
/*     */       try {
/* 137 */         FileUpload duplicate = replace(content);
/* 138 */         success = true;
/* 139 */         return duplicate;
/*     */       } finally {
/* 141 */         if (!success) {
/* 142 */           content.release();
/*     */         }
/*     */       } 
/*     */     } 
/* 146 */     return replace((ByteBuf)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload replace(ByteBuf content) {
/* 153 */     MemoryFileUpload upload = new MemoryFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
/* 154 */     if (content != null) {
/*     */       try {
/* 156 */         upload.setContent(content);
/* 157 */       } catch (IOException e) {
/* 158 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 161 */     upload.setCompleted(isCompleted());
/* 162 */     return upload;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retain() {
/* 167 */     super.retain();
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retain(int increment) {
/* 173 */     super.retain(increment);
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload touch() {
/* 179 */     super.touch();
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload touch(Object hint) {
/* 185 */     super.touch(hint);
/* 186 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\MemoryFileUpload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */