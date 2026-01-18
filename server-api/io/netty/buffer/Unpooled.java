/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Unpooled
/*     */ {
/*  75 */   private static final ByteBufAllocator ALLOC = UnpooledByteBufAllocator.DEFAULT;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static final ByteBuf EMPTY_BUFFER = ALLOC.buffer(0, 0);
/*     */   
/*     */   static {
/*  94 */     assert EMPTY_BUFFER instanceof EmptyByteBuf : "EMPTY_BUFFER must be an EmptyByteBuf.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf buffer() {
/* 102 */     return ALLOC.heapBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf directBuffer() {
/* 110 */     return ALLOC.directBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf buffer(int initialCapacity) {
/* 119 */     return ALLOC.heapBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf directBuffer(int initialCapacity) {
/* 128 */     return ALLOC.directBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf buffer(int initialCapacity, int maxCapacity) {
/* 138 */     return ALLOC.heapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
/* 148 */     return ALLOC.directBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(byte[] array) {
/* 157 */     if (array.length == 0) {
/* 158 */       return EMPTY_BUFFER;
/*     */     }
/* 160 */     return new UnpooledHeapByteBuf(ALLOC, array, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(byte[] array, int offset, int length) {
/* 169 */     if (length == 0) {
/* 170 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 173 */     if (offset == 0 && length == array.length) {
/* 174 */       return wrappedBuffer(array);
/*     */     }
/*     */     
/* 177 */     return wrappedBuffer(array).slice(offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuffer buffer) {
/* 186 */     if (!buffer.hasRemaining()) {
/* 187 */       return EMPTY_BUFFER;
/*     */     }
/* 189 */     if (!buffer.isDirect() && buffer.hasArray())
/* 190 */       return wrappedBuffer(buffer
/* 191 */           .array(), buffer
/* 192 */           .arrayOffset() + buffer.position(), buffer
/* 193 */           .remaining()).order(buffer.order()); 
/* 194 */     if (PlatformDependent.hasUnsafe()) {
/* 195 */       if (buffer.isReadOnly()) {
/* 196 */         if (buffer.isDirect()) {
/* 197 */           return new ReadOnlyUnsafeDirectByteBuf(ALLOC, buffer);
/*     */         }
/* 199 */         return new ReadOnlyByteBufferBuf(ALLOC, buffer);
/*     */       } 
/*     */       
/* 202 */       return new UnpooledUnsafeDirectByteBuf(ALLOC, buffer, buffer.remaining());
/*     */     } 
/*     */     
/* 205 */     if (buffer.isReadOnly()) {
/* 206 */       return new ReadOnlyByteBufferBuf(ALLOC, buffer);
/*     */     }
/* 208 */     return new UnpooledDirectByteBuf(ALLOC, buffer, buffer.remaining());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(long memoryAddress, int size, boolean doFree) {
/* 218 */     return new WrappedUnpooledUnsafeDirectByteBuf(ALLOC, memoryAddress, size, doFree);
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
/*     */   public static ByteBuf wrappedBuffer(ByteBuf buffer) {
/* 230 */     if (buffer.isReadable()) {
/* 231 */       return buffer.slice();
/*     */     }
/* 233 */     buffer.release();
/* 234 */     return EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(byte[]... arrays) {
/* 244 */     return wrappedBuffer(arrays.length, arrays);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuf... buffers) {
/* 255 */     return wrappedBuffer(buffers.length, buffers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuffer... buffers) {
/* 264 */     return wrappedBuffer(buffers.length, buffers);
/*     */   }
/*     */   
/*     */   static <T> ByteBuf wrappedBuffer(int maxNumComponents, CompositeByteBuf.ByteWrapper<T> wrapper, T[] array) {
/* 268 */     switch (array.length) {
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
/*     */       case 0:
/* 288 */         return EMPTY_BUFFER;
/*     */       case 1:
/*     */         if (!wrapper.isEmpty(array[0]))
/*     */           return wrapper.wrap(array[0]); 
/*     */     }  for (int i = 0, len = array.length; i < len; i++) {
/*     */       T bytes = array[i]; if (bytes == null)
/*     */         return EMPTY_BUFFER; 
/*     */       if (!wrapper.isEmpty(bytes))
/*     */         return new CompositeByteBuf(ALLOC, false, maxNumComponents, wrapper, array, i); 
/* 297 */     }  } public static ByteBuf wrappedBuffer(int maxNumComponents, byte[]... arrays) { return wrappedBuffer(maxNumComponents, (CompositeByteBuf.ByteWrapper)CompositeByteBuf.BYTE_ARRAY_WRAPPER, arrays); }
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
/*     */   public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuf... buffers) {
/*     */     ByteBuf buffer;
/* 310 */     switch (buffers.length) {
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
/*     */       case 0:
/* 331 */         return EMPTY_BUFFER;
/*     */       case 1:
/*     */         buffer = buffers[0]; if (buffer.isReadable())
/*     */           return wrappedBuffer(buffer.order(BIG_ENDIAN));  buffer.release();
/*     */     }  for (int i = 0; i < buffers.length; i++) {
/*     */       ByteBuf buf = buffers[i];
/*     */       if (buf.isReadable())
/*     */         return new CompositeByteBuf(ALLOC, false, maxNumComponents, buffers, i); 
/*     */       buf.release();
/* 340 */     }  } public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuffer... buffers) { return wrappedBuffer(maxNumComponents, CompositeByteBuf.BYTE_BUFFER_WRAPPER, buffers); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompositeByteBuf compositeBuffer() {
/* 347 */     return compositeBuffer(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompositeByteBuf compositeBuffer(int maxNumComponents) {
/* 354 */     return new CompositeByteBuf(ALLOC, false, maxNumComponents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(byte[] array) {
/* 363 */     if (array.length == 0) {
/* 364 */       return EMPTY_BUFFER;
/*     */     }
/* 366 */     return wrappedBuffer((byte[])array.clone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(byte[] array, int offset, int length) {
/* 376 */     if (length == 0) {
/* 377 */       return EMPTY_BUFFER;
/*     */     }
/* 379 */     byte[] copy = PlatformDependent.allocateUninitializedArray(length);
/* 380 */     System.arraycopy(array, offset, copy, 0, length);
/* 381 */     return wrappedBuffer(copy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(ByteBuffer buffer) {
/* 391 */     int length = buffer.remaining();
/* 392 */     if (length == 0) {
/* 393 */       return EMPTY_BUFFER;
/*     */     }
/* 395 */     byte[] copy = PlatformDependent.allocateUninitializedArray(length);
/*     */ 
/*     */     
/* 398 */     ByteBuffer duplicate = buffer.duplicate();
/* 399 */     duplicate.get(copy);
/* 400 */     return wrappedBuffer(copy).order(duplicate.order());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(ByteBuf buffer) {
/* 410 */     int readable = buffer.readableBytes();
/* 411 */     if (readable > 0) {
/* 412 */       ByteBuf copy = buffer(readable);
/* 413 */       copy.writeBytes(buffer, buffer.readerIndex(), readable);
/* 414 */       return copy;
/*     */     } 
/* 416 */     return EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(byte[]... arrays) {
/* 427 */     switch (arrays.length) {
/*     */       case 0:
/* 429 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 431 */         if ((arrays[0]).length == 0) {
/* 432 */           return EMPTY_BUFFER;
/*     */         }
/* 434 */         return copiedBuffer(arrays[0]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 439 */     int length = 0;
/* 440 */     for (byte[] a : arrays) {
/* 441 */       if (Integer.MAX_VALUE - length < a.length) {
/* 442 */         throw new IllegalArgumentException("The total length of the specified arrays is too big.");
/*     */       }
/*     */       
/* 445 */       length += a.length;
/*     */     } 
/*     */     
/* 448 */     if (length == 0) {
/* 449 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 452 */     byte[] mergedArray = PlatformDependent.allocateUninitializedArray(length);
/* 453 */     for (int i = 0, j = 0; i < arrays.length; i++) {
/* 454 */       byte[] a = arrays[i];
/* 455 */       System.arraycopy(a, 0, mergedArray, j, a.length);
/* 456 */       j += a.length;
/*     */     } 
/*     */     
/* 459 */     return wrappedBuffer(mergedArray);
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
/*     */   public static ByteBuf copiedBuffer(ByteBuf... buffers) {
/* 473 */     switch (buffers.length) {
/*     */       case 0:
/* 475 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 477 */         return copiedBuffer(buffers[0]);
/*     */     } 
/*     */ 
/*     */     
/* 481 */     ByteOrder order = null;
/* 482 */     int length = 0;
/* 483 */     for (ByteBuf b : buffers) {
/* 484 */       int bLen = b.readableBytes();
/* 485 */       if (bLen > 0) {
/*     */ 
/*     */         
/* 488 */         if (Integer.MAX_VALUE - length < bLen) {
/* 489 */           throw new IllegalArgumentException("The total length of the specified buffers is too big.");
/*     */         }
/*     */         
/* 492 */         length += bLen;
/* 493 */         if (order != null) {
/* 494 */           if (!order.equals(b.order())) {
/* 495 */             throw new IllegalArgumentException("inconsistent byte order");
/*     */           }
/*     */         } else {
/* 498 */           order = b.order();
/*     */         } 
/*     */       } 
/*     */     } 
/* 502 */     if (length == 0) {
/* 503 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 506 */     byte[] mergedArray = PlatformDependent.allocateUninitializedArray(length);
/* 507 */     for (int i = 0, j = 0; i < buffers.length; i++) {
/* 508 */       ByteBuf b = buffers[i];
/* 509 */       int bLen = b.readableBytes();
/* 510 */       b.getBytes(b.readerIndex(), mergedArray, j, bLen);
/* 511 */       j += bLen;
/*     */     } 
/*     */     
/* 514 */     return wrappedBuffer(mergedArray).order(order);
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
/*     */   public static ByteBuf copiedBuffer(ByteBuffer... buffers) {
/* 528 */     switch (buffers.length) {
/*     */       case 0:
/* 530 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 532 */         return copiedBuffer(buffers[0]);
/*     */     } 
/*     */ 
/*     */     
/* 536 */     ByteOrder order = null;
/* 537 */     int length = 0;
/* 538 */     for (ByteBuffer b : buffers) {
/* 539 */       int bLen = b.remaining();
/* 540 */       if (bLen > 0) {
/*     */ 
/*     */         
/* 543 */         if (Integer.MAX_VALUE - length < bLen) {
/* 544 */           throw new IllegalArgumentException("The total length of the specified buffers is too big.");
/*     */         }
/*     */         
/* 547 */         length += bLen;
/* 548 */         if (order != null) {
/* 549 */           if (!order.equals(b.order())) {
/* 550 */             throw new IllegalArgumentException("inconsistent byte order");
/*     */           }
/*     */         } else {
/* 553 */           order = b.order();
/*     */         } 
/*     */       } 
/*     */     } 
/* 557 */     if (length == 0) {
/* 558 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 561 */     byte[] mergedArray = PlatformDependent.allocateUninitializedArray(length);
/* 562 */     for (int i = 0, j = 0; i < buffers.length; i++) {
/*     */ 
/*     */       
/* 565 */       ByteBuffer b = buffers[i].duplicate();
/* 566 */       int bLen = b.remaining();
/* 567 */       b.get(mergedArray, j, bLen);
/* 568 */       j += bLen;
/*     */     } 
/*     */     
/* 571 */     return wrappedBuffer(mergedArray).order(order);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(CharSequence string, Charset charset) {
/* 581 */     ObjectUtil.checkNotNull(string, "string");
/* 582 */     if (CharsetUtil.UTF_8.equals(charset)) {
/* 583 */       return copiedBufferUtf8(string);
/*     */     }
/* 585 */     if (CharsetUtil.US_ASCII.equals(charset)) {
/* 586 */       return copiedBufferAscii(string);
/*     */     }
/* 588 */     if (string instanceof CharBuffer) {
/* 589 */       return copiedBuffer((CharBuffer)string, charset);
/*     */     }
/*     */     
/* 592 */     return copiedBuffer(CharBuffer.wrap(string), charset);
/*     */   }
/*     */   
/*     */   private static ByteBuf copiedBufferUtf8(CharSequence string) {
/* 596 */     boolean release = true;
/*     */     
/* 598 */     int byteLength = ByteBufUtil.utf8Bytes(string);
/* 599 */     ByteBuf buffer = ALLOC.heapBuffer(byteLength);
/*     */     try {
/* 601 */       ByteBufUtil.reserveAndWriteUtf8(buffer, string, byteLength);
/* 602 */       release = false;
/* 603 */       return buffer;
/*     */     } finally {
/* 605 */       if (release) {
/* 606 */         buffer.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ByteBuf copiedBufferAscii(CharSequence string) {
/* 612 */     boolean release = true;
/*     */     
/* 614 */     ByteBuf buffer = ALLOC.heapBuffer(string.length());
/*     */     try {
/* 616 */       ByteBufUtil.writeAscii(buffer, string);
/* 617 */       release = false;
/* 618 */       return buffer;
/*     */     } finally {
/* 620 */       if (release) {
/* 621 */         buffer.release();
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
/*     */   public static ByteBuf copiedBuffer(CharSequence string, int offset, int length, Charset charset) {
/* 634 */     ObjectUtil.checkNotNull(string, "string");
/* 635 */     if (length == 0) {
/* 636 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 639 */     if (string instanceof CharBuffer) {
/* 640 */       CharBuffer buf = (CharBuffer)string;
/* 641 */       if (buf.hasArray()) {
/* 642 */         return copiedBuffer(buf
/* 643 */             .array(), buf
/* 644 */             .arrayOffset() + buf.position() + offset, length, charset);
/*     */       }
/*     */ 
/*     */       
/* 648 */       buf = buf.slice();
/* 649 */       buf.limit(length);
/* 650 */       buf.position(offset);
/* 651 */       return copiedBuffer(buf, charset);
/*     */     } 
/*     */     
/* 654 */     return copiedBuffer(CharBuffer.wrap(string, offset, offset + length), charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(char[] array, Charset charset) {
/* 664 */     ObjectUtil.checkNotNull(array, "array");
/* 665 */     return copiedBuffer(array, 0, array.length, charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(char[] array, int offset, int length, Charset charset) {
/* 675 */     ObjectUtil.checkNotNull(array, "array");
/* 676 */     if (length == 0) {
/* 677 */       return EMPTY_BUFFER;
/*     */     }
/* 679 */     return copiedBuffer(CharBuffer.wrap(array, offset, length), charset);
/*     */   }
/*     */   
/*     */   private static ByteBuf copiedBuffer(CharBuffer buffer, Charset charset) {
/* 683 */     return ByteBufUtil.encodeString0(ALLOC, true, buffer, charset, 0);
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
/*     */   @Deprecated
/*     */   public static ByteBuf unmodifiableBuffer(ByteBuf buffer) {
/* 696 */     ByteOrder endianness = buffer.order();
/* 697 */     if (endianness == BIG_ENDIAN) {
/* 698 */       return newReadyOnlyBuffer(buffer);
/*     */     }
/*     */     
/* 701 */     return newReadyOnlyBuffer(buffer.order(BIG_ENDIAN)).order(LITTLE_ENDIAN);
/*     */   }
/*     */   
/*     */   private static ReadOnlyByteBuf newReadyOnlyBuffer(ByteBuf buffer) {
/* 705 */     return (buffer instanceof AbstractByteBuf) ? 
/* 706 */       new ReadOnlyAbstractByteBuf((AbstractByteBuf)buffer) : 
/* 707 */       new ReadOnlyByteBuf(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyInt(int value) {
/* 714 */     ByteBuf buf = buffer(4);
/* 715 */     buf.writeInt(value);
/* 716 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyInt(int... values) {
/* 723 */     if (values == null || values.length == 0) {
/* 724 */       return EMPTY_BUFFER;
/*     */     }
/* 726 */     ByteBuf buffer = buffer(values.length * 4);
/* 727 */     for (int v : values) {
/* 728 */       buffer.writeInt(v);
/*     */     }
/* 730 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyShort(int value) {
/* 737 */     ByteBuf buf = buffer(2);
/* 738 */     buf.writeShort(value);
/* 739 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyShort(short... values) {
/* 746 */     if (values == null || values.length == 0) {
/* 747 */       return EMPTY_BUFFER;
/*     */     }
/* 749 */     ByteBuf buffer = buffer(values.length * 2);
/* 750 */     for (int v : values) {
/* 751 */       buffer.writeShort(v);
/*     */     }
/* 753 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyShort(int... values) {
/* 760 */     if (values == null || values.length == 0) {
/* 761 */       return EMPTY_BUFFER;
/*     */     }
/* 763 */     ByteBuf buffer = buffer(values.length * 2);
/* 764 */     for (int v : values) {
/* 765 */       buffer.writeShort(v);
/*     */     }
/* 767 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyMedium(int value) {
/* 774 */     ByteBuf buf = buffer(3);
/* 775 */     buf.writeMedium(value);
/* 776 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyMedium(int... values) {
/* 783 */     if (values == null || values.length == 0) {
/* 784 */       return EMPTY_BUFFER;
/*     */     }
/* 786 */     ByteBuf buffer = buffer(values.length * 3);
/* 787 */     for (int v : values) {
/* 788 */       buffer.writeMedium(v);
/*     */     }
/* 790 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyLong(long value) {
/* 797 */     ByteBuf buf = buffer(8);
/* 798 */     buf.writeLong(value);
/* 799 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyLong(long... values) {
/* 806 */     if (values == null || values.length == 0) {
/* 807 */       return EMPTY_BUFFER;
/*     */     }
/* 809 */     ByteBuf buffer = buffer(values.length * 8);
/* 810 */     for (long v : values) {
/* 811 */       buffer.writeLong(v);
/*     */     }
/* 813 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyBoolean(boolean value) {
/* 820 */     ByteBuf buf = buffer(1);
/* 821 */     buf.writeBoolean(value);
/* 822 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyBoolean(boolean... values) {
/* 829 */     if (values == null || values.length == 0) {
/* 830 */       return EMPTY_BUFFER;
/*     */     }
/* 832 */     ByteBuf buffer = buffer(values.length);
/* 833 */     for (boolean v : values) {
/* 834 */       buffer.writeBoolean(v);
/*     */     }
/* 836 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyFloat(float value) {
/* 843 */     ByteBuf buf = buffer(4);
/* 844 */     buf.writeFloat(value);
/* 845 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyFloat(float... values) {
/* 852 */     if (values == null || values.length == 0) {
/* 853 */       return EMPTY_BUFFER;
/*     */     }
/* 855 */     ByteBuf buffer = buffer(values.length * 4);
/* 856 */     for (float v : values) {
/* 857 */       buffer.writeFloat(v);
/*     */     }
/* 859 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyDouble(double value) {
/* 866 */     ByteBuf buf = buffer(8);
/* 867 */     buf.writeDouble(value);
/* 868 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyDouble(double... values) {
/* 875 */     if (values == null || values.length == 0) {
/* 876 */       return EMPTY_BUFFER;
/*     */     }
/* 878 */     ByteBuf buffer = buffer(values.length * 8);
/* 879 */     for (double v : values) {
/* 880 */       buffer.writeDouble(v);
/*     */     }
/* 882 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf unreleasableBuffer(ByteBuf buf) {
/* 889 */     return new UnreleasableByteBuf(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static ByteBuf unmodifiableBuffer(ByteBuf... buffers) {
/* 900 */     return wrappedUnmodifiableBuffer(true, buffers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedUnmodifiableBuffer(ByteBuf... buffers) {
/* 910 */     return wrappedUnmodifiableBuffer(false, buffers);
/*     */   }
/*     */   
/*     */   private static ByteBuf wrappedUnmodifiableBuffer(boolean copy, ByteBuf... buffers) {
/* 914 */     switch (buffers.length) {
/*     */       case 0:
/* 916 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 918 */         return buffers[0].asReadOnly();
/*     */     } 
/* 920 */     if (copy) {
/* 921 */       buffers = Arrays.<ByteBuf, ByteBuf>copyOf(buffers, buffers.length, ByteBuf[].class);
/*     */     }
/* 923 */     return new FixedCompositeByteBuf(ALLOC, buffers);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\Unpooled.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */