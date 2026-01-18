/*    */ package com.hypixel.hytale.builtin.buildertools;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BuilderToolsUserData
/*    */   implements Component<EntityStore> {
/*    */   public static final String ID = "BuilderTools";
/*    */   private static final String SELECTION_HISTORY_KEY = "SelectionHistory";
/*    */   private static final String SELECTION_HISTORY_DOC = "Controls whether changes to the block selection box are recorded in the undo/redo history.";
/* 20 */   public static final BuilderCodec<BuilderToolsUserData> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BuilderToolsUserData.class, BuilderToolsUserData::new)
/* 21 */     .append(new KeyedCodec("SelectionHistory", (Codec)Codec.BOOLEAN), BuilderToolsUserData::setRecordSelectionHistory, BuilderToolsUserData::isRecordingSelectionHistory)
/* 22 */     .addValidator(Validators.nonNull())
/* 23 */     .documentation("Controls whether changes to the block selection box are recorded in the undo/redo history.")
/* 24 */     .add())
/* 25 */     .build();
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolsUserData get(@Nonnull Player player) {
/* 29 */     BuilderToolsUserData userData = (BuilderToolsUserData)player.toHolder().getComponent(getComponentType());
/* 30 */     if (userData == null) return new BuilderToolsUserData(); 
/* 31 */     return userData;
/*    */   }
/*    */   
/*    */   public static ComponentType<EntityStore, BuilderToolsUserData> getComponentType() {
/* 35 */     return BuilderToolsPlugin.get().getUserDataComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean selectionHistory = true;
/*    */ 
/*    */   
/*    */   public boolean isRecordingSelectionHistory() {
/* 43 */     return this.selectionHistory;
/*    */   }
/*    */   
/*    */   public void setRecordSelectionHistory(boolean selectionHistory) {
/* 47 */     this.selectionHistory = selectionHistory;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 53 */     return "BuilderToolsUserData{selectionHistory=" + this.selectionHistory + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 60 */     if (this == o) return true; 
/* 61 */     if (o == null || getClass() != o.getClass()) return false; 
/* 62 */     BuilderToolsUserData that = (BuilderToolsUserData)o;
/* 63 */     return (this.selectionHistory == that.selectionHistory);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 68 */     return this.selectionHistory ? 1 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 74 */     BuilderToolsUserData settings = new BuilderToolsUserData();
/* 75 */     settings.selectionHistory = this.selectionHistory;
/* 76 */     return settings;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\BuilderToolsUserData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */