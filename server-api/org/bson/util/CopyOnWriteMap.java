/*     */ package org.bson.util;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class CopyOnWriteMap<K, V>
/*     */   extends AbstractCopyOnWriteMap<K, V, Map<K, V>>
/*     */ {
/*     */   private static final long serialVersionUID = 7935514534647505917L;
/*     */   
/*     */   public static <K, V> Builder<K, V> builder() {
/*  70 */     return new Builder<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder<K, V>
/*     */   {
/*  80 */     private AbstractCopyOnWriteMap.View.Type viewType = AbstractCopyOnWriteMap.View.Type.STABLE;
/*  81 */     private final Map<K, V> initialValues = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> stableViews() {
/*  90 */       this.viewType = AbstractCopyOnWriteMap.View.Type.STABLE;
/*  91 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> addAll(Map<? extends K, ? extends V> values) {
/*  98 */       this.initialValues.putAll(values);
/*  99 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> liveViews() {
/* 106 */       this.viewType = AbstractCopyOnWriteMap.View.Type.LIVE;
/* 107 */       return this;
/*     */     }
/*     */     
/*     */     public CopyOnWriteMap<K, V> newHashMap() {
/* 111 */       return new CopyOnWriteMap.Hash<>(this.initialValues, this.viewType);
/*     */     }
/*     */     
/*     */     public CopyOnWriteMap<K, V> newLinkedMap() {
/* 115 */       return new CopyOnWriteMap.Linked<>(this.initialValues, this.viewType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> CopyOnWriteMap<K, V> newHashMap() {
/* 125 */     Builder<K, V> builder = builder();
/* 126 */     return builder.newHashMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> CopyOnWriteMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
/* 135 */     Builder<K, V> builder = builder();
/* 136 */     return builder.addAll(map).newHashMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> CopyOnWriteMap<K, V> newLinkedMap() {
/* 146 */     Builder<K, V> builder = builder();
/* 147 */     return builder.newLinkedMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> CopyOnWriteMap<K, V> newLinkedMap(Map<? extends K, ? extends V> map) {
/* 157 */     Builder<K, V> builder = builder();
/* 158 */     return builder.addAll(map).newLinkedMap();
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
/*     */   protected CopyOnWriteMap(Map<? extends K, ? extends V> map) {
/* 171 */     this(map, AbstractCopyOnWriteMap.View.Type.LIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CopyOnWriteMap() {
/* 178 */     this(Collections.emptyMap(), AbstractCopyOnWriteMap.View.Type.LIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CopyOnWriteMap(Map<? extends K, ? extends V> map, AbstractCopyOnWriteMap.View.Type viewType) {
/* 188 */     super(map, viewType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CopyOnWriteMap(AbstractCopyOnWriteMap.View.Type viewType) {
/* 195 */     super(Collections.emptyMap(), viewType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract <N extends Map<? extends K, ? extends V>> Map<K, V> copy(N paramN);
/*     */ 
/*     */ 
/*     */   
/*     */   static class Hash<K, V>
/*     */     extends CopyOnWriteMap<K, V>
/*     */   {
/*     */     private static final long serialVersionUID = 5221824943734164497L;
/*     */ 
/*     */ 
/*     */     
/*     */     Hash(Map<? extends K, ? extends V> map, AbstractCopyOnWriteMap.View.Type viewType) {
/* 213 */       super(map, viewType);
/*     */     }
/*     */ 
/*     */     
/*     */     public <N extends Map<? extends K, ? extends V>> Map<K, V> copy(N map) {
/* 218 */       return new HashMap<>((Map<? extends K, ? extends V>)map);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Linked<K, V>
/*     */     extends CopyOnWriteMap<K, V>
/*     */   {
/*     */     private static final long serialVersionUID = -8659999465009072124L;
/*     */     
/*     */     Linked(Map<? extends K, ? extends V> map, AbstractCopyOnWriteMap.View.Type viewType) {
/* 229 */       super(map, viewType);
/*     */     }
/*     */ 
/*     */     
/*     */     public <N extends Map<? extends K, ? extends V>> Map<K, V> copy(N map) {
/* 234 */       return new LinkedHashMap<>((Map<? extends K, ? extends V>)map);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bso\\util\CopyOnWriteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */