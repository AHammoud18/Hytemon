/*    */ package com.hypixel.hytale.codec.schema.metadata.ui;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
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
/*    */ public class Timeline
/*    */   implements UIEditor.EditorComponent
/*    */ {
/* 43 */   public static final BuilderCodec<Timeline> CODEC = BuilderCodec.builder(Timeline.class, Timeline::new)
/* 44 */     .build();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadat\\ui\UIEditor$Timeline.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */