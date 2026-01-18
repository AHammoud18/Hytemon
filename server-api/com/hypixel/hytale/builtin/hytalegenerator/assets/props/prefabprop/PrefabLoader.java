/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.buffer.BsonPrefabBufferDeserializer;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*    */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ 
/*    */ public class PrefabLoader
/*    */ {
/*    */   @Nonnull
/*    */   public static void loadAllPrefabBuffersUnder(@Nonnull Path dirPath, List<PrefabBuffer> pathPrefabs) {
/* 20 */     if (!Files.isDirectory(dirPath, new java.nio.file.LinkOption[0])) {
/*    */       
/* 22 */       PrefabBuffer prefab = loadPrefabBufferAt(dirPath);
/* 23 */       if (prefab == null)
/* 24 */         return;  pathPrefabs.add(prefab);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/*    */     try {
/* 30 */       Files.walkFileTree(dirPath, new PrefabFileVisitor(pathPrefabs));
/* 31 */     } catch (IOException e) {
/* 32 */       String msg = "Exception thrown by HytaleGenerator while loading a Prefab:\n";
/* 33 */       msg = msg + msg;
/* 34 */       LoggerUtil.getLogger().severe(msg);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static PrefabBuffer loadPrefabBufferAt(@Nonnull Path filePath) {
/* 40 */     if (!hasJsonExtension(filePath)) return null;
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 45 */       BsonDocument prefabAsBson = BsonUtil.readDocumentNow(filePath);
/* 46 */       if (prefabAsBson == null) return null; 
/* 47 */       return BsonPrefabBufferDeserializer.INSTANCE.deserialize(filePath, prefabAsBson);
/* 48 */     } catch (Exception e) {
/* 49 */       String msg = "Exception thrown by HytaleGenerator while loading a PrefabBuffer for " + String.valueOf(filePath) + ":\n";
/* 50 */       msg = msg + msg;
/* 51 */       LoggerUtil.getLogger().severe(msg);
/*    */       
/* 53 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean hasJsonExtension(@Nonnull Path path) {
/* 58 */     String pathString = path.toString();
/* 59 */     return pathString.toLowerCase().endsWith(".json");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\prefabprop\PrefabLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */