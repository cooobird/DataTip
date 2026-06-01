package com.cooobird.datatip.tooltip;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import com.cooobird.datatip.data.TooltipEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * 找出所有匹配当前物品的规则。
 */
public class TooltipMatcher {

    public static List<TooltipEntry> matchAll(ItemStack stack, List<TooltipEntry> entries) {
        var key = stack.getItemHolder().getKey();
        if (key == null) return List.of();
        ResourceLocation id = key.location();
        if (id == null) return List.of();
        String fullId = id.toString();

        List<TooltipEntry> result = new ArrayList<>();
        for (TooltipEntry e : entries) {
            if (!idMatches(e, fullId, id)) continue;
            if (!nbtMatches(e, stack)) continue;
            if (!checkConds(e.conditions())) continue;
            result.add(e);
        }
        return result;
    }

    private static boolean idMatches(TooltipEntry e, String fullId, ResourceLocation id) {
        return switch (e.matchType()) {
            case EXACT -> e.match().equals(fullId);
            case TAG -> BuiltInRegistries.ITEM.getHolder(id)
                .map(h -> h.is(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.parse(e.match()))))
                .orElse(false);
            case WILDCARD -> e.wildcardMatch(fullId, e.match());
        };
    }

    // NBT 匹配——直接从数据组件取值，不依赖序列化后的 NBT 结构
    private static boolean nbtMatches(TooltipEntry e, ItemStack stack) {
        if (e.nbt() == null) return true;
        for (var p : e.nbt().entrySet()) {
            Object want = p.getValue();
            if (want == null) continue;
            String wantStr = want.toString();

            String actual = switch (p.getKey()) {
                case "Damage" -> String.valueOf(stack.getDamageValue());
                case "Count" -> String.valueOf(stack.getCount());
                case "RepairCost" -> String.valueOf(
                    stack.getOrDefault(net.minecraft.core.component.DataComponents.REPAIR_COST, 0));
                default -> {
                    var cd = stack.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA,
                        net.minecraft.world.item.component.CustomData.EMPTY);
                    if (cd.isEmpty()) {
                        yield null;
                    }
                    var tag = cd.copyTag();
                    yield tag.contains(p.getKey()) ? tag.get(p.getKey()).getAsString() : null;
                }
            };
            if (actual == null || !actual.equals(wantStr)) return false;
        }
        return true;
    }

    private static boolean checkConds(List<TooltipEntry.Condition> conds) {
        if (conds.isEmpty()) return true;
        var p = Minecraft.getInstance().player;
        if (p == null || p.level() == null) return false;

        for (var c : conds) {
            Object raw = c.value();
            if (raw == null) continue;
            String val = raw.toString();

            switch (c.type()) {
                case "dimension" -> {
                    var dim = p.level().dimension();
                    if (dim == null || !dim.location().toString().equals(val)) return false;
                }
                case "biome" -> {
                    boolean ok = p.level().getBiome(p.blockPosition()).unwrapKey()
                        .map(k -> {
                            var loc = k.location();
                            if (loc == null) return false;
                            if (raw instanceof List<?> l) return l.contains(loc.toString());
                            return loc.toString().equals(val);
                        }).orElse(false);
                    if (!ok) return false;
                }
                case "holding" -> {
                    var mh = p.getMainHandItem();
                    var oh = p.getOffhandItem();
                    var mhKey = mh.isEmpty() ? null : mh.getItemHolder().getKey();
                    var ohKey = oh.isEmpty() ? null : oh.getItemHolder().getKey();
                    String mainId = mhKey != null ? mhKey.location().toString() : "";
                    String offId = ohKey != null ? ohKey.location().toString() : "";
                    if (!mainId.equals(val) && !offId.equals(val)) return false;
                }
                case "sneaking" -> {
                    if (!p.isShiftKeyDown()) return false;
                }
            }
        }
        return true;
    }
}
