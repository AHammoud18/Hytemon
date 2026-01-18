/*    */ package com.hypixel.hytale.logger.backend;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.nio.file.attribute.FileAttribute;
/*    */ import java.time.LocalDateTime;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.util.Collection;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.BlockingQueue;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ import java.util.logging.FileHandler;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class HytaleFileHandler extends Thread {
/* 21 */   public static final DateTimeFormatter LOG_FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
/*    */   
/* 23 */   public static final HytaleFileHandler INSTANCE = new HytaleFileHandler();
/*    */   
/* 25 */   private final BlockingQueue<LogRecord> logRecords = new LinkedBlockingQueue<>();
/*    */   
/*    */   @Nullable
/*    */   private FileHandler fileHandler;
/*    */   
/*    */   public HytaleFileHandler() {
/* 31 */     super("HytaleLogger");
/* 32 */     setDaemon(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 37 */     if (this.fileHandler == null) throw new IllegalStateException("Thread should not be started when no file handler exists!"); 
/*    */     try {
/* 39 */       for (; !isInterrupted(); this.fileHandler.publish(this.logRecords.take()));
/* 40 */     } catch (InterruptedException ignored) {
/* 41 */       Thread.currentThread().interrupt();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public FileHandler getFileHandler() {
/* 47 */     return this.fileHandler;
/*    */   }
/*    */   
/*    */   public void enable() {
/* 51 */     if (this.fileHandler != null) throw new IllegalStateException("Already enabled!");
/*    */     
/*    */     try {
/* 54 */       Path logsDirectory = Paths.get("logs/", new String[0]);
/* 55 */       if (!Files.isDirectory(logsDirectory, new java.nio.file.LinkOption[0])) Files.createDirectory(logsDirectory, (FileAttribute<?>[])new FileAttribute[0]);
/*    */       
/* 57 */       String fileNamePart = "logs/" + LOG_FILE_DATE_FORMAT.format(LocalDateTime.now());
/* 58 */       String fileName = fileNamePart + "_server.log";
/* 59 */       if (Files.exists(Paths.get(fileName, new String[0]), new java.nio.file.LinkOption[0])) fileName = fileNamePart + "%u_server.log"; 
/* 60 */       this.fileHandler = new FileHandler(fileName);
/* 61 */       this.fileHandler.setEncoding("UTF-8");
/* 62 */       this.fileHandler.setLevel(Level.ALL);
/* 63 */       this.fileHandler.setFormatter(new HytaleLogFormatter(() -> false));
/* 64 */     } catch (IOException e) {
/* 65 */       throw new RuntimeException("Failed to create file handler!", e);
/*    */     } 
/*    */     
/* 68 */     start();
/*    */   }
/*    */   
/*    */   public void log(@Nonnull LogRecord logRecord) {
/* 72 */     if (!isAlive()) {
/* 73 */       if (this.fileHandler != null) this.fileHandler.publish(logRecord); 
/*    */       return;
/*    */     } 
/* 76 */     this.logRecords.add(logRecord);
/*    */   }
/*    */   
/*    */   public void shutdown() {
/* 80 */     if (this.fileHandler != null) {
/*    */       
/* 82 */       interrupt();
/*    */       try {
/* 84 */         join();
/* 85 */       } catch (InterruptedException ignored) {
/* 86 */         Thread.currentThread().interrupt();
/*    */       } 
/*    */ 
/*    */       
/* 90 */       ObjectArrayList objectArrayList = new ObjectArrayList();
/* 91 */       this.logRecords.drainTo((Collection<? super LogRecord>)objectArrayList);
/* 92 */       Objects.requireNonNull(this.fileHandler); objectArrayList.forEach(this.fileHandler::publish);
/* 93 */       this.fileHandler.flush();
/* 94 */       this.fileHandler.close();
/* 95 */       this.fileHandler = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logger\backend\HytaleFileHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */