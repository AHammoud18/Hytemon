/*     */ package io.sentry.vendor.gson.stream;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.Flushable;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ @Internal
/*     */ public class JsonWriter
/*     */   implements Closeable, Flushable
/*     */ {
/* 156 */   private static final String[] REPLACEMENT_CHARS = new String[128]; static {
/* 157 */     for (int i = 0; i <= 31; i++) {
/* 158 */       REPLACEMENT_CHARS[i] = String.format("\\u%04x", new Object[] { Integer.valueOf(i) });
/*     */     } 
/* 160 */     REPLACEMENT_CHARS[34] = "\\\"";
/* 161 */     REPLACEMENT_CHARS[92] = "\\\\";
/* 162 */     REPLACEMENT_CHARS[9] = "\\t";
/* 163 */     REPLACEMENT_CHARS[8] = "\\b";
/* 164 */     REPLACEMENT_CHARS[10] = "\\n";
/* 165 */     REPLACEMENT_CHARS[13] = "\\r";
/* 166 */     REPLACEMENT_CHARS[12] = "\\f";
/* 167 */   } private static final String[] HTML_SAFE_REPLACEMENT_CHARS = (String[])REPLACEMENT_CHARS.clone(); static {
/* 168 */     HTML_SAFE_REPLACEMENT_CHARS[60] = "\\u003c";
/* 169 */     HTML_SAFE_REPLACEMENT_CHARS[62] = "\\u003e";
/* 170 */     HTML_SAFE_REPLACEMENT_CHARS[38] = "\\u0026";
/* 171 */     HTML_SAFE_REPLACEMENT_CHARS[61] = "\\u003d";
/* 172 */     HTML_SAFE_REPLACEMENT_CHARS[39] = "\\u0027";
/*     */   }
/*     */ 
/*     */   
/*     */   private final Writer out;
/*     */   
/* 178 */   private int[] stack = new int[32];
/* 179 */   private int stackSize = 0; private String indent;
/*     */   public JsonWriter(Writer out) {
/* 181 */     push(6);
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
/* 193 */     this.separator = ":";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     this.serializeNulls = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     if (out == null) {
/* 210 */       throw new NullPointerException("out == null");
/*     */     }
/* 212 */     this.out = out;
/*     */   }
/*     */ 
/*     */   
/*     */   private String separator;
/*     */   
/*     */   private boolean lenient;
/*     */   private boolean htmlSafe;
/*     */   private String deferredName;
/*     */   private boolean serializeNulls;
/*     */   
/*     */   public final void setIndent(@Nullable String indent) {
/* 224 */     if (indent == null || indent.length() == 0) {
/* 225 */       this.indent = null;
/* 226 */       this.separator = ":";
/*     */     } else {
/* 228 */       this.indent = indent;
/* 229 */       this.separator = ": ";
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getIndent() {
/* 235 */     return this.indent;
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
/*     */   public final void setLenient(boolean lenient) {
/* 251 */     this.lenient = lenient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLenient() {
/* 258 */     return this.lenient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setHtmlSafe(boolean htmlSafe) {
/* 269 */     this.htmlSafe = htmlSafe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isHtmlSafe() {
/* 277 */     return this.htmlSafe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setSerializeNulls(boolean serializeNulls) {
/* 285 */     this.serializeNulls = serializeNulls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getSerializeNulls() {
/* 293 */     return this.serializeNulls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter beginArray() throws IOException {
/* 303 */     writeDeferredName();
/* 304 */     return open(1, '[');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter endArray() throws IOException {
/* 313 */     return close(1, 2, ']');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter beginObject() throws IOException {
/* 323 */     writeDeferredName();
/* 324 */     return open(3, '{');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter endObject() throws IOException {
/* 333 */     return close(3, 5, '}');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonWriter open(int empty, char openBracket) throws IOException {
/* 341 */     beforeValue();
/* 342 */     push(empty);
/* 343 */     this.out.write(openBracket);
/* 344 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonWriter close(int empty, int nonempty, char closeBracket) throws IOException {
/* 353 */     int context = peek();
/* 354 */     if (context != nonempty && context != empty) {
/* 355 */       throw new IllegalStateException("Nesting problem.");
/*     */     }
/* 357 */     if (this.deferredName != null) {
/* 358 */       throw new IllegalStateException("Dangling name: " + this.deferredName);
/*     */     }
/*     */     
/* 361 */     this.stackSize--;
/* 362 */     if (context == nonempty) {
/* 363 */       newline();
/*     */     }
/* 365 */     this.out.write(closeBracket);
/* 366 */     return this;
/*     */   }
/*     */   
/*     */   private void push(int newTop) {
/* 370 */     if (this.stackSize == this.stack.length) {
/* 371 */       this.stack = Arrays.copyOf(this.stack, this.stackSize * 2);
/*     */     }
/* 373 */     this.stack[this.stackSize++] = newTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int peek() {
/* 380 */     if (this.stackSize == 0) {
/* 381 */       throw new IllegalStateException("JsonWriter is closed.");
/*     */     }
/* 383 */     return this.stack[this.stackSize - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void replaceTop(int topOfStack) {
/* 390 */     this.stack[this.stackSize - 1] = topOfStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter name(String name) throws IOException {
/* 400 */     if (name == null) {
/* 401 */       throw new NullPointerException("name == null");
/*     */     }
/* 403 */     if (this.deferredName != null) {
/* 404 */       throw new IllegalStateException();
/*     */     }
/* 406 */     if (this.stackSize == 0) {
/* 407 */       throw new IllegalStateException("JsonWriter is closed.");
/*     */     }
/* 409 */     this.deferredName = name;
/* 410 */     return this;
/*     */   }
/*     */   
/*     */   private void writeDeferredName() throws IOException {
/* 414 */     if (this.deferredName != null) {
/* 415 */       beforeName();
/* 416 */       string(this.deferredName);
/* 417 */       this.deferredName = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(@Nullable String value) throws IOException {
/* 428 */     if (value == null) {
/* 429 */       return nullValue();
/*     */     }
/* 431 */     writeDeferredName();
/* 432 */     beforeValue();
/* 433 */     string(value);
/* 434 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter jsonValue(@Nullable String value) throws IOException {
/* 445 */     if (value == null) {
/* 446 */       return nullValue();
/*     */     }
/* 448 */     writeDeferredName();
/* 449 */     beforeValue();
/* 450 */     this.out.append(value);
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter nullValue() throws IOException {
/* 460 */     if (this.deferredName != null) {
/* 461 */       if (this.serializeNulls) {
/* 462 */         writeDeferredName();
/*     */       } else {
/* 464 */         this.deferredName = null;
/* 465 */         return this;
/*     */       } 
/*     */     }
/* 468 */     beforeValue();
/* 469 */     this.out.write("null");
/* 470 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(boolean value) throws IOException {
/* 479 */     writeDeferredName();
/* 480 */     beforeValue();
/* 481 */     this.out.write(value ? "true" : "false");
/* 482 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(@Nullable Boolean value) throws IOException {
/* 491 */     if (value == null) {
/* 492 */       return nullValue();
/*     */     }
/* 494 */     writeDeferredName();
/* 495 */     beforeValue();
/* 496 */     this.out.write(value.booleanValue() ? "true" : "false");
/* 497 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(double value) throws IOException {
/* 508 */     writeDeferredName();
/* 509 */     if (!this.lenient && (Double.isNaN(value) || Double.isInfinite(value))) {
/* 510 */       throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
/*     */     }
/* 512 */     beforeValue();
/* 513 */     this.out.append(Double.toString(value));
/* 514 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(long value) throws IOException {
/* 523 */     writeDeferredName();
/* 524 */     beforeValue();
/* 525 */     this.out.write(Long.toString(value));
/* 526 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(@Nullable Number value) throws IOException {
/* 537 */     if (value == null) {
/* 538 */       return nullValue();
/*     */     }
/*     */     
/* 541 */     writeDeferredName();
/* 542 */     String string = value.toString();
/* 543 */     if (!this.lenient && (string
/* 544 */       .equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
/* 545 */       throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
/*     */     }
/* 547 */     beforeValue();
/* 548 */     this.out.append(string);
/* 549 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 557 */     if (this.stackSize == 0) {
/* 558 */       throw new IllegalStateException("JsonWriter is closed.");
/*     */     }
/* 560 */     this.out.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 569 */     this.out.close();
/*     */     
/* 571 */     int size = this.stackSize;
/* 572 */     if (size > 1 || (size == 1 && this.stack[size - 1] != 7)) {
/* 573 */       throw new IOException("Incomplete document");
/*     */     }
/* 575 */     this.stackSize = 0;
/*     */   }
/*     */   
/*     */   private void string(String value) throws IOException {
/* 579 */     String[] replacements = this.htmlSafe ? HTML_SAFE_REPLACEMENT_CHARS : REPLACEMENT_CHARS;
/* 580 */     this.out.write(34);
/* 581 */     int last = 0;
/* 582 */     int length = value.length();
/* 583 */     for (int i = 0; i < length; i++) {
/* 584 */       String replacement; char c = value.charAt(i);
/*     */       
/* 586 */       if (c < '') {
/* 587 */         replacement = replacements[c];
/* 588 */         if (replacement == null) {
/*     */           continue;
/*     */         }
/* 591 */       } else if (c == ' ') {
/* 592 */         replacement = "\\u2028";
/* 593 */       } else if (c == ' ') {
/* 594 */         replacement = "\\u2029";
/*     */       } else {
/*     */         continue;
/*     */       } 
/* 598 */       if (last < i) {
/* 599 */         this.out.write(value, last, i - last);
/*     */       }
/* 601 */       this.out.write(replacement);
/* 602 */       last = i + 1; continue;
/*     */     } 
/* 604 */     if (last < length) {
/* 605 */       this.out.write(value, last, length - last);
/*     */     }
/* 607 */     this.out.write(34);
/*     */   }
/*     */   
/*     */   private void newline() throws IOException {
/* 611 */     if (this.indent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 615 */     this.out.write(10);
/* 616 */     for (int i = 1, size = this.stackSize; i < size; i++) {
/* 617 */       this.out.write(this.indent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void beforeName() throws IOException {
/* 626 */     int context = peek();
/* 627 */     if (context == 5) {
/* 628 */       this.out.write(44);
/* 629 */     } else if (context != 3) {
/* 630 */       throw new IllegalStateException("Nesting problem.");
/*     */     } 
/* 632 */     newline();
/* 633 */     replaceTop(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void beforeValue() throws IOException {
/* 643 */     switch (peek()) {
/*     */       case 7:
/* 645 */         if (!this.lenient) {
/* 646 */           throw new IllegalStateException("JSON must have only one top-level value.");
/*     */         }
/*     */ 
/*     */       
/*     */       case 6:
/* 651 */         replaceTop(7);
/*     */         return;
/*     */       
/*     */       case 1:
/* 655 */         replaceTop(2);
/* 656 */         newline();
/*     */         return;
/*     */       
/*     */       case 2:
/* 660 */         this.out.append(',');
/* 661 */         newline();
/*     */         return;
/*     */       
/*     */       case 4:
/* 665 */         this.out.append(this.separator);
/* 666 */         replaceTop(5);
/*     */         return;
/*     */     } 
/*     */     
/* 670 */     throw new IllegalStateException("Nesting problem.");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\vendor\gson\stream\JsonWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */