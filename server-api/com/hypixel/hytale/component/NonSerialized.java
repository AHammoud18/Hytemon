/*    */ package com.hypixel.hytale.component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NonSerialized<ECS_TYPE>
/*    */   implements Component<ECS_TYPE>
/*    */ {
/* 10 */   private static final NonSerialized<?> INSTANCE = new NonSerialized();
/*    */ 
/*    */   
/*    */   public static <ECS_TYPE> NonSerialized<ECS_TYPE> get() {
/* 14 */     return (NonSerialized)INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component<ECS_TYPE> clone() {
/* 19 */     return get();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\NonSerialized.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */