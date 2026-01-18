/*    */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.CaveGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CaveGeneratorJsonLoader
/*    */   extends JsonLoader<SeedStringResource, CaveGenerator>
/*    */ {
/*    */   protected final Path caveFolder;
/*    */   protected final ZoneFileContext zoneContext;
/*    */   
/*    */   public CaveGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, Path caveFolder, ZoneFileContext zoneContext) {
/* 30 */     super(seed.append(".CaveGenerator"), dataFolder, json);
/* 31 */     this.caveFolder = caveFolder;
/* 32 */     this.zoneContext = zoneContext;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public CaveGenerator load() {
/* 38 */     CaveGenerator caveGenerator = null;
/* 39 */     if (this.caveFolder != null && Files.exists(this.caveFolder, new java.nio.file.LinkOption[0])) {
/* 40 */       Path file = this.caveFolder.resolve("Caves.json");
/*    */       try {
/*    */         JsonObject cavesJson;
/* 43 */         JsonReader reader = new JsonReader(Files.newBufferedReader(file)); 
/* 44 */         try { cavesJson = JsonParser.parseReader(reader).getAsJsonObject();
/* 45 */           reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */            throw throwable; }
/* 47 */          caveGenerator = new CaveGenerator(loadCaveTypes(cavesJson));
/*    */       }
/* 49 */       catch (Throwable e) {
/* 50 */         JsonObject cavesJson; throw new Error(String.format("Error while loading caves for world generator from %s", new Object[] { file.toString() }), cavesJson);
/*    */       } 
/*    */     } 
/* 53 */     return caveGenerator;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CaveType[] loadCaveTypes(@Nonnull JsonObject jsonObject) {
/* 58 */     return (new CaveTypesJsonLoader(this.seed, this.dataFolder, jsonObject.get("Types"), this.caveFolder, this.zoneContext))
/* 59 */       .load();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String FILE_CAVES_JSON = "Caves.json";
/*    */     public static final String KEY_TYPES = "Types";
/*    */     public static final String ERROR_LOADING_CAVES = "Error while loading caves for world generator from %s";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CaveGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */