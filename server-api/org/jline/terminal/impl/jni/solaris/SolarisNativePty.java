/*     */ package org.jline.terminal.impl.jni.solaris;public class SolarisNativePty extends JniNativePty { private static final int VINTR = 0; private static final int VQUIT = 1; private static final int VERASE = 2; private static final int VKILL = 3; private static final int VEOF = 4; private static final int VTIME = 5; private static final int VMIN = 6; private static final int VSWTC = 7; private static final int VSTART = 8; private static final int VSTOP = 9; private static final int VSUSP = 10; private static final int VEOL = 11; private static final int VREPRINT = 12; private static final int VDISCARD = 13; private static final int VWERASE = 14; private static final int VLNEXT = 15; private static final int VEOL2 = 16; private static final int IGNBRK = 1; private static final int BRKINT = 2; private static final int IGNPAR = 4; private static final int PARMRK = 16; private static final int INPCK = 32; private static final int ISTRIP = 64; private static final int INLCR = 256; private static final int IGNCR = 512; private static final int ICRNL = 1024; private static final int IUCLC = 4096; private static final int IXON = 8192; private static final int IXANY = 16384; private static final int IXOFF = 65536; private static final int IMAXBEL = 131072; private static final int IUTF8 = 262144;
/*     */   private static final int OPOST = 1;
/*     */   private static final int OLCUC = 2;
/*     */   private static final int ONLCR = 4;
/*     */   private static final int OCRNL = 16;
/*     */   private static final int ONOCR = 32;
/*     */   private static final int ONLRET = 64;
/*     */   private static final int OFILL = 256;
/*     */   private static final int OFDEL = 512;
/*     */   private static final int NLDLY = 1024;
/*     */   private static final int NL0 = 0;
/*     */   private static final int NL1 = 1024;
/*     */   private static final int CRDLY = 12288;
/*     */   private static final int CR0 = 0;
/*     */   private static final int CR1 = 4096;
/*     */   private static final int CR2 = 8192;
/*     */   private static final int CR3 = 12288;
/*     */   private static final int TABDLY = 81920;
/*     */   private static final int TAB0 = 0;
/*     */   private static final int TAB1 = 16384;
/*     */   private static final int TAB2 = 65536;
/*     */   private static final int TAB3 = 81920;
/*     */   private static final int XTABS = 81920;
/*     */   
/*     */   public static SolarisNativePty current(TerminalProvider provider, SystemStream systemStream) throws IOException {
/*     */     try {
/*  27 */       switch (systemStream) {
/*     */         case Output:
/*  29 */           return new SolarisNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 1, FileDescriptor.out, 
/*  30 */               ttyname(1));
/*     */         case Error:
/*  32 */           return new SolarisNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 2, FileDescriptor.err, 
/*  33 */               ttyname(2));
/*     */       } 
/*  35 */       throw new IllegalArgumentException("Unsupported stream for console: " + systemStream);
/*     */     }
/*  37 */     catch (IOException e) {
/*  38 */       throw new IOException("Not a tty", e);
/*     */     } 
/*     */   }
/*     */   private static final int BSDLY = 131072; private static final int BS0 = 0; private static final int BS1 = 131072; private static final int VTDLY = 262144; private static final int VT0 = 0; private static final int VT1 = 262144; private static final int FFDLY = 1048576; private static final int FF0 = 0; private static final int FF1 = 1048576; private static final int CBAUD = 65559; private static final int B0 = 0; private static final int B50 = 1; private static final int B75 = 2; private static final int B110 = 3; private static final int B134 = 4; private static final int B150 = 5; private static final int B200 = 6; private static final int B300 = 7; private static final int B600 = 16; private static final int B1200 = 17; private static final int B1800 = 18; private static final int B2400 = 19; private static final int B4800 = 20; private static final int B9600 = 21; private static final int B19200 = 22; private static final int B38400 = 23; private static final int EXTA = 11637248; private static final int EXTB = 11764736; private static final int CSIZE = 96; private static final int CS5 = 0; private static final int CS6 = 32; private static final int CS7 = 64; private static final int CS8 = 96; private static final int CSTOPB = 256; private static final int CREAD = 512; private static final int PARENB = 1024; private static final int PARODD = 4096; private static final int HUPCL = 8192; private static final int CLOCAL = 16384; private static final int ISIG = 1; private static final int ICANON = 2; private static final int XCASE = 4; private static final int ECHO = 16; private static final int ECHOE = 32; private static final int ECHOK = 64; private static final int ECHONL = 256; private static final int NOFLSH = 512; private static final int TOSTOP = 1024; private static final int ECHOCTL = 4096; private static final int ECHOPRT = 8192; private static final int ECHOKE = 16384; private static final int FLUSHO = 65536; private static final int PENDIN = 262144; private static final int IEXTEN = 1048576; private static final int EXTPROC = 2097152;
/*     */   public static SolarisNativePty open(TerminalProvider provider, Attributes attr, Size size) throws IOException {
/*  43 */     int[] master = new int[1];
/*  44 */     int[] slave = new int[1];
/*  45 */     byte[] buf = new byte[64];
/*  46 */     CLibrary.openpty(master, slave, buf, 
/*     */ 
/*     */ 
/*     */         
/*  50 */         (attr != null) ? termios(attr) : null, 
/*  51 */         (size != null) ? new CLibrary.WinSize((short)size.getRows(), (short)size.getColumns()) : null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CLibrary.Termios toTermios(Attributes t) {
/* 201 */     return termios(t);
/*     */   }
/*     */   
/*     */   static CLibrary.Termios termios(Attributes t) {
/* 205 */     CLibrary.Termios tio = new CLibrary.Termios();
/* 206 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNBRK), 1L, tio.c_iflag);
/* 207 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.BRKINT), 2L, tio.c_iflag);
/* 208 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNPAR), 4L, tio.c_iflag);
/* 209 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.PARMRK), 16L, tio.c_iflag);
/* 210 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INPCK), 32L, tio.c_iflag);
/* 211 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ISTRIP), 64L, tio.c_iflag);
/* 212 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INLCR), 256L, tio.c_iflag);
/* 213 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNCR), 512L, tio.c_iflag);
/* 214 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ICRNL), 1024L, tio.c_iflag);
/* 215 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXON), 8192L, tio.c_iflag);
/* 216 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXOFF), 65536L, tio.c_iflag);
/* 217 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXANY), 16384L, tio.c_iflag);
/* 218 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IMAXBEL), 131072L, tio.c_iflag);
/* 219 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IUTF8), 262144L, tio.c_iflag);
/*     */     
/* 221 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OPOST), 1L, tio.c_oflag);
/* 222 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLCR), 4L, tio.c_oflag);
/*     */ 
/*     */     
/* 225 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OCRNL), 16L, tio.c_oflag);
/* 226 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOCR), 32L, tio.c_oflag);
/* 227 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLRET), 64L, tio.c_oflag);
/* 228 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFILL), 256L, tio.c_oflag);
/* 229 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.NLDLY), 1024L, tio.c_oflag);
/* 230 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.TABDLY), 81920L, tio.c_oflag);
/* 231 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288L, tio.c_oflag);
/* 232 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.FFDLY), 1048576L, tio.c_oflag);
/* 233 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.BSDLY), 131072L, tio.c_oflag);
/* 234 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.VTDLY), 262144L, tio.c_oflag);
/* 235 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFDEL), 512L, tio.c_oflag);
/*     */ 
/*     */     
/* 238 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS5), 0L, tio.c_cflag);
/* 239 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS6), 32L, tio.c_cflag);
/* 240 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS7), 64L, tio.c_cflag);
/* 241 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS8), 96L, tio.c_cflag);
/* 242 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CSTOPB), 256L, tio.c_cflag);
/* 243 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CREAD), 512L, tio.c_cflag);
/* 244 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARENB), 1024L, tio.c_cflag);
/* 245 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARODD), 4096L, tio.c_cflag);
/* 246 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.HUPCL), 8192L, tio.c_cflag);
/* 247 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CLOCAL), 16384L, tio.c_cflag);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOKE), 16384L, tio.c_lflag);
/* 255 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOE), 32L, tio.c_lflag);
/* 256 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOK), 64L, tio.c_lflag);
/* 257 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHO), 16L, tio.c_lflag);
/* 258 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHONL), 256L, tio.c_lflag);
/* 259 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 8192L, tio.c_lflag);
/* 260 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 4096L, tio.c_lflag);
/* 261 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ISIG), 1L, tio.c_lflag);
/* 262 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ICANON), 2L, tio.c_lflag);
/*     */     
/* 264 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1048576L, tio.c_lflag);
/* 265 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2097152L, tio.c_lflag);
/* 266 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.TOSTOP), 1024L, tio.c_lflag);
/* 267 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.FLUSHO), 65536L, tio.c_lflag);
/*     */     
/* 269 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.PENDIN), 262144L, tio.c_lflag);
/* 270 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOFLSH), 512L, tio.c_lflag);
/*     */     
/* 272 */     tio.c_cc[4] = (byte)t.getControlChar(Attributes.ControlChar.VEOF);
/* 273 */     tio.c_cc[11] = (byte)t.getControlChar(Attributes.ControlChar.VEOL);
/* 274 */     tio.c_cc[16] = (byte)t.getControlChar(Attributes.ControlChar.VEOL2);
/* 275 */     tio.c_cc[2] = (byte)t.getControlChar(Attributes.ControlChar.VERASE);
/* 276 */     tio.c_cc[14] = (byte)t.getControlChar(Attributes.ControlChar.VWERASE);
/* 277 */     tio.c_cc[3] = (byte)t.getControlChar(Attributes.ControlChar.VKILL);
/* 278 */     tio.c_cc[12] = (byte)t.getControlChar(Attributes.ControlChar.VREPRINT);
/* 279 */     tio.c_cc[0] = (byte)t.getControlChar(Attributes.ControlChar.VINTR);
/* 280 */     tio.c_cc[1] = (byte)t.getControlChar(Attributes.ControlChar.VQUIT);
/* 281 */     tio.c_cc[10] = (byte)t.getControlChar(Attributes.ControlChar.VSUSP);
/*     */     
/* 283 */     tio.c_cc[8] = (byte)t.getControlChar(Attributes.ControlChar.VSTART);
/* 284 */     tio.c_cc[9] = (byte)t.getControlChar(Attributes.ControlChar.VSTOP);
/* 285 */     tio.c_cc[15] = (byte)t.getControlChar(Attributes.ControlChar.VLNEXT);
/* 286 */     tio.c_cc[13] = (byte)t.getControlChar(Attributes.ControlChar.VDISCARD);
/* 287 */     tio.c_cc[6] = (byte)t.getControlChar(Attributes.ControlChar.VMIN);
/* 288 */     tio.c_cc[5] = (byte)t.getControlChar(Attributes.ControlChar.VTIME);
/*     */     
/* 290 */     return tio;
/*     */   }
/*     */   
/*     */   protected Attributes toAttributes(CLibrary.Termios tio) {
/* 294 */     Attributes attr = new Attributes();
/*     */     
/* 296 */     EnumSet<Attributes.InputFlag> iflag = attr.getInputFlags();
/* 297 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 298 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 299 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.BRKINT, 2);
/* 300 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNPAR, 4);
/* 301 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.PARMRK, 16);
/* 302 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.INPCK, 32);
/* 303 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.ISTRIP, 64);
/* 304 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.INLCR, 256);
/* 305 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNCR, 512);
/* 306 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.ICRNL, 1024);
/* 307 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IXON, 8192);
/* 308 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IXOFF, 65536);
/* 309 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IXANY, 16384);
/* 310 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IMAXBEL, 131072);
/* 311 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IUTF8, 262144);
/*     */     
/* 313 */     EnumSet<Attributes.OutputFlag> oflag = attr.getOutputFlags();
/* 314 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OPOST, 1);
/* 315 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONLCR, 4);
/*     */ 
/*     */     
/* 318 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OCRNL, 16);
/* 319 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONOCR, 32);
/* 320 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONLRET, 64);
/* 321 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OFILL, 256);
/* 322 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.NLDLY, 1024);
/* 323 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.TABDLY, 81920);
/* 324 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.CRDLY, 12288);
/* 325 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.FFDLY, 1048576);
/* 326 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.BSDLY, 131072);
/* 327 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.VTDLY, 262144);
/* 328 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OFDEL, 512);
/*     */     
/* 330 */     EnumSet<Attributes.ControlFlag> cflag = attr.getControlFlags();
/*     */     
/* 332 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS5, 0);
/* 333 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS6, 32);
/* 334 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS7, 64);
/* 335 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS8, 96);
/* 336 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CSTOPB, 256);
/* 337 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CREAD, 512);
/* 338 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.PARENB, 1024);
/* 339 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.PARODD, 4096);
/* 340 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.HUPCL, 8192);
/* 341 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CLOCAL, 16384);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     EnumSet<Attributes.LocalFlag> lflag = attr.getLocalFlags();
/* 348 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOKE, 16384);
/* 349 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOE, 32);
/* 350 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOK, 64);
/* 351 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHO, 16);
/* 352 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHONL, 256);
/* 353 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOPRT, 8192);
/* 354 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOCTL, 4096);
/* 355 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ISIG, 1);
/* 356 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ICANON, 2);
/*     */     
/* 358 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.IEXTEN, 1048576);
/* 359 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.EXTPROC, 2097152);
/* 360 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.TOSTOP, 1024);
/* 361 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.FLUSHO, 65536);
/*     */     
/* 363 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.PENDIN, 262144);
/* 364 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.NOFLSH, 512);
/*     */     
/* 366 */     EnumMap<Attributes.ControlChar, Integer> cc = attr.getControlChars();
/* 367 */     cc.put(Attributes.ControlChar.VEOF, Integer.valueOf(tio.c_cc[4]));
/* 368 */     cc.put(Attributes.ControlChar.VEOL, Integer.valueOf(tio.c_cc[11]));
/* 369 */     cc.put(Attributes.ControlChar.VEOL2, Integer.valueOf(tio.c_cc[16]));
/* 370 */     cc.put(Attributes.ControlChar.VERASE, Integer.valueOf(tio.c_cc[2]));
/* 371 */     cc.put(Attributes.ControlChar.VWERASE, Integer.valueOf(tio.c_cc[14]));
/* 372 */     cc.put(Attributes.ControlChar.VKILL, Integer.valueOf(tio.c_cc[3]));
/* 373 */     cc.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(tio.c_cc[12]));
/* 374 */     cc.put(Attributes.ControlChar.VINTR, Integer.valueOf(tio.c_cc[0]));
/* 375 */     cc.put(Attributes.ControlChar.VQUIT, Integer.valueOf(tio.c_cc[1]));
/* 376 */     cc.put(Attributes.ControlChar.VSUSP, Integer.valueOf(tio.c_cc[10]));
/*     */     
/* 378 */     cc.put(Attributes.ControlChar.VSTART, Integer.valueOf(tio.c_cc[8]));
/* 379 */     cc.put(Attributes.ControlChar.VSTOP, Integer.valueOf(tio.c_cc[9]));
/* 380 */     cc.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(tio.c_cc[15]));
/* 381 */     cc.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(tio.c_cc[13]));
/* 382 */     cc.put(Attributes.ControlChar.VMIN, Integer.valueOf(tio.c_cc[6]));
/* 383 */     cc.put(Attributes.ControlChar.VTIME, Integer.valueOf(tio.c_cc[5]));
/*     */ 
/*     */     
/* 386 */     return attr;
/*     */   }
/*     */   
/*     */   private static long setFlag(boolean flag, long value, long org) {
/* 390 */     return flag ? (org | value) : org;
/*     */   }
/*     */   
/*     */   private static <T extends Enum<T>> void addFlag(long value, EnumSet<T> flags, T flag, int v) {
/* 394 */     if ((value & v) != 0L)
/* 395 */       flags.add(flag); 
/*     */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jni\solaris\SolarisNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */