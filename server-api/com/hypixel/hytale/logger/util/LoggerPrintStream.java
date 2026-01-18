/*    */ package com.hypixel.hytale.logger.util;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public class LoggerPrintStream
/*    */   extends PrintStream
/*    */ {
/*    */   private final HytaleLogger logger;
/*    */   private final Level level;
/*    */   private final ByteArrayOutputStream bufferedOutput;
/*    */   private int last;
/*    */   
/*    */   public LoggerPrintStream(HytaleLogger logger, Level level) {
/* 17 */     super(new ByteArrayOutputStream());
/* 18 */     this.logger = logger;
/* 19 */     this.level = level;
/* 20 */     this.bufferedOutput = (ByteArrayOutputStream)this.out;
/*    */     
/* 22 */     this.last = -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int b) {
/* 27 */     if (this.last == 13 && b == 10) {
/* 28 */       this.last = -1;
/*    */     } else {
/* 30 */       if (b != 10 && b != 13) {
/* 31 */         super.write(b);
/*    */       } else {
/*    */         try {
/* 34 */           this.logger.at(this.level).log(this.bufferedOutput.toString());
/*    */         } finally {
/* 36 */           this.bufferedOutput.reset();
/*    */         } 
/*    */       } 
/*    */       
/* 40 */       this.last = b;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] buf, int off, int len) {
/* 46 */     if (len < 0) {
/* 47 */       throw new ArrayIndexOutOfBoundsException(len);
/*    */     }
/* 49 */     for (int i = 0; i < len; i++) {
/* 50 */       write(buf[off + i]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public HytaleLogger getLogger() {
/* 56 */     return this.logger;
/*    */   }
/*    */   
/*    */   public Level getLevel() {
/* 60 */     return this.level;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logge\\util\LoggerPrintStream.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */