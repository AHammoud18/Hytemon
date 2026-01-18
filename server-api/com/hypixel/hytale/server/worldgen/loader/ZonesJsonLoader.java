/*    */ package com.hypixel.hytale.server.worldgen.loader;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.hypixel.hytale.procedurallib.json.Loader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.FileContext;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.FileLoadingContext;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import com.hypixel.hytale.server.worldgen.loader.zone.ZoneJsonLoader;
/*    */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZonesJsonLoader
/*    */   extends Loader<SeedStringResource, Zone[]>
/*    */ {
/*    */   protected final FileLoadingContext loadingContext;
/*    */   
/*    */   public ZonesJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, FileLoadingContext loadingContext) {
/* 27 */     super(seed.append(".Zones"), dataFolder);
/* 28 */     this.loadingContext = loadingContext;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Zone[] load() {
/* 34 */     FileContext.Registry<ZoneFileContext> zoneRegistry = this.loadingContext.getZones();
/* 35 */     int index = 0;
/* 36 */     Zone[] zones = new Zone[zoneRegistry.size()];
/* 37 */     for (Map.Entry<String, ZoneFileContext> zoneEntry : zoneRegistry) {
/*    */       
/* 39 */       ZoneFileContext zoneContext = zoneEntry.getValue(); 
/* 40 */       try { JsonReader reader = new JsonReader(Files.newBufferedReader(zoneContext.getPath().resolve("Zone.json"))); 
/* 41 */         try { JsonElement zoneJson = JsonParser.parseReader(reader);
/*    */           
/* 43 */           Zone zone = (new ZoneJsonLoader(this.seed, this.dataFolder, zoneJson, zoneContext)).load();
/*    */           
/* 45 */           zones[index++] = zone;
/* 46 */           reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 47 */       { throw new Error(String.format("Error while loading zone \"%s\" for world generator from file.", new Object[] { zoneContext.getPath().toString() }), e); }
/*    */     
/*    */     } 
/* 50 */     return zones;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String PATH_ZONES = "Zones";
/*    */     public static final String FILE_ZONE_MAIN_FILE = "Zone.json";
/*    */     public static final String ERROR_LOADING_ZONE = "Error while loading zone \"%s\" for world generator from file.";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\ZonesJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */