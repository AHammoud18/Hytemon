/*    */ package io.sentry.internal.modules;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NoOpModulesLoader
/*    */   implements IModulesLoader {
/*  8 */   private static final NoOpModulesLoader instance = new NoOpModulesLoader();
/*    */   
/*    */   public static NoOpModulesLoader getInstance() {
/* 11 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Map<String, String> getOrLoadModules() {
/* 18 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\modules\NoOpModulesLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */