# FranekBoxSets

![banner](assets/banner.png)

Generator tematycznych setów pod serwery BoxPvP. Tworzy gotowe zestawy (hełm, napierśnik, spodnie, buty, miecz, kilof, nożyce i elytra) podzielone na 14 poziomów z własnymi kolorami HEX, ikonami enchantów w lore i automatycznym skalowaniem poziomów zaklęć.

Każdy przedmiot ma ukryte standardowe flagi Minecrafta, jest niezniszczalny i posiada zapisany poziom w NBT (`franek_set_level`).

## Komendy

| Komenda | Co robi |
|---|---|
| `/setgive <gracz> <poziom> [przedmiot\|all]` | daje graczowi cały set z wybranego poziomu lub konkretną część (`helmet`, `sword`, `pickaxe`, `elytra` itd.) |
| `/setreload` | przeładowuje `config.yml` na żywo |

Uprawnienia: `franek.boxsets.give` oraz `franek.boxsets.reload` (domyślnie dla `op`).

## Config

`plugins/FranekBoxSets/config.yml`, tworzy się sam przy starcie:

```yaml
max-level: 14

enchants:
  enchant-min: 1
  enchant-max: 10

sword:
  fire-aspect-from-level: 10
  fire-aspect-min: 1
  fire-aspect-max: 2

levels:
  1:
    theme: "ᴡɪʟᴋᴀ"
    hex: "#AAAAAA"
    material-tier: "LEATHER"
```

Dostępne tiery materiałów to `LEATHER`, `IRON`, `DIAMOND` oraz `NETHERITE` (dla skórzanego tieru miecz i kilof są automatycznie kamienne).

## Instalacja

Jar z folderu [`releases/`](releases/) wrzucasz do `plugins/` na serwerze (Spigot / Paper 1.20+) i restart.

## Budowanie

```bash
mvn package
```

Wychodzi `target/FranekBoxSets-1.0.jar`. Wymaga JDK 17+ i Mavena.

## Licencja

Wszelkie prawa zastrzeżone. Autor: franekk ([GitHub](https://github.com/Blejziak)).
