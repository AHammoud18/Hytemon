/*    */ package com.hypixel.hytale.server.npc;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AllNPCsLoadedEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   @Nonnull
/*    */   private final Int2ObjectMap<BuilderInfo> allNPCs;
/*    */   @Nonnull
/*    */   private final Int2ObjectMap<BuilderInfo> loadedNPCs;
/*    */   
/*    */   public AllNPCsLoadedEvent(@Nonnull Int2ObjectMap<BuilderInfo> allNPCs, @Nonnull Int2ObjectMap<BuilderInfo> loadedNPCs) {
/* 21 */     Objects.requireNonNull(allNPCs, "Map of all NPCs must not be empty in AllNPCsLoadedEvent");
/* 22 */     Objects.requireNonNull(loadedNPCs, "Map of loaded NPCs must not be empty in AllNPCsLoadedEvent");
/* 23 */     this.allNPCs = Int2ObjectMaps.unmodifiable(allNPCs);
/* 24 */     this.loadedNPCs = Int2ObjectMaps.unmodifiable(loadedNPCs);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Int2ObjectMap<BuilderInfo> getAllNPCs() {
/* 29 */     return this.allNPCs;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Int2ObjectMap<BuilderInfo> getLoadedNPCs() {
/* 34 */     return this.loadedNPCs;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 40 */     return "AllNPCsLoadedEvent{allNPCs=" + String.valueOf(this.allNPCs) + ", loadedNPCs=" + String.valueOf(this.loadedNPCs) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\AllNPCsLoadedEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */