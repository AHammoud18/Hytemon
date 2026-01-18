/*     */ package com.hypixel.hytale.builtin.instances.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InstanceDiscoveryConfig
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<InstanceDiscoveryConfig> CODEC;
/*     */   @Nullable
/*     */   private String titleKey;
/*     */   @Nullable
/*     */   private String subtitleKey;
/*     */   
/*     */   static {
/*  94 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InstanceDiscoveryConfig.class, InstanceDiscoveryConfig::new).documentation("Configuration for displaying an event title when a player discovers an instance.")).append(new KeyedCodec("TitleKey", (Codec)Codec.STRING), (o, i) -> o.titleKey = i, o -> o.titleKey).documentation("The translation key for the primary title (e.g., \"server.instances.gaia_temple.title\").").addValidator(Validators.nonNull()).add()).append(new KeyedCodec("SubtitleKey", (Codec)Codec.STRING), (o, i) -> o.subtitleKey = i, o -> o.subtitleKey).documentation("The translation key for the subtitle (e.g., \"server.instances.gaia_temple.subtitle\").").add()).append(new KeyedCodec("Display", (Codec)Codec.BOOLEAN), (o, i) -> o.display = i.booleanValue(), o -> Boolean.valueOf(o.display)).documentation("Whether to display the discovery title and play the discovery sound.").add()).append(new KeyedCodec("AlwaysDisplay", (Codec)Codec.BOOLEAN), (o, i) -> o.alwaysDisplay = i.booleanValue(), o -> Boolean.valueOf(o.alwaysDisplay)).documentation("Whether to always display the discovery title, even if already discovered.").add()).append(new KeyedCodec("DiscoverySoundEventId", (Codec)Codec.STRING), (o, i) -> o.discoverySoundEventId = i, o -> o.discoverySoundEventId).documentation("The sound event ID to play when discovering this instance.").add()).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (o, i) -> o.icon = i, o -> o.icon).documentation("The icon to display with the event title.").add()).append(new KeyedCodec("Major", (Codec)Codec.BOOLEAN), (o, i) -> o.major = i.booleanValue(), o -> Boolean.valueOf(o.major)).documentation("Whether this is a major discovery (affects visual presentation).").add()).append(new KeyedCodec("Duration", (Codec)Codec.FLOAT), (o, i) -> o.duration = i.floatValue(), o -> Float.valueOf(o.duration)).documentation("The duration to display the event title for, in seconds.").add()).append(new KeyedCodec("FadeInDuration", (Codec)Codec.FLOAT), (o, i) -> o.fadeInDuration = i.floatValue(), o -> Float.valueOf(o.fadeInDuration)).documentation("The fade-in duration for the event title, in seconds.").add()).append(new KeyedCodec("FadeOutDuration", (Codec)Codec.FLOAT), (o, i) -> o.fadeOutDuration = i.floatValue(), o -> Float.valueOf(o.fadeOutDuration)).documentation("The fade-out duration for the event title, in seconds.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean display = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean alwaysDisplay = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String discoverySoundEventId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String icon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean major = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   private float duration = 4.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   private float fadeInDuration = 1.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   private float fadeOutDuration = 1.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getTitleKey() {
/* 163 */     return this.titleKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitleKey(@Nonnull String titleKey) {
/* 172 */     this.titleKey = titleKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getSubtitleKey() {
/* 180 */     return this.subtitleKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubtitleKey(@Nullable String subtitleKey) {
/* 189 */     this.subtitleKey = subtitleKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplay() {
/* 196 */     return this.display;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplay(boolean display) {
/* 205 */     this.display = display;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean alwaysDisplay() {
/* 212 */     return this.alwaysDisplay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlwaysDisplay(boolean alwaysDisplay) {
/* 221 */     this.alwaysDisplay = alwaysDisplay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getDiscoverySoundEventId() {
/* 229 */     return this.discoverySoundEventId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscoverySoundEventId(@Nullable String discoverySoundEventId) {
/* 238 */     this.discoverySoundEventId = discoverySoundEventId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getIcon() {
/* 246 */     return this.icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIcon(@Nullable String icon) {
/* 255 */     this.icon = icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMajor() {
/* 262 */     return this.major;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMajor(boolean major) {
/* 271 */     this.major = major;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDuration() {
/* 278 */     return this.duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDuration(float duration) {
/* 287 */     this.duration = duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFadeInDuration() {
/* 294 */     return this.fadeInDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFadeInDuration(float fadeInDuration) {
/* 303 */     this.fadeInDuration = fadeInDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFadeOutDuration() {
/* 310 */     return this.fadeOutDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFadeOutDuration(float fadeOutDuration) {
/* 319 */     this.fadeOutDuration = fadeOutDuration;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InstanceDiscoveryConfig clone() {
/* 325 */     InstanceDiscoveryConfig clone = new InstanceDiscoveryConfig();
/* 326 */     clone.titleKey = this.titleKey;
/* 327 */     clone.subtitleKey = this.subtitleKey;
/* 328 */     clone.display = this.display;
/* 329 */     clone.alwaysDisplay = this.alwaysDisplay;
/* 330 */     clone.discoverySoundEventId = this.discoverySoundEventId;
/* 331 */     clone.icon = this.icon;
/* 332 */     clone.major = this.major;
/* 333 */     clone.duration = this.duration;
/* 334 */     clone.fadeInDuration = this.fadeInDuration;
/* 335 */     clone.fadeOutDuration = this.fadeOutDuration;
/* 336 */     return clone;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\config\InstanceDiscoveryConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */