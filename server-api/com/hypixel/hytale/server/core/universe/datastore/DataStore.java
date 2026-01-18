/*    */ package com.hypixel.hytale.server.core.universe.datastore;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public interface DataStore<T>
/*    */ {
/*    */   BuilderCodec<T> getCodec();
/*    */   
/*    */   @Nullable
/*    */   T load(String paramString) throws IOException;
/*    */   
/*    */   void save(String paramString, T paramT);
/*    */   
/*    */   void remove(String paramString) throws IOException;
/*    */   
/*    */   List<String> list() throws IOException;
/*    */   
/*    */   @Nonnull
/*    */   default Map<String, T> loadAll() throws IOException {
/* 26 */     Object2ObjectOpenHashMap<String, T> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 27 */     for (String id : list()) {
/* 28 */       T value = load(id);
/* 29 */       if (value == null)
/* 30 */         continue;  object2ObjectOpenHashMap.put(id, value);
/*    */     } 
/* 32 */     return (Map<String, T>)object2ObjectOpenHashMap;
/*    */   }
/*    */   
/*    */   default void saveAll(@Nonnull Map<String, T> objectsToSave) {
/* 36 */     for (Map.Entry<String, T> entry : objectsToSave.entrySet()) {
/* 37 */       save(entry.getKey(), entry.getValue());
/*    */     }
/*    */   }
/*    */   
/*    */   default void removeAll() throws IOException {
/* 42 */     for (String id : list()) remove(id); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\datastore\DataStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */