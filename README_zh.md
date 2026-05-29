# DataTip

JSON 驱动的自定义物品 tooltip。在资源包的 `assets/<模组id>/datatip/datatip.json` 里定义。

## 快速开始

```json
{
  "minecraft:diamond": [
    "一颗闪闪发光的钻石",
    "据说很值钱"
  ]
}
```

放到资源包的 `assets/minecraft/datatip/datatip.json`，悬停钻石。成了。

## 条目字段

条目是物品键下面的值。可以是简单的字符串数组，也可以是带选项的对象。

```json
{
  "minecraft:diamond": ["第一行", "第二行"],

  "minecraft:diamond_sword": {
    "text": {
      "zh_cn": ["削铁如泥"],
      "en_us": ["Cuts through iron like butter"]
    },
    "color": "gold",
    "shift": true,
    "prepend": true
  }
}
```

| 字段           | 类型     | 默认值   | 说明                                                                                                                                              |
|--------------|--------|-------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| `text`       | object | —     | 语言代码 → 行数组。单行不用套数组，直接写 `"text": {"zh_cn": "一行"}`。语言代码跟 Minecraft 一致：`zh_cn`、`en_us`、`ja_jp`、`ko_kr`、`ru_ru` 等。当前语言找不到时，自动 fallback 到第一个写了内容的语言。 |
| `color`      | string | gray  | 所有行的默认颜色。支持十六进制（`"#FF6600"`）和下方列出的 16 种命名色。                                                                                                     |
| `shift`      | bool   | false | `true` 时，tooltip 内容隐藏，显示"按住 Shift 查看详情"。按住 Shift 才展开。适合不想在默认视图占太多空间的长描述。                                                                        |
| `prepend`    | bool   | false | `true` 时，自定义行插在物品名字后面、原版 tooltip（附魔、属性等）前面。`false` 时追加在 tooltip 最末尾。                                                                            |
| `conditions` | object | —     | 一组必须全部满足的条件，不满足就不显示此条 tooltip。详见下方条件说明。                                                                                                         |
| `nbt`        | object | —     | 设置了之后，只有 NBT 数据匹配的物品才显示。值按字符串比较。例如 `{"Damage": "0"}` 只匹配满耐久的工具。                                                                                 |

### 颜色值

十六进制：`"#FF6600"`、`"#AABBCC"`、`"#00FF00"`——任意 6 位十六进制加 `#` 前缀。

命名色（Minecraft 标准 16 色）：

| 颜色                        | 代码 | 中文 |
|---------------------------|----|----|
| `black`                   | 黑色 |    |
| `dark_blue`               | 深蓝 |    |
| `dark_green`              | 深绿 |    |
| `dark_aqua`               | 深青 |    |
| `dark_red`                | 深红 |    |
| `dark_purple`             | 深紫 |    |
| `gold`                    | 金色 |    |
| `gray` / `grey`           | 灰色 |    |
| `dark_gray` / `dark_grey` | 深灰 |    |
| `blue`                    | 蓝色 |    |
| `green`                   | 绿色 |    |
| `aqua`                    | 青色 |    |
| `red`                     | 红色 |    |
| `light_purple`            | 浅紫 |    |
| `yellow`                  | 黄色 |    |
| `white`                   | 白色 |    |

### 条件

对象里的所有条件必须**同时**满足，tooltip 才会显示。

| 类型          | 值                                      | 说明                  |
|-------------|----------------------------------------|---------------------|
| `dimension` | 维度 ID，如 `"minecraft:the_nether"`       | 玩家在此维度时才显示。         |
| `biome`     | 生物群系 ID，如 `"minecraft:plains"`；或 ID 数组 | 玩家在任一指定生物群系时才显示。    |
| `holding`   | 物品 ID，如 `"minecraft:diamond_pickaxe"`  | 玩家主手或副手拿着该物品时才显示。   |
| `sneaking`  | `true`                                 | 玩家潜行（按住 Shift）时才显示。 |

## 单行样式

`text` 里的每一行可以是纯字符串，也可以是带样式的对象：

```json
{"text": "金色加粗", "color": "gold", "bold": true}
{"text": "蓝色斜体", "color": "blue", "italic": true}
{"text": "带下划线", "underlined": true}
{"text": "删除线", "strikethrough": true}
{"text": "像素字体", "font": "minecraft:alt"}
```

行不写 `color` 时继承条目级的 `color`，都没写时默认灰色。

| 字段              | 类型     | 说明                                                                                               |
|-----------------|--------|--------------------------------------------------------------------------------------------------|
| `text`          | string | 显示的文字。必填。                                                                                        |
| `color`         | string | 覆盖条目级的颜色。十六进制或命名色。                                                                               |
| `bold`          | bool   | 粗体                                                                                               |
| `italic`        | bool   | 斜体                                                                                               |
| `underlined`    | bool   | 下划线                                                                                              |
| `strikethrough` | bool   | 删除线                                                                                              |
| `font`          | string | 资源包中的字体。内置可选：`minecraft:default`（默认）、`minecraft:alt`（附魔台符文）、`minecraft:uniform`（等宽）。自定义资源包可添加更多。 |

## 变量

渲染时自动替换为当前值。只在可损坏或有堆叠的物品上有实际意义。

| 变量                 | 替换为            |
|--------------------|----------------|
| `{durability}`     | 剩余耐久（最大 - 已损耗） |
| `{max_durability}` | 最大耐久           |
| `{count}`          | 堆叠数量           |

## 匹配方式

多条规则可同时命中同一个物品——全部叠加显示。

| 方式  | 键示例                             | 匹配范围                                                  |
|-----|---------------------------------|-------------------------------------------------------|
| 精确  | `"minecraft:diamond"`           | 仅该物品                                                  |
| 标签  | `"#minecraft:swords"`           | 所有带该标签的物品。`#` 前缀会被去掉，剩下的作为标签 ID 查找。                   |
| 通配符 | `"staticlogistics:*"`           | `*` 匹配任意字符串，`?` 匹配单个字符。如 `"minecraft:*_sword"` 匹配所有剑。 |
| NBT | 精确匹配 + `"nbt": {"Damage": "0"}` | 物品 ID 和 NBT 键值都匹配时才显示。                                |
| 条件  | 精确匹配 + `"conditions": {...}`    | 物品 ID 匹配且所有条件都满足时才显示。                                 |

## 配置

- 文件：`config/datatip.toml`
- `enabled`：设为 `false` 关闭所有 DataTip 的 tooltip
- 修改 JSON 后 `/reload` 或 F3+T 生效

## 完整示例

```json
{
  // 最简单：纯字符串数组
  "minecraft:diamond": [
    "一颗闪闪发光的钻石",
    "据说很值钱"
  ],

  // 多语言 + 单行样式
  "minecraft:netherite_ingot": {
    "text": {
      "zh_cn": [
        "下界合金锭",
        {"text": "不会被熔岩烧毁", "color": "dark_red", "bold": true}
      ],
      "en_us": [
        "Netherite Ingot",
        {"text": "Immune to lava", "color": "dark_red", "bold": true}
      ]
    },
    "color": "gold"
  },

  // shift + prepend：按 Shift 才展开，放在物品名后面
  "minecraft:diamond_sword": {
    "text": {
      "zh_cn": [{"text": "削铁如泥", "color": "aqua"}],
      "en_us": [{"text": "Cuts through iron like butter", "color": "aqua"}]
    },
    "shift": true,
    "prepend": true
  },

  // 变量：{durability} {max_durability} {count}
  "minecraft:diamond_pickaxe": {
    "text": {
      "zh_cn": ["耐久: {durability} / {max_durability}"],
      "en_us": ["Durability: {durability} / {max_durability}"]
    }
  },

  // 粗体、斜体、shift — 多个条目可叠加
  "minecraft:enchanted_golden_apple": {
    "text": {
      "zh_cn": [
        {"text": "稀有食物", "color": "gold", "bold": true},
        {"text": "生命恢复 IV", "color": "red", "italic": true},
        {"text": "伤害吸收 IV", "color": "aqua", "italic": true}
      ],
      "en_us": [
        {"text": "Rare food", "color": "gold", "bold": true},
        {"text": "Regeneration IV", "color": "red", "italic": true},
        {"text": "Absorption IV", "color": "aqua", "italic": true}
      ]
    },
    "color": "light_purple",
    "shift": true
  },

  // 粗体 + 自定义字体
  "minecraft:nether_star": {
    "text": {
      "zh_cn": [
        {"text": "Boss 掉落", "bold": true},
        {"text": "像素风标题", "font": "minecraft:alt"}
      ],
      "en_us": [
        {"text": "Boss drop", "bold": true},
        {"text": "Pixel title", "font": "minecraft:alt"}
      ]
    },
    "color": "light_purple"
  },

  // 删除线
  "minecraft:stone": {
    "text": {
      "zh_cn": [{"text": "这是删除线", "strikethrough": true}],
      "en_us": [{"text": "Strikethrough text", "strikethrough": true}]
    }
  },

  // 标签匹配 — 所有带 #minecraft:pickaxes 标签的物品
  "#minecraft:pickaxes": {
    "text": {
      "zh_cn": ["所有镐子都显示这句话"],
      "en_us": ["Common pickaxe info"]
    },
    "color": "yellow"
  },

  // 条件：只在下界显示
  "minecraft:diamond_block": {
    "text": {
      "zh_cn": ["只在下界显示"],
      "en_us": ["Only visible in the Nether"]
    },
    "color": "dark_red",
    "conditions": { "dimension": "minecraft:the_nether" }
  },

  // NBT 匹配：只匹配满耐久的（Damage = 0）
  "minecraft:bow": {
    "text": {
      "zh_cn": [{"text": "满耐久才显示这句话", "underlined": true}],
      "en_us": [{"text": "Full durability only", "underlined": true}]
    },
    "nbt": { "Damage": "0" }
  },

  // 通配符匹配 — 某个命名空间下的所有物品
  "staticlogistics:*": {
    "text": {
      "zh_cn": ["静态物流物品"],
      "en_us": ["Static Logistics item"]
    },
    "color": "light_purple"
  }
}
```
