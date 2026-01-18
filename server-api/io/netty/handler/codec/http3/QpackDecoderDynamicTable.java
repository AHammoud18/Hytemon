/*     */ package io.netty.handler.codec.http3;
/*     */ 
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
/*     */ final class QpackDecoderDynamicTable
/*     */ {
/*  29 */   private static final QpackException GET_ENTRY_ILLEGAL_INDEX_VALUE = QpackException.newStatic(QpackDecoderDynamicTable.class, "getEntry(...)", "QPACK - illegal decoder dynamic table index value");
/*     */ 
/*     */   
/*  32 */   private static final QpackException HEADER_TOO_LARGE = QpackException.newStatic(QpackDecoderDynamicTable.class, "add(...)", "QPACK - header entry too large.");
/*     */   
/*     */   private QpackHeaderField[] fields;
/*     */   
/*     */   private int head;
/*     */   private int tail;
/*     */   private long size;
/*  39 */   private long capacity = -1L;
/*     */   private int insertCount;
/*     */   
/*     */   int length() {
/*  43 */     return (this.head < this.tail) ? (this.fields.length - this.tail + this.head) : (this.head - this.tail);
/*     */   }
/*     */   
/*     */   long size() {
/*  47 */     return this.size;
/*     */   }
/*     */   
/*     */   int insertCount() {
/*  51 */     return this.insertCount;
/*     */   }
/*     */   
/*     */   QpackHeaderField getEntry(int index) throws QpackException {
/*  55 */     if (index < 0 || this.fields == null || index >= this.fields.length) {
/*  56 */       throw GET_ENTRY_ILLEGAL_INDEX_VALUE;
/*     */     }
/*  58 */     QpackHeaderField entry = this.fields[index];
/*  59 */     if (entry == null) {
/*  60 */       throw GET_ENTRY_ILLEGAL_INDEX_VALUE;
/*     */     }
/*  62 */     return entry;
/*     */   }
/*     */ 
/*     */   
/*     */   QpackHeaderField getEntryRelativeEncodedField(int index) throws QpackException {
/*  67 */     return getEntry(moduloIndex(index));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   QpackHeaderField getEntryRelativeEncoderInstructions(int index) throws QpackException {
/*  73 */     return getEntry((index > this.tail) ? (this.fields.length - index + this.tail) : (this.tail - index));
/*     */   }
/*     */   
/*     */   void add(QpackHeaderField header) throws QpackException {
/*  77 */     long headerSize = header.size();
/*  78 */     if (headerSize > this.capacity) {
/*  79 */       throw HEADER_TOO_LARGE;
/*     */     }
/*  81 */     while (this.capacity - this.size < headerSize) {
/*  82 */       remove();
/*     */     }
/*  84 */     this.insertCount++;
/*  85 */     this.fields[getAndIncrementHead()] = header;
/*  86 */     this.size += headerSize;
/*     */   }
/*     */   
/*     */   private void remove() {
/*  90 */     QpackHeaderField removed = this.fields[this.tail];
/*  91 */     if (removed == null) {
/*     */       return;
/*     */     }
/*  94 */     this.size -= removed.size();
/*  95 */     this.fields[getAndIncrementTail()] = null;
/*     */   }
/*     */   
/*     */   void clear() {
/*  99 */     if (this.fields != null) {
/* 100 */       Arrays.fill((Object[])this.fields, (Object)null);
/*     */     }
/* 102 */     this.head = 0;
/* 103 */     this.tail = 0;
/* 104 */     this.size = 0L;
/*     */   }
/*     */   
/*     */   void setCapacity(long capacity) throws QpackException {
/* 108 */     if (capacity < 0L || capacity > 4294967295L) {
/* 109 */       throw new IllegalArgumentException("capacity is invalid: " + capacity);
/*     */     }
/*     */     
/* 112 */     if (this.capacity == capacity) {
/*     */       return;
/*     */     }
/* 115 */     this.capacity = capacity;
/*     */     
/* 117 */     if (capacity == 0L) {
/* 118 */       clear();
/*     */     } else {
/*     */       
/* 121 */       while (this.size > capacity) {
/* 122 */         remove();
/*     */       }
/*     */     } 
/*     */     
/* 126 */     int maxEntries = QpackUtil.toIntOrThrow(2L * Math.floorDiv(capacity, 32L));
/*     */ 
/*     */     
/* 129 */     if (this.fields != null && this.fields.length == maxEntries) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     QpackHeaderField[] tmp = new QpackHeaderField[maxEntries];
/*     */ 
/*     */     
/* 136 */     int len = length();
/* 137 */     if (this.fields != null && this.tail != this.head) {
/* 138 */       if (this.head > this.tail) {
/* 139 */         System.arraycopy(this.fields, this.tail, tmp, 0, this.head - this.tail);
/*     */       } else {
/* 141 */         System.arraycopy(this.fields, 0, tmp, 0, this.head);
/* 142 */         System.arraycopy(this.fields, this.tail, tmp, this.head, this.fields.length - this.tail);
/*     */       } 
/*     */     }
/*     */     
/* 146 */     this.tail = 0;
/* 147 */     this.head = this.tail + len;
/* 148 */     this.fields = tmp;
/*     */   }
/*     */   
/*     */   private int getAndIncrementHead() {
/* 152 */     int val = this.head;
/* 153 */     this.head = safeIncrementIndex(val);
/* 154 */     return val;
/*     */   }
/*     */   
/*     */   private int getAndIncrementTail() {
/* 158 */     int val = this.tail;
/* 159 */     this.tail = safeIncrementIndex(val);
/* 160 */     return val;
/*     */   }
/*     */   
/*     */   private int safeIncrementIndex(int index) {
/* 164 */     return ++index % this.fields.length;
/*     */   }
/*     */   
/*     */   private int moduloIndex(int index) {
/* 168 */     return (this.fields == null) ? index : (index % this.fields.length);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackDecoderDynamicTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */