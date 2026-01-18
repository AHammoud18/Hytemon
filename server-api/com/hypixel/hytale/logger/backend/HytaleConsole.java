/*     */ package com.hypixel.hytale.logger.backend;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class HytaleConsole
/*     */   extends Thread
/*     */ {
/*     */   public static final String TYPE_DUMB = "dumb";
/*  19 */   public static final HytaleConsole INSTANCE = new HytaleConsole();
/*     */   
/*  21 */   private final BlockingQueue<LogRecord> logRecords = new LinkedBlockingQueue<>();
/*  22 */   private final HytaleLogFormatter formatter = new HytaleLogFormatter(this::shouldPrintAnsi);
/*     */   @Nullable
/*  24 */   private OutputStreamWriter soutwriter = new OutputStreamWriter(HytaleLoggerBackend.REAL_SOUT, StandardCharsets.UTF_8);
/*     */   @Nullable
/*  26 */   private OutputStreamWriter serrwriter = new OutputStreamWriter(HytaleLoggerBackend.REAL_SERR, StandardCharsets.UTF_8);
/*     */ 
/*     */   
/*  29 */   private String terminalType = "dumb";
/*     */   
/*     */   private HytaleConsole() {
/*  32 */     super("HytaleConsole");
/*  33 */     setDaemon(true);
/*     */     
/*  35 */     start();
/*     */   }
/*     */   
/*     */   public void publish(@Nonnull LogRecord logRecord) {
/*  39 */     if (!isAlive()) {
/*  40 */       publish0(logRecord);
/*     */       return;
/*     */     } 
/*  43 */     this.logRecords.offer(logRecord);
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  49 */       for (; !isInterrupted(); publish0(this.logRecords.take()));
/*  50 */     } catch (InterruptedException ignored) {
/*  51 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {
/*  57 */     interrupt();
/*     */     try {
/*  59 */       join();
/*  60 */     } catch (InterruptedException ignored) {
/*  61 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */ 
/*     */     
/*  65 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/*  66 */     this.logRecords.drainTo((Collection<? super LogRecord>)objectArrayList);
/*  67 */     objectArrayList.forEach(this::publish0);
/*     */     
/*  69 */     if (this.soutwriter != null) {
/*     */       try {
/*  71 */         this.soutwriter.flush();
/*     */       }
/*  73 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  78 */       this.soutwriter = null;
/*     */     } 
/*  80 */     if (this.serrwriter != null) {
/*     */       try {
/*  82 */         this.serrwriter.flush();
/*     */       }
/*  84 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       this.serrwriter = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void publish0(@Nonnull LogRecord record) {
/*     */     String msg;
/*     */     try {
/*  97 */       msg = this.formatter.format(record);
/*  98 */     } catch (Exception ex) {
/*  99 */       if (this.serrwriter != null) {
/* 100 */         ex.printStackTrace(new PrintWriter(this.serrwriter));
/*     */       } else {
/* 102 */         ex.printStackTrace(System.err);
/*     */       } 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 111 */       if (record.getLevel().intValue() >= Level.SEVERE.intValue()) {
/* 112 */         if (this.serrwriter != null) {
/* 113 */           this.serrwriter.write(msg);
/*     */           try {
/* 115 */             this.serrwriter.flush();
/* 116 */           } catch (Exception exception) {}
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 122 */           HytaleLoggerBackend.REAL_SERR.print(msg);
/*     */         }
/*     */       
/* 125 */       } else if (this.soutwriter != null) {
/* 126 */         this.soutwriter.write(msg);
/*     */         try {
/* 128 */           this.soutwriter.flush();
/* 129 */         } catch (Exception exception) {}
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 135 */         HytaleLoggerBackend.REAL_SOUT.print(msg);
/*     */       }
/*     */     
/* 138 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTerminal(String type) {
/* 146 */     this.terminalType = type;
/*     */   }
/*     */   
/*     */   private boolean shouldPrintAnsi() {
/* 150 */     return !"dumb".equals(this.terminalType);
/*     */   }
/*     */   
/*     */   public HytaleLogFormatter getFormatter() {
/* 154 */     return this.formatter;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logger\backend\HytaleConsole.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */