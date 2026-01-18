/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.SentryAttribute;
/*    */ import io.sentry.SentryAttributes;
/*    */ import io.sentry.SentryEvent;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ @Internal
/*    */ public final class LoggerPropertiesUtil
/*    */ {
/*    */   @Internal
/*    */   public static void applyPropertiesToEvent(@NotNull SentryEvent event, @NotNull List<String> targetKeys, @NotNull Map<String, String> properties, @NotNull String contextName) {
/* 33 */     if (!targetKeys.isEmpty() && !properties.isEmpty()) {
/* 34 */       for (String key : targetKeys) {
/* 35 */         String value = properties.remove(key);
/* 36 */         if (value != null) {
/* 37 */           event.setTag(key, value);
/*    */         }
/*    */       } 
/*    */     }
/* 41 */     if (!properties.isEmpty()) {
/* 42 */       event.getContexts().put(contextName, properties);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void applyPropertiesToEvent(@NotNull SentryEvent event, @NotNull List<String> targetKeys, @NotNull Map<String, String> properties) {
/* 50 */     applyPropertiesToEvent(event, targetKeys, properties, "MDC");
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
/*    */ 
/*    */ 
/*    */   
/*    */   @Internal
/*    */   public static void applyPropertiesToAttributes(@NotNull SentryAttributes attributes, @NotNull List<String> targetKeys, @NotNull Map<String, String> properties) {
/* 67 */     if (!targetKeys.isEmpty() && !properties.isEmpty())
/* 68 */       for (String key : targetKeys) {
/* 69 */         String value = properties.get(key);
/* 70 */         if (value != null)
/* 71 */           attributes.add(SentryAttribute.stringAttribute("mdc." + key, value)); 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\LoggerPropertiesUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */