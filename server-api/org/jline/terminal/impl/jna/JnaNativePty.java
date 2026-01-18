/*     */ package org.jline.terminal.impl.jna;
/*     */ 
/*     */ import com.sun.jna.Platform;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.impl.AbstractPty;
/*     */ import org.jline.terminal.impl.jna.freebsd.FreeBsdNativePty;
/*     */ import org.jline.terminal.impl.jna.linux.LinuxNativePty;
/*     */ import org.jline.terminal.impl.jna.osx.OsXNativePty;
/*     */ import org.jline.terminal.impl.jna.solaris.SolarisNativePty;
/*     */ import org.jline.terminal.spi.Pty;
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
/*     */ public abstract class JnaNativePty
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
/*     */   public static JnaNativePty current(TerminalProvider provider, SystemStream systemStream) throws IOException {
/*  42 */     if (Platform.isMac()) {
/*  43 */       if (Platform.is64Bit() && Platform.isARM()) {
/*  44 */         throw new UnsupportedOperationException();
/*     */       }
/*  46 */       return (JnaNativePty)OsXNativePty.current(provider, systemStream);
/*  47 */     }  if (Platform.isLinux())
/*  48 */       return (JnaNativePty)LinuxNativePty.current(provider, systemStream); 
/*  49 */     if (Platform.isSolaris())
/*  50 */       return (JnaNativePty)SolarisNativePty.current(provider, systemStream); 
/*  51 */     if (Platform.isFreeBSD()) {
/*  52 */       return (JnaNativePty)FreeBsdNativePty.current(provider, systemStream);
/*     */     }
/*  54 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public static JnaNativePty open(TerminalProvider provider, Attributes attr, Size size) throws IOException {
/*  59 */     if (Platform.isMac()) {
/*  60 */       if (Platform.is64Bit() && Platform.isARM()) {
/*  61 */         throw new UnsupportedOperationException();
/*     */       }
/*  63 */       return (JnaNativePty)OsXNativePty.open(provider, attr, size);
/*  64 */     }  if (Platform.isLinux())
/*  65 */       return (JnaNativePty)LinuxNativePty.open(provider, attr, size); 
/*  66 */     if (Platform.isSolaris())
/*  67 */       return (JnaNativePty)SolarisNativePty.open(provider, attr, size); 
/*  68 */     if (Platform.isFreeBSD()) {
/*  69 */       return (JnaNativePty)FreeBsdNativePty.open(provider, attr, size);
/*     */     }
/*  71 */     throw new UnsupportedOperationException();
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
/*     */   protected JnaNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, String name) {
/*  83 */     this(provider, systemStream, master, masterFD, slave, slaveFD, slave, slaveFD, name);
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
/*     */   protected JnaNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, int slaveOut, FileDescriptor slaveOutFD, String name) {
/*  96 */     super(provider, systemStream);
/*  97 */     this.master = master;
/*  98 */     this.slave = slave;
/*  99 */     this.slaveOut = slaveOut;
/* 100 */     this.name = name;
/* 101 */     this.masterFD = masterFD;
/* 102 */     this.slaveFD = slaveFD;
/* 103 */     this.slaveOutFD = slaveOutFD;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 108 */     if (this.master > 0) {
/* 109 */       getMasterInput().close();
/*     */     }
/* 111 */     if (this.slave > 0) {
/* 112 */       getSlaveInput().close();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaster() {
/* 117 */     return this.master;
/*     */   }
/*     */   
/*     */   public int getSlave() {
/* 121 */     return this.slave;
/*     */   }
/*     */   
/*     */   public int getSlaveOut() {
/* 125 */     return this.slaveOut;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 129 */     return this.name;
/*     */   }
/*     */   
/*     */   public FileDescriptor getMasterFD() {
/* 133 */     return this.masterFD;
/*     */   }
/*     */   
/*     */   public FileDescriptor getSlaveFD() {
/* 137 */     return this.slaveFD;
/*     */   }
/*     */   
/*     */   public FileDescriptor getSlaveOutFD() {
/* 141 */     return this.slaveOutFD;
/*     */   }
/*     */   
/*     */   public InputStream getMasterInput() {
/* 145 */     return new FileInputStream(getMasterFD());
/*     */   }
/*     */   
/*     */   public OutputStream getMasterOutput() {
/* 149 */     return new FileOutputStream(getMasterFD());
/*     */   }
/*     */   
/*     */   protected InputStream doGetSlaveInput() {
/* 153 */     return new FileInputStream(getSlaveFD());
/*     */   }
/*     */   
/*     */   public OutputStream getSlaveOutput() {
/* 157 */     return new FileOutputStream(getSlaveOutFD());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 162 */     return "JnaNativePty[" + getName() + "]";
/*     */   }
/*     */   
/*     */   public static boolean isPosixSystemStream(SystemStream stream) {
/* 166 */     switch (stream) {
/*     */       case Input:
/* 168 */         return isatty(0);
/*     */       case Output:
/* 170 */         return isatty(1);
/*     */       case Error:
/* 172 */         return isatty(2);
/*     */     } 
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String posixSystemStreamName(SystemStream stream) {
/* 179 */     switch (stream) {
/*     */       case Input:
/* 181 */         return ttyname(0);
/*     */       case Output:
/* 183 */         return ttyname(1);
/*     */       case Error:
/* 185 */         return ttyname(2);
/*     */     } 
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isatty(int fd) {
/* 192 */     if (Platform.isMac()) {
/* 193 */       if (Platform.is64Bit() && Platform.isARM()) {
/* 194 */         throw new UnsupportedOperationException("Unsupported platform mac-aarch64");
/*     */       }
/* 196 */       return (OsXNativePty.isatty(fd) == 1);
/* 197 */     }  if (Platform.isLinux())
/* 198 */       return (LinuxNativePty.isatty(fd) == 1); 
/* 199 */     if (Platform.isSolaris())
/* 200 */       return (SolarisNativePty.isatty(fd) == 1); 
/* 201 */     if (Platform.isFreeBSD()) {
/* 202 */       return (FreeBsdNativePty.isatty(fd) == 1);
/*     */     }
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String ttyname(int fd) {
/* 209 */     if (Platform.isMac()) {
/* 210 */       if (Platform.is64Bit() && Platform.isARM()) {
/* 211 */         throw new UnsupportedOperationException("Unsupported platform mac-aarch64");
/*     */       }
/* 213 */       return OsXNativePty.ttyname(fd);
/* 214 */     }  if (Platform.isLinux())
/* 215 */       return LinuxNativePty.ttyname(fd); 
/* 216 */     if (Platform.isSolaris())
/* 217 */       return SolarisNativePty.ttyname(fd); 
/* 218 */     if (Platform.isFreeBSD()) {
/* 219 */       return FreeBsdNativePty.ttyname(fd);
/*     */     }
/* 221 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\JnaNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */