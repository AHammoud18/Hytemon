/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractByteCollection
/*     */   extends AbstractCollection<Byte>
/*     */   implements ByteCollection
/*     */ {
/*     */   public boolean add(byte k) {
/*  48 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(byte k) {
/*  59 */     ByteIterator iterator = iterator();
/*  60 */     while (iterator.hasNext()) { if (k == iterator.nextByte()) return true;  }
/*  61 */      return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rem(byte k) {
/*  72 */     ByteIterator iterator = iterator();
/*  73 */     while (iterator.hasNext()) { if (k == iterator.nextByte()) {
/*  74 */         iterator.remove();
/*  75 */         return true;
/*     */       }  }
/*  77 */      return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean add(Byte key) {
/*  88 */     return super.add(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean contains(Object key) {
/*  99 */     return super.contains(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean remove(Object key) {
/* 110 */     return super.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toArray(byte[] a) {
/* 115 */     int size = size();
/* 116 */     if (a == null) {
/* 117 */       a = new byte[size];
/* 118 */     } else if (a.length < size) {
/* 119 */       a = Arrays.copyOf(a, size);
/*     */     } 
/* 121 */     ByteIterators.unwrap(iterator(), a);
/* 122 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/* 127 */     int size = size();
/* 128 */     if (size == 0) return ByteArrays.EMPTY_ARRAY; 
/* 129 */     byte[] a = new byte[size];
/* 130 */     ByteIterators.unwrap(iterator(), a);
/* 131 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public byte[] toByteArray(byte[] a) {
/* 143 */     return toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(ByteCollection c) {
/* 148 */     boolean retVal = false;
/* 149 */     for (ByteIterator i = c.iterator(); i.hasNext();) { if (add(i.nextByte())) retVal = true;  }
/* 150 */      return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Byte> c) {
/* 161 */     if (c instanceof ByteCollection) {
/* 162 */       return addAll((ByteCollection)c);
/*     */     }
/* 164 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(ByteCollection c) {
/* 169 */     for (ByteIterator i = c.iterator(); i.hasNext();) { if (!contains(i.nextByte())) return false;  }
/* 170 */      return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 181 */     if (c instanceof ByteCollection) {
/* 182 */       return containsAll((ByteCollection)c);
/*     */     }
/* 184 */     return super.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(ByteCollection c) {
/* 189 */     boolean retVal = false;
/* 190 */     for (ByteIterator i = c.iterator(); i.hasNext();) { if (rem(i.nextByte())) retVal = true;  }
/* 191 */      return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 202 */     if (c instanceof ByteCollection) {
/* 203 */       return removeAll((ByteCollection)c);
/*     */     }
/* 205 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(ByteCollection c) {
/* 210 */     boolean retVal = false;
/* 211 */     for (ByteIterator i = iterator(); i.hasNext();) { if (!c.contains(i.nextByte())) {
/* 212 */         i.remove();
/* 213 */         retVal = true;
/*     */       }  }
/* 215 */      return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 226 */     if (c instanceof ByteCollection) {
/* 227 */       return retainAll((ByteCollection)c);
/*     */     }
/* 229 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 234 */     StringBuilder s = new StringBuilder();
/* 235 */     ByteIterator i = iterator();
/* 236 */     int n = size();
/*     */     
/* 238 */     boolean first = true;
/* 239 */     s.append("{");
/* 240 */     while (n-- != 0) {
/* 241 */       if (first) { first = false; }
/* 242 */       else { s.append(", "); }
/* 243 */        byte k = i.nextByte();
/* 244 */       s.append(String.valueOf(k));
/*     */     } 
/* 246 */     s.append("}");
/* 247 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract ByteIterator iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByteCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */