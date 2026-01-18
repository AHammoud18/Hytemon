/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import java.io.File;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JsonLoader<K extends SeedResource, T>
/*     */   extends Loader<K, T>
/*     */ {
/*     */   @Nullable
/*     */   protected final JsonElement json;
/*     */   
/*     */   public JsonLoader(SeedString<K> seed, Path dataFolder, @Nullable JsonElement json) {
/*  28 */     super(seed, dataFolder);
/*  29 */     if (json != null && json.isJsonObject() && json.getAsJsonObject().has("File")) {
/*  30 */       this.json = loadFileConstructor(json.getAsJsonObject().get("File").getAsString());
/*     */     } else {
/*  32 */       this.json = json;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean has(String name) {
/*  37 */     return (this.json != null && this.json.isJsonObject() && this.json.getAsJsonObject().has(name));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public JsonElement get(String name) {
/*  42 */     if (this.json == null || !this.json.isJsonObject()) return null; 
/*  43 */     JsonElement element = this.json.getAsJsonObject().get(name);
/*  44 */     if (element != null && element.isJsonObject()) {
/*  45 */       JsonObject object = element.getAsJsonObject();
/*  46 */       if (object.has("File")) {
/*  47 */         element = loadFileElem(object.get("File").getAsString());
/*     */       }
/*     */     } 
/*  50 */     return element;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public JsonElement getRaw(String name) {
/*  55 */     if (this.json == null || !this.json.isJsonObject()) return null; 
/*  56 */     return this.json.getAsJsonObject().get(name);
/*     */   }
/*     */   
/*     */   protected JsonElement loadFile(@Nonnull String filePath) {
/*  60 */     Path file = this.dataFolder.resolve(filePath.replace('.', File.separatorChar) + ".json");
/*     */     try {
/*  62 */       JsonReader reader = new JsonReader(Files.newBufferedReader(file)); 
/*  63 */       try { JsonElement jsonElement = JsonParser.parseReader(reader);
/*  64 */         reader.close(); return jsonElement; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; } 
/*  65 */     } catch (Throwable e) {
/*  66 */       throw new Error("Error while loading file reference." + file.toString(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected JsonElement loadFileElem(@Nonnull String filePath) {
/*  71 */     return loadFile(filePath);
/*     */   }
/*     */   
/*     */   protected JsonElement loadFileConstructor(@Nonnull String filePath) {
/*  75 */     return loadFile(filePath);
/*     */   }
/*     */   @Nonnull
/*     */   protected JsonObject mustGetObject(@Nonnull String key, @Nullable JsonObject defaultValue) {
/*  79 */     return mustGet(key, defaultValue, JsonObject.class, JsonElement::isJsonObject, JsonElement::getAsJsonObject);
/*     */   }
/*     */   @Nonnull
/*     */   protected JsonArray mustGetArray(@Nonnull String key, @Nullable JsonArray defaultValue) {
/*  83 */     return mustGet(key, defaultValue, JsonArray.class, JsonElement::isJsonArray, JsonElement::getAsJsonArray);
/*     */   }
/*     */   @Nonnull
/*     */   protected String mustGetString(@Nonnull String key, @Nullable String defaultValue) {
/*  87 */     return mustGet(key, defaultValue, String.class, JsonLoader::isString, JsonElement::getAsString);
/*     */   }
/*     */   @Nonnull
/*     */   protected Boolean mustGetBool(@Nonnull String key, @Nullable Boolean defaultValue) {
/*  91 */     return mustGet(key, defaultValue, Boolean.class, JsonLoader::isBoolean, JsonElement::getAsBoolean);
/*     */   }
/*     */   @Nonnull
/*     */   protected Number mustGetNumber(@Nonnull String key, @Nullable Number defaultValue) {
/*  95 */     return mustGet(key, defaultValue, Number.class, JsonLoader::isNumber, JsonElement::getAsNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <V> V mustGet(@Nonnull String key, @Nullable V defaultValue, @Nonnull Class<V> type, @Nonnull Predicate<JsonElement> predicate, @Nonnull Function<JsonElement, V> mapper) {
/* 103 */     return mustGet(key, get(key), defaultValue, type, predicate, mapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static <V> V mustGet(@Nonnull String key, @Nullable JsonElement element, @Nullable V defaultValue, @Nonnull Class<V> type, @Nonnull Predicate<JsonElement> predicate, @Nonnull Function<JsonElement, V> mapper) {
/* 113 */     if (element == null) {
/* 114 */       if (defaultValue != null) {
/* 115 */         return defaultValue;
/*     */       }
/*     */       
/* 118 */       throw error("Missing property '%s'", new Object[] { key });
/*     */     } 
/*     */     
/* 121 */     if (!predicate.test(element)) {
/* 122 */       throw error("Property '%s' must be of type '%s'", new Object[] { key, type.getSimpleName() });
/*     */     }
/*     */     
/* 125 */     return mapper.apply(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Error error(String format, Object... args) {
/* 134 */     return new Error(String.format(format, args));
/*     */   }
/*     */   
/*     */   protected static Error error(Throwable parent, String format, Object... args) {
/* 138 */     return new Error(String.format(format, args), parent);
/*     */   }
/*     */   
/*     */   private static boolean isString(JsonElement element) {
/* 142 */     return (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString());
/*     */   }
/*     */   
/*     */   protected static boolean isNumber(JsonElement element) {
/* 146 */     return (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber());
/*     */   }
/*     */   
/*     */   protected static boolean isBoolean(JsonElement element) {
/* 150 */     return (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean());
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final char JSON_FILEPATH_SEPARATOR = '.';
/*     */     public static final String KEY_FILE = "File";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\JsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */