/*     */ package com.hypixel.hytale.builtin.buildertools.prefablist;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
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
/*     */ public class PageData
/*     */ {
/*     */   public static final String NAME = "@Name";
/*     */   public static final String ENTITIES = "@Entities";
/*     */   public static final String EMPTY = "@Empty";
/*     */   public static final String OVERWRITE = "@Overwrite";
/*     */   public static final String FROM_CLIPBOARD = "@FromClipboard";
/*     */   public static final BuilderCodec<PageData> CODEC;
/*     */   public PrefabSavePage.Action action;
/*     */   public String name;
/*     */   
/*     */   static {
/* 128 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PageData.class, PageData::new).append(new KeyedCodec("Action", (Codec)new EnumCodec(PrefabSavePage.Action.class, EnumCodec.EnumStyle.LEGACY)), (o, action) -> o.action = action, o -> o.action).add()).append(new KeyedCodec("@Name", (Codec)Codec.STRING), (o, name) -> o.name = name, o -> o.name).add()).append(new KeyedCodec("@Entities", (Codec)Codec.BOOLEAN), (o, entities) -> o.entities = entities.booleanValue(), o -> Boolean.valueOf(o.entities)).add()).append(new KeyedCodec("@Empty", (Codec)Codec.BOOLEAN), (o, empty) -> o.empty = empty.booleanValue(), o -> Boolean.valueOf(o.empty)).add()).append(new KeyedCodec("@Overwrite", (Codec)Codec.BOOLEAN), (o, overwrite) -> o.overwrite = overwrite.booleanValue(), o -> Boolean.valueOf(o.overwrite)).add()).append(new KeyedCodec("@FromClipboard", (Codec)Codec.BOOLEAN), (o, fromClipboard) -> o.fromClipboard = fromClipboard.booleanValue(), o -> Boolean.valueOf(o.fromClipboard)).add()).build();
/*     */   }
/*     */   
/*     */   public boolean entities = true;
/*     */   public boolean empty = false;
/*     */   public boolean overwrite = false;
/*     */   public boolean fromClipboard = false;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefablist\PrefabSavePage$PageData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */