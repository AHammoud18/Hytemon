/*     */ package org.jline.terminal.impl.jna.freebsd;
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
/*     */ public class FreeBsdNativePty
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
/*     */   public static FreeBsdNativePty current(TerminalProvider provider, SystemStream systemStream) throws IOException {
/*  43 */     switch (systemStream) {
/*     */       case Output:
/*  45 */         return new FreeBsdNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 1, FileDescriptor.out, 
/*  46 */             ttyname(0));
/*     */       case Error:
/*  48 */         return new FreeBsdNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 2, FileDescriptor.err, 
/*  49 */             ttyname(0));
/*     */     } 
/*  51 */     throw new IllegalArgumentException("Unsupported stream for console: " + systemStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FreeBsdNativePty open(TerminalProvider provider, Attributes attr, Size size) throws IOException {
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
/*  66 */     return new FreeBsdNativePty(provider, null, master[0], 
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
/*     */   public FreeBsdNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, String name) {
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
/*     */   public FreeBsdNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, int slaveOut, FileDescriptor slaveOutFD, String name) {
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
/* 104 */     C_LIBRARY.tcsetattr(getSlave(), 0, termios);
/*     */   }
/*     */ 
/*     */   
/*     */   public Size getSize() throws IOException {
/* 109 */     CLibrary.winsize sz = new CLibrary.winsize();
/* 110 */     C_LIBRARY.ioctl(getSlave(), 1074295912L, sz);
/* 111 */     return sz.toSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(Size size) throws IOException {
/* 116 */     CLibrary.winsize sz = new CLibrary.winsize(size);
/* 117 */     C_LIBRARY.ioctl(getSlave(), -2146929561L, sz);
/*     */   }
/*     */   
/*     */   public static int isatty(int fd) {
/* 121 */     return C_LIBRARY.isatty(fd);
/*     */   }
/*     */   
/*     */   public static String ttyname(int slave) {
/* 125 */     byte[] buf = new byte[64];
/* 126 */     C_LIBRARY.ttyname_r(slave, buf, buf.length);
/* 127 */     int len = 0;
/* 128 */     while (buf[len] != 0) {
/* 129 */       len++;
/*     */     }
/* 131 */     return new String(buf, 0, len);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\freebsd\FreeBsdNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */