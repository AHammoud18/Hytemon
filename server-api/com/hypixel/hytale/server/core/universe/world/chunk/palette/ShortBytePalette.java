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
/*     */ public class ShortBytePalette
/*     */ {
/*     */   public static final int LENGTH = 1024;
/*  20 */   private short count = 1;
/*  21 */   private final Lock keysLock = new ReentrantLock();
/*  22 */   private short[] keys = new short[] { 0 };
/*     */ 
/*     */   
/*  25 */   private final BitFieldArr array = new BitFieldArr(10, 1024);
/*     */ 
/*     */   
/*     */   public ShortBytePalette() {}
/*     */   
/*     */   public ShortBytePalette(short aDefault) {
/*  31 */     this.keys = new short[] { aDefault };
/*     */   }
/*     */   
/*     */   public boolean set(int x, int z, short key) {
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
/*  63 */             short[] keys = new short[newId + 1];
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
/*     */   public short get(int x, int z) {
/*  79 */     return this.keys[this.array.get(ChunkUtil.indexColumn(x, z))];
/*     */   }
/*     */   
/*     */   public short get(int index) {
/*  83 */     return this.keys[this.array.get(index)];
/*     */   }
/*     */   
/*     */   public short contains(short key) {
/*  87 */     this.keysLock.lock();
/*     */     try {
/*  89 */       for (short i = 0; i < this.keys.length; i = (short)(i + 1)) {
/*  90 */         if (this.keys[i] == key) {
/*  91 */           return i;
/*     */         }
/*     */       } 
/*     */     } finally {
/*  95 */       this.keysLock.unlock();
/*     */     } 
/*  97 */     return -1;
/*     */   }
/*     */   
/*     */   public void optimize() {
/* 101 */     optimize(-1);
/*     */   }
/*     */   
/*     */   private void optimize(int index) {
/* 105 */     ShortBytePalette shortBytePalette = new ShortBytePalette(this.keys[this.array.get(0)]);
/* 106 */     for (int i = 0; i < this.array.getLength(); i++) {
/* 107 */       if (i != index) {
/* 108 */         shortBytePalette.set(ChunkUtil.xFromColumn(i), ChunkUtil.zFromColumn(i), this.keys[this.array.get(i)]);
/*     */       }
/*     */     } 
/* 111 */     this.keysLock.lock();
/*     */     try {
/* 113 */       this.count = shortBytePalette.count;
/* 114 */       this.keys = shortBytePalette.keys;
/* 115 */       this.array.set(shortBytePalette.array.get());
/*     */     } finally {
/* 117 */       this.keysLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf dos) {
/* 122 */     this.keysLock.lock();
/*     */     try {
/* 124 */       dos.writeShortLE(this.count);
/* 125 */       for (int i = 0; i < this.count; i++) {
/* 126 */         dos.writeShortLE(this.keys[i]);
/*     */       }
/* 128 */       byte[] bytes = this.array.get();
/* 129 */       dos.writeIntLE(bytes.length);
/* 130 */       dos.writeBytes(bytes);
/*     */     } finally {
/* 132 */       this.keysLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deserialize(@Nonnull ByteBuf buf) {
/* 137 */     this.keysLock.lock();
/*     */     try {
/* 139 */       this.count = buf.readShortLE();
/* 140 */       this.keys = new short[this.count];
/* 141 */       for (int i = 0; i < this.count; i++) {
/* 142 */         this.keys[i] = buf.readShortLE();
/*     */       }
/* 144 */       int length = buf.readIntLE();
/* 145 */       byte[] bytes = new byte[length];
/* 146 */       buf.readBytes(bytes);
/* 147 */       this.array.set(bytes);
/*     */ 
/*     */       
/* 150 */       if (this.count == 0) {
/* 151 */         this.count = 1;
/* 152 */         this.keys = new short[] { 0 };
/*     */       } 
/*     */     } finally {
/* 155 */       this.keysLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] serialize() {
/* 160 */     ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
/* 161 */     serialize(buf);
/* 162 */     return ByteBufUtil.getBytesRelease(buf);
/*     */   }
/*     */   
/*     */   public void copyFrom(@Nonnull ShortBytePalette other) {
/* 166 */     this.keysLock.lock();
/*     */     try {
/* 168 */       this.count = other.count;
/* 169 */       System.arraycopy(other.keys, 0, this.keys, 0, this.keys.length);
/* 170 */       this.array.copyFrom(other.array);
/*     */     } finally {
/* 172 */       this.keysLock.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\palette\ShortBytePalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */