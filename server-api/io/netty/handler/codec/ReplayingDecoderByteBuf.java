/*      */ package io.netty.handler.codec;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.SwappedByteBuf;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.util.ByteProcessor;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.Signal;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.StringUtil;
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
/*      */ final class ReplayingDecoderByteBuf
/*      */   extends ByteBuf
/*      */ {
/*   41 */   private static final Signal REPLAY = ReplayingDecoder.REPLAY;
/*      */   
/*      */   private ByteBuf buffer;
/*      */   
/*      */   private boolean terminated;
/*      */   
/*      */   private SwappedByteBuf swapped;
/*   48 */   static final ReplayingDecoderByteBuf EMPTY_BUFFER = new ReplayingDecoderByteBuf(Unpooled.EMPTY_BUFFER);
/*      */   
/*      */   static {
/*   51 */     EMPTY_BUFFER.terminate();
/*      */   }
/*      */   
/*      */   ReplayingDecoderByteBuf() {}
/*      */   
/*      */   ReplayingDecoderByteBuf(ByteBuf buffer) {
/*   57 */     setCumulation(buffer);
/*      */   }
/*      */   
/*      */   void setCumulation(ByteBuf buffer) {
/*   61 */     this.buffer = buffer;
/*      */   }
/*      */   
/*      */   void terminate() {
/*   65 */     this.terminated = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int capacity() {
/*   70 */     if (this.terminated) {
/*   71 */       return this.buffer.capacity();
/*      */     }
/*   73 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf capacity(int newCapacity) {
/*   79 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxCapacity() {
/*   84 */     return capacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*   89 */     return this.buffer.alloc();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/*   94 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf asReadOnly() {
/*  100 */     return Unpooled.unmodifiableBuffer(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  105 */     return this.buffer.isDirect();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/*  110 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] array() {
/*  115 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/*  120 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasMemoryAddress() {
/*  125 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public long memoryAddress() {
/*  130 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf clear() {
/*  135 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  140 */     return (this == obj);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf buffer) {
/*  145 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/*  150 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int index, int length) {
/*  155 */     checkIndex(index, length);
/*  156 */     return this.buffer.copy(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardReadBytes() {
/*  161 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf ensureWritable(int writableBytes) {
/*  166 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int minWritableBytes, boolean force) {
/*  171 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/*  176 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedDuplicate() {
/*  181 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int index) {
/*  186 */     checkIndex(index, 1);
/*  187 */     return this.buffer.getBoolean(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  192 */     checkIndex(index, 1);
/*  193 */     return this.buffer.getByte(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int index) {
/*  198 */     checkIndex(index, 1);
/*  199 */     return this.buffer.getUnsignedByte(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/*  204 */     checkIndex(index, length);
/*  205 */     this.buffer.getBytes(index, dst, dstIndex, length);
/*  206 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst) {
/*  211 */     checkIndex(index, dst.length);
/*  212 */     this.buffer.getBytes(index, dst);
/*  213 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/*  218 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/*  223 */     checkIndex(index, length);
/*  224 */     this.buffer.getBytes(index, dst, dstIndex, length);
/*  225 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/*  230 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst) {
/*  235 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int index, GatheringByteChannel out, int length) {
/*  240 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int index, FileChannel out, long position, int length) {
/*  245 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, OutputStream out, int length) {
/*  250 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int index) {
/*  255 */     checkIndex(index, 4);
/*  256 */     return this.buffer.getInt(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getIntLE(int index) {
/*  261 */     checkIndex(index, 4);
/*  262 */     return this.buffer.getIntLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int index) {
/*  267 */     checkIndex(index, 4);
/*  268 */     return this.buffer.getUnsignedInt(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedIntLE(int index) {
/*  273 */     checkIndex(index, 4);
/*  274 */     return this.buffer.getUnsignedIntLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int index) {
/*  279 */     checkIndex(index, 8);
/*  280 */     return this.buffer.getLong(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLongLE(int index) {
/*  285 */     checkIndex(index, 8);
/*  286 */     return this.buffer.getLongLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMedium(int index) {
/*  291 */     checkIndex(index, 3);
/*  292 */     return this.buffer.getMedium(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMediumLE(int index) {
/*  297 */     checkIndex(index, 3);
/*  298 */     return this.buffer.getMediumLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int index) {
/*  303 */     checkIndex(index, 3);
/*  304 */     return this.buffer.getUnsignedMedium(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMediumLE(int index) {
/*  309 */     checkIndex(index, 3);
/*  310 */     return this.buffer.getUnsignedMediumLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int index) {
/*  315 */     checkIndex(index, 2);
/*  316 */     return this.buffer.getShort(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShortLE(int index) {
/*  321 */     checkIndex(index, 2);
/*  322 */     return this.buffer.getShortLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int index) {
/*  327 */     checkIndex(index, 2);
/*  328 */     return this.buffer.getUnsignedShort(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShortLE(int index) {
/*  333 */     checkIndex(index, 2);
/*  334 */     return this.buffer.getUnsignedShortLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(int index) {
/*  339 */     checkIndex(index, 2);
/*  340 */     return this.buffer.getChar(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int index) {
/*  345 */     checkIndex(index, 4);
/*  346 */     return this.buffer.getFloat(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int index) {
/*  351 */     checkIndex(index, 8);
/*  352 */     return this.buffer.getDouble(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence getCharSequence(int index, int length, Charset charset) {
/*  357 */     checkIndex(index, length);
/*  358 */     return this.buffer.getCharSequence(index, length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  363 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int fromIndex, int toIndex, byte value) {
/*  368 */     if (fromIndex == toIndex) {
/*  369 */       return -1;
/*      */     }
/*      */     
/*  372 */     if (Math.max(fromIndex, toIndex) > this.buffer.writerIndex()) {
/*  373 */       throw REPLAY;
/*      */     }
/*      */     
/*  376 */     return this.buffer.indexOf(fromIndex, toIndex, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte value) {
/*  381 */     int bytes = this.buffer.bytesBefore(value);
/*  382 */     if (bytes < 0) {
/*  383 */       throw REPLAY;
/*      */     }
/*  385 */     return bytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int length, byte value) {
/*  390 */     return bytesBefore(this.buffer.readerIndex(), length, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int index, int length, byte value) {
/*  395 */     int writerIndex = this.buffer.writerIndex();
/*  396 */     if (index >= writerIndex) {
/*  397 */       throw REPLAY;
/*      */     }
/*      */     
/*  400 */     if (index <= writerIndex - length) {
/*  401 */       return this.buffer.bytesBefore(index, length, value);
/*      */     }
/*      */     
/*  404 */     int res = this.buffer.bytesBefore(index, writerIndex - index, value);
/*  405 */     if (res < 0) {
/*  406 */       throw REPLAY;
/*      */     }
/*  408 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteProcessor processor) {
/*  414 */     int ret = this.buffer.forEachByte(processor);
/*  415 */     if (ret < 0) {
/*  416 */       throw REPLAY;
/*      */     }
/*  418 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByte(int index, int length, ByteProcessor processor) {
/*  424 */     int writerIndex = this.buffer.writerIndex();
/*  425 */     if (index >= writerIndex) {
/*  426 */       throw REPLAY;
/*      */     }
/*      */     
/*  429 */     if (index <= writerIndex - length) {
/*  430 */       return this.buffer.forEachByte(index, length, processor);
/*      */     }
/*      */     
/*  433 */     int ret = this.buffer.forEachByte(index, writerIndex - index, processor);
/*  434 */     if (ret < 0) {
/*  435 */       throw REPLAY;
/*      */     }
/*  437 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteProcessor processor) {
/*  443 */     if (this.terminated) {
/*  444 */       return this.buffer.forEachByteDesc(processor);
/*      */     }
/*  446 */     throw reject();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
/*  452 */     if (index + length > this.buffer.writerIndex()) {
/*  453 */       throw REPLAY;
/*      */     }
/*      */     
/*  456 */     return this.buffer.forEachByteDesc(index, length, processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markReaderIndex() {
/*  461 */     this.buffer.markReaderIndex();
/*  462 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markWriterIndex() {
/*  467 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteOrder order() {
/*  472 */     return this.buffer.order();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder endianness) {
/*  477 */     if (ObjectUtil.checkNotNull(endianness, "endianness") == order()) {
/*  478 */       return this;
/*      */     }
/*      */     
/*  481 */     SwappedByteBuf swapped = this.swapped;
/*  482 */     if (swapped == null) {
/*  483 */       this.swapped = swapped = new SwappedByteBuf(this);
/*      */     }
/*  485 */     return (ByteBuf)swapped;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable() {
/*  490 */     return (!this.terminated || this.buffer.isReadable());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable(int size) {
/*  495 */     return (!this.terminated || this.buffer.isReadable(size));
/*      */   }
/*      */ 
/*      */   
/*      */   public int readableBytes() {
/*  500 */     if (this.terminated) {
/*  501 */       return this.buffer.readableBytes();
/*      */     }
/*  503 */     return Integer.MAX_VALUE - this.buffer.readerIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/*  509 */     checkReadableBytes(1);
/*  510 */     return this.buffer.readBoolean();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/*  515 */     checkReadableBytes(1);
/*  516 */     return this.buffer.readByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/*  521 */     checkReadableBytes(1);
/*  522 */     return this.buffer.readUnsignedByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/*  527 */     checkReadableBytes(length);
/*  528 */     this.buffer.readBytes(dst, dstIndex, length);
/*  529 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst) {
/*  534 */     checkReadableBytes(dst.length);
/*  535 */     this.buffer.readBytes(dst);
/*  536 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer dst) {
/*  541 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/*  546 */     checkReadableBytes(length);
/*  547 */     this.buffer.readBytes(dst, dstIndex, length);
/*  548 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int length) {
/*  553 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst) {
/*  558 */     checkReadableBytes(dst.writableBytes());
/*  559 */     this.buffer.readBytes(dst);
/*  560 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel out, int length) {
/*  565 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(FileChannel out, long position, int length) {
/*  570 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int length) {
/*  575 */     checkReadableBytes(length);
/*  576 */     return this.buffer.readBytes(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int length) {
/*  581 */     checkReadableBytes(length);
/*  582 */     return this.buffer.readSlice(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readRetainedSlice(int length) {
/*  587 */     checkReadableBytes(length);
/*  588 */     return this.buffer.readRetainedSlice(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(OutputStream out, int length) {
/*  593 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readerIndex() {
/*  598 */     return this.buffer.readerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readerIndex(int readerIndex) {
/*  603 */     this.buffer.readerIndex(readerIndex);
/*  604 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/*  609 */     checkReadableBytes(4);
/*  610 */     return this.buffer.readInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readIntLE() {
/*  615 */     checkReadableBytes(4);
/*  616 */     return this.buffer.readIntLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/*  621 */     checkReadableBytes(4);
/*  622 */     return this.buffer.readUnsignedInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedIntLE() {
/*  627 */     checkReadableBytes(4);
/*  628 */     return this.buffer.readUnsignedIntLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/*  633 */     checkReadableBytes(8);
/*  634 */     return this.buffer.readLong();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLongLE() {
/*  639 */     checkReadableBytes(8);
/*  640 */     return this.buffer.readLongLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/*  645 */     checkReadableBytes(3);
/*  646 */     return this.buffer.readMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMediumLE() {
/*  651 */     checkReadableBytes(3);
/*  652 */     return this.buffer.readMediumLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/*  657 */     checkReadableBytes(3);
/*  658 */     return this.buffer.readUnsignedMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMediumLE() {
/*  663 */     checkReadableBytes(3);
/*  664 */     return this.buffer.readUnsignedMediumLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/*  669 */     checkReadableBytes(2);
/*  670 */     return this.buffer.readShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShortLE() {
/*  675 */     checkReadableBytes(2);
/*  676 */     return this.buffer.readShortLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/*  681 */     checkReadableBytes(2);
/*  682 */     return this.buffer.readUnsignedShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShortLE() {
/*  687 */     checkReadableBytes(2);
/*  688 */     return this.buffer.readUnsignedShortLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/*  693 */     checkReadableBytes(2);
/*  694 */     return this.buffer.readChar();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/*  699 */     checkReadableBytes(4);
/*  700 */     return this.buffer.readFloat();
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/*  705 */     checkReadableBytes(8);
/*  706 */     return this.buffer.readDouble();
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence readCharSequence(int length, Charset charset) {
/*  711 */     checkReadableBytes(length);
/*  712 */     return this.buffer.readCharSequence(length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public String readString(int length, Charset charset) {
/*  717 */     checkReadableBytes(length);
/*  718 */     return this.buffer.readString(length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetReaderIndex() {
/*  723 */     this.buffer.resetReaderIndex();
/*  724 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetWriterIndex() {
/*  729 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBoolean(int index, boolean value) {
/*  734 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setByte(int index, int value) {
/*  739 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/*  744 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src) {
/*  749 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuffer src) {
/*  754 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/*  759 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/*  764 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src) {
/*  769 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, InputStream in, int length) {
/*  774 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setZero(int index, int length) {
/*  779 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, ScatteringByteChannel in, int length) {
/*  784 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, FileChannel in, long position, int length) {
/*  789 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/*  794 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setInt(int index, int value) {
/*  799 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIntLE(int index, int value) {
/*  804 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLong(int index, long value) {
/*  809 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLongLE(int index, long value) {
/*  814 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMedium(int index, int value) {
/*  819 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMediumLE(int index, int value) {
/*  824 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShort(int index, int value) {
/*  829 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShortLE(int index, int value) {
/*  834 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setChar(int index, int value) {
/*  839 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setFloat(int index, float value) {
/*  844 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setDouble(int index, double value) {
/*  849 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf skipBytes(int length) {
/*  854 */     checkReadableBytes(length);
/*  855 */     this.buffer.skipBytes(length);
/*  856 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/*  861 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice() {
/*  866 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int index, int length) {
/*  871 */     checkIndex(index, length);
/*  872 */     return this.buffer.slice(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice(int index, int length) {
/*  877 */     checkIndex(index, length);
/*  878 */     return this.buffer.retainedSlice(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/*  883 */     return this.buffer.nioBufferCount();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/*  888 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer(int index, int length) {
/*  893 */     checkIndex(index, length);
/*  894 */     return this.buffer.nioBuffer(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/*  899 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int index, int length) {
/*  904 */     checkIndex(index, length);
/*  905 */     return this.buffer.nioBuffers(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int index, int length) {
/*  910 */     checkIndex(index, length);
/*  911 */     return this.buffer.internalNioBuffer(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(int index, int length, Charset charset) {
/*  916 */     checkIndex(index, length);
/*  917 */     return this.buffer.toString(index, length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset charsetName) {
/*  922 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  927 */     return StringUtil.simpleClassName(this) + '(' + "ridx=" + 
/*      */       
/*  929 */       readerIndex() + ", widx=" + 
/*      */ 
/*      */       
/*  932 */       writerIndex() + ')';
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  938 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable(int size) {
/*  943 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writableBytes() {
/*  948 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxWritableBytes() {
/*  953 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBoolean(boolean value) {
/*  958 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeByte(int value) {
/*  963 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/*  968 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src) {
/*  973 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer src) {
/*  978 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/*  983 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int length) {
/*  988 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src) {
/*  993 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream in, int length) {
/*  998 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel in, int length) {
/* 1003 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(FileChannel in, long position, int length) {
/* 1008 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeInt(int value) {
/* 1013 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeIntLE(int value) {
/* 1018 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLong(long value) {
/* 1023 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLongLE(long value) {
/* 1028 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMedium(int value) {
/* 1033 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMediumLE(int value) {
/* 1038 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeZero(int length) {
/* 1043 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int writerIndex() {
/* 1048 */     return this.buffer.writerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writerIndex(int writerIndex) {
/* 1053 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShort(int value) {
/* 1058 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShortLE(int value) {
/* 1063 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeChar(int value) {
/* 1068 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeFloat(float value) {
/* 1073 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeDouble(double value) {
/* 1078 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int setCharSequence(int index, CharSequence sequence, Charset charset) {
/* 1083 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeCharSequence(CharSequence sequence, Charset charset) {
/* 1088 */     throw reject();
/*      */   }
/*      */   
/*      */   private void checkIndex(int index, int length) {
/* 1092 */     if (index + length > this.buffer.writerIndex()) {
/* 1093 */       throw REPLAY;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkReadableBytes(int readableBytes) {
/* 1098 */     if (this.buffer.readableBytes() < readableBytes) {
/* 1099 */       throw REPLAY;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardSomeReadBytes() {
/* 1105 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public int refCnt() {
/* 1110 */     return this.buffer.refCnt();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain() {
/* 1115 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain(int increment) {
/* 1120 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf touch() {
/* 1125 */     this.buffer.touch();
/* 1126 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf touch(Object hint) {
/* 1131 */     this.buffer.touch(hint);
/* 1132 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release() {
/* 1137 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release(int decrement) {
/* 1142 */     throw reject();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf unwrap() {
/* 1147 */     throw reject();
/*      */   }
/*      */   
/*      */   private static UnsupportedOperationException reject() {
/* 1151 */     return new UnsupportedOperationException("not a replayable operation");
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\ReplayingDecoderByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */