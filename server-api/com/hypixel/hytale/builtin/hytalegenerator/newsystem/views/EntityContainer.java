package com.hypixel.hytale.builtin.hytalegenerator.newsystem.views;

import com.hypixel.hytale.builtin.hytalegenerator.props.entity.EntityPlacementData;
import javax.annotation.Nonnull;

public interface EntityContainer {
  void addEntity(@Nonnull EntityPlacementData paramEntityPlacementData);
  
  boolean isInsideBuffer(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\views\EntityContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */