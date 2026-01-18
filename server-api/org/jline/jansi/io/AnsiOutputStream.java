/*     */ package org.jline.jansi.io;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import org.jline.jansi.AnsiColors;
/*     */ import org.jline.jansi.AnsiMode;
/*     */ import org.jline.jansi.AnsiType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnsiOutputStream
/*     */   extends FilterOutputStream
/*     */ {
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
/*     */   private static final int FIRST_ESC_CHAR = 27;
/*     */   private static final int SECOND_ESC_CHAR = 91;
/*     */   private static final int SECOND_OSC_CHAR = 93;
/*     */   private static final int BEL = 7;
/*     */   private static final int SECOND_ST_CHAR = 92;
/*     */   private static final int SECOND_CHARSET0_CHAR = 40;
/*  36 */   public static final byte[] RESET_CODE = "\033[0m".getBytes(StandardCharsets.US_ASCII);
/*     */   private static final int SECOND_CHARSET1_CHAR = 41;
/*     */   private AnsiProcessor ap;
/*     */   private static final int MAX_ESCAPE_SEQUENCE_LENGTH = 100;
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface WidthSupplier {
/*     */     int getTerminalWidth(); }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface IoRunnable {
/*     */     void run() throws IOException; }
/*     */   
/*     */   public static class ZeroWidthSupplier implements WidthSupplier {
/*     */     public int getTerminalWidth() {
/*  51 */       return 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private final byte[] buffer = new byte[100];
/*  77 */   private int pos = 0;
/*     */   private int startOfValue;
/*  79 */   private final ArrayList<Object> options = new ArrayList();
/*  80 */   private int state = 0;
/*     */ 
/*     */   
/*     */   private final Charset cs;
/*     */   
/*     */   private final WidthSupplier width;
/*     */   
/*     */   private final AnsiProcessor processor;
/*     */   
/*     */   private final AnsiType type;
/*     */   
/*     */   private final AnsiColors colors;
/*     */   
/*     */   private final IoRunnable installer;
/*     */   
/*     */   private final IoRunnable uninstaller;
/*     */   
/*     */   private AnsiMode mode;
/*     */   
/*     */   private boolean resetAtUninstall;
/*     */ 
/*     */   
/*     */   public AnsiOutputStream(OutputStream os, WidthSupplier width, AnsiMode mode, AnsiProcessor processor, AnsiType type, AnsiColors colors, Charset cs, IoRunnable installer, IoRunnable uninstaller, boolean resetAtUninstall) {
/* 103 */     super(os);
/* 104 */     this.width = width;
/* 105 */     this.processor = processor;
/* 106 */     this.type = type;
/* 107 */     this.colors = colors;
/* 108 */     this.installer = installer;
/* 109 */     this.uninstaller = uninstaller;
/* 110 */     this.resetAtUninstall = resetAtUninstall;
/* 111 */     this.cs = cs;
/* 112 */     setMode(mode);
/*     */   }
/*     */   
/*     */   public int getTerminalWidth() {
/* 116 */     return this.width.getTerminalWidth();
/*     */   }
/*     */   
/*     */   public AnsiType getType() {
/* 120 */     return this.type;
/*     */   }
/*     */   
/*     */   public AnsiColors getColors() {
/* 124 */     return this.colors;
/*     */   }
/*     */   
/*     */   public AnsiMode getMode() {
/* 128 */     return this.mode;
/*     */   }
/*     */   
/*     */   public final void setMode(AnsiMode mode) {
/* 132 */     this
/*     */       
/* 134 */       .ap = (mode == AnsiMode.Strip) ? new AnsiProcessor(this.out) : ((mode == AnsiMode.Force || this.processor == null) ? new ColorsAnsiProcessor(this.out, this.colors) : this.processor);
/* 135 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public boolean isResetAtUninstall() {
/* 139 */     return this.resetAtUninstall;
/*     */   }
/*     */   
/*     */   public void setResetAtUninstall(boolean resetAtUninstall) {
/* 143 */     this.resetAtUninstall = resetAtUninstall;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int data) throws IOException {
/* 151 */     switch (this.state) {
/*     */       case 0:
/* 153 */         if (data == 27) {
/* 154 */           this.buffer[this.pos++] = (byte)data;
/* 155 */           this.state = 1; break;
/*     */         } 
/* 157 */         this.out.write(data);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 162 */         this.buffer[this.pos++] = (byte)data;
/* 163 */         if (data == 91) {
/* 164 */           this.state = 2; break;
/* 165 */         }  if (data == 93) {
/* 166 */           this.state = 5; break;
/* 167 */         }  if (data == 40) {
/* 168 */           this.options.add(Integer.valueOf(0));
/* 169 */           this.state = 9; break;
/* 170 */         }  if (data == 41) {
/* 171 */           this.options.add(Integer.valueOf(1));
/* 172 */           this.state = 9; break;
/*     */         } 
/* 174 */         reset(false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 179 */         this.buffer[this.pos++] = (byte)data;
/* 180 */         if (34 == data) {
/* 181 */           this.startOfValue = this.pos - 1;
/* 182 */           this.state = 3; break;
/* 183 */         }  if (48 <= data && data <= 57) {
/* 184 */           this.startOfValue = this.pos - 1;
/* 185 */           this.state = 4; break;
/* 186 */         }  if (59 == data) {
/* 187 */           this.options.add(null); break;
/* 188 */         }  if (63 == data) {
/* 189 */           this.options.add(Character.valueOf('?')); break;
/* 190 */         }  if (61 == data) {
/* 191 */           this.options.add(Character.valueOf('=')); break;
/*     */         } 
/* 193 */         processEscapeCommand(data);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 200 */         this.buffer[this.pos++] = (byte)data;
/* 201 */         if (48 > data || data > 57) {
/* 202 */           String strValue = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
/* 203 */           Integer value = Integer.valueOf(strValue);
/* 204 */           this.options.add(value);
/* 205 */           if (data == 59) {
/* 206 */             this.state = 2; break;
/*     */           } 
/* 208 */           processEscapeCommand(data);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 214 */         this.buffer[this.pos++] = (byte)data;
/* 215 */         if (34 != data) {
/* 216 */           String value = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue, this.cs);
/* 217 */           this.options.add(value);
/* 218 */           if (data == 59) {
/* 219 */             this.state = 2; break;
/*     */           } 
/* 221 */           processEscapeCommand(data);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 227 */         this.buffer[this.pos++] = (byte)data;
/* 228 */         if (48 <= data && data <= 57) {
/* 229 */           this.startOfValue = this.pos - 1;
/* 230 */           this.state = 6; break;
/*     */         } 
/* 232 */         reset(false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 237 */         this.buffer[this.pos++] = (byte)data;
/* 238 */         if (59 == data) {
/* 239 */           String strValue = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
/* 240 */           Integer value = Integer.valueOf(strValue);
/* 241 */           this.options.add(value);
/* 242 */           this.startOfValue = this.pos;
/* 243 */           this.state = 7; break;
/* 244 */         }  if (48 <= data && data <= 57) {
/*     */           break;
/*     */         }
/*     */         
/* 248 */         reset(false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 253 */         this.buffer[this.pos++] = (byte)data;
/* 254 */         if (7 == data) {
/* 255 */           String value = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue, this.cs);
/* 256 */           this.options.add(value);
/* 257 */           processOperatingSystemCommand(); break;
/* 258 */         }  if (27 == data) {
/* 259 */           this.state = 8;
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 266 */         this.buffer[this.pos++] = (byte)data;
/* 267 */         if (92 == data) {
/* 268 */           String value = new String(this.buffer, this.startOfValue, this.pos - 2 - this.startOfValue, this.cs);
/* 269 */           this.options.add(value);
/* 270 */           processOperatingSystemCommand(); break;
/*     */         } 
/* 272 */         this.state = 7;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 9:
/* 277 */         this.options.add(Character.valueOf((char)data));
/* 278 */         processCharsetSelect();
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 283 */     if (this.pos >= this.buffer.length) {
/* 284 */       reset(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void processCharsetSelect() throws IOException {
/*     */     try {
/* 290 */       reset((this.ap != null && this.ap.processCharsetSelect(this.options)));
/* 291 */     } catch (RuntimeException e) {
/* 292 */       reset(true);
/* 293 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processOperatingSystemCommand() throws IOException {
/*     */     try {
/* 299 */       reset((this.ap != null && this.ap.processOperatingSystemCommand(this.options)));
/* 300 */     } catch (RuntimeException e) {
/* 301 */       reset(true);
/* 302 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processEscapeCommand(int data) throws IOException {
/*     */     try {
/* 308 */       reset((this.ap != null && this.ap.processEscapeCommand(this.options, data)));
/* 309 */     } catch (RuntimeException e) {
/* 310 */       reset(true);
/* 311 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void reset(boolean skipBuffer) throws IOException {
/* 321 */     if (!skipBuffer) {
/* 322 */       this.out.write(this.buffer, 0, this.pos);
/*     */     }
/* 324 */     this.pos = 0;
/* 325 */     this.startOfValue = 0;
/* 326 */     this.options.clear();
/* 327 */     this.state = 0;
/*     */   }
/*     */   
/*     */   public void install() throws IOException {
/* 331 */     if (this.installer != null) {
/* 332 */       this.installer.run();
/*     */     }
/*     */   }
/*     */   
/*     */   public void uninstall() throws IOException {
/* 337 */     if (this.resetAtUninstall && this.type != AnsiType.Redirected && this.type != AnsiType.Unsupported) {
/* 338 */       setMode(AnsiMode.Default);
/* 339 */       write(RESET_CODE);
/* 340 */       flush();
/*     */     } 
/* 342 */     if (this.uninstaller != null) {
/* 343 */       this.uninstaller.run();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 349 */     uninstall();
/* 350 */     super.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\jansi\io\AnsiOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */