/*     */ package org.jline.terminal.impl.jna.linux;
/*     */ 
/*     */ import com.sun.jna.LastErrorException;
/*     */ import com.sun.jna.Library;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Platform;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.impl.jna.JnaNativePty;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinuxNativePty
/*     */   extends JnaNativePty
/*     */ {
/*  32 */   private static final CLibrary C_LIBRARY = (CLibrary)Native.load(Platform.C_LIBRARY_NAME, CLibrary.class);
/*     */   
/*     */   public static interface UtilLibrary
/*     */     extends Library
/*     */   {
/*     */     void openpty(int[] param1ArrayOfint1, int[] param1ArrayOfint2, byte[] param1ArrayOfbyte, CLibrary.termios param1termios, CLibrary.winsize param1winsize) throws LastErrorException;
/*     */     
/*  39 */     public static final UtilLibrary INSTANCE = (UtilLibrary)Native.load("util", UtilLibrary.class);
/*     */   }
/*     */   
/*     */   public static LinuxNativePty current(TerminalProvider provider, SystemStream systemStream) throws IOException {
/*  43 */     switch (systemStream) {
/*     */       case Output:
/*  45 */         return new LinuxNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 1, FileDescriptor.out, 
/*  46 */             ttyname(0));
/*     */       case Error:
/*  48 */         return new LinuxNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 2, FileDescriptor.err, 
/*  49 */             ttyname(0));
/*     */     } 
/*  51 */     throw new IllegalArgumentException("Unsupported stream for console: " + systemStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public static LinuxNativePty open(TerminalProvider provider, Attributes attr, Size size) throws IOException {
/*  56 */     int[] master = new int[1];
/*  57 */     int[] slave = new int[1];
/*  58 */     byte[] buf = new byte[64];
/*  59 */     UtilLibrary.INSTANCE.openpty(master, slave, buf, 
/*  60 */         (attr != null) ? new CLibrary.termios(attr) : null, (size != null) ? new CLibrary.winsize(size) : null);
/*  61 */     int len = 0;
/*  62 */     while (buf[len] != 0) {
/*  63 */       len++;
/*     */     }
/*  65 */     String name = new String(buf, 0, len);
/*  66 */     return new LinuxNativePty(provider, null, master[0], 
/*  67 */         newDescriptor(master[0]), slave[0], newDescriptor(slave[0]), name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinuxNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, String name) {
/*  78 */     super(provider, systemStream, master, masterFD, slave, slaveFD, name);
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
/*     */   public LinuxNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, int slaveOut, FileDescriptor slaveOutFD, String name) {
/*  91 */     super(provider, systemStream, master, masterFD, slave, slaveFD, slaveOut, slaveOutFD, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttr() throws IOException {
/*  96 */     CLibrary.termios termios = new CLibrary.termios();
/*  97 */     C_LIBRARY.tcgetattr(getSlave(), termios);
/*  98 */     return termios.toAttributes();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetAttr(Attributes attr) throws IOException {
/* 103 */     CLibrary.termios termios = new CLibrary.termios(attr);
/* 104 */     CLibrary.termios org = new CLibrary.termios();
/* 105 */     C_LIBRARY.tcgetattr(getSlave(), org);
/* 106 */     org.c_iflag = termios.c_iflag;
/* 107 */     org.c_oflag = termios.c_oflag;
/* 108 */     org.c_lflag = termios.c_lflag;
/* 109 */     System.arraycopy(termios.c_cc, 0, org.c_cc, 0, termios.c_cc.length);
/* 110 */     C_LIBRARY.tcsetattr(getSlave(), 1, org);
/*     */   }
/*     */ 
/*     */   
/*     */   public Size getSize() throws IOException {
/* 115 */     CLibrary.winsize sz = new CLibrary.winsize();
/* 116 */     C_LIBRARY.ioctl(getSlave(), CLibrary.TIOCGWINSZ, sz);
/* 117 */     return sz.toSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(Size size) throws IOException {
/* 122 */     CLibrary.winsize sz = new CLibrary.winsize(size);
/* 123 */     C_LIBRARY.ioctl(getSlave(), CLibrary.TIOCSWINSZ, sz);
/*     */   }
/*     */   
/*     */   public static int isatty(int fd) {
/* 127 */     return C_LIBRARY.isatty(fd);
/*     */   }
/*     */   
/*     */   public static String ttyname(int slave) {
/* 131 */     byte[] buf = new byte[64];
/* 132 */     C_LIBRARY.ttyname_r(slave, buf, buf.length);
/* 133 */     int len = 0;
/* 134 */     while (buf[len] != 0) {
/* 135 */       len++;
/*     */     }
/* 137 */     return new String(buf, 0, len);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\linux\LinuxNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */