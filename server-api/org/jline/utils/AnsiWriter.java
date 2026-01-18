/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.FilterWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnsiWriter
/*     */   extends FilterWriter
/*     */ {
/*     */   private static final int MAX_ESCAPE_SEQUENCE_LENGTH = 100;
/*     */   private final char[] buffer;
/*     */   private int pos;
/*     */   private int startOfValue;
/*     */   private final ArrayList<Object> options;
/*     */   private static final int LOOKING_FOR_FIRST_ESC_CHAR = 0;
/*     */   private static final int LOOKING_FOR_SECOND_ESC_CHAR = 1;
/*     */   private static final int LOOKING_FOR_NEXT_ARG = 2;
/*     */   private static final int LOOKING_FOR_STR_ARG_END = 3;
/*     */   private static final int LOOKING_FOR_INT_ARG_END = 4;
/*     */   private static final int LOOKING_FOR_OSC_COMMAND = 5;
/*     */   private static final int LOOKING_FOR_OSC_COMMAND_END = 6;
/*     */   private static final int LOOKING_FOR_OSC_PARAM = 7;
/*     */   private static final int LOOKING_FOR_ST = 8;
/*     */   private static final int LOOKING_FOR_CHARSET = 9;
/*     */   int state;
/*     */   private static final int FIRST_ESC_CHAR = 27;
/*     */   private static final int SECOND_ESC_CHAR = 91;
/*     */   private static final int SECOND_OSC_CHAR = 93;
/*     */   private static final int BEL = 7;
/*  59 */   private static final char[] RESET_CODE = "\033[0m".toCharArray(); private static final int SECOND_ST_CHAR = 92; private static final int SECOND_CHARSET0_CHAR = 40; private static final int SECOND_CHARSET1_CHAR = 41; protected static final int ERASE_SCREEN_TO_END = 0; protected static final int ERASE_SCREEN_TO_BEGINING = 1; protected static final int ERASE_SCREEN = 2;
/*     */   
/*     */   public AnsiWriter(Writer out) {
/*  62 */     super(out);
/*     */ 
/*     */ 
/*     */     
/*  66 */     this.buffer = new char[100];
/*  67 */     this.pos = 0;
/*     */     
/*  69 */     this.options = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     this.state = 0;
/*     */   }
/*     */   
/*     */   protected static final int ERASE_LINE_TO_END = 0;
/*     */   protected static final int ERASE_LINE_TO_BEGINING = 1;
/*     */   protected static final int ERASE_LINE = 2;
/*     */   protected static final int ATTRIBUTE_INTENSITY_BOLD = 1;
/*     */   protected static final int ATTRIBUTE_INTENSITY_FAINT = 2;
/*     */   protected static final int ATTRIBUTE_ITALIC = 3;
/*     */   
/*     */   public synchronized void write(int data) throws IOException {
/*     */     boolean skip;
/*  94 */     switch (this.state) {
/*     */       case 0:
/*  96 */         if (data == 27) {
/*  97 */           this.buffer[this.pos++] = (char)data;
/*  98 */           this.state = 1; break;
/*     */         } 
/* 100 */         this.out.write(data);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 105 */         this.buffer[this.pos++] = (char)data;
/* 106 */         if (data == 91) {
/* 107 */           this.state = 2; break;
/* 108 */         }  if (data == 93) {
/* 109 */           this.state = 5; break;
/* 110 */         }  if (data == 40) {
/* 111 */           this.options.add(Integer.valueOf(48));
/* 112 */           this.state = 9; break;
/* 113 */         }  if (data == 41) {
/* 114 */           this.options.add(Integer.valueOf(49));
/* 115 */           this.state = 9; break;
/*     */         } 
/* 117 */         reset(false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 122 */         this.buffer[this.pos++] = (char)data;
/* 123 */         if (34 == data) {
/* 124 */           this.startOfValue = this.pos - 1;
/* 125 */           this.state = 3; break;
/* 126 */         }  if (48 <= data && data <= 57) {
/* 127 */           this.startOfValue = this.pos - 1;
/* 128 */           this.state = 4; break;
/* 129 */         }  if (59 == data) {
/* 130 */           this.options.add(null); break;
/* 131 */         }  if (63 == data) {
/* 132 */           this.options.add(Character.valueOf('?')); break;
/* 133 */         }  if (61 == data) {
/* 134 */           this.options.add(Character.valueOf('=')); break;
/*     */         } 
/* 136 */         skip = true;
/*     */         try {
/* 138 */           skip = processEscapeCommand(this.options, data);
/*     */         } finally {
/* 140 */           reset(skip);
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 148 */         this.buffer[this.pos++] = (char)data;
/* 149 */         if (48 > data || data > 57) {
/* 150 */           String strValue = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
/* 151 */           Integer value = Integer.valueOf(strValue);
/* 152 */           this.options.add(value);
/* 153 */           if (data == 59) {
/* 154 */             this.state = 2; break;
/*     */           } 
/* 156 */           boolean bool = true;
/*     */           try {
/* 158 */             bool = processEscapeCommand(this.options, data);
/*     */           } finally {
/* 160 */             reset(bool);
/*     */           } 
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 167 */         this.buffer[this.pos++] = (char)data;
/* 168 */         if (34 != data) {
/* 169 */           String value = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
/* 170 */           this.options.add(value);
/* 171 */           if (data == 59) {
/* 172 */             this.state = 2; break;
/*     */           } 
/* 174 */           reset(processEscapeCommand(this.options, data));
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 180 */         this.buffer[this.pos++] = (char)data;
/* 181 */         if (48 <= data && data <= 57) {
/* 182 */           this.startOfValue = this.pos - 1;
/* 183 */           this.state = 6; break;
/*     */         } 
/* 185 */         reset(false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 190 */         this.buffer[this.pos++] = (char)data;
/* 191 */         if (59 == data) {
/* 192 */           String strValue = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
/* 193 */           Integer value = Integer.valueOf(strValue);
/* 194 */           this.options.add(value);
/* 195 */           this.startOfValue = this.pos;
/* 196 */           this.state = 7; break;
/* 197 */         }  if (48 <= data && data <= 57) {
/*     */           break;
/*     */         }
/*     */         
/* 201 */         reset(false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 206 */         this.buffer[this.pos++] = (char)data;
/* 207 */         if (7 == data) {
/* 208 */           String value = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
/* 209 */           this.options.add(value);
/* 210 */           boolean bool = true;
/*     */           try {
/* 212 */             bool = processOperatingSystemCommand(this.options);
/*     */           } finally {
/* 214 */             reset(bool);
/*     */           }  break;
/* 216 */         }  if (27 == data) {
/* 217 */           this.state = 8;
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 224 */         this.buffer[this.pos++] = (char)data;
/* 225 */         if (92 == data) {
/* 226 */           String value = new String(this.buffer, this.startOfValue, this.pos - 2 - this.startOfValue);
/* 227 */           this.options.add(value);
/* 228 */           boolean bool = true;
/*     */           try {
/* 230 */             bool = processOperatingSystemCommand(this.options);
/*     */           } finally {
/* 232 */             reset(bool);
/*     */           }  break;
/*     */         } 
/* 235 */         this.state = 7;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 9:
/* 240 */         this.options.add(Character.valueOf((char)data));
/* 241 */         reset(processCharsetSelect(this.options));
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 246 */     if (this.pos >= this.buffer.length)
/* 247 */       reset(false); 
/*     */   }
/*     */   protected static final int ATTRIBUTE_UNDERLINE = 4; protected static final int ATTRIBUTE_BLINK_SLOW = 5; protected static final int ATTRIBUTE_BLINK_FAST = 6; protected static final int ATTRIBUTE_NEGATIVE_ON = 7; protected static final int ATTRIBUTE_CONCEAL_ON = 8; protected static final int ATTRIBUTE_UNDERLINE_DOUBLE = 21; protected static final int ATTRIBUTE_INTENSITY_NORMAL = 22; protected static final int ATTRIBUTE_UNDERLINE_OFF = 24; protected static final int ATTRIBUTE_BLINK_OFF = 25;
/*     */   @Deprecated
/*     */   protected static final int ATTRIBUTE_NEGATIVE_Off = 27;
/*     */   protected static final int ATTRIBUTE_NEGATIVE_OFF = 27;
/*     */   protected static final int ATTRIBUTE_CONCEAL_OFF = 28;
/*     */   protected static final int BLACK = 0;
/*     */   
/*     */   private void reset(boolean skipBuffer) throws IOException {
/* 257 */     if (!skipBuffer) {
/* 258 */       this.out.write(this.buffer, 0, this.pos);
/*     */     }
/* 260 */     this.pos = 0;
/* 261 */     this.startOfValue = 0;
/* 262 */     this.options.clear();
/* 263 */     this.state = 0;
/*     */   }
/*     */   protected static final int RED = 1; protected static final int GREEN = 2; protected static final int YELLOW = 3;
/*     */   protected static final int BLUE = 4;
/*     */   protected static final int MAGENTA = 5;
/*     */   protected static final int CYAN = 6;
/*     */   protected static final int WHITE = 7;
/*     */   
/*     */   private int getNextOptionInt(Iterator<Object> optionsIterator) throws IOException {
/*     */     while (true) {
/* 273 */       if (!optionsIterator.hasNext()) throw new IllegalArgumentException(); 
/* 274 */       Object arg = optionsIterator.next();
/* 275 */       if (arg != null) return ((Integer)arg).intValue();
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean processEscapeCommand(ArrayList<Object> options, int command) throws IOException {
/*     */     try {
/*     */       int count;
/*     */       Iterator<Object> optionsIterator;
/* 288 */       switch (command) {
/*     */         case 65:
/* 290 */           processCursorUp(optionInt(options, 0, 1));
/* 291 */           return true;
/*     */         case 66:
/* 293 */           processCursorDown(optionInt(options, 0, 1));
/* 294 */           return true;
/*     */         case 67:
/* 296 */           processCursorRight(optionInt(options, 0, 1));
/* 297 */           return true;
/*     */         case 68:
/* 299 */           processCursorLeft(optionInt(options, 0, 1));
/* 300 */           return true;
/*     */         case 69:
/* 302 */           processCursorDownLine(optionInt(options, 0, 1));
/* 303 */           return true;
/*     */         case 70:
/* 305 */           processCursorUpLine(optionInt(options, 0, 1));
/* 306 */           return true;
/*     */         case 71:
/* 308 */           processCursorToColumn(optionInt(options, 0));
/* 309 */           return true;
/*     */         case 72:
/*     */         case 102:
/* 312 */           processCursorTo(optionInt(options, 0, 1), optionInt(options, 1, 1));
/* 313 */           return true;
/*     */         case 74:
/* 315 */           processEraseScreen(optionInt(options, 0, 0));
/* 316 */           return true;
/*     */         case 75:
/* 318 */           processEraseLine(optionInt(options, 0, 0));
/* 319 */           return true;
/*     */         case 76:
/* 321 */           processInsertLine(optionInt(options, 0, 1));
/* 322 */           return true;
/*     */         case 77:
/* 324 */           processDeleteLine(optionInt(options, 0, 1));
/* 325 */           return true;
/*     */         case 83:
/* 327 */           processScrollUp(optionInt(options, 0, 1));
/* 328 */           return true;
/*     */         case 84:
/* 330 */           processScrollDown(optionInt(options, 0, 1));
/* 331 */           return true;
/*     */         
/*     */         case 109:
/* 334 */           for (Object next : options) {
/* 335 */             if (next != null && next.getClass() != Integer.class) {
/* 336 */               throw new IllegalArgumentException();
/*     */             }
/*     */           } 
/*     */           
/* 340 */           count = 0;
/* 341 */           optionsIterator = options.iterator();
/* 342 */           while (optionsIterator.hasNext()) {
/* 343 */             Object next = optionsIterator.next();
/* 344 */             if (next != null) {
/* 345 */               count++;
/* 346 */               int value = ((Integer)next).intValue();
/* 347 */               if (30 <= value && value <= 37) {
/* 348 */                 processSetForegroundColor(value - 30); continue;
/* 349 */               }  if (40 <= value && value <= 47) {
/* 350 */                 processSetBackgroundColor(value - 40); continue;
/* 351 */               }  if (90 <= value && value <= 97) {
/* 352 */                 processSetForegroundColor(value - 90, true); continue;
/* 353 */               }  if (100 <= value && value <= 107) {
/* 354 */                 processSetBackgroundColor(value - 100, true); continue;
/* 355 */               }  if (value == 38 || value == 48) {
/*     */                 
/* 357 */                 int arg2or5 = getNextOptionInt(optionsIterator);
/* 358 */                 if (arg2or5 == 2) {
/*     */                   
/* 360 */                   int r = getNextOptionInt(optionsIterator);
/* 361 */                   int g = getNextOptionInt(optionsIterator);
/* 362 */                   int b = getNextOptionInt(optionsIterator);
/* 363 */                   if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
/* 364 */                     if (value == 38) { processSetForegroundColorExt(r, g, b); continue; }
/* 365 */                      processSetBackgroundColorExt(r, g, b); continue;
/*     */                   } 
/* 367 */                   throw new IllegalArgumentException();
/*     */                 } 
/* 369 */                 if (arg2or5 == 5) {
/*     */                   
/* 371 */                   int paletteIndex = getNextOptionInt(optionsIterator);
/* 372 */                   if (paletteIndex >= 0 && paletteIndex <= 255) {
/* 373 */                     if (value == 38) { processSetForegroundColorExt(paletteIndex); continue; }
/* 374 */                      processSetBackgroundColorExt(paletteIndex); continue;
/*     */                   } 
/* 376 */                   throw new IllegalArgumentException();
/*     */                 } 
/*     */                 
/* 379 */                 throw new IllegalArgumentException();
/*     */               } 
/*     */               
/* 382 */               switch (value) {
/*     */                 case 39:
/* 384 */                   processDefaultTextColor();
/*     */                   continue;
/*     */                 case 49:
/* 387 */                   processDefaultBackgroundColor();
/*     */                   continue;
/*     */                 case 0:
/* 390 */                   processAttributeRest();
/*     */                   continue;
/*     */               } 
/* 393 */               processSetAttribute(value);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 398 */           if (count == 0) {
/* 399 */             processAttributeRest();
/*     */           }
/* 401 */           return true;
/*     */         case 115:
/* 403 */           processSaveCursorPosition();
/* 404 */           return true;
/*     */         case 117:
/* 406 */           processRestoreCursorPosition();
/* 407 */           return true;
/*     */       } 
/*     */       
/* 410 */       if (97 <= command && 122 <= command) {
/* 411 */         processUnknownExtension(options, command);
/* 412 */         return true;
/*     */       } 
/* 414 */       if (65 <= command && 90 <= command) {
/* 415 */         processUnknownExtension(options, command);
/* 416 */         return true;
/*     */       } 
/* 418 */       return false;
/*     */     }
/* 420 */     catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 422 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean processOperatingSystemCommand(ArrayList<Object> options) throws IOException {
/* 431 */     int command = optionInt(options, 0);
/* 432 */     String label = (String)options.get(1);
/*     */ 
/*     */     
/*     */     try {
/* 436 */       switch (command) {
/*     */         case 0:
/* 438 */           processChangeIconNameAndWindowTitle(label);
/* 439 */           return true;
/*     */         case 1:
/* 441 */           processChangeIconName(label);
/* 442 */           return true;
/*     */         case 2:
/* 444 */           processChangeWindowTitle(label);
/* 445 */           return true;
/*     */       } 
/*     */ 
/*     */       
/* 449 */       processUnknownOperatingSystemCommand(command, label);
/* 450 */       return true;
/*     */     }
/* 452 */     catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 454 */       return false;
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
/*     */   protected void processRestoreCursorPosition() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSaveCursorPosition() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processInsertLine(int optionInt) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processDeleteLine(int optionInt) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processScrollDown(int optionInt) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processScrollUp(int optionInt) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processEraseScreen(int eraseOption) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processEraseLine(int eraseOption) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetAttribute(int attribute) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColor(int color) throws IOException {
/* 570 */     processSetForegroundColor(color, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColor(int color, boolean bright) throws IOException {
/* 581 */     processSetForegroundColorExt(bright ? (color + 8) : color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColorExt(int paletteIndex) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColorExt(int r, int g, int b) throws IOException {
/* 601 */     processSetForegroundColorExt(Colors.roundRgbColor(r, g, b, 16));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColor(int color) throws IOException {
/* 610 */     processSetBackgroundColor(color, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColor(int color, boolean bright) throws IOException {
/* 621 */     processSetBackgroundColorExt(bright ? (color + 8) : color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColorExt(int paletteIndex) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColorExt(int r, int g, int b) throws IOException {
/* 641 */     processSetBackgroundColorExt(Colors.roundRgbColor(r, g, b, 16));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processDefaultTextColor() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processDefaultBackgroundColor() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processAttributeRest() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorTo(int row, int col) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorToColumn(int x) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorUpLine(int count) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorDownLine(int count) throws IOException {
/* 692 */     for (int i = 0; i < count; i++) {
/* 693 */       this.out.write(10);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorLeft(int count) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorRight(int count) throws IOException {
/* 711 */     for (int i = 0; i < count; i++) {
/* 712 */       this.out.write(32);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorDown(int count) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCursorUp(int count) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processUnknownExtension(ArrayList<Object> options, int command) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processChangeIconNameAndWindowTitle(String label) {
/* 737 */     processChangeIconName(label);
/* 738 */     processChangeWindowTitle(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processChangeIconName(String name) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processChangeWindowTitle(String title) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processUnknownOperatingSystemCommand(int command, String param) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean processCharsetSelect(ArrayList<Object> options) throws IOException {
/* 766 */     int set = optionInt(options, 0);
/* 767 */     char seq = ((Character)options.get(1)).charValue();
/* 768 */     processCharsetSelect(set, seq);
/* 769 */     return true;
/*     */   }
/*     */   
/*     */   protected void processCharsetSelect(int set, char seq) {}
/*     */   
/*     */   private int optionInt(ArrayList<Object> options, int index) {
/* 775 */     if (options.size() <= index) throw new IllegalArgumentException(); 
/* 776 */     Object value = options.get(index);
/* 777 */     if (value == null) throw new IllegalArgumentException(); 
/* 778 */     if (!value.getClass().equals(Integer.class)) throw new IllegalArgumentException(); 
/* 779 */     return ((Integer)value).intValue();
/*     */   }
/*     */   
/*     */   private int optionInt(ArrayList<Object> options, int index, int defaultValue) {
/* 783 */     if (options.size() > index) {
/* 784 */       Object value = options.get(index);
/* 785 */       if (value == null) {
/* 786 */         return defaultValue;
/*     */       }
/* 788 */       return ((Integer)value).intValue();
/*     */     } 
/* 790 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(char[] cbuf, int off, int len) throws IOException {
/* 796 */     for (int i = 0; i < len; i++) {
/* 797 */       write(cbuf[off + i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(String str, int off, int len) throws IOException {
/* 804 */     for (int i = 0; i < len; i++) {
/* 805 */       write(str.charAt(off + i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 811 */     write(RESET_CODE);
/* 812 */     flush();
/* 813 */     super.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\AnsiWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */