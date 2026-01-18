/*    */ package com.hypixel.hytale.builtin.portals.components;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.utils.BlockTypeUtils;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class PortalDeviceConfig
/*    */ {
/*    */   public static final BuilderCodec<PortalDeviceConfig> CODEC;
/*    */   
/*    */   static {
/* 47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PortalDeviceConfig.class, PortalDeviceConfig::new).appendInherited(new KeyedCodec("SpawningState", (Codec)Codec.STRING), (config, o) -> config.spawningState = o, config -> config.spawningState, (config, parent) -> config.spawningState = parent.spawningState).documentation("The StateData for the short transition from off to on, when the instance is being created").add()).appendInherited(new KeyedCodec("OnState", (Codec)Codec.STRING), (config, o) -> config.onState = o, config -> config.onState, (config, parent) -> config.onState = parent.onState).documentation("The StateData when the portal is summoned and active.").add()).appendInherited(new KeyedCodec("OffState", (Codec)Codec.STRING), (config, o) -> config.offState = o, config -> config.offState, (config, parent) -> config.offState = parent.offState).documentation("The StateData when there is no portal and the device is inactive.").add()).appendInherited(new KeyedCodec("ReturnBlockType", (Codec)Codec.STRING), (config, o) -> config.returnBlock = o, config -> config.returnBlock, (config, parent) -> config.returnBlock = parent.returnBlock).documentation("This block type will be placed (once) on the spawn point of the portal world.").add()).build();
/*    */   }
/* 49 */   private String onState = "Active";
/* 50 */   private String spawningState = "Spawning";
/* 51 */   private String offState = "default";
/*    */   
/*    */   private String returnBlock;
/*    */   
/*    */   public String getOnState() {
/* 56 */     return this.onState;
/*    */   }
/*    */   
/*    */   public String getSpawningState() {
/* 60 */     return this.spawningState;
/*    */   }
/*    */   
/*    */   public String getOffState() {
/* 64 */     return this.offState;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getReturnBlock() {
/* 69 */     return this.returnBlock;
/*    */   }
/*    */   
/*    */   public String[] getBlockStates() {
/* 73 */     return new String[] { this.onState, this.spawningState, this.offState };
/*    */   }
/*    */   
/*    */   public boolean areBlockStatesValid(BlockType baseBlockType) {
/* 77 */     for (String stateKey : getBlockStates()) {
/* 78 */       BlockType state = BlockTypeUtils.getBlockForState(baseBlockType, stateKey);
/* 79 */       if (state == null) {
/* 80 */         return false;
/*    */       }
/*    */     } 
/* 83 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\components\PortalDeviceConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */