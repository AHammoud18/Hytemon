/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntSupplier;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Cursor;
/*     */ import org.jline.terminal.MouseEvent;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.spi.TerminalExt;
/*     */ import org.jline.utils.ColorPalette;
/*     */ import org.jline.utils.Curses;
/*     */ import org.jline.utils.InfoCmp;
/*     */ import org.jline.utils.Log;
/*     */ import org.jline.utils.Status;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractTerminal
/*     */   implements TerminalExt
/*     */ {
/*     */   protected final String name;
/*     */   protected final String type;
/*     */   protected final Charset encoding;
/*     */   protected final Charset inputEncoding;
/*     */   protected final Charset outputEncoding;
/*  77 */   protected final Map<Terminal.Signal, Terminal.SignalHandler> handlers = new ConcurrentHashMap<>();
/*  78 */   protected final Set<InfoCmp.Capability> bools = new HashSet<>();
/*  79 */   protected final Map<InfoCmp.Capability, Integer> ints = new HashMap<>();
/*  80 */   protected final Map<InfoCmp.Capability, String> strings = new HashMap<>();
/*     */   protected final ColorPalette palette;
/*     */   protected Status status;
/*     */   protected Runnable onClose;
/*  84 */   protected Terminal.MouseTracking currentMouseTracking = Terminal.MouseTracking.Off; private MouseEvent lastMouseEvent;
/*     */   
/*     */   public AbstractTerminal(String name, String type) throws IOException {
/*  87 */     this(name, type, (Charset)null, Terminal.SignalHandler.SIG_DFL);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTerminal(String name, String type, Charset encoding, Terminal.SignalHandler signalHandler) throws IOException {
/*  93 */     this(name, type, encoding, encoding, encoding, signalHandler);
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
/*     */   public void setOnClose(Runnable onClose) {
/* 117 */     this.onClose = onClose;
/*     */   }
/*     */   
/*     */   public Status getStatus() {
/* 121 */     return getStatus(true);
/*     */   }
/*     */   
/*     */   public Status getStatus(boolean create) {
/* 125 */     if (this.status == null && create) {
/* 126 */       this.status = new Status((Terminal)this);
/*     */     }
/* 128 */     return this.status;
/*     */   }
/*     */   
/*     */   public Terminal.SignalHandler handle(Terminal.Signal signal, Terminal.SignalHandler handler) {
/* 132 */     Objects.requireNonNull(signal);
/* 133 */     Objects.requireNonNull(handler);
/* 134 */     return this.handlers.put(signal, handler);
/*     */   }
/*     */   
/*     */   public void raise(Terminal.Signal signal) {
/* 138 */     Objects.requireNonNull(signal);
/* 139 */     Terminal.SignalHandler handler = this.handlers.get(signal);
/* 140 */     if (handler == Terminal.SignalHandler.SIG_DFL) {
/* 141 */       if (this.status != null && signal == Terminal.Signal.WINCH) {
/* 142 */         this.status.resize();
/*     */       }
/* 144 */     } else if (handler != Terminal.SignalHandler.SIG_IGN) {
/* 145 */       handler.handle(signal);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void close() throws IOException {
/*     */     try {
/* 151 */       doClose();
/*     */     } finally {
/* 153 */       if (this.onClose != null) {
/* 154 */         this.onClose.run();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doClose() throws IOException {
/* 160 */     if (this.status != null) {
/* 161 */       this.status.close();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void echoSignal(Terminal.Signal signal) {
/* 166 */     Attributes.ControlChar cc = null;
/* 167 */     switch (signal) {
/*     */       case INT:
/* 169 */         cc = Attributes.ControlChar.VINTR;
/*     */         break;
/*     */       case QUIT:
/* 172 */         cc = Attributes.ControlChar.VQUIT;
/*     */         break;
/*     */       case TSTP:
/* 175 */         cc = Attributes.ControlChar.VSUSP;
/*     */         break;
/*     */     } 
/* 178 */     if (cc != null) {
/* 179 */       int vcc = getAttributes().getControlChar(cc);
/* 180 */       if (vcc > 0 && vcc < 32) {
/* 181 */         writer().write(new char[] { '^', (char)(vcc + 64) }, 0, 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Attributes enterRawMode() {
/* 187 */     Attributes prvAttr = getAttributes();
/* 188 */     Attributes newAttr = new Attributes(prvAttr);
/* 189 */     newAttr.setLocalFlags(EnumSet.of(Attributes.LocalFlag.ICANON, Attributes.LocalFlag.ECHO, Attributes.LocalFlag.IEXTEN), false);
/* 190 */     newAttr.setInputFlags(EnumSet.of(Attributes.InputFlag.IXON, Attributes.InputFlag.ICRNL, Attributes.InputFlag.INLCR), false);
/* 191 */     newAttr.setControlChar(Attributes.ControlChar.VMIN, 0);
/* 192 */     newAttr.setControlChar(Attributes.ControlChar.VTIME, 1);
/* 193 */     setAttributes(newAttr);
/* 194 */     return prvAttr;
/*     */   }
/*     */   
/*     */   public boolean echo() {
/* 198 */     return getAttributes().getLocalFlag(Attributes.LocalFlag.ECHO);
/*     */   }
/*     */   
/*     */   public boolean echo(boolean echo) {
/* 202 */     Attributes attr = getAttributes();
/* 203 */     boolean prev = attr.getLocalFlag(Attributes.LocalFlag.ECHO);
/* 204 */     if (prev != echo) {
/* 205 */       attr.setLocalFlag(Attributes.LocalFlag.ECHO, echo);
/* 206 */       setAttributes(attr);
/*     */     } 
/* 208 */     return prev;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 212 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 216 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getKind() {
/* 220 */     return getClass().getSimpleName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Charset encoding() {
/* 225 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public Charset inputEncoding() {
/* 230 */     return this.inputEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public Charset outputEncoding() {
/* 235 */     return this.outputEncoding;
/*     */   }
/*     */   
/*     */   public void flush() {
/* 239 */     writer().flush();
/*     */   }
/*     */   
/*     */   public boolean puts(InfoCmp.Capability capability, Object... params) {
/* 243 */     String str = getStringCapability(capability);
/* 244 */     if (str == null) {
/* 245 */       return false;
/*     */     }
/* 247 */     Curses.tputs(writer(), str, params);
/* 248 */     return true;
/*     */   }
/*     */   
/*     */   public boolean getBooleanCapability(InfoCmp.Capability capability) {
/* 252 */     return this.bools.contains(capability);
/*     */   }
/*     */   
/*     */   public Integer getNumericCapability(InfoCmp.Capability capability) {
/* 256 */     return this.ints.get(capability);
/*     */   }
/*     */   
/*     */   public String getStringCapability(InfoCmp.Capability capability) {
/* 260 */     return this.strings.get(capability);
/*     */   }
/*     */   
/*     */   protected void parseInfoCmp() {
/* 264 */     String capabilities = null;
/*     */     try {
/* 266 */       capabilities = InfoCmp.getInfoCmp(this.type);
/* 267 */     } catch (Exception e) {
/* 268 */       Log.warn(new Object[] { "Unable to retrieve infocmp for type " + this.type, e });
/*     */     } 
/* 270 */     if (capabilities == null) {
/* 271 */       capabilities = InfoCmp.getDefaultInfoCmp("ansi");
/*     */     }
/* 273 */     InfoCmp.parseInfoCmp(capabilities, this.bools, this.ints, this.strings);
/*     */   }
/*     */ 
/*     */   
/*     */   public Cursor getCursorPosition(IntConsumer discarded) {
/* 278 */     return null;
/*     */   }
/*     */   
/* 281 */   public AbstractTerminal(String name, String type, Charset encoding, Charset inputEncoding, Charset outputEncoding, Terminal.SignalHandler signalHandler) throws IOException { this
/* 282 */       .lastMouseEvent = new MouseEvent(MouseEvent.Type.Moved, MouseEvent.Button.NoButton, EnumSet.noneOf(MouseEvent.Modifier.class), 0, 0); this.name = name; this.type = (type != null) ? type : "ansi"; this.encoding = (encoding != null) ? encoding : Charset.defaultCharset(); this.inputEncoding = (inputEncoding != null) ? inputEncoding : this.encoding;
/*     */     this.outputEncoding = (outputEncoding != null) ? outputEncoding : this.encoding;
/*     */     this.palette = new ColorPalette((Terminal)this);
/*     */     for (Terminal.Signal signal : Terminal.Signal.values())
/* 286 */       this.handlers.put(signal, signalHandler);  } public boolean hasMouseSupport() { return MouseSupport.hasMouseSupport((Terminal)this); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Terminal.MouseTracking getCurrentMouseTracking() {
/* 291 */     return this.currentMouseTracking;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trackMouse(Terminal.MouseTracking tracking) {
/* 296 */     if (MouseSupport.trackMouse((Terminal)this, tracking)) {
/* 297 */       this.currentMouseTracking = tracking;
/* 298 */       return true;
/*     */     } 
/* 300 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MouseEvent readMouseEvent() {
/* 306 */     return readMouseEvent(getStringCapability(InfoCmp.Capability.key_mouse));
/*     */   }
/*     */ 
/*     */   
/*     */   public MouseEvent readMouseEvent(IntSupplier reader) {
/* 311 */     return readMouseEvent(reader, getStringCapability(InfoCmp.Capability.key_mouse));
/*     */   }
/*     */ 
/*     */   
/*     */   public MouseEvent readMouseEvent(String prefix) {
/* 316 */     return this.lastMouseEvent = MouseSupport.readMouse((Terminal)this, this.lastMouseEvent, prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public MouseEvent readMouseEvent(IntSupplier reader, String prefix) {
/* 321 */     return this.lastMouseEvent = MouseSupport.readMouse(reader, this.lastMouseEvent, prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFocusSupport() {
/* 326 */     return this.type.startsWith("xterm");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trackFocus(boolean tracking) {
/* 331 */     if (hasFocusSupport()) {
/* 332 */       writer().write(tracking ? "\033[?1004h" : "\033[?1004l");
/* 333 */       writer().flush();
/* 334 */       return true;
/*     */     } 
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkInterrupted() throws InterruptedIOException {
/* 341 */     if (Thread.interrupted()) {
/* 342 */       throw new InterruptedIOException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPauseResume() {
/* 348 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pause() {}
/*     */ 
/*     */   
/*     */   public void pause(boolean wait) throws InterruptedException {}
/*     */ 
/*     */   
/*     */   public void resume() {}
/*     */ 
/*     */   
/*     */   public boolean paused() {
/* 362 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ColorPalette getPalette() {
/* 367 */     return this.palette;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 372 */     return getKind() + "[name='" + this.name + '\'' + ", type='" + this.type + '\'' + ", size='" + 
/*     */ 
/*     */       
/* 375 */       getSize() + '\'' + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultForegroundColor() {
/* 385 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultBackgroundColor() {
/* 395 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\AbstractTerminal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */