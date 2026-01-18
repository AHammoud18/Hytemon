/*     */ package com.hypixel.hytale.server.core.entity.entities.player.pages.audio;
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
/*     */ public class PlaySoundPageEventData
/*     */ {
/*     */   static final String KEY_SOUND_EVENT = "SoundEvent";
/*     */   static final String KEY_TYPE = "Type";
/*     */   static final String KEY_SEARCH_QUERY = "@SearchQuery";
/*     */   static final String KEY_VOLUME = "@Volume";
/*     */   static final String KEY_PITCH = "@Pitch";
/*     */   public static final BuilderCodec<PlaySoundPageEventData> CODEC;
/*     */   private String soundEvent;
/*     */   private String type;
/*     */   private String searchQuery;
/*     */   private Float volume;
/*     */   private Float pitch;
/*     */   
/*     */   static {
/* 194 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlaySoundPageEventData.class, PlaySoundPageEventData::new).append(new KeyedCodec("SoundEvent", (Codec)Codec.STRING), (entry, s) -> entry.soundEvent = s, entry -> entry.soundEvent).add()).append(new KeyedCodec("Type", (Codec)Codec.STRING), (entry, s) -> entry.type = s, entry -> entry.type).add()).append(new KeyedCodec("@SearchQuery", (Codec)Codec.STRING), (entry, s) -> entry.searchQuery = s, entry -> entry.searchQuery).add()).append(new KeyedCodec("@Volume", (Codec)Codec.FLOAT), (entry, f) -> entry.volume = f, entry -> entry.volume).add()).append(new KeyedCodec("@Pitch", (Codec)Codec.FLOAT), (entry, f) -> entry.pitch = f, entry -> entry.pitch).add()).build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\audio\PlaySoundPage$PlaySoundPageEventData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */