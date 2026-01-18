/*     */ package com.hypixel.hytale.server.core.console;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.backend.HytaleConsole;
/*     */ import com.hypixel.hytale.server.core.Constants;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.ShutdownReason;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandManager;
/*     */ import com.hypixel.hytale.server.core.console.command.SayCommand;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import java.io.IOException;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.jline.reader.EndOfFileException;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.LineReaderBuilder;
/*     */ import org.jline.reader.UserInterruptException;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.TerminalBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConsoleModule
/*     */   extends JavaPlugin
/*     */ {
/*  31 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(ConsoleModule.class)
/*  32 */     .build();
/*     */   
/*     */   private static ConsoleModule instance;
/*     */   
/*     */   public static ConsoleModule get() {
/*  37 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private Terminal terminal;
/*     */   private ConsoleRunnable consoleRunnable;
/*     */   
/*     */   public ConsoleModule(@Nonnull JavaPluginInit init) {
/*  45 */     super(init);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  50 */     instance = this;
/*     */     
/*  52 */     getCommandRegistry().registerCommand((AbstractCommand)new SayCommand());
/*     */     
/*     */     try {
/*  55 */       TerminalBuilder builder = TerminalBuilder.builder();
/*  56 */       if (Constants.SINGLEPLAYER) { builder.dumb(true); }
/*  57 */       else { builder.color(true); }
/*  58 */        this.terminal = builder.build();
/*  59 */       HytaleConsole.INSTANCE.setTerminal(this.terminal.getType());
/*     */ 
/*     */ 
/*     */       
/*  63 */       LineReader lineReader = LineReaderBuilder.builder().terminal(this.terminal).build();
/*     */       
/*  65 */       this.consoleRunnable = new ConsoleRunnable(lineReader, ConsoleSender.INSTANCE);
/*  66 */       getLogger().at(Level.INFO).log("Setup console with type: %s", this.terminal.getType());
/*  67 */     } catch (IOException e) {
/*  68 */       ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(e)).log("Failed to start console reader");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void shutdown() {
/*  74 */     getLogger().at(Level.INFO).log("Restoring terminal...");
/*     */     try {
/*  76 */       this.terminal.close();
/*  77 */     } catch (IOException e) {
/*  78 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(e)).log("Failed to restore terminal!");
/*     */     } 
/*  80 */     this.consoleRunnable.interrupt();
/*     */   }
/*     */   
/*     */   public Terminal getTerminal() {
/*  84 */     return this.terminal;
/*     */   }
/*     */   
/*     */   private static class ConsoleRunnable implements Runnable {
/*     */     private final LineReader lineReader;
/*     */     private final ConsoleSender consoleSender;
/*     */     @Nonnull
/*     */     private final Thread consoleThread;
/*     */     
/*     */     public ConsoleRunnable(LineReader lineReader, ConsoleSender consoleSender) {
/*  94 */       this.lineReader = lineReader;
/*  95 */       this.consoleSender = consoleSender;
/*     */       
/*  97 */       this.consoleThread = new Thread(this, "ConsoleThread");
/*  98 */       this.consoleThread.setDaemon(true);
/*  99 */       this.consoleThread.start();
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 105 */         String terminalType = this.lineReader.getTerminal().getType();
/* 106 */         boolean isDumb = ("dumb".equals(terminalType) || "dumb-color".equals(terminalType));
/* 107 */         while (!this.consoleThread.isInterrupted()) {
/* 108 */           String command = this.lineReader.readLine(isDumb ? null : "> ");
/* 109 */           if (command == null) {
/*     */             break;
/*     */           }
/* 112 */           command = command.trim();
/* 113 */           if (!command.isEmpty() && command.charAt(0) == '/') {
/* 114 */             command = command.substring(1);
/*     */           }
/*     */           
/* 117 */           CommandManager.get().handleCommand(this.consoleSender, command);
/*     */         } 
/* 119 */       } catch (UserInterruptException e) {
/* 120 */         HytaleServer.get().shutdownServer(ShutdownReason.SIGINT);
/* 121 */       } catch (EndOfFileException|java.io.IOError endOfFileException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void interrupt() {
/* 128 */       this.consoleThread.interrupt();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\console\ConsoleModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */