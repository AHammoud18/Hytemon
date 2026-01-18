/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.LoadClass;
/*    */ import io.sentry.util.Platform;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class ScopesStorageFactory
/*    */ {
/*    */   private static final String OTEL_SCOPES_STORAGE = "io.sentry.opentelemetry.OtelContextScopesStorage";
/*    */   
/*    */   @NotNull
/*    */   public static IScopesStorage create(@NotNull LoadClass loadClass, @NotNull ILogger logger) {
/* 18 */     IScopesStorage storage = createInternal(loadClass, logger);
/* 19 */     storage.init();
/* 20 */     return storage;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   private static IScopesStorage createInternal(@NotNull LoadClass loadClass, @NotNull ILogger logger) {
/* 25 */     if (Platform.isJvm() && 
/* 26 */       loadClass.isClassAvailable("io.sentry.opentelemetry.OtelContextScopesStorage", logger)) {
/* 27 */       Class<?> otelScopesStorageClazz = loadClass.loadClass("io.sentry.opentelemetry.OtelContextScopesStorage", logger);
/* 28 */       if (otelScopesStorageClazz != null) {
/*    */         
/*    */         try {
/* 31 */           Object otelScopesStorage = otelScopesStorageClazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/* 32 */           if (otelScopesStorage != null && otelScopesStorage instanceof IScopesStorage) {
/* 33 */             return (IScopesStorage)otelScopesStorage;
/*    */           }
/* 35 */         } catch (InstantiationException instantiationException) {
/*    */         
/* 37 */         } catch (IllegalAccessException illegalAccessException) {
/*    */         
/* 39 */         } catch (InvocationTargetException invocationTargetException) {
/*    */         
/* 41 */         } catch (NoSuchMethodException noSuchMethodException) {}
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 48 */     return new DefaultScopesStorage();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ScopesStorageFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */