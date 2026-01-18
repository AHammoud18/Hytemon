/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public class ColorPalette
/*     */ {
/*     */   public static final String XTERM_INITC = "\\E]4;%p1%d;rgb\\:%p2%{255}%*%{1000}%/%2.2X/%p3%{255}%*%{1000}%/%2.2X/%p4%{255}%*%{1000}%/%2.2X\\E\\\\";
/*  55 */   public static final ColorPalette DEFAULT = new ColorPalette();
/*     */   
/*     */   private final Terminal terminal;
/*     */   private String distanceName;
/*     */   private Colors.Distance distance;
/*     */   private boolean osc4;
/*     */   private int[] palette;
/*     */   
/*     */   public ColorPalette() {
/*  64 */     this.terminal = null;
/*  65 */     this.distanceName = null;
/*  66 */     this.palette = Colors.DEFAULT_COLORS_256;
/*     */   }
/*     */   
/*     */   public ColorPalette(Terminal terminal) throws IOException {
/*  70 */     this(terminal, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ColorPalette(Terminal terminal, String distance) throws IOException {
/*  75 */     this.terminal = terminal;
/*  76 */     this.distanceName = distance;
/*  77 */     loadPalette(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDistanceName() {
/*  85 */     return this.distanceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDistance(String name) {
/*  93 */     this.distanceName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canChange() {
/* 101 */     return (this.terminal != null && this.terminal.getBooleanCapability(InfoCmp.Capability.can_change));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean loadPalette() throws IOException {
/* 112 */     if (!this.osc4) {
/* 113 */       loadPalette(true);
/*     */     }
/* 115 */     return this.osc4;
/*     */   }
/*     */   
/*     */   protected void loadPalette(boolean doLoad) throws IOException {
/* 119 */     if (this.terminal != null) {
/* 120 */       int[] pal = doLoad ? doLoad(this.terminal) : null;
/* 121 */       if (pal != null) {
/* 122 */         this.palette = pal;
/* 123 */         this.osc4 = true;
/*     */       } else {
/* 125 */         Integer cols = this.terminal.getNumericCapability(InfoCmp.Capability.max_colors);
/* 126 */         if (cols != null) {
/* 127 */           if (cols.intValue() == Colors.DEFAULT_COLORS_88.length) {
/* 128 */             this.palette = Colors.DEFAULT_COLORS_88;
/*     */           } else {
/* 130 */             this.palette = Arrays.copyOf(Colors.DEFAULT_COLORS_256, Math.min(cols.intValue(), 256));
/*     */           } 
/*     */         } else {
/* 133 */           this.palette = Arrays.copyOf(Colors.DEFAULT_COLORS_256, 256);
/*     */         } 
/* 135 */         this.osc4 = false;
/*     */       } 
/*     */     } else {
/* 138 */       this.palette = Colors.DEFAULT_COLORS_256;
/* 139 */       this.osc4 = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 148 */     return this.palette.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor(int index) {
/* 157 */     return this.palette[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(int index, int color) {
/* 166 */     this.palette[index] = color;
/* 167 */     if (canChange()) {
/* 168 */       String initc = this.terminal.getStringCapability(InfoCmp.Capability.initialize_color);
/* 169 */       if (initc != null || this.osc4) {
/*     */         
/* 171 */         int r = (color >> 16 & 0xFF) * 1000 / 255 + 1;
/* 172 */         int g = (color >> 8 & 0xFF) * 1000 / 255 + 1;
/* 173 */         int b = (color & 0xFF) * 1000 / 255 + 1;
/* 174 */         if (initc == null)
/*     */         {
/* 176 */           initc = "\\E]4;%p1%d;rgb\\:%p2%{255}%*%{1000}%/%2.2X/%p3%{255}%*%{1000}%/%2.2X/%p4%{255}%*%{1000}%/%2.2X\\E\\\\";
/*     */         }
/* 178 */         Curses.tputs(this.terminal.writer(), initc, new Object[] { Integer.valueOf(index), Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b) });
/* 179 */         this.terminal.writer().flush();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isReal() {
/* 185 */     return this.osc4;
/*     */   }
/*     */   
/*     */   public int round(int r, int g, int b) {
/* 189 */     return Colors.roundColor((r << 16) + (g << 8) + b, this.palette, this.palette.length, getDist());
/*     */   }
/*     */   
/*     */   public int round(int col) {
/* 193 */     if (col >= this.palette.length) {
/* 194 */       col = Colors.roundColor(DEFAULT.getColor(col), this.palette, this.palette.length, getDist());
/*     */     }
/* 196 */     return col;
/*     */   }
/*     */   
/*     */   protected Colors.Distance getDist() {
/* 200 */     if (this.distance == null) {
/* 201 */       this.distance = Colors.getDistance(this.distanceName);
/*     */     }
/* 203 */     return this.distance;
/*     */   }
/*     */   
/*     */   private static int[] doLoad(Terminal terminal) throws IOException {
/* 207 */     PrintWriter writer = terminal.writer();
/* 208 */     NonBlockingReader reader = terminal.reader();
/*     */     
/* 210 */     int[] palette = new int[256];
/* 211 */     for (int i = 0; i < 16; i++) {
/* 212 */       int k; StringBuilder req = new StringBuilder(1024);
/* 213 */       req.append("\033]4");
/* 214 */       for (int j = 0; j < 16; j++) {
/* 215 */         req.append(';').append(i * 16 + j).append(";?");
/*     */       }
/* 217 */       req.append("\033\\");
/* 218 */       writer.write(req.toString());
/* 219 */       writer.flush();
/*     */       
/* 221 */       boolean black = true;
/* 222 */       for (int m = 0; m < 16 && 
/* 223 */         reader.peek(50L) >= 0; ) {
/*     */         int c;
/*     */         
/* 226 */         if (reader.read(10L) != 27 || reader
/* 227 */           .read(10L) != 93 || reader
/* 228 */           .read(10L) != 52 || reader
/* 229 */           .read(10L) != 59) {
/* 230 */           return null;
/*     */         }
/* 232 */         int idx = 0;
/*     */         
/*     */         while (true) {
/* 235 */           c = reader.read(10L);
/* 236 */           if (c >= 48 && c <= 57)
/* 237 */           { idx = idx * 10 + c - 48; continue; }  break;
/* 238 */         }  if (c == 59) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 244 */           if (idx > 255) {
/* 245 */             return null;
/*     */           }
/* 247 */           if (reader.read(10L) != 114 || reader
/* 248 */             .read(10L) != 103 || reader
/* 249 */             .read(10L) != 98 || reader
/* 250 */             .read(10L) != 58) {
/* 251 */             return null;
/*     */           }
/* 253 */           StringBuilder sb = new StringBuilder(16);
/* 254 */           List<String> rgb = new ArrayList<>();
/*     */           while (true) {
/* 256 */             c = reader.read(10L);
/* 257 */             if (c == 7) {
/* 258 */               rgb.add(sb.toString()); break;
/*     */             } 
/* 260 */             if (c == 27) {
/* 261 */               c = reader.read(10L);
/* 262 */               if (c == 92) {
/* 263 */                 rgb.add(sb.toString());
/*     */                 break;
/*     */               } 
/* 266 */               return null;
/*     */             } 
/* 268 */             if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
/* 269 */               sb.append((char)c); continue;
/* 270 */             }  if (c == 47) {
/* 271 */               rgb.add(sb.toString());
/* 272 */               sb.setLength(0);
/*     */             } 
/*     */           } 
/* 275 */           if (rgb.size() != 3) {
/* 276 */             return null;
/*     */           }
/*     */           
/* 279 */           double r = Integer.parseInt(rgb.get(0), 16) / ((1 << 4 * ((String)rgb.get(0)).length()) - 1.0D);
/*     */           
/* 281 */           double g = Integer.parseInt(rgb.get(1), 16) / ((1 << 4 * ((String)rgb.get(1)).length()) - 1.0D);
/*     */           
/* 283 */           double b = Integer.parseInt(rgb.get(2), 16) / ((1 << 4 * ((String)rgb.get(2)).length()) - 1.0D);
/* 284 */           palette[idx] = (int)((Math.round(r * 255.0D) << 16L) + (Math.round(g * 255.0D) << 8L) + Math.round(b * 255.0D));
/* 285 */           k = black & ((palette[idx] == 0) ? 1 : 0); m++;
/*     */         }  return null;
/* 287 */       }  if (k != 0) {
/*     */         break;
/*     */       }
/*     */     } 
/* 291 */     int max = 256;
/* 292 */     while (max > 0 && palette[--max] == 0);
/*     */     
/* 294 */     return Arrays.copyOfRange(palette, 0, max + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultForeground() {
/* 302 */     if (this.terminal == null) {
/* 303 */       return -1;
/*     */     }
/* 305 */     return this.terminal.getDefaultForegroundColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultBackground() {
/* 313 */     if (this.terminal == null) {
/* 314 */       return -1;
/*     */     }
/* 316 */     return this.terminal.getDefaultBackgroundColor();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 321 */     return "ColorPalette[length=" + getLength() + ", distance='" + getDist() + "']";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\ColorPalette.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */