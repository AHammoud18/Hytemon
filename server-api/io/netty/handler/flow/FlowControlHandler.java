/*     */ package io.netty.handler.flow;
/*     */ 
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectPool;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayDeque;
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
/*     */ public class FlowControlHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  68 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(FlowControlHandler.class);
/*     */   
/*     */   private final boolean releaseMessages;
/*     */   
/*     */   private RecyclableArrayDeque queue;
/*     */   
/*     */   private ChannelConfig config;
/*     */   
/*     */   private boolean shouldConsume;
/*     */   
/*     */   public FlowControlHandler() {
/*  79 */     this(true);
/*     */   }
/*     */   
/*     */   public FlowControlHandler(boolean releaseMessages) {
/*  83 */     this.releaseMessages = releaseMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isQueueEmpty() {
/*  91 */     return (this.queue == null || this.queue.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void destroy() {
/*  98 */     if (this.queue != null) {
/*     */       
/* 100 */       if (!this.queue.isEmpty()) {
/* 101 */         logger.trace("Non-empty queue: {}", this.queue);
/*     */         
/* 103 */         if (this.releaseMessages) {
/*     */           Object msg;
/* 105 */           while ((msg = this.queue.poll()) != null) {
/* 106 */             ReferenceCountUtil.safeRelease(msg);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 111 */       this.queue.recycle();
/* 112 */       this.queue = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 118 */     this.config = ctx.channel().config();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 123 */     super.handlerRemoved(ctx);
/* 124 */     if (!isQueueEmpty()) {
/* 125 */       dequeue(ctx, this.queue.size());
/*     */     }
/* 127 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 132 */     destroy();
/* 133 */     ctx.fireChannelInactive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 138 */     if (dequeue(ctx, 1) == 0) {
/*     */ 
/*     */ 
/*     */       
/* 142 */       this.shouldConsume = true;
/* 143 */       ctx.read();
/* 144 */     } else if (this.config.isAutoRead()) {
/* 145 */       ctx.read();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 151 */     if (this.queue == null) {
/* 152 */       this.queue = RecyclableArrayDeque.newInstance();
/*     */     }
/*     */     
/* 155 */     this.queue.offer(msg);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     int minConsume = this.shouldConsume ? 1 : 0;
/* 161 */     this.shouldConsume = false;
/*     */     
/* 163 */     dequeue(ctx, minConsume);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 168 */     if (isQueueEmpty()) {
/* 169 */       ctx.fireChannelReadComplete();
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
/*     */   private int dequeue(ChannelHandlerContext ctx, int minConsume) {
/* 190 */     int consumed = 0;
/*     */ 
/*     */ 
/*     */     
/* 194 */     while (this.queue != null && (consumed < minConsume || this.config.isAutoRead())) {
/* 195 */       Object msg = this.queue.poll();
/* 196 */       if (msg == null) {
/*     */         break;
/*     */       }
/*     */       
/* 200 */       consumed++;
/* 201 */       ctx.fireChannelRead(msg);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     if (this.queue != null && this.queue.isEmpty()) {
/* 208 */       this.queue.recycle();
/* 209 */       this.queue = null;
/*     */       
/* 211 */       if (consumed > 0) {
/* 212 */         ctx.fireChannelReadComplete();
/*     */       }
/*     */     } 
/*     */     
/* 216 */     return consumed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class RecyclableArrayDeque
/*     */     extends ArrayDeque<Object>
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     private static final int DEFAULT_NUM_ELEMENTS = 2;
/*     */ 
/*     */ 
/*     */     
/* 231 */     private static final Recycler<RecyclableArrayDeque> RECYCLER = new Recycler<RecyclableArrayDeque>()
/*     */       {
/*     */         protected FlowControlHandler.RecyclableArrayDeque newObject(Recycler.Handle<FlowControlHandler.RecyclableArrayDeque> handle)
/*     */         {
/* 235 */           return new FlowControlHandler.RecyclableArrayDeque(2, (ObjectPool.Handle)handle);
/*     */         }
/*     */       };
/*     */     
/*     */     public static RecyclableArrayDeque newInstance() {
/* 240 */       return (RecyclableArrayDeque)RECYCLER.get();
/*     */     }
/*     */     
/*     */     private final ObjectPool.Handle<RecyclableArrayDeque> handle;
/*     */     
/*     */     private RecyclableArrayDeque(int numElements, ObjectPool.Handle<RecyclableArrayDeque> handle) {
/* 246 */       super(numElements);
/* 247 */       this.handle = handle;
/*     */     }
/*     */     
/*     */     public void recycle() {
/* 251 */       clear();
/* 252 */       this.handle.recycle(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\flow\FlowControlHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */