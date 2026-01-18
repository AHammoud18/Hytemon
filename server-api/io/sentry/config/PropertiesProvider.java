/*     */ package io.sentry.config;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface PropertiesProvider
/*     */ {
/*     */   @Nullable
/*     */   String getProperty(@NotNull String paramString);
/*     */   
/*     */   @NotNull
/*     */   Map<String, String> getMap(@NotNull String paramString);
/*     */   
/*     */   @NotNull
/*     */   default List<String> getList(@NotNull String property) {
/*  37 */     String value = getProperty(property);
/*  38 */     return (value != null) ? Arrays.<String>asList(value.split(",")) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default List<String> getListOrNull(@NotNull String property) {
/*  49 */     String value = getProperty(property);
/*  50 */     return (value != null) ? Arrays.<String>asList(value.split(",")) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default String getProperty(@NotNull String property, @NotNull String defaultValue) {
/*  62 */     String result = getProperty(property);
/*  63 */     return (result != null) ? result : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default Boolean getBooleanProperty(@NotNull String property) {
/*  74 */     String result = getProperty(property);
/*  75 */     return (result != null) ? Boolean.valueOf(result) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default Double getDoubleProperty(@NotNull String property) {
/*  86 */     String prop = getProperty(property);
/*  87 */     Double result = null;
/*  88 */     if (prop != null) {
/*     */       try {
/*  90 */         result = Double.valueOf(prop);
/*  91 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */     
/*  95 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default Long getLongProperty(@NotNull String property) {
/* 106 */     String prop = getProperty(property);
/* 107 */     Long result = null;
/* 108 */     if (prop != null) {
/*     */       try {
/* 110 */         result = Long.valueOf(prop);
/* 111 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */     
/* 115 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\config\PropertiesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */