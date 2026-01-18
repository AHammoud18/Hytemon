/*     */ package com.nimbusds.jose.shaded.gson.stream;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.nimbusds.jose.shaded.gson.FormattingStyle;
/*     */ import com.nimbusds.jose.shaded.gson.Strictness;
/*     */ import java.io.Closeable;
/*     */ import java.io.Flushable;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonWriter
/*     */   implements Closeable, Flushable
/*     */ {
/* 167 */   private static final Pattern VALID_JSON_NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9][0-9]*)(?:\\.[0-9]+)?(?:[eE][-+]?[0-9]+)?");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   private static final String[] REPLACEMENT_CHARS = new String[128]; static {
/* 184 */     for (int i = 0; i <= 31; i++) {
/* 185 */       REPLACEMENT_CHARS[i] = String.format("\\u%04x", new Object[] { Integer.valueOf(i) });
/*     */     } 
/* 187 */     REPLACEMENT_CHARS[34] = "\\\"";
/* 188 */     REPLACEMENT_CHARS[92] = "\\\\";
/* 189 */     REPLACEMENT_CHARS[9] = "\\t";
/* 190 */     REPLACEMENT_CHARS[8] = "\\b";
/* 191 */     REPLACEMENT_CHARS[10] = "\\n";
/* 192 */     REPLACEMENT_CHARS[13] = "\\r";
/* 193 */     REPLACEMENT_CHARS[12] = "\\f";
/* 194 */   } private static final String[] HTML_SAFE_REPLACEMENT_CHARS = (String[])REPLACEMENT_CHARS.clone(); static {
/* 195 */     HTML_SAFE_REPLACEMENT_CHARS[60] = "\\u003c";
/* 196 */     HTML_SAFE_REPLACEMENT_CHARS[62] = "\\u003e";
/* 197 */     HTML_SAFE_REPLACEMENT_CHARS[38] = "\\u0026";
/* 198 */     HTML_SAFE_REPLACEMENT_CHARS[61] = "\\u003d";
/* 199 */     HTML_SAFE_REPLACEMENT_CHARS[39] = "\\u0027";
/*     */   }
/*     */ 
/*     */   
/*     */   private final Writer out;
/*     */   
/* 205 */   private int[] stack = new int[32];
/* 206 */   private int stackSize = 0; private FormattingStyle formattingStyle; private String formattedColon;
/*     */   
/*     */   public JsonWriter(Writer out) {
/* 209 */     push(6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     this.strictness = Strictness.LEGACY_STRICT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     this.serializeNulls = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     this.out = Objects.<Writer>requireNonNull(out, "out == null");
/* 234 */     setFormattingStyle(FormattingStyle.COMPACT);
/*     */   }
/*     */ 
/*     */   
/*     */   private String formattedComma;
/*     */   
/*     */   private boolean usesEmptyNewlineAndIndent;
/*     */   
/*     */   private Strictness strictness;
/*     */   
/*     */   private boolean htmlSafe;
/*     */   
/*     */   private String deferredName;
/*     */   private boolean serializeNulls;
/*     */   
/*     */   public final void setIndent(String indent) {
/* 250 */     if (indent.isEmpty()) {
/* 251 */       setFormattingStyle(FormattingStyle.COMPACT);
/*     */     } else {
/* 253 */       setFormattingStyle(FormattingStyle.PRETTY.withIndent(indent));
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
/*     */ 
/*     */   
/*     */   public final void setFormattingStyle(FormattingStyle formattingStyle) {
/* 268 */     this.formattingStyle = Objects.<FormattingStyle>requireNonNull(formattingStyle);
/*     */     
/* 270 */     this.formattedComma = ",";
/* 271 */     if (this.formattingStyle.usesSpaceAfterSeparators()) {
/* 272 */       this.formattedColon = ": ";
/*     */ 
/*     */       
/* 275 */       if (this.formattingStyle.getNewline().isEmpty()) {
/* 276 */         this.formattedComma = ", ";
/*     */       }
/*     */     } else {
/* 279 */       this.formattedColon = ":";
/*     */     } 
/*     */     
/* 282 */     this
/* 283 */       .usesEmptyNewlineAndIndent = (this.formattingStyle.getNewline().isEmpty() && this.formattingStyle.getIndent().isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final FormattingStyle getFormattingStyle() {
/* 294 */     return this.formattingStyle;
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
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final void setLenient(boolean lenient) {
/* 315 */     setStrictness(lenient ? Strictness.LENIENT : Strictness.LEGACY_STRICT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLenient() {
/* 324 */     return (this.strictness == Strictness.LENIENT);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setStrictness(Strictness strictness) {
/* 347 */     this.strictness = Objects.<Strictness>requireNonNull(strictness);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Strictness getStrictness() {
/* 357 */     return this.strictness;
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
/*     */   public final void setHtmlSafe(boolean htmlSafe) {
/* 369 */     this.htmlSafe = htmlSafe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isHtmlSafe() {
/* 378 */     return this.htmlSafe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setSerializeNulls(boolean serializeNulls) {
/* 388 */     this.serializeNulls = serializeNulls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getSerializeNulls() {
/* 398 */     return this.serializeNulls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter beginArray() throws IOException {
/* 409 */     writeDeferredName();
/* 410 */     return openScope(1, '[');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter endArray() throws IOException {
/* 420 */     return closeScope(1, 2, ']');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter beginObject() throws IOException {
/* 431 */     writeDeferredName();
/* 432 */     return openScope(3, '{');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter endObject() throws IOException {
/* 442 */     return closeScope(3, 5, '}');
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   private JsonWriter openScope(int empty, char openBracket) throws IOException {
/* 448 */     beforeValue();
/* 449 */     push(empty);
/* 450 */     this.out.write(openBracket);
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   private JsonWriter closeScope(int empty, int nonempty, char closeBracket) throws IOException {
/* 457 */     int context = peek();
/* 458 */     if (context != nonempty && context != empty) {
/* 459 */       throw new IllegalStateException("Nesting problem.");
/*     */     }
/* 461 */     if (this.deferredName != null) {
/* 462 */       throw new IllegalStateException("Dangling name: " + this.deferredName);
/*     */     }
/*     */     
/* 465 */     this.stackSize--;
/* 466 */     if (context == nonempty) {
/* 467 */       newline();
/*     */     }
/* 469 */     this.out.write(closeBracket);
/* 470 */     return this;
/*     */   }
/*     */   
/*     */   private void push(int newTop) {
/* 474 */     if (this.stackSize == this.stack.length) {
/* 475 */       this.stack = Arrays.copyOf(this.stack, this.stackSize * 2);
/*     */     }
/* 477 */     this.stack[this.stackSize++] = newTop;
/*     */   }
/*     */ 
/*     */   
/*     */   private int peek() {
/* 482 */     if (this.stackSize == 0) {
/* 483 */       throw new IllegalStateException("JsonWriter is closed.");
/*     */     }
/* 485 */     return this.stack[this.stackSize - 1];
/*     */   }
/*     */ 
/*     */   
/*     */   private void replaceTop(int topOfStack) {
/* 490 */     this.stack[this.stackSize - 1] = topOfStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter name(String name) throws IOException {
/* 501 */     Objects.requireNonNull(name, "name == null");
/* 502 */     if (this.deferredName != null) {
/* 503 */       throw new IllegalStateException("Already wrote a name, expecting a value.");
/*     */     }
/* 505 */     int context = peek();
/* 506 */     if (context != 3 && context != 5) {
/* 507 */       throw new IllegalStateException("Please begin an object before writing a name.");
/*     */     }
/* 509 */     this.deferredName = name;
/* 510 */     return this;
/*     */   }
/*     */   
/*     */   private void writeDeferredName() throws IOException {
/* 514 */     if (this.deferredName != null) {
/* 515 */       beforeName();
/* 516 */       string(this.deferredName);
/* 517 */       this.deferredName = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(String value) throws IOException {
/* 529 */     if (value == null) {
/* 530 */       return nullValue();
/*     */     }
/* 532 */     writeDeferredName();
/* 533 */     beforeValue();
/* 534 */     string(value);
/* 535 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(boolean value) throws IOException {
/* 545 */     writeDeferredName();
/* 546 */     beforeValue();
/* 547 */     this.out.write(value ? "true" : "false");
/* 548 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(Boolean value) throws IOException {
/* 559 */     if (value == null) {
/* 560 */       return nullValue();
/*     */     }
/* 562 */     writeDeferredName();
/* 563 */     beforeValue();
/* 564 */     this.out.write(value.booleanValue() ? "true" : "false");
/* 565 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(float value) throws IOException {
/* 580 */     writeDeferredName();
/* 581 */     if (this.strictness != Strictness.LENIENT && (Float.isNaN(value) || Float.isInfinite(value))) {
/* 582 */       throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
/*     */     }
/* 584 */     beforeValue();
/* 585 */     this.out.append(Float.toString(value));
/* 586 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(double value) throws IOException {
/* 600 */     writeDeferredName();
/* 601 */     if (this.strictness != Strictness.LENIENT && (Double.isNaN(value) || Double.isInfinite(value))) {
/* 602 */       throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
/*     */     }
/* 604 */     beforeValue();
/* 605 */     this.out.append(Double.toString(value));
/* 606 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(long value) throws IOException {
/* 616 */     writeDeferredName();
/* 617 */     beforeValue();
/* 618 */     this.out.write(Long.toString(value));
/* 619 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(Number value) throws IOException {
/* 635 */     if (value == null) {
/* 636 */       return nullValue();
/*     */     }
/*     */     
/* 639 */     writeDeferredName();
/* 640 */     String string = value.toString();
/* 641 */     Class<? extends Number> numberClass = (Class)value.getClass();
/*     */     
/* 643 */     if (!alwaysCreatesValidJsonNumber(numberClass))
/*     */     {
/* 645 */       if (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN")) {
/* 646 */         if (this.strictness != Strictness.LENIENT) {
/* 647 */           throw new IllegalArgumentException("Numeric values must be finite, but was " + string);
/*     */         }
/* 649 */       } else if (numberClass != Float.class && numberClass != Double.class && 
/*     */         
/* 651 */         !VALID_JSON_NUMBER_PATTERN.matcher(string).matches()) {
/* 652 */         throw new IllegalArgumentException("String created by " + numberClass + " is not a valid JSON number: " + string);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 657 */     beforeValue();
/* 658 */     this.out.append(string);
/* 659 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter nullValue() throws IOException {
/* 669 */     if (this.deferredName != null) {
/* 670 */       if (this.serializeNulls) {
/* 671 */         writeDeferredName();
/*     */       } else {
/* 673 */         this.deferredName = null;
/* 674 */         return this;
/*     */       } 
/*     */     }
/* 677 */     beforeValue();
/* 678 */     this.out.write("null");
/* 679 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter jsonValue(String value) throws IOException {
/* 694 */     if (value == null) {
/* 695 */       return nullValue();
/*     */     }
/* 697 */     writeDeferredName();
/* 698 */     beforeValue();
/* 699 */     this.out.append(value);
/* 700 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 708 */     if (this.stackSize == 0) {
/* 709 */       throw new IllegalStateException("JsonWriter is closed.");
/*     */     }
/* 711 */     this.out.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 721 */     this.out.close();
/*     */     
/* 723 */     int size = this.stackSize;
/* 724 */     if (size > 1 || (size == 1 && this.stack[size - 1] != 7)) {
/* 725 */       throw new IOException("Incomplete document");
/*     */     }
/* 727 */     this.stackSize = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean alwaysCreatesValidJsonNumber(Class<? extends Number> c) {
/* 734 */     return (c == Integer.class || c == Long.class || c == Byte.class || c == Short.class || c == BigDecimal.class || c == BigInteger.class || c == AtomicInteger.class || c == AtomicLong.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void string(String value) throws IOException {
/* 745 */     String[] replacements = this.htmlSafe ? HTML_SAFE_REPLACEMENT_CHARS : REPLACEMENT_CHARS;
/* 746 */     this.out.write(34);
/* 747 */     int last = 0;
/* 748 */     int length = value.length();
/* 749 */     for (int i = 0; i < length; i++) {
/* 750 */       String replacement; char c = value.charAt(i);
/*     */       
/* 752 */       if (c < '') {
/* 753 */         replacement = replacements[c];
/* 754 */         if (replacement == null) {
/*     */           continue;
/*     */         }
/* 757 */       } else if (c == ' ') {
/* 758 */         replacement = "\\u2028";
/* 759 */       } else if (c == ' ') {
/* 760 */         replacement = "\\u2029";
/*     */       } else {
/*     */         continue;
/*     */       } 
/* 764 */       if (last < i) {
/* 765 */         this.out.write(value, last, i - last);
/*     */       }
/* 767 */       this.out.write(replacement);
/* 768 */       last = i + 1; continue;
/*     */     } 
/* 770 */     if (last < length) {
/* 771 */       this.out.write(value, last, length - last);
/*     */     }
/* 773 */     this.out.write(34);
/*     */   }
/*     */   
/*     */   private void newline() throws IOException {
/* 777 */     if (this.usesEmptyNewlineAndIndent) {
/*     */       return;
/*     */     }
/*     */     
/* 781 */     this.out.write(this.formattingStyle.getNewline());
/* 782 */     for (int i = 1, size = this.stackSize; i < size; i++) {
/* 783 */       this.out.write(this.formattingStyle.getIndent());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void beforeName() throws IOException {
/* 792 */     int context = peek();
/* 793 */     if (context == 5) {
/* 794 */       this.out.write(this.formattedComma);
/* 795 */     } else if (context != 3) {
/* 796 */       throw new IllegalStateException("Nesting problem.");
/*     */     } 
/* 798 */     newline();
/* 799 */     replaceTop(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void beforeValue() throws IOException {
/* 808 */     switch (peek()) {
/*     */       case 7:
/* 810 */         if (this.strictness != Strictness.LENIENT) {
/* 811 */           throw new IllegalStateException("JSON must have only one top-level value.");
/*     */         }
/*     */       
/*     */       case 6:
/* 815 */         replaceTop(7);
/*     */         return;
/*     */       
/*     */       case 1:
/* 819 */         replaceTop(2);
/* 820 */         newline();
/*     */         return;
/*     */       
/*     */       case 2:
/* 824 */         this.out.append(this.formattedComma);
/* 825 */         newline();
/*     */         return;
/*     */       
/*     */       case 4:
/* 829 */         this.out.append(this.formattedColon);
/* 830 */         replaceTop(5);
/*     */         return;
/*     */     } 
/*     */     
/* 834 */     throw new IllegalStateException("Nesting problem.");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\stream\JsonWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */