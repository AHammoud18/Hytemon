/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.ByteProcessor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.zip.Adler32;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.Checksum;
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
/*     */ abstract class ByteBufChecksum
/*     */   implements Checksum
/*     */ {
/*  35 */   private final ByteProcessor updateProcessor = new ByteProcessor()
/*     */     {
/*     */       public boolean process(byte value) throws Exception {
/*  38 */         ByteBufChecksum.this.update(value);
/*  39 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   static ByteBufChecksum wrapChecksum(Checksum checksum) {
/*  44 */     ObjectUtil.checkNotNull(checksum, "checksum");
/*  45 */     if (checksum instanceof ByteBufChecksum) {
/*  46 */       return (ByteBufChecksum)checksum;
/*     */     }
/*  48 */     return new JdkByteBufChecksum(checksum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(ByteBuf b, int off, int len) {
/*  55 */     if (b.hasArray()) {
/*  56 */       update(b.array(), b.arrayOffset() + off, len);
/*     */     } else {
/*  58 */       b.forEachByte(off, len, this.updateProcessor);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class JdkByteBufChecksum extends ByteBufChecksum {
/*     */     protected final Checksum checksum;
/*     */     private byte[] scratchBuffer;
/*     */     
/*     */     JdkByteBufChecksum(Checksum checksum) {
/*  67 */       this.checksum = checksum;
/*     */     }
/*     */ 
/*     */     
/*     */     public void update(int b) {
/*  72 */       this.checksum.update(b);
/*     */     }
/*     */ 
/*     */     
/*     */     public void update(ByteBuf b, int off, int len) {
/*  77 */       if (b.hasArray()) {
/*  78 */         update(b.array(), b.arrayOffset() + off, len);
/*  79 */       } else if (this.checksum instanceof CRC32) {
/*  80 */         ByteBuffer byteBuffer = getSafeBuffer(b, off, len);
/*  81 */         ((CRC32)this.checksum).update(byteBuffer);
/*  82 */       } else if (this.checksum instanceof Adler32) {
/*  83 */         ByteBuffer byteBuffer = getSafeBuffer(b, off, len);
/*  84 */         ((Adler32)this.checksum).update(byteBuffer);
/*     */       } else {
/*  86 */         super.update(b, off, len);
/*     */       } 
/*     */     }
/*     */     
/*     */     private ByteBuffer getSafeBuffer(ByteBuf b, int off, int len) {
/*  91 */       ByteBuffer byteBuffer = CompressionUtil.safeNioBuffer(b, off, len);
/*  92 */       int javaVersion = PlatformDependent.javaVersion();
/*  93 */       if (javaVersion >= 22 && javaVersion < 25 && byteBuffer.isDirect()) {
/*     */         
/*  95 */         if (this.scratchBuffer == null || this.scratchBuffer.length < len) {
/*  96 */           this.scratchBuffer = new byte[len];
/*     */         }
/*  98 */         ByteBuffer copy = ByteBuffer.wrap(this.scratchBuffer, 0, len);
/*  99 */         copy.put(byteBuffer).flip();
/* 100 */         return copy;
/*     */       } 
/* 102 */       return byteBuffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void update(byte[] b, int off, int len) {
/* 107 */       this.checksum.update(b, off, len);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getValue() {
/* 112 */       return this.checksum.getValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void reset() {
/* 117 */       this.checksum.reset();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\ByteBufChecksum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */