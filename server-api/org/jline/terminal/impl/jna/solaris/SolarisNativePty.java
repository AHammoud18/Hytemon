/*     */ package org.jline.terminal.impl.jna.solaris;
/*     */ 
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
/*     */ 
/*     */ public class SolarisNativePty
/*     */   extends JnaNativePty
/*     */ {
/*  31 */   private static final CLibrary C_LIBRARY = (CLibrary)Native.load(Platform.C_LIBRARY_NAME, CLibrary.class);
/*     */   
/*     */   public static SolarisNativePty current(TerminalProvider provider, SystemStream systemStream) throws IOException {
/*  34 */     switch (systemStream) {
/*     */       case Output:
/*  36 */         return new SolarisNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 1, FileDescriptor.out, 
/*  37 */             ttyname(0));
/*     */       case Error:
/*  39 */         return new SolarisNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 2, FileDescriptor.err, 
/*  40 */             ttyname(0));
/*     */     } 
/*  42 */     throw new IllegalArgumentException("Unsupported stream for console: " + systemStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SolarisNativePty open(TerminalProvider provider, Attributes attr, Size size) throws IOException {
/*  47 */     int[] master = new int[1];
/*  48 */     int[] slave = new int[1];
/*  49 */     byte[] buf = new byte[64];
/*  50 */     C_LIBRARY.openpty(master, slave, buf, 
/*  51 */         (attr != null) ? new CLibrary.termios(attr) : null, (size != null) ? new CLibrary.winsize(size) : null);
/*  52 */     int len = 0;
/*  53 */     while (buf[len] != 0) {
/*  54 */       len++;
/*     */     }
/*  56 */     String name = new String(buf, 0, len);
/*  57 */     return new SolarisNativePty(provider, null, master[0], 
/*  58 */         newDescriptor(master[0]), slave[0], newDescriptor(slave[0]), name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolarisNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, String name) {
/*  69 */     super(provider, systemStream, master, masterFD, slave, slaveFD, name);
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
/*     */   public SolarisNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, int slaveOut, FileDescriptor slaveOutFD, String name) {
/*  82 */     super(provider, systemStream, master, masterFD, slave, slaveFD, slaveOut, slaveOutFD, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttr() throws IOException {
/*  87 */     CLibrary.termios termios = new CLibrary.termios();
/*  88 */     C_LIBRARY.tcgetattr(getSlave(), termios);
/*  89 */     return termios.toAttributes();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetAttr(Attributes attr) throws IOException {
/*  94 */     CLibrary.termios termios = new CLibrary.termios(attr);
/*  95 */     C_LIBRARY.tcsetattr(getSlave(), 0, termios);
/*     */   }
/*     */ 
/*     */   
/*     */   public Size getSize() throws IOException {
/* 100 */     CLibrary.winsize sz = new CLibrary.winsize();
/* 101 */     C_LIBRARY.ioctl(getSlave(), 21608L, sz);
/* 102 */     return sz.toSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(Size size) throws IOException {
/* 107 */     CLibrary.winsize sz = new CLibrary.winsize(size);
/* 108 */     C_LIBRARY.ioctl(getSlave(), 21607L, sz);
/*     */   }
/*     */   
/*     */   public static int isatty(int fd) {
/* 112 */     return C_LIBRARY.isatty(fd);
/*     */   }
/*     */   
/*     */   public static String ttyname(int slave) {
/* 116 */     byte[] buf = new byte[64];
/* 117 */     C_LIBRARY.ttyname_r(slave, buf, buf.length);
/* 118 */     int len = 0;
/* 119 */     while (buf[len] != 0) {
/* 120 */       len++;
/*     */     }
/* 122 */     return new String(buf, 0, len);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\solaris\SolarisNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */