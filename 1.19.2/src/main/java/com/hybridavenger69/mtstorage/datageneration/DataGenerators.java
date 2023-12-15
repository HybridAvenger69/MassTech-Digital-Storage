package com.hybridavenger69.mtstorage.datageneration;

import com.hybridavenger69.mtstorage.MS;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DataGenerators {
    @SubscribeEvent
    public void runDataGeneration(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeClient(), new BlockModelGenerator(event.getGenerator(), MS.ID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeServer(), new RecipeGenerator(event.getGenerator()));
        BlockTagGenerator blockTagGenerator = new BlockTagGenerator(
            event.getGenerator(),
            MS.ID,
            event.getExistingFileHelper());
        event.getGenerator().addProvider(event.includeServer(), blockTagGenerator);
        event.getGenerator().addProvider(event.includeServer(), new ItemTagGenerator(
            event.getGenerator(),
            blockTagGenerator,
            MS.ID,
            event.getExistingFileHelper())
        );
        event.getGenerator().addProvider(event.includeServer(), new BlockEntityTagGenerator(
            event.getGenerator(),
            MS.ID,
            event.getExistingFileHelper()
        ));
        event.getGenerator().addProvider(event.includeServer(), new LootTableGenerator(event.getGenerator()));
    }
}
