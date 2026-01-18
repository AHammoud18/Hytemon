/*     */ package io.sentry.instrumentation.file;
/*     */ 
/*     */ import io.sentry.IScopes;
/*     */ import io.sentry.ISpan;
/*     */ import io.sentry.ScopesAdapter;
/*     */ import io.sentry.SentryOptions;
/*     */ import java.io.File;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SentryFileInputStream
/*     */   extends FileInputStream
/*     */ {
/*     */   @NotNull
/*     */   private final FileInputStream delegate;
/*     */   @NotNull
/*     */   private final FileIOSpanManager spanManager;
/*     */   
/*     */   public SentryFileInputStream(@Nullable String name) throws FileNotFoundException {
/*  29 */     this((name != null) ? new File(name) : null, (IScopes)ScopesAdapter.getInstance());
/*     */   }
/*     */   
/*     */   public SentryFileInputStream(@Nullable File file) throws FileNotFoundException {
/*  33 */     this(file, (IScopes)ScopesAdapter.getInstance());
/*     */   }
/*     */   
/*     */   public SentryFileInputStream(@NotNull FileDescriptor fdObj) {
/*  37 */     this(fdObj, (IScopes)ScopesAdapter.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   SentryFileInputStream(@Nullable File file, @NotNull IScopes scopes) throws FileNotFoundException {
/*  42 */     this(init(file, (FileInputStream)null, scopes));
/*     */   }
/*     */   
/*     */   SentryFileInputStream(@NotNull FileDescriptor fdObj, @NotNull IScopes scopes) {
/*  46 */     this(init(fdObj, (FileInputStream)null, scopes), fdObj);
/*     */   }
/*     */ 
/*     */   
/*     */   private SentryFileInputStream(@NotNull FileInputStreamInitData data, @NotNull FileDescriptor fd) {
/*  51 */     super(fd);
/*  52 */     this.spanManager = new FileIOSpanManager(data.span, data.file, data.options);
/*  53 */     this.delegate = data.delegate;
/*     */   }
/*     */ 
/*     */   
/*     */   private SentryFileInputStream(@NotNull FileInputStreamInitData data) throws FileNotFoundException {
/*  58 */     super(getFileDescriptor(data.delegate));
/*  59 */     this.spanManager = new FileIOSpanManager(data.span, data.file, data.options);
/*  60 */     this.delegate = data.delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static FileInputStreamInitData init(@Nullable File file, @Nullable FileInputStream delegate, @NotNull IScopes scopes) throws FileNotFoundException {
/*  66 */     ISpan span = FileIOSpanManager.startSpan(scopes, "file.read");
/*  67 */     if (delegate == null) {
/*  68 */       delegate = new FileInputStream(file);
/*     */     }
/*  70 */     return new FileInputStreamInitData(file, span, delegate, scopes.getOptions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FileInputStreamInitData init(@NotNull FileDescriptor fd, @Nullable FileInputStream delegate, @NotNull IScopes scopes) {
/*  77 */     ISpan span = FileIOSpanManager.startSpan(scopes, "file.read");
/*  78 */     if (delegate == null) {
/*  79 */       delegate = new FileInputStream(fd);
/*     */     }
/*  81 */     return new FileInputStreamInitData(null, span, delegate, scopes.getOptions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*  88 */     AtomicInteger result = new AtomicInteger(0);
/*  89 */     this.spanManager.performIO(() -> {
/*     */           int res = this.delegate.read();
/*     */           
/*     */           result.set(res);
/*     */           return Integer.valueOf((res != -1) ? 1 : 0);
/*     */         });
/*  95 */     return result.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/* 100 */     return ((Integer)this.spanManager.<Integer>performIO(() -> Integer.valueOf(this.delegate.read(b)))).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 105 */     return ((Integer)this.spanManager.<Integer>performIO(() -> Integer.valueOf(this.delegate.read(b, off, len)))).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 110 */     return ((Long)this.spanManager.<Long>performIO(() -> Long.valueOf(this.delegate.skip(n)))).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 115 */     this.spanManager.finish(this.delegate);
/* 116 */     super.close();
/*     */   }
/*     */ 
/*     */   
/*     */   private static FileDescriptor getFileDescriptor(@NotNull FileInputStream stream) throws FileNotFoundException {
/*     */     try {
/* 122 */       return stream.getFD();
/* 123 */     } catch (IOException error) {
/* 124 */       throw new FileNotFoundException("No file descriptor");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Factory
/*     */   {
/*     */     public static FileInputStream create(@NotNull FileInputStream delegate, @Nullable String name) throws FileNotFoundException {
/* 132 */       ScopesAdapter scopesAdapter = ScopesAdapter.getInstance();
/* 133 */       if (isTracingEnabled((IScopes)scopesAdapter)) {  } else {  }  return 
/*     */         
/* 135 */         delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static FileInputStream create(@NotNull FileInputStream delegate, @Nullable File file) throws FileNotFoundException {
/* 141 */       ScopesAdapter scopesAdapter = ScopesAdapter.getInstance();
/* 142 */       return isTracingEnabled((IScopes)scopesAdapter) ? 
/* 143 */         new SentryFileInputStream(SentryFileInputStream.init(file, delegate, (IScopes)scopesAdapter)) : 
/* 144 */         delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public static FileInputStream create(@NotNull FileInputStream delegate, @NotNull FileDescriptor descriptor) {
/* 149 */       ScopesAdapter scopesAdapter = ScopesAdapter.getInstance();
/* 150 */       return isTracingEnabled((IScopes)scopesAdapter) ? 
/* 151 */         new SentryFileInputStream(SentryFileInputStream.init(descriptor, delegate, (IScopes)scopesAdapter), descriptor) : 
/* 152 */         delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static FileInputStream create(@NotNull FileInputStream delegate, @Nullable File file, @NotNull IScopes scopes) throws FileNotFoundException {
/* 160 */       return isTracingEnabled(scopes) ? 
/* 161 */         new SentryFileInputStream(SentryFileInputStream.init(file, delegate, scopes)) : 
/* 162 */         delegate;
/*     */     }
/*     */     
/*     */     private static boolean isTracingEnabled(@NotNull IScopes scopes) {
/* 166 */       SentryOptions options = scopes.getOptions();
/* 167 */       return options.isTracingEnabled();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\instrumentation\file\SentryFileInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */