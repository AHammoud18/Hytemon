/*     */ package com.hypixel.hytale.codec;
/*     */ 
/*     */ import com.hypixel.hytale.codec.store.CodecStore;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.codec.validation.ThrowingValidationResults;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import com.hypixel.hytale.logger.util.GithubMessageUtil;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtraInfo
/*     */ {
/*  22 */   public static final ThreadLocal<ExtraInfo> THREAD_LOCAL = ThreadLocal.withInitial(ExtraInfo::new);
/*     */   
/*     */   public static final String GENERATED_ID_PREFIX = "*";
/*     */   
/*     */   public static final int UNSET_VERSION = 2147483647;
/*     */   
/*     */   private final int legacyVersion;
/*     */   
/*  30 */   private final int keysInitialSize = (this instanceof EmptyExtraInfo) ? 0 : 128;
/*     */   @Nonnull
/*  32 */   private String[] stringKeys = new String[this.keysInitialSize];
/*     */   @Nonnull
/*  34 */   private int[] intKeys = new int[this.keysInitialSize];
/*     */ 
/*     */   
/*  37 */   private int[] lineNumbers = GithubMessageUtil.isGithub() ? new int[this.keysInitialSize] : null;
/*  38 */   private int[] columnNumbers = GithubMessageUtil.isGithub() ? new int[this.keysInitialSize] : null;
/*     */   
/*     */   private int keysSize;
/*     */   @Nonnull
/*  42 */   private String[] ignoredUnknownKeys = new String[this.keysInitialSize];
/*     */   
/*     */   private int ignoredUnknownSize;
/*     */   
/*  46 */   private final List<String> unknownKeys = (List<String>)new ObjectArrayList();
/*     */   
/*     */   private final ValidationResults validationResults;
/*     */   
/*     */   private final CodecStore codecStore;
/*     */   @Deprecated
/*  52 */   private final Map<String, Object> metadata = (Map<String, Object>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */   
/*     */   public ExtraInfo() {
/*  56 */     this.legacyVersion = Integer.MAX_VALUE;
/*  57 */     this.validationResults = (ValidationResults)new ThrowingValidationResults(this);
/*  58 */     this.codecStore = CodecStore.STATIC;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public ExtraInfo(int version) {
/*  63 */     this.legacyVersion = version;
/*  64 */     this.validationResults = (ValidationResults)new ThrowingValidationResults(this);
/*  65 */     this.codecStore = CodecStore.STATIC;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public ExtraInfo(int version, @Nonnull Function<ExtraInfo, ValidationResults> validationResultsSupplier) {
/*  70 */     this.legacyVersion = version;
/*  71 */     this.validationResults = validationResultsSupplier.apply(this);
/*  72 */     this.codecStore = CodecStore.STATIC;
/*     */   }
/*     */   
/*     */   public int getVersion() {
/*  76 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getLegacyVersion() {
/*  87 */     return this.legacyVersion;
/*     */   }
/*     */   
/*     */   public int getKeysSize() {
/*  91 */     return this.keysSize;
/*     */   }
/*     */   
/*     */   public CodecStore getCodecStore() {
/*  95 */     return this.codecStore;
/*     */   }
/*     */   
/*     */   private int nextKeyIndex() {
/*  99 */     int index = this.keysSize++;
/* 100 */     if (this.stringKeys.length <= index) {
/* 101 */       int newLength = grow(index);
/* 102 */       this.stringKeys = Arrays.<String>copyOf(this.stringKeys, newLength);
/* 103 */       this.intKeys = Arrays.copyOf(this.intKeys, newLength);
/* 104 */       if (GithubMessageUtil.isGithub()) {
/* 105 */         this.lineNumbers = Arrays.copyOf(this.lineNumbers, newLength);
/* 106 */         this.columnNumbers = Arrays.copyOf(this.columnNumbers, newLength);
/*     */       } 
/*     */     } 
/* 109 */     return index;
/*     */   }
/*     */   
/*     */   public void pushKey(String key) {
/* 113 */     int index = nextKeyIndex();
/* 114 */     this.stringKeys[index] = key;
/*     */   }
/*     */   
/*     */   public void pushIntKey(int key) {
/* 118 */     int index = nextKeyIndex();
/* 119 */     this.intKeys[index] = key;
/*     */   }
/*     */   
/*     */   public void pushKey(String key, RawJsonReader reader) {
/* 123 */     int index = nextKeyIndex();
/* 124 */     this.stringKeys[index] = key;
/* 125 */     if (GithubMessageUtil.isGithub()) {
/* 126 */       this.lineNumbers[index] = reader.getLine();
/* 127 */       this.columnNumbers[index] = reader.getColumn();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void pushIntKey(int key, RawJsonReader reader) {
/* 132 */     int index = nextKeyIndex();
/* 133 */     this.intKeys[index] = key;
/* 134 */     if (GithubMessageUtil.isGithub()) {
/* 135 */       this.lineNumbers[index] = reader.getLine();
/* 136 */       this.columnNumbers[index] = reader.getColumn();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void popKey() {
/* 141 */     this.stringKeys[this.keysSize] = null;
/* 142 */     this.keysSize--;
/*     */   }
/*     */   
/*     */   private int nextIgnoredUnknownIndex() {
/* 146 */     int index = this.ignoredUnknownSize++;
/* 147 */     if (this.ignoredUnknownKeys.length <= index) {
/* 148 */       this.ignoredUnknownKeys = Arrays.<String>copyOf(this.ignoredUnknownKeys, grow(index));
/*     */     }
/* 150 */     return index;
/*     */   }
/*     */   
/*     */   public void ignoreUnusedKey(String key) {
/* 154 */     int index = nextIgnoredUnknownIndex();
/* 155 */     this.ignoredUnknownKeys[index] = key;
/*     */   }
/*     */   
/*     */   public void popIgnoredUnusedKey() {
/* 159 */     this.ignoredUnknownKeys[this.ignoredUnknownSize] = null;
/* 160 */     this.ignoredUnknownSize--;
/*     */   }
/*     */   
/*     */   public boolean consumeIgnoredUnknownKey(@Nonnull RawJsonReader reader) throws IOException {
/* 164 */     if (this.ignoredUnknownSize <= 0) return false;
/*     */     
/* 166 */     int lastIndex = this.ignoredUnknownSize - 1;
/* 167 */     String ignoredUnknownKey = this.ignoredUnknownKeys[lastIndex];
/* 168 */     if (ignoredUnknownKey == null) return false;
/*     */     
/* 170 */     if (!reader.tryConsumeString(ignoredUnknownKey)) return false;
/*     */     
/* 172 */     this.ignoredUnknownKeys[lastIndex] = null;
/* 173 */     return true;
/*     */   }
/*     */   
/*     */   public boolean consumeIgnoredUnknownKey(@Nonnull String key) {
/* 177 */     if (this.ignoredUnknownSize <= 0) return false;
/*     */     
/* 179 */     int lastIndex = this.ignoredUnknownSize - 1;
/* 180 */     if (!key.equals(this.ignoredUnknownKeys[lastIndex])) return false;
/*     */     
/* 182 */     this.ignoredUnknownKeys[lastIndex] = null;
/* 183 */     return true;
/*     */   }
/*     */   
/*     */   public void readUnknownKey(@Nonnull RawJsonReader reader) throws IOException {
/* 187 */     if (consumeIgnoredUnknownKey(reader))
/*     */       return; 
/* 189 */     String key = reader.readString();
/* 190 */     if (this.keysSize == 0) {
/* 191 */       this.unknownKeys.add(key);
/*     */     } else {
/* 193 */       this.unknownKeys.add(peekKey() + "." + peekKey());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addUnknownKey(@Nonnull String key) {
/* 198 */     switch (key) {
/*     */       case "$Title":
/*     */       case "$Comment":
/*     */       case "$TODO":
/*     */       case "$Author":
/*     */       case "$Position":
/*     */       case "$FloatingFunctionNodes":
/*     */       case "$Groups":
/*     */       case "$WorkspaceID":
/*     */       case "$NodeEditorMetadata":
/*     */       case "$NodeId":
/*     */         return;
/*     */     } 
/* 211 */     if (consumeIgnoredUnknownKey(key))
/* 212 */       return;  if (this.keysSize == 0) {
/* 213 */       if ("Parent".equals(key))
/* 214 */         return;  this.unknownKeys.add(key);
/*     */     } else {
/* 216 */       this.unknownKeys.add(peekKey() + "." + peekKey());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String peekKey() {
/* 221 */     return peekKey('.');
/*     */   }
/*     */   
/*     */   public String peekKey(char separator) {
/* 225 */     if (this.keysSize == 0) return ""; 
/* 226 */     if (this.keysSize == 1) {
/* 227 */       String str = this.stringKeys[0];
/* 228 */       if (str != null) return str; 
/* 229 */       return String.valueOf(this.intKeys[0]);
/*     */     } 
/*     */     
/* 232 */     StringBuilder sb = new StringBuilder();
/* 233 */     for (int i = 0; i < this.keysSize; i++) {
/* 234 */       if (i > 0) sb.append(separator); 
/* 235 */       String str = this.stringKeys[i];
/* 236 */       if (str != null) {
/* 237 */         sb.append(str);
/*     */       } else {
/* 239 */         sb.append(this.intKeys[i]);
/*     */       } 
/*     */     } 
/* 242 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public int peekLine() {
/* 246 */     if (GithubMessageUtil.isGithub() && this.keysSize > 0) {
/* 247 */       return this.lineNumbers[this.keysSize - 1];
/*     */     }
/* 249 */     return -1;
/*     */   }
/*     */   
/*     */   public int peekColumn() {
/* 253 */     if (GithubMessageUtil.isGithub() && this.keysSize > 0) {
/* 254 */       return this.columnNumbers[this.keysSize - 1];
/*     */     }
/* 256 */     return -1;
/*     */   }
/*     */   
/*     */   public List<String> getUnknownKeys() {
/* 260 */     return this.unknownKeys;
/*     */   }
/*     */   
/*     */   public ValidationResults getValidationResults() {
/* 264 */     return this.validationResults;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Map<String, Object> getMetadata() {
/* 269 */     return this.metadata;
/*     */   }
/*     */   
/*     */   public void appendDetailsTo(@Nonnull StringBuilder sb) {
/* 273 */     sb.append("ExtraInfo\n");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 279 */     return "ExtraInfo{version=" + this.legacyVersion + ", stringKeys=" + 
/*     */       
/* 281 */       Arrays.toString((Object[])this.stringKeys) + ", intKeys=" + 
/* 282 */       Arrays.toString(this.intKeys) + ", keysSize=" + this.keysSize + ", ignoredUnknownKeys=" + 
/*     */       
/* 284 */       Arrays.toString((Object[])this.ignoredUnknownKeys) + ", ignoredUnknownSize=" + this.ignoredUnknownSize + ", unknownKeys=" + String.valueOf(this.unknownKeys) + ", validationResults=" + String.valueOf(this.validationResults) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int grow(int oldSize) {
/* 292 */     return oldSize + (oldSize >> 1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\ExtraInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */