/*     */ package org.fusesource.jansi.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public class AnsiProcessor
/*     */ {
/*     */   protected final OutputStream os;
/*     */   protected static final int ERASE_SCREEN_TO_END = 0;
/*     */   protected static final int ERASE_SCREEN_TO_BEGINING = 1;
/*     */   protected static final int ERASE_SCREEN = 2;
/*     */   protected static final int ERASE_LINE_TO_END = 0;
/*     */   protected static final int ERASE_LINE_TO_BEGINING = 1;
/*     */   protected static final int ERASE_LINE = 2;
/*     */   protected static final int ATTRIBUTE_INTENSITY_BOLD = 1;
/*     */   protected static final int ATTRIBUTE_INTENSITY_FAINT = 2;
/*     */   protected static final int ATTRIBUTE_ITALIC = 3;
/*     */   protected static final int ATTRIBUTE_UNDERLINE = 4;
/*     */   protected static final int ATTRIBUTE_BLINK_SLOW = 5;
/*     */   protected static final int ATTRIBUTE_BLINK_FAST = 6;
/*     */   protected static final int ATTRIBUTE_NEGATIVE_ON = 7;
/*     */   
/*     */   public AnsiProcessor(OutputStream os) {
/*  38 */     this.os = os;
/*     */   }
/*     */   protected static final int ATTRIBUTE_CONCEAL_ON = 8; protected static final int ATTRIBUTE_UNDERLINE_DOUBLE = 21; protected static final int ATTRIBUTE_INTENSITY_NORMAL = 22; protected static final int ATTRIBUTE_UNDERLINE_OFF = 24; protected static final int ATTRIBUTE_BLINK_OFF = 25; protected static final int ATTRIBUTE_NEGATIVE_OFF = 27; protected static final int ATTRIBUTE_CONCEAL_OFF = 28; protected static final int BLACK = 0; protected static final int RED = 1; protected static final int GREEN = 2; protected static final int YELLOW = 3;
/*     */   protected static final int BLUE = 4;
/*     */   protected static final int MAGENTA = 5;
/*     */   protected static final int CYAN = 6;
/*     */   protected static final int WHITE = 7;
/*     */   
/*     */   protected int getNextOptionInt(Iterator<Object> optionsIterator) throws IOException {
/*     */     while (true) {
/*  48 */       if (!optionsIterator.hasNext()) throw new IllegalArgumentException(); 
/*  49 */       Object arg = optionsIterator.next();
/*  50 */       if (arg != null) return ((Integer)arg).intValue();
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean processEscapeCommand(ArrayList<Object> options, int command) throws IOException {
/*     */     try {
/*     */       int count;
/*     */       Iterator<Object> optionsIterator;
/*  59 */       switch (command) {
/*     */         case 65:
/*  61 */           processCursorUp(optionInt(options, 0, 1));
/*  62 */           return true;
/*     */         case 66:
/*  64 */           processCursorDown(optionInt(options, 0, 1));
/*  65 */           return true;
/*     */         case 67:
/*  67 */           processCursorRight(optionInt(options, 0, 1));
/*  68 */           return true;
/*     */         case 68:
/*  70 */           processCursorLeft(optionInt(options, 0, 1));
/*  71 */           return true;
/*     */         case 69:
/*  73 */           processCursorDownLine(optionInt(options, 0, 1));
/*  74 */           return true;
/*     */         case 70:
/*  76 */           processCursorUpLine(optionInt(options, 0, 1));
/*  77 */           return true;
/*     */         case 71:
/*  79 */           processCursorToColumn(optionInt(options, 0));
/*  80 */           return true;
/*     */         case 72:
/*     */         case 102:
/*  83 */           processCursorTo(optionInt(options, 0, 1), optionInt(options, 1, 1));
/*  84 */           return true;
/*     */         case 74:
/*  86 */           processEraseScreen(optionInt(options, 0, 0));
/*  87 */           return true;
/*     */         case 75:
/*  89 */           processEraseLine(optionInt(options, 0, 0));
/*  90 */           return true;
/*     */         case 76:
/*  92 */           processInsertLine(optionInt(options, 0, 1));
/*  93 */           return true;
/*     */         case 77:
/*  95 */           processDeleteLine(optionInt(options, 0, 1));
/*  96 */           return true;
/*     */         case 83:
/*  98 */           processScrollUp(optionInt(options, 0, 1));
/*  99 */           return true;
/*     */         case 84:
/* 101 */           processScrollDown(optionInt(options, 0, 1));
/* 102 */           return true;
/*     */         
/*     */         case 109:
/* 105 */           for (Object next : options) {
/* 106 */             if (next != null && next.getClass() != Integer.class) {
/* 107 */               throw new IllegalArgumentException();
/*     */             }
/*     */           } 
/*     */           
/* 111 */           count = 0;
/* 112 */           optionsIterator = options.iterator();
/* 113 */           while (optionsIterator.hasNext()) {
/* 114 */             Object next = optionsIterator.next();
/* 115 */             if (next != null) {
/* 116 */               count++;
/* 117 */               int value = ((Integer)next).intValue();
/* 118 */               if (30 <= value && value <= 37) {
/* 119 */                 processSetForegroundColor(value - 30); continue;
/* 120 */               }  if (40 <= value && value <= 47) {
/* 121 */                 processSetBackgroundColor(value - 40); continue;
/* 122 */               }  if (90 <= value && value <= 97) {
/* 123 */                 processSetForegroundColor(value - 90, true); continue;
/* 124 */               }  if (100 <= value && value <= 107) {
/* 125 */                 processSetBackgroundColor(value - 100, true); continue;
/* 126 */               }  if (value == 38 || value == 48) {
/* 127 */                 if (!optionsIterator.hasNext()) {
/*     */                   continue;
/*     */                 }
/*     */                 
/* 131 */                 int arg2or5 = getNextOptionInt(optionsIterator);
/* 132 */                 if (arg2or5 == 2) {
/*     */                   
/* 134 */                   int r = getNextOptionInt(optionsIterator);
/* 135 */                   int g = getNextOptionInt(optionsIterator);
/* 136 */                   int b = getNextOptionInt(optionsIterator);
/* 137 */                   if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
/* 138 */                     if (value == 38) { processSetForegroundColorExt(r, g, b); continue; }
/* 139 */                      processSetBackgroundColorExt(r, g, b); continue;
/*     */                   } 
/* 141 */                   throw new IllegalArgumentException();
/*     */                 } 
/* 143 */                 if (arg2or5 == 5) {
/*     */                   
/* 145 */                   int paletteIndex = getNextOptionInt(optionsIterator);
/* 146 */                   if (paletteIndex >= 0 && paletteIndex <= 255) {
/* 147 */                     if (value == 38) { processSetForegroundColorExt(paletteIndex); continue; }
/* 148 */                      processSetBackgroundColorExt(paletteIndex); continue;
/*     */                   } 
/* 150 */                   throw new IllegalArgumentException();
/*     */                 } 
/*     */                 
/* 153 */                 throw new IllegalArgumentException();
/*     */               } 
/*     */               
/* 156 */               switch (value) {
/*     */                 case 39:
/* 158 */                   processDefaultTextColor();
/*     */                   continue;
/*     */                 case 49:
/* 161 */                   processDefaultBackgroundColor();
/*     */                   continue;
/*     */                 case 0:
/* 164 */                   processAttributeReset();
/*     */                   continue;
/*     */               } 
/* 167 */               processSetAttribute(value);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 172 */           if (count == 0) {
/* 173 */             processAttributeReset();
/*     */           }
/* 175 */           return true;
/*     */         case 115:
/* 177 */           processSaveCursorPosition();
/* 178 */           return true;
/*     */         case 117:
/* 180 */           processRestoreCursorPosition();
/* 181 */           return true;
/*     */       } 
/*     */       
/* 184 */       if (97 <= command && command <= 122) {
/* 185 */         processUnknownExtension(options, command);
/* 186 */         return true;
/*     */       } 
/* 188 */       if (65 <= command && command <= 90) {
/* 189 */         processUnknownExtension(options, command);
/* 190 */         return true;
/*     */       } 
/* 192 */       return false;
/*     */     }
/* 194 */     catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 196 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean processOperatingSystemCommand(ArrayList<Object> options) {
/* 203 */     int command = optionInt(options, 0);
/* 204 */     String label = (String)options.get(1);
/*     */ 
/*     */     
/*     */     try {
/* 208 */       switch (command) {
/*     */         case 0:
/* 210 */           processChangeIconNameAndWindowTitle(label);
/* 211 */           return true;
/*     */         case 1:
/* 213 */           processChangeIconName(label);
/* 214 */           return true;
/*     */         case 2:
/* 216 */           processChangeWindowTitle(label);
/* 217 */           return true;
/*     */       } 
/*     */ 
/*     */       
/* 221 */       processUnknownOperatingSystemCommand(command, label);
/* 222 */       return true;
/*     */     }
/* 224 */     catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 226 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean processCharsetSelect(ArrayList<Object> options) {
/* 235 */     int set = optionInt(options, 0);
/* 236 */     char seq = ((Character)options.get(1)).charValue();
/* 237 */     processCharsetSelect(set, seq);
/* 238 */     return true;
/*     */   }
/*     */   
/*     */   private int optionInt(ArrayList<Object> options, int index) {
/* 242 */     if (options.size() <= index) throw new IllegalArgumentException(); 
/* 243 */     Object value = options.get(index);
/* 244 */     if (value == null) throw new IllegalArgumentException(); 
/* 245 */     if (!value.getClass().equals(Integer.class)) throw new IllegalArgumentException(); 
/* 246 */     return ((Integer)value).intValue();
/*     */   }
/*     */   
/*     */   private int optionInt(ArrayList<Object> options, int index, int defaultValue) {
/* 250 */     if (options.size() > index) {
/* 251 */       Object value = options.get(index);
/* 252 */       if (value == null) {
/* 253 */         return defaultValue;
/*     */       }
/* 255 */       return ((Integer)value).intValue();
/*     */     } 
/* 257 */     return defaultValue;
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
/* 371 */     processSetForegroundColor(color, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColor(int color, boolean bright) throws IOException {}
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
/*     */   protected void processSetForegroundColorExt(int r, int g, int b) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColor(int color) throws IOException {
/* 407 */     processSetBackgroundColor(color, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColor(int color, boolean bright) throws IOException {}
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
/*     */   protected void processSetBackgroundColorExt(int r, int g, int b) throws IOException {}
/*     */ 
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
/*     */   
/*     */   protected void processDefaultBackgroundColor() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processAttributeReset() throws IOException {}
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
/* 485 */     for (int i = 0; i < count; i++) {
/* 486 */       this.os.write(10);
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
/* 504 */     for (int i = 0; i < count; i++) {
/* 505 */       this.os.write(32);
/*     */     }
/*     */   }
/*     */ 
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
/*     */   
/*     */   protected void processCursorUp(int count) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processUnknownExtension(ArrayList<Object> options, int command) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processChangeIconNameAndWindowTitle(String label) {
/* 535 */     processChangeIconName(label);
/* 536 */     processChangeWindowTitle(label);
/*     */   }
/*     */   
/*     */   protected void processChangeIconName(String label) {}
/*     */   
/*     */   protected void processChangeWindowTitle(String label) {}
/*     */   
/*     */   protected void processUnknownOperatingSystemCommand(int command, String param) {}
/*     */   
/*     */   protected void processCharsetSelect(int set, char seq) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\io\AnsiProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */