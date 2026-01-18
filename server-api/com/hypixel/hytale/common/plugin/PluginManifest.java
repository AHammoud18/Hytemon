/*     */ package com.hypixel.hytale.common.plugin;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.ObjectMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.semver.Semver;
/*     */ import com.hypixel.hytale.common.semver.SemverRange;
/*     */ import com.hypixel.hytale.common.util.java.ManifestUtil;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class PluginManifest
/*     */ {
/*     */   @Nonnull
/*  31 */   private static final BuilderCodec.Builder<PluginManifest> BUILDER = BuilderCodec.builder(PluginManifest.class, PluginManifest::new);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final Codec<PluginManifest> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final Codec<PluginManifest[]> ARRAY_CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CORE_GROUP = "Hytale";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 103 */     CODEC = (Codec<PluginManifest>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BUILDER.append(new KeyedCodec("Group", (Codec)Codec.STRING), (manifest, o) -> manifest.group = o, manifest -> manifest.group).add()).append(new KeyedCodec("Name", (Codec)Codec.STRING), (manifest, o) -> manifest.name = o, manifest -> manifest.name).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Version", Semver.CODEC), (manifest, o) -> manifest.version = o, manifest -> manifest.version).add()).append(new KeyedCodec("Description", (Codec)Codec.STRING), (manifest, o) -> manifest.description = o, manifest -> manifest.description).add()).append(new KeyedCodec("Authors", (Codec)new ArrayCodec(AuthorInfo.CODEC, x$0 -> new AuthorInfo[x$0])), (manifest, o) -> manifest.authors = List.of(o), manifest -> (AuthorInfo[])manifest.authors.toArray(())).add()).append(new KeyedCodec("Website", (Codec)Codec.STRING), (manifest, o) -> manifest.website = o, manifest -> manifest.website).add()).append(new KeyedCodec("Main", (Codec)Codec.STRING), (manifest, o) -> manifest.main = o, manifest -> manifest.main).add()).append(new KeyedCodec("ServerVersion", SemverRange.CODEC), (manifest, o) -> manifest.serverVersion = o, manifest -> manifest.serverVersion).add()).append(new KeyedCodec("Dependencies", (Codec)new ObjectMapCodec(SemverRange.CODEC, Object2ObjectLinkedOpenHashMap::new, PluginIdentifier::toString, PluginIdentifier::fromString)), (manifest, o) -> manifest.dependencies = o, manifest -> manifest.dependencies).add()).append(new KeyedCodec("OptionalDependencies", (Codec)new ObjectMapCodec(SemverRange.CODEC, Object2ObjectLinkedOpenHashMap::new, PluginIdentifier::toString, PluginIdentifier::fromString)), (manifest, o) -> manifest.optionalDependencies = o, manifest -> manifest.optionalDependencies).add()).append(new KeyedCodec("LoadBefore", (Codec)new ObjectMapCodec(SemverRange.CODEC, Object2ObjectLinkedOpenHashMap::new, PluginIdentifier::toString, PluginIdentifier::fromString)), (manifest, o) -> manifest.loadBefore = o, manifest -> manifest.loadBefore).add()).append(new KeyedCodec("DisabledByDefault", (Codec)Codec.BOOLEAN), (manifest, o) -> manifest.disabledByDefault = o.booleanValue(), manifest -> Boolean.valueOf(manifest.disabledByDefault)).add()).append(new KeyedCodec("IncludesAssetPack", (Codec)Codec.BOOLEAN), (manifest, o) -> manifest.includesAssetPack = o.booleanValue(), o -> Boolean.valueOf(o.includesAssetPack)).add()).build();
/*     */ 
/*     */     
/* 106 */     BUILDER.append(new KeyedCodec("SubPlugins", (Codec)new ArrayCodec(CODEC, x$0 -> new PluginManifest[x$0])), (manifest, o) -> manifest.subPlugins = List.of(o), manifest -> (PluginManifest[])manifest.subPlugins.toArray(()))
/*     */ 
/*     */ 
/*     */       
/* 110 */       .add();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     ARRAY_CODEC = (Codec<PluginManifest[]>)new ArrayCodec(CODEC, x$0 -> new PluginManifest[x$0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   private static final Semver CORE_VERSION = (ManifestUtil.getVersion() == null) ? Semver.fromString("0.0.0-dev") : ManifestUtil.getVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String group;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Semver version;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String description;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 154 */   private List<AuthorInfo> authors = (List<AuthorInfo>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String website;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String main;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SemverRange serverVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 177 */   private Map<PluginIdentifier, SemverRange> dependencies = (Map<PluginIdentifier, SemverRange>)new Object2ObjectLinkedOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 183 */   private Map<PluginIdentifier, SemverRange> optionalDependencies = (Map<PluginIdentifier, SemverRange>)new Object2ObjectLinkedOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 189 */   private Map<PluginIdentifier, SemverRange> loadBefore = (Map<PluginIdentifier, SemverRange>)new Object2ObjectLinkedOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 195 */   private List<PluginManifest> subPlugins = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean disabledByDefault = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean includesAssetPack = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginManifest(@Nonnull String group, @Nonnull String name, @Nonnull Semver version, @Nullable String description, @Nonnull List<AuthorInfo> authors, @Nullable String website, @Nullable String main, @Nullable SemverRange serverVersion, @Nonnull Map<PluginIdentifier, SemverRange> dependencies, @Nonnull Map<PluginIdentifier, SemverRange> optionalDependencies, @Nonnull Map<PluginIdentifier, SemverRange> loadBefore, @Nonnull List<PluginManifest> subPlugins, boolean disabledByDefault) {
/* 248 */     this.group = group;
/* 249 */     this.name = name;
/* 250 */     this.version = version;
/* 251 */     this.description = description;
/* 252 */     this.authors = authors;
/* 253 */     this.website = website;
/* 254 */     this.main = main;
/* 255 */     this.serverVersion = serverVersion;
/* 256 */     this.dependencies = dependencies;
/* 257 */     this.optionalDependencies = optionalDependencies;
/* 258 */     this.loadBefore = loadBefore;
/* 259 */     this.subPlugins = subPlugins;
/* 260 */     this.disabledByDefault = disabledByDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGroup() {
/* 267 */     return this.group;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 274 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Semver getVersion() {
/* 281 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getDescription() {
/* 289 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<AuthorInfo> getAuthors() {
/* 297 */     return Collections.unmodifiableList(this.authors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getWebsite() {
/* 305 */     return this.website;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroup(@Nonnull String group) {
/* 314 */     this.group = group;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(@Nonnull String name) {
/* 323 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersion(@Nullable Semver version) {
/* 332 */     this.version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(@Nullable String description) {
/* 341 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthors(@Nonnull List<AuthorInfo> authors) {
/* 350 */     this.authors = authors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWebsite(@Nullable String website) {
/* 359 */     this.website = website;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getMain() {
/* 367 */     return this.main;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SemverRange getServerVersion() {
/* 377 */     return this.serverVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<PluginIdentifier, SemverRange> getDependencies() {
/* 385 */     return Collections.unmodifiableMap(this.dependencies);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void injectDependency(PluginIdentifier identifier, SemverRange range) {
/* 394 */     this.dependencies.put(identifier, range);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<PluginIdentifier, SemverRange> getOptionalDependencies() {
/* 402 */     return Collections.unmodifiableMap(this.optionalDependencies);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<PluginIdentifier, SemverRange> getLoadBefore() {
/* 410 */     return Collections.unmodifiableMap(this.loadBefore);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisabledByDefault() {
/* 417 */     return this.disabledByDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean includesAssetPack() {
/* 424 */     return this.includesAssetPack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<PluginManifest> getSubPlugins() {
/* 432 */     return Collections.unmodifiableList(this.subPlugins);
/*     */   }
/*     */   
/*     */   public void inherit(@Nonnull PluginManifest manifest) {
/* 436 */     if (this.group == null) this.group = manifest.group;
/*     */     
/* 438 */     if (this.version == null) this.version = manifest.version; 
/* 439 */     if (this.description == null) this.description = manifest.description; 
/* 440 */     if (this.authors.isEmpty()) this.authors = manifest.authors; 
/* 441 */     if (this.website == null) this.website = manifest.website; 
/* 442 */     if (!this.disabledByDefault) this.disabledByDefault = manifest.disabledByDefault;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 447 */     this.dependencies.put(new PluginIdentifier(manifest), SemverRange.fromString(manifest.version.toString()));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 453 */     return "PluginManifest{group='" + this.group + "', name='" + this.name + "', version='" + String.valueOf(this.version) + "', description='" + this.description + "', authors=" + String.valueOf(this.authors) + ", website='" + this.website + "', main='" + this.main + "', serverVersion=" + String.valueOf(this.serverVersion) + ", dependencies=" + String.valueOf(this.dependencies) + ", optionalDependencies=" + String.valueOf(this.optionalDependencies) + ", disabledByDefault=" + this.disabledByDefault + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static CoreBuilder corePlugin(@Nonnull Class<?> pluginClass) {
/* 476 */     return new CoreBuilder("Hytale", pluginClass.getSimpleName(), CORE_VERSION, pluginClass.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginManifest() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CoreBuilder
/*     */   {
/*     */     private static final String CORE_GROUP = "Hytale";
/*     */ 
/*     */     
/* 492 */     private static final Semver CORE_VERSION = (ManifestUtil.getVersion() == null) ? Semver.fromString("0.0.0-dev") : ManifestUtil.getVersion();
/*     */     
/*     */     @Nonnull
/*     */     private final String group;
/*     */     
/*     */     @Nonnull
/*     */     private final String name;
/*     */     
/*     */     @Nonnull
/*     */     public static CoreBuilder corePlugin(@Nonnull Class<?> pluginClass) {
/* 502 */       return new CoreBuilder("Hytale", pluginClass.getSimpleName(), CORE_VERSION, pluginClass.getName());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Semver version;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private String description;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final String main;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 538 */     private final Map<PluginIdentifier, SemverRange> dependencies = (Map<PluginIdentifier, SemverRange>)new Object2ObjectLinkedOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 544 */     private final Map<PluginIdentifier, SemverRange> optionalDependencies = (Map<PluginIdentifier, SemverRange>)new Object2ObjectLinkedOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 550 */     private final Map<PluginIdentifier, SemverRange> loadBefore = (Map<PluginIdentifier, SemverRange>)new Object2ObjectLinkedOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private CoreBuilder(@Nonnull String group, @Nonnull String name, @Nonnull Semver version, @Nonnull String main) {
/* 562 */       this.group = group;
/* 563 */       this.name = name;
/* 564 */       this.version = version;
/* 565 */       this.main = main;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CoreBuilder description(@Nonnull String description) {
/* 576 */       this.description = description;
/* 577 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     @SafeVarargs
/*     */     public final CoreBuilder depends(@Nonnull Class<?>... dependencies) {
/* 589 */       for (Class<?> dependency : dependencies) {
/* 590 */         this.dependencies.put(new PluginIdentifier("Hytale", dependency.getSimpleName()), SemverRange.WILDCARD);
/*     */       }
/* 592 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     @SafeVarargs
/*     */     public final CoreBuilder optDepends(@Nonnull Class<?>... dependencies) {
/* 604 */       for (Class<?> optionalDependency : dependencies) {
/* 605 */         this.optionalDependencies.put(new PluginIdentifier("Hytale", optionalDependency.getSimpleName()), SemverRange.WILDCARD);
/*     */       }
/* 607 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     @SafeVarargs
/*     */     public final CoreBuilder loadsBefore(@Nonnull Class<?>... plugins) {
/* 619 */       for (Class<?> plugin : plugins) {
/* 620 */         this.loadBefore.put(new PluginIdentifier("Hytale", plugin.getSimpleName()), SemverRange.WILDCARD);
/*     */       }
/* 622 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public PluginManifest build() {
/* 630 */       return new PluginManifest(this.group, this.name, this.version, this.description, 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 635 */           Collections.emptyList(), null, this.main, null, this.dependencies, this.optionalDependencies, this.loadBefore, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 642 */           Collections.emptyList(), false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\plugin\PluginManifest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */