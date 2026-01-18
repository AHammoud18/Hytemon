/*      */ package io.netty.buffer;
/*      */ 
/*      */ import io.netty.util.ByteProcessor;
/*      */ import io.netty.util.IllegalReferenceCountException;
/*      */ import io.netty.util.ReferenceCountUtil;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.RecyclableArrayList;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.NoSuchElementException;
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
/*      */ public class CompositeByteBuf
/*      */   extends AbstractReferenceCountedByteBuf
/*      */   implements Iterable<ByteBuf>
/*      */ {
/*   51 */   private static final ByteBuffer EMPTY_NIO_BUFFER = Unpooled.EMPTY_BUFFER.nioBuffer();
/*   52 */   private static final Iterator<ByteBuf> EMPTY_ITERATOR = Collections.<ByteBuf>emptyList().iterator();
/*      */   
/*      */   private final ByteBufAllocator alloc;
/*      */   
/*      */   private final boolean direct;
/*      */   
/*      */   private final int maxNumComponents;
/*      */   private int componentCount;
/*      */   private Component[] components;
/*      */   private boolean freed;
/*      */   
/*      */   private CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, int initSize) {
/*   64 */     super(2147483647);
/*      */     
/*   66 */     this.alloc = (ByteBufAllocator)ObjectUtil.checkNotNull(alloc, "alloc");
/*   67 */     if (maxNumComponents < 1) {
/*   68 */       throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents + " (expected: >= 1)");
/*      */     }
/*      */ 
/*      */     
/*   72 */     this.direct = direct;
/*   73 */     this.maxNumComponents = maxNumComponents;
/*   74 */     this.components = newCompArray(initSize, maxNumComponents);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents) {
/*   78 */     this(alloc, direct, maxNumComponents, 0);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, ByteBuf... buffers) {
/*   82 */     this(alloc, direct, maxNumComponents, buffers, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, ByteBuf[] buffers, int offset) {
/*   87 */     this(alloc, direct, maxNumComponents, buffers.length - offset);
/*      */     
/*   89 */     addComponents0(false, 0, buffers, offset);
/*   90 */     consolidateIfNeeded();
/*   91 */     setIndex0(0, capacity());
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, Iterable<ByteBuf> buffers) {
/*   96 */     this(alloc, direct, maxNumComponents, 
/*   97 */         (buffers instanceof Collection) ? ((Collection)buffers).size() : 0);
/*      */     
/*   99 */     addComponents(false, 0, buffers);
/*  100 */     setIndex(0, capacity());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   static final ByteWrapper<byte[]> BYTE_ARRAY_WRAPPER = new ByteWrapper<byte[]>()
/*      */     {
/*      */       public ByteBuf wrap(byte[] bytes) {
/*  112 */         return Unpooled.wrappedBuffer(bytes);
/*      */       }
/*      */       
/*      */       public boolean isEmpty(byte[] bytes) {
/*  116 */         return (bytes.length == 0);
/*      */       }
/*      */     };
/*      */   
/*  120 */   static final ByteWrapper<ByteBuffer> BYTE_BUFFER_WRAPPER = new ByteWrapper<ByteBuffer>()
/*      */     {
/*      */       public ByteBuf wrap(ByteBuffer bytes) {
/*  123 */         return Unpooled.wrappedBuffer(bytes);
/*      */       }
/*      */       
/*      */       public boolean isEmpty(ByteBuffer bytes) {
/*  127 */         return !bytes.hasRemaining();
/*      */       }
/*      */     };
/*      */   private Component lastAccessed;
/*      */   
/*      */   <T> CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, ByteWrapper<T> wrapper, T[] buffers, int offset) {
/*  133 */     this(alloc, direct, maxNumComponents, buffers.length - offset);
/*      */     
/*  135 */     addComponents0(false, 0, wrapper, buffers, offset);
/*  136 */     consolidateIfNeeded();
/*  137 */     setIndex(0, capacity());
/*      */   }
/*      */   
/*      */   private static Component[] newCompArray(int initComponents, int maxNumComponents) {
/*  141 */     int capacityGuess = Math.min(16, maxNumComponents);
/*  142 */     return new Component[Math.max(initComponents, capacityGuess)];
/*      */   }
/*      */ 
/*      */   
/*      */   CompositeByteBuf(ByteBufAllocator alloc) {
/*  147 */     super(2147483647);
/*  148 */     this.alloc = alloc;
/*  149 */     this.direct = false;
/*  150 */     this.maxNumComponents = 0;
/*  151 */     this.components = null;
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
/*      */   public CompositeByteBuf addComponent(ByteBuf buffer) {
/*  165 */     return addComponent(false, buffer);
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
/*      */   
/*      */   public CompositeByteBuf addComponents(ByteBuf... buffers) {
/*  180 */     return addComponents(false, buffers);
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
/*      */   
/*      */   public CompositeByteBuf addComponents(Iterable<ByteBuf> buffers) {
/*  195 */     return addComponents(false, buffers);
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
/*      */   
/*      */   public CompositeByteBuf addComponent(int cIndex, ByteBuf buffer) {
/*  210 */     return addComponent(false, cIndex, buffer);
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
/*      */   public CompositeByteBuf addComponent(boolean increaseWriterIndex, ByteBuf buffer) {
/*  222 */     return addComponent(increaseWriterIndex, this.componentCount, buffer);
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
/*      */   public CompositeByteBuf addComponents(boolean increaseWriterIndex, ByteBuf... buffers) {
/*  235 */     ObjectUtil.checkNotNull(buffers, "buffers");
/*  236 */     addComponents0(increaseWriterIndex, this.componentCount, buffers, 0);
/*  237 */     consolidateIfNeeded();
/*  238 */     return this;
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
/*      */   public CompositeByteBuf addComponents(boolean increaseWriterIndex, Iterable<ByteBuf> buffers) {
/*  251 */     return addComponents(increaseWriterIndex, this.componentCount, buffers);
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
/*      */   public CompositeByteBuf addComponent(boolean increaseWriterIndex, int cIndex, ByteBuf buffer) {
/*  264 */     ObjectUtil.checkNotNull(buffer, "buffer");
/*  265 */     addComponent0(increaseWriterIndex, cIndex, buffer);
/*  266 */     consolidateIfNeeded();
/*  267 */     return this;
/*      */   }
/*      */   
/*      */   private static void checkForOverflow(int capacity, int readableBytes) {
/*  271 */     if (capacity + readableBytes < 0) {
/*  272 */       throw new IllegalArgumentException("Can't increase by " + readableBytes + " as capacity(" + capacity + ") would overflow " + 2147483647);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int addComponent0(boolean increaseWriterIndex, int cIndex, ByteBuf buffer) {
/*  281 */     assert buffer != null;
/*  282 */     boolean wasAdded = false;
/*      */     try {
/*  284 */       checkComponentIndex(cIndex);
/*      */ 
/*      */       
/*  287 */       Component c = newComponent(ensureAccessible(buffer), 0);
/*  288 */       int readableBytes = c.length();
/*      */ 
/*      */ 
/*      */       
/*  292 */       checkForOverflow(capacity(), readableBytes);
/*      */       
/*  294 */       addComp(cIndex, c);
/*  295 */       wasAdded = true;
/*  296 */       if (readableBytes > 0 && cIndex < this.componentCount - 1) {
/*  297 */         updateComponentOffsets(cIndex);
/*  298 */       } else if (cIndex > 0) {
/*  299 */         c.reposition((this.components[cIndex - 1]).endOffset);
/*      */       } 
/*  301 */       if (increaseWriterIndex) {
/*  302 */         this.writerIndex += readableBytes;
/*      */       }
/*  304 */       return cIndex;
/*      */     } finally {
/*  306 */       if (!wasAdded) {
/*  307 */         buffer.release();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static ByteBuf ensureAccessible(ByteBuf buf) {
/*  313 */     if (checkAccessible && !buf.isAccessible()) {
/*  314 */       throw new IllegalReferenceCountException(0);
/*      */     }
/*  316 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   private Component newComponent(ByteBuf buf, int offset) {
/*  321 */     int srcIndex = buf.readerIndex();
/*  322 */     int len = buf.readableBytes();
/*      */ 
/*      */     
/*  325 */     ByteBuf unwrapped = buf;
/*  326 */     int unwrappedIndex = srcIndex;
/*  327 */     while (unwrapped instanceof WrappedByteBuf || unwrapped instanceof SwappedByteBuf) {
/*  328 */       unwrapped = unwrapped.unwrap();
/*      */     }
/*      */ 
/*      */     
/*  332 */     if (unwrapped instanceof AbstractUnpooledSlicedByteBuf) {
/*  333 */       unwrappedIndex += ((AbstractUnpooledSlicedByteBuf)unwrapped).idx(0);
/*  334 */       unwrapped = unwrapped.unwrap();
/*  335 */     } else if (unwrapped instanceof PooledSlicedByteBuf) {
/*  336 */       unwrappedIndex += ((PooledSlicedByteBuf)unwrapped).adjustment;
/*  337 */       unwrapped = unwrapped.unwrap();
/*  338 */     } else if (unwrapped instanceof DuplicatedByteBuf || unwrapped instanceof PooledDuplicatedByteBuf) {
/*  339 */       unwrapped = unwrapped.unwrap();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  344 */     ByteBuf slice = (buf.capacity() == len) ? buf : null;
/*      */     
/*  346 */     return new Component(buf.order(ByteOrder.BIG_ENDIAN), srcIndex, unwrapped
/*  347 */         .order(ByteOrder.BIG_ENDIAN), unwrappedIndex, offset, len, slice);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponents(int cIndex, ByteBuf... buffers) {
/*  365 */     ObjectUtil.checkNotNull(buffers, "buffers");
/*  366 */     addComponents0(false, cIndex, buffers, 0);
/*  367 */     consolidateIfNeeded();
/*  368 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private CompositeByteBuf addComponents0(boolean increaseWriterIndex, int cIndex, ByteBuf[] buffers, int arrOffset) {
/*  373 */     int len = buffers.length, count = len - arrOffset;
/*      */     
/*  375 */     int readableBytes = 0;
/*  376 */     int capacity = capacity();
/*  377 */     for (int i = arrOffset; i < buffers.length; i++) {
/*  378 */       ByteBuf b = buffers[i];
/*  379 */       if (b == null) {
/*      */         break;
/*      */       }
/*  382 */       readableBytes += b.readableBytes();
/*      */ 
/*      */ 
/*      */       
/*  386 */       checkForOverflow(capacity, readableBytes);
/*      */     } 
/*      */     
/*  389 */     int ci = Integer.MAX_VALUE;
/*      */     try {
/*  391 */       checkComponentIndex(cIndex);
/*  392 */       shiftComps(cIndex, count);
/*  393 */       int nextOffset = (cIndex > 0) ? (this.components[cIndex - 1]).endOffset : 0;
/*  394 */       for (ci = cIndex; arrOffset < len; arrOffset++, ci++) {
/*  395 */         ByteBuf b = buffers[arrOffset];
/*  396 */         if (b == null) {
/*      */           break;
/*      */         }
/*  399 */         Component c = newComponent(ensureAccessible(b), nextOffset);
/*  400 */         this.components[ci] = c;
/*  401 */         nextOffset = c.endOffset;
/*      */       } 
/*  403 */       return this;
/*      */     } finally {
/*      */       
/*  406 */       if (ci < this.componentCount) {
/*  407 */         if (ci < cIndex + count) {
/*      */           
/*  409 */           removeCompRange(ci, cIndex + count);
/*  410 */           for (; arrOffset < len; arrOffset++) {
/*  411 */             ReferenceCountUtil.safeRelease(buffers[arrOffset]);
/*      */           }
/*      */         } 
/*  414 */         updateComponentOffsets(ci);
/*      */       } 
/*  416 */       if (increaseWriterIndex && ci > cIndex && ci <= this.componentCount) {
/*  417 */         this.writerIndex += (this.components[ci - 1]).endOffset - (this.components[cIndex]).offset;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> int addComponents0(boolean increaseWriterIndex, int cIndex, ByteWrapper<T> wrapper, T[] buffers, int offset) {
/*  424 */     checkComponentIndex(cIndex);
/*      */ 
/*      */     
/*  427 */     for (int i = offset, len = buffers.length; i < len; i++) {
/*  428 */       T b = buffers[i];
/*  429 */       if (b == null) {
/*      */         break;
/*      */       }
/*  432 */       if (!wrapper.isEmpty(b)) {
/*  433 */         cIndex = addComponent0(increaseWriterIndex, cIndex, wrapper.wrap(b)) + 1;
/*  434 */         int size = this.componentCount;
/*  435 */         if (cIndex > size) {
/*  436 */           cIndex = size;
/*      */         }
/*      */       } 
/*      */     } 
/*  440 */     return cIndex;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponents(int cIndex, Iterable<ByteBuf> buffers) {
/*  457 */     return addComponents(false, cIndex, buffers);
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
/*      */   public CompositeByteBuf addFlattenedComponents(boolean increaseWriterIndex, ByteBuf buffer) {
/*      */     CompositeByteBuf from;
/*  471 */     ObjectUtil.checkNotNull(buffer, "buffer");
/*  472 */     int ridx = buffer.readerIndex();
/*  473 */     int widx = buffer.writerIndex();
/*  474 */     if (ridx == widx) {
/*  475 */       buffer.release();
/*  476 */       return this;
/*      */     } 
/*  478 */     if (!(buffer instanceof CompositeByteBuf)) {
/*  479 */       addComponent0(increaseWriterIndex, this.componentCount, buffer);
/*  480 */       consolidateIfNeeded();
/*  481 */       return this;
/*      */     } 
/*      */     
/*  484 */     if (buffer instanceof WrappedCompositeByteBuf) {
/*  485 */       from = (CompositeByteBuf)buffer.unwrap();
/*      */     } else {
/*  487 */       from = (CompositeByteBuf)buffer;
/*      */     } 
/*  489 */     from.checkIndex(ridx, widx - ridx);
/*  490 */     Component[] fromComponents = from.components;
/*  491 */     int compCountBefore = this.componentCount;
/*  492 */     int writerIndexBefore = this.writerIndex;
/*      */     try {
/*  494 */       for (int cidx = from.toComponentIndex0(ridx), newOffset = capacity();; cidx++) {
/*  495 */         Component component = fromComponents[cidx];
/*  496 */         int compOffset = component.offset;
/*  497 */         int fromIdx = Math.max(ridx, compOffset);
/*  498 */         int toIdx = Math.min(widx, component.endOffset);
/*  499 */         int len = toIdx - fromIdx;
/*  500 */         if (len > 0) {
/*  501 */           addComp(this.componentCount, new Component(component.srcBuf
/*  502 */                 .retain(), component.srcIdx(fromIdx), component.buf, component
/*  503 */                 .idx(fromIdx), newOffset, len, null));
/*      */         }
/*  505 */         if (widx == toIdx) {
/*      */           break;
/*      */         }
/*  508 */         newOffset += len;
/*      */       } 
/*  510 */       if (increaseWriterIndex) {
/*  511 */         this.writerIndex = writerIndexBefore + widx - ridx;
/*      */       }
/*  513 */       consolidateIfNeeded();
/*  514 */       buffer.release();
/*  515 */       buffer = null;
/*  516 */       return this;
/*      */     } finally {
/*  518 */       if (buffer != null) {
/*      */         
/*  520 */         if (increaseWriterIndex) {
/*  521 */           this.writerIndex = writerIndexBefore;
/*      */         }
/*  523 */         for (int cidx = this.componentCount - 1; cidx >= compCountBefore; cidx--) {
/*  524 */           this.components[cidx].free();
/*  525 */           removeComp(cidx);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CompositeByteBuf addComponents(boolean increaseIndex, int cIndex, Iterable<ByteBuf> buffers) {
/*  535 */     if (buffers instanceof ByteBuf)
/*      */     {
/*  537 */       return addComponent(increaseIndex, cIndex, (ByteBuf)buffers);
/*      */     }
/*  539 */     ObjectUtil.checkNotNull(buffers, "buffers");
/*  540 */     Iterator<ByteBuf> it = buffers.iterator();
/*      */     try {
/*  542 */       checkComponentIndex(cIndex);
/*      */ 
/*      */       
/*  545 */       while (it.hasNext()) {
/*  546 */         ByteBuf b = it.next();
/*  547 */         if (b == null) {
/*      */           break;
/*      */         }
/*  550 */         cIndex = addComponent0(increaseIndex, cIndex, b) + 1;
/*  551 */         cIndex = Math.min(cIndex, this.componentCount);
/*      */       } 
/*      */     } finally {
/*  554 */       while (it.hasNext()) {
/*  555 */         ReferenceCountUtil.safeRelease(it.next());
/*      */       }
/*      */     } 
/*  558 */     consolidateIfNeeded();
/*  559 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void consolidateIfNeeded() {
/*  569 */     int size = this.componentCount;
/*  570 */     if (size > this.maxNumComponents) {
/*  571 */       consolidate0(0, size);
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkComponentIndex(int cIndex) {
/*  576 */     ensureAccessible();
/*  577 */     if (cIndex < 0 || cIndex > this.componentCount)
/*  578 */       throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", new Object[] {
/*      */               
/*  580 */               Integer.valueOf(cIndex), Integer.valueOf(this.componentCount)
/*      */             })); 
/*      */   }
/*      */   
/*      */   private void checkComponentIndex(int cIndex, int numComponents) {
/*  585 */     ensureAccessible();
/*  586 */     if (cIndex < 0 || cIndex + numComponents > this.componentCount)
/*  587 */       throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", new Object[] {
/*      */ 
/*      */               
/*  590 */               Integer.valueOf(cIndex), Integer.valueOf(numComponents), Integer.valueOf(this.componentCount)
/*      */             })); 
/*      */   }
/*      */   
/*      */   private void updateComponentOffsets(int cIndex) {
/*  595 */     int size = this.componentCount;
/*  596 */     if (size <= cIndex) {
/*      */       return;
/*      */     }
/*      */     
/*  600 */     int nextIndex = (cIndex > 0) ? (this.components[cIndex - 1]).endOffset : 0;
/*  601 */     for (; cIndex < size; cIndex++) {
/*  602 */       Component c = this.components[cIndex];
/*  603 */       c.reposition(nextIndex);
/*  604 */       nextIndex = c.endOffset;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf removeComponent(int cIndex) {
/*  614 */     checkComponentIndex(cIndex);
/*  615 */     Component comp = this.components[cIndex];
/*  616 */     if (this.lastAccessed == comp) {
/*  617 */       this.lastAccessed = null;
/*      */     }
/*  619 */     comp.free();
/*  620 */     removeComp(cIndex);
/*  621 */     if (comp.length() > 0)
/*      */     {
/*  623 */       updateComponentOffsets(cIndex);
/*      */     }
/*  625 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf removeComponents(int cIndex, int numComponents) {
/*  635 */     checkComponentIndex(cIndex, numComponents);
/*      */     
/*  637 */     if (numComponents == 0) {
/*  638 */       return this;
/*      */     }
/*  640 */     int endIndex = cIndex + numComponents;
/*  641 */     boolean needsUpdate = false;
/*  642 */     for (int i = cIndex; i < endIndex; i++) {
/*  643 */       Component c = this.components[i];
/*  644 */       if (c.length() > 0) {
/*  645 */         needsUpdate = true;
/*      */       }
/*  647 */       if (this.lastAccessed == c) {
/*  648 */         this.lastAccessed = null;
/*      */       }
/*  650 */       c.free();
/*      */     } 
/*  652 */     removeCompRange(cIndex, endIndex);
/*      */     
/*  654 */     if (needsUpdate)
/*      */     {
/*  656 */       updateComponentOffsets(cIndex);
/*      */     }
/*  658 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator<ByteBuf> iterator() {
/*  663 */     ensureAccessible();
/*  664 */     return (this.componentCount == 0) ? EMPTY_ITERATOR : new CompositeByteBufIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   protected int forEachByteAsc0(int start, int end, ByteProcessor processor) throws Exception {
/*  669 */     if (end <= start) {
/*  670 */       return -1;
/*      */     }
/*  672 */     for (int i = toComponentIndex0(start), length = end - start; length > 0; i++) {
/*  673 */       Component c = this.components[i];
/*  674 */       if (c.offset != c.endOffset) {
/*      */ 
/*      */         
/*  677 */         ByteBuf s = c.buf;
/*  678 */         int localStart = c.idx(start);
/*  679 */         int localLength = Math.min(length, c.endOffset - start);
/*      */ 
/*      */ 
/*      */         
/*  683 */         int result = (s instanceof AbstractByteBuf) ? ((AbstractByteBuf)s).forEachByteAsc0(localStart, localStart + localLength, processor) : s.forEachByte(localStart, localLength, processor);
/*  684 */         if (result != -1) {
/*  685 */           return result - c.adjustment;
/*      */         }
/*  687 */         start += localLength;
/*  688 */         length -= localLength;
/*      */       } 
/*  690 */     }  return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int forEachByteDesc0(int rStart, int rEnd, ByteProcessor processor) throws Exception {
/*  695 */     if (rEnd > rStart) {
/*  696 */       return -1;
/*      */     }
/*  698 */     for (int i = toComponentIndex0(rStart), length = 1 + rStart - rEnd; length > 0; i--) {
/*  699 */       Component c = this.components[i];
/*  700 */       if (c.offset != c.endOffset) {
/*      */ 
/*      */         
/*  703 */         ByteBuf s = c.buf;
/*  704 */         int localRStart = c.idx(length + rEnd);
/*  705 */         int localLength = Math.min(length, localRStart), localIndex = localRStart - localLength;
/*      */ 
/*      */ 
/*      */         
/*  709 */         int result = (s instanceof AbstractByteBuf) ? ((AbstractByteBuf)s).forEachByteDesc0(localRStart - 1, localIndex, processor) : s.forEachByteDesc(localIndex, localLength, processor);
/*      */         
/*  711 */         if (result != -1) {
/*  712 */           return result - c.adjustment;
/*      */         }
/*  714 */         length -= localLength;
/*      */       } 
/*  716 */     }  return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ByteBuf> decompose(int offset, int length) {
/*  723 */     checkIndex(offset, length);
/*  724 */     if (length == 0) {
/*  725 */       return Collections.emptyList();
/*      */     }
/*      */     
/*  728 */     int componentId = toComponentIndex0(offset);
/*  729 */     int bytesToSlice = length;
/*      */     
/*  731 */     Component firstC = this.components[componentId];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  736 */     ByteBuf slice = firstC.srcBuf.slice(firstC.srcIdx(offset), Math.min(firstC.endOffset - offset, bytesToSlice));
/*  737 */     bytesToSlice -= slice.readableBytes();
/*      */     
/*  739 */     if (bytesToSlice == 0) {
/*  740 */       return Collections.singletonList(slice);
/*      */     }
/*      */     
/*  743 */     List<ByteBuf> sliceList = new ArrayList<>(this.componentCount - componentId);
/*  744 */     sliceList.add(slice);
/*      */ 
/*      */     
/*      */     do {
/*  748 */       Component component = this.components[++componentId];
/*      */ 
/*      */ 
/*      */       
/*  752 */       slice = component.srcBuf.slice(component.srcIdx(component.offset), 
/*  753 */           Math.min(component.length(), bytesToSlice));
/*  754 */       bytesToSlice -= slice.readableBytes();
/*  755 */       sliceList.add(slice);
/*  756 */     } while (bytesToSlice > 0);
/*      */     
/*  758 */     return sliceList;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  763 */     int size = this.componentCount;
/*  764 */     if (size == 0) {
/*  765 */       return false;
/*      */     }
/*  767 */     for (int i = 0; i < size; i++) {
/*  768 */       if (!(this.components[i]).buf.isDirect()) {
/*  769 */         return false;
/*      */       }
/*      */     } 
/*  772 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/*  777 */     switch (this.componentCount) {
/*      */       case 0:
/*  779 */         return true;
/*      */       case 1:
/*  781 */         return (this.components[0]).buf.hasArray();
/*      */     } 
/*  783 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] array() {
/*  789 */     switch (this.componentCount) {
/*      */       case 0:
/*  791 */         return EmptyArrays.EMPTY_BYTES;
/*      */       case 1:
/*  793 */         return (this.components[0]).buf.array();
/*      */     } 
/*  795 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/*      */     Component c;
/*  801 */     switch (this.componentCount) {
/*      */       case 0:
/*  803 */         return 0;
/*      */       case 1:
/*  805 */         c = this.components[0];
/*  806 */         return c.idx(c.buf.arrayOffset());
/*      */     } 
/*  808 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasMemoryAddress() {
/*  814 */     switch (this.componentCount) {
/*      */       case 0:
/*  816 */         return Unpooled.EMPTY_BUFFER.hasMemoryAddress();
/*      */       case 1:
/*  818 */         return (this.components[0]).buf.hasMemoryAddress();
/*      */     } 
/*  820 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public long memoryAddress() {
/*      */     Component c;
/*  826 */     switch (this.componentCount) {
/*      */       case 0:
/*  828 */         return Unpooled.EMPTY_BUFFER.memoryAddress();
/*      */       case 1:
/*  830 */         c = this.components[0];
/*  831 */         return c.buf.memoryAddress() + c.adjustment;
/*      */     } 
/*  833 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int capacity() {
/*  839 */     int size = this.componentCount;
/*  840 */     return (size > 0) ? (this.components[size - 1]).endOffset : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf capacity(int newCapacity) {
/*  845 */     checkNewCapacity(newCapacity);
/*      */     
/*  847 */     int size = this.componentCount, oldCapacity = capacity();
/*  848 */     if (newCapacity > oldCapacity) {
/*  849 */       int paddingLength = newCapacity - oldCapacity;
/*  850 */       ByteBuf padding = allocBuffer(paddingLength).setIndex(0, paddingLength);
/*  851 */       addComponent0(false, size, padding);
/*  852 */       if (this.componentCount >= this.maxNumComponents)
/*      */       {
/*      */         
/*  855 */         consolidateIfNeeded();
/*      */       }
/*  857 */     } else if (newCapacity < oldCapacity) {
/*  858 */       this.lastAccessed = null;
/*  859 */       int i = size - 1;
/*  860 */       for (int bytesToTrim = oldCapacity - newCapacity; i >= 0; i--) {
/*  861 */         Component c = this.components[i];
/*  862 */         int cLength = c.length();
/*  863 */         if (bytesToTrim < cLength) {
/*      */           
/*  865 */           c.endOffset -= bytesToTrim;
/*  866 */           ByteBuf slice = c.slice;
/*  867 */           if (slice != null)
/*      */           {
/*      */             
/*  870 */             c.slice = slice.slice(0, c.length());
/*      */           }
/*      */           break;
/*      */         } 
/*  874 */         c.free();
/*  875 */         bytesToTrim -= cLength;
/*      */       } 
/*  877 */       removeCompRange(i + 1, size);
/*      */       
/*  879 */       if (readerIndex() > newCapacity) {
/*  880 */         setIndex0(newCapacity, newCapacity);
/*  881 */       } else if (this.writerIndex > newCapacity) {
/*  882 */         this.writerIndex = newCapacity;
/*      */       } 
/*      */     } 
/*  885 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*  890 */     return this.alloc;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteOrder order() {
/*  895 */     return ByteOrder.BIG_ENDIAN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int numComponents() {
/*  902 */     return this.componentCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int maxNumComponents() {
/*  909 */     return this.maxNumComponents;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int toComponentIndex(int offset) {
/*  916 */     checkIndex(offset);
/*  917 */     return toComponentIndex0(offset);
/*      */   }
/*      */   
/*      */   private int toComponentIndex0(int offset) {
/*  921 */     int size = this.componentCount;
/*  922 */     if (offset == 0) {
/*  923 */       for (int i = 0; i < size; i++) {
/*  924 */         if ((this.components[i]).endOffset > 0) {
/*  925 */           return i;
/*      */         }
/*      */       } 
/*      */     }
/*  929 */     if (size <= 2) {
/*  930 */       return (size == 1 || offset < (this.components[0]).endOffset) ? 0 : 1;
/*      */     }
/*  932 */     for (int low = 0, high = size; low <= high; ) {
/*  933 */       int mid = low + high >>> 1;
/*  934 */       Component c = this.components[mid];
/*  935 */       if (offset >= c.endOffset) {
/*  936 */         low = mid + 1; continue;
/*  937 */       }  if (offset < c.offset) {
/*  938 */         high = mid - 1; continue;
/*      */       } 
/*  940 */       return mid;
/*      */     } 
/*      */ 
/*      */     
/*  944 */     throw new Error("should not reach here");
/*      */   }
/*      */   
/*      */   public int toByteIndex(int cIndex) {
/*  948 */     checkComponentIndex(cIndex);
/*  949 */     return (this.components[cIndex]).offset;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  954 */     Component c = findComponent(index);
/*  955 */     return c.buf.getByte(c.idx(index));
/*      */   }
/*      */ 
/*      */   
/*      */   protected byte _getByte(int index) {
/*  960 */     Component c = findComponent0(index);
/*  961 */     return c.buf.getByte(c.idx(index));
/*      */   }
/*      */ 
/*      */   
/*      */   protected short _getShort(int index) {
/*  966 */     Component c = findComponent0(index);
/*  967 */     if (index + 2 <= c.endOffset)
/*  968 */       return c.buf.getShort(c.idx(index)); 
/*  969 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  970 */       return (short)((_getByte(index) & 0xFF) << 8 | _getByte(index + 1) & 0xFF);
/*      */     }
/*  972 */     return (short)(_getByte(index) & 0xFF | (_getByte(index + 1) & 0xFF) << 8);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected short _getShortLE(int index) {
/*  978 */     Component c = findComponent0(index);
/*  979 */     if (index + 2 <= c.endOffset)
/*  980 */       return c.buf.getShortLE(c.idx(index)); 
/*  981 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  982 */       return (short)(_getByte(index) & 0xFF | (_getByte(index + 1) & 0xFF) << 8);
/*      */     }
/*  984 */     return (short)((_getByte(index) & 0xFF) << 8 | _getByte(index + 1) & 0xFF);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _getUnsignedMedium(int index) {
/*  990 */     Component c = findComponent0(index);
/*  991 */     if (index + 3 <= c.endOffset)
/*  992 */       return c.buf.getUnsignedMedium(c.idx(index)); 
/*  993 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  994 */       return (_getShort(index) & 0xFFFF) << 8 | _getByte(index + 2) & 0xFF;
/*      */     }
/*  996 */     return _getShort(index) & 0xFFFF | (_getByte(index + 2) & 0xFF) << 16;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _getUnsignedMediumLE(int index) {
/* 1002 */     Component c = findComponent0(index);
/* 1003 */     if (index + 3 <= c.endOffset)
/* 1004 */       return c.buf.getUnsignedMediumLE(c.idx(index)); 
/* 1005 */     if (order() == ByteOrder.BIG_ENDIAN) {
/* 1006 */       return _getShortLE(index) & 0xFFFF | (_getByte(index + 2) & 0xFF) << 16;
/*      */     }
/* 1008 */     return (_getShortLE(index) & 0xFFFF) << 8 | _getByte(index + 2) & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _getInt(int index) {
/* 1014 */     Component c = findComponent0(index);
/* 1015 */     if (index + 4 <= c.endOffset)
/* 1016 */       return c.buf.getInt(c.idx(index)); 
/* 1017 */     if (order() == ByteOrder.BIG_ENDIAN) {
/* 1018 */       return (_getShort(index) & 0xFFFF) << 16 | _getShort(index + 2) & 0xFFFF;
/*      */     }
/* 1020 */     return _getShort(index) & 0xFFFF | (_getShort(index + 2) & 0xFFFF) << 16;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _getIntLE(int index) {
/* 1026 */     Component c = findComponent0(index);
/* 1027 */     if (index + 4 <= c.endOffset)
/* 1028 */       return c.buf.getIntLE(c.idx(index)); 
/* 1029 */     if (order() == ByteOrder.BIG_ENDIAN) {
/* 1030 */       return _getShortLE(index) & 0xFFFF | (_getShortLE(index + 2) & 0xFFFF) << 16;
/*      */     }
/* 1032 */     return (_getShortLE(index) & 0xFFFF) << 16 | _getShortLE(index + 2) & 0xFFFF;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected long _getLong(int index) {
/* 1038 */     Component c = findComponent0(index);
/* 1039 */     if (index + 8 <= c.endOffset)
/* 1040 */       return c.buf.getLong(c.idx(index)); 
/* 1041 */     if (order() == ByteOrder.BIG_ENDIAN) {
/* 1042 */       return (_getInt(index) & 0xFFFFFFFFL) << 32L | _getInt(index + 4) & 0xFFFFFFFFL;
/*      */     }
/* 1044 */     return _getInt(index) & 0xFFFFFFFFL | (_getInt(index + 4) & 0xFFFFFFFFL) << 32L;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected long _getLongLE(int index) {
/* 1050 */     Component c = findComponent0(index);
/* 1051 */     if (index + 8 <= c.endOffset)
/* 1052 */       return c.buf.getLongLE(c.idx(index)); 
/* 1053 */     if (order() == ByteOrder.BIG_ENDIAN) {
/* 1054 */       return _getIntLE(index) & 0xFFFFFFFFL | (_getIntLE(index + 4) & 0xFFFFFFFFL) << 32L;
/*      */     }
/* 1056 */     return (_getIntLE(index) & 0xFFFFFFFFL) << 32L | _getIntLE(index + 4) & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 1062 */     checkDstIndex(index, length, dstIndex, dst.length);
/* 1063 */     if (length == 0) {
/* 1064 */       return this;
/*      */     }
/*      */     
/* 1067 */     int i = toComponentIndex0(index);
/* 1068 */     while (length > 0) {
/* 1069 */       Component c = this.components[i];
/* 1070 */       int localLength = Math.min(length, c.endOffset - index);
/* 1071 */       c.buf.getBytes(c.idx(index), dst, dstIndex, localLength);
/* 1072 */       index += localLength;
/* 1073 */       dstIndex += localLength;
/* 1074 */       length -= localLength;
/* 1075 */       i++;
/*      */     } 
/* 1077 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuffer dst) {
/* 1082 */     int limit = dst.limit();
/* 1083 */     int length = dst.remaining();
/*      */     
/* 1085 */     checkIndex(index, length);
/* 1086 */     if (length == 0) {
/* 1087 */       return this;
/*      */     }
/*      */     
/* 1090 */     int i = toComponentIndex0(index);
/*      */     try {
/* 1092 */       while (length > 0) {
/* 1093 */         Component c = this.components[i];
/* 1094 */         int localLength = Math.min(length, c.endOffset - index);
/* 1095 */         dst.limit(dst.position() + localLength);
/* 1096 */         c.buf.getBytes(c.idx(index), dst);
/* 1097 */         index += localLength;
/* 1098 */         length -= localLength;
/* 1099 */         i++;
/*      */       } 
/*      */     } finally {
/* 1102 */       dst.limit(limit);
/*      */     } 
/* 1104 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 1109 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/* 1110 */     if (length == 0) {
/* 1111 */       return this;
/*      */     }
/*      */     
/* 1114 */     int i = toComponentIndex0(index);
/* 1115 */     while (length > 0) {
/* 1116 */       Component c = this.components[i];
/* 1117 */       int localLength = Math.min(length, c.endOffset - index);
/* 1118 */       c.buf.getBytes(c.idx(index), dst, dstIndex, localLength);
/* 1119 */       index += localLength;
/* 1120 */       dstIndex += localLength;
/* 1121 */       length -= localLength;
/* 1122 */       i++;
/*      */     } 
/* 1124 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 1130 */     int count = nioBufferCount();
/* 1131 */     if (count == 1) {
/* 1132 */       return out.write(internalNioBuffer(index, length));
/*      */     }
/* 1134 */     long writtenBytes = out.write(nioBuffers(index, length));
/* 1135 */     if (writtenBytes > 2147483647L) {
/* 1136 */       return Integer.MAX_VALUE;
/*      */     }
/* 1138 */     return (int)writtenBytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/* 1146 */     int count = nioBufferCount();
/* 1147 */     if (count == 1) {
/* 1148 */       return out.write(internalNioBuffer(index, length), position);
/*      */     }
/* 1150 */     long writtenBytes = 0L;
/* 1151 */     for (ByteBuffer buf : nioBuffers(index, length)) {
/* 1152 */       writtenBytes += out.write(buf, position + writtenBytes);
/*      */     }
/* 1154 */     if (writtenBytes > 2147483647L) {
/* 1155 */       return Integer.MAX_VALUE;
/*      */     }
/* 1157 */     return (int)writtenBytes;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 1163 */     checkIndex(index, length);
/* 1164 */     if (length == 0) {
/* 1165 */       return this;
/*      */     }
/*      */     
/* 1168 */     int i = toComponentIndex0(index);
/* 1169 */     while (length > 0) {
/* 1170 */       Component c = this.components[i];
/* 1171 */       int localLength = Math.min(length, c.endOffset - index);
/* 1172 */       c.buf.getBytes(c.idx(index), out, localLength);
/* 1173 */       index += localLength;
/* 1174 */       length -= localLength;
/* 1175 */       i++;
/*      */     } 
/* 1177 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setByte(int index, int value) {
/* 1182 */     Component c = findComponent(index);
/* 1183 */     c.buf.setByte(c.idx(index), value);
/* 1184 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setByte(int index, int value) {
/* 1189 */     Component c = findComponent0(index);
/* 1190 */     c.buf.setByte(c.idx(index), value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setShort(int index, int value) {
/* 1195 */     checkIndex(index, 2);
/* 1196 */     _setShort(index, value);
/* 1197 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setShort(int index, int value) {
/* 1202 */     Component c = findComponent0(index);
/* 1203 */     if (index + 2 <= c.endOffset) {
/* 1204 */       c.buf.setShort(c.idx(index), value);
/* 1205 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/* 1206 */       _setByte(index, (byte)(value >>> 8));
/* 1207 */       _setByte(index + 1, (byte)value);
/*      */     } else {
/* 1209 */       _setByte(index, (byte)value);
/* 1210 */       _setByte(index + 1, (byte)(value >>> 8));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setShortLE(int index, int value) {
/* 1216 */     Component c = findComponent0(index);
/* 1217 */     if (index + 2 <= c.endOffset) {
/* 1218 */       c.buf.setShortLE(c.idx(index), value);
/* 1219 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/* 1220 */       _setByte(index, (byte)value);
/* 1221 */       _setByte(index + 1, (byte)(value >>> 8));
/*      */     } else {
/* 1223 */       _setByte(index, (byte)(value >>> 8));
/* 1224 */       _setByte(index + 1, (byte)value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setMedium(int index, int value) {
/* 1230 */     checkIndex(index, 3);
/* 1231 */     _setMedium(index, value);
/* 1232 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setMedium(int index, int value) {
/* 1237 */     Component c = findComponent0(index);
/* 1238 */     if (index + 3 <= c.endOffset) {
/* 1239 */       c.buf.setMedium(c.idx(index), value);
/* 1240 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/* 1241 */       _setShort(index, (short)(value >> 8));
/* 1242 */       _setByte(index + 2, (byte)value);
/*      */     } else {
/* 1244 */       _setShort(index, (short)value);
/* 1245 */       _setByte(index + 2, (byte)(value >>> 16));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setMediumLE(int index, int value) {
/* 1251 */     Component c = findComponent0(index);
/* 1252 */     if (index + 3 <= c.endOffset) {
/* 1253 */       c.buf.setMediumLE(c.idx(index), value);
/* 1254 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/* 1255 */       _setShortLE(index, (short)value);
/* 1256 */       _setByte(index + 2, (byte)(value >>> 16));
/*      */     } else {
/* 1258 */       _setShortLE(index, (short)(value >> 8));
/* 1259 */       _setByte(index + 2, (byte)value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setInt(int index, int value) {
/* 1265 */     checkIndex(index, 4);
/* 1266 */     _setInt(index, value);
/* 1267 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setInt(int index, int value) {
/* 1272 */     Component c = findComponent0(index);
/* 1273 */     if (index + 4 <= c.endOffset) {
/* 1274 */       c.buf.setInt(c.idx(index), value);
/* 1275 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/* 1276 */       _setShort(index, (short)(value >>> 16));
/* 1277 */       _setShort(index + 2, (short)value);
/*      */     } else {
/* 1279 */       _setShort(index, (short)value);
/* 1280 */       _setShort(index + 2, (short)(value >>> 16));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setIntLE(int index, int value) {
/* 1286 */     Component c = findComponent0(index);
/* 1287 */     if (index + 4 <= c.endOffset) {
/* 1288 */       c.buf.setIntLE(c.idx(index), value);
/* 1289 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/* 1290 */       _setShortLE(index, (short)value);
/* 1291 */       _setShortLE(index + 2, (short)(value >>> 16));
/*      */     } else {
/* 1293 */       _setShortLE(index, (short)(value >>> 16));
/* 1294 */       _setShortLE(index + 2, (short)value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setLong(int index, long value) {
/* 1300 */     checkIndex(index, 8);
/* 1301 */     _setLong(index, value);
/* 1302 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setLong(int index, long value) {
/* 1307 */     Component c = findComponent0(index);
/* 1308 */     if (index + 8 <= c.endOffset) {
/* 1309 */       c.buf.setLong(c.idx(index), value);
/* 1310 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/* 1311 */       _setInt(index, (int)(value >>> 32L));
/* 1312 */       _setInt(index + 4, (int)value);
/*      */     } else {
/* 1314 */       _setInt(index, (int)value);
/* 1315 */       _setInt(index + 4, (int)(value >>> 32L));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setLongLE(int index, long value) {
/* 1321 */     Component c = findComponent0(index);
/* 1322 */     if (index + 8 <= c.endOffset) {
/* 1323 */       c.buf.setLongLE(c.idx(index), value);
/* 1324 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/* 1325 */       _setIntLE(index, (int)value);
/* 1326 */       _setIntLE(index + 4, (int)(value >>> 32L));
/*      */     } else {
/* 1328 */       _setIntLE(index, (int)(value >>> 32L));
/* 1329 */       _setIntLE(index + 4, (int)value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 1335 */     checkSrcIndex(index, length, srcIndex, src.length);
/* 1336 */     if (length == 0) {
/* 1337 */       return this;
/*      */     }
/*      */     
/* 1340 */     int i = toComponentIndex0(index);
/* 1341 */     while (length > 0) {
/* 1342 */       Component c = this.components[i];
/* 1343 */       int localLength = Math.min(length, c.endOffset - index);
/* 1344 */       c.buf.setBytes(c.idx(index), src, srcIndex, localLength);
/* 1345 */       index += localLength;
/* 1346 */       srcIndex += localLength;
/* 1347 */       length -= localLength;
/* 1348 */       i++;
/*      */     } 
/* 1350 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuffer src) {
/* 1355 */     int limit = src.limit();
/* 1356 */     int length = src.remaining();
/*      */     
/* 1358 */     checkIndex(index, length);
/* 1359 */     if (length == 0) {
/* 1360 */       return this;
/*      */     }
/*      */     
/* 1363 */     int i = toComponentIndex0(index);
/*      */     try {
/* 1365 */       while (length > 0) {
/* 1366 */         Component c = this.components[i];
/* 1367 */         int localLength = Math.min(length, c.endOffset - index);
/* 1368 */         src.limit(src.position() + localLength);
/* 1369 */         c.buf.setBytes(c.idx(index), src);
/* 1370 */         index += localLength;
/* 1371 */         length -= localLength;
/* 1372 */         i++;
/*      */       } 
/*      */     } finally {
/* 1375 */       src.limit(limit);
/*      */     } 
/* 1377 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 1382 */     checkSrcIndex(index, length, srcIndex, src.capacity());
/* 1383 */     if (length == 0) {
/* 1384 */       return this;
/*      */     }
/*      */     
/* 1387 */     int i = toComponentIndex0(index);
/* 1388 */     while (length > 0) {
/* 1389 */       Component c = this.components[i];
/* 1390 */       int localLength = Math.min(length, c.endOffset - index);
/* 1391 */       c.buf.setBytes(c.idx(index), src, srcIndex, localLength);
/* 1392 */       index += localLength;
/* 1393 */       srcIndex += localLength;
/* 1394 */       length -= localLength;
/* 1395 */       i++;
/*      */     } 
/* 1397 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 1402 */     checkIndex(index, length);
/* 1403 */     if (length == 0) {
/* 1404 */       return in.read(EmptyArrays.EMPTY_BYTES);
/*      */     }
/*      */     
/* 1407 */     int i = toComponentIndex0(index);
/* 1408 */     int readBytes = 0;
/*      */     do {
/* 1410 */       Component c = this.components[i];
/* 1411 */       int localLength = Math.min(length, c.endOffset - index);
/* 1412 */       if (localLength == 0) {
/*      */         
/* 1414 */         i++;
/*      */       } else {
/*      */         
/* 1417 */         int localReadBytes = c.buf.setBytes(c.idx(index), in, localLength);
/* 1418 */         if (localReadBytes < 0) {
/* 1419 */           if (readBytes == 0) {
/* 1420 */             return -1;
/*      */           }
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/* 1426 */         index += localReadBytes;
/* 1427 */         length -= localReadBytes;
/* 1428 */         readBytes += localReadBytes;
/* 1429 */         if (localReadBytes == localLength)
/* 1430 */           i++; 
/*      */       } 
/* 1432 */     } while (length > 0);
/*      */     
/* 1434 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 1439 */     checkIndex(index, length);
/* 1440 */     if (length == 0) {
/* 1441 */       return in.read(EMPTY_NIO_BUFFER);
/*      */     }
/*      */     
/* 1444 */     int i = toComponentIndex0(index);
/* 1445 */     int readBytes = 0;
/*      */     do {
/* 1447 */       Component c = this.components[i];
/* 1448 */       int localLength = Math.min(length, c.endOffset - index);
/* 1449 */       if (localLength == 0) {
/*      */         
/* 1451 */         i++;
/*      */       } else {
/*      */         
/* 1454 */         int localReadBytes = c.buf.setBytes(c.idx(index), in, localLength);
/*      */         
/* 1456 */         if (localReadBytes == 0) {
/*      */           break;
/*      */         }
/*      */         
/* 1460 */         if (localReadBytes < 0) {
/* 1461 */           if (readBytes == 0) {
/* 1462 */             return -1;
/*      */           }
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/* 1468 */         index += localReadBytes;
/* 1469 */         length -= localReadBytes;
/* 1470 */         readBytes += localReadBytes;
/* 1471 */         if (localReadBytes == localLength)
/* 1472 */           i++; 
/*      */       } 
/* 1474 */     } while (length > 0);
/*      */     
/* 1476 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/* 1481 */     checkIndex(index, length);
/* 1482 */     if (length == 0) {
/* 1483 */       return in.read(EMPTY_NIO_BUFFER, position);
/*      */     }
/*      */     
/* 1486 */     int i = toComponentIndex0(index);
/* 1487 */     int readBytes = 0;
/*      */     do {
/* 1489 */       Component c = this.components[i];
/* 1490 */       int localLength = Math.min(length, c.endOffset - index);
/* 1491 */       if (localLength == 0) {
/*      */         
/* 1493 */         i++;
/*      */       } else {
/*      */         
/* 1496 */         int localReadBytes = c.buf.setBytes(c.idx(index), in, position + readBytes, localLength);
/*      */         
/* 1498 */         if (localReadBytes == 0) {
/*      */           break;
/*      */         }
/*      */         
/* 1502 */         if (localReadBytes < 0) {
/* 1503 */           if (readBytes == 0) {
/* 1504 */             return -1;
/*      */           }
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/* 1510 */         index += localReadBytes;
/* 1511 */         length -= localReadBytes;
/* 1512 */         readBytes += localReadBytes;
/* 1513 */         if (localReadBytes == localLength)
/* 1514 */           i++; 
/*      */       } 
/* 1516 */     } while (length > 0);
/*      */     
/* 1518 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int index, int length) {
/* 1523 */     checkIndex(index, length);
/* 1524 */     ByteBuf dst = allocBuffer(length);
/* 1525 */     if (length != 0) {
/* 1526 */       copyTo(index, length, toComponentIndex0(index), dst);
/*      */     }
/* 1528 */     return dst;
/*      */   }
/*      */   
/*      */   private void copyTo(int index, int length, int componentId, ByteBuf dst) {
/* 1532 */     int dstIndex = 0;
/* 1533 */     int i = componentId;
/*      */     
/* 1535 */     while (length > 0) {
/* 1536 */       Component c = this.components[i];
/* 1537 */       int localLength = Math.min(length, c.endOffset - index);
/* 1538 */       c.buf.getBytes(c.idx(index), dst, dstIndex, localLength);
/* 1539 */       index += localLength;
/* 1540 */       dstIndex += localLength;
/* 1541 */       length -= localLength;
/* 1542 */       i++;
/*      */     } 
/*      */     
/* 1545 */     dst.writerIndex(dst.capacity());
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf component(int cIndex) {
/* 1563 */     checkComponentIndex(cIndex);
/* 1564 */     return this.components[cIndex].duplicate();
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
/*      */   
/*      */   public ByteBuf componentSlice(int cIndex) {
/* 1579 */     checkComponentIndex(cIndex);
/* 1580 */     return this.components[cIndex].slice();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf componentAtOffset(int offset) {
/* 1590 */     return findComponent(offset).duplicate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf internalComponent(int cIndex) {
/* 1600 */     checkComponentIndex(cIndex);
/* 1601 */     return this.components[cIndex].slice();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf internalComponentAtOffset(int offset) {
/* 1611 */     return findComponent(offset).slice();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Component findComponent(int offset) {
/* 1618 */     Component la = this.lastAccessed;
/* 1619 */     if (la != null && offset >= la.offset && offset < la.endOffset) {
/* 1620 */       ensureAccessible();
/* 1621 */       return la;
/*      */     } 
/* 1623 */     checkIndex(offset);
/* 1624 */     return findIt(offset);
/*      */   }
/*      */   
/*      */   private Component findComponent0(int offset) {
/* 1628 */     Component la = this.lastAccessed;
/* 1629 */     if (la != null && offset >= la.offset && offset < la.endOffset) {
/* 1630 */       return la;
/*      */     }
/* 1632 */     return findIt(offset);
/*      */   }
/*      */   
/*      */   private Component findIt(int offset) {
/* 1636 */     for (int low = 0, high = this.componentCount; low <= high; ) {
/* 1637 */       int mid = low + high >>> 1;
/* 1638 */       Component c = this.components[mid];
/* 1639 */       if (c == null) {
/* 1640 */         throw new IllegalStateException("No component found for offset. Composite buffer layout might be outdated, e.g. from a discardReadBytes call.");
/*      */       }
/*      */       
/* 1643 */       if (offset >= c.endOffset) {
/* 1644 */         low = mid + 1; continue;
/* 1645 */       }  if (offset < c.offset) {
/* 1646 */         high = mid - 1; continue;
/*      */       } 
/* 1648 */       this.lastAccessed = c;
/* 1649 */       return c;
/*      */     } 
/*      */ 
/*      */     
/* 1653 */     throw new Error("should not reach here");
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/* 1658 */     int size = this.componentCount;
/* 1659 */     switch (size) {
/*      */       case 0:
/* 1661 */         return 1;
/*      */       case 1:
/* 1663 */         return (this.components[0]).buf.nioBufferCount();
/*      */     } 
/* 1665 */     int count = 0;
/* 1666 */     for (int i = 0; i < size; i++) {
/* 1667 */       count += (this.components[i]).buf.nioBufferCount();
/*      */     }
/* 1669 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 1675 */     switch (this.componentCount) {
/*      */       case 0:
/* 1677 */         return EMPTY_NIO_BUFFER;
/*      */       case 1:
/* 1679 */         return this.components[0].internalNioBuffer(index, length);
/*      */     } 
/* 1681 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public ByteBuffer nioBuffer(int index, int length) {
/*      */     Component c;
/*      */     ByteBuf buf;
/* 1687 */     checkIndex(index, length);
/*      */     
/* 1689 */     switch (this.componentCount) {
/*      */       case 0:
/* 1691 */         return EMPTY_NIO_BUFFER;
/*      */       case 1:
/* 1693 */         c = this.components[0];
/* 1694 */         buf = c.buf;
/* 1695 */         if (buf.nioBufferCount() == 1) {
/* 1696 */           return buf.nioBuffer(c.idx(index), length);
/*      */         }
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1703 */     ByteBuffer[] buffers = nioBuffers(index, length);
/*      */     
/* 1705 */     if (buffers.length == 1) {
/* 1706 */       return buffers[0];
/*      */     }
/*      */     
/* 1709 */     ByteBuffer merged = ByteBuffer.allocate(length).order(order());
/* 1710 */     for (ByteBuffer byteBuffer : buffers) {
/* 1711 */       merged.put(byteBuffer);
/*      */     }
/*      */     
/* 1714 */     merged.flip();
/* 1715 */     return merged;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 1720 */     checkIndex(index, length);
/* 1721 */     if (length == 0) {
/* 1722 */       return new ByteBuffer[] { EMPTY_NIO_BUFFER };
/*      */     }
/*      */     
/* 1725 */     RecyclableArrayList buffers = RecyclableArrayList.newInstance(this.componentCount);
/*      */     try {
/* 1727 */       int i = toComponentIndex0(index);
/* 1728 */       while (length > 0) {
/* 1729 */         Component c = this.components[i];
/* 1730 */         ByteBuf s = c.buf;
/* 1731 */         int localLength = Math.min(length, c.endOffset - index);
/* 1732 */         switch (s.nioBufferCount()) {
/*      */           case 0:
/* 1734 */             throw new UnsupportedOperationException();
/*      */           case 1:
/* 1736 */             buffers.add(s.nioBuffer(c.idx(index), localLength));
/*      */             break;
/*      */           default:
/* 1739 */             Collections.addAll((Collection<? super ByteBuffer>)buffers, s.nioBuffers(c.idx(index), localLength));
/*      */             break;
/*      */         } 
/* 1742 */         index += localLength;
/* 1743 */         length -= localLength;
/* 1744 */         i++;
/*      */       } 
/*      */       
/* 1747 */       return (ByteBuffer[])buffers.toArray((Object[])EmptyArrays.EMPTY_BYTE_BUFFERS);
/*      */     } finally {
/* 1749 */       buffers.recycle();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf consolidate() {
/* 1757 */     ensureAccessible();
/* 1758 */     consolidate0(0, this.componentCount);
/* 1759 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf consolidate(int cIndex, int numComponents) {
/* 1769 */     checkComponentIndex(cIndex, numComponents);
/* 1770 */     consolidate0(cIndex, numComponents);
/* 1771 */     return this;
/*      */   }
/*      */   
/*      */   private void consolidate0(int cIndex, int numComponents) {
/* 1775 */     if (numComponents <= 1) {
/*      */       return;
/*      */     }
/*      */     
/* 1779 */     int endCIndex = cIndex + numComponents;
/* 1780 */     int startOffset = (cIndex != 0) ? (this.components[cIndex]).offset : 0;
/* 1781 */     int capacity = (this.components[endCIndex - 1]).endOffset - startOffset;
/* 1782 */     ByteBuf consolidated = allocBuffer(capacity);
/*      */     
/* 1784 */     for (int i = cIndex; i < endCIndex; i++) {
/* 1785 */       this.components[i].transferTo(consolidated);
/*      */     }
/* 1787 */     this.lastAccessed = null;
/* 1788 */     removeCompRange(cIndex + 1, endCIndex);
/* 1789 */     this.components[cIndex] = newComponent(consolidated, 0);
/* 1790 */     if (cIndex != 0 || numComponents != this.componentCount) {
/* 1791 */       updateComponentOffsets(cIndex);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf discardReadComponents() {
/* 1799 */     ensureAccessible();
/* 1800 */     int readerIndex = readerIndex();
/* 1801 */     if (readerIndex == 0) {
/* 1802 */       return this;
/*      */     }
/*      */ 
/*      */     
/* 1806 */     int writerIndex = writerIndex();
/* 1807 */     if (readerIndex == writerIndex && writerIndex == capacity()) {
/* 1808 */       for (int i = 0, j = this.componentCount; i < j; i++) {
/* 1809 */         this.components[i].free();
/*      */       }
/* 1811 */       this.lastAccessed = null;
/* 1812 */       clearComps();
/* 1813 */       setIndex(0, 0);
/* 1814 */       adjustMarkers(readerIndex);
/* 1815 */       return this;
/*      */     } 
/*      */ 
/*      */     
/* 1819 */     int firstComponentId = 0;
/* 1820 */     Component c = null;
/* 1821 */     for (int size = this.componentCount; firstComponentId < size; firstComponentId++) {
/* 1822 */       c = this.components[firstComponentId];
/* 1823 */       if (c.endOffset > readerIndex) {
/*      */         break;
/*      */       }
/* 1826 */       c.free();
/*      */     } 
/* 1828 */     if (firstComponentId == 0) {
/* 1829 */       return this;
/*      */     }
/* 1831 */     Component la = this.lastAccessed;
/* 1832 */     if (la != null && la.endOffset <= readerIndex) {
/* 1833 */       this.lastAccessed = null;
/*      */     }
/* 1835 */     removeCompRange(0, firstComponentId);
/*      */ 
/*      */     
/* 1838 */     int offset = c.offset;
/* 1839 */     updateComponentOffsets(0);
/* 1840 */     setIndex(readerIndex - offset, writerIndex - offset);
/* 1841 */     adjustMarkers(offset);
/* 1842 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf discardReadBytes() {
/* 1847 */     ensureAccessible();
/* 1848 */     int readerIndex = readerIndex();
/* 1849 */     if (readerIndex == 0) {
/* 1850 */       return this;
/*      */     }
/*      */ 
/*      */     
/* 1854 */     int writerIndex = writerIndex();
/* 1855 */     if (readerIndex == writerIndex && writerIndex == capacity()) {
/* 1856 */       for (int i = 0, j = this.componentCount; i < j; i++) {
/* 1857 */         this.components[i].free();
/*      */       }
/* 1859 */       this.lastAccessed = null;
/* 1860 */       clearComps();
/* 1861 */       setIndex(0, 0);
/* 1862 */       adjustMarkers(readerIndex);
/* 1863 */       return this;
/*      */     } 
/*      */     
/* 1866 */     int firstComponentId = 0;
/* 1867 */     Component c = null;
/* 1868 */     for (int size = this.componentCount; firstComponentId < size; firstComponentId++) {
/* 1869 */       c = this.components[firstComponentId];
/* 1870 */       if (c.endOffset > readerIndex) {
/*      */         break;
/*      */       }
/* 1873 */       c.free();
/*      */     } 
/*      */ 
/*      */     
/* 1877 */     int trimmedBytes = readerIndex - c.offset;
/* 1878 */     c.offset = 0;
/* 1879 */     c.endOffset -= readerIndex;
/* 1880 */     c.srcAdjustment += readerIndex;
/* 1881 */     c.adjustment += readerIndex;
/* 1882 */     ByteBuf slice = c.slice;
/* 1883 */     if (slice != null)
/*      */     {
/*      */       
/* 1886 */       c.slice = slice.slice(trimmedBytes, c.length());
/*      */     }
/* 1888 */     Component la = this.lastAccessed;
/* 1889 */     if (la != null && la.endOffset <= readerIndex) {
/* 1890 */       this.lastAccessed = null;
/*      */     }
/*      */     
/* 1893 */     removeCompRange(0, firstComponentId);
/*      */ 
/*      */     
/* 1896 */     updateComponentOffsets(0);
/* 1897 */     setIndex(0, writerIndex - readerIndex);
/* 1898 */     adjustMarkers(readerIndex);
/* 1899 */     return this;
/*      */   }
/*      */   
/*      */   private ByteBuf allocBuffer(int capacity) {
/* 1903 */     return this.direct ? alloc().directBuffer(capacity) : alloc().heapBuffer(capacity);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1908 */     String result = super.toString();
/* 1909 */     result = result.substring(0, result.length() - 1);
/* 1910 */     return result + ", components=" + this.componentCount + ')';
/*      */   }
/*      */   
/*      */   static interface ByteWrapper<T> {
/*      */     ByteBuf wrap(T param1T);
/*      */     
/*      */     boolean isEmpty(T param1T); }
/*      */   
/*      */   private static final class Component { final ByteBuf srcBuf;
/*      */     final ByteBuf buf;
/*      */     int srcAdjustment;
/*      */     int adjustment;
/*      */     int offset;
/*      */     int endOffset;
/*      */     private ByteBuf slice;
/*      */     
/*      */     Component(ByteBuf srcBuf, int srcOffset, ByteBuf buf, int bufOffset, int offset, int len, ByteBuf slice) {
/* 1927 */       this.srcBuf = srcBuf;
/* 1928 */       this.srcAdjustment = srcOffset - offset;
/* 1929 */       this.buf = buf;
/* 1930 */       this.adjustment = bufOffset - offset;
/* 1931 */       this.offset = offset;
/* 1932 */       this.endOffset = offset + len;
/* 1933 */       this.slice = slice;
/*      */     }
/*      */     
/*      */     int srcIdx(int index) {
/* 1937 */       return index + this.srcAdjustment;
/*      */     }
/*      */     
/*      */     int idx(int index) {
/* 1941 */       return index + this.adjustment;
/*      */     }
/*      */     
/*      */     int length() {
/* 1945 */       return this.endOffset - this.offset;
/*      */     }
/*      */     
/*      */     void reposition(int newOffset) {
/* 1949 */       int move = newOffset - this.offset;
/* 1950 */       this.endOffset += move;
/* 1951 */       this.srcAdjustment -= move;
/* 1952 */       this.adjustment -= move;
/* 1953 */       this.offset = newOffset;
/*      */     }
/*      */ 
/*      */     
/*      */     void transferTo(ByteBuf dst) {
/* 1958 */       dst.writeBytes(this.buf, idx(this.offset), length());
/* 1959 */       free();
/*      */     }
/*      */     
/*      */     ByteBuf slice() {
/* 1963 */       ByteBuf s = this.slice;
/* 1964 */       if (s == null) {
/* 1965 */         this.slice = s = this.srcBuf.slice(srcIdx(this.offset), length());
/*      */       }
/* 1967 */       return s;
/*      */     }
/*      */     
/*      */     ByteBuf duplicate() {
/* 1971 */       return this.srcBuf.duplicate();
/*      */     }
/*      */ 
/*      */     
/*      */     ByteBuffer internalNioBuffer(int index, int length) {
/* 1976 */       return this.srcBuf.internalNioBuffer(srcIdx(index), length);
/*      */     }
/*      */     
/*      */     void free() {
/* 1980 */       this.slice = null;
/*      */ 
/*      */       
/* 1983 */       this.srcBuf.release();
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readerIndex(int readerIndex) {
/* 1989 */     super.readerIndex(readerIndex);
/* 1990 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writerIndex(int writerIndex) {
/* 1995 */     super.writerIndex(writerIndex);
/* 1996 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setIndex(int readerIndex, int writerIndex) {
/* 2001 */     super.setIndex(readerIndex, writerIndex);
/* 2002 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf clear() {
/* 2007 */     super.clear();
/* 2008 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf markReaderIndex() {
/* 2013 */     super.markReaderIndex();
/* 2014 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf resetReaderIndex() {
/* 2019 */     super.resetReaderIndex();
/* 2020 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf markWriterIndex() {
/* 2025 */     super.markWriterIndex();
/* 2026 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf resetWriterIndex() {
/* 2031 */     super.resetWriterIndex();
/* 2032 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf ensureWritable(int minWritableBytes) {
/* 2037 */     super.ensureWritable(minWritableBytes);
/* 2038 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuf dst) {
/* 2043 */     return getBytes(index, dst, dst.writableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuf dst, int length) {
/* 2048 */     getBytes(index, dst, dst.writerIndex(), length);
/* 2049 */     dst.writerIndex(dst.writerIndex() + length);
/* 2050 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, byte[] dst) {
/* 2055 */     return getBytes(index, dst, 0, dst.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBoolean(int index, boolean value) {
/* 2060 */     return setByte(index, value ? 1 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setChar(int index, int value) {
/* 2065 */     return setShort(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setFloat(int index, float value) {
/* 2070 */     return setInt(index, Float.floatToRawIntBits(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setDouble(int index, double value) {
/* 2075 */     return setLong(index, Double.doubleToRawLongBits(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuf src) {
/* 2080 */     super.setBytes(index, src, src.readableBytes());
/* 2081 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuf src, int length) {
/* 2086 */     super.setBytes(index, src, length);
/* 2087 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, byte[] src) {
/* 2092 */     return setBytes(index, src, 0, src.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setZero(int index, int length) {
/* 2097 */     super.setZero(index, length);
/* 2098 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf dst) {
/* 2103 */     super.readBytes(dst, dst.writableBytes());
/* 2104 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf dst, int length) {
/* 2109 */     super.readBytes(dst, length);
/* 2110 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/* 2115 */     super.readBytes(dst, dstIndex, length);
/* 2116 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(byte[] dst) {
/* 2121 */     super.readBytes(dst, 0, dst.length);
/* 2122 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 2127 */     super.readBytes(dst, dstIndex, length);
/* 2128 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuffer dst) {
/* 2133 */     super.readBytes(dst);
/* 2134 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 2139 */     super.readBytes(out, length);
/* 2140 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf skipBytes(int length) {
/* 2145 */     super.skipBytes(length);
/* 2146 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBoolean(boolean value) {
/* 2151 */     writeByte(value ? 1 : 0);
/* 2152 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeByte(int value) {
/* 2157 */     ensureWritable0(1);
/* 2158 */     _setByte(this.writerIndex++, value);
/* 2159 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeShort(int value) {
/* 2164 */     super.writeShort(value);
/* 2165 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeMedium(int value) {
/* 2170 */     super.writeMedium(value);
/* 2171 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeInt(int value) {
/* 2176 */     super.writeInt(value);
/* 2177 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeLong(long value) {
/* 2182 */     super.writeLong(value);
/* 2183 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeChar(int value) {
/* 2188 */     super.writeShort(value);
/* 2189 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeFloat(float value) {
/* 2194 */     super.writeInt(Float.floatToRawIntBits(value));
/* 2195 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeDouble(double value) {
/* 2200 */     super.writeLong(Double.doubleToRawLongBits(value));
/* 2201 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf src) {
/* 2206 */     super.writeBytes(src, src.readableBytes());
/* 2207 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf src, int length) {
/* 2212 */     super.writeBytes(src, length);
/* 2213 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/* 2218 */     super.writeBytes(src, srcIndex, length);
/* 2219 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(byte[] src) {
/* 2224 */     super.writeBytes(src, 0, src.length);
/* 2225 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/* 2230 */     super.writeBytes(src, srcIndex, length);
/* 2231 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuffer src) {
/* 2236 */     super.writeBytes(src);
/* 2237 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeZero(int length) {
/* 2242 */     super.writeZero(length);
/* 2243 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf retain(int increment) {
/* 2248 */     super.retain(increment);
/* 2249 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf retain() {
/* 2254 */     super.retain();
/* 2255 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf touch() {
/* 2260 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf touch(Object hint) {
/* 2265 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/* 2270 */     return nioBuffers(readerIndex(), readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf discardSomeReadBytes() {
/* 2275 */     return discardReadComponents();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void deallocate() {
/* 2280 */     if (this.freed) {
/*      */       return;
/*      */     }
/*      */     
/* 2284 */     this.freed = true;
/*      */ 
/*      */     
/* 2287 */     for (int i = 0, size = this.componentCount; i < size; i++) {
/* 2288 */       this.components[i].free();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   boolean isAccessible() {
/* 2294 */     return !this.freed;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf unwrap() {
/* 2299 */     return null;
/*      */   }
/*      */   
/*      */   private final class CompositeByteBufIterator implements Iterator<ByteBuf> {
/* 2303 */     private final int size = CompositeByteBuf.this.numComponents();
/*      */     
/*      */     private int index;
/*      */     
/*      */     public boolean hasNext() {
/* 2308 */       return (this.size > this.index);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf next() {
/* 2313 */       if (this.size != CompositeByteBuf.this.numComponents()) {
/* 2314 */         throw new ConcurrentModificationException();
/*      */       }
/* 2316 */       if (!hasNext()) {
/* 2317 */         throw new NoSuchElementException();
/*      */       }
/*      */       try {
/* 2320 */         return CompositeByteBuf.this.components[this.index++].slice();
/* 2321 */       } catch (IndexOutOfBoundsException e) {
/* 2322 */         throw new ConcurrentModificationException();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 2328 */       throw new UnsupportedOperationException("Read-Only");
/*      */     }
/*      */     
/*      */     private CompositeByteBufIterator() {}
/*      */   }
/*      */   
/*      */   private void clearComps() {
/* 2335 */     removeCompRange(0, this.componentCount);
/*      */   }
/*      */   
/*      */   private void removeComp(int i) {
/* 2339 */     removeCompRange(i, i + 1);
/*      */   }
/*      */   
/*      */   private void removeCompRange(int from, int to) {
/* 2343 */     if (from >= to) {
/*      */       return;
/*      */     }
/* 2346 */     int size = this.componentCount;
/* 2347 */     assert from >= 0 && to <= size;
/* 2348 */     if (to < size) {
/* 2349 */       System.arraycopy(this.components, to, this.components, from, size - to);
/*      */     }
/* 2351 */     int newSize = size - to + from;
/* 2352 */     for (int i = newSize; i < size; i++) {
/* 2353 */       this.components[i] = null;
/*      */     }
/* 2355 */     this.componentCount = newSize;
/*      */   }
/*      */   
/*      */   private void addComp(int i, Component c) {
/* 2359 */     shiftComps(i, 1);
/* 2360 */     this.components[i] = c;
/*      */   }
/*      */   
/*      */   private void shiftComps(int i, int count) {
/* 2364 */     int size = this.componentCount, newSize = size + count;
/* 2365 */     assert i >= 0 && i <= size && count > 0;
/* 2366 */     if (newSize > this.components.length) {
/*      */       Component[] newArr;
/* 2368 */       int newArrSize = Math.max(size + (size >> 1), newSize);
/*      */       
/* 2370 */       if (i == size) {
/* 2371 */         newArr = Arrays.<Component, Component>copyOf(this.components, newArrSize, Component[].class);
/*      */       } else {
/* 2373 */         newArr = new Component[newArrSize];
/* 2374 */         if (i > 0) {
/* 2375 */           System.arraycopy(this.components, 0, newArr, 0, i);
/*      */         }
/* 2377 */         if (i < size) {
/* 2378 */           System.arraycopy(this.components, i, newArr, i + count, size - i);
/*      */         }
/*      */       } 
/* 2381 */       this.components = newArr;
/* 2382 */     } else if (i < size) {
/* 2383 */       System.arraycopy(this.components, i, this.components, i + count, size - i);
/*      */     } 
/* 2385 */     this.componentCount = newSize;
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
/*      */   public boolean release(int decrement) {
/* 2398 */     return super.release(decrement);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\CompositeByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */