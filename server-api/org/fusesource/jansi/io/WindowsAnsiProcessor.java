/*     */ package org.fusesource.jansi.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.fusesource.jansi.internal.Kernel32;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WindowsAnsiProcessor
/*     */   extends AnsiProcessor
/*     */ {
/*     */   private final long console;
/*     */   private static final short FOREGROUND_BLACK = 0;
/*  60 */   private static final short FOREGROUND_YELLOW = (short)(Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN);
/*  61 */   private static final short FOREGROUND_MAGENTA = (short)(Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_RED);
/*  62 */   private static final short FOREGROUND_CYAN = (short)(Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_GREEN);
/*  63 */   private static final short FOREGROUND_WHITE = (short)(Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN | Kernel32.FOREGROUND_BLUE);
/*     */   
/*     */   private static final short BACKGROUND_BLACK = 0;
/*  66 */   private static final short BACKGROUND_YELLOW = (short)(Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN);
/*  67 */   private static final short BACKGROUND_MAGENTA = (short)(Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_RED);
/*  68 */   private static final short BACKGROUND_CYAN = (short)(Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_GREEN);
/*  69 */   private static final short BACKGROUND_WHITE = (short)(Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN | Kernel32.BACKGROUND_BLUE);
/*     */   
/*  71 */   private static final short[] ANSI_FOREGROUND_COLOR_MAP = new short[] { 0, Kernel32.FOREGROUND_RED, Kernel32.FOREGROUND_GREEN, FOREGROUND_YELLOW, Kernel32.FOREGROUND_BLUE, FOREGROUND_MAGENTA, FOREGROUND_CYAN, FOREGROUND_WHITE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private static final short[] ANSI_BACKGROUND_COLOR_MAP = new short[] { 0, Kernel32.BACKGROUND_RED, Kernel32.BACKGROUND_GREEN, BACKGROUND_YELLOW, Kernel32.BACKGROUND_BLUE, BACKGROUND_MAGENTA, BACKGROUND_CYAN, BACKGROUND_WHITE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/*     */   
/*     */   private final short originalColors;
/*     */   private boolean negative;
/*  97 */   private short savedX = -1;
/*  98 */   private short savedY = -1;
/*     */   
/*     */   public WindowsAnsiProcessor(OutputStream ps, long console) throws IOException {
/* 101 */     super(ps);
/* 102 */     this.console = console;
/* 103 */     getConsoleInfo();
/* 104 */     this.originalColors = this.info.attributes;
/*     */   }
/*     */   
/*     */   public WindowsAnsiProcessor(OutputStream ps, boolean stdout) throws IOException {
/* 108 */     this(ps, Kernel32.GetStdHandle(stdout ? Kernel32.STD_OUTPUT_HANDLE : Kernel32.STD_ERROR_HANDLE));
/*     */   }
/*     */   
/*     */   public WindowsAnsiProcessor(OutputStream ps) throws IOException {
/* 112 */     this(ps, true);
/*     */   }
/*     */   
/*     */   private void getConsoleInfo() throws IOException {
/* 116 */     this.os.flush();
/* 117 */     if (Kernel32.GetConsoleScreenBufferInfo(this.console, this.info) == 0) {
/* 118 */       throw new IOException("Could not get the screen info: " + Kernel32.getLastErrorMessage());
/*     */     }
/* 120 */     if (this.negative) {
/* 121 */       this.info.attributes = invertAttributeColors(this.info.attributes);
/*     */     }
/*     */   }
/*     */   
/*     */   private void applyAttribute() throws IOException {
/* 126 */     this.os.flush();
/* 127 */     short attributes = this.info.attributes;
/* 128 */     if (this.negative) {
/* 129 */       attributes = invertAttributeColors(attributes);
/*     */     }
/* 131 */     if (Kernel32.SetConsoleTextAttribute(this.console, attributes) == 0) {
/* 132 */       throw new IOException(Kernel32.getLastErrorMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private short invertAttributeColors(short attributes) {
/* 138 */     int fg = 0xF & attributes;
/* 139 */     fg <<= 4;
/* 140 */     int bg = 0xF0 & attributes;
/* 141 */     bg >>= 4;
/* 142 */     attributes = (short)(attributes & 0xFF00 | fg | bg);
/* 143 */     return attributes;
/*     */   }
/*     */   
/*     */   private void applyCursorPosition() throws IOException {
/* 147 */     if (Kernel32.SetConsoleCursorPosition(this.console, this.info.cursorPosition.copy()) == 0)
/* 148 */       throw new IOException(Kernel32.getLastErrorMessage()); 
/*     */   } protected void processEraseScreen(int eraseOption) throws IOException {
/*     */     Kernel32.COORD topLeft;
/*     */     int screenLength;
/*     */     Kernel32.COORD topLeft2;
/*     */     int lengthToCursor, lengthToEnd;
/* 154 */     getConsoleInfo();
/* 155 */     int[] written = new int[1];
/* 156 */     switch (eraseOption) {
/*     */       case 2:
/* 158 */         topLeft = new Kernel32.COORD();
/* 159 */         topLeft.x = 0;
/* 160 */         topLeft.y = this.info.window.top;
/* 161 */         screenLength = this.info.window.height() * this.info.size.x;
/* 162 */         Kernel32.FillConsoleOutputAttribute(this.console, this.info.attributes, screenLength, topLeft, written);
/* 163 */         Kernel32.FillConsoleOutputCharacterW(this.console, ' ', screenLength, topLeft, written);
/*     */         break;
/*     */       case 1:
/* 166 */         topLeft2 = new Kernel32.COORD();
/* 167 */         topLeft2.x = 0;
/* 168 */         topLeft2.y = this.info.window.top;
/* 169 */         lengthToCursor = (this.info.cursorPosition.y - this.info.window.top) * this.info.size.x + this.info.cursorPosition.x;
/* 170 */         Kernel32.FillConsoleOutputAttribute(this.console, this.info.attributes, lengthToCursor, topLeft2, written);
/* 171 */         Kernel32.FillConsoleOutputCharacterW(this.console, ' ', lengthToCursor, topLeft2, written);
/*     */         break;
/*     */       case 0:
/* 174 */         lengthToEnd = (this.info.window.bottom - this.info.cursorPosition.y) * this.info.size.x + this.info.size.x - this.info.cursorPosition.x;
/*     */         
/* 176 */         Kernel32.FillConsoleOutputAttribute(this.console, this.info.attributes, lengthToEnd, this.info.cursorPosition.copy(), written);
/* 177 */         Kernel32.FillConsoleOutputCharacterW(this.console, ' ', lengthToEnd, this.info.cursorPosition.copy(), written);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processEraseLine(int eraseOption) throws IOException {
/*     */     Kernel32.COORD leftColCurrRow, leftColCurrRow2;
/*     */     int lengthToLastCol;
/* 186 */     getConsoleInfo();
/* 187 */     int[] written = new int[1];
/* 188 */     switch (eraseOption) {
/*     */       case 2:
/* 190 */         leftColCurrRow = this.info.cursorPosition.copy();
/* 191 */         leftColCurrRow.x = 0;
/* 192 */         Kernel32.FillConsoleOutputAttribute(this.console, this.info.attributes, this.info.size.x, leftColCurrRow, written);
/* 193 */         Kernel32.FillConsoleOutputCharacterW(this.console, ' ', this.info.size.x, leftColCurrRow, written);
/*     */         break;
/*     */       case 1:
/* 196 */         leftColCurrRow2 = this.info.cursorPosition.copy();
/* 197 */         leftColCurrRow2.x = 0;
/* 198 */         Kernel32.FillConsoleOutputAttribute(this.console, this.info.attributes, this.info.cursorPosition.x, leftColCurrRow2, written);
/* 199 */         Kernel32.FillConsoleOutputCharacterW(this.console, ' ', this.info.cursorPosition.x, leftColCurrRow2, written);
/*     */         break;
/*     */       case 0:
/* 202 */         lengthToLastCol = this.info.size.x - this.info.cursorPosition.x;
/* 203 */         Kernel32.FillConsoleOutputAttribute(this.console, this.info.attributes, lengthToLastCol, this.info.cursorPosition
/* 204 */             .copy(), written);
/* 205 */         Kernel32.FillConsoleOutputCharacterW(this.console, ' ', lengthToLastCol, this.info.cursorPosition.copy(), written);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorLeft(int count) throws IOException {
/* 214 */     getConsoleInfo();
/* 215 */     this.info.cursorPosition.x = (short)Math.max(0, this.info.cursorPosition.x - count);
/* 216 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorRight(int count) throws IOException {
/* 221 */     getConsoleInfo();
/* 222 */     this.info.cursorPosition.x = (short)Math.min(this.info.window.width(), this.info.cursorPosition.x + count);
/* 223 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorDown(int count) throws IOException {
/* 228 */     getConsoleInfo();
/* 229 */     this.info.cursorPosition.y = (short)Math.min(Math.max(0, this.info.size.y - 1), this.info.cursorPosition.y + count);
/* 230 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorUp(int count) throws IOException {
/* 235 */     getConsoleInfo();
/* 236 */     this.info.cursorPosition.y = (short)Math.max(this.info.window.top, this.info.cursorPosition.y - count);
/* 237 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorTo(int row, int col) throws IOException {
/* 242 */     getConsoleInfo();
/* 243 */     this.info.cursorPosition.y = (short)Math.max(this.info.window.top, Math.min(this.info.size.y, this.info.window.top + row - 1));
/* 244 */     this.info.cursorPosition.x = (short)Math.max(0, Math.min(this.info.window.width(), col - 1));
/* 245 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorToColumn(int x) throws IOException {
/* 250 */     getConsoleInfo();
/* 251 */     this.info.cursorPosition.x = (short)Math.max(0, Math.min(this.info.window.width(), x - 1));
/* 252 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorUpLine(int count) throws IOException {
/* 257 */     getConsoleInfo();
/* 258 */     this.info.cursorPosition.x = 0;
/* 259 */     this.info.cursorPosition.y = (short)Math.max(this.info.window.top, this.info.cursorPosition.y - count);
/* 260 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorDownLine(int count) throws IOException {
/* 265 */     getConsoleInfo();
/* 266 */     this.info.cursorPosition.x = 0;
/* 267 */     this.info.cursorPosition.y = (short)Math.max(this.info.window.top, this.info.cursorPosition.y + count);
/* 268 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColor(int color, boolean bright) throws IOException {
/* 273 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFFF8 | ANSI_FOREGROUND_COLOR_MAP[color]);
/* 274 */     if (bright) {
/* 275 */       this.info.attributes = (short)(this.info.attributes | Kernel32.FOREGROUND_INTENSITY);
/*     */     }
/* 277 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColorExt(int paletteIndex) throws IOException {
/* 282 */     int round = Colors.roundColor(paletteIndex, 16);
/* 283 */     processSetForegroundColor((round >= 8) ? (round - 8) : round, (round >= 8));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColorExt(int r, int g, int b) throws IOException {
/* 288 */     int round = Colors.roundRgbColor(r, g, b, 16);
/* 289 */     processSetForegroundColor((round >= 8) ? (round - 8) : round, (round >= 8));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColor(int color, boolean bright) throws IOException {
/* 294 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFF8F | ANSI_BACKGROUND_COLOR_MAP[color]);
/* 295 */     if (bright) {
/* 296 */       this.info.attributes = (short)(this.info.attributes | Kernel32.BACKGROUND_INTENSITY);
/*     */     }
/* 298 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColorExt(int paletteIndex) throws IOException {
/* 303 */     int round = Colors.roundColor(paletteIndex, 16);
/* 304 */     processSetBackgroundColor((round >= 8) ? (round - 8) : round, (round >= 8));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColorExt(int r, int g, int b) throws IOException {
/* 309 */     int round = Colors.roundRgbColor(r, g, b, 16);
/* 310 */     processSetBackgroundColor((round >= 8) ? (round - 8) : round, (round >= 8));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processDefaultTextColor() throws IOException {
/* 315 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFFF0 | this.originalColors & 0xF);
/* 316 */     this.info.attributes = (short)(this.info.attributes & (Kernel32.FOREGROUND_INTENSITY ^ 0xFFFFFFFF));
/* 317 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processDefaultBackgroundColor() throws IOException {
/* 322 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFF0F | this.originalColors & 0xF0);
/* 323 */     this.info.attributes = (short)(this.info.attributes & (Kernel32.BACKGROUND_INTENSITY ^ 0xFFFFFFFF));
/* 324 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processAttributeReset() throws IOException {
/* 329 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFF00 | this.originalColors);
/* 330 */     this.negative = false;
/* 331 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetAttribute(int attribute) throws IOException {
/* 336 */     switch (attribute) {
/*     */       case 1:
/* 338 */         this.info.attributes = (short)(this.info.attributes | Kernel32.FOREGROUND_INTENSITY);
/* 339 */         applyAttribute();
/*     */         break;
/*     */       case 22:
/* 342 */         this.info.attributes = (short)(this.info.attributes & (Kernel32.FOREGROUND_INTENSITY ^ 0xFFFFFFFF));
/* 343 */         applyAttribute();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 349 */         this.info.attributes = (short)(this.info.attributes | Kernel32.BACKGROUND_INTENSITY);
/* 350 */         applyAttribute();
/*     */         break;
/*     */       case 24:
/* 353 */         this.info.attributes = (short)(this.info.attributes & (Kernel32.BACKGROUND_INTENSITY ^ 0xFFFFFFFF));
/* 354 */         applyAttribute();
/*     */         break;
/*     */       
/*     */       case 7:
/* 358 */         this.negative = true;
/* 359 */         applyAttribute();
/*     */         break;
/*     */       case 27:
/* 362 */         this.negative = false;
/* 363 */         applyAttribute();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSaveCursorPosition() throws IOException {
/* 372 */     getConsoleInfo();
/* 373 */     this.savedX = this.info.cursorPosition.x;
/* 374 */     this.savedY = this.info.cursorPosition.y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processRestoreCursorPosition() throws IOException {
/* 380 */     if (this.savedX != -1 && this.savedY != -1) {
/* 381 */       this.os.flush();
/* 382 */       this.info.cursorPosition.x = this.savedX;
/* 383 */       this.info.cursorPosition.y = this.savedY;
/* 384 */       applyCursorPosition();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processInsertLine(int optionInt) throws IOException {
/* 390 */     getConsoleInfo();
/* 391 */     Kernel32.SMALL_RECT scroll = this.info.window.copy();
/* 392 */     scroll.top = this.info.cursorPosition.y;
/* 393 */     Kernel32.COORD org = new Kernel32.COORD();
/* 394 */     org.x = 0;
/* 395 */     org.y = (short)(this.info.cursorPosition.y + optionInt);
/* 396 */     Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO();
/* 397 */     info.attributes = this.originalColors;
/* 398 */     info.unicodeChar = ' ';
/* 399 */     if (Kernel32.ScrollConsoleScreenBuffer(this.console, scroll, scroll, org, info) == 0) {
/* 400 */       throw new IOException(Kernel32.getLastErrorMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processDeleteLine(int optionInt) throws IOException {
/* 406 */     getConsoleInfo();
/* 407 */     Kernel32.SMALL_RECT scroll = this.info.window.copy();
/* 408 */     scroll.top = this.info.cursorPosition.y;
/* 409 */     Kernel32.COORD org = new Kernel32.COORD();
/* 410 */     org.x = 0;
/* 411 */     org.y = (short)(this.info.cursorPosition.y - optionInt);
/* 412 */     Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO();
/* 413 */     info.attributes = this.originalColors;
/* 414 */     info.unicodeChar = ' ';
/* 415 */     if (Kernel32.ScrollConsoleScreenBuffer(this.console, scroll, scroll, org, info) == 0) {
/* 416 */       throw new IOException(Kernel32.getLastErrorMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processChangeWindowTitle(String label) {
/* 422 */     Kernel32.SetConsoleTitle(label);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\io\WindowsAnsiProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */