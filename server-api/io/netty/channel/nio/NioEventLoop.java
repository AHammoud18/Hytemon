/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.EventLoopTaskQueueFactory;
/*     */ import io.netty.channel.IoHandler;
/*     */ import io.netty.channel.IoHandlerFactory;
/*     */ import io.netty.channel.IoRegistration;
/*     */ import io.netty.channel.SingleThreadEventLoop;
/*     */ import io.netty.channel.SingleThreadIoEventLoop;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandler;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
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
/*     */ public final class NioEventLoop
/*     */   extends SingleThreadIoEventLoop
/*     */ {
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
/*     */ 
/*     */ 
/*     */   
/*     */   NioEventLoop(NioEventLoopGroup parent, Executor executor, IoHandlerFactory ioHandlerFactory, EventLoopTaskQueueFactory taskQueueFactory, EventLoopTaskQueueFactory tailTaskQueueFactory, RejectedExecutionHandler rejectedExecutionHandler) {
/*  52 */     super(parent, executor, ioHandlerFactory, newTaskQueue(taskQueueFactory), newTaskQueue(tailTaskQueueFactory), rejectedExecutionHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Queue<Runnable> newTaskQueue(EventLoopTaskQueueFactory queueFactory) {
/*  58 */     if (queueFactory == null) {
/*  59 */       return newTaskQueue0(DEFAULT_MAX_PENDING_TASKS);
/*     */     }
/*  61 */     return queueFactory.newTaskQueue(DEFAULT_MAX_PENDING_TASKS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectorProvider selectorProvider() {
/*  68 */     return ((NioIoHandler)ioHandler()).selectorProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(final SelectableChannel ch, final int interestOps, NioTask<?> task) {
/*  77 */     ObjectUtil.checkNotNull(ch, "ch");
/*  78 */     if (interestOps == 0) {
/*  79 */       throw new IllegalArgumentException("interestOps must be non-zero.");
/*     */     }
/*  81 */     if ((interestOps & (ch.validOps() ^ 0xFFFFFFFF)) != 0) {
/*  82 */       throw new IllegalArgumentException("invalid interestOps: " + interestOps + "(validOps: " + ch
/*  83 */           .validOps() + ')');
/*     */     }
/*  85 */     ObjectUtil.checkNotNull(task, "task");
/*     */     
/*  87 */     if (isShutdown()) {
/*  88 */       throw new IllegalStateException("event loop shut down");
/*     */     }
/*     */ 
/*     */     
/*  92 */     final NioTask<SelectableChannel> nioTask = (NioTask)task;
/*  93 */     if (inEventLoop()) {
/*  94 */       register0(ch, interestOps, nioTask);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*  99 */         submit(new Runnable()
/*     */             {
/*     */               public void run() {
/* 102 */                 NioEventLoop.this.register0(ch, interestOps, nioTask);
/*     */               }
/* 104 */             }).sync();
/* 105 */       } catch (InterruptedException ignore) {
/*     */         
/* 107 */         Thread.currentThread().interrupt();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void register0(SelectableChannel ch, int interestOps, final NioTask<SelectableChannel> task) {
/*     */     try {
/* 133 */       IoRegistration registration = (IoRegistration)register(new NioSelectableChannelIoHandle<SelectableChannel>(ch) { protected void handle(SelectableChannel channel, SelectionKey key) { try { task.channelReady(channel, key); } catch (Exception e) { NioEventLoop.logger.warn("Unexpected exception while running NioTask.channelReady(...)", e); }  } protected void deregister(SelectableChannel channel) { try { task.channelUnregistered(channel, null); } catch (Exception e) { NioEventLoop.logger.warn("Unexpected exception while running NioTask.channelUnregistered(...)", e); }  } }).get();
/* 134 */       registration.submit(NioIoOps.valueOf(interestOps));
/* 135 */     } catch (Exception e) {
/* 136 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIoRatio() {
/* 144 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setIoRatio(int ioRatio) {
/* 154 */     logger.debug("NioEventLoop.setIoRatio(int) logic was removed, this is a no-op");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebuildSelector() {
/* 162 */     if (!inEventLoop()) {
/* 163 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 166 */               ((NioIoHandler)NioEventLoop.this.ioHandler()).rebuildSelector0();
/*     */             }
/*     */           });
/*     */       return;
/*     */     } 
/* 171 */     ((NioIoHandler)ioHandler()).rebuildSelector0();
/*     */   }
/*     */ 
/*     */   
/*     */   public int registeredChannels() {
/* 176 */     return ((NioIoHandler)ioHandler()).numRegistered();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Channel> registeredChannelsIterator() {
/* 181 */     assert inEventLoop();
/* 182 */     final Set<SelectionKey> keys = ((NioIoHandler)ioHandler()).registeredSet();
/* 183 */     if (keys.isEmpty()) {
/* 184 */       return SingleThreadEventLoop.ChannelsReadOnlyIterator.empty();
/*     */     }
/* 186 */     return new Iterator<Channel>()
/*     */       {
/* 188 */         final Iterator<SelectionKey> selectionKeyIterator = ((Set<SelectionKey>)ObjectUtil.checkNotNull(keys, "selectionKeys"))
/* 189 */           .iterator();
/*     */         
/*     */         Channel next;
/*     */         boolean isDone;
/*     */         
/*     */         public boolean hasNext() {
/* 195 */           if (this.isDone) {
/* 196 */             return false;
/*     */           }
/* 198 */           Channel cur = this.next;
/* 199 */           if (cur == null) {
/* 200 */             cur = this.next = nextOrDone();
/* 201 */             return (cur != null);
/*     */           } 
/* 203 */           return true;
/*     */         }
/*     */ 
/*     */         
/*     */         public Channel next() {
/* 208 */           if (this.isDone) {
/* 209 */             throw new NoSuchElementException();
/*     */           }
/* 211 */           Channel cur = this.next;
/* 212 */           if (cur == null) {
/* 213 */             cur = nextOrDone();
/* 214 */             if (cur == null) {
/* 215 */               throw new NoSuchElementException();
/*     */             }
/*     */           } 
/* 218 */           this.next = nextOrDone();
/* 219 */           return cur;
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 224 */           throw new UnsupportedOperationException("remove");
/*     */         }
/*     */         
/*     */         private Channel nextOrDone() {
/* 228 */           Iterator<SelectionKey> it = this.selectionKeyIterator;
/* 229 */           while (it.hasNext()) {
/* 230 */             SelectionKey key = it.next();
/* 231 */             if (key.isValid()) {
/* 232 */               Object attachment = key.attachment();
/* 233 */               if (attachment instanceof NioIoHandler.DefaultNioRegistration) {
/* 234 */                 NioIoHandle handle = ((NioIoHandler.DefaultNioRegistration)attachment).handle();
/* 235 */                 if (handle instanceof AbstractNioChannel.AbstractNioUnsafe) {
/* 236 */                   return ((AbstractNioChannel.AbstractNioUnsafe)handle).channel();
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/* 241 */           this.isDone = true;
/* 242 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   Selector unwrappedSelector() {
/* 248 */     return ((NioIoHandler)ioHandler()).unwrappedSelector();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\NioEventLoop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */