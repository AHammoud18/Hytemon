/*      */ package io.netty.buffer;
/*      */ 
/*      */ import io.netty.util.ByteProcessor;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.ReadOnlyBufferException;
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
/*      */ 
/*      */ public final class EmptyByteBuf
/*      */   extends ByteBuf
/*      */ {
/*      */   static final int EMPTY_BYTE_BUF_HASH_CODE = 1;
/*   43 */   private static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.allocateDirect(0);
/*      */   private static final long EMPTY_BYTE_BUFFER_ADDRESS;
/*      */   
/*      */   static {
/*   47 */     long emptyByteBufferAddress = 0L;
/*      */     try {
/*   49 */       if (PlatformDependent.hasUnsafe()) {
/*   50 */         emptyByteBufferAddress = PlatformDependent.directBufferAddress(EMPTY_BYTE_BUFFER);
/*      */       }
/*   52 */     } catch (Throwable throwable) {}
/*      */ 
/*      */     
/*   55 */     EMPTY_BYTE_BUFFER_ADDRESS = emptyByteBufferAddress;
/*      */   }
/*      */   
/*      */   private final ByteBufAllocator alloc;
/*      */   private final ByteOrder order;
/*      */   private final String str;
/*      */   private EmptyByteBuf swapped;
/*      */   
/*      */   public EmptyByteBuf(ByteBufAllocator alloc) {
/*   64 */     this(alloc, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */   
/*      */   private EmptyByteBuf(ByteBufAllocator alloc, ByteOrder order) {
/*   68 */     this.alloc = (ByteBufAllocator)ObjectUtil.checkNotNull(alloc, "alloc");
/*   69 */     this.order = order;
/*   70 */     this.str = StringUtil.simpleClassName(this) + ((order == ByteOrder.BIG_ENDIAN) ? "BE" : "LE");
/*      */   }
/*      */ 
/*      */   
/*      */   public int capacity() {
/*   75 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf capacity(int newCapacity) {
/*   80 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*   85 */     return this.alloc;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteOrder order() {
/*   90 */     return this.order;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf unwrap() {
/*   95 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf asReadOnly() {
/*  100 */     return Unpooled.unmodifiableBuffer(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/*  105 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  110 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxCapacity() {
/*  115 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder endianness) {
/*  120 */     if (ObjectUtil.checkNotNull(endianness, "endianness") == order()) {
/*  121 */       return this;
/*      */     }
/*      */     
/*  124 */     EmptyByteBuf swapped = this.swapped;
/*  125 */     if (swapped != null) {
/*  126 */       return swapped;
/*      */     }
/*      */     
/*  129 */     this.swapped = swapped = new EmptyByteBuf(alloc(), endianness);
/*  130 */     return swapped;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readerIndex() {
/*  135 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readerIndex(int readerIndex) {
/*  140 */     return checkIndex(readerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writerIndex() {
/*  145 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writerIndex(int writerIndex) {
/*  150 */     return checkIndex(writerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/*  155 */     checkIndex(readerIndex);
/*  156 */     checkIndex(writerIndex);
/*  157 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readableBytes() {
/*  162 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writableBytes() {
/*  167 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxWritableBytes() {
/*  172 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable() {
/*  177 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  182 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf clear() {
/*  187 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markReaderIndex() {
/*  192 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetReaderIndex() {
/*  197 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markWriterIndex() {
/*  202 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetWriterIndex() {
/*  207 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardReadBytes() {
/*  212 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardSomeReadBytes() {
/*  217 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf ensureWritable(int minWritableBytes) {
/*  222 */     ObjectUtil.checkPositiveOrZero(minWritableBytes, "minWritableBytes");
/*  223 */     if (minWritableBytes != 0) {
/*  224 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  226 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int minWritableBytes, boolean force) {
/*  231 */     ObjectUtil.checkPositiveOrZero(minWritableBytes, "minWritableBytes");
/*      */     
/*  233 */     if (minWritableBytes == 0) {
/*  234 */       return 0;
/*      */     }
/*      */     
/*  237 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int index) {
/*  242 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  247 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int index) {
/*  252 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int index) {
/*  257 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShortLE(int index) {
/*  262 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int index) {
/*  267 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShortLE(int index) {
/*  272 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMedium(int index) {
/*  277 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMediumLE(int index) {
/*  282 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int index) {
/*  287 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMediumLE(int index) {
/*  292 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int index) {
/*  297 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getIntLE(int index) {
/*  302 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int index) {
/*  307 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedIntLE(int index) {
/*  312 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int index) {
/*  317 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLongLE(int index) {
/*  322 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(int index) {
/*  327 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int index) {
/*  332 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int index) {
/*  337 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst) {
/*  342 */     return checkIndex(index, dst.writableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/*  347 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/*  352 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst) {
/*  357 */     return checkIndex(index, dst.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/*  362 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/*  367 */     return checkIndex(index, dst.remaining());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, OutputStream out, int length) {
/*  372 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int index, GatheringByteChannel out, int length) {
/*  377 */     checkIndex(index, length);
/*  378 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int index, FileChannel out, long position, int length) {
/*  383 */     checkIndex(index, length);
/*  384 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence getCharSequence(int index, int length, Charset charset) {
/*  389 */     checkIndex(index, length);
/*  390 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBoolean(int index, boolean value) {
/*  395 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setByte(int index, int value) {
/*  400 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShort(int index, int value) {
/*  405 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShortLE(int index, int value) {
/*  410 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMedium(int index, int value) {
/*  415 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMediumLE(int index, int value) {
/*  420 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setInt(int index, int value) {
/*  425 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIntLE(int index, int value) {
/*  430 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLong(int index, long value) {
/*  435 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLongLE(int index, long value) {
/*  440 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setChar(int index, int value) {
/*  445 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setFloat(int index, float value) {
/*  450 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setDouble(int index, double value) {
/*  455 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src) {
/*  460 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/*  465 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/*  470 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src) {
/*  475 */     return checkIndex(index, src.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/*  480 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuffer src) {
/*  485 */     return checkIndex(index, src.remaining());
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, InputStream in, int length) {
/*  490 */     checkIndex(index, length);
/*  491 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, ScatteringByteChannel in, int length) {
/*  496 */     checkIndex(index, length);
/*  497 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, FileChannel in, long position, int length) {
/*  502 */     checkIndex(index, length);
/*  503 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setZero(int index, int length) {
/*  508 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setCharSequence(int index, CharSequence sequence, Charset charset) {
/*  513 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/*  518 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/*  523 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/*  528 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/*  533 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShortLE() {
/*  538 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/*  543 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShortLE() {
/*  548 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/*  553 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMediumLE() {
/*  558 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/*  563 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMediumLE() {
/*  568 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/*  573 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readIntLE() {
/*  578 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/*  583 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedIntLE() {
/*  588 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/*  593 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLongLE() {
/*  598 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/*  603 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/*  608 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/*  613 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int length) {
/*  618 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int length) {
/*  623 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readRetainedSlice(int length) {
/*  628 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst) {
/*  633 */     return checkLength(dst.writableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int length) {
/*  638 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/*  643 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst) {
/*  648 */     return checkLength(dst.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/*  653 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer dst) {
/*  658 */     return checkLength(dst.remaining());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(OutputStream out, int length) {
/*  663 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel out, int length) {
/*  668 */     checkLength(length);
/*  669 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(FileChannel out, long position, int length) {
/*  674 */     checkLength(length);
/*  675 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence readCharSequence(int length, Charset charset) {
/*  680 */     checkLength(length);
/*  681 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   public String readString(int length, Charset charset) {
/*  686 */     checkLength(length);
/*  687 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf skipBytes(int length) {
/*  692 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBoolean(boolean value) {
/*  697 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeByte(int value) {
/*  702 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShort(int value) {
/*  707 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShortLE(int value) {
/*  712 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMedium(int value) {
/*  717 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMediumLE(int value) {
/*  722 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeInt(int value) {
/*  727 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeIntLE(int value) {
/*  732 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLong(long value) {
/*  737 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLongLE(long value) {
/*  742 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeChar(int value) {
/*  747 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeFloat(float value) {
/*  752 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeDouble(double value) {
/*  757 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src) {
/*  762 */     return checkLength(src.readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int length) {
/*  767 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/*  772 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src) {
/*  777 */     return checkLength(src.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/*  782 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer src) {
/*  787 */     return checkLength(src.remaining());
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream in, int length) {
/*  792 */     checkLength(length);
/*  793 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel in, int length) {
/*  798 */     checkLength(length);
/*  799 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(FileChannel in, long position, int length) {
/*  804 */     checkLength(length);
/*  805 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeZero(int length) {
/*  810 */     return checkLength(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeCharSequence(CharSequence sequence, Charset charset) {
/*  815 */     throw new IndexOutOfBoundsException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int fromIndex, int toIndex, byte value) {
/*  820 */     checkIndex(fromIndex);
/*  821 */     checkIndex(toIndex);
/*  822 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte value) {
/*  827 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int length, byte value) {
/*  832 */     checkLength(length);
/*  833 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int index, int length, byte value) {
/*  838 */     checkIndex(index, length);
/*  839 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteProcessor processor) {
/*  844 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(int index, int length, ByteProcessor processor) {
/*  849 */     checkIndex(index, length);
/*  850 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteProcessor processor) {
/*  855 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
/*  860 */     checkIndex(index, length);
/*  861 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/*  866 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int index, int length) {
/*  871 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/*  876 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice() {
/*  881 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int index, int length) {
/*  886 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice(int index, int length) {
/*  891 */     return checkIndex(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/*  896 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedDuplicate() {
/*  901 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/*  906 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/*  911 */     return EMPTY_BYTE_BUFFER;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer(int index, int length) {
/*  916 */     checkIndex(index, length);
/*  917 */     return nioBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/*  922 */     return new ByteBuffer[] { EMPTY_BYTE_BUFFER };
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int index, int length) {
/*  927 */     checkIndex(index, length);
/*  928 */     return nioBuffers();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int index, int length) {
/*  933 */     return EMPTY_BYTE_BUFFER;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/*  938 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] array() {
/*  943 */     return EmptyArrays.EMPTY_BYTES;
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/*  948 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasMemoryAddress() {
/*  953 */     return (EMPTY_BYTE_BUFFER_ADDRESS != 0L);
/*      */   }
/*      */ 
/*      */   
/*      */   public long memoryAddress() {
/*  958 */     if (hasMemoryAddress()) {
/*  959 */       return EMPTY_BYTE_BUFFER_ADDRESS;
/*      */     }
/*  961 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isContiguous() {
/*  967 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset charset) {
/*  972 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(int index, int length, Charset charset) {
/*  977 */     checkIndex(index, length);
/*  978 */     return toString(charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  983 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  988 */     return (obj instanceof ByteBuf && !((ByteBuf)obj).isReadable());
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf buffer) {
/*  993 */     return buffer.isReadable() ? -1 : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  998 */     return this.str;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable(int size) {
/* 1003 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable(int size) {
/* 1008 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int refCnt() {
/* 1013 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain() {
/* 1018 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain(int increment) {
/* 1023 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf touch() {
/* 1028 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf touch(Object hint) {
/* 1033 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release() {
/* 1038 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release(int decrement) {
/* 1043 */     return false;
/*      */   }
/*      */   
/*      */   private ByteBuf checkIndex(int index) {
/* 1047 */     if (index != 0) {
/* 1048 */       throw new IndexOutOfBoundsException();
/*      */     }
/* 1050 */     return this;
/*      */   }
/*      */   
/*      */   private ByteBuf checkIndex(int index, int length) {
/* 1054 */     ObjectUtil.checkPositiveOrZero(length, "length");
/* 1055 */     if (index != 0 || length != 0) {
/* 1056 */       throw new IndexOutOfBoundsException();
/*      */     }
/* 1058 */     return this;
/*      */   }
/*      */   
/*      */   private ByteBuf checkLength(int length) {
/* 1062 */     ObjectUtil.checkPositiveOrZero(length, "length");
/* 1063 */     if (length != 0) {
/* 1064 */       throw new IndexOutOfBoundsException();
/*      */     }
/* 1066 */     return this;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\EmptyByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */