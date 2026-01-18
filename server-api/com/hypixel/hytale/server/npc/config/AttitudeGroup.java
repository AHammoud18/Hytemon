/*    */ package com.hypixel.hytale.server.npc.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttitudeGroup
/*    */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, AttitudeGroup>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, AttitudeGroup> CODEC;
/*    */   private static IndexedLookupTableAssetMap<String, AttitudeGroup> ASSET_MAP;
/*    */   protected AssetExtraInfo.Data data;
/*    */   protected String id;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(AttitudeGroup.class, AttitudeGroup::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("Defines attitudes towards specific groups of NPCs.")).append(new KeyedCodec("Groups", (Codec)new EnumMapCodec(Attitude.class, (Codec)Codec.STRING_ARRAY)), (group, map) -> group.attitudeGroups = map, group -> group.attitudeGroups).documentation("A map of attitudes to NPC groups.").add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public static IndexedLookupTableAssetMap<String, AttitudeGroup> getAssetMap() {
/* 45 */     if (ASSET_MAP == null) ASSET_MAP = (IndexedLookupTableAssetMap<String, AttitudeGroup>)AssetRegistry.getAssetStore(AttitudeGroup.class).getAssetMap(); 
/* 46 */     return ASSET_MAP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   protected Map<Attitude, String[]> attitudeGroups = (Map)Collections.emptyMap();
/*    */   
/*    */   public AttitudeGroup(String id) {
/* 55 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 63 */     return this.id;
/*    */   }
/*    */   
/*    */   public Map<Attitude, String[]> getAttitudeGroups() {
/* 67 */     return this.attitudeGroups;
/*    */   }
/*    */   
/*    */   protected AttitudeGroup() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\config\AttitudeGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */