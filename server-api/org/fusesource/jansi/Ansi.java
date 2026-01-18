/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ansi
/*     */   implements Appendable
/*     */ {
/*     */   private static final char FIRST_ESC_CHAR = '\033';
/*     */   private static final char SECOND_ESC_CHAR = '[';
/*     */   
/*     */   public enum Color
/*     */   {
/*  36 */     BLACK(0, "BLACK"),
/*  37 */     RED(1, "RED"),
/*  38 */     GREEN(2, "GREEN"),
/*  39 */     YELLOW(3, "YELLOW"),
/*  40 */     BLUE(4, "BLUE"),
/*  41 */     MAGENTA(5, "MAGENTA"),
/*  42 */     CYAN(6, "CYAN"),
/*  43 */     WHITE(7, "WHITE"),
/*  44 */     DEFAULT(9, "DEFAULT");
/*     */     
/*     */     private final int value;
/*     */     private final String name;
/*     */     
/*     */     Color(int index, String name) {
/*  50 */       this.value = index;
/*  51 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  56 */       return this.name;
/*     */     }
/*     */     
/*     */     public int value() {
/*  60 */       return this.value;
/*     */     }
/*     */     
/*     */     public int fg() {
/*  64 */       return this.value + 30;
/*     */     }
/*     */     
/*     */     public int bg() {
/*  68 */       return this.value + 40;
/*     */     }
/*     */     
/*     */     public int fgBright() {
/*  72 */       return this.value + 90;
/*     */     }
/*     */     
/*     */     public int bgBright() {
/*  76 */       return this.value + 100;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Attribute
/*     */   {
/*  86 */     RESET(0, "RESET"),
/*  87 */     INTENSITY_BOLD(1, "INTENSITY_BOLD"),
/*  88 */     INTENSITY_FAINT(2, "INTENSITY_FAINT"),
/*  89 */     ITALIC(3, "ITALIC_ON"),
/*  90 */     UNDERLINE(4, "UNDERLINE_ON"),
/*  91 */     BLINK_SLOW(5, "BLINK_SLOW"),
/*  92 */     BLINK_FAST(6, "BLINK_FAST"),
/*  93 */     NEGATIVE_ON(7, "NEGATIVE_ON"),
/*  94 */     CONCEAL_ON(8, "CONCEAL_ON"),
/*  95 */     STRIKETHROUGH_ON(9, "STRIKETHROUGH_ON"),
/*  96 */     UNDERLINE_DOUBLE(21, "UNDERLINE_DOUBLE"),
/*  97 */     INTENSITY_BOLD_OFF(22, "INTENSITY_BOLD_OFF"),
/*  98 */     ITALIC_OFF(23, "ITALIC_OFF"),
/*  99 */     UNDERLINE_OFF(24, "UNDERLINE_OFF"),
/* 100 */     BLINK_OFF(25, "BLINK_OFF"),
/* 101 */     NEGATIVE_OFF(27, "NEGATIVE_OFF"),
/* 102 */     CONCEAL_OFF(28, "CONCEAL_OFF"),
/* 103 */     STRIKETHROUGH_OFF(29, "STRIKETHROUGH_OFF");
/*     */     
/*     */     private final int value;
/*     */     private final String name;
/*     */     
/*     */     Attribute(int index, String name) {
/* 109 */       this.value = index;
/* 110 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 115 */       return this.name;
/*     */     }
/*     */     
/*     */     public int value() {
/* 119 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Erase
/*     */   {
/* 130 */     FORWARD(0, "FORWARD"),
/* 131 */     BACKWARD(1, "BACKWARD"),
/* 132 */     ALL(2, "ALL");
/*     */     
/*     */     private final int value;
/*     */     private final String name;
/*     */     
/*     */     Erase(int index, String name) {
/* 138 */       this.value = index;
/* 139 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 144 */       return this.name;
/*     */     }
/*     */     
/*     */     public int value() {
/* 148 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static final String DISABLE = Ansi.class.getName() + ".disable";
/*     */   
/*     */   private static Callable<Boolean> detector = () -> Boolean.valueOf(!Boolean.getBoolean(DISABLE));
/*     */   
/*     */   public static void setDetector(Callable<Boolean> detector) {
/* 162 */     if (detector == null) throw new IllegalArgumentException(); 
/* 163 */     Ansi.detector = detector;
/*     */   }
/*     */   
/*     */   public static boolean isDetected() {
/*     */     try {
/* 168 */       return ((Boolean)detector.call()).booleanValue();
/* 169 */     } catch (Exception e) {
/* 170 */       return true;
/*     */     } 
/*     */   }
/*     */   
/* 174 */   private static final InheritableThreadLocal<Boolean> holder = new InheritableThreadLocal<Boolean>()
/*     */     {
/*     */       protected Boolean initialValue() {
/* 177 */         return Boolean.valueOf(Ansi.isDetected());
/*     */       }
/*     */     };
/*     */   
/*     */   public static void setEnabled(boolean flag) {
/* 182 */     holder.set(Boolean.valueOf(flag));
/*     */   }
/*     */   private final StringBuilder builder;
/*     */   public static boolean isEnabled() {
/* 186 */     return ((Boolean)holder.get()).booleanValue();
/*     */   }
/*     */   
/*     */   public static Ansi ansi() {
/* 190 */     if (isEnabled()) {
/* 191 */       return new Ansi();
/*     */     }
/* 193 */     return new NoAnsi();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ansi ansi(StringBuilder builder) {
/* 198 */     if (isEnabled()) {
/* 199 */       return new Ansi(builder);
/*     */     }
/* 201 */     return new NoAnsi(builder);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ansi ansi(int size) {
/* 206 */     if (isEnabled()) {
/* 207 */       return new Ansi(size);
/*     */     }
/* 209 */     return new NoAnsi(size);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class NoAnsi
/*     */     extends Ansi
/*     */   {
/*     */     public NoAnsi() {}
/*     */     
/*     */     public NoAnsi(int size) {
/* 219 */       super(size);
/*     */     }
/*     */     
/*     */     public NoAnsi(StringBuilder builder) {
/* 223 */       super(builder);
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi fg(Ansi.Color color) {
/* 228 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi bg(Ansi.Color color) {
/* 233 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi fgBright(Ansi.Color color) {
/* 238 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi bgBright(Ansi.Color color) {
/* 243 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi fg(int color) {
/* 248 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi fgRgb(int r, int g, int b) {
/* 253 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi bg(int color) {
/* 258 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi bgRgb(int r, int g, int b) {
/* 263 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi a(Ansi.Attribute attribute) {
/* 268 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursor(int row, int column) {
/* 273 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorToColumn(int x) {
/* 278 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorUp(int y) {
/* 283 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorRight(int x) {
/* 288 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorDown(int y) {
/* 293 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorLeft(int x) {
/* 298 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorDownLine() {
/* 303 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorDownLine(int n) {
/* 308 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorUpLine() {
/* 313 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorUpLine(int n) {
/* 318 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi eraseScreen() {
/* 323 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi eraseScreen(Ansi.Erase kind) {
/* 328 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi eraseLine() {
/* 333 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi eraseLine(Ansi.Erase kind) {
/* 338 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi scrollUp(int rows) {
/* 343 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi scrollDown(int rows) {
/* 348 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi saveCursorPosition() {
/* 353 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Ansi restorCursorPosition() {
/* 359 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi restoreCursorPosition() {
/* 364 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi reset() {
/* 369 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 374 */   private final ArrayList<Integer> attributeOptions = new ArrayList<>(5);
/*     */   
/*     */   public Ansi() {
/* 377 */     this(new StringBuilder(80));
/*     */   }
/*     */   
/*     */   public Ansi(Ansi parent) {
/* 381 */     this(new StringBuilder(parent.builder));
/* 382 */     this.attributeOptions.addAll(parent.attributeOptions);
/*     */   }
/*     */   
/*     */   public Ansi(int size) {
/* 386 */     this(new StringBuilder(size));
/*     */   }
/*     */   
/*     */   public Ansi(StringBuilder builder) {
/* 390 */     this.builder = builder;
/*     */   }
/*     */   
/*     */   public Ansi fg(Color color) {
/* 394 */     this.attributeOptions.add(Integer.valueOf(color.fg()));
/* 395 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi fg(int color) {
/* 399 */     this.attributeOptions.add(Integer.valueOf(38));
/* 400 */     this.attributeOptions.add(Integer.valueOf(5));
/* 401 */     this.attributeOptions.add(Integer.valueOf(color & 0xFF));
/* 402 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi fgRgb(int color) {
/* 406 */     return fgRgb(color >> 16, color >> 8, color);
/*     */   }
/*     */   
/*     */   public Ansi fgRgb(int r, int g, int b) {
/* 410 */     this.attributeOptions.add(Integer.valueOf(38));
/* 411 */     this.attributeOptions.add(Integer.valueOf(2));
/* 412 */     this.attributeOptions.add(Integer.valueOf(r & 0xFF));
/* 413 */     this.attributeOptions.add(Integer.valueOf(g & 0xFF));
/* 414 */     this.attributeOptions.add(Integer.valueOf(b & 0xFF));
/* 415 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi fgBlack() {
/* 419 */     return fg(Color.BLACK);
/*     */   }
/*     */   
/*     */   public Ansi fgBlue() {
/* 423 */     return fg(Color.BLUE);
/*     */   }
/*     */   
/*     */   public Ansi fgCyan() {
/* 427 */     return fg(Color.CYAN);
/*     */   }
/*     */   
/*     */   public Ansi fgDefault() {
/* 431 */     return fg(Color.DEFAULT);
/*     */   }
/*     */   
/*     */   public Ansi fgGreen() {
/* 435 */     return fg(Color.GREEN);
/*     */   }
/*     */   
/*     */   public Ansi fgMagenta() {
/* 439 */     return fg(Color.MAGENTA);
/*     */   }
/*     */   
/*     */   public Ansi fgRed() {
/* 443 */     return fg(Color.RED);
/*     */   }
/*     */   
/*     */   public Ansi fgYellow() {
/* 447 */     return fg(Color.YELLOW);
/*     */   }
/*     */   
/*     */   public Ansi bg(Color color) {
/* 451 */     this.attributeOptions.add(Integer.valueOf(color.bg()));
/* 452 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi bg(int color) {
/* 456 */     this.attributeOptions.add(Integer.valueOf(48));
/* 457 */     this.attributeOptions.add(Integer.valueOf(5));
/* 458 */     this.attributeOptions.add(Integer.valueOf(color & 0xFF));
/* 459 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi bgRgb(int color) {
/* 463 */     return bgRgb(color >> 16, color >> 8, color);
/*     */   }
/*     */   
/*     */   public Ansi bgRgb(int r, int g, int b) {
/* 467 */     this.attributeOptions.add(Integer.valueOf(48));
/* 468 */     this.attributeOptions.add(Integer.valueOf(2));
/* 469 */     this.attributeOptions.add(Integer.valueOf(r & 0xFF));
/* 470 */     this.attributeOptions.add(Integer.valueOf(g & 0xFF));
/* 471 */     this.attributeOptions.add(Integer.valueOf(b & 0xFF));
/* 472 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi bgCyan() {
/* 476 */     return bg(Color.CYAN);
/*     */   }
/*     */   
/*     */   public Ansi bgDefault() {
/* 480 */     return bg(Color.DEFAULT);
/*     */   }
/*     */   
/*     */   public Ansi bgGreen() {
/* 484 */     return bg(Color.GREEN);
/*     */   }
/*     */   
/*     */   public Ansi bgMagenta() {
/* 488 */     return bg(Color.MAGENTA);
/*     */   }
/*     */   
/*     */   public Ansi bgRed() {
/* 492 */     return bg(Color.RED);
/*     */   }
/*     */   
/*     */   public Ansi bgYellow() {
/* 496 */     return bg(Color.YELLOW);
/*     */   }
/*     */   
/*     */   public Ansi fgBright(Color color) {
/* 500 */     this.attributeOptions.add(Integer.valueOf(color.fgBright()));
/* 501 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi fgBrightBlack() {
/* 505 */     return fgBright(Color.BLACK);
/*     */   }
/*     */   
/*     */   public Ansi fgBrightBlue() {
/* 509 */     return fgBright(Color.BLUE);
/*     */   }
/*     */   
/*     */   public Ansi fgBrightCyan() {
/* 513 */     return fgBright(Color.CYAN);
/*     */   }
/*     */   
/*     */   public Ansi fgBrightDefault() {
/* 517 */     return fgBright(Color.DEFAULT);
/*     */   }
/*     */   
/*     */   public Ansi fgBrightGreen() {
/* 521 */     return fgBright(Color.GREEN);
/*     */   }
/*     */   
/*     */   public Ansi fgBrightMagenta() {
/* 525 */     return fgBright(Color.MAGENTA);
/*     */   }
/*     */   
/*     */   public Ansi fgBrightRed() {
/* 529 */     return fgBright(Color.RED);
/*     */   }
/*     */   
/*     */   public Ansi fgBrightYellow() {
/* 533 */     return fgBright(Color.YELLOW);
/*     */   }
/*     */   
/*     */   public Ansi bgBright(Color color) {
/* 537 */     this.attributeOptions.add(Integer.valueOf(color.bgBright()));
/* 538 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi bgBrightCyan() {
/* 542 */     return bgBright(Color.CYAN);
/*     */   }
/*     */   
/*     */   public Ansi bgBrightDefault() {
/* 546 */     return bgBright(Color.DEFAULT);
/*     */   }
/*     */   
/*     */   public Ansi bgBrightGreen() {
/* 550 */     return bgBright(Color.GREEN);
/*     */   }
/*     */   
/*     */   public Ansi bgBrightMagenta() {
/* 554 */     return bgBright(Color.MAGENTA);
/*     */   }
/*     */   
/*     */   public Ansi bgBrightRed() {
/* 558 */     return bgBright(Color.RED);
/*     */   }
/*     */   
/*     */   public Ansi bgBrightYellow() {
/* 562 */     return bgBright(Color.YELLOW);
/*     */   }
/*     */   
/*     */   public Ansi a(Attribute attribute) {
/* 566 */     this.attributeOptions.add(Integer.valueOf(attribute.value()));
/* 567 */     return this;
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
/*     */   public Ansi cursor(int row, int column) {
/* 579 */     return appendEscapeSequence('H', new Object[] { Integer.valueOf(Math.max(1, row)), Integer.valueOf(Math.max(1, column)) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi cursorToColumn(int x) {
/* 590 */     return appendEscapeSequence('G', Math.max(1, x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi cursorUp(int y) {
/* 600 */     return (y > 0) ? appendEscapeSequence('A', y) : ((y < 0) ? cursorDown(-y) : this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi cursorDown(int y) {
/* 610 */     return (y > 0) ? appendEscapeSequence('B', y) : ((y < 0) ? cursorUp(-y) : this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi cursorRight(int x) {
/* 620 */     return (x > 0) ? appendEscapeSequence('C', x) : ((x < 0) ? cursorLeft(-x) : this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi cursorLeft(int x) {
/* 630 */     return (x > 0) ? appendEscapeSequence('D', x) : ((x < 0) ? cursorRight(-x) : this);
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
/*     */   public Ansi cursorMove(int x, int y) {
/* 643 */     return cursorRight(x).cursorDown(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi cursorDownLine() {
/* 652 */     return appendEscapeSequence('E');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi cursorDownLine(int n) {
/* 663 */     return (n < 0) ? cursorUpLine(-n) : appendEscapeSequence('E', n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi cursorUpLine() {
/* 672 */     return appendEscapeSequence('F');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi cursorUpLine(int n) {
/* 683 */     return (n < 0) ? cursorDownLine(-n) : appendEscapeSequence('F', n);
/*     */   }
/*     */   
/*     */   public Ansi eraseScreen() {
/* 687 */     return appendEscapeSequence('J', Erase.ALL.value());
/*     */   }
/*     */   
/*     */   public Ansi eraseScreen(Erase kind) {
/* 691 */     return appendEscapeSequence('J', kind.value());
/*     */   }
/*     */   
/*     */   public Ansi eraseLine() {
/* 695 */     return appendEscapeSequence('K');
/*     */   }
/*     */   
/*     */   public Ansi eraseLine(Erase kind) {
/* 699 */     return appendEscapeSequence('K', kind.value());
/*     */   }
/*     */   
/*     */   public Ansi scrollUp(int rows) {
/* 703 */     if (rows == Integer.MIN_VALUE) {
/* 704 */       return scrollDown(2147483647);
/*     */     }
/* 706 */     return (rows > 0) ? appendEscapeSequence('S', rows) : ((rows < 0) ? scrollDown(-rows) : this);
/*     */   }
/*     */   
/*     */   public Ansi scrollDown(int rows) {
/* 710 */     if (rows == Integer.MIN_VALUE) {
/* 711 */       return scrollUp(2147483647);
/*     */     }
/* 713 */     return (rows > 0) ? appendEscapeSequence('T', rows) : ((rows < 0) ? scrollUp(-rows) : this);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Ansi restorCursorPosition() {
/* 718 */     return restoreCursorPosition();
/*     */   }
/*     */   
/*     */   public Ansi saveCursorPosition() {
/* 722 */     saveCursorPositionSCO();
/* 723 */     return saveCursorPositionDEC();
/*     */   }
/*     */ 
/*     */   
/*     */   public Ansi saveCursorPositionSCO() {
/* 728 */     return appendEscapeSequence('s');
/*     */   }
/*     */ 
/*     */   
/*     */   public Ansi saveCursorPositionDEC() {
/* 733 */     this.builder.append('\033');
/* 734 */     this.builder.append('7');
/* 735 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi restoreCursorPosition() {
/* 739 */     restoreCursorPositionSCO();
/* 740 */     return restoreCursorPositionDEC();
/*     */   }
/*     */ 
/*     */   
/*     */   public Ansi restoreCursorPositionSCO() {
/* 745 */     return appendEscapeSequence('u');
/*     */   }
/*     */ 
/*     */   
/*     */   public Ansi restoreCursorPositionDEC() {
/* 750 */     this.builder.append('\033');
/* 751 */     this.builder.append('8');
/* 752 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi reset() {
/* 756 */     return a(Attribute.RESET);
/*     */   }
/*     */   
/*     */   public Ansi bold() {
/* 760 */     return a(Attribute.INTENSITY_BOLD);
/*     */   }
/*     */   
/*     */   public Ansi boldOff() {
/* 764 */     return a(Attribute.INTENSITY_BOLD_OFF);
/*     */   }
/*     */   
/*     */   public Ansi a(String value) {
/* 768 */     flushAttributes();
/* 769 */     this.builder.append(value);
/* 770 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(boolean value) {
/* 774 */     flushAttributes();
/* 775 */     this.builder.append(value);
/* 776 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(char value) {
/* 780 */     flushAttributes();
/* 781 */     this.builder.append(value);
/* 782 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(char[] value, int offset, int len) {
/* 786 */     flushAttributes();
/* 787 */     this.builder.append(value, offset, len);
/* 788 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(char[] value) {
/* 792 */     flushAttributes();
/* 793 */     this.builder.append(value);
/* 794 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(CharSequence value, int start, int end) {
/* 798 */     flushAttributes();
/* 799 */     this.builder.append(value, start, end);
/* 800 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(CharSequence value) {
/* 804 */     flushAttributes();
/* 805 */     this.builder.append(value);
/* 806 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(double value) {
/* 810 */     flushAttributes();
/* 811 */     this.builder.append(value);
/* 812 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(float value) {
/* 816 */     flushAttributes();
/* 817 */     this.builder.append(value);
/* 818 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(int value) {
/* 822 */     flushAttributes();
/* 823 */     this.builder.append(value);
/* 824 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(long value) {
/* 828 */     flushAttributes();
/* 829 */     this.builder.append(value);
/* 830 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(Object value) {
/* 834 */     flushAttributes();
/* 835 */     this.builder.append(value);
/* 836 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(StringBuffer value) {
/* 840 */     flushAttributes();
/* 841 */     this.builder.append(value);
/* 842 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi newline() {
/* 846 */     flushAttributes();
/* 847 */     this.builder.append(System.getProperty("line.separator"));
/* 848 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi format(String pattern, Object... args) {
/* 852 */     flushAttributes();
/* 853 */     this.builder.append(String.format(pattern, args));
/* 854 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi apply(Consumer fun) {
/* 865 */     fun.apply(this);
/* 866 */     return this;
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
/*     */   public Ansi render(String text) {
/* 878 */     a(AnsiRenderer.render(text));
/* 879 */     return this;
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
/*     */   public Ansi render(String text, Object... args) {
/* 892 */     a(String.format(AnsiRenderer.render(text), args));
/* 893 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 898 */     flushAttributes();
/* 899 */     return this.builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Ansi appendEscapeSequence(char command) {
/* 907 */     flushAttributes();
/* 908 */     this.builder.append('\033');
/* 909 */     this.builder.append('[');
/* 910 */     this.builder.append(command);
/* 911 */     return this;
/*     */   }
/*     */   
/*     */   private Ansi appendEscapeSequence(char command, int option) {
/* 915 */     flushAttributes();
/* 916 */     this.builder.append('\033');
/* 917 */     this.builder.append('[');
/* 918 */     this.builder.append(option);
/* 919 */     this.builder.append(command);
/* 920 */     return this;
/*     */   }
/*     */   
/*     */   private Ansi appendEscapeSequence(char command, Object... options) {
/* 924 */     flushAttributes();
/* 925 */     return _appendEscapeSequence(command, options);
/*     */   }
/*     */   
/*     */   private void flushAttributes() {
/* 929 */     if (this.attributeOptions.isEmpty())
/* 930 */       return;  if (this.attributeOptions.size() == 1 && ((Integer)this.attributeOptions.get(0)).intValue() == 0) {
/* 931 */       this.builder.append('\033');
/* 932 */       this.builder.append('[');
/* 933 */       this.builder.append('m');
/*     */     } else {
/* 935 */       _appendEscapeSequence('m', this.attributeOptions.toArray());
/*     */     } 
/* 937 */     this.attributeOptions.clear();
/*     */   }
/*     */   
/*     */   private Ansi _appendEscapeSequence(char command, Object... options) {
/* 941 */     this.builder.append('\033');
/* 942 */     this.builder.append('[');
/* 943 */     int size = options.length;
/* 944 */     for (int i = 0; i < size; i++) {
/* 945 */       if (i != 0) {
/* 946 */         this.builder.append(';');
/*     */       }
/* 948 */       if (options[i] != null) {
/* 949 */         this.builder.append(options[i]);
/*     */       }
/*     */     } 
/* 952 */     this.builder.append(command);
/* 953 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ansi append(CharSequence csq) {
/* 958 */     this.builder.append(csq);
/* 959 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ansi append(CharSequence csq, int start, int end) {
/* 964 */     this.builder.append(csq, start, end);
/* 965 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ansi append(char c) {
/* 970 */     this.builder.append(c);
/* 971 */     return this;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Consumer {
/*     */     void apply(Ansi param1Ansi);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\Ansi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */