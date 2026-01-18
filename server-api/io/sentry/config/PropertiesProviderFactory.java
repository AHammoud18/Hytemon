/*    */ package io.sentry.config;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.SystemOutLogger;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Properties;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class PropertiesProviderFactory
/*    */ {
/*    */   @NotNull
/*    */   public static PropertiesProvider create() {
/* 32 */     SystemOutLogger systemOutLogger = new SystemOutLogger();
/* 33 */     List<PropertiesProvider> providers = new ArrayList<>();
/* 34 */     providers.add(new SystemPropertyPropertiesProvider());
/* 35 */     providers.add(new EnvironmentVariablePropertiesProvider());
/*    */     
/* 37 */     String systemPropertyLocation = System.getProperty("sentry.properties.file");
/* 38 */     if (systemPropertyLocation != null) {
/*    */       
/* 40 */       Properties properties1 = (new FilesystemPropertiesLoader(systemPropertyLocation, (ILogger)systemOutLogger)).load();
/* 41 */       if (properties1 != null) {
/* 42 */         providers.add(new SimplePropertiesProvider(properties1));
/*    */       }
/*    */     } 
/*    */     
/* 46 */     String environmentVariablesLocation = System.getenv("SENTRY_PROPERTIES_FILE");
/* 47 */     if (environmentVariablesLocation != null) {
/*    */       
/* 49 */       Properties properties1 = (new FilesystemPropertiesLoader(environmentVariablesLocation, (ILogger)systemOutLogger)).load();
/* 50 */       if (properties1 != null) {
/* 51 */         providers.add(new SimplePropertiesProvider(properties1));
/*    */       }
/*    */     } 
/*    */     
/* 55 */     Properties properties = (new ClasspathPropertiesLoader((ILogger)systemOutLogger)).load();
/* 56 */     if (properties != null) {
/* 57 */       providers.add(new SimplePropertiesProvider(properties));
/*    */     }
/*    */ 
/*    */     
/* 61 */     Properties runDirectoryProperties = (new FilesystemPropertiesLoader("sentry.properties", (ILogger)systemOutLogger, false)).load();
/* 62 */     if (runDirectoryProperties != null) {
/* 63 */       providers.add(new SimplePropertiesProvider(runDirectoryProperties));
/*    */     }
/*    */     
/* 66 */     return new CompositePropertiesProvider(providers);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\config\PropertiesProviderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */