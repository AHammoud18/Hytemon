/*    */ package com.hypixel.hytale.server.core.modules.collision;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class CollisionTracker
/*    */   extends BlockTracker
/*    */ {
/*    */   @Nonnull
/* 12 */   protected BlockData[] blockData = new BlockData[4];
/*    */   @Nonnull
/* 14 */   protected BlockContactData[] contactData = new BlockContactData[4];
/*    */ 
/*    */   
/*    */   public CollisionTracker() {
/* 18 */     for (int i = 0; i < 4; i++) {
/* 19 */       this.blockData[i] = new BlockData();
/* 20 */       this.contactData[i] = new BlockContactData();
/*    */     } 
/*    */   }
/*    */   
/*    */   public BlockData getBlockData(int index) {
/* 25 */     return this.blockData[index];
/*    */   }
/*    */   
/*    */   public BlockContactData getContactData(int index) {
/* 29 */     return this.contactData[index];
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 34 */     super.reset();
/* 35 */     for (int i = 0; i < this.count; i++) {
/* 36 */       this.blockData[i].clear();
/* 37 */       this.contactData[i].clear();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean track(int x, int y, int z, @Nonnull BlockContactData contactData, @Nonnull BlockData blockData) {
/* 42 */     if (isTracked(x, y, z)) return true; 
/* 43 */     trackNew(x, y, z, contactData, blockData);
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public BlockContactData trackNew(int x, int y, int z, @Nonnull BlockContactData contactData, @Nonnull BlockData blockData) {
/* 49 */     trackNew(x, y, z);
/* 50 */     this.blockData[this.count - 1].assign(blockData);
/* 51 */     BlockContactData data = this.contactData[this.count - 1];
/* 52 */     data.assign(contactData);
/* 53 */     return data;
/*    */   }
/*    */ 
/*    */   
/*    */   public void untrack(int index) {
/* 58 */     super.untrack(index);
/* 59 */     if (this.count == 0) {
/* 60 */       this.blockData[0].clear();
/* 61 */       this.contactData[0].clear();
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     int length = this.count - index;
/*    */     
/* 67 */     BlockData block = this.blockData[index];
/* 68 */     block.clear();
/* 69 */     System.arraycopy(this.blockData, index + 1, this.blockData, index, length);
/* 70 */     this.blockData[this.count] = block;
/*    */     
/* 72 */     BlockContactData coll = this.contactData[index];
/* 73 */     coll.clear();
/* 74 */     System.arraycopy(this.contactData, index + 1, this.contactData, index, length);
/* 75 */     this.contactData[this.count] = coll;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BlockContactData getContactData(int x, int y, int z) {
/* 80 */     int index = getIndex(x, y, z);
/* 81 */     if (index == -1) return null;
/*    */     
/* 83 */     return this.contactData[index];
/*    */   }
/*    */ 
/*    */   
/*    */   protected void alloc() {
/* 88 */     super.alloc();
/*    */     
/* 90 */     int newLength = this.blockData.length + 4;
/* 91 */     this.blockData = Arrays.<BlockData>copyOf(this.blockData, newLength);
/* 92 */     this.contactData = Arrays.<BlockContactData>copyOf(this.contactData, newLength);
/* 93 */     for (int i = this.count; i < newLength; i++) {
/* 94 */       this.blockData[i] = new BlockData();
/* 95 */       this.contactData[i] = new BlockContactData();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\CollisionTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */