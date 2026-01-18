/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.impl.AbstractTerminal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Status
/*     */ {
/*     */   protected final Terminal terminal;
/*     */   protected final boolean supported;
/*     */   protected boolean suspended = false;
/*     */   protected AttributedString borderString;
/*  64 */   protected int border = 0;
/*     */   protected Display display;
/*  66 */   protected List<AttributedString> lines = Collections.emptyList(); protected int scrollRegion;
/*     */   private final AttributedString ellipsis;
/*     */   
/*     */   public static Status getStatus(Terminal terminal) {
/*  70 */     return getStatus(terminal, true);
/*     */   }
/*     */   
/*     */   public static Optional<Status> getExistingStatus(Terminal terminal) {
/*  74 */     return Optional.ofNullable(getStatus(terminal, false));
/*     */   }
/*     */   
/*     */   public static Status getStatus(Terminal terminal, boolean create) {
/*  78 */     return (terminal instanceof AbstractTerminal) ? ((AbstractTerminal)terminal).getStatus(create) : null;
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
/*     */   private boolean isValid(Size size) {
/*  98 */     return (size.getRows() > 0 && size.getRows() < 1000 && size.getColumns() > 0 && size.getColumns() < 1000);
/*     */   }
/*     */   
/*     */   public void close() {
/* 102 */     if (this.supported) {
/* 103 */       this.terminal.puts(InfoCmp.Capability.save_cursor, new Object[0]);
/* 104 */       this.terminal.puts(InfoCmp.Capability.change_scroll_region, new Object[] { Integer.valueOf(0), Integer.valueOf(this.display.rows - 1) });
/* 105 */       this.terminal.puts(InfoCmp.Capability.restore_cursor, new Object[0]);
/* 106 */       this.terminal.flush();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBorder(boolean border) {
/* 111 */     this.border = border ? 1 : 0;
/*     */   }
/*     */   
/*     */   public void resize() {
/* 115 */     resize(this.terminal.getSize());
/*     */   }
/*     */   
/*     */   public void resize(Size size) {
/* 119 */     if (this.supported) {
/* 120 */       this.display.resize(size.getRows(), size.getColumns());
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/* 125 */     if (this.supported) {
/* 126 */       this.display.reset();
/* 127 */       this.scrollRegion = this.display.rows;
/* 128 */       this.terminal.puts(InfoCmp.Capability.change_scroll_region, new Object[] { Integer.valueOf(0), Integer.valueOf(this.scrollRegion) });
/*     */     } 
/*     */   }
/*     */   
/*     */   public void redraw() {
/* 133 */     if (this.suspended) {
/*     */       return;
/*     */     }
/* 136 */     update(this.lines);
/*     */   }
/*     */   
/*     */   public void hide() {
/* 140 */     update(Collections.emptyList());
/*     */   }
/*     */   
/*     */   public void update(List<AttributedString> lines) {
/* 144 */     update(lines, true);
/*     */   }
/*     */   
/* 147 */   public Status(Terminal terminal) { this
/* 148 */       .ellipsis = (new AttributedStringBuilder()).append("…", AttributedStyle.INVERSE).toAttributedString(); this.terminal = Objects.<Terminal>requireNonNull(terminal, "terminal can not be null");
/*     */     this.supported = (terminal.getStringCapability(InfoCmp.Capability.change_scroll_region) != null && terminal.getStringCapability(InfoCmp.Capability.save_cursor) != null && terminal.getStringCapability(InfoCmp.Capability.restore_cursor) != null && terminal.getStringCapability(InfoCmp.Capability.cursor_address) != null && isValid(terminal.getSize()));
/*     */     if (this.supported) {
/*     */       this.display = new MovingCursorDisplay(terminal);
/*     */       resize();
/*     */       this.display.reset();
/*     */       this.scrollRegion = this.display.rows - 1;
/* 155 */     }  } public void update(List<AttributedString> lines, boolean flush) { if (!this.supported) {
/*     */       return;
/*     */     }
/* 158 */     this.lines = new ArrayList<>(lines);
/* 159 */     if (this.suspended) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     lines = new ArrayList<>(lines);
/*     */     
/* 165 */     int rows = this.display.rows;
/* 166 */     int columns = this.display.columns;
/* 167 */     if (this.border == 1 && !lines.isEmpty() && rows > 1) {
/* 168 */       lines.add(0, getBorderString(columns));
/*     */     }
/*     */     
/* 171 */     for (int i = 0; i < lines.size(); i++) {
/* 172 */       AttributedString str = lines.get(i);
/* 173 */       if (str.columnLength() > columns) {
/*     */ 
/*     */ 
/*     */         
/* 177 */         str = (new AttributedStringBuilder(columns)).append(((AttributedString)lines.get(i)).columnSubSequence(0, columns - this.ellipsis.columnLength())).append(this.ellipsis).toAttributedString();
/* 178 */       } else if (str.columnLength() < columns) {
/*     */ 
/*     */ 
/*     */         
/* 182 */         str = (new AttributedStringBuilder(columns)).append(str).append(' ', columns - str.columnLength()).toAttributedString();
/*     */       } 
/* 184 */       lines.set(i, str);
/*     */     } 
/*     */     
/* 187 */     List<AttributedString> oldLines = this.display.oldLines;
/*     */     
/* 189 */     int newScrollRegion = this.display.rows - 1 - lines.size();
/*     */ 
/*     */     
/* 192 */     if (newScrollRegion < this.scrollRegion) {
/*     */       
/* 194 */       this.terminal.puts(InfoCmp.Capability.save_cursor, new Object[0]);
/* 195 */       this.terminal.puts(InfoCmp.Capability.cursor_address, new Object[] { Integer.valueOf(this.scrollRegion), Integer.valueOf(0) }); int j;
/* 196 */       for (j = newScrollRegion; j < this.scrollRegion; j++) {
/* 197 */         this.terminal.puts(InfoCmp.Capability.cursor_down, new Object[0]);
/*     */       }
/* 199 */       this.terminal.puts(InfoCmp.Capability.change_scroll_region, new Object[] { Integer.valueOf(0), Integer.valueOf(newScrollRegion) });
/* 200 */       this.terminal.puts(InfoCmp.Capability.restore_cursor, new Object[0]);
/* 201 */       for (j = newScrollRegion; j < this.scrollRegion; j++) {
/* 202 */         this.terminal.puts(InfoCmp.Capability.cursor_up, new Object[0]);
/*     */       }
/* 204 */       this.scrollRegion = newScrollRegion;
/* 205 */     } else if (newScrollRegion > this.scrollRegion) {
/* 206 */       this.terminal.puts(InfoCmp.Capability.save_cursor, new Object[0]);
/* 207 */       this.terminal.puts(InfoCmp.Capability.change_scroll_region, new Object[] { Integer.valueOf(0), Integer.valueOf(newScrollRegion) });
/* 208 */       this.terminal.puts(InfoCmp.Capability.restore_cursor, new Object[0]);
/* 209 */       this.scrollRegion = newScrollRegion;
/*     */     } 
/*     */ 
/*     */     
/* 213 */     List<AttributedString> toDraw = new ArrayList<>(lines);
/* 214 */     int nbToDraw = toDraw.size();
/* 215 */     int nbOldLines = oldLines.size();
/* 216 */     if (nbOldLines > nbToDraw) {
/* 217 */       this.terminal.puts(InfoCmp.Capability.save_cursor, new Object[0]);
/* 218 */       this.terminal.puts(InfoCmp.Capability.cursor_address, new Object[] { Integer.valueOf(this.display.rows - nbOldLines), Integer.valueOf(0) });
/* 219 */       for (int j = 0; j < nbOldLines - nbToDraw; j++) {
/* 220 */         this.terminal.puts(InfoCmp.Capability.clr_eol, new Object[0]);
/* 221 */         if (j < nbOldLines - nbToDraw - 1) {
/* 222 */           this.terminal.puts(InfoCmp.Capability.cursor_down, new Object[0]);
/*     */         }
/* 224 */         oldLines.remove(0);
/*     */       } 
/* 226 */       this.terminal.puts(InfoCmp.Capability.restore_cursor, new Object[0]);
/*     */     } 
/*     */     
/* 229 */     this.display.update(lines, -1, flush); }
/*     */ 
/*     */   
/*     */   private AttributedString getBorderString(int columns) {
/* 233 */     if (this.borderString == null || this.borderString.length() != columns) {
/* 234 */       char borderChar = '─';
/* 235 */       AttributedStringBuilder bb = new AttributedStringBuilder();
/* 236 */       for (int i = 0; i < columns; i++) {
/* 237 */         bb.append(borderChar);
/*     */       }
/* 239 */       this.borderString = bb.toAttributedString();
/*     */     } 
/* 241 */     return this.borderString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suspend() {
/* 250 */     if (!this.suspended) {
/* 251 */       this.suspended = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restore() {
/* 262 */     if (this.suspended) {
/* 263 */       this.suspended = false;
/* 264 */       update(this.lines);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/* 269 */     return size(this.lines);
/*     */   }
/*     */   
/*     */   private int size(List<?> lines) {
/* 273 */     int l = lines.size();
/* 274 */     return (l > 0) ? (l + this.border) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 279 */     return "Status[supported=" + this.supported + ']';
/*     */   }
/*     */   
/*     */   static class MovingCursorDisplay extends Display {
/*     */     protected int firstLine;
/*     */     
/*     */     public MovingCursorDisplay(Terminal terminal) {
/* 286 */       super(terminal, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void update(List<AttributedString> newLines, int targetCursorPos, boolean flush) {
/* 291 */       this.cursorPos = -1;
/* 292 */       this.firstLine = this.rows - newLines.size();
/* 293 */       super.update(newLines, targetCursorPos, flush);
/* 294 */       if (this.cursorPos != -1) {
/* 295 */         this.terminal.puts(InfoCmp.Capability.restore_cursor, new Object[0]);
/* 296 */         if (flush) {
/* 297 */           this.terminal.flush();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void moveVisualCursorTo(int targetPos, List<AttributedString> newLines) {
/* 304 */       initCursor();
/* 305 */       super.moveVisualCursorTo(targetPos, newLines);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int moveVisualCursorTo(int i1) {
/* 310 */       initCursor();
/* 311 */       return super.moveVisualCursorTo(i1);
/*     */     }
/*     */     
/*     */     void initCursor() {
/* 315 */       if (this.cursorPos == -1) {
/* 316 */         this.terminal.puts(InfoCmp.Capability.save_cursor, new Object[0]);
/* 317 */         this.terminal.puts(InfoCmp.Capability.cursor_address, new Object[] { Integer.valueOf(this.firstLine), Integer.valueOf(0) });
/* 318 */         this.cursorPos = 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\Status.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */