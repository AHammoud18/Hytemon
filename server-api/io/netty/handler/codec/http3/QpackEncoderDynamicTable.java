/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.internal.MathUtil;
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
/*     */ final class QpackEncoderDynamicTable
/*     */ {
/*  33 */   private static final QpackException INVALID_KNOW_RECEIVED_COUNT_INCREMENT = QpackException.newStatic(QpackDecoder.class, "incrementKnownReceivedCount(...)", "QPACK - invalid known received count increment.");
/*     */ 
/*     */   
/*  36 */   private static final QpackException INVALID_REQUIRED_INSERT_COUNT_INCREMENT = QpackException.newStatic(QpackDecoder.class, "acknowledgeInsertCount(...)", "QPACK - invalid required insert count acknowledgment.");
/*     */ 
/*     */   
/*  39 */   private static final QpackException INVALID_TABLE_CAPACITY = QpackException.newStatic(QpackDecoder.class, "validateCapacity(...)", "QPACK - dynamic table capacity is invalid.");
/*     */ 
/*     */   
/*  42 */   private static final QpackException CAPACITY_ALREADY_SET = QpackException.newStatic(QpackDecoder.class, "maxTableCapacity(...)", "QPACK - dynamic table capacity is already set.");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int NOT_FOUND = -2147483648;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final HeaderEntry[] fields;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int expectedFreeCapacityPercentage;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte hashMask;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private long maxTableCapacity = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final HeaderEntry head;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HeaderEntry drain;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HeaderEntry knownReceived;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HeaderEntry tail;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   QpackEncoderDynamicTable() {
/* 116 */     this(16, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   QpackEncoderDynamicTable(int arraySizeHint, int expectedFreeCapacityPercentage) {
/* 122 */     this.fields = new HeaderEntry[MathUtil.findNextPositivePowerOfTwo(Math.max(2, Math.min(arraySizeHint, 128)))];
/* 123 */     this.hashMask = (byte)(this.fields.length - 1);
/*     */ 
/*     */     
/* 126 */     this.head = new HeaderEntry(-1, (CharSequence)AsciiString.EMPTY_STRING, (CharSequence)AsciiString.EMPTY_STRING, -1, null);
/* 127 */     this.expectedFreeCapacityPercentage = expectedFreeCapacityPercentage;
/* 128 */     resetIndicesToHead();
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
/*     */   int add(CharSequence name, CharSequence value, long headerSize) {
/* 140 */     if (this.maxTableCapacity - this.size < headerSize) {
/* 141 */       return -1;
/*     */     }
/*     */     
/* 144 */     if (this.tail.index == Integer.MAX_VALUE) {
/*     */       
/* 146 */       evictUnreferencedEntries();
/* 147 */       return -1;
/*     */     } 
/* 149 */     int h = AsciiString.hashCode(name);
/* 150 */     int i = index(h);
/* 151 */     HeaderEntry old = this.fields[i];
/* 152 */     HeaderEntry e = new HeaderEntry(h, name, value, this.tail.index + 1, old);
/* 153 */     this.fields[i] = e;
/* 154 */     e.addNextTo(this.tail);
/* 155 */     this.tail = e;
/* 156 */     this.size += headerSize;
/*     */     
/* 158 */     ensureFreeCapacity();
/* 159 */     return e.index;
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
/*     */   void acknowledgeInsertCountOnAck(int entryIndex) throws QpackException {
/* 171 */     acknowledgeInsertCount(entryIndex, true);
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
/*     */   void acknowledgeInsertCountOnCancellation(int entryIndex) throws QpackException {
/* 183 */     acknowledgeInsertCount(entryIndex, false);
/*     */   }
/*     */   
/*     */   private void acknowledgeInsertCount(int entryIndex, boolean updateKnownReceived) throws QpackException {
/* 187 */     if (entryIndex < 0) {
/* 188 */       throw INVALID_REQUIRED_INSERT_COUNT_INCREMENT;
/*     */     }
/* 190 */     for (HeaderEntry e = this.head.next; e != null; e = e.next) {
/* 191 */       if (e.index == entryIndex) {
/* 192 */         assert e.refCount > 0;
/* 193 */         e.refCount--;
/* 194 */         if (updateKnownReceived && e.index > this.knownReceived.index)
/*     */         {
/*     */ 
/*     */           
/* 198 */           this.knownReceived = e;
/*     */         }
/* 200 */         evictUnreferencedEntries();
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     throw INVALID_REQUIRED_INSERT_COUNT_INCREMENT;
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
/*     */   void incrementKnownReceivedCount(int knownReceivedCountIncr) throws QpackException {
/* 219 */     if (knownReceivedCountIncr <= 0) {
/* 220 */       throw INVALID_KNOW_RECEIVED_COUNT_INCREMENT;
/*     */     }
/* 222 */     while (this.knownReceived.next != null && knownReceivedCountIncr > 0) {
/* 223 */       this.knownReceived = this.knownReceived.next;
/* 224 */       knownReceivedCountIncr--;
/*     */     } 
/* 226 */     if (knownReceivedCountIncr == 0) {
/* 227 */       evictUnreferencedEntries();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 233 */     throw INVALID_KNOW_RECEIVED_COUNT_INCREMENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int insertCount() {
/* 242 */     return this.tail.index + 1;
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
/*     */   int encodedRequiredInsertCount(int reqInsertCount) {
/* 258 */     return (reqInsertCount == 0) ? 0 : (reqInsertCount % Math.toIntExact(2L * QpackUtil.maxEntries(this.maxTableCapacity)) + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int encodedKnownReceivedCount() {
/* 264 */     return encodedRequiredInsertCount(this.knownReceived.index + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void maxTableCapacity(long capacity) throws QpackException {
/* 273 */     validateCapacity(capacity);
/* 274 */     if (this.maxTableCapacity >= 0L) {
/* 275 */       throw CAPACITY_ALREADY_SET;
/*     */     }
/* 277 */     this.maxTableCapacity = capacity;
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
/*     */   int relativeIndexForEncoderInstructions(int entryIndex) {
/* 289 */     assert entryIndex >= 0;
/* 290 */     assert entryIndex <= this.tail.index;
/* 291 */     return this.tail.index - entryIndex;
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
/*     */   int getEntryIndex(@Nullable CharSequence name, @Nullable CharSequence value) {
/* 304 */     if (this.tail != this.head && name != null && value != null) {
/* 305 */       int h = AsciiString.hashCode(name);
/* 306 */       int i = index(h);
/* 307 */       HeaderEntry firstNameMatch = null;
/* 308 */       HeaderEntry entry = null;
/* 309 */       for (HeaderEntry e = this.fields[i]; e != null; e = e.nextSibling) {
/* 310 */         if (e.hash == h && QpackUtil.equalsVariableTime(value, e.value)) {
/* 311 */           if (QpackUtil.equalsVariableTime(name, e.name)) {
/* 312 */             entry = e;
/*     */             break;
/*     */           } 
/* 315 */         } else if (firstNameMatch == null && QpackUtil.equalsVariableTime(name, e.name)) {
/* 316 */           firstNameMatch = e;
/*     */         } 
/*     */       } 
/* 319 */       if (entry != null) {
/* 320 */         return entry.index;
/*     */       }
/* 322 */       if (firstNameMatch != null) {
/* 323 */         return -firstNameMatch.index - 1;
/*     */       }
/*     */     } 
/* 326 */     return Integer.MIN_VALUE;
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
/*     */   int addReferenceToEntry(@Nullable CharSequence name, @Nullable CharSequence value, int idx) {
/* 339 */     if (this.tail != this.head && name != null && value != null) {
/* 340 */       int h = AsciiString.hashCode(name);
/* 341 */       int i = index(h);
/* 342 */       for (HeaderEntry e = this.fields[i]; e != null; e = e.nextSibling) {
/* 343 */         if (e.hash == h && idx == e.index) {
/* 344 */           e.refCount++;
/* 345 */           return e.index + 1;
/*     */         } 
/*     */       } 
/*     */     } 
/* 349 */     throw new IllegalArgumentException("Index " + idx + " not found");
/*     */   }
/*     */   
/*     */   boolean requiresDuplication(int idx, long size) {
/* 353 */     assert this.head != this.tail;
/*     */     
/* 355 */     if (this.size + size > this.maxTableCapacity || this.head == this.drain) {
/* 356 */       return false;
/*     */     }
/* 358 */     return (idx >= this.head.next.index && idx <= this.drain.index);
/*     */   }
/*     */   
/*     */   private void evictUnreferencedEntries() {
/* 362 */     if (this.head == this.knownReceived || this.head == this.drain) {
/*     */       return;
/*     */     }
/*     */     
/* 366 */     while (this.head.next != null && this.head.next != this.knownReceived.next && this.head.next != this.drain.next) {
/* 367 */       if (!removeIfUnreferenced()) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeIfUnreferenced() {
/* 374 */     HeaderEntry toRemove = this.head.next;
/* 375 */     if (toRemove.refCount != 0) {
/* 376 */       return false;
/*     */     }
/* 378 */     this.size -= toRemove.size();
/*     */ 
/*     */     
/* 381 */     int i = index(toRemove.hash);
/* 382 */     HeaderEntry e = this.fields[i];
/* 383 */     HeaderEntry prev = null;
/* 384 */     while (e != null && e != toRemove) {
/* 385 */       prev = e;
/* 386 */       e = e.nextSibling;
/*     */     } 
/* 388 */     if (e == toRemove) {
/* 389 */       if (prev == null) {
/* 390 */         this.fields[i] = e.nextSibling;
/*     */       } else {
/* 392 */         prev.nextSibling = e.nextSibling;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 397 */     toRemove.remove(this.head);
/* 398 */     if (toRemove == this.tail) {
/* 399 */       resetIndicesToHead();
/*     */     }
/* 401 */     if (toRemove == this.drain) {
/* 402 */       this.drain = this.head;
/*     */     }
/* 404 */     if (toRemove == this.knownReceived) {
/* 405 */       this.knownReceived = this.head;
/*     */     }
/* 407 */     return true;
/*     */   }
/*     */   
/*     */   private void resetIndicesToHead() {
/* 411 */     this.tail = this.head;
/* 412 */     this.drain = this.head;
/* 413 */     this.knownReceived = this.head;
/*     */   }
/*     */   
/*     */   private void ensureFreeCapacity() {
/* 417 */     long maxDesiredSize = Math.max(32L, (100 - this.expectedFreeCapacityPercentage) * this.maxTableCapacity / 100L);
/* 418 */     long cSize = this.size;
/*     */     HeaderEntry nDrain;
/* 420 */     for (nDrain = this.head; nDrain.next != null && cSize > maxDesiredSize; nDrain = nDrain.next) {
/* 421 */       cSize -= nDrain.next.size();
/*     */     }
/* 423 */     if (cSize != this.size) {
/* 424 */       this.drain = nDrain;
/* 425 */       evictUnreferencedEntries();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int index(int h) {
/* 430 */     return h & this.hashMask;
/*     */   }
/*     */   
/*     */   private static void validateCapacity(long capacity) throws QpackException {
/* 434 */     if (capacity < 0L || capacity > 4294967295L) {
/* 435 */       throw INVALID_TABLE_CAPACITY;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class HeaderEntry
/*     */     extends QpackHeaderField
/*     */   {
/*     */     HeaderEntry next;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     HeaderEntry nextSibling;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int refCount;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final int hash;
/*     */ 
/*     */ 
/*     */     
/*     */     final int index;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     HeaderEntry(int hash, CharSequence name, CharSequence value, int index, @Nullable HeaderEntry nextSibling) {
/* 471 */       super(name, value);
/* 472 */       this.index = index;
/* 473 */       this.hash = hash;
/* 474 */       this.nextSibling = nextSibling;
/*     */     }
/*     */     
/*     */     void remove(HeaderEntry prev) {
/* 478 */       assert prev != this;
/* 479 */       prev.next = this.next;
/* 480 */       this.next = null;
/* 481 */       this.nextSibling = null;
/*     */     }
/*     */     
/*     */     void addNextTo(HeaderEntry prev) {
/* 485 */       assert prev != this;
/* 486 */       this.next = prev.next;
/* 487 */       prev.next = this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackEncoderDynamicTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */