/*     */ package org.jline.reader.impl;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import org.jline.reader.Buffer;
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
/*     */ public class BufferImpl
/*     */   implements Buffer
/*     */ {
/*  40 */   private int cursor = 0;
/*  41 */   private int cursorCol = -1;
/*     */   
/*     */   private int[] buffer;
/*     */   
/*     */   private int g0;
/*     */   
/*     */   private int g1;
/*     */   
/*     */   public BufferImpl() {
/*  50 */     this(64);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferImpl(int size) {
/*  59 */     this.buffer = new int[size];
/*  60 */     this.g0 = 0;
/*  61 */     this.g1 = this.buffer.length;
/*     */   }
/*     */   
/*     */   private BufferImpl(BufferImpl buffer) {
/*  65 */     this.cursor = buffer.cursor;
/*  66 */     this.cursorCol = buffer.cursorCol;
/*  67 */     this.buffer = (int[])buffer.buffer.clone();
/*  68 */     this.g0 = buffer.g0;
/*  69 */     this.g1 = buffer.g1;
/*     */   }
/*     */   
/*     */   public BufferImpl copy() {
/*  73 */     return new BufferImpl(this);
/*     */   }
/*     */   
/*     */   public int cursor() {
/*  77 */     return this.cursor;
/*     */   }
/*     */   
/*     */   public int length() {
/*  81 */     return this.buffer.length - this.g1 - this.g0;
/*     */   }
/*     */   
/*     */   public boolean currChar(int ch) {
/*  85 */     if (this.cursor == length()) {
/*  86 */       return false;
/*     */     }
/*  88 */     this.buffer[adjust(this.cursor)] = ch;
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int currChar() {
/*  94 */     if (this.cursor == length()) {
/*  95 */       return 0;
/*     */     }
/*  97 */     return atChar(this.cursor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int prevChar() {
/* 102 */     if (this.cursor <= 0) {
/* 103 */       return 0;
/*     */     }
/* 105 */     return atChar(this.cursor - 1);
/*     */   }
/*     */   
/*     */   public int nextChar() {
/* 109 */     if (this.cursor >= length() - 1) {
/* 110 */       return 0;
/*     */     }
/* 112 */     return atChar(this.cursor + 1);
/*     */   }
/*     */   
/*     */   public int atChar(int i) {
/* 116 */     if (i < 0 || i >= length()) {
/* 117 */       return 0;
/*     */     }
/* 119 */     return this.buffer[adjust(i)];
/*     */   }
/*     */   
/*     */   private int adjust(int i) {
/* 123 */     return (i >= this.g0) ? (i + this.g1 - this.g0) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int c) {
/* 133 */     write(new int[] { c });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int c, boolean overTyping) {
/* 144 */     if (overTyping) {
/* 145 */       delete(1);
/*     */     }
/* 147 */     write(new int[] { c });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(CharSequence str) {
/* 154 */     Objects.requireNonNull(str);
/* 155 */     write(str.codePoints().toArray());
/*     */   }
/*     */   
/*     */   public void write(CharSequence str, boolean overTyping) {
/* 159 */     Objects.requireNonNull(str);
/* 160 */     int[] ucps = str.codePoints().toArray();
/* 161 */     if (overTyping) {
/* 162 */       delete(ucps.length);
/*     */     }
/* 164 */     write(ucps);
/*     */   }
/*     */   
/*     */   private void write(int[] ucps) {
/* 168 */     moveGapToCursor();
/* 169 */     int len = length() + ucps.length;
/* 170 */     int sz = this.buffer.length;
/* 171 */     if (sz < len) {
/* 172 */       while (sz < len) {
/* 173 */         sz *= 2;
/*     */       }
/* 175 */       int[] nb = new int[sz];
/* 176 */       System.arraycopy(this.buffer, 0, nb, 0, this.g0);
/* 177 */       System.arraycopy(this.buffer, this.g1, nb, this.g1 + sz - this.buffer.length, this.buffer.length - this.g1);
/* 178 */       this.g1 += sz - this.buffer.length;
/* 179 */       this.buffer = nb;
/*     */     } 
/* 181 */     System.arraycopy(ucps, 0, this.buffer, this.cursor, ucps.length);
/* 182 */     this.g0 += ucps.length;
/* 183 */     this.cursor += ucps.length;
/* 184 */     this.cursorCol = -1;
/*     */   }
/*     */   
/*     */   public boolean clear() {
/* 188 */     if (length() == 0) {
/* 189 */       return false;
/*     */     }
/* 191 */     this.g0 = 0;
/* 192 */     this.g1 = this.buffer.length;
/* 193 */     this.cursor = 0;
/* 194 */     this.cursorCol = -1;
/* 195 */     return true;
/*     */   }
/*     */   
/*     */   public String substring(int start) {
/* 199 */     return substring(start, length());
/*     */   }
/*     */   
/*     */   public String substring(int start, int end) {
/* 203 */     if (start >= end || start < 0 || end > length()) {
/* 204 */       return "";
/*     */     }
/* 206 */     if (end <= this.g0)
/* 207 */       return new String(this.buffer, start, end - start); 
/* 208 */     if (start > this.g0) {
/* 209 */       return new String(this.buffer, this.g1 - this.g0 + start, end - start);
/*     */     }
/* 211 */     int[] b = (int[])this.buffer.clone();
/* 212 */     System.arraycopy(b, this.g1, b, this.g0, b.length - this.g1);
/* 213 */     return new String(b, start, end - start);
/*     */   }
/*     */ 
/*     */   
/*     */   public String upToCursor() {
/* 218 */     return substring(0, this.cursor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean cursor(int position) {
/* 225 */     if (position == this.cursor) {
/* 226 */       return true;
/*     */     }
/* 228 */     return (move(position - this.cursor) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int move(int num) {
/* 238 */     int where = num;
/*     */     
/* 240 */     if (this.cursor == 0 && where <= 0) {
/* 241 */       return 0;
/*     */     }
/*     */     
/* 244 */     if (this.cursor == length() && where >= 0) {
/* 245 */       return 0;
/*     */     }
/*     */     
/* 248 */     if (this.cursor + where < 0) {
/* 249 */       where = -this.cursor;
/* 250 */     } else if (this.cursor + where > length()) {
/* 251 */       where = length() - this.cursor;
/*     */     } 
/*     */     
/* 254 */     this.cursor += where;
/* 255 */     this.cursorCol = -1;
/*     */     
/* 257 */     return where;
/*     */   }
/*     */   
/*     */   public boolean up() {
/* 261 */     int col = getCursorCol();
/* 262 */     int pnl = this.cursor - 1;
/* 263 */     while (pnl >= 0 && atChar(pnl) != 10) {
/* 264 */       pnl--;
/*     */     }
/* 266 */     if (pnl < 0) {
/* 267 */       return false;
/*     */     }
/* 269 */     int ppnl = pnl - 1;
/* 270 */     while (ppnl >= 0 && atChar(ppnl) != 10) {
/* 271 */       ppnl--;
/*     */     }
/* 273 */     this.cursor = Math.min(ppnl + col + 1, pnl);
/* 274 */     return true;
/*     */   }
/*     */   
/*     */   public boolean down() {
/* 278 */     int col = getCursorCol();
/* 279 */     int nnl = this.cursor;
/* 280 */     while (nnl < length() && atChar(nnl) != 10) {
/* 281 */       nnl++;
/*     */     }
/* 283 */     if (nnl >= length()) {
/* 284 */       return false;
/*     */     }
/* 286 */     int nnnl = nnl + 1;
/* 287 */     while (nnnl < length() && atChar(nnnl) != 10) {
/* 288 */       nnnl++;
/*     */     }
/* 290 */     this.cursor = Math.min(nnl + col + 1, nnnl);
/* 291 */     return true;
/*     */   }
/*     */   
/*     */   public boolean moveXY(int dx, int dy) {
/* 295 */     int col = 0;
/* 296 */     while (prevChar() != 10 && move(-1) == -1) {
/* 297 */       col++;
/*     */     }
/* 299 */     this.cursorCol = 0;
/* 300 */     while (dy < 0) {
/* 301 */       up();
/* 302 */       dy++;
/*     */     } 
/* 304 */     while (dy > 0) {
/* 305 */       down();
/* 306 */       dy--;
/*     */     } 
/* 308 */     col = Math.max(col + dx, 0);
/* 309 */     for (int i = 0; i < col && 
/* 310 */       move(1) == 1 && currChar() != 10; i++);
/*     */ 
/*     */ 
/*     */     
/* 314 */     this.cursorCol = col;
/* 315 */     return true;
/*     */   }
/*     */   
/*     */   private int getCursorCol() {
/* 319 */     if (this.cursorCol < 0) {
/* 320 */       this.cursorCol = 0;
/* 321 */       int pnl = this.cursor - 1;
/* 322 */       while (pnl >= 0 && atChar(pnl) != 10) {
/* 323 */         pnl--;
/*     */       }
/* 325 */       this.cursorCol = this.cursor - pnl - 1;
/*     */     } 
/* 327 */     return this.cursorCol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int backspace(int num) {
/* 336 */     int count = Math.max(Math.min(this.cursor, num), 0);
/* 337 */     moveGapToCursor();
/* 338 */     this.cursor -= count;
/* 339 */     this.g0 -= count;
/* 340 */     this.cursorCol = -1;
/* 341 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean backspace() {
/* 350 */     return (backspace(1) == 1);
/*     */   }
/*     */   
/*     */   public int delete(int num) {
/* 354 */     int count = Math.max(Math.min(length() - this.cursor, num), 0);
/* 355 */     moveGapToCursor();
/* 356 */     this.g1 += count;
/* 357 */     this.cursorCol = -1;
/* 358 */     return count;
/*     */   }
/*     */   
/*     */   public boolean delete() {
/* 362 */     return (delete(1) == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 367 */     return substring(0, length());
/*     */   }
/*     */   
/*     */   public void copyFrom(Buffer buf) {
/* 371 */     if (!(buf instanceof BufferImpl)) {
/* 372 */       throw new IllegalStateException();
/*     */     }
/* 374 */     BufferImpl that = (BufferImpl)buf;
/* 375 */     this.g0 = that.g0;
/* 376 */     this.g1 = that.g1;
/* 377 */     this.buffer = (int[])that.buffer.clone();
/* 378 */     this.cursor = that.cursor;
/* 379 */     this.cursorCol = that.cursorCol;
/*     */   }
/*     */   
/*     */   private void moveGapToCursor() {
/* 383 */     if (this.cursor < this.g0) {
/* 384 */       int l = this.g0 - this.cursor;
/* 385 */       System.arraycopy(this.buffer, this.cursor, this.buffer, this.g1 - l, l);
/* 386 */       this.g0 -= l;
/* 387 */       this.g1 -= l;
/* 388 */     } else if (this.cursor > this.g0) {
/* 389 */       int l = this.cursor - this.g0;
/* 390 */       System.arraycopy(this.buffer, this.g1, this.buffer, this.g0, l);
/* 391 */       this.g0 += l;
/* 392 */       this.g1 += l;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void zeroOut() {
/* 398 */     Arrays.fill(this.buffer, 0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\BufferImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */