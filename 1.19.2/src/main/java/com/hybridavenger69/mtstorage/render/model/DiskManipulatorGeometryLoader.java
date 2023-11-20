package com.hybridavenger69.mtstorage.render.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

public class DiskManipulatorGeometryLoader implements IGeometryLoader<DiskManipulatorUnbakedGeometry> {
    @Override
    public DiskManipulatorUnbakedGeometry read(final JsonObject jsonObject,
                                               final JsonDeserializationContext deserializationContext) {
        return new DiskManipulatorUnbakedGeometry();
    }
}
