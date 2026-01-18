/*      */ package io.netty.buffer;
/*      */ 
/*      */ import io.netty.util.ByteProcessor;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.StringUtil;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WrappedByteBuf
/*      */   extends ByteBuf
/*      */ {
/*      */   protected final ByteBuf buf;
/*      */   
/*      */   protected WrappedByteBuf(ByteBuf buf) {
/*   45 */     this.buf = (ByteBuf)ObjectUtil.checkNotNull(buf, "buf");
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean hasMemoryAddress() {
/*   50 */     return this.buf.hasMemoryAddress();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isContiguous() {
/*   55 */     return this.buf.isContiguous();
/*      */   }
/*      */ 
/*      */   
/*      */   public final long memoryAddress() {
/*   60 */     return this.buf.memoryAddress();
/*      */   }
/*      */ 
/*      */   
/*      */   public final int capacity() {
/*   65 */     return this.buf.capacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf capacity(int newCapacity) {
/*   70 */     this.buf.capacity(newCapacity);
/*   71 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int maxCapacity() {
/*   76 */     return this.buf.maxCapacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteBufAllocator alloc() {
/*   81 */     return this.buf.alloc();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteOrder order() {
/*   86 */     return this.buf.order();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder endianness) {
/*   91 */     return this.buf.order(endianness);
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteBuf unwrap() {
/*   96 */     return this.buf;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf asReadOnly() {
/*  101 */     return this.buf.asReadOnly();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/*  106 */     return this.buf.isReadOnly();
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isDirect() {
/*  111 */     return this.buf.isDirect();
/*      */   }
/*      */ 
/*      */   
/*      */   public final int readerIndex() {
/*  116 */     return this.buf.readerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteBuf readerIndex(int readerIndex) {
/*  121 */     this.buf.readerIndex(readerIndex);
/*  122 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int writerIndex() {
/*  127 */     return this.buf.writerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteBuf writerIndex(int writerIndex) {
/*  132 */     this.buf.writerIndex(writerIndex);
/*  133 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/*  138 */     this.buf.setIndex(readerIndex, writerIndex);
/*  139 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int readableBytes() {
/*  144 */     return this.buf.readableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public final int writableBytes() {
/*  149 */     return this.buf.writableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public final int maxWritableBytes() {
/*  154 */     return this.buf.maxWritableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxFastWritableBytes() {
/*  159 */     return this.buf.maxFastWritableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isReadable() {
/*  164 */     return this.buf.isReadable();
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isWritable() {
/*  169 */     return this.buf.isWritable();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteBuf clear() {
/*  174 */     this.buf.clear();
/*  175 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteBuf markReaderIndex() {
/*  180 */     this.buf.markReaderIndex();
/*  181 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteBuf resetReaderIndex() {
/*  186 */     this.buf.resetReaderIndex();
/*  187 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteBuf markWriterIndex() {
/*  192 */     this.buf.markWriterIndex();
/*  193 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ByteBuf resetWriterIndex() {
/*  198 */     this.buf.resetWriterIndex();
/*  199 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardReadBytes() {
/*  204 */     this.buf.discardReadBytes();
/*  205 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardSomeReadBytes() {
/*  210 */     this.buf.discardSomeReadBytes();
/*  211 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf ensureWritable(int minWritableBytes) {
/*  216 */     this.buf.ensureWritable(minWritableBytes);
/*  217 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int minWritableBytes, boolean force) {
/*  222 */     return this.buf.ensureWritable(minWritableBytes, force);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int index) {
/*  227 */     return this.buf.getBoolean(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  232 */     return this.buf.getByte(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int index) {
/*  237 */     return this.buf.getUnsignedByte(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int index) {
/*  242 */     return this.buf.getShort(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShortLE(int index) {
/*  247 */     return this.buf.getShortLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int index) {
/*  252 */     return this.buf.getUnsignedShort(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShortLE(int index) {
/*  257 */     return this.buf.getUnsignedShortLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMedium(int index) {
/*  262 */     return this.buf.getMedium(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMediumLE(int index) {
/*  267 */     return this.buf.getMediumLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int index) {
/*  272 */     return this.buf.getUnsignedMedium(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMediumLE(int index) {
/*  277 */     return this.buf.getUnsignedMediumLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int index) {
/*  282 */     return this.buf.getInt(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getIntLE(int index) {
/*  287 */     return this.buf.getIntLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int index) {
/*  292 */     return this.buf.getUnsignedInt(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedIntLE(int index) {
/*  297 */     return this.buf.getUnsignedIntLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int index) {
/*  302 */     return this.buf.getLong(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLongLE(int index) {
/*  307 */     return this.buf.getLongLE(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(int index) {
/*  312 */     return this.buf.getChar(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int index) {
/*  317 */     return this.buf.getFloat(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int index) {
/*  322 */     return this.buf.getDouble(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst) {
/*  327 */     this.buf.getBytes(index, dst);
/*  328 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/*  333 */     this.buf.getBytes(index, dst, length);
/*  334 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/*  339 */     this.buf.getBytes(index, dst, dstIndex, length);
/*  340 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst) {
/*  345 */     this.buf.getBytes(index, dst);
/*  346 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/*  351 */     this.buf.getBytes(index, dst, dstIndex, length);
/*  352 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/*  357 */     this.buf.getBytes(index, dst);
/*  358 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/*  363 */     this.buf.getBytes(index, out, length);
/*  364 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/*  369 */     return this.buf.getBytes(index, out, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/*  374 */     return this.buf.getBytes(index, out, position, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence getCharSequence(int index, int length, Charset charset) {
/*  379 */     return this.buf.getCharSequence(index, length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBoolean(int index, boolean value) {
/*  384 */     this.buf.setBoolean(index, value);
/*  385 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setByte(int index, int value) {
/*  390 */     this.buf.setByte(index, value);
/*  391 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShort(int index, int value) {
/*  396 */     this.buf.setShort(index, value);
/*  397 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShortLE(int index, int value) {
/*  402 */     this.buf.setShortLE(index, value);
/*  403 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMedium(int index, int value) {
/*  408 */     this.buf.setMedium(index, value);
/*  409 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMediumLE(int index, int value) {
/*  414 */     this.buf.setMediumLE(index, value);
/*  415 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setInt(int index, int value) {
/*  420 */     this.buf.setInt(index, value);
/*  421 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIntLE(int index, int value) {
/*  426 */     this.buf.setIntLE(index, value);
/*  427 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLong(int index, long value) {
/*  432 */     this.buf.setLong(index, value);
/*  433 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLongLE(int index, long value) {
/*  438 */     this.buf.setLongLE(index, value);
/*  439 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setChar(int index, int value) {
/*  444 */     this.buf.setChar(index, value);
/*  445 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setFloat(int index, float value) {
/*  450 */     this.buf.setFloat(index, value);
/*  451 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setDouble(int index, double value) {
/*  456 */     this.buf.setDouble(index, value);
/*  457 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src) {
/*  462 */     this.buf.setBytes(index, src);
/*  463 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/*  468 */     this.buf.setBytes(index, src, length);
/*  469 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/*  474 */     this.buf.setBytes(index, src, srcIndex, length);
/*  475 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src) {
/*  480 */     this.buf.setBytes(index, src);
/*  481 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/*  486 */     this.buf.setBytes(index, src, srcIndex, length);
/*  487 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuffer src) {
/*  492 */     this.buf.setBytes(index, src);
/*  493 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, InputStream in, int length) throws IOException {
/*  498 */     return this.buf.setBytes(index, in, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/*  503 */     return this.buf.setBytes(index, in, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/*  508 */     return this.buf.setBytes(index, in, position, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setZero(int index, int length) {
/*  513 */     this.buf.setZero(index, length);
/*  514 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setCharSequence(int index, CharSequence sequence, Charset charset) {
/*  519 */     return this.buf.setCharSequence(index, sequence, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/*  524 */     return this.buf.readBoolean();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/*  529 */     return this.buf.readByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/*  534 */     return this.buf.readUnsignedByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/*  539 */     return this.buf.readShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShortLE() {
/*  544 */     return this.buf.readShortLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/*  549 */     return this.buf.readUnsignedShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShortLE() {
/*  554 */     return this.buf.readUnsignedShortLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/*  559 */     return this.buf.readMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMediumLE() {
/*  564 */     return this.buf.readMediumLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/*  569 */     return this.buf.readUnsignedMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMediumLE() {
/*  574 */     return this.buf.readUnsignedMediumLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/*  579 */     return this.buf.readInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readIntLE() {
/*  584 */     return this.buf.readIntLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/*  589 */     return this.buf.readUnsignedInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedIntLE() {
/*  594 */     return this.buf.readUnsignedIntLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/*  599 */     return this.buf.readLong();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLongLE() {
/*  604 */     return this.buf.readLongLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/*  609 */     return this.buf.readChar();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/*  614 */     return this.buf.readFloat();
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/*  619 */     return this.buf.readDouble();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int length) {
/*  624 */     return this.buf.readBytes(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int length) {
/*  629 */     return this.buf.readSlice(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readRetainedSlice(int length) {
/*  634 */     return this.buf.readRetainedSlice(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst) {
/*  639 */     this.buf.readBytes(dst);
/*  640 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int length) {
/*  645 */     this.buf.readBytes(dst, length);
/*  646 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/*  651 */     this.buf.readBytes(dst, dstIndex, length);
/*  652 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst) {
/*  657 */     this.buf.readBytes(dst);
/*  658 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/*  663 */     this.buf.readBytes(dst, dstIndex, length);
/*  664 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer dst) {
/*  669 */     this.buf.readBytes(dst);
/*  670 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/*  675 */     this.buf.readBytes(out, length);
/*  676 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/*  681 */     return this.buf.readBytes(out, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(FileChannel out, long position, int length) throws IOException {
/*  686 */     return this.buf.readBytes(out, position, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence readCharSequence(int length, Charset charset) {
/*  691 */     return this.buf.readCharSequence(length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public String readString(int length, Charset charset) {
/*  696 */     return this.buf.readString(length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf skipBytes(int length) {
/*  701 */     this.buf.skipBytes(length);
/*  702 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBoolean(boolean value) {
/*  707 */     this.buf.writeBoolean(value);
/*  708 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeByte(int value) {
/*  713 */     this.buf.writeByte(value);
/*  714 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShort(int value) {
/*  719 */     this.buf.writeShort(value);
/*  720 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShortLE(int value) {
/*  725 */     this.buf.writeShortLE(value);
/*  726 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMedium(int value) {
/*  731 */     this.buf.writeMedium(value);
/*  732 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMediumLE(int value) {
/*  737 */     this.buf.writeMediumLE(value);
/*  738 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeInt(int value) {
/*  743 */     this.buf.writeInt(value);
/*  744 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeIntLE(int value) {
/*  749 */     this.buf.writeIntLE(value);
/*  750 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLong(long value) {
/*  755 */     this.buf.writeLong(value);
/*  756 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLongLE(long value) {
/*  761 */     this.buf.writeLongLE(value);
/*  762 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeChar(int value) {
/*  767 */     this.buf.writeChar(value);
/*  768 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeFloat(float value) {
/*  773 */     this.buf.writeFloat(value);
/*  774 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeDouble(double value) {
/*  779 */     this.buf.writeDouble(value);
/*  780 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src) {
/*  785 */     this.buf.writeBytes(src);
/*  786 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int length) {
/*  791 */     this.buf.writeBytes(src, length);
/*  792 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/*  797 */     this.buf.writeBytes(src, srcIndex, length);
/*  798 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src) {
/*  803 */     this.buf.writeBytes(src);
/*  804 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/*  809 */     this.buf.writeBytes(src, srcIndex, length);
/*  810 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer src) {
/*  815 */     this.buf.writeBytes(src);
/*  816 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream in, int length) throws IOException {
/*  821 */     return this.buf.writeBytes(in, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
/*  826 */     return this.buf.writeBytes(in, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(FileChannel in, long position, int length) throws IOException {
/*  831 */     return this.buf.writeBytes(in, position, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeZero(int length) {
/*  836 */     this.buf.writeZero(length);
/*  837 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeCharSequence(CharSequence sequence, Charset charset) {
/*  842 */     return this.buf.writeCharSequence(sequence, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int fromIndex, int toIndex, byte value) {
/*  847 */     return this.buf.indexOf(fromIndex, toIndex, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte value) {
/*  852 */     return this.buf.bytesBefore(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int length, byte value) {
/*  857 */     return this.buf.bytesBefore(length, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int index, int length, byte value) {
/*  862 */     return this.buf.bytesBefore(index, length, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteProcessor processor) {
/*  867 */     return this.buf.forEachByte(processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(int index, int length, ByteProcessor processor) {
/*  872 */     return this.buf.forEachByte(index, length, processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteProcessor processor) {
/*  877 */     return this.buf.forEachByteDesc(processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
/*  882 */     return this.buf.forEachByteDesc(index, length, processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/*  887 */     return this.buf.copy();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int index, int length) {
/*  892 */     return this.buf.copy(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/*  897 */     return this.buf.slice();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice() {
/*  902 */     return this.buf.retainedSlice();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int index, int length) {
/*  907 */     return this.buf.slice(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice(int index, int length) {
/*  912 */     return this.buf.retainedSlice(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/*  917 */     return this.buf.duplicate();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedDuplicate() {
/*  922 */     return this.buf.retainedDuplicate();
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/*  927 */     return this.buf.nioBufferCount();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/*  932 */     return this.buf.nioBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer(int index, int length) {
/*  937 */     return this.buf.nioBuffer(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/*  942 */     return this.buf.nioBuffers();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int index, int length) {
/*  947 */     return this.buf.nioBuffers(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int index, int length) {
/*  952 */     return this.buf.internalNioBuffer(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/*  957 */     return this.buf.hasArray();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] array() {
/*  962 */     return this.buf.array();
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/*  967 */     return this.buf.arrayOffset();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset charset) {
/*  972 */     return this.buf.toString(charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(int index, int length, Charset charset) {
/*  977 */     return this.buf.toString(index, length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  982 */     return this.buf.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  988 */     return this.buf.equals(obj);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf buffer) {
/*  993 */     return this.buf.compareTo(buffer);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  998 */     return StringUtil.simpleClassName(this) + '(' + this.buf.toString() + ')';
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain(int increment) {
/* 1003 */     this.buf.retain(increment);
/* 1004 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain() {
/* 1009 */     this.buf.retain();
/* 1010 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf touch() {
/* 1015 */     this.buf.touch();
/* 1016 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf touch(Object hint) {
/* 1021 */     this.buf.touch(hint);
/* 1022 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isReadable(int size) {
/* 1027 */     return this.buf.isReadable(size);
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isWritable(int size) {
/* 1032 */     return this.buf.isWritable(size);
/*      */   }
/*      */ 
/*      */   
/*      */   public final int refCnt() {
/* 1037 */     return this.buf.refCnt();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release() {
/* 1042 */     return this.buf.release();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release(int decrement) {
/* 1047 */     return this.buf.release(decrement);
/*      */   }
/*      */ 
/*      */   
/*      */   final boolean isAccessible() {
/* 1052 */     return this.buf.isAccessible();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\WrappedByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */