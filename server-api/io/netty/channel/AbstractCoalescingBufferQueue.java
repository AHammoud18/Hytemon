/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ public abstract class AbstractCoalescingBufferQueue
/*     */ {
/*  33 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractCoalescingBufferQueue.class);
/*     */ 
/*     */   
/*     */   private final ArrayDeque<Object> bufAndListenerPairs;
/*     */ 
/*     */   
/*     */   private final PendingBytesTracker tracker;
/*     */ 
/*     */   
/*     */   private int readableBytes;
/*     */ 
/*     */   
/*     */   protected AbstractCoalescingBufferQueue(Channel channel, int initSize) {
/*  46 */     this.bufAndListenerPairs = new ArrayDeque(initSize);
/*  47 */     this.tracker = (channel == null) ? null : PendingBytesTracker.newTracker(channel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addFirst(ByteBuf buf, ChannelPromise promise) {
/*  57 */     addFirst(buf, toChannelFutureListener(promise));
/*     */   }
/*     */ 
/*     */   
/*     */   private void addFirst(ByteBuf buf, ChannelFutureListener listener) {
/*  62 */     buf.touch();
/*     */     
/*  64 */     if (listener != null) {
/*  65 */       this.bufAndListenerPairs.addFirst(listener);
/*     */     }
/*  67 */     this.bufAndListenerPairs.addFirst(buf);
/*  68 */     incrementReadableBytes(buf.readableBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(ByteBuf buf) {
/*  75 */     add(buf, (ChannelFutureListener)null);
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
/*     */   public final void add(ByteBuf buf, ChannelPromise promise) {
/*  87 */     add(buf, toChannelFutureListener(promise));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(ByteBuf buf, ChannelFutureListener listener) {
/*  98 */     buf.touch();
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.bufAndListenerPairs.add(buf);
/* 103 */     if (listener != null) {
/* 104 */       this.bufAndListenerPairs.add(listener);
/*     */     }
/* 106 */     incrementReadableBytes(buf.readableBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ByteBuf removeFirst(ChannelPromise aggregatePromise) {
/* 115 */     Object entry = this.bufAndListenerPairs.poll();
/* 116 */     if (entry == null) {
/* 117 */       return null;
/*     */     }
/* 119 */     assert entry instanceof ByteBuf;
/* 120 */     ByteBuf result = (ByteBuf)entry;
/*     */     
/* 122 */     decrementReadableBytes(result.readableBytes());
/*     */     
/* 124 */     entry = this.bufAndListenerPairs.peek();
/* 125 */     if (entry instanceof ChannelFutureListener) {
/* 126 */       aggregatePromise.addListener((ChannelFutureListener)entry);
/* 127 */       this.bufAndListenerPairs.poll();
/*     */     } 
/* 129 */     return result;
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
/*     */   public final ByteBuf remove(ByteBufAllocator alloc, int bytes, ChannelPromise aggregatePromise) {
/* 144 */     ObjectUtil.checkPositiveOrZero(bytes, "bytes");
/* 145 */     ObjectUtil.checkNotNull(aggregatePromise, "aggregatePromise");
/*     */ 
/*     */     
/* 148 */     if (this.bufAndListenerPairs.isEmpty()) {
/* 149 */       assert this.readableBytes == 0;
/* 150 */       return removeEmptyValue();
/*     */     } 
/* 152 */     bytes = Math.min(bytes, this.readableBytes);
/*     */     
/* 154 */     ByteBuf toReturn = null;
/* 155 */     ByteBuf entryBuffer = null;
/* 156 */     int originalBytes = bytes;
/* 157 */     Object entry = null;
/*     */     try {
/*     */       while (true) {
/* 160 */         entry = this.bufAndListenerPairs.poll();
/* 161 */         if (entry == null) {
/*     */           break;
/*     */         }
/*     */         
/* 165 */         if (entry instanceof ByteBuf) {
/* 166 */           entryBuffer = (ByteBuf)entry;
/* 167 */           int bufferBytes = entryBuffer.readableBytes();
/*     */           
/* 169 */           if (bufferBytes > bytes) {
/*     */             
/* 171 */             this.bufAndListenerPairs.addFirst(entryBuffer);
/* 172 */             if (bytes > 0) {
/*     */               
/* 174 */               entryBuffer = entryBuffer.readRetainedSlice(bytes);
/*     */ 
/*     */               
/* 177 */               toReturn = (toReturn == null) ? entryBuffer : compose(alloc, toReturn, entryBuffer);
/* 178 */               bytes = 0;
/*     */             } 
/*     */             
/*     */             break;
/*     */           } 
/* 183 */           bytes -= bufferBytes;
/* 184 */           if (toReturn == null) {
/*     */ 
/*     */ 
/*     */             
/* 188 */             toReturn = (bytes == 0) ? entryBuffer : composeFirst(alloc, entryBuffer, bufferBytes + bytes);
/*     */           } else {
/* 190 */             toReturn = compose(alloc, toReturn, entryBuffer);
/*     */           } 
/* 192 */           entryBuffer = null; continue;
/* 193 */         }  if (entry instanceof DelegatingChannelPromiseNotifier) {
/* 194 */           aggregatePromise.addListener((DelegatingChannelPromiseNotifier)entry); continue;
/* 195 */         }  if (entry instanceof ChannelFutureListener) {
/* 196 */           aggregatePromise.addListener((ChannelFutureListener)entry);
/*     */         }
/*     */       } 
/* 199 */     } catch (Throwable cause) {
/*     */ 
/*     */ 
/*     */       
/* 203 */       decrementReadableBytes(originalBytes - bytes);
/*     */ 
/*     */       
/* 206 */       entry = this.bufAndListenerPairs.peek();
/* 207 */       if (entry instanceof ChannelFutureListener) {
/* 208 */         aggregatePromise.addListener((ChannelFutureListener)entry);
/* 209 */         this.bufAndListenerPairs.poll();
/*     */       } 
/*     */       
/* 212 */       ReferenceCountUtil.safeRelease(entryBuffer);
/* 213 */       ReferenceCountUtil.safeRelease(toReturn);
/* 214 */       aggregatePromise.setFailure(cause);
/* 215 */       PlatformDependent.throwException(cause);
/*     */     } 
/* 217 */     decrementReadableBytes(originalBytes - bytes);
/* 218 */     return toReturn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int readableBytes() {
/* 225 */     return this.readableBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isEmpty() {
/* 232 */     return this.bufAndListenerPairs.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void releaseAndFailAll(ChannelOutboundInvoker invoker, Throwable cause) {
/* 239 */     releaseAndCompleteAll(invoker.newFailedFuture(cause));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void copyTo(AbstractCoalescingBufferQueue dest) {
/* 247 */     dest.bufAndListenerPairs.addAll(this.bufAndListenerPairs);
/* 248 */     dest.incrementReadableBytes(this.readableBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeAndRemoveAll(ChannelHandlerContext ctx) {
/* 256 */     Throwable pending = null;
/* 257 */     ByteBuf previousBuf = null;
/*     */     while (true) {
/* 259 */       Object entry = this.bufAndListenerPairs.poll();
/*     */       try {
/* 261 */         if (entry == null) {
/* 262 */           if (previousBuf != null) {
/* 263 */             decrementReadableBytes(previousBuf.readableBytes());
/* 264 */             ctx.write(previousBuf, ctx.voidPromise());
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/* 269 */         if (entry instanceof ByteBuf) {
/* 270 */           if (previousBuf != null) {
/* 271 */             decrementReadableBytes(previousBuf.readableBytes());
/* 272 */             ctx.write(previousBuf, ctx.voidPromise());
/*     */           } 
/* 274 */           previousBuf = (ByteBuf)entry; continue;
/* 275 */         }  if (entry instanceof ChannelPromise) {
/* 276 */           decrementReadableBytes(previousBuf.readableBytes());
/* 277 */           ctx.write(previousBuf, (ChannelPromise)entry);
/* 278 */           previousBuf = null; continue;
/*     */         } 
/* 280 */         decrementReadableBytes(previousBuf.readableBytes());
/* 281 */         ctx.write(previousBuf).addListener((ChannelFutureListener)entry);
/* 282 */         previousBuf = null;
/*     */       }
/* 284 */       catch (Throwable t) {
/* 285 */         if (pending == null) {
/* 286 */           pending = t; continue;
/*     */         } 
/* 288 */         logger.info("Throwable being suppressed because Throwable {} is already pending", pending, t);
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     if (pending != null) {
/* 293 */       throw new IllegalStateException(pending);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 299 */     return "bytes: " + this.readableBytes + " buffers: " + (size() >> 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ByteBuf compose(ByteBufAllocator paramByteBufAllocator, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf composeIntoComposite(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf next) {
/* 313 */     CompositeByteBuf composite = alloc.compositeBuffer(size() + 2);
/*     */     try {
/* 315 */       composite.addComponent(true, cumulation);
/* 316 */       composite.addComponent(true, next);
/* 317 */     } catch (Throwable cause) {
/* 318 */       composite.release();
/* 319 */       ReferenceCountUtil.safeRelease(next);
/* 320 */       PlatformDependent.throwException(cause);
/*     */     } 
/* 322 */     return (ByteBuf)composite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf copyAndCompose(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf next) {
/* 333 */     ByteBuf newCumulation = alloc.ioBuffer(cumulation.readableBytes() + next.readableBytes());
/*     */     try {
/* 335 */       newCumulation.writeBytes(cumulation).writeBytes(next);
/* 336 */     } catch (Throwable cause) {
/* 337 */       newCumulation.release();
/* 338 */       ReferenceCountUtil.safeRelease(next);
/* 339 */       PlatformDependent.throwException(cause);
/*     */     } 
/* 341 */     cumulation.release();
/* 342 */     next.release();
/* 343 */     return newCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuf composeFirst(ByteBufAllocator allocator, ByteBuf first, int bufferSize) {
/* 353 */     return composeFirst(allocator, first);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected ByteBuf composeFirst(ByteBufAllocator allocator, ByteBuf first) {
/* 365 */     return first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ByteBuf removeEmptyValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int size() {
/* 379 */     return this.bufAndListenerPairs.size();
/*     */   }
/*     */   
/*     */   private void releaseAndCompleteAll(ChannelFuture future) {
/* 383 */     Throwable pending = null;
/*     */     while (true) {
/* 385 */       Object entry = this.bufAndListenerPairs.poll();
/* 386 */       if (entry == null) {
/*     */         break;
/*     */       }
/*     */       try {
/* 390 */         if (entry instanceof ByteBuf) {
/* 391 */           ByteBuf buffer = (ByteBuf)entry;
/* 392 */           decrementReadableBytes(buffer.readableBytes());
/* 393 */           ReferenceCountUtil.safeRelease(buffer); continue;
/*     */         } 
/* 395 */         ((ChannelFutureListener)entry).operationComplete(future);
/*     */       }
/* 397 */       catch (Throwable t) {
/* 398 */         if (pending == null) {
/* 399 */           pending = t; continue;
/*     */         } 
/* 401 */         logger.info("Throwable being suppressed because Throwable {} is already pending", pending, t);
/*     */       } 
/*     */     } 
/*     */     
/* 405 */     if (pending != null) {
/* 406 */       throw new IllegalStateException(pending);
/*     */     }
/*     */   }
/*     */   
/*     */   private void incrementReadableBytes(int increment) {
/* 411 */     int nextReadableBytes = this.readableBytes + increment;
/* 412 */     if (nextReadableBytes < this.readableBytes) {
/* 413 */       throw new IllegalStateException("buffer queue length overflow: " + this.readableBytes + " + " + increment);
/*     */     }
/* 415 */     this.readableBytes = nextReadableBytes;
/* 416 */     if (this.tracker != null) {
/* 417 */       this.tracker.incrementPendingOutboundBytes(increment);
/*     */     }
/*     */   }
/*     */   
/*     */   private void decrementReadableBytes(int decrement) {
/* 422 */     this.readableBytes -= decrement;
/* 423 */     assert this.readableBytes >= 0;
/* 424 */     if (this.tracker != null) {
/* 425 */       this.tracker.decrementPendingOutboundBytes(decrement);
/*     */     }
/*     */   }
/*     */   
/*     */   private static ChannelFutureListener toChannelFutureListener(ChannelPromise promise) {
/* 430 */     return promise.isVoid() ? null : new DelegatingChannelPromiseNotifier(promise);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\AbstractCoalescingBufferQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */