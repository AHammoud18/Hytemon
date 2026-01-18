/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutorChooserFactory;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ThreadFactory;
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
/*     */ public class MultiThreadIoEventLoopGroup
/*     */   extends MultithreadEventLoopGroup
/*     */   implements IoEventLoopGroup
/*     */ {
/*     */   public MultiThreadIoEventLoopGroup(IoHandlerFactory ioHandlerFactory) {
/*  42 */     this(0, ioHandlerFactory);
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
/*     */   public MultiThreadIoEventLoopGroup(int nThreads, IoHandlerFactory ioHandlerFactory) {
/*  54 */     this(nThreads, (Executor)null, ioHandlerFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiThreadIoEventLoopGroup(ThreadFactory threadFactory, IoHandlerFactory ioHandlerFactory) {
/*  65 */     this(0, threadFactory, ioHandlerFactory);
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
/*     */   public MultiThreadIoEventLoopGroup(Executor executor, IoHandlerFactory ioHandlerFactory) {
/*  78 */     super(0, executor, new Object[] { ioHandlerFactory });
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
/*     */   public MultiThreadIoEventLoopGroup(int nThreads, Executor executor, IoHandlerFactory ioHandlerFactory) {
/*  91 */     super(nThreads, executor, new Object[] { ioHandlerFactory });
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
/*     */   public MultiThreadIoEventLoopGroup(int nThreads, ThreadFactory threadFactory, IoHandlerFactory ioHandlerFactory) {
/* 104 */     super(nThreads, threadFactory, new Object[] { ioHandlerFactory });
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
/*     */   public MultiThreadIoEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, IoHandlerFactory ioHandlerFactory) {
/* 121 */     super(nThreads, executor, chooserFactory, new Object[] { ioHandlerFactory });
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
/*     */   protected MultiThreadIoEventLoopGroup(int nThreads, Executor executor, IoHandlerFactory ioHandlerFactory, Object... args) {
/* 135 */     super(nThreads, executor, combine(ioHandlerFactory, args));
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
/*     */   protected MultiThreadIoEventLoopGroup(int nThreads, ThreadFactory threadFactory, IoHandlerFactory ioHandlerFactory, Object... args) {
/* 149 */     super(nThreads, threadFactory, combine(ioHandlerFactory, args));
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
/*     */   protected MultiThreadIoEventLoopGroup(int nThreads, ThreadFactory threadFactory, IoHandlerFactory ioHandlerFactory, EventExecutorChooserFactory chooserFactory, Object... args) {
/* 166 */     super(nThreads, threadFactory, new Object[] { chooserFactory, combine(ioHandlerFactory, args) });
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
/*     */   protected MultiThreadIoEventLoopGroup(int nThreads, Executor executor, IoHandlerFactory ioHandlerFactory, EventExecutorChooserFactory chooserFactory, Object... args) {
/* 183 */     super(nThreads, executor, chooserFactory, combine(ioHandlerFactory, args));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected EventLoop newChild(Executor executor, Object... args) throws Exception {
/*     */     Object[] argsCopy;
/* 190 */     IoHandlerFactory handlerFactory = (IoHandlerFactory)args[0];
/*     */     
/* 192 */     if (args.length > 1) {
/* 193 */       argsCopy = new Object[args.length - 1];
/* 194 */       System.arraycopy(args, 1, argsCopy, 0, argsCopy.length);
/*     */     } else {
/* 196 */       argsCopy = EmptyArrays.EMPTY_OBJECTS;
/*     */     } 
/* 198 */     return newChild(executor, handlerFactory, argsCopy);
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
/*     */   protected IoEventLoop newChild(Executor executor, IoHandlerFactory ioHandlerFactory, Object... args) {
/* 212 */     return new SingleThreadIoEventLoop(this, executor, ioHandlerFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public IoEventLoop next() {
/* 217 */     return (IoEventLoop)super.next();
/*     */   }
/*     */   
/*     */   private static Object[] combine(IoHandlerFactory handlerFactory, Object... args) {
/* 221 */     List<Object> combinedList = new ArrayList();
/* 222 */     combinedList.add(handlerFactory);
/* 223 */     if (args != null) {
/* 224 */       Collections.addAll(combinedList, args);
/*     */     }
/* 226 */     return combinedList.toArray(new Object[0]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\MultiThreadIoEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */