/*    */ package io.sentry.instrumentation.file;
/*    */ 
/*    */ import io.sentry.ISpan;
/*    */ import io.sentry.SentryOptions;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ final class FileInputStreamInitData
/*    */ {
/*    */   @Nullable
/*    */   final File file;
/*    */   @Nullable
/*    */   final ISpan span;
/*    */   @NotNull
/*    */   final FileInputStream delegate;
/*    */   @NotNull
/*    */   final SentryOptions options;
/*    */   
/*    */   FileInputStreamInitData(@Nullable File file, @Nullable ISpan span, @NotNull FileInputStream delegate, @NotNull SentryOptions options) {
/* 22 */     this.file = file;
/* 23 */     this.span = span;
/* 24 */     this.delegate = delegate;
/* 25 */     this.options = options;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\instrumentation\file\FileInputStreamInitData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */