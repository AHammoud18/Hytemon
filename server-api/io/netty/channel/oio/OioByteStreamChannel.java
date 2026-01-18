/*     */ package io.netty.channel.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.nio.channels.WritableByteChannel;
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
/*     */ @Deprecated
/*     */ public abstract class OioByteStreamChannel
/*     */   extends AbstractOioByteChannel
/*     */ {
/*  41 */   private static final InputStream CLOSED_IN = new InputStream()
/*     */     {
/*     */       public int read() {
/*  44 */         return -1;
/*     */       }
/*     */     };
/*     */   
/*  48 */   private static final OutputStream CLOSED_OUT = new OutputStream()
/*     */     {
/*     */       public void write(int b) throws IOException {
/*  51 */         throw new ClosedChannelException();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream is;
/*     */ 
/*     */   
/*     */   private OutputStream os;
/*     */   
/*     */   private WritableByteChannel outChannel;
/*     */ 
/*     */   
/*     */   protected OioByteStreamChannel(Channel parent) {
/*  66 */     super(parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void activate(InputStream is, OutputStream os) {
/*  73 */     if (this.is != null) {
/*  74 */       throw new IllegalStateException("input was set already");
/*     */     }
/*  76 */     if (this.os != null) {
/*  77 */       throw new IllegalStateException("output was set already");
/*     */     }
/*  79 */     this.is = (InputStream)ObjectUtil.checkNotNull(is, "is");
/*  80 */     this.os = (OutputStream)ObjectUtil.checkNotNull(os, "os");
/*  81 */     if (this.readWhenInactive) {
/*  82 */       eventLoop().execute(this.readTask);
/*  83 */       this.readWhenInactive = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  89 */     InputStream is = this.is;
/*  90 */     if (is == null || is == CLOSED_IN) {
/*  91 */       return false;
/*     */     }
/*     */     
/*  94 */     OutputStream os = this.os;
/*  95 */     return (os != null && os != CLOSED_OUT);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int available() {
/*     */     try {
/* 101 */       return this.is.available();
/* 102 */     } catch (IOException ignored) {
/* 103 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadBytes(ByteBuf buf) throws Exception {
/* 109 */     RecvByteBufAllocator.Handle allocHandle = unsafe().recvBufAllocHandle();
/* 110 */     allocHandle.attemptedBytesRead(Math.max(1, Math.min(available(), buf.maxWritableBytes())));
/* 111 */     return buf.writeBytes(this.is, allocHandle.attemptedBytesRead());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteBytes(ByteBuf buf) throws Exception {
/* 116 */     OutputStream os = this.os;
/* 117 */     if (os == null) {
/* 118 */       throw new NotYetConnectedException();
/*     */     }
/* 120 */     buf.readBytes(os, buf.readableBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteFileRegion(FileRegion region) throws Exception {
/* 125 */     OutputStream os = this.os;
/* 126 */     if (os == null) {
/* 127 */       throw new NotYetConnectedException();
/*     */     }
/* 129 */     if (this.outChannel == null) {
/* 130 */       this.outChannel = Channels.newChannel(os);
/*     */     }
/*     */     
/* 133 */     long written = 0L;
/*     */     do {
/* 135 */       long localWritten = region.transferTo(this.outChannel, written);
/* 136 */       if (localWritten == -1L) {
/* 137 */         checkEOF(region);
/*     */         return;
/*     */       } 
/* 140 */       written += localWritten;
/*     */     }
/* 142 */     while (written < region.count());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkEOF(FileRegion region) throws IOException {
/* 149 */     if (region.transferred() < region.count()) {
/* 150 */       throw new EOFException("Expected to be able to write " + region.count() + " bytes, but only wrote " + region
/* 151 */           .transferred());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 157 */     InputStream is = this.is;
/* 158 */     OutputStream os = this.os;
/* 159 */     this.is = CLOSED_IN;
/* 160 */     this.os = CLOSED_OUT;
/*     */     
/*     */     try {
/* 163 */       if (is != null) {
/* 164 */         is.close();
/*     */       }
/*     */     } finally {
/* 167 */       if (os != null)
/* 168 */         os.close(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\oio\OioByteStreamChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */