/*     */ package org.jline.terminal.impl.jna.win;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
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
/*     */ 
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
/*     */   private static final short FOREGROUND_BLACK = 0;
/*     */   private static final short FOREGROUND_YELLOW = 6;
/*     */   private static final short FOREGROUND_MAGENTA = 5;
/*     */   private static final short FOREGROUND_CYAN = 3;
/*     */   private static final short FOREGROUND_WHITE = 7;
/*     */   private static final short BACKGROUND_BLACK = 0;
/*     */   private static final short BACKGROUND_YELLOW = 96;
/*     */   private static final short BACKGROUND_MAGENTA = 80;
/*     */   private static final short BACKGROUND_CYAN = 48;
/*     */   private static final short BACKGROUND_WHITE = 112;
/*  51 */   private static final short[] ANSI_FOREGROUND_COLOR_MAP = new short[] { 0, 4, 2, 6, 1, 5, 3, 7 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final short[] ANSI_BACKGROUND_COLOR_MAP = new short[] { 0, 64, 32, 96, 16, 80, 48, 112 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_ESCAPE_SEQUENCE_LENGTH = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Pointer console;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/*     */   
/*     */   private final short originalColors;
/*     */   private boolean negative;
/*     */   private boolean bold;
/*     */   private boolean underline;
/*  83 */   private short savedX = -1;
/*  84 */   private short savedY = -1;
/*     */   
/*     */   public WindowsAnsiWriter(Writer out, Pointer console) throws IOException {
/*  87 */     super(out);
/*  88 */     this.console = console;
/*  89 */     getConsoleInfo();
/*  90 */     this.originalColors = this.info.wAttributes;
/*     */   }
/*     */   
/*     */   private void getConsoleInfo() throws IOException {
/*  94 */     this.out.flush();
/*  95 */     Kernel32.INSTANCE.GetConsoleScreenBufferInfo(this.console, this.info);
/*  96 */     if (this.negative) {
/*  97 */       this.info.wAttributes = invertAttributeColors(this.info.wAttributes);
/*     */     }
/*     */   }
/*     */   
/*     */   private void applyAttribute() throws IOException {
/* 102 */     this.out.flush();
/* 103 */     short attributes = this.info.wAttributes;
/*     */     
/* 105 */     if (this.bold) {
/* 106 */       attributes = (short)(attributes | 0x8);
/*     */     }
/*     */     
/* 109 */     if (this.underline) {
/* 110 */       attributes = (short)(attributes | 0x80);
/*     */     }
/* 112 */     if (this.negative) {
/* 113 */       attributes = invertAttributeColors(attributes);
/*     */     }
/* 115 */     Kernel32.INSTANCE.SetConsoleTextAttribute(this.console, attributes);
/*     */   }
/*     */ 
/*     */   
/*     */   private short invertAttributeColors(short attributes) {
/* 120 */     int fg = 0xF & attributes;
/* 121 */     fg <<= 4;
/* 122 */     int bg = 0xF0 & attributes;
/* 123 */     bg >>= 4;
/* 124 */     attributes = (short)(attributes & 0xFF00 | fg | bg);
/* 125 */     return attributes;
/*     */   }
/*     */   
/*     */   private void applyCursorPosition() throws IOException {
/* 129 */     this.info.dwCursorPosition.X = (short)Math.max(0, Math.min(this.info.dwSize.X - 1, this.info.dwCursorPosition.X));
/* 130 */     this.info.dwCursorPosition.Y = (short)Math.max(0, Math.min(this.info.dwSize.Y - 1, this.info.dwCursorPosition.Y));
/* 131 */     Kernel32.INSTANCE.SetConsoleCursorPosition(this.console, this.info.dwCursorPosition); } protected void processEraseScreen(int eraseOption) throws IOException { Kernel32.COORD topLeft;
/*     */     int screenLength;
/*     */     Kernel32.COORD topLeft2;
/*     */     int lengthToCursor, lengthToEnd;
/* 135 */     getConsoleInfo();
/* 136 */     IntByReference written = new IntByReference();
/* 137 */     switch (eraseOption) {
/*     */       case 2:
/* 139 */         topLeft = new Kernel32.COORD();
/* 140 */         topLeft.X = 0;
/* 141 */         topLeft.Y = this.info.srWindow.Top;
/* 142 */         screenLength = this.info.srWindow.height() * this.info.dwSize.X;
/* 143 */         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', screenLength, topLeft, written);
/* 144 */         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, screenLength, topLeft, written);
/*     */         break;
/*     */       case 1:
/* 147 */         topLeft2 = new Kernel32.COORD();
/* 148 */         topLeft2.X = 0;
/* 149 */         topLeft2.Y = this.info.srWindow.Top;
/* 150 */         lengthToCursor = (this.info.dwCursorPosition.Y - this.info.srWindow.Top) * this.info.dwSize.X + this.info.dwCursorPosition.X;
/*     */         
/* 152 */         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', lengthToCursor, topLeft2, written);
/* 153 */         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, lengthToCursor, topLeft2, written);
/*     */         break;
/*     */       
/*     */       case 0:
/* 157 */         lengthToEnd = (this.info.srWindow.Bottom - this.info.dwCursorPosition.Y) * this.info.dwSize.X + this.info.dwSize.X - this.info.dwCursorPosition.X;
/*     */         
/* 159 */         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', lengthToEnd, this.info.dwCursorPosition, written);
/* 160 */         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, lengthToEnd, this.info.dwCursorPosition, written);
/*     */         break;
/*     */     }  }
/*     */    protected void processEraseLine(int eraseOption) throws IOException {
/*     */     Kernel32.COORD leftColCurrRow, leftColCurrRow2;
/*     */     int lengthToLastCol;
/* 166 */     getConsoleInfo();
/* 167 */     IntByReference written = new IntByReference();
/* 168 */     switch (eraseOption) {
/*     */       case 2:
/* 170 */         leftColCurrRow = new Kernel32.COORD((short)0, this.info.dwCursorPosition.Y);
/* 171 */         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', this.info.dwSize.X, leftColCurrRow, written);
/* 172 */         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, this.info.dwSize.X, leftColCurrRow, written);
/*     */         break;
/*     */       
/*     */       case 1:
/* 176 */         leftColCurrRow2 = new Kernel32.COORD((short)0, this.info.dwCursorPosition.Y);
/* 177 */         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', this.info.dwCursorPosition.X, leftColCurrRow2, written);
/*     */         
/* 179 */         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, this.info.dwCursorPosition.X, leftColCurrRow2, written);
/*     */         break;
/*     */       
/*     */       case 0:
/* 183 */         lengthToLastCol = this.info.dwSize.X - this.info.dwCursorPosition.X;
/* 184 */         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', lengthToLastCol, this.info.dwCursorPosition, written);
/*     */         
/* 186 */         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, lengthToLastCol, this.info.dwCursorPosition, written);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processCursorUpLine(int count) throws IOException {
/* 192 */     getConsoleInfo();
/* 193 */     this.info.dwCursorPosition.X = 0;
/* 194 */     this.info.dwCursorPosition.Y = (short)(this.info.dwCursorPosition.Y - (short)count);
/* 195 */     applyCursorPosition();
/*     */   }
/*     */   
/*     */   protected void processCursorDownLine(int count) throws IOException {
/* 199 */     getConsoleInfo();
/* 200 */     this.info.dwCursorPosition.X = 0;
/* 201 */     this.info.dwCursorPosition.Y = (short)(this.info.dwCursorPosition.Y + (short)count);
/* 202 */     applyCursorPosition();
/*     */   }
/*     */   
/*     */   protected void processCursorLeft(int count) throws IOException {
/* 206 */     getConsoleInfo();
/* 207 */     this.info.dwCursorPosition.X = (short)(this.info.dwCursorPosition.X - (short)count);
/* 208 */     applyCursorPosition();
/*     */   }
/*     */   
/*     */   protected void processCursorRight(int count) throws IOException {
/* 212 */     getConsoleInfo();
/* 213 */     this.info.dwCursorPosition.X = (short)(this.info.dwCursorPosition.X + (short)count);
/* 214 */     applyCursorPosition();
/*     */   }
/*     */   
/*     */   protected void processCursorDown(int count) throws IOException {
/* 218 */     getConsoleInfo();
/* 219 */     int nb = Math.max(0, this.info.dwCursorPosition.Y + count - this.info.dwSize.Y + 1);
/* 220 */     if (nb != count) {
/* 221 */       this.info.dwCursorPosition.Y = (short)(this.info.dwCursorPosition.Y + (short)count);
/* 222 */       applyCursorPosition();
/*     */     } 
/* 224 */     if (nb > 0) {
/* 225 */       Kernel32.SMALL_RECT scroll = new Kernel32.SMALL_RECT(this.info.srWindow);
/* 226 */       scroll.Top = 0;
/* 227 */       Kernel32.COORD org = new Kernel32.COORD();
/* 228 */       org.X = 0;
/* 229 */       org.Y = (short)-nb;
/* 230 */       Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO(' ', this.originalColors);
/* 231 */       Kernel32.INSTANCE.ScrollConsoleScreenBuffer(this.console, scroll, scroll, org, info);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processCursorUp(int count) throws IOException {
/* 236 */     getConsoleInfo();
/* 237 */     this.info.dwCursorPosition.Y = (short)(this.info.dwCursorPosition.Y - (short)count);
/* 238 */     applyCursorPosition();
/*     */   }
/*     */   
/*     */   protected void processCursorTo(int row, int col) throws IOException {
/* 242 */     getConsoleInfo();
/* 243 */     this.info.dwCursorPosition.Y = (short)(this.info.srWindow.Top + row - 1);
/* 244 */     this.info.dwCursorPosition.X = (short)(col - 1);
/* 245 */     applyCursorPosition();
/*     */   }
/*     */   
/*     */   protected void processCursorToColumn(int x) throws IOException {
/* 249 */     getConsoleInfo();
/* 250 */     this.info.dwCursorPosition.X = (short)(x - 1);
/* 251 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColorExt(int paletteIndex) throws IOException {
/* 256 */     int color = Colors.roundColor(paletteIndex, 16);
/* 257 */     this.info.wAttributes = (short)(this.info.wAttributes & 0xFFFFFFF8 | ANSI_FOREGROUND_COLOR_MAP[color & 0x7]);
/* 258 */     this.info
/* 259 */       .wAttributes = (short)(this.info.wAttributes & 0xFFFFFFF7 | ((color >= 8) ? 8 : 0));
/* 260 */     applyAttribute();
/*     */   }
/*     */   
/*     */   protected void processSetBackgroundColorExt(int paletteIndex) throws IOException {
/* 264 */     int color = Colors.roundColor(paletteIndex, 16);
/* 265 */     this.info.wAttributes = (short)(this.info.wAttributes & 0xFFFFFF8F | ANSI_BACKGROUND_COLOR_MAP[color & 0x7]);
/* 266 */     this.info
/* 267 */       .wAttributes = (short)(this.info.wAttributes & 0xFFFFFF7F | ((color >= 8) ? 128 : 0));
/* 268 */     applyAttribute();
/*     */   }
/*     */   
/*     */   protected void processDefaultTextColor() throws IOException {
/* 272 */     this.info.wAttributes = (short)(this.info.wAttributes & 0xFFFFFFF0 | this.originalColors & 0xF);
/* 273 */     applyAttribute();
/*     */   }
/*     */   
/*     */   protected void processDefaultBackgroundColor() throws IOException {
/* 277 */     this.info.wAttributes = (short)(this.info.wAttributes & 0xFFFFFF0F | this.originalColors & 0xF0);
/* 278 */     applyAttribute();
/*     */   }
/*     */   
/*     */   protected void processAttributeRest() throws IOException {
/* 282 */     this.info.wAttributes = (short)(this.info.wAttributes & 0xFFFFFF00 | this.originalColors);
/* 283 */     this.negative = false;
/* 284 */     this.bold = false;
/* 285 */     this.underline = false;
/* 286 */     applyAttribute();
/*     */   }
/*     */   
/*     */   protected void processSetAttribute(int attribute) throws IOException {
/* 290 */     switch (attribute) {
/*     */       case 1:
/* 292 */         this.bold = true;
/* 293 */         applyAttribute();
/*     */         break;
/*     */       case 22:
/* 296 */         this.bold = false;
/* 297 */         applyAttribute();
/*     */         break;
/*     */       
/*     */       case 4:
/* 301 */         this.underline = true;
/* 302 */         applyAttribute();
/*     */         break;
/*     */       case 24:
/* 305 */         this.underline = false;
/* 306 */         applyAttribute();
/*     */         break;
/*     */       
/*     */       case 7:
/* 310 */         this.negative = true;
/* 311 */         applyAttribute();
/*     */         break;
/*     */       case 27:
/* 314 */         this.negative = false;
/* 315 */         applyAttribute();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processSaveCursorPosition() throws IOException {
/* 321 */     getConsoleInfo();
/* 322 */     this.savedX = this.info.dwCursorPosition.X;
/* 323 */     this.savedY = this.info.dwCursorPosition.Y;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processRestoreCursorPosition() throws IOException {
/* 328 */     if (this.savedX != -1 && this.savedY != -1) {
/* 329 */       this.out.flush();
/* 330 */       this.info.dwCursorPosition.X = this.savedX;
/* 331 */       this.info.dwCursorPosition.Y = this.savedY;
/* 332 */       applyCursorPosition();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processInsertLine(int optionInt) throws IOException {
/* 338 */     getConsoleInfo();
/* 339 */     Kernel32.SMALL_RECT scroll = new Kernel32.SMALL_RECT(this.info.srWindow);
/* 340 */     scroll.Top = this.info.dwCursorPosition.Y;
/* 341 */     Kernel32.COORD org = new Kernel32.COORD();
/* 342 */     org.X = 0;
/* 343 */     org.Y = (short)(this.info.dwCursorPosition.Y + optionInt);
/* 344 */     Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO(' ', this.originalColors);
/* 345 */     Kernel32.INSTANCE.ScrollConsoleScreenBuffer(this.console, scroll, scroll, org, info);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processDeleteLine(int optionInt) throws IOException {
/* 350 */     getConsoleInfo();
/* 351 */     Kernel32.SMALL_RECT scroll = new Kernel32.SMALL_RECT(this.info.srWindow);
/* 352 */     scroll.Top = this.info.dwCursorPosition.Y;
/* 353 */     Kernel32.COORD org = new Kernel32.COORD();
/* 354 */     org.X = 0;
/* 355 */     org.Y = (short)(this.info.dwCursorPosition.Y - optionInt);
/* 356 */     Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO(' ', this.originalColors);
/* 357 */     Kernel32.INSTANCE.ScrollConsoleScreenBuffer(this.console, scroll, scroll, org, info);
/*     */   }
/*     */   
/*     */   protected void processChangeWindowTitle(String label) {
/* 361 */     Kernel32.INSTANCE.SetConsoleTitle(label);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\win\WindowsAnsiWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */