/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.environment;
/*     */ 
/*     */ import com.hypixel.hytale.function.consumer.IntObjectConsumer;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.function.ToIntFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnvironmentColumn
/*     */ {
/*     */   public static final int MIN = -2147483648;
/*     */   public static final int MAX = 2147483647;
/*     */   @Nonnull
/*     */   private final IntArrayList maxYs;
/*     */   @Nonnull
/*     */   private final IntArrayList values;
/*     */   
/*     */   public EnvironmentColumn(@Nonnull int[] maxYs, @Nonnull int[] values) {
/*  38 */     this(new IntArrayList(maxYs), new IntArrayList(values));
/*     */   }
/*     */   
/*     */   public EnvironmentColumn(@Nonnull IntArrayList maxYs, @Nonnull IntArrayList values) {
/*  42 */     if (maxYs.size() + 1 != values.size()) throw new IllegalStateException("maxY + 1 != values"); 
/*  43 */     this.maxYs = maxYs;
/*  44 */     this.values = values;
/*     */   }
/*     */   
/*     */   public EnvironmentColumn(int initialId) {
/*  48 */     this(new IntArrayList(0), new IntArrayList(new int[] { initialId }));
/*     */   }
/*     */   
/*     */   int maxys_size() {
/*  52 */     return this.maxYs.size();
/*     */   }
/*     */   
/*     */   public int size() {
/*  56 */     return this.values.size();
/*     */   }
/*     */   
/*     */   public int getValue(int index) {
/*  60 */     return this.values.getInt(index);
/*     */   }
/*     */   
/*     */   public int getValueMin(int index) {
/*  64 */     if (index <= 0) return Integer.MIN_VALUE; 
/*  65 */     return this.maxYs.getInt(index - 1) + 1;
/*     */   }
/*     */   
/*     */   public int getValueMax(int index) {
/*  69 */     if (index >= this.maxYs.size()) return Integer.MAX_VALUE; 
/*  70 */     return this.maxYs.getInt(index);
/*     */   }
/*     */   
/*     */   public int indexOf(int y) {
/*  74 */     int n = this.maxYs.size();
/*  75 */     if (n == 0) return 0;
/*     */     
/*  77 */     int l = 0;
/*  78 */     int r = n - 1;
/*     */     
/*  80 */     int i = n;
/*  81 */     while (l <= r) {
/*  82 */       int mid = (l + r) / 2;
/*     */       
/*  84 */       if (this.maxYs.getInt(mid) < y) {
/*  85 */         l = mid + 1; continue;
/*     */       } 
/*  87 */       i = mid;
/*  88 */       r = mid - 1;
/*     */     } 
/*     */ 
/*     */     
/*  92 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int value) {
/* 101 */     this.maxYs.clear();
/* 102 */     this.values.clear();
/* 103 */     this.values.add(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(int y) {
/* 113 */     return this.values.getInt(indexOf(y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int y, int value) {
/* 123 */     int idx = indexOf(y);
/* 124 */     int currentValue = this.values.getInt(idx);
/*     */ 
/*     */     
/* 127 */     if (currentValue == value) {
/*     */       return;
/*     */     }
/*     */     
/* 131 */     int keys = this.maxYs.size();
/*     */     
/* 133 */     int max = Integer.MAX_VALUE;
/* 134 */     if (idx < keys) {
/* 135 */       max = this.maxYs.getInt(idx);
/*     */     }
/*     */     
/* 138 */     int min = Integer.MIN_VALUE;
/* 139 */     if (idx > 0) {
/* 140 */       min = this.maxYs.getInt(idx - 1) + 1;
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
/* 152 */     if (min == max) {
/*     */ 
/*     */       
/* 155 */       if (idx < keys && this.values.getInt(idx + 1) == value) {
/* 156 */         this.maxYs.removeInt(idx);
/* 157 */         this.values.removeInt(idx);
/*     */       } else {
/*     */         
/* 160 */         this.values.set(idx, value);
/*     */       } 
/*     */ 
/*     */       
/* 164 */       if (idx != 0 && this.values.getInt(idx - 1) == value) {
/* 165 */         this.maxYs.removeInt(idx - 1);
/* 166 */         this.values.removeInt(idx - 1);
/*     */       } 
/* 168 */     } else if (min == y) {
/*     */       
/* 170 */       if (idx != 0 && this.values.getInt(idx - 1) == value) {
/*     */ 
/*     */         
/* 173 */         this.maxYs.set(idx - 1, y);
/*     */       } else {
/*     */         
/* 176 */         this.maxYs.add(idx, y);
/* 177 */         this.values.add(idx, value);
/*     */       } 
/* 179 */     } else if (max == y) {
/*     */ 
/*     */       
/* 182 */       if (idx == keys) {
/*     */         
/* 184 */         this.maxYs.add(idx, y - 1);
/* 185 */         this.values.add(idx + 1, value);
/*     */       } else {
/*     */         
/* 188 */         this.maxYs.set(idx, y - 1);
/*     */ 
/*     */         
/* 191 */         if (this.values.getInt(idx + 1) != value) {
/* 192 */           this.maxYs.add(idx + 1, y);
/* 193 */           this.values.add(idx + 1, value);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 201 */       this.maxYs.add(idx, y);
/* 202 */       this.values.add(idx, value);
/*     */       
/* 204 */       this.maxYs.add(idx, y - 1);
/* 205 */       this.values.add(idx, currentValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getMin(int y) {
/* 210 */     int idx = indexOf(y);
/*     */     
/* 212 */     int min = Integer.MIN_VALUE;
/* 213 */     if (idx > 0) {
/* 214 */       min = this.maxYs.getInt(idx - 1) + 1;
/*     */     }
/*     */     
/* 217 */     return min;
/*     */   }
/*     */   
/*     */   public int getMax(int y) {
/* 221 */     int idx = indexOf(y);
/* 222 */     int keys = this.maxYs.size();
/*     */     
/* 224 */     int max = Integer.MAX_VALUE;
/* 225 */     if (idx < keys) {
/* 226 */       max = this.maxYs.getInt(idx);
/*     */     }
/*     */     
/* 229 */     return max;
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
/*     */   public void set(int fromY, int toY, int value) {
/* 241 */     for (int y = fromY; y <= toY; y++) {
/* 242 */       set(y, value);
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
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf, @Nonnull IntObjectConsumer<ByteBuf> valueSerializer) {
/* 259 */     int n = this.maxYs.size();
/* 260 */     buf.writeInt(n); int i;
/* 261 */     for (i = 0; i < n; i++) {
/* 262 */       buf.writeInt(this.maxYs.getInt(i));
/*     */     }
/* 264 */     for (i = 0; i <= n; i++) {
/* 265 */       valueSerializer.accept(this.values.getInt(i), buf);
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
/*     */   public void serializeProtocol(@Nonnull ByteBuf buf) {
/* 277 */     int n = this.maxYs.size();
/* 278 */     buf.writeShortLE(n + 1);
/*     */     
/* 280 */     int min = Integer.MIN_VALUE;
/* 281 */     for (int i = 0; i < n; i++) {
/* 282 */       buf.writeShortLE(min);
/* 283 */       buf.writeShortLE(this.values.getInt(i));
/*     */       
/* 285 */       int max = this.maxYs.getInt(i);
/* 286 */       min = max + 1;
/*     */     } 
/*     */     
/* 289 */     buf.writeShortLE(min);
/* 290 */     buf.writeShortLE(this.values.getInt(n));
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
/*     */   public void deserialize(@Nonnull ByteBuf buf, @Nonnull ToIntFunction<ByteBuf> valueDeserializer) {
/* 306 */     this.maxYs.clear();
/* 307 */     this.values.clear();
/*     */     
/* 309 */     int n = buf.readInt();
/*     */ 
/*     */     
/* 312 */     this.maxYs.ensureCapacity(n);
/* 313 */     this.values.ensureCapacity(n + 1);
/*     */     int i;
/* 315 */     for (i = 0; i < n; i++) {
/* 316 */       this.maxYs.add(buf.readInt());
/*     */     }
/* 318 */     for (i = 0; i <= n; i++) {
/* 319 */       this.values.add(valueDeserializer.applyAsInt(buf));
/*     */     }
/*     */   }
/*     */   
/*     */   public void copyFrom(@Nonnull EnvironmentColumn other) {
/* 324 */     this.maxYs.clear();
/* 325 */     this.values.clear();
/*     */     
/* 327 */     this.maxYs.ensureCapacity(other.maxYs.size());
/* 328 */     this.values.ensureCapacity(other.values.size());
/*     */     
/* 330 */     this.maxYs.addAll((IntList)other.maxYs);
/* 331 */     this.values.addAll((IntList)other.values);
/*     */   }
/*     */   
/*     */   public void trim() {
/* 335 */     this.maxYs.trim();
/* 336 */     this.values.trim();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 341 */     if (this == o) return true; 
/* 342 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 344 */     EnvironmentColumn that = (EnvironmentColumn)o;
/*     */     
/* 346 */     if ((this.maxYs != null) ? !this.maxYs.equals(that.maxYs) : (that.maxYs != null)) return false; 
/* 347 */     return (this.values != null) ? this.values.equals(that.values) : ((that.values == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 352 */     int result = (this.maxYs != null) ? this.maxYs.hashCode() : 0;
/* 353 */     result = 31 * result + ((this.values != null) ? this.values.hashCode() : 0);
/* 354 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 360 */     return "EnvironmentColumn{maxYs=" + String.valueOf(this.maxYs) + ", values=" + String.valueOf(this.values) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\environment\EnvironmentColumn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */