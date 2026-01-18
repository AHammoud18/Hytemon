/*    */ package com.hypixel.hytale.server.worldgen.zone;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZoneColorMapping
/*    */ {
/*    */   @Nonnull
/* 17 */   protected final Int2ObjectMap<Zone[]> mapping = (Int2ObjectMap<Zone[]>)new Int2ObjectOpenHashMap();
/*    */ 
/*    */   
/*    */   public void add(int rgb, Zone zone) {
/* 21 */     add(rgb, new Zone[] { zone });
/*    */   }
/*    */   
/*    */   public void add(int rgb, Zone[] zones) {
/* 25 */     this.mapping.put(rgb, zones);
/*    */   }
/*    */   
/*    */   public Zone[] get(int rgb) {
/* 29 */     return (Zone[])this.mapping.get(rgb);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 35 */     return "ZoneColorMapping{mapping=" + String.valueOf(this.mapping) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zone\ZoneColorMapping.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */