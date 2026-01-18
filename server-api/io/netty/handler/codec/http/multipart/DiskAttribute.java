/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.handler.codec.http.HttpConstants;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiskAttribute
/*     */   extends AbstractDiskHttpData
/*     */   implements Attribute
/*     */ {
/*     */   public static String baseDirectory;
/*     */   public static boolean deleteOnExitTemporaryFile = true;
/*     */   public static final String prefix = "Attr_";
/*     */   public static final String postfix = ".att";
/*     */   private String baseDir;
/*     */   private boolean deleteOnExit;
/*     */   
/*     */   public DiskAttribute(String name) {
/*  48 */     this(name, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, String baseDir, boolean deleteOnExit) {
/*  52 */     this(name, HttpConstants.DEFAULT_CHARSET);
/*  53 */     this.baseDir = (baseDir == null) ? baseDirectory : baseDir;
/*  54 */     this.deleteOnExit = deleteOnExit;
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, long definedSize) {
/*  58 */     this(name, definedSize, HttpConstants.DEFAULT_CHARSET, baseDirectory, deleteOnExitTemporaryFile);
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, long definedSize, String baseDir, boolean deleteOnExit) {
/*  62 */     this(name, definedSize, HttpConstants.DEFAULT_CHARSET);
/*  63 */     this.baseDir = (baseDir == null) ? baseDirectory : baseDir;
/*  64 */     this.deleteOnExit = deleteOnExit;
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, Charset charset) {
/*  68 */     this(name, charset, baseDirectory, deleteOnExitTemporaryFile);
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, Charset charset, String baseDir, boolean deleteOnExit) {
/*  72 */     super(name, charset, 0L);
/*  73 */     this.baseDir = (baseDir == null) ? baseDirectory : baseDir;
/*  74 */     this.deleteOnExit = deleteOnExit;
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, long definedSize, Charset charset) {
/*  78 */     this(name, definedSize, charset, baseDirectory, deleteOnExitTemporaryFile);
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, long definedSize, Charset charset, String baseDir, boolean deleteOnExit) {
/*  82 */     super(name, charset, definedSize);
/*  83 */     this.baseDir = (baseDir == null) ? baseDirectory : baseDir;
/*  84 */     this.deleteOnExit = deleteOnExit;
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, String value) throws IOException {
/*  88 */     this(name, value, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, String value, Charset charset) throws IOException {
/*  92 */     this(name, value, charset, baseDirectory, deleteOnExitTemporaryFile);
/*     */   }
/*     */ 
/*     */   
/*     */   public DiskAttribute(String name, String value, Charset charset, String baseDir, boolean deleteOnExit) throws IOException {
/*  97 */     super(name, charset, 0L);
/*  98 */     setValue(value);
/*  99 */     this.baseDir = (baseDir == null) ? baseDirectory : baseDir;
/* 100 */     this.deleteOnExit = deleteOnExit;
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/* 105 */     return InterfaceHttpData.HttpDataType.Attribute;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() throws IOException {
/* 110 */     byte[] bytes = get();
/* 111 */     return new String(bytes, getCharset());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(String value) throws IOException {
/* 116 */     ObjectUtil.checkNotNull(value, "value");
/* 117 */     byte[] bytes = value.getBytes(getCharset());
/* 118 */     checkSize(bytes.length);
/* 119 */     ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
/* 120 */     if (this.definedSize > 0L) {
/* 121 */       this.definedSize = buffer.readableBytes();
/*     */     }
/* 123 */     setContent(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/* 128 */     long newDefinedSize = this.size + buffer.readableBytes();
/*     */     try {
/* 130 */       checkSize(newDefinedSize);
/* 131 */     } catch (IOException e) {
/* 132 */       buffer.release();
/* 133 */       throw e;
/*     */     } 
/* 135 */     if (this.definedSize > 0L && this.definedSize < newDefinedSize) {
/* 136 */       this.definedSize = newDefinedSize;
/*     */     }
/* 138 */     super.addContent(buffer, last);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 143 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 148 */     if (!(o instanceof Attribute)) {
/* 149 */       return false;
/*     */     }
/* 151 */     Attribute attribute = (Attribute)o;
/* 152 */     return getName().equalsIgnoreCase(attribute.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/* 157 */     if (!(o instanceof Attribute)) {
/* 158 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + o
/* 159 */           .getHttpDataType());
/*     */     }
/* 161 */     return compareTo((Attribute)o);
/*     */   }
/*     */   
/*     */   public int compareTo(Attribute o) {
/* 165 */     return getName().compareToIgnoreCase(o.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     try {
/* 171 */       return getName() + '=' + getValue();
/* 172 */     } catch (IOException e) {
/* 173 */       return getName() + '=' + e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean deleteOnExit() {
/* 179 */     return this.deleteOnExit;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getBaseDirectory() {
/* 184 */     return this.baseDir;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDiskFilename() {
/* 189 */     return getName() + ".att";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPostfix() {
/* 194 */     return ".att";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPrefix() {
/* 199 */     return "Attr_";
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute copy() {
/* 204 */     ByteBuf content = content();
/* 205 */     return replace((content != null) ? content.copy() : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute duplicate() {
/* 210 */     ByteBuf content = content();
/* 211 */     return replace((content != null) ? content.duplicate() : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retainedDuplicate() {
/* 216 */     ByteBuf content = content();
/* 217 */     if (content != null) {
/* 218 */       content = content.retainedDuplicate();
/* 219 */       boolean success = false;
/*     */       try {
/* 221 */         Attribute duplicate = replace(content);
/* 222 */         success = true;
/* 223 */         return duplicate;
/*     */       } finally {
/* 225 */         if (!success) {
/* 226 */           content.release();
/*     */         }
/*     */       } 
/*     */     } 
/* 230 */     return replace((ByteBuf)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute replace(ByteBuf content) {
/* 236 */     DiskAttribute attr = new DiskAttribute(getName(), this.baseDir, this.deleteOnExit);
/* 237 */     attr.setCharset(getCharset());
/* 238 */     if (content != null) {
/*     */       try {
/* 240 */         attr.setContent(content);
/* 241 */       } catch (IOException e) {
/* 242 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 245 */     attr.setCompleted(isCompleted());
/* 246 */     return attr;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain(int increment) {
/* 251 */     super.retain(increment);
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain() {
/* 257 */     super.retain();
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute touch() {
/* 263 */     super.touch();
/* 264 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute touch(Object hint) {
/* 269 */     super.touch(hint);
/* 270 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\DiskAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */