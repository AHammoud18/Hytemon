/*    */ package com.hypixel.hytale.builtin.asseteditor.data;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.JsonUpdateCommand;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ 
/*    */ public class AssetUndoRedoInfo
/*    */ {
/*  9 */   public final Deque<JsonUpdateCommand> undoStack = new ArrayDeque<>();
/* 10 */   public final Deque<JsonUpdateCommand> redoStack = new ArrayDeque<>();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\data\AssetUndoRedoInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */