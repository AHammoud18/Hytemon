/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RecyclableArrayList
/*     */   extends ArrayList<Object>
/*     */ {
/*     */   private static final long serialVersionUID = -8605125654176467947L;
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 8;
/*     */   
/*  36 */   private static final Recycler<RecyclableArrayList> RECYCLER = new Recycler<RecyclableArrayList>()
/*     */     {
/*     */       protected RecyclableArrayList newObject(Recycler.Handle<RecyclableArrayList> handle)
/*     */       {
/*  40 */         return new RecyclableArrayList((ObjectPool.Handle)handle);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private boolean insertSinceRecycled;
/*     */   
/*     */   private final ObjectPool.Handle<RecyclableArrayList> handle;
/*     */   
/*     */   public static RecyclableArrayList newInstance() {
/*  50 */     return newInstance(8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RecyclableArrayList newInstance(int minCapacity) {
/*  57 */     RecyclableArrayList ret = (RecyclableArrayList)RECYCLER.get();
/*  58 */     ret.ensureCapacity(minCapacity);
/*  59 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RecyclableArrayList(ObjectPool.Handle<RecyclableArrayList> handle) {
/*  65 */     this(handle, 8);
/*     */   }
/*     */   
/*     */   private RecyclableArrayList(ObjectPool.Handle<RecyclableArrayList> handle, int initialCapacity) {
/*  69 */     super(initialCapacity);
/*  70 */     this.handle = handle;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<?> c) {
/*  75 */     checkNullElements(c);
/*  76 */     if (super.addAll(c)) {
/*  77 */       this.insertSinceRecycled = true;
/*  78 */       return true;
/*     */     } 
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<?> c) {
/*  85 */     checkNullElements(c);
/*  86 */     if (super.addAll(index, c)) {
/*  87 */       this.insertSinceRecycled = true;
/*  88 */       return true;
/*     */     } 
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   private static void checkNullElements(Collection<?> c) {
/*  94 */     if (c instanceof java.util.RandomAccess && c instanceof List) {
/*     */       
/*  96 */       List<?> list = (List)c;
/*  97 */       int size = list.size();
/*  98 */       for (int i = 0; i < size; i++) {
/*  99 */         if (list.get(i) == null) {
/* 100 */           throw new IllegalArgumentException("c contains null values");
/*     */         }
/*     */       } 
/*     */     } else {
/* 104 */       for (Object element : c) {
/* 105 */         if (element == null) {
/* 106 */           throw new IllegalArgumentException("c contains null values");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Object element) {
/* 114 */     if (super.add(ObjectUtil.checkNotNull(element, "element"))) {
/* 115 */       this.insertSinceRecycled = true;
/* 116 */       return true;
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, Object element) {
/* 123 */     super.add(index, ObjectUtil.checkNotNull(element, "element"));
/* 124 */     this.insertSinceRecycled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object set(int index, Object element) {
/* 129 */     Object old = super.set(index, ObjectUtil.checkNotNull(element, "element"));
/* 130 */     this.insertSinceRecycled = true;
/* 131 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean insertSinceRecycled() {
/* 138 */     return this.insertSinceRecycled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean recycle() {
/* 145 */     clear();
/* 146 */     this.insertSinceRecycled = false;
/* 147 */     this.handle.recycle(this);
/* 148 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\RecyclableArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */