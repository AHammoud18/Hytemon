/*    */ package io.sentry.internal.modules;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
/*    */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Experimental
/*    */ @Internal
/*    */ public final class CompositeModulesLoader
/*    */   extends ModulesLoader
/*    */ {
/*    */   private final List<IModulesLoader> loaders;
/*    */   
/*    */   public CompositeModulesLoader(@NotNull List<IModulesLoader> loaders, @NotNull ILogger logger) {
/* 19 */     super(logger);
/* 20 */     this.loaders = loaders;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Map<String, String> loadModules() {
/* 25 */     TreeMap<String, String> allModules = new TreeMap<>();
/*    */     
/* 27 */     for (IModulesLoader loader : this.loaders) {
/* 28 */       Map<String, String> modules = loader.getOrLoadModules();
/* 29 */       if (modules != null) {
/* 30 */         allModules.putAll(modules);
/*    */       }
/*    */     } 
/*    */     
/* 34 */     return allModules;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\modules\CompositeModulesLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */