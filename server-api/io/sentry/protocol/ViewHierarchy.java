/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ViewHierarchy
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private final String renderingSystem;
/*     */   @Nullable
/*     */   private final List<ViewHierarchyNode> windows;
/*     */   
/*     */   public ViewHierarchy(@Nullable String renderingSystem, @Nullable List<ViewHierarchyNode> windows) {
/*  30 */     this.renderingSystem = renderingSystem;
/*  31 */     this.windows = windows;
/*     */   } @Nullable
/*     */   private Map<String, Object> unknown; public static final class JsonKeys {
/*     */     public static final String RENDERING_SYSTEM = "rendering_system"; public static final String WINDOWS = "windows"; } @Nullable
/*     */   public String getRenderingSystem() {
/*  36 */     return this.renderingSystem;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<ViewHierarchyNode> getWindows() {
/*  41 */     return this.windows;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  47 */     writer.beginObject();
/*  48 */     if (this.renderingSystem != null) {
/*  49 */       writer.name("rendering_system").value(this.renderingSystem);
/*     */     }
/*  51 */     if (this.windows != null) {
/*  52 */       writer.name("windows").value(logger, this.windows);
/*     */     }
/*  54 */     if (this.unknown != null) {
/*  55 */       for (String key : this.unknown.keySet()) {
/*  56 */         Object value = this.unknown.get(key);
/*  57 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*  60 */     writer.endObject();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  65 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  70 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ViewHierarchy>
/*     */   {
/*     */     @NotNull
/*     */     public ViewHierarchy deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*  79 */       String renderingSystem = null;
/*  80 */       List<ViewHierarchyNode> windows = null;
/*  81 */       Map<String, Object> unknown = null;
/*     */       
/*  83 */       reader.beginObject();
/*  84 */       while (reader.peek() == JsonToken.NAME) {
/*  85 */         String nextName = reader.nextName();
/*  86 */         switch (nextName) {
/*     */           case "rendering_system":
/*  88 */             renderingSystem = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "windows":
/*  91 */             windows = reader.nextListOrNull(logger, new ViewHierarchyNode.Deserializer());
/*     */             continue;
/*     */         } 
/*  94 */         if (unknown == null) {
/*  95 */           unknown = new HashMap<>();
/*     */         }
/*  97 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 101 */       reader.endObject();
/*     */       
/* 103 */       ViewHierarchy viewHierarchy = new ViewHierarchy(renderingSystem, windows);
/* 104 */       viewHierarchy.setUnknown(unknown);
/* 105 */       return viewHierarchy;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\ViewHierarchy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */