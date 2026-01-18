/*     */ package org.jline.terminal.impl.ffm;
/*     */ 
/*     */ import java.lang.foreign.Arena;
/*     */ import java.lang.foreign.GroupLayout;
/*     */ import java.lang.foreign.MemoryLayout;
/*     */ import java.lang.foreign.MemorySegment;
/*     */ import java.lang.foreign.ValueLayout;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.invoke.VarHandle;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.utils.OSUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class termios
/*     */ {
/*     */   static final GroupLayout LAYOUT;
/*     */   
/*     */   static {
/* 107 */     if (OSUtils.IS_OSX) {
/* 108 */       LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { ValueLayout.JAVA_LONG
/* 109 */             .withName("c_iflag"), ValueLayout.JAVA_LONG
/* 110 */             .withName("c_oflag"), ValueLayout.JAVA_LONG
/* 111 */             .withName("c_cflag"), ValueLayout.JAVA_LONG
/* 112 */             .withName("c_lflag"), 
/* 113 */             MemoryLayout.sequenceLayout(32L, ValueLayout.JAVA_BYTE).withName("c_cc"), ValueLayout.JAVA_LONG
/* 114 */             .withName("c_ispeed"), ValueLayout.JAVA_LONG
/* 115 */             .withName("c_ospeed") });
/* 116 */     } else if (OSUtils.IS_LINUX) {
/* 117 */       LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { ValueLayout.JAVA_INT
/* 118 */             .withName("c_iflag"), ValueLayout.JAVA_INT
/* 119 */             .withName("c_oflag"), ValueLayout.JAVA_INT
/* 120 */             .withName("c_cflag"), ValueLayout.JAVA_INT
/* 121 */             .withName("c_lflag"), ValueLayout.JAVA_BYTE
/* 122 */             .withName("c_line"), 
/* 123 */             MemoryLayout.sequenceLayout(32L, ValueLayout.JAVA_BYTE).withName("c_cc"), 
/* 124 */             MemoryLayout.paddingLayout(3L), ValueLayout.JAVA_INT
/* 125 */             .withName("c_ispeed"), ValueLayout.JAVA_INT
/* 126 */             .withName("c_ospeed") });
/*     */     } else {
/* 128 */       throw new IllegalStateException("Unsupported system!");
/*     */     } 
/* 130 */   } private static final VarHandle c_iflag = adjust2LinuxHandle(
/* 131 */       FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_iflag") }));
/* 132 */   private static final VarHandle c_oflag = adjust2LinuxHandle(
/* 133 */       FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_oflag") }));
/* 134 */   private static final VarHandle c_cflag = adjust2LinuxHandle(
/* 135 */       FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_cflag") }));
/* 136 */   private static final VarHandle c_lflag = adjust2LinuxHandle(
/* 137 */       FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_lflag") }));
/* 138 */   private static final long c_cc_offset = LAYOUT.byteOffset(new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_cc") });
/* 139 */   private static final VarHandle c_ispeed = adjust2LinuxHandle(
/* 140 */       FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_ispeed") }));
/* 141 */   private static final VarHandle c_ospeed = adjust2LinuxHandle(
/* 142 */       FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("c_ospeed") }));
/*     */   private final MemorySegment seg;
/*     */   
/*     */   private static VarHandle adjust2LinuxHandle(VarHandle v) {
/* 146 */     if (OSUtils.IS_LINUX) {
/* 147 */       MethodHandle id = MethodHandles.identity(int.class);
/* 148 */       v = MethodHandles.filterValue(v, 
/*     */           
/* 150 */           MethodHandles.explicitCastArguments(id, MethodType.methodType(int.class, long.class)), 
/* 151 */           MethodHandles.explicitCastArguments(id, MethodType.methodType(long.class, int.class)));
/*     */     } 
/*     */     
/* 154 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   termios() {
/* 160 */     this.seg = Arena.ofAuto().allocate(LAYOUT);
/*     */   }
/*     */   
/*     */   termios(Attributes t) {
/* 164 */     this();
/*     */     
/* 166 */     long c_iflag = 0L;
/* 167 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNBRK), CLibrary.IGNBRK, c_iflag);
/* 168 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.BRKINT), CLibrary.BRKINT, c_iflag);
/* 169 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNPAR), CLibrary.IGNPAR, c_iflag);
/* 170 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.PARMRK), CLibrary.PARMRK, c_iflag);
/* 171 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INPCK), CLibrary.INPCK, c_iflag);
/* 172 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ISTRIP), CLibrary.ISTRIP, c_iflag);
/* 173 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.INLCR), CLibrary.INLCR, c_iflag);
/* 174 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IGNCR), CLibrary.IGNCR, c_iflag);
/* 175 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.ICRNL), CLibrary.ICRNL, c_iflag);
/* 176 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXON), CLibrary.IXON, c_iflag);
/* 177 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXOFF), CLibrary.IXOFF, c_iflag);
/* 178 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IXANY), CLibrary.IXANY, c_iflag);
/* 179 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IMAXBEL), CLibrary.IMAXBEL, c_iflag);
/* 180 */     c_iflag = setFlag(t.getInputFlag(Attributes.InputFlag.IUTF8), CLibrary.IUTF8, c_iflag);
/* 181 */     c_iflag(c_iflag);
/*     */     
/* 183 */     long c_oflag = 0L;
/* 184 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OPOST), CLibrary.OPOST, c_oflag);
/* 185 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLCR), CLibrary.ONLCR, c_oflag);
/* 186 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OXTABS), CLibrary.OXTABS, c_oflag);
/* 187 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOEOT), CLibrary.ONOEOT, c_oflag);
/* 188 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OCRNL), CLibrary.OCRNL, c_oflag);
/* 189 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONOCR), CLibrary.ONOCR, c_oflag);
/* 190 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.ONLRET), CLibrary.ONLRET, c_oflag);
/* 191 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFILL), CLibrary.OFILL, c_oflag);
/* 192 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.NLDLY), CLibrary.NLDLY, c_oflag);
/* 193 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.TABDLY), CLibrary.TABDLY, c_oflag);
/* 194 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.CRDLY), CLibrary.CRDLY, c_oflag);
/* 195 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.FFDLY), CLibrary.FFDLY, c_oflag);
/* 196 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.BSDLY), CLibrary.BSDLY, c_oflag);
/* 197 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.VTDLY), CLibrary.VTDLY, c_oflag);
/* 198 */     c_oflag = setFlag(t.getOutputFlag(Attributes.OutputFlag.OFDEL), CLibrary.OFDEL, c_oflag);
/* 199 */     c_oflag(c_oflag);
/*     */     
/* 201 */     long c_cflag = 0L;
/* 202 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CIGNORE), CLibrary.CIGNORE, c_cflag);
/* 203 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS5), CLibrary.CS5, c_cflag);
/* 204 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS6), CLibrary.CS6, c_cflag);
/* 205 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS7), CLibrary.CS7, c_cflag);
/* 206 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CS8), CLibrary.CS8, c_cflag);
/* 207 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CSTOPB), CLibrary.CSTOPB, c_cflag);
/* 208 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CREAD), CLibrary.CREAD, c_cflag);
/* 209 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARENB), CLibrary.PARENB, c_cflag);
/* 210 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.PARODD), CLibrary.PARODD, c_cflag);
/* 211 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.HUPCL), CLibrary.HUPCL, c_cflag);
/* 212 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CLOCAL), CLibrary.CLOCAL, c_cflag);
/* 213 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CCTS_OFLOW), CLibrary.CCTS_OFLOW, c_cflag);
/* 214 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CRTS_IFLOW), CLibrary.CRTS_IFLOW, c_cflag);
/* 215 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CDTR_IFLOW), CLibrary.CDTR_IFLOW, c_cflag);
/* 216 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CDSR_OFLOW), CLibrary.CDSR_OFLOW, c_cflag);
/* 217 */     c_cflag = setFlag(t.getControlFlag(Attributes.ControlFlag.CCAR_OFLOW), CLibrary.CCAR_OFLOW, c_cflag);
/* 218 */     c_cflag(c_cflag);
/*     */     
/* 220 */     long c_lflag = 0L;
/* 221 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOKE), CLibrary.ECHOKE, c_lflag);
/* 222 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOE), CLibrary.ECHOE, c_lflag);
/* 223 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOK), CLibrary.ECHOK, c_lflag);
/* 224 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHO), CLibrary.ECHO, c_lflag);
/* 225 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHONL), CLibrary.ECHONL, c_lflag);
/* 226 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOPRT), CLibrary.ECHOPRT, c_lflag);
/* 227 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ECHOCTL), CLibrary.ECHOCTL, c_lflag);
/* 228 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ISIG), CLibrary.ISIG, c_lflag);
/* 229 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ICANON), CLibrary.ICANON, c_lflag);
/* 230 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.ALTWERASE), CLibrary.ALTWERASE, c_lflag);
/* 231 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.IEXTEN), CLibrary.IEXTEN, c_lflag);
/* 232 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.EXTPROC), CLibrary.EXTPROC, c_lflag);
/* 233 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.TOSTOP), CLibrary.TOSTOP, c_lflag);
/* 234 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.FLUSHO), CLibrary.FLUSHO, c_lflag);
/* 235 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOKERNINFO), CLibrary.NOKERNINFO, c_lflag);
/* 236 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.PENDIN), CLibrary.PENDIN, c_lflag);
/* 237 */     c_lflag = setFlag(t.getLocalFlag(Attributes.LocalFlag.NOFLSH), CLibrary.NOFLSH, c_lflag);
/* 238 */     c_lflag(c_lflag);
/*     */     
/* 240 */     byte[] c_cc = new byte[20];
/* 241 */     c_cc[CLibrary.VEOF] = (byte)t.getControlChar(Attributes.ControlChar.VEOF);
/* 242 */     c_cc[CLibrary.VEOL] = (byte)t.getControlChar(Attributes.ControlChar.VEOL);
/* 243 */     c_cc[CLibrary.VEOL2] = (byte)t.getControlChar(Attributes.ControlChar.VEOL2);
/* 244 */     c_cc[CLibrary.VERASE] = (byte)t.getControlChar(Attributes.ControlChar.VERASE);
/* 245 */     c_cc[CLibrary.VWERASE] = (byte)t.getControlChar(Attributes.ControlChar.VWERASE);
/* 246 */     c_cc[CLibrary.VKILL] = (byte)t.getControlChar(Attributes.ControlChar.VKILL);
/* 247 */     c_cc[CLibrary.VREPRINT] = (byte)t.getControlChar(Attributes.ControlChar.VREPRINT);
/* 248 */     c_cc[CLibrary.VINTR] = (byte)t.getControlChar(Attributes.ControlChar.VINTR);
/* 249 */     c_cc[CLibrary.VQUIT] = (byte)t.getControlChar(Attributes.ControlChar.VQUIT);
/* 250 */     c_cc[CLibrary.VSUSP] = (byte)t.getControlChar(Attributes.ControlChar.VSUSP);
/* 251 */     if (CLibrary.VDSUSP != -1) {
/* 252 */       c_cc[CLibrary.VDSUSP] = (byte)t.getControlChar(Attributes.ControlChar.VDSUSP);
/*     */     }
/* 254 */     c_cc[CLibrary.VSTART] = (byte)t.getControlChar(Attributes.ControlChar.VSTART);
/* 255 */     c_cc[CLibrary.VSTOP] = (byte)t.getControlChar(Attributes.ControlChar.VSTOP);
/* 256 */     c_cc[CLibrary.VLNEXT] = (byte)t.getControlChar(Attributes.ControlChar.VLNEXT);
/* 257 */     c_cc[CLibrary.VDISCARD] = (byte)t.getControlChar(Attributes.ControlChar.VDISCARD);
/* 258 */     c_cc[CLibrary.VMIN] = (byte)t.getControlChar(Attributes.ControlChar.VMIN);
/* 259 */     c_cc[CLibrary.VTIME] = (byte)t.getControlChar(Attributes.ControlChar.VTIME);
/* 260 */     if (CLibrary.VSTATUS != -1) {
/* 261 */       c_cc[CLibrary.VSTATUS] = (byte)t.getControlChar(Attributes.ControlChar.VSTATUS);
/*     */     }
/* 263 */     c_cc().copyFrom(MemorySegment.ofArray(c_cc));
/*     */   }
/*     */   
/*     */   MemorySegment segment() {
/* 267 */     return this.seg;
/*     */   }
/*     */   
/*     */   long c_iflag() {
/* 271 */     return c_iflag.get(this.seg);
/*     */   }
/*     */   
/*     */   void c_iflag(long f) {
/* 275 */     c_iflag.set(this.seg, f);
/*     */   }
/*     */   
/*     */   long c_oflag() {
/* 279 */     return c_oflag.get(this.seg);
/*     */   }
/*     */   
/*     */   void c_oflag(long f) {
/* 283 */     c_oflag.set(this.seg, f);
/*     */   }
/*     */   
/*     */   long c_cflag() {
/* 287 */     return c_cflag.get(this.seg);
/*     */   }
/*     */   
/*     */   void c_cflag(long f) {
/* 291 */     c_cflag.set(this.seg, f);
/*     */   }
/*     */   
/*     */   long c_lflag() {
/* 295 */     return c_lflag.get(this.seg);
/*     */   }
/*     */   
/*     */   void c_lflag(long f) {
/* 299 */     c_lflag.set(this.seg, f);
/*     */   }
/*     */   
/*     */   MemorySegment c_cc() {
/* 303 */     return this.seg.asSlice(c_cc_offset, 20L);
/*     */   }
/*     */   
/*     */   long c_ispeed() {
/* 307 */     return c_ispeed.get(this.seg);
/*     */   }
/*     */   
/*     */   void c_ispeed(long f) {
/* 311 */     c_ispeed.set(this.seg, f);
/*     */   }
/*     */   
/*     */   long c_ospeed() {
/* 315 */     return c_ospeed.get(this.seg);
/*     */   }
/*     */   
/*     */   void c_ospeed(long f) {
/* 319 */     c_ospeed.set(this.seg, f);
/*     */   }
/*     */   
/*     */   private static long setFlag(boolean flag, long value, long org) {
/* 323 */     return flag ? (org | value) : org;
/*     */   }
/*     */   
/*     */   private static <T extends Enum<T>> void addFlag(long value, EnumSet<T> flags, T flag, int v) {
/* 327 */     if ((value & v) != 0L) {
/* 328 */       flags.add(flag);
/*     */     }
/*     */   }
/*     */   
/*     */   public Attributes asAttributes() {
/* 333 */     Attributes attr = new Attributes();
/*     */     
/* 335 */     long c_iflag = c_iflag();
/* 336 */     EnumSet<Attributes.InputFlag> iflag = attr.getInputFlags();
/* 337 */     addFlag(c_iflag, iflag, Attributes.InputFlag.IGNBRK, CLibrary.IGNBRK);
/* 338 */     addFlag(c_iflag, iflag, Attributes.InputFlag.IGNBRK, CLibrary.IGNBRK);
/* 339 */     addFlag(c_iflag, iflag, Attributes.InputFlag.BRKINT, CLibrary.BRKINT);
/* 340 */     addFlag(c_iflag, iflag, Attributes.InputFlag.IGNPAR, CLibrary.IGNPAR);
/* 341 */     addFlag(c_iflag, iflag, Attributes.InputFlag.PARMRK, CLibrary.PARMRK);
/* 342 */     addFlag(c_iflag, iflag, Attributes.InputFlag.INPCK, CLibrary.INPCK);
/* 343 */     addFlag(c_iflag, iflag, Attributes.InputFlag.ISTRIP, CLibrary.ISTRIP);
/* 344 */     addFlag(c_iflag, iflag, Attributes.InputFlag.INLCR, CLibrary.INLCR);
/* 345 */     addFlag(c_iflag, iflag, Attributes.InputFlag.IGNCR, CLibrary.IGNCR);
/* 346 */     addFlag(c_iflag, iflag, Attributes.InputFlag.ICRNL, CLibrary.ICRNL);
/* 347 */     addFlag(c_iflag, iflag, Attributes.InputFlag.IXON, CLibrary.IXON);
/* 348 */     addFlag(c_iflag, iflag, Attributes.InputFlag.IXOFF, CLibrary.IXOFF);
/* 349 */     addFlag(c_iflag, iflag, Attributes.InputFlag.IXANY, CLibrary.IXANY);
/* 350 */     addFlag(c_iflag, iflag, Attributes.InputFlag.IMAXBEL, CLibrary.IMAXBEL);
/* 351 */     addFlag(c_iflag, iflag, Attributes.InputFlag.IUTF8, CLibrary.IUTF8);
/*     */     
/* 353 */     long c_oflag = c_oflag();
/* 354 */     EnumSet<Attributes.OutputFlag> oflag = attr.getOutputFlags();
/* 355 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.OPOST, CLibrary.OPOST);
/* 356 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.ONLCR, CLibrary.ONLCR);
/* 357 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.OXTABS, CLibrary.OXTABS);
/* 358 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.ONOEOT, CLibrary.ONOEOT);
/* 359 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.OCRNL, CLibrary.OCRNL);
/* 360 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.ONOCR, CLibrary.ONOCR);
/* 361 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.ONLRET, CLibrary.ONLRET);
/* 362 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.OFILL, CLibrary.OFILL);
/* 363 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.NLDLY, CLibrary.NLDLY);
/* 364 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.TABDLY, CLibrary.TABDLY);
/* 365 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.CRDLY, CLibrary.CRDLY);
/* 366 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.FFDLY, CLibrary.FFDLY);
/* 367 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.BSDLY, CLibrary.BSDLY);
/* 368 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.VTDLY, CLibrary.VTDLY);
/* 369 */     addFlag(c_oflag, oflag, Attributes.OutputFlag.OFDEL, CLibrary.OFDEL);
/*     */     
/* 371 */     long c_cflag = c_cflag();
/* 372 */     EnumSet<Attributes.ControlFlag> cflag = attr.getControlFlags();
/* 373 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CIGNORE, CLibrary.CIGNORE);
/* 374 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CS5, CLibrary.CS5);
/* 375 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CS6, CLibrary.CS6);
/* 376 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CS7, CLibrary.CS7);
/* 377 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CS8, CLibrary.CS8);
/* 378 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CSTOPB, CLibrary.CSTOPB);
/* 379 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CREAD, CLibrary.CREAD);
/* 380 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.PARENB, CLibrary.PARENB);
/* 381 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.PARODD, CLibrary.PARODD);
/* 382 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.HUPCL, CLibrary.HUPCL);
/* 383 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CLOCAL, CLibrary.CLOCAL);
/* 384 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CCTS_OFLOW, CLibrary.CCTS_OFLOW);
/* 385 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CRTS_IFLOW, CLibrary.CRTS_IFLOW);
/* 386 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CDSR_OFLOW, CLibrary.CDSR_OFLOW);
/* 387 */     addFlag(c_cflag, cflag, Attributes.ControlFlag.CCAR_OFLOW, CLibrary.CCAR_OFLOW);
/*     */     
/* 389 */     long c_lflag = c_lflag();
/* 390 */     EnumSet<Attributes.LocalFlag> lflag = attr.getLocalFlags();
/* 391 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOKE, CLibrary.ECHOKE);
/* 392 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOE, CLibrary.ECHOE);
/* 393 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOK, CLibrary.ECHOK);
/* 394 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHO, CLibrary.ECHO);
/* 395 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHONL, CLibrary.ECHONL);
/* 396 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOPRT, CLibrary.ECHOPRT);
/* 397 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ECHOCTL, CLibrary.ECHOCTL);
/* 398 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ISIG, CLibrary.ISIG);
/* 399 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ICANON, CLibrary.ICANON);
/* 400 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.ALTWERASE, CLibrary.ALTWERASE);
/* 401 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.IEXTEN, CLibrary.IEXTEN);
/* 402 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.EXTPROC, CLibrary.EXTPROC);
/* 403 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.TOSTOP, CLibrary.TOSTOP);
/* 404 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.FLUSHO, CLibrary.FLUSHO);
/* 405 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.NOKERNINFO, CLibrary.NOKERNINFO);
/* 406 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.PENDIN, CLibrary.PENDIN);
/* 407 */     addFlag(c_lflag, lflag, Attributes.LocalFlag.NOFLSH, CLibrary.NOFLSH);
/*     */     
/* 409 */     byte[] c_cc = c_cc().toArray(ValueLayout.JAVA_BYTE);
/* 410 */     EnumMap<Attributes.ControlChar, Integer> cc = attr.getControlChars();
/* 411 */     cc.put(Attributes.ControlChar.VEOF, Integer.valueOf(c_cc[CLibrary.VEOF]));
/* 412 */     cc.put(Attributes.ControlChar.VEOL, Integer.valueOf(c_cc[CLibrary.VEOL]));
/* 413 */     cc.put(Attributes.ControlChar.VEOL2, Integer.valueOf(c_cc[CLibrary.VEOL2]));
/* 414 */     cc.put(Attributes.ControlChar.VERASE, Integer.valueOf(c_cc[CLibrary.VERASE]));
/* 415 */     cc.put(Attributes.ControlChar.VWERASE, Integer.valueOf(c_cc[CLibrary.VWERASE]));
/* 416 */     cc.put(Attributes.ControlChar.VKILL, Integer.valueOf(c_cc[CLibrary.VKILL]));
/* 417 */     cc.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(c_cc[CLibrary.VREPRINT]));
/* 418 */     cc.put(Attributes.ControlChar.VINTR, Integer.valueOf(c_cc[CLibrary.VINTR]));
/* 419 */     cc.put(Attributes.ControlChar.VQUIT, Integer.valueOf(c_cc[CLibrary.VQUIT]));
/* 420 */     cc.put(Attributes.ControlChar.VSUSP, Integer.valueOf(c_cc[CLibrary.VSUSP]));
/* 421 */     if (CLibrary.VDSUSP != -1) {
/* 422 */       cc.put(Attributes.ControlChar.VDSUSP, Integer.valueOf(c_cc[CLibrary.VDSUSP]));
/*     */     }
/* 424 */     cc.put(Attributes.ControlChar.VSTART, Integer.valueOf(c_cc[CLibrary.VSTART]));
/* 425 */     cc.put(Attributes.ControlChar.VSTOP, Integer.valueOf(c_cc[CLibrary.VSTOP]));
/* 426 */     cc.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(c_cc[CLibrary.VLNEXT]));
/* 427 */     cc.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(c_cc[CLibrary.VDISCARD]));
/* 428 */     cc.put(Attributes.ControlChar.VMIN, Integer.valueOf(c_cc[CLibrary.VMIN]));
/* 429 */     cc.put(Attributes.ControlChar.VTIME, Integer.valueOf(c_cc[CLibrary.VTIME]));
/* 430 */     if (CLibrary.VSTATUS != -1) {
/* 431 */       cc.put(Attributes.ControlChar.VSTATUS, Integer.valueOf(c_cc[CLibrary.VSTATUS]));
/*     */     }
/*     */     
/* 434 */     return attr;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\ffm\CLibrary$termios.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */