/*     */ package org.jline.terminal.impl.exec;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.impl.AbstractPty;
/*     */ import org.jline.terminal.spi.Pty;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ import org.jline.utils.ExecHelper;
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
/*     */ public class ExecPty
/*     */   extends AbstractPty
/*     */   implements Pty
/*     */ {
/*     */   private final String name;
/*     */   
/*     */   public static Pty current(TerminalProvider provider, SystemStream systemStream) throws IOException {
/*     */     try {
/*  87 */       String result = ExecHelper.exec(true, new String[] { OSUtils.TTY_COMMAND });
/*  88 */       if (systemStream != SystemStream.Output && systemStream != SystemStream.Error) {
/*  89 */         throw new IllegalArgumentException("systemStream should be Output or Error: " + systemStream);
/*     */       }
/*  91 */       return new ExecPty(provider, systemStream, result.trim());
/*  92 */     } catch (IOException e) {
/*  93 */       throw new IOException("Not a tty", e);
/*     */     } 
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
/*     */   protected ExecPty(TerminalProvider provider, SystemStream systemStream, String name) {
/* 111 */     super(provider, systemStream);
/* 112 */     this.name = name;
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
/*     */   public void close() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 141 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getMasterInput() {
/* 146 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream getMasterOutput() {
/* 151 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected InputStream doGetSlaveInput() throws IOException {
/* 156 */     return (this.systemStream != null) ? new FileInputStream(FileDescriptor.in) : new FileInputStream(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream getSlaveOutput() throws IOException {
/* 161 */     return (this.systemStream == SystemStream.Output) ? 
/* 162 */       new FileOutputStream(FileDescriptor.out) : (
/* 163 */       (this.systemStream == SystemStream.Error) ? 
/* 164 */       new FileOutputStream(FileDescriptor.err) : 
/* 165 */       new FileOutputStream(getName()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttr() throws IOException {
/* 170 */     String cfg = doGetConfig();
/* 171 */     return doGetAttr(cfg);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetAttr(Attributes attr) throws IOException {
/* 176 */     List<String> commands = getFlagsToSet(attr, getAttr());
/* 177 */     if (!commands.isEmpty()) {
/* 178 */       commands.add(0, OSUtils.STTY_COMMAND);
/* 179 */       if (this.systemStream == null) {
/* 180 */         commands.add(1, OSUtils.STTY_F_OPTION);
/* 181 */         commands.add(2, getName());
/*     */       } 
/*     */       try {
/* 184 */         ExecHelper.exec((this.systemStream != null), commands.<String>toArray(new String[0]));
/* 185 */       } catch (IOException e) {
/*     */         
/* 187 */         if (e.toString().contains("unable to perform all requested operations")) {
/* 188 */           commands = getFlagsToSet(attr, getAttr());
/* 189 */           if (!commands.isEmpty()) {
/* 190 */             throw new IOException("Could not set the following flags: " + String.join(", ", commands), e);
/*     */           }
/*     */         } else {
/* 193 */           throw e;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List<String> getFlagsToSet(Attributes attr, Attributes current) {
/* 200 */     List<String> commands = new ArrayList<>();
/* 201 */     for (Attributes.InputFlag flag : Attributes.InputFlag.values()) {
/* 202 */       if (attr.getInputFlag(flag) != current.getInputFlag(flag) && flag != Attributes.InputFlag.INORMEOL) {
/* 203 */         commands.add((attr.getInputFlag(flag) ? flag.name() : ("-" + flag.name())).toLowerCase());
/*     */       }
/*     */     } 
/* 206 */     for (Attributes.OutputFlag flag : Attributes.OutputFlag.values()) {
/* 207 */       if (attr.getOutputFlag(flag) != current.getOutputFlag(flag)) {
/* 208 */         commands.add((attr.getOutputFlag(flag) ? flag.name() : ("-" + flag.name())).toLowerCase());
/*     */       }
/*     */     } 
/* 211 */     for (Attributes.ControlFlag flag : Attributes.ControlFlag.values()) {
/* 212 */       if (attr.getControlFlag(flag) != current.getControlFlag(flag)) {
/* 213 */         commands.add((attr.getControlFlag(flag) ? flag.name() : ("-" + flag.name())).toLowerCase());
/*     */       }
/*     */     } 
/* 216 */     for (Attributes.LocalFlag flag : Attributes.LocalFlag.values()) {
/* 217 */       if (attr.getLocalFlag(flag) != current.getLocalFlag(flag)) {
/* 218 */         commands.add((attr.getLocalFlag(flag) ? flag.name() : ("-" + flag.name())).toLowerCase());
/*     */       }
/*     */     } 
/* 221 */     String undef = System.getProperty("os.name").toLowerCase().startsWith("hp") ? "^-" : "undef";
/* 222 */     for (Attributes.ControlChar cchar : Attributes.ControlChar.values()) {
/* 223 */       int v = attr.getControlChar(cchar);
/* 224 */       if (v >= 0 && v != current.getControlChar(cchar)) {
/* 225 */         String str = "";
/* 226 */         commands.add(cchar.name().toLowerCase().substring(1));
/* 227 */         if (cchar == Attributes.ControlChar.VMIN || cchar == Attributes.ControlChar.VTIME) {
/* 228 */           commands.add(Integer.toString(v));
/* 229 */         } else if (v == 0) {
/* 230 */           commands.add(undef);
/*     */         } else {
/* 232 */           if (v >= 128) {
/* 233 */             v -= 128;
/* 234 */             str = str + "M-";
/*     */           } 
/* 236 */           if (v < 32 || v == 127) {
/* 237 */             v ^= 0x40;
/* 238 */             str = str + "^";
/*     */           } 
/* 240 */           str = str + (char)v;
/* 241 */           commands.add(str);
/*     */         } 
/*     */       } 
/*     */     } 
/* 245 */     return commands;
/*     */   }
/*     */ 
/*     */   
/*     */   public Size getSize() throws IOException {
/* 250 */     String cfg = doGetConfig();
/* 251 */     return doGetSize(cfg);
/*     */   }
/*     */   
/*     */   protected String doGetConfig() throws IOException {
/* 255 */     return (this.systemStream != null) ? 
/* 256 */       ExecHelper.exec(true, new String[] { OSUtils.STTY_COMMAND, "-a"
/* 257 */         }) : ExecHelper.exec(false, new String[] { OSUtils.STTY_COMMAND, OSUtils.STTY_F_OPTION, getName(), "-a" });
/*     */   }
/*     */   
/*     */   public static Attributes doGetAttr(String cfg) throws IOException {
/* 261 */     Attributes attributes = new Attributes();
/* 262 */     for (Attributes.InputFlag flag : Attributes.InputFlag.values()) {
/* 263 */       Boolean value = doGetFlag(cfg, (Enum<?>)flag);
/* 264 */       if (value != null) {
/* 265 */         attributes.setInputFlag(flag, value.booleanValue());
/*     */       }
/*     */     } 
/* 268 */     for (Attributes.OutputFlag flag : Attributes.OutputFlag.values()) {
/* 269 */       Boolean value = doGetFlag(cfg, (Enum<?>)flag);
/* 270 */       if (value != null) {
/* 271 */         attributes.setOutputFlag(flag, value.booleanValue());
/*     */       }
/*     */     } 
/* 274 */     for (Attributes.ControlFlag flag : Attributes.ControlFlag.values()) {
/* 275 */       Boolean value = doGetFlag(cfg, (Enum<?>)flag);
/* 276 */       if (value != null) {
/* 277 */         attributes.setControlFlag(flag, value.booleanValue());
/*     */       }
/*     */     } 
/* 280 */     for (Attributes.LocalFlag flag : Attributes.LocalFlag.values()) {
/* 281 */       Boolean value = doGetFlag(cfg, (Enum<?>)flag);
/* 282 */       if (value != null) {
/* 283 */         attributes.setLocalFlag(flag, value.booleanValue());
/*     */       }
/*     */     } 
/* 286 */     for (Attributes.ControlChar cchar : Attributes.ControlChar.values()) {
/* 287 */       String name = cchar.name().toLowerCase().substring(1);
/* 288 */       if ("reprint".endsWith(name)) {
/* 289 */         name = "(?:reprint|rprnt)";
/*     */       }
/*     */       
/* 292 */       Matcher matcher = Pattern.compile("[\\s;]" + name + "\\s*=\\s*(.+?)[\\s;]").matcher(cfg);
/* 293 */       if (matcher.find()) {
/* 294 */         attributes.setControlChar(cchar, 
/* 295 */             parseControlChar(matcher.group(1).toUpperCase()));
/*     */       }
/*     */     } 
/* 298 */     return attributes;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Boolean doGetFlag(String cfg, Enum<?> flag) {
/* 303 */     Matcher matcher = Pattern.compile("(?:^|[\\s;])(\\-?" + flag.name().toLowerCase() + ")(?:[\\s;]|$)").matcher(cfg);
/* 304 */     return matcher.find() ? Boolean.valueOf(!matcher.group(1).startsWith("-")) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   static int parseControlChar(String str) {
/* 309 */     if ("<UNDEF>".equals(str)) {
/* 310 */       return -1;
/*     */     }
/*     */     
/* 313 */     if ("DEL".equalsIgnoreCase(str)) {
/* 314 */       return 127;
/*     */     }
/*     */     
/* 317 */     if (str.charAt(0) == '0') {
/* 318 */       return Integer.parseInt(str, 8);
/*     */     }
/*     */     
/* 321 */     if (str.charAt(0) >= '1' && str.charAt(0) <= '9') {
/* 322 */       return Integer.parseInt(str, 10);
/*     */     }
/*     */     
/* 325 */     if (str.charAt(0) == '^') {
/* 326 */       if (str.charAt(1) == '?') {
/* 327 */         return 127;
/*     */       }
/* 329 */       return str.charAt(1) - 64;
/*     */     } 
/* 331 */     if (str.charAt(0) == 'M' && str.charAt(1) == '-') {
/* 332 */       if (str.charAt(2) == '^') {
/* 333 */         if (str.charAt(3) == '?') {
/* 334 */           return 255;
/*     */         }
/* 336 */         return str.charAt(3) - 64 + 128;
/*     */       } 
/*     */       
/* 339 */       return str.charAt(2) + 128;
/*     */     } 
/*     */     
/* 342 */     return str.charAt(0);
/*     */   }
/*     */ 
/*     */   
/*     */   static Size doGetSize(String cfg) throws IOException {
/* 347 */     return new Size(doGetInt("columns", cfg), doGetInt("rows", cfg));
/*     */   }
/*     */   
/*     */   static int doGetInt(String name, String cfg) throws IOException {
/* 351 */     String[] patterns = { "\\b([0-9]+)\\s+" + name + "\\b", "\\b" + name + "\\s+([0-9]+)\\b", "\\b" + name + "\\s*=\\s*([0-9]+)\\b" };
/*     */ 
/*     */     
/* 354 */     for (String pattern : patterns) {
/* 355 */       Matcher matcher = Pattern.compile(pattern).matcher(cfg);
/* 356 */       if (matcher.find()) {
/* 357 */         return Integer.parseInt(matcher.group(1));
/*     */       }
/*     */     } 
/* 360 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(Size size) throws IOException {
/* 365 */     if (this.systemStream != null) {
/* 366 */       ExecHelper.exec(true, new String[] { OSUtils.STTY_COMMAND, "columns", 
/*     */ 
/*     */ 
/*     */             
/* 370 */             Integer.toString(size.getColumns()), "rows", 
/*     */             
/* 372 */             Integer.toString(size.getRows()) });
/*     */     } else {
/* 374 */       ExecHelper.exec(false, new String[] { OSUtils.STTY_COMMAND, OSUtils.STTY_F_OPTION, 
/*     */ 
/*     */ 
/*     */             
/* 378 */             getName(), "columns", 
/*     */             
/* 380 */             Integer.toString(size.getColumns()), "rows", 
/*     */             
/* 382 */             Integer.toString(size.getRows()) });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 388 */     return "ExecPty[" + getName() + ((this.systemStream != null) ? ", system]" : "]");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\exec\ExecPty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */