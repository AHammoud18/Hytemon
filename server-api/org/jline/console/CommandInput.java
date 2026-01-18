/*     */ package org.jline.console;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import org.jline.terminal.Terminal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandInput
/*     */ {
/*     */   String command;
/*     */   String[] args;
/*     */   Object[] xargs;
/*     */   Terminal terminal;
/*     */   InputStream in;
/*     */   PrintStream out;
/*     */   PrintStream err;
/*     */   
/*     */   public CommandInput(String command, Object[] xargs, CommandRegistry.CommandSession session) {
/*  45 */     if (xargs != null) {
/*  46 */       this.xargs = xargs;
/*  47 */       this.args = new String[xargs.length];
/*  48 */       for (int i = 0; i < xargs.length; i++) {
/*  49 */         this.args[i] = (xargs[i] != null) ? xargs[i].toString() : null;
/*     */       }
/*     */     } 
/*  52 */     this.command = command;
/*  53 */     this.terminal = session.terminal();
/*  54 */     this.in = session.in();
/*  55 */     this.out = session.out();
/*  56 */     this.err = session.err();
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
/*     */   public CommandInput(String command, Object[] args, Terminal terminal, InputStream in, PrintStream out, PrintStream err) {
/*  71 */     this(command, args, new CommandRegistry.CommandSession(terminal, in, out, err));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String command() {
/*  80 */     return this.command;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] args() {
/*  89 */     return this.args;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] xargs() {
/*  98 */     return this.xargs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Terminal terminal() {
/* 107 */     return this.terminal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream in() {
/* 116 */     return this.in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintStream out() {
/* 125 */     return this.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintStream err() {
/* 134 */     return this.err;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandRegistry.CommandSession session() {
/* 143 */     return new CommandRegistry.CommandSession(this.terminal, this.in, this.out, this.err);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\console\CommandInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */