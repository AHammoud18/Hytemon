/*     */ package org.jline.terminal.impl.jni.osx;public class OsXNativePty extends JniNativePty { private static final int VEOF = 0; private static final int VEOL = 1; private static final int VEOL2 = 2; private static final int VERASE = 3; private static final int VWERASE = 4; private static final int VKILL = 5; private static final int VREPRINT = 6; private static final int VINTR = 8; private static final int VQUIT = 9; private static final int VSUSP = 10; private static final int VDSUSP = 11; private static final int VSTART = 12; private static final int VSTOP = 13; private static final int VLNEXT = 14; private static final int VDISCARD = 15; private static final int VMIN = 16; private static final int VTIME = 17; private static final int VSTATUS = 18;
/*     */   private static final int IGNBRK = 1;
/*     */   private static final int BRKINT = 2;
/*     */   private static final int IGNPAR = 4;
/*     */   private static final int PARMRK = 8;
/*     */   private static final int INPCK = 16;
/*     */   private static final int ISTRIP = 32;
/*     */   private static final int INLCR = 64;
/*     */   private static final int IGNCR = 128;
/*     */   private static final int ICRNL = 256;
/*     */   private static final int IXON = 512;
/*     */   private static final int IXOFF = 1024;
/*     */   private static final int IXANY = 2048;
/*     */   private static final int IMAXBEL = 8192;
/*     */   private static final int IUTF8 = 16384;
/*     */   private static final int OPOST = 1;
/*     */   private static final int ONLCR = 2;
/*     */   private static final int OXTABS = 4;
/*     */   private static final int ONOEOT = 8;
/*     */   private static final int OCRNL = 16;
/*     */   private static final int ONOCR = 32;
/*     */   private static final int ONLRET = 64;
/*     */   private static final int OFILL = 128;
/*     */   
/*     */   public static OsXNativePty current(TerminalProvider provider, SystemStream systemStream) throws IOException {
/*     */     try {
/*  27 */       switch (systemStream) {
/*     */         case Output:
/*  29 */           return new OsXNativePty(provider, SystemStream.Output, -1, null, 0, FileDescriptor.in, 1, FileDescriptor.out, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  38 */               ttyname(1));
/*     */         case Error:
/*  40 */           return new OsXNativePty(provider, SystemStream.Error, -1, null, 0, FileDescriptor.in, 2, FileDescriptor.err, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  49 */               ttyname(2));
/*     */       } 
/*  51 */       throw new IllegalArgumentException("Unsupported stream for console: " + systemStream);
/*     */     }
/*  53 */     catch (IOException e) {
/*  54 */       throw new IOException("Not a tty", e);
/*     */     } 
/*     */   }
/*     */   private static final int NLDLY = 768; private static final int TABDLY = 3076; private static final int CRDLY = 12288; private static final int FFDLY = 16384; private static final int BSDLY = 32768; private static final int VTDLY = 65536; private static final int OFDEL = 131072; private static final int CIGNORE = 1; private static final int CS5 = 0; private static final int CS6 = 256; private static final int CS7 = 512; private static final int CS8 = 768; private static final int CSTOPB = 1024; private static final int CREAD = 2048; private static final int PARENB = 4096; private static final int PARODD = 8192; private static final int HUPCL = 16384; private static final int CLOCAL = 32768; private static final int CCTS_OFLOW = 65536; private static final int CRTS_IFLOW = 131072; private static final int CDTR_IFLOW = 262144; private static final int CDSR_OFLOW = 524288; private static final int CCAR_OFLOW = 1048576; private static final int ECHOKE = 1; private static final int ECHOE = 2; private static final int ECHOK = 4; private static final int ECHO = 8; private static final int ECHONL = 16; private static final int ECHOPRT = 32; private static final int ECHOCTL = 64; private static final int ISIG = 128; private static final int ICANON = 256; private static final int ALTWERASE = 512; private static final int IEXTEN = 1024; private static final int EXTPROC = 2048; private static final int TOSTOP = 4194304; private static final int FLUSHO = 8388608; private static final int NOKERNINFO = 33554432; private static final int PENDIN = 536870912; private static final int NOFLSH = -2147483648;
/*     */   public static OsXNativePty open(TerminalProvider provider, Attributes attr, Size size) throws IOException {
/*  59 */     int[] master = new int[1];
/*  60 */     int[] slave = new int[1];
/*  61 */     byte[] buf = new byte[64];
/*  62 */     CLibrary.openpty(master, slave, buf, 
/*     */ 
/*     */ 
/*     */         
/*  66 */         (attr != null) ? termios(attr) : null, 
/*  67 */         (size != null) ? new CLibrary.WinSize((short)size.getRows(), (short)size.getColumns()) : null);
/*  68 */     int len = 0;
/*  69 */     while (buf[len] != 0) {
/*  70 */       len++;
/*     */     }
/*  72 */     String name = new String(buf, 0, len);
/*  73 */     return new OsXNativePty(provider, null, master[0], 
/*  74 */         newDescriptor(master[0]), slave[0], newDescriptor(slave[0]), name);
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
/*  85 */     super(provider, systemStream, master, masterFD, slave, slaveFD, name);
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
/*  98 */     super(provider, systemStream, master, masterFD, slave, slaveFD, slaveOut, slaveOutFD, name);
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
/*     */   protected CLibrary.Termios toTermios(Attributes t) {
/* 189 */     return termios(t);
/*     */   }
/*     */   
/*     */   static CLibrary.Termios termios(Attributes t) {
/* 193 */     CLibrary.Termios tio = new CLibrary.Termios();
/* 194 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNBRK), 1L, tio.c_iflag);
/* 195 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.BRKINT), 2L, tio.c_iflag);
/* 196 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNPAR), 4L, tio.c_iflag);
/* 197 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.PARMRK), 8L, tio.c_iflag);
/* 198 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INPCK), 16L, tio.c_iflag);
/* 199 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ISTRIP), 32L, tio.c_iflag);
/* 200 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INLCR), 64L, tio.c_iflag);
/* 201 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNCR), 128L, tio.c_iflag);
/* 202 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ICRNL), 256L, tio.c_iflag);
/* 203 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXON), 512L, tio.c_iflag);
/* 204 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXOFF), 1024L, tio.c_iflag);
/* 205 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXANY), 2048L, tio.c_iflag);
/* 206 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IMAXBEL), 8192L, tio.c_iflag);
/* 207 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IUTF8), 16384L, tio.c_iflag);
/*     */     
/* 209 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OPOST), 1L, tio.c_oflag);
/* 210 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLCR), 2L, tio.c_oflag);
/* 211 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OXTABS), 4L, tio.c_oflag);
/* 212 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOEOT), 8L, tio.c_oflag);
/* 213 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OCRNL), 16L, tio.c_oflag);
/* 214 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOCR), 32L, tio.c_oflag);
/* 215 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLRET), 64L, tio.c_oflag);
/* 216 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFILL), 128L, tio.c_oflag);
/* 217 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.NLDLY), 768L, tio.c_oflag);
/* 218 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.TABDLY), 3076L, tio.c_oflag);
/* 219 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288L, tio.c_oflag);
/* 220 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.FFDLY), 16384L, tio.c_oflag);
/* 221 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.BSDLY), 32768L, tio.c_oflag);
/* 222 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.VTDLY), 65536L, tio.c_oflag);
/* 223 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFDEL), 131072L, tio.c_oflag);
/*     */     
/* 225 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CIGNORE), 1L, tio.c_cflag);
/* 226 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS5), 0L, tio.c_cflag);
/* 227 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS6), 256L, tio.c_cflag);
/* 228 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS7), 512L, tio.c_cflag);
/* 229 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS8), 768L, tio.c_cflag);
/* 230 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CSTOPB), 1024L, tio.c_cflag);
/* 231 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CREAD), 2048L, tio.c_cflag);
/* 232 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARENB), 4096L, tio.c_cflag);
/* 233 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARODD), 8192L, tio.c_cflag);
/* 234 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.HUPCL), 16384L, tio.c_cflag);
/* 235 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CLOCAL), 32768L, tio.c_cflag);
/* 236 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CCTS_OFLOW), 65536L, tio.c_cflag);
/* 237 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CRTS_IFLOW), 131072L, tio.c_cflag);
/* 238 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CDTR_IFLOW), 262144L, tio.c_cflag);
/* 239 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CDSR_OFLOW), 524288L, tio.c_cflag);
/* 240 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CCAR_OFLOW), 1048576L, tio.c_cflag);
/*     */     
/* 242 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOKE), 1L, tio.c_lflag);
/* 243 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOE), 2L, tio.c_lflag);
/* 244 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOK), 4L, tio.c_lflag);
/* 245 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHO), 8L, tio.c_lflag);
/* 246 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHONL), 16L, tio.c_lflag);
/* 247 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 32L, tio.c_lflag);
/* 248 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 64L, tio.c_lflag);
/* 249 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ISIG), 128L, tio.c_lflag);
/* 250 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ICANON), 256L, tio.c_lflag);
/* 251 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ALTWERASE), 512L, tio.c_lflag);
/* 252 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1024L, tio.c_lflag);
/* 253 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2048L, tio.c_lflag);
/* 254 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.TOSTOP), 4194304L, tio.c_lflag);
/* 255 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.FLUSHO), 8388608L, tio.c_lflag);
/* 256 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOKERNINFO), 33554432L, tio.c_lflag);
/* 257 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.PENDIN), 536870912L, tio.c_lflag);
/* 258 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOFLSH), -2147483648L, tio.c_lflag);
/*     */     
/* 260 */     tio.c_cc[0] = (byte)t.getControlChar(Attributes.ControlChar.VEOF);
/* 261 */     tio.c_cc[1] = (byte)t.getControlChar(Attributes.ControlChar.VEOL);
/* 262 */     tio.c_cc[2] = (byte)t.getControlChar(Attributes.ControlChar.VEOL2);
/* 263 */     tio.c_cc[3] = (byte)t.getControlChar(Attributes.ControlChar.VERASE);
/* 264 */     tio.c_cc[4] = (byte)t.getControlChar(Attributes.ControlChar.VWERASE);
/* 265 */     tio.c_cc[5] = (byte)t.getControlChar(Attributes.ControlChar.VKILL);
/* 266 */     tio.c_cc[6] = (byte)t.getControlChar(Attributes.ControlChar.VREPRINT);
/* 267 */     tio.c_cc[8] = (byte)t.getControlChar(Attributes.ControlChar.VINTR);
/* 268 */     tio.c_cc[9] = (byte)t.getControlChar(Attributes.ControlChar.VQUIT);
/* 269 */     tio.c_cc[10] = (byte)t.getControlChar(Attributes.ControlChar.VSUSP);
/* 270 */     tio.c_cc[11] = (byte)t.getControlChar(Attributes.ControlChar.VDSUSP);
/* 271 */     tio.c_cc[12] = (byte)t.getControlChar(Attributes.ControlChar.VSTART);
/* 272 */     tio.c_cc[13] = (byte)t.getControlChar(Attributes.ControlChar.VSTOP);
/* 273 */     tio.c_cc[14] = (byte)t.getControlChar(Attributes.ControlChar.VLNEXT);
/* 274 */     tio.c_cc[15] = (byte)t.getControlChar(Attributes.ControlChar.VDISCARD);
/* 275 */     tio.c_cc[16] = (byte)t.getControlChar(Attributes.ControlChar.VMIN);
/* 276 */     tio.c_cc[17] = (byte)t.getControlChar(Attributes.ControlChar.VTIME);
/* 277 */     tio.c_cc[18] = (byte)t.getControlChar(Attributes.ControlChar.VSTATUS);
/* 278 */     return tio;
/*     */   }
/*     */   
/*     */   protected Attributes toAttributes(CLibrary.Termios tio) {
/* 282 */     Attributes attr = new Attributes();
/*     */     
/* 284 */     EnumSet<Attributes.InputFlag> iflag = attr.getInputFlags();
/* 285 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 286 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 287 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.BRKINT, 2);
/* 288 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNPAR, 4);
/* 289 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.PARMRK, 8);
/* 290 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.INPCK, 16);
/* 291 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.ISTRIP, 32);
/* 292 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.INLCR, 64);
/* 293 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNCR, 128);
/* 294 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.ICRNL, 256);
/* 295 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IXON, 512);
/* 296 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IXOFF, 1024);
/* 297 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IXANY, 2048);
/* 298 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IMAXBEL, 8192);
/* 299 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IUTF8, 16384);
/*     */     
/* 301 */     EnumSet<Attributes.OutputFlag> oflag = attr.getOutputFlags();
/* 302 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OPOST, 1);
/* 303 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONLCR, 2);
/* 304 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OXTABS, 4);
/* 305 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONOEOT, 8);
/* 306 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OCRNL, 16);
/* 307 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONOCR, 32);
/* 308 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONLRET, 64);
/* 309 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OFILL, 128);
/* 310 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.NLDLY, 768);
/* 311 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.TABDLY, 3076);
/* 312 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.CRDLY, 12288);
/* 313 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.FFDLY, 16384);
/* 314 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.BSDLY, 32768);
/* 315 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.VTDLY, 65536);
/* 316 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OFDEL, 131072);
/*     */     
/* 318 */     EnumSet<Attributes.ControlFlag> cflag = attr.getControlFlags();
/* 319 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CIGNORE, 1);
/* 320 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS5, 0);
/* 321 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS6, 256);
/* 322 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS7, 512);
/* 323 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS8, 768);
/* 324 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CSTOPB, 1024);
/* 325 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CREAD, 2048);
/* 326 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.PARENB, 4096);
/* 327 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.PARODD, 8192);
/* 328 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.HUPCL, 16384);
/* 329 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CLOCAL, 32768);
/* 330 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CCTS_OFLOW, 65536);
/* 331 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CRTS_IFLOW, 131072);
/* 332 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CDSR_OFLOW, 524288);
/* 333 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CCAR_OFLOW, 1048576);
/*     */     
/* 335 */     EnumSet<Attributes.LocalFlag> lflag = attr.getLocalFlags();
/* 336 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOKE, 1);
/* 337 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOE, 2);
/* 338 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOK, 4);
/* 339 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHO, 8);
/* 340 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHONL, 16);
/* 341 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOPRT, 32);
/* 342 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOCTL, 64);
/* 343 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ISIG, 128);
/* 344 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ICANON, 256);
/* 345 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ALTWERASE, 512);
/* 346 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.IEXTEN, 1024);
/* 347 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.EXTPROC, 2048);
/* 348 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.TOSTOP, 4194304);
/* 349 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.FLUSHO, 8388608);
/* 350 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.NOKERNINFO, 33554432);
/* 351 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.PENDIN, 536870912);
/* 352 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.NOFLSH, -2147483648);
/*     */     
/* 354 */     EnumMap<Attributes.ControlChar, Integer> cc = attr.getControlChars();
/* 355 */     cc.put(Attributes.ControlChar.VEOF, Integer.valueOf(tio.c_cc[0]));
/* 356 */     cc.put(Attributes.ControlChar.VEOL, Integer.valueOf(tio.c_cc[1]));
/* 357 */     cc.put(Attributes.ControlChar.VEOL2, Integer.valueOf(tio.c_cc[2]));
/* 358 */     cc.put(Attributes.ControlChar.VERASE, Integer.valueOf(tio.c_cc[3]));
/* 359 */     cc.put(Attributes.ControlChar.VWERASE, Integer.valueOf(tio.c_cc[4]));
/* 360 */     cc.put(Attributes.ControlChar.VKILL, Integer.valueOf(tio.c_cc[5]));
/* 361 */     cc.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(tio.c_cc[6]));
/* 362 */     cc.put(Attributes.ControlChar.VINTR, Integer.valueOf(tio.c_cc[8]));
/* 363 */     cc.put(Attributes.ControlChar.VQUIT, Integer.valueOf(tio.c_cc[9]));
/* 364 */     cc.put(Attributes.ControlChar.VSUSP, Integer.valueOf(tio.c_cc[10]));
/* 365 */     cc.put(Attributes.ControlChar.VDSUSP, Integer.valueOf(tio.c_cc[11]));
/* 366 */     cc.put(Attributes.ControlChar.VSTART, Integer.valueOf(tio.c_cc[12]));
/* 367 */     cc.put(Attributes.ControlChar.VSTOP, Integer.valueOf(tio.c_cc[13]));
/* 368 */     cc.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(tio.c_cc[14]));
/* 369 */     cc.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(tio.c_cc[15]));
/* 370 */     cc.put(Attributes.ControlChar.VMIN, Integer.valueOf(tio.c_cc[16]));
/* 371 */     cc.put(Attributes.ControlChar.VTIME, Integer.valueOf(tio.c_cc[17]));
/* 372 */     cc.put(Attributes.ControlChar.VSTATUS, Integer.valueOf(tio.c_cc[18]));
/*     */     
/* 374 */     return attr;
/*     */   }
/*     */   
/*     */   private static long setFlag(boolean flag, long value, long org) {
/* 378 */     return flag ? (org | value) : org;
/*     */   }
/*     */   
/*     */   private static <T extends Enum<T>> void addFlag(long value, EnumSet<T> flags, T flag, int v) {
/* 382 */     if ((value & v) != 0L)
/* 383 */       flags.add(flag); 
/*     */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jni\osx\OsXNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */