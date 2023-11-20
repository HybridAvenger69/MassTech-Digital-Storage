package com.hybridavenger69.mtstorage.apiimpl.network.security;

import com.hybridavenger69.mtstorage.api.network.security.ISecurityCard;
import com.hybridavenger69.mtstorage.api.network.security.Permission;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class SecurityCard implements ISecurityCard {
    private final UUID owner;
    private final Map<Permission, Boolean> permissions = new EnumMap<>(Permission.class);

    public SecurityCard(@Nullable UUID owner) {
        this.owner = owner;
    }

    public Map<Permission, Boolean> getPermissions() {
        return permissions;
    }

    @Override
    @Nullable
    public UUID getOwner() {
        return owner;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return permissions.get(permission);
    }
}
