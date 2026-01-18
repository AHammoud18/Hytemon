/*     */ package com.google.common.flogger.context;
/*     */ 
/*     */ import com.google.common.flogger.LoggingScope;
/*     */ import com.google.common.flogger.MetadataKey;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import com.google.errorprone.annotations.CheckReturnValue;
/*     */ import com.google.errorprone.annotations.MustBeClosed;
/*     */ import java.io.Closeable;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ScopedLoggingContext
/*     */ {
/*     */   public static interface LoggingContextCloseable
/*     */     extends Closeable
/*     */   {
/*     */     void close();
/*     */   }
/*     */   
/*     */   public static final class ScopeList
/*     */   {
/*     */     private final ScopeType key;
/*     */     private final LoggingScope scope;
/*     */     @NullableDecl
/*     */     private final ScopeList next;
/*     */     
/*     */     @NullableDecl
/*     */     public static ScopeList addScope(@NullableDecl ScopeList list, @NullableDecl ScopeType type) {
/* 101 */       return (type != null && lookup(list, type) == null) ? 
/* 102 */         new ScopeList(type, type.newScope(), list) : 
/* 103 */         list;
/*     */     }
/*     */     
/*     */     @NullableDecl
/*     */     public static LoggingScope lookup(@NullableDecl ScopeList list, ScopeType type) {
/* 108 */       while (list != null) {
/* 109 */         if (type.equals(list.key)) {
/* 110 */           return list.scope;
/*     */         }
/* 112 */         list = list.next;
/*     */       } 
/* 114 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ScopeList(ScopeType key, LoggingScope scope, @NullableDecl ScopeList next) {
/* 122 */       this.key = (ScopeType)Checks.checkNotNull(key, "scope type");
/* 123 */       this.scope = (LoggingScope)Checks.checkNotNull(scope, "scope");
/* 124 */       this.next = next;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class Builder
/*     */   {
/* 136 */     private Tags tags = null;
/* 137 */     private ScopeMetadata.Builder metadata = null;
/* 138 */     private LogLevelMap logLevelMap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CheckReturnValue
/*     */     public final Builder withTags(Tags tags) {
/* 148 */       Checks.checkState((this.tags == null), "tags already set");
/* 149 */       Checks.checkNotNull(tags, "tags");
/* 150 */       this.tags = tags;
/* 151 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CheckReturnValue
/*     */     public final <T> Builder withMetadata(MetadataKey<T> key, T value) {
/* 160 */       if (this.metadata == null) {
/* 161 */         this.metadata = ScopeMetadata.builder();
/*     */       }
/* 163 */       this.metadata.add(key, value);
/* 164 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CheckReturnValue
/*     */     public final Builder withLogLevelMap(LogLevelMap logLevelMap) {
/* 173 */       Checks.checkState((this.logLevelMap == null), "log level map already set");
/* 174 */       Checks.checkNotNull(logLevelMap, "log level map");
/* 175 */       this.logLevelMap = logLevelMap;
/* 176 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CheckReturnValue
/*     */     public final Runnable wrap(final Runnable r) {
/* 189 */       return new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 194 */             ScopedLoggingContext.LoggingContextCloseable context = ScopedLoggingContext.Builder.this.install();
/* 195 */             boolean hasError = true;
/*     */             try {
/* 197 */               r.run();
/* 198 */               hasError = false;
/*     */             } finally {
/* 200 */               ScopedLoggingContext.closeAndMaybePropagateError(context, hasError);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CheckReturnValue
/*     */     public final <R> Callable<R> wrap(final Callable<R> c) {
/* 216 */       return new Callable<R>()
/*     */         {
/*     */           public R call() throws Exception
/*     */           {
/* 220 */             ScopedLoggingContext.LoggingContextCloseable context = ScopedLoggingContext.Builder.this.install();
/* 221 */             boolean hasError = true;
/*     */             try {
/* 223 */               R result = c.call();
/* 224 */               hasError = false;
/* 225 */               return result;
/*     */             } finally {
/* 227 */               ScopedLoggingContext.closeAndMaybePropagateError(context, hasError);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public final void run(Runnable r) {
/* 235 */       wrap(r).run();
/*     */     }
/*     */ 
/*     */     
/*     */     public final <R> R call(Callable<R> c) throws Exception {
/* 240 */       return wrap(c).call();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final <R> R callUnchecked(Callable<R> c) {
/*     */       try {
/* 249 */         return call(c);
/* 250 */       } catch (RuntimeException e) {
/* 251 */         throw e;
/* 252 */       } catch (Exception e) {
/* 253 */         throw new RuntimeException("checked exception caught during context call", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CheckReturnValue
/*     */     @MustBeClosed
/*     */     public abstract ScopedLoggingContext.LoggingContextCloseable install();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NullableDecl
/*     */     protected final Tags getTags() {
/* 294 */       return this.tags;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NullableDecl
/*     */     protected final ScopeMetadata getMetadata() {
/* 303 */       return (this.metadata != null) ? this.metadata.build() : null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NullableDecl
/*     */     protected final LogLevelMap getLogLevelMap() {
/* 312 */       return this.logLevelMap;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CheckReturnValue
/*     */   public static ScopedLoggingContext getInstance() {
/* 323 */     return ContextDataProvider.getInstance().getContextApiSingleton();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @CheckReturnValue
/*     */   public Builder newScope() {
/* 363 */     return newContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addTags(Tags tags) {
/* 380 */     Checks.checkNotNull(tags, "tags");
/* 381 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> boolean addMetadata(MetadataKey<T> key, T value) {
/* 394 */     Checks.checkNotNull(key, "key");
/* 395 */     Checks.checkNotNull(value, "value");
/* 396 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean applyLogLevelMap(LogLevelMap logLevelMap) {
/* 414 */     Checks.checkNotNull(logLevelMap, "log level map");
/* 415 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void closeAndMaybePropagateError(LoggingContextCloseable context, boolean callerHasError) {
/*     */     try {
/* 426 */       context.close();
/* 427 */     } catch (RuntimeException e) {
/*     */ 
/*     */       
/* 430 */       if (!callerHasError) {
/* 431 */         throw (e instanceof InvalidLoggingContextStateException) ? 
/* 432 */           (InvalidLoggingContextStateException)e : 
/* 433 */           new InvalidLoggingContextStateException("invalid logging context state", e);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @CheckReturnValue
/*     */   public abstract Builder newContext();
/*     */   
/*     */   public static final class InvalidLoggingContextStateException
/*     */     extends IllegalStateException
/*     */   {
/*     */     public InvalidLoggingContextStateException(String message, Throwable cause) {
/* 445 */       super(message, cause);
/*     */     }
/*     */     
/*     */     public InvalidLoggingContextStateException(String message) {
/* 449 */       super(message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\context\ScopedLoggingContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */