/*     */ package org.jline.terminal.impl.ffm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.lang.foreign.Arena;
/*     */ import java.lang.foreign.MemorySegment;
/*     */ import java.lang.foreign.ValueLayout;
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
/*     */ class WindowsAnsiWriter
/*     */   extends AnsiWriter
/*     */ {
/*  42 */   private static final MemorySegment console = Kernel32.GetStdHandle(-11);
/*     */   
/*     */   private static final short FOREGROUND_BLACK = 0;
/*     */   
/*     */   private static final short FOREGROUND_YELLOW = 6;
/*     */   
/*     */   private static final short FOREGROUND_MAGENTA = 5;
/*     */   private static final short FOREGROUND_CYAN = 3;
/*     */   private static final short FOREGROUND_WHITE = 7;
/*     */   private static final short BACKGROUND_BLACK = 0;
/*     */   private static final short BACKGROUND_YELLOW = 96;
/*     */   private static final short BACKGROUND_MAGENTA = 80;
/*     */   private static final short BACKGROUND_CYAN = 48;
/*     */   private static final short BACKGROUND_WHITE = 112;
/*  56 */   private static final short[] ANSI_FOREGROUND_COLOR_MAP = new short[] { 0, 4, 2, 6, 1, 5, 3, 7 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final short[] ANSI_BACKGROUND_COLOR_MAP = new short[] { 0, 64, 32, 96, 16, 80, 48, 112 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO(Arena.ofAuto());
/*     */   
/*     */   private final short originalColors;
/*     */   private boolean negative;
/*     */   private boolean bold;
/*     */   private boolean underline;
/*  84 */   private short savedX = -1;
/*  85 */   private short savedY = -1;
/*     */   
/*     */   public WindowsAnsiWriter(Writer out) throws IOException {
/*  88 */     super(out);
/*  89 */     getConsoleInfo();
/*  90 */     this.originalColors = this.info.attributes();
/*     */   }
/*     */   
/*     */   private void getConsoleInfo() throws IOException {
/*  94 */     this.out.flush();
/*  95 */     if (Kernel32.GetConsoleScreenBufferInfo(console, this.info) == 0) {
/*  96 */       throw new IOException("Could not get the screen info: " + Kernel32.getLastErrorMessage());
/*     */     }
/*  98 */     if (this.negative) {
/*  99 */       this.info.attributes(invertAttributeColors(this.info.attributes()));
/*     */     }
/*     */   }
/*     */   
/*     */   private void applyAttribute() throws IOException {
/* 104 */     this.out.flush();
/* 105 */     short attributes = this.info.attributes();
/*     */     
/* 107 */     if (this.bold) {
/* 108 */       attributes = (short)(attributes | 0x8);
/*     */     }
/*     */     
/* 111 */     if (this.underline) {
/* 112 */       attributes = (short)(attributes | 0x80);
/*     */     }
/* 114 */     if (this.negative) {
/* 115 */       attributes = invertAttributeColors(attributes);
/*     */     }
/* 117 */     if (Kernel32.SetConsoleTextAttribute(console, attributes) == 0) {
/* 118 */       throw new IOException(Kernel32.getLastErrorMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private short invertAttributeColors(short attributes) {
/* 124 */     int fg = 0xF & attributes;
/* 125 */     fg <<= 4;
/* 126 */     int bg = 0xF0 & attributes;
/* 127 */     bg >>= 4;
/* 128 */     attributes = (short)(attributes & 0xFF00 | fg | bg);
/* 129 */     return attributes;
/*     */   }
/*     */   
/*     */   private void applyCursorPosition() throws IOException {
/* 133 */     this.info.cursorPosition().x(
/* 134 */         (short)Math.max(0, Math.min(this.info.size().x() - 1, this.info.cursorPosition().x())));
/* 135 */     this.info.cursorPosition().y(
/* 136 */         (short)Math.max(0, Math.min(this.info.size().y() - 1, this.info.cursorPosition().y())));
/* 137 */     if (Kernel32.SetConsoleCursorPosition(console, this.info.cursorPosition()) == 0) {
/* 138 */       throw new IOException(Kernel32.getLastErrorMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processEraseScreen(int eraseOption) throws IOException {
/* 144 */     getConsoleInfo();
/* 145 */     Arena arena = Arena.ofConfined(); 
/* 146 */     try { Kernel32.COORD topLeft, topLeft2; int lengthToEnd, screenLength, lengthToCursor; MemorySegment written = arena.allocate(ValueLayout.JAVA_INT);
/* 147 */       switch (eraseOption) {
/*     */         case 2:
/* 149 */           topLeft = new Kernel32.COORD(arena, (short)0, this.info.window().top());
/* 150 */           screenLength = this.info.window().height() * this.info.size().x();
/* 151 */           Kernel32.FillConsoleOutputAttribute(console, this.originalColors, screenLength, topLeft, written);
/* 152 */           Kernel32.FillConsoleOutputCharacterW(console, ' ', screenLength, topLeft, written);
/*     */           break;
/*     */         case 1:
/* 155 */           topLeft2 = new Kernel32.COORD(arena, (short)0, this.info.window().top());
/*     */ 
/*     */ 
/*     */           
/* 159 */           lengthToCursor = (this.info.cursorPosition().y() - this.info.window().top()) * this.info.size().x() + this.info.cursorPosition().x();
/* 160 */           Kernel32.FillConsoleOutputAttribute(console, this.originalColors, lengthToCursor, topLeft2, written);
/* 161 */           Kernel32.FillConsoleOutputCharacterW(console, ' ', lengthToCursor, topLeft2, written);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 0:
/* 167 */           lengthToEnd = (this.info.window().bottom() - this.info.cursorPosition().y()) * this.info.size().x() + this.info.size().x() - this.info.cursorPosition().x();
/* 168 */           Kernel32.FillConsoleOutputAttribute(console, this.originalColors, lengthToEnd, this.info.cursorPosition(), written);
/* 169 */           Kernel32.FillConsoleOutputCharacterW(console, ' ', lengthToEnd, this.info.cursorPosition(), written);
/*     */           break;
/*     */       } 
/*     */       
/* 173 */       if (arena != null) arena.close();  }
/*     */     catch (Throwable throwable) { if (arena != null)
/*     */         try { arena.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 178 */      } protected void processEraseLine(int eraseOption) throws IOException { getConsoleInfo();
/* 179 */     Arena arena = Arena.ofConfined(); 
/* 180 */     try { Kernel32.COORD leftColCurrRow, leftColCurrRow2; int lengthToLastCol; MemorySegment written = arena.allocate(ValueLayout.JAVA_INT);
/* 181 */       switch (eraseOption) {
/*     */         
/*     */         case 2:
/* 184 */           leftColCurrRow = new Kernel32.COORD(arena, (short)0, this.info.cursorPosition().y());
/* 185 */           Kernel32.FillConsoleOutputAttribute(console, this.originalColors, this.info
/* 186 */               .size().x(), leftColCurrRow, written);
/* 187 */           Kernel32.FillConsoleOutputCharacterW(console, ' ', this.info.size().x(), leftColCurrRow, written);
/*     */           break;
/*     */         
/*     */         case 1:
/* 191 */           leftColCurrRow2 = new Kernel32.COORD(arena, (short)0, this.info.cursorPosition().y());
/* 192 */           Kernel32.FillConsoleOutputAttribute(console, this.originalColors, this.info
/* 193 */               .cursorPosition().x(), leftColCurrRow2, written);
/* 194 */           Kernel32.FillConsoleOutputCharacterW(console, ' ', this.info
/* 195 */               .cursorPosition().x(), leftColCurrRow2, written);
/*     */           break;
/*     */         
/*     */         case 0:
/* 199 */           lengthToLastCol = this.info.size().x() - this.info.cursorPosition().x();
/* 200 */           Kernel32.FillConsoleOutputAttribute(console, this.originalColors, lengthToLastCol, this.info
/* 201 */               .cursorPosition(), written);
/* 202 */           Kernel32.FillConsoleOutputCharacterW(console, ' ', lengthToLastCol, this.info.cursorPosition(), written);
/*     */           break;
/*     */       } 
/*     */       
/* 206 */       if (arena != null) arena.close();  } catch (Throwable throwable) { if (arena != null)
/*     */         try { arena.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 210 */      } protected void processCursorUpLine(int count) throws IOException { getConsoleInfo();
/* 211 */     this.info.cursorPosition().x((short)0);
/* 212 */     this.info.cursorPosition().y((short)(this.info.cursorPosition().y() - count));
/* 213 */     applyCursorPosition(); }
/*     */ 
/*     */   
/*     */   protected void processCursorDownLine(int count) throws IOException {
/* 217 */     getConsoleInfo();
/* 218 */     this.info.cursorPosition().x((short)0);
/* 219 */     this.info.cursorPosition().y((short)(this.info.cursorPosition().y() + count));
/* 220 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorLeft(int count) throws IOException {
/* 225 */     getConsoleInfo();
/* 226 */     this.info.cursorPosition().x((short)(this.info.cursorPosition().x() - count));
/* 227 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorRight(int count) throws IOException {
/* 232 */     getConsoleInfo();
/* 233 */     this.info.cursorPosition().x((short)(this.info.cursorPosition().x() + count));
/* 234 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorDown(int count) throws IOException {
/* 239 */     getConsoleInfo();
/* 240 */     int nb = Math.max(0, this.info.cursorPosition().y() + count - this.info.size().y() + 1);
/* 241 */     if (nb != count) {
/* 242 */       this.info.cursorPosition().y((short)(this.info.cursorPosition().y() + count));
/* 243 */       applyCursorPosition();
/*     */     } 
/* 245 */     if (nb > 0) {
/* 246 */       Arena arena = Arena.ofConfined(); 
/* 247 */       try { Kernel32.SMALL_RECT scroll = new Kernel32.SMALL_RECT(arena, this.info.window());
/* 248 */         scroll.top((short)0);
/* 249 */         Kernel32.COORD org = new Kernel32.COORD(arena);
/* 250 */         org.x((short)0);
/* 251 */         org.y((short)-nb);
/* 252 */         Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO(arena, ' ', this.originalColors);
/* 253 */         Kernel32.ScrollConsoleScreenBuffer(console, scroll, scroll, org, info);
/* 254 */         if (arena != null) arena.close();  }
/*     */       catch (Throwable throwable) { if (arena != null)
/*     */           try { arena.close(); }
/*     */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/*     */     
/* 260 */     }  } protected void processCursorUp(int count) throws IOException { getConsoleInfo();
/* 261 */     this.info.cursorPosition().y((short)(this.info.cursorPosition().y() - count));
/* 262 */     applyCursorPosition(); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorTo(int row, int col) throws IOException {
/* 267 */     getConsoleInfo();
/* 268 */     this.info.cursorPosition().y((short)(this.info.window().top() + row - 1));
/* 269 */     this.info.cursorPosition().x((short)(col - 1));
/* 270 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorToColumn(int x) throws IOException {
/* 275 */     getConsoleInfo();
/* 276 */     this.info.cursorPosition().x((short)(x - 1));
/* 277 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColorExt(int paletteIndex) throws IOException {
/* 282 */     int color = Colors.roundColor(paletteIndex, 16);
/* 283 */     this.info.attributes((short)(this.info.attributes() & 0xFFFFFFF8 | ANSI_FOREGROUND_COLOR_MAP[color & 0x7]));
/* 284 */     this.info.attributes(
/* 285 */         (short)(this.info.attributes() & 0xFFFFFFF7 | ((color >= 8) ? 8 : 0)));
/* 286 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColorExt(int paletteIndex) throws IOException {
/* 291 */     int color = Colors.roundColor(paletteIndex, 16);
/* 292 */     this.info.attributes((short)(this.info.attributes() & 0xFFFFFF8F | ANSI_BACKGROUND_COLOR_MAP[color & 0x7]));
/* 293 */     this.info.attributes(
/* 294 */         (short)(this.info.attributes() & 0xFFFFFF7F | ((color >= 8) ? 128 : 0)));
/* 295 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processDefaultTextColor() throws IOException {
/* 300 */     this.info.attributes((short)(this.info.attributes() & 0xFFFFFFF0 | this.originalColors & 0xF));
/* 301 */     this.info.attributes((short)(this.info.attributes() & 0xFFFFFFF7));
/* 302 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processDefaultBackgroundColor() throws IOException {
/* 307 */     this.info.attributes((short)(this.info.attributes() & 0xFFFFFF0F | this.originalColors & 0xF0));
/* 308 */     this.info.attributes((short)(this.info.attributes() & 0xFFFFFF7F));
/* 309 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processAttributeRest() throws IOException {
/* 314 */     this.info.attributes((short)(this.info.attributes() & 0xFFFFFF00 | this.originalColors));
/* 315 */     this.negative = false;
/* 316 */     this.bold = false;
/* 317 */     this.underline = false;
/* 318 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetAttribute(int attribute) throws IOException {
/* 323 */     switch (attribute) {
/*     */       case 1:
/* 325 */         this.bold = true;
/* 326 */         applyAttribute();
/*     */         break;
/*     */       case 22:
/* 329 */         this.bold = false;
/* 330 */         applyAttribute();
/*     */         break;
/*     */       case 4:
/* 333 */         this.underline = true;
/* 334 */         applyAttribute();
/*     */         break;
/*     */       case 24:
/* 337 */         this.underline = false;
/* 338 */         applyAttribute();
/*     */         break;
/*     */       case 7:
/* 341 */         this.negative = true;
/* 342 */         applyAttribute();
/*     */         break;
/*     */       case 27:
/* 345 */         this.negative = false;
/* 346 */         applyAttribute();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSaveCursorPosition() throws IOException {
/* 354 */     getConsoleInfo();
/* 355 */     this.savedX = this.info.cursorPosition().x();
/* 356 */     this.savedY = this.info.cursorPosition().y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processRestoreCursorPosition() throws IOException {
/* 362 */     if (this.savedX != -1 && this.savedY != -1) {
/* 363 */       this.out.flush();
/* 364 */       this.info.cursorPosition().x(this.savedX);
/* 365 */       this.info.cursorPosition().y(this.savedY);
/* 366 */       applyCursorPosition();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processInsertLine(int optionInt) throws IOException {
/* 372 */     Arena arena = Arena.ofConfined(); 
/* 373 */     try { getConsoleInfo();
/* 374 */       Kernel32.SMALL_RECT scroll = this.info.window().copy(arena);
/* 375 */       scroll.top(this.info.cursorPosition().y());
/*     */       
/* 377 */       Kernel32.COORD org = new Kernel32.COORD(arena, (short)0, (short)(this.info.cursorPosition().y() + optionInt));
/* 378 */       Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO(arena, ' ', this.originalColors);
/* 379 */       if (Kernel32.ScrollConsoleScreenBuffer(console, scroll, scroll, org, info) == 0) {
/* 380 */         throw new IOException(Kernel32.getLastErrorMessage());
/*     */       }
/* 382 */       if (arena != null) arena.close();  }
/*     */     catch (Throwable throwable) { if (arena != null)
/*     */         try { arena.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 387 */      } protected void processDeleteLine(int optionInt) throws IOException { Arena arena = Arena.ofConfined(); 
/* 388 */     try { getConsoleInfo();
/* 389 */       Kernel32.SMALL_RECT scroll = this.info.window().copy(arena);
/* 390 */       scroll.top(this.info.cursorPosition().y());
/*     */       
/* 392 */       Kernel32.COORD org = new Kernel32.COORD(arena, (short)0, (short)(this.info.cursorPosition().y() - optionInt));
/* 393 */       Kernel32.CHAR_INFO info = new Kernel32.CHAR_INFO(arena, ' ', this.originalColors);
/* 394 */       if (Kernel32.ScrollConsoleScreenBuffer(console, scroll, scroll, org, info) == 0) {
/* 395 */         throw new IOException(Kernel32.getLastErrorMessage());
/*     */       }
/* 397 */       if (arena != null) arena.close();  }
/*     */     catch (Throwable throwable) { if (arena != null)
/*     */         try { arena.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 402 */      } protected void processChangeWindowTitle(String title) { Arena session = Arena.ofConfined(); try {
/* 403 */       MemorySegment str = session.allocateFrom(title);
/* 404 */       Kernel32.SetConsoleTitleW(str);
/* 405 */       if (session != null) session.close(); 
/*     */     } catch (Throwable throwable) {
/*     */       if (session != null)
/*     */         try {
/*     */           session.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }  
/*     */       throw throwable;
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\ffm\WindowsAnsiWriter.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */