/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
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
/*     */ public class DefaultAttributeMap
/*     */   implements AttributeMap
/*     */ {
/*  32 */   private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, DefaultAttribute[]> ATTRIBUTES_UPDATER = (AtomicReferenceFieldUpdater)AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, (Class)DefaultAttribute[].class, "attributes");
/*  33 */   private static final DefaultAttribute[] EMPTY_ATTRIBUTES = new DefaultAttribute[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int searchAttributeByKey(DefaultAttribute[] sortedAttributes, AttributeKey<?> key) {
/*  40 */     int low = 0;
/*  41 */     int high = sortedAttributes.length - 1;
/*     */     
/*  43 */     while (low <= high) {
/*  44 */       int mid = low + high >>> 1;
/*  45 */       DefaultAttribute midVal = sortedAttributes[mid];
/*  46 */       AttributeKey<?> midValKey = midVal.key;
/*  47 */       if (midValKey == key) {
/*  48 */         return mid;
/*     */       }
/*  50 */       int midValKeyId = midValKey.id();
/*  51 */       int keyId = key.id();
/*  52 */       assert midValKeyId != keyId;
/*  53 */       boolean searchRight = (midValKeyId < keyId);
/*  54 */       if (searchRight) {
/*  55 */         low = mid + 1; continue;
/*     */       } 
/*  57 */       high = mid - 1;
/*     */     } 
/*     */ 
/*     */     
/*  61 */     return -(low + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void orderedCopyOnInsert(DefaultAttribute[] sortedSrc, int srcLength, DefaultAttribute[] copy, DefaultAttribute toInsert) {
/*  67 */     int id = toInsert.key.id();
/*     */     int i;
/*  69 */     for (i = srcLength - 1; i >= 0; i--) {
/*  70 */       DefaultAttribute attribute = sortedSrc[i];
/*  71 */       assert attribute.key.id() != id;
/*  72 */       if (attribute.key.id() < id) {
/*     */         break;
/*     */       }
/*  75 */       copy[i + 1] = sortedSrc[i];
/*     */     } 
/*  77 */     copy[i + 1] = toInsert;
/*  78 */     int toCopy = i + 1;
/*  79 */     if (toCopy > 0) {
/*  80 */       System.arraycopy(sortedSrc, 0, copy, 0, toCopy);
/*     */     }
/*     */   }
/*     */   
/*  84 */   private volatile DefaultAttribute[] attributes = EMPTY_ATTRIBUTES;
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Attribute<T> attr(AttributeKey<T> key) {
/*  89 */     ObjectUtil.checkNotNull(key, "key");
/*  90 */     DefaultAttribute<T> newAttribute = null;
/*     */     while (true) {
/*  92 */       DefaultAttribute[] newAttributes, attributes = this.attributes;
/*  93 */       int index = searchAttributeByKey(attributes, key);
/*     */       
/*  95 */       if (index >= 0) {
/*  96 */         DefaultAttribute<T> attribute = attributes[index];
/*  97 */         assert attribute.key() == key;
/*  98 */         if (!attribute.isRemoved()) {
/*  99 */           return attribute;
/*     */         }
/*     */         
/* 102 */         if (newAttribute == null) {
/* 103 */           newAttribute = new DefaultAttribute<>(this, key);
/*     */         }
/* 105 */         int count = attributes.length;
/* 106 */         newAttributes = Arrays.<DefaultAttribute>copyOf(attributes, count);
/* 107 */         newAttributes[index] = newAttribute;
/*     */       } else {
/* 109 */         if (newAttribute == null) {
/* 110 */           newAttribute = new DefaultAttribute<>(this, key);
/*     */         }
/* 112 */         int count = attributes.length;
/* 113 */         newAttributes = new DefaultAttribute[count + 1];
/* 114 */         orderedCopyOnInsert(attributes, count, newAttributes, newAttribute);
/*     */       } 
/* 116 */       if (ATTRIBUTES_UPDATER.compareAndSet(this, attributes, newAttributes)) {
/* 117 */         return newAttribute;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean hasAttr(AttributeKey<T> key) {
/* 124 */     ObjectUtil.checkNotNull(key, "key");
/* 125 */     return (searchAttributeByKey(this.attributes, key) >= 0);
/*     */   } private <T> void removeAttributeIfMatch(AttributeKey<T> key, DefaultAttribute<T> value) {
/*     */     DefaultAttribute[] attributes;
/*     */     DefaultAttribute[] newAttributes;
/*     */     do {
/* 130 */       attributes = this.attributes;
/* 131 */       int index = searchAttributeByKey(attributes, key);
/* 132 */       if (index < 0) {
/*     */         return;
/*     */       }
/* 135 */       DefaultAttribute<T> attribute = attributes[index];
/* 136 */       assert attribute.key() == key;
/* 137 */       if (attribute != value) {
/*     */         return;
/*     */       }
/* 140 */       int count = attributes.length;
/* 141 */       int newCount = count - 1;
/*     */       
/* 143 */       newAttributes = (newCount == 0) ? EMPTY_ATTRIBUTES : new DefaultAttribute[newCount];
/*     */       
/* 145 */       System.arraycopy(attributes, 0, newAttributes, 0, index);
/* 146 */       int remaining = count - index - 1;
/* 147 */       if (remaining <= 0)
/* 148 */         continue;  System.arraycopy(attributes, index + 1, newAttributes, index, remaining);
/*     */     }
/* 150 */     while (!ATTRIBUTES_UPDATER.compareAndSet(this, attributes, newAttributes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class DefaultAttribute<T>
/*     */     extends AtomicReference<T>
/*     */     implements Attribute<T>
/*     */   {
/* 160 */     private static final AtomicReferenceFieldUpdater<DefaultAttribute, DefaultAttributeMap> MAP_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultAttribute.class, DefaultAttributeMap.class, "attributeMap");
/*     */     
/*     */     private static final long serialVersionUID = -2661411462200283011L;
/*     */     
/*     */     private volatile DefaultAttributeMap attributeMap;
/*     */     private final AttributeKey<T> key;
/*     */     
/*     */     DefaultAttribute(DefaultAttributeMap attributeMap, AttributeKey<T> key) {
/* 168 */       this.attributeMap = attributeMap;
/* 169 */       this.key = key;
/*     */     }
/*     */ 
/*     */     
/*     */     public AttributeKey<T> key() {
/* 174 */       return this.key;
/*     */     }
/*     */     
/*     */     private boolean isRemoved() {
/* 178 */       return (this.attributeMap == null);
/*     */     }
/*     */ 
/*     */     
/*     */     public T setIfAbsent(T value) {
/* 183 */       while (!compareAndSet(null, value)) {
/* 184 */         T old = get();
/* 185 */         if (old != null) {
/* 186 */           return old;
/*     */         }
/*     */       } 
/* 189 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public T getAndRemove() {
/* 194 */       DefaultAttributeMap attributeMap = this.attributeMap;
/* 195 */       boolean removed = (attributeMap != null && MAP_UPDATER.compareAndSet(this, attributeMap, null));
/* 196 */       T oldValue = getAndSet(null);
/* 197 */       if (removed) {
/* 198 */         attributeMap.removeAttributeIfMatch(this.key, this);
/*     */       }
/* 200 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 205 */       DefaultAttributeMap attributeMap = this.attributeMap;
/* 206 */       boolean removed = (attributeMap != null && MAP_UPDATER.compareAndSet(this, attributeMap, null));
/* 207 */       set(null);
/* 208 */       if (removed)
/* 209 */         attributeMap.removeAttributeIfMatch(this.key, this); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\DefaultAttributeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */