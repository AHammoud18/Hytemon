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
/*     */ public final class ViewHierarchyNode
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private String renderingSystem;
/*     */   @Nullable
/*     */   private String type;
/*     */   @Nullable
/*     */   private String identifier;
/*     */   @Nullable
/*     */   private String tag;
/*     */   @Nullable
/*     */   private Double width;
/*     */   @Nullable
/*     */   private Double height;
/*     */   
/*     */   public void setRenderingSystem(String renderingSystem) {
/*  49 */     this.renderingSystem = renderingSystem;
/*     */   } @Nullable private Double x; @Nullable private Double y; @Nullable private String visibility; @Nullable
/*     */   private Double alpha; @Nullable
/*     */   private List<ViewHierarchyNode> children; @Nullable
/*  53 */   private Map<String, Object> unknown; public static final class JsonKeys { public static final String RENDERING_SYSTEM = "rendering_system"; public static final String TYPE = "type"; public static final String IDENTIFIER = "identifier"; public static final String TAG = "tag"; public static final String WIDTH = "width"; public static final String HEIGHT = "height"; public static final String X = "x"; public static final String Y = "y"; public static final String VISIBILITY = "visibility"; public static final String ALPHA = "alpha"; public static final String CHILDREN = "children"; } public void setType(String type) { this.type = type; }
/*     */ 
/*     */   
/*     */   public void setIdentifier(@Nullable String identifier) {
/*  57 */     this.identifier = identifier;
/*     */   }
/*     */   
/*     */   public void setTag(@Nullable String tag) {
/*  61 */     this.tag = tag;
/*     */   }
/*     */   
/*     */   public void setWidth(@Nullable Double width) {
/*  65 */     this.width = width;
/*     */   }
/*     */   
/*     */   public void setHeight(@Nullable Double height) {
/*  69 */     this.height = height;
/*     */   }
/*     */   
/*     */   public void setX(@Nullable Double x) {
/*  73 */     this.x = x;
/*     */   }
/*     */   
/*     */   public void setY(@Nullable Double y) {
/*  77 */     this.y = y;
/*     */   }
/*     */   
/*     */   public void setVisibility(@Nullable String visibility) {
/*  81 */     this.visibility = visibility;
/*     */   }
/*     */   
/*     */   public void setAlpha(@Nullable Double alpha) {
/*  85 */     this.alpha = alpha;
/*     */   }
/*     */   
/*     */   public void setChildren(@Nullable List<ViewHierarchyNode> children) {
/*  89 */     this.children = children;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getRenderingSystem() {
/*  94 */     return this.renderingSystem;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getType() {
/*  99 */     return this.type;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getIdentifier() {
/* 104 */     return this.identifier;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getTag() {
/* 109 */     return this.tag;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getWidth() {
/* 114 */     return this.width;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getHeight() {
/* 119 */     return this.height;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getX() {
/* 124 */     return this.x;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getY() {
/* 129 */     return this.y;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getVisibility() {
/* 134 */     return this.visibility;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getAlpha() {
/* 139 */     return this.alpha;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<ViewHierarchyNode> getChildren() {
/* 144 */     return this.children;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 150 */     writer.beginObject();
/* 151 */     if (this.renderingSystem != null) {
/* 152 */       writer.name("rendering_system").value(this.renderingSystem);
/*     */     }
/* 154 */     if (this.type != null) {
/* 155 */       writer.name("type").value(this.type);
/*     */     }
/* 157 */     if (this.identifier != null) {
/* 158 */       writer.name("identifier").value(this.identifier);
/*     */     }
/* 160 */     if (this.tag != null) {
/* 161 */       writer.name("tag").value(this.tag);
/*     */     }
/* 163 */     if (this.width != null) {
/* 164 */       writer.name("width").value(this.width);
/*     */     }
/* 166 */     if (this.height != null) {
/* 167 */       writer.name("height").value(this.height);
/*     */     }
/* 169 */     if (this.x != null) {
/* 170 */       writer.name("x").value(this.x);
/*     */     }
/* 172 */     if (this.y != null) {
/* 173 */       writer.name("y").value(this.y);
/*     */     }
/* 175 */     if (this.visibility != null) {
/* 176 */       writer.name("visibility").value(this.visibility);
/*     */     }
/* 178 */     if (this.alpha != null) {
/* 179 */       writer.name("alpha").value(this.alpha);
/*     */     }
/* 181 */     if (this.children != null && !this.children.isEmpty()) {
/* 182 */       writer.name("children").value(logger, this.children);
/*     */     }
/*     */     
/* 185 */     if (this.unknown != null) {
/* 186 */       for (String key : this.unknown.keySet()) {
/* 187 */         Object value = this.unknown.get(key);
/* 188 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 191 */     writer.endObject();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 196 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 201 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ViewHierarchyNode>
/*     */   {
/*     */     @NotNull
/*     */     public ViewHierarchyNode deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 209 */       Map<String, Object> unknown = null;
/* 210 */       ViewHierarchyNode node = new ViewHierarchyNode();
/*     */       
/* 212 */       reader.beginObject();
/* 213 */       while (reader.peek() == JsonToken.NAME) {
/* 214 */         String nextName = reader.nextName();
/* 215 */         switch (nextName) {
/*     */           case "rendering_system":
/* 217 */             node.renderingSystem = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "type":
/* 220 */             node.type = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "identifier":
/* 223 */             node.identifier = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "tag":
/* 226 */             node.tag = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "width":
/* 229 */             node.width = reader.nextDoubleOrNull();
/*     */             continue;
/*     */           case "height":
/* 232 */             node.height = reader.nextDoubleOrNull();
/*     */             continue;
/*     */           case "x":
/* 235 */             node.x = reader.nextDoubleOrNull();
/*     */             continue;
/*     */           case "y":
/* 238 */             node.y = reader.nextDoubleOrNull();
/*     */             continue;
/*     */           case "visibility":
/* 241 */             node.visibility = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "alpha":
/* 244 */             node.alpha = reader.nextDoubleOrNull();
/*     */             continue;
/*     */           case "children":
/* 247 */             node.children = reader.nextListOrNull(logger, this);
/*     */             continue;
/*     */         } 
/* 250 */         if (unknown == null) {
/* 251 */           unknown = new HashMap<>();
/*     */         }
/* 253 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 257 */       reader.endObject();
/*     */       
/* 259 */       node.setUnknown(unknown);
/* 260 */       return node;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\ViewHierarchyNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */