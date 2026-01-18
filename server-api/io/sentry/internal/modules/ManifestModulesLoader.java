/*    */ package io.sentry.internal.modules;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.SentryLevel;
/*    */ import io.sentry.util.ClassLoaderUtils;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Experimental
/*    */ @Internal
/*    */ public final class ManifestModulesLoader extends ModulesLoader {
/* 22 */   private final Pattern URL_LIB_PATTERN = Pattern.compile(".*/(.+)!/META-INF/MANIFEST.MF");
/* 23 */   private final Pattern NAME_AND_VERSION = Pattern.compile("(.*?)-(\\d+\\.\\d+.*).jar");
/*    */   private final ClassLoader classLoader;
/*    */   
/*    */   public ManifestModulesLoader(@NotNull ILogger logger) {
/* 27 */     this(ManifestModulesLoader.class.getClassLoader(), logger);
/*    */   }
/*    */   
/*    */   ManifestModulesLoader(@Nullable ClassLoader classLoader, @NotNull ILogger logger) {
/* 31 */     super(logger);
/* 32 */     this.classLoader = ClassLoaderUtils.classLoaderOrDefault(classLoader);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Map<String, String> loadModules() {
/* 37 */     Map<String, String> modules = new HashMap<>();
/* 38 */     List<Module> detectedModules = detectModulesViaManifestFiles();
/*    */     
/* 40 */     for (Module module : detectedModules) {
/* 41 */       modules.put(module.name, module.version);
/*    */     }
/*    */     
/* 44 */     return modules;
/*    */   }
/*    */   @NotNull
/*    */   private List<Module> detectModulesViaManifestFiles() {
/* 48 */     List<Module> modules = new ArrayList<>();
/*    */     
/*    */     try {
/* 51 */       Enumeration<URL> manifestUrls = this.classLoader.getResources("META-INF/MANIFEST.MF");
/* 52 */       while (manifestUrls.hasMoreElements()) {
/* 53 */         URL manifestUrl = manifestUrls.nextElement();
/* 54 */         String originalName = extractDependencyNameFromUrl(manifestUrl);
/* 55 */         Module module = convertOriginalNameToModule(originalName);
/* 56 */         if (module != null) {
/* 57 */           modules.add(module);
/*    */         }
/*    */       } 
/* 60 */     } catch (Throwable e) {
/* 61 */       this.logger.log(SentryLevel.ERROR, "Unable to detect modules via manifest files.", e);
/*    */     } 
/*    */     
/* 64 */     return modules;
/*    */   }
/*    */   @Nullable
/*    */   private Module convertOriginalNameToModule(@Nullable String originalName) {
/* 68 */     if (originalName == null) {
/* 69 */       return null;
/*    */     }
/*    */     
/* 72 */     Matcher matcher = this.NAME_AND_VERSION.matcher(originalName);
/* 73 */     if (matcher.matches() && matcher.groupCount() == 2) {
/* 74 */       String moduleName = matcher.group(1);
/* 75 */       String moduleVersion = matcher.group(2);
/* 76 */       return new Module(moduleName, moduleVersion);
/*    */     } 
/*    */     
/* 79 */     return null;
/*    */   }
/*    */   @Nullable
/*    */   private String extractDependencyNameFromUrl(@NotNull URL url) {
/* 83 */     String urlString = url.toString();
/* 84 */     Matcher matcher = this.URL_LIB_PATTERN.matcher(urlString);
/* 85 */     if (matcher.matches() && matcher.groupCount() == 1) {
/* 86 */       return matcher.group(1);
/*    */     }
/*    */     
/* 89 */     return null;
/*    */   }
/*    */   
/*    */   private static final class Module {
/*    */     @NotNull
/*    */     private final String name;
/*    */     
/*    */     public Module(@NotNull String name, @NotNull String version) {
/* 97 */       this.name = name;
/* 98 */       this.version = version;
/*    */     }
/*    */     
/*    */     @NotNull
/*    */     private final String version;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\modules\ManifestModulesLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */