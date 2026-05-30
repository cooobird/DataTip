# DataTip

JSON-driven custom item tooltips. Define tooltips in resource packs at `assets/<modid>/datatip/datatip.json`.

## Quick Start

```json
{
  "minecraft:diamond": [
    "A shiny diamond",
    "Worth a fortune"
  ]
}
```

Put this in a resource pack at `assets/minecraft/datatip/datatip.json`, then hover a diamond in-game. That's it.

## Entry Fields

An entry is the value under an item key. It can be a simple array of strings, or an object with options.

```json
{
  "minecraft:diamond": ["Line 1", "Line 2"],

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

| Field        | Type   | Default | Description                                                                                                                                                                                                                                                                     |
|--------------|--------|---------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `text`       | object | —       | Maps language codes to line arrays. Single-line values can be a plain string instead of `["..."]`. Language codes follow Minecraft locales: `zh_cn`, `en_us`, `ja_jp`, `ko_kr`, `ru_ru`, etc. If the current language is not found, falls back to the first available language. |
| `color`      | string | gray    | Default text color for all lines when not overridden per-line. Supports hex (`"#FF6600"`) and the 16 named Minecraft colors listed below.                                                                                                                                       |
| `shift`      | bool   | false   | When `true`, the tooltip lines are hidden behind a "Hold key" hint. The content only appears when the player holds the configured key (default: Left Shift, rebindable in Controls → DataTip). Useful for long descriptions that shouldn't clutter the default view.            |
| `prepend`    | bool   | false   | When `true`, custom lines are inserted right after the item name (before enchantments, attributes, etc.) instead of being appended at the very end of the tooltip.                                                                                                              |
| `conditions` | object | —       | A set of requirements that must all be met for the tooltip to appear. See the Conditions section below.                                                                                                                                                                         |
| `nbt`        | object | —       | If set, only items whose NBT data matches these key-value pairs will show the tooltip. Values are compared as strings. Example: `{"Damage": "0"}` matches an undamaged tool.                                                                                                    |

### Color Values

Hex colors: `"#FF6600"`, `"#AABBCC"`, `"#00FF00"` — any 6-digit hex with `#` prefix.

Named colors (standard Minecraft formatting codes):

| Color                     | Name         |
|---------------------------|--------------|
| `black`                   | Black        |
| `dark_blue`               | Dark Blue    |
| `dark_green`              | Dark Green   |
| `dark_aqua`               | Dark Aqua    |
| `dark_red`                | Dark Red     |
| `dark_purple`             | Dark Purple  |
| `gold`                    | Gold         |
| `gray` / `grey`           | Gray         |
| `dark_gray` / `dark_grey` | Dark Gray    |
| `blue`                    | Blue         |
| `green`                   | Green        |
| `aqua`                    | Aqua         |
| `red`                     | Red          |
| `light_purple`            | Light Purple |
| `yellow`                  | Yellow       |
| `white`                   | White        |

### Conditions

All conditions in the object must be met for the tooltip to show. They are AND-ed together.

| Type        | Value                                                    | Description                                                     |
|-------------|----------------------------------------------------------|-----------------------------------------------------------------|
| `dimension` | A dimension ID, e.g. `"minecraft:the_nether"`            | Only shows when the player is in this dimension.                |
| `biome`     | A biome ID like `"minecraft:plains"`, or an array of IDs | Only shows when the player is in one of these biomes.           |
| `holding`   | An item ID, e.g. `"minecraft:diamond_pickaxe"`           | Only shows when the player is holding this item in either hand. |
| `sneaking`  | `true`                                                   | Only shows when the player is sneaking (holding Shift).         |

## Per-line Styling

Each line in the `text` arrays can be a plain string or an object with style options:

```json
{"text": "Bold gold", "color": "gold", "bold": true}
{"text": "Italic blue", "color": "blue", "italic": true}
{"text": "Underlined", "underlined": true}
{"text": "Crossed out", "strikethrough": true}
{"text": "Pixel font", "font": "minecraft:alt"}
```

When a line has no `color`, it inherits the entry-level `color`. When neither is set, it defaults to gray.

| Field           | Type   | Description                                                                                                                                                                                                     |
|-----------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `text`          | string | The text to display. Required.                                                                                                                                                                                  |
| `color`         | string | Overrides the entry-level color for this line. Hex or named.                                                                                                                                                    |
| `bold`          | bool   | Bold text.                                                                                                                                                                                                      |
| `italic`        | bool   | Italic text.                                                                                                                                                                                                    |
| `underlined`    | bool   | Underlined text.                                                                                                                                                                                                |
| `strikethrough` | bool   | Strikethrough text.                                                                                                                                                                                             |
| `font`          | string | A font from a resource pack. Built-in options include `minecraft:default` (the normal font), `minecraft:alt` (the enchanting table runes), `minecraft:uniform` (monospace). Custom resource packs may add more. |

## Variables

These placeholders are replaced with live values at render time. They only have meaning on items that are damageable or stackable.

| Variable           | Replaced with                       |
|--------------------|-------------------------------------|
| `{durability}`     | Remaining durability (max - damage) |
| `{max_durability}` | Maximum durability                  |
| `{count}`          | Stack size                          |

## Matching

Items are matched in order. Multiple rules can match the same item — they all show, stacked together.

| Method     | Key Example                                  | What it matches                                                                                                                      |
|------------|----------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| Exact      | `"minecraft:diamond"`                        | Only that specific item.                                                                                                             |
| Tag        | `"#minecraft:swords"`                        | All items with that tag. The `#` prefix is stripped and the rest is used as a tag ID.                                                |
| Wildcard   | `"staticlogistics:*"`                        | `*` matches any sequence of characters, `?` matches exactly one. `"minecraft:*_sword"` matches `wooden_sword`, `diamond_sword`, etc. |
| NBT        | Same as exact, plus `"nbt": {"Damage": "0"}` | Only exact matches with matching NBT key-value pairs.                                                                                |
| Conditions | Same as exact, plus `"conditions": {...}`    | Only when all conditions are satisfied.                                                                                              |

## Config

- File: `config/datatip.toml`
- `enabled`: Set to `false` to disable all DataTip tooltips.
- The key for showing Shift-protected tooltips can be rebound in **Options → Controls → DataTip** (default: Left Shift).
- Changes to JSON resource files take effect after `/reload` or F3+T.

## Complete Example

```json
{
  "minecraft:diamond": [
    "A shiny diamond",
    "Worth a fortune"
  ],

  // Multi-language with per-line styling
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

  // shift + prepend: hidden until Shift, shown after item name
  "minecraft:diamond_sword": {
    "text": {
      "zh_cn": [{"text": "削铁如泥", "color": "aqua"}],
      "en_us": [{"text": "Cuts through iron like butter", "color": "aqua"}]
    },
    "shift": true,
    "prepend": true
  },

  // Variables: {durability} {max_durability} {count}
  "minecraft:diamond_pickaxe": {
    "text": {
      "zh_cn": ["耐久: {durability} / {max_durability}"],
      "en_us": ["Durability: {durability} / {max_durability}"]
    }
  },

  // Bold, italic, shift — multiple styled lines from different entries stack
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

  // Bold + custom font
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

  // Strikethrough
  "minecraft:stone": {
    "text": {
      "zh_cn": [{"text": "这是删除线", "strikethrough": true}],
      "en_us": [{"text": "Strikethrough text", "strikethrough": true}]
    }
  },

  // Tag match — all items with the #minecraft:pickaxes tag
  "#minecraft:pickaxes": {
    "text": {
      "zh_cn": ["所有镐子都显示这句话"],
      "en_us": ["Common pickaxe info"]
    },
    "color": "yellow"
  },

  // Condition: only in the Nether
  "minecraft:diamond_block": {
    "text": {
      "zh_cn": ["只在下界显示"],
      "en_us": ["Only visible in the Nether"]
    },
    "color": "dark_red",
    "conditions": { "dimension": "minecraft:the_nether" }
  },

  // NBT match: only when undamaged (Damage = 0)
  "minecraft:bow": {
    "text": {
      "zh_cn": [{"text": "满耐久才显示这句话", "underlined": true}],
      "en_us": [{"text": "Full durability only", "underlined": true}]
    },
    "nbt": { "Damage": "0" }
  },

  // Wildcard match — all items from a namespace
  "staticlogistics:*": {
    "text": {
      "zh_cn": ["静态物流物品"],
      "en_us": ["Static Logistics item"]
    },
    "color": "light_purple"
  }
}
```
