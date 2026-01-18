/*      */ package com.hypixel.hytale.codec.util;
/*      */ import ch.randelshofer.fastdoubleparser.JavaDoubleParser;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.ExtraInfo;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.unsafe.UnsafeUtil;
/*      */ import io.sentry.Sentry;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.util.Arrays;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ import org.bson.BsonArray;
/*      */ import org.bson.BsonBoolean;
/*      */ import org.bson.BsonDocument;
/*      */ import org.bson.BsonDouble;
/*      */ import org.bson.BsonValue;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ public class RawJsonReader implements AutoCloseable {
/*   26 */   public static final ThreadLocal<char[]> READ_BUFFER = (ThreadLocal)ThreadLocal.withInitial(() -> new char[131072]);
/*      */   
/*      */   public static final int DEFAULT_CHAR_BUFFER_SIZE = 32768;
/*      */   
/*      */   public static final int MIN_CHAR_BUFFER_READ = 16384;
/*      */   
/*      */   public static final int BUFFER_GROWTH = 1048576;
/*      */   
/*      */   private static final int UNMARKED = -1;
/*      */   
/*      */   private int streamIndex;
/*      */   
/*      */   @Nullable
/*      */   private Reader in;
/*      */   @Nullable
/*      */   private char[] buffer;
/*      */   private int bufferIndex;
/*      */   private int bufferSize;
/*   44 */   private int markIndex = -1;
/*   45 */   private int markLine = -1;
/*   46 */   private int markLineStart = -1;
/*      */   
/*      */   private StringBuilder tempSb;
/*      */   
/*      */   private int line;
/*      */   private int lineStart;
/*      */   public static final int ERROR_LINES_BUFFER = 10;
/*      */   
/*      */   public RawJsonReader(@Nonnull char[] preFilledBuffer) {
/*   55 */     if (preFilledBuffer == null) throw new IllegalArgumentException("buffer can't be null!"); 
/*   56 */     this.in = null;
/*   57 */     this.buffer = preFilledBuffer;
/*   58 */     this.bufferIndex = 0;
/*   59 */     this.streamIndex = 0;
/*   60 */     this.bufferSize = preFilledBuffer.length;
/*      */   }
/*      */   
/*      */   public RawJsonReader(Reader in, @Nonnull char[] buffer) {
/*   64 */     if (buffer == null) throw new IllegalArgumentException("buffer can't be null!"); 
/*   65 */     this.in = in;
/*   66 */     this.buffer = buffer;
/*   67 */     this.bufferIndex = 0;
/*   68 */     this.streamIndex = 0;
/*   69 */     this.bufferSize = 0;
/*      */   }
/*      */   
/*      */   public char[] getBuffer() {
/*   73 */     return this.buffer;
/*      */   }
/*      */   
/*      */   public int getBufferIndex() {
/*   77 */     return this.bufferIndex;
/*      */   }
/*      */   
/*      */   public int getBufferSize() {
/*   81 */     return this.bufferSize;
/*      */   }
/*      */   
/*      */   public int getLine() {
/*   85 */     return this.line + 1;
/*      */   }
/*      */   
/*      */   public int getColumn() {
/*   89 */     return this.bufferIndex - this.lineStart + 1;
/*      */   }
/*      */   
/*      */   private boolean ensure() throws IOException {
/*   93 */     return ensure(1);
/*      */   }
/*      */   
/*      */   private boolean ensure(int n) throws IOException {
/*   97 */     boolean filled = false;
/*   98 */     while (this.bufferIndex + n > this.bufferSize) {
/*   99 */       if (!fill()) throw unexpectedEOF(); 
/*  100 */       filled = true;
/*      */     } 
/*  102 */     return filled;
/*      */   }
/*      */   
/*      */   private boolean fill() throws IOException {
/*      */     int dst, len;
/*  107 */     if (this.markIndex <= -1) {
/*  108 */       this.streamIndex += this.bufferIndex;
/*  109 */       dst = 0;
/*  110 */       len = this.buffer.length;
/*      */     } else {
/*  112 */       int spaceInBuffer = this.buffer.length - this.bufferIndex;
/*  113 */       if (spaceInBuffer > 16384) {
/*  114 */         dst = this.bufferIndex;
/*  115 */         len = spaceInBuffer;
/*      */       } else {
/*  117 */         int delta = this.bufferIndex - this.markIndex;
/*  118 */         if (this.markIndex > 16384) {
/*      */           
/*  120 */           System.arraycopy(this.buffer, this.markIndex, this.buffer, 0, delta);
/*      */         } else {
/*      */           
/*  123 */           int newSize = this.bufferIndex + 1048576;
/*  124 */           System.err.println("Reallocate: " + this.buffer.length + " to " + newSize);
/*  125 */           char[] ncb = new char[newSize];
/*  126 */           System.arraycopy(this.buffer, this.markIndex, ncb, 0, delta);
/*  127 */           this.buffer = ncb;
/*      */         } 
/*      */         
/*  130 */         this.streamIndex += this.markIndex;
/*  131 */         this.markIndex = 0;
/*  132 */         dst = delta;
/*  133 */         this.bufferIndex = this.bufferSize = delta;
/*  134 */         len = this.buffer.length - dst;
/*      */       } 
/*      */     } 
/*      */     
/*  138 */     if (this.in == null) return false;
/*      */     
/*  140 */     int n = this.in.read(this.buffer, dst, len);
/*  141 */     if (n > 0) {
/*  142 */       this.bufferSize = dst + n;
/*  143 */       this.bufferIndex = dst;
/*  144 */       return true;
/*      */     } 
/*  146 */     return false;
/*      */   }
/*      */   
/*      */   public int peek() throws IOException {
/*  150 */     return peek(0);
/*      */   }
/*      */   
/*      */   public int peek(int n) throws IOException {
/*  154 */     if (this.bufferIndex + n >= this.bufferSize) {
/*  155 */       fill();
/*  156 */       if (this.bufferIndex + n >= this.bufferSize) return -1; 
/*      */     } 
/*  158 */     return this.buffer[this.bufferIndex + n];
/*      */   }
/*      */   
/*      */   public int read() throws IOException {
/*  162 */     if (this.bufferIndex >= this.bufferSize) {
/*  163 */       fill();
/*  164 */       if (this.bufferIndex >= this.bufferSize) return -1; 
/*      */     } 
/*  166 */     char c = this.buffer[this.bufferIndex++];
/*  167 */     if (c == '\n') {
/*  168 */       this.line++;
/*  169 */       this.lineStart = this.bufferIndex;
/*      */     } 
/*  171 */     return c;
/*      */   }
/*      */ 
/*      */   
/*      */   public long skip(long skip) throws IOException {
/*  176 */     if (skip < 0L) {
/*  177 */       int negativeBufferIndex = -this.bufferIndex;
/*  178 */       if (skip < negativeBufferIndex) {
/*  179 */         this.bufferIndex = 0;
/*  180 */         return negativeBufferIndex;
/*      */       } 
/*      */       
/*  183 */       this.bufferIndex = (int)(this.bufferIndex + skip);
/*  184 */       return skip;
/*      */     } 
/*      */     
/*  187 */     long haveSkipped = 0L;
/*  188 */     while (haveSkipped < skip) {
/*  189 */       long charsInBuffer = (this.bufferSize - this.bufferIndex);
/*  190 */       long charsToSkip = skip - haveSkipped;
/*  191 */       if (charsToSkip <= charsInBuffer) {
/*  192 */         this.bufferIndex = (int)(this.bufferIndex + charsToSkip);
/*  193 */         return skip;
/*      */       } 
/*      */       
/*  196 */       haveSkipped += charsInBuffer;
/*  197 */       this.bufferIndex = this.bufferSize;
/*      */       
/*  199 */       fill();
/*  200 */       if (this.bufferIndex >= this.bufferSize)
/*      */         break; 
/*  202 */     }  return haveSkipped;
/*      */   }
/*      */   
/*      */   public int findOffset(char value) throws IOException {
/*  206 */     return findOffset(0, value);
/*      */   }
/*      */   
/*      */   public int findOffset(int start, char value) throws IOException {
/*      */     while (true) {
/*  211 */       ensure();
/*  212 */       char c = this.buffer[this.bufferIndex + start];
/*  213 */       if (c == value) return start; 
/*  214 */       start++;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void skipOrThrow(long n) throws IOException {
/*  219 */     long skipped = skip(n);
/*  220 */     if (skipped != n) throw new IOException("Failed to skip " + n + " char's!"); 
/*      */   }
/*      */   
/*      */   public boolean ready() throws IOException {
/*  224 */     return ((this.buffer != null && this.bufferIndex < this.bufferSize) || this.in.ready());
/*      */   }
/*      */   
/*      */   public boolean markSupported() {
/*  228 */     return true;
/*      */   }
/*      */   
/*      */   public void mark(int readAheadLimit) throws IOException {
/*  232 */     mark();
/*      */   }
/*      */   
/*      */   public boolean isMarked() {
/*  236 */     return (this.markIndex >= 0);
/*      */   }
/*      */   
/*      */   public void mark() throws IOException {
/*  240 */     if (this.markIndex >= 0) throw new IOException("mark can't be used while already marked!"); 
/*  241 */     this.markIndex = this.bufferIndex;
/*  242 */     this.markLine = this.line;
/*  243 */     this.markLineStart = this.lineStart;
/*      */   }
/*      */   
/*      */   public void unmark() {
/*  247 */     this.markIndex = -1;
/*  248 */     this.markLine = -1;
/*  249 */     this.markLineStart = -1;
/*      */   }
/*      */   
/*      */   public int getMarkDistance() {
/*  253 */     return this.bufferIndex - this.markIndex;
/*      */   }
/*      */   
/*      */   public char[] cloneMark() {
/*  257 */     return Arrays.copyOfRange(this.buffer, this.markIndex, this.bufferIndex);
/*      */   }
/*      */   
/*      */   public void reset() throws IOException {
/*  261 */     if (this.markIndex < 0) throw new IOException("Stream not marked"); 
/*  262 */     this.bufferIndex = this.markIndex;
/*  263 */     this.markIndex = -1;
/*  264 */     this.line = this.markLine;
/*  265 */     this.lineStart = this.markLineStart;
/*  266 */     this.markLine = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/*  271 */     if (this.buffer == null)
/*      */       return;  try {
/*  273 */       if (this.in != null) this.in.close(); 
/*      */     } finally {
/*  275 */       this.in = null;
/*  276 */       this.buffer = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public char[] closeAndTakeBuffer() throws IOException {
/*  281 */     char[] buffer = this.buffer;
/*  282 */     close();
/*  283 */     return buffer;
/*      */   }
/*      */   
/*      */   public boolean peekFor(char consume) throws IOException {
/*  287 */     ensure();
/*  288 */     return (this.buffer[this.bufferIndex] == consume);
/*      */   }
/*      */   
/*      */   public boolean tryConsume(char consume) throws IOException {
/*  292 */     ensure();
/*  293 */     if (this.buffer[this.bufferIndex] == consume) {
/*  294 */       this.bufferIndex++;
/*  295 */       if (consume == '\n') {
/*  296 */         this.line++;
/*  297 */         this.lineStart = this.bufferIndex;
/*      */       } 
/*  299 */       return true;
/*      */     } 
/*  301 */     return false;
/*      */   }
/*      */   
/*      */   public boolean tryConsumeString(@Nonnull String str) throws IOException {
/*  305 */     mark();
/*  306 */     if (tryConsume('"') && tryConsume(str) && tryConsume('"')) {
/*  307 */       unmark();
/*  308 */       return true;
/*      */     } 
/*  310 */     reset();
/*  311 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean tryConsume(@Nonnull String str) throws IOException {
/*  316 */     return tryConsume(str, 0);
/*      */   }
/*      */   
/*      */   public boolean tryConsume(@Nonnull String str, int start) throws IOException {
/*  320 */     while (start < str.length()) {
/*  321 */       ensure();
/*      */       
/*  323 */       while (start < str.length() && this.bufferIndex < this.bufferSize) {
/*  324 */         char c = this.buffer[this.bufferIndex];
/*  325 */         if (c != str.charAt(start++)) return false; 
/*  326 */         this.bufferIndex++;
/*  327 */         if (c == '\n') {
/*  328 */           this.line++;
/*  329 */           this.lineStart = this.bufferIndex;
/*      */         } 
/*      */       } 
/*      */     } 
/*  333 */     return true;
/*      */   }
/*      */   
/*      */   public int tryConsumeSome(@Nonnull String str, int start) throws IOException {
/*  337 */     while (start < str.length()) {
/*  338 */       ensure();
/*      */       
/*  340 */       while (start < str.length() && this.bufferIndex < this.bufferSize) {
/*  341 */         char c = this.buffer[this.bufferIndex];
/*  342 */         if (c != str.charAt(start)) return start; 
/*  343 */         start++;
/*  344 */         this.bufferIndex++;
/*  345 */         if (c == '\n') {
/*  346 */           this.line++;
/*  347 */           this.lineStart = this.bufferIndex;
/*      */         } 
/*      */       } 
/*      */     } 
/*  351 */     return start;
/*      */   }
/*      */   
/*      */   public void expect(char expect) throws IOException {
/*  355 */     ensure();
/*  356 */     char read = this.buffer[this.bufferIndex++];
/*  357 */     if (read != expect) throw expecting(read, expect); 
/*  358 */     if (expect == '\n') {
/*  359 */       this.line++;
/*  360 */       this.lineStart = this.bufferIndex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void expect(@Nonnull String str, int start) throws IOException {
/*  365 */     ensure(str.length() - start);
/*  366 */     while (start < str.length()) {
/*  367 */       char c = this.buffer[this.bufferIndex];
/*  368 */       if (c != str.charAt(start++)) throw expecting(c, str, start); 
/*  369 */       this.bufferIndex++;
/*  370 */       if (c == '\n') {
/*  371 */         this.line++;
/*  372 */         this.lineStart = this.bufferIndex;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean tryConsumeOrExpect(char consume, char expect) throws IOException {
/*  378 */     ensure();
/*  379 */     char read = this.buffer[this.bufferIndex];
/*  380 */     if (read == consume) {
/*  381 */       this.bufferIndex++;
/*  382 */       if (consume == '\n') {
/*  383 */         this.line++;
/*  384 */         this.lineStart = this.bufferIndex;
/*      */       } 
/*  386 */       return true;
/*  387 */     }  if (read == expect) {
/*  388 */       this.bufferIndex++;
/*  389 */       if (expect == '\n') {
/*  390 */         this.line++;
/*  391 */         this.lineStart = this.bufferIndex;
/*      */       } 
/*  393 */       return false;
/*      */     } 
/*  395 */     throw expecting(read, expect);
/*      */   }
/*      */ 
/*      */   
/*      */   public void consumeWhiteSpace() throws IOException {
/*      */     while (true) {
/*  401 */       if (this.bufferIndex >= this.bufferSize) {
/*  402 */         fill();
/*  403 */         if (this.bufferIndex >= this.bufferSize)
/*      */           break; 
/*      */       } 
/*  406 */       while (this.bufferIndex < this.bufferSize) {
/*  407 */         char ch = this.buffer[this.bufferIndex];
/*  408 */         switch (ch) {
/*      */           case '\n':
/*  410 */             this.bufferIndex++;
/*  411 */             this.line++;
/*  412 */             this.lineStart = this.bufferIndex;
/*      */             continue;
/*      */           case '\t':
/*      */           case '\r':
/*      */           case ' ':
/*  417 */             this.bufferIndex++;
/*      */             continue;
/*      */         } 
/*  420 */         if (Character.isWhitespace(ch)) {
/*  421 */           this.bufferIndex++;
/*      */           continue;
/*      */         } 
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void consumeIgnoreCase(@Nonnull String str, int start) throws IOException {
/*  431 */     ensure(str.length() - start);
/*  432 */     while (start < str.length()) {
/*  433 */       char c = this.buffer[this.bufferIndex];
/*  434 */       if (!equalsIgnoreCase(c, str.charAt(start++))) throw expecting(c, str, start); 
/*  435 */       this.bufferIndex++;
/*  436 */       if (c == '\n') {
/*  437 */         this.line++;
/*  438 */         this.lineStart = this.bufferIndex;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public String readString() throws IOException {
/*  445 */     expect('"');
/*  446 */     return readRemainingString();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public String readRemainingString() throws IOException {
/*  451 */     if (this.tempSb == null) this.tempSb = new StringBuilder(1024); 
/*      */     while (true) {
/*  453 */       ensure();
/*      */       
/*  455 */       while (this.bufferIndex < this.bufferSize) {
/*  456 */         String string; int digit, hex; char read = this.buffer[this.bufferIndex++];
/*  457 */         switch (read) {
/*      */           case '"':
/*  459 */             string = this.tempSb.toString();
/*  460 */             this.tempSb.setLength(0);
/*  461 */             return string;
/*      */           case '\\':
/*  463 */             ensure();
/*  464 */             read = this.buffer[this.bufferIndex++];
/*  465 */             switch (read) {
/*      */               case '"':
/*      */               case '/':
/*      */               case '\\':
/*  469 */                 this.tempSb.append(read);
/*      */                 continue;
/*      */               case 'b':
/*  472 */                 this.tempSb.append('\b');
/*      */                 continue;
/*      */               case 'f':
/*  475 */                 this.tempSb.append('\f');
/*      */                 continue;
/*      */               case 'n':
/*  478 */                 this.tempSb.append('\n');
/*      */                 continue;
/*      */               case 'r':
/*  481 */                 this.tempSb.append('\r');
/*      */                 continue;
/*      */               case 't':
/*  484 */                 this.tempSb.append('\t');
/*      */                 continue;
/*      */               case 'u':
/*  487 */                 ensure(4);
/*      */                 
/*  489 */                 read = this.buffer[this.bufferIndex++];
/*  490 */                 digit = Character.digit(read, 16);
/*  491 */                 if (digit == -1) throw expectingWhile(read, "HEX Digit 0-F", "reading string"); 
/*  492 */                 hex = digit << 12;
/*      */                 
/*  494 */                 read = this.buffer[this.bufferIndex++];
/*  495 */                 digit = Character.digit(read, 16);
/*  496 */                 if (digit == -1) throw expectingWhile(read, "HEX Digit 0-F", "reading string"); 
/*  497 */                 hex |= digit << 8;
/*      */                 
/*  499 */                 read = this.buffer[this.bufferIndex++];
/*  500 */                 digit = Character.digit(read, 16);
/*  501 */                 if (digit == -1) throw expectingWhile(read, "HEX Digit 0-F", "reading string"); 
/*  502 */                 hex |= digit << 4;
/*      */                 
/*  504 */                 read = this.buffer[this.bufferIndex++];
/*  505 */                 digit = Character.digit(read, 16);
/*  506 */                 if (digit == -1) throw expectingWhile(read, "HEX Digit 0-F", "reading string"); 
/*  507 */                 hex |= digit;
/*      */                 
/*  509 */                 this.tempSb.appendCodePoint(hex);
/*      */                 continue;
/*      */             } 
/*  512 */             throw expecting(read, "escape char");
/*      */         } 
/*      */ 
/*      */         
/*  516 */         if (Character.isISOControl(read)) throw unexpectedChar(read); 
/*  517 */         this.tempSb.append(read);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void skipString() throws IOException {
/*  525 */     expect('"');
/*  526 */     skipRemainingString();
/*      */   }
/*      */   
/*      */   public void skipRemainingString() throws IOException {
/*      */     while (true) {
/*  531 */       ensure();
/*      */       
/*  533 */       while (this.bufferIndex < this.bufferSize) {
/*  534 */         int digit; char read = this.buffer[this.bufferIndex++];
/*  535 */         switch (read) {
/*      */           case '"':
/*      */             return;
/*      */           case '\\':
/*  539 */             ensure();
/*  540 */             read = this.buffer[this.bufferIndex++];
/*  541 */             switch (read) {
/*      */               case '"':
/*      */               case '/':
/*      */               case '\\':
/*      */               case 'b':
/*      */               case 'f':
/*      */               case 'n':
/*      */               case 'r':
/*      */               case 't':
/*      */                 continue;
/*      */               case 'u':
/*  552 */                 ensure(4);
/*      */                 
/*  554 */                 read = this.buffer[this.bufferIndex++];
/*  555 */                 digit = Character.digit(read, 16);
/*  556 */                 if (digit == -1) throw expectingWhile(read, "HEX Digit 0-F", "skipping string");
/*      */                 
/*  558 */                 read = this.buffer[this.bufferIndex++];
/*  559 */                 digit = Character.digit(read, 16);
/*  560 */                 if (digit == -1) throw expectingWhile(read, "HEX Digit 0-F", "skipping string");
/*      */                 
/*  562 */                 read = this.buffer[this.bufferIndex++];
/*  563 */                 digit = Character.digit(read, 16);
/*  564 */                 if (digit == -1) throw expectingWhile(read, "HEX Digit 0-F", "skipping string");
/*      */                 
/*  566 */                 read = this.buffer[this.bufferIndex++];
/*  567 */                 digit = Character.digit(read, 16);
/*  568 */                 if (digit == -1) throw expectingWhile(read, "HEX Digit 0-F", "skipping string"); 
/*      */                 continue;
/*      */             } 
/*  571 */             throw expecting(read, "escape char");
/*      */         } 
/*      */ 
/*      */         
/*  575 */         if (Character.isISOControl(read)) throw unexpectedChar(read);
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public long readStringPartAsLong(int count) throws IOException {
/*  582 */     assert count > 0 && count <= 4;
/*  583 */     if (UnsafeUtil.UNSAFE != null && this.bufferIndex + count <= this.bufferSize) return readStringPartAsLongUnsafe(count); 
/*  584 */     return readStringPartAsLongSlow(count);
/*      */   }
/*      */   
/*      */   protected long readStringPartAsLongSlow(int count) throws IOException {
/*  588 */     ensure(count);
/*      */     
/*  590 */     char c1 = this.buffer[this.bufferIndex++];
/*  591 */     if (count == 1) return c1;
/*      */     
/*  593 */     char c2 = this.buffer[this.bufferIndex++];
/*  594 */     long value = c1 | c2 << 16L;
/*  595 */     if (count == 2) return value;
/*      */     
/*  597 */     char c3 = this.buffer[this.bufferIndex++];
/*  598 */     value |= c3 << 32L;
/*  599 */     if (count == 3) return value;
/*      */     
/*  601 */     char c4 = this.buffer[this.bufferIndex++];
/*  602 */     return value | c4 << 48L;
/*      */   }
/*      */   
/*      */   protected long readStringPartAsLongUnsafe(int count) throws IOException {
/*  606 */     ensure(count);
/*      */     
/*  608 */     int offset = Unsafe.ARRAY_CHAR_BASE_OFFSET + Unsafe.ARRAY_CHAR_INDEX_SCALE * this.bufferIndex;
/*  609 */     long value = UnsafeUtil.UNSAFE.getLong(this.buffer, offset);
/*  610 */     this.bufferIndex += count;
/*  611 */     long mask = (count == 4) ? -1L : ((1L << count * 16) - 1L);
/*  612 */     return value & mask;
/*      */   }
/*      */   
/*      */   public boolean readBooleanValue() throws IOException {
/*  616 */     ensure(4);
/*      */     
/*  618 */     char read = this.buffer[this.bufferIndex++];
/*  619 */     switch (read) { case 'T':
/*      */       case 't':
/*  621 */         consumeIgnoreCase("true", 1);
/*      */       
/*      */       case 'F':
/*      */       case 'f':
/*  625 */         consumeIgnoreCase("false", 1); }
/*      */ 
/*      */     
/*  628 */     throw expecting(read, "true' or 'false");
/*      */   }
/*      */ 
/*      */   
/*      */   public void skipBooleanValue() throws IOException {
/*  633 */     readBooleanValue();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Void readNullValue() throws IOException {
/*  638 */     consumeIgnoreCase("null", 0);
/*  639 */     return null;
/*      */   }
/*      */   
/*      */   public void skipNullValue() throws IOException {
/*  643 */     consumeIgnoreCase("null", 0);
/*      */   }
/*      */   
/*      */   public double readDoubleValue() throws IOException {
/*  647 */     int start = this.bufferIndex;
/*      */     while (true)
/*  649 */     { if (this.bufferIndex >= this.bufferSize) {
/*  650 */         fill();
/*  651 */         if (this.bufferIndex >= this.bufferSize) {
/*  652 */           return JavaDoubleParser.parseDouble(this.buffer, start, this.bufferIndex - start);
/*      */         }
/*      */       } 
/*      */       
/*  656 */       while (this.bufferIndex < this.bufferSize)
/*  657 */       { char read = this.buffer[this.bufferIndex];
/*  658 */         switch (read) {
/*      */           case '+':
/*      */           case '-':
/*      */           case '.':
/*      */           case 'E':
/*      */           case 'e':
/*  664 */             this.bufferIndex++;
/*      */             continue;
/*      */         } 
/*  667 */         if (Character.isDigit(read)) {
/*  668 */           this.bufferIndex++;
/*      */           continue;
/*      */         } 
/*  671 */         return JavaDoubleParser.parseDouble(this.buffer, start, this.bufferIndex - start); }  }  return JavaDoubleParser.parseDouble(this.buffer, start, this.bufferIndex - start);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void skipDoubleValue() throws IOException {
/*      */     while (true) {
/*  678 */       if (this.bufferIndex >= this.bufferSize) {
/*  679 */         fill();
/*  680 */         if (this.bufferIndex >= this.bufferSize)
/*      */           return; 
/*      */       } 
/*  683 */       while (this.bufferIndex < this.bufferSize) {
/*  684 */         char read = this.buffer[this.bufferIndex];
/*  685 */         switch (read) {
/*      */           case '+':
/*      */           case '-':
/*      */           case '.':
/*      */           case 'E':
/*      */           case 'e':
/*  691 */             this.bufferIndex++;
/*      */             continue;
/*      */         } 
/*  694 */         if (Character.isDigit(read)) {
/*  695 */           this.bufferIndex++;
/*      */           continue;
/*      */         } 
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloatValue() throws IOException {
/*  705 */     return (float)readDoubleValue();
/*      */   }
/*      */   
/*      */   public void skipFloatValue() throws IOException {
/*  709 */     skipDoubleValue();
/*      */   }
/*      */   
/*      */   public long readLongValue() throws IOException {
/*  713 */     return readLongValue(10);
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLongValue(int radix) throws IOException {
/*  718 */     if (this.tempSb == null) this.tempSb = new StringBuilder(1024);
/*      */     
/*      */     while (true)
/*  721 */     { if (this.bufferIndex >= this.bufferSize) {
/*  722 */         fill();
/*  723 */         if (this.bufferIndex >= this.bufferSize) {
/*  724 */           long value = Long.parseLong(this.tempSb, 0, this.tempSb.length(), radix);
/*  725 */           this.tempSb.setLength(0);
/*  726 */           return value;
/*      */         } 
/*      */       } 
/*      */       
/*  730 */       while (this.bufferIndex < this.bufferSize)
/*  731 */       { char read = this.buffer[this.bufferIndex];
/*  732 */         switch (read) {
/*      */           case '+':
/*      */           case '-':
/*      */           case '.':
/*      */           case 'E':
/*      */           case 'e':
/*  738 */             this.tempSb.append(read);
/*  739 */             this.bufferIndex++;
/*      */             continue;
/*      */         } 
/*  742 */         if (Character.digit(read, radix) >= 0) {
/*  743 */           this.tempSb.append(read);
/*  744 */           this.bufferIndex++;
/*      */           continue;
/*      */         } 
/*  747 */         long value = Long.parseLong(this.tempSb, 0, this.tempSb.length(), radix);
/*  748 */         this.tempSb.setLength(0);
/*  749 */         return value; }  }  long l = Long.parseLong(this.tempSb, 0, this.tempSb.length(), radix); this.tempSb.setLength(0); return l;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void skipLongValue() throws IOException {
/*  755 */     skipLongValue(10);
/*      */   }
/*      */   
/*      */   public void skipLongValue(int radix) throws IOException {
/*      */     while (true) {
/*  760 */       if (this.bufferIndex >= this.bufferSize) {
/*  761 */         fill();
/*  762 */         if (this.bufferIndex >= this.bufferSize) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */       
/*  767 */       while (this.bufferIndex < this.bufferSize) {
/*  768 */         char read = this.buffer[this.bufferIndex];
/*  769 */         switch (read) {
/*      */           case '+':
/*      */           case '-':
/*      */           case '.':
/*      */           case 'E':
/*      */           case 'e':
/*  775 */             this.bufferIndex++;
/*      */             continue;
/*      */         } 
/*  778 */         if (Character.digit(read, radix) >= 0) {
/*  779 */           this.bufferIndex++;
/*      */           continue;
/*      */         } 
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int readIntValue() throws IOException {
/*  788 */     return readIntValue(10);
/*      */   }
/*      */   
/*      */   public int readIntValue(int radix) throws IOException {
/*  792 */     return (int)readLongValue(radix);
/*      */   }
/*      */   
/*      */   public byte readByteValue() throws IOException {
/*  796 */     return readByteValue(10);
/*      */   }
/*      */   
/*      */   public byte readByteValue(int radix) throws IOException {
/*  800 */     return (byte)(int)readLongValue(radix);
/*      */   }
/*      */   
/*      */   public void skipIntValue() throws IOException {
/*  804 */     skipLongValue();
/*      */   }
/*      */   
/*      */   public void skipIntValue(int radix) throws IOException {
/*  808 */     skipLongValue(radix);
/*      */   }
/*      */   
/*      */   public void skipObject() throws IOException {
/*  812 */     expect('{');
/*  813 */     skipObjectContinued();
/*      */   }
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
/*      */   public void skipObjectContinued() throws IOException {
/*      */     // Byte code:
/*      */     //   0: iconst_1
/*      */     //   1: istore_1
/*      */     //   2: aload_0
/*      */     //   3: invokevirtual ensure : ()Z
/*      */     //   6: pop
/*      */     //   7: aload_0
/*      */     //   8: getfield bufferIndex : I
/*      */     //   11: aload_0
/*      */     //   12: getfield bufferSize : I
/*      */     //   15: if_icmpge -> 2
/*      */     //   18: aload_0
/*      */     //   19: getfield buffer : [C
/*      */     //   22: aload_0
/*      */     //   23: dup
/*      */     //   24: getfield bufferIndex : I
/*      */     //   27: dup_x1
/*      */     //   28: iconst_1
/*      */     //   29: iadd
/*      */     //   30: putfield bufferIndex : I
/*      */     //   33: caload
/*      */     //   34: istore_2
/*      */     //   35: iload_2
/*      */     //   36: lookupswitch default -> 107, 10 -> 72, 123 -> 93, 125 -> 99
/*      */     //   72: aload_0
/*      */     //   73: dup
/*      */     //   74: getfield line : I
/*      */     //   77: iconst_1
/*      */     //   78: iadd
/*      */     //   79: putfield line : I
/*      */     //   82: aload_0
/*      */     //   83: aload_0
/*      */     //   84: getfield bufferIndex : I
/*      */     //   87: putfield lineStart : I
/*      */     //   90: goto -> 107
/*      */     //   93: iinc #1, 1
/*      */     //   96: goto -> 107
/*      */     //   99: iinc #1, -1
/*      */     //   102: iload_1
/*      */     //   103: ifne -> 107
/*      */     //   106: return
/*      */     //   107: goto -> 7
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #818	-> 0
/*      */     //   #820	-> 2
/*      */     //   #822	-> 7
/*      */     //   #823	-> 18
/*      */     //   #824	-> 35
/*      */     //   #826	-> 72
/*      */     //   #827	-> 82
/*      */     //   #828	-> 90
/*      */     //   #830	-> 93
/*      */     //   #831	-> 96
/*      */     //   #833	-> 99
/*      */     //   #834	-> 102
/*      */     //   #837	-> 107
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   35	72	2	read	C
/*      */     //   0	110	0	this	Lcom/hypixel/hytale/codec/util/RawJsonReader;
/*      */     //   2	108	1	count	I
/*      */   }
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
/*      */   public void skipArray() throws IOException {
/*  842 */     expect('[');
/*  843 */     skipArrayContinued();
/*      */   }
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
/*      */   public void skipArrayContinued() throws IOException {
/*      */     // Byte code:
/*      */     //   0: iconst_1
/*      */     //   1: istore_1
/*      */     //   2: aload_0
/*      */     //   3: invokevirtual ensure : ()Z
/*      */     //   6: pop
/*      */     //   7: aload_0
/*      */     //   8: getfield bufferIndex : I
/*      */     //   11: aload_0
/*      */     //   12: getfield bufferSize : I
/*      */     //   15: if_icmpge -> 2
/*      */     //   18: aload_0
/*      */     //   19: getfield buffer : [C
/*      */     //   22: aload_0
/*      */     //   23: dup
/*      */     //   24: getfield bufferIndex : I
/*      */     //   27: dup_x1
/*      */     //   28: iconst_1
/*      */     //   29: iadd
/*      */     //   30: putfield bufferIndex : I
/*      */     //   33: caload
/*      */     //   34: istore_2
/*      */     //   35: iload_2
/*      */     //   36: lookupswitch default -> 107, 10 -> 72, 91 -> 93, 93 -> 99
/*      */     //   72: aload_0
/*      */     //   73: dup
/*      */     //   74: getfield line : I
/*      */     //   77: iconst_1
/*      */     //   78: iadd
/*      */     //   79: putfield line : I
/*      */     //   82: aload_0
/*      */     //   83: aload_0
/*      */     //   84: getfield bufferIndex : I
/*      */     //   87: putfield lineStart : I
/*      */     //   90: goto -> 107
/*      */     //   93: iinc #1, 1
/*      */     //   96: goto -> 107
/*      */     //   99: iinc #1, -1
/*      */     //   102: iload_1
/*      */     //   103: ifne -> 107
/*      */     //   106: return
/*      */     //   107: goto -> 7
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #848	-> 0
/*      */     //   #850	-> 2
/*      */     //   #852	-> 7
/*      */     //   #853	-> 18
/*      */     //   #854	-> 35
/*      */     //   #856	-> 72
/*      */     //   #857	-> 82
/*      */     //   #858	-> 90
/*      */     //   #860	-> 93
/*      */     //   #861	-> 96
/*      */     //   #863	-> 99
/*      */     //   #864	-> 102
/*      */     //   #867	-> 107
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   35	72	2	read	C
/*      */     //   0	110	0	this	Lcom/hypixel/hytale/codec/util/RawJsonReader;
/*      */     //   2	108	1	count	I
/*      */   }
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
/*      */   public void skipValue() throws IOException {
/*  872 */     ensure();
/*      */     
/*  874 */     char read = this.buffer[this.bufferIndex];
/*  875 */     switch (read) {
/*      */       case '"':
/*  877 */         skipString();
/*      */         return;
/*      */       case 'N':
/*      */       case 'n':
/*  881 */         skipNullValue();
/*      */         return;
/*      */       case 'T':
/*      */       case 't':
/*  885 */         consumeIgnoreCase("true", 0);
/*      */         return;
/*      */       case 'F':
/*      */       case 'f':
/*  889 */         consumeIgnoreCase("false", 0);
/*      */         return;
/*      */       case '{':
/*  892 */         skipObject();
/*      */         return;
/*      */       case '[':
/*  895 */         skipArray();
/*      */         return;
/*      */       case '+':
/*      */       case '-':
/*  899 */         skipDoubleValue();
/*      */         return;
/*      */     } 
/*  902 */     if (Character.isDigit(read)) {
/*  903 */       skipDoubleValue();
/*      */     } else {
/*      */       
/*  906 */       throw unexpectedChar(read);
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private IOException unexpectedEOF() {
/*  912 */     return new IOException("Unexpected EOF!");
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private IOException unexpectedChar(char read) {
/*  917 */     return new IOException("Unexpected character: " + Integer.toHexString(read) + ", '" + read + "'!");
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private IOException expecting(char read, char expect) {
/*  922 */     return new IOException("Unexpected character: " + Integer.toHexString(read) + ", '" + read + "' expected '" + expect + "'!");
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private IOException expecting(char read, String expected) {
/*  927 */     return new IOException("Unexpected character: " + Integer.toHexString(read) + ", '" + read + "' expected '" + expected + "'!");
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private IOException expectingWhile(char read, String expected, String reason) {
/*  932 */     return new IOException("Unexpected character: " + Integer.toHexString(read) + ", '" + read + "' expected '" + expected + "' while " + reason + "!");
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private IOException expecting(char read, @Nonnull String expected, int index) {
/*  937 */     return new IOException("Unexpected character: " + Integer.toHexString(read) + ", '" + read + "' when consuming string '" + expected + "' expected '" + expected.substring(index - 1) + "'!");
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String toString() {
/*  943 */     if (this.buffer == null) return "Closed RawJsonReader";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  952 */     StringBuilder s = (new StringBuilder("Index: ")).append(this.streamIndex + this.bufferIndex).append(", StreamIndex: ").append(this.streamIndex).append(", BufferIndex: ").append(this.bufferIndex).append(", BufferSize: ").append(this.bufferSize).append(", Line: ").append(this.line).append(", MarkIndex: ").append(this.markIndex).append(", MarkLine: ").append(this.markLine).append('\n');
/*      */ 
/*      */     
/*  955 */     int lineStart = findLineStart(this.bufferIndex);
/*  956 */     int lineNumber = this.line;
/*  957 */     while (lineStart > 0 && lineNumber > this.line - 10) {
/*  958 */       lineStart = findLineStart(lineStart);
/*  959 */       lineNumber--;
/*      */     } 
/*  961 */     while (lineNumber < this.line) {
/*  962 */       lineStart = appendLine(s, lineStart, lineNumber);
/*  963 */       lineNumber++;
/*      */     } 
/*      */ 
/*      */     
/*  967 */     lineStart = appendProblemLine(s, lineStart, this.line);
/*      */ 
/*      */     
/*  970 */     while (lineStart < this.bufferSize && lineNumber < this.line + 10) {
/*  971 */       lineStart = appendLine(s, lineStart, lineNumber);
/*  972 */       lineNumber++;
/*      */     } 
/*      */     
/*  975 */     return (this.in == null) ? ("Buffer RawJsonReader: " + 
/*  976 */       String.valueOf(s)) : ("Streamed RawJsonReader: " + 
/*      */ 
/*      */ 
/*      */       
/*  980 */       String.valueOf(s));
/*      */   }
/*      */   
/*      */   private int findLineStart(int index) {
/*  984 */     index--;
/*  985 */     while (index > 0 && this.buffer[index] != '\n') {
/*  986 */       index--;
/*      */     }
/*  988 */     return index;
/*      */   }
/*      */   
/*      */   private int appendLine(@Nonnull StringBuilder sb, int index, int lineNumber) {
/*  992 */     int lineStart = index + 1;
/*  993 */     index++;
/*  994 */     while (index < this.bufferSize && this.buffer[index] != '\n') {
/*  995 */       index++;
/*      */     }
/*  997 */     sb.append("L").append(String.format("%3s", new Object[] { Integer.valueOf(lineNumber) })).append('|').append(this.buffer, lineStart, index - lineStart).append('\n');
/*  998 */     return index;
/*      */   }
/*      */ 
/*      */   
/*      */   private int appendProblemLine(@Nonnull StringBuilder sb, int index, int lineNumber) {
/* 1003 */     int lineStart = ++index;
/* 1004 */     while (index < this.bufferSize && this.buffer[index] != '\n') {
/* 1005 */       index++;
/*      */     }
/* 1007 */     sb.append("L").append(String.format("%3s", new Object[] { Integer.valueOf(lineNumber) })).append('>').append(this.buffer, lineStart, index - lineStart).append('\n');
/* 1008 */     sb.append("    |");
/* 1009 */     sb.append("-".repeat(Math.max(0, this.bufferIndex - lineStart - 1)));
/* 1010 */     sb.append('^').append('\n');
/* 1011 */     return index;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static RawJsonReader fromRawString(String str) {
/* 1016 */     return fromJsonString("\"" + str + "\"");
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static RawJsonReader fromJsonString(@Nonnull String str) {
/* 1021 */     return fromBuffer(str.toCharArray());
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static RawJsonReader fromPath(@Nonnull Path path, @Nonnull char[] buffer) throws IOException {
/* 1026 */     return new RawJsonReader(new InputStreamReader(Files.newInputStream(path, new java.nio.file.OpenOption[0]), StandardCharsets.UTF_8), buffer);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static RawJsonReader fromBuffer(@Nonnull char[] buffer) {
/* 1031 */     return new RawJsonReader(buffer);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean equalsIgnoreCase(char c1, char c2) {
/* 1036 */     if (c1 == c2) return true; 
/* 1037 */     char u1 = Character.toUpperCase(c1);
/* 1038 */     char u2 = Character.toUpperCase(c2);
/* 1039 */     return (u1 == u2 || Character.toLowerCase(u1) == Character.toLowerCase(u2));
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public static BsonDocument readBsonDocument(@Nonnull RawJsonReader reader) throws IOException {
/* 1044 */     reader.expect('{');
/*      */     
/* 1046 */     StringBuilder sb = new StringBuilder("{");
/* 1047 */     readBsonDocument0(reader, sb);
/* 1048 */     return BsonDocument.parse(sb.toString());
/*      */   }
/*      */   
/*      */   private static void readBsonDocument0(@Nonnull RawJsonReader reader, @Nonnull StringBuilder sb) throws IOException {
/* 1052 */     int count = 1;
/*      */     
/*      */     int read;
/* 1055 */     while ((read = reader.read()) != -1) {
/* 1056 */       sb.append((char)read);
/* 1057 */       switch (read) {
/*      */         case 123:
/* 1059 */           count++;
/*      */         
/*      */         case 125:
/* 1062 */           count--;
/* 1063 */           if (count == 0)
/*      */             return; 
/*      */         case 91:
/* 1066 */           readBsonArray0(reader, sb);
/*      */         
/*      */         case 10:
/* 1069 */           reader.line++;
/* 1070 */           reader.lineStart = reader.bufferIndex;
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/* 1075 */     throw reader.unexpectedEOF();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public static BsonArray readBsonArray(@Nonnull RawJsonReader reader) throws IOException {
/* 1080 */     reader.expect('[');
/*      */     
/* 1082 */     StringBuilder sb = new StringBuilder("[");
/* 1083 */     readBsonArray0(reader, sb);
/* 1084 */     return BsonArray.parse(sb.toString());
/*      */   }
/*      */   
/*      */   private static void readBsonArray0(@Nonnull RawJsonReader reader, @Nonnull StringBuilder sb) throws IOException {
/* 1088 */     int count = 1;
/*      */     
/*      */     int read;
/* 1091 */     while ((read = reader.read()) != -1) {
/* 1092 */       sb.append((char)read);
/* 1093 */       switch (read) {
/*      */         case 91:
/* 1095 */           count++;
/*      */         
/*      */         case 93:
/* 1098 */           count--;
/* 1099 */           if (count == 0)
/*      */             return; 
/*      */         case 123:
/* 1102 */           readBsonDocument0(reader, sb);
/*      */         
/*      */         case 10:
/* 1105 */           reader.line++;
/* 1106 */           reader.lineStart = reader.bufferIndex;
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/* 1111 */     throw reader.unexpectedEOF();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public static BsonValue readBsonValue(@Nonnull RawJsonReader reader) throws IOException {
/* 1116 */     int read = reader.peek();
/* 1117 */     if (read == -1) throw reader.unexpectedEOF(); 
/* 1118 */     switch (read) { case 34: 
/*      */       case 78:
/*      */       case 110:
/* 1121 */         reader.skipNullValue();
/*      */       case 70: case 84: case 102:
/*      */       case 116:
/* 1124 */         return reader.readBooleanValue() ? (BsonValue)BsonBoolean.TRUE : (BsonValue)BsonBoolean.FALSE;
/*      */       case 123:
/*      */       
/*      */       case 91:
/*      */       
/*      */       case 43:
/*      */       case 45:
/* 1131 */        }  if (Character.isDigit(read)) {
/*      */     
/*      */     } else {
/* 1134 */       throw reader.unexpectedChar((char)read);
/*      */     } 
/*      */     return (BsonValue)new BsonDouble(reader.readDoubleValue());
/*      */   }
/*      */   
/*      */   public static boolean seekToKey(@Nonnull RawJsonReader reader, @Nonnull String search) throws IOException {
/* 1140 */     reader.consumeWhiteSpace();
/* 1141 */     reader.expect('{');
/* 1142 */     reader.consumeWhiteSpace();
/* 1143 */     if (reader.tryConsume('}')) return false;
/*      */     
/*      */     while (true) {
/* 1146 */       reader.expect('"');
/* 1147 */       if (reader.tryConsume(search) && reader.tryConsume('"')) {
/* 1148 */         reader.consumeWhiteSpace();
/* 1149 */         reader.expect(':');
/* 1150 */         reader.consumeWhiteSpace();
/* 1151 */         return true;
/*      */       } 
/* 1153 */       reader.skipRemainingString();
/*      */       
/* 1155 */       reader.consumeWhiteSpace();
/* 1156 */       reader.expect(':');
/* 1157 */       reader.consumeWhiteSpace();
/*      */       
/* 1159 */       reader.skipValue();
/* 1160 */       reader.consumeWhiteSpace();
/*      */       
/* 1162 */       if (reader.tryConsumeOrExpect('}', ',')) return false; 
/* 1163 */       reader.consumeWhiteSpace();
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static String seekToKeyFromObjectStart(@Nonnull RawJsonReader reader, @Nonnull String search1, @Nonnull String search2) throws IOException {
/* 1169 */     reader.consumeWhiteSpace();
/* 1170 */     reader.expect('{');
/* 1171 */     reader.consumeWhiteSpace();
/* 1172 */     if (reader.tryConsume('}')) return null;
/*      */     
/*      */     while (true) {
/* 1175 */       reader.expect('"');
/* 1176 */       int search1Index = reader.tryConsumeSome(search1, 0);
/* 1177 */       if (search1Index == search1.length() && reader.tryConsume('"')) {
/* 1178 */         reader.consumeWhiteSpace();
/* 1179 */         reader.expect(':');
/* 1180 */         reader.consumeWhiteSpace();
/* 1181 */         return search1;
/* 1182 */       }  if (reader.tryConsume(search2, search1Index) && reader.tryConsume('"')) {
/* 1183 */         reader.consumeWhiteSpace();
/* 1184 */         reader.expect(':');
/* 1185 */         reader.consumeWhiteSpace();
/* 1186 */         return search2;
/*      */       } 
/* 1188 */       reader.skipRemainingString();
/*      */       
/* 1190 */       reader.consumeWhiteSpace();
/* 1191 */       reader.expect(':');
/* 1192 */       reader.consumeWhiteSpace();
/*      */       
/* 1194 */       reader.skipValue();
/* 1195 */       reader.consumeWhiteSpace();
/*      */       
/* 1197 */       if (reader.tryConsumeOrExpect('}', ',')) return null; 
/* 1198 */       reader.consumeWhiteSpace();
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static String seekToKeyFromObjectContinued(@Nonnull RawJsonReader reader, @Nonnull String search1, @Nonnull String search2) throws IOException {
/* 1204 */     reader.consumeWhiteSpace();
/* 1205 */     if (reader.tryConsumeOrExpect('}', ',')) return null; 
/* 1206 */     reader.consumeWhiteSpace();
/*      */     
/*      */     while (true) {
/* 1209 */       reader.expect('"');
/* 1210 */       int search1Index = reader.tryConsumeSome(search1, 0);
/* 1211 */       if (search1Index == search1.length() && reader.tryConsume('"')) {
/* 1212 */         reader.consumeWhiteSpace();
/* 1213 */         reader.expect(':');
/* 1214 */         reader.consumeWhiteSpace();
/* 1215 */         return search1;
/* 1216 */       }  if (reader.tryConsume(search2, search1Index) && reader.tryConsume('"')) {
/* 1217 */         reader.consumeWhiteSpace();
/* 1218 */         reader.expect(':');
/* 1219 */         reader.consumeWhiteSpace();
/* 1220 */         return search2;
/*      */       } 
/* 1222 */       reader.skipRemainingString();
/*      */       
/* 1224 */       reader.consumeWhiteSpace();
/* 1225 */       reader.expect(':');
/* 1226 */       reader.consumeWhiteSpace();
/*      */       
/* 1228 */       reader.skipValue();
/* 1229 */       reader.consumeWhiteSpace();
/*      */       
/* 1231 */       if (reader.tryConsumeOrExpect('}', ',')) return null; 
/* 1232 */       reader.consumeWhiteSpace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void validateBsonDocument(@Nonnull RawJsonReader reader) throws IOException {
/* 1237 */     reader.expect('{');
/* 1238 */     reader.consumeWhiteSpace();
/* 1239 */     if (reader.tryConsume('}'))
/*      */       return; 
/*      */     while (true) {
/* 1242 */       reader.skipString();
/*      */       
/* 1244 */       reader.consumeWhiteSpace();
/* 1245 */       reader.expect(':');
/* 1246 */       reader.consumeWhiteSpace();
/*      */       
/* 1248 */       validateBsonValue(reader);
/* 1249 */       reader.consumeWhiteSpace();
/*      */       
/* 1251 */       if (reader.tryConsumeOrExpect('}', ','))
/* 1252 */         return;  reader.consumeWhiteSpace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void validateBsonArray(@Nonnull RawJsonReader reader) throws IOException {
/* 1257 */     reader.expect('[');
/* 1258 */     reader.consumeWhiteSpace();
/* 1259 */     if (reader.tryConsume(']'))
/*      */       return; 
/*      */     while (true) {
/* 1262 */       validateBsonValue(reader);
/* 1263 */       reader.consumeWhiteSpace();
/*      */       
/* 1265 */       if (reader.tryConsumeOrExpect(']', ','))
/* 1266 */         return;  reader.consumeWhiteSpace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void validateBsonValue(@Nonnull RawJsonReader reader) throws IOException {
/* 1271 */     int read = reader.peek();
/* 1272 */     if (read == -1) throw reader.unexpectedEOF(); 
/* 1273 */     switch (read) { case 34:
/* 1274 */         reader.skipString(); return;
/* 1275 */       case 78: case 110: reader.skipNullValue(); return;
/* 1276 */       case 70: case 84: case 102: case 116: reader.readBooleanValue(); return;
/* 1277 */       case 123: validateBsonDocument(reader); return;
/* 1278 */       case 91: validateBsonArray(reader); return;
/* 1279 */       case 43: case 45: reader.readDoubleValue(); return; }
/*      */     
/* 1281 */     if (Character.isDigit(read)) {
/* 1282 */       reader.readDoubleValue();
/*      */       return;
/*      */     } 
/* 1285 */     throw reader.unexpectedChar((char)read);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static <T> T readSync(@Nonnull Path path, @Nonnull Codec<T> codec, @Nonnull HytaleLogger logger) throws IOException {
/* 1292 */     char[] buffer = READ_BUFFER.get();
/* 1293 */     RawJsonReader reader = fromPath(path, buffer);
/*      */     try {
/* 1295 */       ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 1296 */       T value = (T)codec.decodeJson(reader, extraInfo);
/* 1297 */       extraInfo.getValidationResults().logOrThrowValidatorExceptions(logger);
/*      */       
/* 1299 */       return value;
/*      */     } finally {
/* 1301 */       char[] newBuffer = reader.closeAndTakeBuffer();
/* 1302 */       if (newBuffer.length > buffer.length) READ_BUFFER.set(newBuffer);
/*      */     
/*      */     } 
/*      */   }
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
/*      */   @Nullable
/*      */   public static <T> T readSyncWithBak(@Nonnull Path path, @Nonnull Codec<T> codec, @Nonnull HytaleLogger logger) {
/*      */     try {
/* 1321 */       return readSync(path, codec, logger);
/* 1322 */     } catch (IOException e) {
/* 1323 */       Path backupPath = path.resolveSibling(String.valueOf(path.getFileName()) + ".bak");
/* 1324 */       if (e instanceof NoSuchFileException && !Files.exists(backupPath, new java.nio.file.LinkOption[0])) {
/* 1325 */         return null;
/*      */       }
/* 1327 */       if (Sentry.isEnabled()) Sentry.captureException(e); 
/* 1328 */       ((HytaleLogger.Api)logger.at(Level.SEVERE).withCause(e)).log("Failed to load from primary file %s, trying backup file", path);
/*      */       
/*      */       try {
/* 1331 */         T value = readSync(backupPath, codec, logger);
/* 1332 */         logger.at(Level.WARNING).log("Loaded from backup file %s after primary file %s failed to load", backupPath, path);
/* 1333 */         return value;
/* 1334 */       } catch (NoSuchFileException e1) {
/* 1335 */         return null;
/* 1336 */       } catch (IOException e1) {
/* 1337 */         ((HytaleLogger.Api)logger.at(Level.WARNING).withCause(e)).log("Failed to load from both %s and backup file %s", path, backupPath);
/* 1338 */         return null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\code\\util\RawJsonReader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */