/*      */ package com.hypixel.hytale.codec.builder;
/*      */ 
/*      */ import com.hypixel.hytale.codec.ExtraInfo;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*      */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*      */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*      */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class BuilderBase<T, S extends BuilderCodec.BuilderBase<T, S>>
/*      */ {
/*      */   protected final Class<T> tClass;
/*      */   protected final Supplier<T> supplier;
/*      */   @Nullable
/*      */   protected final BuilderCodec<? super T> parentCodec;
/*  856 */   protected final Map<String, List<BuilderField<T, ?>>> entries = (Map<String, List<BuilderField<T, ?>>>)new Object2ObjectLinkedOpenHashMap();
/*      */   
/*      */   @Nonnull
/*      */   protected final StringTreeMap<BuilderCodec.KeyEntry<T>> stringTreeMap;
/*      */   
/*      */   protected BiConsumer<T, ValidationResults> validator;
/*      */   protected BiConsumer<T, ExtraInfo> afterDecode;
/*      */   protected String documentation;
/*      */   protected List<Metadata> metadata;
/*  865 */   protected int codecVersion = Integer.MIN_VALUE;
/*  866 */   protected int minCodecVersion = Integer.MAX_VALUE;
/*      */   protected boolean versioned = false;
/*      */   protected boolean useLegacyVersion = false;
/*      */   
/*      */   protected BuilderBase(Class<T> tClass, Supplier<T> supplier) {
/*  871 */     this(tClass, supplier, null);
/*      */   }
/*      */   
/*      */   protected BuilderBase(Class<T> tClass, Supplier<T> supplier, @Nullable BuilderCodec<? super T> parentCodec) {
/*  875 */     this.tClass = tClass;
/*  876 */     this.supplier = supplier;
/*  877 */     this.parentCodec = parentCodec;
/*  878 */     if (parentCodec != null) {
/*      */ 
/*      */       
/*  881 */       this.stringTreeMap = (StringTreeMap)new StringTreeMap<>(parentCodec.stringTreeMap);
/*      */     } else {
/*  883 */       this.stringTreeMap = new StringTreeMap<>(Map.ofEntries((Map.Entry<? extends String, ? extends BuilderCodec.KeyEntry<T>>[])new Map.Entry[] { 
/*  884 */               Map.entry("$Title", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  885 */               Map.entry("$Comment", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  886 */               Map.entry("$TODO", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  887 */               Map.entry("$Author", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  888 */               Map.entry("$Position", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  889 */               Map.entry("$FloatingFunctionNodes", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  890 */               Map.entry("$Groups", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  891 */               Map.entry("$WorkspaceID", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  892 */               Map.entry("$NodeEditorMetadata", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  893 */               Map.entry("$NodeId", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE)), 
/*  894 */               Map.entry("Parent", new BuilderCodec.KeyEntry(BuilderCodec.EntryType.IGNORE_IN_BASE_OBJECT)) }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private S self() {
/*  901 */     return (S)this;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public S documentation(String doc) {
/*  906 */     this.documentation = doc;
/*  907 */     return self();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public S versioned() {
/*  926 */     this.versioned = true;
/*  927 */     return self();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   @Deprecated
/*      */   public S legacyVersioned() {
/*  937 */     this.versioned = true;
/*  938 */     this.useLegacyVersion = true;
/*  939 */     return self();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   @Deprecated
/*      */   public <FieldType> S addField(@Nonnull KeyedCodec<FieldType> codec, @Nonnull BiConsumer<T, FieldType> setter, @Nonnull Function<T, FieldType> getter) {
/*  950 */     return addField(new BuilderField<>(codec, (t, fieldType, extraInfo) -> setter.accept(t, fieldType), (t1, extraInfo1) -> getter.apply(t1), null));
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public <FieldType> BuilderField.FieldBuilder<T, FieldType, S> append(KeyedCodec<FieldType> codec, @Nonnull BiConsumer<T, FieldType> setter, @Nonnull Function<T, FieldType> getter) {
/*  955 */     return append(codec, (t, fieldType, extraInfo) -> setter.accept(t, fieldType), (t, extraInfo) -> getter.apply(t));
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public <FieldType> BuilderField.FieldBuilder<T, FieldType, S> append(KeyedCodec<FieldType> codec, TriConsumer<T, FieldType, ExtraInfo> setter, BiFunction<T, ExtraInfo, FieldType> getter) {
/*  960 */     return new BuilderField.FieldBuilder<>(self(), codec, setter, getter, null);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public <FieldType> BuilderField.FieldBuilder<T, FieldType, S> appendInherited(KeyedCodec<FieldType> codec, @Nonnull BiConsumer<T, FieldType> setter, @Nonnull Function<T, FieldType> getter, @Nonnull BiConsumer<T, T> inherit) {
/*  965 */     return appendInherited(codec, (t, fieldType, extraInfo) -> setter.accept(t, fieldType), (t, extraInfo) -> getter.apply(t), (t, parent, extraInfo) -> inherit.accept(t, parent));
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public <FieldType> BuilderField.FieldBuilder<T, FieldType, S> appendInherited(KeyedCodec<FieldType> codec, TriConsumer<T, FieldType, ExtraInfo> setter, BiFunction<T, ExtraInfo, FieldType> getter, TriConsumer<T, T, ExtraInfo> inherit) {
/*  970 */     return new BuilderField.FieldBuilder<>(self(), codec, setter, getter, inherit);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public <FieldType> S addField(@Nonnull BuilderField<T, FieldType> entry) {
/*  975 */     if (entry.getMinVersion() > entry.getMaxVersion()) throw new IllegalArgumentException("Min version must be less than the max version: " + String.valueOf(entry)); 
/*  976 */     List<BuilderField<T, ?>> fields = this.entries.computeIfAbsent(entry.getCodec().getKey(), k -> new ObjectArrayList());
/*  977 */     for (BuilderField<T, ?> field : fields) {
/*  978 */       if (entry.getMaxVersion() >= field.getMinVersion() && entry.getMinVersion() <= field.getMaxVersion()) {
/*  979 */         throw new IllegalArgumentException("Field already defined for this version range!");
/*      */       }
/*      */     } 
/*  982 */     fields.add(entry);
/*  983 */     this.stringTreeMap.put(entry.getCodec().getKey(), new BuilderCodec.KeyEntry<>(fields));
/*  984 */     return self();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public S afterDecode(@Nonnull Consumer<T> afterDecode) {
/*  989 */     Objects.requireNonNull(afterDecode, "afterDecodeAndValidate can't be null!");
/*  990 */     return afterDecode((t, extraInfo) -> afterDecode.accept(t));
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public S afterDecode(BiConsumer<T, ExtraInfo> afterDecode) {
/*  995 */     this.afterDecode = Objects.<BiConsumer<T, ExtraInfo>>requireNonNull(afterDecode, "afterDecodeAndValidate can't be null!");
/*  996 */     return self();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   @Deprecated
/*      */   public S validator(BiConsumer<T, ValidationResults> validator) {
/* 1002 */     this.validator = Objects.<BiConsumer<T, ValidationResults>>requireNonNull(validator, "validator can't be null!");
/* 1003 */     return self();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public S metadata(Metadata metadata) {
/* 1008 */     if (this.metadata == null) this.metadata = (List<Metadata>)new ObjectArrayList(); 
/* 1009 */     this.metadata.add(metadata);
/* 1010 */     return self();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public S codecVersion(int minCodecVersion, int codecVersion) {
/* 1015 */     this.minCodecVersion = minCodecVersion;
/* 1016 */     this.codecVersion = codecVersion;
/* 1017 */     return self();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public S codecVersion(int codecVersion) {
/* 1022 */     this.minCodecVersion = 0;
/* 1023 */     this.codecVersion = codecVersion;
/* 1024 */     return self();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public BuilderCodec<T> build() {
/* 1029 */     return new BuilderCodec<>(this);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\builder\BuilderCodec$BuilderBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */