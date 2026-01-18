/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.handler.codec.http.HttpConstants;
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
/*     */ public class MixedAttribute
/*     */   extends AbstractMixedHttpData<Attribute>
/*     */   implements Attribute
/*     */ {
/*     */   public MixedAttribute(String name, long limitSize) {
/*  29 */     this(name, limitSize, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */   public MixedAttribute(String name, long definedSize, long limitSize) {
/*  33 */     this(name, definedSize, limitSize, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */   public MixedAttribute(String name, long limitSize, Charset charset) {
/*  37 */     this(name, limitSize, charset, DiskAttribute.baseDirectory, DiskAttribute.deleteOnExitTemporaryFile);
/*     */   }
/*     */   
/*     */   public MixedAttribute(String name, long limitSize, Charset charset, String baseDir, boolean deleteOnExit) {
/*  41 */     this(name, 0L, limitSize, charset, baseDir, deleteOnExit);
/*     */   }
/*     */   
/*     */   public MixedAttribute(String name, long definedSize, long limitSize, Charset charset) {
/*  45 */     this(name, definedSize, limitSize, charset, DiskAttribute.baseDirectory, DiskAttribute.deleteOnExitTemporaryFile);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MixedAttribute(String name, long definedSize, long limitSize, Charset charset, String baseDir, boolean deleteOnExit) {
/*  51 */     super(limitSize, baseDir, deleteOnExit, new MemoryAttribute(name, definedSize, charset));
/*     */   }
/*     */ 
/*     */   
/*     */   public MixedAttribute(String name, String value, long limitSize) {
/*  56 */     this(name, value, limitSize, HttpConstants.DEFAULT_CHARSET, DiskAttribute.baseDirectory, DiskFileUpload.deleteOnExitTemporaryFile);
/*     */   }
/*     */ 
/*     */   
/*     */   public MixedAttribute(String name, String value, long limitSize, Charset charset) {
/*  61 */     this(name, value, limitSize, charset, DiskAttribute.baseDirectory, DiskFileUpload.deleteOnExitTemporaryFile);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Attribute makeInitialAttributeFromValue(String name, String value, long limitSize, Charset charset, String baseDir, boolean deleteOnExit) {
/*  67 */     if (value.length() > limitSize) {
/*     */       try {
/*  69 */         return new DiskAttribute(name, value, charset, baseDir, deleteOnExit);
/*  70 */       } catch (IOException e) {
/*     */         
/*     */         try {
/*  73 */           return new MemoryAttribute(name, value, charset);
/*  74 */         } catch (IOException ignore) {
/*  75 */           throw new IllegalArgumentException(e);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     try {
/*  80 */       return new MemoryAttribute(name, value, charset);
/*  81 */     } catch (IOException e) {
/*  82 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MixedAttribute(String name, String value, long limitSize, Charset charset, String baseDir, boolean deleteOnExit) {
/*  89 */     super(limitSize, baseDir, deleteOnExit, 
/*  90 */         makeInitialAttributeFromValue(name, value, limitSize, charset, baseDir, deleteOnExit));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() throws IOException {
/*  95 */     return this.wrapped.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(String value) throws IOException {
/* 100 */     this.wrapped.setValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   Attribute makeDiskData() {
/* 105 */     DiskAttribute diskAttribute = new DiskAttribute(getName(), definedLength(), this.baseDir, this.deleteOnExit);
/* 106 */     diskAttribute.setMaxSize(getMaxSize());
/* 107 */     return diskAttribute;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute copy() {
/* 113 */     return super.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute duplicate() {
/* 119 */     return super.duplicate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute replace(ByteBuf content) {
/* 125 */     return super.replace(content);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute retain() {
/* 131 */     return super.retain();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute retain(int increment) {
/* 137 */     return super.retain(increment);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute retainedDuplicate() {
/* 143 */     return super.retainedDuplicate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute touch() {
/* 149 */     return super.touch();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute touch(Object hint) {
/* 155 */     return super.touch(hint);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\MixedAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */