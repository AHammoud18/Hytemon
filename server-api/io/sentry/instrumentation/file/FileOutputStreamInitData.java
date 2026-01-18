/*    */ package io.sentry.instrumentation.file;
/*    */ 
/*    */ import io.sentry.ISpan;
/*    */ import io.sentry.SentryOptions;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ final class FileOutputStreamInitData
/*    */ {
/*    */   @Nullable
/*    */   final File file;
/*    */   @Nullable
/*    */   final ISpan span;
/*    */   final boolean append;
/*    */   @NotNull
/*    */   final FileOutputStream delegate;
/*    */   @NotNull
/*    */   final SentryOptions options;
/*    */   
/*    */   FileOutputStreamInitData(@Nullable File file, boolean append, @Nullable ISpan span, @NotNull FileOutputStream delegate, @NotNull SentryOptions options) {
/* 24 */     this.file = file;
/* 25 */     this.append = append;
/* 26 */     this.span = span;
/* 27 */     this.delegate = delegate;
/* 28 */     this.options = options;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\instrumentation\file\FileOutputStreamInitData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */