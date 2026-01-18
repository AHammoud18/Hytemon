/*    */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import java.io.File;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CaveNodeTypeStorage
/*    */ {
/*    */   protected final SeedString<SeedStringResource> seed;
/*    */   protected final Path dataFolder;
/*    */   protected final Path caveFolder;
/*    */   protected final ZoneFileContext zoneContext;
/*    */   @Nonnull
/*    */   protected final Map<String, CaveNodeType> caveNodeTypes;
/*    */   
/*    */   public CaveNodeTypeStorage(SeedString<SeedStringResource> seed, Path dataFolder, Path caveFolder, ZoneFileContext zoneContext) {
/* 34 */     this.seed = seed;
/* 35 */     this.dataFolder = dataFolder;
/* 36 */     this.caveFolder = caveFolder;
/* 37 */     this.zoneContext = zoneContext;
/* 38 */     this.caveNodeTypes = new HashMap<>();
/*    */   }
/*    */   
/*    */   public SeedString<SeedStringResource> getSeed() {
/* 42 */     return this.seed;
/*    */   }
/*    */   
/*    */   public void add(String name, CaveNodeType caveNodeType) {
/* 46 */     if (this.caveNodeTypes.containsKey(name)) {
/* 47 */       throw new Error(String.format("CaveNodeType (%s) has already been added to CaveNodeTypeStorage!", new Object[] { name }));
/*    */     }
/* 49 */     this.caveNodeTypes.put(name, caveNodeType);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CaveNodeType getOrLoadCaveNodeType(@Nonnull String name) {
/* 54 */     CaveNodeType caveNodeType = getCaveNodeType(name);
/* 55 */     if (caveNodeType == null) {
/* 56 */       caveNodeType = loadCaveNodeType(name);
/*    */     }
/* 58 */     return caveNodeType;
/*    */   }
/*    */   
/*    */   public CaveNodeType getCaveNodeType(String name) {
/* 62 */     return this.caveNodeTypes.get(name);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CaveNodeType loadCaveNodeType(@Nonnull String name) {
/* 67 */     Path file = this.caveFolder.resolve(String.format("%s.node.json", new Object[] { name.replace(".", File.separator) })); 
/* 68 */     try { JsonReader reader = new JsonReader(Files.newBufferedReader(file)); 
/* 69 */       try { JsonObject caveNodeJson = JsonParser.parseReader(reader).getAsJsonObject();
/*    */         
/* 71 */         CaveNodeType caveNodeType = (new CaveNodeTypeJsonLoader(this.seed, this.dataFolder, (JsonElement)caveNodeJson, name, this, this.zoneContext)).load();
/* 72 */         reader.close(); return caveNodeType; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 73 */     { throw new Error(String.format("Error while loading CaveNodeType %s for world generator from %s", new Object[] { name, file.toString() }), e); }
/*    */   
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String ERROR_ALREADY_ADDED = "CaveNodeType (%s) has already been added to CaveNodeTypeStorage!";
/*    */     public static final String ERROR_LOADING_CAVE_NODE_TYPE = "Error while loading CaveNodeType %s for world generator from %s";
/*    */     public static final String FILE_SUFFIX = "%s.node.json";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CaveNodeTypeStorage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */