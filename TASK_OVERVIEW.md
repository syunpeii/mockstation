# Mockstation Implementation Task Overview

## 目的

このドキュメントは、`mockstation` を UI モック段階から実装段階へ進めるための全体タスク整理です。

- 目的は OSS として成立する構成で設計・実装すること
- 現時点では Desktop UI は先行実装済みだが、Server / Domain / Data / Persistence は未接続の部分が多い

詳細タスクは以下に分割する。

- `TASK_DESKTOP.md`: Desktop アプリ実装タスク
- `TASK_SERVER.md`: Server 実装タスク

## 現状サマリー

### 実装済み

- Compose Desktop の画面骨組み
    - Home
    - Device Management
    - Test Case Search
    - Settings
- Design System / Navigation / Preview
- KMP のモジュール分割
    - `core/model`
    - `core/network`
    - `core/data`
    - `core/domain`
    - `core/database`
    - `core/datastore`

### 実装済み（Phase S0～S5）

- ✅ Server の mock server 本体機能（MockResponseResolver, ResFileParser）
- ✅ ファイルベースのテストケース読込（TestCaseFileService）
- ✅ デバイス管理 API（CRUD完全実装: GET/POST/PATCH/DELETE）
- ✅ テストケース切替 API（/api/testcases/activate）
- ✅ 外部ディレクトリ対応（application.conf, ServerSettings）
- ✅ サーバー設定 API（/api/server/settings）
- ✅ エラーレスポンス統一（ErrorResponse.kt）
- ✅ リクエスト履歴の記録（MockRouting自動記録）
- ✅ 履歴検索・フィルタリング API（/api/request-history、フィルタ・ソート・ページング対応）

### 現在のフェーズ

- ✅ Desktop Phase D0: 基盤整備（完了）
- ✅ Desktop Phase D1: Settings の実機能化（完了）
- ⏳ Desktop Phase D2: Test Case Search の実機能化（次のステップ）

### 未実装またはモック状態

- Desktop ViewModel の実データ接続（Phase D0～D5で対応予定）
- WebSocket 配信（Phase S6、Desktop接続完了後）
- 遅延設定の詳細拡張（Phase S7）
- OSS 配布向けの Docker、リリース整備（Phase S8）

## 機能一覧と優先度

| 機能領域               | 優先度    |
|--------------------|--------|
| テストケース切替           | Must   |
| デバイス単位の設定          | Must   |
| ファイルベース応答          | Must   |
| リクエスト履歴表示          | Must   |
| 遅延設定               | Must   |
| テストケース検索           | Must   |
| 設定画面               | Must   |
| 外部 testCase ディレクトリ | Must   |
| WebSocket 連携       | Should |
| パラメータ条件分岐          | Should |
| Docker / CI / 配布   | Should |
| シナリオテスト            | Could  |
| スクリプト実行            | Could  |
| プロキシ/フォールバック       | Could  |
| 静的ファイル配信           | Could  |

## MVP の定義と実装状況

MVP では以下を満たすことを目標にする。

| # | 要件                                                        | 実装状況               |
|---|-----------------------------------------------------------|--------------------|
| 1 | Server が外部 `testCase` ディレクトリを読み込み、HTTP リクエストに対してモック応答を返せる | ✅ 完了（Phase S1, S2） |
| 2 | Device ごとに現在の test case と delay 設定を保持できる                  | ✅ 完了（Phase S3）     |
| 3 | Desktop から test case / device / delay を操作できる              | ✅ 完了（Phase S4）     |
| 4 | Desktop と Server の接続先を設定できる                               | ✅ 完了（Phase S0, S4） |
| 5 | リクエスト履歴を Desktop で確認できる                                   | ✅ 完了（Phase S5）     |
| 6 | OSS 利用者が README だけで起動できる起動手順がある                           | ⏳ Phase S8 で実装予定   |

## .res ファイル形式

### 形式の種類

Mockstation は 2 つの `.res` ファイル形式をサポートする。

| 値        | 形式名           | パターン                     | 例                                         | 用途             |
|----------|---------------|--------------------------|-------------------------------------------|----------------|
| 1（デフォルト） | METHOD_SUFFIX | `{apiPath}/{METHOD}.res` | `api/users/GET.res`, `api/users/POST.res` | RESTful API 向け |
| 2        | SIMPLE        | `{apiPath}.res`          | `get-user-info.res`                       | メソッド込みパス向け     |

### 設定方法

- サーバー設定 API（`/api/server/settings`）で切り替え
- サーバー側で永続的に保存
- プロジェクト単位で統一

### パラメータ条件分岐

クエリパラメータに応じて異なるレスポンスを返す場合のファイル命名規則:

```
GET@page__1.res
GET@page__1--status__active.res
```

## 設定管理

### サーバー設定の永続化

サーバーは以下の設定を永続的に保存する:

```json
{
    "resFileFormat": 1,
    "testCaseDirectory": "/path/to/testCase",
    "defaultDelayMs": 0,
    "port": 8080
}
```

※ `resFileFormat`: 1 = METHOD_SUFFIX（デフォルト）, 2 = SIMPLE

### 設定 API

```
GET  /api/server/settings        # 現在の設定取得
PATCH /api/server/settings       # 設定更新
```

## ドキュメント方針

- **仕様は README に集約**: SPEC_*.md は作成しない
- **TASK_*.md は実装タスク用**: 仕様ではなく実装計画を記載
- **コードコメントに仕様を書かない**: 実装上必要な補足のみ記載

## 責務分割

### Desktop の責務

- Server 接続設定
- test case 一覧/検索/詳細表示
- device 一覧/登録/編集/有効無効
- request history 可視化
- delay 設定編集
- 接続状態とエラー表示
- 利用者向け設定の保持

### Server の責務

- test case ディレクトリ読込
- mock response 解決
- device 識別と状態保持
- Desktop 向け管理 API 提供
- request history 収集と配信
- delay / response rule の適用
- OSS 配布を前提にした設定外部化

### Common/Core の責務

- ドメインモデルの定義
- Repository / UseCase
- API DTO と serialization
- SQLDelight / DataStore の I/O 抽象化

## 実装フェーズ

### Phase 0: 仕様固定

- 機能要件の整理完了
- `TASK_DESKTOP.md` `TASK_SERVER.md` の作成
- API と永続化対象の一覧化

### Phase 1: Server MVP

- testCase ディレクトリ解決
- mock response 解決
- device 識別
- Desktop 管理 API の最小セット
- request history 保存

### Phase 2: Desktop 接続

- Settings で接続先管理
- Test Case Search の実データ化
- Device Management の実データ化
- Home の実データ化
- request history 表示の実データ化

### Phase 3: リアルタイム化と運用強化

- WebSocket 配信
- 再接続処理
- エラー/空状態の強化
- ログと監視

### Phase 4: OSS 整備

- 外部ディレクトリ設定の整理
- Docker
- GitHub Actions
- 配布物作成
- README / docs / サンプル testCase 整備

### Phase 5: 拡張機能

- シナリオテスト
- 高度な response rule
- プロキシ/フォールバック
- スクリプト実行

## 優先順位ルール

- `Must`: MVP に必要。最初に着手
- `Should`: MVP 後すぐに必要。設計時点で見越しておく
- `Could`: 後続フェーズへ送る
- `Out`: 今回の計画には入れるが、実装対象にはしない

## 重要インターフェース候補

以下はドキュメント上で先に意識しておくインターフェース。最終名称は実装時に調整してよいが、責務は固定する。

### Server API 候補

- `GET /api/server/status`
- `GET /api/server/settings`
- `PATCH /api/server/settings`
- `GET /api/testcases`
- `GET /api/testcases/{id}`
- `POST /api/testcases/activate`
- `GET /api/devices`
- `GET /api/devices/{id}`
- `POST /api/devices/{id}/register`
- `PATCH /api/devices/{id}`
- `DELETE /api/devices/{id}`
- `GET /api/request-history`
- `DELETE /api/request-history`
- `GET /api/delay-rules/{deviceId}`
- `PATCH /api/delay-rules/{deviceId}`
- `GET /ws/request-history`

### 型候補

- `ServerStatus`
- `ServerSettings`
- `TestCaseSummary`
- `TestCaseDetail`
- `TestCaseActivationRequest`
- `DeviceSummary`
- `DeviceDetail`
- `DeviceRegisterRequest`
- `DeviceUpdateRequest`
- `DelayRule`
- `DelaySettings` (type, delayMs, targetFiles, isEnabled)
- `RequestHistoryEntry`
- `RequestHistoryFilter`

## ドキュメント運用ルール

- TODO は機能単位ではなく、実装者がそのまま着手できる粒度まで落とす
- 各タスクに以下を含める
    - 目的
    - 前提依存
    - 実装項目
    - 完了条件
    - テスト観点
- 仕様変更があったら `TASK_OVERVIEW.md` を先に更新し、そのあと Desktop / Server 側へ反映する

## 完了条件

以下を満たしたら、実装計画の土台が整ったとみなす。

- `TASK_DESKTOP.md` に Desktop 側のフェーズ・依存 API・受け入れ条件が書かれている
- `TASK_SERVER.md` に Server 側のフェーズ・API・永続化・受け入れ条件が書かれている
- MVP と拡張機能の境界が明確
- OSS 化タスクが後回しにならず、独立したフェーズとして定義されている

## 保留事項

以下は実装前に見直すが、この時点ではデフォルト方針を採用する。

- testCase のファイル形式は本ドキュメントの「.res ファイル形式」に従う
- Desktop から Server を同梱起動するかは保留とし、当面は別プロセス前提とする
- WebSocket は request history 配信を主用途とし、制御メッセージの双方向化は後続フェーズで判断する
