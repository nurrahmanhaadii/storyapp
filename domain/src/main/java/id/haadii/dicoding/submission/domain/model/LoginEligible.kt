package id.haadii.dicoding.submission.domain.model

/**
 * Created by nurrahmanhaadii on 18,March,2024
 */
data class LoginEligible(
    var isEmailValid: Boolean = false,
    var isPasswordValid: Boolean = false
)
