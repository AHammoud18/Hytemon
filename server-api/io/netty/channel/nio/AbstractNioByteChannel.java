/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.IoRegistration;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import io.netty.channel.socket.ChannelInputShutdownReadComplete;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.nio.channels.SelectableChannel;
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
/*     */ public abstract class AbstractNioByteChannel
/*     */   extends AbstractNioChannel
/*     */ {
/*  47 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
/*  48 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*  49 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*  50 */     StringUtil.simpleClassName(FileRegion.class) + ')';
/*     */   
/*  52 */   private final Runnable flushTask = new Runnable()
/*     */     {
/*     */       
/*     */       public void run()
/*     */       {
/*  57 */         ((AbstractNioChannel.AbstractNioUnsafe)AbstractNioByteChannel.this.unsafe()).flush0();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inputClosedSeenErrorOnRead;
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractNioByteChannel(Channel parent, SelectableChannel ch) {
/*  69 */     super(parent, ch, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInputShutdown0() {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
/*  83 */     return new NioByteUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  88 */     return METADATA;
/*     */   }
/*     */   
/*     */   final boolean shouldBreakReadReady(ChannelConfig config) {
/*  92 */     return (isInputShutdown0() && (this.inputClosedSeenErrorOnRead || !isAllowHalfClosure(config)));
/*     */   }
/*     */   
/*     */   private static boolean isAllowHalfClosure(ChannelConfig config) {
/*  96 */     return (config instanceof SocketChannelConfig && ((SocketChannelConfig)config)
/*  97 */       .isAllowHalfClosure());
/*     */   }
/*     */   
/*     */   protected class NioByteUnsafe
/*     */     extends AbstractNioChannel.AbstractNioUnsafe {
/*     */     private void closeOnRead(ChannelPipeline pipeline) {
/* 103 */       if (!AbstractNioByteChannel.this.isInputShutdown0()) {
/* 104 */         if (AbstractNioByteChannel.isAllowHalfClosure(AbstractNioByteChannel.this.config())) {
/* 105 */           AbstractNioByteChannel.this.shutdownInput();
/* 106 */           pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */         } else {
/* 108 */           close(voidPromise());
/*     */         } 
/* 110 */       } else if (!AbstractNioByteChannel.this.inputClosedSeenErrorOnRead) {
/* 111 */         AbstractNioByteChannel.this.inputClosedSeenErrorOnRead = true;
/* 112 */         pipeline.fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, RecvByteBufAllocator.Handle allocHandle) {
/* 118 */       if (byteBuf != null) {
/* 119 */         if (byteBuf.isReadable()) {
/* 120 */           AbstractNioByteChannel.this.readPending = false;
/* 121 */           pipeline.fireChannelRead(byteBuf);
/*     */         } else {
/* 123 */           byteBuf.release();
/*     */         } 
/*     */       }
/* 126 */       allocHandle.readComplete();
/* 127 */       pipeline.fireChannelReadComplete();
/* 128 */       pipeline.fireExceptionCaught(cause);
/*     */ 
/*     */ 
/*     */       
/* 132 */       if (close || cause instanceof OutOfMemoryError || cause instanceof io.netty.util.LeakPresenceDetector.AllocationProhibitedException || cause instanceof java.io.IOException)
/*     */       {
/*     */ 
/*     */         
/* 136 */         closeOnRead(pipeline);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public final void read() {
/* 142 */       ChannelConfig config = AbstractNioByteChannel.this.config();
/* 143 */       if (AbstractNioByteChannel.this.shouldBreakReadReady(config)) {
/* 144 */         AbstractNioByteChannel.this.clearReadPending();
/*     */         return;
/*     */       } 
/* 147 */       ChannelPipeline pipeline = AbstractNioByteChannel.this.pipeline();
/* 148 */       ByteBufAllocator allocator = config.getAllocator();
/* 149 */       RecvByteBufAllocator.Handle allocHandle = recvBufAllocHandle();
/* 150 */       allocHandle.reset(config);
/*     */       
/* 152 */       ByteBuf byteBuf = null;
/* 153 */       boolean close = false;
/*     */       try {
/*     */         do {
/* 156 */           byteBuf = allocHandle.allocate(allocator);
/* 157 */           allocHandle.lastBytesRead(AbstractNioByteChannel.this.doReadBytes(byteBuf));
/* 158 */           if (allocHandle.lastBytesRead() <= 0) {
/*     */             
/* 160 */             byteBuf.release();
/* 161 */             byteBuf = null;
/* 162 */             close = (allocHandle.lastBytesRead() < 0);
/* 163 */             if (close)
/*     */             {
/* 165 */               AbstractNioByteChannel.this.readPending = false;
/*     */             }
/*     */             
/*     */             break;
/*     */           } 
/* 170 */           allocHandle.incMessagesRead(1);
/* 171 */           AbstractNioByteChannel.this.readPending = false;
/* 172 */           pipeline.fireChannelRead(byteBuf);
/* 173 */           byteBuf = null;
/* 174 */         } while (allocHandle.continueReading());
/*     */         
/* 176 */         allocHandle.readComplete();
/* 177 */         pipeline.fireChannelReadComplete();
/*     */         
/* 179 */         if (close) {
/* 180 */           closeOnRead(pipeline);
/*     */         }
/* 182 */       } catch (Throwable t) {
/* 183 */         handleReadException(pipeline, byteBuf, t, close, allocHandle);
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */ 
/*     */         
/* 191 */         if (!AbstractNioByteChannel.this.readPending && !config.isAutoRead()) {
/* 192 */           removeReadOp();
/*     */         }
/*     */       } 
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int doWrite0(ChannelOutboundBuffer in) throws Exception {
/* 213 */     Object msg = in.current();
/* 214 */     if (msg == null)
/*     */     {
/* 216 */       return 0;
/*     */     }
/* 218 */     return doWriteInternal(in, in.current());
/*     */   }
/*     */   
/*     */   private int doWriteInternal(ChannelOutboundBuffer in, Object msg) throws Exception {
/* 222 */     if (msg instanceof ByteBuf) {
/* 223 */       ByteBuf buf = (ByteBuf)msg;
/* 224 */       if (!buf.isReadable()) {
/* 225 */         in.remove();
/* 226 */         return 0;
/*     */       } 
/*     */       
/* 229 */       int localFlushedAmount = doWriteBytes(buf);
/* 230 */       if (localFlushedAmount > 0) {
/* 231 */         in.progress(localFlushedAmount);
/* 232 */         if (!buf.isReadable()) {
/* 233 */           in.remove();
/*     */         }
/* 235 */         return 1;
/*     */       } 
/* 237 */     } else if (msg instanceof FileRegion) {
/* 238 */       FileRegion region = (FileRegion)msg;
/* 239 */       if (region.transferred() >= region.count()) {
/* 240 */         in.remove();
/* 241 */         return 0;
/*     */       } 
/*     */       
/* 244 */       long localFlushedAmount = doWriteFileRegion(region);
/* 245 */       if (localFlushedAmount > 0L) {
/* 246 */         in.progress(localFlushedAmount);
/* 247 */         if (region.transferred() >= region.count()) {
/* 248 */           in.remove();
/*     */         }
/* 250 */         return 1;
/*     */       } 
/*     */     } else {
/*     */       
/* 254 */       throw new Error("Unexpected message type: " + StringUtil.className(msg));
/*     */     } 
/* 256 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 261 */     int writeSpinCount = config().getWriteSpinCount();
/*     */     do {
/* 263 */       Object msg = in.current();
/* 264 */       if (msg == null) {
/*     */         
/* 266 */         clearOpWrite();
/*     */         
/*     */         return;
/*     */       } 
/* 270 */       writeSpinCount -= doWriteInternal(in, msg);
/* 271 */     } while (writeSpinCount > 0);
/*     */     
/* 273 */     incompleteWrite((writeSpinCount < 0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Object filterOutboundMessage(Object msg) {
/* 278 */     if (msg instanceof ByteBuf) {
/* 279 */       ByteBuf buf = (ByteBuf)msg;
/* 280 */       if (buf.isDirect()) {
/* 281 */         return msg;
/*     */       }
/*     */       
/* 284 */       return newDirectBuffer(buf);
/*     */     } 
/*     */     
/* 287 */     if (msg instanceof FileRegion) {
/* 288 */       return msg;
/*     */     }
/*     */     
/* 291 */     throw new UnsupportedOperationException("unsupported message type: " + 
/* 292 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void incompleteWrite(boolean setOpWrite) {
/* 297 */     if (setOpWrite) {
/* 298 */       setOpWrite();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 304 */       clearOpWrite();
/*     */ 
/*     */       
/* 307 */       eventLoop().execute(this.flushTask);
/*     */     } 
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
/*     */   protected final void setOpWrite() {
/* 332 */     IoRegistration registration = registration();
/*     */ 
/*     */ 
/*     */     
/* 336 */     if (!registration.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 340 */     addAndSubmit(NioIoOps.WRITE);
/*     */   }
/*     */   
/*     */   protected final void clearOpWrite() {
/* 344 */     IoRegistration registration = registration();
/*     */ 
/*     */ 
/*     */     
/* 348 */     if (!registration.isValid()) {
/*     */       return;
/*     */     }
/* 351 */     removeAndSubmit(NioIoOps.WRITE);
/*     */   }
/*     */   
/*     */   protected abstract ChannelFuture shutdownInput();
/*     */   
/*     */   protected abstract long doWriteFileRegion(FileRegion paramFileRegion) throws Exception;
/*     */   
/*     */   protected abstract int doReadBytes(ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract int doWriteBytes(ByteBuf paramByteBuf) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\AbstractNioByteChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */