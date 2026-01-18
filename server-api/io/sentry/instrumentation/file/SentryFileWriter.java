/*    */ package io.sentry.instrumentation.file;
/*    */ 
/*    */ import io.sentry.IScopes;
/*    */ import java.io.File;
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.OutputStreamWriter;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class SentryFileWriter extends OutputStreamWriter {
/*    */   public SentryFileWriter(@NotNull String fileName) throws FileNotFoundException {
/* 12 */     super(new SentryFileOutputStream(fileName));
/*    */   }
/*    */ 
/*    */   
/*    */   public SentryFileWriter(@NotNull String fileName, boolean append) throws FileNotFoundException {
/* 17 */     super(new SentryFileOutputStream(fileName, append));
/*    */   }
/*    */   
/*    */   public SentryFileWriter(@NotNull File file) throws FileNotFoundException {
/* 21 */     super(new SentryFileOutputStream(file));
/*    */   }
/*    */ 
/*    */   
/*    */   public SentryFileWriter(@NotNull File file, boolean append) throws FileNotFoundException {
/* 26 */     super(new SentryFileOutputStream(file, append));
/*    */   }
/*    */   
/*    */   public SentryFileWriter(@NotNull FileDescriptor fd) {
/* 30 */     super(new SentryFileOutputStream(fd));
/*    */   }
/*    */ 
/*    */   
/*    */   SentryFileWriter(@NotNull File file, boolean append, @NotNull IScopes scopes) throws FileNotFoundException {
/* 35 */     super(new SentryFileOutputStream(file, append, scopes));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\instrumentation\file\SentryFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */