# Mockstation Desktop Task List

## 目的

Compose Desktop で先行実装した UI を、`mockstation` の実機能に接続するための実装タスクを整理する。

対象は以下の画面と関連モジュール。

- `composeApp/src/desktopMain/kotlin/com/github/syunpeii/mockstation/app/ui/home`
- `composeApp/src/desktopMain/kotlin/com/github/syunpeii/mockstation/app/ui/devicemanagement`
- `composeApp/src/desktopMain/kotlin/com/github/syunpeii/mockstation/app/ui/testcasesearch`
- `composeApp/src/desktopMain/kotlin/com/github/syunpeii/mockstation/app/ui/settings`
- `core/network`
- `core/data`
- `core/domain`
- `core/datastore`
- `core/database`

## 現状整理

### すでにあるもの

- 画面構成とナビゲーション
- UI state の型
- mock data を返す ViewModel
- Design System コンポーネント

### Server 側で実装済み（Phase S0～S5）

- ✅ Server API（Device CRUD、Settings、Test Case、Request History）
- ✅ リクエスト履歴記録機能
- ✅ リクエスト履歴検索・フィルタリング API

### Desktop で実装が必要な部分

- 実 API 呼び出し（HttpClient/Repository 接続）
- 永続化と初期値読込
- エラーハンドリング方針
- 画面間で共有する接続/選択状態
- mock data から実データへの移行計画

## 実装方針

1. まず Settings で接続先とローカル設定を確立する
2. 次に Test Case Search と Device Management を Server API に接続する（**Server の S4 が完了済み**）
3. Home は集約表示なので、他画面の基盤ができた後に接続する
4. Request History は Server API で HTTP 取得が実装済み（**S5 が完了済み**）。ページング・フィルタ機能も対応。
5. WebSocket は Phase D6 以降で段階的に導入
6. ViewModel から直接 `HttpClient` を呼ばず、Repository / UseCase を通す

**Server 実装完了による利点:**

- Desktop は即座に Device CRUD、Request History 検索機能を利用可能
- API 仕様が固定されたため、Desktop 実装の予測可能性が高い
- 自動テストのモック対象が明確化

## Public Interface / Type 追加候補

### Desktop で利用する Repository

- `SettingsRepository`
- `ConnectionRepository`
- `ServerStatusRepository`
- `TestCaseRepository`
- `DeviceRepository`
- `RequestHistoryRepository`

### Desktop で利用する UseCase

- `GetServerStatusUseCase`
- `GetTestCasesUseCase`
- `GetTestCaseDetailUseCase`
- `ActivateTestCaseUseCase`
- `GetDevicesUseCase`
- `UpdateDeviceUseCase`
- `GetRequestHistoryUseCase`
- `ObserveRequestHistoryUseCase`
- `UpdateDelayRuleUseCase`

### UI 向け DTO / Model

- `ConnectionSetting`
- `TestCaseListItem`
- `TestCaseDetailModel`
- `DeviceListItem`
- `DeviceEditorState`
- `RequestHistoryListItem`
- `RequestHistoryFilterState`
- `DelaySettingsForm`

## フェーズ一覧

- Phase D0: 基盤整備
- Phase D1: Settings の実機能化
- Phase D2: Test Case Search の実機能化
- Phase D3: Device Management の実機能化
- Phase D4: Request History の実機能化
- Phase D5: Home の実機能化
- Phase D6: UX / 回復性 / 品質向上
- Phase D7: Desktop 配布と OSS 向け整備

---

## Phase D0: 基盤整備

### 目的

mock data 実装を段階的に置き換えられる基盤を作る。

### タスク

- [ ] Desktop から利用する Repository 一覧を確定する
- [ ] `core/network` の API interface を Desktop 要件に合わせて分割する
- [ ] `core/data` に Repository 実装を追加する
- [ ] `core/domain` に最低限の UseCase を追加する
- [ ] `core/datastore` に接続設定保存用の model を定義する
- [ ] `core/database` で Desktop ローカル保持が必要な情報を整理する
- [ ] Koin module を Desktop 実装に合わせて配線する
- [ ] mock state から実データへ切り替える対象 ViewModel を一覧化する

### 完了条件

- 各 ViewModel が今後どの Repository / UseCase を使うか明示されている
- API / DataStore / DB の責務が重複していない

### テスト観点

- DI 初期化時に依存解決エラーが発生しない
- Repository を fake 実装に差し替えられる

---

## Phase D1: Settings の実機能化

### 目的

Desktop アプリから接続先 Server を設定・保存・検証できるようにする。

### 対象 UI

- `SettingsScreen`
- `SettingsViewModel`

### タスク

- [ ] 接続先 URL の保存/読込を実装する
- [ ] 接続先の複数候補管理方針を確定する
    - デフォルト: 複数保存可、アクティブ接続は 1 件
- [ ] `GET /api/server/status` を利用した接続確認処理を実装する
- [ ] 接続テスト結果の UI 表示を実装する
- [ ] testCase directory 表示の役割を明確化する
    - Desktop が保持するのか
    - Server の設定値を参照するだけなのか
- [ ] テーマ / ナビゲーション設定の保存を実装する
- [ ] `onAddConnection`, `onEditConnection`, `onDeleteConnection` を実機能へ置換する
- [ ] サーバー設定の表示を実装する
    - `GET /api/server/settings` を利用
- [ ] res ファイル形式の設定変更 UI を実装する（必要に応じて）
    - 1 (METHOD_SUFFIX) / 2 (SIMPLE) の切り替え
- [ ] testCase ディレクトリの表示を実装する
    - Server 側設定値の参照表示

### Server 依存

- `GET /api/server/status`
- `GET /api/server/settings`
- `PATCH /api/server/settings`

### 完了条件

- アプリ再起動後も接続設定が復元される
- 接続失敗時に UI で理由が分かる
- 設定画面の主要操作が mock 実装ではなくなる

### テスト観点

- URL 保存後の再起動復元
- 不正 URL / 接続拒否 / タイムアウト時の表示
- 接続先切替後に他画面が追従する

---

## Phase D2: Test Case Search の実機能化

### 目的

Server 上の test case を検索・表示・選択できるようにする。

### 対象 UI

- `TestCaseSearchScreen`
- `TestCaseSearchViewModel`

### タスク

- [ ] `GET /api/testcases` を使った一覧取得を実装する
- [ ] test case の検索条件をローカルフィルタか Server フィルタか決める
    - デフォルト: 初期はローカルフィルタ
- [ ] 選択中 test case の詳細取得を実装する
- [ ] Markdown / README / meta 情報の表示元を決める
- [ ] test case の適用操作を Server API に接続する
- [ ] tag 検索と自由入力検索を実データに対応させる
- [ ] refresh を mock から API 再読込へ置換する
- [ ] 選択中 test case が device 単位か全体単位かを UI に明示する
    - デフォルト: device 単位を優先

### Server 依存

- `GET /api/testcases`
- `GET /api/testcases/{id}`
- `POST /api/testcases/activate`

### 完了条件

- 一覧・検索・詳細・適用が実データで動く
- 適用対象が UI 上で誤解なく分かる

### テスト観点

- test case 数が多い場合の検索
- 存在しない ID 選択時の扱い
- 別 device 選択中に test case を切り替えた場合の反映

---

## Phase D3: Device Management の実機能化

### 目的

Server に認識されている device と Desktop 上で管理する device を正しく扱えるようにする。

### 対象 UI

- `DeviceManagementScreen`
- `DeviceManagementViewModel`

### タスク

- [ ] device 一覧取得を実装する
- [ ] `registeredDevices` と `serverDevices` の意味を整理する
    - デフォルト: `serverDevices` は Server が把握している全 device
    - デフォルト: `registeredDevices` は Desktop 上で表示/操作対象として採用した device
- [ ] device 登録フローを API とローカル状態に接続する
- [ ] device 名の編集を保存可能にする
- [ ] device の有効/無効を保存可能にする
- [ ] device 削除時の扱いを確定する
    - デフォルト: Desktop の管理対象から外すだけで、Server 側の履歴は消さない
- [ ] selected device の共有状態を他画面へ伝搬する仕組みを追加する
- [ ] delay 設定ダイアログを実データに接続する
    - DelayType (OFF, PRESET, CUSTOM) の切り替え
    - カスタム遅延時間の入力
    - targetFiles の選択（Select All / Clear All 対応）
    - 有効/無効トグル
- [ ] Server から新規 device を検出したときの導線を整える

### Server 依存

- `GET /api/devices`
- `GET /api/devices/{id}`
- `POST /api/devices/{id}/register`
- `PATCH /api/devices/{id}`
- `DELETE /api/devices/{id}`
- `GET /api/delay-rules/{deviceId}`
- `PATCH /api/delay-rules/{deviceId}`

### 完了条件

- UI 上の device 操作が保存される
- selected device が test case 適用や履歴表示に使われる
- delay 設定が device 単位で扱える

### テスト観点

- device 0 件時
- 新規 device 検出時
- 無効化 device の test case 操作禁止
- 削除した device の再登録

---

## Phase D4: Request History の実機能化

### 目的

request history の取得・検索・フィルタ・リアルタイム更新を実装する。

### 対象 UI

- `DeviceManagementUiState.RequestHistoryState`
- request history 関連コンポーネント

### タスク

- [ ] `GET /api/request-history` による初回取得を実装する
- [ ] device ごとの列表示に必要なレスポンス形式を定義する
- [ ] filter 条件を API と整合させる
    - searchText: パスの部分一致検索
    - selectedMethods: HTTPメソッド複数選択 (GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS)
    - selectedStatusCategories: ステータスカテゴリ (2xx, 3xx, 4xx, 5xx)
    - timeRange: 時間範囲 (1時間, 24時間, 7日, 全て)
    - sortOrder: ソート順序 (最新優先, 最古優先)
- [ ] 初期段階は HTTP 再取得で成立させる
- [ ] その後 `WS /ws/request-history` を追加し、リアルタイム反映に切り替える
- [ ] WebSocket 切断時の再接続戦略を実装する
    - デフォルト: バックオフ付き自動再接続
- [ ] 詳細表示で request body / response body / headers を見せる
- [ ] 履歴クリアやエクスポートの要否を判断する
    - デフォルト: クリアは MVP 対象、エクスポートは後続

### Server 依存

- `GET /api/request-history`
- `DELETE /api/request-history`
- `GET /ws/request-history`

### 完了条件

- filter が実データに対して有効
- 新規 request が UI に反映される
- 切断/失敗時でも UI が壊れない

### テスト観点

- 高頻度 request 時の描画負荷
- 履歴 0 件時
- body が大きい response の表示
- WebSocket 切断と再接続

---

## Phase D5: Home の実機能化

### 目的

Home を Server 状態の集約ダッシュボードとして成立させる。

### 対象 UI

- `HomeScreen`
- `HomeViewModel`

### タスク

- [ ] active devices を実データで表示する
- [ ] current test case を selected device または default context と対応付ける
- [ ] server summary の算出元を決める
    - デフォルト: Server API から集約値を取得
- [ ] 設定画面への導線を接続状態に応じて改善する
- [ ] ローディング / エラー / 空状態を実データに合わせて調整する

### Server 依存

- `GET /api/server/status`
- `GET /api/devices`
- `GET /api/server/summary` または status 拡張

### 完了条件

- Home が mock summary ではなく実際の接続状態を示す
- 接続切れや初期未設定時に適切な案内が出る

### テスト観点

- 初回起動で接続先未設定
- Server 停止中
- device と履歴が増減した場合の反映

---

## Phase D6: UX / 回復性 / 品質向上

### 目的

機能が揃った後に、Desktop ツールとして実用になる品質へ持っていく。

### タスク

- [ ] 共通エラー表示コンポーネントを整備する
- [ ] 空状態・初回状態・ローディング状態を各画面で統一する
- [ ] 長時間処理時の progress 表示を整備する
- [ ] optimistic update の適用箇所を見直す
- [ ] retry 操作を各画面に追加する
- [ ] Preview と実データ実装の乖離を減らす
- [ ] fake repository を使った ViewModel テストを追加する

### 完了条件

- 通信失敗時でも主要画面が復帰できる
- 各画面の状態遷移が統一されている

### テスト観点

- API 500 / timeout / parse error
- 途中で selected device が消えた場合
- 設定変更直後の多画面反映

---

## Phase D7: Desktop 配布と OSS 向け整備

### 目的

OSS 利用者が Desktop アプリを配布物として扱える状態へ近づける。

### タスク

- [ ] version 表示をビルド設定と連動させる
- [ ] package 生成フローを確認する
    - `.dmg`
    - `.msi`
    - `.deb` または Linux 向け代替
- [ ] 初回起動時の設定導線を整備する
- [ ] README に Desktop の使い方を反映できるようスクリーンと用語を固定する
- [ ] 将来の自動更新機能を入れる余地を確認する

### 完了条件

- Desktop 配布を想定した不足項目が洗い出されている
- リリース対象 OS ごとのビルドタスクが整理されている

---

## 実装順の依存関係

1. D0
2. D1
3. D2 と D3
4. D4
5. D5
6. D6
7. D7

## Desktop 側の受け入れ条件

- mock data 依存が主要画面から排除されている
- 接続先設定を変更すると全画面が同じ Server を参照する
- selected device を基準に test case / delay / history が一貫している
- request history が調査用途に十分な情報量で見られる
- エラー時に利用者が次の行動を取れる

## 保留事項とデフォルト判断

- testCase directory の選択主体
    - デフォルト: Server 主体。Desktop は参照表示のみ
- test case の適用単位
    - デフォルト: device 単位を優先、未選択時は default context
- request history のエクスポート
    - デフォルト: MVP から外す
- Desktop から Server 起動管理を行うか
    - デフォルト: まずは行わない
