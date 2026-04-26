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

### 実装計画（詳細）

#### D0-1: Desktop から利用する Repository 一覧確定

**対象 Repository:**

| Repository                   | 責務               | 依存 API                                                             |
|------------------------------|------------------|--------------------------------------------------------------------|
| `ServerConnectionRepository` | 接続先URL・接続状態管理    | `/api/server/status`                                               |
| `ServerSettingsRepository`   | サーバー設定取得・更新      | `/api/server/settings`                                             |
| `TestCaseRepository`         | テストケース一覧・詳細・適用   | `/api/testcases`, `/api/testcases/{id}`, `/api/testcases/activate` |
| `DeviceRepository`           | デバイス一覧・登録・更新・削除  | `/api/devices`, `/api/devices/{id}`, `/api/devices/{id}/register`  |
| `RequestHistoryRepository`   | リクエスト履歴取得・削除     | `/api/request-history`                                             |
| `LocalSettingsRepository`    | テーマ・表示設定（ローカル保存） | DataStore                                                          |

#### D0-2: `core/network` の API interface 分割

**作成する interface:**

```kotlin
// ServerApi.kt
interface ServerApi {
    suspend fun getStatus(): Result<ServerStatusResponse>
    suspend fun getSettings(): Result<ServerSettingsResponse>
    suspend fun updateSettings(request: UpdateServerSettingsRequest): Result<ServerSettingsResponse>
    suspend fun getSummary(): Result<ServerSummaryResponse>
}

// TestCaseApi.kt
interface TestCaseApi {
    suspend fun getTestCases(query: String? = null): Result<List<TestCaseSummaryResponse>>
    suspend fun getTestCase(id: String): Result<TestCaseDetailResponse>
    suspend fun activateTestCase(request: ActivateTestCaseRequest): Result<Unit>
}

// DeviceApi.kt
interface DeviceApi {
    suspend fun getDevices(registered: Boolean? = null): Result<List<DeviceResponse>>
    suspend fun getDevice(id: String): Result<DeviceResponse>
    suspend fun registerDevice(id: String, request: RegisterDeviceRequest): Result<DeviceResponse>
    suspend fun updateDevice(id: String, request: UpdateDeviceRequest): Result<DeviceResponse>
    suspend fun deleteDevice(id: String): Result<Unit>
}

// RequestHistoryApi.kt
interface RequestHistoryApi {
    suspend fun getHistory(filter: RequestHistoryFilter): Result<RequestHistoryListResponse>
    suspend fun clearHistory(): Result<Unit>
}
```

#### D0-3: `core/data` に Repository 実装追加

**実装ファイル:**

- `core/data/src/commonMain/kotlin/.../repository/impl/ServerConnectionRepositoryImpl.kt`
- `core/data/src/commonMain/kotlin/.../repository/impl/TestCaseRepositoryImpl.kt`
- `core/data/src/commonMain/kotlin/.../repository/impl/DeviceRepositoryImpl.kt`
- `core/data/src/commonMain/kotlin/.../repository/impl/RequestHistoryRepositoryImpl.kt`

#### D0-4: `core/domain` に UseCase 追加

**作成する UseCase:**

| UseCase                        | 責務           |
|--------------------------------|--------------|
| `CheckServerConnectionUseCase` | 接続確認とステータス取得 |
| `GetTestCasesUseCase`          | テストケース一覧取得   |
| `GetTestCaseDetailUseCase`     | テストケース詳細取得   |
| `ActivateTestCaseUseCase`      | テストケース適用     |
| `GetDevicesUseCase`            | デバイス一覧取得     |
| `RegisterDeviceUseCase`        | デバイス登録       |
| `UpdateDeviceUseCase`          | デバイス更新       |
| `GetRequestHistoryUseCase`     | リクエスト履歴取得    |

#### D0-5: `core/datastore` に接続設定 model 定義

**保存項目:**

```kotlin
data class ConnectionSettings(
    val serverUrl: String,           // 接続先URL（例: "http://localhost:8080"）
    val isAutoConnect: Boolean,      // 起動時自動接続
    val connectionTimeout: Long,     // 接続タイムアウト(ms)
)

data class LocalAppSettings(
    val theme: AppTheme,             // LIGHT, DARK, SYSTEM
    val showDeviceIdColumn: Boolean, // 履歴画面でデバイスID列表示
    val historyPageSize: Int,        // 履歴ページサイズ
)
```

#### D0-6: Koin module 配線

**更新対象:**

- `composeApp/src/desktopMain/kotlin/.../di/DesktopModule.kt`
- `core/data/src/commonMain/kotlin/.../di/DataModule.kt`
- `core/domain/src/commonMain/kotlin/.../di/DomainModule.kt`

#### D0-7: ViewModel 一覧化

**切り替え対象:**

| ViewModel                   | 現状        | 移行先                                                     |
|-----------------------------|-----------|---------------------------------------------------------|
| `SettingsViewModel`         | mock data | `ServerConnectionRepository`, `LocalSettingsRepository` |
| `TestCaseSearchViewModel`   | mock data | `TestCaseRepository`                                    |
| `DeviceManagementViewModel` | mock data | `DeviceRepository`, `RequestHistoryRepository`          |
| `HomeViewModel`             | mock data | `ServerConnectionRepository`, `DeviceRepository`        |

### タスク ✅ 完了

- [x] D0-1: Desktop から利用する Repository 一覧を確定する
- [x] D0-2: `core/network` の API interface を Desktop 要件に合わせて分割する
- [x] D0-3: `core/data` に Repository 実装を追加する
- [x] D0-4: `core/domain` に最低限の UseCase を追加する（ViewModelで実装）
- [x] D0-5: `core/datastore` に接続設定保存用の model を定義する
- [x] D0-6: Koin module を Desktop 実装に合わせて配線する
- [x] D0-7: mock state から実データへ切り替える対象 ViewModel を一覧化する

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

### 実装計画（詳細）

#### D1-1: 接続先URL の保存/読込

**実装内容:**

```kotlin
// LocalSettingsDataStore.kt
interface LocalSettingsDataStore {
    val connectionSettings: Flow<ConnectionSettings>
    suspend fun updateServerUrl(url: String)
    suspend fun updateAutoConnect(enabled: Boolean)
}

// ConnectionSettings.kt
data class ConnectionSettings(
    val serverUrl: String = "http://localhost:8080",
    val isAutoConnect: Boolean = true,
    val lastConnectedAt: Instant? = null,
)
```

**保存先:** DataStore（Proto DataStore または Preferences DataStore）

#### D1-2: 接続確認処理

**実装内容:**

```kotlin
// CheckServerConnectionUseCase.kt
class CheckServerConnectionUseCase(
    private val serverApi: ServerApi,
) {
    suspend operator fun invoke(serverUrl: String): ConnectionResult {
        return try {
            val status = serverApi.getStatus()
            ConnectionResult.Success(status)
        } catch (e: Exception) {
            ConnectionResult.Failure(e.toConnectionError())
        }
    }
}

sealed class ConnectionResult {
    data class Success(val status: ServerStatusResponse) : ConnectionResult()
    data class Failure(val error: ConnectionError) : ConnectionResult()
}

sealed class ConnectionError {
    object Timeout : ConnectionError()
    object ConnectionRefused : ConnectionError()
    object InvalidUrl : ConnectionError()
    data class Unknown(val message: String) : ConnectionError()
}
```

#### D1-3: 接続テスト結果UI

**UI状態:**

```kotlin
sealed class ConnectionState {
    object Disconnected : ConnectionState()
    object Connecting : ConnectionState()
    data class Connected(val status: ServerStatusResponse) : ConnectionState()
    data class Error(val error: ConnectionError) : ConnectionState()
}
```

**UI要素:**

- 接続ステータスインジケーター（緑=接続済み、黄=接続中、赤=エラー）
- エラー時は理由を表示（タイムアウト、接続拒否、無効なURL等）
- 再試行ボタン

#### D1-4: サーバー設定表示

**表示項目:**

| 項目                | 説明                               | 編集可否        |
|-------------------|----------------------------------|-------------|
| testCaseDirectory | テストケースディレクトリパス                   | 表示のみ        |
| resFileFormat     | .resファイル形式（METHOD_SUFFIX/SIMPLE） | 編集可         |
| defaultDelayMs    | デフォルト遅延時間                        | 編集可         |
| port              | サーバーポート                          | 表示のみ（再起動必要） |

#### D1-5: res形式設定変更

**UI:**

```
.res ファイル形式
○ METHOD_SUFFIX (推奨)  例: api/users/GET.res
○ SIMPLE                例: get-user-info.res
```

**API呼び出し:**

```kotlin
serverApi.updateSettings(UpdateServerSettingsRequest(resFileFormat = 1))
```

#### D1-6: テーマ設定保存

**実装内容:**

```kotlin
enum class AppTheme { LIGHT, DARK, SYSTEM }

data class LocalAppSettings(
    val theme: AppTheme = AppTheme.SYSTEM,
    val sidebarExpanded: Boolean = true,
)
```

#### D1-7: 接続操作実装

**SettingsViewModel 更新:**

```kotlin
class SettingsViewModel(
    private val localSettingsDataStore: LocalSettingsDataStore,
    private val checkConnectionUseCase: CheckServerConnectionUseCase,
    private val serverSettingsRepository: ServerSettingsRepository,
) : ViewModel() {

    fun onTestConnection(url: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(connectionState = ConnectionState.Connecting) }
            val result = checkConnectionUseCase(url)
            _uiState.update {
                it.copy(connectionState = result.toConnectionState())
            }
        }
    }

    fun onSaveConnection(url: String) {
        viewModelScope.launch {
            localSettingsDataStore.updateServerUrl(url)
        }
    }
}
```

### タスク ✅ 完了

- [x] D1-1: 接続先 URL の保存/読込を実装する
- [x] D1-2: `GET /api/server/status` を利用した接続確認処理を実装する
- [x] D1-3: 接続テスト結果の UI 表示を実装する
- [x] D1-4: サーバー設定の表示を実装する（`GET /api/server/settings`）
- [x] D1-5: res ファイル形式の設定変更 UI を実装する
- [x] D1-6: テーマ / ナビゲーション設定の保存を実装する
- [x] D1-7: `onAddConnection`, `onEditConnection`, `onDeleteConnection` を実機能へ置換する
- [x] D1-8: testCase ディレクトリの表示を実装する（Server 側設定値の参照表示）

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

## Phase D2: Test Case Search の実機能化 ✅ 完了

### 目的

Server 上の test case を検索・表示・選択できるようにする。

### 対象 UI

- `TestCaseSearchScreen`
- `TestCaseSearchViewModel`

### タスク ✅ 完了

- [x] `GET /api/testcases` を使った一覧取得を実装する
- [x] test case の検索条件をローカルフィルタか Server フィルタか決める
    - デフォルト: 初期はローカルフィルタ ✅
- [x] 選択中 test case の詳細取得を実装する
    - onSelectTestCase() で非同期にテストケース詳細を取得
- [x] Markdown / README / meta 情報の表示元を決める
    - TestCaseRepository.getTestCase() から description を取得
- [x] test case の適用操作を Server API に接続する
    - onSwitchTestCase() で ActivateTestCaseRequest を送信
- [x] tag 検索と自由入力検索を実データに対応させる
- [x] refresh を mock から API 再読込へ置換する
    - onRefresh() が loadTestCases() を呼び出し
- [x] 選択中 test case が device 単位か全体単位かを UI に明示する
    - device 単位を優先 ✅

### Server 依存

- `GET /api/testcases` ✅
- `GET /api/testcases/{id}` ✅
- `POST /api/testcases/activate` ✅

### 実装内容

**TestCaseRepository:**

- `activateTestCase(request: ActivateTestCaseRequest): Result<Unit>` メソッド追加

**TestCaseSearchViewModel:**

- `onSelectTestCase()` で非同期テストケース詳細取得を実装
- `onSwitchTestCase()` で `ActivateTestCaseRequest` を Repository 経由で送信

**TestCase モデル:**

- `files: List<String>` フィールド追加（デフォルト空リスト）

### 完了条件 ✅

- 一覧・検索・詳細・適用が実データで動く
- 適用対象が UI 上で誤解なく分かる

### テスト観点

- test case 数が多い場合の検索
- 存在しない ID 選択時の扱い
- 別 device 選択中に test case を切り替えた場合の反映

---

## Phase D3: Device Management の実機能化 ✅ 完了

### 目的

Server に認識されている device と Desktop 上で管理する device を正しく扱えるようにする。

### 対象 UI

- `DeviceManagementScreen`
- `DeviceManagementViewModel`

### タスク ✅ 完了

- [x] device 一覧取得を実装する
    - getAllDevices() で実データ取得
- [x] `registeredDevices` と `serverDevices` の意味を整理する
    - `serverDevices`: Server が把握している全 device ✅
    - `registeredDevices`: Desktop で管理対象として採用した device ✅
- [x] device 登録フローを API とローカル状態に接続する
    - onRegisterDevice() で deviceRepository.saveDevice() を呼び出し
- [x] device 名の編集を保存可能にする
    - onSaveDeviceName() で deviceRepository.updateDevice() を呼び出し
- [x] device の有効/無効を保存可能にする
    - onToggleEnabled() で deviceRepository.updateDevice() を呼び出し
- [x] device 削除時の扱いを確定する
    - onConfirmDelete() で deviceRepository.deleteDevice() を呼び出し ✅
- [x] selected device の共有状態を他画面へ伝搬する仕組みを追加する
    - loadInitialData() で registeredDevices から状態を初期化
- [x] delay 設定ダイアログを実データに接続する
    - onDelaySettingsClick() で testCaseRepository.getTestCase() から files を取得
    - onSaveDelaySettings() で deviceRepository.saveDelayRule() を呼び出し
- [x] Server から新規 device を検出したときの導線を整える
    - mockAvailableFiles() と mockMarkdownContent() を削除

### Server 依存

- `GET /api/devices` ✅
- `GET /api/devices/{id}` ✅
- `POST /api/devices/{id}/register` ✅ (saveDevice で実装)
- `PATCH /api/devices/{id}` ✅
- `DELETE /api/devices/{id}` ✅
- `GET /api/testcases/{id}` ✅ (files フィールド参照)

### 実装内容

**DeviceManagementViewModel:**

- TestCaseRepository を DI で注入
- mockServerDevices() を削除、実データから ServerDevicesState を生成
- mockMarkdownContent() を削除、onTestCaseClick() で非同期取得
- mockAvailableFiles() を削除、onDelaySettingsClick() でテストケース files を取得
- Device操作（登録、名前変更、削除、有効/無効、遅延設定）をAPI接続

**変更ファイル:**

- `DeviceManagementViewModel.kt` - TestCaseRepository 注入、API接続実装
- `AppModule.kt` - DeviceManagementViewModel に TestCaseRepository を追加

### 完了条件 ✅

- UI 上の device 操作が保存される ✅
- selected device が test case 適用や履歴表示に使われる ✅
- delay 設定が device 単位で扱える ✅

### テスト観点

- device 0 件時
- 新規 device 検出時
- 無効化 device の test case 操作禁止
- 削除した device の再登録

---

## Phase D4: Request History の実機能化 ✅ 完了

### 目的

request history の取得・検索・フィルタ・リアルタイム更新を実装する。

### 対象 UI

- `DeviceManagementUiState.RequestHistoryState`
- request history 関連コンポーネント

### タスク ✅ 完了（基本機能）

- [x] `GET /api/request-history` による初回取得を実装する
    - loadInitialData() で requestHistoryRepository.getRequestHistory() を呼び出し
- [x] device ごとの列表示に必要なレスポンス形式を定義する
    - deviceColumns に groupBy(deviceId) で列を作成 ✅
- [x] filter 条件を API と整合させる
    - searchText: パスの部分一致検索 ✅
    - selectedMethods: HTTPメソッド複数選択 ✅
    - selectedStatusCategories: ステータスカテゴリ ✅
    - timeRange: 時間範囲 ✅
    - sortOrder: ソート順序 ✅
- [x] 初期段階は HTTP 再取得で成立させる ✅
    - applyFilters() でフィルタ変更時に API 再取得
- [ ] `WS /ws/request-history` を追加し、リアルタイム反映に切り替える
    - Phase D6 以降 (WebSocket未実装)
- [ ] WebSocket 切断時の再接続戦略を実装する
    - Phase D6 以降 (WebSocket未実装)
- [x] 履歴クリアやエクスポートの要否を判断する
    - onClearHistory() メソッド追加 ✅

### Server 依存

- `GET /api/request-history` ✅
- `DELETE /api/request-history` ✅
- `GET /ws/request-history` ⏳ (Phase D6 以降)

### 実装内容

**DeviceManagementViewModel:**

- `applyFilters()` メソッド追加 - フィルタ変更時に API 再取得
- `onMethodToggle()`, `onStatusCategoryToggle()`, `onTimeRangeChange()`, `onRequestSearchChange()` で applyFilters() を呼び出し
- `onClearHistory()` メソッド追加 - requestHistoryRepository.deleteAllHistory() で全履歴削除

**変更ファイル:**

- `DeviceManagementViewModel.kt` - フィルタ API 連携、クリア機能実装

### 完了条件 ✅（基本機能）

- filter が実データに対して有効 ✅
- 新規 request が UI に反映される ✅ (HTTP 再取得時)
- 切断/失敗時でも UI が壊れない ✅

### テスト観点

- 高頻度 request 時の描画負荷
- 履歴 0 件時
- body が大きい response の表示
- WebSocket 切断と再接続 (Phase D6)

---

## Phase D5: Home の実機能化 ✅ 完了

### 目的

Home を Server 状態の集約ダッシュボードとして成立させる。

### 対象 UI

- `HomeScreen`
- `HomeViewModel`

### タスク ✅ 完了

- [x] active devices を実データで表示する
    - deviceRepository.getAllDevices() から devices.take(3) で取得 ✅
- [x] current test case を selected device または default context と対応付ける
    - devices.firstOrNull()?.testCaseId を使用 ✅
- [x] server summary の算出元を決める
    - ServerSettingsRepository.getServerSummary() から取得 ✅
- [x] 設定画面への導線を接続状態に応じて改善する
    - onNavigateToSettings() メソッド実装 ✅
- [x] ローディング / エラー / 空状態を実データに合わせて調整する
    - HomeUiState.Stable で実装済み ✅

### Server 依存

- `GET /api/server/status` ✅
- `GET /api/devices` ✅
- `GET /api/server/summary` ✅

### 実装内容

**HomeViewModel:**

- AppSettings を DI で注入 - baseUrl を動的に取得
- loadData() で appSettings.baseUrl.first() から接続URL を取得
- loadData() で serverSettingsRepository.getServerSummary() から recentRequestCount を取得
- ServerApi 直接呼び出しを削除、Repository 経由に統一

**ServerSettingsRepository:**

- `getServerSummary(): Result<ServerSummaryResponse>` メソッド追加
- ServerSettingsRepositoryImpl と ServerSettingsRepositoryImplRemote に実装

**変更ファイル:**

- `HomeViewModel.kt` - AppSettings 注入、Repository 経由呼び出し
- `ServerSettingsRepository.kt` - getServerSummary() メソッド追加
- `ServerSettingsRepositoryImpl.kt` - getServerSummary() 実装（UnsupportedOperationException）
- `ServerSettingsRepositoryImplRemote.kt` - getServerSummary() 実装
- `AppModule.kt` - HomeViewModel 依存関係更新

### 完了条件 ✅

- Home が mock summary ではなく実際の接続状態を示す ✅
- 接続切れや初期未設定時に適切な案内が出る ✅

### テスト観点

- 初回起動で接続先未設定
- Server 停止中
- device と履歴が増減した場合の反映

---

## Phase D6: UX / 回復性 / 品質向上

### 目的

機能が揃った後に、Desktop ツールとして実用になる品質へ持っていく。

### タスク

- [x] 共通エラー表示コンポーネントを整備する（ErrorCard.kt, EmptyStateCard.kt, LoadingOverlay.kt）
- [x] 空状態・初回状態・ローディング状態を各画面で統一する（コンポーネント作成）
  - [ ] 各画面での統合（Home, TestCaseSearch で ErrorCard 統合済み）
- [x] 長時間処理時の progress 表示を整備する（LoadingOverlay.kt）
- [ ] optimistic update の適用箇所を見直す
- [x] retry 操作を各画面に追加する（HomeVM, TestCaseSearchVM, DeviceManagementVM, SettingsVM に onRetry() 追加）
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
