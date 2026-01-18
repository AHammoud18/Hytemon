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
/*     */ public class MemoryAttribute
/*     */   extends AbstractMemoryHttpData
/*     */   implements Attribute
/*     */ {
/*     */   public MemoryAttribute(String name) {
/*  34 */     this(name, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */   public MemoryAttribute(String name, long definedSize) {
/*  38 */     this(name, definedSize, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */   public MemoryAttribute(String name, Charset charset) {
/*  42 */     super(name, charset, 0L);
/*     */   }
/*     */   
/*     */   public MemoryAttribute(String name, long definedSize, Charset charset) {
/*  46 */     super(name, charset, definedSize);
/*     */   }
/*     */   
/*     */   public MemoryAttribute(String name, String value) throws IOException {
/*  50 */     this(name, value, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */   public MemoryAttribute(String name, String value, Charset charset) throws IOException {
/*  54 */     super(name, charset, 0L);
/*  55 */     setValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/*  60 */     return InterfaceHttpData.HttpDataType.Attribute;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  65 */     return getByteBuf().toString(getCharset());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(String value) throws IOException {
/*  70 */     ObjectUtil.checkNotNull(value, "value");
/*  71 */     byte[] bytes = value.getBytes(getCharset());
/*  72 */     checkSize(bytes.length);
/*  73 */     ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
/*  74 */     if (this.definedSize > 0L) {
/*  75 */       this.definedSize = buffer.readableBytes();
/*     */     }
/*  77 */     setContent(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/*  82 */     int localsize = buffer.readableBytes();
/*     */     try {
/*  84 */       checkSize(this.size + localsize);
/*  85 */     } catch (IOException e) {
/*  86 */       buffer.release();
/*  87 */       throw e;
/*     */     } 
/*  89 */     if (this.definedSize > 0L && this.definedSize < this.size + localsize) {
/*  90 */       this.definedSize = this.size + localsize;
/*     */     }
/*  92 */     super.addContent(buffer, last);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  97 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 102 */     if (!(o instanceof Attribute)) {
/* 103 */       return false;
/*     */     }
/* 105 */     Attribute attribute = (Attribute)o;
/* 106 */     return getName().equalsIgnoreCase(attribute.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData other) {
/* 111 */     if (!(other instanceof Attribute)) {
/* 112 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + other
/* 113 */           .getHttpDataType());
/*     */     }
/* 115 */     return compareTo((Attribute)other);
/*     */   }
/*     */   
/*     */   public int compareTo(Attribute o) {
/* 119 */     return getName().compareToIgnoreCase(o.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return getName() + '=' + getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute copy() {
/* 129 */     ByteBuf content = content();
/* 130 */     return replace((content != null) ? content.copy() : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute duplicate() {
/* 135 */     ByteBuf content = content();
/* 136 */     return replace((content != null) ? content.duplicate() : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retainedDuplicate() {
/* 141 */     ByteBuf content = content();
/* 142 */     if (content != null) {
/* 143 */       content = content.retainedDuplicate();
/* 144 */       boolean success = false;
/*     */       try {
/* 146 */         Attribute duplicate = replace(content);
/* 147 */         success = true;
/* 148 */         return duplicate;
/*     */       } finally {
/* 150 */         if (!success) {
/* 151 */           content.release();
/*     */         }
/*     */       } 
/*     */     } 
/* 155 */     return replace((ByteBuf)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute replace(ByteBuf content) {
/* 161 */     MemoryAttribute attr = new MemoryAttribute(getName());
/* 162 */     attr.setCharset(getCharset());
/* 163 */     if (content != null) {
/*     */       try {
/* 165 */         attr.setContent(content);
/* 166 */       } catch (IOException e) {
/* 167 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 170 */     attr.setCompleted(isCompleted());
/* 171 */     return attr;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain() {
/* 176 */     super.retain();
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain(int increment) {
/* 182 */     super.retain(increment);
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute touch() {
/* 188 */     super.touch();
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute touch(Object hint) {
/* 194 */     super.touch(hint);
/* 195 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\MemoryAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */