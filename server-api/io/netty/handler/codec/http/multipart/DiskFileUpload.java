/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.File;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiskFileUpload
/*     */   extends AbstractDiskHttpData
/*     */   implements FileUpload
/*     */ {
/*     */   public static String baseDirectory;
/*     */   public static boolean deleteOnExitTemporaryFile = true;
/*     */   public static final String prefix = "FUp_";
/*     */   public static final String postfix = ".tmp";
/*     */   private final String baseDir;
/*     */   private final boolean deleteOnExit;
/*     */   private String filename;
/*     */   private String contentType;
/*     */   private String contentTransferEncoding;
/*     */   
/*     */   public DiskFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size, String baseDir, boolean deleteOnExit) {
/*  52 */     super(name, charset, size);
/*  53 */     setFilename(filename);
/*  54 */     setContentType(contentType);
/*  55 */     setContentTransferEncoding(contentTransferEncoding);
/*  56 */     this.baseDir = (baseDir == null) ? baseDirectory : baseDir;
/*  57 */     this.deleteOnExit = deleteOnExit;
/*     */   }
/*     */ 
/*     */   
/*     */   public DiskFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
/*  62 */     this(name, filename, contentType, contentTransferEncoding, charset, size, baseDirectory, deleteOnExitTemporaryFile);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/*  68 */     return InterfaceHttpData.HttpDataType.FileUpload;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFilename() {
/*  73 */     return this.filename;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFilename(String filename) {
/*  78 */     this.filename = (String)ObjectUtil.checkNotNull(filename, "filename");
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  83 */     return FileUploadUtil.hashCode(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  88 */     return (o instanceof FileUpload && FileUploadUtil.equals(this, (FileUpload)o));
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/*  93 */     if (!(o instanceof FileUpload)) {
/*  94 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + o
/*  95 */           .getHttpDataType());
/*     */     }
/*  97 */     return compareTo((FileUpload)o);
/*     */   }
/*     */   
/*     */   public int compareTo(FileUpload o) {
/* 101 */     return FileUploadUtil.compareTo(this, o);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentType(String contentType) {
/* 106 */     this.contentType = (String)ObjectUtil.checkNotNull(contentType, "contentType");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 111 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentTransferEncoding() {
/* 116 */     return this.contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTransferEncoding(String contentTransferEncoding) {
/* 121 */     this.contentTransferEncoding = contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     File file = null;
/*     */     try {
/* 128 */       file = getFile();
/* 129 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/* 133 */     return HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + 
/* 134 */       getName() + "\"; " + HttpHeaderValues.FILENAME + "=\"" + this.filename + "\"\r\n" + HttpHeaderNames.CONTENT_TYPE + ": " + this.contentType + (
/*     */ 
/*     */       
/* 137 */       (getCharset() != null) ? ("; " + HttpHeaderValues.CHARSET + '=' + getCharset().name() + "\r\n") : "\r\n") + HttpHeaderNames.CONTENT_LENGTH + ": " + 
/* 138 */       length() + "\r\nCompleted: " + 
/* 139 */       isCompleted() + "\r\nIsInMemory: " + 
/* 140 */       isInMemory() + "\r\nRealFile: " + (
/* 141 */       (file != null) ? file.getAbsolutePath() : "null") + " DeleteAfter: " + this.deleteOnExit;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean deleteOnExit() {
/* 146 */     return this.deleteOnExit;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getBaseDirectory() {
/* 151 */     return this.baseDir;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDiskFilename() {
/* 156 */     return "upload";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPostfix() {
/* 161 */     return ".tmp";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPrefix() {
/* 166 */     return "FUp_";
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload copy() {
/* 171 */     ByteBuf content = content();
/* 172 */     return replace((content != null) ? content.copy() : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload duplicate() {
/* 177 */     ByteBuf content = content();
/* 178 */     return replace((content != null) ? content.duplicate() : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retainedDuplicate() {
/* 183 */     ByteBuf content = content();
/* 184 */     if (content != null) {
/* 185 */       content = content.retainedDuplicate();
/* 186 */       boolean success = false;
/*     */       try {
/* 188 */         FileUpload duplicate = replace(content);
/* 189 */         success = true;
/* 190 */         return duplicate;
/*     */       } finally {
/* 192 */         if (!success) {
/* 193 */           content.release();
/*     */         }
/*     */       } 
/*     */     } 
/* 197 */     return replace((ByteBuf)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload replace(ByteBuf content) {
/* 204 */     DiskFileUpload upload = new DiskFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size, this.baseDir, this.deleteOnExit);
/*     */     
/* 206 */     if (content != null) {
/*     */       try {
/* 208 */         upload.setContent(content);
/* 209 */       } catch (IOException e) {
/* 210 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 213 */     upload.setCompleted(isCompleted());
/* 214 */     return upload;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retain(int increment) {
/* 219 */     super.retain(increment);
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retain() {
/* 225 */     super.retain();
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload touch() {
/* 231 */     super.touch();
/* 232 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload touch(Object hint) {
/* 237 */     super.touch(hint);
/* 238 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\DiskFileUpload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */