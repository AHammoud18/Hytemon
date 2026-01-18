/*    */ package io.sentry.config;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CompositePropertiesProvider
/*    */   implements PropertiesProvider
/*    */ {
/*    */   @NotNull
/*    */   private final List<PropertiesProvider> providers;
/*    */   
/*    */   public CompositePropertiesProvider(@NotNull List<PropertiesProvider> providers) {
/* 18 */     this.providers = providers;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getProperty(@NotNull String property) {
/* 23 */     for (PropertiesProvider provider : this.providers) {
/* 24 */       String result = provider.getProperty(property);
/* 25 */       if (result != null) {
/* 26 */         return result;
/*    */       }
/*    */     } 
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Map<String, String> getMap(@NotNull String property) {
/* 34 */     Map<String, String> result = new ConcurrentHashMap<>();
/* 35 */     for (PropertiesProvider provider : this.providers) {
/* 36 */       result.putAll(provider.getMap(property));
/*    */     }
/* 38 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\config\CompositePropertiesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */