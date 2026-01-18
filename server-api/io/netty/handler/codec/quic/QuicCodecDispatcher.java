/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public abstract class QuicCodecDispatcher
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private static final int MAX_LOCAL_CONNECTION_ID_LENGTH = 20;
/*  58 */   private final List<ChannelHandlerContextDispatcher> contextList = new CopyOnWriteArrayList<>();
/*     */ 
/*     */   
/*     */   private final int localConnectionIdLength;
/*     */ 
/*     */   
/*     */   protected QuicCodecDispatcher() {
/*  65 */     this(20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected QuicCodecDispatcher(int localConnectionIdLength) {
/*  76 */     this.localConnectionIdLength = ObjectUtil.checkInRange(localConnectionIdLength, 10, 20, "localConnectionIdLength");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isSharable() {
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  87 */     super.handlerAdded(ctx);
/*     */     
/*  89 */     ChannelHandlerContextDispatcher ctxDispatcher = new ChannelHandlerContextDispatcher(ctx);
/*  90 */     this.contextList.add(ctxDispatcher);
/*  91 */     int idx = this.contextList.indexOf(ctxDispatcher);
/*     */     try {
/*  93 */       QuicConnectionIdGenerator idGenerator = newIdGenerator((short)idx);
/*  94 */       initChannel(ctx.channel(), this.localConnectionIdLength, idGenerator);
/*  95 */     } catch (Exception e) {
/*     */ 
/*     */       
/*  98 */       this.contextList.set(idx, null);
/*  99 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 105 */     super.handlerRemoved(ctx);
/*     */     
/* 107 */     for (int idx = 0; idx < this.contextList.size(); idx++) {
/* 108 */       ChannelHandlerContextDispatcher ctxDispatcher = this.contextList.get(idx);
/* 109 */       if (ctxDispatcher != null && ctxDispatcher.ctx.equals(ctx)) {
/*     */         
/* 111 */         this.contextList.set(idx, null);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 119 */     DatagramPacket packet = (DatagramPacket)msg;
/* 120 */     ByteBuf connectionId = getDestinationConnectionId((ByteBuf)packet.content(), this.localConnectionIdLength);
/* 121 */     if (connectionId != null) {
/* 122 */       int idx = decodeIndex(connectionId);
/* 123 */       if (this.contextList.size() > idx) {
/* 124 */         ChannelHandlerContextDispatcher selectedCtx = this.contextList.get(idx);
/* 125 */         if (selectedCtx != null) {
/* 126 */           selectedCtx.fireChannelRead(msg);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void channelReadComplete(ChannelHandlerContext ctx) {
/* 141 */     boolean dispatchForOwnContextAlready = false;
/* 142 */     for (int i = 0; i < this.contextList.size(); i++) {
/* 143 */       ChannelHandlerContextDispatcher ctxDispatcher = this.contextList.get(i);
/* 144 */       if (ctxDispatcher != null) {
/* 145 */         boolean fired = ctxDispatcher.fireChannelReadCompleteIfNeeded();
/* 146 */         if (fired && !dispatchForOwnContextAlready)
/*     */         {
/* 148 */           dispatchForOwnContextAlready = ctx.equals(ctxDispatcher.ctx);
/*     */         }
/*     */       } 
/*     */     } 
/* 152 */     if (!dispatchForOwnContextAlready) {
/* 153 */       ctx.fireChannelReadComplete();
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
/*     */   protected abstract void initChannel(Channel paramChannel, int paramInt, QuicConnectionIdGenerator paramQuicConnectionIdGenerator) throws Exception;
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
/*     */   protected int decodeIndex(ByteBuf connectionId) {
/* 184 */     return decodeIdx(connectionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static ByteBuf getDestinationConnectionId(ByteBuf buffer, int localConnectionIdLength) throws QuicException {
/* 196 */     if (buffer.readableBytes() > 1) {
/* 197 */       int offset = buffer.readerIndex();
/* 198 */       boolean shortHeader = hasShortHeader(buffer);
/* 199 */       offset++;
/*     */ 
/*     */       
/* 202 */       if (shortHeader)
/*     */       {
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
/* 215 */         return QuicHeaderParser.sliceCid(buffer, offset, localConnectionIdLength);
/*     */       }
/*     */     } 
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean hasShortHeader(ByteBuf buffer) {
/* 223 */     return QuicHeaderParser.hasShortHeader(buffer.getByte(buffer.readerIndex()));
/*     */   }
/*     */ 
/*     */   
/*     */   static int decodeIdx(ByteBuf connectionId) {
/* 228 */     if (connectionId.readableBytes() >= 2) {
/* 229 */       return connectionId.getUnsignedShort(connectionId.readerIndex());
/*     */     }
/* 231 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteBuffer encodeIdx(ByteBuffer buffer, int idx) {
/* 237 */     ByteBuffer b = ByteBuffer.allocate(buffer.capacity() + 2);
/*     */     
/* 239 */     b.putShort((short)idx).put(buffer).flip();
/* 240 */     return b;
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
/*     */   protected QuicConnectionIdGenerator newIdGenerator(int idx) {
/* 254 */     return new IndexAwareQuicConnectionIdGenerator(idx, SecureRandomQuicConnectionIdGenerator.INSTANCE);
/*     */   }
/*     */   
/*     */   private static final class IndexAwareQuicConnectionIdGenerator implements QuicConnectionIdGenerator {
/*     */     private final int idx;
/*     */     private final QuicConnectionIdGenerator idGenerator;
/*     */     
/*     */     IndexAwareQuicConnectionIdGenerator(int idx, QuicConnectionIdGenerator idGenerator) {
/* 262 */       this.idx = idx;
/* 263 */       this.idGenerator = idGenerator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer newId(int length) {
/* 268 */       if (length > 2) {
/* 269 */         return QuicCodecDispatcher.encodeIdx(this.idGenerator.newId(length - 2), this.idx);
/*     */       }
/* 271 */       return this.idGenerator.newId(length);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer newId(ByteBuffer input, int length) {
/* 276 */       if (length > 2) {
/* 277 */         return QuicCodecDispatcher.encodeIdx(this.idGenerator.newId(input, length - 2), this.idx);
/*     */       }
/* 279 */       return this.idGenerator.newId(input, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer newId(ByteBuffer scid, ByteBuffer dcid, int length) {
/* 284 */       if (length > 2) {
/* 285 */         return QuicCodecDispatcher.encodeIdx(this.idGenerator.newId(scid, dcid, length - 2), this.idx);
/*     */       }
/* 287 */       return this.idGenerator.newId(scid, dcid, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public int maxConnectionIdLength() {
/* 292 */       return this.idGenerator.maxConnectionIdLength();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isIdempotent() {
/* 298 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ChannelHandlerContextDispatcher
/*     */     extends AtomicBoolean {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     ChannelHandlerContextDispatcher(ChannelHandlerContext ctx) {
/* 307 */       this.ctx = ctx;
/*     */     }
/*     */     
/*     */     void fireChannelRead(Object msg) {
/* 311 */       this.ctx.fireChannelRead(msg);
/* 312 */       set(true);
/*     */     }
/*     */     
/*     */     boolean fireChannelReadCompleteIfNeeded() {
/* 316 */       if (getAndSet(false)) {
/*     */ 
/*     */         
/* 319 */         this.ctx.fireChannelReadComplete();
/* 320 */         return true;
/*     */       } 
/* 322 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicCodecDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */