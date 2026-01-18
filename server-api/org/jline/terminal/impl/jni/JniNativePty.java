/*     */ package org.jline.terminal.impl.jni;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.jline.nativ.CLibrary;
/*     */ import org.jline.nativ.Kernel32;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.impl.AbstractPty;
/*     */ import org.jline.terminal.impl.jni.win.NativeWinSysTerminal;
/*     */ import org.jline.terminal.spi.Pty;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ import org.jline.utils.OSUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JniNativePty
/*     */   extends AbstractPty
/*     */   implements Pty
/*     */ {
/*     */   private final int master;
/*     */   private final int slave;
/*     */   private final int slaveOut;
/*     */   private final String name;
/*     */   private final FileDescriptor masterFD;
/*     */   private final FileDescriptor slaveFD;
/*     */   private final FileDescriptor slaveOutFD;
/*     */   
/*     */   public JniNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, String name) {
/*  49 */     this(provider, systemStream, master, masterFD, slave, slaveFD, slave, slaveFD, name);
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
/*     */   public JniNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, int slaveOut, FileDescriptor slaveOutFD, String name) {
/*  62 */     super(provider, systemStream);
/*  63 */     this.master = master;
/*  64 */     this.slave = slave;
/*  65 */     this.slaveOut = slaveOut;
/*  66 */     this.name = name;
/*  67 */     this.masterFD = masterFD;
/*  68 */     this.slaveFD = slaveFD;
/*  69 */     this.slaveOutFD = slaveOutFD;
/*     */   }
/*     */   
/*     */   protected static String ttyname(int fd) throws IOException {
/*  73 */     String name = CLibrary.ttyname(fd);
/*  74 */     if (name != null) {
/*  75 */       name = name.trim();
/*     */     }
/*  77 */     if (name == null || name.isEmpty()) {
/*  78 */       throw new IOException("Not a tty");
/*     */     }
/*  80 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  85 */     if (this.master > 0) {
/*  86 */       getMasterInput().close();
/*     */     }
/*  88 */     if (this.slave > 0) {
/*  89 */       getSlaveInput().close();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaster() {
/*  94 */     return this.master;
/*     */   }
/*     */   
/*     */   public int getSlave() {
/*  98 */     return this.slave;
/*     */   }
/*     */   
/*     */   public int getSlaveOut() {
/* 102 */     return this.slaveOut;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 106 */     return this.name;
/*     */   }
/*     */   
/*     */   public FileDescriptor getMasterFD() {
/* 110 */     return this.masterFD;
/*     */   }
/*     */   
/*     */   public FileDescriptor getSlaveFD() {
/* 114 */     return this.slaveFD;
/*     */   }
/*     */   
/*     */   public FileDescriptor getSlaveOutFD() {
/* 118 */     return this.slaveOutFD;
/*     */   }
/*     */   
/*     */   public InputStream getMasterInput() {
/* 122 */     return new FileInputStream(getMasterFD());
/*     */   }
/*     */   
/*     */   public OutputStream getMasterOutput() {
/* 126 */     return new FileOutputStream(getMasterFD());
/*     */   }
/*     */   
/*     */   protected InputStream doGetSlaveInput() {
/* 130 */     return new FileInputStream(getSlaveFD());
/*     */   }
/*     */   
/*     */   public OutputStream getSlaveOutput() {
/* 134 */     return new FileOutputStream(getSlaveOutFD());
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttr() throws IOException {
/* 139 */     CLibrary.Termios tios = new CLibrary.Termios();
/* 140 */     CLibrary.tcgetattr(this.slave, tios);
/* 141 */     return toAttributes(tios);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetAttr(Attributes attr) throws IOException {
/* 146 */     CLibrary.Termios tios = toTermios(attr);
/* 147 */     CLibrary.tcsetattr(this.slave, CLibrary.TCSANOW, tios);
/*     */   }
/*     */ 
/*     */   
/*     */   public Size getSize() throws IOException {
/* 152 */     CLibrary.WinSize sz = new CLibrary.WinSize();
/* 153 */     int res = CLibrary.ioctl(this.slave, CLibrary.TIOCGWINSZ, sz);
/* 154 */     if (res != 0) {
/* 155 */       throw new IOException("Error calling ioctl(TIOCGWINSZ): return code is " + res);
/*     */     }
/* 157 */     return new Size(sz.ws_col, sz.ws_row);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(Size size) throws IOException {
/* 162 */     CLibrary.WinSize sz = new CLibrary.WinSize((short)size.getRows(), (short)size.getColumns());
/* 163 */     int res = CLibrary.ioctl(this.slave, CLibrary.TIOCSWINSZ, sz);
/* 164 */     if (res != 0) {
/* 165 */       throw new IOException("Error calling ioctl(TIOCSWINSZ): return code is " + res);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract CLibrary.Termios toTermios(Attributes paramAttributes);
/*     */   
/*     */   protected abstract Attributes toAttributes(CLibrary.Termios paramTermios);
/*     */   
/*     */   public String toString() {
/* 175 */     return "NativePty[" + getName() + "]";
/*     */   }
/*     */   
/*     */   public static boolean isPosixSystemStream(SystemStream stream) {
/* 179 */     return (CLibrary.isatty(fd(stream)) == 1);
/*     */   }
/*     */   
/*     */   public static String posixSystemStreamName(SystemStream systemStream) {
/* 183 */     return CLibrary.ttyname(fd(systemStream));
/*     */   }
/*     */   
/*     */   public static int systemStreamWidth(SystemStream systemStream) {
/*     */     try {
/* 188 */       if (OSUtils.IS_WINDOWS) {
/* 189 */         Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/* 190 */         long outConsole = NativeWinSysTerminal.getConsole(systemStream);
/* 191 */         Kernel32.GetConsoleScreenBufferInfo(outConsole, info);
/* 192 */         return info.windowWidth();
/*     */       } 
/* 194 */       CLibrary.WinSize sz = new CLibrary.WinSize();
/* 195 */       int res = CLibrary.ioctl(fd(systemStream), CLibrary.TIOCGWINSZ, sz);
/* 196 */       if (res != 0) {
/* 197 */         throw new IOException("Error calling ioctl(TIOCGWINSZ): return code is " + res);
/*     */       }
/* 199 */       return sz.ws_col;
/*     */     }
/* 201 */     catch (Throwable t) {
/* 202 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int fd(SystemStream systemStream) {
/* 207 */     switch (systemStream) {
/*     */       case Input:
/* 209 */         return 0;
/*     */       case Output:
/* 211 */         return 1;
/*     */       case Error:
/* 213 */         return 2;
/*     */     } 
/* 215 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jni\JniNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */