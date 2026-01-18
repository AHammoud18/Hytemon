/*     */ package org.jline.terminal.impl.jna.osx;public interface CLibrary extends Library { public static final long TIOCGWINSZ = 1074295912L; public static final long TIOCSWINSZ = 2148037735L; public static final int TCSANOW = 0; public static final int VEOF = 0; public static final int VEOL = 1; public static final int VEOL2 = 2; public static final int VERASE = 3; public static final int VWERASE = 4; public static final int VKILL = 5; public static final int VREPRINT = 6; public static final int VINTR = 8; public static final int VQUIT = 9; public static final int VSUSP = 10; public static final int VDSUSP = 11; public static final int VSTART = 12; public static final int VSTOP = 13; public static final int VLNEXT = 14; public static final int VDISCARD = 15; public static final int VMIN = 16; public static final int VTIME = 17; public static final int VSTATUS = 18; public static final int IGNBRK = 1; public static final int BRKINT = 2; public static final int IGNPAR = 4; public static final int PARMRK = 8; public static final int INPCK = 16; public static final int ISTRIP = 32; public static final int INLCR = 64; public static final int IGNCR = 128; public static final int ICRNL = 256; public static final int IXON = 512; public static final int IXOFF = 1024; public static final int IXANY = 2048; public static final int IMAXBEL = 8192; public static final int IUTF8 = 16384; public static final int OPOST = 1; public static final int ONLCR = 2; public static final int OXTABS = 4; public static final int ONOEOT = 8; public static final int OCRNL = 16; public static final int ONOCR = 32; public static final int ONLRET = 64; public static final int OFILL = 128; public static final int NLDLY = 768; public static final int TABDLY = 3076; public static final int CRDLY = 12288; public static final int FFDLY = 16384; public static final int BSDLY = 32768; public static final int VTDLY = 65536; public static final int OFDEL = 131072; public static final int CIGNORE = 1;
/*     */   public static final int CS5 = 0;
/*     */   public static final int CS6 = 256;
/*     */   public static final int CS7 = 512;
/*     */   public static final int CS8 = 768;
/*     */   public static final int CSTOPB = 1024;
/*     */   public static final int CREAD = 2048;
/*     */   public static final int PARENB = 4096;
/*     */   public static final int PARODD = 8192;
/*     */   public static final int HUPCL = 16384;
/*     */   public static final int CLOCAL = 32768;
/*     */   public static final int CCTS_OFLOW = 65536;
/*     */   public static final int CRTS_IFLOW = 131072;
/*     */   public static final int CDTR_IFLOW = 262144;
/*     */   public static final int CDSR_OFLOW = 524288;
/*     */   public static final int CCAR_OFLOW = 1048576;
/*     */   public static final int ECHOKE = 1;
/*     */   public static final int ECHOE = 2;
/*     */   public static final int ECHOK = 4;
/*     */   public static final int ECHO = 8;
/*     */   public static final int ECHONL = 16;
/*     */   public static final int ECHOPRT = 32;
/*     */   public static final int ECHOCTL = 64;
/*     */   public static final int ISIG = 128;
/*     */   public static final int ICANON = 256;
/*     */   public static final int ALTWERASE = 512;
/*     */   public static final int IEXTEN = 1024;
/*     */   public static final int EXTPROC = 2048;
/*     */   public static final int TOSTOP = 4194304;
/*     */   public static final int FLUSHO = 8388608;
/*     */   public static final int NOKERNINFO = 33554432;
/*     */   public static final int PENDIN = 536870912;
/*     */   public static final int NOFLSH = -2147483648;
/*     */   
/*     */   void tcgetattr(int paramInt, termios paramtermios) throws LastErrorException;
/*     */   
/*     */   void tcsetattr(int paramInt1, int paramInt2, termios paramtermios) throws LastErrorException;
/*     */   
/*     */   void ioctl(int paramInt, NativeLong paramNativeLong, winsize paramwinsize) throws LastErrorException;
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
/*  51 */       this.ws_row = (short)ws.getRows();
/*  52 */       this.ws_col = (short)ws.getColumns();
/*     */     } public short ws_xpixel; public short ws_ypixel;
/*     */     public winsize() {}
/*     */     public Size toSize() {
/*  56 */       return new Size(this.ws_col, this.ws_row);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  61 */       return Arrays.asList(new String[] { "ws_row", "ws_col", "ws_xpixel", "ws_ypixel" });
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class termios
/*     */     extends Structure
/*     */   {
/*     */     public NativeLong c_iflag;
/*     */     
/*     */     public NativeLong c_oflag;
/*     */     
/*     */     public NativeLong c_cflag;
/*     */     
/*     */     public NativeLong c_lflag;
/*  76 */     public byte[] c_cc = new byte[20];
/*     */     
/*     */     public NativeLong c_ispeed;
/*     */     public NativeLong c_ospeed;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  82 */       return Arrays.asList(new String[] { "c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc", "c_ispeed", "c_ospeed" });
/*     */     }
/*     */ 
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
/*     */     
/*     */     public termios(Attributes t) {
/*  97 */       setFlag(t.getInputFlag(Attributes.InputFlag.IGNBRK), 1L, this.c_iflag);
/*  98 */       setFlag(t.getInputFlag(Attributes.InputFlag.BRKINT), 2L, this.c_iflag);
/*  99 */       setFlag(t.getInputFlag(Attributes.InputFlag.IGNPAR), 4L, this.c_iflag);
/* 100 */       setFlag(t.getInputFlag(Attributes.InputFlag.PARMRK), 8L, this.c_iflag);
/* 101 */       setFlag(t.getInputFlag(Attributes.InputFlag.INPCK), 16L, this.c_iflag);
/* 102 */       setFlag(t.getInputFlag(Attributes.InputFlag.ISTRIP), 32L, this.c_iflag);
/* 103 */       setFlag(t.getInputFlag(Attributes.InputFlag.INLCR), 64L, this.c_iflag);
/* 104 */       setFlag(t.getInputFlag(Attributes.InputFlag.IGNCR), 128L, this.c_iflag);
/* 105 */       setFlag(t.getInputFlag(Attributes.InputFlag.ICRNL), 256L, this.c_iflag);
/* 106 */       setFlag(t.getInputFlag(Attributes.InputFlag.IXON), 512L, this.c_iflag);
/* 107 */       setFlag(t.getInputFlag(Attributes.InputFlag.IXOFF), 1024L, this.c_iflag);
/* 108 */       setFlag(t.getInputFlag(Attributes.InputFlag.IXANY), 2048L, this.c_iflag);
/* 109 */       setFlag(t.getInputFlag(Attributes.InputFlag.IMAXBEL), 8192L, this.c_iflag);
/* 110 */       setFlag(t.getInputFlag(Attributes.InputFlag.IUTF8), 16384L, this.c_iflag);
/*     */       
/* 112 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.OPOST), 1L, this.c_oflag);
/* 113 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLCR), 2L, this.c_oflag);
/* 114 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.OXTABS), 4L, this.c_oflag);
/* 115 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOEOT), 8L, this.c_oflag);
/* 116 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.OCRNL), 16L, this.c_oflag);
/* 117 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOCR), 32L, this.c_oflag);
/* 118 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLRET), 64L, this.c_oflag);
/* 119 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.OFILL), 128L, this.c_oflag);
/* 120 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.NLDLY), 768L, this.c_oflag);
/* 121 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.TABDLY), 3076L, this.c_oflag);
/* 122 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288L, this.c_oflag);
/* 123 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.FFDLY), 16384L, this.c_oflag);
/* 124 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.BSDLY), 32768L, this.c_oflag);
/* 125 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.VTDLY), 65536L, this.c_oflag);
/* 126 */       setFlag(t.getOutputFlag(Attributes.OutputFlag.OFDEL), 131072L, this.c_oflag);
/*     */       
/* 128 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CIGNORE), 1L, this.c_cflag);
/* 129 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CS5), 0L, this.c_cflag);
/* 130 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CS6), 256L, this.c_cflag);
/* 131 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CS7), 512L, this.c_cflag);
/* 132 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CS8), 768L, this.c_cflag);
/* 133 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CSTOPB), 1024L, this.c_cflag);
/* 134 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CREAD), 2048L, this.c_cflag);
/* 135 */       setFlag(t.getControlFlag(Attributes.ControlFlag.PARENB), 4096L, this.c_cflag);
/* 136 */       setFlag(t.getControlFlag(Attributes.ControlFlag.PARODD), 8192L, this.c_cflag);
/* 137 */       setFlag(t.getControlFlag(Attributes.ControlFlag.HUPCL), 16384L, this.c_cflag);
/* 138 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CLOCAL), 32768L, this.c_cflag);
/* 139 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CCTS_OFLOW), 65536L, this.c_cflag);
/* 140 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CRTS_IFLOW), 131072L, this.c_cflag);
/* 141 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CDTR_IFLOW), 262144L, this.c_cflag);
/* 142 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CDSR_OFLOW), 524288L, this.c_cflag);
/* 143 */       setFlag(t.getControlFlag(Attributes.ControlFlag.CCAR_OFLOW), 1048576L, this.c_cflag);
/*     */       
/* 145 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOKE), 1L, this.c_lflag);
/* 146 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOE), 2L, this.c_lflag);
/* 147 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOK), 4L, this.c_lflag);
/* 148 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHO), 8L, this.c_lflag);
/* 149 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHONL), 16L, this.c_lflag);
/* 150 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 32L, this.c_lflag);
/* 151 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 64L, this.c_lflag);
/* 152 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ISIG), 128L, this.c_lflag);
/* 153 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ICANON), 256L, this.c_lflag);
/* 154 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.ALTWERASE), 512L, this.c_lflag);
/* 155 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1024L, this.c_lflag);
/* 156 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2048L, this.c_lflag);
/* 157 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.TOSTOP), 4194304L, this.c_lflag);
/* 158 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.FLUSHO), 8388608L, this.c_lflag);
/* 159 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.NOKERNINFO), 33554432L, this.c_lflag);
/* 160 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.PENDIN), 536870912L, this.c_lflag);
/* 161 */       setFlag(t.getLocalFlag(Attributes.LocalFlag.NOFLSH), -2147483648L, this.c_lflag);
/*     */       
/* 163 */       this.c_cc[0] = (byte)t.getControlChar(Attributes.ControlChar.VEOF);
/* 164 */       this.c_cc[1] = (byte)t.getControlChar(Attributes.ControlChar.VEOL);
/* 165 */       this.c_cc[2] = (byte)t.getControlChar(Attributes.ControlChar.VEOL2);
/* 166 */       this.c_cc[3] = (byte)t.getControlChar(Attributes.ControlChar.VERASE);
/* 167 */       this.c_cc[4] = (byte)t.getControlChar(Attributes.ControlChar.VWERASE);
/* 168 */       this.c_cc[5] = (byte)t.getControlChar(Attributes.ControlChar.VKILL);
/* 169 */       this.c_cc[6] = (byte)t.getControlChar(Attributes.ControlChar.VREPRINT);
/* 170 */       this.c_cc[8] = (byte)t.getControlChar(Attributes.ControlChar.VINTR);
/* 171 */       this.c_cc[9] = (byte)t.getControlChar(Attributes.ControlChar.VQUIT);
/* 172 */       this.c_cc[10] = (byte)t.getControlChar(Attributes.ControlChar.VSUSP);
/* 173 */       this.c_cc[11] = (byte)t.getControlChar(Attributes.ControlChar.VDSUSP);
/* 174 */       this.c_cc[12] = (byte)t.getControlChar(Attributes.ControlChar.VSTART);
/* 175 */       this.c_cc[13] = (byte)t.getControlChar(Attributes.ControlChar.VSTOP);
/* 176 */       this.c_cc[14] = (byte)t.getControlChar(Attributes.ControlChar.VLNEXT);
/* 177 */       this.c_cc[15] = (byte)t.getControlChar(Attributes.ControlChar.VDISCARD);
/* 178 */       this.c_cc[16] = (byte)t.getControlChar(Attributes.ControlChar.VMIN);
/* 179 */       this.c_cc[17] = (byte)t.getControlChar(Attributes.ControlChar.VTIME);
/* 180 */       this.c_cc[18] = (byte)t.getControlChar(Attributes.ControlChar.VSTATUS);
/*     */     }
/*     */     
/*     */     private void setFlag(boolean flag, long value, NativeLong org) {
/* 184 */       org.setValue(flag ? (org.longValue() | value) : org.longValue());
/*     */     }
/*     */     
/*     */     public Attributes toAttributes() {
/* 188 */       Attributes attr = new Attributes();
/*     */       
/* 190 */       EnumSet<Attributes.InputFlag> iflag = attr.getInputFlags();
/* 191 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.IGNBRK, 1);
/* 192 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.IGNBRK, 1);
/* 193 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.BRKINT, 2);
/* 194 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.IGNPAR, 4);
/* 195 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.PARMRK, 8);
/* 196 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.INPCK, 16);
/* 197 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.ISTRIP, 32);
/* 198 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.INLCR, 64);
/* 199 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.IGNCR, 128);
/* 200 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.ICRNL, 256);
/* 201 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.IXON, 512);
/* 202 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.IXOFF, 1024);
/* 203 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.IXANY, 2048);
/* 204 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.IMAXBEL, 8192);
/* 205 */       addFlag(this.c_iflag.longValue(), iflag, Attributes.InputFlag.IUTF8, 16384);
/*     */       
/* 207 */       EnumSet<Attributes.OutputFlag> oflag = attr.getOutputFlags();
/* 208 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.OPOST, 1);
/* 209 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.ONLCR, 2);
/* 210 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.OXTABS, 4);
/* 211 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.ONOEOT, 8);
/* 212 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.OCRNL, 16);
/* 213 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.ONOCR, 32);
/* 214 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.ONLRET, 64);
/* 215 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.OFILL, 128);
/* 216 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.NLDLY, 768);
/* 217 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.TABDLY, 3076);
/* 218 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.CRDLY, 12288);
/* 219 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.FFDLY, 16384);
/* 220 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.BSDLY, 32768);
/* 221 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.VTDLY, 65536);
/* 222 */       addFlag(this.c_oflag.longValue(), oflag, Attributes.OutputFlag.OFDEL, 131072);
/*     */       
/* 224 */       EnumSet<Attributes.ControlFlag> cflag = attr.getControlFlags();
/* 225 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CIGNORE, 1);
/* 226 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CS5, 0);
/* 227 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CS6, 256);
/* 228 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CS7, 512);
/* 229 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CS8, 768);
/* 230 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CSTOPB, 1024);
/* 231 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CREAD, 2048);
/* 232 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.PARENB, 4096);
/* 233 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.PARODD, 8192);
/* 234 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.HUPCL, 16384);
/* 235 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CLOCAL, 32768);
/* 236 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CCTS_OFLOW, 65536);
/* 237 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CRTS_IFLOW, 131072);
/* 238 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CDSR_OFLOW, 524288);
/* 239 */       addFlag(this.c_cflag.longValue(), cflag, Attributes.ControlFlag.CCAR_OFLOW, 1048576);
/*     */       
/* 241 */       EnumSet<Attributes.LocalFlag> lflag = attr.getLocalFlags();
/* 242 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ECHOKE, 1);
/* 243 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ECHOE, 2);
/* 244 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ECHOK, 4);
/* 245 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ECHO, 8);
/* 246 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ECHONL, 16);
/* 247 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ECHOPRT, 32);
/* 248 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ECHOCTL, 64);
/* 249 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ISIG, 128);
/* 250 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ICANON, 256);
/* 251 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.ALTWERASE, 512);
/* 252 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.IEXTEN, 1024);
/* 253 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.EXTPROC, 2048);
/* 254 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.TOSTOP, 4194304);
/* 255 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.FLUSHO, 8388608);
/* 256 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.NOKERNINFO, 33554432);
/* 257 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.PENDIN, 536870912);
/* 258 */       addFlag(this.c_lflag.longValue(), lflag, Attributes.LocalFlag.NOFLSH, -2147483648);
/*     */       
/* 260 */       EnumMap<Attributes.ControlChar, Integer> cc = attr.getControlChars();
/* 261 */       cc.put(Attributes.ControlChar.VEOF, Integer.valueOf(this.c_cc[0]));
/* 262 */       cc.put(Attributes.ControlChar.VEOL, Integer.valueOf(this.c_cc[1]));
/* 263 */       cc.put(Attributes.ControlChar.VEOL2, Integer.valueOf(this.c_cc[2]));
/* 264 */       cc.put(Attributes.ControlChar.VERASE, Integer.valueOf(this.c_cc[3]));
/* 265 */       cc.put(Attributes.ControlChar.VWERASE, Integer.valueOf(this.c_cc[4]));
/* 266 */       cc.put(Attributes.ControlChar.VKILL, Integer.valueOf(this.c_cc[5]));
/* 267 */       cc.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(this.c_cc[6]));
/* 268 */       cc.put(Attributes.ControlChar.VINTR, Integer.valueOf(this.c_cc[8]));
/* 269 */       cc.put(Attributes.ControlChar.VQUIT, Integer.valueOf(this.c_cc[9]));
/* 270 */       cc.put(Attributes.ControlChar.VSUSP, Integer.valueOf(this.c_cc[10]));
/* 271 */       cc.put(Attributes.ControlChar.VDSUSP, Integer.valueOf(this.c_cc[11]));
/* 272 */       cc.put(Attributes.ControlChar.VSTART, Integer.valueOf(this.c_cc[12]));
/* 273 */       cc.put(Attributes.ControlChar.VSTOP, Integer.valueOf(this.c_cc[13]));
/* 274 */       cc.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(this.c_cc[14]));
/* 275 */       cc.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(this.c_cc[15]));
/* 276 */       cc.put(Attributes.ControlChar.VMIN, Integer.valueOf(this.c_cc[16]));
/* 277 */       cc.put(Attributes.ControlChar.VTIME, Integer.valueOf(this.c_cc[17]));
/* 278 */       cc.put(Attributes.ControlChar.VSTATUS, Integer.valueOf(this.c_cc[18]));
/*     */       
/* 280 */       return attr;
/*     */     }
/*     */     
/*     */     private <T extends Enum<T>> void addFlag(long value, EnumSet<T> flags, T flag, int v) {
/* 284 */       if ((value & v) != 0L)
/* 285 */         flags.add(flag); 
/*     */     }
/*     */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\osx\CLibrary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */