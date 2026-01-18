/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.AbstractReferenceCounted;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.IllegalReferenceCountException;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.security.PrivateKey;
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
/*     */ public final class PemPrivateKey
/*     */   extends AbstractReferenceCounted
/*     */   implements PrivateKey, PemEncoded
/*     */ {
/*     */   private static final long serialVersionUID = 7978017465645018936L;
/*  46 */   private static final byte[] BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
/*  47 */   private static final byte[] END_PRIVATE_KEY = "\n-----END PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PKCS8_FORMAT = "PKCS#8";
/*     */ 
/*     */   
/*     */   private final ByteBuf content;
/*     */ 
/*     */ 
/*     */   
/*     */   static PemEncoded toPEM(ByteBufAllocator allocator, boolean useDirect, PrivateKey key) {
/*  59 */     if (key instanceof PemEncoded) {
/*  60 */       return ((PemEncoded)key).retain();
/*     */     }
/*     */     
/*  63 */     byte[] bytes = key.getEncoded();
/*  64 */     if (bytes == null) {
/*  65 */       throw new IllegalArgumentException(key.getClass().getName() + " does not support encoding");
/*     */     }
/*     */     
/*  68 */     return toPEM(allocator, useDirect, bytes);
/*     */   }
/*     */   
/*     */   static PemEncoded toPEM(ByteBufAllocator allocator, boolean useDirect, byte[] bytes) {
/*  72 */     ByteBuf encoded = Unpooled.wrappedBuffer(bytes);
/*     */     try {
/*  74 */       ByteBuf base64 = SslUtils.toBase64(allocator, encoded);
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
/*     */     }
/*     */     finally {
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
/*  98 */       SslUtils.zerooutAndRelease(encoded);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PemPrivateKey valueOf(byte[] key) {
/* 109 */     return valueOf(Unpooled.wrappedBuffer(key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PemPrivateKey valueOf(ByteBuf key) {
/* 119 */     return new PemPrivateKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PemPrivateKey(ByteBuf content) {
/* 125 */     this.content = (ByteBuf)ObjectUtil.checkNotNull(content, "content");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSensitive() {
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/* 135 */     int count = refCnt();
/* 136 */     if (count <= 0) {
/* 137 */       throw new IllegalReferenceCountException(count);
/*     */     }
/*     */     
/* 140 */     return this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public PemPrivateKey copy() {
/* 145 */     return replace(this.content.copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public PemPrivateKey duplicate() {
/* 150 */     return replace(this.content.duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public PemPrivateKey retainedDuplicate() {
/* 155 */     return replace(this.content.retainedDuplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public PemPrivateKey replace(ByteBuf content) {
/* 160 */     return new PemPrivateKey(content);
/*     */   }
/*     */ 
/*     */   
/*     */   public PemPrivateKey touch() {
/* 165 */     this.content.touch();
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PemPrivateKey touch(Object hint) {
/* 171 */     this.content.touch(hint);
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PemPrivateKey retain() {
/* 177 */     return (PemPrivateKey)super.retain();
/*     */   }
/*     */ 
/*     */   
/*     */   public PemPrivateKey retain(int increment) {
/* 182 */     return (PemPrivateKey)super.retain(increment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/* 189 */     SslUtils.zerooutAndRelease(this.content);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() {
/* 194 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAlgorithm() {
/* 199 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormat() {
/* 204 */     return "PKCS#8";
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
/*     */   public void destroy() {
/* 216 */     release(refCnt());
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
/*     */   public boolean isDestroyed() {
/* 228 */     return (refCnt() == 0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\PemPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */