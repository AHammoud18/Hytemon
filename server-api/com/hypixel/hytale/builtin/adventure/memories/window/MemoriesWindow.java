/*    */ package com.hypixel.hytale.builtin.adventure.memories.window;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.memories.component.PlayerMemories;
/*    */ import com.hypixel.hytale.builtin.adventure.memories.memories.Memory;
/*    */ import com.hypixel.hytale.codec.EmptyExtraInfo;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MemoriesWindow extends Window {
/* 18 */   private final JsonObject windowData = new JsonObject();
/*    */   
/*    */   public MemoriesWindow() {
/* 21 */     super(WindowType.Memories);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public JsonObject getData() {
/* 26 */     return this.windowData;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onOpen0() {
/* 31 */     JsonArray array = new JsonArray();
/* 32 */     Ref<EntityStore> ref = getPlayerRef().getReference();
/* 33 */     PlayerMemories playerMemories = (PlayerMemories)ref.getStore().getComponent(ref, PlayerMemories.getComponentType());
/* 34 */     if (playerMemories != null) {
/* 35 */       this.windowData.addProperty("capacity", Integer.valueOf(playerMemories.getMemoriesCapacity()));
/* 36 */       for (Memory memory : playerMemories.getRecordedMemories()) {
/* 37 */         JsonObject obj = new JsonObject();
/* 38 */         obj.addProperty("title", memory.getTitle());
/* 39 */         obj.add("tooltipText", BsonUtil.translateBsonToJson(Message.CODEC.encode(memory.getTooltipText(), (ExtraInfo)EmptyExtraInfo.EMPTY).asDocument()));
/* 40 */         String iconPath = memory.getIconPath();
/* 41 */         if (iconPath != null && !iconPath.isEmpty()) obj.addProperty("icon", iconPath);
/*    */         
/* 43 */         String category = GetCategoryIconPathForMemory(memory);
/* 44 */         if (category != null) {
/* 45 */           obj.addProperty("categoryIcon", category);
/*    */         }
/*    */         
/* 48 */         array.add((JsonElement)obj);
/*    */       } 
/*    */     } else {
/* 51 */       this.windowData.addProperty("capacity", Integer.valueOf(0));
/*    */     } 
/* 53 */     this.windowData.add("memories", (JsonElement)array);
/* 54 */     invalidate();
/* 55 */     return true;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static String GetCategoryIconPathForMemory(@Nonnull Memory memory) {
/* 60 */     Map<String, Set<Memory>> allMemories = MemoriesPlugin.get().getAllMemories();
/*    */     
/* 62 */     for (Map.Entry<String, Set<Memory>> entry : allMemories.entrySet()) {
/* 63 */       if (!((Set)entry.getValue()).contains(memory)) {
/*    */         continue;
/*    */       }
/* 66 */       String memoryCategoryIconBasePath = "UI/Custom/Pages/Memories/categories/%s.png";
/* 67 */       return String.format("UI/Custom/Pages/Memories/categories/%s.png", new Object[] { entry.getKey() });
/*    */     } 
/*    */     
/* 70 */     return null;
/*    */   }
/*    */   
/*    */   public void onClose0() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\window\MemoriesWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */