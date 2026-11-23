# Banking Account Screen – ViewModel & SavedStateHandle

## 1. Project Overview

This project implements a Banking Account Screen using **Kotlin and Jetpack Compose** in Android Studio.

The application displays the user's account number and allows users to select an account type, such as Savings Account, Current Account, or Salary Account.

The main objective is to demonstrate how **ViewModel and SavedStateHandle** manage and restore UI state when the device is rotated or the Activity is recreated.

The application also provides a responsive layout that automatically adjusts between portrait and landscape orientations.

## 2. Problem Statement

In a banking application, users should be able to view their account information and select an account type. When the device is rotated or the Activity is recreated, the previously selected account type should be restored automatically.

To achieve this, the application uses ViewModel and SavedStateHandle to store the selected account type, expose it as UI state, and restore the previous selection after recreation.

## 3. Objectives

- Implement an Account Screen using Jetpack Compose.
- Display the user's banking account number.
- Provide multiple account type options.
- Allow users to select an account type using RadioButtons.
- Manage UI-related data using ViewModel.
- Save and restore selected values using SavedStateHandle.
- Expose UI state using StateFlow.
- Maintain the selected account type after device rotation.
- Support portrait and landscape orientations.

## 4. Technologies Used

| Technology | Purpose |
|---|---|
| Kotlin | Programming language |
| Android Studio | Development environment |
| Jetpack Compose | Building the user interface |
| Material 3 | UI components and styling |
| ViewModel | Managing UI state |
| SavedStateHandle | Saving and restoring selected values |
| StateFlow | Exposing observable UI state |
| Kotlin Coroutines | Managing asynchronous state updates |

## 5. Application Features

### 5.1 Account Information
- Displays the banking account number.
- Shows the account details section.

### 5.2 Account Type Selection

Users can select one of the following account types:

- Savings Account
- Current Account
- Salary Account

RadioButtons are used to select the account type.

### 5.3 ViewModel Implementation

The `AccountViewModel` class manages account-related UI state.

- Handles account type selection.
- Updates the selected account type.
- Maintains separation between UI and state management.

### 5.4 SavedStateHandle Implementation

`SavedStateHandle` stores the selected account type using the key `account_type`.

Whenever the user selects another account type, the ViewModel updates the saved value.

### 5.5 StateFlow Implementation

`StateFlow` exposes the `AccountUiState` to the user interface.

The Compose screen observes the state and automatically updates whenever the selected account type changes.

### 5.6 Screen Rotation Support

The application supports both portrait and landscape orientations.

- **Portrait Mode:** Account details and account selection are displayed vertically using a Column.
- **Landscape Mode:** Account details and account selection are displayed horizontally using a Row.
- The selected account type remains unchanged after rotation.

## 6. Project Architecture

The application follows the **MVVM (Model-View-ViewModel)** architecture.

```text
             BankingAccountApp
                     |
                     |
                MainActivity
                     |
                     |
                AccountScreen
                     |
                     |
              AccountViewModel
                     |
                     |
               SavedStateHandle
                     |
                     |
                  StateFlow
                     |
                     |
               AccountUiState
                     |
                     |
               Updated UI Screen
```

### Components Description

**MainActivity**
- Acts as the main entry point of the application.
- Initializes the Compose UI.
- Obtains the AccountViewModel.

**AccountScreen**
- Displays account details.
- Provides account type selection.
- Observes UI state using `collectAsStateWithLifecycle()`.
- Adjusts its layout according to screen orientation.

**AccountViewModel**
- Manages account-related data.
- Handles account type selection.
- Updates SavedStateHandle.
- Exposes UI state through StateFlow.

**SavedStateHandle**
- Stores the selected account type.
- Restores the previously saved value after eligible Activity recreation.

**AccountUiState**
- Contains the account number.
- Contains the currently selected account type.

## 7. Working of the Application

The application works through the following steps:

1. When the application starts, Savings Account is selected by default.
2. The user selects another account type using the RadioButtons.
3. The selected value is passed to the ViewModel.
4. The ViewModel stores the selected value inside SavedStateHandle.
5. StateFlow updates the AccountUiState.
6. The Compose UI observes the updated state and displays the selected account type.
7. When the device is rotated, Android recreates the Activity.
8. The existing ViewModel is retained during a normal configuration change, while SavedStateHandle provides saved state when the system recreates the Activity.
9. The screen displays the previously selected account type automatically.

## 8. Screen Rotation Implementation

The application detects the current screen orientation using `LocalConfiguration`.

```kotlin
val configuration = LocalConfiguration.current

val isLandscape =
    configuration.screenWidthDp >
    configuration.screenHeightDp
```

Based on the orientation:

- Portrait mode uses a vertical `Column` layout.
- Landscape mode uses a horizontal `Row` layout.

This makes the application responsive and helps display account information properly across different screen orientations.

## 9. Important Code Implementation

### 9.1 Saving the Selected Account Type

```kotlin
fun onAccountTypeSelected(type: String) {
    savedStateHandle[KEY_ACCOUNT_TYPE] = type
}
```

This function updates the selected account type in SavedStateHandle whenever the user changes the selection.

### 9.2 Retrieving the Saved Account Type

```kotlin
private val selectedAccountType =
    savedStateHandle.getStateFlow(
        KEY_ACCOUNT_TYPE,
        "Savings"
    )
```

The `getStateFlow()` function provides the saved value. If no value exists, it uses Savings as the default selection.

### 9.3 Exposing UI State

```kotlin
val uiState: StateFlow<AccountUiState> =
    selectedAccountType
        .map { type ->
            AccountUiState(
                accountNumber = "123456789012",
                selectedAccountType = type
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AccountUiState()
        )
```

This converts the selected account type into an observable UI state.

### 9.4 Observing UI State in Compose

```kotlin
val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()
```

The Compose screen collects the StateFlow and automatically recomposes whenever the UI state changes.

## 10. How to Run the Application

Follow these steps to execute the project:

1. Open Android Studio.
2. Create a new Empty Activity project using Kotlin and Jetpack Compose.
3. Open the `MainActivity.kt` file.
4. Add the complete application code.
5. Add the required dependencies in `app/build.gradle.kts`.
6. Sync the Gradle files.
7. Start an Android Emulator or connect a physical Android device.
8. Click the **Run** button to execute the application.

## 11. Testing Procedure

The following test cases can be used to verify the application's functionality.

| Test Case | Expected Result |
|---|---|
| Launch application | Savings Account selected by default |
| Select Current Account | Current Account becomes selected |
| Select Salary Account | Salary Account becomes selected |
| Rotate device | Screen adjusts to landscape |
| Check selected value after rotation | Previously selected value remains selected |
| Rotate back to portrait | Layout returns to vertical orientation |
| Recreate Activity through a supported system configuration change | Previously selected account type is restored |

## 12. Expected Output

The application displays:

- Banking Account Screen.
- Account number.
- Three account type options.
- RadioButtons for account selection.
- Currently selected account type.
- Responsive portrait and landscape layouts.

The selected account type remains available after device rotation and supported Activity recreation.

## 13. Advantages

- Simple and user-friendly interface.
- Proper separation of UI and business logic.
- Efficient UI state management.
- State restoration using SavedStateHandle.
- Responsive design for different orientations.
- Uses modern Android development practices.

## 14. Limitations

- The account number is currently sample data.
- The application does not connect to an actual banking server.
- No real banking transactions are performed.
- SavedStateHandle is intended for small UI state values and does not replace permanent database storage.
- State restoration depends on Android's saved-state mechanism and is not intended to guarantee persistence after every possible app termination scenario.

## 15. Conclusion

This project successfully demonstrates the implementation of a Banking Account Screen using Kotlin, Jetpack Compose, ViewModel, StateFlow, and SavedStateHandle.

The application allows users to select different account types and automatically restores the previously selected value after device rotation or supported Activity recreation.

It demonstrates effective UI state management, lifecycle awareness, MVVM architecture, and responsive Android application development.

---

## Project Information

| Detail | Description |
|---|---|
| Project Title | Banking Account Screen Using ViewModel and SavedStateHandle |
| Platform | Android |
| Language | Kotlin |
| IDE | Android Studio |
| UI Framework | Jetpack Compose |
| Architecture | MVVM |
| State Management | ViewModel, StateFlow, SavedStateHandle |
