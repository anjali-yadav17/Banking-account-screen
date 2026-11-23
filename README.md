# Banking Account Screen – ViewModel & SavedStateHandle

## 1. Project Overview

This project implements a Banking Account Screen using **Kotlin and Jetpack Compose** in Android Studio.

The application displays the user's account number and allows the user to select an account type such as Savings Account, Current Account, or Salary Account.

The main purpose of this application is to demonstrate how **ViewModel and SavedStateHandle** are used to manage and restore UI state when the device is rotated or the Activity is recreated.

The application also provides a responsive layout that adjusts automatically between portrait and landscape orientations.

## 2. Problem Statement

Suppose a banking application displays the user's account number and selected account type. When the user rotates the device or the application is recreated, the selected account type should be restored automatically.

The application must use ViewModel and SavedStateHandle to store the selected account type, expose it as UI state, handle user changes through the ViewModel, and restore the previously selected value after recreation.

## 3. Objectives

- Implement an Account Screen using Jetpack Compose.
- Display the user's account number.
- Provide multiple account type options.
- Allow users to select an account type using RadioButtons.
- Use ViewModel to manage UI-related data.
- Use SavedStateHandle to save and restore the selected account type.
- Expose the selected account type as UI state using StateFlow.
- Maintain the selected value after device rotation.
- Support both portrait and landscape screen orientations.

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
| Coroutines | Managing asynchronous state updates |

## 5. Application Features

### 5.1 Account Information
- Displays the banking account number.
- Shows the account details section.

### 5.2 Account Type Selection

The user can select one of the following account types:

- Savings Account
- Current Account
- Salary Account

RadioButtons are used to select the account type.

### 5.3 ViewModel Implementation

The AccountViewModel class manages the account-related UI state.

It handles user selections and updates the selected account type.

### 5.4 SavedStateHandle Implementation

SavedStateHandle stores the selected account type using a key named `account_type`.

Whenever the user selects another account type, the ViewModel updates the saved value.

### 5.5 StateFlow Implementation

StateFlow exposes the AccountUiState to the user interface.

The Compose screen observes the state and automatically updates whenever the selected account type changes.

### 5.6 Screen Rotation Support

The application supports both portrait and landscape orientations.

- In portrait mode, account details and account selection are displayed vertically.
- In landscape mode, account details and account selection are displayed horizontally.
- The selected account type remains unchanged after rotation.

## 6. Project Architecture

The application follows a simple MVVM architecture.

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
- Observes UI state using collectAsStateWithLifecycle().
- Adjusts its layout according to screen orientation.

**AccountViewModel**

- Manages account-related data.
- Handles account type selection.
- Updates SavedStateHandle.
- Exposes UI state through StateFlow.

**SavedStateHandle**

- Stores the selected account type.
- Provides the previously saved value after eligible recreation.

**AccountUiState**

- Contains the account number.
- Contains the currently selected account type.

## 7. Working of the Application

The application works through the following steps:

1. When the application starts, the Savings Account is selected by default.
2. The user can select another account type using the RadioButtons.
3. The selected value is passed to the ViewModel.
4. The ViewModel stores the selected value inside SavedStateHandle.
5. StateFlow updates the AccountUiState.
6. The Compose UI observes the updated state and displays the selected account type.
7. When the device is rotated, Android recreates the Activity.
8. The ViewModel and SavedStateHandle restore the selected account type.
9. The screen displays the previously selected account type automatically.

## 8. Screen Rotation Implementation

The application detects the current screen orientation using LocalConfiguration.

```kotlin
val configuration =
    LocalConfiguration.current

val isLandscape =
    configuration.screenWidthDp >
    configuration.screenHeightDp
```

Based on the orientation:

- Portrait mode uses a vertical Column layout.
- Landscape mode uses a horizontal Row layout.

This makes the application responsive and ensures that the complete account screen remains visible after rotation.

## 9. Important Code Implementation

### Saving the selected account type

```kotlin
fun onAccountTypeSelected(type: String) {
    savedStateHandle[KEY_ACCOUNT_TYPE] = type
}
```

### Retrieving the saved account type

```kotlin
private val selectedAccountType =
    savedStateHandle.getStateFlow(
        KEY_ACCOUNT_TYPE,
        "Savings"
    )
```

### Exposing UI State

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

### Observing UI State in Compose

```kotlin
val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()
```

## 10. How to Run the Application

Follow these steps to execute the project:

1. Open Android Studio.
2. Create a new Empty Activity project using Kotlin and Jetpack Compose.
3. Open the MainActivity.kt file.
4. Add the complete application code.
5. Add the required dependencies in app/build.gradle.kts.
6. Sync the Gradle files.
7. Start an Android Emulator or connect a physical Android device.
8. Click the Run button to execute the application.

## 11. Testing Procedure

The following steps can be used to test the application's functionality:

| Test Case | Expected Result |
|---|---|
| Launch application | Savings Account selected by default |
| Select Current Account | Current Account becomes selected |
| Select Salary Account | Salary Account becomes selected |
| Rotate device | Complete screen adjusts to landscape |
| Check selected value after rotation | Previously selected value remains selected |
| Rotate back to portrait | Layout returns to vertical orientation |
| Recreate Activity through system configuration change | Previously selected account type is restored |

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
- Automatic state restoration through SavedStateHandle.
- Responsive design for different orientations.
- Uses modern Android development practices.

## 14. Limitations

- The account number is currently sample data.
- The application does not connect to an actual banking server.
- No real banking transactions are performed.
- SavedStateHandle is intended for small UI state values and does not replace permanent database storage.

## 15. Conclusion

This project successfully demonstrates the implementation of an Account Screen using Kotlin, Jetpack Compose, ViewModel, and SavedStateHandle.

The application allows users to select different account types and automatically restores the previously selected value after device rotation or supported Activity recreation.

It demonstrates effective UI state management, lifecycle awareness, and responsive Android application development.

---

**Project Title:** Banking Account Screen Using ViewModel and SavedStateHandle

**Platform:** Android

**Language:** Kotlin

**UI Framework:** Jetpack Compose
