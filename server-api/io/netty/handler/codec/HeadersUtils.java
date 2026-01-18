/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HeadersUtils
/*     */ {
/*     */   public static <K, V> List<String> getAllAsString(Headers<K, V, ?> headers, K name) {
/*  41 */     final List<V> allNames = headers.getAll(name);
/*  42 */     return new AbstractList<String>()
/*     */       {
/*     */         public String get(int index) {
/*  45 */           V value = allNames.get(index);
/*  46 */           return (value != null) ? value.toString() : null;
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/*  51 */           return allNames.size();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> String getAsString(Headers<K, V, ?> headers, K name) {
/*  63 */     V orig = headers.get(name);
/*  64 */     return (orig != null) ? orig.toString() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator<Map.Entry<String, String>> iteratorAsString(Iterable<Map.Entry<CharSequence, CharSequence>> headers) {
/*  72 */     return new StringEntryIterator(headers.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> String toString(Class<?> headersClass, Iterator<Map.Entry<K, V>> headersIt, int size) {
/*  83 */     String simpleName = headersClass.getSimpleName();
/*  84 */     if (size == 0) {
/*  85 */       return simpleName + "[]";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  90 */     StringBuilder sb = (new StringBuilder(simpleName.length() + 2 + size * 20)).append(simpleName).append('[');
/*  91 */     while (headersIt.hasNext()) {
/*  92 */       Map.Entry<?, ?> header = headersIt.next();
/*  93 */       sb.append(header.getKey()).append(": ").append(header.getValue()).append(", ");
/*     */     } 
/*  95 */     sb.setLength(sb.length() - 2);
/*  96 */     return sb.append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> namesAsString(Headers<CharSequence, CharSequence, ?> headers) {
/* 106 */     return new DelegatingNameSet(headers);
/*     */   }
/*     */   
/*     */   private static final class StringEntryIterator implements Iterator<Map.Entry<String, String>> {
/*     */     private final Iterator<Map.Entry<CharSequence, CharSequence>> iter;
/*     */     
/*     */     StringEntryIterator(Iterator<Map.Entry<CharSequence, CharSequence>> iter) {
/* 113 */       this.iter = iter;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 118 */       return this.iter.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map.Entry<String, String> next() {
/* 123 */       return new HeadersUtils.StringEntry(this.iter.next());
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 128 */       this.iter.remove();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class StringEntry implements Map.Entry<String, String> {
/*     */     private final Map.Entry<CharSequence, CharSequence> entry;
/*     */     private String name;
/*     */     private String value;
/*     */     
/*     */     StringEntry(Map.Entry<CharSequence, CharSequence> entry) {
/* 138 */       this.entry = entry;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 143 */       if (this.name == null) {
/* 144 */         this.name = ((CharSequence)this.entry.getKey()).toString();
/*     */       }
/* 146 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 151 */       if (this.value == null && this.entry.getValue() != null) {
/* 152 */         this.value = ((CharSequence)this.entry.getValue()).toString();
/*     */       }
/* 154 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String setValue(String value) {
/* 159 */       String old = getValue();
/* 160 */       this.entry.setValue(value);
/* 161 */       return old;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 166 */       return this.entry.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class StringIterator<T> implements Iterator<String> {
/*     */     private final Iterator<T> iter;
/*     */     
/*     */     StringIterator(Iterator<T> iter) {
/* 174 */       this.iter = iter;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.iter.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public String next() {
/* 184 */       T next = this.iter.next();
/* 185 */       return (next != null) ? next.toString() : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 190 */       this.iter.remove();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DelegatingNameSet extends AbstractCollection<String> implements Set<String> {
/*     */     private final Headers<CharSequence, CharSequence, ?> headers;
/*     */     
/*     */     DelegatingNameSet(Headers<CharSequence, CharSequence, ?> headers) {
/* 198 */       this.headers = (Headers<CharSequence, CharSequence, ?>)ObjectUtil.checkNotNull(headers, "headers");
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 203 */       return this.headers.names().size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 208 */       return this.headers.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 213 */       return this.headers.contains(o.toString());
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<String> iterator() {
/* 218 */       return new HeadersUtils.StringIterator(this.headers.names().iterator());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\HeadersUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */