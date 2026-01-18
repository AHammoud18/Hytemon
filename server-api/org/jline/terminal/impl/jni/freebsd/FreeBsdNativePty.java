/*     */ package org.jline.terminal.impl.jni.freebsd;public class FreeBsdNativePty extends JniNativePty { private static final int VEOF = 0; private static final int VEOL = 1; private static final int VEOL2 = 2; private static final int VERASE = 3; private static final int VWERASE = 4; private static final int VKILL = 5; private static final int VREPRINT = 6; private static final int VERASE2 = 7; private static final int VINTR = 8; private static final int VQUIT = 9; private static final int VSUSP = 10; private static final int VDSUSP = 11;
/*     */   private static final int VSTART = 12;
/*     */   private static final int VSTOP = 13;
/*     */   private static final int VLNEXT = 14;
/*     */   private static final int VDISCARD = 15;
/*     */   private static final int VMIN = 16;
/*     */   private static final int VTIME = 17;
/*     */   private static final int VSTATUS = 18;
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
/*     */   private static final int OPOST = 1;
/*     */   private static final int ONLCR = 2;
/*     */   
/*     */   public static FreeBsdNativePty current(TerminalProvider provider, SystemStream systemStream) throws IOException {
/*     */     try {
/*  27 */       switch (systemStream) {
/*     */         case Output:
/*  29 */           return new FreeBsdNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 1, FileDescriptor.out, 
/*  30 */               ttyname(1));
/*     */         case Error:
/*  32 */           return new FreeBsdNativePty(provider, systemStream, -1, null, 0, FileDescriptor.in, 2, FileDescriptor.err, 
/*  33 */               ttyname(2));
/*     */       } 
/*  35 */       throw new IllegalArgumentException("Unsupported stream for console: " + systemStream);
/*     */     }
/*  37 */     catch (IOException e) {
/*  38 */       throw new IOException("Not a tty", e);
/*     */     } 
/*     */   }
/*     */   private static final int TABDLY = 4; private static final int TAB0 = 0; private static final int TAB3 = 4; private static final int ONOEOT = 8; private static final int OCRNL = 16; private static final int ONLRET = 64; private static final int CIGNORE = 1; private static final int CSIZE = 768; private static final int CS5 = 0; private static final int CS6 = 256; private static final int CS7 = 512; private static final int CS8 = 768; private static final int CSTOPB = 1024; private static final int CREAD = 2048; private static final int PARENB = 4096; private static final int PARODD = 8192; private static final int HUPCL = 16384; private static final int CLOCAL = 32768; private static final int ECHOKE = 1; private static final int ECHOE = 2; private static final int ECHOK = 4; private static final int ECHO = 8; private static final int ECHONL = 16; private static final int ECHOPRT = 32; private static final int ECHOCTL = 64; private static final int ISIG = 128; private static final int ICANON = 256; private static final int ALTWERASE = 512; private static final int IEXTEN = 1024; private static final int EXTPROC = 2048; private static final int TOSTOP = 4194304; private static final int FLUSHO = 8388608; private static final int PENDIN = 33554432; private static final int NOFLSH = 134217728;
/*     */   public static FreeBsdNativePty open(TerminalProvider provider, Attributes attr, Size size) throws IOException {
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
/*  57 */     return new FreeBsdNativePty(provider, null, master[0], 
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
/*     */   public FreeBsdNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, String name) {
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
/*     */   public FreeBsdNativePty(TerminalProvider provider, SystemStream systemStream, int master, FileDescriptor masterFD, int slave, FileDescriptor slaveFD, int slaveOut, FileDescriptor slaveOutFD, String name) {
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
/*     */   protected CLibrary.Termios toTermios(Attributes t) {
/* 160 */     return termios(t);
/*     */   }
/*     */   
/*     */   static CLibrary.Termios termios(Attributes t) {
/* 164 */     CLibrary.Termios tio = new CLibrary.Termios();
/* 165 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNBRK), 1L, tio.c_iflag);
/* 166 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.BRKINT), 2L, tio.c_iflag);
/* 167 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNPAR), 4L, tio.c_iflag);
/* 168 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.PARMRK), 8L, tio.c_iflag);
/* 169 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INPCK), 16L, tio.c_iflag);
/* 170 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ISTRIP), 32L, tio.c_iflag);
/* 171 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INLCR), 64L, tio.c_iflag);
/* 172 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNCR), 128L, tio.c_iflag);
/* 173 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ICRNL), 256L, tio.c_iflag);
/* 174 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXON), 512L, tio.c_iflag);
/* 175 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXOFF), 1024L, tio.c_iflag);
/* 176 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXANY), 2048L, tio.c_iflag);
/* 177 */     tio.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IMAXBEL), 8192L, tio.c_iflag);
/*     */ 
/*     */     
/* 180 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OPOST), 1L, tio.c_oflag);
/* 181 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLCR), 2L, tio.c_oflag);
/*     */     
/* 183 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOEOT), 8L, tio.c_oflag);
/* 184 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OCRNL), 16L, tio.c_oflag);
/*     */     
/* 186 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLRET), 64L, tio.c_oflag);
/*     */ 
/*     */     
/* 189 */     tio.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.TABDLY), 4L, tio.c_oflag);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CIGNORE), 1L, tio.c_cflag);
/* 197 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS5), 0L, tio.c_cflag);
/* 198 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS6), 256L, tio.c_cflag);
/* 199 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS7), 512L, tio.c_cflag);
/* 200 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS8), 768L, tio.c_cflag);
/* 201 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CSTOPB), 1024L, tio.c_cflag);
/* 202 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CREAD), 2048L, tio.c_cflag);
/* 203 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARENB), 4096L, tio.c_cflag);
/* 204 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARODD), 8192L, tio.c_cflag);
/* 205 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.HUPCL), 16384L, tio.c_cflag);
/* 206 */     tio.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CLOCAL), 32768L, tio.c_cflag);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOKE), 1L, tio.c_lflag);
/* 214 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOE), 2L, tio.c_lflag);
/* 215 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOK), 4L, tio.c_lflag);
/* 216 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHO), 8L, tio.c_lflag);
/* 217 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHONL), 16L, tio.c_lflag);
/* 218 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 32L, tio.c_lflag);
/* 219 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 64L, tio.c_lflag);
/* 220 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ISIG), 128L, tio.c_lflag);
/* 221 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ICANON), 256L, tio.c_lflag);
/* 222 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ALTWERASE), 512L, tio.c_lflag);
/* 223 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1024L, tio.c_lflag);
/* 224 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2048L, tio.c_lflag);
/* 225 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.TOSTOP), 4194304L, tio.c_lflag);
/* 226 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.FLUSHO), 8388608L, tio.c_lflag);
/*     */     
/* 228 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.PENDIN), 33554432L, tio.c_lflag);
/* 229 */     tio.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOFLSH), 134217728L, tio.c_lflag);
/*     */     
/* 231 */     tio.c_cc[0] = (byte)t.getControlChar(Attributes.ControlChar.VEOF);
/* 232 */     tio.c_cc[1] = (byte)t.getControlChar(Attributes.ControlChar.VEOL);
/* 233 */     tio.c_cc[2] = (byte)t.getControlChar(Attributes.ControlChar.VEOL2);
/* 234 */     tio.c_cc[3] = (byte)t.getControlChar(Attributes.ControlChar.VERASE);
/* 235 */     tio.c_cc[4] = (byte)t.getControlChar(Attributes.ControlChar.VWERASE);
/* 236 */     tio.c_cc[5] = (byte)t.getControlChar(Attributes.ControlChar.VKILL);
/* 237 */     tio.c_cc[6] = (byte)t.getControlChar(Attributes.ControlChar.VREPRINT);
/* 238 */     tio.c_cc[8] = (byte)t.getControlChar(Attributes.ControlChar.VINTR);
/* 239 */     tio.c_cc[9] = (byte)t.getControlChar(Attributes.ControlChar.VQUIT);
/* 240 */     tio.c_cc[10] = (byte)t.getControlChar(Attributes.ControlChar.VSUSP);
/*     */     
/* 242 */     tio.c_cc[12] = (byte)t.getControlChar(Attributes.ControlChar.VSTART);
/* 243 */     tio.c_cc[13] = (byte)t.getControlChar(Attributes.ControlChar.VSTOP);
/* 244 */     tio.c_cc[14] = (byte)t.getControlChar(Attributes.ControlChar.VLNEXT);
/* 245 */     tio.c_cc[15] = (byte)t.getControlChar(Attributes.ControlChar.VDISCARD);
/* 246 */     tio.c_cc[16] = (byte)t.getControlChar(Attributes.ControlChar.VMIN);
/* 247 */     tio.c_cc[17] = (byte)t.getControlChar(Attributes.ControlChar.VTIME);
/*     */     
/* 249 */     return tio;
/*     */   }
/*     */   
/*     */   protected Attributes toAttributes(CLibrary.Termios tio) {
/* 253 */     Attributes attr = new Attributes();
/*     */     
/* 255 */     EnumSet<Attributes.InputFlag> iflag = attr.getInputFlags();
/* 256 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 257 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 258 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.BRKINT, 2);
/* 259 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNPAR, 4);
/* 260 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.PARMRK, 8);
/* 261 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.INPCK, 16);
/* 262 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.ISTRIP, 32);
/* 263 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.INLCR, 64);
/* 264 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IGNCR, 128);
/* 265 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.ICRNL, 256);
/* 266 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IXON, 512);
/* 267 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IXOFF, 1024);
/* 268 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IXANY, 2048);
/* 269 */     addFlag(tio.c_iflag, iflag, Attributes.InputFlag.IMAXBEL, 8192);
/*     */ 
/*     */     
/* 272 */     EnumSet<Attributes.OutputFlag> oflag = attr.getOutputFlags();
/* 273 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OPOST, 1);
/* 274 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONLCR, 2);
/*     */     
/* 276 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONOEOT, 8);
/* 277 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.OCRNL, 16);
/*     */     
/* 279 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.ONLRET, 64);
/*     */ 
/*     */     
/* 282 */     addFlag(tio.c_oflag, oflag, Attributes.OutputFlag.TABDLY, 4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     EnumSet<Attributes.ControlFlag> cflag = attr.getControlFlags();
/* 290 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CIGNORE, 1);
/* 291 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS5, 0);
/* 292 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS6, 256);
/* 293 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS7, 512);
/* 294 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CS8, 768);
/* 295 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CSTOPB, 1024);
/* 296 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CREAD, 2048);
/* 297 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.PARENB, 4096);
/* 298 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.PARODD, 8192);
/* 299 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.HUPCL, 16384);
/* 300 */     addFlag(tio.c_cflag, cflag, Attributes.ControlFlag.CLOCAL, 32768);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 306 */     EnumSet<Attributes.LocalFlag> lflag = attr.getLocalFlags();
/* 307 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOKE, 1);
/* 308 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOE, 2);
/* 309 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOK, 4);
/* 310 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHO, 8);
/* 311 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHONL, 16);
/* 312 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOPRT, 32);
/* 313 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ECHOCTL, 64);
/* 314 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ISIG, 128);
/* 315 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ICANON, 256);
/* 316 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.ALTWERASE, 512);
/* 317 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.IEXTEN, 1024);
/* 318 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.EXTPROC, 2048);
/* 319 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.TOSTOP, 4194304);
/* 320 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.FLUSHO, 8388608);
/*     */     
/* 322 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.PENDIN, 33554432);
/* 323 */     addFlag(tio.c_lflag, lflag, Attributes.LocalFlag.NOFLSH, 134217728);
/*     */     
/* 325 */     EnumMap<Attributes.ControlChar, Integer> cc = attr.getControlChars();
/* 326 */     cc.put(Attributes.ControlChar.VEOF, Integer.valueOf(tio.c_cc[0]));
/* 327 */     cc.put(Attributes.ControlChar.VEOL, Integer.valueOf(tio.c_cc[1]));
/* 328 */     cc.put(Attributes.ControlChar.VEOL2, Integer.valueOf(tio.c_cc[2]));
/* 329 */     cc.put(Attributes.ControlChar.VERASE, Integer.valueOf(tio.c_cc[3]));
/* 330 */     cc.put(Attributes.ControlChar.VWERASE, Integer.valueOf(tio.c_cc[4]));
/* 331 */     cc.put(Attributes.ControlChar.VKILL, Integer.valueOf(tio.c_cc[5]));
/* 332 */     cc.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(tio.c_cc[6]));
/* 333 */     cc.put(Attributes.ControlChar.VINTR, Integer.valueOf(tio.c_cc[8]));
/* 334 */     cc.put(Attributes.ControlChar.VQUIT, Integer.valueOf(tio.c_cc[9]));
/* 335 */     cc.put(Attributes.ControlChar.VSUSP, Integer.valueOf(tio.c_cc[10]));
/*     */     
/* 337 */     cc.put(Attributes.ControlChar.VSTART, Integer.valueOf(tio.c_cc[12]));
/* 338 */     cc.put(Attributes.ControlChar.VSTOP, Integer.valueOf(tio.c_cc[13]));
/* 339 */     cc.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(tio.c_cc[14]));
/* 340 */     cc.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(tio.c_cc[15]));
/* 341 */     cc.put(Attributes.ControlChar.VMIN, Integer.valueOf(tio.c_cc[16]));
/* 342 */     cc.put(Attributes.ControlChar.VTIME, Integer.valueOf(tio.c_cc[17]));
/*     */ 
/*     */     
/* 345 */     return attr;
/*     */   }
/*     */   
/*     */   private static long setFlag(boolean flag, long value, long org) {
/* 349 */     return flag ? (org | value) : org;
/*     */   }
/*     */   
/*     */   private static <T extends Enum<T>> void addFlag(long value, EnumSet<T> flags, T flag, int v) {
/* 353 */     if ((value & v) != 0L)
/* 354 */       flags.add(flag); 
/*     */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jni\freebsd\FreeBsdNativePty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */