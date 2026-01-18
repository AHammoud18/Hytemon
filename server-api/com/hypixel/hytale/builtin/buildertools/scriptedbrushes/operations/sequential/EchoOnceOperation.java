/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EchoOnceOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<EchoOnceOperation> CODEC;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EchoOnceOperation.class, EchoOnceOperation::new).append(new KeyedCodec("Message", (Codec)Codec.STRING), (op, val) -> op.messageArg = val, op -> op.messageArg).documentation("A message to print to chat when this operation is first executed").add()).documentation("Print text to chat only on the first execution after brush load")).build();
/*    */   }
/* 33 */   private String messageArg = "Default message";
/*    */   private boolean hasBeenExecuted = false;
/*    */   
/*    */   public EchoOnceOperation() {
/* 37 */     super("Echo Once to Chat", "Print text to chat only on the first execution after brush load", false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetInternalState() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 49 */     if (!this.hasBeenExecuted) {
/* 50 */       PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 51 */       assert playerRefComponent != null;
/* 52 */       playerRefComponent.sendMessage(Message.raw(this.messageArg));
/* 53 */       this.hasBeenExecuted = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\EchoOnceOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */