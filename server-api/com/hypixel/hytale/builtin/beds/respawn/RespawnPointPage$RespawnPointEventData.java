/*     */ package com.hypixel.hytale.builtin.beds.respawn;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RespawnPointEventData
/*     */ {
/*     */   static final String KEY_ACTION = "Action";
/*     */   static final String ACTION_CANCEL = "Cancel";
/*     */   static final String KEY_INDEX = "Index";
/*     */   static final String KEY_RESPAWN_POINT_NAME = "@RespawnPointName";
/*     */   public static final BuilderCodec<RespawnPointEventData> CODEC;
/*     */   private String action;
/*     */   private String indexStr;
/*     */   
/*     */   static {
/* 169 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RespawnPointEventData.class, RespawnPointEventData::new).append(new KeyedCodec("Action", (Codec)Codec.STRING), (entry, s) -> entry.action = s, entry -> entry.action).add()).append(new KeyedCodec("Index", (Codec)Codec.STRING), (entry, s) -> { entry.indexStr = s; entry.index = Integer.parseInt(s); }entry -> entry.indexStr).add()).append(new KeyedCodec("@RespawnPointName", (Codec)Codec.STRING), (entry, s) -> entry.respawnPointName = s, entry -> entry.respawnPointName).add()).build();
/*     */   }
/*     */ 
/*     */   
/* 173 */   private int index = -1;
/*     */   private String respawnPointName;
/*     */   
/*     */   public String getAction() {
/* 177 */     return this.action;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/* 181 */     return this.index;
/*     */   }
/*     */   
/*     */   public String getRespawnPointName() {
/* 185 */     return this.respawnPointName;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\respawn\RespawnPointPage$RespawnPointEventData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */