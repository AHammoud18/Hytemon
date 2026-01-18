/*    */ package com.hypixel.hytale.server.core.modules.collision;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockTracker
/*    */   implements IBlockTracker
/*    */ {
/*    */   public static final int NOT_FOUND = -1;
/*    */   protected static final int ALLOC_SIZE = 4;
/*    */   @Nonnull
/* 20 */   protected Vector3i[] positions = new Vector3i[4];
/*    */   
/*    */   protected int count;
/*    */   
/*    */   public BlockTracker() {
/* 25 */     for (int i = 0; i < this.positions.length; i++) {
/* 26 */       this.positions[i] = new Vector3i();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector3i getPosition(int index) {
/* 32 */     return this.positions[index];
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 37 */     return this.count;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 41 */     this.count = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean track(int x, int y, int z) {
/* 46 */     if (isTracked(x, y, z)) return true; 
/* 47 */     trackNew(x, y, z);
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void trackNew(int x, int y, int z) {
/* 53 */     if (this.count >= this.positions.length) {
/* 54 */       alloc();
/*    */     }
/* 56 */     Vector3i v = this.positions[this.count++];
/* 57 */     v.x = x;
/* 58 */     v.y = y;
/* 59 */     v.z = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTracked(int x, int y, int z) {
/* 64 */     return (getIndex(x, y, z) >= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void untrack(int x, int y, int z) {
/* 69 */     int index = getIndex(x, y, z);
/* 70 */     if (index >= 0) untrack(index); 
/*    */   }
/*    */   
/*    */   public void untrack(int index) {
/* 74 */     if (this.count <= 0) throw new IllegalStateException("Calling untrack on empty tracker"); 
/* 75 */     this.count--;
/* 76 */     if (this.count == 0)
/*    */       return; 
/* 78 */     Vector3i v = this.positions[index];
/* 79 */     System.arraycopy(this.positions, index + 1, this.positions, index, this.count - index);
/* 80 */     this.positions[this.count] = v;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIndex(int x, int y, int z) {
/* 85 */     for (int i = this.count - 1; i >= 0; i--) {
/* 86 */       Vector3i v = this.positions[i];
/* 87 */       if (v.x == x && v.y == y && v.z == z) return i; 
/*    */     } 
/* 89 */     return -1;
/*    */   }
/*    */   
/*    */   protected void alloc() {
/* 93 */     this.positions = Arrays.<Vector3i>copyOf(this.positions, this.positions.length + 4);
/* 94 */     for (int i = this.count; i < this.positions.length; i++)
/* 95 */       this.positions[i] = new Vector3i(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\BlockTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */