package com.cooobird.datatip.event;

import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.TipContentEntry;
import com.cooobird.datatip.api.content.CarouselContent;
import com.cooobird.datatip.api.content.ContainerContent;
import com.cooobird.datatip.api.node.TipNode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.List;

/**
 * 选择合并后的 Shift 提示，将提示规则与渲染组件创建分离。
 */
record ShiftHintSelection(boolean visible, boolean prepend, @Nullable TipContent custom) {
    static ShiftHintSelection select(List<TipContentEntry> entries, boolean shiftDown) {
        if (shiftDown) return new ShiftHintSelection(false, false, null);
        boolean visible = false;
        boolean prepend = false;
        TipContent custom = null;
        for (TipContentEntry entry : entries) {
            if (!entry.showShiftHint()) continue;
            // 保留现有优先级：采用参与提示的条目中第一个自定义提示。
            if (custom == null) custom = entry.shiftHint();
            if (entry.shift() || containsShiftNode(entry.content())) {
                visible = true;
                prepend |= entry.prepend();
            }
        }
        return new ShiftHintSelection(visible, prepend, visible ? custom : null);
    }

    int insertionIndex(int elementCount) {
        return prepend ? Math.min(1, elementCount) : elementCount;
    }

    private static boolean containsShiftNode(TipContent content) {
        ArrayDeque<TipContent> work = new ArrayDeque<>();
        work.push(content);
        while (!work.isEmpty()) {
            TipContent current = work.pop();
            if (current instanceof TipNode node) {
                if (node.modifiers().shift()) return true;
                work.push(node.inner());
                continue;
            }
            if (current instanceof ContainerContent container) {
                for (TipContent child : container.children()) {
                    work.push(child);
                }
                continue;
            }
            if (current instanceof CarouselContent carousel) {
                for (TipContent frame : carousel.getFrames()) {
                    work.push(frame);
                }
            }
        }
        return false;
    }

}
