/*     */ package org.jline.terminal.impl.jna.osx;
/*     */ 
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.NativeLong;
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
/*     */ 
/*     */ public class OsXNativePty
/*     */   extends JnaNativePty
/*     */ {
/*  32 */   private static final CLibrary C_LIBRARY = (CLibrary)Native.load(Platform.C_LIBRARY_NAME, CLibrary.class);
/*     */   
/*     */   public static OsXNativePty current(TerminalProvider provider, SystemStream systemStream) throws IOException {
/*  35 */     switch (systemStream) {
/*     */       case Output:
/*  37 */         return new OsXNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 1, FileDescriptor.out, 
/*  38 */             ttyname(0));
/*     */       case Error:
/*  40 */         return new OsXNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 2, FileDescriptor.err, 
/*  41 */             ttyname(0));
/*     */     } 
/*  43 */     throw new IllegalArgumentException("Unsupported stream for console: " + systemStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public static OsXNativePty open(TerminalProvider provider, Attributes attr, Size size) throws IOException {
/*  48 */     int[] master = new int[1];
/*  49 */     int[] slave = new int[1];
/*  50 */     byte[] buf = new byte[64];
/*  51 */     C_LIBRARY.openpty(master, slave, buf, 
/*  52 */         (attr != null) ? new CLibrary.termios(attr) : null, (size != null) ? new CLibrary.winsize(size) : null);
/*  53 */     int len = 0;
/*  54 */     while (buf[len] != 0) {
/*  55 */       len++;
/*     */     }
/*  57 */     String name = new String(buf, 0, len);
/*  58 */     return new OsXNativePty(provider, null, master[0], 
/*  59 */         newDescriptor(master[0]), slave[0], newDescriptor(slave[0]), name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OsXNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, String name) {
/*  70 */     super(provider, systemStream, master, masterFD, slave, slaveFD, name);
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
/*     */   public OsXNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, int slaveOut, FileDescriptor slaveOutFD, String name) {
/*  83 */     super(provider, systemStream, master, masterFD, slave, slaveFD, slaveOut, slaveOutFD, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttr() throws IOException {
/*  88 */     CLibrary.termios termios = new CLibrary.termios();
/*  89 */     C_LIBRARY.tcgetattr(getSlave(), termios);
/*  90 */     return termios.toAttributes();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetAttr(Attributes attr) throws IOException {
/*  95 */     CLibrary.termios termios = new CLibrary.termios(attr);
/*  96 */     C_LIBRARY.tcsetattr(getSlave(), 0, termios);
/*     */   }
/*     */ 
/*     */   
/*     */   public Size getSize() throws IOException {
/* 101 */     CLibrary.winsize sz = new CLibrary.winsize();
/* 102 */     C_LIBRARY.ioctl(getSlave(), new NativeLong(1074295912L), sz);
/* 103 */     return sz.toSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(Size size) throws IOException {
/* 108 */     CLibrary.winsize sz = new CLibrary.winsize(size);
/* 109 */     C_LIBRARY.ioctl(getSlave(), new NativeLong(2148037735L), sz);
/*     */   }
/*     */   
/*     */   public static int isatty(int fd) {
/* 113 */     return C_LIBRARY.isatty(fd);
/*     */   }
/*     */   
/*     */   public static String ttyname(int fd) {
/* 117 */     byte[] buf = new byte[64];
/* 118 */     C_LIBRARY.ttyname_r(fd, buf, buf.length);
/* 119 */     int len = 0;
/* 120 */     while (buf[len] != 0) {
/* 121 */       len++;
/*     */     }
/* 123 */     return new String(buf, 0, len);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\osx\OsXNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */