# Mock Station

This is a Kotlin Multiplatform project targeting Desktop (JVM), Server.

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:

- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### Build and Run Server

To build and run the development version of the server, use the run configuration from the run widget
in your IDE's toolbar or run it directly from the terminal:

- on macOS/Linux
  ```shell
  ./gradlew :server:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :server:run
  ```

### Verify Server Implementation

After starting the server, you can verify all implemented APIs using the verification script:

- on macOS/Linux
  ```shell
  ./scripts/verify-server.sh
  ```

This script will test:

- **Phase S0-S1**: Server status, test case loading
- **Phase S2**: Mock responses
- **Phase S3**: Device identification
- **Phase S4**: Management APIs (Device CRUD, Server settings)
- **Phase S5**: Request history recording and search

For detailed test cases and expected results, see `IMPLEMENTATION_SUMMARY.md` (section "動作確認チェックリスト").

### Known Issues

**Server Startup: Address Already in Use**

If you encounter `java.net.BindException: Address already in use`, try:

```shell
# Stop Gradle daemon and restart
./gradlew --stop
./gradlew :server:run

# Or run on a different port
PORT=9091 ./gradlew :server:run
```

For more details, see `TASK_SERVER.md` (section "既知の問題と対応").
