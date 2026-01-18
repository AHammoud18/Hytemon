/*    */ package com.hypixel.hytale.server.core.modules.entityui;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entityui.asset.EntityUIComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIComponentList
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<UIComponentList> CODEC;
/*    */   protected String[] components;
/*    */   protected int[] componentIds;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UIComponentList.class, UIComponentList::new).append(new KeyedCodec("Components", (Codec)Codec.STRING_ARRAY), (list, v) -> list.components = v, list -> list.components).add()).afterDecode(list -> { list.componentIds = ArrayUtil.EMPTY_INT_ARRAY; list.update(); })).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, UIComponentList> getComponentType() {
/* 33 */     return EntityUIModule.get().getUIComponentListType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UIComponentList() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public UIComponentList(@Nonnull UIComponentList other) {
/* 43 */     this.componentIds = other.componentIds;
/* 44 */     this.components = other.components;
/*    */   }
/*    */   
/*    */   public void update() {
/* 48 */     IndexedLookupTableAssetMap<String, EntityUIComponent> assetMap = EntityUIComponent.getAssetMap();
/*    */     
/* 50 */     int assetCount = assetMap.getNextIndex();
/* 51 */     int oldLength = this.componentIds.length;
/* 52 */     if (oldLength <= assetCount) {
/* 53 */       this.componentIds = Arrays.copyOf(this.componentIds, assetCount);
/*    */       
/* 55 */       for (int index = oldLength; index < assetCount; index++) {
/* 56 */         this.componentIds[index] = index;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public int[] getComponentIds() {
/* 62 */     return this.componentIds;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 68 */     return new UIComponentList(this);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\UIComponentList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */