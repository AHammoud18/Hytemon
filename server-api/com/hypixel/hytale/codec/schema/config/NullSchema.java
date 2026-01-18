/*   */ package com.hypixel.hytale.codec.schema.config;
/*   */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*   */ import java.util.function.Supplier;
/*   */ 
/*   */ public class NullSchema extends Schema {
/* 6 */   public static final BuilderCodec<NullSchema> CODEC = BuilderCodec.builder(NullSchema.class, NullSchema::new, Schema.BASE_CODEC)
/* 7 */     .build();
/* 8 */   public static final NullSchema INSTANCE = new NullSchema();
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\config\NullSchema.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */