/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.DefaultSelectStrategyFactory;
/*     */ import io.netty.channel.IoHandle;
/*     */ import io.netty.channel.IoHandler;
/*     */ import io.netty.channel.IoHandlerContext;
/*     */ import io.netty.channel.IoHandlerFactory;
/*     */ import io.netty.channel.IoOps;
/*     */ import io.netty.channel.IoRegistration;
/*     */ import io.netty.channel.SelectStrategy;
/*     */ import io.netty.channel.SelectStrategyFactory;
/*     */ import io.netty.util.IntSupplier;
/*     */ import io.netty.util.concurrent.ThreadAwareExecutor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.ReflectionUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NioIoHandler
/*     */   implements IoHandler
/*     */ {
/*  59 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioIoHandler.class);
/*     */ 
/*     */   
/*     */   private static final int CLEANUP_INTERVAL = 256;
/*     */   
/*  64 */   private static final boolean DISABLE_KEY_SET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
/*     */   
/*     */   private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
/*     */   private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
/*     */   
/*  69 */   private final IntSupplier selectNowSupplier = new IntSupplier()
/*     */     {
/*     */       public int get() throws Exception {
/*  72 */         return NioIoHandler.this.selectNow();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private Selector selector;
/*     */   private Selector unwrappedSelector;
/*     */   private SelectedSelectionKeySet selectedKeys;
/*     */   private final SelectorProvider provider;
/*     */   
/*     */   static {
/*  83 */     int selectorAutoRebuildThreshold = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
/*  84 */     if (selectorAutoRebuildThreshold < 3) {
/*  85 */       selectorAutoRebuildThreshold = 0;
/*     */     }
/*     */     
/*  88 */     SELECTOR_AUTO_REBUILD_THRESHOLD = selectorAutoRebuildThreshold;
/*     */     
/*  90 */     if (logger.isDebugEnabled()) {
/*  91 */       logger.debug("-Dio.netty.noKeySetOptimization: {}", Boolean.valueOf(DISABLE_KEY_SET_OPTIMIZATION));
/*  92 */       logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", Integer.valueOf(SELECTOR_AUTO_REBUILD_THRESHOLD));
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
/* 111 */   private final AtomicBoolean wakenUp = new AtomicBoolean();
/*     */   
/*     */   private final SelectStrategy selectStrategy;
/*     */   
/*     */   private final ThreadAwareExecutor executor;
/*     */   private int cancelledKeys;
/*     */   private boolean needsToSelectAgain;
/*     */   
/*     */   private NioIoHandler(ThreadAwareExecutor executor, SelectorProvider selectorProvider, SelectStrategy strategy) {
/* 120 */     this.executor = (ThreadAwareExecutor)ObjectUtil.checkNotNull(executor, "executionContext");
/* 121 */     this.provider = (SelectorProvider)ObjectUtil.checkNotNull(selectorProvider, "selectorProvider");
/* 122 */     this.selectStrategy = (SelectStrategy)ObjectUtil.checkNotNull(strategy, "selectStrategy");
/* 123 */     SelectorTuple selectorTuple = openSelector();
/* 124 */     this.selector = selectorTuple.selector;
/* 125 */     this.unwrappedSelector = selectorTuple.unwrappedSelector;
/*     */   }
/*     */   
/*     */   private static final class SelectorTuple {
/*     */     final Selector unwrappedSelector;
/*     */     final Selector selector;
/*     */     
/*     */     SelectorTuple(Selector unwrappedSelector) {
/* 133 */       this.unwrappedSelector = unwrappedSelector;
/* 134 */       this.selector = unwrappedSelector;
/*     */     }
/*     */     
/*     */     SelectorTuple(Selector unwrappedSelector, Selector selector) {
/* 138 */       this.unwrappedSelector = unwrappedSelector;
/* 139 */       this.selector = selector;
/*     */     }
/*     */   }
/*     */   
/*     */   private SelectorTuple openSelector() {
/*     */     final Selector unwrappedSelector;
/*     */     try {
/* 146 */       unwrappedSelector = this.provider.openSelector();
/* 147 */     } catch (IOException e) {
/* 148 */       throw new ChannelException("failed to open a new selector", e);
/*     */     } 
/*     */     
/* 151 */     if (DISABLE_KEY_SET_OPTIMIZATION) {
/* 152 */       return new SelectorTuple(unwrappedSelector);
/*     */     }
/*     */     
/* 155 */     Object maybeSelectorImplClass = AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/*     */             try {
/* 159 */               return Class.forName("sun.nio.ch.SelectorImpl", false, 
/*     */ 
/*     */                   
/* 162 */                   PlatformDependent.getSystemClassLoader());
/* 163 */             } catch (Throwable cause) {
/* 164 */               return cause;
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 169 */     if (!(maybeSelectorImplClass instanceof Class) || 
/*     */       
/* 171 */       !((Class)maybeSelectorImplClass).isAssignableFrom(unwrappedSelector.getClass())) {
/* 172 */       if (maybeSelectorImplClass instanceof Throwable) {
/* 173 */         Throwable t = (Throwable)maybeSelectorImplClass;
/* 174 */         logger.trace("failed to instrument a special java.util.Set into: {}", unwrappedSelector, t);
/*     */       } 
/* 176 */       return new SelectorTuple(unwrappedSelector);
/*     */     } 
/*     */     
/* 179 */     final Class<?> selectorImplClass = (Class)maybeSelectorImplClass;
/* 180 */     final SelectedSelectionKeySet selectedKeySet = new SelectedSelectionKeySet();
/*     */     
/* 182 */     Object maybeException = AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/*     */             try {
/* 186 */               Field selectedKeysField = selectorImplClass.getDeclaredField("selectedKeys");
/* 187 */               Field publicSelectedKeysField = selectorImplClass.getDeclaredField("publicSelectedKeys");
/*     */               
/* 189 */               if (PlatformDependent.javaVersion() >= 9 && PlatformDependent.hasUnsafe()) {
/*     */ 
/*     */                 
/* 192 */                 long selectedKeysFieldOffset = PlatformDependent.objectFieldOffset(selectedKeysField);
/*     */                 
/* 194 */                 long publicSelectedKeysFieldOffset = PlatformDependent.objectFieldOffset(publicSelectedKeysField);
/*     */                 
/* 196 */                 if (selectedKeysFieldOffset != -1L && publicSelectedKeysFieldOffset != -1L) {
/* 197 */                   PlatformDependent.putObject(unwrappedSelector, selectedKeysFieldOffset, selectedKeySet);
/*     */                   
/* 199 */                   PlatformDependent.putObject(unwrappedSelector, publicSelectedKeysFieldOffset, selectedKeySet);
/*     */                   
/* 201 */                   return null;
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/* 206 */               Throwable cause = ReflectionUtil.trySetAccessible(selectedKeysField, true);
/* 207 */               if (cause != null) {
/* 208 */                 return cause;
/*     */               }
/* 210 */               cause = ReflectionUtil.trySetAccessible(publicSelectedKeysField, true);
/* 211 */               if (cause != null) {
/* 212 */                 return cause;
/*     */               }
/*     */               
/* 215 */               selectedKeysField.set(unwrappedSelector, selectedKeySet);
/* 216 */               publicSelectedKeysField.set(unwrappedSelector, selectedKeySet);
/* 217 */               return null;
/* 218 */             } catch (NoSuchFieldException|IllegalAccessException e) {
/* 219 */               return e;
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 224 */     if (maybeException instanceof Exception) {
/* 225 */       this.selectedKeys = null;
/* 226 */       Exception e = (Exception)maybeException;
/* 227 */       logger.trace("failed to instrument a special java.util.Set into: {}", unwrappedSelector, e);
/* 228 */       return new SelectorTuple(unwrappedSelector);
/*     */     } 
/* 230 */     this.selectedKeys = selectedKeySet;
/* 231 */     logger.trace("instrumented a special java.util.Set into: {}", unwrappedSelector);
/* 232 */     return new SelectorTuple(unwrappedSelector, new SelectedSelectionKeySetSelector(unwrappedSelector, selectedKeySet));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectorProvider selectorProvider() {
/* 240 */     return this.provider;
/*     */   }
/*     */   
/*     */   Selector selector() {
/* 244 */     return this.selector;
/*     */   }
/*     */   
/*     */   int numRegistered() {
/* 248 */     return selector().keys().size() - this.cancelledKeys;
/*     */   }
/*     */   
/*     */   Set<SelectionKey> registeredSet() {
/* 252 */     return selector().keys();
/*     */   }
/*     */   void rebuildSelector0() {
/*     */     SelectorTuple newSelectorTuple;
/* 256 */     Selector oldSelector = this.selector;
/*     */ 
/*     */     
/* 259 */     if (oldSelector == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 264 */       newSelectorTuple = openSelector();
/* 265 */     } catch (Exception e) {
/* 266 */       logger.warn("Failed to create a new Selector.", e);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 271 */     int nChannels = 0;
/* 272 */     for (SelectionKey key : oldSelector.keys()) {
/* 273 */       DefaultNioRegistration handle = (DefaultNioRegistration)key.attachment();
/*     */       try {
/* 275 */         if (!key.isValid() || key.channel().keyFor(newSelectorTuple.unwrappedSelector) != null) {
/*     */           continue;
/*     */         }
/*     */         
/* 279 */         handle.register(newSelectorTuple.unwrappedSelector);
/* 280 */         nChannels++;
/* 281 */       } catch (Exception e) {
/* 282 */         logger.warn("Failed to re-register a NioHandle to the new Selector.", e);
/* 283 */         handle.cancel();
/*     */       } 
/*     */     } 
/*     */     
/* 287 */     this.selector = newSelectorTuple.selector;
/* 288 */     this.unwrappedSelector = newSelectorTuple.unwrappedSelector;
/*     */ 
/*     */     
/*     */     try {
/* 292 */       oldSelector.close();
/* 293 */     } catch (Throwable t) {
/* 294 */       if (logger.isWarnEnabled()) {
/* 295 */         logger.warn("Failed to close the old Selector.", t);
/*     */       }
/*     */     } 
/*     */     
/* 299 */     if (logger.isInfoEnabled()) {
/* 300 */       logger.info("Migrated " + nChannels + " channel(s) to the new Selector.");
/*     */     }
/*     */   }
/*     */   
/*     */   private static NioIoHandle nioHandle(IoHandle handle) {
/* 305 */     if (handle instanceof NioIoHandle) {
/* 306 */       return (NioIoHandle)handle;
/*     */     }
/* 308 */     throw new IllegalArgumentException("IoHandle of type " + StringUtil.simpleClassName(handle) + " not supported");
/*     */   }
/*     */   
/*     */   private static NioIoOps cast(IoOps ops) {
/* 312 */     if (ops instanceof NioIoOps) {
/* 313 */       return (NioIoOps)ops;
/*     */     }
/* 315 */     throw new IllegalArgumentException("IoOps of type " + StringUtil.simpleClassName(ops) + " not supported");
/*     */   }
/*     */   
/*     */   final class DefaultNioRegistration implements IoRegistration {
/* 319 */     private final AtomicBoolean canceled = new AtomicBoolean();
/*     */     
/*     */     private final NioIoHandle handle;
/*     */     private volatile SelectionKey key;
/*     */     
/*     */     DefaultNioRegistration(ThreadAwareExecutor executor, NioIoHandle handle, NioIoOps initialOps, Selector selector) throws IOException {
/* 325 */       this.handle = handle;
/* 326 */       this.key = handle.selectableChannel().register(selector, initialOps.value, this);
/*     */     }
/*     */     
/*     */     NioIoHandle handle() {
/* 330 */       return this.handle;
/*     */     }
/*     */     
/*     */     void register(Selector selector) throws IOException {
/* 334 */       SelectionKey newKey = this.handle.selectableChannel().register(selector, this.key.interestOps(), this);
/* 335 */       this.key.cancel();
/* 336 */       this.key = newKey;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T attachment() {
/* 342 */       return (T)this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 347 */       return (!this.canceled.get() && this.key.isValid());
/*     */     }
/*     */ 
/*     */     
/*     */     public long submit(IoOps ops) {
/* 352 */       if (!isValid()) {
/* 353 */         return -1L;
/*     */       }
/* 355 */       int v = (NioIoHandler.cast(ops)).value;
/* 356 */       this.key.interestOps(v);
/* 357 */       return v;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancel() {
/* 362 */       if (!this.canceled.compareAndSet(false, true)) {
/* 363 */         return false;
/*     */       }
/* 365 */       this.key.cancel();
/* 366 */       NioIoHandler.this.cancelledKeys++;
/* 367 */       if (NioIoHandler.this.cancelledKeys >= 256) {
/* 368 */         NioIoHandler.this.cancelledKeys = 0;
/* 369 */         NioIoHandler.this.needsToSelectAgain = true;
/*     */       } 
/* 371 */       this.handle.unregistered();
/* 372 */       return true;
/*     */     }
/*     */     
/*     */     void close() {
/* 376 */       cancel();
/*     */       try {
/* 378 */         this.handle.close();
/* 379 */       } catch (Exception e) {
/* 380 */         NioIoHandler.logger.debug("Exception during closing " + this.handle, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     void handle(int ready) {
/* 385 */       if (!isValid()) {
/*     */         return;
/*     */       }
/* 388 */       this.handle.handle(this, NioIoOps.eventOf(ready));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IoRegistration register(IoHandle handle) throws Exception {
/* 395 */     NioIoHandle nioHandle = nioHandle(handle);
/* 396 */     NioIoOps ops = NioIoOps.NONE;
/* 397 */     boolean selected = false;
/*     */     while (true) {
/*     */       try {
/* 400 */         IoRegistration registration = new DefaultNioRegistration(this.executor, nioHandle, ops, unwrappedSelector());
/* 401 */         handle.registered();
/* 402 */         return registration;
/* 403 */       } catch (CancelledKeyException e) {
/* 404 */         if (!selected) {
/*     */ 
/*     */           
/* 407 */           selectNow();
/* 408 */           selected = true; continue;
/*     */         }  break;
/*     */       } 
/*     */     } 
/* 412 */     throw e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int run(IoHandlerContext context) {
/* 420 */     int handled = 0;
/*     */ 
/*     */     
/* 423 */     try { switch (this.selectStrategy.calculateStrategy(this.selectNowSupplier, !context.canBlock())) {
/*     */         case -2:
/* 425 */           if (context.shouldReportActiveIoTime()) {
/* 426 */             context.reportActiveIoTime(0L);
/*     */           }
/* 428 */           return 0;
/*     */ 
/*     */ 
/*     */         
/*     */         case -3:
/*     */         case -1:
/* 434 */           select(context, this.wakenUp.getAndSet(false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 464 */           if (this.wakenUp.get()) {
/* 465 */             this.selector.wakeup();
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 478 */       this.cancelledKeys = 0;
/* 479 */       this.needsToSelectAgain = false;
/*     */       
/* 481 */       if (context.shouldReportActiveIoTime())
/*     */       
/* 483 */       { long activeIoStartTimeNanos = System.nanoTime();
/* 484 */         handled = processSelectedKeys();
/* 485 */         long activeIoEndTimeNanos = System.nanoTime();
/* 486 */         context.reportActiveIoTime(activeIoEndTimeNanos - activeIoStartTimeNanos); }
/*     */       else
/* 488 */       { handled = processSelectedKeys(); }  }
/*     */     catch (IOException e) { rebuildSelector0(); handleLoopException(e); return 0; }
/* 490 */     catch (Error e)
/* 491 */     { throw e; }
/* 492 */     catch (Throwable t)
/* 493 */     { handleLoopException(t); }
/*     */     
/* 495 */     return handled;
/*     */   }
/*     */   
/*     */   private static void handleLoopException(Throwable t) {
/* 499 */     logger.warn("Unexpected exception in the selector loop.", t);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 504 */       Thread.sleep(1000L);
/* 505 */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int processSelectedKeys() {
/* 511 */     if (this.selectedKeys != null) {
/* 512 */       return processSelectedKeysOptimized();
/*     */     }
/* 514 */     return processSelectedKeysPlain(this.selector.selectedKeys());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/*     */     try {
/* 521 */       this.selector.close();
/* 522 */     } catch (IOException e) {
/* 523 */       logger.warn("Failed to close a selector.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int processSelectedKeysPlain(Set<SelectionKey> selectedKeys) {
/* 531 */     if (selectedKeys.isEmpty()) {
/* 532 */       return 0;
/*     */     }
/*     */     
/* 535 */     Iterator<SelectionKey> i = selectedKeys.iterator();
/* 536 */     int handled = 0;
/*     */     while (true) {
/* 538 */       SelectionKey k = i.next();
/* 539 */       i.remove();
/*     */       
/* 541 */       processSelectedKey(k);
/* 542 */       handled++;
/*     */       
/* 544 */       if (!i.hasNext()) {
/*     */         break;
/*     */       }
/*     */       
/* 548 */       if (this.needsToSelectAgain) {
/* 549 */         selectAgain();
/* 550 */         selectedKeys = this.selector.selectedKeys();
/*     */ 
/*     */         
/* 553 */         if (selectedKeys.isEmpty()) {
/*     */           break;
/*     */         }
/* 556 */         i = selectedKeys.iterator();
/*     */       } 
/*     */     } 
/*     */     
/* 560 */     return handled;
/*     */   }
/*     */   
/*     */   private int processSelectedKeysOptimized() {
/* 564 */     int handled = 0;
/* 565 */     for (int i = 0; i < this.selectedKeys.size; i++) {
/* 566 */       SelectionKey k = this.selectedKeys.keys[i];
/*     */ 
/*     */       
/* 569 */       this.selectedKeys.keys[i] = null;
/*     */       
/* 571 */       processSelectedKey(k);
/* 572 */       handled++;
/*     */       
/* 574 */       if (this.needsToSelectAgain) {
/*     */ 
/*     */         
/* 577 */         this.selectedKeys.reset(i + 1);
/*     */         
/* 579 */         selectAgain();
/* 580 */         i = -1;
/*     */       } 
/*     */     } 
/* 583 */     return handled;
/*     */   }
/*     */   
/*     */   private void processSelectedKey(SelectionKey k) {
/* 587 */     DefaultNioRegistration registration = (DefaultNioRegistration)k.attachment();
/* 588 */     if (!registration.isValid()) {
/*     */       try {
/* 590 */         registration.handle.close();
/* 591 */       } catch (Exception e) {
/* 592 */         logger.debug("Exception during closing " + registration.handle, e);
/*     */       } 
/*     */       return;
/*     */     } 
/* 596 */     registration.handle(k.readyOps());
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareToDestroy() {
/* 601 */     selectAgain();
/* 602 */     Set<SelectionKey> keys = this.selector.keys();
/* 603 */     Collection<DefaultNioRegistration> registrations = new ArrayList<>(keys.size());
/* 604 */     for (SelectionKey k : keys) {
/* 605 */       DefaultNioRegistration handle = (DefaultNioRegistration)k.attachment();
/* 606 */       registrations.add(handle);
/*     */     } 
/*     */     
/* 609 */     for (DefaultNioRegistration reg : registrations) {
/* 610 */       reg.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void wakeup() {
/* 616 */     if (!this.executor.isExecutorThread(Thread.currentThread()) && this.wakenUp.compareAndSet(false, true)) {
/* 617 */       this.selector.wakeup();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompatible(Class<? extends IoHandle> handleType) {
/* 623 */     return NioIoHandle.class.isAssignableFrom(handleType);
/*     */   }
/*     */   
/*     */   Selector unwrappedSelector() {
/* 627 */     return this.unwrappedSelector;
/*     */   }
/*     */   
/*     */   private void select(IoHandlerContext runner, boolean oldWakenUp) throws IOException {
/* 631 */     Selector selector = this.selector;
/*     */     try {
/* 633 */       int selectCnt = 0;
/* 634 */       long currentTimeNanos = System.nanoTime();
/* 635 */       long delayNanos = runner.delayNanos(currentTimeNanos);
/*     */ 
/*     */       
/* 638 */       long selectDeadLineNanos = Long.MAX_VALUE;
/* 639 */       if (delayNanos != Long.MAX_VALUE) {
/* 640 */         selectDeadLineNanos = currentTimeNanos + runner.delayNanos(currentTimeNanos);
/*     */       }
/*     */       while (true) {
/*     */         long timeoutMillis;
/* 644 */         if (delayNanos != Long.MAX_VALUE) {
/* 645 */           long millisBeforeDeadline = millisBeforeDeadline(selectDeadLineNanos, currentTimeNanos);
/* 646 */           if (millisBeforeDeadline <= 0L) {
/* 647 */             if (selectCnt == 0) {
/* 648 */               selector.selectNow();
/* 649 */               selectCnt = 1;
/*     */             } 
/*     */             break;
/*     */           } 
/* 653 */           timeoutMillis = millisBeforeDeadline;
/*     */         } else {
/*     */           
/* 656 */           timeoutMillis = 0L;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 662 */         if (!runner.canBlock() && this.wakenUp.compareAndSet(false, true)) {
/* 663 */           selector.selectNow();
/* 664 */           selectCnt = 1;
/*     */           
/*     */           break;
/*     */         } 
/* 668 */         int selectedKeys = selector.select(timeoutMillis);
/* 669 */         selectCnt++;
/*     */         
/* 671 */         if (selectedKeys != 0 || oldWakenUp || this.wakenUp.get() || !runner.canBlock()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 678 */         if (Thread.interrupted()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 684 */           if (logger.isDebugEnabled()) {
/* 685 */             logger.debug("Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioHandler.shutdownGracefully() to shutdown the NioHandler.");
/*     */           }
/*     */ 
/*     */           
/* 689 */           selectCnt = 1;
/*     */           
/*     */           break;
/*     */         } 
/* 693 */         long time = System.nanoTime();
/* 694 */         if (time - TimeUnit.MILLISECONDS.toNanos(timeoutMillis) >= currentTimeNanos) {
/*     */           
/* 696 */           selectCnt = 1;
/* 697 */         } else if (SELECTOR_AUTO_REBUILD_THRESHOLD > 0 && selectCnt >= SELECTOR_AUTO_REBUILD_THRESHOLD) {
/*     */ 
/*     */ 
/*     */           
/* 701 */           selector = selectRebuildSelector(selectCnt);
/* 702 */           selectCnt = 1;
/*     */           
/*     */           break;
/*     */         } 
/* 706 */         currentTimeNanos = time;
/*     */       } 
/*     */       
/* 709 */       if (selectCnt > 3 && 
/* 710 */         logger.isDebugEnabled()) {
/* 711 */         logger.debug("Selector.select() returned prematurely {} times in a row for Selector {}.", 
/* 712 */             Integer.valueOf(selectCnt - 1), selector);
/*     */       }
/*     */     }
/* 715 */     catch (CancelledKeyException e) {
/* 716 */       if (logger.isDebugEnabled()) {
/* 717 */         logger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector {} - JDK bug?", selector, e);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long millisBeforeDeadline(long selectDeadLineNanos, long currentTimeNanos) {
/* 725 */     assert selectDeadLineNanos != Long.MAX_VALUE;
/* 726 */     long nanosBeforeDeadline = selectDeadLineNanos - currentTimeNanos;
/*     */ 
/*     */     
/* 729 */     if (nanosBeforeDeadline >= 9223372036854275807L) {
/* 730 */       return 9223372036854L;
/*     */     }
/*     */     
/* 733 */     return (nanosBeforeDeadline + 500000L) / 1000000L;
/*     */   }
/*     */   
/*     */   int selectNow() throws IOException {
/*     */     try {
/* 738 */       return this.selector.selectNow();
/*     */     } finally {
/*     */       
/* 741 */       if (this.wakenUp.get()) {
/* 742 */         this.selector.wakeup();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Selector selectRebuildSelector(int selectCnt) throws IOException {
/* 750 */     logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding Selector {}.", 
/*     */         
/* 752 */         Integer.valueOf(selectCnt), this.selector);
/*     */     
/* 754 */     rebuildSelector0();
/* 755 */     Selector selector = this.selector;
/*     */ 
/*     */     
/* 758 */     selector.selectNow();
/* 759 */     return selector;
/*     */   }
/*     */   
/*     */   private void selectAgain() {
/* 763 */     this.needsToSelectAgain = false;
/*     */     try {
/* 765 */       this.selector.selectNow();
/* 766 */     } catch (Throwable t) {
/* 767 */       logger.warn("Failed to update SelectionKeys.", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IoHandlerFactory newFactory() {
/* 777 */     return newFactory(SelectorProvider.provider(), DefaultSelectStrategyFactory.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IoHandlerFactory newFactory(SelectorProvider selectorProvider) {
/* 787 */     return newFactory(selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
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
/*     */   public static IoHandlerFactory newFactory(final SelectorProvider selectorProvider, final SelectStrategyFactory selectStrategyFactory) {
/* 799 */     ObjectUtil.checkNotNull(selectorProvider, "selectorProvider");
/* 800 */     ObjectUtil.checkNotNull(selectStrategyFactory, "selectStrategyFactory");
/* 801 */     return new IoHandlerFactory()
/*     */       {
/*     */         public IoHandler newHandler(ThreadAwareExecutor executor) {
/* 804 */           return new NioIoHandler(executor, selectorProvider, selectStrategyFactory.newSelectStrategy());
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isChangingThreadSupported() {
/* 809 */           return true;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\NioIoHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */