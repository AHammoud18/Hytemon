/*    */ package com.hypixel.hytale.server.spawning.managers;
/*    */ 
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawns.config.NPCSpawn;
/*    */ import com.hypixel.hytale.server.spawning.wrappers.SpawnWrapper;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.concurrent.locks.StampedLock;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SpawnManager<T extends SpawnWrapper<U>, U extends NPCSpawn>
/*    */ {
/* 22 */   private final Int2ObjectMap<T> spawnWrapperCache = (Int2ObjectMap<T>)new Int2ObjectOpenHashMap();
/* 23 */   private final Object2IntMap<String> wrapperNameMap = (Object2IntMap<String>)new Object2IntOpenHashMap();
/* 24 */   private final StampedLock wrapperLock = new StampedLock();
/*    */   
/*    */   public T getSpawnWrapper(int spawnConfigIndex) {
/* 27 */     long stamp = this.wrapperLock.readLock();
/*    */     try {
/* 29 */       return (T)this.spawnWrapperCache.get(spawnConfigIndex);
/*    */     } finally {
/* 31 */       this.wrapperLock.unlockRead(stamp);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T removeSpawnWrapper(int spawnConfigurationIndex) {
/* 37 */     long stamp = this.wrapperLock.writeLock();
/*    */     try {
/* 39 */       SpawnWrapper spawnWrapper = (SpawnWrapper)this.spawnWrapperCache.remove(spawnConfigurationIndex);
/* 40 */       if (spawnWrapper == null) return null;
/*    */       
/* 42 */       this.wrapperNameMap.removeInt(spawnWrapper.getSpawn().getId());
/* 43 */       return (T)spawnWrapper;
/*    */     } finally {
/* 45 */       this.wrapperLock.unlockWrite(stamp);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean addSpawnWrapper(@Nonnull T spawnWrapper) {
/* 50 */     NPCSpawn nPCSpawn = spawnWrapper.getSpawn();
/* 51 */     int spawnConfigIndex = spawnWrapper.getSpawnIndex();
/* 52 */     long stamp = this.wrapperLock.writeLock();
/*    */     try {
/* 54 */       this.spawnWrapperCache.put(spawnConfigIndex, spawnWrapper);
/* 55 */       this.wrapperNameMap.put(nPCSpawn.getId(), spawnConfigIndex);
/*    */     } finally {
/* 57 */       this.wrapperLock.unlockWrite(stamp);
/*    */     } 
/* 59 */     SpawningPlugin.get().getLogger().at(Level.FINE).log("Set up NPCSpawn %s", nPCSpawn.getId());
/* 60 */     return true;
/*    */   }
/*    */   
/*    */   public void onNPCLoaded(String name, @Nonnull IntSet changeSet) {
/* 64 */     long stamp = this.wrapperLock.writeLock();
/*    */     try {
/* 66 */       for (ObjectIterator<Int2ObjectMap.Entry<T>> objectIterator = this.spawnWrapperCache.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<T> entry = objectIterator.next();
/* 67 */         SpawnWrapper spawnWrapper = (SpawnWrapper)entry.getValue();
/*    */         
/* 69 */         if (spawnWrapper.hasInvalidNPC(name)) {
/* 70 */           changeSet.add(spawnWrapper.getSpawnIndex());
/*    */         } }
/*    */     
/*    */     } finally {
/* 74 */       this.wrapperLock.unlockWrite(stamp);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onNPCSpawnRemoved(String key) {
/*    */     int index;
/* 80 */     long stamp = this.wrapperLock.readLock();
/*    */     try {
/* 82 */       index = this.wrapperNameMap.getInt(key);
/*    */     } finally {
/* 84 */       this.wrapperLock.unlockRead(stamp);
/*    */     } 
/* 86 */     untrackNPCs(index);
/* 87 */     removeSpawnWrapper(index);
/*    */   }
/*    */   
/*    */   protected void untrackNPCs(int index) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\managers\SpawnManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */