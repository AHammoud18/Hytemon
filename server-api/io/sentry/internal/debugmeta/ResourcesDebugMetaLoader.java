/*    */ package io.sentry.internal.debugmeta;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.SentryLevel;
/*    */ import io.sentry.util.ClassLoaderUtils;
/*    */ import io.sentry.util.DebugMetaPropertiesApplier;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Enumeration;
/*    */ import java.util.List;
/*    */ import java.util.Properties;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class ResourcesDebugMetaLoader implements IDebugMetaLoader {
/*    */   @NotNull
/*    */   private final ILogger logger;
/*    */   @NotNull
/*    */   private final ClassLoader classLoader;
/*    */   
/*    */   public ResourcesDebugMetaLoader(@NotNull ILogger logger) {
/* 26 */     this(logger, ResourcesDebugMetaLoader.class.getClassLoader());
/*    */   }
/*    */   
/*    */   ResourcesDebugMetaLoader(@NotNull ILogger logger, @Nullable ClassLoader classLoader) {
/* 30 */     this.logger = logger;
/* 31 */     this.classLoader = ClassLoaderUtils.classLoaderOrDefault(classLoader);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public List<Properties> loadDebugMeta() {
/* 36 */     List<Properties> debugPropertyList = new ArrayList<>();
/*    */     
/*    */     try {
/* 39 */       Enumeration<URL> resourceUrls = this.classLoader.getResources(DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
/*    */       
/* 41 */       while (resourceUrls.hasMoreElements()) {
/* 42 */         URL currentUrl = resourceUrls.nextElement(); 
/* 43 */         try { InputStream is = currentUrl.openStream(); 
/* 44 */           try { Properties currentProperties = new Properties();
/* 45 */             currentProperties.load(is);
/* 46 */             debugPropertyList.add(currentProperties);
/* 47 */             this.logger.log(SentryLevel.INFO, "Debug Meta Data Properties loaded from %s", new Object[] { currentUrl });
/* 48 */             if (is != null) is.close();  } catch (Throwable throwable) { if (is != null) try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (RuntimeException e)
/* 49 */         { this.logger.log(SentryLevel.ERROR, e, "%s file is malformed.", new Object[] { currentUrl }); }
/*    */       
/*    */       } 
/* 52 */     } catch (IOException e) {
/* 53 */       this.logger.log(SentryLevel.ERROR, e, "Failed to load %s", new Object[] { DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME });
/*    */     } 
/*    */     
/* 56 */     if (debugPropertyList.isEmpty()) {
/* 57 */       this.logger.log(SentryLevel.INFO, "No %s file was found.", new Object[] { DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME });
/* 58 */       return null;
/*    */     } 
/*    */     
/* 61 */     return debugPropertyList;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\debugmeta\ResourcesDebugMetaLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */