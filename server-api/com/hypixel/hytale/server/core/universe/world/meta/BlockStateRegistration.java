/*    */ package com.hypixel.hytale.server.core.universe.world.meta;
/*    */ 
/*    */ import com.hypixel.hytale.registry.Registration;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockStateRegistration
/*    */   extends Registration
/*    */ {
/*    */   private final Class<? extends BlockState> blockStateClass;
/*    */   
/*    */   public BlockStateRegistration(Class<? extends BlockState> blockStateClass, BooleanSupplier isEnabled, Runnable unregister) {
/* 17 */     super(isEnabled, unregister);
/* 18 */     this.blockStateClass = blockStateClass;
/*    */   }
/*    */   
/*    */   public BlockStateRegistration(@Nonnull BlockStateRegistration registration, BooleanSupplier isEnabled, Runnable unregister) {
/* 22 */     super(isEnabled, unregister);
/* 23 */     this.blockStateClass = registration.blockStateClass;
/*    */   }
/*    */   
/*    */   public Class<? extends BlockState> getBlockStateClass() {
/* 27 */     return this.blockStateClass;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 33 */     return "BlockStateRegistration{blockStateClass=" + String.valueOf(this.blockStateClass) + ", " + super
/*    */       
/* 35 */       .toString() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\BlockStateRegistration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */