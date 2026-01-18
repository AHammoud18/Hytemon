/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.util.AbstractReferenceCounted;
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
/*     */ public abstract class AbstractHttpData
/*     */   extends AbstractReferenceCounted
/*     */   implements HttpData
/*     */ {
/*     */   private final String name;
/*     */   protected long definedSize;
/*     */   protected long size;
/*  37 */   private Charset charset = HttpConstants.DEFAULT_CHARSET;
/*     */   private boolean completed;
/*  39 */   private long maxSize = -1L;
/*     */   
/*     */   protected AbstractHttpData(String name, Charset charset, long size) {
/*  42 */     ObjectUtil.checkNotNull(name, "name");
/*     */     
/*  44 */     this.name = ObjectUtil.checkNonEmpty(cleanName(name), "name");
/*  45 */     if (charset != null) {
/*  46 */       setCharset(charset);
/*     */     }
/*  48 */     this.definedSize = size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String cleanName(String name) {
/*  54 */     int len = name.length();
/*  55 */     StringBuilder sb = null;
/*     */     
/*  57 */     int start = 0;
/*  58 */     int end = len;
/*     */ 
/*     */     
/*  61 */     while (start < end && Character.isWhitespace(name.charAt(start))) {
/*  62 */       start++;
/*     */     }
/*     */ 
/*     */     
/*  66 */     while (end > start && Character.isWhitespace(name.charAt(end - 1))) {
/*  67 */       end--;
/*     */     }
/*     */     
/*  70 */     for (int i = start; i < end; i++) {
/*  71 */       char c = name.charAt(i);
/*     */       
/*  73 */       if (c == '\n') {
/*     */         
/*  75 */         if (sb == null) {
/*  76 */           sb = new StringBuilder(len);
/*  77 */           sb.append(name, start, i);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*  82 */       else if (c == '\r' || c == '\t') {
/*  83 */         if (sb == null) {
/*  84 */           sb = new StringBuilder(len);
/*  85 */           sb.append(name, start, i);
/*     */         } 
/*  87 */         sb.append(' ');
/*  88 */       } else if (sb != null) {
/*  89 */         sb.append(c);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  94 */     return (sb == null) ? name.substring(start, end) : sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getMaxSize() {
/*  99 */     return this.maxSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxSize(long maxSize) {
/* 104 */     this.maxSize = maxSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkSize(long newSize) throws IOException {
/* 109 */     if (this.maxSize >= 0L && newSize > this.maxSize) {
/* 110 */       throw new IOException("Size exceed allowed maximum capacity");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 116 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompleted() {
/* 121 */     return this.completed;
/*     */   }
/*     */   
/*     */   protected void setCompleted() {
/* 125 */     setCompleted(true);
/*     */   }
/*     */   
/*     */   protected void setCompleted(boolean completed) {
/* 129 */     this.completed = completed;
/*     */   }
/*     */ 
/*     */   
/*     */   public Charset getCharset() {
/* 134 */     return this.charset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCharset(Charset charset) {
/* 139 */     this.charset = (Charset)ObjectUtil.checkNotNull(charset, "charset");
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() {
/* 144 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public long definedLength() {
/* 149 */     return this.definedSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*     */     try {
/* 155 */       return getByteBuf();
/* 156 */     } catch (IOException e) {
/* 157 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/* 163 */     delete();
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpData retain() {
/* 168 */     super.retain();
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpData retain(int increment) {
/* 174 */     super.retain(increment);
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public abstract HttpData touch();
/*     */   
/*     */   public abstract HttpData touch(Object paramObject);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\AbstractHttpData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */