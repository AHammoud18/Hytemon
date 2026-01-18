/*     */ package org.jline.jansi.io;
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
/*  31 */     this.os = os;
/*     */   }
/*     */   protected static final int ATTRIBUTE_CONCEAL_ON = 8; protected static final int ATTRIBUTE_UNDERLINE_DOUBLE = 21; protected static final int ATTRIBUTE_INTENSITY_NORMAL = 22; protected static final int ATTRIBUTE_UNDERLINE_OFF = 24; protected static final int ATTRIBUTE_BLINK_OFF = 25; protected static final int ATTRIBUTE_NEGATIVE_OFF = 27; protected static final int ATTRIBUTE_CONCEAL_OFF = 28; protected static final int BLACK = 0; protected static final int RED = 1; protected static final int GREEN = 2; protected static final int YELLOW = 3;
/*     */   protected static final int BLUE = 4;
/*     */   protected static final int MAGENTA = 5;
/*     */   protected static final int CYAN = 6;
/*     */   protected static final int WHITE = 7;
/*     */   
/*     */   protected int getNextOptionInt(Iterator<Object> optionsIterator) throws IOException {
/*     */     while (true) {
/*  41 */       if (!optionsIterator.hasNext()) throw new IllegalArgumentException(); 
/*  42 */       Object arg = optionsIterator.next();
/*  43 */       if (arg != null) return ((Integer)arg).intValue();
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean processEscapeCommand(ArrayList<Object> options, int command) throws IOException {
/*     */     try {
/*     */       int count;
/*     */       Iterator<Object> optionsIterator;
/*  52 */       switch (command) {
/*     */         case 65:
/*  54 */           processCursorUp(optionInt(options, 0, 1));
/*  55 */           return true;
/*     */         case 66:
/*  57 */           processCursorDown(optionInt(options, 0, 1));
/*  58 */           return true;
/*     */         case 67:
/*  60 */           processCursorRight(optionInt(options, 0, 1));
/*  61 */           return true;
/*     */         case 68:
/*  63 */           processCursorLeft(optionInt(options, 0, 1));
/*  64 */           return true;
/*     */         case 69:
/*  66 */           processCursorDownLine(optionInt(options, 0, 1));
/*  67 */           return true;
/*     */         case 70:
/*  69 */           processCursorUpLine(optionInt(options, 0, 1));
/*  70 */           return true;
/*     */         case 71:
/*  72 */           processCursorToColumn(optionInt(options, 0));
/*  73 */           return true;
/*     */         case 72:
/*     */         case 102:
/*  76 */           processCursorTo(optionInt(options, 0, 1), optionInt(options, 1, 1));
/*  77 */           return true;
/*     */         case 74:
/*  79 */           processEraseScreen(optionInt(options, 0, 0));
/*  80 */           return true;
/*     */         case 75:
/*  82 */           processEraseLine(optionInt(options, 0, 0));
/*  83 */           return true;
/*     */         case 76:
/*  85 */           processInsertLine(optionInt(options, 0, 1));
/*  86 */           return true;
/*     */         case 77:
/*  88 */           processDeleteLine(optionInt(options, 0, 1));
/*  89 */           return true;
/*     */         case 83:
/*  91 */           processScrollUp(optionInt(options, 0, 1));
/*  92 */           return true;
/*     */         case 84:
/*  94 */           processScrollDown(optionInt(options, 0, 1));
/*  95 */           return true;
/*     */         
/*     */         case 109:
/*  98 */           for (Object next : options) {
/*  99 */             if (next != null && next.getClass() != Integer.class) {
/* 100 */               throw new IllegalArgumentException();
/*     */             }
/*     */           } 
/*     */           
/* 104 */           count = 0;
/* 105 */           optionsIterator = options.iterator();
/* 106 */           while (optionsIterator.hasNext()) {
/* 107 */             Object next = optionsIterator.next();
/* 108 */             if (next != null) {
/* 109 */               count++;
/* 110 */               int value = ((Integer)next).intValue();
/* 111 */               if (30 <= value && value <= 37) {
/* 112 */                 processSetForegroundColor(value - 30); continue;
/* 113 */               }  if (40 <= value && value <= 47) {
/* 114 */                 processSetBackgroundColor(value - 40); continue;
/* 115 */               }  if (90 <= value && value <= 97) {
/* 116 */                 processSetForegroundColor(value - 90, true); continue;
/* 117 */               }  if (100 <= value && value <= 107) {
/* 118 */                 processSetBackgroundColor(value - 100, true); continue;
/* 119 */               }  if (value == 38 || value == 48) {
/* 120 */                 if (!optionsIterator.hasNext()) {
/*     */                   continue;
/*     */                 }
/*     */                 
/* 124 */                 int arg2or5 = getNextOptionInt(optionsIterator);
/* 125 */                 if (arg2or5 == 2) {
/*     */                   
/* 127 */                   int r = getNextOptionInt(optionsIterator);
/* 128 */                   int g = getNextOptionInt(optionsIterator);
/* 129 */                   int b = getNextOptionInt(optionsIterator);
/* 130 */                   if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
/* 131 */                     if (value == 38) { processSetForegroundColorExt(r, g, b); continue; }
/* 132 */                      processSetBackgroundColorExt(r, g, b); continue;
/*     */                   } 
/* 134 */                   throw new IllegalArgumentException();
/*     */                 } 
/* 136 */                 if (arg2or5 == 5) {
/*     */                   
/* 138 */                   int paletteIndex = getNextOptionInt(optionsIterator);
/* 139 */                   if (paletteIndex >= 0 && paletteIndex <= 255) {
/* 140 */                     if (value == 38) { processSetForegroundColorExt(paletteIndex); continue; }
/* 141 */                      processSetBackgroundColorExt(paletteIndex); continue;
/*     */                   } 
/* 143 */                   throw new IllegalArgumentException();
/*     */                 } 
/*     */                 
/* 146 */                 throw new IllegalArgumentException();
/*     */               } 
/*     */               
/* 149 */               switch (value) {
/*     */                 case 39:
/* 151 */                   processDefaultTextColor();
/*     */                   continue;
/*     */                 case 49:
/* 154 */                   processDefaultBackgroundColor();
/*     */                   continue;
/*     */                 case 0:
/* 157 */                   processAttributeReset();
/*     */                   continue;
/*     */               } 
/* 160 */               processSetAttribute(value);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 165 */           if (count == 0) {
/* 166 */             processAttributeReset();
/*     */           }
/* 168 */           return true;
/*     */         case 115:
/* 170 */           processSaveCursorPosition();
/* 171 */           return true;
/*     */         case 117:
/* 173 */           processRestoreCursorPosition();
/* 174 */           return true;
/*     */       } 
/*     */       
/* 177 */       if (97 <= command && command <= 122) {
/* 178 */         processUnknownExtension(options, command);
/* 179 */         return true;
/*     */       } 
/* 181 */       if (65 <= command && command <= 90) {
/* 182 */         processUnknownExtension(options, command);
/* 183 */         return true;
/*     */       } 
/* 185 */       return false;
/*     */     }
/* 187 */     catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 189 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean processOperatingSystemCommand(ArrayList<Object> options) {
/* 196 */     int command = optionInt(options, 0);
/* 197 */     String label = (String)options.get(1);
/*     */ 
/*     */     
/*     */     try {
/* 201 */       switch (command) {
/*     */         case 0:
/* 203 */           processChangeIconNameAndWindowTitle(label);
/* 204 */           return true;
/*     */         case 1:
/* 206 */           processChangeIconName(label);
/* 207 */           return true;
/*     */         case 2:
/* 209 */           processChangeWindowTitle(label);
/* 210 */           return true;
/*     */       } 
/*     */ 
/*     */       
/* 214 */       processUnknownOperatingSystemCommand(command, label);
/* 215 */       return true;
/*     */     }
/* 217 */     catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 219 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean processCharsetSelect(ArrayList<Object> options) {
/* 228 */     int set = optionInt(options, 0);
/* 229 */     char seq = ((Character)options.get(1)).charValue();
/* 230 */     processCharsetSelect(set, seq);
/* 231 */     return true;
/*     */   }
/*     */   
/*     */   private int optionInt(ArrayList<Object> options, int index) {
/* 235 */     if (options.size() <= index) throw new IllegalArgumentException(); 
/* 236 */     Object value = options.get(index);
/* 237 */     if (value == null) throw new IllegalArgumentException(); 
/* 238 */     if (!value.getClass().equals(Integer.class)) throw new IllegalArgumentException(); 
/* 239 */     return ((Integer)value).intValue();
/*     */   }
/*     */   
/*     */   private int optionInt(ArrayList<Object> options, int index, int defaultValue) {
/* 243 */     if (options.size() > index) {
/* 244 */       Object value = options.get(index);
/* 245 */       if (value == null) {
/* 246 */         return defaultValue;
/*     */       }
/* 248 */       return ((Integer)value).intValue();
/*     */     } 
/* 250 */     return defaultValue;
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
/* 364 */     processSetForegroundColor(color, false);
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
/* 400 */     processSetBackgroundColor(color, false);
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
/* 478 */     for (int i = 0; i < count; i++) {
/* 479 */       this.os.write(10);
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
/* 497 */     for (int i = 0; i < count; i++) {
/* 498 */       this.os.write(32);
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
/* 528 */     processChangeIconName(label);
/* 529 */     processChangeWindowTitle(label);
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\jansi\io\AnsiProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */