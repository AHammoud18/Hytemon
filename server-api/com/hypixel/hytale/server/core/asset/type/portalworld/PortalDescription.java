/*     */ package com.hypixel.hytale.server.core.asset.type.portalworld;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class PortalDescription
/*     */ {
/*     */   public static final BuilderCodec<PortalDescription> CODEC;
/*     */   private String displayNameKey;
/*     */   private String flavorTextKey;
/*     */   private Color themeColor;
/*     */   private PillTag[] pillTags;
/*     */   private String[] objectivesKeys;
/*     */   private String[] wisdomKeys;
/*     */   private String splashImageFilename;
/*     */   
/*     */   static {
/*  74 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PortalDescription.class, PortalDescription::new).append(new KeyedCodec("DisplayName", (Codec)Codec.STRING), (portalType, o) -> portalType.displayNameKey = o, portalType -> portalType.displayNameKey).documentation("The translation key for the name of this portal.").add()).append(new KeyedCodec("FlavorText", (Codec)Codec.STRING), (portalType, o) -> portalType.flavorTextKey = o, portalType -> portalType.flavorTextKey).documentation("The translation key for the description of this portal.").add()).append(new KeyedCodec("ThemeColor", (Codec)ProtocolCodecs.COLOR), (portalType, o) -> portalType.themeColor = o, portalType -> portalType.themeColor).documentation("What color do you associate with this portal? May be used in many places.").add()).append(new KeyedCodec("DescriptionTags", (Codec)new ArrayCodec((Codec)PillTag.CODEC, x$0 -> new PillTag[x$0])), (portalType, o) -> portalType.pillTags = o, portalType -> portalType.pillTags).documentation("Purely cosmetic list of tags for the UI.").add()).append(new KeyedCodec("Objectives", (Codec)Codec.STRING_ARRAY), (portalType, o) -> portalType.objectivesKeys = o, portalType -> portalType.objectivesKeys).documentation("List of translation keys for the objectives in this portal.").add()).append(new KeyedCodec("Tips", (Codec)Codec.STRING_ARRAY), (portalType, o) -> portalType.wisdomKeys = o, portalType -> portalType.wisdomKeys).documentation("List of translation keys for the tips/wisdom offered for this portal.").add()).append(new KeyedCodec("SplashImage", (Codec)Codec.STRING), (portalType, o) -> portalType.splashImageFilename = o, portalType -> portalType.splashImageFilename).documentation("The filename of the splash image for this portal. Your best bet to find the folder is to search for an existing portal's image in assets. Screenshots taken 60 fov.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayNameKey() {
/*  85 */     return this.displayNameKey;
/*     */   }
/*     */   
/*     */   public Message getDisplayName() {
/*  89 */     return Message.translation(this.displayNameKey);
/*     */   }
/*     */   
/*     */   public String getFlavorTextKey() {
/*  93 */     return this.flavorTextKey;
/*     */   }
/*     */   
/*     */   public Message getFlavorText() {
/*  97 */     return Message.translation(this.flavorTextKey);
/*     */   }
/*     */   
/*     */   public Color getThemeColor() {
/* 101 */     return this.themeColor;
/*     */   }
/*     */   
/*     */   public List<PillTag> getPillTags() {
/* 105 */     return (this.pillTags == null) ? Collections.<PillTag>emptyList() : Arrays.<PillTag>asList(this.pillTags);
/*     */   }
/*     */   
/*     */   public String[] getObjectivesKeys() {
/* 109 */     return (this.objectivesKeys == null) ? ArrayUtil.EMPTY_STRING_ARRAY : this.objectivesKeys;
/*     */   }
/*     */   
/*     */   public String[] getWisdomKeys() {
/* 113 */     return (this.wisdomKeys == null) ? ArrayUtil.EMPTY_STRING_ARRAY : this.wisdomKeys;
/*     */   }
/*     */   
/*     */   public String getSplashImageFilename() {
/* 117 */     return this.splashImageFilename;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\portalworld\PortalDescription.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */