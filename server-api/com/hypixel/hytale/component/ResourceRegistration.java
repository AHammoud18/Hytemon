/*    */ package com.hypixel.hytale.component;
/*    */ public final class ResourceRegistration<ECS_TYPE, T extends Resource<ECS_TYPE>> extends Record { @Nonnull
/*    */   private final Class<? super T> typeClass; @Nullable
/*    */   private final String id;
/*    */   @Nullable
/*    */   private final BuilderCodec<T> codec;
/*    */   @Nonnull
/*    */   private final Supplier<T> supplier;
/*    */   @Nonnull
/*    */   private final ResourceType<ECS_TYPE, T> resourceType;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/component/ResourceRegistration;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/component/ResourceRegistration;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/component/ResourceRegistration<TECS_TYPE;TT;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/component/ResourceRegistration;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/component/ResourceRegistration;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/component/ResourceRegistration<TECS_TYPE;TT;>;
/*    */   }
/*    */   
/* 20 */   public ResourceRegistration(@Nonnull Class<? super T> typeClass, @Nullable String id, @Nullable BuilderCodec<T> codec, @Nonnull Supplier<T> supplier, @Nonnull ResourceType<ECS_TYPE, T> resourceType) { this.typeClass = typeClass; this.id = id; this.codec = codec; this.supplier = supplier; this.resourceType = resourceType; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/component/ResourceRegistration;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/component/ResourceRegistration;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 20 */     //   0	8	0	this	Lcom/hypixel/hytale/component/ResourceRegistration<TECS_TYPE;TT;>; } @Nonnull public Class<? super T> typeClass() { return this.typeClass; } @Nullable public String id() { return this.id; } @Nullable public BuilderCodec<T> codec() { return this.codec; } @Nonnull public Supplier<T> supplier() { return this.supplier; } @Nonnull public ResourceType<ECS_TYPE, T> resourceType() { return this.resourceType; }
/*    */    }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\ResourceRegistration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */