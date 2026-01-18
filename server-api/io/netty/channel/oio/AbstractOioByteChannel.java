/*     */ package io.netty.channel.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import io.netty.channel.socket.ChannelInputShutdownReadComplete;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public abstract class AbstractOioByteChannel
/*     */   extends AbstractOioChannel
/*     */ {
/*  43 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  44 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*  45 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*  46 */     StringUtil.simpleClassName(FileRegion.class) + ')';
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractOioByteChannel(Channel parent) {
/*  52 */     super(parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  57 */     return METADATA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isInputShutdown();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ChannelFuture shutdownInput();
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeOnRead(ChannelPipeline pipeline) {
/*  73 */     if (isOpen()) {
/*  74 */       if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
/*  75 */         shutdownInput();
/*  76 */         pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */       } else {
/*  78 */         unsafe().close(unsafe().voidPromise());
/*     */       } 
/*  80 */       pipeline.fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, RecvByteBufAllocator.Handle allocHandle) {
/*  86 */     if (byteBuf != null) {
/*  87 */       if (byteBuf.isReadable()) {
/*  88 */         this.readPending = false;
/*  89 */         pipeline.fireChannelRead(byteBuf);
/*     */       } else {
/*  91 */         byteBuf.release();
/*     */       } 
/*     */     }
/*  94 */     allocHandle.readComplete();
/*  95 */     pipeline.fireChannelReadComplete();
/*  96 */     pipeline.fireExceptionCaught(cause);
/*     */ 
/*     */ 
/*     */     
/* 100 */     if (close || cause instanceof OutOfMemoryError || cause instanceof io.netty.util.LeakPresenceDetector.AllocationProhibitedException || cause instanceof java.io.IOException)
/*     */     {
/*     */ 
/*     */       
/* 104 */       closeOnRead(pipeline);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRead() {
/* 110 */     ChannelConfig config = config();
/* 111 */     if (isInputShutdown() || !this.readPending) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     this.readPending = false;
/*     */     
/* 120 */     ChannelPipeline pipeline = pipeline();
/* 121 */     ByteBufAllocator allocator = config.getAllocator();
/* 122 */     RecvByteBufAllocator.Handle allocHandle = unsafe().recvBufAllocHandle();
/* 123 */     allocHandle.reset(config);
/*     */     
/* 125 */     ByteBuf byteBuf = null;
/* 126 */     boolean close = false;
/* 127 */     boolean readData = false;
/*     */     try {
/* 129 */       byteBuf = allocHandle.allocate(allocator);
/*     */       do {
/* 131 */         allocHandle.lastBytesRead(doReadBytes(byteBuf));
/* 132 */         if (allocHandle.lastBytesRead() <= 0) {
/* 133 */           if (!byteBuf.isReadable()) {
/* 134 */             byteBuf.release();
/* 135 */             byteBuf = null;
/* 136 */             close = (allocHandle.lastBytesRead() < 0);
/* 137 */             if (close)
/*     */             {
/* 139 */               this.readPending = false;
/*     */             }
/*     */           } 
/*     */           break;
/*     */         } 
/* 144 */         readData = true;
/*     */ 
/*     */         
/* 147 */         int available = available();
/* 148 */         if (available <= 0) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 153 */         if (byteBuf.isWritable())
/* 154 */           continue;  int capacity = byteBuf.capacity();
/* 155 */         int maxCapacity = byteBuf.maxCapacity();
/* 156 */         if (capacity == maxCapacity) {
/* 157 */           allocHandle.incMessagesRead(1);
/* 158 */           this.readPending = false;
/* 159 */           pipeline.fireChannelRead(byteBuf);
/* 160 */           byteBuf = allocHandle.allocate(allocator);
/*     */         } else {
/* 162 */           int writerIndex = byteBuf.writerIndex();
/* 163 */           if (writerIndex + available > maxCapacity) {
/* 164 */             byteBuf.capacity(maxCapacity);
/*     */           } else {
/* 166 */             byteBuf.ensureWritable(available);
/*     */           }
/*     */         
/*     */         } 
/* 170 */       } while (allocHandle.continueReading());
/*     */       
/* 172 */       if (byteBuf != null) {
/*     */ 
/*     */         
/* 175 */         if (byteBuf.isReadable()) {
/* 176 */           this.readPending = false;
/* 177 */           pipeline.fireChannelRead(byteBuf);
/*     */         } else {
/* 179 */           byteBuf.release();
/*     */         } 
/* 181 */         byteBuf = null;
/*     */       } 
/*     */       
/* 184 */       if (readData) {
/* 185 */         allocHandle.readComplete();
/* 186 */         pipeline.fireChannelReadComplete();
/*     */       } 
/*     */       
/* 189 */       if (close) {
/* 190 */         closeOnRead(pipeline);
/*     */       }
/* 192 */     } catch (Throwable t) {
/* 193 */       handleReadException(pipeline, byteBuf, t, close, allocHandle);
/*     */     } finally {
/* 195 */       if (this.readPending || config.isAutoRead() || (!readData && isActive()))
/*     */       {
/*     */         
/* 198 */         read();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     while (true) {
/* 206 */       Object msg = in.current();
/* 207 */       if (msg == null) {
/*     */         break;
/*     */       }
/*     */       
/* 211 */       if (msg instanceof ByteBuf) {
/* 212 */         ByteBuf buf = (ByteBuf)msg;
/* 213 */         int readableBytes = buf.readableBytes();
/* 214 */         while (readableBytes > 0) {
/* 215 */           doWriteBytes(buf);
/* 216 */           int newReadableBytes = buf.readableBytes();
/* 217 */           in.progress((readableBytes - newReadableBytes));
/* 218 */           readableBytes = newReadableBytes;
/*     */         } 
/* 220 */         in.remove(); continue;
/* 221 */       }  if (msg instanceof FileRegion) {
/* 222 */         FileRegion region = (FileRegion)msg;
/* 223 */         long transferred = region.transferred();
/* 224 */         doWriteFileRegion(region);
/* 225 */         in.progress(region.transferred() - transferred);
/* 226 */         in.remove(); continue;
/*     */       } 
/* 228 */       in.remove(new UnsupportedOperationException("unsupported message type: " + 
/* 229 */             StringUtil.simpleClassName(msg)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object filterOutboundMessage(Object msg) throws Exception {
/* 236 */     if (msg instanceof ByteBuf || msg instanceof FileRegion) {
/* 237 */       return msg;
/*     */     }
/*     */     
/* 240 */     throw new UnsupportedOperationException("unsupported message type: " + 
/* 241 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */   
/*     */   protected abstract int available();
/*     */   
/*     */   protected abstract int doReadBytes(ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract void doWriteBytes(ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract void doWriteFileRegion(FileRegion paramFileRegion) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\oio\AbstractOioByteChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */