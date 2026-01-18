/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.palette;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.util.io.ByteBufUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntBytePalette
/*     */ {
/*     */   public static final int LENGTH = 1024;
/*  20 */   private short count = 1;
/*  21 */   private final Lock keysLock = new ReentrantLock();
/*  22 */   private int[] keys = new int[] { 0 };
/*     */ 
/*     */   
/*  25 */   private final BitFieldArr array = new BitFieldArr(10, 1024);
/*     */ 
/*     */   
/*     */   public IntBytePalette() {}
/*     */   
/*     */   public IntBytePalette(int aDefault) {
/*  31 */     this.keys = new int[] { aDefault };
/*     */   }
/*     */   
/*     */   public boolean set(int x, int z, int key) {
/*  35 */     short id = contains(key);
/*  36 */     int index = ChunkUtil.indexColumn(x, z);
/*  37 */     if (id >= 1024) {
/*  38 */       optimize(index);
/*  39 */       id = contains(key);
/*     */     } 
/*  41 */     if (id >= 0) {
/*  42 */       this.array.set(index, id);
/*     */     } else {
/*  44 */       this.keysLock.lock();
/*     */       try {
/*  46 */         short oldId = contains(key);
/*  47 */         if (oldId >= 1024) {
/*  48 */           optimize(index);
/*  49 */           oldId = contains(key);
/*     */         } 
/*  51 */         if (oldId >= 0) {
/*  52 */           this.array.set(index, oldId);
/*     */         } else {
/*  54 */           short newId = this.count = (short)(this.count + 1);
/*  55 */           if (newId >= Short.MAX_VALUE)
/*  56 */             throw new IllegalArgumentException("Can't have more than 32767"); 
/*  57 */           if (newId >= 1024) {
/*  58 */             optimize(index);
/*  59 */             newId = this.count = (short)(this.count + 1);
/*     */           } 
/*     */           
/*  62 */           if (newId >= this.keys.length) {
/*  63 */             int[] keys = new int[newId + 1];
/*  64 */             System.arraycopy(this.keys, 0, keys, 0, this.keys.length);
/*  65 */             this.keys = keys;
/*     */           } 
/*     */           
/*  68 */           this.keys[newId] = key;
/*  69 */           this.array.set(index, newId);
/*     */         } 
/*     */       } finally {
/*  72 */         this.keysLock.unlock();
/*     */       } 
/*     */     } 
/*  75 */     return true;
/*     */   }
/*     */   
/*     */   public int get(int x, int z) {
/*  79 */     return this.keys[this.array.get(ChunkUtil.indexColumn(x, z))];
/*     */   }
/*     */   
/*     */   public short contains(int key) {
/*  83 */     this.keysLock.lock();
/*     */     try {
/*  85 */       for (short i = 0; i < this.keys.length; i = (short)(i + 1)) {
/*  86 */         int k = this.keys[i];
/*  87 */         if (k == key) {
/*  88 */           return i;
/*     */         }
/*     */       } 
/*     */     } finally {
/*  92 */       this.keysLock.unlock();
/*     */     } 
/*  94 */     return -1;
/*     */   }
/*     */   
/*     */   public void optimize() {
/*  98 */     optimize(-1);
/*     */   }
/*     */   
/*     */   private void optimize(int index) {
/* 102 */     IntBytePalette intBytePalette = new IntBytePalette(this.keys[this.array.get(0)]);
/* 103 */     for (int i = 0; i < this.array.getLength(); i++) {
/* 104 */       if (i != index) {
/* 105 */         intBytePalette.set(ChunkUtil.xFromColumn(i), ChunkUtil.zFromColumn(i), this.keys[this.array.get(i)]);
/*     */       }
/*     */     } 
/* 108 */     this.keysLock.lock();
/*     */     try {
/* 110 */       this.count = intBytePalette.count;
/* 111 */       this.keys = intBytePalette.keys;
/* 112 */       this.array.set(intBytePalette.array.get());
/*     */     } finally {
/* 114 */       this.keysLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf dos) {
/* 119 */     this.keysLock.lock();
/*     */     try {
/* 121 */       dos.writeShortLE(this.count);
/* 122 */       for (int i = 0; i < this.count; i++) {
/* 123 */         dos.writeIntLE(this.keys[i]);
/*     */       }
/* 125 */       byte[] bytes = this.array.get();
/* 126 */       dos.writeIntLE(bytes.length);
/* 127 */       dos.writeBytes(bytes);
/*     */     } finally {
/* 129 */       this.keysLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deserialize(@Nonnull ByteBuf dis) {
/* 134 */     this.keysLock.lock();
/*     */     try {
/* 136 */       this.count = dis.readShortLE();
/* 137 */       this.keys = new int[this.count];
/* 138 */       for (int i = 0; i < this.count; i++) {
/* 139 */         this.keys[i] = dis.readIntLE();
/*     */       }
/* 141 */       int length = dis.readIntLE();
/* 142 */       byte[] bytes = new byte[length];
/* 143 */       dis.readBytes(bytes);
/* 144 */       this.array.set(bytes);
/*     */ 
/*     */       
/* 147 */       if (this.count == 0) {
/* 148 */         this.count = 1;
/* 149 */         this.keys = new int[] { 0 };
/*     */       } 
/*     */     } finally {
/* 152 */       this.keysLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] serialize() {
/* 157 */     ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
/* 158 */     serialize(buf);
/* 159 */     return ByteBufUtil.getBytesRelease(buf);
/*     */   }
/*     */   
/*     */   public void copyFrom(@Nonnull IntBytePalette other) {
/* 163 */     this.keysLock.lock();
/*     */     try {
/* 165 */       this.count = other.count;
/* 166 */       System.arraycopy(other.keys, 0, this.keys, 0, this.keys.length);
/* 167 */       this.array.copyFrom(other.array);
/*     */     } finally {
/* 169 */       this.keysLock.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\palette\IntBytePalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */