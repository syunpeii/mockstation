package com.github.syunpeii.mockstation.app.ui.testcasesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.syunpeii.mockstation.app.ui.testcasesearch.model.TestCaseDisplay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestCaseSearchViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<TestCaseSearchUiState>(TestCaseSearchUiState.Loading)
    val uiState: StateFlow<TestCaseSearchUiState> = _uiState.asStateFlow()
    private var allTestCases: List<TestCaseDisplay> = emptyList()

    init {
        loadInitialData()
    }

    fun onSearchInputChange(text: String) {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val filteredTestCases = filterTestCases(currentState.searchTags, text)
            _uiState.value = currentState.copy(
                currentInput = text,
                testCases = filteredTestCases,
            )
        }
    }

    fun onAddTag() {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val newTag = currentState.currentInput.trim()
            if (newTag.isNotEmpty()) {
                onAddTag(newTag)
            }
        }
    }

    fun onRemoveTag(tag: String) {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val newTags = currentState.searchTags - tag
            val filteredTestCases = filterTestCases(newTags, currentState.currentInput)
            _uiState.value = currentState.copy(
                searchTags = newTags,
                testCases = filteredTestCases,
            )
        }
    }

    fun onClearAllTags() {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val filteredTestCases = filterTestCases(emptyList(), currentState.currentInput)
            _uiState.value = currentState.copy(
                searchTags = emptyList(),
                testCases = filteredTestCases,
            )
        }
    }

    fun onSelectTestCase(id: String) {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            if (currentState.selectedTestCaseId == id) {
                _uiState.value = currentState.copy(selectedTestCaseId = null)
            } else {
                _uiState.value = currentState.copy(selectedTestCaseId = id)
            }
        }
    }

    fun onDeselectTestCase() {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            _uiState.value = currentState.copy(selectedTestCaseId = null)
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _uiState.value = TestCaseSearchUiState.Loading
            delay(1_000)
            allTestCases = mockTestCases()
            _uiState.value = TestCaseSearchUiState.Stable(
                testCases = allTestCases,
                searchTags = emptyList(),
                selectedTestCaseId = null,
                currentInput = "",
            )
        }
    }

    fun onSwitchTestCase(id: String) {
        println("Switching to test case: $id")
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            delay(1_000)
            allTestCases = mockTestCases()
            _uiState.value = TestCaseSearchUiState.Stable(
                testCases = allTestCases,
                searchTags = emptyList(),
                selectedTestCaseId = null,
                currentInput = "",
            )
        }
    }

    private fun filterTestCases(
        searchTags: List<String>,
        currentInput: String,
    ): List<TestCaseDisplay> {
        val filterTerms = buildList {
            addAll(searchTags)
            val trimmedInput = currentInput.trim()
            if (trimmedInput.isNotEmpty()) {
                add(trimmedInput)
            }
        }
        if (filterTerms.isEmpty()) {
            return allTestCases
        }

        return allTestCases.filter { testCase ->
            filterTerms.all { term ->
                testCase.id.contains(term, ignoreCase = true) ||
                    testCase.description.contains(term, ignoreCase = true) ||
                    testCase.tags.any { it.contains(term, ignoreCase = true) }
            }
        }
    }

    private fun onAddTag(tag: String) {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val newTags = (currentState.searchTags + tag).distinct()
            val filteredTestCases = filterTestCases(newTags, "")
            _uiState.value = currentState.copy(
                currentInput = "",
                searchTags = newTags,
                testCases = filteredTestCases,
            )
        }
    }

    private fun mockTestCases() = listOf(
        TestCaseDisplay(
            id = "TC-001",
            description = "User login with valid credentials",
            tags = listOf("login", "authentication", "user"),
            content = """
                # Test Case: User Login

                ## Objective
                Verify that users can successfully log in with valid credentials.

                ## Prerequisites
                - User account exists in the system
                - Valid username and password are known

                ## Test Steps
                1. Navigate to the login page
                2. Enter valid username in the username field
                3. Enter valid password in the password field
                4. Click the "Login" button

                ## Expected Result
                - User should be successfully authenticated
                - User should be redirected to the dashboard page
                - Welcome message should be displayed

                ## Test Data
                - Username: test_user@example.com
                - Password: Test123!

                ## Notes
                This is a critical path test case for user authentication.
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-002",
            description = "Device registration flow",
            tags = listOf("device", "registration"),
            content = """
                # Test Case: Device Registration

                ## Objective
                Verify that new devices can be successfully registered in the system.

                ## Prerequisites
                - User is logged in
                - Device ID is available from the server

                ## Test Steps
                1. Open Device Management page
                2. Navigate to "Server Device List" tab
                3. Find the target device in the list
                4. Click the "Register" button
                5. Verify the device appears in "Registered Devices" tab

                ## Expected Result
                - Device should be added to the registered devices list
                - Device should have default settings applied
                - Device should be in enabled state

                ## Notes
                Device can be deleted from the registered list if needed.
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-003",
            description = "API request filtering by HTTP method",
            tags = listOf("api", "filter", "http"),
            content = """
                # Test Case: API Request Filtering

                ## Objective
                Verify that API request history can be filtered by HTTP method.

                ## Prerequisites
                - At least one device is enabled
                - Multiple API requests with different methods exist

                ## Test Steps
                1. Navigate to "API Request History" tab
                2. Click "Advanced Filters"
                3. Select specific HTTP methods (e.g., GET, POST)
                4. Verify only requests with selected methods are displayed

                ## Expected Result
                - Filter should apply immediately
                - Only matching requests should be visible
                - Request count should update accordingly

                ## Test Data
                - Test with GET, POST, PUT, DELETE methods
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-004",
            description = "Delay settings configuration",
            tags = listOf("delay", "settings", "configuration"),
            content = """
                # Test Case: Delay Settings

                ## Objective
                Verify that delay settings can be configured for registered devices.

                ## Prerequisites
                - Device is registered
                - Test case files are available

                ## Test Steps
                1. Click "Delay Settings" on a registered device
                2. Select delay type (Off, Preset, Custom)
                3. Configure delay time (if applicable)
                4. Select target files
                5. Enable delay
                6. Save settings

                ## Expected Result
                - Settings should be saved successfully
                - Delay should be applied to API responses
                - Target files should be affected

                ## Test Data
                - Preset delay: 5000ms
                - Custom delay: 10000ms
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-005",
            description = "Test case content rendering",
            tags = listOf("testcase", "markdown", "display"),
            content = """
                # Test Case: Markdown Rendering

                ## Objective
                Verify that test case content is properly rendered with markdown formatting.

                ## Features to Test
                - **Bold text**
                - *Italic text*
                - Headers (H1, H2, H3)
                - Lists (ordered and unordered)
                - Code blocks

                ## Code Example
                ```kotlin
                fun main() {
                    println("Hello, World!")
                }
                ```

                ## Expected Result
                All markdown elements should be properly rendered.
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-006",
            description = "Search functionality with multiple tags",
            tags = listOf("search", "filter", "tags"),
            content = """
                # Test Case: Multi-Tag Search

                ## Objective
                Verify that search functionality works with multiple tags using AND logic.

                ## Test Steps
                1. Enter first search tag and press Space or Enter
                2. Enter second search tag and press Space or Enter
                3. Verify only test cases matching ALL tags are shown
                4. Remove one tag
                5. Verify results update immediately

                ## Expected Result
                - AND logic should be applied
                - Results should update in real-time
                - Empty state should be shown when no matches
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-007",
            description = "Responsive layout on window resize",
            tags = listOf("ui", "responsive", "layout"),
            content = """
                # Test Case: Responsive Layout

                ## Objective
                Verify that the UI adapts correctly to different window sizes.

                ## Test Steps
                1. Start with Expanded layout (wide window)
                2. Verify 2-column layout (list + detail panel)
                3. Resize window to Compact size
                4. Verify layout switches to single column
                5. Select a test case
                6. Verify detail shown in dialog

                ## Expected Result
                - Layout should adapt smoothly
                - No content should be lost
                - User experience should remain intuitive
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-008",
            description = "Error handling for failed requests",
            tags = listOf("error", "handling", "api"),
            content = """
                # Test Case: Error Handling

                ## Objective
                Verify that errors are properly handled and displayed to users.

                ## Test Scenarios
                1. Network timeout
                2. Server error (500)
                3. Invalid response format
                4. Authentication failure

                ## Expected Result
                - Clear error messages should be displayed
                - User should be able to retry
                - Application should not crash
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-009",
            description = "Test case switching functionality",
            tags = listOf("testcase", "switch", "api"),
            content = """
                # Test Case: Test Case Switching

                ## Objective
                Verify that active test case can be switched from the search screen.

                ## Prerequisites
                - Multiple test cases are available
                - User has appropriate permissions

                ## Test Steps
                1. Search for a test case
                2. Select it from the list
                3. Click "Switch to this test case" button
                4. Verify confirmation or success message

                ## Expected Result
                - Test case should be activated
                - All devices should use the new test case
                - API responses should reflect the change
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-010",
            description = "Localization support verification",
            tags = listOf("i18n", "localization", "language"),
            content = """
                # Test Case: Localization

                ## Objective
                Verify that the application properly supports multiple languages.

                ## Supported Languages
                - English (default)
                - Japanese (日本語)

                ## Test Steps
                1. Verify all UI text is externalized
                2. Switch system language
                3. Verify all text updates correctly
                4. Check for missing translations

                ## Expected Result
                - No hardcoded strings
                - All text should be translated
                - Formatting should be appropriate for each language
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-011",
            description = "Performance test with large dataset",
            tags = listOf("performance", "scalability", "test"),
            content = """
                # Test Case: Performance Testing

                ## Objective
                Verify application performance with large number of test cases.

                ## Test Setup
                - Load 100+ test cases
                - Apply multiple filters
                - Scroll through results

                ## Metrics to Measure
                - Initial load time
                - Filter response time
                - Scroll smoothness
                - Memory usage

                ## Expected Result
                - Load time < 2 seconds
                - Filter response < 500ms
                - Smooth 60fps scrolling
            """.trimIndent(),
        ),
        TestCaseDisplay(
            id = "TC-012",
            description = "Keyboard navigation accessibility",
            tags = listOf("accessibility", "keyboard", "navigation"),
            content = """
                # Test Case: Keyboard Accessibility

                ## Objective
                Verify that all functionality is accessible via keyboard.

                ## Keyboard Shortcuts to Test
                - Tab: Navigate between elements
                - Enter: Activate buttons/links
                - Space: Toggle selections
                - Escape: Close dialogs/panels

                ## Expected Result
                - All interactive elements should be keyboard accessible
                - Focus should be clearly visible
                - Logical tab order should be maintained
            """.trimIndent(),
        ),
    )
}
