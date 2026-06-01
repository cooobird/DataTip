package com.cooobird.datatip.datagen;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 用 {@link TooltipBuilder} 生成所有功能组合的示例 datatip.json。
 * 运行 gradle runData 生成到 src/generated/resources/assets/minecraft/datatip/datatip.json
 */
public class ExampleTooltipProvider implements DataProvider {
    private final PackOutput output;

    public ExampleTooltipProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Path path = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
            .resolve("minecraft/datatip/datatip.json");

        Map<String, Object> map = new LinkedHashMap<>();
        var b = TooltipBuilder.create();

        map.put("minecraft:diamond", List.of("A shiny diamond", "Worth a fortune"));

        put(map, b.key("minecraft:netherite_ingot").color("gold")
            .line("zh_cn", "下界合金锭")
            .line("zh_cn", TooltipBuilder.TooltipLine.of("不会被熔岩烧毁").color("dark_red").bold())
            .line("en_us", "Netherite Ingot")
            .line("en_us", TooltipBuilder.TooltipLine.of("Immune to lava").color("dark_red").bold())
            .build());

        put(map, b.key("minecraft:diamond_sword").shift().prepend()
            .line("zh_cn", TooltipBuilder.TooltipLine.of("削铁如泥").color("aqua"))
            .line("en_us", TooltipBuilder.TooltipLine.of("Cuts through iron like butter").color("aqua"))
            .build());

        put(map, b.key("minecraft:diamond_pickaxe")
            .line("zh_cn", "耐久: {durability} / {max_durability}")
            .line("en_us", "Durability: {durability} / {max_durability}")
            .build());

        put(map, b.key("minecraft:enchanted_golden_apple").color("light_purple").shift()
            .line("zh_cn",
                TooltipBuilder.TooltipLine.of("稀有食物").color("gold").bold(),
                TooltipBuilder.TooltipLine.of("生命恢复 IV").color("red").italic(),
                TooltipBuilder.TooltipLine.of("伤害吸收 IV").color("aqua").italic())
            .line("en_us",
                TooltipBuilder.TooltipLine.of("Rare food").color("gold").bold(),
                TooltipBuilder.TooltipLine.of("Regeneration IV").color("red").italic(),
                TooltipBuilder.TooltipLine.of("Absorption IV").color("aqua").italic())
            .build());

        put(map, b.key("minecraft:nether_star").color("light_purple")
            .line("zh_cn",
                TooltipBuilder.TooltipLine.of("Boss 掉落").color("#FFD700").bold(),
                TooltipBuilder.TooltipLine.of("像素风标题").font("minecraft:alt"))
            .line("en_us",
                TooltipBuilder.TooltipLine.of("Boss drop").color("#FFD700").bold(),
                TooltipBuilder.TooltipLine.of("Pixel title").font("minecraft:alt"))
            .build());

        put(map, b.key("minecraft:stone")
            .line("zh_cn", TooltipBuilder.TooltipLine.of("这是删除线").strike())
            .line("en_us", TooltipBuilder.TooltipLine.of("Strikethrough text").strike())
            .build());

        put(map, b.key("#minecraft:pickaxes").color("yellow")
            .line("zh_cn", "所有镐子都显示这句话")
            .line("en_us", "Common pickaxe info")
            .build());

        put(map, b.key("minecraft:diamond_block").color("dark_red")
            .condition("dimension", "minecraft:the_nether")
            .line("zh_cn", "只在下界显示")
            .line("en_us", "Only visible in the Nether")
            .build());

        put(map, b.key("minecraft:bow").nbt("Damage", "0")
            .line("zh_cn", TooltipBuilder.TooltipLine.of("满耐久才显示").underline())
            .line("en_us", TooltipBuilder.TooltipLine.of("Full durability only").underline())
            .build());

        put(map, b.key("staticlogistics:*").color("light_purple")
            .line("zh_cn", "静态物流物品")
            .line("en_us", "Static Logistics item")
            .build());

        put(map, b.key("minecraft:totem_of_undying").color("gold").shift().prepend()
            .condition("sneaking", true)
            .line("zh_cn",
                TooltipBuilder.TooltipLine.of("不死图腾").color("#FFD700").bold().underline(),
                TooltipBuilder.TooltipLine.of("手持时免疫致命伤害").color("red").italic())
            .line("en_us",
                TooltipBuilder.TooltipLine.of("Totem of Undying").color("#FFD700").bold().underline(),
                TooltipBuilder.TooltipLine.of("Prevents fatal damage when held").color("red").italic())
            .build());

        return DataProvider.saveStable(cache,
            JsonParser.parseString(new GsonBuilder().setPrettyPrinting().create().toJson(map)), path);
    }

    @Override
    public String getName() {
        return "Example Tooltips";
    }

    private static void put(Map<String, Object> map, Map.Entry<String, Object> entry) {
        map.put(entry.getKey(), entry.getValue());
    }
}
