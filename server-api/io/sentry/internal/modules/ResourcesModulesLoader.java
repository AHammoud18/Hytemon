/*    */ package io.sentry.internal.modules;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.SentryLevel;
/*    */ import io.sentry.util.ClassLoaderUtils;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class ResourcesModulesLoader
/*    */   extends ModulesLoader {
/*    */   @NotNull
/*    */   private final ClassLoader classLoader;
/*    */   
/*    */   public ResourcesModulesLoader(@NotNull ILogger logger) {
/* 21 */     this(logger, ResourcesModulesLoader.class.getClassLoader());
/*    */   }
/*    */   
/*    */   ResourcesModulesLoader(@NotNull ILogger logger, @Nullable ClassLoader classLoader) {
/* 25 */     super(logger);
/* 26 */     this.classLoader = ClassLoaderUtils.classLoaderOrDefault(classLoader);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Map<String, String> loadModules() {
/* 31 */     Map<String, String> modules = new TreeMap<>();
/*    */     
/* 33 */     try { InputStream resourcesStream = this.classLoader.getResourceAsStream("sentry-external-modules.txt");
/*    */       
/* 35 */       try { if (resourcesStream == null)
/* 36 */         { this.logger.log(SentryLevel.INFO, "%s file was not found.", new Object[] { "sentry-external-modules.txt" });
/* 37 */           Map<String, String> map1 = modules;
/*    */ 
/*    */ 
/*    */           
/* 41 */           if (resourcesStream != null) resourcesStream.close();  return map1; }  Map<String, String> map = parseStream(resourcesStream); if (resourcesStream != null) resourcesStream.close();  return map; } catch (Throwable throwable) { if (resourcesStream != null) try { resourcesStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SecurityException e)
/* 42 */     { this.logger.log(SentryLevel.INFO, "Access to resources denied.", e); }
/* 43 */     catch (IOException e)
/* 44 */     { this.logger.log(SentryLevel.INFO, "Access to resources failed.", e); }
/*    */     
/* 46 */     return modules;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\modules\ResourcesModulesLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */