/*    */ package com.hypixel.hytale.server.npc.metadata;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*    */ public class CapturedNPCMetadata
/*    */ {
/*    */   public static final String KEY = "CapturedEntity";
/*    */   public static final BuilderCodec<CapturedNPCMetadata> CODEC;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CapturedNPCMetadata.class, CapturedNPCMetadata::new).appendInherited(new KeyedCodec("IconPath", (Codec)Codec.STRING), (meta, s) -> meta.iconPath = s, meta -> meta.iconPath, (meta, parent) -> meta.iconPath = parent.iconPath).add()).appendInherited(new KeyedCodec("RoleIndex", (Codec)Codec.INTEGER), (meta, s) -> meta.roleIndex = s.intValue(), meta -> Integer.valueOf(meta.roleIndex), (meta, parent) -> meta.roleIndex = parent.roleIndex).add()).appendInherited(new KeyedCodec("NpcNameKey", (Codec)Codec.STRING), (meta, s) -> meta.npcNameKey = s, meta -> meta.npcNameKey, (meta, parent) -> meta.npcNameKey = parent.npcNameKey).add()).appendInherited(new KeyedCodec("FullItemIcon", (Codec)Codec.STRING), (meta, s) -> meta.fullItemIcon = s, meta -> meta.fullItemIcon, (meta, parent) -> meta.fullItemIcon = parent.fullItemIcon).add()).build();
/* 42 */   } public static final KeyedCodec<CapturedNPCMetadata> KEYED_CODEC = new KeyedCodec("CapturedEntity", (Codec)CODEC);
/*    */   private String iconPath;
/*    */   private int roleIndex;
/*    */   private String npcNameKey;
/*    */   private String fullItemIcon;
/*    */   
/*    */   public int getRoleIndex() {
/* 49 */     return this.roleIndex;
/* 50 */   } public String getIconPath() { return this.iconPath; }
/* 51 */   public String getNpcNameKey() { return this.npcNameKey; } public String getFullItemIcon() {
/* 52 */     return this.fullItemIcon;
/*    */   }
/*    */   public void setIconPath(String iconPath) {
/* 55 */     this.iconPath = iconPath;
/*    */   }
/*    */   
/*    */   public void setRoleIndex(int roleIndex) {
/* 59 */     this.roleIndex = roleIndex;
/*    */   }
/*    */   
/*    */   public void setNpcNameKey(String npcNameKey) {
/* 63 */     this.npcNameKey = npcNameKey;
/*    */   }
/*    */   
/*    */   public void setFullItemIcon(String fullItemIcon) {
/* 67 */     this.fullItemIcon = fullItemIcon;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\metadata\CapturedNPCMetadata.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */