/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
/*     */ import org.jline.terminal.Terminal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Display
/*     */ {
/*     */   protected final Terminal terminal;
/*     */   protected final boolean fullScreen;
/*  59 */   protected List<AttributedString> oldLines = Collections.emptyList();
/*     */   
/*     */   protected int cursorPos;
/*     */   protected int columns;
/*     */   protected int columns1;
/*     */   protected int rows;
/*     */   protected boolean reset;
/*     */   protected boolean delayLineWrap;
/*  67 */   protected final Map<InfoCmp.Capability, Integer> cost = new HashMap<>();
/*     */   
/*     */   protected final boolean canScroll;
/*     */   protected final boolean wrapAtEol;
/*     */   protected final boolean delayedWrapAtEol;
/*     */   protected final boolean cursorDownIsNewLine;
/*     */   
/*     */   public Display(Terminal terminal, boolean fullscreen) {
/*  75 */     this.terminal = terminal;
/*  76 */     this.fullScreen = fullscreen;
/*     */     
/*  78 */     this
/*  79 */       .canScroll = (can(InfoCmp.Capability.insert_line, InfoCmp.Capability.parm_insert_line) && can(InfoCmp.Capability.delete_line, InfoCmp.Capability.parm_delete_line));
/*  80 */     this.wrapAtEol = terminal.getBooleanCapability(InfoCmp.Capability.auto_right_margin);
/*  81 */     this.delayedWrapAtEol = (this.wrapAtEol && terminal.getBooleanCapability(InfoCmp.Capability.eat_newline_glitch));
/*  82 */     this.cursorDownIsNewLine = "\n".equals(Curses.tputs(terminal.getStringCapability(InfoCmp.Capability.cursor_down), new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delayLineWrap() {
/*  91 */     return this.delayLineWrap;
/*     */   }
/*     */   
/*     */   public void setDelayLineWrap(boolean v) {
/*  95 */     this.delayLineWrap = v;
/*     */   }
/*     */   
/*     */   public void resize(int rows, int columns) {
/*  99 */     if (rows == 0 || columns == 0) {
/* 100 */       columns = 2147483646;
/* 101 */       rows = 1;
/*     */     } 
/* 103 */     if (this.rows != rows || this.columns != columns) {
/* 104 */       this.rows = rows;
/* 105 */       this.columns = columns;
/* 106 */       this.columns1 = columns + 1;
/* 107 */       this
/* 108 */         .oldLines = AttributedString.join(AttributedString.EMPTY, this.oldLines).columnSplitLength(columns, true, delayLineWrap());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/* 113 */     this.oldLines = Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 121 */     if (this.fullScreen) {
/* 122 */       this.reset = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateAnsi(List<String> newLines, int targetCursorPos) {
/* 127 */     update((List<AttributedString>)newLines.stream().map(AttributedString::fromAnsi).collect(Collectors.toList()), targetCursorPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(List<AttributedString> newLines, int targetCursorPos) {
/* 136 */     update(newLines, targetCursorPos, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(List<AttributedString> newLines, int targetCursorPos, boolean flush) {
/* 146 */     if (this.reset) {
/* 147 */       this.terminal.puts(InfoCmp.Capability.clear_screen, new Object[0]);
/* 148 */       this.oldLines.clear();
/* 149 */       this.cursorPos = 0;
/* 150 */       this.reset = false;
/*     */     } 
/*     */ 
/*     */     
/* 154 */     Integer cols = this.terminal.getNumericCapability(InfoCmp.Capability.max_colors);
/* 155 */     if (cols == null || cols.intValue() < 8)
/*     */     {
/*     */       
/* 158 */       newLines = (List<AttributedString>)newLines.stream().map(s -> new AttributedString(s.toString())).collect(Collectors.toList());
/*     */     }
/*     */ 
/*     */     
/* 162 */     if ((this.fullScreen || newLines.size() >= this.rows) && newLines.size() == this.oldLines.size() && this.canScroll) {
/* 163 */       int nbHeaders = 0;
/* 164 */       int nbFooters = 0;
/*     */       
/* 166 */       int l = newLines.size();
/* 167 */       while (nbHeaders < l && Objects.equals(newLines.get(nbHeaders), this.oldLines.get(nbHeaders))) {
/* 168 */         nbHeaders++;
/*     */       }
/* 170 */       while (nbFooters < l - nbHeaders - 1 && 
/* 171 */         Objects.equals(newLines
/* 172 */           .get(newLines.size() - nbFooters - 1), this.oldLines
/* 173 */           .get(this.oldLines.size() - nbFooters - 1))) {
/* 174 */         nbFooters++;
/*     */       }
/* 176 */       List<AttributedString> o1 = newLines.subList(nbHeaders, newLines.size() - nbFooters);
/* 177 */       List<AttributedString> o2 = this.oldLines.subList(nbHeaders, this.oldLines.size() - nbFooters);
/* 178 */       int[] common = longestCommon(o1, o2);
/* 179 */       if (common != null) {
/* 180 */         int s1 = common[0];
/* 181 */         int s2 = common[1];
/* 182 */         int sl = common[2];
/* 183 */         if (sl > 1 && s1 < s2) {
/* 184 */           moveVisualCursorTo((nbHeaders + s1) * this.columns1);
/* 185 */           int nb = s2 - s1;
/* 186 */           deleteLines(nb); int i;
/* 187 */           for (i = 0; i < nb; i++) {
/* 188 */             this.oldLines.remove(nbHeaders + s1);
/*     */           }
/* 190 */           if (nbFooters > 0) {
/* 191 */             moveVisualCursorTo((nbHeaders + s1 + sl) * this.columns1);
/* 192 */             insertLines(nb);
/* 193 */             for (i = 0; i < nb; i++) {
/* 194 */               this.oldLines.add(nbHeaders + s1 + sl, new AttributedString(""));
/*     */             }
/*     */           } 
/* 197 */         } else if (sl > 1 && s1 > s2) {
/* 198 */           int nb = s1 - s2;
/* 199 */           if (nbFooters > 0) {
/* 200 */             moveVisualCursorTo((nbHeaders + s2 + sl) * this.columns1);
/* 201 */             deleteLines(nb);
/* 202 */             for (int j = 0; j < nb; j++) {
/* 203 */               this.oldLines.remove(nbHeaders + s2 + sl);
/*     */             }
/*     */           } 
/* 206 */           moveVisualCursorTo((nbHeaders + s2) * this.columns1);
/* 207 */           insertLines(nb);
/* 208 */           for (int i = 0; i < nb; i++) {
/* 209 */             this.oldLines.add(nbHeaders + s2, new AttributedString(""));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     int lineIndex = 0;
/* 216 */     int currentPos = 0;
/* 217 */     int numLines = Math.min(this.rows, Math.max(this.oldLines.size(), newLines.size()));
/* 218 */     boolean wrapNeeded = false;
/* 219 */     while (lineIndex < numLines) {
/* 220 */       AttributedString oldLine = (lineIndex < this.oldLines.size()) ? this.oldLines.get(lineIndex) : AttributedString.EMPTY;
/* 221 */       AttributedString newLine = (lineIndex < newLines.size()) ? newLines.get(lineIndex) : AttributedString.EMPTY;
/* 222 */       currentPos = lineIndex * this.columns1;
/* 223 */       int curCol = currentPos;
/* 224 */       int oldLength = oldLine.length();
/* 225 */       int newLength = newLine.length();
/* 226 */       boolean oldNL = (oldLength > 0 && oldLine.charAt(oldLength - 1) == '\n');
/* 227 */       boolean newNL = (newLength > 0 && newLine.charAt(newLength - 1) == '\n');
/* 228 */       if (oldNL) {
/* 229 */         oldLength--;
/* 230 */         oldLine = oldLine.substring(0, oldLength);
/*     */       } 
/* 232 */       if (newNL) {
/* 233 */         newLength--;
/* 234 */         newLine = newLine.substring(0, newLength);
/*     */       } 
/* 236 */       if (wrapNeeded && lineIndex == (this.cursorPos + 1) / this.columns1 && lineIndex < newLines.size()) {
/*     */         
/* 238 */         this.cursorPos++;
/* 239 */         if (newLength == 0 || newLine.isHidden(0)) {
/*     */           
/* 241 */           rawPrint(32);
/* 242 */           this.terminal.puts(InfoCmp.Capability.cursor_left, new Object[0]);
/*     */         } else {
/* 244 */           AttributedString firstChar = newLine.substring(0, 1);
/*     */           
/* 246 */           rawPrint(firstChar);
/* 247 */           this.cursorPos += firstChar.columnLength();
/* 248 */           newLine = newLine.substring(1, newLength);
/* 249 */           newLength--;
/* 250 */           if (oldLength > 0) {
/* 251 */             oldLine = oldLine.substring(1, oldLength);
/* 252 */             oldLength--;
/*     */           } 
/* 254 */           currentPos = this.cursorPos;
/*     */         } 
/*     */       } 
/* 257 */       List<DiffHelper.Diff> diffs = DiffHelper.diff(oldLine, newLine);
/* 258 */       boolean ident = true;
/* 259 */       boolean cleared = false;
/* 260 */       for (int i = 0; i < diffs.size(); i++) {
/* 261 */         int oldLen, newLen, nb; DiffHelper.Diff diff = diffs.get(i);
/* 262 */         int width = diff.text.columnLength();
/* 263 */         switch (diff.operation) {
/*     */           case EQUAL:
/* 265 */             if (!ident) {
/* 266 */               this.cursorPos = moveVisualCursorTo(currentPos);
/* 267 */               rawPrint(diff.text);
/* 268 */               this.cursorPos += width;
/* 269 */               currentPos = this.cursorPos; break;
/*     */             } 
/* 271 */             currentPos += width;
/*     */             break;
/*     */           
/*     */           case INSERT:
/* 275 */             if (i <= diffs.size() - 2 && ((DiffHelper.Diff)diffs.get(i + 1)).operation == DiffHelper.Operation.EQUAL) {
/* 276 */               this.cursorPos = moveVisualCursorTo(currentPos);
/* 277 */               if (insertChars(width)) {
/* 278 */                 rawPrint(diff.text);
/* 279 */                 this.cursorPos += width;
/* 280 */                 currentPos = this.cursorPos;
/*     */                 break;
/*     */               } 
/* 283 */             } else if (i <= diffs.size() - 2 && ((DiffHelper.Diff)diffs
/* 284 */               .get(i + 1)).operation == DiffHelper.Operation.DELETE && width == ((DiffHelper.Diff)diffs
/* 285 */               .get(i + 1)).text.columnLength()) {
/* 286 */               moveVisualCursorTo(currentPos);
/* 287 */               rawPrint(diff.text);
/* 288 */               this.cursorPos += width;
/* 289 */               currentPos = this.cursorPos;
/* 290 */               i++;
/*     */               break;
/*     */             } 
/* 293 */             moveVisualCursorTo(currentPos);
/* 294 */             rawPrint(diff.text);
/* 295 */             this.cursorPos += width;
/* 296 */             currentPos = this.cursorPos;
/* 297 */             ident = false;
/*     */             break;
/*     */           case DELETE:
/* 300 */             if (cleared) {
/*     */               break;
/*     */             }
/* 303 */             if (currentPos - curCol >= this.columns) {
/*     */               break;
/*     */             }
/* 306 */             if (i <= diffs.size() - 2 && ((DiffHelper.Diff)diffs.get(i + 1)).operation == DiffHelper.Operation.EQUAL && 
/* 307 */               currentPos + ((DiffHelper.Diff)diffs.get(i + 1)).text.columnLength() < this.columns) {
/* 308 */               moveVisualCursorTo(currentPos);
/* 309 */               if (deleteChars(width)) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */             
/* 314 */             oldLen = oldLine.columnLength();
/* 315 */             newLen = newLine.columnLength();
/* 316 */             nb = Math.max(oldLen, newLen) - currentPos - curCol;
/* 317 */             moveVisualCursorTo(currentPos);
/* 318 */             if (!this.terminal.puts(InfoCmp.Capability.clr_eol, new Object[0])) {
/* 319 */               rawPrint(' ', nb);
/* 320 */               this.cursorPos += nb;
/*     */             } 
/* 322 */             cleared = true;
/* 323 */             ident = false;
/*     */             break;
/*     */         } 
/*     */       } 
/* 327 */       lineIndex++;
/* 328 */       boolean newWrap = (!newNL && lineIndex < newLines.size());
/* 329 */       if (targetCursorPos + 1 == lineIndex * this.columns1 && (newWrap || !this.delayLineWrap)) targetCursorPos++; 
/* 330 */       boolean atRight = ((this.cursorPos - curCol) % this.columns1 == this.columns);
/* 331 */       wrapNeeded = false;
/* 332 */       if (this.delayedWrapAtEol) {
/* 333 */         boolean oldWrap = (!oldNL && lineIndex < this.oldLines.size());
/* 334 */         if (newWrap != oldWrap && (!oldWrap || !cleared)) {
/* 335 */           moveVisualCursorTo(lineIndex * this.columns1 - 1, newLines);
/* 336 */           if (newWrap) { wrapNeeded = true; continue; }
/* 337 */            this.terminal.puts(InfoCmp.Capability.clr_eol, new Object[0]);
/*     */         }  continue;
/* 339 */       }  if (atRight) {
/* 340 */         if (this.wrapAtEol) {
/* 341 */           if (!this.fullScreen || (this.fullScreen && lineIndex < numLines)) {
/* 342 */             rawPrint(32);
/* 343 */             this.terminal.puts(InfoCmp.Capability.cursor_left, new Object[0]);
/* 344 */             this.cursorPos++;
/*     */           } 
/*     */         } else {
/* 347 */           this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
/* 348 */           this.cursorPos = curCol;
/*     */         } 
/* 350 */         currentPos = this.cursorPos;
/*     */       } 
/*     */     } 
/* 353 */     if (this.cursorPos != targetCursorPos) {
/* 354 */       moveVisualCursorTo((targetCursorPos < 0) ? currentPos : targetCursorPos, newLines);
/*     */     }
/* 356 */     this.oldLines = newLines;
/*     */     
/* 358 */     if (flush) {
/* 359 */       this.terminal.flush();
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean deleteLines(int nb) {
/* 364 */     return perform(InfoCmp.Capability.delete_line, InfoCmp.Capability.parm_delete_line, nb);
/*     */   }
/*     */   
/*     */   protected boolean insertLines(int nb) {
/* 368 */     return perform(InfoCmp.Capability.insert_line, InfoCmp.Capability.parm_insert_line, nb);
/*     */   }
/*     */   
/*     */   protected boolean insertChars(int nb) {
/* 372 */     return perform(InfoCmp.Capability.insert_character, InfoCmp.Capability.parm_ich, nb);
/*     */   }
/*     */   
/*     */   protected boolean deleteChars(int nb) {
/* 376 */     return perform(InfoCmp.Capability.delete_character, InfoCmp.Capability.parm_dch, nb);
/*     */   }
/*     */   
/*     */   protected boolean can(InfoCmp.Capability single, InfoCmp.Capability multi) {
/* 380 */     return (this.terminal.getStringCapability(single) != null || this.terminal.getStringCapability(multi) != null);
/*     */   }
/*     */   
/*     */   protected boolean perform(InfoCmp.Capability single, InfoCmp.Capability multi, int nb) {
/* 384 */     boolean hasMulti = (this.terminal.getStringCapability(multi) != null);
/* 385 */     boolean hasSingle = (this.terminal.getStringCapability(single) != null);
/* 386 */     if (hasMulti && (!hasSingle || cost(single) * nb > cost(multi))) {
/* 387 */       this.terminal.puts(multi, new Object[] { Integer.valueOf(nb) });
/* 388 */       return true;
/* 389 */     }  if (hasSingle) {
/* 390 */       for (int i = 0; i < nb; i++) {
/* 391 */         this.terminal.puts(single, new Object[0]);
/*     */       }
/* 393 */       return true;
/*     */     } 
/* 395 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int cost(InfoCmp.Capability cap) {
/* 400 */     return ((Integer)this.cost.computeIfAbsent(cap, this::computeCost)).intValue();
/*     */   }
/*     */   
/*     */   private int computeCost(InfoCmp.Capability cap) {
/* 404 */     String s = Curses.tputs(this.terminal.getStringCapability(cap), new Object[] { Integer.valueOf(0) });
/* 405 */     return (s != null) ? s.length() : Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   private static int[] longestCommon(List<AttributedString> l1, List<AttributedString> l2) {
/* 409 */     int start1 = 0;
/* 410 */     int start2 = 0;
/* 411 */     int max = 0;
/* 412 */     for (int i = 0; i < l1.size(); i++) {
/* 413 */       for (int j = 0; j < l2.size(); j++) {
/* 414 */         int x = 0;
/* 415 */         while (Objects.equals(l1.get(i + x), l2.get(j + x))) {
/* 416 */           x++;
/* 417 */           if (i + x >= l1.size() || j + x >= l2.size())
/*     */             break; 
/* 419 */         }  if (x > max) {
/* 420 */           max = x;
/* 421 */           start1 = i;
/* 422 */           start2 = j;
/*     */         } 
/*     */       } 
/*     */     } 
/* 426 */     (new int[3])[0] = start1; (new int[3])[1] = start2; (new int[3])[2] = max; return (max != 0) ? new int[3] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void moveVisualCursorTo(int targetPos, List<AttributedString> newLines) {
/* 435 */     if (this.cursorPos != targetPos) {
/* 436 */       boolean atRight = (targetPos % this.columns1 == this.columns);
/* 437 */       moveVisualCursorTo(targetPos - (atRight ? 1 : 0));
/* 438 */       if (atRight) {
/*     */ 
/*     */         
/* 441 */         int row = targetPos / this.columns1;
/*     */ 
/*     */         
/* 444 */         AttributedString lastChar = (row >= newLines.size()) ? AttributedString.EMPTY : ((AttributedString)newLines.get(row)).columnSubSequence(this.columns - 1, this.columns);
/* 445 */         if (lastChar.length() == 0) { rawPrint(32); }
/* 446 */         else { rawPrint(lastChar); }
/* 447 */          this.cursorPos++;
/*     */       } 
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
/*     */   protected int moveVisualCursorTo(int i1) {
/* 460 */     int i0 = this.cursorPos;
/* 461 */     if (i0 == i1) return i1; 
/* 462 */     int width = this.columns1;
/* 463 */     int l0 = i0 / width;
/* 464 */     int c0 = i0 % width;
/* 465 */     int l1 = i1 / width;
/* 466 */     int c1 = i1 % width;
/* 467 */     if (c0 == this.columns) {
/* 468 */       this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
/* 469 */       c0 = 0;
/*     */     } 
/* 471 */     if (l0 > l1) {
/* 472 */       perform(InfoCmp.Capability.cursor_up, InfoCmp.Capability.parm_up_cursor, l0 - l1);
/* 473 */     } else if (l0 < l1) {
/*     */       
/* 475 */       if (this.fullScreen) {
/* 476 */         if (!this.terminal.puts(InfoCmp.Capability.parm_down_cursor, new Object[] { Integer.valueOf(l1 - l0) })) {
/* 477 */           for (int i = l0; i < l1; i++) {
/* 478 */             this.terminal.puts(InfoCmp.Capability.cursor_down, new Object[0]);
/*     */           }
/* 480 */           if (this.cursorDownIsNewLine) {
/* 481 */             c0 = 0;
/*     */           }
/*     */         } 
/*     */       } else {
/* 485 */         this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
/* 486 */         rawPrint('\n', l1 - l0);
/* 487 */         c0 = 0;
/*     */       } 
/*     */     } 
/* 490 */     if (c0 != 0 && c1 == 0) {
/* 491 */       this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
/* 492 */     } else if (c0 < c1) {
/* 493 */       perform(InfoCmp.Capability.cursor_right, InfoCmp.Capability.parm_right_cursor, c1 - c0);
/* 494 */     } else if (c0 > c1) {
/* 495 */       perform(InfoCmp.Capability.cursor_left, InfoCmp.Capability.parm_left_cursor, c0 - c1);
/*     */     } 
/* 497 */     this.cursorPos = i1;
/* 498 */     return i1;
/*     */   }
/*     */   
/*     */   void rawPrint(char c, int num) {
/* 502 */     for (int i = 0; i < num; i++) {
/* 503 */       rawPrint(c);
/*     */     }
/*     */   }
/*     */   
/*     */   void rawPrint(int c) {
/* 508 */     this.terminal.writer().write(c);
/*     */   }
/*     */   
/*     */   void rawPrint(AttributedString str) {
/* 512 */     str.print(this.terminal);
/*     */   }
/*     */   
/*     */   public int wcwidth(String str) {
/* 516 */     return (str != null) ? AttributedString.fromAnsi(str).columnLength() : 0;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\Display.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */