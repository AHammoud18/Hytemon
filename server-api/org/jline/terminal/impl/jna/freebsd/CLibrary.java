/*     */ package org.jline.terminal.impl.jna.freebsd;public interface CLibrary extends Library { public static final int TIOCGWINSZ = 1074295912; public static final int TIOCSWINSZ = -2146929561; public static final int VEOF = 0; public static final int VEOL = 1; public static final int VEOL2 = 2; public static final int VERASE = 3; public static final int VWERASE = 4; public static final int VKILL = 5; public static final int VREPRINT = 6; public static final int VERASE2 = 7; public static final int VINTR = 8; public static final int VQUIT = 9; public static final int VSUSP = 10; public static final int VDSUSP = 11; public static final int VSTART = 12; public static final int VSTOP = 13; public static final int VLNEXT = 14; public static final int VDISCARD = 15; public static final int VMIN = 16; public static final int VTIME = 17; public static final int VSTATUS = 18; public static final int IGNBRK = 1; public static final int BRKINT = 2; public static final int IGNPAR = 4; public static final int PARMRK = 8; public static final int INPCK = 16; public static final int ISTRIP = 32; public static final int INLCR = 64; public static final int IGNCR = 128; public static final int ICRNL = 256; public static final int IXON = 512; public static final int IXOFF = 1024; public static final int IXANY = 2048; public static final int IMAXBEL = 8192; public static final int OPOST = 1; public static final int ONLCR = 2; public static final int TABDLY = 4; public static final int TAB0 = 0; public static final int TAB3 = 4; public static final int ONOEOT = 8; public static final int OCRNL = 16; public static final int ONLRET = 64;
/*     */   public static final int CIGNORE = 1;
/*     */   public static final int CSIZE = 768;
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
/*     */   public static final int PENDIN = 33554432;
/*     */   public static final int NOFLSH = 134217728;
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
/*     */   public static class winsize extends Structure { public short ws_row;
/*     */     public short ws_col;
/*     */     
/*     */     public winsize(Size ws) {
/*  48 */       this.ws_row = (short)ws.getRows();
/*  49 */       this.ws_col = (short)ws.getColumns();
/*     */     } public short ws_xpixel; public short ws_ypixel;
/*     */     public winsize() {}
/*     */     public Size toSize() {
/*  53 */       return new Size(this.ws_col, this.ws_row);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  58 */       return Arrays.asList(new String[] { "ws_row", "ws_col", "ws_xpixel", "ws_ypixel" });
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
/*  73 */     public byte[] c_cc = new byte[20];
/*     */     
/*     */     public int c_ispeed;
/*     */     public int c_ospeed;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  79 */       return Arrays.asList(new String[] { "c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc", "c_ispeed", "c_ospeed" });
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
/*  94 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNBRK), 1, this.c_iflag);
/*  95 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.BRKINT), 2, this.c_iflag);
/*  96 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNPAR), 4, this.c_iflag);
/*  97 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.PARMRK), 8, this.c_iflag);
/*  98 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INPCK), 16, this.c_iflag);
/*  99 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ISTRIP), 32, this.c_iflag);
/* 100 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INLCR), 64, this.c_iflag);
/* 101 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNCR), 128, this.c_iflag);
/* 102 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ICRNL), 256, this.c_iflag);
/* 103 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXON), 512, this.c_iflag);
/* 104 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXOFF), 1024, this.c_iflag);
/* 105 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXANY), 2048, this.c_iflag);
/* 106 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IMAXBEL), 8192, this.c_iflag);
/*     */       
/* 108 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OPOST), 1, this.c_oflag);
/* 109 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLCR), 2, this.c_oflag);
/* 110 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OCRNL), 16, this.c_oflag);
/* 111 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLRET), 64, this.c_oflag);
/* 112 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.TABDLY), 4, this.c_oflag);
/*     */       
/* 114 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS5), 0, this.c_cflag);
/* 115 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS6), 256, this.c_cflag);
/* 116 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS7), 512, this.c_cflag);
/* 117 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS8), 768, this.c_cflag);
/* 118 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CSTOPB), 1024, this.c_cflag);
/* 119 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CREAD), 2048, this.c_cflag);
/* 120 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARENB), 4096, this.c_cflag);
/* 121 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARODD), 8192, this.c_cflag);
/* 122 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.HUPCL), 16384, this.c_cflag);
/* 123 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CLOCAL), 32768, this.c_cflag);
/*     */       
/* 125 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOKE), 1, this.c_lflag);
/* 126 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOE), 2, this.c_lflag);
/* 127 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOK), 4, this.c_lflag);
/* 128 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHO), 8, this.c_lflag);
/* 129 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHONL), 16, this.c_lflag);
/* 130 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 32, this.c_lflag);
/* 131 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 64, this.c_lflag);
/* 132 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ISIG), 128, this.c_lflag);
/* 133 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ICANON), 256, this.c_lflag);
/* 134 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1024, this.c_lflag);
/* 135 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2048, this.c_lflag);
/* 136 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.TOSTOP), 4194304, this.c_lflag);
/* 137 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.FLUSHO), 8388608, this.c_lflag);
/* 138 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.PENDIN), 33554432, this.c_lflag);
/* 139 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOFLSH), 134217728, this.c_lflag);
/*     */       
/* 141 */       this.c_cc[0] = (byte)t.getControlChar(Attributes.ControlChar.VEOF);
/* 142 */       this.c_cc[1] = (byte)t.getControlChar(Attributes.ControlChar.VEOL);
/* 143 */       this.c_cc[2] = (byte)t.getControlChar(Attributes.ControlChar.VEOL2);
/* 144 */       this.c_cc[3] = (byte)t.getControlChar(Attributes.ControlChar.VERASE);
/* 145 */       this.c_cc[4] = (byte)t.getControlChar(Attributes.ControlChar.VWERASE);
/* 146 */       this.c_cc[5] = (byte)t.getControlChar(Attributes.ControlChar.VKILL);
/* 147 */       this.c_cc[6] = (byte)t.getControlChar(Attributes.ControlChar.VREPRINT);
/* 148 */       this.c_cc[8] = (byte)t.getControlChar(Attributes.ControlChar.VINTR);
/* 149 */       this.c_cc[9] = (byte)t.getControlChar(Attributes.ControlChar.VQUIT);
/* 150 */       this.c_cc[10] = (byte)t.getControlChar(Attributes.ControlChar.VSUSP);
/* 151 */       this.c_cc[12] = (byte)t.getControlChar(Attributes.ControlChar.VSTART);
/* 152 */       this.c_cc[13] = (byte)t.getControlChar(Attributes.ControlChar.VSTOP);
/* 153 */       this.c_cc[14] = (byte)t.getControlChar(Attributes.ControlChar.VLNEXT);
/* 154 */       this.c_cc[15] = (byte)t.getControlChar(Attributes.ControlChar.VDISCARD);
/* 155 */       this.c_cc[16] = (byte)t.getControlChar(Attributes.ControlChar.VMIN);
/* 156 */       this.c_cc[17] = (byte)t.getControlChar(Attributes.ControlChar.VTIME);
/*     */     }
/*     */     
/*     */     private int setFlag(boolean flag, int value, int org) {
/* 160 */       return flag ? (org | value) : org;
/*     */     }
/*     */     
/*     */     public Attributes toAttributes() {
/* 164 */       Attributes attr = new Attributes();
/*     */       
/* 166 */       EnumSet<Attributes.InputFlag> iflag = attr.getInputFlags();
/* 167 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 168 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 169 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.BRKINT, 2);
/* 170 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNPAR, 4);
/* 171 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.PARMRK, 8);
/* 172 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.INPCK, 16);
/* 173 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.ISTRIP, 32);
/* 174 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.INLCR, 64);
/* 175 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNCR, 128);
/* 176 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.ICRNL, 256);
/* 177 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IXON, 512);
/* 178 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IXOFF, 1024);
/* 179 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IXANY, 2048);
/* 180 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IMAXBEL, 8192);
/*     */       
/* 182 */       EnumSet<Attributes.OutputFlag> oflag = attr.getOutputFlags();
/* 183 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OPOST, 1);
/* 184 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.ONLCR, 2);
/* 185 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OCRNL, 16);
/* 186 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.ONLRET, 64);
/* 187 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.TABDLY, 4);
/*     */       
/* 189 */       EnumSet<Attributes.ControlFlag> cflag = attr.getControlFlags();
/* 190 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS5, 0);
/* 191 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS6, 256);
/* 192 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS7, 512);
/* 193 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS8, 768);
/* 194 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CSTOPB, 1024);
/* 195 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CREAD, 2048);
/* 196 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.PARENB, 4096);
/* 197 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.PARODD, 8192);
/* 198 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.HUPCL, 16384);
/* 199 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CLOCAL, 32768);
/*     */       
/* 201 */       EnumSet<Attributes.LocalFlag> lflag = attr.getLocalFlags();
/* 202 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOKE, 1);
/* 203 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOE, 2);
/* 204 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOK, 4);
/* 205 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHO, 8);
/* 206 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHONL, 16);
/* 207 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOPRT, 32);
/* 208 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOCTL, 64);
/* 209 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ISIG, 128);
/* 210 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ICANON, 256);
/* 211 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.IEXTEN, 1024);
/* 212 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.EXTPROC, 2048);
/* 213 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.TOSTOP, 4194304);
/* 214 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.FLUSHO, 8388608);
/* 215 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.PENDIN, 33554432);
/* 216 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.NOFLSH, 134217728);
/*     */       
/* 218 */       EnumMap<Attributes.ControlChar, Integer> cc = attr.getControlChars();
/* 219 */       cc.put(Attributes.ControlChar.VEOF, Integer.valueOf(this.c_cc[0]));
/* 220 */       cc.put(Attributes.ControlChar.VEOL, Integer.valueOf(this.c_cc[1]));
/* 221 */       cc.put(Attributes.ControlChar.VEOL2, Integer.valueOf(this.c_cc[2]));
/* 222 */       cc.put(Attributes.ControlChar.VERASE, Integer.valueOf(this.c_cc[3]));
/* 223 */       cc.put(Attributes.ControlChar.VWERASE, Integer.valueOf(this.c_cc[4]));
/* 224 */       cc.put(Attributes.ControlChar.VKILL, Integer.valueOf(this.c_cc[5]));
/* 225 */       cc.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(this.c_cc[6]));
/* 226 */       cc.put(Attributes.ControlChar.VINTR, Integer.valueOf(this.c_cc[8]));
/* 227 */       cc.put(Attributes.ControlChar.VQUIT, Integer.valueOf(this.c_cc[9]));
/* 228 */       cc.put(Attributes.ControlChar.VSUSP, Integer.valueOf(this.c_cc[10]));
/* 229 */       cc.put(Attributes.ControlChar.VSTART, Integer.valueOf(this.c_cc[12]));
/* 230 */       cc.put(Attributes.ControlChar.VSTOP, Integer.valueOf(this.c_cc[13]));
/* 231 */       cc.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(this.c_cc[14]));
/* 232 */       cc.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(this.c_cc[15]));
/* 233 */       cc.put(Attributes.ControlChar.VMIN, Integer.valueOf(this.c_cc[16]));
/* 234 */       cc.put(Attributes.ControlChar.VTIME, Integer.valueOf(this.c_cc[17]));
/*     */       
/* 236 */       return attr;
/*     */     }
/*     */     
/*     */     private <T extends Enum<T>> void addFlag(int value, EnumSet<T> flags, T flag, int v) {
/* 240 */       if ((value & v) != 0)
/* 241 */         flags.add(flag); 
/*     */     }
/*     */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\freebsd\CLibrary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */