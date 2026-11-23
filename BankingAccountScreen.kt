package com.example.accountscreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class AccountUiState(
    val accountNumber: String = "123456789012",
    val selectedAccountType: String = "Savings"
)

class AccountViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_ACCOUNT_TYPE = "account_type"
    }

    private val selectedAccountType =
        savedStateHandle.getStateFlow(
            KEY_ACCOUNT_TYPE,
            "Savings"
        )

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

    fun onAccountTypeSelected(type: String) {
        savedStateHandle[KEY_ACCOUNT_TYPE] = type
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MaterialTheme {

                val accountViewModel: AccountViewModel = viewModel()

                AccountScreen(accountViewModel)
            }
        }
    }
}

@Composable
fun AccountScreen(viewModel: AccountViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val configuration =
        androidx.compose.ui.platform.LocalConfiguration.current

    val isLandscape =
        configuration.screenWidthDp > configuration.screenHeightDp

    val accountTypes = listOf(
        "Savings",
        "Current",
        "Salary"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isLandscape) 12.dp else 20.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "My Banking Account",
                style = if (isLandscape)
                    MaterialTheme.typography.titleLarge
                else
                    MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(
                    if (isLandscape) 8.dp else 20.dp
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                shape = RoundedCornerShape(16.dp),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                )
            ) {

                if (isLandscape) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "Account Details",
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text("Account Number")

                            Text(
                                text = uiState.accountNumber,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = 12.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "Select Account Type",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            accountTypes.forEach { type ->

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected =
                                                uiState.selectedAccountType == type,

                                            onClick = {
                                                viewModel.onAccountTypeSelected(type)
                                            },

                                            role = Role.RadioButton
                                        ),

                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    RadioButton(
                                        selected =
                                            uiState.selectedAccountType == type,

                                        onClick = {
                                            viewModel.onAccountTypeSelected(type)
                                        }
                                    )

                                    Text("$type Account")
                                }
                            }

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "Selected: ${uiState.selectedAccountType}",

                                color = MaterialTheme.colorScheme.primary,

                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }

                } else {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),

                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Account Details",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text("Account Number")

                        Text(
                            text = uiState.accountNumber,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "Select Account Type",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        accountTypes.forEach { type ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected =
                                            uiState.selectedAccountType == type,

                                        onClick = {
                                            viewModel.onAccountTypeSelected(type)
                                        },

                                        role = Role.RadioButton
                                    ),

                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                RadioButton(
                                    selected =
                                        uiState.selectedAccountType == type,

                                    onClick = {
                                        viewModel.onAccountTypeSelected(type)
                                    }
                                )

                                Text("$type Account")
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        HorizontalDivider()

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text =
                                "Selected Account: ${uiState.selectedAccountType}",

                            color = MaterialTheme.colorScheme.primary,

                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}