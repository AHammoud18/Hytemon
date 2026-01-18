/*     */ package org.jline.builtins.ssh;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import org.apache.sshd.client.SshClient;
/*     */ import org.apache.sshd.client.auth.keyboard.UserInteraction;
/*     */ import org.apache.sshd.client.channel.ChannelShell;
/*     */ import org.apache.sshd.client.channel.ClientChannel;
/*     */ import org.apache.sshd.client.channel.ClientChannelEvent;
/*     */ import org.apache.sshd.client.future.ConnectFuture;
/*     */ import org.apache.sshd.client.session.ClientSession;
/*     */ import org.apache.sshd.common.NamedResource;
/*     */ import org.apache.sshd.common.channel.PtyMode;
/*     */ import org.apache.sshd.common.config.keys.FilePasswordProvider;
/*     */ import org.apache.sshd.common.session.SessionContext;
/*     */ import org.apache.sshd.common.util.io.input.NoCloseInputStream;
/*     */ import org.apache.sshd.common.util.io.output.NoCloseOutputStream;
/*     */ import org.apache.sshd.scp.server.ScpCommandFactory;
/*     */ import org.apache.sshd.server.SshServer;
/*     */ import org.apache.sshd.server.channel.ChannelSession;
/*     */ import org.apache.sshd.server.command.Command;
/*     */ import org.apache.sshd.server.command.CommandFactory;
/*     */ import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
/*     */ import org.apache.sshd.server.session.ServerSession;
/*     */ import org.apache.sshd.sftp.server.SftpSubsystemFactory;
/*     */ import org.jline.builtins.Options;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ 
/*     */ public class Ssh {
/*  43 */   public static final String[] functions = new String[] { "ssh", "sshd" }; private static final int defaultPort = 2022; private final Consumer<ShellParams> shell; private final Consumer<ExecuteParams> execute; private final Supplier<SshServer> serverBuilder; private final Supplier<SshClient> clientBuilder; private SshServer server; private int port;
/*     */   private String ip;
/*     */   
/*     */   public static class ShellParams { private final Map<String, String> env;
/*     */     private final Terminal terminal;
/*     */     private final Runnable closer;
/*     */     private final ServerSession session;
/*     */     
/*     */     public ShellParams(Map<String, String> env, ServerSession session, Terminal terminal, Runnable closer) {
/*  52 */       this.env = env;
/*  53 */       this.session = session;
/*  54 */       this.terminal = terminal;
/*  55 */       this.closer = closer;
/*     */     }
/*     */     
/*     */     public Map<String, String> getEnv() {
/*  59 */       return this.env;
/*     */     }
/*     */     
/*     */     public ServerSession getSession() {
/*  63 */       return this.session;
/*     */     }
/*     */     
/*     */     public Terminal getTerminal() {
/*  67 */       return this.terminal;
/*     */     }
/*     */     
/*     */     public Runnable getCloser() {
/*  71 */       return this.closer;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ExecuteParams
/*     */   {
/*     */     private final String command;
/*     */     
/*     */     private final Map<String, String> env;
/*     */     
/*     */     private final ServerSession session;
/*     */     
/*     */     private final InputStream in;
/*     */     
/*     */     private final OutputStream out;
/*     */     private final OutputStream err;
/*     */     
/*     */     public ExecuteParams(String command, Map<String, String> env, ServerSession session, InputStream in, OutputStream out, OutputStream err) {
/*  90 */       this.command = command;
/*  91 */       this.session = session;
/*  92 */       this.env = env;
/*  93 */       this.in = in;
/*  94 */       this.out = out;
/*  95 */       this.err = err;
/*     */     }
/*     */     
/*     */     public String getCommand() {
/*  99 */       return this.command;
/*     */     }
/*     */     
/*     */     public Map<String, String> getEnv() {
/* 103 */       return this.env;
/*     */     }
/*     */     
/*     */     public ServerSession getSession() {
/* 107 */       return this.session;
/*     */     }
/*     */     
/*     */     public InputStream getIn() {
/* 111 */       return this.in;
/*     */     }
/*     */     
/*     */     public OutputStream getOut() {
/* 115 */       return this.out;
/*     */     }
/*     */     
/*     */     public OutputStream getErr() {
/* 119 */       return this.err;
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
/*     */   
/*     */   public Ssh(Consumer<ShellParams> shell, Consumer<ExecuteParams> execute, Supplier<SshServer> serverBuilder, Supplier<SshClient> clientBuilder) {
/* 138 */     this.shell = shell;
/* 139 */     this.execute = execute;
/* 140 */     this.serverBuilder = serverBuilder;
/* 141 */     this.clientBuilder = clientBuilder;
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
/*     */   public void ssh(Terminal terminal, LineReader reader, String user, InputStream stdin, PrintStream stdout, PrintStream stderr, String[] argv) throws Exception {
/* 153 */     String[] usage = { "ssh - connect to a server using ssh", "Usage: ssh [user@]hostname [command]", "  -? --help                show help" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     Options opt = Options.compile(usage).parse((Object[])argv, true);
/* 160 */     List<String> args = opt.args();
/*     */     
/* 162 */     if (opt.isSet("help") || args.isEmpty()) {
/* 163 */       throw new Options.HelpException(opt.usage());
/*     */     }
/*     */     
/* 166 */     String username = user;
/* 167 */     String hostname = args.remove(0);
/* 168 */     int port = this.port;
/* 169 */     String command = null;
/* 170 */     int idx = hostname.indexOf('@');
/* 171 */     if (idx >= 0) {
/* 172 */       username = hostname.substring(0, idx);
/* 173 */       hostname = hostname.substring(idx + 1);
/*     */     } 
/* 175 */     idx = hostname.indexOf(':');
/* 176 */     if (idx >= 0) {
/* 177 */       port = Integer.parseInt(hostname.substring(idx + 1));
/* 178 */       hostname = hostname.substring(0, idx);
/*     */     } 
/* 180 */     if (!args.isEmpty()) {
/* 181 */       command = String.join(" ", (Iterable)args);
/*     */     }
/*     */     
/* 184 */     SshClient client = this.clientBuilder.get(); 
/* 185 */     try { JLineUserInteraction ui = new JLineUserInteraction(terminal, reader, stderr);
/* 186 */       client.setFilePasswordProvider(ui);
/* 187 */       client.setUserInteraction(ui);
/* 188 */       client.start();
/*     */ 
/*     */       
/* 191 */       ClientSession sshSession = connectWithRetries(terminal.writer(), client, username, hostname, port, 3); 
/* 192 */       try { sshSession.auth().verify();
/* 193 */         if (command != null) {
/* 194 */           ClientChannel channel = sshSession.createChannel("exec", command + "\n");
/* 195 */           channel.setIn(new ByteArrayInputStream(new byte[0]));
/* 196 */           channel.setOut((OutputStream)new NoCloseOutputStream(stdout));
/* 197 */           channel.setErr((OutputStream)new NoCloseOutputStream(stderr));
/* 198 */           channel.open().verify();
/* 199 */           channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
/*     */         } else {
/* 201 */           ChannelShell channel = sshSession.createShellChannel();
/* 202 */           Attributes attributes = terminal.enterRawMode();
/*     */           try {
/* 204 */             Map<PtyMode, Integer> modes = new HashMap<>();
/*     */             
/* 206 */             setMode(modes, PtyMode.VINTR, attributes.getControlChar(Attributes.ControlChar.VINTR));
/* 207 */             setMode(modes, PtyMode.VQUIT, attributes.getControlChar(Attributes.ControlChar.VQUIT));
/* 208 */             setMode(modes, PtyMode.VERASE, attributes.getControlChar(Attributes.ControlChar.VERASE));
/* 209 */             setMode(modes, PtyMode.VKILL, attributes.getControlChar(Attributes.ControlChar.VKILL));
/* 210 */             setMode(modes, PtyMode.VEOF, attributes.getControlChar(Attributes.ControlChar.VEOF));
/* 211 */             setMode(modes, PtyMode.VEOL, attributes.getControlChar(Attributes.ControlChar.VEOL));
/* 212 */             setMode(modes, PtyMode.VEOL2, attributes.getControlChar(Attributes.ControlChar.VEOL2));
/* 213 */             setMode(modes, PtyMode.VSTART, attributes.getControlChar(Attributes.ControlChar.VSTART));
/* 214 */             setMode(modes, PtyMode.VSTOP, attributes.getControlChar(Attributes.ControlChar.VSTOP));
/* 215 */             setMode(modes, PtyMode.VSUSP, attributes.getControlChar(Attributes.ControlChar.VSUSP));
/* 216 */             setMode(modes, PtyMode.VDSUSP, attributes.getControlChar(Attributes.ControlChar.VDSUSP));
/* 217 */             setMode(modes, PtyMode.VREPRINT, attributes.getControlChar(Attributes.ControlChar.VREPRINT));
/* 218 */             setMode(modes, PtyMode.VWERASE, attributes.getControlChar(Attributes.ControlChar.VWERASE));
/* 219 */             setMode(modes, PtyMode.VLNEXT, attributes.getControlChar(Attributes.ControlChar.VLNEXT));
/* 220 */             setMode(modes, PtyMode.VSTATUS, attributes.getControlChar(Attributes.ControlChar.VSTATUS));
/* 221 */             setMode(modes, PtyMode.VDISCARD, attributes.getControlChar(Attributes.ControlChar.VDISCARD));
/*     */             
/* 223 */             setMode(modes, PtyMode.IGNPAR, getFlag(attributes, Attributes.InputFlag.IGNPAR));
/* 224 */             setMode(modes, PtyMode.PARMRK, getFlag(attributes, Attributes.InputFlag.PARMRK));
/* 225 */             setMode(modes, PtyMode.INPCK, getFlag(attributes, Attributes.InputFlag.INPCK));
/* 226 */             setMode(modes, PtyMode.ISTRIP, getFlag(attributes, Attributes.InputFlag.ISTRIP));
/* 227 */             setMode(modes, PtyMode.INLCR, getFlag(attributes, Attributes.InputFlag.INLCR));
/* 228 */             setMode(modes, PtyMode.IGNCR, getFlag(attributes, Attributes.InputFlag.IGNCR));
/* 229 */             setMode(modes, PtyMode.ICRNL, getFlag(attributes, Attributes.InputFlag.ICRNL));
/* 230 */             setMode(modes, PtyMode.IXON, getFlag(attributes, Attributes.InputFlag.IXON));
/* 231 */             setMode(modes, PtyMode.IXANY, getFlag(attributes, Attributes.InputFlag.IXANY));
/* 232 */             setMode(modes, PtyMode.IXOFF, getFlag(attributes, Attributes.InputFlag.IXOFF));
/*     */             
/* 234 */             setMode(modes, PtyMode.ISIG, getFlag(attributes, Attributes.LocalFlag.ISIG));
/* 235 */             setMode(modes, PtyMode.ICANON, getFlag(attributes, Attributes.LocalFlag.ICANON));
/* 236 */             setMode(modes, PtyMode.ECHO, getFlag(attributes, Attributes.LocalFlag.ECHO));
/* 237 */             setMode(modes, PtyMode.ECHOE, getFlag(attributes, Attributes.LocalFlag.ECHOE));
/* 238 */             setMode(modes, PtyMode.ECHOK, getFlag(attributes, Attributes.LocalFlag.ECHOK));
/* 239 */             setMode(modes, PtyMode.ECHONL, getFlag(attributes, Attributes.LocalFlag.ECHONL));
/* 240 */             setMode(modes, PtyMode.NOFLSH, getFlag(attributes, Attributes.LocalFlag.NOFLSH));
/* 241 */             setMode(modes, PtyMode.TOSTOP, getFlag(attributes, Attributes.LocalFlag.TOSTOP));
/* 242 */             setMode(modes, PtyMode.IEXTEN, getFlag(attributes, Attributes.LocalFlag.IEXTEN));
/*     */             
/* 244 */             setMode(modes, PtyMode.OPOST, getFlag(attributes, Attributes.OutputFlag.OPOST));
/* 245 */             setMode(modes, PtyMode.ONLCR, getFlag(attributes, Attributes.OutputFlag.ONLCR));
/* 246 */             setMode(modes, PtyMode.OCRNL, getFlag(attributes, Attributes.OutputFlag.OCRNL));
/* 247 */             setMode(modes, PtyMode.ONOCR, getFlag(attributes, Attributes.OutputFlag.ONOCR));
/* 248 */             setMode(modes, PtyMode.ONLRET, getFlag(attributes, Attributes.OutputFlag.ONLRET));
/* 249 */             channel.setPtyModes(modes);
/* 250 */             channel.setPtyColumns(terminal.getWidth());
/* 251 */             channel.setPtyLines(terminal.getHeight());
/* 252 */             channel.setAgentForwarding(true);
/* 253 */             channel.setEnv("TERM", terminal.getType());
/*     */             
/* 255 */             channel.setIn((InputStream)new NoCloseInputStream(stdin));
/* 256 */             channel.setOut((OutputStream)new NoCloseOutputStream(stdout));
/* 257 */             channel.setErr((OutputStream)new NoCloseOutputStream(stderr));
/* 258 */             channel.open().verify();
/* 259 */             Terminal.SignalHandler prevWinchHandler = terminal.handle(Terminal.Signal.WINCH, signal -> {
/*     */                   try {
/*     */                     Size size = terminal.getSize();
/*     */                     channel.sendWindowChange(size.getColumns(), size.getRows());
/* 263 */                   } catch (IOException iOException) {}
/*     */                 });
/*     */ 
/*     */             
/* 267 */             Terminal.SignalHandler prevQuitHandler = terminal.handle(Terminal.Signal.QUIT, signal -> {
/*     */                   try {
/*     */                     channel.getInvertedIn().write(attributes.getControlChar(Attributes.ControlChar.VQUIT));
/*     */                     channel.getInvertedIn().flush();
/* 271 */                   } catch (IOException iOException) {}
/*     */                 });
/*     */ 
/*     */             
/* 275 */             Terminal.SignalHandler prevIntHandler = terminal.handle(Terminal.Signal.INT, signal -> {
/*     */                   try {
/*     */                     channel.getInvertedIn().write(attributes.getControlChar(Attributes.ControlChar.VINTR));
/*     */                     channel.getInvertedIn().flush();
/* 279 */                   } catch (IOException iOException) {}
/*     */                 });
/*     */ 
/*     */             
/* 283 */             Terminal.SignalHandler prevStopHandler = terminal.handle(Terminal.Signal.TSTP, signal -> {
/*     */                   try {
/*     */                     channel.getInvertedIn().write(attributes.getControlChar(Attributes.ControlChar.VDSUSP));
/*     */                     channel.getInvertedIn().flush();
/* 287 */                   } catch (IOException iOException) {}
/*     */                 });
/*     */ 
/*     */             
/*     */             try {
/* 292 */               channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
/*     */             } finally {
/* 294 */               terminal.handle(Terminal.Signal.WINCH, prevWinchHandler);
/* 295 */               terminal.handle(Terminal.Signal.INT, prevIntHandler);
/* 296 */               terminal.handle(Terminal.Signal.TSTP, prevStopHandler);
/* 297 */               terminal.handle(Terminal.Signal.QUIT, prevQuitHandler);
/*     */             } 
/*     */           } finally {
/* 300 */             terminal.setAttributes(attributes);
/*     */           } 
/*     */         } 
/* 303 */         if (sshSession != null) sshSession.close();  } catch (Throwable throwable) { if (sshSession != null)
/* 304 */           try { sshSession.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (client != null) client.close();  } catch (Throwable throwable) { if (client != null)
/*     */         try { client.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 308 */      } private static void setMode(Map<PtyMode, Integer> modes, PtyMode vintr, int attributes) { if (attributes >= 0) {
/* 309 */       modes.put(vintr, Integer.valueOf(attributes));
/*     */     } }
/*     */ 
/*     */   
/*     */   private static int getFlag(Attributes attributes, Attributes.InputFlag flag) {
/* 314 */     return attributes.getInputFlag(flag) ? 1 : 0;
/*     */   }
/*     */   
/*     */   private static int getFlag(Attributes attributes, Attributes.OutputFlag flag) {
/* 318 */     return attributes.getOutputFlag(flag) ? 1 : 0;
/*     */   }
/*     */   
/*     */   private static int getFlag(Attributes attributes, Attributes.LocalFlag flag) {
/* 322 */     return attributes.getLocalFlag(flag) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientSession connectWithRetries(PrintWriter stdout, SshClient client, String username, String host, int port, int maxAttempts) throws Exception {
/* 328 */     ClientSession session = null;
/* 329 */     int retries = 0;
/*     */     while (true) {
/* 331 */       ConnectFuture future = client.connect(username, host, port);
/* 332 */       future.await();
/*     */       try {
/* 334 */         session = (ClientSession)future.getSession();
/* 335 */       } catch (Exception ex) {
/* 336 */         if (retries++ < maxAttempts) {
/* 337 */           Thread.sleep(2000L);
/* 338 */           stdout.println("retrying (attempt " + retries + ") ...");
/*     */         } else {
/* 340 */           throw ex;
/*     */         } 
/*     */       } 
/* 343 */       if (session != null)
/* 344 */         return session; 
/*     */     } 
/*     */   }
/*     */   public void sshd(PrintStream stdout, PrintStream stderr, String[] argv) throws Exception {
/* 348 */     String[] usage = { "sshd - start an ssh server", "Usage: sshd [-i ip] [-p port] start | stop | status", "  -i --ip=INTERFACE        listen interface (default=127.0.0.1)", "  -p --port=PORT           listen port (default=2022)", "  -? --help                show help" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     Options opt = Options.compile(usage).parse((Object[])argv, true);
/* 357 */     List<String> args = opt.args();
/*     */     
/* 359 */     if (opt.isSet("help") || args.isEmpty()) {
/* 360 */       throw new Options.HelpException(opt.usage());
/*     */     }
/*     */     
/* 363 */     String command = args.get(0);
/*     */     
/* 365 */     if ("start".equals(command)) {
/* 366 */       if (this.server != null) {
/* 367 */         throw new IllegalStateException("sshd is already running on port " + this.port);
/*     */       }
/* 369 */       this.ip = opt.get("ip");
/* 370 */       this.port = opt.getNumber("port");
/* 371 */       start();
/* 372 */       status(stdout);
/* 373 */     } else if ("stop".equals(command)) {
/* 374 */       if (this.server == null) {
/* 375 */         throw new IllegalStateException("sshd is not running.");
/*     */       }
/* 377 */       stop();
/* 378 */     } else if ("status".equals(command)) {
/* 379 */       status(stdout);
/*     */     } else {
/* 381 */       throw opt.usageError("bad command: " + command);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void status(PrintStream stdout) {
/* 386 */     if (this.server != null) {
/* 387 */       stdout.println("sshd is running on " + this.ip + ":" + this.port);
/*     */     } else {
/* 389 */       stdout.println("sshd is not running.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void start() throws IOException {
/* 394 */     this.server = this.serverBuilder.get();
/* 395 */     this.server.setPort(this.port);
/* 396 */     this.server.setHost(this.ip);
/* 397 */     this.server.setShellFactory(new ShellFactoryImpl(this.shell));
/* 398 */     this.server.setCommandFactory((CommandFactory)(new ScpCommandFactory.Builder())
/* 399 */         .withDelegate((channel, command) -> new ShellCommand(this.execute, command))
/* 400 */         .build());
/* 401 */     this.server.setSubsystemFactories(Collections.singletonList((new SftpSubsystemFactory.Builder()).build()));
/* 402 */     this.server.setKeyPairProvider((KeyPairProvider)new SimpleGeneratorHostKeyProvider());
/* 403 */     this.server.start();
/*     */   }
/*     */   
/*     */   private void stop() throws IOException {
/*     */     try {
/* 408 */       this.server.stop();
/*     */     } finally {
/* 410 */       this.server = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class JLineUserInteraction implements UserInteraction, FilePasswordProvider {
/*     */     private final Terminal terminal;
/*     */     private final LineReader reader;
/*     */     private final PrintStream stderr;
/*     */     
/*     */     public JLineUserInteraction(Terminal terminal, LineReader reader, PrintStream stderr) {
/* 420 */       this.terminal = terminal;
/* 421 */       this.reader = reader;
/* 422 */       this.stderr = stderr;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPassword(SessionContext session, NamedResource resourceKey, int retryIndex) throws IOException {
/* 428 */       return readLine("Enter password for " + resourceKey + ":", false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void welcome(ClientSession session, String banner, String lang) {
/* 433 */       this.terminal.writer().println(banner);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String[] interactive(ClientSession s, String name, String instruction, String lang, String[] prompt, boolean[] echo) {
/* 439 */       String[] answers = new String[prompt.length];
/*     */       try {
/* 441 */         for (int i = 0; i < prompt.length; i++) {
/* 442 */           answers[i] = readLine(prompt[i], echo[i]);
/*     */         }
/* 444 */       } catch (Exception e) {
/* 445 */         this.stderr.append(e.getClass().getSimpleName())
/* 446 */           .append(" while read prompts: ")
/* 447 */           .println(e.getMessage());
/*     */       } 
/* 449 */       return answers;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isInteractionAllowed(ClientSession session) {
/* 454 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void serverVersionInfo(ClientSession session, List<String> lines) {
/* 459 */       for (String l : lines) {
/* 460 */         this.terminal.writer().append('\t').println(l);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String getUpdatedPassword(ClientSession session, String prompt, String lang) {
/*     */       try {
/* 467 */         return readLine(prompt, false);
/* 468 */       } catch (Exception e) {
/* 469 */         this.stderr.append(e.getClass().getSimpleName())
/* 470 */           .append(" while reading password: ")
/* 471 */           .println(e.getMessage());
/*     */         
/* 473 */         return null;
/*     */       } 
/*     */     }
/*     */     private String readLine(String prompt, boolean echo) {
/* 477 */       return this.reader.readLine(prompt + " ", echo ? null : Character.valueOf(false));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\ssh\Ssh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */