package com.hypixel.hytale.builtin.portals.utils.spatial;

import java.util.List;

interface CellVisitor<T> {
  boolean visit(List<SpatialHashGrid.Entry<T>> paramList);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\spatial\SpatialHashGrid$CellVisitor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */