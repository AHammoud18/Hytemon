/*     */ package com.google.common.flogger.context;
/*     */ 
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
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
/*     */ public final class Tags
/*     */ {
/*     */   private enum Type
/*     */   {
/*  71 */     BOOLEAN
/*     */     {
/*     */       int compare(Object lhs, Object rhs) {
/*  74 */         return ((Boolean)lhs).compareTo((Boolean)rhs);
/*     */       }
/*     */     },
/*  77 */     STRING
/*     */     {
/*     */       int compare(Object lhs, Object rhs) {
/*  80 */         return ((String)lhs).compareTo((String)rhs);
/*     */       }
/*     */     },
/*  83 */     LONG
/*     */     {
/*     */       int compare(Object lhs, Object rhs) {
/*  86 */         return ((Long)lhs).compareTo((Long)rhs);
/*     */       }
/*     */     },
/*  89 */     DOUBLE
/*     */     {
/*     */       int compare(Object lhs, Object rhs) {
/*  92 */         return ((Double)lhs).compareTo((Double)rhs);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Type of(Object tag) {
/* 100 */       if (tag instanceof String)
/* 101 */         return STRING; 
/* 102 */       if (tag instanceof Boolean)
/* 103 */         return BOOLEAN; 
/* 104 */       if (tag instanceof Long)
/* 105 */         return LONG; 
/* 106 */       if (tag instanceof Double) {
/* 107 */         return DOUBLE;
/*     */       }
/*     */       
/* 110 */       throw new AssertionError("invalid tag type: " + tag.getClass());
/*     */     }
/*     */     
/*     */     abstract int compare(Object param1Object1, Object param1Object2); }
/*     */   
/* 115 */   private static final Comparator<Object> VALUE_COMPARATOR = new Comparator()
/*     */     {
/*     */       
/*     */       public int compare(Object lhs, Object rhs)
/*     */       {
/* 120 */         Tags.Type ltype = Tags.Type.of(lhs);
/* 121 */         Tags.Type rtype = Tags.Type.of(rhs);
/* 122 */         return (ltype == rtype) ? ltype.compare(lhs, rhs) : ltype.compareTo(rtype);
/*     */       }
/*     */     };
/*     */   
/*     */   private static final class KeyValuePair {
/*     */     private final String key;
/*     */     @NullableDecl
/*     */     private final Object value;
/*     */     
/*     */     private KeyValuePair(String key, @NullableDecl Object value) {
/* 132 */       this.key = key;
/* 133 */       this.value = value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   private static final Comparator<KeyValuePair> KEY_VALUE_COMPARATOR = new Comparator<KeyValuePair>()
/*     */     {
/*     */       public int compare(Tags.KeyValuePair lhs, Tags.KeyValuePair rhs)
/*     */       {
/* 144 */         int signum = lhs.key.compareTo(rhs.key);
/* 145 */         if (signum == 0) {
/* 146 */           if (lhs.value != null) {
/* 147 */             signum = (rhs.value != null) ? Tags.VALUE_COMPARATOR.compare(lhs.value, rhs.value) : 1;
/*     */           } else {
/* 149 */             signum = (rhs.value != null) ? -1 : 0;
/*     */           } 
/*     */         }
/* 152 */         return signum;
/*     */       }
/*     */     };
/*     */   
/* 156 */   private static final Tags EMPTY_TAGS = new Tags(new LightweightTagMap(
/* 157 */         Collections.emptyList()));
/*     */   private final LightweightTagMap map;
/*     */   
/*     */   public static final class Builder {
/* 161 */     private final List<Tags.KeyValuePair> keyValuePairs = new ArrayList<Tags.KeyValuePair>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addTag(String name) {
/* 173 */       return addImpl(name, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addTag(String name, String value) {
/* 182 */       Checks.checkArgument((value != null), "tag value");
/* 183 */       return addImpl(name, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addTag(String name, boolean value) {
/* 192 */       return addImpl(name, Boolean.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addTag(String name, long value) {
/* 205 */       return addImpl(name, Long.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addTag(String name, double value) {
/* 218 */       return addImpl(name, Double.valueOf(value));
/*     */     }
/*     */     
/*     */     private Builder addImpl(String name, @NullableDecl Object value) {
/* 222 */       this.keyValuePairs.add(new Tags.KeyValuePair(Checks.checkMetadataIdentifier(name), value));
/* 223 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Tags build() {
/* 228 */       if (this.keyValuePairs.isEmpty()) {
/* 229 */         return Tags.EMPTY_TAGS;
/*     */       }
/*     */ 
/*     */       
/* 233 */       Collections.sort(this.keyValuePairs, Tags.KEY_VALUE_COMPARATOR);
/* 234 */       return new Tags(new Tags.LightweightTagMap(this.keyValuePairs));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 239 */       return build().toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/* 245 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Tags empty() {
/* 250 */     return EMPTY_TAGS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tags of(String name, String value) {
/* 258 */     return new Tags(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tags of(String name, boolean value) {
/* 266 */     return new Tags(name, Boolean.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tags of(String name, long value) {
/* 274 */     return new Tags(name, Long.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tags of(String name, double value) {
/* 282 */     return new Tags(name, Double.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Tags(String name, Object value) {
/* 289 */     this(new LightweightTagMap(Checks.checkMetadataIdentifier(name), Checks.checkNotNull(value, "value")));
/*     */   }
/*     */ 
/*     */   
/*     */   private Tags(LightweightTagMap map) {
/* 294 */     this.map = map;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Set<Object>> asMap() {
/* 299 */     return this.map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 305 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Tags merge(Tags other) {
/* 311 */     if (other.isEmpty()) {
/* 312 */       return this;
/*     */     }
/* 314 */     if (isEmpty()) {
/* 315 */       return other;
/*     */     }
/*     */ 
/*     */     
/* 319 */     return new Tags(new LightweightTagMap(this.map, other.map));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@NullableDecl Object obj) {
/* 324 */     return (obj instanceof Tags && ((Tags)obj).map.equals(this.map));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 330 */     return this.map.hashCode() ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 340 */     return this.map.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LightweightTagMap
/*     */     extends AbstractMap<String, Set<Object>>
/*     */   {
/* 365 */     private static final Comparator<Object> ENTRY_COMPARATOR = new Comparator()
/*     */       {
/*     */         
/*     */         public int compare(Object s1, Object s2)
/*     */         {
/* 370 */           return ((String)((Map.Entry)s1).getKey()).compareTo((String)((Map.Entry)s2).getKey());
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int SMALL_ARRAY_LENGTH = 16;
/*     */ 
/*     */ 
/*     */     
/* 381 */     private static final int[] singletonOffsets = new int[] { 1, 2 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Object[] array;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int[] offsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 402 */     private final Set<Map.Entry<String, Set<Object>>> entrySet = new SortedArraySet<Map.Entry<String, Set<Object>>>(-1);
/*     */ 
/*     */ 
/*     */     
/* 406 */     private Integer hashCode = null;
/* 407 */     private String toString = null;
/*     */ 
/*     */ 
/*     */     
/*     */     LightweightTagMap(String name, Object value) {
/* 412 */       this.offsets = singletonOffsets;
/* 413 */       this.array = new Object[] { newEntry(name, 0), value };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LightweightTagMap(List<Tags.KeyValuePair> sortedPairs) {
/* 421 */       int entryCount = countMapEntries(sortedPairs);
/* 422 */       Object[] array = new Object[entryCount + sortedPairs.size()];
/* 423 */       int[] offsets = new int[entryCount + 1];
/*     */       
/* 425 */       int totalElementCount = makeTagMap(sortedPairs, entryCount, array, offsets);
/* 426 */       this.array = maybeResizeElementArray(array, totalElementCount);
/* 427 */       this.offsets = offsets;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LightweightTagMap(LightweightTagMap lhs, LightweightTagMap rhs) {
/* 438 */       int maxEntryCount = lhs.size() + rhs.size();
/* 439 */       Object[] array = new Object[lhs.getTotalElementCount() + rhs.getTotalElementCount()];
/* 440 */       int[] offsets = new int[maxEntryCount + 1];
/*     */       
/* 442 */       int totalElementCount = mergeTagMaps(lhs, rhs, maxEntryCount, array, offsets);
/* 443 */       this.array = adjustOffsetsAndMaybeResize(array, offsets, totalElementCount);
/* 444 */       this.offsets = maybeResizeOffsetsArray(offsets);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static int countMapEntries(List<Tags.KeyValuePair> sortedPairs) {
/* 451 */       String key = null;
/* 452 */       int count = 0;
/* 453 */       for (Tags.KeyValuePair pair : sortedPairs) {
/* 454 */         if (!pair.key.equals(key)) {
/* 455 */           key = pair.key;
/* 456 */           count++;
/*     */         } 
/*     */       } 
/* 459 */       return count;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int makeTagMap(List<Tags.KeyValuePair> sortedPairs, int entryCount, Object[] array, int[] offsets) {
/* 466 */       String key = null;
/* 467 */       Object value = null;
/* 468 */       int newEntryIndex = 0;
/* 469 */       int valueStart = entryCount;
/* 470 */       for (Tags.KeyValuePair pair : sortedPairs) {
/* 471 */         if (!pair.key.equals(key)) {
/* 472 */           key = pair.key;
/* 473 */           array[newEntryIndex] = newEntry(key, newEntryIndex);
/* 474 */           offsets[newEntryIndex] = valueStart;
/* 475 */           newEntryIndex++;
/* 476 */           value = null;
/*     */         } 
/* 478 */         if (pair.value != null && !pair.value.equals(value)) {
/* 479 */           value = pair.value;
/* 480 */           array[valueStart++] = value;
/*     */         } 
/*     */       } 
/*     */       
/* 484 */       if (newEntryIndex != entryCount) {
/* 485 */         throw new ConcurrentModificationException("corrupted tag map");
/*     */       }
/* 487 */       offsets[entryCount] = valueStart;
/* 488 */       return valueStart;
/*     */     }
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
/*     */     private int mergeTagMaps(LightweightTagMap lhs, LightweightTagMap rhs, int maxEntryCount, Object[] array, int[] offsets) {
/* 502 */       int valueStart = maxEntryCount;
/*     */       
/* 504 */       offsets[0] = valueStart;
/*     */ 
/*     */       
/* 507 */       int lhsEntryIndex = 0;
/* 508 */       Map.Entry<String, SortedArraySet<Object>> lhsEntry = lhs.getEntryOrNull(lhsEntryIndex);
/* 509 */       int rhsEntryIndex = 0;
/* 510 */       Map.Entry<String, SortedArraySet<Object>> rhsEntry = rhs.getEntryOrNull(rhsEntryIndex);
/*     */       
/* 512 */       int newEntryIndex = 0;
/* 513 */       while (lhsEntry != null || rhsEntry != null) {
/*     */         
/* 515 */         int signum = (lhsEntry == null) ? 1 : ((rhsEntry == null) ? -1 : 0);
/* 516 */         if (signum == 0) {
/*     */           
/* 518 */           signum = ((String)lhsEntry.getKey()).compareTo(rhsEntry.getKey());
/* 519 */           if (signum == 0) {
/*     */             
/* 521 */             array[newEntryIndex] = newEntry(lhsEntry.getKey(), newEntryIndex);
/* 522 */             newEntryIndex++;
/* 523 */             valueStart = mergeValues(lhsEntry.getValue(), rhsEntry.getValue(), array, valueStart);
/* 524 */             offsets[newEntryIndex] = valueStart;
/* 525 */             lhsEntry = lhs.getEntryOrNull(++lhsEntryIndex);
/* 526 */             rhsEntry = rhs.getEntryOrNull(++rhsEntryIndex);
/*     */             
/*     */             continue;
/*     */           } 
/*     */         } 
/* 531 */         if (signum < 0) {
/* 532 */           valueStart = copyEntryAndValues(lhsEntry, newEntryIndex++, valueStart, array, offsets);
/* 533 */           lhsEntry = lhs.getEntryOrNull(++lhsEntryIndex); continue;
/*     */         } 
/* 535 */         valueStart = copyEntryAndValues(rhsEntry, newEntryIndex++, valueStart, array, offsets);
/* 536 */         rhsEntry = rhs.getEntryOrNull(++rhsEntryIndex);
/*     */       } 
/*     */       
/* 539 */       return newEntryIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static int mergeValues(SortedArraySet<?> lhs, SortedArraySet<?> rhs, Object[] array, int valueStart) {
/* 547 */       int lhsIndex = 0;
/* 548 */       int rhsIndex = 0;
/* 549 */       while (lhsIndex < lhs.size() || rhsIndex < rhs.size()) {
/* 550 */         Object value; int signum = (lhsIndex == lhs.size()) ? 1 : ((rhsIndex == rhs.size()) ? -1 : 0);
/* 551 */         if (signum == 0) {
/* 552 */           signum = Tags.VALUE_COMPARATOR.compare(lhs.getValue(lhsIndex), rhs.getValue(rhsIndex));
/*     */         }
/*     */ 
/*     */         
/* 556 */         if (signum < 0) {
/* 557 */           value = lhs.getValue(lhsIndex++);
/*     */         } else {
/* 559 */           value = rhs.getValue(rhsIndex++);
/* 560 */           if (signum == 0)
/*     */           {
/* 562 */             lhsIndex++;
/*     */           }
/*     */         } 
/* 565 */         array[valueStart++] = value;
/*     */       } 
/* 567 */       return valueStart;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int copyEntryAndValues(Map.Entry<String, SortedArraySet<Object>> entry, int entryIndex, int valueStart, Object[] array, int[] offsets) {
/* 577 */       SortedArraySet<Object> values = entry.getValue();
/* 578 */       int valueCount = values.getEnd() - values.getStart();
/* 579 */       System.arraycopy(values.getValuesArray(), values.getStart(), array, valueStart, valueCount);
/* 580 */       array[entryIndex] = newEntry(entry.getKey(), entryIndex);
/*     */       
/* 582 */       int valueEnd = valueStart + valueCount;
/* 583 */       offsets[entryIndex + 1] = valueEnd;
/* 584 */       return valueEnd;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Object[] adjustOffsetsAndMaybeResize(Object[] array, int[] offsets, int entryCount) {
/* 593 */       int maxEntries = offsets[0];
/* 594 */       int offsetReduction = maxEntries - entryCount;
/* 595 */       if (offsetReduction == 0) {
/* 596 */         return array;
/*     */       }
/* 598 */       for (int i = 0; i <= entryCount; i++) {
/* 599 */         offsets[i] = offsets[i] - offsetReduction;
/*     */       }
/* 601 */       Object[] dstArray = array;
/* 602 */       int totalElementCount = offsets[entryCount];
/* 603 */       int valueCount = totalElementCount - entryCount;
/* 604 */       if (mustResize(array.length, totalElementCount)) {
/* 605 */         dstArray = new Object[totalElementCount];
/* 606 */         System.arraycopy(array, 0, dstArray, 0, entryCount);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 611 */       System.arraycopy(array, maxEntries, dstArray, entryCount, valueCount);
/* 612 */       return dstArray;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Object[] maybeResizeElementArray(Object[] array, int bestLength) {
/* 619 */       return mustResize(array.length, bestLength) ? Arrays.<Object>copyOf(array, bestLength) : array;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static int[] maybeResizeOffsetsArray(int[] offsets) {
/* 625 */       int bestLength = offsets[0] + 1;
/* 626 */       return mustResize(offsets.length, bestLength) ? Arrays.copyOf(offsets, bestLength) : offsets;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean mustResize(int actualLength, int bestLength) {
/* 632 */       return (actualLength > 16 && 9 * actualLength > 10 * bestLength);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Map.Entry<String, SortedArraySet<Object>> newEntry(String key, int index) {
/* 638 */       return new AbstractMap.SimpleImmutableEntry<String, SortedArraySet<Object>>(key, new SortedArraySet(index));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Map.Entry<String, SortedArraySet<Object>> getEntryOrNull(int index) {
/* 644 */       return (index < this.offsets[0]) ? (Map.Entry<String, SortedArraySet<Object>>)this.array[index] : null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private int getTotalElementCount() {
/* 650 */       return this.offsets[size()];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<String, Set<Object>>> entrySet() {
/* 657 */       return this.entrySet;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     class SortedArraySet<T>
/*     */       extends AbstractSet<T>
/*     */     {
/*     */       final int index;
/*     */ 
/*     */ 
/*     */       
/*     */       SortedArraySet(int index) {
/* 671 */         this.index = index;
/*     */       }
/*     */       
/*     */       Object[] getValuesArray() {
/* 675 */         return Tags.LightweightTagMap.this.array;
/*     */       }
/*     */ 
/*     */       
/*     */       Object getValue(int n) {
/* 680 */         return Tags.LightweightTagMap.this.array[getStart() + n];
/*     */       }
/*     */       
/*     */       int getStart() {
/* 684 */         return (this.index == -1) ? 0 : Tags.LightweightTagMap.this.offsets[this.index];
/*     */       }
/*     */       
/*     */       int getEnd() {
/* 688 */         return Tags.LightweightTagMap.this.offsets[this.index + 1];
/*     */       }
/*     */       
/*     */       private Comparator<Object> getComparator() {
/* 692 */         return (this.index == -1) ? Tags.LightweightTagMap.ENTRY_COMPARATOR : Tags.VALUE_COMPARATOR;
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/* 697 */         return getEnd() - getStart();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean contains(Object o) {
/* 704 */         return (Arrays.binarySearch(Tags.LightweightTagMap.this.array, getStart(), getEnd(), o, getComparator()) >= 0);
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<T> iterator() {
/* 709 */         return new Iterator<T>() {
/* 710 */             private int n = 0;
/*     */ 
/*     */             
/*     */             public boolean hasNext() {
/* 714 */               return (this.n < Tags.LightweightTagMap.SortedArraySet.this.size());
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public T next() {
/* 724 */               int index = this.n;
/* 725 */               if (index < Tags.LightweightTagMap.SortedArraySet.this.size()) {
/* 726 */                 T value = (T)Tags.LightweightTagMap.this.array[Tags.LightweightTagMap.SortedArraySet.this.getStart() + index];
/*     */                 
/* 728 */                 this.n = index + 1;
/* 729 */                 return value;
/*     */               } 
/* 731 */               throw new NoSuchElementException();
/*     */             }
/*     */           };
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 740 */       if (this.hashCode == null) {
/* 741 */         this.hashCode = Integer.valueOf(super.hashCode());
/*     */       }
/* 743 */       return this.hashCode.intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 748 */       if (this.toString == null) {
/* 749 */         this.toString = super.toString();
/*     */       }
/* 751 */       return this.toString;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\context\Tags.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */