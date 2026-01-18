/*     */ package io.netty.channel.local;
/*     */ 
/*     */ import io.netty.channel.IoHandle;
/*     */ import io.netty.channel.IoHandler;
/*     */ import io.netty.channel.IoHandlerContext;
/*     */ import io.netty.channel.IoHandlerFactory;
/*     */ import io.netty.channel.IoOps;
/*     */ import io.netty.channel.IoRegistration;
/*     */ import io.netty.util.concurrent.ThreadAwareExecutor;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.HashSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.locks.LockSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LocalIoHandler
/*     */   implements IoHandler
/*     */ {
/*  34 */   private final Set<LocalIoHandle> registeredChannels = new HashSet<>(64);
/*     */   private final ThreadAwareExecutor executor;
/*     */   private volatile Thread executionThread;
/*     */   
/*     */   private LocalIoHandler(ThreadAwareExecutor executor) {
/*  39 */     this.executor = Objects.<ThreadAwareExecutor>requireNonNull(executor, "executor");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IoHandlerFactory newFactory() {
/*  46 */     return LocalIoHandler::new;
/*     */   }
/*     */   
/*     */   private static LocalIoHandle cast(IoHandle handle) {
/*  50 */     if (handle instanceof LocalIoHandle) {
/*  51 */       return (LocalIoHandle)handle;
/*     */     }
/*  53 */     throw new IllegalArgumentException("IoHandle of type " + StringUtil.simpleClassName(handle) + " not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public int run(IoHandlerContext context) {
/*  58 */     if (this.executionThread == null) {
/*  59 */       this.executionThread = Thread.currentThread();
/*     */     }
/*  61 */     if (context.canBlock())
/*     */     {
/*  63 */       LockSupport.parkNanos(this, context.delayNanos(System.nanoTime()));
/*     */     }
/*     */     
/*  66 */     if (context.shouldReportActiveIoTime()) {
/*  67 */       context.reportActiveIoTime(0L);
/*     */     }
/*  69 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void wakeup() {
/*  74 */     if (!this.executor.isExecutorThread(Thread.currentThread())) {
/*  75 */       Thread thread = this.executionThread;
/*  76 */       if (thread != null)
/*     */       {
/*  78 */         LockSupport.unpark(thread);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareToDestroy() {
/*  85 */     for (LocalIoHandle handle : this.registeredChannels) {
/*  86 */       handle.closeNow();
/*     */     }
/*  88 */     this.registeredChannels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {}
/*     */ 
/*     */   
/*     */   public IoRegistration register(IoHandle handle) {
/*  97 */     LocalIoHandle localHandle = cast(handle);
/*  98 */     if (this.registeredChannels.add(localHandle)) {
/*  99 */       LocalIoRegistration registration = new LocalIoRegistration(this.executor, localHandle);
/* 100 */       localHandle.registered();
/* 101 */       return registration;
/*     */     } 
/* 103 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompatible(Class<? extends IoHandle> handleType) {
/* 108 */     return LocalIoHandle.class.isAssignableFrom(handleType);
/*     */   }
/*     */   
/*     */   private final class LocalIoRegistration implements IoRegistration {
/* 112 */     private final AtomicBoolean canceled = new AtomicBoolean();
/*     */     private final ThreadAwareExecutor executor;
/*     */     private final LocalIoHandle handle;
/*     */     
/*     */     LocalIoRegistration(ThreadAwareExecutor executor, LocalIoHandle handle) {
/* 117 */       this.executor = executor;
/* 118 */       this.handle = handle;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T attachment() {
/* 123 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public long submit(IoOps ops) {
/* 128 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 133 */       return !this.canceled.get();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancel() {
/* 138 */       if (!this.canceled.compareAndSet(false, true)) {
/* 139 */         return false;
/*     */       }
/* 141 */       if (this.executor.isExecutorThread(Thread.currentThread())) {
/* 142 */         cancel0();
/*     */       } else {
/* 144 */         this.executor.execute(this::cancel0);
/*     */       } 
/* 146 */       return true;
/*     */     }
/*     */     
/*     */     private void cancel0() {
/* 150 */       if (LocalIoHandler.this.registeredChannels.remove(this.handle))
/* 151 */         this.handle.unregistered(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\local\LocalIoHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */