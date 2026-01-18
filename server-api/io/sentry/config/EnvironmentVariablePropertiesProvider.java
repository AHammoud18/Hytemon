/*    */ package io.sentry.config;
/*    */ 
/*    */ import io.sentry.util.StringUtils;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class EnvironmentVariablePropertiesProvider
/*    */   implements PropertiesProvider
/*    */ {
/*    */   private static final String PREFIX = "SENTRY";
/*    */   
/*    */   @Nullable
/*    */   public String getProperty(@NotNull String property) {
/* 19 */     return StringUtils.removeSurrounding(
/* 20 */         System.getenv(propertyToEnvironmentVariableName(property)), "\"");
/*    */   }
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
/*    */   @NotNull
/*    */   public Map<String, String> getMap(@NotNull String property) {
/* 34 */     String prefix = propertyToEnvironmentVariableName(property) + "_";
/*    */     
/* 36 */     Map<String, String> result = new ConcurrentHashMap<>();
/* 37 */     for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
/* 38 */       String key = entry.getKey();
/* 39 */       if (key.startsWith(prefix)) {
/* 40 */         String value = StringUtils.removeSurrounding(entry.getValue(), "\"");
/* 41 */         if (value != null) {
/* 42 */           result.put(key.substring(prefix.length()).toLowerCase(Locale.ROOT), value);
/*    */         }
/*    */       } 
/*    */     } 
/* 46 */     return result;
/*    */   }
/*    */   @NotNull
/*    */   private String propertyToEnvironmentVariableName(@NotNull String property) {
/* 50 */     return "SENTRY_" + property.replace(".", "_").replace("-", "_").toUpperCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\config\EnvironmentVariablePropertiesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */