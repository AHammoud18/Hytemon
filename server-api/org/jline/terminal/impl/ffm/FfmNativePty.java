/*     */ package org.jline.terminal.impl.ffm;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.impl.AbstractPty;
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
/*     */ class FfmNativePty
/*     */   extends AbstractPty
/*     */ {
/*     */   private final int master;
/*     */   private final int slave;
/*     */   private final int slaveOut;
/*     */   private final String name;
/*     */   private final FileDescriptor masterFD;
/*     */   private final FileDescriptor slaveFD;
/*     */   private final FileDescriptor slaveOutFD;
/*     */   
/*     */   public FfmNativePty(TerminalProvider provider, SystemStream systemStream, int master, int slave, String name) {
/*  34 */     this(provider, systemStream, master, 
/*     */ 
/*     */ 
/*     */         
/*  38 */         newDescriptor(master), slave, 
/*     */         
/*  40 */         newDescriptor(slave), slave, 
/*     */         
/*  42 */         newDescriptor(slave), name);
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
/*     */   public FfmNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, int slaveOut, FileDescriptor slaveOutFD, String name) {
/*  56 */     super(provider, systemStream);
/*  57 */     this.master = master;
/*  58 */     this.slave = slave;
/*  59 */     this.slaveOut = slaveOut;
/*  60 */     this.name = name;
/*  61 */     this.masterFD = masterFD;
/*  62 */     this.slaveFD = slaveFD;
/*  63 */     this.slaveOutFD = slaveOutFD;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  68 */     if (this.master > 0) {
/*  69 */       getMasterInput().close();
/*     */     }
/*  71 */     if (this.slave > 0) {
/*  72 */       getSlaveInput().close();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaster() {
/*  77 */     return this.master;
/*     */   }
/*     */   
/*     */   public int getSlave() {
/*  81 */     return this.slave;
/*     */   }
/*     */   
/*     */   public int getSlaveOut() {
/*  85 */     return this.slaveOut;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  89 */     return this.name;
/*     */   }
/*     */   
/*     */   public FileDescriptor getMasterFD() {
/*  93 */     return this.masterFD;
/*     */   }
/*     */   
/*     */   public FileDescriptor getSlaveFD() {
/*  97 */     return this.slaveFD;
/*     */   }
/*     */   
/*     */   public FileDescriptor getSlaveOutFD() {
/* 101 */     return this.slaveOutFD;
/*     */   }
/*     */   
/*     */   public InputStream getMasterInput() {
/* 105 */     return new FileInputStream(getMasterFD());
/*     */   }
/*     */   
/*     */   public OutputStream getMasterOutput() {
/* 109 */     return new FileOutputStream(getMasterFD());
/*     */   }
/*     */   
/*     */   protected InputStream doGetSlaveInput() {
/* 113 */     return new FileInputStream(getSlaveFD());
/*     */   }
/*     */   
/*     */   public OutputStream getSlaveOutput() {
/* 117 */     return new FileOutputStream(getSlaveOutFD());
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttr() throws IOException {
/* 122 */     return CLibrary.getAttributes(this.slave);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetAttr(Attributes attr) throws IOException {
/* 127 */     CLibrary.setAttributes(this.slave, attr);
/*     */   }
/*     */ 
/*     */   
/*     */   public Size getSize() throws IOException {
/* 132 */     return CLibrary.getTerminalSize(this.slave);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(Size size) throws IOException {
/* 137 */     CLibrary.setTerminalSize(this.slave, size);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 142 */     return "FfmNativePty[" + getName() + "]";
/*     */   }
/*     */   
/*     */   public static boolean isPosixSystemStream(SystemStream stream) {
/* 146 */     switch (stream) {
/*     */       case Input:
/* 148 */         return CLibrary.isTty(0);
/*     */       case Output:
/* 150 */         return CLibrary.isTty(1);
/*     */       case Error:
/* 152 */         return CLibrary.isTty(2);
/*     */     } 
/* 154 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String posixSystemStreamName(SystemStream stream) {
/* 159 */     switch (stream) {
/*     */       case Input:
/* 161 */         return CLibrary.ttyName(0);
/*     */       case Output:
/* 163 */         return CLibrary.ttyName(1);
/*     */       case Error:
/* 165 */         return CLibrary.ttyName(2);
/*     */     } 
/* 167 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int systemStreamWidth(SystemStream systemStream) {
/* 172 */     int fd = (systemStream == SystemStream.Output) ? 1 : 2;
/* 173 */     return CLibrary.getTerminalSize(fd).getColumns();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\ffm\FfmNativePty.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */