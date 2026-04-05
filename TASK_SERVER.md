# Mockstation Server Task List

## 目的

`server` モジュールを、実用的な mock server に育てるための実装タスクを整理する。

対象は以下のモジュール。

- `server`
- `core/network`
- `core/data`
- `core/domain`
- `core/database`
- `core/model`

## 現状整理

### すでにあるもの

- Ktor サーバー起動基盤
- sample の `/api/testcases` API
- serialization plugin
- DI エントリポイント

### まだないもの

- 実際の testCase ディレクトリ読込
- mock response 解決
- 任意 path を受ける本体 routing
- device 識別
- Desktop 向け管理 API
- request history 保存
- WebSocket 配信
- OSS 配布前提の設定外部化

## 設計原則

1. TASK_OVERVIEW.md の仕様に従い、命名と責務を整理する
2. mock response 本体と Desktop 管理 API を分離する
3. Desktop 側から必要な集約 API は Server で提供する
4. 初期はシンプルな file-based implementation で成立させる
5. 外部 testCase ディレクトリ指定を早い段階で入れる

## Public API / Interface 追加候補

### 管理 API

- `GET /api/server/status`
- `GET /api/server/summary`
- `GET /api/testcases`
- `GET /api/testcases/{id}`
- `POST /api/testcases/activate`
- `GET /api/devices`
- `GET /api/devices/{id}`
- `PATCH /api/devices/{id}`
- `GET /api/request-history`
- `DELETE /api/request-history`
- `PATCH /api/delay-rules/{deviceId}`

### リアルタイム API

- `GET /ws/request-history`

### 内部 interface 候補

- `TestCaseFileRepository`
- `MockResponseResolver`
- `DeviceStateRepository`
- `RequestHistoryRepository`
- `DelayRuleRepository`
- `ServerStatusService`

### 型候補

- `ServerStatusResponse`
- `ServerSummaryResponse`
- `TestCaseSummaryResponse`
- `TestCaseDetailResponse`
- `ActivateTestCaseRequest`
- `DeviceResponse`
- `UpdateDeviceRequest`
- `RequestHistoryResponse`
- `DelayRuleResponse`

## フェーズ一覧

- Phase S0: 基盤設計の整理
- Phase S1: testCase ディレクトリ読込
- Phase S2: mock response 解決
- Phase S3: device 識別と状態保持
- Phase S4: Desktop 向け管理 API
- Phase S5: request history 保存と検索
- Phase S6: WebSocket 配信
- Phase S7: 遅延設定と response rule 拡張
- Phase S8: OSS 配布と運用整備
- Phase S9: 拡張機能

---

## Phase S0: 基盤設計の整理

### 目的

sample API 中心の現在構成を、本体機能を入れられる構成へ改める。

### タスク

- [ ] sample の `TestCaseApi` を暫定 API として位置付け直す
- [ ] Desktop 管理 API と mock response routing を分離する
- [ ] `server/plugins/Routing.kt` の責務を分割する
- [ ] service / repository / resolver の層を整理する
- [ ] Server 設定値の取得元を整理する
    - ポート
    - testCase directory
    - DB パス
    - ログレベル
- [ ] サーバー設定の永続化方式を決定する
    - デフォルト: 設定ファイル（JSON）または SQLDelight
    - 設定項目: `resFileFormat`, `testCaseDirectory`, `defaultDelayMs`, `port`

### 完了条件

- 今後の routing 追加先と service の配置方針が固定される
- 設定の永続化方式が決定される

---

## Phase S1: testCase ディレクトリ読込

### 目的

Mockstation のコア機能である file-based mock data を実装する。

### タスク

- [ ] testCase ディレクトリのルート解決を実装する
- [ ] 外部ディレクトリ指定に対応する
    - デフォルト: 環境変数または設定ファイルで指定可能
- [ ] test case 一覧の走査ロジックを実装する
- [ ] test case 詳細取得ロジックを実装する
- [ ] README / meta / res ファイルの扱いを定義する
- [ ] 存在しない/壊れた testCase の扱いを定義する
- [ ] res ファイル形式の設定値を読み込む
    - `resFileFormat`: 1 (METHOD_SUFFIX) | 2 (SIMPLE)
- [ ] 設定に応じた res ファイル探索ロジックを実装する
    - 1 (METHOD_SUFFIX): `{apiPath}/{METHOD}.res`
    - 2 (SIMPLE): `{apiPath}.res`

### 完了条件

- 外部ディレクトリから testCase 一覧を取得できる
- Desktop 管理 API の `GET /api/testcases` で使えるデータが揃う
- 設定された res ファイル形式に従ってファイルを探索できる

### テスト観点

- ルート未指定
- 空ディレクトリ
- 不正なファイル構成
- 大量 testCase

---

## Phase S2: mock response 解決

### 目的

実際の HTTP リクエストに対し、testCase ディレクトリから適切な response を返す。

### タスク

- [ ] 任意 path/method を受ける routing を追加する
- [ ] `default` testCase への fallback を実装する
- [ ] レスポンスファイル parser を実装する
    - status
    - headers
    - body
- [ ] リクエスト path から対応する `.res` ファイルを解決する
- [ ] path parameter / query parameter を考慮した探索仕様を定義する
    - query parameter 条件分岐: `GET@page__1.res`, `GET@page__1--status__active.res`
    - 詳細は TASK_OVERVIEW.md「.res ファイル形式」を参照
- [ ] res ファイル未発見時のエラー応答を整理する
- [ ] content type 推定または header 優先ロジックを実装する

### 完了条件

- `default` と任意 testCase の双方で mock response を返せる
- 不一致時の挙動が利用者に分かる

### テスト観点

- GET/POST/PUT/DELETE
- query parameter あり/なし
- 該当ファイルなし
- header 指定あり

---

## Phase S3: device 識別と状態保持

### 目的

device ごとに test case と delay 設定を持てる状態にする。

### タスク

- [ ] device 識別方式を実装する
    - デフォルト: `deviceId` header があれば使用
    - デフォルト: なければ新規発行して `X-Device-Id` で返却
- [ ] device 状態の永続化先を決める
    - デフォルト: SQLDelight
- [ ] device と現在 test case の紐付けを保存する
- [ ] device と delay rule の紐付けを保存する
- [ ] last accessed などの管理情報を保存する
- [ ] Desktop 管理 API で見せる項目を確定する

### 完了条件

- 同じ deviceId で再アクセスすると設定が維持される
- 新規 device が Server 上で把握される

### テスト観点

- header あり/なし
- 初回アクセス
- 同一 device の再アクセス
- 永続化データ破損時

---

## Phase S4: Desktop 向け管理 API

### 目的

Desktop 側が必要とする CRUD と状態取得を行えるようにする。

### タスク

- [ ] `GET /api/server/status` を実装する
- [ ] `GET /api/server/summary` を実装する
- [ ] `GET /api/testcases` を本実装へ置換する
- [ ] `GET /api/testcases/{id}` を本実装へ置換する
- [ ] `POST /api/testcases/activate` を実装する
- [ ] `GET /api/devices` を実装する
    - クエリパラメータ: `registered` (true/false) で登録済み/未登録をフィルタ
- [ ] `GET /api/devices/{id}` を実装する
- [ ] `POST /api/devices/{id}/register` を実装する
    - サーバーデバイスを登録済みに変更
- [ ] `PATCH /api/devices/{id}` を実装する
    - 更新項目: `name`, `isEnabled`, `testCaseId`
- [ ] `DELETE /api/devices/{id}` を実装する
    - Desktop の管理対象から外す（履歴は保持）
- [ ] DTO と domain model の変換層を整理する
- [ ] API エラー形式を統一する
- [ ] `GET /api/server/settings` を実装する
- [ ] `PATCH /api/server/settings` を実装する
- [ ] 設定変更時の永続化を実装する
- [ ] 設定項目の一覧:
    - `resFileFormat`: number (1 = METHOD_SUFFIX, 2 = SIMPLE)
    - `testCaseDirectory`: string
    - `defaultDelayMs`: number
    - `port`: number（再起動が必要な項目は警告を返す）

### 完了条件

- Desktop MVP に必要な API 一式が揃う
- DTO が sample 用ではなく管理用途に置き換わる
- 設定 API が動作し、変更が永続化される

### テスト観点

- 正常系
- 未存在 resource
- validation error
- serialization error

---

## Phase S5: request history 保存と検索

### 目的

mock server の利用履歴を Desktop から参照できるようにする。

### タスク

- [ ] request 発生時に履歴を生成する
- [ ] 保存項目を確定する
    - method
    - path
    - status code
    - timestamp
    - deviceId
    - request body
    - response body
    - request/response headers
    - duration
- [ ] 履歴の保存先を決める
    - デフォルト: SQLDelight
- [ ] `GET /api/request-history` を実装する
- [ ] filter 条件（クエリパラメータ）を定義する
    - `search`: パスの部分一致検索
    - `methods`: HTTPメソッド複数選択 (GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS)
    - `statusCategories`: ステータスカテゴリ (2xx, 3xx, 4xx, 5xx)
    - `timeRange`: 時間範囲 (1h, 24h, 7d, all)
    - `sortOrder`: ソート順序 (newest, oldest)
    - `deviceId`: デバイスIDでの絞り込み
- [ ] `DELETE /api/request-history` を実装する
- [ ] 履歴肥大化対策を入れる
    - デフォルト: 件数上限と古いデータの削除

### 完了条件

- Desktop から履歴を取得して絞り込みできる
- 履歴が無制限に増え続けない

### テスト観点

- 高頻度アクセス
- body が大きい場合
- 件数上限超過
- clear 操作

---

## Phase S6: WebSocket 配信

### 目的

request history を Desktop へリアルタイム配信する。

### タスク

- [ ] Ktor WebSocket を設定する
- [ ] `GET /ws/request-history` を追加する
- [ ] request history 保存と配信の順序を決める
    - デフォルト: 保存成功後に配信
- [ ] 接続管理を実装する
- [ ] backpressure と buffer 方針を決める
- [ ] 送信 payload を HTTP API の履歴型と揃える
- [ ] 接続失敗や slow consumer の扱いを決める

### 完了条件

- Desktop が購読できる
- 切断や高頻度更新で Server が不安定にならない

### テスト観点

- 複数接続
- 切断/再接続
- burst traffic

---

## Phase S7: 遅延設定と response rule 拡張

### 目的

delay と条件分岐を整理した仕様で提供する。

### タスク

- [ ] device 単位 delay rule を適用する
- [ ] DelayType の3モードを実装する
    - OFF: 遅延なし
    - PRESET: プリセット値（デフォルト: 5000ms）
    - CUSTOM: ユーザー定義値
- [ ] targetFiles による対象ファイル指定を実装する
    - 空の場合: すべてのファイルに適用
    - 指定時: 特定ファイルのみに適用
- [ ] query parameter 条件分岐の探索を実装する
- [ ] 将来の header/body 条件分岐に備えた拡張ポイントを設計する
- [ ] `PATCH /api/delay-rules/{deviceId}` を実装する
    - リクエストボディ: `{ type, delayMs, targetFiles, isEnabled }`
- [ ] `GET /api/delay-rules/{deviceId}` を実装する
- [ ] rule の優先順位を明文化する

### 完了条件

- Desktop から設定した delay が実レスポンスに反映される
- query parameter に応じて別 response を返せる

### テスト観点

- delay 0 / 非 0
- 複数 parameter
- rule 不一致時 fallback

---

## Phase S8: OSS 配布と運用整備

### 目的

ローカル開発用ツールから OSS 配布物へ移行できる状態にする。

### タスク

- [ ] `TESTCASE_DIR` 相当の外部設定を正式化する
- [ ] Dockerfile を作成する
- [ ] compose 例を作成する
- [ ] `.dockerignore` を整備する
- [ ] GitHub Actions の build/publish 方針を決める
- [ ] README のサーバー起動手順を更新する
- [ ] サンプル testCase を整備する
- [ ] logging 設定を運用向けに見直す
- [ ] README に全仕様を記載する
    - .res ファイル形式（METHOD_SUFFIX / SIMPLE）
    - パラメータ条件分岐の命名規則
    - 設定 API 仕様
    - 管理 API 仕様
    - WebSocket 仕様
    - 起動手順

### 完了条件

- OSS 利用者が testCase を外出しして Server を起動できる
- CI/CD で再現可能な build 手順が定義される
- README に全仕様が集約されている

---

## Phase S9: 拡張機能

### 対象候補

- [ ] シナリオテスト
- [ ] スクリプト実行
- [ ] プロキシ/フォールバック
- [ ] マルチテナント
- [ ] AI 支援
- [ ] 静的ファイル配信（Ktorの静的ファイル配信機能）
    - WebView + JS Interface の疎通テスト用ページ
    - テスト用リンク先ページのホスティング
    - testCase ディレクトリ内の `static/` を配信

### 方針

- MVP の core value に影響しないものは後続へ送る
- ただし API や storage の設計が後から壊れないように拡張余地は残す

## 実装順の依存関係

1. S0
2. S1
3. S2
4. S3
5. S4
6. S5
7. S6
8. S7
9. S8
10. S9

## Server 側の受け入れ条件

- file-based mock server として動作する
- device ごとに状態を切り替えられる
- Desktop が必要とする API が揃う
- request history を調査に使える
- 外部 testCase ディレクトリと OSS 配布を前提にした設定がある

## 保留事項とデフォルト判断

- mock response の file format
    - デフォルト: TASK_OVERVIEW.md「.res ファイル形式」に従う
- Desktop 管理 API の認証
    - デフォルト: ローカル利用前提のため MVP では未導入
- WebSocket message schema
    - デフォルト: HTTP 履歴レスポンスと同系統に揃える
- request history export
    - デフォルト: MVP から外す
