/*      */ package io.netty.channel;
/*      */ 
/*      */ import io.netty.util.ReferenceCountUtil;
/*      */ import io.netty.util.ResourceLeakDetector;
/*      */ import io.netty.util.concurrent.EventExecutor;
/*      */ import io.netty.util.concurrent.EventExecutorGroup;
/*      */ import io.netty.util.concurrent.FastThreadLocal;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.net.SocketAddress;
/*      */ import java.util.ArrayList;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.concurrent.RejectedExecutionException;
/*      */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultChannelPipeline
/*      */   implements ChannelPipeline
/*      */ {
/*   47 */   static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
/*      */   
/*   49 */   private static final String HEAD_NAME = generateName0(HeadContext.class);
/*   50 */   private static final String TAIL_NAME = generateName0(TailContext.class);
/*      */   
/*   52 */   private static final FastThreadLocal<Map<Class<?>, String>> nameCaches = new FastThreadLocal<Map<Class<?>, String>>()
/*      */     {
/*      */       protected Map<Class<?>, String> initialValue()
/*      */       {
/*   56 */         return new WeakHashMap<>();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*   61 */   private static final AtomicReferenceFieldUpdater<DefaultChannelPipeline, MessageSizeEstimator.Handle> ESTIMATOR = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelPipeline.class, MessageSizeEstimator.Handle.class, "estimatorHandle");
/*      */   
/*      */   final HeadContext head;
/*      */   
/*      */   final TailContext tail;
/*      */   private final Channel channel;
/*      */   private final ChannelFuture succeededFuture;
/*      */   private final VoidChannelPromise voidPromise;
/*   69 */   private final boolean touch = ResourceLeakDetector.isEnabled();
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<EventExecutorGroup, EventExecutor> childExecutors;
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile MessageSizeEstimator.Handle estimatorHandle;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstRegistration = true;
/*      */ 
/*      */   
/*      */   private PendingHandlerCallback pendingHandlerCallbackHead;
/*      */ 
/*      */   
/*      */   private boolean registered;
/*      */ 
/*      */ 
/*      */   
/*      */   protected DefaultChannelPipeline(Channel channel) {
/*   92 */     this.channel = (Channel)ObjectUtil.checkNotNull(channel, "channel");
/*   93 */     this.succeededFuture = new SucceededChannelFuture(channel, null);
/*   94 */     this.voidPromise = new VoidChannelPromise(channel, true);
/*      */     
/*   96 */     this.tail = new TailContext(this);
/*   97 */     this.head = new HeadContext(this);
/*      */     
/*   99 */     this.head.next = this.tail;
/*  100 */     this.tail.prev = this.head;
/*      */   }
/*      */   
/*      */   final MessageSizeEstimator.Handle estimatorHandle() {
/*  104 */     MessageSizeEstimator.Handle handle = this.estimatorHandle;
/*  105 */     if (handle == null) {
/*  106 */       handle = this.channel.config().getMessageSizeEstimator().newHandle();
/*  107 */       if (!ESTIMATOR.compareAndSet(this, null, handle)) {
/*  108 */         handle = this.estimatorHandle;
/*      */       }
/*      */     } 
/*  111 */     return handle;
/*      */   }
/*      */   
/*      */   final Object touch(Object msg, AbstractChannelHandlerContext next) {
/*  115 */     return this.touch ? ReferenceCountUtil.touch(msg, next) : msg;
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext newContext(EventExecutorGroup group, String name, ChannelHandler handler) {
/*  119 */     return new DefaultChannelHandlerContext(this, childExecutor(group), name, handler);
/*      */   }
/*      */   
/*      */   private EventExecutor childExecutor(EventExecutorGroup group) {
/*  123 */     if (group == null) {
/*  124 */       return null;
/*      */     }
/*  126 */     Boolean pinEventExecutor = this.channel.config().<Boolean>getOption(ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP);
/*  127 */     if (pinEventExecutor != null && !pinEventExecutor.booleanValue()) {
/*  128 */       return group.next();
/*      */     }
/*  130 */     Map<EventExecutorGroup, EventExecutor> childExecutors = this.childExecutors;
/*  131 */     if (childExecutors == null)
/*      */     {
/*  133 */       childExecutors = this.childExecutors = new IdentityHashMap<>(4);
/*      */     }
/*      */ 
/*      */     
/*  137 */     EventExecutor childExecutor = childExecutors.get(group);
/*  138 */     if (childExecutor == null) {
/*  139 */       childExecutor = group.next();
/*  140 */       childExecutors.put(group, childExecutor);
/*      */     } 
/*  142 */     return childExecutor;
/*      */   }
/*      */   
/*      */   public final Channel channel() {
/*  146 */     return this.channel;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addFirst(String name, ChannelHandler handler) {
/*  151 */     return addFirst(null, name, handler);
/*      */   }
/*      */   
/*      */   private enum AddStrategy {
/*  155 */     ADD_FIRST,
/*  156 */     ADD_LAST,
/*  157 */     ADD_BEFORE,
/*  158 */     ADD_AFTER;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ChannelPipeline internalAdd(EventExecutorGroup group, String name, ChannelHandler handler, String baseName, AddStrategy addStrategy) {
/*      */     AbstractChannelHandlerContext newCtx;
/*  165 */     synchronized (this) {
/*  166 */       checkMultiplicity(handler);
/*  167 */       name = filterName(name, handler);
/*      */       
/*  169 */       newCtx = newContext(group, name, handler);
/*      */       
/*  171 */       switch (addStrategy) {
/*      */         case ADD_FIRST:
/*  173 */           addFirst0(newCtx);
/*      */           break;
/*      */         case ADD_LAST:
/*  176 */           addLast0(newCtx);
/*      */           break;
/*      */         case ADD_BEFORE:
/*  179 */           addBefore0(getContextOrDie(baseName), newCtx);
/*      */           break;
/*      */         case ADD_AFTER:
/*  182 */           addAfter0(getContextOrDie(baseName), newCtx);
/*      */           break;
/*      */         default:
/*  185 */           throw new IllegalArgumentException("unknown add strategy: " + addStrategy);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  191 */       if (!this.registered) {
/*  192 */         newCtx.setAddPending();
/*  193 */         callHandlerCallbackLater(newCtx, true);
/*  194 */         return this;
/*      */       } 
/*      */       
/*  197 */       EventExecutor executor = newCtx.executor();
/*  198 */       if (!executor.inEventLoop()) {
/*  199 */         callHandlerAddedInEventLoop(newCtx, executor);
/*  200 */         return this;
/*      */       } 
/*      */     } 
/*  203 */     callHandlerAdded0(newCtx);
/*  204 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addFirst(EventExecutorGroup group, String name, ChannelHandler handler) {
/*  209 */     return internalAdd(group, name, handler, null, AddStrategy.ADD_FIRST);
/*      */   }
/*      */   
/*      */   private void addFirst0(AbstractChannelHandlerContext newCtx) {
/*  213 */     AbstractChannelHandlerContext nextCtx = this.head.next;
/*  214 */     newCtx.prev = this.head;
/*  215 */     newCtx.next = nextCtx;
/*  216 */     this.head.next = newCtx;
/*  217 */     nextCtx.prev = newCtx;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addLast(String name, ChannelHandler handler) {
/*  222 */     return addLast(null, name, handler);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addLast(EventExecutorGroup group, String name, ChannelHandler handler) {
/*  227 */     return internalAdd(group, name, handler, null, AddStrategy.ADD_LAST);
/*      */   }
/*      */   
/*      */   private void addLast0(AbstractChannelHandlerContext newCtx) {
/*  231 */     AbstractChannelHandlerContext prev = this.tail.prev;
/*  232 */     newCtx.prev = prev;
/*  233 */     newCtx.next = this.tail;
/*  234 */     prev.next = newCtx;
/*  235 */     this.tail.prev = newCtx;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addBefore(String baseName, String name, ChannelHandler handler) {
/*  240 */     return addBefore(null, baseName, name, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addBefore(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
/*  246 */     return internalAdd(group, name, handler, baseName, AddStrategy.ADD_BEFORE);
/*      */   }
/*      */   
/*      */   private static void addBefore0(AbstractChannelHandlerContext ctx, AbstractChannelHandlerContext newCtx) {
/*  250 */     newCtx.prev = ctx.prev;
/*  251 */     newCtx.next = ctx;
/*  252 */     ctx.prev.next = newCtx;
/*  253 */     ctx.prev = newCtx;
/*      */   }
/*      */   
/*      */   private String filterName(String name, ChannelHandler handler) {
/*  257 */     if (name == null) {
/*  258 */       return generateName(handler);
/*      */     }
/*  260 */     checkDuplicateName(name);
/*  261 */     return name;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addAfter(String baseName, String name, ChannelHandler handler) {
/*  266 */     return addAfter(null, baseName, name, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addAfter(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
/*  272 */     return internalAdd(group, name, handler, baseName, AddStrategy.ADD_AFTER);
/*      */   }
/*      */   
/*      */   private static void addAfter0(AbstractChannelHandlerContext ctx, AbstractChannelHandlerContext newCtx) {
/*  276 */     newCtx.prev = ctx;
/*  277 */     newCtx.next = ctx.next;
/*  278 */     ctx.next.prev = newCtx;
/*  279 */     ctx.next = newCtx;
/*      */   }
/*      */   
/*      */   public final ChannelPipeline addFirst(ChannelHandler handler) {
/*  283 */     return addFirst((String)null, handler);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addFirst(ChannelHandler... handlers) {
/*  288 */     return addFirst((EventExecutorGroup)null, handlers);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addFirst(EventExecutorGroup executor, ChannelHandler... handlers) {
/*  293 */     ObjectUtil.checkNotNull(handlers, "handlers");
/*  294 */     if (handlers.length == 0 || handlers[0] == null) {
/*  295 */       return this;
/*      */     }
/*      */     
/*      */     int size;
/*  299 */     for (size = 1; size < handlers.length && 
/*  300 */       handlers[size] != null; size++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  305 */     for (int i = size - 1; i >= 0; i--) {
/*  306 */       ChannelHandler h = handlers[i];
/*  307 */       addFirst(executor, null, h);
/*      */     } 
/*      */     
/*  310 */     return this;
/*      */   }
/*      */   
/*      */   public final ChannelPipeline addLast(ChannelHandler handler) {
/*  314 */     return addLast((String)null, handler);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addLast(ChannelHandler... handlers) {
/*  319 */     return addLast((EventExecutorGroup)null, handlers);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline addLast(EventExecutorGroup executor, ChannelHandler... handlers) {
/*  324 */     ObjectUtil.checkNotNull(handlers, "handlers");
/*      */     
/*  326 */     for (ChannelHandler h : handlers) {
/*  327 */       if (h == null) {
/*      */         break;
/*      */       }
/*  330 */       addLast(executor, null, h);
/*      */     } 
/*      */     
/*  333 */     return this;
/*      */   }
/*      */   
/*      */   private String generateName(ChannelHandler handler) {
/*  337 */     Map<Class<?>, String> cache = (Map<Class<?>, String>)nameCaches.get();
/*  338 */     Class<?> handlerType = handler.getClass();
/*  339 */     String name = cache.get(handlerType);
/*  340 */     if (name == null) {
/*  341 */       name = generateName0(handlerType);
/*  342 */       cache.put(handlerType, name);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  347 */     if (context0(name) != null) {
/*  348 */       String baseName = name.substring(0, name.length() - 1);
/*  349 */       for (int i = 1;; i++) {
/*  350 */         String newName = baseName + i;
/*  351 */         if (context0(newName) == null) {
/*  352 */           name = newName;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  357 */     return name;
/*      */   }
/*      */   
/*      */   private static String generateName0(Class<?> handlerType) {
/*  361 */     return StringUtil.simpleClassName(handlerType) + "#0";
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline remove(ChannelHandler handler) {
/*  366 */     remove(getContextOrDie(handler));
/*  367 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandler remove(String name) {
/*  372 */     return remove(getContextOrDie(name)).handler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final <T extends ChannelHandler> T remove(Class<T> handlerType) {
/*  378 */     return (T)remove(getContextOrDie(handlerType)).handler();
/*      */   }
/*      */   
/*      */   public final <T extends ChannelHandler> T removeIfExists(String name) {
/*  382 */     return removeIfExists(context(name));
/*      */   }
/*      */   
/*      */   public final <T extends ChannelHandler> T removeIfExists(Class<T> handlerType) {
/*  386 */     return removeIfExists(context(handlerType));
/*      */   }
/*      */   
/*      */   public final <T extends ChannelHandler> T removeIfExists(ChannelHandler handler) {
/*  390 */     return removeIfExists(context(handler));
/*      */   }
/*      */ 
/*      */   
/*      */   private <T extends ChannelHandler> T removeIfExists(ChannelHandlerContext ctx) {
/*  395 */     if (ctx == null) {
/*  396 */       return null;
/*      */     }
/*  398 */     return (T)remove((AbstractChannelHandlerContext)ctx).handler();
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext remove(final AbstractChannelHandlerContext ctx) {
/*  402 */     assert ctx != this.head && ctx != this.tail;
/*      */     
/*  404 */     synchronized (this) {
/*  405 */       atomicRemoveFromHandlerList(ctx);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  410 */       if (!this.registered) {
/*  411 */         callHandlerCallbackLater(ctx, false);
/*  412 */         return ctx;
/*      */       } 
/*      */       
/*  415 */       EventExecutor executor = ctx.executor();
/*  416 */       if (!executor.inEventLoop()) {
/*  417 */         executor.execute(new Runnable()
/*      */             {
/*      */               public void run() {
/*  420 */                 DefaultChannelPipeline.this.callHandlerRemoved0(ctx);
/*      */               }
/*      */             });
/*  423 */         return ctx;
/*      */       } 
/*      */     } 
/*  426 */     callHandlerRemoved0(ctx);
/*  427 */     return ctx;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void atomicRemoveFromHandlerList(AbstractChannelHandlerContext ctx) {
/*  434 */     AbstractChannelHandlerContext prev = ctx.prev;
/*  435 */     AbstractChannelHandlerContext next = ctx.next;
/*  436 */     prev.next = next;
/*  437 */     next.prev = prev;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandler removeFirst() {
/*  442 */     if (this.head.next == this.tail) {
/*  443 */       throw new NoSuchElementException();
/*      */     }
/*  445 */     return remove(this.head.next).handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandler removeLast() {
/*  450 */     if (this.head.next == this.tail) {
/*  451 */       throw new NoSuchElementException();
/*      */     }
/*  453 */     return remove(this.tail.prev).handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline replace(ChannelHandler oldHandler, String newName, ChannelHandler newHandler) {
/*  458 */     replace(getContextOrDie(oldHandler), newName, newHandler);
/*  459 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandler replace(String oldName, String newName, ChannelHandler newHandler) {
/*  464 */     return replace(getContextOrDie(oldName), newName, newHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final <T extends ChannelHandler> T replace(Class<T> oldHandlerType, String newName, ChannelHandler newHandler) {
/*  471 */     return (T)replace(getContextOrDie(oldHandlerType), newName, newHandler);
/*      */   }
/*      */   
/*      */   private ChannelHandler replace(final AbstractChannelHandlerContext ctx, String newName, ChannelHandler newHandler) {
/*      */     final AbstractChannelHandlerContext newCtx;
/*  476 */     assert ctx != this.head && ctx != this.tail;
/*      */ 
/*      */     
/*  479 */     synchronized (this) {
/*  480 */       checkMultiplicity(newHandler);
/*  481 */       if (newName == null) {
/*  482 */         newName = generateName(newHandler);
/*      */       } else {
/*  484 */         boolean sameName = ctx.name().equals(newName);
/*  485 */         if (!sameName) {
/*  486 */           checkDuplicateName(newName);
/*      */         }
/*      */       } 
/*      */       
/*  490 */       newCtx = newContext((EventExecutorGroup)ctx.childExecutor, newName, newHandler);
/*      */       
/*  492 */       replace0(ctx, newCtx);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  498 */       if (!this.registered) {
/*  499 */         callHandlerCallbackLater(newCtx, true);
/*  500 */         callHandlerCallbackLater(ctx, false);
/*  501 */         return ctx.handler();
/*      */       } 
/*  503 */       EventExecutor executor = ctx.executor();
/*  504 */       if (!executor.inEventLoop()) {
/*  505 */         executor.execute(new Runnable()
/*      */             {
/*      */ 
/*      */               
/*      */               public void run()
/*      */               {
/*  511 */                 DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
/*  512 */                 DefaultChannelPipeline.this.callHandlerRemoved0(ctx);
/*      */               }
/*      */             });
/*  515 */         return ctx.handler();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  521 */     callHandlerAdded0(newCtx);
/*  522 */     callHandlerRemoved0(ctx);
/*  523 */     return ctx.handler();
/*      */   }
/*      */   
/*      */   private static void replace0(AbstractChannelHandlerContext oldCtx, AbstractChannelHandlerContext newCtx) {
/*  527 */     AbstractChannelHandlerContext prev = oldCtx.prev;
/*  528 */     AbstractChannelHandlerContext next = oldCtx.next;
/*  529 */     newCtx.prev = prev;
/*  530 */     newCtx.next = next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  536 */     prev.next = newCtx;
/*  537 */     next.prev = newCtx;
/*      */ 
/*      */     
/*  540 */     oldCtx.prev = newCtx;
/*  541 */     oldCtx.next = newCtx;
/*      */   }
/*      */   
/*      */   private static void checkMultiplicity(ChannelHandler handler) {
/*  545 */     if (handler instanceof ChannelHandlerAdapter) {
/*  546 */       ChannelHandlerAdapter h = (ChannelHandlerAdapter)handler;
/*  547 */       if (!h.isSharable() && h.added) {
/*  548 */         throw new ChannelPipelineException(h
/*  549 */             .getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
/*      */       }
/*      */       
/*  552 */       h.added = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void callHandlerAdded0(AbstractChannelHandlerContext ctx) {
/*      */     try {
/*  558 */       ctx.callHandlerAdded();
/*  559 */     } catch (Throwable t) {
/*  560 */       boolean removed = false;
/*      */       try {
/*  562 */         atomicRemoveFromHandlerList(ctx);
/*  563 */         ctx.callHandlerRemoved();
/*  564 */         removed = true;
/*  565 */       } catch (Throwable t2) {
/*  566 */         if (logger.isWarnEnabled()) {
/*  567 */           logger.warn("Failed to remove a handler: " + ctx.name(), t2);
/*      */         }
/*      */       } 
/*      */       
/*  571 */       if (removed) {
/*  572 */         fireExceptionCaught(new ChannelPipelineException(ctx
/*  573 */               .handler().getClass().getName() + ".handlerAdded() has thrown an exception; removed.", t));
/*      */       } else {
/*      */         
/*  576 */         fireExceptionCaught(new ChannelPipelineException(ctx
/*  577 */               .handler().getClass().getName() + ".handlerAdded() has thrown an exception; also failed to remove.", t));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void callHandlerRemoved0(AbstractChannelHandlerContext ctx) {
/*      */     try {
/*  586 */       ctx.callHandlerRemoved();
/*  587 */     } catch (Throwable t) {
/*  588 */       fireExceptionCaught(new ChannelPipelineException(ctx
/*  589 */             .handler().getClass().getName() + ".handlerRemoved() has thrown an exception.", t));
/*      */     } 
/*      */   }
/*      */   
/*      */   final void invokeHandlerAddedIfNeeded() {
/*  594 */     assert this.channel.eventLoop().inEventLoop();
/*  595 */     if (this.firstRegistration) {
/*  596 */       this.firstRegistration = false;
/*      */ 
/*      */       
/*  599 */       callHandlerAddedForAllHandlers();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandler first() {
/*  605 */     ChannelHandlerContext first = firstContext();
/*  606 */     if (first == null) {
/*  607 */       return null;
/*      */     }
/*  609 */     return first.handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandlerContext firstContext() {
/*  614 */     AbstractChannelHandlerContext first = this.head.next;
/*  615 */     if (first == this.tail) {
/*  616 */       return null;
/*      */     }
/*  618 */     return this.head.next;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandler last() {
/*  623 */     AbstractChannelHandlerContext last = this.tail.prev;
/*  624 */     if (last == this.head) {
/*  625 */       return null;
/*      */     }
/*  627 */     return last.handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandlerContext lastContext() {
/*  632 */     AbstractChannelHandlerContext last = this.tail.prev;
/*  633 */     if (last == this.head) {
/*  634 */       return null;
/*      */     }
/*  636 */     return last;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandler get(String name) {
/*  641 */     ChannelHandlerContext ctx = context(name);
/*  642 */     if (ctx == null) {
/*  643 */       return null;
/*      */     }
/*  645 */     return ctx.handler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final <T extends ChannelHandler> T get(Class<T> handlerType) {
/*  652 */     ChannelHandlerContext ctx = context(handlerType);
/*  653 */     if (ctx == null) {
/*  654 */       return null;
/*      */     }
/*  656 */     return (T)ctx.handler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final ChannelHandlerContext context(String name) {
/*  662 */     return context0((String)ObjectUtil.checkNotNull(name, "name"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandlerContext context(ChannelHandler handler) {
/*  667 */     ObjectUtil.checkNotNull(handler, "handler");
/*      */     
/*  669 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     
/*      */     while (true) {
/*  672 */       if (ctx == null) {
/*  673 */         return null;
/*      */       }
/*      */       
/*  676 */       if (ctx.handler() == handler) {
/*  677 */         return ctx;
/*      */       }
/*      */       
/*  680 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelHandlerContext context(Class<? extends ChannelHandler> handlerType) {
/*  686 */     ObjectUtil.checkNotNull(handlerType, "handlerType");
/*      */     
/*  688 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     while (true) {
/*  690 */       if (ctx == null) {
/*  691 */         return null;
/*      */       }
/*  693 */       if (handlerType.isAssignableFrom(ctx.handler().getClass())) {
/*  694 */         return ctx;
/*      */       }
/*  696 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final List<String> names() {
/*  702 */     List<String> list = new ArrayList<>();
/*  703 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     while (true) {
/*  705 */       if (ctx == null) {
/*  706 */         return list;
/*      */       }
/*  708 */       list.add(ctx.name());
/*  709 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final Map<String, ChannelHandler> toMap() {
/*  715 */     Map<String, ChannelHandler> map = new LinkedHashMap<>();
/*  716 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     while (true) {
/*  718 */       if (ctx == this.tail) {
/*  719 */         return map;
/*      */       }
/*  721 */       map.put(ctx.name(), ctx.handler());
/*  722 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final Iterator<Map.Entry<String, ChannelHandler>> iterator() {
/*  728 */     return toMap().entrySet().iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String toString() {
/*  738 */     StringBuilder buf = (new StringBuilder()).append(StringUtil.simpleClassName(this)).append('{');
/*  739 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     
/*  741 */     while (ctx != this.tail) {
/*      */ 
/*      */ 
/*      */       
/*  745 */       buf.append('(')
/*  746 */         .append(ctx.name())
/*  747 */         .append(" = ")
/*  748 */         .append(ctx.handler().getClass().getName())
/*  749 */         .append(')');
/*      */       
/*  751 */       ctx = ctx.next;
/*  752 */       if (ctx == this.tail) {
/*      */         break;
/*      */       }
/*      */       
/*  756 */       buf.append(", ");
/*      */     } 
/*  758 */     buf.append('}');
/*  759 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline fireChannelRegistered() {
/*  764 */     if (this.head.executor().inEventLoop()) {
/*  765 */       if (this.head.invokeHandler()) {
/*  766 */         this.head.channelRegistered(this.head);
/*      */       } else {
/*  768 */         this.head.fireChannelRegistered();
/*      */       } 
/*      */     } else {
/*  771 */       this.head.executor().execute(this::fireChannelRegistered);
/*      */     } 
/*  773 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline fireChannelUnregistered() {
/*  778 */     if (this.head.executor().inEventLoop()) {
/*  779 */       if (this.head.invokeHandler()) {
/*  780 */         this.head.channelUnregistered(this.head);
/*      */       } else {
/*  782 */         this.head.fireChannelUnregistered();
/*      */       } 
/*      */     } else {
/*  785 */       this.head.executor().execute(this::fireChannelUnregistered);
/*      */     } 
/*  787 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void destroy() {
/*  801 */     destroyUp(this.head.next, false);
/*      */   }
/*      */   
/*      */   private void destroyUp(AbstractChannelHandlerContext ctx, boolean inEventLoop) {
/*  805 */     Thread currentThread = Thread.currentThread();
/*  806 */     AbstractChannelHandlerContext tail = this.tail;
/*      */     while (true) {
/*  808 */       if (ctx == tail) {
/*  809 */         destroyDown(currentThread, tail.prev, inEventLoop);
/*      */         
/*      */         break;
/*      */       } 
/*  813 */       EventExecutor executor = ctx.executor();
/*  814 */       if (!inEventLoop && !executor.inEventLoop(currentThread)) {
/*  815 */         final AbstractChannelHandlerContext finalCtx = ctx;
/*  816 */         executor.execute(new Runnable()
/*      */             {
/*      */               public void run() {
/*  819 */                 DefaultChannelPipeline.this.destroyUp(finalCtx, true);
/*      */               }
/*      */             });
/*      */         
/*      */         break;
/*      */       } 
/*  825 */       ctx = ctx.next;
/*  826 */       inEventLoop = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void destroyDown(Thread currentThread, AbstractChannelHandlerContext ctx, boolean inEventLoop) {
/*  832 */     AbstractChannelHandlerContext head = this.head;
/*      */     
/*  834 */     while (ctx != head) {
/*      */ 
/*      */ 
/*      */       
/*  838 */       EventExecutor executor = ctx.executor();
/*  839 */       if (inEventLoop || executor.inEventLoop(currentThread)) {
/*  840 */         atomicRemoveFromHandlerList(ctx);
/*  841 */         callHandlerRemoved0(ctx);
/*      */       } else {
/*  843 */         final AbstractChannelHandlerContext finalCtx = ctx;
/*  844 */         executor.execute(new Runnable()
/*      */             {
/*      */               public void run() {
/*  847 */                 DefaultChannelPipeline.this.destroyDown(Thread.currentThread(), finalCtx, true);
/*      */               }
/*      */             });
/*      */         
/*      */         break;
/*      */       } 
/*  853 */       ctx = ctx.prev;
/*  854 */       inEventLoop = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline fireChannelActive() {
/*  860 */     if (this.head.executor().inEventLoop()) {
/*  861 */       if (this.head.invokeHandler()) {
/*  862 */         this.head.channelActive(this.head);
/*      */       } else {
/*  864 */         this.head.fireChannelActive();
/*      */       } 
/*      */     } else {
/*  867 */       this.head.executor().execute(this::fireChannelActive);
/*      */     } 
/*  869 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline fireChannelInactive() {
/*  874 */     if (this.head.executor().inEventLoop()) {
/*  875 */       if (this.head.invokeHandler()) {
/*  876 */         this.head.channelInactive(this.head);
/*      */       } else {
/*  878 */         this.head.fireChannelInactive();
/*      */       } 
/*      */     } else {
/*  881 */       this.head.executor().execute(this::fireChannelInactive);
/*      */     } 
/*  883 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline fireExceptionCaught(Throwable cause) {
/*  888 */     if (this.head.executor().inEventLoop()) {
/*  889 */       if (this.head.invokeHandler()) {
/*  890 */         this.head.exceptionCaught(this.head, cause);
/*      */       } else {
/*  892 */         this.head.fireExceptionCaught(cause);
/*      */       } 
/*      */     } else {
/*  895 */       this.head.executor().execute(() -> fireExceptionCaught(cause));
/*      */     } 
/*  897 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline fireUserEventTriggered(Object event) {
/*  902 */     if (this.head.executor().inEventLoop()) {
/*  903 */       if (this.head.invokeHandler()) {
/*  904 */         this.head.userEventTriggered(this.head, event);
/*      */       } else {
/*  906 */         this.head.fireUserEventTriggered(event);
/*      */       } 
/*      */     } else {
/*  909 */       this.head.executor().execute(() -> fireUserEventTriggered(event));
/*      */     } 
/*  911 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline fireChannelRead(Object msg) {
/*  916 */     if (this.head.executor().inEventLoop()) {
/*  917 */       if (this.head.invokeHandler()) {
/*  918 */         this.head.channelRead(this.head, msg);
/*      */       } else {
/*  920 */         this.head.fireChannelRead(msg);
/*      */       } 
/*      */     } else {
/*  923 */       this.head.executor().execute(() -> fireChannelRead(msg));
/*      */     } 
/*  925 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline fireChannelReadComplete() {
/*  930 */     if (this.head.executor().inEventLoop()) {
/*  931 */       if (this.head.invokeHandler()) {
/*  932 */         this.head.channelReadComplete(this.head);
/*      */       } else {
/*  934 */         this.head.fireChannelReadComplete();
/*      */       } 
/*      */     } else {
/*  937 */       this.head.executor().execute(this::fireChannelReadComplete);
/*      */     } 
/*  939 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline fireChannelWritabilityChanged() {
/*  944 */     if (this.head.executor().inEventLoop()) {
/*  945 */       if (this.head.invokeHandler()) {
/*  946 */         this.head.channelWritabilityChanged(this.head);
/*      */       } else {
/*  948 */         this.head.fireChannelWritabilityChanged();
/*      */       } 
/*      */     } else {
/*  951 */       this.head.executor().execute(this::fireChannelWritabilityChanged);
/*      */     } 
/*  953 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture bind(SocketAddress localAddress) {
/*  958 */     return this.tail.bind(localAddress);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture connect(SocketAddress remoteAddress) {
/*  963 */     return this.tail.connect(remoteAddress);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/*  968 */     return this.tail.connect(remoteAddress, localAddress);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture disconnect() {
/*  973 */     return this.tail.disconnect();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture close() {
/*  978 */     return this.tail.close();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture deregister() {
/*  983 */     return this.tail.deregister();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline flush() {
/*  988 */     this.tail.flush();
/*  989 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
/*  994 */     return this.tail.bind(localAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/*  999 */     return this.tail.connect(remoteAddress, promise);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 1005 */     return this.tail.connect(remoteAddress, localAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture disconnect(ChannelPromise promise) {
/* 1010 */     return this.tail.disconnect(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture close(ChannelPromise promise) {
/* 1015 */     return this.tail.close(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture deregister(ChannelPromise promise) {
/* 1020 */     return this.tail.deregister(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPipeline read() {
/* 1025 */     this.tail.read();
/* 1026 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture write(Object msg) {
/* 1031 */     return this.tail.write(msg);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture write(Object msg, ChannelPromise promise) {
/* 1036 */     return this.tail.write(msg, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/* 1041 */     return this.tail.writeAndFlush(msg, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture writeAndFlush(Object msg) {
/* 1046 */     return this.tail.writeAndFlush(msg);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPromise newPromise() {
/* 1051 */     return new DefaultChannelPromise(this.channel);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelProgressivePromise newProgressivePromise() {
/* 1056 */     return new DefaultChannelProgressivePromise(this.channel);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture newSucceededFuture() {
/* 1061 */     return this.succeededFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture newFailedFuture(Throwable cause) {
/* 1066 */     return new FailedChannelFuture(this.channel, null, cause);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPromise voidPromise() {
/* 1071 */     return this.voidPromise;
/*      */   }
/*      */   
/*      */   private void checkDuplicateName(String name) {
/* 1075 */     if (context0(name) != null) {
/* 1076 */       throw new IllegalArgumentException("Duplicate handler name: " + name);
/*      */     }
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext context0(String name) {
/* 1081 */     AbstractChannelHandlerContext context = this.head.next;
/* 1082 */     while (context != this.tail) {
/* 1083 */       if (context.name().equals(name)) {
/* 1084 */         return context;
/*      */       }
/* 1086 */       context = context.next;
/*      */     } 
/* 1088 */     return null;
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext getContextOrDie(String name) {
/* 1092 */     AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext)context(name);
/* 1093 */     if (ctx == null) {
/* 1094 */       throw new NoSuchElementException(name);
/*      */     }
/* 1096 */     return ctx;
/*      */   }
/*      */ 
/*      */   
/*      */   private AbstractChannelHandlerContext getContextOrDie(ChannelHandler handler) {
/* 1101 */     AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext)context(handler);
/* 1102 */     if (ctx == null) {
/* 1103 */       throw new NoSuchElementException(handler.getClass().getName());
/*      */     }
/* 1105 */     return ctx;
/*      */   }
/*      */ 
/*      */   
/*      */   private AbstractChannelHandlerContext getContextOrDie(Class<? extends ChannelHandler> handlerType) {
/* 1110 */     AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext)context(handlerType);
/* 1111 */     if (ctx == null) {
/* 1112 */       throw new NoSuchElementException(handlerType.getName());
/*      */     }
/* 1114 */     return ctx;
/*      */   }
/*      */ 
/*      */   
/*      */   private void callHandlerAddedForAllHandlers() {
/*      */     PendingHandlerCallback pendingHandlerCallbackHead;
/* 1120 */     synchronized (this) {
/* 1121 */       assert !this.registered;
/*      */ 
/*      */       
/* 1124 */       this.registered = true;
/*      */       
/* 1126 */       pendingHandlerCallbackHead = this.pendingHandlerCallbackHead;
/*      */       
/* 1128 */       this.pendingHandlerCallbackHead = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1134 */     PendingHandlerCallback task = pendingHandlerCallbackHead;
/* 1135 */     while (task != null) {
/* 1136 */       task.execute();
/* 1137 */       task = task.next;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void callHandlerCallbackLater(AbstractChannelHandlerContext ctx, boolean added) {
/* 1142 */     assert !this.registered;
/*      */     
/* 1144 */     PendingHandlerCallback task = added ? new PendingHandlerAddedTask(ctx) : new PendingHandlerRemovedTask(ctx);
/* 1145 */     PendingHandlerCallback pending = this.pendingHandlerCallbackHead;
/* 1146 */     if (pending == null) {
/* 1147 */       this.pendingHandlerCallbackHead = task;
/*      */     } else {
/*      */       
/* 1150 */       while (pending.next != null) {
/* 1151 */         pending = pending.next;
/*      */       }
/* 1153 */       pending.next = task;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void callHandlerAddedInEventLoop(final AbstractChannelHandlerContext newCtx, EventExecutor executor) {
/* 1158 */     newCtx.setAddPending();
/* 1159 */     executor.execute(new Runnable()
/*      */         {
/*      */           public void run() {
/* 1162 */             DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onUnhandledInboundException(Throwable cause) {
/*      */     try {
/* 1173 */       logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", cause);
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 1178 */       ReferenceCountUtil.release(cause);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onUnhandledInboundChannelActive() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onUnhandledInboundChannelInactive() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onUnhandledInboundMessage(Object msg) {
/*      */     try {
/* 1203 */       logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", msg);
/*      */     }
/*      */     finally {
/*      */       
/* 1207 */       ReferenceCountUtil.release(msg);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onUnhandledInboundMessage(ChannelHandlerContext ctx, Object msg) {
/* 1217 */     onUnhandledInboundMessage(msg);
/* 1218 */     if (logger.isDebugEnabled()) {
/* 1219 */       logger.debug("Discarded message pipeline : {}. Channel : {}.", ctx
/* 1220 */           .pipeline().names(), ctx.channel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onUnhandledInboundChannelReadComplete() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onUnhandledInboundUserEventTriggered(Object evt) {
/* 1239 */     ReferenceCountUtil.release(evt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onUnhandledChannelWritabilityChanged() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void incrementPendingOutboundBytes(long size) {
/* 1250 */     ChannelOutboundBuffer buffer = this.channel.unsafe().outboundBuffer();
/* 1251 */     if (buffer != null) {
/* 1252 */       buffer.incrementPendingOutboundBytes(size);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void decrementPendingOutboundBytes(long size) {
/* 1257 */     ChannelOutboundBuffer buffer = this.channel.unsafe().outboundBuffer();
/* 1258 */     if (buffer != null)
/* 1259 */       buffer.decrementPendingOutboundBytes(size); 
/*      */   }
/*      */   
/*      */   final class TailContext
/*      */     extends AbstractChannelHandlerContext
/*      */     implements ChannelInboundHandler
/*      */   {
/*      */     TailContext(DefaultChannelPipeline pipeline) {
/* 1267 */       super(pipeline, (EventExecutor)null, DefaultChannelPipeline.TAIL_NAME, (Class)TailContext.class);
/* 1268 */       setAddComplete();
/*      */     }
/*      */ 
/*      */     
/*      */     public ChannelHandler handler() {
/* 1273 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelRegistered(ChannelHandlerContext ctx) {}
/*      */ 
/*      */     
/*      */     public void channelUnregistered(ChannelHandlerContext ctx) {}
/*      */ 
/*      */     
/*      */     public void channelActive(ChannelHandlerContext ctx) {
/* 1284 */       DefaultChannelPipeline.this.onUnhandledInboundChannelActive();
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelInactive(ChannelHandlerContext ctx) {
/* 1289 */       DefaultChannelPipeline.this.onUnhandledInboundChannelInactive();
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelWritabilityChanged(ChannelHandlerContext ctx) {
/* 1294 */       DefaultChannelPipeline.this.onUnhandledChannelWritabilityChanged();
/*      */     }
/*      */ 
/*      */     
/*      */     public void handlerAdded(ChannelHandlerContext ctx) {}
/*      */ 
/*      */     
/*      */     public void handlerRemoved(ChannelHandlerContext ctx) {}
/*      */ 
/*      */     
/*      */     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 1305 */       DefaultChannelPipeline.this.onUnhandledInboundUserEventTriggered(evt);
/*      */     }
/*      */ 
/*      */     
/*      */     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
/* 1310 */       DefaultChannelPipeline.this.onUnhandledInboundException(cause);
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelRead(ChannelHandlerContext ctx, Object msg) {
/* 1315 */       DefaultChannelPipeline.this.onUnhandledInboundMessage(ctx, msg);
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelReadComplete(ChannelHandlerContext ctx) {
/* 1320 */       DefaultChannelPipeline.this.onUnhandledInboundChannelReadComplete();
/*      */     }
/*      */   }
/*      */   
/*      */   final class HeadContext
/*      */     extends AbstractChannelHandlerContext
/*      */     implements ChannelOutboundHandler, ChannelInboundHandler {
/*      */     private final Channel.Unsafe unsafe;
/*      */     
/*      */     HeadContext(DefaultChannelPipeline pipeline) {
/* 1330 */       super(pipeline, (EventExecutor)null, DefaultChannelPipeline.HEAD_NAME, (Class)HeadContext.class);
/* 1331 */       this.unsafe = pipeline.channel().unsafe();
/* 1332 */       setAddComplete();
/*      */     }
/*      */ 
/*      */     
/*      */     public ChannelHandler handler() {
/* 1337 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handlerAdded(ChannelHandlerContext ctx) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handlerRemoved(ChannelHandlerContext ctx) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) {
/* 1353 */       this.unsafe.bind(localAddress, promise);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 1361 */       this.unsafe.connect(remoteAddress, localAddress, promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 1366 */       this.unsafe.disconnect(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 1371 */       this.unsafe.close(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 1376 */       this.unsafe.deregister(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void read(ChannelHandlerContext ctx) {
/* 1381 */       this.unsafe.beginRead();
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
/* 1386 */       this.unsafe.write(msg, promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void flush(ChannelHandlerContext ctx) {
/* 1391 */       this.unsafe.flush();
/*      */     }
/*      */ 
/*      */     
/*      */     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
/* 1396 */       ctx.fireExceptionCaught(cause);
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelRegistered(ChannelHandlerContext ctx) {
/* 1401 */       DefaultChannelPipeline.this.invokeHandlerAddedIfNeeded();
/* 1402 */       ctx.fireChannelRegistered();
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelUnregistered(ChannelHandlerContext ctx) {
/* 1407 */       ctx.fireChannelUnregistered();
/*      */ 
/*      */       
/* 1410 */       if (!DefaultChannelPipeline.this.channel.isOpen()) {
/* 1411 */         DefaultChannelPipeline.this.destroy();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelActive(ChannelHandlerContext ctx) {
/* 1417 */       ctx.fireChannelActive();
/*      */       
/* 1419 */       readIfIsAutoRead();
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelInactive(ChannelHandlerContext ctx) {
/* 1424 */       ctx.fireChannelInactive();
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelRead(ChannelHandlerContext ctx, Object msg) {
/* 1429 */       ctx.fireChannelRead(msg);
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelReadComplete(ChannelHandlerContext ctx) {
/* 1434 */       ctx.fireChannelReadComplete();
/*      */       
/* 1436 */       readIfIsAutoRead();
/*      */     }
/*      */     
/*      */     private void readIfIsAutoRead() {
/* 1440 */       if (DefaultChannelPipeline.this.channel.config().isAutoRead()) {
/* 1441 */         DefaultChannelPipeline.this.channel.read();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 1447 */       ctx.fireUserEventTriggered(evt);
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelWritabilityChanged(ChannelHandlerContext ctx) {
/* 1452 */       ctx.fireChannelWritabilityChanged();
/*      */     }
/*      */   }
/*      */   
/*      */   private static abstract class PendingHandlerCallback implements Runnable {
/*      */     final AbstractChannelHandlerContext ctx;
/*      */     PendingHandlerCallback next;
/*      */     
/*      */     PendingHandlerCallback(AbstractChannelHandlerContext ctx) {
/* 1461 */       this.ctx = ctx;
/*      */     }
/*      */     
/*      */     abstract void execute();
/*      */   }
/*      */   
/*      */   private final class PendingHandlerAddedTask
/*      */     extends PendingHandlerCallback {
/*      */     PendingHandlerAddedTask(AbstractChannelHandlerContext ctx) {
/* 1470 */       super(ctx);
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/* 1475 */       DefaultChannelPipeline.this.callHandlerAdded0(this.ctx);
/*      */     }
/*      */ 
/*      */     
/*      */     void execute() {
/* 1480 */       EventExecutor executor = this.ctx.executor();
/* 1481 */       if (executor.inEventLoop()) {
/* 1482 */         DefaultChannelPipeline.this.callHandlerAdded0(this.ctx);
/*      */       } else {
/*      */         try {
/* 1485 */           executor.execute(this);
/* 1486 */         } catch (RejectedExecutionException e) {
/* 1487 */           if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 1488 */             DefaultChannelPipeline.logger.warn("Can't invoke handlerAdded() as the EventExecutor {} rejected it, removing handler {}.", new Object[] { executor, this.ctx
/*      */                   
/* 1490 */                   .name(), e });
/*      */           }
/* 1492 */           DefaultChannelPipeline.this.atomicRemoveFromHandlerList(this.ctx);
/* 1493 */           this.ctx.setRemoved();
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private final class PendingHandlerRemovedTask
/*      */     extends PendingHandlerCallback {
/*      */     PendingHandlerRemovedTask(AbstractChannelHandlerContext ctx) {
/* 1502 */       super(ctx);
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/* 1507 */       DefaultChannelPipeline.this.callHandlerRemoved0(this.ctx);
/*      */     }
/*      */ 
/*      */     
/*      */     void execute() {
/* 1512 */       EventExecutor executor = this.ctx.executor();
/* 1513 */       if (executor.inEventLoop()) {
/* 1514 */         DefaultChannelPipeline.this.callHandlerRemoved0(this.ctx);
/*      */       } else {
/*      */         try {
/* 1517 */           executor.execute(this);
/* 1518 */         } catch (RejectedExecutionException e) {
/* 1519 */           if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 1520 */             DefaultChannelPipeline.logger.warn("Can't invoke handlerRemoved() as the EventExecutor {} rejected it, removing handler {}.", new Object[] { executor, this.ctx
/*      */                   
/* 1522 */                   .name(), e });
/*      */           }
/*      */           
/* 1525 */           this.ctx.setRemoved();
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\DefaultChannelPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */