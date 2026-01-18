/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LazyStringArrayList
/*     */   extends AbstractProtobufList<String>
/*     */   implements LazyStringList, RandomAccess
/*     */ {
/*  42 */   private static final LazyStringArrayList EMPTY_LIST = new LazyStringArrayList(false);
/*     */ 
/*     */   
/*     */   public static LazyStringArrayList emptyList() {
/*  46 */     return EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  54 */   public static final LazyStringList EMPTY = emptyList();
/*     */   
/*     */   private final List<Object> list;
/*     */   
/*     */   public LazyStringArrayList() {
/*  59 */     this(10);
/*     */   }
/*     */   
/*     */   private LazyStringArrayList(boolean isMutable) {
/*  63 */     super(isMutable);
/*  64 */     this.list = Collections.emptyList();
/*     */   }
/*     */   
/*     */   public LazyStringArrayList(int initialCapacity) {
/*  68 */     this(new ArrayList(initialCapacity));
/*     */   }
/*     */   
/*     */   public LazyStringArrayList(LazyStringList from) {
/*  72 */     this.list = new ArrayList(from.size());
/*  73 */     addAll(from);
/*     */   }
/*     */   
/*     */   public LazyStringArrayList(List<String> from) {
/*  77 */     this(new ArrayList(from));
/*     */   }
/*     */   
/*     */   private LazyStringArrayList(ArrayList<Object> list) {
/*  81 */     this.list = list;
/*     */   }
/*     */ 
/*     */   
/*     */   public LazyStringArrayList mutableCopyWithCapacity(int capacity) {
/*  86 */     if (capacity < size()) {
/*  87 */       throw new IllegalArgumentException();
/*     */     }
/*  89 */     ArrayList<Object> newList = new ArrayList(capacity);
/*  90 */     newList.addAll(this.list);
/*  91 */     return new LazyStringArrayList(newList);
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(int index) {
/*  96 */     Object o = this.list.get(index);
/*  97 */     if (o instanceof String)
/*  98 */       return (String)o; 
/*  99 */     if (o instanceof ByteString) {
/* 100 */       ByteString bs = (ByteString)o;
/* 101 */       String str = bs.toStringUtf8();
/* 102 */       if (bs.isValidUtf8()) {
/* 103 */         this.list.set(index, str);
/*     */       }
/* 105 */       return str;
/*     */     } 
/* 107 */     byte[] ba = (byte[])o;
/* 108 */     String s = Internal.toStringUtf8(ba);
/* 109 */     if (Internal.isValidUtf8(ba)) {
/* 110 */       this.list.set(index, s);
/*     */     }
/* 112 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 118 */     return this.list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, String element) {
/* 123 */     ensureIsMutable();
/* 124 */     this.list.add(index, element);
/* 125 */     this.modCount++;
/*     */   }
/*     */   
/*     */   private void add(int index, ByteString element) {
/* 129 */     ensureIsMutable();
/* 130 */     this.list.add(index, element);
/* 131 */     this.modCount++;
/*     */   }
/*     */   
/*     */   private void add(int index, byte[] element) {
/* 135 */     ensureIsMutable();
/* 136 */     this.list.add(index, element);
/* 137 */     this.modCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public boolean add(String element) {
/* 143 */     ensureIsMutable();
/* 144 */     this.list.add(element);
/* 145 */     this.modCount++;
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ByteString element) {
/* 151 */     ensureIsMutable();
/* 152 */     this.list.add(element);
/* 153 */     this.modCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(byte[] element) {
/* 158 */     ensureIsMutable();
/* 159 */     this.list.add(element);
/* 160 */     this.modCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends String> c) {
/* 169 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends String> c) {
/* 174 */     ensureIsMutable();
/*     */ 
/*     */ 
/*     */     
/* 178 */     Collection<?> collection = (c instanceof LazyStringList) ? ((LazyStringList)c).getUnderlyingElements() : c;
/* 179 */     boolean ret = this.list.addAll(index, collection);
/* 180 */     this.modCount++;
/* 181 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAllByteString(Collection<? extends ByteString> values) {
/* 186 */     ensureIsMutable();
/* 187 */     boolean ret = this.list.addAll(values);
/* 188 */     this.modCount++;
/* 189 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAllByteArray(Collection<byte[]> c) {
/* 194 */     ensureIsMutable();
/* 195 */     boolean ret = this.list.addAll(c);
/* 196 */     this.modCount++;
/* 197 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public String remove(int index) {
/* 202 */     ensureIsMutable();
/* 203 */     Object o = this.list.remove(index);
/* 204 */     this.modCount++;
/* 205 */     return asString(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 210 */     ensureIsMutable();
/* 211 */     this.list.clear();
/* 212 */     this.modCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getRaw(int index) {
/* 217 */     return this.list.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteString getByteString(int index) {
/* 222 */     Object o = this.list.get(index);
/* 223 */     ByteString b = asByteString(o);
/* 224 */     if (b != o) {
/* 225 */       this.list.set(index, b);
/*     */     }
/* 227 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(int index) {
/* 232 */     Object o = this.list.get(index);
/* 233 */     byte[] b = asByteArray(o);
/* 234 */     if (b != o) {
/* 235 */       this.list.set(index, b);
/*     */     }
/* 237 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public String set(int index, String s) {
/* 242 */     ensureIsMutable();
/* 243 */     Object o = this.list.set(index, s);
/* 244 */     return asString(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int index, ByteString s) {
/* 249 */     setAndReturn(index, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int index, byte[] s) {
/* 254 */     setAndReturn(index, s);
/*     */   }
/*     */   
/*     */   private Object setAndReturn(int index, ByteString s) {
/* 258 */     ensureIsMutable();
/* 259 */     return this.list.set(index, s);
/*     */   }
/*     */   
/*     */   private Object setAndReturn(int index, byte[] s) {
/* 263 */     ensureIsMutable();
/* 264 */     return this.list.set(index, s);
/*     */   }
/*     */   
/*     */   private static String asString(Object o) {
/* 268 */     if (o instanceof String)
/* 269 */       return (String)o; 
/* 270 */     if (o instanceof ByteString) {
/* 271 */       return ((ByteString)o).toStringUtf8();
/*     */     }
/* 273 */     return Internal.toStringUtf8((byte[])o);
/*     */   }
/*     */ 
/*     */   
/*     */   private static ByteString asByteString(Object o) {
/* 278 */     if (o instanceof ByteString)
/* 279 */       return (ByteString)o; 
/* 280 */     if (o instanceof String) {
/* 281 */       return ByteString.copyFromUtf8((String)o);
/*     */     }
/* 283 */     return ByteString.copyFrom((byte[])o);
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] asByteArray(Object o) {
/* 288 */     if (o instanceof byte[])
/* 289 */       return (byte[])o; 
/* 290 */     if (o instanceof String) {
/* 291 */       return Internal.toByteArray((String)o);
/*     */     }
/* 293 */     return ((ByteString)o).toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<?> getUnderlyingElements() {
/* 299 */     return Collections.unmodifiableList(this.list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mergeFrom(LazyStringList other) {
/* 304 */     ensureIsMutable();
/* 305 */     for (Object o : other.getUnderlyingElements()) {
/* 306 */       if (o instanceof byte[]) {
/* 307 */         byte[] b = (byte[])o;
/*     */ 
/*     */         
/* 310 */         this.list.add(Arrays.copyOf(b, b.length)); continue;
/*     */       } 
/* 312 */       this.list.add(o);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class ByteArrayListView
/*     */     extends AbstractList<byte[]> implements RandomAccess {
/*     */     private final LazyStringArrayList list;
/*     */     
/*     */     ByteArrayListView(LazyStringArrayList list) {
/* 321 */       this.list = list;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] get(int index) {
/* 326 */       return this.list.getByteArray(index);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 331 */       return this.list.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] set(int index, byte[] s) {
/* 336 */       Object o = this.list.setAndReturn(index, s);
/* 337 */       this.modCount++;
/* 338 */       return LazyStringArrayList.asByteArray(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, byte[] s) {
/* 343 */       this.list.add(index, s);
/* 344 */       this.modCount++;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] remove(int index) {
/* 349 */       Object o = this.list.remove(index);
/* 350 */       this.modCount++;
/* 351 */       return LazyStringArrayList.asByteArray(o);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<byte[]> asByteArrayList() {
/* 357 */     return new ByteArrayListView(this);
/*     */   }
/*     */   
/*     */   private static class ByteStringListView extends AbstractList<ByteString> implements RandomAccess {
/*     */     private final LazyStringArrayList list;
/*     */     
/*     */     ByteStringListView(LazyStringArrayList list) {
/* 364 */       this.list = list;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteString get(int index) {
/* 369 */       return this.list.getByteString(index);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 374 */       return this.list.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteString set(int index, ByteString s) {
/* 379 */       Object o = this.list.setAndReturn(index, s);
/* 380 */       this.modCount++;
/* 381 */       return LazyStringArrayList.asByteString(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, ByteString s) {
/* 386 */       this.list.add(index, s);
/* 387 */       this.modCount++;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteString remove(int index) {
/* 392 */       Object o = this.list.remove(index);
/* 393 */       this.modCount++;
/* 394 */       return LazyStringArrayList.asByteString(o);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ByteString> asByteStringList() {
/* 400 */     return new ByteStringListView(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public LazyStringList getUnmodifiableView() {
/* 405 */     if (isModifiable()) {
/* 406 */       return new UnmodifiableLazyStringList(this);
/*     */     }
/* 408 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\LazyStringArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */