/*     */ package com.hypixel.hytale.logger.backend;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.logging.Formatter;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class HytaleLogFormatter extends Formatter {
/*  16 */   private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
/*  17 */   private static final Pattern ANSI_CONTROL_CODES = Pattern.compile("\033\\[[;\\d]*m");
/*     */   
/*     */   private BooleanSupplier ansi;
/*     */   public int maxModuleName;
/*     */   private int shorterCount;
/*     */   
/*     */   public HytaleLogFormatter(BooleanSupplier ansi) {
/*  24 */     this.ansi = ansi;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String format(@Nonnull LogRecord record) {
/*  31 */     String level, message = record.getMessage();
/*     */     
/*  33 */     if (message == null) message = "null";
/*     */     
/*  35 */     if (record.getParameters() != null && (record.getParameters()).length > 0) {
/*     */       try {
/*  37 */         message = String.format(message, record.getParameters());
/*  38 */       } catch (RuntimeException e) {
/*  39 */         throw new IllegalArgumentException("Error logging using format string: " + record.getMessage(), e);
/*     */       } 
/*     */     }
/*     */     
/*  43 */     if (record.getThrown() != null) {
/*  44 */       StringWriter writer = new StringWriter();
/*  45 */       record.getThrown().printStackTrace(new PrintWriter(writer));
/*  46 */       message = message + "\n" + message;
/*     */     } 
/*     */     
/*  49 */     boolean ansi = this.ansi.getAsBoolean();
/*  50 */     if (ansi) message = message + "\033[m";
/*     */     
/*  52 */     if (record instanceof HytaleLoggerBackend.RawLogRecord) {
/*  53 */       if (!ansi)
/*     */       {
/*  55 */         return stripAnsi(message) + "\n";
/*     */       }
/*  57 */       return message + "\n";
/*     */     } 
/*     */ 
/*     */     
/*  61 */     String loggerName = record.getLoggerName();
/*  62 */     int moduleNameTextSize = loggerName.length() + 3;
/*  63 */     if ((moduleNameTextSize > this.maxModuleName && moduleNameTextSize < 35) || this.shorterCount > 500) {
/*  64 */       this.maxModuleName = moduleNameTextSize;
/*  65 */       this.shorterCount = 0;
/*  66 */     } else if (moduleNameTextSize < this.maxModuleName) {
/*  67 */       this.shorterCount++;
/*     */     } 
/*     */     
/*  70 */     StringBuilder sb = new StringBuilder(33 + this.maxModuleName + message.length());
/*     */     
/*  72 */     if (ansi) {
/*  73 */       String color = null;
/*  74 */       int i = record.getLevel().intValue();
/*  75 */       if (i <= Level.ALL.intValue()) {
/*  76 */         color = "\033[37m";
/*  77 */       } else if (i <= Level.FINEST.intValue()) {
/*  78 */         color = "\033[36m";
/*  79 */       } else if (i <= Level.FINER.intValue()) {
/*  80 */         color = "\033[34m";
/*  81 */       } else if (i <= Level.FINE.intValue()) {
/*  82 */         color = "\033[35m";
/*  83 */       } else if (i <= Level.CONFIG.intValue()) {
/*  84 */         color = "\033[32m";
/*  85 */       } else if (i <= Level.INFO.intValue()) {
/*  86 */         color = "\033[m";
/*  87 */       } else if (i <= Level.WARNING.intValue()) {
/*  88 */         color = "\033[33m";
/*  89 */       } else if (i <= Level.SEVERE.intValue()) {
/*  90 */         color = "\033[31m";
/*     */       } 
/*  92 */       if (color != null) sb.append(color);
/*     */     
/*     */     } 
/*  95 */     sb.append('[');
/*  96 */     DATE_FORMATTER.formatTo(LocalDateTime.ofInstant(record.getInstant(), ZoneOffset.UTC), sb);
/*  97 */     sb.append(' ');
/*     */ 
/*     */     
/* 100 */     if (Level.WARNING.equals(record.getLevel())) {
/* 101 */       level = "WARN";
/*     */     } else {
/* 103 */       level = record.getLevel().getName();
/*     */     } 
/* 105 */     int levelLength = level.length();
/* 106 */     if (levelLength < 6) {
/* 107 */       sb.append(" ".repeat(6 - levelLength));
/* 108 */       sb.append(level);
/*     */     } else {
/* 110 */       sb.append(level, 0, 6);
/*     */     } 
/* 112 */     sb.append("] ");
/*     */     
/* 114 */     sb.append(" ".repeat(Math.max(0, this.maxModuleName - moduleNameTextSize)));
/* 115 */     sb.append('[').append(loggerName).append("] ").append(ansi ? message : stripAnsi(message)).append('\n');
/*     */     
/* 117 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String stripAnsi(@Nonnull String message) {
/* 121 */     return ANSI_CONTROL_CODES.matcher(message).replaceAll("");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logger\backend\HytaleLogFormatter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */