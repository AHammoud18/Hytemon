/*     */ package com.google.common.flogger.backend;
/*     */ 
/*     */ import com.google.common.flogger.AbstractLogger;
/*     */ import com.google.common.flogger.LogSite;
/*     */ import com.google.common.flogger.context.ContextDataProvider;
/*     */ import com.google.common.flogger.context.Tags;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
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
/*     */ public abstract class Platform
/*     */ {
/*  46 */   private static String DEFAULT_PLATFORM = "com.google.common.flogger.backend.system.DefaultPlatform";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static final String[] AVAILABLE_PLATFORMS = new String[] { DEFAULT_PLATFORM };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class LazyHolder
/*     */   {
/*  65 */     private static final Platform INSTANCE = loadFirstAvailablePlatform(Platform.AVAILABLE_PLATFORMS);
/*     */     
/*     */     private static Platform loadFirstAvailablePlatform(String[] platformClass) {
/*  68 */       Platform platform = null;
/*     */       
/*     */       try {
/*  71 */         platform = PlatformProvider.getPlatform();
/*  72 */       } catch (NoClassDefFoundError noClassDefFoundError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  78 */       if (platform != null) {
/*  79 */         return platform;
/*     */       }
/*     */       
/*  82 */       StringBuilder errorMessage = new StringBuilder();
/*     */       
/*  84 */       for (String clazz : platformClass) {
/*     */         try {
/*  86 */           return Class.forName(clazz).getConstructor(new Class[0]).newInstance(new Object[0]);
/*  87 */         } catch (Throwable e) {
/*     */ 
/*     */           
/*  90 */           if (e instanceof java.lang.reflect.InvocationTargetException) {
/*  91 */             e = e.getCause();
/*     */           }
/*  93 */           errorMessage.append('\n').append(clazz).append(": ").append(e);
/*     */         } 
/*     */       } 
/*  96 */       throw new IllegalStateException(errorMessage
/*  97 */           .insert(0, "No logging platforms found:").toString());
/*     */     }
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
/*     */   public static abstract class LogCallerFinder
/*     */   {
/*     */     public abstract String findLoggingClass(Class<? extends AbstractLogger<?>> param1Class);
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
/*     */     public abstract LogSite findLogSite(Class<?> param1Class, int param1Int);
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
/*     */   public static LogCallerFinder getCallerFinder() {
/* 161 */     return LazyHolder.INSTANCE.getCallerFinderImpl();
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
/*     */   public static LoggerBackend getBackend(String className) {
/* 176 */     return LazyHolder.INSTANCE.getBackendImpl(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ContextDataProvider getContextDataProvider() {
/* 187 */     return LazyHolder.INSTANCE.getContextDataProviderImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ContextDataProvider getContextDataProviderImpl() {
/* 193 */     return NoOpContextDataProvider.getInstance();
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
/*     */   public static boolean shouldForceLogging(String loggerName, Level level, boolean isEnabled) {
/* 210 */     return getContextDataProvider().shouldForceLogging(loggerName, level, isEnabled);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Tags getInjectedTags() {
/* 215 */     return getContextDataProvider().getTags();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Metadata getInjectedMetadata() {
/* 221 */     return getContextDataProvider().getMetadata();
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
/*     */   public static long getCurrentTimeNanos() {
/* 234 */     return LazyHolder.INSTANCE.getCurrentTimeNanosImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long getCurrentTimeNanosImpl() {
/* 242 */     return TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis());
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
/*     */   public static String getConfigInfo() {
/* 260 */     return LazyHolder.INSTANCE.getConfigInfoImpl();
/*     */   }
/*     */   
/*     */   protected abstract LogCallerFinder getCallerFinderImpl();
/*     */   
/*     */   protected abstract LoggerBackend getBackendImpl(String paramString);
/*     */   
/*     */   protected abstract String getConfigInfoImpl();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */