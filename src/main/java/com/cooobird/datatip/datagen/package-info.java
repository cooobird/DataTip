/**
 * 数据生成构建器统一使用 xxxOptions() 创建链式选项。
 * <p>未设置的属性保留便捷工厂或 apply(source) 来源内容的值。<br>
 * 每次应用创建新内容；修改选项不会改变已经创建的内容。</p>
 * <p>选项字段使用底层内容类的名称，颜色同时接受整数与命名色或十六进制字符串。<br>
 * 枚举同时接受对应枚举值与不区分大小写的字符串，非法字符串会抛出异常。<br>
 * 可选字段用 clearXxx() 显式清除，避免重载调用 null 时出现歧义。<br>
 * 标签可用 String、Component 或 {@code Map<String, String>}，设置标签后用 showLabel(true) 控制显示。</p>
 *
 * <pre>{@code
 * progress(0.75f, 120, progressOptions().height(10).colorFg("green").showLabel(true).label("75%"));
 * item("minecraft:diamond", itemOptions().count(16).size(24).showDurability(false));
 * typewriter(typewriterOptions().italic(true).charsPerSecond(5).loop(true), "第一行", "第二行");
 * cycleText(translate("tip.example"), cycleTextOptions().colors("red", "gold").phase(0.25));
 * vbox(vboxOptions().gap(3).padding(2).align("center"), text("Title"), spacer(4));
 * modifiers(text("Limited"), modifierOptions().maxWidth(160).opacity(0.8).shift(true));
 * }</pre>
 *
 * <p>可用选项：Text、CycleText、Typewriter、Spacer、Divider、Item、Progress、VBox、HBox、Stack、
 * Carousel、Aligned、Entity、Block、Atlas、Image、Chart、ChartEntry、Modifier。</p>
 * <p>公共几何、条件、透明度和可见性设置通过 ModifierOptions 包装任意内容。<br>
 * TextOptions.maxWidth 是文本换行宽度，ModifierOptions.maxWidth 是节点布局约束。<br>
 * 底层内容的尺寸约束与动画样式规则仍然生效。</p>
 */
package com.cooobird.datatip.datagen;
