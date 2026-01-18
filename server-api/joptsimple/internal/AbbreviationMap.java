/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbbreviationMap<V>
/*     */   implements OptionNameMap<V>
/*     */ {
/*  61 */   private final Map<Character, AbbreviationMap<V>> children = new TreeMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private String key;
/*     */ 
/*     */ 
/*     */   
/*     */   private V value;
/*     */ 
/*     */   
/*     */   private int keysBeyond;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String key) {
/*  77 */     return (get(key) != null);
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
/*     */   public V get(String key) {
/*  91 */     char[] chars = charsOf(key);
/*     */     
/*  93 */     AbbreviationMap<V> child = this;
/*  94 */     for (char each : chars) {
/*  95 */       child = child.children.get(Character.valueOf(each));
/*  96 */       if (child == null) {
/*  97 */         return null;
/*     */       }
/*     */     } 
/* 100 */     return child.value;
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
/*     */   public void put(String key, V newValue) {
/* 114 */     if (newValue == null)
/* 115 */       throw new NullPointerException(); 
/* 116 */     if (key.length() == 0) {
/* 117 */       throw new IllegalArgumentException();
/*     */     }
/* 119 */     char[] chars = charsOf(key);
/* 120 */     add(chars, newValue, 0, chars.length);
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
/*     */   public void putAll(Iterable<String> keys, V newValue) {
/* 134 */     for (String each : keys)
/* 135 */       put(each, newValue); 
/*     */   }
/*     */   
/*     */   private boolean add(char[] chars, V newValue, int offset, int length) {
/* 139 */     if (offset == length) {
/* 140 */       this.value = newValue;
/* 141 */       boolean wasAlreadyAKey = (this.key != null);
/* 142 */       this.key = new String(chars);
/* 143 */       return !wasAlreadyAKey;
/*     */     } 
/*     */     
/* 146 */     char nextChar = chars[offset];
/* 147 */     AbbreviationMap<V> child = this.children.get(Character.valueOf(nextChar));
/* 148 */     if (child == null) {
/* 149 */       child = new AbbreviationMap();
/* 150 */       this.children.put(Character.valueOf(nextChar), child);
/*     */     } 
/*     */     
/* 153 */     boolean newKeyAdded = child.add(chars, newValue, offset + 1, length);
/*     */     
/* 155 */     if (newKeyAdded) {
/* 156 */       this.keysBeyond++;
/*     */     }
/* 158 */     if (this.key == null) {
/* 159 */       this.value = (this.keysBeyond > 1) ? null : newValue;
/*     */     }
/* 161 */     return newKeyAdded;
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
/*     */   public void remove(String key) {
/* 173 */     if (key.length() == 0) {
/* 174 */       throw new IllegalArgumentException();
/*     */     }
/* 176 */     char[] keyChars = charsOf(key);
/* 177 */     remove(keyChars, 0, keyChars.length);
/*     */   }
/*     */   
/*     */   private boolean remove(char[] aKey, int offset, int length) {
/* 181 */     if (offset == length) {
/* 182 */       return removeAtEndOfKey();
/*     */     }
/* 184 */     char nextChar = aKey[offset];
/* 185 */     AbbreviationMap<V> child = this.children.get(Character.valueOf(nextChar));
/* 186 */     if (child == null || !child.remove(aKey, offset + 1, length)) {
/* 187 */       return false;
/*     */     }
/* 189 */     this.keysBeyond--;
/* 190 */     if (child.keysBeyond == 0)
/* 191 */       this.children.remove(Character.valueOf(nextChar)); 
/* 192 */     if (this.keysBeyond == 1 && this.key == null) {
/* 193 */       setValueToThatOfOnlyChild();
/*     */     }
/* 195 */     return true;
/*     */   }
/*     */   
/*     */   private void setValueToThatOfOnlyChild() {
/* 199 */     Map.Entry<Character, AbbreviationMap<V>> entry = this.children.entrySet().iterator().next();
/* 200 */     AbbreviationMap<V> onlyChild = entry.getValue();
/* 201 */     this.value = onlyChild.value;
/*     */   }
/*     */   
/*     */   private boolean removeAtEndOfKey() {
/* 205 */     if (this.key == null) {
/* 206 */       return false;
/*     */     }
/* 208 */     this.key = null;
/* 209 */     if (this.keysBeyond == 1) {
/* 210 */       setValueToThatOfOnlyChild();
/*     */     } else {
/* 212 */       this.value = null;
/*     */     } 
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, V> toJavaUtilMap() {
/* 224 */     Map<String, V> mappings = new TreeMap<>();
/* 225 */     addToMappings(mappings);
/* 226 */     return mappings;
/*     */   }
/*     */   
/*     */   private void addToMappings(Map<String, V> mappings) {
/* 230 */     if (this.key != null) {
/* 231 */       mappings.put(this.key, this.value);
/*     */     }
/* 233 */     for (AbbreviationMap<V> each : this.children.values())
/* 234 */       each.addToMappings(mappings); 
/*     */   }
/*     */   
/*     */   private static char[] charsOf(String aKey) {
/* 238 */     char[] chars = new char[aKey.length()];
/* 239 */     aKey.getChars(0, aKey.length(), chars, 0);
/* 240 */     return chars;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\internal\AbbreviationMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */