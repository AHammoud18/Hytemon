/*     */ package org.jline.terminal.impl.jna.solaris;public interface CLibrary extends Library { public static final int _TIOC = 21504; public static final int TIOCGWINSZ = 21608; public static final int TIOCSWINSZ = 21607; public static final int VINTR = 0; public static final int VQUIT = 1; public static final int VERASE = 2; public static final int VKILL = 3; public static final int VEOF = 4; public static final int VTIME = 5; public static final int VMIN = 6; public static final int VSWTC = 7; public static final int VSTART = 8; public static final int VSTOP = 9; public static final int VSUSP = 10; public static final int VEOL = 11; public static final int VREPRINT = 12; public static final int VDISCARD = 13; public static final int VWERASE = 14; public static final int VLNEXT = 15; public static final int VEOL2 = 16; public static final int IGNBRK = 1; public static final int BRKINT = 2; public static final int IGNPAR = 4; public static final int PARMRK = 16; public static final int INPCK = 32; public static final int ISTRIP = 64; public static final int INLCR = 256; public static final int IGNCR = 512; public static final int ICRNL = 1024; public static final int IUCLC = 4096; public static final int IXON = 8192; public static final int IXANY = 16384; public static final int IXOFF = 65536; public static final int IMAXBEL = 131072; public static final int IUTF8 = 262144; public static final int OPOST = 1; public static final int OLCUC = 2; public static final int ONLCR = 4; public static final int OCRNL = 16; public static final int ONOCR = 32; public static final int ONLRET = 64; public static final int OFILL = 256; public static final int OFDEL = 512; public static final int NLDLY = 1024; public static final int NL0 = 0; public static final int NL1 = 1024; public static final int CRDLY = 12288; public static final int CR0 = 0; public static final int CR1 = 4096; public static final int CR2 = 8192; public static final int CR3 = 12288; public static final int TABDLY = 81920; public static final int TAB0 = 0; public static final int TAB1 = 16384; public static final int TAB2 = 65536; public static final int TAB3 = 81920; public static final int XTABS = 81920; public static final int BSDLY = 131072; public static final int BS0 = 0; public static final int BS1 = 131072; public static final int VTDLY = 262144; public static final int VT0 = 0; public static final int VT1 = 262144; public static final int FFDLY = 1048576; public static final int FF0 = 0; public static final int FF1 = 1048576; public static final int CBAUD = 65559; public static final int B0 = 0; public static final int B50 = 1; public static final int B75 = 2; public static final int B110 = 3; public static final int B134 = 4; public static final int B150 = 5; public static final int B200 = 6; public static final int B300 = 7; public static final int B600 = 16; public static final int B1200 = 17; public static final int B1800 = 18; public static final int B2400 = 19; public static final int B4800 = 20; public static final int B9600 = 21; public static final int B19200 = 22; public static final int B38400 = 23; public static final int EXTA = 11637248;
/*     */   public static final int EXTB = 11764736;
/*     */   public static final int CSIZE = 96;
/*     */   public static final int CS5 = 0;
/*     */   public static final int CS6 = 32;
/*     */   public static final int CS7 = 64;
/*     */   public static final int CS8 = 96;
/*     */   public static final int CSTOPB = 256;
/*     */   public static final int CREAD = 512;
/*     */   public static final int PARENB = 1024;
/*     */   public static final int PARODD = 4096;
/*     */   public static final int HUPCL = 8192;
/*     */   public static final int CLOCAL = 16384;
/*     */   public static final int ISIG = 1;
/*     */   public static final int ICANON = 2;
/*     */   public static final int XCASE = 4;
/*     */   public static final int ECHO = 16;
/*     */   public static final int ECHOE = 32;
/*     */   public static final int ECHOK = 64;
/*     */   public static final int ECHONL = 256;
/*     */   public static final int NOFLSH = 512;
/*     */   public static final int TOSTOP = 1024;
/*     */   public static final int ECHOCTL = 4096;
/*     */   public static final int ECHOPRT = 8192;
/*     */   public static final int ECHOKE = 16384;
/*     */   public static final int FLUSHO = 65536;
/*     */   public static final int PENDIN = 262144;
/*     */   public static final int IEXTEN = 1048576;
/*     */   public static final int EXTPROC = 2097152;
/*     */   public static final int TCSANOW = 0;
/*     */   public static final int TCSADRAIN = 1;
/*     */   public static final int TCSAFLUSH = 2;
/*     */   
/*     */   void tcgetattr(int paramInt, termios paramtermios) throws LastErrorException;
/*     */   
/*     */   void tcsetattr(int paramInt1, int paramInt2, termios paramtermios) throws LastErrorException;
/*     */   
/*     */   void ioctl(int paramInt, long paramLong, winsize paramwinsize) throws LastErrorException;
/*     */   
/*     */   int isatty(int paramInt);
/*     */   
/*     */   void ttyname_r(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws LastErrorException;
/*     */   
/*     */   void openpty(int[] paramArrayOfint1, int[] paramArrayOfint2, byte[] paramArrayOfbyte, termios paramtermios, winsize paramwinsize) throws LastErrorException;
/*     */   
/*     */   public static class winsize extends Structure { public short ws_row;
/*     */     public short ws_col;
/*     */     
/*     */     public winsize(Size ws) {
/*  50 */       this.ws_row = (short)ws.getRows();
/*  51 */       this.ws_col = (short)ws.getColumns();
/*     */     } public short ws_xpixel; public short ws_ypixel;
/*     */     public winsize() {}
/*     */     public Size toSize() {
/*  55 */       return new Size(this.ws_col, this.ws_row);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  60 */       return Arrays.asList(new String[] { "ws_row", "ws_col", "ws_xpixel", "ws_ypixel" });
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class termios
/*     */     extends Structure
/*     */   {
/*     */     public int c_iflag;
/*     */     
/*     */     public int c_oflag;
/*     */     
/*     */     public int c_cflag;
/*     */     
/*     */     public int c_lflag;
/*  75 */     public byte[] c_cc = new byte[32];
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  79 */       return Arrays.asList(new String[] { "c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc" });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public termios() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public termios(Attributes t) {
/*  92 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNBRK), 1, this.c_iflag);
/*  93 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.BRKINT), 2, this.c_iflag);
/*  94 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNPAR), 4, this.c_iflag);
/*  95 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.PARMRK), 16, this.c_iflag);
/*  96 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INPCK), 32, this.c_iflag);
/*  97 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ISTRIP), 64, this.c_iflag);
/*  98 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INLCR), 256, this.c_iflag);
/*  99 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNCR), 512, this.c_iflag);
/* 100 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ICRNL), 1024, this.c_iflag);
/* 101 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXON), 8192, this.c_iflag);
/* 102 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXOFF), 65536, this.c_iflag);
/* 103 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXANY), 16384, this.c_iflag);
/* 104 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IMAXBEL), 131072, this.c_iflag);
/* 105 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IUTF8), 262144, this.c_iflag);
/*     */       
/* 107 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OPOST), 1, this.c_oflag);
/* 108 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLCR), 4, this.c_oflag);
/* 109 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OCRNL), 16, this.c_oflag);
/* 110 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOCR), 32, this.c_oflag);
/* 111 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLRET), 64, this.c_oflag);
/* 112 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFILL), 256, this.c_oflag);
/* 113 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.NLDLY), 1024, this.c_oflag);
/* 114 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.TABDLY), 81920, this.c_oflag);
/* 115 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288, this.c_oflag);
/* 116 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.FFDLY), 1048576, this.c_oflag);
/* 117 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.BSDLY), 131072, this.c_oflag);
/* 118 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.VTDLY), 262144, this.c_oflag);
/* 119 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFDEL), 512, this.c_oflag);
/*     */       
/* 121 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS5), 0, this.c_cflag);
/* 122 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS6), 32, this.c_cflag);
/* 123 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS7), 64, this.c_cflag);
/* 124 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS8), 96, this.c_cflag);
/* 125 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CSTOPB), 256, this.c_cflag);
/* 126 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CREAD), 512, this.c_cflag);
/* 127 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARENB), 1024, this.c_cflag);
/* 128 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARODD), 4096, this.c_cflag);
/* 129 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.HUPCL), 8192, this.c_cflag);
/* 130 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CLOCAL), 16384, this.c_cflag);
/*     */       
/* 132 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOKE), 16384, this.c_lflag);
/* 133 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOE), 32, this.c_lflag);
/* 134 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOK), 64, this.c_lflag);
/* 135 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHO), 16, this.c_lflag);
/* 136 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHONL), 256, this.c_lflag);
/* 137 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 8192, this.c_lflag);
/* 138 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 4096, this.c_lflag);
/* 139 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ISIG), 1, this.c_lflag);
/* 140 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ICANON), 2, this.c_lflag);
/* 141 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1048576, this.c_lflag);
/* 142 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2097152, this.c_lflag);
/* 143 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.TOSTOP), 1024, this.c_lflag);
/* 144 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.FLUSHO), 65536, this.c_lflag);
/* 145 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.PENDIN), 262144, this.c_lflag);
/* 146 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOFLSH), 512, this.c_lflag);
/*     */       
/* 148 */       this.c_cc[4] = (byte)t.getControlChar(Attributes.ControlChar.VEOF);
/* 149 */       this.c_cc[11] = (byte)t.getControlChar(Attributes.ControlChar.VEOL);
/* 150 */       this.c_cc[16] = (byte)t.getControlChar(Attributes.ControlChar.VEOL2);
/* 151 */       this.c_cc[2] = (byte)t.getControlChar(Attributes.ControlChar.VERASE);
/* 152 */       this.c_cc[14] = (byte)t.getControlChar(Attributes.ControlChar.VWERASE);
/* 153 */       this.c_cc[3] = (byte)t.getControlChar(Attributes.ControlChar.VKILL);
/* 154 */       this.c_cc[12] = (byte)t.getControlChar(Attributes.ControlChar.VREPRINT);
/* 155 */       this.c_cc[0] = (byte)t.getControlChar(Attributes.ControlChar.VINTR);
/* 156 */       this.c_cc[1] = (byte)t.getControlChar(Attributes.ControlChar.VQUIT);
/* 157 */       this.c_cc[10] = (byte)t.getControlChar(Attributes.ControlChar.VSUSP);
/* 158 */       this.c_cc[8] = (byte)t.getControlChar(Attributes.ControlChar.VSTART);
/* 159 */       this.c_cc[9] = (byte)t.getControlChar(Attributes.ControlChar.VSTOP);
/* 160 */       this.c_cc[15] = (byte)t.getControlChar(Attributes.ControlChar.VLNEXT);
/* 161 */       this.c_cc[13] = (byte)t.getControlChar(Attributes.ControlChar.VDISCARD);
/* 162 */       this.c_cc[6] = (byte)t.getControlChar(Attributes.ControlChar.VMIN);
/* 163 */       this.c_cc[5] = (byte)t.getControlChar(Attributes.ControlChar.VTIME);
/*     */     }
/*     */     
/*     */     private int setFlag(boolean flag, int value, int org) {
/* 167 */       return flag ? (org | value) : org;
/*     */     }
/*     */     
/*     */     public Attributes toAttributes() {
/* 171 */       Attributes attr = new Attributes();
/*     */       
/* 173 */       EnumSet<Attributes.InputFlag> iflag = attr.getInputFlags();
/* 174 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 175 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 176 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.BRKINT, 2);
/* 177 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNPAR, 4);
/* 178 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.PARMRK, 16);
/* 179 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.INPCK, 32);
/* 180 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.ISTRIP, 64);
/* 181 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.INLCR, 256);
/* 182 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNCR, 512);
/* 183 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.ICRNL, 1024);
/* 184 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IXON, 8192);
/* 185 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IXOFF, 65536);
/* 186 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IXANY, 16384);
/* 187 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IMAXBEL, 131072);
/* 188 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IUTF8, 262144);
/*     */       
/* 190 */       EnumSet<Attributes.OutputFlag> oflag = attr.getOutputFlags();
/* 191 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OPOST, 1);
/* 192 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.ONLCR, 4);
/* 193 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OCRNL, 16);
/* 194 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.ONOCR, 32);
/* 195 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.ONLRET, 64);
/* 196 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OFILL, 256);
/* 197 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.NLDLY, 1024);
/* 198 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.TABDLY, 81920);
/* 199 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.CRDLY, 12288);
/* 200 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.FFDLY, 1048576);
/* 201 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.BSDLY, 131072);
/* 202 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.VTDLY, 262144);
/* 203 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OFDEL, 512);
/*     */       
/* 205 */       EnumSet<Attributes.ControlFlag> cflag = attr.getControlFlags();
/* 206 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS5, 0);
/* 207 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS6, 32);
/* 208 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS7, 64);
/* 209 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS8, 96);
/* 210 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CSTOPB, 256);
/* 211 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CREAD, 512);
/* 212 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.PARENB, 1024);
/* 213 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.PARODD, 4096);
/* 214 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.HUPCL, 8192);
/* 215 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CLOCAL, 16384);
/*     */       
/* 217 */       EnumSet<Attributes.LocalFlag> lflag = attr.getLocalFlags();
/* 218 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOKE, 16384);
/* 219 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOE, 32);
/* 220 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOK, 64);
/* 221 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHO, 16);
/* 222 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHONL, 256);
/* 223 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOPRT, 8192);
/* 224 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOCTL, 4096);
/* 225 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ISIG, 1);
/* 226 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ICANON, 2);
/* 227 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.IEXTEN, 1048576);
/* 228 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.EXTPROC, 2097152);
/* 229 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.TOSTOP, 1024);
/* 230 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.FLUSHO, 65536);
/* 231 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.PENDIN, 262144);
/* 232 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.NOFLSH, 512);
/*     */       
/* 234 */       EnumMap<Attributes.ControlChar, Integer> cc = attr.getControlChars();
/* 235 */       cc.put(Attributes.ControlChar.VEOF, Integer.valueOf(this.c_cc[4]));
/* 236 */       cc.put(Attributes.ControlChar.VEOL, Integer.valueOf(this.c_cc[11]));
/* 237 */       cc.put(Attributes.ControlChar.VEOL2, Integer.valueOf(this.c_cc[16]));
/* 238 */       cc.put(Attributes.ControlChar.VERASE, Integer.valueOf(this.c_cc[2]));
/* 239 */       cc.put(Attributes.ControlChar.VWERASE, Integer.valueOf(this.c_cc[14]));
/* 240 */       cc.put(Attributes.ControlChar.VKILL, Integer.valueOf(this.c_cc[3]));
/* 241 */       cc.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(this.c_cc[12]));
/* 242 */       cc.put(Attributes.ControlChar.VINTR, Integer.valueOf(this.c_cc[0]));
/* 243 */       cc.put(Attributes.ControlChar.VQUIT, Integer.valueOf(this.c_cc[1]));
/* 244 */       cc.put(Attributes.ControlChar.VSUSP, Integer.valueOf(this.c_cc[10]));
/* 245 */       cc.put(Attributes.ControlChar.VSTART, Integer.valueOf(this.c_cc[8]));
/* 246 */       cc.put(Attributes.ControlChar.VSTOP, Integer.valueOf(this.c_cc[9]));
/* 247 */       cc.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(this.c_cc[15]));
/* 248 */       cc.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(this.c_cc[13]));
/* 249 */       cc.put(Attributes.ControlChar.VMIN, Integer.valueOf(this.c_cc[6]));
/* 250 */       cc.put(Attributes.ControlChar.VTIME, Integer.valueOf(this.c_cc[5]));
/*     */       
/* 252 */       return attr;
/*     */     }
/*     */     
/*     */     private <T extends Enum<T>> void addFlag(int value, EnumSet<T> flags, T flag, int v) {
/* 256 */       if ((value & v) != 0)
/* 257 */         flags.add(flag); 
/*     */     }
/*     */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\solaris\CLibrary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */