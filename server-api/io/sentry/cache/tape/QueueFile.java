/*     */ package io.sentry.cache.tape;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ @Internal
/*     */ public final class QueueFile
/*     */   implements Closeable, Iterable<byte[]>
/*     */ {
/*     */   private static final int VERSIONED_HEADER = -2147483647;
/*     */   static final int INITIAL_LENGTH = 4096;
/*  68 */   private static final byte[] ZEROES = new byte[4096];
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
/*     */   RandomAccessFile raf;
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
/*     */   final File file;
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
/* 103 */   final int headerLength = 32;
/*     */ 
/*     */   
/*     */   long fileLength;
/*     */ 
/*     */   
/*     */   int elementCount;
/*     */ 
/*     */   
/*     */   Element first;
/*     */ 
/*     */   
/*     */   private Element last;
/*     */ 
/*     */   
/* 118 */   private final byte[] buffer = new byte[32];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   int modCount = 0;
/*     */ 
/*     */   
/*     */   private final boolean zero;
/*     */   
/*     */   private final int maxElements;
/*     */   
/*     */   boolean closed;
/*     */ 
/*     */   
/*     */   static RandomAccessFile initializeFromFile(File file) throws IOException {
/* 136 */     if (!file.exists()) {
/*     */       
/* 138 */       File tempFile = new File(file.getPath() + ".tmp");
/* 139 */       RandomAccessFile raf = open(tempFile);
/*     */       try {
/* 141 */         raf.setLength(4096L);
/* 142 */         raf.seek(0L);
/* 143 */         raf.writeInt(-2147483647);
/* 144 */         raf.writeLong(4096L);
/*     */       } finally {
/* 146 */         raf.close();
/*     */       } 
/*     */ 
/*     */       
/* 150 */       if (!tempFile.renameTo(file)) {
/* 151 */         throw new IOException("Rename failed!");
/*     */       }
/*     */     } 
/*     */     
/* 155 */     return open(file);
/*     */   }
/*     */ 
/*     */   
/*     */   private static RandomAccessFile open(File file) throws FileNotFoundException {
/* 160 */     return new RandomAccessFile(file, "rwd");
/*     */   }
/*     */   
/*     */   QueueFile(File file, RandomAccessFile raf, boolean zero, int maxElements) throws IOException {
/* 164 */     this.file = file;
/* 165 */     this.raf = raf;
/* 166 */     this.zero = zero;
/* 167 */     this.maxElements = maxElements;
/*     */     
/* 169 */     readInitialData();
/*     */   }
/*     */   
/*     */   private void readInitialData() throws IOException {
/* 173 */     this.raf.seek(0L);
/* 174 */     this.raf.readFully(this.buffer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     this.fileLength = readLong(this.buffer, 4);
/* 180 */     this.elementCount = readInt(this.buffer, 12);
/* 181 */     long firstOffset = readLong(this.buffer, 16);
/* 182 */     long lastOffset = readLong(this.buffer, 24);
/*     */     
/* 184 */     if (this.fileLength > this.raf.length())
/* 185 */       throw new IOException("File is truncated. Expected length: " + this.fileLength + ", Actual length: " + this.raf
/* 186 */           .length()); 
/* 187 */     if (this.fileLength <= 32L) {
/* 188 */       throw new IOException("File is corrupt; length stored in header (" + this.fileLength + ") is invalid.");
/*     */     }
/*     */ 
/*     */     
/* 192 */     this.first = readElement(firstOffset);
/* 193 */     this.last = readElement(lastOffset);
/*     */   }
/*     */   
/*     */   private void resetFile() throws IOException {
/* 197 */     this.raf.close();
/* 198 */     this.file.delete();
/* 199 */     this.raf = initializeFromFile(this.file);
/* 200 */     readInitialData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeInt(byte[] buffer, int offset, int value) {
/* 208 */     buffer[offset] = (byte)(value >> 24);
/* 209 */     buffer[offset + 1] = (byte)(value >> 16);
/* 210 */     buffer[offset + 2] = (byte)(value >> 8);
/* 211 */     buffer[offset + 3] = (byte)value;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int readInt(byte[] buffer, int offset) {
/* 216 */     return ((buffer[offset] & 0xFF) << 24) + ((buffer[offset + 1] & 0xFF) << 16) + ((buffer[offset + 2] & 0xFF) << 8) + (buffer[offset + 3] & 0xFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeLong(byte[] buffer, int offset, long value) {
/* 227 */     buffer[offset] = (byte)(int)(value >> 56L);
/* 228 */     buffer[offset + 1] = (byte)(int)(value >> 48L);
/* 229 */     buffer[offset + 2] = (byte)(int)(value >> 40L);
/* 230 */     buffer[offset + 3] = (byte)(int)(value >> 32L);
/* 231 */     buffer[offset + 4] = (byte)(int)(value >> 24L);
/* 232 */     buffer[offset + 5] = (byte)(int)(value >> 16L);
/* 233 */     buffer[offset + 6] = (byte)(int)(value >> 8L);
/* 234 */     buffer[offset + 7] = (byte)(int)value;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long readLong(byte[] buffer, int offset) {
/* 239 */     return ((buffer[offset] & 0xFFL) << 56L) + ((buffer[offset + 1] & 0xFFL) << 48L) + ((buffer[offset + 2] & 0xFFL) << 40L) + ((buffer[offset + 3] & 0xFFL) << 32L) + ((buffer[offset + 4] & 0xFFL) << 24L) + ((buffer[offset + 5] & 0xFFL) << 16L) + ((buffer[offset + 6] & 0xFFL) << 8L) + (buffer[offset + 7] & 0xFFL);
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
/*     */   private void writeHeader(long fileLength, int elementCount, long firstPosition, long lastPosition) throws IOException {
/* 257 */     this.raf.seek(0L);
/*     */     
/* 259 */     writeInt(this.buffer, 0, -2147483647);
/* 260 */     writeLong(this.buffer, 4, fileLength);
/* 261 */     writeInt(this.buffer, 12, elementCount);
/* 262 */     writeLong(this.buffer, 16, firstPosition);
/* 263 */     writeLong(this.buffer, 24, lastPosition);
/* 264 */     this.raf.write(this.buffer, 0, 32);
/*     */   }
/*     */   
/*     */   Element readElement(long position) throws IOException {
/* 268 */     if (position == 0L) return Element.NULL; 
/* 269 */     boolean success = ringRead(position, this.buffer, 0, 4);
/* 270 */     if (!success) {
/* 271 */       return Element.NULL;
/*     */     }
/* 273 */     int length = readInt(this.buffer, 0);
/* 274 */     return new Element(position, length);
/*     */   }
/*     */ 
/*     */   
/*     */   long wrapPosition(long position) {
/* 279 */     return (position < this.fileLength) ? position : (32L + position - this.fileLength);
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
/*     */   private void ringWrite(long position, byte[] buffer, int offset, int count) throws IOException {
/* 291 */     position = wrapPosition(position);
/* 292 */     if (position + count <= this.fileLength) {
/* 293 */       this.raf.seek(position);
/* 294 */       this.raf.write(buffer, offset, count);
/*     */     }
/*     */     else {
/*     */       
/* 298 */       int beforeEof = (int)(this.fileLength - position);
/* 299 */       this.raf.seek(position);
/* 300 */       this.raf.write(buffer, offset, beforeEof);
/* 301 */       this.raf.seek(32L);
/* 302 */       this.raf.write(buffer, offset + beforeEof, count - beforeEof);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void ringErase(long position, long length) throws IOException {
/* 307 */     while (length > 0L) {
/* 308 */       int chunk = (int)Math.min(length, ZEROES.length);
/* 309 */       ringWrite(position, ZEROES, 0, chunk);
/* 310 */       length -= chunk;
/* 311 */       position += chunk;
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
/*     */   boolean ringRead(long position, byte[] buffer, int offset, int count) throws IOException {
/*     */     try {
/* 325 */       position = wrapPosition(position);
/* 326 */       if (position + count <= this.fileLength) {
/* 327 */         this.raf.seek(position);
/* 328 */         this.raf.readFully(buffer, offset, count);
/*     */       }
/*     */       else {
/*     */         
/* 332 */         int beforeEof = (int)(this.fileLength - position);
/* 333 */         this.raf.seek(position);
/* 334 */         this.raf.readFully(buffer, offset, beforeEof);
/* 335 */         this.raf.seek(32L);
/* 336 */         this.raf.readFully(buffer, offset + beforeEof, count - beforeEof);
/*     */       } 
/* 338 */       return true;
/* 339 */     } catch (EOFException e) {
/*     */ 
/*     */       
/* 342 */       resetFile();
/* 343 */     } catch (IOException e) {
/* 344 */       throw e;
/* 345 */     } catch (Throwable e) {
/*     */       
/* 347 */       resetFile();
/*     */     } 
/* 349 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(byte[] data) throws IOException {
/* 358 */     add(data, 0, data.length);
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
/*     */   public void add(byte[] data, int offset, int count) throws IOException {
/* 371 */     if (data == null) {
/* 372 */       throw new NullPointerException("data == null");
/*     */     }
/* 374 */     if ((offset | count) < 0 || count > data.length - offset) {
/* 375 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 377 */     if (this.closed) throw new IllegalStateException("closed");
/*     */ 
/*     */     
/* 380 */     if (isAtFullCapacity()) {
/* 381 */       remove();
/*     */     }
/*     */     
/* 384 */     expandIfNecessary(count);
/*     */ 
/*     */     
/* 387 */     boolean wasEmpty = isEmpty();
/*     */     
/* 389 */     long position = wasEmpty ? 32L : wrapPosition(this.last.position + 4L + this.last.length);
/* 390 */     Element newLast = new Element(position, count);
/*     */ 
/*     */     
/* 393 */     writeInt(this.buffer, 0, count);
/* 394 */     ringWrite(newLast.position, this.buffer, 0, 4);
/*     */ 
/*     */     
/* 397 */     ringWrite(newLast.position + 4L, data, offset, count);
/*     */ 
/*     */     
/* 400 */     long firstPosition = wasEmpty ? newLast.position : this.first.position;
/* 401 */     writeHeader(this.fileLength, this.elementCount + 1, firstPosition, newLast.position);
/* 402 */     this.last = newLast;
/* 403 */     this.elementCount++;
/* 404 */     this.modCount++;
/* 405 */     if (wasEmpty) this.first = this.last; 
/*     */   }
/*     */   
/*     */   private long usedBytes() {
/* 409 */     if (this.elementCount == 0) return 32L;
/*     */     
/* 411 */     if (this.last.position >= this.first.position)
/*     */     {
/* 413 */       return this.last.position - this.first.position + 4L + this.last.length + 32L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     return this.last.position + 4L + this.last.length + this.fileLength - this.first.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long remainingBytes() {
/* 428 */     return this.fileLength - usedBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 433 */     return (this.elementCount == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void expandIfNecessary(long dataLength) throws IOException {
/* 442 */     long newLength, elementLength = 4L + dataLength;
/* 443 */     long remainingBytes = remainingBytes();
/* 444 */     if (remainingBytes >= elementLength) {
/*     */       return;
/*     */     }
/* 447 */     long previousLength = this.fileLength;
/*     */ 
/*     */     
/*     */     do {
/* 451 */       remainingBytes += previousLength;
/* 452 */       newLength = previousLength << 1L;
/* 453 */       previousLength = newLength;
/* 454 */     } while (remainingBytes < elementLength);
/*     */     
/* 456 */     setLength(newLength);
/*     */ 
/*     */     
/* 459 */     long endOfLastElement = wrapPosition(this.last.position + 4L + this.last.length);
/* 460 */     long count = 0L;
/*     */     
/* 462 */     if (endOfLastElement <= this.first.position) {
/* 463 */       FileChannel channel = this.raf.getChannel();
/* 464 */       channel.position(this.fileLength);
/* 465 */       count = endOfLastElement - 32L;
/* 466 */       if (channel.transferTo(32L, count, channel) != count) {
/* 467 */         throw new AssertionError("Copied insufficient number of bytes!");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 472 */     if (this.last.position < this.first.position) {
/* 473 */       long newLastPosition = this.fileLength + this.last.position - 32L;
/* 474 */       writeHeader(newLength, this.elementCount, this.first.position, newLastPosition);
/* 475 */       this.last = new Element(newLastPosition, this.last.length);
/*     */     } else {
/* 477 */       writeHeader(newLength, this.elementCount, this.first.position, this.last.position);
/*     */     } 
/*     */     
/* 480 */     this.fileLength = newLength;
/*     */     
/* 482 */     if (this.zero) {
/* 483 */       ringErase(32L, count);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLength(long newLength) throws IOException {
/* 490 */     this.raf.setLength(newLength);
/* 491 */     this.raf.getChannel().force(true);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public byte[] peek() throws IOException {
/* 496 */     if (this.closed) throw new IllegalStateException("closed"); 
/* 497 */     if (isEmpty()) return null; 
/* 498 */     int length = this.first.length;
/* 499 */     byte[] data = new byte[length];
/* 500 */     boolean success = ringRead(this.first.position + 4L, data, 0, length);
/* 501 */     return success ? data : null;
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
/*     */   public Iterator<byte[]> iterator() {
/* 516 */     return new ElementIterator();
/*     */   }
/*     */   
/*     */   private final class ElementIterator
/*     */     implements Iterator<byte[]> {
/* 521 */     int nextElementIndex = 0;
/*     */ 
/*     */     
/* 524 */     private long nextElementPosition = QueueFile.this.first.position;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 530 */     int expectedModCount = QueueFile.this.modCount;
/*     */ 
/*     */ 
/*     */     
/*     */     private void checkForComodification() {
/* 535 */       if (QueueFile.this.modCount != this.expectedModCount) throw new ConcurrentModificationException();
/*     */     
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 540 */       if (QueueFile.this.closed) throw new IllegalStateException("closed"); 
/* 541 */       checkForComodification();
/* 542 */       return (this.nextElementIndex != QueueFile.this.elementCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] next() {
/* 547 */       if (QueueFile.this.closed) throw new IllegalStateException("closed"); 
/* 548 */       checkForComodification();
/* 549 */       if (QueueFile.this.isEmpty()) throw new NoSuchElementException(); 
/* 550 */       if (this.nextElementIndex >= QueueFile.this.elementCount) throw new NoSuchElementException();
/*     */ 
/*     */       
/*     */       try {
/* 554 */         QueueFile.Element current = QueueFile.this.readElement(this.nextElementPosition);
/* 555 */         byte[] buffer = new byte[current.length];
/* 556 */         this.nextElementPosition = QueueFile.this.wrapPosition(current.position + 4L);
/* 557 */         boolean success = QueueFile.this.ringRead(this.nextElementPosition, buffer, 0, current.length);
/* 558 */         if (!success) {
/*     */           
/* 560 */           this.nextElementIndex = QueueFile.this.elementCount;
/* 561 */           return QueueFile.ZEROES;
/*     */         } 
/*     */ 
/*     */         
/* 565 */         this
/* 566 */           .nextElementPosition = QueueFile.this.wrapPosition(current.position + 4L + current.length);
/* 567 */         this.nextElementIndex++;
/*     */ 
/*     */         
/* 570 */         return buffer;
/* 571 */       } catch (IOException e) {
/* 572 */         throw (Error)QueueFile.getSneakyThrowable(e);
/* 573 */       } catch (OutOfMemoryError e) {
/*     */         
/*     */         try {
/* 576 */           QueueFile.this.resetFile();
/*     */           
/* 578 */           this.nextElementIndex = QueueFile.this.elementCount;
/* 579 */         } catch (IOException ex) {
/* 580 */           throw (Error)QueueFile.getSneakyThrowable(ex);
/*     */         } 
/* 582 */         return QueueFile.ZEROES;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 588 */       checkForComodification();
/*     */       
/* 590 */       if (QueueFile.this.isEmpty()) throw new NoSuchElementException(); 
/* 591 */       if (this.nextElementIndex != 1) {
/* 592 */         throw new UnsupportedOperationException("Removal is only permitted from the head.");
/*     */       }
/*     */       
/*     */       try {
/* 596 */         QueueFile.this.remove();
/* 597 */       } catch (IOException e) {
/* 598 */         throw (Error)QueueFile.getSneakyThrowable(e);
/*     */       } 
/*     */       
/* 601 */       this.expectedModCount = QueueFile.this.modCount;
/* 602 */       this.nextElementIndex--;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 608 */     return this.elementCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() throws IOException {
/* 617 */     remove(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int n) throws IOException {
/* 626 */     if (n < 0) {
/* 627 */       throw new IllegalArgumentException("Cannot remove negative (" + n + ") number of elements.");
/*     */     }
/* 629 */     if (n == 0) {
/*     */       return;
/*     */     }
/* 632 */     if (n == this.elementCount) {
/* 633 */       clear();
/*     */       return;
/*     */     } 
/* 636 */     if (isEmpty()) {
/* 637 */       throw new NoSuchElementException();
/*     */     }
/* 639 */     if (n > this.elementCount) {
/* 640 */       throw new IllegalArgumentException("Cannot remove more elements (" + n + ") than present in queue (" + this.elementCount + ").");
/*     */     }
/*     */ 
/*     */     
/* 644 */     long eraseStartPosition = this.first.position;
/* 645 */     long eraseTotalLength = 0L;
/*     */ 
/*     */     
/* 648 */     long newFirstPosition = this.first.position;
/* 649 */     int newFirstLength = this.first.length;
/* 650 */     for (int i = 0; i < n; i++) {
/* 651 */       eraseTotalLength += (4 + newFirstLength);
/* 652 */       newFirstPosition = wrapPosition(newFirstPosition + 4L + newFirstLength);
/* 653 */       boolean success = ringRead(newFirstPosition, this.buffer, 0, 4);
/* 654 */       if (!success) {
/*     */         return;
/*     */       }
/* 657 */       newFirstLength = readInt(this.buffer, 0);
/*     */     } 
/*     */ 
/*     */     
/* 661 */     writeHeader(this.fileLength, this.elementCount - n, newFirstPosition, this.last.position);
/* 662 */     this.elementCount -= n;
/* 663 */     this.modCount++;
/* 664 */     this.first = new Element(newFirstPosition, newFirstLength);
/*     */     
/* 666 */     if (this.zero) {
/* 667 */       ringErase(eraseStartPosition, eraseTotalLength);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() throws IOException {
/* 673 */     if (this.closed) throw new IllegalStateException("closed");
/*     */ 
/*     */     
/* 676 */     writeHeader(4096L, 0, 0L, 0L);
/*     */     
/* 678 */     if (this.zero) {
/*     */       
/* 680 */       this.raf.seek(32L);
/* 681 */       this.raf.write(ZEROES, 0, 4064);
/*     */     } 
/*     */     
/* 684 */     this.elementCount = 0;
/* 685 */     this.first = Element.NULL;
/* 686 */     this.last = Element.NULL;
/* 687 */     if (this.fileLength > 4096L) setLength(4096L); 
/* 688 */     this.fileLength = 4096L;
/* 689 */     this.modCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAtFullCapacity() {
/* 699 */     if (this.maxElements == -1)
/*     */     {
/* 701 */       return false;
/*     */     }
/* 703 */     return (size() == this.maxElements);
/*     */   }
/*     */ 
/*     */   
/*     */   public File file() {
/* 708 */     return this.file;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 713 */     this.closed = true;
/* 714 */     this.raf.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 719 */     return "QueueFile{file=" + this.file + ", zero=" + this.zero + ", length=" + this.fileLength + ", size=" + this.elementCount + ", first=" + this.first + ", last=" + this.last + '}';
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
/*     */   static final class Element
/*     */   {
/* 737 */     static final Element NULL = new Element(0L, 0);
/*     */ 
/*     */ 
/*     */     
/*     */     static final int HEADER_LENGTH = 4;
/*     */ 
/*     */ 
/*     */     
/*     */     final long position;
/*     */ 
/*     */ 
/*     */     
/*     */     final int length;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Element(long position, int length) {
/* 755 */       this.position = position;
/* 756 */       this.length = length;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 761 */       return getClass().getSimpleName() + "[position=" + this.position + ", length=" + this.length + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     final File file;
/*     */     boolean zero = true;
/* 769 */     int size = -1;
/*     */ 
/*     */     
/*     */     public Builder(File file) {
/* 773 */       if (file == null) {
/* 774 */         throw new NullPointerException("file == null");
/*     */       }
/* 776 */       this.file = file;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder zero(boolean zero) {
/* 781 */       this.zero = zero;
/* 782 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder size(int size) {
/* 787 */       this.size = size;
/* 788 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public QueueFile build() throws IOException {
/* 796 */       RandomAccessFile raf = QueueFile.initializeFromFile(this.file);
/* 797 */       QueueFile qf = null;
/*     */       try {
/* 799 */         qf = new QueueFile(this.file, raf, this.zero, this.size);
/* 800 */         return qf;
/*     */       } finally {
/* 802 */         if (qf == null) {
/* 803 */           raf.close();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends Throwable> T getSneakyThrowable(Throwable t) throws T {
/* 815 */     throw (T)t;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\tape\QueueFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */