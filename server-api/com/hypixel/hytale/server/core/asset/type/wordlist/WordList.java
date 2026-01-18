/*     */ package com.hypixel.hytale.server.core.asset.type.wordlist;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
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
/*     */ public class WordList
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, WordList>>
/*     */ {
/*     */   private static final String WORDLISTS_TRANSLATION_FILE = "wordlists";
/*     */   public static final AssetBuilderCodec<String, WordList> CODEC;
/*     */   private static AssetStore<String, WordList, DefaultAssetMap<String, WordList>> ASSET_STORE;
/*     */   
/*     */   static {
/*  45 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(WordList.class, WordList::new, (Codec)Codec.STRING, (wordList, s) -> wordList.id = s, wordList -> wordList.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("TranslationKeys", (Codec)Codec.STRING_ARRAY), (wordList, o) -> wordList.translationKeys = o, wordList -> wordList.translationKeys, (wordList, parent) -> wordList.translationKeys = parent.translationKeys).documentation("The list of word message keys. Need to be added in Assets/Server/Languages/wordlists.lang. For example if the WordList asset file is 'animals' and you write 'cow' here, it will refer to 'animals.cow' (full path is 'wordlists.animals.cow')").add()).afterDecode(WordList::processConfig)).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static AssetStore<String, WordList, DefaultAssetMap<String, WordList>> getAssetStore() {
/*  50 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(WordList.class); 
/*  51 */     return ASSET_STORE;
/*     */   }
/*     */   
/*  54 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(WordList::getAssetStore));
/*     */   
/*     */   public static DefaultAssetMap<String, WordList> getAssetMap() {
/*  57 */     return (DefaultAssetMap<String, WordList>)getAssetStore().getAssetMap();
/*     */   }
/*     */   
/*  60 */   private static final WordList EMPTY = new WordList(); protected AssetExtraInfo.Data data;
/*     */   
/*     */   public static WordList getWordList(@Nullable String assetKey) {
/*  63 */     if (assetKey == null || assetKey.isEmpty()) return EMPTY; 
/*  64 */     WordList wordList = (WordList)getAssetMap().getAsset(assetKey);
/*  65 */     if (wordList == null) return EMPTY; 
/*  66 */     return wordList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String id;
/*     */ 
/*     */   
/*     */   protected String[] translationKeys;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  80 */     return this.id;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/*  84 */     if (this.translationKeys != null) {
/*  85 */       String idLower = this.id.toLowerCase();
/*  86 */       String[] remappedTranslationKeys = new String[this.translationKeys.length];
/*  87 */       for (int i = 0; i < this.translationKeys.length; i++) {
/*  88 */         remappedTranslationKeys[i] = "wordlists." + idLower + "." + this.translationKeys[i];
/*     */       }
/*  90 */       this.translationKeys = remappedTranslationKeys;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String pickDefaultLanguage(@Nonnull Random random, @Nonnull Set<String> alreadyUsedTranslated) {
/*  97 */     String translationKey = pickTranslationKey(random, alreadyUsedTranslated, "en-US");
/*  98 */     if (translationKey == null) return null; 
/*  99 */     return I18nModule.get().getMessage("en-US", translationKey);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String pickTranslationKey(@Nonnull Random random, @Nonnull Set<String> alreadyUsedTranslated, String languageForAlreadyUsed) {
/* 105 */     List<String> available = toKeysListMinusTranslated(this.translationKeys, alreadyUsedTranslated, languageForAlreadyUsed);
/* 106 */     if (available.isEmpty()) return null; 
/* 107 */     return available.get(random.nextInt(available.size()));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static <T> List<T> toListMinusSet(@Nullable T[] array, @Nonnull Set<T> set) {
/* 112 */     if (array == null || array.length == 0) return Collections.emptyList();
/*     */     
/* 114 */     ObjectArrayList<T> objectArrayList = new ObjectArrayList(array.length);
/* 115 */     for (T elem : array) {
/* 116 */       if (!set.contains(elem)) {
/* 117 */         objectArrayList.add(elem);
/*     */       }
/*     */     } 
/* 120 */     return (List<T>)objectArrayList;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static List<String> toKeysListMinusTranslated(@Nullable String[] translationKeys, @Nonnull Set<String> alreadyUsedTranslated, String language) {
/* 125 */     if (translationKeys == null || translationKeys.length == 0) return Collections.emptyList();
/*     */     
/* 127 */     ObjectArrayList<String> objectArrayList = new ObjectArrayList(translationKeys.length);
/* 128 */     for (String translationKey : translationKeys) {
/* 129 */       String translated = I18nModule.get().getMessage(language, translationKey);
/* 130 */       if (translated != null && !alreadyUsedTranslated.contains(translated)) {
/* 131 */         objectArrayList.add(translationKey);
/*     */       }
/*     */     } 
/* 134 */     return (List<String>)objectArrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 140 */     return "WordList{id='" + this.id + "', translationKeys=" + String.valueOf(this.translationKeys) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\wordlist\WordList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */