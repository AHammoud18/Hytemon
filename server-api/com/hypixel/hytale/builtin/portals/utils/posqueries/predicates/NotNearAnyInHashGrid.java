/*    */ package com.hypixel.hytale.builtin.portals.utils.posqueries.predicates;
/*    */ public final class NotNearAnyInHashGrid extends Record implements PositionPredicate { private final SpatialHashGrid<?> hashGrid; private final double radius;
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/portals/utils/posqueries/predicates/NotNearAnyInHashGrid;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/portals/utils/posqueries/predicates/NotNearAnyInHashGrid;
/*    */   }
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/portals/utils/posqueries/predicates/NotNearAnyInHashGrid;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/portals/utils/posqueries/predicates/NotNearAnyInHashGrid;
/*    */   }
/*    */   
/* 10 */   public NotNearAnyInHashGrid(SpatialHashGrid<?> hashGrid, double radius) { this.hashGrid = hashGrid; this.radius = radius; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/portals/utils/posqueries/predicates/NotNearAnyInHashGrid;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/portals/utils/posqueries/predicates/NotNearAnyInHashGrid;
/* 10 */     //   0	8	1	o	Ljava/lang/Object; } public SpatialHashGrid<?> hashGrid() { return this.hashGrid; } public double radius() { return this.radius; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(World world, Vector3d point) {
/* 16 */     return !this.hashGrid.hasAnyWithin(point, this.radius);
/*    */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\predicates\NotNearAnyInHashGrid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */