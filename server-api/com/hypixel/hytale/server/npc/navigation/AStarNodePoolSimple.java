/*    */ package com.hypixel.hytale.server.npc.navigation;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AStarNodePoolSimple
/*    */   implements AStarNodePool
/*    */ {
/* 12 */   protected final List<AStarNode> nodePool = (List<AStarNode>)new ObjectArrayList();
/*    */   private final int childCount;
/*    */   
/*    */   public AStarNodePoolSimple(int childCount) {
/* 16 */     this.childCount = childCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public AStarNode allocate() {
/* 21 */     return this.nodePool.isEmpty() ? new AStarNode(this.childCount) : this.nodePool.removeLast();
/*    */   }
/*    */ 
/*    */   
/*    */   public void deallocate(AStarNode node) {
/* 26 */     this.nodePool.add(node);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\navigation\AStarNodePoolSimple.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */