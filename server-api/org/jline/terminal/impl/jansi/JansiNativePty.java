/*     */ package org.jline.terminal.impl.jansi;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.fusesource.jansi.internal.CLibrary;
/*     */ import org.fusesource.jansi.internal.Kernel32;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.impl.AbstractPty;
/*     */ import org.jline.terminal.impl.jansi.win.JansiWinSysTerminal;
/*     */ import org.jline.terminal.spi.Pty;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ import org.jline.utils.ExecHelper;
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
/*     */ 
/*     */ 
/*     */ public abstract class JansiNativePty
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
/*     */   public JansiNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, String name) {
/*  52 */     this(provider, systemStream, master, masterFD, slave, slaveFD, slave, slaveFD, name);
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
/*     */   public JansiNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, int slaveOut, FileDescriptor slaveOutFD, String name) {
/*  65 */     super(provider, systemStream);
/*  66 */     this.master = master;
/*  67 */     this.slave = slave;
/*  68 */     this.slaveOut = slaveOut;
/*  69 */     this.name = name;
/*  70 */     this.masterFD = masterFD;
/*  71 */     this.slaveFD = slaveFD;
/*  72 */     this.slaveOutFD = slaveOutFD;
/*     */   }
/*     */   
/*     */   protected static String ttyname() throws IOException {
/*     */     String name;
/*  77 */     if (JansiTerminalProvider.JANSI_MAJOR_VERSION > 1 || (JansiTerminalProvider.JANSI_MAJOR_VERSION == 1 && JansiTerminalProvider.JANSI_MINOR_VERSION >= 16)) {
/*  78 */       name = CLibrary.ttyname(0);
/*     */     } else {
/*     */       try {
/*  81 */         name = ExecHelper.exec(true, new String[] { OSUtils.TTY_COMMAND });
/*  82 */       } catch (IOException e) {
/*  83 */         throw new IOException("Not a tty", e);
/*     */       } 
/*     */     } 
/*  86 */     if (name != null) {
/*  87 */       name = name.trim();
/*     */     }
/*  89 */     if (name == null || name.isEmpty()) {
/*  90 */       throw new IOException("Not a tty");
/*     */     }
/*  92 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  97 */     if (this.master > 0) {
/*  98 */       getMasterInput().close();
/*     */     }
/* 100 */     if (this.slave > 0) {
/* 101 */       getSlaveInput().close();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaster() {
/* 106 */     return this.master;
/*     */   }
/*     */   
/*     */   public int getSlave() {
/* 110 */     return this.slave;
/*     */   }
/*     */   
/*     */   public int getSlaveOut() {
/* 114 */     return this.slaveOut;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 118 */     return this.name;
/*     */   }
/*     */   
/*     */   public FileDescriptor getMasterFD() {
/* 122 */     return this.masterFD;
/*     */   }
/*     */   
/*     */   public FileDescriptor getSlaveFD() {
/* 126 */     return this.slaveFD;
/*     */   }
/*     */   
/*     */   public FileDescriptor getSlaveOutFD() {
/* 130 */     return this.slaveOutFD;
/*     */   }
/*     */   
/*     */   public InputStream getMasterInput() {
/* 134 */     return new FileInputStream(getMasterFD());
/*     */   }
/*     */   
/*     */   public OutputStream getMasterOutput() {
/* 138 */     return new FileOutputStream(getMasterFD());
/*     */   }
/*     */   
/*     */   protected InputStream doGetSlaveInput() {
/* 142 */     return new FileInputStream(getSlaveFD());
/*     */   }
/*     */   
/*     */   public OutputStream getSlaveOutput() {
/* 146 */     return new FileOutputStream(getSlaveOutFD());
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttr() throws IOException {
/* 151 */     CLibrary.Termios tios = new CLibrary.Termios();
/* 152 */     CLibrary.tcgetattr(this.slave, tios);
/* 153 */     return toAttributes(tios);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetAttr(Attributes attr) throws IOException {
/* 158 */     CLibrary.Termios tios = toTermios(attr);
/* 159 */     CLibrary.tcsetattr(this.slave, CLibrary.TCSANOW, tios);
/*     */   }
/*     */ 
/*     */   
/*     */   public Size getSize() throws IOException {
/* 164 */     CLibrary.WinSize sz = new CLibrary.WinSize();
/* 165 */     int res = CLibrary.ioctl(this.slave, CLibrary.TIOCGWINSZ, sz);
/* 166 */     if (res != 0) {
/* 167 */       throw new IOException("Error calling ioctl(TIOCGWINSZ): return code is " + res);
/*     */     }
/* 169 */     return new Size(sz.ws_col, sz.ws_row);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(Size size) throws IOException {
/* 174 */     CLibrary.WinSize sz = new CLibrary.WinSize((short)size.getRows(), (short)size.getColumns());
/* 175 */     int res = CLibrary.ioctl(this.slave, CLibrary.TIOCSWINSZ, sz);
/* 176 */     if (res != 0) {
/* 177 */       throw new IOException("Error calling ioctl(TIOCSWINSZ): return code is " + res);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract CLibrary.Termios toTermios(Attributes paramAttributes);
/*     */   
/*     */   protected abstract Attributes toAttributes(CLibrary.Termios paramTermios);
/*     */   
/*     */   public String toString() {
/* 187 */     return "JansiNativePty[" + getName() + "]";
/*     */   }
/*     */   
/*     */   public static boolean isPosixSystemStream(SystemStream stream) {
/* 191 */     return (CLibrary.isatty(fd(stream)) == 1);
/*     */   }
/*     */   
/*     */   public static String posixSystemStreamName(SystemStream systemStream) {
/* 195 */     return CLibrary.ttyname(fd(systemStream));
/*     */   }
/*     */   
/*     */   public static int systemStreamWidth(SystemStream systemStream) {
/*     */     try {
/* 200 */       if (OSUtils.IS_WINDOWS) {
/* 201 */         Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/* 202 */         long outConsole = JansiWinSysTerminal.getConsole(systemStream);
/* 203 */         Kernel32.GetConsoleScreenBufferInfo(outConsole, info);
/* 204 */         return info.windowWidth();
/*     */       } 
/* 206 */       CLibrary.WinSize sz = new CLibrary.WinSize();
/* 207 */       int res = CLibrary.ioctl(fd(systemStream), CLibrary.TIOCGWINSZ, sz);
/* 208 */       if (res != 0) {
/* 209 */         throw new IOException("Error calling ioctl(TIOCGWINSZ): return code is " + res);
/*     */       }
/* 211 */       return sz.ws_col;
/*     */     }
/* 213 */     catch (Throwable t) {
/* 214 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int fd(SystemStream systemStream) {
/* 219 */     switch (systemStream) {
/*     */       case Input:
/* 221 */         return 0;
/*     */       case Output:
/* 223 */         return 1;
/*     */       case Error:
/* 225 */         return 2;
/*     */     } 
/* 227 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jansi\JansiNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */