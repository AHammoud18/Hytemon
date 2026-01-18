/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class SentryAttributes {
/*    */   @NotNull
/*    */   private final Map<String, SentryAttribute> attributes;
/*    */   
/*    */   private SentryAttributes(@NotNull Map<String, SentryAttribute> attributes) {
/* 13 */     this.attributes = attributes;
/*    */   }
/*    */   
/*    */   public void add(@Nullable SentryAttribute attribute) {
/* 17 */     if (attribute == null) {
/*    */       return;
/*    */     }
/* 20 */     this.attributes.put(attribute.getName(), attribute);
/*    */   }
/*    */   @NotNull
/*    */   public Map<String, SentryAttribute> getAttributes() {
/* 24 */     return this.attributes;
/*    */   }
/*    */   @NotNull
/*    */   public static SentryAttributes of(@Nullable SentryAttribute... attributes) {
/* 28 */     if (attributes == null) {
/* 29 */       return new SentryAttributes(new ConcurrentHashMap<>());
/*    */     }
/* 31 */     SentryAttributes sentryAttributes = new SentryAttributes(new ConcurrentHashMap<>(attributes.length));
/*    */     
/* 33 */     for (SentryAttribute attribute : attributes) {
/* 34 */       sentryAttributes.add(attribute);
/*    */     }
/* 36 */     return sentryAttributes;
/*    */   }
/*    */   @NotNull
/*    */   public static SentryAttributes fromMap(@Nullable Map<String, Object> attributes) {
/* 40 */     if (attributes == null) {
/* 41 */       return new SentryAttributes(new ConcurrentHashMap<>());
/*    */     }
/*    */     
/* 44 */     SentryAttributes sentryAttributes = new SentryAttributes(new ConcurrentHashMap<>(attributes.size()));
/* 45 */     for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
/* 46 */       String key = attribute.getKey();
/* 47 */       if (key != null) {
/* 48 */         sentryAttributes.add(SentryAttribute.named(key, attribute.getValue()));
/*    */       }
/*    */     } 
/*    */     
/* 52 */     return sentryAttributes;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */