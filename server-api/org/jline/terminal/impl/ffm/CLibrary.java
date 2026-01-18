/*      */ package org.jline.terminal.impl.ffm;
/*      */ import java.lang.foreign.MemoryLayout;
/*      */ import java.lang.foreign.ValueLayout;
/*      */ import java.util.EnumSet;
/*      */ import org.jline.terminal.Attributes;
/*      */ 
/*      */ class CLibrary {
/*      */   static final MethodHandle ioctl;
/*      */   static final MethodHandle isatty;
/*      */   static final MethodHandle openpty;
/*      */   static final MethodHandle tcsetattr;
/*      */   static final MethodHandle tcgetattr;
/*      */   static final MethodHandle ttyname_r;
/*      */   static LinkageError openptyError;
/*      */   private static final int TIOCGWINSZ;
/*      */   private static final int TIOCSWINSZ;
/*      */   private static final int TCSANOW;
/*      */   private static int TCSADRAIN;
/*      */   private static int TCSAFLUSH;
/*      */   private static final int VEOF;
/*      */   private static final int VEOL;
/*      */   private static final int VEOL2;
/*      */   private static final int VERASE;
/*      */   private static final int VWERASE;
/*      */   private static final int VKILL;
/*      */   private static final int VREPRINT;
/*      */   private static final int VERASE2;
/*      */   private static final int VINTR;
/*      */   private static final int VQUIT;
/*      */   private static final int VSUSP;
/*      */   private static final int VDSUSP;
/*      */   private static final int VSTART;
/*      */   private static final int VSTOP;
/*      */   private static final int VLNEXT;
/*      */   private static final int VDISCARD;
/*      */   private static final int VMIN;
/*      */   private static final int VSWTC;
/*      */   private static final int VTIME;
/*      */   private static final int VSTATUS;
/*      */   private static final int IGNBRK;
/*   41 */   private static final Logger logger = Logger.getLogger("org.jline"); private static final int BRKINT; private static final int IGNPAR; private static final int PARMRK; private static final int INPCK; private static final int ISTRIP; private static final int INLCR; private static final int IGNCR; private static final int ICRNL; private static int IUCLC; private static final int IXON; private static final int IXOFF; private static final int IXANY; private static final int IMAXBEL; private static int IUTF8; private static final int OPOST; private static int OLCUC; private static final int ONLCR; private static int OXTABS; private static int NLDLY; private static int NL0; private static int NL1; private static final int TABDLY; private static int TAB0; private static int TAB1; private static int TAB2; private static int TAB3; private static int CRDLY; private static int CR0; private static int CR1; private static int CR2; private static int CR3; private static int FFDLY; private static int FF0; private static int FF1; private static int XTABS; private static int BSDLY; private static int BS0; private static int BS1; private static int VTDLY; private static int VT0; private static int VT1; private static int CBAUD; private static int B0; private static int B50; private static int B75; private static int B110; private static int B134; private static int B150; private static int B200; private static int B300; private static int B600; private static int B1200; private static int B1800; private static int B2400; private static int B4800; private static int B9600; private static int B19200; private static int B38400; private static int EXTA; private static int EXTB; private static int OFDEL; private static int ONOEOT; private static final int OCRNL; private static int ONOCR; private static final int ONLRET; private static int OFILL; private static int CIGNORE; private static int CSIZE; private static final int CS5; private static final int CS6; private static final int CS7; private static final int CS8; private static final int CSTOPB; private static final int CREAD; private static final int PARENB; private static final int PARODD; private static final int HUPCL; private static final int CLOCAL; private static int CCTS_OFLOW; private static int CRTS_IFLOW; private static int CDTR_IFLOW; private static int CDSR_OFLOW; private static int CCAR_OFLOW; private static final int ECHOKE; private static final int ECHOE; private static final int ECHOK; private static final int ECHO; private static final int ECHONL; private static final int ECHOPRT; private static final int ECHOCTL; private static final int ISIG; private static final int ICANON; private static int XCASE;
/*      */   private static int ALTWERASE;
/*      */   private static final int IEXTEN;
/*      */   private static final int EXTPROC;
/*      */   private static final int TOSTOP;
/*      */   private static final int FLUSHO;
/*      */   private static int NOKERNINFO;
/*      */   private static final int PENDIN;
/*      */   private static final int NOFLSH;
/*      */   
/*   51 */   static class winsize { static final GroupLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { ValueLayout.JAVA_SHORT
/*   52 */           .withName("ws_row"), ValueLayout.JAVA_SHORT
/*   53 */           .withName("ws_col"), ValueLayout.JAVA_SHORT, ValueLayout.JAVA_SHORT });
/*      */ 
/*      */ 
/*      */     
/*   57 */     private static final VarHandle ws_col = FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("ws_col") }); private static final VarHandle ws_row = FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("ws_row") });
/*      */     
/*      */     static {
/*      */     
/*      */     }
/*      */     
/*   63 */     private final MemorySegment seg = Arena.ofAuto().allocate(LAYOUT);
/*      */ 
/*      */     
/*      */     winsize(short ws_col, short ws_row) {
/*   67 */       this();
/*   68 */       ws_col(ws_col);
/*   69 */       ws_row(ws_row);
/*      */     }
/*      */     
/*      */     MemorySegment segment() {
/*   73 */       return this.seg;
/*      */     }
/*      */     
/*      */     short ws_col() {
/*   77 */       return ws_col.get(this.seg);
/*      */     }
/*      */     
/*      */     void ws_col(short col) {
/*   81 */       ws_col.set(this.seg, col);
/*      */     }
/*      */     
/*      */     short ws_row() {
/*   85 */       return ws_row.get(this.seg);
/*      */     }
/*      */     
/*      */     void ws_row(short row) {
/*   89 */       ws_row.set(this.seg, row);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     winsize() {} }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class termios
/*      */   {
/*      */     static final GroupLayout LAYOUT;
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*  107 */       if (OSUtils.IS_OSX) {
/*  108 */         LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { ValueLayout.JAVA_LONG
/*  109 */               .withName("c_iflag"), ValueLayout.JAVA_LONG
/*  110 */               .withName("c_oflag"), ValueLayout.JAVA_LONG
/*  111 */               .withName("c_cflag"), ValueLayout.JAVA_LONG
/*  112 */               .withName("c_lflag"), 
/*  113 */               MemoryLayout.sequenceLayout(32L, ValueLayout.JAVA_BYTE).withName("c_cc"), ValueLayout.JAVA_LONG
/*  114 */               .withName("c_ispeed"), ValueLayout.JAVA_LONG
/*  115 */               .withName("c_ospeed") });
/*  116 */       } else if (OSUtils.IS_LINUX) {
/*  117 */         LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { ValueLayout.JAVA_INT
/*  118 */               .withName("c_iflag"), ValueLayout.JAVA_INT
/*  119 */               .withName("c_oflag"), ValueLayout.JAVA_INT
/*  120 */               .withName("c_cflag"), ValueLayout.JAVA_INT
/*  121 */               .withName("c_lflag"), ValueLayout.JAVA_BYTE
/*  122 */               .withName("c_line"), 
/*  123 */               MemoryLayout.sequenceLayout(32L, ValueLayout.JAVA_BYTE).withName("c_cc"), 
/*  124 */               MemoryLayout.paddingLayout(3L), ValueLayout.JAVA_INT
/*  125 */               .withName("c_ispeed"), ValueLayout.JAVA_INT
/*  126 */               .withName("c_ospeed") });
/*      */       } else {
/*  128 */         throw new IllegalStateException("Unsupported system!");
/*      */       } 
/*  130 */     } private static final VarHandle c_iflag = adjust2LinuxHandle(
/*  131 */         FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_iflag") }));
/*  132 */     private static final VarHandle c_oflag = adjust2LinuxHandle(
/*  133 */         FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_oflag") }));
/*  134 */     private static final VarHandle c_cflag = adjust2LinuxHandle(
/*  135 */         FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_cflag") }));
/*  136 */     private static final VarHandle c_lflag = adjust2LinuxHandle(
/*  137 */         FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_lflag") }));
/*  138 */     private static final long c_cc_offset = LAYOUT.byteOffset(new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_cc") });
/*  139 */     private static final VarHandle c_ispeed = adjust2LinuxHandle(
/*  140 */         FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_ispeed") }));
/*  141 */     private static final VarHandle c_ospeed = adjust2LinuxHandle(
/*  142 */         FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_ospeed") }));
/*      */     private final MemorySegment seg;
/*      */     
/*      */     private static VarHandle adjust2LinuxHandle(VarHandle v) {
/*  146 */       if (OSUtils.IS_LINUX) {
/*  147 */         MethodHandle id = MethodHandles.identity(int.class);
/*  148 */         v = MethodHandles.filterValue(v, 
/*      */             
/*  150 */             MethodHandles.explicitCastArguments(id, MethodType.methodType(int.class, long.class)), 
/*  151 */             MethodHandles.explicitCastArguments(id, MethodType.methodType(long.class, int.class)));
/*      */       } 
/*      */       
/*  154 */       return v;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     termios() {
/*  160 */       this.seg = Arena.ofAuto().allocate(LAYOUT);
/*      */     }
/*      */     
/*      */     termios(Attributes t) {
/*  164 */       this();
/*      */       
/*  166 */       long c_iflag = 0L;
/*  167 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNBRK), CLibrary.IGNBRK, c_iflag);
/*  168 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.BRKINT), CLibrary.BRKINT, c_iflag);
/*  169 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNPAR), CLibrary.IGNPAR, c_iflag);
/*  170 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.PARMRK), CLibrary.PARMRK, c_iflag);
/*  171 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INPCK), CLibrary.INPCK, c_iflag);
/*  172 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ISTRIP), CLibrary.ISTRIP, c_iflag);
/*  173 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INLCR), CLibrary.INLCR, c_iflag);
/*  174 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNCR), CLibrary.IGNCR, c_iflag);
/*  175 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ICRNL), CLibrary.ICRNL, c_iflag);
/*  176 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXON), CLibrary.IXON, c_iflag);
/*  177 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXOFF), CLibrary.IXOFF, c_iflag);
/*  178 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXANY), CLibrary.IXANY, c_iflag);
/*  179 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IMAXBEL), CLibrary.IMAXBEL, c_iflag);
/*  180 */       c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IUTF8), CLibrary.IUTF8, c_iflag);
/*  181 */       c_iflag(c_iflag);
/*      */       
/*  183 */       long c_oflag = 0L;
/*  184 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OPOST), CLibrary.OPOST, c_oflag);
/*  185 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLCR), CLibrary.ONLCR, c_oflag);
/*  186 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OXTABS), CLibrary.OXTABS, c_oflag);
/*  187 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOEOT), CLibrary.ONOEOT, c_oflag);
/*  188 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OCRNL), CLibrary.OCRNL, c_oflag);
/*  189 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOCR), CLibrary.ONOCR, c_oflag);
/*  190 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLRET), CLibrary.ONLRET, c_oflag);
/*  191 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFILL), CLibrary.OFILL, c_oflag);
/*  192 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.NLDLY), CLibrary.NLDLY, c_oflag);
/*  193 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.TABDLY), CLibrary.TABDLY, c_oflag);
/*  194 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.CRDLY), CLibrary.CRDLY, c_oflag);
/*  195 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.FFDLY), CLibrary.FFDLY, c_oflag);
/*  196 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.BSDLY), CLibrary.BSDLY, c_oflag);
/*  197 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.VTDLY), CLibrary.VTDLY, c_oflag);
/*  198 */       c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFDEL), CLibrary.OFDEL, c_oflag);
/*  199 */       c_oflag(c_oflag);
/*      */       
/*  201 */       long c_cflag = 0L;
/*  202 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CIGNORE), CLibrary.CIGNORE, c_cflag);
/*  203 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS5), CLibrary.CS5, c_cflag);
/*  204 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS6), CLibrary.CS6, c_cflag);
/*  205 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS7), CLibrary.CS7, c_cflag);
/*  206 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS8), CLibrary.CS8, c_cflag);
/*  207 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CSTOPB), CLibrary.CSTOPB, c_cflag);
/*  208 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CREAD), CLibrary.CREAD, c_cflag);
/*  209 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARENB), CLibrary.PARENB, c_cflag);
/*  210 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARODD), CLibrary.PARODD, c_cflag);
/*  211 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.HUPCL), CLibrary.HUPCL, c_cflag);
/*  212 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CLOCAL), CLibrary.CLOCAL, c_cflag);
/*  213 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CCTS_OFLOW), CLibrary.CCTS_OFLOW, c_cflag);
/*  214 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CRTS_IFLOW), CLibrary.CRTS_IFLOW, c_cflag);
/*  215 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CDTR_IFLOW), CLibrary.CDTR_IFLOW, c_cflag);
/*  216 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CDSR_OFLOW), CLibrary.CDSR_OFLOW, c_cflag);
/*  217 */       c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CCAR_OFLOW), CLibrary.CCAR_OFLOW, c_cflag);
/*  218 */       c_cflag(c_cflag);
/*      */       
/*  220 */       long c_lflag = 0L;
/*  221 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOKE), CLibrary.ECHOKE, c_lflag);
/*  222 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOE), CLibrary.ECHOE, c_lflag);
/*  223 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOK), CLibrary.ECHOK, c_lflag);
/*  224 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHO), CLibrary.ECHO, c_lflag);
/*  225 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHONL), CLibrary.ECHONL, c_lflag);
/*  226 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOPRT), CLibrary.ECHOPRT, c_lflag);
/*  227 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOCTL), CLibrary.ECHOCTL, c_lflag);
/*  228 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ISIG), CLibrary.ISIG, c_lflag);
/*  229 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ICANON), CLibrary.ICANON, c_lflag);
/*  230 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ALTWERASE), CLibrary.ALTWERASE, c_lflag);
/*  231 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.IEXTEN), CLibrary.IEXTEN, c_lflag);
/*  232 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.EXTPROC), CLibrary.EXTPROC, c_lflag);
/*  233 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.TOSTOP), CLibrary.TOSTOP, c_lflag);
/*  234 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.FLUSHO), CLibrary.FLUSHO, c_lflag);
/*  235 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOKERNINFO), CLibrary.NOKERNINFO, c_lflag);
/*  236 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.PENDIN), CLibrary.PENDIN, c_lflag);
/*  237 */       c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOFLSH), CLibrary.NOFLSH, c_lflag);
/*  238 */       c_lflag(c_lflag);
/*      */       
/*  240 */       byte[] c_cc = new byte[20];
/*  241 */       c_cc[CLibrary.VEOF] = (byte)t.getControlChar(Attributes.ControlChar.VEOF);
/*  242 */       c_cc[CLibrary.VEOL] = (byte)t.getControlChar(Attributes.ControlChar.VEOL);
/*  243 */       c_cc[CLibrary.VEOL2] = (byte)t.getControlChar(Attributes.ControlChar.VEOL2);
/*  244 */       c_cc[CLibrary.VERASE] = (byte)t.getControlChar(Attributes.ControlChar.VERASE);
/*  245 */       c_cc[CLibrary.VWERASE] = (byte)t.getControlChar(Attributes.ControlChar.VWERASE);
/*  246 */       c_cc[CLibrary.VKILL] = (byte)t.getControlChar(Attributes.ControlChar.VKILL);
/*  247 */       c_cc[CLibrary.VREPRINT] = (byte)t.getControlChar(Attributes.ControlChar.VREPRINT);
/*  248 */       c_cc[CLibrary.VINTR] = (byte)t.getControlChar(Attributes.ControlChar.VINTR);
/*  249 */       c_cc[CLibrary.VQUIT] = (byte)t.getControlChar(Attributes.ControlChar.VQUIT);
/*  250 */       c_cc[CLibrary.VSUSP] = (byte)t.getControlChar(Attributes.ControlChar.VSUSP);
/*  251 */       if (CLibrary.VDSUSP != -1) {
/*  252 */         c_cc[CLibrary.VDSUSP] = (byte)t.getControlChar(Attributes.ControlChar.VDSUSP);
/*      */       }
/*  254 */       c_cc[CLibrary.VSTART] = (byte)t.getControlChar(Attributes.ControlChar.VSTART);
/*  255 */       c_cc[CLibrary.VSTOP] = (byte)t.getControlChar(Attributes.ControlChar.VSTOP);
/*  256 */       c_cc[CLibrary.VLNEXT] = (byte)t.getControlChar(Attributes.ControlChar.VLNEXT);
/*  257 */       c_cc[CLibrary.VDISCARD] = (byte)t.getControlChar(Attributes.ControlChar.VDISCARD);
/*  258 */       c_cc[CLibrary.VMIN] = (byte)t.getControlChar(Attributes.ControlChar.VMIN);
/*  259 */       c_cc[CLibrary.VTIME] = (byte)t.getControlChar(Attributes.ControlChar.VTIME);
/*  260 */       if (CLibrary.VSTATUS != -1) {
/*  261 */         c_cc[CLibrary.VSTATUS] = (byte)t.getControlChar(Attributes.ControlChar.VSTATUS);
/*      */       }
/*  263 */       c_cc().copyFrom(MemorySegment.ofArray(c_cc));
/*      */     }
/*      */     
/*      */     MemorySegment segment() {
/*  267 */       return this.seg;
/*      */     }
/*      */     
/*      */     long c_iflag() {
/*  271 */       return c_iflag.get(this.seg);
/*      */     }
/*      */     
/*      */     void c_iflag(long f) {
/*  275 */       c_iflag.set(this.seg, f);
/*      */     }
/*      */     
/*      */     long c_oflag() {
/*  279 */       return c_oflag.get(this.seg);
/*      */     }
/*      */     
/*      */     void c_oflag(long f) {
/*  283 */       c_oflag.set(this.seg, f);
/*      */     }
/*      */     
/*      */     long c_cflag() {
/*  287 */       return c_cflag.get(this.seg);
/*      */     }
/*      */     
/*      */     void c_cflag(long f) {
/*  291 */       c_cflag.set(this.seg, f);
/*      */     }
/*      */     
/*      */     long c_lflag() {
/*  295 */       return c_lflag.get(this.seg);
/*      */     }
/*      */     
/*      */     void c_lflag(long f) {
/*  299 */       c_lflag.set(this.seg, f);
/*      */     }
/*      */     
/*      */     MemorySegment c_cc() {
/*  303 */       return this.seg.asSlice(c_cc_offset, 20L);
/*      */     }
/*      */     
/*      */     long c_ispeed() {
/*  307 */       return c_ispeed.get(this.seg);
/*      */     }
/*      */     
/*      */     void c_ispeed(long f) {
/*  311 */       c_ispeed.set(this.seg, f);
/*      */     }
/*      */     
/*      */     long c_ospeed() {
/*  315 */       return c_ospeed.get(this.seg);
/*      */     }
/*      */     
/*      */     void c_ospeed(long f) {
/*  319 */       c_ospeed.set(this.seg, f);
/*      */     }
/*      */     
/*      */     private static long setFlag(boolean flag, long value, long org) {
/*  323 */       return flag ? (org | value) : org;
/*      */     }
/*      */     
/*      */     private static <T extends Enum<T>> void addFlag(long value, EnumSet<T> flags, T flag, int v) {
/*  327 */       if ((value & v) != 0L) {
/*  328 */         flags.add(flag);
/*      */       }
/*      */     }
/*      */     
/*      */     public Attributes asAttributes() {
/*  333 */       Attributes attr = new Attributes();
/*      */       
/*  335 */       long c_iflag = c_iflag();
/*  336 */       EnumSet<Attributes.InputFlag> iflag = attr.getInputFlags();
/*  337 */       addFlag(c_iflag, iflag, Attributes.InputFlag.IGNBRK, CLibrary.IGNBRK);
/*  338 */       addFlag(c_iflag, iflag, Attributes.InputFlag.IGNBRK, CLibrary.IGNBRK);
/*  339 */       addFlag(c_iflag, iflag, Attributes.InputFlag.BRKINT, CLibrary.BRKINT);
/*  340 */       addFlag(c_iflag, iflag, Attributes.InputFlag.IGNPAR, CLibrary.IGNPAR);
/*  341 */       addFlag(c_iflag, iflag, Attributes.InputFlag.PARMRK, CLibrary.PARMRK);
/*  342 */       addFlag(c_iflag, iflag, Attributes.InputFlag.INPCK, CLibrary.INPCK);
/*  343 */       addFlag(c_iflag, iflag, Attributes.InputFlag.ISTRIP, CLibrary.ISTRIP);
/*  344 */       addFlag(c_iflag, iflag, Attributes.InputFlag.INLCR, CLibrary.INLCR);
/*  345 */       addFlag(c_iflag, iflag, Attributes.InputFlag.IGNCR, CLibrary.IGNCR);
/*  346 */       addFlag(c_iflag, iflag, Attributes.InputFlag.ICRNL, CLibrary.ICRNL);
/*  347 */       addFlag(c_iflag, iflag, Attributes.InputFlag.IXON, CLibrary.IXON);
/*  348 */       addFlag(c_iflag, iflag, Attributes.InputFlag.IXOFF, CLibrary.IXOFF);
/*  349 */       addFlag(c_iflag, iflag, Attributes.InputFlag.IXANY, CLibrary.IXANY);
/*  350 */       addFlag(c_iflag, iflag, Attributes.InputFlag.IMAXBEL, CLibrary.IMAXBEL);
/*  351 */       addFlag(c_iflag, iflag, Attributes.InputFlag.IUTF8, CLibrary.IUTF8);
/*      */       
/*  353 */       long c_oflag = c_oflag();
/*  354 */       EnumSet<Attributes.OutputFlag> oflag = attr.getOutputFlags();
/*  355 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.OPOST, CLibrary.OPOST);
/*  356 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.ONLCR, CLibrary.ONLCR);
/*  357 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.OXTABS, CLibrary.OXTABS);
/*  358 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.ONOEOT, CLibrary.ONOEOT);
/*  359 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.OCRNL, CLibrary.OCRNL);
/*  360 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.ONOCR, CLibrary.ONOCR);
/*  361 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.ONLRET, CLibrary.ONLRET);
/*  362 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.OFILL, CLibrary.OFILL);
/*  363 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.NLDLY, CLibrary.NLDLY);
/*  364 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.TABDLY, CLibrary.TABDLY);
/*  365 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.CRDLY, CLibrary.CRDLY);
/*  366 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.FFDLY, CLibrary.FFDLY);
/*  367 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.BSDLY, CLibrary.BSDLY);
/*  368 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.VTDLY, CLibrary.VTDLY);
/*  369 */       addFlag(c_oflag, oflag, Attributes.OutputFlag.OFDEL, CLibrary.OFDEL);
/*      */       
/*  371 */       long c_cflag = c_cflag();
/*  372 */       EnumSet<Attributes.ControlFlag> cflag = attr.getControlFlags();
/*  373 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CIGNORE, CLibrary.CIGNORE);
/*  374 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CS5, CLibrary.CS5);
/*  375 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CS6, CLibrary.CS6);
/*  376 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CS7, CLibrary.CS7);
/*  377 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CS8, CLibrary.CS8);
/*  378 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CSTOPB, CLibrary.CSTOPB);
/*  379 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CREAD, CLibrary.CREAD);
/*  380 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.PARENB, CLibrary.PARENB);
/*  381 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.PARODD, CLibrary.PARODD);
/*  382 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.HUPCL, CLibrary.HUPCL);
/*  383 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CLOCAL, CLibrary.CLOCAL);
/*  384 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CCTS_OFLOW, CLibrary.CCTS_OFLOW);
/*  385 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CRTS_IFLOW, CLibrary.CRTS_IFLOW);
/*  386 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CDSR_OFLOW, CLibrary.CDSR_OFLOW);
/*  387 */       addFlag(c_cflag, cflag, Attributes.ControlFlag.CCAR_OFLOW, CLibrary.CCAR_OFLOW);
/*      */       
/*  389 */       long c_lflag = c_lflag();
/*  390 */       EnumSet<Attributes.LocalFlag> lflag = attr.getLocalFlags();
/*  391 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOKE, CLibrary.ECHOKE);
/*  392 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOE, CLibrary.ECHOE);
/*  393 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOK, CLibrary.ECHOK);
/*  394 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHO, CLibrary.ECHO);
/*  395 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHONL, CLibrary.ECHONL);
/*  396 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOPRT, CLibrary.ECHOPRT);
/*  397 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOCTL, CLibrary.ECHOCTL);
/*  398 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ISIG, CLibrary.ISIG);
/*  399 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ICANON, CLibrary.ICANON);
/*  400 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.ALTWERASE, CLibrary.ALTWERASE);
/*  401 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.IEXTEN, CLibrary.IEXTEN);
/*  402 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.EXTPROC, CLibrary.EXTPROC);
/*  403 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.TOSTOP, CLibrary.TOSTOP);
/*  404 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.FLUSHO, CLibrary.FLUSHO);
/*  405 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.NOKERNINFO, CLibrary.NOKERNINFO);
/*  406 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.PENDIN, CLibrary.PENDIN);
/*  407 */       addFlag(c_lflag, lflag, Attributes.LocalFlag.NOFLSH, CLibrary.NOFLSH);
/*      */       
/*  409 */       byte[] c_cc = c_cc().toArray(ValueLayout.JAVA_BYTE);
/*  410 */       EnumMap<Attributes.ControlChar, Integer> cc = attr.getControlChars();
/*  411 */       cc.put(Attributes.ControlChar.VEOF, Integer.valueOf(c_cc[CLibrary.VEOF]));
/*  412 */       cc.put(Attributes.ControlChar.VEOL, Integer.valueOf(c_cc[CLibrary.VEOL]));
/*  413 */       cc.put(Attributes.ControlChar.VEOL2, Integer.valueOf(c_cc[CLibrary.VEOL2]));
/*  414 */       cc.put(Attributes.ControlChar.VERASE, Integer.valueOf(c_cc[CLibrary.VERASE]));
/*  415 */       cc.put(Attributes.ControlChar.VWERASE, Integer.valueOf(c_cc[CLibrary.VWERASE]));
/*  416 */       cc.put(Attributes.ControlChar.VKILL, Integer.valueOf(c_cc[CLibrary.VKILL]));
/*  417 */       cc.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(c_cc[CLibrary.VREPRINT]));
/*  418 */       cc.put(Attributes.ControlChar.VINTR, Integer.valueOf(c_cc[CLibrary.VINTR]));
/*  419 */       cc.put(Attributes.ControlChar.VQUIT, Integer.valueOf(c_cc[CLibrary.VQUIT]));
/*  420 */       cc.put(Attributes.ControlChar.VSUSP, Integer.valueOf(c_cc[CLibrary.VSUSP]));
/*  421 */       if (CLibrary.VDSUSP != -1) {
/*  422 */         cc.put(Attributes.ControlChar.VDSUSP, Integer.valueOf(c_cc[CLibrary.VDSUSP]));
/*      */       }
/*  424 */       cc.put(Attributes.ControlChar.VSTART, Integer.valueOf(c_cc[CLibrary.VSTART]));
/*  425 */       cc.put(Attributes.ControlChar.VSTOP, Integer.valueOf(c_cc[CLibrary.VSTOP]));
/*  426 */       cc.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(c_cc[CLibrary.VLNEXT]));
/*  427 */       cc.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(c_cc[CLibrary.VDISCARD]));
/*  428 */       cc.put(Attributes.ControlChar.VMIN, Integer.valueOf(c_cc[CLibrary.VMIN]));
/*  429 */       cc.put(Attributes.ControlChar.VTIME, Integer.valueOf(c_cc[CLibrary.VTIME]));
/*  430 */       if (CLibrary.VSTATUS != -1) {
/*  431 */         cc.put(Attributes.ControlChar.VSTATUS, Integer.valueOf(c_cc[CLibrary.VSTATUS]));
/*      */       }
/*      */       
/*  434 */       return attr;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  448 */     Linker linker = Linker.nativeLinker();
/*  449 */     SymbolLookup lookup = SymbolLookup.loaderLookup().or(linker.defaultLookup());
/*      */     
/*  451 */     ioctl = linker.downcallHandle(lookup
/*  452 */         .find("ioctl").get(), 
/*  453 */         FunctionDescriptor.of(ValueLayout.JAVA_INT, new MemoryLayout[] { ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS }), new Linker.Option[] {
/*      */           
/*  455 */           Linker.Option.firstVariadicArg(2)
/*      */         });
/*  457 */     isatty = linker.downcallHandle(lookup
/*  458 */         .find("isatty").get(), FunctionDescriptor.of(ValueLayout.JAVA_INT, new MemoryLayout[] { ValueLayout.JAVA_INT }), new Linker.Option[0]);
/*      */     
/*  460 */     tcsetattr = linker.downcallHandle(lookup
/*  461 */         .find("tcsetattr").get(), 
/*  462 */         FunctionDescriptor.of(ValueLayout.JAVA_INT, new MemoryLayout[] { ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS }), new Linker.Option[0]);
/*      */ 
/*      */     
/*  465 */     tcgetattr = linker.downcallHandle(lookup
/*  466 */         .find("tcgetattr").get(), 
/*  467 */         FunctionDescriptor.of(ValueLayout.JAVA_INT, new MemoryLayout[] { ValueLayout.JAVA_INT, ValueLayout.ADDRESS }), new Linker.Option[0]);
/*      */     
/*  469 */     ttyname_r = linker.downcallHandle(lookup
/*  470 */         .find("ttyname_r").get(), 
/*  471 */         FunctionDescriptor.of(ValueLayout.JAVA_INT, new MemoryLayout[] { ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG }), new Linker.Option[0]);
/*      */ 
/*      */     
/*  474 */     LinkageError error = null;
/*  475 */     Optional<MemorySegment> openPtyAddr = lookup.find("openpty");
/*  476 */     if (openPtyAddr.isEmpty()) {
/*  477 */       StringBuilder sb = new StringBuilder();
/*  478 */       sb.append("Unable to find openpty native method in static libraries and unable to load the util library.");
/*  479 */       List<Throwable> suppressed = new ArrayList<>();
/*      */       try {
/*  481 */         System.loadLibrary("util");
/*  482 */         openPtyAddr = lookup.find("openpty");
/*  483 */       } catch (Throwable t) {
/*  484 */         suppressed.add(t);
/*      */       } 
/*  486 */       if (openPtyAddr.isEmpty()) {
/*  487 */         String libUtilPath = System.getProperty("org.jline.ffm.libutil");
/*  488 */         if (libUtilPath != null && !libUtilPath.isEmpty()) {
/*      */           try {
/*  490 */             System.load(libUtilPath);
/*  491 */             openPtyAddr = lookup.find("openpty");
/*  492 */           } catch (Throwable t) {
/*  493 */             suppressed.add(t);
/*      */           } 
/*      */         }
/*      */       } 
/*  497 */       if (openPtyAddr.isEmpty() && OSUtils.IS_LINUX) {
/*      */ 
/*      */         
/*  500 */         try { Process p = Runtime.getRuntime().exec(new String[] { "uname", "-m" });
/*  501 */           p.waitFor();
/*  502 */           InputStream in = p.getInputStream(); 
/*  503 */           try { String hwName = readFully(in).trim();
/*  504 */             Path libDir = Paths.get("/usr/lib", new String[] { hwName + "-linux-gnu" });
/*  505 */             Stream<Path> stream = Files.list(libDir);
/*      */ 
/*      */             
/*  508 */             try { List<Path> libs = (List<Path>)stream.filter(l -> l.getFileName().toString().startsWith("libutil.so.")).collect(Collectors.toList());
/*  509 */               for (Path lib : libs) {
/*      */                 try {
/*  511 */                   System.load(lib.toString());
/*  512 */                   openPtyAddr = lookup.find("openpty");
/*  513 */                   if (openPtyAddr.isPresent()) {
/*      */                     break;
/*      */                   }
/*  516 */                 } catch (Throwable t) {
/*  517 */                   suppressed.add(t);
/*      */                 } 
/*      */               } 
/*  520 */               if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*  521 */                 try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (in != null) in.close();  } catch (Throwable throwable) { if (in != null)
/*  522 */               try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable t)
/*  523 */         { suppressed.add(t); }
/*      */       
/*      */       }
/*  526 */       if (openPtyAddr.isEmpty()) {
/*  527 */         for (Throwable t : suppressed) {
/*  528 */           sb.append("\n\t- ").append(t.toString());
/*      */         }
/*  530 */         error = new LinkageError(sb.toString());
/*  531 */         Objects.requireNonNull(error); suppressed.forEach(error::addSuppressed);
/*  532 */         if (logger.isLoggable(Level.FINE)) {
/*  533 */           logger.log(Level.WARNING, error.getMessage(), error);
/*      */         } else {
/*  535 */           logger.log(Level.WARNING, error.getMessage());
/*      */         } 
/*      */       } 
/*      */     } 
/*  539 */     if (openPtyAddr.isPresent()) {
/*  540 */       openpty = linker.downcallHandle(openPtyAddr
/*  541 */           .get(), 
/*  542 */           FunctionDescriptor.of(ValueLayout.JAVA_INT, new MemoryLayout[] { ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS }), new Linker.Option[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  549 */       openptyError = null;
/*      */     } else {
/*  551 */       openpty = null;
/*  552 */       openptyError = error;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  799 */     String osName = System.getProperty("os.name");
/*  800 */     if (osName.startsWith("Linux")) {
/*  801 */       String arch = System.getProperty("os.arch");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  807 */       boolean isMipsPpcOrSparc = (arch.equals("mips") || arch.equals("mips64") || arch.equals("mipsel") || arch.equals("mips64el") || arch.startsWith("ppc") || arch.startsWith("sparc"));
/*  808 */       TIOCGWINSZ = isMipsPpcOrSparc ? 1074295912 : 21523;
/*  809 */       TIOCSWINSZ = isMipsPpcOrSparc ? -2146929561 : 21524;
/*      */       
/*  811 */       TCSANOW = 0;
/*  812 */       TCSADRAIN = 1;
/*  813 */       TCSAFLUSH = 2;
/*      */       
/*  815 */       VINTR = 0;
/*  816 */       VQUIT = 1;
/*  817 */       VERASE = 2;
/*  818 */       VKILL = 3;
/*  819 */       VEOF = 4;
/*  820 */       VTIME = 5;
/*  821 */       VMIN = 6;
/*  822 */       VSWTC = 7;
/*  823 */       VSTART = 8;
/*  824 */       VSTOP = 9;
/*  825 */       VSUSP = 10;
/*  826 */       VEOL = 11;
/*  827 */       VREPRINT = 12;
/*  828 */       VDISCARD = 13;
/*  829 */       VWERASE = 14;
/*  830 */       VLNEXT = 15;
/*  831 */       VEOL2 = 16;
/*  832 */       VERASE2 = -1;
/*  833 */       VDSUSP = -1;
/*  834 */       VSTATUS = -1;
/*      */       
/*  836 */       IGNBRK = 1;
/*  837 */       BRKINT = 2;
/*  838 */       IGNPAR = 4;
/*  839 */       PARMRK = 8;
/*  840 */       INPCK = 16;
/*  841 */       ISTRIP = 32;
/*  842 */       INLCR = 64;
/*  843 */       IGNCR = 128;
/*  844 */       ICRNL = 256;
/*  845 */       IUCLC = 512;
/*  846 */       IXON = 1024;
/*  847 */       IXANY = 2048;
/*  848 */       IXOFF = 4096;
/*  849 */       IMAXBEL = 8192;
/*  850 */       IUTF8 = 16384;
/*      */       
/*  852 */       OPOST = 1;
/*  853 */       OLCUC = 2;
/*  854 */       ONLCR = 4;
/*  855 */       OCRNL = 8;
/*  856 */       ONOCR = 16;
/*  857 */       ONLRET = 32;
/*  858 */       OFILL = 64;
/*  859 */       OFDEL = 128;
/*  860 */       NLDLY = 256;
/*  861 */       NL0 = 0;
/*  862 */       NL1 = 256;
/*  863 */       CRDLY = 1536;
/*  864 */       CR0 = 0;
/*  865 */       CR1 = 512;
/*  866 */       CR2 = 1024;
/*  867 */       CR3 = 1536;
/*  868 */       TABDLY = 6144;
/*  869 */       TAB0 = 0;
/*  870 */       TAB1 = 2048;
/*  871 */       TAB2 = 4096;
/*  872 */       TAB3 = 6144;
/*  873 */       XTABS = 6144;
/*  874 */       BSDLY = 8192;
/*  875 */       BS0 = 0;
/*  876 */       BS1 = 8192;
/*  877 */       VTDLY = 16384;
/*  878 */       VT0 = 0;
/*  879 */       VT1 = 16384;
/*  880 */       FFDLY = 32768;
/*  881 */       FF0 = 0;
/*  882 */       FF1 = 32768;
/*      */       
/*  884 */       CBAUD = 4111;
/*  885 */       B0 = 0;
/*  886 */       B50 = 1;
/*  887 */       B75 = 2;
/*  888 */       B110 = 3;
/*  889 */       B134 = 4;
/*  890 */       B150 = 5;
/*  891 */       B200 = 6;
/*  892 */       B300 = 7;
/*  893 */       B600 = 8;
/*  894 */       B1200 = 9;
/*  895 */       B1800 = 10;
/*  896 */       B2400 = 11;
/*  897 */       B4800 = 12;
/*  898 */       B9600 = 13;
/*  899 */       B19200 = 14;
/*  900 */       B38400 = 15;
/*  901 */       EXTA = B19200;
/*  902 */       EXTB = B38400;
/*  903 */       CSIZE = 48;
/*  904 */       CS5 = 0;
/*  905 */       CS6 = 16;
/*  906 */       CS7 = 32;
/*  907 */       CS8 = 48;
/*  908 */       CSTOPB = 64;
/*  909 */       CREAD = 128;
/*  910 */       PARENB = 256;
/*  911 */       PARODD = 512;
/*  912 */       HUPCL = 1024;
/*  913 */       CLOCAL = 2048;
/*      */       
/*  915 */       ISIG = 1;
/*  916 */       ICANON = 2;
/*  917 */       XCASE = 4;
/*  918 */       ECHO = 8;
/*  919 */       ECHOE = 16;
/*  920 */       ECHOK = 32;
/*  921 */       ECHONL = 64;
/*  922 */       NOFLSH = 128;
/*  923 */       TOSTOP = 256;
/*  924 */       ECHOCTL = 512;
/*  925 */       ECHOPRT = 1024;
/*  926 */       ECHOKE = 2048;
/*  927 */       FLUSHO = 4096;
/*  928 */       PENDIN = 8192;
/*  929 */       IEXTEN = 32768;
/*  930 */       EXTPROC = 65536;
/*  931 */     } else if (osName.startsWith("Solaris") || osName.startsWith("SunOS")) {
/*  932 */       int _TIOC = 21504;
/*  933 */       TIOCGWINSZ = _TIOC | 0x68;
/*  934 */       TIOCSWINSZ = _TIOC | 0x67;
/*      */       
/*  936 */       TCSANOW = 0;
/*  937 */       TCSADRAIN = 1;
/*  938 */       TCSAFLUSH = 2;
/*      */       
/*  940 */       VINTR = 0;
/*  941 */       VQUIT = 1;
/*  942 */       VERASE = 2;
/*  943 */       VKILL = 3;
/*  944 */       VEOF = 4;
/*  945 */       VTIME = 5;
/*  946 */       VMIN = 6;
/*  947 */       VSWTC = 7;
/*  948 */       VSTART = 8;
/*  949 */       VSTOP = 9;
/*  950 */       VSUSP = 10;
/*  951 */       VEOL = 11;
/*  952 */       VREPRINT = 12;
/*  953 */       VDISCARD = 13;
/*  954 */       VWERASE = 14;
/*  955 */       VLNEXT = 15;
/*  956 */       VEOL2 = 16;
/*  957 */       VERASE2 = -1;
/*  958 */       VDSUSP = -1;
/*  959 */       VSTATUS = -1;
/*      */       
/*  961 */       IGNBRK = 1;
/*  962 */       BRKINT = 2;
/*  963 */       IGNPAR = 4;
/*  964 */       PARMRK = 16;
/*  965 */       INPCK = 32;
/*  966 */       ISTRIP = 64;
/*  967 */       INLCR = 256;
/*  968 */       IGNCR = 512;
/*  969 */       ICRNL = 1024;
/*  970 */       IUCLC = 4096;
/*  971 */       IXON = 8192;
/*  972 */       IXANY = 16384;
/*  973 */       IXOFF = 65536;
/*  974 */       IMAXBEL = 131072;
/*  975 */       IUTF8 = 262144;
/*      */       
/*  977 */       OPOST = 1;
/*  978 */       OLCUC = 2;
/*  979 */       ONLCR = 4;
/*  980 */       OCRNL = 16;
/*  981 */       ONOCR = 32;
/*  982 */       ONLRET = 64;
/*  983 */       OFILL = 256;
/*  984 */       OFDEL = 512;
/*  985 */       NLDLY = 1024;
/*  986 */       NL0 = 0;
/*  987 */       NL1 = 1024;
/*  988 */       CRDLY = 12288;
/*  989 */       CR0 = 0;
/*  990 */       CR1 = 4096;
/*  991 */       CR2 = 8192;
/*  992 */       CR3 = 12288;
/*  993 */       TABDLY = 81920;
/*  994 */       TAB0 = 0;
/*  995 */       TAB1 = 16384;
/*  996 */       TAB2 = 65536;
/*  997 */       TAB3 = 81920;
/*  998 */       XTABS = 81920;
/*  999 */       BSDLY = 131072;
/* 1000 */       BS0 = 0;
/* 1001 */       BS1 = 131072;
/* 1002 */       VTDLY = 262144;
/* 1003 */       VT0 = 0;
/* 1004 */       VT1 = 262144;
/* 1005 */       FFDLY = 1048576;
/* 1006 */       FF0 = 0;
/* 1007 */       FF1 = 1048576;
/*      */       
/* 1009 */       CBAUD = 65559;
/* 1010 */       B0 = 0;
/* 1011 */       B50 = 1;
/* 1012 */       B75 = 2;
/* 1013 */       B110 = 3;
/* 1014 */       B134 = 4;
/* 1015 */       B150 = 5;
/* 1016 */       B200 = 6;
/* 1017 */       B300 = 7;
/* 1018 */       B600 = 16;
/* 1019 */       B1200 = 17;
/* 1020 */       B1800 = 18;
/* 1021 */       B2400 = 19;
/* 1022 */       B4800 = 20;
/* 1023 */       B9600 = 21;
/* 1024 */       B19200 = 22;
/* 1025 */       B38400 = 23;
/* 1026 */       EXTA = 11637248;
/* 1027 */       EXTB = 11764736;
/* 1028 */       CSIZE = 96;
/* 1029 */       CS5 = 0;
/* 1030 */       CS6 = 32;
/* 1031 */       CS7 = 64;
/* 1032 */       CS8 = 96;
/* 1033 */       CSTOPB = 256;
/* 1034 */       CREAD = 512;
/* 1035 */       PARENB = 1024;
/* 1036 */       PARODD = 4096;
/* 1037 */       HUPCL = 8192;
/* 1038 */       CLOCAL = 16384;
/*      */       
/* 1040 */       ISIG = 1;
/* 1041 */       ICANON = 2;
/* 1042 */       XCASE = 4;
/* 1043 */       ECHO = 16;
/* 1044 */       ECHOE = 32;
/* 1045 */       ECHOK = 64;
/* 1046 */       ECHONL = 256;
/* 1047 */       NOFLSH = 512;
/* 1048 */       TOSTOP = 1024;
/* 1049 */       ECHOCTL = 4096;
/* 1050 */       ECHOPRT = 8192;
/* 1051 */       ECHOKE = 16384;
/* 1052 */       FLUSHO = 65536;
/* 1053 */       PENDIN = 262144;
/* 1054 */       IEXTEN = 1048576;
/* 1055 */       EXTPROC = 2097152;
/* 1056 */     } else if (osName.startsWith("Mac") || osName.startsWith("Darwin")) {
/* 1057 */       TIOCGWINSZ = 1074295912;
/* 1058 */       TIOCSWINSZ = -2146929561;
/*      */       
/* 1060 */       TCSANOW = 0;
/*      */       
/* 1062 */       VEOF = 0;
/* 1063 */       VEOL = 1;
/* 1064 */       VEOL2 = 2;
/* 1065 */       VERASE = 3;
/* 1066 */       VWERASE = 4;
/* 1067 */       VKILL = 5;
/* 1068 */       VREPRINT = 6;
/* 1069 */       VINTR = 8;
/* 1070 */       VQUIT = 9;
/* 1071 */       VSUSP = 10;
/* 1072 */       VDSUSP = 11;
/* 1073 */       VSTART = 12;
/* 1074 */       VSTOP = 13;
/* 1075 */       VLNEXT = 14;
/* 1076 */       VDISCARD = 15;
/* 1077 */       VMIN = 16;
/* 1078 */       VTIME = 17;
/* 1079 */       VSTATUS = 18;
/* 1080 */       VERASE2 = -1;
/* 1081 */       VSWTC = -1;
/*      */       
/* 1083 */       IGNBRK = 1;
/* 1084 */       BRKINT = 2;
/* 1085 */       IGNPAR = 4;
/* 1086 */       PARMRK = 8;
/* 1087 */       INPCK = 16;
/* 1088 */       ISTRIP = 32;
/* 1089 */       INLCR = 64;
/* 1090 */       IGNCR = 128;
/* 1091 */       ICRNL = 256;
/* 1092 */       IXON = 512;
/* 1093 */       IXOFF = 1024;
/* 1094 */       IXANY = 2048;
/* 1095 */       IMAXBEL = 8192;
/* 1096 */       IUTF8 = 16384;
/*      */       
/* 1098 */       OPOST = 1;
/* 1099 */       ONLCR = 2;
/* 1100 */       OXTABS = 4;
/* 1101 */       ONOEOT = 8;
/* 1102 */       OCRNL = 16;
/* 1103 */       ONOCR = 32;
/* 1104 */       ONLRET = 64;
/* 1105 */       OFILL = 128;
/* 1106 */       NLDLY = 768;
/* 1107 */       TABDLY = 3076;
/* 1108 */       CRDLY = 12288;
/* 1109 */       FFDLY = 16384;
/* 1110 */       BSDLY = 32768;
/* 1111 */       VTDLY = 65536;
/* 1112 */       OFDEL = 131072;
/*      */       
/* 1114 */       CIGNORE = 1;
/* 1115 */       CS5 = 0;
/* 1116 */       CS6 = 256;
/* 1117 */       CS7 = 512;
/* 1118 */       CS8 = 768;
/* 1119 */       CSTOPB = 1024;
/* 1120 */       CREAD = 2048;
/* 1121 */       PARENB = 4096;
/* 1122 */       PARODD = 8192;
/* 1123 */       HUPCL = 16384;
/* 1124 */       CLOCAL = 32768;
/* 1125 */       CCTS_OFLOW = 65536;
/* 1126 */       CRTS_IFLOW = 131072;
/* 1127 */       CDTR_IFLOW = 262144;
/* 1128 */       CDSR_OFLOW = 524288;
/* 1129 */       CCAR_OFLOW = 1048576;
/*      */       
/* 1131 */       ECHOKE = 1;
/* 1132 */       ECHOE = 2;
/* 1133 */       ECHOK = 4;
/* 1134 */       ECHO = 8;
/* 1135 */       ECHONL = 16;
/* 1136 */       ECHOPRT = 32;
/* 1137 */       ECHOCTL = 64;
/* 1138 */       ISIG = 128;
/* 1139 */       ICANON = 256;
/* 1140 */       ALTWERASE = 512;
/* 1141 */       IEXTEN = 1024;
/* 1142 */       EXTPROC = 2048;
/* 1143 */       TOSTOP = 4194304;
/* 1144 */       FLUSHO = 8388608;
/* 1145 */       NOKERNINFO = 33554432;
/* 1146 */       PENDIN = 536870912;
/* 1147 */       NOFLSH = Integer.MIN_VALUE;
/* 1148 */     } else if (osName.startsWith("FreeBSD")) {
/* 1149 */       TIOCGWINSZ = 1074295912;
/* 1150 */       TIOCSWINSZ = -2146929561;
/*      */       
/* 1152 */       TCSANOW = 0;
/* 1153 */       TCSADRAIN = 1;
/* 1154 */       TCSAFLUSH = 2;
/*      */       
/* 1156 */       VEOF = 0;
/* 1157 */       VEOL = 1;
/* 1158 */       VEOL2 = 2;
/* 1159 */       VERASE = 3;
/* 1160 */       VWERASE = 4;
/* 1161 */       VKILL = 5;
/* 1162 */       VREPRINT = 6;
/* 1163 */       VERASE2 = 7;
/* 1164 */       VINTR = 8;
/* 1165 */       VQUIT = 9;
/* 1166 */       VSUSP = 10;
/* 1167 */       VDSUSP = 11;
/* 1168 */       VSTART = 12;
/* 1169 */       VSTOP = 13;
/* 1170 */       VLNEXT = 14;
/* 1171 */       VDISCARD = 15;
/* 1172 */       VMIN = 16;
/* 1173 */       VTIME = 17;
/* 1174 */       VSTATUS = 18;
/* 1175 */       VSWTC = -1;
/*      */       
/* 1177 */       IGNBRK = 1;
/* 1178 */       BRKINT = 2;
/* 1179 */       IGNPAR = 4;
/* 1180 */       PARMRK = 8;
/* 1181 */       INPCK = 16;
/* 1182 */       ISTRIP = 32;
/* 1183 */       INLCR = 64;
/* 1184 */       IGNCR = 128;
/* 1185 */       ICRNL = 256;
/* 1186 */       IXON = 512;
/* 1187 */       IXOFF = 1024;
/* 1188 */       IXANY = 2048;
/* 1189 */       IMAXBEL = 8192;
/*      */       
/* 1191 */       OPOST = 1;
/* 1192 */       ONLCR = 2;
/* 1193 */       TABDLY = 4;
/* 1194 */       TAB0 = 0;
/* 1195 */       TAB3 = 4;
/* 1196 */       ONOEOT = 8;
/* 1197 */       OCRNL = 16;
/* 1198 */       ONLRET = 64;
/*      */       
/* 1200 */       CIGNORE = 1;
/* 1201 */       CSIZE = 768;
/* 1202 */       CS5 = 0;
/* 1203 */       CS6 = 256;
/* 1204 */       CS7 = 512;
/* 1205 */       CS8 = 768;
/* 1206 */       CSTOPB = 1024;
/* 1207 */       CREAD = 2048;
/* 1208 */       PARENB = 4096;
/* 1209 */       PARODD = 8192;
/* 1210 */       HUPCL = 16384;
/* 1211 */       CLOCAL = 32768;
/*      */       
/* 1213 */       ECHOKE = 1;
/* 1214 */       ECHOE = 2;
/* 1215 */       ECHOK = 4;
/* 1216 */       ECHO = 8;
/* 1217 */       ECHONL = 16;
/* 1218 */       ECHOPRT = 32;
/* 1219 */       ECHOCTL = 64;
/* 1220 */       ISIG = 128;
/* 1221 */       ICANON = 256;
/* 1222 */       ALTWERASE = 512;
/* 1223 */       IEXTEN = 1024;
/* 1224 */       EXTPROC = 2048;
/* 1225 */       TOSTOP = 4194304;
/* 1226 */       FLUSHO = 8388608;
/* 1227 */       PENDIN = 33554432;
/* 1228 */       NOFLSH = 134217728;
/*      */     } else {
/* 1230 */       throw new UnsupportedOperationException();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String readFully(InputStream in) throws IOException {
/*      */     int readLen = 0;
/*      */     ByteArrayOutputStream b = new ByteArrayOutputStream();
/*      */     byte[] buf = new byte[32];
/*      */     while ((readLen = in.read(buf, 0, buf.length)) >= 0)
/*      */       b.write(buf, 0, readLen); 
/*      */     return b.toString();
/*      */   }
/*      */   
/*      */   static Size getTerminalSize(int fd) {
/*      */     try {
/*      */       winsize ws = new winsize();
/*      */       int res = ioctl.invoke(fd, TIOCGWINSZ, ws.segment());
/*      */       return new Size(ws.ws_col(), ws.ws_row());
/*      */     } catch (Throwable e) {
/*      */       throw new RuntimeException("Unable to call ioctl(TIOCGWINSZ)", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static void setTerminalSize(int fd, Size size) {
/*      */     try {
/*      */       winsize ws = new winsize();
/*      */       ws.ws_row((short)size.getRows());
/*      */       ws.ws_col((short)size.getColumns());
/*      */       int i = ioctl.invoke(fd, TIOCSWINSZ, ws.segment());
/*      */     } catch (Throwable e) {
/*      */       throw new RuntimeException("Unable to call ioctl(TIOCSWINSZ)", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static Attributes getAttributes(int fd) {
/*      */     try {
/*      */       termios t = new termios();
/*      */       int res = tcgetattr.invoke(fd, t.segment());
/*      */       return t.asAttributes();
/*      */     } catch (Throwable e) {
/*      */       throw new RuntimeException("Unable to call tcgetattr()", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static void setAttributes(int fd, Attributes attr) {
/*      */     try {
/*      */       termios t = new termios(attr);
/*      */       int i = tcsetattr.invoke(fd, TCSANOW, t.segment());
/*      */     } catch (Throwable e) {
/*      */       throw new RuntimeException("Unable to call tcsetattr()", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static boolean isTty(int fd) {
/*      */     try {
/*      */       return (isatty.invoke(fd) == 1);
/*      */     } catch (Throwable e) {
/*      */       throw new RuntimeException("Unable to call isatty()", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static String ttyName(int fd) {
/*      */     try {
/*      */       MemorySegment buf = Arena.ofAuto().allocate(64L);
/*      */       int res = ttyname_r.invoke(fd, buf, buf.byteSize());
/*      */       byte[] data = buf.toArray(ValueLayout.JAVA_BYTE);
/*      */       int len = 0;
/*      */       while (data[len] != 0)
/*      */         len++; 
/*      */       return new String(data, 0, len);
/*      */     } catch (Throwable e) {
/*      */       throw new RuntimeException("Unable to call ttyname_r()", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static Pty openpty(TerminalProvider provider, Attributes attr, Size size) {
/*      */     if (openptyError != null)
/*      */       throw openptyError; 
/*      */     try {
/*      */       MemorySegment buf = Arena.ofAuto().allocate(64L);
/*      */       MemorySegment master = Arena.ofAuto().allocate(ValueLayout.JAVA_INT);
/*      */       MemorySegment slave = Arena.ofAuto().allocate(ValueLayout.JAVA_INT);
/*      */       int res = openpty.invoke(master, slave, buf, (attr != null) ? (new termios(attr)).segment() : MemorySegment.NULL, (size != null) ? (new winsize((short)size.getRows(), (short)size.getColumns())).segment() : MemorySegment.NULL);
/*      */       byte[] str = buf.toArray(ValueLayout.JAVA_BYTE);
/*      */       int len = 0;
/*      */       while (str[len] != 0)
/*      */         len++; 
/*      */       String device = new String(str, 0, len);
/*      */       return (Pty)new FfmNativePty(provider, null, master.get(ValueLayout.JAVA_INT, 0L), slave.get(ValueLayout.JAVA_INT, 0L), device);
/*      */     } catch (Throwable e) {
/*      */       throw new RuntimeException("Unable to call openpty()", e);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\ffm\CLibrary.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */