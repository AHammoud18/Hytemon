/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.concurrent.ForkJoinTask;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
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
/*     */ public class Diag
/*     */ {
/*     */   private final PrintStream out;
/*     */   private final boolean verbose;
/*     */   
/*     */   public static void main(String[] args) {
/*  67 */     diag(System.out, Arrays.<String>asList(args).contains("--verbose"));
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
/*     */   public static void diag(PrintStream out) {
/*  82 */     diag(out, true);
/*     */   }
/*     */   
/*     */   public static void diag(PrintStream out, boolean verbose) {
/*  86 */     (new Diag(out, verbose)).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Diag(PrintStream out, boolean verbose) {
/*  93 */     this.out = out;
/*  94 */     this.verbose = verbose;
/*     */   }
/*     */   
/*     */   public void run() {
/*  98 */     this.out.println("System properties");
/*  99 */     this.out.println("=================");
/* 100 */     this.out.println("os.name =         " + System.getProperty("os.name"));
/* 101 */     this.out.println("OSTYPE =          " + System.getenv("OSTYPE"));
/* 102 */     this.out.println("MSYSTEM =         " + System.getenv("MSYSTEM"));
/* 103 */     this.out.println("PWD =             " + System.getenv("PWD"));
/* 104 */     this.out.println("ConEmuPID =       " + System.getenv("ConEmuPID"));
/* 105 */     this.out.println("WSL_DISTRO_NAME = " + System.getenv("WSL_DISTRO_NAME"));
/* 106 */     this.out.println("WSL_INTEROP =     " + System.getenv("WSL_INTEROP"));
/* 107 */     this.out.println();
/*     */     
/* 109 */     this.out.println("OSUtils");
/* 110 */     this.out.println("=================");
/* 111 */     this.out.println("IS_WINDOWS = " + OSUtils.IS_WINDOWS);
/* 112 */     this.out.println("IS_CYGWIN =  " + OSUtils.IS_CYGWIN);
/* 113 */     this.out.println("IS_MSYSTEM = " + OSUtils.IS_MSYSTEM);
/* 114 */     this.out.println("IS_WSL =     " + OSUtils.IS_WSL);
/* 115 */     this.out.println("IS_WSL1 =    " + OSUtils.IS_WSL1);
/* 116 */     this.out.println("IS_WSL2 =    " + OSUtils.IS_WSL2);
/* 117 */     this.out.println("IS_CONEMU =  " + OSUtils.IS_CONEMU);
/* 118 */     this.out.println("IS_OSX =     " + OSUtils.IS_OSX);
/* 119 */     this.out.println();
/*     */ 
/*     */     
/* 122 */     this.out.println("FFM Support");
/* 123 */     this.out.println("=================");
/*     */     try {
/* 125 */       TerminalProvider provider = TerminalProvider.load("ffm");
/* 126 */       testProvider(provider);
/* 127 */     } catch (Throwable t) {
/* 128 */       error("FFM support not available", t);
/*     */     } 
/* 130 */     this.out.println();
/*     */     
/* 132 */     this.out.println("JnaSupport");
/* 133 */     this.out.println("=================");
/*     */     try {
/* 135 */       TerminalProvider provider = TerminalProvider.load("jna");
/* 136 */       testProvider(provider);
/* 137 */     } catch (Throwable t) {
/* 138 */       error("JNA support not available", t);
/*     */     } 
/* 140 */     this.out.println();
/*     */     
/* 142 */     this.out.println("Jansi2Support");
/* 143 */     this.out.println("=================");
/*     */     try {
/* 145 */       TerminalProvider provider = TerminalProvider.load("jansi");
/* 146 */       testProvider(provider);
/* 147 */     } catch (Throwable t) {
/* 148 */       error("Jansi 2 support not available", t);
/*     */     } 
/* 150 */     this.out.println();
/*     */     
/* 152 */     this.out.println("JniSupport");
/* 153 */     this.out.println("=================");
/*     */     try {
/* 155 */       TerminalProvider provider = TerminalProvider.load("jni");
/* 156 */       testProvider(provider);
/* 157 */     } catch (Throwable t) {
/* 158 */       error("JNI support not available", t);
/*     */     } 
/* 160 */     this.out.println();
/*     */ 
/*     */     
/* 163 */     this.out.println("Exec Support");
/* 164 */     this.out.println("=================");
/*     */     try {
/* 166 */       TerminalProvider provider = TerminalProvider.load("exec");
/* 167 */       testProvider(provider);
/* 168 */     } catch (Throwable t) {
/* 169 */       error("Exec support not available", t);
/*     */     } 
/*     */     
/* 172 */     if (!this.verbose) {
/* 173 */       this.out.println();
/* 174 */       this.out.println("Run with --verbose argument to print stack traces");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void testProvider(TerminalProvider provider) {
/*     */     try {
/* 180 */       this.out.println("StdIn stream =    " + provider.isSystemStream(SystemStream.Input));
/* 181 */       this.out.println("StdOut stream =   " + provider.isSystemStream(SystemStream.Output));
/* 182 */       this.out.println("StdErr stream =   " + provider.isSystemStream(SystemStream.Error));
/* 183 */     } catch (Throwable t) {
/* 184 */       error("Unable to check stream", t);
/*     */     } 
/*     */     try {
/* 187 */       this.out.println("StdIn stream name =     " + provider.systemStreamName(SystemStream.Input));
/* 188 */       this.out.println("StdOut stream name =    " + provider.systemStreamName(SystemStream.Output));
/* 189 */       this.out.println("StdErr stream name =    " + provider.systemStreamName(SystemStream.Error));
/* 190 */     } catch (Throwable t) {
/* 191 */       error("Unable to check stream names", t);
/*     */     }  
/* 193 */     try { Terminal terminal = provider.sysTerminal("diag", "xterm", false, StandardCharsets.UTF_8, StandardCharsets.UTF_8, StandardCharsets.UTF_8, false, Terminal.SignalHandler.SIG_DFL, false, SystemStream.Output);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       try { if (terminal != null) {
/* 205 */           Attributes attr = terminal.enterRawMode();
/*     */           try {
/* 207 */             this.out.println("Terminal size: " + terminal.getSize());
/* 208 */             ForkJoinPool forkJoinPool = new ForkJoinPool(1);
/*     */             
/*     */             try {
/* 211 */               ForkJoinTask<Integer> t = forkJoinPool.submit(() -> Integer.valueOf(terminal.reader().read(1L)));
/* 212 */               t.get(1000L, TimeUnit.MILLISECONDS);
/*     */             } finally {
/* 214 */               forkJoinPool.shutdown();
/*     */             } 
/* 216 */             StringBuilder sb = new StringBuilder();
/* 217 */             sb.append("The terminal seems to work: ");
/* 218 */             sb.append("terminal ").append(terminal.getClass().getName());
/* 219 */             if (terminal instanceof AbstractPosixTerminal) {
/* 220 */               sb.append(" with pty ")
/* 221 */                 .append(((AbstractPosixTerminal)terminal)
/* 222 */                   .getPty()
/* 223 */                   .getClass()
/* 224 */                   .getName());
/*     */             }
/* 226 */             this.out.println(sb);
/* 227 */           } catch (Throwable t2) {
/* 228 */             error("Unable to read from terminal", t2);
/*     */           } finally {
/* 230 */             terminal.setAttributes(attr);
/*     */           } 
/*     */         } else {
/* 233 */           this.out.println("Not supported by provider");
/*     */         } 
/* 235 */         if (terminal != null) terminal.close();  } catch (Throwable throwable) { if (terminal != null) try { terminal.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable t)
/* 236 */     { error("Unable to open terminal", t); }
/*     */   
/*     */   }
/*     */   
/*     */   private void error(String message, Throwable cause) {
/* 241 */     if (this.verbose) {
/* 242 */       this.out.println(message);
/* 243 */       cause.printStackTrace(this.out);
/*     */     } else {
/* 245 */       this.out.println(message + ": " + cause);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\Diag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */