/*     */ package org.fusesource.jansi.io;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import org.fusesource.jansi.AnsiColors;
/*     */ import org.fusesource.jansi.AnsiMode;
/*     */ import org.fusesource.jansi.AnsiType;
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
/*  43 */   public static final byte[] RESET_CODE = "\033[0m".getBytes(StandardCharsets.US_ASCII);
/*     */   private static final int FIRST_ESC_CHAR = 27;
/*     */   private static final int SECOND_ESC_CHAR = 91;
/*     */   private static final int SECOND_OSC_CHAR = 93;
/*     */   private static final int BEL = 7;
/*     */   private static final int SECOND_ST_CHAR = 92;
/*     */   private static final int SECOND_CHARSET0_CHAR = 40;
/*     */   private static final int SECOND_CHARSET1_CHAR = 41;
/*     */   private AnsiProcessor ap;
/*     */   private static final int MAX_ESCAPE_SEQUENCE_LENGTH = 100;
/*     */   
/*     */   public static class ZeroWidthSupplier
/*     */     implements WidthSupplier
/*     */   {
/*     */     public int getTerminalWidth() {
/*  58 */       return 0;
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
/*  83 */   private final byte[] buffer = new byte[100];
/*  84 */   private int pos = 0;
/*     */   private int startOfValue;
/*  86 */   private final ArrayList<Object> options = new ArrayList();
/*  87 */   private int state = 0;
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
/* 110 */     super(os);
/* 111 */     this.width = width;
/* 112 */     this.processor = processor;
/* 113 */     this.type = type;
/* 114 */     this.colors = colors;
/* 115 */     this.installer = installer;
/* 116 */     this.uninstaller = uninstaller;
/* 117 */     this.resetAtUninstall = resetAtUninstall;
/* 118 */     this.cs = cs;
/* 119 */     setMode(mode);
/*     */   }
/*     */   
/*     */   public int getTerminalWidth() {
/* 123 */     return this.width.getTerminalWidth();
/*     */   }
/*     */   
/*     */   public AnsiType getType() {
/* 127 */     return this.type;
/*     */   }
/*     */   
/*     */   public AnsiColors getColors() {
/* 131 */     return this.colors;
/*     */   }
/*     */   
/*     */   public AnsiMode getMode() {
/* 135 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setMode(AnsiMode mode) {
/* 139 */     this
/*     */       
/* 141 */       .ap = (mode == AnsiMode.Strip) ? new AnsiProcessor(this.out) : ((mode == AnsiMode.Force || this.processor == null) ? new ColorsAnsiProcessor(this.out, this.colors) : this.processor);
/* 142 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public boolean isResetAtUninstall() {
/* 146 */     return this.resetAtUninstall;
/*     */   }
/*     */   
/*     */   public void setResetAtUninstall(boolean resetAtUninstall) {
/* 150 */     this.resetAtUninstall = resetAtUninstall;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int data) throws IOException {
/* 158 */     switch (this.state) {
/*     */       case 0:
/* 160 */         if (data == 27) {
/* 161 */           this.buffer[this.pos++] = (byte)data;
/* 162 */           this.state = 1; break;
/*     */         } 
/* 164 */         this.out.write(data);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 169 */         this.buffer[this.pos++] = (byte)data;
/* 170 */         if (data == 91) {
/* 171 */           this.state = 2; break;
/* 172 */         }  if (data == 93) {
/* 173 */           this.state = 5; break;
/* 174 */         }  if (data == 40) {
/* 175 */           this.options.add(Integer.valueOf(0));
/* 176 */           this.state = 9; break;
/* 177 */         }  if (data == 41) {
/* 178 */           this.options.add(Integer.valueOf(1));
/* 179 */           this.state = 9; break;
/*     */         } 
/* 181 */         reset(false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 186 */         this.buffer[this.pos++] = (byte)data;
/* 187 */         if (34 == data) {
/* 188 */           this.startOfValue = this.pos - 1;
/* 189 */           this.state = 3; break;
/* 190 */         }  if (48 <= data && data <= 57) {
/* 191 */           this.startOfValue = this.pos - 1;
/* 192 */           this.state = 4; break;
/* 193 */         }  if (59 == data) {
/* 194 */           this.options.add(null); break;
/* 195 */         }  if (63 == data) {
/* 196 */           this.options.add(Character.valueOf('?')); break;
/* 197 */         }  if (61 == data) {
/* 198 */           this.options.add(Character.valueOf('=')); break;
/*     */         } 
/* 200 */         processEscapeCommand(data);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 207 */         this.buffer[this.pos++] = (byte)data;
/* 208 */         if (48 > data || data > 57) {
/* 209 */           String strValue = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
/* 210 */           Integer value = Integer.valueOf(strValue);
/* 211 */           this.options.add(value);
/* 212 */           if (data == 59) {
/* 213 */             this.state = 2; break;
/*     */           } 
/* 215 */           processEscapeCommand(data);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 221 */         this.buffer[this.pos++] = (byte)data;
/* 222 */         if (34 != data) {
/* 223 */           String value = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue, this.cs);
/* 224 */           this.options.add(value);
/* 225 */           if (data == 59) {
/* 226 */             this.state = 2; break;
/*     */           } 
/* 228 */           processEscapeCommand(data);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 234 */         this.buffer[this.pos++] = (byte)data;
/* 235 */         if (48 <= data && data <= 57) {
/* 236 */           this.startOfValue = this.pos - 1;
/* 237 */           this.state = 6; break;
/*     */         } 
/* 239 */         reset(false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 244 */         this.buffer[this.pos++] = (byte)data;
/* 245 */         if (59 == data) {
/* 246 */           String strValue = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
/* 247 */           Integer value = Integer.valueOf(strValue);
/* 248 */           this.options.add(value);
/* 249 */           this.startOfValue = this.pos;
/* 250 */           this.state = 7; break;
/* 251 */         }  if (48 <= data && data <= 57) {
/*     */           break;
/*     */         }
/*     */         
/* 255 */         reset(false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 260 */         this.buffer[this.pos++] = (byte)data;
/* 261 */         if (7 == data) {
/* 262 */           String value = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue, this.cs);
/* 263 */           this.options.add(value);
/* 264 */           processOperatingSystemCommand(); break;
/* 265 */         }  if (27 == data) {
/* 266 */           this.state = 8;
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 273 */         this.buffer[this.pos++] = (byte)data;
/* 274 */         if (92 == data) {
/* 275 */           String value = new String(this.buffer, this.startOfValue, this.pos - 2 - this.startOfValue, this.cs);
/* 276 */           this.options.add(value);
/* 277 */           processOperatingSystemCommand(); break;
/*     */         } 
/* 279 */         this.state = 7;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 9:
/* 284 */         this.options.add(Character.valueOf((char)data));
/* 285 */         processCharsetSelect();
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 290 */     if (this.pos >= this.buffer.length) {
/* 291 */       reset(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void processCharsetSelect() throws IOException {
/*     */     try {
/* 297 */       reset((this.ap != null && this.ap.processCharsetSelect(this.options)));
/* 298 */     } catch (RuntimeException e) {
/* 299 */       reset(true);
/* 300 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processOperatingSystemCommand() throws IOException {
/*     */     try {
/* 306 */       reset((this.ap != null && this.ap.processOperatingSystemCommand(this.options)));
/* 307 */     } catch (RuntimeException e) {
/* 308 */       reset(true);
/* 309 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processEscapeCommand(int data) throws IOException {
/*     */     try {
/* 315 */       reset((this.ap != null && this.ap.processEscapeCommand(this.options, data)));
/* 316 */     } catch (RuntimeException e) {
/* 317 */       reset(true);
/* 318 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void reset(boolean skipBuffer) throws IOException {
/* 328 */     if (!skipBuffer) {
/* 329 */       this.out.write(this.buffer, 0, this.pos);
/*     */     }
/* 331 */     this.pos = 0;
/* 332 */     this.startOfValue = 0;
/* 333 */     this.options.clear();
/* 334 */     this.state = 0;
/*     */   }
/*     */   
/*     */   public void install() throws IOException {
/* 338 */     if (this.installer != null) {
/* 339 */       this.installer.run();
/*     */     }
/*     */   }
/*     */   
/*     */   public void uninstall() throws IOException {
/* 344 */     if (this.resetAtUninstall && this.type != AnsiType.Redirected && this.type != AnsiType.Unsupported) {
/* 345 */       setMode(AnsiMode.Default);
/* 346 */       write(RESET_CODE);
/* 347 */       flush();
/*     */     } 
/* 349 */     if (this.uninstaller != null) {
/* 350 */       this.uninstaller.run();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 356 */     uninstall();
/* 357 */     super.close();
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface WidthSupplier {
/*     */     int getTerminalWidth();
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface IoRunnable {
/*     */     void run() throws IOException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\io\AnsiOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */