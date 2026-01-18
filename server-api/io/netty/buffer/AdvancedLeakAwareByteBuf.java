/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ByteProcessor;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.ResourceLeakDetector;
/*     */ import io.netty.util.ResourceLeakTracker;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
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
/*     */ final class AdvancedLeakAwareByteBuf
/*     */   extends SimpleLeakAwareByteBuf
/*     */ {
/*     */   private static final String PROP_ACQUIRE_AND_RELEASE_ONLY = "io.netty.leakDetection.acquireAndReleaseOnly";
/*     */   private static final boolean ACQUIRE_AND_RELEASE_ONLY;
/*  42 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AdvancedLeakAwareByteBuf.class);
/*     */   
/*     */   static {
/*  45 */     ACQUIRE_AND_RELEASE_ONLY = SystemPropertyUtil.getBoolean("io.netty.leakDetection.acquireAndReleaseOnly", false);
/*     */     
/*  47 */     if (logger.isDebugEnabled()) {
/*  48 */       logger.debug("-D{}: {}", "io.netty.leakDetection.acquireAndReleaseOnly", Boolean.valueOf(ACQUIRE_AND_RELEASE_ONLY));
/*     */     }
/*     */     
/*  51 */     ResourceLeakDetector.addExclusions(AdvancedLeakAwareByteBuf.class, new String[] { "touch", "recordLeakNonRefCountingOperation" });
/*     */   }
/*     */ 
/*     */   
/*     */   AdvancedLeakAwareByteBuf(ByteBuf buf, ResourceLeakTracker<ByteBuf> leak) {
/*  56 */     super(buf, leak);
/*     */   }
/*     */   
/*     */   AdvancedLeakAwareByteBuf(ByteBuf wrapped, ByteBuf trackedByteBuf, ResourceLeakTracker<ByteBuf> leak) {
/*  60 */     super(wrapped, trackedByteBuf, leak);
/*     */   }
/*     */   
/*     */   static void recordLeakNonRefCountingOperation(ResourceLeakTracker<ByteBuf> leak) {
/*  64 */     if (!ACQUIRE_AND_RELEASE_ONLY) {
/*  65 */       leak.record();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf order(ByteOrder endianness) {
/*  71 */     recordLeakNonRefCountingOperation(this.leak);
/*  72 */     return super.order(endianness);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice() {
/*  77 */     recordLeakNonRefCountingOperation(this.leak);
/*  78 */     return super.slice();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/*  83 */     recordLeakNonRefCountingOperation(this.leak);
/*  84 */     return super.slice(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedSlice() {
/*  89 */     recordLeakNonRefCountingOperation(this.leak);
/*  90 */     return super.retainedSlice();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedSlice(int index, int length) {
/*  95 */     recordLeakNonRefCountingOperation(this.leak);
/*  96 */     return super.retainedSlice(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedDuplicate() {
/* 101 */     recordLeakNonRefCountingOperation(this.leak);
/* 102 */     return super.retainedDuplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readRetainedSlice(int length) {
/* 107 */     recordLeakNonRefCountingOperation(this.leak);
/* 108 */     return super.readRetainedSlice(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/* 113 */     recordLeakNonRefCountingOperation(this.leak);
/* 114 */     return super.duplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readSlice(int length) {
/* 119 */     recordLeakNonRefCountingOperation(this.leak);
/* 120 */     return super.readSlice(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardReadBytes() {
/* 125 */     recordLeakNonRefCountingOperation(this.leak);
/* 126 */     return super.discardReadBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardSomeReadBytes() {
/* 131 */     recordLeakNonRefCountingOperation(this.leak);
/* 132 */     return super.discardSomeReadBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ensureWritable(int minWritableBytes) {
/* 137 */     recordLeakNonRefCountingOperation(this.leak);
/* 138 */     return super.ensureWritable(minWritableBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public int ensureWritable(int minWritableBytes, boolean force) {
/* 143 */     recordLeakNonRefCountingOperation(this.leak);
/* 144 */     return super.ensureWritable(minWritableBytes, force);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(int index) {
/* 149 */     recordLeakNonRefCountingOperation(this.leak);
/* 150 */     return super.getBoolean(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 155 */     recordLeakNonRefCountingOperation(this.leak);
/* 156 */     return super.getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getUnsignedByte(int index) {
/* 161 */     recordLeakNonRefCountingOperation(this.leak);
/* 162 */     return super.getUnsignedByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 167 */     recordLeakNonRefCountingOperation(this.leak);
/* 168 */     return super.getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedShort(int index) {
/* 173 */     recordLeakNonRefCountingOperation(this.leak);
/* 174 */     return super.getUnsignedShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMedium(int index) {
/* 179 */     recordLeakNonRefCountingOperation(this.leak);
/* 180 */     return super.getMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 185 */     recordLeakNonRefCountingOperation(this.leak);
/* 186 */     return super.getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 191 */     recordLeakNonRefCountingOperation(this.leak);
/* 192 */     return super.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUnsignedInt(int index) {
/* 197 */     recordLeakNonRefCountingOperation(this.leak);
/* 198 */     return super.getUnsignedInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 203 */     recordLeakNonRefCountingOperation(this.leak);
/* 204 */     return super.getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public char getChar(int index) {
/* 209 */     recordLeakNonRefCountingOperation(this.leak);
/* 210 */     return super.getChar(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(int index) {
/* 215 */     recordLeakNonRefCountingOperation(this.leak);
/* 216 */     return super.getFloat(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/* 221 */     recordLeakNonRefCountingOperation(this.leak);
/* 222 */     return super.getDouble(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst) {
/* 227 */     recordLeakNonRefCountingOperation(this.leak);
/* 228 */     return super.getBytes(index, dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/* 233 */     recordLeakNonRefCountingOperation(this.leak);
/* 234 */     return super.getBytes(index, dst, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 239 */     recordLeakNonRefCountingOperation(this.leak);
/* 240 */     return super.getBytes(index, dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst) {
/* 245 */     recordLeakNonRefCountingOperation(this.leak);
/* 246 */     return super.getBytes(index, dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 251 */     recordLeakNonRefCountingOperation(this.leak);
/* 252 */     return super.getBytes(index, dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 257 */     recordLeakNonRefCountingOperation(this.leak);
/* 258 */     return super.getBytes(index, dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 263 */     recordLeakNonRefCountingOperation(this.leak);
/* 264 */     return super.getBytes(index, out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 269 */     recordLeakNonRefCountingOperation(this.leak);
/* 270 */     return super.getBytes(index, out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public CharSequence getCharSequence(int index, int length, Charset charset) {
/* 275 */     recordLeakNonRefCountingOperation(this.leak);
/* 276 */     return super.getCharSequence(index, length, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBoolean(int index, boolean value) {
/* 281 */     recordLeakNonRefCountingOperation(this.leak);
/* 282 */     return super.setBoolean(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 287 */     recordLeakNonRefCountingOperation(this.leak);
/* 288 */     return super.setByte(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 293 */     recordLeakNonRefCountingOperation(this.leak);
/* 294 */     return super.setShort(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 299 */     recordLeakNonRefCountingOperation(this.leak);
/* 300 */     return super.setMedium(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 305 */     recordLeakNonRefCountingOperation(this.leak);
/* 306 */     return super.setInt(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 311 */     recordLeakNonRefCountingOperation(this.leak);
/* 312 */     return super.setLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setChar(int index, int value) {
/* 317 */     recordLeakNonRefCountingOperation(this.leak);
/* 318 */     return super.setChar(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setFloat(int index, float value) {
/* 323 */     recordLeakNonRefCountingOperation(this.leak);
/* 324 */     return super.setFloat(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setDouble(int index, double value) {
/* 329 */     recordLeakNonRefCountingOperation(this.leak);
/* 330 */     return super.setDouble(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src) {
/* 335 */     recordLeakNonRefCountingOperation(this.leak);
/* 336 */     return super.setBytes(index, src);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/* 341 */     recordLeakNonRefCountingOperation(this.leak);
/* 342 */     return super.setBytes(index, src, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 347 */     recordLeakNonRefCountingOperation(this.leak);
/* 348 */     return super.setBytes(index, src, srcIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src) {
/* 353 */     recordLeakNonRefCountingOperation(this.leak);
/* 354 */     return super.setBytes(index, src);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 359 */     recordLeakNonRefCountingOperation(this.leak);
/* 360 */     return super.setBytes(index, src, srcIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 365 */     recordLeakNonRefCountingOperation(this.leak);
/* 366 */     return super.setBytes(index, src);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 371 */     recordLeakNonRefCountingOperation(this.leak);
/* 372 */     return super.setBytes(index, in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 377 */     recordLeakNonRefCountingOperation(this.leak);
/* 378 */     return super.setBytes(index, in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setZero(int index, int length) {
/* 383 */     recordLeakNonRefCountingOperation(this.leak);
/* 384 */     return super.setZero(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setCharSequence(int index, CharSequence sequence, Charset charset) {
/* 389 */     recordLeakNonRefCountingOperation(this.leak);
/* 390 */     return super.setCharSequence(index, sequence, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBoolean() {
/* 395 */     recordLeakNonRefCountingOperation(this.leak);
/* 396 */     return super.readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() {
/* 401 */     recordLeakNonRefCountingOperation(this.leak);
/* 402 */     return super.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readUnsignedByte() {
/* 407 */     recordLeakNonRefCountingOperation(this.leak);
/* 408 */     return super.readUnsignedByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() {
/* 413 */     recordLeakNonRefCountingOperation(this.leak);
/* 414 */     return super.readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() {
/* 419 */     recordLeakNonRefCountingOperation(this.leak);
/* 420 */     return super.readUnsignedShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readMedium() {
/* 425 */     recordLeakNonRefCountingOperation(this.leak);
/* 426 */     return super.readMedium();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedMedium() {
/* 431 */     recordLeakNonRefCountingOperation(this.leak);
/* 432 */     return super.readUnsignedMedium();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() {
/* 437 */     recordLeakNonRefCountingOperation(this.leak);
/* 438 */     return super.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readUnsignedInt() {
/* 443 */     recordLeakNonRefCountingOperation(this.leak);
/* 444 */     return super.readUnsignedInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() {
/* 449 */     recordLeakNonRefCountingOperation(this.leak);
/* 450 */     return super.readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public char readChar() {
/* 455 */     recordLeakNonRefCountingOperation(this.leak);
/* 456 */     return super.readChar();
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() {
/* 461 */     recordLeakNonRefCountingOperation(this.leak);
/* 462 */     return super.readFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() {
/* 467 */     recordLeakNonRefCountingOperation(this.leak);
/* 468 */     return super.readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(int length) {
/* 473 */     recordLeakNonRefCountingOperation(this.leak);
/* 474 */     return super.readBytes(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst) {
/* 479 */     recordLeakNonRefCountingOperation(this.leak);
/* 480 */     return super.readBytes(dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int length) {
/* 485 */     recordLeakNonRefCountingOperation(this.leak);
/* 486 */     return super.readBytes(dst, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/* 491 */     recordLeakNonRefCountingOperation(this.leak);
/* 492 */     return super.readBytes(dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst) {
/* 497 */     recordLeakNonRefCountingOperation(this.leak);
/* 498 */     return super.readBytes(dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 503 */     recordLeakNonRefCountingOperation(this.leak);
/* 504 */     return super.readBytes(dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 509 */     recordLeakNonRefCountingOperation(this.leak);
/* 510 */     return super.readBytes(dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 515 */     recordLeakNonRefCountingOperation(this.leak);
/* 516 */     return super.readBytes(out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 521 */     recordLeakNonRefCountingOperation(this.leak);
/* 522 */     return super.readBytes(out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public CharSequence readCharSequence(int length, Charset charset) {
/* 527 */     recordLeakNonRefCountingOperation(this.leak);
/* 528 */     return super.readCharSequence(length, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public String readString(int length, Charset charset) {
/* 533 */     recordLeakNonRefCountingOperation(this.leak);
/* 534 */     return super.readString(length, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf skipBytes(int length) {
/* 539 */     recordLeakNonRefCountingOperation(this.leak);
/* 540 */     return super.skipBytes(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBoolean(boolean value) {
/* 545 */     recordLeakNonRefCountingOperation(this.leak);
/* 546 */     return super.writeBoolean(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeByte(int value) {
/* 551 */     recordLeakNonRefCountingOperation(this.leak);
/* 552 */     return super.writeByte(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeShort(int value) {
/* 557 */     recordLeakNonRefCountingOperation(this.leak);
/* 558 */     return super.writeShort(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeMedium(int value) {
/* 563 */     recordLeakNonRefCountingOperation(this.leak);
/* 564 */     return super.writeMedium(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeInt(int value) {
/* 569 */     recordLeakNonRefCountingOperation(this.leak);
/* 570 */     return super.writeInt(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeLong(long value) {
/* 575 */     recordLeakNonRefCountingOperation(this.leak);
/* 576 */     return super.writeLong(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeChar(int value) {
/* 581 */     recordLeakNonRefCountingOperation(this.leak);
/* 582 */     return super.writeChar(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeFloat(float value) {
/* 587 */     recordLeakNonRefCountingOperation(this.leak);
/* 588 */     return super.writeFloat(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeDouble(double value) {
/* 593 */     recordLeakNonRefCountingOperation(this.leak);
/* 594 */     return super.writeDouble(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src) {
/* 599 */     recordLeakNonRefCountingOperation(this.leak);
/* 600 */     return super.writeBytes(src);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int length) {
/* 605 */     recordLeakNonRefCountingOperation(this.leak);
/* 606 */     return super.writeBytes(src, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/* 611 */     recordLeakNonRefCountingOperation(this.leak);
/* 612 */     return super.writeBytes(src, srcIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src) {
/* 617 */     recordLeakNonRefCountingOperation(this.leak);
/* 618 */     return super.writeBytes(src);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/* 623 */     recordLeakNonRefCountingOperation(this.leak);
/* 624 */     return super.writeBytes(src, srcIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer src) {
/* 629 */     recordLeakNonRefCountingOperation(this.leak);
/* 630 */     return super.writeBytes(src);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(InputStream in, int length) throws IOException {
/* 635 */     recordLeakNonRefCountingOperation(this.leak);
/* 636 */     return super.writeBytes(in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
/* 641 */     recordLeakNonRefCountingOperation(this.leak);
/* 642 */     return super.writeBytes(in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeZero(int length) {
/* 647 */     recordLeakNonRefCountingOperation(this.leak);
/* 648 */     return super.writeZero(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(int fromIndex, int toIndex, byte value) {
/* 653 */     recordLeakNonRefCountingOperation(this.leak);
/* 654 */     return super.indexOf(fromIndex, toIndex, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(byte value) {
/* 659 */     recordLeakNonRefCountingOperation(this.leak);
/* 660 */     return super.bytesBefore(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int length, byte value) {
/* 665 */     recordLeakNonRefCountingOperation(this.leak);
/* 666 */     return super.bytesBefore(length, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int index, int length, byte value) {
/* 671 */     recordLeakNonRefCountingOperation(this.leak);
/* 672 */     return super.bytesBefore(index, length, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(ByteProcessor processor) {
/* 677 */     recordLeakNonRefCountingOperation(this.leak);
/* 678 */     return super.forEachByte(processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(int index, int length, ByteProcessor processor) {
/* 683 */     recordLeakNonRefCountingOperation(this.leak);
/* 684 */     return super.forEachByte(index, length, processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(ByteProcessor processor) {
/* 689 */     recordLeakNonRefCountingOperation(this.leak);
/* 690 */     return super.forEachByteDesc(processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
/* 695 */     recordLeakNonRefCountingOperation(this.leak);
/* 696 */     return super.forEachByteDesc(index, length, processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy() {
/* 701 */     recordLeakNonRefCountingOperation(this.leak);
/* 702 */     return super.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 707 */     recordLeakNonRefCountingOperation(this.leak);
/* 708 */     return super.copy(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 713 */     recordLeakNonRefCountingOperation(this.leak);
/* 714 */     return super.nioBufferCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer() {
/* 719 */     recordLeakNonRefCountingOperation(this.leak);
/* 720 */     return super.nioBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 725 */     recordLeakNonRefCountingOperation(this.leak);
/* 726 */     return super.nioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 731 */     recordLeakNonRefCountingOperation(this.leak);
/* 732 */     return super.nioBuffers();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 737 */     recordLeakNonRefCountingOperation(this.leak);
/* 738 */     return super.nioBuffers(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 743 */     recordLeakNonRefCountingOperation(this.leak);
/* 744 */     return super.internalNioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(Charset charset) {
/* 749 */     recordLeakNonRefCountingOperation(this.leak);
/* 750 */     return super.toString(charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(int index, int length, Charset charset) {
/* 755 */     recordLeakNonRefCountingOperation(this.leak);
/* 756 */     return super.toString(index, length, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/* 761 */     recordLeakNonRefCountingOperation(this.leak);
/* 762 */     return super.capacity(newCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShortLE(int index) {
/* 767 */     recordLeakNonRefCountingOperation(this.leak);
/* 768 */     return super.getShortLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedShortLE(int index) {
/* 773 */     recordLeakNonRefCountingOperation(this.leak);
/* 774 */     return super.getUnsignedShortLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMediumLE(int index) {
/* 779 */     recordLeakNonRefCountingOperation(this.leak);
/* 780 */     return super.getMediumLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMediumLE(int index) {
/* 785 */     recordLeakNonRefCountingOperation(this.leak);
/* 786 */     return super.getUnsignedMediumLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntLE(int index) {
/* 791 */     recordLeakNonRefCountingOperation(this.leak);
/* 792 */     return super.getIntLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUnsignedIntLE(int index) {
/* 797 */     recordLeakNonRefCountingOperation(this.leak);
/* 798 */     return super.getUnsignedIntLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLongLE(int index) {
/* 803 */     recordLeakNonRefCountingOperation(this.leak);
/* 804 */     return super.getLongLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShortLE(int index, int value) {
/* 809 */     recordLeakNonRefCountingOperation(this.leak);
/* 810 */     return super.setShortLE(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setIntLE(int index, int value) {
/* 815 */     recordLeakNonRefCountingOperation(this.leak);
/* 816 */     return super.setIntLE(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMediumLE(int index, int value) {
/* 821 */     recordLeakNonRefCountingOperation(this.leak);
/* 822 */     return super.setMediumLE(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLongLE(int index, long value) {
/* 827 */     recordLeakNonRefCountingOperation(this.leak);
/* 828 */     return super.setLongLE(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShortLE() {
/* 833 */     recordLeakNonRefCountingOperation(this.leak);
/* 834 */     return super.readShortLE();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShortLE() {
/* 839 */     recordLeakNonRefCountingOperation(this.leak);
/* 840 */     return super.readUnsignedShortLE();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readMediumLE() {
/* 845 */     recordLeakNonRefCountingOperation(this.leak);
/* 846 */     return super.readMediumLE();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedMediumLE() {
/* 851 */     recordLeakNonRefCountingOperation(this.leak);
/* 852 */     return super.readUnsignedMediumLE();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readIntLE() {
/* 857 */     recordLeakNonRefCountingOperation(this.leak);
/* 858 */     return super.readIntLE();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readUnsignedIntLE() {
/* 863 */     recordLeakNonRefCountingOperation(this.leak);
/* 864 */     return super.readUnsignedIntLE();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLongLE() {
/* 869 */     recordLeakNonRefCountingOperation(this.leak);
/* 870 */     return super.readLongLE();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeShortLE(int value) {
/* 875 */     recordLeakNonRefCountingOperation(this.leak);
/* 876 */     return super.writeShortLE(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeMediumLE(int value) {
/* 881 */     recordLeakNonRefCountingOperation(this.leak);
/* 882 */     return super.writeMediumLE(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeIntLE(int value) {
/* 887 */     recordLeakNonRefCountingOperation(this.leak);
/* 888 */     return super.writeIntLE(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeLongLE(long value) {
/* 893 */     recordLeakNonRefCountingOperation(this.leak);
/* 894 */     return super.writeLongLE(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeCharSequence(CharSequence sequence, Charset charset) {
/* 899 */     recordLeakNonRefCountingOperation(this.leak);
/* 900 */     return super.writeCharSequence(sequence, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/* 905 */     recordLeakNonRefCountingOperation(this.leak);
/* 906 */     return super.getBytes(index, out, position, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/* 911 */     recordLeakNonRefCountingOperation(this.leak);
/* 912 */     return super.setBytes(index, in, position, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(FileChannel out, long position, int length) throws IOException {
/* 917 */     recordLeakNonRefCountingOperation(this.leak);
/* 918 */     return super.readBytes(out, position, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(FileChannel in, long position, int length) throws IOException {
/* 923 */     recordLeakNonRefCountingOperation(this.leak);
/* 924 */     return super.writeBytes(in, position, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf asReadOnly() {
/* 929 */     recordLeakNonRefCountingOperation(this.leak);
/* 930 */     return super.asReadOnly();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain() {
/* 935 */     this.leak.record();
/* 936 */     return super.retain();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain(int increment) {
/* 941 */     this.leak.record();
/* 942 */     return super.retain(increment);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 947 */     this.leak.record();
/* 948 */     return super.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 953 */     this.leak.record();
/* 954 */     return super.release(decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf touch() {
/* 959 */     this.leak.record();
/* 960 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf touch(Object hint) {
/* 965 */     this.leak.record(hint);
/* 966 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AdvancedLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf buf, ByteBuf trackedByteBuf, ResourceLeakTracker<ByteBuf> leakTracker) {
/* 972 */     return new AdvancedLeakAwareByteBuf(buf, trackedByteBuf, leakTracker);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AdvancedLeakAwareByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */