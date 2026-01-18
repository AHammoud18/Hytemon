/*    */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CaveTypesJsonLoader
/*    */   extends JsonLoader<SeedStringResource, CaveType[]>
/*    */ {
/*    */   protected final Path caveFolder;
/*    */   protected final ZoneFileContext zoneContext;
/*    */   
/*    */   public CaveTypesJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, Path caveFolder, ZoneFileContext zoneContext) {
/* 26 */     super(seed, dataFolder, json);
/* 27 */     this.caveFolder = caveFolder;
/* 28 */     this.zoneContext = zoneContext;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CaveType[] load() {
/* 34 */     if (this.json == null || !this.json.isJsonArray()) throw new IllegalArgumentException("CaveTypes must be a JSON array."); 
/* 35 */     JsonArray typesArray = this.json.getAsJsonArray();
/* 36 */     CaveType[] caveTypes = new CaveType[typesArray.size()];
/* 37 */     for (int i = 0; i < typesArray.size(); i++) {
/* 38 */       JsonObject caveTypeObject = typesArray.get(i).getAsJsonObject();
/* 39 */       String name = loadName(caveTypeObject);
/* 40 */       caveTypes[i] = loadCaveType(name, (JsonElement)caveTypeObject);
/*    */     } 
/* 42 */     return caveTypes;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CaveType loadCaveType(String name, JsonElement json) {
/* 47 */     return (new CaveTypeJsonLoader(this.seed.append(String.format("-%s", new Object[] { name })), this.dataFolder, json, this.caveFolder, name, this.zoneContext))
/* 48 */       .load();
/*    */   }
/*    */   
/*    */   protected String loadName(@Nonnull JsonObject jsonObject) {
/* 52 */     return jsonObject.get("Name").getAsString();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_NAME = "Name";
/*    */     public static final String SEED_CAVE_TYPE_SUFFIX = "-%s";
/*    */     public static final String ERROR_NOT_AN_ARRAY = "CaveTypes must be a JSON array.";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CaveTypesJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */