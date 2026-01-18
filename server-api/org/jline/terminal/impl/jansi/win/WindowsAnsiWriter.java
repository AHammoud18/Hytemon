/*     */ package org.jline.terminal.impl.jansi.win;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import org.fusesource.jansi.internal.Kernel32;
/*     */ import org.jline.utils.AnsiWriter;
/*     */ import org.jline.utils.Colors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WindowsAnsiWriter
/*     */   extends AnsiWriter
/*     */ {
/*  30 */   private static final long console = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
/*     */   
/*     */   private static final short FOREGROUND_BLACK = 0;
/*  33 */   private static final short FOREGROUND_YELLOW = (short)(Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN);
/*  34 */   private static final short FOREGROUND_MAGENTA = (short)(Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_RED);
/*  35 */   private static final short FOREGROUND_CYAN = (short)(Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_GREEN);
/*  36 */   private static final short FOREGROUND_WHITE = (short)(Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN | Kernel32.FOREGROUND_BLUE);
/*     */   
/*     */   private static final short BACKGROUND_BLACK = 0;
/*  39 */   private static final short BACKGROUND_YELLOW = (short)(Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN);
/*  40 */   private static final short BACKGROUND_MAGENTA = (short)(Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_RED);
/*  41 */   private static final short BACKGROUND_CYAN = (short)(Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_GREEN);
/*  42 */   private static final short BACKGROUND_WHITE = (short)(Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN | Kernel32.BACKGROUND_BLUE);
/*     */   
/*  44 */   private static final short[] ANSI_FOREGROUND_COLOR_MAP = new short[] { 0, Kernel32.FOREGROUND_RED, Kernel32.FOREGROUND_GREEN, FOREGROUND_YELLOW, Kernel32.FOREGROUND_BLUE, FOREGROUND_MAGENTA, FOREGROUND_CYAN, FOREGROUND_WHITE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final short[] ANSI_BACKGROUND_COLOR_MAP = new short[] { 0, Kernel32.BACKGROUND_RED, Kernel32.BACKGROUND_GREEN, BACKGROUND_YELLOW, Kernel32.BACKGROUND_BLUE, BACKGROUND_MAGENTA, BACKGROUND_CYAN, BACKGROUND_WHITE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/*     */   
/*     */   private final short originalColors;
/*     */   private boolean negative;
/*     */   private boolean bold;
/*     */   private boolean underline;
/*  72 */   private short savedX = -1;
/*  73 */   private short savedY = -1;
/*     */   
/*     */   public WindowsAnsiWriter(Writer out) throws IOException {
/*  76 */     super(out);
/*  77 */     getConsoleInfo();
/*  78 */     this.originalColors = this.info.attributes;
/*     */   }
/*     */   
/*     */   private void getConsoleInfo() throws IOException {
/*  82 */     this.out.flush();
/*  83 */     if (Kernel32.GetConsoleScreenBufferInfo(console, this.info) == 0) {
/*  84 */       throw new IOException("Could not get the screen info: " + WindowsSupport.getLastErrorMessage());
/*     */     }
/*  86 */     if (this.negative) {
/*  87 */       this.info.attributes = invertAttributeColors(this.info.attributes);
/*     */     }
/*     */   }
/*     */   
/*     */   private void applyAttribute() throws IOException {
/*  92 */     this.out.flush();
/*  93 */     short attributes = this.info.attributes;
/*     */     
/*  95 */     if (this.bold) {
/*  96 */       attributes = (short)(attributes | Kernel32.FOREGROUND_INTENSITY);
/*     */     }
/*     */     
/*  99 */     if (this.underline) {
/* 100 */       attributes = (short)(attributes | Kernel32.BACKGROUND_INTENSITY);
/*     */     }
/* 102 */     if (this.negative) {
/* 103 */       attributes = invertAttributeColors(attributes);
/*     */     }
/* 105 */     if (Kernel32.SetConsoleTextAttribute(console, attributes) == 0) {
/* 106 */       throw new IOException(WindowsSupport.getLastErrorMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private short invertAttributeColors(short attributes) {
/* 112 */     int fg = 0xF & attributes;
/* 113 */     fg <<= 4;
/* 114 */     int bg = 0xF0 & attributes;
/* 115 */     bg >>= 4;
/* 116 */     attributes = (short)(attributes & 0xFF00 | fg | bg);
/* 117 */     return attributes;
/*     */   }
/*     */   
/*     */   private void applyCursorPosition() throws IOException {
/* 121 */     this.info.cursorPosition.x = (short)Math.max(0, Math.min(this.info.size.x - 1, this.info.cursorPosition.x));
/* 122 */     this.info.cursorPosition.y = (short)Math.max(0, Math.min(this.info.size.y - 1, this.info.cursorPosition.y));
/* 123 */     if (Kernel32.SetConsoleCursorPosition(console, this.info.cursorPosition.copy()) == 0)
/* 124 */       throw new IOException(WindowsSupport.getLastErrorMessage()); 
/*     */   } protected void processEraseScreen(int eraseOption) throws IOException {
/*     */     Kernel32.COORD topLeft;
/*     */     int screenLength;
/*     */     Kernel32.COORD topLeft2;
/*     */     int lengthToCursor, lengthToEnd;
/* 130 */     getConsoleInfo();
/* 131 */     int[] written = new int[1];
/* 132 */     switch (eraseOption) {
/*     */       case 2:
/* 134 */         topLeft = new Kernel32.COORD();
/* 135 */         topLeft.x = 0;
/* 136 */         topLeft.y = this.info.window.top;
/* 137 */         screenLength = this.info.window.height() * this.info.size.x;
/* 138 */         Kernel32.FillConsoleOutputAttribute(console, this.originalColors, screenLength, topLeft, written);
/* 139 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', screenLength, topLeft, written);
/*     */         break;
/*     */       case 1:
/* 142 */         topLeft2 = new Kernel32.COORD();
/* 143 */         topLeft2.x = 0;
/* 144 */         topLeft2.y = this.info.window.top;
/* 145 */         lengthToCursor = (this.info.cursorPosition.y - this.info.window.top) * this.info.size.x + this.info.cursorPosition.x;
/* 146 */         Kernel32.FillConsoleOutputAttribute(console, this.originalColors, lengthToCursor, topLeft2, written);
/* 147 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', lengthToCursor, topLeft2, written);
/*     */         break;
/*     */       case 0:
/* 150 */         lengthToEnd = (this.info.window.bottom - this.info.cursorPosition.y) * this.info.size.x + this.info.size.x - this.info.cursorPosition.x;
/*     */         
/* 152 */         Kernel32.FillConsoleOutputAttribute(console, this.originalColors, lengthToEnd, this.info.cursorPosition.copy(), written);
/* 153 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', lengthToEnd, this.info.cursorPosition.copy(), written);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processEraseLine(int eraseOption) throws IOException {
/*     */     Kernel32.COORD leftColCurrRow, leftColCurrRow2;
/*     */     int lengthToLastCol;
/* 162 */     getConsoleInfo();
/* 163 */     int[] written = new int[1];
/* 164 */     switch (eraseOption) {
/*     */       case 2:
/* 166 */         leftColCurrRow = this.info.cursorPosition.copy();
/* 167 */         leftColCurrRow.x = 0;
/* 168 */         Kernel32.FillConsoleOutputAttribute(console, this.originalColors, this.info.size.x, leftColCurrRow, written);
/* 169 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', this.info.size.x, leftColCurrRow, written);
/*     */         break;
/*     */       case 1:
/* 172 */         leftColCurrRow2 = this.info.cursorPosition.copy();
/* 173 */         leftColCurrRow2.x = 0;
/* 174 */         Kernel32.FillConsoleOutputAttribute(console, this.originalColors, this.info.cursorPosition.x, leftColCurrRow2, written);
/* 175 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', this.info.cursorPosition.x, leftColCurrRow2, written);
/*     */         break;
/*     */       case 0:
/* 178 */         lengthToLastCol = this.info.size.x - this.info.cursorPosition.x;
/* 179 */         Kernel32.FillConsoleOutputAttribute(console, this.originalColors, lengthToLastCol, this.info.cursorPosition
/* 180 */             .copy(), written);
/* 181 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', lengthToLastCol, this.info.cursorPosition.copy(), written);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorUpLine(int count) throws IOException {
/* 189 */     getConsoleInfo();
/* 190 */     this.info.cursorPosition.x = 0;
/* 191 */     this.info.cursorPosition.y = (short)(this.info.cursorPosition.y - (short)count);
/* 192 */     applyCursorPosition();
/*     */   }
/*     */   
/*     */   protected void processCursorDownLine(int count) throws IOException {
/* 196 */     getConsoleInfo();
/* 197 */     this.info.cursorPosition.x = 0;
/* 198 */     this.info.cursorPosition.y = (short)(this.info.cursorPosition.y + (short)count);
/* 199 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorLeft(int count) throws IOException {
/* 204 */     getConsoleInfo();
/* 205 */     this.info.cursorPosition.x = (short)(this.info.cursorPosition.x - (short)count);
/* 206 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorRight(int count) throws IOException {
/* 211 */     getConsoleInfo();
/* 212 */     this.info.cursorPosition.x = (short)(this.info.cursorPosition.x + (short)count);
/* 213 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorDown(int count) throws IOException {
/* 218 */     getConsoleInfo();
/* 219 */     int nb = Math.max(0, this.info.cursorPosition.y + count - this.info.size.y + 1);
/* 220 */     if (nb != count) {
/* 221 */       this.info.cursorPosition.y = (short)(this.info.cursorPosition.y + (short)count);
/* 222 */       applyCursorPosition();
/*     */     } 
/* 224 */     if (nb > 0) {
/* 225 */       Kernel32.SMALL_RECT scroll = this.info.window.copy();
/* 226 */       scroll.top = 0;
/* 227 */       Kernel32.COORD org = new Kernel32.COORD();
/* 228 */       org.x = 0;
/* 229 */       org.y = (short)-nb;
/* 230 */       Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO();
/* 231 */       info.unicodeChar = ' ';
/* 232 */       info.attributes = this.originalColors;
/* 233 */       Kernel32.ScrollConsoleScreenBuffer(console, scroll, scroll, org, info);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorUp(int count) throws IOException {
/* 239 */     getConsoleInfo();
/* 240 */     this.info.cursorPosition.y = (short)(this.info.cursorPosition.y - (short)count);
/* 241 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorTo(int row, int col) throws IOException {
/* 246 */     getConsoleInfo();
/* 247 */     this.info.cursorPosition.y = (short)(this.info.window.top + row - 1);
/* 248 */     this.info.cursorPosition.x = (short)(col - 1);
/* 249 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorToColumn(int x) throws IOException {
/* 254 */     getConsoleInfo();
/* 255 */     this.info.cursorPosition.x = (short)(x - 1);
/* 256 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColorExt(int paletteIndex) throws IOException {
/* 261 */     int color = Colors.roundColor(paletteIndex, 16);
/* 262 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFFF8 | ANSI_FOREGROUND_COLOR_MAP[color & 0x7]);
/* 263 */     this.info.attributes = (short)(this.info.attributes & (Kernel32.FOREGROUND_INTENSITY ^ 0xFFFFFFFF) | ((color >= 8) ? Kernel32.FOREGROUND_INTENSITY : 0));
/* 264 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColorExt(int paletteIndex) throws IOException {
/* 269 */     int color = Colors.roundColor(paletteIndex, 16);
/* 270 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFF8F | ANSI_BACKGROUND_COLOR_MAP[color & 0x7]);
/* 271 */     this.info.attributes = (short)(this.info.attributes & (Kernel32.BACKGROUND_INTENSITY ^ 0xFFFFFFFF) | ((color >= 8) ? Kernel32.BACKGROUND_INTENSITY : 0));
/* 272 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processDefaultTextColor() throws IOException {
/* 277 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFFF0 | this.originalColors & 0xF);
/* 278 */     this.info.attributes = (short)(this.info.attributes & (Kernel32.FOREGROUND_INTENSITY ^ 0xFFFFFFFF));
/* 279 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processDefaultBackgroundColor() throws IOException {
/* 284 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFF0F | this.originalColors & 0xF0);
/* 285 */     this.info.attributes = (short)(this.info.attributes & (Kernel32.BACKGROUND_INTENSITY ^ 0xFFFFFFFF));
/* 286 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processAttributeRest() throws IOException {
/* 291 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFF00 | this.originalColors);
/* 292 */     this.negative = false;
/* 293 */     this.bold = false;
/* 294 */     this.underline = false;
/* 295 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetAttribute(int attribute) throws IOException {
/* 300 */     switch (attribute) {
/*     */       case 1:
/* 302 */         this.bold = true;
/* 303 */         applyAttribute();
/*     */         break;
/*     */       case 22:
/* 306 */         this.bold = false;
/* 307 */         applyAttribute();
/*     */         break;
/*     */       
/*     */       case 4:
/* 311 */         this.underline = true;
/* 312 */         applyAttribute();
/*     */         break;
/*     */       case 24:
/* 315 */         this.underline = false;
/* 316 */         applyAttribute();
/*     */         break;
/*     */       
/*     */       case 7:
/* 320 */         this.negative = true;
/* 321 */         applyAttribute();
/*     */         break;
/*     */       case 27:
/* 324 */         this.negative = false;
/* 325 */         applyAttribute();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSaveCursorPosition() throws IOException {
/* 334 */     getConsoleInfo();
/* 335 */     this.savedX = this.info.cursorPosition.x;
/* 336 */     this.savedY = this.info.cursorPosition.y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processRestoreCursorPosition() throws IOException {
/* 342 */     if (this.savedX != -1 && this.savedY != -1) {
/* 343 */       this.out.flush();
/* 344 */       this.info.cursorPosition.x = this.savedX;
/* 345 */       this.info.cursorPosition.y = this.savedY;
/* 346 */       applyCursorPosition();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processInsertLine(int optionInt) throws IOException {
/* 352 */     getConsoleInfo();
/* 353 */     Kernel32.SMALL_RECT scroll = this.info.window.copy();
/* 354 */     scroll.top = this.info.cursorPosition.y;
/* 355 */     Kernel32.COORD org = new Kernel32.COORD();
/* 356 */     org.x = 0;
/* 357 */     org.y = (short)(this.info.cursorPosition.y + optionInt);
/* 358 */     Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO();
/* 359 */     info.attributes = this.originalColors;
/* 360 */     info.unicodeChar = ' ';
/* 361 */     if (Kernel32.ScrollConsoleScreenBuffer(console, scroll, scroll, org, info) == 0) {
/* 362 */       throw new IOException(WindowsSupport.getLastErrorMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processDeleteLine(int optionInt) throws IOException {
/* 368 */     getConsoleInfo();
/* 369 */     Kernel32.SMALL_RECT scroll = this.info.window.copy();
/* 370 */     scroll.top = this.info.cursorPosition.y;
/* 371 */     Kernel32.COORD org = new Kernel32.COORD();
/* 372 */     org.x = 0;
/* 373 */     org.y = (short)(this.info.cursorPosition.y - optionInt);
/* 374 */     Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO();
/* 375 */     info.attributes = this.originalColors;
/* 376 */     info.unicodeChar = ' ';
/* 377 */     if (Kernel32.ScrollConsoleScreenBuffer(console, scroll, scroll, org, info) == 0) {
/* 378 */       throw new IOException(WindowsSupport.getLastErrorMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processChangeWindowTitle(String title) {
/* 384 */     Kernel32.SetConsoleTitle(title);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jansi\win\WindowsAnsiWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */