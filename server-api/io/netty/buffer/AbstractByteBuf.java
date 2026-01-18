/*      */ package io.netty.buffer;
/*      */ 
/*      */ import io.netty.util.AsciiString;
/*      */ import io.netty.util.ByteProcessor;
/*      */ import io.netty.util.CharsetUtil;
/*      */ import io.netty.util.IllegalReferenceCountException;
/*      */ import io.netty.util.ResourceLeakDetector;
/*      */ import io.netty.util.ResourceLeakDetectorFactory;
/*      */ import io.netty.util.internal.MathUtil;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.SystemPropertyUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.nio.charset.Charset;
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
/*      */ public abstract class AbstractByteBuf
/*      */   extends ByteBuf
/*      */ {
/*   48 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractByteBuf.class);
/*      */   
/*      */   private static final String LEGACY_PROP_CHECK_ACCESSIBLE = "io.netty.buffer.bytebuf.checkAccessible";
/*      */   private static final String PROP_CHECK_ACCESSIBLE = "io.netty.buffer.checkAccessible";
/*      */   static final boolean checkAccessible;
/*      */   private static final String PROP_CHECK_BOUNDS = "io.netty.buffer.checkBounds";
/*      */   
/*      */   static {
/*   56 */     if (SystemPropertyUtil.contains("io.netty.buffer.checkAccessible")) {
/*   57 */       checkAccessible = SystemPropertyUtil.getBoolean("io.netty.buffer.checkAccessible", true);
/*      */     } else {
/*   59 */       checkAccessible = SystemPropertyUtil.getBoolean("io.netty.buffer.bytebuf.checkAccessible", true);
/*      */     } 
/*   61 */   } private static final boolean checkBounds = SystemPropertyUtil.getBoolean("io.netty.buffer.checkBounds", true); static {
/*   62 */     if (logger.isDebugEnabled()) {
/*   63 */       logger.debug("-D{}: {}", "io.netty.buffer.checkAccessible", Boolean.valueOf(checkAccessible));
/*   64 */       logger.debug("-D{}: {}", "io.netty.buffer.checkBounds", Boolean.valueOf(checkBounds));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*   69 */   static final ResourceLeakDetector<ByteBuf> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ByteBuf.class);
/*      */   
/*      */   int readerIndex;
/*      */   int writerIndex;
/*      */   private int markedReaderIndex;
/*      */   private int markedWriterIndex;
/*      */   private int maxCapacity;
/*      */   
/*      */   protected AbstractByteBuf(int maxCapacity) {
/*   78 */     ObjectUtil.checkPositiveOrZero(maxCapacity, "maxCapacity");
/*   79 */     this.maxCapacity = maxCapacity;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/*   84 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf asReadOnly() {
/*   90 */     if (isReadOnly()) {
/*   91 */       return this;
/*      */     }
/*   93 */     return Unpooled.unmodifiableBuffer(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxCapacity() {
/*   98 */     return this.maxCapacity;
/*      */   }
/*      */   
/*      */   protected final void maxCapacity(int maxCapacity) {
/*  102 */     this.maxCapacity = maxCapacity;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readerIndex() {
/*  107 */     return this.readerIndex;
/*      */   }
/*      */   
/*      */   private static void checkIndexBounds(int readerIndex, int writerIndex, int capacity) {
/*  111 */     if (readerIndex < 0 || readerIndex > writerIndex || writerIndex > capacity) {
/*  112 */       throw new IndexOutOfBoundsException(String.format("readerIndex: %d, writerIndex: %d (expected: 0 <= readerIndex <= writerIndex <= capacity(%d))", new Object[] {
/*      */               
/*  114 */               Integer.valueOf(readerIndex), Integer.valueOf(writerIndex), Integer.valueOf(capacity)
/*      */             }));
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteBuf readerIndex(int readerIndex) {
/*  120 */     if (checkBounds) {
/*  121 */       checkIndexBounds(readerIndex, this.writerIndex, capacity());
/*      */     }
/*  123 */     this.readerIndex = readerIndex;
/*  124 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writerIndex() {
/*  129 */     return this.writerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writerIndex(int writerIndex) {
/*  134 */     if (checkBounds) {
/*  135 */       checkIndexBounds(this.readerIndex, writerIndex, capacity());
/*      */     }
/*  137 */     this.writerIndex = writerIndex;
/*  138 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/*  143 */     if (checkBounds) {
/*  144 */       checkIndexBounds(readerIndex, writerIndex, capacity());
/*      */     }
/*  146 */     setIndex0(readerIndex, writerIndex);
/*  147 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf clear() {
/*  152 */     this.readerIndex = this.writerIndex = 0;
/*  153 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable() {
/*  158 */     return (this.writerIndex > this.readerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable(int numBytes) {
/*  163 */     return (this.writerIndex - this.readerIndex >= numBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  168 */     return (capacity() > this.writerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable(int numBytes) {
/*  173 */     return (capacity() - this.writerIndex >= numBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readableBytes() {
/*  178 */     return this.writerIndex - this.readerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writableBytes() {
/*  183 */     return capacity() - this.writerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxWritableBytes() {
/*  188 */     return maxCapacity() - this.writerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markReaderIndex() {
/*  193 */     this.markedReaderIndex = this.readerIndex;
/*  194 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetReaderIndex() {
/*  199 */     readerIndex(this.markedReaderIndex);
/*  200 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markWriterIndex() {
/*  205 */     this.markedWriterIndex = this.writerIndex;
/*  206 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetWriterIndex() {
/*  211 */     writerIndex(this.markedWriterIndex);
/*  212 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardReadBytes() {
/*  217 */     if (this.readerIndex == 0) {
/*  218 */       ensureAccessible();
/*  219 */       return this;
/*      */     } 
/*      */     
/*  222 */     if (this.readerIndex != this.writerIndex) {
/*  223 */       setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
/*  224 */       this.writerIndex -= this.readerIndex;
/*  225 */       adjustMarkers(this.readerIndex);
/*  226 */       this.readerIndex = 0;
/*      */     } else {
/*  228 */       ensureAccessible();
/*  229 */       adjustMarkers(this.readerIndex);
/*  230 */       this.writerIndex = this.readerIndex = 0;
/*      */     } 
/*  232 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardSomeReadBytes() {
/*  237 */     if (this.readerIndex > 0) {
/*  238 */       if (this.readerIndex == this.writerIndex) {
/*  239 */         ensureAccessible();
/*  240 */         adjustMarkers(this.readerIndex);
/*  241 */         this.writerIndex = this.readerIndex = 0;
/*  242 */         return this;
/*      */       } 
/*      */       
/*  245 */       if (this.readerIndex >= capacity() >>> 1) {
/*  246 */         setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
/*  247 */         this.writerIndex -= this.readerIndex;
/*  248 */         adjustMarkers(this.readerIndex);
/*  249 */         this.readerIndex = 0;
/*  250 */         return this;
/*      */       } 
/*      */     } 
/*  253 */     ensureAccessible();
/*  254 */     return this;
/*      */   }
/*      */   
/*      */   protected final void adjustMarkers(int decrement) {
/*  258 */     if (this.markedReaderIndex <= decrement) {
/*  259 */       this.markedReaderIndex = 0;
/*  260 */       if (this.markedWriterIndex <= decrement) {
/*  261 */         this.markedWriterIndex = 0;
/*      */       } else {
/*  263 */         this.markedWriterIndex -= decrement;
/*      */       } 
/*      */     } else {
/*  266 */       this.markedReaderIndex -= decrement;
/*  267 */       this.markedWriterIndex -= decrement;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void trimIndicesToCapacity(int newCapacity) {
/*  273 */     if (writerIndex() > newCapacity) {
/*  274 */       setIndex0(Math.min(readerIndex(), newCapacity), newCapacity);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf ensureWritable(int minWritableBytes) {
/*  280 */     ensureWritable0(ObjectUtil.checkPositiveOrZero(minWritableBytes, "minWritableBytes"));
/*  281 */     return this;
/*      */   }
/*      */   
/*      */   final void ensureWritable0(int minWritableBytes) {
/*  285 */     int writerIndex = writerIndex();
/*  286 */     int targetCapacity = writerIndex + minWritableBytes;
/*      */     
/*  288 */     if ((((targetCapacity >= 0) ? 1 : 0) & ((targetCapacity <= capacity()) ? 1 : 0)) != 0) {
/*  289 */       ensureAccessible();
/*      */       return;
/*      */     } 
/*  292 */     if (checkBounds && (targetCapacity < 0 || targetCapacity > this.maxCapacity)) {
/*  293 */       ensureAccessible();
/*  294 */       throw new IndexOutOfBoundsException(String.format("writerIndex(%d) + minWritableBytes(%d) exceeds maxCapacity(%d): %s", new Object[] {
/*      */               
/*  296 */               Integer.valueOf(writerIndex), Integer.valueOf(minWritableBytes), Integer.valueOf(this.maxCapacity), this
/*      */             }));
/*      */     } 
/*      */     
/*  300 */     int fastWritable = maxFastWritableBytes();
/*      */     
/*  302 */     int newCapacity = (fastWritable >= minWritableBytes) ? (writerIndex + fastWritable) : alloc().calculateNewCapacity(targetCapacity, this.maxCapacity);
/*      */ 
/*      */     
/*  305 */     capacity(newCapacity);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int minWritableBytes, boolean force) {
/*  310 */     ensureAccessible();
/*  311 */     ObjectUtil.checkPositiveOrZero(minWritableBytes, "minWritableBytes");
/*      */     
/*  313 */     if (minWritableBytes <= writableBytes()) {
/*  314 */       return 0;
/*      */     }
/*      */     
/*  317 */     int maxCapacity = maxCapacity();
/*  318 */     int writerIndex = writerIndex();
/*  319 */     if (minWritableBytes > maxCapacity - writerIndex) {
/*  320 */       if (!force || capacity() == maxCapacity) {
/*  321 */         return 1;
/*      */       }
/*      */       
/*  324 */       capacity(maxCapacity);
/*  325 */       return 3;
/*      */     } 
/*      */     
/*  328 */     int fastWritable = maxFastWritableBytes();
/*      */     
/*  330 */     int newCapacity = (fastWritable >= minWritableBytes) ? (writerIndex + fastWritable) : alloc().calculateNewCapacity(writerIndex + minWritableBytes, maxCapacity);
/*      */ 
/*      */     
/*  333 */     capacity(newCapacity);
/*  334 */     return 2;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder endianness) {
/*  339 */     if (endianness == order()) {
/*  340 */       return this;
/*      */     }
/*  342 */     ObjectUtil.checkNotNull(endianness, "endianness");
/*  343 */     return newSwappedByteBuf();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SwappedByteBuf newSwappedByteBuf() {
/*  350 */     return new SwappedByteBuf(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  355 */     checkIndex(index);
/*  356 */     return _getByte(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int index) {
/*  363 */     return (getByte(index) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int index) {
/*  368 */     return (short)(getByte(index) & 0xFF);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int index) {
/*  373 */     checkIndex(index, 2);
/*  374 */     return _getShort(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getShortLE(int index) {
/*  381 */     checkIndex(index, 2);
/*  382 */     return _getShortLE(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int index) {
/*  389 */     return getShort(index) & 0xFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShortLE(int index) {
/*  394 */     return getShortLE(index) & 0xFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int index) {
/*  399 */     checkIndex(index, 3);
/*  400 */     return _getUnsignedMedium(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUnsignedMediumLE(int index) {
/*  407 */     checkIndex(index, 3);
/*  408 */     return _getUnsignedMediumLE(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMedium(int index) {
/*  415 */     int value = getUnsignedMedium(index);
/*  416 */     if ((value & 0x800000) != 0) {
/*  417 */       value |= 0xFF000000;
/*      */     }
/*  419 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMediumLE(int index) {
/*  424 */     int value = getUnsignedMediumLE(index);
/*  425 */     if ((value & 0x800000) != 0) {
/*  426 */       value |= 0xFF000000;
/*      */     }
/*  428 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int index) {
/*  433 */     checkIndex(index, 4);
/*  434 */     return _getInt(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIntLE(int index) {
/*  441 */     checkIndex(index, 4);
/*  442 */     return _getIntLE(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int index) {
/*  449 */     return getInt(index) & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedIntLE(int index) {
/*  454 */     return getIntLE(index) & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int index) {
/*  459 */     checkIndex(index, 8);
/*  460 */     return _getLong(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLongLE(int index) {
/*  467 */     checkIndex(index, 8);
/*  468 */     return _getLongLE(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getChar(int index) {
/*  475 */     return (char)getShort(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int index) {
/*  480 */     return Float.intBitsToFloat(getInt(index));
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int index) {
/*  485 */     return Double.longBitsToDouble(getLong(index));
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst) {
/*  490 */     getBytes(index, dst, 0, dst.length);
/*  491 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst) {
/*  496 */     getBytes(index, dst, dst.writableBytes());
/*  497 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/*  502 */     getBytes(index, dst, dst.writerIndex(), length);
/*  503 */     dst.writerIndex(dst.writerIndex() + length);
/*  504 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence getCharSequence(int index, int length, Charset charset) {
/*  509 */     if (CharsetUtil.US_ASCII.equals(charset) || CharsetUtil.ISO_8859_1.equals(charset))
/*      */     {
/*  511 */       return (CharSequence)new AsciiString(ByteBufUtil.getBytes(this, index, length, true), false);
/*      */     }
/*  513 */     return toString(index, length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence readCharSequence(int length, Charset charset) {
/*  518 */     CharSequence sequence = getCharSequence(this.readerIndex, length, charset);
/*  519 */     this.readerIndex += length;
/*  520 */     return sequence;
/*      */   }
/*      */ 
/*      */   
/*      */   public String readString(int length, Charset charset) {
/*  525 */     String string = toString(this.readerIndex, length, charset);
/*  526 */     this.readerIndex += length;
/*  527 */     return string;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setByte(int index, int value) {
/*  532 */     checkIndex(index);
/*  533 */     _setByte(index, value);
/*  534 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setBoolean(int index, boolean value) {
/*  541 */     setByte(index, value ? 1 : 0);
/*  542 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShort(int index, int value) {
/*  547 */     checkIndex(index, 2);
/*  548 */     _setShort(index, value);
/*  549 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setShortLE(int index, int value) {
/*  556 */     checkIndex(index, 2);
/*  557 */     _setShortLE(index, value);
/*  558 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setChar(int index, int value) {
/*  565 */     setShort(index, value);
/*  566 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMedium(int index, int value) {
/*  571 */     checkIndex(index, 3);
/*  572 */     _setMedium(index, value);
/*  573 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setMediumLE(int index, int value) {
/*  580 */     checkIndex(index, 3);
/*  581 */     _setMediumLE(index, value);
/*  582 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setInt(int index, int value) {
/*  589 */     checkIndex(index, 4);
/*  590 */     _setInt(index, value);
/*  591 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setIntLE(int index, int value) {
/*  598 */     checkIndex(index, 4);
/*  599 */     _setIntLE(index, value);
/*  600 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setFloat(int index, float value) {
/*  607 */     setInt(index, Float.floatToRawIntBits(value));
/*  608 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLong(int index, long value) {
/*  613 */     checkIndex(index, 8);
/*  614 */     _setLong(index, value);
/*  615 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setLongLE(int index, long value) {
/*  622 */     checkIndex(index, 8);
/*  623 */     _setLongLE(index, value);
/*  624 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setDouble(int index, double value) {
/*  631 */     setLong(index, Double.doubleToRawLongBits(value));
/*  632 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src) {
/*  637 */     setBytes(index, src, 0, src.length);
/*  638 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src) {
/*  643 */     setBytes(index, src, src.readableBytes());
/*  644 */     return this;
/*      */   }
/*      */   
/*      */   private static void checkReadableBounds(ByteBuf src, int length) {
/*  648 */     if (length > src.readableBytes()) {
/*  649 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", new Object[] {
/*  650 */               Integer.valueOf(length), Integer.valueOf(src.readableBytes()), src
/*      */             }));
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/*  656 */     checkIndex(index, length);
/*  657 */     ObjectUtil.checkNotNull(src, "src");
/*  658 */     if (checkBounds) {
/*  659 */       checkReadableBounds(src, length);
/*      */     }
/*      */     
/*  662 */     setBytes(index, src, src.readerIndex(), length);
/*  663 */     src.readerIndex(src.readerIndex() + length);
/*  664 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setZero(int index, int length) {
/*  669 */     if (length == 0) {
/*  670 */       return this;
/*      */     }
/*      */     
/*  673 */     checkIndex(index, length);
/*      */     
/*  675 */     int nLong = length >>> 3;
/*  676 */     int nBytes = length & 0x7; int i;
/*  677 */     for (i = nLong; i > 0; i--) {
/*  678 */       _setLong(index, 0L);
/*  679 */       index += 8;
/*      */     } 
/*  681 */     if (nBytes == 4) {
/*  682 */       _setInt(index, 0);
/*      */     }
/*  684 */     else if (nBytes < 4) {
/*  685 */       for (i = nBytes; i > 0; i--) {
/*  686 */         _setByte(index, 0);
/*  687 */         index++;
/*      */       } 
/*      */     } else {
/*  690 */       _setInt(index, 0);
/*  691 */       index += 4;
/*  692 */       for (i = nBytes - 4; i > 0; i--) {
/*  693 */         _setByte(index, 0);
/*  694 */         index++;
/*      */       } 
/*      */     } 
/*  697 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setCharSequence(int index, CharSequence sequence, Charset charset) {
/*  702 */     return setCharSequence0(index, sequence, charset, false);
/*      */   }
/*      */   
/*      */   private int setCharSequence0(int index, CharSequence sequence, Charset charset, boolean expand) {
/*  706 */     if (charset.equals(CharsetUtil.UTF_8)) {
/*  707 */       int length = ByteBufUtil.utf8MaxBytes(sequence);
/*  708 */       if (expand) {
/*  709 */         ensureWritable0(length);
/*  710 */         checkIndex0(index, length);
/*      */       } else {
/*  712 */         checkIndex(index, length);
/*      */       } 
/*  714 */       return ByteBufUtil.writeUtf8(this, index, length, sequence, sequence.length());
/*      */     } 
/*  716 */     if (charset.equals(CharsetUtil.US_ASCII) || charset.equals(CharsetUtil.ISO_8859_1)) {
/*  717 */       int length = sequence.length();
/*  718 */       if (expand) {
/*  719 */         ensureWritable0(length);
/*  720 */         checkIndex0(index, length);
/*      */       } else {
/*  722 */         checkIndex(index, length);
/*      */       } 
/*  724 */       return ByteBufUtil.writeAscii(this, index, sequence, length);
/*      */     } 
/*  726 */     byte[] bytes = sequence.toString().getBytes(charset);
/*  727 */     if (expand) {
/*  728 */       ensureWritable0(bytes.length);
/*      */     }
/*      */     
/*  731 */     setBytes(index, bytes);
/*  732 */     return bytes.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/*  737 */     checkReadableBytes0(1);
/*  738 */     int i = this.readerIndex;
/*  739 */     byte b = _getByte(i);
/*  740 */     this.readerIndex = i + 1;
/*  741 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/*  746 */     return (readByte() != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/*  751 */     return (short)(readByte() & 0xFF);
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/*  756 */     checkReadableBytes0(2);
/*  757 */     short v = _getShort(this.readerIndex);
/*  758 */     this.readerIndex += 2;
/*  759 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShortLE() {
/*  764 */     checkReadableBytes0(2);
/*  765 */     short v = _getShortLE(this.readerIndex);
/*  766 */     this.readerIndex += 2;
/*  767 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/*  772 */     return readShort() & 0xFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShortLE() {
/*  777 */     return readShortLE() & 0xFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/*  782 */     int value = readUnsignedMedium();
/*  783 */     if ((value & 0x800000) != 0) {
/*  784 */       value |= 0xFF000000;
/*      */     }
/*  786 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMediumLE() {
/*  791 */     int value = readUnsignedMediumLE();
/*  792 */     if ((value & 0x800000) != 0) {
/*  793 */       value |= 0xFF000000;
/*      */     }
/*  795 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/*  800 */     checkReadableBytes0(3);
/*  801 */     int v = _getUnsignedMedium(this.readerIndex);
/*  802 */     this.readerIndex += 3;
/*  803 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMediumLE() {
/*  808 */     checkReadableBytes0(3);
/*  809 */     int v = _getUnsignedMediumLE(this.readerIndex);
/*  810 */     this.readerIndex += 3;
/*  811 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/*  816 */     checkReadableBytes0(4);
/*  817 */     int v = _getInt(this.readerIndex);
/*  818 */     this.readerIndex += 4;
/*  819 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readIntLE() {
/*  824 */     checkReadableBytes0(4);
/*  825 */     int v = _getIntLE(this.readerIndex);
/*  826 */     this.readerIndex += 4;
/*  827 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/*  832 */     return readInt() & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedIntLE() {
/*  837 */     return readIntLE() & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/*  842 */     checkReadableBytes0(8);
/*  843 */     long v = _getLong(this.readerIndex);
/*  844 */     this.readerIndex += 8;
/*  845 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLongLE() {
/*  850 */     checkReadableBytes0(8);
/*  851 */     long v = _getLongLE(this.readerIndex);
/*  852 */     this.readerIndex += 8;
/*  853 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/*  858 */     return (char)readShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/*  863 */     return Float.intBitsToFloat(readInt());
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/*  868 */     return Double.longBitsToDouble(readLong());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int length) {
/*  873 */     checkReadableBytes(length);
/*  874 */     if (length == 0) {
/*  875 */       return Unpooled.EMPTY_BUFFER;
/*      */     }
/*      */     
/*  878 */     ByteBuf buf = alloc().buffer(length, this.maxCapacity);
/*  879 */     buf.writeBytes(this, this.readerIndex, length);
/*  880 */     this.readerIndex += length;
/*  881 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int length) {
/*  886 */     checkReadableBytes(length);
/*  887 */     ByteBuf slice = slice(this.readerIndex, length);
/*  888 */     this.readerIndex += length;
/*  889 */     return slice;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readRetainedSlice(int length) {
/*  894 */     checkReadableBytes(length);
/*  895 */     ByteBuf slice = retainedSlice(this.readerIndex, length);
/*  896 */     this.readerIndex += length;
/*  897 */     return slice;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/*  902 */     checkReadableBytes(length);
/*  903 */     getBytes(this.readerIndex, dst, dstIndex, length);
/*  904 */     this.readerIndex += length;
/*  905 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst) {
/*  910 */     readBytes(dst, 0, dst.length);
/*  911 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst) {
/*  916 */     readBytes(dst, dst.writableBytes());
/*  917 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int length) {
/*  922 */     if (checkBounds && 
/*  923 */       length > dst.writableBytes()) {
/*  924 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds dst.writableBytes(%d) where dst is: %s", new Object[] {
/*  925 */               Integer.valueOf(length), Integer.valueOf(dst.writableBytes()), dst
/*      */             }));
/*      */     }
/*  928 */     readBytes(dst, dst.writerIndex(), length);
/*  929 */     dst.writerIndex(dst.writerIndex() + length);
/*  930 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/*  935 */     checkReadableBytes(length);
/*  936 */     getBytes(this.readerIndex, dst, dstIndex, length);
/*  937 */     this.readerIndex += length;
/*  938 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer dst) {
/*  943 */     int length = dst.remaining();
/*  944 */     checkReadableBytes(length);
/*  945 */     getBytes(this.readerIndex, dst);
/*  946 */     this.readerIndex += length;
/*  947 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/*  953 */     checkReadableBytes(length);
/*  954 */     int readBytes = getBytes(this.readerIndex, out, length);
/*  955 */     this.readerIndex += readBytes;
/*  956 */     return readBytes;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int readBytes(FileChannel out, long position, int length) throws IOException {
/*  962 */     checkReadableBytes(length);
/*  963 */     int readBytes = getBytes(this.readerIndex, out, position, length);
/*  964 */     this.readerIndex += readBytes;
/*  965 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/*  970 */     checkReadableBytes(length);
/*  971 */     getBytes(this.readerIndex, out, length);
/*  972 */     this.readerIndex += length;
/*  973 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf skipBytes(int length) {
/*  978 */     checkReadableBytes(length);
/*  979 */     this.readerIndex += length;
/*  980 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBoolean(boolean value) {
/*  985 */     writeByte(value ? 1 : 0);
/*  986 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeByte(int value) {
/*  991 */     ensureWritable0(1);
/*  992 */     _setByte(this.writerIndex++, value);
/*  993 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShort(int value) {
/*  998 */     ensureWritable0(2);
/*  999 */     _setShort(this.writerIndex, value);
/* 1000 */     this.writerIndex += 2;
/* 1001 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShortLE(int value) {
/* 1006 */     ensureWritable0(2);
/* 1007 */     _setShortLE(this.writerIndex, value);
/* 1008 */     this.writerIndex += 2;
/* 1009 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMedium(int value) {
/* 1014 */     ensureWritable0(3);
/* 1015 */     _setMedium(this.writerIndex, value);
/* 1016 */     this.writerIndex += 3;
/* 1017 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMediumLE(int value) {
/* 1022 */     ensureWritable0(3);
/* 1023 */     _setMediumLE(this.writerIndex, value);
/* 1024 */     this.writerIndex += 3;
/* 1025 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeInt(int value) {
/* 1030 */     ensureWritable0(4);
/* 1031 */     _setInt(this.writerIndex, value);
/* 1032 */     this.writerIndex += 4;
/* 1033 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeIntLE(int value) {
/* 1038 */     ensureWritable0(4);
/* 1039 */     _setIntLE(this.writerIndex, value);
/* 1040 */     this.writerIndex += 4;
/* 1041 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLong(long value) {
/* 1046 */     ensureWritable0(8);
/* 1047 */     _setLong(this.writerIndex, value);
/* 1048 */     this.writerIndex += 8;
/* 1049 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLongLE(long value) {
/* 1054 */     ensureWritable0(8);
/* 1055 */     _setLongLE(this.writerIndex, value);
/* 1056 */     this.writerIndex += 8;
/* 1057 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeChar(int value) {
/* 1062 */     writeShort(value);
/* 1063 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeFloat(float value) {
/* 1068 */     writeInt(Float.floatToRawIntBits(value));
/* 1069 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeDouble(double value) {
/* 1074 */     writeLong(Double.doubleToRawLongBits(value));
/* 1075 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/* 1080 */     ensureWritable(length);
/* 1081 */     setBytes(this.writerIndex, src, srcIndex, length);
/* 1082 */     this.writerIndex += length;
/* 1083 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src) {
/* 1088 */     writeBytes(src, 0, src.length);
/* 1089 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src) {
/* 1094 */     writeBytes(src, src.readableBytes());
/* 1095 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int length) {
/* 1100 */     if (checkBounds) {
/* 1101 */       checkReadableBounds(src, length);
/*      */     }
/* 1103 */     writeBytes(src, src.readerIndex(), length);
/* 1104 */     src.readerIndex(src.readerIndex() + length);
/* 1105 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/* 1110 */     ensureWritable(length);
/* 1111 */     setBytes(this.writerIndex, src, srcIndex, length);
/* 1112 */     this.writerIndex += length;
/* 1113 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer src) {
/* 1118 */     int length = src.remaining();
/* 1119 */     ensureWritable0(length);
/* 1120 */     setBytes(this.writerIndex, src);
/* 1121 */     this.writerIndex += length;
/* 1122 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream in, int length) throws IOException {
/* 1128 */     ensureWritable(length);
/* 1129 */     int writtenBytes = setBytes(this.writerIndex, in, length);
/* 1130 */     if (writtenBytes > 0) {
/* 1131 */       this.writerIndex += writtenBytes;
/*      */     }
/* 1133 */     return writtenBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
/* 1138 */     ensureWritable(length);
/* 1139 */     int writtenBytes = setBytes(this.writerIndex, in, length);
/* 1140 */     if (writtenBytes > 0) {
/* 1141 */       this.writerIndex += writtenBytes;
/*      */     }
/* 1143 */     return writtenBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(FileChannel in, long position, int length) throws IOException {
/* 1148 */     ensureWritable(length);
/* 1149 */     int writtenBytes = setBytes(this.writerIndex, in, position, length);
/* 1150 */     if (writtenBytes > 0) {
/* 1151 */       this.writerIndex += writtenBytes;
/*      */     }
/* 1153 */     return writtenBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeZero(int length) {
/* 1158 */     if (length == 0) {
/* 1159 */       return this;
/*      */     }
/*      */     
/* 1162 */     ensureWritable(length);
/* 1163 */     int wIndex = this.writerIndex;
/* 1164 */     checkIndex0(wIndex, length);
/*      */     
/* 1166 */     int nLong = length >>> 3;
/* 1167 */     int nBytes = length & 0x7; int i;
/* 1168 */     for (i = nLong; i > 0; i--) {
/* 1169 */       _setLong(wIndex, 0L);
/* 1170 */       wIndex += 8;
/*      */     } 
/* 1172 */     if (nBytes == 4) {
/* 1173 */       _setInt(wIndex, 0);
/* 1174 */       wIndex += 4;
/* 1175 */     } else if (nBytes < 4) {
/* 1176 */       for (i = nBytes; i > 0; i--) {
/* 1177 */         _setByte(wIndex, 0);
/* 1178 */         wIndex++;
/*      */       } 
/*      */     } else {
/* 1181 */       _setInt(wIndex, 0);
/* 1182 */       wIndex += 4;
/* 1183 */       for (i = nBytes - 4; i > 0; i--) {
/* 1184 */         _setByte(wIndex, 0);
/* 1185 */         wIndex++;
/*      */       } 
/*      */     } 
/* 1188 */     this.writerIndex = wIndex;
/* 1189 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeCharSequence(CharSequence sequence, Charset charset) {
/* 1194 */     int written = setCharSequence0(this.writerIndex, sequence, charset, true);
/* 1195 */     this.writerIndex += written;
/* 1196 */     return written;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/* 1201 */     return copy(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/* 1206 */     ensureAccessible();
/* 1207 */     return new UnpooledDuplicatedByteBuf(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedDuplicate() {
/* 1212 */     return duplicate().retain();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/* 1217 */     return slice(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice() {
/* 1222 */     return slice().retain();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int index, int length) {
/* 1227 */     ensureAccessible();
/* 1228 */     return new UnpooledSlicedByteBuf(this, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice(int index, int length) {
/* 1233 */     return slice(index, length).retain();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/* 1238 */     return nioBuffer(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/* 1243 */     return nioBuffers(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset charset) {
/* 1248 */     return toString(this.readerIndex, readableBytes(), charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(int index, int length, Charset charset) {
/* 1253 */     return ByteBufUtil.decodeString(this, index, length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int fromIndex, int toIndex, byte value) {
/* 1258 */     if (fromIndex <= toIndex) {
/* 1259 */       return ByteBufUtil.firstIndexOf(this, fromIndex, toIndex, value);
/*      */     }
/* 1261 */     return ByteBufUtil.lastIndexOf(this, fromIndex, toIndex, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte value) {
/* 1266 */     return bytesBefore(readerIndex(), readableBytes(), value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int length, byte value) {
/* 1271 */     checkReadableBytes(length);
/* 1272 */     return bytesBefore(readerIndex(), length, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int index, int length, byte value) {
/* 1277 */     int endIndex = indexOf(index, index + length, value);
/* 1278 */     if (endIndex < 0) {
/* 1279 */       return -1;
/*      */     }
/* 1281 */     return endIndex - index;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteProcessor processor) {
/* 1286 */     ensureAccessible();
/*      */     try {
/* 1288 */       return forEachByteAsc0(this.readerIndex, this.writerIndex, processor);
/* 1289 */     } catch (Exception e) {
/* 1290 */       PlatformDependent.throwException(e);
/* 1291 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(int index, int length, ByteProcessor processor) {
/* 1297 */     checkIndex(index, length);
/*      */     try {
/* 1299 */       return forEachByteAsc0(index, index + length, processor);
/* 1300 */     } catch (Exception e) {
/* 1301 */       PlatformDependent.throwException(e);
/* 1302 */       return -1;
/*      */     } 
/*      */   }
/*      */   
/*      */   int forEachByteAsc0(int start, int end, ByteProcessor processor) throws Exception {
/* 1307 */     for (; start < end; start++) {
/* 1308 */       if (!processor.process(_getByte(start))) {
/* 1309 */         return start;
/*      */       }
/*      */     } 
/*      */     
/* 1313 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteProcessor processor) {
/* 1318 */     ensureAccessible();
/*      */     try {
/* 1320 */       return forEachByteDesc0(this.writerIndex - 1, this.readerIndex, processor);
/* 1321 */     } catch (Exception e) {
/* 1322 */       PlatformDependent.throwException(e);
/* 1323 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
/* 1329 */     checkIndex(index, length);
/*      */     try {
/* 1331 */       return forEachByteDesc0(index + length - 1, index, processor);
/* 1332 */     } catch (Exception e) {
/* 1333 */       PlatformDependent.throwException(e);
/* 1334 */       return -1;
/*      */     } 
/*      */   }
/*      */   
/*      */   int forEachByteDesc0(int rStart, int rEnd, ByteProcessor processor) throws Exception {
/* 1339 */     for (; rStart >= rEnd; rStart--) {
/* 1340 */       if (!processor.process(_getByte(rStart))) {
/* 1341 */         return rStart;
/*      */       }
/*      */     } 
/* 1344 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1349 */     return ByteBufUtil.hashCode(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1354 */     return (o instanceof ByteBuf && ByteBufUtil.equals(this, (ByteBuf)o));
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf that) {
/* 1359 */     return ByteBufUtil.compare(this, that);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1364 */     if (refCnt() == 0) {
/* 1365 */       return StringUtil.simpleClassName(this) + "(freed)";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1372 */     StringBuilder buf = (new StringBuilder()).append(StringUtil.simpleClassName(this)).append("(ridx: ").append(this.readerIndex).append(", widx: ").append(this.writerIndex).append(", cap: ").append(capacity());
/* 1373 */     if (this.maxCapacity != Integer.MAX_VALUE) {
/* 1374 */       buf.append('/').append(this.maxCapacity);
/*      */     }
/*      */     
/* 1377 */     ByteBuf unwrapped = unwrap();
/* 1378 */     if (unwrapped != null) {
/* 1379 */       buf.append(", unwrapped: ").append(unwrapped);
/*      */     }
/* 1381 */     buf.append(')');
/* 1382 */     return buf.toString();
/*      */   }
/*      */   
/*      */   protected final void checkIndex(int index) {
/* 1386 */     checkIndex(index, 1);
/*      */   }
/*      */   
/*      */   protected final void checkIndex(int index, int fieldLength) {
/* 1390 */     ensureAccessible();
/* 1391 */     checkIndex0(index, fieldLength);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkRangeBoundsTrustedCapacity(String indexName, int index, int fieldLength, int capacity) {
/* 1396 */     if (isOutOfBoundsTrustedCapacity(index, fieldLength, capacity)) {
/* 1397 */       rangeBoundsCheckFailed(indexName, index, fieldLength, capacity);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isOutOfBoundsTrustedCapacity(int index, int fieldLength, int capacity) {
/* 1406 */     return (index < 0 || fieldLength < 0 || index + fieldLength < 0 || index + fieldLength > capacity);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkRangeBounds(String indexName, int index, int fieldLength, int capacity) {
/* 1411 */     if (MathUtil.isOutOfBounds(index, fieldLength, capacity)) {
/* 1412 */       rangeBoundsCheckFailed(indexName, index, fieldLength, capacity);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void rangeBoundsCheckFailed(String indexName, int index, int fieldLength, int capacity) {
/* 1417 */     throw new IndexOutOfBoundsException(String.format("%s: %d, length: %d (expected: range(0, %d))", new Object[] { indexName, 
/* 1418 */             Integer.valueOf(index), Integer.valueOf(fieldLength), Integer.valueOf(capacity) }));
/*      */   }
/*      */   
/*      */   final void checkIndex0(int index, int fieldLength) {
/* 1422 */     if (checkBounds) {
/* 1423 */       checkRangeBoundsTrustedCapacity("index", index, fieldLength, capacity());
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void checkSrcIndex(int index, int length, int srcIndex, int srcCapacity) {
/* 1428 */     checkIndex(index, length);
/* 1429 */     if (checkBounds) {
/* 1430 */       checkRangeBounds("srcIndex", srcIndex, length, srcCapacity);
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void checkDstIndex(int index, int length, int dstIndex, int dstCapacity) {
/* 1435 */     checkIndex(index, length);
/* 1436 */     if (checkBounds) {
/* 1437 */       checkRangeBounds("dstIndex", dstIndex, length, dstCapacity);
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void checkDstIndex(int length, int dstIndex, int dstCapacity) {
/* 1442 */     checkReadableBytes(length);
/* 1443 */     if (checkBounds) {
/* 1444 */       checkRangeBounds("dstIndex", dstIndex, length, dstCapacity);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void checkReadableBytes(int minimumReadableBytes) {
/* 1454 */     checkReadableBytes0(ObjectUtil.checkPositiveOrZero(minimumReadableBytes, "minimumReadableBytes"));
/*      */   }
/*      */   
/*      */   protected final void checkNewCapacity(int newCapacity) {
/* 1458 */     ensureAccessible();
/* 1459 */     if (checkBounds && (newCapacity < 0 || newCapacity > maxCapacity())) {
/* 1460 */       throw new IllegalArgumentException("newCapacity: " + newCapacity + " (expected: 0-" + 
/* 1461 */           maxCapacity() + ')');
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkReadableBytes0(int minimumReadableBytes) {
/* 1466 */     ensureAccessible();
/* 1467 */     if (checkBounds && this.readerIndex > this.writerIndex - minimumReadableBytes) {
/* 1468 */       throw new IndexOutOfBoundsException(String.format("readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s", new Object[] {
/*      */               
/* 1470 */               Integer.valueOf(this.readerIndex), Integer.valueOf(minimumReadableBytes), Integer.valueOf(this.writerIndex), this
/*      */             }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void ensureAccessible() {
/* 1479 */     if (checkAccessible && !isAccessible()) {
/* 1480 */       throw new IllegalReferenceCountException(0);
/*      */     }
/*      */   }
/*      */   
/*      */   final void setIndex0(int readerIndex, int writerIndex) {
/* 1485 */     this.readerIndex = readerIndex;
/* 1486 */     this.writerIndex = writerIndex;
/*      */   }
/*      */   
/*      */   final void discardMarks() {
/* 1490 */     this.markedReaderIndex = this.markedWriterIndex = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   long _memoryAddress() {
/* 1497 */     return (isAccessible() && hasMemoryAddress()) ? memoryAddress() : 0L;
/*      */   }
/*      */   
/*      */   protected abstract byte _getByte(int paramInt);
/*      */   
/*      */   protected abstract short _getShort(int paramInt);
/*      */   
/*      */   protected abstract short _getShortLE(int paramInt);
/*      */   
/*      */   protected abstract int _getUnsignedMedium(int paramInt);
/*      */   
/*      */   protected abstract int _getUnsignedMediumLE(int paramInt);
/*      */   
/*      */   protected abstract int _getInt(int paramInt);
/*      */   
/*      */   protected abstract int _getIntLE(int paramInt);
/*      */   
/*      */   protected abstract long _getLong(int paramInt);
/*      */   
/*      */   protected abstract long _getLongLE(int paramInt);
/*      */   
/*      */   protected abstract void _setByte(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setShort(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setShortLE(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setMedium(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setMediumLE(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setInt(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setIntLE(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setLong(int paramInt, long paramLong);
/*      */   
/*      */   protected abstract void _setLongLE(int paramInt, long paramLong);
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AbstractByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */