/*     */ package com.nimbusds.jose.shaded.gson;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public final class JsonObject
/*     */   extends JsonElement
/*     */ {
/*  41 */   private final LinkedTreeMap<String, JsonElement> members = new LinkedTreeMap(false);
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
/*     */   public JsonObject deepCopy() {
/*  54 */     JsonObject result = new JsonObject();
/*  55 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)this.members.entrySet()) {
/*  56 */       result.add(entry.getKey(), ((JsonElement)entry.getValue()).deepCopy());
/*     */     }
/*  58 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String property, JsonElement value) {
/*  70 */     this.members.put(property, (value == null) ? JsonNull.INSTANCE : value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonElement remove(String property) {
/*  83 */     return (JsonElement)this.members.remove(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(String property, String value) {
/*  94 */     add(property, (value == null) ? JsonNull.INSTANCE : new JsonPrimitive(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(String property, Number value) {
/* 105 */     add(property, (value == null) ? JsonNull.INSTANCE : new JsonPrimitive(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(String property, Boolean value) {
/* 116 */     add(property, (value == null) ? JsonNull.INSTANCE : new JsonPrimitive(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(String property, Character value) {
/* 127 */     add(property, (value == null) ? JsonNull.INSTANCE : new JsonPrimitive(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, JsonElement>> entrySet() {
/* 137 */     return this.members.entrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 147 */     return this.members.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 157 */     return this.members.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 167 */     return this.members.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean has(String memberName) {
/* 177 */     return this.members.containsKey(memberName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement get(String memberName) {
/* 187 */     return (JsonElement)this.members.get(memberName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPrimitive getAsJsonPrimitive(String memberName) {
/* 199 */     return (JsonPrimitive)this.members.get(memberName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonArray getAsJsonArray(String memberName) {
/* 211 */     return (JsonArray)this.members.get(memberName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObject getAsJsonObject(String memberName) {
/* 223 */     return (JsonObject)this.members.get(memberName);
/*     */   }
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
/*     */   public Map<String, JsonElement> asMap() {
/* 239 */     return (Map<String, JsonElement>)this.members;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 248 */     return (o == this || (o instanceof JsonObject && ((JsonObject)o).members.equals(this.members)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 257 */     return this.members.hashCode();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\JsonObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */