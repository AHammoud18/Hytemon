/*     */ package org.jline.terminal.impl.jna.linux;
/*     */ 
/*     */ import com.sun.jna.LastErrorException;
/*     */ import com.sun.jna.Library;
/*     */ import com.sun.jna.Platform;
/*     */ import com.sun.jna.Structure;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface CLibrary
/*     */   extends Library
/*     */ {
/*     */   public static class winsize
/*     */     extends Structure
/*     */   {
/*     */     public short ws_row;
/*     */     public short ws_col;
/*     */     public short ws_xpixel;
/*     */     public short ws_ypixel;
/*     */     
/*     */     public winsize() {}
/*     */     
/*     */     public winsize(Size ws) {
/*  49 */       this.ws_row = (short)ws.getRows();
/*  50 */       this.ws_col = (short)ws.getColumns();
/*     */     }
/*     */     
/*     */     public Size toSize() {
/*  54 */       return new Size(this.ws_col, this.ws_row);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  59 */       return Arrays.asList(new String[] { "ws_row", "ws_col", "ws_xpixel", "ws_ypixel" });
/*     */     }
/*     */   }
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
/*     */     public byte c_line;
/*  75 */     public byte[] c_cc = new byte[32];
/*     */     
/*     */     public int c_ispeed;
/*     */     public int c_ospeed;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  81 */       return Arrays.asList(new String[] { "c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_line", "c_cc", "c_ispeed", "c_ospeed" });
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
/*     */     
/*     */     public termios(Attributes t) {
/*  97 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNBRK), 1, this.c_iflag);
/*  98 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.BRKINT), 2, this.c_iflag);
/*  99 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNPAR), 4, this.c_iflag);
/* 100 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.PARMRK), 8, this.c_iflag);
/* 101 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INPCK), 16, this.c_iflag);
/* 102 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ISTRIP), 32, this.c_iflag);
/* 103 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INLCR), 64, this.c_iflag);
/* 104 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNCR), 128, this.c_iflag);
/* 105 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ICRNL), 256, this.c_iflag);
/* 106 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXON), 1024, this.c_iflag);
/* 107 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXOFF), 4096, this.c_iflag);
/* 108 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXANY), 2048, this.c_iflag);
/* 109 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IMAXBEL), 8192, this.c_iflag);
/* 110 */       this.c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IUTF8), 16384, this.c_iflag);
/*     */       
/* 112 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OPOST), 1, this.c_oflag);
/* 113 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLCR), 4, this.c_oflag);
/* 114 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OCRNL), 8, this.c_oflag);
/* 115 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOCR), 16, this.c_oflag);
/* 116 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLRET), 32, this.c_oflag);
/* 117 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFILL), 64, this.c_oflag);
/* 118 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.NLDLY), 256, this.c_oflag);
/* 119 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.TABDLY), 6144, this.c_oflag);
/* 120 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.CRDLY), 1536, this.c_oflag);
/* 121 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.FFDLY), 32768, this.c_oflag);
/* 122 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.BSDLY), 8192, this.c_oflag);
/* 123 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.VTDLY), 16384, this.c_oflag);
/* 124 */       this.c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFDEL), 128, this.c_oflag);
/*     */       
/* 126 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS5), 0, this.c_cflag);
/* 127 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS6), 16, this.c_cflag);
/* 128 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS7), 32, this.c_cflag);
/* 129 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS8), 48, this.c_cflag);
/* 130 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CSTOPB), 64, this.c_cflag);
/* 131 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CREAD), 128, this.c_cflag);
/* 132 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARENB), 256, this.c_cflag);
/* 133 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARODD), 512, this.c_cflag);
/* 134 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.HUPCL), 1024, this.c_cflag);
/* 135 */       this.c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CLOCAL), 2048, this.c_cflag);
/*     */       
/* 137 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOKE), 2048, this.c_lflag);
/* 138 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOE), 16, this.c_lflag);
/* 139 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOK), 32, this.c_lflag);
/* 140 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHO), 8, this.c_lflag);
/* 141 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHONL), 64, this.c_lflag);
/* 142 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 1024, this.c_lflag);
/* 143 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 512, this.c_lflag);
/* 144 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ISIG), 1, this.c_lflag);
/* 145 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ICANON), 2, this.c_lflag);
/* 146 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.EXTPROC), 65536, this.c_lflag);
/* 147 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.TOSTOP), 256, this.c_lflag);
/* 148 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.FLUSHO), 4096, this.c_lflag);
/* 149 */       this.c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOFLSH), 128, this.c_lflag);
/*     */       
/* 151 */       this.c_cc[4] = (byte)t.getControlChar(Attributes.ControlChar.VEOF);
/* 152 */       this.c_cc[11] = (byte)t.getControlChar(Attributes.ControlChar.VEOL);
/* 153 */       this.c_cc[16] = (byte)t.getControlChar(Attributes.ControlChar.VEOL2);
/* 154 */       this.c_cc[2] = (byte)t.getControlChar(Attributes.ControlChar.VERASE);
/* 155 */       this.c_cc[14] = (byte)t.getControlChar(Attributes.ControlChar.VWERASE);
/* 156 */       this.c_cc[3] = (byte)t.getControlChar(Attributes.ControlChar.VKILL);
/* 157 */       this.c_cc[12] = (byte)t.getControlChar(Attributes.ControlChar.VREPRINT);
/* 158 */       this.c_cc[0] = (byte)t.getControlChar(Attributes.ControlChar.VINTR);
/* 159 */       this.c_cc[1] = (byte)t.getControlChar(Attributes.ControlChar.VQUIT);
/* 160 */       this.c_cc[10] = (byte)t.getControlChar(Attributes.ControlChar.VSUSP);
/* 161 */       this.c_cc[8] = (byte)t.getControlChar(Attributes.ControlChar.VSTART);
/* 162 */       this.c_cc[9] = (byte)t.getControlChar(Attributes.ControlChar.VSTOP);
/* 163 */       this.c_cc[15] = (byte)t.getControlChar(Attributes.ControlChar.VLNEXT);
/* 164 */       this.c_cc[13] = (byte)t.getControlChar(Attributes.ControlChar.VDISCARD);
/* 165 */       this.c_cc[6] = (byte)t.getControlChar(Attributes.ControlChar.VMIN);
/* 166 */       this.c_cc[5] = (byte)t.getControlChar(Attributes.ControlChar.VTIME);
/*     */     }
/*     */     
/*     */     private int setFlag(boolean flag, int value, int org) {
/* 170 */       return flag ? (org | value) : org;
/*     */     }
/*     */     
/*     */     public Attributes toAttributes() {
/* 174 */       Attributes attr = new Attributes();
/*     */       
/* 176 */       EnumSet<Attributes.InputFlag> iflag = attr.getInputFlags();
/* 177 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 178 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNBRK, 1);
/* 179 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.BRKINT, 2);
/* 180 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNPAR, 4);
/* 181 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.PARMRK, 8);
/* 182 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.INPCK, 16);
/* 183 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.ISTRIP, 32);
/* 184 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.INLCR, 64);
/* 185 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IGNCR, 128);
/* 186 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.ICRNL, 256);
/* 187 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IXON, 1024);
/* 188 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IXOFF, 4096);
/* 189 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IXANY, 2048);
/* 190 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IMAXBEL, 8192);
/* 191 */       addFlag(this.c_iflag, iflag, Attributes.InputFlag.IUTF8, 16384);
/*     */       
/* 193 */       EnumSet<Attributes.OutputFlag> oflag = attr.getOutputFlags();
/* 194 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OPOST, 1);
/* 195 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.ONLCR, 4);
/* 196 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OCRNL, 8);
/* 197 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.ONOCR, 16);
/* 198 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.ONLRET, 32);
/* 199 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OFILL, 64);
/* 200 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.NLDLY, 256);
/* 201 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.TABDLY, 6144);
/* 202 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.CRDLY, 1536);
/* 203 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.FFDLY, 32768);
/* 204 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.BSDLY, 8192);
/* 205 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.VTDLY, 16384);
/* 206 */       addFlag(this.c_oflag, oflag, Attributes.OutputFlag.OFDEL, 128);
/*     */       
/* 208 */       EnumSet<Attributes.ControlFlag> cflag = attr.getControlFlags();
/* 209 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS5, 0);
/* 210 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS6, 16);
/* 211 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS7, 32);
/* 212 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CS8, 48);
/* 213 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CSTOPB, 64);
/* 214 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CREAD, 128);
/* 215 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.PARENB, 256);
/* 216 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.PARODD, 512);
/* 217 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.HUPCL, 1024);
/* 218 */       addFlag(this.c_cflag, cflag, Attributes.ControlFlag.CLOCAL, 2048);
/*     */       
/* 220 */       EnumSet<Attributes.LocalFlag> lflag = attr.getLocalFlags();
/* 221 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOKE, 2048);
/* 222 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOE, 16);
/* 223 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOK, 32);
/* 224 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHO, 8);
/* 225 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHONL, 64);
/* 226 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOPRT, 1024);
/* 227 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ECHOCTL, 512);
/* 228 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ISIG, 1);
/* 229 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.ICANON, 2);
/* 230 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.EXTPROC, 65536);
/* 231 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.TOSTOP, 256);
/* 232 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.FLUSHO, 4096);
/* 233 */       addFlag(this.c_lflag, lflag, Attributes.LocalFlag.NOFLSH, 128);
/*     */       
/* 235 */       EnumMap<Attributes.ControlChar, Integer> cc = attr.getControlChars();
/* 236 */       cc.put(Attributes.ControlChar.VEOF, Integer.valueOf(this.c_cc[4]));
/* 237 */       cc.put(Attributes.ControlChar.VEOL, Integer.valueOf(this.c_cc[11]));
/* 238 */       cc.put(Attributes.ControlChar.VEOL2, Integer.valueOf(this.c_cc[16]));
/* 239 */       cc.put(Attributes.ControlChar.VERASE, Integer.valueOf(this.c_cc[2]));
/* 240 */       cc.put(Attributes.ControlChar.VWERASE, Integer.valueOf(this.c_cc[14]));
/* 241 */       cc.put(Attributes.ControlChar.VKILL, Integer.valueOf(this.c_cc[3]));
/* 242 */       cc.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(this.c_cc[12]));
/* 243 */       cc.put(Attributes.ControlChar.VINTR, Integer.valueOf(this.c_cc[0]));
/* 244 */       cc.put(Attributes.ControlChar.VQUIT, Integer.valueOf(this.c_cc[1]));
/* 245 */       cc.put(Attributes.ControlChar.VSUSP, Integer.valueOf(this.c_cc[10]));
/* 246 */       cc.put(Attributes.ControlChar.VSTART, Integer.valueOf(this.c_cc[8]));
/* 247 */       cc.put(Attributes.ControlChar.VSTOP, Integer.valueOf(this.c_cc[9]));
/* 248 */       cc.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(this.c_cc[15]));
/* 249 */       cc.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(this.c_cc[13]));
/* 250 */       cc.put(Attributes.ControlChar.VMIN, Integer.valueOf(this.c_cc[6]));
/* 251 */       cc.put(Attributes.ControlChar.VTIME, Integer.valueOf(this.c_cc[5]));
/*     */       
/* 253 */       return attr;
/*     */     }
/*     */     
/*     */     private <T extends Enum<T>> void addFlag(int value, EnumSet<T> flags, T flag, int v) {
/* 257 */       if ((value & v) != 0) {
/* 258 */         flags.add(flag);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 265 */   public static final int TIOCGWINSZ = (Platform.isMIPS() || Platform.isPPC() || Platform.isSPARC()) ? 1074295912 : 21523;
/* 266 */   public static final int TIOCSWINSZ = (Platform.isMIPS() || Platform.isPPC() || Platform.isSPARC()) ? -2146929561 : 21524;
/*     */   public static final int VINTR = 0;
/*     */   public static final int VQUIT = 1;
/*     */   public static final int VERASE = 2;
/*     */   public static final int VKILL = 3;
/*     */   public static final int VEOF = 4;
/*     */   public static final int VTIME = 5;
/*     */   public static final int VMIN = 6;
/*     */   public static final int VSWTC = 7;
/*     */   public static final int VSTART = 8;
/*     */   public static final int VSTOP = 9;
/*     */   public static final int VSUSP = 10;
/*     */   public static final int VEOL = 11;
/*     */   public static final int VREPRINT = 12;
/*     */   public static final int VDISCARD = 13;
/*     */   public static final int VWERASE = 14;
/*     */   public static final int VLNEXT = 15;
/*     */   public static final int VEOL2 = 16;
/*     */   public static final int IGNBRK = 1;
/*     */   public static final int BRKINT = 2;
/*     */   public static final int IGNPAR = 4;
/*     */   public static final int PARMRK = 8;
/*     */   public static final int INPCK = 16;
/*     */   public static final int ISTRIP = 32;
/*     */   public static final int INLCR = 64;
/*     */   public static final int IGNCR = 128;
/*     */   public static final int ICRNL = 256;
/*     */   public static final int IUCLC = 512;
/*     */   public static final int IXON = 1024;
/*     */   public static final int IXANY = 2048;
/*     */   public static final int IXOFF = 4096;
/*     */   public static final int IMAXBEL = 8192;
/*     */   public static final int IUTF8 = 16384;
/*     */   public static final int OPOST = 1;
/*     */   public static final int OLCUC = 2;
/*     */   public static final int ONLCR = 4;
/*     */   public static final int OCRNL = 8;
/*     */   public static final int ONOCR = 16;
/*     */   public static final int ONLRET = 32;
/*     */   public static final int OFILL = 64;
/*     */   public static final int OFDEL = 128;
/*     */   public static final int NLDLY = 256;
/*     */   public static final int NL0 = 0;
/*     */   public static final int NL1 = 256;
/*     */   public static final int CRDLY = 1536;
/*     */   public static final int CR0 = 0;
/*     */   public static final int CR1 = 512;
/*     */   public static final int CR2 = 1024;
/*     */   public static final int CR3 = 1536;
/*     */   public static final int TABDLY = 6144;
/*     */   public static final int TAB0 = 0;
/*     */   public static final int TAB1 = 2048;
/*     */   public static final int TAB2 = 4096;
/*     */   public static final int TAB3 = 6144;
/*     */   public static final int XTABS = 6144;
/*     */   public static final int BSDLY = 8192;
/*     */   public static final int BS0 = 0;
/*     */   public static final int BS1 = 8192;
/*     */   public static final int VTDLY = 16384;
/*     */   public static final int VT0 = 0;
/*     */   public static final int VT1 = 16384;
/*     */   public static final int FFDLY = 32768;
/*     */   public static final int FF0 = 0;
/*     */   public static final int FF1 = 32768;
/*     */   public static final int CBAUD = 4111;
/*     */   public static final int B0 = 0;
/*     */   public static final int B50 = 1;
/*     */   public static final int B75 = 2;
/*     */   public static final int B110 = 3;
/*     */   public static final int B134 = 4;
/*     */   public static final int B150 = 5;
/*     */   public static final int B200 = 6;
/*     */   public static final int B300 = 7;
/*     */   public static final int B600 = 8;
/*     */   public static final int B1200 = 9;
/*     */   public static final int B1800 = 10;
/*     */   public static final int B2400 = 11;
/*     */   public static final int B4800 = 12;
/*     */   public static final int B9600 = 13;
/*     */   public static final int B19200 = 14;
/*     */   public static final int B38400 = 15;
/*     */   public static final int EXTA = 14;
/*     */   public static final int EXTB = 15;
/*     */   public static final int CSIZE = 48;
/*     */   public static final int CS5 = 0;
/*     */   public static final int CS6 = 16;
/*     */   public static final int CS7 = 32;
/*     */   public static final int CS8 = 48;
/*     */   public static final int CSTOPB = 64;
/*     */   public static final int CREAD = 128;
/*     */   public static final int PARENB = 256;
/*     */   public static final int PARODD = 512;
/*     */   public static final int HUPCL = 1024;
/*     */   public static final int CLOCAL = 2048;
/*     */   public static final int ISIG = 1;
/*     */   public static final int ICANON = 2;
/*     */   public static final int XCASE = 4;
/*     */   public static final int ECHO = 8;
/*     */   public static final int ECHOE = 16;
/*     */   public static final int ECHOK = 32;
/*     */   public static final int ECHONL = 64;
/*     */   public static final int NOFLSH = 128;
/*     */   public static final int TOSTOP = 256;
/*     */   public static final int ECHOCTL = 512;
/*     */   public static final int ECHOPRT = 1024;
/*     */   public static final int ECHOKE = 2048;
/*     */   public static final int FLUSHO = 4096;
/*     */   public static final int PENDIN = 8192;
/*     */   public static final int IEXTEN = 32768;
/*     */   public static final int EXTPROC = 65536;
/*     */   public static final int TCSANOW = 0;
/*     */   public static final int TCSADRAIN = 1;
/*     */   public static final int TCSAFLUSH = 2;
/*     */   
/*     */   void tcgetattr(int paramInt, termios paramtermios) throws LastErrorException;
/*     */   
/*     */   void tcsetattr(int paramInt1, int paramInt2, termios paramtermios) throws LastErrorException;
/*     */   
/*     */   void ioctl(int paramInt1, int paramInt2, winsize paramwinsize) throws LastErrorException;
/*     */   
/*     */   int isatty(int paramInt);
/*     */   
/*     */   void ttyname_r(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws LastErrorException;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\linux\CLibrary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */