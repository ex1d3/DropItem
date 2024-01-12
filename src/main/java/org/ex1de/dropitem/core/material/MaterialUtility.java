package org.ex1de.dropitem.core.material;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public final class MaterialUtility {
    public static List<String> filterMaterialsByName(
            Material[] materials,
            String materialNameToFind
    ) {
        return Arrays.stream(materials)
                .map(Material::name)
                .filter(materialName ->
                        materialName.startsWith(materialNameToFind)
                ).toList();
    }
}
