/*    */ package io.sentry.instrumentation.file;
/*    */ 
/*    */ import io.sentry.IScopes;
/*    */ import java.io.File;
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.InputStreamReader;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class SentryFileReader extends InputStreamReader {
/*    */   public SentryFileReader(@NotNull String fileName) throws FileNotFoundException {
/* 12 */     super(new SentryFileInputStream(fileName));
/*    */   }
/*    */   
/*    */   public SentryFileReader(@NotNull File file) throws FileNotFoundException {
/* 16 */     super(new SentryFileInputStream(file));
/*    */   }
/*    */   
/*    */   public SentryFileReader(@NotNull FileDescriptor fd) {
/* 20 */     super(new SentryFileInputStream(fd));
/*    */   }
/*    */ 
/*    */   
/*    */   SentryFileReader(@NotNull File file, @NotNull IScopes scopes) throws FileNotFoundException {
/* 25 */     super(new SentryFileInputStream(file, scopes));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\instrumentation\file\SentryFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */