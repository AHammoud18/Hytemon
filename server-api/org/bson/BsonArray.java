/*     */ package org.bson;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import org.bson.codecs.BsonArrayCodec;
/*     */ import org.bson.codecs.DecoderContext;
/*     */ import org.bson.json.JsonReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BsonArray
/*     */   extends BsonValue
/*     */   implements List<BsonValue>, Cloneable
/*     */ {
/*     */   private final List<BsonValue> values;
/*     */   
/*     */   public BsonArray(List<? extends BsonValue> values) {
/*  45 */     this(values, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonArray() {
/*  52 */     this(new ArrayList<>(), false);
/*     */   }
/*     */ 
/*     */   
/*     */   BsonArray(List<? extends BsonValue> values, boolean copy) {
/*  57 */     if (copy) {
/*  58 */       this.values = new ArrayList<>(values);
/*     */     } else {
/*  60 */       this.values = (List)values;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static BsonArray parse(String json) {
/*  75 */     return (new BsonArrayCodec()).decode((BsonReader)new JsonReader(json), DecoderContext.builder().build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BsonValue> getValues() {
/*  84 */     return Collections.unmodifiableList(this.values);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/*  89 */     return BsonType.ARRAY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  94 */     return this.values.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  99 */     return this.values.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/* 104 */     return this.values.contains(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<BsonValue> iterator() {
/* 109 */     return this.values.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 114 */     return this.values.toArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 119 */     return this.values.toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(BsonValue bsonValue) {
/* 124 */     return this.values.add(bsonValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/* 129 */     return this.values.remove(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 134 */     return this.values.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends BsonValue> c) {
/* 139 */     return this.values.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends BsonValue> c) {
/* 144 */     return this.values.addAll(index, c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 149 */     return this.values.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 154 */     return this.values.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 159 */     this.values.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue get(int index) {
/* 164 */     return this.values.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue set(int index, BsonValue element) {
/* 169 */     return this.values.set(index, element);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, BsonValue element) {
/* 174 */     this.values.add(index, element);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue remove(int index) {
/* 179 */     return this.values.remove(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object o) {
/* 184 */     return this.values.indexOf(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(Object o) {
/* 189 */     return this.values.lastIndexOf(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListIterator<BsonValue> listIterator() {
/* 194 */     return this.values.listIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public ListIterator<BsonValue> listIterator(int index) {
/* 199 */     return this.values.listIterator(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BsonValue> subList(int fromIndex, int toIndex) {
/* 204 */     return this.values.subList(fromIndex, toIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 209 */     if (this == o) {
/* 210 */       return true;
/*     */     }
/* 212 */     if (!(o instanceof BsonArray)) {
/* 213 */       return false;
/*     */     }
/*     */     
/* 216 */     BsonArray that = (BsonArray)o;
/* 217 */     return getValues().equals(that.getValues());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 222 */     return this.values.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 227 */     return "BsonArray{values=" + this.values + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonArray clone() {
/* 234 */     BsonArray to = new BsonArray();
/* 235 */     for (BsonValue cur : this) {
/* 236 */       switch (cur.getBsonType()) {
/*     */         case DOCUMENT:
/* 238 */           to.add(cur.asDocument().clone());
/*     */           continue;
/*     */         case ARRAY:
/* 241 */           to.add(cur.asArray().clone());
/*     */           continue;
/*     */         case BINARY:
/* 244 */           to.add(BsonBinary.clone(cur.asBinary()));
/*     */           continue;
/*     */         case JAVASCRIPT_WITH_SCOPE:
/* 247 */           to.add(BsonJavaScriptWithScope.clone(cur.asJavaScriptWithScope()));
/*     */           continue;
/*     */       } 
/* 250 */       to.add(cur);
/*     */     } 
/*     */     
/* 253 */     return to;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */