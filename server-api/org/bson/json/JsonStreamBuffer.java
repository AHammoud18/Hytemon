/*     */ package org.bson.json;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ class JsonStreamBuffer
/*     */   implements JsonBuffer
/*     */ {
/*     */   private final Reader reader;
/*  27 */   private final List<Integer> markedPositions = new ArrayList<>();
/*     */   private final int initialBufferSize;
/*     */   private int position;
/*     */   private int lastChar;
/*     */   private boolean reuseLastChar;
/*     */   private boolean eof;
/*     */   private char[] buffer;
/*     */   private int bufferStartPos;
/*     */   private int bufferCount;
/*     */   
/*     */   JsonStreamBuffer(Reader reader) {
/*  38 */     this(reader, 16);
/*     */   }
/*     */   
/*     */   JsonStreamBuffer(Reader reader, int initialBufferSize) {
/*  42 */     this.initialBufferSize = initialBufferSize;
/*  43 */     this.reader = reader;
/*  44 */     resetBuffer();
/*     */   }
/*     */   
/*     */   public int getPosition() {
/*  48 */     return this.position;
/*     */   }
/*     */   
/*     */   public int read() {
/*  52 */     if (this.eof) {
/*  53 */       throw new JsonParseException("Trying to read past EOF.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  58 */     if (this.reuseLastChar) {
/*  59 */       this.reuseLastChar = false;
/*  60 */       int reusedChar = this.lastChar;
/*  61 */       this.lastChar = -1;
/*  62 */       this.position++;
/*  63 */       return reusedChar;
/*     */     } 
/*     */ 
/*     */     
/*  67 */     if (this.position - this.bufferStartPos < this.bufferCount) {
/*  68 */       int currChar = this.buffer[this.position - this.bufferStartPos];
/*  69 */       this.lastChar = currChar;
/*  70 */       this.position++;
/*  71 */       return currChar;
/*     */     } 
/*     */     
/*  74 */     if (this.markedPositions.isEmpty()) {
/*  75 */       resetBuffer();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  80 */       int nextChar = this.reader.read();
/*  81 */       if (nextChar != -1) {
/*  82 */         this.lastChar = nextChar;
/*  83 */         addToBuffer((char)nextChar);
/*     */       } 
/*  85 */       this.position++;
/*  86 */       if (nextChar == -1) {
/*  87 */         this.eof = true;
/*     */       }
/*  89 */       return nextChar;
/*     */     }
/*  91 */     catch (IOException e) {
/*  92 */       throw new JsonParseException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetBuffer() {
/*  97 */     this.bufferStartPos = -1;
/*  98 */     this.bufferCount = 0;
/*  99 */     this.buffer = new char[this.initialBufferSize];
/*     */   }
/*     */   
/*     */   public void unread(int c) {
/* 103 */     this.eof = false;
/* 104 */     if (c != -1 && this.lastChar == c) {
/* 105 */       this.reuseLastChar = true;
/* 106 */       this.position--;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int mark() {
/* 111 */     if (this.bufferCount == 0) {
/* 112 */       this.bufferStartPos = this.position;
/*     */     }
/* 114 */     if (!this.markedPositions.contains(Integer.valueOf(this.position))) {
/* 115 */       this.markedPositions.add(Integer.valueOf(this.position));
/*     */     }
/* 117 */     return this.position;
/*     */   }
/*     */   
/*     */   public void reset(int markPos) {
/* 121 */     if (markPos > this.position) {
/* 122 */       throw new IllegalStateException("mark cannot reset ahead of position, only back");
/*     */     }
/* 124 */     int idx = this.markedPositions.indexOf(Integer.valueOf(markPos));
/* 125 */     if (idx == -1) {
/* 126 */       throw new IllegalArgumentException("mark invalidated");
/*     */     }
/* 128 */     if (markPos != this.position) {
/* 129 */       this.reuseLastChar = false;
/*     */     }
/* 131 */     this.markedPositions.subList(idx, this.markedPositions.size()).clear();
/* 132 */     this.position = markPos;
/*     */   }
/*     */   
/*     */   public void discard(int markPos) {
/* 136 */     int idx = this.markedPositions.indexOf(Integer.valueOf(markPos));
/* 137 */     if (idx == -1) {
/*     */       return;
/*     */     }
/* 140 */     this.markedPositions.subList(idx, this.markedPositions.size()).clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToBuffer(char curChar) {
/* 145 */     if (!this.markedPositions.isEmpty()) {
/* 146 */       if (this.bufferCount == this.buffer.length) {
/* 147 */         char[] newBuffer = new char[this.buffer.length * 2];
/* 148 */         System.arraycopy(this.buffer, 0, newBuffer, 0, this.bufferCount);
/* 149 */         this.buffer = newBuffer;
/*     */       } 
/* 151 */       this.buffer[this.bufferCount] = curChar;
/* 152 */       this.bufferCount++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonStreamBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */