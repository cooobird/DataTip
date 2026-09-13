package com.cooobird.datatip.event;

import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.TipContentEntry;
import com.cooobird.datatip.api.TipEventManager;
import com.cooobird.datatip.api.component.ScrollHintTooltipComponent;
import com.cooobird.datatip.api.component.TipContentTooltipComponent;
import com.cooobird.datatip.api.component.TooltipViewportBudget;

import com.cooobird.datatip.api.content.VBoxContent;

import com.cooobird.datatip.api.session.TooltipHit;
import com.cooobird.datatip.config.DatatipConfig;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Tooltip 元素插入工具。
 */
final class TipTooltipElements {
    private static final Object PREPEND_SCROLL_KEY = new Object();
    private static final Object NORMAL_SCROLL_KEY = new Object();

    private TipTooltipElements() {
    }

    @Nullable
    static TooltipViewportBudget insertContentEntries(
        RenderTooltipEvent.GatherComponents event,
        List<TipContentEntry> entries,
        TooltipHit hit,
        boolean shiftDown
    ) {
        List<TipContent> prependContents = new ArrayList<>();
        List<TipContent> normalContents = new ArrayList<>();
        collectVisibleContents(entries, shiftDown, prependContents, normalContents);
        // Gather 阶段原版尚未换行，先按原版背景外扩保留边界；
        // 最终预算会在 Pre 阶段按真实组件高度覆盖。
        TooltipViewportBudget budget = TooltipViewportBudget.forScreen(
            event.getScreenWidth(),
            event.getScreenHeight()
        );
        insertPrependContents(event, prependContents, hit, budget);
        appendNormalContents(event, normalContents, hit, budget);
        return prependContents.isEmpty() && normalContents.isEmpty()
            ? null
            : budget;
    }

    static void appendShiftHint(
        RenderTooltipEvent.GatherComponents event,
        List<TipContentEntry> entries,
        boolean shiftDown,
        KeyMapping showTipKey,
        TooltipHit hit
    ) {
        ShiftHintSelection selection = ShiftHintSelection.select(entries, shiftDown);
        if (selection.visible()) {
            addShiftHint(event, selection.custom(), showTipKey, hit,
                selection.insertionIndex(event.getTooltipElements().size()));
        }
    }

    private static void addShiftHint(
        RenderTooltipEvent.GatherComponents event,
        @Nullable TipContent custom,
        KeyMapping showTipKey,
        TooltipHit hit,
        int insertionIndex
    ) {

        if (custom != null) {
            event.getTooltipElements().add(insertionIndex, Either.right(
                new TipContentTooltipComponent(
                    custom,
                    hit.stackSnapshot(),
                    hit
                )
            ));
            return;
        }
        Component hint = Component.translatable(
            "tooltip.datatip.hold_shift",
            showTipKey.getTranslatedKeyMessage()
        ).withStyle(style -> style
            .withColor(DatatipConfig.shiftHintColor())
            .withItalic(true));
        event.getTooltipElements().add(insertionIndex, Either.left(hint));
    }

    private static void collectVisibleContents(
        List<TipContentEntry> entries,
        boolean shiftDown,
        List<TipContent> prependContents,
        List<TipContent> normalContents
    ) {
        for (TipContentEntry entry : entries) {
            if (entry.shift() && !shiftDown) continue;

            if (entry.prepend()) {
                prependContents.add(entry.content());
            } else {
                normalContents.add(entry.content());
            }
        }
    }

    private static void insertPrependContents(
        RenderTooltipEvent.GatherComponents event,
        List<TipContent> prependContents,
        TooltipHit hit,
        TooltipViewportBudget budget
    ) {
        if (prependContents.isEmpty()) return;
        TipContent content = prependContents.size() == 1
            ? prependContents.getFirst()
            : new VBoxContent(
            prependContents,
            0,
            0,
            VBoxContent.HorizontalAlign.LEFT
        );
        event.getTooltipElements().add(
            Math.min(1, event.getTooltipElements().size()),
            Either.right(prepareComponent(
                content,
                hit,
                budget,
                PREPEND_SCROLL_KEY
            ))
        );
    }

    private static void appendNormalContents(
        RenderTooltipEvent.GatherComponents event,
        List<TipContent> normalContents,
        TooltipHit hit,
        TooltipViewportBudget budget
    ) {
        if (normalContents.isEmpty()) return;
        TipContent content = normalContents.size() == 1
            ? normalContents.getFirst()
            : new VBoxContent(
            normalContents,
            0,
            0,
            VBoxContent.HorizontalAlign.LEFT
        );
        event.getTooltipElements().add(
            Either.right(prepareComponent(
                content,
                hit,
                budget,
                NORMAL_SCROLL_KEY
            ))
        );
    }

    private static TipContentTooltipComponent prepareComponent(
        TipContent content,
        TooltipHit hit,
        TooltipViewportBudget budget,
        Object scrollKey
    ) {
        return new TipContentTooltipComponent(
            content,
            hit.stackSnapshot(),
            hit,
            budget,
            scrollKey
        );
    }

    static List<Component> collectExtraLines(ItemStack stack) {
        TipEventManager.AppendLinesEvent appendEvent = TipEventManager.fireAppendLines(stack);
        ArrayList<Component> result = new ArrayList<>();
        for (String line : appendEvent.getLines()) {
            result.add(Component.literal(line));
        }
        return List.copyOf(result);
    }

    static void appendExtraLines(
        RenderTooltipEvent.GatherComponents event,
        List<Component> lines
    ) {
        for (Component line : lines) {
            event.getTooltipElements().add(Either.left(line));
        }
    }

    static void appendScrollHint(
        RenderTooltipEvent.GatherComponents event,
        @Nullable TooltipViewportBudget budget,
        KeyMapping scrollKey,
        List<TipContentEntry> entries,
        TooltipHit hit
    ) {
        if (budget == null) return;
        TipContent custom = firstScrollHint(entries);
        event.getTooltipElements().add(
            Either.right(custom != null
                ? new ScrollHintTooltipComponent(budget, custom, hit)
                : new ScrollHintTooltipComponent(
                budget,
                scrollKey.getTranslatedKeyMessage()
            ))
        );
    }

    @Nullable
    private static TipContent firstScrollHint(List<TipContentEntry> entries) {
        for (TipContentEntry entry : entries) {
            TipContent hint = entry.scrollHint();
            if (hint != null) return hint;
        }
        return null;
    }

}
