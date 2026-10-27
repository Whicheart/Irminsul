# irminsul

原神角色资料 API。把 MongoDB 里的数据聚合成前端可直接渲染的 JSON。

> 项目仅用于学习与研究；角色、素材名称及相关内容的版权归米哈游（miHoYo / HoYoverse）所有。

## 目录

- [功能](#功能)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [配置](#配置)
- [接口](#接口)
- [数据模型](#数据模型)
- [实现约定](#实现约定)
- [开发](#开发)
- [已知待办](#已知待办)

## 功能

- **角色列表**：支持按名称模糊匹配、元素、武器、星级过滤，按图鉴 `sortId` 倒序返回。
- **角色基础信息**：称号、所属、生日、命之座/神之眼名称、四国 CV、1–100 级生命/攻击/防御成长、突破加成属性、突破与天赋升级材料。

## 技术栈

| 类别      | 选型                                                           |
| --------- | -------------------------------------------------------------- |
| 运行时    | Java 17                                                        |
| 框架      | Spring Boot 4.0.1                                              |
| 持久化    | Spring Data MongoDB                                            |
| 接口文档  | springdoc-openapi 3.0.0（Swagger UI）                          |
| 辅助      | Lombok；Caffeine（已在依赖中，当前未接入缓存）                 |
| 构建/格式 | Maven Wrapper（Maven 3.9.12）、Spotless + palantir-java-format |

## 快速开始

前置条件：

- JDK 17 及以上
- 可访问的 MongoDB，且已完成数据导入
- 如未完成导入，可通过[脚本](https://github.com/Whicheart/GenshinDataImporter)进行导入

```bash
git clone <repo-url> irminsul
cd irminsul

# 可选：默认就是本地 27017 的 GI 库
export MONGODB_URI='mongodb://localhost:27017/GI'

# 启动（Windows 用 mvnw.cmd spring-boot:run）
./mvnw spring-boot:run
```

启动后：

- 服务地址 `http://localhost:8080`
- Swagger UI `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON `http://localhost:8080/v3/api-docs`

打包与运行：

```bash
./mvnw clean package
java -jar target/irminsul-0.0.1-SNAPSHOT.jar
```

## 配置

| 配置项        | 默认值                         | 说明                               |
| ------------- | ------------------------------ | ---------------------------------- |
| `MONGODB_URI` | `mongodb://localhost:27017/GI` | 连接串；带账号口令时用环境变量注入 |
| `server.port` | `8080`                         | 可用 `--server.port=9090` 覆盖     |

仓库自带 `.gitignore` 规则与提交前密钥检查，启用方式见 [.githooks/README.md](.githooks/README.md)：

```bash
git config core.hooksPath .githooks
```

## 接口

所有接口统一返回 `{ "code": 200, "msg": "ok", "data": ... }`，`code` 非 200 表示业务或系统错误。

| 方法与路径                            | 说明                                                                                                      |
| ------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| `GET /api/avatar/list`                | 角色列表。可选参数：`name`（模糊包含）、`elements`、`weaponTypes`、`qualityTypes`（可重复传参或逗号分隔） |
| `GET /api/avatar/{id}/base-info`      | 角色基础信息，含成长曲线与材料                                                                            |
| `GET /api/avatar/{id}/outfits`        | 衣装列表                                                                                                  |
| `GET /api/avatar/{id}/stories`        | 角色故事                                                                                                  |
| `GET /api/avatar/{id}/voices`         | 语音（`dailies` 日常、`battles` 战斗）                                                                    |
| `GET /api/avatar/{id}/constellations` | 命之座 1–6                                                                                                |
| `GET /api/avatar/{id}/skills`         | 战斗技能、固有天赋、特殊固有天赋                                                                          |

过滤参数的枚举取值：

- `elements`：`PYRO`、`HYDRO`、`ANEMO`、`ELECTRO`、`DENDRO`、`CRYO`、`GEO`
- `weaponTypes`：`WEAPON_SWORD_ONE_HAND`、`WEAPON_CLAYMORE`、`WEAPON_POLE`、`WEAPON_BOW`、`WEAPON_CATALYST`
- `qualityTypes`：`QUALITY_ORANGE`、`QUALITY_PURPLE`

示例：

```bash
curl 'http://localhost:8080/api/avatar/list?name=绫华'
curl 'http://localhost:8080/api/avatar/list?elements=HYDRO&qualityTypes=QUALITY_ORANGE'
curl 'http://localhost:8080/api/avatar/10000002/base-info'
```

## 数据模型

默认库 `GI`，共 23 个集合。当前接口实际读取的：

| 集合                  | 用途                                                           |
| --------------------- | -------------------------------------------------------------- |
| `avatar`              | 角色主表：基础白值、图标名、成长曲线、突破组 ID                |
| `avatar_codex`        | 图鉴排序 `sortId` 与上线时间                                   |
| `avatar_promote`      | 突破等级、属性加成、突破材料                                   |
| `avatar_curve`        | 1–90 级成长乘数                                                |
| `avatar_skill_depot`  | 技能组：`skills`、`energySkill`、`talents`、`proudSkillGroups` |
| `avatar_skill`        | 技能名称/描述与原始图标                                        |
| `proud_skill`         | 天赋逐级数值与升级材料                                         |
| `avatar_talent`       | 命之座名称与文案                                               |
| `avatar_costume`      | 衣装                                                           |
| `fetter_info`         | 角色资料：生日、阵营、神之眼、CV                               |
| `fetter_story`        | 角色故事                                                       |
| `fetters`             | 语音（`type` 1 日常、2 战斗）                                  |
| `material`            | 材料名称                                                       |
| `text_map_chs_medium` | 文本哈希表，`hash → 中文文本`                                  |

已建模但暂未接入接口：`avatar_ability`、`avatar_fetters_level`、`avatar_level`、`avatar_trace_effect`、`attack_attenuation`、`fetter_character_card`、`material_codex`、`material_source_data`、`text_map_en_medium`。

## 实现约定

- **只读**：服务不写入数据库。
- **角色范围**：仅保留 `id` 在 `10000002`–`10000899` 之间的角色，目前排除旅行者与奇偶。
- **技能过滤**：跳过 `id ≥ 100000` 的技能，并跳过 `30506`、`11076` 两个特殊技能。
- **图标名**：
    - 列表：`<iconName>_Gacha`
    - 立绘：`<iconName>_Splash`
    - 命之座：`<iconName>_Constellation_<1-6>`
    - 战斗技能：`<iconName>_Talent_<NormalAttack|ElementSkill|ElementBurst>`，未命中的特殊技能保留原始 `skillIcon`
    - 固有天赋：`<iconName>_Passive_<n>`，与特殊固有天赋共用自增序号
- **文本翻译**：所有 `*TextMapHash` 字段经 `text_map_chs_medium` 解析，结果缓存在进程内 `ConcurrentHashMap`。
- **富文本**：描述中保留 `<color=#...>` 等游戏内标记，需要前端自行解析。
- **JSON 格式**：开启 `indent-output`，字段按字母序输出；`GachaIconName` 首字母大写（`AvatarBrief` 字段名如此，前端取值时注意大小写）。

## 开发

```bash
./mvnw test              # 目前仅 contextLoads，连接信息取自同一份配置
./mvnw spotless:apply    # 格式化（palantir-java-format）
./mvnw verify            # 会执行 spotless:check，格式不合规即失败
```

代码风格由 `.editorconfig` 约定：UTF-8、LF、4 空格缩进（`yml`/`xml`/`json` 为 2 空格），`.cmd` 使用 CRLF。

提交前会扫描暂存区的密钥特征，启用与绕过方式见 [.githooks/README.md](.githooks/README.md)。

## 已知待办

**功能性**
- 旅行者、奇偶的角色信息。
- 武器、圣遗物、三深渊、卡池等信息。
- 技能更详细的信息，如削韧、抗打断、附着情况、判定范围、攻击类型等。

**非功能性**
- CORS 仍为开发期白名单（`localhost:5173`、`localhost:3000`）。
- 未接入的集合对应能力尚缺：角色属性详情、技能等级、材料来源、英文文本。
- Caffeine 已引入但未使用，热点数据（如 `text_map` 全量、角色列表）可考虑加缓存层。
- 认证与鉴权未启用：`spring-boot-starter-security`、JJWT 依赖在 `pom.xml` 中处于注释状态。
