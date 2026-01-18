/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ import org.jline.utils.Curses;
/*     */ import org.jline.utils.InfoCmp;
/*     */ import org.jline.utils.Log;
/*     */ import org.jline.utils.NonBlocking;
/*     */ import org.jline.utils.NonBlockingInputStream;
/*     */ import org.jline.utils.NonBlockingPumpReader;
/*     */ import org.jline.utils.NonBlockingReader;
/*     */ import org.jline.utils.ShutdownHooks;
/*     */ import org.jline.utils.Signals;
/*     */ import org.jline.utils.WriterOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractWindowsTerminal<Console>
/*     */   extends AbstractTerminal
/*     */ {
/*     */   public static final String TYPE_WINDOWS = "windows";
/*     */   public static final String TYPE_WINDOWS_256_COLOR = "windows-256color";
/*     */   protected static final int FOREGROUND_BLUE = 1;
/*     */   protected static final int FOREGROUND_GREEN = 2;
/*     */   protected static final int FOREGROUND_RED = 4;
/*     */   protected static final int FOREGROUND_INTENSITY = 8;
/*     */   protected static final int BACKGROUND_BLUE = 16;
/*     */   protected static final int BACKGROUND_GREEN = 32;
/*     */   protected static final int BACKGROUND_RED = 64;
/*     */   protected static final int BACKGROUND_INTENSITY = 128;
/*     */   public static final String TYPE_WINDOWS_CONEMU = "windows-conemu";
/*     */   public static final String TYPE_WINDOWS_VTP = "windows-vtp";
/*     */   public static final int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
/*     */   private static final int UTF8_CODE_PAGE = 65001;
/*     */   protected static final int ENABLE_PROCESSED_INPUT = 1;
/*     */   protected static final int ENABLE_LINE_INPUT = 2;
/*     */   protected static final int ENABLE_ECHO_INPUT = 4;
/*     */   protected static final int ENABLE_WINDOW_INPUT = 8;
/*     */   protected static final int ENABLE_MOUSE_INPUT = 16;
/*     */   protected static final int ENABLE_INSERT_MODE = 32;
/*     */   protected static final int ENABLE_QUICK_EDIT_MODE = 64;
/*     */   protected static final int ENABLE_EXTENDED_FLAGS = 128;
/*     */   protected final Writer slaveInputPipe;
/*     */   protected final NonBlockingInputStream input;
/*     */   protected final OutputStream output;
/*     */   protected final NonBlockingReader reader;
/*     */   protected final PrintWriter writer;
/* 115 */   protected final Map<Terminal.Signal, Object> nativeHandlers = new HashMap<>();
/*     */   protected final ShutdownHooks.Task closer;
/* 117 */   protected final Attributes attributes = new Attributes();
/*     */   
/*     */   protected final Console inConsole;
/*     */   protected final Console outConsole;
/*     */   protected final int originalInConsoleMode;
/*     */   protected final int originalOutConsoleMode;
/*     */   private final TerminalProvider provider;
/*     */   private final SystemStream systemStream;
/* 125 */   protected final Object lock = new Object();
/*     */   
/*     */   protected boolean paused = true;
/*     */   protected Thread pump;
/* 129 */   protected Terminal.MouseTracking tracking = Terminal.MouseTracking.Off;
/*     */   
/*     */   protected boolean focusTracking = false;
/*     */   
/*     */   private volatile boolean closing;
/*     */   
/*     */   protected boolean skipNextLf;
/*     */   static final int SHIFT_FLAG = 1;
/*     */   static final int ALT_FLAG = 2;
/*     */   static final int CTRL_FLAG = 4;
/*     */   static final int RIGHT_ALT_PRESSED = 1;
/*     */   static final int LEFT_ALT_PRESSED = 2;
/*     */   static final int RIGHT_CTRL_PRESSED = 4;
/*     */   static final int LEFT_CTRL_PRESSED = 8;
/*     */   static final int SHIFT_PRESSED = 16;
/*     */   static final int NUMLOCK_ON = 32;
/*     */   static final int SCROLLLOCK_ON = 64;
/*     */   static final int CAPSLOCK_ON = 128;
/*     */   
/*     */   public AbstractWindowsTerminal(TerminalProvider provider, SystemStream systemStream, Writer writer, String name, String type, Charset encoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, Console inConsole, int inConsoleMode, Console outConsole, int outConsoleMode) throws IOException {
/* 149 */     this(provider, systemStream, writer, name, type, encoding, encoding, encoding, nativeSignals, signalHandler, inConsole, inConsoleMode, outConsole, outConsoleMode);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractWindowsTerminal(TerminalProvider provider, SystemStream systemStream, Writer writer, String name, String type, Charset encoding, Charset inputEncoding, Charset outputEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, Console inConsole, int inConsoleMode, Console outConsole, int outConsoleMode) throws IOException {
/* 183 */     super(name, type, encoding, inputEncoding, outputEncoding, signalHandler);
/* 184 */     this.provider = provider;
/* 185 */     this.systemStream = systemStream;
/* 186 */     NonBlockingPumpReader reader = NonBlocking.nonBlockingPumpReader();
/* 187 */     this.slaveInputPipe = reader.getWriter();
/* 188 */     this.reader = (NonBlockingReader)reader;
/* 189 */     this.input = NonBlocking.nonBlockingStream((NonBlockingReader)reader, inputEncoding());
/* 190 */     this.writer = new PrintWriter(writer);
/* 191 */     this.output = (OutputStream)new WriterOutputStream(writer, outputEncoding());
/* 192 */     this.inConsole = inConsole;
/* 193 */     this.outConsole = outConsole;
/* 194 */     parseInfoCmp();
/*     */     
/* 196 */     this.originalInConsoleMode = inConsoleMode;
/* 197 */     this.originalOutConsoleMode = outConsoleMode;
/* 198 */     this.attributes.setLocalFlag(Attributes.LocalFlag.ISIG, true);
/* 199 */     this.attributes.setControlChar(Attributes.ControlChar.VINTR, ctrl('C'));
/* 200 */     this.attributes.setControlChar(Attributes.ControlChar.VEOF, ctrl('D'));
/* 201 */     this.attributes.setControlChar(Attributes.ControlChar.VSUSP, ctrl('Z'));
/*     */     
/* 203 */     if (nativeSignals) {
/* 204 */       for (Terminal.Signal signal : Terminal.Signal.values()) {
/* 205 */         if (signalHandler == Terminal.SignalHandler.SIG_DFL) {
/* 206 */           this.nativeHandlers.put(signal, Signals.registerDefault(signal.name()));
/*     */         } else {
/* 208 */           this.nativeHandlers.put(signal, Signals.register(signal.name(), () -> raise(signal)));
/*     */         } 
/*     */       } 
/*     */     }
/* 212 */     this.closer = this::close;
/* 213 */     ShutdownHooks.add(this.closer);
/*     */     
/* 215 */     if ("windows-conemu".equals(getType()) && 
/* 216 */       !Boolean.getBoolean("org.jline.terminal.conemu.disable-activate")) {
/* 217 */       writer.write("\033[9999E");
/* 218 */       writer.flush();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Terminal.SignalHandler handle(Terminal.Signal signal, Terminal.SignalHandler handler) {
/* 224 */     Terminal.SignalHandler prev = super.handle(signal, handler);
/* 225 */     if (prev != handler) {
/* 226 */       if (handler == Terminal.SignalHandler.SIG_DFL) {
/* 227 */         Signals.registerDefault(signal.name());
/*     */       } else {
/* 229 */         Signals.register(signal.name(), () -> raise(signal));
/*     */       } 
/*     */     }
/* 232 */     return prev;
/*     */   }
/*     */   
/*     */   public NonBlockingReader reader() {
/* 236 */     return this.reader;
/*     */   }
/*     */   
/*     */   public PrintWriter writer() {
/* 240 */     return this.writer;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream input() {
/* 245 */     return (InputStream)this.input;
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream output() {
/* 250 */     return this.output;
/*     */   }
/*     */   
/*     */   public Attributes getAttributes() {
/* 254 */     int mode = getConsoleMode(this.inConsole);
/* 255 */     if ((mode & 0x4) != 0) {
/* 256 */       this.attributes.setLocalFlag(Attributes.LocalFlag.ECHO, true);
/*     */     }
/* 258 */     if ((mode & 0x2) != 0) {
/* 259 */       this.attributes.setLocalFlag(Attributes.LocalFlag.ICANON, true);
/*     */     }
/* 261 */     return new Attributes(this.attributes);
/*     */   }
/*     */   
/*     */   public void setAttributes(Attributes attr) {
/* 265 */     this.attributes.copy(attr);
/* 266 */     updateConsoleMode();
/*     */   }
/*     */   
/*     */   protected void updateConsoleMode() {
/* 270 */     int mode = 8;
/* 271 */     if (this.attributes.getLocalFlag(Attributes.LocalFlag.ISIG)) {
/* 272 */       mode |= 0x1;
/*     */     }
/* 274 */     if (this.attributes.getLocalFlag(Attributes.LocalFlag.ECHO)) {
/* 275 */       mode |= 0x4;
/*     */     }
/* 277 */     if (this.attributes.getLocalFlag(Attributes.LocalFlag.ICANON)) {
/* 278 */       mode |= 0x2;
/*     */     }
/* 280 */     if (this.tracking != Terminal.MouseTracking.Off) {
/* 281 */       mode |= 0x10;
/*     */ 
/*     */       
/* 284 */       mode |= 0x80;
/*     */     } 
/* 286 */     setConsoleMode(this.inConsole, mode);
/*     */   }
/*     */   
/*     */   protected int ctrl(char key) {
/* 290 */     return Character.toUpperCase(key) & 0x1F;
/*     */   }
/*     */   
/*     */   public void setSize(Size size) {
/* 294 */     throw new UnsupportedOperationException("Can not resize windows terminal");
/*     */   }
/*     */   
/*     */   protected void doClose() throws IOException {
/* 298 */     super.doClose();
/* 299 */     this.closing = true;
/* 300 */     if (this.pump != null) {
/* 301 */       this.pump.interrupt();
/*     */     }
/* 303 */     ShutdownHooks.remove(this.closer);
/* 304 */     for (Map.Entry<Terminal.Signal, Object> entry : this.nativeHandlers.entrySet()) {
/* 305 */       Signals.unregister(((Terminal.Signal)entry.getKey()).name(), entry.getValue());
/*     */     }
/* 307 */     this.reader.close();
/* 308 */     this.writer.close();
/* 309 */     setConsoleMode(this.inConsole, this.originalInConsoleMode);
/* 310 */     setConsoleMode(this.outConsole, this.originalOutConsoleMode);
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
/*     */   protected void processKeyEvent(boolean isKeyDown, short virtualKeyCode, char ch, int controlKeyState) throws IOException {
/* 329 */     boolean isCtrl = ((controlKeyState & 0xC) > 0);
/* 330 */     boolean isAlt = ((controlKeyState & 0x3) > 0);
/* 331 */     boolean isShift = ((controlKeyState & 0x10) > 0);
/*     */     
/* 333 */     if (isKeyDown && ch != '\003') {
/*     */ 
/*     */       
/* 336 */       if (ch != '\000' && (controlKeyState & 0xF) == 9) {
/*     */ 
/*     */ 
/*     */         
/* 340 */         processInputChar(ch);
/*     */       } else {
/* 342 */         String keySeq = getEscapeSequence(virtualKeyCode, (
/* 343 */             isCtrl ? 4 : 0) + (isAlt ? 2 : 0) + (isShift ? 1 : 0));
/* 344 */         if (keySeq != null) {
/* 345 */           for (char c : keySeq.toCharArray()) {
/* 346 */             processInputChar(c);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 358 */         if (ch > '\000') {
/* 359 */           if (isAlt) {
/* 360 */             processInputChar('\033');
/*     */           }
/* 362 */           if (isCtrl && ch != '\n' && ch != '') {
/* 363 */             processInputChar((char)((ch == '?') ? '' : (Character.toUpperCase(ch) & 0x1F)));
/*     */           } else {
/* 365 */             processInputChar(ch);
/*     */           } 
/* 367 */         } else if (isCtrl) {
/* 368 */           if (virtualKeyCode >= 65 && virtualKeyCode <= 90) {
/* 369 */             ch = (char)(virtualKeyCode - 64);
/* 370 */           } else if (virtualKeyCode == 191) {
/* 371 */             ch = '';
/*     */           } 
/* 373 */           if (ch > '\000') {
/* 374 */             if (isAlt) {
/* 375 */               processInputChar('\033');
/*     */             }
/* 377 */             processInputChar(ch);
/*     */           } 
/*     */         } 
/*     */       } 
/* 381 */     } else if (isKeyDown && ch == '\003') {
/* 382 */       processInputChar('\003');
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 387 */     else if (virtualKeyCode == 18 && ch > '\000') {
/* 388 */       processInputChar(ch);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getEscapeSequence(short keyCode, int keyState) {
/* 396 */     String escapeSequence = null;
/* 397 */     switch (keyCode) {
/*     */       case 8:
/* 399 */         escapeSequence = ((keyState & 0x2) > 0) ? "\\E^H" : getRawSequence(InfoCmp.Capability.key_backspace);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 475 */         return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 9: escapeSequence = ((keyState & 0x1) > 0) ? getRawSequence(InfoCmp.Capability.key_btab) : null; return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 33: escapeSequence = getRawSequence(InfoCmp.Capability.key_ppage); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 34: escapeSequence = getRawSequence(InfoCmp.Capability.key_npage); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 35: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dF" : getRawSequence(InfoCmp.Capability.key_end); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 36: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dH" : getRawSequence(InfoCmp.Capability.key_home); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 37: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dD" : getRawSequence(InfoCmp.Capability.key_left); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 38: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dA" : getRawSequence(InfoCmp.Capability.key_up); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 39: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dC" : getRawSequence(InfoCmp.Capability.key_right); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 40: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dB" : getRawSequence(InfoCmp.Capability.key_down); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 45: escapeSequence = getRawSequence(InfoCmp.Capability.key_ic); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 46: escapeSequence = getRawSequence(InfoCmp.Capability.key_dc); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 112: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dP" : getRawSequence(InfoCmp.Capability.key_f1); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 113: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dQ" : getRawSequence(InfoCmp.Capability.key_f2); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 114: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dR" : getRawSequence(InfoCmp.Capability.key_f3); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 115: escapeSequence = (keyState > 0) ? "\\E[1;%p1%dS" : getRawSequence(InfoCmp.Capability.key_f4); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 116: escapeSequence = (keyState > 0) ? "\\E[15;%p1%d~" : getRawSequence(InfoCmp.Capability.key_f5); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 117: escapeSequence = (keyState > 0) ? "\\E[17;%p1%d~" : getRawSequence(InfoCmp.Capability.key_f6); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 118: escapeSequence = (keyState > 0) ? "\\E[18;%p1%d~" : getRawSequence(InfoCmp.Capability.key_f7); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 119: escapeSequence = (keyState > 0) ? "\\E[19;%p1%d~" : getRawSequence(InfoCmp.Capability.key_f8); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 120: escapeSequence = (keyState > 0) ? "\\E[20;%p1%d~" : getRawSequence(InfoCmp.Capability.key_f9); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 121: escapeSequence = (keyState > 0) ? "\\E[21;%p1%d~" : getRawSequence(InfoCmp.Capability.key_f10); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 122: escapeSequence = (keyState > 0) ? "\\E[23;%p1%d~" : getRawSequence(InfoCmp.Capability.key_f11); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });case 123: escapeSequence = (keyState > 0) ? "\\E[24;%p1%d~" : getRawSequence(InfoCmp.Capability.key_f12); return Curses.tputs(escapeSequence, new Object[] { Integer.valueOf(keyState + 1) });
/*     */     } 
/*     */     return null;
/*     */   } protected String getRawSequence(InfoCmp.Capability cap) {
/* 479 */     return this.strings.get(cap);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFocusSupport() {
/* 484 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trackFocus(boolean tracking) {
/* 489 */     this.focusTracking = tracking;
/* 490 */     return true;
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
/*     */   
/*     */   protected int convertAttributeToRgb(int attribute, boolean foreground) {
/* 516 */     int index = 0;
/* 517 */     if (foreground) {
/* 518 */       if ((attribute & 0x4) != 0) index |= 0x1; 
/* 519 */       if ((attribute & 0x2) != 0) index |= 0x2; 
/* 520 */       if ((attribute & 0x1) != 0) index |= 0x4; 
/* 521 */       if ((attribute & 0x8) != 0) index |= 0x8; 
/*     */     } else {
/* 523 */       if ((attribute & 0x40) != 0) index |= 0x1; 
/* 524 */       if ((attribute & 0x20) != 0) index |= 0x2; 
/* 525 */       if ((attribute & 0x10) != 0) index |= 0x4; 
/* 526 */       if ((attribute & 0x80) != 0) index |= 0x8; 
/*     */     } 
/* 528 */     return ANSI_COLORS[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 534 */   protected static final int[] ANSI_COLORS = new int[] { 0, 13434880, 52480, 13487360, 238, 13435085, 52685, 15066597, 8355711, 16711680, 65280, 16776960, 6053119, 16711935, 65535, 16777215 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPauseResume() {
/* 555 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pause() {
/* 560 */     synchronized (this.lock) {
/* 561 */       this.paused = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void pause(boolean wait) throws InterruptedException {
/*     */     Thread p;
/* 568 */     synchronized (this.lock) {
/* 569 */       this.paused = true;
/* 570 */       p = this.pump;
/*     */     } 
/* 572 */     if (p != null) {
/* 573 */       p.interrupt();
/* 574 */       p.join();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resume() {
/* 580 */     synchronized (this.lock) {
/* 581 */       this.paused = false;
/* 582 */       if (this.pump == null) {
/* 583 */         this.pump = new Thread(this::pump, "WindowsStreamPump");
/* 584 */         this.pump.setDaemon(true);
/* 585 */         this.pump.start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean paused() {
/* 592 */     synchronized (this.lock) {
/* 593 */       return this.paused;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void pump() {
/*     */     try {
/* 599 */       while (!this.closing) {
/* 600 */         synchronized (this.lock) {
/* 601 */           if (this.paused) {
/* 602 */             this.pump = null;
/*     */             break;
/*     */           } 
/*     */         } 
/* 606 */         if (processConsoleInput()) {
/* 607 */           this.slaveInputPipe.flush();
/*     */         }
/*     */       } 
/* 610 */     } catch (IOException e) {
/* 611 */       if (!this.closing) {
/* 612 */         Log.warn(new Object[] { "Error in WindowsStreamPump", e });
/*     */         try {
/* 614 */           close();
/* 615 */         } catch (IOException e1) {
/* 616 */           Log.warn(new Object[] { "Error closing terminal", e });
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 620 */       synchronized (this.lock) {
/* 621 */         this.pump = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processInputChar(char c) throws IOException {
/* 627 */     if (this.attributes.getLocalFlag(Attributes.LocalFlag.ISIG)) {
/* 628 */       if (c == this.attributes.getControlChar(Attributes.ControlChar.VINTR)) {
/* 629 */         raise(Terminal.Signal.INT); return;
/*     */       } 
/* 631 */       if (c == this.attributes.getControlChar(Attributes.ControlChar.VQUIT)) {
/* 632 */         raise(Terminal.Signal.QUIT); return;
/*     */       } 
/* 634 */       if (c == this.attributes.getControlChar(Attributes.ControlChar.VSUSP)) {
/* 635 */         raise(Terminal.Signal.TSTP); return;
/*     */       } 
/* 637 */       if (c == this.attributes.getControlChar(Attributes.ControlChar.VSTATUS)) {
/* 638 */         raise(Terminal.Signal.INFO);
/*     */       }
/*     */     } 
/* 641 */     if (this.attributes.getInputFlag(Attributes.InputFlag.INORMEOL)) {
/* 642 */       if (c == '\r') {
/* 643 */         this.skipNextLf = true;
/* 644 */         c = '\n';
/* 645 */       } else if (c == '\n') {
/* 646 */         if (this.skipNextLf) {
/* 647 */           this.skipNextLf = false;
/*     */           return;
/*     */         } 
/*     */       } else {
/* 651 */         this.skipNextLf = false;
/*     */       } 
/* 653 */     } else if (c == '\r') {
/* 654 */       if (this.attributes.getInputFlag(Attributes.InputFlag.IGNCR)) {
/*     */         return;
/*     */       }
/* 657 */       if (this.attributes.getInputFlag(Attributes.InputFlag.ICRNL)) {
/* 658 */         c = '\n';
/*     */       }
/* 660 */     } else if (c == '\n' && this.attributes.getInputFlag(Attributes.InputFlag.INLCR)) {
/* 661 */       c = '\r';
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 667 */     this.slaveInputPipe.write(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trackMouse(Terminal.MouseTracking tracking) {
/* 672 */     this.tracking = tracking;
/* 673 */     updateConsoleMode();
/* 674 */     return true;
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
/*     */   public TerminalProvider getProvider() {
/* 691 */     return this.provider;
/*     */   }
/*     */ 
/*     */   
/*     */   public SystemStream getSystemStream() {
/* 696 */     return this.systemStream;
/*     */   }
/*     */   
/*     */   public abstract int getDefaultForegroundColor();
/*     */   
/*     */   public abstract int getDefaultBackgroundColor();
/*     */   
/*     */   protected abstract int getConsoleMode(Console paramConsole);
/*     */   
/*     */   protected abstract void setConsoleMode(Console paramConsole, int paramInt);
/*     */   
/*     */   protected abstract boolean processConsoleInput() throws IOException;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\AbstractWindowsTerminal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */