/*     */ package org.bson.types;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bson.BSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicBSONList
/*     */   extends ArrayList<Object>
/*     */   implements BSONObject
/*     */ {
/*     */   private static final long serialVersionUID = -4415279469780082174L;
/*     */   
/*     */   public Object put(String key, Object v) {
/*  64 */     return put(_getInt(key), v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(int key, Object value) {
/*  75 */     while (key >= size()) {
/*  76 */       add(null);
/*     */     }
/*  78 */     set(key, value);
/*  79 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map m) {
/*  85 */     for (Map.Entry entry : m.entrySet()) {
/*  86 */       put(entry.getKey().toString(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(BSONObject o) {
/*  92 */     for (String k : o.keySet()) {
/*  93 */       put(k, o.get(k));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String key) {
/* 105 */     int i = _getInt(key);
/* 106 */     if (i < 0) {
/* 107 */       return null;
/*     */     }
/* 109 */     if (i >= size()) {
/* 110 */       return null;
/*     */     }
/* 112 */     return get(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object removeField(String key) {
/* 117 */     int i = _getInt(key);
/* 118 */     if (i < 0) {
/* 119 */       return null;
/*     */     }
/* 121 */     if (i >= size()) {
/* 122 */       return null;
/*     */     }
/* 124 */     return remove(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsField(String key) {
/* 129 */     int i = _getInt(key, false);
/* 130 */     if (i < 0) {
/* 131 */       return false;
/*     */     }
/* 133 */     return (i >= 0 && i < size());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 138 */     return new StringRangeSet(size());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map toMap() {
/* 144 */     Map<Object, Object> m = new HashMap<>();
/* 145 */     Iterator<String> i = keySet().iterator();
/* 146 */     while (i.hasNext()) {
/* 147 */       Object s = i.next();
/* 148 */       m.put(s, get(String.valueOf(s)));
/*     */     } 
/* 150 */     return m;
/*     */   }
/*     */   
/*     */   int _getInt(String s) {
/* 154 */     return _getInt(s, true);
/*     */   }
/*     */   
/*     */   int _getInt(String s, boolean err) {
/*     */     try {
/* 159 */       return Integer.parseInt(s);
/* 160 */     } catch (Exception e) {
/* 161 */       if (err) {
/* 162 */         throw new IllegalArgumentException("BasicBSONList can only work with numeric keys, not: [" + s + "]");
/*     */       }
/* 164 */       return -1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\BasicBSONList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */