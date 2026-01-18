/*    */ package io.sentry.config;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import io.sentry.util.StringUtils;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ abstract class AbstractPropertiesProvider
/*    */   implements PropertiesProvider
/*    */ {
/*    */   @NotNull
/*    */   private final String prefix;
/*    */   @NotNull
/*    */   private final Properties properties;
/*    */   
/*    */   protected AbstractPropertiesProvider(@NotNull String prefix, @NotNull Properties properties) {
/* 20 */     this.prefix = (String)Objects.requireNonNull(prefix, "prefix is required");
/* 21 */     this.properties = (Properties)Objects.requireNonNull(properties, "properties are required");
/*    */   }
/*    */   
/*    */   protected AbstractPropertiesProvider(@NotNull Properties properties) {
/* 25 */     this("", properties);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getProperty(@NotNull String property) {
/* 30 */     return StringUtils.removeSurrounding(this.properties.getProperty(this.prefix + property), "\"");
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
/* 44 */     String prefix = this.prefix + property + ".";
/*    */     
/* 46 */     Map<String, String> result = new HashMap<>();
/* 47 */     for (Map.Entry<Object, Object> entry : this.properties.entrySet()) {
/* 48 */       if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
/* 49 */         String key = (String)entry.getKey();
/* 50 */         if (key.startsWith(prefix)) {
/* 51 */           String value = StringUtils.removeSurrounding((String)entry.getValue(), "\"");
/* 52 */           result.put(key.substring(prefix.length()), value);
/*    */         } 
/*    */       } 
/*    */     } 
/* 56 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\config\AbstractPropertiesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */