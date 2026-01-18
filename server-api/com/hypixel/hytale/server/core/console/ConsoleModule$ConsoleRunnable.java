/*     */ package com.hypixel.hytale.server.core.console;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.ShutdownReason;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandManager;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.jline.reader.EndOfFileException;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.UserInterruptException;
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
/*     */ class ConsoleRunnable
/*     */   implements Runnable
/*     */ {
/*     */   private final LineReader lineReader;
/*     */   private final ConsoleSender consoleSender;
/*     */   @Nonnull
/*     */   private final Thread consoleThread;
/*     */   
/*     */   public ConsoleRunnable(LineReader lineReader, ConsoleSender consoleSender) {
/*  94 */     this.lineReader = lineReader;
/*  95 */     this.consoleSender = consoleSender;
/*     */     
/*  97 */     this.consoleThread = new Thread(this, "ConsoleThread");
/*  98 */     this.consoleThread.setDaemon(true);
/*  99 */     this.consoleThread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 105 */       String terminalType = this.lineReader.getTerminal().getType();
/* 106 */       boolean isDumb = ("dumb".equals(terminalType) || "dumb-color".equals(terminalType));
/* 107 */       while (!this.consoleThread.isInterrupted()) {
/* 108 */         String command = this.lineReader.readLine(isDumb ? null : "> ");
/* 109 */         if (command == null) {
/*     */           break;
/*     */         }
/* 112 */         command = command.trim();
/* 113 */         if (!command.isEmpty() && command.charAt(0) == '/') {
/* 114 */           command = command.substring(1);
/*     */         }
/*     */         
/* 117 */         CommandManager.get().handleCommand(this.consoleSender, command);
/*     */       } 
/* 119 */     } catch (UserInterruptException e) {
/* 120 */       HytaleServer.get().shutdownServer(ShutdownReason.SIGINT);
/* 121 */     } catch (EndOfFileException|java.io.IOError endOfFileException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interrupt() {
/* 128 */     this.consoleThread.interrupt();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\console\ConsoleModule$ConsoleRunnable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */