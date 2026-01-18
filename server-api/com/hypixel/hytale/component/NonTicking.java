/*    */ package com.hypixel.hytale.component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NonTicking<ECS_TYPE>
/*    */   implements Component<ECS_TYPE>
/*    */ {
/* 17 */   private static final NonTicking<?> INSTANCE = new NonTicking();
/*    */ 
/*    */   
/*    */   public static <ECS_TYPE> NonTicking<ECS_TYPE> get() {
/* 21 */     return (NonTicking)INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component<ECS_TYPE> clone() {
/* 26 */     return get();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\NonTicking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */