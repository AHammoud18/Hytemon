/*     */ package io.sentry.instrumentation.file;
/*     */ 
/*     */ import io.sentry.IScopes;
/*     */ import io.sentry.ISpan;
/*     */ import io.sentry.ScopesAdapter;
/*     */ import io.sentry.SentryOptions;
/*     */ import java.io.File;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SentryFileOutputStream
/*     */   extends FileOutputStream
/*     */ {
/*     */   @NotNull
/*     */   private final FileOutputStream delegate;
/*     */   @NotNull
/*     */   private final FileIOSpanManager spanManager;
/*     */   
/*     */   public SentryFileOutputStream(@Nullable String name) throws FileNotFoundException {
/*  28 */     this((name != null) ? new File(name) : null, false, (IScopes)ScopesAdapter.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public SentryFileOutputStream(@Nullable String name, boolean append) throws FileNotFoundException {
/*  33 */     this(init((name != null) ? new File(name) : null, append, null, (IScopes)ScopesAdapter.getInstance()));
/*     */   }
/*     */   
/*     */   public SentryFileOutputStream(@Nullable File file) throws FileNotFoundException {
/*  37 */     this(file, false, (IScopes)ScopesAdapter.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public SentryFileOutputStream(@Nullable File file, boolean append) throws FileNotFoundException {
/*  42 */     this(init(file, append, null, (IScopes)ScopesAdapter.getInstance()));
/*     */   }
/*     */   
/*     */   public SentryFileOutputStream(@NotNull FileDescriptor fdObj) {
/*  46 */     this(init(fdObj, null, (IScopes)ScopesAdapter.getInstance()), fdObj);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SentryFileOutputStream(@Nullable File file, boolean append, @NotNull IScopes scopes) throws FileNotFoundException {
/*  52 */     this(init(file, append, null, scopes));
/*     */   }
/*     */ 
/*     */   
/*     */   private SentryFileOutputStream(@NotNull FileOutputStreamInitData data, @NotNull FileDescriptor fd) {
/*  57 */     super(fd);
/*  58 */     this.spanManager = new FileIOSpanManager(data.span, data.file, data.options);
/*  59 */     this.delegate = data.delegate;
/*     */   }
/*     */ 
/*     */   
/*     */   private SentryFileOutputStream(@NotNull FileOutputStreamInitData data) throws FileNotFoundException {
/*  64 */     super(getFileDescriptor(data.delegate));
/*  65 */     this.spanManager = new FileIOSpanManager(data.span, data.file, data.options);
/*  66 */     this.delegate = data.delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FileOutputStreamInitData init(@Nullable File file, boolean append, @Nullable FileOutputStream delegate, @NotNull IScopes scopes) throws FileNotFoundException {
/*  75 */     ISpan span = FileIOSpanManager.startSpan(scopes, "file.write");
/*  76 */     if (delegate == null) {
/*  77 */       delegate = new FileOutputStream(file, append);
/*     */     }
/*  79 */     return new FileOutputStreamInitData(file, append, span, delegate, scopes.getOptions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FileOutputStreamInitData init(@NotNull FileDescriptor fd, @Nullable FileOutputStream delegate, @NotNull IScopes scopes) {
/*  86 */     ISpan span = FileIOSpanManager.startSpan(scopes, "file.write");
/*  87 */     if (delegate == null) {
/*  88 */       delegate = new FileOutputStream(fd);
/*     */     }
/*  90 */     return new FileOutputStreamInitData(null, false, span, delegate, scopes.getOptions());
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/*  95 */     this.spanManager.performIO(() -> {
/*     */           this.delegate.write(b);
/*     */           return Integer.valueOf(1);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/* 104 */     this.spanManager.performIO(() -> {
/*     */           this.delegate.write(b);
/*     */           return Integer.valueOf(b.length);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 113 */     this.spanManager.performIO(() -> {
/*     */           this.delegate.write(b, off, len);
/*     */           return Integer.valueOf(len);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 122 */     this.spanManager.finish(this.delegate);
/* 123 */     super.close();
/*     */   }
/*     */ 
/*     */   
/*     */   private static FileDescriptor getFileDescriptor(@NotNull FileOutputStream stream) throws FileNotFoundException {
/*     */     try {
/* 129 */       return stream.getFD();
/* 130 */     } catch (IOException error) {
/* 131 */       throw new FileNotFoundException("No file descriptor");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Factory
/*     */   {
/*     */     public static FileOutputStream create(@NotNull FileOutputStream delegate, @Nullable String name) throws FileNotFoundException {
/* 139 */       ScopesAdapter scopesAdapter = ScopesAdapter.getInstance();
/* 140 */       if (isTracingEnabled((IScopes)scopesAdapter)) {  } else {  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 147 */         delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static FileOutputStream create(@NotNull FileOutputStream delegate, @Nullable String name, boolean append) throws FileNotFoundException {
/* 153 */       ScopesAdapter scopesAdapter = ScopesAdapter.getInstance();
/* 154 */       if (isTracingEnabled((IScopes)scopesAdapter)) {  } else {  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 161 */         delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static FileOutputStream create(@NotNull FileOutputStream delegate, @Nullable File file) throws FileNotFoundException {
/* 167 */       ScopesAdapter scopesAdapter = ScopesAdapter.getInstance();
/* 168 */       return isTracingEnabled((IScopes)scopesAdapter) ? 
/* 169 */         new SentryFileOutputStream(SentryFileOutputStream.init(file, false, delegate, (IScopes)ScopesAdapter.getInstance())) : 
/* 170 */         delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static FileOutputStream create(@NotNull FileOutputStream delegate, @Nullable File file, boolean append) throws FileNotFoundException {
/* 176 */       ScopesAdapter scopesAdapter = ScopesAdapter.getInstance();
/* 177 */       return isTracingEnabled((IScopes)scopesAdapter) ? 
/* 178 */         new SentryFileOutputStream(SentryFileOutputStream.init(file, append, delegate, (IScopes)ScopesAdapter.getInstance())) : 
/* 179 */         delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public static FileOutputStream create(@NotNull FileOutputStream delegate, @NotNull FileDescriptor fdObj) {
/* 184 */       ScopesAdapter scopesAdapter = ScopesAdapter.getInstance();
/* 185 */       return isTracingEnabled((IScopes)scopesAdapter) ? 
/* 186 */         new SentryFileOutputStream(SentryFileOutputStream.init(fdObj, delegate, (IScopes)ScopesAdapter.getInstance()), fdObj) : 
/* 187 */         delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static FileOutputStream create(@NotNull FileOutputStream delegate, @Nullable File file, @NotNull IScopes scopes) throws FileNotFoundException {
/* 195 */       return isTracingEnabled(scopes) ? 
/* 196 */         new SentryFileOutputStream(SentryFileOutputStream.init(file, false, delegate, scopes)) : 
/* 197 */         delegate;
/*     */     }
/*     */     
/*     */     private static boolean isTracingEnabled(@NotNull IScopes scopes) {
/* 201 */       SentryOptions options = scopes.getOptions();
/* 202 */       return options.isTracingEnabled();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\instrumentation\file\SentryFileOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */